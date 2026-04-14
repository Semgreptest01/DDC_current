/**
*-------------------------------------------------------------------------------
* クラス名			Config.class
* システム名称		共通
* 名称				システム・共通クラス（基底クラス）
* 会社名or所属名	富士ソフトＤＩＳ
* 作成日			2006/11/08 00:00:00
* @author			Y.Takabayashi
* @since			1.0
* @version			1.0
* *** 修正履歴 ***
* No. Date			Author		Description
*-------------------------------------------------------------------------------
*/
package common;

import java.util.ResourceBundle;

import javax.servlet.ServletContext;

public class Config extends Object {
	// *********************************************************************************************
	// * ☆☆☆ 環境設定 ☆☆☆
	// * ・各種Configuration値をこのクラスで記述するようにしてください。
	// * ・環境にインパクトを与えるような記述をサブクラス側で直記述するのは絶対に避けること。
	// * ・ここで指定する変数値は、重要な意味を持っています。本番Deploy時には、十分注意すること。
	// *	_/_/_/	2005.06.03 Y.Takabayashi	_/_/_/
	// * (1)【実マシン番号設定】
	// *	/prod/contdata直下にIP_ADDR.txt
	// *    (Windows-Tomcatでのテストの場合、C:\prod\contdataに配置する。)
	// *	ファイルを配置します。内容は、IPアドレスの最下番号を記述しておきます。
	// * (2)【Cookie Load Blance設定】
	// *	VIP(バーチャルIP）によるロードバランスを設定するためには、VIP用の
	// *	CookieをServletから発行しなければなりません。そのときのSessionCookie
	// *	内容は、ロードバランスする実機の番号を記述する必要があります。
	// *	そのためには、Servletは、自分が動作している実機を知る必要があり、その
	// *	号機別に割り振られたServerID値をCookieにセットする必要があります。実機
	// *	は、Actual_IP_Fileを読めばわかります。号機別に割り振られたServerID値は、
	// *	getServerID()メソッドにより求めること。（先に述べたIP_ADDR.txtファイルの第２レコード値）
	// * (3)【Connection設定】
	// *	RDBへのＪＤＢＣ接続は、通常はJNDI接続します。接続方法がサーバーにより
	// *	異なりますから、CON_SWの値でコントロールします。
	// *	（Xhsflj_Dbcomクラスにより自動的に判定されます）
	// * (4)【DEBUG設定】(通常は、0)
	// *	本番機へDeployする場合は、必ず 0 をセットしてください。
	// *	1:DEBUG中の場合、コンソールに大量のログ（SQL文等）が出力されます。
	// *    (※WeblogicServerの場合、STDOUT,STDERRは、破棄されますが、iASは出力されます）
	// * (5)【APP_LOG設定】(通常は、0)
	// *	ここで指すログとは、Servlet処理結果（パフォーマンス情報）をＪＳＰへ渡し、
	// *    ＪＳＰ経由でServerLog（コンソールではない）へ表示するかどうかを指定します。
	// *    Weblogicの標準的な起動では、標準出力は、破棄されます。
	// *    このSWを 1 にすれば ServerLog(DomainLog)にServletのパフォーマンス情報がログ出力されます。
	// * (6)【LOG設定】(通常は、0)
	// *	ここで指すログとは、アプリケーションで実装したログＤＢのことを指します。
	// *	（システムログのことでは、ありません）
	// *	この値が 2 の場合、Servletのパフォーマンス情報
	// *	（VM全メモリ、VM_フリーメモリ、ELAPS、スレッド情報）が出力されます。
	// * (7)【パフォーマンス計測SW設定】(通常は、0)
	// *	このSWは、上記のLOG設定の情報を画面へ表示する／しないの設定です。
	// * (8)【セッションID設定】(通常は、0)
	// *	このSWは、セッションＩＤを画面へ表示する／しないの設定です。
	// * (9)【セッション終了の設定】
	// *	このSWは、「閉じる」ボタンでセッションを終了する／しないの設定です。
	// *	共通セキュリティ・メニューから複数の画面起動を許している関係上、この
	// *	ＳＷ = 1とすると同一コンテキストの画面を複数開いている場合、セッショ
	// *	ンを終了すると、他方の画面セッションも破棄されます。これをＳＷ = 1の
	// *	ままで回避するには、「複数画面で操作中に他方のセッションを終わりたく
	// *	ない場合、×ボタンで閉じるという方法しかありません。」
	// *	ＳＷ = 0にするとセッションを破棄しないのでＪＶＭシステム資源の効率は、
	// *	ガベージ・コレクション任せとなりますから効率の良い作りとはいえませんが
	// *	外部に開放する画面等で複数画面のオープン操作を許可したいのであれば、上
	// *    記の運用には無理がありますから、ＳＷ = 0で運用することになります。
	// *    ※SessionTimeOutが長い場合は、VMメモリ食いの運用となります。
	// * (10)【Redirect SWの設定】(通常は、0)
	// *	通常、セッションの維持は、Cookieによって行いますが、Cookieを使用せずに
	// *	行いたい場合、このＳＷを 1 にします。ただし、Cookie必須のNetwork環境の
	// *    場合(ｻｰﾊﾞｰｱｲｱﾝ等が介在する場合)は、このSWは、0 にしておくこと。
	// * (11)【COOKIE_SWの設定】(通常は、0)
	// *	Cookie内容をコンソールに表示する／しないの設定です。
	// *    (ブラウザからのCookie内容を確かめたい場合、1にすればCookieが表示されます）
	// * (12)【COOKIE_LOADB_SWの設定】(通常は、1)
	// *	Cookieロードバランスする／しないの設定です。
	// * (13)【SNOOP_SWの設定】(通常は、0)
	// *	ブラウザからのリクエスト情報等を画面Msg行領域に表示する／しないの設定です。
	// *    表示する情報は、以下のとおりです。
	// *    ・実マシン番号、Request Method、Request URI、Request Protocol、Servlet path、
	// *      Path info、Path translated、Query string、Content length、Content type、
	// *      Server name、Server port、Remote user、Remote address、Remote host、
	// *      Authorization scheme、Locale、Session ID From Cookie、Session ID From URL、
	// *      Session ID Valid、User Agent(ブラウザ･バージョン情報)、COOKIE内容
	// * (14)【CHAR_SETの設定】
	// *	文字コードセットの指定をします。アプリケーション・サーバがWindows機の場合、
	// *	Shift_JIS または Windows-31J、UNIX機の場合、EUC-JPとなります。
	// *    （Windows環境の場合でJ2SDK1.4.1以前：Shift_JIS、J2SDK1.4.1以降：Windows-31J）
	// * (15)【CONT_PATHの設定】
	// *	静的コンテンツのパスを記述します。
	// * (16)【JSP_PATHの設定】
	// *	ＪＳＰのパスを記述します。
	// *********************************************************************************************
	/**
	 * アプリケーション名
	 */
	public static String APP_NM					= "TEST";
	/**
	 * 主要なパッケージ名
	 */
	public final static String MAIN_PCKG_NM		= "app";
	/**
	 * パッケージ名の配列
	 */
	public final static String[] PCKG_NMS			= { MAIN_PCKG_NM, "common", "util" };
	/**
	 * 静的コンテンツディレクトリPATH
	 */
	public final static String CONT_DIR_PATH		= "contdata";
	/**
	 * StatementファイルディレクトリPATH
	 */
	public final static String STTMNT_FL_DIR_PATH = "/contdata/statement";
	/**
	 * XLSディレクトリPATH
	 */
	private final static String XLS_DIR_PATH		= "/contdata/xls";
	/**
	 * JSPディレクトリPATH
	 */
	public final static String JSP_DIR_PATH 		= "/jsp";
	/**
	 * 0:半角カナを使わない 1:半角カナを使う
	 */
	public final static String ALLW_HLF_WDTH_KANA	= "1";
	
	// 以下はpropetiesから読込む
	/**
	 * 実マシン番号&CookieLoadBlance設定ファイル
	 */
	public static String ACTUAL_IP_FILE		= "/prod/cont-data/IP_ADDR.txt";
	/**
	 * 0:IP設定ファイルを読まない 1:IP設定ファイルを読む
	 */
	public static String READ_IP_FILE_SW		= "1";
	/**
	 * Shift_JIS or EUC-JP or Windows-31J
	 */
	public static String CHAR_SET 				= "Shift_JIS";
	/**
	 * Shift_JIS or EUC-JP or Windows-31J
	 */
	public static String DWNLD_CSV_CHAR_SET 	= "Shift_JIS";
	/**
	 * 1:iAS(JNDI)　2:Tomcat(Direct)　3:J2SDKEE(JNDI)
	 * 4:weblogic(JNDI) 5:OracleiAS(JNDI)
	 * 6:Tomcat5(JNDI)  7:OracleiAS(Direct)
	 */
	public static String CON_SW				= "2";
	/**
	 * 0:本番 1:DEBUG中
	 */
	public static String DEBUG_SW 				= "1";
	/**
	 * 0:Systemからコントロール日付を取得 1:DBからコントロール日付を取得
	 */
	public static String CNTRL_DT_FRM_DB		= "1";
	/**
	 * 0:FOR UPDATEを使わない 1:FOR UPDATEを使う
	 */
	public static String USE_FOR_UPDATE		= "1";
	/**
	 * SQL文 FOR UPDATE
	 */
	public static String SQL_FR_UPDT			= "";
	/**
	 * パフォーマンス計測SW     (0:画面Msgに表示しない 1:画面Msgに表示する)
	 */
	public static String PERFORMANCE_SW		= "0";
	/**
	 * Session_id表示SW
	 * (0:画面Msgに表示しない 1:画面Msgに表示する)
	 */
	public static String SESSION_ID_SW			= "0";
	/**
	 * encodeRedirectURLSW
	 * (0:使用しない 1:使用する)
	 */
	public static String ENCD_REDIRECT_SW		= "0";
	/**
	 * COOKIE情報SW
	 * (0:コンソール表示しない 1:コンソール表示する)
	 */
	public static String COOKIE_SW				= "0";
	/**
	 * COOKIEロードバランスSW
	 * (0:しない 1:する)
	 */
	public static String COOKIE_LOADB_SW		= "1";
	/**
	 * SNOOP情報SW
	 * (0:画面Msgに表示しない 1:画面Msgに表示する)
	 */
	public static String SNOOP_SW				= "0";
	/**
	 * setMaxInactiveIntervalをコールするかどうか
	 * （0:コールしない 1:コールする）
	 * コールしなかった場合、web.xmlのsession-timeoutが使われる
	 */
	public static String SESSION_TIME_SW		= "0";
	/**
	 * setMaxInactiveIntervalで使用するsession-timeout数値（秒）
	 * setMaxInactiveIntervalをコールした場合、
	 * こちらの方がweb.xmlのsession-timeoutより優先される
	 */
	public static int    SESSION_TIMEOUT		= 7200;
	/**
	 * 一度に取得する明細の最大行数
	 */
	public static int    DTL_MAX				= 999;
	/**
	 * 一度に表示する明細の最大行数
	 */
	public static int    DTL_MAX_DSP_AT_A_TM	= 15;
	/**
	 * 日付・最小値
	 */
	public static String MIN_DATE				= "19000101";
	/**
	 * 日付・最大値
	 */
	public static String MAX_DATE				= "29991231";
	/**
	 * SMTP_HOST_Name
	 */
	public static String SMTP_HOST				= "localhost";
	/**
	 * CSV File Upload Encording
	 */
	public static String UPLOAD_ENCORDING		= "Windows-31J";
	/**
	 * 時間チェックSW(0:時間チェックしない 1:時間チェックする)
	 */
	public static String TIME_SW				= "1";
	/**
	 * SQL改行SW(0:改行しない 1:改行する)
	 */
	public static String SQL_LF_SW				= "1";
	/**
	 * JNDI Name
	 */
	public static String JNDI_NAME				= "jdbc/TEST_ORACLE001";

	// これ以降は、Direct Connection(Tomcat & Oracle)時に使用します。(JNDI接続の場合は、未使用）

	/**
	 * 開発用DB
	 */
	public static String THIN_DRIVER_URL		= "jdbc:oracle:thin:@10.173.110.9:1521:LT1A";
	/**
	 * Driver Name
	 */
	public static String DRIVER_NAME			= "oracle.jdbc.driver.OracleDriver";
	/**
	 * DB User ID
	 */
	public static String DB_USER				= "OXK001";
	/**
	 * DB Password
	 */
	public static String DB_PASS				= "OXK001";

	// 以下は、Direct Connection(Tomcat & MS-Access)時に使用します。(通常は、未使用）（Servlet単位にMDBに切り替えたい場合）
	/**
	 * 開発用DB(MS-Access)
	 */
	public static String MS_DRIVER_URL			= "jdbc:odbc:OXK001";
	/**
	 * Driver Name
	 */
	public static String MS_DRIVER_NAME		= "sun.jdbc.odbc.JdbcOdbcDriver";
	/**
	 * DB User ID
	 */
	public static String MS_USER				= "";
	/**
	 * DB Password
	 */
	public static String MS_PASS				= "";
  	
	/**
	 * Configプロパティファイル名
	 */
	private static final String CNFG_PRPRTY	= MAIN_PCKG_NM;
	/**
	********************************************************************************
	* プロパティの取得
	* @param	rsrcBndl	(ResourceBundle)
	* @param	prprtyVl	(String)
	* @param	prprtyNm	(String)
	* @return	プロパティ	(String)
	********************************************************************************
	*/
	private static String getPrprtyString(	ResourceBundle	rsrcBndl,
											String			prprtyVl,
											String			prprtyNm	)
	{
		String rslt = prprtyVl;
		try {
			rslt = rsrcBndl.getString( prprtyNm );
		} catch ( Exception e ) {}
		return rslt;
	}
	/**
	********************************************************************************
	* プロパティの取得
	* @param	rsrcBndl	(ResourceBundle)
	* @param	prprtyVl	(int)
	* @param	prprtyNm	(String)
	* @return	プロパティ	(int)
	********************************************************************************
	*/
	private static int getPrprtyInt(	ResourceBundle	rsrcBndl,
										int				prprtyVl,
										String			prprtyNm	)
	{
		int rslt = prprtyVl;
		try {
			rslt = Integer.parseInt( rsrcBndl.getString( prprtyNm ) );
		} catch ( Exception e ) {}
		return rslt;
	}
	
	static {
		ResourceBundle rsrcBndl = ResourceBundle.getBundle( CNFG_PRPRTY );
		APP_NM				= Config.getPrprtyString( rsrcBndl, APP_NM, "APP_NM" );
		ACTUAL_IP_FILE		= Config.getPrprtyString( rsrcBndl, ACTUAL_IP_FILE, "ACTUAL_IP_FILE" );
		READ_IP_FILE_SW		= Config.getPrprtyString( rsrcBndl, READ_IP_FILE_SW, "READ_IP_FILE_SW" );
		CHAR_SET			= Config.getPrprtyString( rsrcBndl, CHAR_SET, "CHAR_SET" );
		DWNLD_CSV_CHAR_SET	= Config.getPrprtyString( rsrcBndl, DWNLD_CSV_CHAR_SET, "DWNLD_CSV_CHAR_SET" );
		CON_SW				= Config.getPrprtyString( rsrcBndl, CON_SW, "CON_SW" );
		DEBUG_SW			= Config.getPrprtyString( rsrcBndl, DEBUG_SW, "DEBUG_SW" );
		CNTRL_DT_FRM_DB		= Config.getPrprtyString( rsrcBndl, CNTRL_DT_FRM_DB, "CNTRL_DT_FRM_DB" );
		USE_FOR_UPDATE		= Config.getPrprtyString( rsrcBndl, USE_FOR_UPDATE, "USE_FOR_UPDATE" );
		SQL_FR_UPDT			= Const.SW_ON.equals( Config.USE_FOR_UPDATE ) ? " FOR UPDATE" : "";
		PERFORMANCE_SW		= Config.getPrprtyString( rsrcBndl, PERFORMANCE_SW, "PERFORMANCE_SW" );
		SESSION_ID_SW		= Config.getPrprtyString( rsrcBndl, SESSION_ID_SW, "SESSION_ID_SW" );
		ENCD_REDIRECT_SW	= Config.getPrprtyString( rsrcBndl, ENCD_REDIRECT_SW, "ENCD_REDIRECT_SW" );
		COOKIE_SW			= Config.getPrprtyString( rsrcBndl, COOKIE_SW, "COOKIE_SW" );
		COOKIE_LOADB_SW		= Config.getPrprtyString( rsrcBndl, COOKIE_LOADB_SW, "COOKIE_LOADB_SW" );
		SNOOP_SW			= Config.getPrprtyString( rsrcBndl, SNOOP_SW, "SNOOP_SW" );
		SESSION_TIME_SW		= Config.getPrprtyString( rsrcBndl, SESSION_TIME_SW, "SESSION_TIME_SW" );
		SESSION_TIMEOUT		= Config.getPrprtyInt( rsrcBndl, SESSION_TIMEOUT, "SESSION_TIMEOUT" );
		DTL_MAX				= Config.getPrprtyInt( rsrcBndl, DTL_MAX, "DTL_MAX" );
		DTL_MAX_DSP_AT_A_TM	= Config.getPrprtyInt( rsrcBndl, DTL_MAX_DSP_AT_A_TM, "DTL_MAX_DSP_AT_A_TM" );
		MIN_DATE			= Config.getPrprtyString( rsrcBndl, MIN_DATE, "MIN_DATE" );
		MAX_DATE			= Config.getPrprtyString( rsrcBndl, MAX_DATE, "MAX_DATE" );
		SMTP_HOST			= Config.getPrprtyString( rsrcBndl, SMTP_HOST, "SMTP_HOST" );
		UPLOAD_ENCORDING	= Config.getPrprtyString( rsrcBndl, UPLOAD_ENCORDING, "UPLOAD_ENCORDING" );
		TIME_SW				= Config.getPrprtyString( rsrcBndl, TIME_SW, "TIME_SW" );
		SQL_LF_SW			= Config.getPrprtyString( rsrcBndl, SQL_LF_SW, "SQL_LF_SW" );
		JNDI_NAME			= Config.getPrprtyString( rsrcBndl, JNDI_NAME, "JNDI_NAME" );
		THIN_DRIVER_URL		= Config.getPrprtyString( rsrcBndl, THIN_DRIVER_URL, "THIN_DRIVER_URL" );
		DRIVER_NAME			= Config.getPrprtyString( rsrcBndl, DRIVER_NAME, "DRIVER_NAME" );
		DB_USER				= Config.getPrprtyString( rsrcBndl, DB_USER, "DB_USER" );
		DB_PASS				= Config.getPrprtyString( rsrcBndl, DB_PASS, "DB_PASS" );
		MS_DRIVER_URL		= Config.getPrprtyString( rsrcBndl, MS_DRIVER_URL, "MS_DRIVER_URL" );
		MS_DRIVER_NAME		= Config.getPrprtyString( rsrcBndl, MS_DRIVER_NAME, "MS_DRIVER_NAME" );
		MS_USER				= Config.getPrprtyString( rsrcBndl, MS_USER, "MS_USER" );
		MS_PASS				= Config.getPrprtyString( rsrcBndl, MS_PASS, "MS_PASS" );
	}
	/**
	********************************************************************************
	* StatementファイルディレクトリPATHの取得
	* @param	srvltCntxt							(ServletContext)
	* @return	StatementファイルディレクトリPATH	(String)
	********************************************************************************
	*/
	public static String getSttmntFlDrctryPth(	ServletContext		srvltCntxt	)
	{
		return srvltCntxt.getRealPath( STTMNT_FL_DIR_PATH );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* XLSディレクトリPATHの取得
	* @param	srvltCntxt			(ServletContext)
	* @return	XLSディレクトリPATH	(String)
	********************************************************************************
	*/
	public static String getXlsDrctryPth(	ServletContext		srvltCntxt	)
	{
		return srvltCntxt.getRealPath( XLS_DIR_PATH );
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
