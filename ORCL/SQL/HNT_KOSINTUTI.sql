set heading off
set feedback off
set trimspool on
set termout off
set echo off
set pages 0
set line 9999
spool ###OUT1###
select
TRANSLATE(NVL(TO_CHAR(データ区分),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(最終更新日,'YYYYMMDD'),'_'),' '||chr(9)||chr(10)||chr(13),'___')
from
HNT_更新通知
;
spool off
;
quit
