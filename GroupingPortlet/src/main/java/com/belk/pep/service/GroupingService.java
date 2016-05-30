
package com.belk.pep.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.belk.pep.dto.ClassDetails;
import com.belk.pep.dto.DepartmentDetails;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.CreateGroupForm;
import com.belk.pep.form.GroupAttributeForm;
import com.belk.pep.form.GroupSearchForm;

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
	
	/**
     * Method to get the groups for search group.
     *    
     * @param groupSearchForm GroupSearchForm   
     * @return GroupSearchForm 
     * 
     * Method added For PIM Phase 2 - groupSearch
     * Date: 05/19/2016
     * Added By: Cognizant
     */
	GroupSearchForm groupSearch(GroupSearchForm groupSearchForm) throws PEPServiceException, PEPPersistencyException;
	
	/**
	 * Method to get Group search record count
     * @param groupSearchForm GroupSearchForm
     * @return resordCount int
     * 
     * Method added For PIM Phase 2 - groupSearch
     * Date: 05/26/2016
     * Added By: Cognizant
     * @throws PEPPersistencyException 
     * @throws PEPServiceException 
     */
    public int groupSearchCount(GroupSearchForm groupSearchForm) throws PEPPersistencyException, PEPServiceException;
    
	/**
     * Method to get the Depts for search group.
     *    
     * @param departmentsToBesearched String   
     * @return ArrayList 
     * 
     * Method added For PIM Phase 2 - groupSearch
     * Date: 05/25/2016
     * Added By: Cognizant
     * @throws PEPPersistencyException 
     */
    public ArrayList<DepartmentDetails> getDeptDetailsByDepNoFromADSE() throws PEPPersistencyException, PEPServiceException;
    
    /**
     * Method to get the classes for search group.
     *    
     * @param departmentNumbers String   
     * @return List<ClassDetails> 
     * 
     * Method added For PIM Phase 2 - groupSearch
     * Date: 05/26/2016
     * Added By: Cognizant
     * @throws PEPPersistencyException 
     */
    public List<ClassDetails> getClassDetailsByDepNos(String departmentNumbers)
    throws PEPPersistencyException, PEPServiceException;
    
    /**
     * This method is used to call Group Delete Service
     * @param groupId
     * @param updatedBy
     * @return String
     * @throws Exception
     * @throws PEPFetchException
     */
    public String deleteGroup(String groupId, String groupType, String updatedBy) 
    throws Exception, PEPFetchException;

}

