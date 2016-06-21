
package com.belk.pep.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
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
import com.belk.pep.vo.SkuAttributesVO;
import com.belk.pep.vo.StyleColorFamilyVO;
import com.belk.pep.vo.StyleInformationVO;

/**
 * The Interface ContentService.
 */
public interface ContentService {


    /**
     * Gets the cards brand list.
     *
     * @param orinNumber the orin number
     * @param supplierId the supplier id
     * @return the cards brand list
     * @throws PEPServiceException the PEP service exception
     */
    public List<CarBrandVO> getCardsBrandList(String orinNumber, String supplierId)
            throws PEPServiceException ;


    List<StyleColorFamilyVO> getColorFamilyDataSet()    throws PEPServiceException;


    /**
     * Gets the content history from adse.
     *
     * @param orinNumber the orin number
     * @return the content history from adse
     * @throws PEPServiceException the PEP service exception
     */
    List<ContentHistoryVO>  getContentHistoryFromADSE(String orinNumber) throws PEPServiceException;

    /**
     * Gets the content managment info from adse.
     *
     * @param orinNumber the orin number
     * @return the content managment info from adse
     * @throws PEPServiceException the PEP service exception
     */
    ContentManagementVO getContentManagmentInfoFromADSE(String orinNumber) throws PEPServiceException;

    /**
     * Gets the copy attributes from adse.
     *
     * @param orinNumber the orin number
     * @return the copy attributes from adse
     * @throws PEPServiceException the PEP service exception
     */
    List<CopyAttributesVO> getCopyAttributesFromADSE(String orinNumber) throws PEPServiceException;


    /**
     * Gets the family categories from iph.
     *
     * @param merchCategoryId the merch category id
     * @return the family categories from iph
     * @throws PEPServiceException the PEP service exception
     */
    List<ItemPrimaryHierarchyVO> getFamilyCategoriesFromIPH(String merchCategoryId)    throws PEPServiceException;


    /**
     * Gets the IPH categories.
     *
     * @return the IPH categories
     * @throws PEPServiceException the PEP service exception
     */
    List<ItemPrimaryHierarchyVO> getIPHCategories() throws PEPServiceException;


    /**
     * Gets the IPH categories from adse merchandise hierarchy.
     *
     * @param orinNumber the orin number
     * @return the IPH categories from adse merchandise hierarchy
     * @throws PEPFetchException the PEP fetch exception
     * @throws PEPServiceException
     */
    List<ItemPrimaryHierarchyVO> getIPHCategoriesFromAdseMerchandiseHierarchy(String orinNumber)
            throws PEPServiceException;


    /**
     * Gets the IPH categories from adse pet catalog.
     *
     * @param orinNumber the orin number
     * @return the IPH categories from adse pet catalog
     * @throws PEPServiceException the PEP service exception
     */
    List<ItemPrimaryHierarchyVO> getIPHCategoriesFromADSEPetCatalog(String orinNumber)
            throws PEPServiceException;



    /**
     * Gets the omni channel brand list.
     *
     * @param orinNumber the orin number
     * @param supplierId the supplier id
     * @return the omni channel brand list
     * @throws PEPServiceException the PEP service exception
     */
    public List<OmniChannelBrandVO> getOmniChannelBrandList(String orinNumber, String supplierId)
            throws PEPServiceException ;


    /**
     * Gets the pet attribute details.
     *
     * @param categoryId the category id
     * @return the pet attribute details
     * @throws PEPFetchException the PEP fetch exception
     */
    List<PetAttributeVO>  getPetAttributeDetails(String categoryId)    throws PEPFetchException;


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
     * @throws PEPServiceException the PEP service exception
     */
    public List<BlueMartiniAttributesVO> getPetBlueMartiniAttributesDetails(String categoryId,String orinNumber)
            throws PEPServiceException;

    /**
     * Gets the product info from adse.
     *
     * @param orinNumber the orin number
     * @return the product info from adse
     * @throws PEPServiceException the PEP service exception
     */
    ProductDetailsVO getProductInfoFromADSE(String orinNumber) throws PEPServiceException;


    /**
     * Gets the sku attributes info from adse.
     *
     * @param skuOrinNumber the sku orin number
     * @return the sku attributes info from adse
     * @throws PEPServiceException the PEP service exception
     */
    SkuAttributesVO getSkuAttributesInfoFromADSE(String skuOrinNumber)     throws PEPServiceException ;


    /**
     * Gets the skus from adse.
     *
     * @param orinNumber the orin number
     * @return the skus from adse
     * @throws PEPServiceException the PEP service exception
     */
    List<ChildSkuVO> getSkusFromADSE(String orinNumber) throws PEPServiceException;


    /**
     * Gets the style and its child from adse.
     *
     * @param orinNumber the orin number
     * @return the style and its child from adse
     * @throws PEPServiceException the PEP service exception
     */
    List<PetsFound> getStyleAndItsChildFromADSE(String orinNumber) throws PEPServiceException;


    List<PetsFound> getStyleAndItsChildFromADSE(String roleName,
        String orinNumber) throws PEPServiceException;

    /**
     * Gets the style attributes from adse.
     *
     * @param orinNumber the orin number
     * @return the style attributes from adse
     * @throws PEPServiceException the PEP service exception
     */
    GlobalAttributesVO getStyleAttributesFromADSE(String orinNumber)  throws PEPServiceException;

    /**
     * Gets the style color attributes from adse.
     *
     * @param styleColorOrinNumber the style color orin number
     * @return the style color attributes from adse
     * @throws PEPServiceException the PEP service exception
     */
    ColorAttributesVO getStyleColorAttributesFromADSE(String styleColorOrinNumber)  throws PEPServiceException;


    /**
     * Gets the style info from adse.
     *
     * @param orinNumber the orin number
     * @return the style info from adse
     * @throws PEPServiceException the PEP service exception
     */
    StyleInformationVO getStyleInfoFromADSE(String orinNumber) throws PEPServiceException;

    
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
    CopyAttributeVO getCopyAttribute(String orin) throws PEPServiceException;
    
    
    /**
     * This method retrieve grouping information
     * @param groupId
     * @return StyleInformationVO
     * @throws PEPServiceException
     * @author AFUSKJ2 6/17/2016
     */
    public StyleInformationVO getGroupingInformation(String groupId) throws PEPServiceException;
    
    
    /**
     * This method retrieves Grouping Details
     * @param groupId
     * @return
     * @throws PEPServiceException
     * @author AFUSKJ2 6/17/2016
     */
    public ProductDetailsVO getGroupingDetails(String groupId)throws PEPServiceException;
    
    /**
     * This method retrieves Grouping Copy Attributes
     * @param groupId
     * @return
     * @throws PEPServiceException
     * @author AFUSKJ2 6/17/2016
     */
    public CopyAttributeVO getGroupingCopyAttributes(String groupId) throws PEPServiceException;
    
    
    /**
     * This method retrieves department details for group if department is not null
     * @param styleInformationVO
     * @return
     * @throws PEPServiceException
     * @author AFUSKJ2 6/17/2016
     */
    public StyleInformationVO getGroupingDepartmentDetails(StyleInformationVO styleInformationVO) throws PEPServiceException;
    
    /**
     * This method retrieves omni-channel brand list for group
     * @param groupId
     * @return
     * @throws PEPServiceException
     * @author AFUSKJ2 6/17/2016
     */
    public List<OmniChannelBrandVO> getGroupingOmniChannelBrand(String groupId) throws PEPServiceException;
    
    /**
     * This method populates car brand list for group
     * @param groupId
     * @return
     * @throws PEPServiceException
     * @author AFUSKJ2 6/17/2016
     */
    public List<CarBrandVO> populateGroupCarBrandList(String groupId) throws PEPServiceException;
    
    /**
     * This method populates group component list
     * @param groupId
     * @return
     * @throws PEPServiceException
     * @author AFUSKJ2 6/17/2016
     * 
     */
    public List<GroupsFound> getGroupingComponents(String groupId) throws PEPServiceException;
    
    /**
     * This method populates grouping content history list
     * @param groupId
     * @return
     * @throws PEPServiceException
     * @author AFUSKJ2 6/17/2016
     */
    public List<ContentHistoryVO> getGroupContentHistory(String groupId) throws PEPServiceException;
    
    /**
     * This method populates IPH Category drop down list for group
     * @param groupId
     * @return
     * @throws PEPServiceException
     * @author AFUSKJ2 6/17/2016s
     */
    public List<ItemPrimaryHierarchyVO> populateIPHCategorydropdown(String groupId, String groupType) throws PEPServiceException;
    
    /**
     * This method populates IPH Category drop down list for group
     * @param groupId
     * @return
     * @throws PEPServiceException
     * @author AFUSKJ2 6/17/2016s
     */
    public List<ItemPrimaryHierarchyVO> selectedIPHCategorydropdown(String groupId) throws PEPServiceException;
  
    /**
     * This method populates Grouping Specific attributes
     * @param groupId
     * @return
     * @throws PEPServiceException
     * @author AFUSKJ2 6/17/2016
     */
    public GroupingVO getGroupingSpecificAttributes(String groupId) throws PEPServiceException;
    
    /**
     * This method saves grouping data
     * @param createContentWebServiceReq
     * @return
     */
    public String createGroupContentWebService(final JSONObject jsonContentUpdateColor) throws MalformedURLException, ClassCastException, 
	IOException, JSONException;
    
    /**
     * Method to get the group copy validation from database.
     *    
     * @param groupId String  
     * @param styleId String
     * @return String
     * 
     * Method added For PIM Phase 2 - Group Content
     * Date: 06/18/2016
     * Added By: Cognizant
     */
	String getGroupCopyValidation(String groupId, String styleId)
			throws PEPServiceException;


	/**
     * Method to get the call copy content web service.
     *    
     * @param groupId String  
     * @param styleId String
     * @return String
     * @throws PEPServiceException
     * 
     * Method added For PIM Phase 2 - Group Content
     * Date: 06/18/2016
     * Added By: Cognizant
     */
	String callCopyOrinContentService(String groupId, String styleId,
			String updatedBy) throws PEPServiceException;


	/**
     * Method to get the call update content status web service.
     *    
     * @param groupId String  
     * @param groupType String
     * @param overallStatus String
     * @param updatedBy String
     * @return String
     * @throws PEPServiceException
     * 
     * Method added For PIM Phase 2 - Group Content
     * Date: 06/18/2016
     * Added By: Cognizant
     */
	String callUpdateContentStatusService(String groupId, String groupType,
			String overallStatus, String updatedBy) throws PEPServiceException;
}