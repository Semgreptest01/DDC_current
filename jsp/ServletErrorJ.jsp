<%@ page contentType="text/html;charset=Windows-31J"%>
<%@ page import="util.DateTimeUtility"%>
<%@ include file="JspHeaderJ.jsp"%>
<!--
// (JSPの履歴確認やDeploy後のJSP差替確認に使用するので必ずHTMLコメントで記述すること。)
// *---------------------------------------------------------------------
// * システム　　名称 : 共通
// * 画面名称		  : エラー画面（ServletErrorJ.jsp）
// * 会社名or所属名   : 株式会社ヴィクサス
// * 作成日 		  : 2012/11/06 00:00:00
// * 作成者 		  : Y.Takabayashi
// * *** 修正履歴 ***
// * No.  Date          Author      Description
// *---------------------------------------------------------------------
-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Shift_JIS">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<link rel="stylesheet" type="text/css"
	href="<%= CONT_DIR_PATH %>/css/common.css">
<title></title>
<script language="JavaScript"
	src="<%= CONT_DIR_PATH %>/js/app_common/app_common.js"></script>
<script language="JavaScript"
	src="<%= CONT_DIR_PATH %>/js/app_common/const.js"></script>
<script language="JavaScript"
	src="<%= CONT_DIR_PATH %>/js/common/common.js"></script>
<script language="JavaScript"
	src="<%= CONT_DIR_PATH %>/js/app_common/message.js"></script>
<script language="JavaScript">
<!--
// DECLARE_CONST_START
// DECLARE_CONST_END
	function initial() {
		document.title = "エラー画面";
		try{
			resizeTo( SUB_WINDOW_WIDTH, SUB_WINDOW_HEIGHT );
			self.moveTo( SUB_WINDOW_LEFT, SUB_WINDOW_TOP );
		} catch(e) {};
		document.form1.btnCls.focus();
	}
// -->
</script>
</head>
<body topmargin="5" leftmargin="10" onload="initial();">
<form name="form1" method="post" target="_self"><input
	type="text" name="dummy" class="dummy" disabled>
<table width="715" height="49" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td>
			<table border="1" width="714" bgcolor="#8080ff">
				<tr>
					<td nowrap bgcolor="#0000ff" width="360" style="font-size: 9pt; color: #ffffff;">
						<b>SHNMS9999 エラー画面</b>
					</td>
					<td nowrap bgcolor="#b5b5ff" align="right" style="font-size: 9pt;">|
						<input type="button" class="bttn_lnk" style="width: 30px; text-decoration: none; color: #ff0000;"
						name="bttnClose0" value="終了" onclick="closeClick();">|
						&nbsp;<%= DateTimeUtility.curr_Timestamp() %>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td><textarea rows="18" id="msg_out" class="msg_box_err" readonly><%= CollectionUtility.cnvrtStrngLnFrmVctr( msgBx ) %></textarea>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td class="err_msg">サーバー処理でエラーが発生しました。（上記内容を確認して下さい。）</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="center"><input type="button" class="bttn_dflt"
			name="btnCls" value="閉じる" onclick="window.close();"></td>
	</tr>
</table>
</form>
</body>
</html>
<%@ include file="JspFooterJ.jsp"%>
