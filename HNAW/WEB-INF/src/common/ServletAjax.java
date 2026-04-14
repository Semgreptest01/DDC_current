/**
*-------------------------------------------------------------------------------
* クラス名			ServletAjax.class
* システム名称		共通
* 名称				サーブレットAjax スーパークラス
* 会社名or所属名	富士ソフトＤＩＳ
* 作成日			2006/11/08 00:00:00
* @author			Hirata
* @since			1.0
* @version			1.0
* *** 修正履歴 ***
* No.  Date 		Author			Description
*-------------------------------------------------------------------------------
*/
package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.CommonUtility;
import util.MessageUtility;

public abstract class ServletAjax extends HttpServlet
{
	private static final long	serialVersionUID = 1L;
	/**
	 * エラー・例外発生時にRollBack処理をするかどうか
	 */
	public boolean			dbRllBckFlg				= true;
	/**
	********************************************************************************
	* Servlet初期処理
	* @param	config	(ServletConfig)
	* @return	なし
	********************************************************************************
	*/
	public void init(	ServletConfig	config	) throws ServletException
	{
		super.init( config );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* Servletサービス
	* @param	request		(HttpServletRequest)
	* @param	response	(HttpServletResponse)
	* @return	なし
	********************************************************************************
	*/
	public void service(	HttpServletRequest	request,
							HttpServletResponse	response	) throws ServletException, IOException
	{
		response.setContentType( "text/xml; charset=UTF-8" );
		request.setCharacterEncoding( "UTF-8" );
		
		HttpSession		session		=	request.getSession( true );
		Connection		con			=	null;
		PrintWriter		prntWrtr	=	null;
		String			rslt		=	null;
		try {
			prntWrtr = response.getWriter();
			// DB connection
			con = Dbcom.dbConnect( Config.DB_USER, Config.DB_PASS );
			// ビジネス・ロジック
			rslt = businessProc( request, response, session, con );
			prntWrtr.print( rslt );
			Dbcom.dbCommit( con );
		} catch ( Throwable e ) {
			e.printStackTrace();
			if ( dbRllBckFlg ) {
				try {
					Dbcom.dbRollback( con );
				} catch ( Exception ex ) {}
			}
			if ( e instanceof Error ) {
				throw (Error)e;
			}
		} finally {
			Dbcom.dbDisconnect( con );
		}
		return;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* DBAccessクラスのセッション生成
	* @param	request			(HttpServletRequest)
	* @param	session			(HttpSession)
	* @return	DBAccessクラス	(DBAccess)
	********************************************************************************
	*/
	protected abstract DBAccess getDBAccess(HttpServletRequest request, HttpSession session) throws Exception;
	/**
	********************************************************************************
	* DBAccessクラスのセッション生成
	* @param	request			(HttpServletRequest)
	* @param	session			(HttpSession)
	* @param	sssnArgNm		(String)
	* @return	DBAccessクラス	(DBAccess)
	********************************************************************************
	*/
	protected DBAccess getDBAccess(HttpServletRequest request, HttpSession session, String sssnArgNm) throws Exception
	{
		DBAccess dba = null;
		dba = (DBAccess)session.getAttribute( sssnArgNm );
		if ( dba == null ) {
			throw new Exception( CommonUtility.getExcptnMssg( MessageUtility.getMsg( MessageUtility.SRVLT_MSG_ID_00 ) ) );
		}
		dba.clearMssg();
		return dba;
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
	protected abstract String businessProc(	HttpServletRequest	request,
												HttpServletResponse	response,
												HttpSession			session,
												Connection			con	) throws Exception;
}		// *********************** End of Class  ***********************************
