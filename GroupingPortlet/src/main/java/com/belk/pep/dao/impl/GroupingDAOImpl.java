package com.belk.pep.dao.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.belk.pep.constants.XqueryConstants;
import com.belk.pep.dao.GroupingDAO;
import com.belk.pep.dto.CreateGroupDTO;
import com.belk.pep.form.CreateGroupForm;
import com.belk.pep.form.GroupAttributeForm;

/**
 * This class responsible for handling all the DAO call to the VP Database.
 * @author AFUPYB3
 *
 */

public class GroupingDAOImpl implements GroupingDAO{
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(GroupingDAOImpl.class
        .getName()); 

    /** The session factory. */
    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }




    /** The xquery constants. */
    XqueryConstants xqueryConstants= new XqueryConstants();

    /**
     * Sets the session factory.
     *
     * @param sessionFactory
     *            the new session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    
    /**
     * This method is used to get the Group Header Details from Database.
     * @param groupId
     * @return CreateGroupForm
     * @author Cognizant
     */
    public CreateGroupDTO getGroupHeaderDetails(String groupId){
        LOGGER.info("Fetch Group Header Details--> getGroupHeaderDetails-->Start");
        LOGGER.debug("Group Id-->"+groupId);
        
        XqueryConstants xqueryConstants= new XqueryConstants();
        Session session = null;
        //Transaction tx =  null;
        CreateGroupDTO createGroupDTO = null;
          try{
            createGroupDTO = new CreateGroupDTO();
            session = sessionFactory.openSession();
            //tx = session.beginTransaction();      
           //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
            Query query = session.createSQLQuery(xqueryConstants.getGroupHeaderDetails());
            query.setParameter("groupIdSql", groupId);
            query.setFetchSize(100);
            
            LOGGER.info("Query-->getGroupHeaderDetails-->" + query);
            // execute select SQL statement
            //List<Object[]> rows = query.list();
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List<Object> rows = query.list();
            
            if (rows != null) {
                LOGGER.info("recordsFetched..." + rows.size());
                
                //for(Object[] row : rows){  
                for(Object row : rows){
                    LOGGER.info("In..." + rows.size());
                    Map rowMap = (Map)row;
                    /*String groupName=row[1]!=null?row[1].toString():null;
                    String groupDesc=row[2]!=null?row[2].toString():null;
                    String startDate=row[3]!=null?row[3].toString():null;
                    String endDate=row[4]!=null?row[4].toString():null;*/
                    String groupName=rowMap.get("GROUP_NAME") != null ? rowMap.get("GROUP_NAME").toString() : "";
                    String groupDesc=rowMap.get("Description") != null ? rowMap.get("Description").toString() : "";
                    String startDate=rowMap.get("Effective_Start_Date") != null ? rowMap.get("Effective_Start_Date").toString() : "";
                    String endDate=rowMap.get("Effective_End_Date") != null ? rowMap.get("Effective_End_Date").toString() : "";
                    LOGGER.debug("getGroupHeaderDetails.groupName-->"+groupName);
                    LOGGER.debug("getGroupHeaderDetails.startDate-->"+startDate);
                    createGroupDTO.setGroupId(groupId);
                    createGroupDTO.setGroupName(groupName);
                    createGroupDTO.setGroupDesc(groupDesc);
                    createGroupDTO.setGroupLaunchDate(startDate);
                    createGroupDTO.setEndDate(endDate);
                }
            }
          }catch(Exception e){
              e.printStackTrace();
          }
           finally
           {
               LOGGER.info("recordsFetched. getGroupHeaderDetails finally block.." );
               session.flush();
               //tx.commit();
               session.close();
           }

        LOGGER.info("Fetch Group Header Details--> getGroupHeaderDetails-->End");
        return createGroupDTO;
    }
    

    /**
     * This method is used to get the Group Component Details from Database.
     * @param groupId
     * @return CreateGroupForm
     * @author Cognizant
     */
    /*public CreateGroupForm getGroupComponentDetails(String groupId, CreateGroupForm createGroupForm){
        LOGGER.info("Fetch Group Component Details Details--> getGroupComponentDetails-->Start");
        LOGGER.debug("Group Id-->"+groupId);

        Session session = null;
        Transaction tx =  null;
        List<GroupAttributeForm> groupAttributeFormList = null;
        XqueryConstants xqueryConstants= new XqueryConstants();
          try{
            session = sessionFactory.openSession();
            tx = session.beginTransaction();      
            //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
            Query query = session.createSQLQuery(xqueryConstants.getGroupHeaderDetails());
            query.setString(1, groupId);
            query.setFetchSize(100);
            
            LOGGER.info("Query-->getGroupComponentDetails-->" + query);
            // execute select SQL statement
            List<Object[]> rows = query.list();
            if (rows != null) {
                LOGGER.info("recordsFetched..." + rows.size());
                
                for(Object[] row : rows){                         
                    String groupName=row[0]!=null?row[0].toString():null;
                    String groupDesc=row[1]!=null?row[1].toString():null;
                    String startDate=row[2]!=null?row[2].toString():null;
                    String endDate=row[3]!=null?row[3].toString():null;
                    
                    createGroupForm.setGroupId(groupId);
                    createGroupForm.setGroupName(groupName);
                    createGroupForm.setGroupDesc(groupDesc);
                    createGroupForm.setGroupLaunchDate(startDate);
                    createGroupForm.setEndDate(endDate);
                }
            }
          }catch(Exception e){
              e.printStackTrace();
          }
           finally
           {
               LOGGER.info("recordsFetched. getGroupComponentDetails finally block.." );
               session.flush();
               tx.commit();
               session.close();
           }

        LOGGER.info("Fetch Group Header Details--> getGroupComponentDetails-->End");
        return createGroupForm;
    }*/
    

    /**
     * This method is used to get the Group Attribute Details for Split Color from Database.
     * @param vendorStyleNo
     * @param styleOrin
     * @param groupAttributeFormList
     * @return
     */
    public List<GroupAttributeForm> getSplitColorDetails(String vendorStyleNo, String styleOrin){
        LOGGER.info("Fetch Split Color Details--> getSplitColorDetails-->Start");
        LOGGER.debug("Vendor Style No-->"+vendorStyleNo);
        LOGGER.debug("Style Orin No-->"+styleOrin);

        Session session = null;
        GroupAttributeForm groupAttributeForm = null;
        List<GroupAttributeForm> groupAttributeFormList = null;
        XqueryConstants xqueryConstants= new XqueryConstants();
          try{
            session = sessionFactory.openSession();
            vendorStyleNo = (null == vendorStyleNo ? null : vendorStyleNo.equals("") ? null : vendorStyleNo.equals("") ? null : vendorStyleNo.trim());
           //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
            Query query = session.createSQLQuery(xqueryConstants.getSplitColorDetails(vendorStyleNo));
            
            if(vendorStyleNo != null){
                query.setParameter("styleIdSql", vendorStyleNo);
            }else{
                query.setParameter("mdmidSql", styleOrin);
            }
            query.setFetchSize(100);
            LOGGER.info("Query-->getSplitColorDetails-->" + query);
            
            groupAttributeFormList = new ArrayList<GroupAttributeForm>();
            
            // execute select SQL statement
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List<Object> rows = query.list();
            if (rows != null) {
                LOGGER.info("recordsFetched..." + rows.size());
                
                for(Object row : rows){
                    //LOGGER.info("In..." + rows.size());
                    Map rowMap = (Map)row;
                    groupAttributeForm = new GroupAttributeForm();
                    
                    String mdmid=rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
                    String styleNo=rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
                    String productName=rowMap.get("PRODUCT_NAME") != null ? rowMap.get("PRODUCT_NAME").toString() : "";
                    String colorCode=rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
                    String colorDesc=rowMap.get("COLOR_DESC") != null ? rowMap.get("COLOR_DESC").toString() : "";
                    String isAlreadyInGroup=rowMap.get("ALREADY_IN_GROUP") != null ? rowMap.get("ALREADY_IN_GROUP").toString() : "";
                    
                    groupAttributeForm.setOrinNumber(mdmid);
                    groupAttributeForm.setStyleNumber(styleNo);
                    groupAttributeForm.setProdName(productName);
                    groupAttributeForm.setColorCode(colorCode);
                    groupAttributeForm.setColorName(colorDesc);
                    groupAttributeForm.setIsAlreadyInGroup(isAlreadyInGroup);
                    
                    groupAttributeFormList.add(groupAttributeForm);
                }
            }
          }catch(Exception e){
              e.printStackTrace();
          }
           finally
           {
               LOGGER.info("recordsFetched. getSplitColorDetails finally block.." );
               session.flush();
               session.close();
           }

        LOGGER.info("Fetch Split Color Details--> getSplitColorDetails-->End");
        return groupAttributeFormList;
    }
    

    /**
     * This method is used to get the Group Attribute Details for Split SKU from Database.
     * @param vendorStyleNo
     * @param styleOrin
     * @param groupAttributeFormList
     * @return
     */
    public List<GroupAttributeForm> getSplitSKUDetails(String vendorStyleNo, String styleOrin){
        LOGGER.info("Fetch Split Color Details--> getSplitSKUDetails-->Start");
        LOGGER.debug("Vendor Style No-->"+vendorStyleNo);
        LOGGER.debug("Style Orin No-->"+styleOrin);

        Session session = null;
        GroupAttributeForm groupAttributeForm = null;
        List<GroupAttributeForm> groupAttributeFormList = null;
        XqueryConstants xqueryConstants= new XqueryConstants();
          try{
            session = sessionFactory.openSession();
            vendorStyleNo = (null == vendorStyleNo ? null : vendorStyleNo.equals("") ? null : vendorStyleNo.equals("") ? null : vendorStyleNo.trim());
           //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
            Query query = session.createSQLQuery(xqueryConstants.getSplitSKUDetails(vendorStyleNo));

            if(vendorStyleNo != null){
                query.setParameter("styleIdSql", vendorStyleNo);
            }else{
                query.setParameter("mdmidSql", styleOrin);
            }
            query.setFetchSize(100);
            LOGGER.info("Query-->getSplitColorDetails-->" + query);
            
            groupAttributeFormList = new ArrayList<GroupAttributeForm>();
            
            // execute select SQL statement
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List<Object> rows = query.list();
            if (rows != null) {
                LOGGER.info("recordsFetched..." + rows.size());
                
                for(Object row : rows){
                    LOGGER.info("In..." + rows.size());
                    Map rowMap = (Map)row;
                    groupAttributeForm = new GroupAttributeForm();
                    
                    String mdmid=rowMap.get("MDMID") != null ? rowMap.get("MDMID").toString() : "";
                    String styleNo=rowMap.get("PRIMARYSUPPLIERVPN") != null ? rowMap.get("PRIMARYSUPPLIERVPN").toString() : "";
                    String productName=rowMap.get("NAME") != null ? rowMap.get("NAME").toString() : "";
                    String colorCode=rowMap.get("COLOR_CODE") != null ? rowMap.get("COLOR_CODE").toString() : "";
                    String colorDesc=rowMap.get("COLOR_NAME") != null ? rowMap.get("COLOR_NAME").toString() : "";
                    String sizeDesc=rowMap.get("SIZEDESC") != null ? rowMap.get("SIZEDESC").toString() : "";
                    String isAlreadyInGroup=rowMap.get("ALREADY_IN_GROUP") != null ? rowMap.get("ALREADY_IN_GROUP").toString() : "";
                    
                    groupAttributeForm.setOrinNumber(mdmid);
                    groupAttributeForm.setStyleNumber(styleNo);
                    groupAttributeForm.setProdName(productName);
                    groupAttributeForm.setColorCode(colorCode);
                    groupAttributeForm.setColorName(colorDesc);
                    groupAttributeForm.setSize(sizeDesc);
                    groupAttributeForm.setIsAlreadyInGroup(isAlreadyInGroup);
                    
                    groupAttributeFormList.add(groupAttributeForm);
                }
            }
          }catch(Exception e){
              e.printStackTrace();
          }
           finally
           {
               LOGGER.info("recordsFetched. getSplitSKUDetails finally block.." );
               session.flush();
               session.close();
           }

        LOGGER.info("Fetch Split SKU Details--> getSplitSKUDetails-->End");
        return groupAttributeFormList;
    }

}
