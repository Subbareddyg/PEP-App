package com.belk.pep.dao;

import java.util.ArrayList;
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
	 * @return List<GroupAttributeForm> */
	List<GroupAttributeForm> getSplitColorDetails(String vendorStyleNo, String styleOrin);

	/** Method getSplitColorDetails.
	 * 
	 * @param vendorStyleNo String
	 * @param styleOrin String
	 * @return List<StyleAttributeForm> */
	List<StyleAttributeForm> getNewCPGDetails(final String vendorStyleNo, final String styleOrin, final String deptNoSearch, 
			final String classNoSearch,	final String supplierSiteIdSearch, final String upcNoSearch);

	/** Method getSplitSKUDetails.
	 * 
	 * @param vendorStyleNo String
	 * @param styleOrin String
	 * @return List<GroupAttributeForm> */
	List<GroupAttributeForm> getSplitSKUDetails(String vendorStyleNo, String styleOrin);

	/** This method is used to get the Existing Group Attribute Details for Split
	 * Color from Database.
	 * 
	 * @param groupId
	 * @return */
	List<GroupAttributeForm> getExistSplitColorDetails(String groupId);

	/** This method is used to get the Existing Group Attribute Details for Split
	 * Sku from Database.
	 * 
	 * @param groupId
	 * @return */
	List<GroupAttributeForm> getExistSplitSkuDetails(String groupId);

	/** This method is used to get the Existing Group Attribute Details for CPG
	 * from Database.
	 * 
	 * @param groupId
	 * @return */
	List<StyleAttributeForm> getExistCPGDetails(String groupId);

	/** Method to get the groups for search group.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @return List<GroupSearchDTO>
	 * @throws PEPServiceException PEPServiceException
	 * @throws PEPPersistencyException PEPPersistencyException
	 * 
	 *             Method added For PIM Phase 2 - groupSearch Date: 05/19/2016
	 *             Added By: Cognizant */
	List<GroupSearchDTO> groupSearch(GroupSearchForm groupSearchForm) throws PEPServiceException, PEPPersistencyException;

	/** Method to get the groups count for search group.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @return int
	 * 
	 * @throws PEPServiceException PEPServiceException
	 * @throws PEPPersistencyException PEPPersistencyException
	 * 
	 *             Method added For PIM Phase 2 - groupSearch Date: 05/19/2016
	 *             Added By: Cognizant */
	int groupSearchCount(GroupSearchForm groupSearchForm) throws PEPServiceException, PEPPersistencyException;

	/** Method to get the groups for search group.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @param groupSearchDTOList groupSearchParent(List<GroupSearchDTO>
	 * @return List<GroupSearchDTO>
	 * 
	 * @throws PEPServiceException PEPServiceException
	 * @throws PEPPersistencyException PEPPersistencyException
	 * 
	 *             Method added For PIM Phase 2 - groupSearch Date: 05/19/2016
	 *             Added By: Cognizant */
	List<GroupSearchDTO> groupSearchParent(List<GroupSearchDTO> groupSearchDTOList, GroupSearchForm groupSearchForm)
			throws PEPServiceException, PEPPersistencyException;

	/** Method to get the Depts for search group.
	 * 
	 * @return ArrayList<DepartmentDetails>
	 * @throws PEPPersistencyException PEPPersistencyException
	 * 
	 *             Method added For PIM Phase 2 - groupSearch Date: 05/25/2016
	 *             Added By: Cognizant */
	ArrayList<DepartmentDetails> getDeptDetailsByDepNoFromADSE() throws PEPPersistencyException;

	/** Method to get the classes for search group.
	 * 
	 * @param departmentNumbers String
	 * @return List<ClassDetails>
	 * @throws PEPPersistencyException PEPPersistencyException
	 * 
	 *             Method added For PIM Phase 2 - groupSearch Date: 05/26/2016
	 *             Added By: Cognizant */
	List<ClassDetails> getClassDetailsByDepNos(String departmentNumbers) throws PEPPersistencyException;

	/** Method to get the parents groups count for search group.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @param groupSearchDTOList List<GroupSearchDTO>
	 * @return int
	 * 
	 * @throws PEPPersistencyException PEPPersistencyException
	 * @throws PEPServiceException PEPServiceException
	 * 
	 *             Method added For PIM Phase 2 - groupSearch Date: 05/27/2016
	 *             Added By: Cognizant */
	int groupSearchParentCount(List<GroupSearchDTO> groupSearchDTOList, GroupSearchForm groupSearchForm) throws PEPServiceException,
			PEPPersistencyException;

}
