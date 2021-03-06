package com.belk.pep.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
//import java.util.logging.Logger;
import com.belk.pep.common.model.PageAnchorDetails;
import org.apache.log4j.Logger;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.belk.pep.common.model.ImageDetails;
import com.belk.pep.constants.ImageConstants;
import com.belk.pep.delegate.ImageRequestDelegate;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.FileUploadForm;
import com.belk.pep.form.ImageForm;
import com.belk.pep.form.ImageProductDetails;
import com.belk.pep.form.PepDetailsHistory;
import com.belk.pep.form.SamleImageDetails;
import com.belk.pep.form.StyleInfoDetails;
import com.belk.pep.form.WorkListDisplayForm;
import com.belk.pep.helper.GroupingImageHelper;
import com.belk.pep.model.ImageLinkVO;
import com.belk.pep.model.ImageRejectReason;
import com.belk.pep.model.UploadImagesDTO;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.util.FtpUtil;
import com.belk.pep.util.PropertyLoader;



/**
 * This class is responsible to controlling the image screen activities
 * 
 * 
 */
@Controller
@RequestMapping("VIEW")
public class ImageRequestController {

    /** The Constant LOGGER. */
    private final static Logger LOGGER =
        Logger.getLogger(ImageRequestController.class.getName());
    /*This helper class is used for grouping image functionality.
     *  New Helper class is introduced as part of PIM Phase 2
     *  06/04/2016
     *  Modified By: Cognizant
     */    
  private GroupingImageHelper groupingImageHelper = new GroupingImageHelper(); 
    
 private FileUploadForm fdForm=null ;
	
	@ModelAttribute("fdForm")
	private FileUploadForm getFdForm() {
		return fdForm;
	}
	
	private String uploadedSuccess = "";	
	
	/**
	 * @param uploadedSuccess the uploadedSuccess to set
	 */
	public void setUploadedSuccess(String uploadedSuccess) {
		this.uploadedSuccess = uploadedSuccess;
	}
	

	/**
	 * @return the uploadedSuccess
	 */
	@ModelAttribute("uploadedSuccess")
	public String getUploadedSuccess() {
		return uploadedSuccess;
	}

	@InitBinder
    public final void initBinder(final PortletRequest request,
			final PortletRequestDataBinder binder) throws Exception {
	}

    /** The login delegate. */
    private ImageRequestDelegate imageRequestDelegate;

   // private ImageForm imageForm;

    public ImageRequestDelegate getImageRequestDelegate() {
        return imageRequestDelegate;
    }

    public void setImageRequestDelegate(
        ImageRequestDelegate imageRequestDelegate) {
        this.imageRequestDelegate = imageRequestDelegate;
    }
    
    /** 
     * Web service to callRemoveImageWebService.
     * 
     * @param imageInfo
     * @return
     * @throws Exception
     * @throws PEPFetchException
     */
    private String callRemoveImageWebService(JSONArray imageInfo) throws Exception,
        PEPFetchException {
        return imageRequestDelegate.callRemoveImageWebService(imageInfo);
    }
	
	// Service Call for Upload VPI
	private String callUploadVPIService(JSONArray imageInfo)
			throws Exception, PEPFetchException {

		String responseMsg = null;
		responseMsg = imageRequestDelegate.callUploadVPIService(imageInfo);
		return responseMsg;

	}
	
	// Service Call for SubmitorReject
	private String callSubmitOrRejectService(JSONArray jsonArray)
			throws Exception, PEPFetchException {

		String responseMsg = null;
		responseMsg = imageRequestDelegate.callSubmitOrRejectService(jsonArray);
		return responseMsg;

	}
	
	
	// Service to callSaveImageShotTypeService
    private String callSaveImageShotTypeService(JSONArray imageInfo) throws Exception,
        PEPFetchException {
    	
        String responseMsg = null;
        responseMsg = imageRequestDelegate.callSaveImageShotTypeService(imageInfo);
        return responseMsg;

    }
	
    /**
     * This is default render when ImagePortlet page loads 1st time
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@RenderMapping
    public ModelAndView handleRenderRequest(
    		RenderRequest request, 
    		RenderResponse response) throws Exception {
    	//check for active session
    	if(request.getPortletSession().getAttribute("ImageDetailsOBJ")==null){
    		String username=request.getParameter("username");
    		ModelAndView mv=new ModelAndView("redirect");
    		mv.addObject("username", username);
    		return mv;
    	}
    	this.fdForm = new FileUploadForm();
    	
    	//Get user details in render logic for user details need to modify for the actual user handling
    	String userAttr ="";
    	ImageDetails imageDetailsFromIPC = getUserDetailsfromLogin(request);
    	
    	ModelAndView mv = null;
    	String roleNameFromIPC = null;        
        String orinNumberFromIPC = null;
        String orinNumber = null;
        String petStatus = null ;
        String imageStatus = null ;
        String pageNumber = request.getParameter("returnPageNumber");
        String selectedParentOrin = request.getParameter("returnOrinNumber");
        String [] imageWithPetStatusArray = null;
        if(request.getPortletSession().getAttribute("internalUser")!=null ){
        	  LOGGER.info("internal user name ---" + request.getPortletSession().getAttribute("internalUser"));
        	  String url ="http://ralpimpmapdv01.belkinc.com:10079/wps/portal/home/InternalUserLogin";
        	
        	 
        	 
        	 
        }
        if(null != imageDetailsFromIPC){
        	LOGGER.info("imageDetailsFromIPC check here ----" + imageDetailsFromIPC.getOrinNumber());	
        }
        else{
        	LOGGER.info("imageDetailsFromIPC- null ---" );	
        	
        }
        
    	if(null != imageDetailsFromIPC){
    		String orinOrGrouping =  imageDetailsFromIPC.getOrinNumber();
    		if(null != orinOrGrouping && (orinOrGrouping.trim()).length()>7) {
        String loggedInUser = "";
        LOGGER.info("imageDetailsFromIPC----" + imageDetailsFromIPC.getOrinNumber());
    		if (imageDetailsFromIPC.getUserData().getBelkUser() != null) {
				loggedInUser = imageDetailsFromIPC.getUserData().getBelkUser().getLanId();
			} else if (imageDetailsFromIPC.getUserData().getVpUser() != null) {
				loggedInUser = imageDetailsFromIPC.getUserData().getVpUser().getUserEmailAddress();
			}
    		
    		LOGGER.info("loggedInUser:::::::::" +loggedInUser);
    		orinNumberFromIPC = imageDetailsFromIPC.getOrinNumber();
			LOGGER.info("Selected Orin from IPC Login****"+ imageDetailsFromIPC.getOrinNumber());			
			if (StringUtils.isNotBlank(orinNumberFromIPC)) {
				orinNumber = orinNumberFromIPC;
			}
    	
			imageWithPetStatusArray = imageDetailsFromIPC.getImageStatus().split("-");
			if(null != imageWithPetStatusArray){
				LOGGER.info("*****imageWithPetStatusArray not null from IPC*******");
				imageStatus = imageWithPetStatusArray[0];
				petStatus = imageWithPetStatusArray[1];				
			}
		LOGGER.info("-----imageStatus----" + imageStatus +"----petStatus-----"+ petStatus);
		
		roleNameFromIPC = imageDetailsFromIPC.getUserData().getRoleName();
		LOGGER.info("roleNameFromIPC Image----" + roleNameFromIPC);
		
		if("Deactivated".equalsIgnoreCase(petStatus)){
    		request.setAttribute("userAttr", "readonly");
    		LOGGER.info("Setting rolename to readonly------" + request.getAttribute("userAttr"));
    	}else{
    		LOGGER.info("----------Get Role Name for Non Deactivated PET00000000--------------" + roleNameFromIPC);
    		request.setAttribute("userAttr", roleNameFromIPC);
    	}
    	//LOGGER.info("GetRoleName::::" + imageDetailsFromIPC.getUserData().getRoleName());
    	
		//Setting petStatus coming from IPC
    	request.setAttribute("petStatus", petStatus);
    	
    	
    	//Putting 'userAttr' in session to pass correct role name
    	PortletSession session = request.getPortletSession();
    	if("Deactivated".equalsIgnoreCase(petStatus)){    	
    		session.setAttribute("userAttr", "readonly", PortletSession.PORTLET_SCOPE);
    		LOGGER.info("Setting rolename to readonly1111111------" + session.getAttribute("userAttr"));
    	}else{
    		LOGGER.info("----------Set Role Name for Non Deactivated PET1111111--------------" + roleNameFromIPC);
    		session.setAttribute("userAttr", roleNameFromIPC, PortletSession.PORTLET_SCOPE);
    	}

        
    	ArrayList styleInfoList = imageRequestDelegate.getStyleInfoDetails(orinNumber);        
        ArrayList imageProductInfoList = imageRequestDelegate.getImageInfoDetails(orinNumber);
        ArrayList vendorInformationList = imageRequestDelegate.getVendorInformation(orinNumber);
        ArrayList contactInformationList = imageRequestDelegate.getContactInformation(orinNumber); 
        ArrayList pepHistoryList = imageRequestDelegate.getPepHistoryDetails(orinNumber);
        List<ImageRejectReason> allImageRejectReasons = imageRequestDelegate.getImageRejectReasons();
        
        LOGGER.info("****inside the  handle handleRenderRequest method****"
            + request.getParameter(ImageConstants.ACTION));
        
        ImageForm imageForm = new ImageForm();
        String formSessionKey = request.getPortletSession().getId() + loggedInUser;
        request.getPortletSession().setAttribute("formSessionKey", formSessionKey);
        
        
                
                LOGGER.info("Before checking list value");
                
                if (styleInfoList.size() > 0 && styleInfoList !=null) {
                    LOGGER.info("inside StyleInfoList"
                        + ((StyleInfoDetails) styleInfoList.get(0))
                            .getOrinGrouping());
                    imageForm.setUsername(loggedInUser);
                    imageForm.setStyleInfo(styleInfoList);
                    LOGGER.info("exiting StyleInfoList");
                }
                if (imageProductInfoList.size() > 0 && imageProductInfoList !=null) {
                    LOGGER.info("inside ImageInfoList");
                    imageForm.setImageProductInfo(imageProductInfoList);
                    LOGGER.info("exiting inside ImageInfoList");
                }
                if (vendorInformationList.size() > 0 && vendorInformationList !=null) {
                    LOGGER.info("inside vendorInformationList");
                    imageForm.setVendorInfoList(vendorInformationList);
                    LOGGER.info("exiting vendorInformationList");
                }
                
                if (contactInformationList.size() > 0 && contactInformationList !=null) {
                    LOGGER.info("inside contactInformationList");
                    imageForm.setContactInfoList(contactInformationList);
                    LOGGER.info("exiting inside contactInformationList");
                }
                
                if (pepHistoryList.size() > 0 && pepHistoryList !=null) {
                    LOGGER.info("inside pepHistoryList");
                    imageForm.setPepHistoryList(pepHistoryList);
                    LOGGER.info("exiting inside pepHistoryList");
                }
                                            

                if (allImageRejectReasons.size() > 0 && allImageRejectReasons !=null) {
                    LOGGER.info("inside allImageRejectReasons");
                    imageForm.setAllImageRejectReasons(allImageRejectReasons);
                    LOGGER.info("exiting inside allImageRejectReasons");
                }

                List<WorkFlow> workFlowList   = new ArrayList<WorkFlow>();   	
            	workFlowList =  imageRequestDelegate.getImageMgmtDetailsByOrin(orinNumber);
            	WorkListDisplayForm workListDisplayForm = new WorkListDisplayForm();
            	   if(workFlowList.size()>0){
            	       workListDisplayForm.setPetNotFound(null); 
            	   }
	           
	     	    
	     	   //File upload toggle after service call 
				String tempStatus  =  (String) request.getPortletSession().getAttribute("uploadedSucess");
				String imageName  =  (String) request.getPortletSession().getAttribute("imageName");
				
				mv = new ModelAndView(ImageConstants.MODELVIEW_SUCCESS);
			
				
	     	    
				if(tempStatus != null){
					if(tempStatus == "Y"){
						setUploadedSuccess("Y");
						LOGGER.info("selectedColorOrin *************** setUploadedSuccess"+request.getAttribute("selectedColorOrin"));
						 ArrayList sampleImageLinkList = imageRequestDelegate.getSampleImageLinks((String)request.getAttribute("selectedColorOrin"));
						 if (sampleImageLinkList.size() > 0 && sampleImageLinkList !=null) {	                 
							 	imageForm.setSampleImageDetailList(sampleImageLinkList);
							 	mv.addObject("selectedColorOrin", request.getAttribute("selectedColorOrin"));
						 }
						 
					}
					else if(tempStatus == "N"){
						setUploadedSuccess("N");
					}
				}
				
				
				
				
				mv.addObject(ImageConstants.IMAGE_INFO_DETAILS, imageForm);
	            workListDisplayForm.setWorkFlowlist(workFlowList);
	     	    mv.addObject("workflowForm", workListDisplayForm);
	     	    mv.addObject("uploadSuccess", tempStatus );
	     	    mv.addObject("imageName", imageName );
	     	    request.getPortletSession().removeAttribute("uploadedSucess");    	    
	     	    LOGGER.info("action value "+ request.getParameter(ImageConstants.ACTION)+"uploadSuccess::123"+tempStatus );
	     	    
	     	    LOGGER.info("formSessionKEY------------------" +formSessionKey +"loggedinUSER------------" + loggedInUser);
	     	    request.getPortletSession().setAttribute(formSessionKey, imageForm);
	     	    mv.addObject("imageForm", (ImageForm)request.getPortletSession().getAttribute(formSessionKey));
	     	 
    		}else {
    			mv = getGroupingImageDetails(request,response,imageDetailsFromIPC);	
    		}   
    	}
        mv.addObject("pageNumber", pageNumber);
        
        if(selectedParentOrin!=null && !selectedParentOrin.equals(""))
        {
        	mv.addObject("orinNumber", selectedParentOrin);
        }
        return mv;
    }
    
    
   
   /**
    * This method will attempt to delete multiple images via PIM's Image Web Service.  
    * If successful, this method will also soft-delete the images from PEP database.
    * 
    * @param request
    * @param response
    */
   @ResourceMapping("removeSampleImageUrl")
   public void removeSampleImageMethod(ResourceRequest request, ResourceResponse response){

       String responseMsg = "";

       JSONObject jsObj = new JSONObject();
       LOGGER.info("inside.............resource");

       ImageDetails imageDetailsFromIPC = getUserDetailsfromLogin(request);
       String updatedBy = "";
       if (imageDetailsFromIPC.getUserData().getBelkUser() != null) {
           updatedBy = imageDetailsFromIPC.getUserData().getBelkUser().getLanId();
       } else if (imageDetailsFromIPC.getUserData().getVpUser() != null) {
           updatedBy = imageDetailsFromIPC.getUserData().getVpUser().getUserEmailAddress();
       }

       String orinId = request.getParameter("selectedColorOrin");
       String imagId[] = request.getParameterValues("imageIDToDel[]");
       String imagName[] = request.getParameterValues("imageNameToDel[]");
       JSONObject removeJSON = new JSONObject();
       JSONArray jsonArray = new JSONArray();
       try {
           JSONObject jsonStyle;

           for (int i = 0; i < imagId.length; i++) {
               jsonStyle = populateJson(orinId.trim(), imagId[i]);
               jsonArray.put(jsonStyle);
           }
           // First, call PIM webservice for multiple image deletes
           responseMsg = callRemoveImageWebService(jsonArray);
           String[] resCodeWithMsg = responseMsg.split("_");
           String resCode = resCodeWithMsg[1];
           
           LOGGER.info("*** Response Code from webservice ***"+resCode);
           
           if ("100".equalsIgnoreCase(resCode)) {
               LOGGER.info("***Service success response For Remove***");
               try {
                   // Then, insert "image delete" record.
                   callDeleteImage(orinId, updatedBy, imagId, imagName);
                   
               } catch (PEPFetchException ex) {
                   LOGGER.error("PEPFetchException: " + ex.getMessage());
               }

               try {
                   /*
                    * Updating Image management Image Status
                    */
                   JSONObject jsonStyle1 = new JSONObject();
                   String responseMsg1 = "";
                   JSONArray jsonArray1 = new JSONArray();
                   ArrayList<SamleImageDetails> sampleImageLinkList = imageRequestDelegate.getSampleImageLinks(orinId);

                   if (null != sampleImageLinkList && sampleImageLinkList.size() > 0) {
                       for (int i = 0; i < sampleImageLinkList.size(); i++) {
                           SamleImageDetails sampleImageDetailsBean = sampleImageLinkList.get(i);
                           if (sampleImageDetailsBean.getImageStatus().contains("Rejected")) {
                               // rejected_status = true;
                               LOGGER.info("Call Service rejected in remove");
                               jsonStyle1 = populateJsonForImageManagement(orinId, updatedBy, "04");
                               jsonArray1.put(jsonStyle1);
                               //responseMsg1 = callApproveActionService(jsonArray1);
                               //LOGGER.info("-----remove responseMsg in rejected-----" + responseMsg1);
                           }
                           if (sampleImageDetailsBean.getImageStatus().equalsIgnoreCase("Ready_For_Review")
                                   || sampleImageDetailsBean.getImageStatus().equalsIgnoreCase("ReadyForReview")
                                   || sampleImageDetailsBean.getImageStatus().contains("Ready_")) {
                               // review_status = true;
                               LOGGER.info("Call Service readyforreview in remove");
                               jsonStyle1 = populateJsonForImageManagement(orinId, updatedBy, "08");
                               jsonArray1.put(jsonStyle1);
                               //responseMsg1 = callApproveActionService(jsonArray1);
                               //LOGGER.info("-----remove responseMsg in ReadyforReview-----" + responseMsg1);
                           }
                           if (sampleImageDetailsBean.getImageStatus().equalsIgnoreCase("Initiated")) {
                               // initiated_status = true;
                               LOGGER.info("Call Service initiated in remove");
                               jsonStyle1 = populateJsonForImageManagement(orinId, updatedBy, "01");
                               jsonArray1.put(jsonStyle1);
                               //responseMsg1 = callApproveActionService(jsonArray1);
                               //LOGGER.info("-----remove responseMsg in Initiated-----" + responseMsg1);
                           }
                           if (sampleImageDetailsBean.getImageStatus().equalsIgnoreCase("Completed")) {
                               // completed_status = true;
                               LOGGER.info("Call Service completed in remove");
                               jsonStyle1 = populateJsonForImageManagement(orinId, updatedBy, "02");
                               jsonArray1.put(jsonStyle1);
                               //responseMsg1 = callApproveActionService(jsonArray1);
                               //LOGGER.info("-----remove responseMsg in completed-----" + responseMsg1);
                           }
                       }
                   } else {
                       // initiated_status = true;
                       LOGGER.info("Call Service initiated in remove");
                       jsonStyle1 = populateJsonForImageManagement(orinId, updatedBy, "01");
                       jsonArray1.put(jsonStyle1);
                       //responseMsg1 = callApproveActionService(jsonArray1);
                       //LOGGER.info("-----remove responseMsg in Initiated-----" + responseMsg1);
                   }
                   
                   if ((jsonArray1 != null) && (jsonArray1.length() != 0))
                   {
                       responseMsg1 = callApproveActionService(jsonArray1);
                       LOGGER.info("-----remove responseMsg in Initiated-----" + responseMsg1);
                   }

                   if ("Image status update is successful".equalsIgnoreCase(responseMsg1)) {
                       LOGGER.info(" ---Service Response is Success on Remove--- ");
                       String responseCodeOnRemove = "100";
                       jsObj.put("responseCodeOnRemove", responseCodeOnRemove);
                       response.getWriter().write(jsObj.toString());
                       response.getWriter().flush();
                       response.getWriter().close();
                   }
               } catch (Exception ex) {
                   LOGGER.error("Exception: " + ex.getMessage());
               }
           } else {
               // Remove Failure
               LOGGER.info("Remove Respone Failing code::" + resCode);
               removeJSON.put("responseCodeOnRemove", resCode);
               //removeJSON.put("imageIdRemove", imagId);
               LOGGER.info("removeJSON::" + removeJSON.toString());
               response.getWriter().write(removeJSON.toString());
               response.getWriter().flush();
               response.getWriter().close();

           }
       } catch (Exception e) {
           LOGGER.error("Caught Exception**************removeSampleImageMethod---Controller",e);
       }
   }
   
   // Method to pass JSON Array
   public JSONObject populateJson(String orinNo, String imagId) {
       JSONObject jsonObj = new JSONObject();
       try {
    	    LOGGER.info("orinNo ---->"+orinNo);
           jsonObj.put(ImageConstants.IMAGE_ID, imagId);
           jsonObj.put(ImageConstants.PET_ID, orinNo);
         
       } catch (JSONException e) {
           
       }
       return jsonObj;

   }
   
   /**
    * Getting IPC details
    * @param request
    * @return
    */
   private ImageDetails getUserDetailsfromLogin(PortletRequest request)
   {
     if (request.getPortletSession().getAttribute("ImageDetailsOBJ") != null)
     {
       ImageDetails imageDetails= (ImageDetails)request.getPortletSession().getAttribute("ImageDetailsOBJ");
       return imageDetails;
     }
     return null;
   }
   
   
    
    /**
	 * Upload FTP images from upload images screen
	 * @param uploadImagesDTO
	 * @param vendorNumber
	 * @param styleNumber
	 * @param colorCode
	 * @return
	 */
	@SuppressWarnings({ "unused", "static-access" })
	private String UploadFTPImages(UploadImagesDTO uploadImagesDTO) {
		String imageType = "";
		boolean validImage = false;
		String ftpResult = "";
		String ftpUrl = uploadImagesDTO.getFtpUrl();
		String ftpPath = uploadImagesDTO.getFtpPath();
		String ftpUserId = uploadImagesDTO.getFtpUserId();
		String ftpPassword = uploadImagesDTO.getFtpPassword();
		String ftpImageName = uploadImagesDTO.getFtpFileName();
		if (ftpImageName != null && !ftpImageName.isEmpty()) {
			uploadImagesDTO.setUserUploadedFileName(ftpImageName);
			String[] fileExtn = ftpImageName.split("\\.");
			setImageDetails(uploadImagesDTO, fileExtn[(fileExtn.length - 1)]);
			FtpUtil ftp = new FtpUtil();
			// download image from FTP site
			ftpResult = ftp.downloadFTPImage(ftpUrl, ftpUserId, ftpPassword,
					ftpPath, ftpImageName, uploadImagesDTO.getImageName(),
					uploadImagesDTO.getVendorImageUploadDir());
			/*if (ftpResult.equals("success")) {
				if (vendorImageManager.saveImageData(uploadImagesDTO)) {
					ftpResult = "success";
				} else {
					ftpResult = "unableToSaveData";
				}
			} else {
				log.error("Unable to download the vendor image " + ftpResult);
			}*/
		}
		return ftpResult;
	}
   
	/**
	 * Set the imageName and Shot type to UploadImagesDTO
	 * 
	 * @param uploadImagesDTO
	 * @param fileExtn
	 */
	public void setImageDetails(UploadImagesDTO uploadImagesDTO, String fileExtn) {
		String vendorImageUploadDir = "";
		String RRDImageUploadedDir = "";
		try {
			LOGGER.info("setImageDetails 44 :");			
			
			 LOGGER.info("getVendorNumber  "+uploadImagesDTO.getVendorNumber());
			 LOGGER.info("getStyleNumber "+uploadImagesDTO.getStyleNumber());
			 LOGGER.info("getStyleNumber  "+uploadImagesDTO.getStyleNumber());
			 LOGGER.info("fileExtn "+fileExtn);
			 Random randomGenerator = new Random();
			 int randomInt = randomGenerator.nextInt(10000);
			     LOGGER.info("Generated : " + randomInt);
			
			String imageName = uploadImagesDTO.getVendorNumber() + "_"
					+ uploadImagesDTO.getStyleNumber() + "_"					
					+ uploadImagesDTO.getColorCode() + "_"
					+ randomInt + "." + fileExtn;
			
			LOGGER.info("setImageDetails 455 :"+imageName);
			uploadImagesDTO.setImageName(imageName.toUpperCase());
			LOGGER.info("setImageDetails 666 :");
		} catch (Exception e) {
			LOGGER.info("VendorImageUploadFormController.setImageDetails() error occured while setting the values" +e);
		}
		try {
			vendorImageUploadDir = imageRequestDelegate.getVendorImageUploadDir();
			uploadImagesDTO.setVendorImageUploadDir(vendorImageUploadDir);
			RRDImageUploadedDir = imageRequestDelegate.getRRDImageUploadedDir();
			uploadImagesDTO.setRRDImageUploadedDir(RRDImageUploadedDir);

		} catch (Exception e) {
			LOGGER.info("Unable to read the vendorImageUploadDir property from properties file");
		}
	}
    @ActionMapping(params = "action=workListDisplay")
    public void goToWorkListDisplay(ActionRequest request, ActionResponse response,
            final @ModelAttribute("fdForm") FileUploadForm fdForm, final BindingResult result, final Model model) {

        String orinNumber = request.getParameter("orinNumber");
        String pageNumber = request.getParameter("pageNumber");
        PageAnchorDetails pageAnchorDetails = new PageAnchorDetails();
        pageAnchorDetails.setOrinNumber(orinNumber);
        pageAnchorDetails.setPageNumber(pageNumber);
        response.setEvent(ImageConstants.EVENT_PAGINATION, pageAnchorDetails);
        LOGGER.info(request.getParameter("lockedPetId"));
    }
	 /**
     * Call for the file Upload VPI
     * @param request
     * @param response
     */
   @ActionMapping(params = "action=imageUploadAction")
    public void imageUploadAction(ActionRequest request, 
                  ActionResponse resourceResponse, 
                  final @ModelAttribute("fdForm") FileUploadForm fdForm,
                  final BindingResult result, 
                  final Model model) {
    			  String imageLocation = request.getParameter("imageLocation");
    			  String displaySwatch=request.getParameter("displaySwatch");
    			  
    			  Properties prop =PropertyLoader.getPropertyLoader(ImageConstants.LOAD_IMAGE_PROPERTY_FILE);
    			  String fileDir = prop.getProperty(ImageConstants.FILE_UPLOAD_PATH);
    			  //String fileDir = ImageConstants.FILE_UPLOAD_PATH;
    			  //String fileDir = "/tmp/";
    			  
    			  	String imageLocationType="";
    				String ftpResult="";
    				String uploadedSucess="";
    				String imageNameRender="";
    				UploadImagesDTO uploadImagesDTO = new UploadImagesDTO();    				
    				uploadImagesDTO.setVendorNumber(request.getParameter("supplierIdVPI"));
    				uploadImagesDTO.setStyleNumber(request.getParameter("vendorStyle#"));
    				uploadImagesDTO.setColorCode(request.getParameter("colorCode"));
    				if(request.getParameter("selectedColorOrin")!=null){
    				LOGGER.info("File111111111111111111" +request.getParameter("selectedColorOrin"));	
    				request.setAttribute("selectedColorOrin", request.getParameter("selectedColorOrin"));
    				request.setAttribute("selectedColorCode", request.getParameter("colorCode"));
    				uploadImagesDTO.setPetId(request.getParameter("selectedColorOrin"));
    				LOGGER.info("File2222222222222222222222222222");
    				}
    				String cName="Blue";        				
    				uploadImagesDTO.setColorName(cName); 
    				
    				
    			  if(imageLocation.equalsIgnoreCase("BML")){
	    				LOGGER.info("BML DISPLAY");
	    				CommonsMultipartFile multipartfile = fdForm.getFileData();
	    				long fileSize = multipartfile.getSize();
	    				try {
	    					
	    				    String responseMsg ="";			
	        				LOGGER.info("displayname 11 :");        				
	        				uploadImagesDTO.setUserUploadedFileName(multipartfile.getOriginalFilename());
	        				LOGGER.info("displayname 22 :");
	        				String[] fileExtn = uploadImagesDTO.getUserUploadedFileName().split("\\.");
	        				LOGGER.info("displayname 33 :");
	        				setImageDetails(uploadImagesDTO, fileExtn[(fileExtn.length - 1)]);
	        				LOGGER.info("displayname 44 :");
	        				InputStream is = null;
	        				OutputStream os = null;        				
	        				try {
	        					is = multipartfile.getInputStream();	        					
	        					String filepath=fileDir+ uploadImagesDTO.getImageName();
	        					
	        					//os = new FileOutputStream(filepath);
	        					
	        					LOGGER.info("filepath-->>>>" + filepath);
	        					//Setting Image Location
	        					uploadImagesDTO.setCdImageLocation(imageLocation);
	        					uploadImagesDTO.setImageType("");
	        					LOGGER.info(" uploadImagesDTO.getImageName() "+uploadImagesDTO.getImageName());
	        					imageNameRender= uploadImagesDTO.getImageName();
	        					
	        					/*OutputStream out = new FileOutputStream(filepath);
	        					byte[] b = new byte[2048];
	        					int length;
	        					while ((length = is.read(b)) != -1) {
	        						os.write(b, 0, length);
	        					}*/
	        					
	        					ImageDetails imageDetailsFromIPC = getUserDetailsfromLogin(request);
	        				     String updatedBy = "";
	        				     if(imageDetailsFromIPC.getUserData().getBelkUser()!= null){
	        				        updatedBy = imageDetailsFromIPC.getUserData().getBelkUser().getLanId();
	        				     }else if (imageDetailsFromIPC.getUserData().getVpUser()!= null){
	        				         updatedBy = imageDetailsFromIPC.getUserData().getVpUser().getUserEmailAddress();
	        				     }
	        					
	        					//TODO convert the DTO to JSON Object
	        					JSONArray jsonArray = new JSONArray();	        				     
	        				                     
	        				       try {         
	        				                       
	        				           JSONObject jsonUploadVPI = populateFileUploadJson(uploadImagesDTO,updatedBy);
	        				           jsonArray.put(jsonUploadVPI);
	        				           LOGGER.info("jsonArray --> "+jsonArray);         				         
	        				           responseMsg = callUploadVPIService(jsonArray);
	        				           LOGGER.info("json Object petId:: "+ jsonUploadVPI.getString("petId")+"responseMsg::" +responseMsg);
	        				       } catch (Exception e) {
	        				           LOGGER.info("inside catch for UploadImage ");
	        				           e.printStackTrace();
	        				       }
	        					//TODO Service Call needed to upload images	
	        				    if("New image id generated and image attributes successfully uploaded".trim().equalsIgnoreCase(responseMsg)){
	        				    	LOGGER.info("Image Upload Success in controller");
	        				    	uploadedSucess = "Y";
	        				    	os = new FileOutputStream(filepath);
	        				    	byte[] b = new byte[2048];
		        					int length;
		        					while ((length = is.read(b)) != -1) {
		        						os.write(b, 0, length);
		        					}
		        					//Closing opStream after Successful file upload
		        					os.close();
	        				    }else{
	        				    	LOGGER.info("Image Upload Failed in controller");
	        				    	uploadedSucess = "N";
	        				    }
	        					//uploadedSucess = "Y"; 					
	        				} catch (Exception e) {
	        					//uploadedSucess = "N";
	        					LOGGER.info("Unable to upload the local image" + e);
	        				}finally{
	        					try{
	        						is.close();
	        						//os.close();
	        					}catch(Exception e){
	        						LOGGER.info("Unable to close OutputStream objects" + e);
	        					}
	        				}	        				
	        				if (uploadedSucess=="Y"){
	        					uploadedSucess = "Y";
	        					LOGGER.info("Image uploaded successfully........");
	        				}else if(uploadedSucess =="N"){
	        					LOGGER.info("Error occured while saving the vendor image data to database");
	        					uploadedSucess = "N";	        					
	        				}	    					
	                  
	    				} catch (Exception e) {                  
	    					LOGGER.info("Exception:::" +e );
	    				}
    			  	}else{
    			  	//FTP BLock
    				uploadImagesDTO.setFtpUrl(request.getParameter("ftpUrl1"));
    				uploadImagesDTO.setFtpPath(request.getParameter("ftpPath"));
    				uploadImagesDTO.setFtpUserId(request.getParameter("ftpUserId"));
    				uploadImagesDTO.setFtpPassword(request.getParameter("ftpPassword1"));
    				uploadImagesDTO.setFtpFileName(request.getParameter("ftpFileName"));    				
    				ftpResult=UploadFTPImages(uploadImagesDTO);
    			}
    			  
    			   LOGGER.info("uploadedSucess:::1234" + uploadedSucess);
    			  PortletSession session = request.getPortletSession();
    			  session.setAttribute("uploadedSucess", uploadedSucess, PortletSession.PORTLET_SCOPE);
    			  session.setAttribute("imageName", imageNameRender, PortletSession.PORTLET_SCOPE);
    			  
    			  
    		}
   
   	
   public JSONObject populateFileUploadJson(UploadImagesDTO uploadImagesDTO,String updatedBy) {
       JSONObject jsonObj = new JSONObject();
       try {      
        jsonObj.put("imageLocation", uploadImagesDTO.getCdImageLocation());
        jsonObj.put("petId", uploadImagesDTO.getPetId());
        jsonObj.put("imageType", uploadImagesDTO.getImageType());       
        jsonObj.put("imageName", uploadImagesDTO.getImageName());
        jsonObj.put("createdBy", updatedBy);
       } catch (JSONException e) {

           e.printStackTrace();
       }
       LOGGER.info("jsonObj ---->"+jsonObj);      
       return jsonObj;

   }
  
    
   @ResourceMapping("getUploadVPIDetails")
   public ModelAndView getUploadVPIDetails(ResourceRequest request, ResourceResponse response){
   	LOGGER.info("getVendorVPIDetails ************.." );  
   	 
   	 ModelAndView  mv = null;
   	 try {
   		 if(null!=request.getParameter(ImageConstants.SELECTED_COLOR_ORIN)){
   			 LOGGER.info(" selected ORIN *******"+request.getParameter(ImageConstants.SELECTED_COLOR_ORIN));   	       
					   JSONArray jsonImageDtls = new JSONArray();															
					   jsonImageDtls = populateJsonVPI(request.getParameter(ImageConstants.SELECTED_COLOR_ORIN),(String)request.getPortletSession().getAttribute("userAttr"));
					   LOGGER.info("jsonImageDtls *************"+jsonImageDtls);					 				  
					   response.getWriter().write(jsonImageDtls.toString());
	                            
	                 
	             
   	        }
   		
			  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
   	
   	LOGGER.info("Exiting getVendorVPIDetails method" );   
   	 return mv;
   	
   }

    
    
    /**
     * Json for file upload service
     * @param OrinNo
     * @param roleToPass
     * @return
     */
    public JSONArray populateJsonVPI(String OrinNo,String roleToPass) {
    	 JSONObject jsonObj = null;
         JSONArray jsonArrayImageDtls = new JSONArray();
         LOGGER.info("Entering populateJsonVPI....Controller::");
         LOGGER.info("roleToPass----" +roleToPass);
         Properties prop =PropertyLoader.getPropertyLoader(ImageConstants.LOAD_IMAGE_PROPERTY_FILE);
		 
         String fileDir = prop.getProperty(ImageConstants.FILE_UPLOAD_PATH);
         //String fileDir = ImageConstants.FILE_UPLOAD_PATH;
         //String fileDir = "/tmp/";
		 try {
        	 ArrayList<SamleImageDetails> sampleImageLinkList = imageRequestDelegate.getSampleImageLinks(OrinNo);
        	if (sampleImageLinkList.size() > 0 && sampleImageLinkList !=null) {
        	for (SamleImageDetails item : sampleImageLinkList) {
        		String imageFilePath = fileDir+item.getImageName();
       		 LOGGER.info("populateJsonVPI imageFilePath ::::::::::: "+imageFilePath);
        	jsonObj = new JSONObject();
            jsonObj.put("imageID", item.getImageId());
            jsonObj.put("imageName", item.getImageName());
            jsonObj.put("imageLocation", item.getImageLocation());
            jsonObj.put("shotType", item.getImageShotType());
            jsonObj.put("linkStatus", item.getLinkStatus());
            jsonObj.put("imageStatus", item.getImageStatus());
            jsonObj.put("sampleId", item.getImageSampleId() );
            jsonObj.put("sampleReceived", item.getImageSamleReceived());
            jsonObj.put("silhouette", item.getImageSilhouette());
            jsonObj.put("turnInDate", item.getTiDate());
            jsonObj.put("sampleCordinatorNote", item.getSampleCoordinatorNote());
            jsonObj.put("action", "");
            jsonObj.put("imagefilepath", imageFilePath);
            jsonObj.put("role", roleToPass);
            jsonObj.put("rejectType", item.getRejectCode());
            jsonObj.put("rejectReason", item.getRejectReason());
            jsonObj.put("rejectionTimestamp", item.getRejectionTimestamp());
            
            jsonArrayImageDtls.put(jsonObj);
        	 }
        	}
        	
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (PEPServiceException e) {
			e.printStackTrace();
		} catch (PEPPersistencyException e) {
			e.printStackTrace();
		}
		LOGGER.info("Exiting populateJsonVPI....Controller::");
        return jsonArrayImageDtls;
    }

    /**
     * Event details from receive from worklistdisplay
     */
    @EventMapping
	public void handleEventRequest(EventRequest request, EventResponse response) throws Exception {
	  LOGGER.info("ImageRequestController:handleEventRequest ***** ");
	  Event event = request.getEvent();
	  if(event.getName()!=null ){    
	  if(event.getName().equals("ImageDetailsOBJ")){ 
    	LOGGER.info("ImageRequestController:handlingPagination:Enter 2 "); 
    	ImageDetails imageDetails= (ImageDetails) event.getValue(); 
    	LOGGER.info("Inside handle handleEventRequest...imagePetDetails...getOrinNumber" + imageDetails.getOrinNumber());
    	LOGGER.info("Inside handle handleEventRequest...imagePetDetails...getVpUser" + imageDetails.getVpUser());
    	LOGGER.info("Inside handle handleEventRequest...imagePetDetails...getUserData" + imageDetails.getUserData());
    	LOGGER.info("Inside handle handleEventRequest...imagePetDetails...getBelkUser" + imageDetails.getBelkUser());
    	request.getPortletSession().setAttribute("ImageDetailsOBJ",imageDetails);  
      }
  	}
}



	

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@ResourceMapping("submitOrRejectAjaxUrl")
	public void getSubmitorRejectStatus(ResourceRequest request,
			ResourceResponse response){
		
		LOGGER.info("Entering getSubmitorRejectStatus...Controller--in");		
		@SuppressWarnings("unused")
		String passImageStatusToService = "";
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonStyle1 = new JSONObject();
		ImageDetails imageDetailsFromIPC = getUserDetailsfromLogin(request);
        String updatedBy = "";
        if(imageDetailsFromIPC.getUserData().getBelkUser()!= null){
            updatedBy = imageDetailsFromIPC.getUserData().getBelkUser().getLanId();
       }else if (imageDetailsFromIPC.getUserData().getVpUser()!= null){
            updatedBy = imageDetailsFromIPC.getUserData().getVpUser().getUserEmailAddress();
	    }
		LOGGER.info("Entering getSubmitorRejectStatus...Controller--in");
		
		String orinNumber = request.getParameter("selectedColorOrin");
		String imageId = request.getParameter("imageId");
		String imageStatus = request.getParameter("imageStatus");
		String statusParam = request.getParameter("statusparam");
		String shotTypeOnSubmit = request.getParameter("shotTypeValueOnSubmit");
		String rejectType = request.getParameter("rejectType");
		String rejectReason = request.getParameter("rejectReason");
		
		String responseMsg = "";
		String responseMsg1 = "";
		String shotTypeResponseMsg = "";
		JSONArray jsonArray = new JSONArray();
		JSONArray jsonArray1 = new JSONArray();
		JSONArray shotTypeJsonArray = new JSONArray();
		
		LOGGER.info("orinNumbersss::" + orinNumber +"\t"+"::imageIdss::" + imageId +"\t"+ 
				"::imageStatusss::" + imageStatus+"\t"+ "::statusParam::" +statusParam+"\t"+ "::shotTypeOnSubmit::" +shotTypeOnSubmit);
		
		if("Submit".equalsIgnoreCase(statusParam)){
			LOGGER.info("status param submit::");
			if("Initiated".equalsIgnoreCase(imageStatus) || "Ready_For_Review".equalsIgnoreCase(imageStatus)|| imageStatus.contains("Ready")||"ReadyForReview".equalsIgnoreCase(imageStatus)){//get value from db
				LOGGER.info("Before Submit image status Initiated::");
				passImageStatusToService = "Ready_For_Review";
			}
		}else if("Reject".equalsIgnoreCase(statusParam)){
			LOGGER.info("status param reject::");
			if("Initiated".equalsIgnoreCase(imageStatus) || "Completed".equalsIgnoreCase(imageStatus)||"Ready_For_Review".equalsIgnoreCase(imageStatus)||imageStatus.contains("Ready")||"ReadyForReview".equalsIgnoreCase(imageStatus)){
				LOGGER.info("Before Reject image status check*******::");
				passImageStatusToService = "Rejected";//For Reject Call
			}
		}
		try {          
            
	           JSONObject jsonStyle = populateJsonForSubmitOrReject(orinNumber.trim(),imageId,passImageStatusToService,updatedBy, rejectType, rejectReason);
	           jsonArray.put(jsonStyle);
	           LOGGER.info("json Object petId "+ jsonStyle.getString("petId"));
	           responseMsg = callSubmitOrRejectService(jsonArray);
	           LOGGER.info("responseMsg_code----controller-------"+ responseMsg);
	           
	           
	           //Response Msg handle after submitReject Success
	           String [] resCode = responseMsg.split("_");
	           String resMsg = resCode[0];
	           String resCodeRet = resCode[1];
	           LOGGER.info("resMsg::"+ resMsg +"\t"+"resCodeRet::" +resCodeRet); 
	           
	           //Call shotType Service on Submit
	           if("100".equalsIgnoreCase(resCodeRet) && "Ready_For_Review".equalsIgnoreCase(passImageStatusToService) && null != imageDetailsFromIPC.getUserData().getBelkUser()){
	        	   LOGGER.info("-------------Before Calling shotType on submit----------------");
	        	   JSONObject jsonShotType = populateSaveInageShotTypeJson(orinNumber.trim(),imageId,shotTypeOnSubmit,updatedBy);
	        	   shotTypeJsonArray.put(jsonShotType);
	        	   
	        	   try{
	        		   shotTypeResponseMsg = callSaveImageShotTypeService(shotTypeJsonArray);
	                   LOGGER.info("::::responseMsg_code Controller save shotType on Submit::" +shotTypeResponseMsg);
	                   
	                   String[] responseCodeMessageonShotTypeSubmit = shotTypeResponseMsg.split("_");
	                   String resMessageShotType = responseCodeMessageonShotTypeSubmit[0];
	                   String resCodeShotType = responseCodeMessageonShotTypeSubmit[1];
	                   
	                   LOGGER.info("::resMessageShotType on Submit::" + resMessageShotType +"-----"+ "::resCodeShotType on Submit::" +resCodeShotType);               
	    		   }catch(Exception ex){
	    			   LOGGER.info("---inside catch for service call shotType on Submit---Controller");
	                   ex.printStackTrace();
	    		   }
	    		   LOGGER.info("End Block of shotType on Submit"); 
	           }
 			  	
 			  	jsonObj.put("resCodeRet", resCodeRet);
 			  	jsonObj.put("statusParamRet", statusParam);
 			  	jsonObj.put("imageIdRet", request.getParameter("imageId"));
 			  	//response.getWriter().write(jsonObj.toString());	
 			  	
 			  	LOGGER.info("write success 123 json::" + jsonObj.getString("imageIdRet"));
 			  	/*
 			  	 * Updating Image management Image Status 
 			  	 */
 			  	ArrayList<SamleImageDetails> sampleImageLinkList = imageRequestDelegate.getSampleImageLinks(orinNumber);
 			  	boolean rejected_status = false;
 			  	boolean review_status = false;
 			  	boolean initiated_status = false;
 			  	boolean completed_status = false;
 			  	
 			  	for(int i=0 ;i < sampleImageLinkList.size();i++){
 			  		SamleImageDetails sampleImageDetailsBean = sampleImageLinkList.get(i);
 			  		if(sampleImageDetailsBean.getImageStatus().contains("Rejected")){
 			  			rejected_status = true;
 			  		}
 			  		if(sampleImageDetailsBean.getImageStatus().equalsIgnoreCase("Ready_For_Review")||sampleImageDetailsBean.getImageStatus().equalsIgnoreCase("ReadyForReview") || sampleImageDetailsBean.getImageStatus().contains("Ready_")){
 			  			review_status = true;
 			  		}
 			  		if(sampleImageDetailsBean.getImageStatus().equalsIgnoreCase("Initiated")){
 			  			initiated_status = true;
 			  		}
 			  		if(sampleImageDetailsBean.getImageStatus().equalsIgnoreCase("Completed")){
 			  			completed_status = true;
 			  		}
 			  		
 			  		
 			  	}
 			  	LOGGER.info("Rejected_status = " + rejected_status + " Review Status = "+ review_status + " Intiated Status = " + initiated_status + " Completed Status " + completed_status);
 			  	
 			  	if("100".equalsIgnoreCase(resCodeRet)){
 			  		LOGGER.info("----------SubmitOrReject==='100' before calling imageStatus----------");
 			  		if(rejected_status){
 			  			LOGGER.info("Call Service rejected");
 			  			jsonStyle1 = populateJsonForImageManagement(orinNumber, updatedBy, "07");
 			  			jsonArray1.put(jsonStyle1);
 			  			responseMsg1 = callApproveActionService(jsonArray1);
 			  			LOGGER.info("--imageStatus response update in submit for reject---" + responseMsg1);
 			  		}
 			  		else if(review_status){
 			  			LOGGER.info("Call Service review");
 			  			jsonStyle1 = populateJsonForImageManagement(orinNumber, updatedBy, "08");
 			  			jsonArray1.put(jsonStyle1);
 			  			responseMsg1 = callApproveActionService(jsonArray1);
 			  			LOGGER.info("--imageStatus response update in submit for readyforReview---" + responseMsg1);
 			  		}
 			  		else if(initiated_status){
 			  			LOGGER.info("Call Service initiated");
 			  			jsonStyle1 = populateJsonForImageManagement(orinNumber, updatedBy, "01");
 			  			jsonArray1.put(jsonStyle1);
 			  			responseMsg1 = callApproveActionService(jsonArray1);
 			  			LOGGER.info("--imageStatus response update in submit for initiated---" + responseMsg1);
 			  		}
 			  		else if(completed_status){
 			  			LOGGER.info("Call Service completed");
 			  			jsonStyle1 = populateJsonForImageManagement(orinNumber, updatedBy, "02");
 			  			jsonArray1.put(jsonStyle1);
 			  			responseMsg1 = callApproveActionService(jsonArray1);
 			  			LOGGER.info("--imageStatus response update in submit for completed---" + responseMsg1);
 			  		}
 			  		else{
 			  			LOGGER.info("Do Nothing");
 			  		}
 			  	}
 			  	if("Image status update is successful".equalsIgnoreCase(responseMsg1)){
						LOGGER.info(" ---Service Response is Success on SubmitOrReject--- ");
						String responseCodeOnSubmit = "100";
						//jsonObj.remove(resCodeRet);					
						jsonObj.put("responseCodeOnSubmit", responseCodeOnSubmit);	
					}
 			  	response.getWriter().write(jsonObj.toString());					
				response.getWriter().flush();
				response.getWriter().close(); 
	       } catch (Exception e) {
	           LOGGER.info("Caught Exception getSubmitorRejectStatus controller******************");
	           e.printStackTrace();
	       }
		LOGGER.info("Exiting getSubmitorRejectStatus...Controller--out");
	}
	
	
	   /**
	    * method for passing json to submit or reject
	    * @param orinNo
	    * @param imagId
	    * @param imageStatus
	    * @param updatedBy
	    * @return
	    */
	   public JSONObject populateJsonForSubmitOrReject(String orinNo, String imagId,String imageStatus,String updatedBy, String rejectType, String rejectReason){
	       JSONObject jsonObj = new JSONObject();
	       try {
	    	   LOGGER.info("populateJsonForSubmitOrReject Enter.....Controller---->");
	    	   LOGGER.info("orinNo ---->"+orinNo);
	    	   jsonObj.put(ImageConstants.PET_ID, orinNo);
	    	   jsonObj.put(ImageConstants.IMAGE_ID, imagId);
	    	   jsonObj.put(ImageConstants.SAVE_IMAGE_STATUS, imageStatus); 
	    	   jsonObj.put(ImageConstants.UPDATEDBY, updatedBy); 
	    	   
	    	   if(StringUtils.isNotBlank(rejectType)){
	    		   jsonObj.put(ImageConstants.REJECT_TYPE, rejectType);
	    		   jsonObj.put(ImageConstants.REJECT_REASON ,StringUtils.substringAfter(rejectReason, " "));
	    	   }
	    	   
	           LOGGER.info("jsonObj populateJsonForSubmitOrReject************ ---->"+jsonObj);
	       } catch (JSONException e) {
	    	   LOGGER.info("Caught**** Exception...Controller");
	           e.printStackTrace();
	       }
	       LOGGER.info("populateJsonForSubmitOrReject Exit...Controller---->");
	       return jsonObj;

	   }
	   
	   /**
	    * This method is used for saving image shotType on ajax call
	    * @param request
	    * @param response
	    * @param model
	    */
	   @ResourceMapping("saveImageShotType")
	   public void saveImageShotType(ResourceRequest request,ResourceResponse response,Model model){
		   LOGGER.info("Entering.....saveImageShotTypeDetails---Controller");
		   
		   JSONObject jsonObject = new JSONObject();
		   ImageDetails imageDetailsFromIPC = getUserDetailsfromLogin(request);
	        String updatedBy = "";
	        if(imageDetailsFromIPC.getUserData().getBelkUser()!= null){
	            updatedBy = imageDetailsFromIPC.getUserData().getBelkUser().getLanId();
	       }else if (imageDetailsFromIPC.getUserData().getVpUser()!= null){
	            updatedBy = imageDetailsFromIPC.getUserData().getVpUser().getUserEmailAddress();
		    }
		   String responseMsg ="";
		   JSONArray jsonArray = new JSONArray();
		   String orinNumber = "";
		   String passImgid = "";
		   String passShotType = "";
		   String selectedArray = request.getParameter("selectedOrinImageShotArray");	   
   
		  
		   String[] selectedOrinImageShotArray = selectedArray.split(";");
		   
		   LOGGER.info("selectedOrinImageShotArray::" + selectedOrinImageShotArray.length);
		   
		   
		   for(int i=0; i<selectedOrinImageShotArray.length; i++){
			   
			   orinNumber = "";
			   passImgid = "";
			   passShotType = "";
			   //LOGGER.info("selectedOrinImageShotArray[i]" +selectedOrinImageShotArray[i]);			   
			   String[] arrayInnerSelectedOrin = selectedOrinImageShotArray[i].split("_");
			   
			   //LOGGER.info("arrayInnerSelectedOrin........" +arrayInnerSelectedOrin.length);
			   
			   	
			   orinNumber = arrayInnerSelectedOrin[0].replace("//s", "");
			   passImgid  = arrayInnerSelectedOrin[1].replace("//s", "");
			   passShotType = arrayInnerSelectedOrin[2].replace("//s", "");
			   
			   LOGGER.info("Changed ShotType-----"+"orinNumber::" + orinNumber +"\t"+"passImgid::"+passImgid+"\t"+"passShotType::"+ passShotType);
			   
			   JSONObject jsonStyle = populateSaveInageShotTypeJson(orinNumber.trim(),passImgid,passShotType,updatedBy);
			   jsonArray.put(jsonStyle);
		   }
		   try{
			   responseMsg = callSaveImageShotTypeService(jsonArray);
               LOGGER.info("responseMsg_code Controller Activate::" +responseMsg);
               
               String[] responseCodeMessage = responseMsg.split("_");
               String resMessage = responseCodeMessage[0];
               String resCode = responseCodeMessage[1];
               
               LOGGER.info("resMessage::" + resMessage +"-----"+ "resCode::" +resCode);               
               jsonObject.put("resCode", resCode);
               response.getWriter().write(jsonObject.toString());
               LOGGER.info("Response Code written::" +jsonObject.toString());               
		   }catch(Exception ex){
			   LOGGER.info("inside catch for service call---......Controller");
               ex.printStackTrace();
		   }
	
	 }
	   
	   
	   /**
	    * populating shotType ajax json
	    * @param petId
	    * @param imageId
	    * @param shotType
	    * @param updatedBy
	    * @return
	    */
	   public JSONObject populateSaveInageShotTypeJson(String petId,String imageId,String shotType,String updatedBy) {
	        JSONObject jsonObj = new JSONObject();
	        LOGGER.info("------- in parsing the jsonObj111111111");
	        try {          
	            jsonObj.put("petId",petId);
	            jsonObj.put("imageId",imageId);
	            jsonObj.put("shotType",shotType);
	            jsonObj.put("updatedBy",updatedBy);
	        } catch (JSONException e) {
	            LOGGER.info("Exeception in parsing the jsonObj111111111");
	            e.printStackTrace();
	        }
	        return jsonObj;
	     }
	   
	   	/**
	   	 * Call for approveAction Image
	   	 * @param request
	   	 * @param response
	   	 */
		@ResourceMapping("imageApproveAction")
		public void imageApproveAction(ResourceRequest request,ResourceResponse response) {
			LOGGER.info("entering imageApproveAction method");
			
			JSONObject jObj = new JSONObject();
			String responseCode = "";
			
			ImageDetails imageDetailsFromIPC = getUserDetailsfromLogin(request);
	        String updatedBy = "";
	        if(imageDetailsFromIPC.getUserData().getBelkUser()!= null){
	            updatedBy = imageDetailsFromIPC.getUserData().getBelkUser().getLanId();
	       }else if (imageDetailsFromIPC.getUserData().getVpUser()!= null){
	            updatedBy = imageDetailsFromIPC.getUserData().getVpUser().getUserEmailAddress();
		    }
	        LOGGER.info("Status Value updatedBy:" + updatedBy);
			String responseMsg ="";
			String orinId = request.getParameter("approveOrRejectOrinNum");		
			LOGGER.info(" orinId::"+ orinId );

			JSONArray jsonArray = new JSONArray();		
			try {
				JSONObject jsonStyle = populateJsonOnApproveAction(
						orinId.trim(),  updatedBy);
				jsonArray.put(jsonStyle);
				LOGGER.info(" final object -->"+jsonArray);
				responseMsg = callApproveActionService(jsonArray);
				LOGGER.info("---Service Response on Approve---" + responseMsg);
				//Logic for Success Response
				if("Image status update is successful".equalsIgnoreCase(responseMsg)){
					LOGGER.info(" ---Service Response is Success on Approve--- ");
					responseCode = "100";
					jObj.put("responseCode", responseCode);
					response.getWriter().write(jObj.toString());
					response.getWriter().flush();
					response.getWriter().close();
				}
			} catch (Exception e) {
				LOGGER.info("inside catch for imageApproveAction() method ");
				e.printStackTrace();
			}
			
		}
		
		/**
		 * method for populate json for approve
		 * @param orinNo
		 * @param updatedBy
		 * @return
		 */
		public JSONObject populateJsonOnApproveAction(String orinNo,String updatedBy) {
			JSONObject jsonObj = new JSONObject();
			try {
				LOGGER.info("orinNo ---->" + orinNo);	
				jsonObj.put(ImageConstants.PET_ID, orinNo);			
				jsonObj.put(ImageConstants.IMAGE_STATUS, ImageConstants.IMAGE_STATUS_COMPLETED);
				jsonObj.put(ImageConstants.UPDATEDBY, updatedBy);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonObj;

		}
		
		//Service method for Approve or Reject Action
		public JSONObject populateJsonForImageManagement(String orinNo,String updatedBy, String imageStatus) {
			JSONObject jsonObj = new JSONObject();
			try {
				LOGGER.info("orinNo in Approve ---->" + orinNo);	
				jsonObj.put(ImageConstants.PET_ID, orinNo);			
				jsonObj.put(ImageConstants.IMAGE_STATUS, imageStatus);
				jsonObj.put(ImageConstants.UPDATEDBY, updatedBy);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonObj;

		}
		
		
		
		// Service Call for Approve  Action Start
		private String callApproveActionService(JSONArray imageInfo)
				throws Exception, PEPFetchException {

			String responseMsg = null;
			responseMsg = imageRequestDelegate.callApproveorRejectActionService(imageInfo);
			return responseMsg;

		}
		
        /**
         * This method will insert record into IMAGE_SOFT_DELETE table. Separate
         * batch job will read from this table and physically delete the images
         * then.
         * 
         * @param orin
         * @param deletedBy
         * @param imageIds
         * @param imageNames
         * @return
         * @throws Exception
         * @throws PEPFetchException
         */
        private boolean callDeleteImage(String orin, String deletedBy, String[] imageIds, String[] imageNames)
                throws Exception, PEPFetchException {
            return imageRequestDelegate.insertImageDelete(orin, deletedBy, imageIds, imageNames);
        }
		
		/**
		 * This method is used to delete the file physically
		 * @param fileName
		 * @return
		 * @throws IOException
		 * @throws FileNotFoundException
		 */
		public static boolean fileDelete(String fileName) throws IOException,
		FileNotFoundException {
			LOGGER.info("Entering....fileDelete");
			boolean fileDelete = false;
			File file = new File(fileName);
			if (file.exists()) {
				LOGGER.info("Exist****");
				if (file.delete()) {
					LOGGER.info("File Deleted");
					fileDelete = true;
				}
			} else {
				LOGGER.info("File not Deleted");
				fileDelete = false;
			}
			LOGGER.info("Exiting....fileDelete");
			return fileDelete;
		}
		
		/**
		 * Method for locking pet
		 * @param request
		 * @param response
		 */
		@ResourceMapping("releseLockedPet")
        public void releseLockedPet(ResourceRequest request, ResourceResponse response){
		   	LOGGER.info("releseLockedPet ************.." ); 		 
		   	String loggedInUser= request.getParameter("loggedInUser");   	
			String lockedPet= request.getParameter("lockedPet"); 
			String pepFunction = request.getParameter("pepFunction"); 	
			
			
		 	LOGGER.info("releseLockedPet lockedPet IMAGE REQUEST CONTROLLER ************.." +lockedPet);
		 	try {
				 imageRequestDelegate.releseLockedPet(lockedPet,loggedInUser,pepFunction);
				 if (request.getPortletSession().getAttribute("ImageDetailsOBJ") != null)
			     {					 
					
						LOGGER.info("session value here updated ." +request.getPortletSession().getAttribute("formSessionKey"));
						
					/*if((request.getPortletSession().getAttribute("formSessionKey")!=null && ((String)request.getPortletSession().getAttribute("formSessionKey")).contains(loggedInUser))){
						 request.getPortletSession().invalidate();	
						 request.getPortletSession().setAttribute("internalUser","afusyg6");
					}*/
						
				
			     } 
					
			} catch (PEPPersistencyException e1) {
				e1.printStackTrace();
			}
		   	LOGGER.info("Exiting getVendorVPIDetails method" );		   	
		   }
	
		@ResourceMapping("downloadImageFile")
		public void readFile(ResourceRequest request, ResourceResponse response){
			LOGGER.info("Entering readFile method" );
			String filePath = request.getParameter("filePath");
			String imageName = request.getParameter("imageName");
			LOGGER.info(" readFile method: "+filePath );
			boolean isImageValid = false ;
			try {
				
				File tempFile = new File(filePath);
				LOGGER.info(" readFile tempFile.exists(): "+tempFile.exists() );
				String  mimeType = "application/octet-stream";
			        if(null != filePath && filePath.trim().length() > 0){
			        	if(filePath.toLowerCase().endsWith(".jpeg")){
			        		mimeType = "image/jpeg";
			        	}else if(filePath.toLowerCase().endsWith(".tiff") || filePath.toLowerCase().endsWith(".tif")){
			        		mimeType = "image/tiff";
			        	}else if(filePath.toLowerCase().endsWith(".psd")){
			        		mimeType = "application/octet-stream";
			        	}else if(filePath.toLowerCase().endsWith(".eps")){
			        		mimeType = "application/postscript";
			        	}else if(filePath.toLowerCase().endsWith(".jpg")){
			        		mimeType = "image/jpg";
			        	}
			        	
			        } 
			        
			        long length = tempFile.length();
			        LOGGER.info("tempFile.length() method" +length);	
			        if(length > 0){
			        	isImageValid = true;
			        }
			    	LOGGER.info(" readFile method: "+filePath );
			    	LOGGER.info(" readFile method mimeType: "+mimeType+ "  isImageValid:"+isImageValid);
				if(isImageValid && tempFile.exists()){
					response.setContentType(mimeType);
			        //response.addProperty("Content-disposition", "attachment; filename="+imageName);
			        
					response.setContentLength((int) length);
					 FileInputStream inStream = new FileInputStream(tempFile);
					 OutputStream outStream = response.getPortletOutputStream();
			         
				        byte[] buffer = new byte[4096];
				        int bytesRead = -1;
				         
				        while ((bytesRead = inStream.read(buffer)) != -1) {
				            outStream.write(buffer, 0, bytesRead);
				        }
				         
				        inStream.close();
				        outStream.close(); 
				}else{
					String fileNotFoundmsg="<h1>The Requested Image could not be found</h1>";
					response.setContentLength((int) fileNotFoundmsg.length());
					OutputStream outStream = response.getPortletOutputStream();
					outStream.write(fileNotFoundmsg.getBytes());
					
				}
			} catch (Exception e) {
				 LOGGER.info("Exception readFile method" +e);	
				e.printStackTrace();
			}LOGGER.info("Exiting readFile method" );	
		}
		/**
		 * @param request
		 * @param response
		 * @param imageDetailsFromIPC
		 * @return
		 * @throws PEPServiceException
		 * @throws PEPPersistencyException
		 */
		private ModelAndView getGroupingImageDetails(RenderRequest request, 
	    		RenderResponse response ,ImageDetails imageDetailsFromIPC ) throws PEPServiceException, PEPPersistencyException{
			ModelAndView mv = new ModelAndView();
			String roleNameFromIPC = null;  
	        String groupingId = null;
	        String petStatus = null ;      
	        String [] imageWithPetStatusArray = null;
	        List<ImageRejectReason> allImageRejectReasons = new ArrayList<ImageRejectReason>();
			if(null != imageDetailsFromIPC){
			 try {
	    		String loggedInUser = "";
	    		LOGGER.info(" GroupingId  in getGroupingImageDetails method  -" + imageDetailsFromIPC.getOrinNumber());	
	    		loggedInUser = groupingImageHelper.getLoggedInUserName(imageDetailsFromIPC);			  
	    		LOGGER.info("loggedInUser:::::::::" +loggedInUser);
	    		if(imageDetailsFromIPC.getOrinNumber()!=null){
	    			groupingId = imageDetailsFromIPC.getOrinNumber();
	    		}			
				imageWithPetStatusArray = imageDetailsFromIPC.getImageStatus().split("-");
				if(null != imageWithPetStatusArray){				
					petStatus = imageWithPetStatusArray[1];				
				}	
			 roleNameFromIPC = imageDetailsFromIPC.getUserData().getRoleName();
			 if("Deactivated".equalsIgnoreCase(petStatus)){
	    		request.setAttribute("userAttr", "readonly");    		
	    	}else{    		
	    		request.setAttribute("userAttr", roleNameFromIPC);
	    	}	
	    	request.setAttribute("petStatus", petStatus);    
	    	PortletSession session = request.getPortletSession();
	    	if("Deactivated".equalsIgnoreCase(petStatus)){    	
	    		session.setAttribute("userAttr", "readonly", PortletSession.PORTLET_SCOPE);    		
	    	}else{    		
	    		session.setAttribute("userAttr", roleNameFromIPC, PortletSession.PORTLET_SCOPE);
	    	}        
	    	ArrayList<StyleInfoDetails> styleInfoList = null;
	    	ArrayList<ImageProductDetails> imageProductInfoList = null ;    	
	    	ArrayList<PepDetailsHistory> pepHistoryList  = null ; 
				styleInfoList = imageRequestDelegate.getGroupingInfoDetails(groupingId);
				imageProductInfoList = imageRequestDelegate.getGroupingDetails(groupingId);	       
		        pepHistoryList = imageRequestDelegate.getGroupingHistoryDetails(groupingId);	    	
		        List<ImageLinkVO> imageLinkVOList = imageRequestDelegate.getGroupingScene7ImageLinks(groupingId);
		        allImageRejectReasons = imageRequestDelegate.getImageRejectReasons();
		        ImageForm imageForm = new ImageForm();
		        String formSessionKey = request.getPortletSession().getId() + loggedInUser;
		        request.getPortletSession().setAttribute("formSessionKey", formSessionKey);
		        mv = new ModelAndView(ImageConstants.MODELVIEW_SUCCESS_GROUPING);
		        
	            if (styleInfoList !=null && styleInfoList.size() > 0 ){   
	            	mv.addObject(ImageConstants.IMAGE_OVERALL_STATUS, styleInfoList.get(0).getGroupImageStatus());
	                imageForm.setUsername(loggedInUser);
	                imageForm.setStyleInfo(styleInfoList);                   
	            }
	            if (imageProductInfoList !=null && imageProductInfoList.size() > 0 ) {                
	                imageForm.setImageProductInfo(imageProductInfoList);                   
	            }                    
	            if (pepHistoryList !=null && pepHistoryList.size() > 0){                   
	                imageForm.setPepHistoryList(pepHistoryList);                   
	            }
	            if (imageLinkVOList !=null && imageLinkVOList.size() > 0){                   
	                imageForm.setImageLinkVOList(imageLinkVOList);                   
	            }
                if (allImageRejectReasons.size() > 0 && allImageRejectReasons !=null) {
                    imageForm.setAllImageRejectReasons(allImageRejectReasons);
                }
	                      
	            Properties prop =PropertyLoader.getPropertyLoader(ImageConstants.LOAD_IMAGE_PROPERTY_FILE);
	  		    String fileDir = prop.getProperty(ImageConstants.FILE_UPLOAD_PATH);         
				String tempStatus  =  (String) request.getPortletSession().getAttribute("uploadedSucess");
				String imageName  =  (String) request.getPortletSession().getAttribute("imageName");
				 ArrayList<SamleImageDetails> sampleImageLinkList = imageRequestDelegate.getGroupingSampleImageLinks(groupingId);
				 if (sampleImageLinkList !=null && sampleImageLinkList.size() > 0 ) {				 
					 JSONObject  groupVPILinks=groupingImageHelper.populateJsonGroupingVPI(fileDir,sampleImageLinkList);
					  sampleImageLinkList.get(0).setImageUrl(fileDir+sampleImageLinkList.get(0).getImageName());
					 	imageForm.setSampleImageDetailList(sampleImageLinkList);
						mv.addObject("groupVPILinks", groupVPILinks);
						mv.addObject("uploadImgeId", sampleImageLinkList.get(0).getImageId());					 	
					 	mv.addObject("imageCount", sampleImageLinkList.size() );
					 	mv.addObject(ImageConstants.IMAGE_OVERALL_STATUS, sampleImageLinkList.get(0).getImageStatus());	
					 	
				 }			
				mv.addObject(ImageConstants.IMAGE_INFO_DETAILS, imageForm);	          
	     	    mv.addObject("uploadSuccess", tempStatus );
	     	    mv.addObject("imageName", imageName );
	     	    request.getPortletSession().removeAttribute("uploadedSucess");      	  
	     	    request.getPortletSession().setAttribute(formSessionKey, imageForm);
	     	    mv.addObject("imageForm", (ImageForm)request.getPortletSession().getAttribute(formSessionKey));	       
				 } catch (PEPServiceException e) {
					 LOGGER.error("PEPServiceException ",e);				
				} catch (PEPPersistencyException e) {
					 LOGGER.info(" PEPPersistencyException  ",e);				
				}  
	    	   }
			
			return mv;
			
		}
		/**
		 * This method is invoked while uploading the grouping images.
		 * @param request
		 * @param resourceResponse
		 * @param fdForm
		 * @param result
		 * @param model
		 */
		@ActionMapping(params = "action=groupingImgUploadAction")
		    public void groupingImgUploadAction(ActionRequest request, ActionResponse resourceResponse, 
		                  final @ModelAttribute("fdForm") FileUploadForm fdForm,
		                  final BindingResult result,final Model model) {				 
						  String responseMsg ="";
					      String uploadedSucess="";
					      String imageNameRender="";	
			  			  try {		
			  			  Properties prop =PropertyLoader.getPropertyLoader(ImageConstants.LOAD_IMAGE_PROPERTY_FILE);
			  			  ImageDetails imageDetailsFromIPC = getUserDetailsfromLogin(request);				
			  			  String updatedBy = groupingImageHelper.getLoggedInUserName(imageDetailsFromIPC);			  
						  String imageLocation = request.getParameter("imageLocation");
			  			  String fileDir = prop.getProperty(ImageConstants.FILE_UPLOAD_PATH);  				
			  			  UploadImagesDTO uploadImagesDTO = new UploadImagesDTO();      			
			  			  uploadImagesDTO.setPetId(request.getParameter("groupingIdSel")); 
			  			  uploadImagesDTO.setGroupingType(request.getParameter("groupType"));  				
		  			      if(imageLocation.equalsIgnoreCase("BML")){
			    				LOGGER.info(" imageLocation "+imageLocation);
			    				CommonsMultipartFile multipartfile = fdForm.getFileData();    						
			    					LOGGER.info(" multipartfile.getOriginalFilename() "+multipartfile.getOriginalFilename());
			        				uploadImagesDTO.setUserUploadedFileName(multipartfile.getOriginalFilename());	        				        			
			        				setGroupingImageDetails(uploadImagesDTO);	        			
			        				InputStream is = null;
			        				OutputStream os = null;        				
			        				try {
			        					is = multipartfile.getInputStream();	        					
			        					String filepath=fileDir+ uploadImagesDTO.getImageName();	        					
			        					uploadImagesDTO.setCdImageLocation(imageLocation);
			        					uploadImagesDTO.setImageType("");
			        					LOGGER.info(" uploadImagesDTO.getImageName() "+uploadImagesDTO.getImageName());
			        					imageNameRender= uploadImagesDTO.getImageName();	        				
			        					JSONArray jsonArray = new JSONArray();
			        					JSONObject jsonMapObject = new JSONObject();        
			        					jsonMapObject.put(ImageConstants.GROUP_ID,uploadImagesDTO.getPetId());
			        					jsonMapObject.put(ImageConstants.GROUP_TYPE,uploadImagesDTO.getGroupingType());
			        					jsonMapObject.put(ImageConstants.CREATEDBY,updatedBy);
			        					try {         				                       
		        				           JSONObject jsonUploadVPI = groupingImageHelper.populateGroupingImgUploadJsonObj(uploadImagesDTO,updatedBy);
		        				           jsonArray.put(jsonUploadVPI);
		        				           jsonMapObject.put(ImageConstants.GROUPING_IMAGE_UPLOAD_LIST, jsonArray);
		        				           responseMsg = callGroupingImageUploadService(jsonMapObject);
			        					 } catch (Exception e) {
		        				           LOGGER.info("inside catch for UploadImage ",e);
		        				           e.printStackTrace();
		        				          }
			        				    if(ImageConstants.RESPONSE_SUCCESS_MESSAGE.equalsIgnoreCase(responseMsg)){
			        				    	uploadedSucess = "Y";
			        				    	os = new FileOutputStream(filepath);
			        				    	byte[] b = new byte[2048];
				        					int length;
				        					while ((length = is.read(b)) != -1) {
				        						os.write(b, 0, length);
				        					}
				        					//Closing opStream after Successful file upload
				        					os.close();
			        				    }else{
			        				    	LOGGER.info("Image Upload Failed in controller");
			        				    	uploadedSucess = "N";
			        				    }	        								
			        				} catch (Exception e) {	        				
			        					LOGGER.info("Unable to upload the local image", e);
			        				}finally{
			        					try{
			        						is.close();	        						
			        					}catch(Exception e){
			        						LOGGER.info("Unable to close OutputStream objects", e);
			        					}
			        				}	        				
			        				if (uploadedSucess=="Y"){
			        					uploadedSucess = "Y";
			        					LOGGER.info("Image uploaded successfully........");
			        				}else if(uploadedSucess =="N"){
			        					LOGGER.info("Error occured while saving the vendor image data to database");
			        					uploadedSucess = "N";	        					
			        				}
		  			  	  }
		  			      LOGGER.info("uploadedSucess:::" + uploadedSucess);
			  			  LOGGER.info("imageNameRender:::" + imageNameRender);
			  			  PortletSession session = request.getPortletSession();
			  			  session.setAttribute("uploadedSucess", uploadedSucess, PortletSession.PORTLET_SCOPE);
			  			  session.setAttribute("imageName", imageNameRender, PortletSession.PORTLET_SCOPE);
			  			} catch (Exception e) {                  
							LOGGER.info("Exception in groupingImgUploadAction:::",e );
						}
		  			  
		    		}
		/**
		 * @param uploadImagesDTO
		 */
		public void setGroupingImageDetails(UploadImagesDTO uploadImagesDTO) {
			String vendorImageUploadDir = "";
			String RRDImageUploadedDir = "";
			try {				
				 Random randomGenerator = new Random();
				 int randomInt = randomGenerator.nextInt(10000);					
				 String imageName = uploadImagesDTO.getPetId() + "_"+ randomInt + "_" + uploadImagesDTO.getUserUploadedFileName();
				 LOGGER.info("imageName :"+imageName);
				 uploadImagesDTO.setImageName(imageName.toUpperCase());				
						vendorImageUploadDir = imageRequestDelegate.getVendorImageUploadDir();
						uploadImagesDTO.setVendorImageUploadDir(vendorImageUploadDir);
						RRDImageUploadedDir = imageRequestDelegate.getRRDImageUploadedDir();
						uploadImagesDTO.setRRDImageUploadedDir(RRDImageUploadedDir);					
			} catch (Exception e) {
				LOGGER.info("VendorImageUploadFormController.setImageDetails() error occured while setting the values" ,e);
			}
			
		}
		
		/**
		 * Call for approveAction Image
		 * @param request
		 * @param response
		 */
	@ResourceMapping("groupingImgApproveAction")
	public void groupingImgApproveAction(ResourceRequest request,ResourceResponse response) {
		LOGGER.info("entering groupingImgApproveAction method");
		try{
			JSONObject jObj = new JSONObject();	
			ImageDetails imageDetailsFromIPC = getUserDetailsfromLogin(request);
			String updatedBy = groupingImageHelper.getLoggedInUserName(imageDetailsFromIPC);	   
			String responseMsg ="";		
			String groupingId = request.getParameter("groupingId");			
			boolean contentStatus =  true ;
			String groupingType = request.getParameter("groupingType");
		    String groupOverallStatus = request.getParameter("groupOverallStatus");		   
			if(groupOverallStatus!=null && !groupOverallStatus.isEmpty()){				
				contentStatus =  getContentStatus(groupingId);
				if(!contentStatus){
					responseMsg =ImageConstants.CONTENT_MSG;					
					jObj.put("responseCode", responseMsg);
					response.getWriter().write(jObj.toString());
					response.getWriter().flush();
					response.getWriter().close();
					return ;
				}
			}	
		    	String imageId = request.getParameter("imageId");		  		
		    	if(null!= imageId && !imageId.isEmpty()){	    		
		    		JSONObject updateGRPImageStatusJsonObj = groupingImageHelper.updateGroupImageStatusJsonObj(groupingId,updatedBy,groupingType,imageId);								
					responseMsg = callUpdateGroupImageStatusJsonObj(updateGRPImageStatusJsonObj);					
					   if(ImageConstants.RESPONSE_SUCCESS_MESSAGE.equalsIgnoreCase(responseMsg)){			   
						JSONObject approveGRPImgJsonObj = groupingImageHelper.populateGroupingImgAproveJsonObj(groupingId,updatedBy,groupingType,groupOverallStatus);			   
						responseMsg = callApproveGroupingImageService(approveGRPImgJsonObj);								
					   }					
		    	}else if(imageId.isEmpty()){
		    		JSONObject approveGRPImgJsonObj = groupingImageHelper.populateGroupingImgAproveJsonObj(groupingId,updatedBy,groupingType,groupOverallStatus);			   
					responseMsg = callApproveGroupingImageService(approveGRPImgJsonObj);
					
		    	}		    	
		    	jObj.put("responseCode", responseMsg);
				response.getWriter().write(jObj.toString());
				response.getWriter().flush();
				response.getWriter().close();	
					
		
	} catch (Exception e) {
		LOGGER.info("inside catch for groupingImgApproveAction() method ",e);		
	}
		
}
	
	public boolean getContentStatus(String groupingId){		
		boolean contentStatus = true ;
		try {
			contentStatus = imageRequestDelegate.getContentStatus(groupingId);
		} catch (PEPServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PEPPersistencyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentStatus;
		
	}

	@ResourceMapping("removeGroupingImage")
	public void removeGroupingImage(ResourceRequest request, ResourceResponse response){  
		String responseMsg = "";
		ImageDetails imageDetailsFromIPC = getUserDetailsfromLogin(request);	       
		   String updatedBy = groupingImageHelper.getLoggedInUserName(imageDetailsFromIPC);	  
		   String imagId = request.getParameter("imageIDToDel");	   
		   String imagName = request.getParameter("imageNameToDel");	   
		   Properties prop =PropertyLoader.getPropertyLoader(ImageConstants.LOAD_IMAGE_PROPERTY_FILE);
		   String fileDir = prop.getProperty(ImageConstants.FILE_UPLOAD_PATH);
		   String fileToBeDeleted = fileDir + imagName; 
		   boolean fileRemove = false ;		
	      JSONObject jsonMapObject = new JSONObject();        
		  jsonMapObject.put(ImageConstants.GROUP_ID,request.getParameter("groupingId"));
		  jsonMapObject.put(ImageConstants.GROUP_TYPE,request.getParameter("groupType"));
		  jsonMapObject.put(ImageConstants.CREATEDBY,updatedBy);
	      JSONArray jsonArray = new JSONArray();
	       try {  
	        JSONObject jsonObj = groupingImageHelper.populateGroupImgRemoveJsonObj(imagId);
	        jsonArray.put(jsonObj);     
	        jsonMapObject.put(ImageConstants.GROUP_IMAGE_ID_LIST, jsonArray);
	        LOGGER.info("  jsonMapObject in removeGroupingImage method  --> "+jsonMapObject);	
	        responseMsg = callRemoveGroupingImageWebService(jsonMapObject);           
	        if(ImageConstants.RESPONSE_SUCCESS_MESSAGE.equalsIgnoreCase(responseMsg)){
	     	   LOGGER.info("***Service success response For Remove***");     	   
	     	try {
				fileRemove = fileDelete(fileToBeDeleted);
					LOGGER.error("fileRemove::" +fileRemove);
				}catch (FileNotFoundException e) {
					LOGGER.info("*** FileNotFoundException in imagerequest controller class***",e);    
				}catch (IOException ex) {
					LOGGER.info("*** IOException in imagerequest controller class***",ex);   
				}		
				catch(Exception ex){
					LOGGER.info("*** Exception in imagerequest controller class***",ex);   
					ex.printStackTrace();
				}
	        }else{
	        	 LOGGER.info("  Image remove failure in controller   --> ");	
	        	
	        }
	    } catch (Exception e) {
	        LOGGER.info("Caught Exception**************removeGroupingImage---Controller",e);
	       
	    }
	}
	//WebService Call for Uploading grouping image 
	private String callGroupingImageUploadService(JSONObject jsonMapObject)
			throws Exception, PEPFetchException {	
		String responseMsg = null;
		responseMsg = imageRequestDelegate.callGroupingImageUploadService(jsonMapObject);
		return responseMsg;
	 }

	//Web service to callRemoveGroupingImageWebService
	private String callRemoveGroupingImageWebService(JSONObject imageInfo) throws Exception,
	    PEPFetchException {
		
	    String responseMsg = null;
	    responseMsg = imageRequestDelegate.callRemoveGroupingImageWebService(imageInfo);
	    return responseMsg;
	}

	//Service Call for Approve  Action Start
	private String callUpdateGroupImageStatusJsonObj(JSONObject imageInfo)
			throws Exception, PEPFetchException {

		String responseMsg = null;
		responseMsg = imageRequestDelegate.callUpdateGroupImageStatusJsonObj(imageInfo);
		return responseMsg;

	}
	//Service Call for Approve  Action Start
	private String callApproveGroupingImageService(JSONObject imageInfo)
			throws Exception, PEPFetchException {

		String responseMsg = null;
		responseMsg = imageRequestDelegate.callApproveGroupingImageService(imageInfo);
		return responseMsg;

	}

	/**
	 * Method to get the Image attribute details in screen.
	 * 
	 * Method added For PIM Phase 2 - Regular Item Image Link Attribute
	 * Date: 05/13/2016
	 * Added By: Cognizant
	 */	
	@ResourceMapping("scene7ImageURL")
	public ModelAndView getScene7ImageURLDetails(ResourceRequest request,
			ResourceResponse response) {
		LOGGER.info("getScene7ImageURLDetails ************..");

		ModelAndView mv = null;
		try {
			if (null != request
					.getParameter(ImageConstants.SELECTED_COLOR_ORIN)) {
				LOGGER.info(" selected ORIN *******"
						+ request
								.getParameter(ImageConstants.SELECTED_COLOR_ORIN));
				String orinNumber = request.getParameter(ImageConstants.SELECTED_COLOR_ORIN);
		        List<ImageLinkVO> imageLinkVOList = imageRequestDelegate.getScene7ImageLinks(orinNumber);
		        LOGGER.debug("Image link attribute list size -- " + imageLinkVOList.size());
		        
				JSONArray jsonImageDtls = new JSONArray();
				jsonImageDtls = populateScene7ImageURLJSON(imageLinkVOList);
				LOGGER.info("jsonImageLinkDtls *************" + jsonImageDtls);
				response.getWriter().write(jsonImageDtls.toString());
			}

		} catch (Exception e) {
			LOGGER.error("Exception in getScene7ImageURLDetails method - " + e.getMessage());
		}

		LOGGER.info("Exiting getScene7ImageURLDetails method");
		return mv;

	}
	
	/**
     * JSON for Scene 7 Image URL
     * @param imageLinkVOList List<ImageLinkVO>
     * @return jsonArrayImageDtls JSONArray
     */
	public JSONArray populateScene7ImageURLJSON(
			List<ImageLinkVO> imageLinkVOList) {
		JSONObject jsonObj = null;
		JSONArray jsonArrayImageDtls = new JSONArray();
		LOGGER.info("Entering populateScene7ImageURLJSON....Controller::");

		for (ImageLinkVO item : imageLinkVOList) {
			jsonObj = new JSONObject();
			jsonObj.put("shotType", item.getShotType());
			jsonObj.put("viewUrl", item.getViewURL());
			jsonObj.put("swatchUrl", item.getSwatchURL());
			jsonObj.put("imageUrl", item.getImageURL());
			jsonArrayImageDtls.put(jsonObj);

		}

		LOGGER.info("Exiting populateScene7ImageURLJSON....Controller::");
		return jsonArrayImageDtls;
	}
	/**
     * Modification End.
     */
	//logout changes - session invalidation
	@ResourceMapping("invalidate")
    public void invalidateSession(ResourceRequest request){
    	LOGGER.info("Image screen - invalidating session");
    	request.getPortletSession().invalidate();
    }
}