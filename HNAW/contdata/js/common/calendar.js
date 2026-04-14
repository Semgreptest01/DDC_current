// *----------------------------------------------------------------------------
// * システム		:	共通
// * スクリプト		:	カレンダー
// * 会社名or所属名	:	株式会社ヴィクサス
// * 作成日			:	2006/03/31
// * 作成者			:	Hirata
// * *** 修正履歴 ***
// * No.	Date		Author		Description
// *----------------------------------------------------------------------------
var CLNDR_DT_SLASH_FLG			=	true;
var CLNDR_DSP_MIN_MAX_FLG		=	false;
var CLNDR_RPLC_ENTR_WTH_TB_FLG	=	true;

var CLNDR_MIN_YEAR				=	1970;
var CLNDR_MAX_YEAR				=	2100;

var CLNDR_FIRE_EVENT			=	"onblur";
//var CLNDR_FIRE_EVENT			=	"onchange";

var CLNDR_WEEK_DAY_NAME_ARRAY	=	new Array( "日", "月", "火", "水", "木", "金", "土" );
var CLNDR_MONTH_NAME_ARRAY		=	new Array( "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月" );

var CLNDR_Z_IDX					=	"100";
var CLNDR_BCK_COLOR				=	"#6699CC";
var CLNDR_DT_CLL_BCK_COLOR		=	"#008080";
var CLNDR_DY_COLOR				=	"#990099";
var CLNDR_OTHR_MNTH_COLOR		=	"#808080";
var CLNDR_WKDY_COLOR			=	"#000000";
var CLNDR_HLDY_COLOR			=	"#FF0000";
var CLNDR_POINT_COLOR			=	"#FFFF00";
var CLNDR_DFLT_BG_COLOR			=	"#CCCCCC";
var CLNDR_DFLT_FNT_COLOR		=	"#000000";

var gClndrCrrntDtObj			=	new Date();
var gClndrCrrntYr				=	gClndrCrrntDtObj.getFullYear();
var gClndrCrrntMnth				=	gClndrCrrntDtObj.getMonth() + 1;
var gClndrCrrntDy				=	gClndrCrrntDtObj.getDate();
var gClndrOtptCtrl				=	null;
var gClndrWkDyObj				=	null;
var gClndrDtArry				=	null;
var gClndrOthrSlctTgArry		=	null;
var gClndrPntdDtCllId			=	"";
/***********************************************************
 *関数名：ClndrCouple( iX, iY )
 *機能　：ClndrCoupleオブジェクト定義
 ***********************************************************/
function ClndrCouple( iX, iY ) {
	this.x = iX;
	this.y = iY;
}
/***********************************************************
 *関数名：popClndr( popCtrl, outputCtrl, weekDayObj )
 *機能　：カレンダー表示
 ***********************************************************/
function popClndr( popCtrl, outputCtrl, weekDayObj ) {
	if ( weekDayObj == undefined ) {
		weekDayObj == null;
	}
	gClndrWkDyObj = weekDayObj;
	gClndrOtptCtrl = outputCtrl;
	setSlctYrAndMnth( gClndrCrrntYr, gClndrCrrntMnth );
	updateClndr( gClndrCrrntYr, gClndrCrrntMnth );
	var pint = getCrrntDspObjXY( popCtrl );
	with ( ppClndr.style ) {
		left = pint.x;
		top = pint.y + popCtrl.offsetHeight + 1;
		width = ppClndr.offsetWidth;
		height = ppClndr.offsetHeight;
		
		if ( parseInt( left ) + parseInt( width ) > parseInt( document.body.clientWidth ) ) {
			left = parseInt( document.body.clientWidth ) - parseInt( width );
		}
		if ( parseInt( top ) + parseInt( height ) > parseInt( document.body.clientHeight ) ) {
			top = parseInt( document.body.clientHeight ) - parseInt( height );
		}
		
		hideOthrSlctTg();
		visibility = "visible";
	}
	ppClndr.focus();
}
/***********************************************************
 *関数名：getCrrntDspObjXY( obj )
 *機能　：オブジェクトの現在表示されている座標を取得
 ***********************************************************/
function getCrrntDspObjXY( obj ) {
	var tmpObj = obj;
	var pint = new ClndrCouple( 0, 0 );
	do {
		pint.x += tmpObj.offsetLeft - tmpObj.scrollLeft;
		pint.y += tmpObj.offsetTop - tmpObj.scrollTop;
		tmpObj = tmpObj.offsetParent;
	} while ( tmpObj.tagName != "BODY" );
	return pint;
}
/***********************************************************
 *関数名：drawDtCll()
 *機能　：日付セル描画
 ***********************************************************/
function drawDtCll() {
	var stylStrng = ' bgcolor="' + CLNDR_DFLT_BG_COLOR + '" bordercolor="' + CLNDR_DFLT_BG_COLOR + '" style="font: bold 12pt Courier; ';
	
	with ( document ) {
		for ( var w = 0; w < 7; w++ ) {
			write( '<tr height="20">' );
			for ( var d = 0; d < 7; d++ ) {
				if ( w == 0 ) {
					write( '<td ' + stylStrng + ' color: ' + CLNDR_DY_COLOR + ';">' + CLNDR_WEEK_DAY_NAME_ARRAY[ d ] + '</td>' );
				} else {
					write( '<td id="clndrDt' + String( w - 1 ) + String( d ) + '" ' + stylStrng + '" onmouseover="dtCllMsovr( this );" onmouseout="dtCllMsout( this );" onclick="dtCllClck( this );">' );
					write( '<span id="clndrDtTxt"></span>' );
					write( '</td>' );
				}
			}
			write( '</tr>' );
		}
	}
}
/***********************************************************
 *関数名：dtCllMsovr( dtCll )
 *機能　：日付セル上にマウスポインタが来た時の処理
 ***********************************************************/
function dtCllMsovr( dtCll ) {
	gClndrPntdDtCllId = dtCll.id;
	pointDtCll( dtCll, !( dtCll.innerText == "" ) );
}
/***********************************************************
 *関数名：dtCllMsout( dtCll )
 *機能　：日付セル上からマウスポインタが出た時の処理
 ***********************************************************/
function dtCllMsout( dtCll ) {
	gClndrPntdDtCllId = "";
	pointDtCll( dtCll, false );
}
/***********************************************************
 *関数名：pointDtCll( obj, pintFlg )
 *機能　：日付セルを指定状態にする
 ***********************************************************/
function pointDtCll( obj, pintFlg ) {
	with ( obj ) {
		if ( pintFlg ) {
			style.cursor = "hand";
			bgColor = CLNDR_POINT_COLOR;
		} else {
			style.cursor = "default";
			bgColor = CLNDR_DFLT_BG_COLOR;
		}
	}
}
/***********************************************************
 *関数名：setSlctYrAndMnth( yr, mnth )
 *機能　：年、月セレクト選択指定
 ***********************************************************/
function setSlctYrAndMnth( yr, mnth ) {
	slctClndrMnth.options[ mnth - 1 ].selected = true;
	for ( var i = 0; i < slctClndrYr.options.length; i++ ) {
		if ( slctClndrYr.options[ i ].value == yr ) {
			slctClndrYr.options[ i ].selected = true;
			break;
		}
	}
}
/***********************************************************
 *関数名：updateClndr( yr, mnth )
 *機能　：カレンダーに日付を表示
 ***********************************************************/
function updateClndr( yr, mnth ) {
	gClndrDtArry = buildClndrArry( yr, mnth );
	var i = 0;
	for ( var w = 0; w < 6; w++ ) {
		for ( var d = 0; d < 7; d++ ) {
			with ( clndrDtTxt[ ( w * 7 ) + d ] ) {
				if ( gClndrDtArry[ w ][ d ] < 0 ) {
					pointDtCll( parentElement, false );
					style.color = CLNDR_OTHR_MNTH_COLOR;
					if (
						( w == 0 && yr == CLNDR_MIN_YEAR && mnth == 1 ) ||
						( w != 0 && yr == CLNDR_MAX_YEAR && mnth == 12 )
					) {
						innerText = "";
					} else {
						if ( gClndrPntdDtCllId == parentElement.id ) {
							pointDtCll( parentElement, true );
						}
						innerText = -gClndrDtArry[ w ][ d ];
					}
				} else {
					if ( gClndrPntdDtCllId == parentElement.id ) {
						pointDtCll( parentElement, true );
					}
					style.color = ( d == 0 || d == 6 ) ? CLNDR_HLDY_COLOR : CLNDR_WKDY_COLOR;
					innerText = gClndrDtArry[ w ][ d ];
				}
			}
		}
	}
	controlDsblOfBtnNxtMnth( yr, mnth );
}
/***********************************************************
 *関数名：buildClndrArry( yr, mnth )
 *機能　：カレンダーの日付の配列を作成
 ***********************************************************/
function buildClndrArry( yr, mnth ) {
	var dtArry = new Array();
	for ( var i = 0; i < 6; i++ ) {
		dtArry[ i ] = new Array( i );
	}
	var frstDyOfMnth = new Date( yr, mnth - 1, 1 ).getDay();
	var dysInMnth = new Date( yr, mnth, 0 ).getDate();
	var frstDyOfClndr = new Date( yr, mnth - 1, 0).getDate() - frstDyOfMnth + 1;
	
	var dt = 1;
	var nxt = 1;
	for ( var w = 0; w < 6; w++ ) {
		for ( var d = 0; d < 7; d++ ) {
			if ( w == 0 ) {
				dtArry[ w ][ d ] = ( d < frstDyOfMnth ) ? -( frstDyOfClndr + d ) : dt++;
			} else {
				dtArry[ w ][ d ] = ( dt <= dysInMnth ) ? dt++ : -( nxt++ );
			}
		}
	}
	
	return dtArry;
}
/***********************************************************
 *関数名：setNxtMnthClndr( nxtFlg )
 *機能　：カレンダーの月を1つずらして再表示する
 ***********************************************************/
function setNxtMnthClndr( nxtFlg ) {
	var yr = slctClndrYr.value;
	var mnth = slctClndrMnth.value;
	
	var yM;
	if ( nxtFlg ) {
		yM = fixYrMnth( yr, ++mnth );
	} else {
		yM = fixYrMnth( yr, --mnth );
	}
	yr = yM.x;
	mnth = yM.y;
	
	setSlctYrAndMnth( yr, mnth );
	updateClndr( yr, mnth );
}
/***********************************************************
 *関数名：fixYrMnth( yr, mnth )
 *機能　：年、月の対を正しい値に整える
 ***********************************************************/
function fixYrMnth( yr, mnth ) {
	var yM = new ClndrCouple( yr, mnth );
	if ( mnth > 12 ) {
		yM.x++;
		yM.y = 1;
	}
	if ( mnth < 1 ) {
		yM.x--;
		yM.y = 12;
	}
	return yM;
}
/***********************************************************
 *関数名：controlDsblOfBtnNxtMnth( yr, mnth )
 *機能　：年月に応じて次月ボタンの使用可不可を制御する
 ***********************************************************/
function controlDsblOfBtnNxtMnth( yr, mnth ) {
	btnClndrPrvMnth.disabled = false;
	btnClndrNxtMnth.disabled = false;
	if ( yr == CLNDR_MIN_YEAR && mnth == 1 ) {
		btnClndrPrvMnth.disabled = true;
	} else if ( yr == CLNDR_MAX_YEAR && mnth == 12 ) {
		btnClndrNxtMnth.disabled = true;
	}
}
/***********************************************************
 *関数名：ppClndrBlr()
 *機能　：カレンダーからフォーカスが外れた時の処理
 ***********************************************************/
function ppClndrBlr() {
	if (
		event.clientX > 0 &&
		event.clientY > 0 &&
		event.clientX < document.body.clientWidth &&
		event.clientY < document.body.clientHeight
	) {
		var obj = document.elementFromPoint( event.clientX, event.clientY );
		while ( obj.tagName != "BODY" ) {
			if ( obj.id == "ppClndr" ) {
				return;
			}
			obj = obj.offsetParent;
		}
	}
	hidePpClndr();
}
/***********************************************************
 *関数名：hidePpClndr()
 *機能　：カレンダーを隠す
 ***********************************************************/
function hidePpClndr() {
	ppClndr.style.visibility = "hidden";
	showOthrSlctTg();
}
/***********************************************************
 *関数名：setDateToOtptCtrl( yr, mnth, dy )
 *機能　：出力インプットに日付をセットする
 ***********************************************************/
function setDateToOtptCtrl( yr, mnth, dy ) {
	var wrkYr = yr;
	var wrkMnth = mnth;
	var wrkDy = dy;
	if ( Number( mnth ) < 10 ) {
		wrkMnth = "0" + mnth;
	}
	if ( Number( dy ) < 10 ) {
		wrkDy = "0" + dy;
	}
	setWkDy( gClndrWkDyObj, yr, mnth, dy );
	if ( CLNDR_DT_SLASH_FLG ) {
		gClndrOtptCtrl.value = wrkYr + "/"+ wrkMnth + "/" + wrkDy;
	} else {
		gClndrOtptCtrl.value = wrkYr + wrkMnth + wrkDy;
	}
	gClndrOtptCtrl.fireEvent( CLNDR_FIRE_EVENT );
	hidePpClndr();
}
/***********************************************************
 *関数名：getWkDy( yr, mnth, dy )
 *機能　：曜日を取得する
 ***********************************************************/
function getWkDy( yr, mnth, dy ) {
	return CLNDR_WEEK_DAY_NAME_ARRAY[ new Date( Number( yr ), Number( mnth ) - 1, Number( dy ) ).getDay() ];
}
/***********************************************************
 *関数名：setWkDy( obj, yr, mnth, dy )
 *機能　：曜日をセットする
 ***********************************************************/
function setWkDy( obj, yr, mnth, dy ) {
	if ( obj != null ) {
		obj.innerText = getWkDy( yr, mnth, dy );
	}
}
/***********************************************************
 *関数名：setWkDyByDt( obj, dt )
 *機能　：日付から曜日をセットする
 ***********************************************************/
function setWkDyByDt( obj, dt ) {
	if ( dt != "" ) {
		var dtArry = getYrMnthDyArry( dt );
		setWkDy( obj, Number( dtArry[ 0 ] ), Number( dtArry[ 1 ] ), Number( dtArry[ 2 ] ) );
	} else {
		obj.innerText = "";
	}
}
/***********************************************************
 *関数名：getYrMnthDyArry( dt )
 *機能　：日付から年、月、日の配列を取得する
 ***********************************************************/
function getYrMnthDyArry( dt ) {
	var yr;
	var mnth;
	var dy;
	if ( CLNDR_DT_SLASH_FLG ) {
		yr = dt.substring( 0, 4 );
		mnth = dt.substring( 5, 7 );
		dy = dt.substring( 8, 10 );
	} else {
		yr = dt.substring( 0, 4 );
		mnth = dt.substring( 4, 6 );
		dy = dt.substring( 6, 8 );
	}
	return new Array( yr, mnth, dy );
}
/***********************************************************
 *関数名：dtCllClck( dtCll )
 *機能　：日付セルをクリックした時の処理
 ***********************************************************/
function dtCllClck( dtCll ) {
	with ( dtCll.children[ "clndrDtTxt" ] ) {
		if ( innerText == "" ) {
			return;
		}
		var yr = Number( slctClndrYr.value );
		var mnth = Number( slctClndrMnth.value );
		var dy = Number( innerText );
		if ( style.color == CLNDR_OTHR_MNTH_COLOR ) {
			var yM = fixYrMnth( yr, mnth + ( Number( dtCll.id.substr ( 7, 1 ) == 0 ) ? -1 : 1 ) );
			yr = yM.x;
			mnth = yM.y;
		}
	}
	
	self.event.cancelBubble = true;
	dtCll.bgColor = CLNDR_DFLT_BG_COLOR;
	
	setDateToOtptCtrl( yr, mnth, dy );
}
/***********************************************************
 *関数名：isTgInBndOfPpClndr( tg )
 *機能　：タグがカレンダー領域に重なっているかを判定する
 ***********************************************************/
function isTgInBndOfPpClndr( tg ) {
	var rslt = true;
	with ( ppClndr.style ) {
		var lft = parseInt( left );
		var tp = parseInt( top );
		var rght = lft + parseInt( width );
		var bttm = tp + parseInt( height );
	}
	var tgPint = getCrrntDspObjXY( tg );
	if (
		tgPint.x > rght ||
		tgPint.x + tg.offsetWidth < lft ||
		tgPint.y > bttm ||
		tgPint.y + tg.offsetHeight < tp
	) {
		rslt = false;
	}
	return rslt;
}
/***********************************************************
 *関数名：hideOthrSlctTg()
 *機能　：カレンダーに重なっている他のSELECTを非表示にする
 ***********************************************************/
function hideOthrSlctTg() {
	gClndrOthrSlctTgArry = new Array();
	with ( document.all.tags( "SELECT" ) ) {
		var lngth = length;
		for ( var i = 0; i < lngth; i++ ) {
			if (
				item( i ).id != "slctClndrYr" &&
				item( i ).id != "slctClndrMnth" &&
				isTgInBndOfPpClndr( item( i ) )
			) {
				gClndrOthrSlctTgArry[ gClndrOthrSlctTgArry.length ] = new Array( item( i ), item( i ).style.visibility );
				item( i ).style.visibility = "hidden";
			}
		}
	}
}
/***********************************************************
 *関数名：showOthrSlctTg()
 *機能　：非表示にした他のSELECTを再表示する
 ***********************************************************/
function showOthrSlctTg() {
	for ( var i in gClndrOthrSlctTgArry ) {
		gClndrOthrSlctTgArry[ i ][ 0 ].style.visibility = gClndrOthrSlctTgArry[ i ][ 1 ];
	}
}
/***********************************************************
 *関数名：btnClndrNxtMnthKydwn( nxtFlg )
 *機能　：次月ボタンでキーを押下した時の処理
 ***********************************************************/
function btnClndrNxtMnthKydwn( nxtFlg ) {
	if (
		(
			event.keyCode == 9 ||
			(
				CLNDR_RPLC_ENTR_WTH_TB_FLG &&
				event.keyCode == 13
			)
		) &&
		(
			(
				!nxtFlg &&
				event.shiftKey
			) ||
			(
				nxtFlg &&
				!event.shiftKey
			)
		)
	) {
		hidePpClndr();
	}
}
/***********************************************************
 *機能　：カレンダー描画
 ***********************************************************/
with ( document ) {
	write( '<div id="ppClndr" style="visibility: hidden; position: absolute; border: 2px ridge; z-index: ' + CLNDR_Z_IDX + ';" onclick="if ( this.style.visibility == \'visible\' ) { this.focus(); }" onblur="ppClndrBlr();">' );
	write( '<table width="210" border="0" bgcolor="' + CLNDR_BCK_COLOR + '" style="font-size: 10pt; font-family: \'ＭＳ ゴシック\'; background-color: inherit; table-layout: fixed;">' );
	write( '<col width="25" align="center" valign="middle">' );
	write( '<col width="70" align="center" valign="middle">' );
	write( '<col width="90" align="center" valign="middle">' );
	write( '<col width="25" align="center" valign="middle">' );
	write( '<tr>' );
	write( '<td>' );
	write( '<input type="button" id="btnClndrPrvMnth" value="&lt;" style="height: 20px; width: 20px; font-size: 10pt; font-family: \'ＭＳ ゴシック\'; text-align: center;" onclick="setNxtMnthClndr( false );self.event.cancelBubble=true;" onblur="ppClndrBlr();" onkeydown="btnClndrNxtMnthKydwn( false );">' );
	write( '</td>' );
	write( '<td>' );
	write( '<select id="slctClndrMnth" style="width: 60px; font-size: 10pt; font-family: \'ＭＳ ゴシック\';" onclick="self.event.cancelBubble=true;" onchange="updateClndr( slctClndrYr.value, slctClndrMnth.value );" onblur="ppClndrBlr();">' );
	for ( var i = 0; i < CLNDR_MONTH_NAME_ARRAY.length; i++ ) {
		write( '<option value="' + ( i + 1 ) + '">&nbsp;' + CLNDR_MONTH_NAME_ARRAY[ i ] + '</option>' );
	}
	write( '</select>' );
	write( '</td>' );
	write( '<td>' );
	write( '<select id="slctClndrYr" style="width: 80px; font-size: 10pt; font-family: \'ＭＳ ゴシック\';" onclick="self.event.cancelBubble=true;" onchange="updateClndr( slctClndrYr.value, slctClndrMnth.value );" onblur="ppClndrBlr();">' );
	for ( var i = CLNDR_MIN_YEAR; i <= CLNDR_MAX_YEAR; i++ ) {
		write( '<option value="' + i + '">&nbsp;' + i + '年</option>' );
	}
	write( '</select>' );
	write( '</td>' );
	write( '<td>' );
	write( '<input type="button" id="btnClndrNxtMnth" value="&gt;" style="height: 20px; width: 20px; font-size: 10pt; font-family: \'ＭＳ ゴシック\'; text-align: center;" onclick="setNxtMnthClndr( true );self.event.cancelBubble=true;" onblur="ppClndrBlr();" onkeydown="btnClndrNxtMnthKydwn( true );">' );
	write( '</td>' );
	write( '</tr>' );
	write( '<tr>' );
	write( '<td colspan="4">' );
	write( '<div style="background-color: ' + CLNDR_DT_CLL_BCK_COLOR + ';">' );
	write( '<table width="196" border="0" style="font-size: 10pt; font-family: \'ＭＳ ゴシック\'; background-color: inherit; table-layout: fixed;">' );
	for ( var i = 0; i < 7; i++ ) {
		write( '<col width="28" align="center" valign="middle">' );
	}
	drawDtCll();
	write( '</table>' );
	write( '</div>' );
	write( '</td>' );
	write( '</tr>' );
	write( '<tr>' );
	write( '<td>' );
	if ( CLNDR_DSP_MIN_MAX_FLG ) {
		write( '<b style="cursor: hand;" onclick="setDateToOtptCtrl( CLNDR_MIN_YEAR, 1, 1 );" onmouseover="this.style.color=CLNDR_POINT_COLOR;" onmouseout="this.style.color=CLNDR_DFLT_FNT_COLOR;">Min</b>' );
	} else {
		write( '&nbsp;' );
	}
	write( '</td>' );
	write( '<td colspan="2">' );
	write( '<b style="cursor: hand;" onclick="setDateToOtptCtrl( gClndrCrrntYr, gClndrCrrntMnth, gClndrCrrntDy );" onmouseover="this.style.color=CLNDR_POINT_COLOR;" onmouseout="this.style.color=CLNDR_DFLT_FNT_COLOR;">' );
	write( 'Today:' + gClndrCrrntYr + '年' + CLNDR_MONTH_NAME_ARRAY[ gClndrCrrntMnth - 1 ] + gClndrCrrntDy + '日' );
	write( '</b>' );
	write( '</td>' );
	write( '<td>' );
	if ( CLNDR_DSP_MIN_MAX_FLG ) {
		write( '<b style="cursor: hand;" onclick="setDateToOtptCtrl( CLNDR_MAX_YEAR, 12, 31 );" onmouseover="this.style.color=CLNDR_POINT_COLOR;" onmouseout="this.style.color=CLNDR_DFLT_FNT_COLOR;">Max</b>' );
	} else {
		write( '&nbsp;' );
	}
	write( '</td>' );
	write( '</tr>' );
	write( '</table>' );
	write( '</div>' );
}
