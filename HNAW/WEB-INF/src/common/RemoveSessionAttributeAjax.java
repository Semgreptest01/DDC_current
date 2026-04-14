/**
*-------------------------------------------------------------------------------
* クラス名			RemoveSessionAttributeAjax.class
* システム名称		共通
* 名称				セッション変数削除
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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.CommonUtility;

public class RemoveSessionAttributeAjax extends HttpServlet
{
	private static final long	serialVersionUID = 1L;
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
		try {
			HttpSession session = request.getSession( true );
			String sssnArgNm = request.getParameter( "sssnArgNm" );
			if ( sssnArgNm != null && !"".equals( sssnArgNm ) ) {
				session.removeAttribute( sssnArgNm );
				CommonUtility.debugPrint( "セッション変数の削除:" + sssnArgNm );
			}
		} catch ( Throwable e ) {
			e.printStackTrace();
			if ( e instanceof Error ) {
				throw (Error)e;
			}				
		}
		return;
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
