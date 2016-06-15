package com.belk.pep.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.belk.pep.form.StyleAttributeForm;
import com.belk.pep.service.GroupingService;
import com.belk.pep.util.GroupingUtil;
import com.belk.pep.util.PropertyLoader;

/** The Class GroupingController. */
@Controller
@RequestMapping("View")
public class GroupingController {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(GroupingController.class.getName());


	/** The Grouping Service. */
	private GroupingService groupingService;

	/**
	 * Return GroupingService Object.
	 *  @return the groupingService
	 *  */
	public GroupingService getGroupingService() {
		return groupingService;
	}

	/** 
	 * Set GroupingService Object.
	 * @param groupingService the groupingService to set */
	public void setGroupingService(GroupingService groupingService) {
		this.groupingService = groupingService;
	}

	/** Create URL to render page for SPLIT Color group.
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
		UserData custuser = null;
		String sessionDataKey = (String) renderRequest.getPortletSession().getAttribute("sessionDataKey");
		if (null != sessionDataKey) {
			String userID = sessionDataKey.split(GroupingConstants.USER_DATA + renderRequest.getPortletSession().getId())[1];
			LOGGER.info("display user ID" + userID);
			mv.addObject(GroupingConstants.LAN_ID, userID);
			custuser = (UserData) renderRequest.getPortletSession().getAttribute(sessionDataKey);
			if(GroupingConstants.READ_ONLY_ROLE.equals(custuser.getRoleName())){
				mv.addObject(GroupingConstants.READ_ONLY_ROLE, GroupingConstants.YES_VALUE);
			}else{
				mv.addObject(GroupingConstants.READ_ONLY_ROLE, GroupingConstants.NO_VALUE);
			}
		}
		/** changes to display lan id - changed by Ramkumar - ends. **/

		/** changes to display Grouping Types - Starts. **/
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
		/** changes to display Grouping Types - Ends. **/
		LOGGER.info("GroupingControlle:splitRenderHandler:Exit");
		return mv;
	}

	/** Create URL to render page for SPLIT SKU group.
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
		UserData custuser = null;
		String sessionDataKey = (String) renderRequest.getPortletSession().getAttribute("sessionDataKey");
		if (null != sessionDataKey) {
			String userID = sessionDataKey.split(GroupingConstants.USER_DATA + renderRequest.getPortletSession().getId())[1];
			LOGGER.info("display user ID" + userID);
			mv.addObject(GroupingConstants.LAN_ID, userID);
			custuser = (UserData) renderRequest.getPortletSession().getAttribute(sessionDataKey);
			if(GroupingConstants.READ_ONLY_ROLE.equals(custuser.getRoleName())){
				mv.addObject(GroupingConstants.READ_ONLY_ROLE, GroupingConstants.YES_VALUE);
			}else{
				mv.addObject(GroupingConstants.READ_ONLY_ROLE, GroupingConstants.NO_VALUE);
			}
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

	/** Default Render method of Grouping Portlet when page loads. */
	@RenderMapping
	public ModelAndView handleRenderRequest(RenderRequest request, RenderResponse response) {

		LOGGER.info("GroupingControlle:handleRenderRequest:Enter");

		String createGroupType = request.getParameter("groupTypeSelector");
		LOGGER.info("createGroupType----------------->" + createGroupType);
		ModelAndView modelAndView = null;
		modelAndView = new ModelAndView(GroupingConstants.GROUPING_PAGE); // groupingPage.jsp
		/** changes to display lan id - changed by Ramkumar - starts **/
		UserData custuser = null;
		String sessionDataKey = (String) request.getPortletSession().getAttribute("sessionDataKey");
		if (null != sessionDataKey) {
			String userID = sessionDataKey.split(GroupingConstants.USER_DATA + request.getPortletSession().getId())[1];
			LOGGER.info("display user ID" + userID);
			modelAndView.addObject(GroupingConstants.LAN_ID, userID);
			custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);
			if(GroupingConstants.READ_ONLY_ROLE.equals(custuser.getRoleName())){
				modelAndView.addObject(GroupingConstants.READ_ONLY_ROLE, GroupingConstants.YES_VALUE);
			}else{
				modelAndView.addObject(GroupingConstants.READ_ONLY_ROLE, GroupingConstants.NO_VALUE);
			}
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
		/** changes to display Grouping Types - Ends. **/

		LOGGER.info("GroupingControlle:handleRenderRequest:Exit");
		return modelAndView;
	}

	/** This method will handle the Event request and to fetch the user details
	 * from the login portlet */
	/**
	 * @param request
	 * @param response
	 */
	@EventMapping
	public void handleEventRequest(EventRequest request, EventResponse response) {
		LOGGER.info("GroupingController:handleEventRequest:Enter ");
		Event event = request.getEvent();
		String loggedInUser = "";

		if (event.getName() != null && GroupingConstants.USER_DATA_OBJ.equals(event.getName())) {
			LOGGER.info("GroupingController:handlingPagination : " + event.getName());
				UserData custuser = (UserData) event.getValue();

				if (null != custuser) {
					Common_BelkUser belkUser = (Common_BelkUser) custuser.getBelkUser();
					
						if (null!=custuser.getVpUser() && null != custuser.getVpUser().getUserEmailAddress()) {
							LOGGER.info("This is from Event Email Id********************" + custuser.getVpUser().getUserEmailAddress());
							loggedInUser = custuser.getVpUser().getUserEmailAddress();
						} else if (null != custuser.getRoleName()) {
							LOGGER.info("This is from Event Role name********************" + custuser.getRoleName());
						} else if (null != custuser.getAccessRight()) {
							LOGGER.info("This is from Access********************" + custuser.getAccessRight());
						}

						if (null != belkUser && null != belkUser.getLanId()) {
							LOGGER.info("belkUser.getLanId() ******************" + belkUser.getLanId());
							loggedInUser = belkUser.getLanId();
						}

					String sessionDataKey = GroupingConstants.USER_DATA + request.getPortletSession().getId() + loggedInUser;
					String formSessionKey = request.getPortletSession().getId() + loggedInUser;
					
					

					LOGGER.info(" loggedInUser **************" + formSessionKey);

					request.getPortletSession().setAttribute("formSessionKey", formSessionKey);
					request.getPortletSession().setAttribute("sessionDataKey", sessionDataKey);
					request.getPortletSession().setAttribute(sessionDataKey, custuser);
				}
			}

	}

	/** This method will take care of handleResourceRequest, handles all Ajax
	 * calls. Used to handle Split Color and Split SKU search request
	 * 
	 * @param request
	 * @param response
	 * @return
	  */
	@SuppressWarnings("unchecked")
	@ResourceMapping("splitAttributeSearch")
	public ModelAndView handleSplitAttrSearchRequest(ResourceRequest request, ResourceResponse response)  {
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
try{
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
		if ("SearchColor".equals(strFromPage)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("in fetch split color attribute-->");
			}
			vendorStyleNo = request.getParameter(GroupingConstants.VENDOR_STYLE_NO);
			styleOrin = request.getParameter(GroupingConstants.STYLE_ORIN_NO_SEARCH_PARAM);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search Attribute: -- vendorStyleNo-->" + vendorStyleNo + "  styleOrin-->" + styleOrin);
			}
			List<GroupAttributeForm> getSplitColorDetailsList = groupingService.getSplitColorDetails(vendorStyleNo, styleOrin);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of getSplitColorDetailsList-->" + getSplitColorDetailsList.size());
			}

			/** Validate Split Attribute List **/

			String errorCode = "";
			List<GroupAttributeForm> updatedSplitColorDetailsList = new ArrayList<GroupAttributeForm>();
			Map<String, List<GroupAttributeForm>> updatedSplitColorDetailsMap;

				updatedSplitColorDetailsMap = groupingService
						.validateSCGAttributeDetails(getSplitColorDetailsList);
			
			Iterator<Entry<String, List<GroupAttributeForm>>> entries = updatedSplitColorDetailsMap.entrySet().iterator();
			while (entries.hasNext()) {
				@SuppressWarnings("rawtypes")
				Entry thisEntry = (Entry) entries.next();
				errorCode = (String) thisEntry.getKey();
				updatedSplitColorDetailsList = (List<GroupAttributeForm>) thisEntry.getValue();
			}
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of updatedSplitColorDetailsList.errorCode-->" + errorCode);
				LOGGER.debug("Size of updatedSplitColorDetailsList-->" + updatedSplitColorDetailsList.size());
			}
			if (getSplitColorDetailsList.isEmpty() || "noData".equals(errorCode)) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_NO_DATA); // "No record found!"
			} else if (updatedSplitColorDetailsList.isEmpty() && "notCompleted".equals(errorCode)) {
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

				if(jsonObj!=null) {
					response.getWriter().write(jsonObj.toString());
				}
			
			view = new ModelAndView(null);
		} else {
			/** Fetch SKU details for Split group **/
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("in fetch split SKU attribute-->");
			}
			vendorStyleNo = request.getParameter(GroupingConstants.VENDOR_STYLE_NO);
			styleOrin = request.getParameter(GroupingConstants.STYLE_ORIN_NO_SEARCH_PARAM);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Search Attribute: -- vendorStyleNo-->" + vendorStyleNo + "  styleOrin-->" + styleOrin);
			}
			List<GroupAttributeForm> getSplitSKUDetailsList = groupingService.getSplitSKUDetails(vendorStyleNo, styleOrin);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of getSplitSKUDetailsList-->" + getSplitSKUDetailsList.size());
			}

			/** Validate Split Attribute List **/
			String errorCode = "";
			List<GroupAttributeForm> updatedSplitSKUDetailsList = new ArrayList<GroupAttributeForm>();
			Map<String, List<GroupAttributeForm>> updatedSplitSKUDetailsMap = groupingService
					.validateSSGAttributeDetails(getSplitSKUDetailsList);
			Iterator<Entry<String, List<GroupAttributeForm>>> entries = updatedSplitSKUDetailsMap.entrySet().iterator();
			while (entries.hasNext()) {
				@SuppressWarnings("rawtypes")
				Entry thisEntry = (Entry) entries.next();
				errorCode = (String) thisEntry.getKey();
				updatedSplitSKUDetailsList = (List<GroupAttributeForm>) thisEntry.getValue();
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of updatedSplitSKUDetailsList.errorCode-->" + errorCode);
				LOGGER.debug("Size of updatedSplitSKUDetailsList-->" + updatedSplitSKUDetailsList.size());
			}
			if (getSplitSKUDetailsList.isEmpty() || "noData".equals(errorCode)) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_NO_DATA); // "No record found!"
			} else if (updatedSplitSKUDetailsList.isEmpty() && "notCompleted".equals(errorCode)) {
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


				response.getWriter().write(jsonObj.toString());
			
			view = new ModelAndView(null);
		}
} catch (PEPPersistencyException e) {
	LOGGER.error("GroupingControlle:handleCreateGroupForm handleSplitAttrSearchRequest:PEPPersistencyException------------>"+ e);
	}
catch (IOException e) {
	LOGGER.error("GroupingControlle:handleCreateGroupForm handleSplitAttrSearchRequest:IOException------------>"
			+ e);
} catch (PEPServiceException e) {
	LOGGER.error("GroupingControlle:handleCreateGroupForm handleSplitAttrSearchRequest:PEPServiceException------------>"
			+ e);
} catch (PEPFetchException e) {
	LOGGER.error("GroupingControlle:handleCreateGroupForm handleSplitAttrSearchRequest:PEPFetchException------------>"
			+ e);
}
		LOGGER.info("Exit handleSplitAttrSearchRequest-->");
		return view;
	}

	/** Method to create JSON object to display search result in jsp for SCG and
	 * SSG.
	 * 
	 * @param message String
	 * @param totalRecords String
	 * @param defaultSortCol String
	 * @param defaultSortOrder String
	 * @param selectedSplitAttributeList List
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

				styleOrinNoSearch = GroupingUtil.checkNull(groupAttributeForm.getParentMdmid());
				vendorStyleNoSearch = GroupingUtil.checkNull(groupAttributeForm.getStyleNumber());
				jsonObjComponent.put(GroupingConstants.STYLE_ORIN_NO, styleOrinNoSearch);
				jsonObjComponent.put(GroupingConstants.VENDOR_STYLE_NO, vendorStyleNoSearch);
				jsonObjComponent.put(GroupingConstants.PRODUCT_NAME, groupAttributeForm.getProdName());
				jsonObjComponent.put(GroupingConstants.COLOR_CODE, groupAttributeForm.getColorCode());
				jsonObjComponent.put(GroupingConstants.COLOR_NAME, groupAttributeForm.getColorName());
				String defaultColor = null == groupAttributeForm.getIsDefault() ? "" : groupAttributeForm.getIsDefault();

				jsonObjComponent.put(GroupingConstants.COMPONENT_DEFAULT_COLOR, defaultColor);
				String isAlreadyInGroup = groupAttributeForm.getIsAlreadyInGroup();
				
				isAlreadyInGroup = (null == isAlreadyInGroup ? "No" : ("").equalsIgnoreCase(isAlreadyInGroup.trim()) ? "No" 
						: ("N").equalsIgnoreCase(isAlreadyInGroup.trim()) ? "No" : ("Y")
						.equalsIgnoreCase(isAlreadyInGroup.trim()) ? "Yes" : "No");

				jsonObjComponent.put(GroupingConstants.ALREADY_IN_GROUP, isAlreadyInGroup);
				jsonObjComponent.put(GroupingConstants.COMPONENT_SIZE, groupAttributeForm.getSize()); // Only for Split SKU

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
			LOGGER.error("Exeception in parsing the jsonObj-->" + e);
		}
		LOGGER.info("Exit getSplitGrpJsonResponse-->");
		return jsonObj;
	}

	/** Method to create JSON object to display search result in jsp for CPG.
	 * 
	 * @param message String
	 * @param totalRecords String
	 * @param defaultSortCol String
	 * @param defaultSortOrder String
	 * @param existCPGDetails List
	 * 
	 * @return JSONObject
	 * @author Cognizant */
	public final JSONObject getCPGGrpJsonResponse(final String message, final String totalRecords, final String defaultSortCol,
			final String defaultSortOrder, final List<StyleAttributeForm> existCPGDetails) {
		LOGGER.info("Enter getCPGGrpJsonResponse-->");
		String vendorStyleNoSearch = "";
		String styleOrinNoSearch = "";
		String classId = "";
		List<GroupAttributeForm> groupAttributeFormList = null;
		GroupAttributeForm groupAttributeForm = null;
		
		JSONObject jsonObjComponent = null;
		JSONObject jsonObjComponentSub = null;
		JSONArray jsonArraySub = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();

		try {
			for (int i = 0; i < existCPGDetails.size(); i++) {

				StyleAttributeForm styleAttributeForm = existCPGDetails.get(i);
				jsonObjComponent = new JSONObject();
				jsonArraySub = new JSONArray();
				styleOrinNoSearch = GroupingUtil.checkNull(styleAttributeForm.getOrinNumber());
				classId = GroupingUtil.checkNull(styleAttributeForm.getClassId());

				vendorStyleNoSearch = GroupingUtil.checkNull(styleAttributeForm.getStyleNumber());
				jsonObjComponent.put(GroupingConstants.STYLE_ORIN_NO, styleOrinNoSearch);
				jsonObjComponent.put(GroupingConstants.VENDOR_STYLE_NO, vendorStyleNoSearch);
				jsonObjComponent.put(GroupingConstants.PRODUCT_NAME, styleAttributeForm.getProdName());
				jsonObjComponent.put(GroupingConstants.COLOR_CODE, styleAttributeForm.getColorCode());
				jsonObjComponent.put(GroupingConstants.COLOR_NAME, styleAttributeForm.getColorName());
				String defaultColor = null == styleAttributeForm.getIsDefault() ? "" : styleAttributeForm.getIsDefault();
				jsonObjComponent.put(GroupingConstants.COMPONENT_DEFAULT_COLOR, defaultColor);
				String isAlreadyInGroup = styleAttributeForm.getIsAlreadyInGroup();
				isAlreadyInGroup = (null == isAlreadyInGroup ? "No" : ("").equalsIgnoreCase(isAlreadyInGroup.trim()) ? "No" : ("N")
						.equalsIgnoreCase(isAlreadyInGroup.trim()) ? "No" : ("Y").equalsIgnoreCase(isAlreadyInGroup.trim()) ? "Yes"
						: "No");
				jsonObjComponent.put(GroupingConstants.ALREADY_IN_GROUP, isAlreadyInGroup);

				String isAlreadyInSameGroup = styleAttributeForm.getIsAlreadyInSameGroup();
				isAlreadyInSameGroup = (null == isAlreadyInSameGroup ? "No" : ("").equalsIgnoreCase(isAlreadyInSameGroup.trim()) ? "No" : ("N")
						.equalsIgnoreCase(isAlreadyInSameGroup.trim()) ? "No" : ("Y").equalsIgnoreCase(isAlreadyInSameGroup.trim()) ? "Yes"
						: "No");
				
				jsonObjComponent.put(GroupingConstants.ALREADY_IN_SAME_GROUP, isAlreadyInSameGroup);

				groupAttributeFormList = styleAttributeForm.getGroupAttributeFormList();
				
				for (int j = 0; j < groupAttributeFormList.size(); j++) {
					jsonObjComponentSub = new JSONObject();
					groupAttributeForm = groupAttributeFormList.get(j);

					styleOrinNoSearch = GroupingUtil.checkNull(groupAttributeForm.getOrinNumber());

					vendorStyleNoSearch = GroupingUtil.checkNull(groupAttributeForm.getStyleNumber());
					jsonObjComponentSub.put(GroupingConstants.STYLE_ORIN_NO, styleOrinNoSearch);
					jsonObjComponentSub.put(GroupingConstants.VENDOR_STYLE_NO, vendorStyleNoSearch);
					jsonObjComponentSub.put(GroupingConstants.PRODUCT_NAME, groupAttributeForm.getProdName());
					jsonObjComponentSub.put(GroupingConstants.COLOR_CODE, groupAttributeForm.getColorCode());
					jsonObjComponentSub.put(GroupingConstants.COLOR_NAME, groupAttributeForm.getColorName());
					defaultColor = (null == groupAttributeForm.getIsDefault() ? "" : groupAttributeForm.getIsDefault());
					jsonObjComponentSub.put(GroupingConstants.COMPONENT_DEFAULT_COLOR, defaultColor);
					isAlreadyInGroup = groupAttributeForm.getIsAlreadyInGroup();
					isAlreadyInGroup = (null == isAlreadyInGroup ? "No" : ("").equalsIgnoreCase(isAlreadyInGroup.trim()) ? "No"
							: ("N").equalsIgnoreCase(isAlreadyInGroup.trim()) ? "No"
									: ("Y").equalsIgnoreCase(isAlreadyInGroup.trim()) ? "Yes" : "No");
					jsonObjComponentSub.put(GroupingConstants.ALREADY_IN_GROUP, isAlreadyInGroup);

					jsonArraySub.put(jsonObjComponentSub);
				}
				jsonObjComponent.put(GroupingConstants.COMPONENT_LIST_SUB, jsonArraySub);
				jsonArray.put(jsonObjComponent);
			}

			jsonObj.put(GroupingConstants.MESSAGE, message);
			jsonObj.put(GroupingConstants.SPLIT_GROUP_TOTAL_RECORDS, totalRecords);
			jsonObj.put(GroupingConstants.SPLIT_GROUP_DEFAULT_SORT_COL, defaultSortCol);
			jsonObj.put(GroupingConstants.SPLIT_GROUP_DEFAULT_SORT_ORDER, defaultSortOrder);
			jsonObj.put(GroupingConstants.STYLE_ORIN_NO_SEARCH, styleOrinNoSearch);
			jsonObj.put(GroupingConstants.VENDOR_STYLE_NO_SEARCH, vendorStyleNoSearch);
			jsonObj.put(GroupingConstants.CLASS_ID, classId);
			
			jsonObj.put(GroupingConstants.COMPONENT_LIST, jsonArray);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("JSON getCPGGrpJsonResponse-->" + jsonObj);
			}
		} catch (JSONException e) {
			LOGGER.error("Exeception in parsing the jsonObj-->" + e);
		}
		LOGGER.info("Exit getCPGGrpJsonResponse-->");
		return jsonObj;
	}

	/** This method is used to create group.
	 * 
	 * @param request ResourceRequest
	 * @param response ResourceResponse
	 * @return ModelAndView
 */
	@ResourceMapping("submitCreateGroupForm")
	public final ModelAndView handleCreateGroupForm(final ResourceRequest request, final ResourceResponse response)
 {
		LOGGER.info("GroupingControlle:handleCreateGroupForm ResourceRequest:Enter------------>");
		ModelAndView modelAndView = null;
		String formSessionKey = (String) request.getPortletSession().getAttribute("formSessionKey");
		LOGGER.info("handleRenderRequest formSessionKey  ----------------------------->" + formSessionKey);
		String sessionDataKey = (String) request.getPortletSession().getAttribute("sessionDataKey");
		LOGGER.info(" handleActionRequest sessionDataKey -----------------------------> " + sessionDataKey);
		UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);
		LOGGER.info(" handleActionRequest custuser -----------------------------------> " + custuser);
		String pepUserId = custuser.getBelkUser().getLanId();
		LOGGER.info("This is from Reneder Internal User--------------------->" + pepUserId);
try{
		String carsGroupingType = "";
		String[] selectedItemsArr = null;
		String groupType = GroupingUtil.checkNull(request.getParameter(GroupingConstants.GROUP_TYPE));
		String groupName = request.getParameter(GroupingConstants.GROUP_NAME);
		String groupDesc = request.getParameter(GroupingConstants.GROUP_DESC);
		String startDate = request.getParameter(GroupingConstants.START_DATE);
		String endDate = request.getParameter(GroupingConstants.END_DATE);
		String defaultSelectedAttr = request.getParameter(GroupingConstants.COMPONENT_DEFAULT_COLOR);
		if(GroupingConstants.GROUP_TYPE_REGULAR_COLLECTION.equals(groupType)){
			carsGroupingType = request.getParameter(GroupingConstants.CARS_GROUPING_TYPE);
			carsGroupingType = (null == carsGroupingType ? "" : carsGroupingType.trim());
		} else if(GroupingConstants.GROUP_TYPE_BEAUTY_COLLECTION.equals(groupType)){
			carsGroupingType = GroupingConstants.CARS_GROUP_TYPE_BCG;
		} else if(GroupingConstants.GROUP_TYPE_GROUP_BY_SIZE.equals(groupType)){
			carsGroupingType = GroupingConstants.CARS_GROUP_TYPE_GBS;
		} else if(GroupingConstants.GROUP_TYPE_CONSOLIDATE_PRODUCT.equals(groupType)){
			carsGroupingType = GroupingConstants.CARS_GROUP_TYPE_CPG;
		}
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

			/** Add Selected Attribute to List (selectedSplitAttributeList) for
			 * Split Color and Group **/
			@SuppressWarnings("unchecked")
			List<GroupAttributeForm> updatedSplitColorDetailsList = (List<GroupAttributeForm>) request.getPortletSession()
					.getAttribute(GroupingConstants.SELECTED_ATTRIBUTE_LIST);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("All Color Attribute List Size()-->" + updatedSplitColorDetailsList.size());
			}
			selectedSplitAttributeList = groupingService.getSelectedColorAttributeList(updatedSplitColorDetailsList, selectedItemsArr,
					defaultSelectedAttr);
			
		}
		if (GroupingConstants.GROUP_TYPE_SPLIT_SKU.equals(groupType)
				&& null != selectedItemsArr) {
			@SuppressWarnings("unchecked")
			List<GroupAttributeForm> updatedSplitSkuDetailsList = (List<GroupAttributeForm>) request.getPortletSession().getAttribute(
					GroupingConstants.SELECTED_ATTRIBUTE_LIST);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("All SKU Attribute List Size()-->" + updatedSplitSkuDetailsList.size());
			}
			selectedSplitAttributeList = groupingService.getSelectedSKUAttributeList(updatedSplitSkuDetailsList, selectedItemsArr,
					defaultSelectedAttr);

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

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("jsonObj-->" + jsonObj);
		}

		

			response.getWriter().write(jsonObj.toString());
} catch (IOException e) {
	LOGGER.error("GroupingControlle:handleCreateGroupForm ResourceRequest:Exception------------>" + e);
} catch (PEPServiceException e) {
	LOGGER.error("GroupingControlle:handleCreateGroupForm ResourceRequest:PEPServiceException------------>" + e);
} catch (PEPPersistencyException e) {
	LOGGER.error("GroupingControlle:handleCreateGroupForm ResourceRequest:PEPPersistencyException------------>" + e);
}

		LOGGER.info("GroupingControlle:handleCreateGroupForm ResourceRequest:Exit------------>");
		return modelAndView;
	}

	/** Create URL to render page for Create Group successfully.
	 * 
	 * @param renderRequest RenderRequest
	 * @param renderResponse RenderResponse
	 * @return ModelAndView
	 * @throws ParseException ParseException */
	@RenderMapping(params = "createGroupSuccessRender=CreateGrpSuccess")
	public final ModelAndView createGroupSuccessRender(final RenderRequest renderRequest, final RenderResponse renderResponse)
			throws ParseException {
		// do some processing here
		LOGGER.info("GroupingControlle:createGroupSuccessRender:Enter");
		
		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);

		CreateGroupForm objCreateGroupForm = (CreateGroupForm) renderRequest.getPortletSession().getAttribute(
				GroupingConstants.GROUP_DETAILS_FORM);
		String groupTypeCode = null == objCreateGroupForm.getGroupType() ? "" : objCreateGroupForm.getGroupType().trim();
		String groupTypeDesc = prop.getProperty(groupTypeCode);
		String groupStatusCode = null == objCreateGroupForm.getGroupStatus() ? "" : objCreateGroupForm.getGroupStatus().trim();
		String groupStatusDesc = prop.getProperty(GroupingConstants.GROUP_STATUS_EXT + groupStatusCode);
		
		objCreateGroupForm.setGroupTypeDesc(groupTypeDesc);
		objCreateGroupForm.setGroupStatusDesc(groupStatusDesc);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("createGroupSuccessRender.objCreateGroupForm-->" + objCreateGroupForm);
			LOGGER.debug("createGroupSuccessRender.objCreateGroupForm.getGroupId()-->" + objCreateGroupForm.getGroupId());
			LOGGER.debug(groupTypeCode + " createGroupSuccessRender.objCreateGroupForm.groupTypeDesc-->" + groupTypeDesc);
			LOGGER.debug(groupStatusCode + " createGroupSuccessRender.objCreateGroupForm.groupStatus-->" + groupStatusDesc);
		}
		ModelAndView mv = new ModelAndView(GroupingConstants.GROUPING_ADD_COMPONENT);
		mv.addObject(GroupingConstants.GROUP_DETAILS_FORM, objCreateGroupForm);
		/** changes to display lan id - changed by Ramkumar - starts **/
		UserData custuser = null;
		String sessionDataKey = (String) renderRequest.getPortletSession().getAttribute("sessionDataKey");
		if (null != sessionDataKey) {
			String userID = sessionDataKey.split(GroupingConstants.USER_DATA + renderRequest.getPortletSession().getId())[1];
			LOGGER.info("display user ID" + userID);
			mv.addObject(GroupingConstants.LAN_ID, userID);
			custuser = (UserData) renderRequest.getPortletSession().getAttribute(sessionDataKey);
			if(GroupingConstants.READ_ONLY_ROLE.equals(custuser.getRoleName())){
				mv.addObject(GroupingConstants.READ_ONLY_ROLE, GroupingConstants.YES_VALUE);
			}else{
				mv.addObject(GroupingConstants.READ_ONLY_ROLE, GroupingConstants.NO_VALUE);
			}
		}
		/** changes to display lan id - changed by Ramkumar - ends **/

		/** changes to display Grouping Types - starts **/

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
	 * @param createGroupForm CreateGroupForm
	 * @param updatedBy String
	 * @return CreateGroupForm
	 * @author AFUPYB3 */
	private final CreateGroupForm createGroup(final CreateGroupForm createGroupForm, String updatedBy) {
		LOGGER.info("Entering:: createGroup method controller");

		JSONObject jsonStyle = null;
		CreateGroupForm createGroupFormRes = null;

		try {
			jsonStyle = populateCreateGroupJson(createGroupForm, updatedBy);
			LOGGER.info("Create Group Json Style-->" + jsonStyle);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("json Object createGroup Group Name--> " + jsonStyle.getString("groupName"));
			}
		} catch (Exception e) {
			LOGGER.error("inside catch for createGroup()-->controller-->" + e);
		}
		try {
			createGroupFormRes = groupingService.saveGroupHeaderDetails(jsonStyle, updatedBy,
					createGroupForm.getGroupAttributeFormList());

			LOGGER.info("responseMsg_code Controller createGroup-->" + createGroupFormRes.getGroupCretionMsg());
		} catch (PEPFetchException eService) {
			LOGGER.error("Exception Block in Controller::PEPFetchException-->" + eService);
		} catch (Exception e) {
			LOGGER.error("Exception Block in Controller::Exception-->" + e);
		}
		LOGGER.info("Exiting--> createGroup method controller");

		return createGroupFormRes;
	} // End Group Creation

	/** Method to pass JSON Array to call the Create Group service.
	 * 
	 * @param createGroupForm CreateGroupForm
	 * @param updatedBy String
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
			LOGGER.error("Exeception in parsing the jsonObj-->" + e);
		}
		return jsonObj;
	}

	/** Method to create JSON objects for Search Group List.
	 * 
	 * @param groupSearchForm GroupSearchForm
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
	 * @param request ResourceRequest
	 * @param response ResourceResponse
	 * @return ModelAndView
	 *  */
	@ResourceMapping
	public final ModelAndView handleResourceRequest(final ResourceRequest request, final ResourceResponse response)  {

		LOGGER.info("Entering handleResourceRequest() in GroupingController class.");
		String action = GroupingUtil.checkNull(request.getParameter("resourceType"));
		ModelAndView modelAndView = null;
		try{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Action is -- " + action);
		}
		if ("searchGroup".equals(action)) {
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
						+ "\nSUPPILER ID: " + searchForm.getSupplierSiteId() + "\nDEPARTMENTS: " + searchForm.getDepts()
						+ "\nCLASSES: " + searchForm.getClasses() + "\nPAGE NUMBER: " + searchForm.getPageNumber()
						+ "\nRECODRS PER PAGE: " + searchForm.getRecordsPerPage() + "\nSORT COLUMN: " + searchForm.getSortColumn()
						+ "\nASC/DESC: " + searchForm.getAscDescOrder());
			}

			searchForm = groupingService.groupSearch(searchForm);
			if (searchForm.getGroupId() != null && !"".equals(searchForm.getGroupId().trim())) {
				searchForm.setTotalRecordCount(groupingService.groupSearchCount(searchForm) + searchForm.getParentCount());
			} else {
				searchForm.setTotalRecordCount(groupingService.groupSearchCount(searchForm));
			}
			PortletSession portletSession = request.getPortletSession();
			portletSession.setAttribute("SEARCH_RESULT", searchForm);
			JSONObject jsonObject = searchGroupJsonObject(searchForm);


				response.getWriter().write(jsonObject.toString());

		}else if("searchDept".equals(action)){
			ArrayList<DepartmentDetails> deptList = groupingService.getDeptDetailsByDepNoFromADSE();
			JSONObject jsonObject = departmentListJsonObject(deptList);
				response.getWriter().write(jsonObject.toString());

		}else if("searchClass".equals(action)) {
			String depts = GroupingUtil.checkNull(request.getParameter("depts"));
			List<ClassDetails> classList = groupingService.getClassDetailsByDepNos(depts);
			JSONObject jsonObject = classListJsonObject(classList);

				response.getWriter().write(jsonObject.toString());

		}else if("deleteGroup".equals(action)) {
			LOGGER.info("handleDeleteGroup ResourceRequest:Enter------------>");
			String formSessionKey = (String) request.getPortletSession().getAttribute("formSessionKey");
			LOGGER.info("handleDeleteGroup formSessionKey  ----------------------------->" + formSessionKey);
			String sessionDataKey = (String) request.getPortletSession().getAttribute("sessionDataKey");
			LOGGER.info(" handleDeleteGroup sessionDataKey -----------------------------> " + sessionDataKey);
			UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);
			LOGGER.info(" handleDeleteGroup custuser -----------------------------------> " + custuser);
			String pepUserId = custuser.getBelkUser().getLanId();
			LOGGER.info("This is from Reneder Internal User--------------------->" + pepUserId);

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

				response.getWriter().write(json.toString());
			
		}
			}catch (IOException e) {
				LOGGER.error("handleDeleteGroup ResourceRequest:IOException------------>" + e);
			}
		catch (Exception e) {
			LOGGER.error("handleDeleteGroup ResourceRequest:Exception------------>" + e);
		}
		LOGGER.info("Exiting handleResourceRequest() in GroupingController class.");
		return modelAndView;
	}

	/** Method to create JSON objects for Dept List.
	 * 
	 * @param departmentList ArrayList
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
	 * @param classList ArrayList
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
	 * @param request ResourceRequest
	 * @param response ResourceResponse
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
			response.getWriter().write(json.toString());
			
		} catch (Exception e) {
			LOGGER.error("handleDeleteGroup ResourceRequest:Exception------------>" + e);
		}

		LOGGER.info("GroupingControlle:handleDeleteGroup ResourceRequest:Exit------------>");
		return modelAndView;
	}

	/** This method is used to get the existing group Header details.
	 * 
	 * @param request ActionRequest
	 * @param response ActionResponse
 */
	@ActionMapping(params = "getGroupDetails=getGroupDetails")
	public final void getExistingGrpDetails(final ActionRequest request, final ActionResponse response) {
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

	/** This method is used to get the existing group Header details and Render
	 * that page to Component details Page. It's calling from
	 * getExistingGrpDetails()
	 * 
	 * @param request RenderRequest
	 * @param response RenderResponse
	 * @return ModelAndView */
	@RenderMapping(params = "existingGrpDetailsRender=existingGrpDetailsRender")
	public final ModelAndView existingGrpDetailsRender(final RenderRequest request, final RenderResponse response) {

		LOGGER.info("GroupingControlle.existingGrpDetailsRender:Enter");
		ModelAndView modelAndView = null;
		modelAndView = new ModelAndView(GroupingConstants.GROUPING_ADD_COMPONENT);

		String groupType = (String) request.getPortletSession().getAttribute(GroupingConstants.GROUP_TYPE);
		String groupId = (String) request.getPortletSession().getAttribute(GroupingConstants.GROUP_ID);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("existingGrpDetailsRender.groupType---------->" + groupType);
			LOGGER.debug("existingGrpDetailsRender.groupId---------->" + groupId);
		}
		try{

		CreateGroupForm objCreateGroupForm = groupingService.getExistingGrpDetails(groupId);

		
		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);
		String groupTypeCode = null == objCreateGroupForm.getGroupType() ? "" : objCreateGroupForm.getGroupType().trim();
		String groupTypeDesc = prop.getProperty(groupTypeCode);
		String groupStatusCode = null == objCreateGroupForm.getGroupStatus() ? "" : objCreateGroupForm.getGroupStatus().trim();
		String groupStatusDesc = prop.getProperty(GroupingConstants.GROUP_STATUS_EXT + groupStatusCode);
		

		objCreateGroupForm.setGroupTypeDesc(groupTypeDesc);
		objCreateGroupForm.setGroupStatusDesc(groupStatusDesc);
		

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getExistingGrpDetails.objCreateGroupForm-->" + objCreateGroupForm);
			LOGGER.debug("getExistingGrpDetails.objCreateGroupForm.getGroupId()-->" + objCreateGroupForm.getGroupId());
			LOGGER.debug(groupTypeCode + " getExistingGrpDetails.objCreateGroupForm.groupTypeDesc-->" + groupTypeDesc);
			LOGGER.debug(groupStatusCode + " getExistingGrpDetails.objCreateGroupForm.groupStatus-->" + groupStatusDesc);
		}
		modelAndView.addObject(GroupingConstants.GROUP_DETAILS_FORM, objCreateGroupForm);

		/** changes to display lan id - changed by Ramkumar - starts **/
		UserData custuser = null;
		String sessionDataKey = (String) request.getPortletSession().getAttribute("sessionDataKey");
		if (null != sessionDataKey) {
			String userID = sessionDataKey.split(GroupingConstants.USER_DATA + request.getPortletSession().getId())[1];
			LOGGER.info("display user ID" + userID);
			modelAndView.addObject(GroupingConstants.LAN_ID, userID);
			custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);
			if(GroupingConstants.READ_ONLY_ROLE.equals(custuser.getRoleName())){
				modelAndView.addObject(GroupingConstants.READ_ONLY_ROLE, GroupingConstants.YES_VALUE);
			}else{
				modelAndView.addObject(GroupingConstants.READ_ONLY_ROLE, GroupingConstants.NO_VALUE);
			}
		}
		/** changes to display lan id - changed by Ramkumar - ends **/

		/** changes to display Grouping Types - starts **/
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
		} catch( Exception e){
			LOGGER.error("GroupingControlle:existingGrpDetailsRender :Exception --> " +e);
		}

		LOGGER.info("GroupingControlle:existingGrpDetailsRender:Exit");
		return modelAndView;
	}

	/** This method is used to get Existing Group Component details.
	 * 
	 * @param request
	 * @param response
	 * @return modelAndView*/
	@ResourceMapping("getExistGrpComponent")
	public final ModelAndView getExistGrpComponentResource(final ResourceRequest request, final ResourceResponse response)
 {
		LOGGER.info("GroupingControlle:getExistGrpComponentResource ResourceRequest:Enter------------>");
		String message = "";
		ModelAndView modelAndView = null;
		JSONObject jsonObj = null;
		String totalRecords = "0";
		String defaultSortCol = GroupingConstants.STYLE_ORIN_NO;
		String defaultSortOrder = GroupingConstants.SORT_ASC;
		List<GroupAttributeForm> existComponentDetails = new ArrayList<GroupAttributeForm>();
		List<StyleAttributeForm> existCPGDetails = new ArrayList<StyleAttributeForm>();

		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);

		String groupType = GroupingUtil.checkNull(request.getParameter(GroupingConstants.GROUP_TYPE));
		String groupId = GroupingUtil.checkNull(request.getParameter(GroupingConstants.GROUP_ID));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getExistGrpComponentResource.groupType---------->" + groupType);
			LOGGER.debug("getExistGrpComponentResource.groupId---------->" + groupId);
		}
		
		try{

		if (groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_COLOR)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getExistGrpComponentResource. In SCG");
			}

				existComponentDetails = groupingService.getExistSplitColorDetails(groupId);
			
			existComponentDetails = groupingService.prepareListForView(existComponentDetails);
		} else if (groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_SKU)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getExistGrpComponentResource. In SSG");
			}
			existComponentDetails = groupingService.getExistSplitSkuDetails(groupId);
			existComponentDetails = groupingService.prepareListForView(existComponentDetails);
			
		} else if (groupType.equals(GroupingConstants.GROUP_TYPE_CONSOLIDATE_PRODUCT)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getExistGrpComponentResource. In CPG");
			}
			existCPGDetails = groupingService.getExistCPGDetails(groupId);
		} else if (groupType.equals(GroupingConstants.GROUP_TYPE_GROUP_BY_SIZE)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getExistGrpComponentResource. In GBS");
			}
		}

		/** Code to generate response to display Search result in JSP **/
		if (groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_COLOR) || groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_SKU)) {
			if (existComponentDetails.isEmpty()) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_NO_DATA);
			}
			totalRecords = String.valueOf(existComponentDetails.size());
			jsonObj = getSplitGrpJsonResponse(message, totalRecords, defaultSortCol, defaultSortOrder, existComponentDetails);
			
		}else if (groupType.equals(GroupingConstants.GROUP_TYPE_CONSOLIDATE_PRODUCT)) {
			if (existComponentDetails.isEmpty()) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_NO_DATA);
			}
			totalRecords = String.valueOf(existCPGDetails.size());
			jsonObj = getCPGGrpJsonResponse(message, totalRecords, defaultSortCol, defaultSortOrder, existCPGDetails);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("existComponentDetails JsonResponse-->" + jsonObj);
		}

			if(jsonObj!=null) {
			response.getWriter().write(jsonObj.toString());
			}
		} catch (PEPServiceException e) {
			LOGGER.error("GroupingControlle:getExistGrpComponentResource:PEPServiceException------------>" + e);
		} catch (PEPPersistencyException e) {
			LOGGER.error("GroupingControlle:getExistGrpComponentResource:PEPPersistencyException------------>" + e);
		} catch (IOException e) {
			LOGGER.error("GroupingControlle:getExistGrpComponentResource:IOException------------>" + e);
		}

		LOGGER.info("GroupingControlle:getExistGrpComponentResource ResourceRequest:Exit------------>");
		return modelAndView;
	}

	/** This method is used to get New Group Component details after searching
	 * with the different Criteria.
	 * 
	 * @param request
	 * @param response
	 * @return */
	@SuppressWarnings("unchecked")
	@ResourceMapping("getNewGrpComponent")
	public final ModelAndView getNewGrpComponentResource(final ResourceRequest request, final ResourceResponse response)
			 {
		LOGGER.info("GroupingControlle:getNewGrpComponentResource ResourceRequest:Enter------------>");
		String message = "";
		String vendorStyleNo = "";
		String styleOrin = "";
		String classId = "";
		String deptNoSearch = "";
		String classNoSearch = "";
		String supplierSiteIdSearch = "";
		String upcNoSearch = "";
		ModelAndView modelAndView = null;


		Properties prop = PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);

		String groupType = GroupingUtil.checkNull(request.getParameter(GroupingConstants.GROUP_TYPE));
		String groupId = GroupingUtil.checkNull(request.getParameter(GroupingConstants.GROUP_ID));
		try{
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getNewGrpComponentResource.groupType---------->" + groupType);
			LOGGER.debug("getNewGrpComponentResource.groupId---------->" + groupId);
		}

		if (groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_COLOR)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("in fetch split color attribute getNewGrpComponentResource-->");
			}
			vendorStyleNo = request.getParameter(GroupingConstants.VENDOR_STYLE_NO);
			styleOrin = request.getParameter(GroupingConstants.STYLE_ORIN_NO_SEARCH_PARAM);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getNewGrpComponentResource.Search Attribute: vendorStyleNo-->" + vendorStyleNo + "  styleOrin-->"
						+ styleOrin);
			}
			List<GroupAttributeForm> getSplitColorDetailsList = groupingService.getSplitColorDetails(vendorStyleNo, styleOrin);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of getNewGrpComponentResource.getSplitColorDetailsList-->" + getSplitColorDetailsList.size());
			}

			/** Validate Split Attribute List **/

			String errorCode = "";
			List<GroupAttributeForm> updatedSplitColorDetailsList = new ArrayList<GroupAttributeForm>();
			Map<String, List<GroupAttributeForm>> updatedSplitColorDetailsMap = groupingService
					.validateSCGAttributeDetails(getSplitColorDetailsList);
			Iterator<Entry<String, List<GroupAttributeForm>>> entries = updatedSplitColorDetailsMap.entrySet().iterator();
			while (entries.hasNext()) {
				@SuppressWarnings("rawtypes")
				Entry thisEntry = (Entry) entries.next();
				errorCode = (String) thisEntry.getKey();
				updatedSplitColorDetailsList = (List<GroupAttributeForm>) thisEntry.getValue();
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of getNewGrpComponentResource.errorCode-->" + errorCode);
				LOGGER.debug("Size of getNewGrpComponentResource-->" + updatedSplitColorDetailsList.size());
			}
			if (getSplitColorDetailsList.isEmpty() || "noData".equals(errorCode)) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_NO_DATA); // "No record found!"
			} else if (updatedSplitColorDetailsList.isEmpty() && "notCompleted".equals(errorCode)) {
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

				response.getWriter().write(jsonObj.toString());
			
			modelAndView = new ModelAndView(null);

		} else if (groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_SKU)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("in fetch split SKU attribute getNewGrpComponentResource-->");
			}

			/** Fetch SKU details for Split group **/
			vendorStyleNo = request.getParameter(GroupingConstants.VENDOR_STYLE_NO);
			styleOrin = request.getParameter(GroupingConstants.STYLE_ORIN_NO_SEARCH_PARAM);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getNewGrpComponentResource.Search Attribute: vendorStyleNo-->" + vendorStyleNo + "  styleOrin-->"
						+ styleOrin);
			}
			List<GroupAttributeForm> getSplitSKUDetailsList = groupingService.getSplitSKUDetails(vendorStyleNo, styleOrin);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of getNewGrpComponentResource.getSplitSKUDetailsList-->" + getSplitSKUDetailsList.size());
			}

			/** Validate Split Attribute List **/
			String errorCode = "";
			List<GroupAttributeForm> updatedSplitSKUDetailsList = new ArrayList<GroupAttributeForm>();
			Map<String, List<GroupAttributeForm>> updatedSplitSKUDetailsMap = groupingService
					.validateSSGAttributeDetails(getSplitSKUDetailsList);
			Iterator<Entry<String, List<GroupAttributeForm>>> entries = updatedSplitSKUDetailsMap.entrySet().iterator();
			while (entries.hasNext()) {
				@SuppressWarnings("rawtypes")
				Entry thisEntry = (Entry) entries.next();
				errorCode = (String) thisEntry.getKey();
				updatedSplitSKUDetailsList = (List<GroupAttributeForm>) thisEntry.getValue();
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of getNewGrpComponentResource.errorCode-->" + errorCode);
				LOGGER.debug("Size of getNewGrpComponentResource.updatedSplitSKUDetailsList-->" + updatedSplitSKUDetailsList.size());
			}
			if (getSplitSKUDetailsList.isEmpty() || "noData".equals(errorCode)) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_NO_DATA); // "No record found!"
			} else if (updatedSplitSKUDetailsList.isEmpty() && "notCompleted".equals(errorCode)) {
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

				response.getWriter().write(jsonObj.toString());
			
			modelAndView = new ModelAndView(null);
		} else if (groupType.equals(GroupingConstants.GROUP_TYPE_CONSOLIDATE_PRODUCT)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("in fetch CPG attribute getNewGrpComponentResource-->");
			}
			String totalRecords = "0";
			List<StyleAttributeForm> getCPGDetailsList = null;
			String defaultSortCol = GroupingConstants.STYLE_ORIN_NO;
			String defaultSortOrder = GroupingConstants.SORT_ASC;

			/** Fetch SKU details for Split group **/
			vendorStyleNo = request.getParameter(GroupingConstants.VENDOR_STYLE_NO);
			styleOrin = request.getParameter(GroupingConstants.STYLE_ORIN_NO_SEARCH_PARAM);
			classId = GroupingUtil.checkNull(request.getParameter(GroupingConstants.CLASS_ID)); // TODO need from UI. Available in JSON header
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("classId-->"+classId);
			}
			deptNoSearch = request.getParameter(GroupingConstants.DEPT_SEARCH);
			classNoSearch = request.getParameter(GroupingConstants.CLASS_SEARCH);
			supplierSiteIdSearch = request.getParameter(GroupingConstants.SUPPLIER_SEARCH);
			upcNoSearch = request.getParameter(GroupingConstants.UPC_SEARCH);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("getNewCPGDetails.Search Attribute: vendorStyleNo-->" + vendorStyleNo + "  styleOrin-->"
						+ styleOrin + " groupId-->" + groupId + " deptNoSearch-->" + deptNoSearch + " classNoSearch-->" + classNoSearch 
						+ " supplierSiteIdSearch-->" + supplierSiteIdSearch + " upcNoSearch-->" + upcNoSearch);
			}

			getCPGDetailsList = groupingService.getNewCPGDetails(vendorStyleNo, styleOrin, deptNoSearch, classNoSearch,
					supplierSiteIdSearch, upcNoSearch, groupId);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Size of getNewGrpComponentResource.getNewCPGDetails-->" + getCPGDetailsList.size());
			}

			/** Validate CPG Attribute List **/
			if (getCPGDetailsList.isEmpty()) {
				message = prop.getProperty(GroupingConstants.MESSAGE_SPLITGROUP_VALIDATION_NO_DATA);
			}
			
			/** set in session to call add attribute webservice after creating
			 * group **/
			request.getPortletSession().setAttribute(GroupingConstants.SELECTED_ATTRIBUTE_LIST, getCPGDetailsList);

			/** Code to generate response to display Search result in JSP **/
			totalRecords = String.valueOf(getCPGDetailsList.size());

			JSONObject jsonObj = getCPGGrpJsonResponse(message, totalRecords, defaultSortCol, defaultSortOrder,
					getCPGDetailsList);
				response.getWriter().write(jsonObj.toString());
			
			modelAndView = new ModelAndView(null);
			}
		} catch (IOException e) {
			LOGGER.error("Exception in getNewGrpComponentResource:IOException------------>" + e);
		} catch (PEPServiceException e) {
			LOGGER.error("Exception in getNewGrpComponentResource:PEPServiceException------------>" + e);
		} catch (PEPPersistencyException e) {
			LOGGER.error("Exception in getNewGrpComponentResource:PEPPersistencyException------------>" + e);
		} catch (PEPFetchException e) {
			LOGGER.error("Exception in getNewGrpComponentResource:PEPFetchException------------>" + e);
		}

		LOGGER.info("GroupingControlle:getNewGrpComponentResource ResourceRequest:Exit------------>");
		return modelAndView;
	}

	/** This method is used to call add Component Service to add newly selected
	 * Component from Add Component Page and fetch data from database.
	 * 
	 * @param request
	 * @param response
	 * @return */
	@ResourceMapping("addComponentToGroup")
	public final ModelAndView addComponentToGroup(final ResourceRequest request, final ResourceResponse response) {
		LOGGER.info("GroupingControlle:addComponentToGroup ResourceRequest:Enter------------>");
		ModelAndView modelAndView = null;
		String[] selectedItemsArr = null;
		String updatedBy = "";
		CreateGroupForm createGroupForm = new CreateGroupForm();

		try {
			
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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(" handleSplitAttrSearchRequest selectedItems --> " + selectedItems);
		}
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

		if (GroupingConstants.GROUP_TYPE_SPLIT_COLOR.equals(groupType) && null != selectedItems
				&& null != selectedItemsArr) {

			@SuppressWarnings("unchecked")
			List<GroupAttributeForm> updatedSplitColorDetailsList = (List<GroupAttributeForm>) request.getPortletSession()
					.getAttribute(GroupingConstants.SELECTED_ATTRIBUTE_LIST);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("All Color Attribute List Size()-->" + updatedSplitColorDetailsList.size());
			}
			selectedSplitAttributeList = groupingService.getSelectedColorAttributeList(updatedSplitColorDetailsList, selectedItemsArr,
					defaultSelectedAttr);
			
		}
		if (GroupingConstants.GROUP_TYPE_SPLIT_SKU.equals(groupType) && null != selectedItems
				&& null != selectedItemsArr) {

			@SuppressWarnings("unchecked")
			List<GroupAttributeForm> updatedSplitSkuDetailsList = (List<GroupAttributeForm>) request.getPortletSession().getAttribute(
					GroupingConstants.SELECTED_ATTRIBUTE_LIST);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("All SKU Attribute List Size()-->" + updatedSplitSkuDetailsList.size());
			}
			
				selectedSplitAttributeList = groupingService.getSelectedSKUAttributeList(updatedSplitSkuDetailsList, selectedItemsArr,
						defaultSelectedAttr);
			
		}
		if (GroupingConstants.GROUP_TYPE_CONSOLIDATE_PRODUCT.equals(groupType) ) {
			
			String existClassId = request.getParameter(GroupingConstants.CLASS_ID);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Existing Attribute Class Id-->" + existClassId);
			}
			
			@SuppressWarnings("unchecked")
			List<StyleAttributeForm> getCPGDetailsList = (List<StyleAttributeForm>) request.getPortletSession().getAttribute(
					GroupingConstants.SELECTED_ATTRIBUTE_LIST);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("All CPG Attribute List Size()-->" + getCPGDetailsList.size());
			}
			List<StyleAttributeForm> getCPGSelectedAttrbuteList = groupingService.getSelectedCPGAttributeList(getCPGDetailsList, selectedItemsArr,
					defaultSelectedAttr);
			

			String cpgValidationMsg = groupingService.validateCPGAttributeDetails(existClassId, getCPGSelectedAttrbuteList);
			
			if("".equals(cpgValidationMsg)){
				createGroupForm = groupingService.addCPGComponentToGroup(groupId, updatedBy, groupType, getCPGSelectedAttrbuteList);
			}else{
				createGroupForm.setGroupId(groupId);
				createGroupForm.setGroupType(groupType);
				createGroupForm.setGroupCretionMsg(cpgValidationMsg);
				createGroupForm.setGroupCreationStatus(GroupingConstants.COMPONENT_ADDITION_FAILED);
			}
		}
		
		// Call Service to add attribute
		if (GroupingConstants.GROUP_TYPE_SPLIT_COLOR.equals(groupType) || (GroupingConstants.GROUP_TYPE_SPLIT_SKU).equals(groupType)){
			createGroupForm = groupingService.addComponentToGroup(groupId, updatedBy, groupType,
					selectedSplitAttributeList);
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put(GroupingConstants.GROUP_ID, createGroupForm.getGroupId());
		jsonObj.put(GroupingConstants.GROUP_TYPE, createGroupForm.getGroupType());
		jsonObj.put(GroupingConstants.GROUP_CREATION_MSG, createGroupForm.getGroupCretionMsg());
		jsonObj.put(GroupingConstants.GROUP_CREATION_STATUS_CODE, createGroupForm.getGroupCreationStatus());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("jsonObj-->" + jsonObj);
		}

		response.getWriter().write(jsonObj.toString());

		modelAndView = new ModelAndView(null);
		LOGGER.info("GroupingControlle:addComponentToGroup:Exit------------>");
		
		}
		catch (PEPServiceException e) {
			LOGGER.error("GroupingControlle:addComponentToGroup:PEPServiceException------------>" + e);
		} catch (PEPPersistencyException e) {
			LOGGER.error("GroupingControlle:addComponentToGroup:PEPPersistencyException------------>" + e);
		} catch (IOException e) {
			LOGGER.error("GroupingControlle:addComponentToGroup:IOException------------>" + e);
		}
		catch (Exception e) {
			LOGGER.error("GroupingControlle:addComponentToGroup:Exception------------>" + e);
		}
		return modelAndView;
	}
	
	/**
	 * Edit Grouping.
	 * @param request
	 * @param response
	 */
	@ResourceMapping("saveEditedGroup")
	public void saveEditedGroup(ResourceRequest request,ResourceResponse response){
		LOGGER.info("Entered saveEditedGroup Method of Grouping Controller");
		CreateGroupForm createGroupForm=new CreateGroupForm();
		createGroupForm.setGroupId(request.getParameter(GroupingConstants.GROUP_ID));
		createGroupForm.setGroupName(request.getParameter(GroupingConstants.GROUP_NAME));
		createGroupForm.setGroupDesc(request.getParameter(GroupingConstants.GROUP_DESC));
		createGroupForm.setGroupStatus(request.getParameter(GroupingConstants.GROUP_STATUS));
		createGroupForm.setGroupLaunchDate(request.getParameter(GroupingConstants.START_DATE));
		createGroupForm.setEndDate(request.getParameter(GroupingConstants.END_DATE));
		String modifiedBy=request.getParameter(GroupingConstants.MODIFIED_BY);
		createGroupForm.setGroupType(request.getParameter(GroupingConstants.GROUP_TYPE));
		String resp="";
		try {
			resp=groupingService.updateGroupHeaderDetails(createGroupForm, modifiedBy);
			response.getWriter().write(resp);
		
		} catch (Exception e) {
			LOGGER.error("GroupingControlle:saveEditedGroup ResourceRequest:Exception------------>" + e);
		}
		
	}
	/** edit Grouping **/
}
