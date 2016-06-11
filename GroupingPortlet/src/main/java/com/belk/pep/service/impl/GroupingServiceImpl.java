package com.belk.pep.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
	 * Sets the Grouping DAO. GroupingDAO the new Grouping DAO
	 */
	public final void setGroupingDAO(GroupingDAO groupingDAO) {
		this.groupingDAO = groupingDAO;
	}

	/**
	 * This method is used to call Group Creation Service and fetch data from
	 * database
	 * 
	 * @param jsonStyle
	 * @param updatedBy
	 * @param selectedSplitAttributeList
	 * @return CreateGroupForm
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	public final CreateGroupForm saveGroupHeaderDetails(final JSONObject jsonStyle, final String updatedBy,
			final List<GroupAttributeForm> selectedSplitAttributeList) throws Exception, PEPFetchException {
		LOGGER.info("Entering saveGroupHeaderDetails-->");

		String groupIdRes = "";
		String responseMsg = "";
		String responseMsgCode = "";
		String groupCreationStatus = "";

		/** Calling Web Service to create Group except Split type **/
		LOGGER.info("Create Group Service Start currentTimeMillis-->" + System.currentTimeMillis());
		String resMsg = callCreateGroupService(jsonStyle);
		LOGGER.info("Create Group Service End currentTimeMillis-->" + System.currentTimeMillis());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Create Group Service message-->" + resMsg);
		}
		final Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);

		/** Extract Service message **/
		JSONObject jsonObjectRes = null;
		if(null != resMsg && !("").equals(resMsg)){
			jsonObjectRes = new JSONObject(resMsg);
		}
		if(null != jsonObjectRes){
			responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
			groupIdRes = jsonObjectRes.getString(GroupingConstants.COMPONENT_ID);
		}

		// responseMsgCode = responseMsgArray[0].split(":")[1];
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("responseMsgCode-->" + responseMsgCode);
			LOGGER.debug("groupIdRes-->" + groupIdRes);
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
		if(null != responseMsgCode
				&& responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)){
		if (null != groupType && groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_COLOR)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Calling Add component for Split Color service Start");
			}
			// Create Split Color Group
			JSONObject jsonStyleSpliColor = populateAddComponentSCGJson(groupIdRes, groupType, updatedBy, selectedSplitAttributeList/* componentList */);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add Color Attribute JSON-->" + jsonStyleSpliColor);
				LOGGER.debug("json Object Add Component to Split Color groupId--> " + jsonStyleSpliColor.getString("groupId"));
			}
			LOGGER.info("Create Split Color Group Service Start currentTimeMillis-->" + System.currentTimeMillis());
			final String resMsgSplitColor = callAddComponentSCGService(jsonStyleSpliColor); // TODO uncomment
			LOGGER.info("Create Split Color Group Service End currentTimeMillis-->" + System.currentTimeMillis());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add Component to Split Color Group Service message-->" + resMsgSplitColor);
			}

			/** Extract Service message **/
			if(null != resMsgSplitColor && !("").equals(resMsgSplitColor)){
				jsonObjectRes = new JSONObject(resMsgSplitColor);
			}
			if(null != jsonObjectRes){
				responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("resMsgSplitColor.responseMsgCode-->" + responseMsgCode);
			}

			if (null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
				responseMsg = "#" + groupIdRes + " - " + prop.getProperty(GroupingConstants.ADD_COMPONENT_SCG_SERVICE_SUCCESS);
				groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_COMPONENT_SCG;
				LOGGER.info("Add Component to Split Color Group. ResponseMsg100::Success-->" + responseMsg);
			} else {
				responseMsg = "#" + groupIdRes + " - " + prop.getProperty(GroupingConstants.ADD_COMPONENT_SCG_SERVICE_FAILURE);
				groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_OUT_COMPONENT_SCG;
				LOGGER.info("Add Component to Split Color Group. ResponseMsg101::Failure-->" + responseMsg);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Calling Add component for Split Color service End");
			}
		}/** Call add component service to add split SKU attribute details List **/
		else if (null != groupType && groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_SKU) ) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Calling Add component for Split SKU service Start");
			}
			// Create Split SKU Group
			JSONObject jsonStyleSpliSku = populateAddComponentSSGJson(groupIdRes, groupType, updatedBy, selectedSplitAttributeList/* componentList */);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add SKU Attribute JSON-->" + jsonStyleSpliSku);
				LOGGER.debug("json Object Add Component to Split SKU groupId--> " + jsonStyleSpliSku.getString("groupId"));
			}
			LOGGER.info("Create Split SKU Group Service Start currentTimeMillis-->" + System.currentTimeMillis());
			String resMsgSplitSku = ""; // callAddComponentSSGService(jsonStyleSpliSku); // TODO Uncomment
			LOGGER.info("Create Split SKU Group Service End currentTimeMillis-->" + System.currentTimeMillis());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Add Component to Split SKU Group Service message-->" + resMsgSplitSku);
			}

			/** Extract Service message **/
			if(null != resMsgSplitSku && !("").equals(resMsgSplitSku)){
				jsonObjectRes = new JSONObject(resMsgSplitSku);
			}
			if(null != jsonObjectRes){
				responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("resMsgSplitSku.responseMsgCode-->" + responseMsgCode);
			}

			if (null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
				responseMsg = "#" + groupIdRes + " - " + prop.getProperty(GroupingConstants.ADD_COMPONENT_SSG_SERVICE_SUCCESS);
				groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_COMPONENT_SSG;
				LOGGER.info("Add Component to Split SKU Group. ResponseMsg100::Success-->" + responseMsg);
			} else {
				responseMsg = "#" + groupIdRes + " - " + prop.getProperty(GroupingConstants.ADD_COMPONENT_SSG_SERVICE_FAILURE);
				groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_OUT_COMPONENT_SSG;
				LOGGER.info("Add Component to Split SKU Group. ResponseMsg101::Failure-->" + responseMsg);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Calling Add component for Split SKU service End");
			}
		} }
		/** End Group Creation for All Type **/

		// Call DAO to fetch Group Details after getting response from service
		CreateGroupForm createGroupForm = new CreateGroupForm();
		CreateGroupDTO createGroupDTO = new CreateGroupDTO();
		if (null != responseMsgCode && (GroupingConstants.SUCCESS_CODE).equals(responseMsgCode)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Before Calling database method getGroupHeaderDetails() to retreive Group Header Details-->");
			}
			createGroupDTO = groupingDAO.getGroupHeaderDetails(groupIdRes);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("After Calling database method getGroupHeaderDetails() to retreive Group Header Details-->"
						+ createGroupDTO.getGroupId());
			}
		}
		createGroupDTO.setGroupCretionMsg(responseMsg);

		/** Transfer object value from DTO to Form Object Start **/
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer object value from DTO to Form Object Start");
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

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer object value from DTO to Form Object End");

		}
		/** **/

		LOGGER.info("Exist saveGroupHeaderDetails-->");
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
	public final String callCreateGroupService(final JSONObject jsonStyle) throws Exception, PEPFetchException {
		LOGGER.info("Entering callCreateGroupService-->");
		
		String responseMsg = "";
		BufferedReader responseBuffer = null;
		try {
			Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_URL);
			LOGGER.info("Create Group ServiceURL-->" + serviceURL);

			URL targetUrl = new URL(serviceURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod(prop.getProperty(GroupingConstants.SERVICE_REQUEST_METHOD));
			httpConnection.setRequestProperty(prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
					prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));

			LOGGER.info("callCreateGroupService Service::Json Array-->" + jsonStyle.toString());

			String input = jsonStyle.toString();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			String output;
			LOGGER.info("Output from Server::: after Calling-->");
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("CreateGroupService Service Output-->" + output);

				responseMsg = output;
			}

			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new PEPFetchException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new PEPFetchException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new IOException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new JSONException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("inside Exception-->" + e);
			throw new Exception(e.getMessage());
		} finally {
			if(responseBuffer!=null)
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
	 * @return getSplitColorDetailsList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<StyleAttributeForm> getNewCPGDetails(final String vendorStyleNo, final String styleOrin, final String deptNoSearch, 
			final String classNoSearch,	final String supplierSiteIdSearch, final String upcNoSearch, final String groupId)
			throws PEPServiceException, PEPPersistencyException {
		// getting the Style Color details from Database
		LOGGER.info("*Enter-->calling getNewCPGDetails from GroupingServiceImpl.");
		List<StyleAttributeForm> getNewCPGDetails = null;
		try {
			getNewCPGDetails = groupingDAO.getNewCPGDetails(vendorStyleNo, styleOrin, deptNoSearch, classNoSearch,
					supplierSiteIdSearch, upcNoSearch, groupId);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer.getNewCPGDetails-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getNewCPGDetails from GroupingServiceImpl.");
		return getNewCPGDetails;
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
		List<GroupAttributeForm> updatedSplitColorDetailsList = new ArrayList<GroupAttributeForm>();
		GroupAttributeForm groupAttributeForm = null;
		String productName = "";
		String errorCode = "noData";
		Map<String, List<GroupAttributeForm>> validateMap = new HashMap<String, List<GroupAttributeForm>>();
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
					LOGGER.debug("Pet Status not Completed. Orin No: " + groupAttributeForm.getOrinNumber() + ", PetStatus: " + petStatus);
					LOGGER.debug("ColorCode------------------added------------------------------------>"
							+ groupAttributeForm.getColorCode());
				}
				if (null != petStatus && !(GroupingConstants.PET_STATUS_COMPLETED).equals(petStatus.trim()) 
						&& !(GroupingConstants.PET_STATUS_CLOSED).equals(petStatus.trim())) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Pet Status not Completed. Orin No: " + groupAttributeForm.getOrinNumber() + ", PetStatus: "
								+ petStatus);
					}
					updatedSplitColorDetailsList = new ArrayList<GroupAttributeForm>();
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
		List<GroupAttributeForm> updatedgetSplitSKUDetailsList = new ArrayList<GroupAttributeForm>();
		GroupAttributeForm groupAttributeForm = null;
		String productName = "";
		String errorCode = "noData";
		Map<String, List<GroupAttributeForm>> validateMap = new HashMap<String, List<GroupAttributeForm>>();
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
						&& !(GroupingConstants.PET_STATUS_CLOSED).equals(petStatus.trim())) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Pet Status not Completed. Orin No: " + groupAttributeForm.getOrinNumber() + ", PetStatus: "
								+ petStatus);
					}
					updatedgetSplitSKUDetailsList = new ArrayList<GroupAttributeForm>();
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
	 * This method is used to validate CPG Attribute list before add it
	 * to group.
	 * @param existClassId
	 * @param getCPGSelectedAttrbuteList
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final String validateCPGAttributeDetails(final String existClassId, 
			final List<StyleAttributeForm> getCPGSelectedAttrbuteList)
					throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("Enter-->calling validateCPGAttributeDetails. existClassId-->"+existClassId);
		String message = "";
		String classIdSelectedPrev = "";
		StyleAttributeForm styleAttributeForm = null;
		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);
		try {
			String existClassIdSt = GroupingUtil.checkNull(existClassId);
			for (int i = 0; i < getCPGSelectedAttrbuteList.size(); i++) {
				styleAttributeForm = getCPGSelectedAttrbuteList.get(i);
				String classIdSelected = GroupingUtil.checkNull(styleAttributeForm.getClassId());
				if(("").equals(classIdSelectedPrev)){
					classIdSelectedPrev = classIdSelected;
				}
				if(!("").equals(existClassIdSt) && !existClassIdSt.equals(classIdSelected)){
					message = prop.getProperty(GroupingConstants.MESSAGE_CPG_VALIDATION_DIFF_CLASS_ID_ONE) + " " 
							+ classIdSelected + prop.getProperty(GroupingConstants.MESSAGE_CPG_VALIDATION_DIFF_CLASS_ID_TWO) + " " 
							+ existClassIdSt + prop.getProperty(GroupingConstants.MESSAGE_CPG_VALIDATION_DIFF_CLASS_ID_THREE);
					break;
				}
				if(!classIdSelected.equals(classIdSelectedPrev)){
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
		LOGGER.info("Entering populateAddComponentSCGJson-->");

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
	 * This method is used to call Add Component to Split Color Service
	 * 
	 * @param jsonStyleSpliColor
	 * @return responseMsg
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	public final String callAddComponentSCGService(final JSONObject jsonStyleSpliColor) throws Exception, PEPFetchException {
		LOGGER.info("Entering callAddComponentSCGService-->");
		String responseMsg = "";
		BufferedReader responseBuffer=null;
		try {
			Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop.getProperty(GroupingConstants.ADD_COMPONENT_TO_SCG_SERVICE_URL);
			LOGGER.info("Add Component to Split Color ServiceURL-->" + serviceURL);

			URL targetUrl = new URL(serviceURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod(prop.getProperty(GroupingConstants.SERVICE_REQUEST_METHOD));
			httpConnection.setRequestProperty(prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
					prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));

			LOGGER.info("callAddComponentSCGService Service::Json Array-->" + jsonStyleSpliColor.toString());

			String input = jsonStyleSpliColor.toString();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			String output;
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("call callAddComponentSCGService Service Output-->" + output);
				responseMsg = output;

			}

			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new PEPFetchException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new PEPFetchException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new IOException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new JSONException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("inside Exception-->" + e);
			throw new Exception(e.getMessage());
		} finally {
			
			if(responseBuffer!=null)
				responseBuffer.close();
		}
		LOGGER.info("Exiting callAddComponentSCGService-->" + responseMsg);
		return responseMsg;
	}

	/**
	 * Method to pass JSON Array to call the Add Component service for CPG Group.
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
		LOGGER.info("Entering populateAddComponentCPGJson-->");

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
	 * This method is used to call Add Component to CPG Service
	 * 
	 * @param jsonStyleSpliColor
	 * @return responseMsg
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	public final String callAddComponentCPGService(final JSONObject jsonCpgComponent) throws Exception, PEPFetchException {
		LOGGER.info("Entering callAddComponentCPGService-->");
 
		String responseMsg = "";
		BufferedReader responseBuffer =null;
		try {
			Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop.getProperty(GroupingConstants.ADD_COMPONENT_TO_CPG_SERVICE_URL);
			LOGGER.info("Add Component to CPG ServiceURL-->" + serviceURL);

			URL targetUrl = new URL(serviceURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod(prop.getProperty(GroupingConstants.SERVICE_REQUEST_METHOD));
			httpConnection.setRequestProperty(prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
					prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));

			LOGGER.info("callAddComponentCPGService Service::Json Array-->" + jsonCpgComponent.toString());

			String input = jsonCpgComponent.toString();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			String output;
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("call callAddComponentCPGService Service Output-->" + output);

				responseMsg = output;
			}

			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new PEPFetchException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new PEPFetchException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new IOException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new JSONException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("inside Exception-->" + e);
			throw new Exception(e.getMessage());
		} finally {
			if (responseBuffer!=null){
				responseBuffer.close();
			}
		}
		LOGGER.info("Exiting callAddComponentCPGService-->" + responseMsg);
		return responseMsg;
	}

	/**
	 * Method to pass JSON Array to call the Add Component service for Split SKU
	 * Group
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
		LOGGER.info("Entering populateAddComponentSSGJson-->");

		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObjComponent = null;
		GroupAttributeForm groupAttributeForm = null;
		JSONArray jsonArray = new JSONArray();
		try {

			for (int i = 0; i < selectedSplitAttributeList.size(); i++) {
				jsonObjComponent = new JSONObject();
				groupAttributeForm = selectedSplitAttributeList.get(i);
				jsonObjComponent.put(GroupingConstants.COMPONENT_ID, groupAttributeForm.getOrinNumber());
				jsonObjComponent.put(GroupingConstants.COMPONENT_IS_DEFAULT, groupAttributeForm.getIsDefault());
				jsonObjComponent.put(GroupingConstants.COMPONENT_COLOR, groupAttributeForm.getColorCode());
				jsonObjComponent.put(GroupingConstants.COMPONENT_SIZE, groupAttributeForm.getSize());
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
	 * This method is used to call Add Component to Split SKU Service
	 * 
	 * @param jsonStyleSpliSku
	 * @return responseMsg
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	public final String callAddComponentSSGService(final JSONObject jsonStyleSpliSku) throws Exception, PEPFetchException {
		LOGGER.info("Entering callAddComponentSSGService-->");
 
		String responseMsg = "";
		BufferedReader responseBuffer = null;
		try {
			Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop.getProperty(GroupingConstants.ADD_COMPONENT_TO_SSG_SERVICE_URL);
			LOGGER.info("Add Component to Split SKU ServiceURL-->" + serviceURL);

			URL targetUrl = new URL(serviceURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod(prop.getProperty(GroupingConstants.SERVICE_REQUEST_METHOD));
			httpConnection.setRequestProperty(prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
					prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));

			LOGGER.info("callAddComponentSSGService Service::Json Array-->" + jsonStyleSpliSku.toString());

			String input = jsonStyleSpliSku.toString();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			String output;
			LOGGER.info("Output from Server::: after Calling-->");
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("call callAddComponentSSGService Service Output-->" + output);

				responseMsg = output;

			}

			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new PEPFetchException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new PEPFetchException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new IOException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new JSONException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("inside Exception-->" + e);
			throw new Exception(e.getMessage());
		} finally{
			if (responseBuffer!=null)
				responseBuffer.close();
		}
		LOGGER.info("Exiting callAddComponentSSGService-->" + responseMsg);
		return responseMsg;
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
		List<GroupAttributeForm> selectedSplitAttributeList = new ArrayList<GroupAttributeForm>();
		GroupAttributeForm groupAttributeForm = null;
		String colorCode = "";
		for (int i = 0; i < updatedSplitColorDetailsList.size(); i++) {
			groupAttributeForm = updatedSplitColorDetailsList.get(i);
			colorCode = (null == groupAttributeForm.getColorCode() ? "" : groupAttributeForm.getColorCode().trim());
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
		List<GroupAttributeForm> selectedSplitAttributeList = new ArrayList<GroupAttributeForm>();
		GroupAttributeForm groupAttributeForm = null;
		String sizeCode = "";
		for (int i = 0; i < updatedSplitSKUDetailsList.size(); i++) {
			groupAttributeForm = updatedSplitSKUDetailsList.get(i);
			sizeCode = (null == groupAttributeForm.getSize() ? "" : groupAttributeForm.getSize().trim());
			if (sizeCode.equals(defaultSelectedAttr)) {
				groupAttributeForm.setIsDefault("yes");
			} else {
				groupAttributeForm.setIsDefault("no");
			}
			for (int j = 0; j < selectedItemsArr.length; j++) {

				if (selectedItemsArr[j].equals(sizeCode)) {
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
		List<StyleAttributeForm> selectedAttributeList = new ArrayList<StyleAttributeForm>();
		StyleAttributeForm styleAttributeForm = null;
		String mdmId = "";
		for (int i = 0; i < getCPGDetailsList.size(); i++) {
			styleAttributeForm = getCPGDetailsList.get(i);
			mdmId = (null == styleAttributeForm.getOrinNumber() ? "" : styleAttributeForm.getOrinNumber().trim());

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
	 * This method is used to get the Existing Group Details
	 * 
	 * @param groupId
	 * @return createGroupForm
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	public final CreateGroupForm getExistingGrpDetails(final String groupId) throws Exception, PEPFetchException {
		LOGGER.info("Entering getExistingGrpDetails-->");

		// Call DAO to fetch Group Details after getting response from service
		CreateGroupForm createGroupForm = new CreateGroupForm();
		CreateGroupDTO createGroupDTO = new CreateGroupDTO();
		List<GroupAttributeForm> groupAttributeDTOList = new ArrayList<GroupAttributeForm>();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Before Calling database method getGroupHeaderDetails() to retreive Group Header Details-->");
		}
		createGroupDTO = groupingDAO.getGroupHeaderDetails(groupId);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("After Calling database method getGroupHeaderDetails() to retreive Group Header Details-->"
					+ createGroupDTO.getGroupId());
			LOGGER.debug("Before Calling database method getExistingGrpDetails() to retreive Existing Group Attribute Details-->");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("After Calling database method getExistingGrpDetails() to retreive Existing Group Attribute Details-->"
					+ createGroupDTO.getGroupId());

			/** Transfer object value from DTO to Form Object Start **/
			LOGGER.debug("Transfer object value from DTO to Form Object Start");
		}
		createGroupForm.setGroupId(createGroupDTO.getGroupId());
		createGroupForm.setGroupName(createGroupDTO.getGroupName());
		createGroupForm.setGroupType(createGroupDTO.getGroupType());
		createGroupForm.setGroupDesc(createGroupDTO.getGroupDesc());
		createGroupForm.setGroupLaunchDate(createGroupDTO.getGroupLaunchDate());
		createGroupForm.setEndDate(createGroupDTO.getEndDate());
		createGroupForm.setGroupCretionMsg("");
		createGroupForm.setGroupAttributeFormList(groupAttributeDTOList);
		createGroupForm.setGroupCreationStatus("");
		createGroupForm.setGroupStatus(createGroupDTO.getGroupStatus());
		createGroupForm.setCarsGroupType(createGroupDTO.getCarsGroupType());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer object value from DTO to Form Object End");
		}
		/** **/

		LOGGER.info("Exist getExistingGrpDetails-->");
		return createGroupForm;
	}

	/**
	 * Method to get the groups for search group.
	 * 
	 * @param groupSearchForm
	 *            GroupSearchForm
	 * @return GroupSearchForm
	 * 
	 *         Method added For PIM Phase 2 - groupSearch Date: 05/19/2016 Added
	 *         By: Cognizant
	 * @throws PEPPersistencyException
	 */
	@Override
	public final GroupSearchForm groupSearch(final GroupSearchForm groupSearchForm) throws PEPServiceException, PEPPersistencyException {

		LOGGER.info("Entering groupSearch() in GroupingService class.");

		List<GroupForm> groupFormList = new ArrayList<GroupForm>();
		List<GroupSearchDTO> listOneGroup = groupingDAO.groupSearch(groupSearchForm);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("List one size -- " + listOneGroup.size());
		}
		if (listOneGroup.size() > 0) {
			GroupForm groupFormParent = null;
			Map<String, GroupForm> mapGroup = new HashMap<String, GroupForm>();
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
			if (groupSearchForm.getGroupId() != null && !groupSearchForm.getGroupId().trim().equals("")) {
				List<GroupSearchDTO> listTwoGroup = groupingDAO.groupSearchParent(listOneGroup, groupSearchForm);
				int parentCount = groupingDAO.groupSearchParentCount(listOneGroup, groupSearchForm);
				groupSearchForm.setParentCount(parentCount);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("List two size -- " + listTwoGroup.size());
				}
				if (listTwoGroup.size() > 0) {
					GroupForm groupForm = null;
					//List<GroupForm> childGroupFormList = null;
					List<GroupForm> parentGroupFormList = new ArrayList<GroupForm>();
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
						groupFormList = new ArrayList<GroupForm>();
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
	 * Method to get Group search record count
	 * 
	 * @param groupSearchForm
	 *            GroupSearchForm
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
	 * @param departmentsToBesearched
	 *            String
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
	 * @param departmentNumbers
	 *            String
	 * @return List<ClassDetails>
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
	private String callDeleteGroupService(final JSONObject jsonGroup) throws Exception, PEPFetchException {
		LOGGER.info("Entering callDeletGroupService-->");

		String responseMsg = "";
		BufferedReader responseBuffer =null;
		try {
			Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop.getProperty(GroupingConstants.DELETE_GROUP_SERVICE_URL);
			LOGGER.info("Delete Group ServiceURL-->" + serviceURL);

			URL targetUrl = new URL(serviceURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod(prop.getProperty(GroupingConstants.SERVICE_REQUEST_METHOD));
			httpConnection.setRequestProperty(prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
					prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));

			LOGGER.info("callDeletGroupService Service::Json Array-->" + jsonGroup.toString());

			String input = jsonGroup.toString();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			String output;
			LOGGER.info("Output from Server::: after Calling-->");
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("DeleteGroupService Service Output-->" + output);

				responseMsg = output;
			}

			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new PEPFetchException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new PEPFetchException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new IOException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new JSONException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("inside Exception-->" + e);
			throw new Exception(e.getMessage());
		} finally {
			if(responseBuffer!=null){
				responseBuffer.close();
			}
		}
		LOGGER.info("Exiting callCreateGroupService-->" + responseMsg);
		return responseMsg;
	}

	/**
	 * This method is used to call Group Delete Service
	 * 
	 * @param groupId
	 * @param updatedBy
	 * @return String
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	@Override
	public final String deleteGroup(final String groupId, final String groupType, final String updatedBy) throws Exception,
			PEPFetchException {
		LOGGER.info("Entering deleteGroup-->");
		String responseMsg = "";
		String responseMsgCode = "";

		JSONObject jsonObj = new JSONObject();
		jsonObj.put(GroupingConstants.GROUP_ID, groupId);
		jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
		jsonObj.put(GroupingConstants.CREATED_BY, updatedBy);

		/** Calling Web Service to create Group except Split type **/
		LOGGER.info("Delete Group Service Start currentTimeMillis-->" + System.currentTimeMillis());
		String resMsg = callDeleteGroupService(jsonObj);
		LOGGER.info("Delete Group Service End currentTimeMillis-->" + System.currentTimeMillis());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Delete Group Service message-->" + resMsg);
		}
		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);

		/** Extract Service message **/
		JSONObject jsonObjectRes = null;
		if(null != resMsg && !("").equals(resMsg)){
			jsonObjectRes = new JSONObject(resMsg);
		}
		if(null != jsonObjectRes){
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

		LOGGER.info("Exist deleteGroup-->");
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
	 * This method is used to get the Existing Consolidated Grouping details details.
	 * 
	 * @param groupId
	 * @return getSplitColorDetailsList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<StyleAttributeForm> getExistCPGDetails(final String groupId) throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("Enter-->calling getSplitSkuDetailsList from GroupingServiceImpl.");
//		List<GroupAttributeForm> getSplitSkuDetailsList = null;
		List<StyleAttributeForm> styleAttributeFormList = null;
		try {
			styleAttributeFormList = groupingDAO.getExistCPGDetails(groupId);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the getSplitSkuDetailsList().Service Implementation Layer-->" + e);
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getSplitSkuDetailsList from GroupingServiceImpl.");
		return styleAttributeFormList;
	}
	
	
	
	
	/**
	 * This method is used to call add Component Service and fetch data from database
	 * @param updatedBy
	 * @param groupType
	 * @param selectedSplitAttributeList
	 * @return
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	public final CreateGroupForm addComponentToGroup(final String groupId, final String updatedBy, final String groupType,
			final List<GroupAttributeForm> selectedSplitAttributeList) throws Exception, PEPFetchException {
		LOGGER.info("Entering addComponentToGroup-->");

		String responseMsg = "";
		String responseMsgCode = "";
		String groupCreationStatus = "";
		JSONObject jsonObjectRes = null;
		final Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);

		/** Call add component service to add Split Color attribute details List **/
		if (null != groupType && groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_COLOR)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Calling Add component for Split Color service Start");
			}
			// Create Split Color Group
			JSONObject jsonStyleSpliColor = populateAddComponentSCGJson(groupId, groupType, updatedBy, selectedSplitAttributeList);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Add Color Attribute JSON-->" + jsonStyleSpliColor);
				LOGGER.debug("addComponentToGroup.json Object Add Component to Split Color groupId--> " + jsonStyleSpliColor.getString("groupId"));
			}
			LOGGER.info("addComponentToGroup.Create Split Color Group Service Start currentTimeMillis-->" + System.currentTimeMillis());
			final String resMsgSplitColor = callAddComponentSCGService(jsonStyleSpliColor);
			LOGGER.info("addComponentToGroup.Create Split Color Group Service End currentTimeMillis-->" + System.currentTimeMillis());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Add Component to Split Color Group Service message-->" + resMsgSplitColor);
			}

			/** Extract Service message **/
			if(null != resMsgSplitColor && !("").equals(resMsgSplitColor)){
				jsonObjectRes = new JSONObject(resMsgSplitColor);
			}
			if(null != jsonObjectRes){
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
				LOGGER.debug("addComponentToGroup.Calling Add component for Split Color service End");
			}
		}

		/** Call add component service to add split SKU attribute details List **/
		if (null != groupType && groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_SKU)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Calling Add component for Split SKU service Start");
			}
			// Create Split SKU Group
			LOGGER.debug("groupId-->"+ groupId +" groupType-->"+ groupType +" updatedBy--> "+ updatedBy);
			JSONObject jsonStyleSpliSku = populateAddComponentSSGJson(groupId, groupType, updatedBy, selectedSplitAttributeList);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Add SKU Attribute JSON-->" + jsonStyleSpliSku);
				LOGGER.debug("addComponentToGroup.json Object Add Component to Split SKU groupId--> " + jsonStyleSpliSku.getString("groupId"));
			}
			LOGGER.info("addComponentToGroup.Create Split SKU Group Service Start currentTimeMillis-->" + System.currentTimeMillis());
			String resMsgSplitSku = ""; // callAddComponentSSGService(jsonStyleSpliSku); // TODO Uncomment
			LOGGER.info("addComponentToGroup.Create Split SKU Group Service End currentTimeMillis-->" + System.currentTimeMillis());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("addComponentToGroup.Add Component to Split SKU Group Service message-->" + resMsgSplitSku);
			}

			/** Extract Service message **/
			if(null != resMsgSplitSku && !("").equals(resMsgSplitSku)){
				jsonObjectRes = new JSONObject(resMsgSplitSku);
			}
			if(null != jsonObjectRes){
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
				LOGGER.debug("addComponentToGroup.Calling Add component for Split SKU service End");
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
			LOGGER.debug("Transfer object value from DTO to Form Object End");

		}
		/** **/

		LOGGER.info("Exist addComponentToGroup-->");
		return createGroupForm;
	}


	/**
	 * This method is used to call add CPG Component Service and fetch data from database
	 * @param updatedBy
	 * @param groupType
	 * @param selectedSplitAttributeList
	 * @return
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	public final CreateGroupForm addCPGComponentToGroup(String groupId, String updatedBy, String groupType,
			List<StyleAttributeForm> getCPGSelectedAttrbuteList) throws Exception, PEPFetchException {
		LOGGER.info("Entering addCPGComponentToGroup-->");

		String responseMsg = "";
		String responseMsgCode = "";
		String groupCreationStatus = "";
		JSONObject jsonObjectRes = null;
		final Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);

		/** Call add component service to add CPG attribute details List **/
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addCPGComponentToGroup.Calling Add component for CPG service Start");
		}
		// Create Split Color Group
		JSONObject jsonCpgComponent = populateAddComponentCPGJson(groupId, groupType, updatedBy, getCPGSelectedAttrbuteList); // TODO
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addCPGComponentToGroup.Add CPG Attribute JSON-->" + jsonCpgComponent);
			LOGGER.debug("addCPGComponentToGroup.json Object Add Component to CPG groupId--> " + jsonCpgComponent.getString("groupId"));
		}
		LOGGER.info("addCPGComponentToGroup.Create CPG Group Service Start currentTimeMillis-->" + System.currentTimeMillis());
		final String resMsgCPG = callAddComponentCPGService(jsonCpgComponent);
		LOGGER.info("addCPGComponentToGroup.Create CPG Group Service End currentTimeMillis-->" + System.currentTimeMillis());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addCPGComponentToGroup.Add Component to CPG Group Service message-->" + resMsgCPG);
		}

		/** Extract Service message **/
		if(null != resMsgCPG && !("").equals(resMsgCPG)){
			jsonObjectRes = new JSONObject(resMsgCPG);
		}
		if(null != jsonObjectRes){
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
			LOGGER.debug("addCPGComponentToGroup.Calling Add component for CPG service End");
		}
		/** End Component Addition for All Type **/

		// Call DAO to fetch Group Details after getting response from service
		CreateGroupForm createGroupForm = new CreateGroupForm();

		createGroupForm.setGroupId(groupId);
		createGroupForm.setGroupType(groupType);
		createGroupForm.setGroupCretionMsg(responseMsg);
		createGroupForm.setGroupCreationStatus(groupCreationStatus);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer object value from DTO to Form Object End");

		}
		/** **/

		LOGGER.info("Exist addComponentToGroup-->");
		return createGroupForm;
	}
	
	/**
	 * This method prepare the list for UI
	 * 
	 * @param getSplitColorDetailsList
	 * @return
	 */
	public final List<GroupAttributeForm> prepareListForView(final List<GroupAttributeForm> getSplitColorDetailsList) {
		LOGGER.info("Enter-->calling prepareListForView");
		List<GroupAttributeForm> updatedSplitColorDetailsList = new ArrayList<GroupAttributeForm>();
		GroupAttributeForm groupAttributeForm = null;
		String productName = "";

			for (int i = 0; i < getSplitColorDetailsList.size(); i++) {
				groupAttributeForm = getSplitColorDetailsList.get(i);
				String entryType = groupAttributeForm.getEntryType();
				if (null != entryType && ("Style").equals(entryType)) {
					productName = groupAttributeForm.getProdName();
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Style ProductName is -->" + productName);
					}
				}else if(null != entryType && ("StyleColor").equals(entryType)){
					
					if (LOGGER.isDebugEnabled()) {
						
						LOGGER.debug("ColorCode------------------added------------------------------------>"
								+ groupAttributeForm.getColorCode());
					}
					groupAttributeForm.setProdName(productName);
					updatedSplitColorDetailsList.add(groupAttributeForm);
				}else{ //TBD for SplitSKU
					
				}
			}
			
		LOGGER.info("Exit-->calling prepareListForView");
		return updatedSplitColorDetailsList;
	}

}
