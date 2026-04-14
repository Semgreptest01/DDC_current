set heading off
set feedback off
set trimspool on
set termout off
set echo off
set pages 0
set line 9999
spool ###OUT1###
select
TRANSLATE(NVL(TO_CHAR(SEND_NO),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(substr(CLNDR_DT,1,4)),'_')||'/'||NVL(TO_CHAR(substr(CLNDR_DT,5,2)),'_')||'/'||NVL(TO_CHAR(substr(CLNDR_DT,7,2)),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SKU_DPT_ORGAREA_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(DLVR_CENTR_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||''''||
TRANSLATE(NVL(TO_CHAR(REP_SKU_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||''''||
TRANSLATE(NVL(TO_CHAR(JAN_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SKU_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SKU_DIV_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(DEPT_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(CLASS_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SUBCLASS_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(ODR_PSSBL_STR_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(BFR_DT_STCK_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(RCPT_GDS_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SHIPNG_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(MKR_RTN_GDS_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(ESTMT_STCK_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(NXT_RCPT_GDS_EXPCTD_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(substr(NXT_RCPT_GDS_EXPCTD_DT,1,4)),'_')||'/'||NVL(TO_CHAR(substr(NXT_RCPT_GDS_EXPCTD_DT,5,2)),'_')||'/'||NVL(TO_CHAR(substr(NXT_RCPT_GDS_EXPCTD_DT,7,2)),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(BFR_WK_DGST_DT_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(CRNT_WK_DGST_DT_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(STR_STCK_DGST_DT_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(STCK_CNT_11_DGT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(BFR_SALE_CNT_DT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(CRNT_SALE_CNT_DT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(BFR_PSAD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(CRNT_PSAD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(HNDLG_CVR_RATE),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(substr(CUT_DT,1,4)),'_')||'/'||NVL(TO_CHAR(substr(CUT_DT,5,2)),'_')||'/'||NVL(TO_CHAR(substr(CUT_DT,7,2)),'_'),' '||chr(9)||chr(10)||chr(13),'___')
from
XAT690
;
spool off
;
quit
