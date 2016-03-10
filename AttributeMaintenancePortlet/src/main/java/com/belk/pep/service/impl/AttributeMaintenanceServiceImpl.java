package com.belk.pep.service.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.belk.pep.dao.AttributeMaintenanceDAO;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.service.AttributeMaintenanceService;
import com.belk.pep.vo.AttributeDetailsVO;
import com.belk.pep.vo.AttributeSearchVO;
import com.belk.pep.vo.AttributeValueVO;
import com.belk.pep.vo.CategoryListVO;


/**
 * The Class ContentServiceImpl.
 */
public class AttributeMaintenanceServiceImpl implements AttributeMaintenanceService {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(AttributeMaintenanceServiceImpl.class.getName()); 
	
    /** The content dao impl. */
    private AttributeMaintenanceDAO  attributeMaintenanceDAO;

    
    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getStyleInfoFromADSE(java.lang.String)
     */
    @Override
    public List<AttributeDetailsVO> getAttributeListForSearchCriteria(AttributeSearchVO attributeSearchVO)  throws PEPServiceException {
        List<AttributeDetailsVO> lstAttributeDetails = null;
        LOGGER.info("****start of  getAttributeListForSearchCriteria  method****");
        
        try {
            lstAttributeDetails = attributeMaintenanceDAO.getAttributeListForSearchCriteria(attributeSearchVO);
            LOGGER.info("****end of  getAttributeListForSearchCriteria  method****");
        }
        catch (PEPFetchException fetchException) {
            fetchException.printStackTrace();
            LOGGER.log(Level.SEVERE, fetchException.getMessage());           
            throw new PEPServiceException(fetchException.getMessage());
        }      
        return lstAttributeDetails;
    }
    
    
    
   
    /* (non-Javadoc)
     * @see com.belk.pep.service.AttributeMaintenanceService#getAttributeList()
     */
    @Override
    public List<CategoryListVO> getCategoryList()  throws PEPServiceException {
        List<CategoryListVO> lstAttributeDetails = null;
        LOGGER.info("****start of  getAttributeList  method****");
        
        try {
            lstAttributeDetails = attributeMaintenanceDAO.getCategoryList();
            LOGGER.info("****end of  getAttributeList  method****");
        }
        catch (PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());           
            throw new PEPServiceException(fetchException.getMessage());
        }      
        return lstAttributeDetails;
    }
    
    
    @Override
    public int updateAttributeDetails(AttributeValueVO attributeValueVO)  throws PEPServiceException {
        int updateStatus = -1;
        LOGGER.info("****start of  updateAttributeDetails  method****");
        
        try {
            updateStatus = attributeMaintenanceDAO.updateAttributeDetails(attributeValueVO);
            LOGGER.info("****end of  updateAttributeDetails  method****");
        }
        catch (PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());           
            throw new PEPServiceException(fetchException.getMessage());
        }      
        return updateStatus;
    }
    
    
    /* (non-Javadoc)
     * @see com.belk.pep.service.AttributeMaintenanceService#getAttributeDetailsForCatIdAttId(java.lang.String, java.lang.String)
     */
    @Override
    public AttributeValueVO getAttributeDetailsForCatIdAttId(String categoryId, String attributeId)  throws PEPServiceException {
        AttributeValueVO attributeValueVO = null;
        LOGGER.info("****start of  getAttributeDetailsForCatIdAttId  method****");
        
        try {
            attributeValueVO = attributeMaintenanceDAO.getAttributeDetailsForCatIdAttId(categoryId, attributeId);
            LOGGER.info("****end of  getAttributeDetailsForCatIdAttId  method****");
        }
        catch (PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());           
            throw new PEPServiceException(fetchException.getMessage());
        }      
        return attributeValueVO;
    }
    
    
    public AttributeMaintenanceDAO getAttributeMaintenanceDAO() {
        return attributeMaintenanceDAO;
    }

    public void setAttributeMaintenanceDAO(
        AttributeMaintenanceDAO attributeMaintenanceDAO) {
        this.attributeMaintenanceDAO = attributeMaintenanceDAO;
    }
}
