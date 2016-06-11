package com.belk.pep.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.belk.pep.dto.ClassDetails;
import com.belk.pep.dto.DepartmentDetails;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.CreateGroupForm;
import com.belk.pep.form.GroupAttributeForm;
import com.belk.pep.form.GroupSearchForm;
import com.belk.pep.form.StyleAttributeForm;

/** This calss responsible for service delegate
 * 
 * @author AFUPYB3 */
public interface GroupingService {

	/** This method is used to create group
	 * 
	 * @param jsonStyle
	 * @param updatedBy
	 * @param selectedSplitAttributeList
	 * @return CreateGroupForm
	 * @throws Exception
	 * @throws PEPFetchException */
	CreateGroupForm saveGroupHeaderDetails(JSONObject jsonStyle, String updatedBy, List<GroupAttributeForm> selectedSplitAttributeList)
			throws Exception, PEPFetchException;

	/** This method is used to call create group service
	 * 
	 * @param jsonStyle
	 * @return String
	 * @throws Exception
	 * @throws PEPFetchException */
	String callCreateGroupService(JSONObject jsonStyle) throws Exception, PEPFetchException;

	/** This method is used to get Component details for Split Color
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @return List<GroupAttributeForm>
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException */
	List<GroupAttributeForm> getSplitColorDetails(String vendorStyleNo, String styleOrin) throws PEPFetchException, PEPServiceException,
			PEPPersistencyException;

	/** This method is used to get Component details for CPG
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @return List<GroupAttributeForm>
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException */
	List<StyleAttributeForm> getNewCPGDetails(String vendorStyleNo, String styleOrin, String deptNoSearch, String classNoSearch,
			String supplierSiteIdSearch, String upcNoSearch, String groupId) throws PEPServiceException,
			PEPPersistencyException;

	/** This method is used to get Component details for Split SKU
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @return List<GroupAttributeForm>
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException */
	List<GroupAttributeForm> getSplitSKUDetails(String vendorStyleNo, String styleOrin) throws PEPServiceException,
			PEPPersistencyException;

	/** This method is used to validate SCG attribute list before add to group
	 * 
	 * @param getSplitColorDetailsList
	 * @return List<GroupAttributeForm>
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException */
	Map<String, List<GroupAttributeForm>> validateSCGAttributeDetails(List<GroupAttributeForm> getSplitColorDetailsList)
			throws PEPServiceException, PEPPersistencyException;

	/** This method is used to validate SCG attribute list before add to group
	 * 
	 * @param getSplitSKUDetailsList
	 * @return List<GroupAttributeForm>
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException */
	Map<String, List<GroupAttributeForm>> validateSSGAttributeDetails(List<GroupAttributeForm> getSplitSKUDetailsList)
			throws PEPServiceException, PEPPersistencyException;
	
	/**
	 * This method is used to validate CPG attribute list before add to group
	 * @param existClassId
	 * @param getCPGSelectedAttrbuteList
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	String validateCPGAttributeDetails(String existClassId, List<StyleAttributeForm> getCPGSelectedAttrbuteList)
					throws PEPServiceException, PEPPersistencyException;
	

	/** This method is used to get the attribute list which is selected by user
	 * before to add SCG group
	 * 
	 * @param updatedSplitColorDetailsList
	 * @param selectedItemsArr
	 * @param defaultSelectedAttr
	 * @return List<GroupAttributeForm>
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException */
	List<GroupAttributeForm> getSelectedColorAttributeList(List<GroupAttributeForm> updatedSplitColorDetailsList,
			String[] selectedItemsArr, String defaultSelectedAttr) throws PEPServiceException, PEPPersistencyException;

	/** This method is used to get the attribute list which is selected by user
	 * before to add SSG group
	 * 
	 * @param updatedSplitSKUDetailsList
	 * @param selectedItemsArr
	 * @param defaultSelectedAttr
	 * @return List<GroupAttributeForm>
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException */
	List<GroupAttributeForm> getSelectedSKUAttributeList(List<GroupAttributeForm> updatedSplitSKUDetailsList,
			String[] selectedItemsArr, String defaultSelectedAttr) throws PEPServiceException, PEPPersistencyException;
	

	/** This method is used to get the attribute list which is selected by user
	 * before to add CPG group
	 * 
	 * @param updatedSplitSKUDetailsList
	 * @param selectedItemsArr
	 * @param defaultSelectedAttr
	 * @return List<GroupAttributeForm>
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException */
	List<StyleAttributeForm> getSelectedCPGAttributeList(final List<StyleAttributeForm> getCPGDetailsList,
			final String[] selectedItemsArr, final String defaultSelectedAttr) throws PEPServiceException, PEPPersistencyException;

	/** This method is used to Existing Group details from DB
	 * 
	 * @param groupId
	 * @return CreateGroupForm
	 * @throws Exception
	 * @throws PEPFetchException */
	CreateGroupForm getExistingGrpDetails(String groupId) throws Exception, PEPFetchException;

	/** This method is used to get the Existing Style Color details.
	 * 
	 * @param groupId
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException */
	List<GroupAttributeForm> getExistSplitColorDetails(String groupId) throws PEPServiceException, PEPPersistencyException;

	/** This method is used to get the Existing Style Sku details.
	 * 
	 * @param groupId
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException */
	List<GroupAttributeForm> getExistSplitSkuDetails(String groupId) throws PEPServiceException, PEPPersistencyException;

	/** This method is used to get the Existing CPG details.
	 * 
	 * @param groupId
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException */
	List<StyleAttributeForm> getExistCPGDetails(String groupId) throws PEPServiceException, PEPPersistencyException;

	/** Method to get the groups for search group.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @return GroupSearchForm
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/19/2016 Added
	 *         By: Cognizant */
	GroupSearchForm groupSearch(GroupSearchForm groupSearchForm) throws PEPServiceException, PEPPersistencyException;

	/** Method to get Group search record count
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @return resordCount int
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/26/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 * @throws PEPServiceException */
	int groupSearchCount(GroupSearchForm groupSearchForm) throws PEPPersistencyException, PEPServiceException;

	/** Method to get the Depts for search group.
	 * 
	 * @param departmentsToBesearched String
	 * @return ArrayList
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/25/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException */
	ArrayList<DepartmentDetails> getDeptDetailsByDepNoFromADSE() throws PEPPersistencyException, PEPServiceException;

	/** Method to get the classes for search group.
	 * 
	 * @param departmentNumbers String
	 * @return List<ClassDetails>
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/26/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException */
	List<ClassDetails> getClassDetailsByDepNos(String departmentNumbers) throws PEPPersistencyException, PEPServiceException;

	/** This method is used to call Group Delete Service
	 * 
	 * @param groupId
	 * @param updatedBy
	 * @return String
	 * @throws Exception
	 * @throws PEPFetchException */
	String deleteGroup(String groupId, String groupType, String updatedBy) throws Exception, PEPFetchException;

	/** This method is used to call add Component Service and fetch data from
	 * database
	 * 
	 * @param updatedBy
	 * @param groupType
	 * @param selectedSplitAttributeList
	 * @return
	 * @throws Exception
	 * @throws PEPFetchException */
	CreateGroupForm addComponentToGroup(String groupId, String updatedBy, String groupType,
			List<GroupAttributeForm> selectedSplitAttributeList) throws Exception, PEPFetchException;

	/** This method is used to call add CPG Component Service and fetch data from
	 * database
	 * 
	 * @param updatedBy
	 * @param groupType
	 * @param selectedSplitAttributeList
	 * @return
	 * @throws Exception
	 * @throws PEPFetchException */
	CreateGroupForm addCPGComponentToGroup(String groupId, String updatedBy, String groupType,
			List<StyleAttributeForm> getCPGSelectedAttrbuteList) throws Exception, PEPFetchException;
	
	/**
	 * @param getSplitColorDetailsList
	 * @return
	 */
	List<GroupAttributeForm> prepareListForView(final List<GroupAttributeForm> getSplitColorDetailsList);
}
