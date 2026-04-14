/**
*-------------------------------------------------------------------------------
* クラス名			DateTimeUtility.class
* システム名称		共通
* 名称				ユーティリティ・日時クラス
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtility {
	/**
	********************************************************************************
	* 現在のシステム日時を取得 ("yyyy/MM/dd HH:mm"）
	* @return	現在のシステム日時	(String)
	********************************************************************************
	*/
	public static String getPrsntDtTm() throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd HH:mm" );
		return sdf.format( new Date() );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* カレント時刻ＧＥＴ
	* @return	HH:MM:SS	(String)                 
	********************************************************************************
	*/
	public static String curr_Time() {
		int gmt = 9;
		int hh, ms, ss;
		String	s, st_hh, st_ms, st_ss;
		String	tz[] = TimeZone.getAvailableIDs( gmt * 60 * 60 * 1000 );
		Calendar d = Calendar.getInstance( TimeZone.getTimeZone( tz[ 0 ] ) );
		hh	= d.get( Calendar.HOUR_OF_DAY );
		st_hh = "";
		st_hh += hh;
		ms	= d.get( Calendar.MINUTE );
		st_ms = "";
		st_ms += ms;
		ss	= d.get( Calendar.SECOND );
		st_ss = "";
		st_ss += ss;
		if ( hh < 10 ) { st_hh = "0" + hh; }
		if ( ms < 10 ) { st_ms = "0" + ms; }
		if ( ss < 10 ) { st_ss = "0" + ss; }
		s = st_hh + ":" + st_ms + ":" + st_ss;
		return s;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* カレント時刻ＧＥＴ
	* @return	HHMMSS99	(String)                 
	********************************************************************************
	*/
	public static String curr_Time1() {
		int gmt = 9;
		int hh, ms, ss, css;
		String	s, st_hh, st_ms, st_ss, st_css;
		String	tz[] = TimeZone.getAvailableIDs( gmt * 60 * 60 * 1000 );
		Calendar d = Calendar.getInstance( TimeZone.getTimeZone( tz[ 0 ] ) );
		hh	= d.get( Calendar.HOUR_OF_DAY );
		st_hh = "";
		st_hh += hh;
		ms	= d.get( Calendar.MINUTE );
		st_ms = "";
		st_ms += ms;
		ss	= d.get( Calendar.SECOND );
		st_ss = "";
		st_ss += ss;
		css	= d.get( Calendar.MILLISECOND ) / 10;
		st_css = "";
		st_css += css;
		if ( hh < 10 ) { st_hh = "0" + hh; }
		if ( ms < 10 ) { st_ms = "0" + ms; }
		if ( ss < 10 ) { st_ss = "0" + ss; }
		if ( css < 10 ) { st_css = "0" + css; }
		s = st_hh + st_ms + st_ss + st_css;
		return s;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* カレント日付ＧＥＴ
	* @return	YYYYMMDD	(String)
	********************************************************************************
	*/
	public static String curr_Date() {
		int gmt = 9;
		int yyyy, mm, dd;
		String	s, st_yyyy, st_mm, st_dd;
		String	tz[] = TimeZone.getAvailableIDs( gmt * 60 * 60 * 1000 );
		Calendar d = Calendar.getInstance( TimeZone.getTimeZone( tz[ 0 ] ) );
		yyyy = d.get( Calendar.YEAR );
		st_yyyy = "";
		st_yyyy += yyyy;
		mm	= d.get( Calendar.MONTH ) + 1;
		st_mm = "";
		st_mm += mm;
		dd	= d.get( Calendar.DATE );
		st_dd = "";
		st_dd += dd;
		if ( yyyy < 1000 ) { st_yyyy = "0" + yyyy; }
		if ( yyyy < 100	) { st_yyyy = "00" + yyyy; }
		if ( yyyy < 10	) { st_yyyy = "000" + yyyy; }
		if ( mm < 10 ) { st_mm = "0" + mm; }
		if ( dd < 10 ) { st_dd = "0" + dd; }
		s = st_yyyy + st_mm + st_dd;
		return s;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* カレント日付ＧＥＴ
	* @return	YYYY/MM/DD	(String)
	********************************************************************************
	*/
	public static String curr_Date1() {
		int gmt = 9;
		int yyyy, mm, dd;
		String	s, st_yyyy, st_mm, st_dd;
		String	tz[] = TimeZone.getAvailableIDs( gmt * 60 * 60 * 1000 );
		Calendar d = Calendar.getInstance( TimeZone.getTimeZone( tz[ 0 ] ) );
		yyyy = d.get( Calendar.YEAR );
		st_yyyy = "";
		st_yyyy += yyyy;
		mm	= d.get( Calendar.MONTH ) + 1;
		st_mm = "";
		st_mm += mm;
		dd	= d.get( Calendar.DATE );
		st_dd = "";
		st_dd += dd;
		if ( yyyy < 1000 ) { st_yyyy = "0" + yyyy; }
		if ( yyyy < 100	) { st_yyyy = "00" + yyyy; }
		if ( yyyy < 10	) { st_yyyy = "000" + yyyy; }
		if ( mm < 10 ) { st_mm = "0" + mm; }
		if ( dd < 10 ) { st_dd = "0" + dd; }
		s = st_yyyy + "/" + st_mm + "/" + st_dd;
		return s;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* カレントタイムスタンプＧＥＴ
	* @return	YYYY/MM/DD HH:MM:SS	(String)
	********************************************************************************
	*/
	public static String curr_Timestamp() throws Exception {
		return cvtDate1( curr_Date() ) + " " + curr_Time();
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付書式変換
	* @param	format_sw				(int)
	* @param	in_data					(String)
	* @return	Not null:OK null:ERR	(Calendar)
	********************************************************************************
	*/
	public static Calendar ymd_Format( int format_sw, String in_data ) {
		try {
			Calendar out_data = Calendar.getInstance();
			switch ( format_sw ) {
				case 1:
				case 3:
					out_data.set( ( new Integer( in_data.substring( 0, 4 ) ) ).intValue(),
								( ( new Integer( in_data.substring( 5, 7 ) ) ).intValue() - 1 ),
								( new Integer( in_data.substring( 8, 10 ) ) ).intValue() );
					break;
				case 2:
					out_data.set( ( new Integer( in_data.substring( 0, 4 ) ) ).intValue(),
								( ( new Integer( in_data.substring( 4, 6 ) ) ).intValue() - 1 ),
								( new Integer( in_data.substring( 6, 8 ) ) ).intValue() );
					break;
				default:
					throw new Exception();
			}
			return out_data;
		} catch ( Exception e ) {
			return null;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 期間の日数取得
	* @param	frmD		(String)
	* @param	toDt		(String)
	* @return	期間の日数	(long)
	********************************************************************************
	*/
	public static long getTrmDys(	String frmDt, String toDt	) {
		Calendar frmDtCllndr = ymd_Format( ( frmDt.length() == 10 ) ? 1 : 2, frmDt );
		Calendar toDtCllndr = ymd_Format( ( toDt.length() == 10 ) ? 1 : 2, toDt );
		long rslt = ( toDtCllndr.getTimeInMillis() - frmDtCllndr.getTimeInMillis() ) / ( 24 * 60 * 60 * 1000 );
		return ( rslt < 0 ) ? --rslt : ++rslt;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 曜日コードの全角変換
	* @param	youbi_cd			(String)                 
	* @return  Not "":OK "":ERR	(String)
	********************************************************************************
	*/
	public static String getYoubiMoji( String youbi_cd ) {
		try {
			if ( youbi_cd == null || "".equals(youbi_cd) ) {
				return "";
			}
			return getYoubiMoji( ( new Integer( youbi_cd ) ).intValue() );
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 曜日コードの全角変換
	* @param	youbi_cd			(int)                 
	* @return  Not "":OK "":ERR	(String)
	********************************************************************************
	*/
	public static String getYoubiMoji( int youbi_cd ) {
		try {
			switch ( youbi_cd ) {
				case 1:
					return "月";
				case 2:
					return "火";
				case 3:
					return "水";
				case 4:
					return "木";
				case 5:
					return "金";
				case 6:
					return "土";
				case 7:
					return "日";
			}
			return "";
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 曜日コード取得(Java標準)
	* @param	ymd_data	(String)
	* @return  曜日コード	(int)
	********************************************************************************
	*/
	public static int getWkDyCd( String ymd_data ) {
		Calendar cal;
		if ( ymd_data.length() == 8 ) {
			cal = ymd_Format( 2, ymd_data );
		} else {
			cal = ymd_Format( 1, ymd_data );
		}
		return cal.get( Calendar.DAY_OF_WEEK );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 曜日コードの全角変換(Java標準)
	* @param	wkDyCd				(int)                 
	* @return  Not "":OK "":ERR	(String)
	********************************************************************************
	*/
	public static String getWkDy( int wkDyCd ) {
		String wkDy = "";
		switch ( wkDyCd ) {
			case Calendar.SUNDAY:		wkDy = "日"; break;
			case Calendar.MONDAY:		wkDy = "月"; break;
			case Calendar.TUESDAY:		wkDy = "火"; break;
			case Calendar.WEDNESDAY:	wkDy = "水"; break;
			case Calendar.THURSDAY:		wkDy = "木"; break;
			case Calendar.FRIDAY:		wkDy = "金"; break;
			case Calendar.SATURDAY: 	wkDy = "土"; break;
	    }
		return wkDy;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 曜日の取得(Java標準)
	* @param	ymd_data			(String)
	* @return  Not "":OK "":ERR	(String)
	********************************************************************************
	*/
	public static String getWkDy( String ymd_data ) {
		try {
			if ( ymd_data == null || "".equals( ymd_data ) ) {
				return "";
			}
			return getWkDy( getWkDyCd( ymd_data ) ) ;
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 経過日数の日付取得
	* @param	ymd_data(YYYYMMDD)	(String)
	* @param	keika_nissu			(int)
	* @return	Not "":OK "":ERR	(String)
	********************************************************************************
	*/
	public static String getTodate( String ymd_data,int keika_nissu )	 {
		try {
			String ret_str = "";
			if ( ymd_data == null || "".equals( ymd_data ) ) {
				return "";
			}
			Calendar cal = ymd_Format( 2, ymd_data );
			cal.add( Calendar.DATE, keika_nissu );
			Integer wk_year = new Integer( cal.get( Calendar.YEAR ) );	
			Integer wk_month = new Integer( cal.get( Calendar.MONTH ) + 1 );	
			Integer wk_day = new Integer( cal.get( Calendar.DAY_OF_MONTH ) );
			ret_str = wk_year.toString();
			if ( wk_month.intValue() < 10 ) {
				ret_str += "0";
			}
			ret_str += wk_month.toString();
			if ( wk_day.intValue() < 10 ) {
				ret_str += "0";
			}
			ret_str += wk_day.toString();
			return ret_str;
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 経過日数の日付取得
	* @param	ymd_data(YYYYMMDD)	(String)
	* @param	field				(int)
	* @param	keika_su			(int)
	* @return	Not "":OK "":ERR	(String)
	********************************************************************************
	*/
	public static String getTodate( String ymd_data,int field,int keika_su )	 {
		try {
			String ret_str = "";
			if ( ymd_data == null || "".equals( ymd_data ) ) {
				return "";
			}
			Calendar cal = ymd_Format( 2, ymd_data );
			cal.add( field, keika_su );
			Integer wk_year = new Integer( cal.get( Calendar.YEAR ) );	
			Integer wk_month = new Integer( cal.get( Calendar.MONTH ) + 1 );	
			Integer wk_day = new Integer( cal.get( Calendar.DAY_OF_MONTH ) );
			ret_str = wk_year.toString();
			if ( wk_month.intValue() < 10 ) {
				ret_str += "0";
			}
			ret_str += wk_month.toString();
			if ( wk_day.intValue() < 10 ) {
				ret_str += "0";
			}
			ret_str += wk_day.toString();
			return ret_str;
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付の画面表示用形式へ変換
	* @param	ymd_data(yyyymmdd or yyyymm)	(String)
	* @return	Not "":OK "":ERR				(String)
	********************************************************************************
	*/
	public static String ymd_Print( int format_sw,	String ymd_data )	 {
		try {
			if ( ymd_data == null || "".equals( ymd_data ) ) {
				return "";
			}
			if ( ymd_data.length() == 6 ) {
				//YYYYMM
				switch ( format_sw ) {
						case 1:
						return ymd_data.substring( 0, 4 ) + "/"
							 + ymd_data.substring( 4, 6 );
					case 2:
						return ymd_data.substring( 0, 4 ) + "年"
							 + ymd_data.substring( 4, 6 ) + "月";
				}
			} else {
				//YYYYMMDD
				switch ( format_sw ) {
					case 1:
						return ymd_data.substring( 0, 4 ) + "/"
							 + ymd_data.substring( 4, 6 ) + "/"
							 + ymd_data.substring( 6, 8 );
					case 2:
						return ymd_data.substring( 0, 4 ) + "年"
							 + ymd_data.substring( 4, 6 ) + "月"
							 + ymd_data.substring( 6, 8 ) + "日";
					case 3:
						String wk_str = ymd_data.substring( 4, 6 ) + "月"
							 + ymd_data.substring( 6, 8 ) + "日";
						if ( "0".equals( ( wk_str.substring( 0, 1 ) ) ) ) {
							wk_str = wk_str.substring( 1 );
						}
						return wk_str;
					case 4:
						wk_str = ymd_data.substring( 4, 6 ) + "/"
							 + ymd_data.substring( 6, 8 );
						if ( "0".equals( ( wk_str.substring( 0, 1 ) ) ) ) {
							wk_str = wk_str.substring( 1 );
						}
						return wk_str;
				}
			}
			return "";
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付の曜日を返却する
	* @param	ymd_data		(String)
	* @return	Not 0:OK 0:ERR	(int)
	********************************************************************************
	*/
	public static int ymd_GetYoubi( String ymd_data )	 {
		try {
			if ( ymd_data == null || "".equals( ymd_data ) ) {
				return 0;
			}
			Calendar cal;
			if ( ymd_data.length() == 8 ) {
				cal = ymd_Format( 2, ymd_data );
			} else {
				cal = ymd_Format( 1, ymd_data );
			}
			int youbi = cal.get( Calendar.DAY_OF_WEEK );
			if ( youbi == Calendar.MONDAY )		return 1;
			if ( youbi == Calendar.TUESDAY )	return 2;
			if ( youbi == Calendar.WEDNESDAY )	return 3;
			if ( youbi == Calendar.THURSDAY ) 	return 4;
			if ( youbi == Calendar.FRIDAY )		return 5;
			if ( youbi == Calendar.SATURDAY ) 	return 6;
			if ( youbi == Calendar.SUNDAY )		return 7;
			return 0;
		} catch ( Exception e ) {
			return 0;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 直近指定曜日＋日数の日付取得
	* @param	youbi	指定曜日	（1:月 2:火 3:水 4:木 5:金 6:土 7:日)	(int)
	* @param	today	基準日	（YYYYMMDDの形式)							(String)
	* @param	days	加算日数											(int)
	* @return	日付	（YYYY/MM/DDの形式）( ERRの時は、"" )				(String)
	********************************************************************************
	*/
	public static String getNextYoubi( int youbi, String today, int days ) throws Exception
	{
		String	w	=	"";
		String	s	=	"";
		String s0 = today;
		String s1 = getTodate( today, 1 );
		String s2 = getTodate( today, 2 );
		String s3 = getTodate( today, 3 );
		String s4 = getTodate( today, 4 );
		String s5 = getTodate( today, 5 );
		String s6 = getTodate( today, 6 );
		int i0 = ymd_GetYoubi( s0 );
		int i1 = ymd_GetYoubi( s1 );
		int i2 = ymd_GetYoubi( s2 );
		int i3 = ymd_GetYoubi( s3 );
		int i4 = ymd_GetYoubi( s4 );
		int i5 = ymd_GetYoubi( s5 );
		int i6 = ymd_GetYoubi( s6 );
		if ( i0 == youbi ) {
			w = getTodate( s0, days );
			s = w.substring( 0, 4 ) + "/" + w.substring( 4, 6 ) + "/" + w.substring( 6, 8 );
		}
		if ( i1 == youbi ) {
			w = getTodate( s1, days );
			s = w.substring( 0, 4 ) + "/" + w.substring( 4, 6 ) + "/" + w.substring( 6, 8 );
		}
		if ( i2 == youbi ) {
			w = getTodate( s2, days );
			s = w.substring( 0, 4 ) + "/" + w.substring( 4, 6 ) + "/" + w.substring( 6, 8 );
		}
		if ( i3 == youbi ) {
			w = getTodate( s3, days );
			s = w.substring( 0, 4 ) + "/" + w.substring( 4, 6 ) + "/" + w.substring( 6, 8 );
		}
		if ( i4 == youbi ) {
			w = getTodate( s4, days );
			s = w.substring( 0, 4 ) + "/" + w.substring( 4, 6 ) + "/" + w.substring( 6, 8 );
		}
		if ( i5 == youbi ) {
			w = getTodate( s5, days );
			s = w.substring( 0, 4 ) + "/" + w.substring( 4, 6 ) + "/" + w.substring( 6, 8 );
		}
		if ( i6 == youbi ) {
			w = getTodate( s6, days );
			s = w.substring( 0, 4 ) + "/" + w.substring( 4, 6 ) + "/" + w.substring( 6, 8 );
		}
		return s;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* カレント日付＆時刻ＧＥＴ
	* @param	sw	 (0:YYYYMMDDHHMM形式 0以外:YYYY/MM/DD HH:MM形式)	(int)
	* @return	HH:MM:SS												(String)
	********************************************************************************
	*/
	public static String curr_DateTime( int sw ) {

		int gmt = 9;
		int yyyy, mm, dd, hh, ms, ss;
		String	s, s1, s2, st_yyyy, st_mm, st_dd, st_hh, st_ms, st_ss;
		String	tz[] = TimeZone.getAvailableIDs( gmt * 60 * 60 * 1000 );
		Calendar d = Calendar.getInstance( TimeZone.getTimeZone( tz[ 0 ] ) );
		yyyy = d.get( Calendar.YEAR );
		st_yyyy = "";
		st_yyyy += yyyy;
		mm	= d.get( Calendar.MONTH ) + 1;
		st_mm = "";
		st_mm += mm;
		dd	= d.get( Calendar.DATE );
		st_dd = "";
		st_dd += dd;
		if ( yyyy < 1000 ) { st_yyyy = "0" + yyyy; }
		if ( yyyy < 100	) { st_yyyy = "00" + yyyy; }
		if ( yyyy < 10	) { st_yyyy = "000" + yyyy; }
		if ( mm < 10 ) { st_mm = "0" + mm; }
		if ( dd < 10 ) { st_dd = "0" + dd; }
		if ( sw == 0 ) {
			s1 = st_yyyy + st_mm + st_dd;
		} else {
			s1 = st_yyyy + "/" + st_mm + "/" + st_dd;
		}

		hh	= d.get( Calendar.HOUR_OF_DAY );
		st_hh = "";
		st_hh += hh;
		ms	= d.get( Calendar.MINUTE );
		st_ms = "";
		st_ms += ms;
		ss	= d.get( Calendar.SECOND );
		st_ss = "";
		st_ss += ss;
		if ( hh < 10 ) { st_hh = "0" + hh; }
		if ( ms < 10 ) { st_ms = "0" + ms; }
		if ( ss < 10 ) { st_ss = "0" + ss; }
		if ( sw == 0 ) {
			s2 = st_hh + st_ms;
			s = s1 + s2;
		} else {
			s2 = st_hh + ":" + st_ms;
			s = s1 + " " + s2;
		}
		return s;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付文字列変換 --> YYYYMMDD
	* @param	strVal	( YYYY-MM-DD HH.MI.SS.X ）	(String)
	* @return	エンコード結果:OK null:ERR			(String)
	********************************************************************************
	*/
	static public String cvtDate0(	String	strVal	) throws Exception
	{
		if ( strVal == null ) {
			return null;
		} else {
			if ( strVal.length() > 9 ) {
				String s = strVal.substring( 0, 4 ) + strVal.substring( 5, 7 ) + strVal.substring( 8, 10 );
				return s;
			} else {
				throw new Exception( CommonUtility.getExcptnMssg( "cvtDate0", "日付サイズエラー" ) );
			}
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付文字列変換 --> YYYYMMDD（ブランクは変換しない）
	* @param	strVal	( YYYY-MM-DD HH.MI.SS.X ）	(String)
	* @return	エンコード結果:OK null:ERR			(String)
	********************************************************************************
	*/
	static public String cvtNtBlnkDate0(	String	strVal	) throws Exception
	{
		if ( "".equals( strVal ) ) {
			return "";
		} else {
			return cvtDate0( strVal );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付文字列変換 --> YYYY-MM-DD
	* @param	strVal	( YYYYMMDD ）		(String)
	* @return	エンコード結果:OK null:ERR	(String)
	********************************************************************************
	*/
	static public String cvtDate1(String	strVal	) throws Exception
	{
		if( strVal == null ) {
			return null;
		} else {
			if ( strVal.length() == 8 ) {
				String s = strVal.substring( 0, 4 ) + "-" + strVal.substring( 4, 6 ) + "-" + strVal.substring( 6, 8 );
				return s;
			} else {
				throw new Exception( CommonUtility.getExcptnMssg( "cvtDate1", "日付サイズエラー（8桁）" ) );
			}
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付文字列変換 --> YYYY/MM/DD
	* @param	strVal	( YYYYMMDD ）		(String)
	* @return	エンコード結果:OK null:ERR	(String)
	********************************************************************************
	*/
	static public String cvtDate2(	String	strVal	) throws Exception
	{
		if( strVal == null ) {
			return null;
		} else {
			if ( strVal.length() == 8 ) {
				String s = strVal.substring( 0, 4 ) + "/" + strVal.substring( 4, 6 ) + "/" + strVal.substring( 6, 8 );
				return s;
			} else {
				throw new Exception( CommonUtility.getExcptnMssg( "cvtDate2", "日付サイズエラー（8桁）" ) );
			}
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付文字列変換 --> YY/MM/DD
	* @param	strVal	( YYYY-MM-DD HH.MI.SS.X ）	(String)
	* @return	エンコード結果:OK null:ERR			(String)
	********************************************************************************
	*/
	static public String cvtDate3(	String	strVal	) throws Exception
	{
		if ( strVal == null ) {
			return null;
		} else {
			if ( strVal.length() > 9 ) {
				String s = strVal.substring( 2, 10 ).replace( '-', '/' );
				return s;
			} else {
				throw new Exception( CommonUtility.getExcptnMssg( "cvtDate3", "日付サイズエラー" ) );
			}
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付文字列変換 --> YYYY/MM
	* @param	strVal	( YYYYMM ）			(String)
	* @return	エンコード結果:OK null:ERR	(String)
	********************************************************************************
	*/
	static public String cvtDate4(	String	strVal	) throws Exception
	{
		if ( strVal == null ) {
			return null;
		} else {
			if ( strVal.length() == 6 || strVal.length() == 8 ) {
				String s = strVal.substring( 0, 4 ) + "/" + strVal.substring( 4, 6 );
				return s;
			} else {
				throw new Exception( CommonUtility.getExcptnMssg( "cvtDate4", "日付サイズエラー" ) );
			}
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付文字列変換 --> YYYY/MM/DD(DAY)
	* @param	strVal	( YYYY-MM-DD HH.MI.SS.X ）	(String)
	* @return	エンコード結果:OK null:ERR			(String)
	********************************************************************************
	*/
	static public String cvtDate5(	String	strVal	) throws Exception
	{
		return cvtDate7( strVal ) + "(" + getYoubiMoji( ymd_GetYoubi( cvtDate8( strVal ) ) ) + ")";
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付文字列変換 --> YYYY-MM-DD
	* @param	strVal	( YYYYMMDD, YYYY/MM/DD ）	(String)
	* @return	エンコード結果:OK null:ERR			(String)
	********************************************************************************
	*/
	static public String cvtDate6(	String	strVal	) throws Exception
	{
		if ( strVal == null ) {
			return null;
		} else {
			if ( strVal.length() == 8 ) {
				String s = strVal.substring( 0, 4 ) + "-" + strVal.substring( 4, 6 ) + "-" + strVal.substring( 6, 8 );
				return s;
			}else if ( strVal.length() > 9 ) {
				String s = strVal.substring( 0, 10 ).replace( '/', '-' );
				return s;
			} else {
				throw new Exception( CommonUtility.getExcptnMssg( "cvtDate6", "日付サイズエラー" ) );
			}
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付文字列変換 --> YYYY/MM/DD
	* @param	strVal	( YYYYMMDD, YYYY-MM-DD ）	(String)
	* @return	エンコード結果:OK null:ERR			(String)
	********************************************************************************
	*/
	static public String cvtDate7(	String	strVal	) throws Exception
	{
		if ( strVal == null ) {
			return null;
		} else {
			if ( strVal.length() == 8 ) {
				String s = strVal.substring( 0, 4 ) + "/" + strVal.substring( 4, 6 ) + "/" + strVal.substring( 6, 8 );
				return s;
			}else if ( strVal.length() > 9 ) {
				String s = strVal.substring( 0, 10 ).replace( '-', '/' );
				return s;
			} else {
				throw new Exception( CommonUtility.getExcptnMssg( "cvtDate7", "日付サイズエラー" ) );
			}
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付文字列変換 --> YYYYMMDD
	* @param	strVal	( YYYYMMDD, YYYY-MM-DD ）	(String)
	* @return	エンコード結果:OK null:ERR			(String)
	********************************************************************************
	*/
	static public String cvtDate8(	String	strVal	) throws Exception
	{
		if ( strVal == null ) {
			return null;
		} else {
			if ( strVal.length() == 8 ) {
				return strVal;
			} else {
				return cvtDate0( strVal );
			}
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付文字列変換 --> YYYY/MM/DD
	* @param	strVal	( YYYY-MM-DD HH.MI.SS.X ）	(String)
	* @return	エンコード結果:OK null:ERR			(String)
	********************************************************************************
	*/
	static public String cvtDate(	String	strVal	) throws Exception
	{
		if ( strVal == null ) {
			return null;
		} else {
			if ( strVal.length() > 9 ) {
				String s = strVal.substring( 0, 10 ).replace( '-', '/' );
				return s;
			} else {
				throw new Exception( CommonUtility.getExcptnMssg( "cvtDate", "日付サイズエラー" ) );
			}
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付時刻文字列変換 --> YYYY/MM/DD HH:MI:SS
	* @param	strVal	( YYYY-MM-DD HH.MI.SS.X ）	(String)
	* @return	エンコード結果:OK null:ERR			(String)
	********************************************************************************
	*/
	static public String cvtDateTime(	String	strVal	) throws Exception
	{
		if ( strVal == null ) {
			return null;
		} else {
			if ( strVal.length() > 9 ) {
				String wk = strVal.substring( 0, 19 ).replace( '-', '/' );
				String s  = wk.substring( 0, 19 ).replace( '.', ':' );
				return s;
			} else {
				throw new Exception( CommonUtility.getExcptnMssg( "cvtDateTime", "日付サイズエラー" ) );
			}
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付時刻文字列変換 --> YYYY-MM-DD HH.MI.SS
	* @param	strVal	( YYYY/MM/DD HH:MI:SS ）(String)
	* @return	エンコード結果:OK null:ERR		(String)
	********************************************************************************
	*/
	static public String cvtDateTime1(	String	strVal	) throws Exception
	{
		if ( strVal == null ) {
			return null;
		} else {
			if ( strVal.length() > 9 ) {
				String wk = strVal.substring( 0, 19 ).replace( '/', '-' );
				String s  = wk.substring( 0, 19 ).replace( ':', '.' );
				return s;
			} else {
				throw new Exception( CommonUtility.getExcptnMssg( "cvtDateTime1", "日付サイズエラー" ) );
			}
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付時刻文字列変換 --> YYYY-MM-DD 00:00:00
	* @param	strVal	( YYYYMMDD, YYYY/MM/DD ）	(String)
	* @return	エンコード結果:OK null:ERR			(String)
	********************************************************************************
	*/
	static public String cvtDateTime2(	String	strVal	) throws Exception
	{
		return cvtDate6( strVal ) + " 00:00:00";
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付時刻文字列変換 --> YYYY/MM/DD HH:MI
	* @param	strVal	( YYYY-MM-DD HH.MI.SS.X ）	(String)
	* @return	エンコード結果:OK null:ERR			(String)
	********************************************************************************
	*/
	static public String cvtDateTime3(	String	strVal	) throws Exception
	{
		String dtm = cvtDateTime( strVal );
		if ( strVal.length() > 15 ) {
			dtm = dtm.substring( 0, 16 );
		}
		return dtm;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 指定カレンダーから日付を文字列で取得する。
	* @param	d									(Calendar)
	* @return	文字列日付（"YYYY-MM-DD HH:MM:SS"）	(String)
	********************************************************************************
	*/
	public static String getYMDString(	Calendar	d	) throws Exception
	{
		int yyyy, mm, dd, hh, ms, ss;
		String	yyyymmdd, st_yyyy, st_mm, st_dd, hhmmss, st_hh, st_ms, st_ss, now;

		yyyy = d.get( Calendar.YEAR );
		st_yyyy = "";
		st_yyyy += yyyy;
		mm	= d.get( Calendar.MONTH ) + 1;
		st_mm = "";
		st_mm += mm;
		dd	= d.get( Calendar.DATE );
		st_dd = "";
		st_dd += dd;
		if ( yyyy < 1000 ) { st_yyyy = "0" + yyyy; }
		if ( yyyy < 100	) { st_yyyy = "00" + yyyy; }
		if ( yyyy < 10	) { st_yyyy = "000" + yyyy; }
		if ( mm < 10 ) { st_mm = "0" + mm; }
		if ( dd < 10 ) { st_dd = "0" + dd; }
		yyyymmdd = st_yyyy + "-" + st_mm + "-" + st_dd;

		hh	= d.get( Calendar.HOUR_OF_DAY );
		st_hh = "";
		st_hh += hh;
		ms	= d.get( Calendar.MINUTE );
		st_ms = "";
		st_ms += ms;
		ss	= d.get( Calendar.SECOND );
		st_ss = "";
		st_ss += ss;
		if ( hh < 10 ) { st_hh = "0" + hh; }
		if ( ms < 10 ) { st_ms = "0" + ms; }
		if ( ss < 10 ) { st_ss = "0" + ss; }
		hhmmss = st_hh + ":" + st_ms + ":" + st_ss;
		now = yyyymmdd + " " + hhmmss;
		return now;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 指定Dateから日付を文字列で取得する。
	* @param	dt									(Date)
	* @return	文字列日付（"YYYY-MM-DD HH:MM:SS"）	(String)
	********************************************************************************
	*/
	public static String getYMDString(	Date	dt	) throws Exception
	{
		DateFormat dtFrmt = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		return dtFrmt.format( dt );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 日付文字整形(YYYY/M/D--->YYYY/MM/DD)<br>
	* 数値部の変換エラーの場合は、入力値をそのまま返す。
	* @param	in_data	(YYYY/M/D)	(String)
	* @return	YYYY/MM/DD			(String)
	********************************************************************************
	*/
	public static String cvtYMD(String	in_data) {
		try {
			String	result		= "";
			int		sw			= 0;								// 0:YYYY 1:M 2:D
			int		int_wk		= 0;
			int		int_dec		= 0;
			char	ch_sp		= '/';
			String	str_yyyy	= "";
			String	str_mm		= "";
			String	str_dd		= "";
			String	out_yyyy	= "";
			String	out_mm		= "";
			String	out_dd		= "";
			if (in_data != "") {
				//1文字ずつ取り出して / か判断する。
				for ( int i = 0; i<in_data.length(); i++ ) {

					if ( ch_sp != in_data.charAt( i ) ) {
						int_dec = (int)in_data.charAt( i );
						if ( int_dec < 48 || 57 < int_dec ) {		// 半角数字以外か？(0030～0039:48～57）
							return in_data;
						}
						if ( sw == 0 ) {
							str_yyyy += in_data.charAt( i );
						}
						if ( sw == 1 ) {
							str_mm += in_data.charAt( i );
						}
						if ( sw == 2 ) {
							str_dd += in_data.charAt( i );
						}
					} else {
						sw++;
					}
				}
				// **** YYYY 整形 ****
				int_wk	= Integer.parseInt( str_yyyy );
				if ( int_wk < 10 ) {
					out_yyyy = "000" + Integer.toString( int_wk );
				} else {
					if ( int_wk < 100 ) {
						out_yyyy = "00" + Integer.toString( int_wk );
					} else {
						if ( int_wk < 1000 ) {
							out_yyyy = "0" + Integer.toString( int_wk );
						} else {
							out_yyyy = str_yyyy;
						}
					}
				}
				// **** MM 整形 ****
				int_wk	= Integer.parseInt( str_mm );
				if ( int_wk < 10 ) {
					out_mm = "0" + Integer.toString( int_wk );
				} else {
					out_mm = str_mm;
				}
				// **** DD 整形 ****
				int_wk	= Integer.parseInt( str_dd );
				if ( int_wk < 10 ) {
					out_dd = "0" + Integer.toString( int_wk );
				} else {
					out_dd = str_dd;
				}
				result = out_yyyy + "/" + out_mm + "/" + out_dd;
			} else {
				result = in_data;
			}
			return result;
		} catch ( Exception e ) {
			return in_data;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 年月から年を取得
	* @param	yrMnth	YYYYMM	(String)
	* @return	年				(String)
	********************************************************************************
	*/
	public static String getYrFrmYrMnth(	String		yrMnth	)
	{
		return yrMnth.substring( 0, 4 );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 年月から月を取得
	* @param	yrMnth	YYYYMM	(String)
	* @return	月				(String)
	********************************************************************************
	*/
	public static String getMnthFrmYrMnth(	String		yrMnth	)
	{
		return yrMnth.substring( 4, 6 );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 年月からその月の始まりの年月日を取得
	* @param	yrMnth	YYYYMM	(String)
	* @return	YYYYMM01		(String)
	********************************************************************************
	*/
	public static String getDtFrmYrMnth(	String		yrMnth	)
	{
		String dt = "";
		if ( yrMnth != null && yrMnth.length() >= 6 ) {
			dt = yrMnth.substring( 0, 6 ) + "01";
		}
		return dt;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 会計年度＋期首月から年月を算出
	* @param	nendo	会計年度( yyyy )					(String)
	* @param	kishu	期首月(MM)						(String)
	* @param	i		index(0～11)：(表の年月列Index)	(int)
	* @return	年月									(String)
	********************************************************************************
	*/
	public static String callNendo(	String	nendo,
										String	kishu,
										int		i	) throws Exception
	{
		String result = "";
		// ***** 年月算出用Array *****
		String[] MON_TBL1 = {
			"01","02","03","04","05","06","07","08","09","10","11","12"
		};	
		String[] MON_TBL2 = {
			"02","03","04","05","06","07","08","09","10","11","12","01"
		};	
		String[] MON_TBL3 = {
			"03","04","05","06","07","08","09","10","11","12","01","02"
		};	
		String[] MON_TBL4 = {
			"04","05","06","07","08","09","10","11","12","01","02","03"
		};	
		String[] MON_TBL5 = {
			"05","06","07","08","09","10","11","12","01","02","03","04"
		};	
		String[] MON_TBL6 = {
			"06","07","08","09","10","11","12","01","02","03","04","05"
		};	
		String[] MON_TBL7 = {
			"07","08","09","10","11","12","01","02","03","04","05","06"
		};	
		String[] MON_TBL8 = {
			"08","09","10","11","12","01","02","03","04","05","06","07"
		};	
		String[] MON_TBL9 = {
			"09","10","11","12","01","02","03","04","05","06","07","08"
		};	
		String[] MON_TBL10 = {
			"10","11","12","01","02","03","04","05","06","07","08","09"
		};	
		String[] MON_TBL11 = {
			"11","12","01","02","03","04","05","06","07","08","09","10"
		};	
		String[] MON_TBL12 = {
			"12","01","02","03","04","05","06","07","08","09","10","11"
		};	
		int m = Integer.parseInt( kishu );
		int yyyy = Integer.parseInt( nendo );
		String s = "";
		switch ( m ) {
			case 1: // 期首が1月のときは、年は、変化しない
			s = MON_TBL1[ i ];
				result = Integer.toString( yyyy ) + s;
				break;
			case 2: // 期首が2月のときは、1月の年に+1
				s = MON_TBL2[ i ];
				if ( "01".equals( s ) ) {
					result = Integer.toString( yyyy + 1 ) + s;
				} else {
					result = Integer.toString( yyyy ) + s;
				}
				break;
			case 3: // 期首が3月のときは、1～2月の年に+1
				s = MON_TBL3[ i ];
				if ( "01".equals( s ) || "02".equals( s ) ) {
					result = Integer.toString( yyyy + 1 ) + s;
				} else {
					result = Integer.toString( yyyy ) + s;
				}
				break;
			case 4: // 期首が3月のときは、1～3月の年に+1
				s = MON_TBL4[ i ];
				if ( "01".equals( s ) || "02".equals( s ) || "03".equals( s ) ) {
					result = Integer.toString( yyyy + 1 ) + s;
				} else {
					result = Integer.toString( yyyy ) + s;
				}
				break;
			case 5: // 期首が5月のときは、1～4月の年に+1
				s = MON_TBL5[ i ];
				if ( "01".equals( s ) || "02".equals( s ) || "03".equals( s ) ||  
					"04".equals( s ) ) {
					result = Integer.toString( yyyy + 1 ) + s;
				} else {
					result = Integer.toString( yyyy ) + s;
				}
				break;
			case 6: // 期首が6月のときは、1～5月の年に+1
				s = MON_TBL6[ i ];
				if ( "01".equals( s ) || "02".equals( s ) || "03".equals( s ) ||  
					"04".equals( s ) || "05".equals( s ) ) {
					result = Integer.toString( yyyy + 1 ) + s;
				} else {
					result = Integer.toString( yyyy ) + s;
				}
				break;
			case 7: // 期首が7月のときは、1～6月の年に+1
				s = MON_TBL7[ i ];
				if ( "01".equals( s ) || "02".equals( s ) || "03".equals( s ) ||  
					"04".equals( s ) || "05".equals( s ) || "06".equals( s ) ) {
					result = Integer.toString( yyyy + 1 ) + s;
				} else {
					result = Integer.toString( yyyy ) + s;
				}
				break;
			case 8: // 期首が8月のときは、1～7月の年に+1
				s = MON_TBL8[ i ];
				if ( "01".equals( s ) || "02".equals( s ) || "03".equals( s ) ||  
					"04".equals( s ) || "05".equals( s ) || "06".equals( s ) ||
					"07".equals( s ) ) {
					result = Integer.toString( yyyy + 1 ) + s;
				} else {
					result = Integer.toString( yyyy ) + s;
				}
				break;
			case 9: // 期首が9月のときは、1～8月の年に+1
				s = MON_TBL9[ i ];
				if ( "01".equals( s ) || "02".equals( s ) || "03".equals( s ) ||  
					"04".equals( s ) || "05".equals( s ) || "06".equals( s ) ||
					"07".equals( s ) || "08".equals( s ) ) {
					result = Integer.toString( yyyy + 1 ) + s;
				} else {
					result = Integer.toString( yyyy ) + s;
				}
				break;
			case 10: // 期首が10月のときは、1～9月の年に+1
				s = MON_TBL10[ i ];
				if ( "01".equals( s ) || "02".equals( s ) || "03".equals( s ) ||  
					"04".equals( s ) || "05".equals( s ) || "06".equals( s ) ||
					"07".equals( s ) || "08".equals( s ) || "09".equals( s ) ) {
					result = Integer.toString( yyyy + 1 ) + s;
				} else {
					result = Integer.toString( yyyy ) + s;
				}
				break;
			case 11: // 期首が11月のときは、1～10月は年に+1
				s = MON_TBL11[ i ];
				if ( "01".equals( s ) || "02".equals( s ) || "03".equals( s ) ||  
					"04".equals( s ) || "05".equals( s ) || "06".equals( s ) ||
					"07".equals( s ) || "08".equals( s ) || "09".equals( s ) ||
					"10".equals( s ) ) {
					result = Integer.toString( yyyy + 1 ) + s;
				} else {
					result = Integer.toString( yyyy ) + s;
				}
				break;
			case 12: // 期首が12月のときは、12月は年に-1
				s = MON_TBL12[ i ];
				if(	"12".equals( s ) ) {
					result = Integer.toString( yyyy - 1 ) + s;
				} else {
					result = Integer.toString( yyyy ) + s;
				}
				break;
		}
		return result;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 時刻文字列整形
	* @param	strHHMM	時刻(HHMM)	(String)
	* @return	時刻(HH:MM)			(String)<br>
	* ※1～4文字以外は、"00:00"を返す。
	********************************************************************************
	*/
	public static String getHHMM(	String 	strHHMM	) throws Exception
	{
		String result = "00:00";
		if ( strHHMM.length() == 1 ) {
			result = "00:0" + strHHMM;
		}
		if ( strHHMM.length() == 2 ) {
			result = "00:" + strHHMM;
		}
		if ( strHHMM.length() == 3 ) {
			result = "0" + strHHMM.substring( 0, 1 ) + ":" + strHHMM.substring( 1 );
		}
		if ( strHHMM.length() == 4 ) {
			result = strHHMM.substring( 0, 2 ) + ":" + strHHMM.substring( 2 );
		}
		return result;
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
