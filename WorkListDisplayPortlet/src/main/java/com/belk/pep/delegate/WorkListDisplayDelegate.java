package com.belk.pep.delegate;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
    
    public List<WorkFlow> getPetDetailsByDepNosForParent(ArrayList departmentNumbers,String email,List<String> supplierIdList)
    throws PEPPersistencyException,PEPServiceException {
        return workListDisplayService.getPetDetailsByDepNosForParent(departmentNumbers,email,supplierIdList);  
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
    
    public List<WorkFlow> getPetDetailsByAdvSearchForParent(AdvanceSearch advanceSearch,List<String> supplierIdList,String vendorEmail) throws PEPServiceException, PEPPersistencyException  {
        return workListDisplayService.getPetDetailsByAdvSearchForParent(advanceSearch,supplierIdList,vendorEmail);  
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
}
