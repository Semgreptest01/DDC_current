/**
*-------------------------------------------------------------------------------
* クラス名			Hnawlj0010Ajax.class
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

import common.DBAccess;

public class Hnawlj0010Ajax extends common.ServletAjax
{
	private static final long	serialVersionUID = 1L;
	/**
	 * DBAccess名
	 */
	private static final	String	DBACCESS_NM		= "Hnawlj0010DBAccess";
	/**
	 * Session変数名
	 */
	private static final	String	SSSN_ARG_NM		= DBACCESS_NM;
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
	* ビジネスロジック
	* @param	request			(HttpServletRequest)
	* @param	response		(HttpServletResponse)
	* @param	session			(HttpSession)
	* @param	con				(Connection)
	* @return	responseText	(String)
	********************************************************************************
	*/
	protected String businessProc(	HttpServletRequest	request,
									HttpServletResponse	response,
									HttpSession			session,
									Connection			con	) throws Exception
	{
		String rslt = null;
		Hnawlj0010DBAccess dba = (Hnawlj0010DBAccess)getDBAccess( request, session );
		// ビジネスロジック前処理
		dba.beforeBusinessProc( request, session, con );
		
		// 画面固有の処理
		// HTTP-Paramの取得
		dba.setRqstPrmtr( request );
		// CSV件数取得
		rslt = String.valueOf( dba.getCsvCnt( con ) );
		
		// ビジネスロジック後処理
		dba.afterBusinessProc();
		return rslt;
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************

