package com.belk.pep.service.impl;

//import java.util.logging.Logger;
import org.apache.log4j.Logger;
import com.belk.pep.dao.InternalPortalLoginDao;
import com.belk.pep.model.UserObject;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.service.InternalPortalLoginService;


/**
 * The Class InternalPortalLoginServiceImpl.
 */
public class  InternalPortalLoginServiceImpl implements InternalPortalLoginService {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(InternalPortalLoginServiceImpl.class.getName()); 


    /** The user dao. */
    private InternalPortalLoginDao userDao;


    /* (non-Javadoc)
     * @see belk.com.pep.service.UserService#save(String)
     */
    public UserObject fetchUserDetails(String Id) throws PEPServiceException {
        LOGGER.info("InternalPortalLoginServiceImpl:::fetchUserDetails");		
        UserObject userObject = new UserObject();
        try {
            userObject = (UserObject)userDao.fetchUserDetails(Id);            
        }
        catch (PEPPersistencyException e) {

            LOGGER.info("Exception occurred at the Service Implementation Layer");
        }		
        catch (Exception e) {	          
            LOGGER.info("Exception occurred at the Service Implementation Layer");
        }
        return userObject;
    }


    /**
     * @return the userDao
     */
    public InternalPortalLoginDao getUserDao() {
        return userDao;
    }


    /**
     * @param userDao the userDao to set
     */
    public void setUserDao(InternalPortalLoginDao userDao) {
        this.userDao = userDao;
    }






}
