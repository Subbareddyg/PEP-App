set echo on
set time on
set timing on

spool 25-ALTER_PET_ATTRIBUTE_TABLE_DDL.out

show user;


select to_char (sysdate, 'Dy DD-MON-YYYY @ HH24:MI:SS')
                as " Job Began at:" from dual;

host hostname;


--- ******** SCRIPT - BEGIN SQL *********************


set define off;



alter table
  "VENDORPORTAL"."PET_ATTRIBUTE"
drop
   (CREATED_BY, CREATED_DATE,UPDATED_BY,UPDATED_DATE);
   
alter table "VENDORPORTAL"."PET_ATTRIBUTE"  add (DISPLAY_ORDER  VARCHAR2(40 BYTE));
alter table "VENDORPORTAL"."PET_ATTRIBUTE"  add (ATTRIBUTE_PATH   VARCHAR2(150 BYTE));
alter table "VENDORPORTAL"."PET_ATTRIBUTE"  add (CREATED_BY  VARCHAR2(20 BYTE));
alter table "VENDORPORTAL"."PET_ATTRIBUTE"  add (CREATED_DATE   TIMESTAMP (6));
alter table "VENDORPORTAL"."PET_ATTRIBUTE"  add (UPDATED_BY   VARCHAR2(20 BYTE));
alter table "VENDORPORTAL"."PET_ATTRIBUTE"  add (UPDATED_DATE   TIMESTAMP (6));


  

 
COMMIT;

--- ******** DATABASE CONTROLS - AFTER   SCRIPT ******

--- ****************************************************
select to_char (sysdate, 'Dy DD-MON-YYYY @ HH24:MI:SS')
                as " Job Ended at: " from dual;

exit;