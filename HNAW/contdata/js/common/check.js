// *----------------------------------------------------------------------------
// * システム		:	共通
// * スクリプト		:	チェック
// * 会社名or所属名	:	株式会社ヴィクサス
// * 作成日			:	2006/03/03
// * 作成者			:	Hirata
// * *** 修正履歴 ***
// * No.	Date		Author		Description
// *----------------------------------------------------------------------------
var errObjArry = new Array();
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
 *関数名：errObjFcs( obj )
 *機能　：エラーオブジェクトフォーカス
 ***********************************************************/
function errObjFcs( obj ) {
	if ( errObjArry.length > 0 ) {
		objFcs( errObjArry[ 0 ] );
	}
}
/***********************************************************
 *関数名：errSt( obj, fcsFlg )
 *機能　：エラーセット
 ***********************************************************/
function errSt( obj, fcsFlg ) {
	if ( fcsFlg == undefined ) {
		fcsFlg = true;
	}
	if ( obj.style.backgroundColor != ERR_INPT_COLOR ) {
		errObjArry.unshift( obj );
	}
	obj.style.backgroundColor = ERR_INPT_COLOR;
	
	if ( fcsFlg ) {
		objFcs( obj );
	}
}
/***********************************************************
 *関数名：dltFrmErrObjArry( obj )
 *機能　：エラーオブジェクト配列から削除
 ***********************************************************/
function dltFrmErrObjArry( obj ) {
	for ( i in errObjArry ) {
		if ( errObjArry[ i ] == obj ) {
			errObjArry.splice( i, 1 );
			break;
		}
	}
}
/***********************************************************
 *関数名：errRst( obj, chngClrFlg )
 *機能　：エラーリセット
 ***********************************************************/
function errRst( obj, chngClrFlg ) {
	if ( chngClrFlg == undefined ) {
		chngClrFlg = true;
	}
	if ( obj.style.backgroundColor == ERR_INPT_COLOR ) {
		dltFrmErrObjArry( obj );
	}
	if (
		chngClrFlg &&
		(
			(
				( obj.tagName == "INPUT" || obj.tagName == "TEXTAREA" ) &&
				obj.value != obj.defaultValue
			) || 
			(
				obj.tagName == "SELECT" &&
				obj.selectedIndex >= 0 &&
				!obj.options[ obj.selectedIndex ].defaultSelected
			)
		)
	) {
		obj.style.backgroundColor = CHNG_INPT_COLOR;
	} else {
		obj.style.backgroundColor = NRML_INPT_COLOR;
	}
}
/***********************************************************
 *関数名：errRstWthDfltVl( obj, dfltVl, chngClrFlg )
 *機能　：エラーリセット（初期値を渡す）
 ***********************************************************/
function errRstWthDfltVl( obj, dfltVl, chngClrFlg ) {
	if ( chngClrFlg == undefined ) {
		chngClrFlg = true;
	}
	if ( obj.style.backgroundColor == ERR_INPT_COLOR ) {
		dltFrmErrObjArry( obj );
	}
	if (
		chngClrFlg &&
		(
			(
				( obj.tagName == "INPUT" || obj.tagName == "TEXTAREA" ) &&
				obj.value != dfltVl
			) || 
			(
				obj.tagName == "SELECT" &&
				obj.selectedIndex >= 0 &&
				obj.options[ obj.selectedIndex ].value != dfltVl
			)
		)
	) {
		obj.style.backgroundColor = CHNG_INPT_COLOR;
	} else {
		obj.style.backgroundColor = NRML_INPT_COLOR;
	}
}
/***********************************************************
 *関数名：initInpt( obj, dfltVlFlg )
 *機能　：インプット初期化
 ***********************************************************/
function initInpt( obj, dfltVlFlg ) {
	if ( dfltVlFlg == undefined ) {
		dfltVlFlg = true;
	}
	if ( dfltVlFlg ) {
		if ( obj.tagName == "INPUT" || obj.tagName == "TEXTAREA" ) {
			obj.value = obj.defaultValue;
		} else if ( obj.tagName == "SELECT" ) {
			obj.selectedIndex = -1;
			var lngth = obj.options.length;
			for ( var i = 0; i < lngth; i++ ) {
				if ( obj.options[ i ].defaultSelected ) {
					obj.selectedIndex = i;
					break;
				}
			}
		}
	} else {
		obj.value = "";
	}
	errRst( obj );
}
/***********************************************************
 *関数名：initInptFrDlt( obj, actnDltFlg )
 *機能　：削除用インプット初期化
 ***********************************************************/
function initInptFrDlt( obj, actnDltFlg ) {
	if ( actnDltFlg ) {
		initInpt( obj );
		obj.style.backgroundColor = DEL_INPT_COLOR;
		obj.disabled = true;
	} else {
		errRst( obj )
		obj.disabled = false;
	}
}
/***********************************************************
 *関数名：initInptWthDfltVl( obj, dfltVl, dfltVlFlg )
 *機能　：インプット初期化（初期値を渡す）
 ***********************************************************/
function initInptWthDfltVl( obj, dfltVl, dfltVlFlg ) {
	if ( dfltVlFlg == undefined ) {
		dfltVlFlg = true;
	}
	if ( dfltVlFlg ) {
		if ( obj.tagName == "INPUT" || obj.tagName == "TEXTAREA" ) {
			obj.value = dfltVl;
		} else if ( obj.tagName == "SELECT" ) {
			obj.selectedIndex = -1;
			setSlctByVl( obj, dfltVl )
		}
	} else {
		obj.value = "";
	}
	errRstWthDfltVl( obj, dfltVl );
}
/***********************************************************
 *関数名：initInptFrDltWthDfltVl( obj, dfltVl, actnDltFlg )
 *機能　：削除用インプット初期化（初期値を渡す）
 ***********************************************************/
function initInptFrDltWthDfltVl( obj, dfltVl, actnDltFlg ) {
	if ( actnDltFlg ) {
		initInptWthDfltVl( obj, dfltVl );
		obj.style.backgroundColor = DEL_INPT_COLOR;
		obj.disabled = true;
	} else {
		errRstWthDfltVl( obj, dfltVl )
		obj.disabled = false;
	}
}
/***********************************************************
 *関数名：isErrInpt( obj )
 *機能　：インプットがエラーかどうか
 ***********************************************************/
function isErrInpt( obj ) {
	return ( obj.style.backgroundColor == ERR_INPT_COLOR );
}
/***********************************************************
 *関数名：rmnErrChk( obj, alrtFlg, fcsFlg )
 *機能　：エラー残チェック
 ***********************************************************/
function rmnErrChk( obj, alrtFlg, fcsFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( fcsFlg == undefined ) {
		fcsFlg = true;
	}
	if ( obj.style.backgroundColor == ERR_INPT_COLOR ) {
		if ( alrtFlg ) {
			dspMsg( "F0000" );
		}
		if ( fcsFlg ) {
			objFcs( obj );
		}
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：rmnErrCntrChk( alrtFlg, fcsFlg )
 *機能　：エラーカウンタ残チェック
 ***********************************************************/
function rmnErrCntrChk( alrtFlg, fcsFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( fcsFlg == undefined ) {
		fcsFlg = true;
	}
	if ( errObjArry.length > 0 ) {
		if ( alrtFlg ) {
			dspMsg( "F0000" );
		}
		if ( fcsFlg ) {
			errObjFcs();
		}
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：necChk( obj, alrtFlg )
 *機能　：必須チェック
 ***********************************************************/
function necChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !obj.disabled && obj.value == "" ) {
		if ( alrtFlg ) {
			dspMsg( "F0012" );
			errSt( obj );
		}
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：filePathInptChk( obj, alrtFlg )
 *機能　：ファイルパスインプットチェック
 ***********************************************************/
function filePathInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !filePathChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：filePathChk( str, alrtFlg )
 *機能　：ファイルパスチェック
 ***********************************************************/
function filePathChk( str, alrtFlg ) {
	var arry = sliceFilePath( str );
	var drive = arry[0];
	var path = arry[1];
	var file = arry[2];
	var ext = arry[3];
	
	if ( path == "" ) {
		if ( alrtFlg ) {
			dspMsg( "F0002" );
		}
		return false;
	}
	if ( file == "" ) {
		if ( alrtFlg ) {
			dspMsg( "F0003" );
		}
		return false;
	}
	if ( ext == "" ) {
		if ( alrtFlg ) {
			dspMsg( "F0004" );
		}
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：numrcInptChk( obj, alrtFlg )
 *機能　：数値インプットチェック
 ***********************************************************/
function numrcInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !numrcChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：numrcChk( str, alrtFlg )
 *機能　：数値チェック
 ***********************************************************/
function numrcChk( str, alrtFlg ) {
	if ( isNaN( str ) ) {
		if ( alrtFlg ) {
			dspMsg( "F0006" );
		}
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：intgrInptChk( obj, alrtFlg )
 *機能　：整数インプットチェック
 ***********************************************************/
function intgrInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !intgrChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：intgrChk( str, alrtFlg )
 *機能　：整数チェック
 ***********************************************************/
function intgrChk( str, alrtFlg ) {
	if ( str.match( /\D/ ) ) {
		if ( alrtFlg ) {
			dspMsg( "F0033" );
		}
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthAlphbtOrDgtInptChk( obj, alrtFlg )
 *機能　：半角英数字インプットチェック
 ***********************************************************/
function hlfwdthAlphbtOrDgtInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !hlfwdthAlphbtOrDgtChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthAlphbtOrDgtChk( str, alrtFlg )
 *機能　：半角英数字チェック
 ***********************************************************/
function hlfwdthAlphbtOrDgtChk( str, alrtFlg ) {
	if ( str != "" ) {
		// 1文字ずつ取り出して全角か判断する。
		for ( var i = 0; i < str.length; i++ ) {
			if ( escape( str.charAt( i ) ).length >= 4 ) {
				if ( alrtFlg ) {
					dspMsg( "F0005" );
				}
				return false;
			}
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthCptlAlphbtOrDgtOrSgnInptChk( obj, alrtFlg )
 *機能　：半角大英字数字記号インプットチェック
 ***********************************************************/
function hlfwdthCptlAlphbtOrDgtOrSgnInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !hlfwdthCptlAlphbtOrDgtOrSgnChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthCptlAlphbtOrDgtOrSgnChk( str, alrtFlg )
 *機能　：半角大英字数字記号チェック
 ***********************************************************/
function hlfwdthCptlAlphbtOrDgtOrSgnChk( str, alrtFlg ) {
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
			if ( alrtFlg ) {
				dspMsg( "F0030" );
			}
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthCptlAlphbtOrDgtInptChk( obj, alrtFlg )
 *機能　：半角大英字数字インプットチェック
 ***********************************************************/
function hlfwdthCptlAlphbtOrDgtInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !hlfwdthCptlAlphbtOrDgtChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthCptlAlphbtOrDgtChk( str, alrtFlg )
 *機能　：半角大英字数字チェック
 ***********************************************************/
function hlfwdthCptlAlphbtOrDgtChk( str, alrtFlg ) {
	var chrCd = "";
	
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = escapeUnicodeAll( str.charAt( i ) );
		if (
			!( chrCd >= "%u0030" && chrCd <= "%u0039" ) &&
			!( chrCd >= "%u0041" && chrCd <= "%u005A" )
		) {
			if ( alrtFlg ) {
				dspMsg( "F0031" );
			}
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthInptChk( obj, alrtFlg )
 *機能　：半角インプットチェック
 ***********************************************************/
function hlfwdthInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !hlfwdthChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthChk( str, alrtFlg )
 *機能　：半角チェック
 ***********************************************************/
function hlfwdthChk( str, alrtFlg ) {
	if ( str != "" ) {
		var chrCd;
		for ( var i = 0; i < str.length; i++ ) {
			// 1文字ずつ取り出して半角か判断する。
			chrCd = escape( str.charAt( i ) );
			if (
				chrCd.length >= 4 &&
				( chrCd < "%uFF61" || chrCd > "%uFF9F" )
			) {
				if ( alrtFlg ) {
					dspMsg( "F0028" );
				}
				return false;
			}
		}
	}
	return true;
}
/***********************************************************
 *関数名：dblwdthInptChk( obj, alrtFlg )
 *機能　：全角インプットチェック
 ***********************************************************/
function dblwdthInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !dblwdthChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：dblwdthChk( str, alrtFlg )
 *機能　：全角チェック
 ***********************************************************/
function dblwdthChk( str, alrtFlg ) {
	if ( str != "" ) {
		var errFlg = false;
		if ( !hlfwdthKanaNtExstChk( str, false ) ) {
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
			if ( alrtFlg ) {
				dspMsg( "F0026" );
			}
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthKanaNtExstInptChk( obj, alrtFlg )
 *機能　：半角カナ非存在インプットチェック
 ***********************************************************/
function hlfwdthKanaNtExstInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !hlfwdthKanaNtExstChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthKanaNtExstChk( str, alrtFlg )
 *機能　：半角カナ非存在チェック
 ***********************************************************/
function hlfwdthKanaNtExstChk( str, alrtFlg ) {
	var chrCd = "";
	
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = escapeUnicodeAll( str.charAt( i ) );
		if ( chrCd >= "%uFF61" && chrCd <= "%uFF9F" ) {
			if ( alrtFlg ) {
				dspMsg( "F0009" );
			}
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthKanaInptChk( obj, alrtFlg )
 *機能　：半角カナインプットチェック
 ***********************************************************/
function hlfwdthKanaInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !hlfwdthKanaChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthKanaChk( str, alrtFlg )
 *機能　：半角カナチェック
 ***********************************************************/
function hlfwdthKanaChk( str, alrtFlg ) {
	var chrCd = "";
	
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = escapeUnicodeAll( str.charAt( i ) );
		if ( chrCd < "%uFF61" || chrCd > "%uFF9F" ) {
			if ( alrtFlg ) {
				dspMsg( "F0027" );
			}
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthKanaOrCptlAlphbtOrDgtOrSgnInptChk( obj, alrtFlg )
 *機能　：半角カナ大英字数字記号インプットチェック
 ***********************************************************/
function hlfwdthKanaOrCptlAlphbtOrDgtOrSgnInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !hlfwdthKanaOrCptlAlphbtOrDgtOrSgnChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthKanaOrCptlAlphbtOrDgtOrSgnChk( str, alrtFlg )
 *機能　：半角カナ大英字数字記号チェック
 ***********************************************************/
function hlfwdthKanaOrCptlAlphbtOrDgtOrSgnChk( str, alrtFlg ) {
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
			if ( alrtFlg ) {
				dspMsg( "F0029" );
			}
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthKanaOrAlphbtOrDgtInptChk( obj, alrtFlg )
 *機能　：半角カナ英字数字インプットチェック
 ***********************************************************/
function hlfwdthKanaOrAlphbtOrDgtInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !hlfwdthKanaOrAlphbtOrDgtChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：hlfwdthKanaOrAlphbtOrDgtChk( str, alrtFlg )
 *機能　：半角カナ英字数字チェック
 ***********************************************************/
function hlfwdthKanaOrAlphbtOrDgtChk( str, alrtFlg ) {
	var chrCd = "";
	
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = escapeUnicodeAll( str.charAt( i ) );
		if (
			
			!( chrCd >= "%u0030" && chrCd <= "%u0039" ) &&
			!( chrCd >= "%u0041" && chrCd <= "%u005A" ) &&
			!( chrCd >= "%u0061" && chrCd <= "%u007A" ) &&
			!( chrCd >= "%uFF61" && chrCd <= "%uFF9F" )
			
		) {
			if ( alrtFlg ) {
				dspMsg( "F0032" );
			}
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：dblwdthKatakanaInptChk( obj, alrtFlg )
 *機能　：全角カタカナインプットチェック
 ***********************************************************/
function dblwdthKatakanaInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !dblwdthKatakanaChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：dblwdthKatakanaChk( str, alrtFlg )
 *機能　：全角カタカナチェック
 ***********************************************************/
function dblwdthKatakanaChk( str, alrtFlg ) {
	var chrCd = "";
	
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = escapeUnicodeAll( str.charAt( i ) );
		if ( !( chrCd >= "%u30A1" && chrCd <= "%u30F6" ) && chrCd != "%u30FC" ) {
			if ( alrtFlg ) {
				dspMsg( "F0018" );
			}
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：frbddnChrInptChk( obj, alrtFlg )
 *機能　：使用禁止文字インプットチェック
 ***********************************************************/
function frbddnChrInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !frbddnChrChk( obj.value, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：frbddnChrChk( str, alrtFlg )
 *機能　：使用禁止文字チェック
 ***********************************************************/
function frbddnChrChk( str, alrtFlg ) {
	if ( !ALLW_HLF_WDTH_KANA_FLG && !hlfwdthKanaNtExstChk( str, alrtFlg ) ) {
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
			if ( alrtFlg ) {
				dspMsg( "F0010" );
			}
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：smNmInptRptChk( inptArry, setErrNo )
 *機能　：同名インプット重複チェック
 ***********************************************************/
function smNmInptRptChk( inptArry, setErrNo ) {
	if ( setErrNo == undefined ) {
		setErrNo = 0;
	}
	for ( var i = 0; i < inptArry.length; i++ ) {
		if ( inptArry[ i ].value != "" ) {
			for ( var j = i + 1; j < inptArry.length; j++ ) {
				if ( inptArry[ i ].value == inptArry[ j ].value ) {
					if (
						!inptArry[ i ].disabled && 
						( inptArry[ j ].disabled || ( setErrNo & 1 ) == 1 )
					) {
						errSt( inptArry[ i ] );
					}
					if (
						!inptArry[ j ].disabled && 
						( inptArry[ i ].disabled || ( setErrNo & 2 ) == 2 )
					) {
						errSt( inptArry[ j ] );
					}
					return false;
				}
			}
		}
	}
	return true;
}
/***********************************************************
 *関数名：nmbrInptOrdrChk( obj0, obj1, alrtFlg )
 *機能　：数値インプット順序チェック
 ***********************************************************/
function nmbrInptOrdrChk( obj0, obj1, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( obj0.value != "" && obj1.value != "" ) {
		if ( Number( obj0.value ) > Number( obj1.value ) ) {
			if ( alrtFlg ) {
				dspMsg( "F0014" );
			}
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：smNmNmbrInptOrdrChk( inptArry )
 *機能　：同名数値インプット順序チェック
 ***********************************************************/
function smNmNmbrInptOrdrChk( inptArry ) {
	var maxVal = null;
	var val;
	for ( var i = 0; i < inptArry.length; i++ ) {
		val = inptArry[ i ].value;
		if ( val != "" ) {
			if ( maxVal != null && Number( maxVal ) >= Number( val ) ) {
				return false;
			} else {
				maxVal = val;
			}
		}
	}
	return true;
}
/***********************************************************
 *関数名：figureInptChk( obj, lngth, alrtFlg )
 *機能　：桁インプットチェック
 ***********************************************************/
function figureInptChk( obj, lngth, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !figureLngthChk( obj.value, lngth, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：figureLngthChk( str, lngth, alrtFlg )
 *機能　：桁チェック
 ***********************************************************/
function figureLngthChk( str, lngth, alrtFlg ) {
	if ( str.length != lngth ) {
		if ( alrtFlg ) {
			dspMsg( "F0011", new Array( String( lngth ) ) );
		}
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：strngLngthInptChk( obj, maxLngth, alrtFlg )
 *機能　：文字数インプットチェック
 ***********************************************************/
function strngLngthInptChk( obj, maxLngth, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( !strngLngthChk( obj.value, maxLngth, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：strngLngthChk( str, maxLngth, alrtFlg )
 *機能　：文字数チェック
 ***********************************************************/
function strngLngthChk( str, maxLngth, alrtFlg ) {
	if ( str.length > maxLngth ) {
		if ( alrtFlg ) {
			dspMsg( "F0019", new Array( String( maxLngth ) ) );
		}
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：dtInptChk( obj, alrtFlg )
 *機能　：日付インプットチェック
 ***********************************************************/
function dtInptChk( obj, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( DATE_SLASH_FLG ) {
		obj.value = insertDtSlsh( obj.value );
	}
	if ( !dtChk( obj.value, DATE_SLASH_FLG, alrtFlg ) ) {
		errSt( obj );
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：dtChk( str, dtSlshFlg, alrtFlg )
 *機能　：日付チェック
 ***********************************************************/
function dtChk( str, dtSlshFlg, alrtFlg ) {
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
			if ( alrtFlg ) {
				dspMsg( "F0021", new Array( String( MIN_YEAR ), String( MAX_YEAR ) ) );
			}
			return false;
		}
		// 月範囲チェック
		if ( mnth < 1 || mnth > 12 ) {
			if ( alrtFlg ) {
				dspMsg( "F0022" );
			}
			return false;
		}
		// 日範囲チェック
		if ( dy < 1 || dy > 31 ) {
			if ( alrtFlg ) {
				dspMsg( "F0023" );
			}
			return false;
		}
		if (
			( ( mnth == 4 || mnth == 6 || mnth == 9 || mnth == 11 ) && dy > 30 ) ||
			( mnth == 2 && dy > 29 )
		) {
			if ( alrtFlg ) {
				dspMsg( "F0024" );
			}
			return false;
		}
		// 閏年チェック
		if (
			mnth == 2 &&
			dy == 29 &&
			!( yr % 4 == 0 && ( yr % 100 != 0 || yr % 400 == 0 ) )
		) {
			if ( alrtFlg ) {
				dspMsg( "F0025" );
			}
			return false;
		}
	} else {
		if ( alrtFlg ) {
			if ( dtSlshFlg ) {
				dspMsg( "F0020", new Array( "YYYY/MM/DD" ) );
			} else {
				dspMsg( "F0020", new Array( "YYYYMMDD" ) );
			}
		}
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：dtRngInptChk( obj0, obj1, allwEqlFlg, alrtFlg )
 *機能　：日付範囲インプットチェック
 ***********************************************************/
function dtRngInptChk( obj0, obj1, allwEqlFlg, alrtFlg ) {
	if ( alrtFlg == undefined ) {
		alrtFlg = true;
	}
	if ( obj0.value != "" && !dtInptChk( obj0, alrtFlg ) ) {
		return false;
	}
	if ( obj1.value != "" && !dtInptChk( obj1, alrtFlg ) ) {
		return false;
	}
	if (
		obj0.value != "" &&
		obj1.value != "" &&
		!dtRngChk( obj0.value, obj1.value, alrtFlg, allwEqlFlg )
	) {
		return false;
	}
	return true;
}
/***********************************************************
 *関数名：dtRngChk( str0, str1, alrtFlg, allwEqlFlg )
 *機能　：日付範囲チェック
 ***********************************************************/
function dtRngChk( str0, str1, alrtFlg, allwEqlFlg ) {
	if ( allwEqlFlg == undefined ) {
		allwEqlFlg = true;
	}
	var nmbr0 = str0;
	var nmbr1 = str1;
	if ( DATE_SLASH_FLG ) {
		nmbr0 = removeSlsh( nmbr0 );
		nmbr1 = removeSlsh( nmbr1 );
	}
	nmbr0 = Number( nmbr0 );
	nmbr1 = Number( nmbr1 );
	if ( allwEqlFlg ) {
		if ( nmbr0 > nmbr1 ) {
			if ( alrtFlg ) {
				dspMsg( "F0016" );
			}
			return false;
		}
	} else {
		if ( nmbr0 >= nmbr1 ) {
			if ( alrtFlg ) {
				dspMsg( "F0017" );
			}
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：isEqlPrvVl( obj, prvObj, sbstttFlg )
 *機能　：以前の値と等しいか
 ***********************************************************/
function isEqlPrvVl( obj, prvObj, sbstttFlg ) {
	if ( sbstttFlg == undefined ) {
		sbstttFlg = true;
	}
	var rslt = true;
	if ( obj.value != prvObj.value ) {
		if ( sbstttFlg ) {
			prvObj.value = obj.value;
		}
		rslt = false;
	}
	return rslt;
}
