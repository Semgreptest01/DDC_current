<!-- Begin
//*----------------------------------------------------------------------------
//* システム		:	共通
//* スクリプト		:	ドラッガー
//* 会社名or所属名	:	株式会社ヴィクサス
//* 作成日			:	2006/03/03
//* 作成者			:	Y.Takabayashi
//* *** 修正履歴 ***
//* No.	Date		Author		Description
//*----------------------------------------------------------------------------
// 使用方法
// (1)<HEAD>～</HEAD> に以下の指定をします。
//    <style>
//    <!--
//    .drag{position:relative;cursor:hand}
//    -->
//    </style>
//    <SCRIPT language="JavaScript1.2" src="drager.js"></SCRIPT>
// (2)<BODY>～</BODY> に以下の指定をします。
//    <img src="a.gif" class="drag"><br>
//    <img src="b.gif" class="drag"><br>
//    <img src="c.gif" class="drag"><br>
//    <img src="d.gif" class="drag"><br>
//----------------------------------------------------------------------------#
var dragapproved=false
var z,x,y
ns4 = (document.layers)? true:false
ie4 = (document.all)? true:false
function move_drager(){
   if (ns4) {var mouseX=e.pageX; var mouseY=e.pageY}
   if (ie4) {var mouseX=event.x; var mouseY=event.y}
   //status="x= "+event.clientX+", y= "+event.clientY;
   if (event.button==1&&dragapproved){
      z.style.pixelLeft=temp1+event.clientX-x
      z.style.pixelTop=temp2+event.clientY-y
      return false
   }
}
function drager(){
   if (!document.all)
      return
   if (event.srcElement.className=="drag"){
      dragapproved=true
      z=event.srcElement
      temp1=z.style.pixelLeft
      temp2=z.style.pixelTop
      x=event.clientX
      y=event.clientY
      document.onmousemove=move_drager
   }
}
document.onmousedown=drager
document.onmouseup=new Function("dragapproved=false")
//  End -->
