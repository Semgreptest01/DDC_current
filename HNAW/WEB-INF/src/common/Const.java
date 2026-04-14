/**
*-------------------------------------------------------------------------------
* クラス名			Const.class
* システム名称		共通
* 名称				システム・定数クラス
* 会社名or所属名	株式会社ヴィクサス
* 作成日			2006/11/08 00:00:00
* @author			Y.Takabayashi
* @since			1.0
* @version			1.0
* *** 修正履歴 ***
* No. Date			Author		Description
*-------------------------------------------------------------------------------
*/
package common;

public class Const {
	/**
	 * パラメータ名 ユーザーID
	 */
	public static final String PRMTR_NM_USER_ID	= "namecd";
	/**
	 * パラメータ名 画面から送られてくる親画面のDBAccessセッション変数名
	 */
	public static final String PRMTR_NM_PRNT_DBA_NM	= "GNO";
	
	/**
	 * CON_SW iAS(JNDI)
	 */
	public static final String CON_SW_IAS			= "1";
	/**
	 * CON_SW Tomcat(Direct)
	 */
	public static final String CON_SW_TOMCAT_DRCT	= "2";
	/**
	 * CON_SW J2SDKEE(JNDI)
	 */
	public static final String CON_SW_J2SDKEE		= "3";
	/**
	 * CON_SW weblogic(JNDI)
	 */
	public static final String CON_SW_WEBLOGIC	= "4";
	/**
	 * CON_SW OracleiAS(JNDI)
	 */
	public static final String CON_SW_OAS_JNDI	= "5";
	/**
	 * CON_SW Tomcat5(JNDI)
	 */
	public static final String CON_SW_TOMCAT_JNDI	= "6";
	/**
	 * CON_SW OracleiAS(Direct)
	 */
	public static final String CON_SW_OAS_DRCT	= "7";
	
	/**
	 * サーブレット処理結果 通常
	 */
	public static final int SRVLT_RSLT_NRML		= 1;
	
	/**
	 * サーブレット処理結果 戻る
	 */
	public static final int SRVLT_RSLT_BCK		= -1;
	/**
	 * サーブレット処理結果 表示画面無し
	 */
	public static final int SRVLT_RSLT_NO_SCRN	= -2;
	/**
	 * サーブレット処理結果 サービス時間エラー
	 */
	public static final int SRVLT_RSLT_TIME_ERR	= -3;
	
	/**
	 * 共通・サービス時間チェック画面JSP名
	 */
	public static final String	CMN_TIME_JSP	= "TimeCheckJ.jsp";
	/**
	 * 共通・エラー画面JSP名
	 */
	public static final String	CMN_ERR_JSP		= "ServletErrorJ.jsp";
	/**
	 * 共通・ダミー画面JSP名
	 */
	public static final String	DUMMY_JSP		= "Dummy";
	
	/**
	 * 実マシン番号&CookieLoadBlance設定ファイルのindex 実マシン番号
	 */
	public static final int ACTUAL_IP_FILE_INDEX_ACTUAL_IP	= 0;
	
	/**
	 * 実マシン番号&CookieLoadBlance設定ファイルのindex Cookie LoadBalance用のServerID値
	 */
	public static final int ACTUAL_IP_FILE_INDEX_SRVR_ID	= 1;
	
	/**
	 * defaultのCookie LoadBalance用のServerID値
	 */
	public static final String DFLT_SRVR_ID	= "9999";
	
	/**
	 * Timeout値をミリ秒で設定
	 */
	public static final String SMTP_TIME_OUT	= "10000";
	/**
	 * 200K
	 */
	public static final int    MAX_IMAGE_SIZE	= 204800;
	/**
	 * 30M
	 */
	public static final int    MAX_CSV_SIZE	= 30720000;
	/**
	 * 10M
	 */
	public static final int    MAX_XLS_SIZE	= 10240000;
	/**
	 * 3000
	 */
	public static final int    MAX_CSV_ROWS	= 3000;
	/**
	 * バッファサイズ
	 */
	public static final int    BFFR_SIZE		= 2048;
	/**
	 * ダウンロード
	 */
	public static final String CNTNT_TYP_DWNLD	= "application/octet-stream";
	/**
	 * CSVダウンロード
	 */
	public static final String CNTNT_TYP_DWNLD_CSV	= "app/data-download; charset=";
	/**
	 * XLS
	 * Excelダウンロード&起動は、Http-Responseに以下の記述をする。
	 * response.setHeader("Content-Disposition","attachment;filename=XXXXXXXX.xls");
	 * response.setContentType(CNTNT_TYP);
	 */
	public static final String CNTNT_TYP_XLS		= "app/vnd.ms-excel";
	/**
	 * 文字セット EUC-JP
	 */
	public static final String CHAR_SET_EUC_JP	= "EUC-JP";
	/**
	 * XLS拡張子
	 */
	public static final String XLS_EXTNSN		= ".xls";
	/**
	 * <
	 */
	public static final String CSV_SPRTR		= ",";
	/**
	 * <
	 */
	public static final String CSV_QTTN_MRK	= "\"";
	/**
	 * <
	 */
	public static final String LSS_THN		= "<";
	/**
	 * >
	 */
	public static final String GRTR_THN		= ">";
	/**
	 * &
	 */
	public static final String AMPRSND		= "&";
	/**
	 * スペース
	 */
	public static final String SPC			= " ";
	/**
	 * 改行コード
	 */
	public static final String LN_FD_CD		= "\r\n";
	/**
	 * <（表示用）
	 */
	public static final String DSP_LSS_THN	= "&lt;";
	/**
	 * >（表示用）
	 */
	public static final String DSP_GRTR_THN	= "&gt;";
	/**
	 * &（表示用）
	 */
	public static final String DSP_AMPRSND	= "&amp;";
	/**
	 * スペース（表示用）
	 */
	public static final String DSP_SPC		= "&nbsp;";
	/**
	 * 改行コード（表示用）
	 */
	public static final String DSP_LN_FD_CD	= "<br>";
	/**
	 * 自動改行コード（表示用）
	 */
	public static final String DSP_AT_LN_FD_CD	= "<wbr>";
	/**
	 * 無効記号（表示用）
	 */
	public static final String DSP_INVLD_MRK	= "-";
	/**
	 * 禁止文字の代表
	 */
	public static final String PRHBTV_STR		= "\'￡\'";
	/**
	 * デフォルトの登録ボタンのラベル
	 */
	public static final String DFLT_RGST_BTTN_LBL	= "登録";
	/**
	 * 年のsuffix
	 */
	public static final String SFFX_YR	= "年";
	/**
	 * 年度のsuffix
	 */
	public static final String SFFX_FSCL_YR	= "年度";
	/**
	 * 月のsuffix
	 */
	public static final String SFFX_MNTH	= "月";
	/**
	 * 偶数行のクラス
	 */
	public static final String TR_CLS_EVN		= "line_even";
	/**
	 * 奇数行のクラス
	 */
	public static final String TR_CLS_ODD		= "line_odd";
	/**
	 * エラー行のクラス
	 */
	public static final String TR_CLS_ERR		= "line_err";
	/**
	 * 変更行のクラス
	 */
	public static final String TR_CLS_CHNG	= "line_chng";
	/**
	 * 通常列のクラス
	 */
	public static final String CLMN_NRML		= "column";
	/**
	 * エラー列のクラス
	 */
	public static final String CLMN_ERR		= "err_column";
	/**
	 * 不可視のクラス
	 */
	public static final String CLS_VSBLTY_HDDN	= "vsblty_hddn";
	/**
	 * 非表示のクラス
	 */
	public static final String CLS_DSPLY_NN	= "dsply_nn";
	/**
	 * エラーメッセージのクラス
	 */
	public static final String CLS_ERR_MSG	= "err_msg";
	/**
	 * エラーoptionのクラス
	 */
	public static final String CLS_ERR_OPTN	= "err_optn";
	/**
	 * 変更状態のインプットのスタイル
	 */
	public static final String INPT_STYL_CHNG	= " style=\"background-color: palegreen;\"";
	/**
	 * 削除状態のインプットのスタイル
	 */
	public static final String INPT_STYL_DEL	= " style=\"background-color: khaki;\"";
	/**
	 * インプットdisabled
	 */
	public static final String INPT_DSABLD	= " disabled";
	/**
	 * インプットchecked
	 */
	public static final String CHK_BX_CHCKD	= " checked";
	/**
	 * インプットselected
	 */
	public static final String SLCT_SLCTD		= " selected";
	/**
	 * 変更区分 追加
	 */
	public static final String CHG_KBN_ADD	= "1";
	/**
	 * 変更区分 変更
	 */
	public static final String CHG_KBN_CHG	= "2";
	/**
	 * 変更区分 削除
	 */
	public static final String CHG_KBN_DLT	= "3";
	/**
	 * 動作区分 新規
	 */
	public static final String ACTN_CLS_NEW	= "0";
	/**
	 * 動作区分 更新
	 */
	public static final String ACTN_CLS_UPDT	= "1";
	/**
	 * SW ON
	 */
	public static final String SW_ON			= "1";
	/**
	 * SW OFF
	 */
	public static final String SW_OFF			= "0";
	/**
	 * 配列index 変数 種別
	 */
	public static final int   ARRY_INDX_DB_TYP	= 0;
	/**
	 * 配列index 変数 値
	 */
	public static final int   ARRY_INDX_DB_VL		= 1;
	/**
	 * 配列index コード
	 */
	public static final int   ARRY_INDX_CD		= 0;
	/**
	 * 配列index 名称
	 */
	public static final int   ARRY_INDX_NM		= 1;
	/**
	 * 配列index エラーSW
	 */
	public static final int   ARRY_INDX_ERR_SW	= 2;
	/**
	 * 配列index 整数部
	 */
	public static final int ARRY_INDX_INTGR		= 0;
	/**
	 * 配列index 小数部
	 */
	public static final int ARRY_INDX_DCML		= 1;
	/**
	 * 配列index 変数 開始
	 */
	public static final int   ARRY_INDX_STRT	= 0;
	/**
	 * 配列index 変数 終了
	 */
	public static final int   ARRY_INDX_END	= 1;
	/**
	 * Vector index 変数 キー
	 */
	public static final int   VCTR_INDX_KYS			= 0;
	/**
	 * Vector index 変数 コードと名前の配列
	 */
	public static final int   VCTR_INDX_CD_AND_NMS	= 1;
	/**
	 * DBにセットする変数がString型である
	 */
	public static final int   INT_DB_STR		= 0;
	/**
	 * DBにセットする変数がDate型である
	 */
	public static final int   INT_DB_DT		= 1;
	/**
	 * DBにセットする変数が日時型である
	 */
	public static final int   INT_DB_DT_TM	= 2;
	/**
	 * DBにセットする変数が整数型である
	 */
	public static final int   INT_DB_INT		= 3;
	/**
	 * DBにセットする変数が浮動小数点型である
	 */
	public static final int   INT_DB_FLT		= 4;
	/**
	 * DBにセットする変数がString型である
	 */
	public static final String DB_STR			= String.valueOf( INT_DB_STR );
	/**
	 * DBにセットする変数がDate型である
	 */
	public static final String DB_DT			= String.valueOf( INT_DB_DT );
	/**
	 * DBにセットする変数が日時型である
	 */
	public static final String DB_DT_TM		= String.valueOf( INT_DB_DT_TM );
	/**
	 * DBにセットする変数が整数型である
	 */
	public static final String DB_INT			= String.valueOf( INT_DB_INT );
	/**
	 * DBにセットする変数が浮動小数点型である
	 */
	public static final String DB_FLT			= String.valueOf( INT_DB_FLT );
	/**
	 * 一週間の日数
	 */
	public static final int   WK_DY_CNT		= 7;
}		// *********************** End of Class  ***********************************
