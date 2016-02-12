package com.belk.pep.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.belk.pep.dao.InternalPortalLoginDao;
import com.belk.pep.domain.BelkUser;
import com.belk.pep.domain.PepRole;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.model.UserObject;

/**
 * 
 * The Class InternalPortalLoginDaoImpl.
 */
public class InternalPortalLoginDaoImpl implements InternalPortalLoginDao {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(InternalPortalLoginDaoImpl.class
        .getName());

    /** The session factory. */
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	/**
     * Sets the session factory.
     *
     * @param sessionFactory
     *            the new session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public UserObject fetchUserDetails(String Id) throws PEPPersistencyException {

        LOGGER.info("InternalPortalLoginDaoImpl:: fetchUserDetails");
        if(getSessionFactory()!=null){
        	  LOGGER.info("InternalPortalLoginDaoImpl:: fetchUserDetails getSessionFactory");
        }
        
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        UserObject belkUserObj = null;
        try{

        LOGGER.info("InternalPortalLoginDaoImpl::fetchUserDetails::userQuery ");
        /* Retrieve Belk User Details */
        if(StringUtils.isNotBlank(Id)) {     	
        	
        		        
	        Query query = session.getNamedQuery("BelkUser.findbelkUser");          
            query.setString("1", Id.toLowerCase());
            
            BelkUser belkUser = null;
			
            List<BelkUser> listbelkUser = query.list();
            if(listbelkUser.size()>0){
            	belkUser = listbelkUser.get(0);
                LOGGER.info("@getBelkId="+belkUser.getBelkId());
                LOGGER.info("@getPepRoleId="+belkUser.getPepRole().getPepRoleId());
            

	        LOGGER.info("InternalPortalLoginDaoImpl::fetchUserDetails::pepRoleQuery "+belkUser.getBelkId());
	        /* Retrieve Pep Role Details */
	        
	        Query pepRoleQuery = session.getNamedQuery("PepRole.select")       
	        .setString("pepRoleId", belkUser.getPepRole().getPepRoleId());
	        PepRole pepRole = (PepRole) pepRoleQuery.uniqueResult(); 		
	        LOGGER.info("InternalPortalLoginDaoImpl::fetchUserDetails::saveUser");       
	      
	        LOGGER.info("InternalPortalLoginDaoImpl::fetchUserDetails::Transaction successfull");		
	         belkUserObj = new UserObject();
	        /* populate User Object */
	         belkUserObj =(UserObject)populateUser(belkUser,belkUserObj);
	        /* populate User Object */
	        belkUserObj =(UserObject)populatePepUser(pepRole,belkUserObj);
            }
        }
       
        }catch(Exception e){
        	 LOGGER.info("Exception ocurred..."+e.getMessage());
        }
        finally
        {
        	LOGGER.info("in finally block ");
        	session.flush();
            tx.commit();
            //session.clear();
            session.close();
           
        } 
        LOGGER.info("belkUserObj ..."+belkUserObj);
        return belkUserObj;
    }


    /*Populate user Object from  belk user entity bean */
    private UserObject populateUser(BelkUser belkUser,UserObject userObj)
    {
        LOGGER.info("populate User");
        String lanId = belkUser.getBelkId();
        if(StringUtils.isNotBlank(lanId))
        {	
        	 LOGGER.info("LAN_ID"+lanId ); 
        	 userObj.setLanID(lanId);
        }
         
        if(StringUtils.isNotBlank(belkUser.getBelkEmail()))
        {	
        	userObj.setEmailId(belkUser.getBelkEmail());
        }
                
        if(StringUtils.isNotBlank(belkUser.getPepRole().getPepRoleId()))
        {	
        	userObj.setPepRoleId(belkUser.getPepRole().getPepRoleId());        	
        }
        		        
        return userObj;
    }

    /*Populate user Object from  PepRole entity bean */
    private UserObject populatePepUser(PepRole pepRole,UserObject userObj)
    {
        LOGGER.info("populate User");
        String lanId = pepRole.getPepRoleId();
        userObj.setPepRoleStatus(pepRole.getRoleStatus());
        userObj.setRoleAccessType(pepRole.getAccessType());
        userObj.setUserType(pepRole.getUserType());                        
        return userObj;
    }


}
