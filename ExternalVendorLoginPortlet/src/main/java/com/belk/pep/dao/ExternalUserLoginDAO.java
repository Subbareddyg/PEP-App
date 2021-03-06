
package com.belk.pep.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import com.belk.pep.domain.ExternalUser;
import com.belk.pep.domain.PepRole;
import com.belk.pep.exception.checked.PEPPersistencyException;
/**
 * This class responsible for handling all the DAO call to the VP Database
 * @author AFUBXJ1
 *
 */

public  interface ExternalUserLoginDAO {
	/**
	 * This method will get the VP user details from the VPUSER table using the Email address
	 * @param emailAddress
	 * @return
	 * @throws SQLException
	 */
	public List<ExternalUser> getUserDetailsFromDataBaseByEmail(String emailAddress) throws PEPPersistencyException ;
	
	
	

/**
 * This method will get the UserRole details from the USER_ROLE table using roleId
 * @param roleId
 * @return
 * @throws SQLException
 */
	public PepRole getRoleDetailsFromDataBaseByPepRoleId(String pepRoleID) throws PEPPersistencyException ;

/**
 * Update external user password by email id.
 *
 * @param rpEmailAddress the rp email address
 * @return true, if successful
 * @throws PEPPersistencyException the PEP persistency exception
 */


public boolean updateExternalUserPasswordByEmailId(ExternalUser rpEmailAddress,Properties props)throws PEPPersistencyException ;


}
