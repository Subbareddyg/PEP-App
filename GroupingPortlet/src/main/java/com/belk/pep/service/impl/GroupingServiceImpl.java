package com.belk.pep.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.belk.pep.constants.GroupingConstants;
import com.belk.pep.dao.GroupingDAO;
import com.belk.pep.dto.ClassDetails;
import com.belk.pep.dto.CreateGroupDTO;
import com.belk.pep.dto.DepartmentDetails;
import com.belk.pep.dto.GroupSearchDTO;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.CreateGroupForm;
import com.belk.pep.form.GroupAttributeForm;
import com.belk.pep.form.GroupForm;
import com.belk.pep.form.GroupSearchForm;
import com.belk.pep.form.StyleAttributeForm;
import com.belk.pep.service.GroupingService;
import com.belk.pep.util.GroupingUtil;
import com.belk.pep.util.PetLock;
import com.belk.pep.util.PropertyLoader;

/**
 * The Class GroupingServiceImpl.
 */
public class GroupingServiceImpl implements GroupingService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(GroupingServiceImpl.class.getName());

	/** The GroupingDAO. */
	private GroupingDAO groupingDAO;

	/**
	 * Gets the Grouping DAO.
	 * 
	 * @return the Grouping DAO
	 */
	public final GroupingDAO getGroupingDAO() {
		return groupingDAO;
	}

	/**
	 * Sets the Grouping DAO. GroupingDAO the new Grouping DAO.
	 */
	public final void setGroupingDAO(GroupingDAO groupingDAO) {
		this.groupingDAO = groupingDAO;
	}

	/**
	 * This method is used to call Group Creation Service and fetch data from.
	 * database.
	 * 
	 * @param jsonStyle
	 * @param updatedBy
	 * @param selectedSplitAttributeList
	 * @return CreateGroupForm
	 * @throws PEPFetchException
	 * @throws IOException
	 * @throws JSONException
	 * @throws ClassCastException
	 * @throws MalformedURLException
	 */
	public final CreateGroupForm saveGroupHeaderDetails(final JSONObject jsonStyle, final String updatedBy,
			final List<GroupAttributeForm> selectedSplitAttributeList) throws PEPFetchException, MalformedURLException, ClassCastException,
			JSONException, IOException, ParseException {
		LOGGER.info("Entering saveGroupHeaderDetails-->.");

		String groupIdRes = "";
		String responseMsg = "";
		String responseMsgCode = "";
		String groupCreationStatus = "";

		/** Calling Web Service to create Group except Split type **/
		String resMsg = callCreateGroupService(jsonStyle);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create Group Service message-->" + resMsg);
		}
		final Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);

		/** Extract Service message **/
		JSONObject jsonObjectRes = null;
		if (null != resMsg && !("").equals(resMsg)) {
			jsonObjectRes = new JSONObject(resMsg);
		}
		if (null != jsonObjectRes) {
			responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
			groupIdRes = jsonObjectRes.getString(GroupingConstants.COMPONENT_ID);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("responseMsgCode-->" + responseMsgCode + " \ngroupIdRes-->" + groupIdRes);
		}

		if (null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
			responseMsg = prop.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_SUCCESS);
			groupCreationStatus = GroupingConstants.GROUP_CREATED;
			LOGGER.info("responseMsg100::Success-->" + responseMsg);
		} else {
			responseMsg = prop.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_FAILURE);
			groupCreationStatus = GroupingConstants.GROUP_NOT_CREATED;
			LOGGER.info("responseMsg101::Failure-->" + responseMsg);
		}
		/** End of Group Header Creation **/

		String groupType = jsonStyle.getString(GroupingConstants.GROUP_TYPE);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("groupType from jsonStyle Response after creating Group Header-->" + groupType);
			LOGGER.debug("responseMsgCode from jsonStyle Response after creating Group Header-->" + responseMsgCode);
		}
		/** Call add component service to add Split Color attribute details List **/
		if (null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
			if (null != groupType && groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_COLOR)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Calling Add component for Split Color service Start.");
				}
				// Create Split Color Group
				JSONObject jsonStyleSpliColor = populateAddComponentSCGJson(groupIdRes, groupType, updatedBy, selectedSplitAttributeList/* componentList */);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Add Color Attribute JSON-->" + jsonStyleSpliColor);
					LOGGER.debug("json Object Add Component to Split Color groupId--> " + jsonStyleSpliColor.getString("groupId"));
				}
				// final String resMsgSplitColor =
				final String resMsgSplitColor = callAddComponentService(jsonStyleSpliColor, groupType);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Add Component to Split Color Group Service message-->" + resMsgSplitColor);
				}

				/** Extract Service message **/
				if (null != resMsgSplitColor && !("").equals(resMsgSplitColor)) {
					jsonObjectRes = new JSONObject(resMsgSplitColor);
				}
				if (null != jsonObjectRes) {
					responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("resMsgSplitColor.responseMsgCode-->" + responseMsgCode);
				}

				if (null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
					responseMsg = "Grouping #" + groupIdRes + " - " + prop.getProperty(GroupingConstants.ADD_COMPONENT_SCG_SERVICE_SUCCESS);
					groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_COMPONENT_SCG;
					LOGGER.info("Add Component to Split Color Group. ResponseMsg100::Success-->" + responseMsg);
				} else {
					responseMsg = "#" + groupIdRes + " - " + prop.getProperty(GroupingConstants.ADD_COMPONENT_SCG_SERVICE_FAILURE);
					groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_OUT_COMPONENT_SCG;
					LOGGER.info("Add Component to Split Color Group. ResponseMsg101::Failure-->" + responseMsg);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Calling Add component for Split Color service End.");
				}
			}/**
			 * Call add component service to add split SKU attribute details
			 * List
			 **/
			else if (null != groupType && groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_SKU)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Calling Add component for Split SKU service Start.");
				}
				// Create Split SKU Group
				JSONObject jsonStyleSpliSku = populateAddComponentSSGJson(groupIdRes, groupType, updatedBy, selectedSplitAttributeList);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Add SKU Attribute JSON-->" + jsonStyleSpliSku);
					LOGGER.debug("json Object Add Component to Split SKU groupId--> " + jsonStyleSpliSku.getString("groupId"));
				}
				String resMsgSplitSku = callAddComponentService(jsonStyleSpliSku, groupType);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Add Component to Split SKU Group Service message-->" + resMsgSplitSku);
				}

				/** Extract Service message. **/
				if (null != resMsgSplitSku && !("").equals(resMsgSplitSku)) {
					jsonObjectRes = new JSONObject(resMsgSplitSku);
				}
				if (null != jsonObjectRes) {
					responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("resMsgSplitSku.responseMsgCode-->" + responseMsgCode);
				}

				if (null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
					responseMsg = "Grouping #" + groupIdRes + " - " + prop.getProperty(GroupingConstants.ADD_COMPONENT_SSG_SERVICE_SUCCESS);
					groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_COMPONENT_SSG;
					LOGGER.info("Add Component to Split SKU Group. ResponseMsg100::Success-->" + responseMsg);
				} else {
					responseMsg = "#" + groupIdRes + " - " + prop.getProperty(GroupingConstants.ADD_COMPONENT_SSG_SERVICE_FAILURE);
					groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_OUT_COMPONENT_SSG;
					LOGGER.info("Add Component to Split SKU Group. ResponseMsg101::Failure-->" + responseMsg);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Calling Add component for Split SKU service End.");
				}
			}
		}
		/** End Group Creation for All Type. **/

		// Call DAO to fetch Group Details after getting response from service
		CreateGroupForm createGroupForm = new CreateGroupForm();
		CreateGroupDTO createGroupDTO = new CreateGroupDTO();
		if (null != responseMsgCode && (GroupingConstants.SUCCESS_CODE).equals(responseMsgCode)) {

			createGroupDTO = groupingDAO.getGroupHeaderDetails(groupIdRes);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("After Calling database method getGroupHeaderDetails() to retreive Group Header Details-->"
						+ createGroupDTO.getGroupId());
			}
		}
		createGroupDTO.setGroupCretionMsg(responseMsg);

		/** Transfer object value from DTO to Form Object Start. **/
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer object value from DTO to Form Object Start.");
		}
		createGroupForm.setGroupId(createGroupDTO.getGroupId());
		createGroupForm.setGroupName(createGroupDTO.getGroupName());
		createGroupForm.setGroupType(createGroupDTO.getGroupType());
		createGroupForm.setGroupDesc(createGroupDTO.getGroupDesc());
		createGroupForm.setGroupLaunchDate(createGroupDTO.getGroupLaunchDate());
		createGroupForm.setEndDate(createGroupDTO.getEndDate());
		createGroupForm.setGroupCretionMsg(createGroupDTO.getGroupCretionMsg());
		createGroupForm.setGroupAttributeFormList(createGroupDTO.getGroupAttributeDTOList());
		createGroupForm.setGroupCreationStatus(groupCreationStatus);
		createGroupForm.setGroupStatus(createGroupDTO.getGroupStatus());
		createGroupForm.setCarsGroupType(createGroupDTO.getCarsGroupType());
		createGroupForm.setIsAlreadyInGroup(createGroupDTO.getIsAlreadyInGroup());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer object value from DTO to Form Object end.");

		}
		/** **/

		LOGGER.info("Exist saveGroupHeaderDetails-->.");
		return createGroupForm;
	}

	/**
	 * This method is used to call Group Creation Service.
	 * 
	 * @param jsonStyle
	 * @return String
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	public final String callCreateGroupService(final JSONObject jsonStyle) throws MalformedURLException, ClassCastException, IOException,
			JSONException {
		LOGGER.info("Entering callCreateGroupService-->.");

		String responseMsg = "";
		BufferedReader responseBuffer = null;
		HttpURLConnection httpConnection = null;
		try {
			Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_URL);
			LOGGER.info("Create Group ServiceURL-->" + serviceURL);

			httpConnection = getServiceConnection(serviceURL);

			LOGGER.info("callCreateGroupService Service::Json Array-->" + jsonStyle.toString());

			String input = jsonStyle.toString();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes(GroupingConstants.DEFAULT_CHARSET));
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), GroupingConstants.DEFAULT_CHARSET));
			String output;
			LOGGER.info("Output from Server::: after Calling-->.");
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("CreateGroupService Service Output-->" + output);

				responseMsg = output;
			}

		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new MalformedURLException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new ClassCastException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new JSONException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new IOException(e.getMessage());
		} finally {
			if (null != httpConnection) {
				httpConnection.disconnect();
			}
			if (responseBuffer != null)
				responseBuffer.close();
		}
		LOGGER.info("Exiting callCreateGroupService-->" + responseMsg);
		return responseMsg;
	}

	/**
	 * This method is used to get the Style Color details to create Split Color
	 * group.
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @return getSplitColorDetailsList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<GroupAttributeForm> getSplitColorDetails(final String vendorStyleNo, final String styleOrin)
			throws PEPFetchException, PEPServiceException, PEPPersistencyException {
		// getting the Style Color details from Database
		LOGGER.info("Enter-->calling getSplitColorDetails from GroupingServiceImpl.");
		List<GroupAttributeForm> getSplitColorDetailsList = null;
		try {
			getSplitColorDetailsList = groupingDAO.getSplitColorDetails(vendorStyleNo, styleOrin);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getSplitColorDetails from GroupingServiceImpl.");
		return getSplitColorDetailsList;
	}

	/**
	 * This method is used to get the SKU details to create Split Color group.
	 * 
	 * @param vendorStyleNo
	 * @param styleOrin
	 * @return getSplitColorDetailsList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<GroupAttributeForm> getSplitSKUDetails(final String vendorStyleNo, final String styleOrin)
			throws PEPServiceException, PEPPersistencyException {
		// getting the Style Color details from Database
		LOGGER.info("*Enter-->calling getSplitSKUDetails from GroupingServiceImpl.");
		List<GroupAttributeForm> getSplitColorDetailsList = null;
		try {
			getSplitColorDetailsList = groupingDAO.getSplitSKUDetails(vendorStyleNo, styleOrin);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getSplitSKUDetails from GroupingServiceImpl.");
		return getSplitColorDetailsList;
	}

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
	 * @throws PEPFetchException
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<StyleAttributeForm> getNewCPGDetails(final String vendorStyleNo, final String styleOrin, final String deptNoSearch,
			final String classNoSearch, final String supplierSiteIdSearch, final String upcNoSearch, final String groupId)
			throws PEPFetchException, PEPServiceException, PEPPersistencyException {
		// getting the Style Color details from Database
		LOGGER.info("*Enter-->calling getNewCPGDetails from GroupingServiceImpl.");
		List<StyleAttributeForm> getNewCPGDetails = null;
		try {
			getNewCPGDetails = groupingDAO.getNewCPGDetails(vendorStyleNo, styleOrin, deptNoSearch, classNoSearch, supplierSiteIdSearch,
					upcNoSearch, groupId);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer.getNewCPGDetails-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getNewCPGDetails from GroupingServiceImpl.");
		return getNewCPGDetails;
	}

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
	 * @throws PEPFetchException
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<String> getSKUCount(final String vendorStyleNo, final String styleOrin, final String deptNoSearch,
			final String classNoSearch, final String supplierSiteIdSearch, final String upcNoSearch, final String groupId)
			throws PEPFetchException, PEPServiceException, PEPPersistencyException {
		// getting the Style Color details from Database
		LOGGER.info("Enter-->calling getSKUCount from GroupingServiceImpl.");
		List<String> orinList = null;
		try {
			orinList = groupingDAO.getSKUCount(vendorStyleNo, styleOrin, deptNoSearch, classNoSearch, supplierSiteIdSearch, upcNoSearch,
					groupId);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getSKUCount from GroupingServiceImpl.");
		return orinList;
	}

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
	 * @throws PEPFetchException
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<GroupAttributeForm> getNewGBSDetails(final String vendorStyleNo, final String styleOrin, final String deptNoSearch,
			final String classNoSearch, final String supplierSiteIdSearch, final String upcNoSearch, final String groupId,
			final List<String> orinList) throws PEPFetchException, PEPServiceException, PEPPersistencyException {
		// getting the Style Color details from Database
		LOGGER.info("Enter-->calling getNewGBSDetails from GroupingServiceImpl.");
		List<GroupAttributeForm> getNewGBSDetailsList = null;
		try {
			getNewGBSDetailsList = groupingDAO.getNewGBSDetails(vendorStyleNo, styleOrin, deptNoSearch, classNoSearch,
					supplierSiteIdSearch, upcNoSearch, groupId, orinList);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getNewGBSDetails from GroupingServiceImpl.");
		return getNewGBSDetailsList;
	}

	/**
	 * This method is used to validate Split Color Attribute list before add it
	 * to group.
	 * 
	 * @param getSplitColorDetailsList
	 * @return validateMap
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final Map<String, List<GroupAttributeForm>> validateSCGAttributeDetails(final List<GroupAttributeForm> getSplitColorDetailsList)
			throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("Enter-->calling validateSCGAttributeDetails");
		List<GroupAttributeForm> updatedSplitColorDetailsList = new ArrayList<>();
		GroupAttributeForm groupAttributeForm = null;
		String productName = "";
		String errorCode = "noData";
		Map<String, List<GroupAttributeForm>> validateMap = new HashMap<>();
		try {

			for (int i = 0; i < getSplitColorDetailsList.size(); i++) {
				groupAttributeForm = getSplitColorDetailsList.get(i);
				String entryType = groupAttributeForm.getEntryType();
				if (null != entryType && ("Style").equals(entryType)) {
					productName = groupAttributeForm.getProdName();
					break;
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Style ProductName is -->" + productName);
			}

			for (int i = 0; i < getSplitColorDetailsList.size(); i++) {
				groupAttributeForm = getSplitColorDetailsList.get(i);
				String entryType = groupAttributeForm.getEntryType();
				String petStatus = groupAttributeForm.getPetStatus();
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Pet Status not Completed. Orin No: " + groupAttributeForm.getOrinNumber() + ", \nPetStatus: " + petStatus);
					LOGGER.debug("ColorCode------------------added------------------------------------>"
							+ groupAttributeForm.getColorCode());
				}
				if (null != petStatus && !(GroupingConstants.PET_STATUS_COMPLETED).equals(petStatus.trim())
						&& !(GroupingConstants.PET_STATUS_CLOSED).equals(petStatus.trim())
						&& !(GroupingConstants.PET_STATUS_WAITING_TO_BE_CLOSED).equals(petStatus.trim())
						&& !(GroupingConstants.PET_STATUS_PUBLISH_TO_WEB).equals(petStatus.trim())) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("SCG. Not Completed Pet Status not Completed. Orin No: " + groupAttributeForm.getOrinNumber()
								+ ", PetStatus: " + petStatus);
					}
					updatedSplitColorDetailsList = new ArrayList<>();
					errorCode = "notCompleted";
					break;
				}
				if (null != entryType && !("Style").equals(entryType)) {
					groupAttributeForm.setProdName(productName);
					updatedSplitColorDetailsList.add(groupAttributeForm);
					errorCode = "dataAvailable";
				}
			}
			validateMap.put(errorCode, updatedSplitColorDetailsList);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling validateSCGAttributeDetails");
		return validateMap;
	}

	/**
	 * This method is used to validate Split SKU Attribute list before add it to
	 * group.
	 * 
	 * @param getSplitColorDetailsList
	 * @return validateMap
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final Map<String, List<GroupAttributeForm>> validateSSGAttributeDetails(final List<GroupAttributeForm> getSplitSKUDetailsList)
			throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("Enter-->calling validateSSGAttributeDetails");
		List<GroupAttributeForm> updatedgetSplitSKUDetailsList = new ArrayList<>();
		GroupAttributeForm groupAttributeForm = null;
		String productName = "";
		String errorCode = "noData";
		Map<String, List<GroupAttributeForm>> validateMap = new HashMap<>();
		try {

			for (int i = 0; i < getSplitSKUDetailsList.size(); i++) {
				groupAttributeForm = getSplitSKUDetailsList.get(i);
				String entryType = groupAttributeForm.getEntryType();
				if (null != entryType && ("Style").equals(entryType)) {
					productName = groupAttributeForm.getProdName();
					break;
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Style ProductName is -->" + productName);
			}

			for (int i = 0; i < getSplitSKUDetailsList.size(); i++) {
				groupAttributeForm = getSplitSKUDetailsList.get(i);
				String entryType = groupAttributeForm.getEntryType();
				String petStatus = groupAttributeForm.getPetStatus();
				if (null != petStatus && !(GroupingConstants.PET_STATUS_COMPLETED).equals(petStatus.trim())
						&& !(GroupingConstants.PET_STATUS_CLOSED).equals(petStatus.trim())
						&& !(GroupingConstants.PET_STATUS_WAITING_TO_BE_CLOSED).equals(petStatus.trim())
						&& !(GroupingConstants.PET_STATUS_PUBLISH_TO_WEB).equals(petStatus.trim())) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("SSG. Not Completed Pet Status not Completed. Orin No: " + groupAttributeForm.getOrinNumber()
								+ ", PetStatus: " + petStatus);
					}
					updatedgetSplitSKUDetailsList = new ArrayList<>();
					errorCode = "notCompleted";
					break;
				}
				if (null != entryType && (!("Style").equals(entryType)) && !("StyleColor").equals(entryType)) {
					groupAttributeForm.setProdName(productName);
					updatedgetSplitSKUDetailsList.add(groupAttributeForm);
					errorCode = "dataAvailable";
				}
			}
			validateMap.put(errorCode, updatedgetSplitSKUDetailsList);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling validateSSGAttributeDetails");
		return validateMap;
	}

	/**
	 * This method is used to validate CPG Attribute list before add it to
	 * group.
	 * 
	 * @param existClassId
	 * @param getCPGSelectedAttrbuteList
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final String validateCPGAttributeDetails(final String existClassId, final List<StyleAttributeForm> getCPGSelectedAttrbuteList)
			throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("Enter-->calling validateCPGAttributeDetails. existClassId-->" + existClassId);
		String message = "";
		String classIdSelectedPrev = "";
		StyleAttributeForm styleAttributeForm = null;
		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);
		try {
			String existClassIdSt = GroupingUtil.checkNull(existClassId);
			for (int i = 0; i < getCPGSelectedAttrbuteList.size(); i++) {
				styleAttributeForm = getCPGSelectedAttrbuteList.get(i);
				String classIdSelected = GroupingUtil.checkNull(styleAttributeForm.getClassId());
				if ("".equals(classIdSelectedPrev)) {
					classIdSelectedPrev = classIdSelected;
				}
				if (!("").equals(existClassIdSt) && !existClassIdSt.equals(classIdSelected)) {
					message = prop.getProperty(GroupingConstants.MESSAGE_CPG_VALIDATION_DIFF_CLASS_ID_ONE) + " " + classIdSelected
							+ prop.getProperty(GroupingConstants.MESSAGE_CPG_VALIDATION_DIFF_CLASS_ID_TWO) + " " + existClassIdSt
							+ prop.getProperty(GroupingConstants.MESSAGE_CPG_VALIDATION_DIFF_CLASS_ID_THREE);
					break;
				}
				if (!classIdSelected.equals(classIdSelectedPrev)) {
					message = prop.getProperty(GroupingConstants.MESSAGE_CPG_VALIDATION_DIFF_CLASS_ID_FOUR);
					break;
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("CPG Validation message is -->" + message);
			}

		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling validateCPGAttributeDetails");
		return message;
	}

	/**
	 * Method to pass JSON Array to call the Add Component service for Split
	 * Color Group.
	 * 
	 * @param groupIdRes
	 * @param groupType
	 * @param updatedBy
	 * @param selectedSplitAttributeList
	 * @return jsonObj
	 * @author Cognizant
	 */
	public final JSONObject populateAddComponentSCGJson(final String groupIdRes, final String groupType, final String updatedBy,
			final List<GroupAttributeForm> selectedSplitAttributeList) {
		LOGGER.info("Entering populateAddComponentSCGJson-->.");

		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObjComponent = null;
		GroupAttributeForm groupAttributeForm = null;
		JSONArray jsonArray = new JSONArray();
		try {

			for (int i = 0; i < selectedSplitAttributeList.size(); i++) {
				jsonObjComponent = new JSONObject();
				groupAttributeForm = selectedSplitAttributeList.get(i);
				jsonObjComponent.put(GroupingConstants.COMPONENT_ID_ATTR, groupAttributeForm.getOrinNumber());
				jsonObjComponent.put(GroupingConstants.COMPONENT_IS_DEFAULT_ATTR, groupAttributeForm.getIsDefault());
				jsonArray.put(jsonObjComponent);
			}

			jsonObj.put(GroupingConstants.GROUP_ID, groupIdRes);
			jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
			jsonObj.put(GroupingConstants.COMPONENT_LIST, jsonArray);
			jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy);
		} catch (JSONException e) {
			LOGGER.error("Exeception in parsing the jsonObj-->" + e);
		}
		LOGGER.info("Exiting populateAddComponentSCGJson-->" + jsonObj);
		return jsonObj;
	}

	/**
	 * Method to pass JSON Array to call the Add Component service for CPG
	 * Group.
	 * 
	 * @param groupIdRes
	 * @param groupType
	 * @param updatedBy
	 * @param getCPGSelectedAttrbuteList
	 * @return jsonObj
	 * @author Cognizant
	 */
	public final JSONObject populateAddComponentCPGJson(final String groupIdRes, final String groupType, final String updatedBy,
			final List<StyleAttributeForm> getCPGSelectedAttrbuteList) {
		LOGGER.info("Entering populateAddComponentCPGJson-->.");

		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObjComponent = null;
		StyleAttributeForm styleAttributeForm = null;
		JSONArray jsonArray = new JSONArray();
		try {
			for (int i = 0; i < getCPGSelectedAttrbuteList.size(); i++) {
				jsonObjComponent = new JSONObject();
				styleAttributeForm = getCPGSelectedAttrbuteList.get(i);
				jsonObjComponent.put(GroupingConstants.COMPONENT_ATTR, styleAttributeForm.getOrinNumber());
				jsonArray.put(jsonObjComponent);
			}

			jsonObj.put(GroupingConstants.GROUP_ID, groupIdRes);
			jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy);
			jsonObj.put(GroupingConstants.COMPONENT_LIST, jsonArray);
		} catch (JSONException e) {
			LOGGER.error("Exeception in parsing the jsonObj-->" + e);
		}
		LOGGER.info("Exiting populateAddComponentCPGJson-->" + jsonObj);
		return jsonObj;
	}

	/**
	 * Method to pass JSON Array to call the Add Component service for GBS
	 * Group.
	 * 
	 * @param groupIdRes
	 * @param groupType
	 * @param updatedBy
	 * @param getCPGSelectedAttrbuteList
	 * @return jsonObj
	 * @author Cognizant
	 */
	public final JSONObject populateAddComponentGBSJson(final String groupIdRes, final String groupType, final String updatedBy,
			final List<GroupAttributeForm> getGBSSelectedAttrbuteList) {
		LOGGER.info("Entering populateAddComponentGBSJson-->.");

		/*
		 * { "groupId":"12345676", "groupType":"RCG/GSS/BCG",
		 * "modifiedBy":"AFUPRS", "componentList" :[{"id":"23098765001",
		 * "type":"CPG",
		 * "component":[{"Style":"st1","ColorList"[{"color":"clr1"}
		 * ,{"color":"clr2"}]},
		 * {"Style":"st2","ColorList"[{"color":"clr1"},{"color":"clr2"}]}] },
		 * {"id":"23098765",
		 * "type":"Style","ColorList"[{"color":"clr1"},{"color":"clr2"}]}] }
		 */
		JSONObject jsonObj = new JSONObject();
		// JSONObject jsonObjComponent = null;
		GroupAttributeForm groupAttributeForm = null;
		JSONArray jsonArrayComponentList = new JSONArray();
		JSONObject jsonObjStyle = null;

		try {

			for (int i = 0; i < getGBSSelectedAttrbuteList.size(); i++) {
				jsonObjStyle = new JSONObject();
				groupAttributeForm = getGBSSelectedAttrbuteList.get(i);
				jsonObjStyle.put(GroupingConstants.COMPONENT_ID, groupAttributeForm.getParentMdmid());
				jsonObjStyle.put(GroupingConstants.COMPONENT_TYPE, GroupingConstants.COMPONENT_TYPE_STYLE);
				// if ColorList is available add here with in a new json array
				jsonArrayComponentList.put(jsonObjStyle);
			}

			jsonObj.put(GroupingConstants.GROUP_ID, groupIdRes);
			jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
			jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy);
			jsonObj.put(GroupingConstants.COMPONENT_LIST, jsonArrayComponentList);
		} catch (JSONException e) {
			LOGGER.error("Exeception in parsing the jsonObj-->" + e);
		}
		LOGGER.info("Exiting populateAddComponentGBSJson-->");
		return jsonObj;
	}

	/**
	 * Method to pass JSON Array to call the Add Component service for RCG and
	 * BCG Group.
	 * 
	 * @param groupIdRes
	 * @param groupType
	 * @param updatedBy
	 * @param getCPGSelectedAttrbuteList
	 * @return jsonObj
	 * @author Cognizant
	 */
	public final JSONObject populateAddComponentRCGBCGJson(final String groupIdRes, final String groupType, final String updatedBy,
			final List<StyleAttributeForm> getRCGSelectedAttrbuteList) {
		LOGGER.info("Entering populateAddComponentRCGBCGJson-->.");

		/*
		 * { "groupId":"12345676", "groupType":"RCG/GSS/BCG",
		 * "modifiedBy":"AFUPRS", "componentList" :[{"id":"2309876",
		 * "type":"CPG",
		 * "component":[{"Style":"st1","ColorList"[{"color":"clr1"}
		 * ,{"color":"clr2"}]},
		 * {"Style":"st2","ColorList"[{"color":"clr1"},{"color":"clr2"}]}] },
		 * {"id":"23098765",
		 * "type":"Style","ColorList"[{"color":"clr1"},{"color":"clr2"}]}] }
		 */
		JSONObject jsonObj = new JSONObject();
		GroupAttributeForm groupAttributeForm = null;
		StyleAttributeForm styleAttributeForm = null;
		JSONArray jsonArrayComponentList = new JSONArray();
		JSONObject jsonObjStyleGrp = null;
		JSONObject jsonObjColor = null;
		JSONArray jsonArrayColorList = null;
		List<GroupAttributeForm> groupAttributeFormList = new ArrayList<>();

		try {

			for (int i = 0; i < getRCGSelectedAttrbuteList.size(); i++) {
				jsonObjStyleGrp = new JSONObject();
				styleAttributeForm = getRCGSelectedAttrbuteList.get(i);
				String styleOrGrpNo = GroupingUtil.checkNull(styleAttributeForm.getOrinNumber());
				groupAttributeFormList = styleAttributeForm.getGroupAttributeFormList();

				if (styleOrGrpNo.length() == 7) {
					// Group
					jsonObjStyleGrp.put(GroupingConstants.COMPONENT_ID, styleOrGrpNo);
					jsonObjStyleGrp.put(GroupingConstants.COMPONENT_TYPE, styleAttributeForm.getEntryType());
				} else {
					// Style
					jsonArrayColorList = new JSONArray();

					jsonObjStyleGrp.put(GroupingConstants.COMPONENT_ID, styleOrGrpNo);
					jsonObjStyleGrp.put(GroupingConstants.COMPONENT_TYPE, GroupingConstants.COMPONENT_TYPE_STYLE);
					// if ColorList is available add here with in a new json
					// array
					if (GroupingConstants.GROUP_TYPE_BEAUTY_COLLECTION.equals(groupType) && null != groupAttributeFormList
							&& groupAttributeFormList.size() > 0) {
						for (int j = 0; j < groupAttributeFormList.size(); j++) {
							groupAttributeForm = groupAttributeFormList.get(j);
							jsonObjColor = new JSONObject();
							jsonObjColor.put(GroupingConstants.COMPONENT_COLOR, groupAttributeForm.getColorCode());
							jsonArrayColorList.put(jsonObjColor);
						}
						jsonObjStyleGrp.put(GroupingConstants.COMPONENT_COLOR_LIST, jsonArrayColorList);
					}
				}
				jsonArrayComponentList.put(jsonObjStyleGrp);
			}

			jsonObj.put(GroupingConstants.GROUP_ID, groupIdRes);
			jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
			jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy);
			jsonObj.put(GroupingConstants.COMPONENT_LIST, jsonArrayComponentList);
		} catch (JSONException e) {
			LOGGER.error("Exeception in parsing the jsonObj-->" + e);
		}
		LOGGER.info("Exiting populateAddComponentRCGBCGJson-->" + jsonObj);
		return jsonObj;
	}

	/**
	 * This method is used to call Add Component to CPG Service.
	 * 
	 * @param jsonStyleSpliColor
	 * @return responseMsg
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	public final String callAddComponentService(final JSONObject jsonCpgComponent, final String groupType) throws MalformedURLException,
			ClassCastException, IOException, JSONException {
		LOGGER.info("Entering callAddComponentService-->.");

		String responseMsg = "";
		String serviceURL = "";
		BufferedReader responseBuffer = null;
		HttpURLConnection httpConnection = null;
		try {
			Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);

			if (GroupingConstants.GROUP_TYPE_SPLIT_COLOR.equals(groupType)) {
				serviceURL = prop.getProperty(GroupingConstants.ADD_COMPONENT_TO_SCG_SERVICE_URL);
			} else if (GroupingConstants.GROUP_TYPE_SPLIT_SKU.equals(groupType)) {
				serviceURL = prop.getProperty(GroupingConstants.ADD_COMPONENT_TO_SSG_SERVICE_URL);
			} else if (GroupingConstants.GROUP_TYPE_CONSOLIDATE_PRODUCT.equals(groupType)) {
				serviceURL = prop.getProperty(GroupingConstants.ADD_COMPONENT_TO_CPG_SERVICE_URL);
			} else if (GroupingConstants.GROUP_TYPE_GROUP_BY_SIZE.equals(groupType)
					|| GroupingConstants.GROUP_TYPE_REGULAR_COLLECTION.equals(groupType)
					|| GroupingConstants.GROUP_TYPE_BEAUTY_COLLECTION.equals(groupType)) {
				serviceURL = prop.getProperty(GroupingConstants.ADD_COMPONENT_TO_COLLECTION_SERVICE_URL);
			}
			LOGGER.info("Add Component to Group ServiceURL-->" + serviceURL);

			httpConnection = getServiceConnection(serviceURL);

			LOGGER.info("callAddComponentService Service::Json Array-->" + jsonCpgComponent.toString());

			String input = jsonCpgComponent.toString();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes(GroupingConstants.DEFAULT_CHARSET));
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), GroupingConstants.DEFAULT_CHARSET));
			String output;
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("call callAddComponentService Service Output-->" + output);

				responseMsg = output;
			}

		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new MalformedURLException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new ClassCastException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new JSONException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new IOException(e.getMessage());
		} finally {
			if (null != httpConnection) {
				httpConnection.disconnect();
			}
			if (responseBuffer != null) {
				responseBuffer.close();
			}
		}
		LOGGER.info("Exiting callAddComponentCPGService-->" + responseMsg);
		return responseMsg;
	}

	/**
	 * Method to pass JSON Array to call the Add Component service for Split SKU
	 * Group.
	 * 
	 * @param groupIdRes
	 * @param groupType
	 * @param updatedBy
	 * @param selectedSplitAttributeList
	 * @return jsonObj
	 * @author Cognizant
	 */
	public final JSONObject populateAddComponentSSGJson(final String groupIdRes, final String groupType, final String updatedBy,
			final List<GroupAttributeForm> selectedSplitAttributeList) {
		LOGGER.info("Entering populateAddComponentSSGJson-->.");

		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObjComponent = null;
		GroupAttributeForm groupAttributeForm = null;
		JSONArray jsonArray = new JSONArray();
		try {

			for (int i = 0; i < selectedSplitAttributeList.size(); i++) {
				jsonObjComponent = new JSONObject();
				groupAttributeForm = selectedSplitAttributeList.get(i);
				jsonObjComponent.put(GroupingConstants.COMPONENT_ID_ATTR, groupAttributeForm.getParentMdmid());
				jsonObjComponent.put(GroupingConstants.COMPONENT_IS_DEFAULT_ATTR, groupAttributeForm.getIsDefault());
				jsonObjComponent.put(GroupingConstants.COMPONENT_COLOR, groupAttributeForm.getColorCode());
				jsonObjComponent.put(GroupingConstants.COMPONENT_SIZE, groupAttributeForm.getSizeCode());
				jsonObjComponent.put(GroupingConstants.COMPONENT_SKU, groupAttributeForm.getOrinNumber());
				jsonArray.put(jsonObjComponent);
			}

			jsonObj.put(GroupingConstants.GROUP_ID, groupIdRes);
			jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
			jsonObj.put(GroupingConstants.COMPONENT_LIST, jsonArray);
			jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy);
		} catch (JSONException e) {
			LOGGER.error("Exeception in parsing the jsonObj-->" + e);
		}
		LOGGER.info("Exiting populateAddComponentSSGJson-->" + jsonObj);
		return jsonObj;
	}

	/**
	 * This method is used to get the selected Attribute List for SCG.
	 * 
	 * @param updatedSplitColorDetailsList
	 * @param selectedItemsArr
	 * @param defaultSelectedAttr
	 * @return selectedSplitAttributeList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<GroupAttributeForm> getSelectedColorAttributeList(final List<GroupAttributeForm> updatedSplitColorDetailsList,
			final String[] selectedItemsArr, final String defaultSelectedAttr) throws PEPServiceException, PEPPersistencyException {

		LOGGER.info("*Enter-->calling getSelectedColorAttributeList from GroupingServiceImpl.");
		List<GroupAttributeForm> selectedSplitAttributeList = new ArrayList<>();
		GroupAttributeForm groupAttributeForm = null;
		String colorCode = "";
		for (int i = 0; i < updatedSplitColorDetailsList.size(); i++) {
			groupAttributeForm = updatedSplitColorDetailsList.get(i);
			colorCode = null == groupAttributeForm.getColorCode() ? "" : groupAttributeForm.getColorCode().trim();
			if (colorCode.equals(defaultSelectedAttr)) {
				groupAttributeForm.setIsDefault("yes");
			} else {
				groupAttributeForm.setIsDefault("no");
			}
			for (int j = 0; j < selectedItemsArr.length; j++) {
				if (selectedItemsArr[j].equals(colorCode)) {
					selectedSplitAttributeList.add(groupAttributeForm);
					break;
				}
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("selectedSplitAttributeList.size()-->" + selectedSplitAttributeList.size());
		}
		LOGGER.info("Exit-->calling getSelectedColorAttributeList from GroupingServiceImpl.");
		return selectedSplitAttributeList;
	}

	/**
	 * This method is used to get the selected Attribute List for SSG.
	 * 
	 * @param updatedSplitSKUDetailsList
	 * @param selectedItemsArr
	 * @param defaultSelectedAttr
	 * @return selectedSplitAttributeList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<GroupAttributeForm> getSelectedSKUAttributeList(final List<GroupAttributeForm> updatedSplitSKUDetailsList,
			final String[] selectedItemsArr, final String defaultSelectedAttr) throws PEPServiceException, PEPPersistencyException {
		// getting the Style Color details from Database
		LOGGER.info("*Enter-->calling getSelectedSKUAttributeList from GroupingServiceImpl.");
		List<GroupAttributeForm> selectedSplitAttributeList = new ArrayList<>();
		GroupAttributeForm groupAttributeForm = null;
		String sizeCode = "";
		String colorCode = "";
		String selectedAttr = "";
		String[] selectedAttrArr = null;
		String[] defaultSelectedAttrArr = defaultSelectedAttr.split("_");
		for (int i = 0; i < updatedSplitSKUDetailsList.size(); i++) {
			groupAttributeForm = updatedSplitSKUDetailsList.get(i);
			sizeCode = null == groupAttributeForm.getSize() ? "" : groupAttributeForm.getSize().trim();
			colorCode = null == groupAttributeForm.getColorCode() ? "" : groupAttributeForm.getColorCode().trim();

			if (defaultSelectedAttrArr[0].equals(colorCode) && defaultSelectedAttrArr[1].equals(sizeCode)) {
				groupAttributeForm.setIsDefault("yes");
			} else {
				groupAttributeForm.setIsDefault("no");
			}
			for (int j = 0; j < selectedItemsArr.length; j++) {
				selectedAttr = GroupingUtil.checkNull(selectedItemsArr[j]);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Split SKU selectedAttr-->" + selectedAttr);
				}
				selectedAttrArr = selectedAttr.split("_");
				if (selectedAttrArr[0].equals(colorCode) && selectedAttrArr[1].equals(sizeCode)) {
					selectedSplitAttributeList.add(groupAttributeForm);
					break;
				}
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("selectedSplitAttributeList.size()-->" + selectedSplitAttributeList.size());
		}
		LOGGER.info("Exit-->calling getSelectedSKUAttributeList from GroupingServiceImpl.");
		return selectedSplitAttributeList;
	}

	/**
	 * This method is used to get the selected Attribute List for CPG.
	 * 
	 * @param updatedSplitSKUDetailsList
	 * @param selectedItemsArr
	 * @param defaultSelectedAttr
	 * @return selectedSplitAttributeList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<StyleAttributeForm> getSelectedCPGAttributeList(final List<StyleAttributeForm> getCPGDetailsList,
			final String[] selectedItemsArr, final String defaultSelectedAttr) throws PEPServiceException, PEPPersistencyException {
		// getting the Style Color details from Database
		LOGGER.info("*Enter-->calling getSelectedCPGAttributeList from GroupingServiceImpl.");
		List<StyleAttributeForm> selectedAttributeList = new ArrayList<>();
		StyleAttributeForm styleAttributeForm = null;
		String mdmId = "";
		for (int i = 0; i < getCPGDetailsList.size(); i++) {
			styleAttributeForm = getCPGDetailsList.get(i);
			mdmId = null == styleAttributeForm.getOrinNumber() ? "" : styleAttributeForm.getOrinNumber().trim();

			for (int j = 0; j < selectedItemsArr.length; j++) {

				if (selectedItemsArr[j].equals(mdmId)) {
					selectedAttributeList.add(styleAttributeForm);
					break;
				}
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("selectedAttributeList.size()-->" + selectedAttributeList.size());
		}
		LOGGER.info("Exit-->calling getSelectedCPGAttributeList from GroupingServiceImpl.");
		return selectedAttributeList;
	}

	/**
	 * This method is used to get the selected Attribute List for GBS.
	 * 
	 * @param getGBSDetailsList
	 * @param selectedItemsArr
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<GroupAttributeForm> getSelectedGBSAttributeList(final List<GroupAttributeForm> getGBSDetailsList,
			final String[] selectedItemsArr) throws PEPServiceException, PEPPersistencyException {

		LOGGER.info("*Enter-->calling getSelectedGBSAttributeList from GroupingServiceImpl.");
		List<GroupAttributeForm> selectedGBSAttributeList = new ArrayList<>();
		GroupAttributeForm groupAttributeForm = null;
		String styleOrinNo = "";
		for (int i = 0; i < getGBSDetailsList.size(); i++) {
			groupAttributeForm = getGBSDetailsList.get(i);
			styleOrinNo = null == groupAttributeForm.getParentMdmid() ? "" : groupAttributeForm.getParentMdmid().trim();
			for (int j = 0; j < selectedItemsArr.length; j++) {
				if (selectedItemsArr[j].equals(styleOrinNo)) {
					selectedGBSAttributeList.add(groupAttributeForm);
					break;
				}
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("selectedSplitAttributeList.size()-->" + selectedGBSAttributeList.size());
		}
		LOGGER.info("Exit-->calling getSelectedGBSAttributeList from GroupingServiceImpl.");
		return selectedGBSAttributeList;
	}

	/**
	 * This method is used to get the selected Attribute List for RCG.
	 * 
	 * @param getGBSDetailsList
	 * @param selectedItemsArr
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<StyleAttributeForm> getSelectedRCGAttributeList(final List<StyleAttributeForm> getRCGDetailsList,
			final String[] selectedItemsArr) throws PEPServiceException, PEPPersistencyException {

		LOGGER.info("*Enter-->calling getSelectedRCGAttributeList from GroupingServiceImpl.");
		List<StyleAttributeForm> selectedRCGAttributeList = new ArrayList<>();
		StyleAttributeForm styleAttributeForm = null;
		String styleOrinNo = "";
		for (int i = 0; i < getRCGDetailsList.size(); i++) {
			styleAttributeForm = getRCGDetailsList.get(i);
			styleOrinNo = null == styleAttributeForm.getOrinNumber() ? "" : styleAttributeForm.getOrinNumber().trim();
			for (int j = 0; j < selectedItemsArr.length; j++) {
				String selectedOrin = GroupingUtil.checkNull(selectedItemsArr[j]);
				if (selectedOrin.indexOf(":") != -1) {
					selectedOrin = selectedOrin.substring(0, selectedOrin.indexOf(":"));
				}
				if (selectedOrin.equals(styleOrinNo)) {
					selectedRCGAttributeList.add(styleAttributeForm);
					break;
				}
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getSelectedRCGAttributeList.size()-->" + selectedRCGAttributeList.size());
		}
		LOGGER.info("Exit-->calling getSelectedRCGAttributeList from GroupingServiceImpl.");
		return selectedRCGAttributeList;
	}

	/**
	 * This method is used to get the selected Attribute List for RCG.
	 * 
	 * @param getGBSDetailsList
	 * @param selectedItemsArr
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<StyleAttributeForm> getSelectedBCGAttributeList(final List<StyleAttributeForm> getRCGDetailsList,
			final String[] selectedItemsArr) throws PEPServiceException, PEPPersistencyException {

		LOGGER.info("*Enter-->calling getSelectedBCGAttributeList from GroupingServiceImpl.-->" + selectedItemsArr);
		List<StyleAttributeForm> selectedRCGAttributeList = new ArrayList<>();
		StyleAttributeForm styleAttributeForm = null;
		List<GroupAttributeForm> groupAttributeFormList = new ArrayList<>();
		List<GroupAttributeForm> groupAttributeFormListNew = new ArrayList<>();
		GroupAttributeForm groupAttributeForm = null;
		String styleOrinGrpNo = "";
		// boolean isColorSelected = false;
		String[] selectedItemsColorArr = null;
		for (int i = 0; i < getRCGDetailsList.size(); i++) {
			styleAttributeForm = getRCGDetailsList.get(i);
			styleOrinGrpNo = GroupingUtil.checkNull(styleAttributeForm.getOrinNumber());
			groupAttributeFormList = styleAttributeForm.getGroupAttributeFormList();
			LOGGER.debug("styleOrinGrpNo-->" + styleOrinGrpNo);
			for (int j = 0; j < selectedItemsArr.length; j++) {
				// isColorSelected = false;
				// 100000520:100000520400
				String selectedOrinGrpNo = GroupingUtil.checkNull(selectedItemsArr[j]);
				if (selectedOrinGrpNo.indexOf(":") != -1) {
					// isColorSelected = true;
					String selectedOrinGrp = selectedOrinGrpNo.substring(0, selectedOrinGrpNo.indexOf(":"));
					// LOGGER.debug("selectedOrinGrpNo-->"+selectedOrinGrp);
					String colorList = GroupingUtil.checkNull(selectedOrinGrpNo.substring(selectedOrinGrpNo.indexOf(":") + 1));
					// LOGGER.debug("colorList-->"+colorList);
					selectedItemsColorArr = colorList.split("-");
					// LOGGER.debug("selectedItemsColorArr.length-->"+selectedItemsColorArr.length);
					groupAttributeFormListNew = new ArrayList<>();
					if (selectedOrinGrp.equals(styleOrinGrpNo)) {
						for (int k = 0; k < groupAttributeFormList.size(); k++) {
							groupAttributeForm = groupAttributeFormList.get(k);

							for (int l = 0; l < selectedItemsColorArr.length; l++) {
								// LOGGER.debug("selectedItemsColorArr[l]-->"+selectedItemsColorArr[l]);
								if (selectedItemsColorArr[l].equals(groupAttributeForm.getOrinNumber())) {
									groupAttributeFormListNew.add(groupAttributeForm);
									break;
								}
							}
						}
						styleAttributeForm.setGroupAttributeFormList(groupAttributeFormListNew);
						selectedRCGAttributeList.add(styleAttributeForm);
						break;
					}
				} else {
					if (selectedOrinGrpNo.equals(styleOrinGrpNo)) {
						selectedRCGAttributeList.add(styleAttributeForm);
						break;
					}
				}
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getSelectedBCGAttributeList.size()-->" + selectedRCGAttributeList.size());
		}
		LOGGER.info("Exit-->calling getSelectedBCGAttributeList from GroupingServiceImpl.");
		return selectedRCGAttributeList;
	}

	/**
	 * This method is used to get the Existing Group Details.
	 * 
	 * @param groupId
	 * @return createGroupForm
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	public final CreateGroupForm getExistingGrpDetails(final String groupId) throws PEPFetchException, ParseException {
		LOGGER.info("Entering getExistingGrpDetails-->.");

		// Call DAO to fetch Group Details after getting response from service
		CreateGroupForm createGroupForm = new CreateGroupForm();
		CreateGroupDTO createGroupDTO = new CreateGroupDTO();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Before Calling database method getGroupHeaderDetails() to retreive Group Header Details-->.");
		}
		createGroupDTO = groupingDAO.getGroupHeaderDetails(groupId);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("After Calling database method getGroupHeaderDetails() to retreive Group Header Details-->"
					+ createGroupDTO.getGroupId());

		}

		createGroupForm.setGroupId(createGroupDTO.getGroupId());
		createGroupForm.setGroupName(createGroupDTO.getGroupName());
		createGroupForm.setGroupType(createGroupDTO.getGroupType());
		createGroupForm.setGroupDesc(createGroupDTO.getGroupDesc());
		createGroupForm.setGroupLaunchDate(createGroupDTO.getGroupLaunchDate());
		createGroupForm.setEndDate(createGroupDTO.getEndDate());
		createGroupForm.setGroupCretionMsg("");
		createGroupForm.setGroupCreationStatus("");
		createGroupForm.setGroupStatus(createGroupDTO.getGroupStatus());
		createGroupForm.setCarsGroupType(createGroupDTO.getCarsGroupType());
		createGroupForm.setIsAlreadyInGroup(createGroupDTO.getIsAlreadyInGroup());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer object value from DTO to Form Object end.");
		}
		/** **/

		LOGGER.info("Exist getExistingGrpDetails-->.");
		return createGroupForm;
	}

	/**
	 * Method to get the groups for search group.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @return GroupSearchForm
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/19/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 */
	@Override
	public final GroupSearchForm groupSearch(final GroupSearchForm groupSearchForm) throws PEPServiceException, PEPPersistencyException {

		LOGGER.info("Entering groupSearch() in GroupingService class.");

		List<GroupForm> groupFormList = new ArrayList<>();
		List<GroupSearchDTO> listOneGroup = groupingDAO.groupSearch(groupSearchForm);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("List one size -- " + listOneGroup.size());
		}
		if (listOneGroup.size() > 0) {
			GroupForm groupFormParent = null;
			Map<String, GroupForm> mapGroup = new HashMap<>();
			Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);
			for (Iterator<GroupSearchDTO> iterator = listOneGroup.iterator(); iterator.hasNext();) {
				GroupSearchDTO groupSearchDTOParent = (GroupSearchDTO) iterator.next();
				if (groupSearchDTOParent != null) {
					groupFormParent = new GroupForm();
					groupFormParent.setGroupId(groupSearchDTOParent.getGroupId());
					groupFormParent.setGroupName(groupSearchDTOParent.getGroupName());
					groupFormParent.setGroupContentStatus(groupSearchDTOParent.getGroupContentStatus());
					groupFormParent.setGroupImageStatus(groupSearchDTOParent.getGroupImageStatus());
					groupFormParent.setGroupType(prop.getProperty("group.types." + groupSearchDTOParent.getGroupType()));
					groupFormParent.setGroupTypeCode(groupSearchDTOParent.getGroupType());
					groupFormParent.setChildGroup(groupSearchDTOParent.getChildGroup());
					if (groupSearchDTOParent.getStartDate().length() >= GroupingConstants.DATE_FIELD_LENGTH) {
						groupFormParent.setStartDate(groupSearchDTOParent.getStartDate().substring(0, GroupingConstants.DATE_FIELD_LENGTH));
					} else {
						groupFormParent.setStartDate(groupSearchDTOParent.getStartDate());
					}
					if (groupSearchDTOParent.getEndDate().length() >= GroupingConstants.DATE_FIELD_LENGTH) {
						groupFormParent.setEndDate(groupSearchDTOParent.getEndDate().substring(0, GroupingConstants.DATE_FIELD_LENGTH));
					} else {
						groupFormParent.setEndDate(groupSearchDTOParent.getEndDate());
					}
					mapGroup.put(groupFormParent.getGroupId(), groupFormParent);
					groupFormList.add(groupFormParent);
				}
			}
			if (groupSearchForm.getGroupId() != null && !"".equals(groupSearchForm.getGroupId().trim())) {
				List<GroupSearchDTO> listTwoGroup = groupingDAO.groupSearchParent(listOneGroup, groupSearchForm);
				int parentCount = groupingDAO.groupSearchParentCount(listOneGroup, groupSearchForm);
				groupSearchForm.setParentCount(parentCount);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("List two size -- " + listTwoGroup.size());
				}
				if (listTwoGroup.size() > 0) {
					GroupForm groupForm = null;

					List<GroupForm> parentGroupFormList = new ArrayList<>();
					for (Iterator<GroupSearchDTO> iterator = listTwoGroup.iterator(); iterator.hasNext();) {
						GroupSearchDTO groupSearchDTO = (GroupSearchDTO) iterator.next();
						if (groupSearchDTO != null) {
							groupForm = new GroupForm();

							groupForm.setGroupId(groupSearchDTO.getGroupId());
							groupForm.setGroupName(groupSearchDTO.getGroupName());
							groupForm.setGroupContentStatus(groupSearchDTO.getGroupContentStatus());
							groupForm.setGroupImageStatus(groupSearchDTO.getGroupImageStatus());
							groupForm.setGroupType(prop.getProperty("group.types." + groupSearchDTO.getGroupType()));
							groupForm.setGroupTypeCode(groupSearchDTO.getGroupType());
							groupForm.setChildGroup(groupSearchDTO.getChildGroup());
							if (groupSearchDTO.getStartDate().length() >= GroupingConstants.DATE_FIELD_LENGTH) {
								groupForm.setStartDate(groupSearchDTO.getStartDate().substring(0, GroupingConstants.DATE_FIELD_LENGTH));
							} else {
								groupForm.setStartDate(groupSearchDTO.getStartDate());
							}
							if (groupSearchDTO.getEndDate().length() >= GroupingConstants.DATE_FIELD_LENGTH) {
								groupForm.setEndDate(groupSearchDTO.getEndDate().substring(0, GroupingConstants.DATE_FIELD_LENGTH));
							} else {
								groupForm.setEndDate(groupSearchDTO.getEndDate());
							}

							parentGroupFormList.add(groupForm);
						}
					}
					if (groupSearchForm.getPageNumber() > 1) {
						groupFormList = new ArrayList<>();
					}
					groupFormList.addAll(parentGroupFormList);
				}
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Final List size -- " + groupFormList.size());
			}
		}
		groupSearchForm.setGroupList(groupFormList);

		LOGGER.info("Exiting groupSearch() in GroupingService class.");
		return groupSearchForm;
	}

	/**
	 * Method to get Group search record count.
	 * 
	 * @param groupSearchForm GroupSearchForm
	 * @return resordCount int
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/27/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 * @throws PEPServiceException
	 */
	@Override
	public final int groupSearchCount(final GroupSearchForm groupSearchForm) throws PEPPersistencyException, PEPServiceException {
		LOGGER.info("Entering groupSearchCount() in GroupingService class.");
		int recordCount = 0;
		try {
			recordCount = groupingDAO.groupSearchCount(groupSearchForm);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at groupSearchCount() in GroupingService class-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exiting groupSearchCount() in GroupingService class.");
		return recordCount;
	}

	/**
	 * Method to get the Depts for search group.
	 * 
	 * @param departmentsToBesearched String
	 * @return ArrayList
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/26/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 * @throws PEPServiceException
	 */
	@Override
	public final ArrayList<DepartmentDetails> getDeptDetailsByDepNoFromADSE() throws PEPPersistencyException, PEPServiceException {
		LOGGER.info("Entering getDeptDetailsByDepNoFromADSE() in GroupingService class.");
		ArrayList<DepartmentDetails> getDepartmentList = null;
		try {
			getDepartmentList = groupingDAO.getDeptDetailsByDepNoFromADSE();
		} catch (Exception e) {
			LOGGER.error("Exception occurred at getDeptDetailsByDepNoFromADSE() in GroupingService class-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exiting getDeptDetailsByDepNoFromADSE() in GroupingService class.");
		return getDepartmentList;
	}

	/**
	 * Method to get the classes for search group.
	 * 
	 * @param departmentNumbers String
	 * @return List
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/26/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 * @throws PEPServiceException
	 */
	@Override
	public final List<ClassDetails> getClassDetailsByDepNos(final String departmentNumbers) throws PEPPersistencyException,
			PEPServiceException {
		LOGGER.info("Entering getClassDetailsByDepNos() in GroupingService class.");
		List<ClassDetails> getClassList = null;
		try {
			getClassList = groupingDAO.getClassDetailsByDepNos(departmentNumbers);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at getClassDetailsByDepNos() in GroupingService class-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exiting getClassDetailsByDepNos() in GroupingService class.");
		return getClassList;
	}

	/**
	 * 
	 * @param jsonGroup
	 * @return
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	private String callDeleteGroupService(final JSONObject jsonGroup) throws MalformedURLException, ClassCastException, IOException,
			JSONException {
		LOGGER.info("Entering callDeletGroupService-->.");

		String responseMsg = "";
		BufferedReader responseBuffer = null;
		HttpURLConnection httpConnection = null;
		try {
			Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop.getProperty(GroupingConstants.DELETE_GROUP_SERVICE_URL);
			LOGGER.info("Delete Group ServiceURL-->" + serviceURL);

			httpConnection = getServiceConnection(serviceURL);

			LOGGER.info("callDeletGroupService Service::Json Array-->" + jsonGroup.toString());

			String input = jsonGroup.toString();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes(GroupingConstants.DEFAULT_CHARSET));
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), GroupingConstants.DEFAULT_CHARSET));
			String output;
			LOGGER.info("Output from Server::: after Calling-->.");
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("DeleteGroupService Service Output-->" + output);

				responseMsg = output;
			}

		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new MalformedURLException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new ClassCastException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new JSONException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new IOException(e.getMessage());
		} finally {
			if (null != httpConnection) {
				httpConnection.disconnect();
			}
			if (responseBuffer != null) {
				responseBuffer.close();
			}
		}
		LOGGER.info("Exiting callCreateGroupService-->" + responseMsg);
		return responseMsg;
	}

	/**
	 * This method is used to call Group Delete Service.
	 * 
	 * @param groupId
	 * @param updatedBy
	 * @return String
	 * @throws PEPFetchException
	 * @throws IOException
	 * @throws JSONException
	 * @throws ClassCastException
	 * @throws MalformedURLException
	 */
	@Override
	public final String deleteGroup(final String groupId, final String groupType, final String updatedBy) throws PEPFetchException,
			MalformedURLException, ClassCastException, JSONException, IOException {
		LOGGER.info("Entering deleteGroup-->.");
		String responseMsg = "";
		String responseMsgCode = "";

		JSONObject jsonObj = new JSONObject();
		jsonObj.put(GroupingConstants.GROUP_ID, groupId);
		jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
		jsonObj.put(GroupingConstants.CREATED_BY, updatedBy);

		/** Calling Web Service to create Group except Split type **/
		String resMsg = callDeleteGroupService(jsonObj);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Delete Group Service message-->" + resMsg);
		}
		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);

		/** Extract Service message **/
		JSONObject jsonObjectRes = null;
		if (null != resMsg && !("").equals(resMsg)) {
			jsonObjectRes = new JSONObject(resMsg);
		}
		if (null != jsonObjectRes) {
			responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("resMsgGroupDelete.responseMsgCode-->" + responseMsgCode);
			LOGGER.debug("responseMsgCode-->" + responseMsgCode);
		}

		if (null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
			responseMsg = prop.getProperty(GroupingConstants.DELETE_GROUP_SERVICE_SUCCESS);
		} else {
			responseMsg = prop.getProperty(GroupingConstants.DELETE_GROUP_SERVICE_FAILURE);
		}

		LOGGER.info("Exist deleteGroup-->.");
		return responseMsg;
	}

	/**
	 * This method is used to get the Existing Style Color details.
	 * 
	 * @param groupId
	 * @return getSplitColorDetailsList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<GroupAttributeForm> getExistSplitColorDetails(final String groupId) throws PEPServiceException,
			PEPPersistencyException {
		LOGGER.info("Enter-->calling getExistSplitColorDetails from GroupingServiceImpl.");
		List<GroupAttributeForm> getSplitColorDetailsList = null;
		try {
			getSplitColorDetailsList = groupingDAO.getExistSplitColorDetails(groupId);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the getExistSplitColorDetails().Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getExistSplitColorDetails from GroupingServiceImpl.");
		return getSplitColorDetailsList;
	}

	/**
	 * This method is used to get the Existing Style Sku details.
	 * 
	 * @param groupId
	 * @return getSplitColorDetailsList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<GroupAttributeForm> getExistSplitSkuDetails(final String groupId) throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("Enter-->calling getSplitSkuDetailsList from GroupingServiceImpl.");
		List<GroupAttributeForm> getSplitSkuDetailsList = null;
		try {
			getSplitSkuDetailsList = groupingDAO.getExistSplitSkuDetails(groupId);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the getSplitSkuDetailsList().Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getSplitSkuDetailsList from GroupingServiceImpl.");
		return getSplitSkuDetailsList;
	}

	/**
	 * This method is used to get the Existing Consolidated Grouping details
	 * details.
	 * 
	 * @param groupId
	 * @return getSplitColorDetailsList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<StyleAttributeForm> getExistCPGDetails(final String groupId) throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("Enter-->calling getExistCPGDetails from GroupingServiceImpl.");

		List<StyleAttributeForm> styleAttributeFormList = null;
		try {
			styleAttributeFormList = groupingDAO.getExistCPGDetails(groupId);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the getExistCPGDetails().Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getExistCPGDetails from GroupingServiceImpl.");
		return styleAttributeFormList;
	}

	/**
	 * This method is used to get the Existing GSS details.
	 * 
	 * @param groupId
	 * @return getSplitColorDetailsList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<GroupAttributeForm> getExistGBSDetails(final String groupId) throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("Enter-->calling getExistGBSDetails from GroupingServiceImpl.");
		List<GroupAttributeForm> getGBSDetailsList = null;
		try {
			getGBSDetailsList = groupingDAO.getExistGBSDetails(groupId);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the getExistGBSDetails().Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getExistGBSDetails from GroupingServiceImpl.");
		return getGBSDetailsList;
	}

	/**
	 * This method is used to call add Component Service and fetch data from
	 * database.
	 * 
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
	public final CreateGroupForm addComponentToGroup(final String groupId, final String updatedBy, final String groupType,
			final List<GroupAttributeForm> selectedSplitAttributeList) throws PEPFetchException, MalformedURLException, ClassCastException,
			JSONException, IOException {
		LOGGER.info("Entering addComponentToGroup-->.");

		String responseMsg = "";
		String responseMsgCode = "";
		String groupCreationStatus = "";
		JSONObject jsonObjectRes = null;
		final Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);

		/** Call add component service to add Split Color attribute details List **/
		if (null != groupType && groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_COLOR)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Calling Add component for Split Color service start.");
			}
			// Create Split Color Group
			JSONObject jsonStyleSpliColor = populateAddComponentSCGJson(groupId, groupType, updatedBy, selectedSplitAttributeList);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Add Color Attribute JSON-->" + jsonStyleSpliColor);
				LOGGER.debug("addComponentToGroup.json Object Add Component to Split Color groupId--> "
						+ jsonStyleSpliColor.getString("groupId"));
			}
			final String resMsgSplitColor = callAddComponentService(jsonStyleSpliColor, groupType);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Add Component to Split Color Group Service message-->" + resMsgSplitColor);
			}

			/** Extract Service message **/
			if (null != resMsgSplitColor && !("").equals(resMsgSplitColor)) {
				jsonObjectRes = new JSONObject(resMsgSplitColor);
			}
			if (null != jsonObjectRes) {
				responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.resMsgSplitColor.responseMsgCode-->" + responseMsgCode);
			}

			if (null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
				responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_SUCCESS);
				groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_COMPONENT_SCG;
				LOGGER.info("addComponentToGroup.Add Component to Split Color Group. ResponseMsg100::Success-->" + responseMsg);
			} else {
				responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_FAILURE);
				groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_OUT_COMPONENT_SCG;
				LOGGER.info("addComponentToGroup.Add Component to Split Color Group. ResponseMsg101::Failure-->" + responseMsg);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Calling Add component for Split Color service end.");
			}
		}

		/** Call add component service to add split SKU attribute details List **/
		if (null != groupType && groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_SKU)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Calling Add component for Split SKU service start.");
			}
			// Create Split SKU Group
			LOGGER.debug("groupId-->" + groupId + " groupType-->" + groupType + " updatedBy--> " + updatedBy);
			JSONObject jsonStyleSpliSku = populateAddComponentSSGJson(groupId, groupType, updatedBy, selectedSplitAttributeList);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Add SKU Attribute JSON-->" + jsonStyleSpliSku);
				LOGGER.debug("addComponentToGroup.json Object Add Component to Split SKU groupId--> "
						+ jsonStyleSpliSku.getString("groupId"));
			}
			final String resMsgSplitSku = callAddComponentService(jsonStyleSpliSku, groupType);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Add Component to Split SKU Group Service message-->" + resMsgSplitSku);
			}

			/** Extract Service message **/
			if (null != resMsgSplitSku && !("").equals(resMsgSplitSku)) {
				jsonObjectRes = new JSONObject(resMsgSplitSku);
			}
			if (null != jsonObjectRes) {
				responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.resMsgSplitSku.responseMsgCode-->" + responseMsgCode);
			}

			if (null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
				responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_SUCCESS);
				groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_COMPONENT_SSG;
				LOGGER.info("addComponentToGroup.Add Component to Split SKU Group. ResponseMsg100::Success-->" + responseMsg);
			} else {
				responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_FAILURE);
				groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_OUT_COMPONENT_SSG;
				LOGGER.info("addComponentToGroup.Add Component to Split SKU Group. ResponseMsg101::Failure-->" + responseMsg);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Calling Add component for Split SKU service end.");
			}
		}
		/** End Component Addition for All Type **/

		// Call DAO to fetch Group Details after getting response from service
		CreateGroupForm createGroupForm = new CreateGroupForm();

		createGroupForm.setGroupId(groupId);
		createGroupForm.setGroupType(groupType);
		createGroupForm.setGroupCretionMsg(responseMsg);
		createGroupForm.setGroupCreationStatus(groupCreationStatus);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer object value from DTO to Form Object end.");

		}
		/** **/

		LOGGER.info("Exist addComponentToGroup-->.");
		return createGroupForm;
	}

	/**
	 * This method is used to call add CPG Component Service and fetch data from
	 * database.
	 * 
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
	public final CreateGroupForm addCPGComponentToGroup(final String groupId, final String updatedBy, final String groupType,
			final List<StyleAttributeForm> getCPGSelectedAttrbuteList) throws PEPFetchException, MalformedURLException, ClassCastException,
			JSONException, IOException {
		LOGGER.info("Entering addCPGComponentToGroup-->.");

		String responseMsg = "";
		String responseMsgCode = "";
		String groupCreationStatus = "";
		JSONObject jsonObjectRes = null;
		final Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);

		/** Call add component service to add CPG attribute details List **/
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addCPGComponentToGroup.Calling Add component for CPG service start.");
		}
		// Create CPG Group
		JSONObject jsonCpgComponent = populateAddComponentCPGJson(groupId, groupType, updatedBy, getCPGSelectedAttrbuteList);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addCPGComponentToGroup.Add CPG Attribute JSON-->" + jsonCpgComponent);
			LOGGER.debug("addCPGComponentToGroup.json Object Add Component to CPG groupId--> " + jsonCpgComponent.getString("groupId"));
		}
		final String resMsgCPG = callAddComponentService(jsonCpgComponent, groupType);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addCPGComponentToGroup.Add Component to CPG Group Service message-->" + resMsgCPG);
		}

		/** Extract Service message **/
		if (null != resMsgCPG && !("").equals(resMsgCPG)) {
			jsonObjectRes = new JSONObject(resMsgCPG);
		}
		if (null != jsonObjectRes) {
			responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addCPGComponentToGroup.resMsgSplitColor.responseMsgCode-->" + responseMsgCode);
		}

		if (null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
			responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_SUCCESS);
			groupCreationStatus = GroupingConstants.COMPONENT_ADDEDD_SUCCESSFULLY;
			LOGGER.info("addCPGComponentToGroup.Add Component to CPG Group. ResponseMsg100::Success-->" + responseMsg);
		} else {
			responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_FAILURE);
			groupCreationStatus = GroupingConstants.COMPONENT_ADDITION_FAILED;
			LOGGER.info("addCPGComponentToGroup.Add Component to CPG Group. ResponseMsg101::Failure-->" + responseMsg);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addCPGComponentToGroup.Calling Add component for CPG service end.");
		}
		/** End Component Addition for All Type **/

		// Call DAO to fetch Group Details after getting response from service
		CreateGroupForm createGroupForm = new CreateGroupForm();

		createGroupForm.setGroupId(groupId);
		createGroupForm.setGroupType(groupType);
		createGroupForm.setGroupCretionMsg(responseMsg);
		createGroupForm.setGroupCreationStatus(groupCreationStatus);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer object value from DTO to Form Object end.");

		}
		/** **/

		LOGGER.info("Exist addComponentToGroup-->.");
		return createGroupForm;
	}

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
	public final CreateGroupForm addGBSComponentToGroup(final String groupId, final String updatedBy, final String groupType,
			final List<GroupAttributeForm> getGBSSelectedAttrbuteList) throws PEPFetchException, MalformedURLException, ClassCastException,
			JSONException, IOException {
		LOGGER.info("Entering addGBSComponentToGroup-->.");

		String responseMsg = "";
		String responseMsgCode = "";
		String groupCreationStatus = "";
		JSONObject jsonObjectRes = null;
		final Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);

		/** Call add component service to add CPG attribute details List **/
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addCPGComponentToGroup.Calling Add component for CPG service start.");
		}
		// Create GBS Group
		JSONObject jsonGbsComponent = populateAddComponentGBSJson(groupId, groupType, updatedBy, getGBSSelectedAttrbuteList);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addGBSComponentToGroup.Add GBS Attribute JSON-->" + jsonGbsComponent);
			LOGGER.debug("addGBSComponentToGroup.json Object Add Component to CPG groupId--> " + jsonGbsComponent.getString("groupId"));
		}
		final String resMsgCPG = callAddComponentService(jsonGbsComponent, groupType);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addGBSComponentToGroup.Add Component to GBS Group Service message-->" + resMsgCPG);
		}

		/** Extract Service message **/
		if (null != resMsgCPG && !("").equals(resMsgCPG)) {
			jsonObjectRes = new JSONObject(resMsgCPG);
		}
		if (null != jsonObjectRes) {
			responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addGBSComponentToGroup.responseMsgCode-->" + responseMsgCode);
		}

		if (null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
			responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_SUCCESS);
			groupCreationStatus = GroupingConstants.COMPONENT_ADDEDD_SUCCESSFULLY;
			LOGGER.info("addGBSComponentToGroup.Add Component to GBS Group. ResponseMsg100::Success-->" + responseMsg);
		} else {
			responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_FAILURE);
			groupCreationStatus = GroupingConstants.COMPONENT_ADDITION_FAILED;
			LOGGER.info("addGBSComponentToGroup.Add Component to GBS Group. ResponseMsg101::Failure-->" + responseMsg);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addGBSComponentToGroup.Calling Add component for GBS service end.");
		}
		/** End Component Addition for All Type **/

		// Call DAO to fetch Group Details after getting response from service
		CreateGroupForm createGroupForm = new CreateGroupForm();

		createGroupForm.setGroupId(groupId);
		createGroupForm.setGroupType(groupType);
		createGroupForm.setGroupCretionMsg(responseMsg);
		createGroupForm.setGroupCreationStatus(groupCreationStatus);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer object value from DTO to Form Object end.");

		}
		/** **/
		LOGGER.info("Exist addGBSComponentToGroup-->.");
		return createGroupForm;
	}

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
	public final CreateGroupForm addRCGBCGComponentToGroup(final String groupId, final String updatedBy, final String groupType,
			final List<StyleAttributeForm> getRCGBCGSelectedAttrbuteList) throws PEPFetchException, MalformedURLException,
			ClassCastException, JSONException, IOException {
		LOGGER.info("Entering addRCGBCGComponentToGroup-->.");

		String responseMsg = "";
		String responseMsgCode = "";
		String groupCreationStatus = "";
		JSONObject jsonObjectRes = null;
		final Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);

		/** Call add component service to add CPG attribute details List **/
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addRCGBCGComponentToGroup.Calling Add component for RCG and BCG service start.");
		}
		// Create RCG Group
		JSONObject jsonGbsComponent = populateAddComponentRCGBCGJson(groupId, groupType, updatedBy, getRCGBCGSelectedAttrbuteList);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addRCGBCGComponentToGroup.Add RCG and BCG Attribute JSON-->" + jsonGbsComponent);
			LOGGER.debug("addRCGBCGComponentToGroup.json Object Add Component to RCG and BCG groupId--> "
					+ jsonGbsComponent.getString("groupId"));
		}
		final String resMsgCPG = callAddComponentService(jsonGbsComponent, groupType);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addRCGBCGComponentToGroup.Add Component to RCG and BCG Group Service message-->" + resMsgCPG);
		}

		/** Extract Service message **/
		if (null != resMsgCPG && !("").equals(resMsgCPG)) {
			jsonObjectRes = new JSONObject(resMsgCPG);
		}
		if (null != jsonObjectRes) {
			responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addRCGBCGComponentToGroup.responseMsgCode-->" + responseMsgCode);
		}

		if (null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
			responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_SUCCESS);
			groupCreationStatus = GroupingConstants.COMPONENT_ADDEDD_SUCCESSFULLY;
			LOGGER.info("addRCGBCGComponentToGroup.Add Component to RCG and BCG Group. ResponseMsg100::Success-->" + responseMsg);
		} else {
			responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_FAILURE);
			groupCreationStatus = GroupingConstants.COMPONENT_ADDITION_FAILED;
			LOGGER.info("addRCGBCGComponentToGroup.Add Component to RCG and BCG Group. ResponseMsg101::Failure-->" + responseMsg);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addRCGBCGComponentToGroup.Calling Add component for RCG and BCG service end.");
		}
		/** End Component Addition for All Type **/

		// Call DAO to fetch Group Details after getting response from service
		CreateGroupForm createGroupForm = new CreateGroupForm();

		createGroupForm.setGroupId(groupId);
		createGroupForm.setGroupType(groupType);
		createGroupForm.setGroupCretionMsg(responseMsg);
		createGroupForm.setGroupCreationStatus(groupCreationStatus);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer object value from DTO to Form Object end.");

		}
		/** **/
		LOGGER.info("Exist addRCGBCGComponentToGroup-->.");
		return createGroupForm;
	}

	/**
	 * This method prepare the list for UI.
	 * 
	 * @param getSplitColorDetailsList
	 * @return
	 */
	public final List<GroupAttributeForm> prepareListForView(final List<GroupAttributeForm> getSplitColorDetailsList) {
		LOGGER.info("Enter-->calling prepareListForView");
		List<GroupAttributeForm> updatedSplitColorDetailsList = new ArrayList<>();
		GroupAttributeForm groupAttributeForm = null;
		String productName = "";

		for (int i = 0; i < getSplitColorDetailsList.size(); i++) {
			groupAttributeForm = getSplitColorDetailsList.get(i);
			String entryType = groupAttributeForm.getEntryType();
			if (null != entryType && ("Style").equals(entryType)) {
				productName = GroupingUtil.checkNull(groupAttributeForm.getProdName());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Style ProductName is -->" + productName);
				}
			} else if (null != entryType && ("StyleColor".equals(entryType) || ("SKU").equals(entryType))) {

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("ColorCode------------added------------->" + groupAttributeForm.getColorCode());
				}
				if ("".equals(productName)) {
					productName = groupAttributeForm.getProdName();
					if (productName.indexOf(":") != -1) {
						productName = productName.substring(0, productName.indexOf(":"));
					}
				}
				groupAttributeForm.setProdName(productName);
				updatedSplitColorDetailsList.add(groupAttributeForm);
			} else { // TBD for SplitSKU

			}
		}

		LOGGER.info("Exit-->calling prepareListForView");
		return updatedSplitColorDetailsList;
	}

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
	@Override
	public String updateGroupHeaderDetails(CreateGroupForm createGroupForm, String modifiedBy) throws PEPFetchException,
			MalformedURLException, ClassCastException, JSONException, IOException {
		LOGGER.info("Entered saveEditedGroupHeader of Grouping Service Impl");
		String groupId = createGroupForm.getGroupId();
		String groupName = createGroupForm.getGroupName();
		String groupDesc = createGroupForm.getGroupDesc();
		String status = createGroupForm.getGroupStatus();
		String startDate = createGroupForm.getGroupLaunchDate();
		String endDate = createGroupForm.getEndDate();
		String groupType = createGroupForm.getGroupType();
		JSONObject requestJSON = new JSONObject();
		requestJSON.put(GroupingConstants.GROUP_ID, groupId != null ? groupId : GroupingConstants.EMPTY);
		requestJSON.put(GroupingConstants.GROUP_NAME, groupName != null ? groupName : GroupingConstants.EMPTY);
		requestJSON.put(GroupingConstants.GROUP_DESC, groupDesc != null ? groupDesc : GroupingConstants.EMPTY);
		requestJSON.put(GroupingConstants.GROUP_STATUS, status != null ? status : GroupingConstants.EMPTY);
		requestJSON.put(GroupingConstants.START_DATE, startDate != null ? startDate : GroupingConstants.EMPTY);
		requestJSON.put(GroupingConstants.END_DATE, endDate != null ? endDate : GroupingConstants.EMPTY);
		requestJSON.put(GroupingConstants.MODIFIED_BY, modifiedBy != null ? modifiedBy : GroupingConstants.EMPTY);
		requestJSON.put(GroupingConstants.GROUP_TYPE, groupType != null ? groupType : GroupingConstants.EMPTY);
		String resMsg = callUpdateGroupService(requestJSON);
		LOGGER.info(resMsg);
		return resMsg;
	}

	/**
	 * This method is used to call the Edit Group Service.
	 * 
	 * @param jsonGroup
	 * @return
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	private String callUpdateGroupService(final JSONObject jsonGroup) throws MalformedURLException, ClassCastException, IOException,
			JSONException {
		LOGGER.info("Entering callUpdateGroupService-->.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("jsonArray-->" + jsonGroup);
		}
		String responseMsg = "";
		BufferedReader responseBuffer = null;
		HttpURLConnection httpConnection = null;
		try {
			Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop.getProperty(GroupingConstants.UPDATE_GROUP_SERVICE_URL);
			LOGGER.info("Update Group ServiceURL-->" + serviceURL);

			httpConnection = getServiceConnection(serviceURL);

			LOGGER.info("callUpdateGroupService Service::Json Array-->" + jsonGroup.toString());

			String input = jsonGroup.toString();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("final object in json-->" + jsonGroup.toString());
			}
			LOGGER.info("input....json-->" + input);
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes(GroupingConstants.DEFAULT_CHARSET));
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), GroupingConstants.DEFAULT_CHARSET));
			String output;
			LOGGER.info("Output from Server::: after Calling-->.");
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("UpdateGroupService Service Output-->" + output);
				responseMsg = output;
			}

			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new MalformedURLException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new ClassCastException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new JSONException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new IOException(e.getMessage());
		} finally {
			if (null != httpConnection) {
				httpConnection.disconnect();
			}
			if (responseBuffer != null)
				responseBuffer.close();
		}
		LOGGER.info("Exiting callCreateGroupService-->" + responseMsg);
		return responseMsg;
	}

	/**
	 * This method handles the removing of existing components
	 * 
	 * @param jsonObject
	 * @return
	 * @throws PEPFetchException
	 * @throws MalformedURLException
	 * @throws ClassCastException
	 * @throws JSONException
	 * @throws IOException
	 */
	public String removeSelectedComponent(JSONObject jsonObject) throws PEPFetchException, MalformedURLException, ClassCastException,
			JSONException, IOException {
		LOGGER.info("Entering callUpdateGroupService-->.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("jsonArray-->" + jsonObject);
		}
		String responseMsg = "";

		BufferedReader responseBuffer = null;
		HttpURLConnection httpConnection = null;
		try {
			Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop.getProperty(GroupingConstants.REMOVE_COMPNT_GROUP_SERVICE_URL);
			LOGGER.info("remove component ServiceURL-->" + serviceURL);

			httpConnection = getServiceConnection(serviceURL);

			String input = jsonObject.toString();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("final object in json-->" + jsonObject.toString());
			}
			LOGGER.info("input....json-->" + input);
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes(GroupingConstants.DEFAULT_CHARSET));
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), GroupingConstants.DEFAULT_CHARSET));
			String output;
			LOGGER.info("Output from Server::: after Calling-->.");
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("remove component Service Output-->" + output);
				responseMsg = output;
			}

			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new MalformedURLException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new ClassCastException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new JSONException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new IOException(e.getMessage());
		} finally {
			if (null != httpConnection) {
				httpConnection.disconnect();
			}
			if (responseBuffer != null)
				responseBuffer.close();
		}
		LOGGER.info("Exiting callCreateGroupService-->" + responseMsg);
		return responseMsg;
	}

	/**
	 * This method handles the implementation method for setting component
	 * priority
	 * 
	 * @param jsonObject
	 * @return
	 * @throws PEPFetchException
	 * @throws MalformedURLException
	 * @throws ClassCastException
	 * @throws JSONException
	 * @throws IOException
	 */
	@Override
	public String setComponentPriority(JSONObject jsonObject) throws PEPFetchException, MalformedURLException, ClassCastException,
			JSONException, IOException {
		LOGGER.info("Entering callUpdateGroupService-->.");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("jsonArray-->" + jsonObject);
		}
		String responseMsg = "";
		BufferedReader responseBuffer = null;
		HttpURLConnection httpConnection = null;
		try {
			Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop.getProperty(GroupingConstants.SET_COMPONENT_PRIORITY_SERVICE_URL);
			LOGGER.info("SET COMPONENT PRIOROTY ServiceURL-->" + serviceURL);

			httpConnection = getServiceConnection(serviceURL);

			LOGGER.info("SET PRIOROTY Service::Json Array-->" + jsonObject.toString());

			String input = jsonObject.toString();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("final object in json-->" + jsonObject.toString());
			}
			LOGGER.info("input....json-->" + input);
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes(GroupingConstants.DEFAULT_CHARSET));
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), GroupingConstants.DEFAULT_CHARSET));
			String output;
			LOGGER.info("Output from Server::: after Calling-->.");
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("set cmponent prioroty Service Output-->" + output);
				responseMsg = output;
			}

			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new MalformedURLException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new ClassCastException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new JSONException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new IOException(e.getMessage());
		} finally {
			if (null != httpConnection) {
				httpConnection.disconnect();
			}
			if (responseBuffer != null)
				responseBuffer.close();
		}
		LOGGER.info("Exiting set prioroty GroupService-->" + responseMsg);
		return responseMsg;
	}

	/**
	 * Method to call service for default color selection.
	 * 
	 * @param jsonGroup
	 * @return String
	 * @throws PEPFetchException
	 */
	private String callDefaultColorSizeService(final JSONObject jsonGroup) throws MalformedURLException, ClassCastException, IOException,
			JSONException {
		LOGGER.info("Entering callDefaultColorSizeService.");

		String responseMsg = GroupingConstants.EMPTY;
		BufferedReader responseBuffer = null;
		HttpURLConnection httpConnection = null;
		try {
			final Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
			final String serviceURL = prop.getProperty(GroupingConstants.SET_DEFAULT_COLOR_SERVICE_URL);
			LOGGER.info("Default Color/Size ServiceURL-->" + serviceURL);

			httpConnection = getServiceConnection(serviceURL);

			LOGGER.info("callDefaultColorSizeService Service::Json Array-->" + jsonGroup.toString());

			String input = jsonGroup.toString();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes(GroupingConstants.DEFAULT_CHARSET));
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), GroupingConstants.DEFAULT_CHARSET));
			String output;
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("Default Color/Size Service Output-->" + output);

				responseMsg = output;
			}

		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new MalformedURLException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new ClassCastException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new JSONException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new IOException(e.getMessage());
		} finally {
			if (null != httpConnection) {
				httpConnection.disconnect();
			}
			if (responseBuffer != null) {
				responseBuffer.close();
			}
		}
		LOGGER.info("Exiting callDefaultColorSizeService");
		return responseMsg;
	}

	/** edit Grouping **/

	/**
	 * This method is used to call Default Color/Size Service.
	 * 
	 * @param groupId String
	 * @param groupType String
	 * @param colorId String
	 * @param childOrinId String
	 * @param updatedBy String
	 * @return String
	 * @throws IOException
	 * @throws JSONException
	 * @throws ClassCastException
	 * @throws MalformedURLException
	 */
	@Override
	public final String setDefaultColorSize(final String groupId, final String groupType, final String colorId, final String childOrinId,
			final String updatedBy) throws MalformedURLException, ClassCastException, IOException, JSONException {
		LOGGER.info("Entering setDefaultColorSize.");
		// String responseMsg = GroupingConstants.EMPTY;
		String responseMsgCode = GroupingConstants.EMPTY;

		JSONObject jsonObj = new JSONObject();
		jsonObj.put(GroupingConstants.GROUP_ID, groupId);
		jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
		jsonObj.put(GroupingConstants.COLOR_CODE, colorId);
		jsonObj.put(GroupingConstants.COMPONENT_ID_ATTR, childOrinId);
		jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy);

		/** Calling Web Service **/
		String resMsg = callDefaultColorSizeService(jsonObj);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Default Color/Size Service message-->" + resMsg);
		}

		/** Extract Service message **/
		JSONObject jsonObjectRes = null;
		if (null != resMsg && !(GroupingConstants.EMPTY).equals(resMsg)) {
			jsonObjectRes = new JSONObject(resMsg);
		}
		if (null != jsonObjectRes) {
			responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setDefaultColor.responseMsgCode-->" + responseMsgCode);
		}
		LOGGER.info("Exiting setDefaultColorSize.");
		return responseMsgCode;
	}

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
	@Override
	public final List<StyleAttributeForm> getRegularBeautySearchResult(final String vendorStyleNo, final String styleOrin,
			final String deptNoSearch, final String classNoSearch, final String supplierSiteIdSearch, final String upcNoSearch,
			final String groupId, final String groupIdSearch, final String groupNameSearch, final String sortedColumn,
			final String sortingOrder, final int pageNumber, final int recordsPerPage) throws PEPServiceException {

		LOGGER.info("*Enter-->calling getRegularBeautySearchResult from GroupingServiceImpl.");
		List<StyleAttributeForm> styleAttributeFormList = null;
		try {
			styleAttributeFormList = groupingDAO.getRegularBeautySearchResult(vendorStyleNo, styleOrin, deptNoSearch, classNoSearch,
					supplierSiteIdSearch, upcNoSearch, groupId, groupIdSearch, groupNameSearch, sortedColumn, sortingOrder, pageNumber,
					recordsPerPage);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer.getRegularBeautySearchResult-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getRegularBeautySearchResult from GroupingServiceImpl.");
		return styleAttributeFormList;
	}

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
	@Override
	public final int getRegularBeautySearchResultCount(final String vendorStyleNo, final String styleOrin, final String deptNoSearch,
			final String classNoSearch, final String supplierSiteIdSearch, final String upcNoSearch, final String groupId,
			final String groupIdSearch, final String groupNameSearch) {

		LOGGER.info("*Enter-->calling getRegularBeautySearchResultCount from GroupingServiceImpl.");
		int totalCount = 0;
		totalCount = groupingDAO.getRegularBeautySearchResultCount(vendorStyleNo, styleOrin, deptNoSearch, classNoSearch,
				supplierSiteIdSearch, upcNoSearch, groupId, groupIdSearch, groupNameSearch);
		LOGGER.info("Exit-->calling getRegularBeautySearchResultCount from GroupingServiceImpl.");
		return totalCount;
	}

	/**
	 * This method is used to get the Existing Regular/Beauty Collection
	 * Grouping details.
	 * 
	 * @param groupId
	 * @return List<StyleAttributeForm>
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	@Override
	public final List<StyleAttributeForm> getExistRegularBeautyDetails(final String groupId) throws PEPServiceException {
		LOGGER.info("Enter-->calling getExistRegularBeautyDetails from GroupingServiceImpl.");

		List<StyleAttributeForm> styleAttributeFormList = null;
		try {
			styleAttributeFormList = groupingDAO.getExistRegularBeautyDetails(groupId);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the getExistRegularBeautyDetails().Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getExistRegularBeautyDetails from GroupingServiceImpl.");
		return styleAttributeFormList;
	}

	/**
	 * This method is used to get the Child Regular/Beauty Collection Grouping
	 * details.
	 * 
	 * @param groupId
	 * @return List<StyleAttributeForm>
	 * @throws PEPServiceException
	 */
	@Override
	public final List<StyleAttributeForm> getRegularBeautyChildDetails(final String groupId) throws PEPServiceException {
		LOGGER.info("Enter-->calling getRegularBeautyChildDetails from GroupingServiceImpl.");

		List<StyleAttributeForm> styleAttributeFormList = null;
		try {
			styleAttributeFormList = groupingDAO.getRegularBeautyChildDetails(groupId);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the getRegularBeautyChildDetails().Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getRegularBeautyChildDetails from GroupingServiceImpl.");
		return styleAttributeFormList;
	}

	/**
	 * This method is used to get the WebService Connection.
	 * 
	 * @param serviceURL
	 * @return
	 * @throws PEPServiceException
	 */
	public final HttpURLConnection getServiceConnection(final String serviceURL) throws ProtocolException, MalformedURLException,
			IOException {

		HttpURLConnection httpConnection = null;
		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
		URL targetUrl;
		try {
			targetUrl = new URL(serviceURL);

			httpConnection = (HttpURLConnection) targetUrl.openConnection();

			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod(prop.getProperty(GroupingConstants.SERVICE_REQUEST_METHOD));
			httpConnection.setRequestProperty(prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
					prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));
		} catch (ProtocolException e) {
			LOGGER.error("getServiceConnection.ProtocolException-->" + e);
			throw new ProtocolException(e.getMessage());
		} catch (MalformedURLException e) {
			LOGGER.error("getServiceConnection.MalformedURLException-->" + e);
			throw new ProtocolException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("getServiceConnection.IOException-->" + e);
			throw new ProtocolException(e.getMessage());
		}

		return httpConnection;
	}

	/**
	 * This method is used check the LOCK status of a pet.
	 * 
	 * @param Orin
	 * @param pepUserId
	 * @param searchPetLockedtype
	 * @return
	 * @throws PEPPersistencyException
	 */
	public ArrayList<PetLock> isPETLocked(final String pepUserId, final String orin, final String searchPetLockedtype)
			throws PEPPersistencyException {
		LOGGER.info("service impl :: isPETLocked");
		ArrayList<PetLock> petLockedDtls = null;
		try {
			petLockedDtls = groupingDAO.isPETLocked(orin, pepUserId, searchPetLockedtype);
		} catch (PEPPersistencyException e) {

			LOGGER.info("Exception occurred at the Service DAO Layer");
			throw e;
		}
		return petLockedDtls;

	}

	/**
	 * This method is used to lock a PET while using.
	 * 
	 * @param orin
	 * @param pepUserID
	 * @param pepfunction
	 * @return
	 * @throws PEPPersistencyException
	 */
	public boolean lockPET(final String orin, final String pepUserID, final String pepfunction) throws PEPPersistencyException {
		LOGGER.info("service impl :: lockPET");
		try {
			groupingDAO.lockPET(orin, pepUserID, pepfunction);
		} catch (PEPPersistencyException e) {
			LOGGER.info("Exception occurred at the Service DAO Layer");
			throw e;
		}
		return false;
	}

	/**
	 * This method is used to release Grouping pet lock.
	 * @param orin
	 * @param pepUserID
	 * @param pepFunction
	 * @return
	 * @throws PEPPersistencyException
	 */
	public boolean releseLockedPet(final String orin, final String pepUserID, final String pepFunction) throws PEPPersistencyException {
		LOGGER.info("Grouping releseLockedPet service :: lockPET Enter");
		boolean isPetReleased = false;
		try {
			isPetReleased = groupingDAO.releseLockedPet(orin, pepUserID, pepFunction);
		} catch (PEPPersistencyException e) {
			LOGGER.info("Exception occurred at the Service DAO Layer");
			throw e;
		}
		LOGGER.info("Grouping releseLockedPet service :: lockPET Exit");
		return isPetReleased;

	}
}
