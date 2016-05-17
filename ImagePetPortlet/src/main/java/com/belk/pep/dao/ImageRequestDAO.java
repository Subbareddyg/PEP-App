
package com.belk.pep.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.form.ArtDirectorRequestDetails;
import com.belk.pep.model.ImageLinkVO;
import com.belk.pep.model.WorkFlow;

/**
 * This class responsible for handling all the DAO call to the VP Database
 * 
 *
 */

public  interface ImageRequestDAO {

	
	
	/**
     * 
     * @param orinNo
     * @return
     * @throws SQLException
     */
    public ArrayList getStyleInfoDetails(String orinNo) throws PEPPersistencyException;
    
    /**
     * 
     * @param orinNo
     * @return
     * @throws SQLException
     */
    public ArrayList getImageInfoDetails(String orinNo) throws PEPPersistencyException;
    
    /**
     * 
     * @param orinNo
     * @return
     * @throws PEPPersistencyException     
     */
    
    public ArrayList getVendorInformation(String orinNo) throws PEPPersistencyException;
    
    
    /**
     * 
     * @param orinNo
     * @return
     * @throws PEPPersistencyException     
     */
    
    public ArrayList getContactInformation(String orinNo) throws PEPPersistencyException;
    
    /**
     * 
     * @param orinNo
     * @return
     * @throws PEPPersistencyException
     */
    public ArrayList getPepHistoryDetails(String orinNo) throws PEPPersistencyException;
    
    /**
     * 
     * @param orinNo
     * @return
     * @throws PEPPersistencyException
     */
    public ArrayList getSampleImageLinks(String orinNo) throws PEPPersistencyException;
    
    
    /**
     * 
     * @param orinNo
     * @return
     * @throws PEPPersistencyException
     */
    public ArrayList getArtDirectorRequestDetails(String orinNo) throws PEPPersistencyException;
    
    public List<WorkFlow> getImageMgmtDetailsByOrin(String orinNum)
    throws PEPPersistencyException;
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
    public List<ImageLinkVO> getCopyImageLinks(String orin) throws PEPPersistencyException;
}
