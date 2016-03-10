set echo on
set time on
set timing on

spool 25-DROP_PEP_TABLES_DDL.out

show user;


select to_char (sysdate, 'Dy DD-MON-YYYY @ HH24:MI:SS')
                as " Job Began at:" from dual;

host hostname;


--- ******** SCRIPT - BEGIN SQL *********************


set define off;

drop table "VENDORPORTAL"."CONFIG" cascade constraints;
drop table "VENDORPORTAL"."PEP_ROLE" cascade constraints;
drop table "VENDORPORTAL"."PET_ATTRIBUTE" cascade constraints;
drop table "VENDORPORTAL"."PEP_DEPARTMENT" cascade constraints;
drop table "VENDORPORTAL"."EXTERNAL_USER" cascade constraints;
drop table "VENDORPORTAL"."BELK_USER" cascade constraints;

 
COMMIT;

--- ******** DATABASE CONTROLS - AFTER   SCRIPT ******

--- ****************************************************
select to_char (sysdate, 'Dy DD-MON-YYYY @ HH24:MI:SS')
                as " Job Ended at: " from dual;

exit;