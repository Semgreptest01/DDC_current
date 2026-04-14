/**
*-------------------------------------------------------------------------------
* クラス名			DBAccess.class
* システム名称		共通
* 名称				ＤＢＡｃｃｅｓｓ・抽象クラス
* 会社名or所属名	富士ソフトＤＩＳ
* 作成日			2006/11/08 00:00:00
* @author			Y.Takabayashi
* @since			1.0
* @version			1.0
* *** 修正履歴 ***
* No.	Date		Author			Description
*-------------------------------------------------------------------------------
*/
package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import util.DateTimeUtility;
import util.MessageUtility;
import util.TextUtility;

public abstract class DBAccess extends Object implements java.io.Serializable
{
	// ステートメント
	/**
	 * 共通StatementHolder
	 */
	protected StatementHolder	cmnSttmntHldr	= new StatementHolder();
	/**
	 * 個別StatementHolder
	 */
	protected StatementHolder	appSttmntHldr	= new StatementHolder();
	/**
	 * 検索条件
	 */
	protected Vector	whrArgmnts			= new Vector();
	/**
	 * HTMLパラメータ
	 */
	protected Vector	htmlArgmnts			= new Vector();
	// HTMLタグ
	/**
	 * 明細HTML
	 */
	public Vector	dtlHtmls				= new Vector();
	// その他
	/**
	 * 画面ID
	 */
	public String	scrnId					= "";
	/**
	 * 画面名称
	 */
	public String	scrnNm					= "";
	/**
	 * メッセージ
	 */
	public String	dbaMsg					= "";
	/**
	 * メッセージボックス
	 */
	public Vector	dbaMsgBx				= new Vector();
	/**
	 * 明細件数
	 */
	public int		dtlCnt					= 0;
	/**
	 * コントロール日付(YYYYMMDD)
	 */
	public String	cntrlDt					= "";
	/**
	 * 最終更新日時
	 */
	public Vector	lstUpdDtTms				= new Vector();
	/**
	 * Session変数名
	 */
	public String	sssnArgNm				= "";
	/**
	 * エラー・例外発生時にRollBack処理をするかどうか
	 */
	public boolean	dbRllBckOnErrFlg		= true;
	/**
	 * ダウンロード用のsetContentType処理をしたかどうか
	 */
	public boolean	stDwnldCntntTypFlg		= false;
	
	// 定数
	
	/**
	********************************************************************************
	* 認証チェック（不正アクセス・チェック）
	* @param	session				(HttpSession)
	* @return	true:OK false:ERR	(boolean)
	********************************************************************************
	*/
	public abstract boolean athntctnChk(	HttpSession	session	) throws Exception;
	/**
	********************************************************************************
	* メニューパラメータの取得
	* @param	request	(HttpServletRequest)
	* @return	なし
	********************************************************************************
	*/
	public abstract void setMnPrmtr(	HttpServletRequest	request	) throws Exception;
	/**
	********************************************************************************
	* DBAccess初期化
	* @param	request	(HttpServletRequest)
	* @param	session	(HttpSession)
	* @param	con		(Connection)
	* @return	なし
	********************************************************************************
	*/
	public abstract void beforeBusinessProc(	HttpServletRequest	request,
												HttpSession			session,
												Connection			con	) throws Exception;
	/**
	********************************************************************************
	* ビジネスロジック後処理
	* @return	なし
	********************************************************************************
	*/
	public abstract void afterBusinessProc() throws Exception;
	/**
	********************************************************************************
	* 共通Statementファイル名の取得
	* @return	共通Statementファイル名	(String)
	********************************************************************************
	*/
	protected abstract String getCmnSttmntFlNm();
	/**
	********************************************************************************
	* 個別Statementファイル名の取得
	* @return	個別Statementファイル名	(String)
	********************************************************************************
	*/
	protected abstract String getAppSttmntFlNm();
	/**
	********************************************************************************
	* メッセージ初期化
	* @return	なし
	********************************************************************************
	*/
	public void clearMssg()
	{
		dbaMsg = "";		
		dbaMsgBx.clear();		
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 各種情報の子DBAccessへのコピー
	* @param	dbAccss			(DBAccess)
	* @param	sssnArgNmCpyFlg	(boolean)
	* @param	sssnArgNmCpyFlg	(boolean)
	* @return	なし
	********************************************************************************
	*/
	public void initChildDBAccess(	DBAccess	dbAccss,
									boolean	sssnArgNmCpyFlg,
									boolean	scrnDtCpyFlg	) throws Exception
	{
		dbAccss.cmnSttmntHldr		=	(StatementHolder)cmnSttmntHldr.clone();
		dbAccss.appSttmntHldr		=	(StatementHolder)appSttmntHldr.clone();
		dbAccss.whrArgmnts			=	(Vector)whrArgmnts.clone();
		dbAccss.htmlArgmnts			=	(Vector)htmlArgmnts.clone();
		dbAccss.cntrlDt				=	new String( cntrlDt );
		if ( sssnArgNmCpyFlg ) {
			dbAccss.sssnArgNm		=	new String( sssnArgNm );
		}
		if ( scrnDtCpyFlg ) {
			dbAccss.scrnId			=	new String( scrnId );
			dbAccss.scrnNm			=	new String( scrnNm );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* Statementファイル読み込み
	* @param	srvltCntxt	(ServletContext)
	* @return	なし
	********************************************************************************
	*/
	public void readDfltSttmntFl(	ServletContext	srvltCntxt	) throws Exception
	{
		cmnSttmntHldr.readSttmntFl( srvltCntxt, getCmnSttmntFlNm() );
		appSttmntHldr.readSttmntFl( srvltCntxt, getAppSttmntFlNm() );
	}
	/**
	********************************************************************************
	* SQLステートメント取得（共通）<br>
	* ※whrArgmntsへ置換え変数を入れてコールする。（リターン時にクリア）<br>
	* （例）<br>
	* whrArgmnts.add( xxxx );<br>
	* String sql = getSQL( 1 );
	* @param	idx	index								(int)
	* @return	SqlStatement(見つからない場合は、"")	(String)
	********************************************************************************
	*/
	protected String getCmnSQL(	int	idx	) throws Exception
	{
		String sql = cmnSttmntHldr.getSQL( idx, whrArgmnts );
		return sql;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* SQLステートメント取得（個別）<br>
	* ※whrArgmntsへ置換え変数を入れてコールする。（リターン時にクリア）<br>
	* （例）<br>
	* whrArgmnts.add( xxxx );<br>
	* String sql = getSQL( 1 );
	* @param	idx	index								(int)
	* @return	SqlStatement(見つからない場合は、"")	(String)
	********************************************************************************
	*/
	protected String getSQL(	int	idx	) throws Exception
	{
		String sql = appSttmntHldr.getSQL( idx, whrArgmnts );
		return sql;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* PreparedSQL取得（共通）
	* @param	idx	index								(int)
	* @return	SqlStatement(見つからない場合は、"")	(String)
	********************************************************************************
	*/
	protected String getCmnPreparedSQL(	int	idx	) throws Exception
	{
		String sql = cmnSttmntHldr.getPreparedSQL( idx );
		return sql;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* PreparedSQL取得（個別）
	* @param	idx	index								(int)
	* @return	SqlStatement(見つからない場合は、"")	(String)
	********************************************************************************
	*/
	protected String getPreparedSQL(	int	idx	) throws Exception
	{
		String sql = appSttmntHldr.getPreparedSQL( idx );
		return sql;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* HTML取得（共通）<br>
	* ※htmlArgmntsへ置換え変数を入れてコールする。（リターン時にクリア）<br>
	* （例）<br>
	* htmlArgmnts.add( xxxx );<br>
	* String html = getHTML( 1 );
	* @param	idx		index					(int)
	* @return	HTML(見つからない場合は、"")	(String)
	********************************************************************************
	*/
	protected String getCmnHTML(	int	idx	) throws Exception
	{
		String html = cmnSttmntHldr.getHTML( idx, htmlArgmnts );
		return html;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* HTML取得（個別）<br>
	* ※htmlArgmntsへ置換え変数を入れてコールする。（リターン時にクリア）<br>
	* （例）<br>
	* htmlArgmnts.add( xxxx );<br>
	* String html = getHTML( 1 );
	* @param	idx		index					(int)
	* @return	HTML(見つからない場合は、"")	(String)
	********************************************************************************
	*/
	protected String getHTML(	int	idx	) throws Exception
	{
		String html = appSttmntHldr.getHTML( idx, htmlArgmnts );
		return html;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* DB用文字列に変換
	* @param	str			(String)
	* @return	DB用文字列	(String)
	********************************************************************************
	*/
	protected String toDBString(	String	str	) throws Exception
	{
		return "'" + TextUtility.cvtNullToBlnk( str ) + "'";
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* DB用日付に変換
	* @param	str			(String)
	* @return	DB用文字列	(String)
	********************************************************************************
	*/
	public String toDBDate(	String	str	) throws Exception
	{
		return "{ d'" + ( ( str == null || "".equals( str ) ) ? "" : DateTimeUtility.cvtDate6( str ) ) + "'}";
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* DB用日時に変換
	* @param	str			(String)
	* @return	DB用文字列	(String)
	********************************************************************************
	*/
	protected String toDBDateTime(	String	str	) throws Exception
	{
		return ( ( str == null || "".equals( str ) ) ? "null" : "{ ts'" + str + "'}" );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* DB用NUMBERに変換
	* @param	str			(String)
	* @return	DB用文字列	(String)
	********************************************************************************
	*/
	protected String toDBInt(	String	str	) throws Exception
	{
		return ( str == null || "".equals( str ) ) ? "0" : str;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* DB用NUMBERに変換
	* @param	str			(String)
	* @return	DB用文字列	(String)
	********************************************************************************
	*/
	protected String toDBFloat(	String	str	) throws Exception
	{
		return ( str == null || "".equals( str ) ) ? "0" : str;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* PreparedStatementにデータを設定
	* @param	pstmt	(PreparedStatement)
	* @param	itm_num	(int)
	* @param	type	(String)
	* @param	dat		(String)
	* @return	なし
	********************************************************************************
	*/
	protected void setDatPstmt(	PreparedStatement	pstmt,
									int					itm_num,
									String				type,
									String				dat	) throws Exception
	{
		switch ( Integer.parseInt( type ) ) {
			case Const.INT_DB_STR:
				pstmt.setString( itm_num, dat );
			break;
			case Const.INT_DB_DT:
				String tmp_dat = ( dat == null || "".equals( dat ) ) ? null : DateTimeUtility.cvtDate6( dat );
				pstmt.setDate( itm_num, ( tmp_dat == null ? null : java.sql.Date.valueOf( tmp_dat ) ) );
			break;
			case Const.INT_DB_DT_TM:
				pstmt.setTimestamp( itm_num, Timestamp.valueOf( dat ) );
			break;
			case Const.INT_DB_INT:
				pstmt.setInt( itm_num, Integer.parseInt( dat ) );
			break;
			case Const.INT_DB_FLT:
				pstmt.setFloat( itm_num, Float.parseFloat( dat ) );
			break;
		}
		return;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* PreparedStatementにデータを設定
	* @param	pstmt	(PreparedStatement)
	* @param	itm_num	(int)
	* @param	type	(String)
	* @param	dat		(Object)
	* @return	なし
	********************************************************************************
	*/
	protected void setDatPstmt(	PreparedStatement	pstmt,
									int					itm_num,
									String				type,
									Object				dat	) throws Exception
	{
		setDatPstmt( pstmt, itm_num, type, (String)dat );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* PreparedStatementにデータを設定
	* @param	pstmt	(PreparedStatement)
	* @param	itm_num	(int)
	* @param	type	(String)
	* @param	dat		(int)
	* @return	なし
	********************************************************************************
	*/
	protected void setDatPstmt(	PreparedStatement	pstmt,
									int					itm_num,
									String				type,
									int					dat	) throws Exception
	{
		setDatPstmt( pstmt, itm_num, type, String.valueOf( dat ) );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* PreparedStatementにデータを設定
	* @param	pstmt		(PreparedStatement)
	* @param	itm_num		(int)
	* @param	srch_keys	(Vector)
	* @return	なし
	********************************************************************************
	*/
	protected void setDatPstmt(	PreparedStatement	pstmt,
									int					itm_num,
									Vector				srch_keys	) throws Exception
	{
		if ( srch_keys != null ) {
			String[] wk_srch_key = null;
			for ( int i = 0; i < srch_keys.size(); i++ ) {
				wk_srch_key = (String[])srch_keys.get( i );
				setDatPstmt( pstmt, itm_num++, wk_srch_key[ Const.ARRY_INDX_DB_TYP ], wk_srch_key[ Const.ARRY_INDX_DB_VL ] );
			}
		}
		return;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* PreparedStatementにデータを設定
	* @param	pstmt		(PreparedStatement)
	* @param	srch_keys	(Vector)
	* @return	なし
	********************************************************************************
	*/
	protected void setDatPstmt(	PreparedStatement	pstmt,
									Vector				srch_keys	) throws Exception
	{
		setDatPstmt( pstmt, 1, srch_keys );
		return;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* whrArgmntsにデータを設定
	* @param	type	(String)
	* @param	dat		(String)
	* @return	なし
	********************************************************************************
	*/
	protected void setDatWhrNms(	String	type,
									String	dat	) throws Exception
	{
		switch ( Integer.parseInt( type ) ) {
			case Const.INT_DB_STR:
				whrArgmnts.add( toDBString( dat ) );
			break;
			case Const.INT_DB_DT:
				whrArgmnts.add( toDBDate( dat ) );
			break;
			case Const.INT_DB_DT_TM:
				whrArgmnts.add( toDBDateTime( dat ) );
			break;
			case Const.INT_DB_INT:
				whrArgmnts.add( toDBInt( dat ) );
			break;
			case Const.INT_DB_FLT:
				whrArgmnts.add( toDBFloat( dat ) );
			break;
		}
		return;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* whrArgmntsにデータを設定
	* @param	type	(String)
	* @param	dat		(Object)
	* @return	なし
	********************************************************************************
	*/
	protected void setDatWhrNms(	String	type,
									Object	dat	) throws Exception
	{
		setDatWhrNms( type, (String)dat );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* whrArgmntsにデータを設定
	* @param	srch_keys	(Vector)
	* @return	なし
	********************************************************************************
	*/
	protected void setDatWhrNms(	Vector	srch_keys	) throws Exception
	{
		if ( srch_keys != null ) {
			String[] wk_srch_key = null;
			for ( int i = 0; i < srch_keys.size(); i++ ) {
				wk_srch_key = (String[])srch_keys.get( i );
				setDatWhrNms( wk_srch_key[ Const.ARRY_INDX_DB_TYP ], wk_srch_key[ Const.ARRY_INDX_DB_VL ] );
			}
		}
		return;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 最終更新日時の取得
	* @param	con				(Connection)
	* @param	sql				(String)
	* @param	srch_keys		(Vector)
	* @return	最終更新日時	(Vector)
	********************************************************************************
	*/
	protected Vector getLstUpdtDtTm(	Connection		con,
										String			sql,
										Vector			srch_keys	) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			Vector wk_lst_upd_dt_tms = new Vector();
			pstmt = con.prepareStatement( sql );
			setDatPstmt( pstmt, srch_keys );
			rs = pstmt.executeQuery();
			while ( rs.next() ) {
				wk_lst_upd_dt_tms.add( TextUtility.cvtNullToBlnk( DateTimeUtility.cvtDateTime( rs.getString( "UPD_DTM" ) ) ) );
			}
			return wk_lst_upd_dt_tms;
		} finally {
			if ( rs != null ) { rs.close(); }
			if ( pstmt != null ) { pstmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 最終更新日時の比較チェック
	* @param	con											(Connection)
	* @param	sql											(String)
	* @param	srch_keys									(Vector)
	* @param	lst_upd_dt_tms								(Vector)
	* @param	lck_flg										(boolean)
	* @return	ステータス( true:比較OK false:アンマッチ )	(boolean)
	********************************************************************************
	*/
	protected boolean chkLstUpdtDtTm(	Connection		con,
										String			sql,
										Vector			srch_keys,
										Vector			lst_upd_dt_tms,
										boolean		lck_flg	) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			boolean result = true;
			String wk_lst_upd_dt_tm = "";
			String lck_sql = "";
			if ( lck_flg ) {
				lck_sql = Config.SQL_FR_UPDT;
			}
			pstmt = con.prepareStatement( sql + lck_sql );
			setDatPstmt( pstmt, srch_keys );
			rs = pstmt.executeQuery();
			int i = 0;
			while ( rs.next() ) {
				wk_lst_upd_dt_tm	= TextUtility.cvtNullToBlnk( DateTimeUtility.cvtDateTime( rs.getString( "UPD_DTM" ) ) );
				if ( !wk_lst_upd_dt_tm.equals( lst_upd_dt_tms.get( i++ ) ) ) {
					result = false;
					dbaMsg	= MessageUtility.getMsg( MessageUtility.DBA_MSG_ID_06 );
					break;
				}
			}
			return result;
		} finally {
			if ( rs != null ) { rs.close(); }
			if ( pstmt != null ) { pstmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* レコード存在チェック
	* @param	con										(Connection)
	* @param	sql										(String)
	* @param	srch_keys								(Vector)
	* @param	rplc_strg_arry							(String[])
	* @param	lck_flg									(boolean)
	* @return	ステータス( true:存在 false:非存在 )	(boolean)
	********************************************************************************
	*/
	protected boolean chkRcrdExstnc(	Connection	con,
										String		sql,
										Vector		srch_keys,
										String[]	rplc_strg_arry,
										boolean	lck_flg	) throws Exception
	{
		boolean result = false;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String lck_sql = "";
		if ( lck_flg ) {
			lck_sql = Config.SQL_FR_UPDT;
		}
		try {
			pstmt = con.prepareStatement( TextUtility.rplcStrg( sql, rplc_strg_arry ) + lck_sql );
			setDatPstmt( pstmt, srch_keys );
			rs = pstmt.executeQuery();
			if ( rs.next() ) {
				result	= true;
			}
			return result;
		} finally {
			if ( rs != null ) { rs.close(); }
			if ( pstmt != null ) { pstmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* レコード存在チェック
	* @param	con										(Connection)
	* @param	sql										(String)
	* @param	srch_keys								(Vector)
	* @param	rplc_strg								(String)
	* @param	lck_flg									(boolean)
	* @return	ステータス( true:存在 false:非存在 )	(boolean)
	********************************************************************************
	*/
	protected boolean chkRcrdExstnc(	Connection	con,
										String		sql,
										Vector		srch_keys,
										String		rplc_strg,
										boolean	lck_flg	) throws Exception
	{
		String[] wk_arry = { rplc_strg };
		return chkRcrdExstnc( con, sql, srch_keys, wk_arry, lck_flg );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* レコード存在チェック
	* @param	con										(Connection)
	* @param	sql										(String)
	* @param	srch_keys								(Vector)
	* @param	lck_flg									(boolean)
	* @return	ステータス( true:存在 false:非存在 )	(boolean)
	********************************************************************************
	*/
	public boolean chkRcrdExstnc(	Connection		con,
									String			sql,
									Vector			srch_keys,
									boolean		lck_flg	) throws Exception
	{
		String[] wk_arry = {};
		return chkRcrdExstnc( con, sql, srch_keys, wk_arry, lck_flg );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* レコード存在チェック
	* @param	con										(Connection)
	* @param	pstmt									(PreparedStatement)
	* @param	srch_keys								(Vector)
	* @return	ステータス( true:存在 false:非存在 )	(boolean)
	********************************************************************************
	*/
	protected boolean chkRcrdExstnc(	Connection			con,
										PreparedStatement	pstmt,
										Vector				srch_keys ) throws Exception
	{
		boolean result = false;
		ResultSet rs = null;
		try {
			setDatPstmt( pstmt, srch_keys );
			rs = pstmt.executeQuery();
			if ( rs.next() ) {
				result	= true;
			}
			return result;
		} finally {
			if ( rs != null ) { rs.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* レコード件数取得
	* @param	con				(Connection)
	* @param	sql				(String)
	* @param	srch_keys		(Vector)
	* @param	lck_flg			(boolean)
	* @return	レコード件数	(int)
	********************************************************************************
	*/
	protected int getRcrdCnt(	Connection		con,
								String			sql,
								Vector			srch_keys,
								boolean		lck_flg	) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			int cnt = 0;
			String lck_sql = "";
			if ( lck_flg ) {
				lck_sql = Config.SQL_FR_UPDT;
			}
			pstmt = con.prepareStatement( sql + lck_sql );
			setDatPstmt( pstmt, srch_keys );
			rs = pstmt.executeQuery();
			while(rs.next()) {
				cnt = rs.getInt( "COUNT" );
			}
			return cnt;
		} finally {
			if ( rs != null ) { rs.close(); }
			if ( pstmt != null ) { pstmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* レコード件数取得
	* @param	con				(Connection)
	* @param	pstmt			(PreparedStatement)
	* @param	srch_keys		(Vector)
	* @return	レコード件数	(int)
	********************************************************************************
	*/
	protected int getRcrdCnt(	Connection			con,
								PreparedStatement	pstmt,
								Vector				srch_keys ) throws Exception
	{
		ResultSet rs = null;
		try {
			int cnt = 0;
			setDatPstmt( pstmt, srch_keys );
			rs = pstmt.executeQuery();
			while(rs.next()) {
				cnt = rs.getInt( "COUNT" );
			}
			return cnt;
		} finally {
			if ( rs != null ) { rs.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* コードの取得
	* @param	con				(Connection)
	* @param	sql				(String)
	* @param	srch_keys		(Vector)
	* @param	rplc_strg_arry	(String[])
	* @return	コード			(Vector)
	********************************************************************************
	*/
	protected Vector getCds(	Connection	con,
								String		sql,
								Vector		srch_keys,
								String[]	rplc_strg_arry	) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			Vector wk_cds = new Vector();
			pstmt = con.prepareStatement( TextUtility.rplcStrg( sql, rplc_strg_arry ) );
			setDatPstmt( pstmt, srch_keys );
			rs = pstmt.executeQuery();
			while ( rs.next() ) {
				String wk_str	= TextUtility.cvtNullToBlnk( rs.getString( "CD" ) );
				wk_cds.add( wk_str );
			}
			return wk_cds;
		} finally {
			if ( rs != null ) { rs.close(); }
			if ( pstmt != null ) { pstmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* コードの取得
	* @param	con			(Connection)
	* @param	sql			(String)
	* @param	srch_keys	(Vector)
	* @param	rplc_strg	(String)
	* @return	コード		(Vector)
	********************************************************************************
	*/
	protected Vector getCds(	Connection	con,
								String		sql,
								Vector		srch_keys,
								String		rplc_strg	) throws Exception
	{
		String[] wk_arry = { rplc_strg };
		return getCds( con, sql, srch_keys, wk_arry );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* コードの取得
	* @param	con			(Connection)
	* @param	sql			(String)
	* @param	srch_keys	(Vector)
	* @return	コード		(Vector)
	********************************************************************************
	*/
	protected Vector getCds(	Connection	con,
								String		sql,
								Vector		srch_keys	) throws Exception
	{
		String[] wk_arry = {};
		return getCds( con, sql, srch_keys, wk_arry );
	}
	/**
	********************************************************************************
	* コードの取得
	* @param	con			(Connection)
	* @param	sql			(String)
	* @param	srch_keys	(Vector)
	* @return	コード		(String)
	********************************************************************************
	*/
	protected String getCd(	Connection	con,
								String		sql,
								Vector		srch_keys	) throws Exception
	{
		String[] wk_arry = {};
		Vector wk_cds = getCds( con, sql, srch_keys, wk_arry );
		String wk_str = null;
		if ( wk_cds.size() > 0 ) {
			wk_str = (String)wk_cds.firstElement();
		}
		return wk_str;
	}
	/**
	********************************************************************************
	* コードの取得
	* @param	con			(Connection)
	* @param	pstmt		(PreparedStatement)
	* @param	srch_keys	(Vector)
	* @return	コード		(String)
	********************************************************************************
	*/
	protected String getCd(	Connection			con,
								PreparedStatement	pstmt,
								Vector				srch_keys ) throws Exception
	{
		ResultSet rs = null;
		try {
			String wk_cd = null;
			setDatPstmt( pstmt, srch_keys );
			rs = pstmt.executeQuery();
			while ( rs.next() ) {
				wk_cd	= TextUtility.cvtNullToBlnk( rs.getString( "CD" ) );
			}
			return wk_cd;
		} finally {
			if ( rs != null ) { rs.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 名称の取得
	* @param	con				(Connection)
	* @param	sql				(String)
	* @param	srch_keys		(Vector)
	* @param	rplc_strg_arry	(String[])
	* @return	名称			(String)
	********************************************************************************
	*/
	protected String getNm(	Connection	con,
								String		sql,
								Vector		srch_keys,
								String[]	rplc_strg_arry	) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String wk_nm = null;
			pstmt = con.prepareStatement( TextUtility.rplcStrg( sql, rplc_strg_arry ) );
			setDatPstmt( pstmt, srch_keys );
			rs = pstmt.executeQuery();
			while ( rs.next() ) {
				wk_nm	= TextUtility.cvtNullToBlnk( TextUtility.cnvrtSpclChr( rs.getString( "NM" ) ) );
			}
			return wk_nm;
		} finally {
			if ( rs != null ) { rs.close(); }
			if ( pstmt != null ) { pstmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 名称の取得
	* @param	con			(Connection)
	* @param	sql			(String)
	* @param	srch_keys	(Vector)
	* @param	rplc_strg	(String)
	* @return	名称		(String)
	********************************************************************************
	*/
	protected String getNm(	Connection	con,
								String		sql,
								Vector		srch_keys,
								String		rplc_strg	) throws Exception
	{
		String[] wk_arry = { rplc_strg };
		return getNm( con, sql, srch_keys, wk_arry );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 名称の取得
	* @param	con			(Connection)
	* @param	sql			(String)
	* @param	srch_keys	(Vector)
	* @return	名称		(String)
	********************************************************************************
	*/
	protected String getNm(	Connection	con,
								String		sql,
								Vector		srch_keys	) throws Exception
	{
		String[] wk_arry = {};
		return getNm( con, sql, srch_keys, wk_arry );
	}
	/**
	********************************************************************************
	* 名称の取得
	* @param	con			(Connection)
	* @param	pstmt		(PreparedStatement)
	* @param	srch_keys	(Vector)
	* @return	名称		(String)
	********************************************************************************
	*/
	protected String getNm(	Connection			con,
								PreparedStatement	pstmt,
								Vector				srch_keys ) throws Exception
	{
		ResultSet rs = null;
		try {
			String wk_nm = null;
			setDatPstmt( pstmt, srch_keys );
			rs = pstmt.executeQuery();
			while ( rs.next() ) {
				wk_nm	= TextUtility.cvtNullToBlnk( TextUtility.cnvrtSpclChr( rs.getString( "NM" ) ) );
			}
			return wk_nm;
		} finally {
			if ( rs != null ) { rs.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* コードと名称の取得
	* @param	con				(Connection)
	* @param	sql				(String)
	* @param	srch_keys		(Vector)
	* @param	rplc_strg_arry	(String[])
	* @return	コードと名称	(Vector)
	********************************************************************************
	*/
	protected Vector getCdAndNms(	Connection	con,
									String		sql,
									Vector		srch_keys,
									String[]	rplc_strg_arry	) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			Vector wk_cdAndNms = new Vector();
			pstmt = con.prepareStatement( TextUtility.rplcStrg( sql, rplc_strg_arry ) );
			setDatPstmt( pstmt, srch_keys );
			rs = pstmt.executeQuery();
			String[] wk_arry = { "", "" };
			while ( rs.next() ) {
				wk_arry[ Const.ARRY_INDX_CD ]	= TextUtility.cvtNullToBlnk( rs.getString( "CD" ) );
				wk_arry[ Const.ARRY_INDX_NM ]	= TextUtility.cvtNullToBlnk( TextUtility.cnvrtSpclChr( rs.getString( "NM" ) ) );
				wk_cdAndNms.add( wk_arry.clone() );
			}
			return wk_cdAndNms;
		} finally {
			if ( rs != null ) { rs.close(); }
			if ( pstmt != null ) { pstmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* コードと名称の取得
	* @param	con				(Connection)
	* @param	sql				(String)
	* @param	srch_keys		(Vector)
	* @param	rplc_strg		(String)
	* @return	コードと名称	(Vector)
	********************************************************************************
	*/
	protected Vector getCdAndNms(	Connection	con,
									String		sql,
									Vector		srch_keys,
									String		rplc_strg	) throws Exception
	{
		String[] wk_arry = { rplc_strg };
		return getCdAndNms( con, sql, srch_keys, wk_arry );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* コードと名称の取得
	* @param	con				(Connection)
	* @param	sql				(String)
	* @param	srch_keys		(Vector)
	* @return	コードと名称	(Vector)
	********************************************************************************
	*/
	public Vector getCdAndNms(	Connection	con,
								String		sql,
								Vector		srch_keys	) throws Exception
	{
		String[] wk_arry = {};
		return getCdAndNms( con, sql, srch_keys, wk_arry );
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
