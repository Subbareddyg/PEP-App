package com.belk.pep.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.belk.pep.constants.GroupingConstants;
import com.belk.pep.dao.GroupingDAO;
import com.belk.pep.dto.CreateGroupDTO;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.Component;
import com.belk.pep.form.CreateGroupForm;
import com.belk.pep.form.GroupAttributeForm;
import com.belk.pep.service.GroupingService;
import com.belk.pep.util.PropertyLoader;

/**
 * The Class GroupingServiceImpl.
 */
public class GroupingServiceImpl implements GroupingService {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(GroupingServiceImpl.class.getName()); 


    /** The GroupingDAO. */
    private GroupingDAO groupingDAO;




    /**
     * Gets the Grouping DAO.
     * @return the Grouping DAO
     */
    public GroupingDAO getGroupingDAO() {
        return groupingDAO;
    }

    /**
     * Sets the Grouping DAO.
     * @param GroupingDAO the new Grouping DAO
     */
    public void setGroupingDAO(GroupingDAO groupingDAO) {
        this.groupingDAO = groupingDAO;
    }


    /**
     * This method is used to call Group Creation Service and fetch data from database
     * @param jsonArray
     * @param groupId
     * @returnCreateGroupForm
     * @throws Exception
     * @throws PEPFetchException
     */
    public CreateGroupForm saveGroupHeaderDetails(JSONObject jsonStyle, String updatedBy, List<GroupAttributeForm> selectedSplitAttributeList) 
            throws Exception, PEPFetchException {
        LOGGER.info("Entering saveGroupHeaderDetails-->");
        //CreateGroupDTO createGroupD = groupingDAO.getGroupHeaderDetails("1005002"); // TODO
        //LOGGER.debug("------>"+createGroupD.getGroupName());
        
        String groupIdRes = "";
        Component component = null;
        List<Component> componentList = null;
        String responseMsg = "";
        String[] responseMsgArray = null;
        String responseMsgCode = "";
        String groupCreationStatus = "";
        
        /** Calling Web Service to create Group except Split type**/
        LOGGER.debug("Create Group Service Start currentTimeMillis-->"+System.currentTimeMillis());
        String resMsg = callCreateGroupService(jsonStyle);
        LOGGER.debug("Create Group Service End currentTimeMillis-->"+System.currentTimeMillis());
        LOGGER.debug("Create Group Service message-->"+resMsg);
        
        resMsg = (null == resMsg ? "" : resMsg.replaceAll("\"", ""));
        resMsg = resMsg.substring(1, resMsg.length() - 1);
        LOGGER.debug("Create Group Service message 1-->"+resMsg);
        Properties prop =PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
        LOGGER.debug("------------------------>Start");
        /** Extract Service message **/
        responseMsgArray = resMsg.split(",");
        LOGGER.debug("responseMsgArray-->"+responseMsgArray);
        for(int i = 0; i < responseMsgArray.length; i++){
            if(responseMsgArray[i].split(":").length == 2){
                String key = responseMsgArray[i].split(":")[0];
                String value = responseMsgArray[i].split(":")[1];
                if(("code").equalsIgnoreCase(key)){
                    responseMsgCode = value;
                }
                if(("id").equalsIgnoreCase(key)){
                    groupIdRes = value;
                }
            }
        }
        
        //responseMsgCode = responseMsgArray[0].split(":")[1];
        LOGGER.debug("responseMsgCode-->"+responseMsgCode);
        /*if(null != responseMsgCode && (GroupingConstants.SUCCESS_CODE).equals(responseMsgCode)){
            groupIdRes = responseMsgArray[3].split(":")[1];
        }*/
        LOGGER.debug("groupIdRes-->"+groupIdRes);
        if(null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)){
        	responseMsg = prop.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_SUCCESS);
        	groupCreationStatus = GroupingConstants.GROUP_CREATED;
            LOGGER.info("responseMsg100::Success-->" + responseMsg);
        }else{
            responseMsg = prop.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_FAILURE);
            groupCreationStatus = GroupingConstants.GROUP_NOT_CREATED;
            LOGGER.info("responseMsg101::Failure-->" + responseMsg);
        }
        /** End of Group Header Creation **/
        
        //JSONObject jsonStyle = (JSONObject) jsonArray.get(0);
        String groupType = jsonStyle.getString(GroupingConstants.GROUP_TYPE);
        LOGGER.debug("groupType from jsonStyle Response after creating Group Header-->"+groupType);
        LOGGER.debug("responseMsgCode from jsonStyle Response after creating Group Header-->"+responseMsgCode);
        
        /** Call add component service to add Split Color attribute details List **/
        if(null != groupType && groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_COLOR) 
                && null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)){
            LOGGER.debug("Calling Add component for Split Color service Start");
            // Create Split Group

            /*JSONArray jsonArraySplitColor = new JSONArray();
            componentList = new ArrayList<Component>();
            for(int i = 0; i<selectedSplitAttributeList.size(); i++){
                component = new Component();
                GroupAttributeForm selectedGroupAttributeForm = selectedSplitAttributeList.get(i);
                component.setId(selectedGroupAttributeForm.getOrinNumber());
                component.setDefaultAttr(selectedGroupAttributeForm.getIsDefault());
                componentList.add(component);
            }*/
            JSONObject jsonStyleSpliColor = populateAddComponentSCGJson(groupIdRes, groupType, updatedBy, selectedSplitAttributeList/*componentList*/);
            //jsonArraySplitColor.put(jsonStyleSpliColor);
            LOGGER.debug("json Object Add Component to Split Color groupId--> "+ jsonStyleSpliColor.getString("groupId"));
            LOGGER.debug("Create Split Color Group Service Start currentTimeMillis-->"+System.currentTimeMillis());
            String resMsgSplitColor = callAddComponentSCGService(jsonStyleSpliColor);
            LOGGER.debug("Create Split Color Group Service End currentTimeMillis-->"+System.currentTimeMillis());
            LOGGER.debug("Add Component to Split Color Group Service message-->"+resMsgSplitColor);
            
            /** Extract Service message **/
            responseMsgArray = resMsgSplitColor.split(",");
            LOGGER.debug("responseMsgArray Split Color -->"+responseMsgArray);
            for(int i = 0; i < responseMsgArray.length; i++){
                if(responseMsgArray[i].split(":").length == 2){
                    String key = responseMsgArray[i].split(":")[0];
                    String value = responseMsgArray[i].split(":")[1];
                    if(("code").equalsIgnoreCase(key)){
                        responseMsgCode = value;
                    }
                }
            }
            if(null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)){
                responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_SCG_SERVICE_SUCCESS);
                groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_COMPONENT_SCG;
                LOGGER.info("Add Component to Split Color Group. ResponseMsg100::Success-->" + responseMsg);
            }else{
                responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_SCG_SERVICE_FAILURE);
                groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_OUT_COMPONENT_SCG;
                LOGGER.info("Add Component to Split Color Group. ResponseMsg101::Failure-->" + responseMsg);
            }
            LOGGER.debug("Calling Add component for Split Color service End");
        }
        
        /** Call add component service to add split SKU attribute details List **/
        if(null != groupType && groupType.equals(GroupingConstants.GROUP_TYPE_SPLIT_SKU) 
                && null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)){
            LOGGER.debug("Calling Add component for Split SKU service Start");
            // Create Split Group

            /*JSONArray jsonArraySplitSku = new JSONArray();
            componentList = new ArrayList<Component>();
            for(int i = 0; i<selectedSplitAttributeList.size(); i++){
                component = new Component();
                GroupAttributeForm selectedGroupAttributeForm = selectedSplitAttributeList.get(i);
                component.setId(selectedGroupAttributeForm.getOrinNumber());
                component.setDefaultAttr(selectedGroupAttributeForm.getIsDefault());
                component.setColor(selectedGroupAttributeForm.getColorCode());
                component.setSize(selectedGroupAttributeForm.getSize());
                componentList.add(component);
            }*/
            JSONObject jsonStyleSpliSku = populateAddComponentSSGJson(groupIdRes, groupType, updatedBy, selectedSplitAttributeList/*componentList*/);
            //jsonArraySplitSku.put(jsonStyleSpliSku);
            LOGGER.debug("json Object Add Component to Split SKU groupId--> "+ jsonStyleSpliSku.getString("groupId"));
            LOGGER.debug("Create Split SKU Group Service Start currentTimeMillis-->"+System.currentTimeMillis());
            String resMsgSplitColor = callAddComponentSSGService(jsonStyleSpliSku);
            LOGGER.debug("Create Split SKU Group Service End currentTimeMillis-->"+System.currentTimeMillis());
            LOGGER.debug("Add Component to Split SKU Group Service message-->"+resMsgSplitColor);

            /** Extract Service message **/
            responseMsgArray = resMsgSplitColor.split(",");
            LOGGER.debug("responseMsgArray Split SKU -->"+responseMsgArray);
            for(int i = 0; i < responseMsgArray.length; i++){
                if(responseMsgArray[i].split(":").length == 2){
                    String key = responseMsgArray[i].split(":")[0];
                    String value = responseMsgArray[i].split(":")[1];
                    if(("code").equalsIgnoreCase(key)){
                        responseMsgCode = value;
                    }
                }
            }
            if(null != responseMsgCode && responseMsgCode.equals(GroupingConstants.SUCCESS_CODE)){
                responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_SSG_SERVICE_SUCCESS);
                groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_COMPONENT_SSG;
                LOGGER.info("Add Component to Split SKU Group. ResponseMsg100::Success-->" + responseMsg);
            }else{
                responseMsg = prop.getProperty(GroupingConstants.ADD_COMPONENT_SSG_SERVICE_FAILURE);
                groupCreationStatus = GroupingConstants.GROUP_CREATED_WITH_OUT_COMPONENT_SSG;
                LOGGER.info("Add Component to Split SKU Group. ResponseMsg101::Failure-->" + responseMsg);
            }
            LOGGER.debug("Calling Add component for Split SKU service End");
        }
        /** End Group Creation for All Type **/
        
        // Call DAO to fetch Group Details after getting response from service
        CreateGroupForm createGroupForm = new CreateGroupForm();
        CreateGroupDTO createGroupDTO = new CreateGroupDTO();
        if(null != responseMsgCode && (GroupingConstants.SUCCESS_CODE).equals(responseMsgCode)){
            LOGGER.debug("Before Calling database method getGroupHeaderDetails() to retreive Group Header Details-->");
            createGroupDTO = groupingDAO.getGroupHeaderDetails(groupIdRes);
            LOGGER.debug("After Calling database method getGroupHeaderDetails() to retreive Group Header Details-->"+createGroupDTO.getGroupId());
        }
        createGroupDTO.setGroupCretionMsg(responseMsg);
        
        /** Transfer object value from DTO to Form Object Start**/
        LOGGER.debug("Transfer object value from DTO to Form Object Start");
        createGroupForm.setGroupId(createGroupDTO.getGroupId());
        createGroupForm.setGroupName(createGroupDTO.getGroupName());
        createGroupForm.setGroupType(createGroupDTO.getGroupType());
        createGroupForm.setGroupDesc(createGroupDTO.getGroupDesc());
        createGroupForm.setGroupLaunchDate(createGroupDTO.getGroupLaunchDate());
        createGroupForm.setEndDate(createGroupDTO.getEndDate());
        createGroupForm.setGroupCretionMsg(createGroupDTO.getGroupCretionMsg());
        createGroupForm.setGroupAttributeFormList(createGroupDTO.getGroupAttributeDTOList());
        createGroupForm.setGroupCreationStatus(groupCreationStatus);
        
        LOGGER.debug("Transfer object value from DTO to Form Object End");
        /** **/
        
        LOGGER.info("Exist saveGroupHeaderDetails-->");
        return createGroupForm;
    }
    

    /**
     * This method is used to call Group Creation Service 
     * @param jsonArray
     * @return
     * @throws Exception
     * @throws PEPFetchException
     */
    public String callCreateGroupService(JSONObject jsonStyle) throws Exception, PEPFetchException {
        LOGGER.info("Entering callCreateGroupService-->");
        LOGGER.debug("jsonArray-->" + jsonStyle);
        String responseMsg = "";
        try {
            Properties prop =PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
            String serviceURL = prop.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_URL);
            LOGGER.info("Create Group ServiceURL-->" + serviceURL);

            URL targetUrl = new URL(serviceURL);
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type","application/json");

            LOGGER.info("callCreateGroupService Service::Json Array-->"+ jsonStyle.toString());

            /*JSONObject jsonMap = new JSONObject();
            jsonMap.put(GroupingConstants.CREATE_GROUP_SERVICE_LIST,jsonArray);
            String input = jsonMap.toString();*/
            
            String input = jsonStyle.toString();
            LOGGER.debug("final object in json-->" +"\t"+ jsonStyle.toString());
            LOGGER.info("input....json-->"+ input);
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
            String output;
            LOGGER.info("Output from Server:::"+"\t"+"after Calling-->");
            while ((output = responseBuffer.readLine()) != null) {
                LOGGER.info("CreateGroupService Service Output-->"+output);
                
                /* Response Message format-
                Pass:- {"code":"100","description":"Group created ","status":"SUCCESS",”groupId”:”XXXXXXX”}
                Fail:- {"code":"101","description":"Group is not created ","status":"FAIL"}*/
                
                // Below if block is for handling single row data.
                //if ((jsonArray != null) && (jsonArray.length() <= 1)) {
                    LOGGER.info("Single Request-->");
                    responseMsg = output;

                    /*if ((output != null)  && output.contains("not") && output.indexOf("not") != -1) {
                        responseMsg = prop.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_FAILURE);
                        LOGGER.info("responseMsg101::Failure-->" + responseMsg);
                    }
                    else {
                        responseMsg = prop.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_SUCCESS);
                        LOGGER.info("responseMsg100::Success-->" + responseMsg);
                    }*/
                //}
                
                
                
                /*else {
                    LOGGER.info("Multiple Request-->");
                    // This block is for handling multiple row data.
                    JsonElement jelement = new JsonParser().parse(output);
                    JsonObject jobject = jelement.getAsJsonObject();
                    JsonArray jsonObject =(JsonArray) jobject.get(GroupingConstants.CREATE_GROUP_SERVICE_LIST);

                    for (int i = 0; i < jsonObject.size(); i++) {                        
                        LOGGER.info("inactivateRequestServiceImpl::Id value size::399-->"+ jsonObject.size() +"::"+"i-->"+i);                        
                        if (jsonObject.size() == 1) {
                            LOGGER.info("Multiple One");
                            JsonObject individualjson = jsonObject.getAsJsonObject();
                            Object msgCode = individualjson.get(GroupingConstants.MSG_CODE);
                            LOGGER.debug("MsgCode::403-->" + msgCode.toString());
                            msgCodeStr = msgCode.toString();
                            msgCodeStr = msgCodeStr.substring(1, msgCodeStr.length() - 1);
                            LOGGER.info("msgCodeStr::One::407-->" + msgCodeStr);
                        }
                        else {
                            LOGGER.info("Multiple Many-->");
                            JsonObject individualjson = jsonObject.get(i).getAsJsonObject();
                            Object msgCode = individualjson.get(GroupingConstants.MSG_CODE);
                            LOGGER.debug("MsgCode::414-->" + msgCode.toString());

                            msgCodeStr = msgCode.toString();
                            msgCodeStr = msgCodeStr.substring(1, msgCodeStr.length() - 1);
                            LOGGER.debug("msgCodeStr::417-->" + msgCodeStr);
                        }

                        if (msgCodeStr.equalsIgnoreCase(GroupingConstants.SUCCESS_CODE)) {
                            flag = true;
                            LOGGER.debug("flag::" + flag+"msgCodeStr::421-->"+msgCodeStr);
                        }
                        else if (msgCodeStr.equalsIgnoreCase(GroupingConstants.FAILURE_CODE)) {
                            flag = false;
                            LOGGER.debug("flag::" + flag+"msgCodeStr::425-->"+msgCodeStr);
                        }
                    }
                    if (flag) {
                        responseMsg = prop.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_SUCCESS);
                        responseMsg = responseMsg+"_"+msgCodeStr;
                        LOGGER.info("responseMsg::431-->" + responseMsg);

                    }
                    else {
                        responseMsg = prop.getProperty(GroupingConstants.CREATE_GROUP_SERVICE_FAILURE);
                        responseMsg = responseMsg+"_"+msgCodeStr;
                        LOGGER.info("responseMsg::436-->" + responseMsg);

                    }
                }*/

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
        LOGGER.info("Exiting callCreateGroupService-->" +responseMsg);
        return responseMsg;
    }
    

    /**
     * This method is used to get the Style Color details to create Split Color group.
     * @param vendorStyleNo
     * @param styleOrin
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public List<GroupAttributeForm> getSplitColorDetails(String vendorStyleNo, String styleOrin) 
            throws PEPServiceException, PEPPersistencyException{
        //getting the Style Color  details from Database
        LOGGER.info("Enter-->calling getSplitColorDetails from GroupingServiceImpl.");
        List<GroupAttributeForm> getSplitColorDetailsList = null;
        try {
            getSplitColorDetailsList = groupingDAO.getSplitColorDetails(vendorStyleNo, styleOrin);
        }
        catch (Exception e) {
            LOGGER.info("Exception occurred at the Service Implementation Layer");
            throw new PEPServiceException(e.getMessage());
        }
        LOGGER.info("Exit-->calling getSplitColorDetails from GroupingServiceImpl.");
        return getSplitColorDetailsList;
    }
    

    /**
     * This method is used to get the SKU details to create Split Color group.
     * @param vendorStyleNo
     * @param styleOrin
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public List<GroupAttributeForm> getSplitSKUDetails(String vendorStyleNo, String styleOrin) 
            throws PEPServiceException, PEPPersistencyException{
        //getting the Style Color  details from Database
        LOGGER.info("*Enter-->calling getSplitSKUDetails from GroupingServiceImpl.");
        List<GroupAttributeForm> getSplitColorDetailsList = null;
        try {
            getSplitColorDetailsList = groupingDAO.getSplitSKUDetails(vendorStyleNo, styleOrin);
        }
        catch (Exception e) {
            LOGGER.info("Exception occurred at the Service Implementation Layer");
            throw new PEPServiceException(e.getMessage());
        }
        LOGGER.info("Exit-->calling getSplitSKUDetails from GroupingServiceImpl.");
        return getSplitColorDetailsList;
    }
    

    /**
     * Method to pass JSON Array to call the Add Component service for Split Color Group
     * @param petId
     * @param petStatus
     * @return
     * @author Cognizant
     */
    public JSONObject populateAddComponentSCGJson(String groupIdRes, String groupType, String updatedBy, 
        List<GroupAttributeForm> selectedSplitAttributeList/*List<Component> componentList*/) {
        /*JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put(GroupingConstants.GROUP_ID, groupIdRes);
            jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
            jsonObj.put(GroupingConstants.COMPONENT_LIST, componentList);
            jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy);
        } catch (JSONException e) {
            LOGGER.info("Exeception in parsing the jsonObj");
            e.printStackTrace();
        }
        return jsonObj;*/
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObjComponent = new JSONObject();
        GroupAttributeForm groupAttributeForm = null;
        JSONArray jsonArray = new JSONArray();
        try {       
            //selectedSplitAttributeList = createGroupForm.getGroupAttributeFormList();
            for(int i = 0; i < selectedSplitAttributeList.size(); i++){
                groupAttributeForm = selectedSplitAttributeList.get(i);
                jsonObjComponent.put(GroupingConstants.COMPONENT_ID, groupAttributeForm.getOrinNumber());
                jsonObjComponent.put(GroupingConstants.COMPONENT_IS_DEFAULT, groupAttributeForm.getIsDefault());
                jsonArray.put(jsonObjComponent);
            }
            
            jsonObj.put(GroupingConstants.GROUP_ID, groupIdRes);
            jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
            jsonObj.put(GroupingConstants.COMPONENT_LIST, jsonArray);
            jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy);
        } catch (JSONException e) {
            LOGGER.info("Exeception in parsing the jsonObj");
            e.printStackTrace();
        }
        return jsonObj;
     }
     

     /**
      * This method is used to call Add Component to Split Color Service 
      * @param jsonArray
      * @return
      * @throws Exception
      * @throws PEPFetchException
      */
     public String callAddComponentSCGService(JSONObject jsonStyleSpliColor/*JSONArray jsonArray*/) throws Exception, PEPFetchException {
         LOGGER.info("Entering callAddComponentSCGService-->");
         LOGGER.debug("jsonStyleSpliColor-->" + jsonStyleSpliColor);
         String responseMsg = "";
         try {
             Properties prop =PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
             String serviceURL = prop.getProperty(GroupingConstants.ADD_COMPONENT_TO_SCG_SERVICE_URL);
             LOGGER.info("Add Component to Split Color ServiceURL-->" + serviceURL);

             URL targetUrl = new URL(serviceURL);
             HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
             httpConnection.setDoOutput(true);
             httpConnection.setRequestMethod("POST");
             httpConnection.setRequestProperty("Content-Type","application/json");

             LOGGER.info("callAddComponentSCGService Service::Json Array-->"+ jsonStyleSpliColor.toString());

             /*JSONObject jsonMap = new JSONObject();
             jsonMap.put(GroupingConstants.CREATE_GROUP_SERVICE_LIST,jsonArray);
             String input = jsonMap.toString();*/
             
             String input = jsonStyleSpliColor.toString();
             LOGGER.debug("final object in json-->" +"\t"+ jsonStyleSpliColor.toString());
             LOGGER.info("input....json-->"+ input);
             OutputStream outputStream = httpConnection.getOutputStream();
             outputStream.write(input.getBytes());
             outputStream.flush();

             BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
             String output;
             LOGGER.info("Output from Server:::"+"\t"+"after Calling-->");
             while ((output = responseBuffer.readLine()) != null) {
                 LOGGER.info("call callAddComponentSCGService Service Output-->"+output);
                 
                 /* Response Message format-
                 Pass:- {"code":"100","description":"Group created ","status":"SUCCESS",”groupId”:”XXXXXXX”}
                 Fail:- {"code":"101","description":"Group is not created ","status":"FAIL",”groupId”:””}*/
                 
                 // Below if block is for handling single row data.
                 //if ((jsonArray != null) && (jsonArray.length() <= 1)) {
                 LOGGER.info("Single Request-->");
                 responseMsg = output;
                 //}
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
         LOGGER.info("Exiting callAddComponentSCGService-->" +responseMsg);
         return responseMsg;
     }
     

     /**
      * Method to pass JSON Array to call the Add Component service for Split SKU Group
      * @param petId
      * @param petStatus
      * @return
      * @author Cognizant
      */
     public JSONObject populateAddComponentSSGJson(String groupIdRes, String groupType, String updatedBy, 
         List<GroupAttributeForm> selectedSplitAttributeList/*List<Component> componentList*//*List<Component> componentList*/) {
         /*JSONObject jsonObj = new JSONObject();
         try {
             jsonObj.put(GroupingConstants.GROUP_ID, groupIdRes);
             jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
             jsonObj.put(GroupingConstants.COMPONENT_LIST, componentList);
             jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy);
         } catch (JSONException e) {
             LOGGER.info("Exeception in parsing the jsonObj");
             e.printStackTrace();
         }
         return jsonObj;*/

         JSONObject jsonObj = new JSONObject();
         JSONObject jsonObjComponent = new JSONObject();
         GroupAttributeForm groupAttributeForm = null;
         JSONArray jsonArray = new JSONArray();
         try {       
             //selectedSplitAttributeList = createGroupForm.getGroupAttributeFormList();
             for(int i = 0; i < selectedSplitAttributeList.size(); i++){
                 groupAttributeForm = selectedSplitAttributeList.get(i);
                 jsonObjComponent.put(GroupingConstants.COMPONENT_ID, groupAttributeForm.getOrinNumber());
                 jsonObjComponent.put(GroupingConstants.COMPONENT_IS_DEFAULT, groupAttributeForm.getIsDefault());
                 jsonObjComponent.put(GroupingConstants.COMPONENT_COLOR, groupAttributeForm.getColorCode());
                 jsonObjComponent.put(GroupingConstants.COMPONENT_SIZE, groupAttributeForm.getSize());
                 jsonArray.put(jsonObjComponent);
             }
             
             jsonObj.put(GroupingConstants.GROUP_ID, groupIdRes);
             jsonObj.put(GroupingConstants.GROUP_TYPE, groupType);
             jsonObj.put(GroupingConstants.COMPONENT_LIST, jsonArray);
             jsonObj.put(GroupingConstants.MODIFIED_BY, updatedBy);
         } catch (JSONException e) {
             LOGGER.info("Exeception in parsing the jsonObj");
             e.printStackTrace();
         }
         return jsonObj;
      }
      

      /**
       * This method is used to call Add Component to Split SKU Service 
       * @param jsonArray
       * @return
       * @throws Exception
       * @throws PEPFetchException
       */
      public String callAddComponentSSGService(JSONObject jsonStyleSpliSku) throws Exception, PEPFetchException {
          LOGGER.info("Entering callAddComponentSSGService-->");
          LOGGER.debug("jsonArray-->" + jsonStyleSpliSku);
          String responseMsg = "";
          try {
              Properties prop =PropertyLoader.getPropertyLoader(GroupingConstants.MESS_PROP);
              String serviceURL = prop.getProperty(GroupingConstants.ADD_COMPONENT_TO_SSG_SERVICE_URL);
              LOGGER.info("Add Component to Split SKU ServiceURL-->" + serviceURL);

              URL targetUrl = new URL(serviceURL);
              HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
              httpConnection.setDoOutput(true);
              httpConnection.setRequestMethod("POST");
              httpConnection.setRequestProperty("Content-Type","application/json");

              LOGGER.info("callAddComponentSSGService Service::Json Array-->"+ jsonStyleSpliSku.toString());

              /*JSONObject jsonMap = new JSONObject();
              jsonMap.put(GroupingConstants.CREATE_GROUP_SERVICE_LIST,jsonArray);
              String input = jsonMap.toString();*/
              
              String input = jsonStyleSpliSku.toString();
              LOGGER.debug("final object in json-->" +"\t"+ jsonStyleSpliSku.toString());
              LOGGER.info("input....json-->"+ input);
              OutputStream outputStream = httpConnection.getOutputStream();
              outputStream.write(input.getBytes());
              outputStream.flush();

              BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
              String output;
              LOGGER.info("Output from Server:::"+"\t"+"after Calling-->");
              while ((output = responseBuffer.readLine()) != null) {
                  LOGGER.info("call callAddComponentSSGService Service Output-->"+output);
                  
                  /* Response Message format-
                  Pass:- {"code":"100","description":"Group created ","status":"SUCCESS",”groupId”:”XXXXXXX”}
                  Fail:- {"code":"101","description":"Group is not created ","status":"FAIL",”groupId”:””}*/
                  
                  // Below if block is for handling single row data.
                  //if ((jsonArray != null) && (jsonArray.length() <= 1)) {
                  LOGGER.info("Single Request-->");
                  responseMsg = output;
                  //}
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
          LOGGER.info("Exiting callAddComponentSSGService-->" +responseMsg);
          return responseMsg;
      }

}
