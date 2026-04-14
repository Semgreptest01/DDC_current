<%@ page contentType="text/html;charset=Windows-31J"%>
<jsp:useBean id="Hnmslj0010DBAccess" scope="session" class="app.Hnmslj0010DBAccess" />
<% app.Hnmslj0010DBAccess dba = Hnmslj0010DBAccess; %>
<%@ include file="JspHeaderJ.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--
// *----------------------------------------------------------------------------
// * システム		:	返品コスト削減
// * 画面			:	SHNMS0010 アドレス登録画面
// * 会社名or所属名	:	株式会社ヴィンクス
// * 作成日			:	2013/08/20
// * 作成者			:	S.Oyama
// * *** 修正履歴 ***
// * No.	Date		Author		Description
// *----------------------------------------------------------------------------
-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Shift_JIS">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<link rel="stylesheet" type="text/css" href="contdata/css/common.css">
<link rel="stylesheet" type="text/css" href="contdata/css/eddclj0010.css">
<title></title>
<script language="JavaScript" src="contdata/js/common/ajax.js"></script>
<script language="JavaScript" src="contdata/js/common/check.js"></script>
<script language="JavaScript" src="contdata/js/common/common.js"></script>
<script language="JavaScript" src="contdata/js/common/drager.js"></script>
<script language="JavaScript" src="contdata/js/app_common/app_common.js"></script>
<script language="JavaScript" src="contdata/js/app_common/const.js"></script>
<script language="JavaScript" src="contdata/js/app_common/message.js"></script>
<script language="JavaScript" src="contdata/js/app_common/scrn_nm.js"></script>
<script language="JavaScript" src="contdata/js/Eddclj0010.js"></script>
<script language="JavaScript">
<!--
var CLOCK_TIMER = 10;
var TIMER_ID = 10;
var MSG_INTERVAL = 5000;
var WINDOW_WIDTH = 1250;
var WINDOW_HEIGHT = 730;
var WINDOW_LEFT = 0;
var WINDOW_TOP = 0;
var SESSION_ARG_NM	     = "Hnmslj0010DBAccess";
var WINDOW_ID		     = "SHNMS0010";
var JSP_NM			     = "Hnmslj0010bJ.jsp";
var BFR_SRVLT            = "";					// 閉じる
var NXT_SRVLT1           = "Hnmslj0010a";		// 検索
var NXT_SRVLT2           = "Hnmslj0010ajax";	// 変更
var NXT_SRVLT6           = "Hnmslj0011ajax";	// 変更
var DATA_SW              = "<%= dba.DATA_SW %>";
var INI_SW               = "<%= dba.INI_SW %>";
function initial() {
	window.onunload = unloadWindow;
	document.title = getScrnNm( WINDOW_ID );
	try{
		resizeTo( WINDOW_WIDTH, WINDOW_HEIGHT );
		self.moveTo( WINDOW_LEFT, WINDOW_TOP );

		with ( document.form1 ) {
			if(DATA_SW == "1"){
				// W0001 正常に登録しました。
				dspMsg( "W0001" );
			}else if(DATA_SW == "2"){
				 // W0002 正常に更新しました。
				dspMsg( "W0002" );
			}else if(DATA_SW == "3"){
				// W0003 正常に削除しました。
				dspMsg( "W0003" );
			}else{
				//なにもしない
			}
		}
	} catch(e) {};
}
var chk;
 --></script>
</head>
	<body topmargin="5" leftmargin="10" onload="initial();">
		<form name="form1" method="post" target="_self">
			<input type="text" name="dummy" class="dummy" disabled>
			<input type="hidden" name="cancelSw" value="0">
			<input type="hidden" name="before_servlet" value="Hnmslj0010b">
			<!-- HIDDEN設定 -->
			<input type="hidden" name="user_renban" value="<%= dba.user_renban %>">
			<input type="hidden" name="user_namecd" value="<%= dba.namecd %>">
			<input type="hidden" name="wk_proc_num" value = "">

			<script language="JavaScript">
				<!--
				   writeScreenHeader2("SHNMS0010","<%= util.DateTimeUtility.curr_Timestamp() %>");
				// -->
			</script>
			<BR>
			<!--  ******** BODY START ********************************* -->
			<!--  ******** HEADDER START ********************************* -->
			<div class="hedder">
				<!-- 検索部  -->
				<div class="shdiv">
					<div class="fl">
						<table class="shtab">
							<col width="80px">
							<col width="80px">
							<col width="90px">
							<col width="320px">
							<tr>
								<th colspan="4">検索条件入力</th>
							</tr>
							<tr>
								<th style="color: #ff0000;background-color: #ffff00;">利用者コード</th>
								<td><input name="in_user_cd_search" type="text" class="input_text" style="width: 100%;" maxlength="7" onblur="user_cd_chk(this)"></td>
								<th>メールアドレス</th>
								<td><input name="in_mail_ad_search" type="text" class="code50" maxlength="50" onblur="mail_ad_chk(this)"></td>
							</tr>
						</table>
					</div>
					<!-- 検索部 ボタン  -->
					<div class="fr">
						<div class="shbtnfl" >
							<input type="button" name="search_btn" class="mid_button" value="検索" onclick="SearchAddr();">
						</div>
						<div class="shbtnfr">
							<input type="button" class="btn_mini" value="閉じる" onclick="closeClick();">
						</div>
						<div class="fc">
						</div>
					</div>
					<div class="fc" style="font-size: 9pt;">
						※利用者コードは必ず入力してください。
					</div>
				</div>
			</div>
			<!--  ******** HEADDER END ********************************* -->
			<hr>
			<!--  ******** MAIN START ********************************* -->
			<div class = "main" >
			</div>
			<!--  ******** MAIN END ********************************* -->
			<!--  ******** FOOTER START ********************************* -->
			<div class="footer" >

			</div>
			<!--  ******** FOOTER END ********************************* -->
			<!--  ******** BODY END *********************************** -->
			<!-- お待ちくださいを表示する＜レイヤ画面１＞ -->
			<table width="230" border="0" cellpadding="0" cellspacing="0"
				   style="position: absolute;top:400px;left:40%;display:none;" id="waitlayer">
				<tr>
					<td align="center" style="background-color:#87ceeb;height:50;">
						<table width="200" border="0" cellpadding="0" cellspacing="0" style="cursor: auto">
							<tr><th>お待ちください。</th></tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<%@ include file="JspFooterJ.jsp"%>
