
package com.belk.pep.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.belk.pep.domain.PepDepartment;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.WorkListDisplayForm;
import com.belk.pep.model.AdvanceSearch;
import com.belk.pep.model.PetsFound;
import com.belk.pep.model.StyleColor;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.util.ClassDetails;
import com.belk.pep.util.DepartmentDetails;
/**
 * This class responsible for handling all the DAO call to the VP Database
 * @author AFUBXJ1
 *
 */

public  interface WorkListDisplayDAO {
    /**
     * This method will get the Department details from the PEP_DEPARTMENT table using the pepUserId
     * @param pepUserId
     * @return
     * @throws PEPPersistencyException
     */
    public ArrayList getSavedDepartmentDetailsByPepUserId(String pepUserId) throws PEPPersistencyException ;
    
    
     /**
     * This method will return the PET details from the ADSE on base of department 
     * @param departmentNumbers
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    
    public List<WorkFlow> getPetDetailsByDepNos(ArrayList departmentNumbers,String email,List<String> supplierIdList) throws PEPPersistencyException ;
    
    public List<WorkFlow> getPetDetailsByDepNosForParent(ArrayList departmentNumbers,String email,List<String> supplierIdList)
    throws PEPPersistencyException;
    
    public List<StyleColor> getPetDetailsByDepNosForChild(String email,String parentOrin)
    throws PEPPersistencyException;
    
    

/**
 * This method will update the Department details in to the DB
 * @param updatedPePDetailsToDb
 * @param pepUserID 
 * @return
 */
    public boolean updatePepDeptDetails(
        List<PepDepartment> updatedPePDetailsToDb, String pepUserID) throws PEPPersistencyException ;

    /**
     * This method will return the department details on base of dependent no/desc from ADSE table
     * @param departmentsToBesearched
     * @return
     * @throws PEPPersistencyException
     * @throws  
     */
    public ArrayList getDeptDetailsByDepNoFromADSE(String departmentsToBesearched)throws PEPPersistencyException;

/**
 * This method will return the Department Details from ADSE table on base of Lan ID for internal User
 * @param lanId
 * @return
 */
    public ArrayList getDepartmentDetailsForInernalFirstTimeLogin(String lanId)throws PEPPersistencyException;

/**
 * This method will return the Pet details on base of Vendor Email
 * @param userEmailAddress
 * @param supplierId 
 * @return
 */
    public List<WorkFlow> getPetDetailsByVendor(String userEmailAddress, List<String> supplierIdList)throws PEPPersistencyException;

/**
 * This method will return the Department details for the External user for the first time using Email address
 * @param userEmailAddress
 * @return
 * @throws PEPPersistencyException
 */
    public ArrayList getDepartmentDetailsForExternalFirstTimeLogin(String userEmailAddress)throws PEPPersistencyException;
    
    /**
     * Gets the department details for external user first time login.
     *
     * @param vendorEmail the vendor email
     * @return the department details for external user first time login
     * @throws PEPFetchException the PEP fetch exception
     */
    ArrayList<DepartmentDetails> getDepartmentDetailsForExternalUserFirstTimeLogin(String vendorEmail)  throws PEPFetchException;
    
    /**
     * Gets the like search department details.
     *
     * @param searchString the search string
     * @return the like search department details
     * @throws PEPFetchException the PEP fetch exception
     */
    ArrayList<DepartmentDetails> getLikeSearchDepartmentDetails(String searchString) throws PEPFetchException;
    
    /**
     * Gets the all department details.
     *
     * @return the all department details
     * @throws PEPFetchException the PEP fetch exception
     */
    ArrayList<DepartmentDetails> getAllDepartmentDetails() throws PEPFetchException;
/**
 * This method will return the Class details from the ADSE on base of department
 * @param departmentNumbers
 * @return
 * @throws PEPFetchException
 */
    public List<ClassDetails> getClassDetailsByDepNos(String departmentNumbers)throws PEPPersistencyException;

/**
 * This method will fetch the WorkList Details on base of the Advance search.
 * @param advanceSearch
 * @return
 * @throws PEPPersistencyException
 */
public List<WorkFlow> getPetDetailsByAdvSearch(AdvanceSearch advanceSearch,List<String> supplierIdList,String vendorEmail)throws PEPPersistencyException;

public List<WorkFlow> getPetDetailsByAdvSearchForParent(AdvanceSearch advanceSearch,List<String> supplierIdList,String vendorEmail)throws PEPPersistencyException;

public List<StyleColor> getPetDetailsByAdvSearchForChild(AdvanceSearch advanceSearch, String parentOrin)
throws PEPPersistencyException;


public ArrayList isPETLocked(String pepUserId,String Orin,String searchPetLockedtype) throws PEPPersistencyException ;
public boolean lockPET(  String orin, String pepUserID,String pepfunction)throws PEPPersistencyException ;

    /**
     * This method will populate work like based on grouping id or name
     * @param adSearch
     * @param supplierIdList
     * @param vendorEmail
     * @param styleOrinList
     * @return List<String>
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
     * Method to get the child of Groups for search pet.
     * 
     * @param groupId String
     * @param pageNumber int
     * @param advanceSearch AdvanceSearch
     * @param parentGroupId String
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Search Pet Date: 06/06/2016 Added
     *         By: Cognizant
     */
    public List<WorkFlow> getChildForGroup(String groupId, AdvanceSearch advanceSearch, String parentGroupId);

    /**
     * Method to get the child of Groups for search pet.
     * 
     * @param departmentNumbers ArrayList
     * @param pageNumber int
     * @param sortColumn String
     * @param sortOrder String
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Worklist Group Date: 06/08/2016 Added
     *         By: Cognizant
     */
    List<WorkFlow> getWorklistGroupData(ArrayList departmentNumbers, int pageNumber, String sortColumn, String sortOrder);

    /**
     * Method to get the groups count for Worklist group.
     * 
     * @param departmentNumbers
     *            ArrayList
     * @return int
     * 
     *         Method added For PIM Phase 2 - WorkList Group Date: 06/08/2016 Added
     *         By: Cognizant
     */
    int groupWorklistSearchCount(ArrayList departmentNumbers);

    /**
     * Method to get the child of Groups for Worklist.
     * 
     * @param groupId String
     * @param parentGroupId String
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Group Worklist Date: 06/09/2016 Added
     *         By: Cognizant
     */
    List<WorkFlow> getChildForGroupWorklist(String groupId, String parentGroupId);
}
