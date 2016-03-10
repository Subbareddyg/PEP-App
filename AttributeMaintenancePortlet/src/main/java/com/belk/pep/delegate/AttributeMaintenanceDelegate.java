package com.belk.pep.delegate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.belk.pep.exception.checked.PEPDelegateException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.service.AttributeMaintenanceService;
import com.belk.pep.vo.AttributeDetailsVO;
import com.belk.pep.vo.AttributeSearchVO;
import com.belk.pep.vo.AttributeValueVO;
import com.belk.pep.vo.CategoryListVO;


/**
 * The Class ContentDelegate.
 */
public class AttributeMaintenanceDelegate {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(AttributeMaintenanceDelegate.class.getName()); 	
	
	   /** The content service. */
    private AttributeMaintenanceService attributeMaintenanceService;
    

    public AttributeMaintenanceService getAttributeMaintenanceService() {
        return attributeMaintenanceService;
    }

    public void setAttributeMaintenanceService(
        AttributeMaintenanceService attributeMaintenanceService) {
        this.attributeMaintenanceService = attributeMaintenanceService;
    }
 
    
    /**
     * Gets the style information.
     *
     * @param orinNumber the orin number
     * @return the style information
     */
    public List<AttributeDetailsVO> getAttributeListForSearchCriteria(AttributeSearchVO attributeSearchVO)
        throws PEPDelegateException {
        List<AttributeDetailsVO> lstAttributeDetailsVO = null;
        try {
            lstAttributeDetailsVO =
                attributeMaintenanceService.getAttributeListForSearchCriteria(attributeSearchVO);
        }
        catch (PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());

            throw new PEPDelegateException(serviceException.getMessage());
        }
        return lstAttributeDetailsVO;
    }
    
    /**
     * Gets the attribute list.
     *
     * @param iPHvalue the i p hvalue
     * @return the attribute list
     * @throws PEPDelegateException the PEP delegate exception
     */
    public List<CategoryListVO> getCategoryList()
        throws PEPDelegateException {
        List<CategoryListVO> lstAttributeDetailsVO = null;
        try {
            lstAttributeDetailsVO =
                attributeMaintenanceService.getCategoryList();
        }
        catch (PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());

            throw new PEPDelegateException(serviceException.getMessage());
        }
        return lstAttributeDetailsVO;
    }
    
    
    /**
     * Gets the attribute details for category id attribute id.
     *
     * @param categoryId the category id
     * @param attributeId the attribute id
     * @return the attribute details for category id attribute id
     * @throws PEPDelegateException the PEP delegate exception
     */
    public AttributeValueVO getAttributeDetailsForCatIdAttId(
        String categoryId, String attributeId) throws PEPDelegateException {
        AttributeValueVO attributeValueVO = null;
        try {
            attributeValueVO =
                attributeMaintenanceService.getAttributeDetailsForCatIdAttId(
                    categoryId, attributeId);
        }
        catch (PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());

            throw new PEPDelegateException(serviceException.getMessage());
        }
        return attributeValueVO;
    }
    
    /**
     * Gets the attribute details for cat id att id.
     *
     * @param attributeValueVO the attribute value vo
     * @return the attribute details for cat id att id
     * @throws PEPDelegateException the PEP delegate exception
     */
    public int updateAttributeDetails(AttributeValueVO attributeValueVO) throws PEPDelegateException {
        int updateStatus = -1;
        try {
            updateStatus =
                attributeMaintenanceService.updateAttributeDetails(attributeValueVO);
        }
        catch (PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());

            throw new PEPDelegateException(serviceException.getMessage());
        }
        return updateStatus;
    }
}
