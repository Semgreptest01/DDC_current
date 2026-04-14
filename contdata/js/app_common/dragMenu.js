// *---------------------------------------------------------------------------
// * システム       :   共通
// * スクリプト     :   POPUP_MENUのドラッグ処理
// * 会社名or所属名 :   株式会社ヴィクサス
// * 作成日         :   2009/08/20
// * 作成者         :   Y.Takabayashi
// * *** 修正履歴 ***
// * No.    Date        Author        Description
// *---------------------------------------------------------------------------
/***********************************************************
 *関数名：move_drager()
 *機能　：Objectの移動処理
 ***********************************************************/
var dragapproved=false
var z,x,y;
ie4 = (document.all)? true:false;
function move_drager(){
   var mouseX=event.x;
   var mouseY=event.y;
   if (event.button==1&&dragapproved){
      z.style.pixelLeft=temp1+event.clientX-x;
      z.style.pixelTop=temp2+event.clientY-y;
      // 移動範囲の制限補正
      if( z.style.pixelLeft < -100 ){ z.style.pixelLeft = -100; }
      if( z.style.pixelLeft > 500 ){ z.style.pixelLeft = 500; }
      if( z.style.pixelTop < -80 ){ z.style.pixelTop = -80; }
      if( z.style.pixelTop > 500 ){ z.style.pixelTop = 500; }
      // 検索条件COMBOの表示判定
      //x=z.style.pixelLeft;
      //y=z.style.pixelTop;
      //dragChk(x, y);
      with ( document.form1 ) {
         if( z.style.pixelTop < -10 ){
            select_week.style.display = "none";
            select_SkuArea.style.display = "none";
            //select_SkuChiiki.style.display = "none";
            select_DeptClass.style.display = "none";
         }else{
            select_week.style.display = "";
            select_SkuArea.style.display = "";
            //select_SkuChiiki.style.display = "";
            select_DeptClass.style.display = "";
         }
         if( z.style.pixelLeft > 50 ){
            select_week.style.display = "";
         }
         if( z.style.pixelLeft > 215 ){
            select_SkuArea.style.display = "";
         }
         if( z.style.pixelLeft > 377 ){
            //select_SkuChiiki.style.display = "";
         }
      }
      return false;
   }
}
/***********************************************************
 *関数名：drager()
 *機能　：Objectのドラッグ・イベント開始
 ***********************************************************/
function drager(){
   if (!document.all)
      return;
   if (event.srcElement.className=="drag"){
      dragapproved=true;
      z=event.srcElement;
      temp1=z.style.pixelLeft;
      temp2=z.style.pixelTop;
      x=event.clientX;
      y=event.clientY;
      document.onmousemove=move_drager;
   }
}
document.onmousedown=drager;
document.onmouseup=new Function("dragapproved=false")
