<%@ page import="java.lang.*"%><%@ page import="java.util.*"%><%@ page
	import="java.io.*"%><%@ page import="java.net.*"%>
<%
	String filePath = config.getServletContext().getRealPath( config.getInitParameter( "filePath" ) );
	String fileName = new String( config.getInitParameter( "fileName" ).getBytes("Shift_JIS") , "ISO8859_1" );
	
	BufferedInputStream inptStrm = null;
	BufferedOutputStream otptStrm = null;
	try {
		inptStrm = new BufferedInputStream( new FileInputStream( filePath ) );
		otptStrm = new BufferedOutputStream( response.getOutputStream() );
		byte buf[] = new byte[ 1024 ];
		int len;
		response.setContentType( "application/octet-stream" );
		response.setHeader( "Content-Disposition", "attachment; filename=\"" + fileName + "\"" );
		while ( ( len = inptStrm.read( buf ) ) != -1 ) {
			otptStrm.write( buf, 0, len );
		}
		otptStrm.flush();
	} finally {
		if ( otptStrm != null ) {
			otptStrm.close();
		}
		if ( inptStrm != null ) {
			inptStrm.close();
		}
	}
%>