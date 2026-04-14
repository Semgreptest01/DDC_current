// *----------------------------------------------------------------------------
// * システム		:	共通 
// * スクリプト		:	都道府県データ
// * 会社名or所属名	:	株式会社ヴィクサス
// * 作成日			:	2012/12/25
// * 作成者			:	Y.Takabayashi
// * *** 修正履歴 ***
// * No.	Date		Author		Description
// *----------------------------------------------------------------------------
var TODOFUKEN_SU         = 47;
//------------------------------------
//地方_都道府県・配列
//------------------------------------
var todofukenList = new Array();
todofukenList[0] = "001,01";							// 北海道地方
todofukenList[1] = "002,02,03,04,05,06,07";				// 東北地方
todofukenList[2] = "003,08,09,10,11,12,13,14";			// 関東地方
todofukenList[3] = "004,15,16,17,18,19,20,21,22,23";	// 中部地方
todofukenList[4] = "005,24,25,26,27,28,29,30";			// 近畿地方
todofukenList[5] = "006,31,32,33,34,35";				// 中国地方
todofukenList[6] = "007,36,37,38,39";					// 四国地方
todofukenList[7] = "008,40,41,42,43,44,45,46";			// 九州地方,  
todofukenList[8] = "009,47";							// 沖縄地方,  
//------------------------------------
// 地方名・連想配列
//------------------------------------
var chihouStr		=	new Array();
chihouStr["001"]        = "北海道地方";
chihouStr["002"]        = "東北地方";
chihouStr["003"]        = "関東地方";
chihouStr["004"]        = "中部地方";
chihouStr["005"]        = "近畿地方";
chihouStr["006"]        = "中国地方";
chihouStr["007"]        = "四国地方";
chihouStr["008"]        = "九州地方";
chihouStr["009"]        = "沖縄地方";
//------------------------------------
//都道府県・連想配列
//------------------------------------
var todofukenStr        =	new Array();
todofukenStr[ "01" ]	=	"北海道";
todofukenStr[ "02" ]	=	"青森";
todofukenStr[ "03" ]	=	"岩手";
todofukenStr[ "04" ]	=	"宮城";
todofukenStr[ "05" ]	=	"秋田";
todofukenStr[ "06" ]	=	"山形";
todofukenStr[ "07" ]	=	"福島";
todofukenStr[ "08" ]	=	"茨城";
todofukenStr[ "09" ]	=	"栃木";
todofukenStr[ "10" ]	=	"群馬";
todofukenStr[ "11" ]	=	"埼玉";
todofukenStr[ "12" ]	=	"千葉";
todofukenStr[ "13" ]	=	"東京";
todofukenStr[ "14" ]	=	"神奈川";
todofukenStr[ "15" ]	=	"新潟";
todofukenStr[ "16" ]	=	"富山";
todofukenStr[ "17" ]	=	"石川";
todofukenStr[ "18" ]	=	"福井";
todofukenStr[ "19" ]	=	"山梨";
todofukenStr[ "20" ]	=	"長野";
todofukenStr[ "21" ]	=	"岐阜";
todofukenStr[ "22" ]	=	"静岡";
todofukenStr[ "23" ]	=	"愛知";
todofukenStr[ "24" ]	=	"三重";
todofukenStr[ "25" ]	=	"滋賀";
todofukenStr[ "26" ]	=	"京都";
todofukenStr[ "27" ]	=	"大阪";
todofukenStr[ "28" ]	=	"兵庫";
todofukenStr[ "29" ]	=	"奈良";
todofukenStr[ "30" ]	=	"和歌山";
todofukenStr[ "31" ]	=	"鳥取";
todofukenStr[ "32" ]	=	"島根";
todofukenStr[ "33" ]	=	"岡山";
todofukenStr[ "34" ]	=	"広島";
todofukenStr[ "35" ]	=	"山口";
todofukenStr[ "36" ]	=	"徳島";
todofukenStr[ "37" ]	=	"香川";
todofukenStr[ "38" ]	=	"愛媛";
todofukenStr[ "39" ]	=	"高知";
todofukenStr[ "40" ]	=	"福岡";
todofukenStr[ "41" ]	=	"佐賀";
todofukenStr[ "42" ]	=	"長崎";
todofukenStr[ "43" ]	=	"熊本";
todofukenStr[ "44" ]	=	"大分";
todofukenStr[ "45" ]	=	"宮崎";
todofukenStr[ "46" ]	=	"鹿児島";
todofukenStr[ "47" ]	=	"沖縄";
/***********************************************************
 *関数名：getTodofukenList( id )
 *機能　：都道府県リストを取得する（idx:0...8）
 ***********************************************************/
function getTodofukenList( idx ) {
	var str = "";
	if ( todofukenList[ idx ] != undefined ) {
		str = todofukenList[ idx ];
	}
	return str;
}
/***********************************************************
 *関数名：getTodofuken( id )
 *機能　：地方名を取得する
 ***********************************************************/
function getChihou( id ) {
	var str = "";
	if ( chihouStr[ id ] != undefined ) {
		str = chihouStr[ id ];
	}
	return str;
}
/***********************************************************
 *関数名：getTodofuken( id )
 *機能　：都道府県名を取得する
 ***********************************************************/
function getTodofuken( id ) {
	var str = "";
	if ( todofukenStr[ id ] != undefined ) {
		str = todofukenStr[ id ];
	}
	return str;
}
/***********************************************************
 *関数名：substituteMsg( str, addMsgs )
 *機能　：メッセージに配列の文字列を代入
 ***********************************************************/
function substituteMsg( str, addMsgs ) {
	if ( addMsgs != undefined && addMsgs != null ) {
		var reg;
		var str1 = "";
		var str2 = "";
		for ( var i = 0; i < addMsgs.length; i++ ) {
			reg = new RegExp( "\\[&" + i + "\\]" );
			if ( str.match( reg ) ) {
				str = RegExp.leftContext + addMsgs[i] + RegExp.rightContext;
			}
		}
	}
	return str;
}
/***********************************************************
 *関数名：dspMsg( id, addMsgs )
 *機能　：メッセージ出力
 ***********************************************************/
function dspMsg( id, addMsgs ) {
	alert( substituteMsg( getMsg( id ), addMsgs ) );
	return true;
}
/***********************************************************
 *関数名：cfmMsg( id, addMsgs )
 *機能　：確認メッセージ出力
 ***********************************************************/
function cfmMsg( id, addMsgs ) {
	return confirm( substituteMsg( getMsg( id ), addMsgs ) );
}
