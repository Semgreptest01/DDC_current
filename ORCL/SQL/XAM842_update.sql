set heading off
set feedback off
set trimspool on
set termout off
set echo off
set pages 0
set line 9999
spool ###OUT1###
update XAM842
set FLG = '0'
;
commit;
quit
