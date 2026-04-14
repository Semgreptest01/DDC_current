/**
*-------------------------------------------------------------------------------
* クラス名			AppMessage.class
* システム名称		共通
* 名称				共通メッセージクラス
* 会社名or所属名	株式会社ヴィクサス
* 作成日			2006/11/08 00:00:00
* @author			Y.Takabayashi
* @since			1.0
* @version			1.0
* *** 修正履歴 ***
* No. Date			Author		Description
*-------------------------------------------------------------------------------
*/
package app;
import util.MessageUtility;

public class AppMessage extends MessageUtility {
	// AppDBAccess
	/**
	 * 認証チェックでエラーになりました。
	 */
	public static final String   C_APP_MSG_ID_00	= "10";
	/**
	 * 認証チェック・エラーです。（またはタイムアウトです。）
	 */
	public static final String   C_APP_MSG_ID_01	= "11";
	/**
	 * Cookieを許可して下さい。
	 */
	public static final String   C_APP_MSG_ID_02	= "12";
	/**
	 * 日付情報取得失敗(日付コントロール)
	 */
	public static final String   C_APP_MSG_ID_03	= "13";
	/**
	 * サービス時間外です。
	 */
	public static final String   C_APP_MSG_ID_04	= "14";
	/**
	 * サービス時間チェック（SQL Exception）
	 */
	public static final String   C_APP_MSG_ID_05	= "15";
	/**
	 * サービス時間チェック（Exception）
	 */
	public static final String   C_APP_MSG_ID_06	= "16";
	/**
	 * バッチ処理中のためサービス停止中です。
	 */
	public static final String   C_APP_MSG_ID_07	= "17";
	/**
	 * トラブルのためサービス停止中です。
	 */
	public static final String   C_APP_MSG_ID_08	= "18";
	/**
	 * マシン保守のためサービス停止中です。
	 */
	public static final String   C_APP_MSG_ID_09	= "19";
	/**
	 * サービス時間チェック（NO DATA FOUND）
	 */
	public static final String   C_APP_MSG_ID_10	= "20";
	/**
	 * サービス時間チェック（データ取得エラー）
	 */
	public static final String   C_APP_MSG_ID_11	= "21";
	/**
	 * 月次確定処理中のためサービス停止中です。
	 */
	public static final String   C_APP_MSG_ID_12	= "22";
	/**
	 * サービス時間チェック（その他）
	 */
	public static final String   C_APP_MSG_ID_13	= "23";
	
	/**
	 * このクラスがロードされたかどうか
	 */
	public static boolean	loadFlg	= false;
	
	static {
		// AppDBAccess
		setMsg( "10", "認証チェックでエラーになりました。" );
		setMsg( "11", "認証チェック・エラーです。（またはタイムアウトです。）" );
		setMsg( "12", "Cookieを許可して下さい。" );
		setMsg( "13", "日付情報取得失敗(日付コントロール)" );
		setMsg( "14", "サービス時間外です。" );
		setMsg( "15", "サービス時間チェック（SQL Exception）" );
		setMsg( "16", "サービス時間チェック（Exception）" );
		setMsg( "17", "バッチ処理中のためサービス停止中です。" );
		setMsg( "18", "トラブルのためサービス停止中です。" );
		setMsg( "19", "マシン保守のためサービス停止中です。" );
		setMsg( "20", "サービス時間チェック（NO DATA FOUND）" );
		setMsg( "21", "サービス時間チェック（データ取得エラー）" );
		setMsg( "22", "月次確定処理中のためサービス停止中です。" );
		setMsg( "23", "サービス時間チェック（その他）" );
	}
}		// *********************** End of Class  ***********************************
