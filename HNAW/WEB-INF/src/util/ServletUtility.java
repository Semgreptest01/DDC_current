/**
*-------------------------------------------------------------------------------
* クラス名			ServletUtility.class
* システム名称		共通
* 名称				ユーティリティ・サーブレットクラス
* 会社名or所属名	株式会社ヴィクサス
* 作成日			2006/11/08 00:00:00
* @author			Y.Takabayashi
* @since			1.0
* @version			1.0
* *** 修正履歴 ***
* No. Date			Author		Description
*-------------------------------------------------------------------------------
*/
package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Config;
import common.Const;

public class ServletUtility {
	/**
	********************************************************************************
	* 認証情報のSET
	* @param	request				(HttpServletRequest)
	* @param	session				(HttpSession)            
	* @param	msgBx				(Vector)                 
	* @return	true:OK false:ERR	(boolean)
	********************************************************************************
	*/
	public static void setAthntctnInf(	HttpServletRequest	request,
											HttpSession 		session,
											Vector				msgBx	)
	{
		String user_id	= (String)request.getParameter( Const.PRMTR_NM_USER_ID );
		if ( user_id == null ) {
			user_id = (String)session.getAttribute( Const.PRMTR_NM_USER_ID );
		} else {
			// セッション情報の出力・ユーザーID
			session.setAttribute( Const.PRMTR_NM_USER_ID, user_id );
		}
		CommonUtility.debugMsg( msgBx, "***DEBUG*** ユーザーID  　　　：" + user_id );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 実IPファイルの読み込み
	* @param	idx															(int)            
	* @return	実IP-Addressのidx行目の文字列(取得できなかった場合は、"")	(String)
	********************************************************************************
	*/
	private static String readActualIPFl(	int idx	)
	{
		String Result = "";
		try {
			FileReader objFR = new FileReader( Config.ACTUAL_IP_FILE );
			BufferedReader objBR = new BufferedReader(objFR);
			int i = 0;
			while ( objBR.ready() ) {
				if ( i == idx ) {
					Result = objBR.readLine();
				}else{
					objBR.readLine();
				}
				i++;
			}
			objBR.close();
		} catch ( Exception e ) {
			return Result;
		}
		return Result;

	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 実IPファイルの取得(1Recordのみ)
	* @return	実IP-Address(取得できなかった場合は、"")	(String)
	********************************************************************************
	*/
	public static String getActualIP()
	{
		return readActualIPFl( Const.ACTUAL_IP_FILE_INDEX_ACTUAL_IP );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* Cookie LoadBalance用のServerID値を取得
	* @return	ServerID値	(String)
	********************************************************************************
	*/
	public static String getServerID() throws Exception
	{
		String tmpIp = readActualIPFl( Const.ACTUAL_IP_FILE_INDEX_SRVR_ID );
		return !"".equals( tmpIp ) ? tmpIp : Const.DFLT_SRVR_ID;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* Cookie LoadBalance処理
	* @param	response	(HttpServletResponse)
	* @return	なし
	********************************************************************************
	*/
	public static void ckLdBlnc(	HttpServletResponse	response	) throws Exception
	{
		if ( Const.SW_ON.equals( Config.COOKIE_LOADB_SW ) ) {
			Cookie cookie = new Cookie( "ServerID", getServerID() );
			cookie.setPath( "/" );
			response.addCookie( cookie );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* アクティブ・スレッド数の取得
	* @return	9999(ActiveThread数):9999(自身のThread番号)	(String)
	********************************************************************************
	*/
	public static String getActiveThread() throws Exception
	{
		String Result = "";
		Thread		current	=	Thread.currentThread();
		ThreadGroup	thgroup	=	current.getThreadGroup();
		int			count	=	thgroup.activeCount();
		Thread[]	threads	=	new Thread[ count ];
		String[]	items	=	new String[ count ];
		String		s		=	"";
		// Threadオブジェクトのリストを配列に格納
		int num = thgroup.enumerate( threads, true );
		// Thread名の取り出し
		for ( int i = 0; i < num; i++ ) {
			if ( current.equals( threads[ i ] ) ) {
				items[ i ]	= threads[ i ].getName() + "*";
				s	= TextUtility.fillZero( Integer.toString( i ), 4 );
			}else{
				items[ i ]	= threads[ i ].getName();
			}
		}
		Result = TextUtility.fillZero( Integer.toString( count ), 4 ) + "(" + s + ")";
		return Result;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* Cookie情報の取得（コンソール表示）
	* @param	req	(HttpServletRequest)			
	* @return	なし
	********************************************************************************
	*/
	public static void getCookieInf(	HttpServletRequest	req	)
	{
		if ( Const.SW_ON.equals( Config.COOKIE_SW ) ) {
			String	remote_addr = req.getRemoteAddr();
			//String	remote_user = req.getRemoteUser();
			String	remote_host = req.getRemoteHost();

			Cookie cookies[] = req.getCookies();
			if ( cookies != null ) {
				CommonUtility.debugPrint( "***** Cookie 内容 ***** Request元:" + remote_addr + "(" + remote_host + ")" );
				for ( int i = 0; i < cookies.length; i++ ) {
					CommonUtility.debugPrint( "* Name   :" + cookies[ i ].getName() );
					CommonUtility.debugPrint( "* Value  :" + cookies[ i ].getValue() );
					CommonUtility.debugPrint( "* Comment:" + cookies[ i ].getComment() );
					CommonUtility.debugPrint( "* Domain :" + cookies[ i ].getDomain() );
					CommonUtility.debugPrint( "* Path   :" + cookies[ i ].getPath() );
					CommonUtility.debugPrint( "* MaxAge :" + cookies[ i ].getMaxAge() );
					CommonUtility.debugPrint( "* Version:" + cookies[ i ].getVersion() );
				}
				CommonUtility.debugPrint( "******************************************************************************" );
			} else {
				CommonUtility.debugPrint( "***** Cookie なし ***** Request元:" + remote_addr + "(" + remote_host + ")" );
			}
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* セッションID表示
	* @param	session	(HttpSession)
	* @param	msgBx	(Vector)
	* @return	なし
	********************************************************************************
	*/
	public static void dsplySssnId(	HttpSession	session,
										Vector		msgBx	)
	{
		if ( Const.SW_ON.equals( Config.SESSION_ID_SW ) ) {
			String mssg = "";
			if ( Const.SW_ON.equals( Config.READ_IP_FILE_SW ) ) {
				mssg += "(" + getActualIP() + "号機) ";
			}
			mssg += DateTimeUtility.curr_Time();
			if ( session.isNew() ) {
				mssg = "新しいセッションを開始しました。" + mssg;
			} else {
				mssg = "旧セッションを引継ぎました。" + mssg;
			}
			msgBx.add( mssg );
			msgBx.add( "session_id:" + session.getId() + " SessionTimeout:" + Integer.toString( session.getMaxInactiveInterval() ) );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* SNOOP情報の取得
	* @param	req		(HttpServletRequest)
	* @param	msgBx	(Vector)
	* @return	なし
	********************************************************************************
	*/
	public static void getSnoopInf(	HttpServletRequest	req,
									 	Vector				msgBx	) throws Exception
	{
		if ( Const.SW_ON.equals( Config.SNOOP_SW ) ) {
			String User_Agent = req.getHeader("User-Agent");
			msgBx.add( "************ SNOOP情報 Start ***************************" );
			if ( Const.SW_ON.equals( Config.READ_IP_FILE_SW ) ) {
				msgBx.add(	"Actual No.      :" + getActualIP() );
			}
			msgBx.add(	"Request Method  :" + req.getMethod() );
			msgBx.add(	"Request URI     :" + req.getRequestURI() );
			msgBx.add(	"Request Protocol:" + req.getProtocol() );
			msgBx.add(	"Servlet Path    :" + req.getServletPath() );
			msgBx.add(	"Path Infomation :" + req.getPathInfo() );
			msgBx.add(	"Path Translated :" + req.getPathTranslated() );
			msgBx.add(	"Query String    :" + req.getQueryString() );
			msgBx.add(	"Content Length  :" + req.getContentLength() );
			msgBx.add(	"Content Type    :" + req.getContentType() );
			msgBx.add(	"Server Name     :" + req.getServerName() );
			msgBx.add(	"Server Port     :" + req.getServerPort() );
			msgBx.add(	"Remote User     :" + req.getRemoteUser() );
			msgBx.add(	"Remote Address  :" + req.getRemoteAddr() );
			msgBx.add(	"Remote Host     :" + req.getRemoteHost() );
			msgBx.add(	"Author Scheme   :" + req.getAuthType() );
			msgBx.add(	"Locale          :" + req.getLocale() );
			if ( req.isRequestedSessionIdFromCookie() ) {
				msgBx.add(	"Session ID From Cookie:有効" );
			}else{
				msgBx.add(	"Session ID From Cookie:無効" );
			}
			if ( req.isRequestedSessionIdFromURL() ) {
				msgBx.add(	"Session ID From URL   :有効" );
			}else{
				msgBx.add(	"Session ID From URL   :無効" );
			}
			if ( req.isRequestedSessionIdValid() ) {
				msgBx.add(	"Session ID Valid      :有効" );
			}else{
				msgBx.add(	"Session ID Valid      :無効" );
			}
			msgBx.add(	"User Agent      :" + User_Agent );
			Cookie	cookies[]		=	req.getCookies();
			if ( cookies != null ) {
				msgBx.add( "***** Cookie Start *****" );
				for ( int i = 0; i < cookies.length; i++ ) {
					msgBx.add( "* Name   :" + cookies[ i ].getName() );
					msgBx.add( "* Value  :" + cookies[ i ].getValue() );
					msgBx.add( "* Comment:" + cookies[ i ].getComment() );
					msgBx.add( "* Domain :" + cookies[ i ].getDomain() );
					msgBx.add( "* Path   :" + cookies[ i ].getPath() );
					msgBx.add( "* MaxAge :" + cookies[ i ].getMaxAge() );
					msgBx.add( "* Version:" + cookies[ i ].getVersion() );
					msgBx.add( "*-----------------------" );
				}
				msgBx.add( "***** Cookie End   *****" );
			} else {
				msgBx.add( "***** Cookie なし  *****" );
			}
			msgBx.add( "************ SNOOP情報 End   ***************************" );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 各種情報表示
	* @param	request	(HttpServletRequest)
	* @param	session	(HttpSession)
	* @param	msgBx	(Vector)
	* @return	なし
	********************************************************************************
	*/
	public static void dsplyVrsInf(	HttpServletRequest	request,
										HttpSession			session,
										Vector				msgBx	)
	{
		getCookieInf( request );
		try {
			dsplySssnId( session, msgBx );
			getSnoopInf( request, msgBx );
		} catch ( Exception ex ) {
			MessageUtility.msgOut( msgBx, MessageUtility.SRVLT_MSG_ID_01 );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* キャンセルSWの取得
	* @param	request			(HttpServletRequest)
	* @return	キャンセルSW	(String)
	********************************************************************************
	*/
	public static String getCancelSW(	HttpServletRequest	request	) throws Exception
	{
		return (String)request.getParameter( "cancelSw" );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* キャンセルかどうか
	* @param	request				(HttpServletRequest)
	* @return	true:YES false:NO	(boolean)
	********************************************************************************
	*/
	public static boolean isCancel(	HttpServletRequest	request	) throws Exception
	{
		return Const.SW_ON.equals( ServletUtility.getCancelSW( request ) );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 環境がOracleiASかどうか
	* @return	true:YES false:NO	(boolean)
	********************************************************************************
	*/
	public static boolean isOrclIas()
	{
		return ( Const.CON_SW_OAS_JNDI.equals( Config.CON_SW ) || Const.CON_SW_OAS_DRCT.equals( Config.CON_SW ) );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 環境がOracleiASかどうか
	* @param	request	(HttpServletRequest)
	* @return	なし
	********************************************************************************
	*/
	public static void setChrctrEncdngFrOas(	HttpServletRequest	request	) throws Exception
	{
		if ( isOrclIas() ) {
			request.setCharacterEncoding( Config.CHAR_SET );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* セッション変数開放＆ガベージコレクション
	* @param	session			(HttpSession)
	* @param	session_name	(String)
	* @param	msgBx			(Vector)
	* @return	なし
	********************************************************************************
	*/
	public static void removeAtt(	HttpSession	session,
									String 		session_name,
									Vector		msgBx	)
	{
		session.removeAttribute( session_name );
		String mssg = "セッション変数の削除:" + session_name;
		CommonUtility.debugPrint( mssg );
		CommonUtility.debugMsg( msgBx, mssg );
		// ガベージコレクションの実行
		System.gc();
		System.runFinalization();
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* パフォーマンス計測・終了処理
	* @param	ServletName					(String)
	* @param	msgBx						(Vector)
	* @param	tStart						(long)
	* @param	mStart						(String)
	* @return	パフォーマンス計測データ文字列	(String)
	********************************************************************************
	*/
	public static String performEndProc(	String	ServletName,
											Vector	msgBx,
											long	tStart,
											String	mStart	) throws Exception
	{
		// 起源からの時間をミリ秒単位で返す。
		long tEnd = System.currentTimeMillis();
		// 現在のメモリを取得する。
		String mEnd = getCrrntUsMmry();
		String s = ServletName + ":" + ( tEnd - tStart ) + "msec,Memory:" + mStart + "->" + mEnd + ":" + ServletUtility.getActiveThread();
		// パフォーマンス計測？
		if ( Const.SW_ON.equals( Config.PERFORMANCE_SW ) ) {
			msgBx.add( s );
		}
		return s;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* パフォーマンス計測・終了処理
	* @param	ServletName					(String)
	* @param	msgBx						(Vector)
	* @param	tStart						(long)
	* @param	mStart						(String)
	* @return	パフォーマンス計測データ文字列	(String)
	********************************************************************************
	*/
	public static String getCrrntUsMmry()
	{
		String crrntUsMmry = TextUtility.fillZero( Long.toString( Runtime.getRuntime().totalMemory() ), 10 ) + "("
			+ TextUtility.fillZero( Long.toString( Runtime.getRuntime().freeMemory() ), 10 ) + ")";
		return crrntUsMmry;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* サーブレット用のsetContentType処理
	* @param	response	(HttpServletResponse)
	* @return	なし
	********************************************************************************
	*/
	public static void setCntntTypFrSrvlt(	HttpServletResponse	response	)
	{
		response.setContentType( "text/html; charset=" + Config.CHAR_SET );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* JSP起動
	* @param	request		(HttpServletRequest)
	* @param	response	(HttpServletResponse)
	* @param	session		(HttpSession)
	* @param	srvltCntxt	(ServletContext)
	* @param	prcRslt		(int)
	* @param	bfrJsp		(String)
	* @param	tmJsp		(String)
	* @param	nrmlJsp		(String)
	* @return	なし
	********************************************************************************
	*/
	public static void incldNxtJsp(	HttpServletRequest	request,
										HttpServletResponse	response,
										HttpSession			session,
										ServletContext		srvltCntxt,
										int					prcRslt,
										String				bfrJsp,
										String				tmJsp,
										String				nrmlJsp	) throws Exception
	{
		if ( prcRslt != Const.SRVLT_RSLT_NO_SCRN ) {
			setCntntTypFrSrvlt( response );
			String nxtJsp = Config.JSP_DIR_PATH + "/";
			if ( prcRslt == Const.SRVLT_RSLT_BCK ) {
				nxtJsp += bfrJsp;
			} else if ( prcRslt == Const.SRVLT_RSLT_TIME_ERR ) {
				nxtJsp += tmJsp;
			} else {
				nxtJsp += nrmlJsp;
			}
			RequestDispatcher dispatcher = srvltCntxt.getRequestDispatcher( nxtJsp );
			dispatcher.include( request, response );
		}
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
