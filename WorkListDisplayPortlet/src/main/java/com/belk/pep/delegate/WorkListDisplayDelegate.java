package com.belk.pep.delegate;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.belk.pep.domain.ExternalUser;
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
import com.belk.pep.form.WorkListDisplayForm;

/**
 * The Class ExternalVendorLoginDelegate.
 * @author AFUBXJ1
 *
 */
public class WorkListDisplayDelegate {
    
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(WorkListDisplayDelegate.class.getName());     
    
    
    /** The user service. */
    private WorkListDisplayService workListDisplayService;


    public WorkListDisplayService getWorkListDisplayService() {
        return workListDisplayService;
    }
    public void setWorkListDisplayService(
        WorkListDisplayService workListDisplayService) {
        this.workListDisplayService = workListDisplayService;
    }
    /**
     * This method will get the Department details from the PEP_DEPARTMENT table using the pepUserId
     * @param pepUserId
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public ArrayList getSavedDepartmentDetailsByPepUserId(String pepUserId) throws PEPServiceException, PEPPersistencyException {
        return workListDisplayService.getSavedDepartmentDetailsByPepUserId(pepUserId);
    }
    
    /**
     * This method will return the PET details from the ADSE on base of department 
     * @param departmentNumbers
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public List<WorkFlow> getPetDetailsByDepNos(ArrayList departmentNumbers,String email,List<String> supplierIdList) throws PEPServiceException, PEPPersistencyException {
        return workListDisplayService.getPetDetailsByDepNos(departmentNumbers,email,supplierIdList);  
    }
    
    public List<WorkFlow> getPetDetailsByDepNosForParent(List departmentNumbers,String email,List<String> supplierIdList,
    		int startIndex, int maxResults, String sortColumn,String sortOrder)
    throws PEPPersistencyException,PEPServiceException {
        return workListDisplayService.getPetDetailsByDepNosForParent(departmentNumbers,email,supplierIdList, startIndex, maxResults, sortColumn, sortOrder);  
    }
    
    public List<StyleColor> getPetDetailsByDepNosForChild(String email,String parentOrin)
    throws PEPPersistencyException{
        return workListDisplayService.getPetDetailsByDepNosForChild(email,parentOrin); 
    }
    
    /**
     * This method will update the new Department details to the DB
     * @param updatedPePDetailsToDb
     * @param pepUserId 
     * @return
     * @throws PEPServiceException 
     * @throws PEPPersistencyException 
     */
    public boolean updatePepDeptDetails(
        List<PepDepartment> updatedPePDetailsToDb, String pepUserId) throws PEPPersistencyException, PEPServiceException {
        return workListDisplayService.updatePepDeptDetails(updatedPePDetailsToDb,pepUserId); 
    }
    /**
     * This method will return the department details on base of dependent no/desc from ADSE table
     * @param departmentsToBesearched
     * @return
     * @throws PEPPersistencyException 
     * @throws PEPServiceException 
     */
    public List getDepartmentDetailsFromADSE(String departmentsToBesearched) throws PEPServiceException, PEPPersistencyException {
        return workListDisplayService.getDeptDetailsByDepNoFromADSE(departmentsToBesearched); 
    }
    /**
     * This method will return the Department Details from ADSE table on base of Lan ID for internal User
     * @param lanId
     * @return
     */
    public List getDepartmentDetailsForInernalFirstTimeLogin(String lanId) throws PEPServiceException, PEPPersistencyException {
        return workListDisplayService.getDepartmentDetailsForInernalFirstTimeLogin(lanId); 
    }
    
    /**
     * This method will return the Pet details on base of Vendor email address
     * @param userEmailAddress
     * @param supplierId 
     * @return
     */
    public List<WorkFlow> getPetDetailsByVendor(String userEmailAddress, List<String> supplierIdList) throws PEPServiceException, PEPPersistencyException {
        return workListDisplayService.getPetDetailsByVendor(userEmailAddress, supplierIdList);
    }
    
    /**
     * This method will return the Department details for the External user for the first time using Email address
     * @param userEmailAddress
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public List getDepartmentDetailsForExternalFirstTimeLogin(
        String userEmailAddress) throws PEPServiceException, PEPPersistencyException {
        return workListDisplayService.getDepartmentDetailsForExternalFirstTimeLogin(userEmailAddress);
    }
    
    /**
     * This method will return the Class details from the ADSE on base of department 
     * @param departmentNumbers
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public List<ClassDetails> getClassDetailsByDepNos(String departmentNumbers) throws PEPServiceException, PEPPersistencyException {
        return workListDisplayService.getClassDetailsByDepNos(departmentNumbers);  
    }
    /**
     * This method will fetch the WorkList Details on base of the Advance search.
     * @param advanceSearch
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public List<WorkFlow> getPetDetailsByAdvSearch(AdvanceSearch advanceSearch,List<String> supplierIdList,String vendorEmail) throws PEPServiceException, PEPPersistencyException  {
        return workListDisplayService.getPetDetailsByAdvSearch(advanceSearch,supplierIdList,vendorEmail);  
    }
    
    public List<WorkFlow> getPetDetailsByAdvSearchForParent(AdvanceSearch advanceSearch,List<String> supplierIdList,String vendorEmail, Integer startIndex, Integer maxResults) throws PEPServiceException, PEPPersistencyException  {
        return workListDisplayService.getPetDetailsByAdvSearchForParent(advanceSearch,supplierIdList,vendorEmail, startIndex, maxResults);  
    }

    public List<StyleColor> getPetDetailsByAdvSearchForChild(AdvanceSearch advanceSearch, String parentOrin)
    throws PEPPersistencyException  {
        return workListDisplayService.getPetDetailsByAdvSearchForChild(advanceSearch,parentOrin);  
    }
    
    /**
     * Default Inactivate service call delegate
     * @param jsonArray
     * @return
     * @throws Exception
     */
    public String callInActivateAndActivatePetService(JSONArray jsonArray)throws Exception {
        LOGGER.info("Activate/Inactivate Delegate:: callInActivateAndActivatePetService");
        return workListDisplayService.callInActivateAndActivatePetService(jsonArray); 
        
    }
    /**
     * Reinitiate pet service
     * @param jsonArray
     * @return
     * @throws Exception
     */
    public String callReInitiatePetService(JSONArray jsonArray)throws Exception {
        LOGGER.info("ReInitiate Delegate:: callReInitiatePetService");
        return workListDisplayService.callReInitiatePetService(jsonArray); 
        
    }
     /** 
     * This method will update the Completion date for DCA users
     * @param orinNumberfdu
     * @param completionDatefdu
     * @return
     */
    public String callSaveCompletionDateService(JSONArray completionDateInfo) throws Exception,PEPFetchException {

        LOGGER.info("worklistdisplaydelegate:: callSaveCompletionDateService");
        return workListDisplayService.callSaveCompletionDateService(completionDateInfo);
    }
    public ArrayList isPETLocked(String pepUserId,String orin, String searchPetLockedtype) throws PEPPersistencyException{
        LOGGER.info("worklistdisplaydelegate:: lockPET");
        ArrayList petLockedDtls = null;
        petLockedDtls = workListDisplayService.isPETLocked(orin,pepUserId,searchPetLockedtype);
       return petLockedDtls;
        
    }
    public boolean lockPET(  String orin, String pepUserID, String pepfunction)throws PEPPersistencyException {
        LOGGER.info("worklistdisplaydelegate:: lockPET");
         workListDisplayService.lockPET(orin,pepUserID,pepfunction);
        return false;
        
    }
    
    /**
     * This method will populate the work list based on grouping id or name
     * @throws PEPServiceException 
     */
    public List<WorkFlow> getAdvWorklistGroupingData(final AdvanceSearch adSearch,
        final List<String> supplierIdList, final String vendorEmail, Integer startIndex, Integer maxResults)
        throws PEPPersistencyException, PEPServiceException {
        LOGGER.info("Entering getAdvWorklistGroupingData() in Delegate class");
        List<WorkFlow> workflowList = new ArrayList<WorkFlow>();
        List<WorkFlow> styleWorkflowList = new ArrayList<WorkFlow>();
        List<WorkFlow> parentWorkflowList = new ArrayList<WorkFlow>();
        List<String> styleOrinList = new ArrayList<String>();
        List<WorkFlow> groupSearchList = new ArrayList<WorkFlow>();
        //AFUAXK4
        if(adSearch.getGroupingID() != null && !adSearch.getGroupingID().equals("")
                || adSearch.getGroupingName() != null && !adSearch.getGroupingName().equals(""))
        { 
            workflowList = workListDisplayService.getAdvWorklistGroupingData(adSearch, supplierIdList, vendorEmail, styleOrinList);
            LOGGER.info("List size: " + workflowList.size());
            if(adSearch.getGroupingID() != null && !adSearch.getGroupingID().equals(""))
            {
                if(workflowList.size() == 1)
                {
                    parentWorkflowList = workListDisplayService.groupSearchParent(workflowList.get(0).getOrinNumber());
                    workflowList.addAll(parentWorkflowList);
                }
            }
        }
        else
        {
            styleWorkflowList = workListDisplayService.getPetDetailsByAdvSearchForParent(adSearch, supplierIdList, vendorEmail, startIndex, maxResults);
            LOGGER.info("List 1 size: " + styleWorkflowList.size());
            String styleOrin = "";
            for(final WorkFlow flow : styleWorkflowList)
            {
                if(flow.getEntryType().equalsIgnoreCase("Style"))
                {
                    styleOrin = flow.getOrinNumber();
                    styleOrinList.add(styleOrin);
                }
            }
            if(styleWorkflowList.size() > 0)
            {
                workflowList = workListDisplayService.getAdvWorklistGroupingData(new AdvanceSearch(), supplierIdList, vendorEmail, styleOrinList);
            }
            groupSearchList = workListDisplayService.getAdvWorklistGroupingData(adSearch, supplierIdList, vendorEmail, new ArrayList<String>());
            
            LOGGER.info("List 2 size: " + workflowList.size());
            workflowList.addAll(groupSearchList);
            LOGGER.info("List 3 size: " + groupSearchList.size());
            LOGGER.info("List 4 size: " + workflowList.size());
            Map<String, WorkFlow> uniqueValues = new HashMap<String, WorkFlow>();
            for(WorkFlow workFlow: workflowList)
            {
                String key = workFlow.getOrinNumber();
                if(!uniqueValues.containsKey(key))
                {
                    uniqueValues.put(key, workFlow);
                }
            }
            if(uniqueValues.size() > 0)
            {
            	List<WorkFlow> uniqueList = new ArrayList<WorkFlow>(uniqueValues.values());            
            	workflowList = uniqueList;
            }
            LOGGER.info("List 5 size: " + workflowList.size());
            styleWorkflowList.addAll(workflowList);
            workflowList = styleWorkflowList;   
            LOGGER.info("List 6 size: " + workflowList.size());
        }
        
        return workflowList;
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
    public List<WorkFlow> getChildForGroup(final String groupId, final AdvanceSearch advanceSearch,
    		final String parengGroupId) {
        LOGGER.info("WorkListDisplayDelegate getChildForGroup Start");
        List<WorkFlow> workFlowList = null;
        workFlowList = workListDisplayService.getChildForGroup(groupId, advanceSearch, parengGroupId);
        LOGGER.info("WorkListDisplayDelegate getChildForGroup End");
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
    public List<WorkFlow> getGroupWorkListDetails(final List departmentNumbers, int startIndex, int maxResult, String sortColumn, String sortOrder) {
        LOGGER.info("WorkListDisplayDelegate getGroupWorkListDetails Start");
        List<WorkFlow> workFlowList = null;
        workFlowList = workListDisplayService.getGroupWorkListDetails(departmentNumbers, startIndex, maxResult, sortColumn, sortOrder);
        LOGGER.info("WorkListDisplayDelegate getGroupWorkListDetails End");
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
    public int getGroupWorkListCountDetails(final List departmentNumbers) {
        LOGGER.info("WorkListDisplayDelegate getGroupWorkListCountDetails Start");
        int totalRecordsCount = 0;
        totalRecordsCount = workListDisplayService.getGroupWorkListCountDetails(departmentNumbers);
        LOGGER.info("WorkListDisplayDelegate getGroupWorkListCountDetails End");
        return totalRecordsCount;
    }
    
    /**
     * Method to get the child for group for Worklist.
     * 
     * @param groupId String
     * @param parentGroupId String
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Worklist Date: 06/09/2016 Added
     *         By: Cognizant
     */
    public List<WorkFlow> getChildForGroupWorklist(final String groupId, final String parentGroupId) {
        LOGGER.info("WorkListDisplayDelegate getChildForGroupWorklist Start");
        List<WorkFlow> workFlowList = null;
        workFlowList = workListDisplayService.getChildForGroupWorklist(groupId, parentGroupId);
        LOGGER.info("WorkListDisplayDelegate getChildForGroupWorklist End");
        return workFlowList;
    }
    
    /**
     * 
     * @param jsonArray
     *            JSONArray
     * @return String
     * @throws Exception
     */
    public String callReInitiateGroupService(JSONArray jsonArray)
        throws Exception {
        LOGGER.info("RWorkLisrDelegate callReInitiateGroupService : Starts");
        return workListDisplayService.callReInitiateGroupService(jsonArray);
    }

    /**
     * 
     * @param jsonArray
     *            JSONArray
     * @return String
     * @throws Exception
     */
    public String callInactivateGroupService(JSONArray jsonArray)
        throws Exception {
        LOGGER.info("Inactivate group Delegate : callInactivateGroupSerivce");
        return workListDisplayService.callInactivateGroupService(jsonArray);
    }
    
    /**
     * MissingAsset pet service
     * @param jsonArray
     * @return
     * @throws Exception
     */
    public String callMissingAssetPetService(JSONArray jsonArray)throws Exception {
        LOGGER.info("MissingAsset Delegate:: callMissingAssetPetService");
        return workListDisplayService.callMissingAssetPetService(jsonArray); 
        
    }
    
}
