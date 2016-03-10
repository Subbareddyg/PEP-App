
package com.belk.pep.dao.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.belk.pep.constants.XqueryConstants;
import com.belk.pep.dao.AttributeMaintenanceDAO;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.vo.AttributeDetailsVO;
import com.belk.pep.vo.AttributeSearchVO;
import com.belk.pep.vo.AttributeValueListVO;
import com.belk.pep.vo.AttributeValueVO;
import com.belk.pep.vo.CategoryListVO;

/**
 * The Class ContentDAOImpl.
 */
public class AttributeMaintenanceDAOImpl implements AttributeMaintenanceDAO{
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(AttributeMaintenanceDAOImpl.class
        .getName());

    /** The session factory. */
    private SessionFactory sessionFactory;

  
    /**
     * Sets the session factory.
     *
     * @param sessionFactory the new session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Gets the session factory.
     *
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

   
    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getStyleInfoFromADSE(java.lang.String)
     */
    @Override
    public List<AttributeDetailsVO> getAttributeListForSearchCriteria(AttributeSearchVO attributeSearchVO)
        throws PEPFetchException {
        Session session = null;
        Transaction transaction = null;
        List<Object[]> rows=null;
        List<AttributeDetailsVO>  lstAttributeDetails = null;
        AttributeDetailsVO attributeDetails = null;
        XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            Query query =session.createSQLQuery(xqueryConstants.getAttributeDetailList(attributeSearchVO));
            if(query!=null)
            {
                 rows = query.list();
            }
            
            if(rows!=null)
             { lstAttributeDetails = new ArrayList<AttributeDetailsVO>();
                for (Object[] row : rows) {
                    attributeDetails = new AttributeDetailsVO();
                    attributeDetails.setCatagoryId(checkNull(row[0]));
                    attributeDetails.setAttributeId(checkNull(row[1]));
                    attributeDetails.setAttributeName(checkNull(row[2]));
                    attributeDetails.setAttributeFieldType(checkNull(row[3]));
                    
                    // Adding to ArrayList
                    lstAttributeDetails.add(attributeDetails);
                } 
            }

        }catch(Exception e) {
            e.printStackTrace();
            throw new PEPFetchException(e);
        }
        finally {
            session.flush();
            transaction.commit();
            session.close();
        }
        return lstAttributeDetails;
    }
   
    /* (non-Javadoc)
     * @see com.belk.pep.dao.ContentDAO#getStyleInfoFromADSE(java.lang.String)
     */
    @Override
    public List<CategoryListVO> getCategoryList()
        throws PEPFetchException {
        Session session = null;
        Transaction transaction = null;
        List<Object[]> rows=null;
        List<CategoryListVO>  lstCategoryListVO = null;
        CategoryListVO categoryListVO = null;
        XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            Query query =session.createSQLQuery(xqueryConstants.getIPHListQuery());
            if(query!=null)
            {
                 rows = query.list();
            }
            
            if(rows!=null)
             { lstCategoryListVO = new ArrayList<CategoryListVO>();
                for (Object[] row : rows) {
                    categoryListVO = new CategoryListVO();
                    categoryListVO.setCategoryId(checkNull(row[0]));
                    categoryListVO.setCategoryName(checkNull(row[1]));
                    
                    lstCategoryListVO.add(categoryListVO);
                } 
            }
            

        }catch(Exception e) {
            e.printStackTrace();
            throw new PEPFetchException(e);
        }
        finally {
            session.flush();
            transaction.commit();
            session.close();
        }
        return lstCategoryListVO;
    }
    
    @Override
    public int updateAttributeDetails(AttributeValueVO attributeValueVO)
        throws PEPFetchException {
        int updateStaus = -1;
        Session session = null;
        Transaction transaction = null;
        
        XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            Query query =
                session.createSQLQuery(xqueryConstants
                    .updateAttributeDetails(attributeValueVO));
            
            updateStaus = query.executeUpdate();
            System.out.println(" Update Status "+updateStaus);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new PEPFetchException(e);
        }
        finally {
            session.flush();
            transaction.commit();
            session.close();
        }
        return updateStaus;
    }
    
   
    /* (non-Javadoc)
     * @see com.belk.pep.dao.AttributeMaintenanceDAO#getAttributeDetailsForCatIdAttId(java.lang.String, java.lang.String)
     */
    @Override
    public AttributeValueVO getAttributeDetailsForCatIdAttId(String categoryId, String attributeId)
        throws PEPFetchException {
        Session session = null;
        Transaction transaction = null;
        List<Object[]> rows=null;
        AttributeValueVO attributeValueVO = null;
        AttributeValueListVO attributeValueListVO = null;
        List<AttributeValueListVO> attributeValueList = null;
        String attributeValueSet = "";
        
                
        XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            // Hibernate provides a createSQLQuery method to let you call your
            // native SQL statement directly.
            Query query =session.createSQLQuery(xqueryConstants.getAttributeDetailQuery(categoryId,attributeId));
            if(query!=null)
            {
                 rows = query.list();
            }
            
            if(rows!=null)
             { 
                for (Object[] row : rows) {
                    attributeValueVO = new AttributeValueVO();
                    attributeValueVO.setCategoryId(checkNull(row[0]));
                    attributeValueVO.setAttributeId(checkNull(row[1]));
                    attributeValueVO.setAttributeFieldType(checkNull(row[2]));
                    attributeValueVO.setAttributeName(checkNull(row[3]));
                    attributeValueVO.setAttributeDisplayName(checkNull(row[4]));
                    attributeValueVO.setAttributeStatus(checkNull(row[5]));
                    attributeValueVO.setIsSearchable(checkNull(row[6]));
                    attributeValueVO.setIsEditable(checkNull(row[7]));
                    attributeValueVO.setIsRequired(checkNull(row[8]));
                    attributeValueSet = (checkNull(row[9]));
                    
                    // Get Attribute List Values
                   /* query =session.createSQLQuery(xqueryConstants.getAttributeValueList(categoryId,attributeId));
                    if(query!=null)
                    {
                         rows = query.list();
                    }*/
                    System.out.println("Outside Loop--------------------------------> "+attributeValueSet);
                    String attributeType = attributeValueVO.getAttributeFieldType();
                    if(attributeType != null && attributeType.equalsIgnoreCase("Drop Down")){
                        if(attributeValueSet != null){
                            attributeValueList = new ArrayList<AttributeValueListVO>();
                            String valueList  = attributeValueSet.replace('|', '~');
                            String attributeValSet[] = valueList.split("~");
                            if(attributeValSet != null){
                                int length = attributeValSet.length;
                                for(int i=0; i<length; i++){
                                    System.out.println("Inside Loop--------------------------------> "+attributeValSet[i]);
                                    attributeValueListVO = new AttributeValueListVO();
                                    attributeValueListVO.setAttributeFieldValueSeq((i+1)+"");
                                    attributeValueListVO.setAttributeFieldValue(attributeValSet[i]);
                                    // Adding to ArrayList
                                    attributeValueList.add(attributeValueListVO);
                                }
                            }
                            attributeValueVO.setAttributeValueList(attributeValueList);
                        } 
                    }
                    else if (attributeType != null
                        && attributeType.equalsIgnoreCase("Radio Button")) {
                        String valueList  = attributeValueSet.replace('|', '~');
                        String attributeValSet[] = valueList.split("~");
                        attributeValueVO.setRadioFirstValue(attributeValSet[0]);
                        attributeValueVO.setRadioSecondValue(attributeValSet[1]);
                    }
                    else if (attributeType != null
                        && attributeType.equalsIgnoreCase("Check Boxes")) {
                        String valueList  = attributeValueSet.replace('|', '~');
                        String attributeValSet[] = valueList.split("~");
                        attributeValueVO.setCheckboxFirstValue(attributeValSet[0]);
                        attributeValueVO.setCheckboxSecondValue(attributeValSet[1]);
                    }
                    else {
                        attributeValueVO
                            .setAttributeFieldValue(attributeValueSet);
                    }
                    
                    
                   /* if(rows!=null)
                     { attributeValueList = new ArrayList<AttributeValueListVO>();
                     
                        for (Object[] valueRow : rows) {
                            attributeValueListVO = new AttributeValueListVO();
                            attributeValueListVO.setAttributeFieldValueSeq(checkNull(valueRow[0]));
                            attributeValueListVO.setAttributeFieldValue(checkNull(valueRow[1]));
                            // Adding to ArrayList
                            attributeValueList.add(attributeValueListVO);
                        } 
                    }
                    attributeValueVO.setAttributeValueList(attributeValueList);*/
                } 
            }

        }catch(Exception e) {
            e.printStackTrace();
            throw new PEPFetchException(e);
        }
        finally {
            session.flush();
            transaction.commit();
            session.close();
        }
        return attributeValueVO;
    }
    
 
    
    
    
    /**
     * Check null.
     *
     * @param objectValue the object value
     * @return String
     */
    public String checkNull(Object objectValue){
        String valueStr = "";
        
        if(objectValue == null ){
            System.out.println("row object is null, setting to default value");
            
            valueStr = "No Data";
            
        }
        else
        {
            valueStr = objectValue.toString();
        }
        
        return valueStr;
    }

}
