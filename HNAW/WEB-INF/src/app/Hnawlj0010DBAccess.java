/**
*-------------------------------------------------------------------------------
* クラス名			Hnawlj0010DBAccess
* システム名称		計画終了対象商品案内
* 名称				HNAW0010 計画終了対象商品ダウンロード
* 会社名or所属名	株式会社ヴィクサス
* 作成日			2010/01/18 00:00:00
* @author			Hirata
* @since			1.0
* @version			1.0
* *** 修正履歴 ***
* No.	Date		Author			Description
*-------------------------------------------------------------------------------
*/
package app;

import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.CollectionUtility;
import util.DateTimeUtility;
import util.ExcelUtility;
import util.JspUtility;
import util.TextUtility;
import util.UpDownloadUtility;

import common.Const;

public class Hnawlj0010DBAccess extends AppDBAccess
{
	private static final long serialVersionUID = 1L;
	
	// Statementファイル
	/**
	 * 個別Statementファイル
	 */
	private final static String APP_STTMNT_FL_NM = "Hnawlj0010.txt";
	
	// パラメータ
	/**
	 * 利用者コード
	 */
	public String usrCd = "";
	/**
	 * センター
	 */
	public String cntrCd = CNTR_CD_ALL;
	/**
	 * 店案内週 開始
	 */
	public String tpAnniYrwkNoStrt = "";
	/**
	 * 店案内週 終了
	 */
	public String tpAnniYrwkNoEnd = "";
	
	// 変数
	/**
	 * 特権ユーザフラグ
	 */
	public boolean sprUsrFlg = false;
	/**
	 * 利用者コードエラー警告フラグ
	 */
	public boolean usrCdErrAlrtFlg = false;
	/**
	 * メッセージ１
	 */
	public String msg1 = "";
	/**
	 * メッセージ２
	 */
	public String msg2 = "";
	/**
	 * 利用者名
	 */
	public String usrNm = "";
	/**
	 * オプション文字列 センター
	 */
	public String optnStrng_cntrCd = "";
	/**
	 * オプション文字列 店案内週
	 */
	public String optnStrng_tpAnniYrwkNo = "";
	/**
	 * コードと名称のマップ 店案内週
	 */
	public HashMap cdAndNmsMp_tpAnniYrwkNo = new HashMap();
	
	// 明細
	
	// 定数
	/**
	 * 画面名
	 */
	private static final String SCRN_NM = "計画終了対象商品ダウンロード";
	
	/**
	 * セレクトボックスに表示する名称 全て
	 */
	private static final String SLCT_NM_ALL = "全て";
	/**
	 * センターコード 全て
	 */
	public static final String CNTR_CD_ALL = "000000";
	
	/**
	 * 表示する店案内週の接尾辞
	 */
	private static final String DSPLY_TP_ANNI_YR_WK_NO_SFFX = "週";
	
	/**
	 * ダウンロードCSVファイルの最大件数
	 */
	public static final int CSV_CNT_MX = 15000;
	
	/**
	 * ダウンロードCSVファイル名
	 */
	private static final String DWNLD_CSV_FL_NM = "KEIKAKUSYURYO.[&STR0].[&STR1].csv";
	
	/**
	 * CSV列名
	 */
	private static final String[] CSV_CLMN_NMS = { "センターコード", "センター名", "Dept名", "Class名", "商品コード", "商品名", "店案内日", "計画終了有効開始日", "取消日" };
	
	/**
	 * setSprUsrFlg 特権ユーザフラグを設定
	 */
	private static final int SQL_000 = 0;
	/**
	 * setMsg メッセージ取得
	 */
	private static final int SQL_001 = 1;
	/**
	 * setSlctLstDt セレクトボックスに表示するリストのデータ取得 センター
	 */
	private static final int SQL_002 = 2;
	/**
	 * setSlctLstDt センター名取得
	 */
	private static final int SQL_003 = 3;
	/**
	 * setSlctLstDt セレクトボックスに表示するリストのデータ取得 店案内週
	 */
	private static final int SQL_004 = 4;
	/**
	 * downloadCsv CSVダウンロード 件数 0
	 */
	private static final int SQL_005 = 5;
	/**
	 * downloadCsv CSVダウンロード 件数 1
	 */
	private static final int SQL_006 = 6;
	/**
	 * downloadCsv CSVダウンロード データ取得 0
	 */
	private static final int SQL_007 = 7;
	/**
	 * downloadCsv CSVダウンロード キー取得 条件 センター
	 */
	private static final int SQL_008 = 8;
	/**
	 * downloadCsv CSVダウンロード キー取得 1
	 */
	private static final int SQL_009 = 9;
	/**
	 * rgstDwnldLg HNT_ダウンロードログ登録
	 */
	private static final int SQL_010 = 10;
	
	/**
	********************************************************************************
	* 個別Statementファイル名の取得
	* @return	個別Statementファイル名	(String)
	********************************************************************************
	*/
	protected String getAppSttmntFlNm()
	{
		return APP_STTMNT_FL_NM;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* DBAccess初期化
	* @param	request				(HttpServletRequest)
	* @param	session				(HttpSession)
	* @param	con					(Connection)
	* @return	true:OK false:ERR	(boolean)
	********************************************************************************
	*/
	public boolean initDBAccess(	HttpServletRequest	request,
									HttpSession			session,
									Connection			con	) throws Exception
	{
		setCntrlDt( con );
		scrnNm = SCRN_NM;
		setSprUsrFlg( con );
		setMsg( con );
		if ( !sprUsrFlg ) {
			setUsrNm( con );
			setSlctLstDt( con );
		}
		return true;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 特権ユーザフラグを設定
	* @param	con	(Connection)
	* @return	なし
	********************************************************************************
	*/
	private void setSprUsrFlg(	Connection	con ) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement( getPreparedSQL( SQL_000 ) );
			int itm_num = 1;
			setDatPstmt( pstmt, itm_num++, Const.DB_STR, super.user_namecd );
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				sprUsrFlg = true;
			} else {
				sprUsrFlg = false;
				usrCd = super.user_namecd;
			}
		} finally {
			if ( rs != null ) { rs.close(); }
			if ( pstmt != null ) { pstmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* メッセージ取得
	* @param	con					(Connection)
	* @return	なし
	********************************************************************************
	*/
	private void setMsg(	Connection	con	) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement( getPreparedSQL( SQL_001 ) );
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				msg1 = TextUtility.cnvrtSpclChr( rs.getString( "MSG_1" ) );
				msg2 = TextUtility.cnvrtSpclChr( rs.getString( "MSG_2" ) );
			} else {
				msg1 = "";
				msg2 = "";
			}
			
			msg1 = TextUtility.cvtBlnkToHtmlSpc( msg1 );
			msg2 = TextUtility.cvtBlnkToHtmlSpc( msg2 );
		} finally {
			if ( rs != null ) { rs.close(); }
			if ( pstmt != null ) { pstmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 利用者名取得
	* @param	con					(Connection)
	* @return	true:OK false:ERR	(boolean)
	********************************************************************************
	*/
	public boolean setUsrNm(	Connection	con	) throws Exception
	{
		CallableStatement cStmt = null;
		try {
			usrNm = null;
			usrCdErrAlrtFlg = false;
			cStmt = con.prepareCall( "begin smzzop102.main(?, ?, ?); end;" );
			cStmt.setString( 1, usrCd );
			cStmt.registerOutParameter( 2, Types.CHAR );
			cStmt.registerOutParameter( 3, Types.CHAR );
			cStmt.execute();
			if ( "00".equals( cStmt.getString( 2 ) ) ) {
				usrNm = TextUtility.cvtBlnkToHtmlSpc( TextUtility.cnvrtSpclChr( cStmt.getString( 3 ) ) );
				return true;
			} else {
				usrCdErrAlrtFlg = true;
				return false;
			}
		} finally {
			if ( cStmt != null ) { cStmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* セレクトボックスに表示するリストのデータ取得
	* @param	con	(Connection)
	* @return	なし
	********************************************************************************
	*/
	public void setSlctLstDt(	Connection	con ) throws Exception
	{
		String[] wk_arry = { "", "" };
		PreparedStatement pstmt0 = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		int itm_num;
		try {
			String[] srch_key = { Const.DB_STR, usrCd };
			Vector srch_keys = new Vector();
			srch_keys.add( srch_key.clone() );
			srch_keys.add( srch_key );
			
			Vector cdAndNms_cntrCd = new Vector();
			Vector cdAndNms_tpAnniYrwkNo = new Vector();
			
			ResultSet rs0 = null;
			ResultSet rs1 = null;
			
			pstmt0 = con.prepareStatement( getPreparedSQL( SQL_002 ) );
			pstmt1 = con.prepareStatement( getPreparedSQL( SQL_003 ) );
			pstmt2 = con.prepareStatement( getPreparedSQL( SQL_004 ) );
			
			cntrCd = CNTR_CD_ALL;
			try {
				wk_arry[ Const.ARRY_INDX_CD ] = CNTR_CD_ALL;
				wk_arry[ Const.ARRY_INDX_NM ] = SLCT_NM_ALL;
				cdAndNms_cntrCd.add( wk_arry.clone() );
				
				itm_num = 1;
				setDatPstmt( pstmt0, itm_num++, Const.DB_STR, usrCd );
				rs0 = pstmt0.executeQuery();
				while ( rs0.next() ) {
					String tmpCntrCd = TextUtility.cvtNullToBlnk( rs0.getString( "CNTR_CD" ) );
					srch_key[ Const.ARRY_INDX_DB_VL ] = tmpCntrCd;
					wk_arry[ Const.ARRY_INDX_CD ] = tmpCntrCd;
					wk_arry[ Const.ARRY_INDX_NM ] = super.getNm( con, pstmt1, srch_keys );
					cdAndNms_cntrCd.add( wk_arry.clone() );
				}
			} finally {
				if ( rs0 != null ) { rs0.close(); }
			}
			
			tpAnniYrwkNoStrt = "";
			tpAnniYrwkNoEnd = "";
			try {
				itm_num = 1;
				setDatPstmt( pstmt2, itm_num++, Const.DB_STR, usrCd );
				rs1 = pstmt2.executeQuery();
				while ( rs1.next() ) {
					String tpAnniYrwkNo = TextUtility.cvtNullToBlnk( rs1.getString( "TP_ANNI_YR_WK_NO" ) );
					String wkStrtDt = TextUtility.cvtNullToBlnk( DateTimeUtility.cvtDate( rs1.getString( "TP_ANNI_WK_STRT_DT" ) ) );
					wk_arry[ Const.ARRY_INDX_CD ] = tpAnniYrwkNo;
					wk_arry[ Const.ARRY_INDX_NM ] = wkStrtDt + DSPLY_TP_ANNI_YR_WK_NO_SFFX;
					cdAndNms_tpAnniYrwkNo.add( wk_arry.clone() );
					cdAndNmsMp_tpAnniYrwkNo.put( tpAnniYrwkNo, wkStrtDt );
					if ( "".equals( tpAnniYrwkNoStrt ) ) {
						tpAnniYrwkNoStrt = tpAnniYrwkNo;
						tpAnniYrwkNoEnd = tpAnniYrwkNo;
					}
				}
			} finally {
				if ( rs1 != null ) { rs1.close(); }
			}
			
			optnStrng_cntrCd = CollectionUtility.cnvrtSprtdLnStrngFrmVctr( JspUtility.getOptnTg( cdAndNms_cntrCd, null, false, false ) );
			optnStrng_tpAnniYrwkNo = CollectionUtility.cnvrtSprtdLnStrngFrmVctr( JspUtility.getOptnTg( cdAndNms_tpAnniYrwkNo, null, false, false ) );
		} finally {
			if ( pstmt0 != null ) { pstmt0.close(); }
			if ( pstmt1 != null ) { pstmt1.close(); }
			if ( pstmt2 != null ) { pstmt2.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* requestパラメタの取得
	* @param	request	(HttpServletRequest)
	* @return	なし
	********************************************************************************
	*/
	public void setRqstPrmtr(	HttpServletRequest	request	) throws Exception
	{
		String tmpUsrCd			= request.getParameter( "usrCd" );
		if ( tmpUsrCd != null ) {
			usrCd = tmpUsrCd;
		} else {
			cntrCd				= request.getParameter( "cntrCd" );
			tpAnniYrwkNoStrt	= request.getParameter( "tpAnniYrwkNoStrt" );
			tpAnniYrwkNoEnd		= request.getParameter( "tpAnniYrwkNoEnd" );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* CSV件数取得
	* @param	con		(Connection)
	* @return	CSV件数	(int)
	********************************************************************************
	*/
	public int getCsvCnt(	Connection	con )
	{
		int csvCnt = -1;
		
		try {
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			try {
				Vector kys = new Vector();
				String sql = getPreparedSQL( SQL_005 );
				sql += getPreparedSQL( SQL_007 );
				if ( !CNTR_CD_ALL.equals( cntrCd ) ) {
					sql += getPreparedSQL( SQL_008 );
					kys.add( cntrCd );
				}
				sql += getPreparedSQL( SQL_009 );
				sql += getPreparedSQL( SQL_006 );
				pstmt = con.prepareStatement( sql );
				int itm_num = 1;
				setDatPstmt( pstmt, itm_num++, Const.DB_STR, usrCd );
				setDatPstmt( pstmt, itm_num++, Const.DB_STR, tpAnniYrwkNoStrt );
				setDatPstmt( pstmt, itm_num++, Const.DB_STR, tpAnniYrwkNoEnd );
				Iterator kysItrtr = kys.iterator();
				while ( kysItrtr.hasNext() ) {
					setDatPstmt( pstmt, itm_num++, Const.DB_STR, kysItrtr.next() );
				}
				
				rs = pstmt.executeQuery();
				
				if ( rs.next() ) {
					int tmpCnt = rs.getInt( "CNT" );
					rgstDwnldLg( con, tmpCnt );
					csvCnt = tmpCnt;
				}
			} finally {
				if ( rs != null ) { rs.close(); }
				if ( pstmt != null ) { pstmt.close(); }
			}
		} catch ( Exception e ) {
		}
		
		return csvCnt;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* HNT_ダウンロードログ登録
	* @param	con		(Connection)
	* @param	csvCnt	(int)
	* @return	なし
	********************************************************************************
	*/
	private void rgstDwnldLg(	Connection	con,	int	csvCnt ) throws Exception
	{
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement( getPreparedSQL( SQL_010 ) );
			int itm_num = 1;
			setDatPstmt( pstmt, itm_num++, Const.DB_STR, super.user_namecd );
			setDatPstmt( pstmt, itm_num++, Const.DB_STR, usrCd );
			setDatPstmt( pstmt, itm_num++, Const.DB_DT_TM, DateTimeUtility.curr_Timestamp() );
			setDatPstmt( pstmt, itm_num++, Const.DB_STR, scrnId );
			setDatPstmt( pstmt, itm_num++, Const.DB_STR, cntrCd );
			setDatPstmt( pstmt, itm_num++, Const.DB_DT, cdAndNmsMp_tpAnniYrwkNo.get( tpAnniYrwkNoStrt ) );
			setDatPstmt( pstmt, itm_num++, Const.DB_DT, cdAndNmsMp_tpAnniYrwkNo.get( tpAnniYrwkNoEnd ) );
			setDatPstmt( pstmt, itm_num++, Const.DB_INT, csvCnt );
			pstmt.executeUpdate();
		} finally {
			if ( pstmt != null ) { pstmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* CSVダウンロード
	* @param	response			(HttpServletResponse)
	* @param	con					(Connection)
	* @return	true:OK false:ERR	(boolean)
	********************************************************************************
	*/
	public boolean downloadCsv(	HttpServletResponse	response,
									Connection			con ) throws Exception
	{
		boolean rslt = true;
		
		String[] kyArry = { cntrCd, super.cntrlDt };
		String flNm = TextUtility.rplcStrg( DWNLD_CSV_FL_NM, kyArry );
		
		ArrayList arryLst = new ArrayList();
		for ( int i = 0; i < CSV_CLMN_NMS.length; i++ ) {
			arryLst.add( CSV_CLMN_NMS[ i ] );
		}
		
		UpDownloadUtility.setCntntTypFrCsvDwnld( response, this );
		UpDownloadUtility.setDwnldHdr( response, flNm );
		
		// HttpServletResponseのPrintWriterはエラー出力にも使うのでcloseしない
		PrintWriter prntWrtr = response.getWriter();
		prntWrtr.println( UpDownloadUtility.getCsvLn( arryLst, false ) );
		prntWrtr.flush();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			Vector kys = new Vector();
			String sql = getPreparedSQL( SQL_007 );
			if ( !CNTR_CD_ALL.equals( cntrCd ) ) {
				sql += getPreparedSQL( SQL_008 );
				kys.add( cntrCd );
			}
			sql += getPreparedSQL( SQL_009 );
			pstmt = con.prepareStatement( sql );
			int itm_num = 1;
			setDatPstmt( pstmt, itm_num++, Const.DB_STR, usrCd );
			setDatPstmt( pstmt, itm_num++, Const.DB_STR, tpAnniYrwkNoStrt );
			setDatPstmt( pstmt, itm_num++, Const.DB_STR, tpAnniYrwkNoEnd );
			Iterator kysItrtr = kys.iterator();
			while ( kysItrtr.hasNext() ) {
				setDatPstmt( pstmt, itm_num++, Const.DB_STR, kysItrtr.next() );
			}
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				arryLst.clear();
				arryLst.add( ExcelUtility.addEql( TextUtility.cvtNullToBlnk( rs.getString( "CNTR_CD" ) ) ) );
				arryLst.add( TextUtility.cvtNullToBlnk( TextUtility.cnvrtSpclChrFrCsvDwnld( rs.getString( "CNTR_NM" ) ) ) );
				arryLst.add( TextUtility.cvtNullToBlnk( TextUtility.cnvrtSpclChrFrCsvDwnld( rs.getString( "DEPT_NM" ) ) ) );
				arryLst.add( TextUtility.cvtNullToBlnk( TextUtility.cnvrtSpclChrFrCsvDwnld( rs.getString( "CLASS_NM" ) ) ) );
				arryLst.add( ExcelUtility.addEql( TextUtility.cvtNullToBlnk( rs.getString( "SHN_CD" ) ) ) );
				arryLst.add( TextUtility.cvtNullToBlnk( TextUtility.cnvrtSpclChrFrCsvDwnld( rs.getString( "SHN_NM" ) ) ) );
				arryLst.add( TextUtility.cvtNullToBlnk( DateTimeUtility.cvtDate( rs.getString( "TP_ANNI_DT" ) ) ) );
				arryLst.add( TextUtility.cvtNullToBlnk( DateTimeUtility.cvtDate( rs.getString( "KIKK_SYR_STDT" ) ) ) );
				arryLst.add( TextUtility.cvtNullToBlnk( DateTimeUtility.cvtDate( rs.getString( "SIS_TRKS_DT" ) ) ) );
				prntWrtr.println( UpDownloadUtility.getCsvLn( arryLst, false ) );
			}
		} finally {
			if ( rs != null ) { rs.close(); }
			if ( pstmt != null ) { pstmt.close(); }
		}
		prntWrtr.flush();
		
		return rslt;
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
