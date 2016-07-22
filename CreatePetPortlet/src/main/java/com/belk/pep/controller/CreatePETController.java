package com.belk.pep.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.Controller;
import org.springframework.web.portlet.mvc.EventAwareController;
import org.springframework.web.portlet.mvc.ResourceAwareController;
import com.belk.pep.common.model.Common_BelkUser;
import com.belk.pep.common.userdata.UserData;
import com.belk.pep.constants.CreatePETPortalConstants;
import com.belk.pep.delegate.CreatePETDelegate;
import com.belk.pep.exception.checked.OrinNotFoundException;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.model.WorkFlowForm;
import com.belk.pep.util.PropertyLoader;

public class CreatePETController implements Controller,EventAwareController,ResourceAwareController  {

    String errorMsg = "";

    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger
        .getLogger(CreatePETController.class.getName());

    /** The Create PET delegate. */
    private CreatePETDelegate createPETDelegate;

    // Web service to Create Pet
    private String callPetWebService(JSONArray styleInfo) throws Exception,
        PEPFetchException {
        // Integration of Create PET webservice
        String responseMsg = null;
        System.out.println(" JSON ARRAY ** "+styleInfo.toString());
        responseMsg = createPETDelegate.createPetWebService(styleInfo);
        return responseMsg;

    }

    /**
     * @return the createPETDelegate
     */
    public CreatePETDelegate getCreatePETDelegate() {
        return createPETDelegate;
    }

    @Override
    public void handleActionRequest(ActionRequest request,
        ActionResponse response) throws Exception {
        ArrayList<WorkFlow> petDetails = null;
        String responseMsg = "";
        boolean flag = false;
        
        Properties prop =PropertyLoader.getPropertyLoader(CreatePETPortalConstants.MESS_PROP);
        LOGGER.info("Action  in render method -->"+ request.getParameter(CreatePETPortalConstants.ACTION_PARAM));
       
        String orinNumber = request.getParameter(CreatePETPortalConstants.ORIN_NO);       
        if (request.getParameter(CreatePETPortalConstants.ACTION_PARAM)
            .equalsIgnoreCase(CreatePETPortalConstants.SELECT)) {           
            response.setRenderParameter(CreatePETPortalConstants.ORIN_NO,orinNumber);

            if (parseWithFallback(orinNumber)) {
                try {
                    String msg = "";
                    // DB call to validate orinNumber
                    msg = createPETDelegate.validateOrin(orinNumber);
                    LOGGER.info("CreatepetController:::msg" + msg);
                    if (!CreatePETPortalConstants.SUCCESS.equalsIgnoreCase(msg)) {
                        if (msg.equalsIgnoreCase(CreatePETPortalConstants.ORIN_NOT_FOUND)) {
                            errorMsg = prop.getProperty(CreatePETPortalConstants.ORIN_NOT_FOUND_ERROR_MSG);
                            LOGGER.info("ORIN_NOT_FOUND -->" + errorMsg);
                        } else if (msg.equalsIgnoreCase(CreatePETPortalConstants.PET_EXIST)) {                          
                            errorMsg = prop.getProperty(CreatePETPortalConstants.NOT_MET_PET_CRITERIA);
                        } else if (msg.equalsIgnoreCase(CreatePETPortalConstants.ORIN_NOT_ELIGIBLE)) {                           
                            errorMsg = prop.getProperty(CreatePETPortalConstants.CREATE_PET_NOT_ELIGIBLE);
                        }else if (msg.equalsIgnoreCase(CreatePETPortalConstants.INACTIVE_SUPPLIER_MSG)) {                         
                            errorMsg = prop.getProperty(CreatePETPortalConstants.INACTIVE_SUPPLIER);
                        }
                         else {
                            LOGGER.info(" HERE ***  " + msg);
                            //errorMsg = msg;

                        }
                    } else {
                        errorMsg = "";
                        flag = true;
                        try {
                            petDetails = createPETDelegate.fetchPETDetails(orinNumber);
                            LOGGER.info("CreatePETController::petDetails::handleAction" + petDetails);
                            Iterator workFlowEach = petDetails.iterator();
                            while (workFlowEach.hasNext()) {
                                WorkFlow workflow =(WorkFlow) workFlowEach.next();
                                LOGGER.info("each workflow Value " + workflow);
                                LOGGER.info("inputId each Dept Value "+ workflow.getOrin());
                            }

                        } catch (OrinNotFoundException e) {
                            LOGGER.info("OrinNotFoundException...");                            
                            errorMsg = prop.getProperty(CreatePETPortalConstants.CREATE_PET_NOT_ELIGIBLE);
                            e.printStackTrace();
                        } catch (PEPFetchException e) {
                            LOGGER.info("DBexception");
                            
                            errorMsg = prop.getProperty(CreatePETPortalConstants.INVALID_DB_ERR);
                            e.printStackTrace();
                        } catch (Exception e) {
                            LOGGER.info("DBexception");
                            errorMsg = prop.getProperty(CreatePETPortalConstants.INVALID_DB_ERR);
                            e.printStackTrace();
                        }

                    }

                } catch (Exception e) {

                }

            } else {
                if (orinNumber.isEmpty()) {
                    errorMsg = prop.getProperty(CreatePETPortalConstants.ORIN_NUMBER_NULL);

                } else {
                    errorMsg = prop.getProperty(CreatePETPortalConstants.ORIN_NOT_FOUND_ERROR_MSG);
                    LOGGER.info("Orin Not Found errorMsg---> " + errorMsg);
                }
            }
        }
        

        if (request.getParameter(CreatePETPortalConstants.ACTION_PARAM)
            .equalsIgnoreCase(CreatePETPortalConstants.CREATE_PET)) {
            errorMsg = "";
            responseMsg = "";
            String valueId = "";
            String colorDes = "";
            LOGGER.info("CreatePETController:::handleActionRequest:::createPET");
            String[] selectedParentStyle = request.getParameterValues(CreatePETPortalConstants.SELECTED_ITEMS);
            LOGGER.info("SubmitList  style --->"+ request.getParameterValues(CreatePETPortalConstants.SELECTED_ITEMS));
            String[] selectedStyleColor = request.getParameterValues(CreatePETPortalConstants.SELECTED_STYLES);
            String[] selectedSkus = request.getParameterValues(CreatePETPortalConstants.SELECTED_SKUS);
            String selectedentryType="";
            if(null !=request.getParameter("entryType")){
            selectedentryType = request.getParameter("entryType");            
            }
            String createdBy  = getLoggedInUserName(request);          
            JSONArray jsonArray = new JSONArray();
            // to add the request parameters for webservice         
            LOGGER.info("If selectedStyleColor   ************"+ selectedStyleColor);
            LOGGER.info("If selectedSkus   ************" + selectedSkus); 
            LOGGER.info(" selectedentryType   ************" + selectedentryType); 
            LOGGER.info("selectedParentStyle before    ************" + selectedParentStyle);            
            LOGGER.info("selectedParentStyle after    ************" + selectedParentStyle); 
            boolean selectedOrin= false;
            LOGGER.info("selectedOrin  before  -->" + selectedOrin);
            if(selectedParentStyle!=null && selectedParentStyle.length >0){
            	selectedOrin = true ;            	
            }else  if(selectedStyleColor!=null && selectedStyleColor.length > 0){
            	selectedOrin = true ;
            }else  if(selectedSkus!=null && selectedSkus.length >0){
            	selectedOrin = true ;            	
            }
            
            try {
                                      
                         if (null != selectedParentStyle ) {
                        	 for (String styleId : selectedParentStyle) {                            	 
                            	 String itemType=CreatePETPortalConstants.STYLE;
                            	 if(null !=request.getParameter("entryType") && request.getParameter("entryType").contains("Complex")) {
                            		 itemType= CreatePETPortalConstants.COMPLEX_PACK;
                            	 }
                                 LOGGER.info("Update itemType *************"                  + itemType);
                                 JSONObject jsonStyle = populateJson(styleId.trim(),itemType,
                                         CreatePETPortalConstants.EMPTY,createdBy);
                                 jsonArray.put(jsonStyle);
                                 LOGGER.info("json Object item id"
                                     + jsonStyle.getString("item_id"));
                             }
                           
                         }                  
                         LOGGER.info("else block   ************");
                         // add style colors
                       

                         if (null != selectedStyleColor) {
                             for (String styleColor : selectedStyleColor) {
                                 LOGGER.info("checkbox style" + styleColor);
                                 int index = styleColor.indexOf(",");
                                 valueId = styleColor.substring(0, index);
                                 LOGGER.info("CreatePETController::valueId"
                                     + valueId);
                                 colorDes = styleColor.substring(index + 1);
                                 LOGGER
                                     .info("CreatePETController::colorDes before Tokenizing"
                                         + colorDes);
                                 StringTokenizer stringToken =
                                     new StringTokenizer(colorDes, " ");
                                 colorDes = stringToken.nextToken();
                                 LOGGER.info("CreatePETController::colorDes"
                                     + colorDes);
                                 JSONObject jsonStyle =
                                     populateJson(valueId,
                                         CreatePETPortalConstants.STYLE_COLOR,
                                         colorDes,createdBy);
                                 jsonArray.put(jsonStyle);
                                 LOGGER.info("jsonSku item id"
                                     + jsonStyle.getString("item_id"));
                                 LOGGER.info("CreatePETController::valueId"
                                     + valueId);

                             }
                         }
                         if (null != selectedSkus) {
                             // add style colors
                             for (String styleSku : selectedSkus) {
                                 LOGGER.info("checkbox style" + styleSku);
                                 JSONObject jsonObj =
                                     populateJson(styleSku,
                                         CreatePETPortalConstants.SKU,
                                         CreatePETPortalConstants.EMPTY,createdBy);
                                 jsonArray.put(jsonObj);
                                 LOGGER.info("jsonSku item id"
                                     + jsonObj.getString("item_id"));
                                 LOGGER.info("CreatePETController::valueId"
                                     + styleSku);
                             }
                         }
                     //}

            } catch (Exception e) {
                LOGGER.info("inside catch for style/sku/color ");
                e.printStackTrace();
            }

            try {
            	 LOGGER.info("selectedOrin  after  -->" + selectedOrin);
                if (selectedOrin) {
                   
                    responseMsg = callPetWebService(jsonArray);
                    LOGGER.info("responseMsg -->" + responseMsg);
               } else {
                    errorMsg = prop.getProperty(CreatePETPortalConstants.ATLEAST_ONE_SELECT_ORIN);                    
                    petDetails = 
                        (ArrayList<WorkFlow>) request.getPortletSession().getAttribute(CreatePETPortalConstants.PET_DETAILS);                    
                    		response.setRenderParameter(CreatePETPortalConstants.ENABLE_MSG,CreatePETPortalConstants.ENABLE_PET);
                            request.setAttribute(CreatePETPortalConstants.ENABLE_MSG,CreatePETPortalConstants.ENABLE_PET);
                  }
                
            } catch (PEPFetchException e) {
                LOGGER.info("CreatePETController::Exception from Web Service ");
                errorMsg = prop.getProperty(CreatePETPortalConstants.INVALID_DB_ERR);
                if (request.getPortletSession().getAttribute(CreatePETPortalConstants.PET_DETAILS) != null) {
                    petDetails = (ArrayList<WorkFlow>) request.getPortletSession().getAttribute(CreatePETPortalConstants.PET_DETAILS);
                    // response.setRenderParameter(CreatePETPortalConstants.ENABLE_MSG,
                    // CreatePETPortalConstants.ENABLE_PET);
                    request.setAttribute(CreatePETPortalConstants.ENABLE_MSG,CreatePETPortalConstants.ENABLE_PET);
                }
                e.printStackTrace();
            } catch (Exception e) {
                LOGGER.info("CreatePETController::Exception from Web Service ");
                if (request.getPortletSession().getAttribute(CreatePETPortalConstants.PET_DETAILS) != null) {
                    petDetails = (ArrayList<WorkFlow>) request.getPortletSession().getAttribute(CreatePETPortalConstants.PET_DETAILS);
                    // response.setRenderParameter(CreatePETPortalConstants.ENABLE_MSG,
                    // CreatePETPortalConstants.ENABLE_PET);
                    request.setAttribute(CreatePETPortalConstants.ENABLE_MSG,CreatePETPortalConstants.ENABLE_PET);
                }
                errorMsg = prop.getProperty(CreatePETPortalConstants.INVALID_DB_ERR);
                e.printStackTrace();
            }

        }
        if (null != petDetails) {
            request.setAttribute(CreatePETPortalConstants.PET_DETAILS, petDetails);
            request.getPortletSession().setAttribute(CreatePETPortalConstants.PET_DETAILS, petDetails);
            LOGGER.info("CreatePETController::handleRenderRequest::petDetails");
        }
            
        if (request.getParameter(CreatePETPortalConstants.ACTION_PARAM).equalsIgnoreCase("ClearForm")) {        
        	errorMsg = "";        	
        }        
        LOGGER.info(" errorMsg -->" + errorMsg);
        if (null != errorMsg) {
            response.setRenderParameter(CreatePETPortalConstants.ERR_MSG,errorMsg);
        }
        if (flag) {
            response.setRenderParameter(CreatePETPortalConstants.ENABLE_MSG, CreatePETPortalConstants.ENABLE_PET);
        }
        if (null != responseMsg) {
            response.setRenderParameter(CreatePETPortalConstants.SUCCESS_MSG,responseMsg);
            LOGGER.info("CreatePETController::handleRenderRequest::responseMsg"+ responseMsg);
        }

    }
    
    private String getLoggedInUserName(PortletRequest request){
    	String createdBy= null;
    	if(request.getPortletSession().getAttribute(CreatePETPortalConstants.LOGGEDIN_USER_DETAILS)!=null){
    		UserData custuser =  (UserData) request.getPortletSession().getAttribute(CreatePETPortalConstants.LOGGEDIN_USER_DETAILS);
    		Common_BelkUser belkUser = ( Common_BelkUser)custuser.getBelkUser();
            LOGGER.info("create manual pet :handlingPagination:Enter 2 ");
            if(custuser.getVpUser()!=null){
               if(null!=custuser.getVpUser().getUserEmailAddress()){
            	   createdBy = custuser.getVpUser().getUserEmailAddress();  
               }
            }
            if(null!=belkUser) {                
             if(null!=belkUser.getLanId()) {
            	 createdBy = belkUser.getLanId();  
             }
            }
    	}    	
    	return createdBy;
    }

    @Override
    public ModelAndView handleRenderRequest(RenderRequest request,
        RenderResponse response) throws Exception {
        LOGGER.info("CreatePETController:::handleRenderRequest");
      //check for active session
    	if(request.getPortletSession().getAttribute(CreatePETPortalConstants.LOGGEDIN_USER_DETAILS)==null){
    		String username=request.getParameter("username");
    		ModelAndView mv=new ModelAndView("redirect");
    		mv.addObject("username", username);
    		return mv;
    	}
        ModelAndView modelAndView = null;
        modelAndView = new ModelAndView(CreatePETPortalConstants.CREATE_PET);
        LOGGER.info("Create PET render");
        ArrayList petList =(ArrayList) request.getAttribute(CreatePETPortalConstants.PET_DETAILS);
        if (null != petList) {
            LOGGER.info("CreatePETController::handleRenderRequest::petDetails");
            Iterator workFlowEach = petList.iterator();
            while (workFlowEach.hasNext()) {
                WorkFlow workflow = (WorkFlow) workFlowEach.next();               
            }
        }
        WorkFlowForm workFlowForm = new WorkFlowForm();
        String loggedInUser  = getLoggedInUserName(request);        
        workFlowForm.setWorkFlowList(petList);
        workFlowForm.setLoggedInUser(loggedInUser);
        
        LOGGER.info("CreatePETController::handleRenderRequest::form"+ workFlowForm.getWorkFlowList());
        modelAndView = new ModelAndView(CreatePETPortalConstants.CREATE_PET);
        String errMsg = request.getParameter(CreatePETPortalConstants.ERR_MSG);
        boolean errorOcuured=false;
        if (null != errMsg && errMsg != "") {
            LOGGER.info("CreatePETController:::ErrMsg" + errMsg);
            modelAndView.addObject(CreatePETPortalConstants.ERR_MSG, errMsg);
            errorOcuured=true;
        }

        String resMsg = request.getParameter(CreatePETPortalConstants.SUCCESS_MSG);

        if (null != resMsg && resMsg != "") {
            LOGGER.info("CreatePETController:::Response Msg" + resMsg);
            modelAndView.addObject(CreatePETPortalConstants.SUCCESS_MSG, resMsg);
        }

        // To enable create pet button
        String enableMsg = request.getParameter(CreatePETPortalConstants.ENABLE_MSG);
        LOGGER.info("CreatePETController:::enableMsg" + enableMsg);
        if (null != enableMsg && enableMsg != "" && !errorOcuured) {
            LOGGER.info("CreatePETController:::enableMsg" + enableMsg);
            modelAndView.addObject(CreatePETPortalConstants.ENABLE_MSG,enableMsg);
        }
        String orinNumber =  request.getParameter(CreatePETPortalConstants.ORIN_NO);
        LOGGER.info("CreatePETController:::orinNo" + orinNumber);
        modelAndView.addObject("orinNumber", orinNumber);
        modelAndView.addObject("workflowForm", workFlowForm);
        return modelAndView;
    }

    public boolean parseWithFallback(String orinNo) {
        try {

            LOGGER.info("groupOrin dd -->" + orinNo);

            if (!orinNo.isEmpty()) {
                if (orinNo.length() < 9 || orinNo.length() > 9) {
                    return false;
                } else {
                    String groupOrin = "" + orinNo;
                    groupOrin = "" + groupOrin.charAt(0);
                    // Checking whether Orin is Group Number or Style Orin
                    if (groupOrin
                        .equalsIgnoreCase(CreatePETPortalConstants.G_ORIN)) {
                        return true;
                    } else {
                        Integer.parseInt(orinNo);
                        return true;
                    } // else ends
                }

            } else {
                return false;
            }

        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Method to pass JSON Array
    public JSONObject populateJson(String orinNo, String itemType,
        String colorCode,String createdBy) {
        JSONObject jsonObj = new JSONObject();
        
        System.out.println("createdBy "+createdBy);

        try {
            jsonObj.put(CreatePETPortalConstants.ITEM_TYPE, itemType);
            jsonObj.put(CreatePETPortalConstants.COLOR_CODE, colorCode);
            jsonObj.put(CreatePETPortalConstants.ITEM_ID, orinNo);
            ////CHANGE REQ
            jsonObj.put(CreatePETPortalConstants.CREATED_BY, createdBy);
          //CHANGE REQ

        } catch (JSONException e) {

            e.printStackTrace();
        }
        return jsonObj;

    }

    /**
     * @param createPETDelegate
     *            the createPETDelegate to set
     */
    public void setCreatePETDelegate(CreatePETDelegate createPETDelegate) {
        this.createPETDelegate = createPETDelegate;
    }
    
    public void handleEventRequest(EventRequest request, EventResponse response)
    throws Exception {
	LOGGER.info("create manual pet :Enter "); 
	Event event = request.getEvent();
	if(event.getName()!=null ){    
    LOGGER.info("create manual pet :handlingPagination:Enter 1 "); 
    if(event.getName().equals(CreatePETPortalConstants.LOGGEDIN_USER_DETAILS)){ 
    LOGGER.info("create manual pet :handlingPagination:Enter 2 "); 
    UserData custuser= (UserData) event.getValue();               
	    if(null!=custuser){        
	     request.getPortletSession().invalidate();    
	     request.getPortletSession().setAttribute(CreatePETPortalConstants.LOGGEDIN_USER_DETAILS,custuser);   
	    }
    }

	}
}

	/* (non-Javadoc)
	 * @see org.springframework.web.portlet.mvc.ResourceAwareController#handleResourceRequest(javax.portlet.ResourceRequest, javax.portlet.ResourceResponse)
	 */
	@Override
	public ModelAndView handleResourceRequest(ResourceRequest request,
			ResourceResponse response) throws Exception {
		if(request.getResourceID()!=null && request.getResourceID().equals("invalidate")){
    		request.getPortletSession().invalidate();
    		response.getWriter().write(new String("invalidated"));
    	}
		return null;
	}

}
