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
hlpHtmlNm[ "SHNAW0010" ] = "Hnawlj0010Help.htm";
var hlpScrnNm	=	new Array();
hlpScrnNm[ "SHNAW0010" ] = "計画終了対象商品ダウンロード　ヘルプ画面";
/***********************************************************
 *関数名：openHelp( id )
 *機能　：ヘルプを開く
 ***********************************************************/
function openHelp( id ) {
	var winNm = getHlpHtmlNm( id );
	var reg = new RegExp( "\\." );
	if ( winNm.match( reg ) ) {
		winNm = RegExp.leftContext + RegExp.rightContext;
	}
	var ApWindow = window.open( '', winNm, OPEN_WINDOW_PRMTR );
	ApWindow.document.write( '<html><head><title>ヘルプ</title>' );
	ApWindow.document.write( '</head><body><form name="form1" target="_self">' );
	ApWindow.document.write( '<table width="100%" height="100%"><tr><td align="center">' );
	ApWindow.document.write( '</td></tr></table>' );
	ApWindow.document.write( '</form></body></html>' );
	ApWindow.document.form1.action = HELP_DIR_PATH + "/" + getHlpHtmlNm( id );
	ApWindow.document.form1.method = "POST";
	ApWindow.document.form1.submit();
}
/***********************************************************
 *関数名：writeHelpHeader( id, msg )
 *機能　：ヘルプ画面の共通ヘッダーを出力する
 ***********************************************************/
function writeHelpHeader( id )
{
	document.writeln( '<table width="100%" border="0" cellpadding="0" cellspacing="0">' );
	document.writeln( '<tr>' );
	document.writeln( '<td>' );
	document.writeln( '<table width="100%" height="28" border="0" cellpadding="0" cellspacing="0" class="header">' );
	document.writeln( '<tr>' );
	document.writeln( '<td>' + getHlpScrnNm( id ) + '</td>' );
	document.writeln( '</tr>' );
	document.writeln( '</table>' );
	document.writeln( '</td>' );
	document.writeln( '</tr>' );
	document.writeln( '</table>' );
	document.writeln( '<table height="15"><tr><td></td></tr></table>' );
}
