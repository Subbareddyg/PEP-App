
package com.belk.pep.constants;

import java.util.List;
import java.util.logging.Logger;

import com.belk.pep.vo.AttributeSearchVO;
import com.belk.pep.vo.AttributeValueListVO;
import com.belk.pep.vo.AttributeValueVO;

/**
 * The Class XqueryConstants.
 *
 * @author AFUSOS3
 */
public class XqueryConstants {
    
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(XqueryConstants.class.getName());     

 
    /**
     * Gets the attribute details query.
     *
     * @param iphValue the iph value
     * @return the attribute details query
     */
    public String getAttributeDetailQuery(String categoryId, String attributeId) {

        final String GET_ATTRIBUTE_SEARCH_QUERY = " SELECT "
                +"  ATT.CATEGORY_ID,   ATT.ATTRIBUTE_ID, ATT.ATTRIBUTE_FIELD_TYPE,  ATT.DISPLAY_NAME,   ATT.HTML_DISPLAY_DESC, "
                +"  ATT.ATTRIBUTE_STATUS,   ATT.IS_DISPLAYABLE,   ATT.IS_EDITABLE,  ATT.IS_MANDATORY, "
                +"  ATT.ATTRIBUTE_FIELD_VALUE "
                +"    FROM  "
                +"    PET_ATTRIBUTE ATT "
              //  +"    INNER JOIN PET_ATTRIBUTE_VALUE VAL "
              //  +"    ON ATT.CATEGORY_ID = VAL.CATEGORY_ID "
              //  +"    AND ATT.ATTRIBUTE_ID = VAL.ATTRIBUTE_ID "
                +"    WHERE ATT.ATTRIBUTE_ID = '"+attributeId+"'" 
                +"    AND ATT.CATEGORY_ID     = '"+categoryId+"' ";

        return GET_ATTRIBUTE_SEARCH_QUERY;
    }
    
    public String getAttributeValueList(String categoryId, String attributeId){
        
        final String GET_ATTRIBUTE_VALUE_LIST_QUERY = " SELECT "
         +"  ATTRIBUTE_FIELD_VALUE_SEQ,   ATTRIBUTE_FIELD_VALUE "
         +"    FROM PET_ATTRIBUTE_VALUE "
         +"    WHERE ATTRIBUTE_ID = '"+attributeId+"'" 
         +"    AND  CATEGORY_ID     = '"+categoryId+"' ";
        
        return GET_ATTRIBUTE_VALUE_LIST_QUERY;
    }
   
    /**
     * Gets the IPH list query.
     *
     * @return the IPH list query
     */
    public String getIPHListQuery() {
        
      //  String GET_ATTRIBUTE_LIST_QUERY =
       //     "SELECT CATEGORY_ID, CATEGORY_ID FROM PET_ATTRIBUTE";

        String GET_ATTRIBUTE_LIST_QUERY =
        " SELECT DISTINCT PET.CATEGORY_ID,  HIE.NAME as CATEGORY_NAME, HIE.MDMID FROM ( "
        +"    SELECT I.MDMID, t.ID, t.NAME , t.DISPLAY "
        +"   FROM VENDORPORTAl.ADSE_ITEM_PRIMARY_HIERARCHY  i, "
        +"   xmltable('$c/pim_category/entry/Item_Primary_Hier_Spec' passing i.XML_DATA as \"c\" "
        +"   columns Id varchar(100) path 'Id', "
        +"   Name varchar(100) path 'Name', "
        +"   Display varchar(100)  PATH 'Display') AS t  "
        +"   where  MOD_DTM = to_date('01/01/2000 12:00:00','MM/DD/YYYY HH24:MI:SS') ) HIE  "
        +"  INNER JOIN PET_ATTRIBUTE PET  "
        +"  ON PET.CATEGORY_ID = HIE.MDMID  "
        +"  order by PET.CATEGORY_ID asc  ";
         
        return GET_ATTRIBUTE_LIST_QUERY;
    }
    
    /**
     * Gets the attribute details.
     *
     * @param iphValue the iph value
     * @param attributeStatus the attribute status
     * @param attributeType the attribute type
     * @return the attribute details
     */
    public String getAttributeDetailList(AttributeSearchVO attributeSearchVO) {
        StringBuffer queryBuffer = new StringBuffer();
        
        String pimSelection = attributeSearchVO.getAttributeTypePIM();
        String bmSelection = attributeSearchVO.getAttributeTypeBM();
        
        final String GET_ATTRIBUTE_DETAILS_QUERY = "SELECT CATEGORY_ID, "
               +" ATTRIBUTE_ID, ATTRIBUTE_NAME, ATTRIBUTE_FIELD_TYPE "
               +" FROM PET_ATTRIBUTE  "
               +" WHERE CATEGORY_ID          = '"+ attributeSearchVO.getCategoryIPH()+"'"
               +" AND UPPER(ATTRIBUTE_STATUS) = UPPER('"+attributeSearchVO.getAttributeStatus()+"') "
               +" AND UPPER(ATTRIBUTE_TYPE)   = UPPER('"+attributeSearchVO+"') ";
        
        
        queryBuffer.append(" SELECT CATEGORY_ID, ");
        queryBuffer.append(" ATTRIBUTE_ID, ATTRIBUTE_NAME, ATTRIBUTE_FIELD_TYPE  ");
        queryBuffer.append(" FROM PET_ATTRIBUTE   ");
        queryBuffer.append(" WHERE CATEGORY_ID          = '"+ attributeSearchVO.getCategoryIPH()+"'" );
        queryBuffer.append(" AND UPPER(ATTRIBUTE_STATUS) = UPPER('"+attributeSearchVO.getAttributeStatus()+"') ");
          
          if(pimSelection != null && pimSelection.equalsIgnoreCase("on") &&
                  bmSelection != null && bmSelection.equalsIgnoreCase("on")){
             queryBuffer.append("AND ATTRIBUTE_TYPE IN ('PIM', 'BM')  ");
          }else if(pimSelection != null && pimSelection.equalsIgnoreCase("on")){
              queryBuffer.append("AND UPPER(ATTRIBUTE_TYPE)   = UPPER('PIM')  ");
          }else if(bmSelection != null && bmSelection.equalsIgnoreCase("on")) {
              queryBuffer.append("AND UPPER(ATTRIBUTE_TYPE)   = UPPER('BM')  ");
          }
        
          System.out.println(queryBuffer.toString());
        return queryBuffer.toString();
    }
    
    
    /**
     * Update attribute details.
     *
     * @param attributeValueVO the attribute value vo
     * @return true, if successful
     */
    public String updateAttributeDetails(AttributeValueVO attributeValueVO){
        StringBuffer queryBuffer = new StringBuffer();
        
        queryBuffer.append(" UPDATE PET_ATTRIBUTE ");
        queryBuffer.append(" SET DISPLAY_NAME = '"+attributeValueVO.getAttributeName() +"',");
        queryBuffer.append(" HTML_DISPLAY_DESC = '"+attributeValueVO.getAttributeDisplayName() +"',");
        queryBuffer.append(" ATTRIBUTE_STATUS = '"+attributeValueVO.getAttributeStatus() +"',");
        queryBuffer.append(" IS_DISPLAYABLE = '"+attributeValueVO.getIsSearchable() +"',");
        queryBuffer.append(" IS_EDITABLE = '"+attributeValueVO.getIsEditable() +"',");
        queryBuffer.append(" IS_MANDATORY = '"+attributeValueVO.getIsRequired() +"',");
        queryBuffer.append(" ATTRIBUTE_FIELD_VALUE = '"+attributeValueVO.getAttributeFieldValue() +"' ");
        queryBuffer.append(" WHERE CATEGORY_ID = '"+attributeValueVO.getCategoryId() +"' ");
        queryBuffer.append(" AND ATTRIBUTE_ID = '"+attributeValueVO.getAttributeId() +"' ");
        
       // getAttributeSetValue(attributeValueVO);
        
        return queryBuffer.toString();
    }
    
    
    /**
     * Gets the attribute set value.
     *
     * @param attributeValueVO the attribute value vo
     * @return the attribute set value
     */
    private String getAttributeSetValue(AttributeValueVO attributeValueVO) {
        String finalValueSet = "";
        List<AttributeValueListVO> lstAttributeValueListVO = attributeValueVO.getAttributeValueList();
        if (lstAttributeValueListVO != null) {
            for (int i = 0; i < lstAttributeValueListVO.size(); i++) {
                AttributeValueListVO attributeValueListVO =
                    lstAttributeValueListVO.get(i);
                if(i == 0){
                    finalValueSet = attributeValueListVO.getAttributeFieldValue();
                }else{
                    finalValueSet = finalValueSet + " | " +attributeValueListVO.getAttributeFieldValue();
                }
            }
        }
        System.out.println("Final String "+finalValueSet);
        return finalValueSet;
    }
}
