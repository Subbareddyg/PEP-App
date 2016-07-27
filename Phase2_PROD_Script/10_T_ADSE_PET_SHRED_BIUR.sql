create or replace TRIGGER VENDORPORTAL."T_ADSE_PET_SHRED_BIUR"
FOR INSERT  OR UPDATE OF XML_DATA ON VENDORPORTAL.ADSE_PET_CATALOG 
    COMPOUND TRIGGER
L_sql_code                 INT :=0;
L_sql_err                  VARCHAR2(2000) := NULL;
L_XML_DATA ADSE_PET_CATALOG.XML_DATA%type :=:New.XML_DATA;
L_PETEXISTS ADSE_ITEM_CATALOG.PETEXISTS%type :='Y';

CURSOR SHREDED_ROW IS 
SELECT PET_XML.COLOR_CODE,PET_XML.COLOR_DESC,PET_XML.PRODUCT_NAME
FROM 
			XMLTABLE( 'let                                                                    
	  $colordesc:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,     
	  $colorCode:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code      
	  return                                                                  
	  <COLOR>                                                                 
	  <COLOR_CODE>{$colorCode}</COLOR_CODE>                                   
	  <COLOR_DESC>{$colordesc}</COLOR_DESC>                                   
	  <PRODUCT_NAME>{/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>  
	  </COLOR>'                                                               
	  passing L_XML_DATA Columns COLOR_CODE VARCHAR2(20) path '/COLOR/COLOR_CODE',  
	  COLOR_DESC                              VARCHAR2(100) path '/COLOR/COLOR_DESC'  
	  , PRODUCT_NAME                          VARCHAR2(1000) path '/COLOR/PRODUCT_NAME' ) PET_XML;

BEFORE EACH ROW
IS
BEGIN
  -- Data shredding for Group catalog

    IF (:NEW.ENTRY_TYPE in ('Style','StyleColor')) THEN
			 FOR SHRED IN  SHREDED_ROW
  LOOP
		IF (:New.DELETED_FLAG = 'true') THEN
			L_PETEXISTS := 'N';
		END IF;
		
      EXECUTE IMMEDIATE 'UPDATE ADSE_ITEM_CATALOG SET PETEXISTS ='''||L_PETEXISTS||''',PRODUCT_NAME= '''||REPLACE(SHRED.PRODUCT_NAME,'''','''''')||''', COLOR_CODE='''||SHRED.COLOR_CODE||''',COLOR_NAME='''||REPLACE(SHRED.COLOR_DESC,'''','''''')||''' WHERE MDMID = :1' USING :NEW.MDMID; 
        END LOOP;
    ELSE
    IF INSERTING THEN 
      EXECUTE IMMEDIATE 'UPDATE ADSE_ITEM_CATALOG SET PETEXISTS =''Y'' WHERE MDMID = :1' USING :NEW.MDMID; 
    END IF;
    END IF;
     

  

  EXCEPTION
  WHEN OTHERS THEN
    L_sql_code := SQLCODE;
    L_sql_err  := SUBSTR(SQLERRM,1,200);
    DBMS_OUTPUT.PUT_LINE ('Trigger T_ADSE_PET_SHRED_BIUR Error = '||L_sql_code || L_sql_err);
	raise_application_error(-20000, 'Fatal error from trigger T_ADSE_PET_SHRED_BIUR '||L_sql_code || L_sql_err);
  END BEFORE EACH ROW;
END ;