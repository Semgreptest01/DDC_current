/**
*-------------------------------------------------------------------------------
* クラス名			RemoveSessionAttribute.class
* システム名称		共通
* 名称				セッション変数削除
* 会社名or所属名	富士ソフトＤＩＳ
* 作成日			2006/05/25 00:00:00
* @author			Y.Takabayashi
* @since			1.0
* @version			1.0
* *** 修正履歴 ***
* No.  Date 		Author			Description
*-------------------------------------------------------------------------------
*/
package common;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.CommonUtility;
import util.MessageUtility;
import util.ServletUtility;

public class RemoveSessionAttribute extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	/**
	 * 通常JSP名
	 */
	public static final	String	NRML_JSP		= "RemoveSessionAttributeJ.jsp";
	/**
	 * 共通・エラー画面
	 */
	public static final	String	ERR_JSP			= Const.CMN_ERR_JSP;
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
		// 画面Msg変数
		final Vector	msgBx	= new Vector();
		request.setAttribute( "msgBx", msgBx );
		try {
			ServletUtility.setCntntTypFrSrvlt( response );
			
			HttpSession session = request.getSession( true );
			String sssnArgNm = request.getParameter( "sssnArgNm" );
			if ( sssnArgNm != null && !"".equals( sssnArgNm ) ) {
				session.removeAttribute( sssnArgNm );
				msgBx.add( "セッション変数の削除:" + sssnArgNm );
				CommonUtility.debugPrint( (String)msgBx.lastElement() );
			}
			
			// JSP 起動
			ServletUtility.incldNxtJsp( request, response, session, getServletContext(), Const.SRVLT_RSLT_NRML, null, null, NRML_JSP );
			return;
		} catch ( Throwable e ) {
			e.printStackTrace();
			if ( e instanceof Error ) {
				throw (Error)e;
			} else {
				CommonUtility.debugMsg( request, msgBx, "* ERR *" );
				MessageUtility.msgOut( msgBx, MessageUtility.SRVLT_MSG_ID_02 );
				if ( e instanceof Exception ) {
					msgBx.add( CommonUtility.getDsplyStckTrc( (Exception)e ) );
				}
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher( Config.JSP_DIR_PATH + "/" + ERR_JSP );
				// JSP 起動
				dispatcher.include( request, response );
				return;	
			}
		}
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
