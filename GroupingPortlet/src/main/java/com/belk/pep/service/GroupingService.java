
package com.belk.pep.service;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.CreateGroupForm;
import com.belk.pep.form.GroupAttributeForm;

/**
 * This calss responsible for service delegate
 * @author AFUPYB3
 *
 */
public interface GroupingService {
    /**
     * This method will get the Department details from the PEP_DEPARTMENT table using the pepUserId
     * @param pepUserId
     * @return
     * @throws PEPPersistencyException 
     * @throws SQLException
     */
    /*public ArrayList getSavedDepartmentDetailsByPepUserId(String pepUserId) throws PEPServiceException, PEPPersistencyException;*/
    
	public CreateGroupForm saveGroupHeaderDetails(JSONObject jsonStyle, String updatedBy, List<GroupAttributeForm> selectedSplitAttributeList) 
	        throws Exception, PEPFetchException;
	public String callCreateGroupService(JSONObject jsonStyle) throws Exception, PEPFetchException;

	public List<GroupAttributeForm> getSplitColorDetails(String vendorStyleNo, String styleOrin) throws PEPServiceException, PEPPersistencyException;
	public List<GroupAttributeForm> getSplitSKUDetails(String vendorStyleNo, String styleOrin) throws PEPServiceException, PEPPersistencyException;
	
	public List<GroupAttributeForm> validateSCGAttributeDetails(List<GroupAttributeForm> getSplitColorDetailsList) throws PEPServiceException, PEPPersistencyException;
	public List<GroupAttributeForm> validateSSGAttributeDetails(List<GroupAttributeForm> getSplitSKUDetailsList)  throws PEPServiceException, PEPPersistencyException;
	
	public List<GroupAttributeForm> getSelectedColorAttributeList(List<GroupAttributeForm> updatedSplitColorDetailsList, String[] selectedItemsArr, String defaultSelectedAttr) 
			throws PEPServiceException, PEPPersistencyException;
	public List<GroupAttributeForm> getSelectedSKUAttributeList(List<GroupAttributeForm> updatedSplitSKUDetailsList, String[] selectedItemsArr, String defaultSelectedAttr) 
			throws PEPServiceException, PEPPersistencyException;

}

