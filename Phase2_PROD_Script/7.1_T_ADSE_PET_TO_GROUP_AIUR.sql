create or replace TRIGGER VENDORPORTAL."T_ADSE_PET_TO_GROUP_AIUR"
FOR UPDATE OF PET_STATE,PET_STYLE_STATE ON VENDORPORTAL.ADSE_PET_CATALOG
WHEN (NEW.ENTRY_TYPE = 'Style')
    COMPOUND TRIGGER
L_sql_code                 INT :=0;
L_sql_err                  VARCHAR2(2000) := NULL;
L_STYLE_MDMID  ADSE_PET_CATALOG.MDMID%type;
STATUS          			VARCHAR2(1) :='P';
CHILD_GROUP_OPEN          	VARCHAR2(1):='';
CHILD_EXIST          		VARCHAR2(1):='';
CHILD_STYLE_OPEN          	VARCHAR2(1):='';
Updatesqlquery             VARCHAR2(600);
L_PET_STATE ADSE_PET_CATALOG.PET_STATE%type;
L_PET_STYLE_STATE ADSE_PET_CATALOG.PET_STYLE_STATE%type;


CURSOR GROUP_MDMID IS 
SELECT 
MDMID FROM ADSE_GROUP_CHILD_MAPPING WHERE COMPONENT_STYLE_ID = L_STYLE_MDMID;

AFTER EACH ROW
IS BEGIN
L_PET_STATE :=:NEW.PET_STATE;
L_PET_STYLE_STATE:=:NEW.PET_STYLE_STATE;
L_STYLE_MDMID:=:NEW.MDMID;
END AFTER EACH ROW;

AFTER STATEMENT
IS
BEGIN



IF (L_PET_STATE='01' OR L_PET_STYLE_STATE='Y') THEN 
 EXECUTE IMMEDIATE 'UPDATE ADSE_GROUP_CATALOG SET PET_DISPLAY_FLAG =''O'' WHERE MDMID IN (SELECT MDMID FROM ADSE_GROUP_CHILD_MAPPING WHERE COMPONENT_STYLE_ID ='''||L_STYLE_MDMID||''')';
 ELSE
 
 	 FOR GROUP_MDMID_ID IN  GROUP_MDMID
  LOOP
 
 BEGIN
		SELECT 'Y' INTO CHILD_EXIST
		FROM ADSE_GROUP_CHILD_MAPPING
		WHERE MDMID = GROUP_MDMID_ID.MDMID AND ROWNUM=1;
	EXCEPTION
		WHEN NO_DATA_FOUND THEN
		CHILD_EXIST :='N';
	END; 
  -- INSERT INTO TEST_QUERY(TEXT) VALUES('CHILD_EXIST'||CHILD_EXIST);
    BEGIN
    SELECT 'Y' INTO CHILD_GROUP_OPEN 
	FROM ADSE_GROUP_CATALOG 
	WHERE MDMID IN ( 
		SELECT COMPONENT_GROUPING_ID
		FROM ADSE_GROUP_CHILD_MAPPING
		WHERE MDMID = GROUP_MDMID_ID.MDMID
					) 
		AND (GROUP_OVERALL_STATUS_CODE ='01' OR PET_DISPLAY_FLAG ='O') AND ROWNUM=1; -- 'O' -Open Child exists
    EXCEPTION
		WHEN NO_DATA_FOUND THEN
		CHILD_GROUP_OPEN :='N';
	END;
	--INSERT INTO TEST_QUERY(TEXT) VALUES('CHILD_GROUP_OPEN'||CHILD_GROUP_OPEN);
	BEGIN
	SELECT 'Y' INTO CHILD_STYLE_OPEN 
	FROM ADSE_PET_CATALOG 
	WHERE MDMID IN ( 
		SELECT COMPONENT_STYLE_ID
		FROM ADSE_GROUP_CHILD_MAPPING
		WHERE MDMID = GROUP_MDMID_ID.MDMID
					) 
		AND ( PET_STYLE_STATE ='Y' OR PET_STATE='01')  AND ROWNUM=1;
	EXCEPTION
		WHEN NO_DATA_FOUND THEN
		CHILD_STYLE_OPEN :='N';
	END;
  --INSERT INTO TEST_QUERY(TEXT) VALUES('CHILD_STYLE_OPEN'||CHILD_STYLE_OPEN);
	STATUS := 'P';
	IF (CHILD_EXIST='Y') THEN
		IF (CHILD_GROUP_OPEN='Y' OR CHILD_STYLE_OPEN = 'Y') THEN
			STATUS := 'O';
		ELSE
			STATUS := 'C';
		END IF;
	END IF;
 -- INSERT INTO TEST_QUERY(TEXT) VALUES('STATUS'||STATUS);
    Updatesqlquery:= 'UPDATE Adse_GROUP_Catalog  Set PET_DISPLAY_FLAG = '''|| STATUS || ''' WHERE MDMID = '''||GROUP_MDMID_ID.MDMID||'''';
    --INSERT INTO TEST_QUERY(TEXT) VALUES('Updatesqlquery'||Updatesqlquery);
    EXECUTE Immediate Updatesqlquery;
 END LOOP;
 
END IF;

  EXCEPTION
  WHEN OTHERS THEN
    L_sql_code := SQLCODE;
    L_sql_err  := SUBSTR(SQLERRM,1,200);
    DBMS_OUTPUT.PUT_LINE ('Trigger T_ADSE_PET_TO_GROUP_AIUR Error = '||L_sql_code || L_sql_err);
	raise_application_error(-20000, 'Fatal error from trigger T_ADSE_PET_TO_GROUP_AIUR '||L_sql_code || L_sql_err);
  END AFTER STATEMENT;
END ;