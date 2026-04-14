// *----------------------------------------------------------------------------
// * システム		:	共通
// * スクリプト		:	連動セレクト
// * 会社名or所属名	:	株式会社ヴィクサス
// * 作成日			:	2006/03/28
// * 作成者			:	Hirata
// * *** 修正履歴 ***
// * No.	Date		Author		Description
// *----------------------------------------------------------------------------
// 引数に渡すoptnArryは以下のように定義する。
// 
// var mmArry = new Array();
// 
// 配列の第一要素は必ずブランク行とする。
//	mmArry.push( new Array( new Array( "" ), new Array( new Array( "", "" ) ) ) );
// 引数のdsplyBlnkRwFlgで表示の有無を切り替える。
// 
// 以降はキーの値の配列、オプションの配列を要素とする配列とする。
// オプションの配列の要素はvalue、textの順の配列とする。
// mmArry.push( new Array( new Array( "0" ), new Array(
// 		new Array( "0", "MM0" )
// 	,	new Array( "1", "MM1" )
// ) ) );
// mmArry.push( new Array( new Array( "1" ), new Array(
// 		new Array( "2", "MM2" )
// 	,	new Array( "3", "MM3" )
// ) ) );
/************************************************************
 *関数名：stSlctOptn( slct, kyArry, dsplyBlnkRwFlg, optnArry, blnkWldCrdFlg )
 *機能　：セレクトオプションセット
 ***********************************************************/
function stSlctOptn( slct, kyArry, dsplyBlnkRwFlg, optnArry, blnkWldCrdFlg ) {
	if ( blnkWldCrdFlg == undefined ) {
		blnkWldCrdFlg = false;
	}
	var addCnt = 0;
	
	while ( 1 < slct.options.length ) {
		slct.options[ slct.options.length - 1 ] = null;
	}
	
	if ( dsplyBlnkRwFlg ) {
		slct.options[ addCnt++ ] = new Option( optnArry[ 0 ][ 1 ][ 0 ][ 1 ], optnArry[ 0 ][ 1 ][ 0 ][ 0 ] );
	} else {
		slct.options[ 0 ] = new Option( "", "" );
	}
	
	var evryKyBlnkFlg = false;
	if ( !blnkWldCrdFlg ) {
		evryKyBlnkFlg = true;
		for ( var i = 0; i < kyArry.length; i++ ) {
			if ( kyArry[ i ] != "" ) {
				evryKyBlnkFlg = false;
				break;
			}
		}
	}
	if ( blnkWldCrdFlg || !evryKyBlnkFlg ) {
		var addFlg = true;
		for ( var i = 1; i < optnArry.length; i++ ) {
			for ( var j = 0; j < kyArry.length; j++ ) {
				if (
					!( blnkWldCrdFlg && kyArry[ j ] == "" ) &&
					kyArry[ j ] != optnArry[ i ][ 0 ][ j ]
				) {
					addFlg = false;
					break;
				}
			}
			if ( addFlg ) {
				for ( var j = 0; j < optnArry[ i ][ 1 ].length; j++ ) {
					slct.options[ addCnt++ ] = new Option( optnArry[ i ][ 1 ][ j ][ 1 ], optnArry[ i ][ 1 ][ j ][ 0 ] );
				}
			}
			addFlg = true;
		}
	}
}
/***********************************************************
 *関数名：clrSlctOptn( slct, dsplyBlnkRwFlg, optnArry )
 *機能　：セレクトオプションクリア
 ***********************************************************/
function clrSlctOptn( slct, dsplyBlnkRwFlg, optnArry ) {
	while ( 1 < slct.options.length ) {
		slct.options[ slct.options.length - 1 ] = null;
	}
	
	if ( dsplyBlnkRwFlg ) {
		if ( optnArry != undefined ) {
			slct.options[ 0 ] = new Option( optnArry[ 0 ][ 1 ][ 0 ][ 1 ], optnArry[ 0 ][ 1 ][ 0 ][ 0 ] );
		} else {
			slct.options[ 0 ] = new Option( "", "" );
		}
	}
}
