
package com.belk.pep.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringEscapeUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;



import com.belk.pep.constants.ContentScreenConstants;
import com.belk.pep.constants.SqueryConstants;
import com.belk.pep.constants.XqueryConstants;
import com.belk.pep.dao.ContentDAO;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.model.GroupsFound;
import com.belk.pep.model.PetsFound;
import com.belk.pep.util.PropertiesFileLoader;
import com.belk.pep.vo.BlueMartiniAttributesVO;
import com.belk.pep.vo.CarBrandVO;
import com.belk.pep.vo.ChildSkuVO;
import com.belk.pep.vo.ColorAttributesVO;
import com.belk.pep.vo.ContentHistoryVO;
import com.belk.pep.vo.ContentManagementVO;
import com.belk.pep.vo.CopyAttributeVO;
import com.belk.pep.vo.CopyAttributesVO;
import com.belk.pep.vo.GlobalAttributesVO;
import com.belk.pep.vo.GroupingVO;
import com.belk.pep.vo.ItemPrimaryHierarchyVO;
import com.belk.pep.vo.OmniChannelBrandVO;
import com.belk.pep.vo.PetAttributeVO;
import com.belk.pep.vo.ProductDetailsVO;
import com.belk.pep.vo.RegularPetCopy;
import com.belk.pep.vo.SkuAttributesVO;
import com.belk.pep.vo.StyleColorFamilyVO;
import com.belk.pep.vo.StyleInformationVO;
import com.belk.pep.exception.checked.PEPPersistencyException;

/**
 * The Class ContentDAOImpl.
 */
public class ContentDAOImpl implements ContentDAO{
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(ContentDAOImpl.class
        .getName());

    /** The session factory. */
    private SessionFactory sessionFactory;


    /**
     * Check null.
     *
     * @param objectValue the object value
     * @return String
     */
    public String checkNull(Object objectValue){
        String valueStr = "";

        if(objectValue == null ){
           // System.out.println("row object is null, setting to default value");

            valueStr = " ";

        }
        else
        {
            valueStr = objectValue.toString();
        }

        return valueStr;
    }

    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getCardsBrandList(java.lang.String, java.lang.String)
     */
    @Override
    public List<CarBrandVO> getCardsBrandList(String orinNumber, String supplierId)
            throws PEPFetchException {
        LOGGER.info("---inside getCardsBrandList---");
        Session session = null;
        
        List<CarBrandVO> listCarBrandVO=null;
        CarBrandVO carBrandVO = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getCarsBrandQuery(orinNumber,supplierId));
            if(query!=null)
            {   
                query.setParameter("orinNo", orinNumber);
                query.setParameter("supplier", supplierId);
                query.setFetchSize(20);
                rows = query.list();
               
            }
            
            /**
             * MODIIFIED BY AFUAXK4
             * DATE: 02/05/2016
             */
        
            
            if(rows!=null)
            {
                listCarBrandVO = new ArrayList<CarBrandVO>();
                List<CarBrandVO> listCarBrandVOFinal = new ArrayList<CarBrandVO>();
                String selectedBrand = "";
                int i=0; 
                for (final Object[] row : rows) 
                {  
                    String omniBrandCode = checkNull(row[0]);
                    String omniBrandDesc = checkNull(row[1]);
                    String selectedBrandStyle = checkNull(row[2]);
                    String selectedBrandComplexPack = checkNull(row[3]);
                    String entryType = checkNull(row[7]);
                    
                    carBrandVO = new CarBrandVO();
                    carBrandVO.setCarBrandCode(omniBrandCode);
                    carBrandVO.setCarBrandDesc(omniBrandDesc);
                    
                   // if(omniBrandDesc.equals(selectedBrandStyle)
                     //    || omniBrandDesc.equals(selectedBrandComplexPack))
                  //  {
                    if(i==0){
                        if(entryType.equals("Style"))
                        {
                            selectedBrand = selectedBrandStyle;                            
                        }
                        else if(entryType.equals("ComplexPack"))
                        {
                            selectedBrand = selectedBrandComplexPack;                                         
                        }
                    }                                          
                    listCarBrandVO.add(carBrandVO);
                    i++;
                }
                LOGGER.info("selectedBrand:"+selectedBrand);  //JIRA VP9
                for(CarBrandVO carBrand: listCarBrandVO)
                {
                    carBrand.setSelectedBrand(StringEscapeUtils.unescapeHtml4(selectedBrand)); //JIRA VP9
                    listCarBrandVOFinal.add(carBrand);
                }
                listCarBrandVO = listCarBrandVOFinal;
            }
            /**
             * MODIFICATION END AFUAXK4
             * DATE: 02/05/2016
             */
        }catch(final Exception e) {
            throw new PEPFetchException(e);
        }
        finally {
            session.flush();
            session.close();
        }
        return listCarBrandVO;
    }

    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getContentHistoryFromADSE(java.lang.String)
     */
    @Override
    public List<StyleColorFamilyVO> getColorFamilyDataSet()
            throws PEPFetchException {
        Session session = null;
        
        StyleColorFamilyVO styleColor = null;

        List<StyleColorFamilyVO> styleColorList=new ArrayList<StyleColorFamilyVO>();

        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getOminiChannleColorFamily());
            if(query!=null)
            {
                query.setFetchSize(100);
                rows = query.list();
            }

            if(rows!=null)
            {

                for (final Object[] row : rows) {
                    styleColor = new StyleColorFamilyVO();
                    styleColor.setCode(checkNull(row[0]));
                    styleColor.setColorCodeEnd(checkNull(row[1]));
                    styleColor.setColorCodeStart(checkNull(row[2]));
                    styleColor.setSuperColorCode(checkNull(row[3]));
                    styleColor.setSuperColorDesc(checkNull(row[4]));
                    styleColorList.add(styleColor);
                }
            }


        }catch(final Exception e) {
            e.printStackTrace();
            throw new PEPFetchException(e);
        }
        finally {
            session.flush();
            
            session.close();
        }
        return styleColorList;

    }

    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getContentHistoryFromADSE(java.lang.String)
     */
    @Override
    public List<ContentHistoryVO> getContentHistoryFromADSE(String orinNumber)
            throws PEPFetchException {

        Session session = null;
        
        ContentHistoryVO  contentHistory = null;
        List<Object[]> rows=null;
        List<ContentHistoryVO> contentHistoryList = null;

        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getContentHistory(orinNumber));
            if(query!=null)
            {
                query.setParameter("orinNo", orinNumber);
                query.setFetchSize(100);
                rows = query.list();
            }

            if((rows!=null) && (rows.size()>0))
            {contentHistoryList = new ArrayList<ContentHistoryVO> ();
            for (final Object[] row : rows) {
                contentHistory = new ContentHistoryVO();
                contentHistory.setOrinNumber(checkNull(row[0]));
                contentHistory.setContentCreatedDate(checkNull(row[1]));
                contentHistory.setContentCreatedBy(checkNull(row[2]));
                contentHistory.setContentLastUpdatedBy(checkNull(row[3]));
                contentHistory.setContentLastUpdatedDate(checkNull(row[4]));

                contentHistory.setContentStatus(checkNull(row[5]));
                contentHistory.setEntryType(checkNull(row[6]));
                contentHistoryList.add(contentHistory);
            }
            }

        }
        finally {
            session.flush();
            
            session.close();
        }
        return contentHistoryList;
    }

    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getCopyAttributesFromADSE(java.lang.String)
     */
    @Override
    public ContentManagementVO getContentManagmentInfoFromADSE(String orinNumber)
            throws PEPFetchException {

        Session session = null;
        
        ContentManagementVO  contentManagement = null;
        List<Object[]> rows=null;

        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getPetContentManagmentDetails(orinNumber));
            if(query!=null)
            {
                query.setParameter("orinNo", orinNumber);
                query.setFetchSize(100);
                rows = query.list();
            }

            if((rows!=null) && (rows.size()>0))
            {
                for (final Object[] row : rows) {
                    contentManagement = new ContentManagementVO();
                    contentManagement.setOrinNumber(checkNull(row[0]));
                    contentManagement.setStyleNumber(checkNull(row[1]));
                    contentManagement.setStyleName(checkNull(row[2]));
                    contentManagement.setBrand(checkNull(row[3]));
                    contentManagement.setPriority(checkNull(row[4]));
                    contentManagement.setEntryType(checkNull(row[5]));

                }
            }


        }
        finally {
            session.flush();
            
            session.close();
        }
        return contentManagement;
    }





    @Override
    public List<CopyAttributesVO> getCopyAttributesFromADSE(String orinNumber)
            throws PEPFetchException {

        Session session = null;
        
        List<CopyAttributesVO>  copyAttributeList = null;
        List<Object[]> rows=null;
        CopyAttributesVO copyAttributes = new CopyAttributesVO();

        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getCopyAttributeDetails(orinNumber));
            if(query!=null)
            {
                query.setParameter("orinNo", orinNumber);
                rows = query.list();
            }

            if((rows!=null) && (rows.size()>0))
            {
                copyAttributeList= new ArrayList<CopyAttributesVO>();
                for (final Object[] row : rows) {
                    copyAttributes= new CopyAttributesVO();
                    copyAttributes.setOrin(checkNull(row[0]));
                    copyAttributes.setStyleId(checkNull(row[1]));
                    copyAttributes.setColor(checkNull(row[2]));
                    copyAttributes.setSize(checkNull(row[3]));
                    copyAttributes.setEntryType(checkNull(row[4]));
                    copyAttributeList.add(copyAttributes);

                }
            }


        }
        finally {
            session.flush();
            
            session.close();
        }
        return copyAttributeList;
    }


    /**
     * Gets the family categories from iph.
     *
     * @param merchCategoryId the merch category id
     * @return the family categories from iph
     * @throws PEPFetchException the PEP fetch exception
     */
    @Override
    public List<ItemPrimaryHierarchyVO> getFamilyCategoriesFromIPH(String merchCategoryId)
            throws PEPFetchException {
        LOGGER.info("****start of  getFamilyCategoriesFromIPH  method****");
        Session session = null;
        
        ItemPrimaryHierarchyVO iph = null;
        List<ItemPrimaryHierarchyVO> iphList=new ArrayList<ItemPrimaryHierarchyVO>();
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getFamilyCategoryFromIPH(merchCategoryId));
            if(query!=null)
            {
                query.setParameter("categoryId", merchCategoryId);
                query.setFetchSize(10);
                rows = query.list();
            }

            if(rows!=null)
            {
                for (final Object[] row : rows) {
                    iph = new ItemPrimaryHierarchyVO();
                    iph.setCategoryId(checkNull(row[0]));
                    iph.setCategoryName(checkNull(row[1]));
                    iphList.add(iph);

                }
            }
            LOGGER.info("****end of  getFamilyCategoriesFromIPH  method****");

        }catch(final Exception e) {
            e.printStackTrace();
            throw new PEPFetchException(e);
        }
        finally {
            session.flush();
            
            session.close();
        }
        return iphList;

    }



    @Override
    public List<ItemPrimaryHierarchyVO> getIPHCategories()
            throws PEPFetchException {
        // TODO Auto-generated method stub
        return null;
    }



    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getIPHCategories(java.lang.String)
     */
    @Override
    public List<ItemPrimaryHierarchyVO> getIPHCategories(String groupId, String groupType)
            throws PEPFetchException {
         LOGGER.info("start of getIPHCategories........"+groupId);
         Session session = null;
         
         List<Object[]> rows=null;
         ItemPrimaryHierarchyVO itemPrimaryHierarchy = null;
         List<ItemPrimaryHierarchyVO> iphCategoryList = null;

         final XqueryConstants xqueryConstants = new XqueryConstants();
         try {
             session = sessionFactory.openSession();
             
             // Hibernate provides a createSQLQuery method to let you call your
             // native SQL statement directly.
             Query query =null;
             if(ContentScreenConstants.CONSOLIDATED_PRODUCT_GROUP_TYPE.equals(groupType)){
                 query =session.createSQLQuery(xqueryConstants.populateGroupIPH());				
             }else{
                 query =session.createSQLQuery(xqueryConstants.populateGroupIPHForCollections());
             }
             if(query!=null)
             {
                 if(LOGGER.isDebugEnabled())
                 LOGGER.debug("Query updated -->" + query);
                 query.setParameter("groupingNo", groupId);
                 query.setFetchSize(20);
                 rows = query.list();
             }

             if((rows!=null) && (rows.size()>0))
             {
                 iphCategoryList = new ArrayList<ItemPrimaryHierarchyVO>();
                 String finalPath = "";
                 
                 for (final Object[] row : rows) {
                     itemPrimaryHierarchy =new ItemPrimaryHierarchyVO();
                     if(checkNull(row[4]).trim().equals(""))
                     {
                         if(checkNull(row[5]).trim().equals(""))
                         {
                             if(checkNull(row[6]).trim().equals(""))
                             {
                                 if(checkNull(row[7]).trim().equals(""))
                                 {
                                     if(checkNull(row[8]).trim().equals(""))
                                     {
                                         if(checkNull(row[9]).trim().equals(""))
                                         {
                                             if(checkNull(row[10]).trim().equals(""))
                                             {
                                                 itemPrimaryHierarchy.setMerchandiseCategoryId(checkNull(row[10]));
                                                 itemPrimaryHierarchy.setMerchandiseCategoryName(checkNull(row[10]));
                                             }
                                             else
                                             {
                                                 String[] values = checkNull(row[10]).split("-");
                                                 itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                                                 itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                                                 finalPath =  values[1];
                                                 if(LOGGER.isDebugEnabled())
                                                     LOGGER.debug(finalPath);
                                                 itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                                             }
                                         }
                                         else
                                         {
                                             String[] values = checkNull(row[9]).split("-");
                                             itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                                             itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                                             String[] path10 = checkNull(row[10]).split("-");
                                             finalPath =  path10[1] +"/"+ values[1];
                                             if(LOGGER.isDebugEnabled())
                                                 LOGGER.debug(finalPath);
                                             itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                                         }
                                     }
                                     else
                                     {
                                         String[] values = checkNull(row[8]).split("-");
                                         itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                                         itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                                         String[] path10 = checkNull(row[10]).split("-");
                                         String[] path9 = checkNull(row[9]).split("-");
                                         finalPath =  path10[1] +"/"+ path9[1] + "/" +values[1];
                                         if(LOGGER.isDebugEnabled())
                                             LOGGER.debug(finalPath);
                                         itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                                     }
                                 }
                                 else
                                 {
                                     String[] values = checkNull(row[7]).split("-");
                                     itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                                     itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                                     String[] path10 = checkNull(row[10]).split("-");
                                     String[] path9 = checkNull(row[9]).split("-");
                                     String[] path8 = checkNull(row[8]).split("-");
                                     finalPath =  path10[1] +"/"+ path9[1] + "/" + path8[1] + "/" + values[1];
                                     if(LOGGER.isDebugEnabled())
                                         LOGGER.debug(finalPath);
                                     itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                                 }
                             }
                             else
                             {
                                 String[] values = checkNull(row[6]).split("-");
                                 itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                                 itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                                 String[] path10 = checkNull(row[10]).split("-");
                                 String[] path9 = checkNull(row[9]).split("-");
                                 String[] path8 = checkNull(row[8]).split("-");
                                 String[] path7 = checkNull(row[7]).split("-");
                                 finalPath =  path10[1] +"/"+ path9[1] + "/" + path8[1] + "/" +  path7[1] + "/" +values[1];
                                 if(LOGGER.isDebugEnabled())
                                     LOGGER.debug(finalPath);
                                 itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                             }
                         }
                         else
                         {
                             String[] values = checkNull(row[5]).split("-");
                             itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                             itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                             String[] path10 = checkNull(row[10]).split("-");
                             String[] path9 = checkNull(row[9]).split("-");
                             String[] path8 = checkNull(row[8]).split("-");
                             String[] path7 = checkNull(row[7]).split("-");
                             String[] path6 = checkNull(row[6]).split("-");
                             finalPath =  path10[1] +"/"+ path9[1] + "/" + path8[1] + "/" +  path7[1] + "/" +  path6[1] + "/" +values[1];
                             if(LOGGER.isDebugEnabled())
                                 LOGGER.debug(finalPath);
                             itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                         }
                     }
                     else
                     {
                         String[] values = checkNull(row[4]).split("-");
                         itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                         itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                         String[] path10 = checkNull(row[10]).split("-");
                         String[] path9 = checkNull(row[9]).split("-");
                         String[] path8 = checkNull(row[8]).split("-");
                         String[] path7 = checkNull(row[7]).split("-");
                         String[] path6 = checkNull(row[6]).split("-");
                         String[] path5 = checkNull(row[5]).split("-");
                         finalPath =  path10[1] +"/"+ path9[1] + "/" + path8[1] + "/" +  path7[1] + "/" +  path6[1] + "/" +  path5[1] + "/" +values[1];
                         if(LOGGER.isDebugEnabled())
                             LOGGER.debug(finalPath);
                         itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                     }
                     
                     iphCategoryList.add(itemPrimaryHierarchy);
                     Collections.sort(iphCategoryList);
                 }}}

         finally {
             if (session!=null)             
             session.close();
         }
         LOGGER.info("End of getIPHCategories...");
         return iphCategoryList;
    }


  

    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getIPHCategories()
     */
    /**
     * Gets the IPH categories from adse merchandise hierarchy.
     *
     * @param orinNumber the orin number
     * @return the IPH categories from adse merchandise hierarchy
     * @throws PEPFetchException the PEP fetch exception
     */
    @Override
    public List<ItemPrimaryHierarchyVO> getIPHCategoriesFromAdseMerchandiseHierarchy(String orinNumber)
            throws PEPFetchException {
        LOGGER.info("start of getIPHCategoriesFromAdseMerchandiseHierarchy........");
        Session session = null;
        
        List<Object[]> rows=null;
        ItemPrimaryHierarchyVO itemPrimaryHierarchy = null;
        List<ItemPrimaryHierarchyVO> iphCategoryList = null;

        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getIphCategoriesFromAdseMerchandiseHierarchy(orinNumber));
            if(query!=null)
            {
                query.setParameter("orinNo", orinNumber);
                query.setFetchSize(100);
                rows = query.list();
            }

            if((rows!=null) && (rows.size()>0))
            {
                iphCategoryList = new ArrayList<ItemPrimaryHierarchyVO>();
                String finalPath = "";
                
                for (final Object[] row : rows) {
                    itemPrimaryHierarchy =new ItemPrimaryHierarchyVO();
                    /**
                     * Modified by AFUAXK4
                     * DATE: 02/05/2016
                     */
                    if(checkNull(row[4]).trim().equals(""))
                    {
                        if(checkNull(row[5]).trim().equals(""))
                        {
                            if(checkNull(row[6]).trim().equals(""))
                            {
                                if(checkNull(row[7]).trim().equals(""))
                                {
                                    if(checkNull(row[8]).trim().equals(""))
                                    {
                                        if(checkNull(row[9]).trim().equals(""))
                                        {
                                            if(checkNull(row[10]).trim().equals(""))
                                            {
                                                itemPrimaryHierarchy.setMerchandiseCategoryId(checkNull(row[10]));
                                                itemPrimaryHierarchy.setMerchandiseCategoryName(checkNull(row[10]));
                                            }
                                            else
                                            {
                                                String[] values = checkNull(row[10]).split("-");
                                                itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                                                itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                                                finalPath =  values[1];
                                                itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                                            }
                                        }
                                        else
                                        {
                                            String[] values = checkNull(row[9]).split("-");
                                            itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                                            itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                                            String[] path10 = checkNull(row[10]).split("-");
                                            finalPath =  path10[1] +"/"+ values[1];
                                            itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                                        }
                                    }
                                    else
                                    {
                                        String[] values = checkNull(row[8]).split("-");
                                        itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                                        itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                                        String[] path10 = checkNull(row[10]).split("-");
                                        String[] path9 = checkNull(row[9]).split("-");
                                        finalPath =  path10[1] +"/"+ path9[1] + "/" +values[1];
                                        itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                                    }
                                }
                                else
                                {
                                    String[] values = checkNull(row[7]).split("-");
                                    itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                                    itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                                    String[] path10 = checkNull(row[10]).split("-");
                                    String[] path9 = checkNull(row[9]).split("-");
                                    String[] path8 = checkNull(row[8]).split("-");
                                    finalPath =  path10[1] +"/"+ path9[1] + "/" + path8[1] + "/" + values[1];
                                    itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                                }
                            }
                            else
                            {
                                String[] values = checkNull(row[6]).split("-");
                                itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                                itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                                String[] path10 = checkNull(row[10]).split("-");
                                String[] path9 = checkNull(row[9]).split("-");
                                String[] path8 = checkNull(row[8]).split("-");
                                String[] path7 = checkNull(row[7]).split("-");
                                finalPath =  path10[1] +"/"+ path9[1] + "/" + path8[1] + "/" +  path7[1] + "/" +values[1];
                                itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                            }
                        }
                        else
                        {
                            String[] values = checkNull(row[5]).split("-");
                            itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                            itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                            String[] path10 = checkNull(row[10]).split("-");
                            String[] path9 = checkNull(row[9]).split("-");
                            String[] path8 = checkNull(row[8]).split("-");
                            String[] path7 = checkNull(row[7]).split("-");
                            String[] path6 = checkNull(row[6]).split("-");
                            finalPath =  path10[1] +"/"+ path9[1] + "/" + path8[1] + "/" +  path7[1] + "/" +  path6[1] + "/" +values[1];
                            itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                        }
                    }
                    else
                    {
                        String[] values = checkNull(row[4]).split("-");
                        itemPrimaryHierarchy.setMerchandiseCategoryId(values[0]);
                        itemPrimaryHierarchy.setMerchandiseCategoryName(values[1]);
                        String[] path10 = checkNull(row[10]).split("-");
                        String[] path9 = checkNull(row[9]).split("-");
                        String[] path8 = checkNull(row[8]).split("-");
                        String[] path7 = checkNull(row[7]).split("-");
                        String[] path6 = checkNull(row[6]).split("-");
                        String[] path5 = checkNull(row[5]).split("-");
                        finalPath =  path10[1] +"/"+ path9[1] + "/" + path8[1] + "/" +  path7[1] + "/" +  path6[1] + "/" +  path5[1] + "/" +values[1];
                        itemPrimaryHierarchy.setCategoryFullPath(finalPath);
                    }
                    
                    /**
                     * MODIFICATION END BY AFUAXK4
                     * DATE: 02/05/2016
                     */
                    //Add each itemPrimaryHierarchy object to the list
                    iphCategoryList.add(itemPrimaryHierarchy);
                    Collections.sort(iphCategoryList);
                }
            }
        }
        catch(final Exception e )
        {
            throw new PEPFetchException(e);

        }

        finally {
            session.flush();
            
            session.close();
        }
        LOGGER.info("end of getIPHCategoriesFromAdseMerchandiseHierarchy........");
        return iphCategoryList;
    }



    @Override
    public List<ItemPrimaryHierarchyVO> getIPHCategoriesFromADSEPetCatalog(String orinNumber)
            throws PEPFetchException {

        Session session = null;
        
        List<Object[]> rows=null;
        ItemPrimaryHierarchyVO itemPrimaryHierarchy = null;
        List<ItemPrimaryHierarchyVO> iphCategoryList = null;

        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getIphCategoriesFromAdsePetCatalog(orinNumber));
            if(query!=null)
            {
                query.setParameter("orinNo", orinNumber);
                query.setFetchSize(100);
                rows = query.list();
            }
            if((rows!=null) && (rows.size()>0))
            {
                iphCategoryList = new ArrayList<ItemPrimaryHierarchyVO>();

                for (final Object[] row : rows) {
                    itemPrimaryHierarchy =new ItemPrimaryHierarchyVO();
                    String categoryName = checkNull(row[1]);
                    String finalCategory = "";
                    String finalCategoryId = "";
                    if(categoryName != null){
                        String[] catArr = categoryName.split("///");
                        for(int i=1; i<catArr.length; i++){
                            String value = catArr[i].split("-")[1];
                            if(finalCategory == ""){
                                finalCategory = finalCategory +  value;
                            }else{
                                finalCategory = finalCategory + "/" +  value;
                            }
                            if(i == (catArr.length-1)){
                                finalCategoryId = catArr[i].split("-")[0];
                            }
                        }
                    }
                    itemPrimaryHierarchy.setPetCategoryId(finalCategoryId);
                    itemPrimaryHierarchy.setPetCategoryName(finalCategory);
                    //Add each itemPrimaryHierarchy object to the list
                    iphCategoryList.add(itemPrimaryHierarchy);

                }

            }


        }
        catch(final Exception e )
        {
            throw new PEPFetchException(e);

        }

        finally {
            session.flush();
            
            session.close();
        }
        return iphCategoryList;
    }

    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getOmniChannelBrandList(java.lang.String, java.lang.String)
     */
    @Override
    public List<OmniChannelBrandVO> getOmniChannelBrandList(String orinNumber, String supplierId)
            throws PEPFetchException {
        Session session = null;
        
        List<OmniChannelBrandVO> listOmniChannelBrandVO=null;
        OmniChannelBrandVO omniChannelBrandVO = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getOmniChannelBrandQuery(orinNumber,supplierId));
            if(query!=null)
            {
                query.setParameter("orinNo", orinNumber);
                query.setParameter("supplier", supplierId);
                query.setFetchSize(20);
                rows = query.list();
            }
            /**
             * MODIFIED BY AFUAXK4
             * DATE: 02/05/2016
             */

            if(rows!=null)
            {
                listOmniChannelBrandVO = new ArrayList<OmniChannelBrandVO>();
                List<OmniChannelBrandVO> listOmniChannelBrandVOFinal = new ArrayList<OmniChannelBrandVO>();
                String selectedBrand = "";
                int i=0;
                for (final Object[] row : rows) 
                {
                    String omniBrandCode = checkNull(row[0]);
                    String omniBrandDesc = checkNull(row[1]);
                    String selectedBrandStyle = checkNull(row[2]);
                    String selectedBrandComplexPack = checkNull(row[3]);
                    String entryType = checkNull(row[7]);
                    
                    omniChannelBrandVO = new OmniChannelBrandVO();
                    omniChannelBrandVO.setOmniChannelBrandCode(omniBrandCode);
                    omniChannelBrandVO.setOmniChannelBrandDesc(omniBrandDesc);

                    if(i==0){
                        if(entryType.equals("Style"))
                        {
                            selectedBrand = selectedBrandStyle;                            
                        }
                        else if(entryType.equals("ComplexPack"))
                        {
                            selectedBrand = selectedBrandComplexPack;                                           
                        }
                    }                       
                    listOmniChannelBrandVO.add(omniChannelBrandVO);    
                    i++;
                }                
                for(OmniChannelBrandVO omniVO:listOmniChannelBrandVO)
                {
                    omniVO.setSelectedBrand(selectedBrand);
                    listOmniChannelBrandVOFinal.add(omniVO);
                }
                listOmniChannelBrandVO = listOmniChannelBrandVOFinal;
            }
            /**
             * MODIFICATION END AFUAXK4
             * DATE: 02/05/2016
             */
        }catch(final Exception e) {
            throw new PEPFetchException(e);
        }
        finally {
            session.flush();
            
            session.close();
        }
        return listOmniChannelBrandVO;
    }

    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getPetAttributeDetails(java.lang.String)
     */
    @Override
    public List<PetAttributeVO> getPetAttributeDetails(String categoryId)
            throws PEPFetchException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PetAttributeVO> getPetAttributeDetails(String categoryId,String orinNumber)
            throws PEPFetchException {

        Session session = null;
        
        List<Object[]> rows=null;
        new XqueryConstants();
        List<PetAttributeVO> petAttributeList=null;
        //final String categoryIdDummy ="4630";
        try {
            session = sessionFactory.openSession();
            
            Query query  = null;
            if(orinNumber.length() == 7){
                query = session.createSQLQuery(SqueryConstants.getGroupIPHAttributeData(categoryId, orinNumber));
            }else{
                query=session.createSQLQuery(SqueryConstants.getProductAttributes(categoryId,orinNumber));
            }
            
            if(query!=null)
            {
               // LOGGER.info("query executed successfully.........for categoryId."+categoryId);
                query.setParameter("orinNo", orinNumber);
                query.setParameter("categoryId", categoryId);
                query.setFetchSize(100);
                rows = query.list();

            }

            if(rows!=null)
            {
                petAttributeList=new ArrayList<PetAttributeVO>();
                for (final Object[] row : rows) {

                    // LOGGER.info("row[14]....."+row[14]);
                    final PetAttributeVO petAttributesObject = new PetAttributeVO();
                    petAttributesObject.setMdmId(checkNull(row[0]));
                    //Fix for ALM Defect 3165
                    if("Text Field".equals(checkNull(row[5]))){
                        petAttributesObject.setSecondarySpecValue(StringEscapeUtils.escapeHtml4(checkNull(row[1])));
                        
                    }else{
                        petAttributesObject.setSecondarySpecValue(checkNull(row[1]));
                        
                    }
                    petAttributesObject.setAttributeId(checkNull(row[2]));
                    petAttributesObject.setCategoryId(checkNull(row[3]));
                    petAttributesObject.setAttributeName(checkNull(row[4]));
                    petAttributesObject.setAttributeFieldType(checkNull(row[5]));
                    petAttributesObject.setMappingKey(checkNull(row[6]));
                    petAttributesObject.setAttributePath(checkNull(row[7]));
                    petAttributesObject.setAttributeStatus(checkNull(row[8]));
                    petAttributesObject.setDisplayName(checkNull(row[9]));
                    petAttributesObject.setIsDisplayable(checkNull(row[10]));
                    petAttributesObject.setIsEditable(checkNull(row[11]));
                    petAttributesObject.setIsMandatory(checkNull(row[12]));
                    petAttributesObject.setHtmlDescription(checkNull(row[13]));
                    petAttributesObject.setMaximumOcurrence(checkNull(row[14]));
                    petAttributesObject.setAttributeType(checkNull(row[15]));
                    petAttributesObject.setAttributeFieldValue(checkNull(row[16]));
                    final String attributeId= petAttributesObject.getAttributeId();
                    final String categoryId1=petAttributesObject.getCategoryId();
                    final String attributeFieldType=petAttributesObject.getAttributeFieldType();
                  //  LOGGER.info("petAttributesObject....prints......"+petAttributesObject.toString());

                    //petAttributesObject.setMapOfDisplayFields(mapOfDisplayFields);
                    petAttributeList.add(petAttributesObject);


                }
                LOGGER.info(" petAttributeList....."+ petAttributeList.size());
            }


        }
        catch(final Exception exception)
        {
            throw new PEPFetchException(exception);
        }
        finally {
            session.flush();
            
            session.close();
        }
        return petAttributeList;



    }


    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getProductInfoFromADSE(java.lang.String)
     */
    /**
     * Gets the pet blue martini attributes details.
     *
     * @param categoryId the category id
     * @param orinNumber the orin number
     * @return the pet blue martini attributes details
     * @throws PEPFetchException the PEP fetch exception
     */
    @Override
    public List<BlueMartiniAttributesVO> getPetBlueMartiniAttributesDetails(String categoryId,String orinNumber)
            throws PEPFetchException {

        Session session = null;
        
        List<Object[]> rows=null;
        new XqueryConstants();
        List<BlueMartiniAttributesVO> blueMartiniAttributesList=null;
        try {
            session = sessionFactory.openSession();
            
            Query query = null;
            if(orinNumber.length() == 7){
                query = session.createSQLQuery(SqueryConstants.getGroupBlueMartiniAttributes(categoryId, orinNumber));
            }else{
                query = session.createSQLQuery(SqueryConstants.getBlueMartiniAttributes(categoryId, orinNumber));
            }
            
            
            
            LOGGER.info("getPetBlueMartiniAttributesDetails  query  "+query   );
            if(query!=null)
            {
                LOGGER.info("query executed successfully.........for categoryId."+categoryId);
                query.setParameter("orinNo", orinNumber);
                query.setParameter("categoryId", categoryId);
                query.setFetchSize(100);
                rows = query.list();

            }

            if(rows!=null)
            {
                blueMartiniAttributesList=new ArrayList<BlueMartiniAttributesVO>();
                for (final Object[] row : rows) {

                    
                    final BlueMartiniAttributesVO blueMartiniAttributeObject = new BlueMartiniAttributesVO();
                    blueMartiniAttributeObject.setMdmId(checkNull(row[0]));
                    //Fix for ALM Defect 3165
                    if("Text Field".equals(checkNull(row[5]))){
                        blueMartiniAttributeObject.setZbmSecondarySpecValue(StringEscapeUtils.escapeHtml4(checkNull(row[1])));
                    }else{                    
                        blueMartiniAttributeObject.setZbmSecondarySpecValue(checkNull(row[1]));
                    }                   
                    
                    blueMartiniAttributeObject.setAttributeId(checkNull(row[2]));
                    blueMartiniAttributeObject.setCategoryId(checkNull(row[3]));
                    blueMartiniAttributeObject.setAttributeName(checkNull(row[4]));
                    blueMartiniAttributeObject.setAttributeFieldType(checkNull(row[5]));
                    blueMartiniAttributeObject.setMappingKey(checkNull(row[6]));
                    blueMartiniAttributeObject.setAttributePath(checkNull(row[7]));
                    blueMartiniAttributeObject.setAttributeStatus(checkNull(row[8]));
                    blueMartiniAttributeObject.setDisplayName(checkNull(row[9]));
                    blueMartiniAttributeObject.setIsDisplayable(checkNull(row[10]));
                    blueMartiniAttributeObject.setIsEditable(checkNull(row[11]));
                    blueMartiniAttributeObject.setIsMandatory(checkNull(row[12]));
                    blueMartiniAttributeObject.setHtmlDescription(checkNull(row[13]));
                    blueMartiniAttributeObject.setMaximumOcurrence(checkNull(row[14]));
                    blueMartiniAttributeObject.setAttributeType(checkNull(row[15]));
                    blueMartiniAttributeObject.setAttributeFieldValue(checkNull(row[16]));
                    final String attributeId= blueMartiniAttributeObject.getAttributeId();
                    final String categoryId1=blueMartiniAttributeObject.getCategoryId();
                    final String attributeFieldType=blueMartiniAttributeObject.getAttributeFieldType();
                  
                    blueMartiniAttributesList.add(blueMartiniAttributeObject);

                }
                LOGGER.info(" petAttributeList....."+ blueMartiniAttributesList.size());
            }


        }
        catch(final Exception exception)
        {
            throw new PEPFetchException(exception);
        }
        finally {
            session.flush();
            
            session.close();
        }
        return blueMartiniAttributesList;



    }


    @Override
    public ProductDetailsVO getProductInfoFromADSE(String orinNumber)
            throws PEPFetchException {

        Session session = null;
        
        ProductDetailsVO  productDetails = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getProductDetails(orinNumber));
            if(query!=null)
            {
                query.setParameter("orinNo", orinNumber);
                query.setFetchSize(10);
                rows = query.list();
            }

            if(rows!=null)
            {
                for (final Object[] row : rows) {
                    productDetails = new ProductDetailsVO();
                    String productName = checkNull(row[2]);
                    String productDescription = checkNull(row[3]);
                    productDetails.setProductName(productName);
                    productDetails.setProductDescription(productDescription);
                }
            }
        }
        finally {
            session.flush();
            
            session.close();
        }
        return productDetails;
    }

    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getSkuAttributesFromADSE(java.lang.String)
     */
    /**
     * Gets the session factory.
     *
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getSkusFromADSE(java.lang.String)
     */
    @Override
    public SkuAttributesVO getSkuAttributesFromADSE(String skuOrinNumber) throws PEPFetchException {

        Session session = null;
        
        SkuAttributesVO  skuAttributes = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getSkuAttributes(skuOrinNumber));
            if(query!=null)
            {
                query.setParameter("orinNo", skuOrinNumber);
                query.setFetchSize(10);
                rows = query.list();
            }

            if(rows!=null)
            {
                for (final Object[] row : rows) {
                    skuAttributes = new SkuAttributesVO ();
                    skuAttributes.setSourceDomestic(checkNull(row[9]));
                    skuAttributes.setNrfSizeCode(checkNull(row[10]));
                    skuAttributes.setVendorSizeDescription(checkNull(row[5]));
                    skuAttributes.setOmnichannelSizeDescription(checkNull(row[7]));
                    skuAttributes.setBelk04Upc(checkNull(row[1]));
                    skuAttributes.setVendorUpc(checkNull(row[3]));
                    skuAttributes.setLaunchDate(checkNull(row[11]));
                    skuAttributes.setGiftBox(checkNull(row[12]));
                    skuAttributes.setGwp(checkNull(row[13]));
                    skuAttributes.setPwp(checkNull(row[14]));
                    skuAttributes.setPyg(checkNull(row[15]));
                    skuAttributes.setProductDimensionUom(checkNull(row[16]));
                    skuAttributes.setProductLength(checkNull(row[17]));
                    skuAttributes.setProductHeight(checkNull(row[18]));
                    skuAttributes.setProductWidth(checkNull(row[19]));
                    skuAttributes.setProductWeightUom(checkNull(row[20]));
                    skuAttributes.setProductWeight(checkNull(row[21]));
                }
            }


        }
        catch(final Exception exception)
        {
            throw new PEPFetchException(exception);
        }
        finally {
            session.flush();
            
            session.close();
        }
        return skuAttributes;
    }

    @Override
    public List<ChildSkuVO> getSkusFromADSE(String orinNumber)
            throws PEPFetchException {

        Session session = null;
        
        ChildSkuVO  sku = null;
        List<Object[]> rows=null;
        List<ChildSkuVO> skuList=null;

        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getChildSKUDetails(orinNumber));
            if(query!=null)
            {
                query.setParameter("orinNo", orinNumber);
                query.setFetchSize(100);
                rows = query.list();
            }

            if((rows!=null) && (rows.size()>0))
            {
                skuList= new ArrayList<ChildSkuVO>();
                for (final Object[] row : rows)
                {
                    sku = new ChildSkuVO();
                    sku.setOrinNumber(checkNull(row[0]));
                    sku.setVendorName(checkNull(row[1]));
                    sku.setStyleName(checkNull(row[2]));
                    sku.setStyleNumber(checkNull(row[3]));
                    sku.setColorCode(checkNull(row[4]));
                    sku.setColorName(checkNull(row[5]));
                    sku.setSizeName(checkNull(row[6]));
                    sku.setEntryType(checkNull(row[7]));
                    skuList.add(sku);

                }
            }

        }
        finally {
            session.flush();
            
            session.close();
        }
        return skuList;
    }



    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getStyleAttributesFromADSE(java.lang.String)
     */
    @Override
    public List<PetsFound> getStyleAndItsChildFromADSE(String roleName,String orinNumber)
            throws PEPFetchException {
        LOGGER.info("start  of  getStyleAndItsChildFromADSE..." );
        final List<PetsFound> petList = new ArrayList<PetsFound>();
        PetsFound pet=null;
        final XqueryConstants xqueryConstants= new XqueryConstants();
        Session session = null;
        

        try{
            session = sessionFactory.openSession();
            
            //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.
            final Query query = session.createSQLQuery(xqueryConstants.getStyleAndStyleColorAndSKU(roleName, orinNumber));
            LOGGER.info("Updated query with bind variable ***." );
            if(query != null){
                query.setParameter("orinNo", orinNumber);
                query.setFetchSize(100);
            final List<Object[]> rows = query.list();
                if (rows != null) {
                   // LOGGER.info("recordsFetched..." + rows);
                  //  LOGGER.info(" getStyleAndItsChildFromADSE  Query Executing.....");
                    /*
                    for(final Object[] row : rows){
    
                        pet = new PetsFound();
                        pet.setParentStyleOrin(checkNull(row[0]));
                        pet.setOrinNumber(checkNull(row[1]));
                        pet.setColorCode(checkNull(row[2]));
                        pet.setColor(checkNull(row[3]));
                        pet.setVendorSize(checkNull(row[4]));
                        pet.setOmniSizeDescription(checkNull(row[5]));
                        pet.setContentState(checkNull(row[6]));
                        pet.setPetState(checkNull(row[7]));
                        pet.setCompletionDate(checkNull(row[8]));
                        pet.setEntryType(checkNull(row[9]));
                        */
                    /**
                     * MODIFIED BY AFUAXK4
                     * DATE: 02/05/2016
                     */
                    String entryType = "";
                    String timeStamp = "";
                    final Properties prop =   PropertiesFileLoader.getPropertyLoader("contentDisplay.properties");
                    for(final Object[] row : rows){
                        timeStamp = "";
                        pet = new PetsFound();
                        pet.setParentStyleOrin(checkNull(row[0]));
                        pet.setOrinNumber(checkNull(row[1]));
                        pet.setColorCode(checkNull(row[2]));
                        pet.setColor(checkNull(row[3]));
                        pet.setVendorSize(checkNull(row[4]));
                        pet.setOmniSizeDescription(checkNull(row[5]));
                        pet.setContentState(prop.getProperty("Content"+checkNull(row[6])));
                        pet.setPetState(prop.getProperty(checkNull(row[7])));
                        entryType = checkNull(row[10]);
                        if(entryType.equals("Style") || entryType.equals("Complex Pack"))
                        {
                            timeStamp = checkNull(row[9]);
                            if(timeStamp.length() > 10)
                            {
                                timeStamp = timeStamp.substring(0, 10);
                            }
                            pet.setCompletionDate(timeStamp);
                        }
                        else
                        {
                            timeStamp = checkNull(row[8]);
                            if(timeStamp.length() > 10)
                            {
                                timeStamp = timeStamp.substring(0, 10);
                            }
                            pet.setCompletionDate(timeStamp);
                        }
                        //pet.setCompletionDate(checkNull(row[8]));
                        pet.setEntryType(entryType);
                        /**
                         * MODIFICATION END BY AFUAXK4
                         * DATE: 02/05/2016
                         */
                        pet.setImageState(prop.getProperty("Image"+checkNull(row[11])));
                        petList.add(pet);
                    }
                    LOGGER.info("petList size..."+petList.size());
                }
            }


        }catch(final Exception e){
            LOGGER.info("Exception..." + e.getMessage());
            e.printStackTrace();
            throw new PEPFetchException(e);

        }
        finally
        {
          //  LOGGER.info("recordsFetched. getStyleAndItsChildFromADSE finally block.." );
            session.flush();
            
            session.close();

        }

        LOGGER.info("end of  getStyleAndItsChildFromADSE..." );
        return petList;

    }



    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getStyleColorAttributesFromADSE(java.lang.String)
     */
    @Override
    public GlobalAttributesVO getStyleAttributesFromADSE(String orinNumber)
            throws PEPFetchException {

        Session session = null;
        
        GlobalAttributesVO  styleAttributes = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getGlobalAttributesQuery(orinNumber));
            if(query!=null)
            {
                query.setParameter("orinNo", orinNumber);
                query.setFetchSize(10);
                rows = query.list();
            }

            if(rows!=null)
            {
                for (final Object[] row : rows) {
                    styleAttributes = new GlobalAttributesVO ();
                    styleAttributes.setOmniChannelBrand(checkNull(row[0]));
                    styleAttributes.setLongDescription(checkNull(row[1]));
                    styleAttributes.setBelkExclusive(checkNull(row[2]));
                    styleAttributes.setChannelExclusive(checkNull(row[3]));
                    styleAttributes.setSdf(checkNull(row[4]));
                    styleAttributes.setBopis(checkNull(row[5]));
                    styleAttributes.setImportDomestic(checkNull(row[6]));
                    styleAttributes.setStyleOrinNumber(checkNull(row[7]));
                    styleAttributes.setGwp(checkNull(row[10]));
                    styleAttributes.setPwp(checkNull(row[11]));
                    styleAttributes.setPyg(checkNull(row[9]));
                    styleAttributes.setPetStatus(checkNull(row[12]));
                }
            }


        }
        catch(final Exception exception)
        {
            throw  new PEPFetchException(exception);
        }
        finally {
            session.flush();
            
            session.close();
        }
        return styleAttributes;
    }



    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getStyleColorAttributesFromADSE(java.lang.String)
     */
    @Override
    public ColorAttributesVO getStyleColorAttributesFromADSE(String styleColorOrinNumber) throws PEPFetchException {

        Session session = null;
        
        ColorAttributesVO  styleColorAttributes = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getStyleColorAttributes(styleColorOrinNumber));
            if(query!=null)
            {
                query.setParameter("orinNo", styleColorOrinNumber);
                query.setFetchSize(10);
                rows = query.list();
            }

            if(rows!=null)
            {
                for (final Object[] row : rows) {
                    styleColorAttributes = new ColorAttributesVO ();
                    styleColorAttributes.setNrfColorCode(checkNull(row[0]));
                    styleColorAttributes.setNrfColorDescription(checkNull(row[1]));//same as vendor color
                    styleColorAttributes.setSecondaryColorOne(checkNull(row[2]));
                    styleColorAttributes.setSecondaryColorTwo(checkNull(row[3]));
                    styleColorAttributes.setSecondaryColorThree(checkNull(row[4]));
                    styleColorAttributes.setSecondaryColorFour(checkNull(row[5]));
                    styleColorAttributes.setOmniChannelColor(checkNull(row[6]));
                    styleColorAttributes.setColorFamilyCode(checkNull(row[7]));
                    styleColorAttributes.setCode(checkNull(row[8]));
                    styleColorAttributes.setSuperColorName(checkNull(row[9]));

                }
            }
        }
        catch(final Exception exception)
        {
            throw new PEPFetchException(exception);
        }
        finally {
            session.flush();
            
            session.close();
        }
        return styleColorAttributes;
    }


    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getStyleInfoFromADSE(java.lang.String)
     */
    @Override
    public StyleInformationVO getStyleInfoFromADSE(String orinNumber)
            throws PEPFetchException {
        Session session = null;
        
        StyleInformationVO style = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getStyleInformation(orinNumber));
            if(query!=null)
            {
            query.setParameter("orinNo", orinNumber);
                query.setFetchSize(10);
                rows = query.list();
            }

            if(rows!=null)
            {
                for (final Object[] row : rows) {
                    style = new StyleInformationVO();
                    style.setOrin(checkNull(row[0]));
                    style.setDepartmentId(checkNull(row[1]));
                    style.setVendorName(checkNull(row[2]));
                    //LOGGER.info("-----getStyleId--checkNull(row[3])----"+ checkNull(row[3]));
                    style.setStyleId(checkNull(row[3]));
                    style.setClassId(checkNull(row[4]));
                    style.setVendorId(checkNull(row[5]));
                    style.setDescription(checkNull(row[6]));
                    style.setOmniChannelVendorIndicator(checkNull(row[7]));
                    style.setVendorProvidedImageIndicator(checkNull(row[8]));
                    style.setVendorSampleIndicator(checkNull(row[9]));
                    style.setEntryType(checkNull(row[10]));
                    style.setCompletionDateOfStyle(checkNull(row[11]));
                    style.setDeptDescription(checkNull(row[12]));
                    style.setClassDescription(checkNull(row[13]));
                    style.setSupplierSiteId(checkNull(row[14]));
                }
            }


        }catch(final Exception e) {
            e.printStackTrace();
            throw new PEPFetchException(e);
        }
        finally {
            session.flush();
            
            session.close();
        }
        return style;

    }


public boolean releseLockedPet(  String orin, String pepUserID,String pepFunction)throws PEPPersistencyException {
        LOGGER.info("releseLockedPet image request DAO Impl::  lockPET"); 
        boolean  isPetReleased  = false;        
   
        LOGGER.info("pepUserId in the releseLo ckedPet layer"+pepUserID);
        LOGGER.info(" iamge dao impl  orin layer"+orin);        
        LOGGER.info(" pepFunction dao impl  orin layer"+pepFunction);       
         Session session = null;
         Transaction tx = null;
         try{
           session = sessionFactory.openSession();
           tx = session.beginTransaction();
           Query query = session.getNamedQuery("PetLock.deleteLockedPet");
           query.setString("petId", orin);
          // query.setString("pepUser", pepUserID);
           query.setString("pepFunction", pepFunction); 
           query.executeUpdate();
            
         }catch(Exception e) {
             e.printStackTrace();
         }
         finally{
             session.flush();
             tx.commit();
             session.close(); 
         }
            
        return isPetReleased;
        
    }


    /**
     * Sets the session factory.
     *
     * @param sessionFactory the new session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    /**
     * Method to get the Copy attribute details from database.
     *    
     * @param orin String   
     * @return copyAttributeVO CopyAttributeVO
     * 
     * Method added For PIM Phase 2 - Regular Item Copy Attribute
     * Date: 05/16/2016
     * Added By: Cognizant
     * @throws PEPFetchException 
     */
    @Override
    public CopyAttributeVO fetchCopyAttributes(String orin) throws PEPFetchException {

        LOGGER.info("***Entering fetchCopyAttributes() method.");
        Session session = null;        
        CopyAttributeVO copyAttributeVO = null;
        List<Object[]> rows = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();            
            final Query query = session.createSQLQuery(xqueryConstants.fetchCopyAttributesQuery());
            if(query!=null)
            {
                query.setParameter("orinNum", orin);                
                rows = query.list();
            }

            if(rows!=null)
            {
                for (final Object[] row : rows) {
                    copyAttributeVO = new CopyAttributeVO();
                    copyAttributeVO.setOrin(checkNull(row[0]));
                    if(row[1] != null)
                    {
                        Clob clob = (Clob) (row[1]);
                        copyAttributeVO.setProductCopyText(StringEscapeUtils.escapeHtml4(clobToString(clob)));
                        
                    }
                    else
                    {
                        copyAttributeVO.setProductCopyText("");
                    }
                    copyAttributeVO.setCopyLine1(StringEscapeUtils.escapeHtml4(checkNull(row[2])));
                    copyAttributeVO.setCopyLine2(StringEscapeUtils.escapeHtml4(checkNull(row[3])));
                    copyAttributeVO.setCopyLine3(StringEscapeUtils.escapeHtml4(checkNull(row[4])));
                    copyAttributeVO.setCopyLine4(StringEscapeUtils.escapeHtml4(checkNull(row[5])));
                    copyAttributeVO.setCopyLine5(StringEscapeUtils.escapeHtml4(checkNull(row[6])));
                    copyAttributeVO.setCopyProductName(StringEscapeUtils.escapeHtml4(checkNull(row[7])));
                    copyAttributeVO.setMaterial(StringEscapeUtils.escapeHtml4(checkNull(row[8])));
                    copyAttributeVO.setCare(StringEscapeUtils.escapeHtml4(checkNull(row[9])));
                    copyAttributeVO.setCountryOfOrigin(StringEscapeUtils.escapeHtml4(checkNull(row[10])));
                    copyAttributeVO.setExclusive(StringEscapeUtils.escapeHtml4(checkNull(row[11])));
                    copyAttributeVO.setCaprop65Compliant(StringEscapeUtils.escapeHtml4(checkNull(row[12])));
                    copyAttributeVO.setImportDomestic(StringEscapeUtils.escapeHtml4(checkNull(row[13])));
                    
                    LOGGER.debug("Copy Attribute Values -- \nORIN: " + copyAttributeVO.getOrin() +
                        "\nPRODUCT COPY TEXT: " + copyAttributeVO.getProductCopyText() +
                        "\nCOPY LINE 1: " + copyAttributeVO.getCopyLine1() + 
                        "\nCOPY LINE 2: " + copyAttributeVO.getCopyLine2() + 
                        "\nCOPY LINE 3: " + copyAttributeVO.getCopyLine3() + 
                        "\nCOPY LINE 4: " + copyAttributeVO.getCopyLine4() + 
                        "\nCOPY LINE 5: " + copyAttributeVO.getCopyLine5() + 
                        "\nCOPY PRODUCT NAME: " + copyAttributeVO.getCopyProductName() +
                        "\nMATERIAL: " + copyAttributeVO.getMaterial() +
                        "\nCARE: " + copyAttributeVO.getCare() +
                        "\nCOUNTRY OF ORIGIN: " + copyAttributeVO.getCountryOfOrigin() +
                        "\nEXCLUSIVE: " + copyAttributeVO.getExclusive() +
                        "\nCAPROP65 COMPLIANT: " + copyAttributeVO.getCaprop65Compliant() +
                        "\nCOPY IMPORT DOMESTIC: " + copyAttributeVO.getImportDomestic());
                }
            }
        } catch (PEPFetchException e) {
            LOGGER.error("ContentDAOImpl :fetchCopyAttributes:fetchCopyAttributes"+e);
            throw e;
        }
        finally {
            if(session!=null)         
            session.close();
        }
        LOGGER.info("***Exiting fetchCopyAttributes() method.");
        return copyAttributeVO;
    }
    
    /**
     * Method to get the STring datatype from CLOB.
     *    
     * @param data Clob   
     * @return sb String
     * 
     * Method added For PIM Phase 2 - Regular Item Copy Attribute
     * Date: 05/16/2016
     * Added By: Cognizant
     * @throws PEPFetchException 
     */
    private String clobToString(Clob data) throws PEPFetchException {
        LOGGER.info("***Entering clobToString() method.");
        StringBuilder sb = new StringBuilder();
        try {
            Reader reader = data.getCharacterStream();
            BufferedReader br = new BufferedReader(reader);

            
            int b;
            while(-1 != (b = br.read())) {
                sb.append((char)b);
            }
            br.close();
        } catch (SQLException e) {
            LOGGER.error("Exception in clobToString() method. -- " + e);
            throw new PEPFetchException(e);
        } catch (IOException e) {
            LOGGER.error("Exception in clobToString() method. -- " + e);
            throw new PEPFetchException(e);
        }
        LOGGER.info("***Exiting clobToString() method.");
        return sb.toString();
    }
    
    /**
     * This method to retrieve group information
     * @author AFUSKJ2 6/17/2016
     * @return StyleInformationVO
     * @param groupId
     */
    @Override
    public StyleInformationVO getGroupingInformation(String groupId)
            throws PEPFetchException {
        LOGGER.info("ContentDAOImpl getGroupingInformation : Starts");
        StyleInformationVO styleInformation = new StyleInformationVO();
        
        Session session = null;
        
        List<Object[]> row = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        
        try{
            session = sessionFactory.openSession();
            
            // XQuery for retrieving group information 
            final Query query = session.createSQLQuery(xqueryConstants.getGroupingInfoAttribute());
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("Xquery query in getGroupingInformation:----- "+query);
            }
            
            
            if(query != null){
                query.setParameter("groupId", groupId);
                query.setFetchSize(10);
                row = query.list();
            }

            
            final Properties prop =   PropertiesFileLoader.getPropertyLoader("contentDisplay.properties");
            if(null!=row && row.size() > 0){
                for(Object[] obj : row){
                    // Populate the Group information object here
                    styleInformation.setOrin(checkNull(obj[0]).toString());
                    styleInformation.setDepartmentId(checkNull(obj[1]).toString());
                    styleInformation.setStyle(checkNull(obj[2]).toString());
                    styleInformation.setGroupingType(checkNull(obj[3]).toString());
                    styleInformation.setVendorId(checkNull(obj[4]).toString());
                    styleInformation.setVendorName(checkNull(obj[5]).toString());
                    styleInformation.setOmniChannelVendorIndicator(checkNull(obj[6]).toString());
                    styleInformation.setClassId(checkNull(obj[7]).toString());
                    styleInformation.setVendorProvidedImageIndicator(checkNull(obj[8]).toString());
                    styleInformation.setVendorSampleIndicator(checkNull(obj[9]).toString());
                    String completionDate = checkNull(obj[10]);
                    if(null == obj[10]){
                        completionDate = "";
                    }else{
                        completionDate =  obj[10].toString().substring(0, 10);
                    }
                    styleInformation.setCompletionDateOfStyle(completionDate);
                    styleInformation.setContentStatus(prop.getProperty("Content"+checkNull(obj[11])));
                    styleInformation.setImageStatusCode(checkNull(obj[12]));
                    styleInformation.setOverallStatusCode(checkNull(obj[13]));
                }
            }
        }
        finally{
            if(session!=null)
            session.close();
        }
        return styleInformation;
    }
    
    
    /**
     * This method retrieves department details for the group if department id is not null
     * @author AFUSKJ2 6/17/2016
     * @return StyleInformationVO
     * @param styleInformationVo
     */
    @Override
    public StyleInformationVO getGroupingDepartmentDetails(StyleInformationVO styleInformationVO) throws PEPFetchException {
        LOGGER.info("ContentDaoImpl getGroupingDepartmentDetails : starts");
        Session session = null;
        List<Object[]> rows = null;
        
        final XqueryConstants xqueryConstants = new XqueryConstants();
        
        try{
            session = sessionFactory.openSession();
            
            
            final Query query = session.createSQLQuery(xqueryConstants.getGroupDepartmentDetails(styleInformationVO.getDepartmentId(), styleInformationVO.getClassId()));
            
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("Quer in getGroupingDepartmentDetails::: "+query);
            }
            
            
            if(null!= query){
                rows = query.list();
            }

            
            if(null!=rows && rows.size()>0){
                for(Object[] obj : rows){
                    // Populate style information object with the department details
                    styleInformationVO.setDeptDescription(checkNull(obj[0]).toString());
                    styleInformationVO.setClassDescription(checkNull(obj[1]).toString());
                }
            }
        }
        finally{
            if(session!=null)
                session.close();
        }
        LOGGER.info("ContentDaoImpl getGroupingDepartmentDetails : end");
        return styleInformationVO;
    }
    
    
    /**
     * This method retrieves grouping details
     * @author AFUSKJ2 6/17/2016
     * @return ProductDetailsVO
     * @param groupId
     * @throws PEPFetchException 
     */
    @Override
    public ProductDetailsVO getGroupingDetails(String groupId) throws PEPFetchException{
        LOGGER.info("ContentDAOImpl getGroupingDetails : start");
        ProductDetailsVO productDetailsVO = new ProductDetailsVO();
        
        Session session = null;
        
        List<Object[]> row = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        
        try{
            session = sessionFactory.openSession();
            
            
            //XQuery for retrieving grouping details
            final Query query = session.createSQLQuery(xqueryConstants.getGroupingDetails());
            
            if(null!= query){
                query.setParameter("groupingNo", groupId);
                query.setFetchSize(10);
                row = query.list();
            }

            
            if(null!= row && row.size() > 0){
                for(Object[] obj : row){
                    // Populate the product details object from query result
                    
                    productDetailsVO.setProductName(checkNull(obj[1]).toString());
                    
                    final Clob groupDescClob = (Clob)((Clob)obj[2]!=null ? obj[2]:null);
                    String groupDesc = clobToString(groupDescClob);

                    if(LOGGER.isDebugEnabled()){
                        LOGGER.debug("Prodcut Description Clob converted to String:::::: "+groupDesc);
                    }
                    
                    productDetailsVO.setProductDescription(groupDesc);
                    productDetailsVO.setStyleId(checkNull(obj[0]).toString());
                }
            }
            
        } catch (PEPFetchException e) {
            LOGGER.info("ContentDAOImpl getGroupingDetails : PEPFetchException"+e);
            throw e;
        }finally{
            if(session!=null)
                session.close();
        }
        LOGGER.info("ContentDAOImpl getGroupingDetails : end");
        return productDetailsVO;
    }
    
    /**
     * This method populates Grouping Copy Attrbites section data
     * @author AFUSKJ2 6/17/2016
     * @param groupId
     * @return
     * @throws PEPFetchException
     */
    @Override
    public CopyAttributeVO getGroupingCopyAttributes(String groupId)throws PEPFetchException {
        CopyAttributeVO copyAttributeVO = new CopyAttributeVO();
        LOGGER.info("ContentDAOImpl getGroupingCopyAttributes : start");
        Session session = null;
        
        List<Object[]> row = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        
        try{
            session = sessionFactory.openSession();
            //
            
            // XQuery to retieve Grouping Copy Attributes
            final Query query = session.createSQLQuery(xqueryConstants.getGroupingCopyAttributes());
            
            if(null!= query){
                query.setParameter("groupingNo", groupId);
                row = query.list();
            }
            

            
            
            if(null!= row && row.size() > 0){
                for(Object[] obj : row){
                    // Populate Copy Attributes object from row
                    copyAttributeVO.setOrin(checkNull(obj[0]).toString());
                    final Clob groupDescClob = (Clob) ((Clob) obj[1]!=null?obj[1]:null);
                     
                    String groupDesc = clobToString(groupDescClob);
                    if(LOGGER.isDebugEnabled()){
                        LOGGER.debug("Clob after converted to string:::: "+groupDesc);
                    }
                    copyAttributeVO.setProductCopyText(groupDesc != null ? groupDesc : "");
                    copyAttributeVO.setCopyLine1(checkNull(obj[2]).toString());
                    copyAttributeVO.setCopyLine2(checkNull(obj[3]).toString());
                    copyAttributeVO.setCopyLine3(checkNull(obj[4]).toString());
                    copyAttributeVO.setCopyLine4(checkNull(obj[5]).toString());
                    copyAttributeVO.setCopyLine5(checkNull(obj[6]).toString());
                    copyAttributeVO.setCopyProductName(checkNull(obj[7]).toString());
                    copyAttributeVO.setMaterial(checkNull(obj[8]).toString());
                    copyAttributeVO.setCare(checkNull(obj[9]).toString());
                    copyAttributeVO.setCountryOfOrigin(checkNull(obj[10]).toString());
                    copyAttributeVO.setExclusive(checkNull(obj[11]).toString());
                    copyAttributeVO.setCaprop65Compliant(checkNull(obj[12]).toString());
                    copyAttributeVO.setImportDomestic(checkNull(obj[13]).toString());
                }
            }
        }finally{
            if (session!=null)
                session.close();
        }
        LOGGER.info("ContentDAOImpl getGroupingCopyAttributes : end");
        return copyAttributeVO;
    }
    
    
    /**
     * This method retrieves omni-channel brand list for group
     * @author AFUSKJ2 6/17/2016
     * @return List<OmniChannelBrandVo>
     * @param groupId
     * @throws PEPFetchException
     */
    @Override
    public List<OmniChannelBrandVO> getGroupingOmniChannelBrand(String groupId){
        LOGGER.info("ContentDAOImpl getGroupingOmniChannelBrand : start");
        List<OmniChannelBrandVO> listOmniChannelBrand = null;
        Session session = null;

        List<Object[]> rows = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        OmniChannelBrandVO omniChannelBrandVO = null;
        
        
        try{
            session = sessionFactory.openSession();

            // XQuery constants string
            final Query query = session.createSQLQuery(xqueryConstants.getGroupingOmniChannelBrand());
            
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("Query query in getGroupingOmniChannelBrand:::: "+query);
            }
            
            
            if(null!= query){
                query.setParameter("groupingNo", groupId);
                rows = query.list();
            }

            
            if(null!= rows){
                listOmniChannelBrand = new ArrayList<OmniChannelBrandVO>();
                for(Object[] obj : rows){
                    // Populate the values;
                    omniChannelBrandVO = new OmniChannelBrandVO();
                    omniChannelBrandVO.setOmniChannelBrandCode(checkNull(obj[0]).toString());
                    omniChannelBrandVO.setOmniChannelBrandDesc(checkNull(obj[1]).toString());
                    omniChannelBrandVO.setSelectedBrand(checkNull(checkNull(obj[2]).toString()));
                    if("CPG".equals(checkNull(checkNull(obj[7]).toString()))){
                        omniChannelBrandVO.setSelectedBrand(checkNull(checkNull(obj[2]).toString()));
                    }
                    else{
                        omniChannelBrandVO.setSelectedBrand(checkNull(checkNull(obj[3]).toString()));
                    }
                    listOmniChannelBrand.add(omniChannelBrandVO);
                }
                
                
            }
        }finally{
            if(session!=null)
                session.close();
        }
        LOGGER.info("ContentDAOImpl getGroupingOmniChannelBrand : end");
        return listOmniChannelBrand;
    }
    
    
    /**
     * This method populates car brand list for group
     * @author AFUSKJ2 6/17/2016
     * @return List<CarBrandVO>
     * @param groupId
     * @throws PEPFetchException
     */
    public List<CarBrandVO> populateGroupCarBrandList(String groupId){
        LOGGER.info("ContentDAOImpl populateGroupCarBrandList : start");
        List<CarBrandVO> carBrandList = null;
        Session session = null;
        CarBrandVO carBrandVO = null;
        List<Object[]> rows = null;
        
        final XqueryConstants xqueryConstants = new XqueryConstants();
        
        try{
            session = sessionFactory.openSession();
            //

            // Populate Car brand query
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("Query in populateGroupCarBrandList DAO -->"+xqueryConstants.getGroupingCarBrandQuery());
            }
            final Query query = session.createSQLQuery(xqueryConstants.getGroupingCarBrandQuery());
            
            if(null!= query){
                query.setParameter("groupingNo", groupId);
                rows = query.list();
            }
            
            
            if(null != rows){
                carBrandList = new ArrayList<CarBrandVO>();
                for(Object[] obj : rows){
                    carBrandVO = new CarBrandVO();
                    carBrandVO.setCarBrandCode(checkNull(obj[0]));
                    carBrandVO.setCarBrandDesc(checkNull(obj[1]));
                    
                    if("CPG".equals(checkNull(checkNull(obj[7]).toString()))){
                        carBrandVO.setSelectedBrand(StringEscapeUtils.unescapeHtml4(checkNull(obj[2])));
                    }
                    else{
                        carBrandVO.setSelectedBrand(StringEscapeUtils.unescapeHtml4(checkNull(obj[3])));
                    }
                    carBrandList.add(carBrandVO);
                }
            }
            
        }finally{
            if(session!=null)
                session.close();
        }
        LOGGER.info("ContentDAOImpl populateGroupCarBrandList : end");
        return carBrandList;
    }
    
    /**
     * This method populates grouping component section data
     * @param groupId
     * @return List<GroupsFound>
     * @throws PEPFetchException
     */
    @Override
    public List<GroupsFound> getGroupingComponents(String groupId) {
        LOGGER.info("ContentDAOImpl getGroupingComponents : start updatated  "+groupId);
        List<GroupsFound> groupsList = new ArrayList<GroupsFound>();
        Session session = null;        
        List<Object[]> rows = null;
        List<Object[]> components = null;
        GroupsFound groupsFound = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();        
        try{            
            session = sessionFactory.openSession();
             final Query getComponents = session.createSQLQuery(xqueryConstants.getComponentIDs());
                if(null!=getComponents){
                    getComponents.setParameter(0, groupId);
                    components = getComponents.list();
                }
              
            List componentIds = new ArrayList();
            if(null!= components && components.size()>0){
                new ArrayList();
                 for(Object[] obj : components){ 
                     if(obj[1]!=null ){
                      componentIds.add(obj[1].toString());
                     } else if (obj[2]!=null){
                         componentIds.add(obj[2].toString());
                     }
                
               }                
              final Query query = session.createSQLQuery(xqueryConstants.getGroupingComponents());            
              if(null!=query){
                  query.setParameter("groupingNo", groupId);
                  query.setParameterList("componentIds", componentIds);           
                  rows = query.list();
              }
              if(LOGGER.isDebugEnabled()){
                  LOGGER.debug("Query in DAO getGroupingComponents:::: "+query);
              }  
              LOGGER.debug(" rows.size() "+rows.size());
              final Properties prop =   PropertiesFileLoader.getPropertyLoader("contentDisplay.properties");                
                 if(null!= rows){                     
                     for(Object[] obj : rows){
                         groupsFound = new GroupsFound();  
                         if (checkNull(obj[6])!=null && !(obj[6].toString()).equals("SKU")){
                         groupsFound.setGroupId(checkNull(obj[0]).toString());
                         groupsFound.setStyleId(checkNull(obj[1]).toString());
                         groupsFound.setComponentId(checkNull(obj[2]).toString());
                         String completionDate = "";
                         if(null != obj[3]){
                             completionDate = obj[3].toString().substring(0, 10);
                         }else{
                             completionDate = "";
                         }
                         groupsFound.setCompletionDate(completionDate);
                         groupsFound.setPetStatus(checkNull(obj[4]).toString());
                         groupsFound.setContentStatus(prop.getProperty("Content"+checkNull(obj[5])));                         
                         groupsFound.setEntryType(checkNull(obj[6]).toString());
                         groupsFound.setComponentType(checkNull(obj[7]).toString());
                         groupsFound.setColorCode(checkNull(obj[8]).toString());
                         groupsFound.setColorDesc(checkNull(obj[9]).toString());
                         groupsFound.setVendorSizeodeDesc(checkNull(obj[10]).toString());
                         groupsFound.setOmniChannelCodeDesc("");
                         groupsFound.setVPN(checkNull(obj[11]).toString());
                         groupsList.add(groupsFound);
                         }
                     }
                 }
                 
            }
            
            }catch(Exception e){
                LOGGER.debug("Query in DAO getGroupingComponents:::: ",e);
                e.printStackTrace();
            }            
        finally{
            if(session!=null)
            session.close();
        }
        LOGGER.info("ContentDAOImpl getGroupingComponents : groupsList end"+groupsList);
        return groupsList;
    }
    
    
    /**
     * This method populates grouping content history section data
     * @param groupId
     * @return List<ContentHistoryVO>
     * @throws PEPFetchException
     */
    @Override
    public List<ContentHistoryVO> getGroupContentHistory(String groupId){
        
        LOGGER.info("ContentDAOImpl getGroupContentHistory : start");
        List<ContentHistoryVO> groupContentHistoryList = null;
        Session session = null;
        
        List<Object[]> rows = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        
        try{
            session = sessionFactory.openSession();
            //
            
            final Query query = session.createSQLQuery(xqueryConstants.getGroupContentHistoryQuery());
            
            if(null!=query){
                query.setParameter("groupingNo", groupId);
                rows = query.list();
            }
            
            
            
            
            if(null != rows){
                groupContentHistoryList = new ArrayList<ContentHistoryVO>();
                for(Object[] obj : rows){
                    ContentHistoryVO historyVO = new ContentHistoryVO();
                    historyVO.setOrinNumber(checkNull(obj[0]).toString());
                    historyVO.setContentCreatedBy(checkNull(obj[1]).toString());
                    String createdDate = "";
                    if(null != obj[2]){
                        createdDate = obj[2].toString().substring(0,10);
                        
                    }else{
                        createdDate = "";
                    }
                    historyVO.setContentCreatedDate(createdDate);
                    historyVO.setContentStatus(checkNull(obj[3]).toString());
                    String lastUpdateddate = "";
                    if(null!=obj[4]){
                        lastUpdateddate = obj[4].toString().substring(0, 10);
                        
                    }else{
                        lastUpdateddate = "";
                    }
                    historyVO.setContentLastUpdatedDate(lastUpdateddate);
                    historyVO.setContentLastUpdatedBy(checkNull(obj[5]).toString());
                    historyVO.setEntryType(checkNull(obj[6]).toString());
                    groupContentHistoryList.add(historyVO);
                }
            }
        }finally{
            if(session!=null)
                session.close();
        }
        LOGGER.info("ContentDAOImpl getGroupContentHistory : end");
        return groupContentHistoryList;
    }
    
    
    
    /**
     * This method populates IPH category drop down list
     * @return List<ItemPrimaryHierarchyVO>
     * @param groupId
     * @throws PEPFetchException
     * @author AFUSKJ2 6/17/2016
     */
    @Override
    public List<ItemPrimaryHierarchyVO> selectedIPHCategorydropdown(String groupId) {
        LOGGER.info("ContentDAOImpl selectedIPHCategorydropdown : starts");
        List<Object[]> rows=null;
        ItemPrimaryHierarchyVO itemPrimaryHierarchy = null;
        List<ItemPrimaryHierarchyVO> iphCategoryList = null;
        Session session = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        
        try{
            session = sessionFactory.openSession();
            
            
            // Query to populate Group IPH Category Paths
            final Query query = session.createSQLQuery(xqueryConstants.populateGroupIPHcategoryPath());
            
            if(null!=query){
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("query -- >"+query);
                }
                query.setParameter("groupingNo", groupId);
                rows = query.list();
            }

            
            
             if((rows!=null) && (rows.size()>0))
                {
                    iphCategoryList = new ArrayList<ItemPrimaryHierarchyVO>();

                    for (final Object[] row : rows) {
                        itemPrimaryHierarchy =new ItemPrimaryHierarchyVO();
                        String categoryName = checkNull(row[1]);
                        String finalCategory = "";
                        String finalCategoryId = "";
                        if(categoryName != null){
                            String[] catArr = categoryName.split("///");
                            for(int i=1; i<catArr.length; i++){
                                String value = catArr[i].split("-")[1];
                                if(finalCategory == ""){
                                    finalCategory = finalCategory +  value;
                                }else{
                                    finalCategory = finalCategory + "/" +  value;
                                }
                                if(i == (catArr.length-1)){
                                    finalCategoryId = catArr[i].split("-")[0];
                                }
                            }
                        }
                        itemPrimaryHierarchy.setPetCategoryId(finalCategoryId);
                        itemPrimaryHierarchy.setPetCategoryName(finalCategory);
                        //Add each itemPrimaryHierarchy object to the list
                        iphCategoryList.add(itemPrimaryHierarchy);

                    }
                    
                }
                        
        
        }finally{
            if(session!=null)
                session.close();
        }
        LOGGER.info("ContentDAOImpl selectedIPHCategorydropdown : ends");
        return iphCategoryList;
    }
    
    /**
     * Method to get the group copy validation from database.
     *    
     * @param groupId String  
     * @param styleId String
     * @return String
     * @throws PEPServiceException
     * 
     * Method added For PIM Phase 2 - Group Content
     * Date: 06/18/2016
     * Added By: Cognizant
     */
    @Override
    public String getGroupCopyValidation(String groupId, String styleId)
            throws PEPFetchException {

        LOGGER.info("***Entering getGroupCopyValidation() method.");
        Session session = null;
        String message = ContentScreenConstants.FAILURE;
        List<Object> rows = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            Query query = session.createSQLQuery(xqueryConstants
                    .getGroupCopyValidation());
            if (query != null) {
                query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                query.setParameter(ContentScreenConstants.STYLE_ID, styleId);
                query.setParameter(ContentScreenConstants.GROUP_ID, groupId);
                rows = query.list();
            }
            int validCount =0;
            if (rows != null) {
                for (final Object rowObj : rows) {
                    final Map row = (Map) rowObj;

                validCount = row
                            .get(ContentScreenConstants.COUNT_GROUP) == null ? 0
                            : Integer.parseInt(row.get(
                                    ContentScreenConstants.COUNT_GROUP)
                                    .toString());
                    if(LOGGER.isDebugEnabled())
                    {
                        LOGGER.debug("Value --COUNT_GROUP: " + validCount);
                    }
                    
                    
                }
            }
            
            if (validCount > 0) {
                
                query = session.createSQLQuery(xqueryConstants
                        .getPETStateCopyValidation());
                if (query != null) {
                    query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                    query.setParameter(ContentScreenConstants.STYLE_ID, styleId);
                    rows = query.list();
                }
                
                if (rows != null) {
                    for (final Object rowObjPET : rows) {
                        final Map rowPET = (Map) rowObjPET;

                        String PETState = (rowPET.get(ContentScreenConstants.PET_STATE)==null?"00":rowPET.get(ContentScreenConstants.PET_STATE)).toString();

                        if(LOGGER.isDebugEnabled())
                        {
                            LOGGER.debug("Value -- PETState:" + PETState);
                        }
                        //02- Completed
                        //06-Closed
                        //07-Waiting to be Closed
                        //09-Published to Web
                        if("02".equals(PETState) || "06".equals(PETState) || "07".equals(PETState) || "09".equals(PETState)) {
                            message = ContentScreenConstants.SUCCESS;
                        }else{
                            message = ContentScreenConstants.ORIN_NOT_COMPLETED;
                        }
                    }
            }
            }
        } catch (Exception exception) {
            LOGGER.error("Exception in getGroupCopyValidation() method. -- "
                    + exception);
            throw new PEPFetchException(exception.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        LOGGER.info("***Exiting getGroupCopyValidation() method.");
        return message;
    }
    
    
    
    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getGroupGlobalAttribute(java.lang.String)
     */
    @Override
    public GlobalAttributesVO getGroupGlobalAttribute(String groupingId)
            {
        LOGGER.info("ContentDAOImp :getGroupGolbalAttribute: start" );
        Session session = null;        
        GlobalAttributesVO  groupAttribute = null;
        List<Object[]> rows=null;
        
        try {
            session = sessionFactory.openSession();
            
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(XqueryConstants.getGroupGlobalAttributes());
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug(" Query -->" +XqueryConstants.getGroupGlobalAttributes());
            }
            if(query!=null)
            {
                query.setParameter("groupingId", groupingId);
                query.setFetchSize(1);
                rows = query.list();
            }

            if(rows!=null)
            {
                for (final Object[] row : rows) {
                    groupAttribute = new GlobalAttributesVO ();

                    groupAttribute.setBelkExclusive(checkNull(row[0]));
                    groupAttribute.setChannelExclusive(checkNull(row[1]));
                    groupAttribute.setBopis(checkNull(row[2]));
                    groupAttribute.setPyg(checkNull(row[3]));
                    groupAttribute.setGwp(checkNull(row[4]));
                    groupAttribute.setPwp(checkNull(row[5]));
                    groupAttribute.setSdf(checkNull(row[6]));
                    
                }
            }


        }
       
        finally {
            if(session!=null)            
                session.close();
        }
        LOGGER.info("ContentDAOImp :getGroupGolbalAttribute: end" );
        return groupAttribute;
    }
    
    /**
     * Method to get the copy validation from database.
     *    
     * @param petCopy RegularPetCopy  
     * @return RegularPetCopy
     * @throws PEPServiceException
     * 
     * Method added For PIM Phase 2 - Group Content
     * Added By: Cognizant
     */
    @Override
    public RegularPetCopy getRegularCopyValidation(RegularPetCopy petCopy)
            throws PEPFetchException {

        LOGGER.info("***Entering getRegularCopyValidation() method.");
        Session session = null;
        
        List<Object> rows = null;
        
        try {
            session = sessionFactory.openSession();
            Query query = session.createSQLQuery(XqueryConstants.copyRegularContentValidationQuery());
            if(LOGGER.isDebugEnabled())
            {
                LOGGER.debug("Query, in DAOImpl: " + XqueryConstants.copyRegularContentValidationQuery());
            }
            if (query != null) {
                query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                query.setParameter(ContentScreenConstants.STYLE_ID, petCopy.getFromMDMId());
                rows = query.list();
            }
            if (rows != null && !rows.isEmpty()) {
            
                for (final Object rowObj : rows) {
                    final Map row = (Map) rowObj;

                    petCopy.setFromMDMId(row.get(ContentScreenConstants.MDMID).toString());
                    petCopy.setFromOrinEntryType(row.get(ContentScreenConstants.ENTRY_TYPE).toString());
                    petCopy.setSuccess(true);
                    if(LOGGER.isDebugEnabled())
                    {
                        LOGGER.debug("From MDMID to be copied, in DAOImpl: " + petCopy.getFromMDMId());
                    }
                }
            }else {
            	petCopy.setMessageToDisplay(ContentScreenConstants.REG_COPY_FAIL_INVALID);
            	LOGGER.debug(petCopy.getMessageToDisplay());
            }
            
        } catch (Exception exception) {
            LOGGER.error("Exception in getRegularCopyValidation() method. -- "
                    + exception);
            throw new PEPFetchException(exception.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        LOGGER.info("***Exiting getRegularCopyValidation() method.");
        return petCopy;
    }

}