<%@ page import="java.io.BufferedInputStream" %><%@ page import="java.io.BufferedOutputStream" %><%@ page import="java.io.FileInputStream" %><%
	String filePath = config.getServletContext().getRealPath( config.getInitParameter( "filePath" ) );
	String fileName = new String( config.getInitParameter( "fileName" ).getBytes("EUC-JP"), "ISO8859_1" );
	
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