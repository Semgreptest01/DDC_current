CREATE OR REPLACE PACKAGE OSF001.sfzzop903	as
/************************************************************************/
/*	ＰＧＭ名	：  セキュリティシステム処理日ＧＥＴ					*/
/*	ＰＧＭ ｉｄ	：	sfzzop903											*/
/*  機能		：	『SFC_画面処理日』を参照							*/
/*																		*/
/*	作成者		：	DIS	R.Kameoki										*/
/*	作成日		：	2000/02/21											*/
/************************************************************************/
function	main
	return	date;
end	sfzzop903;
/

GRANT EXECUTE ON OSF001.SFZZOP903 TO OSF002;

