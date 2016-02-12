package com.belk.pep.constants;

import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import com.belk.pep.model.AdvanceSearch;
import com.belk.pep.util.PropertiesFileLoader;

import java.util.Date;
import java.util.Properties;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


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
                +" Input(emailId) as (Select '"
                + vendorEmail
                +"' from dual)"
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
             +"' is not null and (DeptId like ('%' || '"
             + searchString
             +" '|| '%') "
             +" or Dept like ('%' || '"
             + searchString
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
 public String getWorkListDisplayData(String depts, String email, String pepId, String supplierId) {
     //String GET_ALL_ORIN_WITH_PETS_XQUERY=null;
     
     if(depts!=null){
         depts = "'"+depts+"'";
         }
     if(email!=null){
         email = "'"+email+"'";  
     }
     if(pepId!=null){
         pepId = "'"+pepId+"'";  
     }
     if(supplierId!=null){
         supplierId = "'"+supplierId+"'";  
     }

     StringBuffer workListQuery = new StringBuffer();
     workListQuery.append("WITH ");
     workListQuery.append("  Input(Depts, EmailId, LANId, SuppIds) AS ");
     workListQuery.append("  ( ");
     workListQuery.append("    SELECT ");
     workListQuery.append("         ");
     workListQuery.append(depts);
     workListQuery.append(" Depts, ");
     workListQuery.append(email);
     workListQuery.append(" EmailId, ");
     workListQuery.append(pepId);
     workListQuery.append(" LANId, ");
     workListQuery.append(supplierId);
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
    workListQuery.append("      SELECT /*+ index(aic ITEM_CTG_IDX8) */                                                                       ");
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
    workListQuery.append("        SELECT /*+ index(aic ITEM_CTG_IDX9) */                                                                     ");
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
    workListQuery.append("      SELECT /*+ use_nl(pet)  index(pet PET_CTG_IDX8) */                                                           ");
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
    workListQuery.append("        sda.mdmid     =pet.mdmid                                                                                   ");
    workListQuery.append("    ),                                                                                                             ");
    workListQuery.append("    finalList as (                                                                                                 ");
    workListQuery.append("      SELECT /*+ use_nl(supplier) index(supplier SUPP_CTG_IDX4) */                                                 ");
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
 public String getWorkListDisplayDataComplexPack(String depts, String email, String pepId, String supplierId) {

     //String GET_ALL_ORIN_WITH_PETS_XQUERY=null;
     
     if(depts!=null){
         depts = "'"+depts+"'";
         }
     if(email!=null){
         email = "'"+email+"'";  
     }
     if(pepId!=null){
         pepId = "'"+pepId+"'";  
     }
     if(supplierId!=null){
         supplierId = "'"+supplierId+"'";  
     }

     StringBuffer workListQuery = new StringBuffer();
     workListQuery.append("WITH ");
     workListQuery.append("  Input(Depts, EmailId, LANId, SuppIds) AS ");
     workListQuery.append("  ( ");
     workListQuery.append("    SELECT ");
     workListQuery.append("         ");
     workListQuery.append(depts);
     workListQuery.append(" Depts, ");
     workListQuery.append(email);
     workListQuery.append(" EmailId, ");
     workListQuery.append(pepId);
     workListQuery.append(" LANId, ");
     workListQuery.append(supplierId);
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
    workListQuery.append("      SELECT /*+ index(aic ITEM_CTG_IDX9) */                                                                  ");
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
    workListQuery.append("      SELECT /*+ use_nl(pet)  index(pet PET_CTG_IDX8) */                                                      ");
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
    workListQuery.append("        sda.mdmid     =pet.mdmid                                                                              ");
    workListQuery.append("    ),                                                                                                        ");
    workListQuery.append("    finalList as (                                                                                            ");
    workListQuery.append("      SELECT /*+ use_nl(supplier) index(supplier SUPP_CTG_IDX4) */                                            ");
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
 * @param deptids
 * @return
 */
public  String getClassDetailsUsingDeptnumbers(String deptids) {  
    
    /**
     * Modified by AFUAXK4
     * Date: 02/09/2016
     */
    

    StringBuilder  queryfragment = new StringBuilder();
    queryfragment.append("WITH Input(Depts) AS ");
    queryfragment.append("  (SELECT '");
    queryfragment.append(deptids);
    queryfragment.append("' Depts ");    
    queryfragment.append("  FROM dual) ");
    //Kept it part of development
    /*queryfragment.append(" ");
    queryfragment.append("select ClsId, Cls ");
    queryfragment.append("    from  ADSE_MERCHANDISE_HIERARCHY cls, ");
    queryfragment.append("        XMLTABLE('for $cls in $XML_DATA/pim_category/entry ");
    queryfragment.append("            where fn:count($cls/Merchandise_Hier_Spec/System/Removal_Flag) eq 0 or $cls/Merchandise_Hier_Spec/System/Removal_Flag eq \"false\" ");
    queryfragment.append("            return <out> ");
    queryfragment.append("                    <cls_id>{$cls/Merchandise_Hier_Spec/Identifiers/RMS_Id}</cls_id> ");
    queryfragment.append("                    <cls_name>{$cls/Merchandise_Hier_Spec/Name}</cls_name> ");
    queryfragment.append("                    <dept_id>{fn:tokenize($cls/../merchandise_category_header/full_path,\"\\||///\")[5]}</dept_id> ");
    queryfragment.append("                </out>' ");
    queryfragment.append("            passing cls.XML_DATA as \"XML_DATA\" ");
    queryfragment.append("            columns ");
    queryfragment.append("            Cls varchar2(64) path '/out/cls_name', ");
    queryfragment.append("            ClsId varchar2(64) path '/out/cls_id', ");
    queryfragment.append("            DeptId varchar2(10) path '/out/dept_id' ");
    queryfragment.append("        ) CLS_NAME, Input inp ");
    queryfragment.append("        where cls.ENTRY_TYPE = 5 and MOD_DTM = '01-JAN-00 12.00.00.000000000 PM' ");
    queryfragment.append("        and (depts   IS  NULL ");
    queryfragment.append("    or deptId      IN ");
    
    queryfragment.append("      (SELECT regexp_substr(depts,'[^,]+',1,LEVEL) ");
    queryfragment.append("      FROM dual ");
    queryfragment.append("        CONNECT BY regexp_substr(depts,'[^,]+',1,LEVEL) IS NOT NULL ");
    queryfragment.append("      )) ");
    queryfragment.append("        order by cast(ClsId as Integer)");*/
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
    queryfragment.append("      Removal_Flag VARCHAR2(10) path '/out/Removal_Flag') CLS_NAME,                                                 ");
    queryfragment.append("    Input inp                                                                                                       ");
    queryfragment.append("  WHERE cls.ENTRY_TYPE = 5                                                                                          ");
    queryfragment.append("  AND (CLS_NAME.Removal_Flag = 'false' or CLS_NAME.Removal_Flag is null)                                            ");
    queryfragment.append("  AND deptId in ( ");
    queryfragment.append(getDeptNumbersFormatted(deptids));
    queryfragment.append("  )                                                                                       ");
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
        vendorStyle =  "'"+advSearch.getVendorStyle()+"'";
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
        advQueryFragment.append("               )                                                                                                             ");
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
        advQueryFragment.append("         ia.ven_style,                                                                                                       ");
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
        advQueryFragment.append("       (                                                                                                                      ");
        advQueryFragment.append("         SELECT                                                                                                               ");
        advQueryFragment.append("           regexp_substr(inpImageStatus,'[^,]+',1,LEVEL)                                                                      ");
        advQueryFragment.append("         FROM                                                                                                                 ");
        advQueryFragment.append("           dual                                                                                                               ");
        advQueryFragment.append("           CONNECT BY regexp_substr(inpImageStatus,'[^,]+',1,LEVEL) IS NOT NULL                                               ");
        advQueryFragment.append("       )                                                                                                                      ");
        advQueryFragment.append("     )                                                                                                                        ");
        advQueryFragment.append("   AND                                                                                                                        ");
        advQueryFragment.append("     (                                                                                                                        ");
        advQueryFragment.append("       inpContentStatus IS NULL                                                                                               ");
        advQueryFragment.append("     OR pet.content_status    IN                                                                                              ");
        advQueryFragment.append("       (                                                                                                                      ");
        advQueryFragment.append("         SELECT                                                                                                               ");
        advQueryFragment.append("           regexp_substr(inpContentStatus,'[^,]+',1,LEVEL)                                                                    ");
        advQueryFragment.append("         FROM                                                                                                                 ");
        advQueryFragment.append("           dual                                                                                                               ");
        advQueryFragment.append("           CONNECT BY regexp_substr(inpContentStatus,'[^,]+',1,LEVEL) IS NOT NULL                                             ");
        advQueryFragment.append("       )                                                                                                                      ");
        advQueryFragment.append("     )                                                                                                                        ");
        advQueryFragment.append("   AND                                                                                                                        ");
        advQueryFragment.append("     (                                                                                                                        ");
        advQueryFragment.append("       RequestType IS NULL                                                                                                    ");
        advQueryFragment.append("     OR Req_Type       IN                                                                                                     ");
        advQueryFragment.append("       (                                                                                                                      ");
        advQueryFragment.append("         SELECT                                                                                                               ");
        advQueryFragment.append("           regexp_substr(RequestType,'[^,]+',1,LEVEL)                                                                         ");
        advQueryFragment.append("         FROM                                                                                                                 ");
        advQueryFragment.append("           dual                                                                                                               ");
        advQueryFragment.append("           CONNECT BY regexp_substr(RequestType,'[^,]+',1,LEVEL) IS NOT NULL                                                  ");
        advQueryFragment.append("       )                                                                                                                      ");
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
        advQueryFragment.append("         sia.ven_style,                                                                                                        ");
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
        advQueryFragment.append("           AND (INPUTPETSTATUS IS NULL OR sia.PETSTATUS = INPUTPETSTATUS) AND (VSTYLE IS NULL OR VEN_STYLE = VSTYLE))          ");
        advQueryFragment.append("         OR                                                                                                                    ");
        advQueryFragment.append("           (entry_type = 'StyleColor'                                                                                          ");
        advQueryFragment.append("           AND PARENT_MDMID IN                                                                                                 ");
        advQueryFragment.append("               (SELECT MDMID   FROM supplierDetails WHERE ENTRY_TYPE='Style' AND                                               ");
        advQueryFragment.append("                 (INPUTPETSTATUS IS NULL OR PETSTATUS = INPUTPETSTATUS) AND (VSTYLE IS NULL OR VEN_STYLE = VSTYLE)             ");
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
        advQueryFragment.append("           (                                                                                                                   ");
        advQueryFragment.append("             SELECT                                                                                                            ");
        advQueryFragment.append("               regexp_substr(classes,'[^,]+',1,LEVEL)                                                                          ");
        advQueryFragment.append("             FROM                                                                                                              ");
        advQueryFragment.append("               dual                                                                                                            ");
        advQueryFragment.append("               CONNECT BY regexp_substr(classes,'[^,]+',1,LEVEL) IS NOT NULL                                                   ");
        advQueryFragment.append("           )                                                                                                                   ");
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
        vendorStyle =  "'"+advSearch.getVendorStyle()+"'";
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
        advQueryFragment.append("         ia.ven_style,                                                                                            ");
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
        advQueryFragment.append("       (                                                                                                                               ");
        advQueryFragment.append("         SELECT                                                                                                                        ");
        advQueryFragment.append("           regexp_substr(inpImageStatus,'[^,]+',1,LEVEL)                                                                               ");
        advQueryFragment.append("         FROM                                                                                                                          ");
        advQueryFragment.append("           dual                                                                                                                        ");
        advQueryFragment.append("           CONNECT BY regexp_substr(inpImageStatus,'[^,]+',1,LEVEL) IS NOT NULL                                                        ");
        advQueryFragment.append("       )                                                                                                                               ");
        advQueryFragment.append("     )                                                                                                                                 ");
        advQueryFragment.append("   AND                                                                                                                                 ");
        advQueryFragment.append("     (                                                                                                                                 ");
        advQueryFragment.append("       inpContentStatus IS NULL                                                                                                        ");
        advQueryFragment.append("     OR pet.content_status    IN                                                                                                       ");
        advQueryFragment.append("       (                                                                                                                               ");
        advQueryFragment.append("         SELECT                                                                                                                        ");
        advQueryFragment.append("           regexp_substr(inpContentStatus,'[^,]+',1,LEVEL)                                                                             ");
        advQueryFragment.append("         FROM                                                                                                                          ");
        advQueryFragment.append("           dual                                                                                                                        ");
        advQueryFragment.append("           CONNECT BY regexp_substr(inpContentStatus,'[^,]+',1,LEVEL) IS NOT NULL                                                      ");
        advQueryFragment.append("       )                                                                                                                               ");
        advQueryFragment.append("     )                                                                                                                                 ");
        advQueryFragment.append("   AND                                                                                                                                 ");
        advQueryFragment.append("     (                                                                                                                                 ");
        advQueryFragment.append("       RequestType IS NULL                                                                                                             ");
        advQueryFragment.append("     OR Req_Type       IN                                                                                                              ");
        advQueryFragment.append("       (                                                                                                                               ");
        advQueryFragment.append("         SELECT                                                                                                                        ");
        advQueryFragment.append("           regexp_substr(RequestType,'[^,]+',1,LEVEL)                                                                                  ");
        advQueryFragment.append("         FROM                                                                                                                          ");
        advQueryFragment.append("           dual                                                                                                                        ");
        advQueryFragment.append("           CONNECT BY regexp_substr(RequestType,'[^,]+',1,LEVEL) IS NOT NULL                                                           ");
        advQueryFragment.append("       )                                                                                                                               ");
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
        advQueryFragment.append("         sia.ven_style,                                                                                                                 ");
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
        advQueryFragment.append("           AND (INPUTPETSTATUS IS NULL OR sia.PETSTATUS = INPUTPETSTATUS) AND (VSTYLE IS NULL OR VEN_STYLE = VSTYLE))                   ");
        advQueryFragment.append("         OR                                                                                                                             ");
        advQueryFragment.append("           (entry_type = 'PackColor'                                                                                                    ");
        advQueryFragment.append("           AND PARENT_MDMID IN                                                                                                          ");
        advQueryFragment.append("               (SELECT MDMID   FROM styleID_DescAA WHERE ENTRY_TYPE='Complex Pack' AND                                                  ");
        advQueryFragment.append("                   (INPUTPETSTATUS IS NULL OR PETSTATUS = INPUTPETSTATUS) AND (VSTYLE IS NULL OR VEN_STYLE = VSTYLE)                    ");
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
        advQueryFragment.append("           (                                                                                                                            ");
        advQueryFragment.append("             SELECT                                                                                                                     ");
        advQueryFragment.append("               regexp_substr(classes,'[^,]+',1,LEVEL)                                                                                   ");
        advQueryFragment.append("             FROM                                                                                                                       ");
        advQueryFragment.append("               dual                                                                                                                     ");
        advQueryFragment.append("               CONNECT BY regexp_substr(classes,'[^,]+',1,LEVEL) IS NOT NULL                                                            ");
        advQueryFragment.append("           )                                                                                                                            ");
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
public String getWorkListDisplayDataParent(String depts, String email, String pepId, String supplierId, boolean vendorLogin) {
    
     if(depts!=null){
         depts = "'"+depts+"'";
         }
     if(email!=null){
         email = "'"+email+"'";  
     }
     if(pepId!=null){
         pepId = "'"+pepId+"'";  
     }
     if(supplierId!=null){
         supplierId = "'"+supplierId+"'";  
     }
    
     StringBuffer workListQuery = new StringBuffer();
     workListQuery.append("WITH ");
     workListQuery.append("  Input(Depts, EmailId, LANId, SuppIds) AS ");
     workListQuery.append("  ( ");
     workListQuery.append("    SELECT ");
     workListQuery.append("         ");
     workListQuery.append(depts);
     workListQuery.append(" Depts, ");
     workListQuery.append(email);
     workListQuery.append(" EmailId, ");
     workListQuery.append(pepId);
     workListQuery.append(" LANId, ");
     workListQuery.append(supplierId);
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
    workListQuery.append("      SELECT /*+ index(aic ITEM_CTG_IDX8) */                                                                          ");
    workListQuery.append("        PARENT_MDMID,                                                                                                 ");
    workListQuery.append("        MDMID,                                                                                                        ");
    workListQuery.append("        XML_DATA,                                                                                                     ");
    workListQuery.append("        ENTRY_TYPE,                                                                                                   ");
    workListQuery.append("        MOD_DTM,                                                                                                      ");
    workListQuery.append("        CASE                                                                                                          ");
    workListQuery.append("          when PRIMARY_UPC is null                                                                                    ");
    workListQuery.append("          then NUMBER_04                                                                                              ");
    workListQuery.append("           when PRIMARY_UPC = ' '                                                                                     ");
    workListQuery.append("           then NUMBER_04                                                                                             ");
    workListQuery.append("          else     PRIMARY_UPC                                                                                        ");
    workListQuery.append("        end PRIMARY_UPC,                                                                                              ");
    workListQuery.append("        aic.DEPT_ID, aic.PRIMARY_SUPPLIER_ID Supplier_Id                                                              ");
    workListQuery.append("      FROM                                                                                                            ");
    workListQuery.append("        ADSE_Item_CATALOG aic, givenInpDept d, givenInpSupplier s, Input inp                                          ");
    workListQuery.append("      WHERE                                                                                                           ");
    workListQuery.append("        ENTRY_TYPE in ('Style', 'Complex Pack')                                                                       ");
    workListQuery.append("        AND                                                                                                           ");
    workListQuery.append("         ((                                                                                                           ");
    workListQuery.append("             (inp.EmailId IS NULL)                                                                                    ");
    workListQuery.append("              AND aic.DEPT_ID = d.dept_id                                                                             ");
    workListQuery.append("          )                                                                                                           ");
    workListQuery.append("          OR                                                                                                          ");
    workListQuery.append("            ( (inp.EmailId IS NOT NULL)                                                                               ");
    workListQuery.append("            AND aic.DEPT_ID =d.dept_id                                                                                ");
    workListQuery.append("              AND inp.SuppIds is not null AND aic.PRIMARY_SUPPLIER_ID = s.supplier_id                                 ");
    workListQuery.append("        ))                                                                                                            ");
    workListQuery.append("    ),                                                                                                                ");
    workListQuery.append("		styleID_DescA AS(                                                  ");
	workListQuery.append("			SELECT                                                         ");
	workListQuery.append("			  sl.PARENT_MDMID,                                             ");
	workListQuery.append("			  sl.MDMID,                                                    ");
	workListQuery.append("			  sl.XML_DATA,                                                 ");
	workListQuery.append("			  sl.ENTRY_TYPE,                                               ");
	workListQuery.append("			  sl.PRIMARY_UPC,	                                           ");
	workListQuery.append("			  sl.Supplier_Id,                                              ");
	workListQuery.append("			  sl.DEPT_ID,                                                  ");
	workListQuery.append("			  ia.ven_style                                                 ");
	workListQuery.append("			FROM                                                           ");
	workListQuery.append("			  entryTypeStyleList sl,                                       ");
	workListQuery.append("			  XMLTABLE('//pim_entry/entry/Item_Ctg_Spec/Supplier'          ");
	workListQuery.append("			  passing sl.XML_DATA                                          ");
	workListQuery.append("			  columns                                                      ");
	workListQuery.append("			  ven_style    VARCHAR(25) path 'VPN',                         ");
	workListQuery.append("			  primary_flag VARCHAR(25) path 'Primary_Flag') ia		       ");
	workListQuery.append("			WHERE                                                          ");
	workListQuery.append("			  primary_flag = 'true'	  		                               ");
	workListQuery.append("		),                                                                 ");
    workListQuery.append("  styleID_DescAA AS(                                                                                                  ");
    workListQuery.append("      SELECT /*+ use_nl(pet)  index(pet PET_CTG_IDX8) */                                                              ");
    workListQuery.append("        sda.PARENT_MDMID,                                                                                             ");
    workListQuery.append("        sda.MDMID ORIN_NUM,                                                                                           ");
    workListQuery.append("        sda.XML_DATA,                                                                                                 ");
    workListQuery.append("        sda.ENTRY_TYPE,                                                                                               ");
    workListQuery.append("        sda.PRIMARY_UPC,                                                                                              ");
    workListQuery.append("        sda.Supplier_Id,                                                                                              ");
    workListQuery.append("        sda.DEPT_ID,                                                                                                  ");
    workListQuery.append("        p.PRODUCT_NAME,                                                                                               ");
    workListQuery.append("        sda.ven_style,                                                                                                ");
    workListQuery.append("        pet.pet_state PETSTATUS,                                                                                      ");
    workListQuery.append("        pet.image_status ImageState,                                                                                  ");
    workListQuery.append("        pet.content_status CONTENTSTATUS,                                                                             ");
    workListQuery.append("        p.completion_date,                                                                                            ");
    workListQuery.append("        p.req_type,                                                                                                   ");
    workListQuery.append("        pet.PET_STYLE_STATE,                                                                                          ");
    workListQuery.append("        pet.PET_STYLE_IMAGE,                                                                                          ");
    workListQuery.append("        pet.PET_STYLE_CONTENT, pet.PET_EARLIEST_COMP_DATE, p.PRODUCT_NAME_COMPLEX                                     ");
    workListQuery.append("     from                                                                                                             ");
    workListQuery.append("        styleID_DescA sda,                                                                                            ");
    workListQuery.append("        ADSE_PET_CATALOG pet,                                                                                         ");
    workListQuery.append("        XMLTABLE(                                                                                                     ");
    workListQuery.append("        'let                                                                                                          ");
    workListQuery.append("        $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date                                         ");
    workListQuery.append("        return                                                                                                        ");
    workListQuery.append("        <out>                                                                                                         ");
    workListQuery.append("          <completion_date>{$completionDate}</completion_date>                                                        ");
    workListQuery.append("          <req_type>{$pets/pim_entry/entry/Pet_Ctg_Spec/SourceSystem}</req_type>                                      ");
    workListQuery.append("          <PRODUCT_NAME>{$pets/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>                          ");
    workListQuery.append("          <PRODUCT_NAME_COMPLEX>{$pets/pim_entry/entry/Ecomm_ComplexPack_Spec/Product_Name}</PRODUCT_NAME_COMPLEX>    ");
    workListQuery.append("        </out>'                                                                                                       ");
    workListQuery.append("        passing pet.xml_data AS \"pets\" Columns                                                                        ");
    workListQuery.append("        completion_date             VARCHAR2(10)  path  '/out/completion_date',                                       ");
    workListQuery.append("        req_type                    VARCHAR2(20)  path '/out/req_type',                                               ");
    workListQuery.append("        PRODUCT_NAME                VARCHAR2(50)  path '/out/PRODUCT_NAME',                                           ");
    workListQuery.append("        PRODUCT_NAME_COMPLEX                VARCHAR2(50)  path '/out/PRODUCT_NAME_COMPLEX'                            ");
    workListQuery.append("        ) p                                                                                                           ");
    workListQuery.append("      WHERE                                                                                                           ");
    workListQuery.append("        sda.mdmid     =pet.mdmid                                                                                      ");
    if(vendorLogin){
        workListQuery.append("        AND ((pet.pet_state = '01' AND (pet.image_status NOT IN ('08','02') OR pet.CONTENT_STATUS NOT IN ('08','02'))) OR (pet.PET_STYLE_STATE = 'Y' AND (pet.PET_STYLE_IMAGE = 'Y' OR pet.PET_STYLE_CONTENT = 'Y'))) ");
    }else{
        workListQuery.append("        and (pet.pet_state = '01' OR PET_STYLE_STATE = 'Y')                                                          ");
    }
    workListQuery.append("    )                                                                                                                  ");
    workListQuery.append("      SELECT /*+ use_nl(supplier) index(supplier SUPP_CTG_IDX4) */                                                     ");
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
    workListQuery.append("        orin.PET_STYLE_CONTENT, orin.PET_EARLIEST_COMP_DATE, orin.PRODUCT_NAME_COMPLEX                                 ");
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
    workListQuery.append("        orin.Supplier_Id = supplier.mdmid                                                                              ");
     
     LOGGER.info("\n--------->> GET_ALL_PARENT_ORIN_WITH_PETS_XQUERY "
         + workListQuery.toString());

     return workListQuery.toString();
}


/**
 * Gets the work list display data for Child ORINs.
 *
 * @param Parent ORIN the parentOrin
 * @return the work list display data
 */
public String getWorkListDisplayDataChild(String parentOrin, boolean vendorLogin) {
    
     if(parentOrin!=null){
         parentOrin = "'"+parentOrin+"'";
         }         
    
     StringBuffer workListQuery = new StringBuffer();         
     workListQuery.append("WITH ");
     workListQuery.append("  Input(ORIN) AS ");
     workListQuery.append("  ( ");
     workListQuery.append("    SELECT ");         
     workListQuery.append(parentOrin);
     workListQuery.append(" ORIN ");         
     workListQuery.append("    FROM ");
     workListQuery.append("      dual ");
     workListQuery.append("  ) ");
     workListQuery.append("  , ");
    workListQuery.append("    styleListA as(                                                                                                     ");
    workListQuery.append("      SELECT /*+ index(aic ITEM_CTG_IDX9) */                                                                           ");
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
    workListQuery.append("          aic.DEPT_ID, aic.PRIMARY_SUPPLIER_ID Supplier_Id                                                             ");
    workListQuery.append("        FROM                                                                                                           ");
    workListQuery.append("          ADSE_ITEM_CATALOG aic, adse_pet_catalog pet, Input inp                                                       ");
    workListQuery.append("        WHERE                                                                                                          ");
    workListQuery.append("              aic.PARENT_MDMID = inp.ORIN                                                                              ");
    workListQuery.append("           AND                                                                                                         ");
    workListQuery.append("            aic.ENTRY_TYPE IN ('StyleColor', 'PackColor')                                                              ");
    workListQuery.append("              and aic.mdmid = pet.mdmid                                                                                ");
    workListQuery.append("              and pet.pet_state='01'                                                                                   ");
    if(vendorLogin){
        workListQuery.append("              AND (pet.IMAGE_STATUS NOT IN ('08','02') OR pet.CONTENT_STATUS NOT IN ('08','02'))                    ");
    }
    workListQuery.append("  ),                                                                                                                   ");
    workListQuery.append("		styleID_DescA AS(                                                 ");
	workListQuery.append("			SELECT                                                        ");
	workListQuery.append("			  sl.PARENT_MDMID,                                            ");
	workListQuery.append("			  sl.MDMID,                                                   ");
	workListQuery.append("			  sl.XML_DATA,                                                ");
	workListQuery.append("			  sl.ENTRY_TYPE,                                              ");
	workListQuery.append("			  sl.PRIMARY_UPC,	                                          ");
	workListQuery.append("			  sl.Supplier_Id,                                             ");
	workListQuery.append("			  sl.DEPT_ID,                                                 ");
	workListQuery.append("			  ia.ven_style       	                                      ");
	workListQuery.append("			FROM                                                          ");
	workListQuery.append("			  styleListA sl,                                              ");
	workListQuery.append("			  XMLTABLE('//pim_entry/entry/Item_Ctg_Spec/Supplier'         ");
	workListQuery.append("			  passing sl.XML_DATA                                         ");
	workListQuery.append("			  columns                                                     ");
	workListQuery.append("			  ven_style    VARCHAR(25) path 'VPN',                        ");
	workListQuery.append("			  primary_flag VARCHAR(25) path 'Primary_Flag') ia	 	      ");
	workListQuery.append("			WHERE                                                         ");
	workListQuery.append("			  primary_flag = 'true'		                                  ");
	workListQuery.append("		),                                                                ");
    workListQuery.append("  styleID_DescAA AS(                                                                                                   ");
    workListQuery.append("      SELECT /*+ use_nl(pet)  index(pet PET_CTG_IDX8) */                                                               ");
    workListQuery.append("        sda.PARENT_MDMID,                                                                                              ");
    workListQuery.append("        sda.MDMID ORIN_NUM,                                                                                            ");
    workListQuery.append("        sda.XML_DATA,                                                                                                  ");
    workListQuery.append("        sda.ENTRY_TYPE,                                                                                                ");
    workListQuery.append("        sda.PRIMARY_UPC,                                                                                               ");
    workListQuery.append("        p.COLO_DESC,                                                                                                   ");
    workListQuery.append("        sda.Supplier_Id,                                                                                               ");
    workListQuery.append("        sda.DEPT_ID,                                                                                                   ");
    workListQuery.append("        p.PRODUCT_NAME,                                                                                                ");
    workListQuery.append("        sda.ven_style,                                                                                                 ");
    workListQuery.append("        pet.pet_state PETSTATUS,                                                                                       ");
    workListQuery.append("        pet.image_status ImageState,                                                                                   ");
    workListQuery.append("        pet.content_status CONTENTSTATUS,                                                                              ");
    workListQuery.append("        p.completion_date,                                                                                             ");
    workListQuery.append("        p.req_type,                                                                                                    ");
    workListQuery.append("        p.COLO_DESC_COMPLEX,                                                                                           ");
    workListQuery.append("        p.PRODUCT_NAME_COMPLEX                                                                                         ");
    workListQuery.append("     from                                                                                                              ");
    workListQuery.append("        styleID_DescA sda,                                                                                             ");
    workListQuery.append("        ADSE_PET_CATALOG pet,                                                                                          ");
    workListQuery.append("        XMLTABLE(                                                                                                      ");
    workListQuery.append("        'let                                                                                                           ");
    workListQuery.append("        $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date,                                         ");
    workListQuery.append("        $colordesc:= $pets/pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,                                ");
    workListQuery.append("        $colordescComplex:= $pets/pim_entry/entry/Ecomm_PackColor_Spec/NRF_Color_Description                           ");
    workListQuery.append("        return                                                                                                         ");
    workListQuery.append("        <out>                                                                                                          ");
    workListQuery.append("          <completion_date>{$completionDate}</completion_date>                                                         ");
    workListQuery.append("          <req_type>{$pets/pim_entry/entry/Pet_Ctg_Spec/SourceSystem}</req_type>                                       ");
    workListQuery.append("          <PRODUCT_NAME>{$pets/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>                           ");
    workListQuery.append("          <PRODUCT_NAME_COMPLEX>{$pets/pim_entry/entry/Ecomm_ComplexPack_Spec/Product_Name}</PRODUCT_NAME_COMPLEX>     ");
    workListQuery.append("            <COLO_DESC>{$colordesc}</COLO_DESC>                                                                        ");
    workListQuery.append("            <COLO_DESC_COMPLEX>{$colordescComplex}</COLO_DESC_COMPLEX>                                                 ");
    workListQuery.append("        </out>'                                                                                                        ");
    workListQuery.append("        passing pet.xml_data AS \"pets\" Columns                                                                         ");
    workListQuery.append("        completion_date             VARCHAR2(10)  path  '/out/completion_date',                                        ");
    workListQuery.append("        req_type                    VARCHAR2(20)  path '/out/req_type',                                                ");
    workListQuery.append("        PRODUCT_NAME                VARCHAR2(50)  path '/out/PRODUCT_NAME',                                            ");
    workListQuery.append("          COLO_DESC              VARCHAR2(50)  path '/out/COLO_DESC',                                                  ");
    workListQuery.append("          COLO_DESC_COMPLEX              VARCHAR2(50)  path '/out/COLO_DESC_COMPLEX',                                  ");
    workListQuery.append("        PRODUCT_NAME_COMPLEX                VARCHAR2(50)  path '/out/PRODUCT_NAME_COMPLEX'                             ");
    workListQuery.append("        ) p                                                                                                            ");
    workListQuery.append("      WHERE                                                                                                            ");
    workListQuery.append("        sda.mdmid     =pet.mdmid                                                                                       ");
    workListQuery.append("    )                                                                                                                  ");
    workListQuery.append("    SELECT /*+ use_nl(supplier) index(supplier SUPP_CTG_IDX4) */                                                       ");
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
    workListQuery.append("        orin.COLO_DESC_COMPLEX,                                                                                        ");
    workListQuery.append("        orin.PRODUCT_NAME_COMPLEX                                                                                      ");
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
    workListQuery.append("        orin.Supplier_Id = supplier.mdmid ORDER BY ORIN_NUM DESC                                                       ");
     
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
    advQueryFragment.append("           SELECT /*+ index(aic ITEM_CTG_IDX9) */                                                                             ");
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
    advQueryFragment.append("               aic.DEPT_ID, aic.PRIMARY_SUPPLIER_ID Supplier_Id,                                                              ");
    advQueryFragment.append("               inp.classes,                                                                                                   ");
    advQueryFragment.append("               inp.CompletionFrom,inp.CompletionTo,                                                                           ");
    advQueryFragment.append("         inp.ImageStatus inpImageStatus,inp.ContentStatus inpContentStatus,                                                   ");
    advQueryFragment.append("         inp.RequestType,inp.createdToday, inp.ORIN PARENT_ORIN, inp.PETStatus INPUTPETSTATUS                                 ");
    advQueryFragment.append("             FROM                                                                                                             ");
    advQueryFragment.append("               ADSE_ITEM_CATALOG aic, adse_pet_catalog pet, Input inp                                                         ");
    advQueryFragment.append("             WHERE                                                                                                            ");
    advQueryFragment.append("                 aic.PARENT_MDMID = inp.ORIN                                                                                  ");
    advQueryFragment.append("              AND                                                                                                             ");
    advQueryFragment.append("                 aic.ENTRY_TYPE IN ('StyleColor', 'PackColor')                                                                ");
    advQueryFragment.append("                 and aic.mdmid = pet.mdmid                                                                                    ");
    advQueryFragment.append("       )                                                                                                                      ");
    advQueryFragment.append("	,styleID_DescA1 AS(                                                                   ");
	advQueryFragment.append("		SELECT                                                                            ");
	advQueryFragment.append("		  sl.PARENT_MDMID,                                                                ");
	advQueryFragment.append("		  sl.MDMID,                                                                       ");
	advQueryFragment.append("		  sl.XML_DATA,                                                                    ");
	advQueryFragment.append("		  sl.ENTRY_TYPE,                                                                  ");
	advQueryFragment.append("		  sl.PRIMARY_UPC,                                                                 ");
	advQueryFragment.append("		  sl.Supplier_Id,                                                                 ");
	advQueryFragment.append("		  sl.DEPT_ID,                                                                     ");
	advQueryFragment.append("		  sl.INPUTPETSTATUS,                                                              ");
	advQueryFragment.append("		  sl.CompletionFrom,sl.CompletionTo,sl.inpImageStatus,sl.inpContentStatus,sl.RequestType,sl.createdToday, sl.PARENT_ORIN, sl.classes, ");	
	advQueryFragment.append("		  iaa.ven_style                                                                   ");
	advQueryFragment.append("		FROM                                                                              ");
	advQueryFragment.append("		  styleListA sl,                                                                  ");
	advQueryFragment.append("			  XMLTABLE('//pim_entry/entry/Item_Ctg_Spec/Supplier'                         ");
	advQueryFragment.append("			  passing sl.XML_DATA                                                         ");
	advQueryFragment.append("			  columns                                                                     ");
	advQueryFragment.append("			  ven_style    VARCHAR(25) path 'VPN',                                        ");
	advQueryFragment.append("			  primary_flag VARCHAR(25) path 'Primary_Flag') iaa		                      ");
	advQueryFragment.append("			WHERE                                                                         ");
	advQueryFragment.append("			  iaa.primary_flag = 'true'	                                                  ");
	advQueryFragment.append("	   ),                                                                                 ");
	advQueryFragment.append("	   styleID_DescA AS(                                                                  ");
	advQueryFragment.append("		SELECT                                                                            ");
	advQueryFragment.append("		  sl.PARENT_MDMID,                                                                ");
	advQueryFragment.append("		  sl.MDMID,                                                                       ");
	advQueryFragment.append("		  sl.XML_DATA,                                                                    ");
	advQueryFragment.append("		  sl.ENTRY_TYPE,                                                                  ");
	advQueryFragment.append("		  sl.PRIMARY_UPC,                                                                 ");
	advQueryFragment.append("		  sl.Supplier_Id,                                                                 ");
	advQueryFragment.append("		  sl.DEPT_ID,                                                                     ");
	advQueryFragment.append("		  sl.ven_style,                                                                   ");
	advQueryFragment.append("		  ia.clsId,                                                                       ");
	advQueryFragment.append("		  sl.INPUTPETSTATUS,                                                              ");
	advQueryFragment.append("		  sl.CompletionFrom,sl.CompletionTo,sl.inpImageStatus,sl.inpContentStatus,sl.RequestType,sl.createdToday, sl.PARENT_ORIN   ");
	advQueryFragment.append("		FROM                                                                                                                       ");
	advQueryFragment.append("		  styleID_DescA1 sl,                                                                                                       ");
	advQueryFragment.append("		  XMLTABLE(                                                                                                                ");
	advQueryFragment.append("		  'let                                                                                                                     ");
	advQueryFragment.append("		  $marchant_id := fn:tokenize($XML_DATA/pim_entry/item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path, \"\\||///\") ");
	advQueryFragment.append("		  return                                                                                                                  ");
	advQueryFragment.append("		  <out>                                                                                                                   ");
	advQueryFragment.append("			<cls_id>{$marchant_id[6]}</cls_id>                                                                                    ");
	advQueryFragment.append("		  </out>'                                                                                                                 ");
	advQueryFragment.append("		  passing sl.XML_DATA AS \"XML_DATA\" columns                                                                               ");
	advQueryFragment.append("		  clsId VARCHAR(10) path  '/out/cls_id') ia                                                                               ");
	advQueryFragment.append("		                                                                                                                          ");
	advQueryFragment.append("		WHERE                                                                                                                     ");
	advQueryFragment.append("		classes IS NULL                                                                                                           ");
	advQueryFragment.append("		   OR clsId  IN                                                                                                           ");
	advQueryFragment.append("			(                                                                                                                     ");
	advQueryFragment.append("			  SELECT                                                                                                              ");
	advQueryFragment.append("				regexp_substr(classes,'[^,]+',1,LEVEL)                                                                            ");
	advQueryFragment.append("			  FROM                                                                                                                ");
	advQueryFragment.append("				dual                                                                                                              ");
	advQueryFragment.append("				CONNECT BY regexp_substr(classes,'[^,]+',1,LEVEL) IS NOT NULL                                                     ");
	advQueryFragment.append("			)                                                                                                                     ");
	advQueryFragment.append("		                                                                                                                          ");
	advQueryFragment.append("	   ),                                                                                                                         ");
    advQueryFragment.append("     styleID_DescAA AS(                                                                                                          ");
    advQueryFragment.append("       SELECT                                                                                                                    ");
    advQueryFragment.append("         sda.PARENT_MDMID,                                                                                                       ");
    advQueryFragment.append("         sda.MDMID,                                                                                                              ");
    advQueryFragment.append("         sda.XML_DATA,                                                                                                           ");
    advQueryFragment.append("         sda.ENTRY_TYPE,                                                                                                         ");
    advQueryFragment.append("         sda.PRIMARY_UPC,                                                                                                        ");
    advQueryFragment.append("         p.COLO_DESC,                                                                                                            ");
    advQueryFragment.append("         sda.Supplier_Id,                                                                                                        ");
    advQueryFragment.append("         sda.DEPT_ID,                                                                                                            ");
    advQueryFragment.append("         p.PRODUCT_NAME,                                                                                                         ");
    advQueryFragment.append("         sda.ven_style,                                                                                                          ");
    advQueryFragment.append("         sda.clsId,                                                                                                              ");
    advQueryFragment.append("         sda.INPUTPETSTATUS,                                                                                                     ");
    advQueryFragment.append("         sda.PARENT_ORIN,                                                                                                        ");
    advQueryFragment.append("         p.completion_date,                                                                                                      ");
    advQueryFragment.append("         p.req_type,                                                                                                             ");
    advQueryFragment.append("         pet.pet_state PETSTATUS,                                                                                                ");
    advQueryFragment.append("         pet.image_status ImageState,                                                                                            ");
    advQueryFragment.append("         pet.content_status CONTENTSTATUS,                                                                                       ");
    advQueryFragment.append("         p.COLO_DESC_COMPLEX,                                                                                                    ");
    advQueryFragment.append("         p.PRODUCT_NAME_COMPLEX                                                                                                  ");
    advQueryFragment.append("   FROM                                                                                                                          ");
    advQueryFragment.append("     styleID_DescA sda,                                                                                                          ");
    advQueryFragment.append("     ADSE_PET_CATALOG pet,                                                                                                       ");
    advQueryFragment.append("     XMLTABLE(                                                                                                                   ");
    advQueryFragment.append("     'let                                                                                                                        ");
    advQueryFragment.append("     $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date,                                                      ");
    advQueryFragment.append("     $colordesc:= $pets/pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,                                             ");
    advQueryFragment.append("     $colordescComplex:= $pets/pim_entry/entry/Ecomm_PackColor_Spec/NRF_Color_Description                                        ");
    advQueryFragment.append("     return                                                                                                                      ");
    advQueryFragment.append("     <out>                                                                                                                       ");
    advQueryFragment.append("         <completion_date>{$completionDate}</completion_date>                                                                    ");
    advQueryFragment.append("         <req_type>{$pets/pim_entry/entry/Pet_Ctg_Spec/SourceSystem}</req_type>                                                  ");
    advQueryFragment.append("         <PRODUCT_NAME>{$pets/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>                                      ");
    advQueryFragment.append("         <COLO_DESC>{$colordesc}</COLO_DESC>                                                                                     ");
    advQueryFragment.append("         <COLO_DESC_COMPLEX>{$colordescComplex}</COLO_DESC_COMPLEX>                                                              ");
    advQueryFragment.append("         <PRODUCT_NAME_COMPLEX>{$pets/pim_entry/entry/Ecomm_ComplexPack_Spec/Product_Name}</PRODUCT_NAME_COMPLEX>                ");
    advQueryFragment.append("     </out>'                                                                                                                     ");
    advQueryFragment.append("     passing pet.xml_data AS \"pets\" Columns                                                                                      ");
    advQueryFragment.append("         completion_date      VARCHAR2(10) path  '/out/completion_date',                                                         ");
    advQueryFragment.append("         req_type             VARCHAR2(20) path '/out/req_type',                                                                 ");
    advQueryFragment.append("         PRODUCT_NAME         VARCHAR2(40) path '/out/PRODUCT_NAME',                                                             ");
    advQueryFragment.append("         COLO_DESC_COMPLEX    VARCHAR2(50)  path '/out/COLO_DESC_COMPLEX',                                                       ");
    advQueryFragment.append("         COLO_DESC            VARCHAR2(50)  path '/out/COLO_DESC',                                                               ");
    advQueryFragment.append("         PRODUCT_NAME_COMPLEX         VARCHAR2(40) path '/out/PRODUCT_NAME_COMPLEX'                                              ");
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
    advQueryFragment.append("       (                                                                                                                         ");
    advQueryFragment.append("         SELECT                                                                                                                  ");
    advQueryFragment.append("           regexp_substr(inpImageStatus,'[^,]+',1,LEVEL)                                                                         ");
    advQueryFragment.append("         FROM                                                                                                                    ");
    advQueryFragment.append("           dual                                                                                                                  ");
    advQueryFragment.append("           CONNECT BY regexp_substr(inpImageStatus,'[^,]+',1,LEVEL) IS NOT NULL                                                  ");
    advQueryFragment.append("       )                                                                                                                         ");
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
    advQueryFragment.append("         orin.COLO_DESC_COMPLEX,                                                                                                 ");
    advQueryFragment.append("         orin.PARENT_ORIN,                                                                                                       ");
    advQueryFragment.append("         orin.PRODUCT_NAME_COMPLEX                                                                                               ");
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
    advQueryFragment.append("         sia.COLO_DESC_COMPLEX,                                                                                                  ");
    advQueryFragment.append("         sia.PRODUCT_NAME_COMPLEX                                                                                                ");
    advQueryFragment.append("       from supplierDetails sia                                                                                                  ");
    advQueryFragment.append("       where                                                                                                                     ");
    advQueryFragment.append("           entry_type IN ('StyleColor', 'PackColor')                                                                             ");
    advQueryFragment.append("           AND PARENT_MDMID = PARENT_ORIN                                                                                        ");
    advQueryFragment.append("           AND (INPUTPETSTATUS IS NULL OR PETSTATUS = INPUTPETSTATUS) ORDER BY sia.MDMID DESC                                    "); 
    
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
        vendorStyle =  "'"+advSearch.getVendorStyle()+"'";
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
        advQueryFragment.append("     entryTypeStyleList AS                                                                                                ");
        advQueryFragment.append("     (                                                                                                                    ");
        advQueryFragment.append("       SELECT                                                                                                             ");
        advQueryFragment.append("         PARENT_MDMID,                                                                                                    ");
        advQueryFragment.append("         MDMID,                                                                                                           ");
        advQueryFragment.append("         XML_DATA,                                                                                                        ");
        advQueryFragment.append("         ENTRY_TYPE,                                                                                                      ");
        advQueryFragment.append("         MOD_DTM,                                                                                                         ");
        advQueryFragment.append("         CASE                                                                                                             ");
        advQueryFragment.append("         when PRIMARY_UPC is null                                                                                         ");
        advQueryFragment.append("         then NUMBER_04                                                                                                   ");
        advQueryFragment.append("          when PRIMARY_UPC = ' '                                                                                          ");
        advQueryFragment.append("          then NUMBER_04                                                                                                  ");
        advQueryFragment.append("         else     PRIMARY_UPC                                                                                             ");
        advQueryFragment.append("         end PRIMARY_UPC,                                                                                                 ");
        advQueryFragment.append("         DEPT_ID, PRIMARY_SUPPLIER_ID Supplier_Id                                                                         ");
        advQueryFragment.append("       FROM                                                                                                               ");
        advQueryFragment.append("         ADSE_Item_CATALOG, Input inp                                                                                     ");
        advQueryFragment.append("       WHERE                                                                                                              ");
        advQueryFragment.append("         ENTRY_TYPE IN ('Style', 'Complex Pack')                                                                          ");
        advQueryFragment.append("         AND                                                                                                              ");
        advQueryFragment.append("         (                                                                                                                ");
        advQueryFragment.append("           inp.primaryUpc is NULL                                                                                         ");
        advQueryFragment.append("           OR PRIMARY_UPC = inp.primaryUpc                                                                                ");
        advQueryFragment.append("           OR NUMBER_04 = inp.primaryUpc                                                                                  ");
        advQueryFragment.append("         )                                                                                                                ");
        advQueryFragment.append("         AND                                                                                                              ");
        advQueryFragment.append("             (                                                                                                            ");
        advQueryFragment.append("               inp.depts   IS NULL                                                                                        ");
        advQueryFragment.append("             OR DEPT_ID IN                                                                                                ");
        advQueryFragment.append("               (                                                                                                          ");
        advQueryFragment.append("                 SELECT                                                                                                   ");
        advQueryFragment.append("                   regexp_substr(inp.depts,'[^,]+',1,LEVEL)                                                               ");
        advQueryFragment.append("                 FROM                                                                                                     ");
        advQueryFragment.append("                   dual                                                                                                   ");
        advQueryFragment.append("                   CONNECT BY regexp_substr(inp.depts,'[^,]+',1,LEVEL) IS NOT NULL                                        ");
        advQueryFragment.append("               )                                                                                                          ");
        advQueryFragment.append("             )                                                                                                            ");
        advQueryFragment.append("           AND                                                                                                            ");
        advQueryFragment.append("             (                                                                                                            ");
        advQueryFragment.append("               inp.ORIN         IS NULL                                                                                   ");
        advQueryFragment.append("             OR MDMID        = inp.ORIN                                                                                   ");
        advQueryFragment.append("             )                                                                                                            ");
        advQueryFragment.append("           AND                                                                                                            ");
        advQueryFragment.append("             (                                                                                                            ");
        advQueryFragment.append("               inp.vendor is NULL                                                                                         ");
        advQueryFragment.append("               OR PRIMARY_SUPPLIER_ID = inp.vendor                                                                        ");
        advQueryFragment.append("             )                                                                                                            ");
        advQueryFragment.append("       UNION ALL                                                                                                          ");
        advQueryFragment.append("             SELECT                                                                                                       ");
        advQueryFragment.append("             PARENT_MDMID,                                                                                                ");
        advQueryFragment.append("             MDMID,                                                                                                       ");
        advQueryFragment.append("             XML_DATA,                                                                                                    ");
        advQueryFragment.append("             ENTRY_TYPE,                                                                                                  ");
        advQueryFragment.append("             MOD_DTM,                                                                                                     ");
        advQueryFragment.append("             CASE                                                                                                         ");
        advQueryFragment.append("             when PRIMARY_UPC is null                                                                                     ");
        advQueryFragment.append("             then NUMBER_04                                                                                               ");
        advQueryFragment.append("              when PRIMARY_UPC = ' '                                                                                      ");
        advQueryFragment.append("              then NUMBER_04                                                                                              ");
        advQueryFragment.append("             else     PRIMARY_UPC                                                                                         ");
        advQueryFragment.append("             end PRIMARY_UPC,                                                                                             ");
        advQueryFragment.append("             DEPT_ID, PRIMARY_SUPPLIER_ID Supplier_Id                                                                     ");
        advQueryFragment.append("           FROM                                                                                                           ");
        advQueryFragment.append("             ADSE_Item_CATALOG, Input inp                                                                                 ");
        advQueryFragment.append("           WHERE                                                                                                          ");
        advQueryFragment.append("               MDMID IN (                                                                                                 ");
        advQueryFragment.append("                   SELECT PARENT_MDMID FROM ADSE_Item_CATALOG WHERE ENTRY_TYPE = 'SKU'                                    ");
        advQueryFragment.append("                   AND inp.primaryUpc is NOT NULL AND PRIMARY_UPC = inp.primaryUpc                                        ");
        advQueryFragment.append("               )                                                                                                          ");
        advQueryFragment.append("     ),                                                                                                                   ");
        advQueryFragment.append("     itemGroup as(                                                                                                        ");
        advQueryFragment.append("       SELECT                                                                                                             ");
        advQueryFragment.append("         PARENT_MDMID,                                                                                                    ");
        advQueryFragment.append("         MDMID,                                                                                                           ");
        advQueryFragment.append("         XML_DATA,                                                                                                        ");
        advQueryFragment.append("         ENTRY_TYPE,                                                                                                      ");
        advQueryFragment.append("         MOD_DTM,                                                                                                         ");
        advQueryFragment.append("         PRIMARY_UPC,                                                                                                     ");
        advQueryFragment.append("         DEPT_ID, Supplier_Id,                                                                                            ");
        advQueryFragment.append("         inp.PETStatus INPUTPETSTATUS,                                                                                    ");
        advQueryFragment.append("         inp.VENDOR_STYLE VSTYLE,                                                                                         ");
        advQueryFragment.append("         inp.classes,                                                                                                     ");
        advQueryFragment.append("         inp.CompletionFrom,inp.CompletionTo,inp.ImageStatus inpImageStatus,                                              ");
        advQueryFragment.append("         inp.ContentStatus inpContentStatus,inp.RequestType,inp.createdToday                                              ");
        advQueryFragment.append("       FROM                                                                                                               ");
        advQueryFragment.append("         (                                                                                                                ");
        advQueryFragment.append("           SELECT                                                                                                         ");
        advQueryFragment.append("             PARENT_MDMID,                                                                                                ");
        advQueryFragment.append("             MDMID,                                                                                                       ");
        advQueryFragment.append("             XML_DATA,                                                                                                    ");
        advQueryFragment.append("             ENTRY_TYPE,                                                                                                  ");
        advQueryFragment.append("             MOD_DTM,                                                                                                     ");
        advQueryFragment.append("             PRIMARY_UPC,                                                                                                 ");
        advQueryFragment.append("             DEPT_ID, Supplier_Id                                                                                         ");
        advQueryFragment.append("           FROM                                                                                                           ");
        advQueryFragment.append("             entryTypeStyleList                                                                                           ");
        advQueryFragment.append("       UNION ALL                                                                                                          ");
        advQueryFragment.append("           SELECT                                                                                                         ");
        advQueryFragment.append("             PARENT_MDMID,                                                                                                ");
        advQueryFragment.append("             MDMID,                                                                                                       ");
        advQueryFragment.append("             XML_DATA,                                                                                                    ");
        advQueryFragment.append("             ENTRY_TYPE,                                                                                                  ");
        advQueryFragment.append("             MOD_DTM,                                                                                                     ");
        advQueryFragment.append("             CASE                                                                                                         ");
        advQueryFragment.append("             when PRIMARY_UPC is null                                                                                     ");
        advQueryFragment.append("             then NUMBER_04                                                                                               ");
        advQueryFragment.append("              when PRIMARY_UPC = ' '                                                                                      ");
        advQueryFragment.append("              then NUMBER_04                                                                                              ");
        advQueryFragment.append("             else     PRIMARY_UPC                                                                                         ");
        advQueryFragment.append("             end PRIMARY_UPC,                                                                                             ");
        advQueryFragment.append("             DEPT_ID, PRIMARY_SUPPLIER_ID Supplier_Id                                                                     ");
        advQueryFragment.append("           FROM                                                                                                           ");
        advQueryFragment.append("             ADSE_ITEM_CATALOG                                                                                            ");
        advQueryFragment.append("           WHERE                                                                                                          ");
        advQueryFragment.append("             PARENT_MDMID IN (SELECT MDMID FROM entryTypeStyleList)                                                       ");
        advQueryFragment.append("             AND ENTRY_TYPE IN ('StyleColor', 'PackColor')                                                                ");
        advQueryFragment.append("         ) aic, Input inp                                                                                                 ");
        advQueryFragment.append("      )                                                                                                                   ");
        advQueryFragment.append("	,styleID_DescA1 AS(                                                                                                  ");
		advQueryFragment.append("		SELECT                                                                                                           ");
		advQueryFragment.append("		  sl.PARENT_MDMID,                                                                                               ");
		advQueryFragment.append("		  sl.MDMID,                                                                                                      ");
		advQueryFragment.append("		  sl.XML_DATA,                                                                                                   ");
		advQueryFragment.append("		  sl.ENTRY_TYPE,                                                                                                 ");
		advQueryFragment.append("		  sl.PRIMARY_UPC,                                                                                                ");
		advQueryFragment.append("		  sl.Supplier_Id,                                                                                                ");
		advQueryFragment.append("		  sl.DEPT_ID,                                                                                                    ");
		advQueryFragment.append("		  iaa.ven_style,                                                                                                 ");
		advQueryFragment.append("		  sl.INPUTPETSTATUS,                                                                                             ");
		advQueryFragment.append("		  sl.VSTYLE,                                                                                                     ");
		advQueryFragment.append("		  sl.classes,                                                                                                    ");
		advQueryFragment.append("		  sl.CompletionFrom,sl.CompletionTo,sl.inpImageStatus,sl.inpContentStatus,sl.RequestType,sl.createdToday         ");
		advQueryFragment.append("		FROM                                                                                                             ");
		advQueryFragment.append("		  itemGroup sl,                                                                                                  ");
		advQueryFragment.append("			  XMLTABLE('//pim_entry/entry/Item_Ctg_Spec/Supplier'                                                        ");
		advQueryFragment.append("			  passing sl.XML_DATA                                                                                        ");
		advQueryFragment.append("			  columns                                                                                                    ");
		advQueryFragment.append("			  ven_style    VARCHAR(25) path 'VPN',                                                                       ");
		advQueryFragment.append("			  primary_flag VARCHAR(25) path 'Primary_Flag') iaa		                                                     ");
		advQueryFragment.append("			WHERE                                                                                                        ");
		advQueryFragment.append("			  iaa.primary_flag = 'true'	                                                                                 ");
		advQueryFragment.append("	   ),                                                                                                                ");
		advQueryFragment.append("	   styleID_DescA AS(                                                                                                 ");
		advQueryFragment.append("		SELECT                                                                                                           ");
		advQueryFragment.append("		  sl.PARENT_MDMID,                                                                                               ");
		advQueryFragment.append("		  sl.MDMID,                                                                                                      ");
		advQueryFragment.append("		  sl.XML_DATA,                                                                                                   ");
		advQueryFragment.append("		  sl.ENTRY_TYPE,                                                                                                 ");
		advQueryFragment.append("		  sl.PRIMARY_UPC,                                                                                                ");
		advQueryFragment.append("		  sl.Supplier_Id,                                                                                                ");
		advQueryFragment.append("		  sl.DEPT_ID,                                                                                                    ");
		advQueryFragment.append("		  sl.ven_style,                                                                                                  ");
		advQueryFragment.append("		  ia.clsId,                                                                                                      ");
		advQueryFragment.append("		  sl.INPUTPETSTATUS,                                                                                             ");
		advQueryFragment.append("		  sl.VSTYLE,                                                                                                     ");
		advQueryFragment.append("		  sl.classes,                                                                                                    ");
		advQueryFragment.append("		  sl.CompletionFrom,sl.CompletionTo,sl.inpImageStatus,sl.inpContentStatus,sl.RequestType,sl.createdToday         ");
		advQueryFragment.append("		FROM                                                                                                             ");
		advQueryFragment.append("		  styleID_DescA1 sl,                                                                                             ");
		advQueryFragment.append("		  XMLTABLE(                                                                                                      ");
		advQueryFragment.append("		  'let                                                                                                           ");
		advQueryFragment.append("		  $marchant_id := fn:tokenize($XML_DATA/pim_entry/item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path, \"\\||///\") ");
		advQueryFragment.append("		  return                                                                                                          ");
		advQueryFragment.append("		  <out>                                                                                                           ");
		advQueryFragment.append("			<cls_id>{$marchant_id[6]}</cls_id>                                                                            ");
		advQueryFragment.append("		  </out>'                                                                                                         ");
		advQueryFragment.append("		  passing sl.XML_DATA AS \"XML_DATA\" columns                                                                       ");
		advQueryFragment.append("		  clsId VARCHAR(10) path  '/out/cls_id') ia                                                                       ");
		advQueryFragment.append("		WHERE                                                                                                             ");
		advQueryFragment.append("		classes IS NULL                                                                                                   ");
		advQueryFragment.append("		   OR clsId  IN                                                                                                   ");
		advQueryFragment.append("			(                                                                                                             ");
		advQueryFragment.append("			  SELECT                                                                                                      ");
		advQueryFragment.append("				regexp_substr(classes,'[^,]+',1,LEVEL)                                                                    ");
		advQueryFragment.append("			  FROM                                                                                                        ");
		advQueryFragment.append("				dual                                                                                                      ");
		advQueryFragment.append("				CONNECT BY regexp_substr(classes,'[^,]+',1,LEVEL) IS NOT NULL                                             ");
		advQueryFragment.append("			)                                                                                                             ");
		advQueryFragment.append("	   ),                                                                                                                 ");
        advQueryFragment.append("     styleID_DescAA AS(                                                                                                           ");
        advQueryFragment.append("       SELECT                                                                                                                     ");
        advQueryFragment.append("         sda.PARENT_MDMID,                                                                                                        ");
        advQueryFragment.append("         sda.MDMID,                                                                                                               ");
        advQueryFragment.append("         sda.XML_DATA,                                                                                                            ");
        advQueryFragment.append("         sda.ENTRY_TYPE,                                                                                                          ");
        advQueryFragment.append("         sda.PRIMARY_UPC,                                                                                                         ");
        advQueryFragment.append("         sda.Supplier_Id,                                                                                                         ");
        advQueryFragment.append("         sda.DEPT_ID,                                                                                                             ");
        advQueryFragment.append("         p.PRODUCT_NAME,                                                                                                          ");
        advQueryFragment.append("         sda.ven_style,                                                                                                           ");
        advQueryFragment.append("         sda.clsId,                                                                                                               ");
        advQueryFragment.append("         sda.INPUTPETSTATUS,                                                                                                      ");
        advQueryFragment.append("         sda.VSTYLE,                                                                                                              ");
        advQueryFragment.append("         sda.classes,                                                                                                             ");
        advQueryFragment.append("         sda.CompletionFrom,sda.CompletionTo,sda.inpImageStatus,sda.inpContentStatus,sda.RequestType,sda.createdToday,            ");
        advQueryFragment.append("         p.completion_date,                                                                                                       ");
        advQueryFragment.append("         p.req_type,                                                                                                              ");
        advQueryFragment.append("         pet.pet_state PETSTATUS,                                                                                                 ");
        advQueryFragment.append("         pet.image_status ImageState,                                                                                             ");
        advQueryFragment.append("         pet.content_status CONTENTSTATUS,                                                                                        ");
        advQueryFragment.append("         pet.PET_EARLIEST_COMP_DATE, p.PRODUCT_NAME_COMPLEX                                                                       ");
        advQueryFragment.append("   FROM                                                                                                                           ");
        advQueryFragment.append("     styleID_DescA sda,                                                                                                           ");
        advQueryFragment.append("     ADSE_PET_CATALOG pet,                                                                                                        ");
        advQueryFragment.append("     XMLTABLE(                                                                                                                    ");
        advQueryFragment.append("     'let                                                                                                                         ");
        advQueryFragment.append("     $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date                                                        ");
        advQueryFragment.append("     return                                                                                                                       ");
        advQueryFragment.append("     <out>                                                                                                                        ");
        advQueryFragment.append("         <completion_date>{$completionDate}</completion_date>                                                                     ");
        advQueryFragment.append("         <req_type>{$pets/pim_entry/entry/Pet_Ctg_Spec/SourceSystem}</req_type>                                                   ");
        advQueryFragment.append("         <PRODUCT_NAME>{$pets/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>                                       ");
        advQueryFragment.append("         <PRODUCT_NAME_COMPLEX>{$pets/pim_entry/entry/Ecomm_ComplexPack_Spec/Product_Name}</PRODUCT_NAME_COMPLEX>                 ");
        advQueryFragment.append("     </out>'                                                                                                                      ");
        advQueryFragment.append("     passing pet.xml_data AS \"pets\" Columns                                                                                       ");
        advQueryFragment.append("         completion_date      VARCHAR2(10) path  '/out/completion_date',                                                          ");
        advQueryFragment.append("         req_type             VARCHAR2(20) path '/out/req_type',                                                                  ");
        advQueryFragment.append("         PRODUCT_NAME         VARCHAR2(40) path '/out/PRODUCT_NAME',                                                              ");
        advQueryFragment.append("         PRODUCT_NAME_COMPLEX         VARCHAR2(40) path '/out/PRODUCT_NAME_COMPLEX'                                               ");
        advQueryFragment.append("     )p                                                                                                                           ");
        advQueryFragment.append("   WHERE                                                                                                                          ");
        advQueryFragment.append("     sda.MDMID     =pet.mdmid                                                                                                     ");
        advQueryFragment.append("   AND                                                                                                                            ");
        advQueryFragment.append("     (                                                                                                                            ");
        advQueryFragment.append("       CompletionFrom IS NULL                                                                                                     ");
        advQueryFragment.append("     OR (PET_EARLIEST_COMP_DATE >=TO_DATE(CompletionFrom, 'YYYY-MM-DD') AND sda.ENTRY_TYPE IN ('Style','Complex Pack'))  OR (LAST_PO_DATE >=TO_DATE(CompletionFrom, 'YYYY-MM-DD') AND sda.ENTRY_TYPE IN ('StyleColor','PackColor'))                                                          ");
        advQueryFragment.append("     )                                                                                                                            ");
        advQueryFragment.append("   AND                                                                                                                            ");
        advQueryFragment.append("     (                                                                                                                            ");
        advQueryFragment.append("       CompletionTo IS NULL                                                                                                       ");
        advQueryFragment.append("     OR (PET_EARLIEST_COMP_DATE <=TO_DATE(CompletionTo, 'YYYY-MM-DD')  AND sda.ENTRY_TYPE IN ('Style','Complex Pack'))  OR (LAST_PO_DATE <=TO_DATE(CompletionTo, 'YYYY-MM-DD') AND sda.ENTRY_TYPE IN ('StyleColor','PackColor'))                                                              ");
        advQueryFragment.append("     )                                                                                                                            ");
        advQueryFragment.append("   AND                                                                                                                            ");
        advQueryFragment.append("     (                                                                                                                            ");
        advQueryFragment.append("       inpImageStatus IS NULL                                                                                                     ");
        advQueryFragment.append("     OR pet.image_status   IN                                                                                                     ");
        advQueryFragment.append("       (                                                                                                                          ");
        advQueryFragment.append("         SELECT                                                                                                                   ");
        advQueryFragment.append("           regexp_substr(inpImageStatus,'[^,]+',1,LEVEL)                                                                          ");
        advQueryFragment.append("         FROM                                                                                                                     ");
        advQueryFragment.append("           dual                                                                                                                   ");
        advQueryFragment.append("           CONNECT BY regexp_substr(inpImageStatus,'[^,]+',1,LEVEL) IS NOT NULL                                                   ");
        advQueryFragment.append("       )                                                                                                                          ");
        advQueryFragment.append("     )                                                                                                                            ");
        advQueryFragment.append("   AND                                                                                                                            ");
        advQueryFragment.append("     (                                                                                                                            ");
        advQueryFragment.append("       inpContentStatus IS NULL                                                                                                   ");
        advQueryFragment.append("     OR pet.content_status    IN                                                                                                  ");
        advQueryFragment.append("       (                                                                                                                          ");
        advQueryFragment.append("         SELECT                                                                                                                   ");
        advQueryFragment.append("           regexp_substr(inpContentStatus,'[^,]+',1,LEVEL)                                                                        ");
        advQueryFragment.append("         FROM                                                                                                                     ");
        advQueryFragment.append("           dual                                                                                                                   ");
        advQueryFragment.append("           CONNECT BY regexp_substr(inpContentStatus,'[^,]+',1,LEVEL) IS NOT NULL                                                 ");
        advQueryFragment.append("       )                                                                                                                          ");
        advQueryFragment.append("     )                                                                                                                            ");
        advQueryFragment.append("   AND                                                                                                                            ");
        advQueryFragment.append("     (                                                                                                                            ");
        advQueryFragment.append("       RequestType IS NULL                                                                                                        ");
        advQueryFragment.append("     OR Req_Type       IN                                                                                                         ");
        advQueryFragment.append("       (                                                                                                                          ");
        advQueryFragment.append("         SELECT                                                                                                                   ");
        advQueryFragment.append("           regexp_substr(RequestType,'[^,]+',1,LEVEL)                                                                             ");
        advQueryFragment.append("         FROM                                                                                                                     ");
        advQueryFragment.append("           dual                                                                                                                   ");
        advQueryFragment.append("           CONNECT BY regexp_substr(RequestType,'[^,]+',1,LEVEL) IS NOT NULL                                                      ");
        advQueryFragment.append("       )                                                                                                                          ");
        advQueryFragment.append("     )                                                                                                                            ");
        advQueryFragment.append("   AND                                                                                                                            ");
        advQueryFragment.append("     (                                                                                                                            ");
        advQueryFragment.append("       createdToday                           IS NULL                                                                             ");
        advQueryFragment.append("     OR createdToday                          <>'yes'                                                                             ");
        advQueryFragment.append("     OR to_date(SUBSTR(pet.CREATED_DTM, 1, 9), 'YYYY-MM-DD') = to_date(SUBSTR(CURRENT_TIMESTAMP, 1, 9), 'YYYY-MM-DD')             ");
        advQueryFragment.append("     )                                                                                                                            ");
        advQueryFragment.append("   ),                                                                                                                             ");
        advQueryFragment.append("   supplierDetails AS                                                                                                             ");
        advQueryFragment.append("     (                                                                                                                            ");
        advQueryFragment.append("       SELECT                                                                                                                     ");
        advQueryFragment.append("         orin.PARENT_MDMID,                                                                                                       ");
        advQueryFragment.append("         orin.MDMID,                                                                                                              ");
        advQueryFragment.append("         orin.ENTRY_TYPE,                                                                                                         ");
        advQueryFragment.append("         orin.DEPT_Id,                                                                                                            ");
        advQueryFragment.append("         orin.PRODUCT_NAME,                                                                                                       ");
        advQueryFragment.append("         orin.ven_style,                                                                                                          ");
        advQueryFragment.append("         orin.Supplier_Id,                                                                                                        ");
        advQueryFragment.append("         orin.PRIMARY_UPC,                                                                                                        ");
        advQueryFragment.append("         orin.clsId,                                                                                                              ");
        advQueryFragment.append("         orin.INPUTPETSTATUS,                                                                                                     ");
        advQueryFragment.append("         orin.VSTYLE,                                                                                                             ");
        advQueryFragment.append("         orin.classes,                                                                                                            ");
        advQueryFragment.append("         orin.completion_date,                                                                                                    ");
        advQueryFragment.append("         orin.req_type,                                                                                                           ");
        advQueryFragment.append("         orin.ImageState,                                                                                                         ");
        advQueryFragment.append("         orin.CONTENTSTATUS,                                                                                                      ");
        advQueryFragment.append("         orin.PETSTATUS,                                                                                                          ");
        advQueryFragment.append("         s.ven_name,                                                                                                              ");
        advQueryFragment.append("         s.omniChannelIndicator,                                                                                                  ");
        advQueryFragment.append("         orin.PET_EARLIEST_COMP_DATE, orin.PRODUCT_NAME_COMPLEX                                                                   ");
        advQueryFragment.append("       FROM                                                                                                                       ");
        advQueryFragment.append("         styleID_DescAA orin,                                                                                                     ");
        advQueryFragment.append("         ADSE_SUPPLIER_CATALOG supplier,                                                                                          ");
        advQueryFragment.append("         XMLTABLE(                                                                                                                ");
        advQueryFragment.append("         'let                                                                                                                     ");
        advQueryFragment.append("         $ven_name := $supplierCatalog/pim_entry/entry/Supplier_Ctg_Spec/Name,                                                    ");
        advQueryFragment.append("         $isOmniChannel := $supplierCatalog/pim_entry/entry/Supplier_Site_Spec/Omni_Channel                                       ");
        advQueryFragment.append("         return                                                                                                                   ");
        advQueryFragment.append("         <out>                                                                                                                    ");
        advQueryFragment.append("             <ven_name>{$ven_name}</ven_name>                                                                                     ");
        advQueryFragment.append("             <omni_channel>{if($isOmniChannel eq \"true\") then \"Y\" else \"N\"}</omni_channel>                                        ");
        advQueryFragment.append("         </out>'                                                                                                                  ");
        advQueryFragment.append("         PASSING XMLELEMENT(\"Id\", orin.Supplier_Id) AS \"Suppliers\",                                                               ");
        advQueryFragment.append("         supplier.XML_DATA                          AS \"supplierCatalog\" columns                                                  ");
        advQueryFragment.append("         ven_name                          VARCHAR2(80) path '/out/ven_name',                                                     ");
        advQueryFragment.append("         omniChannelIndicator VARCHAR2      (2) path '/out/omni_channel') s                                                       ");
        advQueryFragment.append("       WHERE                                                                                                                      ");
        advQueryFragment.append("       orin.Supplier_Id = supplier.mdmid                                                                                          ");
        advQueryFragment.append("      ),                                                                                                                          ");
        advQueryFragment.append("     styleID_Desc AS(                                                                                                             ");
        advQueryFragment.append("       SELECT                                                                                                                     ");
        advQueryFragment.append("         sia.PARENT_MDMID,                                                                                                        ");
        advQueryFragment.append("         sia.MDMID ORIN_NUM,                                                                                                      ");
        advQueryFragment.append("         sia.ENTRY_TYPE,                                                                                                          ");
        advQueryFragment.append("         sia.Supplier_Id,                                                                                                         ");
        advQueryFragment.append("         sia.DEPT_ID,                                                                                                             ");
        advQueryFragment.append("         sia.PRODUCT_NAME,                                                                                                        ");
        advQueryFragment.append("         sia.ven_style,                                                                                                           ");
        advQueryFragment.append("         sia.PRIMARY_UPC,                                                                                                         ");
        advQueryFragment.append("         sia.clsId,                                                                                                               ");
        advQueryFragment.append("         sia.INPUTPETSTATUS,                                                                                                      ");
        advQueryFragment.append("         sia.VSTYLE,                                                                                                              ");
        advQueryFragment.append("         sia.classes,                                                                                                             ");
        advQueryFragment.append("         sia.completion_date,                                                                                                     ");
        advQueryFragment.append("         sia.req_type,                                                                                                            ");
        advQueryFragment.append("         sia.ImageState,                                                                                                          ");
        advQueryFragment.append("         sia.CONTENTSTATUS,                                                                                                       ");
        advQueryFragment.append("         sia.PETSTATUS,                                                                                                           ");
        advQueryFragment.append("         sia.ven_name,                                                                                                            ");
        advQueryFragment.append("         sia.omniChannelIndicator,                                                                                                ");
        advQueryFragment.append("         sia.PET_EARLIEST_COMP_DATE, sia.PRODUCT_NAME_COMPLEX                                                                     ");
        advQueryFragment.append("       from supplierDetails sia                                                                                                   ");
        advQueryFragment.append("       where                                                                                                                      ");
        advQueryFragment.append("           (sia.Entry_Type IN ('Style','Complex Pack')                                                                            ");
        advQueryFragment.append("           AND (INPUTPETSTATUS IS NULL OR sia.PETSTATUS = INPUTPETSTATUS) AND (VSTYLE IS NULL OR VEN_STYLE = VSTYLE))             ");
        advQueryFragment.append("         OR                                                                                                                       ");
        advQueryFragment.append("           (entry_type IN ('StyleColor','PackColor')                                                                              ");
        advQueryFragment.append("           AND PARENT_MDMID IN                                                                                                    ");
        advQueryFragment.append("               (SELECT MDMID   FROM supplierDetails WHERE ENTRY_TYPE IN ('Style','Complex Pack') AND                              ");
        advQueryFragment.append("                 (INPUTPETSTATUS IS NULL OR PETSTATUS = INPUTPETSTATUS) AND (VSTYLE IS NULL OR VEN_STYLE = VSTYLE)                ");
        advQueryFragment.append("               )                                                                                                                  ");
        advQueryFragment.append("           AND (INPUTPETSTATUS IS NULL OR PETSTATUS = INPUTPETSTATUS))                                                            ");
        advQueryFragment.append("     )                                                                                                                            ");
        advQueryFragment.append("     SELECT                                                                                                                       ");
        advQueryFragment.append("        CASE                                                                                                                      ");
        advQueryFragment.append("         WHEN LENGTH(ORIN_NUM) > 9                                                                                                ");
        advQueryFragment.append("           THEN SUBSTR(ORIN_NUM,1,9)                                                                                              ");
        advQueryFragment.append("           ELSE ORIN_NUM                                                                                                          ");
        advQueryFragment.append("         END Parent_Style_Orin,                                                                                                   ");
        advQueryFragment.append("         ORIN_NUM,                                                                                                                ");
        advQueryFragment.append("         DEPT_ID,                                                                                                                 ");
        advQueryFragment.append("         Supplier_Id,                                                                                                             ");
        advQueryFragment.append("         PRODUCT_NAME,                                                                                                            ");
        advQueryFragment.append("         ENTRY_TYPE,                                                                                                              ");
        advQueryFragment.append("         PRIMARY_UPC,                                                                                                             ");
        advQueryFragment.append("         VEN_NAME,                                                                                                                ");
        advQueryFragment.append("         VEN_STYLE,                                                                                                               ");
        advQueryFragment.append("         clsId,                                                                                                                   ");
        advQueryFragment.append("         PETSTATUS,                                                                                                               ");
        advQueryFragment.append("         ImageState,                                                                                                              ");
        advQueryFragment.append("         CONTENTSTATUS,                                                                                                           ");
        advQueryFragment.append("         completion_date,                                                                                                         ");
        advQueryFragment.append("         omniChannelIndicator,                                                                                                    ");
        advQueryFragment.append("         req_type,                                                                                                                ");
        advQueryFragment.append("         PET_EARLIEST_COMP_DATE, PRODUCT_NAME_COMPLEX                                                                             ");
        advQueryFragment.append("     FROM styleID_Desc                                                                                                            ");
        advQueryFragment.append("     WHERE                                                                                                                        ");
        advQueryFragment.append("         (                                                                                                                        ");
        advQueryFragment.append("           classes IS NULL                                                                                                        ");
        advQueryFragment.append("          OR clsId  IN                                                                                                            ");
        advQueryFragment.append("           (                                                                                                                      ");
        advQueryFragment.append("             SELECT                                                                                                               ");
        advQueryFragment.append("               regexp_substr(classes,'[^,]+',1,LEVEL)                                                                             ");
        advQueryFragment.append("             FROM                                                                                                                 ");
        advQueryFragment.append("               dual                                                                                                               ");
        advQueryFragment.append("               CONNECT BY regexp_substr(classes,'[^,]+',1,LEVEL) IS NOT NULL                                                      ");
        advQueryFragment.append("           )                                                                                                                      ");
        advQueryFragment.append("         )                                                                                                                        ");

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

}
