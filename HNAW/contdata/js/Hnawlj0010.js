// *----------------------------------------------------------------------------
// * システム		:	計画終了対象商品案内
// * 画面			:	HNAW0010 計画終了対象商品ダウンロード
// * 会社名or所属名	:	株式会社ヴィクサス
// * 作成日			:	2010/01/18
// * 作成者			:	Hirata
// * *** 修正履歴 ***
// * No.	Date		Author		Description
// *----------------------------------------------------------------------------
var TIMER_ID =	null;
/***********************************************************
 *関数名：enableAll()
 *機能　：全有効化
 ***********************************************************/
function enableAll()
{
	with ( document.form1 ) {
		bttnHelp.disabled = false;
		bttnClose.disabled = false;
		bttnSubmit0.disabled = false;
		if ( JSP_NM == B_JSP ) {
			txtUsrCd.disabled = false;
		} else if ( !USR_CD_ERR_ALRT_FLG && slctTpAnniYrwkNoStrt.options.length > 0 ) {
			slctCntrCd.disabled = false;
			slctTpAnniYrwkNoStrt.disabled = false;
			slctTpAnniYrwkNoEnd.disabled = false;
			bttnSubmit1.disabled = false;
		}
	}
}
/***********************************************************
 *関数名：disableAll()
 *機能　：全無効化
 ***********************************************************/
function disableAll()
{
	with ( document.form1 ) {
		bttnHelp.disabled = true;
		bttnClose.disabled = true;
		bttnSubmit0.disabled = true;
		if ( JSP_NM == B_JSP ) {
			txtUsrCd.disabled = true;
		} else {
			slctCntrCd.disabled = true;
			slctTpAnniYrwkNoStrt.disabled = true;
			slctTpAnniYrwkNoEnd.disabled = true;
			bttnSubmit1.disabled = true;
		}
	}
}
/***********************************************************
 *関数名：backClick()
 *機能　：戻るボタンが押された場合
 ***********************************************************/
function backClick() {
	document.form1.cancelSw.value = FLG_ON;
	trnstnFlg = true;
	submitExec( NXT_SRVLT0 );
	return true;
}
/***********************************************************
 *関数名：submit0Click()
 *機能　：submit0ボタンが押された場合
 ***********************************************************/
function submit0Click() {
	if ( !submit0Chk() ) {
		return false;
	}
	trnstnFlg = true;
	submitExec( NXT_SRVLT0 );
	return true;
}
/***********************************************************
 *関数名：submit0Chk()
 *機能　：submit0実行前チェック
 ***********************************************************/
function submit0Chk() {
	with ( document.form1 ) {
		if (
			!necChk( txtUsrCd.value, "利用者コード" ) ||
			!intgrChk( txtUsrCd.value, "利用者コード" ) ||
			!figureChk( txtUsrCd.value, 7, "利用者コード" )
		) {
			objFcs( txtUsrCd );
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：submit1Click()
 *機能　：submit1ボタンが押された場合
 ***********************************************************/
function submit1Click() {
	if ( !submit1Chk() ) {
		return false;
	}
	
	disableAll();
	var prmArry = new Array();
	with ( document.form1 ) {
		prmArry[ "cntrCd" ] = slctCntrCd.value;
		prmArry[ "tpAnniYrwkNoStrt" ] = slctTpAnniYrwkNoStrt.value;
		prmArry[ "tpAnniYrwkNoEnd" ] = slctTpAnniYrwkNoEnd.value;
	}
	var csvCntMxArry = new Array();
	csvCntMxArry.push( CSV_CNT_MX );
	var ajaxHndlr = function( rspnsTxt ) {
		var csvCnt = eval( rspnsTxt );
		var errId = "";
		var addMsgs = null;
		if ( csvCnt < 0 ) {
			errId = "F0029";
		} else if ( csvCnt == 0 ) {
			errId = "F0030";
		} else if ( csvCnt > CSV_CNT_MX ) {
			errId = "F0031";
			addMsgs = csvCntMxArry;
		}
		if ( errId != "" ) {
			dspMsg( errId, addMsgs );
			enableAll();
		} else {
			if ( TIMER_ID != null ) {
				clearTimeout( TIMER_ID );
			}
			
			trnstnFlg = false;
			TIMER_ID = setTimeout( "enableAll();document.form1.bttnSubmit1.focus();", TIMER_INTERVAL );
			submitExec( NXT_SRVLT2 );
		}
	}
	sendAjaxRqst( NXT_SRVLT1, prmArry, ajaxHndlr, false );
	return true;
}
/***********************************************************
 *関数名：submit1Chk()
 *機能　：submit1実行前チェック
 ***********************************************************/
function submit1Chk() {
	with ( document.form1 ) {
		if ( Number( slctTpAnniYrwkNoStrt.value ) > Number( slctTpAnniYrwkNoEnd.value ) ) {
			dspMsg( "F0025" );
			return false;
		}
	}
	return true;
}
/***********************************************************
 *関数名：submitExec( path )
 *機能　：submit実行
 ***********************************************************/
function submitExec( path ) {
	with ( document.form1 ) {
		if ( JSP_NM == B_JSP ) {
			usrCd.value = txtUsrCd.value;
		} else {
			cntrCd.value = slctCntrCd.value;
			tpAnniYrwkNoStrt.value = slctTpAnniYrwkNoStrt.value;
			tpAnniYrwkNoEnd.value = slctTpAnniYrwkNoEnd.value;
		}
		
		disableAll();
		
		action = path;
		
		submit();
	}
}
