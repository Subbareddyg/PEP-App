package com.belk.pep.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.Serializable;

import com.belk.pep.common.model.PageAnchorDetails;
import com.belk.pep.util.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.belk.pep.model.StyleColor;

import javax.portlet.*;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.portlet.mvc.Controller;
import org.springframework.web.portlet.mvc.EventAwareController;
import org.springframework.web.portlet.mvc.ResourceAwareController;

import com.belk.pep.common.model.Common_BelkUser;
import com.belk.pep.common.model.ContentPetDetails;
import com.belk.pep.common.model.ImageDetails;
import com.belk.pep.common.userdata.UserData;
import com.belk.pep.constants.WorkListDisplayConstants;
import com.belk.pep.delegate.WorkListDisplayDelegate;
import com.belk.pep.domain.PepDepartment;
import com.belk.pep.domain.PepDepartmentPK;
import com.belk.pep.domain.PetLock;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.WorkListDisplayForm;
import com.belk.pep.model.AdvanceSearch;
import com.belk.pep.model.ContentStatusDropValues;
import com.belk.pep.model.ImageStatusDropValues;
import com.belk.pep.model.RequestTypeDropValues;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.util.ClassDetails;
import com.belk.pep.util.DepartmentDetails;
import com.belk.pep.util.PropertiesFileLoader;

/**
 * The Class WorkListDisplayController.
 */
public class WorkListDisplayController implements Controller,EventAwareController,ResourceAwareController {
    
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(WorkListDisplayController.class.getName());
    
    /** The work list display delegate. */
    private WorkListDisplayDelegate workListDisplayDelegate;
    
    /** The work list display form. */
   // private WorkListDisplayForm workListDisplayForm;
    
    /** The work flow list. */
    @SuppressWarnings("rawtypes")
  //  private List<WorkFlow> workFlowList;  
    
    /** The mv. */
    private ModelAndView mv ;
    
    /** The acending list. */
    @SuppressWarnings("rawtypes")
    private ArrayList acendingList; 
    
    /** The decending list. */
    @SuppressWarnings("rawtypes")
    private ArrayList decendingList; 
    
   
    
    /**
     * getter method for WorkListDisplay delegate.
     *
     * @return the work list display delegate
     */
    public WorkListDisplayDelegate getWorkListDisplayDelegate() {
        return workListDisplayDelegate;
    }
    
    /**
     * Setter method for delegate.
     *
     * @param workListDisplayDelegate the new work list display delegate
     */
    public void setWorkListDisplayDelegate(
        WorkListDisplayDelegate workListDisplayDelegate) {
        this.workListDisplayDelegate = workListDisplayDelegate;
    }
    
    
    /**
     * Web service to Inactivate Pet
     * @param jsonArray
     * @return
     * @throws Exception
     * @throws PEPFetchException
     */
    private String callInActivateAndActivatePetService(JSONArray jsonArray) throws Exception,
         PEPFetchException {
         
         String responseMsg = null;
         responseMsg = workListDisplayDelegate.callInActivateAndActivatePetService(jsonArray);
         return responseMsg;

     }
    
    /**
     * Web service to ReInitiate Pet
     * @param jsonArray
     * @return
     * @throws Exception
     * @throws PEPFetchException
     */
    private String callReInitiatePetService(JSONArray jsonArray) throws Exception,
         PEPFetchException {
         
         String responseMsg = null;
         responseMsg = workListDisplayDelegate.callReInitiatePetService(jsonArray);
         return responseMsg;

     }
    

    /**
     * This method will handle the Action from the portlet.
     *
     * @param request the request
     * @param response the response
     * @throws Exception the exception
     */

    public void handleActionRequest(ActionRequest request, ActionResponse response)
            throws Exception {
            LOGGER.info("WorkListDisplayPortlet:handleActionRequest:Enter"); 
            
            String imageStatusToPassOnIPC = "";
            String petStatusToPassOnIPC = "";
            
            LOGGER.info("selectedOrin ****************"+request.getParameter("selectedOrin"));
            LOGGER.info("imageStatus ****************"+request.getParameter("imageStatus"));
            LOGGER.info("contentStatus on click of the hyper link for ipc ****************"+request.getParameter("contentStatus"));
    
            String sessionDataKey =  (String)request.getPortletSession().getAttribute("sessionDataKey");//TODO
            UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);//TODO
            
            LOGGER.info(" handleActionRequest sessionDataKey ---------------------------------------------------------------- "+sessionDataKey);
            LOGGER.info(" handleActionRequest custuser ---------------------------------------------------------------- "+custuser);
            
/** Changes to populate ContentPetDetails object for IPC
             * @author AFUSKJ2 6/17/2016
             * **/
            /** Changes Starts **/
            /*LOGGER.info("Search result radio:------- "+request.getParameter("groupingType"));
            LOGGER.info("Grouping ID:------- "+request.getParameter("groupingID"));
            LOGGER.info("Grouping Name:------- "+request.getParameter("groupingName"));*/
            //String searchResult = "";
            //String groupingType = (String)request.getParameter("groupingType");
            //String groupID = (String)request.getParameter("selectedOrin");
            /*String groupName = (String)request.getParameter("groupingName");
            
            if(StringUtils.isNotBlank(groupingType) &&(StringUtils.isNotBlank(groupID) || StringUtils.isNotBlank(groupName))){
                searchResult = "groupingContent";
            }*/
            /** Changes ends**/
            
            //IPC from worklist display portlet to image pet portlet
            if((request.getParameter("imageStatus")!=null && !request.getParameter("imageStatus").isEmpty()) &&
                    (request.getParameter("petStatus")!=null && !request.getParameter("petStatus").isEmpty()) && custuser!=null)
              {
                //code for Inserting PET lock START
                String petOrigin=WorkListDisplayConstants.SEARCH_LOCKED_TYPE_IMAGE;
                if(sessionDataKey != null){
                    custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);
                }
                String pepUserId =null;
                if(custuser!=null){
                    if(custuser.isExternal()){
                        pepUserId= custuser.getVpUser().getUserEmailAddress();
                    }else{
                        pepUserId = custuser.getBelkUser().getLanId();
                    }
                }    
                 workListDisplayDelegate.lockPET(request.getParameter("selectedOrin"), pepUserId,petOrigin);
                 //code for Inserting PET lock END
                 LOGGER.info("selectedOrin image  update  ****************"+request.getParameter("selectedOrin"));
                 ImageDetails imageDetails = new ImageDetails();
                 imageDetails.setUserData(custuser); 
                 imageDetails.setOrinNumber(request.getParameter("selectedOrin"));
                 
                 imageStatusToPassOnIPC = request.getParameter("imageStatus");
                 petStatusToPassOnIPC = request.getParameter("petStatus");
                 
                 imageDetails.setImageStatus(imageStatusToPassOnIPC+"-"+petStatusToPassOnIPC);
                 LOGGER.info("*************** image with pet status  ****************"+imageDetails.getImageStatus());
                 response.setEvent(WorkListDisplayConstants.IMAGE_DETAILS, imageDetails);
                 response.setRenderParameter("returnPageNumber", request.getParameter("selectedPageNumber"));
                 response.setRenderParameter("returnOrinNumber", request.getParameter("selectedParentOrin"));
            }
        
        //IPC from worklist display portlet to content pet portlet
        if((request.getParameter("contentStatus")!=null && !request.getParameter("contentStatus").isEmpty()) 
                && custuser!=null)//TODO
        {
            LOGGER.info("contentStatus content  update  ****************"+request.getParameter("contentStatus"));
            LOGGER.info("selectedOrin content  update  ****************"+request.getParameter("selectedOrin"));
            //code for Inserting PET lock START
            String petOrigin=WorkListDisplayConstants.SEARCH_LOCKED_TYPE_CONTENT;
            
            if(sessionDataKey != null){
                custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);
                LOGGER.info("custuser content  update  ****************"+custuser);
            }
            String pepUserId =null;
            if(custuser!=null){
                LOGGER.info("custuser content  update  ****************"+custuser.isExternal());
                if(custuser.isExternal()){
                    
                    pepUserId= custuser.getVpUser().getUserEmailAddress();
                }else{
                    pepUserId = custuser.getBelkUser().getLanId();
                }
            } 
            LOGGER.info("custuser content  pepUserId  ****************"+pepUserId);
           /**
             * Conditions to lock Group ID/ PET
             * @author AFUSKJ2 6/17/2016 
             */
            /** Change starts */
            /*if(WorkListDisplayConstants.ADV_SEARCH_INCLUDE_GROUPINGS.equals(request.getParameter("searchResultInput"))
                    &&(StringUtils.isNotBlank(groupID) || StringUtils.isNotBlank(groupName))){
            	if(LOGGER.isDebugEnabled()){
            		LOGGER.debug("groupID-->"+ groupID + "pepUserId-->"+ pepUserId + "petOrigin-->"+ petOrigin );
            	}
                workListDisplayDelegate.lockPET(groupID, pepUserId,petOrigin);
            }else{*/
            if(LOGGER.isDebugEnabled()){
         	   LOGGER.debug("selected orin number::: "+request.getParameter("selectedOrin"));
            }
                workListDisplayDelegate.lockPET(request.getParameter("selectedOrin"), pepUserId,petOrigin);
           // }
            
            /** Change ends **/
             //code for Inserting PET lock START
             LOGGER.info("selectedOrin image  update  ****************"+request.getParameter("selectedOrin"));
            
           LOGGER.info("contentStatus selectedOrin ****************"+request.getParameter("selectedOrin"));
           ContentPetDetails contentPetDetails = new ContentPetDetails();
           contentPetDetails.setUserData(custuser); //TODO
           contentPetDetails.setOrinNumber(request.getParameter("selectedOrin"));
           contentPetDetails.setContentStatus(request.getParameter("contentStatus"));
           LOGGER.info(" contentPetDetails.setUserData****************"+ contentPetDetails.getUserData());
           LOGGER.info("  contentPetDetails.setOrinNumber****************"+ contentPetDetails.getOrinNumber());
           LOGGER.info("  contentPetDetails.getContentStatus****************"+ contentPetDetails.getContentStatus());
           LOGGER.info("contentStatus setting event ****************");
           response.setEvent(WorkListDisplayConstants.CONTENT_PET_DETAILS, contentPetDetails);
           response.setRenderParameter("returnPageNumber", request.getParameter("selectedPageNumber"));
           response.setRenderParameter("returnOrinNumber", request.getParameter("selectedParentOrin"));
           LOGGER.info("after contentStatus setting event ****************" + response.getClass());
            
        }
       
    LOGGER.info("Dep no from the action method"+request.getParameter(WorkListDisplayConstants.DEPT_NO_PARAM));
        if(null!=request.getParameter(WorkListDisplayConstants.DEPT_NO_PARAM)){
            response.setRenderParameter(WorkListDisplayConstants.DEPT_NO_PARAM, request.getParameter(WorkListDisplayConstants.DEPT_NO_PARAM));
        }
        if((request.getParameter("createManualPet")!=null && !request.getParameter("createManualPet").isEmpty()) 
                && custuser!=null){
            LOGGER.info("createManualPet  inside ****************"+request.getParameter("createManualPet"));
            response.setEvent(WorkListDisplayConstants.LOGGEDIN_USER_DETAILS, (Serializable) custuser); //TODO
        }
        /** Belk PIM Phase - II. Change for Create Grouping - Start. AFUPYB3 **/
        if((request.getParameter("goToGrouping")!=null && !request.getParameter("goToGrouping").isEmpty()) 
                && custuser!=null){
            LOGGER.info("goToGrouping  inside ****************"+request.getParameter("goToGrouping"));
            response.setEvent("GroupingUserObj", (Serializable) custuser); //AFUPYB3
        }
        /** Belk PIM Phase - II. Change for Create Grouping - End. AFUPYB3 **/
        
        /** Belk PIM Phase - II. Change for Group Worklist - Start. Cognizant **/
        if((request.getParameter("workListType")!=null && !request.getParameter("workListType").isEmpty()) 
                && custuser!=null){
            LOGGER.info("workListType inside ****************"+request.getParameter("workListType"));
            request.getPortletSession().setAttribute("groupWorklistSession", request.getParameter("workListType")); //AFUPYB3
        }
        /** Belk PIM Phase - II. Change for Group Worklist - End. Cognizant **/
        
    }
    
    
    /**
     * 
     * Default Render method of worklistdisplay when page loads
     */
    public ModelAndView handleRenderRequest(RenderRequest request,
            RenderResponse response) throws Exception {

        LOGGER.info("WorkListDisplayController:handleRenderRequest:Enter");
        // Setting the Userdata for Testing purpose since the Login screen is not working because of DBdesign change.
        List<WorkFlow> workFlowListSri = null;
        WorkListDisplayForm  renderForm = null;
        Properties prop= PropertiesFileLoader.getExternalLoginProperties();       
        int maxResults=Integer.parseInt(prop.getProperty(WorkListDisplayConstants.PAGE_LIMIT));
        getUserDetailsFromLoginScreen(request);
        ArrayList departmentDetailsListToLoadPet = new ArrayList();
        PortletSession portletSession = request.getPortletSession();
        boolean isPageAnchor = false;
        String anchoredPageNumber = null;
        if((request.getPortletSession().getAttribute("formSessionKey")!=null)  && (request.getPortletSession().getAttribute("sessionDataKey")!=null) ){
            LOGGER.info("session data is not null  *******");
        
        String formSessionKey =  (String)request.getPortletSession().getAttribute("formSessionKey");
        String sessionDataKey =  (String)request.getPortletSession().getAttribute("sessionDataKey");
        
        LOGGER.info("handleRenderRequest formSessionKey  ---------------------------------------------------------------------------"+formSessionKey);
        LOGGER.info("handleRenderRequest sessionDataKey  ---------------------------------------------------------------------------"+sessionDataKey);
        
        //Advance Search
       
            String preVisitedOrin = null;
        UserData custuser = null;
        if(sessionDataKey != null){
            custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);
        }
         //added below property to keep track in sorting from pagination anchor
        String sortingColumn = null;
        String sortingOrder = null;

        if(custuser!=null){
                /**
                 * Handle the request from pagination event to enable anchoring
                 * populating "paginationFlow" request attribute if the renderrequest thru pagination event
                 * removing the attribute to make sure the successive call will go thru the regular flow
                 * and pagination handled correctly
                 *
                 */
                if (portletSession.getAttribute("paginationFlow") != null && Boolean
                        .valueOf((String) portletSession.getAttribute("paginationFlow"))) {
                    portletSession.removeAttribute("paginationFlow");
                    PageAnchorDetails pageAnchorDetails = (PageAnchorDetails) portletSession
                            .getAttribute("pageAnchorDetails");
                    portletSession.removeAttribute("pageAnchorDetails");
                    if (pageAnchorDetails != null) {
                        anchoredPageNumber = pageAnchorDetails.getPageNumber();
                        preVisitedOrin = pageAnchorDetails.getOrinNumber();
                        String groupId = pageAnchorDetails.getGroupId();
                        List<String> supplierIdList = null;
                        //Default page number when anchored page number is unavailable
                        int pageNumber = 1;
                        if(StringUtils.isNotBlank(anchoredPageNumber)){
                        	pageNumber = Integer.parseInt(anchoredPageNumber);
                        }

                        if (custuser != null && custuser.getVpUser() != null) {
                            supplierIdList = custuser.getVpUser().getSupplierIdsList();
                        }
                        WorkListDisplayForm resourceForm = (WorkListDisplayForm) request.getPortletSession()
                                .getAttribute(formSessionKey);
                        if ((resourceForm != null && StringUtils
                                .equalsIgnoreCase("Yes", resourceForm.getSearchClicked())) || StringUtils
                                .isNotBlank(groupId)) {
                            
                            String callType = "";
                            /**
                             * Update the advance search parameters based on
                             * callType -->"groupingSearch" for includeGrps search, ""-->for item search
                             */
                            AdvanceSearch advanceSearch = resourceForm.getAdvanceSearch();
                            if (StringUtils.isNotBlank(groupId)) {
                                advanceSearch.setGroupingID(groupId);
                                advanceSearch.setSearchResults("includeGrps");
                                advanceSearch.setContentStatus(WorkListDisplayConstants.EMPTY_STRING);
                                advanceSearch.setImageStatus(WorkListDisplayConstants.EMPTY_STRING);
                                advanceSearch.setRequestType(WorkListDisplayConstants.EMPTY_STRING);
                                resourceForm.setSearchClicked("Yes");
                                resourceForm.setWorkListType(WorkListDisplayConstants.GROUPINGS);
                            }
                            if (StringUtils.equalsIgnoreCase("includeGrps", advanceSearch.getSearchResults())) {
                                callType = "groupingSearch";
                            }
                            resourceForm.setAdvanceSearch(advanceSearch);
                            
                            mv.addObject(WorkListDisplayConstants.WORK_FLOW_FORM,
                                    getUpdatedAdvanceSearchForm(resourceForm, supplierIdList,
                                            request.getPortletSession(), pageNumber, callType));
                            //                        ((WorkListDisplayForm) request.getPortletSession().getAttribute(formSessionKey)));
                            if (preVisitedOrin != null) {
                                mv.addObject("prevVisitedOrin", preVisitedOrin);
                            }
                            return mv;
                        }else if(resourceForm != null){
                        	if (StringUtils.isNotBlank(anchoredPageNumber) && StringUtils.isNotBlank(preVisitedOrin)) {
                        		List<WorkFlow> workFlowList = getWorkFlowListFromDB(resourceForm, resourceForm.getWorkListType(), resourceForm.getSelectedDepartmentFromDB(), 
                                		resourceForm.getVendorEmail(), supplierIdList, pageNumber, maxResults);
                        		resourceForm.setWorkFlowlist(workFlowList);
                            	mv.addObject(WorkListDisplayConstants.WORK_FLOW_FORM,resourceForm);
                            	
                                if (preVisitedOrin != null) {
                                    mv.addObject("prevVisitedOrin", preVisitedOrin);
                                }
                                return mv;
                        	}
                        }
                    }
                }
            mv = new ModelAndView(WorkListDisplayConstants.MODEL_VIEW_NAME);
            mv.addObject(WorkListDisplayConstants.IS_PET_AVAILABLE,WorkListDisplayConstants.NO_VALUE);
            String pepUserId="";
            //Handling the Internal user
            if(custuser.isInternal()){
                renderForm =  new WorkListDisplayForm();
                //for handling sorting column
                if(StringUtils.isNotBlank(sortingColumn)){
                    renderForm.setSelectedColumn(sortingColumn);
                    renderForm.setSortingAscending(sortingOrder);
                }
                LOGGER.info("formSessionKey "+formSessionKey);
                if(formSessionKey != null){
                    request.getPortletSession().setAttribute(formSessionKey, renderForm); // Added by Sriharsha
                }
                //Setting Editable or not
                assignRole(renderForm,custuser.getRoleName());
               // assignRole(renderForm,"dca");
               
               // assignRole(workListDisplayForm,custuser.getRoleName()); //TODO
              //  assignRole(workListDisplayForm,"dca"); //TODO
                mv.addObject(WorkListDisplayConstants.IS_INTERNAL,WorkListDisplayConstants.YES_VALUE);
                pepUserId = custuser.getBelkUser().getLanId();
                LOGGER.info("This is from Reneder Internal User:"+pepUserId);
                renderForm.setPepUserID(pepUserId);
                ArrayList depListFromDB = workListDisplayDelegate.getSavedDepartmentDetailsByPepUserId(pepUserId);
                //keeping the current department list in the form
                renderForm.setSelectedPepDepartmentFromDB(depListFromDB);
                departmentDetailsListToLoadPet= (ArrayList) populateDepartmentDetailsFromDB(depListFromDB);
                if(departmentDetailsListToLoadPet.size()>0){
                    renderForm.setSelectedDepartmentFromDB(departmentDetailsListToLoadPet);
                }else{//Internal First time Login
                    //Get the department details 
                    LOGGER.info("WorkListDisplayController:This is first time login internal user get the Department details using XML query input is LAN ID");
                    List firstTimedeptdetailsFromADSE = workListDisplayDelegate.getDepartmentDetailsForInernalFirstTimeLogin(custuser.getBelkUser().getLanId());
                    //LOGGER.info("searchedDeptdetailsFromADSE..Size" + firstTimedeptdetailsFromADSE.size());
                    if(firstTimedeptdetailsFromADSE.size()>0){
                        LOGGER.info("<<<< Line 316 >>>>>");
                        renderForm.setFirstTimesearchedDeptdetailsFromADSE(firstTimedeptdetailsFromADSE); 
                    }
                    LOGGER.info("<<<< Line 319 >>>>>");
                    mv.addObject(WorkListDisplayConstants.IS_PET_AVAILABLE,WorkListDisplayConstants.YES_VALUE);//This is to show the Department section
                    //renderForm.setPetNotFound(prop.getProperty(WorkListDisplayConstants.PET_NOT_FOUND_FIRST_LOGIN));
                    renderForm.setPetNotFound(prop.getProperty(WorkListDisplayConstants.FIRST_TIME_USER_LOGIN_MESSAGE));
                    renderForm.setTotalNumberOfPets("0");
                }
                //User may enter the department no Directly overriding the dep details with entered one
                if(null!=request.getParameter(WorkListDisplayConstants.DEPT_NO_PARAM) && request.getParameter(WorkListDisplayConstants.DEPT_NO_PARAM).length()>1){
                    LOGGER.info("This is from render method, Internal User entered the dep details directly in the box");
                    String depNo=request.getParameter(WorkListDisplayConstants.DEPT_NO_PARAM);
                    DepartmentDetails departmentDetails = new DepartmentDetails();
                    departmentDetails.setId(depNo);
                    departmentDetailsListToLoadPet = new ArrayList();
                    departmentDetailsListToLoadPet.add(departmentDetails);   
                    renderForm.setDeptNo(depNo);
                }
               //request.getPortletSession().setAttribute(formSessionKey, renderForm); // Added by Sriharsha
            }else{//Handling External User
                renderForm =  new WorkListDisplayForm();
                if(StringUtils.isNotBlank(sortingColumn)){
                    renderForm.setSelectedColumn(sortingColumn);
                    renderForm.setSortingAscending(sortingOrder);
                }
                LOGGER.info("formSessionKey "+formSessionKey);
                if(formSessionKey != null){
                    request.getPortletSession().setAttribute(formSessionKey, renderForm); // Added by Sriharsha
                }
              //Setting Editable or not
                LOGGER.info("Handling External User Setting Editable or not-----------------------------");
                LOGGER.info("Before assigning role for external user-----------------------------");
                assignRole(renderForm,custuser.getRoleName());
             //   assignRole(workListDisplayForm,custuser.getRoleName()); //TODO
                LOGGER.info("After assigning role for external user-----------------------------");
                LOGGER.info("workListDisplayForm.getRoleEditable() for external user-----------------------------"+renderForm.getRoleEditable());
                LOGGER.info("********----------before setting vendor email-------------------");
                renderForm.setVendorEmail(custuser.getVpUser().getUserEmailAddress());
                LOGGER.info("********-------------after setting vendor email----------------");
                LOGGER.info("Supplier ID from the Event processing**************************"+custuser.getVpUser().getUserid());
                
                mv.addObject(WorkListDisplayConstants.IS_INTERNAL,WorkListDisplayConstants.NO_VALUE);
                LOGGER.info("This is from Reneder External User and got the Dep details from DB");
                pepUserId = custuser.getVpUser().getPepUserID();
                LOGGER.info("This is from Reneder External User:"+pepUserId);
                ArrayList depListFromDB = workListDisplayDelegate.getSavedDepartmentDetailsByPepUserId(pepUserId);
                renderForm.setPepUserID(pepUserId);
                //keeping the current department list in the form
                renderForm.setSelectedPepDepartmentFromDB(depListFromDB);
                //Formating the Department details to display in the JSP
                departmentDetailsListToLoadPet= (ArrayList) populateDepartmentDetailsFromDB(depListFromDB);
                if(departmentDetailsListToLoadPet.size()>0){
                    LOGGER.info("<<<< Line 364 >>>>>");
                    renderForm.setSelectedDepartmentFromDB(departmentDetailsListToLoadPet);
                }else{ // First time External 
                    LOGGER.info("WorkListDisplayController:This is first time login External user get the Department details using XML query input is LAN ID");
                    // getting the all department details from ADSE table using XML query using supplier id and supplier email 
                    List firstTimedeptdetailsFromADSE = workListDisplayDelegate.getDepartmentDetailsForExternalFirstTimeLogin(custuser.getVpUser().getUserEmailAddress());
                    if(firstTimedeptdetailsFromADSE.size()>0){
                        LOGGER.info("<<<< Line 371 >>>>>");
                        renderForm.setFirstTimesearchedDeptdetailsFromADSE(firstTimedeptdetailsFromADSE);
                    }
                    
                    //Default Pagination
                    int selectedPageNumber = 1;
                    if (null != request.getParameter(WorkListDisplayConstants.CURRENT_PAGE_NUMBER)) {
                        selectedPageNumber = Integer
                                .valueOf(request.getParameter(WorkListDisplayConstants.CURRENT_PAGE_NUMBER));
                    }
                    
                    // Added for Pagination Perf Enhancements
                    String workListType = (String) request.getPortletSession().getAttribute(WorkListDisplayConstants.GROUP_WORKLIST_SESSION);
                    workFlowListSri = getWorkFlowListFromDB(renderForm, workListType, departmentDetailsListToLoadPet, 
                    		custuser.getVpUser().getUserEmailAddress(), custuser.getVpUser().getSupplierIdsList(), selectedPageNumber, maxResults);
                    
                    if(workFlowListSri != null && workFlowListSri.size() > 0){
                         LOGGER.info("<<<< Line 382 >>>>>");
                         renderForm.setWorkFlowlist(workFlowListSri);
	                     mv.addObject(WorkListDisplayConstants.IS_PET_AVAILABLE,WorkListDisplayConstants.YES_VALUE);
	                     handlingPaginationRender(selectedPageNumber,renderForm, workFlowListSri);
	                     renderForm.setSelectedPage(String.valueOf(selectedPageNumber));
                    }else{
                        LOGGER.info("workFlowList.size == 0 On default Load");
                        mv.addObject(WorkListDisplayConstants.IS_PET_AVAILABLE,WorkListDisplayConstants.YES_VALUE);
                        //renderForm.setPetNotFound(prop.getProperty(WorkListDisplayConstants.PET_NOT_FOUND_FIRST_LOGIN));
                        renderForm.setPetNotFound(prop.getProperty(WorkListDisplayConstants.FIRST_TIME_USER_LOGIN_MESSAGE));
                        renderForm.setTotalNumberOfPets("0");
                    }//Fix for 261 end

                }
               //User may enter the dep no Directly so overriding the dep details with entered one
                if(null!=request.getParameter(WorkListDisplayConstants.DEPT_NO_PARAM) && request.getParameter(WorkListDisplayConstants.DEPT_NO_PARAM).length()>1){
                    LOGGER.info("This is from Reneder External User and enter the dep details directly in the box");
                    LOGGER.info(" <<<< Line 413 >>>>>");
                    String depNo=request.getParameter(WorkListDisplayConstants.DEPT_NO_PARAM);
                    DepartmentDetails departmentDetails = new DepartmentDetails();
                    departmentDetails.setId(depNo);
                    departmentDetailsListToLoadPet = new ArrayList();
                    departmentDetailsListToLoadPet.add(departmentDetails);
                    renderForm.setDeptNo(depNo);
                }
            }
            
            if(null!=departmentDetailsListToLoadPet && departmentDetailsListToLoadPet.size()>0){
                LOGGER.info(" <<<< Line 425 >>>>>");
                mv.addObject(WorkListDisplayConstants.IS_PET_AVAILABLE,WorkListDisplayConstants.YES_VALUE);
                //Getting the  Pet Details 
                
                String email = null;
                if(custuser!=null && custuser.getVpUser()!=null && custuser.getVpUser().getUserEmailAddress()!=null) {
                    email = custuser.getVpUser().getUserEmailAddress();
                }
                
                //Changes for multi supplier id start
                List<String> supplierIdList = null;
                if(custuser!=null && custuser.getVpUser()!=null){
                   supplierIdList =  custuser.getVpUser().getSupplierIdsList(); 
                }
                
                //Default Pagination
                int selectedPageNumber = 1;
                if (null != request.getParameter(WorkListDisplayConstants.CURRENT_PAGE_NUMBER)) {
                    selectedPageNumber = Integer
                            .valueOf(request.getParameter(WorkListDisplayConstants.CURRENT_PAGE_NUMBER));
                }
                
                String workListType =
                    (String) request.getPortletSession().getAttribute(WorkListDisplayConstants.GROUP_WORKLIST_SESSION);
                renderForm.setWorkListType(workListType);
                
                workFlowListSri = getWorkFlowListFromDB(renderForm, workListType, departmentDetailsListToLoadPet, 
                		email, supplierIdList, selectedPageNumber, maxResults);
                renderForm.setWorkFlowlist(workFlowListSri); 
                
                //Changes for multi supplier id end
                if(workFlowListSri!=null && workFlowListSri.size()>0){
                    renderForm.setPetNotFound(null);
                    handlingPaginationRender(selectedPageNumber,renderForm, workFlowListSri);
                    renderForm.setSelectedPage(String.valueOf(selectedPageNumber));
                }else{//There is no PET for searched content
                    LOGGER.info(" <<<< Line 457 >>>>>");
                    renderForm.setPetNotFound(prop.getProperty(WorkListDisplayConstants.PET_NOT_FOUND));
                    renderForm.setTotalNumberOfPets("0");
                }
            }

            populatingAdvanceSearchDefaultValues(renderForm); 
            
            if(custuser.isInternal()){
                mv.addObject(WorkListDisplayConstants.IS_INTERNAL,WorkListDisplayConstants.YES_VALUE);
                renderForm.setRoleName(WorkListDisplayConstants.DCA_ROLE);
            }else{
                mv.addObject(WorkListDisplayConstants.IS_INTERNAL,WorkListDisplayConstants.NO_VALUE);
                renderForm.setRoleName("");
            }
            System.out.println(" Original Work List ROle -----------------> "+ renderForm.getRoleName());
            
            request.getPortletSession().setAttribute(formSessionKey, renderForm); 
            
            System.out.println(" Original Work List ROle -----------------> "+ ((WorkListDisplayForm)request.getPortletSession().getAttribute(formSessionKey)).getRoleName());
            mv.addObject(WorkListDisplayConstants.WORK_FLOW_FORM, ((WorkListDisplayForm)request.getPortletSession().getAttribute(formSessionKey)));  
        } 
        
        String belkBestPlan = prop.getProperty(WorkListDisplayConstants.BELK_BEST_PLAN);
        mv.addObject(WorkListDisplayConstants.BELK_BEST_PLAN_URL_KEY, belkBestPlan);
 		if (preVisitedOrin != null) {
            mv.addObject("prevVisitedOrin", preVisitedOrin);
        }
        }else{          
            mv = new ModelAndView(WorkListDisplayConstants.MODEL_VIEW_LOGIN_PAGE);
            mv.addObject("loggedInUser",  request.getPortletSession().getAttribute("loggedInUser") );           
        }
       
             LOGGER.info("WorkListDisplayController:handleRenderRequest:Exit");
            return mv;
    }
    
    /**
     * This method will populate the Advance search with the Default value, Drop downs etc.
     *
     * @param renderForm the render form
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void populatingAdvanceSearchDefaultValues(WorkListDisplayForm  renderForm) throws IOException {
        Properties prop= PropertiesFileLoader.getExternalLoginProperties();
        
        AdvanceSearch advanceSearch = new AdvanceSearch();
        //Setting the Image status default DropDownvalues
        List imageStatusDropDownValues = new ArrayList<ImageStatusDropValues>();
        ImageStatusDropValues initialted= new ImageStatusDropValues();
        initialted.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        initialted.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_IMG_DROPDOWN_INITIATED));
        
        ImageStatusDropValues readyForReview= new ImageStatusDropValues();
        readyForReview.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);        
        readyForReview.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_IMG_DROPDOWN_READYFORREVIEW));
        
        /*ImageStatusDropValues pending= new ImageStatusDropValues();
        pending.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        pending.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_IMG_DROPDOWN_PENDING));
        
        ImageStatusDropValues imageNotApproved= new ImageStatusDropValues();
        imageNotApproved.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        imageNotApproved.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_IMG_DROPDOWN_IMAGE_NOT_APPROVED));
        
        ImageStatusDropValues approved= new ImageStatusDropValues();
        approved.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        approved.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_IMG_DROPDOWN_APPROVED));
        */
        
        ImageStatusDropValues completed= new ImageStatusDropValues();
        completed.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        completed.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_IMG_DROPDOWN_COMPLETED));
        
        ImageStatusDropValues closed = new ImageStatusDropValues();
        closed.setChecked(WorkListDisplayConstants.ADV_SEARCH_NO_VALUE);
        closed.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_IMG_DROPDOWN_CLOSED));
        
        imageStatusDropDownValues.add(initialted);
        imageStatusDropDownValues.add(readyForReview);
        imageStatusDropDownValues.add(completed);
        imageStatusDropDownValues.add(closed);
        advanceSearch.setImageStatusDropDown(imageStatusDropDownValues);
        
        //Setting the Content Status
        
        List contentStatusDropDownValues = new ArrayList<ContentStatusDropValues>();
        ContentStatusDropValues initialtedContent= new ContentStatusDropValues();
        initialtedContent.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        initialtedContent.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_CONTENT_DROPDOWN_INITIATED));
        
        ContentStatusDropValues readyForReviewContent= new ContentStatusDropValues();
        readyForReviewContent.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        readyForReviewContent.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_CONTENT_DROPDOWN_READYFORREVIEW));
        
        ContentStatusDropValues approvedContent= new ContentStatusDropValues();
        approvedContent.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        approvedContent.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_CONTENT_DROPDOWN_APPROVED));
        
        ContentStatusDropValues contenetNotApproved= new ContentStatusDropValues();
        contenetNotApproved.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        contenetNotApproved.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_CONTENT_DROPDOWN_CONTENT_NOT_APPROVED));
        
        
        ContentStatusDropValues completedContent= new ContentStatusDropValues();
        completedContent.setChecked(WorkListDisplayConstants.ADV_SEARCH_NO_VALUE);
        completedContent.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_CONTENT_DROPDOWN_COMPLETED));
        
        ContentStatusDropValues closedContent= new ContentStatusDropValues();
        closedContent.setChecked(WorkListDisplayConstants.ADV_SEARCH_NO_VALUE);
        closedContent.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_CONTENT_DROPDOWN_CLOSED));
        
        contentStatusDropDownValues.add(initialtedContent);
        contentStatusDropDownValues.add(readyForReviewContent);
        contentStatusDropDownValues.add(completedContent);
        contentStatusDropDownValues.add(closedContent);
        advanceSearch.setContentStatusDropDown(contentStatusDropDownValues);
        
        //Setting the Request Type details
        List requestTypeDropDownValues = new ArrayList<RequestTypeDropValues>();
        RequestTypeDropValues manual= new RequestTypeDropValues();
        manual.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        manual.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_REQUEST_DROPDOWN_MANUAL));
        
        RequestTypeDropValues directShipItem= new RequestTypeDropValues();
        directShipItem.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        directShipItem.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_REQUEST_DROPDOWN_DSI));
        
        RequestTypeDropValues itemAttributeSetup= new RequestTypeDropValues();
        itemAttributeSetup.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        itemAttributeSetup.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_REQUEST_DROPDOWN_IAS));
        
        RequestTypeDropValues po= new RequestTypeDropValues();
        po.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        po.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_REQUEST_DROPDOWN_PO));
        
        
        RequestTypeDropValues grouping= new RequestTypeDropValues();
        grouping.setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
        grouping.setValue(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_REQUEST_DROPDOWN_GROUPING));
        
        requestTypeDropDownValues.add(manual);
        requestTypeDropDownValues.add(directShipItem);
        requestTypeDropDownValues.add(itemAttributeSetup);
        requestTypeDropDownValues.add(po);
        advanceSearch.setRequestTypeDropDown(requestTypeDropDownValues);
        
        //Setting the Default values for the text boxes
        advanceSearch.setImageStatus(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_IMAGE_TEXT_VALUE));
        advanceSearch.setContentStatus(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_CONTENT_TEXT_VALUE));
        advanceSearch.setRequestType(prop.getProperty(WorkListDisplayConstants.ADV_SEARCH_REQUEST_TEXT_VALUE));
        advanceSearch.setActive("01");
        advanceSearch.setSearchResults("includeGrps");
        advanceSearch.setSearchTimePeriod(prop.getProperty(WorkListDisplayConstants.ADVANCE_SEARCH_TIME_PERIOD));
        renderForm.setAdvanceSearch(advanceSearch);
        }    
    
    private void handlingPaginationRenderAdvSearch(int selectedPageNumber, WorkListDisplayForm renderForm, List<WorkFlow> workFlowListSri) throws IOException {
        LOGGER.info("WorkListDisplayController:handlingPaginationRenderAdvSearch:Enter");
        if(workFlowListSri != null && renderForm != null){
            //fix for 496 start
            int numberOfPets = renderForm.getFullWorkFlowlist().size();
            workFlowListSri = renderForm.getFullWorkFlowlist();
            if(null!=renderForm.getTotalNumberOfPets()){
                numberOfPets = Integer.parseInt(renderForm.getTotalNumberOfPets().toString());
                
            }
            renderForm.setTotalNumberOfPets(String.valueOf(numberOfPets));
            //Setting the limit
            Properties prop= PropertiesFileLoader.getExternalLoginProperties();
            renderForm.setPageLimit(prop.getProperty(WorkListDisplayConstants.ADVANCE_SEARCH_PAGE_LIMIT));
            //Setting the number of pages            
            int numberOfPages = 1;
            double numPage = 1;
            int pageLimit = Integer.parseInt(prop.getProperty(WorkListDisplayConstants.ADVANCE_SEARCH_PAGE_LIMIT));
            if(numberOfPets>pageLimit){
                   double pageLimeiDoub = Double.parseDouble(String.valueOf(pageLimit));
                   numPage = numberOfPets/pageLimeiDoub;
                   numPage = Math.ceil(Double.parseDouble(String.valueOf(numPage)));
                }
                   numberOfPages = (int) numPage;
            
            //Setting the page list
            ArrayList<String> pageNumberList = new ArrayList();
            for(int i=0;i<numberOfPages;i++){
                pageNumberList.add(String.valueOf(i+1)); 
            }
            renderForm.setPageNumberList(pageNumberList);
            //Setting the first page Default
            LOGGER.info("Selected Page number is=="+selectedPageNumber);
            int startindex= 0;
            int endIndex= 9;
            startindex= (selectedPageNumber * pageLimit) - pageLimit;
            endIndex= (selectedPageNumber * pageLimit) - 1;
            //Logic for last page
            if(selectedPageNumber==numberOfPages  ){
                endIndex =  numberOfPets;  
            }
            //fix for 496 end
            LOGGER.info("Start index is =="+startindex+"endIndex is =="+endIndex);
            LOGGER.info("workFlowListSri size:" + workFlowListSri.size());
            List currentPageworkFlowList = workFlowListSri.subList(startindex, endIndex);
            renderForm.setSelectedPage(String.valueOf(selectedPageNumber));
            renderForm.setPreviousCount(String.valueOf(selectedPageNumber-1));
            renderForm.setNextCount(String.valueOf(selectedPageNumber+1));
            renderForm.setWorkFlowlist(currentPageworkFlowList);
            renderForm.setFullWorkFlowlist(workFlowListSri);

            renderForm.setDisplayPagination("yes");
            renderForm.setStartIndex(String.valueOf(startindex+1));
            renderForm.setEndIndex(String.valueOf(endIndex+1));
            if(selectedPageNumber==numberOfPages  ){
                renderForm.setEndIndex(String.valueOf(endIndex));  
            }
        }
        
        LOGGER.info("WorkListDisplayController:handlingPaginationRenderAdvSearch:Exit");   
    }

    /**
     * Handling pagination render.
     *
     * @param selectedPageNumber the selected page number
     * @param renderForm         the render form
     * @param workFlowListSri    the work flow list sri
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void handlingPaginationRender(int selectedPageNumber, WorkListDisplayForm renderForm,
            List<WorkFlow> workFlowListSri) throws IOException {
    	LOGGER.info("WorkListDisplayController:handlingPagination:Enter");
        if(workFlowListSri != null && renderForm != null){
            //fix for 496 start\
        	renderForm.setWorkFlowlist(workFlowListSri);
        	int numberOfPets = workFlowListSri.size();
            renderForm.setTotalNumberOfPets(String.valueOf(numberOfPets));
            //Setting the limit
            Properties prop= PropertiesFileLoader.getExternalLoginProperties();
            renderForm.setPageLimit(prop.getProperty(WorkListDisplayConstants.PAGE_LIMIT));
            //Setting the number of pages            
            int numberOfPages = 1;
            
            double numPage = 1;
            int pageLimit = Integer.parseInt(prop.getProperty(WorkListDisplayConstants.PAGE_LIMIT));
            if(numberOfPets>=pageLimit){ 
            	// Pagination Enhancements - if numberOfPets >= pageLimit, assume next page is needed
            	numPage = selectedPageNumber+1;
            }
            numberOfPages = (int) numPage;
            
            renderForm.setSelectedPage(String.valueOf(selectedPageNumber));
            renderForm.setTotalPageno(String.valueOf(numberOfPages));
            renderForm.setPreviousCount(String.valueOf(selectedPageNumber-1));
            renderForm.setNextCount(String.valueOf(selectedPageNumber+1));
            
            if(numberOfPages>1 || selectedPageNumber>1) {
                renderForm.setDisplayPagination("yes");
            } 
            else {
            	renderForm.setDisplayPagination("no");
            }
        }
        
        LOGGER.info("WorkListDisplayController:handlingPagination:Exit");
    }
    
    /**
     * Handling sorting render.
     *
     * @param selectedColumn the selected column
     * @param renderForm the render form
     * @param workFlowListSri the work flow list sri
     * @throws ParseException the parse exception
     */
    private void handlingSortingRender(String selectedColumn, WorkListDisplayForm renderForm, List<WorkFlow> workFlowListSri) throws ParseException {
        LOGGER.info("WorkListDisplayController:handlingSorting:Enter");
        findTypeOfSortingRender(selectedColumn, renderForm);
        workFlowListSri = sortWorkFlowListRender(workFlowListSri,selectedColumn, renderForm);
        renderForm.setSelectedColumn(selectedColumn);
        renderForm.setWorkFlowlist(workFlowListSri);
        LOGGER.info("WorkListDisplayController:handlingSorting:Exit");
       // return workFlowListSri;
    }



    
    public List<WorkFlow> sortWorkFlowListRender(List<WorkFlow> workFlowList2,
        String selectedColumn, WorkListDisplayForm renderForm) throws ParseException {
        
        
        LOGGER.info("WorkListDisplayController:sortWorkFlowList:Enter");
        List<WorkFlow> sortedList = new ArrayList();
        if(workFlowList2 != null){
       
      if(WorkListDisplayConstants.COMPLETION_DATE.equalsIgnoreCase(selectedColumn)){
          LOGGER.info("This is Completion date sorting.");
          WorkFlow currentWorkFlow = null;
          WorkFlow nextWorkFlow = null;
          if(renderForm.getSortingAscending().equalsIgnoreCase(WorkListDisplayConstants.TRUE_VALUE)){
              //Ascending Sorting
              LOGGER.info("This is Comletion date sorting....Ascending is true"); 
              for(int i=0;i< workFlowList2.size();i++){
                  
                      for (int j = i + 1; j < workFlowList2.size(); j++) 
                      {
                          if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                                  currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                                  nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                              if(null!=currentWorkFlow.getCompletionDate() && null!=nextWorkFlow.getCompletionDate()){
                                 
                                    if(DateUtil.stringToDate(currentWorkFlow.getCompletionDate()).after(DateUtil.stringToDate(nextWorkFlow.getCompletionDate()))){
                                        Collections.swap(workFlowList2, i, j);
                                      }
                               
                              }
                          }
                        
                      }
              }
              sortedList = workFlowList2;
          }else{
              //Descending sorting
              LOGGER.info("This is Completion date sorting....Ascending is false"); 
              for(int i=0;i< workFlowList2.size();i++){
                  for (int j = i + 1; j < workFlowList2.size(); j++) 
                  {
                      if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                          currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                          nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                          if(null!=currentWorkFlow.getCompletionDate() && null!=nextWorkFlow.getCompletionDate()){
                             
                                if(DateUtil.stringToDate(currentWorkFlow.getCompletionDate()).before(DateUtil.stringToDate(nextWorkFlow.getCompletionDate()))){
                                    Collections.swap(workFlowList2, i, j);
                                  }
                          }
                      }
                    
                  }
          }
              sortedList = workFlowList2;
          }
      }else if(WorkListDisplayConstants.IMAGE_STATUS.equalsIgnoreCase(selectedColumn)){
        LOGGER.info("Sorting happening on base of imageStatus"+renderForm.getSortingAscending()); 
        LOGGER.info("This is imageStatus sorting....");
        WorkFlow currentWorkFlow = null;
        WorkFlow nextWorkFlow = null;
        if(renderForm.getSortingAscending().equalsIgnoreCase(WorkListDisplayConstants.TRUE_VALUE)){
            //Ascending Sorting
            LOGGER.info("This is imageStatus sorting....Ascending is true"); 
            for(int i=0;i< workFlowList2.size();i++){
                    for (int j = i + 1; j < workFlowList2.size(); j++) 
                    {
                       
                        if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                            currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                            nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                            if(null!=currentWorkFlow.getImageStatus() && null!=nextWorkFlow.getImageStatus()){
                                  if(currentWorkFlow.getImageStatus().compareTo(nextWorkFlow.getImageStatus()) > 0 ){
                                      Collections.swap(workFlowList2, i, j);
                                    }
                            }
                        }
                      
                    }
            }
            sortedList = workFlowList2;
        }else{
            //Descending sorting
            LOGGER.info("This is imageStatus sorting....Ascending is false"); 
            for(int i=0;i< workFlowList2.size();i++){
                for (int j = i + 1; j < workFlowList2.size(); j++) 
                {
                    if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                        currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                        nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                        if(null!=currentWorkFlow.getCompletionDate() && null!=nextWorkFlow.getCompletionDate()){
                            if(null!=currentWorkFlow.getImageStatus() && null!=nextWorkFlow.getImageStatus()){
                                if(currentWorkFlow.getImageStatus().compareTo(nextWorkFlow.getImageStatus()) < 0 ){
                                    Collections.swap(workFlowList2, i, j);
                                  }
                          }
                        }
                    }
                  
                }
        }
            sortedList = workFlowList2;
        }
      }else if(WorkListDisplayConstants.CONTENT_STATUS.equalsIgnoreCase(selectedColumn)){
        LOGGER.info("Sorting happening on base of contentStatus"+renderForm.getSortingAscending()); 
        WorkFlow currentWorkFlow = null;
        WorkFlow nextWorkFlow = null;
        if(renderForm.getSortingAscending().equalsIgnoreCase(WorkListDisplayConstants.TRUE_VALUE)){
            //Ascending Sorting
            LOGGER.info("This is contentStatus sorting....Ascending is true"); 
            for(int i=0;i< workFlowList2.size();i++){
                    for (int j = i + 1; j < workFlowList2.size(); j++) 
                    {
                        if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                            currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                            nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                            if(null!=currentWorkFlow.getContentStatus() && null!=nextWorkFlow.getContentStatus()){
                                  if(currentWorkFlow.getContentStatus().compareTo(nextWorkFlow.getContentStatus()) > 0 ){
                                      Collections.swap(workFlowList2, i, j);
                                    }
                            }
                        }
                      
                    }
            }
            sortedList = workFlowList2;
        }else{
            //Descending sorting
            LOGGER.info("This is contentStatus sorting....Ascending is false"); 
            for(int i=0;i< workFlowList2.size();i++){
                for (int j = i + 1; j < workFlowList2.size(); j++) 
                {
                    if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                        currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                        nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                            if(null!=currentWorkFlow.getContentStatus() && null!=nextWorkFlow.getContentStatus()){
                                if(currentWorkFlow.getContentStatus().compareTo(nextWorkFlow.getContentStatus()) < 0 ){
                                    Collections.swap(workFlowList2, i, j);
                                  }
                            
                          }
                    }
                  
                }
        }
            sortedList = workFlowList2;
        }
      }else if(WorkListDisplayConstants.PRODUCT_NAME.equalsIgnoreCase(selectedColumn)){
        LOGGER.info("Sorting happening on base of productName"+renderForm.getSortingAscending()); 
        LOGGER.info("This is productName sorting....");
        WorkFlow currentWorkFlow = null;
        WorkFlow nextWorkFlow = null;
        if(renderForm.getSortingAscending().equalsIgnoreCase(WorkListDisplayConstants.TRUE_VALUE)){
            //Ascending Sorting
            LOGGER.info("This is productName sorting....Ascending is true"); 
            for(int i=0;i< workFlowList2.size();i++){
                
                    for (int j = i + 1; j < workFlowList2.size(); j++) 
                    {
                        if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                            currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                            nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                            if(null!=currentWorkFlow.getProductName() && null!=nextWorkFlow.getProductName()){
                                  if(currentWorkFlow.getProductName().compareTo(nextWorkFlow.getProductName()) > 0 ){
                                      Collections.swap(workFlowList2, i, j);
                                    }
                            }
                        }
                      
                    }
            }
            sortedList = workFlowList2;
        }else{
            //Descending sorting
            LOGGER.info("This is productName sorting....Ascending is false"); 
            for(int i=0;i< workFlowList2.size();i++){
                for (int j = i + 1; j < workFlowList2.size(); j++) 
                {
                    if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                        currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                        nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                        LOGGER.info("currentWorkFlow.getProductName()"+currentWorkFlow.getProductName()); 
                        LOGGER.info("nextWorkFlow.getProductName()"+nextWorkFlow.getProductName()); 
                            if(null!=currentWorkFlow.getProductName() && null!=nextWorkFlow.getProductName()){
                                if(currentWorkFlow.getProductName().compareTo(nextWorkFlow.getProductName()) < 0 ){
                                    Collections.swap(workFlowList2, i, j);
                                  }
                            
                          }
                    }
                  
                }
        }
            sortedList = workFlowList2;
        }
      }else if(WorkListDisplayConstants.VENDOR_STYLE.equalsIgnoreCase(selectedColumn)){
          LOGGER.info("Sorting happening on base of vendorStyle"+renderForm.getSortingAscending()); 
        WorkFlow currentWorkFlow = null;
        WorkFlow nextWorkFlow = null;
        if(renderForm.getSortingAscending().equalsIgnoreCase(WorkListDisplayConstants.TRUE_VALUE)){
            //Ascending Sorting
            LOGGER.info("This is vendorStyle sorting....Ascending is true"); 
            for(int i=0;i< workFlowList2.size();i++){
                
                    for (int j = i + 1; j < workFlowList2.size(); j++) 
                    {
                       
                        if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                            currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                            nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                            if(null!=currentWorkFlow.getVendorStyle() && null!=nextWorkFlow.getVendorStyle()){
                                  if(currentWorkFlow.getVendorStyle().compareTo(nextWorkFlow.getVendorStyle()) > 0 ){
                                      Collections.swap(workFlowList2, i, j);
                                    }
                              
                            }
                        }
                      
                    }
            }
            sortedList = workFlowList2;
        }else{
            //Descending sorting
            LOGGER.info("This is vendorStyle sorting....Ascending is false"); 
            for(int i=0;i< workFlowList2.size();i++){
                for (int j = i + 1; j < workFlowList2.size(); j++) 
                {
                    if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                        currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                        nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                        if(null!=currentWorkFlow.getVendorStyle() && null!=nextWorkFlow.getVendorStyle()){
                            if(currentWorkFlow.getVendorStyle().compareTo(nextWorkFlow.getVendorStyle()) < 0 ){
                                Collections.swap(workFlowList2, i, j);
                              }
                        
                      }
                    }
                  
                }
        }
            sortedList = workFlowList2;
        }
      }
      //PetSource Sort Flow Starts Afuszr6
      else if("petSourceType".equalsIgnoreCase(selectedColumn)){
          LOGGER.info("<<<< Sorting happening on base of petSourceType >>>> "+renderForm.getSortingAscending()); 
        WorkFlow currentWorkFlow = null;
        WorkFlow nextWorkFlow = null;
        if(renderForm.getSortingAscending().equalsIgnoreCase(WorkListDisplayConstants.TRUE_VALUE)){
            //Ascending Sorting
            LOGGER.info("This is petSourceType sorting....Ascending is true"); 
            for(int i=0;i< workFlowList2.size();i++){
                
                    for (int j = i + 1; j < workFlowList2.size(); j++) 
                    {
                       
                        if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                            currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                            nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                            if(null!=currentWorkFlow.getSourceType() && null!=nextWorkFlow.getSourceType()){
                                  if(currentWorkFlow.getSourceType().compareTo(nextWorkFlow.getSourceType()) > 0 ){
                                      Collections.swap(workFlowList2, i, j);
                                    }
                              
                            }
                        }
                      
                    }
            }
            sortedList = workFlowList2;
        }else{
            //Descending sorting
            LOGGER.info("This is petSourceType sorting....Ascending is false"); 
            for(int i=0;i< workFlowList2.size();i++){
                for (int j = i + 1; j < workFlowList2.size(); j++) 
                {
                    if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                        currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                        nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                        if(null!=currentWorkFlow.getSourceType() && null!=nextWorkFlow.getSourceType()){
                            if(currentWorkFlow.getSourceType().compareTo(nextWorkFlow.getSourceType()) < 0 ){
                                Collections.swap(workFlowList2, i, j);
                              }
                        
                      }
                    }
                  
                }
        }
            sortedList = workFlowList2;
        }
      }
      
      //PetSource Sort floe Ends
      
      else if(WorkListDisplayConstants.VENDOR_NAME.equalsIgnoreCase(selectedColumn)){
        LOGGER.info("Sorting happening on base of vendorName"+renderForm.getSortingAscending());  
        WorkFlow currentWorkFlow = null;
        WorkFlow nextWorkFlow = null;
        if(renderForm.getSortingAscending().equalsIgnoreCase(WorkListDisplayConstants.TRUE_VALUE)){
            //Ascending Sorting
            LOGGER.info("This is vendorName sorting....Ascending is true"); 
            for(int i=0;i< workFlowList2.size();i++){
                
                    for (int j = i + 1; j < workFlowList2.size(); j++) 
                    {
                       
                        if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                            currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                            nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                            if(null!=currentWorkFlow.getVendorName() && null!=nextWorkFlow.getVendorName()){
                                  if(currentWorkFlow.getVendorName().compareTo(nextWorkFlow.getVendorName()) > 0 ){
                                      Collections.swap(workFlowList2, i, j);
                                    }
                              
                            }
                        }
                      
                    }
            }
            sortedList = workFlowList2;
        }else{
            //Descending sorting
            LOGGER.info("This is vendorName sorting....Ascending is false"); 
            for(int i=0;i< workFlowList2.size();i++){
                for (int j = i + 1; j < workFlowList2.size(); j++) 
                {
                    if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                        currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                        nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                        if(null!=currentWorkFlow.getVendorName() && null!=nextWorkFlow.getVendorName()){
                            if(currentWorkFlow.getVendorName().compareTo(nextWorkFlow.getVendorName()) < 0 ){
                                Collections.swap(workFlowList2, i, j);
                              }
                        
                      }
                    }
                  
                }
        }
            sortedList = workFlowList2;
        }
      }else if(WorkListDisplayConstants.DEPT_COLUMN.equalsIgnoreCase(selectedColumn)){
        LOGGER.info("Sorting happening on base of dept"+renderForm.getSortingAscending()); 
        LOGGER.info("This is dept sorting....");
        WorkFlow currentWorkFlow = null;
        WorkFlow nextWorkFlow = null;
        if(renderForm.getSortingAscending().equalsIgnoreCase(WorkListDisplayConstants.TRUE_VALUE)){
            //Ascending Sorting
            LOGGER.info("This is dept sorting....Ascending is true"); 
            for(int i=0;i< workFlowList2.size();i++){
                
                    for (int j = i + 1; j < workFlowList2.size(); j++) 
                    {
                       
                        if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                            currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                            nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                            if(null!=currentWorkFlow.getDeptId() && null!=nextWorkFlow.getDeptId()){
                                  if(currentWorkFlow.getDeptId().compareTo(nextWorkFlow.getDeptId()) > 0 ){
                                      Collections.swap(workFlowList2, i, j);
                                    }
                              
                            }
                        }
                      
                    }
            }
            sortedList = workFlowList2;
        }else{
            //Descending sorting
            LOGGER.info("This is dept sorting....Ascending is false"); 
            for(int i=0;i< workFlowList2.size();i++){
                for (int j = i + 1; j < workFlowList2.size(); j++) 
                {
                    if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                        currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                        nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                        if(null!=currentWorkFlow.getDeptId() && null!=nextWorkFlow.getDeptId()){
                            if(currentWorkFlow.getDeptId().compareTo(nextWorkFlow.getDeptId()) < 0 ){
                                Collections.swap(workFlowList2, i, j);
                              }
                        
                      }
                    }
                  
                }
        }
            sortedList = workFlowList2;
        }
      }else if(WorkListDisplayConstants.ORIN_GROUP.equalsIgnoreCase(selectedColumn)){
        LOGGER.info("Sorting happening on base of orinGroup"+renderForm.getSortingAscending()); 
        LOGGER.info("This is orinGroup sorting....");
        WorkFlow currentWorkFlow = null;
        WorkFlow nextWorkFlow = null;
        if(renderForm.getSortingAscending().equalsIgnoreCase(WorkListDisplayConstants.TRUE_VALUE)){
            //Ascending Sorting
            LOGGER.info("This is orinGroup sorting....Ascending is true"); 
            for(int i=0;i< workFlowList2.size();i++){
                
                    for (int j = i + 1; j < workFlowList2.size(); j++) 
                    {
                       
                        if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                            currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                            nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                            if(null!=currentWorkFlow.getOrinNumber() && null!=nextWorkFlow.getOrinNumber()){
                                int valueFirst = Integer.parseInt(currentWorkFlow.getOrinNumber());
                                int valueLast = Integer.parseInt(nextWorkFlow.getOrinNumber());                                
                                  if(Double.compare(valueFirst, valueLast) > 0 ){
                                      Collections.swap(workFlowList2, i, j);
                                      }
                              
                            }
                        }
                      
                    }
            }
            sortedList = workFlowList2;
        }else{
            //Descending sorting
            LOGGER.info("This is orinGroup sorting....Ascending is false"); 
            for(int i=0;i< workFlowList2.size();i++){
                for (int j = i + 1; j < workFlowList2.size(); j++) 
                {
                    if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                        currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                        nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                        if(null!=currentWorkFlow.getOrinNumber() && null!=nextWorkFlow.getOrinNumber()){
                            if(currentWorkFlow.getOrinNumber().compareTo(nextWorkFlow.getOrinNumber()) < 0 ){
                                Collections.swap(workFlowList2, i, j);
                              }
                        
                      }
                    }
                  
                }
        }
            sortedList = workFlowList2;
        }
      }else if(WorkListDisplayConstants.PET_STATUS.equalsIgnoreCase(selectedColumn)){
          LOGGER.info("Sorting happening on base of petStatus"+renderForm.getSortingAscending()); 
          LOGGER.info("This is petStatus sorting....");
          WorkFlow currentWorkFlow = null;
          WorkFlow nextWorkFlow = null;
          if(renderForm.getSortingAscending().equalsIgnoreCase(WorkListDisplayConstants.TRUE_VALUE)){
              //Ascending Sorting
              LOGGER.info("This is imageStatus sorting....Ascending is true"); 
              for(int i=0;i< workFlowList2.size();i++){
                      for (int j = i + 1; j < workFlowList2.size(); j++) 
                      {
                         
                          if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                              currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                              nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                              if(null!=currentWorkFlow.getPetStatus() && null!=nextWorkFlow.getPetStatus()){
                                    if(currentWorkFlow.getPetStatus().compareTo(nextWorkFlow.getPetStatus()) > 0 ){
                                        Collections.swap(workFlowList2, i, j);
                                      }
                              }
                          }
                        
                      }
              }
              sortedList = workFlowList2;
          }else{
              //Descending sorting
              LOGGER.info("This is petStatus sorting....Ascending is false"); 
              for(int i=0;i< workFlowList2.size();i++){
                  for (int j = i + 1; j < workFlowList2.size(); j++) 
                  {
                      if(null!=workFlowList2.get(i) && null!=workFlowList2.get(j)){
                          currentWorkFlow = (WorkFlow)workFlowList2.get(i);
                          nextWorkFlow = (WorkFlow)workFlowList2.get(j);
                          if(null!=currentWorkFlow.getPetStatus() && null!=nextWorkFlow.getPetStatus()){
                              if(currentWorkFlow.getPetStatus().compareTo(nextWorkFlow.getPetStatus()) < 0 ){
                                  Collections.swap(workFlowList2, i, j);
                                }
                          }
                      }
                    
                  }
          }
              sortedList = workFlowList2;
          }
        }else{
          LOGGER.info("Sorting happening on unlisting selected"+renderForm.getSortingAscending()); 
      }
        }// End of If condition
      
      LOGGER.info("WorkListDisplayController:sortWorkFlowList:Exit");
      return sortedList;
    }
    
    
    /**
     * This method will find the type of sorting Ascending or descending and will set the image.
     *
     * @param selectedColumn the selected column
     * @param renderForm the render form
     */
        private void findTypeOfSortingRender(String selectedColumn, WorkListDisplayForm renderForm) {
            LOGGER.info("WorkListDisplayController:findTypeOfSorting:Enter");
            if(acendingList==null){
                acendingList = new ArrayList();  
            }
            if(decendingList==null){
                decendingList = new ArrayList();  
            }
            if(acendingList.contains(selectedColumn)){
                renderForm.setSortingImage(WorkListDisplayConstants.ARROW_DOWN_IMAGE);  
                acendingList.remove(selectedColumn);
                decendingList.add(selectedColumn);
                renderForm.setSortingAscending(WorkListDisplayConstants.FALSE_VALUE);
            }else if(decendingList.contains(selectedColumn)){
                renderForm.setSortingImage(WorkListDisplayConstants.ARROW_UP_IMAGE);  
                decendingList.remove(selectedColumn);
                acendingList.add(selectedColumn);
                renderForm.setSortingAscending(WorkListDisplayConstants.TRUE_VALUE);
            }//DefaultImage setting
            else{
                renderForm.setSortingImage(WorkListDisplayConstants.ARROW_DOWN_IMAGE); 
                renderForm.setSortingAscending(WorkListDisplayConstants.FALSE_VALUE);
                decendingList.add(selectedColumn);
            }
            LOGGER.info("WorkListDisplayController:findTypeOfSorting:Exit");
            
        }
        

/**
 * This method will assign the DCA  role. 
 * @param workListDisplayForm2
 * @param roleName
 */
private void assignRole(WorkListDisplayForm workListDisplayForm2,
        String roleName) { 
    LOGGER.info("Entering Assinrole*****************");
        if(WorkListDisplayConstants.DCA_ROLE.equalsIgnoreCase(roleName)){
            LOGGER.info("inside the dca role role......"+roleName);
            workListDisplayForm2.setRoleEditable(WorkListDisplayConstants.YES_VALUE);
            workListDisplayForm2.setReadOnlyUser(WorkListDisplayConstants.NO_VALUE);
            //Set role details
          //Completion date edit start
            workListDisplayForm2.setRoleName(WorkListDisplayConstants.DCA_ROLE);
          //Completion date edit end
            
        }
        if(WorkListDisplayConstants.VENDOR.equalsIgnoreCase(roleName)){
            LOGGER.info("inside the vendor role......"+roleName);
            workListDisplayForm2.setRoleEditable(WorkListDisplayConstants.YES_VALUE);
            workListDisplayForm2.setReadOnlyUser(WorkListDisplayConstants.NO_VALUE);
            workListDisplayForm2.setRoleName(WorkListDisplayConstants.VENDOR);
        }
        
        if(WorkListDisplayConstants.READ_ONLY_ROLE.equalsIgnoreCase(roleName)){
            LOGGER.info("inside the read only role......"+roleName);
            workListDisplayForm2.setReadOnlyUser(WorkListDisplayConstants.YES_VALUE);
            workListDisplayForm2.setRoleName(WorkListDisplayConstants.READ_ONLY_ROLE);
        } 
        
    }

/**
   * This method is responsible for returning the user object along with hardcoded value.Needs to remove  
   * @param request
   */
    private void getUserDetailsFromLoginScreen(RenderRequest request) {
        if(request.getPortletSession().getAttribute("sessionDataKey")!=null){
        String sessionDataKey =  (String)request.getPortletSession().getAttribute("sessionDataKey");//TODO
        UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);//TODO
        
        LOGGER.info(" getUserDetailsFromLoginScreen sessionDataKey ---------------------------------------------------------------- "+sessionDataKey);
        LOGGER.info(" getUserDetailsFromLoginScreen custuser ---------------------------------------------------------------- "+custuser);
        
        if(custuser!=null){
            if(custuser!=null){
                request.getPortletSession().setAttribute(sessionDataKey,custuser);
                if(custuser.getBelkUser()!=null){
                    LOGGER.info(" inside getbelk user "+custuser.getBelkUser());
                    
                }else if(custuser.getVpUser()!=null){
                    LOGGER.info(" inside getVpUser  user "+custuser.getVpUser());
                 
                }
                
            }
            
        }
       }
        
    }



    /**
     * This method will populate the DepartmentDetails object from the Department Domain Object array
     * @param depList
     * @return
     */
    private List<DepartmentDetails> populateDepartmentDetailsFromDB(
        ArrayList depList) {
    LOGGER.info("WorkListDisplayController:populateDepartmentDetailsFromDB:Enter");
        ArrayList departmentDetailsList = new ArrayList();
        if(null != depList && depList.size()>0){
            PepDepartment dep = null;
            for(int i=0;i<depList.size();i++){
                DepartmentDetails departmentDetails =new DepartmentDetails();
                dep = (PepDepartment) depList.get(i); 
                LOGGER.info("WorkListDisplayController:populateDepartmentDetailsFromDB:dep.getDeptId()"+dep.getId().getDeptId());
                LOGGER.info("WorkListDisplayController:populateDepartmentDetailsFromDB:dep.getDeptDescription()"+dep.getDeptName());
                departmentDetails.setId(dep.getId().getDeptId());
                departmentDetails.setDesc(dep.getDeptName());
                departmentDetailsList.add(departmentDetails);
            }
        }
        LOGGER.info("WorkListDisplayController:populateDepartmentDetailsFromDB:Exit");
        return departmentDetailsList;
    }

  
/**
 * This method will handle the Event request and to fetch the user details from the login portlet
 * 
 */

    public void handleEventRequest(EventRequest request, EventResponse response)
            throws Exception {
        LOGGER.info("WorkListDisplayController:handleEventRequest:Enter "); 
        Event event = request.getEvent();
        String loggedInUser = "";
        PortletSession portletSession = request.getPortletSession();
        if(event.getName()!=null ){
            
            LOGGER.info("WorkListDisplayController:handlingPagination:Enter 1 "); 
        if(event.getName().equals(WorkListDisplayConstants.USER_DATA_OBJ)){ 
            LOGGER.info("WorkListDisplayController:handlingPagination:Enter 2 "); 
            UserData custuser= (UserData) event.getValue();
            
                      
            if(null!=custuser){     
                Common_BelkUser belkUser = ( Common_BelkUser)custuser.getBelkUser();
             LOGGER.info("WorkListDisplayController:handlingPagination:Enter 2 ");
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
          
             String sessionDataKey = (WorkListDisplayConstants.USER_DATA+request.getPortletSession().getId()+loggedInUser);
             String formSessionKey = request.getPortletSession().getId()+loggedInUser;
             String listSessionKey = "LIST"+request.getPortletSession().getId()+loggedInUser;
             
             LOGGER.info(" loggedInUser **************"+formSessionKey);
             
                    portletSession.setAttribute("formSessionKey", formSessionKey);
                    portletSession.setAttribute("sessionDataKey", sessionDataKey);
                    portletSession.setAttribute(sessionDataKey, custuser);
        }
            } else if (event.getName().equals(WorkListDisplayConstants.EVENT_PAGINATION)) {
                PageAnchorDetails pageAnchorDetails = (PageAnchorDetails) event.getValue();
                portletSession.setAttribute("pageAnchorDetails", pageAnchorDetails);
                portletSession.setAttribute("paginationFlow", "true");
        }
        
        }
    }

/**
 * This method will take care of handleResourceRequest, handles all Ajax calls
 */

    @Override
    public ModelAndView handleResourceRequest(ResourceRequest request,
        ResourceResponse response) throws Exception {
        Properties prop= PropertiesFileLoader.getExternalLoginProperties();
        int maxResults=Integer.parseInt(prop.getProperty(WorkListDisplayConstants.PAGE_LIMIT));
        
        if((String)request.getPortletSession().getAttribute("formSessionKey")!=null)
        {
            
        String formSessionKey =  (String)request.getPortletSession().getAttribute("formSessionKey");
        WorkListDisplayForm resourceForm =  (WorkListDisplayForm)request.getPortletSession().getAttribute(formSessionKey);
        String sessionDataKey =  (String)request.getPortletSession().getAttribute("sessionDataKey");//TODO
        UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);//TODO
       
        
        String loggedInUser= request.getParameter("loggedInUser");  
        
        LOGGER.info(" ----loggedInUser -------- "+loggedInUser);
        if(loggedInUser!=null && !loggedInUser.isEmpty()){
            invalidateUserSession(request,
                response);
            
        }
        
        LOGGER.info(" handleResourceRequest formSessionKey ---------------------------------------------------------------- "+formSessionKey);
        LOGGER.info(" handleResourceRequest resourceForm ---------------------------------------------------------------- "+resourceForm);
        LOGGER.info(" handleResourceRequest sessionDataKey ---------------------------------------------------------------- "+sessionDataKey);
        LOGGER.info(" handleResourceRequest custuser ---------------------------------------------------------------- "+custuser);
        
       //GetChild data method
        String callType = request.getParameter(WorkListDisplayConstants.CALL_TYPE_PARAMETER);
        LOGGER.info(" 1373handleResourceRequest callType ---------------------------------------------------------------- "+callType);
        if("getChildData".equalsIgnoreCase(callType)
                || WorkListDisplayConstants.GET_CHILD_GROUP.equalsIgnoreCase(callType)){ 
            getChildData(request, response);
        }
        
        LOGGER.info(" GetChild data method callType:"+callType);
        //57 Search
        List<String> supplierIdList = null;
        if(custuser!=null && custuser.getVpUser()!=null){
           supplierIdList =  custuser.getVpUser().getSupplierIdsList();
        }
        if(resourceForm == null){
            resourceForm = new WorkListDisplayForm();
            // Populate User Details
            setUserDataToForm(resourceForm, custuser);
        }
        
        //Get the Worklist 
        String workListType =
            (String) request.getPortletSession().getAttribute(WorkListDisplayConstants.GROUP_WORKLIST_SESSION);
        ArrayList deptList = (ArrayList) resourceForm.getSelectedDepartmentFromDB();
        String fromPage = request.getParameter(WorkListDisplayConstants.FROM_PAGE);
        
        List workFlowList = resourceForm.getWorkFlowlist();
        resourceForm.setWorkFlowlist(workFlowList);
        
        //Start of Missing Asset Flow
        String missingAsset = request.getParameter(WorkListDisplayConstants.MISSING_ASSET_VAR);   
        String orinVal = request.getParameter(WorkListDisplayConstants.ORIN_NUM);
        
        if(missingAsset != null && orinVal!=null && !missingAsset.trim().equals(WorkListDisplayConstants.EMPTY_STRING) && !orinVal.trim().equals(WorkListDisplayConstants.EMPTY_STRING))
        {
            LOGGER.info("Missing Assert Update Starts with Orin :" + orinVal + " MissingAsset : "+missingAsset); 
            orinVal = orinVal.replaceAll("\\s+", "");
            
            if(orinVal.length() > 12){                
                orinVal = orinVal.substring(0, 12);
                LOGGER.info("Missing Asset Orin Length gt 12 after trim-->" + orinVal);                
            }
            changeMissingAsset(missingAsset, orinVal);
        }
        //End of Missing Asset Flow        
        
        //Inactivate and Activate flow
        String statusParameter = request.getParameter(WorkListDisplayConstants.PET_STATUS_PARAMETER);
        
        //PET LOCKING FUNCTIONALITY.
        String lockedPet = request.getParameter("lockedPet");
        String lockedPettype = request.getParameter("lockedPettype");
        LOGGER.info("lockedPet *******************:::"+ lockedPet);
        if(lockedPet!=null){
         isPetLocked( request,
              response,lockedPet,lockedPettype);
        }
        
        LOGGER.info("statusParameter:::"+ statusParameter);
        LOGGER.info("Search flag for inactivation::: "+request
                    .getParameter(WorkListDisplayConstants.INACTIVATE_ACTIVATE_GROUP_SEARCH_FLAG));
        if(statusParameter != null && !statusParameter.trim().equals(WorkListDisplayConstants.EMPTY_STRING))
        {
            String orinNumbers =            
                request
                    .getParameter(WorkListDisplayConstants.SELECTED_ROWS_FOR_ACTIVATE_OR_INACTIVATE);
            String orinNumbersArray[] = null;
            StringBuffer styleArray = new StringBuffer();
            StringBuffer groupArray = new StringBuffer();
            if (null != orinNumbers) {
                orinNumbersArray =
                    orinNumbers.split(WorkListDisplayConstants.COMMA);
            }
            for (String orinDetails : orinNumbersArray)
            {
                String orin = orinDetails.split(WorkListDisplayConstants.COLON)[0].toString().trim();
                String group = orinDetails.split(WorkListDisplayConstants.COLON)[1].toString().trim();
                if(group != null && group.equalsIgnoreCase(WorkListDisplayConstants.YES_Y))
                {
                    groupArray.append(orin + WorkListDisplayConstants.COMMA);
                }
                else
                {
                    styleArray.append(orin + WorkListDisplayConstants.COMMA); 
                }
            }
            if(groupArray.length() > 0)
            {
                LOGGER.info("GROUPS TO INACTIVATE: " + groupArray);
                if (WorkListDisplayConstants.ACTIVATE_PET_ACTION
                        .equalsIgnoreCase(statusParameter))
                {
                    activateGroups(request, groupArray.toString());
                }                
                if (WorkListDisplayConstants.INACTIVATE_PET_ACTION
                        .equalsIgnoreCase(statusParameter))
                {
                    inActivateGroups(request, groupArray.toString());
                }
            }
            if(styleArray.length() > 0)
            {
                LOGGER.info("REGULAR ITEMS TO ACTIVATE: " + styleArray);
                if (WorkListDisplayConstants.ACTIVATE_PET_ACTION
                        .equalsIgnoreCase(statusParameter))
                {
                    activatePets(request, styleArray.toString());
                }                
                if (WorkListDisplayConstants.INACTIVATE_PET_ACTION
                        .equalsIgnoreCase(statusParameter))
                {
                    inActivatePets(request, styleArray.toString());
                }            
            }  
        }
        
        String pageNo ="1";
        //Pagination Flow for Advance Search
        if ((WorkListDisplayConstants.YES_VALUE.equalsIgnoreCase(resourceForm.getAdvSearchClicked()) ||
        		WorkListDisplayConstants.YES_VALUE.equalsIgnoreCase(resourceForm.getSearchClicked())) && 
        		(fromPage != null && fromPage.equals(WorkListDisplayConstants.PAGINATION) && null!=request.getParameter(WorkListDisplayConstants.AJAX_PAGE_NO) 
        		&& request.getParameter(WorkListDisplayConstants.AJAX_PAGE_NO).length()>0)) {
        	pageNo = request.getParameter(WorkListDisplayConstants.AJAX_PAGE_NO);
        	resourceForm.setSearchResultsReturned(WorkListDisplayConstants.YES_VALUE);
        	getPetsWithAdvanceSearch(request, callType, resourceForm, workFlowList, supplierIdList, prop, Integer.parseInt(pageNo), resourceForm.getSearchClicked());
        }
        //Sorting Flow for Advance Search
        else if((WorkListDisplayConstants.YES_VALUE.equalsIgnoreCase(resourceForm.getAdvSearchClicked()) ||
        		WorkListDisplayConstants.YES_VALUE.equalsIgnoreCase(resourceForm.getSearchClicked())) && 
        		null!=request.getParameter(WorkListDisplayConstants.AJAX_SELECTED_COLUMN_NAME) 
                && request.getParameter(WorkListDisplayConstants.AJAX_SELECTED_COLUMN_NAME).length()>0) {
        	String selectedColumn = request.getParameter(WorkListDisplayConstants.AJAX_SELECTED_COLUMN_NAME);
        	int totalNumberOfPets = resourceForm.getFullWorkFlowlist().size();
            if (resourceForm.getTotalNumberOfPets()!=null) {
            	totalNumberOfPets = Integer.parseInt(resourceForm.getTotalNumberOfPets());
            }
            if (WorkListDisplayConstants.GROUPINGS
                .equalsIgnoreCase(workListType)) {
                resourceForm.setWorkFlowlist(resourceForm.getWorkFlowlist());
                resourceForm.setFullWorkFlowlist(resourceForm.getFullWorkFlowlist());
                resourceForm.setSelectedColumn(selectedColumn);
                String fromPageSource = request.getParameter(WorkListDisplayConstants.FROM_PAGE);
                if (fromPageSource == null
                    || !fromPageSource.equals(WorkListDisplayConstants.PAGINATION)) {
                	handlingSortingRender(selectedColumn, resourceForm,
                            resourceForm.getFullWorkFlowlist());
                    handlingPaginationRenderGroups(1, resourceForm,
                        workFlowList, totalNumberOfPets);
                }
            }
            else {
                /**
                 * 031916 afuszr6 for 1012
                 */
                // handlingSortingRender(selectedColumn, resourceForm,
                // workFlowList);
                handlingSortingRender(selectedColumn, resourceForm,
                    resourceForm.getFullWorkFlowlist());
                
                handlingPaginationRender(1, resourceForm, resourceForm.getFullWorkFlowlist());// fix
                                                                        // for
                                                                        // 496
            }
        }
        
        //Pagination flow for Dept search
        else if(fromPage != null && fromPage.equals(WorkListDisplayConstants.PAGINATION) && null!=request.getParameter(WorkListDisplayConstants.AJAX_PAGE_NO) 
        		&& request.getParameter(WorkListDisplayConstants.AJAX_PAGE_NO).length()>0){
            pageNo = request.getParameter(WorkListDisplayConstants.AJAX_PAGE_NO);
            LOGGER.info("Page Number="+pageNo);
            int selectedPageNumber = Integer.parseInt(pageNo);
            
            List<DepartmentDetails> departmentDetailsListToLoadPet = resourceForm.getSelectedDepartmentFromDB();
            if (departmentDetailsListToLoadPet==null) {
            	String depNo = resourceForm.getDeptNo();
            	if (depNo!=null && !depNo.equals(WorkListDisplayConstants.EMPTY_STRING)) {
            		DepartmentDetails departmentDetails = new DepartmentDetails();
                    departmentDetails.setId(depNo);
                    departmentDetailsListToLoadPet = new ArrayList<DepartmentDetails>();
                    departmentDetailsListToLoadPet.add(departmentDetails);  
            	}
            }
            
            workFlowList = getWorkFlowListFromDB(resourceForm, workListType, departmentDetailsListToLoadPet, 
            		resourceForm.getVendorEmail(), supplierIdList, selectedPageNumber, maxResults);
            resourceForm.setWorkFlowlist(workFlowList);
            
            //Changes for multi Supplier id end
            if(workFlowList != null && workFlowList.size()>0){
                resourceForm.setPetNotFound(null); 
            }else{//There is no PET for searched content
                resourceForm.setPetNotFound(prop.getProperty(WorkListDisplayConstants.PET_NOT_FOUND)); 
                resourceForm.setTotalNumberOfPets("0");//Setting the pet count to Zero
            }
            
             //Fix for Defect 218 & 263 Start
            if (workFlowList != null) {
            	resourceForm.setTotalNumberOfPets(String.valueOf(workFlowList.size()));
            }
            handlingPaginationRender(selectedPageNumber,resourceForm, workFlowList);// fix for 496                     
            resourceForm.setSelectedPage(String.valueOf(selectedPageNumber));        
        }
        
        //Sorting Flow for Dept search
        else if(null!=request.getParameter(WorkListDisplayConstants.AJAX_SELECTED_COLUMN_NAME) 
                && request.getParameter(WorkListDisplayConstants.AJAX_SELECTED_COLUMN_NAME).length()>0) {
            String selectedColumn = request.getParameter(WorkListDisplayConstants.AJAX_SELECTED_COLUMN_NAME);
            LOGGER.info("Selected Column is="+selectedColumn);
            
            handlingSortingRender(selectedColumn, resourceForm, workFlowList);
            
            LOGGER.info("Selected Column is="+selectedColumn);
            
            int selectedPageNumber = Integer.parseInt(pageNo);
            
            List<DepartmentDetails> departmentDetailsListToLoadPet = resourceForm.getSelectedDepartmentFromDB();
            if (departmentDetailsListToLoadPet==null) {
            	String depNo = resourceForm.getDeptNo();
            	if (depNo!=null && !depNo.equals(WorkListDisplayConstants.EMPTY_STRING)) {
            		DepartmentDetails departmentDetails = new DepartmentDetails();
                    departmentDetails.setId(depNo);
                    departmentDetailsListToLoadPet = new ArrayList<DepartmentDetails>();
                    departmentDetailsListToLoadPet.add(departmentDetails);  
            	}
            }
            
            workFlowList = getWorkFlowListFromDB(resourceForm, workListType, departmentDetailsListToLoadPet, 
            		resourceForm.getVendorEmail(), supplierIdList, selectedPageNumber, maxResults);
            resourceForm.setWorkFlowlist(workFlowList);
            
            //Changes for multi Supplier id end
            if(workFlowList != null && workFlowList.size()>0){
                resourceForm.setPetNotFound(null); 
            }else{//There is no PET for searched content
                resourceForm.setPetNotFound(prop.getProperty(WorkListDisplayConstants.PET_NOT_FOUND)); 
                resourceForm.setTotalNumberOfPets("0");//Setting the pet count to Zero
            }
            
            //Fix for Defect 218 & 263 Start
            if (workFlowList!=null) {
            	resourceForm.setTotalNumberOfPets(String.valueOf(workFlowList.size()));
            }
            handlingPaginationRender(selectedPageNumber,resourceForm, workFlowList);// fix for 496    
            resourceForm.setSelectedPage(String.valueOf(selectedPageNumber));
        }
        
        // Department Flow
        else if(null!=request.getParameter(WorkListDisplayConstants.AJAX_DEPARTMENT_OPERATION) 
                && request.getParameter(WorkListDisplayConstants.AJAX_DEPARTMENT_OPERATION).length()>0){
        	String departmentOperation = request.getParameter(WorkListDisplayConstants.AJAX_DEPARTMENT_OPERATION);
            // Department save and close Flow
                if(WorkListDisplayConstants.DEP_SAVE_CLOSE.equalsIgnoreCase(departmentOperation)){
                    LOGGER.info("Handling Save and Close operation.operation..." + departmentOperation+" advSearchClick:"+request.getParameter(WorkListDisplayConstants.SEARCH_CLICKED));
                    resourceForm.setSearchClicked("no");
                    if(null!=request.getParameter("selectedDepartments")&& request.getParameter("selectedDepartments").length()>0){
                    String selectedDepartments =  request.getParameter("selectedDepartments"); 
                    LOGGER.info("Departmentts.." + selectedDepartments);
                    //Get the department details array by using comma separator
                    
                    //574 Additional Fix on dept removal and render to page Start                    
                    AdvanceSearch adSearch = resourceForm.getAdvanceSearch();
                    if(null!=adSearch){
                        if(null!=adSearch.getDeptNumbers() && !adSearch.getDeptNumbers().equals("")){
                            adSearch.setDeptNumbers(selectedDepartments);
                            resourceForm.setAdvanceSearch(adSearch);
                        }
                    }
                    //574 Additional Fix on dept removal and render to page End
                    
                    
                    String[] selectedDeptArray = selectedDepartments.split(",");
                    List <PepDepartment> updatedPePDetailsToDb = new ArrayList();
                    //Getting the department details using id if existing in the current DB and to add new Department details
                    if(workFlowList!=null) {
                        resourceForm.setTotalNumberOfPets(String.valueOf(workFlowList.size()));
                    } else {
                        resourceForm.setTotalNumberOfPets("0");
                    }
                    
                    boolean skipIfFoundExistingDept = false;
                    boolean skipIfFoundSecondSection = false;
                    for(int i=0;i<selectedDeptArray.length;i++){
                        skipIfFoundExistingDept = false;
                        skipIfFoundSecondSection = false;
                        LOGGER.info("Departmentts. in for." + selectedDeptArray[i]);
                        if(null!=resourceForm.getSelectedPepDepartmentFromDB()){
                            LOGGER.info("Department is from PEP details from DB********************" );
                            for(int j=0;j<resourceForm.getSelectedPepDepartmentFromDB().size();j++){
                                PepDepartment pepdetails =resourceForm.getSelectedPepDepartmentFromDB().get(j);
                                if(pepdetails.getId().getDeptId().equalsIgnoreCase(selectedDeptArray[i].trim())){
                                    updatedPePDetailsToDb.add(pepdetails);  
                                    LOGGER.info("Adding PepDept details"+pepdetails.getId().getDeptId());
                                    skipIfFoundExistingDept = true;
                                }
                               
                            }
                        }
                        // Adding the newly added Department details
                        if(!skipIfFoundExistingDept){
                        if(null!=resourceForm.getSearchedDeptdetailsFromADSE()){
                            LOGGER.info("Department is from PEP details Search********************" );
                            for(int k=0;k<resourceForm.getSearchedDeptdetailsFromADSE().size();k++){
                                DepartmentDetails deptDetails =resourceForm.getSearchedDeptdetailsFromADSE().get(k);
                                if(deptDetails.getId().equalsIgnoreCase(selectedDeptArray[i].trim())){
                                    //Creating new PepDepartmentDetails to update in DB
                                    PepDepartment pepDetails = new PepDepartment();
                                    pepDetails.setDeptName(deptDetails.getDesc());
                                    PepDepartmentPK pepDepartmentPk= new PepDepartmentPK();
                                    pepDepartmentPk.setDeptId(deptDetails.getId());
                                    pepDepartmentPk.setPepUserId(resourceForm.getPepUserID());
                                    pepDetails.setId(pepDepartmentPk);
                                    updatedPePDetailsToDb.add(pepDetails);  
                                    LOGGER.info("Adding PepDept details"+pepDetails.getId().getDeptId());
                                    skipIfFoundSecondSection = true;
                                 }
                           }
                         }
                        
                      //Adding the Searched Department for Initial time login added
                        if(!skipIfFoundSecondSection){
                            if(null!=resourceForm.getFirstTimesearchedDeptdetailsFromADSE()){
                                LOGGER.info("Department is from PEP details first time *******************" );
                                for(int k=0;k<resourceForm.getFirstTimesearchedDeptdetailsFromADSE().size();k++){
                                    DepartmentDetails deptDetails =resourceForm.getFirstTimesearchedDeptdetailsFromADSE().get(k);
                                    if(deptDetails.getId().equalsIgnoreCase(selectedDeptArray[i].trim())){
                                        //Creating new PepDepartmentDetails to update in DB
                                        PepDepartment pepDetails = new PepDepartment();
                                        pepDetails.setDeptName(deptDetails.getDesc());
                                        PepDepartmentPK pepDepartmentPk= new PepDepartmentPK();
                                        pepDepartmentPk.setDeptId(deptDetails.getId());
                                        pepDepartmentPk.setPepUserId(resourceForm.getPepUserID());
                                        pepDetails.setId(pepDepartmentPk);
                                        updatedPePDetailsToDb.add(pepDetails);  
                                        LOGGER.info("Adding PepDept details"+pepDetails.getId().getDeptId());
                                     }
                               }
                             }
                            }
                        }
                    }
                    
                   //Sending the updated pepdetails list to DB layer for update operation
                    boolean isPepDeptUpdated = workListDisplayDelegate.updatePepDeptDetails(updatedPePDetailsToDb,resourceForm.getPepUserID());
                    LOGGER.info("isPepDeptUpdated="+isPepDeptUpdated);
                    if(isPepDeptUpdated){//Table is updated
                        //Setting the PepDepartment details into the FORM
                        resourceForm.setSelectedPepDepartmentFromDB(updatedPePDetailsToDb);
                      //updating the new Department details to display in department filter
                        LOGGER.info("After Table update");
                        ArrayList updatedDepartmentFromDB = new ArrayList();
                        for(int i=0;i<updatedPePDetailsToDb.size();i++){
                            DepartmentDetails depDetails = new DepartmentDetails();  
                            depDetails.setId(updatedPePDetailsToDb.get(i).getId().getDeptId());
                            depDetails.setDesc(updatedPePDetailsToDb.get(i).getDeptName());
                            updatedDepartmentFromDB.add(depDetails);
                        }
                        resourceForm.setSelectedDepartmentFromDB(updatedDepartmentFromDB);
                        //Clearing the Search result operation from Form
                        List closeSearchedDeptdetailsFromADSE = new ArrayList();
                        resourceForm.setSearchedDeptdetailsFromADSE(closeSearchedDeptdetailsFromADSE);
                        
                      //Clearing the first time Search result operation from Form
                        List closeFirstSearchedDeptdetailsFromADSE = new ArrayList();
                        resourceForm.setFirstTimesearchedDeptdetailsFromADSE(closeFirstSearchedDeptdetailsFromADSE);
                        // Clearing if there is result not found is there
                        resourceForm.setDeptSearchResult("");
                        
                       // Getting the PET details on base of departments
                        
                        //Changes for multi Supplier id Start
                        if(custuser!=null && custuser.getVpUser()!=null){
                           supplierIdList =  custuser.getVpUser().getSupplierIdsList(); 
                        }
                        
                        int selectedPageNumber = Integer.parseInt(pageNo);
                        workFlowList = getWorkFlowListFromDB(resourceForm, workListType, updatedDepartmentFromDB, 
                        		resourceForm.getVendorEmail(), supplierIdList, selectedPageNumber, maxResults);
                        resourceForm.setWorkFlowlist(workFlowList);
                        
                        //Changes for multi Supplier id end
                        if(workFlowList != null && workFlowList.size()>0){
                            resourceForm.setPetNotFound(null); 
                        }else{//There is no PET for searched content
                            resourceForm.setPetNotFound(prop.getProperty(WorkListDisplayConstants.PET_NOT_FOUND)); 
                            resourceForm.setTotalNumberOfPets("0");//Setting the pet count to Zero
                        }
                        
                         //Fix for Defect 218 & 263 Start
                        resourceForm.setTotalNumberOfPets(String.valueOf(workFlowList.size()));
                        handlingPaginationRender(selectedPageNumber,resourceForm, workFlowList);// fix for 496                    
                        resourceForm.setSelectedPage(String.valueOf(selectedPageNumber));
                        //Fix for Defect 218 & 263 Start Ends
                    }else{
                        LOGGER.info("Table is not updated");
                    }
                    
                }
            }
                //Department Search operation
                if(WorkListDisplayConstants.DEP_SEARCH.equalsIgnoreCase(departmentOperation)){
                    //keeping the current department list in the form
                    List<DepartmentDetails> departmentDetailsListToLoadPet=(ArrayList<DepartmentDetails>)resourceForm.getSelectedDepartmentFromDB();
                    //Clearing the Existing searched Result
                    ArrayList emptySearchedDeptdetailsFromADSE = new ArrayList();
                    resourceForm.setSearchedDeptdetailsFromADSE(emptySearchedDeptdetailsFromADSE);
                    resourceForm.setDeptSearchResult("");
                    String departmentsToBesearched = null;
                    LOGGER.info("Handling Save and Close operation.operation." + departmentOperation);
                    if(null!=request.getParameter(WorkListDisplayConstants.DEP_TO_SEARCH)&& request.getParameter(WorkListDisplayConstants.DEP_TO_SEARCH).length()>0){
                    departmentsToBesearched =  request.getParameter(WorkListDisplayConstants.DEP_TO_SEARCH); 
                    LOGGER.info("Departmentts.." + departmentsToBesearched);
                    List searchedDeptdetailsFromADSE = workListDisplayDelegate.getDepartmentDetailsFromADSE(departmentsToBesearched);
                    List<String> selectedDeptsList=null;
                    if(null!=request.getParameter("selectedDepartments")&& request.getParameter("selectedDepartments").length()>0) {
                        
                        String selectedDepts =  request.getParameter("selectedDepartments");                       
                        //Get the department details array by using comma separator
                        String[] selectedDeptArray = selectedDepts.split(",");   
                        
                        selectedDeptsList = new ArrayList<String>(Arrays.asList(selectedDeptArray));
                        
                    }
                    LOGGER.info("::::selectedDeptsList in AdvSearch::::::" +selectedDeptsList);
                    boolean isDuplicateDeptAdded = false;
                    if(selectedDeptsList !=null && selectedDeptsList.size() > 0) {                        
                        int iDupDeptFound=0;
                       // for(String selString :selectedDeptsList ) {
                            
                            Iterator<DepartmentDetails> iter = searchedDeptdetailsFromADSE.iterator();
                            while (iter.hasNext()) {
                                DepartmentDetails departmentDetails = (DepartmentDetails)iter.next();
                                
                                String id =  departmentDetails.getId();
                                for(DepartmentDetails ddObj : departmentDetailsListToLoadPet){
                                    
                                    if(ddObj.getId()!=null && id.trim().equals(ddObj.getId().trim())){
                                        LOGGER.info("------------TESTING1111 .. removing " + id.trim());
                                        iter.remove();
                                        iDupDeptFound++;   
                                    }
                                }
                            }
                            
                        if(iDupDeptFound > 0) {
                            isDuplicateDeptAdded = true;
                        }
                       
                        
                    }
                    //For advance Search
                    LOGGER.info("advanceSearchClicked++++++++++>>>" + request.getParameter(WorkListDisplayConstants.ADV_SEARCH_CLICKED));
                    String advanceSearchClicked = WorkListDisplayConstants.ADV_SEARCH_NO_VALUE;
                    advanceSearchClicked =  request.getParameter(WorkListDisplayConstants.ADV_SEARCH_CLICKED);
                   
                    LOGGER.info("advanceSearchClicked++++++++++" + advanceSearchClicked);
                    resourceForm.setAdvSearchClicked(advanceSearchClicked);
                    LOGGER.info("AdvSearch Clicked Values11111::" +resourceForm.getAdvSearchClicked());
                    if(searchedDeptdetailsFromADSE.size()>0){
                        resourceForm.setSearchedDeptdetailsFromADSE(searchedDeptdetailsFromADSE);
                        LOGGER.info(":::::::resourceForm.setSearchedDeptdetailsFromADSE:::" + resourceForm.getSearchedDeptdetailsFromADSE());
                    }else{
                        if(!isDuplicateDeptAdded) {
                            resourceForm.setDeptSearchResult(prop.getProperty(WorkListDisplayConstants.WORKLIST_DEPT_NOT_FOUND_MSG));
                        }
                    }
                   }
                  }
                // Close operation
                if(WorkListDisplayConstants.DEP_CLOSE.equalsIgnoreCase(departmentOperation)){
                    LOGGER.info("Handling Close operation.operation." + departmentOperation);
                    List closeSearchedDeptdetailsFromADSE = new ArrayList();
                    resourceForm.setSearchedDeptdetailsFromADSE(closeSearchedDeptdetailsFromADSE);
                    resourceForm.setDeptSearchResult("");
                   }
                //Clear operation
                if(WorkListDisplayConstants.DEP_CLEAR.equalsIgnoreCase(departmentOperation)){
                    LOGGER.info("Handling clear operation.operation." + departmentOperation);
                    List closeSearchedDeptdetailsFromADSE = new ArrayList();
                    resourceForm.setSearchedDeptdetailsFromADSE(closeSearchedDeptdetailsFromADSE);
                    resourceForm.setDeptSearchResult("");
                    
                   }
        }
        
        
        
        //Fix for Defect 748 Issue 1 Start AFUSZR6
        else if(null!=request.getParameter("advSearchPopUp")){
        	LOGGER.info("Advance Search Operation" + request.getParameter(WorkListDisplayConstants.ADV_SEARCH_OPERATION_PARAM));
            LOGGER.info("Call Class Details block::");
            
            String departDetails = request.getParameter("defaultDeptNos");
            String advDepartDetails = request.getParameter("advSearchDept");
            String dateFrom = request.getParameter("completionDateFrom");
            String dateTo = request.getParameter("completionDateTo");
            String imageStatus = request.getParameter("imageStatus");
            String contentStatus = request.getParameter("contentStatus");
            String petStatus =  request.getParameter("petStatus");
            String requestType =  request.getParameter("requestType");
           
            AdvanceSearch adSearch = resourceForm.getAdvanceSearch();
            if(null!=adSearch){
                if(null!=adSearch.getDeptNumbers() && !adSearch.getDeptNumbers().equals("")){
                    adSearch.setDeptNumbers(advDepartDetails);                    
                    resourceForm.setAdvanceSearch(adSearch);
                }
            }
            LOGGER.info("departDetails:::" + departDetails);
            
            
            resourceForm.getAdvanceSearch().setDateFrom(dateFrom);
            resourceForm.getAdvanceSearch().setDateTo(dateTo);
            resourceForm.getAdvanceSearch().setImageStatus(imageStatus);
            resourceForm.getAdvanceSearch().setContentStatus(contentStatus);
            resourceForm.getAdvanceSearch().setActive(petStatus);
            resourceForm.getAdvanceSearch().setRequestType(requestType);
            List<ClassDetails> classDetailsList1 = new ArrayList<ClassDetails>();
            classDetailsList1 = workListDisplayDelegate.getClassDetailsByDepNos(departDetails);
            populateClassDetailsInAdvanceSearch(classDetailsList1, resourceForm);
        }//Fix for Defect 748 Issue 1 End AFUSZR6
        
        //Advance Search Flow
        else if(null!=request.getParameter(WorkListDisplayConstants.ADV_SEARCH_OPERATION_PARAM) 
                && request.getParameter(WorkListDisplayConstants.ADV_SEARCH_OPERATION_PARAM).length()>0){
            String advSearchOperation=request.getParameter(WorkListDisplayConstants.ADV_SEARCH_OPERATION_PARAM);
            LOGGER.info("This is from Advance Search Operation" + advSearchOperation);   
            if(WorkListDisplayConstants.ADV_SEARCH_OPERATION_RESET.equals(advSearchOperation)){
                LOGGER.info("This is from Advance Search Reset operation");
                populatingAdvanceSearchDefaultValues(resourceForm);
            }
            if(WorkListDisplayConstants.ADV_SEARCH_OPERATION_SAVEANDCLOSE.equals(advSearchOperation)){
                LOGGER.info("This is from Advance Search Save and Close operation");
                String departmentDetails = request.getParameter("advSelectedDepartments");
                LOGGER.info("Department Details  Advance Search and close operation"+departmentDetails);
                //--------------------------574 Start ----------------------------------------//
                
                String[] selectedDeptArray = departmentDetails.split(",");
                LOGGER.info("-----------selectedDeptArray Length in ADVSEACRH-----------" + selectedDeptArray.length);
                List <PepDepartment> updatedPePDetailsToDb = new ArrayList();
                //Getting the department details using id if existing in the current DB and to add new Department details
                if(workFlowList!=null) {
                    resourceForm.setTotalNumberOfPets(String.valueOf(workFlowList.size()));
                } else {
                    resourceForm.setTotalNumberOfPets("0");
                }
                
                boolean skipIfFoundExistingDept1 = false;
                boolean skipIfFoundSecondSection1 = false;
                for(int i=0;i<selectedDeptArray.length;i++){
                    skipIfFoundExistingDept1 = false;
                    skipIfFoundSecondSection1 = false;
                    LOGGER.info("Departmentts. in for.ADVSEARCH" + selectedDeptArray[i]);
                    if(null!=resourceForm.getSelectedPepDepartmentFromDB()){
                        LOGGER.info("Department is from PEP details from DB in ADVSEACH********************" );
                        for(int j=0;j<resourceForm.getSelectedPepDepartmentFromDB().size();j++){
                            PepDepartment pepdetails =resourceForm.getSelectedPepDepartmentFromDB().get(j);
                            if(pepdetails.getId().getDeptId().equalsIgnoreCase(selectedDeptArray[i].trim())){
                                updatedPePDetailsToDb.add(pepdetails);  
                                LOGGER.info("Adding PepDept details in AdvSeach::::::"+pepdetails.getId().getDeptId());
                                skipIfFoundExistingDept1 = true; 
                            }
                           
                        }
                    }
                    // Adding the newly added Department details
                    if(!skipIfFoundExistingDept1){
                    if(null!=resourceForm.getSearchedDeptdetailsFromADSE()){
                        LOGGER.info("Department is from PEP details ----ADVSEACR---Search********************" );
                        for(int k=0;k<resourceForm.getSearchedDeptdetailsFromADSE().size();k++){
                            DepartmentDetails deptDetails =resourceForm.getSearchedDeptdetailsFromADSE().get(k);
                            if(deptDetails.getId().equalsIgnoreCase(selectedDeptArray[i].trim())){
                                //Creating new PepDepartmentDetails to update in DB
                                PepDepartment pepDetails = new PepDepartment();
                                pepDetails.setDeptName(deptDetails.getDesc());
                                PepDepartmentPK pepDepartmentPk= new PepDepartmentPK();
                                pepDepartmentPk.setDeptId(deptDetails.getId());
                                pepDepartmentPk.setPepUserId(resourceForm.getPepUserID());
                                pepDetails.setId(pepDepartmentPk);
                                updatedPePDetailsToDb.add(pepDetails);  
                                LOGGER.info("Adding PepDept details"+pepDetails.getId().getDeptId());
                                skipIfFoundSecondSection1 = true;
                             }
                       }
                     }
                    
                  //Adding the Searched Department for Initial time login added
                    if(!skipIfFoundSecondSection1){
                        if(null!=resourceForm.getFirstTimesearchedDeptdetailsFromADSE()){
                            LOGGER.info("Department is from PEP details first time *******************" );
                            for(int k=0;k<resourceForm.getFirstTimesearchedDeptdetailsFromADSE().size();k++){
                                DepartmentDetails deptDetails =resourceForm.getFirstTimesearchedDeptdetailsFromADSE().get(k);
                                if(deptDetails.getId().equalsIgnoreCase(selectedDeptArray[i].trim())){
                                    //Creating new PepDepartmentDetails to update in DB
                                    PepDepartment pepDetails = new PepDepartment();
                                    pepDetails.setDeptName(deptDetails.getDesc());
                                    PepDepartmentPK pepDepartmentPk= new PepDepartmentPK();
                                    pepDepartmentPk.setDeptId(deptDetails.getId());
                                    pepDepartmentPk.setPepUserId(resourceForm.getPepUserID());
                                    pepDetails.setId(pepDepartmentPk);
                                    updatedPePDetailsToDb.add(pepDetails);  
                                    LOGGER.info("-----Adding PepDept details in ADVSEACRH-----"+pepDetails.getId().getDeptId());
                                 }
                           }
                         }
                        }
                    }
                }
                
               //Sending the updated pepdetails list to DB layer for update operation
                boolean isPepDeptUpdated = workListDisplayDelegate.updatePepDeptDetails(updatedPePDetailsToDb,resourceForm.getPepUserID());
                LOGGER.info("isPepDeptUpdated="+isPepDeptUpdated);
                if(isPepDeptUpdated){//Table is updated
                    //Setting the PepDepartment details into the FORM
                    resourceForm.setSelectedPepDepartmentFromDB(updatedPePDetailsToDb);
                  //updating the new Department details to display in department filter
                    LOGGER.info("---------------After Table update in ADVSEARCH----------------");
                    ArrayList updatedDepartmentFromDB = new ArrayList();
                    for(int i=0;i<updatedPePDetailsToDb.size();i++){
                        DepartmentDetails depDetails = new DepartmentDetails();  
                        depDetails.setId(updatedPePDetailsToDb.get(i).getId().getDeptId());
                        depDetails.setDesc(updatedPePDetailsToDb.get(i).getDeptName());
                        updatedDepartmentFromDB.add(depDetails);
                    }
                    LOGGER.info("-------updatedDepartmentFromDB size in ADVSEARCH-------"+updatedDepartmentFromDB.size());
                    resourceForm.setSelectedDepartmentFromDB(updatedDepartmentFromDB);
                    //Clearing the Search result operation from Form
                    List closeSearchedDeptdetailsFromADSE = new ArrayList();
                    resourceForm.setSearchedDeptdetailsFromADSE(closeSearchedDeptdetailsFromADSE);
                    
                  //Clearing the first time Search result operation from Form
                    List closeFirstSearchedDeptdetailsFromADSE = new ArrayList();
                    resourceForm.setFirstTimesearchedDeptdetailsFromADSE(closeFirstSearchedDeptdetailsFromADSE);
                    // Clearing if there is result not found is there
                    resourceForm.setDeptSearchResult("");
                }
                //--------------------------574 End -----------------------------------------//
                List<ClassDetails> classDetailsList = new ArrayList<ClassDetails>();
                classDetailsList = workListDisplayDelegate.getClassDetailsByDepNos(departmentDetails);
                populateClassDetailsInAdvanceSearch(classDetailsList, resourceForm);
                //Retaining already selected values
                setAdvanceSearchfieldsFromAjax(request, WorkListDisplayConstants.NO_VALUE);
            }
            if(WorkListDisplayConstants.ADV_SEARCH_OPERATION_SEARCH.equals(advSearchOperation)){
            	resourceForm.setSearchResultsReturned(WorkListDisplayConstants.NO_VALUE);
            	getPetsWithAdvanceSearch(request, callType, resourceForm, workFlowList, supplierIdList, prop, 1, WorkListDisplayConstants.NO_VALUE);
            }
        
        }
      //Completion Date Change Start
        if(null!=request.getParameter("completionDate")&& request.getParameter("completionDate").length()>0){
            LOGGER.info("This is from Completion date change to invoke the web service ************************");
            LOGGER.info("orinNumber is:"+request.getParameter("orinNumber"));
            LOGGER.info("StyleorinNumber is:"+request.getParameter("styleorinNumber"));
            String orinNumberfdu=request.getParameter("orinNumber");
            String styleOrinNumberfdu=request.getParameter("styleorinNumber");
            String completionDatefdu=request.getParameter("completionDate");
            String isCmpDateEarlier = request.getParameter("isCmpDateEarlier");
            String parentCompletionDateNullValue = request.getParameter("parentCompletionDateNullValue");
            String parentCompletionDateVal = request.getParameter("parentCompletionDateVal");
            //invoke web service to update the completion date
            //Code change to send the Orin Number at Style color level
            
            styleOrinNumberfdu = filterStyleOrinNumberfdu(styleOrinNumberfdu);            
            LOGGER.info("parentCompletionDateNullValue::" + parentCompletionDateNullValue + "parentCompletionDateVal::" + parentCompletionDateVal + "orinNumberfdu::" + orinNumberfdu +"::styleOrinNumberfdu::"+styleOrinNumberfdu + "::completionDatefdu::" + completionDatefdu + "::isCmpDateEarlier::" +isCmpDateEarlier);
            
            String completeDateStatusStyle =  updateCompletionDate(styleOrinNumberfdu,completionDatefdu);
            
            //updateTheCurrentPetListWithNewCompletionDate(orinNumberfdu,styleOrinNumberfdu,completionDatefdu);
            if("Completion date update is successful".equalsIgnoreCase(completeDateStatusStyle)){
               // updateTheCurrentPetListWithNewCompletionDate(orinNumberfdu,styleOrinNumberfdu,completionDatefdu);
                if("Y".equalsIgnoreCase(isCmpDateEarlier.trim())){
                    updateCompletionDate(orinNumberfdu,completionDatefdu);
                    //updateTheCurrentPetListWithNewCompletionDate(orinNumberfdu,styleOrinNumberfdu,completionDatefdu);
                }else if("Y".equalsIgnoreCase(parentCompletionDateNullValue)){
                    updateCompletionDate(orinNumberfdu,parentCompletionDateVal);
                }
                resourceForm.setUpdateCompletionDateMsg("Completion date update is successful.Please push the Search button to see your changes.");
            }else{
                resourceForm.setUpdateCompletionDateMsg("Completion date not updated successfully");
            }
            
        }
     
        LOGGER.info("  custuser          object in resource reqeust ---------------> "+custuser);
        setUserRoleAndPermission(resourceForm, custuser);
        LOGGER.info("Form before leaving  resource render request method --------------------------> "+resourceForm);
        //Completion Date Change End    
        mv.addObject(WorkListDisplayConstants.WORK_FLOW_FORM, resourceForm);
        }
        return mv;
    }
    

    private void getPetsWithAdvanceSearch(ResourceRequest request, String callType, WorkListDisplayForm resourceForm, List<WorkFlow> workFlowList, List<String> supplierIdList, Properties prop, Integer pageNumber,String searchClicked)  throws Exception{
        int advSearchPageLimit = Integer.parseInt(prop.getProperty(WorkListDisplayConstants.ADVANCE_SEARCH_PAGE_LIMIT));
    	if(WorkListDisplayConstants.NO_VALUE.equalsIgnoreCase(resourceForm.getSearchResultsReturned())){
    		//Retaining already selected values
    		setAdvanceSearchfieldsFromAjax(request, WorkListDisplayConstants.NO_VALUE);
    	}
    	
        int startIndex=(pageNumber-1)*advSearchPageLimit;

        //Getting the PET details on base of the Advance search selections
        mv.addObject(WorkListDisplayConstants.IS_PET_AVAILABLE,WorkListDisplayConstants.YES_VALUE);
        if(null!=resourceForm.getAdvanceSearch() && !resourceForm.getAdvanceSearch().isAllFieldEmpty()){
        	System.out.println("CALL TYPE>>>>>>>>>>>>>>>>>" +callType);
        	// Condition for grouping search
            if (WorkListDisplayConstants.ADV_SEARCH_CALLTYPE_GROUPINGSEARCH
                .equals(callType)) {
            workFlowList = workListDisplayDelegate.getAdvWorklistGroupingData(resourceForm.getAdvanceSearch(),supplierIdList, resourceForm.getVendorEmail(), startIndex, advSearchPageLimit);
            LOGGER.info("Controller List Size: " + workFlowList.size());
            if (workFlowList.size() == 0) {
        		resourceForm.setPetNotFound(WorkListDisplayConstants.NO_GROUP_FOUND_FOR_GROUP_SEARCH);
            }
            resourceForm.setSearchClicked(WorkListDisplayConstants.YES);
            request.getPortletSession().setAttribute("groupWorklistSession", "Regular PET");
            resourceForm.setWorkListType("Regular PET");
            resourceForm.setWorkFlowlist(workFlowList);
            }
            else {
            	// 57 Search
            	if (!"getChildData".equalsIgnoreCase(callType)
            			&& !WorkListDisplayConstants.GET_CHILD_GROUP.equalsIgnoreCase(callType)) {
            		if (null != resourceForm.getVendorEmail()) {
            			LOGGER.info("Line 1849 Vendor in Search Controller:: Calling workListDisplayDelegate.getPetDetailsByAdvSearchForParent");
            			workFlowList = workListDisplayDelegate.getPetDetailsByAdvSearchForParent(resourceForm.getAdvanceSearch(), supplierIdList, resourceForm.getVendorEmail(), startIndex, advSearchPageLimit);
            			resourceForm.setSearchClicked("yes");
            			resourceForm.setSearchResultsReturned(WorkListDisplayConstants.YES_VALUE);
            		}
            		else {
            			LOGGER.info("Line 1854 Vendor in Search Controller:: Calling workListDisplayDelegate.getPetDetailsByAdvSearchForParent");
            			workFlowList = workListDisplayDelegate.getPetDetailsByAdvSearchForParent(
                                    resourceForm.getAdvanceSearch(),
                                    supplierIdList,
                                    resourceForm.getVendorEmail(), startIndex, advSearchPageLimit);
                        resourceForm.setSearchClicked("yes");
            			resourceForm.setSearchResultsReturned(WorkListDisplayConstants.YES_VALUE);
                    }
                }
            }
                    
            if(workFlowList != null && workFlowList.size()>0){
            	//Default sorting. needs to remove if the sorted list is coming from SQL query
                resourceForm.setPetNotFound(null);
                String advSelectedColumn=WorkListDisplayConstants.COMPLETION_DATE;
                //handlingSortingRender(advSelectedColumn,resourceForm,workFlowList); //Commented By AFUSZR6
                //Default Pagination  
                int selectedPageNumber = 1;
                if(null!=request.getParameter(WorkListDisplayConstants.CURRENT_PAGE_NUMBER)){
                	selectedPageNumber = Integer.valueOf(request.getParameter(WorkListDisplayConstants.CURRENT_PAGE_NUMBER)); 
                }
                    
                if((!"getChildData".equalsIgnoreCase(callType)) && (!WorkListDisplayConstants.GET_CHILD_GROUP.equals(callType))){ 
                    resourceForm.setFullWorkFlowlist(workFlowList);
                    resourceForm.setTotalNumberOfPets(String.valueOf(workFlowList.size()));                                        
                }
                //For 496 End           
                
                handlingPaginationRender(pageNumber,resourceForm,workFlowList);//fix for 496
                resourceForm.setSelectedPage(String.valueOf(pageNumber));
            }
            else{//There is no PET for searched content
            	//Fix for Defect 177
                if(setAdvanceSearchfieldsForVendorStyleAjax(request)){
                    if(!"getChildData".equalsIgnoreCase(callType)){
	                    if(null != resourceForm.getVendorEmail()){
	                        LOGGER.info("Line 1885 Vendor in Search Controller:: Calling workListDisplayDelegate.getPetDetailsByAdvSearchForParent");
	                        workFlowList =  workListDisplayDelegate.getPetDetailsByAdvSearchForParent(resourceForm.getAdvanceSearch(),supplierIdList,resourceForm.getVendorEmail(), startIndex, advSearchPageLimit);
	                        resourceForm.setSearchClicked("yes");
	                    }else{
	                        LOGGER.info("Line 1890 DCA  Vendor in Search Controller:: Calling workListDisplayDelegate.getPetDetailsByAdvSearchForParent");
	                        workFlowList =  workListDisplayDelegate.getPetDetailsByAdvSearchForParent(resourceForm.getAdvanceSearch(),supplierIdList,resourceForm.getVendorEmail(), startIndex, advSearchPageLimit);
	                        resourceForm.setSearchClicked("yes");
	                    }
                    }
                    if(workFlowList != null && workFlowList.size()==0){
	                    LOGGER.info("venstyle***** no pet found" +resourceForm.getAdvanceSearch().getVendorStyle());
	                    List<WorkFlow> emptystyleListForWorkListDisplayforVendorStyle = new ArrayList<WorkFlow>();
	                    resourceForm.setPetNotFound(prop.getProperty(WorkListDisplayConstants.NO_PET_FOUND_FOR_VENDOR_STYLE));
	                    LOGGER.info("Vendor--------::" +resourceForm.getPetNotFound());
	                    resourceForm.setTotalNumberOfPets("0");
	                    resourceForm.setWorkFlowlist(emptystyleListForWorkListDisplayforVendorStyle); 
                    }//177 End
                }
                else{
                    LOGGER.info("Line 1439..");
                    List<WorkFlow> emptystyleListForWorkListDisplay = new ArrayList<WorkFlow>();
                    resourceForm.setPetNotFound(prop.getProperty(WorkListDisplayConstants.PET_NOT_FOUND));
                    resourceForm.setTotalNumberOfPets("0");
                    resourceForm.setWorkFlowlist(emptystyleListForWorkListDisplay);
                }
            }
        }
        else{//No Criteria selected
            LOGGER.info("Line 1448..");
            List<WorkFlow> emptystyleListForWorkListDisplay = new ArrayList<WorkFlow>();
            resourceForm.setPetNotFound(prop.getProperty(WorkListDisplayConstants.PET_NOT_FOUND));
            resourceForm.setTotalNumberOfPets("0");
            resourceForm.setWorkFlowlist(emptystyleListForWorkListDisplay); 
        }
    }
    
    /**
     * This method will update the current PET list latest Date.
     * @param completionDatefdu2 
     * @param styleOrinNumberfdu 
     * @param completionDate
     */
  private void updateTheCurrentPetListWithNewCompletionDate(
    String orinNumberfdu, String styleOrinNumberfdu, String completionDatefdu2, WorkListDisplayForm resourceForm) {
      
      LOGGER.info("updateTheCurrentPetListWithNewCompletionDate: Start");
      List<WorkFlow> existingWorkLIst = resourceForm.getWorkFlowlist();
      for(WorkFlow currentFlow:existingWorkLIst){
          if(currentFlow.getOrin().equalsIgnoreCase(orinNumberfdu)){
              //This is the parent of selected Style color
              List<StyleColor> existingStyleList = currentFlow.getStyleColor();
              for(StyleColor currentstylecolor:existingStyleList){
                  if(currentstylecolor.getOrinNumber().equalsIgnoreCase(styleOrinNumberfdu)){
                      //This is the selected Style color
                      LOGGER.info("Match Found...");
                      currentstylecolor.setCompletionDate(completionDatefdu2);
                      break;
                  }
              }
              //Checking any earliest completion date with other style colors 
              String newStyleCompletionDate = "";
             
              try{
              SimpleDateFormat newFormat = new SimpleDateFormat("mm/dd/yyyy");
              newStyleCompletionDate = completionDatefdu2;
              Date newCompletionDate = (Date)newFormat.parse(completionDatefdu2);
             LOGGER.info("------- finalDate"+newStyleCompletionDate);        
             
              for(StyleColor currentstylecolor:existingStyleList){
                  
                  if(null!=currentstylecolor.getCompletionDate() && currentstylecolor.getCompletionDate().length()>0 ){
                        
                      
                          SimpleDateFormat newFormat2 = new SimpleDateFormat("mm/dd/yyyy");
                          Date completionDate = (Date)newFormat2.parse(currentstylecolor.getCompletionDate());
                         LOGGER.info("------- finalDate"+newCompletionDate);        
                     //if any of the Style color is less than new date given then take the earliest date as new completionDate
                     if(completionDate.before(newCompletionDate)){
                         LOGGER.info("------- completionDate"+completionDate);
                         LOGGER.info("newCompletionDate"+newCompletionDate);
                         newStyleCompletionDate =  currentstylecolor.getCompletionDate(); 
                        
                     }
                  }
                 
                  
              }
              } catch (ParseException e) {        
                  e.printStackTrace();
              }
              //setting the earliest date as completion date of Style level
              LOGGER.info("Match newStyleCompletionDate..." +newStyleCompletionDate);
              currentFlow.setCompletionDate("");
              currentFlow.setCompletionDate(newStyleCompletionDate);
             
          }
      }
      resourceForm.setWorkFlowlist(existingWorkLIst);
      LOGGER.info("updateTheCurrentPetListWithNewCompletionDate: End");
}
    
    
  /**
  
   * This method will populate the Advance search object with the parameter values  
   * @param request
   */
private void setAdvanceSearchfieldsFromAjax(ResourceRequest request, String searchAgain) {
   
    LOGGER.info("This is from Advance Search Search operation: Enter");
    String formSessionKey =  (String)request.getPortletSession().getAttribute("formSessionKey");
    WorkListDisplayForm resourceForm =  (WorkListDisplayForm)request.getPortletSession().getAttribute(formSessionKey);

    LOGGER.info(" setAdvanceSearchfieldsForVendorStyleAjax  ---------------------------------------------------------------- "+formSessionKey);
    LOGGER.info(" setAdvanceSearchfieldsForVendorStyleAjax  ---------------------------------------------------------------- "+resourceForm);
    List<WorkFlow> workFlowlist = resourceForm.getWorkFlowlist();
    List<WorkFlow> fullWorkList = resourceForm.getFullWorkFlowlist();
    String completionDateFrom ="";
    String completionDateTo = "";
    String createdToday = "";
    
    String departmentDetails = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_SELECTED_DEPT_PARAM);
    LOGGER.info("departmentDetails:"+departmentDetails);
    completionDateFrom = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_COMPLETION_DATE_FROM_PARAM);
    LOGGER.info("completionDateFrom:"+completionDateFrom);
    completionDateTo = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_COMPLETION_DATE_TO_PARAM);
    LOGGER.info("completionDateTo:"+completionDateTo);
    String imageStatus = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_IMAGE_STATUS_PARAM);
    LOGGER.info("imageStatus:"+imageStatus);
    String contentStatus = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_CONTENT_STATUS_PARAM);
    LOGGER.info("contentStatus:"+contentStatus);
    String petStatus = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_PET_STATUS_PARAM);
    LOGGER.info("petStatus:"+petStatus);
    String requestType = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_REQUEST_TYPE_PARAM);
    LOGGER.info("requestType:"+requestType);
    String orinNumber = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_ORIN_PARAM);
    LOGGER.info("orinNumber:"+orinNumber);
    String vendorStyle = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_VENDOR_STYLE_PARAM);
    LOGGER.info("vendorStyle:"+vendorStyle);
    String upc = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_UPC_PARAM);
    LOGGER.info("upc:"+upc);
    String classNumber = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_CLASS_NUMBER_PARAM);
    LOGGER.info("classNumber:"+classNumber);
    createdToday = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_CREATED_TODAY_PARAM);
    LOGGER.info("createdToday::"+createdToday);
    String finalTodayDate = request.getParameter("finalTodayDate");
    
    String vendorNumber = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_VENDOR_NUMBER_PARAM);
    LOGGER.info("vendor Number::"+vendorNumber);

    // Populate Grouping ID, Grouping Name and Search result
    String searchResult = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_SEARCH_RESULT);
    LOGGER.info("Search Result::"+searchResult);
    String groupingID = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_GROUPING_ID);
    LOGGER.info("Grouping ID::"+groupingID);
    String groupingName = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_GROUPING_NAME);
    LOGGER.info("Grouping name::"+groupingName);
    
    //Fix for Defect #186 Start
    if("yes".equalsIgnoreCase(createdToday)){
        completionDateFrom = "";
        completionDateTo =  "";
    }else{
        LOGGER.info("yes not Selected");
    }
    
    AdvanceSearch adSearch = resourceForm.getAdvanceSearch();
    if(adSearch == null){
        adSearch = new AdvanceSearch();
        adSearch.setAllFieldEmpty(false);
    }
    
    if(WorkListDisplayConstants.NO_VALUE.equalsIgnoreCase(searchAgain)) {
	    /*
	     * Changes for Grouping ID an Grouping Name
	     * Conditions:- 1) If Search result is "Include Groupings" and EITHER OF grouping id or name is populated, ignore all other search criteria
	     *              2) If Search Result is "Include Groupings" and NEITHER OF grouping id and name are populated, populate all other search criteria
	     *              3) If Search result is "Show Only Items", DO NOT POPULATE grouping id and name in object. Populate all other search criteria 
	     */
	    /** Changes Start **/
	    if(WorkListDisplayConstants.ADV_SEARCH_INCLUDE_GROUPINGS.equals(searchResult)){     // Condition to check if Include Groupings is selected in search result radio
	        
	        //Check if parameters are not blank and populate those criterias
	        if(StringUtils.isNotBlank(departmentDetails)||
	                StringUtils.isNotBlank(completionDateFrom)||
	                StringUtils.isNotBlank(completionDateTo)||
	                StringUtils.isNotBlank(imageStatus)||
	                StringUtils.isNotBlank(contentStatus)||
	                StringUtils.isNotBlank(petStatus)||
	                StringUtils.isNotBlank(requestType)||
	                StringUtils.isNotBlank(orinNumber)||
	                StringUtils.isNotBlank(vendorStyle)||
	                StringUtils.isNotBlank(upc)||
	                StringUtils.isNotBlank(classNumber)||
	                StringUtils.isNotBlank(createdToday)||
	                StringUtils.isNotBlank(vendorNumber) ||
	                StringUtils.isNotBlank(groupingID) ||
	                StringUtils.isNotBlank(groupingName))
	               {
	            adSearch.setAllFieldEmpty(false);
	            if(StringUtils.isNotBlank(departmentDetails)||
	                    StringUtils.isNotBlank(completionDateFrom)||
	                    StringUtils.isNotBlank(completionDateTo)||
	                    StringUtils.isNotBlank(imageStatus)||
	                    StringUtils.isNotBlank(contentStatus)||
	                    StringUtils.isNotBlank(petStatus)||
	                    StringUtils.isNotBlank(requestType)||
	                    StringUtils.isNotBlank(orinNumber)||
	                    StringUtils.isNotBlank(vendorStyle)||
	                    StringUtils.isNotBlank(upc)||
	                    StringUtils.isNotBlank(classNumber)||
	                    StringUtils.isNotBlank(createdToday)||
	                    StringUtils.isNotBlank(vendorNumber))
	            {
	                adSearch = new AdvanceSearch();
	                adSearch.setDeptNumbers(departmentDetails);
	                adSearch.setDateFrom(completionDateFrom);
	                adSearch.setDateTo(completionDateTo);
	                adSearch.setImageStatus(imageStatus);
	                adSearch.setContentStatus(contentStatus);
	                adSearch.setActive(petStatus);
	                adSearch.setInActive(petStatus);
	                adSearch.setClosed(petStatus);
	                adSearch.setRequestType(requestType);
	                adSearch.setOrin(orinNumber);
	                adSearch.setVendorStyle(vendorStyle);
	                adSearch.setUpc(upc);
	                adSearch.setClassNumber(classNumber);
	                adSearch.setCreatedToday(createdToday);
	                adSearch.setVendorNumber(vendorNumber);
	            }
	            else if(StringUtils.isNotBlank(groupingID) ||
	                    StringUtils.isNotBlank(groupingName))
	            {
	                if(StringUtils.isNotBlank(groupingName))
	                {
	                    adSearch = new AdvanceSearch();
	                    adSearch.setGroupingName(groupingName);
	                    if(StringUtils.isNotBlank(groupingID))
	                    {
	                        adSearch.setGroupingID(groupingID);
	                    }
	                }
	                else
	                {
	                    adSearch = new AdvanceSearch();
	                    adSearch.setGroupingID(groupingID);
	                }
	            }
	        }else{
	            LOGGER.info("All Advance Search Fields are Empty.");
	            adSearch.setAllFieldEmpty(true); 
	        }
	        
	    }else{
	        // "Show Only Items" radio is selected in Search Result. IGNORE GROUPING ID AND NAME 
	        if(StringUtils.isNotBlank(departmentDetails)||
	                StringUtils.isNotBlank(completionDateFrom)||
	                StringUtils.isNotBlank(completionDateTo)||
	                StringUtils.isNotBlank(imageStatus)||
	                StringUtils.isNotBlank(contentStatus)||
	                StringUtils.isNotBlank(petStatus)||
	                StringUtils.isNotBlank(requestType)||
	                StringUtils.isNotBlank(orinNumber)||
	                StringUtils.isNotBlank(vendorStyle)||
	                StringUtils.isNotBlank(upc)||
	                StringUtils.isNotBlank(classNumber)||
	                StringUtils.isNotBlank(createdToday)||
	                StringUtils.isNotBlank(vendorNumber))
	               {
	            adSearch.setAllFieldEmpty(false);
	        }else{
	            LOGGER.info("All Advance Search Fields are Empty.");
	            adSearch.setAllFieldEmpty(true); 
	        }
	        adSearch.setDeptNumbers(departmentDetails);
	        adSearch.setDateFrom(completionDateFrom);
	        adSearch.setDateTo(completionDateTo);
	        adSearch.setImageStatus(imageStatus);
	        adSearch.setContentStatus(contentStatus);
	        adSearch.setActive(petStatus);
	        adSearch.setInActive(petStatus);
	        adSearch.setClosed(petStatus);
	        adSearch.setRequestType(requestType);
	        adSearch.setOrin(orinNumber);
	        adSearch.setVendorStyle(vendorStyle);
	        adSearch.setUpc(upc);
	        adSearch.setClassNumber(classNumber);
	        adSearch.setCreatedToday(createdToday);
	        adSearch.setVendorNumber(vendorNumber);
	        adSearch.setGroupingID("");
	        adSearch.setGroupingName("");
	    }
	    adSearch.setSearchResults(searchResult);    // Populating Search Result radio value in Advacned Search object
	}
    /** Changes End **/
    //Fix for 836 start     
     List<ImageStatusDropValues> imageStatusDropValues = adSearch.getImageStatusDropDown();
        String[] imageStatusChecked = imageStatus.split(",");
        if(imageStatusDropValues!=null){
            for (int i = 0; i < imageStatusDropValues.size(); i++) {
                imageStatusDropValues.get(i).setChecked(WorkListDisplayConstants.ADV_SEARCH_NO_VALUE);
                if (null != imageStatus && StringUtils.isNotBlank(imageStatus)) {
                    for (int j = 0; j < imageStatusChecked.length; j++) {
                        if (imageStatusChecked[j].equals(imageStatusDropValues.get(i).getValue())) {
                            imageStatusDropValues.get(i).setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
                        }
                        if (imageStatusChecked[j].equals("Ready For Review") && imageStatusDropValues.get(i).getValue().equals("Ready_For_Review")) {
                            imageStatusDropValues.get(i).setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
                        }
                    }
                }
            }
        }
        
        adSearch.setImageStatusDropDown(imageStatusDropValues);
    
        List<ContentStatusDropValues> contentStatusDropValues = adSearch.getContentStatusDropDown();
        String[] contentStatusChecked = contentStatus.split(",");
        if(contentStatusDropValues!=null){
            for (int k = 0; k < contentStatusDropValues.size(); k++) {
                contentStatusDropValues.get(k).setChecked(WorkListDisplayConstants.ADV_SEARCH_NO_VALUE);
                if (null != contentStatus && StringUtils.isNotBlank(contentStatus)) {
                    for (int m = 0; m < contentStatusChecked.length; m++) {
                        if (contentStatusChecked[m].equals(contentStatusDropValues.get(k).getValue())) {
                            contentStatusDropValues.get(k).setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
                        }
                        if (contentStatusChecked[m].equals("Ready For Review") && contentStatusDropValues.get(k).getValue().equals("Ready_For_Review")) {
                            contentStatusDropValues.get(k).setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
                        }
                    }
                }
            }
        }
        
        adSearch.setContentStatusDropDown(contentStatusDropValues);
        
        List<RequestTypeDropValues> requestTypeDropValues = adSearch.getRequestTypeDropDown();
        String[] requestTypeChecked = requestType.split(",");
        if(requestTypeDropValues!=null){
            for (int q = 0; q < requestTypeDropValues.size(); q++) {
                requestTypeDropValues.get(q).setChecked(WorkListDisplayConstants.ADV_SEARCH_NO_VALUE);
                if (null != requestType && StringUtils.isNotBlank(requestType)) {
                    for (int r = 0; r < requestTypeChecked.length; r++) {
                        if (requestTypeChecked[r].equals(requestTypeDropValues.get(q).getValue())) {
                            requestTypeDropValues.get(q).setChecked(WorkListDisplayConstants.ADV_SEARCH_YES_VALUE);
                        }                    
                    }
                }
            }
        }
        
        adSearch.setRequestTypeDropDown(requestTypeDropValues);
        //Fix for 836 End
    
    resourceForm.setAdvanceSearch(adSearch);
   
    workFlowlist = resourceForm.getWorkFlowlist();
    fullWorkList = resourceForm.getFullWorkFlowlist();
    LOGGER.info("This is from Advance Search Search operation: Exit");
}

//Fix for Defect 177 Start
private boolean setAdvanceSearchfieldsForVendorStyleAjax(ResourceRequest request) {
    
    String formSessionKey =  (String)request.getPortletSession().getAttribute("formSessionKey");
    WorkListDisplayForm resourceForm =  (WorkListDisplayForm)request.getPortletSession().getAttribute(formSessionKey);
   
    LOGGER.info(" setAdvanceSearchfieldsForVendorStyleAjax  ---------------------------------------------------------------- "+formSessionKey);
    LOGGER.info(" setAdvanceSearchfieldsForVendorStyleAjax  ---------------------------------------------------------------- "+resourceForm);
    
    LOGGER.info("This is from Advance Search Search operation venStyle: Enter");
    boolean venStyle = false ;
    String departmentDetails = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_SELECTED_DEPT_PARAM);
    LOGGER.info("departmentDetails:"+departmentDetails);
    String completionDateFrom = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_COMPLETION_DATE_FROM_PARAM);
    LOGGER.info("completionDateFrom:"+completionDateFrom);
    String completionDateTo = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_COMPLETION_DATE_TO_PARAM);
    LOGGER.info("completionDateTo:"+completionDateTo);
    String imageStatus = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_IMAGE_STATUS_PARAM);
    LOGGER.info("imageStatus:"+imageStatus);
    String contentStatus = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_CONTENT_STATUS_PARAM);
    LOGGER.info("contentStatus:"+contentStatus);
    String petStatus = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_PET_STATUS_PARAM);
    LOGGER.info("petStatus:"+petStatus);
    String requestType = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_REQUEST_TYPE_PARAM);
    LOGGER.info("requestType:"+requestType);
    String orinNumber = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_ORIN_PARAM);
    LOGGER.info("orinNumber:"+orinNumber);
    String vendorStyle = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_VENDOR_STYLE_PARAM);
    LOGGER.info("vendorStyle:"+vendorStyle);
    String upc = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_UPC_PARAM);
    LOGGER.info("upc:"+upc);
    String classNumber = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_CLASS_NUMBER_PARAM);
    LOGGER.info("classNumber:"+classNumber);
    String createdToday = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_CREATED_TODAY_PARAM);
    LOGGER.info("createdToday:"+createdToday);
    String vendorNumber = request.getParameter(WorkListDisplayConstants.ADV_SEARCH_VENDOR_NUMBER_PARAM);
    LOGGER.info("vendor Number::"+vendorNumber);
    
    AdvanceSearch adSearch = resourceForm.getAdvanceSearch();
    if(adSearch == null){
        adSearch = new AdvanceSearch();
        adSearch.setAllFieldEmpty(false);
    }
    if(StringUtils.isBlank(departmentDetails)&&
            StringUtils.isBlank(completionDateFrom)&&
            StringUtils.isBlank(completionDateTo)&&
            StringUtils.isBlank(imageStatus)&&
            StringUtils.isBlank(contentStatus)&&
            StringUtils.isBlank(petStatus)&&
            StringUtils.isBlank(requestType)&&
            StringUtils.isBlank(orinNumber)&&
            StringUtils.isNotBlank(vendorStyle)&&
            StringUtils.isBlank(upc)&&
            StringUtils.isBlank(classNumber)&&
            StringUtils.isBlank(createdToday)&&
            StringUtils.isBlank(vendorNumber))
           {
        adSearch.setAllFieldEmpty(false);
        venStyle = true ;
    }else{
        LOGGER.info("All Advance Search Fields are Empty.");
        venStyle = false;
        adSearch.setAllFieldEmpty(true); 
        
    }
    adSearch.setDeptNumbers(departmentDetails);
    adSearch.setDateFrom(completionDateFrom);
    adSearch.setDateTo(completionDateTo);
    adSearch.setImageStatus(imageStatus);
    adSearch.setContentStatus(contentStatus);
    adSearch.setActive(petStatus);
    adSearch.setInActive(petStatus);
    adSearch.setClosed(petStatus);
    adSearch.setRequestType(requestType);
    adSearch.setOrin(orinNumber);
    adSearch.setVendorStyle(vendorStyle);
    adSearch.setUpc(upc);
    adSearch.setClassNumber(classNumber);
    adSearch.setCreatedToday(createdToday);
    adSearch.setVendorNumber(vendorNumber);
    
    LOGGER.info("ImageStatus from request venstyle::" + adSearch.getImageStatus());
    LOGGER.info("VendorStyle from request venstyle::" + adSearch.getVendorStyle());

    resourceForm.setAdvanceSearch(adSearch);
    LOGGER.info("This is from Advance Search Search operation venstyle: Exit1111::" + venStyle);
    return venStyle;
}
//Fix for Defect 177 End

/**
 * This method will set the class details in to the Advance search
 * @param classDetailsList
 */
private void populateClassDetailsInAdvanceSearch(
    List<ClassDetails> classDetailsList, WorkListDisplayForm resourceForm) {
    LOGGER.info("This is from ApopulateClassDetailsInAdvanceSearch: Enter" ); 
    AdvanceSearch adSearch = resourceForm.getAdvanceSearch();
    if(adSearch == null){
        adSearch = new AdvanceSearch();
    }
    if(null!=classDetailsList && classDetailsList.size()>0){
        LOGGER.info("classDetailsList size="+classDetailsList.size() );
        adSearch.setClassDetails(classDetailsList);
    }
    resourceForm.setAdvanceSearch(adSearch);
    LOGGER.info("This is from ApopulateClassDetailsInAdvanceSearch: Exit" );
}

//New Requirement Inactivate/Activate Integrate

/**
 * This method is responsible for calling Inactivate Pets onselection 
 * @param request
 */
private void inActivatePets(ResourceRequest request, String param) {
    LOGGER.info("Entering:: inActivatePets method controller");
    
    String []orinNumbersArray = null;      
    String orinNo = ""; 
    String petStatus ="";
    String orinNumbers = param;
    String responseMsg = "";
    String petStatusCode ="";
    JSONArray jsonArray = new JSONArray();
    
    AdvanceSearch advSearch = new AdvanceSearch();
    
    if(null != orinNumbers){
        orinNumbersArray = orinNumbers.split(",");
    }
    
    if(null != orinNumbersArray){
        LOGGER.info("orinNumbersArray length inactivate::" + orinNumbersArray.length);
        for(String orinAndStatus : orinNumbersArray){
            orinAndStatus = orinAndStatus.replaceAll("\\s+", "");
            orinNo = orinAndStatus.substring(0, orinAndStatus.indexOf("_"));
            orinNo = orinNo.replaceAll("\\s+", "");//Removed WhiteSpace from StyleColor Orin no. if any for service call
            
            if(orinNo.length() > 12){                
                orinNo = orinNo.substring(0, 12);
                LOGGER.info("Length gt 12-->" + orinNo);
                
            }
            
            petStatus = orinAndStatus.substring(orinAndStatus.indexOf("_")+1);
            LOGGER.info("orinNo::"+ orinNo +"petStatus::"+ petStatus);
            try{
                String petStatusCodeToService = petStatusCodePassToService(petStatus,petStatusCode);
                LOGGER.info("petStatusCodeToService::" + petStatusCodeToService);
                JSONObject jsonStyle = populateActivateInactiveJson(orinNo.trim(),petStatusCodeToService);
                jsonArray.put(jsonStyle);
                LOGGER.info("json Object inActivatePets petId::.. "+ jsonStyle.getString("petId"));
                
            }catch (Exception e) {                    
                LOGGER.info("inside catch for inActivatePets()...controller");
                e.printStackTrace();
                
            }
        }//End of for loop
            try {
                responseMsg = callInActivateAndActivatePetService(jsonArray);
                LOGGER.info("responseMsg_code Controller inActivate::-->" +responseMsg);
            }catch(PEPFetchException eService){
               LOGGER.info("Exception Block in Controller::32");
               eService.printStackTrace();                    
            }catch (Exception e) {
               LOGGER.info("Exception Block in Controller::21");
               e.printStackTrace();
            }
    }
    LOGGER.info("Exiting:: inActivatePets method controller");
} //End Inactivate





/**
 *  This method is responsible for calling Activate Pets onselection
 * @param request
 */
private void activatePets(ResourceRequest request, String param) {
    LOGGER.info("Entering:: activatePets method controller");
    
    String []orinNumbersArray = null;       
    String orinNumbers = param;
    String orinNo ="";
    String petStatus ="";
    String responseMsg ="";
    String petStatusCode ="";
    
    String updatedBy = "";
    
    String sessionDataKey =  (String)request.getPortletSession().getAttribute("sessionDataKey");//TODO
    UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);//TODO
    LOGGER.info("userData----in activate -------------------------------------------------------------------->"+custuser);
    if(custuser !=null){
        LOGGER.info("userData----in activate");
        if(custuser.isInternal()){                     
            updatedBy = custuser.getBelkUser().getLanId();
            LOGGER.info("Activate Internal user---:"+updatedBy);
        }
    }
    
    JSONArray jsonArray = new JSONArray();
    
    if(null != orinNumbers){
        orinNumbersArray = orinNumbers.split(",");
    }      
    
    if(null != orinNumbersArray){
        for(String orinAndStatus : orinNumbersArray){
            orinAndStatus = orinAndStatus.replaceAll("\\s+", "");
            orinNo = orinAndStatus.substring(0, orinAndStatus.indexOf("_"));
            orinNo = orinNo.replaceAll("\\s+", "");//Removed WhiteSpace from StyleColor Orin no. if any for service call
            
            
            LOGGER.info("orinNo***Reinitiate::"+ orinNo);
            
            if(orinNo.length() > 12){                
                orinNo = orinNo.substring(0, 12);
                LOGGER.info("Length gt 12 Reinitiate-->" + orinNo);                
            }
            
            try{
                JSONObject jsonStyle = populateReInitiateJson(orinNo.trim(),updatedBy);
                jsonArray.put(jsonStyle);
                
                LOGGER.info("json Object activate123 petId::.. "+ jsonStyle.getString("petId"));
            }catch (Exception e) {                   
                LOGGER.info("inside catch for activatePets()...controller");
                e.printStackTrace();
            }
        }//End of forLoop
        //Service call
        try {
            responseMsg = callReInitiatePetService(jsonArray);
            LOGGER.info("responseMsg_code Controller Activate::" +responseMsg);
        }catch(PEPFetchException eService){
           LOGGER.info("Exception Block in activate Controller::11");
           eService.printStackTrace();                    
        }catch (Exception e) {
           LOGGER.info("Exception Block in activate Controller::12");
           e.printStackTrace();
        }
    }
    LOGGER.info("Exiting:: activatePets method controller");
}//End Activate


/**
 * Method to pass JSON Array to call the Inactivate/Activate service
 * @param petId
 * @param petStatus
 * @return
 */
public JSONObject populateActivateInactiveJson(String petId,String petStatus) {
    JSONObject jsonObj = new JSONObject();
    try {          
        jsonObj.put(WorkListDisplayConstants.PET_ID,petId);
        jsonObj.put(WorkListDisplayConstants.PET_STATUS,petStatus);
    } catch (JSONException e) {
        LOGGER.info("Exeception in parsing the jsonObj");
        e.printStackTrace();
    }
    return jsonObj;
 }


public JSONObject populateReInitiateJson(String petId,String updatedBy) {
    LOGGER.info("populateReInitiateJson----->Controller");
    JSONObject jsonObj = new JSONObject();
    try {          
        
        jsonObj.put(WorkListDisplayConstants.PET_ID,petId);
        jsonObj.put("UpdatedBy",updatedBy);
        
    } catch (JSONException e) {
        LOGGER.info("Exeception in parsing the jsonObj");
        e.printStackTrace();
    }
    return jsonObj;
 }

/**
 * This method is used to replace petStatus with the service code to pass on the service call
 * @param petStatus
 * @param petStatusCode
 * @return
 */
	private String petStatusCodePassToService(String petStatus, String petStatusCode) {
		LOGGER.info("Entering petStatusCodeToService.. Controller::");
		if ("Initiated".equalsIgnoreCase(petStatus)) {
			petStatusCode = "01";
		} else if ("Completed".equalsIgnoreCase(petStatus) || WorkListDisplayConstants.IN_PROGRESS.equalsIgnoreCase(petStatus)) { //  for Style Level Pet Status changed
			petStatusCode = "02";
		} else if ("Approved".equalsIgnoreCase(petStatus)) {
			petStatusCode = "03";
		} else if ("Rejected".equalsIgnoreCase(petStatus)) {
			petStatusCode = "04";
		} else if ("Deactivated".equalsIgnoreCase(petStatus)) {
			petStatusCode = "05";
		} else if ("Closed".equalsIgnoreCase(petStatus) ) {
			petStatusCode = "06";
		} else if ("Waiting_To_Be_Closed".equalsIgnoreCase(petStatus)) {
			petStatusCode = "07";
		} else if ("Ready_For_Review".equalsIgnoreCase(petStatus)) {
			petStatusCode = "08";
		} else if ("Publish_To_Web".equalsIgnoreCase(petStatus)) {
			petStatusCode = "09";
		} else if ("Pre Cut Initiated".equalsIgnoreCase(petStatus)) {
			petStatusCode = "10";
		} else if (null == petStatus || StringUtils.isBlank(petStatus) || "undefined" == petStatus) {
			LOGGER.info("petStatus is not valid in db");
			petStatusCode = "";
		}
		// Passing inactivate pets to make activate and vice-e-versa
		if (petStatusCode == "01" || petStatusCode == "02" || petStatusCode == "03" || petStatusCode == "06"
				|| petStatusCode=="07" || petStatusCode=="08" || petStatusCode== "09" || petStatusCode == "10"){
			petStatusCode = "05";
		} else if (petStatusCode == "05") {
			petStatusCode = "01";
		}
		LOGGER.info("petStatusCode::" + petStatusCode);
		LOGGER.info("Exiting petStatusCodeToService.. Controller::");
		return petStatusCode;
	}


/**
 * @param OrinNum
 * @param compDate
 * @return
 */
private String updateCompletionDate(String OrinNum,String compDate){
    JSONArray jsonArray = new JSONArray();
    String responseMsg ="";
    JSONObject jsonCompletionDt = populateCompletionDateJson(OrinNum.trim(),compDate);
    jsonArray.put(jsonCompletionDt);
    LOGGER.info("json Object updateCompletionDate petId::.. "+ jsonCompletionDt.getString("petId"));
    try{
        responseMsg = callSaveCompletionDateService(jsonArray);
        LOGGER.info("responseMsg_code Controller Activate::" +responseMsg);
    }catch(Exception ex){
        LOGGER.info("inside catch for service call---......Controller");
        ex.printStackTrace();
    }
    
    return responseMsg;
}
/**
 * @param completionDateInfo
 * @return
 * @throws Exception
 * @throws PEPFetchException
 */
private String callSaveCompletionDateService(JSONArray completionDateInfo) throws Exception,
    PEPFetchException {
    
    String responseMsg = null;
    responseMsg = workListDisplayDelegate.callSaveCompletionDateService(completionDateInfo);
    return responseMsg;

}
/**
 * @param petId
 * @param completionDate
 * @return
 */
public JSONObject populateCompletionDateJson(String petId,String completionDate) {
     JSONObject jsonObj = new JSONObject();
     LOGGER.info("------- in parsing the jsonObj111111111");
     try {          
         jsonObj.put("petId",petId);
         
        String completionDateStr = ConvertDate(completionDate);
        LOGGER.info("------- in parsing the jsonObj111111111"+completionDateStr);
        jsonObj.put("completionDate",completionDateStr);
         
     } catch (JSONException e) {
         LOGGER.info("Exeception in parsing the jsonObj111111111");
         e.printStackTrace();
     }
     return jsonObj;
  }

public String ConvertDate(String completionDate){
    String finalDate = null;
    try {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd"); 
    Date date = (Date)formatter.parse(completionDate);
    SimpleDateFormat newFormat = new SimpleDateFormat("mm/dd/yyyy");
     finalDate = newFormat.format(date);
    LOGGER.info("------- finalDate"+finalDate);        
    
    } catch (ParseException e) {        
        e.printStackTrace();
    }
    return finalDate;
  }


    /**
     * This method will return 
     * @param styleOrinNumberfdu
     * @return
     */
    private String filterStyleOrinNumberfdu(String styleOrinNumberfdu) {
        String styleOrinNumberfduL = "";
        // eg:220709046 020 Grey
        if (null != styleOrinNumberfdu) {
            styleOrinNumberfduL = styleOrinNumberfdu.replaceAll(" ", "");
            if (styleOrinNumberfduL.length() > 12) {
                styleOrinNumberfduL = styleOrinNumberfduL.substring(0, 12);
            }
        }
        LOGGER.info("styleOrinNumberfduL" + styleOrinNumberfduL);
        return styleOrinNumberfduL;
    }


    /**
     * Sets the user data to form.
     *
     * @param workListForm the work list form
     * @param custuser the custuser
     */
    private void setUserDataToForm(WorkListDisplayForm workListForm,
        UserData custuser) {
        String loggedInUser = "";
        if (null != custuser) {
            Common_BelkUser belkUser = (Common_BelkUser) custuser.getBelkUser();
            if (null != belkUser) {
                loggedInUser = belkUser.getLanId(); // Internal
                workListForm.setPepUserID(loggedInUser);
            }
            if (custuser.getVpUser() != null) {
                 String vendorEmail = custuser.getVpUser().getUserEmailAddress(); // External User
                 loggedInUser = custuser.getVpUser().getPepUserID();
                 workListForm.setPepUserID(loggedInUser);
                 workListForm.setVendorEmail(vendorEmail);
                 LOGGER.info("Email User Id "+vendorEmail);
            }
        }
        LOGGER.info("PepUser Id "+loggedInUser);
      
    }

    /**
     * Sets the user role and permission.
     *
     * @param workListForm the work list form
     * @param custuser the custuser
     */
    private void setUserRoleAndPermission(WorkListDisplayForm workListForm, UserData custuser){
        if(custuser != null && custuser.isInternal()){
            mv.addObject(WorkListDisplayConstants.IS_INTERNAL,WorkListDisplayConstants.YES_VALUE);
            workListForm.setRoleName(WorkListDisplayConstants.DCA_ROLE);
        }else{
            mv.addObject(WorkListDisplayConstants.IS_INTERNAL,WorkListDisplayConstants.NO_VALUE);
            workListForm.setRoleName("");
        }
    }
    /**
     *  This method is responsible for get pet page locked or not
     * @param request
     */
    private void isPetLocked(ResourceRequest request,
        ResourceResponse response,String lockedPet,String lockedPettype) {
    
        LOGGER.info("Entering:: aisPetLocked controller");
        /* VP-8 - PEP lock out fix - Ramkumar - start 1/2*/
        String formSessionKey =  (String)request.getPortletSession().getAttribute("formSessionKey");
        WorkListDisplayForm resourceForm =  (WorkListDisplayForm)request.getPortletSession().getAttribute(formSessionKey);
        String sessionDataKey =  (String)request.getPortletSession().getAttribute("sessionDataKey");
        UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);
        String loggedinuser="";
        if(custuser.isInternal()){
            loggedinuser=custuser.getBelkUser().getLanId();
        }else {
            loggedinuser=custuser.getVpUser().getPepUserID();
        }
        /* VP-8 - PEP lock out fix - Ramkumar -end 1/2 */
        ArrayList lockedPetDtls = null;
        try {
            String searchLockedtype="";
            if(lockedPettype.equalsIgnoreCase(WorkListDisplayConstants.SEARCH_LOCKED_TYPE_CONTENT)){
                searchLockedtype= WorkListDisplayConstants.SEARCH_LOCKED_TYPE_CONTENT;
                LOGGER.info("Entering:: isPetLocked  ****** searchLockedtype"+searchLockedtype);
                
            }else if(lockedPettype.equalsIgnoreCase(WorkListDisplayConstants.SEARCH_LOCKED_TYPE_IMAGE)){
                searchLockedtype= WorkListDisplayConstants.SEARCH_LOCKED_TYPE_IMAGE;
                LOGGER.info("Entering:: isPetLocked  ****** searchLockedtype"+searchLockedtype);
            }
            lockedPetDtls =  workListDisplayDelegate.isPETLocked(lockedPet, "",searchLockedtype);
            boolean petLocked = false ;
            String petLockedUser = null;;
            if(null != lockedPetDtls && lockedPetDtls.size()>0){
                PetLock petLockedDtl = null;
                for(int i=0;i<lockedPetDtls.size();i++){                  
                    petLockedDtl = (PetLock) lockedPetDtls.get(i); 
                    LOGGER.info("petLockedDtl.getId().getPetId() "+petLockedDtl.getId().getPetId());
                    LOGGER.info("petLockedDtl.getPepFunction()  "+petLockedDtl.getPepFunction());
                    LOGGER.info("Locked Status in controller class "+petLockedDtl.getLockStatus());
                  
                    if(petLockedDtl.getId().getPepUser()!=null ){
                        petLockedUser =petLockedDtl.getId().getPepUser();
                      } 
                    if(petLockedDtl.getLockStatus()!=null && petLockedDtl.getLockStatus().equalsIgnoreCase("Yes")){
                      petLocked = true;
                    }
                    /* VP-8 - PEP lock out fix - Ramkumar - start 2/2*/
                    if(petLockedUser.equalsIgnoreCase(loggedinuser)){
                        petLocked=false;
                    }
                    /* VP-8 - PEP lock out fix - Ramkumar - end 2/2*/
                }
            }
            
            LOGGER.info("petLocked here ******** "+petLocked);
            JSONObject jsonObj = null;
            JSONArray jsonArrayPetDtls = new JSONArray();
            jsonObj = new JSONObject();
            jsonObj.put("LockStatus",petLocked );
            if(petLockedUser!=null){
            jsonObj.put("petLockedUser", petLockedUser);  
            }else{
                jsonObj.put("petLockedUser", "NoUser");   
            }
            jsonArrayPetDtls.put(jsonObj);
          
            LOGGER.info("Locked Status end  "+jsonArrayPetDtls.toString());
            response.getWriter().write(jsonArrayPetDtls.toString());
        }
        
        catch (PEPPersistencyException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
       
    }
    
    
    public void getChildData(ResourceRequest request,
        ResourceResponse response) {
        
        LOGGER.info("**************Entering into**** getChildData() method");
        String orinNum = request.getParameter("orinNum");
        if(orinNum == null || orinNum.trim().equals(""))
        {
            orinNum = request.getParameter(WorkListDisplayConstants.GRPUING_ID);
        }
        
        String advSearchClick = request.getParameter(WorkListDisplayConstants.SEARCH_CLICKED);
        List<StyleColor> childPETList = null;
        JSONArray jsonArrayPetDtls = new JSONArray();
        JSONArray jsonArrayColorList = new JSONArray();
        JSONObject jsonObj = null;
        List<WorkFlow> groupChildList = new ArrayList<WorkFlow>();
        
        String email = null;
        String formSessionKey =  (String)request.getPortletSession().getAttribute("formSessionKey");
        WorkListDisplayForm resourceForm =  (WorkListDisplayForm)request.getPortletSession().getAttribute(formSessionKey);
        String sessionDataKey =  (String)request.getPortletSession().getAttribute("sessionDataKey");//TODO
        UserData custuser = (UserData) request.getPortletSession().getAttribute(sessionDataKey);//TODO
        
        List<WorkFlow> workFlowlist = resourceForm.getWorkFlowlist();
        LOGGER.info("workFlowlist Size-->"+workFlowlist.size());
        List fullWorkList = resourceForm.getFullWorkFlowlist();
        if (null != custuser.getVpUser()){
            email = custuser.getVpUser().getUserEmailAddress();
            LOGGER.info("email:: "+email);
        }
        
        try {
            LOGGER.info("email try:: "+email);
            String parentGroupId = WorkListDisplayConstants.EMPTY_STRING;
        	if(request
                    .getParameter(WorkListDisplayConstants.PARENT_GROUP_ID) != null 
                    && !request
                    .getParameter(WorkListDisplayConstants.PARENT_GROUP_ID).trim()
                    .equals(WorkListDisplayConstants.EMPTY_STRING))
        	{
        		parentGroupId = request.getParameter(WorkListDisplayConstants.PARENT_GROUP_ID);
        	}
            //getPetDetailsByAdvSearchForChild 
            if("yes".equalsIgnoreCase(advSearchClick)){ 
                setAdvanceSearchfieldsFromAjax(request, WorkListDisplayConstants.NO_VALUE);
                AdvanceSearch advanceSearch = resourceForm.getAdvanceSearch();
                LOGGER.info("**************If block Calling workListDisplayDelegate.getPetDetailsByDepNosForChil getChildData() method " + "email:::"+email+"orinNum::"+orinNum);
                if (WorkListDisplayConstants.GET_CHILD_GROUP
                    .equalsIgnoreCase(request
                       .getParameter(WorkListDisplayConstants.CALL_TYPE_PARAMETER))) {                	
                            
                    groupChildList =
                        workListDisplayDelegate.getChildForGroup(orinNum,
                            advanceSearch, parentGroupId);
                }
                else {
                    childPETList =
                        workListDisplayDelegate
                            .getPetDetailsByAdvSearchForChild(advanceSearch,
                                orinNum);
                }
                LOGGER.info("IF Block : orinNum:: "+childPETList);
                resourceForm.setSearchClicked("yes");
                if(null != childPETList){
                    LOGGER.info("IF Block : orinNum:: "+childPETList.size());
                }
            }else{
                LOGGER.info("************** else Calling workListDisplayDelegate.getPetDetailsByDepNosForChil getChildData() method " + "email:::"+email+"orinNum::"+orinNum);
                if (WorkListDisplayConstants.GET_CHILD_GROUP
                    .equalsIgnoreCase(request
                        .getParameter(WorkListDisplayConstants.CALL_TYPE_PARAMETER))) {
                    groupChildList =
                        workListDisplayDelegate
                            .getChildForGroupWorklist(orinNum, parentGroupId);
                }
                else {
                    childPETList =
                        workListDisplayDelegate.getPetDetailsByDepNosForChild(
                            email, orinNum);
                }               
            }
            workFlowlist = resourceForm.getWorkFlowlist();
            fullWorkList = resourceForm.getFullWorkFlowlist();
            LOGGER.info("workFlowlist size:"+workFlowlist.size());
            LOGGER.info("**************After workListDisplayDelegate.getPetDetailsByDepNosForChil this call in  getChildData() method");
            LOGGER.info("getChildData : orinNum:: "+childPETList);
            //Getchild data based on ORIN NUM and generate the JSON Object array
            if(null != childPETList && childPETList.size()> 0){
                for(StyleColor styleColor:childPETList){
                    jsonObj = new JSONObject();
                    jsonObj.put("styleOrinNum",styleColor.getOrinNumber() );
                    jsonObj.put("omniChannelVendor",styleColor.getOmniChannelVendor());
                    jsonObj.put("deptId",styleColor.getDeptId() );
                    jsonObj.put("vendorName",styleColor.getVendorName() );
                    jsonObj.put("vendorStyle",styleColor.getVendorStyle());
                    jsonObj.put("productName",styleColor.getProductName());
                    jsonObj.put("contentStatus",styleColor.getContentStatus());
                    jsonObj.put("imageStatus",styleColor.getImageStatus());
                    jsonObj.put("completionDate",styleColor.getCompletionDate());
                    jsonObj.put("petStatus",styleColor.getPetStatus());
                    
                    jsonObj.put("petSourceType",styleColor.getSourceType());
                    jsonObj.put(WorkListDisplayConstants.ISGROUP,
                        WorkListDisplayConstants.NO_N);
                    jsonObj.put(WorkListDisplayConstants.EXISTSINGROUP,
                    		styleColor.getExistsInGroup());
                    jsonObj.put(WorkListDisplayConstants.ENTRY_TYPE,
                            styleColor.getEntryType());
                    jsonObj.put(WorkListDisplayConstants.MISSING_ASSET_VAR,
                            styleColor.getMissingAsset());
                    
                    jsonArrayPetDtls.put(jsonObj); 
                }                
            }
            else if (groupChildList != null && groupChildList.size() > 0) {
                for (WorkFlow workFlow : groupChildList) {

                    JSONObject jsonObjColor = null;
                    if (!workFlow.getEntryType().equalsIgnoreCase(
                        WorkListDisplayConstants.STYLE)
                        && !workFlow.getEntryType().equalsIgnoreCase(
                            WorkListDisplayConstants.STYLECOLOR)) {
                        jsonObj = new JSONObject();
                        jsonObj.put(WorkListDisplayConstants.STYLEORINNUM,
                            workFlow.getOrinNumber());
                        jsonObj.put(WorkListDisplayConstants.DEPTID,
                            workFlow.getDeptId());
                        jsonObj.put(WorkListDisplayConstants.VENDORNAME,
                            workFlow.getVendorName());
                        jsonObj.put(WorkListDisplayConstants.VENDORSTYLE,
                            workFlow.getVendorStyle());
                        jsonObj.put(WorkListDisplayConstants.PRODUCTNAME,
                            workFlow.getProductName());
                        jsonObj.put(WorkListDisplayConstants.CONTENTSTATUS,
                            workFlow.getContentStatus());
                        jsonObj.put(WorkListDisplayConstants.IMAGESTATUS,
                            workFlow.getImageStatus());
                        jsonObj.put(WorkListDisplayConstants.COMPLETIONDATE,
                            workFlow.getCompletionDate());
                        jsonObj.put(WorkListDisplayConstants.PETSTATUS,
                            workFlow.getPetStatus());
                        jsonObj.put(WorkListDisplayConstants.PETSOURCETYPE,
                            workFlow.getSourceType());
                        jsonObj.put(WorkListDisplayConstants.COLORLIST,
                            WorkListDisplayConstants.EMPTY_STRING);
                        jsonObj.put(WorkListDisplayConstants.CHILDAVAILABLE,
                            workFlow.getIsChildPresent());
                        jsonObj.put(WorkListDisplayConstants.ISGROUP,
                            WorkListDisplayConstants.YES_Y);
                        jsonObj.put(WorkListDisplayConstants.OMNICHANNELVENDOR,
                            workFlow.getOmniChannelVendor());
                        jsonObj.put(WorkListDisplayConstants.EXISTSINGROUP,
                            workFlow.getExistsInGroup());
                        jsonObj.put(WorkListDisplayConstants.ROOTORIN, orinNum);
                        jsonObj.put(WorkListDisplayConstants.ADVSEARCHCLICKED,
                            advSearchClick);
                        jsonObj.put(WorkListDisplayConstants.ENTRY_TYPE,
                        		workFlow.getEntryType());
                        jsonObj.put(WorkListDisplayConstants.MISSING_ASSET_VAR,
                                workFlow.getMissingAsset());
                        jsonArrayPetDtls.put(jsonObj);
                    }
                    else if (workFlow.getEntryType().equalsIgnoreCase(
                        WorkListDisplayConstants.STYLE)) {
                        jsonObj = new JSONObject();
                        jsonObj.put(WorkListDisplayConstants.STYLEORINNUM,
                            workFlow.getOrinNumber());
                        jsonObj.put(WorkListDisplayConstants.DEPTID,
                            workFlow.getDeptId());
                        jsonObj.put(WorkListDisplayConstants.VENDORNAME,
                            workFlow.getVendorName());
                        jsonObj.put(WorkListDisplayConstants.VENDORSTYLE,
                            workFlow.getVendorStyle());
                        jsonObj.put(WorkListDisplayConstants.PRODUCTNAME,
                            workFlow.getProductName());
                        jsonObj.put(WorkListDisplayConstants.CONTENTSTATUS,
                            workFlow.getContentStatus());
                        jsonObj.put(WorkListDisplayConstants.IMAGESTATUS,
                            workFlow.getImageStatus());
                        jsonObj.put(WorkListDisplayConstants.COMPLETIONDATE,
                            workFlow.getCompletionDate());
                        jsonObj.put(WorkListDisplayConstants.PETSTATUS,
                            workFlow.getPetStatus());
                        jsonObj.put(WorkListDisplayConstants.PETSOURCETYPE,
                            workFlow.getSourceType());
                        jsonObj.put(WorkListDisplayConstants.CHILDAVAILABLE,
                            workFlow.getIsChildPresent());
                        jsonObj.put(WorkListDisplayConstants.ISGROUP,
                            WorkListDisplayConstants.NO_N);
                        jsonObj.put(WorkListDisplayConstants.OMNICHANNELVENDOR,
                            workFlow.getOmniChannelVendor());
                        jsonObj.put(WorkListDisplayConstants.EXISTSINGROUP,
                            workFlow.getExistsInGroup());
                        jsonObj.put(WorkListDisplayConstants.ROOTORIN, orinNum);
                        jsonObj.put(WorkListDisplayConstants.ENTRY_TYPE,
                            workFlow.getEntryType());
                        jsonObj.put(WorkListDisplayConstants.MISSING_ASSET_VAR,
                            workFlow.getMissingAsset());
                        List childColor = workFlow.getColorList();
                        WorkFlow workFlowColor = null;
                        jsonArrayColorList = new JSONArray();
                        if (childColor != null && childColor.size() > 0) {
                            for (Iterator iterator = childColor.iterator(); iterator
                                .hasNext();) {
                                workFlowColor = (WorkFlow) iterator.next();
                                jsonObjColor = new JSONObject();
                                jsonObjColor.put(
                                    WorkListDisplayConstants.STYLEORINNUM,
                                    workFlowColor.getOrinNumber());
                                jsonObjColor.put(
                                    WorkListDisplayConstants.DEPTID,
                                    workFlowColor.getDeptId());
                                jsonObjColor.put(
                                    WorkListDisplayConstants.VENDORNAME,
                                    workFlowColor.getVendorName());
                                jsonObjColor.put(
                                    WorkListDisplayConstants.VENDORSTYLE,
                                    workFlowColor.getVendorStyle());
                                jsonObjColor.put(
                                    WorkListDisplayConstants.PRODUCTNAME,
                                    workFlowColor.getProductName());
                                jsonObjColor.put(
                                    WorkListDisplayConstants.CONTENTSTATUS,
                                    workFlowColor.getContentStatus());
                                jsonObjColor.put(
                                    WorkListDisplayConstants.IMAGESTATUS,
                                    workFlowColor.getImageStatus());
                                jsonObjColor.put(
                                    WorkListDisplayConstants.COMPLETIONDATE,
                                    workFlowColor.getCompletionDate());
                                jsonObjColor.put(
                                    WorkListDisplayConstants.PETSTATUS,
                                    workFlowColor.getPetStatus());
                                jsonObjColor.put(
                                    WorkListDisplayConstants.PETSOURCETYPE,
                                    workFlowColor.getSourceType());
                                jsonObjColor.put(
                                    WorkListDisplayConstants.CHILDAVAILABLE,
                                    WorkListDisplayConstants.EMPTY_STRING);
                                jsonObjColor.put(
                                    WorkListDisplayConstants.ISGROUP,
                                    WorkListDisplayConstants.NO_N);
                                jsonObjColor.put(
                                    WorkListDisplayConstants.OMNICHANNELVENDOR,
                                    workFlow.getOmniChannelVendor());
                                jsonObjColor.put(
                                    WorkListDisplayConstants.EXISTSINGROUP,
                                    workFlow.getExistsInGroup());
                                jsonObjColor.put(
                                    WorkListDisplayConstants.ROOTORIN, orinNum);
                                jsonObjColor.put(WorkListDisplayConstants.ENTRY_TYPE,
                                    workFlowColor.getEntryType());
                                jsonObjColor.put(WorkListDisplayConstants.MISSING_ASSET_VAR,
                                    workFlowColor.getMissingAsset());
                                jsonArrayColorList.put(jsonObjColor);
                            }
                        }
                        else {
                            jsonObjColor = new JSONObject();
                            jsonObjColor.put(
                                WorkListDisplayConstants.NO_CHILD_MESSAGE,
                                WorkListDisplayConstants.NO_CHILD_COLOR_FOUND);
                            jsonObjColor.put(WorkListDisplayConstants.ROOTORIN,
                                orinNum);
                            jsonArrayColorList.put(jsonObjColor);
                        }
                        jsonObj.put(WorkListDisplayConstants.COLORLIST,
                            jsonArrayColorList);
                        jsonArrayPetDtls.put(jsonObj);
                    }
                }
            }
            else if (groupChildList == null || groupChildList.size() == 0) {
                jsonObj = new JSONObject();
                jsonObj.put(WorkListDisplayConstants.NO_CHILD_MESSAGE,
                    WorkListDisplayConstants.NO_CHILD_DATA_FOUND);
                jsonObj.put(WorkListDisplayConstants.ROOTORIN, orinNum);
                jsonArrayPetDtls.put(jsonObj);
            }
            LOGGER.info("**************End of**** getChildData() method");
            response.getWriter().write(
                WorkListDisplayConstants.STARTJSON
                    + jsonArrayPetDtls.toString()
                    + WorkListDisplayConstants.ENDJSON);
        }
        
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (PEPPersistencyException e) {
            e.printStackTrace();
        }
        
       
    }

    /**
     * Handling pagination render for Groups.
     * 
     * @param selectedPageNumber
     *            the selected page number
     * @param renderForm
     *            the render form
     * @param workFlowListSri
     *            the work flow list
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void handlingPaginationRenderGroups(int selectedPageNumber,
        WorkListDisplayForm renderForm, List<WorkFlow> workFlowListSri,
        int totalNumberOfPets) throws IOException {
        LOGGER
            .info("WorkListDisplayController:handlingPaginationRenderGroups:Enter");
        if (workFlowListSri != null) {
            // fix for 496 start
            int numberOfPets = totalNumberOfPets;
            workFlowListSri = renderForm.getFullWorkFlowlist();
            renderForm.setTotalNumberOfPets(String.valueOf(numberOfPets));
            // Setting the limit
            Properties prop = PropertiesFileLoader.getExternalLoginProperties();
            renderForm.setPageLimit(prop
                .getProperty(WorkListDisplayConstants.ADVANCE_SEARCH_PAGE_LIMIT));
            // Setting the number of pages
            int numberOfPages = 1;
            double numPage = 1;
            int pageLimit =Integer.parseInt(prop.getProperty(WorkListDisplayConstants.ADVANCE_SEARCH_PAGE_LIMIT));
            if (numberOfPets > pageLimit) {
                double pageLimeiDoub =
                    Double.parseDouble(String.valueOf(pageLimit));
                numPage = numberOfPets / pageLimeiDoub;
                numPage =
                    Math.ceil(Double.parseDouble(String.valueOf(numPage)));
            }
            numberOfPages = (int) numPage;

            // Setting the page list
            ArrayList<String> pageNumberList = new ArrayList();
            for (int i = 0; i < numberOfPages; i++) {
                pageNumberList.add(String.valueOf(i + 1));
            }
            renderForm.setPageNumberList(pageNumberList);
            // Setting the first page Default
            LOGGER.info("Selected Page number is==" + selectedPageNumber);
            int startindex = 0;
            int endIndex = 9;
            startindex = (selectedPageNumber * pageLimit) - pageLimit;
            endIndex = (selectedPageNumber * pageLimit) - 1;
            // Logic for last page
            if (selectedPageNumber == numberOfPages) {
                endIndex = numberOfPets;
            }
            // fix for 496 end
            LOGGER.info("Start index is ==" + startindex + "endIndex is =="
                + endIndex);
            List currentPageworkFlowList = workFlowListSri.subList(startindex, endIndex);
            renderForm.setSelectedPage(String.valueOf(selectedPageNumber));
            renderForm.setTotalPageno(String.valueOf(numberOfPages));
            renderForm.setPreviousCount(String.valueOf(selectedPageNumber - 1));
            renderForm.setNextCount(String.valueOf(selectedPageNumber + 1));
            renderForm.setWorkFlowlist(currentPageworkFlowList);
            renderForm.setFullWorkFlowlist(workFlowListSri);
            if (numberOfPages > 1) {
                renderForm.setDisplayPagination(WorkListDisplayConstants.YES);
                renderForm.setStartIndex(String.valueOf(startindex + 1));
                renderForm.setEndIndex(String.valueOf(endIndex + 1));
                if (selectedPageNumber == numberOfPages) {
                    renderForm.setEndIndex(String.valueOf(endIndex));
                }

            }
            else {
                renderForm.setDisplayPagination(WorkListDisplayConstants.NO);
                renderForm.setStartIndex(String.valueOf(startindex + 1));
                renderForm.setEndIndex(String.valueOf(endIndex));
            }
        }
        
        LOGGER.info("WorkListDisplayController:handlingPaginationRenderGroups:Exit");   
    }
    
    /**
     * This method is responsible for service call to deactivate group
     * @param request
     */
    private void inActivateGroups(ResourceRequest request, String params) {
        LOGGER.info("WorkListDisplayController inActivateGroups : Starts");
        String groupNumbersArray[] = null;
        String groupNo = WorkListDisplayConstants.EMPTY_STRING;
        String groupStatus = WorkListDisplayConstants.EMPTY_STRING;
        String groupType = WorkListDisplayConstants.EMPTY_STRING;
        String groupNumber = params;
        String responseMsg = WorkListDisplayConstants.EMPTY_STRING;
        String groupStatusCode = WorkListDisplayConstants.EMPTY_STRING;
        JSONArray jsonArray = new JSONArray();
        String updatedBy = WorkListDisplayConstants.EMPTY_STRING;

        AdvanceSearch advanceSearch = new AdvanceSearch();

        String sessionDataKey =
            (String) request.getPortletSession().getAttribute(
                WorkListDisplayConstants.SESSION_DATA_KEY);
        UserData userData =
            (UserData) request.getPortletSession().getAttribute(sessionDataKey);

        if (null != userData) {
            if (userData.isInternal()) {
                updatedBy = userData.getBelkUser().getLanId();
                LOGGER.info("Inactivating user id:----- " + updatedBy);
            }
        }

        if (null != groupNumber) {
            groupNumbersArray =
                groupNumber.split(WorkListDisplayConstants.COMMA);
        }

        if (null != groupNumbersArray) {
            LOGGER.info("groupNumbersArray length inactivate: "
                + groupNumbersArray.length);

            for (String grpNosAndStatus : groupNumbersArray) {
                grpNosAndStatus =
                    grpNosAndStatus.replaceAll(
                        WorkListDisplayConstants.WILD_CHAR,
                        WorkListDisplayConstants.EMPTY_STRING);
                String groupStatusType = grpNosAndStatus.substring(grpNosAndStatus.indexOf(WorkListDisplayConstants.UNDERSCORE) + 1);
                
                groupNo =
                    grpNosAndStatus.substring(0, grpNosAndStatus.indexOf(WorkListDisplayConstants.UNDERSCORE));
                groupStatus =
                	groupStatusType.split(WorkListDisplayConstants.HASH_SIGN)[0].toString().trim();
                groupType =
                	groupStatusType.split(WorkListDisplayConstants.HASH_SIGN)[1].toString().trim();
                
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Group Number:--- " + groupNo
                        + "  Group Status:--- " + groupStatus + 
                        "  Group Type:--- " + groupType);
                }

                try {
                    String groupStatusCodeToService =
                        petStatusCodePassToService(groupStatus, groupStatusCode);
                    LOGGER.info("Group Status code to service:-- "
                        + groupStatusCodeToService);
                    JSONObject jsonObject =
                        populateActivateInactiveGroupJson(groupNo, groupType,
                            groupStatusCodeToService, updatedBy);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("JSON Object after population:----- "
                            + jsonObject.toString());
                    }
                    jsonArray.put(jsonObject);
                    LOGGER.info("Josn value of the inactivated group no:-- "
                        + jsonObject
                            .getString(WorkListDisplayConstants.GROUP_ID));

                }
                catch (Exception e) {
                    LOGGER.error("Error in inActivateGroup in Controller.."
                        + e.getMessage());
                }
            }

            try {
                responseMsg = callInactivateGroupService(jsonArray);
                LOGGER.info("response_code callInactivateGroupService:-- "
                    + responseMsg);
            }
            catch (PEPFetchException e) {
                LOGGER
                    .error("Exception Block in controller::" + e.getMessage());
            }
            catch (Exception e) {
                LOGGER.info("Exception Block in Controller:" + e.getMessage());
            }
        }
        LOGGER.info("WorkListDisplayController inActivateGroups : Starts");
    }

    /**
     * This method will be called to activate groups
     * @param request
     */
    private void activateGroups(ResourceRequest request, String param) {
        LOGGER.info("WorkListDisplayController activateGroups : Start");

        String[] groupsNosArray = null;
        String groupNumbers = param;
        String groupNo = WorkListDisplayConstants.EMPTY_STRING;
        String groupStatus = WorkListDisplayConstants.EMPTY_STRING;
        String groupType = WorkListDisplayConstants.EMPTY_STRING;
        String responseMsg = WorkListDisplayConstants.EMPTY_STRING;
        String groupStatusCode = WorkListDisplayConstants.EMPTY_STRING;

        String updatedBy = WorkListDisplayConstants.EMPTY_STRING;

        String sessionDataKey =
            (String) request.getPortletSession().getAttribute(
                WorkListDisplayConstants.SESSION_DATA_KEY);
        UserData custUser =
            (UserData) request.getPortletSession().getAttribute(sessionDataKey);

        LOGGER.info("user data:------- " + custUser);

        if (custUser != null) {
            if (custUser.isInternal()) {
                updatedBy = custUser.getBelkUser().getLanId();
                LOGGER.info("Activate Internal user:------ " + updatedBy);
            }
        }

        JSONArray jsonArray = new JSONArray();

        if (null != groupNumbers) {
            groupsNosArray = groupNumbers.split(WorkListDisplayConstants.COMMA);
        }

        if (null != groupsNosArray) {
            for (String groupNosAndStatus : groupsNosArray) {
                groupNosAndStatus =
                    groupNosAndStatus.replaceAll(
                        WorkListDisplayConstants.WILD_CHAR,
                        WorkListDisplayConstants.EMPTY_STRING);

                String groupStatusType = groupNosAndStatus.substring(groupNosAndStatus.indexOf(WorkListDisplayConstants.UNDERSCORE) + 1);
                
                groupNo =
                	groupNosAndStatus.substring(0, groupNosAndStatus.indexOf(WorkListDisplayConstants.UNDERSCORE));
                groupStatus =
                	groupStatusType.split(WorkListDisplayConstants.HASH_SIGN)[0].toString().trim();
                groupType =
                	groupStatusType.split(WorkListDisplayConstants.HASH_SIGN)[1].toString().trim();

                LOGGER.info("group reinitialise:--- " + groupNo + "\nGroup Type -- " + groupType);

                try {
                    JSONObject jsonstyle =
                        populateReInitiateGroupJson(groupNo.trim(), updatedBy, groupType);
                    jsonArray.put(jsonstyle);
                }
                catch (Exception e) {
                    LOGGER.error("Error in activate group ........ controller"
                        + e.getMessage());
                }
            }

            try {
                responseMsg = callReInitiategroupService(jsonArray);
                LOGGER.info("Response Msg in Controller....... " + responseMsg);
            }
            catch (PEPFetchException e) {
                LOGGER.error("Exception in Activate Controller"
                    + e.getMessage());
            }
            catch (Exception e) {
                LOGGER.error("Exception in Activate Controller"
                    + e.getMessage());
            }
        }

        LOGGER.info("WorkListDisplayController activateGroups : Ends");
    }
    
    /**
     * 
     * @param groupId String
     * @param groupType String
     * @param groupStatus String
     * @param updatedBy String
     * @return JSONObject Stringe
     */
    public JSONObject populateActivateInactiveGroupJson(String groupId,
        String groupType, String groupStatus, String updatedBy) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(WorkListDisplayConstants.GROUP_ID, groupId);
            jsonObject.put(WorkListDisplayConstants.GROUP_TYPE, groupType);
            jsonObject.put(WorkListDisplayConstants.GROUP_OVERALL_STATUS,
                groupStatus);
            jsonObject.put(WorkListDisplayConstants.MODIFIED_BY, updatedBy);
        }
        catch (JSONException e) {
            LOGGER.error("Exception in parsin group object" + e.getMessage());
        }
        return jsonObject;
    }
    
    /**
     * 
     * @param jsonArray
     *            JSONArray
     * @return String
     * @throws Exception
     * @throws PEPFetchException
     */
    private String callInactivateGroupService(JSONArray jsonArray)
        throws Exception, PEPFetchException {
        String responseMsg = null;
        responseMsg =
            workListDisplayDelegate.callInactivateGroupService(jsonArray);
        return responseMsg;
    }

    /**
     * 
     * @param jsonArray
     *            JSONArray
     * @return String
     * @throws Exception
     * @throws PEPFetchException
     */
    private String callReInitiategroupService(JSONArray jsonArray)
        throws Exception, PEPFetchException {
        String responseMsg = null;
        responseMsg =
            workListDisplayDelegate.callReInitiateGroupService(jsonArray);
        return responseMsg;
    }

    /**
     * 
     * @param groupId
     *            String
     * @param updatedBy
     *            String
     * @return JSONObject
     */
    public JSONObject populateReInitiateGroupJson(String groupId,
        String updatedBy, String groupType) {
        LOGGER.info("populateReInitiateGroupJson:: start");
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put(WorkListDisplayConstants.GROUP_ID, groupId);
            jsonObj.put(WorkListDisplayConstants.GROUP_TYPE, groupType);
            jsonObj.put(WorkListDisplayConstants.MODIFIED_BY, updatedBy);
        }
        catch (JSONException e) {
            LOGGER.error("Error in pasring object" + e.getMessage());
        }
        return jsonObj;
    }
    
    /**
     * Method for locking pet
     * @param request
     * @param response
     */
  
    public void invalidateUserSession(ResourceRequest request,
        ResourceResponse response) {
        String loggedInUser = request.getParameter("loggedInUser");
        LOGGER.info("loggedInUser invalidateUserSession ************.." + loggedInUser);
        try {

            if ((String) request.getPortletSession().getAttribute("sessionDataKey") != null) {
                if ((request.getPortletSession().getAttribute("formSessionKey") != null && ((String) request
                    .getPortletSession().getAttribute("formSessionKey")).contains(loggedInUser))) {
                    LOGGER.info("setting session value here " + loggedInUser);                   
                    request.getPortletSession().invalidate();
                    request.getPortletSession().setAttribute("loggedInUser",loggedInUser);
                }

            }

        }
        catch (Exception e) {
            LOGGER.info("invalidateUserSession", e);
        }

    }
    
    /**
     * Method added for Pagination Perf Enhancements
     * 
     * Perform query and return the subset list of PETs based on 
     * given conditions set in the WorklistDisplayForm and selected page.
     * 
     * @param renderForm
     * @param departmentDetailsList
     * @param email
     * @param supplierIdList
     * @param selectedPageNumber
     * @param maxResult
     * @return
     * @throws PEPPersistencyException
     * @throws PEPServiceException
     */
    public List<WorkFlow> getWorkFlowListFromDB(WorkListDisplayForm renderForm, String workListType, 
    		List departmentDetailsList, String email, List supplierIdList, int selectedPageNumber, int maxResults) 
			throws PEPPersistencyException, PEPServiceException {
        
        int startIndex=0;
        if (selectedPageNumber>1) {
        	startIndex = (selectedPageNumber-1)*maxResults;
        }
        
        String sortColumn = WorkListDisplayConstants.EMPTY_STRING;
        String sortOrder = WorkListDisplayConstants.ASCENDING;
        if (renderForm.getSelectedColumn()!=null) {
        	sortColumn = renderForm.getSelectedColumn();
        	if (WorkListDisplayConstants.FALSE_VALUE.equals(renderForm.getSortingAscending())) {
        		sortOrder = WorkListDisplayConstants.DESCENDING;
        	}
        }
        
        if (WorkListDisplayConstants.GROUPINGS.equalsIgnoreCase(workListType)) {
        	return workListDisplayDelegate.getGroupWorkListDetails(
        			departmentDetailsList, startIndex,maxResults,sortColumn,sortOrder);
        }
        else {
            return workListDisplayDelegate.getPetDetailsByDepNosForParent(departmentDetailsList,
            		email,supplierIdList,startIndex, maxResults,sortColumn,sortOrder);
        }
    }
    
    /**
     * Method will be called to populate the advance search result while coming back from pet/image/group details page
     *
     * @param resourceForm
     * @return
     */
    private WorkListDisplayForm getUpdatedAdvanceSearchForm(WorkListDisplayForm resourceForm,
            List<String> supplierIdList, PortletSession portletSession, int selectedPageNumber, String callType) {
        //call type, supplier id list, vendor email, ajax page number as paramer

        List<WorkFlow> workFlowList = null;
        List<WorkFlow> fullWorkList = null;
        try {
            Properties prop = PropertiesFileLoader.getExternalLoginProperties();
            int maxResults=Integer.parseInt(prop.getProperty(WorkListDisplayConstants.ADVANCE_SEARCH_PAGE_LIMIT));
            int startIndex=(selectedPageNumber-1)*maxResults;

            
            if (resourceForm != null) {
                AdvanceSearch advanceSearch = resourceForm.getAdvanceSearch();
                mv.addObject(WorkListDisplayConstants.IS_PET_AVAILABLE, WorkListDisplayConstants.YES_VALUE);
                if (null != resourceForm.getAdvanceSearch() && !resourceForm.getAdvanceSearch().isAllFieldEmpty()) {

                    // Condition for grouping search
                    if (WorkListDisplayConstants.ADV_SEARCH_CALLTYPE_GROUPINGSEARCH.equals(callType)) {
                        workFlowList = workListDisplayDelegate
                                .getAdvWorklistGroupingData(resourceForm.getAdvanceSearch(), supplierIdList,
                                        resourceForm.getVendorEmail(), startIndex, maxResults);
                        LOGGER.info("Controller List Size: " + workFlowList.size());
                        if (workFlowList.size() == 0) {
                            resourceForm.setPetNotFound(WorkListDisplayConstants.NO_GROUP_FOUND_FOR_GROUP_SEARCH);
                        }
                        resourceForm.setSearchClicked(WorkListDisplayConstants.YES);
                        portletSession.setAttribute("groupWorklistSession", "Regular PET");
                        resourceForm.setWorkListType("Regular PET");
                        resourceForm.setWorkFlowlist(workFlowList);
                    } else {
                        if (!"getChildData".equalsIgnoreCase(callType) && !WorkListDisplayConstants.GET_CHILD_GROUP
                                .equalsIgnoreCase(callType)) {
                            if (null != resourceForm.getVendorEmail()) {
                                LOGGER.info(
                                        "Line 1849 Vendor in Search Controller:: Calling workListDisplayDelegate.getPetDetailsByAdvSearchForParent");
                                workFlowList = workListDisplayDelegate
                                        .getPetDetailsByAdvSearchForParent(resourceForm.getAdvanceSearch(),
                                                supplierIdList, resourceForm.getVendorEmail(), startIndex, maxResults);
                                resourceForm.setSearchClicked("yes");
                            } else {
                                LOGGER.info(
                                        "Line 1854 Vendor in Search Controller:: Calling workListDisplayDelegate.getPetDetailsByAdvSearchForParent");
                                workFlowList = workListDisplayDelegate
                                        .getPetDetailsByAdvSearchForParent(resourceForm.getAdvanceSearch(),
                                                supplierIdList, resourceForm.getVendorEmail(), startIndex, maxResults);
                                resourceForm.setSearchClicked("yes");
                            }
                        }
                    }
                    /******************Search completed for  internal user flow************************/
                    if (workFlowList != null && workFlowList.size() > 0) {
                        //Default sorting. needs to remove if the sorted list is coming from SQL query
                        resourceForm.setPetNotFound(null);
                        String advSelectedColumn = WorkListDisplayConstants.COMPLETION_DATE;
                        if (!"getChildData".equalsIgnoreCase(callType)) {
                            if (!WorkListDisplayConstants.GET_CHILD_GROUP.equals(callType)) {
                                resourceForm.setFullWorkFlowlist(workFlowList);
                                resourceForm.setTotalNumberOfPets(String.valueOf(workFlowList.size()));
                            }
                        }
                        //Fix for 835 Start
                        if (null != resourceForm.getFullWorkFlowlist()) {
                            LOGGER.info("1789 : resourceForm:" + resourceForm.getFullWorkFlowlist().size());
                            fullWorkList = resourceForm.getFullWorkFlowlist();
                        }
                    	handlingPaginationRender(selectedPageNumber, resourceForm, fullWorkList);
                        resourceForm.setSelectedPage(String.valueOf(selectedPageNumber));
                    } else {//There is no PET for searched content
                        //Fix for Defect 177

                        if (StringUtils.isBlank(advanceSearch.getDeptNumbers()) &&
                                StringUtils.isBlank(advanceSearch.getDateFrom()) &&
                                StringUtils.isBlank(advanceSearch.getDateTo()) &&
                                StringUtils.isBlank(advanceSearch.getImageStatus()) &&
                                StringUtils.isBlank(advanceSearch.getContentStatus()) &&
                                StringUtils.isBlank(advanceSearch.getActive()) &&
                                StringUtils.isBlank(advanceSearch.getRequestType()) &&
                                StringUtils.isBlank(advanceSearch.getOrin()) &&
                                StringUtils.isNotBlank(advanceSearch.getVendorStyle()) &&
                                StringUtils.isBlank(advanceSearch.getUpc()) &&
                                StringUtils.isBlank(advanceSearch.getClassNumber()) &&
                                StringUtils.isBlank(advanceSearch.getCreatedToday()) &&
                                StringUtils.isBlank(advanceSearch.getVendorNumber())) {

                            if (!"getChildData".equalsIgnoreCase(callType)) {
                                if (null != resourceForm.getVendorEmail()) {
                                    LOGGER.info(
                                            "Line 1885 Vendor in Search Controller:: Calling workListDisplayDelegate.getPetDetailsByAdvSearchForParent");
                                    workFlowList = workListDisplayDelegate
                                            .getPetDetailsByAdvSearchForParent(resourceForm.getAdvanceSearch(),
                                                    supplierIdList, resourceForm.getVendorEmail(), startIndex, maxResults);
                                    resourceForm.setSearchClicked("yes");
                                } else {
                                    LOGGER.info(
                                            "Line 1890 DCA  Vendor in Search Controller:: Calling workListDisplayDelegate.getPetDetailsByAdvSearchForParent");
                                    workFlowList = workListDisplayDelegate
                                            .getPetDetailsByAdvSearchForParent(resourceForm.getAdvanceSearch(),
                                                    supplierIdList, resourceForm.getVendorEmail(), startIndex, maxResults);
                                    resourceForm.setSearchClicked("yes");
                                }
                            }
                            if (workFlowList != null && workFlowList.size() == 0) {

                                LOGGER.info("venstyle***** no pet found" + resourceForm.getAdvanceSearch()
                                        .getVendorStyle());
                                List<WorkFlow> emptystyleListForWorkListDisplayforVendorStyle = new ArrayList<WorkFlow>();
                                resourceForm.setPetNotFound(
                                        prop.getProperty(WorkListDisplayConstants.NO_PET_FOUND_FOR_VENDOR_STYLE));
                                LOGGER.info("Vendor--------::" + resourceForm.getPetNotFound());
                                resourceForm.setTotalNumberOfPets("0");
                                resourceForm.setWorkFlowlist(emptystyleListForWorkListDisplayforVendorStyle);
                            }//177 End
                        } else {
                            LOGGER.info("Line 1439..");
                            List<WorkFlow> emptystyleListForWorkListDisplay = new ArrayList<WorkFlow>();
                            resourceForm.setPetNotFound(prop.getProperty(WorkListDisplayConstants.PET_NOT_FOUND));
                            resourceForm.setTotalNumberOfPets("0");
                            resourceForm.setWorkFlowlist(emptystyleListForWorkListDisplay);
                        }
                    }

                } else {//No Criteria selected
                    LOGGER.info("Line 1448..");
                    List<WorkFlow> emptystyleListForWorkListDisplay = new ArrayList<WorkFlow>();
                    resourceForm.setPetNotFound(prop.getProperty(WorkListDisplayConstants.PET_NOT_FOUND));
                    resourceForm.setTotalNumberOfPets("0");
                    resourceForm.setWorkFlowlist(emptystyleListForWorkListDisplay);
                }

            }
        } catch (Exception ex) {
            LOGGER.error("Error while advance search back", ex);
        }
        return resourceForm;
    }
    
    /**
     *  This method is responsible for calling update status of Missing Asset onselection
     * @param request
     */
    private void changeMissingAsset(String missingAsset, String orin) {
        LOGGER.info("Entering:: changeMissingAsset method controller");
        
        String []orinNumbersArray = null;       
        String orinNumbers = orin;
        String orinNo =orin;
        String petStatus ="";
        String responseMsg ="";
        String petStatusCode ="";        
        
        JSONArray jsonArray = new JSONArray();
        
        if(null != orinNo){
                orinNo = orinNo.replaceAll("\\s+", "");//Removed WhiteSpace from StyleColor Orin no. if any for service call                
                
                LOGGER.info("orinNo***status of missingAsset::"+ orinNo);
                
                if(orinNo.length() > 12){                
                    orinNo = orinNo.substring(0, 12);
                    LOGGER.info("Length gt 12 of missingAsset-->" + orinNo);                
                }
                
                try{
                    JSONObject jsonStyle = populateMissingAssetJson(orinNo.trim(),missingAsset);
                    jsonArray.put(jsonStyle);
                    
                    LOGGER.info("json Object of Missing Asset petId::.. "+ jsonStyle.getString("petId"));
                    LOGGER.info("json Object of Missing Asset petId::.. "+ jsonStyle.getString("petId"));
                }catch (Exception e) {                   
                    LOGGER.info("inside catch for changeMissingAsset()...controller");
                    e.printStackTrace();
                }
            //Service call Start
            try {
                responseMsg = callMissingAssetPetService(jsonArray);
                LOGGER.info("responseMsg_code Controller changeMissingAsset()::" +responseMsg);
            }catch(PEPFetchException eService){
               LOGGER.info("Exception Block in changeMissingAsset() Controller::11");
               eService.printStackTrace();                    
            }catch (Exception e) {
               LOGGER.info("Exception Block in changeMissingAsset() Controller::12");
               e.printStackTrace();
            }
            //Service call End
        }
        LOGGER.info("Exiting:: changeMissingAsset() method controller");
    }
    
    public JSONObject populateMissingAssetJson(String petId, String missingAsset) {
        LOGGER.info("populateMissingAssetJson----->Controller");
        JSONObject jsonObj = new JSONObject();
        try {          
            
            jsonObj.put(WorkListDisplayConstants.PET_ID,petId);
            jsonObj.put(WorkListDisplayConstants.MISSING_ASSET_VAR,missingAsset);
            
        } catch (JSONException e) {
            LOGGER.info("Exeception in parsing the jsonObj");
            e.printStackTrace();
        }
        return jsonObj;
     }
    
    /**
     * Web service to update Missing Asset Pet
     * @param jsonArray
     * @return {@link String}
     * @throws Exception
     * @throws PEPFetchException
     */
    private String callMissingAssetPetService(JSONArray jsonArray) throws Exception,
         PEPFetchException {
         
         String responseMsg = null;
         responseMsg = workListDisplayDelegate.callMissingAssetPetService(jsonArray);
         return responseMsg;

     }

    
}
