package com.belk.pep.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
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
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.belk.pep.common.model.Common_BelkUser;
import com.belk.pep.common.userdata.UserData;
import com.belk.pep.constants.GroupingConstants;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.form.CreateGroupForm;
import com.belk.pep.form.GroupAttributeForm;
import com.belk.pep.service.GroupingService;
import com.belk.pep.util.PropertyLoader;

/**
 * The Class GroupingController.
 */
@Controller 
@RequestMapping("View")
public class GroupingController  {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(GroupingController.class.getName());



    /** The mv. */
    private ModelAndView mv ;
    
    /** The Grouping Service. */
    private GroupingService groupingService;


    /**
     * @return the groupingService
     */
    public GroupingService getGroupingService() {
        return groupingService;
    }


    /**
     * @param groupingService the groupingService to set
     */
    public void setGroupingService(GroupingService groupingService) {
        this.groupingService = groupingService;
    }


    /**
     * This method will handle the Action from the portlet.
     *
     * @param request the request
     * @param response the response
     * @throws Exception the exception
     */
    /*@ActionMapping(params="action=createAction")
    public void createGroupActionMapping(ActionRequest request, ActionResponse response, @ModelAttribute("createGroupForm") CreateGroupForm createGroupForm)
            throws Exception {

        LOGGER.info("GroupingControlle:createGroup ActionRequest:Enter------------>");
        
        String formSessionKey =  (String)request.getPortletSession().getAttribute("formSessionKey");
        LOGGER.info("handleRenderRequest formSessionKey  ----------------------------->"+formSessionKey);
        String sessionDataKey =  (String)request.getPortletSession().getAttribute("sessionDataKey");
        LOGGER.info(" handleActionRequest sessionDataKey -----------------------------> "+sessionDataKey);
        UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);
        LOGGER.info(" handleActionRequest custuser -----------------------------------> "+custuser);
        String pepUserId = custuser.getBelkUser().getLanId();
        LOGGER.info("This is from Reneder Internal User--------------------->"+pepUserId);

        String groupType = request.getParameter(GroupingConstants.GROUP_TYPE);
        String groupName = request.getParameter(GroupingConstants.GROUP_NAME);
        String groupDesc = request.getParameter(GroupingConstants.GROUP_DESC);
        String startDate = request.getParameter(GroupingConstants.START_DATE); // dd/mm/yyyy
        String endDate = request.getParameter(GroupingConstants.END_DATE); // dd/mm/yyyy
                
        String groupType = createGroupForm.getGroupType();
        String groupName = createGroupForm.getGroupName();
        String groupDesc = createGroupForm.getGroupDesc();
        String startDate = createGroupForm.getGroupLaunchDate(); // dd/mm/yyyy
        String endDate = createGroupForm.getEndDate(); // dd/mm/yyyy
        startDate = (null == startDate ? "" : startDate.trim());
        endDate = (null == endDate ? "" : endDate.trim());
        LOGGER.debug("groupType----------------->"+groupType);
        LOGGER.debug("groupName----------------->"+groupName);
        LOGGER.debug("groupDesc----------------->"+groupDesc);
        LOGGER.debug("startDate----------------->"+startDate);
        LOGGER.debug("endDate----------------->"+endDate);
        
        *//** If request came with create group request **//*
        //CreateGroupForm createGroupForm = new CreateGroupForm();
        List<GroupAttributeForm> selectedSplitAttributeList = null;
        createGroupForm.setGroupType(groupType);
        createGroupForm.setGroupName(groupName);
        createGroupForm.setGroupDesc(groupDesc);
        createGroupForm.setGroupLaunchDate(startDate);
        createGroupForm.setEndDate(endDate);
        createGroupForm.setGroupAttributeFormList(selectedSplitAttributeList);
        
        LOGGER.debug("Before calling createGroup()-->");
        CreateGroupForm createGroupFormRes = createGroup(createGroupForm, pepUserId);
        LOGGER.debug("After calling createGroup()-->getGroupCreationStatus: "+createGroupFormRes.getGroupCreationStatus());
        LOGGER.debug("After calling createGroup()-->getGroupCretionMsg: "+createGroupFormRes.getGroupCretionMsg());
        
        //request.getPortletSession().setAttribute(formSessionKey, renderForm); 
        request.setAttribute("createGroupFormRes", createGroupFormRes);
        response.setRenderParameter("submitRender", "groupLanding");
        
        LOGGER.info("GroupingControlle:createGroup ActionRequest:Exit");
    }*/
    
    
    /**
     * Create Group for All group except SPlit group.
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
   /* @RenderMapping(params="submitRender=groupLanding")
    public ModelAndView renderGroupLandingPage(RenderRequest request,
        RenderResponse response) throws Exception {

        LOGGER.info("GroupingControlle.renderGroupLandingPage - enter");
        System.out.println("GroupingControlleController:renderGroupLandingPage:Enter-->");
        CreateGroupForm createGroupFormRes = (CreateGroupForm)request.getAttribute("createGroupFormRes");
        String createGroupType = createGroupFormRes.getGroupType();
        LOGGER.info("createGroupType----------------->"+createGroupType);
        ModelAndView modelAndView = null;
        System.out.println(" Original Work List ROle -----------------> "+ ((WorkListDisplayForm)request.getPortletSession().getAttribute(formSessionKey)).getRoleName());
        modelAndView = new ModelAndView(GroupingConstants.GROUPING_ADD_COMPONENT);
        modelAndView.addObject(GroupingConstants.CREATE_GROUP_FORM, createGroupFormRes);
        LOGGER.info("GroupingControlle:renderGroupLandingPage:Exit");
        return modelAndView;
    }*/
    
    
    /**
     * Create URL to render page for SPLIT Color group 
     * @param renderRequest
     * @param renderResponse
     * @return
     */
    @RenderMapping(params="groupingTypeSplitColor=splitColor")
    public ModelAndView splitColorRenderHandler(RenderRequest renderRequest, RenderResponse renderResponse){
        //do some processing here
        LOGGER.info("GroupingControlle:splitRenderHandler:Enter");
        ModelAndView mv=new ModelAndView(GroupingConstants.GROUPING_CREATE_SPLIT_COLOR_GROUP);
        LOGGER.info("GroupingControlle:splitRenderHandler:Exit");
        return mv;
    }
    

    /**
     * Create URL to render page for SPLIT SKU group 
     * @param renderRequest
     * @param renderResponse
     * @return
     */
    @RenderMapping(params="groupingTypeSplitSKU=splitSKU")
    public ModelAndView splitSKURenderHandler(RenderRequest renderRequest, RenderResponse renderResponse){
        //do some processing here
        LOGGER.info("GroupingControlle:splitSKURenderHandler:Enter");
        ModelAndView mv=new ModelAndView(GroupingConstants.GROUPING_CREATE_SPLIT_SKU_GROUP);
        LOGGER.info("GroupingControlle:splitSKURenderHandler:Exit");
        return mv;
    }
    

    /**
     * 
     * Default Render method of Grouping Portlet when page loads
     */
    @RenderMapping
    public ModelAndView handleRenderRequest(RenderRequest request,
        RenderResponse response) throws Exception {

        LOGGER.info("GroupingControlle:handleRenderRequest:Enter");
        System.out.println("GroupingControlleController:handleRenderRequest:Enter");
        String createGroupType = request.getParameter("groupTypeSelector");
        LOGGER.info("createGroupType----------------->"+createGroupType);
        ModelAndView modelAndView = null;
        modelAndView = new ModelAndView(GroupingConstants.GROUPING_PAGE); // groupingPage.jsp
        
        Properties prop =PropertyLoader.getPropertyLoader(GroupingConstants.GROUPING_PROPERTIES_FILE_NAME);  
        String groups = prop.getProperty(GroupingConstants.GROUP_TYPES);        
        String groupTypes [] = groups.split(",");
        Map<String, String> groupMap = new TreeMap<String, String>();
        for(int count = 0; count < groupTypes.length; count++)
        {            
            String key = groupTypes[count].split("-")[0];
            String value = groupTypes[count].split("-")[1];
            groupMap.put(key, value);
        }
        modelAndView.addObject("groupTypesMap", groupMap);
        
        LOGGER.info("GroupingControlle:handleRenderRequest:Exit");
        return modelAndView;
    }



    /**
     * This method will handle the Event request and to fetch the user details from the login portlet
     * 
     */
    @EventMapping
    public void handleEventRequest(EventRequest request, EventResponse response)
            throws Exception {
        LOGGER.info("GroupingController:handleEventRequest:Enter "); 
        System.out.println("GroupingController:handleEventRequest:Enter ");
        Event event = request.getEvent();
        String loggedInUser = "";

        /*if(event.getName()!=null ){
            if(event.getName().equals("LoggedInUserDetailsOBJ")){
                LOGGER.info("Event recieved successfully");
            }
        }*/

        if(event.getName()!=null ){
            LOGGER.info("GroupingController:handlingPagination:Enter 1 "+event.getName()); 
            if(event.getName().equals(GroupingConstants.USER_DATA_OBJ)){ 
                LOGGER.info("GroupingController:handlingPagination:Enter 2 "); 
                UserData custuser= (UserData) event.getValue();


                if(null!=custuser){     
                    Common_BelkUser belkUser = ( Common_BelkUser)custuser.getBelkUser();
                    LOGGER.info("GroupingController:handlingPagination:Enter 2 ");
                    if(custuser.getVpUser()!=null)
                    {
                        if(null!=custuser.getVpUser().getUserEmailAddress()){
                            LOGGER.info("This is from Event Email Id********************"+custuser.getVpUser().getUserEmailAddress()); 
                            loggedInUser = custuser.getVpUser().getUserEmailAddress();
                        }else if(null!=custuser.getRoleName()){
                            LOGGER.info("This is from Event Role name********************"+custuser.getRoleName());    
                        }else if(null!=custuser.getAccessRight()){
                            LOGGER.info("This is from Access********************"+custuser.getAccessRight());
                        }

                    }
                    if(null!=belkUser) {
                        LOGGER.info("belkUser.getLanId() 1111**************");
                        if(null!=belkUser.getLanId()) {
                            LOGGER.info("belkUser.getLanId() ******************"+belkUser.getLanId());  
                            loggedInUser = belkUser.getLanId();
                        }
                    }

                    String sessionDataKey = (GroupingConstants.USER_DATA+request.getPortletSession().getId()+loggedInUser);
                    String formSessionKey = request.getPortletSession().getId()+loggedInUser;
                    String listSessionKey = "LIST"+request.getPortletSession().getId()+loggedInUser;

                    LOGGER.info(" loggedInUser **************"+formSessionKey);

                    request.getPortletSession().setAttribute("formSessionKey", formSessionKey);
                    request.getPortletSession().setAttribute("sessionDataKey", sessionDataKey);
                    request.getPortletSession().setAttribute(sessionDataKey,custuser);
                }
            }

        }
    }


    /**
     * This method will take care of handleResourceRequest, handles all Ajax calls
     */
    @ResourceMapping
    public ModelAndView handleResourceRequest(ResourceRequest request,
        ResourceResponse response) throws Exception {
    	LOGGER.info("Entering handleResourceRequest-->");
    	String vendorStyleNo = "";
    	String styleOrin = "";
    	String updatedBy = "";

        String formSessionKey =  (String)request.getPortletSession().getAttribute("formSessionKey");
        CreateGroupForm createGroupForm =  (CreateGroupForm)request.getPortletSession().getAttribute(formSessionKey);
        String sessionDataKey =  (String)request.getPortletSession().getAttribute("sessionDataKey");
        UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);

        LOGGER.debug(" handleResourceRequest formSessionKey --------------------------------------------------------------> "+formSessionKey);
        LOGGER.debug(" handleResourceRequest resourceForm ----------------------------------------------------------------> "+createGroupForm);
        LOGGER.debug(" handleResourceRequest sessionDataKey --------------------------------------------------------------> "+sessionDataKey);
        LOGGER.info(" handleResourceRequest custuser ---------------------------------------------------------------------> "+custuser);
        ModelAndView view=new ModelAndView("groupingPage");
        

        if(custuser !=null){
            LOGGER.info("userData---->in activate");
            if(custuser.isInternal()){                     
                updatedBy = custuser.getBelkUser().getLanId();
                LOGGER.info("Activate Internal user--->"+updatedBy);
            }
        }
        
        /** Fetch Color details for Split group **/
        String strFromPage =request.getParameter("fromPage");
        LOGGER.info("in fetch split color attribute strFromPage--->"+strFromPage);
        if(strFromPage.equals("SearchColor")){
            LOGGER.debug("in fetch split color attribute-->");
            vendorStyleNo = request.getParameter(GroupingConstants.VENDOR_STYLE_NO);
            styleOrin = request.getParameter(GroupingConstants.STYLE_ORIN_NO);
            //List<GroupAttributeForm> getSplitColorDetailsList = groupingDelegate.getSplitColorDetails(vendorStyleNo, styleOrin);
            List<GroupAttributeForm> getSplitColorDetailsList = groupingService.getSplitColorDetails(vendorStyleNo, styleOrin);
            LOGGER.debug("Size of getSplitColorDetailsList-->"+ getSplitColorDetailsList.size());
            
            /*JSONObject jsonObj = getSCGJsonResponse(String totalRecords, String defaultSortCol, String defaultSortOrder, selectedSplitAttributeList);
            try {
                response.getWriter().write(jsonObj.toString());
            }
            catch (IOException e) {
                LOGGER.info("GroupingControlle:handleCreateGroupForm ResourceRequest:Exception------------>"+e.getMessage());
                e.printStackTrace();
            }*/
            
        }
        else{
            /** Fetch SKU details for Split group **/
            LOGGER.debug("in fetch split SKU attribute-->");
            vendorStyleNo = request.getParameter(GroupingConstants.VENDOR_STYLE_NO);
            styleOrin = request.getParameter(GroupingConstants.STYLE_ORIN_NO);
            //List<GroupAttributeForm> getSplitSKUDetailsList = groupingDelegate.getSplitSKUDetails(vendorStyleNo, styleOrin);
            List<GroupAttributeForm> getSplitSKUDetailsList = groupingService.getSplitSKUDetails(vendorStyleNo, styleOrin);      
            LOGGER.debug("Size of getSplitSKUDetailsList-->"+ getSplitSKUDetailsList.size());
            
            /*JSONObject jsonObj = getSCGJsonResponse(String totalRecords, String defaultSortCol, String defaultSortOrder, selectedSplitAttributeList);
            try {
                response.getWriter().write(jsonObj.toString());
            }
            catch (IOException e) {
                LOGGER.info("GroupingControlle:handleCreateGroupForm ResourceRequest:Exception------------>"+e.getMessage());
                e.printStackTrace();
            }*/
        }

        LOGGER.info("Exit handleResourceRequest-->");
        return view;
    }
    

    /**
     * Method to create JSON object to display search result in jsp for SCG and SSG
     * @param petId
     * @param petStatus
     * @return
     * @author Cognizant
     */
    public JSONObject getSCGJsonResponse(String totalRecords, String defaultSortCol, String defaultSortOrder, 
        List<GroupAttributeForm> selectedSplitAttributeList) {

        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObjComponent = new JSONObject();
        GroupAttributeForm groupAttributeForm = null;
        JSONArray jsonArray = new JSONArray();
        try {       
            for(int i = 0; i < selectedSplitAttributeList.size(); i++){
                groupAttributeForm = selectedSplitAttributeList.get(i);
                jsonObjComponent.put(GroupingConstants.STYLE_ORIN_NO, groupAttributeForm.getOrinNumber());
                jsonObjComponent.put(GroupingConstants.VENDOR_STYLE_NO, groupAttributeForm.getStyleNumber());
                jsonObjComponent.put(GroupingConstants.PRODUCT_NAME, groupAttributeForm.getProdName());
                jsonObjComponent.put(GroupingConstants.COLOR_CODE, groupAttributeForm.getColorCode());
                jsonObjComponent.put(GroupingConstants.COLOR_NAME, groupAttributeForm.getColorName());
                jsonObjComponent.put(GroupingConstants.COMPONENT_IS_DEFAULT, groupAttributeForm.getIsDefault());
                String isAlreadyInGroup = groupAttributeForm.getIsAlreadyInGroup();
                isAlreadyInGroup = (null == isAlreadyInGroup ? "No" : ("N").equalsIgnoreCase(isAlreadyInGroup) ? "No" : ("Y").equalsIgnoreCase(isAlreadyInGroup) ? "Yes" : "No");
                jsonObjComponent.put(GroupingConstants.ALREADY_IN_GROUP, isAlreadyInGroup);
                jsonObjComponent.put(GroupingConstants.COMPONENT_SIZE, groupAttributeForm.getSize()); // Only for Split SKU Group
                jsonArray.put(jsonObjComponent);
            }
            
            jsonObj.put(GroupingConstants.SPLIT_GROUP_TOTAL_RECORDS, totalRecords);
            jsonObj.put(GroupingConstants.SPLIT_GROUP_DEFAULT_SORT_COL, defaultSortCol);
            jsonObj.put(GroupingConstants.SPLIT_GROUP_DEFAULT_SORT_ORDER, defaultSortOrder);
            jsonObj.put(GroupingConstants.COMPONENT_LIST, jsonArray);
            
        } catch (JSONException e) {
            LOGGER.info("Exeception in parsing the jsonObj");
            e.printStackTrace();
        }
        return jsonObj;
     }
    
    
    /**
     * This method is used to create group 
     * @param request
     * @param response
     * @return
     */
    @ResourceMapping("submitCreateGroupForm")
    public ModelAndView handleCreateGroupForm(ResourceRequest request, ResourceResponse response   ){
        LOGGER.info("GroupingControlle:handleCreateGroupForm ResourceRequest:Enter------------>");
        
        String formSessionKey =  (String)request.getPortletSession().getAttribute("formSessionKey");
        LOGGER.info("handleRenderRequest formSessionKey  ----------------------------->"+formSessionKey);
        String sessionDataKey =  (String)request.getPortletSession().getAttribute("sessionDataKey");
        LOGGER.info(" handleActionRequest sessionDataKey -----------------------------> "+sessionDataKey);
        UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);
        LOGGER.info(" handleActionRequest custuser -----------------------------------> "+custuser);
        String pepUserId = custuser.getBelkUser().getLanId();
        LOGGER.info("This is from Reneder Internal User--------------------->"+pepUserId);

        String groupType = request.getParameter(GroupingConstants.GROUP_TYPE);
        String groupName = request.getParameter(GroupingConstants.GROUP_NAME);
        String groupDesc = request.getParameter(GroupingConstants.GROUP_DESC);
        String startDate = request.getParameter(GroupingConstants.START_DATE); // dd/mm/yyyy
        String endDate = request.getParameter(GroupingConstants.END_DATE); // dd/mm/yyyy
                
        /*String groupType = createGroupForm.getGroupType();
        String groupName = createGroupForm.getGroupName();
        String groupDesc = createGroupForm.getGroupDesc();
        String startDate = createGroupForm.getGroupLaunchDate(); // dd/mm/yyyy
        String endDate = createGroupForm.getEndDate();*/ // dd/mm/yyyy
        
        startDate = (null == startDate ? "" : startDate.trim());
        endDate = (null == endDate ? "" : endDate.trim());
        LOGGER.debug("groupType----------------->"+groupType);
        LOGGER.debug("groupName----------------->"+groupName);
        LOGGER.debug("groupDesc----------------->"+groupDesc);
        LOGGER.debug("startDate----------------->"+startDate);
        LOGGER.debug("endDate----------------->"+endDate);
        
        /** If request came with create group request **/
        CreateGroupForm createGroupForm = new CreateGroupForm();
        List<GroupAttributeForm> selectedSplitAttributeList = new ArrayList<GroupAttributeForm>();
        
        if(null != groupType && ((GroupingConstants.GROUP_TYPE_SPLIT_COLOR).equals(groupType) || (GroupingConstants.GROUP_TYPE_SPLIT_SKU).equals(groupType))){
            // TODO
            /** Add Selected Attribute to List (selectedSplitAttributeList) for Split Color and Group **/
            
            //createGroupForm.setGroupAttributeFormList(selectedSplitAttributeList);
        }

        createGroupForm.setGroupType(groupType);
        createGroupForm.setGroupName(groupName);
        createGroupForm.setGroupDesc(groupDesc);
        createGroupForm.setGroupLaunchDate(startDate);
        createGroupForm.setEndDate(endDate);
        createGroupForm.setGroupAttributeFormList(selectedSplitAttributeList);
        
        LOGGER.debug("Before calling createGroup()-->");
        CreateGroupForm createGroupFormRes = createGroup(createGroupForm, pepUserId);
        LOGGER.debug("After calling createGroup()-->getGroupCreationStatus: "+createGroupFormRes.getGroupCreationStatus());
        LOGGER.debug("After calling createGroup()-->getGroupCretionMsg: "+createGroupFormRes.getGroupCretionMsg());
        
        
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(GroupingConstants.GROUP_ID, createGroupFormRes.getGroupId());
        jsonObj.put(GroupingConstants.GROUP_NAME, createGroupFormRes.getGroupName());
        jsonObj.put(GroupingConstants.GROUP_TYPE, createGroupFormRes.getGroupType());
        jsonObj.put(GroupingConstants.GROUP_DESC, createGroupFormRes.getGroupDesc());
        jsonObj.put(GroupingConstants.START_DATE, createGroupFormRes.getGroupLaunchDate());
        jsonObj.put(GroupingConstants.START_DATE, createGroupFormRes.getEndDate());
        jsonObj.put(GroupingConstants.GROUP_CREATION_MSG, createGroupFormRes.getGroupCretionMsg());
        jsonObj.put(GroupingConstants.GROUP_CREATION_STATUS_CODE, createGroupFormRes.getGroupCreationStatus());
        //createGroupForm.setGroupAttributeFormList(createGroupDTO.getGroupAttributeDTOList());
        
        //request.getPortletSession().setAttribute(formSessionKey, renderForm); 
        //request.setAttribute("createGroupFormRes", createGroupFormRes);
        LOGGER.debug("jsonObj-->"+ jsonObj);
        
        //ModelAndView modelAndView=new ModelAndView(GroupingConstants.GROUPING_ADD_COMPONENT);
        //modelAndView.addObject(GroupingConstants.CREATE_GROUP_FORM, jsonObj);
        //request.setAttribute(GroupingConstants.CREATE_GROUP_FORM, jsonObj);
        
        ModelAndView modelAndView = null;
        try {
            response.getWriter().write(jsonObj.toString());
        }
        catch (IOException e) {
            LOGGER.info("GroupingControlle:handleCreateGroupForm ResourceRequest:Exception------------>"+e.getMessage());
            e.printStackTrace();
        }
        
        LOGGER.info("GroupingControlle:handleCreateGroupForm ResourceRequest:Exit------------>");
        return modelAndView;
    }
    

    /**
     * Create URL to render page for Create Group successfully 
     * @param renderRequest
     * @param renderResponse
     * @return
     */
    @RenderMapping(params="createGroupSuccessRender=CreateGrpSuccess")
    public ModelAndView createGroupSuccessRender(RenderRequest renderRequest, RenderResponse renderResponse){
        //do some processing here
        LOGGER.info("GroupingControlle:createGroupSuccessRender:Enter");
        ModelAndView mv=new ModelAndView(GroupingConstants.GROUPING_ADD_COMPONENT);
        LOGGER.info("GroupingControlle:createGroupSuccessRender:Exit");
        return mv;
    }
    
    
    /**
     * This method is used to call Group Creation Service and fetch data from database 
     * @param request
     * @author AFUPYB3
     */
    private CreateGroupForm createGroup(CreateGroupForm createGroupForm, String updatedBy) {
        LOGGER.info("Entering:: createGroup method controller");

        //String responseMsg = "";
        JSONObject jsonStyle = null;
        //JSONArray jsonArray = new JSONArray();
        CreateGroupForm createGroupFormRes = null;
        
        try{
            jsonStyle = populateCreateGroupJson(createGroupForm, updatedBy);
            LOGGER.info("Create Group Json Style-->"+jsonStyle);
            //jsonArray.put(jsonStyle);
            LOGGER.debug("json Object createGroup Group Name--> "+ jsonStyle.getString("groupName"));
        }catch (Exception e) {                    
            LOGGER.info("inside catch for createGroup()-->controller");
            e.printStackTrace();
        }
        try {
            //createGroupFormRes = groupingDelegate.saveGroupHeaderDetails(jsonStyle, updatedBy, createGroupForm.getGroupAttributeFormList());
            createGroupFormRes = groupingService.saveGroupHeaderDetails(jsonStyle, updatedBy, createGroupForm.getGroupAttributeFormList());

            LOGGER.info("responseMsg_code Controller createGroup-->" +createGroupFormRes.getGroupCretionMsg());
        }catch(PEPFetchException eService){
            LOGGER.info("Exception Block in Controller::32");
            eService.printStackTrace();                    
        }catch (Exception e) {
            LOGGER.info("Exception Block in Controller::21");
            e.printStackTrace();
        }
        LOGGER.info("Exiting--> createGroup method controller");
        
        return createGroupFormRes;
    } //End Group Creation
    
    
    /**
     * Method to pass JSON Array to call the Create Group service
     * @param petId
     * @param petStatus
     * @return
     * @author Cognizant
     */
    public JSONObject populateCreateGroupJson(CreateGroupForm createGroupForm, String updatedBy) {
        JSONObject jsonObj = new JSONObject();
        try {          
            jsonObj.put(GroupingConstants.GROUP_NAME, createGroupForm.getGroupName());
            jsonObj.put(GroupingConstants.GROUP_TYPE, createGroupForm.getGroupType());
            jsonObj.put(GroupingConstants.GROUP_DESC, createGroupForm.getGroupDesc());
            jsonObj.put(GroupingConstants.START_DATE, createGroupForm.getGroupLaunchDate());
            jsonObj.put(GroupingConstants.END_DATE, createGroupForm.getEndDate());
            jsonObj.put(GroupingConstants.CREATED_BY, updatedBy);
            jsonObj.put(GroupingConstants.GROUP_STATUS, "");
        } catch (JSONException e) {
            LOGGER.info("Exeception in parsing the jsonObj");
            e.printStackTrace();
        }
        return jsonObj;
     }

}
