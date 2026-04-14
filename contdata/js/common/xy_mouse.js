<!-- Begin
//*----------------------------------------------------------------------------
//* システム		:	共通
//* スクリプト		:	マウス座標のステータス行表示
//* 会社名or所属名	:	株式会社ヴィクサス
//* 作成日			:	2006/03/03
//* 作成者			:	Y.Takabayashi
//* *** 修正履歴 ***
//* No.	Date		Author		Description
//*----------------------------------------------------------------------------
// 使用方法
// (1)<HEAD>～</HEAD> に以下の指定をします。
//    <SCRIPT language="JavaScript" src="xy_mouse.js"></SCRIPT>
// (2)<BODY> に以下の指定をします。
//    <BODY onLoad="xy_mouse()">
//-----------------------------------------------------------------------------
function xy_mouse() {
   document.onmousemove=mousemove;
}
function xy_mouse_reset() {
   status="";
   document.onmousemove="";
}
function mousemove(e) {
   var mouseX=event.x;
   var mouseY=event.y;
   status="x= "+mouseX+", y= "+mouseY;
}
//  End -->
