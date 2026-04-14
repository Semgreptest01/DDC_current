/**
*-------------------------------------------------------------------------------
* クラス名			MessageUtility.class
* システム名称		共通
* 名称				ユーティリティ・メッセージクラス
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

import java.util.HashMap;
import java.util.Vector;

import app.AppMessage;

public class MessageUtility {
	// Servlet
	/**
	 * セッション情報・読み込みエラーです。
	 */
	public static final String	SRVLT_MSG_ID_00	= "0";
	/**
	 * SnoopInf Error !
	 */
	public static final String	SRVLT_MSG_ID_01	= "1";
	/**
	 * 9999 システム障害です。
	 */
	public static final String	SRVLT_MSG_ID_02	= "2";
	// DBAccess
	/**
	 * 登録しました。
	 */
	public static final String   DBA_MSG_ID_01	= "3";
	/**
	 * 変更しました。
	 */
	public static final String   DBA_MSG_ID_02	= "4";
	/**
	 * 削除しました。
	 */
	public static final String   DBA_MSG_ID_03	= "5";
	/**
	 * データが存在しません。
	 */
	public static final String   DBA_MSG_ID_04	= "6";
	/**
	 * 明細行の最大行数を超えました。
	 */
	public static final String   DBA_MSG_ID_05	= "7";
	/**
	 * 他ユーザーにより変更されました。
	 */
	public static final String   DBA_MSG_ID_06	= "8";
	/**
	 * 設定ファイルの連番が異常です。:[&STR0]
	 */
	public static final String   DBA_MSG_ID_07	= "9";
	
	/**
	 * メッセージMap
	 */
	private static final HashMap MSG_MAP = new HashMap();
	static {
		// Servlet
		setMsg( "0", "セッション情報・読み込みエラーです。" );
		setMsg( "1", "SnoopInf Error !" );
		setMsg( "2", "9999 システム障害です。" );
		// DBAccess
		setMsg( "3", "登録しました。" );
		setMsg( "4", "変更しました。" );
		setMsg( "5", "削除しました。" );
		setMsg( "6", "データが存在しません。" );
		setMsg( "7", "明細行の最大行数を超えました。" );
		setMsg( "8", "他ユーザーにより変更されました。" );
		setMsg( "9", "設定ファイルの連番が異常です。:[&STR0]" );
		AppMessage.loadFlg = true;
	}
	
	/**
	********************************************************************************
	* メッセージ設定
	* @param	msgId	(String)
	* @param	msg		(String)
	* @return	なし
	********************************************************************************
	*/
	protected static void setMsg(	String 	msgId,	String 	msg ) {
		MSG_MAP.put( msgId, msg );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* メッセージ出力
	* @param	msgId		(String)
	* @return	メッセージ	(String)
	********************************************************************************
	*/
	public static String getMsg(	String 	msgId ) {
		try {
			String msg = "";
			if ( MSG_MAP.containsKey( msgId ) ) {
				msg = (String)MSG_MAP.get( msgId );
			}
			return msg;
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* メッセージ出力
	* @param	msgId		(String)
	* @param	argmnts		(String[])
	* @return	メッセージ	(String)
	********************************************************************************
	*/
	public static String getMsg(	String msgId,	String[]	argmnts )
	{
		try {
			String msg = getMsg( msgId );
			msg = TextUtility.rplcStrg( msg, argmnts );
			return msg;
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* メッセージ出力
	* @param	msgId		(String)
	* @param	argmnt		(String)
	* @return	メッセージ	(String)
	********************************************************************************
	*/
	public static String getMsg(	String msgId,	String	argmnt )
	{
		String[] tmpArry = { argmnt };
		return getMsg( msgId, tmpArry );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* メッセージ出力
	* @param	msgBx	(Vector)
	* @param	msgId	(String)
	* @return	なし
	********************************************************************************
	*/
	public static void msgOut(	Vector	msgBx,	String msgId )
	{
		msgBx.add( getMsg( msgId ) );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* メッセージ出力
	* @param	msgBx	(Vector)
	* @param	msgId	(String)
	* @param	argmnts	(String[])
	* @return	なし
	********************************************************************************
	*/
	public static void msgOut(	Vector	msgBx,	String msgId,	String[]	argmnts )
	{
		msgBx.add( getMsg( msgId, argmnts ) );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* メッセージ出力
	* @param	msgBx	(Vector)
	* @param	msgId	(String)
	* @param	argmnt	(String)
	* @return	なし
	********************************************************************************
	*/
	public static void msgOut(	Vector	msgBx,	String msgId,	String	argmnt )
	{
		msgBx.add( getMsg( msgId, argmnt ) );
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
