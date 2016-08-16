
package com.belk.pep.dao;

import java.util.List;

import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.model.GroupsFound;
import com.belk.pep.model.PetsFound;
import com.belk.pep.vo.BlueMartiniAttributesVO;
import com.belk.pep.vo.CarBrandVO;
import com.belk.pep.vo.ChildSkuVO;
import com.belk.pep.vo.ColorAttributesVO;
import com.belk.pep.vo.ContentHistoryVO;
import com.belk.pep.vo.ContentManagementVO;
import com.belk.pep.vo.CopyAttributeVO;
import com.belk.pep.vo.CopyAttributesVO;
import com.belk.pep.vo.GlobalAttributesVO;
import com.belk.pep.vo.GroupingVO;
import com.belk.pep.vo.ItemPrimaryHierarchyVO;
import com.belk.pep.vo.OmniChannelBrandVO;
import com.belk.pep.vo.PetAttributeVO;
import com.belk.pep.vo.ProductDetailsVO;
import com.belk.pep.vo.RegularPetCopy;
import com.belk.pep.vo.SkuAttributesVO;
import com.belk.pep.vo.StyleColorFamilyVO;
import com.belk.pep.vo.StyleInformationVO;


/**
 * The Interface ContentDAO.
 */
public  interface ContentDAO {



    /**
     * Gets the cards brand list.
     *
     * @param orinNumber the orin number
     * @param supplierId the supplier id
     * @return the cards brand list
     * @throws PEPFetchException the PEP fetch exception
     */
    public List<CarBrandVO> getCardsBrandList(String orinNumber, String supplierId)
            throws PEPFetchException ;


    List<StyleColorFamilyVO> getColorFamilyDataSet() throws PEPFetchException;


    /**
     * Gets the content history from adse.
     *
     * @param orinNumber the orin number
     * @return the content history from adse
     * @throws PEPFetchException the PEP fetch exception
     */
    List<ContentHistoryVO> getContentHistoryFromADSE(String orinNumber)   throws PEPFetchException;



    /**
     * Gets the content managment info from adse.
     *
     * @param orinNumber the orin number
     * @return the content managment info from adse
     * @throws PEPFetchException the PEP fetch exception
     */
    ContentManagementVO getContentManagmentInfoFromADSE(String orinNumber)   throws PEPFetchException;



    /**
     * Gets the copy attributes from adse.
     *
     * @param orinNumber the orin number
     * @return the copy attributes from adse
     * @throws PEPFetchException the PEP fetch exception
     */
    List<CopyAttributesVO> getCopyAttributesFromADSE(String orinNumber) throws PEPFetchException;



    /**
     * Gets the family categories from iph.
     *
     * @param merchCategoryId the merch category id
     * @return the family categories from iph
     * @throws PEPFetchException the PEP fetch exception
     */
    List<ItemPrimaryHierarchyVO> getFamilyCategoriesFromIPH(String merchCategoryId)    throws PEPFetchException;


    /**
     * Gets the IPH categories.
     *
     * @return the IPH categories
     * @throws PEPFetchException the PEP fetch exception
     */
    List<ItemPrimaryHierarchyVO> getIPHCategories()  throws PEPFetchException;



    /**
     * Gets the IPH categories.
     *
     * @param orinNumber the orin number
     * @return the IPH categories
     * @throws PEPFetchException the PEP fetch exception
     */
    List<ItemPrimaryHierarchyVO> getIPHCategories(String groupId, String groupType)
            throws PEPFetchException;


    /**
     * Gets the IPH categories from adse merchandise hierarchy.
     *
     * @param orinNumber the orin number
     * @return the IPH categories from adse merchandise hierarchy
     * @throws PEPFetchException the PEP fetch exception
     */
    List<ItemPrimaryHierarchyVO> getIPHCategoriesFromAdseMerchandiseHierarchy(String orinNumber)
            throws PEPFetchException ;

    /**
     * Gets the IPH categories from adse pet catalog.
     *
     * @param orinNumber the orin number
     * @return the IPH categories from adse pet catalog
     * @throws PEPFetchException the PEP fetch exception
     */
    List<ItemPrimaryHierarchyVO> getIPHCategoriesFromADSEPetCatalog(
        String orinNumber) throws PEPFetchException;


    /**
     * Gets the omni channel brand list.
     *
     * @param orinNumber the orin number
     * @param supplierId the supplier id
     * @return the omni channel brand list
     * @throws PEPFetchException the PEP fetch exception
     */
    public List<OmniChannelBrandVO> getOmniChannelBrandList(String orinNumber, String supplierId)
            throws PEPFetchException ;


    /**
     * Gets the pet attribute details.
     *
     * @param categoryId the category id
     * @return the pet attribute details
     * @throws PEPFetchException the PEP fetch exception
     */
    List<PetAttributeVO> getPetAttributeDetails(String categoryId)
            throws PEPFetchException;


    /**
     * Gets the pet attribute details.
     *
     * @param categoryId the category id
     * @param orinNumber the orin number
     * @return the pet attribute details
     * @throws PEPFetchException the PEP fetch exception
     */
    List<PetAttributeVO> getPetAttributeDetails(String categoryId,
        String orinNumber) throws PEPFetchException;


    /**
     * Gets the pet blue martini attributes details.
     *
     * @param categoryId the category id
     * @param orinNumber the orin number
     * @return the pet blue martini attributes details
     * @throws PEPFetchException the PEP fetch exception
     */
    public List<BlueMartiniAttributesVO> getPetBlueMartiniAttributesDetails(String categoryId,String orinNumber)    throws PEPFetchException;


    /**
     * Gets the product info from adse.
     *
     * @param orinNumber the orin number
     * @return the product info from adse
     * @throws PEPFetchException the PEP fetch exception
     */
    ProductDetailsVO getProductInfoFromADSE(String orinNumber)throws PEPFetchException;


    /**
     * Gets the sku attributes from adse.
     *
     * @param skuOrinNumber the sku orin number
     * @return the sku attributes from adse
     * @throws PEPFetchException the PEP fetch exception
     */
    SkuAttributesVO getSkuAttributesFromADSE(String skuOrinNumber) throws PEPFetchException;


    /**
     * Gets the skus from adse.
     *
     * @param orinNumbe the orin numbe
     * @return the skus from adse
     * @throws PEPFetchException the PEP fetch exception
     */
    List<ChildSkuVO>   getSkusFromADSE(String orinNumbe)  throws PEPFetchException;


    /**
     * Gets the style and its child from adse.
     *
     * @param roleName
     * @param orinNumber the orin number
     * @return the style and its child from adse
     * @throws PEPFetchException the PEP fetch exception
     */

    List<PetsFound> getStyleAndItsChildFromADSE(String roleName,
        String orinNumber) throws PEPFetchException;


    /**
     * Gets the style attributes from adse.
     *
     * @param orinNumber the orin number
     * @return the style attributes from adse
     * @throws PEPFetchException the PEP fetch exception
     */
    GlobalAttributesVO getStyleAttributesFromADSE(String orinNumber)  throws PEPFetchException;

    /**
     * Gets the style color attributes from adse.
     *
     * @param styleColorOrinNumber the style color orin number
     * @return the style color attributes from adse
     * @throws PEPFetchException the PEP fetch exception
     */
    ColorAttributesVO getStyleColorAttributesFromADSE(String styleColorOrinNumber)  throws PEPFetchException;



    /**
     * Gets the style info from adse.
     *
     * @param orinNumber the orin number
     * @return the style info from adse
     * @throws PEPFetchException the PEP fetch exception
     */
    StyleInformationVO getStyleInfoFromADSE(String orinNumber)throws PEPFetchException;

    /**
     * Relese locked pet.
     *
     * @param orin the orin
     * @param pepUserID the pep user id
     * @param pepFunction the pep function
     * @return true, if successful
     * @throws PEPPersistencyException the PEP persistency exception
     */
    boolean releseLockedPet(String orin, String pepUserID,String pepFunction)throws PEPPersistencyException;  

    /**
     * Method to get the Copy attribute details from database.
     *    
     * @param orin String   
     * @return copyAttributeVO CopyAttributeVO
     * 
     * Method added For PIM Phase 2 - Regular Item Copy Attribute
     * Date: 05/16/2016
     * Added By: Cognizant
     */
    CopyAttributeVO fetchCopyAttributes(String orin) throws PEPFetchException;  


	 /**
      * Method to retrieve group information
      * @param groupId
      * @return
      * @throws PEPFetchException
      */
    public StyleInformationVO getGroupingInformation(String groupId) throws PEPFetchException;
    
    /**
     * This method retrieves Group Details
     * @param groupId
     * @return
     * @throws PEPFetchException
     */
    public ProductDetailsVO getGroupingDetails(String groupId)throws PEPFetchException;
    
    /**
     * This method retrieves Group Copy Attributes
     * @param groupId
     * @return
     * @throws PEPFetchException
     */
    public CopyAttributeVO getGroupingCopyAttributes(String groupId) throws PEPFetchException;
    
    /**
     * This method retrieves department details for group if department id is not null
     * @param styleInformationVO
     * @return
     * @throws PEPFetchException
     */
    public StyleInformationVO getGroupingDepartmentDetails(StyleInformationVO styleInformationVO) throws PEPFetchException;
    
    /**
     * This method retrieves omni-channel brand list for the group
     */
    public List<OmniChannelBrandVO> getGroupingOmniChannelBrand(String groupId);
    
    /**
     * This method populates car brand list for group
     * @param groupId
     * @return
     * @throws PEPFetchException
     */
    public List<CarBrandVO> populateGroupCarBrandList(String groupId);
    
    /**
     * This method retrieves Group Component list
     * @param groupId
     * @return
     * @throws PEPFetchException
     */
    public List<GroupsFound> getGroupingComponents(String groupId);
    
    
    /**
     * This method populates IPH category drop down list
     * @param groupId
     * @return
     * @throws PEPFetchException
     * @author AFUSKJ2 6/17/2016
     */
    public List<ItemPrimaryHierarchyVO> selectedIPHCategorydropdown(String groupId);
    
    
    /**
     * This method populates Grouping Content History list
     * @param groupId
     * @return
     * @throws PEPFetchException
     * @author AFUSKJ2 6/17/2016
     */
    public List<ContentHistoryVO> getGroupContentHistory(String groupId);
    
    
    
    /**
     * Method to get the group copy validation from database.
     *    
     * @param groupId String  
     * @param styleId String
     * @return String
     * 
     * Method added For PIM Phase 2 - Regular Item Copy Attribute
     * Date: 05/16/2016
     * Added By: Cognizant
     */
	String getGroupCopyValidation(String groupId, String styleId)
			throws PEPFetchException;


	/**
	 * Method to fetch Group Global Attribute
	 * 
	 * @param groupingId
	 * @return GlobalAttributesVO
	 */
	GlobalAttributesVO getGroupGlobalAttribute(String groupingId);


	/**
	 * @param petCopy
	 * @return
	 * @throws PEPFetchException
	 */
	RegularPetCopy getRegularCopyValidation(RegularPetCopy petCopy)
			throws PEPFetchException;
}
