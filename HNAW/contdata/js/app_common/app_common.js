// *----------------------------------------------------------------------------
// * システム		:	共通
// * スクリプト		:	共通
// * 会社名or所属名	:	株式会社ヴィクサス
// * 作成日			:	2006/03/03
// * 作成者			:	Hirata
// * *** 修正履歴 ***
// * No.	Date		Author		Description
// *----------------------------------------------------------------------------
/***********************************************************
 *関数名：writeHeader( id, msg )
 *機能　：共通ヘッダーを出力する
 ***********************************************************/
function writeHeader( id, msg )
{
	if ( msg == undefined ) {
		msg = "";
	}
	document.writeln( '<table width="965" border="0" cellpadding="0" cellspacing="0">' );
	document.writeln( '<tr>' );
	document.writeln( '<td>' );
	document.writeln( '<table width="965" height="28" border="0" cellpadding="0" cellspacing="0" class="header">' );
	document.writeln( '<col width="200">' );
	document.writeln( '<col width="765">' );
	document.writeln( '<tr>' );
	document.writeln( '<td>画面ID：' + id + '</td>' );
	document.writeln( '<td>画面名：' + getScrnNm( id ) + '</td>' );
	document.writeln( '</tr>' );
	document.writeln( '</table>' );
	document.writeln( '<textarea id="msgBox" class="msg_box" rows="1" cols="147" wrap="soft" readonly onfocus="this.rows=5" onblur="this.rows=1">' );
	document.writeln( msg );
	document.writeln( '</textarea>' );
	document.writeln( '</td>' );
	document.writeln( '</tr>' );
	document.writeln( '</table>' );
	document.writeln( '<table width="965" border="0" cellpadding="0" cellspacing="0">' );
	document.writeln( '<tr>' );
	document.writeln( '<td align="right">' );
	document.writeln( '<input type="button" class="bttn_dflt" name="bttnHelp" value="ヘルプ" onclick="helpClick();" disabled><input type="button" class="bttn_dflt" name="bttnClose" value="閉じる" onclick="closeClick();" disabled>' );
	document.writeln( '</td>' );
	document.writeln( '</tr>' );
	document.writeln( '</table>' );
	document.writeln( '<table height="15"><tr><td></td></tr></table>' );
}
/***********************************************************
 *関数名：helpClick()
 *機能　：ヘルプボタンが押された場合
 ***********************************************************/
function helpClick() {
	openHelp( WINDOW_ID );
}
/***********************************************************
 *関数名：closeClick()
 *機能　：終了ボタンが押された場合
 ***********************************************************/
function closeClick() {
	windowClose();
}
