<%@ page import="util.CollectionUtility" %>
<%@ page contentType="text/html;charset=EUC-JP" %>
<%@ include file="JspHeaderJ.jsp" %>
<!--
// (JSPの履歴確認やDeploy後のJSP差替確認に使用するので必ずHTMLコメントで記述すること。)
// *---------------------------------------------------------------------
// * システム　　名称 : 共通
// * 画面名称		  : サービス時間チェック画面（TimeCheckJ.jsp）
// * 会社名or所属名   : 株式会社ヴィクサス
// * 作成日 		  : 2006/11/08 00:00:00
// * 作成者 		  : Y.Takabayashi
// * *** 修正履歴 ***
// * No.  Date          Author      Description
// *---------------------------------------------------------------------
-->
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-JP">
		<meta http-equiv="Content-Style-Type" content="text/css">
		<meta http-equiv="Content-Script-Type" content="text/javascript">
		<link rel="stylesheet" type="text/css" href="<%= CONT_DIR_PATH %>/css/common.css">
		<title></title>
		<script language="JavaScript" src="<%= CONT_DIR_PATH %>/js/app_common/const.js"></script>
		<script language="JavaScript" src="<%= CONT_DIR_PATH %>/js/common/common.js"></script>
		<script language="JavaScript" src="<%= CONT_DIR_PATH %>/js/app_common/message.js"></script>
		<script language="JavaScript">
<!--
// DECLARE_CONST_START
// DECLARE_CONST_END
	function initial() {
		document.title = "サービス時間チェック画面";
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
		<form name="form1" method="post" target="_self">
			<input type="text" name="dummy" class="dummy" disabled>
			<table width="715" height="49" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<table width="710" height="49" class="header">
							<col width="200">
							<col width="510">
							<tr>
								<td>画面ID：S<%= Config.APP_NM %>9991</td>
								<td>画面名：サービス時間チェック画面</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<textarea rows="18" id="msg_out" class="msg_box_err" readonly><%= CollectionUtility.cnvrtSprtdLnStrngFrmVctr( msgBx ) %></textarea>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td class="err_msg">
						現在、この画面はサービス出来ません。
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td align="center">
						<input type="button" class="bttn_dflt" name="btnCls" value="閉じる" onclick="window.close();">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<%@ include file="JspFooterJ.jsp" %>
