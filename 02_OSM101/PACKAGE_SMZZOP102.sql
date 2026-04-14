CREATE OR REPLACE PACKAGE OSM101.smzzop102
/************************************************************************/
/*  ＰＧＭ名    ：  取引相手先コード取得                                */
/*  ＰＧＭ ｉｄ ：  smzzop102                                           */
/*  機能  ： in_paramの氏名コード（利用者コード）          */
/*       に対する取引相手先名＿略称を取得する          */
/*                                   */
/* 作成者  ： SAVE R.Shirato                    */
/* 作成日  ： 2001/10/04                      */
/************************************************************************/
as
procedure   main
            (P_氏名コード       in  varchar2,
            P_処理結果          out varchar2,
            P_取引相手先名    out varchar2);
end smzzop102;
/

GRANT EXECUTE ON OSM101.SMZZOP102 TO HXA011;
GRANT EXECUTE ON OSM101.SMZZOP102 TO HHN001 WITH GRANT OPTION;

