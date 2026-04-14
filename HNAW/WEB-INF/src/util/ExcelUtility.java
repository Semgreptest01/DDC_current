/**
*-------------------------------------------------------------------------------
* クラス名			ExcelUtility.class
* システム名称		共通
* 名称				ユーティリティ・Excelクラス
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

public class ExcelUtility {
	/**
	********************************************************************************
	* ExcelのTEXT関数付与
	* @param	in_data					(String)
	* @return	Not null:OK null:ERR	(String)
	********************************************************************************
	*/
	public static String addEql(	String	in_data	) {
		try {
			String s = "";
			// 空は空で返す。
			if ( !"".equals( in_data ) ) {
				s = "=\"" + in_data + "\"";
			}
			return s;
		} catch ( Exception e ) {
			return null;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* Excel関数の削除フィルター( =" を取り除く)<br>
	* 補足　=X"ＡＢＣ" ----> ＡＢＣ に変換する。
	* @param	in_data				(String)
	* @return	Not "":OK "":ERR	(String)
	********************************************************************************
	*/
	public static String deleteEql(	String	in_data	) {
		try {
			String result = "";
			int sw1 = 0;
			char ch_eq = '=';
			char ch_dq = '"';
			if ( in_data != "" ) {
				//1文字ずつ取り出して = か判断する。
				for ( int i = 0; i < in_data.length(); i++ ) {
					if ( ch_eq == in_data.charAt( i ) ) {
						// ＝が先頭以外の場合は、取り除かない
						if ( i != 0 ) {
							result += in_data.charAt( i );
						}else{
							sw1 = 1;
						}
					} else {
						if( ch_dq == in_data.charAt( i ) ) {
							// sw1が１以外は取り除かない
							if ( sw1 != 1 ) {
								result += in_data.charAt( i );
							} else {
								// ”が先頭＋１ or 最後の場合は、取り除く
								if ( i != 1 || i != in_data.length() - 1 ) {
								} else {
									result += in_data.charAt( i );
								}
							}
						} else {
							result += in_data.charAt( i );
						}
					}
				}
			} else {
				result = in_data;
			}
			return result;
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
