/**
*-------------------------------------------------------------------------------
* クラス名			TextUtility.class
* システム名称		共通
* 名称				ユーティリティ・テキストクラス
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

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.regex.Pattern;

import common.Config;
import common.Const;

public class TextUtility {
	/**
	 * エスケープ文字列
	 */
	private static final String ESCP_STRNG		= "\\";
	/**
	 * グループ参照文字列
	 */
	private static final String GRP_RFR_STRNG		= "$";
	
	/**
	********************************************************************************
	* 数値カンマ編集
	* @param	in_data				(long)
	* @return	Not "":OK "":ERR	(String)
	********************************************************************************
	*/
	public static String c_Edit(	long	in_data	) {
		try {
			String wk_str=( new Long( in_data ) ).toString();
			return c_Edit( wk_str );
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 数値カンマ編集
	* @param	in_data				(double)
	* @param	syousu_keta			(int)
	* @return	Not "":OK "":ERR	(String)
	********************************************************************************
	*/
	public static String c_Edit(	double	in_data,	int syousu_keta	) {
		try {
			double	wk_double = in_data * Math.pow( 10, syousu_keta );
			wk_double = Math.round( wk_double );
			wk_double = wk_double / (double)Math.pow( 10, syousu_keta );
			String	wk_str = ( new Double( wk_double ) ).toString();
			int 	wk_piri_pos = wk_str.indexOf( '.' );
			String	wk_syosu = wk_str.substring( wk_piri_pos );
			if ( wk_syosu.length() <= syousu_keta ) {
				while ( wk_syosu.length() <= syousu_keta ) {
					wk_syosu += "0";
				}
			} else {
				wk_syosu = wk_syosu.substring( 0, syousu_keta + 1 );
			}
			return c_Edit( wk_str.substring( 0, wk_piri_pos ) ) + wk_syosu;
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 数値カンマ編集
	* @param	in_data				(String)
	* @return	Not "":OK "":ERR	(String)
	********************************************************************************
	*/
	public static String c_Edit(	String	in_data	) {
		try {
			String wk_str = in_data;
			String out_data = "";
			String fugou = "";
			if ( wk_str.indexOf( "-" ) >= 0 ) {
				fugou = "-";
				wk_str = wk_str.substring( 1 );
			}
			while ( wk_str.length() > 3 ) {
				out_data = "," + wk_str.substring( wk_str.length() - 3 ) + out_data;
				wk_str = wk_str.substring( 0, wk_str.length() - 3 );
			}
			out_data = wk_str + out_data;
			return fugou + out_data;
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 数値カンマ除去
	* @param	in_data				(String)
	* @return	Not "":OK "":ERR	(String)
	********************************************************************************
	*/
	public static String c_Delete(	String	in_data	) {
		try {
			String wk_str = in_data;
			wk_str = Pattern.compile( "," ).matcher( wk_str ).replaceAll( "" );
			return wk_str;
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 数値編集（３桁カンマ付与＆少数桁・四捨五入まるめ）
	* @param	in_data											(String)
	* @param	ketasu (小数点桁数）							(int)
	* @param	yen (先行付与文字）								(String)
	* @param	c_sw (0:カンマ付与しない 0以外:カンマ付与する）	(int)
	* @return	Not null:OK null:ERR							(String)
	********************************************************************************
	*/
	public static String numberFormat(	String	in_data,
										int		ketasu,
										String	yen,
										int		c_sw	) {
		try {
			if (
				"".equals( in_data ) || "NaN".equals( in_data ) ||
				"Infinity".equals( in_data ) || "-Infinity".equals( in_data )
			) {
				in_data = "0";
			}
			//String s = "";
			String out_data = "";
			NumberFormat formatter = NumberFormat.getNumberInstance();
			// 指定桁数で少数桁・四捨五入
			BigDecimal big_in		= new BigDecimal( in_data );
			BigDecimal big1			= new BigDecimal( "1" );
			BigDecimal bigResult	= big_in.divide( big1, ketasu,BigDecimal.ROUND_HALF_UP );
			if ( c_sw == 0 ) {
				out_data			= yen + bigResult.toString();
			} else {
				out_data			= yen + formatter.format( bigResult );
			}
			return out_data;
		} catch ( Exception e ) {
			return null;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* バイト数からキロバイト数へ変換
	* @param	sz				(long)
	* @param	plcsOfDcmls		(int)
	* @return	キロバイト数	(double)
	********************************************************************************
	*/
	public static double cvrtBytToKbyt(	long	sz,	int	plcsOfDcmls	)
	{
		return new BigDecimal( (double)sz / 1024 ).setScale( plcsOfDcmls, BigDecimal.ROUND_HALF_UP ).doubleValue();
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* スペース文字埋め込み
	* @param	in_data					(String)
	* @param	in_size					(int)
	* @return	Not null:OK null:ERR	(String)
	********************************************************************************
	*/
	public static String fillSpace(	String	in_data,	int		in_size	) {
		try {
			String s = "";
			int x = in_data.length();
			int y = in_size;
			if ( x > y ) {
				int j = x - y;
				for ( int i = 0 ; i < j; i++ ) {
					s += " ";
				}
				return in_data + s;
			}else{
				return in_data;
			}
		} catch ( Exception e ) {
			return null;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 先行ゼロ削除
	* @param	in_data					(String)
	* @param	in_size					(int)
	* @return	Not null:OK null:ERR	(String)
	********************************************************************************
	*/
	public static String delZero( String	in_data ) {
		try {
			int x = in_data.length();
			int wk;
			wk = x;
			if ( !"".equals( in_data ) ) {
				for ( int i = 0; i < x - 1; i++ ) {
					if ( "0".equals( in_data.substring( 0, 1 ) ) ) {
						in_data = in_data.substring( 1, wk );
						wk = wk - 1;
					}else{
						break;
					}
				}
			}
			return in_data;
		} catch ( Exception e ) {
			return null;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 先行ゼロ埋め込み
	* @param	in_data					(String)
	* @param	in_size					(int)
	* @return	Not null:OK null:ERR	(String)
	********************************************************************************
	*/
	public static String fillZero(	String	in_data,
									int		in_size) {
		try {
			String s = "";
			int x = in_data.length();
			int y = in_size;
			if ( x < y ) {
				int j = y - x;
				for ( int i = 0; i < j; i++ ) {
					s += "0";
				}
				return s + in_data;
			}else{
				return in_data;
			}
		} catch ( Exception e ) {
			return null;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字列の指定文字分切り出し
	* @param	in_data				(String)
	* @param	out_length			(int)
	* @return	Not "":OK "":ERR	(String)
	********************************************************************************
	*/
	public static String getSubString(	String	in_data,
										int		out_length) {
		try {
			String out_data = "";
			out_data = in_data;
			if ( in_data.length() > out_length ) {
				out_data = in_data.substring( 0, out_length );
			}
			return out_data;
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 半角の数値文字列を全角へ変換
	* @param	from_str			(String)
	* @return	全角の数値文字列	(String)
	********************************************************************************
	*/
	public static String chngNmbrToDbl(	String	from_str	) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		char chr;
		for ( int i = 0; i < from_str.length(); i++ ) {
			chr = from_str.charAt( i );
			chr += '０' - '0';
			buf.append( chr );
		}
		return buf.toString();
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 引用符で囲む
	* @param	in_data					(String)
	* @return	引用符で囲んだ文字列	(String)
	********************************************************************************
	*/
	public static String quote(	String	in_data	) {
		return "'" + in_data + "'";
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 数値文字列をintに変換
	* @param	in_data	(String)
	* @return	数値	(int)
	********************************************************************************
	*/
	public static int cvtStringToInt(	String	in_data	) {
		try {
			return ( in_data == null || "".equals( in_data ) ? 0 : Integer.parseInt( in_data ) );
		} catch ( Exception e ) {
			return 0;
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字列エンコード
	* @param	strVal	(入力文字列）		(String)
	* @return	エンコード結果:OK null:ERR	(String)
	********************************************************************************
	*/
	public static String strEncode(	String	strVal	) throws UnsupportedEncodingException
	{
		if ( strVal == null ) {
			return null;
		} else {
			if ( Const.CON_SW_TOMCAT_DRCT.equals( Config.CON_SW ) ) {
				return ( new String( strVal.getBytes( "ISO-8859-1" ), "JISAutoDetect" ) );
			} else {
				return strVal;
			}
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 指定文字列のエンコード
	* @param	in_data									(String)
	* @param	from_enc ( JISAutoDetect, EUC-JP, 等）	(String)
	* @param	to_enc   ( SJIS, ISO8859_1, EUC-JP, 等）(String)
	* @return	エラーの場合、入力をそのまま返す		(String)
	********************************************************************************
	*/
	public static String strEncode(	String	in_data,
										String	from_enc,
										String	to_enc	) {
		String out_str = in_data;
		if ( in_data != null ) {
			try {
				return new String( in_data.getBytes( to_enc ), from_enc );
			} catch ( UnsupportedEncodingException e ) {
				return in_data;
			}
		}
		return out_str;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 指定文字列のHEX表示
	* @param	in_data		(String)
	* @return	HexString	(String)
	********************************************************************************
	*/
	public static String printCharCode(	String	s	) {
		int hex;
		String result = "";
		for ( int i = 0; i < s.length(); i++ ) {
			hex = (int)s.charAt( i );
			result += s.charAt( i ) + ":[" + Integer.toHexString( hex ) + "] ";
		}
		return result;
	}
	/**
	********************************************************************************
	* エスケープ文字列をエスケープ
	* @param	in_data	(String)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String escapeEscpStrng(	String	in_data	) {
		String out_data = in_data;
		if ( out_data != null ) {
			out_data = Pattern.compile( ESCP_STRNG + ESCP_STRNG ).matcher( out_data ).replaceAll( ESCP_STRNG + ESCP_STRNG + ESCP_STRNG + ESCP_STRNG );
		}
		return out_data;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* グループ参照文字列をエスケープ
	* @param	in_data	(String)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String escapeGrpRfrStrng(	String	in_data	) {
		String out_data = in_data;
		if ( out_data != null ) {
			out_data = Pattern.compile( ESCP_STRNG + GRP_RFR_STRNG ).matcher( out_data ).replaceAll( ESCP_STRNG + ESCP_STRNG + ESCP_STRNG + GRP_RFR_STRNG );
		}
		return out_data;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 置換文字列をエスケープ
	* @param	in_data	(String)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String escapeRplcStrng(	String	in_data	) {
		return escapeGrpRfrStrng( escapeEscpStrng( in_data ) );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字列を置換する
	* @param	in_data	(String)
	* @param	argmnts	(String[])
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String rplcStrg(	String		in_data,
									String[]	argmnts	) throws Exception
	{
		String str = in_data;
		for ( int i = 0; i < argmnts.length; i++ ) {
			str = Pattern.compile( "\\[&STR" + String.valueOf( i ) + "\\]" ).matcher( str ).replaceAll( escapeRplcStrng( argmnts[ i ] ) );
		}
		return str;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字列を置換する
	* @param	in_data	(String)
	* @param	argmnt	(String)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String rplcStrg(	String	in_data,
									String	argmnt	) throws Exception
	{
		String[] tmpArry = { argmnt };
		return rplcStrg( in_data, tmpArry );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 置換用文字列取得
	* @param	i					(int)
	* @return	置換用文字列取得	(String)
	********************************************************************************
	*/
	public String getRplcStrng( int i )
	{
		return "[&STR" + String.valueOf( i ) + "]";
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 千円単位変換（円単位→千円単位）
	* @param	in_str	円単位の金額文字列	(String)
	* @return	千円単位の金額文字列		(String)
	********************************************************************************
	*/
	public static String get1000YenUnit(	String	in_str	) throws Exception
	{
		String result = "";
		// 千円単位に変換する。
		// ""の場合は、そのまま。0は、0で返す。
		// 上記以外は、1000で割り、整数四捨五入(ROUND_HALF_UP)とする。
		int wk_int = 0;
		if( !"".equals( in_str ) ) {
			wk_int = Integer.parseInt( in_str );
			if ( wk_int == 0 ) {
				result = "0";
			} else {
				BigDecimal decimal1000 = BigDecimal.valueOf( 1000L );
				BigDecimal decimal_in  = new BigDecimal( in_str );
				BigDecimal decimal_out = new BigDecimal( "0" );
				// 四捨五入の場合は以下、切捨ての場合は上記の記述となる。
				decimal_out = decimal_in.divide(
						decimal1000, 0 , BigDecimal.ROUND_HALF_UP );
				result = decimal_out.toString();
			}
		}
    	return result;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 円単位変換（千円単位→円単位）
	* @param	in_str	千円単位の金額文字列	(String)
	* @return	円単位の金額文字列				(String)
	********************************************************************************
	*/
	public static String getYenUnit(	String	in_str	) throws Exception
	{
		String result = "";
		// 千円単位文字列を円単位文字列に変換する。
		// ""の場合は、そのまま。0は、0で返す。
		int wk_int = 0;
		if ( !"".equals( in_str ) ) {
			wk_int = Integer.parseInt(in_str);
			if ( wk_int == 0 ) {
				result = "0";
			} else {
				result = Integer.toString( Integer.parseInt( in_str ) * 1000 );
			}
		}
    	return result;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ３桁カンマ編集
	* @param	inString	文字列数値		(String)
	* @param	seisuMax	取出整数Max桁数	(int)
	* @param	seisuMin	取出整数Min桁数	(int)
	* @param	shousuMax	取出小数Max桁数	(int)
	* @param	shousuMin	取出小数Min桁数	(int)
	* @return	取出データ					(String)
	********************************************************************************
	*/
	public static String getCom3(	String	inString,
									int		seisuMax,
									int		seisuMin,
									int		shousuMax,
									int		shousuMin	) throws Exception
	{
		String	result = "";
		double d = 0;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits( shousuMin );		// 小数最小桁数
		nf.setMaximumFractionDigits( shousuMax );		// 小数最大桁数
		nf.setMinimumIntegerDigits( seisuMin );			// 整数最小桁数
		nf.setMaximumIntegerDigits( seisuMax );			// 整数最大桁数
		nf.setParseIntegerOnly ( true );				// 整数のみ解析
		nf.setGroupingUsed ( true );					// ３桁カンマ編集
		try {
           	d = Double.parseDouble( inString );
           	result = nf.format( d );
		} catch ( Exception e ) {
			result = inString;
		}
		return result;
	}   // ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ３桁カンマ編集
	* @param	inString	文字列数値		(String)
	* @param	seisuMax	取出整数Max桁数	(int)
	* @param	shousuMax	取出小数Max桁数	(int)
	* @return	取出データ					(String)
	********************************************************************************
	*/
	public static String getCom3(	String	inString,
									int		seisuMax,
									int		shousuMax	) throws Exception
	{
		return getCom3( inString, seisuMax, 1, shousuMax, 0 );
	}   // ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 全角スペース文字埋め込み（後ろへ追加）
	* @param	in_data	入力文字列		(String)
	* @param	in_size	文字数			(int)
	* @return	Not null:OK null:ERR	(String)
	********************************************************************************
	*/
	public static String ZFillSpace(	String	in_data,	int	in_size	) {
	   try {
	      String s = "";
	      int x = in_data.length();
	      int y = in_size;
	      if ( x < y ) {
	         int j = y - x;
	         for ( int i = 0; i < j; i++ ) {
	            s += "　";
	         }
	         return in_data + s;
	      }else{
	         return in_data;
	      }
	   } catch ( Exception e ) {
	      return null;
	   }
	}   // ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 半角スペース文字埋め込み（後ろへ追加）
	* @param	in_data	入力文字列		(String)
	* @param	in_size	文字数			(int)
	* @return	Not null:OK null:ERR	(String)
	********************************************************************************
	*/
	public static String HFillSpace(	String	in_data,	int	in_size	) {
	   try {
	      String s = "";
	      int x = in_data.length();
	      int y = in_size;
	      if ( x < y ) {
	         int j = y - x;
	         for ( int i = 0; i < j; i++ ) {
	            s += " ";
	         }
	         return in_data + s;
	      }else{
	         return in_data;
	      }
	   } catch ( Exception e ) {
	      return null;
	   }
	}   // ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* StringBufferに句点を追加
	* @param	buf			(StringBuffer)
	* @param	strt_num	(int)
	* @param	ed_num		(int)
	* @return	なし
	********************************************************************************
	*/
	public static void addComma(	StringBuffer	buf,
									int				strt_num,
									int				ed_num	)
	{
		for ( int i = strt_num; i < ed_num; i++ ) {
			buf.append( "," );
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ASCII_HEX数値StringからUnicodeStringへの変換
	* @param	hexString				(String)
	* @param	byteSize	文字数		(int)
	* @return	変換後のUnicodeString	(String)
	********************************************************************************
	*/
	public static String toUniStr(	String	hexString,	int	byteSize	) throws Exception
	{
		String str = null;														// 返却用変換済み文字列
		int cnt = 0;															// 文字列のインデックス
		byte b[] = new byte[ byteSize ];										// 作業用バイト配列
		int cnt2 = 0;															// バイト配列インデックス
		for ( ; cnt < byteSize; cnt += 2 ) {									// 文字列長分だけ以下を繰り返す
			if ( ( hexString.substring( cnt, cnt + 2 ) ).equals( "0x" ) ) {
				// 切り取った2文字が16進を表す"0x"の場合は何もしない。
				continue;
			}else{
				// ２文字分ずつint変換後、byteを生成し､byte配列要素に格納。
				b[ cnt2 ] = Integer.valueOf( hexString.substring( cnt, cnt + 2 ), 16 ).byteValue();
				cnt2++;															// バイト配列インデックスをインクリメント。
			}
		}
		str = new String( b, "EUCJIS" );										// 対応する文字列を生成する。
		return str;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* nullをブランクに変換
	* @param	in_data		(String)
	* @return	出力文字列	(String)
	********************************************************************************
	*/
	public static String cvtNullToBlnk(	String	in_data	) {
		try {
			return ( in_data == null ? "" : in_data );
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* nullを0に変換
	* @param	in_data		(String)
	* @return	出力文字列	(String)
	********************************************************************************
	*/
	public static String cvtBlnkToZr(	String	in_data	) {
		try {
			return ( in_data == null || "".equals( in_data ) ? "0" : in_data );
		} catch ( Exception e ) {
			return "0";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ブランクをHTML表示用スペースに変換
	* @param	in_data		(String)
	* @return	出力文字列	(String)
	********************************************************************************
	*/
	public static String cvtBlnkToHtmlSpc(	String	in_data	) {
		try {
			return ( in_data == null || "".equals( in_data ) ? Const.DSP_SPC : in_data );
		} catch ( Exception e ) {
			return Const.DSP_SPC;	
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字列をHTML表示用に加工
	* @param	in_data			(String)
	* @param	cvt_spc_flg		(boolean)
	* @param	cvt_LF_flg		(boolean)
	* @param	cvt_blnk_flg	(boolean)
	* @param	ins_at_LF_flg	(boolean)
	* @return	出力文字列		(String)
	********************************************************************************
	*/
	public static String cvtStrngToHtmlTxt(	String		in_data,
												boolean	cvt_spc_flg,
												boolean	cvt_LF_flg,
												boolean	cvt_blnk_flg,
												boolean	ins_at_LF_flg	)
		throws Exception
	{
		String out_data = in_data;
		out_data = Pattern.compile( Const.LN_FD_CD ).matcher( out_data ).replaceAll( "\n" );
		if ( ins_at_LF_flg ) {
			StringBuffer buf = new StringBuffer();
			for ( int i = 0; i < out_data.length(); i++ ) {
				if ( "\n".equals( out_data.substring( i, i + 1 ) ) ) {
					buf.append( "\n" );
				} else {
					buf.append( out_data.substring( i, i + 1 ) + Const.PRHBTV_STR );
				}
			}
			out_data = buf.toString();
		}
		out_data = cvtToChrctrEnttyRfrncs( out_data, cvt_spc_flg );
		if ( cvt_LF_flg ) {
			out_data = Pattern.compile( "\n" ).matcher( out_data ).replaceAll( Const.DSP_LN_FD_CD );
		}
		if ( ins_at_LF_flg ) {
			out_data = Pattern.compile( Const.PRHBTV_STR ).matcher( out_data ).replaceAll( Const.DSP_AT_LN_FD_CD );
		}
		if ( cvt_blnk_flg ) {
			out_data = cvtBlnkToHtmlSpc( out_data );
		}
		return out_data;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字列をHTML表示用に加工
	* @param	in_data		(String)
	* @return	出力文字列	(String)
	********************************************************************************
	*/
	public static String cvtStrngToHtmlTxt(	String		in_data	)
		throws Exception
	{
		return cvtStrngToHtmlTxt( in_data, true, true, true, false );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字を文字実態参照に変換
	* @param	in_data		(String)
	* @param	cvt_spc_flg	(boolean)
	* @return	出力文字列	(String)
	********************************************************************************
	*/
	public static String cvtToChrctrEnttyRfrncs(	String		in_data,
													boolean	cvt_spc_flg	)
		throws Exception
	{
		String out_data = in_data;
		out_data = Pattern.compile( Const.AMPRSND ).matcher( out_data ).replaceAll( Const.DSP_AMPRSND );
		out_data = Pattern.compile( Const.LSS_THN ).matcher( out_data ).replaceAll( Const.DSP_LSS_THN );
		out_data = Pattern.compile( Const.GRTR_THN ).matcher( out_data ).replaceAll( Const.DSP_GRTR_THN );
		if ( cvt_spc_flg ) {
			out_data = Pattern.compile( Const.SPC ).matcher( out_data ).replaceAll( Const.DSP_SPC );
		}
		return out_data;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字実態参照に変換
	* @param	in_data		(String)
	* @return	出力文字列	(String)
	********************************************************************************
	*/
	public static String cvtToChrctrEnttyRfrncs(	String	in_data	) throws Exception
	{
		return cvtToChrctrEnttyRfrncs( in_data, false );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 改行コード（表示用）削除
	* @param	in_data		(String)
	* @return	出力文字列	(String)
	********************************************************************************
	*/
	public static String deleteDspLnFdCd(	String	in_data	) {
		return Pattern.compile( Const.DSP_LN_FD_CD ).matcher( in_data ).replaceAll( "" );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字列の改行文字をスペースに変換
	* @param	in_data								(String)
	* @return	改行文字をスペースに変換した文字列	(String)
	********************************************************************************
	*/
	public static String cvtLFToSpc(	String	in_data	) throws Exception
	{
		return Pattern.compile( Const.LN_FD_CD ).matcher( in_data ).replaceAll( Const.SPC );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 括弧の前にHTML用の改行文字を挿入する
	* @param	in_data								(String)
	* @return	HTML用の改行文字を挿入された文字列	(String)
	********************************************************************************
	*/
	public static String insrtDspLFBfrPrnthss(	String	in_data	) throws Exception
	{
		String str = Pattern.compile( "（" ).matcher( in_data ).replaceAll( Const.DSP_LN_FD_CD + "（" );
		return Pattern.compile( "\\(" ).matcher( str ).replaceAll( Const.DSP_LN_FD_CD + "(" );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字列を置換する
	* @param	rgx		(String)
	* @param	in_data	(String)
	* @param	rplcmnt	(String)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String rplcStrg(	String	rgx,
									String	in_data,
									String	rplcmnt	)
	{
		try {
			int x = in_data.indexOf( rgx );
			String ot_data;
			if ( x >= 0 ) {
				String tmpStr = in_data.substring( 0, x ) + rplcmnt + in_data.substring( x + rgx.length(), in_data.length() );
				ot_data = rplcStrg( rgx, tmpStr, rplcmnt );
			} else {
				ot_data = in_data;
			}
			return ot_data;
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字化け対応文字列変換（～、∥、－、￠、￡、￢）
	* @param	in_data			(String)
	* @param	eucJpFlg		(boolean)
	* @return	変換後の文字列	(String)
	********************************************************************************
	*/
	public static String cnvrtSpclChr(	String	in_data,	boolean	eucJpFlg	) {
		try {
			String out_data = in_data;
			if ( out_data == null ) {
				return "";
			}
			if ( eucJpFlg ) {
				out_data = out_data.replace( '\uff5e', '\u301c' );	// ～
				out_data = out_data.replace( '\u2225', '\u2016' );	// ∥
				out_data = out_data.replace( '\uff0d', '\u2212' );	// －
				out_data = out_data.replace( '\uffe0', '\u00a2' );	// ￠		
				out_data = out_data.replace( '\uffe1', '\u00a3' );	// ￡
				out_data = out_data.replace( '\uffe2', '\u00ac' );	// ￢
			} else {
				out_data = out_data.replace( '\u301c', '\uff5e' );	// ～
				out_data = out_data.replace( '\u2016', '\u2225' );	// ∥
				out_data = out_data.replace( '\u2212', '\uff0d' );	// －
			}
			return out_data;
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字化け対応文字列変換
	* @param	in_data			(String)
	* @return	変換後の文字列	(String)
	********************************************************************************
	*/
	public static String cnvrtSpclChr(	String	in_data	) {
		return cnvrtSpclChr( in_data, Config.CHAR_SET.equals( Const.CHAR_SET_EUC_JP ) );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* CSVダウンロード用文字化け対応文字列変換
	* @param	in_data			(String)
	* @return	変換後の文字列	(String)
	********************************************************************************
	*/
	public static String cnvrtSpclChrFrCsvDwnld(	String	in_data	) {
		return cnvrtSpclChr( in_data, Config.DWNLD_CSV_CHAR_SET.equals( Const.CHAR_SET_EUC_JP ) );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 画面表示で文字化けしない"～"を取得
	* @return	画面表示で文字化けしない"～"	(String)
	********************************************************************************
	*/
	public static String getDsplyWvDsh() {
		return TextUtility.cnvrtSpclChr( "～" );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 画面表示で文字化けしない"∥"を取得
	* @return	画面表示で文字化けしない"∥"	(String)
	********************************************************************************
	*/
	public static String getDsplyDblVrtcl() {
		return TextUtility.cnvrtSpclChr( "∥" );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 画面表示で文字化けしない"－"を取得
	* @return	画面表示で文字化けしない"－"	(String)
	********************************************************************************
	*/
	public static String getDsplyMnsSgn() {
		return TextUtility.cnvrtSpclChr( "－" );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 画面表示で文字化けしない"￠"を取得
	* @return	画面表示で文字化けしない"￠"	(String)
	********************************************************************************
	*/
	public static String getDsplyCntSgn() {
		return TextUtility.cnvrtSpclChr( "￠" );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 画面表示で文字化けしない"￡"を取得
	* @return	画面表示で文字化けしない"￡"	(String)
	********************************************************************************
	*/
	public static String getDsplyPndSgn() {
		return TextUtility.cnvrtSpclChr( "￡" );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 画面表示で文字化けしない"￢"を取得
	* @return	画面表示で文字化けしない"￢"	(String)
	********************************************************************************
	*/
	public static String getDsplyNtSgn() {
		return TextUtility.cnvrtSpclChr( "￢" );
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
