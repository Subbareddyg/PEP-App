create or replace TRIGGER VENDORPORTAL."T_ADSE_ITEM_SHRED_BIUR"
FOR INSERT  OR UPDATE OF XML_DATA ON VENDORPORTAL.ADSE_ITEM_CATALOG 
    COMPOUND TRIGGER
L_sql_code                 INT :=0;
L_sql_err                  VARCHAR2(2000) := NULL;
L_XML_DATA ADSE_ITEM_CATALOG.XML_DATA%type :=:New.XML_DATA;

CURSOR SHREDED_ROW IS 
SELECT ITEM_XML.COLOR_CODE,ITEM_XML.COLOR_NAME,ITEM_XML.SIZEDESC,ITEM_XML.SIZE_CODE,ITEM_XML.PRODUCTNAME
FROM 
		XMLTABLE('let
    $colorcode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq "COLOR"]/Code,
		$colorname:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq "COLOR"]/Vendor_Description,
		$size:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq "SIZE"]/Vendor_Description,
		$sizeCode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq "SIZE"]/Code,
		$name:=/pim_entry/entry/Item_Ctg_Spec/Description/Short
		return                                                                                       
		    <SPEC>                                                                                   
		        <COLOR_CODE>{$colorcode}</COLOR_CODE>                                                
		        <COLOR_NAME>{$colorname}</COLOR_NAME>                                                
		        <SIZE>{$size}</SIZE>                                                                 
		        <SIZE_CODE>{$sizeCode}</SIZE_CODE>                                                                 
		        <PRODUCTNAME>{$name}</PRODUCTNAME>                                                                 
		    </SPEC>'                                                                                 
		passing L_XML_DATA                                                                        
		Columns                                                                                      
		    COLOR_CODE VARCHAR2(20) path '/SPEC/COLOR_CODE',                                          
		    COLOR_NAME VARCHAR2(100) path '/SPEC/COLOR_NAME',                                         
		    SIZEDESC VARCHAR2(20) path '/SPEC/SIZE',                                                 
		    SIZE_CODE VARCHAR2(100) path '/SPEC/SIZE_CODE',                                           
		    PRODUCTNAME VARCHAR2(1000) path '/SPEC/PRODUCTNAME') ITEM_XML;

BEFORE EACH ROW
IS
BEGIN
  -- Data shredding for Item catalog
	 
    IF (:NEW.ENTRY_TYPE ='SKU') THEN
		FOR SHRED IN  SHREDED_ROW
		LOOP
		:New.PRODUCT_NAME:=SHRED.PRODUCTNAME; 
		:New.COLOR_CODE := SHRED.COLOR_CODE;
		:New.COLOR_NAME := SHRED.COLOR_NAME;
		:New.SIZE_CODE := SHRED.SIZE_CODE;
		:New.SIZE_NAME := SHRED.SIZEDESC;
		END LOOP;
    END IF; 
  

  EXCEPTION
  WHEN OTHERS THEN
    L_sql_code := SQLCODE;
    L_sql_err  := SUBSTR(SQLERRM,1,200);
    DBMS_OUTPUT.PUT_LINE ('Trigger T_ADSE_ITEM_SHRED_BIUR Error = '||L_sql_code || L_sql_err);
	raise_application_error(-20000, 'Fatal error from trigger T_ADSE_ITEM_SHRED_BIUR '||L_sql_code || L_sql_err);
  END BEFORE EACH ROW;
END ;