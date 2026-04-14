CREATE OR REPLACE PACKAGE BODY OSM101.smzzop102
/************************************************************************/
/* ＰＧＭ名 ：  取引相手先名称取得                  */
/* ＰＧＭ ｉｄ ： smzzop102                      */
/*  機能  ： in_paramの氏名コード（利用者コード）に対する      */
/*       取引相手先名＿略称を取得する              */
/*                                   */
/* 作成者  ： SAVE R.Shirato                    */
/* 作成日  ： 2001/10/04                      */
/************************************************************************/
as
procedure main
   (P_氏名コード  in varchar2,
   P_処理結果   out varchar2,
   P_取引相手先名 out varchar2)
 as
--      ＰＧＭ内変数
 W_有効日付     date;
begin

--      セキュリティシステム日付ＧＥＴ
W_有効日付      :=      smzzop903.main;

--  セキュリティシステムの照会可否チェック
 P_処理結果 := greatest(smzzop901.main('SMM_セキュリティ取引相手先'));
 if P_処理結果 != smzzop900.C_CHK_00 then
  return;
 end if;
-- 取引相手先名＿略称取得
 select 取引相手先名＿略称
  into P_取引相手先名
  from SMM_セキュリティ取引相手先
  where 取引相手先ＳＥＱコード = substrb(P_氏名コード,1,5)
          and W_有効日付 between 有効開始日 and 有効終了日;
 P_処理結果 := '00';
exception
 when no_data_found then
  P_処理結果 := '08';
 when others then
  P_処理結果 := '09';
end;
end smzzop102;
/

GRANT EXECUTE ON OSM101.SMZZOP102 TO HXA011;
GRANT EXECUTE ON OSM101.SMZZOP102 TO HHN001 WITH GRANT OPTION;

