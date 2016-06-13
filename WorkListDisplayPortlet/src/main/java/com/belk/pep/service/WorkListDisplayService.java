
package com.belk.pep.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.belk.pep.domain.PepDepartment;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.model.AdvanceSearch;
import com.belk.pep.model.PetsFound;
import com.belk.pep.model.StyleColor;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.util.ClassDetails;
import com.belk.pep.form.WorkListDisplayForm;
/**
 * This calss responsible for service delegate
 * @author AFUBXJ1
 *
 */
public interface WorkListDisplayService {
    /**
     * This method will get the Department details from the PEP_DEPARTMENT table using the pepUserId
     * @param pepUserId
     * @return
     * @throws PEPPersistencyException 
     * @throws SQLException
     */
    public ArrayList getSavedDepartmentDetailsByPepUserId(String pepUserId) throws PEPServiceException, PEPPersistencyException;
    
     /**
     * This method will return the PET details from the ADSE on base of department 
     * @param departmentNumbers
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    public List<WorkFlow> getPetDetailsByDepNos(ArrayList departmentNumbers,String email,List<String> supplierIdList) throws PEPServiceException, PEPPersistencyException;

    public List<WorkFlow> getPetDetailsByDepNosForParent(ArrayList departmentNumbers,String email,List<String> supplierIdList)
    throws PEPPersistencyException,PEPServiceException ;
    
    public List<StyleColor> getPetDetailsByDepNosForChild(String email,String parentOrin)
    throws PEPPersistencyException;
    
    /**
 * This method will update the new Department details to the DB
 * @param updatedPePDetailsToDb
 * @param pepUserId 
 * @return
 * @throws PEPPersistencyException 
 * @throws PEPServiceException 
 */
    public boolean updatePepDeptDetails(List<PepDepartment> updatedPePDetailsToDb, String pepUserId) throws PEPPersistencyException, PEPServiceException;
/**
 * This method will return the department details on base of dependent no/desc from ADSE table
 * @param departmentsToBesearched
 * @return
 * @throws PEPServiceException
 * @throws PEPPersistencyException
 */
    public List getDeptDetailsByDepNoFromADSE(String departmentsToBesearched)throws PEPServiceException, PEPPersistencyException;
/**
 * This method will return the Department Details from ADSE table on base of Lan ID for internal User
 * @param lanId
 * @return
 * @throws PEPServiceException
 * @throws PEPPersistencyException
 */
public List getDepartmentDetailsForInernalFirstTimeLogin(String lanId)throws PEPServiceException, PEPPersistencyException;
/**
 * This method will return the Pet details on base of Vendor Email
 * @param userEmailAddress
 * @param supplierId 
 * @return
 * @throws PEPServiceException
 * @throws PEPPersistencyException
 */
public List<WorkFlow> getPetDetailsByVendor(String userEmailAddress, List<String> supplierIdList)throws PEPServiceException, PEPPersistencyException;
/**
 * This method will return the Department details for the External user for the first time using Email address
 * @param userEmailAddress
 * @return
 */
public List getDepartmentDetailsForExternalFirstTimeLogin(String userEmailAddress)throws PEPServiceException, PEPPersistencyException;
/**
 * This method will return the Class details from the ADSE on base of department 
 * @param departmentNumbers
 * @return
 * @throws PEPServiceException
 * @throws PEPPersistencyException
 */
public List<ClassDetails> getClassDetailsByDepNos(String departmentNumbers) throws PEPServiceException, PEPPersistencyException ;
/**
 * This method will fetch the WorkList Details on base of the Advance search.
 * @param advanceSearch
 * @return
 * @throws PEPServiceException
 * @throws PEPPersistencyException
 */
public List<WorkFlow> getPetDetailsByAdvSearch(AdvanceSearch advanceSearch,List<String> supplierIdList,String vendorEmail)throws PEPServiceException, PEPPersistencyException; 


public List<WorkFlow> getPetDetailsByAdvSearchForParent(AdvanceSearch advanceSearch,List<String> supplierIdList,String vendorEmail)throws PEPServiceException, PEPPersistencyException;


public List<StyleColor> getPetDetailsByAdvSearchForChild(AdvanceSearch advanceSearch, String parentOrin)
throws PEPPersistencyException;

/**
 * 
 * @param jsonArray
 * @return
 * @throws Exception
 */
public String callInActivateAndActivatePetService(JSONArray jsonArray)throws Exception ; 

/**
 * Reinitiate service interface
 * @param jsonArray
 * @return
 * @throws Exception
 */
public String callReInitiatePetService(JSONArray jsonArray)throws Exception ;

/**
 * This method will update the Completion date for DCA users
 * @param orinNumberfdu
 * @param completionDatefdu
 * @return
 * @throws PEPServiceException
 */
public String callSaveCompletionDateService(JSONArray completionDateInfo) throws Exception;

public ArrayList isPETLocked(String pepUserId,String Orin,String searchPetLockedtype) throws PEPPersistencyException ;
public boolean lockPET(  String orin, String pepUserID, String pepfunction)throws PEPPersistencyException ;


    /**
     * This method will populate work like based on grouping id or name
     * @param adSearch
     * @param supplierIdList
     * @param vendorEmail
     * @return List<WorkFlow>
     */
    public List<WorkFlow> getAdvWorklistGroupingData(AdvanceSearch adSearch, List<String> supplierIdList, String vendorEmail, List<String> styleOrinList);
    
    /**
     * Method to get the groups for search pet.
     * 
     * @param groupId String
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Search Pet Date: 06/02/2016 Added
     *         By: Cognizant
     */
    public List<WorkFlow> groupSearchParent(final String groupId);

    /**
     * Method to get the child for group for search pet.
     * 
     * @param groupId String
     * @param advanceSearch AdvanceSearch
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Search Pet Date: 06/06/2016 Added
     *         By: Cognizant
     */
    List<WorkFlow> getChildForGroup(String groupId, AdvanceSearch advanceSearch);
    
    /**
     * Method to get the groups for worklist display count.
     * 
     * @param departmentNumbers ArrayList
     * @return int
     * 
     *         Method added For PIM Phase 2 - Search Pet Date: 06/06/2016 Added
     *         By: Cognizant
     */
    int getGroupWorkListCountDetails(ArrayList departmentNumbers);
    
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
    List<WorkFlow> getGroupWorkListDetails(ArrayList departmentNumbers,
        int pageNumber, String sortColumn, String sortOrder);
    /**
     * Method to get the child for group for Worklist.
     * 
     * @param groupId String
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Worklist Group Date: 06/06/2016 Added
     *         By: Cognizant
     */
    List<WorkFlow> getChildForGroupWorklist(String groupId);
    
    /**
     * This method will inactivate the group selected
     * @param jsonArray
     * @return
     * @throws Exception
     */
    public String callInactivateGroupService(JSONArray jsonArray) throws Exception,PEPFetchException;


    /**
     * 
     * @param jsonArray
     * @return
     * @throws Exception
     * @throws PEPFetchException
     */
    public String callReInitiateGroupService(JSONArray jsonArray)throws Exception, PEPFetchException;

}

