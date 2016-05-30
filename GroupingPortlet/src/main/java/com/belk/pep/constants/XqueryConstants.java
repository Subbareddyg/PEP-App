package com.belk.pep.constants;

//import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.belk.pep.dto.GroupSearchDTO;
import com.belk.pep.form.GroupSearchForm;


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
     * This method is used to build the Query to retrieve Group Header Details from Database against Group Id.
     * @return
     */
    public  String getGroupHeaderDetails() {          
        final String getGroupHeaderDetailsQuery=
                "  SELECT                                                                                "
                +"      AGC.MDMID GROUP_ID,                                                               "
                +"      AGC.GROUP_NAME,                                                                   "           
                +"      AGCXML.Description,                                                               "             
                +"      AGCXML.Effective_Start_Date,                                                      "             
                +"      AGCXML.Effective_End_Date,                                                        "               
                +"      AGCXML.CARS_Group_Type,                                                      "            
                +"      AGC.ENTRY_TYPE GROUP_TYPE, AGC.GROUP_OVERALL_STATUS_CODE,                          "                        
                +"      AGC.CREATED_BY                                                                    "      
                +"        /*XML_DATA*/                                                                      "             
                +"      FROM ADSE_GROUP_CATALOG AGC,                                                      "             
                +"        XMLTABLE( 'let                                                                  "             
                +"        $Description:= /pim_entry/entry/Group_Ctg_Spec/Description,                     "                   
                +"        $Effective_Start_Date:= /pim_entry/entry/Group_Ctg_Spec/Effective_Start_Date,   "    
                +"        $Effective_End_Date:= /pim_entry/entry/Group_Ctg_Spec/Effective_End_Date,        "    
                +"        $CARS_Group_Type:= /pim_entry/entry/Group_Ctg_Spec/CARS_Group_Type        "
                +"        return                                                                          "                                      
                +"      <out>                                                                             "             
                +"          <Description>{$Description}</Description>                                     "             
                +"          <Effective_Start_Date>{$Effective_Start_Date}</Effective_Start_Date>          "             
                +"          <Effective_End_Date>{$Effective_End_Date}</Effective_End_Date>                "             
                +"          <CARS_Group_Type>{$CARS_Group_Type}</CARS_Group_Type>                "             
                +"      </out>' passing AGC.XML_DATA                                                      "
                +"        Columns                                                                         "             
                //+"        Description VARCHAR2(50) path '/out/Description',                               "             
                +"        Description CLOB path '/out/Description',                               "
                +"        Effective_Start_Date VARCHAR2(50) path '/out/Effective_Start_Date' ,            "             
                +"        Effective_End_Date VARCHAR2(50) path '/out/Effective_End_Date',                 "              
                +"        CARS_Group_Type VARCHAR2(50) path '/out/CARS_Group_Type') AGCXML          "             
                +"                                                                                        "             
                +"      WHERE                                                                             "             
                +"      MDMID = :groupIdSql AND DELETED_FLAG = 'false'                                     ";


        return getGroupHeaderDetailsQuery;             
    }



    /**
     * This method is used to build the Query to retrieve Split Color details to create Split Color Group.
     * @return
     */
    public  String getSplitColorDetails(String vendorStyleNo) {          
        String getSplitColorDetailsQuery=
                "   Select                                                                                              "
                        +"  ITEM.MDMID, ITEM.PARENT_MDMID,                                                                      "
                        +"  ITEM.PRIMARYSUPPLIERVPN,                                                                            "
                        +"  PET_XML.PRODUCT_NAME,                                                                               "
                        +"  PET_XML.COLOR_CODE,                                                                                 "
                        +"  PET_XML.COLOR_DESC,                                                                                 "
                        +"  CASE                                                                                                "
                        +"      WHEN MAPPING.COMPONENT_GROUPING_ID is NULL                                                                "
                        +"      THEN 'N'                                                                                        "
                        +"      ELSE 'Y' END ALREADY_IN_GROUP, PET.PET_STATE, PET.ENTRY_TYPE                                    "
                        +"  FROM                                                                                                "
                        +"  ADSE_ITEM_CATALOG ITEM,                                                                             "
                        +"  ADSE_PET_CATALOG PET                                                                                "
                        +"      LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING MAPPING                                                            "
                        +"      ON MAPPING.COMPONENT_STYLECOLOR_ID = PET.MDMID AND PET.ENTRY_TYPE =  MAPPING.COMPONENT_TYPE,    "
                        +"  XMLTABLE('let                                                                                       "
                        +"      $colordesc:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description,                      "
                        +"      $colorCode:= /pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Code                              "
                        +"  return                                                                                              "
                        +"  <COLOR>                                                                                             "
                        +"      <COLOR_CODE>{$colorCode}</COLOR_CODE>                                                           "
                        +"      <COLOR_DESC>{$colordesc}</COLOR_DESC>                                                           "
                        +"      <PRODUCT_NAME>{/pim_entry/entry/Ecomm_Style_Spec/Product_Name}</PRODUCT_NAME>                   "
                        +"  </COLOR>'                                                                                           "
                        +"  passing Pet.XML_DATA                                                                                "
                        +"  Columns                                                                                             "
                        +"      COLOR_CODE VARCHAR2(5) path '/COLOR/COLOR_CODE',                                                "
                        +"      COLOR_DESC VARCHAR2(20) path '/COLOR/COLOR_DESC',                                               "
                        +"      PRODUCT_NAME VARCHAR2(20) path '/COLOR/PRODUCT_NAME'                                            "
                        +"      ) PET_XML                                                                                       "
                        +"  WHERE                                                                                               "
                        +"  ITEM.ENTRY_TYPE in ('Style', 'StyleColor')                                                          "
                        +"  AND ITEM.DELETED_FLAG= 'false'                                                                      "
                        +"   AND PET.MDMID=ITEM.MDMID                                                                           ";
        if(vendorStyleNo != null){
            getSplitColorDetailsQuery = getSplitColorDetailsQuery + " AND item.PRIMARYSUPPLIERVPN = :styleIdSql";
        }else{
            getSplitColorDetailsQuery = getSplitColorDetailsQuery + " AND ITEM.MDMID = :mdmidSql";
        }

        return getSplitColorDetailsQuery;             
    }



    /**
     * This method is used to build the Query to retrieve Split SKU details to create Split SKU Group.
     * @return
     */
    public  String getSplitSKUDetails(String vendorStyleNo) {          
        String getSplitSKUDetailsQuery=
                "   Select                                                                                              "
                        +"  ITEM.MDMID,                                                                                         "
                        +"  ITEM.PRIMARYSUPPLIERVPN,                                                                            "
                        +"  ITEM_XML.NAME,                                                                                      "
                        +"  ITEM_XML.COLOR_CODE,                                                                                "
                        +"  ITEM_XML.COLOR_NAME,                                                                                "
                        +"  ITEM_XML.SIZEDESC,                                                                                  "
                        +"  CASE                                                                                                "
                        +"      WHEN MAPPING.COMPONENT_GROUPING_ID is NULL                                                                "
                        +"      THEN 'N'                                                                                        "
                        +"      ELSE 'Y' END ALREADY_IN_GROUP, PET.PET_STATE, PET.ENTRY_TYPE                                    "
                        +"  FROM                                                                                                "
                        +"  ADSE_ITEM_CATALOG ITEM,                                                                             "
                        +"  ADSE_PET_CATALOG PET                                                                                "
                        +"      LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING MAPPING                                                            "
                        +"      ON MAPPING.COMPONENT_SKU_ID = PET.MDMID AND PET.ENTRY_TYPE =  MAPPING.COMPONENT_TYPE,           "
                        +"  XMLTABLE('let                                                                                       "
                        +"  $colorcode:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code,                   "
                        +"  $colorname:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description,     "
                        +"  $size:=/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description,           "
                        +"  $name:=/pim_entry/entry/Item_Ctg_Spec/Description/Short                                             "
                        +"  return                                                                                              "
                        +"      <SPEC>                                                                                          "
                        +"          <COLOR_CODE>{$colorcode}</COLOR_CODE>                                                       "
                        +"          <COLOR_NAME>{$colorname}</COLOR_NAME>                                                       "
                        +"          <SIZE>{$size}</SIZE>                                                                        "
                        +"          <NAME>{$name}</NAME>                                                                        "
                        +"      </SPEC>'                                                                                        "
                        +"  passing ITEM.XML_DATA                                                                               "
                        +"  Columns                                                                                             "
                        +"      COLOR_CODE VARCHAR2(5) path '/SPEC/COLOR_CODE',                                                 "
                        +"      COLOR_NAME VARCHAR2(20) path '/SPEC/COLOR_NAME',                                                "
                        +"      SIZEDESC VARCHAR2(20) path '/SPEC/SIZE',                                                        "
                        +"      NAME VARCHAR2(50) path '/SPEC/NAME') ITEM_XML                                                   "
                        +"  WHERE                                                                                               "
                        +"      ITEM.ENTRY_TYPE in ('Style', 'StyleColor', 'SKU')                                               "
                        +"      AND ITEM.DELETED_FLAG= 'false'                                                                  "
                        +"      AND PET.MDMID=ITEM.MDMID                                                                        ";
        if(vendorStyleNo != null){
            getSplitSKUDetailsQuery = getSplitSKUDetailsQuery + " AND item.PRIMARYSUPPLIERVPN = :styleIdSql";
        }else{
            getSplitSKUDetailsQuery = getSplitSKUDetailsQuery + " AND ITEM.MDMID = :mdmidSql";
        }

        return getSplitSKUDetailsQuery;             
    }

    /**
     * Method to get the Search Group query.
     *    
     * @return the query string
     * 
     * Method added For PIM Phase 2 - Search Group
     * Date: 05/19/2016
     * Added By: Cognizant
     */
    public  String getGroupDetailsQuery(GroupSearchForm groupSearchForm) { 
        
        LOGGER.info("Entering getGroupDetailsQuery() in Grouping XQueryConstant class.");
        
        StringBuffer getGroupDetailsQuery = new StringBuffer();
        getGroupDetailsQuery.append(" SELECT MDMID GROUP_ID, GROUP_NAME, ENTRY_TYPE GROUP_TYPE, GROUP_CONTENT_STATUS_CODE, START_DATE START_DATE, END_DATE END_DATE, GROUP_IMAGE_STATUS_CODE FROM ADSE_GROUP_CATALOG   ");        
        getGroupDetailsQuery.append(" WHERE                                                                                          ");
        getGroupDetailsQuery.append(" DELETED_FLAG = 'false'                                                                       ");
        if(groupSearchForm.getGroupId() != null && !groupSearchForm.getGroupId().trim().equals("")){
            getGroupDetailsQuery.append(" AND MDMID = '");
            getGroupDetailsQuery.append(groupSearchForm.getGroupId());
            getGroupDetailsQuery.append("'");
        }
        if(groupSearchForm.getGroupName() != null && !groupSearchForm.getGroupName().trim().equals("")){
            getGroupDetailsQuery.append(" AND UPPER(GROUP_NAME) LIKE '%");
            getGroupDetailsQuery.append(groupSearchForm.getGroupName().toUpperCase());
            getGroupDetailsQuery.append("%'");
        } 
        if((groupSearchForm.getVendor() != null && !groupSearchForm.getVendor().trim().equals(""))
                || (groupSearchForm.getDepts() != null && !groupSearchForm.getDepts().trim().equals(""))
                || (groupSearchForm.getClasses() != null && !groupSearchForm.getClasses().trim().equals(""))
                || (groupSearchForm.getOrinNumber() != null && !groupSearchForm.getOrinNumber().trim().equals(""))
                || (groupSearchForm.getSupplierSiteId() != null && !groupSearchForm.getSupplierSiteId().trim().equals(""))){
            
            getGroupDetailsQuery.append(" AND MDMID IN ( ");
            getGroupDetailsQuery.append(" SELECT GCM.MDMID FROM ADSE_GROUP_CHILD_MAPPING GCM, ");
            getGroupDetailsQuery.append(" (SELECT MDMID FROM ADSE_ITEM_CATALOG ");
            getGroupDetailsQuery.append(" WHERE DELETED_FLAG = 'false' AND ENTRY_TYPE = 'Style' ");
            if(groupSearchForm.getDepts() != null && !groupSearchForm.getDepts().trim().equals(""))
            {
                getGroupDetailsQuery.append(" AND DEPT_ID IN (");                
                getGroupDetailsQuery.append(getNumbersInCorrectFormat(groupSearchForm.getDepts()));
                getGroupDetailsQuery.append(")");
            }
            if(groupSearchForm.getClasses() != null && !groupSearchForm.getClasses().trim().equals(""))
            {
                getGroupDetailsQuery.append(" AND CLASS_ID IN (");
                getGroupDetailsQuery.append(getNumbersInCorrectFormat(groupSearchForm.getClasses()));
                getGroupDetailsQuery.append(")");
            }
            if(groupSearchForm.getOrinNumber() != null && !groupSearchForm.getOrinNumber().trim().equals(""))
            {
                getGroupDetailsQuery.append(" AND MDMID IN ('");
                getGroupDetailsQuery.append(groupSearchForm.getOrinNumber());
                getGroupDetailsQuery.append("')");
            }
            if(groupSearchForm.getSupplierSiteId() != null && !groupSearchForm.getSupplierSiteId().trim().equals(""))
            {
                getGroupDetailsQuery.append(" AND PRIMARY_SUPPLIER_ID IN ('");
                getGroupDetailsQuery.append(groupSearchForm.getSupplierSiteId());
                getGroupDetailsQuery.append("')");
            }
            if(groupSearchForm.getVendor() != null && !groupSearchForm.getVendor().trim().equals(""))
            {
                getGroupDetailsQuery.append(" AND PRIMARYSUPPLIERVPN IN ('");
                getGroupDetailsQuery.append(groupSearchForm.getVendor());
                getGroupDetailsQuery.append("')");
            }
            getGroupDetailsQuery.append(" ) T1 WHERE ");
            getGroupDetailsQuery.append("(GCM.COMPONENT_STYLE_ID = T1.MDMID)  )");
            getGroupDetailsQuery.append(" ORDER BY ENTRY_TYPE, MDMID ");
        }                       

        LOGGER.debug("SEARCH GROUP QUERY -- \n" + getGroupDetailsQuery.toString());
        //System.out.println("getGroupDetailsQuery--" + getGroupDetailsQuery.toString());
        LOGGER.info("Exiting getGroupDetailsQuery() in Grouping XQueryConstant class.");
        return getGroupDetailsQuery.toString();             
    }
    
    /**
     * Method to get the Search Group Result Count query.
     *    
     * @return the query string
     * 
     * Method added For PIM Phase 2 - Search Group
     * Date: 05/19/2016
     * Added By: Cognizant
     */
    public  String getGroupDetailsCountQuery(GroupSearchForm groupSearchForm) { 
        
        LOGGER.info("Entering getGroupDetailsCountQuery() in Grouping XQueryConstant class.");
        
        StringBuffer getGroupDetailsCountQuery = new StringBuffer();
        getGroupDetailsCountQuery.append(" SELECT COUNT(*) TOTAL_COUNT FROM ADSE_GROUP_CATALOG   ");        
        getGroupDetailsCountQuery.append(" WHERE                                                                                          ");
        getGroupDetailsCountQuery.append(" DELETED_FLAG = 'false'                                                                       ");
        if(groupSearchForm.getGroupId() != null && !groupSearchForm.getGroupId().trim().equals("")){
            getGroupDetailsCountQuery.append(" AND MDMID = '");
            getGroupDetailsCountQuery.append(groupSearchForm.getGroupId());
            getGroupDetailsCountQuery.append("'");
        }
        if(groupSearchForm.getGroupName() != null && !groupSearchForm.getGroupName().trim().equals("")){
            getGroupDetailsCountQuery.append(" AND UPPER(GROUP_NAME) LIKE '%");
            getGroupDetailsCountQuery.append(groupSearchForm.getGroupName().toUpperCase());
            getGroupDetailsCountQuery.append("%'");
        } 
        if((groupSearchForm.getVendor() != null && !groupSearchForm.getVendor().trim().equals(""))
                || (groupSearchForm.getDepts() != null && !groupSearchForm.getDepts().trim().equals(""))
                || (groupSearchForm.getClasses() != null && !groupSearchForm.getClasses().trim().equals(""))
                || (groupSearchForm.getOrinNumber() != null && !groupSearchForm.getOrinNumber().trim().equals(""))
                || (groupSearchForm.getSupplierSiteId() != null && !groupSearchForm.getSupplierSiteId().trim().equals(""))){
            
            getGroupDetailsCountQuery.append(" AND MDMID IN ( ");
            getGroupDetailsCountQuery.append(" SELECT GCM.MDMID FROM ADSE_GROUP_CHILD_MAPPING GCM, ");
            getGroupDetailsCountQuery.append(" (SELECT MDMID FROM ADSE_ITEM_CATALOG ");
            getGroupDetailsCountQuery.append(" WHERE DELETED_FLAG = 'false' AND ENTRY_TYPE = 'Style' ");
            if(groupSearchForm.getDepts() != null && !groupSearchForm.getDepts().trim().equals(""))
            {
                getGroupDetailsCountQuery.append(" AND DEPT_ID IN (");                
                getGroupDetailsCountQuery.append(getNumbersInCorrectFormat(groupSearchForm.getDepts()));
                getGroupDetailsCountQuery.append(")");
            }
            if(groupSearchForm.getClasses() != null && !groupSearchForm.getClasses().trim().equals(""))
            {
                getGroupDetailsCountQuery.append(" AND CLASS_ID IN (");
                getGroupDetailsCountQuery.append(getNumbersInCorrectFormat(groupSearchForm.getClasses()));
                getGroupDetailsCountQuery.append(")");
            }
            if(groupSearchForm.getOrinNumber() != null && !groupSearchForm.getOrinNumber().trim().equals(""))
            {
                getGroupDetailsCountQuery.append(" AND MDMID IN ('");
                getGroupDetailsCountQuery.append(groupSearchForm.getOrinNumber());
                getGroupDetailsCountQuery.append("')");
            }
            if(groupSearchForm.getSupplierSiteId() != null && !groupSearchForm.getSupplierSiteId().trim().equals(""))
            {
                getGroupDetailsCountQuery.append(" AND PRIMARY_SUPPLIER_ID IN ('");
                getGroupDetailsCountQuery.append(groupSearchForm.getSupplierSiteId());
                getGroupDetailsCountQuery.append("')");
            }
            if(groupSearchForm.getVendor() != null && !groupSearchForm.getVendor().trim().equals(""))
            {
                getGroupDetailsCountQuery.append(" AND PRIMARYSUPPLIERVPN IN ('");
                getGroupDetailsCountQuery.append(groupSearchForm.getVendor());
                getGroupDetailsCountQuery.append("')");
            }
            getGroupDetailsCountQuery.append(" ) T1 WHERE ");
            getGroupDetailsCountQuery.append("(GCM.COMPONENT_STYLE_ID = T1.MDMID)  )");            
        }                       

        LOGGER.debug("SEARCH GROUP COUNT QUERY -- \n" + getGroupDetailsCountQuery.toString());
        //System.out.println("getGroupDetailsCountQuery--" + getGroupDetailsCountQuery.toString());
        LOGGER.info("Exiting getGroupDetailsCountQuery() in Grouping XQueryConstant class.");
        return getGroupDetailsCountQuery.toString();             
    }
    
    /**
     * Method to get the Search Group Parent query.
     *    
     * @return the query string
     * 
     * Method added For PIM Phase 2 - Search Group
     * Date: 05/19/2016
     * Added By: Cognizant
     */
    public  String getGroupDetailsQueryParent(List<GroupSearchDTO> groupSearchDTOList) { 
        
        LOGGER.info("Entering getGroupDetailsQueryParent() in Grouping XQueryConstant class.");
        
        StringBuffer getGroupDetailsQueryParent = new StringBuffer();
        getGroupDetailsQueryParent.append(" SELECT AGC.MDMID GROUP_ID, AGC.GROUP_NAME GROUP_NAME, AGC.ENTRY_TYPE GROUP_TYPE, AGC.GROUP_CONTENT_STATUS_CODE GROUP_CONTENT_STATUS_CODE, START_DATE START_DATE, END_DATE END_DATE, GCM.COMPONENT_GROUPING_ID COMPONENT_GROUPING_ID, AGC.GROUP_IMAGE_STATUS_CODE GROUP_IMAGE_STATUS_CODE  ");
        getGroupDetailsQueryParent.append(" FROM ADSE_GROUP_CATALOG AGC, ADSE_GROUP_CHILD_MAPPING GCM                                                                                        ");       
        getGroupDetailsQueryParent.append(" WHERE DELETED_FLAG = 'false'                                                                                                               ");
        getGroupDetailsQueryParent.append(" AND AGC.MDMID = GCM.MDMID                                                                                                              ");        
        List<String> groupIdsList = getCommaSeparatedValues(groupSearchDTOList);
        if(groupIdsList.size() > 0)
        {
            int count = 0;
            for (Iterator<String> iterator = groupIdsList.iterator(); iterator.hasNext();) {
                String strGroupIds = (String) iterator.next();
                if(count == 0)
                {
                    getGroupDetailsQueryParent.append(" AND (GCM.COMPONENT_GROUPING_ID IN ("+  strGroupIds + ")");
                }
                else
                {
                    getGroupDetailsQueryParent.append(" OR GCM.COMPONENT_GROUPING_ID IN ("+  strGroupIds + ")");
                }
                count++;
            }            
            getGroupDetailsQueryParent.append(" )");            
        }
        
        getGroupDetailsQueryParent.append(" AND COMPONENT_TYPE = 'Group'                                                                                                                ");
                  
        LOGGER.debug("SEARCH GROUP PARENT QUERY -- \n" + getGroupDetailsQueryParent.toString());
        LOGGER.info("Exiting getGroupDetailsQueryParent() in Grouping XQueryConstant class.");
        return getGroupDetailsQueryParent.toString();             
    }
    
    /**
     * Method to get the Search Group Parent query Group List.
     *    
     * @return List<String>
     * 
     * Method added For PIM Phase 2 - Search Group
     * Date: 05/20/2016
     * Added By: Cognizant
     */
    public List<String> getCommaSeparatedValues(List<GroupSearchDTO> groupSearchDTOList)
    {
        LOGGER.info("Entering getCommaSeparatedValues() in Grouping XQueryConstant class.");
        List<String> listGroupIds = new ArrayList<String>();        
        StringBuilder strGroupIdList = new StringBuilder();
        
        if(groupSearchDTOList.size() > 0){             
            int count = 0;            
            for(GroupSearchDTO groupSearchDTO : groupSearchDTOList){

                strGroupIdList.append("'" + groupSearchDTO.getGroupId() + "',");

                count++;

                if(count>999){

                    listGroupIds.add(strGroupIdList.substring(0, strGroupIdList.length() - 1));
                    count = 0;
                    strGroupIdList = new StringBuilder();
                }
            }
        }
        if(strGroupIdList.length() > 0)
        {
            listGroupIds.add(strGroupIdList.substring(0, strGroupIdList.length() - 1));
        }
        LOGGER.debug("Group Ids list size -- " + listGroupIds.size());
        LOGGER.info("Exiting getCommaSeparatedValues() in Grouping XQueryConstant class.");
        return listGroupIds;
    }
    
    /**
     * Gets the departments in correct format.
     *
     * @param deptId
     * @return finalString
     * 
     * Method added For PIM Phase 2 - Search Group
     * Modified by Cognizant
     * Date: 05/24/2016
     */     
    public String getNumbersInCorrectFormat(String ids) {
        LOGGER.info("Entering getNumbersInCorrectFormat() in Grouping XQueryConstant class.");
        String finalString = "";
        if(null != ids && ids.trim().length() > 0)
        {
            String[] breakId = ids.split(",");
            for(int count = 0; count < breakId.length; count++)
            {
                finalString = finalString + "'" + breakId[count].trim() + "',";
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
        LOGGER.info("Exiting getNumbersInCorrectFormat() in Grouping XQueryConstant class.");
        return finalString;
        
    }
    
    /**
     * Gets the departments for Group Search.
     *
     * @param deptId
     * @return String
     * 
     * Method added For PIM Phase 2 - Search Group
     * Modified by Cognizant
     * Date: 02/25/2016
     */
    public  String getLikeDepartmentDetailsForString() {          
        LOGGER.info("Entering getLikeDepartmentDetailsForString() in Grouping XQueryConstant class.");
        
        final String GET_ALL_DEPARTMENT_XQUERY=
                " select DeptId DEPTID, DeptName DEPTNAME"
                +" from  ADSE_MERCHANDISE_HIERARCHY dept, "
                +" XMLTABLE('for $dept in $XML_DATA/pim_category/entry/Merchandise_Hier_Spec"
                + " where fn:count($dept/System/Removal_Flag) eq 0 or $dept/System/Removal_Flag eq \"false\""
                +"  return <out><dept_id>{$dept/Identifiers/RMS_Id}</dept_id>"
                + " <dept_name>{$dept/Name}</dept_name></out>' "
                +"  passing dept.XML_DATA as \"XML_DATA\""
                +" columns"
                +" DeptName varchar2(64) path '/out/dept_name',"
                +" DeptId varchar2(64) path '/out/dept_id'"
                +" ) DEPT_NAME"
                +" where dept.ENTRY_TYPE = 4 order by cast(DeptId as Integer)";     
                        
        LOGGER.debug("SEARCH DEPARTMENT QUERY -- \n" + GET_ALL_DEPARTMENT_XQUERY);
        LOGGER.info("Exiting getLikeDepartmentDetailsForString() in Grouping XQueryConstant class.");
        return GET_ALL_DEPARTMENT_XQUERY;             
        
    }
    
    /**
     * Gets the class for Group Search.
     *
     * @param deptIds
     * @return String
     * 
     * Method added For PIM Phase 2 - Search Group
     * Modified by Cognizant
     * Date: 02/25/2016
     */
    public  String getClassDetailsUsingDeptnumbers(String deptids) {  
        LOGGER.info("Entering getClassDetailsUsingDeptnumbers() in Grouping XQueryConstant class.");
        StringBuilder  queryfragment = new StringBuilder();
        
        queryfragment.append("  SELECT CLS_NAME.ClsId CLASS_ID, CLS_NAME.ClsName CLASS_NAME, CLS_NAME.Removal_Flag  ");
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
        queryfragment.append("      Removal_Flag VARCHAR2(10) path '/out/Removal_Flag') CLS_NAME                                                 ");        
        queryfragment.append("  WHERE cls.ENTRY_TYPE = 5                                                                                          ");
        queryfragment.append("  AND (CLS_NAME.Removal_Flag = 'false' or CLS_NAME.Removal_Flag is null)                                            ");
        queryfragment.append("  AND deptId in ( ");
        queryfragment.append(getNumbersInCorrectFormat(deptids));
        queryfragment.append("  )                                                                                       ");
        queryfragment.append("  ORDER BY ClsId                                                                                                    ");

        LOGGER.debug("SEARCH CLASS QUERY -- \n" + queryfragment.toString());
        LOGGER.info("Exiting getClassDetailsUsingDeptnumbers() in Grouping XQueryConstant class.");
        return queryfragment.toString();
           
       }


}
