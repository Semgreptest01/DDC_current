/**
*-------------------------------------------------------------------------------
* クラス名			Hnawlj0010d
* システム名称		計画終了対象商品案内
* 名称				HNAW0010 計画終了対象商品ダウンロード
* 会社名or所属名	株式会社ヴィクサス
* 作成日			2010/01/18 00:00:00
* @author			Hirata
* @since			1.0
* @version			1.0
* *** 修正履歴 ***
* No.  Date 		Author			Description
*-------------------------------------------------------------------------------
*/
package app;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Const;
import common.DBAccess;

public class Hnawlj0010d extends common.ServletA
{
	private static final long	serialVersionUID = 1L;
	/**
	 * サーブレット名
	 */
	private static final	String	SRVLT_NM		= "Hnawlj0010d";
	/**
	 * DBAccess名
	 */
	private static final	String	DBACCESS_NM		= "Hnawlj0010DBAccess";
	/**
	 * Session変数名
	 */
	private static final	String	SSSN_ARG_NM		= DBACCESS_NM;
	/**
	 * 通常JSP名
	 */
	private static final	String	NRML_JSP		= "Hnawlj0010bJ.jsp";
	/**
	 * 前JSP名
	 */
	private static final	String	BFR_JSP			= "Hnawlj0010bJ.jsp";
	/**
	********************************************************************************
	* DBAccessクラスのセッション生成
	* @param	request			(HttpServletRequest)
	* @param	session			(HttpSession)
	* @return	DBAccessクラス	(DBAccess)
	********************************************************************************
	*/
	protected DBAccess getDBAccess(	HttpServletRequest	request,
										HttpSession			session	) throws Exception
	{
		return getDBAccess( request, session, SSSN_ARG_NM );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* SRVLT_NMの取得
	* @return	SRVLT_NM	(String)
	********************************************************************************
	*/
	protected String getSRVLT_NM()
	{
		return SRVLT_NM;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* NRML_JSPの取得
	* @param	request		(HttpServletRequest)
	* @param	session		(HttpSession)
	* @return	NRML_JSP	(String)
	********************************************************************************
	*/
	protected String getNRML_JSP(	HttpServletRequest	request,	HttpSession	session	) throws Exception
	{
		return NRML_JSP;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* BFR_JSPの取得
	* @param	request	(HttpServletRequest)
	* @param	session	(HttpSession)
	* @return	BFR_JSP	(String)
	********************************************************************************
	*/
	protected String getBFR_JSP(	HttpServletRequest	request,	HttpSession	session	) throws Exception
	{
		return BFR_JSP;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ビジネスロジック
	* @param	request		(HttpServletRequest)
	* @param	response	(HttpServletResponse)
	* @param	session		(HttpSession)
	* @param	con			(Connection)
	* @param	argmntDba	(DBAccess)
	* @return	実行結果	(int)<br>
	* ※ リターン値が<br>
	* -1:BFR_JSPへDispatchする   -2:Dispatchしない<br>
	* -3:サービス時間チェック　以外:NRML_JSPへDispatchする
	********************************************************************************
	*/
	protected int businessProc(	HttpServletRequest	request,
									HttpServletResponse	response,
									HttpSession			session,
									Connection			con,
									DBAccess			argmntDba	) throws Exception
	{
		int rslt = Const.SRVLT_RSLT_NO_SCRN;
		Hnawlj0010DBAccess dba = (Hnawlj0010DBAccess)argmntDba;
		// サービス時間チェック
		if ( !dba.srvcTmChk( request, con ) ) {
			return Const.SRVLT_RSLT_TIME_ERR;
		}
		// ビジネスロジック前処理
		dba.beforeBusinessProc( request, session, con );
		
		// 画面固有の処理
		// HTTP-Paramの取得
		dba.setRqstPrmtr( request );
		dba.downloadCsv( response, con );
		
		// ビジネスロジック後処理
		dba.afterBusinessProc();
		return rslt;
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
