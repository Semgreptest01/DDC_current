set heading off
set feedback off
set trimspool on
set termout off
set echo off
set pages 0
set line 9999
spool ###OUT1###
select
TRANSLATE(NVL(TO_CHAR(DATE_KEY),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(DAY,'YYYYMMDD'),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(DAY_FLG),'_'),' '||chr(9)||chr(10)||chr(13),'___')
from
WPHDS299.WPHDM_ALL_DATES
;
spool off
;
quit
