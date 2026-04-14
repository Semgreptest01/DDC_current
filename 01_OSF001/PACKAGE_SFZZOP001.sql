CREATE OR REPLACE PACKAGE OSF001.sfzzop001
/************************************************************************/
/*	ＰＧＭ名	：  サービス時間判定									*/
/*	ＰＧＭ ｉｄ	：	sfzzop001											*/
/*  機能		：	in_parameterの処理連番をＫＥＹに『処理』を参照し	*/
/*					ｻｰﾋﾞｽ時間の判定を行う								*/
/*	作成者		：	DIS	R.Kameoki										*/
/*	作成日		：	2000/02/02											*/
/************************************************************************/
as
procedure	main
	(P_メニュー単位連番	in	varchar2,
	P_判定結果			out	varchar2,
	P_処理日			out	varchar2);
end	sfzzop001;
/

GRANT EXECUTE ON OSF001.SFZZOP001 TO OPL001;
GRANT EXECUTE ON OSF001.SFZZOP001 TO HXA011;
GRANT EXECUTE ON OSF001.SFZZOP001 TO OPL002;
GRANT EXECUTE ON OSF001.SFZZOP001 TO OHN001;

