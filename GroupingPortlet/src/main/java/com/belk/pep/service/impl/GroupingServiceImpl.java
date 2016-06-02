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
import com.belk.pep.service.GroupingService;
import com.belk.pep.util.PropertyLoader;

/**
 * The Class GroupingServiceImpl.
 */
public class GroupingServiceImpl implements GroupingService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(GroupingServiceImpl.class.getName());

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
	public final CreateGroupForm saveGroupHeaderDetails(
			final JSONObject jsonStyle, final String updatedBy,
			final List<GroupAttributeForm> selectedSplitAttributeList)
			throws Exception, PEPFetchException {
		LOGGER.info("Entering saveGroupHeaderDetails-->");
		// CreateGroupDTO createGroupD =
		// groupingDAO.getGroupHeaderDetails("1005002"); // TODO

		String groupIdRes = "";
		/*
		 * Component component = null; List<Component> componentList = null;
		 */
		String responseMsg = "";
		String responseMsgCode = "";
		String groupCreationStatus = "";

		/** Calling Web Service to create Group except Split type **/
		LOGGER.info("Create Group Service Start currentTimeMillis-->"+ System.currentTimeMillis());
		String resMsg = callCreateGroupService(jsonStyle);
		LOGGER.info("Create Group Service End currentTimeMillis-->"	+ System.currentTimeMillis());
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Create Group Service message-->" + resMsg);
		}
		final Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
		
		/** Extract Service message **/
		/*resMsg = (null == resMsg ? "" : resMsg.replaceAll("\"", ""));
		resMsg = resMsg.substring(1, resMsg.length() - 1);
		responseMsgArray = resMsg.split(",");
		LOGGER.debug("responseMsgArray-->" + responseMsgArray);
		for (int i = 0; i < responseMsgArray.length; i++) {
			if (responseMsgArray[i].split(":").length == 2) {
				String key = responseMsgArray[i].split(":")[0];
				String value = responseMsgArray[i].split(":")[1];
				if (("code").equalsIgnoreCase(key)) {
					responseMsgCode = value;
				}
				if (("id").equalsIgnoreCase(key)) {
					groupIdRes = value;
				}
			}
		}*/
		
		JSONObject jsonObjectRes = new JSONObject(resMsg);
		responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE); 
		groupIdRes = jsonObjectRes.getString(GroupingConstants.COMPONENT_ID);

		// responseMsgCode = responseMsgArray[0].split(":")[1];
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("responseMsgCode-->" + responseMsgCode);
			LOGGER.debug("groupIdRes-->" + groupIdRes);
		}
		
		if (null != responseMsgCode
				&& responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
			responseMsg = prop
					.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_SUCCESS);
			groupCreationStatus = GroupingConstants.GROUP_CREATED;
			LOGGER.info("responseMsg100::Success-->" + responseMsg);
		} else {
			responseMsg = prop
					.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_FAILURE);
			groupCreationStatus = GroupingConstants.GROUP_NOT_CREATED;
			LOGGER.info("responseMsg101::Failure-->" + responseMsg);
		}
		/** End of Group Header Creation **/

		// JSONObject jsonStyle = (JSONObject) jsonArray.get(0);
		String groupType = jsonStyle.getString(GroupingConstants.GROUP_TYPE);
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("groupType from jsonStyle Response after creating Group Header-->"
					+ groupType);
			LOGGER.debug("responseMsgCode from jsonStyle Response after creating Group Header-->"
					+ responseMsgCode);
		}
		/** Call add component service to add Split Color attribute details List **/
		if (null != groupType
				&& groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_COLOR)
				&& null != responseMsgCode
				&& responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Calling Add component for Split Color service Start");
			}
			// Create Split Group

			/*
			 * JSONArray jsonArraySplitColor = new JSONArray(); componentList =
			 * new ArrayList<Component>(); for (int i = 0; i <
			 * selectedSplitAttributeList.size(); i++) { component = new
			 * Component(); GroupAttributeForm selectedGroupAttributeForm =
			 * selectedSplitAttributeList.get(i);
			 * component.setId(selectedGroupAttributeForm.getOrinNumber());
			 * component
			 * .setDefaultAttr(selectedGroupAttributeForm.getIsDefault());
			 * componentList.add(component); }
			 */
			JSONObject jsonStyleSpliColor = populateAddComponentSCGJson(
					groupIdRes, groupType, updatedBy,
					selectedSplitAttributeList/* componentList */);
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Add Color Attribute JSON-->" + jsonStyleSpliColor);
	
				// jsonArraySplitColor.put(jsonStyleSpliColor);
				LOGGER.debug("json Object Add Component to Split Color groupId--> "
						+ jsonStyleSpliColor.getString("groupId"));
			}
			LOGGER.info("Create Split Color Group Service Start currentTimeMillis-->"
					+ System.currentTimeMillis());
			final String resMsgSplitColor = ""; // callAddComponentSCGService(jsonStyleSpliColor);
												// // TODO uncomment
			LOGGER.info("Create Split Color Group Service End currentTimeMillis-->"
					+ System.currentTimeMillis());
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Add Component to Split Color Group Service message-->"
						+ resMsgSplitColor);
			}

			/** Extract Service message **/
			/*responseMsgArray = resMsgSplitColor.split(",");
			LOGGER.debug("responseMsgArray Split Color -->" + responseMsgArray);
			for (int i = 0; i < responseMsgArray.length; i++) {
				if (responseMsgArray[i].split(":").length == 2) {
					String key = responseMsgArray[i].split(":")[0];
					String value = responseMsgArray[i].split(":")[1];
					if (("code").equalsIgnoreCase(key)) {
						responseMsgCode = value;
					}
				}
			}*/
			
			jsonObjectRes = new JSONObject(resMsgSplitColor);
			responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("resMsgSplitColor.responseMsgCode-->" + responseMsgCode);
			}

			if (null != responseMsgCode
					&& responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
				responseMsg = "#"
						+ groupIdRes
						+ " "
						+ prop.getProperty(GroupingConstants.ADD_COMPONENT_SCG_SERVICE_SUCCESS);
				groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_COMPONENT_SCG;
				LOGGER.info("Add Component to Split Color Group. ResponseMsg100::Success-->"
						+ responseMsg);
			} else {
				responseMsg = prop
						.getProperty(GroupingConstants.ADD_COMPONENT_SCG_SERVICE_FAILURE);
				groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_OUT_COMPONENT_SCG;
				LOGGER.info("Add Component to Split Color Group. ResponseMsg101::Failure-->"
						+ responseMsg);
			}
			LOGGER.debug("Calling Add component for Split Color service End");
		}

		/** Call add component service to add split SKU attribute details List **/
		if (null != groupType
				&& groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_SKU)
				&& null != responseMsgCode
				&& responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
			LOGGER.debug("Calling Add component for Split SKU service Start");
			// Create Split Group

			/*
			 * JSONArray jsonArraySplitSku = new JSONArray(); componentList =
			 * new ArrayList<Component>(); for (int i = 0; i <
			 * selectedSplitAttributeList.size(); i++) { component = new
			 * Component(); GroupAttributeForm selectedGroupAttributeForm =
			 * selectedSplitAttributeList.get(i);
			 * component.setId(selectedGroupAttributeForm.getOrinNumber());
			 * component
			 * .setDefaultAttr(selectedGroupAttributeForm.getIsDefault());
			 * component.setColor(selectedGroupAttributeForm.getColorCode());
			 * component.setSize(selectedGroupAttributeForm.getSize());
			 * componentList.add(component); }
			 */
			JSONObject jsonStyleSpliSku = populateAddComponentSSGJson(
					groupIdRes, groupType, updatedBy,
					selectedSplitAttributeList/* componentList */);
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Add SKU Attribute JSON-->" + jsonStyleSpliSku);
				LOGGER.debug("json Object Add Component to Split SKU groupId--> "
						+ jsonStyleSpliSku.getString("groupId"));
			}
			LOGGER.info("Create Split SKU Group Service Start currentTimeMillis-->"
					+ System.currentTimeMillis());
			String resMsgSplitSku = ""; // callAddComponentSSGService(jsonStyleSpliSku);
										// // TODO Uncomment
			LOGGER.info("Create Split SKU Group Service End currentTimeMillis-->"
					+ System.currentTimeMillis());
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Add Component to Split SKU Group Service message-->" + resMsgSplitSku);
			}

			/** Extract Service message **/
			/*responseMsgArray = resMsgSplitSku.split(",");
			LOGGER.debug("responseMsgArray Split SKU -->" + responseMsgArray);
			for (int i = 0; i < responseMsgArray.length; i++) {
				if (responseMsgArray[i].split(":").length == 2) {
					String key = responseMsgArray[i].split(":")[0];
					String value = responseMsgArray[i].split(":")[1];
					if (("code").equalsIgnoreCase(key)) {
						responseMsgCode = value;
					}
				}
			}*/
			jsonObjectRes = new JSONObject(resMsgSplitSku);
			responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("resMsgSplitSku.responseMsgCode-->" + responseMsgCode);
			}

			if (null != responseMsgCode
					&& responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
				responseMsg = groupIdRes
						+ "-"
						+ prop.getProperty(GroupingConstants.ADD_COMPONENT_SSG_SERVICE_SUCCESS);
				groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_COMPONENT_SSG;
				LOGGER.info("Add Component to Split SKU Group. ResponseMsg100::Success-->"
						+ responseMsg);
			} else {
				responseMsg = "#"
						+ groupIdRes
						+ " "
						+ prop.getProperty(GroupingConstants.ADD_COMPONENT_SSG_SERVICE_FAILURE);
				groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_OUT_COMPONENT_SSG;
				LOGGER.info("Add Component to Split SKU Group. ResponseMsg101::Failure-->"
						+ responseMsg);
			}
			LOGGER.debug("Calling Add component for Split SKU service End");
		}
		/** End Group Creation for All Type **/

		// Call DAO to fetch Group Details after getting response from service
		CreateGroupForm createGroupForm = new CreateGroupForm();
		CreateGroupDTO createGroupDTO = new CreateGroupDTO();
		if (null != responseMsgCode
				&& (GroupingConstants.SUCCESS_CODE).equals(responseMsgCode)) {
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Before Calling database method getGroupHeaderDetails() to retreive Group Header Details-->");
			}
			createGroupDTO = groupingDAO.getGroupHeaderDetails(groupIdRes);
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("After Calling database method getGroupHeaderDetails() to retreive Group Header Details-->"
						+ createGroupDTO.getGroupId());
			}
		}
		createGroupDTO.setGroupCretionMsg(responseMsg);

		/** Transfer object value from DTO to Form Object Start **/
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Transfer object value from DTO to Form Object Start");
		}
		createGroupForm.setGroupId(createGroupDTO.getGroupId());
		createGroupForm.setGroupName(createGroupDTO.getGroupName());
		createGroupForm.setGroupType(createGroupDTO.getGroupType());
		createGroupForm.setGroupDesc(createGroupDTO.getGroupDesc());
		createGroupForm.setGroupLaunchDate(createGroupDTO.getGroupLaunchDate());
		createGroupForm.setEndDate(createGroupDTO.getEndDate());
		createGroupForm.setGroupCretionMsg(createGroupDTO.getGroupCretionMsg());
		createGroupForm.setGroupAttributeFormList(createGroupDTO
				.getGroupAttributeDTOList());
		createGroupForm.setGroupCreationStatus(groupCreationStatus);
		createGroupForm.setGroupStatus(createGroupDTO.getGroupStatus());
		createGroupForm.setCarsGroupType(createGroupDTO.getCarsGroupType());
		
		if(LOGGER.isDebugEnabled()){
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
	public final String callCreateGroupService(final JSONObject jsonStyle)
			throws Exception, PEPFetchException {
		LOGGER.info("Entering callCreateGroupService-->");
		LOGGER.debug("jsonArray-->" + jsonStyle);
		String responseMsg = "";
		try {
			Properties prop = PropertyLoader
					.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop
					.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_URL);
			LOGGER.info("Create Group ServiceURL-->" + serviceURL);

			URL targetUrl = new URL(serviceURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl
					.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod(prop
					.getProperty(GroupingConstants.SERVICE_REQUEST_METHOD));
			httpConnection
					.setRequestProperty(
							prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
							prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));

			/*
			 * httpConnection.setRequestMethod("POST");
			 * httpConnection.setRequestProperty
			 * ("Content-Type","application/json");
			 */

			LOGGER.info("callCreateGroupService Service::Json Array-->"
					+ jsonStyle.toString());

			String input = jsonStyle.toString();
			LOGGER.debug("final object in json-->" + "\t"
					+ jsonStyle.toString());
			LOGGER.info("input....json-->" + input);
			// LOGGER.info("json object-->" + (new
			// JSONObject(input)).getString("groupDesc"));
			// LOGGER.info("httpConnection.getOutputStream()-->" +
			// httpConnection.getOutputStream());

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();

			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));
			String output;
			LOGGER.info("Output from Server:::" + "\t" + "after Calling-->");
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("CreateGroupService Service Output-->" + output);

				LOGGER.info("Single Request-->");
				responseMsg = output;
			}

			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException");
			throw new PEPFetchException();
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException");
			throw new PEPFetchException();
		} catch (IOException e) {
			LOGGER.error("inside IOException");
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException");
		} catch (Exception e) {
			LOGGER.error("inside Exception" + e);
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
	public final List<GroupAttributeForm> getSplitColorDetails(
			final String vendorStyleNo, final String styleOrin)
			throws PEPServiceException, PEPPersistencyException {
		// getting the Style Color details from Database
		LOGGER.info("Enter-->calling getSplitColorDetails from GroupingServiceImpl.");
		List<GroupAttributeForm> getSplitColorDetailsList = null;
		try {
			getSplitColorDetailsList = groupingDAO.getSplitColorDetails(
					vendorStyleNo, styleOrin);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer");
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
	public final List<GroupAttributeForm> getSplitSKUDetails(
			final String vendorStyleNo, final String styleOrin)
			throws PEPServiceException, PEPPersistencyException {
		// getting the Style Color details from Database
		LOGGER.info("*Enter-->calling getSplitSKUDetails from GroupingServiceImpl.");
		List<GroupAttributeForm> getSplitColorDetailsList = null;
		try {
			getSplitColorDetailsList = groupingDAO.getSplitSKUDetails(
					vendorStyleNo, styleOrin);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer");
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getSplitSKUDetails from GroupingServiceImpl.");
		return getSplitColorDetailsList;
	}

	/**
	 * This method is used to validate Split Color Attribute list before add it
	 * to group.
	 * 
	 * @param getSplitColorDetailsList
	 * @return updatedSplitColorDetailsList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<GroupAttributeForm> validateSCGAttributeDetails(
			final List<GroupAttributeForm> getSplitColorDetailsList)
			throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("Enter-->calling validateSCGAttributeDetails");
		List<GroupAttributeForm> updatedSplitColorDetailsList = new ArrayList<GroupAttributeForm>();
		GroupAttributeForm groupAttributeForm = null;
		String productName = "";
		try {

			for (int i = 0; i < getSplitColorDetailsList.size(); i++) {
				groupAttributeForm = getSplitColorDetailsList.get(i);
				String entryType = groupAttributeForm.getEntryType();
				if (null != entryType && ("Style").equals(entryType)) {
					productName = groupAttributeForm.getProdName();
					break;
				}
			}
			LOGGER.debug("Style ProductName is -->" + productName);

			for (int i = 0; i < getSplitColorDetailsList.size(); i++) {
				groupAttributeForm = getSplitColorDetailsList.get(i);
				String entryType = groupAttributeForm.getEntryType();
				String petStatus = groupAttributeForm.getPetStatus();
				// LOGGER.debug("Pet Status not Completed. Orin No: " +
				// groupAttributeForm.getOrinNumber() + ", PetStatus: " +
				// petStatus);
				// LOGGER.debug("ColorCode------------------added------------------------------------>"
				// + groupAttributeForm.getColorCode());
				if (null != petStatus
						&& !(GroupingConstants.PET_STATUS_COMPLETED)
								.equals(petStatus.trim())) {
					LOGGER.debug("Pet Status not Completed. Orin No: "
							+ groupAttributeForm.getOrinNumber()
							+ ", PetStatus: " + petStatus);
					updatedSplitColorDetailsList = new ArrayList<GroupAttributeForm>();
					break;
				}
				if (null != entryType && !("Style").equals(entryType)) {
					groupAttributeForm.setProdName(productName);
					updatedSplitColorDetailsList.add(groupAttributeForm);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer");
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling validateSCGAttributeDetails");
		return updatedSplitColorDetailsList;
	}

	/**
	 * This method is used to validate Split SKU Attribute list before add it to
	 * group.
	 * 
	 * @param getSplitColorDetailsList
	 * @return updatedgetSplitSKUDetailsList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<GroupAttributeForm> validateSSGAttributeDetails(
			final List<GroupAttributeForm> getSplitSKUDetailsList)
			throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("Enter-->calling validateSSGAttributeDetails");
		List<GroupAttributeForm> updatedgetSplitSKUDetailsList = new ArrayList<GroupAttributeForm>();
		GroupAttributeForm groupAttributeForm = null;
		String productName = "";
		try {

			for (int i = 0; i < getSplitSKUDetailsList.size(); i++) {
				groupAttributeForm = getSplitSKUDetailsList.get(i);
				String entryType = groupAttributeForm.getEntryType();
				if (null != entryType && ("Style").equals(entryType)) {
					productName = groupAttributeForm.getProdName();
					break;
				}
			}
			LOGGER.debug("Style ProductName is -->" + productName);

			for (int i = 0; i < getSplitSKUDetailsList.size(); i++) {
				groupAttributeForm = getSplitSKUDetailsList.get(i);
				String entryType = groupAttributeForm.getEntryType();
				String petStatus = groupAttributeForm.getPetStatus();
				if (null != petStatus
						&& !(GroupingConstants.PET_STATUS_COMPLETED)
								.equals(petStatus.trim())) {
					LOGGER.debug("Pet Status not Completed. Orin No: "
							+ groupAttributeForm.getOrinNumber()
							+ ", PetStatus: " + petStatus);
					updatedgetSplitSKUDetailsList = new ArrayList<GroupAttributeForm>();
					break;
				}
				if (null != entryType && (!("Style").equals(entryType))
						&& !("StyleColor").equals(entryType)) {
					groupAttributeForm.setProdName(productName);
					updatedgetSplitSKUDetailsList.add(groupAttributeForm);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the Service Implementation Layer");
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling validateSSGAttributeDetails");
		return updatedgetSplitSKUDetailsList;
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
	public final JSONObject populateAddComponentSCGJson(
			final String groupIdRes, final String groupType,
			final String updatedBy,
			final List<GroupAttributeForm> selectedSplitAttributeList/*
																	 * List<
																	 * Component
																	 * >
																	 * componentList
																	 */) {
		LOGGER.info("Entering populateAddComponentSCGJson-->");
		/*
		 * JSONObject jsonObj = new JSONObject(); try {
		 * jsonObj.put(GroupingConstants.GROUP_ID, groupIdRes);
		 * jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
		 * jsonObj.put(GroupingConstants.COMPONENT_LIST, componentList);
		 * jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy); } catch
		 * (JSONException e) {
		 * LOGGER.error("Exeception in parsing the jsonObj"); } return jsonObj;
		 */
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObjComponent = null;
		GroupAttributeForm groupAttributeForm = null;
		JSONArray jsonArray = new JSONArray();
		try {
			// selectedSplitAttributeList =
			// createGroupForm.getGroupAttributeFormList();
			for (int i = 0; i < selectedSplitAttributeList.size(); i++) {
				jsonObjComponent = new JSONObject();
				groupAttributeForm = selectedSplitAttributeList.get(i);
				jsonObjComponent.put(GroupingConstants.COMPONENT_ID,
						groupAttributeForm.getOrinNumber());
				jsonObjComponent.put(GroupingConstants.COMPONENT_IS_DEFAULT,
						groupAttributeForm.getIsDefault());
				jsonArray.put(jsonObjComponent);
			}

			jsonObj.put(GroupingConstants.GROUP_ID, groupIdRes);
			jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
			jsonObj.put(GroupingConstants.COMPONENT_LIST, jsonArray);
			jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy);
		} catch (JSONException e) {
			LOGGER.error("Exeception in parsing the jsonObj");
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
	public final String callAddComponentSCGService(
			final JSONObject jsonStyleSpliColor) throws Exception,
			PEPFetchException {
		LOGGER.info("Entering callAddComponentSCGService-->");
		LOGGER.debug("jsonStyleSpliColor-->" + jsonStyleSpliColor);
		String responseMsg = "";
		try {
			Properties prop = PropertyLoader
					.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop
					.getProperty(GroupingConstants.ADD_COMPONENT_TO_SCG_SERVICE_URL);
			LOGGER.info("Add Component to Split Color ServiceURL-->"
					+ serviceURL);

			URL targetUrl = new URL(serviceURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl
					.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod(prop
					.getProperty(GroupingConstants.SERVICE_REQUEST_METHOD));
			httpConnection
					.setRequestProperty(
							prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
							prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));

			LOGGER.info("callAddComponentSCGService Service::Json Array-->"
					+ jsonStyleSpliColor.toString());

			/*
			 * JSONObject jsonMap = new JSONObject();
			 * jsonMap.put(GroupingConstants
			 * .CREATE_GROUP_SERVICE_LIST,jsonArray); String input =
			 * jsonMap.toString();
			 */

			String input = jsonStyleSpliColor.toString();
			LOGGER.debug("final object in json-->" + "\t"
					+ jsonStyleSpliColor.toString());
			LOGGER.info("input....json-->" + input);
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();

			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));
			String output;
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("call callAddComponentSCGService Service Output-->"
						+ output);

				/*
				 * Response Message format- Pass:-
				 * {"code":"100","description":"Group created "
				 * ,"status":"SUCCESS",”groupId”:”XXXXXXX”} Fail:-
				 * {"code":"101",
				 * "description":"Group is not created ","status":"FAIL"
				 * ,”groupId”:””}
				 */

				// Below if block is for handling single row data.
				// if ((jsonArray != null) && (jsonArray.length() <= 1)) {
				LOGGER.info("Single Request-->");
				responseMsg = output;
				// }
			}

			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException");
			throw new PEPFetchException();
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException");
			throw new PEPFetchException();
		} catch (IOException e) {
			LOGGER.error("inside IOException");
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException");
		} catch (Exception e) {
			LOGGER.error("inside Exception" + e);
		}
		LOGGER.info("Exiting callAddComponentSCGService-->" + responseMsg);
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
	public final JSONObject populateAddComponentSSGJson(
			final String groupIdRes, final String groupType,
			final String updatedBy,
			final List<GroupAttributeForm> selectedSplitAttributeList/*
																	 * List<
																	 * Component
																	 * >
																	 * componentList
																	 *//*
																		 * List<
																		 * Component
																		 * >
																		 * componentList
																		 */) {
		LOGGER.info("Entering populateAddComponentSSGJson-->");
		/*
		 * JSONObject jsonObj = new JSONObject(); try {
		 * jsonObj.put(GroupingConstants.GROUP_ID, groupIdRes);
		 * jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
		 * jsonObj.put(GroupingConstants.COMPONENT_LIST, componentList);
		 * jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy); } catch
		 * (JSONException e) {
		 * LOGGER.error("Exeception in parsing the jsonObj"); } return jsonObj;
		 */

		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObjComponent = null;
		GroupAttributeForm groupAttributeForm = null;
		JSONArray jsonArray = new JSONArray();
		try {
			// selectedSplitAttributeList =
			// createGroupForm.getGroupAttributeFormList();
			for (int i = 0; i < selectedSplitAttributeList.size(); i++) {
				jsonObjComponent = new JSONObject();
				groupAttributeForm = selectedSplitAttributeList.get(i);
				jsonObjComponent.put(GroupingConstants.COMPONENT_ID,
						groupAttributeForm.getOrinNumber());
				jsonObjComponent.put(GroupingConstants.COMPONENT_IS_DEFAULT,
						groupAttributeForm.getIsDefault());
				jsonObjComponent.put(GroupingConstants.COMPONENT_COLOR,
						groupAttributeForm.getColorCode());
				jsonObjComponent.put(GroupingConstants.COMPONENT_SIZE,
						groupAttributeForm.getSize());
				jsonArray.put(jsonObjComponent);
			}

			jsonObj.put(GroupingConstants.GROUP_ID, groupIdRes);
			jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
			jsonObj.put(GroupingConstants.COMPONENT_LIST, jsonArray);
			jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy);
		} catch (JSONException e) {
			LOGGER.error("Exeception in parsing the jsonObj");
		}
		LOGGER.info("Exiting populateAddComponentSSGJson-->" + jsonArray);
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
	public final String callAddComponentSSGService(
			final JSONObject jsonStyleSpliSku) throws Exception,
			PEPFetchException {
		LOGGER.info("Entering callAddComponentSSGService-->");
		LOGGER.debug("jsonArray-->" + jsonStyleSpliSku);
		String responseMsg = "";
		try {
			Properties prop = PropertyLoader
					.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop
					.getProperty(GroupingConstants.ADD_COMPONENT_TO_SSG_SERVICE_URL);
			LOGGER.info("Add Component to Split SKU ServiceURL-->" + serviceURL);

			URL targetUrl = new URL(serviceURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl
					.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod(prop
					.getProperty(GroupingConstants.SERVICE_REQUEST_METHOD));
			httpConnection
					.setRequestProperty(
							prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
							prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));

			LOGGER.info("callAddComponentSSGService Service::Json Array-->"
					+ jsonStyleSpliSku.toString());

			/*
			 * JSONObject jsonMap = new JSONObject();
			 * jsonMap.put(GroupingConstants
			 * .CREATE_GROUP_SERVICE_LIST,jsonArray); String input =
			 * jsonMap.toString();
			 */

			String input = jsonStyleSpliSku.toString();
			LOGGER.debug("final object in json-->" + "\t"
					+ jsonStyleSpliSku.toString());
			LOGGER.info("input....json-->" + input);
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();

			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));
			String output;
			LOGGER.info("Output from Server:::" + "\t" + "after Calling-->");
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("call callAddComponentSSGService Service Output-->"
						+ output);

				/*
				 * Response Message format- Pass:-
				 * {"code":"100","description":"Group created "
				 * ,"status":"SUCCESS",”groupId”:”XXXXXXX”} Fail:-
				 * {"code":"101",
				 * "description":"Group is not created ","status":"FAIL"
				 * ,”groupId”:””}
				 */

				// Below if block is for handling single row data.
				// if ((jsonArray != null) && (jsonArray.length() <= 1)) {
				LOGGER.info("Single Request-->");
				responseMsg = output;
				// }
			}

			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException");
			throw new PEPFetchException();
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException");
			throw new PEPFetchException();
		} catch (IOException e) {
			LOGGER.error("inside IOException");
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException");
		} catch (Exception e) {
			LOGGER.error("inside Exception" + e);
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
	public final List<GroupAttributeForm> getSelectedColorAttributeList(
			final List<GroupAttributeForm> updatedSplitColorDetailsList,
			final String[] selectedItemsArr, final String defaultSelectedAttr)
			throws PEPServiceException, PEPPersistencyException {
		// getting the Style Color details from Database
		LOGGER.info("*Enter-->calling getSelectedColorAttributeList from GroupingServiceImpl.");
		List<GroupAttributeForm> selectedSplitAttributeList = new ArrayList<GroupAttributeForm>();
		GroupAttributeForm groupAttributeForm = null;
		String colorCode = "";
		for (int i = 0; i < updatedSplitColorDetailsList.size(); i++) {
			groupAttributeForm = updatedSplitColorDetailsList.get(i);
			colorCode = (null == groupAttributeForm.getColorCode() ? ""
					: groupAttributeForm.getColorCode().trim());
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

		LOGGER.debug("selectedSplitAttributeList.size()-->"
				+ selectedSplitAttributeList.size());
		LOGGER.info("Exit-->calling getSelectedColorAttributeList from GroupingServiceImpl.");
		return selectedSplitAttributeList;
	}

	/**
	 * This method is used to get the selected Attribute List for SSJ.
	 * 
	 * @param updatedSplitSKUDetailsList
	 * @param selectedItemsArr
	 * @param defaultSelectedAttr
	 * @return selectedSplitAttributeList
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public final List<GroupAttributeForm> getSelectedSKUAttributeList(
			final List<GroupAttributeForm> updatedSplitSKUDetailsList,
			final String[] selectedItemsArr, final String defaultSelectedAttr)
			throws PEPServiceException, PEPPersistencyException {
		// getting the Style Color details from Database
		LOGGER.info("*Enter-->calling getSelectedSKUAttributeList from GroupingServiceImpl.");
		List<GroupAttributeForm> selectedSplitAttributeList = new ArrayList<GroupAttributeForm>();
		GroupAttributeForm groupAttributeForm = null;
		String sizeCode = "";
		for (int i = 0; i < updatedSplitSKUDetailsList.size(); i++) {
			groupAttributeForm = updatedSplitSKUDetailsList.get(i);
			sizeCode = (null == groupAttributeForm.getSize() ? ""
					: groupAttributeForm.getSize().trim());
			if (sizeCode.equals(defaultSelectedAttr)) {
				groupAttributeForm.setIsDefault("yes");
			} else {
				groupAttributeForm.setIsDefault("no");
			}
			for (int j = 0; j < selectedItemsArr.length; j++) {
				// LOGGER.debug(sizeCode + "<--Bean Array-->" +
				// selectedItemsArr[j] + "<--");
				if (selectedItemsArr[j].equals(sizeCode)) {
					selectedSplitAttributeList.add(groupAttributeForm);
					break;
				}
			}
		}

		LOGGER.debug("selectedSplitAttributeList.size()-->"
				+ selectedSplitAttributeList.size());
		LOGGER.info("Exit-->calling getSelectedSKUAttributeList from GroupingServiceImpl.");
		return selectedSplitAttributeList;
	}

	/**
	 * This method is used to get the Existing Group Details
	 * 
	 * @param groupId
	 * @return createGroupForm
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	public final CreateGroupForm getExistingGrpDetails(final String groupId)
			throws Exception, PEPFetchException {
		LOGGER.info("Entering getExistingGrpDetails-->");

		// Call DAO to fetch Group Details after getting response from service
		CreateGroupForm createGroupForm = new CreateGroupForm();
		CreateGroupDTO createGroupDTO = new CreateGroupDTO();
		List<GroupAttributeForm> groupAttributeDTOList = new ArrayList<GroupAttributeForm>();

		LOGGER.debug("Before Calling database method getGroupHeaderDetails() to retreive Group Header Details-->");
		createGroupDTO = groupingDAO.getGroupHeaderDetails(groupId);
		LOGGER.debug("After Calling database method getGroupHeaderDetails() to retreive Group Header Details-->"
				+ createGroupDTO.getGroupId());

		LOGGER.debug("Before Calling database method getExistingGrpDetails() to retreive Existing Group Attribute Details-->");
		// createGroupDTO = groupingDAO.getGroupHeaderDetails(groupId);
		// TODO
		LOGGER.debug("After Calling database method getExistingGrpDetails() to retreive Existing Group Attribute Details-->"
				+ createGroupDTO.getGroupId());

		/** Transfer object value from DTO to Form Object Start **/
		LOGGER.debug("Transfer object value from DTO to Form Object Start");
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

		LOGGER.debug("Transfer object value from DTO to Form Object End");
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
	public final GroupSearchForm groupSearch(
			final GroupSearchForm groupSearchForm) throws PEPServiceException,
			PEPPersistencyException {

		LOGGER.info("Entering groupSearch() in GroupingService class.");

		List<GroupForm> groupFormList = new ArrayList<GroupForm>();
		List<GroupSearchDTO> listOneGroup = groupingDAO
				.groupSearch(groupSearchForm);
		LOGGER.debug("List one size -- " + listOneGroup.size());
		if (listOneGroup.size() > 0) {
			GroupForm groupFormParent = null;
			Map<String, GroupForm> mapGroup = new HashMap<String, GroupForm>();
			Properties prop = PropertyLoader
					.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);
			for (Iterator<GroupSearchDTO> iterator = listOneGroup.iterator(); iterator
					.hasNext();) {
				GroupSearchDTO groupSearchDTOParent = (GroupSearchDTO) iterator
						.next();
				if (groupSearchDTOParent != null) {
					groupFormParent = new GroupForm();
					groupFormParent.setGroupId(groupSearchDTOParent
							.getGroupId());
					groupFormParent.setGroupName(groupSearchDTOParent
							.getGroupName());
					groupFormParent.setGroupContentStatus(groupSearchDTOParent
							.getGroupContentStatus());
					groupFormParent.setGroupImageStatus(groupSearchDTOParent
							.getGroupImageStatus());
					groupFormParent.setGroupType(prop
							.getProperty("group.types."
									+ groupSearchDTOParent.getGroupType()));
					groupFormParent.setGroupTypeCode(groupSearchDTOParent
							.getGroupType());
					groupFormParent.setChildGroup(groupSearchDTOParent
							.getChildGroup());
					if (groupSearchDTOParent.getStartDate().length() >= GroupingConstants.DATE_FIELD_LENGTH) {
						groupFormParent.setStartDate(groupSearchDTOParent
								.getStartDate().substring(0,
										GroupingConstants.DATE_FIELD_LENGTH));
					} else {
						groupFormParent.setStartDate(groupSearchDTOParent
								.getStartDate());
					}
					if (groupSearchDTOParent.getEndDate().length() >= GroupingConstants.DATE_FIELD_LENGTH) {
						groupFormParent.setEndDate(groupSearchDTOParent
								.getEndDate().substring(0,
										GroupingConstants.DATE_FIELD_LENGTH));
					} else {
						groupFormParent.setEndDate(groupSearchDTOParent
								.getEndDate());
					}
					mapGroup.put(groupFormParent.getGroupId(), groupFormParent);
					groupFormList.add(groupFormParent);
				}
			}
			if (groupSearchForm.getGroupId() != null
					&& !groupSearchForm.getGroupId().trim().equals("")) {
				List<GroupSearchDTO> listTwoGroup = groupingDAO
						.groupSearchParent(listOneGroup, groupSearchForm);
				int parentCount = groupingDAO.groupSearchParentCount(
						listOneGroup, groupSearchForm);
				groupSearchForm.setParentCount(parentCount);
				LOGGER.debug("List two size -- " + listTwoGroup.size());
				if (listTwoGroup.size() > 0) {
					GroupForm groupForm = null;
					List<GroupForm> childGroupFormList = null;
					List<GroupForm> parentGroupFormList = new ArrayList<GroupForm>();
					for (Iterator<GroupSearchDTO> iterator = listTwoGroup
							.iterator(); iterator.hasNext();) {
						GroupSearchDTO groupSearchDTO = (GroupSearchDTO) iterator
								.next();
						if (groupSearchDTO != null) {
							groupForm = new GroupForm();

							groupForm.setGroupId(groupSearchDTO.getGroupId());
							groupForm.setGroupName(groupSearchDTO
									.getGroupName());
							groupForm.setGroupContentStatus(groupSearchDTO
									.getGroupContentStatus());
							groupForm.setGroupImageStatus(groupSearchDTO
									.getGroupImageStatus());
							groupForm.setGroupType(prop
									.getProperty("group.types."
											+ groupSearchDTO.getGroupType()));
							groupForm.setGroupTypeCode(groupSearchDTO
									.getGroupType());
							groupForm.setChildGroup(groupSearchDTO
									.getChildGroup());
							if (groupSearchDTO.getStartDate().length() >= GroupingConstants.DATE_FIELD_LENGTH) {
								groupForm
										.setStartDate(groupSearchDTO
												.getStartDate()
												.substring(
														0,
														GroupingConstants.DATE_FIELD_LENGTH));
							} else {
								groupForm.setStartDate(groupSearchDTO
										.getStartDate());
							}
							if (groupSearchDTO.getEndDate().length() >= GroupingConstants.DATE_FIELD_LENGTH) {
								groupForm
										.setEndDate(groupSearchDTO
												.getEndDate()
												.substring(
														0,
														GroupingConstants.DATE_FIELD_LENGTH));
							} else {
								groupForm.setEndDate(groupSearchDTO
										.getEndDate());
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

			LOGGER.debug("Final List size -- " + groupFormList.size());
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
	public final int groupSearchCount(final GroupSearchForm groupSearchForm)
			throws PEPPersistencyException, PEPServiceException {
		LOGGER.info("Entering groupSearchCount() in GroupingService class.");
		int recordCount = 0;
		try {
			recordCount = groupingDAO.groupSearchCount(groupSearchForm);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at groupSearchCount() in GroupingService class");
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
	public final ArrayList<DepartmentDetails> getDeptDetailsByDepNoFromADSE()
			throws PEPPersistencyException, PEPServiceException {
		LOGGER.info("Entering getDeptDetailsByDepNoFromADSE() in GroupingService class.");
		ArrayList<DepartmentDetails> getDepartmentList = null;
		try {
			getDepartmentList = groupingDAO.getDeptDetailsByDepNoFromADSE();
		} catch (Exception e) {
			LOGGER.error("Exception occurred at getDeptDetailsByDepNoFromADSE() in GroupingService class");
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
	public final List<ClassDetails> getClassDetailsByDepNos(
			final String departmentNumbers) throws PEPPersistencyException,
			PEPServiceException {
		LOGGER.info("Entering getClassDetailsByDepNos() in GroupingService class.");
		List<ClassDetails> getClassList = null;
		try {
			getClassList = groupingDAO
					.getClassDetailsByDepNos(departmentNumbers);
		} catch (Exception e) {
			LOGGER.error("Exception occurred at getClassDetailsByDepNos() in GroupingService class");
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
	private String callDeleteGroupService(final JSONObject jsonGroup)
			throws Exception, PEPFetchException {
		LOGGER.info("Entering callDeletGroupService-->");
		LOGGER.debug("jsonArray-->" + jsonGroup);
		String responseMsg = "";
		try {
			Properties prop = PropertyLoader
					.getPropertyLoader(GroupingConstants.MESS_PROP);
			String serviceURL = prop
					.getProperty(GroupingConstants.DELETE_GROUP_SERVICE_URL);
			LOGGER.info("Delete Group ServiceURL-->" + serviceURL);

			URL targetUrl = new URL(serviceURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl
					.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod(prop
					.getProperty(GroupingConstants.SERVICE_REQUEST_METHOD));
			httpConnection
					.setRequestProperty(
							prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
							prop.getProperty(GroupingConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));

			LOGGER.info("callDeletGroupService Service::Json Array-->"
					+ jsonGroup.toString());

			String input = jsonGroup.toString();
			LOGGER.debug("final object in json-->" + "\t"
					+ jsonGroup.toString());
			LOGGER.info("input....json-->" + input);
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();

			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));
			String output;
			LOGGER.info("Output from Server:::" + "\t" + "after Calling-->");
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("DeleteGroupService Service Output-->" + output);

				LOGGER.info("Single Request-->");
				responseMsg = output;
			}

			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException");
			throw new PEPFetchException();
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException");
			throw new PEPFetchException();
		} catch (IOException e) {
			LOGGER.error("inside IOException");
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException");
		} catch (Exception e) {
			LOGGER.error("inside Exception" + e);
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
	public final String deleteGroup(final String groupId,
			final String groupType, final String updatedBy) throws Exception,
			PEPFetchException {
		LOGGER.info("Entering deleteGroup-->");
		String responseMsg = "";
		String responseMsgCode = "";

		JSONObject jsonObj = new JSONObject();
		jsonObj.put(GroupingConstants.GROUP_ID, groupId);
		jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
		jsonObj.put(GroupingConstants.CREATED_BY, updatedBy);

		/** Calling Web Service to create Group except Split type **/
		LOGGER.info("Delete Group Service Start currentTimeMillis-->"
				+ System.currentTimeMillis());
		String resMsg = callDeleteGroupService(jsonObj);
		LOGGER.info("Delete Group Service End currentTimeMillis-->"
				+ System.currentTimeMillis());
		LOGGER.debug("Delete Group Service message-->" + resMsg);
		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
		
		/** Extract Service message **/
		/*resMsg = (null == resMsg ? "" : resMsg.replaceAll("\"", ""));
		resMsg = resMsg.substring(1, resMsg.length() - 1);
		responseMsgArray = resMsg.split(",");
		LOGGER.debug("responseMsgArray-->" + responseMsgArray);
		for (int i = 0; i < responseMsgArray.length; i++) {
			if (responseMsgArray[i].split(":").length == 2) {
				String key = responseMsgArray[i].split(":")[0];
				String value = responseMsgArray[i].split(":")[1];
				if (("code").equalsIgnoreCase(key)) {
					responseMsgCode = value;
				}
			}
		}*/
		JSONObject jsonObjectRes = new JSONObject(resMsg);
		responseMsgCode = jsonObjectRes.getString(GroupingConstants.MSG_CODE);
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("resMsgGroupDelete.responseMsgCode-->" + responseMsgCode);
		}

		LOGGER.debug("responseMsgCode-->" + responseMsgCode);

		if (null != responseMsgCode
				&& responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)) {
			responseMsg = prop
					.getProperty(GroupingConstants.DELETE_GROUP_SERVICE_SUCCESS);
		} else {
			responseMsg = prop
					.getProperty(GroupingConstants.DELETE_GROUP_SERVICE_FAILURE);
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
	public final List<GroupAttributeForm> getExistSplitColorDetails(final String groupId) 
			throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("Enter-->calling getExistSplitColorDetails from GroupingServiceImpl.");
		List<GroupAttributeForm> getSplitColorDetailsList = null;
		try {
			getSplitColorDetailsList = groupingDAO.getExistSplitColorDetails(groupId);	
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the getExistSplitColorDetails().Service Implementation Layer");
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
	public final List<GroupAttributeForm> getExistSplitSkuDetails(final String groupId) 
			throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("Enter-->calling getSplitSkuDetailsList from GroupingServiceImpl.");
		List<GroupAttributeForm> getSplitSkuDetailsList = null;
		try {
			getSplitSkuDetailsList = groupingDAO.getExistSplitColorDetails(groupId);	
		} catch (Exception e) {
			LOGGER.error("Exception occurred at the getSplitSkuDetailsList().Service Implementation Layer");
			throw new PEPServiceException(e.getMessage());
		}
		LOGGER.info("Exit-->calling getSplitSkuDetailsList from GroupingServiceImpl.");
		return getSplitSkuDetailsList;
	}

}
