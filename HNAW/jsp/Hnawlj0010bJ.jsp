<%@ page import="util.TextUtility" %>
<%@ page contentType="text/html;charset=EUC-JP" %>
<jsp:useBean id="Hnawlj0010DBAccess" scope="session" class="app.Hnawlj0010DBAccess" />
<% app.Hnawlj0010DBAccess dba = Hnawlj0010DBAccess; %>
<%@ include file="JspHeaderJ.jsp" %>
<!--
// *----------------------------------------------------------------------------
// * システム		:	計画終了対象商品案内
// * 画面			:	HNAW0010 計画終了対象商品ダウンロード
// * 会社名or所属名	:	株式会社ヴィクサス
// * 作成日			:	2010/01/18
// * 作成者			:	Hirata
// * *** 修正履歴 ***
// * No.	Date		Author		Description
// *----------------------------------------------------------------------------
-->
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-JP">
		<meta http-equiv="Content-Style-Type" content="text/css">
		<link rel="stylesheet" type="text/css" href="<%= CONT_DIR_PATH %>/css/common.css">
		<title></title>
		<script language="JavaScript" src="<%= CONT_DIR_PATH %>/js/common/check_simple.js"></script>
		<script language="JavaScript" src="<%= CONT_DIR_PATH %>/js/common/common.js"></script>
		<script language="JavaScript" src="<%= CONT_DIR_PATH %>/js/app_common/app_common.js"></script>
		<script language="JavaScript" src="<%= CONT_DIR_PATH %>/js/app_common/const.js"></script>
		<script language="JavaScript" src="<%= CONT_DIR_PATH %>/js/app_common/help.js"></script>
		<script language="JavaScript" src="<%= CONT_DIR_PATH %>/js/app_common/message_check_simple.js"></script>
		<script language="JavaScript" src="<%= CONT_DIR_PATH %>/js/Hnawlj0010.js"></script>
		<script language="JavaScript">
<!--
// DECLARE_CONST_START
var SESSION_ARG_NM	=	"<%= dba.sssnArgNm %>";
var WINDOW_ID		=	"<%= dba.scrnId %>";
var B_JSP			=	"Hnawlj0010bJ.jsp";
var A_JSP			=	"Hnawlj0010aJ.jsp";
var JSP_NM			=	B_JSP;
var NXT_SRVLT0		=	"Hnawlj0010a";
var USR_CD_ERR_ALRT_FLG	=	<%= dba.usrCdErrAlrtFlg ? "true" : "false" %>;
// DECLARE_CONST_END
	function initial() {
		window.onunload = unloadWindow;
		document.title = "<%= dba.scrnNm %>";
		try{
			resizeTo( WINDOW_WIDTH, WINDOW_HEIGHT );
			self.moveTo( WINDOW_LEFT, WINDOW_TOP );
		} catch(e) {}
		enableAll();
		with ( document.form1 ) {
			if ( USR_CD_ERR_ALRT_FLG ) {
				dspMsg( "F0026" );
			}
			txtUsrCd.focus();
		}
	}
// -->
</script>
	</head>
	<body topmargin="5" leftmargin="10" onload="initial();">
		<form name="form1" method="post" target="_self">
			<input type="text" name="dummy" class="dummy" disabled>
			<input type="hidden" name="usrCd" value="">
			<input type="hidden" name="cancelSw" value="0">
<%= dba.getHdrHtml( msgBx ) %>
			<table width="965" border="0" cellpadding="0" cellspacing="0">
				<col width="253">
				<col width="450" align="center">
				<col width="252">
				<tr height="15">
					<td align="center" class="alrt_msg" colspan="3">
						<%= dba.msg1 %>
					</td>
				</tr>
				<tr height="15">
					<td align="center" class="alrt_msg" colspan="3">
						<%= dba.msg2 %>
					</td>
				</tr>
				<tr height="30"><td colspan="3"></td></tr>
				<tr>
					<td></td>
					<td>
						<table width="440" border="1" cellpadding="0" cellspacing="0">
							<col width="105">
							<col width="335">
							<tr height="26">
								<td class="title_column"><b>利用者コード</b></td>
								<td class="column">
									<input type="text" class="code7" name="txtUsrCd" value="<%= dba.usrCd %>" size="7" maxlength="7" disabled>
								</td>
							</tr>
							<tr height="26">
								<td class="title_column"><b>利用者名</b></td>
								<td class="column">
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
					<td valign="top">
						<table width="150" border="0">
							<tr>
								<td align="left">
									<input type="button" class="bttn_dflt" name="bttnSubmit0" value="検索" onclick="submit0Click();" disabled>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<%@ include file="JspFooterJ.jsp" %>
