package com.belk.pep.delegate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.apache.log4j.Logger;

import com.belk.pep.constants.ContentScreenConstants;
import com.belk.pep.exception.checked.PEPDelegateException;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.model.PetsFound;
import com.belk.pep.service.ContentService;
import com.belk.pep.util.PropertiesFileLoader;
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
import org.json.JSONObject; 


/**
 * The Class ContentDelegate.
 */
public class ContentDelegate {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(ContentDelegate.class.getName());


    /** The content service. */
    private ContentService contentService;


    /**
     * Call iph mapping web service.
     *
     * @param webserviceRequest the webservice request
     * @return the string
     */
    public String callIPHMappingWebService(String webserviceRequest)
    {

        LOGGER.info("start of createIPHMappingWebServiceMapping ");
        String webServiceResponseCode = null;
        String webServiceResponseDescription = null;
        String webServiceResponseStatus = null;
        String responseMsg = null;
        String webServiceResponse = null;
        LOGGER.info("callIPHMappingWebService called");
        LOGGER.info("callIPHMappingWebService JSON Request....."+webserviceRequest);
        final Properties prop =   PropertiesFileLoader.getPropertyLoader(ContentScreenConstants.MESS_PROP);
        final String targetURL =  prop.getProperty(ContentScreenConstants.IPH_MAPPING_WEBSERVICE_URL);

        LOGGER.info("IPHMappingWebService URL = "+targetURL);

        try{

            final URL createWebServiceURL = new URL(targetURL);
            final HttpURLConnection con = (HttpURLConnection)createWebServiceURL.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json;");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Method", "POST");
            final OutputStream os = con.getOutputStream();
            os.write(webserviceRequest.getBytes("UTF-8"));
            os.close();


            final StringBuilder sb = new StringBuilder();
            final int httpResult =con.getResponseCode();

            LOGGER.info("HTTP Result from IPHMappingWebService =  "+httpResult);

            if((httpResult == HttpURLConnection.HTTP_OK) ||
                    (httpResult == HttpURLConnection.HTTP_CREATED) )

            {

                LOGGER.info("HTTP Result from IPHMappingWebService when OK =  "+httpResult);
                final BufferedReader br = new BufferedReader(new   InputStreamReader(con.getInputStream(),"utf-8"));

                String line = null;
                while ((line = br.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                br.close();
                webServiceResponse = sb.toString();
                LOGGER.info("Response from callIPHMappingWebService in string format = "+ webServiceResponse);
                //parse web service response
                final JSONObject jsonResponseObject = new JSONObject(webServiceResponse);

                webServiceResponseCode = jsonResponseObject.getString("code");
                webServiceResponseDescription = jsonResponseObject.getString("message");
                webServiceResponseStatus = jsonResponseObject.getString("status");


                if(webServiceResponseStatus.equals("SUCCESS") && webServiceResponseDescription.equalsIgnoreCase("Pet successfully mapped to IPH")){
                    LOGGER.info("webServiceResponseCode------"+webServiceResponseCode);
                    LOGGER.info("webServiceResponseStatus------"+webServiceResponseStatus);
                    LOGGER.info("webServiceResponseDescription------"+webServiceResponseDescription);
                    responseMsg ="The IPH mapping has been  updated successfully. ";
                }
                else if(webServiceResponseStatus.equalsIgnoreCase("FAIL") && webServiceResponseDescription.equalsIgnoreCase("Category not found in PIM"))
                {
                    LOGGER.info("webServiceResponseCode------"+webServiceResponseCode);
                    LOGGER.info("webServiceResponseStatus------"+webServiceResponseStatus);
                    LOGGER.info("webServiceResponseDescription------"+webServiceResponseDescription);
                    responseMsg= "The IPH mapping cannot be updated,contact the system administrator.";
                }

                else {
                    LOGGER.info("webServiceResponseCode------"+webServiceResponseCode);
                    LOGGER.info("webServiceResponseStatus------"+webServiceResponseStatus);
                    LOGGER.info("webServiceResponseDescription------"+webServiceResponseDescription);
                    responseMsg ="The IPH mapping cannot be updated , contact the system administrator.";
                    LOGGER.severe("Error returned from content update web service = " + webServiceResponseDescription);
                }
            }
            else
            {
                System.out.println("OTHER THAN 200 OR 201");
                System.out.println("HTTP call response code and response message =  "+con.getResponseCode()+" , "+ con.getResponseMessage());
                LOGGER.info("webServiceResponseCode------"+webServiceResponseCode);
                LOGGER.info("webServiceResponseStatus------"+webServiceResponseStatus);
                LOGGER.info("webServiceResponseDescription------"+webServiceResponseDescription);
                responseMsg ="The IPH mapping cannot  be updated HTTP Error, contact  the System Administrator.";
            }

        }catch(final Exception exception){
            LOGGER.severe("Exception Occurred callIPHMappingWebService =  "+exception.getCause());
            responseMsg ="The IPH mapping cannot  be updated HTTP Error, contact  the System Administrator.";

        }

        return responseMsg;


    }

    /**
     * Call iph mapping web service.
     *
     * @param webserviceRequest the webservice request
     * @return the string
     */
    public void callasyncIPHMappingWebService(String webserviceRequest)
    {

        LOGGER.info("start of createIPHMappingWebServiceMapping ");
        String webServiceResponseCode = null;
        String webServiceResponseDescription = null;
        String webServiceResponseStatus = null;
        String responseMsg = null;
        String webServiceResponse = null;
        LOGGER.info("callIPHMappingWebService called");
        LOGGER.info("callIPHMappingWebService JSON Request....."+webserviceRequest);
        final Properties prop =   PropertiesFileLoader.getPropertyLoader(ContentScreenConstants.MESS_PROP);
        final String targetURL =  prop.getProperty(ContentScreenConstants.IPH_MAPPING_WEBSERVICE_URL);

        LOGGER.info("IPHMappingWebService URL = "+targetURL);

        try{

            final URL createWebServiceURL = new URL(targetURL);
            final HttpURLConnection con = (HttpURLConnection)createWebServiceURL.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json;");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Method", "POST");
            final OutputStream os = con.getOutputStream();
            os.write(webserviceRequest.getBytes("UTF-8"));
            os.close(); // Calling Web Service

           // final StringBuilder sb = new StringBuilder();
           // final int httpResult =con.getResponseCode();

            LOGGER.info("HTTP Result from IPHMappingWebService =  ");
            
            }catch(final Exception exception){
            LOGGER.severe("Exception Occurred callasyncIPHMappingWebService =  "+exception.getCause());
        }
    }

    /**
     * Creates the content web service.
     *
     * @param createContentWebServiceReq the create content web service req
     * @return the string
     */
    public String createContentWebService(String createContentWebServiceReq) {
        String webServiceResponseDescription = null;
        String webServiceResponseStatus = null;
        String responseMsg = null;
        String webServiceResponse = null;
        LOGGER.info("createContentWebService called");
        final Properties prop =   PropertiesFileLoader.getPropertyLoader(ContentScreenConstants.MESS_PROP);
        final String targetURL =  prop.getProperty(ContentScreenConstants.DEV_SERVICE_URL);

        LOGGER.info("createContentWebService URL = "+targetURL);

        try{

            final URL createWebServiceURL = new URL(targetURL);
            final HttpURLConnection con = (HttpURLConnection)createWebServiceURL.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json;");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Method", "POST");
            final OutputStream os = con.getOutputStream();
            os.write(createContentWebServiceReq.getBytes("UTF-8"));
            os.close();


            final StringBuilder sb = new StringBuilder();
            final int httpResult =con.getResponseCode();

            System.out.println("HTTP Result from service =  "+httpResult);

            if((httpResult == HttpURLConnection.HTTP_OK) ||
                    (httpResult == HttpURLConnection.HTTP_CREATED) )

            {

                LOGGER.info("HTTP Result when OK =  "+httpResult);
                final BufferedReader br = new BufferedReader(new   InputStreamReader(con.getInputStream(),"utf-8"));

                String line = null;
                while ((line = br.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                br.close();
                webServiceResponse = sb.toString();
                System.out.println("Response from createContentWebService = "+ webServiceResponse);
                //parse web service response
             //   final JSONObject jsonResponseObject = new JSONObject(webServiceResponse);

              //  jsonResponseObject.getString("code");
             //   webServiceResponseDescription = jsonResponseObject.getString("description");
             //  webServiceResponseStatus = jsonResponseObject.getString("status");

                if ((webServiceResponse != null)
                        && (webServiceResponse.contains("SUCCESS") && (webServiceResponse.indexOf("SUCCESS") != -1))) {

               // if(webServiceResponseStatus.equals("SUCCESS")){
                    responseMsg ="SUCCESS";
                }

                else {

                    responseMsg ="FAILED";
                    LOGGER.severe("Error returned from content update web service = " + webServiceResponseDescription);
                }
            }
            else
            {
                System.out.println("HTTP call response code and response message =  "+con.getResponseCode()+" , "+ con.getResponseMessage());
                responseMsg ="The content cannot  be updated HTTP Error, contact  the System Administrator.";
            }

        }catch(final Exception ex){
            LOGGER.severe("Exception Occurred createContentWebService =  "+ex.getCause());

        }

        return responseMsg;


    }


    /**
     * Gets the cards brand list.
     *
     * @param orinNumber the orin number
     * @param supplierId the supplier id
     * @return the cards brand list
     * @throws PEPDelegateException the PEP delegate exception
     */
    public List<CarBrandVO> getCardsBrandList(String orinNumber, String supplierId)
            throws PEPDelegateException {
        try {
            return contentService.getCardsBrandList(orinNumber, supplierId);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());

            throw new PEPDelegateException(serviceException.getMessage());
        }
    }


    /**
     * Gets the child sku info.
     *
     * @param orinNumber the orin number
     * @return the child sku info
     */
    public List<ChildSkuVO> getChildSkuInfo(String orinNumber)
    {
        try {

            return contentService.getSkusFromADSE(orinNumber);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());
        }
        return null;
    }


    /**
     * Gets the color family data set.
     *
     * @return the color family data set
     * @throws PEPDelegateException the PEP delegate exception
     */
    public List<StyleColorFamilyVO> getColorFamilyDataSet()
            throws PEPDelegateException  {

        try {

            return contentService.getColorFamilyDataSet();
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());
        }
        return null;


    }




    /**
     * Gets the content history.
     *
     * @param orinNumber the orin number
     * @return the content history
     */
    public List<ContentHistoryVO> getContentHistory(String orinNumber)
    {
        try {

            return contentService.getContentHistoryFromADSE(orinNumber);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());
        }
        return null;
    }


    /**
     * Gets the content managment info.
     *
     * @param orinNumber the orin number
     * @return the content managment info
     */
    public ContentManagementVO getContentManagmentInfo(String orinNumber)
    {
        try {

            return contentService.getContentManagmentInfoFromADSE(orinNumber);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());
        }
        return null;
    }


    /**
     * Gets the content service.
     *
     * @return the contentService
     */
    public ContentService getContentService() {
        return contentService;
    }



    /**
     * Gets the copy attributes.
     *
     * @param orinNumber the orin number
     * @return the copy attributes
     */
    public List<CopyAttributesVO> getCopyAttributes(String orinNumber)
    {
        try {

            return contentService.getCopyAttributesFromADSE(orinNumber);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());
        }
        return null;


    }


    /**
     * Gets the family categories from iph.
     *
     * @param merchCategoryId the merch category id
     * @return the family categories from iph
     * @throws PEPDelegateException the PEP delegate exception
     */
    public List<ItemPrimaryHierarchyVO> getFamilyCategoriesFromIPH(
        String merchCategoryId) throws PEPDelegateException {


        LOGGER.info("****start of  getFamilyCategoriesFromIPH  method****");

        try {
            return contentService.getFamilyCategoriesFromIPH(merchCategoryId);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());

            throw new PEPDelegateException(serviceException.getMessage());
        }


    }



    /**
     * Gets the IPH categories from adse merchandise hierarchy.
     *
     * @param orinNumber the orin number
     * @return the IPH categories from adse merchandise hierarchy
     * @throws PEPDelegateException the PEP delegate exception
     */
    public List<ItemPrimaryHierarchyVO> getIPHCategoriesFromAdseMerchandiseHierarchy(String orinNumber) throws PEPDelegateException
    {

        try {
            return contentService.getIPHCategoriesFromAdseMerchandiseHierarchy(orinNumber);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());

            throw new PEPDelegateException(serviceException.getMessage());
        }

    }





    /**
     * Gets the IPH categories from adse pet catalog.
     *
     * @param orinNumber the orin number
     * @return the IPH categories from adse pet catalog
     * @throws PEPDelegateException the PEP delegate exception
     */
    public List<ItemPrimaryHierarchyVO> getIPHCategoriesFromADSEPetCatalog(String orinNumber) throws PEPDelegateException
    {

        try {
            return contentService.getIPHCategoriesFromADSEPetCatalog(orinNumber);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());

            throw new PEPDelegateException(serviceException.getMessage());
        }

    }


    /**
     * Gets the item primary hierarchy categories.
     *
     * @return the item primary hierarchy categories
     */
    public List<ItemPrimaryHierarchyVO> getItemPrimaryHierarchyCategories()
    {
        try {

            return contentService.getIPHCategories();
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());
        }
        return null;


    }



    /**
     * Gets the omni channel brand list.
     *
     * @param orinNumber the orin number
     * @param supplierId the supplier id
     * @return the omni channel brand list
     * @throws PEPDelegateException the PEP delegate exception
     */
    public List<OmniChannelBrandVO> getOmniChannelBrandList(String orinNumber, String supplierId)
            throws PEPDelegateException {

        try {
            return contentService.getOmniChannelBrandList(orinNumber, supplierId);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());

            throw new PEPDelegateException(serviceException.getMessage());
        }
    }

    /**
     * Gets the pet attribute details.
     *
     * @param categoryId the category id
     * @return the pet attribute details
     * @throws PEPFetchException the PEP fetch exception
     */
    public List<PetAttributeVO> getPetAttributeDetails(String categoryId,String orinNumber)
            throws PEPDelegateException {

        LOGGER.info("****start of  getPetAttributeDetails  method****");

        try {
            return contentService.getPetAttributeDetails(categoryId, orinNumber);
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
     * @throws PEPDelegateException the PEP delegate exception
     */
    public List<BlueMartiniAttributesVO> getPetBlueMartiniAttributesDetails(String categoryId,String orinNumber)
            throws PEPDelegateException
            {

        LOGGER.info("****start of  getPetBlueMartiniAttributesDetails  method****");


        try {
            return contentService.getPetBlueMartiniAttributesDetails(categoryId, orinNumber);
        }
        catch (PEPServiceException e) {

            e.printStackTrace();
        }


        return null;



            }

    /**
     * Gets the pet list.
     *
     * @param orinNumber the orin number
     * @param orinNumber2
     * @return the pet list
     */
    public List<PetsFound>  getPetList(String roleName, String orinNumber) throws  PEPDelegateException  {
        try {
            return contentService.getStyleAndItsChildFromADSE(roleName,orinNumber);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());
            throw new PEPDelegateException(serviceException);
        }
    }



    /**
     * Gets the product information.
     *
     * @param orinNumber the orin number
     * @return the product information
     */
    public ProductDetailsVO getProductInformation(String orinNumber)  {
        try {
            return contentService.getProductInfoFromADSE(orinNumber);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());
        }
        return null;
    }

    /**
     * Gets the sku attributes.
     *
     * @param skuOrinNumber the sku orin number
     * @return the sku attributes
     * @throws PEPDelegateException the PEP delegate exception
     */
    public SkuAttributesVO getSkuAttributes(String skuOrinNumber) throws PEPDelegateException  {
        try {
            return contentService.getSkuAttributesInfoFromADSE(skuOrinNumber);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());

            throw new PEPDelegateException(serviceException.getMessage());
        }
    }


    /**
     * Gets the style attributes.
     *
     * @param orinNumber the orin number
     * @return the style attributes
     */
    public GlobalAttributesVO getStyleAttributesADSE(String orinNumber)   throws PEPDelegateException
    {
        try {

            return contentService.getStyleAttributesFromADSE(orinNumber);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());
            throw new PEPDelegateException(serviceException);
        }
    }


    /**
     * Gets the style color attributes.
     *
     * @param styleColorOrinNumber the style color orin number
     * @return the style color attributes
     * @throws PEPDelegateException the PEP delegate exception
     */
    public ColorAttributesVO getStyleColorAttributes(String styleColorOrinNumber)   throws PEPDelegateException {

        try {

            return contentService.getStyleColorAttributesFromADSE(styleColorOrinNumber);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());
            throw new PEPDelegateException(serviceException);
        }

    }


    /**
     * Gets the style information.
     *
     * @param orinNumber the orin number
     * @return the style information
     */
    public StyleInformationVO getStyleInformation(String orinNumber) throws PEPDelegateException  {
        try {
            return contentService.getStyleInfoFromADSE(orinNumber);
        }
        catch (final PEPServiceException serviceException) {

            LOGGER.log(Level.SEVERE, serviceException.getMessage());

            throw new PEPDelegateException(serviceException.getMessage());
        }
    }


    /**
     * Sets the content service.
     *
     * @param contentService the contentService to set
     */
    public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }

    public boolean releseLockedPet(String orin, String pepUserID,String pepFunction)throws PEPPersistencyException {
        LOGGER.info("Image requestdelegate :: releseLockedPet");
        boolean  isPetReleased = contentService.releseLockedPet(orin,pepUserID,pepFunction);     
        return isPetReleased;
        
        
    }


    /**
     * Update content status web service.
     *
     * @param webserviceRequest the webservice request
     * @return the string
     */
    public String  updateContentStatusWebService(String webserviceRequest)
    {

        LOGGER.info("start of updateContentStatusWebService------");
        String webServiceResponseCode = null;
        String webServiceResponseDescription = null;
        String webServiceResponseStatus = null;
        String responseMsg = null;
        String webServiceResponse = null;
        LOGGER.info("updateContentStatusWebService called");
        LOGGER.info("updateContentStatusWebService JSON Request....."+webserviceRequest);
        final Properties prop =   PropertiesFileLoader.getPropertyLoader(ContentScreenConstants.MESS_PROP);
        final String targetURL =  prop.getProperty(ContentScreenConstants.UPDATE_CONTENT_STATUS_WEBSERVICE_URL);

        LOGGER.info("UPDATE_CONTENT_STATUS_WEBSERVICE_URL  = "+targetURL);

        try{

            final URL createWebServiceURL = new URL(targetURL);
            final HttpURLConnection con = (HttpURLConnection)createWebServiceURL.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json;");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Method", "POST");
            final OutputStream os = con.getOutputStream();
            os.write(webserviceRequest.getBytes("UTF-8"));
            os.close();


            final StringBuilder sb = new StringBuilder();
            final int httpResult =con.getResponseCode();

            LOGGER.info("HTTP Result from UPDATE_CONTENT_STATUS_WEBSERVICE_URL =  "+httpResult);

            if((httpResult == HttpURLConnection.HTTP_OK) ||
                    (httpResult == HttpURLConnection.HTTP_CREATED) )

            {

                LOGGER.info("HTTP Result from UPDATE_CONTENT_STATUS_WEBSERVICE_URL when OK =  "+httpResult);
                final BufferedReader br = new BufferedReader(new   InputStreamReader(con.getInputStream(),"utf-8"));

                String line = null;
                while ((line = br.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                br.close();
                webServiceResponse = sb.toString();
                LOGGER.info("Response from  UPDATE_CONTENT_STATUS_WEBSERVICEin string format = "+ webServiceResponse);
                //parse web service response
                final JSONObject jsonResponseObject = new JSONObject(webServiceResponse);

                webServiceResponseCode = jsonResponseObject.getString("code");
                webServiceResponseDescription = jsonResponseObject.getString("message");
                webServiceResponseStatus = jsonResponseObject.getString("status");


                if(webServiceResponseStatus.equals("SUCCESS") && webServiceResponseDescription.equalsIgnoreCase("Pet status successfully updated")){
                    LOGGER.info("webServiceResponseCode------"+webServiceResponseCode);
                    LOGGER.info("webServiceResponseStatus------"+webServiceResponseStatus);
                    LOGGER.info("webServiceResponseDescription------"+webServiceResponseDescription);
                    responseMsg ="Pet status successfully updated";
                }
                else if(webServiceResponseStatus.equalsIgnoreCase("FAIL") && webServiceResponseDescription.equalsIgnoreCase("Provided 1 or more  attribute value may be null or wrong in the request"))
                {
                    LOGGER.info("webServiceResponseCode------"+webServiceResponseCode);
                    LOGGER.info("webServiceResponseStatus------"+webServiceResponseStatus);
                    LOGGER.info("webServiceResponseDescription------"+webServiceResponseDescription);
                    responseMsg= "Pet status cannot be updated,contact the system administrator.";
                }

                else {
                    LOGGER.info("webServiceResponseCode------"+webServiceResponseCode);
                    LOGGER.info("webServiceResponseStatus------"+webServiceResponseStatus);
                    LOGGER.info("webServiceResponseDescription------"+webServiceResponseDescription);
                    responseMsg ="UPDATE_CONTENT_STATUS_WEBSERVICE_URLcannot be updated , contact the system administrator.";
                    LOGGER.severe("Error returned from UPDATE_CONTENT_STATUS_WEBSERVICE_URL = " + webServiceResponseDescription);
                }
            }
            else
            {
                System.out.println("OTHER THAN 200 OR 201");
                System.out.println("HTTP call response code and response message =  "+con.getResponseCode()+" , "+ con.getResponseMessage());
                LOGGER.info("webServiceResponseCode------"+webServiceResponseCode);
                LOGGER.info("webServiceResponseStatus------"+webServiceResponseStatus);
                LOGGER.info("webServiceResponseDescription------"+webServiceResponseDescription);
                responseMsg ="UPDATE_CONTENT_STATUS_WEBSERVICE_URL cannot  be updated HTTP Error, contact  the System Administrator.";
            }

        }catch(final Exception exception){
            LOGGER.severe("Exception Occurred UPDATE_CONTENT_STATUS_WEBSERVICE =  "+exception.getCause());
            responseMsg ="Pet status cannot be updated,contact the system administrator.";

        }

        return responseMsg;


    }


}
