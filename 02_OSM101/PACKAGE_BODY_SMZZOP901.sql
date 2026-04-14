CREATE OR REPLACE PACKAGE BODY OSM101.smzzop901	as
/************************************************************************/
/*	ＰＧＭ名	：  セキュリティシステム稼動可否判定					*/
/*	ＰＧＭ ｉｄ	：	smzzop901											*/
/*  機能		：	『SMC_サービス状況』をin_paraで参照					*/
/*																		*/
/*	作成者		：	DIS	R.Kameoki										*/
/*	作成日		：	2000/02/21											*/
/************************************************************************/
function	main
	(P_テーブル名		in	varchar2
	)
	return	char
as
	W_サービス区分	SMC_サービス状況.サービス区分%type;
begin
	select	サービス区分
		into	W_サービス区分
		from	SMC_サービス状況
		where	テーブル名	=	P_テーブル名;
	return	W_サービス区分;
exception
	when	no_data_found	then
		return	'00';	--サービス中
	when	too_many_rows	then
		return	'13';	--トラブル中
end;
end	smzzop901;
/
