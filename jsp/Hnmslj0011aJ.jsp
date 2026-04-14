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
// * 作成日			:	2013/08/21
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
var WINDOW_WIDTH = 1239;
var SESSION_ARG_NM	     = "Hnmslj0010DBAccess";
var WINDOW_ID		     = "SHNMS0010";
var JSP_NM			     = "Hnmslj0010aJ.jsp";
var BFR_SRVLT            = "Hnmslj0010c";		// 戻る
var NXT_SRVLT1           = "Hnmslj0010a";		// 表示
var NXT_SRVLT2           = "Hnmslj0010ajax";	// 検索
var NXT_SRVLT3           = "Hnmslj0010ajax2";	// 事前チェック
var NXT_SRVLT4           = "Hnmslj0010r";		// 登録/変更/削除
var NXT_SRVLT5           = "Hnmslj0010r2";		// 利用者名変更

var DATA_SW              = "<%= dba.DATA_SW %>";
var TODAY_DATE           = "<%= dba.TODAY_CNT_DATE0 %>";     // 当日日付
var INI_SW               = "<%= dba.INI_SW %>";

function initial() {
	window.onunload = unloadWindow;
	document.title = getScrnNm( WINDOW_ID );
	try{
		resizeTo( WINDOW_WIDTH, WINDOW_HEIGHT );
		self.moveTo( WINDOW_LEFT, WINDOW_TOP );
		with ( document.form1 ) {
				  if (in_mail_ad_search.value != "")
				  {
				    document.getElementById("oyama0").style.display="none";{
				    document.getElementById("oyama").style.display="none";
				    }
				  }
			disableAll(true);
			comboSetting();
			enableServA();
			waitingTimer();
		}
	} catch(e) {};
}
 --></script>
</head>
	<body topmargin="5" leftmargin="10" onload="initial();">
		<form name="form1" method="post" target="_self">
			<input type="text" name="dummy" class="dummy" disabled>
			<input type="hidden" name="cancelSw" value="0">
			<input type="hidden" name="before_servlet" value="Hnmslj0010b">
			<!-- HIDDEN設定 menuから -->
			<input type="hidden" name="user_renban" value="<%= dba.user_renban %>">
			<input type="hidden" name="user_namecd" value="<%= dba.user_namecd %>">
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
							<col width="310px">
							<tr>
								<th colspan="4">検索条件入力</th>
							</tr>
							<tr>
								<th style="color: #ff0000;background-color: #ffff00;">利用者コード</th>
								<td><input id="in_user_cd_search" type="text" class="input_text" style="width: 100%;" value="<%=  dba.in_user_cd_search  %>" disabled></td>
								<th>メールアドレス</th>
								<td><input id="in_mail_ad_search" type="text" class="code50" value="<%=  dba.in_mail_ad_search  %>" disabled></td>
							</tr>
						</table>
					</div>
					<!-- 検索部 ボタン  -->
					<div class="fr">
						<div class="shbtnfl" >
							<input type="button" id="search_btn" class="mid_button" value="検索" onclick="ModDisp();">
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
		<div class="main">
			<!-- 変更画面 HTML版 -->
			<div id="pushModDiv" >
				<div class="moddiv">
					<div class="shdiv">
						<div class="fl">
							<table class="shtab">
								<col width="90px">
								<col width="310px">
								<tr>
									<th>利用者名</th>
								<td><input name="in_user_nm_add"  type="text"  style="width: 100%;" class="user_name"  value="<%=  dba.in_user_nm_search  %>"  maxlength="25"  onblur="user_nm_chk(this)"></td>
								<input name="in_user_nm_hikaku" type="hidden" value="<%=  dba.in_user_nm_search  %>">
								</tr>
							</table>
						</div>
						<!-- 利用者変更 ボタン  -->
						<div class="fr">
							<div class="fl" >
								<input type="button" id="riyou_change_btn" class="mid_button" value="利用者変更"  onclick="click_ri();">&nbsp;
							</div>
							<div class="fr">
							</div>
							<div class="fc">
							</div>
						</div>
						<div class="fc" style="font-size: 9pt;">
							※利用者名の変更がある場合はデータ変更・追加・削除の作業前に実施してください。<br>
						</div>
						<br>
						<br>
					</div>
					<!-- REF-DEL MAIN -->
					<div class="modhedder" id="modHedDiv">
						<table class="reginfotab">
							<col width="35px">
							<col width="310px">
							<col width="72px">
							<col width="72px">
							<col width="65px">
							<col width="65px">
							<col width="65px">
							<col width="65px">
							<col width="45px">
							<tr>
								<th colspan="9">登録済みアドレス一覧表示</th>
							</tr>
							<tr>
								<th rowspan="2" >
									NO.
								</th>
								<th rowspan="2">
									メールアドレス
								</th>
								<th rowspan="2" >
									有効開始日
								</th>
								<th rowspan="2">
									有効終了日
								</th>
								<th colspan="4">
									配信設定
								</th>
								<th rowspan="2" style="background-color: #ff0000;">
									削除
								</th>
							</tr>
							<tr>
								<th>
									緊急<BR>カット商品
								</th>
								<th>
									在庫切れ<BR>速報<BR>(12時締)
								</th>
								<th>
									在庫切れ<BR>速報<BR>(22時締)
								</th>
								<th>
									計画終了<BR>対象商品<BR>(日次)
								</th>
							</tr>
						</table>
					</div>
					<div class="moddatal" id="modDatDiv">
						<table class="reginfotab" id="modDetalTbl">
							<col width="35px" align="center">
							<col width="310px">
							<col width="72px">
							<col width="72px">
							<col width="65px">
							<col width="65px">
							<col width="65px">
							<col width="65px">
							<col width="45px">
						<!--  ******** MEISAI START ********************************* -->
						<%= CollectionUtility.cnvrtStrngLnFrmVctr( dba.MEISAI_HTMLs ) %>
						<!--  ******** MEISAI END ********************************* -->
						</table>
					</div>
					<div style="margin-top: 5px;" id="oyama0">
						<table class="addtab">
							<col width="35px">
							<col width="310px">
							<col width="72px">
							<col width="72px">
							<col width="65px">
							<col width="65px">
							<col width="65px">
							<col width="65px">
							<tr>
								<th colspan="8">追加アドレス記入欄</th>
							</tr>
						</table>
					</div>
					<div class="adddatal" id="oyama">
					<table class="addtab">
						<col width="35px" align="center">
						<col width="310px">
						<col width="72px">
						<col width="72px">
						<col width="65px">
						<col width="65px">
						<col width="65px">
						<col width="65px">
						<!--  ******** MEISAI START ********************************* -->
						<%= CollectionUtility.cnvrtStrngLnFrmVctr( dba.MEISAI_HTMLs2 ) %>
						<!--  ******** MEISAI END ********************************* -->
					</table>
				</div>
			</div>
		</div>
	</div>
			<!--  ******** MAIN END ********************************* -->
			<!--  ******** FOOTER START ********************************* -->
			<div class="footer" >
				<table border="0" cellpadding="0" cellspacing="0" >
					<tr>
						<td style="background-color: #CCFFFF;"align="right">
							<input type="button" class="big_button" name="btn_change" value="変更" onclick="click_change();">
							<input type="button" class="big_button" name="btn_back" value="戻る" onclick="click_back();">
						</td>
					</tr>
				</table>
			</div>
			<!--  ******** FOOTER END ********************************* -->
			<!--  ******** BODY END *********************************** -->
			<!-- 画面操作不可用の透明＜レイヤ画面２＞ -->
			<div style="position:absolute; left:0px; top:0px;display:none; width:100%; height:100%;
			            background-color:#ffffff;  filter: alpha(style=0, opacity=0)" id="divlayer_m">
			</div>
			<!--  ******** 子画面イメージのレイヤ画面1 START ****************** -->
		<!-- LAYER -->
		<table width="840px" border="0" cellpadding="0" cellspacing="0"
				style="position: absolute;top:100px;left:20px;display:none;" id="ModCheckLayer">
			<tr>
				<td align="center" class="drag">
					<table width="800px" border="0" cellpadding="0" cellspacing="0" style="background-color:#CCFFFF ;cursor: auto; position:static; left:10px;">
						<tr>
							<td align="center">
								<table width="794px" border="0" cellpadding="0" cellspacing="0">
								<col width="130" align="left">
								<col width="150" align="left">
								<col width="1" align="left">
									<tr>
										<td nowrap class="ScreenHeader">画面ID:SHNMS0011</td>
										<td id="gamen_nn" nowrap class="ScreenHeader">画面名：変更/削除　確認画面</td>
										<td nowrap class="ScreenClock"></td>
									</tr>
								</table>
								<br>
					<table width="783px" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
						<table class="shtab">
							<col width="80px">
							<col width="80px">
							<col width="90px">
							<col width="310px">
							<tr>
								<th style="color: #ff0000;background-color: #ffff00;">利用者コード</th>
								<td><input id="in_user_cd_search" type="text" class="input_text" style="width: 100%;" value="<%=  dba.in_user_cd_search  %>" disabled></td>
								<th>利用者名</th>
								<td><input name="in_user_nm_change" type="text" class="input_text" style="width: 100%;" value="<%=  dba.in_user_nm_search  %>"  disabled></td>
							</tr>
						</table>
							</td>
						</tr>
					</table>
								<br>
								<table width="783px" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<div>
												<table class="reginfotab2">
													<col width="35px">
													<col width="310px">
													<col width="72px">
													<col width="72px">
													<col width="65px">
													<col width="65px">
													<col width="65px">
													<col width="65px">
													<tr>
														<th colspan="8">変更内容一覧表示</th>
													</tr>
													<tr>
														<th rowspan="2" >
															NO.
														</th>
														<th rowspan="2">
															メールアドレス
														</th>
														<th rowspan="2" >
															有効開始日
														</th>
														<th rowspan="2">
															有効終了日
														</th>
														<th colspan="4">
															配信設定
														</th>
													</tr>
													<tr>
														<th>
															緊急<BR>カット商品
														</th>
														<th>
															在庫切れ<BR>速報<BR>(12時締)
														</th>
														<th>
															在庫切れ<BR>速報<BR>(22時締)
														</th>
														<th>
															計画終了<BR>対象商品<BR>(日次)
														</th>
													</table>
										 	</div>
													<div class="moddatal2" id="layer_detail" >
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>

							<td align="center">
								<div class="fd">
								</div>
								<div class="fd" style="font-size: 9pt;">
									上記の内容で問題無ければ「確定」をして下さい
								</div>
								<input type="button" class="btn_mini" name="btn_confirm" value="確定"
								       onclick="click_confirm();">&nbsp;
								<input type="button" class="btn_mini" name="btn_close" value="閉じる" onclick="modChklayerPop();" >
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
			<!--  ******** 子画面イメージ＜レイヤ画面１＞ END ******************** -->
			<!-- お待ちくださいを表示する＜レイヤ画面３＞ -->
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
