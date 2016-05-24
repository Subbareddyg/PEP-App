package com.belk.pep.service.impl;

import java.sql.SQLException;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.Exception;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.belk.pep.constants.ImageConstants;
import com.belk.pep.dao.ImageRequestDAO;

import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.model.ImageLinkVO;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.service.ImageRequestService;
//import com.belk.pep.service.impl.ArrayList;
//import com.belk.pep.service.impl.Exception;
import com.belk.pep.util.PropertyLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



/**
 * The Class ImageRequestServiceImpl.
 */
public class ImageRequestServiceImpl implements ImageRequestService { 
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(ImageRequestServiceImpl.class.getName()); 
	
	
	/** The user dao. */
    private ImageRequestDAO imageRequestDAO;
    
    
    public ImageRequestDAO getImageRequestDAO() {
        return imageRequestDAO;
    }


    public void setImageRequestDAO(ImageRequestDAO imageRequestDAO) {
        this.imageRequestDAO = imageRequestDAO;
    }

  

	
	
    
    /**
     * 
     */
    @Override
   public ArrayList getStyleInfoDetails(String orinNo) throws PEPServiceException, PEPPersistencyException{
        LOGGER.info("****calling getStyleInfoDetails from ImageRequestServiceImpl****");
        
        ArrayList imageStyleList=null;
        try {
            imageStyleList = imageRequestDAO.getStyleInfoDetails(orinNo);
        }
        catch (PEPPersistencyException e) {

            LOGGER.info("Exception occurred at the Service DAO Layer");
            throw e;
        }

        catch (Exception e) {

            LOGGER
                .info("Exception occurred at the Service Implementation Layer");
            throw new PEPServiceException(e.getMessage());
        }
        return imageStyleList;
        
    }
   /**
    * 
    * 
    */
   @Override
   public ArrayList getImageInfoDetails(String orinNo) throws PEPServiceException, PEPPersistencyException{
        LOGGER.info("****calling getImageInfoDetails from ImageRequestServiceImpl****");
        
        ArrayList imageProductList=null;
        try {
            imageProductList = imageRequestDAO.getImageInfoDetails(orinNo);
           
        }
        catch (PEPPersistencyException e) {

            LOGGER.info("Exception occurred at the Service DAO Layer");
            throw e;
        }

        catch (Exception e) {

            LOGGER
                .info("Exception occurred at the Service Implementation Layer");
            throw new PEPServiceException(e.getMessage());
        }
        return imageProductList;
        
    }
   /**
    * 
    */
   @Override
   public ArrayList getVendorInformation(String orinNo) throws PEPServiceException, PEPPersistencyException{
       LOGGER.info("****calling getVendorInfoDetails from ImageRequestServiceImpl****");
       
       ArrayList vendorInfoList=null;
       try {
           vendorInfoList = imageRequestDAO.getVendorInformation(orinNo);
       }
       catch (PEPPersistencyException e) {

           LOGGER.info("Exception occurred at the Service DAO Layer");
           throw e;
       }

       catch (Exception e) {

           LOGGER
               .info("Exception occurred at the Service Implementation Layer");
           throw new PEPServiceException(e.getMessage());
       }
       return vendorInfoList;
       
   }
   
   /**
    * 
    */
   @Override
   public ArrayList getContactInformation(String orinNo) throws PEPServiceException, PEPPersistencyException{
       LOGGER.info("****calling getContactInfoDetails from ImageRequestServiceImpl****");
       
       ArrayList contactInfoList=null;
       try {
           contactInfoList = imageRequestDAO.getContactInformation(orinNo);
       }
       catch (PEPPersistencyException e) {

           LOGGER.info("Exception occurred at the Service DAO Layer");
           throw e;
       }

       catch (Exception e) {

           LOGGER
               .info("Exception occurred at the Service Implementation Layer");
           throw new PEPServiceException(e.getMessage());
       }
       return contactInfoList;
       
   }
   
   @Override
   public ArrayList getPepHistoryDetails(String orinNo) throws PEPServiceException, PEPPersistencyException{
       LOGGER.info("****calling getPepHistoryDetails from ImageRequestServiceImpl****");
       
       ArrayList pepHistoryList=null;
       try {
           pepHistoryList = imageRequestDAO.getPepHistoryDetails(orinNo);
           
       }
       catch (PEPPersistencyException e) {

           LOGGER.info("Exception occurred at the Service DAO Layer");
           throw e;
       }

       catch (Exception e) {

           LOGGER
               .info("Exception occurred at the Service Implementation Layer");
           throw new PEPServiceException(e.getMessage());
       }
       return pepHistoryList;
       
   }
   /**
    * This is a service impl method for getting SampleImageLinks
    */
   @Override
   public ArrayList getSampleImageLinks(String orinNo) throws PEPServiceException, PEPPersistencyException{
       LOGGER.info("****calling getSampleImageLinks from ImageRequestServiceImpl****");
       
       ArrayList sampleImageLinkList=null;
       try {
    	   sampleImageLinkList = imageRequestDAO.getSampleImageLinks(orinNo);
          
       }
       catch (PEPPersistencyException e) {

           LOGGER.info("Exception occurred at the Service DAO Layer getSampleImageLinks");
           throw e;
       }

       catch (Exception e) {

           LOGGER
               .info("Exception occurred at the Service Implementation Layer getSampleImageLinks");
           throw new PEPServiceException(e.getMessage());
       }
       return sampleImageLinkList;
       
   }
   
   /**
    * This is a service impl method for getting art director request details 
    */
   @Override
   public ArrayList getArtDirectorRequestDetails(String orinNo) throws PEPServiceException, PEPPersistencyException{
       LOGGER.info("****calling getArtDirectorRequestDetails from ImageRequestServiceImpl****");
       
       ArrayList artDirectorList=null;
       try {
    	   artDirectorList = imageRequestDAO.getArtDirectorRequestDetails(orinNo);
           
       }
       catch (PEPPersistencyException e) {

           LOGGER.info("Exception occurred at the Service DAO Layer getArtDirectorRequestDetails");
           throw e;
       }

       catch (Exception e) {

           LOGGER
               .info("Exception occurred at the Service Implementation Layer getArtDirectorRequestDetails");
           throw new PEPServiceException(e.getMessage());
       }
       return artDirectorList;
       
   }
   
   
/**
 * Method call for remove image 
 */
public String callRemoveImageWebService(JSONArray jsonArray) throws Exception,PEPFetchException {
       LOGGER.info("ImageRequestServiceImpl:::callRemoveImageWebService");

       String responseMsg = "";
       boolean flag = false;
       String msgCodeStr = "";
       String responseMSGCode = "";
       try {
    	   String envVariable = System.getProperty("envVariable");
    	   System.out.println("envVariable **********"+envVariable);
    	   LOGGER.info("envVariable **********"+envVariable);
           Properties prop =PropertyLoader.getPropertyLoader(ImageConstants.MESS_PROP);
           String targetURLs = prop.getProperty(ImageConstants.DEV_SERVICE_URL);
           //String targetURLs = ImageConstants.DEV_SERVICE_URL;
           //String targetURLs = "http://ralpimwsasit02:7507/JERSYRest/rest/UpdateItemServices/deleteImage";
           LOGGER.info("targetURLs **********"+targetURLs);
           URL targetUrl = new URL(targetURLs);
           HttpURLConnection httpConnection =(HttpURLConnection) targetUrl.openConnection();
           httpConnection.setDoOutput(true);
           httpConnection.setRequestMethod("POST");
           httpConnection.setRequestProperty("Content-Type","application/json");
           LOGGER.info("ImageRequestServiceImpl::Json Array" + jsonArray.toString());
           JSONObject jsonMap = new JSONObject();
           jsonMap.put(ImageConstants.SERVICE_REMOVE_IMAGE_LIST, jsonArray);
           String input = jsonMap.toString();
           LOGGER.info("final object in json" + jsonMap.toString());
           OutputStream outputStream = httpConnection.getOutputStream();
           outputStream.write(input.getBytes());
           outputStream.flush();
           if (200 == httpConnection.getResponseCode()) {
           }

           if (httpConnection.getResponseCode() != 200) {
               throw new Exception("Failed : HTTP error code : "+ httpConnection.getResponseCode());
           }

           BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
        		   (httpConnection.getInputStream())));

           String output;
           LOGGER.info("Output from Server:\n");
           while ((output = responseBuffer.readLine()) != null) {
               LOGGER.info(output);

               // Below if block is for handling single row data.
               if ((jsonArray != null) && (jsonArray.length() <= 1)) {
                   if ((output != null)
                       && (output.contains("not") && (output.indexOf("not") != -1))) {
                       responseMsg = prop.getProperty(ImageConstants.IMAGE_NOT_REMOVED);
                       responseMSGCode = "101";
                       responseMsg = responseMsg+"_"+responseMSGCode;
                       LOGGER.info("responseMsg---Failed**" + responseMsg);
                       

                   } else {
                      responseMsg = prop.getProperty(ImageConstants.RESPONSE_MSG);
                       responseMSGCode = "100";
                       responseMsg = responseMsg+"_"+responseMSGCode;
                       LOGGER.info("responseMsg---Success**" + responseMsg);
                   }

               } else {
                   // This block is for handling multiple row data.
                   JsonElement jelement = new JsonParser().parse(output);
                   JsonObject jobject = jelement.getAsJsonObject();
                   JsonArray jsonObject =(JsonArray) jobject.get(ImageConstants.SERVICE_REMOVE_IMAGE_LIST);

                   for (int i = 0; i < jsonObject.size(); i++) {
                       LOGGER.info("ImageRequestServiceImpl::Id value size" + jsonObject.size() + "i value" + i);
                       if (jsonObject.size() == 1) {
                           JsonObject individualjson = jsonObject.getAsJsonObject();
                           Object msgCode =individualjson.get(ImageConstants.MSG_CODE);

                           LOGGER.info("ImageRequestServiceImpl::MsgCode with one json" + msgCode.toString());
                           msgCodeStr = msgCode.toString();
                           msgCodeStr =msgCodeStr.substring(1, msgCodeStr.length() - 1);
                           LOGGER.info("aa" + msgCodeStr);
                       } else {
                           JsonObject individualjson = jsonObject.get(i).getAsJsonObject();
                           Object msgCode =individualjson.get(ImageConstants.MSG_CODE);

                           LOGGER.info("ImageRequestServiceImpl::MsgCode" + msgCode.toString());

                           msgCodeStr = msgCode.toString();
                           msgCodeStr =msgCodeStr.substring(1, msgCodeStr.length() - 1);
                           LOGGER.info("msgCodeStr" + msgCodeStr);
                       }

                       if (msgCodeStr.equalsIgnoreCase(ImageConstants.SUCCESS_CODE)) {
                           flag = true;
                           LOGGER.info("ImageRequestServiceImpl:::callRemoveImageWebService:::flag" + flag);
                       } else if (msgCodeStr.equalsIgnoreCase(ImageConstants.FAILURE_CODE)) {
                           flag = false;
                       }
                   }
                   if (flag) {
                       responseMsg = prop.getProperty(ImageConstants.RESPONSE_MSG);
                       LOGGER.info("ImageRequestServiceImpl:::callRemoveImageWebService:::responseMsg" + responseMsg);

                   } else {
                       responseMsg = prop.getProperty(ImageConstants.IMAGE_NOT_REMOVED);
                       LOGGER.info("ImageRequestServiceImpl:::callRemoveImageWebService:::responseMsg"+ responseMsg);

                   }
               }

           }

           httpConnection.disconnect();
       } catch (MalformedURLException e) {
           LOGGER.info("inside malformedException");
           throw new PEPFetchException();
          // e.printStackTrace();

       } catch (ClassCastException e) {
       	
           e.printStackTrace();
           throw new PEPFetchException();
       } catch (IOException e) {
           LOGGER.info("inside IOException");

           e.printStackTrace();
           throw new Exception();

       } catch (JSONException e) {
           LOGGER.info("inside JSOnException");
           

           e.printStackTrace();
           throw new PEPFetchException();
       } catch (Exception e) {
           LOGGER.info("inside Exception" + e);

           e.printStackTrace();
           throw new Exception();

       }

       return responseMsg;
   }

//Service Call for Upload VPI Image
public String callUploadVPIService(JSONArray jsonArray) throws Exception,PEPFetchException {
    LOGGER.info("ImageRequestServiceImpl:::callUploadVPIService");

    String responseMsg = "";
    boolean flag = false;
    String msgCodeStr = "";
    try {
        Properties prop =PropertyLoader.getPropertyLoader(ImageConstants.MESS_PROP);
        String targetURLs = prop.getProperty(ImageConstants.DEV_SERVICE_UPLOADVPI_IMAGE_URL);        
        //String targetURLs = ImageConstants.DEV_SERVICE_UPLOADVPI_IMAGE_URL;
        //String targetURLs = "http://ralpimwsasit02:7507/JERSYRest/rest/UpdateItemServices/uploadImage";
        URL targetUrl = new URL(targetURLs);
        LOGGER.info("ImageRequestServiceImpl::targetURLs callUploadVPIService  " + targetURLs);
        HttpURLConnection httpConnection =(HttpURLConnection) targetUrl.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type","application/json");
        LOGGER.info("ImageRequestServiceImpl::Json Array callUploadVPIService" + jsonArray.toString());
        JSONObject jsonMap = new JSONObject();
        jsonMap.put(ImageConstants.SERVICE_IMAGE_UPLOAD_LIST, jsonArray);
        String input = jsonMap.toString();
        LOGGER.info("final object in json callUploadVPIService::" + jsonMap.toString());
        OutputStream outputStream = httpConnection.getOutputStream();
        outputStream.write(input.getBytes());
        outputStream.flush();
        BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
     		   (httpConnection.getInputStream())));

        String output;
        LOGGER.info("Output from Server:callUploadVPIService::\n ");
        while ((output = responseBuffer.readLine()) != null) {
            LOGGER.info("Upload VPI webservice response *****"+output);
        // This block is for handling Single Request
		if ((jsonArray != null) && (jsonArray.length() <= 1)) {
            LOGGER.info("Single Request file upload:");            
            JsonElement jelement1 = new JsonParser().parse(output);
            JsonObject jobject1 = jelement1.getAsJsonObject();
            JsonArray jsonObject1 =(JsonArray) jobject1.get("list");            
            if ((output != null)
                    && (output.contains("not")||output.contains("may be") || output.contains("Failed".trim())) && (output.indexOf("Failed".trim()) != -1 ||output.indexOf("may be") != -1||output.indexOf("not") != -1)) {
                
				responseMsg = prop.getProperty(ImageConstants.DEV_SERVICE_UPLOADVPI_ERROR_IMAGE_MESSAGE);                
                LOGGER.info("Failure File Upload::" + responseMsg);
            }
            else {
				responseMsg = prop.getProperty(ImageConstants.DEV_SERVICE_UPLOADVPI_SUCCESS_IMAGE_MESSAGE);
                LOGGER.info("Success File Upload::" + responseMsg);
            }
        }else{
				// This block is for handling Multiple Request
				LOGGER.info("Multiple Request File Upload");
                JsonElement jelement = new JsonParser().parse(output);
                JsonObject jobject = jelement.getAsJsonObject();
                JsonArray jsonObject =(JsonArray) jobject.get(ImageConstants.SERVICE_IMAGE_UPLOAD_LIST);
                for (int i = 0; i < jsonObject.size(); i++) {
                    LOGGER.info("ImageRequestServiceImpl::Id value size" + jsonObject.size() + "i value" + i);
                    if (jsonObject.size() == 1) {
                    	LOGGER.info("Line 436");
                        JsonObject individualjson = jsonObject.getAsJsonObject();
                        Object msgCode =individualjson.get(ImageConstants.MSG_CODE);

                        LOGGER.info("ImageRequestServiceImpl::MsgCode with one json" + msgCode.toString());
                        msgCodeStr = msgCode.toString();
                        msgCodeStr =msgCodeStr.substring(1, msgCodeStr.length() - 1);
                        LOGGER.info("aa" + msgCodeStr);
                    } else {
                    	LOGGER.info("Line 445");
                        JsonObject individualjson = jsonObject.get(i).getAsJsonObject();
                        Object msgCode =individualjson.get(ImageConstants.MSG_CODE);

                        LOGGER.info("ImageRequestServiceImpl::MsgCode callUploadImageservice" + msgCode.toString());

                        msgCodeStr = msgCode.toString();
                        msgCodeStr =msgCodeStr.substring(1, msgCodeStr.length() - 1);
                        LOGGER.info("msgCodeStr" + msgCodeStr);
                    }
                    if (msgCodeStr.equalsIgnoreCase(ImageConstants.SUCCESS_CODE)) {
                        flag = true;
                        LOGGER.info("ImageRequestServiceImpl:::callUploadImageservice:::flag" + flag);
                    } else if (msgCodeStr.equalsIgnoreCase(ImageConstants.FAILURE_CODE)) {
                    	LOGGER.info("Line 460");
                        flag = false;
                    }
                }
                if (flag) {
                	LOGGER.info("Line 465");
                    responseMsg = prop.getProperty(ImageConstants.DEV_SERVICE_UPLOADVPI_SUCCESS_IMAGE_MESSAGE);
                    LOGGER.info("ImageRequestServiceImpl:::callUploadImageservice:::responseMsg" + responseMsg);

                } else {
                	LOGGER.info("Line 470");
                    responseMsg = prop.getProperty(ImageConstants.DEV_SERVICE_UPLOADVPI_ERROR_IMAGE_MESSAGE);
                    LOGGER.info("ImageRequestServiceImpl:::callUploadImageservice:::responseMsg"+ responseMsg);

                }
        }
}

        httpConnection.disconnect();
    } catch (MalformedURLException e) {
        LOGGER.info("inside malformedException callUploadImageservice");
        throw new PEPFetchException();
       // e.printStackTrace();

    } catch (ClassCastException e) {
    	
        e.printStackTrace();
        throw new PEPFetchException();
    } catch (IOException e) {
        LOGGER.info("inside IOException callUploadImageservice");

        e.printStackTrace();
        throw new Exception();

    } catch (JSONException e) {
        LOGGER.info("inside JSOnException callUploadImageservice");
        

        e.printStackTrace();
        throw new PEPFetchException();
    } catch (Exception e) {
        LOGGER.info("inside Exception callUploadImageservice" + e);

        e.printStackTrace();
        throw new Exception();

    }

    return responseMsg;
}

/**
 * This method will return the PET details from the ADSE on base of department 
 * @param departmentNumbers
 * @return
 * @throws PEPServiceException
 * @throws PEPPersistencyException
 */
@Override
public List<WorkFlow> getImageMgmtDetailsByOrin(String orinNum)
    throws PEPServiceException, PEPPersistencyException {
  //getting the Pet  details from ADSE Database
    LOGGER.info("****calling getImageMgmtDetailsByOrin from Image ****");
    List<WorkFlow> petList=null;
    try {
        petList = imageRequestDAO.getImageMgmtDetailsByOrin(orinNum);
    }
    catch (PEPPersistencyException e) {
        
        LOGGER.info("Exception occurred at the Service DAO Layer");
        throw e;
    }
    
    catch (Exception e) {
          
            LOGGER.info("Exception occurred at the Service Implementation Layer");
            throw new PEPServiceException(e.getMessage());
        }
    
    return petList;
}
/**
 * This methods gets the directory to upload the image file.
 * 
 * @return String
 */
public String getVendorImageUploadDir() throws Exception {
	Properties properties = PropertyLoader.getPropertyLoader("ftp.properties");
	return properties.getProperty("vendorImageUploadDir");
	/*String url = System.getenv("vendorImageUploadDir"); // AFUPYB3
	return url;*/
}

/**
 * This methods gets the directory for RRD uploaded image file.
 * 
 * @return String
 */
public String getRRDImageUploadedDir() throws Exception {
	Properties properties = PropertyLoader.getPropertyLoader("ftp.properties");
	return properties.getProperty("RRDImageUploadedDir");
	/*String url = System.getenv("RRDImageUploadedDir"); // AFUPYB3
	return url;*/
	
}


/**
 * Service Call Impl for Approve or Reject Action
 * 
 */
public String callApproveorRejectActionService(JSONArray jsonArray) throws Exception,PEPFetchException{
    LOGGER.info("ImageRequestServiceImpl:::callApproveorRejectAction");

    String responseMsg = "";
    boolean flag = false;
    String msgCodeStr = "";
    try {
        Properties prop =PropertyLoader.getPropertyLoader(ImageConstants.MESS_PROP);
        String targetURLs = prop.getProperty(ImageConstants.IMAGE_APPROVE_WEBSERVICE_URL);
        //String targetURLs = ImageConstants.IMAGE_APPROVE_WEBSERVICE_URL;
        //String targetURLs = "http://cltpimwsaspt01.belkinc.com:7507/JERSYRest/rest/UpdateItemServices/imageStatusUpdateService";
        LOGGER.info("targetURLs-----SIT#####" +targetURLs);
        URL targetUrl = new URL(targetURLs);
        HttpURLConnection httpConnection =(HttpURLConnection) targetUrl.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type","application/json");
        LOGGER.info("ImageRequestServiceImpl::Json Array" + jsonArray.toString());
        JSONObject jsonMap = new JSONObject();
        jsonMap.put(ImageConstants.SERVICE_APPROVEORREJECT_ACTION_VALUE_LIST, jsonArray);
        String input = jsonMap.toString();
        LOGGER.info("final object in json" + jsonMap.toString());
        OutputStream outputStream = httpConnection.getOutputStream();
        outputStream.write(input.getBytes());
        outputStream.flush();
		//100 is the response code handled here
        if (200 == httpConnection.getResponseCode()) {
				LOGGER.info("HTTP Service Response code : " + httpConnection.getResponseCode());
        }
        BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
     		   (httpConnection.getInputStream())));

        String output;
        LOGGER.info("Output from Server:\n");
        while ((output = responseBuffer.readLine()) != null) {
            LOGGER.info(output);

            // Below if block is for handling single row data.
            if ((jsonArray != null) && (jsonArray.length() <= 1)) {
                if ((output != null)
                    && (output.contains("not") && (output.indexOf("not") != -1))) {
                    responseMsg = prop.getProperty(ImageConstants.IMAGE_ERROR_STATUS);

                } else {
                    responseMsg =
                        prop.getProperty(ImageConstants.IMAGE_SUCCESS_STATUS);
                }

            } else {
                // This block is for handling multiple row data.
                JsonElement jelement = new JsonParser().parse(output);
                JsonObject jobject = jelement.getAsJsonObject();
                JsonArray jsonObject =(JsonArray) jobject.get(ImageConstants.SERVICE_APPROVEORREJECT_ACTION_VALUE_LIST);

                for (int i = 0; i < jsonObject.size(); i++) {
                    LOGGER.info("ImageRequestServiceImpl::Id value size" + jsonObject.size() + "i value" + i);
                    if (jsonObject.size() == 1) {
                        JsonObject individualjson = jsonObject.getAsJsonObject();
                        Object msgCode =individualjson.get(ImageConstants.MSG_CODE);

                        LOGGER.info("ImageRequestServiceImpl::MsgCode with one json" + msgCode.toString());
                        msgCodeStr = msgCode.toString();
                        msgCodeStr =msgCodeStr.substring(1, msgCodeStr.length() - 1);
                        LOGGER.info("aa" + msgCodeStr);
                    } else {
                        JsonObject individualjson = jsonObject.get(i).getAsJsonObject();
                        Object msgCode =individualjson.get(ImageConstants.MSG_CODE);

                        LOGGER.info("ImageRequestServiceImpl::MsgCode" + msgCode.toString());

                        msgCodeStr = msgCode.toString();
                        msgCodeStr =msgCodeStr.substring(1, msgCodeStr.length() - 1);
                        LOGGER.info("msgCodeStr" + msgCodeStr);
                    }

                    if (msgCodeStr.equalsIgnoreCase(ImageConstants.SUCCESS_CODE)) {
                        flag = true;
                        LOGGER.info("ImageRequestServiceImpl:::callApproveorRejectActionService:::flag" + flag);
                    } else if (msgCodeStr.equalsIgnoreCase(ImageConstants.FAILURE_CODE)) {
                        flag = false;
                    }
                }
                if (flag) {
                    responseMsg = prop.getProperty(ImageConstants.IMAGE_SUCCESS_STATUS);
                    LOGGER.info("ImageRequestServiceImpl:::callApproveorRejectActionService:::responseMsg" + responseMsg);

                } else {
                    responseMsg = prop.getProperty(ImageConstants.IMAGE_ERROR_STATUS);
                    LOGGER.info("ImageRequestServiceImpl:::callApproveorRejectActionService:::responseMsg"+ responseMsg);

                }
            }

        }

        httpConnection.disconnect();
    } catch (MalformedURLException e) {
        LOGGER.info("inside malformedException");
        throw new PEPFetchException();
       // e.printStackTrace();

    } catch (ClassCastException e) {
    	
        e.printStackTrace();
        throw new PEPFetchException();
    } catch (IOException e) {
        LOGGER.info("inside IOException");

        e.printStackTrace();
        throw new Exception();

    } catch (JSONException e) {
        LOGGER.info("inside JSOnException");
        

        e.printStackTrace();
        throw new PEPFetchException();
    } catch (Exception e) {
        LOGGER.info("inside Exception" + e);

        e.printStackTrace();
        throw new Exception();

    }

    return responseMsg;
}

	public String callSubmitOrRejectService(JSONArray jsonArray) throws Exception, PEPFetchException {
		LOGGER.info("Entering callSubmitOrRejectService:: updated ");
		LOGGER.info("jsonArray........ " + jsonArray);
		String responseMsg = "";
		boolean flag = false;
		String msgCodeStr = "";
		String responseCode = "";
		try {
		 	Properties prop = PropertyLoader.getPropertyLoader(ImageConstants.MESS_PROP);
		 	LOGGER.info("prop ::" + prop);
		 	String targetURLs = prop.getProperty(ImageConstants.IMAGE_SUBMITOREJECT_URL);
		 	//String targetURLs = ImageConstants.IMAGE_SUBMITOREJECT_URL;
		 	//String targetURLs = "http://ralpimwsasit02:7507/JERSYRest/rest/UpdateItemServices/imageSubStatusService";
	        LOGGER.info("targetURLs ::" + targetURLs);
	        URL targetUrl = new URL(targetURLs);			
			LOGGER.info("callSubmitOrRejectService serviceURL::" + targetUrl);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type","application/json");
    
			LOGGER.info("callSubmitOrRejectService::Json Array::" + jsonArray.toString());

			JSONObject jsonMap = new JSONObject();
			jsonMap.put(ImageConstants.SERVICE_IMAGE_UPLOAD_LIST,jsonArray);

			String input = jsonMap.toString();
			LOGGER.info("final object in json::" +"\t"+ jsonMap.toString());
			LOGGER.info("input....json" +"\t"+ input);
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();
    

    BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
    String output;
    LOGGER.info("Output from Server1111:::"+"\t"+"after Calling::");
    while ((output = responseBuffer.readLine()) != null) {
        LOGGER.info(output);
        // Below if block is for handling single row data.
        if ((jsonArray != null) && (jsonArray.length() <= 1)) {
            LOGGER.info("Single Request:");
            
            JsonElement jelement1 = new JsonParser().parse(output);
            JsonObject jobject1 = jelement1.getAsJsonObject();
            JsonArray jsonObject1 =(JsonArray) jobject1.get("list");
            
            if ((output != null)
                    && (output.contains("not")||output.contains("may be")) && (output.indexOf("may be") != -1||output.indexOf("not") != -1)) {
                responseMsg = "FAILED";
                responseCode = "101";
                responseMsg = responseMsg+"_"+responseCode;
                LOGGER.info("responseMsg1500::Failure::" + responseMsg);
            }
            else {
              
                responseMsg = "SUCCESS";
                responseCode = "100";
                responseMsg = responseMsg+"_"+responseCode;
                LOGGER.info("responseMsg1510::Success::" + responseMsg);
            }
        }
        else {
            LOGGER.info("Multiple Request:");
            // This block is for handling multiple row data.
            JsonElement jelement = new JsonParser().parse(output);
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray jsonObject =(JsonArray) jobject.get(ImageConstants.SERVICE_IMAGE_UPLOAD_LIST);

            for (int i = 0; i < jsonObject.size(); i++) {                        
                LOGGER.info("callSaveImageShotTypeService::Id value size--1522::"+ jsonObject.size() +"::"+"i-->"+i);                        
                if (jsonObject.size() == 1) {
                    LOGGER.info("Multiple One");
                    JsonObject individualjson = jsonObject.getAsJsonObject();
                    Object msgCode = individualjson.get("code");
                    LOGGER.info("MsgCode::403::" + msgCode.toString());
                    msgCodeStr = msgCode.toString();
                    msgCodeStr = msgCodeStr.substring(1, msgCodeStr.length() - 1);
                    LOGGER.info("msgCodeStr::One--1530::" + msgCodeStr);
                }
                else {
                    LOGGER.info("Multiple Many:");
                    JsonObject individualjson = jsonObject.get(i).getAsJsonObject();
                    Object msgCode = individualjson.get("code");
                    LOGGER.info("ImageRequestServiceImpl::MsgCode--1536::::" + msgCode.toString());

                    msgCodeStr = msgCode.toString();
                    msgCodeStr = msgCodeStr.substring(1, msgCodeStr.length() - 1);
                    LOGGER.info("msgCodeStr--1540::" + msgCodeStr);
                }

                if (msgCodeStr.equalsIgnoreCase("100")) {
                    flag = true;
                    LOGGER.info("flag::" + flag+"msgCodeStr--1545::"+msgCodeStr);
                }
                else if (msgCodeStr.equalsIgnoreCase("101")) {
                    flag = false;
                    LOGGER.info("flag::" + flag+"msgCodeStr--1549::"+msgCodeStr);
                }
            }
            if (flag) {
                responseMsg = "SUCCESS";
                responseMsg = responseMsg+"_"+msgCodeStr;
                LOGGER.info("responseMsg::1555::callSubmitOrRejectService::" + responseMsg);

            }
            else {
                responseMsg = "FAILED";
                responseMsg = responseMsg+"_"+msgCodeStr;
                LOGGER.info("responseMsg::1561" + responseMsg);

            }
        }

    }

    	httpConnection.disconnect();
}
		catch (MalformedURLException e) {
			LOGGER.info("inside malformedException");
				throw new PEPFetchException();
		}
		catch (ClassCastException e) {
			e.printStackTrace();
			throw new PEPFetchException();
		}
		catch (IOException e) {
			LOGGER.info("inside IOException");
			e.printStackTrace();
		}
		catch (JSONException e) {
			LOGGER.info("inside JSOnException callSubmitOrRejectService");
			e.printStackTrace();
		}
		catch (Exception e) {
			LOGGER.info("inside Exception" + e);
				e.printStackTrace();
		}
		LOGGER.info("Exiting callSubmitOrRejectService:::" +responseMsg);
			return responseMsg;
	}

/**
 * 
 * @param jsonArray
 * @return
 * @throws Exception
 * @throws PEPFetchException
 */
@Override
public String callSaveImageShotTypeService(JSONArray jsonArray)
    throws Exception, PEPFetchException {
    LOGGER.info("Entering callSaveImageShotTypeService::");
    LOGGER.info("jsonArray........ " + jsonArray);
    String responseMsg = "";
    boolean flag = false;
    String msgCodeStr = "";
    String responseCode = "";
    try {
    	Properties prop =PropertyLoader.getPropertyLoader(ImageConstants.MESS_PROP);
        String targetURLs = prop.getProperty(ImageConstants.IMAGE_SAVESHOTTYPE_URL);
        //String targetURLs = ImageConstants.IMAGE_SAVESHOTTYPE_URL;
    	//String targetURLs = "http://ralpimwsasit02:7507/JERSYRest/rest/UpdateItemServices/updateImageShotType";
        LOGGER.info("callSaveImageShotTypeService serviceURL::" + targetURLs);
        URL targetUrl = new URL(targetURLs);
        HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type","application/json");
        
        LOGGER.info("callSaveImageShotTypeService::Json Array::" + jsonArray.toString());

        JSONObject jsonMap = new JSONObject();
        jsonMap.put("list",jsonArray);

        String input = jsonMap.toString();
        LOGGER.info("final object in json::" +"\t"+ jsonMap.toString());
        LOGGER.info("input....json" +"\t"+ input);
        OutputStream outputStream = httpConnection.getOutputStream();
        outputStream.write(input.getBytes());
        outputStream.flush();
        BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
        String output;
        boolean flagSuccess = false;
        boolean flagFail = false;
        LOGGER.info("Output from Server1111:::"+"\t"+"after Calling::");
        while ((output = responseBuffer.readLine()) != null) {
            LOGGER.info(output);
            // Below if block is for handling single row data.
            if ((jsonArray != null) && (jsonArray.length() <= 1)) {
                LOGGER.info("Single Request:");
                //TODO if ((output != null)                  
                if ((output != null)
                        && (output.contains("not")||output.contains("may be")) && (output.indexOf("may be") != -1||output.indexOf("not") != -1)) {
                    
                	
                	responseMsg = "FAILED";
                	responseCode ="101";
                	responseMsg = responseMsg +"_"+responseCode;
                    LOGGER.info("responseMsg1628::" + responseMsg);
                }
                else {  
                	
                    responseMsg = "SUCCESSS";
                    responseCode ="100";
                    responseMsg = responseMsg +"_"+responseCode;
                    LOGGER.info("responseMsg1638::" + responseMsg);
                }
            }
            else {
                LOGGER.info("Multiple Request:");
                // This block is for handling multiple row data.
                JsonElement jelement = new JsonParser().parse(output);
                JsonObject jobject = jelement.getAsJsonObject();
                JsonArray jsonObject =(JsonArray) jobject.get("list");

                for (int i = 0; i < jsonObject.size(); i++) {                        
                    LOGGER.info("callSaveImageShotTypeService::Id value size::399::"+ jsonObject.size() +"::"+"i-->"+i);                        
                    if (jsonObject.size() == 1) {
                        LOGGER.info("Multiple One");
                        JsonObject individualjson = jsonObject.getAsJsonObject();
                        Object msgCode = individualjson.get("code");
                        LOGGER.info("MsgCode1654::" + msgCode.toString());
                        msgCodeStr = msgCode.toString();
                        msgCodeStr = msgCodeStr.substring(1, msgCodeStr.length() - 1);
                        LOGGER.info("msgCodeStr::1657::" + msgCodeStr);
                    }
                    else {
                        LOGGER.info("Multiple Many:");
                        JsonObject individualjson = jsonObject.get(i).getAsJsonObject();
                        Object msgCode = individualjson.get("code");
                        LOGGER.info("MsgCode::1663::" + msgCode.toString());

                        msgCodeStr = msgCode.toString();
                        msgCodeStr = msgCodeStr.substring(1, msgCodeStr.length() - 1);
                        LOGGER.info("MsgCode::1667::" + msgCodeStr);
                    }

                    if (msgCodeStr.equalsIgnoreCase("100")) {
                        flag = true;
                        flagSuccess = true;
                        LOGGER.info("flag::" + flag+"msgCodeStr::1672::"+msgCodeStr);
                    }
                    else if (msgCodeStr.equalsIgnoreCase("101")) {
                        flag = false;
                        flagFail = true;
                        LOGGER.info("flag::" + flag+"msgCodeStr::1676::"+msgCodeStr);
                    }
                }
                if (flagSuccess) {
                    responseMsg = "SUCCESS";
                    responseMsg = responseMsg+"_"+"100";
                    LOGGER.info("responseMsg::1682::" + responseMsg);

                }
                else if(flagFail) {
                    responseMsg = "FAILED";
                    responseMsg = responseMsg+"_"+"101";
                    LOGGER.info("responseMsg::1687::" + responseMsg);

                }
                else{
                	 responseMsg = "FAILED";
                     responseMsg = responseMsg+"_"+"101";
                     LOGGER.info("responseMsg::1687::" + responseMsg);
                }
            }

        }

        httpConnection.disconnect();
    }
    catch (MalformedURLException e) {
        LOGGER.info("inside malformedException");
        throw new PEPFetchException();
    }
    catch (ClassCastException e) {
        e.printStackTrace();
        throw new PEPFetchException();
    }
    catch (IOException e) {
        LOGGER.info("inside IOException");
        e.printStackTrace();
    }
    catch (JSONException e) {
        LOGGER.info("inside JSOnException");
        e.printStackTrace();
        throw new PEPFetchException();
    }
    catch (Exception e) {
        LOGGER.info("inside Exception" + e);
        e.printStackTrace();
        throw new Exception();
    }
    LOGGER.info("Exiting callSaveImageShotTypeService:::" +responseMsg);
    return responseMsg;
}

public boolean releseLockedPet(  String orin, String pepUserID,String pepFunction)throws PEPPersistencyException {
    LOGGER.info("releseLockedPet service :: lockPET"); 
    boolean  isPetReleased  = false;
    try {    	  
    	isPetReleased = imageRequestDAO.releseLockedPet(orin,pepUserID,pepFunction);
    } 
    catch (PEPPersistencyException e) {        
        LOGGER.info("Exception occurred at the Service DAO Layer");
        throw e;
    }
        
    return isPetReleased;
    
}   

	/**
	 * Method to get the Image attribute details from database.
	 *    
	 * @param orin String   
	 * @return imageLinkVOList List<ImageLinkVO>
	 * 
	 * Method added For PIM Phase 2 - Regular Item Image Link Attribute
	 * Date: 05/13/2016
	 * Added By: Cognizant
	 */	
	@Override
	public List<ImageLinkVO> getScene7ImageLinks(String orin) throws PEPServiceException, PEPPersistencyException{
		
		LOGGER.info("***Entering ImageRequestService.getScene7ImageLinks() method.");
	    
		List<ImageLinkVO> imageLinkVOList = null;
		
	    try {
	    	imageLinkVOList = imageRequestDAO.getScene7ImageLinks(orin);
	    }
	    catch (PEPPersistencyException persistencyException) {
	
	    	LOGGER.error("PEPPersistencyException in getScene7ImageLinks() method, Service Layer -- " + persistencyException.getMessage());
	        throw persistencyException;
	    }
	    catch (Exception exception) {
	
	    	LOGGER.error("Exception in getScene7ImageLinks() method, Service Layer -- " + exception.getMessage());
	        throw new PEPServiceException(exception.getMessage());
	    }
	    LOGGER.info("***Exiting ImageRequestService.getScene7ImageLinks() method.");
	    return imageLinkVOList;
	    
	}
    
}
