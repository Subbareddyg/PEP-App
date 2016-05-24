package com.belk.pep.delegate;

import java.util.List;

import org.apache.log4j.Logger;

import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.GroupAttributeForm;
import com.belk.pep.service.GroupingService;

/**
 * The Class ExternalVendorLoginDelegate.
 * @author AFUBXJ1
 *
 */
public class GroupingDelegate {
    
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(GroupingDelegate.class.getName());     


    private GroupingService groupingService;


    /**
     * @return the groupingService
     */
    public GroupingService getGroupingService() {
        return groupingService;
    }


    /**
     * @param groupingService the groupingService to set
     */
    public void setGroupingService(GroupingService groupingService) {
        this.groupingService = groupingService;
    }
    

    /**
     * This method is used to call Group Creation Service and fetch data from database
     * @param jsonArray
     * @param groupId
     * @returnCreateGroupForm
     * @throws Exception
     * @throws PEPFetchException
     */
    /*public CreateGroupForm saveGroupHeaderDetails(JSONObject jsonStyle, String updatedBy, List<GroupAttributeForm> selectedSplitAttributeList) 
            throws Exception, PEPFetchException {
        LOGGER.info("Entering saveGroupHeaderDetails-->");
        CreateGroupForm createGroupForm = groupingService.saveGroupHeaderDetails(jsonStyle, updatedBy, selectedSplitAttributeList);
        LOGGER.info("Exist saveGroupHeaderDetails-->");
        return createGroupForm;
    }*/
    

    /**
     * This method is used to get Color details from database
     * @param vendorStyleNo
     * @param styleOrin
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    /*public List<GroupAttributeForm> getSplitColorDetails(String vendorStyleNo, String styleOrin) throws PEPServiceException, PEPPersistencyException {
        LOGGER.info("Entering getSplitColorDetails-->");
        List<GroupAttributeForm> getSplitColorDetailsList= groupingService.getSplitColorDetails(vendorStyleNo, styleOrin);
        LOGGER.info("Exist getSplitColorDetails-->");
        return getSplitColorDetailsList;
    }*/
    

    /**
     * This method is used to get SKU details from database
     * @param vendorStyleNo
     * @param styleOrin
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    /*public List<GroupAttributeForm> getSplitSKUDetails(String vendorStyleNo, String styleOrin) throws PEPServiceException, PEPPersistencyException {
        LOGGER.info("Entering getSplitSKUDetails-->");
        List<GroupAttributeForm> getSplitColorDetailsList= groupingService.getSplitSKUDetails(vendorStyleNo, styleOrin);
        LOGGER.info("Exist getSplitSKUDetails-->");
        return getSplitColorDetailsList;
    }*/
}
