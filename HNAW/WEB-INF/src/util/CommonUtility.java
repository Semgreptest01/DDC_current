/**
*-------------------------------------------------------------------------------
* クラス名			CommonUtility.class
* システム名称		共通
* 名称				ユーティリティ・共通クラス
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

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import common.Config;
import common.Const;

public class CommonUtility {
	/**
	********************************************************************************
	* DEBUG用メッセージ出力
	* @param	request	(HttpServletRequest)
	* @param	msgBx	(Vector)
	* @param   wkMsg	(String)
	* @return 	なし
	********************************************************************************
	*/
	public static void debugMsg(	HttpServletRequest	request,
									Vector				msgBx,
									String				wkMsg ) {
		if ( Const.SW_ON.equals( Config.DEBUG_SW ) ) {
			String d = DateTimeUtility.curr_Date();
			String s = DateTimeUtility.curr_Time();
			String r = request.getRemoteAddr();
			msgBx.add( d + " " + s + " " + r + " " + request.getServletPath() + " " + wkMsg );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* DEBUG用メッセージ出力
	* @param	msgBx	(Vector)
	* @param   wkMsg	(String)
	* @return 	なし
	********************************************************************************
	*/
	public static void debugMsg(	Vector	msgBx,
									String	wkMsg ) {
		if ( Const.SW_ON.equals( Config.DEBUG_SW ) ) {
			msgBx.add( wkMsg );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* デバッグメッセージ出力
	* @param	printData				(String)
	* @return	Not null:OK null:ERR	(String)
	********************************************************************************
	*/
	public static void debugPrint( String printData ) {
		try {
			if ( Const.SW_ON.equals( Config.DEBUG_SW ) ) {
				System.out.println( "[* DEBUG *] " + printData );
			}
		} catch ( Exception e ) {
			return;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* クラス名取得
	* @param	obj		(Object)
	* @param	smplFlg	(boolean)
	* @return	クラス名	(String)
	********************************************************************************
	*/
	public static String getClssNm(	Object	obj,	boolean	smplFlg	)
	{
		String clssNm = "";
		if ( obj != null ) {
			clssNm = obj.getClass().getName();
			if ( smplFlg ) {
				int i = clssNm.lastIndexOf( '.' );
				clssNm = ( i < 0 ) ? clssNm : clssNm.substring( i + 1 );
			}
		}
    	return clssNm;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* クラス名取得
	* @param	obj		(Object)
	* @return	クラス名	(String)
	********************************************************************************
	*/
	public static String getClssNm(	Object	obj	)
	{
    	return getClssNm( obj, true );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 例外用メッセージを取得
	* @param	fnctnNm			(String)
	* @param	mssg			(String)
	* @return	例外用メッセージ	(String)
	********************************************************************************
	*/
	public static String getExcptnMssg(	String	fnctnNm,	String	mssg	)
	{
		String str = "";
		if ( fnctnNm != null && !"".equals( fnctnNm ) ) {
			str += "[" + fnctnNm + "]:";
		}
		str += mssg;
		return str;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 例外用メッセージを取得
	* @param	mssg				(String)
	* @return	例外用メッセージ	(String)
	********************************************************************************
	*/
	public static String getExcptnMssg(	String	mssg	)
	{
		return getExcptnMssg( null, mssg );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 例外から表示用スタックトレースを取得
	* @param	e						(Exception)
	* @return	表示用スタックトレース	(String)
	********************************************************************************
	*/
	public static String getDsplyStckTrc(	Exception	e	)
	{
		StringBuffer buf = new StringBuffer();
		ByteArrayOutputStream baos = null;
		PrintWriter pw = null;
		try {
			baos = new ByteArrayOutputStream();
			pw = new PrintWriter( baos );
			e.printStackTrace( pw );
			pw.flush();
			
			boolean fndFlg = false;
			boolean appndFlg = false;
			StringTokenizer stckTrc = new StringTokenizer( baos.toString(), "\n" );
			for ( int i = 0; stckTrc.hasMoreTokens(); i++ ) {
				appndFlg = false;
				String lnStrng = stckTrc.nextToken();
				if ( i > 0 ) {
					for ( int j = 0; j < Config.PCKG_NMS.length; j++ ) {
						if ( lnStrng.indexOf( "at " + Config.PCKG_NMS[ j ] + '.' ) != -1 ) {
							fndFlg = true;
							appndFlg = true;
							break;
						}
					}
				}
				if ( !fndFlg || appndFlg ) {
					buf.append( lnStrng + '\n' );
				}
				if ( i == 0 && e instanceof SQLException ) {
					buf.append( "(SQL State:" + ( (SQLException)e ).getSQLState() + ")\n" );
				}
			}
		} finally {
			try {
				if ( baos != null ) { baos.close(); }
			} catch ( Exception ex ) {}
			if ( pw != null ) { pw.close(); }
		}
		return buf.toString();
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 例外から例外を発生させたメソッド名を取得
	* @param	e											(Exception)
	* @param	srchNmArry									(String[])
	* @param	lvl	0(パッケージ)・1(クラス)・2(メソッド)	(int)
	* @return	例外を発生させたメソッド名					(String)
	********************************************************************************
	*/
	public static String getErrMthdNm(	Exception	e,	String[]	srchNmArry,	int	lvl	)
	{
		String errMthdNm = "";
		ByteArrayOutputStream baos = null;
		PrintWriter pw = null;
		try {
			baos = new ByteArrayOutputStream();
			pw = new PrintWriter( baos );
			e.printStackTrace( pw );
			pw.flush();
			
			StringTokenizer stckTrc = new StringTokenizer( baos.toString(), "\n" );
			for ( int i = 0; stckTrc.hasMoreTokens(); i++ ) {
				String lnStrng = stckTrc.nextToken();
				if ( i > 0 ) {
					for ( int j = 0; j < srchNmArry.length; j++ ) {
						if ( lnStrng.indexOf( "at " + srchNmArry[ j ] ) != -1 ) {
							errMthdNm = lnStrng.substring( lnStrng.indexOf( "at " ) + 3, lnStrng.indexOf( "(" ) );
							for ( int k = 0; k < lvl; k++ ) {
								errMthdNm = errMthdNm.substring( errMthdNm.indexOf( "." ) + 1 );
							}
							break;
						}
					}
					if ( !"".equals( errMthdNm ) ) {
						break;
					}
				}
			}
		} finally {
			try {
				if ( baos != null ) { baos.close(); }
			} catch ( Exception ex ) {}
			if ( pw != null ) { pw.close(); }
		}
		return errMthdNm;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 例外から例外を発生させたメソッド名を取得
	* @param	e											(Exception)
	* @param	srchNm										(String)
	* @param	lvl	0(パッケージ)・1(クラス)・2(メソッド)	(int)
	* @return	例外を発生させたメソッド名					(String)
	********************************************************************************
	*/
	public static String getErrMthdNm(	Exception	e,	String	srchNm,	int	lvl	)
	{
		String[] tmpArry = { srchNm };
		return getErrMthdNm( e, tmpArry, lvl );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 例外から例外を発生させたメソッド名を取得
	* @param	e											(Exception)
	* @param	lvl	0(パッケージ)・1(クラス)・2(メソッド)	(int)
	* @return	例外を発生させたメソッド名					(String)
	********************************************************************************
	*/
	public static String getErrMthdNm(	Exception	e,	int	lvl	)
	{
		return getErrMthdNm( e, Config.PCKG_NMS, lvl );
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
