/**
*--------------------------------------------------------------------------------
* クラス名			Dbcom.class
* システム名称		共通
* 名称				データベース接続・共通クラス
* 会社名or所属名	富士ソフトＤＩＳ
* 作成日			2006/05/25 00:00:00
* @author			Y.Takabayashi
* @since			1.0
* @version			1.1
* *** 修正履歴 ***
* No.  Date			Author		Description
*--------------------------------------------------------------------------------
*/
package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import util.CommonUtility;

public class Dbcom extends Object {
	// **************************************************************************
	// * **** 重要事項 ****		**** This Class should Not Change ! ****		*
	// * ・ Application Server 環境の切り替えは、Config.CON_SW の値で行う。		*
	// * （ Dbcom 側から全制御されますから、一切の変更は不要です）				*
	// *    con_sw  1:JNDI (iAS)    2:PrimalyPacket (Tomcat)  3:J2EE(J2SDKEE)   *
	// *            4:Bea_weblogic	5:RedHat_OracleiAS        6:Tomcat5(JNDI)   *
	// * ・iAS 設定																*
	// *	JNDI Data Source 登録は、iAS マシン上で（redReg -[File.xnl]）		*
	// *	を実行して、detasourceのプロパティを登録してください。				*
	// * ・J2SDKEE 設定															*
	// *	DataSource名 の変更は、J2EEエンジンconfig配下の						*
	// *	default.propertiesを変更して下さい。								*
	// * 【注意】																*
	// * ・改造する場合のDEBUG時には、msgBxの使用はしないほうが良いでしょう。	*
	// * System.out.println のログでDEBUGすれば基本構造を変更しないで済みます。	*
	// **************************************************************************
	/**
	********************************************************************************
	* DB Connection Process
	* @param	dbUser		接続ユーザーID	(String)
	* @param	dbPassword	接続パスワード	(String)
	* @return	DB接続Object				(Connection)
	********************************************************************************
	*/
	public static Connection dbConnect(	String	dbUser,	String	dbPassword	) throws Throwable
	{
		Connection con = null;
		try {
			if ( Const.CON_SW_TOMCAT_DRCT.equals( Config.CON_SW ) || Const.CON_SW_OAS_DRCT.equals( Config.CON_SW ) ) {
				// Direct ?
				String urlString = Config.THIN_DRIVER_URL;
				CommonUtility.debugPrint( "Dbcom:Direct Connection:" + Config.DRIVER_NAME + " " + urlString );
				// JDBC Driver Loading
				Class.forName( Config.DRIVER_NAME );
				con = DriverManager.getConnection( urlString, dbUser, dbPassword );
				// sun.jdbc.odbc.JdbcOdbcDriverを使用して接続するMS Accessには自動コミットモードしか存在しない
				if ( !"sun.jdbc.odbc.JdbcOdbcDriver".equals( Config.DRIVER_NAME.substring( 0, 28 ) ) ) {
					con.setAutoCommit( false );	
				}
			} else {
				String dsName = Config.JNDI_NAME;
				if ( Const.CON_SW_IAS.equals( Config.CON_SW ) ) {
					// iAS(iPlanet)
					CommonUtility.debugPrint( "Dbcom:iAS Connect dsName:" + dsName );
				} else if ( Const.CON_SW_TOMCAT_JNDI.equals( Config.CON_SW ) ) {
					// Tomcat5(JNDI)
					CommonUtility.debugPrint( "Dbcom:Tomcat5(JNDI) dsName:" + dsName );
					dsName = "java:comp/env/" + dsName;
				} else if ( Const.CON_SW_OAS_JNDI.equals( Config.CON_SW ) ) {	
				} else if ( Const.CON_SW_WEBLOGIC.equals( Config.CON_SW ) ) {
					// weblogic
					CommonUtility.debugPrint( "Dbcom:weblogic dsName:" + dsName );
				} else if ( Const.CON_SW_J2SDKEE.equals( Config.CON_SW ) ) {
					// J2SDKEE ?
				}
				InitialContext ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup( dsName );
				con = ds.getConnection();
				con.setAutoCommit( false );
			}
		} catch ( Throwable e ) {
			dbDisconnect( con );
			throw e;
		}
		return con;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* DB Connection Process( MS Access Connection )
	* @param	msUser		接続ユーザーID	(String)
	* @param	msPassword	接続パスワード	(String)
	* @return	DB接続Object				(Connection)
	********************************************************************************
	*/
	public static Connection msConnect(	String	msUser,	String	msPassword	) throws Throwable
	{
		Connection con = null;
		String driverNm = Config.MS_DRIVER_NAME;
		String urlString = Config.MS_DRIVER_URL;
		CommonUtility.debugPrint( "Dbcom:MDB Direct Connection:" + driverNm );
		try {
			// JDBC Driver Loading
			Class.forName( driverNm );
			con = DriverManager.getConnection( urlString, msUser, msPassword );
			// sun.jdbc.odbc.JdbcOdbcDriverを使用して接続するMS Accessには自動コミットモードしか存在しない
			if ( !"sun.jdbc.odbc.JdbcOdbcDriver".equals( Config.DRIVER_NAME.substring( 0, 28 ) ) ) {
				con.setAutoCommit( false );	
			}
		} catch ( Throwable e ) {
			dbDisconnect( con );
			throw e;
		}
		return con;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* DB切断
	* @param	con	DB接続Object	(Connection)
	* @return	なし
	********************************************************************************
	*/
	public static void dbDisconnect(	Connection	con	)
	{
		try {
			if ( con != null && !con.isClosed() ) {
				con.close();
				CommonUtility.debugPrint( "Dbcom:DB Disconnect" );
			}
		} catch ( Exception e ) {
			CommonUtility.debugPrint( "Dbcom:Disconnect Error !" );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* Transaction Commit
	* @param	con	DB接続Object	(Connection)
	* @return	なし
	********************************************************************************
	*/
	public static void dbCommit(	Connection	con	) throws SQLException
	{
		if ( con != null && !con.isClosed() ) {
			con.commit();
			CommonUtility.debugPrint( "Dbcom:DB Commit" );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* Transaction RollBack
	* @param	con	DB接続Object	(Connection)
	* @return	なし
	********************************************************************************
	*/
	public static void dbRollback(	Connection	con	) throws SQLException
	{
		if ( con != null && !con.isClosed() ) {
			con.rollback();
			CommonUtility.debugPrint( "Dbcom:DB Rollback" );
		}
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
