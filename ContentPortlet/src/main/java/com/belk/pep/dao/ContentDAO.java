
package com.belk.pep.dao;

import java.util.List;

import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.model.PetsFound;
import com.belk.pep.vo.BlueMartiniAttributesVO;
import com.belk.pep.vo.CarBrandVO;
import com.belk.pep.vo.ChildSkuVO;
import com.belk.pep.vo.ColorAttributesVO;
import com.belk.pep.vo.ContentHistoryVO;
import com.belk.pep.vo.ContentManagementVO;
import com.belk.pep.vo.CopyAttributesVO;
import com.belk.pep.vo.GlobalAttributesVO;
import com.belk.pep.vo.ItemPrimaryHierarchyVO;
import com.belk.pep.vo.OmniChannelBrandVO;
import com.belk.pep.vo.PetAttributeVO;
import com.belk.pep.vo.ProductDetailsVO;
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
    List<ItemPrimaryHierarchyVO> getIPHCategories(String orinNumber)
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
     * @param orinNumber the orin number
     * @return the style and its child from adse
     * @throws PEPFetchException the PEP fetch exception
     */
    List<PetsFound>  getStyleAndItsChildFromADSE(String orinNumber) throws  PEPFetchException;


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

}
