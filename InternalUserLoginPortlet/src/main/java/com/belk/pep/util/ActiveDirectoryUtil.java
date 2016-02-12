package com.belk.pep.util;



import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import com.belk.pep.constants.InternalPortalConstants;
import com.sun.jndi.ldap.LdapCtxFactory;
import java.util.logging.Logger;


public class ActiveDirectoryUtil  {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(ActiveDirectoryUtil.class.getName());
	private static DirContext dirContext = null;	
	
	public static DirContext getDirContext() {
		return dirContext;
	}

	public static void setDirContext(DirContext dirContext) {
		ActiveDirectoryUtil.dirContext = dirContext;
	}

	public static boolean authenticateUser(String userID, final String password)
	throws NamingException,Exception {		
		/*CONNECTION TO LDAP*/
		
	    Properties props = PropertyLoader.getPropertyLoader(InternalPortalConstants.ACTIVE_DIR);
		//LOGGER.info("props --"+props);		
		String LDAP_URL_TEST  = props.getProperty(InternalPortalConstants.LDAP_URL_TEST);
		String AD_DOMAIN_TEST = props.getProperty(InternalPortalConstants.AD_DOMAIN_TEST);
		//LOGGER.info("LDAP_URL_TEST "+InternalPortalConstants.LDAP_URL_TEST);
		
		String AD_DOMAIN = AD_DOMAIN_TEST;
		boolean validUser = false;
		Hashtable<String, String> environment = new Hashtable<String, String>();
		// holds parameters that will be passed to the AD server
		userID = userID + AD_DOMAIN;		
		environment.put(Context.INITIAL_CONTEXT_FACTORY,InternalPortalConstants.LDAP_CONTEXT);
		environment.put(Context.PROVIDER_URL, LDAP_URL_TEST);
		environment.put(Context.SECURITY_AUTHENTICATION, "simple");
		environment.put(Context.SECURITY_PRINCIPAL, userID);
		environment.put(Context.SECURITY_CREDENTIALS, password);
		environment.put(Context.REFERRAL, "follow");
		try {
			dirContext = LdapCtxFactory.getLdapCtxInstance(LDAP_URL_TEST + '/',
					environment);
			setDirContext(dirContext);
			validUser = true;
		} catch (NamingException e) {
			LOGGER.severe("Unexpected Exception :: " + e.toString());
			validUser = false;
			throw e;
		}
		catch(Exception e){
			LOGGER.severe("Inside exception");
		}
		LOGGER.info("Validate user against of LDAP---> "+validUser);
		return validUser;
	}

}
