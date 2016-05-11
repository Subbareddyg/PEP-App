package com.belk.pep.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.belk.pep.constants.CreatePETPortalConstants;
import com.belk.pep.dao.CreatePETDao;
import com.belk.pep.exception.checked.OrinNotFoundException;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.service.CreatePETService;
import com.belk.pep.util.PropertyLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class CreatePETServiceImpl.
 */
public class CreatePETServiceImpl implements CreatePETService {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger
        .getLogger(CreatePETServiceImpl.class.getName());

    /** The Create PET dao. */
    private CreatePETDao createPETDao;

    public String createPetWebService(JSONArray jsonArray) throws Exception,PEPFetchException {
        LOGGER.info("CreatePETServiceImpl:::createPetWebService");

        String responseMsg = "";
        boolean flag = false;
        String msgCodeStr = "";
        try {
            Properties prop =
                PropertyLoader
                    .getPropertyLoader(CreatePETPortalConstants.MESS_PROP);
            String targetURLs = prop.getProperty(CreatePETPortalConstants.DEV_SERVICE_URL);
            //String targetURLs = CreatePETPortalConstants.DEV_SERVICE_URL;
            URL targetUrl = new URL(targetURLs);
            HttpURLConnection httpConnection =
                (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type",
                "application/json");

            LOGGER.info("CreatePETServiceImpl::Json Array"
                + jsonArray.toString());

            JSONObject jsonMap = new JSONObject();

            jsonMap.put(CreatePETPortalConstants.SERVICE_PETS, jsonArray);
            String input = jsonMap.toString();
            LOGGER.info("final object in json" + jsonMap.toString());
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            if (200 == httpConnection.getResponseCode()) {
            }

            if (httpConnection.getResponseCode() != 200) {
             //   throw new Exception("Failed : HTTP error code : "
                   // + httpConnection.getResponseCode());
            	
            }

            BufferedReader responseBuffer =
                new BufferedReader(new InputStreamReader(
                    (httpConnection.getInputStream())));

            String output;

            LOGGER.info("Output from Server:\n");
            while ((output = responseBuffer.readLine()) != null) {
                LOGGER.info(output);

                // Below if block is for handling single row data.
                if ((jsonArray != null) && (jsonArray.length() <= 1)) {
                    if ((output != null) && (output.contains("not") && (output.indexOf("not") != -1))) {                    
                        responseMsg = CreatePETPortalConstants.PET_NOT_CREATED;
                    } else if((output != null) && (output.contains("already") && (output.indexOf("already") != -1))) {          	
                    	 responseMsg = prop.getProperty(CreatePETPortalConstants.NOT_MET_PET_CRITERIA);
                    }else if((output != null) && (output.contains("Something") && (output.indexOf("Something") != -1))){
                    	  responseMsg = CreatePETPortalConstants.PET_NOT_CREATED;
                    }                    
                    else{                  
                    	responseMsg =  prop.getProperty(CreatePETPortalConstants.RESPONSE_MSG);
                   }

                } else {
                    // This block is for handling multiple row data.
                    JsonElement jelement = new JsonParser().parse(output);
                    JsonObject jobject = jelement.getAsJsonObject();
                    JsonArray jsonObject =
                        (JsonArray) jobject
                            .get(CreatePETPortalConstants.SERVICE_PETS);

                    for (int i = 0; i < jsonObject.size(); i++) {
                        LOGGER.info("CreatePETServiceImpl::Id value size"
                            + jsonObject.size() + "i value" + i);
                        if (jsonObject.size() == 1) {
                            JsonObject individualjson =
                                jsonObject.getAsJsonObject();
                            Object msgCode =
                                individualjson
                                    .get(CreatePETPortalConstants.MSG_CODE);

                            LOGGER
                                .info("CreatePETServiceImpl::MsgCode with one json"
                                    + msgCode.toString());
                            msgCodeStr = msgCode.toString();
                            msgCodeStr =
                                msgCodeStr
                                    .substring(1, msgCodeStr.length() - 1);
                            LOGGER.info("aa" + msgCodeStr);
                        } else {
                            JsonObject individualjson =
                                jsonObject.get(i).getAsJsonObject();
                            Object msgCode =
                                individualjson
                                    .get(CreatePETPortalConstants.MSG_CODE);

                            LOGGER.info("CreatePETServiceImpl::MsgCode"
                                + msgCode.toString());

                            msgCodeStr = msgCode.toString();

                            msgCodeStr =
                                msgCodeStr
                                    .substring(1, msgCodeStr.length() - 1);
                            LOGGER.info("msgCodeStr" + msgCodeStr);
                        }

                        if (msgCodeStr
                            .equalsIgnoreCase(CreatePETPortalConstants.SUCCESS_CODE)) {
                            flag = true;

                            LOGGER
                                .info("CreatePETServiceImpl:::createPetWebService:::flag"
                                    + flag);
                            break;
                        } else if (msgCodeStr
                            .equalsIgnoreCase(CreatePETPortalConstants.FAILURE_CODE)) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        responseMsg =
                            prop.getProperty(CreatePETPortalConstants.RESPONSE_MSG);
                        LOGGER
                            .info("CreatePETServiceImpl:::createPetWebService:::responseMsg"
                                + responseMsg);

                    } else {
                        responseMsg = CreatePETPortalConstants.PET_NOT_CREATED;

                        LOGGER
                            .info("CreatePETServiceImpl:::createPetWebService:::responseMsg"
                                + responseMsg);

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

    /*
     * (non-Javadoc)
     * 
     * @see belk.com.pep.service.fetchPETDetails
     */
    public ArrayList<WorkFlow> fetchPETDetails(String orinNo)
        throws PEPFetchException,OrinNotFoundException {
        LOGGER.info("CreatePETServiceImpl:::fetchUserDetails");

        ArrayList<WorkFlow> arrList = new ArrayList<WorkFlow>();
        try {
        	
            arrList = createPETDao.fetchPETDetails(orinNo);
            
            LOGGER.info("CreatePETServiceImpl::workflows" + arrList.size());

            Iterator workFlowEach = arrList.iterator();
            while (workFlowEach.hasNext()) {
                WorkFlow workflow = (WorkFlow) workFlowEach.next();
                LOGGER.info(" workflow CreatePETServiceImpl " + workflow);
                LOGGER.info("CreatePETServiceImpl " + workflow.getOrin());
            }

        } catch (OrinNotFoundException onfe) {
            throw onfe;            
        } catch (PEPFetchException pfe) {
            throw pfe;            
        } catch (PEPPersistencyException e) {
            e.printStackTrace();
            e.getCause();

            LOGGER.info("Exception occurred at the  Implementation Layer");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

            LOGGER.info("Exception occurred at the  Implementation Layer");
        }

        return arrList;

    }

    /**
     * @return the createPETDao
     */
    public CreatePETDao getCreatePETDao() {
        return createPETDao;
    }

    /**
     * @param createPETDao
     *            the createPETDao to set
     */
    public void setCreatePETDao(CreatePETDao createPETDao) {
        this.createPETDao = createPETDao;
    }

    /**
     * Validate Orin.
     * 
     * @param String
     *            orinNumber
     * @return String
     * 
     */
    public String validateOrin(String orinNumber) {
        LOGGER.info("CreatePETServiceImpl:::validateOrin");

        String result = "";
        try {
            result = createPETDao.validateOrin(orinNumber);

        } catch (PEPPersistencyException e) {

            LOGGER
                .info("Exception occurred at the Service Implementation Layer");
        } catch (Exception e) {
            LOGGER
                .info("Exception occurred at the Service Implementation Layer");
        }

        return result;

    }
}
