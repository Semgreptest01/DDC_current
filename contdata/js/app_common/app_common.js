// *----------------------------------------------------------------------------
// * システム		:	共通
// * スクリプト		:	共通
// * 会社名or所属名	:	株式会社ヴィクサス
// * 作成日			:	2006/03/03
// * 作成者			:	Hirata
// * *** 修正履歴 ***
// * No.	Date		Author		Description
// *----------------------------------------------------------------------------
/***************************************************************
 *関数名：disp_clock()
 *機能　：Webサーバの現在日付時刻を取得表示（curr_dtime）する。
 ***************************************************************/
function disp_clock() {
	clearTimeout( CLOCK_TIMER );
	CLOCK_TIMER = setTimeout("disp_clock();",CLOCK_INTERVAL);
	DwrBeanScript.getDTime("1", callback_clock);
}
/***************************************************************
 *関数名：callback_clock(str)
 *機能　：サーバからのcallback時刻をcurr_dtimeに表示する。
 ***************************************************************/
function callback_clock(str) {
    dwr.util.setValue("curr_dtime", str);
}
/***********************************************************
 *関数名：writeScreenHeader( id, str )
 *機能　：共通ヘッダーを出力する
 ***********************************************************/
function writeScreenHeader( id, str ){
	document.write( '<TABLE width="965" bgcolor="#0000FF" border="0">' );
	document.write( '<col width="150"><col width="685" align="left"><col width="140" align="left">' );
	document.write( '<TR>' );
	document.write( '<TD nowrap' );
	document.write( ' class="ScreenHeader">画面ID:'+id+'</TD>' );
	document.write( '<TD id="gamen_nn" nowrap' );
	document.write( ' class="ScreenHeader">画面名：'+getScrnNm( id )+'</TD>' );
	document.write( '<TD nowrap class="ScreenClock">' );
	document.write( '    <div id="curr_dtime">'+str+'</div>' );
	document.write( '</TD>' );
	document.write( '</TR>' );
	document.write( '</TABLE>' );
}
/***********************************************************
 *関数名：writeScreenHeader( id, str )
 *機能　：共通ヘッダーを出力する
 ***********************************************************/
function writeScreenHeader2( id, str ){
	document.write( '<TABLE width="1200px" bgcolor="#0000FF" border="0">' );
	document.write( '<col width="150"><col width="685" align="left"><col width="140" align="left">' );
	document.write( '<TR>' );
	document.write( '<TD nowrap' );
	document.write( ' class="ScreenHeader">画面ID:'+id+'</TD>' );
	document.write( '<TD id="gamen_nn" nowrap' );
	document.write( ' class="ScreenHeader">画面名：'+getScrnNm( id )+'</TD>' );
	document.write( '<TD nowrap class="ScreenClock">' );
	document.write( '    <div id="curr_dtime">'+str+'</div>' );
	document.write( '</TD>' );
	document.write( '</TR>' );
	document.write( '</TABLE>' );
}
/***********************************************************
 *関数名：closeClick()
 *機能　：終了ボタンが押された場合
 ***********************************************************/
function closeClick() {
  // C0003 このウィンドウを閉じてもよろしいですか？
  if( cfmMsg( "C0003" ) ){
    windowClose();
  }
}
