CREATE OR REPLACE PACKAGE BODY OSF001.sfzzop903	as
/************************************************************************/
/*	ＰＧＭ名	：  セキュリティシステム処理日ＧＥＴ					*/
/*	ＰＧＭ ｉｄ	：	sfzzop903											*/
/*  機能		：	『SFC_画面処理日』を参照							*/
/*																		*/
/*	作成者		：	DIS	R.Kameoki										*/
/*	作成日		：	2000/02/21											*/
/************************************************************************/
function	main
	return	date
as
	W_処理日	SFC_画面処理日.処理日%type;
begin
	select	TRUNC(処理日)
		into	W_処理日
		from	SFC_画面処理日;
	return	W_処理日;
exception
	when	others	then
		return	trunc(sysdate);
end;
end	sfzzop903;
/

GRANT EXECUTE ON OSF001.SFZZOP903 TO OSF002;

