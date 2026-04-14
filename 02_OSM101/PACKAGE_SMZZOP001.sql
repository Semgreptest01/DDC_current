CREATE OR REPLACE PACKAGE OSM101.smzzop001
/************************************************************************/
/* ＰＧＭ名 ：  サービス時間判定         */
/* ＰＧＭ ｉｄ ： smzzop001           */
/*  機能  ： in_parameterの処理連番をＫＥＹに『処理』を参照し */
/*     ｻｰﾋﾞｽ時間の判定を行う        */
/* 作成者  ： DIS R.Kameoki          */
/* 作成日  ： 2000/02/02           */
/************************************************************************/
as
procedure main
 (P_メニュー単位連番 in varchar2,
 P_判定結果   out varchar2,
 P_処理日   out varchar2);
end smzzop001;
/

GRANT EXECUTE ON OSM101.SMZZOP001 TO HXA011;
GRANT EXECUTE ON OSM101.SMZZOP001 TO HHN001 WITH GRANT OPTION;

