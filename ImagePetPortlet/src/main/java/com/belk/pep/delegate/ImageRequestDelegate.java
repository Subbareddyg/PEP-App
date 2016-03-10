package com.belk.pep.delegate;

import java.sql.SQLException;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;


import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
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
}
