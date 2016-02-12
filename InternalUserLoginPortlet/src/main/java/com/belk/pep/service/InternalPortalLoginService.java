/**
 * 
 */
package com.belk.pep.service;

import com.belk.pep.model.UserObject;


/**
 * The Interface InternalPortalLoginService.
 *
 * @author 
 */
public interface InternalPortalLoginService {	

	
	/**
	 * fetch Belk User Details.
	 *
	 * @param String id
	 * @return  UserObject
	 * @throws Exception the exception
	 */
	public UserObject fetchUserDetails(String Id) throws Exception;

}
