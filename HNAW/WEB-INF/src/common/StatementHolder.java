/**
*-------------------------------------------------------------------------------
* クラス名			StatementHolder
* システム名称		共通
* 名称				Statement保持クラス
* 会社名or所属名	富士ソフトＤＩＳ
* 作成日			2006/11/08 00:00:00
* @author			Y.Takabayashi
* @since			1.0
* @version			1.0
* *** 修正履歴 ***
* No.	Date		Author			Description
*-------------------------------------------------------------------------------
*/
package common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import util.CommonUtility;
import util.MessageUtility;
import util.TextUtility;

public class StatementHolder extends Object implements Cloneable, java.io.Serializable
{
	private static final long	serialVersionUID = 1L;
	// SQLステートメント
	/**
	 * SQL文
	 */
	private Vector	sqlSttmnts	= new Vector();
	/**
	 * SQLログSW
	 */
	private Vector	sqlLgSws	= new Vector();
	/**
	 * HTML文
	 */
	private Vector	htmlSttmnts	= new Vector();
	
	/**
	********************************************************************************
	* Statementファイル読み込み
	* @param	srvltCntxt	(ServletContext)
	* @param	flNm		(String)
	* @return	なし
	********************************************************************************
	*/
	public void readSttmntFl(	ServletContext	srvltCntxt,	String	flNm	) throws Exception
	{
		// クリア
		sqlSttmnts.clear();
		htmlSttmnts.clear();
		sqlLgSws.clear();
		
		if ( flNm != null && !"".equals( flNm ) ) {
			String lnStrng = "";
			String tmpStrng = "";
			String sqlLf = "";
			if ( Const.SW_ON.equals( Config.SQL_LF_SW ) ) {
				sqlLf = "\n";
			}
			int sqlNmbr = -1;
			int htmNmbr = -1;
			boolean rdSqlFlg = false;
			boolean rdHtmlFlg = false;
			FileInputStream flInptStrm = null;
			InputStreamReader inptStrmRdr = null;
			BufferedReader bffrdRdr = null;
			try {
				flInptStrm = new FileInputStream( Config.getSttmntFlDrctryPth( srvltCntxt ) + "/" + flNm );
				inptStrmRdr = new InputStreamReader( flInptStrm, Config.CHAR_SET );
				bffrdRdr = new BufferedReader( inptStrmRdr );
				while ( bffrdRdr.ready() ) {
					lnStrng = bffrdRdr.readLine();
					if ( !lnStrng.startsWith( "//" ) ) {
						if ( lnStrng.length() > 4 ) {
							if ( lnStrng.startsWith( "<SQL" ) ) {
								// <SQLnnn log="n">
								rdSqlFlg = true;
								tmpStrng = sqlLf;
								sqlLgSws.add( lnStrng.substring( lnStrng.length() - 3, lnStrng.length() - 2 ) );
								if ( Integer.parseInt( lnStrng.substring( 4, lnStrng.indexOf( ' ' ) ) ) != ++sqlNmbr ) {
									throw new Exception( CommonUtility.getExcptnMssg( MessageUtility.DBA_MSG_ID_07, lnStrng.substring( 1, lnStrng.indexOf( ' ' ) ) ) );
								}
								continue;
							} else if ( lnStrng.startsWith( "</SQL" ) ) {
								// </SQL>
								rdSqlFlg = false;
								sqlSttmnts.add( tmpStrng );
								if ( Integer.parseInt( lnStrng.substring( 5, lnStrng.indexOf( '>' ) ) ) != sqlNmbr ) {
									throw new Exception( CommonUtility.getExcptnMssg( MessageUtility.DBA_MSG_ID_07, lnStrng.substring( 2, lnStrng.indexOf( '>' ) ) ) );
								}
								continue;
							} else if ( lnStrng.startsWith( "<HTM" ) ) {
								// <HTMnnn>
								rdHtmlFlg = true;
								tmpStrng = "";
								if ( Integer.parseInt( lnStrng.substring( 4, lnStrng.indexOf( '>' ) ) ) != ++htmNmbr ) {
									throw new Exception( CommonUtility.getExcptnMssg( MessageUtility.DBA_MSG_ID_07, lnStrng.substring( 1, lnStrng.indexOf( '>' ) ) ) );
								}
								continue;
							} else if ( lnStrng.startsWith( "</HTM" ) ) {
								// </HTMnnn>
								rdHtmlFlg = false;
								htmlSttmnts.add( tmpStrng );
								if ( Integer.parseInt( lnStrng.substring( 5, lnStrng.indexOf( '>' ) ) ) != htmNmbr ) {
									throw new Exception( CommonUtility.getExcptnMssg( MessageUtility.DBA_MSG_ID_07, lnStrng.substring( 2, lnStrng.indexOf( '>' ) ) ) );
								}
								continue;
							}
						}
						if ( rdSqlFlg ) {
							tmpStrng += lnStrng + sqlLf;
						} else if ( rdHtmlFlg ) {
							tmpStrng += lnStrng + "\n";
						}
					}
				}
			} finally {
				if ( flInptStrm != null ) { flInptStrm.close(); }
				if ( inptStrmRdr != null ) { inptStrmRdr.close(); }
				if ( bffrdRdr != null ) { bffrdRdr.close(); }
			}
		}
	}
	/**
	********************************************************************************
	* SQLステートメント取得<br>
	* ※whrArgmntsへ置換え変数を入れてコールする。（リターン時にクリア）<br>
	* （例）<br>
	* whrArgmnts.add( xxxx );<br>
	* String sql = getSQL( 1 );
	* @param	idx	index								(int)
	* @param	whrArgmnts								(Vector)
	* @return	SqlStatement(見つからない場合は、"")	(String)
	********************************************************************************
	*/
	public String getSQL(	int	idx,	Vector	whrArgmnts	) throws Exception
	{
		String sql = "";
		String names = "";
		if ( sqlSttmnts.size() >= idx ) {
			sql = (String)sqlSttmnts.get( idx );
			for ( int i = 0; i < whrArgmnts.size(); i++ ) {
				names = TextUtility.escapeRplcStrng( (String)whrArgmnts.get( i ) );
				sql = Pattern.compile( "\\[&" + String.valueOf( i ) + "\\]" ).matcher( sql ).replaceAll( names );
			}
			if ( Const.SW_ON.equals( sqlLgSws.get( idx ) ) ) {
				CommonUtility.debugPrint( sql );
			}
		}
		// クリア
		whrArgmnts.clear();
		return sql;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* PreparedSQL取得
	* @param	idx	index								(int)
	* @return	SqlStatement(見つからない場合は、"")	(String)
	********************************************************************************
	*/
	public String getPreparedSQL(	int	idx	) throws Exception
	{
		String sql = "";
		if ( sqlSttmnts.size() >= idx ) {
			sql = (String)sqlSttmnts.get( idx );
			sql = Pattern.compile( "\\[&\\d+\\]" ).matcher( sql ).replaceAll( "?" );
			if ( Const.SW_ON.equals( sqlLgSws.get( idx ) ) ) {
				CommonUtility.debugPrint( sql );
			}
		}
		return sql;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* HTML取得<br>
	* ※htmlArgmntsへ置換え変数を入れてコールする。（リターン時にクリア）<br>
	* （例）<br>
	* htmlArgmnts.add( xxxx );<br>
	* String html = getHTML( 1 );
	* @param	idx		index					(int)
	* @param	htmlArgmnts						(Vector)
	* @return	HTML(見つからない場合は、"")	(String)
	********************************************************************************
	*/
	public String getHTML(	int	idx,	Vector	htmlArgmnts	) throws Exception
	{
		String s		= "";
		String names	= "";
		if ( htmlSttmnts.size() >= idx ) {
			s = (String)htmlSttmnts.get( idx );
			for ( int j = 0; j < htmlArgmnts.size(); j++ ) {
				names = TextUtility.escapeRplcStrng( (String)htmlArgmnts.get( j ) );
				s = Pattern.compile( "\\[&" + String.valueOf( j ) + "\\]" ).matcher( s ).replaceAll( names );
			}
		}
		// クリア
		htmlArgmnts.clear();
		return s;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* HTMLとSQLのパラメータ用文字列取得
	* @param	i									(int)
	* @return	HTMLとSQLのパラメータ用文字列取得	(String)
	********************************************************************************
	*/
	public String getPrmtrStrng(	int	i	)
	{
		return "[&" + String.valueOf( i ) + "]";
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* オブジェクトのクローン取得<br>
	* @return	オブジェクトのクローン	(Object)
	********************************************************************************
	*/
	public Object clone()
	{
		try {
			StatementHolder s = (StatementHolder)super.clone();
			s.sqlSttmnts = (Vector)sqlSttmnts.clone();
			s.sqlLgSws = (Vector)sqlLgSws.clone();
			s.htmlSttmnts = (Vector)htmlSttmnts.clone();
			return s;
		} catch ( CloneNotSupportedException e ) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
