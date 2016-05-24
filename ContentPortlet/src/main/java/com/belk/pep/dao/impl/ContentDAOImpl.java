
package com.belk.pep.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Properties;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.belk.pep.constants.SqueryConstants;
import com.belk.pep.constants.XqueryConstants;
import com.belk.pep.dao.ContentDAO;
import com.belk.pep.exception.checked.PEPFetchException;
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
import com.belk.pep.vo.ItemPrimaryHierarchyVO;
import com.belk.pep.vo.OmniChannelBrandVO;
import com.belk.pep.vo.PetAttributeVO;
import com.belk.pep.vo.ProductDetailsVO;
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
        Session session = null;
        Transaction transaction = null;
        List<CarBrandVO> listCarBrandVO=null;
        CarBrandVO carBrandVO = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
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
            /*
            if(rows!=null)
            {listCarBrandVO = new ArrayList<CarBrandVO>();
            for (final Object[] row : rows) {
                String carBrand = checkNull(row[0]);
                String selectedBrand = checkNull(row[1]);
                if(carBrand != null){
                    String temp  = carBrand.replace('|', '~');
                    String carBrands[] = temp.split("~");
                    if(carBrands != null){
                        for(int i=0; i<(carBrands.length); i++){
                            String carsDes[] = carBrands[i].split("-");
                            carBrandVO = new CarBrandVO();
                            carBrandVO.setCarBrandCode(checkNull(carsDes[0]));
                            carBrandVO.setCarBrandDesc(checkNull(carsDes[1]));
                            carBrandVO.setSelectedBrand(checkNull(selectedBrand));
                            listCarBrandVO.add(carBrandVO);
                        }
                    }
                }
            }
            }*/
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
                for(CarBrandVO carBrand: listCarBrandVO)
                {
                    carBrand.setSelectedBrand(selectedBrand);
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
            transaction.commit();
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
        Transaction transaction = null;
        StyleColorFamilyVO styleColor = null;

        List<StyleColorFamilyVO> styleColorList=new ArrayList<StyleColorFamilyVO>();

        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
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
            transaction.commit();
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
        Transaction transaction = null;
        ContentHistoryVO  contentHistory = null;
        List<Object[]> rows=null;
        List<ContentHistoryVO> contentHistoryList = null;

        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();
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
            transaction.commit();
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
        Transaction transaction = null;
        ContentManagementVO  contentManagement = null;
        List<Object[]> rows=null;

        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();
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
            transaction.commit();
            session.close();
        }
        return contentManagement;
    }





    @Override
    public List<CopyAttributesVO> getCopyAttributesFromADSE(String orinNumber)
            throws PEPFetchException {

        Session session = null;
        Transaction transaction = null;
        List<CopyAttributesVO>  copyAttributeList = null;
        List<Object[]> rows=null;
        CopyAttributesVO copyAttributes = new CopyAttributesVO();

        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();
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
            transaction.commit();
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
        Transaction transaction = null;
        ItemPrimaryHierarchyVO iph = null;
        List<ItemPrimaryHierarchyVO> iphList=new ArrayList<ItemPrimaryHierarchyVO>();
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
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
            transaction.commit();
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



    @Override
    public List<ItemPrimaryHierarchyVO> getIPHCategories(String orinNumber)
            throws PEPFetchException {
        // TODO Auto-generated method stub
        return null;
    }


    /**
     * Gets the IPH categories from adse.
     *
     * @param orinNumber the orin number
     * @return the IPH categories from adse
     * @throws PEPFetchException the PEP fetch exception
     */
    public GlobalAttributesVO getIPHCategoriesFromADSE(String orinNumber)
            throws PEPFetchException {

        Session session = null;
        Transaction transaction = null;
        GlobalAttributesVO  styleAttributes = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getGlobalAttributesQuery(orinNumber));
            if(query!=null)
            {
                query.setParameter("orinNo", orinNumber);
                query.setFetchSize(20);
                rows = query.list();
            }

            if(rows!=null)
            {
                for (final Object[] row : rows) {
                    styleAttributes = new GlobalAttributesVO ();


                    styleAttributes.setMdmid(checkNull(row[0]));
                    styleAttributes.setOmniChannelBrand(checkNull(row[1]));
                    styleAttributes.setOmniChannelBrandXpath(checkNull(row[2]));
                    styleAttributes.setProductDescription(checkNull(row[3]));
                    styleAttributes.setProductDescriptionXpath(checkNull(row[4]));
                    styleAttributes.setLaunchDate(checkNull(row[5]));
                    styleAttributes.setLaunchDateXpath(checkNull(row[6]));
                    styleAttributes.setBelkExclusive(checkNull(row[7]));
                    styleAttributes.setBelkExclusiveXpath(checkNull(row[8]));
                    styleAttributes.setChannelExclusive(checkNull(row[9]));
                    styleAttributes.setBelkExclusiveXpath(checkNull(row[10]));
                    styleAttributes.setSdf(checkNull(row[11]));
                    styleAttributes.setSdfXpath(checkNull(row[12]));
                    styleAttributes.setProductDimensionUom(checkNull(row[13]));
                    styleAttributes.setProductDimensionUomXpath(checkNull(row[14]));
                    styleAttributes.setProductLength(checkNull(row[15]));
                    styleAttributes.setProductLengthXpath(checkNull(row[16]));
                    styleAttributes.setProductHeight(checkNull(row[17]));
                    styleAttributes.setProductHeightXpath(checkNull(row[18]));
                    styleAttributes.setBopis(checkNull(row[19]));
                    styleAttributes.setBopisXpath(checkNull(row[20]));
                    styleAttributes.setGiftBox(checkNull(row[21]));
                    styleAttributes.setGiftBoxXpath(checkNull(row[22]));
                    styleAttributes.setImportDomestic(checkNull(row[23]));
                    styleAttributes.setImportDomesticXpath(checkNull(row[24]));
                    styleAttributes.setProductWeightUom(checkNull(row[25]));
                    styleAttributes.setProductWeightUomXpath(checkNull(row[26]));
                    styleAttributes.setProductWeight(checkNull(row[27]));
                    styleAttributes.setProductWeightXpath(checkNull(row[28]));





                }
            }


        }
        finally {
            session.flush();
            transaction.commit();
            session.close();
        }
        return styleAttributes;
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
        Transaction transaction = null;
        List<Object[]> rows=null;
        ItemPrimaryHierarchyVO itemPrimaryHierarchy = null;
        List<ItemPrimaryHierarchyVO> iphCategoryList = null;

        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();
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
            transaction.commit();
            session.close();
        }
        LOGGER.info("end of getIPHCategoriesFromAdseMerchandiseHierarchy........");
        return iphCategoryList;
    }



    @Override
    public List<ItemPrimaryHierarchyVO> getIPHCategoriesFromADSEPetCatalog(String orinNumber)
            throws PEPFetchException {

        Session session = null;
        Transaction transaction = null;
        List<Object[]> rows=null;
        ItemPrimaryHierarchyVO itemPrimaryHierarchy = null;
        List<ItemPrimaryHierarchyVO> iphCategoryList = null;

        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();
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
            transaction.commit();
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
        Transaction transaction = null;
        List<OmniChannelBrandVO> listOmniChannelBrandVO=null;
        OmniChannelBrandVO omniChannelBrandVO = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
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
            /*
            if(rows!=null)
            {listOmniChannelBrandVO = new ArrayList<OmniChannelBrandVO>();
            for (final Object[] row : rows) {
                String omniBrand = checkNull(row[0]);
                String selectedBrand = checkNull(row[1]);
                if(omniBrand != null){
                    String temp  = omniBrand.replace('|', '~');
                    String omniBrands[] = temp.split("~");
                    if(omniBrands != null){
                        for(int i=0; i<(omniBrands.length); i++){
                            String omniDes[] = omniBrands[i].split("-");
                            omniChannelBrandVO = new OmniChannelBrandVO();
                            omniChannelBrandVO.setOmniChannelBrandCode(checkNull(omniDes[0]));
                            omniChannelBrandVO.setOmniChannelBrandDesc(checkNull(omniDes[1]));
                            omniChannelBrandVO.setSelectedBrand(checkNull(selectedBrand));
                            listOmniChannelBrandVO.add(omniChannelBrandVO);
                        }
                    }
                }
            }
            }*/
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
                    
                 //   if(omniBrandCode.equals(selectedBrandStyle) 
                 //           || omniBrandCode.equals(selectedBrandComplexPack))
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
            transaction.commit();
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
        Transaction transaction = null;
        List<Object[]> rows=null;
        new XqueryConstants();
        List<PetAttributeVO> petAttributeList=null;
        //final String categoryIdDummy ="4630";
        try {
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();

            final Query query =session.createSQLQuery(SqueryConstants.getProductAttributes(categoryId,orinNumber));
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
                    petAttributesObject.setSecondarySpecValue(checkNull(row[1]));
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
            transaction.commit();
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
        Transaction transaction = null;
        List<Object[]> rows=null;
        new XqueryConstants();
        List<BlueMartiniAttributesVO> blueMartiniAttributesList=null;
        try {
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();

            final Query query =session.createSQLQuery(SqueryConstants.getBlueMartiniAttributes(categoryId, orinNumber));
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

                    // LOGGER.info("row[14]....."+row[14]);
                    final BlueMartiniAttributesVO blueMartiniAttributeObject = new BlueMartiniAttributesVO();
                    blueMartiniAttributeObject.setMdmId(checkNull(row[0]));
                    blueMartiniAttributeObject.setZbmSecondarySpecValue(checkNull(row[1]));
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
            transaction.commit();
            session.close();
        }
        return blueMartiniAttributesList;



    }


    @Override
    public ProductDetailsVO getProductInfoFromADSE(String orinNumber)
            throws PEPFetchException {

        Session session = null;
        Transaction transaction = null;
        ProductDetailsVO  productDetails = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();
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
            transaction.commit();
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
        Transaction transaction = null;
        SkuAttributesVO  skuAttributes = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();
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
            transaction.commit();
            session.close();
        }
        return skuAttributes;
    }

    @Override
    public List<ChildSkuVO> getSkusFromADSE(String orinNumber)
            throws PEPFetchException {

        Session session = null;
        Transaction transaction = null;
        ChildSkuVO  sku = null;
        List<Object[]> rows=null;
        List<ChildSkuVO> skuList=null;

        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();
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
            transaction.commit();
            session.close();
        }
        return skuList;
    }

    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getStyleAndItsChildFromADSE(java.lang.String)
     */
    @Override
    public List<PetsFound> getStyleAndItsChildFromADSE(String orinNumber)
            throws PEPFetchException {
        // TODO Auto-generated method stub
        return null;
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
        Transaction transaction =  null;

        try{
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();
            //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.
            final Query query = session.createSQLQuery(xqueryConstants.getStyleAndStyleColorAndSKU(roleName, orinNumber));
            if(query != null){
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
            transaction.commit();
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
        Transaction transaction = null;
        GlobalAttributesVO  styleAttributes = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();
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
                }
            }


        }
        catch(final Exception exception)
        {
            throw  new PEPFetchException(exception);
        }
        finally {
            session.flush();
            transaction.commit();
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
        Transaction transaction = null;
        ColorAttributesVO  styleColorAttributes = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();
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
            transaction.commit();
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
        Transaction transaction = null;
        StyleInformationVO style = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            final Query query =session.createSQLQuery(xqueryConstants.getStyleInformation(orinNumber));
            if(query!=null)
            {
            //    query.setParameter("orinNo", orinNumber);
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
            transaction.commit();
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
                        copyAttributeVO.setProductCopyText(clobToString(clob));
                    }
                    else
                    {
                        copyAttributeVO.setProductCopyText("");
                    }
                    copyAttributeVO.setCopyLine1(checkNull(row[2]));
                    copyAttributeVO.setCopyLine2(checkNull(row[3]));
                    copyAttributeVO.setCopyLine3(checkNull(row[4]));
                    copyAttributeVO.setCopyLine4(checkNull(row[5]));
                    copyAttributeVO.setCopyLine5(checkNull(row[6]));
                    copyAttributeVO.setCopyProductName(checkNull(row[7]));
                    copyAttributeVO.setMaterial(checkNull(row[8]));
                    copyAttributeVO.setCare(checkNull(row[9]));
                    copyAttributeVO.setCountryOfOrigin(checkNull(row[10]));
                    copyAttributeVO.setExclusive(checkNull(row[11]));
                    copyAttributeVO.setCaprop65Compliant(checkNull(row[12]));
                    
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
                        "\nCAPROP65 COMPLIANT: " + copyAttributeVO.getCaprop65Compliant());
                }
            }
        }
        catch(final Exception exception)
        {
            LOGGER.error("Exception in fetchCopyAttributes() method. -- " + exception.getMessage());
            throw new PEPFetchException(exception);
        }
        finally {
            session.flush();            
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

            String line;
            while(null != (line = br.readLine())) {
                sb.append(line);
            }
            br.close();
        } catch (SQLException e) {
            LOGGER.error("Exception in clobToString() method. -- " + e.getMessage());
            throw new PEPFetchException(e);
        } catch (IOException e) {
            LOGGER.error("Exception in clobToString() method. -- " + e.getMessage());
            throw new PEPFetchException(e);
        }
        LOGGER.info("***Exiting clobToString() method.");
        return sb.toString();
    }
}