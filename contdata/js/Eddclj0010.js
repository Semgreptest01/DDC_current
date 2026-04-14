// *---------------------------------------------------------------------------
// * システム       :   横断集計返品メール登録画面
// * 画面           :
// * 会社名or所属名 :   株式会社ヴィンクス
// * 作成日         :   2014/02/01
// * 作成者         :   S.oyamai
// * *** 修正履歴 ***
// * No.   Date	      Author      Description
// *
// * ここに書いてあるJSは基本的にはHTML用の為、実装時には使えないものと思ってください。
// *
// *---------------------------------------------------------------------------
var CLOCK_TIMER = 10;
var TIMER_ID = 10;
var MSG_INTERVAL = 5000;
var WINDOW_WIDTH = 1250;
var WINDOW_HEIGHT = 730;
var WINDOW_LEFT = 0;
var WINDOW_TOP = 0;
//固定値設定
var DISP_PROC_NUM      = "0";
//-------------------------------------
//POP MESSAGE
//-------------------------------------
function messagePop(flg){
	with (document.form1) {
		if(flg == "1"){
			alert("登録が完了しました");
		}else if(flg == "2"){
			alert("変更が完了しました");
		}else{

		}
		s_userCode.value = "";
	}
}
//-------------------------------------
//選択ボタン押下
//-------------------------------------
function dispCtrl(){
	with (document.form1) {
		if(ProcKbnNo.value == "1"){
			pushRegDiv.style.display = "";
			regFotter.style.display = "";
		}else{
			pushModDiv.style.display = "";
		}
		CtrlProc.value = ProcKbnNo.value;
		ProcKbnNo.disabled = true;
		sel_btn.disabled = true;
	}
}
//-------------------------------------
//検索ボタン押下
//-------------------------------------
function SearchAddr(){
	with (document.form1) {
		if(in_user_cd_search.value == ""){
			 alert(( getMsg( 'HN1000' )));
			 errSt( in_user_cd_search, false );
			 return;
		}
		if(in_mail_ad_search.value ==""){
			//お待ちくださいを表示
	 		waitlayer.style.display = "";
	 		wk_proc_num.value = DISP_PROC_NUM;
	 		submitExec(NXT_SRVLT1,false);
		}else{
			// ***********************************
			// **** 送信パラメータ連想配列 *******
			// ***********************************
			var prmArry = new Array();
			prmArry[ "in_user_cd_search" ] = in_user_cd_search.value;
			prmArry[ "in_mail_ad_search" ]= in_mail_ad_search.value;

			// ***********************************
			// ******* Ajax受信ハンドラー ********
			// ***********************************
			var ajaxHndlr = function( rspnsTxt ) {
				if( rspnsTxt == "0" ){
					 alert(( getMsg( 'HN1020' )));
					 errSt( in_mail_ad_search, false );
					 return;
				}else{
					//お待ちくださいを表示
			 		waitlayer.style.display = "";
			 		wk_proc_num.value = DISP_PROC_NUM;
			 		submitExec(NXT_SRVLT1,false);
				}
			}
			// ********* Ajax-Servlet リクエスト *****************
			//alert("*DEBUG* Ajax Servlet:"+servlet);
			sendAjaxRqst( NXT_SRVLT6, prmArry, ajaxHndlr, false );
			//		            |           |        |          |
			//		            |           |        |       true:GET, false:POST
			//		            |           |        |
			//		            |           |        +-----> 受信ハンドラー
			//		            |           +--------------> 送信パラメータ連想配列
			//		            +---------------------------> Ajax-Servlet URL

		}
	}
}

//-------------------------------------
//登録ボタン押下
//-------------------------------------
function click_past(){
	var i="";
	with (document.form1) {
		for(j=0;j<in_mail_ad_add.length;j++){
			var wk_in_mail_ad_add = in_mail_ad_add[j].value;
			if(in_mail_ad_add[j].value != ""){
				for(i=j+1;i<in_mail_ad_add.length;i++){
					if(in_mail_ad_add[i].value != ""){
						if(wk_in_mail_ad_add ==in_mail_ad_add[i].value){
							dspMsg( "HN1017" );//エラーメッセージ
							errSt( in_mail_ad_add[i], true );
							return false;
						}
					}
				}
			}
		}
		if(in_user_nm_add.value != ""){
			for(j=0;j<in_mail_ad_add.length;j++){
				in_user_nm_add.style.backgroundColor = "#ffffff";
				if(in_mail_ad_add[j].value != ""){
				i=1;
				}else{
					if(in_user_nm_add.value != "" && in_mail_ad_add[j].value == "" && in_stdt_add[j].value != ""){
						dspMsg( "HN1007" );//エラーメッセージ
						errSt( in_mail_ad_add[j], true );
						return false;
					}
				}
			}
		}else{
			for(j=0;j<in_mail_ad_add.length;j++){
				var wk_knk_cut_flg_add =[];
				var wk_zaikosokuho_12_flg_add =[];
				var wk_zaikosokuho_22_flg_add =[];
				var wk_kikk_syr_shn_flg_add =[];
				wk_knk_cut_flg_add[j] = chk_box_knk_cut_flg_add[j].checked;
				wk_zaikosokuho_12_flg_add[j] = chk_box_zaikosokuho_12_flg_add[j].checked;
				wk_zaikosokuho_22_flg_add[j] = chk_box_zaikosokuho_22_flg_add[j].checked;
				wk_kikk_syr_shn_flg_add[j] = chk_box_kikk_syr_shn_flg_add[j].checked;
					if(in_user_nm_add.value == "" && in_mail_ad_add[j].value == "" && in_stdt_add[j].value == "" && wk_knk_cut_flg_add[j] == false && wk_zaikosokuho_12_flg_add[j] == false && wk_zaikosokuho_22_flg_add[j] == false && wk_kikk_syr_shn_flg_add[j] == false){
						dspMsg( "HN1014" );//エラーメッセージ
						errSt( in_user_nm_add, true );
						return false;
					}else{
					if(in_user_nm_add.value == ""){
						dspMsg( "HN1015" );//エラーメッセージ
						errSt( in_user_nm_add, true );
						return false;
					}
				}
			}
		}
		for(j=0;j<in_mail_ad_add.length;j++){
			chk_box_zaikosokuho_12_flg_add[j].style.backgroundColor = "#ffffff";
			chk_box_zaikosokuho_22_flg_add[j].style.backgroundColor = "#ffffff";
			chk_box_kikk_syr_shn_flg_add[j].style.backgroundColor = "#ffffff";
			chk_box_knk_cut_flg_add[j].style.backgroundColor = "#ffffff";
		}
		if(i==1){
		for(j=0;j<in_mail_ad_add.length;j++){
			var wk_knk_cut_flg_add =[];
			var wk_zaikosokuho_12_flg_add =[];
			var wk_zaikosokuho_22_flg_add =[];
			var wk_kikk_syr_shn_flg_add =[];
			wk_knk_cut_flg_add[j] = chk_box_knk_cut_flg_add[j].checked;
			wk_zaikosokuho_12_flg_add[j] = chk_box_zaikosokuho_12_flg_add[j].checked;
			wk_zaikosokuho_22_flg_add[j] = chk_box_zaikosokuho_22_flg_add[j].checked;
			wk_kikk_syr_shn_flg_add[j] = chk_box_kikk_syr_shn_flg_add[j].checked;
			if(in_user_nm_add.value != "" && in_mail_ad_add[j].value != "" && in_stdt_add[j].value != "" && in_eddt_add[j].value != ""){
				if(wk_knk_cut_flg_add[j] == false && wk_zaikosokuho_12_flg_add[j] == false && wk_zaikosokuho_22_flg_add[j] == false && wk_kikk_syr_shn_flg_add[j] == false){
				dspMsg( "HN1011" );//エラーメッセージ
				errSt(chk_box_zaikosokuho_12_flg_add[j], true );
				errSt(chk_box_zaikosokuho_22_flg_add[j], true );
				errSt(chk_box_kikk_syr_shn_flg_add[j], true );
				errSt(chk_box_knk_cut_flg_add[j], true );
				return false;
				}else{
					errRst(chk_box_zaikosokuho_12_flg_add[j], true );
					errRst(chk_box_zaikosokuho_22_flg_add[j], true );
					errRst(chk_box_kikk_syr_shn_flg_add[j], true );
					errRst(chk_box_knk_cut_flg_add[j], true );
				}
			}else{
					if(in_user_nm_add.value != "" && in_mail_ad_add[j].value != "" && in_stdt_add[j].value != "" && in_eddt_add[j].value == ""){
						dspMsg( "HN1013" );//エラーメッセージ
						errSt( in_eddt_add[j], true );
						return false;
					}
					if(in_user_nm_add.value != "" && in_stdt_add[j].value == "" && in_mail_ad_add[j].value != ""){
						dspMsg( "HN1008" );//エラーメッセージ
						errSt( in_stdt_add[j], true );
						return false;
					}
			}
		}
	}else{

		dspMsg( "HN1014" );//エラーメッセージ
		errSt( in_mail_ad_add[0], true );
		return false;
	}
	}
	  if( cfmMsg( "C0000" ) ){
			waitlayer.style.display = "";
			submitExec(NXT_SRVLT4,false);
		  }
}


//-------------------------------------
//変更ボタン押下
//-------------------------------------
function click_change(){
	with (document.form1) {
		wk_in_user_nm_add = in_user_nm_add.value;
		wk_in_user_nm_hikaku = in_user_nm_hikaku.value;
		if (in_mail_ad_change.length) {

					for(j=0;j<in_mail_ad_change.length;j++){
						chk_box_knk_cut_flg_change[j].style.backgroundColor = "#ffffff";
						chk_box_zaikosokuho_12_flg_change[j].style.backgroundColor = "#ffffff";
						chk_box_zaikosokuho_22_flg_change[j].style.backgroundColor = "#ffffff";
						chk_box_kikk_syr_shn_flg_change[j].style.backgroundColor = "#ffffff";
					}
					if(wk_in_user_nm_add == "" ){
						 alert(( getMsg( 'HN1006' )));
						 errSt( in_user_nm_add, true );
						 return;
					}else{
						if(wk_in_user_nm_add != wk_in_user_nm_hikaku){
						 alert(( getMsg( 'HN1019' )));
						 errSt( in_user_nm_add, true );
						 return;
					}else{
						for(j=0;j<in_mail_ad_change.length;j++){
							if(in_user_nm_add.value == ""){
								dspMsg( "HN1015" );//エラーメッセージ
								errSt( in_user_nm_add, true );
								return false;
							}else{
							//	if(in_eddt_hikaku == in_eddt_change ){
								var wk_knk_cut_flg_change =[];
								var wk_zaikosokuho_12_flg_change =[];
								var wk_zaikosokuho_22_flg_change =[];
								var wk_kikk_syr_shn_flg_change =[];
								wk_knk_cut_flg_change[j] = chk_box_knk_cut_flg_change[j].checked;
								wk_zaikosokuho_12_flg_change[j] = chk_box_zaikosokuho_12_flg_change[j].checked;
								wk_zaikosokuho_22_flg_change[j] = chk_box_zaikosokuho_22_flg_change[j].checked;
								wk_kikk_syr_shn_flg_change[j] = chk_box_kikk_syr_shn_flg_change[j].checked;
								if(in_eddt_change[j].value == ""){
									dspMsg( "HN1013" );//エラーメッセージ
									errSt( in_eddt_change[j], true );
									return false;
								}
								if(wk_knk_cut_flg_change[j] == false && wk_zaikosokuho_12_flg_change[j] == false && wk_zaikosokuho_22_flg_change[j] == false && wk_kikk_syr_shn_flg_change[j] == false){
								dspMsg( "HN1011" );//エラーメッセージ
								errSt(chk_box_zaikosokuho_12_flg_change[j], true );
								errSt(chk_box_zaikosokuho_22_flg_change[j], true );
								errSt(chk_box_kikk_syr_shn_flg_change[j], true );
								errSt(chk_box_knk_cut_flg_change[j], true );
								return false;
								}
							}
						}
					}
				}
		}else{
			var wk_knk_cut_flg_change ="";
			var wk_zaikosokuho_12_flg_change ="";
			var wk_zaikosokuho_22_flg_change ="";
			var wk_kikk_syr_shn_flg_change ="";
			wk_knk_cut_flg_change = chk_box_knk_cut_flg_change.checked;
			wk_zaikosokuho_12_flg_change = chk_box_zaikosokuho_12_flg_change.checked;
			wk_zaikosokuho_22_flg_change = chk_box_zaikosokuho_22_flg_change.checked;
			wk_kikk_syr_shn_flg_change = chk_box_kikk_syr_shn_flg_change.checked;
			chk_box_knk_cut_flg_change.style.backgroundColor = "#ffffff";
			chk_box_zaikosokuho_12_flg_change.style.backgroundColor = "#ffffff";
			chk_box_zaikosokuho_22_flg_change.style.backgroundColor = "#ffffff";
			chk_box_kikk_syr_shn_flg_change.style.backgroundColor = "#ffffff";
			if(in_eddt_change.value == ""){
				dspMsg( "HN1013" );//エラーメッセージ
				errSt( in_eddt_change, true );
				return;
			}
			if(wk_knk_cut_flg_change == false && wk_zaikosokuho_12_flg_change == false && wk_zaikosokuho_22_flg_change == false && wk_kikk_syr_shn_flg_change == false){
			dspMsg( "HN1011" );//エラーメッセージ
			errSt(chk_box_zaikosokuho_12_flg_change, true );
			errSt(chk_box_zaikosokuho_22_flg_change, true );
			errSt(chk_box_kikk_syr_shn_flg_change, true );
			errSt(chk_box_knk_cut_flg_change, true );
			return;
			}

		}
		for(j=0;j<in_mail_ad_add.length;j++){
			var wk_in_mail_ad_add =[];
			chk_box_knk_cut_flg_add[j].style.backgroundColor = "#ffffff";
			chk_box_zaikosokuho_12_flg_add[j].style.backgroundColor = "#ffffff";
			chk_box_zaikosokuho_22_flg_add[j].style.backgroundColor = "#ffffff";
			chk_box_kikk_syr_shn_flg_add[j].style.backgroundColor = "#ffffff"
		}
		for(j=0;j<in_mail_ad_add.length;j++){
			var wk_in_mail_ad_add = in_mail_ad_add[j].value;
			if(in_mail_ad_add[j].value != ""){
				for(i=j+1;i<in_mail_ad_add.length;i++){
					if(in_mail_ad_add[i].value != ""){
						if(wk_in_mail_ad_add ==in_mail_ad_add[i].value){
							dspMsg( "HN1017" );//エラーメッセージ
							errSt( in_mail_ad_add[i], true );
							return false;
						}
					}
				}
			}
		}
		for(j=0;j<in_mail_ad_add.length;j++){
			var wk_in_mail_ad_add =[];
			wk_in_mail_ad_add = in_mail_ad_add[j].value;
			if(in_mail_ad_add[j].value != "" && in_stdt_add[j].value != "" && in_eddt_add[j].value != ""){
				for(i=0;i<in_mail_ad_change.length;i++){
					if(in_mail_ad_add[j].value == in_mail_ad_change[i].value){
						dspMsg( "HN1004" );//エラーメッセージ
						errSt( in_mail_ad_add[j], true );
						return false;
					}
				}
				var wk_knk_cut_flg_add =[];
				var wk_zaikosokuho_12_flg_add =[];
				var wk_zaikosokuho_22_flg_add =[];
				var wk_kikk_syr_shn_flg_add =[];
				wk_knk_cut_flg_add[j] = chk_box_knk_cut_flg_add[j].checked;
				wk_zaikosokuho_12_flg_add[j] = chk_box_zaikosokuho_12_flg_add[j].checked;
				wk_zaikosokuho_22_flg_add[j] = chk_box_zaikosokuho_22_flg_add[j].checked;
				wk_kikk_syr_shn_flg_add[j] = chk_box_kikk_syr_shn_flg_add[j].checked;
				if(wk_knk_cut_flg_add[j] == false && wk_zaikosokuho_12_flg_add[j] == false && wk_zaikosokuho_22_flg_add[j] == false && wk_kikk_syr_shn_flg_add[j] == false){
				dspMsg( "HN1011" );//エラーメッセージ
				errSt(chk_box_zaikosokuho_12_flg_add[j], true );
				errSt(chk_box_zaikosokuho_22_flg_add[j], true );
				errSt(chk_box_kikk_syr_shn_flg_add[j], true );
				errSt(chk_box_knk_cut_flg_add[j], true );
				return false;
				}
			}else{
				if(in_mail_ad_add[j].value == "" && in_stdt_add[j].value != ""){
					dspMsg( "HN1007" );//エラーメッセージ
					errSt( in_mail_ad_add[j], true );
					return false;
				}
				if(in_stdt_add[j].value == "" && in_mail_ad_add[j].value != ""){
					dspMsg( "HN1008" );//エラーメッセージ
					errSt( in_stdt_add[j], true );
					return false;
				}
				if(in_stdt_add[j].value != "" && in_mail_ad_add[j].value != "" && in_eddt_add[j].value == "" ){
					dspMsg( "HN1013" );//エラーメッセージ
					errSt( in_eddt_add[j], true );
					return false;
				}
			}
		}
		Ajax1();
//		divlayer_m.style.display = "";
//		waitlayer.style.display = "";
//		submitExec(NXT_SRVLT4,false);
		btn_change.disabled = true;
	}
}

//-------------------------------------
//利用者名変更ボタン押下
//-------------------------------------
function click_ri(){
	with (document.form1) {
		wk_in_user_nm_add = in_user_nm_add.value;
		wk_in_user_nm_hikaku = in_user_nm_hikaku.value;
		if(in_user_nm_add.value != ""){
			if(wk_in_user_nm_add == wk_in_user_nm_hikaku){
				 alert(( getMsg( 'HN1009' )));
				 return;
			}else if( cfmMsg( "HN1016" ) ){
			//お待ちくださいを表示
			waitlayer.style.display = "";
			btn_change.disabled = true;
			riyou_change_btn.disabled = true;
			submitExec(NXT_SRVLT5,false);
			}
		}else{
			dspMsg( "HN1015" );//エラーメッセージ
			errSt( in_user_nm_add, true );
			return false;

		}
	}
}


//-------------------------------------
//確定ボタン押下
//-------------------------------------
function click_confirm(){
	with (document.form1) {
		  if( cfmMsg( "C0000" ) ){
		//waitlayer.style.display = "";
		submitExec(NXT_SRVLT4,false);
		}
	}
	//waitlayer.style.display = "";
}

/***********************************************************
 *関数名：click_cancel()
 *機能　：キャンセルボタンが押された
 ***********************************************************/
function click_cancel(obj){
	with ( document.form1 ) {
		disableForChiled(false);
		child_screen1.style.display = "none";
		layer_detail.innerHTML = "";
	}
}

/***********************************************************
 *関数名：pop_conf()
 *機能　：子画面レイヤーPOP処理
 ***********************************************************/
function pop_conf(){
	with ( document.form1 ) {
		ModCheckLayer.style.display = "";
	}
}

//***********************************
//*     ↓ボタン押下関連記述↓      *
//***********************************

/***********************************************************
 *関数名：disableAll()
 *機能　：全無効化
 ***********************************************************/
function disableAll(flg){
    with ( document.form1 ) {
    	//in_user_cd_search.disabled = true;
    	//in_user_nm_search.disabled = true;
    	//in_mail_ad_search.disabled = true;
    	search_btn.disabled = true;
    }
}

/***********************************************************
*関数名：submitExec( path )
*機能　：submit実行
***********************************************************/
function submitExec( path ,flg) {
	disableAll(flg);
	trnstnFlg			=	true;
	document.form1.action = path;
	document.form1.target = "_self";
	document.form1.submit();
}

//-------------------------------------
//明細内部の一括色付けと活性制御
//-------------------------------------
function DetalCtrl(flg,color){
	with (document.form1) {
		var i;
		for(i=0;i<chk_box_delete_flg_change.length;i++){
			DetalColorCtrl(flg,i,color);
			chk_box_delete_flg_change[i].disabled = flg;
			chk_box_delete_flg_change[i].checked = false;

		}
	}
}
//-------------------------------------
//戻るボタン押下
//-------------------------------------
function click_back(){
	with (document.form1) {
		//お待ちくださいを表示
 		waitlayer.style.display = "";
 		submitExec(BFR_SRVLT,false);
	}
}
//-------------------------------------
//テキストフォームに色付け
//-------------------------------------
function cngText(obj){
	with ( document.form1 ) {
		obj.style.backgroundColor="#fff95c";
	}
}

//-------------------------------------
//削除用色付け
//-------------------------------------
function delBtnClick(obj){
	with (document.form1) {
		var DelIndex;
		//どこの削除ボタンか割り出し
		for(i=0;i<chk_box_delete_flg_change.length;i++){
			if(chk_box_delete_flg_change[i] == obj){
				DelIndex = i;
			}
		}
		//行の色付け
		if(obj.checked){
			//ON
			DetalColorCtrl(true,DelIndex,"#b9b9b9");
		}else{
			//OFF
			DetalColorCtrl(false,DelIndex,"#ffffff");
		}
	}
}

//-------------------------------------
//明細一行単位の色付け&活性制御
//-------------------------------------

function DetalColorCtrl(flg,index,color){
	with (document.form1) {
		var delTbl = document.getElementById('modDetalTbl');
		var i;
		if (chk_box_delete_flg_change.length) {
//		in_user_cd_change[index].style.backgroundColor = color;
//		in_user_nm_change[index].style.backgroundColor = color;
		in_mail_ad_change[index].style.backgroundColor = color;
		in_stdt_change[index].style.backgroundColor = color;
		in_eddt_change[index].style.backgroundColor = color;
		chk_box_knk_cut_flg_change[index].style.backgroundColor = color;
		chk_box_zaikosokuho_12_flg_change[index].style.backgroundColor = color;
		chk_box_zaikosokuho_22_flg_change[index].style.backgroundColor = color;
		chk_box_kikk_syr_shn_flg_change[index].style.backgroundColor = color;
		chk_box_delete_flg_change[index].style.backgroundColor = color;

//		in_user_cd_change[index].disabled = flg;
//		in_user_nm_change[index].readonly = flg;
//		in_mail_ad_change[index].readonly = flg;
//		in_stdt_change[index].disabled = flg;
		in_eddt_change[index].readonly = flg;

		cngText(in_eddt_change[index], index);
		}else{
//			in_user_cd_change.style.backgroundColor = color;
			in_user_nm_change.style.backgroundColor = color;
			in_mail_ad_change.style.backgroundColor = color;
			in_stdt_change.style.backgroundColor = color;
			in_eddt_change.style.backgroundColor = color;
			chk_box_knk_cut_flg_change.style.backgroundColor = color;
			chk_box_zaikosokuho_12_flg_change.style.backgroundColor = color;
			chk_box_zaikosokuho_22_flg_change.style.backgroundColor = color;
			chk_box_kikk_syr_shn_flg_change.style.backgroundColor = color;
			chk_box_delete_flg_change.style.backgroundColor = color;

//			in_user_cd_change.disabled = flg;
//			in_user_nm_change.readonly = flg;
//			in_mail_ad_change.readonly = flg;
//			in_stdt_change.disabled = flg;
			in_eddt_change.readonly = flg;
			cngText(in_eddt_change, index);
		}
		for(i=0;i<9;i++){
			delTbl.rows[index].cells[i].style.backgroundColor = color;
		}
	}
}

//-------------------------------------
//編集確認用子画面Pop制御
//-------------------------------------
function modChklayerPop(){
	with (document.form1) {
		if(ModCheckLayer.style.display == ""){
			ModCheckLayer.style.display = "none";
			divlayer_m.style.display = "none";
			btn_change.disabled = false;
		}else{
			ModCheckLayer.style.display = "";
    	}
	}
}

//-------------------------------------
//テキストフォームに色付け
//-------------------------------------
function cngText(obj, idx){
	with (document.form1) {
		var date = obj.value;
		var today = TODAY_DATE; //TODAY_DATE
		var stdt_change;
		var obj2;
		if (in_stdt_change.length) {
			stdt_change = in_stdt_change[idx].value;//入力した有効開始日を取得
			obj2 = in_stdt_change[idx];
		} else {
			stdt_change = in_stdt_change.value;//入力した有効開始日を取得
			obj2 = in_stdt_change;
		}
		// スラッシュを消す
		date = delSlash(date);
		today = delSlash(today);
		stdt_change = delSlash(stdt_change);
		obj.value = date;
		if( obj.value == "" ){
			errRst( obj, false );
		}else{
		// 数値チェック
			if(!stdt_change == "" ){
				if( numrcInptChk( obj, true ) ){
					// 桁数チェック
					if( figureInptChk( obj, 8 ) ){
						if( dtInptChk(obj, true ) ){
							if( date >= stdt_change ){
									if( date > today ){
										if (in_eddt_hikaku.length) {
											if (obj.value != in_eddt_hikaku[idx].value){
												obj.style.backgroundColor="#fff95c";
											}else {
												obj.style.backgroundColor="#ffffff";
											}
										} else {
											if (obj.value != in_eddt_hikaku.value){
												obj.style.backgroundColor="#fff95c";
											}else {
												obj.style.backgroundColor="#ffffff";
											}
										}
									}else{
										dspMsg( "HN1002" );//エラーメッセージ
										errSt( obj, true );
									}
								}else{
									dspMsg( "HN1005" );//エラーメッセージ
									errSt( obj, true );
									return false;
								}
							}
						}
					}
				}
			}
		}
	}



//-------------------------------------
//削除用色付け
//-------------------------------------
function delBtnClick(obj){
	with (document.form1) {
		var DelIndex;
		//どこの削除ボタンか割り出し
//
		if (!chk_box_delete_flg_change.length) {
		DelIndex = 0;
		}else {
			for(i=0;i<chk_box_delete_flg_change.length;i++){
			if(chk_box_delete_flg_change[i] == obj){
					DelIndex = i;
				}
			}
		}
		//行の色付け
		if(obj.checked){
			//ON
			DetalColorCtrl(true,DelIndex,"#b9b9b9");
		}else{
			//OFF
			DetalColorCtrl(false,DelIndex,"#ffffff");
		}
	}
}

//***********************************
//*        ↓Ajax関連記述↓         *
//***********************************
/***********************************************************
*関数名：Ajax1(sw,servlet)
*機能  ：指定KEYで確認画面出力情報を取得する
***********************************************************/
function Ajax1(i) {
	with ( document.form1 ) {
		//過去企画内容を一旦クリアする
		layer_detail.innerHTML = "";
		// ***********************************
		// **** 送信パラメータ連想配列 *******
		// ***********************************
		var prmArry = new Array();
		var wk_mail_ad_change =[];
		var wk_stdt_change =[];
		var wk_eddt_change =[];
		var wk_eddt_hikaku =[];
		var wk_box_knk_cut_flg_hikaku =[];
		var wk_box_zaikosokuho_12_flg_hikaku =[];
		var wk_box_zaikosokuho_22_flg_hikaku =[];
		var wk_box_kikk_syr_shn_flg_hikaku =[];
		var wk_box_knk_cut_flg_hikaku2 =[];
		var wk_box_zaikosokuho_12_flg_hikaku2 =[];
		var wk_box_zaikosokuho_22_flg_hikaku2 =[];
		var wk_box_kikk_syr_shn_flg_hikaku2 =[];
		var wk_box_knk_cut_flg_change =[];
		var wk_box_zaikosokuho_12_flg_change =[];
		var wk_box_zaikosokuho_22_flg_change =[];
		var wk_box_kikk_syr_shn_flg_change =[];
		var wk_box_delete_flg_change =[];
		if (in_mail_ad_change.length) {
			for(i=0;i<in_mail_ad_change.length;i++){
			wk_mail_ad_change[i] = in_mail_ad_change[i].value;
			wk_stdt_change[i] = in_stdt_change[i].value;
			wk_eddt_change[i] = in_eddt_change[i].value;
			wk_eddt_hikaku[i] = in_eddt_hikaku[i].value;
			if(chk_box_knk_cut_flg_hikaku[i].value == "checked"){
				wk_box_knk_cut_flg_hikaku[i] = "true";
			}else{wk_box_knk_cut_flg_hikaku[i] = "false";}
			//wk_box_knk_cut_flg_hikaku[i] = chk_box_knk_cut_flg_hikaku[i].value;
			if(chk_box_zaikosokuho_12_flg_hikaku[i].value == "checked"){
				wk_box_zaikosokuho_12_flg_hikaku[i] = "true";
			}else{wk_box_zaikosokuho_12_flg_hikaku[i] = "false";}
			//wk_box_zaikosokuho_12_flg_hikaku[i] = chk_box_zaikosokuho_12_flg_hikaku[i].value;
			if(chk_box_zaikosokuho_22_flg_hikaku[i].value == "checked"){
				wk_box_zaikosokuho_22_flg_hikaku[i] = "true";
			}else{wk_box_zaikosokuho_22_flg_hikaku[i] = "false";}
			//wk_box_zaikosokuho_22_flg_hikaku[i] = chk_box_zaikosokuho_22_flg_hikaku[i].value;
			if(chk_box_kikk_syr_shn_flg_hikaku[i].value == "checked"){
				wk_box_kikk_syr_shn_flg_hikaku[i] = "true";
			}else{wk_box_kikk_syr_shn_flg_hikaku[i] = "false";}
			//wk_box_kikk_syr_shn_flg_hikaku[i] = chk_box_kikk_syr_shn_flg_hikaku[i].value;
			wk_box_knk_cut_flg_change[i] = chk_box_knk_cut_flg_change[i].checked;
			wk_box_zaikosokuho_12_flg_change[i] = chk_box_zaikosokuho_12_flg_change[i].checked;
			wk_box_zaikosokuho_22_flg_change[i] = chk_box_zaikosokuho_22_flg_change[i].checked;
			wk_box_kikk_syr_shn_flg_change[i] = chk_box_kikk_syr_shn_flg_change[i].checked;
			wk_box_delete_flg_change[i] = chk_box_delete_flg_change[i].checked;
			}
		} else {
			wk_mail_ad_change[0] = in_mail_ad_change.value;
			wk_stdt_change[0] = in_stdt_change.value;
			wk_eddt_change[0] = in_eddt_change.value;
			wk_eddt_hikaku[0] = in_eddt_hikaku.value;
			if(chk_box_knk_cut_flg_hikaku.value == "checked"){
				wk_box_knk_cut_flg_hikaku[0] = "true";
			}else{wk_box_knk_cut_flg_hikaku[0] = "false";}
			//wk_box_knk_cut_flg_hikaku[0] = chk_box_knk_cut_flg_hikaku.value;
			if(chk_box_zaikosokuho_12_flg_hikaku.value == "checked"){
				wk_box_zaikosokuho_12_flg_hikaku[0] = "true";
			}else{wk_box_zaikosokuho_12_flg_hikaku[0] = "false";}
			//wk_box_zaikosokuho_12_flg_hikaku[0] = chk_box_zaikosokuho_12_flg_hikaku.value;
			if(chk_box_zaikosokuho_22_flg_hikaku.value == "checked"){
				wk_box_zaikosokuho_22_flg_hikaku[0] = "true";
			}else{wk_box_zaikosokuho_22_flg_hikaku[0] = "false";}
			//wk_box_zaikosokuho_22_flg_hikaku[0] = chk_box_zaikosokuho_22_flg_hikaku.value;
			if(chk_box_kikk_syr_shn_flg_hikaku.value == "checked"){
				wk_box_kikk_syr_shn_flg_hikaku[0] = "true";
			}else{wk_box_kikk_syr_shn_flg_hikaku[0] = "false";}
			//wk_box_kikk_syr_shn_flg_hikaku[0] = chk_box_kikk_syr_shn_flg_hikaku.value;
			wk_box_knk_cut_flg_change[0] = chk_box_knk_cut_flg_change.checked;
			wk_box_zaikosokuho_12_flg_change[0] = chk_box_zaikosokuho_12_flg_change.checked;
			wk_box_zaikosokuho_22_flg_change[0] = chk_box_zaikosokuho_22_flg_change.checked;
			wk_box_kikk_syr_shn_flg_change[0] = chk_box_kikk_syr_shn_flg_change.checked;
			wk_box_delete_flg_change[0] = chk_box_delete_flg_change.checked;
		}
		var wk_mail_ad_add =[];
		var wk_stdt_add =[];
		var wk_eddt_add =[];
		var wk_box_knk_cut_flg_add =[];
		var wk_box_zaikosokuho_12_flg_add =[];
		var wk_box_zaikosokuho_22_flg_add =[];
		var wk_box_kikk_syr_shn_flg_add =[];
		t=0;
			if (in_mail_ad_add.length) {
				for(i=0;i<in_mail_ad_add.length;i++){
					if(in_mail_ad_add[i].value != ""){
							wk_mail_ad_add[t] = in_mail_ad_add[i].value;
							wk_stdt_add[t] = in_stdt_add[i].value;
							wk_eddt_add[t] = in_eddt_add[i].value;
							wk_box_knk_cut_flg_add[t] = chk_box_knk_cut_flg_add[i].checked;
							wk_box_zaikosokuho_12_flg_add[t] = chk_box_zaikosokuho_12_flg_add[i].checked;
							wk_box_zaikosokuho_22_flg_add[t] = chk_box_zaikosokuho_22_flg_add[i].checked;
							wk_box_kikk_syr_shn_flg_add[t] = chk_box_kikk_syr_shn_flg_add[i].checked;
							t=t+1;
					}
				}
			} else {
				if(in_mail_ad_add[0].value != ""){
				wk_mail_ad_add[0] = in_mail_ad_add.value;
				wk_stdt_add[0] = in_stdt_add.value;
				wk_eddt_add[0] = in_eddt_add.value;
				wk_box_knk_cut_flg_add[0] = chk_box_knk_cut_flg_add.checked;
				wk_box_zaikosokuho_12_flg_add[0] = chk_box_zaikosokuho_12_flg_add.checked;
				wk_box_zaikosokuho_22_flg_add[0] = chk_box_zaikosokuho_22_flg_add.checked;
				wk_box_kikk_syr_shn_flg_add[0] = chk_box_kikk_syr_shn_flg_add.checked;
				}
			}
		prmArry[ "in_mail_ad_change" ] = wk_mail_ad_change;
		prmArry[ "in_stdt_change" ] = wk_stdt_change;
		prmArry[ "in_eddt_change" ] = wk_eddt_change;
		prmArry[ "in_eddt_hikaku" ] = wk_eddt_hikaku;
		prmArry[ "chk_box_knk_cut_flg_hikaku" ] = wk_box_knk_cut_flg_hikaku;
		prmArry[ "chk_box_zaikosokuho_12_flg_hikaku" ] = wk_box_zaikosokuho_12_flg_hikaku;
		prmArry[ "chk_box_zaikosokuho_22_flg_hikaku" ] = wk_box_zaikosokuho_22_flg_hikaku;
		prmArry[ "chk_box_kikk_syr_shn_flg_hikaku" ] = wk_box_kikk_syr_shn_flg_hikaku;
		prmArry[ "chk_box_knk_cut_flg_change" ] = wk_box_knk_cut_flg_change;
		prmArry[ "chk_box_zaikosokuho_12_flg_change" ] = wk_box_zaikosokuho_12_flg_change;
		prmArry[ "chk_box_zaikosokuho_22_flg_change" ] = wk_box_zaikosokuho_22_flg_change;
		prmArry[ "chk_box_kikk_syr_shn_flg_change" ] = wk_box_kikk_syr_shn_flg_change;
		prmArry[ "chk_box_delete_flg_change" ] = wk_box_delete_flg_change;
		prmArry[ "in_mail_ad_add" ] = wk_mail_ad_add;
		prmArry[ "in_stdt_add" ] = wk_stdt_add;
		prmArry[ "in_eddt_add" ] = wk_eddt_add;
		prmArry[ "chk_box_knk_cut_flg_add" ] = wk_box_knk_cut_flg_add;
		prmArry[ "chk_box_zaikosokuho_12_flg_add" ] = wk_box_zaikosokuho_12_flg_add;
		prmArry[ "chk_box_zaikosokuho_22_flg_add" ] = wk_box_zaikosokuho_22_flg_add;
		prmArry[ "chk_box_kikk_syr_shn_flg_add" ] = wk_box_kikk_syr_shn_flg_add;

		// ***********************************
		// ******* Ajax受信ハンドラー ********
		// ***********************************
		var ajaxHndlr = function( rspnsTxt ) {
			AJAX_SW = "0";
			// ------------------------------------------------------------
			// rspnsTxtは、先頭１文字にステータスが返る。
			// 0:OK
			// 1:データなし
			// 9:その他のERROR
			// データ部は、以下のフォーマット
			// STATUS:子画面用HTML
			// エラーの場合は、以下フォーマット
			// STATUS:エラー内容
			// ------------------------------------------------------------
			var err_msg      = "";
			var center_array = new Array(2);
			if( rspnsTxt.substr(0,1) == "9" ){
				err_msg = rspnsTxt.substr(2);
				// F0008 商品情報の取得でエラーが発生しました。
				msg = getMsg("F0008" )+ err_msg;
				alert( msg );
			}
			//通常結果表示用
			if( rspnsTxt.substr(0,1) == "0" ) {
				center_array = rspnsTxt.split("||");
				layer_detail.innerHTML = center_array[1];
				//alert(center_array[1]);
				//透明レイヤーを消す
				divlayer_m.style.display = "";
				//お待ちください表記を消す
				waitlayer.style.display = "none";
				//過去企画pop
				pop_conf();
			}
			//０件結果表示用
			if( rspnsTxt.substr(0,1) == "1" ) {
				dspMsg( "HN1018" );//エラーメッセージ
				btn_change.disabled = false;
				return false;
			}
		}
		// ********* Ajax-Servlet リクエスト *****************
		//alert("*DEBUG* Ajax Servlet:"+servlet);
		sendAjaxRqst( NXT_SRVLT2, prmArry, ajaxHndlr, false );
		//            |           |        |          |
		//            |           |        |       true:GET, false:POST
		//            |           |        |
		//            |           |        +-----> 受信ハンドラー
		//            |           +--------------> 送信パラメータ連想配列
		//            +---------------------------> Ajax-Servlet URL
	}
}


/***********************************************************
 *関数名：user_cd_chk()
 *機能　：利用者コードチェック
 ***********************************************************/
function user_cd_chk(obj){
	with ( document.form1 ) {
		if( obj.value == "" ){
			errRst( obj, false );
		}else{
		// 数値チェック
			if( numrcInptChk( obj, true ) ){
				// 桁数チェック
				if( figureInptChk( obj, 7 ) ){
					errRst( obj, false );
				}
			}
		}
	}
}

/***********************************************************
 *関数名：user_nm_chk()
 *機能　：利用者名チェック
 ***********************************************************/
function user_nm_chk(obj){
	with ( document.form1 ) {
		if( obj.value == "" ){
			errRst( obj, false );
		}else{
			if(frbddnChrInptChk( obj, true ) ){
				if( dblwdthInptChk( obj, true ) ){
					errRst( obj, false );
				}
			}
		}
	}
}

/***********************************************************
 *関数名：mail_ad_chk()
 *機能　：メールアドレスチェック
 ***********************************************************/
function mail_ad_chk(obj){
	with ( document.form1 ) {
			if( obj.value == "" ){
				errRst( obj, false );
			}else{
				//if (!obj.value.match(/^[A-Za-z0-9]+[\w-]+@[\w\.-]+\.\w{2,}$/)){
					//alert("e-mailアドレスをご確認ください。");
					//return false;
					if( hlfwdthInptChk( obj, true  ) ){
						var str;
						str = obj.value;
						if ( str != "" ) {
							var chrCd;
							for ( var i = 0; i < str.length; i++ ) {
								// 1文字ずつ取り出して半角か判断する。
								chrCd = str.charAt( i ) ;
								if (chrCd == " "){
									dspMsg( "HN1001" );
									errSt( obj, true );
									return false;
								}
							}
						}
						errRst( obj, false );
					}
				//}
			}
	}
}

/***********************************************************
 *関数名：mail_ad_chk2()
 *機能　：メールアドレスチェック
 ***********************************************************/
function mail_ad_chk2(obj){
	with ( document.form1 ) {
			if( obj.value == "" ){
				errRst( obj, false );
			}else{
				//if (!obj.value.match(/^[A-Za-z0-9]+[\w-]+@[\w\.-]+\.\w{2,}$/)){
				if (!obj.value.match(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/)){
					alert("メールアドレスとして正しくありません。");
					errSt( obj, true );
					return false;}
				if( hlfwdthInptChk( obj, true  ) ){
					var str;
					str = obj.value;
					if ( str != "" ) {
						var chrCd;
						for ( var i = 0; i < str.length; i++ ) {
							// 1文字ずつ取り出して半角か判断する。
							chrCd = str.charAt( i ) ;
							if (chrCd == " "){
								dspMsg( "HN1001" );
								errSt( obj, true );
								return false;
							}
						}
					}
					errRst( obj, false );
				}
			}
	}
}


/***********************************************************
 *関数名：date_chk
 *機能　：有効開始日チェック
 ***********************************************************/
function date_chk(obj, idx){
	with ( document.form1 ) {
		var date = obj.value;
		var today = TODAY_DATE; //TODAY_DATE　//SQLで取得
		var eddt_add;
		if (in_eddt_add.length) {
			eddt_add = in_eddt_add[idx].value;//入力した有効終了日を取得
		} else {
			eddt_add = in_eddt_add.value;//入力した有効終了日を取得
		}
		// スラッシュを消す
		date = delSlash(date);
		today = delSlash(today);
		eddt_add = delSlash(eddt_add);
		obj.value = date;
		if( obj.value == "" ){
			errRst( obj, false );
		}else{
		// 数値チェック
			if(!eddt_add == "" ){
				if( numrcInptChk( obj, true ) ){
					// 桁数チェック
					if( figureInptChk( obj, 8 ) ){
						if( dtInptChk(obj, true ) ){
							if( date > today ){
								errRst( obj, false );
								if( date <= eddt_add ){
									errRst( obj, false );
								}else{
									dspMsg( "HN1021" );//エラーメッセージ
									errSt( obj, true );
									return false;
								}
							}else{
								dspMsg( "HN1002" );//エラーメッセージ
								errSt( obj, true );
								return false;
							}
						}
					}
				}
			}else{
				if( numrcInptChk( obj, true ) ){
					// 桁数チェック
					if( figureInptChk( obj, 8 ) ){
						if( dtInptChk(obj, true ) ){
							if( date > today ){
								errRst( obj, false );
							}else{
								dspMsg( "HN1002" );//エラーメッセージ
								errSt( obj, true );
								return false;
							}
						}
					}
				}
			}
		}
	}
}
/***********************************************************
 *関数名：date_chk
 *機能　：有効開始日チェック
 ***********************************************************/
function date_edit(obj){
	with ( document.form1 ) {
		var date = obj.value;
		// スラッシュを消す
		date = delSlash(date);
		obj.value = date;
	}
}

/***********************************************************
 *関数名：date_chk2
 *機能　：有効終了日チェック
 ***********************************************************/
function date_chk2(obj, idx){
	with ( document.form1 ) {
		var date = obj.value;
		var today = TODAY_DATE; //TODAY_DATE
		var stdt_add;
		var obj2;
		if (in_stdt_add.length) {
			stdt_add = in_stdt_add[idx].value;//入力した有効開始日を取得
			obj2 = in_stdt_add[idx];
		} else {
			stdt_add = in_stdt_add.value;//入力した有効開始日を取得
			obj2 = in_stdt_add;
		}
		// スラッシュを消す
		date = delSlash(date);
		today = delSlash(today);
		stdt_add = delSlash(stdt_add);
		obj.value = date;
		if( obj.value == "" ){
			errRst( obj, false );
		}else{
		// 数値チェック
			if(!stdt_add == "" ){
				if( numrcInptChk( obj, true ) ){
					// 桁数チェック
					if( figureInptChk( obj, 8 ) ){
						if( dtInptChk(obj, true ) ){
							if( date >= stdt_add ){
								errRst( obj, false );
								if( date > today ){
									errRst( obj, false );
									}
							}else{
								dspMsg( "HN1003" );//エラーメッセージ
								errSt( obj, true );
								return false;
							}
						}
					}
				}
			}else{
				if( numrcInptChk( obj, true ) ){
					// 桁数チェック
					if( figureInptChk( obj, 8 ) ){
						if( dtInptChk(obj, true ) ){
							if( date > today ){
								errRst( obj, false );
							}else{
								dspMsg( "HN1002" );//エラーメッセージ
								errSt( obj, true );
								return false;
							}
						}
					}
				}
			}
		}
	}
}