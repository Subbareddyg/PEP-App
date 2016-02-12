package com.belk.pep.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.belk.pep.dao.ContentDAO;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.model.PetsFound;
import com.belk.pep.service.ContentService;
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
import com.belk.pep.exception.checked.PEPPersistencyException;

/**
 * The Class ContentServiceImpl.
 */
public class ContentServiceImpl implements ContentService {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(ContentServiceImpl.class.getName());


    /** The content dao impl. */
    private ContentDAO  contentDAO;


    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getCardsBrandList(java.lang.String, java.lang.String)
     */
    @Override
    public List<CarBrandVO> getCardsBrandList(String orinNumber, String supplierId)
            throws PEPServiceException {
        LOGGER.info("****start of  getCardsBrandList  method****");

        List<CarBrandVO> lstCarBrandVO = null;
        try {
            lstCarBrandVO = contentDAO.getCardsBrandList(orinNumber,supplierId);
            LOGGER.info("****end of  getCardsBrandList  method****");
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());
            throw new PEPServiceException(fetchException.getMessage());
        }
        return lstCarBrandVO;
    }


    /**
     * Gets the color family data set.
     *
     * @return the color family data set
     * @throws PEPServiceException the PEP service exception
     */
    @Override
    public List<StyleColorFamilyVO> getColorFamilyDataSet()
            throws PEPServiceException {

        LOGGER.info("****start of  getColorFamilyDataSet  method****");

        List<StyleColorFamilyVO> styleColorList=null;
        try {
            styleColorList = contentDAO.getColorFamilyDataSet();
            LOGGER.info("****end of  getColorFamilyDataSet  method****");
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());
            throw new PEPServiceException(fetchException.getMessage());
        }

        return styleColorList;
    }


    /**
     * Gets the content dao.
     *
     * @return the content dao
     */
    public ContentDAO getContentDAO() {
        return contentDAO;
    }

    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getContentHistoryFromADSE(java.lang.String)
     */
    @Override
    public List<ContentHistoryVO> getContentHistoryFromADSE(String orinNumber)
            throws PEPServiceException {
        List<ContentHistoryVO> contentHistoryList = null;
        try {
            contentHistoryList = contentDAO.getContentHistoryFromADSE(orinNumber);
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());

        }
        return contentHistoryList;
    }


    /**
     * Gets the content managment info from adse.
     *
     * @param orinNumber the orin number
     * @return the content managment info from adse
     * @throws PEPServiceException the PEP service exception
     */
    @Override
    public ContentManagementVO getContentManagmentInfoFromADSE(String orinNumber)
            throws PEPServiceException {
        ContentManagementVO  contentManagement = null;
        try {
            contentManagement = contentDAO.getContentManagmentInfoFromADSE(orinNumber);
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());

        }

        return contentManagement;

    }




    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getCopyAttributesFromADSE(java.lang.String)
     */
    @Override
    public List<CopyAttributesVO> getCopyAttributesFromADSE(String orinNumber)
            throws PEPServiceException {
        List<CopyAttributesVO>  copyAttributesList=null;

        try {
            copyAttributesList=contentDAO.getCopyAttributesFromADSE(orinNumber);
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());
        }

        return copyAttributesList;
    }



    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getFamilyCategoriesFromIPH(java.lang.String)
     */
    @Override
    public List<ItemPrimaryHierarchyVO> getFamilyCategoriesFromIPH(
        String merchCategoryId) throws PEPServiceException {


        LOGGER.info("****start of  getFamilyCategoriesFromIPH  method****");

        List<ItemPrimaryHierarchyVO> iphList=null;
        try {
            iphList = contentDAO.getFamilyCategoriesFromIPH(merchCategoryId);
            LOGGER.info("****end of  getFamilyCategoriesFromIPH  method****");
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());
            throw new PEPServiceException(fetchException.getMessage());
        }

        return iphList;


    }

    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getIPHCategories()
     */
    @Override
    public List<ItemPrimaryHierarchyVO> getIPHCategories()
            throws PEPServiceException {
        List<ItemPrimaryHierarchyVO>  iphCategoryList=null;

        try {
            iphCategoryList=contentDAO.getIPHCategories();
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());
        }

        return iphCategoryList;
    }



    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getIPHCategoriesFromAdseMerchandiseHierarchy(java.lang.String)
     */
    @Override
    public List<ItemPrimaryHierarchyVO> getIPHCategoriesFromAdseMerchandiseHierarchy(
        String orinNumber) throws PEPServiceException {
        List<ItemPrimaryHierarchyVO>  iphCategoryList=null;

        try {
            iphCategoryList=contentDAO.getIPHCategoriesFromAdseMerchandiseHierarchy(orinNumber);
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());
            throw new PEPServiceException(fetchException);
        }

        return iphCategoryList;
    }


    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getIPHCategoriesFromADSEPetCatalog(java.lang.String)
     */
    @Override
    public List<ItemPrimaryHierarchyVO> getIPHCategoriesFromADSEPetCatalog(
        String orinNumber) throws PEPServiceException {

        List<ItemPrimaryHierarchyVO>  iphCategoryList=null;

        try {
            iphCategoryList=contentDAO.getIPHCategoriesFromADSEPetCatalog(orinNumber);
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());
            throw new PEPServiceException(fetchException);
        }

        return iphCategoryList;
    }



    /**
     * Gets the omni channel brand list.
     *
     * @param orinNumber the orin number
     * @param supplierId the supplier id
     * @return the omni channel brand list
     * @throws PEPServiceException the PEP service exception
     */
    @Override
    public List<OmniChannelBrandVO> getOmniChannelBrandList(String orinNumber, String supplierId)
            throws PEPServiceException {
        LOGGER.info("****start of  getOmniChannelBrandList  method****");

        List<OmniChannelBrandVO> lstOmniChannelBrandVO = null;
        try {
            lstOmniChannelBrandVO = contentDAO.getOmniChannelBrandList(orinNumber,supplierId);

            LOGGER.info("****end of  getOmniChannelBrandList  method****");
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());
            throw new PEPServiceException(fetchException.getMessage());
        }
        return lstOmniChannelBrandVO;

    }


    @Override
    public List<PetAttributeVO> getPetAttributeDetails(String categoryId)
            throws PEPFetchException {
        // TODO Auto-generated method stub
        return null;
    }


    /**
     * Gets the pet attribute details.
     *
     * @param categoryId the category id
     * @return the pet attribute details
     * @throws PEPFetchException the PEP fetch exception
     */
    @Override
    public    List<PetAttributeVO>  getPetAttributeDetails(String categoryId,String orinNumber)
            throws PEPFetchException {

        LOGGER.info("****start of  getPetAttributeDetails  method****");

        List<PetAttributeVO>  petAttributes=null;
        try {
            petAttributes = contentDAO.getPetAttributeDetails(categoryId,orinNumber);
            return petAttributes;
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());

        }

        LOGGER.info("****end of  getPetAttributeDetails  method****");


        return null;

    }


    /**
     * Gets the pet blue martini attributes details.
     *
     * @param categoryId the category id
     * @param orinNumber the orin number
     * @return the pet blue martini attributes details
     * @throws PEPFetchException the PEP fetch exception
     */
    @Override
    public List<BlueMartiniAttributesVO> getPetBlueMartiniAttributesDetails(String categoryId,String orinNumber)
            throws PEPServiceException
            {

        LOGGER.info("****start of  getPetBlueMartiniAttributesDetails  method****");

        List<BlueMartiniAttributesVO>  blueMartiniAttributes=null;
        try {
            blueMartiniAttributes = contentDAO.getPetBlueMartiniAttributesDetails(categoryId, orinNumber);
            return blueMartiniAttributes;
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());
            throw new PEPServiceException(fetchException);
        }

            }


    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getProductInfoFromADSE(java.lang.String)
     */
    @Override
    public ProductDetailsVO getProductInfoFromADSE(String orinNumber)
            throws PEPServiceException {

        LOGGER.info("****start of  getProductInfoFromADSE  method****");

        ProductDetailsVO  productDetails=null;
        try {
            productDetails = contentDAO.getProductInfoFromADSE(orinNumber);
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());

        }

        return productDetails;
    }


    /**
     * Gets the sku attributes info from adse.
     *
     * @param skuOrinNumber the sku orin number
     * @return the sku attributes info from adse
     * @throws PEPServiceException the PEP service exception
     */
    @Override
    public SkuAttributesVO getSkuAttributesInfoFromADSE(String skuOrinNumber)
            throws PEPServiceException {

        LOGGER.info("****start of  getSkuAttributesInfoFromADSE  method****");

        SkuAttributesVO  skuAttributes=null;
        try {
            skuAttributes = contentDAO.getSkuAttributesFromADSE(skuOrinNumber);
        }
        catch (final PEPFetchException fetchException) {
            throw new PEPServiceException(fetchException);

        }
        LOGGER.info("****end of  getSkuAttributesInfoFromADSE  method****");
        return skuAttributes;
    }


    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getSkusFromADSE(java.lang.String)
     */
    @Override
    public List<ChildSkuVO> getSkusFromADSE(String orinNumber)
    {

        List<ChildSkuVO>  skuList = new ArrayList<ChildSkuVO>();
        try {
            skuList = contentDAO.getSkusFromADSE(orinNumber);
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());

        }


        return skuList;



    }


    @Override
    public List<PetsFound> getStyleAndItsChildFromADSE(String orinNumber)
            throws PEPServiceException {
        // TODO Auto-generated method stub
        return null;
    }


    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getStyleAndItsChildFromADSE(java.lang.String)
     */
    @Override
    public List<PetsFound> getStyleAndItsChildFromADSE(String roleName,String orinNumber)
            throws PEPServiceException {

        LOGGER.info("****start of  getStyleAndItsChildFromADSE  method****");

        List<PetsFound> petList = new ArrayList<PetsFound>();
        try {
            petList = contentDAO.getStyleAndItsChildFromADSE(roleName,orinNumber);
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());

            throw  new PEPServiceException(fetchException);

        }

        return petList;
    }

    @Override
    public  GlobalAttributesVO getStyleAttributesFromADSE(String orinNumber)
            throws PEPServiceException {

        LOGGER.info("****start of  getStyleAttributesFromADSE  method****");

        GlobalAttributesVO styleAttributes=null;
        try {
            styleAttributes = contentDAO.getStyleAttributesFromADSE(orinNumber);
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());
            throw new PEPServiceException(fetchException);

        }

        return styleAttributes;
    }



    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getStyleColorAttributesFromADSE(java.lang.String)
     */
    @Override
    public ColorAttributesVO getStyleColorAttributesFromADSE(String styleColorOrinNumber) throws PEPServiceException {

        LOGGER.info("****Start of  getStyleColorAttributesFromADSE  method****");

        ColorAttributesVO colorAttributes=null;
        try {
            colorAttributes=contentDAO.getStyleColorAttributesFromADSE(styleColorOrinNumber);
            LOGGER.info("****End of  getStyleColorAttributesFromADSE  method****");
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());
            throw new PEPServiceException(fetchException);
        }


        return colorAttributes;

    }

    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getStyleInfoFromADSE(java.lang.String)
     */
    @Override
    public StyleInformationVO getStyleInfoFromADSE(String orinNumber)
            throws PEPServiceException {

        LOGGER.info("****start of  getStyleInfoFromADSE  method****");

        StyleInformationVO style=null;
        try {
            style = contentDAO.getStyleInfoFromADSE(orinNumber);
            LOGGER.info("****end of  getStyleInfoFromADSE  method****");
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.log(Level.SEVERE, fetchException.getMessage());
            throw new PEPServiceException(fetchException.getMessage());
        }

        return style;
    }


    /**
     * Sets the content dao.
     *
     * @param contentDAO the new content dao
     */
    public void setContentDAO(ContentDAO contentDAO) {
        this.contentDAO = contentDAO;
    }

    public boolean releseLockedPet(  String orin, String pepUserID,String pepFunction)throws PEPPersistencyException {
        LOGGER.info("releseLockedPet service :: lockPET"); 
        boolean  isPetReleased  = false;
        try {         
            isPetReleased = contentDAO.releseLockedPet(orin,pepUserID,pepFunction);
        } 
        catch (PEPPersistencyException e) {
            
            LOGGER.severe("Exception occurred at the Service DAO Layer");
            throw e;
        }
            
        return isPetReleased;
    } 

}



