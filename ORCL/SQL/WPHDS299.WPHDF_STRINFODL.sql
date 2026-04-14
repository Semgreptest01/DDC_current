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
TRANSLATE(NVL(TO_CHAR(STR_KEY),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(WEATHER_CD),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(TD_TOTAL_PRCPN),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(TD_TOTAL_HUMIDITY),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(TD_HIGH_TEMP),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(TD_LOW_TEMP),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(TD_AVG_HIGH_TEMP),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(TD_AVG_LOW_TEMP),'_'),' '||chr(9)||chr(10)||chr(13),'___')||' '||
TRANSLATE(NVL(TO_CHAR(LOAD_DATE,'YYYYMMDD'),'_'),' '||chr(9)||chr(10)||chr(13),'___')
from
WPHDS299.WPHDF_STRINFODL
where
DATE_KEY = ###DATE_KEY1###
;
spool off
;
quit
