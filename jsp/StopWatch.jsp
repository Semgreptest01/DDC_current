<%@ page import="java.io.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.lang.*"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%
try{
	if("1".equals(common.Config.STOP_WATCH_SW)){
		SimpleDateFormat sdf    = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
		Calendar calendar       = Calendar.getInstance();
		java.util.Date nowTime  = calendar.getTime();
		String StartDTime       = (String)session.getAttribute( "StartDTime" );
		String ServletNm        = (String)session.getAttribute( "ServletNm" );
		String JspNm            = this.getClass().getName();
		String EndDTime         = sdf.format(nowTime);
		String InfMsg           = ServletNm+" -> "+JspNm;
		String elaps            = "";
		long wk_from            = 0;
		long wk_to              = 0;
		long wk_result          = 0;
		String [] fromArray		= new String[2];
		String [] from_tmArray	= new String[4];
		String [] toArray		= new String[2];
		String [] to_tmArray	= new String[4];
		if( StartDTime == null || "".equals(StartDTime) ||
			EndDTime   == null || "".equals(EndDTime) ){
		}else{
			fromArray    = StartDTime.split(" ");
			from_tmArray = fromArray[1].split(":");
			toArray      = EndDTime.split(" ");
			to_tmArray   = toArray[1].split(":");
			wk_from      = Long.parseLong(from_tmArray[0]) * 60 * 60 * 1000;
			wk_from     += Long.parseLong(from_tmArray[1]) * 60 * 1000;
			wk_from     += Long.parseLong(from_tmArray[2]) * 1000;
			wk_from     += Long.parseLong(from_tmArray[3]);
			wk_to        = Long.parseLong(to_tmArray[0]) * 60 * 60 * 1000;
			wk_to       += Long.parseLong(to_tmArray[1]) * 60 * 1000;
			wk_to       += Long.parseLong(to_tmArray[2]) * 1000;
			wk_to       += Long.parseLong(to_tmArray[3]);
			if( wk_to > wk_from ){
				wk_result = wk_to - wk_from;
			}else{
				wk_to += 86400000;
				wk_result = wk_to - wk_from;
			}
			elaps = String.valueOf(wk_result);
			//System.out.println( "#jsp# "+InfMsg+" ELAPS:"+elaps );
			session.setAttribute( "JspNm", JspNm );
			session.setAttribute( "Elaps", elaps );
		}
	}
}catch( java.lang.Exception e ){
    System.out.println(this.getClass().getName() +":" + e.toString());
}
%>
<script language="JavaScript">
<!--
var stop_watch_sw = "<%= common.Config.STOP_WATCH_SW %>";
var elaps         = "<%= (String)session.getAttribute( "Elaps" ) %>";
var ServletNm     = "<%= (String)session.getAttribute( "ServletNm" ) %>";
var JspNm         = "<%= (String)session.getAttribute( "JspNm" ) %>";
var Sec           = "";
var Msec          = "";
var InfMsg        = "";
var wk_array1     = new Array();
wk_array1 = JspNm.split(".");
var idx = wk_array1.length - 1;
if( idx > 0 ){
	JspNm = wk_array1[idx];
}
if( stop_watch_sw == "1" ){
	if( elaps.length > 3 ){
		Sec   = elaps.substring(0,elaps.length-3);
		Msec  = elaps.substring(elaps.length-3);
	}else{
		Sec   = "0";
		Msec  = elaps.substring(0);
	}
	InfMsg    = "*StopWatch* "+ServletNm + " -> "+ JspNm +
	          " ( "+Sec+"."+Msec+" second )";
	status=InfMsg;
}
// -->
</script>
