// *----------------------------------------------------------------------------
// * システム		:	共通
// * スクリプト		:	簡易チェック
// * 会社名or所属名	:	株式会社ヴィクサス
// * 作成日			:	2006/03/03
// * 作成者			:	Hirata
// * *** 修正履歴 ***
// * No.	Date		Author		Description
// *----------------------------------------------------------------------------
/***********************************************************
 *関数名：objFcs( obj )
 *機能　：フォーカス
 ***********************************************************/
function objFcs( obj ) {
	obj.focus();
	if ( obj.tagName == "INPUT" || obj.tagName == "TEXTAREA" ) {
		obj.select();
	}
}
/***********************************************************
 *関数名：necChk( str, objNm )
 *機能　：必須チェック
 ***********************************************************/
function necChk( str, objNm ) {
	if ( str == "" ) {
		dspMsg( "F0008", new Array( objNm ) );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：filePathChk( str, objNm )
 *機能　：ファイルパスチェック
 ***********************************************************/
function filePathChk( str, objNm ) {
	var arry = sliceFilePath( str );
	var drive = arry[0];
	var path = arry[1];
	var file = arry[2];
	var ext = arry[3];
	
	if ( path == "" ) {
		dspMsg( "F0000", new Array( objNm ) );
		return false;
	}
	if ( file == "" ) {
		dspMsg( "F0001", new Array( objNm ) );
		return false;
	}
	if ( ext == "" ) {
		dspMsg( "F0002", new Array( objNm ) );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：numrcChk( str, objNm )
 *機能　：数値チェック
 ***********************************************************/
function numrcChk( str, objNm ) {
	if ( isNaN( str ) ) {
		dspMsg( "F0004", new Array( objNm ) );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：intgrChk( str, objNm )
 *機能　：整数チェック
 ***********************************************************/
function intgrChk( str, objNm ) {
	if ( str.match( /\D/ ) ) {
		dspMsg( "F0024", new Array( objNm ) );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthAlphbtOrDgtChk( str, objNm )
 *機能　：半角英数字チェック
 ***********************************************************/
function hlfwdthAlphbtOrDgtChk( str, objNm ) {
	if ( str != "" ) {
		// 1文字ずつ取り出して全角か判断する。
		for ( var i = 0; i < str.length; i++ ) {
			if ( escape( str.charAt( i ) ).length >= 4 ) {
				dspMsg( "F0003", new Array( objNm ) );
				return false;
			}
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthCptlAlphbtOrDgtOrSgnChk( str, objNm )
 *機能　：半角大英字数字記号チェック
 ***********************************************************/
function hlfwdthCptlAlphbtOrDgtOrSgnChk( str, objNm ) {
	var chrCd = "";
	
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = escapeUnicodeAll( str.charAt( i ) );
		if (
			chrCd == "%u0022" ||							// """
			chrCd == "%u0027" ||							// "'"
			chrCd == "%u002C" ||							// ","
			( chrCd >= "%u0061" && chrCd <= "%u007A" ) ||
			!( chrCd >= "%u0020" && chrCd <= "%u007E" )
		) {
			dspMsg( "F0021", new Array( objNm ) );
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthCptlAlphbtOrDgtChk( str, objNm )
 *機能　：半角大英字数字チェック
 ***********************************************************/
function hlfwdthCptlAlphbtOrDgtChk( str, objNm ) {
	var chrCd = "";
	
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = escapeUnicodeAll( str.charAt( i ) );
		if (
			!( chrCd >= "%u0030" && chrCd <= "%u0039" ) &&
			!( chrCd >= "%u0041" && chrCd <= "%u005A" )
		) {
			dspMsg( "F0022", new Array( objNm ) );
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthChk( str, objNm )
 *機能　：半角チェック
 ***********************************************************/
function hlfwdthChk( str, objNm ) {
	if ( str != "" ) {
		var chrCd;
		for ( var i = 0; i < str.length; i++ ) {
			// 1文字ずつ取り出して半角か判断する。
			chrCd = escape( str.charAt( i ) );
			if (
				chrCd.length >= 4 &&
				( chrCd < "%uFF61" || chrCd > "%uFF9F" )
			) {
				dspMsg( "F0019", new Array( objNm ) );
				return false;
			}
		}
	}
	return true;
}
/***********************************************************
 *関数名：dblwdthChk( str, objNm )
 *機能　：全角チェック
 ***********************************************************/
function dblwdthChk( str, objNm ) {
	if ( str != "" ) {
		var errFlg = false;
		if ( !hlfwdthKanaNtExstChk( str, objNm ) ) {
			errFlg = true;
		} else {
			// 1文字ずつ取り出して全角か判断する。
			for ( var i = 0; i < str.length; i++ ) {
				if ( escape( str.charAt( i ) ).length < 4 ) {
					errFlg = true;
					break;
				}
			}
		}
		if ( errFlg ) {
			dspMsg( "F0017", new Array( objNm ) );
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthKanaNtExstChk( str, objNm )
 *機能　：半角カナ非存在チェック
 ***********************************************************/
function hlfwdthKanaNtExstChk( str, objNm ) {
	var chrCd = "";
	
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = escapeUnicodeAll( str.charAt( i ) );
		if ( chrCd >= "%uFF61" && chrCd <= "%uFF9F" ) {
			dspMsg( "F0005", new Array( objNm ) );
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthKanaChk( str, objNm )
 *機能　：半角カナチェック
 ***********************************************************/
function hlfwdthKanaChk( str, objNm ) {
	var chrCd = "";
	
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = escapeUnicodeAll( str.charAt( i ) );
		if ( chrCd < "%uFF61" || chrCd > "%uFF9F" ) {
			dspMsg( "F0018", new Array( objNm ) );
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthKanaOrCptlAlphbtOrDgtOrSgnChk( str, objNm )
 *機能　：半角カナ大英字数字記号チェック
 ***********************************************************/
function hlfwdthKanaOrCptlAlphbtOrDgtOrSgnChk( str, objNm ) {
	var chrCd = "";
	
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = escapeUnicodeAll( str.charAt( i ) );
		if (
			chrCd == "%u0022" ||							// """
			chrCd == "%u0027" ||							// "'"
			chrCd == "%u002C" ||							// ","
			( chrCd >= "%u0061" && chrCd <= "%u007A" ) ||
			(
				!( chrCd >= "%u0020" && chrCd <= "%u007E" ) &&
				!( chrCd >= "%uFF61" && chrCd <= "%uFF9F" )
			)
		) {
			dspMsg( "F0020", new Array( objNm ) );
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthKanaOrAlphbtOrDgtChk( str, objNm )
 *機能　：半角カナ英字数字チェック
 ***********************************************************/
function hlfwdthKanaOrAlphbtOrDgtChk( str, objNm ) {
	var chrCd = "";
	
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = escapeUnicodeAll( str.charAt( i ) );
		if (
			
			!( chrCd >= "%u0030" && chrCd <= "%u0039" ) &&
			!( chrCd >= "%u0041" && chrCd <= "%u005A" ) &&
			!( chrCd >= "%u0061" && chrCd <= "%u007A" ) &&
			!( chrCd >= "%uFF61" && chrCd <= "%uFF9F" )
			
		) {
			dspMsg( "F0023", new Array( objNm ) );
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：dblwdthKatakanaChk( str, objNm )
 *機能　：全角カタカナチェック
 ***********************************************************/
function dblwdthKatakanaChk( str, objNm ) {
	var chrCd = "";
	
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = escapeUnicodeAll( str.charAt( i ) );
		if ( !( chrCd >= "%u30A1" && chrCd <= "%u30F6" ) && chrCd != "%u30FC" ) {
			dspMsg( "F0009", new Array( objNm ) );
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：frbddnChrChk( str, objNm )
 *機能　：使用禁止文字チェック
 ***********************************************************/
function frbddnChrChk( str, objNm ) {
	if ( !ALLW_HLF_WDTH_KANA_FLG && !hlfwdthKanaNtExstChk( str, objNm ) ) {
		return false;
	}
	var err_sw = 0;
	var chrCd = "";
	// 1文字ずつ取り出して判断する。
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = escapeUnicodeAll( str.charAt( i ) );
//alert( str.charAt( i ) + ":" + chrCd );
		// ＯＫ
		if (
			chrCd == "%u000A" ||							// "\n" 改行
			chrCd == "%u000D" ||							// "\r" 改行
			chrCd == "%uFF0D" ||							// "−" マイナス符号
			chrCd == "%uFF5E"								// "〜" 波線
		) {
			continue;
		}
		// エラー
		if (
			chrCd == "%u0022" ||							// """
			chrCd == "%u0027" ||							// "'"
			chrCd == "%u002C" ||							// ","
			chrCd == "%u00A8" ||							// "¨"
			chrCd == "%u00A7" ||							// "§"
			chrCd == "%u00B0" ||							// "°"
			chrCd == "%u00B4" ||							// "´"
			chrCd == "%u00B6" ||							// "¶"
			chrCd == "%u2116" ||							// "?"
			chrCd == "%u2121" ||							// "?"
			( chrCd >= "%u2150" && chrCd <= "%u218F" ) ||	// ローマ数字など
			chrCd == "%u2211" ||							// "?"
			chrCd == "%u221F" ||							// "?"
			chrCd == "%u2225" ||							// "‖" 二重縦線
			chrCd == "%u222E" ||							// "?"
			chrCd == "%u22BF" ||							// "?"
			( chrCd >= "%u2460" && chrCd <= "%u24FF" ) ||	// 囲み数字（まる囲み数字）など
			chrCd == "%u301D" ||							// "?" ダブルクォーテーションみたいな記号
			chrCd == "%u301F" ||							// "?" ダブルクォーテーションみたいな記号
			( chrCd >= "%u3220" && chrCd <= "%u32FF" ) ||	// 囲み文字（かっこ付株）など
			( chrCd >= "%u3300" && chrCd <= "%u4DFF" ) ||	// 年号、単位など
			(
				!ALLW_HLF_WDTH_KANA_FLG && 
				chrCd >= "%uFF61" && chrCd <= "%uFF9F"		// 半角カナ
			) ||
			chrCd == "%uFF02" ||							// "?" ダブルクォーテーションみたいな記号
			chrCd == "%uFF07" ||							// "?" シングルクォーテーションみたいな記号
			chrCd == "%uFFE0" ||							// "¢" セント記号
			chrCd == "%uFFE1" ||							// "£" ポンド記号
			chrCd == "%uFFE2" ||							// "¬" ノット記号
			chrCd == "%uFFE4"								// "?"
		) {
			dspMsg( "F0006", new Array( objNm ) );
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：figureChk( str, lngth, objNm )
 *機能　：桁チェック
 ***********************************************************/
function figureChk( str, lngth, objNm ) {
	if ( str.length != lngth ) {
		dspMsg( "F0007", new Array( objNm, String( lngth ) ) );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：strngLngthChk( str, maxLngth, objNm )
 *機能　：文字数チェック
 ***********************************************************/
function strngLngthChk( str, maxLngth, objNm ) {
	if ( str.length > maxLngth ) {
		dspMsg( "F0010", new Array( objNm, String( lngth ) ) );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：dtChk( str, objNm )
 *機能　：日付チェック
 ***********************************************************/
function dtChk( str, objNm ) {
	var dtPttrn = /^(\d{4})\/(\d{2})\/(\d{2})$/;
	if ( !dtSlshFlg ) {
		dtPttrn = /^(\d{4})(\d{2})(\d{2})$/;
	}
	if ( str.match( dtPttrn ) ) {
		var yr = Number( RegExp.$1 );
		var mnth = Number( RegExp.$2 );
		var dy = Number( RegExp.$3 );
		// 年範囲チェック
		if ( yr < MIN_YEAR || yr > MAX_YEAR ) {
			dspMsg( "F0011", new Array( objNm, String( MIN_YEAR ), String( MAX_YEAR ) ) );
			return false;
		}
		// 月範囲チェック
		if ( mnth < 1 || mnth > 12 ) {
			dspMsg( "F0012", new Array( objNm ) );
			return false;
		}
		// 日範囲チェック
		if ( dy < 1 || dy > 31 ) {
			dspMsg( "F0013", new Array( objNm ) );
			return false;
		}
		if (
			( ( mnth == 4 || mnth == 6 || mnth == 9 || mnth == 11 ) && dy > 30 ) ||
			( mnth == 2 && dy > 29 )
		) {
			dspMsg( "F0016", new Array( objNm ) );
			return false;
		}
		// 閏年チェック
		if (
			mnth == 2 &&
			dy == 29 &&
			!( yr % 4 == 0 && ( yr % 100 != 0 || yr % 400 == 0 ) )
		) {
			dspMsg( "F0015", new Array( objNm ) );
			return false;
		}
	} else {
		if ( dtSlshFlg ) {
			dspMsg( "F0011", new Array( objNm, "YYYY/MM/DD" ) );
		} else {
			dspMsg( "F0011", new Array( objNm, "YYYYMMDD" ) );
		}
		return false;
	}
	return true;
}
