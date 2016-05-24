
package com.belk.pep.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;


import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.model.ImageLinkVO;
import com.belk.pep.model.WorkFlow;
//import com.belk.pep.service.ArrayList;
//import com.belk.pep.service.String;
/**
 * This calss responsible for service delegate
 * 
 *
 */
public interface ImageRequestService {
	
	
	
	/**
	 * 
	 * 
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public ArrayList getStyleInfoDetails(String orinNo) throws PEPServiceException, PEPPersistencyException;
	
	/**
	 * 
	 * 
	 * @return
	 * @throws PEPServiceException
	 * @throws PEPPersistencyException
	 */
	public ArrayList getImageInfoDetails(String orinNo) throws PEPServiceException, PEPPersistencyException;
	
	/**
     * 
     * 
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public ArrayList getVendorInformation(String orinNo) throws PEPServiceException, PEPPersistencyException;
    
    /**
     * 
     * 
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public ArrayList getContactInformation(String orinNo) throws PEPServiceException, PEPPersistencyException;
    
    /**
     * 
     * @param orinNo
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public ArrayList getPepHistoryDetails(String orinNo) throws PEPServiceException, PEPPersistencyException;
    
    /**
     * 
     * @param orinNo
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public ArrayList getSampleImageLinks(String orinNo) throws PEPServiceException, PEPPersistencyException;
    
    /**
     * 
     * @param orinNo
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public ArrayList getArtDirectorRequestDetails(String orinNo) throws PEPServiceException, PEPPersistencyException;

    public String callRemoveImageWebService(JSONArray imageInfo) throws Exception;
    
    
    
    public List<WorkFlow> getImageMgmtDetailsByOrin(String orinNum) throws PEPServiceException, PEPPersistencyException;
    
    
    public String getVendorImageUploadDir() throws Exception;

	
    public String getRRDImageUploadedDir() throws Exception;

	//Service Call for Approve or Reject Action
    public String callApproveorRejectActionService(JSONArray imageInfo) throws Exception;
    
    //Service Call Upload VPI
    public String callUploadVPIService(JSONArray imageInfo) throws Exception;
    
    /**
     * Service Call add SubmitorReject Service
     * @param jsonArray
     * @return
     * @throws Exception
     */
    public String callSubmitOrRejectService(JSONArray jsonArray) throws Exception;
    
    
    public String callSaveImageShotTypeService(JSONArray jsonArray) throws Exception;
    
    
    public boolean releseLockedPet(String orin, String pepUserID,String pepFunction)throws PEPPersistencyException;  
    
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
    public List<ImageLinkVO> getScene7ImageLinks(String orin) throws PEPServiceException, PEPPersistencyException;
    
}
