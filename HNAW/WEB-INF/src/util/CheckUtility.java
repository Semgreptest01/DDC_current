/**
*-------------------------------------------------------------------------------
* クラス名			CheckUtility.class
* システム名称		共通
* 名称				ユーティリティ・チェッククラス
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

import common.Config;
import common.Const;

public class CheckUtility {
	/**
	********************************************************************************
	* 全角文字チェック<br>
	* @param	in_data				(String)
	* @return	0:OK 0以外:エラー	(int)<br>
	* **** エラーの説明 ****<br>
	* 1:制御コードあり	( \u0000～\u001F :     0～   31 )<br>
	* 2:半角英数字あり	( \u0020～\u00A1 :    32～  161 )<br>
	* 3:半角カナあり		( \uFF61～\uFF9F : 65377～65439 )<br>
	* 4:使用禁止文字あり<br>
	* \u2150～\u218F :  8528～ 8591　ローマ数字（Ⅰ,Ⅱ）など<br>
	* \u2460～\u24FF :  8512～ 9471　囲み数字（①～⑳）など<br>
	* \u3300～\u4DFF : 13056～19967　年号、単位（㍻,㍼）など<br>
	* \u3220～\u32FF : 12832～13055　囲み文字（㈱,㈲）など<br>
	* \uFFE0（￠）,\uFFE1（￡）,\uFFE2（￢）,\u00B4（´）,<br>
	* \u00A8（¨）,\u00A7（§）,\u00B6（¶）,\u00B0（°）,<br>
	* \u2116（№）,\u2121（℡）,\u2211（∑）,\u222E（∮）,<br>
	* \u221F（∟）,\u22BF（⊿）,\uFFE4（￤）,\u301D（〝）,<br>
	* \u301F（〟）,\uFF07（＇）,\uFF02（＂）<br>
	* 9:その他のエラー<br>
	* **** 使用禁止文字の一覧 ****<br>
	* ・ローマ数字など
	********************************************************************************
	*/
	public static int chkZenkaku(	String	in_data	) {
		try {
			int	int_dec = 0;
			if ( in_data != "") {
				for ( int i = 0; i < in_data.length(); i++ ) {
					int_dec = (int)in_data.charAt( i );
					if ( 63 == int_dec ) {							// エンコードに失敗したか？(使用禁止文字)(003F）
						return 4;
					}
					if ( 0 <= int_dec && int_dec <= 31 ) {			// 制御コードか？(0000～001F:0～31）
						return 1;
					}
					if ( 32 <= int_dec && int_dec <= 161 ) {		// 半角英数字か？(0020～00A1:32～161）
						return 2;
					}
					if ( 65377 <= int_dec && int_dec <= 65439 ) {	// 半角カナか？(FF61～FF9F:65377～65439）
						return 3;
					}
				}
			}
			return 0;
		} catch ( Exception e ) {
			return 9;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 禁止文字チェック
	* @param	in_data				(String)
	* @param	allwHlfWdthKnFlg	(boolean)
	* @return	true:OK false:ERR	(boolean)
	********************************************************************************
	*/
	public static boolean chkFrbddnChr(	String		in_data,
											boolean	allwHlfWdthKnFlg	) {
		boolean rslt = true;
		char chrCd;
		// 1文字ずつ取り出して判断する。
		for ( int i = 0; i < in_data.length(); i++ ) {
			chrCd = (char)in_data.charAt( i );
			// ＯＫ
			if (
				chrCd == '\n' ||								// "\n" 改行 (u000A)
				chrCd == '\r' ||								// "\r" 改行 (u000D)
				chrCd == '\uFF0D' ||							// "－" マイナス符号
				chrCd == '\uFF5E'								// "～" 波線
			) {
				continue;
			}
			// エラー
			if (
				chrCd == '\u0022' ||							// """
				chrCd == '\u0026' ||							// "&"
				chrCd == '\'' ||								// "'" (u0027)
				chrCd == '\u002C' ||							// ","
				chrCd == '\u00A8' ||							// "¨"
				chrCd == '\u00A7' ||							// "§"
				chrCd == '\u00B0' ||							// "°"
				chrCd == '\u00B4' ||							// "´"
				chrCd == '\u00B6' ||							// "¶"
				chrCd == '\u2116' ||							// "№"
				chrCd == '\u2121' ||							// "℡"
				( chrCd >= '\u2150' && chrCd <= '\u218F' ) ||	// ローマ数字など
				chrCd == '\u2211' ||							// "∑"
				chrCd == '\u221F' ||							// "∟"
				chrCd == '\u2225' ||							// "∥" 二重縦線
				chrCd == '\u222E' ||							// "∮"
				chrCd == '\u22BF' ||							// "⊿"
				( chrCd >= '\u2460' && chrCd <= '\u24FF' ) ||	// 囲み数字（まる囲み数字）など
				chrCd == '\u301D' ||							// "〝" ダブルクォーテーションみたいな記号
				chrCd == '\u301F' ||							// "〟" ダブルクォーテーションみたいな記号
				( chrCd >= '\u3220' && chrCd <= '\u32FF' ) ||	// 囲み文字（かっこ付株）など
				( chrCd >= '\u3300' && chrCd <= '\u4DFF' ) ||	// 年号、単位など
				(
					!allwHlfWdthKnFlg &&
					chrCd >= '\uFF61' && chrCd <= '\uFF9F'		// 半角カナ
				) ||
				chrCd == '\uFF02' ||							// "＂" ダブルクォーテーションみたいな記号
				chrCd == '\uFF07' ||							// "＇" シングルクォーテーションみたいな記号
				chrCd == '\uFFE0' ||							// "￠" セント記号
				chrCd == '\uFFE1' ||							// "￡" ポンド記号
				chrCd == '\uFFE2' ||							// "￢" ノット記号
				chrCd == '\uFFE4'								// "￤"
			) {
				rslt = false;
				break;
			}
		}
		return rslt;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 禁止文字チェック
	* @param	in_data				(String)
	* @return	true:OK false:ERR	(boolean)
	********************************************************************************
	*/
	public static boolean chkFrbddnChr(	String		in_data	) {
		return chkFrbddnChr( in_data, Const.SW_ON.equals( Config.ALLW_HLF_WDTH_KANA ) );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 半角数字文字チェック(符号なし)
	* @param	in_data					(String)
	* @return	0:OK, 1or9:エラーあり	(int)
	********************************************************************************
	*/
	public static int chkNumeric(	String	in_data	) {
		try {
			int	int_dec = 0;
			if ( in_data != "" ) {
				for ( int i = 0; i < in_data.length(); i++ ) {
					int_dec = (int)in_data.charAt( i );
					if ( int_dec < 48 || 57 < int_dec ) {	// 半角数字以外か？(0030～0039:48～57）
						return 1;
					}
				}
			}
			return 0;
		} catch ( Exception e ) {
			return 9;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 数値桁数チェック
	* @param	in_data				(String)
	* @param	intgr				(int)
	* @param	dcml				(int)
	* @param	allwPlsFlg			(boolean)
	* @param	allwMnsFlg			(boolean)
	* @param	allwFllZrFlg		(boolean)
	* @return	true:OK false:ERR	(boolean)
	********************************************************************************
	*/
	public static boolean chkNmrcAndDgt(	String		in_data,
											int			intgr,
											int			dcml,
											boolean	allwPlsFlg,
											boolean	allwMnsFlg,
											boolean	allwFllZrFlg	)
	{
		boolean rslt = true;
		if ( intgr > 0 && dcml >= 0 && in_data != null ) {
			String rgx = "\\+{0," + ( allwPlsFlg ? "1" : "0" ) + "}-{0," + ( allwMnsFlg ? "1" : "0" ) + "}";
			rgx += ( allwFllZrFlg ? "[\\d]{1," + String.valueOf( intgr ) + "}" : "(([1-9][\\d]{0," + String.valueOf( intgr - 1 ) + "})|0)" ) + "[.]?[\\d]{0," + String.valueOf( dcml ) + "}";
			rslt = in_data.matches( rgx );
		} else {
			rslt = false;
		}
		return rslt;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 数値桁数チェック
	* @param	in_data						(String)
	* @param	fgrArry( 0:intgr 1:dcml )	(int[])
	* @param	allwPlsFlg					(boolean)
	* @param	allwMnsFlg					(boolean)
	* @param	allwFllZrFlg				(boolean)
	* @return	true:OK false:ERR			(boolean)
	********************************************************************************
	*/
	public static boolean chkNmrcAndDgt(	String		in_data,
											int[]		fgrArry,
											boolean	allwPlsFlg,
											boolean	allwMnsFlg,
											boolean	allwFllZrFlg	)
	{
		return chkNmrcAndDgt( in_data, fgrArry[ Const.ARRY_INDX_INTGR ], fgrArry[ Const.ARRY_INDX_DCML ], allwPlsFlg, allwMnsFlg, allwFllZrFlg );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 半角英数字文字チェック
	* @param	in_data					(String)
	* @return	0:OK, 1or9:エラーあり	(int)
	********************************************************************************
	*/
	public static int chkANumeric(String	in_data) {
		try {
			int	int_dec = 0;
			if ( in_data != "" ) {
				for ( int i = 0; i < in_data.length(); i++ ) {
					int_dec = (int)in_data.charAt( i );
					if ( int_dec < 32 || 126 < int_dec ) {	// 半角英数字以外か？(0020～007E:32～126）
						return 1;
					}
				}
			}
			return 0;
		} catch ( Exception e ) {
			return 9;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 半角英数字文字桁数チェック
	* @param	in_data				(String)
	* @param	lngth				(int)
	* @param	eqlFlg				(boolean)
	* @return	true:OK false:ERR	(boolean)
	********************************************************************************
	*/
	public static boolean chkANmrcAndLngth(	String		in_data,
												int			lngth,
												boolean	eqlFlg	)
	{
		boolean rslt = !eqlFlg || in_data.length() == lngth;
		rslt = rslt && in_data.length() <= lngth;
		rslt = rslt && chkANumeric( in_data ) == 0;
		return  rslt;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付文字チェック(YYYY/MM/DD形式)
	* @param	in_data		(YYYY/MM/DD)	(String)
	* @param	min_date	(YYYYMMDD)		(String)
	* @param	max_data	(YYYYMMDD)		(String)
	* @return	0:OK, 0以外:エラーあり		(int)<br>
	* **** エラーの説明 ****<br>
	* 1:基本チェックエラー<br>
	* 2:実在日チェックエラー<br>
	* 3:閏年チェックエラー<br>
	* 9:その他のエラー（数値部に文字列を指定した場合等）
	********************************************************************************
	*/
	public static int chkYMD(	String	in_data,
								String	min_date,
								String	max_date	) {
		try {
			if ( in_data != "" ) {
				int yyyy	= Integer.parseInt(in_data.substring( 0, 4 ) );
				String s1	= in_data.substring( 4, 5 );
				int mm		= Integer.parseInt( in_data.substring( 5, 7 ) );
				String s2	= in_data.substring( 7, 8 );
				int dd		= Integer.parseInt( in_data.substring( 8, 10 ) );
				int min_ymd	= Integer.parseInt( min_date );
				int max_ymd	= Integer.parseInt( max_date );
				if ( !"/".equals( s1 ) ) {
					return 1;
				}
				if ( !"/".equals( s2 ) ) {
					return 1;
				}
				if ( mm > 12 || mm < 1 ) {
					return 1;
				}
				if ( dd > 31 || dd < 1 ) {
					return 1;
				}
				int yyyymmdd = ( yyyy * 10000 ) + ( mm * 100) + dd;
				if ( yyyymmdd < min_ymd ) {
					return 1;
				}
				if ( yyyymmdd > max_ymd ) {
					return 1;
				}
				// 実在日チェック(4月,6月,9月,11月の日が30日以上だとエラー）
				if ( mm == 4 || mm == 6 || mm == 9 || mm == 11 ) {
					if ( dd > 30 ) {
						return 2;
					}
				}
				// 閏年チェック
				if ( mm == 2 ) {
					if ( ( yyyy % 4 ) == 0 ) {
						if ( ( yyyy % 100 ) != 0 ) {
							if ( dd > 29 || dd < 1 ) {
								return 3;
							}
						} else {
							if ( ( yyyy % 400 ) == 0 ) {
								if ( dd > 29 || dd < 1 ) {
									return 3;
								}
							}
						}
					} else {
						if ( dd > 28 || dd < 1 ) {
							return 3;
						}
					}
				}
			}
			return 0;
		} catch ( Exception e ) {
			return 9;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 時間範囲チェック<br>
	* ※開始時刻(HHMM)と終了時刻(HHMM)が00 または "" の場合は、1を返す。
	* @param	stTime	開始時刻(HHMM)	(String)
	* @param	edTime	終了時刻(HHMM)	(String)
	* @param	wkTime	比較時刻(HHMM)	(String)
	* @return	0:範囲外 1:範囲内 2:無効	(int)
	********************************************************************************
	*/
	public static int checkTime(	String 	stTime,
									String	edTime,
									String	wkTime	) throws Exception
	{
		int wk_st = 0;
		int wk_ed = 0;
		int wk_cm = 0;
		if ( "".equals( stTime ) ) {
			return 1;
		}
		if ( "".equals( edTime ) ) {
			return 1;
		}
		wk_st = Integer.parseInt( stTime );
		wk_ed = Integer.parseInt( edTime );
		wk_cm = Integer.parseInt( wkTime );
		if ( wk_st == 0 && wk_ed == 0 ) {
			return 1;
		}
		if ( wk_st > wk_cm ) {
			return 0;
		}
		if ( wk_ed < wk_cm ) {
			return 0;
		}
		return 1;
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
