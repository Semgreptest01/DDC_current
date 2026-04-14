CREATE OR REPLACE PACKAGE BODY OSF001.sfzzop001	as
/************************************************************************/
/*	ＰＧＭ名	：  サービス時間判定									*/
/*	ＰＧＭ ｉｄ	：	sfzzop001											*/
/*  機能		：	in_parameterの処理連番をＫＥＹに『処理』を参照し	*/
/*					ｻｰﾋﾞｽ時間の判定を行う								*/
/*	作成者		：	DIS	R.Kameoki										*/
/*	作成日		：	2000/02/02											*/
/*	修正		：	2000/03/30 動的ＳＱＬの静的化						*/
/*					2002/08/23 曜日判定ロジックにロケール指定を追加   　*/
/************************************************************************/
procedure	main
	(P_メニュー単位連番	in	varchar2,
	P_判定結果			out	varchar2,
	P_処理日			out	varchar2)
as
--	constant value
	C_SYSDATE	date	:=	sysdate;
--	ＰＧＭ内変数
	W_TODAY			date;
	W_SELECT_DY		varchar2(100);
	W_開始変更時間	char(4);
	W_終了変更時間	char(4);
	W_曜日開始時間	char(4);
	W_曜日終了時間	char(4);
	W_月曜開始時間	char(4);
	W_月曜終了時間	char(4);
	W_火曜開始時間	char(4);
	W_火曜終了時間	char(4);
	W_水曜開始時間	char(4);
	W_水曜終了時間	char(4);
	W_木曜開始時間	char(4);
	W_木曜終了時間	char(4);
	W_金曜開始時間	char(4);
	W_金曜終了時間	char(4);
	W_土曜開始時間	char(4);
	W_土曜終了時間	char(4);
	W_日曜開始時間	char(4);
	W_日曜終了時間	char(4);
	W_標準開始時間	char(4);
	W_標準終了時間	char(4);
	W_サービス区分	char(2);
	W_開始時間		char(4);
	W_終了時間		char(4);
begin
	--	セキュリティシステム日付ＧＥＴ
	W_TODAY	:=	sfzzop903.main;
	P_処理日	:=	to_char(W_TODAY,'YYYYMMDD');
	--  セキュリティシステムの照会可否チェック（SFM_メニュー単位）
	P_判定結果	:=	sfzzop901.main('SFM_メニュー単位');
	if	P_判定結果	!=	sfzzop900.C_CHK_00	then
		return;
	end if;
	begin
		select	サービス開始変更時間,サービス終了変更時間,
				日曜サービス開始時間,日曜サービス終了時間,
				月曜サービス開始時間,月曜サービス終了時間,
				火曜サービス開始時間,火曜サービス終了時間,
				水曜サービス開始時間,水曜サービス終了時間,
				木曜サービス開始時間,木曜サービス終了時間,
				金曜サービス開始時間,金曜サービス終了時間,
				土曜サービス開始時間,土曜サービス終了時間,
				標準サービス開始時間,標準サービス終了時間,
				サービス区分
		into	W_開始変更時間,W_終了変更時間,
				W_日曜開始時間,W_日曜終了時間,
				W_月曜開始時間,W_月曜終了時間,
				W_火曜開始時間,W_火曜終了時間,
				W_水曜開始時間,W_水曜終了時間,
				W_木曜開始時間,W_木曜終了時間,
				W_金曜開始時間,W_金曜終了時間,
				W_土曜開始時間,W_土曜終了時間,
				W_標準開始時間,W_標準終了時間,
				P_判定結果
		from	SFM_メニュー単位
		where	メニュー単位連番 = P_メニュー単位連番
		and 	W_TODAY between trunc(有効開始日) and trunc(有効終了日);
	exception
		--select件数のチェック
		when no_data_found then
			P_判定結果	:=	sfzzop900.C_CHK_08;		--no_data_found
			return;
	end;

	-- 要求メニュー単位のサービス区分のチェック --
	if	P_判定結果	!=	sfzzop900.C_CHK_00	then
		return;
	end if;

	-- 曜日別指定サービス時間取得 --
	if to_char(W_TODAY,'dy','nls_date_language = JAPANESE') = '日' then
		W_曜日開始時間 := W_日曜開始時間;
		W_曜日終了時間 := W_日曜終了時間;
	elsif to_char(W_TODAY,'dy','nls_date_language = JAPANESE') = '月' then
		W_曜日開始時間 := W_月曜開始時間;
		W_曜日終了時間 := W_月曜終了時間;
	elsif to_char(W_TODAY,'dy','nls_date_language = JAPANESE') = '火' then
		W_曜日開始時間 := W_火曜開始時間;
		W_曜日終了時間 := W_火曜終了時間;
	elsif to_char(W_TODAY,'dy','nls_date_language = JAPANESE') = '水' then
		W_曜日開始時間 := W_水曜開始時間;
		W_曜日終了時間 := W_水曜終了時間;
	elsif to_char(W_TODAY,'dy','nls_date_language = JAPANESE') = '木' then
		W_曜日開始時間 := W_木曜開始時間;
		W_曜日終了時間 := W_木曜終了時間;
	elsif to_char(W_TODAY,'dy','nls_date_language = JAPANESE') = '金' then
		W_曜日開始時間 := W_金曜開始時間;
		W_曜日終了時間 := W_金曜終了時間;
	elsif to_char(W_TODAY,'dy','nls_date_language = JAPANESE') = '土' then
		W_曜日開始時間 := W_土曜開始時間;
		W_曜日終了時間 := W_土曜終了時間;
	end if;

	-- 有効なサービス開始時間を求める --
	if		W_開始変更時間	is not	null	then
		W_開始時間	:=	W_開始変更時間;
	elsif	W_曜日開始時間	is not	null	then
		W_開始時間	:=	W_曜日開始時間;
	elsif		W_標準開始時間	is not	null	then
		W_開始時間	:=	W_標準開始時間;
	end if;
	-- 有効なサービス終了時間を求める --
	if		W_終了変更時間	is not	null	then
		W_終了時間	:=	W_終了変更時間;
	elsif	W_曜日終了時間	is not	null	then
		W_終了時間	:=	W_曜日終了時間;
	elsif		W_標準開始時間	is not	null	then
		W_終了時間	:=	W_標準終了時間;
	end if;
	-- サービス時間のチェック --
	if		to_char(C_SYSDATE,'hh24mi')	between	W_開始時間	and	W_終了時間
	 or		to_char(to_number(to_char(C_SYSDATE,'hh24mi'))+2400,'FM0000')
										between	W_開始時間	and	W_終了時間	then
		P_判定結果	:=	sfzzop900.C_CHK_00;		--サービス中
	else
		P_判定結果	:=	sfzzop900.C_CHK_01;		--サービス時間外
	end if;
exception
	when	others	then
	P_判定結果	:=	sfzzop900.C_CHK_09;
end;
end	sfzzop001;
/

GRANT EXECUTE ON OSF001.SFZZOP001 TO OPL001;
GRANT EXECUTE ON OSF001.SFZZOP001 TO HXA011;
GRANT EXECUTE ON OSF001.SFZZOP001 TO OPL002;
GRANT EXECUTE ON OSF001.SFZZOP001 TO OHN001;

