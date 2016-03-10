
package com.belk.pep.service;

import java.util.List;

import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.vo.AttributeDetailsVO;
import com.belk.pep.vo.AttributeSearchVO;
import com.belk.pep.vo.AttributeValueVO;
import com.belk.pep.vo.CategoryListVO;


/**
 * The Interface ContentService.
 */
public interface AttributeMaintenanceService {
    
    
    /**
     * Gets the attribute details for given search criteria
     *
     * @param IPHvalue the IPHvalue number
     * @return void
     * @throws PEPFetchException the PEP fetch exception
     */
    public List<AttributeDetailsVO> getAttributeListForSearchCriteria(AttributeSearchVO attributeSearchVO)  throws PEPServiceException;
            
    /**
     * Gets the attribute list.
     *
     * @return the attribute list
     * @throws PEPFetchException the PEP fetch exception
     */
    List<CategoryListVO> getCategoryList() throws PEPServiceException;
    
    /**
     * Update attribute details.
     *
     * @param attributeValueVO the attribute value vo
     * @return true, if successful
     * @throws PEPServiceException the PEP service exception
     */
    int updateAttributeDetails(AttributeValueVO attributeValueVO)  throws PEPServiceException;
    
    /**
     * Gets the attribute details for cat id att id.
     *
     * @param categoryId the category id
     * @param attributeId the attribute id
     * @return the attribute details for cat id att id
     * @throws PEPServiceException the PEP service exception
     */
    AttributeValueVO getAttributeDetailsForCatIdAttId(String categoryId, String attributeId) throws PEPServiceException;
}
