
package com.belk.pep.dao;

import com.belk.pep.model.UserObject;

/**
 * The Interface InternalPortalLoginDao.
 *
 * @author 
 */
public interface InternalPortalLoginDao {
	
	
	/**
	 * fetch Belk User Details.
	 *
	 * @param String id
	 * @return  UserObject
	 * @throws Exception the exception
	 */
	public UserObject fetchUserDetails(String Id) throws Exception;

}
