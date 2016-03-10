package com.belk.pep.constants;

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

    /*
     * This query checks whether the ORIN is valid or not,if valid,check whether
     * the PET is already exist for the that entered ORIN
     */

public String getPetDetails(String OrinNo) {
    	
    	final String CREATE_PET_QUERY = " WITH INPUT( ORIN) AS " 
                            +"   ( SELECT '"+OrinNo+"' ORIN FROM dual " 
                            +"   ), " 
            /**
        	 * Modification End AFUAXK4
        	 * DATE: 02/10/2016
        	 */
            /*
                            +"   TypeIndex(indx, typ) AS ( " 
                            +"   (SELECT 1 indx, 'Style' typ FROM dual " 
                            +"   ) " 
                            +" UNION " 
                            +"   (SELECT 2 indx, 'StyleColor' typ FROM dual " 
                            +"   ) " 
                            +" UNION " 
                            +"   (SELECT 3 indx, 'SKU' typ FROM dual " 
                            +"   ) " 
                            +" UNION " 
                            +"   (SELECT 4 indx, 'Complex Pack' typ FROM dual " 
                            +"   )), InitialItem AS " 
                            +"   (SELECT ( " 
                            +"     CASE " 
                            +"       WHEN aic.PARENT_MDMID IS NULL " 
                            +"       THEN aic.MDMID " 
                            +"       ELSE aic.PARENT_MDMID " 
                            +"     END) PARENT_MDMID, " 
                            +"     aic.MDMID, " 
                            +"     aic.ENTRY_TYPE, " 
                            +"     descr DESCRIPTION, " 
                            +"     i.supplier_id PRIMARY_SUPPLIER_ID, " 
                            +"     i.deptid dept_id, " 
                            +"     i.classId Class_Id, " 
                            +"     VENDOR_COLOR_CODE, " 
                            +"     VENDOR_COLOR_DESC, " 
                            +"     VENDOR_SIZE_CODE, " 
                            +"     flag, " 
                            +"     checkflag " 
                            +"   FROM VENDORPORTAL.ADSE_ITEM_CATALOG aic, " 
                            +"     XMLTABLE( " 
                            +"     'for $i in $XML_DATA/pim_entry/entry       let $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),       $non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0 and ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),      $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),       $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"        return        <out>        <dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[5]}</dept_id>      <class_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[6]}</class_id>      <supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>      <desc>{$i/Item_Ctg_Spec/Description/Long}</desc>      <flag>{$uda80 and $non_sellable_pack and $removal}</flag>      <checkflag>{$removal}</checkflag>      <colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code}</colorCode>      <colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description}</colorDesc>      <sizeCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code}</sizeCode>      </out>' " 
                            +"     passing aic.XML_DATA AS \"XML_DATA\" columns checkflag VARCHAR(10) path '/out/checkflag', flag VARCHAR(10) path '/out/flag', supplier_id VARCHAR2(20) path '/out/supplier_id', deptid VARCHAR2(20) path '/out/dept_id', classId VARCHAR2(20) path '/out/class_id', descr VARCHAR2(64) path '/out/desc', VENDOR_COLOR_CODE VARCHAR2(10) path '/out/colorCode', VENDOR_COLOR_DESC VARCHAR2(40) path '/out/colorDesc', VENDOR_SIZE_CODE VARCHAR2(10) path '/out/sizeCode') i, " 
                            +"     Input inp " 
                            +"   WHERE aic.MDMID        = inp.ORIN " 
                            +"   AND aic.Entry_type     IN ('Complex Pack', 'SKU', 'Style', 'StyleColor') " 
                            +"   AND aic.MOD_DTM         = '01-JAN-00 12.00.00.000000000 PM' " 
                            +"   AND ((i.flag            = 'false' " 
                            +"   AND aic.Entry_type     IN ('Complex Pack', 'Style')) " 
                            +"   OR (aic.Entry_type NOT IN ('Complex Pack', 'Style') " 
                            +"   AND i.checkflag         = 'false')) " 
                            +"   union all " 
                            +"   SELECT ( " 
                            +"     CASE " 
                            +"       WHEN aic.PARENT_MDMID IS NULL " 
                            +"       THEN aic.MDMID " 
                            +"       ELSE aic.PARENT_MDMID " 
                            +"     END) PARENT_MDMID, " 
                            +"     aic.MDMID, " 
                            +"     aic.ENTRY_TYPE, " 
                            +"     descr DESCRIPTION, " 
                            +"     i.supplier_id PRIMARY_SUPPLIER_ID, " 
                            +"     i.deptid dept_id, " 
                            +"     i.classId Class_Id, " 
                            +"     VENDOR_COLOR_CODE, " 
                            +"     VENDOR_COLOR_DESC, " 
                            +"     VENDOR_SIZE_CODE, " 
                            +"     flag, " 
                            +"     checkflag " 
                            +"   FROM VENDORPORTAL.ADSE_ITEM_CATALOG aic, " 
                            +"     XMLTABLE( " 
                            +"     'for $i in $XML_DATA/pim_entry/entry       let $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),       $non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0 and ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),      $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),       $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"        return        <out>        <dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[5]}</dept_id>      <class_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[6]}</class_id>      <supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>      <desc>{$i/Item_Ctg_Spec/Description/Long}</desc>      <flag>{$uda80 and $non_sellable_pack and $removal}</flag>      <checkflag>{$removal}</checkflag>      <colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code}</colorCode>      <colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description}</colorDesc>      <sizeCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code}</sizeCode>      </out>' " 
                            +"     passing aic.XML_DATA AS \"XML_DATA\" columns checkflag VARCHAR(10) path '/out/checkflag', flag VARCHAR(10) path '/out/flag', supplier_id VARCHAR2(20) path '/out/supplier_id', deptid VARCHAR2(20) path '/out/dept_id', classId VARCHAR2(20) path '/out/class_id', descr VARCHAR2(64) path '/out/desc', VENDOR_COLOR_CODE VARCHAR2(10) path '/out/colorCode', VENDOR_COLOR_DESC VARCHAR2(40) path '/out/colorDesc', VENDOR_SIZE_CODE VARCHAR2(10) path '/out/sizeCode') i, " 
                            +"     Input inp " 
                            +"   WHERE aic.PARENT_MDMID     = inp.ORIN " 
                             +"   AND aic.Entry_type     IN ('Complex Pack', 'SKU', 'Style', 'StyleColor') " 
                            +"   AND aic.MOD_DTM         = '01-JAN-00 12.00.00.000000000 PM' " 
                            +"   AND ((i.flag            = 'false' " 
                            +"   AND aic.Entry_type     IN ('Complex Pack', 'Style')) " 
                            +"   OR (aic.Entry_type NOT IN ('Complex Pack', 'Style') " 
                            +"   AND i.checkflag         = 'false')) " 
                            +"   ), " 
                            +"   Items AS " 
                            +"   (SELECT aic.PARENT_MDMID, " 
                            +"     aic.MDMID, " 
                            +"     aic.ENTRY_TYPE, " 
                            +"     aic.DESCRIPTION, " 
                            +"     aic.PRIMARY_SUPPLIER_ID, " 
                            +"    aic.dept_id, " 
                            +"     aic.Class_Id, " 
                            +"     aic.VENDOR_COLOR_CODE, " 
                            +"     aic.VENDOR_COLOR_DESC, " 
                            +"     aic.VENDOR_SIZE_CODE " 
                            +"   FROM initialItem aic " 
                            +"   WHERE (aic.MDMID NOT IN " 
                            +"     (SELECT p.mdmid " 
                            +"     FROM VENDORPORTAL.ADSE_PET_CATALOG p " 
                            +"     WHERE MOD_DTM           = '01-JAN-00 12.00.00.000000000 PM' " 
                            +"     AND aic.Entry_type NOT IN ('Complex Pack', 'Style') " 
                            +"     )) " 
                            +"   ), " 
                            +"   ItemSupplier AS " 
                            +"   (SELECT i.PARENT_MDMID, " 
                            +"     i.MDMID, " 
                            +"     i.DESCRIPTION, " 
                            +"     i.PRIMARY_SUPPLIER_ID, " 
                            +"     i.dept_id, " 
                            +"     i.Class_Id, " 
                            +"     s.SITE_NAME, " 
                            +"     i.VENDOR_COLOR_CODE, " 
                            +"     i.VENDOR_COLOR_DESC, " 
                            +"     i.VENDOR_SIZE_CODE, " 
                            +"     i.ENTRY_TYPE, " 
                            +"     s.VenId " 
                            +"   FROM Items i, " 
                            +"     VENDORPORTAL.ADSE_SUPPLIER_CATALOG sup, " 
                            +"     XMLTABLE('for $i in $XML_DATA/pim_entry/entry where $i/Supplier_Ctg_Spec/Status/Code ne \"I\" return $i' passing sup.xml_data AS \"XML_DATA\" COLUMNS Id VARCHAR2(20) path 'Supplier_Ctg_Spec/Id', VenId VARCHAR2(20) path 'Supplier_Ctg_Spec/VEN_Id', SITE_NAME VARCHAR2(50) path 'Supplier_Ctg_Spec/Name' ) s " 
                            +"   WHERE sup.ENTRY_TYPE      = 'Supplier_Site' " 
                            +"   AND sup.MOD_DTM           = '01-JAN-00 12.00.00.000000000 PM' " 
                            +"   AND i.PRIMARY_SUPPLIER_ID = sup.MDMID " 
                            +"   ), " 
                            +"   TabVendorColorDesc AS " 
                            +"   (SELECT i.PARENT_MDMID, " 
                            +"     i.MDMID, " 
                            +"     MAX(o.OMNI_SIZE_DESC) AS OMNI_SIZE_DESC " 
                            +"   FROM ItemSupplier i, " 
                            +"     VENDORPORTAL.ADSE_VENDOR_OMNISIZE_DESC omni, " 
                            +"     xmltable('for $i in $XML//omni_size_desc return $i' passing omni.XML_DATA AS \"XML\" columns OMNI_SIZE_DESC VARCHAR(40) path '.' ) o " 
                            +"   WHERE i.Entry_Type     = 'SKU' " 
                            +"   AND i.VENDOR_SIZE_CODE =omni.NRF_SIZE_CODE " 
                            +"   AND i.dept_id          =omni.DEPT_ID " 
                            +"   AND i.Class_Id         =omni.CLASS_ID " 
                            +"   AND i.VenId            =omni.VENDOR_ID " 
                            +"   GROUP BY i.PARENT_MDMID, " 
                            +"     i.MDMID " 
                            +"   ) , " 
                            +"   FinalTab AS ( " 
                            +"   (SELECT i.PARENT_MDMID, " 
                            +"     i.MDMID, " 
                            +"     i.DESCRIPTION, " 
                            +"     i.PRIMARY_SUPPLIER_ID, " 
                            +"     i.dept_id, " 
                            +"     i.SITE_NAME, " 
                            +"     i.VENDOR_COLOR_CODE, " 
                            +"     i.VENDOR_COLOR_DESC, " 
                            +"     i.VENDOR_SIZE_CODE, " 
                            +"     x.OMNI_SIZE_DESC, " 
                            +"     i.ENTRY_TYPE " 
                            +"   FROM ItemSupplier i " 
                            +"   LEFT JOIN TabVendorColorDesc x " 
                            +"   ON i.mdmid         = x.mdmid " 
                            +"   AND i.parent_mdmid = x.parent_mdmid " 
                            +"   ) " 
                            +" UNION  " 
                            +"   (SELECT i.PARENT_MDMID, " 
                            +"     i.MDMID, " 
                            +"     i.DESCRIPTION, " 
                            +"     i.PRIMARY_SUPPLIER_ID, " 
                            +"     i.dept_id, " 
                            +"     i.SITE_NAME, " 
                            +"     i.VENDOR_COLOR_CODE, " 
                            +"     i.VENDOR_COLOR_DESC, " 
                            +"     i.VENDOR_SIZE_CODE, " 
                            +"     NULL OMNI_SIZE_DESC, " 
                            +"     i.ENTRY_TYPE " 
                            +"   FROM ItemSupplier i " 
                            +"   WHERE i.Entry_Type IN ('Style', 'Complex Pack') " 
                            +"   ) ) " 
                            +" SELECT fres.PARENT_MDMID Parent_ORIN, " 
                            +"   fres.MDMID ORIN, " 
                            +"   DESCRIPTION, " 
                            +"   fres.PRIMARY_SUPPLIER_ID SUPPLIER_ID, " 
                            +"   dept_id, " 
                            +"   SITE_NAME, " 
                            +"   VENDOR_COLOR_CODE, " 
                            +"   VENDOR_COLOR_DESC, " 
                            +"   VENDOR_SIZE_CODE, " 
                            +"   OMNI_SIZE_DESC, " 
                            +"   ENTRY_TYPE " 
                            +" FROM finalTab fres " 
                            +" WHERE PARENT_MDMID NOT IN " 
                            +"   (SELECT fres.PARENT_MDMID " 
                            +"   FROM finalTab fres " 
                            +"   WHERE fres.PARENT_MDMID IN " 
                            +"     (SELECT mdmid " 
                            +"     FROM VENDORPORTAL.ADSE_PET_CATALOG pet " 
                            +"     WHERE fres.PARENT_MDMID = pet.mdmid " 
                            +"     AND entry_type         IN ('Style', 'ComplexPack') " 
                            +"     ) " 
                            +"   GROUP BY fres.PARENT_MDMID " 
                            +"   HAVING COUNT(*) = 1 " 
                            +"   ) " 
                            +" ORDER BY ORIN " ;
    	
    	*/
    	
                            +" TypeIndex(indx, typ) AS (														"
                            +"  (SELECT 1 indx, 'Style' typ FROM dual)                                          "
                            +" UNION                                                                            "
                            +"  (SELECT 2 indx, 'StyleColor' typ FROM dual)                                     "
                            +" UNION                                                                            "
                            +"  (SELECT 3 indx, 'SKU' typ FROM dual)                                            "
                            +" UNION                                                                            "
                            +"  (SELECT 4 indx, 'Complex Pack' typ FROM dual)                                   "
                            +" UNION                                                                            "
                            +"  (SELECT 4 indx, 'PackColor' typ FROM dual)                                      "
                            +"  ),                                                                              "
                            +" InitialItem AS(                                                                  "
                            +"  SELECT (                                                                        "
                            +"    CASE                                                                          "
                            +"      WHEN aic.PARENT_MDMID IS NULL                                               "
                            +"      THEN aic.MDMID                                                              "
                            +"      ELSE aic.PARENT_MDMID                                                       "
                            +"    END) PARENT_MDMID,                                                            "
                            +"    aic.MDMID,                                                                    "
                            +"    aic.ENTRY_TYPE,                                                               "
                            +"    descr DESCRIPTION,                                                            "
                            +"    i.supplier_id PRIMARY_SUPPLIER_ID,                                            "
                            +"    i.deptid dept_id,                                                             "
                            +"    i.classId Class_Id,                                                           "
                            +"    VENDOR_COLOR_CODE,                                                            "
                            +"    VENDOR_COLOR_DESC,                                                            "
                            +"    VENDOR_SIZE_CODE,                                                             "
                            +"    flag,                                                                         "
                            +"    checkflag                                                                     "
                            +"  FROM VENDORPORTAL.ADSE_ITEM_CATALOG aic,                                        "
                            +"    XMLTABLE(                                                                     "
                            +"    'for $i in $XML_DATA/pim_entry/entry                                          "
                            +"	let $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),                                                       "
                            +"	$non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0 and ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),          "
                            +"	$non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),   "  
                            +"	$removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"                                                                                        "
                            +"	return                                                                                                                                              "
                            +"	<out>                                                                                                                                               "
                            +"	<dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[5]}</dept_id>    "
                            +"	<class_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[6]}</class_id>  "
                            +"	<supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>      <desc>{$i/Item_Ctg_Spec/Description/Long}</desc>           "
                            +"	<flag>{$uda80 and $non_sellable_pack and $removal}</flag>                                                                                           "
                            +"	<checkflag>{$removal}</checkflag>      <colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code}</colorCode>                            "
                            +"	<colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description}</colorDesc>                                                     "
                            +"	<sizeCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code}</sizeCode>      </out>'                                                         "
                            +"    passing aic.XML_DATA AS \"XML_DATA\" columns checkflag VARCHAR(10) path '/out/checkflag',                                                         "
                            +"	flag VARCHAR(10) path '/out/flag', supplier_id VARCHAR2(20) path '/out/supplier_id',                                                                "
                            +"	deptid VARCHAR2(20) path '/out/dept_id', classId VARCHAR2(20) path '/out/class_id',                                                                 "
                            +"	descr VARCHAR2(64) path '/out/desc', VENDOR_COLOR_CODE VARCHAR2(10) path '/out/colorCode',                                                          "
                            +"	VENDOR_COLOR_DESC VARCHAR2(40) path '/out/colorDesc', VENDOR_SIZE_CODE VARCHAR2(10) path '/out/sizeCode') i,                                        "
                            +"    Input inp                                                                                                                                         "
                            +"  WHERE (aic.MDMID        = inp.ORIN OR aic.PARENT_MDMID     = inp.ORIN)                                                                              "
                            +"  AND aic.Entry_type     IN ('Complex Pack', 'SKU', 'Style', 'StyleColor', 'PackColor')                                                               "
                            +"  AND aic.MOD_DTM         = '01-JAN-00 12.00.00.000000000 PM'                                                                                         "
                            +"  AND ((i.flag            = 'false'                                                                                                                   "
                            +"  AND aic.Entry_type     IN ('Complex Pack', 'Style'))                                                                                                "
                            +"  OR (aic.Entry_type NOT IN ('Complex Pack', 'Style')                                                                                                 "
                            +"  AND i.checkflag         = 'false'))                                                                                                                 "
                            +"  ),                                                                                                                                                  "
                            +"  Items AS(                                                                                                                                           "
                            +"  SELECT aic.PARENT_MDMID,                                                                                                                            "
                            +"    aic.MDMID,                                                                                                                                        "
                            +"    aic.ENTRY_TYPE,                                                                                                                                   "
                            +"    aic.DESCRIPTION,                                                                                                                                  "
                            +"    aic.PRIMARY_SUPPLIER_ID,                                                                                                                          "
                            +"   aic.dept_id,                                                                                                                                       "
                            +"    aic.Class_Id,                                                                                                                                     "
                            +"    aic.VENDOR_COLOR_CODE,                                                                                                                            "
                            +"    aic.VENDOR_COLOR_DESC,                                                                                                                            "
                            +"    aic.VENDOR_SIZE_CODE                                                                                                                              "
                            +"  FROM initialItem aic                                                                                                                                "
                            +"  WHERE (aic.MDMID NOT IN                                                                                                                             "
                            +"    (SELECT p.mdmid  FROM VENDORPORTAL.ADSE_PET_CATALOG p                                                                                             "
                            +"    WHERE aic.Entry_type NOT IN ('Complex Pack', 'Style') ))                                                                                          "
                            +"  ),                                                                                                                                                  "
                            +"  ItemSupplier AS                                                                                                                                     "
                            +"  (SELECT i.PARENT_MDMID,                                                                                                                             "
                            +"    i.MDMID,                                                                                                                                          "
                            +"    i.DESCRIPTION,                                                                                                                                    "
                            +"    i.PRIMARY_SUPPLIER_ID,                                                                                                                            "
                            +"    i.dept_id,                                                                                                                                        "
                            +"    i.Class_Id,                                                                                                                                       "
                            +"    s.SITE_NAME,                                                                                                                                      "
                            +"    i.VENDOR_COLOR_CODE,                                                                                                                              "
                            +"    i.VENDOR_COLOR_DESC,                                                                                                                              "
                            +"    i.VENDOR_SIZE_CODE,                                                                                                                               "
                            +"    i.ENTRY_TYPE,                                                                                                                                     "
                            +"    s.VenId                                                                                                                                           "
                            +"  FROM Items i,                                                                                                                                       "
                            +"    VENDORPORTAL.ADSE_SUPPLIER_CATALOG sup,                                                                                                           "
                            +"    XMLTABLE('for $i in $XML_DATA/pim_entry/entry where $i/Supplier_Ctg_Spec/Status/Code ne \"I\"                                                     "
                            +"    return $i' passing sup.xml_data AS \"XML_DATA\" COLUMNS Id VARCHAR2(20) path 'Supplier_Ctg_Spec/Id',                                              "
                            +"    VenId VARCHAR2(20) path 'Supplier_Ctg_Spec/VEN_Id', SITE_NAME VARCHAR2(50) path 'Supplier_Ctg_Spec/Name' ) s                                      "
                            +"  WHERE sup.ENTRY_TYPE      = 'Supplier_Site'                                                                                                         "
                            +"  AND i.PRIMARY_SUPPLIER_ID = sup.MDMID),                                                                                                             "
                            +"  TabVendorColorDesc AS                                                                                                                               "
                            +"  (SELECT i.PARENT_MDMID,                                                                                                                             "
                            +"    i.MDMID,                                                                                                                                          "
                            +"    MAX(o.OMNI_SIZE_DESC) AS OMNI_SIZE_DESC                                                                                                           "
                            +"  FROM ItemSupplier i,                                                                                                                                "
                            +"    VENDORPORTAL.ADSE_VENDOR_OMNISIZE_DESC omni,                                                                                                      "
                            +"    xmltable('for $i in $XML//omni_size_desc return $i' passing omni.XML_DATA AS \"XML\" columns OMNI_SIZE_DESC VARCHAR(40) path '.' ) o              "
                            +"  WHERE i.Entry_Type     = 'SKU'                                                                                                                      "
                            +"  AND i.VENDOR_SIZE_CODE =omni.NRF_SIZE_CODE                                                                                                          "
                            +"  AND i.dept_id          =omni.DEPT_ID                                                                                                                "
                            +"  AND i.Class_Id         =omni.CLASS_ID                                                                                                               "
                            +"  /*AND i.VenId            =omni.VENDOR_ID  */                                                                                                            "
                            +"  GROUP BY i.PARENT_MDMID,                                                                                                                            "
                            +"    i.MDMID                                                                                                                                           "
                            +"  ) ,                                                                                                                                                 "
                            +"  FinalTab AS (                                                                                                                                       "
                            +"  SELECT i.PARENT_MDMID,                                                                                                                              "
                            +"    i.MDMID,                                                                                                                                          "
                            +"    i.DESCRIPTION,                                                                                                                                    "
                            +"    i.PRIMARY_SUPPLIER_ID,                                                                                                                            "
                            +"    i.dept_id,                                                                                                                                        "
                            +"    i.SITE_NAME,                                                                                                                                      "
                            +"    i.VENDOR_COLOR_CODE,                                                                                                                              "
                            +"    i.VENDOR_COLOR_DESC,                                                                                                                              "
                            +"    i.VENDOR_SIZE_CODE,                                                                                                                               "
                            +"    x.OMNI_SIZE_DESC,                                                                                                                                 "
                            +"    i.ENTRY_TYPE                                                                                                                                      "
                            +"  FROM ItemSupplier i                                                                                                                                 "
                            +"  LEFT JOIN TabVendorColorDesc x                                                                                                                      "
                            +"  ON i.mdmid         = x.mdmid                                                                                                                        "
                            +"  AND i.parent_mdmid = x.parent_mdmid                                                                                                                 "
                            +"  )                                                                                                                                                   "
                            +"  Select                                                                                                                                              "
                            +"    fres.PARENT_MDMID Parent_ORIN,                                                                                                                    "
                            +"    fres.MDMID ORIN,                                                                                                                                  "
                            +"    fres.DESCRIPTION,                                                                                                                                 "
                            +"    fres.PRIMARY_SUPPLIER_ID SUPPLIER_ID,                                                                                                             "
                            +"    fres.dept_id,                                                                                                                                     "
                            +"    fres.SITE_NAME,                                                                                                                                   "
                            +"    fres.VENDOR_COLOR_CODE,                                                                                                                           "
                            +"    fres.VENDOR_COLOR_DESC,                                                                                                                           "
                            +"    fres.VENDOR_SIZE_CODE,                                                                                                                            "
                            +"    fres.OMNI_SIZE_DESC,                                                                                                                              "
                            +"    fres.ENTRY_TYPE                                                                                                                                   "
                            +"  FROM finalTab fres ORDER BY ORIN                                                                                                                    ";
    	/**
    	 * Modification End AFUAXK4
    	 * DATE: 02/10/2016
    	 */


            return CREATE_PET_QUERY;
    	
    	    	
    }
    
    

    /**
     * Validate orin.
     * MODIFIED BY AFUAXK4
     * DATE: 02/12/2016
     *
     * @param OrinNo the orin no
     * @return the string
     */


	public String validateOrin() {
		
		   final String ORIN_VALIDATE_QUERY = "  WITH INPUT(ORIN) AS   "+
				   "  (SELECT  " + ":orinNo"   + " ORIN FROM DUAL  ),  item AS  "+
				   /**
				    * MODIFICATION START BY AFUAXK4
				    * DATE: 02/12/2016
				    */
				   /*"  (SELECT MDMID MDM_ID,XML_DATA  "+
				   "  FROM  "+
				   "  (SELECT MDMID,  "+
				   "   ENTRY_TYPE,  "+
				   "  PARENT_MDMID,  "+
				   "   XML_DATA,  "+
				   "  MOD_DTM  "+
				   "  FROM VENDORPORTAL.ADSE_ITEM_CATALOG  "+
				   "  UNION  "+
				   "  SELECT MDMID,  "+
				   "  ENTRY_TYPE,  "+
				   "  PARENT_MDMID,  "+
				   "  XML_DATA,  "+
				   "  MOD_DTM  "+
				   "  FROM VENDORPORTAL.ADSE_GROUP_CATALOG    ),  "+
				   "  Input i  "+
				   "  WHERE ((i.orin    = mdmid  "+
				   "  AND ENTRY_TYPE   IN ('Style','SKU','Complex Pack'))  "+
				   "  OR (parent_mdmid IS NOT NULL  "+
				   "  AND i.orin LIKE concat(parent_mdmid,'%')  "+
				   "  AND ENTRY_TYPE IN ('StyleColor','PackColor'))  "+
				   "  OR (parent_mdmid IS NOT NULL  "+
				   "  AND i.orin = parent_mdmid  "+
				   "  AND ENTRY_TYPE IN ('SKU'))) ),  "+
				   "  pet AS  "+
				   "  (SELECT *  "+
				   "   FROM VENDORPORTAL.ADSE_PET_CATALOG,  "+
				   "  Input i  "+
				   "  WHERE ((i.orin  = mdmid  "+
				   "  AND ENTRY_TYPE IN ('Style','SKU','ComplexPack'))  "+
				   "  OR (MDMID      IN  "+
				   "   (SELECT MDMID  "+
				   "  FROM VENDORPORTAL.ADSE_ITEM_CATALOG  "+
				   "   WHERE i.orin LIKE concat(parent_mdmid,'%')  "+
				   "   AND ENTRY_TYPE   IN ('StyleColor','PackColor')  "+
				   "  AND PARENT_MDMID IS NOT NULL   ))  "+
				   "  OR (MDMID      IN  "+
				   "  (SELECT MDMID  "+
				   "  FROM VENDORPORTAL.ADSE_ITEM_CATALOG  "+
				   "   WHERE i.orin = parent_mdmid  "+
				   "  AND ENTRY_TYPE   IN ('SKU')  "+
				   "  AND PARENT_MDMID IS NOT NULL   ))  ) ),  "+
				   "  ITEMCOUNT AS  "+
				   "   ( SELECT COUNT(*) ITEMCHECK FROM ITEM  "+
				   "  ),  "+
				   "  PETCOUNT AS  "+
				   "  ( SELECT COUNT(*) PETCHECK FROM PET  "+
				   "   ),  "+
				   "  validORIN(MESSAGE) AS  "+
				   "  (SELECT 'ORIN_NOT_FOUND' FROM dual WHERE NOT EXISTS  "+
				   "  (SELECT * FROM item    )  ),  "+
				"  petCreated(MESSAGE) AS  "+
				"  (SELECT 'PET_EXIST' FROM dual,ITEMCOUNT,PETCOUNT WHERE ITEMCHECK = PETCHECK  ),  "+
				"  otherCriteria(MESSAGE) AS  "+
				"   (SELECT 'ORIN_NOT_ELIGIBLE'  "+
				"   FROM dual  "+
				"  WHERE EXISTS  "+
				"  (SELECT *  "+
				"  FROM item i, INPUT I,  "+
				"   XMLTABLE('for $i in $XML_DATA/pim_entry/entry   "+
				"  let   "+
				"  $uda80 :=  ($i/Item_UDA_Spec/Adjustment-Unidentified eq \"Z VENDOR/DEPARTMENT ADJUSTMENT\")   "+
				"              or ($i/Item_UDA_Spec/Adjustment-Unidentified eq \"Z ADJUSTMENT\")   "+
				"   or ($i/Item_UDA_Spec/Adjustment-Unidentified eq \"UNIDENTIFIED\"),   "+
				                  "  $non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0   "+
				"   and  ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),   "+
				"  $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0   "+
				"    and  ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),   "+
				"  $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"     "+
				"        or $i/Group_Ctg_Spec/System/Removal_Flag eq \"true\"    "+
				                  "  return   "+
				"  <flag>{$uda80 or $non_sellable or $non_sellable_pack or $removal}</flag>'   "+
				"   passing XML_DATA AS \"XML_DATA\"   "+
				"   columns   "+
				"   flag VARCHAR(10) path '/flag' )  "+
				"   WHERE flag = 'true'  "+
				"   AND I.orin = MDM_ID  )  ),  "+
				"  supplierCheck(MESSAGE) AS  "+
				"  (SELECT 'Pet cannot be created for an Inactive supplier site'  "+
				"   FROM dual  "+
				"  WHERE EXISTS  "+
				"   (SELECT 1  "+
				"   FROM item,  "+
				"     xmltable('/pim_entry/entry//Supplier[Primary_Flag eq \"true\"]/Id' passing item.XML_DATA columns supplierid VARCHAR(20) path '.'),  "+
				"    VENDORPORTAL.ADSE_SUPPLIER_CATALOG sup,  "+
				"   xmltable('/pim_entry/entry/Supplier_Ctg_Spec/Status/Code' passing sup.XML_DATA columns supplier_state VARCHAR2(5) path '.')  "+
				"   WHERE supplierid  = mdmid  "+
				"  AND supplier_state='I'  )  )  "+
				"  SELECT MESSAGE  "+
				"  FROM  "+
				"  ( SELECT MESSAGE FROM validORIN  "+
				"   UNION ALL  "+
				"  SELECT MESSAGE FROM petCreated  "+
				"  UNION ALL  "+
				"  SELECT MESSAGE FROM otherCriteria  "+
				"  UNION ALL  "+
				"  SELECT 'Success' MESSAGE FROM dual  )  "+
				"  WHERE rownum=1  ";*/
				
				   "    (                                                                                                                        "+
				   "    SELECT MDMID MDM_ID, XML_DATA                                                                                            "+
				   "      FROM VENDORPORTAL.ADSE_ITEM_CATALOG, Input i                                                                           "+
				   "    WHERE ((i.orin    = mdmid  "+
				   "    AND ENTRY_TYPE   IN ('Style','SKU','Complex Pack'))  "+
				   "    OR (parent_mdmid IS NOT NULL  "+
				   "    AND i.orin LIKE concat(parent_mdmid,'%')  "+
				   "    AND ENTRY_TYPE   IN ('StyleColor','PackColor'))  "+
				   "    OR (parent_mdmid IS NOT NULL  "+
				   "    AND i.orin        = parent_mdmid  "+
				   "    AND ENTRY_TYPE   IN ('SKU')))  "+
				   "    ),                                                                                                                       "+
				   "    pet AS                                                                                                                   "+
				   "    (SELECT pet.MDMID, pet.entry_type FROM VENDORPORTAL.ADSE_PET_CATALOG pet, Item itm WHERE pet.mdmid = itm.mdm_id          "+
				   "    ),                                                                                                                       "+
				   "    ITEMCOUNT AS                                                                                                             "+
				   "    ( SELECT COUNT(*) ITEMCHECK FROM ITEM                                                                                    "+
				   "    ),                                                                                                                       "+
				   "    PETCOUNT AS                                                                                                              "+
				   "    ( SELECT COUNT(*) PETCHECK FROM PET                                                                                      "+
				   "    ),                                                                                                                       "+
				   "    validORIN(MESSAGE) AS                                                                                                    "+
				   "    (SELECT 'ORIN_NOT_FOUND' FROM dual WHERE NOT EXISTS                                                                      "+
				   "      (SELECT * FROM item                                                                                                    "+
				   "      )                                                                                                                      "+
				   "    ),                                                                                                                       "+
				   "    petCreated(MESSAGE) AS                                                                                                   "+
				   "    (SELECT 'PET_EXIST' FROM dual,ITEMCOUNT,PETCOUNT WHERE ITEMCHECK = PETCHECK                                              "+
				   "    ),                                                                                                                       "+
				   "    otherCriteria(MESSAGE) AS                                                                                                "+
				   "    (SELECT 'ORIN_NOT_ELIGIBLE'                                                                                              "+
				   "    FROM dual                                                                                                                "+
				   "    WHERE EXISTS                                                                                                             "+
				   "      (SELECT *                                                                                                              "+
				   "      FROM item i,                                                                                                           "+
				   "        INPUT I,                                                                                                             "+
				   "        XMLTABLE('for $i in $XML_DATA/pim_entry/entry     let     $uda80 :=  ($i/Item_UDA_Spec/Adjustment-Unidentified eq \"Z VENDOR/DEPARTMENT ADJUSTMENT\")                 or ($i/Item_UDA_Spec/Adjustment-Unidentified eq \"Z ADJUSTMENT\")      or ($i/Item_UDA_Spec/Adjustment-Unidentified eq \"UNIDENTIFIED\"),     $non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0      and  ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),     $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0       and  ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),     $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"             or $i/Group_Ctg_Spec/System/Removal_Flag eq \"true\"      return     <flag>{$uda80 or $non_sellable or $non_sellable_pack or $removal}</flag>' passing XML_DATA AS \"XML_DATA\" columns flag VARCHAR(10) path '/flag' ) "+
				   "      WHERE flag = 'true'                                                                                                                     "+
				   "      AND I.orin = MDM_ID                                                                                                                     "+
				   "      )                                                                                                                                       "+
				   "    )                                                                                                    "+
				   "  SELECT MESSAGE                                                                                                                              "+
				   "  FROM                                                                                                                                        "+
				   "    ( SELECT MESSAGE FROM validORIN                                                                                                           "+
				   "    UNION ALL                                                                                                                                 "+
				   "    SELECT MESSAGE FROM petCreated                                                                                                            "+
				   "    UNION ALL                                                                                                                                 "+
				   "    SELECT MESSAGE FROM otherCriteria                                                                                                         "+
				   "    UNION ALL                                                                                                                                 "+
				   "    SELECT 'Success' MESSAGE FROM dual                                                                                                        "+
				   "    )                                                                                                                                         "+
				   "  WHERE rownum=1                                                                                                                              ";			                           
		   /**
		    * MODIFICATION END BY AFUAXK4
		    * DATE: 02/12/2016
		    */
	
		   return ORIN_VALIDATE_QUERY;
	
	}
}
