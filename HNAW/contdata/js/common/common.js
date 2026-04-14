// *----------------------------------------------------------------------------
// * システム		:	共通
// * スクリプト		:	共通
// * 会社名or所属名	:	株式会社ヴィクサス
// * 作成日			:	2006/03/03
// * 作成者			:	Hirata
// * *** 修正履歴 ***
// * No.	Date		Author		Description
// *----------------------------------------------------------------------------
document.onkeydown		=	keyDownPrcss;
document.oncontextmenu	=	function() { return !DSABL_CNTXT_MN; };

var trnstnFlg			=	false;
/***********************************************************
 *関数名：unloadWindow()
 *機能　：画面遷移時にセッション終了処理を呼ぶ
 ***********************************************************/
function unloadWindow()
{
	if ( DO_RMV_SSSN_ATTRBT && !trnstnFlg ) {
		removeSssnAttrbt();
	}
}
/***********************************************************
 *関数名：removeSssnAttrbt()
 *機能　：セッションを終了する
 ***********************************************************/
function removeSssnAttrbt()
{
	if ( USE_AJAX_FR_RMV_S_A ) {
		var prmArry = new Array();
		prmArry[ "sssnArgNm" ] = SESSION_ARG_NM;
		sendAjaxRqst( RMV_S_A_SRVLT_AJAX, prmArry, null, true );
	} else {
		var winNm = SESSION_ARG_NM + "_rmv";
		var ApWindow = window.open( '', winNm, OPEN_WINDOW_PRMTR );
		ApWindow.document.write( '<html>' );
		ApWindow.document.write( '<html><head><title>セッション変数削除</title></head>' );
		ApWindow.document.write( '<body><form name="form1" target="_self"></form></body>' );
		ApWindow.document.write( '</html>' );
		ApWindow.document.form1.action= RMV_S_A_SRVLT + "?&sssnArgNm=" + SESSION_ARG_NM;
		ApWindow.document.form1.method = "POST";
		ApWindow.document.form1.submit();
	}
}
/***********************************************************
 *関数名：windowClose()
 *機能　：画面を終了する
 ***********************************************************/
function windowClose() {
	top.close();
}
/***********************************************************
 *関数名：sliceFilePath( str )
 *機能　：ファイルパスを分割する
 ***********************************************************/
function sliceFilePath( str ) {
	var savePath = str;
	var drive = "";
	var path = "";
	var file = "";
	var ext = "";
	while ( str.indexOf( "\\" ) != -1 ) {
		str = str.slice( str.indexOf( "\\" ) + 1 );
	}
	drive = savePath.slice( 0, savePath.indexOf( ":" ) );
	if ( str != "" ) {
		path = savePath.slice( 0, savePath.indexOf( str ) );
	} else {
		path = savePath;
	}
	if ( str.indexOf( "." ) != -1 ) {
		file = str.slice( 0, str.lastIndexOf( "." ) );
		ext = str.slice( str.lastIndexOf( "." ) );
	} else {
		file = str;
	}
	return new Array( drive, path, file, ext );
}
/***********************************************************
 *関数名：enableLnk( obj )
 *機能　：リンクを押下可能にする
 ***********************************************************/
function enableLnk( obj ) {
	if ( obj.innerHTML.match( /(<A.*>)(<\/A>)(.*)/im ) ) {
		var hdr = RegExp.$1;
		var ftr = RegExp.$2;
		var bdy = RegExp.$3;
		obj.innerHTML = hdr + bdy + ftr;
	}
}
/***********************************************************
 *関数名：disableLnk( obj )
 *機能　：リンクを押下不可にする
 ***********************************************************/
function disableLnk( obj ) {
	if ( obj.innerHTML.match( /(<A.*>)(.*)(<\/A>)/im ) ) {
		var hdr = RegExp.$1;
		var bdy = RegExp.$2;
		var ftr = RegExp.$3;
		obj.innerHTML = hdr + ftr + bdy;
	}
}
/***********************************************************
 *関数名：addMsgToMsgBox( str )
 *機能　：メッセージボックスにメッセージを追加する
 ***********************************************************/
function addMsgToMsgBox( str ) {
	var msg = str;
	
	var reg = new RegExp( MSG_BOX_LN_FD_CD, "i" );
	for ( ; msg.match( reg ); ) {
		msg = RegExp.leftContext + DSP_LN_FD_CD + RegExp.rightContext;
	}
	
	if ( document.all.msgBox.value == "" ) {
		document.all.msgBox.value = msg;
	} else {
		document.all.msgBox.value = document.all.msgBox.value + DSP_LN_FD_CD + msg;
	}
}
/***********************************************************
 *関数名：getRdoVl( objNm )
 *機能　：ラジオボタンの値を取得する
 ***********************************************************/
function getRdoVl( objNm ) {
	var rslt = null;
	var rdoBttns = document.getElementsByName( objNm );
	var lngth = rdoBttns.length;
	for ( var i = 0; i < lngth; i++ ) {
		if ( rdoBttns[ i ].checked ) {
			rslt = rdoBttns[ i ].value;
			break;
		}
	}
	return rslt;
}
/***********************************************************
 *関数名：setRdoByVl( objNm, vl, dfltSlctIdx )
 *機能　：vlの値を持つラジオボタンを選択する
 ***********************************************************/
function setRdoByVl( objNm, vl, dfltSlctIdx ) {
	var slctIdx = dfltSlctIdx;
	var rdoBttns = document.getElementsByName( objNm );
	var lngth = rdoBttns.length;
	for ( var i = 0; i < lngth; i++ ) {
		if ( rdoBttns[ i ].value == vl ) {
			slctIdx = i;
			break;
		}
	}
	rdoBttns[ slctIdx ].checked = true;
}
/***********************************************************
 *関数名：setSlctByVl( obj, vl )
 *機能　：vlの値を持つoptionを選択する
 ***********************************************************/
function setSlctByVl( obj, vl ) {
	if ( vl != null ) {
		obj.value = vl;
	} else {
		obj.selectedIndex = -1;
	}
}
/***********************************************************
 *関数名：getOptnTxtByVl( obj, vl )
 *機能　：vlの値を持つoptionのtextを取得する
 ***********************************************************/
function getOptnTxtByVl( obj, vl ) {
	var lngth = obj.options.length;
	for ( var i = 0; i < lngth; i++ ) {
		if ( obj.options[ i ].value == vl ) {
			return obj.options[ i ].text;
		}
	}
}
/***********************************************************
 *関数名：getIdxByVl( obj, vl )
 *機能　：vlの値を持つoptionのindexを取得する
 ***********************************************************/
function getIdxByVl( obj, vl ) {
	var idx = -1;
	var lngth = obj.options.length;
	for ( var i = 0; i < lngth; i++ ) {
		if ( obj.options[ i ].value == vl ) {
			idx = i;
			break;
		}
	}
	return idx;
}
/***********************************************************
 *関数名：makeSlctTg( nm, clssNm, optnArry, vl, sw, attrbtStrng )
 *機能　：selectタグの文字列を作る
 ***********************************************************/
function makeSlctTg( nm, clssNm, optnArry, vl, sw, attrbtStrng ) {
	var slctStr = '<select class="' + clssNm + '" name="' + nm + '"';
	if ( attrbtStrng != undefined ) {
		slctStr += ' ' + attrbtStrng;
	}
	slctStr += '>';
	if ( sw == 1 ) {
		slctStr += '<option value=""';
		if ( vl == "" ) {
			slctStr += ' selected';
		}
		slctStr += '></option>';
	}
	for ( var i = 0; i < optnArry.length; i++ ) {
		slctStr += '<option value="' + optnArry[ i ][ 0 ] +'"';
		if ( vl == optnArry[ i ][ 0 ] ) {
			slctStr += ' selected';
		}
		slctStr += '>' + optnArry[ i ][ 1 ] + '</option>';
	}
	slctStr += '</select>';
	return slctStr;
}
/***********************************************************
 *関数名：insertDtSlsh( ymd )
 *機能　：'/'挿入
 ***********************************************************/
function insertDtSlsh( ymd ) {
	if ( isNaN( ymd ) || String( ymd ).length != 8 ) {
		return ymd;
	} else {
		var year = String( ymd ).substring( 0, 4 );
		var month = String( ymd ).substring( 4, 6 );
		var day	 = String( ymd ).substring( 6, 8 );
		return ( year + '/' + month + '/' + day );
	}
}
/***********************************************************
 *関数名：removeSlsh( ymd )
 *機能　：'/'除去
 ***********************************************************/
function removeSlsh( ymd ) {
	var str = ymd;
	while ( str.match( /(.*)\/(.*)/ ) ) {
		str = RegExp.$1 + RegExp.$2;
	}
	return str;
}
/***********************************************************
 *関数名：fillZero( cd, lngth )
 *機能　：0埋め
 ***********************************************************/
function fillZero( cd, lngth ) {
	var str = String( cd );
	while ( str.length < lngth ) {
		str = "0" + str;
	}
	return str;
}
/***********************************************************
 *関数名：fillZeroToInpt( obj, lngth ) {
 *機能　：inputのvalueに0埋めする。
 ***********************************************************/
function fillZeroToInpt( obj, lngth ) {
	if ( obj.style.backgroundColor != ERR_INPT_COLOR && obj.value != "" ) {
		obj.value = fillZero( obj.value, lngth );
	}
}
/***********************************************************
 *関数名：delZero( cd )
 *機能　：前0削除
 ***********************************************************/
function delZero( cd ) {
	var str = String( cd );
	while ( str.length > 1 && str.substring( 0, 1 ) == "0" ) {
		str = str.substring( 1 );
	}
	return str;
}
/***********************************************************
 *関数名：delZeroToInpt( obj ) {
 *機能　：inputのvalueに前0削除する。
 ***********************************************************/
function delZeroToInpt( obj ) {
	if ( obj.style.backgroundColor != ERR_INPT_COLOR && obj.value != "" ) {
		obj.value = delZero( obj.value );
	}
}
/***********************************************************
 *関数名：keyDownPrcss()
 *機能　：keyDownの処理
 ***********************************************************/
function keyDownPrcss() {
	var flag = 1;				// OK:1, NG: 0
	var code = event.keyCode;
	switch ( code ) {
		case	8:				// BackSpace
			if (
				!(
					event.srcElement.tagName == "INPUT" &&
					event.srcElement.readOnly == false &&
					event.srcElement.type == "text"
				) &&
				!(
					event.srcElement.tagName == "INPUT" &&
					event.srcElement.readOnly == false &&
					event.srcElement.type == "file"
				) &&
				!(
					event.srcElement.tagName == "TEXTAREA" &&
					event.srcElement.readOnly == false
				) 
			) {
				flag = 0;
			}
		break;
		case	13:				// Enter
			if (
				RPLC_ENTR_WTH_TB_FLG &&
				!(
					event.srcElement.tagName == "INPUT" &&
					event.srcElement.type == "file"
				)
			) {
				event.keyCode = 9;
			}
		break;
		case	37:				// Alt+←
			if ( event.altKey ) {
				flag = 0;
			}
		break;
		case	39:				// Alt+→
			if ( event.altKey ) {
				flag = 0;
			}
		case	66:				// ctrl+B
		case	68:				// ctrl+D
		case	69:				// ctrl+E
		case	72:				// ctrl+H
		case	73:				// ctrl+I
		case	76:				// ctrl+L
		case	78:				// ctrl+N
		case	79:				// ctrl+O
		case	82:				// ctrl+R, r
		case	87:				// ctrl+W
		case	90:				// ctrl+Z
			if (event.ctrlKey) {
				event.keyCode = null;
				flag = 0;
			}
		break;
		case	116:			// F5
		case	122:			// F11
			flag = 0;
			if (
				event.srcElement.tagName != "INPUT" ||
				event.srcElement.type != "file"
			) {
				event.keyCode = null;
			}
		break;
	}
	if ( flag == 0 ) {
		return false;
	} else {
		return true;
	}
}
/***********************************************************
 *関数名：addComma( 入力値 )
 *機能　：入力値に３桁カンマを付与する。
 *使用方法：以下のようにdelCommaと一緒に使う。
 * <input name="money" type="text" value="10,000"
 * onFocus="this.value=delComma(this.value);"
 *  onBlur="this.value=addComma(this.value);"
 * onchange="xxxx(this.value);">
 ***********************************************************/
function addComma(value) {
	var i;
	for(i = 0; i < value.length/3; i++) {
		value = value.replace(/^([+-]?\d+)(\d\d\d)/,"$1,$2");
	}
	return value;
}
/***********************************************************
 *関数名：addCommaToInpt( obj )
 *機能　：inputのvalueに３桁カンマを付与する。
 ***********************************************************/
function addCommaToInpt( obj ) {
	if ( obj.style.backgroundColor != ERR_INPT_COLOR ) {
		obj.value = addComma( obj.value );
	}
}
/***********************************************************
 *関数名：delComma( 入力値 )
 *機能　：入力値から３桁カンマを削除する。
 *使用方法：以下のようにaddCommaと一緒に使う。
 * <input name="money" type="text" value="10,000"
 * onFocus="this.value=delComma(this.value);"
 *  onBlur="this.value=addComma(this.value);"
 * onchange="xxxx(this.value);">
 ***********************************************************/
function delComma(value) {
	return value.split(",").join("")
}
/***********************************************************
 *関数名：delCommaToInpt( obj )
 *機能　：inputのvalueから３桁カンマを削除する。
 ***********************************************************/
function delCommaToInpt( obj ) {
	if ( obj.style.backgroundColor != ERR_INPT_COLOR ) {
		obj.value = delComma( obj.value );
	}
}
/***********************************************************
 *関数名：escapeUnicodeAll( str )
 *機能　：Unicodeで全ての文字をエスケープする。
 ***********************************************************/
function escapeUnicodeAll( str ) {
	var result = "";
	for ( var i = 0; i < str.length; i++ ) {
		chrCd = str.charCodeAt( i ).toString( 16 ).toUpperCase();
		while ( chrCd.length < 4 ) {
			chrCd = "0" + chrCd;
		}
		chrCd = "%u" + chrCd;
		result += chrCd;
	}
	return result;
}
/***********************************************************
 *関数名：screenResize()
 *機能　：端末解像度に合わせて最大化サイズを決定する。
 *        解像度がWINDOW_WIDTH、WINDOW_HEIGHTの標準値以下
 *        の場合のみ上記の適用をする。
 ***********************************************************/
function screenResize( str ) {
	if ( WINDOW_WIDTH > screen.availWidth ) {
		WINDOW_WIDTH = screen.availWidth;
	}
	if ( WINDOW_HEIGHT > screen.availHeight ) {
		WINDOW_HEIGHT = screen.availHeight;
	}
	resizeTo( WINDOW_WIDTH, WINDOW_HEIGHT );
}
/***********************************************************
 *関数名：Kanji2Null(in_data)
 *機能　：漢字文字列を空文字（""）に置き換える
 *コールフォーマット：wk = Kanji2Null(in_data);
 *                                     |
 *                                     +----->入力文字列（文字列）
 *※半角英数字カナは、そのまま出力。
 ***********************************************************/
function Kanji2Null(in_data) {
	var chrCd;
	var out_str = "";
	// 1文字ずつ取り出して半角か判断する。
	// 半角以外は、空置換え
	for(i=0; i<in_data.length; i++) {
		wk=in_data.substr(i,1);
		chrCd = escape( in_data.charAt( i ) );
		if (chrCd.length >= 4 &&
			( chrCd < "%uFF61" || chrCd > "%uFF9F" ) ) {
			wk = "";
		}
		out_str += wk;
	}
	return out_str;
}
/***********************************************************
 *関数名：getDateObject( yyyymmdd )
 *機能　：yyyymmddから日付オブジェクトを取得する。
 ***********************************************************/
function getDateObject( yyyymmdd ) {
	return new Date( yyyymmdd.substring( 0, 4 ), yyyymmdd.substring( 4, 6 ) - 1, yyyymmdd.substring( 6, 8 ) );
}
/***********************************************************
 *関数名：getDys( dt0, dt1, incldStrtDtFlg )
 *機能　：日付間の日数取得
 ***********************************************************/
function getDys( dt0, dt1, incldStrtDtFlg ) {
	if ( incldStrtDtFlg == undefined ) {
		incldStrtDtFlg = false;
	}
	var yyyymmdd0 = dt0;
	var yyyymmdd1 = dt1;
	if ( DATE_SLASH_FLG ) {
		yyyymmdd0 = removeSlsh( yyyymmdd0 );
		yyyymmdd1 = removeSlsh( yyyymmdd1 );
	}
	var dys = ( getDateObject( yyyymmdd1 ).getTime() - getDateObject( yyyymmdd0 ).getTime() ) / ( 1000 * 60 * 60 * 24 );
	if ( incldStrtDtFlg && dys > -1 ) {
		dys++;
	}
	return dys;
}
/***********************************************************
 *関数名：getDysFrmInpt( obj0, obj1, incldStrtDtFlg )
 *機能　：インプットから日付間の日数取得
 ***********************************************************/
function getDysFrmInpt( obj0, obj1, incldStrtDtFlg ) {
	if ( incldStrtDtFlg == undefined ) {
		incldStrtDtFlg = false;
	}
	var rslt;
	if ( !isErrInpt( obj0 ) && !isErrInpt( obj1 ) && obj0.value != "" && obj1.value != "" ) {
		rslt = getDys( obj0.value, obj1.value, incldStrtDtFlg );
	}
	return rslt;
}
/***********************************************************
 *関数名：formatNumber( nmbr, fgr )
 *機能　：nmbrを小数点以下fgr桁の数字にする。
 ***********************************************************/
function formatNumber( nmbr, fgr ) {
	if ( !isNaN( nmbr ) && fgr >= 0 ) {
		nmbr = String( nmbr );
		if ( nmbr.indexOf( "." ) < 0 ) {
			nmbr += ".";
		}
		var dfltFgr = nmbr.length - nmbr.indexOf( "." ) - 1;
		if ( dfltFgr > fgr ) {
			nmbr = Number( nmbr );
			for ( var i = 0; i < fgr; i++ ) {
				nmbr *= 10;
			}
			nmbr = String( Math.round( nmbr ) );
			nmbr = nmbr.slice( 0, nmbr.length - fgr ) + "." + nmbr.slice( fgr * -1 );
		} else {
			if ( nmbr.indexOf( "." ) == 0 ) {
				nmbr = "0" + nmbr;
			}
			while ( dfltFgr++ < fgr ) {
				nmbr += "0";
			}
		}
	}
	return nmbr;
}
/***********************************************************
 *関数名：formatNumberToInpt( obj, fgr )
 *機能　：inputのvalueを小数点以下fgr桁の数字にする。
 ***********************************************************/
function formatNumberToInpt( obj, fgr ) {
	if ( obj.style.backgroundColor != ERR_INPT_COLOR ) {
		obj.value = formatNumber( obj.value, fgr );
	}
}
/***********************************************************
 *関数名：getDateObject( str, dtSlshFlg )
 *機能　：日付オブジェクトを取得する。
 ***********************************************************/
function getDate( str, dtSlshFlg ) {
	if ( dtSlshFlg == undefined ) {
		dtSlshFlg = DATE_SLASH_FLG;
	}
	var dtPttrn = /^(\d{4})\/(\d{2})\/(\d{2})$/;
	if ( !dtSlshFlg ) {
		dtPttrn = /^(\d{4})(\d{2})(\d{2})$/;
	}
	if ( str.match( dtPttrn ) ) {
		return new Date( Number( RegExp.$1 ), Number( RegExp.$2 ), Number( RegExp.$3 ) );
	} else {
		return null;
	}
}
/***********************************************************
 *関数名：getMsg( id )
 *機能　：メッセージを取得する
 ***********************************************************/
function getMsg( id ) {
	var str = "";
	if ( msgStr[ id ] != undefined ) {
		str = msgStr[ id ];
	}
	return str;
}
/***********************************************************
 *関数名：substituteMsg( str, addMsgs )
 *機能　：メッセージに配列の文字列を代入
 ***********************************************************/
function substituteMsg( str, addMsgs ) {
	if ( addMsgs != undefined && addMsgs != null ) {
		var reg;
		for ( var i = 0; i < addMsgs.length; i++ ) {
			reg = new RegExp( "\\[&" + i + "\\]" );
			if ( str.match( reg ) ) {
				str = RegExp.leftContext + addMsgs[i] + RegExp.rightContext;
			}
		}
	}
	return str;
}
/***********************************************************
 *関数名：dspMsg( id, addMsgs )
 *機能　：メッセージ出力
 ***********************************************************/
function dspMsg( id, addMsgs ) {
	alert( substituteMsg( getMsg( id ), addMsgs ) );
	return true;
}
/***********************************************************
 *関数名：cfmMsg( id, addMsgs )
 *機能　：確認メッセージ出力
 ***********************************************************/
function cfmMsg( id, addMsgs ) {
	return confirm( substituteMsg( getMsg( id ), addMsgs ) );
}
/***********************************************************
 *関数名：getHlpHtmlNm( id )
 *機能　：ヘルプHTML名を取得する
 ***********************************************************/
function getHlpHtmlNm( id ) {
	var str = "";
	if ( hlpHtmlNm[ id ] != undefined ) {
		str = hlpHtmlNm[ id ];
	}
	return str;
}
/***********************************************************
 *関数名：getHlpScrnNm( id )
 *機能　：ヘルプ画面名を取得する
 ***********************************************************/
function getHlpScrnNm( id ) {
	var str = "";
	if ( hlpScrnNm[ id ] != undefined ) {
		str = hlpScrnNm[ id ];
	}
	return str;
}
