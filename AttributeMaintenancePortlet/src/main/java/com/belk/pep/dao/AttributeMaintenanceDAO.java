
package com.belk.pep.dao;

import java.util.List;

import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.vo.AttributeDetailsVO;
import com.belk.pep.vo.AttributeSearchVO;
import com.belk.pep.vo.AttributeValueVO;
import com.belk.pep.vo.CategoryListVO;



/**
 * The Interface ContentDAO.
 */
public  interface AttributeMaintenanceDAO {
    
    /**
     * Gets the attribute details for given search criteria
     *
     * @param IPHvalue the IPHvalue number
     * @return void
     * @throws PEPFetchException the PEP fetch exception
     */
    List<AttributeDetailsVO> getAttributeListForSearchCriteria(AttributeSearchVO attributeSearchVO) throws PEPFetchException;
    
    /**
     * Gets the attribute list.
     *
     * @return the attribute list
     * @throws PEPFetchException the PEP fetch exception
     */
    List<CategoryListVO> getCategoryList() throws PEPFetchException;
    
    /**
     * Update attribute details.
     *
     * @param attributeValueVO the attribute value vo
     * @return true, if successful
     * @throws PEPFetchException the PEP fetch exception
     */
    int updateAttributeDetails(AttributeValueVO attributeValueVO)  throws PEPFetchException;
    
    /**
     * Gets the attribute details for category id attribute id.
     *
     * @return the attribute details for category id attribute id
     * @throws PEPFetchException the PEP fetch exception
     */
    AttributeValueVO getAttributeDetailsForCatIdAttId(String categoryId, String attributeId) throws PEPFetchException;
        
  
}
