/**
*-------------------------------------------------------------------------------
* クラス名			UpDownloadUtility.class
* システム名称		共通
* 名称				ユーティリティ・アップロード・ダウンロードクラス
* 会社名or所属名	株式会社ヴィクサス
* 作成日			2006/11/08 00:00:00
* @author			Y.Takabayashi
* @since			1.0
* @version			1.0
* *** 修正履歴 ***
* No. Date			Author		Description
*-------------------------------------------------------------------------------
*/
package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import common.Config;
import common.Const;
import common.DBAccess;
import common.Dbcom;

public class UpDownloadUtility {
	/**
	********************************************************************************
	* CSVダウンロード用のsetContentType処理
	* @param	response	(HttpServletResponse)
	* @param	dba			(DBAccess)
	* @return	なし
	********************************************************************************
	*/
	public static void setCntntTypFrCsvDwnld(	HttpServletResponse	response,
												DBAccess			dba	)
	{
		response.setContentType( Const.CNTNT_TYP_DWNLD_CSV + Config.DWNLD_CSV_CHAR_SET );
		dba.stDwnldCntntTypFlg = true;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ファイルダウンロード用のsetContentType処理
	* @param	response	(HttpServletResponse)
	* @param	dba			(DBAccess)
	* @return	なし
	********************************************************************************
	*/
	public static void setCntntTypFrDwnld(	HttpServletResponse	response,
												DBAccess			dba	)
	{
		response.setContentType( Const.CNTNT_TYP_DWNLD );
		dba.stDwnldCntntTypFlg = true;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* XLSダウンロード用のsetContentType処理
	* @param	response	(HttpServletResponse)
	* @param	dba			(DBAccess)
	* @return	なし
	********************************************************************************
	*/
	public static void setCntntTypFrXlsDwnld(	HttpServletResponse	response,
												DBAccess			dba	)
	{
		response.setContentType( Const.CNTNT_TYP_XLS );
		dba.stDwnldCntntTypFlg = true;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ファイルダウンロード用のsetHeader処理
	* @param	response	(HttpServletResponse)
	* @param	flNm		(String)
	* @return	なし
	********************************************************************************
	*/
	public static void setDwnldHdr(	HttpServletResponse	response,
										String				flNm	) throws Exception
	{
		String wk_flNm = new String( flNm.getBytes("Shift_JIS"), "ISO8859_1" );
		response.setHeader( "Content-Disposition", "attachment;filename=\"" + wk_flNm + "\"" );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ファイルダウンロード用のsetHeader処理(URLEncoder使用)
	* （このやり方だとIE6（他の環境は未確認）では40バイトよりファイル名が長いと前が切り捨てられる）
	* @param	response	(HttpServletResponse)
	* @param	flNm		(String)
	* @return	なし
	********************************************************************************
	*/
	public static void setDwnldHdrWthURLEncd(	HttpServletResponse	response,
												String				flNm	) throws Exception
	{
		String wk_flNm = URLEncoder.encode( flNm, "UTF-8" );
		response.setHeader( "Content-Disposition", "attachment;filename=\"" + wk_flNm + "\"" );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* CSVの行のリストにcntの数だけ空白を設定
	* @param	csvLnDt	(List)
	* @param	cnt		(int)
	* @return	なし
	********************************************************************************
	*/
	public static void setBlnkToCsvLn(	List	csvLnDt,
											int		cnt	)
	{
		for ( int i = 0; i < cnt; i++ ) {
			csvLnDt.add( "" );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* リストの内容を繋げてCSVの行の文字列を取得
	* @param	csvLnDt			(List)
	* @param	addEqlFlg		(boolean)
	* @return	CSVの行の文字列	(String)
	********************************************************************************
	*/
	public static String getCsvLn(	List		csvLnDt,
									boolean	addEqlFlg	) throws Exception
	{
		StringBuffer bffr = new StringBuffer();
		for ( int i = 0; i < csvLnDt.size(); i++ ) {
			if ( i > 0 ) {
				bffr.append( Const.CSV_SPRTR );
			}
			String dtStr = (String)csvLnDt.get( i );
			if ( addEqlFlg ) {
				dtStr = ExcelUtility.addEql( dtStr );
			}
			bffr.append( dtStr );
		}
		csvLnDt.clear();
		return bffr.toString();
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* CSVファイルをダウンロードする
	* @param	response	(HttpServletResponse)
	* @param	dba			(DBAccess)
	* @param	csvDt		(List)
	* @param	flNm		(String)
	* @return	なし
	********************************************************************************
	*/
	public static void downloadCsv(	HttpServletResponse	response,
										DBAccess			dba,
										List				csvDt,
										String				dwnldFlNm	) throws Exception
	{
		setCntntTypFrCsvDwnld( response, dba );
		setDwnldHdr( response, dwnldFlNm );
		
		// HttpServletResponseのPrintWriterはエラー出力にも使うのでcloseしない
		PrintWriter prntWrtr = response.getWriter();
		for ( int i = 0; i < csvDt.size(); i++ ) {
			prntWrtr.println( csvDt.get( i ) );
		}
		prntWrtr.flush();
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* サーバ上のファイルをダウンロードする
	* @param	response	(HttpServletResponse)
	* @param	srvltCntxt	(ServletContext)
	* @param	dba			(DBAccess)
	* @param	rdFlPth		(String)
	* @param	dwnldFlNm	(String)
	* @return	なし
	********************************************************************************
	*/
	public static void downloadFl(	HttpServletResponse	response,
										ServletContext		srvltCntxt,
										DBAccess			dba,
										String				rdFlPth,
										String				dwnldFlNm	) throws Exception
	{
		BufferedInputStream inptStrm = null;
		BufferedOutputStream otptStrm = null;
		try {
			inptStrm = new BufferedInputStream( new FileInputStream( srvltCntxt.getRealPath( rdFlPth ) ) );
			otptStrm = new BufferedOutputStream( response.getOutputStream() );
			byte buf[] = new byte[ Const.BFFR_SIZE ];
			int len;
			setCntntTypFrDwnld( response, dba );
			setDwnldHdr( response, dwnldFlNm );
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
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 実行中SW判定（画面別、ユーザーＩＤ別）<br>
	* DBAccessクラスと連動しないメソッドのため、PreparedStatement Call SQLを直記述
	* @param	dbcom													(Dbcom)
	* @param	con														(Connection)
	* @param	gamen_kbn	画面ＩＤ									(String)
	* @param	user_id		ユーザーＩＤ								(String)
	* @param	data_kbn	データ区分（1:ダウンロード 2:アップロード）	(String)
	* @return	制御区分＋データ進捗率（％整数）						(String)
	********************************************************************************
	*/
	public static String chkWorkingSW(	Dbcom			dbcom,
										Connection		con,
										String			gamen_kbn,
										String			user_id,
										String			data_kbn) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String cntl_kbn = "0";
			String prc_rat = "0";
			String sql	=	"SELECT CTL_KBN, PRC_RAT"
						+	" FROM KF_IMK2060"
						+	" WHERE DTA_KBN = ?"
						+	" AND NAM_COD = ?"
						+	" AND SCN_ID = ?";
			pstmt = con.prepareStatement( sql );
			int itm_num = 1;
			pstmt.setString( itm_num++, data_kbn );
			pstmt.setString( itm_num++, user_id );
			pstmt.setString( itm_num++, gamen_kbn );
			rs = pstmt.executeQuery();
			if ( rs.next() ) {
				cntl_kbn	= rs.getString( "CTL_KBN" );
				prc_rat		= rs.getString( "PRC_RAT" );
			}
			// 制御区分が初期または開始の場合は、データ進捗率を０％にする。
			if("0".equals(cntl_kbn) || "1".equals(cntl_kbn) ) {
				prc_rat = "0";
			}
			// 制御区分が終了の場合は、データ進捗率を100％にする。
			if("9".equals(cntl_kbn)) {
				prc_rat = "100";
			}
			// 上記以外は、排他制御エンティティのデータ進捗率を返す。
			return cntl_kbn + prc_rat;
		} finally {
			if ( rs != null ) { rs.close(); }
			if ( pstmt != null ) { pstmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 実行中SW制御（画面別、ユーザーＩＤ別）<br>
	* DBAccessクラスと連動しないメソッドのため、PreparedStatement Call SQLを直記述
	* @param	con														(Connection)
	* @param	gamen_kbn	画面ＩＤ									(String)
	* @param	user_id		ユーザーＩＤ								(String)
	* @param	data_kbn	データ区分（1:ダウンロード 2:アップロード）	(String)
	* @return	なし
	********************************************************************************
	*/
	public static void resetWorkingSW(	Connection		con,
											String			gamen_kbn,
											String			user_id,
											String			data_kbn) throws Exception
	{
		PreparedStatement pstmt	= null;
		try {
			String sql	= "DELETE FROM KF_IMK2060"
						+ " WHERE DTA_KBN = ?"
						+ " AND NAM_COD = ?"
						+ " AND SCN_ID = ?";
			pstmt = con.prepareStatement( sql );
			int itm_num = 1;
			pstmt.setString( itm_num++, data_kbn );
			pstmt.setString( itm_num++, user_id );
			pstmt.setString( itm_num++, gamen_kbn );
			pstmt.executeUpdate();
			Dbcom.dbCommit( con );
			return;
		} finally {
			if ( pstmt != null ) { pstmt.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 実行中SW制御（画面別、ユーザーＩＤ別）<br>
	* DBAccessクラスと連動しないメソッドのため、PreparedStatement Call SQLを直記述
	* @param	con																		(Connection)
	* @param	gamen_kbn	画面ＩＤ														(String)
	* @param	user_id		ユーザーＩＤ													(String)
	* @param	data_kbn	データ区分（1:ダウンロード 2:アップロード）					(String)
	* @param	cntl_kbn	制御区分  （ 0:初期 1:開始 2:データ取得中 3:処理中 9:終了 ）	(String)
	* @param	prc_rat		データ進捗率（％整数）999の場合は、終了						(String)
	* @return	なし
	********************************************************************************
	*/
	public static void setWorkingSW(	Connection		con,
										String			gamen_kbn,
										String			user_id,
										String			data_kbn,
										String			cntl_kbn,
										String			prc_rat) throws Exception
	{
		PreparedStatement pstmt1	= null;
		PreparedStatement pstmt2	= null;
		try {
			String now		= DateTimeUtility.curr_Timestamp();
			String tmp_now	= ( ( now == null || "".equals( now ) ) ? "null" :  now );
			if ( "1".equals(cntl_kbn) ) {
				// 排他制御のリセット
	    		resetWorkingSW(con, gamen_kbn, user_id, data_kbn);
			}
			String sql1	= "UPDATE KF_IMK2060"
						+ " SET DTA_KBN = ?,"
						+ " CTL_KBN = ?,"
						+ " PRC_RAT = ?,"
						+ " UPD_DTM = ?,"
						+ " UPD_ID = ?'"
						+ " WHERE NAM_COD = ?"
						+ " AND SCN_ID = ?";
			int	st	= 0;
			pstmt1 = con.prepareStatement( sql1 );
			int itm_num = 1;
			pstmt1.setString( itm_num++, data_kbn );
			pstmt1.setString( itm_num++, cntl_kbn );
			pstmt1.setInt( itm_num++, Integer.parseInt( prc_rat ) );
			pstmt1.setTimestamp( itm_num++, Timestamp.valueOf( tmp_now ) );
			pstmt1.setString( itm_num++, user_id );
			pstmt1.setString( itm_num++, user_id );
			pstmt1.setString( itm_num++, gamen_kbn );
			st = pstmt1.executeUpdate();

			// INSERT切替
			if ( st == 0 ) {
				String sql2	= "INSERT INTO KF_IMK2060"
					+ " ( NAM_COD, SCN_ID, CTL_KBN, DTA_KBN, PRC_RAT, UPD_DTM, UPD_ID"
					+ "	) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
				pstmt2 = con.prepareStatement( sql2 );
				itm_num = 1;
				pstmt2.setString   ( itm_num++, user_id );
				pstmt2.setString   ( itm_num++, gamen_kbn );
				pstmt2.setString   ( itm_num++, cntl_kbn );
				pstmt2.setString   ( itm_num++, data_kbn );
				pstmt2.setInt      ( itm_num++, Integer.parseInt( prc_rat ) );
				pstmt2.setTimestamp( itm_num++, Timestamp.valueOf( tmp_now ) );
				pstmt2.setString   ( itm_num++, user_id );
				pstmt2.executeUpdate();
			}
			Dbcom.dbCommit(con);
			return;
		} finally {
			if ( pstmt1 != null ) { pstmt1.close(); }
			if ( pstmt2 != null ) { pstmt2.close(); }
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 現在のデータ進捗率を排他制御エンティティに報告
	* @param	con																		(Connection)
	* @param	gamen_kbn	画面ＩＤ														(String)
	* @param	user_id		ユーザーＩＤ													(String)
	* @param	data_kbn	データ区分（1:ダウンロード 2:アップロード）					(String)
	* @param	cntl_kbn	制御区分  （ 0:初期 1:開始 2:データ取得中 3:処理中 9:終了 ）	(String)
	* @param	curr_cnt	現在の件数													(int)
	* @param	all_cnt		全体の件数													(int)
	* @return	なし
	********************************************************************************
	*/
	public static void setWorkingCount(	Connection		con,
											String			gamen_kbn,
											String			user_id,
											String			data_kbn,
											String			cntl_kbn,
											int				curr_cnt,
											int				all_cnt) throws Exception
	{
		// 50件毎に書き込む
		String s = "";
    	if ( curr_cnt%50 == 0 ) {
    		s = Integer.toString( ( curr_cnt * 100 ) / all_cnt );
    		//System.out.println("データ進捗率:"+s);
    		setWorkingSW( con, gamen_kbn, user_id, data_kbn,cntl_kbn, s );
    	}
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
