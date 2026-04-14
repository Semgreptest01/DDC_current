set heading off
set feedback off
set trimspool on
set termout off
set echo off
set pages 0
set line 9999
spool ###OUT1###
select
TRANSLATE(NVL(TO_CHAR(STR_KEY),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(STR_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(STR_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(STR_KN_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SV_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SV_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SV_KN_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(DR_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(DR_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(DR_KN_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SUB_BRANCH_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SUB_BRANCH_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SUB_BRANCH_KN_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(BRANCH_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(BRANCH_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(BRANCH_KN_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SHI_SYA_KEI_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SHI_SYA_KEI_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SHI_SYA_KEI_KN_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SKU_DEPT_AREA_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SKU_DEPT_AREA_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SKU_DEPT_AREA_KN_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SKU_AREA_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SKU_AREA_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SKU_AREA_KN_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(GROSS_AREA_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(GROSS_AREA_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(GROSS_AREA_KN_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SAKE_LCNS_STR_FLG),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(SAKE_LCNS_GET_DATE,'YYYYMMDD'),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(TOBACCO_LCNS_STR_FLG),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(TOBACCO_LCNS_GET_DATE,'YYYYMMDD'),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(DLVY_AREA_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(COMPANY_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(HQ_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(STR_CLSFN_FLG),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(BSNS_COND_FLG),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(FCRC_FLG),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(JOURNAL_FLG),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(AREA_SORT_KEY),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(ORGANISATIONAL_SORT_KEY),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(LATEST_FLG),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(ST_DATE,'YYYYMMDD'),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(END_DATE,'YYYYMMDD'),'_'),' '||chr(9)||chr(10)||chr(13),'___')
from
WPHDS299.WPHDM_STR
;
spool off
;
quit
