/**
 * 
 */
package com.belk.pep.helper;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.belk.pep.common.model.ImageDetails;
import com.belk.pep.constants.ImageConstants;
import com.belk.pep.controller.ImageRequestController;
import com.belk.pep.delegate.ImageRequestDelegate;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.FileUploadForm;
import com.belk.pep.form.SamleImageDetails;
import com.belk.pep.model.UploadImagesDTO;
import com.belk.pep.util.PropertyLoader;

/**
 * @author afusyg6
 *
 */
public class GroupingImageHelper {


	/** The Constant LOGGER. */
    private final static Logger LOGGER =
        Logger.getLogger(GroupingImageHelper.class.getName());
    
    Properties prop = PropertyLoader.getPropertyLoader(ImageConstants.LOAD_IMAGE_PROPERTY_FILE);

    
 public JSONObject populateGroupingImgUploadJsonObj(UploadImagesDTO uploadImagesDTO,String updatedBy) {
        JSONObject jsonObj = new JSONObject();
        try {  
         jsonObj.put(ImageConstants.IMAGE_NAME, uploadImagesDTO.getImageName());
         jsonObj.put(ImageConstants.IMAGE_URL, "imageUrl");
         jsonObj.put(ImageConstants.IMAGE_UPLOADURL, "uploadedUrl");
         jsonObj.put(ImageConstants.IMAGE_SHOT_TYPE, "A");
         jsonObj.put(ImageConstants.IMAGE_LOCATION, uploadImagesDTO.getCdImageLocation());
         jsonObj.put(ImageConstants.IMAGE_FILENAME, uploadImagesDTO.getUserUploadedFileName());      
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LOGGER.info(" populateFileUploadJson jsonObj ---->"+jsonObj);      
        return jsonObj;

    }
//Method to pass JSON Array
 public JSONObject populateGroupImgRemoveJsonObj( String imagId) {
     JSONObject jsonObj = new JSONObject();
     try {  	   
         jsonObj.put(ImageConstants.IMAGE_ID, imagId);        
     } catch (JSONException e) {
         e.printStackTrace();
     }
     return jsonObj;

 }
 
 /**
	 * method for populate json for approve
	 * @param orinNo
	 * @param updatedBy
	 * @return
	 */
	public JSONObject updateGroupImageStatusJsonObj(String groupingId,String updatedBy,String groupType,String imageId) {
		JSONObject jsonObj = new JSONObject();
		try {
			if(null != groupingId ){
			jsonObj.put(ImageConstants.GROUP_ID,groupingId.trim());			
			jsonObj.put(ImageConstants.GROUP_TYPE,groupType);
			jsonObj.put(ImageConstants.CREATEDBY,updatedBy);			 			
			jsonObj.put(ImageConstants.GROUP_IMAGE_STATUS, ImageConstants.GROUP_IMG_STATUS_COMPLETED);		
			jsonObj.put(ImageConstants.IMAGE_ID, imageId);
			}
			 LOGGER.info("jsonObj in  populateGroupinImgAproveJsonObj : " + jsonObj);			

		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return jsonObj;

	}
 /**
	 * method for populate json for approve
	 * @param orinNo
	 * @param updatedBy
	 * @return
	 */
	public JSONObject populateGroupingImgAproveJsonObj(String groupingId,String updatedBy,String groupType,String groupOverAllStatus) {
		JSONObject jsonObj = new JSONObject();
		try {
			if(null != groupingId ){
			jsonObj.put(ImageConstants.GROUP_ID,groupingId.trim());			
			jsonObj.put(ImageConstants.GROUP_TYPE,groupType);
			jsonObj.put(ImageConstants.CREATEDBY,updatedBy);			 			
			jsonObj.put(ImageConstants.IMAGE_OVERALL_STATUS, ImageConstants.IMAGE_COMPLETEDSTATUS);	
			//Below code should be uncommented while providing the options of publish to web or ready for copy.
				/*if(null!=groupOverAllStatus && ImageConstants.PUBLISH_TO_WEB.equalsIgnoreCase(groupOverAllStatus)){
					jsonObj.put(ImageConstants.GROUP_OVERALL_STATUS, ImageConstants.PUBLISH_TO_WEB);
					jsonObj.put(ImageConstants.GROUP_OVERALL_STATUS, ImageConstants.PUBLISH_TO_WEB);
					
				}else{
					jsonObj.put(ImageConstants.GROUP_OVERALL_STATUS, ImageConstants.READY_FOR_COPY);
				}*/
			
			  jsonObj.put(ImageConstants.GROUP_OVERALL_STATUS, "");
			}
			 LOGGER.info("jsonObj in  populateGroupinImgAproveJsonObj : " + jsonObj);			

		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return jsonObj;

	}
	
   
   /**
	 * Set the imageName and Shot type to UploadImagesDTO
	 * 
	 * @param uploadImagesDTO
	 * @param fileExtn
	 */
	public void setImageDetails(UploadImagesDTO uploadImagesDTO) {
		String vendorImageUploadDir = "";
		String RRDImageUploadedDir = "";
		try {
			
			 Random randomGenerator = new Random();
			 int randomInt = randomGenerator.nextInt(10000);					
			 String imageName = uploadImagesDTO.getPetId() + "_"+ randomInt + "_" + uploadImagesDTO.getUserUploadedFileName();
			 LOGGER.info("imageName :"+imageName);
			 uploadImagesDTO.setImageName(imageName.toUpperCase());
			// vendorImageUploadDir = imageRequestDelegate.getVendorImageUploadDir();
			 
			 //uploadImagesDTO.setVendorImageUploadDir(vendorImageUploadDir);
			 uploadImagesDTO.setVendorImageUploadDir("/opt/IBM/WebSphere/wp_profile2/Image");
			 /*RRDImageUploadedDir = imageRequestDelegate.getRRDImageUploadedDir();
			 uploadImagesDTO.setRRDImageUploadedDir(RRDImageUploadedDir);	*/		
		} catch (Exception e) {
			LOGGER.info("VendorImageUploadFormController.setImageDetails() error occured while setting the values" ,e);
		}
		
	}
	
	 /**
     * Json for file upload service
     * @param OrinNo
     * @param roleToPass
     * @return
     */
    public JSONObject populateJsonGroupingVPI(String fileDir,ArrayList<SamleImageDetails> sampleImageLinkList) throws PEPPersistencyException {
    	 JSONObject jsonObj = null;
       
         LOGGER.info("Entering populateJsonVPI....Controller::");     
         Properties prop =PropertyLoader.getPropertyLoader(ImageConstants.LOAD_IMAGE_PROPERTY_FILE);
		 fileDir = prop.getProperty(ImageConstants.FILE_UPLOAD_PATH);
        try {	
        
        	for (SamleImageDetails item : sampleImageLinkList) {
        		String imageFilePath = fileDir+item.getImageName();
       		 LOGGER.info("populateJsonVPI imageFilePath ::::::::::: "+imageFilePath);
        	jsonObj = new JSONObject();
            jsonObj.put("imageID", item.getImageId());          
            jsonObj.put("originalImageName", item.getOriginalImageName());
            jsonObj.put("imageName", item.getImageName());           
            jsonObj.put("imageStatus", item.getImageStatus());          
            jsonObj.put("imagefilepath", imageFilePath);
            jsonObj.put("role", "dca");
         
        	}       	
          
        	
        } catch (JSONException e) {
			   LOGGER.error(" ---JSONException --- ",e);
            e.printStackTrace();
        }
		LOGGER.info("Exiting populateJsonVPI....Controller::");
        return jsonObj;
    }
	public String getLoggedInUserName(ImageDetails imageDetailsFromIPC){		
		String updatedBy = "";
	       if(imageDetailsFromIPC.getUserData().getBelkUser()!= null){
	             updatedBy = imageDetailsFromIPC.getUserData().getBelkUser().getLanId();
	       }else if (imageDetailsFromIPC.getUserData().getVpUser()!= null){
	             updatedBy = imageDetailsFromIPC.getUserData().getVpUser().getUserEmailAddress();
	       }
	       return updatedBy;
	}
   
}
