// *----------------------------------------------------------------------------
// * システム		:	共通
// * スクリプト		:	定数
// * 会社名or所属名	:	株式会社ヴィクサス
// * 作成日			:	2006/03/03
// * 作成者			:	Hirata
// * *** 修正履歴 ***
// * No.	Date		Author		Description
// *----------------------------------------------------------------------------
var FLG_ON					=	"1";
var FLG_OFF					=	"0";

var WINDOW_WIDTH			=	1024;
var WINDOW_HEIGHT			=	740;
var WINDOW_LEFT				=	0;
var WINDOW_TOP				=	0;
var SUB_WINDOW_WIDTH		=	750;
var SUB_WINDOW_HEIGHT		=	600;
var SUB_WINDOW_LEFT			=	0;
var SUB_WINDOW_TOP			=	0;
var HELP_WINDOW_WIDTH		=	800;
var HELP_WINDOW_HEIGHT		=	600;
var HELP_WINDOW_LEFT		=	0;
var HELP_WINDOW_TOP			=	0;
var OPEN_WINDOW_WIDTH		=	1024;//0;
var OPEN_WINDOW_HEIGHT		=	740;//0;
var OPEN_WINDOW_VISIBLE_FLG	=	false;//true;
var OPEN_WINDOW_PRMTR		=	"width=" + OPEN_WINDOW_WIDTH + ", height=" + OPEN_WINDOW_HEIGHT + ", top=0, left=" + ( OPEN_WINDOW_VISIBLE_FLG ? "0" : "1024" ) + ", scrollbars=yes, resizable=yes, titlebar=no, status=yes";
var RMV_S_A_SRVLT			=	"RemoveSessionAttribute";
var RMV_S_A_SRVLT_AJAX		=	"RemoveSessionAttributeAjax";
var USE_AJAX_FR_RMV_S_A		=	false;
var CLS_RMV_S_A_SCRN		=	true;
var TIMER_INTERVAL			=	2000;
var MSG_BOX_LN_FD_CD		=	"<br>";
var DSP_LN_FD_CD			=	"\n";
var NRML_INPT_COLOR			=	"";
var CHNG_INPT_COLOR			=	"palegreen";
var ERR_INPT_COLOR			=	"pink";
var DEL_INPT_COLOR			=	"khaki";
var SENTENCE_MAX			=	100;
var LINE_MAX				=	100;
var HELP_DIR_PATH			=	"contdata/help";
var IMG_DIR_PATH			=	"contdata/img";
var MIN_YEAR				=	1970;
var MAX_YEAR				=	2100;
var DATE_SLASH_FLG			=	true;
var ALLW_HLF_WDTH_KANA_FLG	=	true;
var RPLC_ENTR_WTH_TB_FLG	=	false;
var DO_RMV_SSSN_ATTRBT		=	false;
var DSABL_CNTXT_MN			=	true;
