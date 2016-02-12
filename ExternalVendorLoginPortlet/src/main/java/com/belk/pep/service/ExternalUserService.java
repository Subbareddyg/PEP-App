
package com.belk.pep.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import com.belk.pep.domain.ExternalUser;
import com.belk.pep.domain.PepRole;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
/**
 * This calss responsible for service delegate
 * @author AFUBXJ1
 *
 */
public interface ExternalUserService {
	/**
	 * This method will get the VP user details from the VPUSER table using the Email address
	 * @param email
	 * @return
	 * @throws PEPPersistencyException 
	 * @throws SQLException
	 */
	public List<ExternalUser> getUserDetailsFromDataBaseByEmail(String email) throws PEPServiceException, PEPPersistencyException;
/**
 * This method will get the UserRole Mapping details from the USER_ROLE_MAPPING table using userId
 * @param userId
 * @return
 * @throws PEPPersistencyException 
 * @throws SQLException
 */
	public PepRole getRoleDetailsFromDataBaseByPepRoleId(String pepRoleId) throws PEPServiceException, PEPPersistencyException ;
		

/**
 * 	
 * @param rPvpUser
 * @return
 * @throws PEPServiceException
 * @throws PEPPersistencyException
 */
	public boolean updateExternalUserPasswordByEmailId(ExternalUser externalUser,Properties prop)throws PEPServiceException, PEPPersistencyException;

}
