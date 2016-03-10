package com.belk.pep.delegate;

//import java.util.logging.Logger;
import org.apache.log4j.Logger;
import com.belk.pep.model.UserObject;
import com.belk.pep.service.InternalPortalLoginService;


/**
 * The Class InternalPortalLoginDelegate.
 */
public class InternalPortalLoginDelegate {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(InternalPortalLoginDelegate.class.getName()); 	


	/** The user service. */
	private InternalPortalLoginService userService;

	/**
	 * Gets the user service.
	 *
	 * @return the userService
	 */
	public InternalPortalLoginService getUserService() {
		return userService;
	}

	/**
	 * Sets the user service.
	 *
	 * @param InternalPortalLoginService the userService to set
	 */
	public void setUserService(InternalPortalLoginService userService) {
		this.userService = userService;
	}

	/**
	 * fetch Belk User Access and role.
	 *
	 * @param p the p
	 * @return the string
	 * @throws Exception the exception
	 */
	public UserObject fetchUserDetails(String Id) throws Exception
	{

		LOGGER.info("InternalPortalLoginDelegate:: fetchUserDetails");		
		return userService.fetchUserDetails(Id);		  
	}
	
}
