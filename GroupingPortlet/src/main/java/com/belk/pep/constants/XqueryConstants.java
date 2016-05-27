package com.belk.pep.constants;

//import java.util.logging.Logger;
import org.apache.log4j.Logger;


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
                +"      AGC.ENTRY_TYPE GROUP_TYPE, AGC.GROUP_OVERALL_STATUS_CODE,                          "                        
                +"      AGC.CREATED_BY                                                                    "      
                +"        /*XML_DATA*/                                                                      "             
                +"      FROM ADSE_GROUP_CATALOG AGC,                                                      "             
                +"        XMLTABLE( 'let                                                                  "             
                +"        $Description:= /pim_entry/entry/Group_Ctg_Spec/Description,                     "                   
                +"        $Effective_Start_Date:= /pim_entry/entry/Group_Ctg_Spec/Effective_Start_Date,   "    
                +"        $Effective_End_Date:= /pim_entry/entry/Group_Ctg_Spec/Effective_End_Date        "    
                +"        return                                                                          "                                      
                +"      <out>                                                                             "             
                +"          <Description>{$Description}</Description>                                     "             
                +"          <Effective_Start_Date>{$Effective_Start_Date}</Effective_Start_Date>          "             
                +"          <Effective_End_Date>{$Effective_End_Date}</Effective_End_Date>                "             
                +"      </out>' passing AGC.XML_DATA                                                      "
                +"        Columns                                                                         "             
                //+"        Description VARCHAR2(50) path '/out/Description',                               "             
                +"        Description CLOB path '/out/Description',                               "
                +"        Effective_Start_Date VARCHAR2(50) path '/out/Effective_Start_Date' ,            "             
                +"        Effective_End_Date VARCHAR2(50) path '/out/Effective_End_Date') AGCXML          "             
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
                        +"  ITEM.MDMID,                                                                                         "
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


}
