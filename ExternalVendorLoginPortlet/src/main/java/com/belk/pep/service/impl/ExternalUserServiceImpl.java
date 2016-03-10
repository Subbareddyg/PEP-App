package com.belk.pep.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;


import com.belk.pep.dao.ExternalUserLoginDAO;
import com.belk.pep.domain.ExternalUser;
import com.belk.pep.domain.PepRole;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.service.ExternalUserService;

/**
 * The Class ExternalUserServiceImpl.
 */
public class ExternalUserServiceImpl implements ExternalUserService {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(ExternalUserServiceImpl.class.getName()); 
	
	
	/** The user dao. */
    private ExternalUserLoginDAO externalUserLoginDAO;


	
	/**
     * This method will get the VP user details from the VPUSER table using the Email address
     * @param email
     * @return
	 * @throws PEPPersistencyException 
     * @throws SQLException
     */
    public List<ExternalUser> getUserDetailsFromDataBaseByEmail(String email) throws PEPServiceException, PEPPersistencyException{
        //getting the belk user details from Database
        LOGGER.info("****calling getUserDetailsFromDataBaseByEmail from ExternalUserServiceImpl****");
        ExternalUser vpUser=null;
        List<ExternalUser> listVpUser = null;
        try {
            listVpUser = externalUserLoginDAO.getUserDetailsFromDataBaseByEmail(email);
        }
        catch (PEPPersistencyException e) {
            
            LOGGER.info("Exception occurred at the Service DAO Layer");
            throw e;
        }
        
        catch (Exception e) {
              
                LOGGER.info("Exception occurred at the Service Implementation Layer");
                throw new PEPServiceException(e.getMessage());
            }
        
        return listVpUser;
       
        
    }


/**
 * This method will fetch the Role details from the DAO layer
 */

    @Override
    public PepRole getRoleDetailsFromDataBaseByPepRoleId(String pepRoleId)
        throws PEPServiceException, PEPPersistencyException {
        LOGGER.info("****calling getRoleNameFromDataBaseByRoleID from UserServiceImpl****");
        String roleName=null;
        PepRole userRole= null;
        try {
            userRole = externalUserLoginDAO.getRoleDetailsFromDataBaseByPepRoleId(pepRoleId);
           
        }
        catch (PEPPersistencyException e) {
            
            LOGGER.info("Exception occurred at the Service DAO Layer");
            throw e;
        }
        
        catch (Exception e) {
              
                LOGGER.info("Exception occurred at the Service Implementation Layer");
                throw new PEPServiceException(e.getMessage());
            }
        
        return userRole;
    }




    /**
     * Gets the external user login dao.
     *
     * @return the external user login dao
     */
    public ExternalUserLoginDAO getExternalUserLoginDAO() {
        return externalUserLoginDAO;
    }


    /**
     * Sets the external user login dao.
     *
     * @param externalUserLoginDAO the new external user login dao
     */
    public void setExternalUserLoginDAO(ExternalUserLoginDAO externalUserLoginDAO) {
        this.externalUserLoginDAO = externalUserLoginDAO;
    }


    /* (non-Javadoc)
     * @see com.belk.pep.service.ExternalUserService#updateExternalUserPasswordByEmailId(com.belk.pep.domain.ExternalUser)
     */
    @Override
    public boolean updateExternalUserPasswordByEmailId(ExternalUser vpUser,Properties prop)
        throws PEPServiceException, PEPPersistencyException {
       
        LOGGER.info("****calling updateVpUserPasswordByEmailId from ExternalUserServiceImpl****");
        boolean isUpdated = false;
        try {
            isUpdated = externalUserLoginDAO.updateExternalUserPasswordByEmailId(vpUser,prop);
        }
        catch (PEPPersistencyException e) {
            
            LOGGER.info("Exception occurred at the Service DAO Layer");
            throw e;
        }
        
        catch (Exception e) {
              
                LOGGER.info("Exception occurred at the Service Implementation Layer");
                throw new PEPServiceException(e.getMessage());
            }
        
        return isUpdated;
    }


   
	
	
}
