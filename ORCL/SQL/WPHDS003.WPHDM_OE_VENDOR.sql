set heading off
set feedback off
set trimspool on
set termout off
set echo off
set pages 0
set line 9999
spool ###OUT1###
select 
VENDER_CD|| Chr(44) ||
to_char(VALD_ST_DATE,'yyyymmdd')|| Chr(44) ||
to_char(VALD_ED_DATE,'yyyymmdd')|| Chr(44) ||
DLVR_CLS
from
WPHDS003.WPHDM_OE_VENDOR
where
PERIOD_CLS = '2'         And 
DLVR_CLS = '0'           And
DLVR_CENTR_CLS = '0'     And
DLVR_CENTR_CD!='000000'  And 
PROC_CLS!='7' 
;
spool off
;
quit
