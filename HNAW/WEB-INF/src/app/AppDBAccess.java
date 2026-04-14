/**
*-------------------------------------------------------------------------------
* クラス名			AppDBAccess.class
* システム名称		共通
* 名称				共通ＤＢＡｃｃｅｓｓ・抽象クラス
* 会社名or所属名	富士ソフトＤＩＳ
* 作成日			2006/11/08 00:00:00
* @author			Hirata
* @since			1.0
* @version			1.0
* *** 修正履歴 ***
* No.	Date		Author			Description
*-------------------------------------------------------------------------------
*/
package app;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import util.CollectionUtility;
import util.CommonUtility;
import util.DateTimeUtility;
import util.TextUtility;

import common.Config;
import common.Const;
import common.DBAccess;

public abstract class AppDBAccess extends DBAccess
{
	// Statementファイル
	/**
	 * 共通Statementファイル
	 */
	private final static String CMN_STTMNT_FL_NM = "App.txt";
	// メニューパラメータ
	/**
	 * 利用者情報未取得フラグ
	 */
	public	boolean notStUserInfoFlg	= true;
	/**
	 * ユーザー 氏名コード
	 */
	public String	user_namecd			= null;
	/**
	 * ユーザー 組織グループ
	 */
	public	String	user_sosiki_grp		= null;
	/**
	 * ユーザー 職位グループ
	 */
	public	String	user_syokui_grp		= null;
	/**
	 * ユーザー 連番
	 */
	public	String	user_renban			= null;
	// 定数
	/**
	 * 全画面用・画面ヘッダ
	 */
	private static final int   C_APP_HTML_000	= 0;

	/**
	 * 共通・日付コントロール当日日付の取得
	 */
	private static final int   C_APP_SQL_000		= 0;
	/**
	********************************************************************************
	* DBAccess初期化
	* @param	request				(HttpServletRequest)
	* @param	session				(HttpSession)
	* @param	con					(Connection)
	* @return	true:OK false:ERR	(boolean)
	********************************************************************************
	*/
	public abstract boolean initDBAccess(	HttpServletRequest	request,
												HttpSession			session,
												Connection			con	) throws Exception;
	/**
	********************************************************************************
	* 共通Statementファイル名の取得
	* @return	共通Statementファイル名	(String)
	********************************************************************************
	*/
	protected String getCmnSttmntFlNm()
	{
		return CMN_STTMNT_FL_NM;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* メニューパラメータの取得
	* @param	request	(HttpServletRequest)
	* @return	なし
	********************************************************************************
	*/
	public void setMnPrmtr(	HttpServletRequest	request	) throws Exception
	{
		if ( request.getParameter( Const.PRMTR_NM_USER_ID ) != null ) {
			user_namecd		=	(String)request.getParameter( Const.PRMTR_NM_USER_ID );
			user_sosiki_grp	=	(String)request.getParameter( "sosiki_grp" );
			user_syokui_grp	=	(String)request.getParameter( "syokui_grp" );
			user_renban		=	(String)request.getParameter("renban");
			CommonUtility.debugPrint( "******* メニューパラメータ ********" );
			CommonUtility.debugPrint( "* namecd       : " + user_namecd );
			CommonUtility.debugPrint( "* sosiki_grp   : " + user_sosiki_grp );
			CommonUtility.debugPrint( "* syokui_grp   : " + user_syokui_grp );
			CommonUtility.debugPrint( "* renban       : " + user_renban );
			CommonUtility.debugPrint( "**********************************" );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ビジネスロジック前処理
	* @param	request	(HttpServletRequest)
	* @param	session	(HttpSession)
	* @param	con		(Connection)
	* @return	なし
	********************************************************************************
	*/
	public void beforeBusinessProc(	HttpServletRequest	request,
										HttpSession			session,
										Connection			con	) throws Exception
	{
		setUsrInfo( con );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ビジネスロジック後処理
	* @return		なし
	********************************************************************************
	*/
	public void afterBusinessProc() throws Exception
	{
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 認証チェック（不正アクセス・チェック）
	* @param	session				(HttpSession)
	* @return	true:OK false:ERR	(boolean)
	********************************************************************************
	*/
	public boolean athntctnChk(	HttpSession	session	) throws Exception
	{
		if ( session.getAttribute( Const.PRMTR_NM_USER_ID ) == null ) {
			AppMessage.msgOut( dbaMsgBx, AppMessage.C_APP_MSG_ID_01 );
			AppMessage.msgOut( dbaMsgBx, AppMessage.C_APP_MSG_ID_02 );
			dbaMsg = AppMessage.getMsg( AppMessage.C_APP_MSG_ID_00 );
			return false;
		}
		return true;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 利用者情報の取得
	* @param	con		(Connection)
	* @return	なし
	********************************************************************************
	*/
	public void setUsrInfo(	Connection	con	) throws Exception
	{
		if ( notStUserInfoFlg ) {
			notStUserInfoFlg = false;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	****************************************************************************
	* 画面用ヘッダHTML生成
	* @param	msgBx	メッセージ（複数行）	(Vector)
	* @return	画面用ヘッダHTML				(String)
	****************************************************************************
	*/
	public String getHdrHtml(	Vector msgBx	) throws Exception
	{
		htmlArgmnts.add( scrnId );
		htmlArgmnts.add( scrnNm );
		htmlArgmnts.add( CollectionUtility.cnvrtSprtdStrngFrmVctr( msgBx, "\n" ) );
		return getCmnHTML( C_APP_HTML_000 );
	}	// ----------------------- End of Method -------------------------------
	/**
	********************************************************************************
	* サービス時間チェック
	* @param	request									(HttpServletRequest)
	* @param	con										(Connection)
	* @return	true:サービス中 false:サービス停止中	(boolean)
	********************************************************************************
	*/
	public boolean srvcTmChk(	HttpServletRequest	request,
								Connection			con	) throws Exception
	{
		CallableStatement cStmt = null;
		try {
			boolean rslt = true;
			if ( Const.SW_ON.equals( Config.TIME_SW ) && !AppConst.RENBAN_NT_CHK_SRVC_TM.equals( user_renban ) ) {
				String errSw = "";
				String errMssg = "";
				try {
					// Oracle Stored Function Call
					cStmt = con.prepareCall( "begin smzzop001.main(?, ?, ?); end;" );
					cStmt.setString( 1, user_renban );
					cStmt.registerOutParameter( 2, Types.CHAR );
					cStmt.registerOutParameter( 3, Types.CHAR );
					cStmt.execute();
					errSw = cStmt.getString( 2 );
				} catch ( SQLException se ) {
					CommonUtility.debugMsg( dbaMsgBx, se.getMessage() );
					// サービス時間チェック（SQL Exception）
					errMssg = AppMessage.getMsg( AppMessage.C_APP_MSG_ID_05 );
					rslt = false;
				} catch ( Exception e ) {
					CommonUtility.debugMsg( dbaMsgBx, e.getMessage() );
					// サービス時間チェック（Exception）
					errMssg = AppMessage.getMsg( AppMessage.C_APP_MSG_ID_06 );
					rslt = false;
				}
				// Status=00 OK
				if ( rslt && !"00".equals( errSw ) ) {
					if ( "01".equals( errSw ) ) {
						// サービス時間外です。
						errMssg = AppMessage.getMsg( AppMessage.C_APP_MSG_ID_04 );
					} else if ( "02".equals( errSw ) ) {
						// バッチ処理中のためサービス停止中です。
						errMssg = AppMessage.getMsg( AppMessage.C_APP_MSG_ID_07 );
					} else if ( "03".equals( errSw ) ) {
						// トラブルのためサービス停止中です。
						errMssg = AppMessage.getMsg( AppMessage.C_APP_MSG_ID_08 );
					} else if ( "04".equals( errSw ) ) {
						// マシン保守のためサービス停止中です。
						errMssg = AppMessage.getMsg( AppMessage.C_APP_MSG_ID_09 );
					} else if ( "08".equals( errSw ) ) {
						// サービス時間チェック（NO DATA FOUND）
						errMssg = AppMessage.getMsg( AppMessage.C_APP_MSG_ID_10 );
					} else if ( "09".equals( errSw ) ) {
						// サービス時間チェック（データ取得エラー）
						errMssg = AppMessage.getMsg( AppMessage.C_APP_MSG_ID_11 );
					} else if ( "21".equals( errSw ) ) {
						// 月次確定処理中のためサービス停止中です。
						errMssg = AppMessage.getMsg( AppMessage.C_APP_MSG_ID_12 );
					} else {
						// サービス時間チェック（その他）
						errMssg = AppMessage.getMsg( AppMessage.C_APP_MSG_ID_13 );
					}
					// サービス時間チェック
					rslt = false;
				}
				if ( !rslt ) {
					dbaMsg = errMssg;
					CommonUtility.debugMsg( dbaMsgBx, "メニュー連番：" + user_renban );
				}
			}
			return	rslt;
		} finally {
			if ( cStmt != null ) { cStmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 各種情報の子DBAccessへのコピー
	* @param	dbAccss			(AppDBAccess)
	* @param	sssnArgNmCpyFlg	(boolean)
	* @param	sssnArgNmCpyFlg	(boolean)
	* @return	なし
	********************************************************************************
	*/
	public void initChildDBAccess(	AppDBAccess	dbAccss,
									boolean	sssnArgNmCpyFlg,
									boolean	scrnDtCpyFlg	) throws Exception
	{
		super.initChildDBAccess( dbAccss, sssnArgNmCpyFlg, scrnDtCpyFlg );
		dbAccss.notStUserInfoFlg	=	notStUserInfoFlg;
		dbAccss.user_namecd			=	new String( user_namecd );
		dbAccss.user_sosiki_grp		=	new String( user_sosiki_grp );
		dbAccss.user_syokui_grp		=	new String( user_syokui_grp );
		dbAccss.user_renban			=	new String( user_renban );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付コントロール当日日付の取得
	* @param	con	(Connection)
	* @return	なし
	********************************************************************************
	*/
	public void setCntrlDt(	Connection	con ) throws Exception
	{
		if ( Const.SW_ON.equals( Config.CNTRL_DT_FRM_DB ) ) {
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			try {
				String s = "";
				pstmt = con.prepareStatement( getCmnPreparedSQL( C_APP_SQL_000 ) );
				rs = pstmt.executeQuery();
				if ( rs.next() ) {
					s = TextUtility.cnvrtSpclChr( rs.getString( "TODAY" ) );
					s = DateTimeUtility.cvtDate0( s );
				} else {
					throw new Exception( CommonUtility.getExcptnMssg( AppMessage.getMsg( AppMessage.C_APP_MSG_ID_03 ) ) );
				}
				cntrlDt = s;
				CommonUtility.debugPrint( "日付コントロール:" + cntrlDt  );
				return;
			} finally {
				if ( rs != null ) { rs.close(); }
				if ( pstmt != null ) { pstmt.close(); }
			}
		} else {
			cntrlDt = DateTimeUtility.curr_Date();
			return;
		}
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
