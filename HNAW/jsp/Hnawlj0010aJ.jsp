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
		<script language="JavaScript" src="<%= CONT_DIR_PATH %>/js/common/ajax.js"></script>
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
var JSP_NM			=	A_JSP;
var NXT_SRVLT0		=	"Hnawlj0010a";
var NXT_SRVLT1		=	"Hnawlj0010Ajax";
var NXT_SRVLT2		=	"Hnawlj0010d";
var CSV_CNT_MX		=	<%= dba.CSV_CNT_MX %>;
var SPR_USR_FLG		=	<%= dba.sprUsrFlg ? "true" : "false" %>;
var USR_CD_ERR_ALRT_FLG	=	<%= dba.usrCdErrAlrtFlg ? "true" : "false" %>;
// DECLARE_CONST_END
	function initial() {
		window.onunload = unloadWindow;
		document.title = "<%= dba.scrnNm %>";
		try{
			resizeTo( WINDOW_WIDTH, WINDOW_HEIGHT );
			self.moveTo( WINDOW_LEFT, WINDOW_TOP );
		} catch(e) {}
		with ( document.form1 ) {
			slctCntrCd.value = "<%= dba.cntrCd %>";
			slctTpAnniYrwkNoStrt.value = "<%= dba.tpAnniYrwkNoStrt %>";
			slctTpAnniYrwkNoEnd.value = "<%= dba.tpAnniYrwkNoEnd %>";
		}
		enableAll();
		with ( document.form1 ) {
			if ( USR_CD_ERR_ALRT_FLG ) {
				dspMsg( "F0027" );
				bttnClose.focus();
			} else if ( slctTpAnniYrwkNoStrt.options.length == 0 ) {
				dspMsg( "F0028" );
				if ( SPR_USR_FLG ) {
					bttnSubmit0.focus();
				} else {
					bttnClose.focus();
				}
			} else {
				bttnSubmit1.focus();
			}
		}
	}
// -->
</script>
	</head>
	<body topmargin="5" leftmargin="10" onload="initial();">
		<form name="form1" method="post" target="_self">
			<input type="text" name="dummy" class="dummy" disabled>
			<input type="hidden" name="cntrCd" value="">
			<input type="hidden" name="tpAnniYrwkNoStrt" value="">
			<input type="hidden" name="tpAnniYrwkNoEnd" value="">
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
									<span<%= dba.sprUsrFlg ? " class=\"dsply_nn\"" : "" %>><%= dba.usrCd %></span>
									<span<%= dba.sprUsrFlg ? "" : " class=\"dsply_nn\"" %>>
										<input type="text" class="code7" name="txtUsrCd" value="<%= dba.usrCd %>" size="7" maxlength="7" disabled>
									</span>
								</td>
							</tr>
							<tr height="26">
								<td class="title_column"><b>利用者名</b></td>
								<td class="column">
									<%= TextUtility.cvtBlnkToHtmlSpc( dba.usrNm ) %>
								</td>
							</tr>
						</table>
					</td>
					<td valign="top">
						<table width="150" border="0">
							<tr>
								<td align="left"<%= dba.sprUsrFlg ? "" : " class=\"dsply_nn\"" %>>
									<input type="button" class="bttn_dflt" name="bttnSubmit0" value="戻る" onclick="backClick();" disabled>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr height="10"><td colspan="3"></td></tr>
				<tr>
					<td></td>
					<td>
						<table width="440" border="1" cellpadding="0" cellspacing="0">
							<col width="105">
							<col width="335">
							<tr height="26">
								<td class="title_column"><b>センター名</b></td>
								<td class="column">
									<select name="slctCntrCd" class="cntr_cd" disabled>
<%= dba.optnStrng_cntrCd %>
									</select>
								</td>
							</tr>
						</table>
					</td>
					<td></td>
				</tr>
				<tr height="10"><td colspan="3"></td></tr>
				<tr>
					<td></td>
					<td>
						<table width="440" border="1" cellpadding="0" cellspacing="0">
							<col width="105">
							<col width="335">
							<tr height="110">
								<td class="title_column"><b>店案内週</b></td>
								<td class="column">
									<table width="315" border="0">
										<col width="150" align="right">
										<col width="15" align="center">
										<col width="150" align="left">
										<tr>
											<td>
												<select name="slctTpAnniYrwkNoStrt" class="tp_anni_yrwk_no" size="7" disabled>
<%= dba.optnStrng_tpAnniYrwkNo %>
												</select>
											</td>
											<td>〜</td>
											<td>
												<select name="slctTpAnniYrwkNoEnd" class="tp_anni_yrwk_no" size="7" disabled>
<%= dba.optnStrng_tpAnniYrwkNo %>
												</select>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
					<td></td>
				</tr>
				<tr height="10"><td colspan="3"></td></tr>
				<tr>
					<td></td>
					<td align="center">
						<input type="button" class="bttn_dwnld" name="bttnSubmit1" value="ダウンロード" onclick="submit1Click();" disabled>
					</td>
					<td></td>
				</tr>
				<tr height="10"><td colspan="3"></td></tr>
				<tr height="15">
					<td></td>
					<td align="center">
						※一度にダウンロードできるのは<%= TextUtility.c_Edit( dba.CSV_CNT_MX ) %>件までです。
					</td>
					<td></td>
				</tr>
			</table>
		</form>
	</body>
</html>
<%@ include file="JspFooterJ.jsp" %>
