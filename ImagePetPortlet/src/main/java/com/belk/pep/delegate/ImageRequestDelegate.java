package com.belk.pep.delegate;

import java.sql.SQLException;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.model.ImageLinkVO;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.service.ImageRequestService;

/**
 * The Class ImageRequestService.
 * 
 *
 */
public class ImageRequestDelegate {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(ImageRequestDelegate.class.getName()); 	
	
	
	/** The user service. */
	private ImageRequestService imageRequestService;

	/**
	 * 
	 * @return
	 */

	public ImageRequestService getExternalUserService() {
        return imageRequestService;
    }
/**
 * 
 * @param externalUserService
 */
    public void setImageRequestService(ImageRequestService imageRequestService) {
        this.imageRequestService = imageRequestService;
    }



    /**
     * 
     * 
     * 
     */
    public ArrayList getStyleInfoDetails(String orinNo) throws PEPServiceException, PEPPersistencyException {
        return imageRequestService.getStyleInfoDetails(orinNo);
    }
    
    /**
     * 
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public ArrayList getImageInfoDetails(String orinNo) throws PEPServiceException, PEPPersistencyException {
        return imageRequestService.getImageInfoDetails(orinNo);
    }
    
    /**
     * 
     * 
     * get the vendor information calling the service
     */
    public ArrayList getVendorInformation(String orinNo) throws PEPServiceException, PEPPersistencyException {
        return imageRequestService.getVendorInformation(orinNo);
    }
    
    /**
     * 
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public ArrayList getContactInformation(String orinNo) throws PEPServiceException, PEPPersistencyException {
        return imageRequestService.getContactInformation(orinNo);
    }
    
    /**
     * 
     * @param orinNo
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public ArrayList getPepHistoryDetails(String orinNo) throws PEPServiceException, PEPPersistencyException {
        return imageRequestService.getPepHistoryDetails(orinNo);
    }
    
    /**
     * 
     * @param orinNo
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public ArrayList getSampleImageLinks(String orinNo) throws PEPServiceException, PEPPersistencyException {
        return imageRequestService.getSampleImageLinks(orinNo);
    }
    
    /**
     * 
     * @param orinNo
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public ArrayList getArtDirectorRequestDetails(String orinNo) throws PEPServiceException, PEPPersistencyException {
        return imageRequestService.getArtDirectorRequestDetails(orinNo);
    }

    public String callRemoveImageWebService(JSONArray imageInfo) throws Exception,PEPFetchException {
        LOGGER.info("callRemoveImageWebService:--Delegate");
        return imageRequestService.callRemoveImageWebService(imageInfo);
    }
    
    //Service call delegate for Approve or Reject
    public String callApproveorRejectActionService(JSONArray imageInfo) throws Exception,PEPFetchException {
        LOGGER.info("ImageRequestDelegate:: createPetWebService");
        return imageRequestService.callApproveorRejectActionService(imageInfo);
    }

    //Service call delegate for Upload VPI
    public String callUploadVPIService(JSONArray imageInfo) throws Exception,PEPFetchException {
        LOGGER.info("ImageRequestDelegate:: callUploadVPIService");
        return imageRequestService.callUploadVPIService(imageInfo);
    }
    
    /**
     * This method will return the PET details from the ADSE on base of department 
     * @param departmentNumbers
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public List<WorkFlow> getImageMgmtDetailsByOrin(String orinNum) throws PEPServiceException, PEPPersistencyException {
        return imageRequestService.getImageMgmtDetailsByOrin(orinNum);  
    }
    
 
   
    public String getVendorImageUploadDir() throws Exception {
        return imageRequestService.getVendorImageUploadDir();  
    }
    
	public String getRRDImageUploadedDir() throws Exception{
		return imageRequestService.getRRDImageUploadedDir();  
	}
	
	/**
	 * Service Call add SubmitorReject Service
	 * @param jsonArray
	 * @return
	 * @throws Exception
	 */
	public String callSubmitOrRejectService(JSONArray jsonArray)throws Exception {
		LOGGER.info("ImageRequestDelegate::callSubmitOrRejectService---*******");
		return imageRequestService.callSubmitOrRejectService(jsonArray); 
	}
	
	public String callSaveImageShotTypeService(JSONArray jsonArray)throws Exception {
		LOGGER.info("ImageRequestDelegate::callSaveImageShotTypeService---*******");
		return imageRequestService.callSaveImageShotTypeService(jsonArray); 
		
	}
	 public boolean releseLockedPet(String orin, String pepUserID,String pepFunction)throws PEPPersistencyException {
	        LOGGER.info("Image requestdelegate :: releseLockedPet");
	        boolean  isPetReleased = imageRequestService.releseLockedPet(orin,pepUserID,pepFunction);     
	        return isPetReleased;
	    }
	 /**
		 * Method to get the Image attribute details from database.
		 *    
		 * @param orin String   
		 * @return imageLinkVOList List<ImageLinkVO>
		 * 
		 * Method added For PIM Phase 2 - Regular Item Image Link Attribute
		 * Date: 05/13/2016
		 * Added By: Cognizant
		*/	
		public List<ImageLinkVO> getScene7ImageLinks(String orinNum) throws PEPServiceException, PEPPersistencyException{
			
			LOGGER.info("***Entering ImageRequestDelegate.getScene7ImageLinks() method.");
		    
			List<ImageLinkVO> imageLinkVOList = null;
			
		    try {
		    	imageLinkVOList = imageRequestService.getScene7ImageLinks(orinNum);
		    }		    
		    catch (Exception exception) {
		
		    	LOGGER.error("Exception in getScene7ImageLinks() method, Delegate Layer -- " + exception.getMessage());
		        throw new PEPServiceException(exception.getMessage());
		    }
		    LOGGER.info("***Exiting ImageRequestDelegate.getScene7ImageLinks() method.");
		    return imageLinkVOList;
		    
		}
		 /**
	     * 
	     * 
	     * 
	     */
	    public ArrayList getGroupingInfoDetails(String groupingId) throws PEPServiceException, PEPPersistencyException {
	        return imageRequestService.getGroupingInfoDetails(groupingId);
	        
	    }
	    /**
	     * 
	     * @return
	     * @throws PEPServiceException
	     * @throws PEPPersistencyException
	     */
	    public ArrayList getGroupingDetails(String groupingId) throws PEPServiceException, PEPPersistencyException {
	    
	        return imageRequestService.getGroupingDetails(groupingId);
	    }
	    /**
	     * 
	     * @param orinNo
	     * @return
	     * @throws PEPServiceException
	     * @throws PEPPersistencyException
	     */
	    public ArrayList getGroupingSampleImageLinks(String groupingId) throws PEPServiceException, PEPPersistencyException {
	        return imageRequestService.getGroupingSampleImageLinks(groupingId);
	    }
	  
	    public String callGroupingImageUploadService(JSONObject jsonMapObject) throws Exception,PEPFetchException {
	        LOGGER.info("ImageRequestDelegate:: callUploadVPIService");
	        return imageRequestService.callGroupingImageUploadService(jsonMapObject);
	    }
	    
	    /**
	     * 
	     * @param orinNo
	     * @return
	     * @throws PEPServiceException
	     * @throws PEPPersistencyException
	     */
	    public ArrayList getGroupingHistoryDetails(String groupingId) throws PEPServiceException, PEPPersistencyException {
	        return imageRequestService.getGroupingHistoryDetails(groupingId);
	    }
	    /**
		 * Method to get the Image attribute details from database.
		 *    
		 * @param orin String   
		 * @return imageLinkVOList List<ImageLinkVO>
		 * 
		 * Method added For PIM Phase 2 - Regular Item Image Link Attribute
		 * Date: 05/13/2016
		 * Added By: Cognizant
		*/	
		public List<ImageLinkVO> getGroupingScene7ImageLinks(String groupingId) throws PEPServiceException, PEPPersistencyException{
			
			LOGGER.info("***Entering ImageRequestDelegate.getScene7ImageLinks() method.");
		    
			List<ImageLinkVO> imageLinkVOList = null;
			
		    try {
		    	imageLinkVOList = imageRequestService.getGroupingScene7ImageLinks(groupingId);
		    }		    
		    catch (Exception exception) {
		
		    	LOGGER.error("Exception in getScene7ImageLinks() method, Delegate Layer -- " + exception.getMessage());
		        throw new PEPServiceException(exception.getMessage());
		    }
		    LOGGER.info("***Exiting ImageRequestDelegate.getScene7ImageLinks() method.");
		    return imageLinkVOList;
		    
		}
		  public String callRemoveGroupingImageWebService(JSONObject imageInfo) throws Exception,PEPFetchException {
		        LOGGER.info("callRemoveImageWebService:--Delegate");
		        return imageRequestService.callRemoveGroupingImageWebService(imageInfo);
		    }
		  
		//Service call delegate for Approve or Reject
		    public String callApproveGroupingImageService(JSONObject imageInfo) throws Exception,PEPFetchException {
		        LOGGER.info("ImageRequestDelegate:: callApproveGroupingImageService");
		        return imageRequestService.callApproveGroupingImageService(imageInfo);
		    }
		  //Service call delegate for Approve or Reject
		    public String callUpdateGroupImageStatusJsonObj(JSONObject imageInfo) throws Exception,PEPFetchException {
		        LOGGER.info("ImageRequestDelegate:: callUpdateGroupImageStatusJsonObj");
		        return imageRequestService.callUpdateGroupImageStatusJsonObj(imageInfo);
		    }
}
