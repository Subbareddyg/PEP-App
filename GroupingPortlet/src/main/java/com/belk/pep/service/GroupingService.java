package com.belk.pep.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
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
import com.belk.pep.util.PetLock;

/**
 * This calss responsible for service delegate.
 * 
 * @author AFUPYB3
 */
public interface GroupingService {

	/**
	 * This method is used to create group.
	 * 
	 * @param jsonStyle
	 * @param updatedBy
	 * @param selectedSplitAttributeList
	 * @return CreateGroupForm
	 * @throws PEPServiceException
	 */
	CreateGroupForm saveGroupHeaderDetails(JSONObject jsonStyle, String updatedBy, List<GroupAttributeForm> selectedSplitAttributeList)
			throws PEPServiceException;

	/**
	 * This method is used to call create group service.
	 * 
	 * @param jsonStyle
	 * @return String
	 * @throws Exception
	 * @throws PEPServiceException
	 */
	String callCreateGroupService(JSONObject jsonStyle) throws PEPServiceException;

	/**
	 * This method is used to get Component details for Split Color.
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @return List
	 * @throws PEPServiceException
	 */
	List<GroupAttributeForm> getSplitColorDetails(String vendorStyleNo, String styleOrin) throws PEPServiceException;

	/**
	 * This method is used to get Component details for CPG.
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @return List
	 * @throws PEPServiceException
	 */
	List<StyleAttributeForm> getNewCPGDetails(String vendorStyleNo, String styleOrin, String deptNoSearch, String classNoSearch,
			String supplierSiteIdSearch, String upcNoSearch, String groupId) throws PEPServiceException;

	/**
	 * This method is used to get the SKU details to create Split Color group.
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoSearch
	 * @param classNoSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @param groupId
	 * @return
	 * @throws PEPServiceException
	 */
	List<String> getSKUCount(String vendorStyleNo, String styleOrin, String deptNoSearch, String classNoSearch,
			String supplierSiteIdSearch, String upcNoSearch, String groupId) throws PEPServiceException;

	/**
	 * This method is used to get Component details for GBS.
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @return List
	 * @throws PEPServiceException
	 */
	List<GroupAttributeForm> getNewGBSDetails(String vendorStyleNo, String styleOrin, String deptNoSearch, String classNoSearch,
			String supplierSiteIdSearch, String upcNoSearch, String groupId, List<String> orinList) throws PEPServiceException;

	/**
	 * This method is used to get Component details for Split SKU.
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @return List
	 * @throws PEPServiceException
	 */
	List<GroupAttributeForm> getSplitSKUDetails(String vendorStyleNo, String styleOrin) throws PEPServiceException;

	/**
	 * This method is used to validate SCG attribute list before add to group.
	 * 
	 * @param getSplitColorDetailsList
	 * @return List
	 * @throws PEPServiceException
	 */
	Map<String, List<GroupAttributeForm>> validateSCGAttributeDetails(List<GroupAttributeForm> getSplitColorDetailsList)
			throws PEPServiceException;

	/**
	 * This method is used to validate SCG attribute list before add to group.
	 * 
	 * @param getSplitSKUDetailsList
	 * @return List
	 * @throws PEPServiceException
	 */
	Map<String, List<GroupAttributeForm>> validateSSGAttributeDetails(List<GroupAttributeForm> getSplitSKUDetailsList)
			throws PEPServiceException;

	/**
	 * This method is used to validate CPG attribute list before add to group.
	 * 
	 * @param existClassId
	 * @param getCPGSelectedAttrbuteList
	 * @return
	 * @throws PEPServiceException
	 */
	String validateCPGAttributeDetails(String existClassId, List<StyleAttributeForm> getCPGSelectedAttrbuteList)
			throws PEPServiceException;

	/**
	 * This method is used to get the attribute list which is selected by user.
	 * before to add SCG group
	 * 
	 * @param updatedSplitColorDetailsList
	 * @param selectedItemsArr
	 * @param defaultSelectedAttr
	 * @return List
	 */
	List<GroupAttributeForm> getSelectedColorAttributeList(List<GroupAttributeForm> updatedSplitColorDetailsList,
			String[] selectedItemsArr, String defaultSelectedAttr);

	/**
	 * This method is used to get the attribute list which is selected by user.
	 * before to add SSG group
	 * 
	 * @param updatedSplitSKUDetailsList
	 * @param selectedItemsArr
	 * @param defaultSelectedAttr
	 * @return List
	 */
	List<GroupAttributeForm> getSelectedSKUAttributeList(List<GroupAttributeForm> updatedSplitSKUDetailsList, String[] selectedItemsArr,
			String defaultSelectedAttr);

	/**
	 * This method is used to get the attribute list which is selected by user.
	 * before to add CPG group
	 * 
	 * @param updatedSplitSKUDetailsList
	 * @param selectedItemsArr
	 * @param defaultSelectedAttr
	 * @return List
	 */
	List<StyleAttributeForm> getSelectedCPGAttributeList(List<StyleAttributeForm> getCPGDetailsList, String[] selectedItemsArr,
			String defaultSelectedAttr);

	/**
	 * This method is used to get the selected Attribute List for GBS.
	 * 
	 * @param getGBSDetailsList
	 * @param selectedItemsArr
	 * @return
	 */
	List<GroupAttributeForm> getSelectedGBSAttributeList(List<GroupAttributeForm> getGBSDetailsList, String[] selectedItemsArr);

	/**
	 * This method is used to get the selected Attribute List for RCG.
	 * 
	 * @param getGBSDetailsList
	 * @param selectedItemsArr
	 * @return
	 */
	List<StyleAttributeForm> getSelectedRCGAttributeList(List<StyleAttributeForm> getRCGDetailsList, String[] selectedItemsArr);

	/**
	 * This method is used to get the selected Attribute List for RCG.
	 * 
	 * @param getGBSDetailsList
	 * @param selectedItemsArr
	 * @return
	 */
	List<StyleAttributeForm> getSelectedBCGAttributeList(List<StyleAttributeForm> getRCGDetailsList, String[] selectedItemsArr);

	/**
	 * This method is used to Existing Group details from DB.
	 * 
	 * @param groupId
	 * @return CreateGroupForm
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	CreateGroupForm getExistingGrpDetails(String groupId) throws PEPFetchException, ParseException;

	/**
	 * This method is used to get the Existing Style Color details.
	 * 
	 * @param groupId
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	List<GroupAttributeForm> getExistSplitColorDetails(String groupId) throws PEPServiceException, PEPPersistencyException;

	/**
	 * This method is used to get the Existing Style Sku details.
	 * 
	 * @param groupId
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	List<GroupAttributeForm> getExistSplitSkuDetails(String groupId) throws PEPServiceException, PEPPersistencyException;

	/**
	 * This method is used to get the Existing CPG details.
	 * 
	 * @param groupId
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	List<StyleAttributeForm> getExistCPGDetails(String groupId) throws PEPServiceException, PEPPersistencyException;

	/**
	 * This method is used to get the Existing GSS details.
	 * 
	 * @param groupId
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	List<GroupAttributeForm> getExistGBSDetails(String groupId) throws PEPServiceException, PEPPersistencyException;

	/**
	 * Method to get the groups for search group.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @return GroupSearchForm
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/19/2016 Added
	 *         By: Cognizant
	 */
	GroupSearchForm groupSearch(GroupSearchForm groupSearchForm) throws PEPServiceException, PEPPersistencyException;

	/**
	 * Method to get Group search record count.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @return resordCount int
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/26/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 * @throws PEPServiceException
	 */
	int groupSearchCount(GroupSearchForm groupSearchForm) throws PEPPersistencyException, PEPServiceException;

	/**
	 * Method to get the Depts for search group.
	 * 
	 * @param departmentsToBesearched String
	 * @return ArrayList
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/25/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 */
	List<DepartmentDetails> getDeptDetailsByDepNoFromADSE() throws PEPPersistencyException, PEPServiceException;

	/**
	 * Method to get the classes for search group.
	 * 
	 * @param departmentNumbers String
	 * @return List
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/26/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 */
	List<ClassDetails> getClassDetailsByDepNos(String departmentNumbers) throws PEPPersistencyException, PEPServiceException;

	/**
	 * This method is used to call Group Delete Service.
	 * 
	 * @param groupId
	 * @param groupType
	 * @param updatedBy
	 * @return
	 * @throws PEPFetchException
	 * @throws MalformedURLException
	 * @throws ClassCastException
	 * @throws JSONException
	 * @throws IOException
	 */
	String deleteGroup(String groupId, String groupType, String updatedBy) throws PEPFetchException, MalformedURLException,
			ClassCastException, JSONException, IOException;

	/**
	 * This method is used to call add Component Service and fetch data from.
	 * database.
	 * 
	 * @param groupId
	 * @param updatedBy
	 * @param groupType
	 * @param selectedSplitAttributeList
	 * @return
	 * @throws PEPFetchException
	 * @throws MalformedURLException
	 * @throws ClassCastException
	 * @throws JSONException
	 * @throws IOException
	 */
	CreateGroupForm addComponentToGroup(String groupId, String updatedBy, String groupType,
			List<GroupAttributeForm> selectedSplitAttributeList) throws PEPFetchException, MalformedURLException, ClassCastException,
			JSONException, IOException;

	/**
	 * This method is used to call add CPG Component Service and fetch data
	 * from.. database.
	 * 
	 * @param groupId
	 * @param updatedBy
	 * @param groupType
	 * @param getCPGSelectedAttrbuteList
	 * @return
	 * @throws PEPFetchException
	 * @throws MalformedURLException
	 * @throws ClassCastException
	 * @throws JSONException
	 * @throws IOException
	 */
	CreateGroupForm addCPGComponentToGroup(String groupId, String updatedBy, String groupType,
			List<StyleAttributeForm> getCPGSelectedAttrbuteList) throws PEPFetchException, MalformedURLException, ClassCastException,
			JSONException, IOException;

	/**
	 * This method is used to call add GBS Component Service and fetch data from
	 * database.
	 * 
	 * @param updatedBy
	 * @param groupType
	 * @param getCPGSelectedAttrbuteList
	 * @return
	 * @throws PEPFetchException
	 * @throws MalformedURLException
	 * @throws ClassCastException
	 * @throws JSONException
	 * @throws IOException
	 */
	CreateGroupForm addGBSComponentToGroup(String groupId, String updatedBy, String groupType,
			List<GroupAttributeForm> getCPGSelectedAttrbuteList) throws PEPFetchException, MalformedURLException, ClassCastException,
			JSONException, IOException;

	/**
	 * This method is used to call add RCG Component Service and fetch data from
	 * database.
	 * 
	 * @param updatedBy
	 * @param groupType
	 * @param getRCGSelectedAttrbuteList
	 * @return
	 * @throws PEPFetchException
	 * @throws MalformedURLException
	 * @throws ClassCastException
	 * @throws JSONException
	 * @throws IOException
	 */
	CreateGroupForm addRCGBCGComponentToGroup(String groupId, String updatedBy, String groupType,
			List<StyleAttributeForm> getRCGBCGSelectedAttrbuteList) throws PEPFetchException, MalformedURLException, ClassCastException,
			JSONException, IOException;

	/**
	 * @param getSplitColorDetailsList
	 * @return
	 */
	List<GroupAttributeForm> prepareListForView(final List<GroupAttributeForm> getSplitColorDetailsList);

	/**
	 * This method is used to call the service method for saving the edited
	 * group headers.
	 * 
	 * @param createGroupForm
	 * @param modifiedBy
	 * @return
	 * @throws PEPFetchException
	 * @throws MalformedURLException
	 * @throws ClassCastException
	 * @throws JSONException
	 * @throws IOException
	 */
	String updateGroupHeaderDetails(CreateGroupForm createGroupForm, String modifiedBy) throws PEPFetchException, MalformedURLException,
			ClassCastException, JSONException, IOException;

	/**
	 * This method handles the removing of exising components
	 * 
	 * @param jsonObject
	 * @return
	 * @throws PEPFetchException
	 * @throws MalformedURLException
	 * @throws ClassCastException
	 * @throws JSONException
	 * @throws IOException
	 */
	String removeSelectedComponent(JSONObject jsonObject) throws PEPFetchException, MalformedURLException, ClassCastException,
			JSONException, IOException;

	/**
	 * This method handles the interface method for setting component priority
	 * 
	 * @param jsonObject
	 * @return
	 * @throws PEPFetchException
	 * @throws MalformedURLException
	 * @throws ClassCastException
	 * @throws JSONException
	 * @throws IOException
	 */
	String setComponentPriority(JSONObject jsonObject) throws PEPFetchException, MalformedURLException, ClassCastException, JSONException,
			IOException;

	/**
	 * This method is used to call Default Color Service.
	 * 
	 * @param groupId String
	 * @param groupType String
	 * @param colorId String
	 * @param childOrinId String
	 * @param updatedBy String
	 * @return String
	 * @throws PEPFetchException
	 */
	String setDefaultColorSize(String groupId, String groupType, String colorId, String childOrinId, String updatedBy)
			throws MalformedURLException, ClassCastException, IOException, JSONException;

	/**
	 * This method is used to get the search result Details for Regular/Beauty
	 * Collection Grouping.
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoSearch
	 * @param classNoSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @param groupId
	 * @param groupIdSearch
	 * @param groupNameSearch
	 * @param sortedColumn
	 * @param sortingOrder
	 * @param pageNumber
	 * @param recordsPerPage
	 * @return List<StyleAttributeForm>
	 * @throws PEPServiceException
	 */
	List<StyleAttributeForm> getRegularBeautySearchResult(final String vendorStyleNo, final String styleOrin, final String deptNoSearch,
			final String classNoSearch, final String supplierSiteIdSearch, final String upcNoSearch, final String groupId,
			final String groupIdSearch, final String groupNameSearch, final String sortedColumn, final String sortingOrder,
			final int pageNumber, final int recordsPerPage) throws PEPServiceException;

	/**
	 * This method is used to get the search result Details count for
	 * Regular/Beauty Collection Grouping.
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoSearch
	 * @param classNoSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @param groupId
	 * @param groupIdSearch
	 * @param groupNameSearch
	 * @return List<StyleAttributeForm>
	 * @throws PEPServiceException
	 */
	int getRegularBeautySearchResultCount(final String vendorStyleNo, final String styleOrin, final String deptNoSearch,
			final String classNoSearch, final String supplierSiteIdSearch, final String upcNoSearch, final String groupId,
			final String groupIdSearch, final String groupNameSearch);

	/**
	 * This method is used to get the existing components query for
	 * Regular/Beauty Collection Grouping.
	 * 
	 * @return String
	 */
	List<StyleAttributeForm> getExistRegularBeautyDetails(String groupId) throws PEPServiceException;

	/**
	 * This method is used to get the group child query for Regular/Beauty
	 * Collection Grouping.
	 * 
	 * @return String
	 */
	List<StyleAttributeForm> getRegularBeautyChildDetails(String groupId) throws PEPServiceException;

	/**
	 * This method is used check the LOCK status of a pet.
	 * 
	 * @param Orin
	 * @param pepUserId
	 * @param searchPetLockedtype
	 * @return
	 * @throws PEPPersistencyException
	 */
	List<PetLock> isPETLocked(String pepUserId, String orin, String searchPetLockedtype) throws PEPPersistencyException;

	/**
	 * This method is used to lock a PET while using.
	 * 
	 * @param orin
	 * @param pepUserID
	 * @param pepfunction
	 * @return
	 * @throws PEPPersistencyException
	 */
	boolean lockPET(String orin, String pepUserID, String pepfunction) throws PEPPersistencyException;

	/**
	 * This method is used to release Grouping pet lock.
	 * @param orin
	 * @param pepUserID
	 * @param pepFunction
	 * @return
	 * @throws PEPPersistencyException
	 */
	boolean releseLockedPet(String orin, String pepUserID, String pepFunction) throws PEPPersistencyException;
	
	/**
	 * This method is used to get the Child Regular/Beauty Collection Grouping
	 * details.
	 * 
	 * @param groupId
	 * @param styleOrinParent
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoSearch
	 * @param classNoSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @return List<GroupAttributeForm>
	 * @throws PEPServiceException
	 */
	List<GroupAttributeForm> getRCGBCGCPGChildDetailsForStyle(String groupId,
			String styleOrinParent, String vendorStyleNo, String styleOrin,
			String supplierSiteIdSearch, String upcNoSearch,
			String deptNoSearch, String classNoSearch)
			throws PEPServiceException;
}
