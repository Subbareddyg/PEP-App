package com.belk.pep.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.belk.pep.constants.WorkListDisplayConstants;
import com.belk.pep.dao.WorkListDisplayDAO;
import com.belk.pep.domain.PepDepartment;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.model.AdvanceSearch;
import com.belk.pep.model.PetsFound;
import com.belk.pep.model.StyleColor;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.service.WorkListDisplayService;
import com.belk.pep.util.ClassDetails;
import com.belk.pep.util.PropertiesFileLoader;
import com.belk.pep.util.PropertyLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class WorkListDisplayServiceImpl.
 */
public class WorkListDisplayServiceImpl implements WorkListDisplayService {
    
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(WorkListDisplayServiceImpl.class.getName()); 
    
    
    /** The workListDisplayDAO. */
    private WorkListDisplayDAO workListDisplayDAO;

    

    
    /**
     * Gets the work list display dao.
     *
     * @return the work list display dao
     */
    public WorkListDisplayDAO getWorkListDisplayDAO() {
        return workListDisplayDAO;
    }


    /**
     * Sets the work list display dao.
     *
     * @param workListDisplayDAO the new work list display dao
     */
    public void setWorkListDisplayDAO(WorkListDisplayDAO workListDisplayDAO) {
        this.workListDisplayDAO = workListDisplayDAO;
    }


    /**
     * This method will get the Department details from the PEP_DEPARTMENT table using the pepUserId
     * @param email
     * @return
     * @throws PEPPersistencyException 
     * @throws SQLException
     */
    public ArrayList getSavedDepartmentDetailsByPepUserId(String pepUserId) throws PEPServiceException, PEPPersistencyException{
        //getting the Department  details from Database
        LOGGER.info("****calling getSavedDepartmentDetailsByPepUserId from WorkListDisplayServiceImpl****");
        ArrayList departmentList=null;
        try {
            departmentList = workListDisplayDAO.getSavedDepartmentDetailsByPepUserId(pepUserId);
        }
        catch (PEPPersistencyException e) {
            
            LOGGER.info("Exception occurred at the Service DAO Layer");
            throw e;
        }
        
        catch (Exception e) {
              
                LOGGER.info("Exception occurred at the Service Implementation Layer");
                throw new PEPServiceException(e.getMessage());
            }
        
        return departmentList;
    }
    
    /**
     * This method will return the PET details from the ADSE on base of department 
     * @param departmentNumbers
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    
    public List<WorkFlow> getPetDetailsByDepNos(ArrayList departmentNumbers,String email,List<String> supplierIdList)
        throws PEPServiceException, PEPPersistencyException {
      //getting the Pet  details from ADSE Database
        LOGGER.info("****calling getPetDetailsByDepNos from WorkListDisplayServiceImpl****");
        List<WorkFlow> petList=null;
        try {
            petList = workListDisplayDAO.getPetDetailsByDepNos(departmentNumbers,email,supplierIdList);
        }
        catch (PEPPersistencyException e) {
            
            LOGGER.info("Exception occurred at the Service DAO Layer");
            throw e;
        }
        
        catch (Exception e) {
            LOGGER.info("Before printing stack trace");
                e.printStackTrace();
                LOGGER.info("After printing stack trace");
                LOGGER.info("Exception occurred at the Service Implementation Layer");
                throw new PEPServiceException(e.getMessage());
            }
        
        return petList;
    }
    
    
    public List<WorkFlow> getPetDetailsByDepNosForParent(List departmentNumbers,String email,List<String> supplierIdList,
    		int startIndex, int maxResults, String sortColumn, String sortOrder)
    throws PEPPersistencyException,PEPServiceException {
  //getting the Pet  details from ADSE Database
    LOGGER.info("****calling getPetDetailsByDepNosForParent from WorkListDisplayServiceImpl****");
    List<WorkFlow> petList=null;
    try {
        petList = workListDisplayDAO.getPetDetailsByDepNosForParent(departmentNumbers,email,supplierIdList, startIndex, maxResults, 
        		sortColumn, sortOrder);
        
    }
    catch (PEPPersistencyException e) {
        
        LOGGER.info("Exception occurred at the Service DAO Layer getPetDetailsByDepNosForParent");
        throw e;
    }
    
    catch (Exception e) {
        LOGGER.info("Before printing stack trace");
            e.printStackTrace();
            LOGGER.info("After printing stack trace");
            LOGGER.info("Exception occurred at the Service Implementation Layer getPetDetailsByDepNosForParent");
            throw new PEPServiceException(e.getMessage());
        }
    
    return petList;
}
    
    
    public List<StyleColor> getPetDetailsByDepNosForChild(String email,String parentOrin)
    throws PEPPersistencyException{

        //getting the Pet  details from ADSE Database
          LOGGER.info("****calling getPetDetailsByDepNosForParent from WorkListDisplayServiceImpl****");
          List<StyleColor> petList=null;
          try {
              petList = workListDisplayDAO.getPetDetailsByDepNosForChild(email,parentOrin);
              
          }
          catch (PEPPersistencyException e) {
              
              LOGGER.info("Exception occurred at the Service DAO Layer getPetDetailsByDepNosForParent");
              throw e;
          }
              
          
          
          
          return petList;

    }
    
/**
 * This method will update the Department details in to the DB
 * @throws PEPPersistencyException 
 * @throws PEPServiceException 
 */

    @Override
    public boolean updatePepDeptDetails(
        List<PepDepartment> updatedPePDetailsToDb, String pepUserID) throws PEPPersistencyException, PEPServiceException {
        LOGGER.info("****calling updatePepDeptDetails from WorkListDisplayServiceImpl****");
        boolean isDeptUpdated = false;
        try {
            isDeptUpdated = workListDisplayDAO.updatePepDeptDetails(updatedPePDetailsToDb,pepUserID);
        }
        catch (PEPPersistencyException e) {
            LOGGER.info("Exception occurred at the Service DAO Layer");
            throw e;
        }
        
        catch (Exception e) {
              
                LOGGER.info("Exception occurred at the Service Implementation Layer");
                throw new PEPServiceException(e.getMessage());
            }
        
        return isDeptUpdated;
    }

    /**
     * This method will return the department details on base of dependent no/desc from ADSE table
     */
    @SuppressWarnings("unchecked")
    @Override
    public List getDeptDetailsByDepNoFromADSE(String departmentsToBesearched)
        throws PEPServiceException, PEPPersistencyException {
        //getting the Pet  details from ADSE Database
        LOGGER.info("****calling getDeptDetailsByDepNoFromADSE from WorkListDisplayServiceImpl****");
        ArrayList searchDepartmentDetailsFromADSE=null;
        try {
            searchDepartmentDetailsFromADSE = workListDisplayDAO.getDeptDetailsByDepNoFromADSE(departmentsToBesearched);
            //For the defect 24 
            
        }
        catch (PEPPersistencyException e) {
            
            LOGGER.info("Exception occurred at the Service DAO Layer");
            throw e;
        }
        
        catch (Exception e) {
              
                LOGGER.info("Exception occurred at the Service Implementation Layer");
                throw new PEPServiceException(e.getMessage());
            }
        
        return searchDepartmentDetailsFromADSE;
    }

/**
 * This method will return the Department Details from ADSE table on base of Lan ID for internal User
 */
    @Override
    public List getDepartmentDetailsForInernalFirstTimeLogin(String lanId)
        throws PEPServiceException, PEPPersistencyException {
        LOGGER.info("****calling getDepartmentDetailsForInernalFirstTimeLogin from WorkListDisplayServiceImpl****");
        ArrayList departmentDetailsFromADSEByLanId=null;
        try {
            departmentDetailsFromADSEByLanId = workListDisplayDAO.getDepartmentDetailsForInernalFirstTimeLogin(lanId);
        }
        catch (PEPPersistencyException e) {
            
            LOGGER.info("Exception occurred at the Service DAO Layer");
            throw e;
        }
        
        catch (Exception e) {
              
                LOGGER.info("Exception occurred at the Service Implementation Layer");
                throw new PEPServiceException(e.getMessage());
            }
        
        return departmentDetailsFromADSEByLanId;
    }
    
    /**
     * This method will return the Pet details on base of Vendor Email
     */

@Override
public List<WorkFlow> getPetDetailsByVendor(String userEmailAddress, List<String> supplierIdList)
    throws PEPServiceException, PEPPersistencyException {
    LOGGER.info("****calling getPetDetailsByVendor from WorkListDisplayServiceImpl****");
    List<WorkFlow> petDetailsByVendorInput=null;
    try {
        petDetailsByVendorInput = workListDisplayDAO.getPetDetailsByVendor(userEmailAddress, supplierIdList);
    }
    catch (PEPPersistencyException e) {
        
        LOGGER.info("Exception occurred at the Service DAO Layer");
        throw e;
    }
    
    catch (Exception e) {
          
            LOGGER.info("Exception occurred at the Service Implementation Layer");
            throw new PEPServiceException(e.getMessage());
        }
    
    return petDetailsByVendorInput;
}

/**
 * This method will return the Department details for the External user for the first time using Email address
 */
    @Override
    public List getDepartmentDetailsForExternalFirstTimeLogin(
        String userEmailAddress)  throws PEPServiceException, PEPPersistencyException{
        LOGGER.info("****calling getDepartmentDetailsForExternalFirstTimeLogin from WorkListDisplayServiceImpl****");
        ArrayList departmentDetailsForExternalByEmailAddress = null;
        try {
            departmentDetailsForExternalByEmailAddress = workListDisplayDAO.getDepartmentDetailsForExternalFirstTimeLogin(userEmailAddress);
        }
        catch (PEPPersistencyException e) {
            
            LOGGER.info("Exception occurred at the Service DAO Layer");
            throw e;
        }
        
        catch (Exception e) {
              
                LOGGER.info("Exception occurred at the Service Implementation Layer");
                throw new PEPServiceException(e.getMessage());
            }
        
        return departmentDetailsForExternalByEmailAddress;
    }

/**
 * This method will return the Class details from the ADSE on base of department 
 */
@Override
public List<ClassDetails> getClassDetailsByDepNos(String departmentNumbers)
    throws PEPServiceException, PEPPersistencyException {
    LOGGER.info("****calling getClassDetailsByDepNos from WorkListDisplayServiceImpl****");
    List<ClassDetails> classDetailsForDeptNumber = null;
    try {
        classDetailsForDeptNumber = workListDisplayDAO.getClassDetailsByDepNos(departmentNumbers);
    }
    catch (PEPPersistencyException e) {
        
        LOGGER.info("Exception occurred at the Service DAO Layer getClassDetailsByDepNos");
        throw e;
    }
    
    catch (Exception e) {
          
            LOGGER.info("Exception occurred at the Service Implementation Layer getClassDetailsByDepNos");
            throw new PEPServiceException(e.getMessage());
        }
    
    return classDetailsForDeptNumber;
   
}

/**
 * This method will fetch the WorkList Details on base of the Advance search.
 */
@Override
public List<WorkFlow> getPetDetailsByAdvSearch(AdvanceSearch advanceSearch,List<String> supplierIdList,String vendorEmail)
    throws PEPServiceException, PEPPersistencyException {
    LOGGER.info("****calling getPetDetailsByAdvSearch from WorkListDisplayServiceImpl****");
    List<WorkFlow> workFlowList = null;
    try {
        workFlowList = workListDisplayDAO.getPetDetailsByAdvSearch(advanceSearch,supplierIdList,vendorEmail);
    }
    catch (PEPPersistencyException e) {
        
        LOGGER.info("Exception occurred at the Service DAO Layer getPetDetailsByAdvSearch");
        throw e;
    }
    
    catch (Exception e) {
          
            LOGGER.info("Exception occurred at the Service Implementation Layer getPetDetailsByAdvSearch");
            throw new PEPServiceException(e.getMessage());
        }
    
    return workFlowList;
}

@Override
public List<WorkFlow> getPetDetailsByAdvSearchForParent(AdvanceSearch advanceSearch,List<String> supplierIdList,String vendorEmail)
    throws PEPServiceException, PEPPersistencyException {
    LOGGER.info("****calling getPetDetailsByAdvSearchForParent from WorkListDisplayServiceImpl****");
    List<WorkFlow> workFlowList = null;
    try {
        workFlowList = workListDisplayDAO.getPetDetailsByAdvSearchForParent(advanceSearch,supplierIdList,vendorEmail);
    }
    catch (PEPPersistencyException e) {
        
        LOGGER.info("Exception occurred at the Service DAO Layer getPetDetailsByAdvSearchForParent");
        throw e;
    }
    
    catch (Exception e) {
          
            LOGGER.info("Exception occurred at the Service Implementation Layer getPetDetailsByAdvSearchForParent");
            throw new PEPServiceException(e.getMessage());
        }
    
    return workFlowList;
}

@Override
public List<StyleColor> getPetDetailsByAdvSearchForChild(AdvanceSearch advanceSearch, String parentOrin)
throws PEPPersistencyException {
    LOGGER.info("****calling getPetDetailsByAdvSearchForChild from WorkListDisplayServiceImpl****");
    List<StyleColor> workFlowList = null;
    try {
        workFlowList = workListDisplayDAO.getPetDetailsByAdvSearchForChild(advanceSearch,parentOrin);
    }
    catch (PEPPersistencyException e) {
        
        LOGGER.info("Exception occurred at the Service DAO Layer getPetDetailsByAdvSearchForChild");
        throw e;
    }
    
    catch (Exception e) {
          
            LOGGER.info("Exception occurred at the Service Implementation Layer getPetDetailsByAdvSearchForChild");
            throw new PEPPersistencyException(e.getMessage());
        }
    
    return workFlowList;
}



/**
 * Default Inactivate service call
 */
public String callInActivateAndActivatePetService(JSONArray jsonArray)
    throws Exception, PEPFetchException {
    LOGGER.info("Entering callInActivateAndActivatePetService::");
    LOGGER.info("jsonArray........ " + jsonArray);
    String responseMsg = "";
    boolean flag = false;
    String msgCodeStr = "";
    try {
        Properties prop =PropertyLoader.getPropertyLoader(WorkListDisplayConstants.MESS_PROP);
        String serviceURL = prop.getProperty(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_URL);
        //String serviceURL = WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_URL; // AFUPYB3
        LOGGER.info("serviceURL::" + serviceURL);
        System.out.println("Service URL--------------------------------------------------------------->"+serviceURL);
        
        URL targetUrl = new URL(serviceURL);
        HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type","application/json");
        
        LOGGER.info("callInActivateAndActivatePetService::Json Array::" + jsonArray.toString());

        JSONObject jsonMap = new JSONObject();
        jsonMap.put(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_LIST,jsonArray);

        String input = jsonMap.toString();
        LOGGER.info("final object in json::" +"\t"+ jsonMap.toString());
        LOGGER.info("input....json" +"\t"+ input);
        OutputStream outputStream = httpConnection.getOutputStream();
        outputStream.write(input.getBytes());
        outputStream.flush();
        
        BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
        String output;
        LOGGER.info("Output from Server:::"+"\t"+"after Calling::");
        while ((output = responseBuffer.readLine()) != null) {
            LOGGER.info(output);
            // Below if block is for handling single row data.
            if ((jsonArray != null) && (jsonArray.length() <= 1)) {
                LOGGER.info("Single Request:");

                if ((output != null)
                        && (output.contains("not")||output.contains("may be")) && (output.indexOf("may be") != -1||output.indexOf("not") != -1)) {
                    responseMsg = prop.getProperty(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_FAILURE);
                    LOGGER.info("responseMsg379::Failure::" + responseMsg);
                }
                else {
                    responseMsg = prop.getProperty(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_SUCCESS);
                    LOGGER.info("responseMsg387::Success::" + responseMsg);
                }
            }
            else {
                LOGGER.info("Multiple Request:");
                // This block is for handling multiple row data.
                JsonElement jelement = new JsonParser().parse(output);
                JsonObject jobject = jelement.getAsJsonObject();
                JsonArray jsonObject =(JsonArray) jobject.get(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_LIST);

                for (int i = 0; i < jsonObject.size(); i++) {                        
                    LOGGER.info("inactivateRequestServiceImpl::Id value size::399::"+ jsonObject.size() +"::"+"i-->"+i);                        
                    if (jsonObject.size() == 1) {
                        LOGGER.info("Multiple One");
                        JsonObject individualjson = jsonObject.getAsJsonObject();
                        Object msgCode = individualjson.get(WorkListDisplayConstants.MSG_CODE);
                        LOGGER.info("MsgCode::403::" + msgCode.toString());
                        msgCodeStr = msgCode.toString();
                        msgCodeStr = msgCodeStr.substring(1, msgCodeStr.length() - 1);
                        LOGGER.info("msgCodeStr::One::407::" + msgCodeStr);
                    }
                    else {
                        LOGGER.info("Multiple Many:");
                        JsonObject individualjson = jsonObject.get(i).getAsJsonObject();
                        Object msgCode = individualjson.get(WorkListDisplayConstants.MSG_CODE);
                        LOGGER.info("ImageRequestServiceImpl::MsgCode::414::" + msgCode.toString());

                        msgCodeStr = msgCode.toString();
                        msgCodeStr = msgCodeStr.substring(1, msgCodeStr.length() - 1);
                        LOGGER.info("msgCodeStr::417::" + msgCodeStr);
                    }

                    if (msgCodeStr.equalsIgnoreCase(WorkListDisplayConstants.SUCCESS_CODE)) {
                        flag = true;
                        LOGGER.info("flag::" + flag+"msgCodeStr::421::"+msgCodeStr);
                    }
                    else if (msgCodeStr.equalsIgnoreCase(WorkListDisplayConstants.FAILURE_CODE)) {
                        flag = false;
                        LOGGER.info("flag::" + flag+"msgCodeStr::425::"+msgCodeStr);
                    }
                }
                if (flag) {
                    responseMsg = prop.getProperty(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_SUCCESS);
                    responseMsg = responseMsg+"_"+msgCodeStr;
                    LOGGER.info("responseMsg::431::" + responseMsg);

                }
                else {
                    responseMsg = prop.getProperty(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_FAILURE);
                    responseMsg = responseMsg+"_"+msgCodeStr;
                    LOGGER.info("responseMsg::436" + responseMsg);

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
    }
    catch (Exception e) {
        LOGGER.info("inside Exception" + e);
        e.printStackTrace();
    }
    LOGGER.info("Exiting WorkListDisplayServiceImpl:::callInActivateAndActivatePetService::" +responseMsg);
    return responseMsg;
}

/**
 * 
 */
public String callReInitiatePetService(JSONArray jsonArray)
throws Exception, PEPFetchException {
LOGGER.info("Entering callReInitiatePetService::");
LOGGER.info("jsonArray*****" + jsonArray);
String responseMsg = "";
boolean flag = false;
String msgCodeStr = "";
String singleResponseCode = "";
try {
    Properties prop =PropertyLoader.getPropertyLoader(WorkListDisplayConstants.MESS_PROP);
    String serviceURL = prop.getProperty(WorkListDisplayConstants.REINITIATE_SERVICE_URL);
    //String serviceURL = WorkListDisplayConstants.REINITIATE_SERVICE_URL; // AFUPYB3
    LOGGER.info("reinitiate...serviceURL::" + serviceURL);
    URL targetUrl = new URL(serviceURL);
    HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
    httpConnection.setDoOutput(true);
    httpConnection.setRequestMethod("POST");
    httpConnection.setRequestProperty("Content-Type","application/json");
    
    LOGGER.info("callReInitiatePetService::Json Array::" + jsonArray.toString());

    JSONObject jsonMap = new JSONObject();
    jsonMap.put(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_LIST,jsonArray);

    String input = jsonMap.toString();
    LOGGER.info("final object in json::" +"\t"+ jsonMap.toString());
    LOGGER.info("input....json" +"\t"+ input);
    OutputStream outputStream = httpConnection.getOutputStream();
    outputStream.write(input.getBytes());
    outputStream.flush();
    
    BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
    String output;
    LOGGER.info("Output from Server:::"+"\t"+"after Calling::");
    while ((output = responseBuffer.readLine()) != null) {
        LOGGER.info(output);
        // Below if block is for handling single row data.
        if ((jsonArray != null) && (jsonArray.length() <= 1)) {
            LOGGER.info("Single Request:");
            if ((output != null)
                    && (output.contains("not")||output.contains("may be") || output.contains("failed")) && (output.indexOf("may be") != -1||output.indexOf("not") != -1 || output.indexOf("failed") != -1)) {
                singleResponseCode = "101";
                responseMsg = prop.getProperty(WorkListDisplayConstants.REINITIATE_SERVICE_FAILURE);
                responseMsg = responseMsg+"_"+singleResponseCode;
                LOGGER.info("responseMsg--Failure:" + responseMsg);
            }
            else {
                responseMsg = prop.getProperty(WorkListDisplayConstants.REINITIATE_SERVICE_SUCCESS);
                singleResponseCode = "100";
                responseMsg = responseMsg+"_"+singleResponseCode;
                LOGGER.info("responseMsg387---Success:--" + responseMsg);
            }
        }
        else {
            LOGGER.info("Multiple Request:");
            // This block is for handling multiple row data.
            JsonElement jelement = new JsonParser().parse(output);
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray jsonObject =(JsonArray) jobject.get(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_LIST);

            for (int i = 0; i < jsonObject.size(); i++) {                        
                LOGGER.info("callReInitiatePetService---value size--"+ jsonObject.size() +"**"+"i-->"+i);                        
                if (jsonObject.size() == 1) {
                    LOGGER.info("Multiple One");
                    JsonObject individualjson = jsonObject.getAsJsonObject();
                    Object msgCode = individualjson.get(WorkListDisplayConstants.MSG_CODE);
                    LOGGER.info("MsgCode--callReInitiatePetService--" + msgCode.toString());
                    msgCodeStr = msgCode.toString();
                    msgCodeStr = msgCodeStr.substring(1, msgCodeStr.length() - 1);
                    LOGGER.info("msgCodeStr::One--**" + msgCodeStr);
                }
                else {
                    LOGGER.info("Multiple Many:");
                    JsonObject individualjson = jsonObject.get(i).getAsJsonObject();
                    Object msgCode = individualjson.get(WorkListDisplayConstants.MSG_CODE);
                    LOGGER.info("callReInitiatePetService::MsgCode::414::" + msgCode.toString());

                    msgCodeStr = msgCode.toString();
                    msgCodeStr = msgCodeStr.substring(1, msgCodeStr.length() - 1);
                    LOGGER.info("msgCodeStr::417::" + msgCodeStr);
                }

                if (msgCodeStr.equalsIgnoreCase(WorkListDisplayConstants.SUCCESS_CODE)) {
                    flag = true;
                    LOGGER.info("flag::" + flag+"msgCodeStr::421::"+msgCodeStr);
                }
                else if (msgCodeStr.equalsIgnoreCase(WorkListDisplayConstants.FAILURE_CODE)) {
                    flag = false;
                    LOGGER.info("flag::" + flag+"msgCodeStr::425::"+msgCodeStr);
                }
            }
            if (flag) {
                responseMsg = prop.getProperty(WorkListDisplayConstants.REINITIATE_SERVICE_SUCCESS);
                responseMsg = responseMsg+"_"+msgCodeStr;
                LOGGER.info("responseMsg::callReInitiatePetService--231--" + responseMsg);

            }
            else {
                responseMsg = prop.getProperty(WorkListDisplayConstants.REINITIATE_SERVICE_FAILURE);
                responseMsg = responseMsg+"_"+msgCodeStr;
                LOGGER.info("responseMsg::callReInitiatePetService--232--" + responseMsg);

            }
        }

    }

    httpConnection.disconnect();
}
catch (MalformedURLException e) {
    LOGGER.info("inside malformedException callReInitiatePetService");
    throw new PEPFetchException();
}
catch (ClassCastException e) {
    e.printStackTrace();
    throw new PEPFetchException();
}
catch (IOException e) {
    LOGGER.info("inside IOException callReInitiatePetService");
    e.printStackTrace();
}
catch (JSONException e) {
    LOGGER.info("inside JSOnException callReInitiatePetService");
    e.printStackTrace();
}
catch (Exception e) {
    LOGGER.info("inside Exception callReInitiatePetService::" + e);
    e.printStackTrace();
}
LOGGER.info("Exiting callReInitiatePetService*****" +responseMsg);
return responseMsg;
}

    
///Service Call for Save Completion date
public String callSaveCompletionDateService(JSONArray jsonArray) throws Exception,PEPFetchException {
    LOGGER.info("WorkList display :::callSaveCompletionDateService");

    String responseMsg = "";
    boolean flag = false;
    String msgCodeStr = "";
    try {
        Properties prop =PropertiesFileLoader.getPropertyLoader(WorkListDisplayConstants.MESS_PROP);
        String targetURLs = prop.getProperty(WorkListDisplayConstants.COMPLETION_DATE_SERVICE_URL);
        //String targetURLs = WorkListDisplayConstants.COMPLETION_DATE_SERVICE_URL; // AFUPYB3
        URL targetUrl = new URL(targetURLs);
        HttpURLConnection httpConnection =(HttpURLConnection) targetUrl.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type","application/json");
        LOGGER.info("ImageRequestServiceImpl::Json Array" + jsonArray.toString());
        JSONObject jsonMap = new JSONObject();
        jsonMap.put(WorkListDisplayConstants.COMPLETION_DATE_SERVICE_LIST, jsonArray);
        String input = jsonMap.toString();
        //LOGGER.info("httpConnection.getInputStream().........." +httpConnection.getInputStream());
        LOGGER.info("final object in json callSaveCompletionDateService::" + jsonMap.toString());
        OutputStream outputStream = httpConnection.getOutputStream();
        outputStream.write(input.getBytes());
        outputStream.flush();
        BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
               (httpConnection.getInputStream())));

        String output;
        LOGGER.info("Output from Server:callSaveCompletionDateService::\n ");
        while ((output = responseBuffer.readLine()) != null) {
            LOGGER.info(output);
            // Below if block is for handling single row data.
            if ((jsonArray != null) && (jsonArray.length() <= 1)) {
                if ((output != null)
                    && (output.contains("not") && (output.indexOf("not") != -1))) {                   
                    responseMsg = WorkListDisplayConstants.COMPLETION_DATE_ERROR_STATUS;
                } else if((output != null)
                        && (output.contains("FAIL") && (output.indexOf("FAIL") != -1)))
                
                { 
                    responseMsg = WorkListDisplayConstants.COMPLETION_DATE_ERROR_STATUS; 
                    
                }else{
                    responseMsg = prop.getProperty(WorkListDisplayConstants.COMPLETION_DATE_SUCCESS_STATUS); 
                }

            } else {
                // This block is for handling multiple row data.
                JsonElement jelement = new JsonParser().parse(output);
                JsonObject jobject = jelement.getAsJsonObject();
                JsonArray jsonObject =(JsonArray) jobject.get(WorkListDisplayConstants.COMPLETION_DATE_SERVICE_LIST);

                for (int i = 0; i < jsonObject.size(); i++) {
                    LOGGER.info("ImageRequestServiceImpl::Id value size" + jsonObject.size() + "i value" + i);
                    if (jsonObject.size() == 1) {
                        JsonObject individualjson = jsonObject.getAsJsonObject();
                        Object msgCode =individualjson.get(WorkListDisplayConstants.MSG_CODE);

                        LOGGER.info("ImageRequestServiceImpl::MsgCode with one json" + msgCode.toString());
                        msgCodeStr = msgCode.toString();
                        msgCodeStr =msgCodeStr.substring(1, msgCodeStr.length() - 1);
                        LOGGER.info("aa" + msgCodeStr);
                    } else {
                        JsonObject individualjson = jsonObject.get(i).getAsJsonObject();
                        Object msgCode =individualjson.get(WorkListDisplayConstants.MSG_CODE);

                        LOGGER.info("ImageRequestServiceImpl::MsgCode callSaveCompletionDateService" + msgCode.toString());

                        msgCodeStr = msgCode.toString();
                        msgCodeStr =msgCodeStr.substring(1, msgCodeStr.length() - 1);
                        LOGGER.info("msgCodeStr" + msgCodeStr);
                    }

                    if (msgCodeStr.equalsIgnoreCase(WorkListDisplayConstants.SUCCESS_CODE)) {
                        flag = true;
                        LOGGER.info("ImageRequestServiceImpl:::callSaveCompletionDateService:::flag" + flag);
                    } else if (msgCodeStr.equalsIgnoreCase(WorkListDisplayConstants.FAILURE_CODE)) {
                        flag = false;
                    }
                }
                if (flag) {
                    responseMsg = prop.getProperty(WorkListDisplayConstants.COMPLETION_DATE_SUCCESS_STATUS);
                    LOGGER.info("ImageRequestServiceImpl:::callSaveCompletionDateService:::responseMsg" + responseMsg);

                } else {
                    responseMsg = WorkListDisplayConstants.COMPLETION_DATE_ERROR_STATUS;
                    LOGGER.info("ImageRequestServiceImpl:::callSaveCompletionDateService:::responseMsg"+ responseMsg);

                }
            }

        }

        httpConnection.disconnect();
    } catch (MalformedURLException e) {
        LOGGER.info("inside malformedException callSaveCompletionDateService");
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
        LOGGER.info("inside JSOnException callSaveCompletionDateService");
        

        e.printStackTrace();
        throw new PEPFetchException();
    } catch (Exception e) {
        LOGGER.info("inside Exception callSaveCompletionDateService" + e);

        e.printStackTrace();
        throw new Exception();

    }
    LOGGER.info("responseMsg  end " + responseMsg);
    return responseMsg;
}

public ArrayList isPETLocked(String pepUserId,String orin,String searchPetLockedtype) throws PEPPersistencyException{
    LOGGER.info("service impl :: isPETLocked");
    ArrayList petLockedDtls = null;
    try {
        petLockedDtls = workListDisplayDAO.isPETLocked(orin, pepUserId,searchPetLockedtype);
    }
    catch (PEPPersistencyException e) {
        
        LOGGER.info("Exception occurred at the Service DAO Layer");
        throw e;
    }
    return petLockedDtls;
    
}
public boolean lockPET(  String orin, String pepUserID, String pepfunction)throws PEPPersistencyException {
    LOGGER.info("service impl :: lockPET");
    try {
       workListDisplayDAO.lockPET(orin, pepUserID,pepfunction);
    }
    catch (PEPPersistencyException e) {
        
        LOGGER.info("Exception occurred at the Service DAO Layer");
        throw e;
    }
    return false;
    
}

/**
 * @param adSearch AdvanceSearch
 * @param supplierIdList List<String>
 * @param vendorEmail String
 * @param styleOrinList List<String>
 * @return List<WorkFlow>
 */
@Override
public List<WorkFlow> getAdvWorklistGroupingData(AdvanceSearch adSearch,
    List<String> supplierIdList, String vendorEmail, List<String> styleOrinList) {
    LOGGER.info("WorkListDisplayServiceImpl getAdvWorklistGroupingData Start");
    List<WorkFlow> workFlowList = null;
    workFlowList = workListDisplayDAO.getAdvWorklistGroupingData(adSearch, supplierIdList, vendorEmail, styleOrinList);
    LOGGER.info("WorkListDisplayServiceImpl getAdvWorklistGroupingData End");
    return workFlowList;
}

    /**
     * Method to get the groups for search pet.
     * 
     * @param groupId String
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Search Pet Date: 06/02/2016 Added
     *         By: Cognizant
     */
    @Override
    public List<WorkFlow> groupSearchParent(final String groupId) {
        LOGGER.info("WorkListDisplayServiceImpl groupSearchParent Start");
        List<WorkFlow> workFlowList = null;
        workFlowList = workListDisplayDAO.groupSearchParent(groupId);
        LOGGER.info("WorkListDisplayServiceImpl groupSearchParent End");
        return workFlowList;
    }
    
    /**
     * Method to get the child for group for search pet.
     * 
     * @param groupId String
     * @param advanceSearch AdvanceSearch
     * @param parengGroupId String
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Search Pet Date: 06/06/2016 Added
     *         By: Cognizant
     */
    @Override
    public List<WorkFlow> getChildForGroup(final String groupId, final AdvanceSearch advanceSearch,
    		final String parengGroupId) {
        LOGGER.info("WorkListDisplayServiceImpl getChildForGroup Start");
        List<WorkFlow> workFlowList = null;
        workFlowList = workListDisplayDAO.getChildForGroup(groupId, advanceSearch, parengGroupId);
        LOGGER.info("WorkListDisplayServiceImpl getChildForGroup End");
        return workFlowList;
    }
    
    
    /**
     * Method to get the groups for worklist display.
     * 
     * @param departmentNumbers ArrayList
     * @param pageNumber int
     * @param sortColumn String
     * @param sortOrder String
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Search Pet Date: 06/06/2016 Added
     *         By: Cognizant
     */
    @Override
    public List<WorkFlow> getGroupWorkListDetails(final List departmentNumbers, int startIndex, int maxResult, String sortColumn, String sortOrder) {
        LOGGER.info("WorkListDisplayServiceImpl getGroupWorkListDetails Start");
        List<WorkFlow> workFlowList = null;
        workFlowList = workListDisplayDAO.getWorklistGroupData(departmentNumbers, startIndex, maxResult, sortColumn, sortOrder);
        LOGGER.info("WorkListDisplayServiceImpl getGroupWorkListDetails End");
        return workFlowList;
    }
    
    /**
     * Method to get the groups for worklist display count.
     * 
     * @param departmentNumbers ArrayList
     * @return int
     * 
     *         Method added For PIM Phase 2 - Search Pet Date: 06/06/2016 Added
     *         By: Cognizant
     */
    @Override
    public int getGroupWorkListCountDetails(final List departmentNumbers) {
        LOGGER.info("WorkListDisplayServiceImpl getGroupWorkListCountDetails Start");
        int totalRecordsCount = 0;
        totalRecordsCount = workListDisplayDAO.groupWorklistSearchCount(departmentNumbers);        
        LOGGER.info("WorkListDisplayServiceImpl getGroupWorkListCountDetails End");
        return totalRecordsCount;
    }
    
    /**
     * Method to get the child for group for Worklist.
     * 
     * @param groupId String
     * @param parentGroupId String
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Worklist Group Date: 06/09/2016 Added
     *         By: Cognizant
     */
    @Override
    public List<WorkFlow> getChildForGroupWorklist(final String groupId, final String parentGroupId){
        LOGGER.info("WorkListDisplayServiceImpl getChildForGroupWorklist Start");
        List<WorkFlow> workFlowList = null;
        workFlowList = workListDisplayDAO.getChildForGroupWorklist(groupId, parentGroupId);
        LOGGER.info("WorkListDisplayServiceImpl getChildForGroupWorklist End");
        return workFlowList;
    }
    

    /**
     * @param jsonArray JSONArray
     * @return String
     * @throws PEPFetchException 
     */
    @Override
    public String callInactivateGroupService(JSONArray jsonArray) throws PEPFetchException {
        LOGGER
            .info("WorkListDisplayserviceImpl callInactivateGroupService : Starts");
        LOGGER.info("jsoArray:---- " + jsonArray);

        String responseMsg = WorkListDisplayConstants.EMPTY_STRING;
        boolean flag = false;
        String msgCodeStr = WorkListDisplayConstants.EMPTY_STRING;
        HttpURLConnection httpConnection = null;

        try {
            Properties prop =
                PropertyLoader
                    .getPropertyLoader(WorkListDisplayConstants.MESS_PROP);
            String serviceURL =
                prop.getProperty(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_GROUPS_SERVICE_URL);
            LOGGER.info("Service URL:--- " + serviceURL);

            System.out.println("ServiceURL:---- " + serviceURL);
            for(int count = 0; count<jsonArray.length(); count++)
            {
                URL targetURL = new URL(serviceURL);
                 httpConnection =
                    (HttpURLConnection) targetURL.openConnection();
                httpConnection.setDoOutput(true);
                httpConnection
                    .setRequestMethod(WorkListDisplayConstants.METHOD_POST);
                httpConnection.setRequestProperty(
                    WorkListDisplayConstants.CONTENT_TYPE,
                    WorkListDisplayConstants.APPLICATION_JSON);
    
                LOGGER.info("callInactivategroupService jsonArray:-- "
                    + jsonArray.toString());
    
                JSONObject jsonMap = new JSONObject();
            
                String input = jsonArray.get(count).toString();
    
                LOGGER.info("final obejct in JSON:-- " + input);
    
                OutputStream outputStream = httpConnection.getOutputStream();
                outputStream.write(input.getBytes());
                outputStream.flush();
                BufferedReader responseReader =
                    new BufferedReader(new InputStreamReader(
                        httpConnection.getInputStream()));
                String output;
    
                LOGGER.info("response reader line::::::: "
                    + responseReader.readLine());
                while ((output = responseReader.readLine()) != null) {
                    LOGGER.info(output);
                    LOGGER.info("output for deactivating group:::::: " + output);
                    // Handling Single Row Data
                    /*if (jsonArray != null && jsonArray.length() <= 1) {
                        LOGGER.info("Single Row");*/
    
                        if (output != null
                            && (output.toLowerCase().contains(WorkListDisplayConstants.NOT) || output
                                    .toLowerCase().contains(WorkListDisplayConstants.MAY_BE))
                            && (output.toLowerCase().indexOf(WorkListDisplayConstants.NOT) != -1 || output
                                    .toLowerCase().indexOf(WorkListDisplayConstants.MAY_BE) != -1)) {
                            responseMsg =
                                WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_FAILURE;
                            LOGGER.info("responseMsg::failure::::: "
                                + responseMsg);
                        }
                        else {
                            responseMsg =
                                WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_SUCCESS;
                            LOGGER.info("responseMsg:::Success::::  "
                                + responseMsg);
                        }
                   /* }
                    else {
                        // Multiple Row Data
    
                        JsonElement jElement = new JsonParser().parse(output);
                        JsonObject jObject = jElement.getAsJsonObject();
                        JsonArray jsonObject =
                            (JsonArray) jObject
                                .get(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_LIST);
    
                        for (int i = 0; i < jsonObject.size(); i++) {
                            if (jsonObject.size() == 1) {
                                LOGGER.info("Multiple One");
                                JsonObject individualJson =
                                    jsonObject.getAsJsonObject();
                                Object msgCode =
                                    individualJson
                                        .get(WorkListDisplayConstants.MSG_CODE);
                                LOGGER.info("Messgafe code-->  "
                                    + msgCode.toString());
                                msgCodeStr = msgCode.toString();
                                msgCodeStr =
                                    msgCodeStr
                                        .substring(1, msgCodeStr.length() - 1);
                            }
                            else {
                                LOGGER.info("Multiple Many");
                                JsonObject individualJson =
                                    jsonObject.get(i).getAsJsonObject();
                                Object msgCode =
                                    individualJson
                                        .get(WorkListDisplayConstants.MSG_CODE);
                                LOGGER.info("Message code--> "
                                    + msgCode.toString());
                                msgCodeStr = msgCode.toString();
                                msgCodeStr =
                                    msgCodeStr
                                        .substring(1, msgCodeStr.length() - 1);
                            }
    
                            if (msgCodeStr
                                .equalsIgnoreCase(WorkListDisplayConstants.SUCCESS_CODE)) {
                                flag = true;
                                LOGGER.info("flag:: " + flag
                                    + "msgCodeStr--> " + msgCodeStr);
                            }
                            else if (msgCodeStr
                                .equalsIgnoreCase(WorkListDisplayConstants.FAILURE_CODE)) {
                                flag = false;
                                LOGGER.info("flag:: " + flag
                                    + " msgCodeStr--> " + msgCodeStr);
                            }
                        }
                        if (flag) {
                            responseMsg =
                                prop.getProperty(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_SUCCESS);
                            responseMsg = responseMsg + "_" + msgCodeStr;
                            LOGGER.info("response msg--> " + responseMsg);
                        }
                        else {
                            responseMsg =
                                prop.getProperty(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_FAILURE);
                            responseMsg = responseMsg + "_" + msgCodeStr;
                            LOGGER.info("responseMsg --> " + responseMsg);
                        }
                    }*/
    
                }
            }

            

        }
        catch (MalformedURLException e) {
            LOGGER.error("inside malformedException" + e);
            throw new PEPFetchException(e);
        }
        catch (ClassCastException e) {
            LOGGER.error("inside ClassCastException" + e);
            throw new PEPFetchException();
        }
        catch (IOException e) {
            LOGGER.error("inside IOException" + e);
            throw new PEPFetchException(e);
        }
        catch (JSONException e) {
            LOGGER.error("inside JSOnException" + e);
            throw new PEPFetchException(e);
        }
        catch (Exception e) {
            LOGGER.info("inside Exception" + e);
            throw new PEPFetchException(e);
        }finally{
            if (httpConnection!=null)
                httpConnection.disconnect();
        }
        LOGGER
            .info("WorkListDisplayserviceImpl callInactivateGroupService : Ends");
        return responseMsg;
    }

    /**
     * This method will reinitiate group selected
     */
    @Override
    public String callReInitiateGroupService(JSONArray jsonArray)
        {

        LOGGER
            .info("WorkListDisplayServiceImpl callReInitiateGroupService : Starts");
        System.out.println("in service impl for activate group");
        String responseMsg = WorkListDisplayConstants.EMPTY_STRING;
        boolean flag = false;
        String msgCodeStr = WorkListDisplayConstants.EMPTY_STRING;
        String singleResponseCode = WorkListDisplayConstants.EMPTY_STRING;

        try {
            Properties prop =
                PropertyLoader
                    .getPropertyLoader(WorkListDisplayConstants.MESS_PROP);
            String serviceUrl =
                prop.getProperty(WorkListDisplayConstants.ACTIVATE_GROUPS_SERVICE_URL);
            ;

            LOGGER.info("Reinitiate Service URL:----- " + serviceUrl);
            for(int count = 0; count<jsonArray.length(); count++)
            {
                URL targetUrl = new URL(serviceUrl);
                HttpURLConnection httpConnection =
                    (HttpURLConnection) targetUrl.openConnection();
                httpConnection.setDoOutput(true);
                httpConnection.setRequestMethod(WorkListDisplayConstants.METHOD_POST);
                httpConnection.setRequestProperty(WorkListDisplayConstants.CONTENT_TYPE,
                    WorkListDisplayConstants.APPLICATION_JSON);
    
                LOGGER.info("Reinitiate group Service ... JSONArray...... "
                    + jsonArray.toString());

            
                String input = jsonArray.get(count).toString();
                LOGGER.info("Final Object:-------- " + input);
    
                OutputStream outputStream = httpConnection.getOutputStream();
                outputStream.write(input.getBytes());
                outputStream.flush();
    
                BufferedReader responseBuffer =
                    new BufferedReader(new InputStreamReader(
                        httpConnection.getInputStream()));
                String output;
    
                while ((output = responseBuffer.readLine()) != null) {
                    LOGGER.info(output);
    
                   /* if (jsonArray != null && jsonArray.length() <= 1) {
                        LOGGER.info("Single Request");*/
    
                        if ((output != null)
                            && (output.toLowerCase().contains(WorkListDisplayConstants.NOT)
                                || output.toLowerCase().contains(WorkListDisplayConstants.MAY_BE) || output
                                .toLowerCase().contains(WorkListDisplayConstants.FAILED))
                            && (output.toLowerCase().indexOf(WorkListDisplayConstants.NOT) != -1
                                || output.toLowerCase().indexOf(WorkListDisplayConstants.MAY_BE) != -1 || output
                                .toLowerCase().indexOf(WorkListDisplayConstants.FAILED) != -1)) {
                            singleResponseCode =
                                WorkListDisplayConstants.RESPONSE_101;
                            responseMsg =
                                prop.getProperty(WorkListDisplayConstants.REINITIATE_SERVICE_FAILURE)
                                    + WorkListDisplayConstants.UNDERSCORE
                                    + singleResponseCode;
                            LOGGER
                                .info("responseMsg -- failure:;:: " + responseMsg);
                        }
                        else {
                            singleResponseCode =
                                WorkListDisplayConstants.RESPONSE_100;
                            responseMsg =
                                prop.getProperty(WorkListDisplayConstants.REINITIATE_SERVICE_SUCCESS)
                                    + WorkListDisplayConstants.UNDERSCORE
                                    + singleResponseCode;
                            LOGGER.info("responseMsg -- success::::: "
                                + responseMsg);
                        }
                    /*}
                    else {
                        LOGGER.info("Multiple Requests");
                        JsonElement jElement = new JsonParser().parse(output);
                        JsonObject jObject = jElement.getAsJsonObject();
                        JsonArray jsonObject =
                            (JsonArray) jObject
                                .get(WorkListDisplayConstants.ACTIVATE_OR_INACTIVATE_SERVICE_LIST);
    
                        for (int i = 0; i < jsonObject.size(); i++) {
                            if (jsonObject.size() == 1) {
                                LOGGER.info("Multiple One");
                                JsonObject individualJson =
                                    jsonObject.getAsJsonObject();
                                Object msgCode =
                                    individualJson
                                        .get(WorkListDisplayConstants.MSG_CODE);
                                msgCodeStr =
                                    msgCode.toString().substring(1,
                                        msgCode.toString().length() - 1);
                                LOGGER
                                    .info("Msg Code String:------- " + msgCodeStr);
                            }
                            else {
                                LOGGER.info("Multiple Many");
                                JsonObject individualJson =
                                    jsonObject.get(i).getAsJsonObject();
                                Object msgCode =
                                    individualJson
                                        .get(WorkListDisplayConstants.MSG_CODE);
                                msgCodeStr =
                                    msgCode.toString().substring(1,
                                        msgCode.toString().length() - 1);
                                LOGGER
                                    .info("Msg code String:------- " + msgCodeStr);
                            }
    
                            if (msgCodeStr
                                .equals(WorkListDisplayConstants.SUCCESS_CODE)) {
                                flag = true;
                                if (LOGGER.isDebugEnabled()) {
                                    LOGGER.debug("Flag:--- " + flag
                                        + " msg code string:----- " + msgCodeStr);
                                }
                            }
                            else if (msgCodeStr
                                .equals(WorkListDisplayConstants.FAILURE_CODE)) {
                                flag = false;
                                if (LOGGER.isDebugEnabled()) {
                                    LOGGER.debug("flag:---- " + flag
                                        + " message code string:----- "
                                        + msgCodeStr);
                                }
                            }
                        }
                        if (flag) {
                            responseMsg =
                                prop.getProperty(WorkListDisplayConstants.REINITIATE_SERVICE_SUCCESS)
                                    + WorkListDisplayConstants.UNDERSCORE
                                    + msgCodeStr;
                        }
                        else {
                            responseMsg =
                                prop.getProperty(WorkListDisplayConstants.REINITIATE_SERVICE_FAILURE)
                                    + WorkListDisplayConstants.UNDERSCORE
                                    + msgCodeStr;
                        }
                    }*/
                }
            
                httpConnection.disconnect();
            }
        }
        catch (MalformedURLException e) {
            LOGGER.error("Inside malformedException callReInitiateGroupService"
                + e);
        }
        catch (ClassCastException e) {
            LOGGER.error("Inside classcastExceotion callReInitiategroupService"
                +e );
        }
        catch (IOException e) {
            LOGGER.error("Inside IOException callreInitiateGroupService"
                + e);
        }
        catch (JSONException e) {
            LOGGER.error("Inside JSONException callReInitiareGroupService"
                + e);
        }
        catch (Exception e) {
            LOGGER.error("Inside Exception callReInitiateGroupService"
                + e);
        }

        LOGGER
            .info("WorkListDisplayServiceImpl callReInitiateGroupService : Ends");
        return responseMsg;
    }  

    
}
