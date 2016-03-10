package com.belk.pep.delegate;

import java.util.List;
import java.util.Properties;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;

import com.belk.pep.domain.ExternalUser;
import com.belk.pep.domain.PepRole;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.service.ExternalUserService;

/**
 * The Class ExternalVendorLoginDelegate.
 * @author AFUBXJ1
 *
 */
public class ExternalVendorLoginDelegate {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(ExternalVendorLoginDelegate.class.getName()); 	
	
	
	/** The user service. */
	private ExternalUserService externalUserService;

	

	/**
	 * Gets the external user service.
	 *
	 * @return the external user service
	 */
	public ExternalUserService getExternalUserService() {
        return externalUserService;
    }

    /**
     * Sets the external user service.
     *
     * @param externalUserService the new external user service
     */
    public void setExternalUserService(ExternalUserService externalUserService) {
        this.externalUserService = externalUserService;
    }

    /**
     * Delegation for getting the user details from the service
     * @param emailAddress
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */

    public List<ExternalUser> getUserDetailsFromDataBaseByEmail(String emailAddress) throws PEPServiceException, PEPPersistencyException {
        return externalUserService.getUserDetailsFromDataBaseByEmail(emailAddress);
    }
    /**
     * Delegation for getting the role details from the service
     * @param pepRoleID
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public PepRole getRoleDetailsFromDataBaseByPepRoleId(String pepRoleID) throws PEPServiceException, PEPPersistencyException {
        // TODO Auto-generated method stub
        return externalUserService.getRoleDetailsFromDataBaseByPepRoleId(pepRoleID);
    }
    /**
     * Delegation to update the user details to the DB by invoking the service
     * @param rpEmailAddress
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public boolean updateExternalUserPasswordByEmailId(ExternalUser rpEmailAddress,Properties prop)throws PEPServiceException, PEPPersistencyException {
        return externalUserService.updateExternalUserPasswordByEmailId(rpEmailAddress,prop);
        
    }

}
