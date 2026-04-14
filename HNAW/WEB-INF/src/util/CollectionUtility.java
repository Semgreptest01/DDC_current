/**
*-------------------------------------------------------------------------------
* クラス名			CollectionUtility.class
* システム名称		共通
* 名称				ユーティリティ・コレクションクラス
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

import java.util.List;
import java.util.Vector;

import common.Const;

public class CollectionUtility {
	/**
	********************************************************************************
	* 配列値の取得
	* @param	array	(String[])
	* @param	idx		(int)
	* @return	配列値	(String)
	********************************************************************************
	*/
	public static String getArrayData(	String[]	array,	int	idx	) throws Exception
	{
		try {
			String result = "";
			result = TextUtility.strEncode( array[ idx ] );
			return result;
		} catch ( Exception e ) {
			return "";
		}
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* Listからidxの文字列を取得
	* @param	lst		(List)
	* @param	idx		(int)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String getStrngFrmLst(	List	lst,	int idx	) throws Exception
	{
		String rslt = null;
		if ( lst.size() > idx ) {
			rslt = (String)lst.get( idx );
		}
		return rslt;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字列配列からの名称の取得
	* @param	array	(String[][])
	* @param	key_cd	(String)
	* @return	名称	(String)
	********************************************************************************
	*/
	public static String getClsNmFrmArry(	String[][]	array,
											String		key_cd	)
	{
		String	wk_nm	=	"";
		for ( int i = 0; i < array.length; i++ ) {
			if ( array[ i ][ Const.ARRY_INDX_CD ].equals( key_cd ) ) {
				wk_nm	=	array[ i ][ Const.ARRY_INDX_NM ];
				break;
			}
		}
		return wk_nm;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ベクトルからのコードに該当するインデックスの取得
	* @param	cds				(Vector)
	* @param	key_cd			(String)
	* @return	インデックス	(int)
	********************************************************************************
	*/
	public static int getIdxFromVector(	Vector	cds,
											String	key_cd	) throws Exception
	{
		String	wk_cd	=	"";
		int		idx		=	-1;
		for ( int i = 0; i < cds.size(); i++ ) {
			wk_cd		=	(String)cds.get( i );
			if ( key_cd.equals( wk_cd ) ) {
				idx		=	i;
				break;
			}
		}
		return idx;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ベクトルからの文字列の取得
	* @param	vals	(Vector)
	* @param	idx		(String)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String getStringFromVector(	Vector	vals,
												int		idx	) throws Exception
	{
		String	wk_val	=	"";
		if ( idx >= 0 ) {
			wk_val		=	(String)vals.get( idx );
		}
		return wk_val;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ベクトルからの文字列の取得
	* @param	cds		(Vector)
	* @param	vals	(Vector)
	* @param	key_cd	(String)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String getStringFromVector(	Vector	cds,
												Vector	vals,
												String	key_cd	) throws Exception
	{
		return getStringFromVector( vals, getIdxFromVector( cds, key_cd ) );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* コードと名称からのコードに該当するインデックスの取得
	* @param	cdAndNms		(Vector)
	* @param	key_cd			(String)
	* @return	インデックス	(int)
	********************************************************************************
	*/
	public static int getIdxFrmCdAndNms(	Vector	cdAndNms,
											String	key_cd	)
	{
		String[]	wk_arry	=	null;
		int			idx		=	-1;
		for ( int i = 0; i < cdAndNms.size(); i++ ) {
			wk_arry		=	(String[])cdAndNms.get( i );
			if ( wk_arry[ Const.ARRY_INDX_CD ].equals( key_cd ) ) {
				idx		=	i;
				break;
			}
		}
		return idx;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* コードと名称からの名称の取得
	* @param	cdAndNms	(Vector)
	* @param	idx			(int)
	* @return	名称		(String)
	********************************************************************************
	*/
	public static String getNmFrmCdAndNms(		Vector	cdAndNms,
												int		idx	)
	{
		String[]	wk_arry	=	null;
		String		wk_nm	=	"";
		if ( idx >= 0 ) {
			wk_arry	=	(String[])cdAndNms.get( idx );
			wk_nm	=	wk_arry[ Const.ARRY_INDX_NM ];
		}
		return wk_nm;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* コードと名称からの名称の取得
	* @param	cdAndNms	(Vector)
	* @param	key_cd		(String)
	* @return	名称		(String)
	********************************************************************************
	*/
	public static String getStrngFrmCdAndNms(	Vector	cdAndNms,
												String	key_cd	)
	{
		return getNmFrmCdAndNms( cdAndNms, getIdxFrmCdAndNms( cdAndNms, key_cd ) );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 配列のベクトルから文字列のベクトルの取得
	* @param	vctr				(Vector)
	* @param	idx					(int)
	* @return	文字列のベクトル	(Vector)
	********************************************************************************
	*/
	public static Vector getStrngVctrFrmArryVctr(	Vector	vctr,
													int		idx	)
	{
		Vector		wk_vctr	=	new Vector();
		String[]	wk_arry	=	null;
		for ( int i = 0; i < vctr.size(); i++ ) {
			wk_arry		=	(String[])vctr.get( i );
			wk_vctr.add( wk_arry[ idx ] );
		}
		return wk_vctr;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ベクトルから文字列に変換
	* @param	vctr	(Vector)
	* @param	sprtr	(String)
	* @param	strtIdx	(int)
	* @param	endIdx	(int)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String cnvrtSprtdStrngFrmVctr(	Vector	vctr,
													String	sprtr,
													int		strtIdx,
													int		endIdx	)
	{
		String	wk_str = "";
		int cnt = 0;
		for ( int i = strtIdx; i <= endIdx; i++ ) {
			if ( cnt > 0 ) {
				wk_str += sprtr;
			}
			wk_str += (String)vctr.get( i );
			cnt++;
		}
		return wk_str;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ベクトルから文字列に変換
	* @param	vctr	(Vector)
	* @param	sprtr	(String)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String cnvrtSprtdStrngFrmVctr(	Vector	vctr,	String	sprtr	)
	{
		return cnvrtSprtdStrngFrmVctr( vctr, sprtr, 0, vctr.size() - 1 );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ベクトルから文字列に変換
	* @param	vctr	(Vector)
	* @param	strtIdx	(int)
	* @param	endIdx	(int)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String cnvrtStrngFrmVctr(	Vector	vctr,
												int		strtIdx,
												int		endIdx	)
	{
		return cnvrtSprtdStrngFrmVctr( vctr, "", strtIdx, endIdx );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ベクトルから文字列に変換
	* @param	vctr	(Vector)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String cnvrtStrngFrmVctr(	Vector	vctr	)
	{
		return cnvrtSprtdStrngFrmVctr( vctr, "" );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ベクトルから文字列に変換
	* @param	vctr	(Vector)
	* @param	strtIdx	(int)
	* @param	endIdx	(int)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String cnvrtSprtdLnStrngFrmVctr(	Vector	vctr,
													int		strtIdx,
													int		endIdx	)
	{
		return cnvrtSprtdStrngFrmVctr( vctr, Const.LN_FD_CD, strtIdx, endIdx );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ベクトルから文字列に変換
	* @param	vctr	(Vector)
	* @return	文字列	(String)
	********************************************************************************
	*/
	public static String cnvrtSprtdLnStrngFrmVctr(	Vector	vctr	)
	{
		return cnvrtSprtdStrngFrmVctr( vctr, Const.LN_FD_CD );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* int配列から文字列ベクトルに変換
	* @param	arry				(int[])
	* @return	文字列のベクトル	(Vector)
	********************************************************************************
	*/
	public static Vector cnvrtStrngVctrFrmIntArry(	int[]	arry	)
	{
		Vector	vctr = new Vector();
		for ( int i = 0; i < arry.length; i++ ) {
			vctr.add( String.valueOf( arry[ i ] ) );
		}
		return vctr;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 文字列配列から文字列ベクトルに変換
	* @param	arry				(String[])
	* @return	文字列のベクトル	(Vector)
	********************************************************************************
	*/
	public static Vector cnvrtStrngVctrFrmStrngArry(	String[]	arry	)
	{
		Vector	vctr = new Vector();
		for ( int i = 0; i < arry.length; i++ ) {
			vctr.add( arry[ i ] );
		}
		return vctr;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ベクトルから値を取り出してカンマ区切りの文字列にする
	* @param	vals					(Vector)
	* @return	カンマ区切りの文字列	(String)
	********************************************************************************
	*/
	public static String getVlStrngFromVector(	Vector	vals	) throws Exception
	{
		StringBuffer	buf		=	new StringBuffer();
		String			wk_val	=	"";
		for ( int i = 0; i < vals.size(); i++ ) {
			wk_val				=	(String)vals.get( i );
			if ( i > 0 ) {
				buf.append( ", " );
			}
			buf.append( TextUtility.quote( wk_val ) );
		}
		return buf.toString();
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ベクトルから値を取り出してカンマ区切りの文字列にする
	* @param	vals									(Vector)
	* @return	ブランクではないカンマ区切りの文字列	(String)
	********************************************************************************
	*/
	public static String getNtBlnkVlStrngFromVector(	Vector	vals	) throws Exception
	{
		String	str	=	"''";
		if ( vals.size() > 0 ) {
			str		=	getVlStrngFromVector( vals );
		}
		return str;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* ListにrwNmbrの行が存在するか
	* @param	lst					(List)
	* @param	rwNmbr				(int)
	* @return	true:YES false:NO	(boolean)
	********************************************************************************
	*/
	public static boolean isExstntRw(	List	lst,	int rwNmbr	) throws Exception
	{
		return lst != null && lst.size() > rwNmbr;
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
