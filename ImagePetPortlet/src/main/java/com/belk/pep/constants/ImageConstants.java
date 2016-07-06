package com.belk.pep.constants;

/**
 * This class is for handling all the constants for the ImageConstants portlet
 * 
 * 
 */
public class ImageConstants {

    public static final String ACTION = "action";

    public static final String MODELVIEW_SUCCESS = "imageDetails";
    public static final String MODELVIEW_SUCCESS_GROUPING = "groupingImageDetails";
    public static final String MODELVIEW_WORKLISDISPLAY = "worklistDisplay";

    public static final String PROPERTIES_FILE_NAME =
        "imagePortletResources.properties";
    
    public static final String IMAGE_INFO_DETAILS = "imageDetailsForm";
    //Orin coming from worklist diplay
    public static final String IMAGE_ORIN = "orinNo";
    public static final String IMAGE_ORIN_SKU_CONSTANT = "Sku";
    public static final String IMAGE_ORIN_STYLE_CONSTANT = "StyleColor";
    
    public static final String IMAGE_CONTACT1_TITLE = "Contact 1";
    public static final String IMAGE_CONTACT2_TITLE = "Contact 2";
    public static final String PET_ID = "petId";
   
    public static final String IMAGE_ID = "imageId";
    public static final String SAVE_IMAGE_STATUS = "imageStatus";
    public static final String IMAGE_SHOT_TYPE = "shotType";
    public static final String TURN_IN_DATE = "turnInDate";
    public static final String SAMPLE_RECEIVED = "sampleReceived";
    public static final String SILHOUETTE = "silhouette";
    public static final String SAMPLEORSWATCHFLG = "sampleorswatchflg";
    
    public static final String MESS_PROP = "message.properties";
    public static final String DEV_SERVICE_URL = "dev.service.url";
    public static final String SERVICE_REMOVE_IMAGE_LIST = "list";
    public static final String IMAGE_NOT_REMOVED = "image.remove.service.response.message";
    public static final String RESPONSE_MSG = "imageremove.success.msg";
    public static final String MSG_CODE = "code";
    public static final String SUCCESS_CODE = "100";
    public static final String FAILURE_CODE = "101";
    public static final String USER_DATA = "UserData" ;
    /** The Constant USER_DATA. */
    public static final String IMAGE_DETAILS = "ImageDetailsOBJ" ;
    public static final String SELECTED_COLOR_ORIN = "selectedColorOrin" ;
 //Common message parameters form ADReceiveReq and ApproveRejectRequest
    public static final String IMAGE_STATUS = "imageStatus";
    public static final String IMAGE_SUCCESS_STATUS = "image.success.message";
    public static final String IMAGE_ERROR_STATUS = "image.error.message";
    
    //Comments
    public static final String IMAGE_COMMENTS="comment";
    public static final String USER_ID="userId";
    
    //For Approve or Reject Service Parameter    
    public static final String IMAGE_APPROVE_WEBSERVICE_URL = "image.approve.webservice.url";
   
    public static final String SERVICE_APPROVEORREJECT_ACTION_VALUE_LIST = "list";
    
    
    //For AD Receive or Reject Service Parameter
    public static final String DEV_SERVICE_ADRECEIVEREQUEST_URL = "dev.service.url.artDirectorReceiveReject.url";
    public static final String SERVICE_ADRECEIVEREQUEST_VALUE_LIST = "list";
    
    
    //For Submit VPI Service
    public static final String DEV_SERVICE_SUBMIT_VPI_URL = "dev.service.url.submitVPI";
    public static final String SERVICE_SUBMIT_VPI_LIST = "list";
    public static final String SERVICE_SUBMIT_IMAGE_STATUS_NOTUPATED = "service.message.submit.imageStatus.notUpdated";
    public static final String SERVICE_SUBMIT_IMAGE_STATUS_UPATED = "service.message.submit.imageStatus.Updated";
    
    
    
    //For Save Image Service
    public static final String DEV_SERVICE_SAVE_IMAGE_URL = "dev.service.saveImage";
    public static final String DEV_SERVICE_SAVE_IMAGE_LIST = "list";
    
    
    //For Upload VPI Service Call
    public static final String DEV_SERVICE_UPLOADVPI_IMAGE_URL = "dev.service.url.uploadVPI";
    public static final String DEV_SERVICE_UPLOADVPI_IMAGE_LIST = "listUploadVPI";
    public static final String DEV_SERVICE_UPLOADVPI_ERROR_IMAGE_MESSAGE = "message.error.imageupload";
    public static final String DEV_SERVICE_UPLOADVPI_SUCCESS_IMAGE_MESSAGE = "message.success.imageupload";
    
    
    //Service call for Add Image Note
	public static final String DEV_SERVICE_ADD_IMAGE_NOTE_URL = "dev.service.addImageNote";
	public static final String ADD_IMAGE_NOTE_LIST = "list";
	public static final String ADD_IMAGE_NOTE_ERROR_MESSAGE = "message.error.addImageNote";
	public static final String ADD_IMAGE_NOTE_SUCCESS_MESSAGE = "message.success.addImageNote";
	
	
	//For AD request
    public static final String DEV_SERVICE_AD_REQUST_URL = "dev.service.adRequest";
    public static final String DEV_SERVICE_AD_REQUEST_LIST = "list";
    public static final String DEV_SERVICE_AD_REQUEST_ACTION_VALUE = "create";
    public static final String DEV_SERVICE_AD_REQUEST_RES_MSG_SUCCESS = "success";
    public static final String DEV_SERVICE_AD_REQUEST_RES_MSG_ERROR = "error";
    
    public static final String IMAGE_AD_REQUEST_PARAM_DISPLAY ="display";
    public static final String IMAGE_AD_REQUEST_PARAM_DISPLAY_SWATCH ="displaySwatch";
    public static final String IMAGE_AD_REQUEST_PARAM_IMAGE_LOCATION_TYPE ="imageLocationType";
    public static final String IMAGE_AD_REQUEST_PARAM_IMAGE_TYPE ="imageType";
    public static final String IMAGE_AD_REQUEST_PARAM_ADR_URL ="imageFinalUrl";
    public static final String IMAGE_AD_REQUEST_PARAM_DESC ="imageDescription";
    public static final String IMAGE_AD_REQUEST_PARAM_NOTETEXT = "imagenotesText";
    public static final String IMAGE_AD_REQUEST_PARAM_CURRENT_ORIN = "currrentAdOrinID";
    public static final String IMAGE_AD_REQUEST_PARAM_CURRENT_ADIMGID = "currentAdImgId";
    public static final String SERVICE_IMAGE_UPLOAD_LIST = "list";
    
    
    //New Image Screen Requirement 
    public static final String UPDATEDBY = "updatedBy";
    public static final String IMAGE_STATUS_COMPLETED = "02";
    public static final String DEV_SERVICE_APPROVE_URL = "dev.service.url.approveImage";
    public static final String FILE_UPLOAD_PATH = "vendor.uploadfile.path";
    public static final String LOAD_IMAGE_PROPERTY_FILE = "imagePortletResources.properties";
    public static final String IMAGE_SUBMITOREJECT_URL = "image.submitORReject.webservice.url";
    public static final String IMAGE_SAVESHOTTYPE_URL = "image.saveImageShotType.webservice.url";
    public static final String IMAGE_REJECTSTATUS = "04";
    public static final String IMAGE_READYFORREVIEWSTATUS = "08";
    public static final String IMAGE_INITIATEDSTATUS = "01";
    public static final String IMAGE_COMPLETEDSTATUS = "02";
    public static final String LOAD_GROUPING_IMAGE_PROPERTY_FILE = "groupingImagePortletResources.properties";
    public static final String GRP_SERVICE_UPLOADVPI_IMAGE_URL = "grp.service.url.uploadVPI";
    public static final String GRP_SERVICE_REMOVEIMG_IMAGE_URL = "grp.service.url.removeImage";
    public static final String GRP_SERVICE_APPROVE_IMAGE_URL = "grp.image.approve.webservice.url";
    public static final String GRP_SERVICE_UPDATE_STATUS_URL = "grp.image.statusupdate.webservice.url";
  
    public static final String GROUPING_IMAGE_UPLOAD_LIST = "imageList";
    public static final String RESPONSE_MESSAGE_STATUS = "status";
    public static final String RESPONSE_SUCCESS_MESSAGE = "SUCCESS";
    public static final String RESPONSE_FAILURE_MESSAGE = "FAIL";
    public static final String GROUP_ID = "groupId";
    public static final String GROUP_TYPE = "groupType";
    public static final String IMAGE_LIST = "imageIdList";
    public static final String IMAGE_NAME = "imageName";
    public static final String IMAGE_OVERALL_STATUS = "imageOverallStatus";
    public static final String GROUP_OVERALL_STATUS = "groupOverallStatus";
    public static final String GROUP_IMAGE_STATUS = "imageStatus";
    public static final String IMAGE_URL = "imageUrl";
    public static final String IMAGE_UPLOADURL = "uploadedUrl";
    public static final String IMAGE_LOCATION = "imageLocation";
    public static final String IMAGE_FILENAME = "fileName";
    public static final String CREATEDBY = "createdBy";
    public static final String GROUP_IMAGE_ID_LIST = "imageIdList";
    public static final String GROUP_IMG_STATUS_COMPLETED = "Completed";
    public static final String PUBLISH_TO_WEB = "09";
    public static final String READY_FOR_COPY = "06";
    public static final String CONTENT_INITIATEDSTATUS = "01";
    public static final String CONTENT_DEACTIVTEDSTATUS = "05";
    public static final String CONTENT_MSG = "grp.content.status";

    
    
    
    
    
    

}
