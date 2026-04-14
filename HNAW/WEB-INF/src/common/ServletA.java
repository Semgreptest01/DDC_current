/**
*-------------------------------------------------------------------------------
* クラス名			ServletA.class
* システム名称		共通
* 名称				サーブレットＡ スーパークラス
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
import java.io.PrintWriter;
import java.sql.Connection;
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

public abstract class ServletA extends HttpServlet
{
	/**
	 * 認証チェックするか
	 */
	private static final boolean IS_CHK_ATHNTCTN = true;
	/**
	 * サービス時間チェック画面JSP名
	 */
	public static final	String	TIME_JSP		= Const.CMN_TIME_JSP;
	/**
	 * エラー画面JSP名
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
		// パフォーマンス計測用変数
		long			tStart;
		String			mStart;
		
		HttpSession		session = request.getSession( true );
		Connection		con		= null;
		DBAccess		tmpDba	= null;
		// ビジネス・ロジック処理結果
		int				prcRslt	= 0;
		try {
			// Cookieロードバランス処理
			ServletUtility.ckLdBlnc( response );
			// OASのEncoding対応
			ServletUtility.setChrctrEncdngFrOas( request );
			
			// 各種情報表示
			ServletUtility.dsplyVrsInf( request, session, msgBx );
			
			// セッションチェック
			if ( session.isNew() && Const.SW_ON.equals( Config.SESSION_TIME_SW ) ) {
				// session-timeoutの設定
				session.setMaxInactiveInterval( Config.SESSION_TIMEOUT );
			}
			
			CommonUtility.debugMsg( request, msgBx, "*START*" );
			// 起源からの時間をミリ秒単位で返す。
			tStart = System.currentTimeMillis();
			// 現在のメモリを取得する。
			mStart = ServletUtility.getCrrntUsMmry();
			
			tmpDba = getDBAccess( request, session );
			if ( isChkAthntctn() ) {
				// 認証チェック
				if ( !tmpDba.athntctnChk( session ) ) {
					throw new Exception( CommonUtility.getExcptnMssg( tmpDba.dbaMsg ) );
				}
			}
			
			// DB connection
			con = Dbcom.dbConnect( Config.DB_USER, Config.DB_PASS );
			// ビジネス・ロジック
			prcRslt = businessProc( request, response, session, con, tmpDba );
			msgBx.addAll( tmpDba.dbaMsgBx );
			if ( isChkAthntctn() ) {
				if ( !"".equals( tmpDba.dbaMsg ) ) {
					msgBx.add( tmpDba.dbaMsg );
				}
			}
			tmpDba.clearMssg();
			
			// パフォーマンス計測・終了処理
			ServletUtility.performEndProc( getSRVLT_NM(), msgBx, tStart, mStart );
			CommonUtility.debugMsg( request, msgBx, "* END *" );
			
			Dbcom.dbCommit( con );
			
			// JSP 起動
			ServletUtility.incldNxtJsp( request, response, session, getServletContext(), prcRslt, getBFR_JSP( request, session ), getTIME_JSP(), getNRML_JSP( request, session ) );
			return;
		} catch ( Throwable e ) {
			e.printStackTrace();
			if ( tmpDba == null || tmpDba.dbRllBckOnErrFlg ) {
				try {
					Dbcom.dbRollback( con );
				} catch ( Exception ex ) {}
			}
			if ( e instanceof Error ) {
				throw (Error)e;
			} else {
				if ( tmpDba != null ) {
					msgBx.addAll( tmpDba.dbaMsgBx );
					tmpDba.clearMssg();
				}
				CommonUtility.debugMsg( request, msgBx, "* ERR *" );
				MessageUtility.msgOut( msgBx, MessageUtility.SRVLT_MSG_ID_02 );
				if ( e instanceof Exception ) {
					msgBx.add( CommonUtility.getDsplyStckTrc( (Exception)e ) );
				}
				if ( tmpDba == null || !tmpDba.stDwnldCntntTypFlg ) {
					ServletUtility.setCntntTypFrSrvlt( response );
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher( Config.JSP_DIR_PATH + "/" + getERR_JSP() );
					// JSP 起動
					dispatcher.include( request, response );
				} else {
					PrintWriter prntWrtr = response.getWriter();
					for ( int i = 0; i < msgBx.size(); i++ ) {
						prntWrtr.println( msgBx.get( i ) );
					}
					prntWrtr.flush();
				}
				return;	
			}
		} finally {
			Dbcom.dbDisconnect( con );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 認証チェックするか
	* @return	IS_CHK_ATHNTCTN	(boolean)
	********************************************************************************
	*/
	protected boolean isChkAthntctn()
	{
		return IS_CHK_ATHNTCTN;
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
		return dba;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* TIME_JSPの取得
	* @return	TIME_JSP	(String)
	********************************************************************************
	*/
	protected String getTIME_JSP()
	{
		return TIME_JSP;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ERR_JSPの取得
	* @return	ERR_JSP	(String)
	********************************************************************************
	*/
	protected String getERR_JSP()
	{
		return ERR_JSP;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* SRVLT_NMの取得
	* @return	SRVLT_NM	(String)
	********************************************************************************
	*/
	protected abstract String getSRVLT_NM();
	/**
	********************************************************************************
	* NRML_JSPの取得
	* @param	request		(HttpServletRequest)
	* @param	session		(HttpSession)
	* @return	NRML_JSP	(String)
	********************************************************************************
	*/
	protected abstract String getNRML_JSP(	HttpServletRequest	request,	HttpSession	session	) throws Exception;
	/**
	********************************************************************************
	* BFR_JSPの取得
	* @param	request	(HttpServletRequest)
	* @param	session	(HttpSession)
	* @return	BFR_JSP	(String)
	********************************************************************************
	*/
	protected abstract String getBFR_JSP(	HttpServletRequest	request,	HttpSession	session	) throws Exception;
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
	protected abstract int businessProc(	HttpServletRequest	request,
											HttpServletResponse	response,
											HttpSession			session,
											Connection			con,
											DBAccess			argmntDba	) throws Exception;
}		// *********************** End of Class  ***********************************
