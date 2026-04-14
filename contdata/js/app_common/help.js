// *----------------------------------------------------------------------------
// * システム		:	共通
// * スクリプト		:	ヘルプ
// * 会社名or所属名	:	株式会社ヴィクサス
// * 作成日			:	2006/03/15
// * 作成者			:	Hirata
// * *** 修正履歴 ***
// * No.	Date		Author		Description
// *----------------------------------------------------------------------------
var hlpHtmlNm	=	new Array();
hlpHtmlNm[ "00000bJ.jsp" ] = "00000bHelp.htm";
/***********************************************************
 *関数名：getHlpHtmlNm( id )
 *機能　：ヘルプHTML名を取得する
 ***********************************************************/
function getHtmlNm( id ) {
	var str = "";
	if ( hlpHtmlNm[ id ] != undefined ) {
		str = hlpHtmlNm[ id ];
	}
	return str;
}
/***********************************************************
 *関数名：openHelp( cntntsDrctryPth, id )
 *機能　：ヘルプを開く
 ***********************************************************/
function openHelp( cntntsDrctryPth, id ) {
	var winNm = getHtmlNm( id );
	var reg = new RegExp( "\\." );
	if ( winNm.match( reg ) ) {
		winNm = RegExp.leftContext + RegExp.rightContext;
	}
	var ApWindow = window.open( '', winNm, OPEN_WINDOW_PRMTR );
	ApWindow.document.write( '<html><head><title>ヘルプ</title>' );
	ApWindow.document.write( '</head><body><form name="form1" target="_self">' );
	ApWindow.document.write( '<table width="100%" height="100%"><tr><td align="center">' );
	ApWindow.document.write( '<img src="' + cntntsDrctryPth + "/" + IMG_DIR_PATH + '/Now_Loading.jpg">' );
	ApWindow.document.write( '</td></tr></table>' );
	ApWindow.document.write( '</form></body></html>' );
	ApWindow.document.form1.action = cntntsDrctryPth + "/" + HELP_DIR_PATH + "/" + getHtmlNm( id );
	ApWindow.document.form1.method = "POST";
	ApWindow.document.form1.submit();
	ApWindow.focus();
}
