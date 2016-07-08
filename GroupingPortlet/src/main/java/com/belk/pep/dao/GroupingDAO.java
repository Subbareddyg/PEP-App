package com.belk.pep.dao;

import java.util.List;

import com.belk.pep.dto.ClassDetails;
import com.belk.pep.dto.CreateGroupDTO;
import com.belk.pep.dto.DepartmentDetails;
import com.belk.pep.dto.GroupSearchDTO;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.GroupAttributeForm;
import com.belk.pep.form.GroupSearchForm;
import com.belk.pep.form.StyleAttributeForm;
import com.belk.pep.util.PetLock;

/** This class responsible for handling all the DAO call to the VP Database.
 * 
 * @author AFUPYB3 */

public interface GroupingDAO {

	/** Method getGroupHeaderDetails.
	 * 
	 * @param groupId String
	 * @return CreateGroupDTO
	 * @throws PEPFetchException PEPFetchException */
	CreateGroupDTO getGroupHeaderDetails(String groupId) throws PEPFetchException;

	/** Method getSplitColorDetails.
	 * 
	 * @param vendorStyleNo String
	 * @param styleOrin String
	 * @return List*/
	List<GroupAttributeForm> getSplitColorDetails(String vendorStyleNo, String styleOrin) throws PEPFetchException;

	/**
	 * This method is used to get the Existing Group Attribute Details for Consolidated Product Grouping from Database.
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoSearch
	 * @param classNoSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @param groupId
	 * @return
	 * @throws PEPFetchException
	 */
	List<StyleAttributeForm> getNewCPGDetails(String vendorStyleNo, String styleOrin, String deptNoSearch, 
			String classNoSearch,	String supplierSiteIdSearch, String upcNoSearch, String groupId) throws PEPFetchException;
	/**
	 * This method is used to get the SKU Count for a Style Orin.
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoSearch
	 * @param classNoSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @param groupId
	 * @return
	 * @throws PEPFetchException
	 */
	List<String> getSKUCount(String vendorStyleNo, String styleOrin, String deptNoSearch, 
			String classNoSearch,	String supplierSiteIdSearch, String upcNoSearch, String groupId) throws PEPFetchException;

	/**
	 * This method is used to get the Existing Group Attribute Details for GBS Grouping from Database.
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoSearch
	 * @param classNoSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @param groupId
	 * @return
	 * @throws PEPFetchException
	 */
	List<GroupAttributeForm> getNewGBSDetails(String vendorStyleNo, String styleOrin, String deptNoSearch, 
			String classNoSearch, String supplierSiteIdSearch, String upcNoSearch, String groupId, List<String> orinList) throws PEPFetchException;

	/** Method getSplitSKUDetails.
	 * 
	 * @param vendorStyleNo String
	 * @param styleOrin String
	 * @return List */
	List<GroupAttributeForm> getSplitSKUDetails(String vendorStyleNo, String styleOrin) throws PEPFetchException;

	/** This method is used to get the Existing Group Attribute Details for Split
	 * Color from Database.
	 * 
	 * @param groupId
	 * @return */
	List<GroupAttributeForm> getExistSplitColorDetails(String groupId) throws PEPFetchException;

	/** This method is used to get the Existing Group Attribute Details for Split
	 * Sku from Database.
	 * 
	 * @param groupId
	 * @return */
	List<GroupAttributeForm> getExistSplitSkuDetails(String groupId) throws PEPFetchException;

	/** This method is used to get the Existing Group Attribute Details for CPG
	 * from Database.
	 * 
	 * @param groupId
	 * @return */
	List<StyleAttributeForm> getExistCPGDetails(String groupId) throws PEPFetchException;

	/** This method is used to get the Existing Group Attribute Details for GSS from Database.
	 * 
	 * @param groupId
	 * @return */
	List<GroupAttributeForm> getExistGBSDetails(String groupId) throws PEPFetchException;

	/** Method to get the groups for search group.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @return List

	 * 
	 *             Method added For PIM Phase 2 - groupSearch Date: 05/19/2016
	 *             Added By: Cognizant */
	List<GroupSearchDTO> groupSearch(GroupSearchForm groupSearchForm);

	/** Method to get the groups count for search group.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @return int
	 * 
	 *             Method added For PIM Phase 2 - groupSearch Date: 05/19/2016
	 *             Added By: Cognizant */
	int groupSearchCount(GroupSearchForm groupSearchForm);

	/** Method to get the groups for search group.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @param groupSearchDTOList groupSearchParent(List)
	 * @return List
	 * 
	 *             Method added For PIM Phase 2 - groupSearch Date: 05/19/2016
	 *             Added By: Cognizant */
	List<GroupSearchDTO> groupSearchParent(List<GroupSearchDTO> groupSearchDTOList, GroupSearchForm groupSearchForm);

	/** Method to get the Depts for search group.
	 * 
	 * @return ArrayList
	 * @throws PEPPersistencyException PEPPersistencyException
	 * 
	 *             Method added For PIM Phase 2 - groupSearch Date: 05/25/2016
	 *             Added By: Cognizant */
	List<DepartmentDetails> getDeptDetailsByDepNoFromADSE() throws PEPPersistencyException;

	/** Method to get the classes for search group.
	 * 
	 * @param departmentNumbers String
	 * @return List
	 * @throws PEPPersistencyException PEPPersistencyException
	 * 
	 *             Method added For PIM Phase 2 - groupSearch Date: 05/26/2016
	 *             Added By: Cognizant */
	List<ClassDetails> getClassDetailsByDepNos(String departmentNumbers) throws PEPPersistencyException;

	/** Method to get the parents groups count for search group.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @param groupSearchDTOList List
	 * @return int
	 * 
	 *             Method added For PIM Phase 2 - groupSearch Date: 05/27/2016
	 *             Added By: Cognizant */
	int groupSearchParentCount(List<GroupSearchDTO> groupSearchDTOList, GroupSearchForm groupSearchForm);
	
	/**
	 *  This method is used to get the search result Details for Regular/Beauty Collection Grouping.
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
	public List<StyleAttributeForm> getRegularBeautySearchResult(final String vendorStyleNo, final String styleOrin, final String deptNoSearch, 
			final String classNoSearch,	final String supplierSiteIdSearch, final String upcNoSearch, final String groupId,
			final String groupIdSearch, final String groupNameSearch, final String sortedColumn, final String sortingOrder,
			final int pageNumber, final int recordsPerPage) throws PEPFetchException;
	
	/**
	 *  This method is used to get the search result Details count for Regular/Beauty Collection Grouping.
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @param deptNoForInSearch
	 * @param classNoForInSearch
	 * @param supplierSiteIdSearch
	 * @param upcNoSearch
	 * @param groupId
	 * @param groupIdSearch
	 * @param groupNameSearch
	 * @return List<StyleAttributeForm>
	 * @throws PEPServiceException
	 */
	public int getRegularBeautySearchResultCount(final String vendorStyleNo, final String styleOrin, final String deptNoForInSearch, 
			final String classNoForInSearch, final String supplierSiteIdSearch, final String upcNoSearch,
			final String groupId, final String groupIdSearch, final String groupNameSearch);

	/**
	 *  This method is used to get the existing components query for Regular/Beauty Collection Grouping.
	 * @return String
	 */
	List<StyleAttributeForm> getExistRegularBeautyDetails(String groupId)
			throws PEPFetchException;

	/**
	 *  This method is used to get the group child query for Regular/Beauty Collection Grouping.
	 * @return String
	 */
	List<StyleAttributeForm> getRegularBeautyChildDetails(String groupId)
			throws PEPFetchException;

	/**
	 * This method is used check the LOCK status of a pet.
	 * @param Orin
	 * @param pepUserId
	 * @param searchPetLockedtype
	 * @return
	 * @throws PEPPersistencyException
	 */
	List<PetLock> isPETLocked(String Orin, String pepUserId, String searchPetLockedtype) throws PEPPersistencyException;
	
	/**
	 * This method is used to lock a PET while using.  
	 * @param orin
	 * @param pepUserID
	 * @param pepfunction
	 * @return
	 * @throws PEPPersistencyException
	 */
	boolean lockPET(String orin, String pepUserID, String pepfunction) throws PEPPersistencyException;
	
	/**
	 * This Method is used to release Group Locking.
	 * @param orin
	 * @param pepUserID
	 * @param pepFunction
	 * @return
	 * @throws PEPPersistencyException
	 */
	boolean releseLockedPet(String orin, String pepUserID, String pepFunction)throws PEPPersistencyException;	
	
	/**
	 * This method is used to get the Child Regular/Beauty Collection Grouping
	 * details from Database.
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
	 */
	List<GroupAttributeForm> getRCGBCGCPGChildDetailsForStyle(
			String groupId, String styleOrinParent, String vendorStyleNo,
			String styleOrin, String supplierSiteIdSearch, String upcNoSearch,
			String deptNoSearch, String classNoSearch) throws PEPFetchException;
	
	/**
	 *  This method return max of priority for a group
	 * @param groupId
	 * @return int
	 */
	public int getMaxPriorityFromDB(final String groupId);

	/**
	 * This method is used to get the available components.
	 * 
	 * @param groupId
	 * @return List
	 */
	List getComponentList(String groupId);
	
}
