/**
 *
 */
package com.belk.pep.constants;

/**
 * The Class SqueryConstants.
 *
 * @author AFUSOS3
 */
public class SqueryConstants {

    /**
     * Gets the blue martini attributes.
     *
     * @param categoryNumber the category number
     * @param orinNmber the orin nmber
     * @return the blue martini attributes
     */
    public static  String getBlueMartiniAttributes(String categoryNumber,String orinNmber ) {

        String GET_BLUE_MARTINI_ATTRIBUTES_XQUERY = " WITH Input(CategoryID, StyleID) AS "
                +"   ( SELECT :categoryId CategoryID, :orinNo StyleID FROM dual "
                +"   ) , " 
                +"   attribfieldvalue AS " 
                +"   (SELECT A.ATTRIBUTE_ID, " 
                +"     A.CATEGORY_ID, " 
                +"     A.ATTRIBUTE_NAME, " 
                +"     A.ATTRIBUTE_FIELD_TYPE, " 
                +"     A.MAPPING_KEY, " 
                +"     A.ATTRIBUTE_PATH, " 
                +"     SUBSTR(A.ATTRIBUTE_PATH,0,INSTR(A.ATTRIBUTE_PATH,'/')-1) PARENT_NODE, " 
                +"     SUBSTR(A.ATTRIBUTE_PATH,INSTR(A.ATTRIBUTE_PATH,'/')  +1 ,LENGTH(A.ATTRIBUTE_PATH)) CHILD_NODE, " 
                +"     A.ATTRIBUTE_STATUS, " 
                +"     A.DISPLAY_NAME, " 
                +"     A.IS_DISPLAYABLE, " 
                +"     A.IS_EDITABLE, " 
                +"     A.IS_MANDATORY, " 
                +"     A.HTML_DISPLAY_DESC, " 
                +"     A.MAX_OCCURANCE, " 
                +"     A.ATTRIBUTE_TYPE, " 
                +"     A.ATTRIBUTE_FIELD_VALUE, " 
                +"     IP.StyleID " 
                +"   FROM VENDORPORTAL.PET_ATTRIBUTE A, " 
                +"     INPUT IP " 
                +"   WHERE Category_Id IN " 
                +"     (SELECT regexp_substr(CategoryID,'[^,]+',1,LEVEL) " 
                +"     FROM dual " 
                +"       CONNECT BY regexp_substr(CategoryID,'[^,]+',1,LEVEL) IS NOT NULL " 
                +"     ) " 
                +"   ) " 
                +" SELECT APC.MDMID, " 
                +"   s.SEC_SPEC_VALUE, " 
                +"   afv.ATTRIBUTE_ID, " 
                +"   afv.CATEGORY_ID, " 
                +"   afv.ATTRIBUTE_NAME, " 
                +"   afv.ATTRIBUTE_FIELD_TYPE, " 
                +"   afv.MAPPING_KEY, " 
                +"   afv.ATTRIBUTE_PATH, " 
                +"   afv.ATTRIBUTE_STATUS, " 
                +"   afv.DISPLAY_NAME, " 
                +"   afv.IS_DISPLAYABLE, " 
                +"   afv.IS_EDITABLE, " 
                +"   afv.IS_MANDATORY, " 
                +"   afv.HTML_DISPLAY_DESC, " 
                +"   afv.MAX_OCCURANCE, " 
                +"   afv.ATTRIBUTE_TYPE, " 
                +"   afv.ATTRIBUTE_FIELD_VALUE " 
                +" FROM attribfieldvalue afv, " 
                +"   VENDORPORTAL.ADSE_PET_CATALOG APC, " 
                +"   XMLTABLE('   <category>  <data>{     for $j in $XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[starts-with(name(),$CHILD_NODE)][ends-with(name(),$CHILD_NODE)]    " 
                +"   return       if (not( ($j) is ($XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[starts-with(name(),$CHILD_NODE)][ends-with(name(),$CHILD_NODE)][last()])[1] ) " 
                +"   and not(empty($j)) and not($j = \"\")) then                                       concat($j, \"|\")                     " 
                +"   else                                      string($j)     }     </data>      </category>       " 
                +"   ' passing APC.xml_data AS \"XML_DATA\",afv.PARENT_NODE AS \"PARENT_NODE\", afv.CHILD_NODE AS \"CHILD_NODE\" COLUMNS SEC_SPEC_VALUE VARCHAR(100) " 
                +"   path '/category/data') s " 
                +" WHERE APC.MDMID=afv.STYLEID  " 
                + "AND  afv.ATTRIBUTE_TYPE='BM' AND afv.IS_DISPLAYABLE='Yes' order by attribute_name asc ";



       // return GET_BLUE_MARTINI_ATTRIBUTES_XQUERY;*/


       /* String GET_BLUE_MARTINI_ATTRIBUTES_XQUERY = " WITH Input(CategoryID, StyleID) AS "
                +"   ( SELECT '"+categoryNumber+"' CategoryID, '"+orinNmber+"' StyleID FROM dual "
                +"   ) , "
                +"   attribfieldvalue AS "
                +"   (SELECT A.ATTRIBUTE_ID, "
                +"     A.CATEGORY_ID, "
                +"     A.ATTRIBUTE_NAME, "
                +"     A.ATTRIBUTE_FIELD_TYPE, "
                +"     A.MAPPING_KEY, "
                +"     A.ATTRIBUTE_PATH, "
                +"     SUBSTR(A.ATTRIBUTE_PATH,0,INSTR(A.ATTRIBUTE_PATH,'/')-1) PARENT_NODE, "
                +"     SUBSTR(A.ATTRIBUTE_PATH,INSTR(A.ATTRIBUTE_PATH,'/')  +1 ,LENGTH(A.ATTRIBUTE_PATH)) CHILD_NODE, "
                +"     A.ATTRIBUTE_STATUS, "
                +"     A.DISPLAY_NAME, "
                +"     A.IS_DISPLAYABLE, "
                +"     A.IS_EDITABLE, "
                +"     A.IS_MANDATORY, "
                +"     A.HTML_DISPLAY_DESC, "
                +"     A.MAX_OCCURANCE, "
                +"     A.ATTRIBUTE_TYPE, "
                +"     A.ATTRIBUTE_FIELD_VALUE, "
                +"     IP.StyleID "
                +"   FROM VENDORPORTAL.PET_ATTRIBUTE A, "
                +"     INPUT IP "
                +"   WHERE  "
                +"     Category_Id in (SELECT regexp_substr(CategoryID,'[^,]+',1,LEVEL) FROM dual CONNECT BY regexp_substr(CategoryID,'[^,]+',1,LEVEL)  IS NOT NULL) "
                +"   ) "
                +" SELECT APC.MDMID, "
                +"   s.SEC_SPEC_VALUE, "
                +"   afv.ATTRIBUTE_ID, "
                +"   afv.CATEGORY_ID, "
                +"   afv.ATTRIBUTE_NAME, "
                +"   afv.ATTRIBUTE_FIELD_TYPE, "
                +"   afv.MAPPING_KEY, "
                +"   afv.ATTRIBUTE_PATH, "
                +"   afv.ATTRIBUTE_STATUS, "
                +"   afv.DISPLAY_NAME, "
                +"   afv.IS_DISPLAYABLE, "
                +"   afv.IS_EDITABLE, "
                +"   afv.IS_MANDATORY, "
                +"   afv.HTML_DISPLAY_DESC, "
                +"   afv.MAX_OCCURANCE, "
                +"   afv.ATTRIBUTE_TYPE, "
                +"   afv.ATTRIBUTE_FIELD_VALUE "
                +" FROM attribfieldvalue afv, "
                +"   VENDORPORTAL.ADSE_PET_CATALOG APC, "
                +"   XMLTABLE('  "
                +" <category> "
                +" <data>{ "
                +"    for $j in $XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[ends-with(name(),$CHILD_NODE)] "
                +"    return   "
                +"    if (not( ($j) is ($XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[ends-with(name(),$CHILD_NODE)][last()])[1] ) and not(empty($j)) and not($j = \"\")) then                                   "
                +"    concat($j, \"|\")                               "
                +"    else                                  "
                +"    string($j)    "
                +" }    "
                +" </data>     "
                +" </category>       "
                +" ' passing APC.xml_data AS \"XML_DATA\",afv.PARENT_NODE AS \"PARENT_NODE\", afv.CHILD_NODE AS \"CHILD_NODE\" COLUMNS SEC_SPEC_VALUE VARCHAR(100) path '/category/data') s "
                +" WHERE APC.MDMID=afv.STYLEID "
                + "AND  afv.ATTRIBUTE_TYPE='BM' ";*/



       // System.out.println("GET_BLUE_MARTINI_ATTRIBUTES_XQUERY----"+GET_BLUE_MARTINI_ATTRIBUTES_XQUERY);
        return GET_BLUE_MARTINI_ATTRIBUTES_XQUERY;

    }
    
    /**
	* Updated BY:AFUSKJ2 06172016
	*/
    
    public static  String getGroupBlueMartiniAttributes(String categoryNumber,String orinNmber ) {

        String GET_BLUE_MARTINI_ATTRIBUTES_XQUERY = " WITH Input(CategoryID, StyleID) AS "
                +"   ( SELECT :categoryId CategoryID, :orinNo StyleID FROM dual "
                +"   ) , " 
                +"   attribfieldvalue AS " 
                +"   (SELECT A.ATTRIBUTE_ID, " 
                +"     A.CATEGORY_ID, " 
                +"     A.ATTRIBUTE_NAME, " 
                +"     A.ATTRIBUTE_FIELD_TYPE, " 
                +"     A.MAPPING_KEY, " 
                +"     A.ATTRIBUTE_PATH, " 
                +"     SUBSTR(A.ATTRIBUTE_PATH,0,INSTR(A.ATTRIBUTE_PATH,'/')-1) PARENT_NODE, " 
                +"     SUBSTR(A.ATTRIBUTE_PATH,INSTR(A.ATTRIBUTE_PATH,'/')  +1 ,LENGTH(A.ATTRIBUTE_PATH)) CHILD_NODE, " 
                +"     A.ATTRIBUTE_STATUS, " 
                +"     A.DISPLAY_NAME, " 
                +"     A.IS_DISPLAYABLE, " 
                +"     A.IS_EDITABLE, " 
                +"     A.IS_MANDATORY, " 
                +"     A.HTML_DISPLAY_DESC, " 
                +"     A.MAX_OCCURANCE, " 
                +"     A.ATTRIBUTE_TYPE, " 
                +"     A.ATTRIBUTE_FIELD_VALUE, " 
                +"     IP.StyleID " 
                +"   FROM VENDORPORTAL.PET_ATTRIBUTE A, " 
                +"     INPUT IP " 
                +"   WHERE Category_Id IN " 
                +"     (SELECT regexp_substr(CategoryID,'[^,]+',1,LEVEL) " 
                +"     FROM dual " 
                +"       CONNECT BY regexp_substr(CategoryID,'[^,]+',1,LEVEL) IS NOT NULL " 
                +"     ) " 
                +"   ) " 
                +" SELECT APC.MDMID, " 
                +"   s.SEC_SPEC_VALUE, " 
                +"   afv.ATTRIBUTE_ID, " 
                +"   afv.CATEGORY_ID, " 
                +"   afv.ATTRIBUTE_NAME, " 
                +"   afv.ATTRIBUTE_FIELD_TYPE, " 
                +"   afv.MAPPING_KEY, " 
                +"   afv.ATTRIBUTE_PATH, " 
                +"   afv.ATTRIBUTE_STATUS, " 
                +"   afv.DISPLAY_NAME, " 
                +"   afv.IS_DISPLAYABLE, " 
                +"   afv.IS_EDITABLE, " 
                +"   afv.IS_MANDATORY, " 
                +"   afv.HTML_DISPLAY_DESC, " 
                +"   afv.MAX_OCCURANCE, " 
                +"   afv.ATTRIBUTE_TYPE, " 
                +"   afv.ATTRIBUTE_FIELD_VALUE " 
                +" FROM attribfieldvalue afv, " 
                +"   VENDORPORTAL.ADSE_GROUP_CATALOG APC, " 
                +"   XMLTABLE('   <category>  <data>{     for $j in $XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[starts-with(name(),$CHILD_NODE)][ends-with(name(),$CHILD_NODE)]    " 
                +"   return       if (not( ($j) is ($XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[starts-with(name(),$CHILD_NODE)][ends-with(name(),$CHILD_NODE)][last()])[1] ) " 
                +"   and not(empty($j)) and not($j = \"\")) then                                       concat($j, \"|\")                     " 
                +"   else                                      string($j)     }     </data>      </category>       " 
                +"   ' passing APC.xml_data AS \"XML_DATA\",afv.PARENT_NODE AS \"PARENT_NODE\", afv.CHILD_NODE AS \"CHILD_NODE\" COLUMNS SEC_SPEC_VALUE VARCHAR(100) " 
                +"   path '/category/data') s " 
                +" WHERE APC.MDMID=afv.STYLEID  " 
                + "AND  afv.ATTRIBUTE_TYPE='BM' AND afv.IS_DISPLAYABLE='Yes' order by attribute_name asc ";



       // return GET_BLUE_MARTINI_ATTRIBUTES_XQUERY;*/


       /* String GET_BLUE_MARTINI_ATTRIBUTES_XQUERY = " WITH Input(CategoryID, StyleID) AS "
                +"   ( SELECT '"+categoryNumber+"' CategoryID, '"+orinNmber+"' StyleID FROM dual "
                +"   ) , "
                +"   attribfieldvalue AS "
                +"   (SELECT A.ATTRIBUTE_ID, "
                +"     A.CATEGORY_ID, "
                +"     A.ATTRIBUTE_NAME, "
                +"     A.ATTRIBUTE_FIELD_TYPE, "
                +"     A.MAPPING_KEY, "
                +"     A.ATTRIBUTE_PATH, "
                +"     SUBSTR(A.ATTRIBUTE_PATH,0,INSTR(A.ATTRIBUTE_PATH,'/')-1) PARENT_NODE, "
                +"     SUBSTR(A.ATTRIBUTE_PATH,INSTR(A.ATTRIBUTE_PATH,'/')  +1 ,LENGTH(A.ATTRIBUTE_PATH)) CHILD_NODE, "
                +"     A.ATTRIBUTE_STATUS, "
                +"     A.DISPLAY_NAME, "
                +"     A.IS_DISPLAYABLE, "
                +"     A.IS_EDITABLE, "
                +"     A.IS_MANDATORY, "
                +"     A.HTML_DISPLAY_DESC, "
                +"     A.MAX_OCCURANCE, "
                +"     A.ATTRIBUTE_TYPE, "
                +"     A.ATTRIBUTE_FIELD_VALUE, "
                +"     IP.StyleID "
                +"   FROM VENDORPORTAL.PET_ATTRIBUTE A, "
                +"     INPUT IP "
                +"   WHERE  "
                +"     Category_Id in (SELECT regexp_substr(CategoryID,'[^,]+',1,LEVEL) FROM dual CONNECT BY regexp_substr(CategoryID,'[^,]+',1,LEVEL)  IS NOT NULL) "
                +"   ) "
                +" SELECT APC.MDMID, "
                +"   s.SEC_SPEC_VALUE, "
                +"   afv.ATTRIBUTE_ID, "
                +"   afv.CATEGORY_ID, "
                +"   afv.ATTRIBUTE_NAME, "
                +"   afv.ATTRIBUTE_FIELD_TYPE, "
                +"   afv.MAPPING_KEY, "
                +"   afv.ATTRIBUTE_PATH, "
                +"   afv.ATTRIBUTE_STATUS, "
                +"   afv.DISPLAY_NAME, "
                +"   afv.IS_DISPLAYABLE, "
                +"   afv.IS_EDITABLE, "
                +"   afv.IS_MANDATORY, "
                +"   afv.HTML_DISPLAY_DESC, "
                +"   afv.MAX_OCCURANCE, "
                +"   afv.ATTRIBUTE_TYPE, "
                +"   afv.ATTRIBUTE_FIELD_VALUE "
                +" FROM attribfieldvalue afv, "
                +"   VENDORPORTAL.ADSE_PET_CATALOG APC, "
                +"   XMLTABLE('  "
                +" <category> "
                +" <data>{ "
                +"    for $j in $XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[ends-with(name(),$CHILD_NODE)] "
                +"    return   "
                +"    if (not( ($j) is ($XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[ends-with(name(),$CHILD_NODE)][last()])[1] ) and not(empty($j)) and not($j = \"\")) then                                   "
                +"    concat($j, \"|\")                               "
                +"    else                                  "
                +"    string($j)    "
                +" }    "
                +" </data>     "
                +" </category>       "
                +" ' passing APC.xml_data AS \"XML_DATA\",afv.PARENT_NODE AS \"PARENT_NODE\", afv.CHILD_NODE AS \"CHILD_NODE\" COLUMNS SEC_SPEC_VALUE VARCHAR(100) path '/category/data') s "
                +" WHERE APC.MDMID=afv.STYLEID "
                + "AND  afv.ATTRIBUTE_TYPE='BM' ";*/



       // System.out.println("GET_BLUE_MARTINI_ATTRIBUTES_XQUERY----"+GET_BLUE_MARTINI_ATTRIBUTES_XQUERY);
        return GET_BLUE_MARTINI_ATTRIBUTES_XQUERY;

    }

    /**
     * Gets the pet attributes.
     *
     * @param categoryId the category id
     * @return the pet attributes
     */
    public static String getPetAttributes(String categoryId)
    {

        final String GET_PET_ATTRIBUTES_QUERY=

                " SELECT "
                        +"  PA.ATTRIBUTE_ID, "
                        +"  PA.CATEGORY_ID, "
                        +"   PA.ATTRIBUTE_NAME,"
                        +"  PA.ATTRIBUTE_FIELD_TYPE,"
                        +"  PA.ATTRIBUTE_PATH,"
                        +"  PA.ATTRIBUTE_STATUS,"
                        +"  PA.DISPLAY_NAME,"
                        +"  PAV.ATTRIBUTE_FIELD_VALUE,"
                        +"   PA.IS_DISPLAYABLE,"
                        +"  PA.IS_EDITABLE,"
                        +"  PA.IS_MANDATORY,"
                        +"  PA.HTML_DISPLAY_DESC,"
                        +"  PA.MAX_OCCURANCE ,"
                        +"  PA.ATTRIBUTE_TYPE,"
                        +"  PAV.ATTRIBUTE_FIELD_VALUE_SEQ "
                        +" FROM "
                        +"  PET_ATTRIBUTE PA "
                        +" LEFT OUTER JOIN PET_ATTRIBUTE_VALUE PAV "
                        +"  ON "
                        +"  PA.CATEGORY_ID = PAV.CATEGORY_ID "
                        +" AND  PA.ATTRIBUTE_ID = PAV.ATTRIBUTE_ID "
                        +" WHERE PAV.CATEGORY_ID ="+ categoryId;

        return GET_PET_ATTRIBUTES_QUERY;

    }

    /**
     * Gets the pet attributes by field type.
     *
     * @param attributeId the attribute id
     * @param categoryId the category id
     * @param attributeFieldType the attribute field type
     * @return the pet attributes by field type
     */
    public static String getPetAttributesByFieldType(String attributeId,String categoryId,String attributeFieldType)
    {

        final String GET_PET_ATTRIBUTES_BY_FIELD_TYPE_QUERY=

                " SELECT "
                        +"  PA.ATTRIBUTE_ID,"
                        +"  PA.CATEGORY_ID,"
                        +"  PA.ATTRIBUTE_NAME,"
                        +"  PA.ATTRIBUTE_FIELD_TYPE,"
                        +"  PAV.ATTRIBUTE_FIELD_VALUE "
                        +" FROM "
                        +"  PET_ATTRIBUTE PA "
                        +"   LEFT OUTER JOIN  PET_ATTRIBUTE_VALUE PAV "
                        +"  ON "
                        +"  PA.CATEGORY_ID    = PAV.CATEGORY_ID "
                        +"  AND PA.ATTRIBUTE_ID = PAV.ATTRIBUTE_ID "
                        +"  WHERE "
                        +"  PA.ATTRIBUTE_ID   = " +attributeId
                        +"   AND PAV.CATEGORY_ID ="+ categoryId
                        +"  AND PA.ATTRIBUTE_FIELD_TYPE = "+attributeFieldType;

        return GET_PET_ATTRIBUTES_BY_FIELD_TYPE_QUERY;

    }



    /**
     * Gets the product attributes.
     *
     * @param categoryId the category id
     * @return the product attributes
     */
    /* public static String getProductAttributes(String categoryId,String orinNumber)
    {

        final String GET_PET_ATTRIBUTES_QUERY=
                " WITH Input(CategoryID, StyleID) AS "
                        +"   ( SELECT '"+categoryId+"' CategoryID, '"+orinNumber+"' StyleID FROM dual "
                        +"   ) , "
                        +"   attribfieldvalue AS "
                        +"   (SELECT A.ATTRIBUTE_ID, "
                        +"     A.CATEGORY_ID, "
                        +"     A.ATTRIBUTE_NAME, "
                        +"     A.ATTRIBUTE_FIELD_TYPE, "
                        +"     A.MAPPING_KEY, "
                        +"     A.ATTRIBUTE_PATH, "
                        +"     SUBSTR(A.ATTRIBUTE_PATH,0,INSTR(A.ATTRIBUTE_PATH,'/')-1) PARENT_NODE, "
                        +"     SUBSTR(A.ATTRIBUTE_PATH,INSTR(A.ATTRIBUTE_PATH,'/')  +1 ,LENGTH(A.ATTRIBUTE_PATH)) CHILD_NODE, "
                        +"     A.ATTRIBUTE_STATUS, "
                        +"     A.DISPLAY_NAME, "
                        +"     A.IS_DISPLAYABLE, "
                        +"     A.IS_EDITABLE, "
                        +"     A.IS_MANDATORY, "
                        +"     A.HTML_DISPLAY_DESC, "
                        +"     A.MAX_OCCURANCE, "
                        +"     A.ATTRIBUTE_TYPE, "
                        +"     A.ATTRIBUTE_FIELD_VALUE, "
                        +"     IP.StyleID "
                        +"   FROM VENDORPORTAL.PET_ATTRIBUTE A, "
                        +"     INPUT IP "
                        +"   WHERE Category_Id=IP.CategoryID "
                        +"   ) "
                        +" SELECT APC.MDMID, "
                        +"   s.SEC_SPEC_VALUE, "
                        +"   afv.ATTRIBUTE_ID, "
                        +"   afv.CATEGORY_ID, "
                        +"   afv.ATTRIBUTE_NAME, "
                        +"   afv.ATTRIBUTE_FIELD_TYPE, "
                        +"   afv.MAPPING_KEY, "
                        +"   afv.ATTRIBUTE_PATH, "
                        +"   afv.ATTRIBUTE_STATUS, "
                        +"   afv.DISPLAY_NAME, "
                        +"   afv.IS_DISPLAYABLE, "
                        +"   afv.IS_EDITABLE, "
                        +"   afv.IS_MANDATORY, "
                        +"   afv.HTML_DISPLAY_DESC, "
                        +"   afv.MAX_OCCURANCE, "
                        +"   afv.ATTRIBUTE_TYPE, "
                        +"   afv.ATTRIBUTE_FIELD_VALUE "
                        +" FROM attribfieldvalue afv, "
                        +"   VENDORPORTAL.ADSE_PET_CATALOG APC, "
                        +"   XMLTABLE('  "
                        +" <category> "
                        +" <data>{ "
                        +" for $j in $XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[ends-with(name(),$CHILD_NODE)] "
                        +" return   "
                        +" if (not( ($j) is ($XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[ends-with(name(),$CHILD_NODE)][last()])[1] ) and not(empty($j)) and not($j = \"\")) then          "
                        +" concat($j, \"|\")      "
                        +" else         "
                        +" string($j)    "
                        +" }    "
                        +" </data>     "
                        +" </category>   "
                        +" ' passing APC.xml_data AS \"XML_DATA\",afv.PARENT_NODE AS \"PARENT_NODE\", afv.CHILD_NODE AS \"CHILD_NODE\" COLUMNS SEC_SPEC_VALUE VARCHAR(100) path '/category/data') s "
                        +" WHERE APC.MDMID=afv.STYLEID ; " ;



        return GET_PET_ATTRIBUTES_QUERY;

    }*/


    public static  String getProductAttributes(String categoryumber ,String orinNmber ) {


        String GET_PET_ATTRIBUTES_QUERY = " WITH Input(CategoryID, StyleID) AS "
                +"   ( SELECT :categoryId CategoryID, :orinNo StyleID FROM dual "
                +"   ) , "
                +"   attribfieldvalue AS "
                +"   (SELECT A.ATTRIBUTE_ID, "
                +"     A.CATEGORY_ID, "
                +"     A.ATTRIBUTE_NAME, "
                +"     A.ATTRIBUTE_FIELD_TYPE, "
                +"     A.MAPPING_KEY, "
                +"     A.ATTRIBUTE_PATH, "
                +"     SUBSTR(A.ATTRIBUTE_PATH,0,INSTR(A.ATTRIBUTE_PATH,'/')-1) PARENT_NODE, "
                +"     SUBSTR(A.ATTRIBUTE_PATH,INSTR(A.ATTRIBUTE_PATH,'/')  +1 ,LENGTH(A.ATTRIBUTE_PATH)) CHILD_NODE, "
                +"     A.ATTRIBUTE_STATUS, "
                +"     A.DISPLAY_NAME, "
                +"     A.IS_DISPLAYABLE, "
                +"     A.IS_EDITABLE, "
                +"     A.IS_MANDATORY, "
                +"     A.HTML_DISPLAY_DESC, "
                +"     A.MAX_OCCURANCE, "
                +"     A.ATTRIBUTE_TYPE, "
                +"     A.ATTRIBUTE_FIELD_VALUE, "
                +"     IP.StyleID "
                +"   FROM VENDORPORTAL.PET_ATTRIBUTE A, "
                +"     INPUT IP "
                +"   WHERE  "
                +"     Category_Id in (SELECT regexp_substr(CategoryID,'[^,]+',1,LEVEL) FROM dual CONNECT BY regexp_substr(CategoryID,'[^,]+',1,LEVEL)  IS NOT NULL) "
                +"   ) "
                +" SELECT APC.MDMID, "
                +"   s.SEC_SPEC_VALUE, "
                +"   afv.ATTRIBUTE_ID, "
                +"   afv.CATEGORY_ID, "
                +"   afv.ATTRIBUTE_NAME, "
                +"   afv.ATTRIBUTE_FIELD_TYPE, "
                +"   afv.MAPPING_KEY, "
                +"   afv.ATTRIBUTE_PATH, "
                +"   afv.ATTRIBUTE_STATUS, "
                +"   afv.DISPLAY_NAME, "
                +"   afv.IS_DISPLAYABLE, "
                +"   afv.IS_EDITABLE, "
                +"   afv.IS_MANDATORY, "
                +"   afv.HTML_DISPLAY_DESC, "
                +"   afv.MAX_OCCURANCE, "
                +"   afv.ATTRIBUTE_TYPE, "
                +"   afv.ATTRIBUTE_FIELD_VALUE "
                +" FROM attribfieldvalue afv, "
                +"   VENDORPORTAL.ADSE_PET_CATALOG APC, "
                +"   XMLTABLE('  "
                +" <category> "
                +" <data>{ "
                +"    for $j in $XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[ends-with(name(),$CHILD_NODE)] "
                +"    return   "
                +"    if (not( ($j) is ($XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[ends-with(name(),$CHILD_NODE)][last()])[1] ) and not(empty($j)) and not($j = \"\")) then                                   "
                +"    concat($j, \"|\")                               "
                +"    else                                  "
                +"    string($j)    "
                +" }    "
                +" </data>     "
                +" </category>       "
                +" ' passing APC.xml_data AS \"XML_DATA\",afv.PARENT_NODE AS \"PARENT_NODE\", afv.CHILD_NODE AS \"CHILD_NODE\" COLUMNS SEC_SPEC_VALUE VARCHAR(100) path '/category/data') s "
                +" WHERE APC.MDMID=afv.STYLEID " 
                + "AND  afv.ATTRIBUTE_TYPE='PIM' AND afv.IS_DISPLAYABLE='Yes' order by attribute_name asc ";


    //    System.out.println("GET_PET_ATTRIBUTES_QUERY----"+GET_PET_ATTRIBUTES_QUERY);



        return GET_PET_ATTRIBUTES_QUERY;

    }
    
    public static String getGroupIPHAttributeData(String categoryumber ,String orinNmber ){



        String GET_PET_ATTRIBUTES_QUERY = " WITH Input(CategoryID, StyleID) AS "
                +"   ( SELECT :categoryId CategoryID, :orinNo StyleID FROM dual "
                +"   ) , "
                +"   attribfieldvalue AS "
                +"   (SELECT A.ATTRIBUTE_ID, "
                +"     A.CATEGORY_ID, "
                +"     A.ATTRIBUTE_NAME, "
                +"     A.ATTRIBUTE_FIELD_TYPE, "
                +"     A.MAPPING_KEY, "
                +"     A.ATTRIBUTE_PATH, "
                +"     SUBSTR(A.ATTRIBUTE_PATH,0,INSTR(A.ATTRIBUTE_PATH,'/')-1) PARENT_NODE, "
                +"     SUBSTR(A.ATTRIBUTE_PATH,INSTR(A.ATTRIBUTE_PATH,'/')  +1 ,LENGTH(A.ATTRIBUTE_PATH)) CHILD_NODE, "
                +"     A.ATTRIBUTE_STATUS, "
                +"     A.DISPLAY_NAME, "
                +"     A.IS_DISPLAYABLE, "
                +"     A.IS_EDITABLE, "
                +"     A.IS_MANDATORY, "
                +"     A.HTML_DISPLAY_DESC, "
                +"     A.MAX_OCCURANCE, "
                +"     A.ATTRIBUTE_TYPE, "
                +"     A.ATTRIBUTE_FIELD_VALUE, "
                +"     IP.StyleID "
                +"   FROM VENDORPORTAL.PET_ATTRIBUTE A, "
                +"     INPUT IP "
                +"   WHERE  "
                +"     Category_Id in (SELECT regexp_substr(CategoryID,'[^,]+',1,LEVEL) FROM dual CONNECT BY regexp_substr(CategoryID,'[^,]+',1,LEVEL)  IS NOT NULL) "
                +"   ) "
                +" SELECT APC.MDMID, "
                +"   s.SEC_SPEC_VALUE, "
                +"   afv.ATTRIBUTE_ID, "
                +"   afv.CATEGORY_ID, "
                +"   afv.ATTRIBUTE_NAME, "
                +"   afv.ATTRIBUTE_FIELD_TYPE, "
                +"   afv.MAPPING_KEY, "
                +"   afv.ATTRIBUTE_PATH, "
                +"   afv.ATTRIBUTE_STATUS, "
                +"   afv.DISPLAY_NAME, "
                +"   afv.IS_DISPLAYABLE, "
                +"   afv.IS_EDITABLE, "
                +"   afv.IS_MANDATORY, "
                +"   afv.HTML_DISPLAY_DESC, "
                +"   afv.MAX_OCCURANCE, "
                +"   afv.ATTRIBUTE_TYPE, "
                +"   afv.ATTRIBUTE_FIELD_VALUE "
                +" FROM attribfieldvalue afv, "
                +"   VENDORPORTAL.ADSE_GROUP_CATALOG APC, "
                +"   XMLTABLE('  "
                +" <category> "
                +" <data>{ "
                +"    for $j in $XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[ends-with(name(),$CHILD_NODE)] "
                +"    return   "
                +"    if (not( ($j) is ($XML_DATA/pim_entry/entry/*[ends-with(name(),$PARENT_NODE)]/*[ends-with(name(),$CHILD_NODE)][last()])[1] ) and not(empty($j)) and not($j = \"\")) then                                   "
                +"    concat($j, \"|\")                               "
                +"    else                                  "
                +"    string($j)    "
                +" }    "
                +" </data>     "
                +" </category>       "
                +" ' passing APC.xml_data AS \"XML_DATA\",afv.PARENT_NODE AS \"PARENT_NODE\", afv.CHILD_NODE AS \"CHILD_NODE\" COLUMNS SEC_SPEC_VALUE VARCHAR(100) path '/category/data') s "
                +" WHERE APC.MDMID=afv.STYLEID " 
                + "AND  afv.ATTRIBUTE_TYPE='PIM' AND afv.IS_DISPLAYABLE='Yes' order by attribute_name asc ";


    //    System.out.println("GET_PET_ATTRIBUTES_QUERY----"+GET_PET_ATTRIBUTES_QUERY);



        return GET_PET_ATTRIBUTES_QUERY;

    
    }

    /**
     * Instantiates a new squery constants.
     */
    private SqueryConstants() {

    }


}
