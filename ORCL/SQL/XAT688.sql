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
TRANSLATE(NVL(TO_CHAR(substr(BGN_WEEK,1,4)),'_')||'/'||NVL(TO_CHAR(substr(BGN_WEEK,5,2)),'_')||'/'||NVL(TO_CHAR(substr(BGN_WEEK,7,2)),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(substr(CLNDR_DT,1,4)),'_')||'/'||NVL(TO_CHAR(substr(CLNDR_DT,5,2)),'_')||'/'||NVL(TO_CHAR(substr(CLNDR_DT,7,2)),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(DAY_NO),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(DAY),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||''''||
TRANSLATE(NVL(TO_CHAR(SKU_DPT_ORGAREA_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SKU_DPT_ORGAREA_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SKU_DPT_AREA_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SKU_DPT_AREA_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||''''||
TRANSLATE(NVL(TO_CHAR(DLVR_CENTR_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(DLVR_CENTR_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||''''||
TRANSLATE(NVL(TO_CHAR(DEPT_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(DEPT_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||''''||
TRANSLATE(NVL(TO_CHAR(CLASS_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(CLASS_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||''''||
TRANSLATE(NVL(TO_CHAR(SUBCLASS_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SUBCLASS_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||''''||
TRANSLATE(NVL(TO_CHAR(REP_SKU_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||''''||
TRANSLATE(NVL(TO_CHAR(JAN_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SKU_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SALE_CNT_11_DGT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(PRCHS_CNT_11_DGT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(RTN_GDS_CNT_11_DGT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(ABNDN_CNT_11_DGT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(STCK_CNT_11_DGT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(ODR_CNT_11_DGT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(ODR_STR_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(ODR_PSSBL_STR_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(HNDLG_STR_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(MNGMNT_REG_STR_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(substr(CUT_DT,1,4)),'_')||'/'||NVL(TO_CHAR(substr(CUT_DT,5,2)),'_')||'/'||NVL(TO_CHAR(substr(CUT_DT,7,2)),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(substr(NEW_SKU_ADPT_DT,1,4)),'_')||'/'||NVL(TO_CHAR(substr(NEW_SKU_ADPT_DT,5,2)),'_')||'/'||NVL(TO_CHAR(substr(NEW_SKU_ADPT_DT,7,2)),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(GNRL_WTHR_COND_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(ATMSPHR_TMPRTR),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(HUMIDITY),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(RAINFALL_CNT),'_'),' '||chr(9)||chr(10)||chr(13),'___')
from
XAT688
;
spool off
;
quit
