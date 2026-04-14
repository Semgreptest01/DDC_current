set heading off
set feedback off
set trimspool on
set termout off
set echo off
set pages 0
set line 9999
spool ###OUT1###
select
TRANSLATE(NVL(TO_CHAR(PG_NM),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(FLG),'_'),' '||chr(9)||chr(10)||chr(13),'___')
from
XAM842
;
spool off
;
quit
