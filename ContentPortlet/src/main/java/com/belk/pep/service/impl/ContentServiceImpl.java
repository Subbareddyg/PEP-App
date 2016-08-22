package com.belk.pep.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.belk.pep.constants.ContentScreenConstants;
import com.belk.pep.dao.ContentDAO;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.model.GroupsFound;
import com.belk.pep.model.PetsFound;
import com.belk.pep.service.ContentService;
import com.belk.pep.util.PropertiesFileLoader;
import com.belk.pep.vo.BlueMartiniAttributesVO;
import com.belk.pep.vo.CarBrandVO;
import com.belk.pep.vo.ChildSkuVO;
import com.belk.pep.vo.ColorAttributesVO;
import com.belk.pep.vo.ContentHistoryVO;
import com.belk.pep.vo.ContentManagementVO;
import com.belk.pep.vo.CopyAttributeVO;
import com.belk.pep.vo.CopyAttributesVO;
import com.belk.pep.vo.GlobalAttributesVO;
import com.belk.pep.vo.ItemPrimaryHierarchyVO;
import com.belk.pep.vo.OmniChannelBrandVO;
import com.belk.pep.vo.PetAttributeVO;
import com.belk.pep.vo.ProductDetailsVO;
import com.belk.pep.vo.RegularPetCopy;
import com.belk.pep.vo.SkuAttributesVO;
import com.belk.pep.vo.StyleColorFamilyVO;
import com.belk.pep.vo.StyleInformationVO;

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
            LOGGER.log(Level.INFO, fetchException.getMessage());
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
            LOGGER.log(Level.INFO, fetchException.getMessage());
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
            LOGGER.log(Level.INFO, fetchException.getMessage());

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
            LOGGER.log(Level.INFO, fetchException.getMessage());

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
            LOGGER.log(Level.INFO, fetchException.getMessage());
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
            LOGGER.log(Level.INFO, fetchException.getMessage());
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
            LOGGER.log(Level.INFO, fetchException.getMessage());
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
            LOGGER.log(Level.INFO, fetchException.getMessage());
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
            LOGGER.log(Level.INFO, fetchException.getMessage());
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
            LOGGER.log(Level.INFO, fetchException.getMessage());
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
            LOGGER.log(Level.INFO, fetchException.getMessage());

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
            LOGGER.log(Level.INFO, fetchException.getMessage());
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
            LOGGER.log(Level.INFO, fetchException.getMessage());

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
            LOGGER.log(Level.INFO, fetchException.getMessage());

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
            LOGGER.log(Level.INFO, fetchException.getMessage());

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
            LOGGER.log(Level.INFO, fetchException.getMessage());
            throw new PEPServiceException(fetchException);

        }

        return styleAttributes;
    }

    
    /* (non-Javadoc)
     * @see com.belk.pep.service.ContentService#getGroupGlobalAttribute(java.lang.String)
     */
    @Override
    public  GlobalAttributesVO getGroupGlobalAttribute(String groupingId)
            {

        LOGGER.info("****start of  getGroupGolbalAttribute  method****");

        GlobalAttributesVO globalAttribute=null;
        	globalAttribute = contentDAO.getGroupGlobalAttribute(groupingId);
        
        LOGGER.info("****End of  getGroupGolbalAttribute  method****");
        return globalAttribute;
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
            LOGGER.log(Level.INFO, fetchException.getMessage());
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
            LOGGER.log(Level.INFO, fetchException.getMessage());
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
            
            LOGGER.info("Exception occurred at the Service DAO Layer");
            throw e;
        }
            
        return isPetReleased;
    } 

    
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
    @Override
    public CopyAttributeVO getCopyAttribute(String orin)
            throws PEPServiceException {

        LOGGER.info("***Entering getCopyAttribute() method.");

        CopyAttributeVO copyAttributeVO = null;
        try {
            copyAttributeVO = contentDAO.fetchCopyAttributes(orin);            
        }
        catch (final PEPFetchException fetchException) {
            LOGGER.error("Exception in getCopyAttribute() method. -- " + fetchException.getMessage());
            throw new PEPServiceException(fetchException.getMessage());
        }
        LOGGER.info("***Exiting getCopyAttribute() method.");
        return copyAttributeVO;
    }
    
    /**
     * This method retrieves Grouping Information 
     * @return StyleInformationVO
     * @param groupId
     * @author AFUSKJ2 6/17/2016
     */
	@Override
	public StyleInformationVO getGroupingInformation(String groupId)
			throws PEPServiceException {
		StyleInformationVO styleinformationVO = null;
		try{
			styleinformationVO = contentDAO.getGroupingInformation(groupId);
		}catch (PEPFetchException e) {
			LOGGER.error("Error in Service::: "+e.getMessage());
			throw new PEPServiceException();
		}
		
		return styleinformationVO;
	}
	
	/**
	 * This method retrieves Group Details
	 * @return ProductDetailsVO
	 * @param groupId
	 * @author AFUSKJ2 6/17/2016
	 */
	@Override
	public ProductDetailsVO getGroupingDetails(String groupId) throws PEPServiceException{
		ProductDetailsVO productDetailsVO = null;
		LOGGER.info("ContentServiceImpl :getGroupingDetails :start");
		try{
			productDetailsVO = contentDAO.getGroupingDetails(groupId);
		}catch (PEPFetchException e) {
			LOGGER.error("ContentServiceImpl :getGroupingDetails::: "+e);
			throw new PEPServiceException(e);
		}
		LOGGER.info("ContentServiceImpl :getGroupingDetails :end");
		return productDetailsVO;
	}
	
	/**
	 * This method populates grouping Copy Attributes section data
	 * @param groupId
	 * @return
	 * @throws PEPServiceException
	 * @author AFUSKJ2 6/17/2016
	 */
	@Override
	public CopyAttributeVO getGroupingCopyAttributes(String groupId)throws PEPServiceException {
		LOGGER.info("ContentServiceImpl :getGroupingCopyAttributes :start");
		CopyAttributeVO copyAttributeVO = null;
		
		try{
			copyAttributeVO = contentDAO.getGroupingCopyAttributes(groupId);
		}catch (PEPFetchException e) {
			LOGGER.error("ContentServiceImpl :getGroupingCopyAttributes::::: "+e);
			throw new PEPServiceException(e);
		}
		LOGGER.info("ContentServiceImpl :getGroupingCopyAttributes :end");
		return copyAttributeVO;
	}
	
	/**
	 * This method retrieves department details for the group
	 * @return StyleInformationVO
	 * @param styleInformationVO
	 * @author AFUSKJ2 6/17/2016
	 */
	@Override
	public StyleInformationVO getGroupingDepartmentDetails(StyleInformationVO styleInformationVO)throws PEPServiceException	{
		LOGGER.info("ContenServiceImpl :getGroupingDepartmentDetails :start");
		StyleInformationVO styleInfoVO =null;
		try{
			styleInfoVO =  contentDAO.getGroupingDepartmentDetails(styleInformationVO);
		}catch (PEPFetchException e) {
			LOGGER.error("ContentServiceImpl : Error in Service getGroupingDepartmentDetails:::: "+e);
			throw new PEPServiceException(e);
		}
		LOGGER.info("ContentServiceImpl :getGroupingDepartmentDetails :end");
		return styleInfoVO;
		
	}

	/**
	 * This method retrieves omni-channel brand list for group
	 * @return List<OmniChannelBrandVo>
	 * @param groupId
	 * @author AFUSKJ2 6/17/2016
	 */
	@Override
	public List<OmniChannelBrandVO> getGroupingOmniChannelBrand(String groupId){
		LOGGER.info("ContentServiceImpl :getGroupingOmniChannelBrand :start");
		List<OmniChannelBrandVO> omniChannelBrandVo = null;
		omniChannelBrandVo= contentDAO.getGroupingOmniChannelBrand(groupId);		
		LOGGER.info("ContentServiceImpl :getGroupingOmniChannelBrand :end");
		return omniChannelBrandVo;
	}
	
	/**
	 * This method populates CAR Brand list for groups
	 * @return List<CarBrandVO>
	 * @param groupId
	 * @author AFUSKJ2 6/17/2016
	 */
	@Override
	public List<CarBrandVO> populateGroupCarBrandList(String groupId){
		LOGGER.info("ContentServiceImpl :populateGroupCarBrandList :end");
		List<CarBrandVO> carBrandVO =null;
		carBrandVO= contentDAO.populateGroupCarBrandList(groupId);
		
		LOGGER.info("ContentServiceImpl :populateGroupCarBrandList :end");
		return carBrandVO;
	}
	
	/**
	 * This method populates group component list
	 * @return List<GroupsFound>
	 * @param groupId
	 * @author AFUSKJ2 6/17/2016
	 */
	@Override
	public List<GroupsFound> getGroupingComponents(String groupId) {
		LOGGER.info("ContentServiceImpl :getGroupingComponents :start");
		List<GroupsFound> grpList = new ArrayList<GroupsFound>();
		grpList = contentDAO.getGroupingComponents(groupId);		
		LOGGER.info("ContentServiceImpl :getGroupingComponents :end");
		return grpList;
	}
	
	/**
	 * This method populates IPH category drop down list for group
	 * @param groupId
	 * @return
	 * @throws PEPServiceException
	 * @author AFUSKJ2 6/17/2016
	 */
	@Override
	public List<ItemPrimaryHierarchyVO> populateIPHCategorydropdown(String groupId, String groupType) throws PEPServiceException {
		LOGGER.info("ContentServiceImpl :populateIPHCategorydropdown :start");
		List<ItemPrimaryHierarchyVO> iphVO = null;
		try{
			iphVO= contentDAO.getIPHCategories(groupId, groupType);
		}catch (PEPFetchException e) {
			LOGGER.error("ContentServiceImpl : Error in service populateIPHCategorydropdown::: "+e);
			
			throw new PEPServiceException(e);
		}
		LOGGER.info("ContentServiceImpl :populateIPHCategorydropdown :end");
		return iphVO;
	}
	
	/**
	 * This method populates grouping content history list
	 * @param groupId
	 * @return
	 * @throws PEPServiceException
	 * @author AFUSKJ2 6/17/2016
	 */
	@Override
	public List<ContentHistoryVO> getGroupContentHistory(String groupId) {
		LOGGER.info("ContentServiceImpl :getGroupContentHistory :start");
			List<ContentHistoryVO>  contentHistoryVO =null;
			contentHistoryVO= contentDAO.getGroupContentHistory(groupId);
			LOGGER.info("ContentServiceImpl :getGroupContentHistory :end");
			return contentHistoryVO;
	}
	



	/**
	 * This method is used to call service to update Group Content.
	 * 
	 * @param jsonStyleSpliColor
	 * @return responseMsg
	 * @throws PEPServiceException 
	 * @throws IOException 
	 * @throws Exception
	 * @throws PEPFetchException
	 */
	public final String createGroupContentWebService(final JSONObject jsonContentUpdateColor) throws  PEPServiceException, IOException {
		LOGGER.info("ContentServiceImpl :Entering createGroupContentWebService-->.");
		String responseMsg = "";
		BufferedReader responseBuffer=null;
		HttpURLConnection httpConnection = null;
		try {
	        final Properties prop =   PropertiesFileLoader.getPropertyLoader(ContentScreenConstants.MESS_PROP);
			final String serviceURL = prop.getProperty(ContentScreenConstants.GROUP_CONTENT_ITEMS_SAVE);
			
			LOGGER.info("Add update content ServiceURL-->" + serviceURL);

			URL targetUrl = new URL(serviceURL);
			httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod(prop.getProperty(ContentScreenConstants.SERVICE_REQUEST_METHOD));
			httpConnection.setRequestProperty(prop.getProperty(ContentScreenConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
					prop.getProperty(ContentScreenConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));
			if(LOGGER.isDebugEnabled())
			LOGGER.debug("Method-->"+prop.getProperty(ContentScreenConstants.SERVICE_REQUEST_METHOD));

			LOGGER.info("createGroupContentWebService Service::Json Array-->" + jsonContentUpdateColor.toString());

			String input = jsonContentUpdateColor.toString();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes(ContentScreenConstants.DEFAULT_CHARSET));
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(),ContentScreenConstants.DEFAULT_CHARSET));
			String output;
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("call createGroupContentWebService Service Output-->" + output);
				responseMsg = output;

			}

		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new PEPServiceException(e);
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new PEPServiceException(e);
		}catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new PEPServiceException(e);
		}catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new PEPServiceException(e);
		}finally {
			if(null != httpConnection){
				httpConnection.disconnect();
			}
			if(responseBuffer!=null)
				responseBuffer.close();
		}
		LOGGER.info("Exiting createGroupContentWebService-->" + responseMsg);
		return responseMsg;
	}
	
	
	/**
     * Method to get the group copy validation from database.
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
    @Override
	public String getGroupCopyValidation(final String groupId,
			final String styleId) throws PEPServiceException {

		LOGGER.info("***Entering getGroupCopyValidation() method.");

		String message = ContentScreenConstants.EMPTY;
		try {
			message = contentDAO.getGroupCopyValidation(groupId, styleId);
		} catch (final PEPFetchException fetchException) {
			LOGGER.error("Exception in getGroupCopyValidation() method. -- "
					+ fetchException);
			throw new PEPServiceException(fetchException.getMessage());
		}
		LOGGER.info("***Exiting getGroupCopyValidation() method.");
		return message;
	}
    
    /**
     * Method to get the call copy content web service.
     *    
     * @param groupId String  
     * @param styleId String
     * @param updatedBy String
     * @return String
     * @throws PEPServiceException
     * 
     * Method added For PIM Phase 2 - Group Content
     * Date: 06/18/2016
     * Added By: Cognizant
     */
    @Override
	public final String callCopyOrinContentService(final String groupId,
			final String styleId, final String updatedBy)
			throws PEPServiceException {
		LOGGER.info("Entering callCopyOrinContentService.");
		String responseMsg = ContentScreenConstants.EMPTY;
		String responseMsgCode = ContentScreenConstants.EMPTY;
try{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(ContentScreenConstants.FROM_MDMID, styleId);
		jsonObj.put(ContentScreenConstants.TO_MDMID, groupId);
		jsonObj.put(ContentScreenConstants.MODIFIED_BY, updatedBy);

		/** Calling Web Service **/
		String resMsg;
	
			resMsg = callContentCopyService(jsonObj);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Copy Content Service message-->" + resMsg);
		}
		Properties prop = PropertiesFileLoader
				.getPropertyLoader(ContentScreenConstants.MESS_PROP);

		/** Extract Service message **/
		JSONObject jsonObjectRes = null;
		if (null != resMsg && !(ContentScreenConstants.EMPTY).equals(resMsg)) {
			jsonObjectRes = new JSONObject(resMsg);
		}
		if (null != jsonObjectRes) {
			responseMsgCode = jsonObjectRes
					.getString(ContentScreenConstants.MSG_CODE);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("responseMsgCode-->" + responseMsgCode);
		}

		if (null != responseMsgCode
				&& responseMsgCode.equals(ContentScreenConstants.SUCCESS_CODE)) {
			responseMsg = prop
					.getProperty(ContentScreenConstants.COPY_CONTENT_SUCCESS_MESSAGE);
		} else {
			responseMsg = prop
					.getProperty(ContentScreenConstants.COPY_CONTENT_FAILURE_MESSAGE);
		}
}catch(IOException e){
	LOGGER.error("Exception in callCopyOrinContentService:IOException "+e);
	throw new PEPServiceException(e);
}catch(JSONException e){
	LOGGER.error("Exception in callCopyOrinContentService:JSONException "+e);
	throw new PEPServiceException(e);
}
		LOGGER.info("Exiting callCopyOrinContentService.");
		return responseMsg;
	}

	/**
	 * Method to get the call copy content web service.
	 * 
	 * @param jsonObject
	 *            JSONObject
	 * @return String
	 * @throws PEPServiceException
	 * 
	 *             Method added For PIM Phase 2 - Group Content Date: 06/18/2016
	 *             Added By: Cognizant
	 * @throws IOException 
	 */
	private String callContentCopyService(final JSONObject jsonObject)
			throws PEPServiceException, IOException {
		LOGGER.info("Entering callContentCopyService.");

		String responseMsg = ContentScreenConstants.EMPTY;
		BufferedReader responseBuffer = null;
		HttpURLConnection httpConnection = null;
		try {
			final Properties prop = PropertiesFileLoader
					.getPropertyLoader(ContentScreenConstants.MESS_PROP);
			final String serviceURL = prop
					.getProperty(ContentScreenConstants.COPY_CONTENT_SERVICE_URL);
			LOGGER.info("Copy Content ServiceURL-->" + serviceURL);

			final URL targetUrl = new URL(serviceURL);
			httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection
					.setRequestMethod(prop
							.getProperty(ContentScreenConstants.SERVICE_REQUEST_METHOD));
			httpConnection
					.setRequestProperty(
							prop.getProperty(ContentScreenConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
							prop.getProperty(ContentScreenConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));

			LOGGER.info("callContentCopyService Service::Json Array-->"
					+ jsonObject.toString());

			String input = jsonObject.toString();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input
					.getBytes(ContentScreenConstants.DEFAULT_CHARSET));
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(
					httpConnection.getInputStream(),
					ContentScreenConstants.DEFAULT_CHARSET));
			String output;
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("Copy Content Service Output-->" + output);

				responseMsg = output;
			}

		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new PEPServiceException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new PEPServiceException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new PEPServiceException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new PEPServiceException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("inside Exception-->" + e);
			throw new PEPServiceException(e.getMessage());
		} finally {
			if (null != httpConnection) {
				httpConnection.disconnect();
			}
			if (responseBuffer != null) {
					responseBuffer.close();				
			}
		}
		LOGGER.info("Exiting callContentCopyService");
		return responseMsg;
	}
	
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
	 * @throws  
     */
    @Override
	public final String callUpdateContentStatusService(final String groupId,
			final String groupType,
			final String overallStatus, final String updatedBy)
			throws PEPServiceException {
		LOGGER.info("Entering callUpdateContentStatusService.");
		String responseMsg = ContentScreenConstants.EMPTY;
		String responseMsgCode = ContentScreenConstants.EMPTY;
		try{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(ContentScreenConstants.GROUP_ID, groupId);
		jsonObj.put(ContentScreenConstants.GROUP_TYPE, groupType);
		jsonObj.put(ContentScreenConstants.CONTENT_STATUS_PARAM, ContentScreenConstants.CONTENT_STATUS);
		jsonObj.put(ContentScreenConstants.MODIFIED_BY, updatedBy);
		jsonObj.put(ContentScreenConstants.OVERALL_STATUS_PARAM, overallStatus);

		/** Calling Web Service **/
		String resMsg = callUpdateContentStatusService(jsonObj);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Update Content Status Service message-->" + resMsg);
		}
		Properties prop = PropertiesFileLoader
				.getPropertyLoader(ContentScreenConstants.MESS_PROP);

		/** Extract Service message **/
		JSONObject jsonObjectRes = null;
		if (null != resMsg && !(ContentScreenConstants.EMPTY).equals(resMsg)) {
			jsonObjectRes = new JSONObject(resMsg);
		}
		if (null != jsonObjectRes) {
			responseMsgCode = jsonObjectRes
					.getString(ContentScreenConstants.MSG_CODE);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("responseMsgCode-->" + responseMsgCode);
		}

		if (null != responseMsgCode
				&& responseMsgCode.equals(ContentScreenConstants.SUCCESS_CODE)) {
			responseMsg = prop
					.getProperty(ContentScreenConstants.UPDATE_CONTENT_STATUS_SUCCESS_MESSAGE);
		} else {
			responseMsg = prop
					.getProperty(ContentScreenConstants.UPDATE_CONTENT_STATUS_FAILURE_MESSAGE);
		}
		}catch(IOException e){
			LOGGER.error("Exception in callUpdateContentStatusService:IOException :"+e);
			throw new PEPServiceException(e);
			
		}catch(JSONException e){
			LOGGER.error("Exception in callUpdateContentStatusService:JSONException :"+e);
			throw new PEPServiceException(e);
			
		}
		LOGGER.info("Exiting callUpdateContentStatusService.");
		return responseMsg;
	}

	/**
	 * Method to get the call update content status web service.
	 * 
	 * @param jsonObject
	 *            JSONObject
	 * @return String
	 * @throws PEPServiceException
	 * 
	 *             Method added For PIM Phase 2 - Group Content Date: 06/18/2016
	 *             Added By: Cognizant
	 * @throws IOException 
	 */
	private String callUpdateContentStatusService(final JSONObject jsonObject)
			throws PEPServiceException, IOException {
		LOGGER.info("Entering callUpdateContentStatusService.");

		String responseMsg = ContentScreenConstants.EMPTY;
		BufferedReader responseBuffer = null;
		HttpURLConnection httpConnection = null;
		try {
			final Properties prop = PropertiesFileLoader
					.getPropertyLoader(ContentScreenConstants.MESS_PROP);
			final String serviceURL = prop
					.getProperty(ContentScreenConstants.UPDATE_CONTENT_STATUS_SERVICE_URL);
			LOGGER.info("Update Content Status ServiceURL-->" + serviceURL);

			final URL targetUrl = new URL(serviceURL);
			httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection
					.setRequestMethod(prop
							.getProperty(ContentScreenConstants.SERVICE_REQUEST_METHOD));
			httpConnection
					.setRequestProperty(
							prop.getProperty(ContentScreenConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
							prop.getProperty(ContentScreenConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));

			LOGGER.info("callUpdateContentStatusService Service::Json Array-->"
					+ jsonObject.toString());

			String input = jsonObject.toString();

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input
					.getBytes(ContentScreenConstants.DEFAULT_CHARSET));
			outputStream.flush();

			responseBuffer = new BufferedReader(new InputStreamReader(
					httpConnection.getInputStream(),
					ContentScreenConstants.DEFAULT_CHARSET));
			String output;
			while ((output = responseBuffer.readLine()) != null) {
				LOGGER.info("Update Content Status Service Output-->" + output);

				responseMsg = output;
			}

		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new PEPServiceException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new PEPServiceException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new PEPServiceException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new PEPServiceException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("inside Exception-->" + e);
			throw new PEPServiceException(e.getMessage());
		} finally {
			if (null != httpConnection) {
				httpConnection.disconnect();
			}
			if (responseBuffer != null) {
					responseBuffer.close();				
			}
		}
		LOGGER.info("Exiting callUpdateContentStatusService");
		return responseMsg;
	}
	
	
	/**
	 * This method populates IPH category drop down list for group
	 * @param groupId
	 * @return
	 * @throws PEPServiceException
	 * @author AFUSKJ2 6/17/2016
	 */
	@Override
	public List<ItemPrimaryHierarchyVO> selectedIPHCategorydropdown(String groupId){
		LOGGER.info("ContentServiceImpl: selectedIPHCategorydropdown: starts");
			List<ItemPrimaryHierarchyVO> iphVo = null;
			iphVo=  contentDAO.selectedIPHCategorydropdown(groupId);
			LOGGER.info("ContentServiceImpl: selectedIPHCategorydropdown: end");
			return iphVo;
		
	}
	

	
	
	/**
	 * Method to get the call copy content web service.
	 * 
	 * @param jsonObject
	 *            String
	 * @return String
	 * @throws PEPServiceException
	 * 
	 *             Method added For PIM Phase 2 - Group Content Date: 06/18/2016
	 *             Added By: Cognizant
	 * @throws IOException 
	 */
	@Override
	public String callRegularContentCopyService(final String jsonObject)
			throws PEPServiceException, IOException {
		LOGGER.info("Entering callRegularContentCopyService.");

		String responseMsg = ContentScreenConstants.EMPTY;
		BufferedReader responseBuffer = null;
		HttpURLConnection httpConnection = null;
		try {
			final Properties prop = PropertiesFileLoader
					.getPropertyLoader(ContentScreenConstants.MESS_PROP);
			final String serviceURL = prop
					.getProperty(ContentScreenConstants.REG_COPY_CONTENT_SERVICE_URL);
			LOGGER.info("Copy Content ServiceURL-->" + serviceURL);

			final URL targetUrl = new URL(serviceURL);
			httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection
					.setRequestMethod(prop
							.getProperty(ContentScreenConstants.SERVICE_REQUEST_METHOD));
			httpConnection
					.setRequestProperty(
							prop.getProperty(ContentScreenConstants.SERVICE_REQUEST_PROPERTY_CONTENT_TYPE),
							prop.getProperty(ContentScreenConstants.SERVICE_REQUEST_PROPERTY_APPLICATION_TYPE));

			LOGGER.info("callContentCopyService Service::Json Array-->"
					+ jsonObject);

				OutputStream outputStream = httpConnection.getOutputStream();
				outputStream.write(jsonObject
						.getBytes(ContentScreenConstants.DEFAULT_CHARSET));
				outputStream.flush();
	
				responseBuffer = new BufferedReader(new InputStreamReader(
						httpConnection.getInputStream(),
						ContentScreenConstants.DEFAULT_CHARSET));
				String output;
				while ((output = responseBuffer.readLine()) != null) {
					LOGGER.info("Copy Content Service Output-->" + output);
	
					responseMsg = output;
				}
				
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Copy Content Service message-->" + responseMsg);
				}
	
	
				/** Extract Service message **/
				JSONObject jsonObjectRes = null;
				if (null != responseMsg && !(ContentScreenConstants.EMPTY).equals(responseMsg)) {
					jsonObjectRes = new JSONObject(responseMsg);
				}
				String responseMsgCode="";
				if (null != jsonObjectRes) {
					responseMsgCode = jsonObjectRes
							.getString(ContentScreenConstants.MSG_CODE);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("responseMsgCode-->" + responseMsgCode);
				}
	
				if (null != responseMsgCode
						&& responseMsgCode.equals(ContentScreenConstants.SUCCESS_CODE)) {
					responseMsg = prop
							.getProperty(ContentScreenConstants.REG_COPY_SUCESS_MESSGAE);
				} else {
					responseMsg = jsonObjectRes
					.getString(ContentScreenConstants.DESCRIPTION);
				}
	         

		} catch (MalformedURLException e) {
			LOGGER.error("inside malformedException-->" + e);
			throw new PEPServiceException(e.getMessage());
		} catch (ClassCastException e) {
			LOGGER.error("inside ClassCastException-->" + e);
			throw new PEPServiceException(e.getMessage());
		} catch (IOException e) {
			LOGGER.error("inside IOException-->" + e);
			throw new PEPServiceException(e.getMessage());
		} catch (JSONException e) {
			LOGGER.error("inside JSOnException-->" + e);
			throw new PEPServiceException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("inside Exception-->" + e);
			throw new PEPServiceException(e.getMessage());
		} finally {
			if (null != httpConnection) {
				httpConnection.disconnect();
			}
			if (responseBuffer != null) {
					responseBuffer.close();				
			}
		}
		LOGGER.info("Exiting callRegularContentCopyService : responseMsg -->"+responseMsg);
		return responseMsg;
	}
	
	/**
     * Method to get the copy validation from database.
     *    
     * @param petCopy RegularPetCopy  
     * @return RegularPetCopy
     * @throws PEPServiceException
     * 
     * Method added For PIM Phase 2
     * Added By: Cognizant
     */
    @Override
	public RegularPetCopy getRegularCopyValidation( RegularPetCopy petCopy) throws PEPServiceException {

		LOGGER.info("***Entering getRegularCopyValidation() method.");

		try {
			petCopy = contentDAO.getRegularCopyValidation(petCopy);
		} catch (final PEPFetchException fetchException) {
			LOGGER.error("Exception in getRegularCopyValidation() method. -- "
					+ fetchException);
			throw new PEPServiceException(fetchException.getMessage());
		}
		LOGGER.info("***Exiting getRegularCopyValidation() method.");
		return petCopy;
	}
	

}



