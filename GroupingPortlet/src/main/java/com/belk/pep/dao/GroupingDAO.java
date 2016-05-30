
package com.belk.pep.dao;

import java.util.ArrayList;
import java.util.List;

import com.belk.pep.dto.ClassDetails;
import com.belk.pep.dto.CreateGroupDTO;
import com.belk.pep.dto.DepartmentDetails;
import com.belk.pep.dto.GroupSearchDTO;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.GroupAttributeForm;
import com.belk.pep.form.GroupSearchForm;

/**
 * This class responsible for handling all the DAO call to the VP Database
 * @author AFUPYB3
 *
 */

public  interface GroupingDAO {

    public CreateGroupDTO getGroupHeaderDetails(String groupId);
    
    public List<GroupAttributeForm> getSplitColorDetails(String vendorStyleNo, String styleOrin);
    public List<GroupAttributeForm> getSplitSKUDetails(String vendorStyleNo, String styleOrin);
    
    /**
     * Method to get the groups for search group.
     *    
     * @param groupSearchForm GroupSearchForm   
     * @return GroupSearchForm 
     * 
     * Method added For PIM Phase 2 - groupSearch
     * Date: 05/19/2016
     * Added By: Cognizant
     */
    List<GroupSearchDTO> groupSearch(GroupSearchForm groupSearchForm) throws PEPServiceException, PEPPersistencyException;
    
    /**
     * Method to get the groups count for search group.
     *    
     * @param groupSearchForm GroupSearchForm   
     * @return GroupSearchForm 
     * 
     * Method added For PIM Phase 2 - groupSearch
     * Date: 05/19/2016
     * Added By: Cognizant
     */
    public int groupSearchCount(GroupSearchForm groupSearchForm)
    throws PEPServiceException, PEPPersistencyException;

    /**
     * Method to get the groups for search group.
     *    
     * @param groupSearchForm GroupSearchForm   
     * @return GroupSearchForm 
     * 
     * Method added For PIM Phase 2 - groupSearch
     * Date: 05/19/2016
     * Added By: Cognizant
     * @throws PEPPersistencyException 
     */
    public List<GroupSearchDTO> groupSearchParent(List<GroupSearchDTO> groupSearchDTOList)
        throws PEPServiceException, PEPPersistencyException;
    
    /**
     * Method to get the Depts for search group.
     *    
     * @param departmentsToBesearched String   
     * @return ArrayList 
     * 
     * Method added For PIM Phase 2 - groupSearch
     * Date: 05/25/2016
     * Added By: Cognizant
     * @throws PEPPersistencyException 
     */
    public ArrayList<DepartmentDetails> getDeptDetailsByDepNoFromADSE() throws PEPPersistencyException;
    
    /**
     * Method to get the classes for search group.
     *    
     * @param departmentNumbers String   
     * @return List<ClassDetails> 
     * 
     * Method added For PIM Phase 2 - groupSearch
     * Date: 05/26/2016
     * Added By: Cognizant
     * @throws PEPPersistencyException 
     */
    public List<ClassDetails> getClassDetailsByDepNos(String departmentNumbers)
           throws PEPPersistencyException;

}
