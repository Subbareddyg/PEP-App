package com.belk.pep.constants;

//import java.util.logging.Logger;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.belk.pep.model.AdvanceSearch;
import com.belk.pep.util.PropertiesFileLoader;


/**
 * The Class XqueryConstants.
 *
 * @author AFUSOS3
 */
public class XqueryConstants {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(XqueryConstants.class.getName());
    /**
     * Instantiates a new xquery constants.
     */
    public XqueryConstants() {

    }

    /** The Constant ORACLE_DRIVER. */
    public static final String ORACLE_DRIVER = "driver";

    /** The Constant DATABASEURL. */
    public static final String DATABASE_URL = "databaseUrl";

    /** The Constant DATABASE_USERNAME. */
    public static final String DATABASE_USERNAME = "databaseUsername";

    /** The Constant DATABASE_PASSWORD. */
    public static final String DATABASE_PASSWORD = "databasePassword";
    
    
    /**
     * Gets the all the merchandise department details for the Belk User,The Belk User is DCA .
     * All Merchandise department details need to be displayed on the  the Department Pop Up Screen ,when the Belk User logs in for the first time 
     * 
     * @param orinNo the orin no
     * @return the all department details
     */
    public  String getAllDepartmentDetails() {          
        
        final String GET_ALL_DEPARTMENT_XQUERY=
                "  select DeptId, Dept " 
                        + " from ADSE_MERCHANDISE_HIERARCHY dept,"
                        + " XMLTABLE('for $dept in $XML_DATA/pim_category/entry/Merchandise_Hier_Spec "
                        + " where fn:count($dept/System/Removal_Flag) eq 0 or $dept/System/Removal_Flag eq \"false\" "
                        +" return <out><dept_id>{$dept/Identifiers/RMS_Id}</dept_id> "
                        +" <dept_name>{$dept/Name}</dept_name></out>'"
                        +" passing dept.XML_DATA as \"XML_DATA\""
                        +" columns"
                        +"  Dept varchar2(64) path '/out/dept_name', "
                        +" DeptId varchar2(64) path '/out/dept_id' "
                        +") DEPT_NAME"
                        +" where dept.ENTRY_TYPE = 4"
                        + " and dept.MOD_DTM='01-JAN-00 12.00.00.000000000 PM'"
                        +" order by cast(DeptId as Integer)";
        
        return GET_ALL_DEPARTMENT_XQUERY;             
        
    }
    
    
 /**
  * Gets the all department details  By External User Email Id.
  * When the External User logs in for the first time get all the departments associated with the External User by Email Address to
  * display on the Department Pop Up Screen  
  * @param vendorEmail the vendor email
  * @return the all department details
  */
 public  String getAllDepartmentDetails(String vendorEmail) {          
        
        final String GET_ALL_DEPARTMENT_XQUERY=
               " with "
                +" Input(emailId) as (Select "
                + ":vendorEmail"
                +" from dual)"
                +" select DeptId, Dept "  
                + " from ADSE_MERCHANDISE_HIERARCHY dept, Input i,"
                + " XMLTABLE('for $dept in $XML_DATA/pim_category/entry/Merchandise_Hier_Spec "
                + " where fn:count($dept/System/Removal_Flag) eq 0 or $dept/System/Removal_Flag eq \"false\" "
                +" return <out><dept_id>{$dept/Identifiers/RMS_Id}</dept_id>"
                + " <dept_name>{$dept/Name}</dept_name></out>' "
                +"passing dept.XML_DATA as \"XML_DATA\" "
                + " columns"
                +" Dept varchar2(64) path '/out/dept_name', "
                + " DeptId varchar2(64) path '/out/dept_id' "
                +" ) DEPT_NAME"
                +"  where dept.ENTRY_TYPE = 4"
                + " and dept.MOD_DTM='01-JAN-00 12.00.00.000000000 PM'"
                + " order by cast(DeptId as Integer)";                     
                        
                      
        return GET_ALL_DEPARTMENT_XQUERY;             
        
    }
 
 
 
 /**
  * Gets the like department details it  is applicable for search box on the pop up department screen applicable for the External User and Belk User as well.
  *
  * @param searchString the search string
  * @return the like department details
  */
 public  String getLikeDepartmentDetailsForString(String  searchString) {          
     
     final String GET_ALL_DEPARTMENT_XQUERY=
             " with   Input(\""
             +searchString
             +"\") as (Select '10' from dual)"
             +" select DeptId, Dept"
             +" from  ADSE_MERCHANDISE_HIERARCHY dept, Input i,"
             +" XMLTABLE('for $dept in $XML_DATA/pim_category/entry/Merchandise_Hier_Spec"
             + " where fn:count($dept/System/Removal_Flag) eq 0 or $dept/System/Removal_Flag eq \"false\""
             +"  return <out><dept_id>{$dept/Identifiers/RMS_Id}</dept_id>"
             + " <dept_name>{$dept/Name}</dept_name></out>' "
             +"  passing dept.XML_DATA as \"XML_DATA\""
             +" columns"
             +" Dept varchar2(64) path '/out/dept_name',"
             +" DeptId varchar2(64) path '/out/dept_id'"
             +" ) DEPT_NAME"
             +" where dept.ENTRY_TYPE = 4 and ( '"
             + searchString
             +"' is not null and (DeptId like ('%"
             + searchString
             +"%') "
             +" or UPPER(Dept) like ('%' || '"
             + searchString.toUpperCase()
             +"' || '%')))"
             +" order by cast(DeptId as Integer)";     
                     
          
     
     return GET_ALL_DEPARTMENT_XQUERY;             
     
 }
 
 /**
  * Gets the like department details it  is applicable for search box on the pop up department screen applicable for the External User and Belk User as well.
  *
  * @param searchString the search string
  * @return the like department details
  */
 
 public String getLikeDepartmentDetailsForNumber(String searchString) {  
     final String GET_ALL_DEPARTMENT_XQUERY=
     " with Input(\""
     +searchString
     +"\") as (Select '10' from dual)"
     +" select DeptId, Dept"
     +" from  ADSE_MERCHANDISE_HIERARCHY dept, Input i,"
     +" XMLTABLE('for $dept in $XML_DATA/pim_category/entry/Merchandise_Hier_Spec"
     + " where fn:count($dept/System/Removal_Flag) eq 0 or $dept/System/Removal_Flag eq \"false\""
     +" return <out><dept_id>{$dept/Identifiers/RMS_Id}</dept_id>"
     + " <dept_name>{$dept/Name}</dept_name></out>' "
     +" passing dept.XML_DATA as \"XML_DATA\""
     +" columns"
     +" Dept varchar2(64) path '/out/dept_name',"
     +" DeptId varchar2(64) path '/out/dept_id'"
     +" ) DEPT_NAME"
     +" where dept.ENTRY_TYPE = 4 and MOD_DTM ='01-JAN-00 12.00.00.000000000 PM' and ( "
     + searchString
     +" is not null and (DeptId like ('%' || "
     + searchString
     +" || '%'))) "
     //+" or Dept like ('%' || "
     //+ searchString
     //+" || '%')))"
     +" order by cast(DeptId as Integer)"; 
     return GET_ALL_DEPARTMENT_XQUERY; 
     }




/**
 * Gets the work list display data.
 *
 * @param depts the depts
 * @param email the email
 * @param pepId the pep id
 * @return the work list display data
 */
 //Redundant method need to be verified and deleted
 public String getWorkListDisplayData() {


     StringBuilder workListQuery = new StringBuilder();
     workListQuery.append("WITH ");
     workListQuery.append("  Input(Depts, EmailId, LANId, SuppIds) AS ");
     workListQuery.append("  ( ");
     workListQuery.append("    SELECT ");
     workListQuery.append("         ");
     workListQuery.append(":depts");
     workListQuery.append(" Depts, ");
     workListQuery.append(":email");
     workListQuery.append(" EmailId, ");
     workListQuery.append(":pepId");
     workListQuery.append(" LANId, ");
     workListQuery.append(":supplierId");
     workListQuery.append("  SuppIds ");
     workListQuery.append("      ");
     workListQuery.append("      ");
     workListQuery.append("    FROM ");
     workListQuery.append("      dual ");
     workListQuery.append("  ) ");
     workListQuery.append("  , ");
    workListQuery.append("    givenInpDept as (                                                                                              ");
    workListQuery.append("      SELECT regexp_substr(inp.depts,'[^,]+',1,LEVEL) as dept_id  FROM Input inp                                   ");
    workListQuery.append("              CONNECT BY regexp_substr(inp.depts,'[^,]+',1,LEVEL) IS NOT NULL                                      ");
    workListQuery.append("    ),                                                                                                             ");
    workListQuery.append("    givenInpSupplier as (                                                                                          ");
    workListQuery.append("      SELECT regexp_substr(inp.SuppIds,'[^,]+',1,LEVEL) as supplier_id FROM Input inp                              ");
    workListQuery.append("        CONNECT BY regexp_substr(inp.SuppIds,'[^,]+',1,LEVEL) IS NOT NULL                                          ");
    workListQuery.append("      ),                                                                                                           ");
    workListQuery.append("                                                                                                                   ");
    workListQuery.append("    entryTypeStyleList AS                                                                                          ");
    workListQuery.append("    (                                                                                                              ");
    workListQuery.append("      SELECT                                                                       ");
    workListQuery.append("        PARENT_MDMID,                                                                                              ");
    workListQuery.append("        MDMID,                                                                                                     ");
    workListQuery.append("        XML_DATA,                                                                                                  ");
    workListQuery.append("        ENTRY_TYPE,                                                                                                ");
    workListQuery.append("        MOD_DTM,                                                                                                   ");
    workListQuery.append("        CASE                                                                                                       ");
    workListQuery.append("          when PRIMARY_UPC is null                                                                                 ");
    workListQuery.append("          then NUMBER_04                                                                                           ");
    workListQuery.append("           when PRIMARY_UPC = ' '                                                                                  ");
    workListQuery.append("           then NUMBER_04                                                                                          ");
    workListQuery.append("          else     PRIMARY_UPC                                                                                     ");
    workListQuery.append("        end PRIMARY_UPC,                                                                                           ");
    workListQuery.append("        aic.DEPT_ID, aic.PRIMARY_SUPPLIER_ID Supplier_Id                                                           ");
    workListQuery.append("      FROM                                                                                                         ");
    workListQuery.append("        ADSE_Item_CATALOG aic, givenInpDept d, givenInpSupplier s, Input inp                                       ");
    workListQuery.append("      WHERE                                                                                                        ");
    workListQuery.append("        ENTRY_TYPE = 'Style'                                                                                       ");
    workListQuery.append("        AND                                                                                                        ");
    workListQuery.append("         ((                                                                                                        ");
    workListQuery.append("             (inp.EmailId IS NULL)                                                                                 ");
    workListQuery.append("              AND aic.DEPT_ID = d.dept_id                                                                          ");
    workListQuery.append("          )                                                                                                        ");
    workListQuery.append("          OR                                                                                                       ");
    workListQuery.append("            ( (inp.EmailId IS NOT NULL)                                                                            ");
    workListQuery.append("            AND aic.DEPT_ID =d.dept_id                                                                             ");
    workListQuery.append("              AND inp.SuppIds is not null AND aic.PRIMARY_SUPPLIER_ID = s.supplier_id                              ");
    workListQuery.append("        ))                                                                                                         ");
    workListQuery.append("    ),                                                                                                             ");
    workListQuery.append("    styleListA as(                                                                                                 ");
    workListQuery.append("    SELECT                                                                                                         ");
    workListQuery.append("        PARENT_MDMID,                                                                                              ");
    workListQuery.append("        MDMID,                                                                                                     ");
    workListQuery.append("        XML_DATA,                                                                                                  ");
    workListQuery.append("        ENTRY_TYPE,                                                                                                ");
    workListQuery.append("        MOD_DTM,                                                                                                   ");
    workListQuery.append("        PRIMARY_UPC,                                                                                               ");
    workListQuery.append("        DEPT_ID, Supplier_Id                                                                                       ");
    workListQuery.append("      from(                                                                                                        ");
    workListQuery.append("        SELECT                                                                                                     ");
    workListQuery.append("          PARENT_MDMID,                                                                                            ");
    workListQuery.append("          MDMID,                                                                                                   ");
    workListQuery.append("          XML_DATA,                                                                                                ");
    workListQuery.append("          ENTRY_TYPE,                                                                                              ");
    workListQuery.append("          MOD_DTM,                                                                                                 ");
    workListQuery.append("          PRIMARY_UPC,                                                                                             ");
    workListQuery.append("          DEPT_ID, Supplier_Id                                                                                     ");
    workListQuery.append("        FROM                                                                                                       ");
    workListQuery.append("          entryTypeStyleList                                                                                       ");
    workListQuery.append("          UNION ALL                                                                                                ");
    workListQuery.append("        SELECT                                                                      ");
    workListQuery.append("          aic.PARENT_MDMID,                                                                                        ");
    workListQuery.append("          aic.MDMID,                                                                                               ");
    workListQuery.append("          aic.XML_DATA,                                                                                            ");
    workListQuery.append("          aic.ENTRY_TYPE,                                                                                          ");
    workListQuery.append("          aic.MOD_DTM,                                                                                             ");
    workListQuery.append("          CASE                                                                                                     ");
    workListQuery.append("          when aic.PRIMARY_UPC is null                                                                             ");
    workListQuery.append("          then NUMBER_04                                                                                           ");
    workListQuery.append("           when aic.PRIMARY_UPC = ' '                                                                              ");
    workListQuery.append("           then NUMBER_04                                                                                          ");
    workListQuery.append("          else     aic.PRIMARY_UPC                                                                                 ");
    workListQuery.append("          end PRIMARY_UPC,                                                                                         ");
    workListQuery.append("          aic.DEPT_ID, aic.PRIMARY_SUPPLIER_ID Supplier_Id                                                         ");
    workListQuery.append("        FROM                                                                                                       ");
    workListQuery.append("          ADSE_ITEM_CATALOG aic, adse_pet_catalog pet                                                              ");
    workListQuery.append("        WHERE                                                                                                      ");
    workListQuery.append("              aic.PARENT_MDMID IN                                                                                  ");
    workListQuery.append("              (                                                                                                    ");
    workListQuery.append("                SELECT                                                                                             ");
    workListQuery.append("                  MDMID                                                                                            ");
    workListQuery.append("                FROM                                                                                               ");
    workListQuery.append("                  entryTypeStyleList                                                                               ");
    workListQuery.append("              )                                                                                                    ");
    workListQuery.append("           AND                                                                                                     ");
    workListQuery.append("            aic.ENTRY_TYPE IN ('StyleColor')                                                                       ");
    workListQuery.append("              and aic.mdmid = pet.mdmid                                                                            ");
    workListQuery.append("              and pet.pet_state='01'                                                                               ");
    workListQuery.append("        )                                                                                                          ");
    workListQuery.append("  ),                                                                                                               ");
    workListQuery.append("  styleID_DescA AS(                                                                                                ");
    workListQuery.append("      SELECT                                                                                                       ");
    workListQuery.append("        sl.PARENT_MDMID,                                                                                           ");
    workListQuery.append("        sl.MDMID,                                                                                                  ");
    workListQuery.append("        sl.XML_DATA,                                                                                               ");
    workListQuery.append("        sl.ENTRY_TYPE,                                                                                             ");
    workListQuery.append("        sl.PRIMARY_UPC,                                                                                            ");
    workListQuery.append("        sl.Supplier_Id,                                                                                            ");
    workListQuery.append("        sl.DEPT_ID,                                                                                                ");
    workListQuery.append("        ia.ven_style                                                                                               ");
    workListQuery.append("      FROM                                                                                                         ");
    workListQuery.append("        styleListA sl,                                                                                             ");
    workListQuery.append("        XMLTABLE(                                                                                                  ");
    workListQuery.append("        'for $i in $XML_DATA/pim_entry/entry/Item_Ctg_Spec/Supplier                                                ");
    workListQuery.append("        let                                                                                                        ");
    workListQuery.append("        $ven_style := $i/VPN/text(),                                                                               ");
    workListQuery.append("        $primary_flag := $i/Primary_Flag/text()                                                                    ");
    workListQuery.append("        return                                                                                                     ");
    workListQuery.append("        <out>                                                                                                      ");
    workListQuery.append("          <ven_style>{$ven_style}</ven_style>                                                                      ");
    workListQuery.append("          <primary_flag>{$primary_flag}</primary_flag>                                                             ");
    workListQuery.append("        </out>'                                                                                                    ");
    workListQuery.append("        passing sl.XML_DATA AS \"XML_DATA\" columns                                                                  ");
    workListQuery.append("        ven_style VARCHAR2(100) path '/out/ven_style',                                                             ");
    workListQuery.append("        primary_flag VARCHAR(25) path '/out/primary_flag') ia                                                      ");
    workListQuery.append("      WHERE                                                                                                        ");
    workListQuery.append("        primary_flag = 'true'                                                                                      ");
    workListQuery.append("  ),                                                                                                               ");
    workListQuery.append("  styleID_DescAA AS(                                                                                               ");
    workListQuery.append("      SELECT                                                           ");
    workListQuery.append("        sda.PARENT_MDMID,                                                                                          ");
    workListQuery.append("        sda.MDMID ORIN_NUM,                                                                                        ");
    workListQuery.append("        sda.XML_DATA,                                                                                              ");
    workListQuery.append("        sda.ENTRY_TYPE,                                                                                            ");
    workListQuery.append("        sda.PRIMARY_UPC,                                                                                           ");
    workListQuery.append("        p.COLO_DESC,                                                                                               ");
    workListQuery.append("        p.COLO_CODE,                                                                                               ");
    workListQuery.append("        sda.Supplier_Id,                                                                                           ");
    workListQuery.append("        sda.DEPT_ID,                                                                                               ");
    workListQuery.append("        p.PRODUCT_NAME,                                                                                            ");
    workListQuery.append("        sda.ven_style,                                                                                             ");
    workListQuery.append("        pet.pet_state PETSTATUS,                                                                                   ");
    workListQuery.append("        pet.image_status ImageState,                                                                               ");
    workListQuery.append("        pet.content_status CONTENTSTATUS,                                                                          ");
    workListQuery.append("        p.completion_date,                                                                                         ");
    workListQuery.append("        p.req_type                                                                                                 ");
    workListQuery.append("     from                                                                                                          ");
    workListQuery.append("        styleID_DescA sda,                                                                                         ");
    workListQuery.append("        ADSE_PET_CATALOG pet,                                                                                      ");
    workListQuery.append("        XMLTABLE(                                                                                                  ");
    workListQuery.append("        'let                                                                                                       ");
    workListQuery.append("        $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date,                                     ");
    workListQuery.append("        $colordesc:= $pets/pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,                            ");
    workListQuery.append("        $colorcode:= $pets/pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code                                    ");
    workListQuery.append("        return                                                                                                     ");
    workListQuery.append("        <out>                                                                                                      ");
    workListQuery.append("          <id>{$pets/pim_entry/entry/Pet_Ctg_Spec/Id}</id>                                                         ");
    workListQuery.append("          <completion_date>{$completionDate}</completion_date>                                                     ");
    workListQuery.append("          <req_type>{$pets/pim_entry/entry/Pet_Ctg_Spec/SourceSystem}</req_type>                                   ");
    workListQuery.append("          <PRODUCT_NAME>{$pets/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>                       ");
    workListQuery.append("            <COLO_DESC>{$colordesc}</COLO_DESC>                                                                    ");
    workListQuery.append("            <COLO_CODE>{$colorcode}</COLO_CODE>                                                                    ");
    workListQuery.append("        </out>'                                                                                                    ");
    workListQuery.append("        passing pet.xml_data AS \"pets\" Columns                                                                     ");
    workListQuery.append("        Id                          VARCHAR2(20)  path '/out/id',                                                  ");
    workListQuery.append("        completion_date             VARCHAR2(10)  path  '/out/completion_date',                                    ");
    workListQuery.append("        req_type                    VARCHAR2(20)  path '/out/req_type',                                            ");
    workListQuery.append("        PRODUCT_NAME                VARCHAR2(50)  path '/out/PRODUCT_NAME',                                        ");
    workListQuery.append("          COLO_CODE              VARCHAR2(50)  path '/out/COLO_CODE',                                              ");
    workListQuery.append("          COLO_DESC              VARCHAR2(50)  path '/out/COLO_DESC'                                               ");
    workListQuery.append("        ) p                                                                                                        ");
    workListQuery.append("      WHERE                                                                                                        ");
    // Changes to stop displaying deactivated PET in WorkList
    workListQuery.append("        sda.mdmid     =pet.mdmid AND pet.PET_STATE <>'05'                                                                                   ");
    workListQuery.append("    ),                                                                                                             ");
    workListQuery.append("    finalList as (                                                                                                 ");
    workListQuery.append("      SELECT                                                  ");
    workListQuery.append("        orin.PARENT_MDMID PARENT_STYLE_ORIN,                                                                       ");
    workListQuery.append("        ORIN_NUM,                                                                                                  ");
    workListQuery.append("        orin.ENTRY_TYPE,                                                                                           ");
    workListQuery.append("        orin.COLO_DESC as COLOR_DESC,                                                                              ");
    workListQuery.append("        orin.COLO_CODE,                                                                                            ");
    workListQuery.append("        orin.DEPT_Id,                                                                                              ");
    workListQuery.append("        orin.PRODUCT_NAME,                                                                                         ");
    workListQuery.append("        orin.ven_style,                                                                                            ");
    workListQuery.append("        orin.Supplier_Id,                                                                                          ");
    workListQuery.append("        orin.PRIMARY_UPC,                                                                                          ");
    workListQuery.append("        orin.PETSTATUS,                                                                                            ");
    workListQuery.append("        orin.ImageState,                                                                                           ");
    workListQuery.append("        orin.CONTENTSTATUS,                                                                                        ");
    workListQuery.append("        orin.completion_date,                                                                                      ");
    workListQuery.append("        s.ven_name,                                                                                                ");
    workListQuery.append("        s.omniChannelIndicator, orin.req_type                                                                      ");
    workListQuery.append("      FROM                                                                                                         ");
    workListQuery.append("        styleID_DescAA orin,                                                                                       ");
    workListQuery.append("        ADSE_SUPPLIER_CATALOG supplier,                                                                            ");
    workListQuery.append("        XMLTABLE(                                                                                                  ");
    workListQuery.append("        'let                                                                                                       ");
    workListQuery.append("        $ven_name := $supplierCatalog/pim_entry/entry/Supplier_Ctg_Spec/Name,                                      ");
    workListQuery.append("        $isOmniChannel := $supplierCatalog/pim_entry/entry/Supplier_Site_Spec/Omni_Channel                         ");
    workListQuery.append("        return                                                                                                     ");
    workListQuery.append("        <out>                                                                                                      ");
    workListQuery.append("            <ven_name>{$ven_name}</ven_name>                                                                       ");
    workListQuery.append("            <omni_channel>{if($isOmniChannel eq \"true\") then \"Y\" else \"N\"}</omni_channel>                          ");
    workListQuery.append("        </out>'                                                                                                    ");
    workListQuery.append("        PASSING XMLELEMENT(\"Id\", orin.Supplier_Id) AS \"Suppliers\",                                                 ");
    workListQuery.append("        supplier.XML_DATA                          AS \"supplierCatalog\" columns                                    ");
    workListQuery.append("        ven_name                          VARCHAR2(80) path '/out/ven_name',                                       ");
    workListQuery.append("        omniChannelIndicator VARCHAR2(2) path '/out/omni_channel') s                                               ");
    workListQuery.append("      WHERE                                                                                                        ");
    workListQuery.append("        orin.Supplier_Id = supplier.mdmid                                                                          ");
    workListQuery.append("     )                                                                                                             ");
    workListQuery.append("    SELECT                                                                                                         ");
    workListQuery.append("    CASE                                                                                                           ");
    workListQuery.append("      WHEN LENGTH(ORIN_NUM) > 9                                                                                    ");
    workListQuery.append("      THEN SUBSTR(ORIN_NUM,1,9)                                                                                    ");
    workListQuery.append("      ELSE ORIN_NUM                                                                                                ");
    workListQuery.append("    END Parent_Style_Orin,                                                                                         ");
    workListQuery.append("    fl.ORIN_NUM,                                                                                                   ");
    workListQuery.append("    fl.DEPT_ID,                                                                                                    ");
    workListQuery.append("    fl.Supplier_Id,                                                                                                ");
    workListQuery.append("    fl.PRODUCT_NAME,                                                                                               ");
    workListQuery.append("    fl.ENTRY_TYPE,                                                                                                 ");
    workListQuery.append("    fl.PRIMARY_UPC,                                                                                                ");
    workListQuery.append("    fl.COLOR_DESC,                                                                                                 ");
    workListQuery.append("    fl.VEN_NAME,                                                                                                   ");
    workListQuery.append("    fl.VEN_STYLE,                                                                                                  ");
    workListQuery.append("    fl.PETSTATUS,                                                                                                  ");
    workListQuery.append("    fl.ImageState,                                                                                                 ");
    workListQuery.append("    fl.CONTENTSTATUS,                                                                                              ");
    workListQuery.append("    fl.completion_date,                                                                                            ");
    workListQuery.append("    fl.omniChannelIndicator,                                                                                       ");
    workListQuery.append("    fl.req_type                                                                                                    ");
    workListQuery.append("  FROM                                                                                                             ");
    workListQuery.append("  finalList fl                                                                                                     ");
    workListQuery.append("  where (fl.ENTRY_TYPE IN ('Style','StyleColor') AND fl.PETSTATUS = '01'                                           ");
    workListQuery.append("        OR fl.ORIN_NUM in (select PARENT_STYLE_ORIN from finalList where petstatus='01' and ENTRY_TYPE = 'StyleColor')) ");

     // GET_ALL_ORIN_WITH_PETS_XQUERY=;

     LOGGER.info("\n--------->> GET_ALL_ORIN_WITH_PETS_XQUERY "
         + workListQuery.toString());

     return workListQuery.toString();
   }

    
    /**
     * Gets the work list display data for Complex Pack.
     *
     * @param depts the depts
     * @param email the email
     * @param pepId the pep id
     * @return the work list display data
     */
 //redundant method need to be verified and deleted
 public String getWorkListDisplayDataComplexPack() {

     StringBuilder workListQuery = new StringBuilder();
     workListQuery.append("WITH ");
     workListQuery.append("  Input(Depts, EmailId, LANId, SuppIds) AS ");
     workListQuery.append("  ( ");
     workListQuery.append("    SELECT ");
     workListQuery.append("         ");
     workListQuery.append(":depts");
     workListQuery.append(" Depts, ");
     workListQuery.append(":email");
     workListQuery.append(" EmailId, ");
     workListQuery.append(":pepId");
     workListQuery.append(" LANId, ");
     workListQuery.append(":supplierId");
     workListQuery.append("  SuppIds ");
     workListQuery.append("      ");
     workListQuery.append("      ");
     workListQuery.append("    FROM ");
     workListQuery.append("      dual ");
     workListQuery.append("  ) ");
     workListQuery.append("  , ");
    workListQuery.append("    givenInpDept as (                                                                                         ");
    workListQuery.append("        SELECT regexp_substr(inp.depts,'[^,]+',1,LEVEL) as dept_id  FROM Input inp                            ");
    workListQuery.append("                  CONNECT BY regexp_substr(inp.depts,'[^,]+',1,LEVEL) IS NOT NULL                             ");
    workListQuery.append("    ),                                                                                                        ");
    workListQuery.append("    givenInpSupplier as (                                                                                     ");
    workListQuery.append("        SELECT regexp_substr(inp.SuppIds,'[^,]+',1,LEVEL) as supplier_id FROM Input inp                       ");
    workListQuery.append("          CONNECT BY regexp_substr(inp.SuppIds,'[^,]+',1,LEVEL) IS NOT NULL                                   ");
    workListQuery.append("    ),                                                                                                        ");
    workListQuery.append("                                                                                                              ");
    workListQuery.append("    entryTypeStyleList AS                                                                                     ");
    workListQuery.append("    (                                                                                                         ");
    workListQuery.append("      SELECT /*+ index(aic ITEM_CTG_IDX8) */                                                                  ");
    workListQuery.append("        PARENT_MDMID,                                                                                         ");
    workListQuery.append("        MDMID,                                                                                                ");
    workListQuery.append("        XML_DATA,                                                                                             ");
    workListQuery.append("        ENTRY_TYPE,                                                                                           ");
    workListQuery.append("        MOD_DTM,                                                                                              ");
    workListQuery.append("        CASE                                                                                                  ");
    workListQuery.append("          when PRIMARY_UPC is null                                                                            ");
    workListQuery.append("          then NUMBER_04                                                                                      ");
    workListQuery.append("           when PRIMARY_UPC = ' '                                                                             ");
    workListQuery.append("           then NUMBER_04                                                                                     ");
    workListQuery.append("          else     PRIMARY_UPC                                                                                ");
    workListQuery.append("        end PRIMARY_UPC,                                                                                      ");
    workListQuery.append("        aic.DEPT_ID, PRIMARY_SUPPLIER_ID Supplier_Id                                                          ");
    workListQuery.append("      FROM                                                                                                    ");
    workListQuery.append("        ADSE_Item_CATALOG aic, givenInpDept d, givenInpSupplier s, Input inp                                  ");
    workListQuery.append("      WHERE                                                                                                   ");
    workListQuery.append("        ENTRY_TYPE = 'Complex Pack'                                                                           ");
    workListQuery.append("        AND                                                                                                   ");
    workListQuery.append("         ((                                                                                                   ");
    workListQuery.append("             (inp.EmailId IS NULL)                                                                            ");
    workListQuery.append("              AND aic.DEPT_ID = d.dept_id                                                                     ");
    workListQuery.append("          )                                                                                                   ");
    workListQuery.append("          OR                                                                                                  ");
    workListQuery.append("            ( (inp.EmailId IS NOT NULL)                                                                       ");
    workListQuery.append("             AND aic.DEPT_ID =d.dept_id                                                                       ");
    workListQuery.append("              AND inp.SuppIds is not null AND aic.PRIMARY_SUPPLIER_ID  = s.supplier_id                        ");
    workListQuery.append("        ))                                                                                                    ");
    workListQuery.append("    ),                                                                                                        ");
    workListQuery.append("    styleListA as(                                                                                            ");
    workListQuery.append("      SELECT                                                                                                  ");
    workListQuery.append("        PARENT_MDMID,                                                                                         ");
    workListQuery.append("        MDMID,                                                                                                ");
    workListQuery.append("        XML_DATA,                                                                                             ");
    workListQuery.append("        ENTRY_TYPE,                                                                                           ");
    workListQuery.append("        MOD_DTM,                                                                                              ");
    workListQuery.append("        PRIMARY_UPC,                                                                                          ");
    workListQuery.append("        DEPT_ID, Supplier_Id                                                                                  ");
    workListQuery.append("      FROM                                                                                                    ");
    workListQuery.append("        (                                                                                                     ");
    workListQuery.append("          SELECT                                                                                              ");
    workListQuery.append("            PARENT_MDMID,                                                                                     ");
    workListQuery.append("            MDMID,                                                                                            ");
    workListQuery.append("            XML_DATA,                                                                                         ");
    workListQuery.append("            ENTRY_TYPE,                                                                                       ");
    workListQuery.append("            MOD_DTM,                                                                                          ");
    workListQuery.append("            PRIMARY_UPC,                                                                                      ");
    workListQuery.append("            DEPT_ID, Supplier_Id                                                                              ");
    workListQuery.append("          FROM                                                                                                ");
    workListQuery.append("            entryTypeStyleList                                                                                ");
    workListQuery.append("      UNION ALL                                                                                               ");
    workListQuery.append("      SELECT                                                                   ");
    workListQuery.append("              aic.PARENT_MDMID,                                                                               ");
    workListQuery.append("              aic.MDMID,                                                                                      ");
    workListQuery.append("              aic.XML_DATA,                                                                                   ");
    workListQuery.append("              aic.ENTRY_TYPE,                                                                                 ");
    workListQuery.append("              aic.MOD_DTM,                                                                                    ");
    workListQuery.append("              CASE                                                                                            ");
    workListQuery.append("              when aic.PRIMARY_UPC is null                                                                    ");
    workListQuery.append("              then NUMBER_04                                                                                  ");
    workListQuery.append("               when aic.PRIMARY_UPC = ' '                                                                     ");
    workListQuery.append("               then NUMBER_04                                                                                 ");
    workListQuery.append("              else     aic.PRIMARY_UPC                                                                        ");
    workListQuery.append("              end PRIMARY_UPC,                                                                                ");
    workListQuery.append("              aic.DEPT_ID, aic.PRIMARY_SUPPLIER_ID Supplier_Id                                                ");
    workListQuery.append("            FROM                                                                                              ");
    workListQuery.append("              ADSE_ITEM_CATALOG aic, adse_pet_catalog pet                                                     ");
    workListQuery.append("          WHERE                                                                                               ");
    workListQuery.append("                aic.PARENT_MDMID IN                                                                           ");
    workListQuery.append("                (                                                                                             ");
    workListQuery.append("                  SELECT                                                                                      ");
    workListQuery.append("                    MDMID                                                                                     ");
    workListQuery.append("                  FROM                                                                                        ");
    workListQuery.append("                    entryTypeStyleList                                                                        ");
    workListQuery.append("                )                                                                                             ");
    workListQuery.append("             AND                                                                                              ");
    workListQuery.append("                 aic.ENTRY_TYPE IN ('PackColor')                                                              ");
    workListQuery.append("                 and aic.mdmid = pet.mdmid                                                                    ");
    workListQuery.append("                 and pet.pet_state='01'                                                                       ");
    workListQuery.append("        )                                                                                                     ");
    workListQuery.append("    ),                                                                                                        ");
    workListQuery.append("   styleID_DescA AS(                                                                                          ");
    workListQuery.append("      SELECT                                                                                                  ");
    workListQuery.append("        sl.PARENT_MDMID,                                                                                      ");
    workListQuery.append("        sl.MDMID,                                                                                             ");
    workListQuery.append("        sl.XML_DATA,                                                                                          ");
    workListQuery.append("        sl.ENTRY_TYPE,                                                                                        ");
    workListQuery.append("        sl.PRIMARY_UPC,                                                                                       ");
    workListQuery.append("        sl.Supplier_Id,                                                                                       ");
    workListQuery.append("        sl.DEPT_ID,                                                                                           ");
    workListQuery.append("        ia.ven_style                                                                                          ");
    workListQuery.append("      FROM                                                                                                    ");
    workListQuery.append("        styleListA sl,                                                                                        ");
    workListQuery.append("        XMLTABLE(                                                                                             ");
    workListQuery.append("        'for $i in $XML_DATA/pim_entry/entry/Item_Ctg_Spec/Supplier                                           ");
    workListQuery.append("        let                                                                                                   ");
    workListQuery.append("        $ven_style := $i/VPN/text(),                                                                          ");
    workListQuery.append("        $primary_flag := $i/Primary_Flag/text()                                                               ");
    workListQuery.append("        return                                                                                                ");
    workListQuery.append("        <out>                                                                                                 ");
    workListQuery.append("          <ven_style>{$ven_style}</ven_style>                                                                 ");
    workListQuery.append("          <primary_flag>{$primary_flag}</primary_flag>                                                        ");
    workListQuery.append("        </out>'                                                                                               ");
    workListQuery.append("        passing sl.XML_DATA AS \"XML_DATA\" columns                                                             ");
    workListQuery.append("        ven_style VARCHAR2(100) path '/out/ven_style',                                                        ");
    workListQuery.append("        primary_flag VARCHAR(25) path '/out/primary_flag') ia                                                 ");
    workListQuery.append("      WHERE                                                                                                   ");
    workListQuery.append("        primary_flag = 'true'                                                                                 ");
    workListQuery.append("    ),                                                                                                        ");
    workListQuery.append("    styleID_DescAA AS(                                                                                        ");
    workListQuery.append("      SELECT                                                       ");
    workListQuery.append("        sda.PARENT_MDMID,                                                                                     ");
    workListQuery.append("        sda.MDMID ORIN_NUM,                                                                                   ");
    workListQuery.append("        sda.XML_DATA,                                                                                         ");
    workListQuery.append("        sda.ENTRY_TYPE,                                                                                       ");
    workListQuery.append("        sda.PRIMARY_UPC,                                                                                      ");
    workListQuery.append("        p.COLO_DESC,                                                                                          ");
    workListQuery.append("        p.COLO_CODE,                                                                                          ");
    workListQuery.append("        sda.Supplier_Id,                                                                                      ");
    workListQuery.append("        sda.DEPT_ID,                                                                                          ");
    workListQuery.append("        p.PRODUCT_NAME,                                                                                       ");
    workListQuery.append("        sda.ven_style,                                                                                        ");
    workListQuery.append("        pet.pet_state PETSTATUS,                                                                              ");
    workListQuery.append("        pet.image_status ImageState,                                                                          ");
    workListQuery.append("        pet.content_status CONTENTSTATUS,                                                                     ");
    workListQuery.append("        p.completion_date,                                                                                    ");
    workListQuery.append("        p.req_type                                                                                            ");
    workListQuery.append("     from                                                                                                     ");
    workListQuery.append("        styleID_DescA sda,                                                                                    ");
    workListQuery.append("        ADSE_PET_CATALOG pet,                                                                                 ");
    workListQuery.append("        XMLTABLE(                                                                                             ");
    workListQuery.append("        'let                                                                                                  ");
    workListQuery.append("        $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date,                                ");
    workListQuery.append("        $colordesc:= $pets/pim_entry/entry/Ecomm_PackColor_Spec/NRF_Color_Description,                        ");
    workListQuery.append("        $colorcode:= $pets/pim_entry/entry/Ecomm_PackColor_Spec/NRF_Color_Code                                ");
    workListQuery.append("        return                                                                                                ");
    workListQuery.append("        <out>                                                                                                 ");
    workListQuery.append("          <id>{$pets/pim_entry/entry/Pet_Ctg_Spec/Id}</id>                                                    ");
    workListQuery.append("          <completion_date>{$completionDate}</completion_date>                                                ");
    workListQuery.append("          <req_type>{$pets/pim_entry/entry/Pet_Ctg_Spec/SourceSystem}</req_type>                              ");
    workListQuery.append("          <PRODUCT_NAME>{$pets/pim_entry/entry/Ecomm_ComplexPack_Spec/Product_Name}</PRODUCT_NAME>            ");
    workListQuery.append("          <COLO_DESC>{$colordesc}</COLO_DESC>                                                                 ");
    workListQuery.append("          <COLO_CODE>{$colorcode}</COLO_CODE>                                                                 ");
    workListQuery.append("        </out>'                                                                                               ");
    workListQuery.append("        passing pet.xml_data AS \"pets\" Columns                                                                ");
    workListQuery.append("        Id                          VARCHAR2(20)  path '/out/id',                                             ");
    workListQuery.append("        completion_date             VARCHAR2(10)  path  '/out/completion_date',                               ");
    workListQuery.append("        req_type                    VARCHAR2(20)  path '/out/req_type',                                       ");
    workListQuery.append("        PRODUCT_NAME              VARCHAR2(50)  path '/out/PRODUCT_NAME',                                     ");
    workListQuery.append("        COLO_CODE              VARCHAR2(50)  path '/out/COLO_CODE',                                           ");
    workListQuery.append("        COLO_DESC              VARCHAR2(50)  path '/out/COLO_DESC'                                            ");
    workListQuery.append("        ) p                                                                                                   ");
    workListQuery.append("      WHERE                                                                                                   ");
    //Changes to stop displaying deactivated PET in WorkList
    workListQuery.append("        sda.mdmid     =pet.mdmid         and pet.PET_STATE<>'05'                                                                     ");
    workListQuery.append("    ),                                                                                                        ");
    workListQuery.append("    finalList as (                                                                                            ");
    workListQuery.append("      SELECT                                             ");
    workListQuery.append("        orin.PARENT_MDMID PARENT_STYLE_ORIN,                                                                  ");
    workListQuery.append("        ORIN_NUM,                                                                                             ");
    workListQuery.append("        orin.ENTRY_TYPE,                                                                                      ");
    workListQuery.append("        orin.COLO_DESC as COLOR_DESC,                                                                         ");
    workListQuery.append("        orin.COLO_CODE,                                                                                       ");
    workListQuery.append("        orin.DEPT_Id,                                                                                         ");
    workListQuery.append("        orin.PRODUCT_NAME,                                                                                    ");
    workListQuery.append("        orin.ven_style,                                                                                       ");
    workListQuery.append("        orin.Supplier_Id,                                                                                     ");
    workListQuery.append("        orin.PRIMARY_UPC,                                                                                     ");
    workListQuery.append("        orin.PETSTATUS,                                                                                       ");
    workListQuery.append("        orin.ImageState,                                                                                      ");
    workListQuery.append("        orin.CONTENTSTATUS,                                                                                   ");
    workListQuery.append("        orin.completion_date,                                                                                 ");
    workListQuery.append("        s.ven_name,                                                                                           ");
    workListQuery.append("        s.omniChannelIndicator, orin.req_type                                                                 ");
    workListQuery.append("      FROM                                                                                                    ");
    workListQuery.append("        styleID_DescAA orin,                                                                                  ");
    workListQuery.append("        ADSE_SUPPLIER_CATALOG supplier,                                                                       ");
    workListQuery.append("        XMLTABLE(                                                                                             ");
    workListQuery.append("        'for $supplier in $supplierCatalog/pim_entry/entry/Supplier_Ctg_Spec                                  ");
    workListQuery.append("        let                                                                                                   ");
    workListQuery.append("        $ven_name := $supplierCatalog/pim_entry/entry/Supplier_Ctg_Spec/Name,                                 ");
    workListQuery.append("        $isOmniChannel := $supplierCatalog/pim_entry/entry/Supplier_Site_Spec/Omni_Channel                    ");
    workListQuery.append("        return                                                                                                ");
    workListQuery.append("        <out>                                                                                                 ");
    workListQuery.append("            <ven_name>{$ven_name}</ven_name>                                                                  ");
    workListQuery.append("            <omni_channel>{if($isOmniChannel eq \"true\") then \"Y\" else \"N\"}</omni_channel>                     ");
    workListQuery.append("        </out>'                                                                                               ");
    workListQuery.append("        PASSING XMLELEMENT(\"Id\", orin.Supplier_Id) AS \"Suppliers\",                                            ");
    workListQuery.append("        supplier.XML_DATA                          AS \"supplierCatalog\" columns                               ");
    workListQuery.append("        ven_name                          VARCHAR2(80) path '/out/ven_name',                                  ");
    workListQuery.append("        omniChannelIndicator VARCHAR2(2) path '/out/omni_channel') s                                          ");
    workListQuery.append("      WHERE                                                                                                   ");
    workListQuery.append("        orin.Supplier_Id = supplier.mdmid                                                                     ");
    workListQuery.append("    )                                                                                                         ");
    workListQuery.append("    SELECT                                                                                                    ");
    workListQuery.append("    CASE                                                                                                      ");
    workListQuery.append("      WHEN LENGTH(ORIN_NUM) > 9                                                                               ");
    workListQuery.append("      THEN SUBSTR(ORIN_NUM,1,9)                                                                               ");
    workListQuery.append("      ELSE ORIN_NUM                                                                                           ");
    workListQuery.append("    END Parent_Style_Orin,                                                                                    ");
    workListQuery.append("    fl.ORIN_NUM,                                                                                              ");
    workListQuery.append("    fl.DEPT_ID,                                                                                               ");
    workListQuery.append("    fl.Supplier_Id,                                                                                           ");
    workListQuery.append("    fl.PRODUCT_NAME,                                                                                          ");
    workListQuery.append("    fl.ENTRY_TYPE,                                                                                            ");
    workListQuery.append("    fl.PRIMARY_UPC,                                                                                           ");
    workListQuery.append("    fl.COLOR_DESC,                                                                                            ");
    workListQuery.append("    fl.VEN_NAME,                                                                                              ");
    workListQuery.append("    fl.VEN_STYLE,                                                                                             ");
    workListQuery.append("    fl.PETSTATUS,                                                                                             ");
    workListQuery.append("    fl.ImageState,                                                                                            ");
    workListQuery.append("    fl.CONTENTSTATUS,                                                                                         ");
    workListQuery.append("    fl.completion_date,                                                                                       ");
    workListQuery.append("    fl.omniChannelIndicator,                                                                                  ");
    workListQuery.append("    fl.req_type                                                                                               ");
    workListQuery.append("  FROM                                                                                                        ");
    workListQuery.append("  finalList fl                                                                                                ");
    workListQuery.append("  where (fl.ENTRY_TYPE in ('Complex Pack', 'PackColor') AND fl.PETSTATUS = '01'                               ");
    workListQuery.append("        OR fl.ORIN_NUM in (select PARENT_STYLE_ORIN from finalList where petstatus='01' and ENTRY_TYPE = 'PackColor')) ");

     // GET_ALL_ORIN_WITH_PETS_XQUERY=;

     LOGGER.info("\n--------->> GET_ALL_ORIN_WITH_PETS_COMPLEXPACK_XQUERY "
         + workListQuery.toString());

     return workListQuery.toString();
 }
                    
                    
                    
/**
 * Gets the all the merchandise department details for the Belk User,The Belk User is DCA .
 * All Merchandise department details need to be displayed on the  the Department Pop Up Screen ,when the Belk User logs in for the first time 
 * 
 * @param orinNo the orin no
 * @return the all department details
 */
public  String getAllDepartmentDetailsLatest(String searchString, String emailId, String pepID) {   
    
    String input="with"
        +"Input(SearchString, emailId, PEPId) as (Select null, null, /*'AFUSOS3'*/ null from dual)";
    
    final String GET_ALL_DEPARTMENT_XQUERY_LATEST=
            "  select DeptId, Dept " 
                    + " from  ADSE_MERCHANDISE_HIERARCHY dept,"
                    + " XMLTABLE('for $dept in $XML_DATA/pim_category/entry/Merchandise_Hier_Spec "
                    + " where fn:count($dept/System/Removal_Flag) eq 0 or $dept/System/Removal_Flag eq \"false\" "
                    +" return <out><dept_id>{$dept/Identifiers/RMS_Id}</dept_id> "
                    +" <dept_name>{$dept/Name}</dept_name></out>'"
                    +" passing dept.XML_DATA as \"XML_DATA\""
                    +" columns"
                    +"  Dept varchar2(64) path '/out/dept_name', "
                    +" DeptId varchar2(64) path '/out/dept_id' "
                    +") DEPT_NAME"
                    +" where dept.ENTRY_TYPE = 4"
                    +" order by cast(DeptId as Integer)";
    
    return GET_ALL_DEPARTMENT_XQUERY_LATEST;             
    
}               

                    
           
             
             
/**
 * Gets the all Class details  By DepartmentId/Ids.
 * @return
 */
public  String getClassDetailsUsingDeptnumbers() {  
    
    /**
     * Modified by AFUAXK4
     * Date: 02/09/2016
     */
    

    StringBuilder  queryfragment = new StringBuilder();
    /*queryfragment.append("WITH Input(Depts) AS ");
    queryfragment.append("  (SELECT ");
    queryfragment.append(":deptids");
    queryfragment.append(" Depts ");    
    queryfragment.append("  FROM dual) ");*/


    queryfragment.append("  SELECT CLS_NAME.ClsId, CLS_NAME.ClsName Cls, CLS_NAME.Removal_Flag  ");
    queryfragment.append("  FROM ADSE_MERCHANDISE_HIERARCHY cls,                                                                              ");
    queryfragment.append("    XMLTABLE('                                                                                                      ");
    queryfragment.append("    for $cls in $XML_DATA/pim_category/entry                                                                        ");
    queryfragment.append("    return                                                                                                          ");
    queryfragment.append("    <out>                                                                                                           ");
    queryfragment.append("      <cls_id>{$XML_DATA/pim_category/entry/Merchandise_Hier_Spec/Identifiers/RMS_Id}</cls_id>                      ");
    queryfragment.append("      <cls_name>{$XML_DATA/pim_category/entry/Merchandise_Hier_Spec/Name}</cls_name>                                ");
    queryfragment.append("      <dept_id>{fn:tokenize($XML_DATA/pim_category/merchandise_category_header/full_path,\"\\||///\")[5]}</dept_id>    ");
    queryfragment.append("      <Removal_Flag>{$XML_DATA/pim_category/entry/Merchandise_Hier_Spec/System/Removal_Flag}</Removal_Flag>         ");
    queryfragment.append("    </out>'                                                                                                         ");
    queryfragment.append("    passing cls.XML_DATA AS \"XML_DATA\" columns                                                                      ");
    queryfragment.append("      ClsName VARCHAR2(64) path '/out/cls_name',                                                                    ");
    queryfragment.append("      ClsId VARCHAR2(64) path '/out/cls_id',                                                                        ");
    queryfragment.append("      DeptId VARCHAR2(10) path '/out/dept_id',                                                                      ");
    queryfragment.append("      Removal_Flag VARCHAR2(10) path '/out/Removal_Flag') CLS_NAME                                                ");
    //queryfragment.append("    ,Input inp                                                                                                       ");
    queryfragment.append("  WHERE cls.ENTRY_TYPE = 5                                                                                          ");
    queryfragment.append("  AND (CLS_NAME.Removal_Flag = 'false' or CLS_NAME.Removal_Flag is null)                                            ");
    queryfragment.append("  AND deptId in (:deptIdListSql) ");
    //queryfragment.append(getDeptNumbersFormatted(deptids));
    //queryfragment.append("  )                                                                                       "); // 08292016
    queryfragment.append("  ORDER BY ClsId                                                                                                    ");
    
    /**
     * Modification End by AFUAXK4
     * Date: 02/09/2016
     */
    LOGGER.info("getClassQuery"+queryfragment.toString());
    return queryfragment.toString();
       
   }              
            
                       
                        
                   
public String getAdvWorkListDisplayData(AdvanceSearch advSearch) {
    StringBuilder  advQueryFragment = new StringBuilder();
    String depts = null;
    
    //Fix for Defect #185
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd-yyyy");
    Date date = new Date();
    
    if(StringUtils.isNotBlank(advSearch.getDeptNumbers())){
        depts =  "'"+advSearch.getDeptNumbers()+"'";
    }

 String completionFrom = null;
    //Fix for #185
    if(StringUtils.isNotBlank(advSearch.getDateFrom())){
        completionFrom = advSearch.getDateFrom();
        completionFrom = completionFrom.trim();
        try{
                date = formatter1.parse(completionFrom);
                completionFrom = formatter.format(date);
                completionFrom = "'"+completionFrom+"'";
                LOGGER.info("completionFrom----"+completionFrom);
        }catch(Exception pe){
            LOGGER.info("Format parse failed fromDate::");
            pe.printStackTrace();
        }
    }
    
 String completionTo = null;
    
    //Fix for #185
    if(StringUtils.isNotBlank(advSearch.getDateTo())){
        completionTo =  advSearch.getDateTo();
        completionTo = completionTo.trim();
        try{
                date = formatter1.parse(completionTo);
                completionTo = formatter.format(date);
                completionTo = "'"+completionTo+"'";
                LOGGER.info("completionTo----"+completionTo);
        }catch(ParseException pe){
            LOGGER.info("Format parse failed toDate::");
            pe.printStackTrace();
        }
    }
    
 String imageStatus = null;
    
    if(StringUtils.isNotBlank(advSearch.getImageStatus())){
        String finalImageStatus = getImageStatusCode(advSearch.getImageStatus());
        //imageStatus =  "'"+advSearch.getImageStatus()+"'";
        imageStatus =  "'"+finalImageStatus+"'";
    }
String contentStatus = null;
    
    if(StringUtils.isNotBlank(advSearch.getContentStatus())){
        //contentStatus =  "'"+advSearch.getContentStatus()+"'";
        String finalContentStatus = getContentStatusCode(advSearch.getContentStatus());
        contentStatus =  "'"+finalContentStatus+"'";
    }
String petStatus = null;
    
   /* if(StringUtils.isNotBlank(advSearch.getActive())){
        if("yes".equalsIgnoreCase(advSearch.getActive())){
            petStatus =  "'01'"; 
        }else if("no".equalsIgnoreCase(advSearch.getActive())){
            petStatus =  "'05'"; 
        }
    }*/
    if(StringUtils.isNotBlank(advSearch.getActive())){
        petStatus =  "'"+advSearch.getActive()+"'"; 
    }
String requestType = null;
    
    if(StringUtils.isNotBlank(advSearch.getRequestType())){
        requestType =  "'"+advSearch.getRequestType()+"'";
    }
    
    if (null != requestType) {
        /*if (requestType.contains("Direct")) {
            requestType = "'DIRECTFLAG,DROPSHIP'";
        }
        else if (requestType.contains("Manual")) {
            requestType = "'PEP'";
        }
        else if (requestType.contains("Attribute")) {
            requestType = "'SOLDONLINE,Sold-Online'";
        }*/
        requestType = "'" + getSourceStatusCode(requestType) + "'";
    }
    System.out.println("requestType -->"+requestType);
       
String orin = null;
    
    if(StringUtils.isNotBlank(advSearch.getOrin())){
        orin =  "'"+advSearch.getOrin()+"'";
    }
    
String vendorStyle = null;
    
    if(StringUtils.isNotBlank(advSearch.getVendorStyle())){
    	 vendorStyle =  "'%"+advSearch.getVendorStyle().toUpperCase()+"%'";
    }
    
String upc = null;
    
    if(StringUtils.isNotBlank(advSearch.getUpc())){
        upc =  "'"+advSearch.getUpc()+"'";
    }
    
String classes = null;
    
    if(StringUtils.isNotBlank(advSearch.getClassNumber())){
        classes =  "'"+advSearch.getClassNumber()+"'";
    }
    
String createdToday = null;
    
    if(StringUtils.isNotBlank(advSearch.getCreatedToday())){
        createdToday =  "'"+advSearch.getCreatedToday()+"'";
    }
    LOGGER.info("createdToday::inConstants::" +createdToday);
    
String vendorNumber = null;
    
    if(StringUtils.isNotBlank(advSearch.getVendorNumber())){
        vendorNumber =  "'"+advSearch.getVendorNumber()+"'";
    }    

    
    advQueryFragment
            .append("WITH Input(Depts, CompletionFrom, CompletionTo, ImageStatus, ContentStatus, PETStatus, RequestType, ORIN, VENDOR_STYLE, classes, createdToday, vendor, primaryUpc) AS ");
        advQueryFragment.append("  (SELECT ");
        advQueryFragment.append(depts);
        advQueryFragment.append(" Depts, ");
        advQueryFragment.append(completionFrom);
        advQueryFragment.append("  CompletionFrom, ");
        advQueryFragment.append(completionTo);
        advQueryFragment.append("  CompletionTo, ");
        advQueryFragment.append(imageStatus);
        advQueryFragment.append("  ImageStatus, ");
        advQueryFragment.append(contentStatus);
        advQueryFragment.append("  ContentStatus, ");
        advQueryFragment.append(petStatus);
        advQueryFragment.append("  PETStatus, ");
        advQueryFragment.append(requestType);
        advQueryFragment.append("  RequestType, ");
        advQueryFragment.append(orin);
        advQueryFragment.append("  ORIN, ");
        advQueryFragment.append(vendorStyle);
        advQueryFragment.append("  VENDOR_STYLE, ");
        advQueryFragment.append(classes);
        advQueryFragment.append("  classes, ");
        advQueryFragment.append(createdToday);
        advQueryFragment.append("  createdToday, ");
        advQueryFragment.append(vendorNumber);
        advQueryFragment.append("  vendor, ");
        advQueryFragment.append(upc);
        advQueryFragment.append("  primaryUpc ");
        advQueryFragment.append("    FROM ");
        advQueryFragment.append("      dual ");
        advQueryFragment.append("  ) ");
        advQueryFragment.append("  , ");
        advQueryFragment.append("     entryTypeStyleList AS                                                                                                   ");
        advQueryFragment.append("     (                                                                                                                       ");
        advQueryFragment.append("       SELECT                                                                                                                ");
        advQueryFragment.append("         PARENT_MDMID,                                                                                                       ");
        advQueryFragment.append("         MDMID,                                                                                                              ");
        advQueryFragment.append("         XML_DATA,                                                                                                           ");
        advQueryFragment.append("         ENTRY_TYPE,                                                                                                         ");
        advQueryFragment.append("         MOD_DTM,                                                                                                            ");
        advQueryFragment.append("         CASE                                                                                                                ");
        advQueryFragment.append("         when PRIMARY_UPC is null                                                                                            ");
        advQueryFragment.append("         then NUMBER_04                                                                                                      ");
        advQueryFragment.append("          when PRIMARY_UPC = ' '                                                                                             ");
        advQueryFragment.append("          then NUMBER_04                                                                                                     ");
        advQueryFragment.append("         else     PRIMARY_UPC                                                                                                ");
        advQueryFragment.append("         end PRIMARY_UPC,                                                                                                    ");
        advQueryFragment.append("         DEPT_ID, PRIMARY_SUPPLIER_ID Supplier_Id                                                                            ");
        advQueryFragment.append("       FROM                                                                                                                  ");
        advQueryFragment.append("         ADSE_Item_CATALOG, Input inp                                                                                        ");
        advQueryFragment.append("       WHERE                                                                                                                 ");
        advQueryFragment.append("         ENTRY_TYPE = 'Style'                                                                                                ");
        advQueryFragment.append("         AND                                                                                                                 ");
        advQueryFragment.append("         (                                                                                                                   ");
        advQueryFragment.append("           inp.primaryUpc is NULL                                                                                            ");
        advQueryFragment.append("           OR PRIMARY_UPC = inp.primaryUpc                                                                                   ");
        advQueryFragment.append("           OR NUMBER_04 = inp.primaryUpc                                                                                     ");
        advQueryFragment.append("         )                                                                                                                   ");
        advQueryFragment.append("         AND                                                                                                                 ");
        advQueryFragment.append("             (                                                                                                               ");
        advQueryFragment.append("               inp.depts   IS NULL                                                                                           ");
        advQueryFragment.append("             OR DEPT_ID IN                                                                                                   ");
        advQueryFragment.append("               (                                                                                                             ");
        advQueryFragment.append("                 SELECT                                                                                                      ");
        advQueryFragment.append("                   regexp_substr(inp.depts,'[^,]+',1,LEVEL)                                                                  ");
        advQueryFragment.append("                 FROM                                                                                                        ");
        advQueryFragment.append("                   dual                                                                                                      ");
        advQueryFragment.append("                   CONNECT BY regexp_substr(inp.depts,'[^,]+',1,LEVEL) IS NOT NULL                                           ");
        advQueryFragment.append("                )                                                                                                            ");
        advQueryFragment.append("             )                                                                                                               ");
        advQueryFragment.append("           AND                                                                                                               ");
        advQueryFragment.append("             (                                                                                                               ");
        advQueryFragment.append("               inp.ORIN         IS NULL                                                                                      ");
        advQueryFragment.append("             OR MDMID        = inp.ORIN                                                                                      ");
        advQueryFragment.append("             )                                                                                                               ");
        advQueryFragment.append("           AND                                                                                                               ");
        advQueryFragment.append("             (                                                                                                               ");
        advQueryFragment.append("               inp.vendor is NULL                                                                                            ");
        advQueryFragment.append("               OR PRIMARY_SUPPLIER_ID = inp.vendor                                                                           ");
        advQueryFragment.append("             )                                                                                                               ");
        advQueryFragment.append("       UNION ALL                                                                                                             ");
        advQueryFragment.append("             SELECT                                                                                                          ");
        advQueryFragment.append("             PARENT_MDMID,                                                                                                   ");
        advQueryFragment.append("             MDMID,                                                                                                          ");
        advQueryFragment.append("             XML_DATA,                                                                                                       ");
        advQueryFragment.append("             ENTRY_TYPE,                                                                                                     ");
        advQueryFragment.append("             MOD_DTM,                                                                                                        ");
        advQueryFragment.append("             CASE                                                                                                            ");
        advQueryFragment.append("             when PRIMARY_UPC is null                                                                                        ");
        advQueryFragment.append("             then NUMBER_04                                                                                                  ");
        advQueryFragment.append("              when PRIMARY_UPC = ' '                                                                                         ");
        advQueryFragment.append("              then NUMBER_04                                                                                                 ");
        advQueryFragment.append("             else     PRIMARY_UPC                                                                                            ");
        advQueryFragment.append("             end PRIMARY_UPC,                                                                                                ");
        advQueryFragment.append("             DEPT_ID, PRIMARY_SUPPLIER_ID Supplier_Id                                                                        ");
        advQueryFragment.append("           FROM                                                                                                              ");
        advQueryFragment.append("             ADSE_Item_CATALOG, Input inp                                                                                    ");
        advQueryFragment.append("           WHERE                                                                                                             ");
        advQueryFragment.append("               MDMID IN (                                                                                                    ");
        advQueryFragment.append("                   SELECT PARENT_MDMID FROM ADSE_Item_CATALOG WHERE ENTRY_TYPE = 'SKU'                                       ");
        advQueryFragment.append("                   AND inp.primaryUpc is NOT NULL AND PRIMARY_UPC = inp.primaryUpc                                           ");
        advQueryFragment.append("               )                                                                                                             ");
        advQueryFragment.append("     ),                                                                                                                      ");
        advQueryFragment.append("     itemGroup as(                                                                                                           ");
        advQueryFragment.append("       SELECT                                                                                                                ");
        advQueryFragment.append("         PARENT_MDMID,                                                                                                       ");
        advQueryFragment.append("         MDMID,                                                                                                              ");
        advQueryFragment.append("         XML_DATA,                                                                                                           ");
        advQueryFragment.append("         ENTRY_TYPE,                                                                                                         ");
        advQueryFragment.append("         MOD_DTM,                                                                                                            ");
        advQueryFragment.append("         PRIMARY_UPC,                                                                                                        ");
        advQueryFragment.append("         DEPT_ID, Supplier_Id,                                                                                               ");
        advQueryFragment.append("         inp.PETStatus INPUTPETSTATUS,                                                                                       ");
        advQueryFragment.append("         inp.VENDOR_STYLE VSTYLE,                                                                                            ");
        advQueryFragment.append("         inp.classes,                                                                                                        ");
        advQueryFragment.append("         inp.CompletionFrom,inp.CompletionTo,inp.ImageStatus inpImageStatus,                                                 ");
        advQueryFragment.append("         inp.ContentStatus inpContentStatus,inp.RequestType,inp.createdToday                                                 ");
        advQueryFragment.append("       FROM                                                                                                                  ");
        advQueryFragment.append("         (                                                                                                                   ");
        advQueryFragment.append("           SELECT                                                                                                            ");
        advQueryFragment.append("             PARENT_MDMID,                                                                                                   ");
        advQueryFragment.append("             MDMID,                                                                                                          ");
        advQueryFragment.append("             XML_DATA,                                                                                                       ");
        advQueryFragment.append("             ENTRY_TYPE,                                                                                                     ");
        advQueryFragment.append("             MOD_DTM,                                                                                                        ");
        advQueryFragment.append("             PRIMARY_UPC,                                                                                                    ");
        advQueryFragment.append("             DEPT_ID, Supplier_Id                                                                                            ");
        advQueryFragment.append("           FROM                                                                                                              ");
        advQueryFragment.append("             entryTypeStyleList                                                                                              ");
        advQueryFragment.append("       UNION ALL                                                                                                             ");
        advQueryFragment.append("           SELECT                                                                                                            ");
        advQueryFragment.append("             PARENT_MDMID,                                                                                                   ");
        advQueryFragment.append("             MDMID,                                                                                                          ");
        advQueryFragment.append("             XML_DATA,                                                                                                       ");
        advQueryFragment.append("             ENTRY_TYPE,                                                                                                     ");
        advQueryFragment.append("             MOD_DTM,                                                                                                        ");
        advQueryFragment.append("             CASE                                                                                                            ");
        advQueryFragment.append("             when PRIMARY_UPC is null                                                                                        ");
        advQueryFragment.append("             then NUMBER_04                                                                                                  ");
        advQueryFragment.append("              when PRIMARY_UPC = ' '                                                                                         ");
        advQueryFragment.append("              then NUMBER_04                                                                                                 ");
        advQueryFragment.append("             else     PRIMARY_UPC                                                                                            ");
        advQueryFragment.append("             end PRIMARY_UPC,                                                                                                ");
        advQueryFragment.append("             DEPT_ID, PRIMARY_SUPPLIER_ID Supplier_Id                                                                        ");
        advQueryFragment.append("           FROM                                                                                                              ");
        advQueryFragment.append("             ADSE_ITEM_CATALOG                                                                                               ");
        advQueryFragment.append("           WHERE                                                                                                             ");
        advQueryFragment.append("             PARENT_MDMID IN (SELECT MDMID FROM entryTypeStyleList)                                                          ");
        advQueryFragment.append("             AND ENTRY_TYPE IN ('StyleColor')                                                                                ");
        advQueryFragment.append("         ) aic, Input inp                                                                                                    ");
        advQueryFragment.append("      )                                                                                                                      ");
        advQueryFragment.append("    ,styleID_DescA AS(                                                                                                       ");
        advQueryFragment.append("       SELECT                                                                                                                ");
        advQueryFragment.append("         sl.PARENT_MDMID,                                                                                                    ");
        advQueryFragment.append("         sl.MDMID,                                                                                                           ");
        advQueryFragment.append("         sl.XML_DATA,                                                                                                        ");
        advQueryFragment.append("         sl.ENTRY_TYPE,                                                                                                      ");
        advQueryFragment.append("         sl.PRIMARY_UPC,                                                                                                     ");
        advQueryFragment.append("         sl.Supplier_Id,                                                                                                     ");
        advQueryFragment.append("         sl.DEPT_ID,                                                                                                         ");
        advQueryFragment.append("         UPPER(ia.ven_style),                                                                                                       ");
        advQueryFragment.append("         ia.clsId,                                                                                                           ");
        advQueryFragment.append("         sl.INPUTPETSTATUS,                                                                                                  ");
        advQueryFragment.append("         sl.VSTYLE,                                                                                                          ");
        advQueryFragment.append("         sl.classes,                                                                                                         ");
        advQueryFragment.append("         sl.CompletionFrom,sl.CompletionTo,sl.inpImageStatus,sl.inpContentStatus,sl.RequestType,sl.createdToday              ");
        advQueryFragment.append("       FROM                                                                                                                  ");
        advQueryFragment.append("         itemGroup sl,                                                                                                       ");
        advQueryFragment.append("         XMLTABLE(                                                                                                           ");
        advQueryFragment.append("         'for $i in $XML_DATA/pim_entry/entry/Item_Ctg_Spec/Supplier                                                         ");
        advQueryFragment.append("         let                                                                                                                 ");
        advQueryFragment.append("         $marchant_id := fn:tokenize($XML_DATA/pim_entry/item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path, \"\\||///\"), ");
        advQueryFragment.append("         $ven_style := $i/VPN/text(),                                                                                         ");
        advQueryFragment.append("         $primary_flag := $i/Primary_Flag/text()                                                                              ");
        advQueryFragment.append("         return                                                                                                               ");
        advQueryFragment.append("         <out>                                                                                                                ");
        advQueryFragment.append("           <cls_id>{$marchant_id[6]}</cls_id>                                                                                 ");
        advQueryFragment.append("           <ven_style>{$ven_style}</ven_style>                                                                                ");
        advQueryFragment.append("           <primary_flag>{$primary_flag}</primary_flag>                                                                       ");
        advQueryFragment.append("         </out>'                                                                                                              ");
        advQueryFragment.append("         passing sl.XML_DATA AS \"XML_DATA\" columns                                                                            ");
        advQueryFragment.append("         clsId VARCHAR(10) path  '/out/cls_id',                                                                               ");
        advQueryFragment.append("         ven_style VARCHAR2(100) path '/out/ven_style',                                                                       ");
        advQueryFragment.append("         primary_flag VARCHAR(25) path '/out/primary_flag') ia                                                                ");
        advQueryFragment.append("       WHERE primary_flag = 'true'                                                                                            ");
        advQueryFragment.append("      ),                                                                                                                      ");
        advQueryFragment.append("     styleID_DescAA AS(                                                                                                       ");
        advQueryFragment.append("       SELECT                                                                                                                 ");
        advQueryFragment.append("         sda.PARENT_MDMID,                                                                                                    ");
        advQueryFragment.append("         sda.MDMID,                                                                                                           ");
        advQueryFragment.append("         sda.XML_DATA,                                                                                                        ");
        advQueryFragment.append("         sda.ENTRY_TYPE,                                                                                                      ");
        advQueryFragment.append("         sda.PRIMARY_UPC,                                                                                                     ");
        advQueryFragment.append("         p.COLO_DESC,                                                                                                         ");
        advQueryFragment.append("         p.COLO_CODE,                                                                                                         ");
        advQueryFragment.append("         sda.Supplier_Id,                                                                                                     ");
        advQueryFragment.append("         sda.DEPT_ID,                                                                                                         ");
        advQueryFragment.append("         p.PRODUCT_NAME,                                                                                                      ");
        advQueryFragment.append("         sda.ven_style,                                                                                                       ");
        advQueryFragment.append("         sda.clsId,                                                                                                           ");
        advQueryFragment.append("         sda.INPUTPETSTATUS,                                                                                                  ");
        advQueryFragment.append("         sda.VSTYLE,                                                                                                          ");
        advQueryFragment.append("         sda.classes,                                                                                                         ");
        advQueryFragment.append("         sda.CompletionFrom,sda.CompletionTo,sda.inpImageStatus,sda.inpContentStatus,sda.RequestType,sda.createdToday,        ");
        advQueryFragment.append("         p.completion_date,                                                                                                   ");
        advQueryFragment.append("         p.req_type,                                                                                                          ");
        advQueryFragment.append("         pet.pet_state PETSTATUS,                                                                                             ");
        advQueryFragment.append("         pet.image_status ImageState,                                                                                         ");
        advQueryFragment.append("         pet.content_status CONTENTSTATUS                                                                                     ");
        advQueryFragment.append("   FROM                                                                                                                       ");
        advQueryFragment.append("     styleID_DescA sda,                                                                                                       ");
        advQueryFragment.append("     ADSE_PET_CATALOG pet,                                                                                                    ");
        advQueryFragment.append("     XMLTABLE(                                                                                                                ");
        advQueryFragment.append("     'let                                                                                                                     ");
        advQueryFragment.append("     $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date,                                                                ");
        advQueryFragment.append("     $colordesc:= $pets/pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,                                          ");
        advQueryFragment.append("     $colorcode:= $pets/pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code                                                  ");
        advQueryFragment.append("     return                                                                                                                   ");
        advQueryFragment.append("     <out>                                                                                                                    ");
        advQueryFragment.append("         <completion_date>{$completionDate}</completion_date>                                                                 ");
        advQueryFragment.append("         <req_type>{$pets/pim_entry/entry/SourceSystem}</req_type>                                                            ");
        advQueryFragment.append("         <PRODUCT_NAME>{$pets/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>                                                    ");
        advQueryFragment.append("         <COLO_DESC>{$colordesc}</COLO_DESC>                                                                                  ");
        advQueryFragment.append("         <COLO_CODE>{$colorcode}</COLO_CODE>                                                                                  ");
        advQueryFragment.append("     </out>'                                                                                                                  ");
        advQueryFragment.append("     passing pet.xml_data AS \"pets\" Columns                                                                                   ");
        advQueryFragment.append("         completion_date      VARCHAR2(10) path  '/out/completion_date',                                                      ");
        advQueryFragment.append("         req_type             VARCHAR2(20) path '/out/req_type',                                                              ");
        advQueryFragment.append("         PRODUCT_NAME         VARCHAR2(40) path '/out/PRODUCT_NAME',                                                          ");
        advQueryFragment.append("         COLO_CODE            VARCHAR2(50)  path '/out/COLO_CODE',                                                            ");
        advQueryFragment.append("         COLO_DESC            VARCHAR2(50)  path '/out/COLO_DESC'                                                             ");
        advQueryFragment.append("     )p                                                                                                                       ");
        advQueryFragment.append("   WHERE                                                                                                                      ");
        advQueryFragment.append("     sda.MDMID     =pet.mdmid                                                                                                 ");
        advQueryFragment.append("   /*AND sda.ENTRY_TYPE = typInd.typ*/                                                                                        ");
        advQueryFragment.append("   AND                                                                                                                        ");
        advQueryFragment.append("     (                                                                                                                        ");
        advQueryFragment.append("       CompletionFrom IS NULL                                                                                                 ");
        advQueryFragment.append("     OR completion_date   >=CompletionFrom                                                                                    ");
        advQueryFragment.append("     )                                                                                                                        ");
        advQueryFragment.append("   AND                                                                                                                        ");
        advQueryFragment.append("     (                                                                                                                        ");
        advQueryFragment.append("       CompletionTo IS NULL                                                                                                   ");
        advQueryFragment.append("     OR completion_date <=CompletionTo                                                                                        ");
        advQueryFragment.append("     )                                                                                                                        ");
        advQueryFragment.append("   AND                                                                                                                        ");
        advQueryFragment.append("     (                                                                                                                        ");
        advQueryFragment.append("       inpImageStatus IS NULL                                                                                                 ");
        advQueryFragment.append("     OR pet.image_status   IN                                                                                                 ");
        advQueryFragment.append("       to_char((                                                                                                                      ");
        advQueryFragment.append("         SELECT                                                                                                               ");
        advQueryFragment.append("           regexp_substr(inpImageStatus,'[^,]+',1,LEVEL)                                                                      ");
        advQueryFragment.append("         FROM                                                                                                                 ");
        advQueryFragment.append("           dual                                                                                                               ");
        advQueryFragment.append("           CONNECT BY regexp_substr(inpImageStatus,'[^,]+',1,LEVEL) IS NOT NULL                                               ");
        advQueryFragment.append("       ))                                                                                                                      ");
        advQueryFragment.append("     )                                                                                                                        ");
        advQueryFragment.append("   AND                                                                                                                        ");
        advQueryFragment.append("     (                                                                                                                        ");
        advQueryFragment.append("       inpContentStatus IS NULL                                                                                               ");
        advQueryFragment.append("     OR pet.content_status    IN                                                                                              ");
        advQueryFragment.append("       to_char((                                                                                                                      ");
        advQueryFragment.append("         SELECT                                                                                                               ");
        advQueryFragment.append("           regexp_substr(inpContentStatus,'[^,]+',1,LEVEL)                                                                    ");
        advQueryFragment.append("         FROM                                                                                                                 ");
        advQueryFragment.append("           dual                                                                                                               ");
        advQueryFragment.append("           CONNECT BY regexp_substr(inpContentStatus,'[^,]+',1,LEVEL) IS NOT NULL                                             ");
        advQueryFragment.append("       ) )                                                                                                                     ");
        advQueryFragment.append("     )                                                                                                                        ");
        advQueryFragment.append("   AND                                                                                                                        ");
        advQueryFragment.append("     (                                                                                                                        ");
        advQueryFragment.append("       RequestType IS NULL                                                                                                    ");
        advQueryFragment.append("     OR Req_Type       IN                                                                                                     ");
        advQueryFragment.append("       to_char((                                                                                                                      ");
        advQueryFragment.append("         SELECT                                                                                                               ");
        advQueryFragment.append("           regexp_substr(RequestType,'[^,]+',1,LEVEL)                                                                         ");
        advQueryFragment.append("         FROM                                                                                                                 ");
        advQueryFragment.append("           dual                                                                                                               ");
        advQueryFragment.append("           CONNECT BY regexp_substr(RequestType,'[^,]+',1,LEVEL) IS NOT NULL                                                  ");
        advQueryFragment.append("       ) )                                                                                                                     ");
        advQueryFragment.append("     )                                                                                                                        ");
        advQueryFragment.append("   AND                                                                                                                        ");
        advQueryFragment.append("     (                                                                                                                        ");
        advQueryFragment.append("       createdToday                           IS NULL                                                                         ");
        advQueryFragment.append("     OR createdToday                          <>'yes'                                                                         ");
        advQueryFragment.append("     OR to_date(SUBSTR(pet.CREATED_DTM, 1, 9), 'YYYY-MM-DD') = to_date(SUBSTR(CURRENT_TIMESTAMP, 1, 9), 'YYYY-MM-DD')/*'09-NOV-15' CURRENT_DATE*/ ");
        advQueryFragment.append("     )                                                                                                                         ");
        advQueryFragment.append("   ),                                                                                                                          ");
        advQueryFragment.append("   supplierDetails AS                                                                                                          ");
        advQueryFragment.append("     (                                                                                                                         ");
        advQueryFragment.append("       SELECT                                                                                                                  ");
        advQueryFragment.append("         orin.PARENT_MDMID,                                                                                                    ");
        advQueryFragment.append("         orin.MDMID,                                                                                                           ");
        advQueryFragment.append("         orin.ENTRY_TYPE,                                                                                                      ");
        advQueryFragment.append("           orin.COLO_CODE,                                                                                                     ");
        advQueryFragment.append("         orin.COLO_DESC,                                                                                                       ");
        advQueryFragment.append("         orin.DEPT_Id,                                                                                                         ");
        advQueryFragment.append("         orin.PRODUCT_NAME,                                                                                                    ");
        advQueryFragment.append("         orin.ven_style,                                                                                                       ");
        advQueryFragment.append("         orin.Supplier_Id,                                                                                                     ");
        advQueryFragment.append("         orin.PRIMARY_UPC,                                                                                                     ");
        advQueryFragment.append("         orin.clsId,                                                                                                           ");
        advQueryFragment.append("         orin.INPUTPETSTATUS,                                                                                                  ");
        advQueryFragment.append("         orin.VSTYLE,                                                                                                          ");
        advQueryFragment.append("         orin.classes,                                                                                                         ");
        advQueryFragment.append("         orin.completion_date,                                                                                                 ");
        advQueryFragment.append("         orin.req_type,                                                                                                        ");
        advQueryFragment.append("         orin.ImageState,                                                                                                      ");
        advQueryFragment.append("         orin.CONTENTSTATUS,                                                                                                   ");
        advQueryFragment.append("         orin.PETSTATUS,                                                                                                       ");
        advQueryFragment.append("         s.ven_name,                                                                                                           ");
        advQueryFragment.append("         s.omniChannelIndicator                                                                                                ");
        advQueryFragment.append("       FROM                                                                                                                    ");
        advQueryFragment.append("         styleID_DescAA orin,                                                                                                  ");
        advQueryFragment.append("         ADSE_SUPPLIER_CATALOG supplier,                                                                                       ");
        advQueryFragment.append("         XMLTABLE(                                                                                                             ");
        advQueryFragment.append("         'let                                                                                                                  ");
        advQueryFragment.append("         $ven_name := $supplierCatalog/pim_entry/entry/Supplier_Ctg_Spec/Name,                                                 ");
        advQueryFragment.append("         $isOmniChannel := $supplierCatalog/pim_entry/entry/Supplier_Site_Spec/Omni_Channel                                    ");
        advQueryFragment.append("         return                                                                                                                ");
        advQueryFragment.append("         <out>                                                                                                                 ");
        advQueryFragment.append("             <ven_name>{$ven_name}</ven_name>                                                                                  ");
        advQueryFragment.append("             <omni_channel>{if($isOmniChannel eq \"true\") then \"Y\" else \"N\"}</omni_channel>                                     ");
        advQueryFragment.append("         </out>'                                                                                                               ");
        advQueryFragment.append("         PASSING XMLELEMENT(\"Id\", orin.Supplier_Id) AS \"Suppliers\",                                                            ");
        advQueryFragment.append("         supplier.XML_DATA                          AS \"supplierCatalog\" columns                                               ");
        advQueryFragment.append("         ven_name                          VARCHAR2(80) path '/out/ven_name',                                                  ");
        advQueryFragment.append("         omniChannelIndicator VARCHAR2      (2) path '/out/omni_channel') s                                                    ");
        advQueryFragment.append("       WHERE                                                                                                                   ");
        advQueryFragment.append("       orin.Supplier_Id = supplier.mdmid                                                                                       ");
        advQueryFragment.append("      ),                                                                                                                       ");
        advQueryFragment.append("     styleID_Desc AS(                                                                                                          ");
        advQueryFragment.append("       SELECT                                                                                                                  ");
        advQueryFragment.append("         sia.PARENT_MDMID,                                                                                                     ");
        advQueryFragment.append("         sia.MDMID ORIN_NUM,                                                                                                   ");
        advQueryFragment.append("         sia.ENTRY_TYPE,                                                                                                       ");
        advQueryFragment.append("         sia.COLO_DESC,                                                                                                        ");
        advQueryFragment.append("         sia.COLO_CODE,                                                                                                        ");
        advQueryFragment.append("         sia.Supplier_Id,                                                                                                      ");
        advQueryFragment.append("         sia.DEPT_ID,                                                                                                          ");
        advQueryFragment.append("         sia.PRODUCT_NAME,                                                                                                     ");
        advQueryFragment.append("         UPPER(sia.ven_style),                                                                                                        ");
        advQueryFragment.append("         sia.PRIMARY_UPC,                                                                                                      ");
        advQueryFragment.append("         sia.clsId,                                                                                                            ");
        advQueryFragment.append("         sia.INPUTPETSTATUS,                                                                                                   ");
        advQueryFragment.append("         sia.VSTYLE,                                                                                                           ");
        advQueryFragment.append("         sia.classes,                                                                                                          ");
        advQueryFragment.append("         sia.completion_date,                                                                                                  ");
        advQueryFragment.append("         sia.req_type,                                                                                                         ");
        advQueryFragment.append("         sia.ImageState,                                                                                                       ");
        advQueryFragment.append("         sia.CONTENTSTATUS,                                                                                                    ");
        advQueryFragment.append("         sia.PETSTATUS,                                                                                                        ");
        advQueryFragment.append("         sia.ven_name,                                                                                                         ");
        advQueryFragment.append("         sia.omniChannelIndicator                                                                                              ");
        advQueryFragment.append("       from supplierDetails sia                                                                                                ");
        advQueryFragment.append("       where                                                                                                                   ");
        advQueryFragment.append("           (sia.Entry_Type='Style'                                                                                             ");
        advQueryFragment.append("           AND (INPUTPETSTATUS IS NULL OR sia.PETSTATUS = INPUTPETSTATUS) AND (VSTYLE IS NULL OR VEN_STYLE like UPPER(VSTYLE)))          ");
        advQueryFragment.append("         OR                                                                                                                    ");
        advQueryFragment.append("           (entry_type = 'StyleColor'                                                                                          ");
        advQueryFragment.append("           AND PARENT_MDMID IN                                                                                                 ");
        advQueryFragment.append("               (SELECT MDMID   FROM supplierDetails WHERE ENTRY_TYPE='Style' AND                                               ");
        advQueryFragment.append("                 (INPUTPETSTATUS IS NULL OR PETSTATUS = INPUTPETSTATUS) AND (VSTYLE IS NULL OR VEN_STYLE like UPPER(VSTYLE))             ");
        advQueryFragment.append("               )                                                                                                               ");
        advQueryFragment.append("           AND (INPUTPETSTATUS IS NULL OR PETSTATUS = INPUTPETSTATUS))                                                         ");
        advQueryFragment.append("     )                                                                                                                         ");
        advQueryFragment.append("     SELECT                                                                                                                    ");
        advQueryFragment.append("         CASE                                                                                                                  ");
        advQueryFragment.append("         WHEN LENGTH(ORIN_NUM) > 9                                                                                             ");
        advQueryFragment.append("           THEN SUBSTR(ORIN_NUM,1,9)                                                                                           ");
        advQueryFragment.append("           ELSE ORIN_NUM                                                                                                       ");
        advQueryFragment.append("         END Parent_Style_Orin,                                                                                                ");
        advQueryFragment.append("         ORIN_NUM,                                                                                                             ");
        advQueryFragment.append("         DEPT_ID,                                                                                                              ");
        advQueryFragment.append("         Supplier_Id,                                                                                                          ");
        advQueryFragment.append("         PRODUCT_NAME,                                                                                                         ");
        advQueryFragment.append("         ENTRY_TYPE,                                                                                                           ");
        advQueryFragment.append("         PRIMARY_UPC,                                                                                                          ");
        advQueryFragment.append("         VEN_NAME,                                                                                                             ");
        advQueryFragment.append("         VEN_STYLE,                                                                                                            ");
        advQueryFragment.append("         clsId,                                                                                                                ");
        advQueryFragment.append("         PETSTATUS,                                                                                                            ");
        advQueryFragment.append("         ImageState,                                                                                                           ");
        advQueryFragment.append("         CONTENTSTATUS,                                                                                                        ");
        advQueryFragment.append("         completion_date,                                                                                                      ");
        advQueryFragment.append("         omniChannelIndicator,                                                                                                 ");
        advQueryFragment.append("         req_type,                                                                                                             ");
        advQueryFragment.append("         COLO_DESC                                                                                                  ");
        advQueryFragment.append("     FROM styleID_Desc                                                                                                         ");
        advQueryFragment.append("     WHERE                                                                                                                     ");
        advQueryFragment.append("         (                                                                                                                     ");
        advQueryFragment.append("           classes IS NULL                                                                                                     ");
        advQueryFragment.append("          OR clsId  IN                                                                                                         ");
        advQueryFragment.append("           to_char((                                                                                                                   ");
        advQueryFragment.append("             SELECT                                                                                                            ");
        advQueryFragment.append("               regexp_substr(classes,'[^,]+',1,LEVEL)                                                                          ");
        advQueryFragment.append("             FROM                                                                                                              ");
        advQueryFragment.append("               dual                                                                                                            ");
        advQueryFragment.append("               CONNECT BY regexp_substr(classes,'[^,]+',1,LEVEL) IS NOT NULL                                                   ");
        advQueryFragment.append("           ))                                                                                                                   ");
        advQueryFragment.append("         )                                                                                                                     ");



    LOGGER.info("getAdvWorkListDisplayData Query"+advQueryFragment.toString());
    
    
    return advQueryFragment.toString();
    
    
 }                   
   
public String getAdvWorkListDisplayDataComplexPack(AdvanceSearch advSearch) {
    StringBuilder  advQueryFragment = new StringBuilder();
    String depts = null;
    
    //Fix for Defect #185
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd-yyyy");
    Date date = new Date();
    
    if(StringUtils.isNotBlank(advSearch.getDeptNumbers())){
        depts =  "'"+advSearch.getDeptNumbers()+"'";
    }

 String completionFrom = null;
    //Fix for #185
    if(StringUtils.isNotBlank(advSearch.getDateFrom())){
        completionFrom = advSearch.getDateFrom();
        completionFrom = completionFrom.trim();
        try{
                date = formatter1.parse(completionFrom);
                completionFrom = formatter.format(date);
                completionFrom = "'"+completionFrom+"'";
                LOGGER.info("completionFrom----"+completionFrom);
        }catch(Exception pe){
            LOGGER.info("Format parse failed fromDate::");
            pe.printStackTrace();
        }
    }
    
 String completionTo = null;
    
    //Fix for #185
    if(StringUtils.isNotBlank(advSearch.getDateTo())){
        completionTo =  advSearch.getDateTo();
        completionTo = completionTo.trim();
        try{
                date = formatter1.parse(completionTo);
                completionTo = formatter.format(date);
                completionTo = "'"+completionTo+"'";
                LOGGER.info("completionTo----"+completionTo);
        }catch(ParseException pe){
            LOGGER.info("Format parse failed toDate::");
            pe.printStackTrace();
        }
    }
    
 String imageStatus = null;
    if(StringUtils.isNotBlank(advSearch.getImageStatus())){
        String finalImageStatus = getImageStatusCode(advSearch.getImageStatus());
        //imageStatus =  "'"+advSearch.getImageStatus()+"'";
        imageStatus =  "'"+finalImageStatus+"'";
    }
String contentStatus = null;
    if(StringUtils.isNotBlank(advSearch.getContentStatus())){
        //contentStatus =  "'"+advSearch.getContentStatus()+"'";
        String finalContentStatus = getContentStatusCode(advSearch.getContentStatus());
        contentStatus =  "'"+finalContentStatus+"'";
    }
    
String petStatus = null;
    
   /* if(StringUtils.isNotBlank(advSearch.getActive())){
        if("yes".equalsIgnoreCase(advSearch.getActive())){
            petStatus =  "'01'"; 
        }else if("no".equalsIgnoreCase(advSearch.getActive())){
            petStatus =  "'05'"; 
        }
    }*/
    if(StringUtils.isNotBlank(advSearch.getActive())){
        petStatus =  "'"+advSearch.getActive()+"'"; 
    }
String requestType = null;
    
    if(StringUtils.isNotBlank(advSearch.getRequestType())){
        requestType =  "'"+advSearch.getRequestType()+"'";
    }
    

    if (null != requestType) {
        /*if (requestType.contains("Direct")) {
            requestType = "'DIRECTFLAG,DROPSHIP'";
        }
        else if (requestType.contains("Manual")) {
            requestType = "'PEP'";
        }
        else if (requestType.contains("Attribute")) {
            requestType = "'SOLDONLINE,Sold-Online'";
        }*/
        requestType = "'" + getSourceStatusCode(requestType) + "'";
    }
    System.out.println("requestType -->"+requestType);
       
String orin = null;
    
    if(StringUtils.isNotBlank(advSearch.getOrin())){
        orin =  "'"+advSearch.getOrin()+"'";
    }
    
String vendorStyle = null;
    
    if(StringUtils.isNotBlank(advSearch.getVendorStyle())){
        vendorStyle =  "'%"+advSearch.getVendorStyle().toUpperCase()+"%'";
    }
    
String upc = null;
    
    if(StringUtils.isNotBlank(advSearch.getUpc())){
        upc =  "'"+advSearch.getUpc()+"'";
    }
    
String classes = null;
    
    if(StringUtils.isNotBlank(advSearch.getClassNumber())){
        classes =  "'"+advSearch.getClassNumber()+"'";
    }
    
String createdToday = null;
    
    if(StringUtils.isNotBlank(advSearch.getCreatedToday())){
        createdToday =  "'"+advSearch.getCreatedToday()+"'";
    }
    LOGGER.info("createdToday::inConstants::" +createdToday);
    
String vendorNumber = null;
    
    if(StringUtils.isNotBlank(advSearch.getVendorNumber())){
        vendorNumber =  "'"+advSearch.getVendorNumber()+"'";
    }    

    
    advQueryFragment
            .append("WITH Input(Depts, CompletionFrom, CompletionTo, ImageStatus, ContentStatus, PETStatus, RequestType, ORIN, VENDOR_STYLE, classes, createdToday, vendor, primaryUpc) AS ");
        advQueryFragment.append("  (SELECT ");
        advQueryFragment.append(depts);
        advQueryFragment.append(" Depts, ");
        advQueryFragment.append(completionFrom);
        advQueryFragment.append("  CompletionFrom, ");
        advQueryFragment.append(completionTo);
        advQueryFragment.append("  CompletionTo, ");
        advQueryFragment.append(imageStatus);
        advQueryFragment.append("  ImageStatus, ");
        advQueryFragment.append(contentStatus);
        advQueryFragment.append("  ContentStatus, ");
        advQueryFragment.append(petStatus);
        advQueryFragment.append("  PETStatus, ");
        advQueryFragment.append(requestType);
        advQueryFragment.append("  RequestType, ");
        advQueryFragment.append(orin);
        advQueryFragment.append("  ORIN, ");
        advQueryFragment.append(vendorStyle);
        advQueryFragment.append("  VENDOR_STYLE, ");
        advQueryFragment.append(classes);
        advQueryFragment.append("  classes, ");
        advQueryFragment.append(createdToday);
        advQueryFragment.append("  createdToday, ");
        advQueryFragment.append(vendorNumber);
        advQueryFragment.append("  vendor, ");
        advQueryFragment.append(upc);
        advQueryFragment.append("  primaryUpc ");
        advQueryFragment.append("    FROM ");
        advQueryFragment.append("      dual ");
        advQueryFragment.append("  ) ");
        advQueryFragment.append("  , ");  
        advQueryFragment.append("     entryTypeStyleList AS                                                                                        ");
        advQueryFragment.append("     (                                                                                                            ");
        advQueryFragment.append("       SELECT                                                                                                     ");
        advQueryFragment.append("         PARENT_MDMID,                                                                                            ");
        advQueryFragment.append("         MDMID,                                                                                                   ");
        advQueryFragment.append("         XML_DATA,                                                                                                ");
        advQueryFragment.append("         ENTRY_TYPE,                                                                                              ");
        advQueryFragment.append("         MOD_DTM,                                                                                                 ");
        advQueryFragment.append("         CASE                                                                                                     ");
        advQueryFragment.append("         when PRIMARY_UPC is null                                                                                 ");
        advQueryFragment.append("         then NUMBER_04                                                                                           ");
        advQueryFragment.append("          when PRIMARY_UPC = ' '                                                                                  ");
        advQueryFragment.append("          then NUMBER_04                                                                                          ");
        advQueryFragment.append("         else     PRIMARY_UPC                                                                                     ");
        advQueryFragment.append("         end PRIMARY_UPC,                                                                                         ");
        advQueryFragment.append("         DEPT_ID, PRIMARY_SUPPLIER_ID Supplier_Id                                                                 ");
        advQueryFragment.append("       FROM                                                                                                       ");
        advQueryFragment.append("         ADSE_Item_CATALOG, Input inp                                                                             ");
        advQueryFragment.append("       WHERE                                                                                                      ");
        advQueryFragment.append("         ENTRY_TYPE = 'Complex Pack'                                                                              ");
        advQueryFragment.append("         AND                                                                                                      ");
        advQueryFragment.append("         (                                                                                                        ");
        advQueryFragment.append("           inp.primaryUpc is NULL                                                                                 ");
        advQueryFragment.append("           OR PRIMARY_UPC = inp.primaryUpc                                                                        ");
        advQueryFragment.append("           OR NUMBER_04 = inp.primaryUpc                                                                          ");
        advQueryFragment.append("         )                                                                                                        ");
        advQueryFragment.append("         AND                                                                                                      ");
        advQueryFragment.append("         (                                                                                                        ");
        advQueryFragment.append("           inp.depts   IS NULL                                                                                    ");
        advQueryFragment.append("         OR DEPT_ID IN                                                                                            ");
        advQueryFragment.append("           (                                                                                                      ");
        advQueryFragment.append("             SELECT                                                                                               ");
        advQueryFragment.append("               regexp_substr(inp.depts,'[^,]+',1,LEVEL)                                                           ");
        advQueryFragment.append("             FROM                                                                                                 ");
        advQueryFragment.append("               dual                                                                                               ");
        advQueryFragment.append("               CONNECT BY regexp_substr(inp.depts,'[^,]+',1,LEVEL) IS NOT NULL                                    ");
        advQueryFragment.append("           )                                                                                                      ");
        advQueryFragment.append("         )                                                                                                        ");
        advQueryFragment.append("           AND                                                                                                    ");
        advQueryFragment.append("             (                                                                                                    ");
        advQueryFragment.append("               inp.ORIN         IS NULL                                                                           ");
        advQueryFragment.append("               OR MDMID        = inp.ORIN                                                                         ");
        advQueryFragment.append("             )                                                                                                    ");
        advQueryFragment.append("           AND                                                                                                    ");
        advQueryFragment.append("             (                                                                                                    ");
        advQueryFragment.append("               inp.vendor is NULL                                                                                 ");
        advQueryFragment.append("               OR PRIMARY_SUPPLIER_ID = inp.vendor                                                                ");
        advQueryFragment.append("             )                                                                                                    ");
        advQueryFragment.append("       UNION ALL                                                                                                  ");
        advQueryFragment.append("             SELECT                                                                                               ");
        advQueryFragment.append("             PARENT_MDMID,                                                                                        ");
        advQueryFragment.append("             MDMID,                                                                                               ");
        advQueryFragment.append("             XML_DATA,                                                                                            ");
        advQueryFragment.append("             ENTRY_TYPE,                                                                                          ");
        advQueryFragment.append("             MOD_DTM,                                                                                             ");
        advQueryFragment.append("             CASE                                                                                                 ");
        advQueryFragment.append("             when PRIMARY_UPC is null                                                                             ");
        advQueryFragment.append("             then NUMBER_04                                                                                       ");
        advQueryFragment.append("              when PRIMARY_UPC = ' '                                                                              ");
        advQueryFragment.append("              then NUMBER_04                                                                                      ");
        advQueryFragment.append("             else     PRIMARY_UPC                                                                                 ");
        advQueryFragment.append("             end PRIMARY_UPC,                                                                                     ");
        advQueryFragment.append("             DEPT_ID, PRIMARY_SUPPLIER_ID Supplier_Id                                                             ");
        advQueryFragment.append("           FROM                                                                                                   ");
        advQueryFragment.append("             ADSE_Item_CATALOG, Input inp                                                                         ");
        advQueryFragment.append("           WHERE                                                                                                  ");
        advQueryFragment.append("               MDMID IN (                                                                                         ");
        advQueryFragment.append("                   SELECT PARENT_MDMID FROM ADSE_Item_CATALOG WHERE ENTRY_TYPE = 'SKU'                            ");
        advQueryFragment.append("                   AND inp.primaryUpc is NOT NULL AND PRIMARY_UPC = inp.primaryUpc                                ");
        advQueryFragment.append("               )                                                                                                  ");
        advQueryFragment.append("     ),                                                                                                           ");
        advQueryFragment.append("     itemGroup as(                                                                                                ");
        advQueryFragment.append("       SELECT                                                                                                     ");
        advQueryFragment.append("         PARENT_MDMID,                                                                                            ");
        advQueryFragment.append("         MDMID,                                                                                                   ");
        advQueryFragment.append("         XML_DATA,                                                                                                ");
        advQueryFragment.append("         ENTRY_TYPE,                                                                                              ");
        advQueryFragment.append("         MOD_DTM,                                                                                                 ");
        advQueryFragment.append("         PRIMARY_UPC,                                                                                             ");
        advQueryFragment.append("         DEPT_ID, Supplier_Id,                                                                                    ");
        advQueryFragment.append("         inp.PETStatus INPUTPETSTATUS,                                                                            ");
        advQueryFragment.append("         inp.VENDOR_STYLE VSTYLE,                                                                                 ");
        advQueryFragment.append("         inp.classes,                                                                                             ");
        advQueryFragment.append("         inp.CompletionFrom,inp.CompletionTo,inp.ImageStatus inpImageStatus,                                      ");
        advQueryFragment.append("         inp.ContentStatus inpContentStatus,inp.RequestType,inp.createdToday                                      ");
        advQueryFragment.append("       FROM                                                                                                       ");
        advQueryFragment.append("         (                                                                                                        ");
        advQueryFragment.append("           SELECT                                                                                                 ");
        advQueryFragment.append("             PARENT_MDMID,                                                                                        ");
        advQueryFragment.append("             MDMID,                                                                                               ");
        advQueryFragment.append("             XML_DATA,                                                                                            ");
        advQueryFragment.append("             ENTRY_TYPE,                                                                                          ");
        advQueryFragment.append("             MOD_DTM,                                                                                             ");
        advQueryFragment.append("             PRIMARY_UPC,                                                                                         ");
        advQueryFragment.append("             DEPT_ID, Supplier_Id                                                                                 ");
        advQueryFragment.append("           FROM                                                                                                   ");
        advQueryFragment.append("             entryTypeStyleList                                                                                   ");
        advQueryFragment.append("       UNION ALL                                                                                                  ");
        advQueryFragment.append("           SELECT                                                                                                 ");
        advQueryFragment.append("             PARENT_MDMID,                                                                                        ");
        advQueryFragment.append("             MDMID,                                                                                               ");
        advQueryFragment.append("             XML_DATA,                                                                                            ");
        advQueryFragment.append("             ENTRY_TYPE,                                                                                          ");
        advQueryFragment.append("             MOD_DTM,                                                                                             ");
        advQueryFragment.append("             CASE                                                                                                 ");
        advQueryFragment.append("             when PRIMARY_UPC is null                                                                             ");
        advQueryFragment.append("             then NUMBER_04                                                                                       ");
        advQueryFragment.append("              when PRIMARY_UPC = ' '                                                                              ");
        advQueryFragment.append("              then NUMBER_04                                                                                      ");
        advQueryFragment.append("             else     PRIMARY_UPC                                                                                 ");
        advQueryFragment.append("             end PRIMARY_UPC,                                                                                     ");
        advQueryFragment.append("             DEPT_ID, PRIMARY_SUPPLIER_ID Supplier_Id                                                             ");
        advQueryFragment.append("           FROM                                                                                                   ");
        advQueryFragment.append("             ADSE_ITEM_CATALOG                                                                                    ");
        advQueryFragment.append("           WHERE                                                                                                  ");
        advQueryFragment.append("             PARENT_MDMID IN (SELECT MDMID FROM entryTypeStyleList)                                               ");
        advQueryFragment.append("             AND ENTRY_TYPE IN ('PackColor')                                                                      ");
        advQueryFragment.append("          ) aic, Input inp                                                                                        ");
        advQueryFragment.append("      ),                                                                                                          ");
        advQueryFragment.append("    styleID_DescA AS(                                                                                             ");
        advQueryFragment.append("       SELECT                                                                                                     ");
        advQueryFragment.append("         sl.PARENT_MDMID,                                                                                         ");
        advQueryFragment.append("         sl.MDMID,                                                                                                ");
        advQueryFragment.append("         sl.XML_DATA,                                                                                             ");
        advQueryFragment.append("         sl.ENTRY_TYPE,                                                                                           ");
        advQueryFragment.append("         sl.PRIMARY_UPC,                                                                                          ");
        advQueryFragment.append("         sl.Supplier_Id,                                                                                          ");
        advQueryFragment.append("         sl.DEPT_ID,                                                                                              ");
        advQueryFragment.append("         UPPER(ia.ven_style),                                                                                            ");
        advQueryFragment.append("         ia.clsId,                                                                                                ");
        advQueryFragment.append("         sl.INPUTPETSTATUS,                                                                                       ");
        advQueryFragment.append("         sl.VSTYLE,                                                                                               ");
        advQueryFragment.append("         sl.classes,                                                                                              ");
        advQueryFragment.append("         sl.CompletionFrom,sl.CompletionTo,sl.inpImageStatus,sl.inpContentStatus,sl.RequestType,sl.createdToday   ");
        advQueryFragment.append("       FROM                                                                                                       ");
        advQueryFragment.append("         itemGroup sl,                                                                                            ");
        advQueryFragment.append("         XMLTABLE(                                                                                                ");
        advQueryFragment.append("         'for $i in $XML_DATA/pim_entry/entry/Item_Ctg_Spec/Supplier                                              ");
        advQueryFragment.append("         let                                                                                                      ");
        advQueryFragment.append("         $marchant_id := fn:tokenize($XML_DATA/pim_entry/item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path, \"\\||///\"), ");
        advQueryFragment.append("         $ven_style := $i/VPN/text(),                                                                                                  ");
        advQueryFragment.append("         $primary_flag := $i/Primary_Flag/text()                                                                                       ");
        advQueryFragment.append("         return                                                                                                                        ");
        advQueryFragment.append("         <out>                                                                                                                         ");
        advQueryFragment.append("           <cls_id>{$marchant_id[6]}</cls_id>                                                                                          ");
        advQueryFragment.append("           <ven_style>{$ven_style}</ven_style>                                                                                         ");
        advQueryFragment.append("           <primary_flag>{$primary_flag}</primary_flag>                                                                                ");
        advQueryFragment.append("         </out>'                                                                                                                       ");
        advQueryFragment.append("         passing sl.XML_DATA AS \"XML_DATA\" columns                                                                                     ");
        advQueryFragment.append("         clsId VARCHAR(10) path  '/out/cls_id',                                                                                        ");
        advQueryFragment.append("         ven_style VARCHAR2(100) path '/out/ven_style',                                                                                ");
        advQueryFragment.append("         primary_flag VARCHAR(25) path '/out/primary_flag') ia                                                                         ");
        advQueryFragment.append("       WHERE primary_flag = 'true'                                                                                                     ");
        advQueryFragment.append("      )                                                                                                                                ");
        advQueryFragment.append("   ,styleID_DescAA AS(                                                                                                                 ");
        advQueryFragment.append("       SELECT                                                                                                                          ");
        advQueryFragment.append("         sda.PARENT_MDMID,                                                                                                             ");
        advQueryFragment.append("         sda.MDMID,                                                                                                                    ");
        advQueryFragment.append("         sda.XML_DATA,                                                                                                                 ");
        advQueryFragment.append("         sda.ENTRY_TYPE,                                                                                                               ");
        advQueryFragment.append("         sda.PRIMARY_UPC,                                                                                                              ");
        advQueryFragment.append("         p.COLO_DESC,                                                                                                                  ");
        advQueryFragment.append("         p.COLO_CODE,                                                                                                                  ");
        advQueryFragment.append("         sda.Supplier_Id,                                                                                                              ");
        advQueryFragment.append("         sda.DEPT_ID,                                                                                                                  ");
        advQueryFragment.append("         p.PRODUCT_NAME,                                                                                                               ");
        advQueryFragment.append("         sda.ven_style,                                                                                                                ");
        advQueryFragment.append("         sda.clsId,                                                                                                                    ");
        advQueryFragment.append("         sda.INPUTPETSTATUS,                                                                                                           ");
        advQueryFragment.append("         sda.VSTYLE,                                                                                                                   ");
        advQueryFragment.append("         sda.classes,                                                                                                                  ");
        advQueryFragment.append("         sda.CompletionFrom,sda.CompletionTo,sda.inpImageStatus,sda.inpContentStatus,sda.RequestType,sda.createdToday,                 ");
        advQueryFragment.append("         p.completion_date,                                                                                                            ");
        advQueryFragment.append("         p.req_type,                                                                                                                   ");
        advQueryFragment.append("         pet.pet_state PETSTATUS,                                                                                                      ");
        advQueryFragment.append("         pet.image_status ImageState,                                                                                                  ");
        advQueryFragment.append("         pet.content_status CONTENTSTATUS                                                                                              ");
        advQueryFragment.append("   FROM                                                                                                                                ");
        advQueryFragment.append("     styleID_DescA sda,                                                                                                                ");
        advQueryFragment.append("     ADSE_PET_CATALOG pet,                                                                                                             ");
        advQueryFragment.append("     XMLTABLE(                                                                                                                         ");
        advQueryFragment.append("     'let                                                                                                                              ");
        advQueryFragment.append("     $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date,                                                            ");
        advQueryFragment.append("     $colordesc:= $pets/pim_entry/entry/Ecomm_PackColor_Spec/NRF_Color_Description,                                                    ");
        advQueryFragment.append("     $colorcode:= $pets/pim_entry/entry/Ecomm_PackColor_Spec/NRF_Color_Code                                                            ");
        advQueryFragment.append("     return                                                                                                                            ");
        advQueryFragment.append("     <out>                                                                                                                             ");
        advQueryFragment.append("         <id>{$pets/pim_entry/entry/Pet_Ctg_Spec/Id}</id>                                                                              ");
        advQueryFragment.append("         <completion_date>{$completionDate}</completion_date>                                                                          ");
        advQueryFragment.append("         <req_type>{$pets/pim_entry/entry/Pet_Ctg_Spec/SourceSystem}</req_type>                                                        ");
        advQueryFragment.append("         <PRODUCT_NAME>{$pets/pim_entry/entry/Ecomm_ComplexPack_Spec/Product_Name}</PRODUCT_NAME>                                                ");
        advQueryFragment.append("         <COLO_DESC>{$colordesc}</COLO_DESC>                                                                                           ");
        advQueryFragment.append("         <COLO_CODE>{$colorcode}</COLO_CODE>                                                                                           ");
        advQueryFragment.append("     </out>'                                                                                                                           ");
        advQueryFragment.append("     passing pet.xml_data AS \"pets\" Columns                                                                                            ");
        advQueryFragment.append("         Id VARCHAR2(20) path '/out/id',                                                                                               ");
        advQueryFragment.append("         completion_date                           VARCHAR2(10) path  '/out/completion_date',                                          ");
        advQueryFragment.append("         PRODUCT_NAME            VARCHAR2(40) path '/out/PRODUCT_NAME',                                                                ");
        advQueryFragment.append("         req_type           VARCHAR2(20) path '/out/req_type',                                                                         ");
        advQueryFragment.append("         COLO_CODE            VARCHAR2(50)  path '/out/COLO_CODE',                                                                     ");
        advQueryFragment.append("         COLO_DESC            VARCHAR2(50)  path '/out/COLO_DESC'                                                                      ");
        advQueryFragment.append("     )p                                                                                                                                ");
        advQueryFragment.append("   WHERE                                                                                                                               ");
        advQueryFragment.append("     sda.MDMID     =pet.mdmid                                                                                                          ");
        advQueryFragment.append("   /*AND sda.ENTRY_TYPE = typInd.typ*/                                                                                                 ");
        advQueryFragment.append("   AND                                                                                                                                 ");
        advQueryFragment.append("     (                                                                                                                                 ");
        advQueryFragment.append("       CompletionFrom IS NULL                                                                                                          ");
        advQueryFragment.append("     OR completion_date   >=CompletionFrom                                                                                             ");
        advQueryFragment.append("     )                                                                                                                                 ");
        advQueryFragment.append("   AND                                                                                                                                 ");
        advQueryFragment.append("     (                                                                                                                                 ");
        advQueryFragment.append("       CompletionTo IS NULL                                                                                                            ");
        advQueryFragment.append("     OR completion_date <=CompletionTo                                                                                                 ");
        advQueryFragment.append("     )                                                                                                                                 ");
        advQueryFragment.append("   AND                                                                                                                                 ");
        advQueryFragment.append("     (                                                                                                                                 ");
        advQueryFragment.append("       inpImageStatus IS NULL                                                                                                          ");
        advQueryFragment.append("     OR pet.image_status   IN                                                                                                          ");
        advQueryFragment.append("       to_char((                                                                                                                               ");
        advQueryFragment.append("         SELECT                                                                                                                        ");
        advQueryFragment.append("           regexp_substr(inpImageStatus,'[^,]+',1,LEVEL)                                                                               ");
        advQueryFragment.append("         FROM                                                                                                                          ");
        advQueryFragment.append("           dual                                                                                                                        ");
        advQueryFragment.append("           CONNECT BY regexp_substr(inpImageStatus,'[^,]+',1,LEVEL) IS NOT NULL                                                        ");
        advQueryFragment.append("       ))                                                                                                                               ");
        advQueryFragment.append("     )                                                                                                                                 ");
        advQueryFragment.append("   AND                                                                                                                                 ");
        advQueryFragment.append("     (                                                                                                                                 ");
        advQueryFragment.append("       inpContentStatus IS NULL                                                                                                        ");
        advQueryFragment.append("     OR pet.content_status    IN                                                                                                       ");
        advQueryFragment.append("       to_char((                                                                                                                               ");
        advQueryFragment.append("         SELECT                                                                                                                        ");
        advQueryFragment.append("           regexp_substr(inpContentStatus,'[^,]+',1,LEVEL)                                                                             ");
        advQueryFragment.append("         FROM                                                                                                                          ");
        advQueryFragment.append("           dual                                                                                                                        ");
        advQueryFragment.append("           CONNECT BY regexp_substr(inpContentStatus,'[^,]+',1,LEVEL) IS NOT NULL                                                      ");
        advQueryFragment.append("       ) )                                                                                                                              ");
        advQueryFragment.append("     )                                                                                                                                 ");
        advQueryFragment.append("   AND                                                                                                                                 ");
        advQueryFragment.append("     (                                                                                                                                 ");
        advQueryFragment.append("       RequestType IS NULL                                                                                                             ");
        advQueryFragment.append("     OR Req_Type       IN                                                                                                              ");
        advQueryFragment.append("       to_char((                                                                                                                               ");
        advQueryFragment.append("         SELECT                                                                                                                        ");
        advQueryFragment.append("           regexp_substr(RequestType,'[^,]+',1,LEVEL)                                                                                  ");
        advQueryFragment.append("         FROM                                                                                                                          ");
        advQueryFragment.append("           dual                                                                                                                        ");
        advQueryFragment.append("           CONNECT BY regexp_substr(RequestType,'[^,]+',1,LEVEL) IS NOT NULL                                                           ");
        advQueryFragment.append("       ))                                                                                                                               ");
        advQueryFragment.append("     )                                                                                                                                 ");
        advQueryFragment.append("   AND                                                                                                                                 ");
        advQueryFragment.append("     (                                                                                                                                 ");
        advQueryFragment.append("       createdToday                           IS NULL                                                                                  ");
        advQueryFragment.append("     OR createdToday                          <>'yes'                                                                                  ");
        advQueryFragment.append("     OR to_date(SUBSTR(pet.CREATED_DTM, 1, 9), 'YYYY-MM-DD') = to_date(SUBSTR(CURRENT_TIMESTAMP, 1, 9), 'YYYY-MM-DD')/*'09-NOV-15' CURRENT_DATE*/ ");
        advQueryFragment.append("     )                                                                                                                                  ");
        advQueryFragment.append("   ),                                                                                                                                   ");
        advQueryFragment.append("    supplierDetails AS                                                                                                                  ");
        advQueryFragment.append("     (                                                                                                                                  ");
        advQueryFragment.append("       SELECT                                                                                                                           ");
        advQueryFragment.append("         orin.PARENT_MDMID,                                                                                                             ");
        advQueryFragment.append("         orin.MDMID,                                                                                                                    ");
        advQueryFragment.append("         orin.ENTRY_TYPE,                                                                                                               ");
        advQueryFragment.append("         orin.COLO_CODE,                                                                                                                ");
        advQueryFragment.append("         orin.COLO_DESC,                                                                                                                ");
        advQueryFragment.append("         orin.DEPT_Id,                                                                                                                  ");
        advQueryFragment.append("         orin.PRODUCT_NAME,                                                                                                             ");
        advQueryFragment.append("         orin.ven_style,                                                                                                                ");
        advQueryFragment.append("         orin.Supplier_Id,                                                                                                              ");
        advQueryFragment.append("         orin.PRIMARY_UPC,                                                                                                              ");
        advQueryFragment.append("         orin.clsId,                                                                                                                    ");
        advQueryFragment.append("         orin.INPUTPETSTATUS,                                                                                                           ");
        advQueryFragment.append("         orin.VSTYLE,                                                                                                                   ");
        advQueryFragment.append("         orin.classes,                                                                                                                  ");
        advQueryFragment.append("         orin.completion_date,                                                                                                          ");
        advQueryFragment.append("         orin.req_type,                                                                                                                 ");
        advQueryFragment.append("         orin.ImageState,                                                                                                               ");
        advQueryFragment.append("         orin.CONTENTSTATUS,                                                                                                            ");
        advQueryFragment.append("         orin.PETSTATUS,                                                                                                                ");
        advQueryFragment.append("         s.ven_name,                                                                                                                    ");
        advQueryFragment.append("         s.omniChannelIndicator                                                                                                         ");
        advQueryFragment.append("       FROM                                                                                                                             ");
        advQueryFragment.append("         styleID_DescAA orin,                                                                                                           ");
        advQueryFragment.append("         ADSE_SUPPLIER_CATALOG supplier,                                                                                                ");
        advQueryFragment.append("         XMLTABLE(                                                                                                                      ");
        advQueryFragment.append("         'let                                                                                                                           ");
        advQueryFragment.append("         $ven_name := $supplierCatalog/pim_entry/entry/Supplier_Ctg_Spec/Name,                                                          ");
        advQueryFragment.append("         $isOmniChannel := $supplierCatalog/pim_entry/entry/Supplier_Site_Spec/Omni_Channel                                             ");
        advQueryFragment.append("         return                                                                                                                         ");
        advQueryFragment.append("         <out>                                                                                                                          ");
        advQueryFragment.append("             <ven_name>{$ven_name}</ven_name>                                                                                           ");
        advQueryFragment.append("             <sup>{$supplierCatalog/pim_entry/entry/Supplier_Ctg_Spec/Id}</sup>                                                         ");
        advQueryFragment.append("             <omni_channel>{if($isOmniChannel eq \"true\") then \"Y\" else \"N\"}</omni_channel>                                              ");
        advQueryFragment.append("         </out>'                                                                                                                        ");
        advQueryFragment.append("         PASSING XMLELEMENT(\"Id\", orin.Supplier_Id) AS \"Suppliers\",                                                                     ");
        advQueryFragment.append("         supplier.XML_DATA                          AS \"supplierCatalog\" columns                                                        ");
        advQueryFragment.append("         ven_name                          VARCHAR2(80) path '/out/ven_name', omniChannelIndicator VARCHAR2                             ");
        advQueryFragment.append("         (2) path '/out/omni_channel', sup VARCHAR2(100) path '/out/sup') s                                                             ");
        advQueryFragment.append("       WHERE                                                                                                                            ");
        advQueryFragment.append("       orin.Supplier_Id = supplier.mdmid                                                                                                ");
        advQueryFragment.append("      ),                                                                                                                                ");
        advQueryFragment.append("      styleID_Desc AS(                                                                                                                  ");
        advQueryFragment.append("       SELECT                                                                                                                           ");
        advQueryFragment.append("         sia.PARENT_MDMID,                                                                                                              ");
        advQueryFragment.append("         sia.MDMID ORIN_NUM,                                                                                                            ");
        advQueryFragment.append("         sia.ENTRY_TYPE,                                                                                                                ");
        advQueryFragment.append("         sia.COLO_DESC,                                                                                                                 ");
        advQueryFragment.append("         sia.COLO_CODE,                                                                                                                 ");
        advQueryFragment.append("         sia.Supplier_Id,                                                                                                               ");
        advQueryFragment.append("         sia.DEPT_ID,                                                                                                                   ");
        advQueryFragment.append("         sia.PRODUCT_NAME,                                                                                                              ");
        advQueryFragment.append("         UPPER(sia.ven_style),                                                                                                                 ");
        advQueryFragment.append("         sia.PRIMARY_UPC,                                                                                                               ");
        advQueryFragment.append("         sia.clsId,                                                                                                                     ");
        advQueryFragment.append("         sia.INPUTPETSTATUS,                                                                                                            ");
        advQueryFragment.append("         sia.VSTYLE,                                                                                                                    ");
        advQueryFragment.append("         sia.classes,                                                                                                                   ");
        advQueryFragment.append("         sia.completion_date,                                                                                                           ");
        advQueryFragment.append("         sia.req_type,                                                                                                                  ");
        advQueryFragment.append("         sia.ImageState,                                                                                                                ");
        advQueryFragment.append("         sia.CONTENTSTATUS,                                                                                                             ");
        advQueryFragment.append("         sia.PETSTATUS,                                                                                                                 ");
        advQueryFragment.append("         sia.ven_name,                                                                                                                  ");
        advQueryFragment.append("         sia.omniChannelIndicator                                                                                                       ");
        advQueryFragment.append("       from supplierDetails sia                                                                                                         ");
        advQueryFragment.append("       where                                                                                                                            ");
        advQueryFragment.append("           (sia.Entry_Type='Complex Pack'                                                                                               ");
        advQueryFragment.append("           AND (INPUTPETSTATUS IS NULL OR sia.PETSTATUS = INPUTPETSTATUS) AND (VSTYLE IS NULL OR VEN_STYLE like UPPER(VSTYLE)))                   ");
        advQueryFragment.append("         OR                                                                                                                             ");
        advQueryFragment.append("           (entry_type = 'PackColor'                                                                                                    ");
        advQueryFragment.append("           AND PARENT_MDMID IN                                                                                                          ");
        advQueryFragment.append("               (SELECT MDMID   FROM styleID_DescAA WHERE ENTRY_TYPE='Complex Pack' AND                                                  ");
        advQueryFragment.append("                   (INPUTPETSTATUS IS NULL OR PETSTATUS = INPUTPETSTATUS) AND (VSTYLE IS NULL OR VEN_STYLE like UPPER(VSTYLE))                    ");
        advQueryFragment.append("               )                                                                                                                        ");
        advQueryFragment.append("           AND (INPUTPETSTATUS IS NULL OR PETSTATUS = INPUTPETSTATUS))                                                                  ");
        advQueryFragment.append("       )                                                                                                                                ");
        advQueryFragment.append("     SELECT                                                                                                                             ");
        advQueryFragment.append("         CASE                                                                                                                           ");
        advQueryFragment.append("         WHEN LENGTH(ORIN_NUM) > 9                                                                                                      ");
        advQueryFragment.append("           THEN SUBSTR(ORIN_NUM,1,9)                                                                                                    ");
        advQueryFragment.append("           ELSE ORIN_NUM                                                                                                                ");
        advQueryFragment.append("         END Parent_Style_Orin,                                                                                                         ");
        advQueryFragment.append("         ORIN_NUM,                                                                                                                      ");
        advQueryFragment.append("         DEPT_ID,                                                                                                                       ");
        advQueryFragment.append("         Supplier_Id,                                                                                                                   ");
        advQueryFragment.append("         PRODUCT_NAME,                                                                                                                  ");
        advQueryFragment.append("         ENTRY_TYPE,                                                                                                                    ");
        advQueryFragment.append("         PRIMARY_UPC,                                                                                                                   ");
        advQueryFragment.append("         VEN_NAME,                                                                                                                      ");
        advQueryFragment.append("         VEN_STYLE,                                                                                                                     ");
        advQueryFragment.append("         clsId,                                                                                                                         ");
        advQueryFragment.append("         PETSTATUS,                                                                                                                     ");
        advQueryFragment.append("         ImageState,                                                                                                                    ");
        advQueryFragment.append("         CONTENTSTATUS,                                                                                                                 ");
        advQueryFragment.append("         completion_date,                                                                                                               ");
        advQueryFragment.append("         omniChannelIndicator,                                                                                                          ");
        advQueryFragment.append("         req_type,                                                                                                                      ");
        advQueryFragment.append("       COLO_DESC                                                                                                                        ");
        advQueryFragment.append("     FROM styleID_Desc                                                                                                                  ");
        advQueryFragment.append("     WHERE                                                                                                                              ");
        advQueryFragment.append("         (                                                                                                                              ");
        advQueryFragment.append("           classes IS NULL                                                                                                              ");
        advQueryFragment.append("          OR clsId  IN                                                                                                                  ");
        advQueryFragment.append("           to_char((                                                                                                                            ");
        advQueryFragment.append("             SELECT                                                                                                                     ");
        advQueryFragment.append("               regexp_substr(classes,'[^,]+',1,LEVEL)                                                                                   ");
        advQueryFragment.append("             FROM                                                                                                                       ");
        advQueryFragment.append("               dual                                                                                                                     ");
        advQueryFragment.append("               CONNECT BY regexp_substr(classes,'[^,]+',1,LEVEL) IS NOT NULL                                                            ");
        advQueryFragment.append("           ))                                                                                                                            ");
        advQueryFragment.append("         )                                                                                                                              ");

    LOGGER.info("getAdvWorkListDisplayData Query"+advQueryFragment.toString());
    
    return advQueryFragment.toString();
    
    
 }  
  


/**
 * Gets the Image status mapping.
 *
 * @param petList the pet list
 * @return the formated pet details
* @throws IOException / AFUPYB3
 */
private String getImageStatusCode(String status) {
    LOGGER.info("This is from getImageContentStatusCode...status-->"+status);
    String finalStatus = "";
    try{
        Properties prop= PropertiesFileLoader.getExternalLoginProperties();
        String[] statusArray = status.split(",");
        for(int i = 0; i<statusArray.length; i++){
            finalStatus = finalStatus + prop.getProperty("Image_"+statusArray[i]) + ",";
        }
        finalStatus = finalStatus.substring(0, finalStatus.length()-1);
    }catch(Exception ex){
        ex.printStackTrace();
    }
    LOGGER.info("This is from getImageStatusCode...finalStatus-->"+finalStatus);
 return finalStatus;
} 


/**
 * Gets the Content status mapping.
 *
 * @param petList the pet list
 * @return the formated pet details
* @throws IOException / AFUPYB3
 */
private String getContentStatusCode(String status) {
    LOGGER.info("This is from getImageContentStatusCode...status-->"+status);
    String finalStatus = "";
    try{
        Properties prop= PropertiesFileLoader.getExternalLoginProperties();
        String[] statusArray = status.split(",");
        for(int i = 0; i<statusArray.length; i++){
            finalStatus = finalStatus + prop.getProperty("Content_"+statusArray[i]) + ",";
        }
        finalStatus = finalStatus.substring(0, finalStatus.length()-1);
    }catch(Exception ex){
        ex.printStackTrace();
    }
    LOGGER.info("This is from getContentStatusCode...finalStatus-->"+finalStatus);
 return finalStatus;
} 

/**
 * Gets the pet status mapping.
 *
 * @param petList the pet list
 * @return the formated pet details
* @throws IOException 
 */
private String getSourceStatusCode(String status) {
    LOGGER.info("This is from getSourceStatusCode...status-->"+status);
    String finalStatus = "";
    try{
        Properties prop= PropertiesFileLoader.getExternalLoginProperties();
        String[] statusArray = status.split(",");
        for(int i = 0; i<statusArray.length; i++){
            if(statusArray[i].contains("Manual"))
            {
                finalStatus = finalStatus + prop.getProperty("Manual") + ",";
            }
            else if(statusArray[i].contains("Direct"))
            {
                finalStatus = finalStatus + prop.getProperty("Direct") + ",";
            }
            else if(statusArray[i].contains("Attribute"))
            {
                finalStatus = finalStatus + prop.getProperty("Attribute") + ",";
            }
            else if(statusArray[i].contains("PO"))
            {
                finalStatus = finalStatus + "PO" + ",";
            }
        }
        finalStatus = finalStatus.substring(0, finalStatus.length()-1);
    }catch(Exception ex){
        ex.printStackTrace();
    }
    LOGGER.info("This is from getSourceStatusCode...finalStatus-->"+finalStatus);
 return finalStatus;
} 



/**
 * Gets the work list display data for Parent ORINs.
 *
 * @param depts the depts
 * @param email the email
 * @param pepId the pep id
 * @return the work list display data
 */
public String getWorkListDisplayDataParent(boolean vendorLogin, String sortColumn, String sortOrder){
	StringBuilder workListQuery = new StringBuilder();
	
	workListQuery.append("WITH Input(Depts, EmailId, LANId, SuppIds) AS\r\n");
	workListQuery.append("                                (\r\n");
	workListQuery.append("                                        SELECT :depts Depts, :email EmailId, :pepId LANId, :supplierId SuppIds FROM dual\r\n");
	workListQuery.append("                                )\r\n");
	workListQuery.append("                                ,\r\n");
	workListQuery.append("                                givenInpDept AS\r\n");
	workListQuery.append("                                (\r\n");
	workListQuery.append("                                        SELECT  regexp_substr(inp.depts,'[^,]+',1,LEVEL) AS dept_id\r\n");
	workListQuery.append("                                        FROM    Input inp CONNECT BY regexp_substr(inp.depts,'[^,]+',1,LEVEL) IS NOT NULL\r\n");
	workListQuery.append("                                )\r\n");
	workListQuery.append("                                ,\r\n");
	workListQuery.append("                                givenInpSupplier AS\r\n");
	workListQuery.append("                                (\r\n");
	workListQuery.append("                                        SELECT  regexp_substr(inp.SuppIds,'[^,]+',1,LEVEL) AS supplier_id\r\n");
	workListQuery.append("                                        FROM    Input inp CONNECT BY regexp_substr(inp.SuppIds,'[^,]+',1,LEVEL) IS NOT NULL\r\n");
	workListQuery.append("                                )\r\n");
	workListQuery.append("                             \r\n");
	workListQuery.append("                        SELECT  aic.MDMID Parent_Style_Orin ,\r\n");
	workListQuery.append("                                aic.MDMID ORIN_NUM                   ,\r\n");
	workListQuery.append("                                aic.DEPT_Id               ,\r\n");
	workListQuery.append("                                aic.PRIMARY_SUPPLIER_ID Supplier_Id           ,\r\n");
	workListQuery.append("                                aic.PRODUCT_NAME          ,\r\n");
	workListQuery.append("                                aic.ENTRY_TYPE            ,\r\n");
	workListQuery.append("                                CASE\r\n");
	workListQuery.append("                                                        WHEN aic.PRIMARY_UPC IS NULL\r\n");
	workListQuery.append("                                                        THEN aic.NUMBER_04\r\n");
	workListQuery.append("                                                        WHEN aic.PRIMARY_UPC = ' '\r\n");
	workListQuery.append("                                                        THEN aic.NUMBER_04\r\n");
	workListQuery.append("                                                        ELSE aic.PRIMARY_UPC\r\n");
	workListQuery.append("                                                END PRIMARY_UPC            ,\r\n");
	workListQuery.append("                                  XMLCAST( (XMLQUERY('pim_entry/entry/Supplier_Ctg_Spec/Name' PASSING supplier.XML_DATA RETURNING CONTENT ) ) AS VARCHAR2(1000) ) ven_name                 ,\r\n");
	workListQuery.append("                                aic.PRIMARYSUPPLIERVPN ven_style             ,\r\n");
	workListQuery.append("                                pet.pet_state PETSTATUS             ,\r\n");
	workListQuery.append("                                pet.image_status ImageState            ,\r\n");
	workListQuery.append("                                pet.content_status CONTENTSTATUS                                  ,\r\n");
	workListQuery.append("                                XMLCAST( (XMLQUERY('pim_entry/entry/Pet_Ctg_Spec/Completion_Date' PASSING  pet.xml_data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) \r\n");
	workListQuery.append("                                                 completion_date               ,\r\n");
	workListQuery.append("                             ( case when \r\n");
	workListQuery.append("                              ((XMLCAST( (XMLQUERY('pim_entry/entry/Supplier_Ctg_Spec/Supplier_Site_Spec/Omni_Channel' PASSING supplier.XML_DATA RETURNING CONTENT ) ) AS VARCHAR2(1000) ))='true') then 'Y' \r\n");
	workListQuery.append("                                else 'N'\r\n");
	workListQuery.append("                                End)\r\n");
	workListQuery.append("                                   omniChannelIndicator     ,\r\n");
	workListQuery.append("                                                 XMLCAST( (XMLQUERY('pim_entry/entry/Pet_Ctg_Spec/SourceSystem' PASSING  pet.xml_data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) \r\n");
	workListQuery.append("                                                 req_type                      ,\r\n");
	workListQuery.append("                                pet.PET_STYLE_STATE       ,\r\n");
	workListQuery.append("                                pet.PET_STYLE_IMAGE       ,\r\n");
	workListQuery.append("                                pet.PET_STYLE_CONTENT     ,\r\n");
	workListQuery.append("                                pet.PET_EARLIEST_COMP_DATE   ,\r\n");
	workListQuery.append("                                pet.EXIST_IN_GROUP        ,\r\n");
	workListQuery.append("                                XMLCAST( (XMLQUERY('pim_entry/entry/Ecomm_Style_Spec/Split_Color_Eligible' PASSING  pet.xml_data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) \r\n");
	workListQuery.append("                                                CFAS                          ,\r\n");
	workListQuery.append("                                XMLCAST( (XMLQUERY('/pim_entry/entry/Pet_Ctg_Spec/System/Pet_Information/Conversion_Flag' PASSING  pet.xml_data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) \r\n");
	workListQuery.append("                                               CONVERSION_FLAG,      \r\n");
	workListQuery.append("                                pet.MISSING_ASSET        \r\n");
	workListQuery.append("                        FROM    ADSE_Item_CATALOG aic,\r\n");
	workListQuery.append("                                        ADSE_PET_CATALOG pet ,\r\n");
	workListQuery.append("                                        givenInpDept d       ,\r\n");
	workListQuery.append("                                        givenInpSupplier s   ,\r\n");
	workListQuery.append("                                       Input inp,\r\n");
	workListQuery.append("                                       ADSE_SUPPLIER_CATALOG supplier\r\n");
	workListQuery.append("\r\n");
	workListQuery.append("                        WHERE   \r\n");
	workListQuery.append("                        aic.ENTRY_TYPE    IN ('Style', 'Complex Pack')\r\n");
	workListQuery.append("                                                AND aic.PETEXISTS  = 'Y'\r\n");
	workListQuery.append("                                                AND pet.mdmid      = aic.mdmid\r\n");
	if(vendorLogin){
        workListQuery.append("        AND (((pet.pet_state = '01' AND (pet.image_status NOT IN ('08','02') OR pet.CONTENT_STATUS NOT IN ('08','02')))) ");
        workListQuery.append("        OR ((pet.PET_STYLE_STATE = 'Y' AND (pet.PET_STYLE_IMAGE = 'Y' OR pet.PET_STYLE_CONTENT = 'Y')))) ");
    }else{
    	workListQuery.append("        AND (pet.pet_state = '01' OR pet.PET_STYLE_STATE = 'Y')                                                         ");
    }
	workListQuery.append("                                                AND\r\n");
	workListQuery.append("                                                (\r\n");
	workListQuery.append("                                                        (\r\n");
	workListQuery.append("                                                                (\r\n");
	workListQuery.append("                                                                        inp.EmailId IS NULL\r\n");
	workListQuery.append("                                                                )\r\n");
	workListQuery.append("                                                                AND aic.DEPT_ID              = d.dept_id\r\n");
	workListQuery.append("                                                                AND aic.PRIMARY_SUPPLIER_ID IS NOT NULL\r\n");
	workListQuery.append("                                                        )\r\n");
	workListQuery.append("                                                        OR\r\n");
	workListQuery.append("                                                        (\r\n");
	workListQuery.append("                                                                (\r\n");
	workListQuery.append("                                                                        inp.EmailId   IS NOT NULL\r\n");
	workListQuery.append("                                                                        AND d.dept_id IS NOT NULL\r\n");
	workListQuery.append("                                                                )\r\n");
	workListQuery.append("                                                                AND aic.DEPT_ID             =d.dept_id\r\n");
	workListQuery.append("                                                                AND inp.SuppIds            IS NOT NULL\r\n");
	workListQuery.append("                                                                AND aic.PRIMARY_SUPPLIER_ID = s.supplier_id\r\n");
	workListQuery.append("                                                        )\r\n");
	workListQuery.append("                                                        OR\r\n");
	workListQuery.append("                                                        (\r\n");
	workListQuery.append("                                                                (\r\n");
	workListQuery.append("                                                                        inp.EmailId   IS NOT NULL\r\n");
	workListQuery.append("                                                                        AND d.dept_id IS NULL\r\n");
	workListQuery.append("                                                                )\r\n");
	workListQuery.append("                                                                AND inp.SuppIds            IS NOT NULL\r\n");
	workListQuery.append("                                                                AND aic.PRIMARY_SUPPLIER_ID = s.supplier_id\r\n");
	workListQuery.append("                                                        )\r\n");
	workListQuery.append("                                                )\r\n");
	workListQuery.append("                        AND pet.WLIST_DISPLAY_FLAG = 'true'                        \r\n");
	workListQuery.append("                        AND aic.PRIMARY_SUPPLIER_ID = supplier.mdmid\r\n");
	
	/** Added for Pagination Perf Enhancements **/
    if (sortColumn == null || sortColumn.trim().equals("")) {
    	workListQuery.append(" ORDER BY pet.PET_EARLIEST_COMP_DATE");
    	workListQuery.append(" , req_type DESC , aic.MDMID");
    }
    else if (sortColumn.equals("orinGroup")) {
    	workListQuery.append(" ORDER BY aic.MDMID " + sortOrder);
    }
    else if (sortColumn.equals("dept")) {
    	workListQuery.append(" ORDER BY aic.DEPT_ID " + sortOrder);
    	workListQuery.append(" , aic.MDMID");
    }
    else if (sortColumn.equals("vendorStyle")) {
    	workListQuery.append(" ORDER BY aic.PRIMARYSUPPLIERVPN " + sortOrder);
    	workListQuery.append(" , aic.MDMID");
    }
    else if (sortColumn.equals("productName")) {
    	workListQuery.append(" ORDER BY aic.PRODUCT_NAME " + sortOrder);
    	workListQuery.append(" , aic.MDMID");
    } 
    else if (sortColumn.equals("contentStatus")) {
    	workListQuery.append(" ORDER BY pet.content_status " + sortOrder);
    	workListQuery.append(" , aic.MDMID");
    } 
    else if (sortColumn.equals("imageStatus")) {
    	workListQuery.append(" ORDER BY pet.image_status " + sortOrder);
    	workListQuery.append(" , aic.MDMID");
    } 
    else if (sortColumn.equals("petStatus")) {
    	workListQuery.append(" ORDER BY pet.pet_state " + sortOrder);
    	workListQuery.append(" , aic.MDMID");
    } 
    else if (sortColumn.equals("completionDate")) {
    	workListQuery.append(" ORDER BY pet.PET_EARLIEST_COMP_DATE " + sortOrder);
    	workListQuery.append(" , aic.MDMID");
    } 
    else if (sortColumn.equals("petSourceType")) {
    	workListQuery.append(" ORDER BY req_type " + sortOrder);
    	workListQuery.append(" , aic.MDMID");
    }
    else if (sortColumn.equals("vendorName")) {
    	workListQuery.append(" ORDER BY ven_name " + sortOrder);
    	workListQuery.append(" , aic.MDMID");
    }

	return workListQuery.toString();
}

public String getWorkListDisplayDataParentOld(boolean vendorLogin) {
    
    StringBuilder workListQuery = new StringBuilder();
        workListQuery.append("WITH ");
        workListQuery.append("  Input(Depts, EmailId, LANId, SuppIds) AS ");
        workListQuery.append("  ( ");
        workListQuery.append("    SELECT ");
        workListQuery.append("         ");
        workListQuery.append(":depts");
        workListQuery.append(" Depts, ");
        workListQuery.append(":email");
        workListQuery.append(" EmailId, ");
        workListQuery.append(":pepId");
        workListQuery.append(" LANId, ");
        workListQuery.append(":supplierId");
        workListQuery.append("  SuppIds ");
        workListQuery.append("      ");
        workListQuery.append("      ");
        workListQuery.append("    FROM ");
        workListQuery.append("      dual ");
        workListQuery.append("  ) ");
        workListQuery.append("  , ");  
        workListQuery.append("    givenInpDept as (                                                                                                 ");
        workListQuery.append("      SELECT regexp_substr(inp.depts,'[^,]+',1,LEVEL) as dept_id  FROM Input inp                                      ");
        workListQuery.append("              CONNECT BY regexp_substr(inp.depts,'[^,]+',1,LEVEL) IS NOT NULL                                         ");
        workListQuery.append("    ),                                                                                                                ");
        workListQuery.append("    givenInpSupplier as (                                                                                             ");
        workListQuery.append("      SELECT regexp_substr(inp.SuppIds,'[^,]+',1,LEVEL) as supplier_id FROM Input inp                                 ");
        workListQuery.append("        CONNECT BY regexp_substr(inp.SuppIds,'[^,]+',1,LEVEL) IS NOT NULL                                             ");
        workListQuery.append("      ),                                                                                                              ");
        workListQuery.append("                                                                                                                      ");
        workListQuery.append("    entryTypeStyleList AS                                                                                             ");
        workListQuery.append("    (                                                                                                                 ");
        workListQuery.append("      SELECT                                                                                                          ");
        workListQuery.append("        aic.PARENT_MDMID,                                                                                                 ");
        workListQuery.append("        aic.MDMID,                                                                                                        ");
        workListQuery.append("        aic.ENTRY_TYPE,                                                                                                   ");
        workListQuery.append("        aic.MOD_DTM,                                                                                                      ");
        workListQuery.append("        CASE                                                                                                          ");
        workListQuery.append("          when aic.PRIMARY_UPC is null                                                                                    ");
        workListQuery.append("          then NUMBER_04                                                                                              ");
        workListQuery.append("           when aic.PRIMARY_UPC = ' '                                                                                     ");
        workListQuery.append("           then NUMBER_04                                                                                             ");
        workListQuery.append("          else     aic.PRIMARY_UPC                                                                                        ");
        workListQuery.append("        end PRIMARY_UPC,                                                                                              ");
        workListQuery.append("        aic.DEPT_ID, aic.PRIMARY_SUPPLIER_ID Supplier_Id, aic.PRIMARYSUPPLIERVPN ven_style, aic.PRODUCT_NAME                ");
        workListQuery.append("      FROM                                                                                                            ");
        workListQuery.append("        ADSE_Item_CATALOG aic,  ADSE_PET_CATALOG pet, givenInpDept d, givenInpSupplier s, Input inp                           ");
        workListQuery.append("      WHERE                                                                                                           ");
        workListQuery.append("        aic.ENTRY_TYPE in ('Style', 'Complex Pack')     AND aic.PETEXISTS = 'Y'                                                                     ");
        workListQuery.append("        AND  pet.mdmid = aic.mdmid AND  pet.PET_STATE <>'05' AND                                                                             ");
        workListQuery.append("         ((                                                                                                           ");
        workListQuery.append("             (inp.EmailId IS NULL)                                                                                    ");
        workListQuery.append("              AND aic.DEPT_ID = d.dept_id and aic.PRIMARY_SUPPLIER_ID is not null                                     ");
        workListQuery.append("          )                                                                                                           ");
        workListQuery.append("          OR                                                                                                          ");
        workListQuery.append("            ( (inp.EmailId IS NOT NULL and d.dept_id is not null)                                                                               ");
        workListQuery.append("            AND aic.DEPT_ID =d.dept_id                                                                                ");
        workListQuery.append("              AND inp.SuppIds is not null AND aic.PRIMARY_SUPPLIER_ID = s.supplier_id                                 ");
        workListQuery.append("        )                                                                                                            ");
        workListQuery.append("          OR                                                                                                       ");
        workListQuery.append("              ( (inp.EmailId IS NOT NULL and d.dept_id is null)                                                    ");
        workListQuery.append("              AND inp.SuppIds is not null AND aic.PRIMARY_SUPPLIER_ID = s.supplier_id                              ");
        workListQuery.append("      )                                                                                                            ");
        workListQuery.append("     )                                                                                                             ");
        workListQuery.append("    ),                                                                                                              ");
        workListQuery.append("  STYLEID_DESC AS(                                                                                                  ");
        workListQuery.append("      SELECT                                                                                                          ");
        workListQuery.append("        sda.PARENT_MDMID,                                                                                             ");
        workListQuery.append("        sda.MDMID ORIN_NUM,                                                                                           ");
        workListQuery.append("        sda.ENTRY_TYPE,                                                                                               ");
        workListQuery.append("        sda.PRIMARY_UPC,                                                                                              ");
        workListQuery.append("        sda.Supplier_Id,                                                                                              ");
        workListQuery.append("        sda.DEPT_ID,                                                                                                  ");
        workListQuery.append("        sda.PRODUCT_NAME,                                                                                               ");
        workListQuery.append("        UPPER(sda.ven_style),                                                                                                ");
        workListQuery.append("        pet.pet_state PETSTATUS,                                                                                      ");
        workListQuery.append("        pet.image_status ImageState,                                                                                  ");
        workListQuery.append("        pet.content_status CONTENTSTATUS,                                                                             ");
        workListQuery.append("        p.completion_date,                                                                                            ");
        workListQuery.append("        p.req_type,                                                                                                   ");
        workListQuery.append("        pet.PET_STYLE_STATE,                                                                                          ");
        workListQuery.append("        pet.PET_STYLE_IMAGE,                                                                                          ");
        workListQuery.append("        pet.PET_STYLE_CONTENT, pet.PET_EARLIEST_COMP_DATE, pet.EXIST_IN_GROUP, p.CFAS, p.CONVERSION_FLAG       ");
        workListQuery.append("     from                                                                                                             ");
        workListQuery.append("        entryTypeStyleList sda,                                                                                       ");
        workListQuery.append("        ADSE_PET_CATALOG pet,                                                                                         ");
        workListQuery.append("        XMLTABLE(                                                                                                     ");
        workListQuery.append("        'let                                                                                                          ");
        workListQuery.append("        $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date                                         ");
        workListQuery.append("        return                                                                                                        ");
        workListQuery.append("        <out>                                                                                                         ");
        workListQuery.append("          <completion_date>{$completionDate}</completion_date>                                                        ");
        workListQuery.append("          <req_type>{$pets/pim_entry/entry/Pet_Ctg_Spec/SourceSystem}</req_type>                                      ");
        workListQuery.append("          <CFAS>{$pets/pim_entry/entry/Ecomm_Style_Spec/Split_Color_Eligible}</CFAS>    ");
        workListQuery.append("          <CONVERSION_FLAG>{$pets/pim_entry/entry/Pet_Ctg_Spec/System/Pet_Information/Conversion_Flag}</CONVERSION_FLAG>    ");
        workListQuery.append("        </out>'                                                                                                       ");
        workListQuery.append("        passing pet.xml_data AS \"pets\" Columns                                                                        ");
        workListQuery.append("        completion_date             VARCHAR2(10)  path  '/out/completion_date',                                       ");
        workListQuery.append("        req_type                    VARCHAR2(20)  path '/out/req_type',                                               ");
        workListQuery.append("        CFAS VARCHAR2(50) path '/out/CFAS'                            ");
        workListQuery.append("        ,CONVERSION_FLAG VARCHAR2(50) path '/out/CONVERSION_FLAG'                            ");
        workListQuery.append("        ) p                                                                                                           ");
        workListQuery.append("      WHERE                                                                                                           ");
        workListQuery.append("        sda.mdmid     =pet.mdmid                                                                                      ");
        if(vendorLogin){
             workListQuery.append("        AND (((pet.pet_state = '01' AND (pet.image_status NOT IN ('08','02') OR pet.CONTENT_STATUS NOT IN ('08','02')))) ");
           workListQuery.append("        OR ((pet.PET_STYLE_STATE = 'Y' AND (pet.PET_STYLE_IMAGE = 'Y' OR pet.PET_STYLE_CONTENT = 'Y')))) ");
        }else{
           workListQuery.append("        AND (pet.pet_state = '01' OR PET_STYLE_STATE = 'Y')                                                         ");
        }
        workListQuery.append("        AND pet.WLIST_DISPLAY_FLAG = 'true'                                                           ");
        workListQuery.append("    )                                                                                                                  ");
        workListQuery.append("      SELECT                                                                                                           ");
        workListQuery.append("        ORIN_NUM Parent_Style_Orin,                                                                                    ");
        workListQuery.append("        ORIN_NUM,                                                                                                      ");
        workListQuery.append("        orin.DEPT_Id,                                                                                                  ");
        workListQuery.append("        orin.Supplier_Id,                                                                                              ");
        workListQuery.append("        orin.PRODUCT_NAME,                                                                                             ");
        workListQuery.append("        orin.ENTRY_TYPE,                                                                                               ");
        workListQuery.append("        orin.PRIMARY_UPC,                                                                                              ");
        workListQuery.append("        s.ven_name,                                                                                                    ");
        workListQuery.append("        orin.ven_style,                                                                                                ");
        workListQuery.append("        orin.PETSTATUS,                                                                                                ");
        workListQuery.append("        orin.ImageState,                                                                                               ");
        workListQuery.append("        orin.CONTENTSTATUS,                                                                                            ");
        workListQuery.append("        orin.completion_date,                                                                                          ");
        workListQuery.append("        s.omniChannelIndicator,                                                                                        ");
        workListQuery.append("        orin.req_type,                                                                                                 ");
        workListQuery.append("        orin.PET_STYLE_STATE,                                                                                          ");
        workListQuery.append("        orin.PET_STYLE_IMAGE,                                                                                          ");
        workListQuery.append("        orin.PET_STYLE_CONTENT, orin.PET_EARLIEST_COMP_DATE, orin.EXIST_IN_GROUP, orin.CFAS, orin.CONVERSION_FLAG ");
        workListQuery.append("      FROM                                                                                                             ");
        workListQuery.append("        STYLEID_DESC orin,                                                                                           ");
        workListQuery.append("        ADSE_SUPPLIER_CATALOG supplier,                                                                                ");
        workListQuery.append("        XMLTABLE(                                                                                                      ");
        workListQuery.append("        'for $supplier in $supplierCatalog/pim_entry/entry/Supplier_Ctg_Spec                                           ");
        workListQuery.append("        let                                                                                                            ");
        workListQuery.append("        $ven_name := $supplier/Name,                                                                                   ");
        workListQuery.append("        $isOmniChannel := $supplier/../Supplier_Site_Spec/Omni_Channel                                                 ");
        workListQuery.append("        return                                                                                                         ");
        workListQuery.append("        <out>                                                                                                          ");
        workListQuery.append("            <ven_name>{$ven_name}</ven_name>                                                                           ");
        workListQuery.append("            <omni_channel>{if($isOmniChannel eq \"true\") then \"Y\" else \"N\"}</omni_channel>                              ");
        workListQuery.append("        </out>'                                                                                                        ");
        workListQuery.append("        PASSING XMLELEMENT(\"Id\", orin.Supplier_Id) AS \"Suppliers\",                                                     ");
        workListQuery.append("        supplier.XML_DATA                          AS \"supplierCatalog\" columns                                        ");
        workListQuery.append("        ven_name                          VARCHAR2(80) path '/out/ven_name',                                           ");
        workListQuery.append("        omniChannelIndicator VARCHAR2(2) path '/out/omni_channel') s                                                   ");
        workListQuery.append("      WHERE                                                                                                            ");
        workListQuery.append("        orin.Supplier_Id = supplier.mdmid                                                                              "); 
        LOGGER.info("\nGET_ALL_PARENT_ORIN_WITH_PETS_XQUERY---->" + workListQuery.toString());

        return workListQuery.toString();
}


/**
 * Gets the work list display data for Child ORINs.
 *
 * @param Parent ORIN the parentOrin
 * @return the work list display data
 */
public String getWorkListDisplayDataChild(boolean vendorLogin) {
    
     /*if(parentOrin!=null){
         parentOrin = "'"+parentOrin+"'";
         } */       
    
     StringBuilder workListQuery = new StringBuilder();         
     workListQuery.append("WITH ");
     workListQuery.append("  Input(ORIN) AS ");
     workListQuery.append("  ( ");
     workListQuery.append("    SELECT ");         
     workListQuery.append(":parentOrin");
     workListQuery.append(" ORIN ");         
     workListQuery.append("    FROM ");
     workListQuery.append("      dual ");
     workListQuery.append("  ) ");
     workListQuery.append("  , ");
    workListQuery.append("    styleListA as(                                                                                                     ");
    workListQuery.append("      SELECT                                                                           ");
    workListQuery.append("          aic.PARENT_MDMID,                                                                                            ");
    workListQuery.append("          aic.MDMID,                                                                                                   ");
    workListQuery.append("          aic.XML_DATA,                                                                                                ");
    workListQuery.append("          aic.ENTRY_TYPE,                                                                                              ");
    workListQuery.append("          aic.MOD_DTM,                                                                                                 ");
    workListQuery.append("          CASE                                                                                                         ");
    workListQuery.append("          when aic.PRIMARY_UPC is null                                                                                 ");
    workListQuery.append("          then NUMBER_04                                                                                               ");
    workListQuery.append("           when aic.PRIMARY_UPC = ' '                                                                                  ");
    workListQuery.append("           then NUMBER_04                                                                                              ");
    workListQuery.append("          else     aic.PRIMARY_UPC                                                                                     ");
    workListQuery.append("          end PRIMARY_UPC,                                                                                             ");
    workListQuery.append("          aic.DEPT_ID, aic.PRIMARY_SUPPLIER_ID Supplier_Id, aic.PRIMARYSUPPLIERVPN ven_style, aic.PRODUCT_NAME, aic.COLOR_NAME  COLO_DESC              ");
    workListQuery.append("        FROM                                                                                                           ");
    workListQuery.append("          ADSE_ITEM_CATALOG aic, adse_pet_catalog pet, Input inp                                                       ");
    workListQuery.append("        WHERE                                                                                                          ");
    workListQuery.append("              aic.PARENT_MDMID = inp.ORIN                                                                              ");
    workListQuery.append("           AND                                                                                                         ");
    workListQuery.append("            aic.ENTRY_TYPE IN ('StyleColor', 'PackColor')   AND aic.PETEXISTS = 'Y'                                                              ");
    workListQuery.append("              and aic.mdmid = pet.mdmid                                                                                ");
    workListQuery.append("              and pet.pet_state='01' and PRIMARY_SUPPLIER_ID is not null                                               ");
    if(vendorLogin){
        workListQuery.append("              AND (pet.IMAGE_STATUS NOT IN ('08','02') OR pet.CONTENT_STATUS NOT IN ('08','02'))                    ");
    }
    workListQuery.append("  ),                                                                                                                   ");
    workListQuery.append("  styleID_DescAA AS(                                                                                                   ");
    workListQuery.append("      SELECT                                                                ");
    workListQuery.append("        sda.PARENT_MDMID,                                                                                              ");
    workListQuery.append("        sda.MDMID ORIN_NUM,                                                                                            ");
    //workListQuery.append("        sda.XML_DATA,                                                                                                  ");
    workListQuery.append("        sda.ENTRY_TYPE,                                                                                                ");
    workListQuery.append("        sda.PRIMARY_UPC,                                                                                               ");
    workListQuery.append("        sda.COLO_DESC,                                                                                                   ");
    workListQuery.append("        sda.Supplier_Id,                                                                                               ");
    workListQuery.append("        sda.DEPT_ID,                                                                                                   ");
    workListQuery.append("        sda.PRODUCT_NAME,                                                                                                ");
    workListQuery.append("        sda.ven_style,                                                                                                 ");
    workListQuery.append("        pet.pet_state PETSTATUS,                                                                                       ");
    workListQuery.append("        pet.image_status ImageState,                                                                                   ");
    workListQuery.append("        pet.content_status CONTENTSTATUS,                                                                              ");
    workListQuery.append("        p.completion_date,                                                                                             ");
    workListQuery.append("        p.req_type,                                                                                                    ");
    workListQuery.append("        p.CONVERSION_FLAG, pet.EXIST_IN_GROUP, pet.MISSING_ASSET                                           ");
    workListQuery.append("     from                                                                                                              ");
    workListQuery.append("        /*styleID_DescA*/ styleListA sda,                                                                   ");
    workListQuery.append("        ADSE_PET_CATALOG pet,                                                                                          ");
    workListQuery.append("        XMLTABLE(                                                                                                      ");
    workListQuery.append("        'let                                                                                                           ");
    workListQuery.append("        $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date                                         ");
    workListQuery.append("        return                                                                                                         ");
    workListQuery.append("        <out>                                                                                                          ");
    workListQuery.append("          <completion_date>{$completionDate}</completion_date>                                                         ");
    workListQuery.append("          <req_type>{$pets/pim_entry/entry/Pet_Ctg_Spec/SourceSystem}</req_type>                                       ");
    workListQuery.append("            <CONVERSION_FLAG>{$pets/pim_entry/entry/Pet_Ctg_Spec/System/Pet_Information/Conversion_Flag}</CONVERSION_FLAG>      ");
    workListQuery.append("        </out>'                                                                                                        ");
    workListQuery.append("        passing pet.xml_data AS \"pets\" Columns                                                                         ");
    workListQuery.append("        completion_date             VARCHAR2(10)  path  '/out/completion_date',                                        ");
    workListQuery.append("        req_type                    VARCHAR2(20)  path '/out/req_type',                                                ");
    workListQuery.append("        CONVERSION_FLAG VARCHAR2(50) path '/out/CONVERSION_FLAG'                             ");
    workListQuery.append("        ) p                                                                                                            ");
    workListQuery.append("      WHERE                                                                                                            ");
    //Changes to stop displaying deactivated PET in WorkList
    workListQuery.append("        sda.mdmid     =pet.mdmid AND pet.PET_STATE <>'05' and pet.WLIST_DISPLAY_FLAG = 'true'                                               ");
    workListQuery.append("    )                                                                                                                  ");
    workListQuery.append("    SELECT                                                        ");
    workListQuery.append("        CASE                                                                                                           ");
    workListQuery.append("          WHEN LENGTH(ORIN_NUM) > 9                                                                                    ");
    workListQuery.append("          THEN SUBSTR(ORIN_NUM,1,9)                                                                                    ");
    workListQuery.append("          ELSE ORIN_NUM                                                                                                ");
    workListQuery.append("        END Parent_Style_Orin,                                                                                         ");
    workListQuery.append("        ORIN_NUM,                                                                                                      ");
    workListQuery.append("        orin.DEPT_Id,                                                                                                  ");
    workListQuery.append("        orin.Supplier_Id,                                                                                              ");
    workListQuery.append("        orin.PRODUCT_NAME,                                                                                             ");
    workListQuery.append("        orin.ENTRY_TYPE,                                                                                               ");
    workListQuery.append("        orin.PRIMARY_UPC,                                                                                              ");
    workListQuery.append("        orin.COLO_DESC as COLOR_DESC,                                                                                  ");
    workListQuery.append("        s.ven_name,                                                                                                    ");
    workListQuery.append("        orin.ven_style,                                                                                                ");
    workListQuery.append("        orin.PETSTATUS,                                                                                                ");
    workListQuery.append("        orin.ImageState,                                                                                               ");
    workListQuery.append("        orin.CONTENTSTATUS,                                                                                            ");
    workListQuery.append("        orin.completion_date,                                                                                          ");
    workListQuery.append("        s.omniChannelIndicator,                                                                                        ");
    workListQuery.append("        orin.req_type,                                                                                                 ");
    workListQuery.append("        orin.CONVERSION_FLAG, orin.EXIST_IN_GROUP, orin.MISSING_ASSET                                           ");
    workListQuery.append("      FROM                                                                                                             ");
    workListQuery.append("        styleID_DescAA orin,                                                                                           ");
    workListQuery.append("        ADSE_SUPPLIER_CATALOG supplier,                                                                                ");
    workListQuery.append("        XMLTABLE(                                                                                                      ");
    workListQuery.append("        'for $supplier in $supplierCatalog/pim_entry/entry/Supplier_Ctg_Spec                                           ");
    workListQuery.append("        let                                                                                                            ");
    workListQuery.append("        $ven_name := $supplier/Name,                                                                                   ");
    workListQuery.append("        $isOmniChannel := $supplier/../Supplier_Site_Spec/Omni_Channel                                                 ");
    workListQuery.append("        return                                                                                                         ");
    workListQuery.append("        <out>                                                                                                          ");
    workListQuery.append("            <ven_name>{$ven_name}</ven_name>                                                                           ");
    workListQuery.append("            <omni_channel>{if($isOmniChannel eq \"true\") then \"Y\" else \"N\"}</omni_channel>                              ");
    workListQuery.append("        </out>'                                                                                                        ");
    workListQuery.append("        PASSING XMLELEMENT(\"Id\", orin.Supplier_Id) AS \"Suppliers\",                                                     ");
    workListQuery.append("        supplier.XML_DATA                          AS \"supplierCatalog\" columns                                        ");
    workListQuery.append("        ven_name                          VARCHAR2(80) path '/out/ven_name',                                           ");
    workListQuery.append("        omniChannelIndicator VARCHAR2(2) path '/out/omni_channel') s                                                   ");
    workListQuery.append("      WHERE                                                                                                            ");
    workListQuery.append("        orin.Supplier_Id = supplier.mdmid ORDER BY orin.completion_date /*ORIN_NUM*/ DESC                                                       ");
     
     LOGGER.info("\n--------->> GET_CHILD_ORIN_WITH_PETS_XQUERY "
         + workListQuery.toString());

     return workListQuery.toString();
}



/**
 * Gets the search pet list display data for Child ORINs.
 *
 * @param Parent ORIN the parentOrin
 * @return the work list display data
 */
public String getAdvWorkListDisplayDataForChild(AdvanceSearch advSearch, String parentOrin) {
    StringBuilder  advQueryFragment = new StringBuilder();

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd-yyyy");
    Date date = new Date();
            

    String completionFrom = null;
    if(StringUtils.isNotBlank(advSearch.getDateFrom())){
        completionFrom = advSearch.getDateFrom();
        completionFrom = completionFrom.trim();
        try{
                date = formatter1.parse(completionFrom);
                completionFrom = formatter.format(date);
                completionFrom = "'"+completionFrom+"'";
                LOGGER.info("completionFrom----"+completionFrom);
        }catch(Exception pe){
            LOGGER.info("Format parse failed fromDate::");
            pe.printStackTrace();
        }
    }
    
    String completionTo = null;

    if(StringUtils.isNotBlank(advSearch.getDateTo())){
        completionTo =  advSearch.getDateTo();
        completionTo = completionTo.trim();
        try{
                date = formatter1.parse(completionTo);
                completionTo = formatter.format(date);
                completionTo = "'"+completionTo+"'";
                LOGGER.info("completionTo----"+completionTo);
        }catch(ParseException pe){
            LOGGER.info("Format parse failed toDate::");
            pe.printStackTrace();
        }
    }
    
    String imageStatus = null;
    
    if(StringUtils.isNotBlank(advSearch.getImageStatus())){
        String finalImageStatus = getImageStatusCode(advSearch.getImageStatus());
        imageStatus =  "'"+finalImageStatus+"'";
    }
    String contentStatus = null;
    
    if(StringUtils.isNotBlank(advSearch.getContentStatus())){
        String finalContentStatus = getContentStatusCode(advSearch.getContentStatus());
        contentStatus =  "'"+finalContentStatus+"'";
    }
    String petStatus = null;
          
    if(StringUtils.isNotBlank(advSearch.getActive())){
        petStatus =  "'"+advSearch.getActive()+"'"; 
    }
    String requestType = null;
    
    if(StringUtils.isNotBlank(advSearch.getRequestType())){
        requestType =  "'"+advSearch.getRequestType()+"'";
    }
    
    if (null != requestType) {            
        requestType = "'" + getSourceStatusCode(requestType) + "'";
    }
       
    String orin = null;
    
    if(StringUtils.isNotBlank(parentOrin)){
        orin =  "'"+parentOrin+"'";
    }               
    
    String classes = null;
    
    if(StringUtils.isNotBlank(advSearch.getClassNumber())){
        classes =  "'"+advSearch.getClassNumber()+"'";
    }
    
    String createdToday = null;
    
    if(StringUtils.isNotBlank(advSearch.getCreatedToday())){
        createdToday =  "'"+advSearch.getCreatedToday()+"'";
    }
    LOGGER.info("createdToday::inConstants::" +createdToday);                  

    
    advQueryFragment.append(" WITH  ");
    advQueryFragment.append("   Input(CompletionFrom, CompletionTo, ImageStatus, ContentStatus,                            ");
    advQueryFragment.append("   PETStatus, RequestType, ORIN, classes, createdToday) AS                                    ");
    advQueryFragment.append("  (SELECT ");
    advQueryFragment.append(completionFrom);
    advQueryFragment.append("  CompletionFrom, ");
    advQueryFragment.append(completionTo);
    advQueryFragment.append("  CompletionTo, ");
    advQueryFragment.append(imageStatus);
    advQueryFragment.append("  ImageStatus, ");
    advQueryFragment.append(contentStatus);
    advQueryFragment.append("  ContentStatus, ");
    advQueryFragment.append(petStatus);
    advQueryFragment.append("  PETStatus, ");
    advQueryFragment.append(requestType);
    advQueryFragment.append("  RequestType, ");
    advQueryFragment.append(orin);
    advQueryFragment.append("  ORIN, ");
    advQueryFragment.append(classes);
    advQueryFragment.append("  classes, ");
    advQueryFragment.append(createdToday);
    advQueryFragment.append("  createdToday ");
    advQueryFragment.append("    FROM ");
    advQueryFragment.append("      dual ");
    advQueryFragment.append("  ) ");
    advQueryFragment.append("  , ");
    advQueryFragment.append("     styleListA as(                                                                                                           ");
    advQueryFragment.append("           SELECT                                                                            ");
    advQueryFragment.append("               aic.PARENT_MDMID,                                                                                              ");
    advQueryFragment.append("               aic.MDMID,                                                                                                     ");
    advQueryFragment.append("               aic.XML_DATA,                                                                                                  ");
    advQueryFragment.append("               aic.ENTRY_TYPE,                                                                                                ");
    advQueryFragment.append("               aic.MOD_DTM,                                                                                                   ");
    advQueryFragment.append("               CASE                                                                                                           ");
    advQueryFragment.append("               when aic.PRIMARY_UPC is null                                                                                   ");
    advQueryFragment.append("               then NUMBER_04                                                                                                 ");
    advQueryFragment.append("                when aic.PRIMARY_UPC = ' '                                                                                    ");
    advQueryFragment.append("                then NUMBER_04                                                                                                ");
    advQueryFragment.append("               else     aic.PRIMARY_UPC                                                                                       ");
    advQueryFragment.append("               end PRIMARY_UPC,                                                                                               ");
    advQueryFragment.append("               aic.DEPT_ID, aic.PRIMARY_SUPPLIER_ID Supplier_Id, aic.PRIMARYSUPPLIERVPN VEN_STYLE, aic.CLASS_ID clsId,   ");
    advQueryFragment.append("               inp.classes,                                                                                                   ");
    advQueryFragment.append("               inp.CompletionFrom,inp.CompletionTo,                                                                           ");
    advQueryFragment.append("         inp.ImageStatus inpImageStatus,inp.ContentStatus inpContentStatus,                                                   ");
    advQueryFragment.append("         inp.RequestType,inp.createdToday, inp.ORIN PARENT_ORIN, inp.PETStatus INPUTPETSTATUS, aic.PRODUCT_NAME, aic.COLOR_NAME COLO_DESC                                ");
    advQueryFragment.append("             FROM                                                                                                             ");
    advQueryFragment.append("               ADSE_ITEM_CATALOG aic, adse_pet_catalog pet, Input inp                                                         ");
    advQueryFragment.append("             WHERE                                                                                                            ");
    advQueryFragment.append("                 aic.PARENT_MDMID = inp.ORIN AND aic.ENTRY_TYPE IN ('StyleColor', 'PackColor')   AND aic.PETEXISTS = 'Y'  and aic.mdmid = pet.mdmid      ");
    advQueryFragment.append("              AND (classes IS NULL OR aic.CLASS_ID  IN                                                             ");
    advQueryFragment.append("                 to_char(( SELECT regexp_substr(classes,'[^,]+',1,LEVEL) FROM dual CONNECT BY regexp_substr(classes,'[^,]+',1,LEVEL) IS NOT NULL )) ");
    advQueryFragment.append("                 )                                                                                                            ");
    advQueryFragment.append("              and PRIMARY_SUPPLIER_ID is not null                                                                             ");
    advQueryFragment.append("       ),                                                                                                                      ");
    advQueryFragment.append("     styleID_DescAA AS(                                                                                                          ");
    advQueryFragment.append("       SELECT                                                                                                                    ");
    advQueryFragment.append("         sda.PARENT_MDMID,                                                                                                       ");
    advQueryFragment.append("         sda.MDMID,                                                                                                              ");
    advQueryFragment.append("         sda.XML_DATA,                                                                                                           ");
    advQueryFragment.append("         sda.ENTRY_TYPE,                                                                                                         ");
    advQueryFragment.append("         sda.PRIMARY_UPC,                                                                                                        ");
    advQueryFragment.append("         sda.COLO_DESC,                                                                                                            ");
    advQueryFragment.append("         sda.Supplier_Id,                                                                                                        ");
    advQueryFragment.append("         sda.DEPT_ID,                                                                                                            ");
    advQueryFragment.append("         sda.PRODUCT_NAME,                                                                                                         ");
    advQueryFragment.append("         sda.ven_style,                                                                                                          ");
    advQueryFragment.append("         sda.clsId,                                                                                                              ");
    advQueryFragment.append("         sda.INPUTPETSTATUS,                                                                                                     ");
    advQueryFragment.append("         sda.PARENT_ORIN,                                                                                                        ");
    advQueryFragment.append("         p.completion_date,                                                                                                      ");
    advQueryFragment.append("         p.req_type,                                                                                                             ");
    advQueryFragment.append("         pet.pet_state PETSTATUS,                                                                                                ");
    advQueryFragment.append("         pet.image_status ImageState,                                                                                            ");
    advQueryFragment.append("         pet.content_status CONTENTSTATUS,                                                                                       ");    
    advQueryFragment.append("         p.CONVERSION_FLAG, pet.EXIST_IN_GROUP, pet.MISSING_ASSET                                                         ");
    advQueryFragment.append("   FROM                                                                                                                          ");
    advQueryFragment.append("     /*styleID_DescA*/ styleListA sda,                                                                                           ");
    advQueryFragment.append("     ADSE_PET_CATALOG pet,                                                                                                       ");
    advQueryFragment.append("     XMLTABLE(                                                                                                                   ");
    advQueryFragment.append("     'let                                                                                                                        ");
    advQueryFragment.append("     $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date                                                      ");
    advQueryFragment.append("     return                                                                                                                      ");
    advQueryFragment.append("     <out>                                                                                                                       ");
    advQueryFragment.append("         <completion_date>{$completionDate}</completion_date>                                                                    ");
    advQueryFragment.append("         <req_type>{$pets/pim_entry/entry/Pet_Ctg_Spec/SourceSystem}</req_type>                                                  ");           
    advQueryFragment.append("         <CONVERSION_FLAG>{$pets/pim_entry/entry/Pet_Ctg_Spec/System/Pet_Information/Conversion_Flag}</CONVERSION_FLAG>        ");
    advQueryFragment.append("     </out>'                                                                                                                     ");
    advQueryFragment.append("     passing pet.xml_data AS \"pets\" Columns                                                                                      ");
    advQueryFragment.append("         completion_date      VARCHAR2(10) path  '/out/completion_date',                                                         ");
    advQueryFragment.append("         req_type             VARCHAR2(20) path '/out/req_type',                                                                 ");    
    advQueryFragment.append("         CONVERSION_FLAG VARCHAR2(50) path '/out/CONVERSION_FLAG'                                              ");
    advQueryFragment.append("     )p                                                                                                                          ");
    advQueryFragment.append("   WHERE                                                                                                                         ");
    advQueryFragment.append("     sda.MDMID     =pet.mdmid                                                                                                    ");
    advQueryFragment.append("   AND                                                                                                                           ");
    advQueryFragment.append("     (                                                                                                                           ");
    advQueryFragment.append("       CompletionFrom IS NULL                                                                                                    ");
    advQueryFragment.append("     OR LAST_PO_DATE   >=TO_DATE(CompletionFrom, 'YYYY-MM-DD')                                                                                        ");
    advQueryFragment.append("     )                                                                                                                           ");
    advQueryFragment.append("   AND                                                                                                                           ");
    advQueryFragment.append("     (                                                                                                                           ");
    advQueryFragment.append("       CompletionTo IS NULL                                                                                                      ");
    advQueryFragment.append("     OR LAST_PO_DATE <=TO_DATE(CompletionTo, 'YYYY-MM-DD')                                                                                           ");
    advQueryFragment.append("     )                                                                                                                           ");
    advQueryFragment.append("   AND                                                                                                                           ");
    advQueryFragment.append("     (                                                                                                                           ");
    advQueryFragment.append("       inpImageStatus IS NULL                                                                                                    ");
    advQueryFragment.append("     OR pet.image_status   IN                                                                                                    ");
    advQueryFragment.append("       to_char((                                                                                                                         ");
    advQueryFragment.append("         SELECT                                                                                                                  ");
    advQueryFragment.append("           regexp_substr(inpImageStatus,'[^,]+',1,LEVEL)                                                                         ");
    advQueryFragment.append("         FROM                                                                                                                    ");
    advQueryFragment.append("           dual                                                                                                                  ");
    advQueryFragment.append("           CONNECT BY regexp_substr(inpImageStatus,'[^,]+',1,LEVEL) IS NOT NULL                                                  ");
    advQueryFragment.append("       ))                                                                                                                         ");
    advQueryFragment.append("     )                                                                                                                           ");
    advQueryFragment.append("   AND                                                                                                                           ");
    advQueryFragment.append("     (                                                                                                                           ");
    advQueryFragment.append("       inpContentStatus IS NULL                                                                                                  ");
    advQueryFragment.append("     OR pet.content_status    IN                                                                                                 ");
    advQueryFragment.append("       (                                                                                                                         ");
    advQueryFragment.append("         SELECT                                                                                                                  ");
    advQueryFragment.append("           regexp_substr(inpContentStatus,'[^,]+',1,LEVEL)                                                                       ");
    advQueryFragment.append("         FROM                                                                                                                    ");
    advQueryFragment.append("           dual                                                                                                                  ");
    advQueryFragment.append("           CONNECT BY regexp_substr(inpContentStatus,'[^,]+',1,LEVEL) IS NOT NULL                                                ");
    advQueryFragment.append("       )                                                                                                                         ");
    advQueryFragment.append("     )                                                                                                                           ");
    advQueryFragment.append("   AND                                                                                                                           ");
    advQueryFragment.append("     (                                                                                                                           ");
    advQueryFragment.append("       RequestType IS NULL                                                                                                       ");
    advQueryFragment.append("     OR Req_Type       IN                                                                                                        ");
    advQueryFragment.append("       (                                                                                                                         ");
    advQueryFragment.append("         SELECT                                                                                                                  ");
    advQueryFragment.append("           regexp_substr(RequestType,'[^,]+',1,LEVEL)                                                                            ");
    advQueryFragment.append("         FROM                                                                                                                    ");
    advQueryFragment.append("           dual                                                                                                                  ");
    advQueryFragment.append("           CONNECT BY regexp_substr(RequestType,'[^,]+',1,LEVEL) IS NOT NULL                                                     ");
    advQueryFragment.append("       )                                                                                                                         ");
    advQueryFragment.append("     )                                                                                                                           ");
    advQueryFragment.append("   AND                                                                                                                           ");
    advQueryFragment.append("     (                                                                                                                           ");
    advQueryFragment.append("       createdToday                           IS NULL                                                                            ");
    advQueryFragment.append("     OR createdToday                          <>'yes'                                                                            ");
    advQueryFragment.append("     OR to_date(SUBSTR(pet.CREATED_DTM, 1, 9), 'YYYY-MM-DD') = to_date(SUBSTR(CURRENT_TIMESTAMP, 1, 9), 'YYYY-MM-DD')            ");
    advQueryFragment.append("     )                                                                                                                           ");
    advQueryFragment.append("   ),                                                                                                                            ");
    advQueryFragment.append("   supplierDetails AS                                                                                                            ");
    advQueryFragment.append("     (                                                                                                                           ");
    advQueryFragment.append("       SELECT                                                                                                                    ");
    advQueryFragment.append("         orin.PARENT_MDMID,                                                                                                      ");
    advQueryFragment.append("         orin.MDMID,                                                                                                             ");
    advQueryFragment.append("         orin.ENTRY_TYPE,                                                                                                        ");
    advQueryFragment.append("         orin.COLO_DESC,                                                                                                         ");
    advQueryFragment.append("         orin.DEPT_Id,                                                                                                           ");
    advQueryFragment.append("         orin.PRODUCT_NAME,                                                                                                      ");
    advQueryFragment.append("         orin.ven_style,                                                                                                         ");
    advQueryFragment.append("         orin.Supplier_Id,                                                                                                       ");
    advQueryFragment.append("         orin.PRIMARY_UPC,                                                                                                       ");
    advQueryFragment.append("         orin.clsId,                                                                                                             ");
    advQueryFragment.append("         orin.INPUTPETSTATUS,                                                                                                    ");
    advQueryFragment.append("         orin.completion_date,                                                                                                   ");
    advQueryFragment.append("         orin.req_type,                                                                                                          ");
    advQueryFragment.append("         orin.ImageState,                                                                                                        ");
    advQueryFragment.append("         orin.CONTENTSTATUS,                                                                                                     ");
    advQueryFragment.append("         orin.PETSTATUS,                                                                                                         ");
    advQueryFragment.append("         s.ven_name,                                                                                                             ");
    advQueryFragment.append("         s.omniChannelIndicator,                                                                                                 ");    
    advQueryFragment.append("         orin.PARENT_ORIN,                                                                                                       ");
    advQueryFragment.append("         orin.CONVERSION_FLAG, orin.EXIST_IN_GROUP, orin.MISSING_ASSET                                                                         ");
    advQueryFragment.append("       FROM                                                                                                                      ");
    advQueryFragment.append("         styleID_DescAA orin,                                                                                                    ");
    advQueryFragment.append("         ADSE_SUPPLIER_CATALOG supplier,                                                                                         ");
    advQueryFragment.append("         XMLTABLE(                                                                                                               ");
    advQueryFragment.append("         'let                                                                                                                    ");
    advQueryFragment.append("         $ven_name := $supplierCatalog/pim_entry/entry/Supplier_Ctg_Spec/Name,                                                   ");
    advQueryFragment.append("         $isOmniChannel := $supplierCatalog/pim_entry/entry/Supplier_Site_Spec/Omni_Channel                                      ");
    advQueryFragment.append("         return                                                                                                                  ");
    advQueryFragment.append("         <out>                                                                                                                   ");
    advQueryFragment.append("             <ven_name>{$ven_name}</ven_name>                                                                                    ");
    advQueryFragment.append("             <omni_channel>{if($isOmniChannel eq \"true\") then \"Y\" else \"N\"}</omni_channel>                                       ");
    advQueryFragment.append("         </out>'                                                                                                                 ");
    advQueryFragment.append("         PASSING XMLELEMENT(\"Id\", orin.Supplier_Id) AS \"Suppliers\",                                                              ");
    advQueryFragment.append("         supplier.XML_DATA                          AS \"supplierCatalog\" columns                                                 ");
    advQueryFragment.append("         ven_name                          VARCHAR2(80) path '/out/ven_name',                                                    ");
    advQueryFragment.append("         omniChannelIndicator VARCHAR2      (2) path '/out/omni_channel') s                                                      ");
    advQueryFragment.append("       WHERE                                                                                                                     ");
    advQueryFragment.append("       orin.Supplier_Id = supplier.mdmid                                                                                         ");
    advQueryFragment.append("      )                                                                                                                          ");
    advQueryFragment.append("       SELECT                                                                                                                    ");
    advQueryFragment.append("         CASE                                                                                                                    ");
    advQueryFragment.append("         WHEN LENGTH(MDMID) > 9                                                                                                  ");
    advQueryFragment.append("           THEN SUBSTR(MDMID,1,9)                                                                                                ");
    advQueryFragment.append("           ELSE MDMID                                                                                                            ");
    advQueryFragment.append("         END Parent_Style_Orin,                                                                                                  ");
    advQueryFragment.append("         sia.MDMID ORIN_NUM,                                                                                                     ");
    advQueryFragment.append("         sia.ENTRY_TYPE,                                                                                                         ");
    advQueryFragment.append("         sia.COLO_DESC,                                                                                                          ");
    advQueryFragment.append("         sia.Supplier_Id,                                                                                                        ");
    advQueryFragment.append("         sia.DEPT_ID,                                                                                                            ");
    advQueryFragment.append("         sia.PRODUCT_NAME,                                                                                                       ");
    advQueryFragment.append("         sia.ven_style,                                                                                                          ");
    advQueryFragment.append("         sia.PRIMARY_UPC,                                                                                                        ");
    advQueryFragment.append("         sia.clsId,                                                                                                              ");
    advQueryFragment.append("         sia.completion_date,                                                                                                    ");
    advQueryFragment.append("         sia.req_type,                                                                                                           ");
    advQueryFragment.append("         sia.ImageState,                                                                                                         ");
    advQueryFragment.append("         sia.CONTENTSTATUS,                                                                                                      ");
    advQueryFragment.append("         sia.PETSTATUS,                                                                                                          ");
    advQueryFragment.append("         sia.ven_name,                                                                                                           ");
    advQueryFragment.append("         sia.omniChannelIndicator,                                                                                               ");    
    advQueryFragment.append("         sia.CONVERSION_FLAG, sia.EXIST_IN_GROUP, sia.MISSING_ASSET                                                                            ");
    advQueryFragment.append("       from supplierDetails sia                                                                                                  ");
    advQueryFragment.append("       where                                                                                                                     ");
    advQueryFragment.append("           entry_type IN ('StyleColor', 'PackColor')                                                                             ");
    advQueryFragment.append("           AND PARENT_MDMID = PARENT_ORIN                                                                                        ");
    advQueryFragment.append("           AND (INPUTPETSTATUS IS NULL OR PETSTATUS = INPUTPETSTATUS) ORDER BY sia.completion_date /*sia.MDMID*/ DESC                                    "); 
    
    LOGGER.info("getAdvWorkListDisplayDataForChild Query"+advQueryFragment.toString());
    
    return advQueryFragment.toString();                 
 } 


/**
 * Gets the search pet list display data for Parent ORINs.
 *
 * @param Parent ORIN the parentOrin
 * @return the work list display data
 */
public String getAdvWorkListDisplayDataForParent(AdvanceSearch advSearch) {
    StringBuilder  advQueryFragment = new StringBuilder();
    String depts = null;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd-yyyy");
    Date date = new Date();
    
    if(StringUtils.isNotBlank(advSearch.getDeptNumbers())){
        depts =  "'"+advSearch.getDeptNumbers()+"'";
    }

    String completionFrom = null;
    if(StringUtils.isNotBlank(advSearch.getDateFrom())){
        completionFrom = advSearch.getDateFrom();
        completionFrom = completionFrom.trim();
        try{
                date = formatter1.parse(completionFrom);
                completionFrom = formatter.format(date);
                completionFrom = "'"+completionFrom+"'";
                LOGGER.info("completionFrom----"+completionFrom);
        }catch(Exception pe){
            LOGGER.info("Format parse failed fromDate::");
            pe.printStackTrace();
        }
    }
    
    String completionTo = null;

    if(StringUtils.isNotBlank(advSearch.getDateTo())){
        completionTo =  advSearch.getDateTo();
        completionTo = completionTo.trim();
        try{
                date = formatter1.parse(completionTo);
                completionTo = formatter.format(date);
                completionTo = "'"+completionTo+"'";
                LOGGER.info("completionTo----"+completionTo);
        }catch(ParseException pe){
            LOGGER.info("Format parse failed toDate::");
            pe.printStackTrace();
        }
    }
    
    String imageStatus = null;
    
    if(StringUtils.isNotBlank(advSearch.getImageStatus())){
        String finalImageStatus = getImageStatusCode(advSearch.getImageStatus());
        imageStatus =  "'"+finalImageStatus+"'";
    }
    String contentStatus = null;
    
    if(StringUtils.isNotBlank(advSearch.getContentStatus())){
        String finalContentStatus = getContentStatusCode(advSearch.getContentStatus());
        contentStatus =  "'"+finalContentStatus+"'";
    }
    String petStatus = null;
          
    if(StringUtils.isNotBlank(advSearch.getActive())){
        petStatus =  "'"+advSearch.getActive()+"'"; 
    }
    String requestType = null;
    
    if(StringUtils.isNotBlank(advSearch.getRequestType())){
        requestType =  "'"+advSearch.getRequestType()+"'";
    }
    
    if (null != requestType) {            
        requestType = "'" + getSourceStatusCode(requestType) + "'";
    }
       
    String orin = null;
    
    if(StringUtils.isNotBlank(advSearch.getOrin())){
        orin =  "'"+advSearch.getOrin()+"'";
    }
    
    String vendorStyle = null;
    
    if(StringUtils.isNotBlank(advSearch.getVendorStyle())){
        vendorStyle =  "'%"+advSearch.getVendorStyle().toUpperCase()+"%'";
    }
    
    String upc = null;
    
    if(StringUtils.isNotBlank(advSearch.getUpc())){
        upc =  "'"+advSearch.getUpc()+"'";
    }
    
    String classes = null;
    
    if(StringUtils.isNotBlank(advSearch.getClassNumber())){
        classes =  "'"+advSearch.getClassNumber()+"'";
    }
    
    String createdToday = null;
    
    if(StringUtils.isNotBlank(advSearch.getCreatedToday())){
        createdToday =  "'"+advSearch.getCreatedToday()+"'";
    }
    LOGGER.info("createdToday::inConstants::" +createdToday);
    
    String vendorNumber = null;
    
    if(StringUtils.isNotBlank(advSearch.getVendorNumber())){
        vendorNumber =  "'"+advSearch.getVendorNumber()+"'";
        
    }    
    advQueryFragment
	.append("WITH Input(Depts, CompletionFrom, CompletionTo, ImageStatus, ContentStatus, PETStatus, RequestType, ORIN, VENDOR_STYLE, classes, createdToday, vendor, primaryUpc) AS ");
	advQueryFragment.append("  (SELECT ");
	advQueryFragment.append(depts);
	advQueryFragment.append(" Depts, ");
	advQueryFragment.append(completionFrom);
	advQueryFragment.append("  CompletionFrom, ");
	advQueryFragment.append(completionTo);
	advQueryFragment.append("  CompletionTo, ");
	advQueryFragment.append(imageStatus);
	advQueryFragment.append("  ImageStatus, ");
	advQueryFragment.append(contentStatus);
	advQueryFragment.append("  ContentStatus, ");
	advQueryFragment.append(petStatus);
	advQueryFragment.append("  PETStatus, ");
	advQueryFragment.append(requestType);
	advQueryFragment.append("  RequestType, ");
	advQueryFragment.append(orin);
	advQueryFragment.append("  ORIN, ");
	advQueryFragment.append(vendorStyle);
	advQueryFragment.append("  VENDOR_STYLE, ");
	advQueryFragment.append(classes);
	advQueryFragment.append("  classes, ");
	advQueryFragment.append(createdToday);
	advQueryFragment.append("  createdToday, ");
	advQueryFragment.append(vendorNumber);
	advQueryFragment.append("  vendor, ");
	advQueryFragment.append(upc);
	advQueryFragment.append("  primaryUpc ");
	advQueryFragment.append("    FROM ");
	advQueryFragment.append("      dual )");
	advQueryFragment.append("          SELECT    (");
	advQueryFragment.append("                    CASE");
	advQueryFragment.append("                              WHEN item.parent_mdmid IS NULL THEN item.mdmid");
	advQueryFragment.append("                              WHEN item.parent_mdmid IS NOT NULL THEN item.parent_mdmid");
	advQueryFragment.append("                    END)         parent_style_orin,");
	advQueryFragment.append("                    item.mdmid   orin_num,");
	advQueryFragment.append("                    item.dept_id dept_id,");
	advQueryFragment.append("                    item.primary_supplier_id,");
	advQueryFragment.append("                    item.product_name,");
	advQueryFragment.append("                    item.entry_type,");
	advQueryFragment.append("                    item.number_04                                                                                                                   primary_upc,");
	advQueryFragment.append("                    xmlcast( (Xmlquery('pim_entry/entry/Supplier_Ctg_Spec/Name' passing supplier.xml_data returning content ) ) as VARCHAR2(80) )    ven_name,");
	advQueryFragment.append("                    item.primarysuppliervpn                                                                                                          vsn_style,");
	advQueryFragment.append("                    item.class_id                                                                                                                    clsid,");
	advQueryFragment.append("                    pet.pet_state                                                                                                                    petstatus,");
	advQueryFragment.append("                    pet.image_status                                                                                                                 imagestate,");
	advQueryFragment.append("                    pet.content_status                                                                                                               contentstatus,");
	advQueryFragment.append("                    xmlcast( (xmlquery('//pim_entry/entry/Pet_Ctg_Spec/Completion_Date' passing pet.xml_data returning content ) ) AS VARCHAR2(10) ) completion_date, (");
	advQueryFragment.append("                    CASE");
	advQueryFragment.append("                              WHEN (");
	advQueryFragment.append("                                                  (");
	advQueryFragment.append("                                                            xmlcast( (xmlquery('pim_entry/entry/Supplier_Site_Spec/Omni_Channel' passing supplier.xml_data returning content ) ) AS VARCHAR2(2) ))='true') THEN 'Y'");
	advQueryFragment.append("                              ELSE 'N'");
	advQueryFragment.append("                    END)                                                                                                                          omnichannelindicator,");
	advQueryFragment.append("                    xmlcast( (xmlquery('//pim_entry/entry/Pet_Ctg_Spec/SourceSystem' passing pet.xml_data returning content ) ) AS VARCHAR2(20) ) xml_req_type,");
	advQueryFragment.append("                    pet.pet_earliest_comp_date ,");
	advQueryFragment.append("                    pet.exist_in_group,");
	advQueryFragment.append("                    xmlcast( (xmlquery('//pim_entry/entry/Ecomm_Style_Spec/Split_Color_Eligible' passing pet.xml_data returning content ) )               AS VARCHAR2(50) ) cfas,");
	advQueryFragment.append("                    xmlcast( (xmlquery('//pim_entry/entry/Pet_Ctg_Spec/System/Pet_Information/Conversion_Flag' passing pet.xml_data returning content ) ) AS VARCHAR2(50) ) conversion_flag,");
	advQueryFragment.append("                    pet.pet_style_state,");
	advQueryFragment.append("                    pet.missing_asset");
	advQueryFragment.append("          FROM      input inp,");
	advQueryFragment.append("                    vendorportal.adse_pet_catalog pet");
	advQueryFragment.append("          left join vendorportal.adse_item_catalog item");
	advQueryFragment.append("          ON        item.mdmid=pet.mdmid");
	advQueryFragment.append("          inner join vendorportal.adse_supplier_catalog supplier");
	advQueryFragment.append("          ON        supplier.mdmid=item.primary_supplier_id");
	advQueryFragment.append("          WHERE     item.entry_type in ('Style','Complex Pack')");
	advQueryFragment.append("          AND       item.petexists = 'Y'");
	advQueryFragment.append("          AND       (");
	advQueryFragment.append("                              inp.orin IS NULL");
	advQueryFragment.append("                    OR        pet.mdmid= inp.orin)");
	advQueryFragment.append("          AND       (");
	advQueryFragment.append("                              inp.primaryupc IS NULL");
	advQueryFragment.append("                    OR        item.primary_upc = inp.primaryupc");
	advQueryFragment.append("                    OR        item.number_04 = inp.primaryupc )");
	advQueryFragment.append("          AND       (");
	advQueryFragment.append("                              inp.depts IS NULL");
	advQueryFragment.append("                    OR        item.dept_id IN ");
	advQueryFragment.append("                                                       (");
	advQueryFragment.append("                                                       SELECT regexp_substr(inp.depts,'[^,]+',1,LEVEL)");
	advQueryFragment.append("                                                       FROM   dual");
	advQueryFragment.append("                                                              CONNECT BY regexp_substr(inp.depts,'[^,]+',1,LEVEL) IS NOT NULL ) )");
	advQueryFragment.append("          AND       (");
	advQueryFragment.append("                              inp.vendor IS NULL");
	advQueryFragment.append("                    OR        item.primary_supplier_id = inp.vendor )");
	advQueryFragment.append("          AND       (");
	advQueryFragment.append("                              inp.vendor_style IS NULL");
	advQueryFragment.append("                    OR        upper(item.primarysuppliervpn) LIKE inp.vendor_style )");
	advQueryFragment.append("          AND       item.primary_supplier_id IS NOT NULL");
	advQueryFragment.append("          AND       (");
	advQueryFragment.append("                              inp.completionfrom IS NULL");
	advQueryFragment.append("                    OR        (");
	advQueryFragment.append("                                        trunc(pet.pet_earliest_comp_date) >=to_date(inp.completionfrom, 'YYYY-MM-DD')) )");
	advQueryFragment.append("          AND       (");
	advQueryFragment.append("                              completionto IS NULL");
	advQueryFragment.append("                    OR        (");
	advQueryFragment.append("                                        trunc(pet.pet_earliest_comp_date) <=to_date(inp.completionto, 'YYYY-MM-DD')) )");
	advQueryFragment.append("          AND       (");
	advQueryFragment.append("                              inp.imagestatus IS NULL");
	advQueryFragment.append("                    OR        pet.image_status IN to_char(");
	advQueryFragment.append("                                                            (");
	advQueryFragment.append("                                                            SELECT regexp_substr(inp.imagestatus,'[^,]+',1,LEVEL)");
	advQueryFragment.append("                                                            FROM   dual");
	advQueryFragment.append("                                                                   CONNECT BY regexp_substr(inp.imagestatus,'[^,]+',1,LEVEL) IS NOT NULL )) )");
	advQueryFragment.append("          AND       (");
	advQueryFragment.append("                              inp.contentstatus IS NULL");
	advQueryFragment.append("                    OR        pet.content_status IN to_char(");
	advQueryFragment.append("                                                              (");
	advQueryFragment.append("                                                              SELECT regexp_substr(inp.contentstatus,'[^,]+',1,LEVEL)");
	advQueryFragment.append("                                                              FROM   dual");
	advQueryFragment.append("                                                                     CONNECT BY regexp_substr(inp.contentstatus,'[^,]+',1,LEVEL) IS NOT NULL )) )");
	advQueryFragment.append("          AND       (");
	advQueryFragment.append("                              inp.petstatus IS NULL");
	advQueryFragment.append("                    OR        pet.pet_state = inp.petstatus)");
	advQueryFragment.append("          AND       (");
	advQueryFragment.append("                              inp.requesttype IS NULL");
	advQueryFragment.append("                    OR        xmlcast( (xmlquery('//pim_entry/entry/Pet_Ctg_Spec/SourceSystem' passing pet.xml_data returning content ) ) AS VARCHAR2(20) ) IN to_char(");
	advQueryFragment.append("                                                                                                                                                                         (");
	advQueryFragment.append("                                                                                                                                                                         SELECT regexp_substr(inp.requesttype,'[^,]+',1,LEVEL)");
	advQueryFragment.append("                                                                                                                                                                         FROM   dual");
	advQueryFragment.append("                                                                                                                                                                                CONNECT BY regexp_substr(inp.requesttype,'[^,]+',1,LEVEL) IS NOT NULL ) ) )");
	advQueryFragment.append("          AND       (");
	advQueryFragment.append("                              inp.classes IS NULL");
	advQueryFragment.append("                    OR        item.class_id IN to_char(");
	advQueryFragment.append("                                                         (");
	advQueryFragment.append("                                                         SELECT regexp_substr(classes,'[^,]+',1,LEVEL)");
	advQueryFragment.append("                                                         FROM   dual");
	advQueryFragment.append("                                                                CONNECT BY regexp_substr(classes,'[^,]+',1,LEVEL) IS NOT NULL ) ) )");
	advQueryFragment.append("          AND       (");
	advQueryFragment.append("                              inp.createdtoday IS NULL");
	advQueryFragment.append("                    OR        inp.createdtoday <>'yes'");
	advQueryFragment.append("                    OR        TRUNC(pet.CREATED_DTM) = TRUNC(sysdate) )");
	advQueryFragment.append("          UNION ALL");
	advQueryFragment.append("          SELECT    (");
	advQueryFragment.append("                    CASE");
	advQueryFragment.append("                              WHEN item.parent_mdmid IS NULL THEN item.mdmid");
	advQueryFragment.append("                              WHEN item.parent_mdmid IS NOT NULL THEN item.parent_mdmid");
	advQueryFragment.append("                    END)         parent_style_orin,");
	advQueryFragment.append("                    item.mdmid   orin_num,");
	advQueryFragment.append("                    item.dept_id dept_id,");
	advQueryFragment.append("                    item.primary_supplier_id,");
	advQueryFragment.append("                    item.product_name,");
	advQueryFragment.append("                    item.entry_type,");
	advQueryFragment.append("                    item.number_04                                                                                                                   primary_upc,");
	advQueryFragment.append("                    xmlcast( (xmlquery('pim_entry/entry/Supplier_Ctg_Spec/Name' passing supplier.xml_data returning content ) ) AS VARCHAR2(80) )    ven_name,");
	advQueryFragment.append("                    item.primarysuppliervpn                                                                                                          vsn_style,");
	advQueryFragment.append("                    item.class_id                                                                                                                    clsid,");
	advQueryFragment.append("                    pet.pet_state                                                                                                                    petstatus,");
	advQueryFragment.append("                    pet.image_status                                                                                                                 imagestate,");
	advQueryFragment.append("                    pet.content_status                                                                                                               contentstatus,");
	advQueryFragment.append("                    xmlcast( (xmlquery('//pim_entry/entry/Pet_Ctg_Spec/Completion_Date' passing pet.xml_data returning content ) ) AS VARCHAR2(10) ) completion_date, (");
	advQueryFragment.append("                    CASE");
	advQueryFragment.append("                              WHEN (");
	advQueryFragment.append("                                                  (");
	advQueryFragment.append("                                                            xmlcast( (xmlquery('pim_entry/entry/Supplier_Site_Spec/Omni_Channel' passing supplier.xml_data returning content ) ) AS VARCHAR2(2) ))='true') THEN 'Y'");
	advQueryFragment.append("                              ELSE 'N'");
	advQueryFragment.append("                    END)                                                                                                                          omnichannelindicator,");
	advQueryFragment.append("                    xmlcast( (xmlquery('//pim_entry/entry/Pet_Ctg_Spec/SourceSystem' passing pet.xml_data returning content ) ) AS VARCHAR2(20) ) xml_req_type,");
	advQueryFragment.append("                    pet.pet_earliest_comp_date ,");
	advQueryFragment.append("                    pet.exist_in_group,");
	advQueryFragment.append("                    xmlcast( (xmlquery('//pim_entry/entry/Ecomm_Style_Spec/Split_Color_Eligible' passing pet.xml_data returning content ) )               AS VARCHAR2(50) ) cfas,");
	advQueryFragment.append("                    xmlcast( (xmlquery('//pim_entry/entry/Pet_Ctg_Spec/System/Pet_Information/Conversion_Flag' passing pet.xml_data returning content ) ) AS VARCHAR2(50) ) conversion_flag,");
	advQueryFragment.append("                    pet.pet_style_state,");
	advQueryFragment.append("                    pet.missing_asset");
	advQueryFragment.append("          FROM      input inp,");
	advQueryFragment.append("                    vendorportal.adse_pet_catalog pet");
	advQueryFragment.append("          left join vendorportal.adse_item_catalog item");
	advQueryFragment.append("          ON        item.mdmid=pet.mdmid");
	advQueryFragment.append("          inner join vendorportal.adse_supplier_catalog supplier");
	advQueryFragment.append("          ON        supplier.mdmid=item.primary_supplier_id");
	advQueryFragment.append("          WHERE     pet.mdmid IN");
	advQueryFragment.append("                    (");
	advQueryFragment.append("                           SELECT parent_mdmid");
	advQueryFragment.append("                           FROM   vendorportal.adse_item_catalog");
	advQueryFragment.append("                           WHERE  entry_type = 'SKU'");
	advQueryFragment.append("                           AND    inp.primaryupc IS NOT NULL");
	advQueryFragment.append("                           AND    primary_upc = inp.primaryupc) ");    //StyleList ends
    LOGGER.info("getAdvWorkListDisplayDataForParent Query"+advQueryFragment.toString());
    
    return advQueryFragment.toString();
    
    
 } 

    /**
     * Gets the departments in correct format.
     *
     * @param deptId
     * @return finalString
     * 
     * Modified by AFUAXK4
     * Date: 02/09/2016
     */     
    public String getDeptNumbersFormatted(String deptId) {
        
        String finalString = "";
        if(null != deptId && deptId.trim().length() > 0)
        {
            String[] breakDept = deptId.split(",");
            for(int count = 0; count < breakDept.length; count++)
            {
                finalString = finalString + "'" + breakDept[count].trim() + "',";
            }
            if(finalString.length() > 0)
            {
                finalString = finalString.substring(0, finalString.length() - 1);
            }
        }
        else
        {
            finalString = "''";
        }
        
        return finalString;
        
    }
    /**
     * Modification End AFUAXK4
     * Date: 02/09/2016
     */
    
    public String getAdvWorkListDetailsUsingGrouping(String groupingId){
        String getAdvWorkListDetailsUsingGroupingQuery = 
            "   SELECT                                                                                                  "
            +"  GROUP_NAME,MDMID FROM VENDORPORTAL.ADSE_GROUP_CATALOG                                                   ";
        if(groupingId != null){
            getAdvWorkListDetailsUsingGroupingQuery = getAdvWorkListDetailsUsingGroupingQuery
                +"  WHERE MDMID = ?";
        }
           
        return getAdvWorkListDetailsUsingGroupingQuery;
    }
    
    /**
     * This method will build the query for Group Search for Adv Search PET only
     * 
     * @param AdvanceSearch request   
     * @param List<String> strStyleORIN
     * @return String
     * 
     * Method added For PIM Phase 2 - Group Search
     * Date: 05/18/2016
     * Added By: Cognizant
     */
    public String getGroupSearchQueryForAdvSearch(AdvanceSearch objAdvanceSearch, List<String> lstStyleOrin) {
            LOGGER.info("Entering getGroupSearchQueryForAdvSearch");
        LOGGER.info("Entering getGroupSearchQueryForAdvSearch");
        StringBuilder  strbAdvSearch = new StringBuilder();
        SimpleDateFormat sdfYearFirst = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfMonthFirst = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        StringBuilder strbStyleOrinList = new StringBuilder();
        List<String> lstStyleOrinQueryParam = new ArrayList<String>();
        int count = 0;
        if(lstStyleOrin.size() > 0){ 
            for(String strStyleORIN : lstStyleOrin){
                strbStyleOrinList.append("'"+strStyleORIN+"',");
                count++;
                if(count>999){
                    lstStyleOrinQueryParam.add(strbStyleOrinList.substring(0, strbStyleOrinList.length() - 1));
                    count=0;
                    strbStyleOrinList = new StringBuilder();
                }
            }
        }
        if(count != 0)
            lstStyleOrinQueryParam.add(strbStyleOrinList.substring(0, strbStyleOrinList.length() - 1));
        
        String strCompletionFrom = null;
        String strCompletionTo = null;
        try{
        if(StringUtils.isNotBlank(objAdvanceSearch.getDateFrom())){
            strCompletionFrom = objAdvanceSearch.getDateFrom();
            strCompletionFrom = strCompletionFrom.trim();
            date = sdfMonthFirst.parse(strCompletionFrom);
           strCompletionFrom = sdfYearFirst.format(date);
            strCompletionFrom = "'"+strCompletionFrom+"'";
            LOGGER.debug("strCompletionFrom--"+strCompletionFrom);
        }
        if(StringUtils.isNotBlank(objAdvanceSearch.getDateTo())){
            strCompletionTo =  objAdvanceSearch.getDateTo();
            strCompletionTo = strCompletionTo.trim();
            date = sdfMonthFirst.parse(strCompletionTo);
            strCompletionTo = sdfYearFirst.format(date);
            strCompletionTo = "'"+strCompletionTo+"'";
            LOGGER.debug("strCompletionTo--"+strCompletionTo);
        }
        }catch(ParseException parseException){
            LOGGER.error("Exception while parsing the date : " + parseException.getMessage());
        }
        String strImageStatus = null;
        
        if(StringUtils.isNotBlank(objAdvanceSearch.getImageStatus())){
            strImageStatus = getImageStatusCode(objAdvanceSearch.getImageStatus());
            strImageStatus =  "'"+strImageStatus+"'";
            LOGGER.debug("strImageStatus--"+strImageStatus);
        }
        
        String strContentStatus = null;
        
        if(StringUtils.isNotBlank(objAdvanceSearch.getContentStatus())){
            strContentStatus = getContentStatusCode(objAdvanceSearch.getContentStatus());
            strContentStatus =  "'"+strContentStatus+"'";
            LOGGER.debug("strContentStatus--"+strContentStatus);
        }
        
        String strRequestType = null;
        
        if(StringUtils.isNotBlank(objAdvanceSearch.getRequestType())){
            strRequestType =  "'"+getSourceStatusCode(objAdvanceSearch.getRequestType())+"'";
            LOGGER.debug("strRequestType--"+strRequestType);
        }
        String strGroupID = null;
        if(StringUtils.isNotBlank(objAdvanceSearch.getGroupingID())){
            strGroupID =  "'"+objAdvanceSearch.getGroupingID()+"'";
            LOGGER.debug("strGroupID--"+strGroupID);
        }
        String strGroupName = null;
        if(StringUtils.isNotBlank(objAdvanceSearch.getGroupingName())){
            strGroupName =  objAdvanceSearch.getGroupingName();
            LOGGER.debug("strGroupName--"+strGroupName);
        }
        
        String strCreatedToday = null;
        
        if(StringUtils.isNotBlank(objAdvanceSearch.getCreatedToday())){
            strCreatedToday =  "'"+objAdvanceSearch.getCreatedToday()+"'";
            LOGGER.debug("strCreatedToday--"+strCreatedToday);
        }
            
        LOGGER.info("Preparing Search Query for Group");
        strbAdvSearch.append("  SELECT  DISTINCT                                                                                        ");
        strbAdvSearch.append("  GRP.MDMID,                                                                                              ");
        strbAdvSearch.append("  GRP.DEF_DEPT_ID,                                                                                            ");
        strbAdvSearch.append("  ASCT.SUPPLIER_NAME SUPPLIER_NAME,                                                                                        ");
        strbAdvSearch.append("  GRP.GROUP_NAME,                                                                                         ");
        strbAdvSearch.append("  GRP.ENTRY_TYPE,                                                                                           ");
        strbAdvSearch.append("  GRP.GROUP_OVERALL_STATUS_CODE,                                                                          ");
        strbAdvSearch.append("  GRP.GROUP_IMAGE_STATUS_CODE IMAGE_STATE,                ");
        strbAdvSearch.append("  GRP.GROUP_CONTENT_STATUS_CODE CONTENT_STATE,                                        ");
        strbAdvSearch.append("  GRP.COMPLETION_DATE,                                                                                    ");
        strbAdvSearch.append("  GRP.PET_SOURCE,                                                                                         ");
        strbAdvSearch.append("  GRP.PET_DISPLAY_FLAG CHILD_GROUP, GRP.EXIST_IN_GROUP, GRP.MISSING_ASSET                                                 ");
        strbAdvSearch.append("  FROM ADSE_GROUP_CATALOG GRP                                                                             ");
        strbAdvSearch.append("    LEFT OUTER JOIN                                                                                       ");
        strbAdvSearch.append("    ADSE_GROUP_CHILD_MAPPING MAPPING                                                                      ");
        strbAdvSearch.append("    ON GRP.MDMID = MAPPING.MDMID                                                                          "); 
        strbAdvSearch.append("    LEFT OUTER JOIN ");
        strbAdvSearch.append("    ADSE_SUPPLIER_CATALOG ASCT ");
        strbAdvSearch.append("    ON GRP.DEF_PRIMARY_SUPPLIER_ID = ASCT.MDMID ");
        strbAdvSearch.append("  WHERE GRP.DELETED_FLAG = 'false' ");
        if(StringUtils.isNotBlank(objAdvanceSearch.getDateFrom()))
        strbAdvSearch.append("  AND ( COMPLETION_DATE BETWEEN TO_DATE("+strCompletionFrom+", 'YYYY-MM-DD') AND TO_DATE("+strCompletionTo+", 'YYYY-MM-DD') )                  ");
        if(StringUtils.isNotBlank(objAdvanceSearch.getGroupingID()))
        strbAdvSearch.append("  AND GRP.MDMID = "+strGroupID+"                                                                                             ");
        if(StringUtils.isNotBlank(objAdvanceSearch.getGroupingName()))
        strbAdvSearch.append("  AND UPPER(GROUP_NAME) Like ('%"+strGroupName.toUpperCase()+"%')                                                                                  ");
        if(lstStyleOrinQueryParam.size() > 0){
            strbAdvSearch.append("  AND (                                                                          ");
            for(String strOrinList : lstStyleOrinQueryParam){
                strbAdvSearch.append(" MAPPING.COMPONENT_STYLE_ID IN ("+strOrinList+") OR ");
            }
            strbAdvSearch = new StringBuilder(strbAdvSearch.substring(0, strbAdvSearch.length() - 4));
            strbAdvSearch.append(" ) ");
        }        
        if(StringUtils.isNotBlank(objAdvanceSearch.getContentStatus()))
        strbAdvSearch.append("  AND GRP.GROUP_CONTENT_STATUS_CODE IN ("+getStatusCode(strContentStatus)+")                     ");
        if(StringUtils.isNotBlank(objAdvanceSearch.getImageStatus()))
        strbAdvSearch.append("  AND GRP.GROUP_IMAGE_STATUS_CODE IN ("+getStatusCode(strImageStatus)+")                          ");
        if(StringUtils.isNotBlank(objAdvanceSearch.getRequestType()))
        strbAdvSearch.append("  AND GRP.PET_SOURCE IN ("+getStatusCode(strRequestType)+")                                    ");
        if(StringUtils.isNotBlank(objAdvanceSearch.getCreatedToday()))
        strbAdvSearch.append("  AND TO_DATE(SUBSTR(GRP.CREATED_DTM, 1, 9), 'YYYY-MM-DD') = to_date(SUBSTR(SYSDATE, 1, 9), 'YYYY-MM-DD') ");

        
        LOGGER.info("Query -->"+strbAdvSearch.toString());
        LOGGER.info("Exiting getGroupSearchQueryForAdvSearch");
        return strbAdvSearch.toString();
        
    }    

    
    /**
     * Method to get the Search Group Parent query.
     * 
     * @param strGroupId String
     * @return the query string
     * 
     *         Method added For PIM Phase 2 - Search Group Date: 05/19/2016
     *         Added By: Cognizant
     */
    public final String getGroupDetailsQueryParent(
            final String strGroupId) {

        LOGGER.info("Entering getGroupDetailsQueryParent() in Grouping XQueryConstant class.");

        final StringBuilder getGroupDetailsQueryParent = new StringBuilder();
        getGroupDetailsQueryParent
                .append(" SELECT DISTINCT AGC.MDMID GROUP_ID, AGC.GROUP_NAME GROUP_NAME, AGC.COMPLETION_DATE COMPLETION_DATE,"
                        + " CONTENT_STATE.THEVALUE CONTENT_STATE, AGC.GROUP_OVERALL_STATUS_CODE GROUP_OVERALL_STATUS_CODE, " 
                                + "GCM.COMPONENT_GROUPING_ID COMPONENT_GROUPING_ID, AGC.PET_SOURCE PET_SOURCE, " 
                        + "IMAGE_STATE.THEVALUE IMAGE_STATE, AGC.PET_DISPLAY_FLAG CHILD_GROUP,  ");
        getGroupDetailsQueryParent.append("  AGC.DEF_DEPT_ID, AGC.ENTRY_TYPE, ");
        getGroupDetailsQueryParent.append("  ASCT.SUPPLIER_NAME, AGC.EXIST_IN_GROUP, AGC.MISSING_ASSET ");
        getGroupDetailsQueryParent
                .append(" FROM ADSE_GROUP_CATALOG AGC LEFT OUTER JOIN ADSE_SUPPLIER_CATALOG ASCT ");
        getGroupDetailsQueryParent
        .append(" ON AGC.DEF_PRIMARY_SUPPLIER_ID = ASCT.MDMID INNER JOIN ");
        getGroupDetailsQueryParent
                .append(" ADSE_REFERENCE_DATA CONTENT_STATE ON GROUP_CONTENT_STATUS_CODE = CONTENT_STATE.MDMID ");
        getGroupDetailsQueryParent
                .append(" AND CONTENT_STATE.ENTRY_TYPE = 'ContentState_Lookup' ");
        getGroupDetailsQueryParent.append(" INNER JOIN ");
        getGroupDetailsQueryParent
                .append(" ADSE_REFERENCE_DATA IMAGE_STATE ON GROUP_IMAGE_STATUS_CODE = IMAGE_STATE.MDMID ");
        getGroupDetailsQueryParent
                .append(" AND IMAGE_STATE.ENTRY_TYPE = 'ImageState_Lookup' ");
        getGroupDetailsQueryParent
                .append(" , ADSE_GROUP_CHILD_MAPPING GCM                                                                                        ");
        getGroupDetailsQueryParent
                .append(" WHERE AGC.DELETED_FLAG = 'false'                   ");
        getGroupDetailsQueryParent
                .append(" AND AGC.MDMID = GCM.MDMID                                                                                                              ");
        
        getGroupDetailsQueryParent
                .append(" AND GCM.COMPONENT_GROUPING_ID = '"
                         + strGroupId + "'");

        getGroupDetailsQueryParent
                .append(" AND PEP_COMPONENT_TYPE = 'Group'                       ");
        

        LOGGER.debug("SEARCH GROUP PARENT QUERY -- \n"
                + getGroupDetailsQueryParent.toString());
        LOGGER.info("Exiting getGroupDetailsQueryParent() in Grouping XQueryConstant class.");
        return getGroupDetailsQueryParent.toString();
    }
    
    /**
     * Method to get the Group child query.
     * 
     * @param strGroupId String
     * @return the query string
     * 
     *         Method added For PIM Phase 2 - Search Group Date: 06/06/2016
     *         Added By: Cognizant
     */
    public final String getChildForGroupQuery(
            final String strGroupId) {

        LOGGER.info("Entering getChildForGroupQuery() in Grouping XQueryConstant class.");

        final StringBuilder getChildForGroup = new StringBuilder();
        getChildForGroup.append("  SELECT  PARENT_MDMID                   ,");
        getChildForGroup.append("         MDMID                          ,");
        getChildForGroup.append("         PRODUCTNAME                    ,");
        getChildForGroup.append("         COMPLETION_DATE                ,");
        getChildForGroup.append("         PET_STATE                      ,");
        getChildForGroup.append("         CONTENT_STATE                  ,");
        getChildForGroup.append("         IMAGE_STATE                    ,");
        getChildForGroup.append("         CHILD                          ,");
        getChildForGroup.append("         DEPT                           ,");
        getChildForGroup.append("         ENTRY_TYPE                     ,");
        getChildForGroup.append("         COMPONENT_TYPE                 ,");
        getChildForGroup.append("         PRIMARY_SUPPLIER_ID SUPPLIER_ID,");
        getChildForGroup.append("         SUPPLIER_NAME                  ,");
        getChildForGroup.append("         VENDOR_STYLE                   ,");
        getChildForGroup.append("         PET_SOURCE                     ,");
        getChildForGroup.append("         OmnichannelIndicator           ,");
        getChildForGroup.append("         CREATED_DTM                    ,");
        getChildForGroup.append("         EXIST_IN_GROUP                 ,");
        getChildForGroup.append("         CONVERSION_FLAG , PET_STYLE_STATE, MISSING_ASSET ");
        getChildForGroup.append(" FROM ");       
        getChildForGroup.append("                 (SELECT  NULL PARENT_MDMID                                        ,");
        getChildForGroup.append("                         AGC.MDMID                                                ,");
        getChildForGroup.append("                         AGC.GROUP_NAME PRODUCTNAME                               ,");
        getChildForGroup.append("                         TO_CHAR(AGC.COMPLETION_DATE,'YYYY-MM-DD') COMPLETION_DATE,");
        getChildForGroup.append("                         AGC.GROUP_OVERALL_STATUS_CODE PET_STATE                  ,");
        getChildForGroup.append("                         AGC.GROUP_CONTENT_STATUS_CODE CONTENT_STATE              ,");
        getChildForGroup.append("                         AGC.GROUP_IMAGE_STATUS_CODE IMAGE_STATE                  ,");
        getChildForGroup.append("                         AGC.PET_DISPLAY_FLAG CHILD                               ,");
        getChildForGroup.append("                         AGC.DEF_DEPT_ID DEPT                                     ,");
        getChildForGroup.append("                         AGC.ENTRY_TYPE                                           ,");
        getChildForGroup.append("                         AGCM.PEP_COMPONENT_TYPE COMPONENT_TYPE                   ,");
        getChildForGroup.append("                          AGC.DEF_PRIMARY_SUPPLIER_ID PRIMARY_SUPPLIER_ID,");
        getChildForGroup.append("                         ASCT.supplier_name SUPPLIER_NAME                           ,");
        getChildForGroup.append("                         AGC.PET_SOURCE PET_SOURCE                                ,");
        getChildForGroup.append("                         AGC.DEF_PRIMARYSUPPLIERVPN VENDOR_STYLE                  ,");
        getChildForGroup.append("                         ''    OmnichannelIndicator                    ,");
        getChildForGroup.append("                         AGC.CREATED_DTM                                          ,");
        getChildForGroup.append("                         AGC.EXIST_IN_GROUP                                       ,");
        getChildForGroup.append("                         NULL CONVERSION_FLAG, NULL PET_STYLE_STATE, AGC.MISSING_ASSET ");
        getChildForGroup.append("                 FROM    ADSE_GROUP_CATALOG AGC ");
        getChildForGroup.append("                         LEFT OUTER JOIN ADSE_SUPPLIER_CATALOG ASCT ");
        getChildForGroup.append("                         ON      ASCT.MDMID = AGC.DEF_PRIMARY_SUPPLIER_ID                                                                                                                                                                                                                                                                                                                                                                    ,");
        getChildForGroup.append("                                ADSE_GROUP_CHILD_MAPPING AGCM ");
        getChildForGroup.append("                 WHERE   AGC.MDMID                  = AGCM.COMPONENT_GROUPING_ID ");
        getChildForGroup.append("                         AND AGCM.PEP_COMPONENT_TYPE='Group' ");
        getChildForGroup.append("                         AND AGCM.MDMID             =:groupOrin ");
        getChildForGroup.append("                 UNION ");
        getChildForGroup.append("                 SELECT  AIC.PARENT_MDMID,");
        getChildForGroup.append("                          AIC.MDMID ");
        getChildForGroup.append("                                              ,");
        getChildForGroup.append("                         AIC.PRODUCT_NAME PRODUCTNAME,");
        getChildForGroup.append("                         TO_CHAR(APC.PET_EARLIEST_COMP_DATE, 'YYYY-MM-DD') ");
        getChildForGroup.append("                         AS completion_date                ,");
        getChildForGroup.append("                         APC.PET_STATE PET_STATE               ,");
        getChildForGroup.append("                         APC.CONTENT_STATUS CONTENT_STATE      ,");
        getChildForGroup.append("                         APC.IMAGE_STATUS IMAGE_STATE          ,");
        getChildForGroup.append("                         NULL CHILD                            ,");
        getChildForGroup.append("                         AIC.DEPT_ID DEPT                      ,");
        getChildForGroup.append("                         AIC.ENTRY_TYPE                        ,");
        getChildForGroup.append("                         AGCM.PEP_COMPONENT_TYPE COMPONENT_TYPE,");
        getChildForGroup.append("                           AIC.PRIMARY_SUPPLIER_ID,");
        getChildForGroup.append("                         ASCT.supplier_name SUPPLIER_NAME    ,");
        getChildForGroup.append("                          XMLCAST( (XMLQUERY('/pim_entry/entry/Pet_Ctg_Spec/SourceSystem' PASSING apc.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) PET_SOURCE ");
        getChildForGroup.append("                                    ,");
        getChildForGroup.append("                         AIC.PRIMARYSUPPLIERVPN VENDOR_STYLE   ,");
        getChildForGroup.append("                         ''  OmnichannelIndicator   ,");
        getChildForGroup.append("                         APC.CREATED_DTM                       ,");
        getChildForGroup.append("                         APC.EXIST_IN_GROUP                    ,");
        getChildForGroup.append("                          XMLCAST( (XMLQUERY('/pim_entry/entry/Pet_Ctg_Spec/System/Pet_Information/Conversion_Flag' PASSING apc.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) CONVERSION_FLAG, APC.PET_STYLE_STATE, APC.MISSING_ASSET ");
        getChildForGroup.append("                 FROM    ADSE_GROUP_CHILD_MAPPING AGCM,");
        getChildForGroup.append("                         ADSE_ITEM_CATALOG AIC ");
        getChildForGroup.append("                         INNER JOIN ADSE_PET_CATALOG APC ");
        getChildForGroup.append("                         ON      AIC.MDMID=APC.MDMID ");
        getChildForGroup.append("                         LEFT OUTER JOIN ADSE_SUPPLIER_CATALOG ASCT ");
        getChildForGroup.append("                         ON      ASCT.MDMID = AIC.PRIMARY_SUPPLIER_ID ");        
        getChildForGroup.append("                         WHERE   AIC.MDMID = AGCM.COMPONENT_STYLE_ID ");
        getChildForGroup.append("                         AND AGCM.PEP_COMPONENT_TYPE!    ='Group' ");
        getChildForGroup.append("                         AND AIC.ENTRY_TYPE  ='Style' ");
        getChildForGroup.append("                         AND ");
        getChildForGroup.append("                         ( ");
        getChildForGroup.append("                                 CASE ");
        getChildForGroup.append("                                         WHEN AIC.PARENT_MDMID IS NOT NULL ");
        getChildForGroup.append("                                                 AND AIC.MDMID !=AGCM.COMPONENT_STYLECOLOR_ID ");
        getChildForGroup.append("                                         THEN 0 ");
        getChildForGroup.append("                                         ELSE 1 ");
        getChildForGroup.append("                                 END ");
        getChildForGroup.append("                         ) ");
        getChildForGroup.append("                                        =1 ");
        getChildForGroup.append("                         AND AGCM.MDMID =:groupOrin ");
        if(!StringUtils.isBlank(strGroupId))
        {
        	getChildForGroup.append(" AND EXISTS (SELECT 1 FROM ADSE_GROUP_CHILD_MAPPING WHERE MDMID = :parentGroupId AND COMPONENT_GROUPING_ID=:groupOrin AND (COMPONENT_STYLE_ID=AIC.MDMID OR COMPONENT_STYLE_ID is NULL)) ");
        }
        getChildForGroup.append("                         union ");
        getChildForGroup.append("                             SELECT  AIC.PARENT_MDMID,");
        getChildForGroup.append("                         SUBSTR(AIC.MDMID,1,9) ");
        getChildForGroup.append("                                         || ");
        getChildForGroup.append("                                         ' ' ");
        getChildForGroup.append("                                         || ");
        getChildForGroup.append("                                         SUBSTR(AIC.MDMID,10,12) ");
        getChildForGroup.append("                                         || ");
        getChildForGroup.append("                                         ' ' ");
        getChildForGroup.append("                                         || ");
        getChildForGroup.append("                                         AIC.COLOR_Name ");
        getChildForGroup.append("                                               ,");
        getChildForGroup.append("                         AIC.PRODUCT_NAME PRODUCTNAME,");
        getChildForGroup.append("                          XMLCAST( (XMLQUERY('pim_entry/entry/Pet_Ctg_Spec/Completion_Date' PASSING apc.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) completion_date                ,");
        getChildForGroup.append("                         APC.PET_STATE PET_STATE               ,");
        getChildForGroup.append("                         APC.CONTENT_STATUS CONTENT_STATE      ,");
        getChildForGroup.append("                         APC.IMAGE_STATUS IMAGE_STATE          ,");
        getChildForGroup.append("                         NULL CHILD                            ,");
        getChildForGroup.append("                         AIC.DEPT_ID DEPT                      ,");
        getChildForGroup.append("                         AIC.ENTRY_TYPE                        ,");
        getChildForGroup.append("                         AGCM.PEP_COMPONENT_TYPE COMPONENT_TYPE,");
        getChildForGroup.append("                         AIC.PRIMARY_SUPPLIER_ID,");
        getChildForGroup.append("                         ASCT.supplier_name SUPPLIER_NAME    ,");
        getChildForGroup.append("                          XMLCAST( (XMLQUERY('/pim_entry/entry/Pet_Ctg_Spec/SourceSystem' PASSING apc.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) PET_SOURCE ");
        getChildForGroup.append("                                  ,");
        getChildForGroup.append("                         AIC.PRIMARYSUPPLIERVPN VENDOR_STYLE   ,");
        getChildForGroup.append("                         '' OmnichannelIndicator   ,");
        getChildForGroup.append("                         APC.CREATED_DTM                       ,");
        getChildForGroup.append("                         APC.EXIST_IN_GROUP                    ,");
        getChildForGroup.append("                         XMLCAST( (XMLQUERY('/pim_entry/entry/Pet_Ctg_Spec/System/Pet_Information/Conversion_Flag' PASSING apc.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) CONVERSION_FLAG, APC.PET_STYLE_STATE, APC.MISSING_ASSET ");        
        getChildForGroup.append("                 FROM    ADSE_GROUP_CHILD_MAPPING AGCM,");
        getChildForGroup.append("                         ADSE_ITEM_CATALOG AIC ");
        getChildForGroup.append("                         INNER JOIN ADSE_PET_CATALOG APC ");
        getChildForGroup.append("                         ON      AIC.MDMID=APC.MDMID ");
        getChildForGroup.append("                         LEFT OUTER JOIN ADSE_SUPPLIER_CATALOG ASCT ");
        getChildForGroup.append("                         ON      ASCT.MDMID = AIC.PRIMARY_SUPPLIER_ID ");
        getChildForGroup.append("                 WHERE  AIC.PARENT_MDMID = AGCM.COMPONENT_STYLE_ID ");
        getChildForGroup.append("                         AND AGCM.PEP_COMPONENT_TYPE!    ='Group' ");
        getChildForGroup.append("                         AND AIC.ENTRY_TYPE             IN ('StyleColor') ");
        getChildForGroup.append("                         AND ");
        getChildForGroup.append("                         ( ");
        getChildForGroup.append("                                 CASE ");
        getChildForGroup.append("                                         WHEN AIC.PARENT_MDMID IS NOT NULL ");
        getChildForGroup.append("                                                 AND AIC.MDMID !=AGCM.COMPONENT_STYLECOLOR_ID ");
        getChildForGroup.append("                                         THEN 0 ");
        getChildForGroup.append("                                         ELSE 1 ");
        getChildForGroup.append("                                 END ");
        getChildForGroup.append("                         ) ");
        getChildForGroup.append("                                        =1 ");
        getChildForGroup.append("                         AND AGCM.MDMID =:groupOrin ");
        if(!StringUtils.isBlank(strGroupId))
        {
        	getChildForGroup.append(" AND EXISTS (SELECT 1 FROM ADSE_GROUP_CHILD_MAPPING WHERE MDMID = :parentGroupId AND COMPONENT_GROUPING_ID=:groupOrin AND ( COMPONENT_STYLECOLOR_ID=AIC.MDMID OR COMPONENT_STYLECOLOR_ID is NULL)) ");
        }
        getChildForGroup.append("         ) ");
        getChildForGroup.append(" ORDER BY COMPONENT_TYPE DESC,");
        getChildForGroup.append("         MDMID               ,");
        getChildForGroup.append("         COMPLETION_DATE ");     
        

        LOGGER.debug("SEARCH GROUP CHILD SEARCH QUERY -- \n"
                + getChildForGroup);
        LOGGER.info("Exiting getChildForGroupQuery() in Grouping XQueryConstant class.");
        return getChildForGroup.toString();
    }

    /**
     * Gets the status mapping.
     *
     * @param status String
     * @return String
     */
    private String getStatusCode(String status) {
        LOGGER.info("This is from getStatusCode...status-->"+status);
        String finalStatus = "";
        try{
            status = status.substring(1, status.length() - 1);
            String[] statusArray = status.split(",");
            for(int i = 0; i<statusArray.length; i++){
                finalStatus = finalStatus + "'"+statusArray[i] + "',";
            }
            finalStatus = finalStatus.substring(0, finalStatus.length()-1);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        LOGGER.info("This is from getStatusCode...finalStatus-->"+finalStatus);
     return finalStatus;
    }
    
    /**
     * Method to get the Worklist Group query.
     * 
     * @param deptIds String
     * @return the query string
     * 
     *         Method added For PIM Phase 2 - Worklist Group Date: 06/08/2016
     *         Added By: Cognizant
     */
    public final String getWorkListGroupQuery(
            final String deptIds, String sortColumn, String sortingOrder) {

        LOGGER.info("Entering getWorkListGroupQuery() in Grouping XQueryConstant class.");
        String dept = "";
        if(deptIds != null)
        {
            dept = "'" + deptIds + "'";
            dept = getStatusCode(dept);
        }
        
        final StringBuilder getGroupDetailsQueryWorkList = new StringBuilder();
        
        getGroupDetailsQueryWorkList.append(" SELECT DISTINCT GRP.MDMID, ");
        getGroupDetailsQueryWorkList.append("   GRP.DEF_DEPT_ID,                                       ");
        getGroupDetailsQueryWorkList.append("   ASCT.SUPPLIER_NAME SUPPLIER_NAME,                      ");
        getGroupDetailsQueryWorkList.append("   GRP.GROUP_NAME,                                        ");
        getGroupDetailsQueryWorkList.append("   GRP.ENTRY_TYPE,                                        ");
        getGroupDetailsQueryWorkList.append("   CASE PET_STATE.THEVALUE            ");
        getGroupDetailsQueryWorkList.append("   WHEN 'Waiting_To_Be_Closed' THEN 'Completed'            ");
        getGroupDetailsQueryWorkList.append("   WHEN 'Reactivated' THEN 'Initiated'                     "); 
        getGroupDetailsQueryWorkList.append("   WHEN 'Closed' THEN 'Completed'                          ");
        getGroupDetailsQueryWorkList.append("   WHEN 'Publish_To_Web' THEN 'Completed'                  ");
        getGroupDetailsQueryWorkList.append("   ELSE PET_STATE.THEVALUE END GROUP_OVERALL_STATUS_CODE,  ");
        getGroupDetailsQueryWorkList.append("   IMAGE_STATE.THEVALUE IMAGE_STATE,               ");
        getGroupDetailsQueryWorkList.append("   CONTENT_STATE.THEVALUE CONTENT_STATE,           ");
        getGroupDetailsQueryWorkList.append("   GRP.COMPLETION_DATE,                                   ");
        getGroupDetailsQueryWorkList.append("   GRP.PET_SOURCE,                                        ");
        getGroupDetailsQueryWorkList.append("   GRP.PET_DISPLAY_FLAG CHILD_GROUP,                      ");
        getGroupDetailsQueryWorkList.append("   GRP.EXIST_IN_GROUP,                                     ");
        getGroupDetailsQueryWorkList.append("   GRP.MISSING_ASSET                                     ");
        getGroupDetailsQueryWorkList.append(" FROM ADSE_GROUP_CATALOG GRP                              ");
        getGroupDetailsQueryWorkList.append(" INNER JOIN ");
        getGroupDetailsQueryWorkList
            .append(" ADSE_REFERENCE_DATA CONTENT_STATE ON GRP.GROUP_CONTENT_STATUS_CODE = CONTENT_STATE.MDMID ");
        getGroupDetailsQueryWorkList
            .append(" AND CONTENT_STATE.ENTRY_TYPE = 'ContentState_Lookup' ");
        getGroupDetailsQueryWorkList.append(" INNER JOIN ");
        getGroupDetailsQueryWorkList
            .append(" ADSE_REFERENCE_DATA IMAGE_STATE ON GRP.GROUP_IMAGE_STATUS_CODE = IMAGE_STATE.MDMID ");
        getGroupDetailsQueryWorkList
            .append(" AND IMAGE_STATE.ENTRY_TYPE = 'ImageState_Lookup' ");
        getGroupDetailsQueryWorkList.append(" INNER JOIN ");
        getGroupDetailsQueryWorkList
            .append(" ADSE_REFERENCE_DATA PET_STATE ON GRP.GROUP_OVERALL_STATUS_CODE = PET_STATE.MDMID ");
        getGroupDetailsQueryWorkList
            .append(" AND PET_STATE.ENTRY_TYPE = 'PetState_Lookup' ");
        
        getGroupDetailsQueryWorkList.append(" INNER JOIN ADSE_GROUP_CHILD_MAPPING MAPPING              ");
        getGroupDetailsQueryWorkList.append(" ON GRP.MDMID = MAPPING.MDMID                             ");

        getGroupDetailsQueryWorkList.append(" LEFT OUTER JOIN ADSE_SUPPLIER_CATALOG ASCT               ");
        getGroupDetailsQueryWorkList.append(" ON GRP.DEF_PRIMARY_SUPPLIER_ID = ASCT.MDMID              ");
        getGroupDetailsQueryWorkList.append(" WHERE GRP.DELETED_FLAG         = 'false'                 ");
        getGroupDetailsQueryWorkList.append(" AND ((GRP.GROUP_OVERALL_STATUS_CODE = '01')              ");
        // Changed as per Defect 3382 - Deactivated Group should not be displayed
        getGroupDetailsQueryWorkList.append(" OR (GRP.GROUP_OVERALL_STATUS_CODE NOT IN ('01','05')     ");
        getGroupDetailsQueryWorkList.append(" AND GRP.PET_DISPLAY_FLAG = 'O'))                         ");
        
        if(dept != null && dept.trim().length() > 0)
        {
            getGroupDetailsQueryWorkList.append(" AND ( MAPPING.COMPONENT_STYLE_ID IN (SELECT AIC.MDMID   ");
            getGroupDetailsQueryWorkList.append(" FROM ADSE_ITEM_CATALOG AIC WHERE AIC.PETEXISTS='Y'                ");
            getGroupDetailsQueryWorkList.append(" AND AIC.DEPT_ID IN (                                     ");
            getGroupDetailsQueryWorkList.append(dept);
            getGroupDetailsQueryWorkList.append(" )) OR  MAPPING.COMPONENT_GROUPING_ID IN (SELECT MDMID ");
            getGroupDetailsQueryWorkList.append(" FROM ADSE_GROUP_CATALOG WHERE DEF_DEPT_ID IN ( ");
            getGroupDetailsQueryWorkList.append(dept);
            getGroupDetailsQueryWorkList.append(" ))) ");
        }
        
        if (sortColumn == null
                || sortColumn.trim().equals("")) {
            getGroupDetailsQueryWorkList
                    .append(" ORDER BY PET_SOURCE, COMPLETION_DATE DESC, GRP.MDMID ");
        } else if (sortColumn != null
                && sortColumn.equals("orinGroup")) {
            getGroupDetailsQueryWorkList.append(" ORDER BY GRP.MDMID "
                    + sortingOrder);
        } else if (sortColumn != null
                && sortColumn.equals("dept")) {
            getGroupDetailsQueryWorkList.append(" ORDER BY GRP.DEF_DEPT_ID "
                    + sortingOrder);
        } else if (sortColumn != null
                && sortColumn.equals("vendorName")) {
            getGroupDetailsQueryWorkList.append(" ORDER BY ASCT.SUPPLIER_NAME "
                    + sortingOrder);
        } else if (sortColumn != null
                && sortColumn.equals("vendorStyle")) {
            getGroupDetailsQueryWorkList
                    .append(" ORDER BY GRP.ENTRY_TYPE " + sortingOrder);
        } else if (sortColumn != null
                && sortColumn.equals("productName")) {
            getGroupDetailsQueryWorkList.append(" ORDER BY GRP.GROUP_NAME "
                    + sortingOrder);
        } else if (sortColumn != null
                && sortColumn.equals("contentStatus")) {
            getGroupDetailsQueryWorkList.append(" ORDER BY CONTENT_STATE.THEVALUE "
                    + sortingOrder);
        } else if (sortColumn != null
                && sortColumn.equals("imageStatus")) {
            getGroupDetailsQueryWorkList.append(" ORDER BY IMAGE_STATE.THEVALUE "
                    + sortingOrder);
        } else if (sortColumn != null
                && sortColumn.equals("petStatus")) {
            getGroupDetailsQueryWorkList.append(" ORDER BY GROUP_OVERALL_STATUS_CODE "
                        + sortingOrder);
        } else if (sortColumn != null
                && sortColumn.equals("completionDate")) {
            getGroupDetailsQueryWorkList
                    .append(" ORDER BY GRP.COMPLETION_DATE " + sortingOrder);
        } else if (sortColumn != null
                && sortColumn.equals("petSourceType")) {
            getGroupDetailsQueryWorkList
                    .append(" ORDER BY GRP.PET_SOURCE " + sortingOrder);
        }

        LOGGER.debug("SEARCH GROUP WORKLIST QUERY -- \n"
                + getGroupDetailsQueryWorkList.toString());
        LOGGER.info("Exiting getWorkListGroupQuery() in Grouping XQueryConstant class.");
        return getGroupDetailsQueryWorkList.toString();
    }
    
    /**
     * Method to get the Worklist Group count query.
     * 
     * @param deptIds String
     * @return the query string
     * 
     *         Method added For PIM Phase 2 - Worklist Group Date: 06/08/2016
     *         Added By: Cognizant
     */
    public final String getWorkListGroupCountQuery(
            final String deptIds) {

        LOGGER.info("Entering getWorkListGroupCountQuery() in Grouping XQueryConstant class.");

        String dept = "";
        if(deptIds != null)
        {
            dept = "'" + deptIds + "'";
            dept = getStatusCode(dept);
        }
        final StringBuilder getGroupDetailsCountQueryWorkList = new StringBuilder();
        
        getGroupDetailsCountQueryWorkList.append(" SELECT COUNT(DISTINCT GRP.MDMID) TOTAL_COUNT ");
        getGroupDetailsCountQueryWorkList.append(" FROM ADSE_GROUP_CATALOG GRP                            ");
        getGroupDetailsCountQueryWorkList.append(" INNER JOIN ADSE_GROUP_CHILD_MAPPING MAPPING            ");
        getGroupDetailsCountQueryWorkList.append(" ON GRP.MDMID = MAPPING.MDMID                           ");

        
        getGroupDetailsCountQueryWorkList.append(" LEFT OUTER JOIN ADSE_SUPPLIER_CATALOG ASCT             ");
        getGroupDetailsCountQueryWorkList.append(" ON GRP.DEF_PRIMARY_SUPPLIER_ID = ASCT.MDMID            ");
        getGroupDetailsCountQueryWorkList.append(" WHERE GRP.DELETED_FLAG         = 'false'               ");
        getGroupDetailsCountQueryWorkList.append(" AND ((GRP.GROUP_OVERALL_STATUS_CODE = '01')            ");
        getGroupDetailsCountQueryWorkList.append(" OR (GRP.GROUP_OVERALL_STATUS_CODE NOT IN ('01','05')      ");
        getGroupDetailsCountQueryWorkList.append(" AND GRP.PET_DISPLAY_FLAG = 'O'))                       ");
        
        if(dept != null && dept.trim().length() > 0)
        {
            getGroupDetailsCountQueryWorkList.append(" AND ( MAPPING.COMPONENT_STYLE_ID IN (SELECT AIC.MDMID   ");
            getGroupDetailsCountQueryWorkList.append(" FROM ADSE_ITEM_CATALOG AIC WHERE AIC.PETEXISTS='Y'                ");
            getGroupDetailsCountQueryWorkList.append(" AND AIC.DEPT_ID IN (                                     ");
            getGroupDetailsCountQueryWorkList.append(dept);
            getGroupDetailsCountQueryWorkList.append(" )) OR  MAPPING.COMPONENT_GROUPING_ID IN (SELECT MDMID ");
            getGroupDetailsCountQueryWorkList.append(" FROM ADSE_GROUP_CATALOG WHERE DEF_DEPT_ID IN ( ");
            getGroupDetailsCountQueryWorkList.append(dept);
            getGroupDetailsCountQueryWorkList.append(" ))) ");
        }
        
        getGroupDetailsCountQueryWorkList.append(" ORDER BY PET_SOURCE, COMPLETION_DATE DESC, GRP.MDMID   ");

        LOGGER.debug("SEARCH GROUP WORKLIST QUERY -- \n"
                + getGroupDetailsCountQueryWorkList.toString());
        LOGGER.info("Exiting getWorkListGroupCountQuery() in Grouping XQueryConstant class.");
        return getGroupDetailsCountQueryWorkList.toString();
    }
    
   /**
     * Method to get the Group child Worklist query.
     * 
     * @param strGroupId String
     * @return the query string
     * 
     *         Method added For PIM Phase 2 - Worklist Group Date: 06/09/2016
     *         Added By: Cognizant
     */
    public final String getChildForGroupWorklistQuery(final String strGroupId) {

        LOGGER.info("Entering getChildForGroupWorklistQuery() in Grouping XQueryConstant class.");

        final StringBuilder getChildForGroup = new StringBuilder();
        	 getChildForGroup.append("        SELECT  PARENT_MDMID                   , ");;
        	 getChildForGroup.append("        MDMID                          , ");
        	 getChildForGroup.append("        PRODUCTNAME                    , ");
        	 getChildForGroup.append("        COMPLETION_DATE                , ");
        	 getChildForGroup.append("        PET_STATE                      , ");
        	 getChildForGroup.append("        CONTENT_STATE                  , ");
        	 getChildForGroup.append("        IMAGE_STATE                    , ");
        	 getChildForGroup.append("        CHILD                          , ");
        	 getChildForGroup.append("        DEPT                           , ");
        	 getChildForGroup.append("        ENTRY_TYPE                     , ");
        	 getChildForGroup.append("        COMPONENT_TYPE                 , ");
        	 getChildForGroup.append("        PRIMARY_SUPPLIER_ID SUPPLIER_ID, ");
        	 getChildForGroup.append("        SUPPLIER_NAME                  , ");
        	 getChildForGroup.append("        VENDOR_STYLE                   , ");
        	 getChildForGroup.append("        PET_SOURCE                     , ");
        	 getChildForGroup.append("        OmnichannelIndicator           , ");
        	 getChildForGroup.append("        CREATED_DTM                    , ");
        	 getChildForGroup.append("        EXIST_IN_GROUP                 , ");
        	 getChildForGroup.append("        CONVERSION_FLAG                , ");
        	 getChildForGroup.append("        MISSING_ASSET ");
        	 getChildForGroup.append("        FROM        (    ");
        	 getChildForGroup.append("                SELECT  NULL PARENT_MDMID                                        , ");
        	 getChildForGroup.append("                        AGC.MDMID                                                , ");
        	 getChildForGroup.append("                        AGC.GROUP_NAME PRODUCTNAME                               , ");
        	 getChildForGroup.append("                        TO_CHAR(AGC.COMPLETION_DATE,'YYYY-MM-DD') COMPLETION_DATE, ");
        	 getChildForGroup.append("                        AGC.GROUP_OVERALL_STATUS_CODE PET_STATE                  , ");
        	 getChildForGroup.append("                        AGC.GROUP_CONTENT_STATUS_CODE CONTENT_STATE              , ");
        	 getChildForGroup.append("                        AGC.GROUP_IMAGE_STATUS_CODE IMAGE_STATE                  , ");
        	 getChildForGroup.append("                        AGC.PET_DISPLAY_FLAG CHILD                               , ");
        	 getChildForGroup.append("                        AGC.DEF_DEPT_ID DEPT                                     , ");
        	 getChildForGroup.append("                        AGC.ENTRY_TYPE                                           , ");
        	 getChildForGroup.append("                        AGCM.PEP_COMPONENT_TYPE COMPONENT_TYPE                   , ");
        	 getChildForGroup.append("                         AGC.DEF_PRIMARY_SUPPLIER_ID PRIMARY_SUPPLIER_ID, ");
        	 getChildForGroup.append("                        ASCT.supplier_name SUPPLIER_NAME                           , ");
        	 getChildForGroup.append("                        AGC.PET_SOURCE PET_SOURCE                                , ");
        	 getChildForGroup.append("                        AGC.DEF_PRIMARYSUPPLIERVPN VENDOR_STYLE                  , ");
        	 getChildForGroup.append("                        ''    OmnichannelIndicator                    , ");
        	 getChildForGroup.append("                        AGC.CREATED_DTM                                          , ");
        	 getChildForGroup.append("                        AGC.EXIST_IN_GROUP                                       , ");
        	 getChildForGroup.append("                        NULL CONVERSION_FLAG                                     , ");
             getChildForGroup.append("                        AGC.MISSING_ASSET ");
        	 getChildForGroup.append("                FROM    ADSE_GROUP_CATALOG AGC ");
        	 getChildForGroup.append("                        LEFT OUTER JOIN ADSE_SUPPLIER_CATALOG ASCT ");
        	 getChildForGroup.append("                        ON      ASCT.MDMID = AGC.DEF_PRIMARY_SUPPLIER_ID       , "); 
        	 getChildForGroup.append("                               ADSE_GROUP_CHILD_MAPPING AGCM ");
        	 getChildForGroup.append("                WHERE   AGC.MDMID                  = AGCM.COMPONENT_GROUPING_ID ");
        	 getChildForGroup.append("                        AND AGCM.PEP_COMPONENT_TYPE='Group' ");
        	 getChildForGroup.append("                     AND ((AGC.GROUP_OVERALL_STATUS_CODE = '01') ");
        	 getChildForGroup.append("                     OR (AGC.GROUP_OVERALL_STATUS_CODE  <> '01' ");
        	 getChildForGroup.append("                    AND AGC.PET_DISPLAY_FLAG            = 'O')) ");
        	 getChildForGroup.append("                    AND AGCM.MDMID                      = :orinNum ");
        	 getChildForGroup.append("                UNION ");
        	 getChildForGroup.append("                SELECT  AIC.PARENT_MDMID, ");
        	 getChildForGroup.append("                        AIC.MDMID, ");
        	 getChildForGroup.append("                        AIC.PRODUCT_NAME PRODUCTNAME, ");
        	 getChildForGroup.append("                        TO_CHAR(APC.PET_EARLIEST_COMP_DATE, 'YYYY-MM-DD') ");
        	 getChildForGroup.append("                        AS completion_date                , ");
        	 getChildForGroup.append("                        APC.PET_STATE PET_STATE               , ");
        	 getChildForGroup.append("                        APC.CONTENT_STATUS CONTENT_STATE      , ");
        	 getChildForGroup.append("                        APC.IMAGE_STATUS IMAGE_STATE          , ");
        	 getChildForGroup.append("                        NULL CHILD                            , ");
        	 getChildForGroup.append("                        AIC.DEPT_ID DEPT                      , ");
        	 getChildForGroup.append("                        AIC.ENTRY_TYPE                        , ");
        	 getChildForGroup.append("                        AGCM.PEP_COMPONENT_TYPE COMPONENT_TYPE, ");
        	 getChildForGroup.append("                        AIC.PRIMARY_SUPPLIER_ID, ");
        	 getChildForGroup.append("                        ASCT.supplier_name SUPPLIER_NAME    , ");
        	 getChildForGroup.append("                        XMLCAST( (XMLQUERY('/pim_entry/entry/Pet_Ctg_Spec/SourceSystem' PASSING apc.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) PET_SOURCE , ");                         
        	 getChildForGroup.append("                        AIC.PRIMARYSUPPLIERVPN VENDOR_STYLE   , ");
        	 getChildForGroup.append("                        ''  OmnichannelIndicator   , ");
        	 getChildForGroup.append("                        APC.CREATED_DTM                       , ");
        	 getChildForGroup.append("                        APC.EXIST_IN_GROUP                    , ");
        	 getChildForGroup.append("                        XMLCAST( (XMLQUERY('/pim_entry/entry/Pet_Ctg_Spec/System/Pet_Information/Conversion_Flag' PASSING apc.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) CONVERSION_FLAG, APC.MISSING_ASSET ");
        	 getChildForGroup.append("                        FROM    ADSE_GROUP_CHILD_MAPPING AGCM, ");
        	 getChildForGroup.append("                        ADSE_ITEM_CATALOG AIC ");
        	 getChildForGroup.append("                        INNER JOIN ADSE_PET_CATALOG APC ");
        	 getChildForGroup.append("                        ON      AIC.MDMID=APC.MDMID ");
        	 getChildForGroup.append("                        LEFT OUTER JOIN ADSE_SUPPLIER_CATALOG ASCT ");
        	 getChildForGroup.append("                        ON      ASCT.MDMID = AIC.PRIMARY_SUPPLIER_ID ");        	 
        	 getChildForGroup.append("                        WHERE   AIC.MDMID = AGCM.COMPONENT_STYLE_ID ");
        	 getChildForGroup.append("                        AND AGCM.PEP_COMPONENT_TYPE!    ='Group' ");
        	 getChildForGroup.append("                        AND AIC.ENTRY_TYPE  ='Style' ");
        	 getChildForGroup.append("                        AND                        (  ");
        	 getChildForGroup.append("                                CASE ");
        	 getChildForGroup.append("                                        WHEN AIC.PARENT_MDMID IS NOT NULL ");
        	 getChildForGroup.append("                                                AND AIC.MDMID !=AGCM.COMPONENT_STYLECOLOR_ID ");
        	 getChildForGroup.append("                                        THEN 0 ");
        	 getChildForGroup.append("                                        ELSE 1 ");
        	 getChildForGroup.append("                                END ) = 1 "); 
        	 getChildForGroup.append("                      AND ( PET_STATE        = '01' ");
        	 getChildForGroup.append("                      OR (APC.Entry_type     ='Style' ");
        	 getChildForGroup.append("                      AND APC.PET_STYLE_STATE='Y' ");
        	 getChildForGroup.append("                      AND PET_STATE         <>'05' ) ) ");
        	 getChildForGroup.append("                      AND AGCM.MDMID         = :orinNum   "); 
        	 if(!StringUtils.isBlank(strGroupId))
        	 {
        		 getChildForGroup.append("                      AND EXISTS (SELECT 1 FROM ADSE_GROUP_CHILD_MAPPING WHERE MDMID = :parentGroupId AND COMPONENT_GROUPING_ID=:orinNum AND (COMPONENT_STYLE_ID=AIC.MDMID OR COMPONENT_STYLE_ID is NULL)) ");
        	 }
        	 getChildForGroup.append("                      union ");
        	 getChildForGroup.append("                      SELECT  AIC.PARENT_MDMID, ");
        	 getChildForGroup.append("                      SUBSTR(AIC.MDMID,1,9) ||  ' '  || SUBSTR(AIC.MDMID,10,12) || ' ' ||  AIC.COLOR_Name ,");
        	 getChildForGroup.append("                        AIC.PRODUCT_NAME PRODUCTNAME, ");
        	 getChildForGroup.append("                        XMLCAST( (XMLQUERY('pim_entry/entry/Pet_Ctg_Spec/Completion_Date' PASSING apc.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) completion_date                , ");
        	 getChildForGroup.append("                        APC.PET_STATE PET_STATE               , ");
        	 getChildForGroup.append("                        APC.CONTENT_STATUS CONTENT_STATE      , ");
        	 getChildForGroup.append("                        APC.IMAGE_STATUS IMAGE_STATE          , ");
        	 getChildForGroup.append("                        NULL CHILD                            , ");
        	 getChildForGroup.append("                        AIC.DEPT_ID DEPT                      , ");
        	 getChildForGroup.append("                        AIC.ENTRY_TYPE                        , ");
        	 getChildForGroup.append("                        AGCM.PEP_COMPONENT_TYPE COMPONENT_TYPE, ");
        	 getChildForGroup.append("                        AIC.PRIMARY_SUPPLIER_ID, ");
        	 getChildForGroup.append("                        ASCT.supplier_name SUPPLIER_NAME    , ");
        	 getChildForGroup.append("                         XMLCAST( (XMLQUERY('/pim_entry/entry/Pet_Ctg_Spec/SourceSystem' PASSING apc.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) PET_SOURCE , ");        	 
        	 getChildForGroup.append("                        AIC.PRIMARYSUPPLIERVPN VENDOR_STYLE   , ");
        	 getChildForGroup.append("                        '' OmnichannelIndicator   , ");
        	 getChildForGroup.append("                        APC.CREATED_DTM                       , ");
        	 getChildForGroup.append("                        APC.EXIST_IN_GROUP                    , ");
        	 getChildForGroup.append("                        XMLCAST( (XMLQUERY('/pim_entry/entry/Pet_Ctg_Spec/System/Pet_Information/Conversion_Flag' PASSING apc.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) CONVERSION_FLAG,  APC.MISSING_ASSET ");        	     
        	 getChildForGroup.append("                FROM    ADSE_GROUP_CHILD_MAPPING AGCM, ");
        	 getChildForGroup.append("                        ADSE_ITEM_CATALOG AIC ");
        	 getChildForGroup.append("                        INNER JOIN ADSE_PET_CATALOG APC ");
        	 getChildForGroup.append("                        ON      AIC.MDMID=APC.MDMID ");
        	 getChildForGroup.append("                        LEFT OUTER JOIN ADSE_SUPPLIER_CATALOG ASCT ");
        	 getChildForGroup.append("                        ON      ASCT.MDMID = AIC.PRIMARY_SUPPLIER_ID ");
        	 getChildForGroup.append("                WHERE  AIC.PARENT_MDMID = AGCM.COMPONENT_STYLE_ID ");
        	 getChildForGroup.append("                        AND AGCM.PEP_COMPONENT_TYPE!    ='Group' ");
        	 getChildForGroup.append("                        AND AIC.ENTRY_TYPE             IN ('StyleColor') ");
        	 getChildForGroup.append("                        AND                        ( ");
        	 getChildForGroup.append("                                CASE ");
        	 getChildForGroup.append("                                        WHEN AIC.PARENT_MDMID IS NOT NULL ");
        	 getChildForGroup.append("                                                AND AIC.MDMID !=AGCM.COMPONENT_STYLECOLOR_ID ");
        	 getChildForGroup.append("                                        THEN 0 ");
        	 getChildForGroup.append("                                        ELSE 1 ");
        	 getChildForGroup.append("                                END ) =1");
        	 getChildForGroup.append("            AND ( PET_STATE        = '01' ");
        	 getChildForGroup.append("            OR (APC.Entry_type     ='Style' ");
        	 getChildForGroup.append("            AND APC.PET_STYLE_STATE='Y' ");
        	 getChildForGroup.append("            AND PET_STATE         <>'05' ) ) ");
        	 getChildForGroup.append("            AND AGCM.MDMID         = :orinNum  ");
        	 if(!StringUtils.isBlank(strGroupId))
        	 {
        		 getChildForGroup.append("            AND EXISTS (SELECT 1 FROM ADSE_GROUP_CHILD_MAPPING WHERE MDMID = :parentGroupId AND COMPONENT_GROUPING_ID=:orinNum AND ( COMPONENT_STYLECOLOR_ID=AIC.MDMID OR COMPONENT_STYLECOLOR_ID is NULL)) ");
        	 }
        	 getChildForGroup.append("            ) ORDER BY COMPONENT_TYPE DESC, ");
        	 getChildForGroup.append("            MDMID               , ");
        	 getChildForGroup.append("           COMPLETION_DATE ") ;
        

        
        LOGGER.info("Exiting getChildForGroupWorklistQuery() in Grouping XQueryConstant class.");
        return getChildForGroup.toString();
    }
    
}
