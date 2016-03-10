/*
 * 
 */

package com.belk.pep.controller;

import java.util.List;
import java.util.logging.Logger;

import com.belk.pep.constants.AttributeMaintenanceScreenConstants;
import com.belk.pep.delegate.AttributeMaintenanceDelegate;
import com.belk.pep.exception.checked.PEPDelegateException;
import com.belk.pep.form.AttributeMaintenanceForm;
import com.belk.pep.vo.AttributeDetailsVO;
import com.belk.pep.vo.AttributeSearchVO;
import com.belk.pep.vo.AttributeValueVO;
import com.belk.pep.vo.CategoryListVO;


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

import org.apache.commons.lang.StringUtils;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.Controller;
import org.springframework.web.portlet.mvc.EventAwareController;
import org.springframework.web.portlet.mvc.ResourceAwareController;


/**
 * The Class ContentController.
 */

public class AttributeMaintenanceController implements Controller ,ResourceAwareController,EventAwareController{
    
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(AttributeMaintenanceController.class.getName());     

    /** The model and view. */
    private ModelAndView modelAndView=null ; 
     
    /** The AttributeMaintenance delegate. */
    private AttributeMaintenanceDelegate attributeMaintenanceDelegate;
    
    /** The attribute value vo. */
    private AttributeValueVO attributeValueVO;
    
    /** The attribute details vo. */
    private List<AttributeDetailsVO> attributeDetailsVOList;
    
    /** The attribute search vo. */
    private AttributeSearchVO attributeSearchVO;
    
    /** The category attribute id. */
    private String categoryAttributeId;
            
    /**
     * Handle action request.
     *
     * @param request the request
     * @param response the response
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.portlet.mvc.Controller#handleActionRequest(javax
     * .portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    public void handleActionRequest(ActionRequest request,ActionResponse response)  {
        
          LOGGER.info("-----Start of handleActionRequest in AttributeMaintenanceController-----");       
        
          // Get the attribute list for given search criteria
          getAttributeListForSearch(request, response);
                            
          LOGGER.info("-----End of handleActionRequest in AttributeMaintenanceController--------");         
    }
    
    /**
     * Gets the attribute list for search.
     *
     * @param request the request
     * @param response the response
     * @return the attribute list for search
     */
    private void getAttributeListForSearch(ActionRequest request, ActionResponse response) {

        String formAction = request.getParameter(AttributeMaintenanceScreenConstants.ACTION_PARAMETER);
        AttributeMaintenanceForm attributeMaintenanceForm = new AttributeMaintenanceForm();
        AttributeValueVO attributeValueVO = null;
        List<AttributeDetailsVO> lstAttributeDetails = null;
        PortletSession portletSession = request.getPortletSession();

        try {
            LOGGER.info(" FormAction ---> " + formAction);

            if (formAction != null  && formAction .equals(AttributeMaintenanceScreenConstants.SEARCH_ATTRIBUTE_KEy)) {

                LOGGER .info(" Inside searchAttribute Action  .....................");

                String iphvalue = request.getParameter("iphvalue");
                String pim = request.getParameter("pim");
                String blueMartin = request.getParameter("blueMartin");
                String radioactive = request.getParameter("radioactive");
                String radioinactive = request.getParameter("radioinactive");
                String categoryValue = request.getParameter("categoryValue");
                AttributeSearchVO attributeSearchVO = new AttributeSearchVO();

                if (categoryValue != null  && !categoryValue .equalsIgnoreCase(AttributeMaintenanceScreenConstants.EMPTY)) {
                    attributeSearchVO.setCategoryIPH(categoryValue.substring(0,  4));
                }
                else {
                    attributeSearchVO.setCategoryIPH("");
                }

                attributeSearchVO.setCategoryIdAndName(categoryValue);
                if (radioactive != null  && radioactive .equalsIgnoreCase(AttributeMaintenanceScreenConstants.YES)) {
                    attributeSearchVO .setAttributeStatus(AttributeMaintenanceScreenConstants.ACTIVE);
                }
                else {
                    attributeSearchVO.setAttributeStatus(AttributeMaintenanceScreenConstants.INACTIVE);
                }
                attributeSearchVO.setAttributeTypePIM(pim);
                attributeSearchVO.setAttributeTypeBM(blueMartin);

                lstAttributeDetails =  attributeMaintenanceDelegate.getAttributeListForSearchCriteria(attributeSearchVO);
                portletSession.setAttribute(AttributeMaintenanceScreenConstants.ATTRIBUTE_LIST, lstAttributeDetails);
                portletSession.setAttribute(AttributeMaintenanceScreenConstants.SEARCH_CRITERIA, attributeSearchVO);
                setAttributeDetailsVOList(lstAttributeDetails);

                response.setRenderParameter(AttributeMaintenanceScreenConstants.ATTRIBUTE_KEY, AttributeMaintenanceScreenConstants.ATTRIBUTE_SEARCH_KEY);

            }
            else if (formAction != null && formAction.equals(AttributeMaintenanceScreenConstants.UPDATE_ATTRIBUTE)) {
                LOGGER.info(" Inside updateAttribute request .....................");

                AttributeValueVO udpateAttributeValueVO = updateAttributeDetails(request);
                int updateStatus =  attributeMaintenanceDelegate.updateAttributeDetails(udpateAttributeValueVO);
                if (updateStatus > 0) {
                    attributeValueVO = attributeMaintenanceDelegate.getAttributeDetailsForCatIdAttId(
                                udpateAttributeValueVO.getCategoryId(),
                                udpateAttributeValueVO.getAttributeId());
                    setAttributeValueVO(attributeValueVO);
                }
                else {
                    // Update Failed...
                }
                response.setRenderParameter(AttributeMaintenanceScreenConstants.ATTRIBUTE_KEY, AttributeMaintenanceScreenConstants.ATTRIBUTE_UPDATE_KEY);

            }
            else if (formAction != null && formAction.equals(AttributeMaintenanceScreenConstants.GET_ATTRIBUTE_DETAILS)) {
                LOGGER
                    .info(" Inside getAttributeDetails request .....................");

                String categoryId = request.getParameter("categoryId");
                String attributeId = request.getParameter("attributeId");
                String attributeType = request.getParameter("attributeType");

                attributeValueVO =  attributeMaintenanceDelegate.getAttributeDetailsForCatIdAttId(categoryId, attributeId);
                setAttributeValueVO(attributeValueVO);
                setCategoryAttributeId(categoryId + "~" + attributeId);

                response.setRenderParameter(AttributeMaintenanceScreenConstants.ATTRIBUTE_KEY, AttributeMaintenanceScreenConstants.GET_ATTRIBUTE_DETAILS);
            }
            else if (formAction != null  && formAction.equals(AttributeMaintenanceScreenConstants.CLEAR_FORM)) {
                LOGGER.info(" Inside clear form .....................");

                portletSession.setAttribute(AttributeMaintenanceScreenConstants.ATTRIBUTE_LIST, null);
                portletSession.setAttribute(AttributeMaintenanceScreenConstants.SEARCH_CRITERIA, null);

                response.setRenderParameter(AttributeMaintenanceScreenConstants.ATTRIBUTE_KEY, AttributeMaintenanceScreenConstants.CLEAR_FORM_DATA);
            }
        }
        catch (PEPDelegateException pEPDelegateException) {
            LOGGER.info(" Exception Block.... ......" + pEPDelegateException.getMessage());
        }

    }
    
    /**
     * Handle render request.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.portlet.mvc.Controller#handleRenderRequest(javax
     * .portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    public ModelAndView handleRenderRequest(RenderRequest request,
            RenderResponse response) throws Exception
    {
        List<CategoryListVO> lstCategoryListVO = null;
        List<AttributeDetailsVO> lstAttributeDetails = null;
        AttributeSearchVO attributeSearchVO = null;
        AttributeMaintenanceForm attributeMaintenanceForm = new AttributeMaintenanceForm();
        PortletSession portletSession = request.getPortletSession();
        modelAndView= new ModelAndView(AttributeMaintenanceScreenConstants.PAGE);  
        
        LOGGER.info("-------Start of handleRenderRequest in AttributeMaintenanceController--------");   
        
        String attSearchKEy = request.getParameter(AttributeMaintenanceScreenConstants.ATTRIBUTE_KEY);
        
        try {
                // Get on load & every time
                lstCategoryListVO = (List<CategoryListVO>)portletSession.getAttribute(AttributeMaintenanceScreenConstants.SEARCH_CATEGORY_LIST);
                if (lstCategoryListVO == null) {
                    lstCategoryListVO = attributeMaintenanceDelegate.getCategoryList();
                    portletSession.setAttribute(AttributeMaintenanceScreenConstants.SEARCH_CATEGORY_LIST, lstCategoryListVO);
                }
                attributeMaintenanceForm.setCategoryListVO(lstCategoryListVO);
            
            if (attSearchKEy != null &&  !StringUtils.isBlank(attSearchKEy) &&
                    attSearchKEy.equalsIgnoreCase(AttributeMaintenanceScreenConstants.ATTRIBUTE_SEARCH_KEY)) {
                LOGGER.info(" Inside searchAttribute render .....................");
               
                attributeMaintenanceForm.setAttributeDetail(getAttributeDetailsVOList());
                attributeSearchVO = (AttributeSearchVO)portletSession.getAttribute(AttributeMaintenanceScreenConstants.SEARCH_CRITERIA);
                attributeMaintenanceForm.setAttributeSearchVO(attributeSearchVO);
                
                if(attributeMaintenanceForm.getAttributeDetail() == null){
                    attributeMaintenanceForm.setSearchResultErrorMessage("No data found for this search criteria.");
                }else if(attributeMaintenanceForm.getAttributeDetail() != null && attributeMaintenanceForm.getAttributeDetail().size() == 0){
                    attributeMaintenanceForm.setSearchResultErrorMessage("No data found for this search criteria.");
                }
            }
            else if (attSearchKEy != null && !StringUtils.isBlank(attSearchKEy)
                         && attSearchKEy.equalsIgnoreCase(AttributeMaintenanceScreenConstants.ATTRIBUTE_UPDATE_KEY)) {
                LOGGER.info(" Inside updateAttribute render .....................");
                
                
                lstAttributeDetails =  (List<AttributeDetailsVO>)portletSession.getAttribute(AttributeMaintenanceScreenConstants.ATTRIBUTE_LIST);
                attributeSearchVO = (AttributeSearchVO)portletSession.getAttribute(AttributeMaintenanceScreenConstants.SEARCH_CRITERIA);
                
                attributeMaintenanceForm.setAttributeSearchVO(attributeSearchVO); // To Retain Search Criteria
                attributeMaintenanceForm.setAttributeValueVO(getAttributeValueVO()); //Get Attribute value object
                attributeMaintenanceForm.setAttributeDetail(lstAttributeDetails); // To Retail the Attribute Details
                attributeMaintenanceForm.setCategoryAttributeId(getCategoryAttributeId());
                attributeMaintenanceForm.setEnableUpdateDetailsButton(true);
                attributeMaintenanceForm.setUpdateStatusMessage("Attribute details updated successfully.");
                
            }
            else if (attSearchKEy != null &&  !StringUtils.isBlank(attSearchKEy)
                    && attSearchKEy.equalsIgnoreCase(AttributeMaintenanceScreenConstants.GET_ATTRIBUTE_DETAILS)) {
                LOGGER.info(" Inside getAttributeDetails render .....................");
           
                lstAttributeDetails =  (List<AttributeDetailsVO>)portletSession.getAttribute(AttributeMaintenanceScreenConstants.ATTRIBUTE_LIST);
                attributeSearchVO = (AttributeSearchVO)portletSession.getAttribute(AttributeMaintenanceScreenConstants.SEARCH_CRITERIA);
                if(lstAttributeDetails != null){
                    attributeMaintenanceForm.setAttributeDetail(lstAttributeDetails);  // To Retain Attribute List
                }
              
                attributeMaintenanceForm.setAttributeSearchVO(attributeSearchVO); // To Retain Search Criteria
                attributeMaintenanceForm.setAttributeValueVO(getAttributeValueVO()); //Get Attribute value object
                attributeMaintenanceForm.setCategoryAttributeId(getCategoryAttributeId());
                attributeMaintenanceForm.setEnableUpdateDetailsButton(true);
                
            } else if (attSearchKEy != null && !StringUtils.isBlank(attSearchKEy) 
                    && attSearchKEy.equalsIgnoreCase(AttributeMaintenanceScreenConstants.CLEAR_FORM_DATA)) {
                LOGGER.info("Inside getAttributeDetails render ....................");
            }
           
            modelAndView.addObject(AttributeMaintenanceScreenConstants.CONTENT_DISPLAY_FORM, attributeMaintenanceForm);
        }
        catch (PEPDelegateException e) {
            e.printStackTrace();
        }
       
        
        LOGGER.info("---------End of handleRenderRequest in AttributeMaintenanceController---------");    
        return modelAndView;  
    }
    
    /**
     * Update attribute details.
     *
     * @param request the request
     * @return the attribute value vo
     */
    private AttributeValueVO updateAttributeDetails(ActionRequest request){
        boolean updateStatus = false;
                
        String attDesription = request.getParameter("attDescription");
        String attDisplayName = request.getParameter("attDisplayname");
        String attributeType = request.getParameter("attributeType");
        String attFieldValue = request.getParameter("attFieldvalue");
        
        String attradioactive = request.getParameter("attradioactive");
        String attradioinactive = request.getParameter("attradioinactive");
        String issearchable = request.getParameter("issearchable");
        String iseditable = request.getParameter("iseditable");
        String isrequired = request.getParameter("isrequired");
        
        String categoryId = request.getParameter("categoryId");
        String attributeId = request.getParameter("attributeId");
        setCategoryAttributeId(categoryId+"~"+attributeId); // Setting category Id & attribute Id
        
        String radioFirstValue = request.getParameter("radiofirst");
        String radioSecondValue =  request.getParameter("radiosecond");
        String checkboxFirstValue = request.getParameter("checkboxfirst");
        String checkboxSecondValue = request.getParameter("checkboxsecond ");
        
        String dropDrownAttributeValue = request.getParameter("dropDrownAttributeValue");
        
        AttributeValueVO updateAttributeValueVO = new AttributeValueVO();

        updateAttributeValueVO.setAttributeId(attributeId);
        updateAttributeValueVO.setCategoryId(categoryId);
        
        if(attDesription != null){
            updateAttributeValueVO.setAttributeName(attDesription);
        } else{
            updateAttributeValueVO.setAttributeName(AttributeMaintenanceScreenConstants.EMPTY);
        }
        if(attDisplayName != null){
            updateAttributeValueVO.setAttributeDisplayName(attDisplayName);
        } else{
            updateAttributeValueVO.setAttributeName(AttributeMaintenanceScreenConstants.EMPTY);
        }
        if(attradioactive != null && attradioactive.equalsIgnoreCase(AttributeMaintenanceScreenConstants.YES)){
            updateAttributeValueVO.setAttributeStatus(AttributeMaintenanceScreenConstants.ACTIVE);
        }else{
            updateAttributeValueVO.setAttributeStatus(AttributeMaintenanceScreenConstants.INACTIVE);
        }
        if(issearchable != null && issearchable.equalsIgnoreCase(AttributeMaintenanceScreenConstants.ON)){
            updateAttributeValueVO.setIsSearchable(AttributeMaintenanceScreenConstants.YES);
        }else{
            updateAttributeValueVO.setIsSearchable(AttributeMaintenanceScreenConstants.NO);
        }
        if(iseditable != null && iseditable.equalsIgnoreCase(AttributeMaintenanceScreenConstants.ON)){
            updateAttributeValueVO.setIsEditable(AttributeMaintenanceScreenConstants.YES);
        }else{
            updateAttributeValueVO.setIsEditable(AttributeMaintenanceScreenConstants.NO);
        }
        if(isrequired != null && isrequired.equalsIgnoreCase(AttributeMaintenanceScreenConstants.ON)){
            updateAttributeValueVO.setIsRequired(AttributeMaintenanceScreenConstants.YES);
        }else{
            updateAttributeValueVO.setIsRequired(AttributeMaintenanceScreenConstants.NO);
        }
        if (attributeType != null) {
            if (attributeType.equalsIgnoreCase(AttributeMaintenanceScreenConstants.TEXT_FIELD)) {
                updateAttributeValueVO.setAttributeFieldValue(attFieldValue);
            }
            else if (attributeType.equalsIgnoreCase(AttributeMaintenanceScreenConstants.DROP_DOWN)) {
                updateAttributeValueVO.setAttributeFieldValue(dropDrownAttributeValue);
            }
            else if (attributeType.equalsIgnoreCase(AttributeMaintenanceScreenConstants.RADIO_BUTTON)) {
                updateAttributeValueVO.setAttributeFieldValue(radioFirstValue + " | " + radioSecondValue);

            }
            else if (attributeType.equalsIgnoreCase(AttributeMaintenanceScreenConstants.CHECK_BOXES)) {
                updateAttributeValueVO.setAttributeFieldValue(checkboxFirstValue + " | " + checkboxSecondValue);
            }
        }
        else {
            updateAttributeValueVO.setAttributeFieldValue(AttributeMaintenanceScreenConstants.EMPTY);
        }
        return updateAttributeValueVO;
    }
    
    /**
     * Handle resource request.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.ResourceAwareController#handleResourceRequest(javax.portlet.ResourceRequest, javax.portlet.ResourceResponse)
     */
    @SuppressWarnings("null")
    @Override
    public ModelAndView handleResourceRequest(ResourceRequest request,ResourceResponse response) throws Exception {
        LOGGER.info("-------Start of handleResourceRequest in AttributeMaintenanceController--------"); 
        modelAndView= new ModelAndView(AttributeMaintenanceScreenConstants.PAGE);  
        LOGGER.info("---------End of handleResourceRequest in AttributeMaintenanceController---------"); 
        return modelAndView;
    }
    
    /**
     * Handle event request.
     *
     * @param request the request
     * @param response the response
     * @throws Exception the exception
     */
    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.EventAwareController#handleEventRequest(javax.portlet.EventRequest, javax.portlet.EventResponse)
     */
    @Override
    public void handleEventRequest(EventRequest request, EventResponse response)
        throws Exception {
        
        LOGGER.info("-------Start of handleResourceRequest in AttributeMaintenanceController--------"); 
        LOGGER.info("---------End of handleResourceRequest in AttributeMaintenanceController---------"); 
    }
    
    /**
     * Gets the attribute maintenance delegate.
     *
     * @return the attribute maintenance delegate
     */
    public AttributeMaintenanceDelegate getAttributeMaintenanceDelegate() {
        return attributeMaintenanceDelegate;
    }

    /**
     * Sets the attribute maintenance delegate.
     *
     * @param attributeMaintenanceDelegate the new attribute maintenance delegate
     */
    public void setAttributeMaintenanceDelegate(
        AttributeMaintenanceDelegate attributeMaintenanceDelegate) {
        this.attributeMaintenanceDelegate = attributeMaintenanceDelegate;
    }

    /**
     * Gets the attribute value vo.
     *
     * @return the attribute value vo
     */
    public AttributeValueVO getAttributeValueVO() {
        return attributeValueVO;
    }

    /**
     * Sets the attribute value vo.
     *
     * @param attributeValueVO the new attribute value vo
     */
    public void setAttributeValueVO(AttributeValueVO attributeValueVO) {
        this.attributeValueVO = attributeValueVO;
    }

    /**
     * Gets the attribute details vo list.
     *
     * @return the attribute details vo list
     */
    public List<AttributeDetailsVO> getAttributeDetailsVOList() {
        return attributeDetailsVOList;
    }

    /**
     * Sets the attribute details vo list.
     *
     * @param attributeDetailsVOList the new attribute details vo list
     */
    public void setAttributeDetailsVOList(
        List<AttributeDetailsVO> attributeDetailsVOList) {
        this.attributeDetailsVOList = attributeDetailsVOList;
    }

    /**
     * Gets the attribute search vo.
     *
     * @return the attribute search vo
     */
    public AttributeSearchVO getAttributeSearchVO() {
        return attributeSearchVO;
    }

    /**
     * Sets the attribute search vo.
     *
     * @param attributeSearchVO the new attribute search vo
     */
    public void setAttributeSearchVO(AttributeSearchVO attributeSearchVO) {
        this.attributeSearchVO = attributeSearchVO;
    }

    /**
     * Gets the category attribute id.
     *
     * @return the category attribute id
     */
    public String getCategoryAttributeId() {
        return categoryAttributeId;
    }

    /**
     * Sets the category attribute id.
     *
     * @param categoryAttributeId the new category attribute id
     */
    public void setCategoryAttributeId(String categoryAttributeId) {
        this.categoryAttributeId = categoryAttributeId;
    }
    
    
}
