/**
*-------------------------------------------------------------------------------
* クラス名			ServletX.class
* システム名称		共通
* 名称				サーブレットＸ スーパークラス（認証チェックなし）
* 会社名or所属名	富士ソフトＤＩＳ
* 作成日			2006/05/25 00:00:00
* @author			Y.Takabayashi
* @since			1.0
* @version			1.0
* *** 修正履歴 ***
* No.  Date 		Author			Description
*-------------------------------------------------------------------------------
*/

package common;

public abstract class ServletX extends ServletA
{
	/**
	 * 認証チェックするか
	 */
	private static final boolean IS_CHK_ATHNTCTN	 = false;
	/**
	********************************************************************************
	* 認証チェックするか
	* @return	IS_CHK_ATHNTCTN	(boolean)
	********************************************************************************
	*/
	protected boolean isChkAthntctn()
	{
		return IS_CHK_ATHNTCTN;
	}	// ----------------------- End of Method -----------------------------------
}		// *********************** End of Class  ***********************************
