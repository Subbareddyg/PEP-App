package com.belk.pep.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.belk.pep.common.model.Common_BelkUser;
import com.belk.pep.common.userdata.UserData;
import com.belk.pep.constants.GroupingConstants;
import com.belk.pep.dto.ClassDetails;
import com.belk.pep.dto.DepartmentDetails;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.CreateGroupForm;
import com.belk.pep.form.GroupAttributeForm;
import com.belk.pep.form.GroupForm;
import com.belk.pep.form.GroupSearchForm;
import com.belk.pep.service.GroupingService;
import com.belk.pep.util.DateUtil;
import com.belk.pep.util.GroupingUtil;
import com.belk.pep.util.PropertyLoader;

/** The Class GroupingController. */
@Controller
@RequestMapping("View")
public class GroupingController {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(GroupingController.class.getName());

	/** The mv. */
	//private ModelAndView mv;

	/** The Grouping Service. */
	private GroupingService groupingService;

	/** @return the groupingService */
	public GroupingService getGroupingService() {
		return groupingService;
	}

	/** @param groupingService
	 *            the groupingService to set */
	public void setGroupingService(GroupingService groupingService) {
		this.groupingService = groupingService;
	}

	/** Create URL to render page for SPLIT Color group
	 * 
	 * @param renderRequest
	 * @param renderResponse
	 * @return */
	@RenderMapping(params = "groupingTypeSplitColor=splitColor")
	public ModelAndView splitColorRenderHandler(RenderRequest renderRequest, RenderResponse renderResponse) {
		// do some processing here
		LOGGER.info("GroupingControlle:splitRenderHandler:Enter");
		ModelAndView mv = new ModelAndView(GroupingConstants.GROUPING_CREATE_SPLIT_COLOR_GROUP);
		/** changes to display lan id - changed by Ramkumar - starts **/
		String sessionDataKey = (String) renderRequest.getPortletSession().getAttribute("sessionDataKey");
		if (null != sessionDataKey) {
			String userID = sessionDataKey.split(GroupingConstants.USER_DATA + renderRequest.getPortletSession().getId())[1];
			LOGGER.info("display user ID" + userID);
			mv.addObject(GroupingConstants.LAN_ID, userID);
		}
		/** changes to display lan id - changed by Ramkumar - ends **/
		
		/** changes to display Grouping Types - Starts **/
		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);
		String groups = prop.getProperty(GroupingConstants.GROUP_TYPES);
		String groupTypes[] = groups.split(",");
		Map<String, String> groupMap = new TreeMap<String, String>();
		for (int count = 0; count < groupTypes.length; count++) {
			String key = groupTypes[count].split("-")[0];
			String value = groupTypes[count].split("-")[1];
			groupMap.put(key, value);
		}
		mv.addObject("groupTypesMap", groupMap);
		/** changes to display Grouping Types - Ends **/
		LOGGER.info("GroupingControlle:splitRenderHandler:Exit");
		return mv;
	}

	/** Create URL to render page for SPLIT SKU group
	 * 
	 * @param renderRequest
	 * @param renderResponse
	 * @return */
	@RenderMapping(params = "groupingTypeSplitSKU=splitSKU")
	public ModelAndView splitSKURenderHandler(RenderRequest renderRequest, RenderResponse renderResponse) {
		// do some processing here
		LOGGER.info("GroupingControlle:splitSKURenderHandler:Enter");
		ModelAndView mv = new ModelAndView(GroupingConstants.GROUPING_CREATE_SPLIT_SKU_GROUP);
		/** changes to display lan id - changed by Ramkumar - starts **/
		String sessionDataKey = (String) renderRequest.getPortletSession().getAttribute("sessionDataKey");
		if (null != sessionDataKey) {
			String userID = sessionDataKey.split(GroupingConstants.USER_DATA + renderRequest.getPortletSession().getId())[1];
			LOGGER.info("display user ID" + userID);
			mv.addObject(GroupingConstants.LAN_ID, userID);
		}
		/** changes to display lan id - changed by Ramkumar - ends **/

		/** changes to display Grouping Types - Starts **/
		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);
		String groups = prop.getProperty(GroupingConstants.GROUP_TYPES);
		String groupTypes[] = groups.split(",");
		Map<String, String> groupMap = new TreeMap<String, String>();
		for (int count = 0; count < groupTypes.length; count++) {
			String key = groupTypes[count].split("-")[0];
			String value = groupTypes[count].split("-")[1];
			groupMap.put(key, value);
		}
		mv.addObject("groupTypesMap", groupMap);
		/** changes to display Grouping Types - Ends **/
		LOGGER.info("GroupingControlle:splitSKURenderHandler:Exit");
		return mv;
	}

	/** Default Render method of Grouping Portlet when page loads */
	@RenderMapping
	public ModelAndView handleRenderRequest(RenderRequest request, RenderResponse response) throws Exception {

		LOGGER.info("GroupingControlle:handleRenderRequest:Enter");

		String createGroupType = request.getParameter("groupTypeSelector");
		LOGGER.info("createGroupType----------------->" + createGroupType);
		ModelAndView modelAndView = null;
		modelAndView = new ModelAndView(GroupingConstants.GROUPING_PAGE); // groupingPage.jsp
		/** changes to display lan id - changed by Ramkumar - starts **/
		String sessionDataKey = (String) request.getPortletSession().getAttribute("sessionDataKey");
		if (null != sessionDataKey) {
			String userID = sessionDataKey.split(GroupingConstants.USER_DATA + request.getPortletSession().getId())[1];
			LOGGER.info("display user ID" + userID);
			modelAndView.addObject(GroupingConstants.LAN_ID, userID);
		}
		/** changes to display lan id - changed by Ramkumar - ends **/
		
		/** changes to display Grouping Types - starts **/
		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);
		String groups = prop.getProperty(GroupingConstants.GROUP_TYPES);
		String groupTypes[] = groups.split(",");
		Map<String, String> groupMap = new TreeMap<String, String>();
		for (int count = 0; count < groupTypes.length; count++) {
			String key = groupTypes[count].split("-")[0];
			String value = groupTypes[count].split("-")[1];
			groupMap.put(key, value);
		}
		modelAndView.addObject("groupTypesMap", groupMap);
		/** changes to display Grouping Types - Ends **/

		LOGGER.info("GroupingControlle:handleRenderRequest:Exit");
		return modelAndView;
	}

	/** This method will handle the Event request and to fetch the user details
	 * from the login portlet */
	@EventMapping
	public void handleEventRequest(EventRequest request, EventResponse response) throws Exception {
		LOGGER.info("GroupingController:handleEventRequest:Enter ");
		Event event = request.getEvent();
		String loggedInUser = "";

		if (event.getName() != null) {
			LOGGER.info("GroupingController:handlingPagination:Enter 1 " + event.getName());
			if (event.getName().equals(GroupingConstants.USER_DATA_OBJ)) {
				LOGGER.info("GroupingController:handlingPagination:Enter 2 ");
				UserData custuser = (UserData) event.getValue();

				if (null != custuser) {
					Common_BelkUser belkUser = (Common_BelkUser) custuser.getBelkUser();
					LOGGER.info("GroupingController:handlingPagination:Enter 2 ");
					if (custuser.getVpUser() != null) {
						if (null != custuser.getVpUser().getUserEmailAddress()) {
							LOGGER.info("This is from Event Email Id********************" + custuser.getVpUser().getUserEmailAddress());
							loggedInUser = custuser.getVpUser().getUserEmailAddress();
						} else if (null != custuser.getRoleName()) {
							LOGGER.info("This is from Event Role name********************" + custuser.getRoleName());
						} else if (null != custuser.getAccessRight()) {
							LOGGER.info("This is from Access********************" + custuser.getAccessRight());
						}

					}
					if (null != belkUser) {
						LOGGER.info("belkUser.getLanId() 1111**************");
						if (null != belkUser.getLanId()) {
							LOGGER.info("belkUser.getLanId() ******************" + belkUser.getLanId());
							loggedInUser = belkUser.getLanId();
						}
					}

					String sessionDataKey = (GroupingConstants.USER_DATA + request.getPortletSession().getId() + loggedInUser);
					String formSessionKey = request.getPortletSession().getId() + loggedInUser;
					//String listSessionKey = "LIST" + request.getPortletSession().getId() + loggedInUser;

					LOGGER.info(" loggedInUser **************" + formSessionKey);

					request.getPortletSession().setAttribute("formSessionKey", formSessionKey);
					request.getPortletSession().setAttribute("sessionDataKey", sessionDataKey);
					request.getPortletSession().setAttribute(sessionDataKey, custuser);
				}
			}

		}
	}

	/** This method will take care of handleResourceRequest, handles all Ajax
	 * calls. Used to handle Split Color and Split SKU search request
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception */
	@ResourceMapping("splitAttributeSearch")
	public ModelAndView handleSplitAttrSearchRequest(ResourceRequest request, ResourceResponse response) throws Exception {
		LOGGER.info("Entering handleSplitAttrSearchRequest-->");
		String vendorStyleNo = "";
		String styleOrin = "";
		String updatedBy = "";
		String message = "";

		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);

		String formSessionKey = (String) request.getPortletSession().getAttribute("formSessionKey");
		CreateGroupForm createGroupForm = (CreateGroupForm) request.getPortletSession().getAttribute(formSessionKey);
		String sessionDataKey = (String) request.getPortletSession().getAttribute("sessionDataKey");
		UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(" handleSplitAttrSearchRequest formSessionKey ------------> " + formSessionKey);
			LOGGER.debug(" handleSplitAttrSearchRequest resourceForm --------------> " + createGroupForm);
			LOGGER.debug(" handleSplitAttrSearchRequest sessionDataKey ------------> " + sessionDataKey);
		}
		LOGGER.info(" handleSplitAttrSearchRequest custuser -----------------------> " + custuser);
		ModelAndView view = new ModelAndView("groupingPage");

		if (custuser != null) {
			LOGGER.info("userData---->in activate");
			if (custuser.isInternal()) {
				updatedBy = custuser.getBelkUser().getLanId();
				LOGGER.info("Activate Internal user--->" + updatedBy);
			}
		}

		/** Fetch Color details for Split group **/
		String strFromPage = request.getParameter("fromPage");
		LOGGER.info("in fetch split color attribute strFromPage--->" + strFromPage);
		if (strFromPage.equals("SearchColor")) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("in fetch split color attribute-->");
			}
			vendorStyleNo = request.getParameter(GroupingConstants.VENDOR_STYLE_NO);
			styleOrin = request.getParameter(GroupingConstants.STYLE_ORIN_NO);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search Attribute: -- vendorStyleNo-->" + vendorStyleNo + "  styleOrin-->" + styleOrin);
			}
			List<GroupAttributeForm> getSplitColorDetailsList = groupingService.getSplitColorDetails(vendorStyleNo, styleOrin);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of getSplitColorDetailsList-->" + getSplitColorDetailsList.size());
			}

			/** Validate Split Attribute List **/
			List<GroupAttributeForm> updatedSplitColorDetailsList = groupingService.validateSCGAttributeDetails(getSplitColorDetailsList);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of updatedSplitColorDetailsList-->" + updatedSplitColorDetailsList.size());
			}
			if (getSplitColorDetailsList.size() == 0) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_NO_DATA); // "No record found!"
			} else if (updatedSplitColorDetailsList.size() == 0) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_INVALID_DATA);
			}
			/** set in session to call add attribute webservice after creating
			 * group **/
			request.getPortletSession().setAttribute(GroupingConstants.SELECTED_ATTRIBUTE_LIST, updatedSplitColorDetailsList);

			/** Code to generate response to display Search result in JSP **/
			String totalRecords = String.valueOf(updatedSplitColorDetailsList.size());
			String defaultSortCol = GroupingConstants.STYLE_ORIN_NO;
			String defaultSortOrder = GroupingConstants.SORT_ASC;
			JSONObject jsonObj = getSplitGrpJsonResponse(message, totalRecords, defaultSortCol, defaultSortOrder,
					updatedSplitColorDetailsList);
			LOGGER.info("getSCGJsonResponse-->" + jsonObj);
			try {
				response.getWriter().write(jsonObj.toString());
			} catch (IOException e) {
				LOGGER.error("GroupingControlle:handleCreateGroupForm handleSplitAttrSearchRequest:Exception------------>" + e.getMessage());
			}
			view = new ModelAndView(null);
		} else {
			/** Fetch SKU details for Split group **/
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("in fetch split SKU attribute-->");
			}
			vendorStyleNo = request.getParameter(GroupingConstants.VENDOR_STYLE_NO);
			styleOrin = request.getParameter(GroupingConstants.STYLE_ORIN_NO);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search Attribute: -- vendorStyleNo-->" + vendorStyleNo + "  styleOrin-->" + styleOrin);
			}
			List<GroupAttributeForm> getSplitSKUDetailsList = groupingService.getSplitSKUDetails(vendorStyleNo, styleOrin);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of getSplitSKUDetailsList-->" + getSplitSKUDetailsList.size());
			}

			/** Validate Split Attribute List **/
			List<GroupAttributeForm> updatedSplitSKUDetailsList = groupingService.validateSSGAttributeDetails(getSplitSKUDetailsList);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of updatedSplitSKUDetailsList-->" + updatedSplitSKUDetailsList.size());
			}
			if (getSplitSKUDetailsList.size() == 0) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_NO_DATA); // "No record found!"
			} else if (updatedSplitSKUDetailsList.size() == 0) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_INVALID_DATA);
			}
			/** set in session to call add attribute webservice after creating
			 * group **/
			request.getPortletSession().setAttribute(GroupingConstants.SELECTED_ATTRIBUTE_LIST, updatedSplitSKUDetailsList);

			/** Code to generate response to display Search result in JSP **/
			String totalRecords = String.valueOf(updatedSplitSKUDetailsList.size());
			String defaultSortCol = GroupingConstants.STYLE_ORIN_NO;
			String defaultSortOrder = GroupingConstants.SORT_ASC;
			JSONObject jsonObj = getSplitGrpJsonResponse(message, totalRecords, defaultSortCol, defaultSortOrder,
					updatedSplitSKUDetailsList);
			LOGGER.info("getSCGJsonResponse-->" + jsonObj);

			try {
				response.getWriter().write(jsonObj.toString());
			} catch (IOException e) {
				LOGGER.error("GroupingControlle:handleCreateGroupForm handleSplitAttrSearchRequest:Exception------------>" + e.getMessage());
			}
			view = new ModelAndView(null);
		}

		LOGGER.info("Exit handleSplitAttrSearchRequest-->");
		return view;
	}

	/** Method to create JSON object to display search result in jsp for SCG and
	 * SSG.
	 * 
	 * @param message
	 *            String
	 * @param totalRecords
	 *            String
	 * @param defaultSortCol
	 *            String
	 * @param defaultSortOrder
	 *            String
	 * @param selectedSplitAttributeList
	 *            List<GroupAttributeForm>
	 * 
	 * @return JSONObject
	 * @author Cognizant */
	public final JSONObject getSplitGrpJsonResponse(final String message, final String totalRecords, final String defaultSortCol,
			final String defaultSortOrder, final List<GroupAttributeForm> selectedSplitAttributeList) {
		LOGGER.info("Enter getSplitGrpJsonResponse-->");
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObjComponent = null;
		GroupAttributeForm groupAttributeForm = null;
		JSONArray jsonArray = new JSONArray();
		String vendorStyleNoSearch = "";
		String styleOrinNoSearch = "";

		try {
			for (int i = 0; i < selectedSplitAttributeList.size(); i++) {
				jsonObjComponent = new JSONObject();
				groupAttributeForm = selectedSplitAttributeList.get(i);
				styleOrinNoSearch = GroupingUtil.checkNull(groupAttributeForm.getOrinNumber());
				vendorStyleNoSearch = GroupingUtil.checkNull(groupAttributeForm.getStyleNumber());
				jsonObjComponent.put(GroupingConstants.STYLE_ORIN_NO, styleOrinNoSearch);
				jsonObjComponent.put(GroupingConstants.VENDOR_STYLE_NO, vendorStyleNoSearch);
				jsonObjComponent.put(GroupingConstants.PRODUCT_NAME, groupAttributeForm.getProdName());
				jsonObjComponent.put(GroupingConstants.COLOR_CODE, groupAttributeForm.getColorCode());
				jsonObjComponent.put(GroupingConstants.COLOR_NAME, groupAttributeForm.getColorName());
				String defaultColor = (null == groupAttributeForm.getIsDefault() ? "" : groupAttributeForm.getIsDefault());
				/*if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("groupAttributeForm.getIsDefault()-->" + defaultColor);
				}*/
				jsonObjComponent.put(GroupingConstants.COMPONENT_DEFAULT_COLOR, defaultColor);
				String isAlreadyInGroup = groupAttributeForm.getIsAlreadyInGroup();
				isAlreadyInGroup = (null == isAlreadyInGroup ? "No" : ("N").equalsIgnoreCase(isAlreadyInGroup.trim()) ? "No" : ("Y")
						.equalsIgnoreCase(isAlreadyInGroup.trim()) ? "Yes" : "No");
				/* if (LOGGER.isDebugEnabled()) {
				 * LOGGER.debug("groupAttributeForm.isAlreadyInGroup()-->" +
				 * isAlreadyInGroup); } */
				jsonObjComponent.put(GroupingConstants.ALREADY_IN_GROUP, isAlreadyInGroup);
				jsonObjComponent.put(GroupingConstants.COMPONENT_SIZE, groupAttributeForm.getSize()); // Only
																										// for
																										// Split
																										// SKU

				jsonArray.put(jsonObjComponent);
			}

			jsonObj.put(GroupingConstants.MESSAGE, message);
			jsonObj.put(GroupingConstants.SPLIT_GROUP_TOTAL_RECORDS, totalRecords);
			jsonObj.put(GroupingConstants.SPLIT_GROUP_DEFAULT_SORT_COL, defaultSortCol);
			jsonObj.put(GroupingConstants.SPLIT_GROUP_DEFAULT_SORT_ORDER, defaultSortOrder);
			jsonObj.put(GroupingConstants.STYLE_ORIN_NO_SEARCH, styleOrinNoSearch);
			jsonObj.put(GroupingConstants.VENDOR_STYLE_NO_SEARCH, vendorStyleNoSearch);
			jsonObj.put(GroupingConstants.COMPONENT_LIST, jsonArray);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("JSON getSplitGrpJsonResponse-->" + jsonObj);
			}
		} catch (JSONException e) {
			LOGGER.error("Exeception in parsing the jsonObj");
		}
		LOGGER.info("Exit getSplitGrpJsonResponse-->");
		return jsonObj;
	}

	/** This method is used to create group.
	 * 
	 * @param request
	 *            ResourceRequest
	 * @param response
	 *            ResourceResponse
	 * @return ModelAndView
	 * @throws PEPPersistencyException
	 *             PEPPersistencyException
	 * @throws PEPServiceException
	 *             PEPServiceException */
	@ResourceMapping("submitCreateGroupForm")
	public final ModelAndView handleCreateGroupForm(final ResourceRequest request, final ResourceResponse response)
			throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("GroupingControlle:handleCreateGroupForm ResourceRequest:Enter------------>");

		String formSessionKey = (String) request.getPortletSession().getAttribute("formSessionKey");
		LOGGER.info("handleRenderRequest formSessionKey  ----------------------------->" + formSessionKey);
		String sessionDataKey = (String) request.getPortletSession().getAttribute("sessionDataKey");
		LOGGER.info(" handleActionRequest sessionDataKey -----------------------------> " + sessionDataKey);
		UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);
		LOGGER.info(" handleActionRequest custuser -----------------------------------> " + custuser);
		String pepUserId = custuser.getBelkUser().getLanId();
		LOGGER.info("This is from Reneder Internal User--------------------->" + pepUserId);

		String[] selectedItemsArr = null;
		String groupType = request.getParameter(GroupingConstants.GROUP_TYPE);
		String groupName = request.getParameter(GroupingConstants.GROUP_NAME);
		String groupDesc = request.getParameter(GroupingConstants.GROUP_DESC);
		String startDate = request.getParameter(GroupingConstants.START_DATE);
		String endDate = request.getParameter(GroupingConstants.END_DATE);
		String defaultSelectedAttr = request.getParameter(GroupingConstants.COMPONENT_DEFAULT_COLOR);
		String carsGroupingType = request.getParameter(GroupingConstants.CARS_GROUPING_TYPE);
		carsGroupingType = (null == carsGroupingType ? "" : carsGroupingType.trim());
		defaultSelectedAttr = (null == defaultSelectedAttr ? "" : defaultSelectedAttr.trim());
		String selectedItems = request.getParameter(GroupingConstants.COMPONENT_SELECTED_ITEMS);
		if (null != selectedItems) {
			selectedItemsArr = selectedItems.split(",");
		}

		startDate = (null == startDate ? "" : startDate.trim());
		endDate = (null == endDate ? "" : endDate.trim());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("groupType----------------->" + groupType);
			LOGGER.debug("groupName----------------->" + groupName);
			LOGGER.debug("groupDesc----------------->" + groupDesc);
			LOGGER.debug("startDate----------------->" + startDate);
			LOGGER.debug("endDate----------------->" + endDate);
			LOGGER.debug("carsGroupingType----------------->" + carsGroupingType);
			LOGGER.debug("defaultSelectedAttr----------------->" + defaultSelectedAttr);
			LOGGER.debug("selectedItems----------------->" + selectedItems);
		}

		/** If request came with create group request **/
		CreateGroupForm createGroupForm = new CreateGroupForm();
		List<GroupAttributeForm> selectedSplitAttributeList = new ArrayList<GroupAttributeForm>();

		if (null != groupType && (GroupingConstants.GROUP_TYPE_SPLIT_COLOR).equals(groupType) && null != selectedItems
				&& null != selectedItemsArr) {
			// TODO
			/** Add Selected Attribute to List (selectedSplitAttributeList) for
			 * Split Color and Group **/
			@SuppressWarnings("unchecked")
			List<GroupAttributeForm> updatedSplitColorDetailsList = (List<GroupAttributeForm>) request.getPortletSession().getAttribute(
					GroupingConstants.SELECTED_ATTRIBUTE_LIST);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("All Color Attribute List Size()-->" + updatedSplitColorDetailsList.size());
			}
			selectedSplitAttributeList = groupingService.getSelectedColorAttributeList(updatedSplitColorDetailsList, selectedItemsArr,
					defaultSelectedAttr);
			// createGroupForm.setGroupAttributeFormList(selectedSplitAttributeList);
		}
		if (null != groupType && (GroupingConstants.GROUP_TYPE_SPLIT_SKU).equals(groupType) && null != selectedItems
				&& null != selectedItemsArr) {
			@SuppressWarnings("unchecked")
			List<GroupAttributeForm> updatedSplitSkuDetailsList = (List<GroupAttributeForm>) request.getPortletSession().getAttribute(
					GroupingConstants.SELECTED_ATTRIBUTE_LIST);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("All SKU Attribute List Size()-->" + updatedSplitSkuDetailsList.size());
			}
			selectedSplitAttributeList = groupingService.getSelectedSKUAttributeList(updatedSplitSkuDetailsList, selectedItemsArr,
					defaultSelectedAttr);
			// createGroupForm.setGroupAttributeFormList(selectedSplitAttributeList);
		}

		createGroupForm.setGroupType(groupType);
		createGroupForm.setGroupName(groupName);
		createGroupForm.setGroupDesc(groupDesc);
		createGroupForm.setGroupLaunchDate(startDate);
		createGroupForm.setEndDate(endDate);
		createGroupForm.setCarsGroupType(carsGroupingType);
		createGroupForm.setGroupAttributeFormList(selectedSplitAttributeList);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Before calling createGroup()-->");
		}
		CreateGroupForm createGroupFormRes = createGroup(createGroupForm, pepUserId);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("After calling createGroup()-->getGroupCreationStatus: " + createGroupFormRes.getGroupCreationStatus());
			LOGGER.debug("After calling createGroup()-->getGroupCretionMsg: " + createGroupFormRes.getGroupCretionMsg());
			LOGGER.debug("After calling createGroup()-->GroupID: " + createGroupFormRes.getGroupId());
			LOGGER.debug("After calling createGroup()-->GroupType: " + createGroupFormRes.getGroupType());
			LOGGER.debug("After calling createGroup()-->GroupDesc: " + createGroupFormRes.getGroupDesc());
			LOGGER.debug("After calling createGroup()-->getCarsGroupType: " + createGroupFormRes.getCarsGroupType());
		}

		request.getPortletSession().setAttribute(GroupingConstants.GROUP_DETAILS_FORM, createGroupFormRes);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put(GroupingConstants.GROUP_ID, createGroupFormRes.getGroupId());
		jsonObj.put(GroupingConstants.GROUP_NAME, createGroupFormRes.getGroupName());
		jsonObj.put(GroupingConstants.GROUP_TYPE, createGroupFormRes.getGroupType());
		jsonObj.put(GroupingConstants.GROUP_DESC, createGroupFormRes.getGroupDesc());
		jsonObj.put(GroupingConstants.START_DATE, createGroupFormRes.getGroupLaunchDate());
		jsonObj.put(GroupingConstants.START_DATE, createGroupFormRes.getEndDate());
		jsonObj.put(GroupingConstants.GROUP_CREATION_MSG, createGroupFormRes.getGroupCretionMsg());
		jsonObj.put(GroupingConstants.GROUP_CREATION_STATUS_CODE, createGroupFormRes.getGroupCreationStatus());
		jsonObj.put(GroupingConstants.CARS_GROUPING_TYPE, createGroupFormRes.getCarsGroupType());
		// createGroupForm.setGroupAttributeFormList(createGroupDTO.getGroupAttributeDTOList());

		// request.getPortletSession().setAttribute(formSessionKey, renderForm);
		// request.setAttribute("createGroupFormRes", createGroupFormRes);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("jsonObj-->" + jsonObj);
		}

		ModelAndView modelAndView = null;
		try {
			response.getWriter().write(jsonObj.toString());
		} catch (IOException e) {
			LOGGER.error("GroupingControlle:handleCreateGroupForm ResourceRequest:Exception------------>" + e.getMessage());
		}

		LOGGER.info("GroupingControlle:handleCreateGroupForm ResourceRequest:Exit------------>");
		return modelAndView;
	}

	/** Create URL to render page for Create Group successfully.
	 * 
	 * @param renderRequest
	 *            RenderRequest
	 * @param renderResponse
	 *            RenderResponse
	 * @return ModelAndView
	 * @throws ParseException
	 *             ParseException */
	@RenderMapping(params = "createGroupSuccessRender=CreateGrpSuccess")
	public final ModelAndView createGroupSuccessRender(final RenderRequest renderRequest, final RenderResponse renderResponse)
			throws ParseException {
		// do some processing here
		LOGGER.info("GroupingControlle:createGroupSuccessRender:Enter");
		String startDateString = "";
		String endDateString = "";
		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);

		CreateGroupForm objCreateGroupForm = (CreateGroupForm) renderRequest.getPortletSession().getAttribute(
				GroupingConstants.GROUP_DETAILS_FORM);
		String groupTypeCode = (null == objCreateGroupForm.getGroupType() ? "" : objCreateGroupForm.getGroupType().trim());
		String groupTypeDesc = prop.getProperty(groupTypeCode);
		String groupStatusCode = (null == objCreateGroupForm.getGroupStatus() ? "" : objCreateGroupForm.getGroupStatus().trim());
		String groupStatusDesc = prop.getProperty(GroupingConstants.GROUP_STATUS_EXT + groupStatusCode);
		String startDate = objCreateGroupForm.getGroupLaunchDate();
		if (null != startDate && !("").equals(startDate)) {
			startDateString = DateUtil.stringToStringMMddyyyy(startDate);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("createGroupSuccessRender.startDateString-->" + startDateString);
		}
		String endDate = objCreateGroupForm.getEndDate();
		if (null != endDate && !("").equals(endDate)) {
			endDateString = DateUtil.stringToStringMMddyyyy(endDate);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("createGroupSuccessRender.endDateString-->" + endDateString);
		}

		objCreateGroupForm.setGroupTypeDesc(groupTypeDesc);
		objCreateGroupForm.setGroupStatusDesc(groupStatusDesc);
		objCreateGroupForm.setGroupLaunchDate(startDateString);
		objCreateGroupForm.setEndDate(endDateString);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("createGroupSuccessRender.objCreateGroupForm-->" + objCreateGroupForm);
			LOGGER.debug("createGroupSuccessRender.objCreateGroupForm.getGroupId()-->" + objCreateGroupForm.getGroupId());
			LOGGER.debug(groupTypeCode + " createGroupSuccessRender.objCreateGroupForm.groupTypeDesc-->" + groupTypeDesc);
			LOGGER.debug(groupStatusCode + " createGroupSuccessRender.objCreateGroupForm.groupStatus-->" + groupStatusDesc);
		}
		ModelAndView mv = new ModelAndView(GroupingConstants.GROUPING_ADD_COMPONENT);
		mv.addObject(GroupingConstants.GROUP_DETAILS_FORM, objCreateGroupForm);
		/** changes to display lan id - changed by Ramkumar - starts **/
		String sessionDataKey = (String) renderRequest.getPortletSession().getAttribute("sessionDataKey");
		if (null != sessionDataKey) {
			String userID = sessionDataKey.split(GroupingConstants.USER_DATA + renderRequest.getPortletSession().getId())[1];
			LOGGER.info("display user ID" + userID);
			mv.addObject(GroupingConstants.LAN_ID, userID);
		}
		/** changes to display lan id - changed by Ramkumar - ends **/
		
		/** changes to display Grouping Types - starts **/
		//Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);
		String groups = prop.getProperty(GroupingConstants.GROUP_TYPES);
		String groupTypes[] = groups.split(",");
		Map<String, String> groupMap = new TreeMap<String, String>();
		for (int count = 0; count < groupTypes.length; count++) {
			String key = groupTypes[count].split("-")[0];
			String value = groupTypes[count].split("-")[1];
			groupMap.put(key, value);
		}
		mv.addObject("groupTypesMap", groupMap);
		/** changes to display Grouping Types - Ends **/
		
		LOGGER.info("GroupingControlle:createGroupSuccessRender:Exit");
		return mv;
	}

	/** This method is used to call Group Creation Service and fetch data from
	 * database.
	 * 
	 * @param createGroupForm
	 *            CreateGroupForm
	 * @param updatedBy
	 *            String
	 * @return CreateGroupForm
	 * @author AFUPYB3 */
	private final CreateGroupForm createGroup(final CreateGroupForm createGroupForm, String updatedBy) {
		LOGGER.info("Entering:: createGroup method controller");

		// String responseMsg = "";
		JSONObject jsonStyle = null;
		// JSONArray jsonArray = new JSONArray();
		CreateGroupForm createGroupFormRes = null;

		try {
			jsonStyle = populateCreateGroupJson(createGroupForm, updatedBy);
			LOGGER.info("Create Group Json Style-->" + jsonStyle);
			// jsonArray.put(jsonStyle);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("json Object createGroup Group Name--> " + jsonStyle.getString("groupName"));
			}
		} catch (Exception e) {
			LOGGER.error("inside catch for createGroup()-->controller");
		}
		try {
			createGroupFormRes = groupingService.saveGroupHeaderDetails(jsonStyle, updatedBy, createGroupForm.getGroupAttributeFormList());

			LOGGER.info("responseMsg_code Controller createGroup-->" + createGroupFormRes.getGroupCretionMsg());
		} catch (PEPFetchException eService) {
			LOGGER.info("Exception Block in Controller::32");
			eService.printStackTrace();
		} catch (Exception e) {
			LOGGER.error("Exception Block in Controller::21");
		}
		LOGGER.info("Exiting--> createGroup method controller");

		return createGroupFormRes;
	} // End Group Creation

	/** Method to pass JSON Array to call the Create Group service.
	 * 
	 * @param createGroupForm
	 *            CreateGroupForm
	 * @param updatedBy
	 *            String
	 * @return JSONObject
	 * @author Cognizant */
	public final JSONObject populateCreateGroupJson(final CreateGroupForm createGroupForm, final String updatedBy) {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put(GroupingConstants.GROUP_NAME, createGroupForm.getGroupName());
			jsonObj.put(GroupingConstants.GROUP_TYPE, createGroupForm.getGroupType());
			jsonObj.put(GroupingConstants.GROUP_DESC, createGroupForm.getGroupDesc());
			jsonObj.put(GroupingConstants.START_DATE, createGroupForm.getGroupLaunchDate());
			jsonObj.put(GroupingConstants.END_DATE, createGroupForm.getEndDate());
			jsonObj.put(GroupingConstants.CREATED_BY, updatedBy);
			jsonObj.put(GroupingConstants.CARS_GROUPING_TYPE, createGroupForm.getCarsGroupType());
		} catch (JSONException e) {
			LOGGER.error("Exeception in parsing the jsonObj");
		}
		return jsonObj;
	}

	/** Method to create JSON objects for Search Group List.
	 * 
	 * @param groupSearchForm
	 *            GroupSearchForm
	 * @return JSONObject
	 * 
	 *         Method added For PIM Phase 2 - Search Group Date: 05/20/2016
	 *         Added By: Cognizant */
	public final JSONObject searchGroupJsonObject(final GroupSearchForm groupSearchForm) {

		LOGGER.info("Entering searchGroupJsonObject() in GroupingController class.");
		JSONObject jsonObjSearch = new JSONObject();
		jsonObjSearch.put(GroupingConstants.GROUP_ID_SEARCH, groupSearchForm.getGroupId());
		jsonObjSearch.put(GroupingConstants.GROUP_NAME_SEARCH, groupSearchForm.getGroupName());
		jsonObjSearch.put(GroupingConstants.DEPT_SEARCH, groupSearchForm.getDepts());
		jsonObjSearch.put(GroupingConstants.CLASS_SEARCH, groupSearchForm.getClasses());
		jsonObjSearch.put(GroupingConstants.VENDOR_SEARCH, groupSearchForm.getVendor());
		jsonObjSearch.put(GroupingConstants.SUPPLIER_SEARCH, groupSearchForm.getSupplierSiteId());
		jsonObjSearch.put(GroupingConstants.TOTAL_RECORD_COUNT, groupSearchForm.getTotalRecordCount());
		jsonObjSearch.put(GroupingConstants.RECORDS_PER_PAGE, groupSearchForm.getRecordsPerPage());
		jsonObjSearch.put(GroupingConstants.PAGE_NUMBER, groupSearchForm.getPageNumber());
		jsonObjSearch.put(GroupingConstants.SORTED_COLUMN, groupSearchForm.getSortColumn());
		jsonObjSearch.put(GroupingConstants.ASC_DESC_ORDER, groupSearchForm.getAscDescOrder());
		List<GroupForm> groupListMain = groupSearchForm.getGroupList();
		JSONArray mainArray = new JSONArray();
		for (Iterator<GroupForm> iterator = groupListMain.iterator(); iterator.hasNext();) {
			GroupForm groupForm = (GroupForm) iterator.next();
			JSONObject jsonObjMain = new JSONObject();
			jsonObjMain.put(GroupingConstants.GROUP_ID, groupForm.getGroupId());
			jsonObjMain.put(GroupingConstants.GROUP_NAME, groupForm.getGroupName());
			jsonObjMain.put(GroupingConstants.GROUP_TYPE, groupForm.getGroupType());
			jsonObjMain.put(GroupingConstants.GROUP_TYPE_CODE, groupForm.getGroupTypeCode());
			jsonObjMain.put(GroupingConstants.GROUP_CONTENT_STATUS, groupForm.getGroupContentStatus());
			jsonObjMain.put(GroupingConstants.GROUP_IMAGE_STATUS, groupForm.getGroupImageStatus());
			jsonObjMain.put(GroupingConstants.START_DATE, groupForm.getStartDate());
			jsonObjMain.put(GroupingConstants.END_DATE, groupForm.getEndDate());
			jsonObjMain.put(GroupingConstants.CHILD_GROUP, groupForm.getChildGroup());
			List<GroupForm> groupListChild = groupForm.getChildList();
			JSONArray childArray = new JSONArray();
			if (groupListChild != null && groupListChild.size() > 0) {
				for (Iterator<GroupForm> iterator2 = groupListChild.iterator(); iterator2.hasNext();) {
					GroupForm groupFormChild = (GroupForm) iterator2.next();
					JSONObject jsonObj = new JSONObject();
					jsonObj.put(GroupingConstants.GROUP_ID, groupFormChild.getGroupId());
					jsonObj.put(GroupingConstants.GROUP_NAME, groupFormChild.getGroupName());
					jsonObj.put(GroupingConstants.GROUP_TYPE, groupFormChild.getGroupType());
					jsonObj.put(GroupingConstants.GROUP_TYPE_CODE, groupFormChild.getGroupTypeCode());
					jsonObj.put(GroupingConstants.GROUP_CONTENT_STATUS, groupFormChild.getGroupContentStatus());
					jsonObj.put(GroupingConstants.GROUP_IMAGE_STATUS, groupFormChild.getGroupImageStatus());
					jsonObj.put(GroupingConstants.START_DATE, groupFormChild.getStartDate());
					jsonObj.put(GroupingConstants.END_DATE, groupFormChild.getEndDate());
					jsonObj.put(GroupingConstants.CHILD_GROUP, groupFormChild.getChildGroup());
					childArray.put(jsonObj);
				}
			}
			jsonObjMain.put(GroupingConstants.CHILD_LIST, childArray);
			mainArray.put(jsonObjMain);
		}
		jsonObjSearch.put(GroupingConstants.MAIN_LIST, mainArray);
		LOGGER.info("Exiting searchGroupJsonObject() in GroupingController class.");
		return jsonObjSearch;
	}

	/** Method to handle all resource requests.
	 * 
	 * @param request
	 *            ResourceRequest
	 * @param response
	 *            ResourceResponse
	 * @return ModelAndView
	 * @throws Exception
	 *             Exception */
	@ResourceMapping
	public final ModelAndView handleResourceRequest(final ResourceRequest request, final ResourceResponse response) throws Exception {

		LOGGER.info("Entering handleResourceRequest() in GroupingController class.");
		String action = GroupingUtil.checkNull(request.getParameter("resourceType"));
		ModelAndView modelAndView = null;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Action is -- " + action);
		}
		if (action.equals("searchGroup")) {
			GroupSearchForm searchForm = new GroupSearchForm();
			searchForm.setVendor(GroupingUtil.checkNull(request.getParameter("vendor")));
			searchForm.setOrinNumber(GroupingUtil.checkNull(request.getParameter("orinNumber")));
			searchForm.setGroupId(GroupingUtil.checkNull(request.getParameter("groupId")));
			searchForm.setGroupName(GroupingUtil.checkNull(request.getParameter("groupName")));
			searchForm.setDepts(GroupingUtil.checkNull(request.getParameter("departments")));
			searchForm.setClasses(GroupingUtil.checkNull(request.getParameter("classes")));
			searchForm.setSupplierSiteId(GroupingUtil.checkNull(request.getParameter("supplierId")));
			if (request.getParameter("totalRecordsCount") != null) {
				searchForm.setTotalRecordCount(Integer.parseInt(request.getParameter("totalRecordsCount")));
			} else {
				searchForm.setTotalRecordCount(0);
			}
			if (request.getParameter("recordsPerPage") != null) {
				searchForm.setRecordsPerPage(Integer.parseInt(request.getParameter("recordsPerPage")));
			} else {
				searchForm.setRecordsPerPage(0);
			}
			if (request.getParameter("pageNumber") != null) {
				searchForm.setPageNumber(Integer.parseInt(request.getParameter("pageNumber")));
			} else {
				searchForm.setPageNumber(1);
			}
			searchForm.setSortColumn(GroupingUtil.checkNull(request.getParameter("sortedColumn")));
			searchForm.setAscDescOrder(GroupingUtil.checkNull(request.getParameter("ascDescOrder")));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search values from screen -- \nGROUP ID: " + searchForm.getGroupId() + "\nGROUP NAME: "
						+ searchForm.getGroupName() + "\nORIN#: " + searchForm.getOrinNumber() + "\nVENDOR: " + searchForm.getVendor()
						+ "\nSUPPILER ID: " + searchForm.getSupplierSiteId() + "\nDEPARTMENTS: " + searchForm.getDepts() + "\nCLASSES: "
						+ searchForm.getClasses() + "\nPAGE NUMBER: " + searchForm.getPageNumber() + "\nRECODRS PER PAGE: "
						+ searchForm.getRecordsPerPage() + "\nSORT COLUMN: " + searchForm.getSortColumn() + "\nASC/DESC: "
						+ searchForm.getAscDescOrder());
			}

			searchForm = groupingService.groupSearch(searchForm);
			if (searchForm.getGroupId() != null && !searchForm.getGroupId().trim().equals("")) {
				searchForm.setTotalRecordCount(groupingService.groupSearchCount(searchForm) + searchForm.getParentCount());
			} else {
				searchForm.setTotalRecordCount(groupingService.groupSearchCount(searchForm));
			}
			PortletSession portletSession = request.getPortletSession();
			portletSession.setAttribute("SEARCH_RESULT", searchForm);
			JSONObject jsonObject = searchGroupJsonObject(searchForm);

			try {
				response.getWriter().write(jsonObject.toString());
			} catch (IOException e) {
				LOGGER.error("GroupingController:Group Search ResourceRequest:Exception------------>" + e.getMessage());
			}
		}
		if (action.equals("searchDept")) {
			ArrayList<DepartmentDetails> deptList = groupingService.getDeptDetailsByDepNoFromADSE();
			JSONObject jsonObject = departmentListJsonObject(deptList);
			try {
				response.getWriter().write(jsonObject.toString());
			} catch (IOException e) {
				LOGGER.error("GroupingController:Dept Search ResourceRequest:Exception------------>" + e.getMessage());
			}
		}
		if (action.equals("searchClass")) {
			String depts = GroupingUtil.checkNull(request.getParameter("depts"));
			List<ClassDetails> classList = groupingService.getClassDetailsByDepNos(depts);
			JSONObject jsonObject = classListJsonObject(classList);
			try {
				response.getWriter().write(jsonObject.toString());
			} catch (IOException e) {
				LOGGER.error("GroupingController:Dept Search ResourceRequest:Exception------------>" + e.getMessage());
			}
		}
		if (action.equals("deleteGroup")) {
			LOGGER.info("handleDeleteGroup ResourceRequest:Enter------------>");
			String formSessionKey = (String) request.getPortletSession().getAttribute("formSessionKey");
			LOGGER.info("handleDeleteGroup formSessionKey  ----------------------------->" + formSessionKey);
			String sessionDataKey = (String) request.getPortletSession().getAttribute("sessionDataKey");
			LOGGER.info(" handleDeleteGroup sessionDataKey -----------------------------> " + sessionDataKey);
			UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);
			LOGGER.info(" handleDeleteGroup custuser -----------------------------------> " + custuser);
			String pepUserId = custuser.getBelkUser().getLanId();
			LOGGER.info("This is from Reneder Internal User--------------------->" + pepUserId);
			try {
				String groupId = request.getParameter(GroupingConstants.GROUP_ID);
				String groupType = request.getParameter(GroupingConstants.GROUP_TYPE);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("groupId----------------->" + groupId);
					LOGGER.debug("groupType----------------->" + groupType);
				}
				String responseMesage = "";
				if (null != groupId) {
					responseMesage = groupingService.deleteGroup(groupId, groupType, pepUserId);
				}
				boolean status = responseMesage.contains("success");
				JSONObject json = new JSONObject();
				json.put(GroupingConstants.DELETE_STATUS_MESSAGE,
						"#" + GroupingUtil.checkNull(groupId) + " " + GroupingUtil.checkNull(responseMesage));
				json.put(GroupingConstants.DELETE_STATUS, status);
				try {
					response.getWriter().write(json.toString());
				} catch (IOException e) {
					LOGGER.error("GroupingController:Group delete ResourceRequest:Exception------------>" + e.getMessage());
				}
			} catch (Exception e) {
				LOGGER.error("handleDeleteGroup ResourceRequest:Exception------------>" + e.getMessage());
			}
		}
		LOGGER.info("Exiting handleResourceRequest() in GroupingController class.");
		return modelAndView;
	}

	/** Method to create JSON objects for Dept List.
	 * 
	 * @param departmentList
	 *            ArrayList<DepartmentDetails>
	 * @return JSONObject
	 * 
	 *         Method added For PIM Phase 2 - Search Group Date: 05/26/2016
	 *         Added By: Cognizant */
	public final JSONObject departmentListJsonObject(final ArrayList<DepartmentDetails> departmentList) {

		LOGGER.info("Entering departmentListJsonObject() in GroupingController class.");
		JSONObject jsonObjDeptParent = new JSONObject();
		JSONArray deptListArray = new JSONArray();

		for (Iterator<DepartmentDetails> iterator = departmentList.iterator(); iterator.hasNext();) {
			DepartmentDetails deptObj = (DepartmentDetails) iterator.next();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put(GroupingConstants.DEPT_ID, deptObj.getId());
			jsonObj.put(GroupingConstants.DEPT_DESC, deptObj.getDesc());

			deptListArray.put(jsonObj);
		}
		jsonObjDeptParent.put(GroupingConstants.DEPT_LIST, deptListArray);
		LOGGER.info("Exiting departmentListJsonObject() in GroupingController class.");
		return jsonObjDeptParent;
	}

	/** Method to create JSON objects for Class List.
	 * 
	 * @param classList
	 *            ArrayList<ClassDetails>
	 * @return JSONObject
	 * 
	 *         Method added For PIM Phase 2 - Search Group Date: 05/26/2016
	 *         Added By: Cognizant */
	public final JSONObject classListJsonObject(final List<ClassDetails> classList) {

		LOGGER.info("Entering classListJsonObject() in GroupingController class.");
		JSONObject jsonObjClassParent = new JSONObject();
		JSONArray classListArray = new JSONArray();

		for (Iterator<ClassDetails> iterator = classList.iterator(); iterator.hasNext();) {
			ClassDetails classObj = (ClassDetails) iterator.next();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put(GroupingConstants.CLASS_ID, classObj.getId());
			jsonObj.put(GroupingConstants.CLASS_DESC, classObj.getDesc());

			classListArray.put(jsonObj);
		}
		jsonObjClassParent.put(GroupingConstants.CLASS_LIST, classListArray);
		LOGGER.info("Exiting classListJsonObject() in GroupingController class.");
		return jsonObjClassParent;
	}

	/** This method is used to create group.
	 * 
	 * @param request
	 *            ResourceRequest
	 * @param response
	 *            ResourceResponse
	 * @return ModelAndView */
	@ResourceMapping("deleteGroupResorceRequest")
	public final ModelAndView handleDeleteGroup(final ResourceRequest request, final ResourceResponse response) {
		LOGGER.info("handleDeleteGroup ResourceRequest:Enter------------>");
		String formSessionKey = (String) request.getPortletSession().getAttribute("formSessionKey");
		LOGGER.info("handleDeleteGroup formSessionKey  ----------------------------->" + formSessionKey);
		String sessionDataKey = (String) request.getPortletSession().getAttribute("sessionDataKey");
		LOGGER.info(" handleDeleteGroup sessionDataKey -----------------------------> " + sessionDataKey);
		UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);
		LOGGER.info(" handleDeleteGroup custuser -----------------------------------> " + custuser);
		String pepUserId = custuser.getBelkUser().getLanId();
		LOGGER.info("This is from Reneder Internal User--------------------->" + pepUserId);
		ModelAndView modelAndView = null;
		try {
			String groupId = request.getParameter(GroupingConstants.GROUP_ID);
			String groupType = request.getParameter(GroupingConstants.GROUP_TYPE);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("groupId----------------->" + groupId);
				LOGGER.debug("groupType----------------->" + groupType);
			}

			String responseMesage = "";
			if (null != groupId) {
				responseMesage = groupingService.deleteGroup(groupId, groupType, pepUserId);
			}
			JSONObject json = new JSONObject();
			json.put(GroupingConstants.DELETE_STATUS_MESSAGE, responseMesage);
			try {
				response.getWriter().write(json.toString());
			} catch (IOException e) {
				LOGGER.error("GroupingController:Group delete ResourceRequest:Exception------------>" + e.getMessage());
			}
		} catch (Exception e) {
			LOGGER.error("handleDeleteGroup ResourceRequest:Exception------------>" + e.getMessage());
		}

		LOGGER.info("GroupingControlle:handleDeleteGroup ResourceRequest:Exit------------>");
		return modelAndView;
	}

	/** This method is used to get the existing group details.
	 * 
	 * @param request
	 *            ActionRequest
	 * @param response
	 *            ActionResponse
	 * @return void
	 * @throws PEPFetchException
	 *             PEPFetchException
	 * @throws Exception
	 *             Exception */
	@ActionMapping(params = "getGroupDetails=getGroupDetails")
	public final void getExistingGrpDetails(final ActionRequest request, final ActionResponse response) throws PEPFetchException, Exception {
		// do some processing here
		LOGGER.info("GroupingControlle:getExistingGrpDetails:Enter-->");
		String groupType = GroupingUtil.checkNull(request.getParameter(GroupingConstants.GROUP_TYPE));
		String groupId = GroupingUtil.checkNull(request.getParameter(GroupingConstants.GROUP_ID));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getExistingGrpDetails.groupType---------->" + groupType);
			LOGGER.debug("getExistingGrpDetails.groupId---------->" + groupId);
		}

		request.getPortletSession().setAttribute(GroupingConstants.GROUP_TYPE, groupType);
		request.getPortletSession().setAttribute(GroupingConstants.GROUP_ID, groupId);

		response.setRenderParameter("existingGrpDetailsRender", "existingGrpDetailsRender");

		LOGGER.info("GroupingControlle:getExistingGrpDetails:Exit");
	}

	/** This method is used to get the existing group details and Render that
	 * page to Component details Page.
	 * 
	 * @param request
	 *            RenderRequest
	 * @param response
	 *            RenderResponse
	 * @return ModelAndView
	 * @throws Exception
	 *             Exception */
	@RenderMapping(params = "existingGrpDetailsRender=existingGrpDetailsRender")
	public final ModelAndView existingGrpDetailsRender(final RenderRequest request, final RenderResponse response) throws Exception {

		LOGGER.info("GroupingControlle.existingGrpDetailsRender:Enter");
		ModelAndView modelAndView = null;
		modelAndView = new ModelAndView(GroupingConstants.GROUPING_ADD_COMPONENT);

		String groupType = (String) request.getPortletSession().getAttribute(GroupingConstants.GROUP_TYPE);
		String groupId = (String) request.getPortletSession().getAttribute(GroupingConstants.GROUP_ID);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("existingGrpDetailsRender.groupType---------->" + groupType);
			LOGGER.debug("existingGrpDetailsRender.groupId---------->" + groupId);
		}

		CreateGroupForm objCreateGroupForm = groupingService.getExistingGrpDetails(groupId);

		String startDateString = "";
		String endDateString = "";
		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);
		String groupTypeCode = (null == objCreateGroupForm.getGroupType() ? "" : objCreateGroupForm.getGroupType().trim());
		String groupTypeDesc = prop.getProperty(groupTypeCode);
		String groupStatusCode = (null == objCreateGroupForm.getGroupStatus() ? "" : objCreateGroupForm.getGroupStatus().trim());
		String groupStatusDesc = prop.getProperty(GroupingConstants.GROUP_STATUS_EXT + groupStatusCode);
		String startDate = objCreateGroupForm.getGroupLaunchDate();
		if (null != startDate && !("").equals(startDate)) {
			startDateString = DateUtil.stringToStringMMddyyyy(startDate);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getExistingGrpDetails.startDateString-->" + startDateString);
		}
		String endDate = objCreateGroupForm.getEndDate();
		if (null != endDate && !("").equals(endDate)) {
			endDateString = DateUtil.stringToStringMMddyyyy(endDate);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getExistingGrpDetails.endDateString-->" + endDateString);
		}

		objCreateGroupForm.setGroupTypeDesc(groupTypeDesc);
		objCreateGroupForm.setGroupStatusDesc(groupStatusDesc);
		objCreateGroupForm.setGroupLaunchDate(startDateString);
		objCreateGroupForm.setEndDate(endDateString);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getExistingGrpDetails.objCreateGroupForm-->" + objCreateGroupForm);
			LOGGER.debug("getExistingGrpDetails.objCreateGroupForm.getGroupId()-->" + objCreateGroupForm.getGroupId());
			LOGGER.debug(groupTypeCode + " getExistingGrpDetails.objCreateGroupForm.groupTypeDesc-->" + groupTypeDesc);
			LOGGER.debug(groupStatusCode + " getExistingGrpDetails.objCreateGroupForm.groupStatus-->" + groupStatusDesc);
		}
		modelAndView.addObject(GroupingConstants.GROUP_DETAILS_FORM, objCreateGroupForm);

		/** changes to display lan id - changed by Ramkumar - starts **/
		String sessionDataKey = (String) request.getPortletSession().getAttribute("sessionDataKey");
		if (null != sessionDataKey) {
			String userID = sessionDataKey.split(GroupingConstants.USER_DATA + request.getPortletSession().getId())[1];
			LOGGER.info("display user ID" + userID);
			modelAndView.addObject(GroupingConstants.LAN_ID, userID);
		}
		/** changes to display lan id - changed by Ramkumar - ends **/

		/** changes to display Grouping Types - starts **/
		//Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);
		String groups = prop.getProperty(GroupingConstants.GROUP_TYPES);
		String groupTypes[] = groups.split(",");
		Map<String, String> groupMap = new TreeMap<String, String>();
		for (int count = 0; count < groupTypes.length; count++) {
			String key = groupTypes[count].split("-")[0];
			String value = groupTypes[count].split("-")[1];
			groupMap.put(key, value);
		}
		modelAndView.addObject("groupTypesMap", groupMap);
		/** changes to display Grouping Types - Ends **/

		LOGGER.info("GroupingControlle:existingGrpDetailsRender:Exit");
		return modelAndView;
	}

	/** This method is used to get Existing Group Component details.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException */
	@ResourceMapping("getExistGrpComponent")
	public final ModelAndView getExistGrpComponentResource(final ResourceRequest request, final ResourceResponse response)
			throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("GroupingControlle:getExistGrpComponentResource ResourceRequest:Enter------------>");
		String message = "";
		ModelAndView modelAndView = null;
		List<GroupAttributeForm> existComponentDetails = new ArrayList<GroupAttributeForm>();

		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);

		String groupType = GroupingUtil.checkNull(request.getParameter(GroupingConstants.GROUP_TYPE));
		String groupId = GroupingUtil.checkNull(request.getParameter(GroupingConstants.GROUP_ID));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getExistGrpComponentResource.groupType---------->" + groupType);
			LOGGER.debug("getExistGrpComponentResource.groupId---------->" + groupId);
		}

		if (groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_COLOR)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getExistGrpComponentResource. In SCG");
			}
			existComponentDetails = groupingService.getExistSplitColorDetails(groupId);
		} else if (groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_SKU)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getExistGrpComponentResource. In SSG");
			}
			existComponentDetails = groupingService.getExistSplitSkuDetails(groupId);
		}

		/** Code to generate response to display Search result in JSP **/
		if (existComponentDetails.size() == 0) {
			message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_NO_DATA);
		}
		String totalRecords = String.valueOf(existComponentDetails.size());
		String defaultSortCol = GroupingConstants.STYLE_ORIN_NO;
		String defaultSortOrder = GroupingConstants.SORT_ASC;
		JSONObject jsonObj = getSplitGrpJsonResponse(message, totalRecords, defaultSortCol, defaultSortOrder, existComponentDetails);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("existComponentDetails JsonResponse-->" + jsonObj);
		}
		try {
			response.getWriter().write(jsonObj.toString());
		} catch (IOException e) {
			LOGGER.error("GroupingControlle:getExistGrpComponentResource:Exception------------>" + e.getMessage());
		}

		LOGGER.info("GroupingControlle:getExistGrpComponentResource ResourceRequest:Exit------------>");
		return modelAndView;
	}

	/** This method is used to get New Group Component details to Alter Component
	 * List.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException */
	@ResourceMapping("getNewGrpComponent")
	public final ModelAndView getNewGrpComponentResource(final ResourceRequest request, final ResourceResponse response)
			throws PEPServiceException, PEPPersistencyException {
		LOGGER.info("GroupingControlle:getNewGrpComponentResource ResourceRequest:Enter------------>");
		String message = "";
		String vendorStyleNo = "";
		String styleOrin = "";
		ModelAndView modelAndView = null;
		// List<GroupAttributeForm> newComponentDetails = new
		// ArrayList<GroupAttributeForm>();

		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);

		String groupType = GroupingUtil.checkNull(request.getParameter(GroupingConstants.GROUP_TYPE));
		String groupId = GroupingUtil.checkNull(request.getParameter(GroupingConstants.GROUP_ID));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getNewGrpComponentResource.groupType---------->" + groupType);
			LOGGER.debug("getNewGrpComponentResource.groupId---------->" + groupId);
		}

		if (groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_COLOR)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("in fetch split color attribute getNewGrpComponentResource-->");
			}
			vendorStyleNo = request.getParameter(GroupingConstants.VENDOR_STYLE_NO);
			styleOrin = request.getParameter(GroupingConstants.STYLE_ORIN_NO);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getNewGrpComponentResource.Search Attribute: vendorStyleNo-->" + vendorStyleNo + "  styleOrin-->" + styleOrin);
			}
			List<GroupAttributeForm> getSplitColorDetailsList = groupingService.getSplitColorDetails(vendorStyleNo, styleOrin);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of getNewGrpComponentResource.getSplitColorDetailsList-->" + getSplitColorDetailsList.size());
			}

			/** Validate Split Attribute List **/
			List<GroupAttributeForm> updatedSplitColorDetailsList = groupingService.validateSCGAttributeDetails(getSplitColorDetailsList);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of getNewGrpComponentResource.updatedSplitColorDetailsList-->" + updatedSplitColorDetailsList.size());
			}
			if (getSplitColorDetailsList.size() == 0) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_NO_DATA); // "No record found!"
			} else if (updatedSplitColorDetailsList.size() == 0) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_INVALID_DATA);
			}
			/** set in session to call add attribute webservice after creating
			 * group **/
			request.getPortletSession().setAttribute(GroupingConstants.SELECTED_ATTRIBUTE_LIST, updatedSplitColorDetailsList);

			/** Code to generate response to display Search result in JSP **/
			String totalRecords = String.valueOf(updatedSplitColorDetailsList.size());
			String defaultSortCol = GroupingConstants.STYLE_ORIN_NO;
			String defaultSortOrder = GroupingConstants.SORT_ASC;
			JSONObject jsonObj = getSplitGrpJsonResponse(message, totalRecords, defaultSortCol, defaultSortOrder,
					updatedSplitColorDetailsList);
			// LOGGER.info("getNewGrpComponentResource.getSCGJsonResponse-->" +
			// jsonObj);
			try {
				response.getWriter().write(jsonObj.toString());
			} catch (IOException e) {
				LOGGER.error("SCG: getNewGrpComponentResource:Exception------------>" + e.getMessage());
			}
			modelAndView = new ModelAndView(null);

		} else if (groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_SKU)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("in fetch split SKU attribute getNewGrpComponentResource-->");
			}

			/** Fetch SKU details for Split group **/
			vendorStyleNo = request.getParameter(GroupingConstants.VENDOR_STYLE_NO);
			styleOrin = request.getParameter(GroupingConstants.STYLE_ORIN_NO);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getNewGrpComponentResource.Search Attribute: vendorStyleNo-->" + vendorStyleNo + "  styleOrin-->" + styleOrin);
			}
			List<GroupAttributeForm> getSplitSKUDetailsList = groupingService.getSplitSKUDetails(vendorStyleNo, styleOrin);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of getNewGrpComponentResource.getSplitSKUDetailsList-->" + getSplitSKUDetailsList.size());
			}

			/** Validate Split Attribute List **/
			List<GroupAttributeForm> updatedSplitSKUDetailsList = groupingService.validateSSGAttributeDetails(getSplitSKUDetailsList);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of getNewGrpComponentResource.updatedSplitSKUDetailsList-->" + updatedSplitSKUDetailsList.size());
			}
			if (getSplitSKUDetailsList.size() == 0) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_NO_DATA); // "No record found!"
			} else if (updatedSplitSKUDetailsList.size() == 0) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_INVALID_DATA);
			}
			/** set in session to call add attribute webservice after creating group **/
			request.getPortletSession().setAttribute(GroupingConstants.SELECTED_ATTRIBUTE_LIST, updatedSplitSKUDetailsList);

			/** Code to generate response to display Search result in JSP **/
			String totalRecords = String.valueOf(updatedSplitSKUDetailsList.size());
			String defaultSortCol = GroupingConstants.STYLE_ORIN_NO;
			String defaultSortOrder = GroupingConstants.SORT_ASC;
			JSONObject jsonObj = getSplitGrpJsonResponse(message, totalRecords, defaultSortCol, defaultSortOrder,
					updatedSplitSKUDetailsList);
			// LOGGER.info("getNewGrpComponentResource.getSCGJsonResponse-->" +
			// jsonObj);

			try {
				response.getWriter().write(jsonObj.toString());
			} catch (IOException e) {
				LOGGER.error("SSG: getNewGrpComponentResource:Exception------------>" + e.getMessage());
			}
			modelAndView = new ModelAndView(null);
		}

		LOGGER.info("GroupingControlle:getNewGrpComponentResource ResourceRequest:Exit------------>");
		return modelAndView;
	}

	/** This method is used to call add Component Service and fetch data from
	 * database.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @throws PEPFetchException */
	@ResourceMapping("addComponentToGroup")
	public final ModelAndView addComponentToGroup(final ResourceRequest request, final ResourceResponse response) throws PEPFetchException,
			Exception {
		LOGGER.info("GroupingControlle:addComponentToGroup ResourceRequest:Enter------------>");
		ModelAndView modelAndView = null;
		String[] selectedItemsArr = null;
		String updatedBy = "";

		/* String formSessionKey = (String)
		 * request.getPortletSession().getAttribute("formSessionKey");
		 * CreateGroupForm createGroupForm = (CreateGroupForm)
		 * request.getPortletSession().getAttribute(formSessionKey); */
		String sessionDataKey = (String) request.getPortletSession().getAttribute("sessionDataKey");
		UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(" handleSplitAttrSearchRequest sessionDataKey ------------> " + sessionDataKey);
		}
		LOGGER.info(" handleSplitAttrSearchRequest custuser -----------------------> " + custuser);

		if (custuser != null) {
			LOGGER.info("userData---->in activate");
			if (custuser.isInternal()) {
				updatedBy = custuser.getBelkUser().getLanId();
				LOGGER.info("Activate Internal user--->" + updatedBy);
			}
		}

		String selectedItems = request.getParameter(GroupingConstants.COMPONENT_SELECTED_ITEMS);
		if (null != selectedItems) {
			selectedItemsArr = selectedItems.split(",");
		}
		String groupType = GroupingUtil.checkNull(request.getParameter(GroupingConstants.GROUP_TYPE));
		String groupId = GroupingUtil.checkNull(request.getParameter(GroupingConstants.GROUP_ID));
		String defaultSelectedAttr = GroupingUtil.checkNull(request.getParameter(GroupingConstants.COMPONENT_DEFAULT_COLOR));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addComponentToGroup.groupType---------->" + groupType);
			LOGGER.debug("addComponentToGroup.groupId---------->" + groupId);
			LOGGER.debug("addComponentToGroup.defaultSelectedAttr---------->" + defaultSelectedAttr);
		}
		List<GroupAttributeForm> selectedSplitAttributeList = new ArrayList<GroupAttributeForm>();

		if (null != groupType && (GroupingConstants.GROUP_TYPE_SPLIT_COLOR).equals(groupType) && null != selectedItems
				&& null != selectedItemsArr) {

			@SuppressWarnings("unchecked")
			List<GroupAttributeForm> updatedSplitColorDetailsList = (List<GroupAttributeForm>) request.getPortletSession().getAttribute(
					GroupingConstants.SELECTED_ATTRIBUTE_LIST);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("All Color Attribute List Size()-->" + updatedSplitColorDetailsList.size());
			}
			selectedSplitAttributeList = groupingService.getSelectedColorAttributeList(updatedSplitColorDetailsList, selectedItemsArr,
					defaultSelectedAttr);
			// createGroupForm.setGroupAttributeFormList(selectedSplitAttributeList);
		}
		if (null != groupType && (GroupingConstants.GROUP_TYPE_SPLIT_SKU).equals(groupType) && null != selectedItems
				&& null != selectedItemsArr) {

			@SuppressWarnings("unchecked")
			List<GroupAttributeForm> updatedSplitSkuDetailsList = (List<GroupAttributeForm>) request.getPortletSession().getAttribute(
					GroupingConstants.SELECTED_ATTRIBUTE_LIST);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("All SKU Attribute List Size()-->" + updatedSplitSkuDetailsList.size());
			}
			selectedSplitAttributeList = groupingService.getSelectedSKUAttributeList(updatedSplitSkuDetailsList, selectedItemsArr,
					defaultSelectedAttr);
			// createGroupForm.setGroupAttributeFormList(selectedSplitAttributeList);
		}

		CreateGroupForm createGroupForm = groupingService.addComponentToGroup(groupId, updatedBy, groupType, selectedSplitAttributeList);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put(GroupingConstants.GROUP_ID, createGroupForm.getGroupId());
		jsonObj.put(GroupingConstants.GROUP_TYPE, createGroupForm.getGroupType());
		jsonObj.put(GroupingConstants.GROUP_CREATION_MSG, createGroupForm.getGroupCretionMsg());
		jsonObj.put(GroupingConstants.GROUP_CREATION_STATUS_CODE, createGroupForm.getGroupCreationStatus());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("jsonObj-->" + jsonObj);
		}

		try {
			response.getWriter().write(jsonObj.toString());
		} catch (IOException e) {
			LOGGER.error("GroupingControlle:handleCreateGroupForm ResourceRequest:Exception------------>" + e.getMessage());
		}
		modelAndView = new ModelAndView(null);
		LOGGER.info("GroupingControlle:getNewGrpComponentResource ResourceRequest:Exit------------>");
		return modelAndView;
	}

}
