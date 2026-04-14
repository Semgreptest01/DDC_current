CREATE OR REPLACE PACKAGE OSM101.smzzop021
/************************************************************************/
/*	ＰＧＭ名	：  SMM_メニュー単位.サービス区分の更新					*/
/*	ＰＧＭ ｉｄ	：	smzzop021											*/
/*  機能		：	『SMM_メニュー単位.サービス区分』のUPDATE			*/
/*					 													*/
/*	作成者		：	DIS	R.Kameoki										*/
/*	作成日		：	2000/03/22											*/
/************************************************************************/
as
function	main
			(P_メニュー単位		in	varchar2,
			P_サービス区分		in	varchar2)
		return	varchar2;
end	smzzop021;
/

GRANT EXECUTE ON OSM101.SMZZOP021 TO OXA011;
GRANT EXECUTE ON OSM101.SMZZOP021 TO OHN001 WITH GRANT OPTION;
GRANT EXECUTE ON OSM101.SMZZOP021 TO OHN002 WITH GRANT OPTION;

