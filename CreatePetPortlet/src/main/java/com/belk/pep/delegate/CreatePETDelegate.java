package com.belk.pep.delegate;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.json.JSONArray;

import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.service.CreatePETService;

/**
 * The Class CreatePETDelegate.
 */
public class CreatePETDelegate {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger
        .getLogger(CreatePETDelegate.class.getName());

    private CreatePETService createPETService;

    /**
     * Create PET webservice .
     * 
     * @param StyleInfo
     * @return the string
     * @throws Exception
     *             the exception
     */

    public String createPetWebService(JSONArray styleInfo) throws Exception,PEPFetchException {

        LOGGER.info("CreatePETDelegate:: createPetWebService");
        return createPETService.createPetWebService(styleInfo);

    }

    /**
     * fetch PET Details
     * 
     * @param String
     *            orinNo
     * @return UserObject
     * @throws Exception
     *             the exception
     */
    public ArrayList<WorkFlow> fetchPETDetails(String orinNo) throws Exception {

        LOGGER.info("CreatePETDelegate:: fetchUserDetails");
        return createPETService.fetchPETDetails(orinNo);
    }

    /**
     * @return the createPETService
     */
    public CreatePETService getCreatePETService() {
        return createPETService;
    }

    /**
     * @param createPETService
     *            the createPETService to set
     */
    public void setCreatePETService(CreatePETService createPETService) {
        this.createPETService = createPETService;
    }

    /**
     * Validate Orin.
     * 
     * @param String
     *            orinNumber
     * @return String
     * @throws Exception
     *             the exception
     */
    public String validateOrin(String orinNumber) throws Exception {
        LOGGER.info("CreatePETDelegate:: validateOrin");
        String orinNo = createPETService.validateOrin(orinNumber);

        return orinNo;
    }
}
