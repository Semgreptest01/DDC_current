/**
*-------------------------------------------------------------------------------
* クラス名			JspUtility.class
* システム名称		共通
* 名称				ユーティリティ・JSPクラス
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

import java.util.Vector;

import common.Config;
import common.Const;

public class JspUtility {
	/**
	********************************************************************************
	* optionタグの取得
	* @param	cdAndNms	(Vector)
	* @param	slctCd		(String)
	* @param	blnkRwFlg	(boolean)
	* @param	dsplyCdFlg	(boolean)
	* @param	blnkLbl		(String)
	* @return	optionタグ	(Vector)
	********************************************************************************
	*/
	public static Vector getOptnTg(	Vector		cdAndNms,
										String		slctCd,
										boolean	blnkRwFlg,
										boolean	dsplyCdFlg,
										String		blnkLbl	) throws Exception
	{
		Vector wk_cmbDt = new Vector();
		String wk_slct = "";
		String wk_clss = "";
		String wk_dsplyCd = "";
		if ( blnkRwFlg ) {
			if ( "".equals( slctCd ) ) {
				wk_slct = Const.SLCT_SLCTD;
			}
			wk_cmbDt.add( "<option value=\"\"" + wk_slct + ">" + blnkLbl + "</option>" );
		}
		String[] wk_arry	= null;
		for ( int i = 0; i < cdAndNms.size(); i++ ) {
			wk_arry	= (String[])cdAndNms.get(i);
			if (
				wk_arry.length > Const.ARRY_INDX_ERR_SW &&
				Const.SW_ON.equals( wk_arry[ Const.ARRY_INDX_ERR_SW ] )
			) {
				wk_clss = " class=\"" + Const.CLS_ERR_OPTN + "\"";
			} else {
				wk_clss = "";
			}
			if ( wk_arry[ Const.ARRY_INDX_CD ].equals( slctCd ) ) {
				wk_slct = Const.SLCT_SLCTD;
			} else {
				wk_slct = "";
			}
			if ( dsplyCdFlg ) {
				wk_dsplyCd = wk_arry[ Const.ARRY_INDX_CD ] + ":";
			}
			wk_cmbDt.add( "<option " + wk_clss + "value=\"" + wk_arry[ Const.ARRY_INDX_CD ] + "\"" + wk_slct + ">" + wk_dsplyCd + wk_arry[ Const.ARRY_INDX_NM ] + "</option>" );
		}
		return wk_cmbDt;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* optionタグの取得
	* @param	cdAndNms	(Vector)
	* @param	slctCd		(String)
	* @param	blnkRwFlg	(boolean)
	* @param	dsplyCdFlg	(boolean)
	* @return	optionタグ	(Vector)
	********************************************************************************
	*/
	public static Vector getOptnTg(	Vector		cdAndNms,
										String		slctCd,
										boolean	blnkRwFlg,
										boolean	dsplyCdFlg ) throws Exception
	{
		return getOptnTg( cdAndNms, slctCd, blnkRwFlg, dsplyCdFlg, "" );
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 連動セレクト情報配列の文字列
	* @param	arryNm							(String)
	* @param	kyNmbr							(int)
	* @param	kyAndArrys						(Vector)
	* @param	dclrVrblFlg						(boolean)
	* @param	dsplyCdFlg						(boolean)
	* @return	連動セレクト情報配列の文字列	(String)
	********************************************************************************
	*/
	public static String getLnkdSlctDtArryStrng(	String		arryNm,
													int			kyNmbr,
													Vector		kyAndArrys,
													boolean	dclrVrblFlg,
													boolean	dsplyCdFlg ) throws Exception
	{
		String wk_str = "";
		String wk_dsplyCd = "";
		if ( dclrVrblFlg ) {
			wk_str = "var ";
		}
		wk_str = arryNm + " = new Array();\n";
		
		wk_str += arryNm + ".push( new Array( new Array( ";
		for ( int i = 0; i < kyNmbr; i++ ) {
			if ( i > 0 ) {
				wk_str += ", ";
			}
			wk_str += "\"\"";
		}
		wk_str += " ), new Array( new Array( \"\", \"\" ) ) ) );\n";
		
		String[] wk_arry	= null;
		Vector wk_kyAndArry	= null;
		Vector wk_kys		= null;
		Vector wk_cdAndNms	= null;
		for ( int i = 0; i < kyAndArrys.size(); i++ ) {
			wk_kyAndArry = (Vector)kyAndArrys.get( i );
			wk_kys = (Vector)wk_kyAndArry.get( Const.VCTR_INDX_KYS );
			wk_cdAndNms = (Vector)wk_kyAndArry.get( Const.VCTR_INDX_CD_AND_NMS );
			
			wk_str += arryNm + ".push( new Array( new Array( ";
			for ( int j = 0; j < kyNmbr; j++ ) {
				if ( j > 0 ) {
					wk_str += ", ";
				}
				wk_str += "\"" + wk_kys.get( j ) + "\"";
			}
			wk_str += " ), new Array(\n";
			for ( int j = 0; j < wk_cdAndNms.size(); j++ ) {
				wk_str += "\t";
				if ( j > 0 ) {
					wk_str += ",";
				}
				wk_str += "\tnew Array( ";
				wk_arry	= (String[])wk_cdAndNms.get( j );
				if ( dsplyCdFlg ) {
					wk_dsplyCd = wk_arry[ Const.ARRY_INDX_CD ] + ":";
				}
				wk_str += "\"" + wk_arry[ Const.ARRY_INDX_CD ] + "\", \"" + wk_dsplyCd + wk_arry[ Const.ARRY_INDX_NM ] + "\" )\n";
			}
			wk_str += ") ) );\n";
		}
		return wk_str;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* セレクト情報配列の文字列の取得
	* @param	arryNm						(String)
	* @param	cdAndNms					(Vector)
	* @param	dsplyCdFlg					(boolean)
	* @return	セレクト情報配列の文字列	(String)
	********************************************************************************
	*/
	public static String getSlctDtArryStrng(	String		arryNm,
												Vector		cdAndNms,
												boolean	dsplyCdFlg ) throws Exception
	{
		String wk_str = "var " + arryNm + " = new Array();\n";
		String wk_dsplyCd = "";
		String[] wk_arry	= null;
		for ( int i = 0; i < cdAndNms.size(); i++ ) {
			wk_arry	= (String[])cdAndNms.get(i);
			if ( dsplyCdFlg ) {
				wk_dsplyCd = wk_arry[ Const.ARRY_INDX_CD ] + ":";
			}
			wk_str += arryNm + ".push( new Array( \"" + wk_arry[ Const.ARRY_INDX_CD ] + "\", \"" + wk_dsplyCd + wk_arry[ Const.ARRY_INDX_NM ] + "\" ) );\n";
		}
		return wk_str;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* 表示終了位置取得
	* @param	dtlCnt		(int)
	* @param	strtIdx		(int)
	* @return	表示終了位置	(int)
	********************************************************************************
	*/
	public static int getEndIdx(	int	dtlCnt,	int	strtIdx	)
	{
		int endIdx	= strtIdx + Config.DTL_MAX_DSP_AT_A_TM - 1;
		if ( endIdx + 1 > dtlCnt ) {
			endIdx	= dtlCnt - 1;
		}
		return endIdx;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* １ページ前の表示開始位置を取得
	* @param	strtIdx						(int)
	* @return	１ページ前の表示開始位置	(int)
	********************************************************************************
	*/
	public static int getPrvStrtIdx(	int	strtIdx	)
	{
		int prvStrtIdx = strtIdx - Config.DTL_MAX_DSP_AT_A_TM;
		if ( prvStrtIdx < 0 ) {
			prvStrtIdx = 0;
		}
		return prvStrtIdx;
	}	// ----------------------- End of Method -----------------------------------
	/**
	********************************************************************************
	* １ページ後の表示開始位置を取得
	* @param	dtlCnt						(int)
	* @param	strtIdx						(int)
	* @return	１ページ後の表示開始位置	(int)
	********************************************************************************
	*/
	public static int getNxtStrtIdx(	int	dtlCnt,	int	strtIdx	)
	{
		int nxtStrtIdx = strtIdx + Config.DTL_MAX_DSP_AT_A_TM;
		if ( nxtStrtIdx + 1 > dtlCnt ) {
			nxtStrtIdx = dtlCnt - 1;
		}
		return nxtStrtIdx;
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
