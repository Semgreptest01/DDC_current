<%@ page import="java.lang.*"%>
<%@ page import="java.util.*"%>
<%@ page import="common.Config"%>
<%@ page import="util.CollectionUtility"%>
<%@ page import="util.TextUtility"%>
<%
try {
	String CONT_DIR_PATH	= Config.CONT_DIR_PATH;
	Vector msgBx			= (Vector)request.getAttribute( "msgBx" );
%>
<%@ include file="/jsp/StopWatch.jsp"%>
