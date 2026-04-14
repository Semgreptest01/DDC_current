CREATE OR REPLACE PACKAGE BODY OSM101.smzzop021	as
/************************************************************************/
/*	ＰＧＭ名	：  SMM_メニュー単位.サービス区分の更新					*/
/*	ＰＧＭ ｉｄ	：	smzzop021											*/
/*  機能		：	『SMM_メニュー単位.サービス区分』のUPDATE			*/
/*					 													*/
/*	作成者		：	DIS	R.Kameoki										*/
/*	作成日		：	2000/03/22											*/
/************************************************************************/
function	main
			(P_メニュー単位		in	varchar2,
			P_サービス区分		in	varchar2)
	return	varchar2
	as
--	ＰＧＭ内変数
	W_TODAY			date;
	W_処理結果		char(2);
	W_存在チェック	number;
begin
--	セキュリティシステム日付ＧＥＴ
	W_TODAY	:=	smzzop903.main;
--  セキュリティシステムの照会可否チェック
	W_処理結果	:=	greatest(smzzop901.main('SMM_メニュー単位'));
	if	W_処理結果	!=	smzzop900.C_CHK_00	then
		return	W_処理結果;
	end if;
--	入力値チェック
	if	P_メニュー単位	is	null
	 or	P_メニュー単位	=	'%'	then
		return	'09';
	end if;
--	SMM_メニュー単位存在チェック
	SELECT	COUNT(*)
		INTO	W_存在チェック
		FROM	SMM_メニュー単位
		WHERE	メニュー単位	like	P_メニュー単位
		  AND	W_TODAY	BETWEEN	有効開始日 AND 有効終了日;
	if	W_存在チェック	=	0	then
		return	'09';
	end if;
--	SMM_メニュー単位更新
	UPDATE	SMM_メニュー単位@LE2A.world
		SET	サービス区分		=	P_サービス区分,
			最終更新日時		=	SYSDATE,
			最終更新氏名コード	=	'SECCOMN'
		WHERE	メニュー単位	like	P_メニュー単位
		  AND	W_TODAY	BETWEEN	有効開始日 AND 有効終了日;
	commit;
	return	'00';
exception
	when	others	then
		return	'09';
end;
end	smzzop021;
/

GRANT EXECUTE ON OSM101.SMZZOP021 TO OXA011;
GRANT EXECUTE ON OSM101.SMZZOP021 TO OHN001 WITH GRANT OPTION;
GRANT EXECUTE ON OSM101.SMZZOP021 TO OHN002 WITH GRANT OPTION;

