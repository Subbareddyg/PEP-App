package com.belk.pep.constants;

import org.apache.log4j.Logger;


/**
 * The Class XqueryConstants.
 * 
 * 
 */
public class XqueryConstants {

    /**
     * Instantiates a new xquery constants.
     */
    public XqueryConstants() {

    }
    
    private final static Logger LOGGER =
        Logger.getLogger(XqueryConstants.class.getName());


    /**
     * This query is to get the styleinfodetails
     * @param orinNo
     * @return
     */
    public String getStyleInfoDetails(){
        final String STYLE_INFO_QUERY =  "  with    " 
            +"  INPUT( ORIN) as  " 
            +"  (Select :orinNo FROM dual),   " 
            +"  TypeIndex(indx, typ) as  " 
            +"  ((Select 1 indx, 'Style' typ  from  dual)  " 
            +"  union (select 2 indx, 'StyleColor' typ from dual)  " 
            +"  union (select 3 indx, 'SKU' typ from dual) " 
            +"  union (select 4 indx, 'Complex Pack' typ from dual) " 
            +"  union (select 5 indx, 'PackColor' typ from dual) " 
            +"  ),   "  
            +"  	  Items AS                                                                                                   "
            +"  	  (SELECT                                                                                                    "
            +"  		aic.MDMID,                                                                                               "
            +"  		aic.XML_DATA,                                                                                            "
            +"  		aic.ENTRY_TYPE,                                                                                          "
            +"  		descr DESCRIPTION,                                                                                       "
            +"  		i.supplier_id PRIMARY_SUPPLIER_ID,                                                                       "
            +"  		deptid dept_id,                                                                                          "
            +"  		classid Class_Id,                                                                                        "
            +"  		Vendor_Image,                                                                                            "
            +"  		Vendor_Sample                                                                                            "
            +"  	  FROM VENDORPORTAL.ADSE_ITEM_CATALOG aic,                                                                   "
            +"  		XMLTABLE('for $i in $XML_DATA/pim_entry/entry  let                                                       "
            +"  		$uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),                  "
            +"  		$non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and  ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),  "
            +"  		$removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"  "
            +"  		return  "
            +"  		<out>  "
            +"  		<dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[5]}</dept_id>  "
            +"  		<class_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path,  \"Merchandise_Hierarchy\")]/path,\"\\||///\")[6]}</class_id>  "
            +"  		<supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>  "
            +"  		<desc>{$i/Item_Ctg_Spec/Description/Long}</desc>                                                                  "
            +"  		<flag>{$uda80 and $non_sellable_pack and $removal}</flag>                                                         "
            +"  		</out>'                                                                                                           "
            +"  		passing aic.XML_DATA AS \"XML_DATA\" columns flag VARCHAR(10) path '/out/flag',                                     "
            +"  		supplier_id VARCHAR2(20) path '/out/supplier_id', deptid VARCHAR2(20)                                             "
            +"  		path '/out/dept_id', classid VARCHAR2(20) path '/out/class_id', descr VARCHAR2(64) path '/out/desc') i,           "
            +"  		Input inp,                                                                                                        "
            +"  		VENDORPORTAL.ADSE_PET_CATALOG pet,                                                                                "
            +"  		XMLTABLE('for $i in $pet/pim_entry/entry  return                                                                  "
            +"  		<out>                                                                                                             "
            +"  		<img>{if(count($i/Image_Sec_Spec/Images//*) gt 0) then \"Y\" else \"N\"}</img>                                        "
            +"  		<sample>{if(count($i/Image_Sec_Spec/Sample//*) gt 0) then \"Y\" else \"N\"}</sample>                                  "
            +"  		</out>'                                                                                                           "
            +"  		passing pet.xml_data AS \"pet\"                                                                                     "
            +"  		columns                                                                                                           "
            +"  		Vendor_Image VARCHAR2(1) path '/out/img', Vendor_Sample VARCHAR2(1) path '/out/sample') s                         "
            +"  	  WHERE flag          = 'false'                                                                                       "
            +"  	  AND pet.mdmid       = aic.mdmid                                                                                     "
            +"  	  AND aic.MDMID       =inp.ORIN                                                                                       "
            +"  	  AND aic.Entry_type IN ('SKU', 'Style', 'StyleColor', 'Complex Pack', 'PackColor')                                   "
            +"  	  ),                                                                                                                  "
            +"  	  VendorStyle AS (                                                                                                    "
            +"  		SELECT                                                                                                            "
            +"  			i.MDMID,                                                                                                      "
            +"  			i.XML_DATA,                                                                                                   "
            +"  			i.ENTRY_TYPE,                                                                                                 "
            +"  			i.DESCRIPTION,                                                                                                "
            +"  			i.PRIMARY_SUPPLIER_ID,                                                                                        "
            +"  			i.dept_id,                                                                                                    "
            +"  			i.Class_Id,                                                                                                   "
            +"  			i.Vendor_Image,                                                                                               "
            +"  			i.Vendor_Sample,                                                                                              "
            +"  			ia.Vendor_Sytle Style_Id                                                                                      "
            +"  		  FROM Items i,                                                                                                   "
            +"  			  XMLTABLE(                                                                                                   "
            +"  			  'for $i in $XML_DATA/pim_entry/entry/Item_Ctg_Spec/Supplier                                                 "
            +"  			  let                                                                                                         "
            +"  			  $ven_style := $i/VPN/text(),                                                                                "
            +"  			  $primary_flag := $i/Primary_Flag/text()	   	                                                              "
            +"  			  return                                                                                                      "
            +"  			  <out>                                                                                                       "
            +"  				<Vendor_Sytle>{$ven_style}</Vendor_Sytle>                                                                 "
            +"  				<primary_flag>{$primary_flag}</primary_flag>                                                              "
            +"  			  </out>'	                                                                                                  "
            +"  			  passing i.XML_DATA AS \"XML_DATA\" columns                                                                    "
            +"  			  Vendor_Sytle VARCHAR2(100) path '/out/Vendor_Sytle',                                                        "
            +"  			  primary_flag VARCHAR(25) path '/out/primary_flag') ia	 	                                                  "
            +"  		  WHERE                                                                                                           "
            +"  			  primary_flag = 'true'                                                                                       "
            +"  	  ),                                                                                                                  "
            +"  	  ItemSupplier AS                                                                                                     "
            +"  	  (SELECT                                                                                                             "
            +"  		i.Style_Id,                                                                                                       "
            +"  		i.MDMID,                                                                                                          "
            +"  		i.DESCRIPTION,                                                                                                    "
            +"  		i.PRIMARY_SUPPLIER_ID,                                                                                            "
            +"  		i.dept_id,                                                                                                        "
            +"  		i.ENTRY_TYPE,                                                                                                     "
            +"  		i.PRIMARY_SUPPLIER_ID VenId,                                                                                                          "
            +"  		i.Class_Id,                                                                                                       "
            +"  		s.OmnichannelIndicator,                                                                                           "
            +"  		s.VenName,                                                                                                        "
            +"  		Vendor_Image,                                                                                                     "
            +"  		Vendor_Sample                                                                                                     "
            +"  	  FROM VendorStyle i,                                                                                                 "
            +"  		VENDORPORTAL.ADSE_SUPPLIER_CATALOG sup,                                                                           "
            +"  		XMLTABLE('for $i in $XML_DATA/pim_entry/entry  return $i'                                                         "
            +"  		passing sup.xml_data AS \"XML_DATA\" COLUMNS                                                                        "
            +"  		Id VARCHAR2(20) path 'Supplier_Ctg_Spec/Id',                                                                      "
            +"  		VenName VARCHAR2(20) path 'Supplier_Ctg_Spec/Name',                                                               "
        
            +"  		OmnichannelIndicator VARCHAR(2) path 'if (Supplier_Site_Spec/Omni_Channel/Omni_Channel_Indicator eq \"true\") then \"Y\" else \"N\"' ) s "
            +"  	  WHERE i.PRIMARY_SUPPLIER_ID = sup.MDMID                                                                             "
            +"  	  )                                                                                                                   "
            +"  	SELECT                                                                                                                "
            +"  	  i.MDMID ORIN,                                                                                                       "
            +"  	  i.dept_id,                                                                                                          "
            +"  	  i.VenName Vendor_Name,                                                                                              "
            +"  	  i.Style_Id,                                                                                                         "
            +"  	  i.Class_Id,                                                                                                         "
            +"  	  i.VenId Vendor_Id,                                                                                                  "
            +"  	  i.DESCRIPTION,                                                                                                      "
            +"  	  i.OmnichannelIndicator OMNIChennel_Vendor,                                                                          "
            +"  	  i.Vendor_Image,                                                                                                     "
            +"  	  i.Vendor_Sample,                                                                                                    "
            +"  	  i.ENTRY_TYPE                                                                                                        "
            +"  	FROM ItemSupplier i, TypeIndex t                                                                                      "
            +"  	WHERE i.ENTRY_TYPE = t.typ                                                                                            "
            +"  	ORDER BY i.Style_Id, t.indx ";

       
        return STYLE_INFO_QUERY;    
        
    }
    
    
    
        
         
    public String getImageManagementDetails() {
        final String IMAGE_MGMT_QUERY ="with    " 
        +" Input(ORIN) as (Select    :orinNo  from dual),     " 
        /*+" TypeIndex(indx, typ) as  " 
        +" ((Select 1 indx, 'Style' typ  from  dual)  " 
        +" union (Select 2 indx, 'StyleColor' typ  from  dual) " 
        +" union (Select 3 indx, 'Complex Pack' typ  from  dual) " 
        +" union (Select 4 indx, 'PackColor' typ  from  dual)),      "
        +"	  Items_style AS                                                                                                                                               "
        +"	  (SELECT NVL(aic.PARENT_MDMID, aic.MDMID) PARENT_MDMID,                                                                                                 "
        +"		aic.MDMID,                                                                                                                                           "
        +"		aic.ENTRY_TYPE,                                                                                                                                      "
        +"		CASE                                                                                                                                                 "
        +"		  WHEN aic.Entry_Type IN ('StyleColor','PackColor')                                                                                                  "
        +"		  THEN SUBSTR(aic.MDMID,10)                                                                                                                          "
        +"		  ELSE VENDOR_COLOR_CODE                                                                                                                             "
        +"		END VENDOR_COLOR_CODE,                                                                                                                               "
        +"		CASE                                                                                                                                                 "
        +"		  WHEN aic.Entry_Type IN ('StyleColor','PackColor')                                                                                                  "
        +"		  THEN                                                                                                                                               "
        +"			(SELECT /*+ NO_XML_QUERY_REWRITE */ /* si.color                                                                                                                                 "
        +"			FROM VENDORPORTAL.ADSE_ITEM_CATALOG styleItem,                                                                                                   "
        +"			  xmltable('/pim_entry/entry/Item_Style_Spec/Differentiators[Type eq \"COLOR\"]/Codes'                                                             "
        +"			  passing styleItem.XML_DATA columns                                                                                                             "
        +"			  code VARCHAR2(10) path 'Code',                                                                                                                 "
        +"			  color VARCHAR2(60) path 'Vendor_Description') si                                                                                               "
        +"			WHERE si.code           = SUBSTR(aic.MDMID,10)                                                                                                   "
        +"			AND styleItem.Entry_Type='Style'                                                                                                                 "
        +"			AND styleItem.MDMID     = aic.Parent_MDMID                                                                                                       "
        +"			)                                                                                                                                                "
        +"		  ELSE VENDOR_COLOR_DESC                                                                                                                             "
        +"		END VENDOR_COLOR_DESC,                                                                                                                               "
        +"		/*(SELECT   thevalue FROM VENDORPORTAL.ADSE_REFERENCE_DATA                                                                                             "
        +"		WHERE mdmid  =p.imagestate AND container=p.imagestate_container AND rownum   =1) ImageStatus,                                                        "
        +"		p.VENDOR_COLOR_CODE, p.VENDOR_COLOR_DESC,*/  /*                                                                                                        "
        +"		pet.image_status ImageStatus,                                                                                                                        "
        +"		pet.pet_state PETSTATUS,                                                                                                                             "
        +"		                                                                                                                                                     "
        +"		p.CompletionDate,                                                                                                                                    "
        +"		y.supp_code Supp_ID,                                                                                                                                 "
        +"		aic.xml_data                                                                                                                                         "
        +"	  FROM VENDORPORTAL.ADSE_ITEM_CATALOG aic,                                                                                                               "
        +"		XMLTABLE ('/pim_entry' PASSING aic.xml_data COLUMNS supp_code VARCHAR2(30) PATH '//entry/Item_Ctg_Spec/Supplier/Id')y,                               "
        +"		XMLTABLE('for $i in $XML_DATA/pim_entry/entry                                                                                                        "
        +"		let                                                                                                                                                  "
        +"		$uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),                                                              "
        +"		$non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0 and     ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),         "
        +"		$non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and     ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),  " 
        +"		$removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"                                                                                           "
        +"		return                                                                                                                                               "
        +"		<out>                                                                                                                                                "
        +"		<flag>{$uda80 and $non_sellable_pack and $removal}</flag>                                                                                            "
        +"		<colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code}</colorCode>                                                                      "
        +"		<colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description}</colorDesc>                                                        "
        +"		</out>'                                                                                                                                              "
        +"		passing aic.XML_DATA AS \"XML_DATA\" columns                                                                                                           "
        +"		flag VARCHAR(10) path '/out/flag',                                                                                                                   "
        +"		VENDOR_COLOR_CODE VARCHAR2(10) path '/out/colorCode',                                                                                                "
        +"		VENDOR_COLOR_DESC VARCHAR2(40) path '/out/colorDesc') i,                                                                                             "
        +"		VENDORPORTAL.ADSE_PET_CATALOG pet,                                                                                                                   "
        +"		  XMLTABLE(                                                                                                                                          "
        +"		  'let                                                                                                                                               "
        +"		  $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date                                                                              "
        +"		  return                                                                                                                                             "
        +"		  <out>                                                                                                                                              "
        +"			<CompletionDate>{$completionDate}</CompletionDate>                                                                                               "
        +"		  </out>'                                                                                                                                            "
        +"		  passing pet.xml_data AS \"pets\" Columns                                                                                                             "
        +"		  CompletionDate             VARCHAR2(10)  path  '/out/CompletionDate'                                                                               "
        +"		  ) p	,                                                                                                                                            "
        +"		Input inp                                                                                                                                            "
        +"		  WHERE flag          = 'false'                                                                                                                      "
        +"		  AND pet.mdmid       = aic.mdmid                                                                                                                    "
        +"		  and aic.MDMID      =inp.ORIN                                                                                           "
        +"		  AND aic.Entry_type IN ('Style', 'StyleColor', 'Complex Pack', 'PackColor')                                                                         "
        +"		  ),                                                                                                                                                  "
        +"	  Items_stylecolor AS                                                                                                                                               "
        +"	  (SELECT NVL(aic.PARENT_MDMID, aic.MDMID) PARENT_MDMID,                                                                                                 "
        +"		aic.MDMID,                                                                                                                                           "
        +"		aic.ENTRY_TYPE,                                                                                                                                      "
        +"		CASE                                                                                                                                                 "
        +"		  WHEN aic.Entry_Type IN ('StyleColor','PackColor')                                                                                                  "
        +"		  THEN SUBSTR(aic.MDMID,10)                                                                                                                          "
        +"		  ELSE VENDOR_COLOR_CODE                                                                                                                             "
        +"		END VENDOR_COLOR_CODE,                                                                                                                               "
        +"		CASE                                                                                                                                                 "
        +"		  WHEN aic.Entry_Type IN ('StyleColor','PackColor')                                                                                                  "
        +"		  THEN                                                                                                                                               "
        +"			(SELECT /*+ NO_XML_QUERY_REWRITE */ /* si.color                                                                                                                                 "
        +"			FROM VENDORPORTAL.ADSE_ITEM_CATALOG styleItem,                                                                                                   "
        +"			  xmltable('/pim_entry/entry/Item_Style_Spec/Differentiators[Type eq \"COLOR\"]/Codes'                                                             "
        +"			  passing styleItem.XML_DATA columns                                                                                                             "
        +"			  code VARCHAR2(10) path 'Code',                                                                                                                 "
        +"			  color VARCHAR2(60) path 'Vendor_Description') si                                                                                               "
        +"			WHERE si.code           = SUBSTR(aic.MDMID,10)                                                                                                   "
        +"			AND styleItem.Entry_Type='Style'                                                                                                                 "
        +"			AND styleItem.MDMID     = aic.Parent_MDMID                                                                                                       "
        +"			)                                                                                                                                                "
        +"		  ELSE VENDOR_COLOR_DESC                                                                                                                             "
        +"		END VENDOR_COLOR_DESC,                                                                                                                               "
        +"		/*(SELECT   thevalue FROM VENDORPORTAL.ADSE_REFERENCE_DATA                                                                                             "
        +"		WHERE mdmid  =p.imagestate AND container=p.imagestate_container AND rownum   =1) ImageStatus,                                                        "
        +"		p.VENDOR_COLOR_CODE, p.VENDOR_COLOR_DESC,*/    /*                                                                                                      "
        +"		pet.image_status ImageStatus,                                                                                                                        "
        +"		pet.pet_state PETSTATUS,                                                                                                                             "
        +"		                                                                                                                                                     "
        +"		p.CompletionDate,                                                                                                                                    "
        +"		y.supp_code Supp_ID,                                                                                                                                 "
        +"		aic.xml_data                                                                                                                                         "
        +"	  FROM VENDORPORTAL.ADSE_ITEM_CATALOG aic,                                                                                                               "
        +"		XMLTABLE ('/pim_entry' PASSING aic.xml_data COLUMNS supp_code VARCHAR2(30) PATH '//entry/Item_Ctg_Spec/Supplier/Id')y,                               "
        +"		XMLTABLE('for $i in $XML_DATA/pim_entry/entry                                                                                                        "
        +"		let                                                                                                                                                  "
        +"		$uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),                                                              "
        +"		$non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0 and     ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),         "
        +"		$non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and     ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),  " 
        +"		$removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"                                                                                           "
        +"		return                                                                                                                                               "
        +"		<out>                                                                                                                                                "
        +"		<flag>{$uda80 and $non_sellable_pack and $removal}</flag>                                                                                            "
        +"		<colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code}</colorCode>                                                                      "
        +"		<colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description}</colorDesc>                                                        "
        +"		</out>'                                                                                                                                              "
        +"		passing aic.XML_DATA AS \"XML_DATA\" columns                                                                                                           "
        +"		flag VARCHAR(10) path '/out/flag',                                                                                                                   "
        +"		VENDOR_COLOR_CODE VARCHAR2(10) path '/out/colorCode',                                                                                                "
        +"		VENDOR_COLOR_DESC VARCHAR2(40) path '/out/colorDesc') i,                                                                                             "
        +"		VENDORPORTAL.ADSE_PET_CATALOG pet,                                                                                                                   "
        +"		  XMLTABLE(                                                                                                                                          "
        +"		  'let                                                                                                                                               "
        +"		  $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date                                                                              "
        +"		  return                                                                                                                                             "
        +"		  <out>                                                                                                                                              "
        +"			<CompletionDate>{$completionDate}</CompletionDate>                                                                                               "
        +"		  </out>'                                                                                                                                            "
        +"		  passing pet.xml_data AS \"pets\" Columns                                                                                                             "
        +"		  CompletionDate             VARCHAR2(10)  path  '/out/CompletionDate'                                                                               "
        +"		  ) p	,                                                                                                                                            "
        +"		Input inp                                                                                                                                            "
        +"		  WHERE flag          = 'false'                                                                                                                      "
        +"		  AND pet.mdmid       = aic.mdmid                                                                                                                    "
        +"		  and aic.parent_mdmid =ORIN                                                                                           "
        +"		  AND aic.Entry_type IN ('Style', 'StyleColor', 'Complex Pack', 'PackColor')                                                                         "
        +"		  )"
        +"	  SELECT                                                                                                                                                 "
        +"		items.PARENT_MDMID,                                                                                                                                  "
        +"		items.MDMID,                                                                                                                                         "
        +"		items.ENTRY_TYPE,                                                                                                                                    "
        +"		ia.Vendor_Sytle Style_ID,                                                                                                                            "
        +"		items.VENDOR_COLOR_CODE,                                                                                                                             "
        +"		items.VENDOR_COLOR_DESC,                                                                                                                             "
        +"		items.ImageStatus,                                                                                                                                   "
        +"		items.CompletionDate,                                                                                                                                "
        +"		items.Supp_ID,                                                                                                                                       "
        +"		items.PETSTATUS                                                                                                                                      "
        +"	  FROM Items_style items,                                                                                                                                      "
        +"		  XMLTABLE(                                                                                                                                          "
        +"		  'for $i in $XML_DATA/pim_entry/entry/Item_Ctg_Spec/Supplier                                                                                        "
        +"		  let                                                                                                                                                "
        +"		  $ven_style := $i/VPN/text(),                                                                                                                       "
        +"		  $primary_flag := $i/Primary_Flag/text()	   	                                                                                                     "
        +"		  return                                                                                                                                             "
        +"		  <out>                                                                                                                                              "
        +"			<Vendor_Sytle>{$ven_style}</Vendor_Sytle>                                                                                                        "
        +"			<primary_flag>{$primary_flag}</primary_flag>                                                                                                     "
        +"		  </out>'	                                                                                                                                         "
        +"		  passing items.XML_DATA AS \"XML_DATA\" columns                                                                                                       "
        +"		  Vendor_Sytle VARCHAR2(100) path '/out/Vendor_Sytle',                                                                                               "
        +"		  primary_flag VARCHAR(25) path '/out/primary_flag') ia	 	                                                                                         "
        +"	  WHERE                                                                                                                                                  "
        +"		  primary_flag = 'true'                                                                                                          "
        +" union SELECT                                                                                                                                                 "
        +"		items.PARENT_MDMID,                                                                                                                                  "
        +"		items.MDMID,                                                                                                                                         "
        +"		items.ENTRY_TYPE,                                                                                                                                    "
        +"		ia.Vendor_Sytle Style_ID,                                                                                                                            "
        +"		items.VENDOR_COLOR_CODE,                                                                                                                             "
        +"		items.VENDOR_COLOR_DESC,                                                                                                                             "
        +"		items.ImageStatus,                                                                                                                                   "
        +"		items.CompletionDate,                                                                                                                                "
        +"		items.Supp_ID,                                                                                                                                       "
        +"		items.PETSTATUS                                                                                                                                      "
        +"	  FROM Items_stylecolor items,                                                                                                                                      "
        +"		  XMLTABLE(                                                                                                                                          "
        +"		  'for $i in $XML_DATA/pim_entry/entry/Item_Ctg_Spec/Supplier                                                                                        "
        +"		  let                                                                                                                                                "
        +"		  $ven_style := $i/VPN/text(),                                                                                                                       "
        +"		  $primary_flag := $i/Primary_Flag/text()	   	                                                                                                     "
        +"		  return                                                                                                                                             "
        +"		  <out>                                                                                                                                              "
        +"			<Vendor_Sytle>{$ven_style}</Vendor_Sytle>                                                                                                        "
        +"			<primary_flag>{$primary_flag}</primary_flag>                                                                                                     "
        +"		  </out>'	                                                                                                                                         "
        +"		  passing items.XML_DATA AS \"XML_DATA\" columns                                                                                                       "
        +"		  Vendor_Sytle VARCHAR2(100) path '/out/Vendor_Sytle',                                                                                               "
        +"		  primary_flag VARCHAR(25) path '/out/primary_flag') ia	 	                                                                                         "
        +"	  WHERE                                                                                                                                                  "
        +"		  primary_flag = 'true'                                                                                                          ";*/
        
        +"   TypeIndex(indx, typ) AS (                                                        "
        +"   (SELECT 1 indx, 'Style' typ FROM dual                                            "
        +"   )                                                                                "
        +" UNION                                                                              "
        +"   (SELECT 2 indx, 'StyleColor' typ FROM dual                                       "
        +"   )                                                                                "
        +" UNION                                                                              "
        +"   (SELECT 3 indx, 'Complex Pack' typ FROM dual                                     "
        +"   )                                                                                "
        +" UNION                                                                              "
        +"   (SELECT 4 indx, 'PackColor' typ FROM dual                                        "
        +"   )),                                                                              "
        +"   Items_style AS                                                                   "
        +"   (SELECT NVL(aic.PARENT_MDMID, aic.MDMID) PARENT_MDMID,                           "
        +"     aic.MDMID,                                                                     "
        +"     aic.ENTRY_TYPE,                                                                "
        +"     CASE                                                                           "
        +"       WHEN aic.Entry_Type IN ('StyleColor','PackColor')                            "
        +"       THEN SUBSTR(aic.MDMID,10)                                                    "
        +"       ELSE VENDOR_COLOR_CODE                                                       "
        +"     END VENDOR_COLOR_CODE,                                                         "
        +"     CASE                                                                           "
        +"       WHEN aic.Entry_Type IN ('StyleColor','PackColor')                            "
        +"       THEN                                                                         "
        +"         (SELECT                                                                    "
        +"           /*+ NO_XML_QUERY_REWRITE */                                              "
        +"           si.color                                                                 "
        +"         FROM VENDORPORTAL.ADSE_ITEM_CATALOG styleItem,                             "
        +"           Xmltable('/pim_entry/entry/Item_Style_Spec/Differentiators[Type eq \"COLOR\"]/Codes' Passing Styleitem.Xml_Data Columns "
        +"           code VARCHAR2(10) path 'Code',                                               "
        +"           color VARCHAR2(60) path 'Vendor_Description') si                             "
        +"         WHERE si.code           = SUBSTR(aic.MDMID,10)                                 "
        +"         AND styleItem.Entry_Type='Style'                                               "
        +"         AND styleItem.MDMID     = aic.Parent_MDMID                                     "
        +"         )                                                                              "
        +"       ELSE VENDOR_COLOR_DESC                                                           "
        +"     END VENDOR_COLOR_DESC,                                                             "
        +"     /*(SELECT   thevalue FROM VENDORPORTAL.ADSE_REFERENCE_DATA                                                                                                            WHERE mdmid  =p.imagestate AND container=p.imagestate_container AND rownum   =1) ImageStatus,                                                                      p.VENDOR_COLOR_CODE, p.VENDOR_COLOR_DESC,*/ "
        +"     pet.image_status ImageStatus,                                                      "
        +"     pet.pet_state PETSTATUS,                                                           "
        +"     p.CompletionDate,                                                                  "
        +"     y.supp_code Supp_ID,                                                               "
        +"     aic.xml_data, p.CarsFlag                                                           "
        +"   FROM VENDORPORTAL.ADSE_ITEM_CATALOG aic,                                             "
        +" Xmltable ('for $data in $items/pim_entry/entry/Item_Ctg_Spec/Supplier let              "
        +"   $sup_code := $data/Id,                                                               "
        +"   $primary_flag := $data/Primary_Flag                                                  "
        +"   return                                                                               "
        +"   <out>                                                                                "
        +"   <supp_code>{$sup_code}</supp_code>                                                   "
        +"   <primary_flag>{$primary_flag}</primary_flag>                                         "
        +"   </out>' passing aic.xml_data as \"items\" columns                                      "
        +"   supp_code VARCHAR2(30) PATH '/out/supp_code', primary_flag VARCHAR2(30) PATH '/out/primary_flag')y,                                                  "
        +"     XMLTABLE(                                                                                                                                          "
        +"     'for $i in $XML_DATA/pim_entry/entry                                                                                                                       let                                                                                                                                                                $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),                                                                            $non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0 and     ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),                      $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and     ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),               $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"                                                                                                         return                                                                                                                                                             <out>                                                                                                                                                              <flag>{$uda80 and $non_sellable_pack and $removal}</flag>                                                                                                          <colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code}</colorCode>                                                                                    <colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description}</colorDesc>                                                                      </out>' "
        +"     passing aic.XML_DATA AS \"XML_DATA\" columns                                                                                                         "
        +"     Flag Varchar(10) Path '/out/flag',                                                                                                                 "
        +"     Vendor_Color_Code Varchar2(10) Path '/out/colorCode',                                                                                              "
        +"     VENDOR_COLOR_DESC VARCHAR2(40) path '/out/colorDesc') i,                                                                                           "
        +"     VENDORPORTAL.ADSE_PET_CATALOG pet,                                                                                                                 "
        +"     XMLTABLE( 'let $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date,                                                              "
        +"     $carsflag := $pets/pim_entry/entry/Pet_Ctg_Spec/System/Pet_Information/PreCut_Car_Exists                                                           "
        +"     return <out> <CompletionDate>{$completionDate}</CompletionDate> <CarsFlag>{$carsflag}</CarsFlag></out>'                                            "
        +"     passing pet.xml_data AS \"pets\" Columns CompletionDate VARCHAR2(10) path '/out/CompletionDate', CarsFlag VARCHAR2(10) path '/out/CarsFlag' ) p ,    "
        +"     Input inp                                                                                                                                          "
        +"   WHERE flag          = 'false'                                                                                                                        "
        +"   AND pet.mdmid       = aic.mdmid                                                                                                                      "
        +"   AND aic.MDMID       =inp.ORIN                                                                                                                        "
        +"   AND aic.Entry_type IN ('Style', 'StyleColor', 'Complex Pack', 'PackColor')                                                                           "
        +"   AND y.primary_flag = 'true'                                                                                                                          "
        +"   ),                                                                                                                                                   "
        +"   Items_stylecolor AS                                                                                                                                  "
        +"   (SELECT NVL(aic.PARENT_MDMID, aic.MDMID) PARENT_MDMID,                                                                                               "
        +"     aic.MDMID,                                                                                                                                         "
        +"     aic.ENTRY_TYPE,                                                                                                                                    "
        +"     CASE                                                                                                                                               "
        +"       WHEN aic.Entry_Type IN ('StyleColor','PackColor')                                                                                                "
        +"       THEN SUBSTR(aic.MDMID,10)                                                                                                                        "
        +"       ELSE VENDOR_COLOR_CODE                                                                                                                           "
        +"     END VENDOR_COLOR_CODE,                                                                                                                             "
        +"     CASE                                                                                                                                               "
        +"       WHEN aic.Entry_Type IN ('StyleColor','PackColor')                                                                                                "
        +"       THEN                                                                                                                                             "
        +"         (SELECT                                                                                                                                        "
        +"           /*+ NO_XML_QUERY_REWRITE */                                                                                                                  "
        +"           si.color                                                                                                                                     "
        +"         FROM VENDORPORTAL.ADSE_ITEM_CATALOG styleItem,                                                                                                 "
        +"           Xmltable('/pim_entry/entry/Item_Style_Spec/Differentiators[Type eq \"COLOR\"]/Codes' Passing Styleitem.Xml_Data Columns                        "
        +"           code VARCHAR2(10) path 'Code',                                                                                                               "
        +"           color VARCHAR2(60) path 'Vendor_Description') si                                                                                             "
        +"         WHERE si.code           = SUBSTR(aic.MDMID,10)                                                                                                 "
        +"         AND styleItem.Entry_Type='Style'                                                                                                               "
        +"         AND styleItem.MDMID     = aic.Parent_MDMID                                                                                                     "
        +"         )                                                                                                                                              "
        +"       ELSE VENDOR_COLOR_DESC                                                                                                                           "
        +"     END VENDOR_COLOR_DESC,                                                                                                                             "
        +"     /*(SELECT   thevalue FROM VENDORPORTAL.ADSE_REFERENCE_DATA                                                                                                            WHERE mdmid  =p.imagestate AND container=p.imagestate_container AND rownum   =1) ImageStatus,                                                                      p.VENDOR_COLOR_CODE, p.VENDOR_COLOR_DESC,*/ "
        +"     pet.image_status ImageStatus,                                                                                                                      "
        +"     pet.pet_state PETSTATUS,                                                                                                                           "
        +"     p.CompletionDate,                                                                                                                                  "
        +"     y.supp_code Supp_ID,                                                                                                                               "
        +"     aic.xml_data, p.CarsFlag                                                                                                                           "
        +"   FROM VENDORPORTAL.ADSE_ITEM_CATALOG aic,                                                                                                             "
        +"    Xmltable ('for $data in $items/pim_entry/entry/Item_Ctg_Spec/Supplier let                                                                           "
        +"   $sup_code := $data/Id,                                                                                                                               "
        +"   $primary_flag := $data/Primary_Flag                                                                                                                  "
        +"   return                                                                                                                                               "
        +"   <out>                                                                                                                                                "
        +"   <supp_code>{$sup_code}</supp_code>                                                                                                                   "
        +"   <primary_flag>{$primary_flag}</primary_flag>                                                                                                         "
        +"   </out>' passing aic.xml_data as \"items\" columns                                                                                                      "
        +"   supp_code VARCHAR2(30) PATH '/out/supp_code', primary_flag VARCHAR2(30) PATH '/out/primary_flag')y,                                                  "
        +"     XMLTABLE(                                                                                                                                          "
        +"     'for $i in $XML_DATA/pim_entry/entry                                                                                                                       let                                                                                                                                                                $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),                                                                            $non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0 and     ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),                      $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and     ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),               $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"                                                                                                         return                                                                                                                                                             <out>                                                                                                                                                              <flag>{$uda80 and $non_sellable_pack and $removal}</flag>                                                                                                          <colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code}</colorCode>                                                                                    <colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description}</colorDesc>                                                                      </out>' "
        +"     Passing Aic.Xml_Data As \"XML_DATA\" Columns                                                                                                         "
        +"     Flag Varchar(10) Path '/out/flag',                                                                                                                 "
        +"     Vendor_Color_Code Varchar2(10) Path '/out/colorCode',                                                                                              "
        +"     VENDOR_COLOR_DESC VARCHAR2(40) path '/out/colorDesc') i,                                                                                           "
        +"     VENDORPORTAL.ADSE_PET_CATALOG pet,                                                                                                                 "
        +"     XMLTABLE( 'let $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date,                                                              "
        +"     $carsflag := $pets/pim_entry/entry/Pet_Ctg_Spec/System/Pet_Information/PreCut_Car_Exists                                                           "
        +"     return <out> <CompletionDate>{$completionDate}</CompletionDate> <CarsFlag>{$carsflag}</CarsFlag></out>'                                            "
        +"     passing pet.xml_data AS \"pets\" Columns CompletionDate VARCHAR2(10) path '/out/CompletionDate', CarsFlag VARCHAR2(10) path '/out/CarsFlag' ) p ,    "
        +"     Input inp                                                                                                                                          "
        +"   WHERE flag           = 'false'                                                                                                                       "
        +"   And Pet.Mdmid        = Aic.Mdmid                                                                                                                     "
        +"   AND aic.parent_mdmid =  ORIN                                                                                                                         "
        +"   AND aic.Entry_type IN ('Style', 'StyleColor', 'Complex Pack', 'PackColor')                                                                           "
        +"   AND y.primary_flag = 'true'                                                                                                                          "
        +"   )                                                                                                                                                    "
        +" SELECT items.PARENT_MDMID,                                                                                                                             "
        +"   items.MDMID,                                                                                                                                         "
        +"   items.ENTRY_TYPE,                                                                                                                                    "
        +"   ia.Vendor_Sytle Style_ID,                                                                                                                            "
        +"   items.VENDOR_COLOR_CODE,                                                                                                                             "
        +"   items.VENDOR_COLOR_DESC,                                                                                                                             "
        +"   items.ImageStatus,                                                                                                                                   "
        +"   items.CompletionDate,                                                                                                                                "
        +"   items.Supp_ID,                                                                                                                                       "
        +"   items.PETSTATUS, items.CarsFlag                                                                                                                      "
        +" FROM Items_style items,                                                                                                                                "
        +"   XMLTABLE(                                                                                                                                            "
        +"   'for $i in $XML_DATA/pim_entry/entry/Item_Ctg_Spec/Supplier                                                                                                         let                                                                                                                                                                $ven_style := $i/VPN/text(),                                                                                                                                       $primary_flag := $i/Primary_Flag/text()                                                                                                                                   return                                                                                                                                                             <out>                                                                                                                                                                   <Vendor_Sytle>{$ven_style}</Vendor_Sytle>                                                                                                                             <primary_flag>{$primary_flag}</primary_flag>                                                                                                                     </out>' "
        +"   Passing Items.Xml_Data As \"XML_DATA\" Columns                                                                                                         "
        +"   Vendor_Sytle Varchar2(100) Path '/out/Vendor_Sytle',                                                                                                 "
        +"   Primary_Flag Varchar(25) Path '/out/primary_flag') Ia                                                                                                "
        +" WHERE ia.primary_flag = 'true'                                                                                                                         "
        +" UNION                                                                                                                                                  "
        +" SELECT items.PARENT_MDMID,                                                                                                                             "
        +"   items.MDMID,                                                                                                                                         "
        +"   items.ENTRY_TYPE,                                                                                                                                    "
        +"   ia.Vendor_Sytle Style_ID,                                                                                                                            "
        +"   items.VENDOR_COLOR_CODE,                                                                                                                             "
        +"   items.VENDOR_COLOR_DESC,                                                                                                                             "
        +"   items.ImageStatus,                                                                                                                                   "
        +"   items.CompletionDate,                                                                                                                                "
        +"   items.Supp_ID,                                                                                                                                       "
        +"   items.PETSTATUS, items.CarsFlag                                                                                                                      "
        +" FROM Items_stylecolor items,                                                                                                                           "
        +"   XMLTABLE(                                                                                                                                            "
        +"   'for $i in $XML_DATA/pim_entry/entry/Item_Ctg_Spec/Supplier                                                                                                         let                                                                                                                                                                $ven_style := $i/VPN/text(),                                                                                                                                       $primary_flag := $i/Primary_Flag/text()                                                                                                                                   return                                                                                                                                                             <out>                                                                                                                                                                   <Vendor_Sytle>{$ven_style}</Vendor_Sytle>                                                                                                                             <primary_flag>{$primary_flag}</primary_flag>                                                                                                                     </out>' "
        +"   Passing Items.Xml_Data As \"XML_DATA\" Columns                                                                                                         "
        +"   Vendor_Sytle Varchar2(100) Path '/out/Vendor_Sytle',                                                                                                 "
        +"   Primary_Flag Varchar(25) Path '/out/primary_flag') Ia                                                                                                "
        +" WHERE ia.primary_flag = 'true'                                                                                                                         ";

  return IMAGE_MGMT_QUERY;
 }
      
    
    
    /**
     * This query is responsible for getting the vendorinfodetails 
     * @param orinNo
     * @return
     */
    public String getVendorInformation() {
        final String VENDOR_INFO_QUERY=" with    " 
                            +" Input(ORIN) as  " 
                            +" (Select :orinNo from dual)   " 
                            +" select  " 
                            +" pet.mdmid ORIN_NUM,  " 
                            +" p.*,  " 
                            +" pet.entry_type  " 
                            +" from VENDORPORTAL.ADSE_PET_CATALOG pet,   " 
                            +" XMLTABLE('for $i in $pet/pim_entry/entry return  $i'  " 
                            +" passing pet.xml_data as \"pet\"   " 
                            +" columns   " 
                            +" Return_Sample_Indicator varchar(10) path  'if(Image_Sec_Spec/Sample/Is_Returnable eq \"true\") then \"Y\" else \"N\"' ,  " 
                            +" Carrier_Acct varchar2(10) path 'Image_Sec_Spec/Sample/Ship_Account_Number'  ,  " 
                            +" Return_Sample_Addr varchar2(60) path 'Image_Sec_Spec/Sample/ReturnSample' ) p,  " 
                            +" Input i   " 
                            +" where mdmid=i.ORIN  " 
                            +" and pet.MOD_DTM = '01-JAN-00 12.00.00.000000000 PM'  " ;
      
        
        return VENDOR_INFO_QUERY; 
    }
        
    
    
    /**
     * This query is responsible for to get the contact information 
     * @param orinNo
     * @return
     */
    public String getContactInformation(){
        final String CONTACT_INFO_QUERY="with    " 
        +" Input(ORIN) as (Select :orinNo from dual),   " 
        +" TypeIndex(indx, typ) as  " 
        +" ((Select 1 indx, 'Style' typ  from  dual)  " 
        +" union  " 
        +" (Select 2 indx, 'StyleColor' typ  from  dual)  " 
        +" union  " 
        +" (select 3 indx, 'SKU' typ from dual) " 
        +" union  " 
        +" (select 4 indx, 'Complex Pack' typ from dual) " 
        +" union  " 
        +" (select 5 indx, 'PackColor' typ from dual) " 
        +" ),   " 
        +" Items as  " 
        +" (select nvl(aic.PARENT_MDMID, aic.MDMID) PARENT_MDMID,  aic.MDMID, aic.ENTRY_TYPE, i.supplier_id PRIMARY_SUPPLIER_ID   " 
        +" from VENDORPORTAL.ADSE_ITEM_CATALOG aic,  " 
        +" XMLTABLE('for $i in $XML_DATA/pim_entry/entry  let $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"), $non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0 and  ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),  $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and  ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),  $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"  return  <out>  <dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[5]}</dept_id>  <class_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path,  \"Merchandise_Hierarchy\")]/path,\"\\||///\")[6]}</class_id>  <supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>  <flag>{$uda80 and $non_sellable_pack and $removal}</flag>  <colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code}</colorCode>  <colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description}</colorDesc>  <sizeCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code}</sizeCode>  </out>'   " 
        +" passing aic.XML_DATA as \"XML_DATA\"   " 
        +" columns   " 
        +" flag varchar(10) path '/out/flag',   " 
        +" supplier_id VARCHAR2(20) path '/out/supplier_id') i,   " 
        +" Input inp  where flag = 'false'    " 
        +" and aic.MDMID=inp.ORIN  " 
        +" and  aic.Entry_type in ('SKU', 'Style', 'StyleColor','Complex Pack','PackColor') " 
        +" ),  " 
        +" ItemSupplier as  " 
        +" (select i.PARENT_MDMID, i.MDMID,  Contact_Name, Phone, Email1,i.PRIMARY_SUPPLIER_ID   " 
        +" from Items i, VENDORPORTAL.ADSE_SUPPLIER_CATALOG sup,   " 
        +" XMLTABLE('for $i in $XML_DATA/pim_entry/entry/Supplier_Site_Spec/Omni_Channel/Vendor_Contact  return $i'  " 
        +" passing sup.xml_data as \"XML_DATA\"   " 
        +" COLUMNS  Contact_Name  varchar2(20) path 'Vendor_Contact_Name',   " 
        +" Phone varchar2(20) path 'Vendor_Contact_Number',   " 
        +" Email1 varchar2(50) path 'Vendor_Email_Address'   " 
        +") s    " 
        +" where   i.PRIMARY_SUPPLIER_ID = sup.MDMID  )   " 
        +" select 'Contact ' || rownum Title, i.*  " 
        +" from ItemSupplier i  " ;

        
        return CONTACT_INFO_QUERY;  
    }
    
    /**
     * This query is responsible for getting the image info details 
     * @param orinNo
     * @return
     */
    
    
    public String getImageProductDetails(){
    	
    	
    	  final String PRODUCT_DTLS_QUERY= " WITH " 
			 +"   INPUT(ORIN) AS " 
			 +"   ( " 
			 +"     SELECT " 
			 +"    :orinNo ORIN " 
			 +"     FROM " 
			 +"       dual " 
			 +"   ) " 
			 +"   , " 
			 +"   TypeIndex(indx, typ) AS ( " 
			 +"   ( " 
			 +"     SELECT " 
			 +"       1 indx, " 
			 +"       'Style' typ " 
			 +"     FROM " 
			 +"       dual " 
			 +"   ) " 
			 +" UNION " 
			 +"   ( " 
			 +"     SELECT " 
			 +"       2 indx, " 
			 +"       'StyleColor' typ " 
			 +"     FROM " 
			 +"       dual " 
			 +"   ) " 
			 +" UNION " 
			 +"   ( " 
			 +"     SELECT " 
			 +"       3 indx, " 
			 +"       'SKU' typ " 
			 +"     FROM " 
			 +"       dual " 
			 +"   ) " 
			 +"   UNION " 
			 +"   ( " 
			 +"     SELECT " 
			 +"       4 indx, " 
			 +"       'Complex Pack' typ " 
			 +"     FROM " 
			 +"       dual " 
			 +"   ) " 
			 +"   UNION " 
			 +"   ( " 
			 +"     SELECT " 
			 +"       5 indx, " 
			 +"       'PackColor' typ " 
			 +"     FROM " 
			 +"       dual " 
			 +"   ) " 
			 +"   ) " 
			 +" SELECT " 
			 +"   NVL(aic.PARENT_MDMID, aic.MDMID) Style_Id, " 
			 +"   aic.MDMID ORIN_NUM, " 
			 +"   i2.ProductName Product_Name, " 
			 +"   i2.ProductDescription DESCRIPTION, " 
			 +"   aic.Entry_Type " 
			 +" FROM " 
			 +"   VENDORPORTAL.ADSE_ITEM_CATALOG aic, " 
			 +"   XMLTABLE( " 
			 +"   'for $i in $XML_DATA/pim_entry/entry            let $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),  $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and        ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),               $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"     return  <out>                <dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[5]}</dept_id>    <supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>   <desc>{$i/Item_Ctg_Spec/Description/Long}</desc>   <flag>{$uda80 and $non_sellable_pack and $removal}</flag>   </out>' " 
			 +"   passing aic.XML_DATA AS \"XML_DATA\" columns flag VARCHAR(10) path '/out/flag', " 
			 +"   supplier_id                                     VARCHAR2(20) path " 
			 +"   '/out/supplier_id', deptid                      VARCHAR2(20) path " 
			 +"   '/out/dept_id', descr                           VARCHAR2(64) path '/out/desc' " 
			 +"   ) i, " 
			 +"   Input inp, " 
			 +"   VENDORPORTAL.ADSE_PET_CATALOG aic2, " 
			 +"   XMLTABLE( " 
			 +"   'for $i in $XML_DATA/pim_entry/entry        return  <out>   <prod_name>{$i//Product_Name}</prod_name>  <prod_desc>{$i//Product_Description}</prod_desc>    </out>' " 
			 +"   passing aic2.XML_DATA AS \"XML_DATA\" columns ProductName VARCHAR2(300) path " 
			 +"   '/out/prod_name', ProductDescription                    VARCHAR2(3000) path " 
			 +"   '/out/prod_desc') i2 " 
			 +" WHERE " 
			 +"   flag                              = 'false' " 
			 +" AND aic.MOD_DTM                     = '01-JAN-00 12.00.00.000000000 PM' " 
			 +" AND aic.MDMID                       =inp.ORIN " 
			 +" AND aic.Entry_type                 IN ('SKU', 'StyleColor', 'Style','Complex Pack','PackColor') " 
			 +" AND aic2.MOD_DTM                    = '01-JAN-00 12.00.00.000000000 PM' " 
			 +" AND aic2.Entry_type                 IN ('Style','ComplexPack') " 
			 +" AND NVL(aic.PARENT_MDMID, aic.MDMID)=aic2.mdmid " ;
    	  
    	  
    	  return PRODUCT_DTLS_QUERY;
    	        
    }
    
    /**
     * This query is responsible for getting the pep history details
     * @param orinNo
     * @return
     */
    public static String getPepHistoryDetails() {
        final String PEP_HISTORY=
            " with   Input(ORIN) as (Select " + ":orinNo"          + " from dual)  "+
            "select                                                                       "+
            "  pet.mdmid, i.createdBy, i.createdOn, i.lastState,                          "+
            "  i.lastUpdateOn, i.lastUpdateBy, i.currentState                             "+
            "from VENDORPORTAL.ADSE_PET_CATALOG pet,                                      "+
            "  XMLTable('for $i in $pet/pim_entry/entry/Pet_Ctg_Spec/Audit/Image     "+
            "  let                                                                        "+
            "              $lastState := $i//Last_State,                                             "+
            "              $lastUpdate := $i//Last_State_On,                                         "+
            "              $lastUpdateBy := $i//Last_State_By,                                       "+
            "              $createdBy := $i//..//Created_By,                                         "+
            "              $createdOn := $i//..//Created_On,                                         "+
            "              $currentState := $i//..//..//State                                        "+
            "                                                                                        "+
            "  return                                                                     "+
            "  <petStatus>                                                                "+
            "              <lastState>{$lastState}</lastState>                                       "+
            "              <lastUpdate>{$lastUpdate}</lastUpdate>                                    "+
            "              <lastUpdateBy>{$lastUpdateBy}</lastUpdateBy>                              "+
            "              <createdBy>{$createdBy}</createdBy>                                       "+
            "              <createdOn>{$createdOn}</createdOn>                                       "+
            "              <currentState>{$currentState}</currentState>                              "+
            "  </petStatus>'                                                              "+
            "  passing pet.XML_DATA as \"pet\"  columns                                   "+
            "  lastState varchar2(40) path '/petStatus/lastState' ,                       "+
            "  lastUpdateOn varchar2(40) path '/petStatus/lastUpdate'  ,                  "+
            "  lastUpdateBy varchar2(40) path '/petStatus/lastUpdateBy',                  "+
            "  createdBy varchar2(40) path '/petStatus/createdBy',                        "+
            "  createdOn varchar2(40) path '/petStatus/createdOn',                        "+
            "  currentState varchar2(40) path '/petStatus/currentState'                   "+
            "  ) i ,                                                                      "+
            "  Input inp  where pet.mdmid like concat(inp.ORIN,'%')                       ";

          return PEP_HISTORY ;
  }
            
    
    public static String getSampleImageLinks() {
		final String SAMPLE_IMAGE_LINKS=" with    " 
            +" Input(ORIN) as    " 
            +" (Select  :orinNo from dual)    " 
            +" select  " 
            +" pet.mdmid ORIN_NUM,  " 
            +" Image_ID,  " 
            +" Image_Name,  " 
            +" nvl(Image_Location, Image_URL) Image_Location,    " 
            +" Image_Shot_Type,  " 
            +" Link_Status,  " 
            +" p.Image_Status,  " 
            +" Sample_Id,  " 
            +" Sample_Recieved,  " 
            +" Silhouette,    " 
            +" Turn_In_Date,  " 
            +" Sample_Coordinator_Note,  " 
            +" pet.entry_type  " 
            +" from VENDORPORTAL.ADSE_PET_CATALOG pet,   " 
            +" XMLTABLE('for $i in $pet/pim_entry/entry/Image_Sec_Spec/Images  return  $i'  " 
            +" passing pet.xml_data as \"pet\"   " 
            +" columns   " 
            +" Image_ID varchar2(10) path 'Id',   " 
            +" Image_Name varchar2(50) path 'FileName',    " 
            +" Image_URL varchar2(256) path 'Image_URL',   " 
            +" Image_Location varchar2(50) path 'Image_Location',   " 
            +" Image_Shot_Type varchar2(10) path 'Shot_Type',   " 
            +" Link_Status varchar2(10) path 'status_cd',   " 
            +" Image_Status varchar2(16) path 'Status',   " 
            +" Sample_Id varchar2(20) path './../Sample/Id',   " 
            +" Sample_Recieved varchar2(20) path 'if(./../Sample/SampleReceived eq \"true\") then \"Y\" else \"N\"',   " 
            +" Silhouette varchar2(30) path 'if (Silhouette eq \"true\") then \"Y\" else \"N\"',   " 
            +" Turn_In_Date varchar2(80) path './../Sample/TurnInDate',   " 
             +" Sample_Coordinator_Note varchar2(200) path './../Sample/SampleSwatchNotes') p,  " 
            +" Input i   " 
            +" where mdmid=i.ORIN    " 
            +" and (Image_URL is not null or Image_Location is not null)  " 
            +" and Image_Name is not null  " 
            +" and image_id is not null  " ;

		return SAMPLE_IMAGE_LINKS ;
	}
    
    /**
     * This method checks for the ARD requests from database
     * @param orinNo
     * @return
     */
    public String getArtDirectorRequestDetails() {
		final String ARD_REQUEST=" with   Input(ORIN) as   (Select   " + ":orinNo"    + "   from dual) " + 
			"  select pet.mdmid ORIN_NUM, Image_ID, Image_Location, Description, AdditionalInfo, " +  
			"  image_url_description, ARD_Indicator, Art_Director_Comment, Status, pet.entry_type from VENDORPORTAL.ADSE_PET_CATALOG pet, " + 
			"  XMLTABLE('for $i in $pet/pim_entry/entry/Image_Sec_Spec/Images " + 
			"  return  " + 
			"  $i' passing pet.xml_data as \"pet\" " + 
			"  columns " + 
			"  Image_ID varchar2(10) path 'Id', " + 
			"  Image_Location varchar2(100) path 'Image_Location', " + 
			"  Description varchar2(512) path 'Description', " + 
			"  AdditionalInfo varchar2(256) path 'AdditionalInfo', " + 
			"  image_url_description varchar2(256) path 'image_url_description', " + 
			"  FileName varchar2(100) path 'FileName', " + 
			"   ARD_Indicator varchar2(20) path 'Artdirectorindicator', " + 
			"  Art_Director_Comment varchar2(100) path 'ArtDirectorComment', " + 
			"  Status varchar2(20) path 'Status') p, Input i " + 
			"  where mdmid=i.ORIN and FileName is null and  " + 
			"  image_url_description is not null  " ;/*+ 
			"  pet.MOD_DTM = '01-JAN-00 12.00.00.000000000 PM'";*/
		return ARD_REQUEST ;
	}
    
    /**
     * Method to get the Image attribute details query.
     *    
     * @return the query string
     * 
     * Method added For PIM Phase 2 - Regular Item Image Link Attribute
     * Date: 05/13/2016
     * Added By: Cognizant
     */
    public  String getScene7ImageLinks() {
        
        LOGGER.info("***Entering getScene7ImageLinks() method.");
        
        String IMAGE_LINKS_QUERY = " SELECT AIC.MDMID,  "+
        "   T.IMAGEURL,                    "+
        "   T.SWATCHURL,                   "+
        "   T.VIEWERURL,                   "+
        "   T.SHOTTYPE                     "+
        " FROM ADSE_PET_CATALOG AIC,       "+
        "   XMLTABLE( 'for $image in $XML_DATA/pim_entry/entry/Image_Sec_Spec/Scene7_Images return $image'  passing AIC.XML_DATA AS \"XML_DATA\" COLUMNS IMAGEURL VARCHAR(2000) path 'ImageURL', SWATCHURL VARCHAR(2000) path 'SwatchURL', VIEWERURL VARCHAR(2000) path 'ViewerURL', SHOTTYPE VARCHAR(100) path 'Shot_Type') T "+
        " WHERE AIC.MDMID = ?   ";

        LOGGER.debug("IMAGE LINKS QUERY -- \n" + IMAGE_LINKS_QUERY);
        LOGGER.info("***Exiting getScene7ImageLinks() method.");
        return IMAGE_LINKS_QUERY;
    }
    
      
    /**
     * This query is to get the getGroupingInfoDetails
     * @param orinNo
     * @return
     */
    public String getGroupingInfoDetails(){
        final String GROUPING_INFO_QUERY = " SELECT DISTINCT AGC.MDMID GROUP_ID, "+
        	" AGC.DEF_DEPT_ID DEPT_ID, "+
        	" AGC.DEF_PRIMARYSUPPLIERVPN VENDOR_STYLE, "+
        	" AGC.ENTRY_TYPE GROUP_TYPE, AGC.DEF_PRIMARY_SUPPLIER_ID VenId, s.VenName, s.OmnichannelIndicator, "+
        	" AIC.CLASS_ID,  Vendor_Image,  Vendor_Sample,  AGC.GROUP_OVERALL_STATUS_CODE, "+
        	" AGC.GROUP_IMAGE_STATUS_CODE, "+
        	" AGC.GROUP_CONTENT_STATUS_CODE "+
        	" FROM VENDORPORTAL.ADSE_GROUP_CATALOG AGC "+
        	" LEFT OUTER JOIN VENDORPORTAL.ADSE_SUPPLIER_CATALOG sup "+
        	" ON SUP.MDMID=AGC.DEF_PRIMARY_SUPPLIER_ID "+
        	" LEFT OUTER JOIN VENDORPORTAL.ADSE_GROUP_CHILD_MAPPING AGCM "+
        	" ON AGCM.MDMID   =AGC.MDMID "+
        	" AND AGCM.COMPONENT_DEFAULT='true' "+
        	" LEFT OUTER JOIN VENDORPORTAL.ADSE_ITEM_CATALOG AIC "+
        	" ON AIC.MDMID = AGCM.COMPONENT_STYLE_ID "+
        	" LEFT OUTER JOIN VENDORPORTAL.ADSE_PET_CATALOG APC "+
        	" ON APC.MDMID=AIC.MDMID, "+
        	" XMLTABLE('for $i in $pet/pim_entry/entry  return    <out>    " +
        	" <img>{if(count($i/Image_Sec_Spec/Images//*) gt 0) then \"Y\" else \"N\"}</img>   "+
        	" <sample>{if(count($i/Image_Sec_Spec/Sample//*) gt 0) then \"Y\" else \"N\"}</sample>  " +
        	" </out>' passing APC.xml_data AS \"pet\" columns Vendor_Image VARCHAR2(1) " +
        	" path '/out/img', Vendor_Sample VARCHAR2(1) path '/out/sample') " +
        	" (+)PET_XML, " +        	
        	" XMLTABLE('for $i in $XML_DATA/pim_entry/entry  return $i' passing sup.xml_data   " +   
        	" AS \"XML_DATA\" COLUMNS Id      VARCHAR2(20) path 'Supplier_Ctg_Spec/Id', VenName VARCHAR2(20) " +
        	" path 'Supplier_Ctg_Spec/Name',  OmnichannelIndicator VARCHAR(2) path 'if "+
        	" (Supplier_Site_Spec/Omni_Channel/Omni_Channel_Indicator eq \"true\") then \"Y\" else \"N\"' ) (+)s "+ 
        	" WHERE AGC.MDMID=? ";

        
        return GROUPING_INFO_QUERY;    
        
    } 
    
    /**
     * This query is responsible for getting the grouping info details 
     * @param orinNo
     * @return
     */
    
    
    public String getGroupingDetails(){    	
    	
    	  final String GROUPING_DTLS_QUERY= " SELECT "+
    		  " AGC.MDMID GROUP_ID, "+ 
    		  " AGC.GROUP_NAME, "+
    		  " AGCXML.Description, "+
    		  " AGCXML.Effective_Start_Date, "+
    		  " AGCXML.Effective_End_Date, "+
    		  " AGCXML.CARS_Group_Type, "+
    		  " AGC.ENTRY_TYPE GROUP_TYPE, "+
    		  " AGC.GROUP_OVERALL_STATUS_CODE, "+
    		  " AGC.CREATED_BY "+    		
    		  " FROM "+
    		  " ADSE_GROUP_CATALOG AGC, "+
    		  " XMLTABLE( "+
    		  " 'let  $Description:= /pim_entry/entry/Group_Ctg_Spec/Description," +
    		  "  $Effective_Start_Date:= /pim_entry/entry/Group_Ctg_Spec/Effective_Start_Date,   " +
    		  "  $Effective_End_Date:= /pim_entry/entry/Group_Ctg_Spec/Effective_End_Date, " +
    		  " $CARS_Group_Type:= /pim_entry/entry/Group_Ctg_Spec/CARS_Group_Type " +
    		  "  return <out>  <Description>{$Description}</Description>  " +
    		  "  <Effective_Start_Date>{$Effective_Start_Date}</Effective_Start_Date>  " +
    		  " <Effective_End_Date>{$Effective_End_Date}</Effective_End_Date> " +
    		  " <CARS_Group_Type>{$CARS_Group_Type}</CARS_Group_Type>   " +
    		  "  </out>' "+
    		  " passing AGC.XML_DATA Columns Description CLOB path '/out/Description', " +
    		  " Effective_Start_Date VARCHAR2(50) path '/out/Effective_Start_Date' , " +
    		  " Effective_End_Date   VARCHAR2(50) path '/out/Effective_End_Date', " +
    		  " CARS_Group_Type      VARCHAR2(50) path '/out/CARS_Group_Type') AGCXML " +
    		  " WHERE "+
    		  " MDMID = ? ";    	  
    	  
    	  return GROUPING_DTLS_QUERY;
    	        
    }
    
    
    public static String getGroupingSampleImageLinks() {
		final String SAMPLE_IMAGE_LINKS=" SELECT pet.mdmid ORIN_NUM, " +
			" Image_ID, " +
			" Image_Name, " +
			" Original_Image_Name, " +
			" NVL(Image_Location, Image_URL) Image_Location, "+
			" Image_Shot_Type, " +
			" Link_Status, " +
			" pet_XML.Image_Status, " +
			" Sample_Id, " +
			" Sample_Recieved, " +
			" Silhouette, " +
			" Turn_In_Date, " +
			" Sample_Coordinator_Note, " +
			" pet.entry_type " +
			" FROM VENDORPORTAL.ADSE_GROUP_CATALOG pet, " +
			" XMLTABLE('for $i in $pet/pim_entry/entry/Image_Sec_Spec/Images  return  $i'  " +
			" passing pet.xml_data AS \"pet\"  " +
			" columns " + 
			" Image_ID VARCHAR2(10) path 'Id',  " +
			" Image_Name VARCHAR2(50) path 'FileName',  " +
			" Original_Image_Name VARCHAR2(50) path 'OriginalFileName',  " +
			" Image_URL VARCHAR2(256) path 'Image_URL',  " +
			" Image_Location VARCHAR2(50) path 'Image_Location',  " +
			" Image_Shot_Type VARCHAR2(10) path 'Shot_Type',  " +
			" Link_Status VARCHAR2(10) path 'status_cd',  " +
			" Image_Status VARCHAR2(16) path 'Status',  " +
			" Sample_Id VARCHAR2(20) path './../Sample/Id', " + 
			" Sample_Recieved VARCHAR2(20) path 'if(./../Sample/SampleReceived eq \"true\") then \"Y\" else \"N\"',  "+
			" Silhouette VARCHAR2(30) path 'if (Silhouette eq \"true\") then \"Y\" else \"N\"',  " +
			" Turn_In_Date VARCHAR2(80) path './../Sample/TurnInDate',  " +
			" Sample_Coordinator_Note VARCHAR2(200) path './../Sample/SampleSwatchNotes') PET_XML " +
			" WHERE mdmid = ? " +
			" AND (Image_URL    IS NOT NULL OR Image_Location IS NOT NULL) " +
			" AND Image_Name    IS NOT NULL " +
			" AND image_id      IS NOT NULL ";

		return SAMPLE_IMAGE_LINKS ;
	}
    
    
    /**
     * This query is responsible for getting the pep history details
     * @param orinNo
     * @return
     */
    public static String getGroupingHistoryDetails() {
        final String PEP_HISTORY=  " SELECT pet.mdmid,  i.createdBy, i.createdOn, i.lastState, " +
        	" i.lastUpdateOn, i.lastUpdateBy, i.currentState " +
        	" FROM VENDORPORTAL.ADSE_GROUP_CATALOG pet, XMLTable( " +
        	" 'for $i in $pet/pim_entry/entry/Group_Ctg_Spec/Audit/Image   " +    
        	"  let  $lastState := $i//Last_State, " +
        	" $lastUpdate := $i//Last_State_On, " +
        	" $lastUpdateBy := $i//..//Last_Modified_By, " +
        	" $createdBy := $i//..//Created_By, " +
        	" $createdOn := $i//..//Created_On, " +
        	" $currentState := $i//..//..//State  " +
        	" return   <petStatus>  <lastState>{$lastState}</lastState> " +
        	" <lastUpdate>{$lastUpdate}</lastUpdate>  " +
        	" <lastUpdateBy>{$lastUpdateBy}</lastUpdateBy> " +
        	" <createdBy>{$createdBy}</createdBy> " +
        	"<createdOn>{$createdOn}</createdOn> " +
        	"<currentState>{$currentState}</currentState> </petStatus>' "+
        	" passing pet.XML_DATA AS \"pet\"  "+
        	" columns "+
        	" lastState VARCHAR2(40) path '/petStatus/lastState' , " + 
        	" lastUpdateOn VARCHAR2(40) path '/petStatus/lastUpdate' ,  " +
        	" lastUpdateBy VARCHAR2(40) path '/petStatus/lastUpdateBy',  " +
        	" createdBy VARCHAR2(40) path '/petStatus/createdBy',  " +
        	" createdOn VARCHAR2(40) path '/petStatus/createdOn',  " +
        	" currentState VARCHAR2(40) path '/petStatus/currentState' ) i  " +
        	" WHERE pet.mdmid=? " ;

          return PEP_HISTORY ;
  }
    /**
     * Method to get the Image attribute details query.
     *    
     * @return the query string
     * 
     * Method added For PIM Phase 2 - Regular Item Image Link Attribute
     * Date: 05/13/2016
     * Added By: Cognizant
     */
    public  String getGroupingScene7ImageLinks() {
        
        LOGGER.info("***Entering getGroupingScene7ImageLinks() method.");
        
        String IMAGE_LINKS_QUERY = " SELECT AGC.MDMID, "+
        	" T.IMAGEURL, T.SWATCHURL, T.VIEWERURL, "+
        	" T.SHOTTYPE FROM ADSE_GROUP_CATALOG AGC, "+
        	" XMLTABLE( 'for $image in $XML_DATA/pim_entry/entry/Image_Sec_Spec/Scene7_Images return $image'  "+
        	" passing AGC.XML_DATA AS \"XML_DATA\"  "+
        	" COLUMNS  IMAGEURL VARCHAR(1000) path 'ImageURL',  "+
        	" SWATCHURL VARCHAR(1000) path 'SwatchURL',  "+
        	" VIEWERURL VARCHAR(1000) path 'ViewerURL',  "+
        	" SHOTTYPE VARCHAR(100) path 'Shot_Type') T "+
        	" WHERE AGC.MDMID = ? "; 
        LOGGER.info("***Exiting getScene7ImageLinks() method.");
        return IMAGE_LINKS_QUERY;
    }
    
    /**
     * This query is responsible for getting the grouping info details 
     * @param orinNo
     * @return
     */
    
    
    public String getContentStatus(){    	
    	
    	  final String CONTENT_STATUS_QUERY= " SELECT T.Content_Status,T.Overall_Status FROM ADSE_GROUP_CATALOG AGC, " +
        	" XMLTABLE( 'for $image in $XML_DATA/pim_entry/entry/Group_Ctg_Spec return $image'   " +
        	" passing AGC.XML_DATA AS \"XML_DATA\"  " +
        	" COLUMNS  Content_Status VARCHAR(1000) path 'Content_Status', Overall_Status VARCHAR(1000) path 'Overall_Status') T  " +
        	" WHERE AGC.MDMID = ?";    		  	  
    	  
    	  return CONTENT_STATUS_QUERY;
    	        
    }
    
    public String getSoftImageDelete()
    {
        LOGGER.info("***Entering getSoftImageDelete() method.");        
        String IMAGE_LINKS_QUERY = "INSERT INTO IMAGE_SOFT_DELETE (image_id, image_name, orin_id, deleted_by, delete_status, deleted_date) VALUES (:image_id,:image_name,:orin_id,:deleted_by,:delete_status,sysdate)"; 
        LOGGER.info("***Exiting getSoftImageDelete() method.");
        return IMAGE_LINKS_QUERY;
    }
      

}
