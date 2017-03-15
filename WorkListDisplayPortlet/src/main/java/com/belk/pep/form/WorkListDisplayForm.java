package com.belk.pep.form;

import java.util.ArrayList;
import java.util.List;

import com.belk.pep.domain.PepDepartment;
import com.belk.pep.model.AdvanceSearch;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.model.WorkListDisplay;
import com.belk.pep.util.DepartmentDetails;


/**
 * The Class WorkListDisplayForm.
 */
public class WorkListDisplayForm {
	
    /** The dept no. */
    public String deptNo;
    
    
    /** The total number of pets. */
    private String totalNumberOfPets;
    
    /** The page limit. */
    private String pageLimit;
    
    /** The page number list. */
    private List <String> pageNumberList;
    
    /** The selected page. */
    private String selectedPage;
    
    /** The total pageno. */
    private String totalPageno;
    
    /** The previous count. */
    private String previousCount;
    
    /** The next count. */
    private String nextCount;
    
    /** The selected column. */
    private String selectedColumn;
    
    /** The sorting ascending. */
    private String sortingAscending;
    
    /** The sorting image. */
    private String sortingImage;
    
    /** The selected department from db. */
    private List <DepartmentDetails> selectedDepartmentFromDB;
    
    /** The selected pep department from db. */
    private List <PepDepartment> selectedPepDepartmentFromDB;
    
    /** The pep user id. */
    private String pepUserID;
    
    /** The test string. */
    private String testString;
    
    /** The searched deptdetails from adse. */
    private List <DepartmentDetails> searchedDeptdetailsFromADSE; 
    
    /** The first timesearched deptdetails from adse. */
    private List <DepartmentDetails> firstTimesearchedDeptdetailsFromADSE;
    
    /** DeptSearchResult**/
    private String deptSearchResult;
    
    
    /** User role**/
    private String roleEditable;
    
    //TODO : need to remove below once New WorkListDisplay is implemented
    /** The work flowlist. */
    private List <WorkFlow>  workFlowlist;
    
    /** The work flowlist. */
    private List <WorkFlow>  fullWorkFlowlist;
    
    
    /** The workListDisplay**/
    
    private WorkListDisplay workListDisplay;
    
    /** The petNotFound **/
    
    private String petNotFound;
    
    /** The petNotFound **/    
    private String readOnlyUser="no";
    private boolean isOmniChannelVendor=false;
    
    private String vendorEmail;
    
    
    private String displayPagination="no";
    
    private String startIndex;
    
    private String endIndex;
    
    //Completion date edit start
    private String roleName;
    
    private String updateCompletionDateMsg;
    
    
    public String getUpdateCompletionDateMsg() {
        return updateCompletionDateMsg;
    }

    public void setUpdateCompletionDateMsg(String updateCompletionDateMsg) {
        this.updateCompletionDateMsg = updateCompletionDateMsg;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    //Completion date edit end

    public String getDisplayPagination() {
        return displayPagination;
    }

    public void setDisplayPagination(String displayPagination) {
        this.displayPagination = displayPagination;
    }

    
    
    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public String getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(String endIndex) {
        this.endIndex = endIndex;
    }

    /**
     * Gets the sorting image.
     *
     * @return the sorting image
     */
    public String getSortingImage() {
        return sortingImage;
    }

    /**
     * Sets the sorting image.
     *
     * @param sortingImage the new sorting image
     */
    public void setSortingImage(String sortingImage) {
        this.sortingImage = sortingImage;
    }

    /**
     * Gets the sorting ascending.
     *
     * @return the sorting ascending
     */
    public String getSortingAscending() {
        return sortingAscending;
    }

    /**
     * Sets the sorting ascending.
     *
     * @param sortingAscending the new sorting ascending
     */
    public void setSortingAscending(String sortingAscending) {
        this.sortingAscending = sortingAscending;
    }

    /**
     * Gets the next count.
     *
     * @return the next count
     */
    public String getNextCount() {
        return nextCount;
    }

    /**
     * Sets the next count.
     *
     * @param nextCount the new next count
     */
    public void setNextCount(String nextCount) {
        this.nextCount = nextCount;
    }

    /**
     * Gets the previous count.
     *
     * @return the previous count
     */
    public String getPreviousCount() {
        return previousCount;
    }

    /**
     * Sets the previous count.
     *
     * @param previousCount the new previous count
     */
    public void setPreviousCount(String previousCount) {
        this.previousCount = previousCount;
    }

    /**
     * Gets the total pageno.
     *
     * @return the total pageno
     */
    public String getTotalPageno() {
        return totalPageno;
    }

    /**
     * Sets the total pageno.
     *
     * @param totalPageno the new total pageno
     */
    public void setTotalPageno(String totalPageno) {
        this.totalPageno = totalPageno;
    }

    /**
     * Gets the page limit.
     *
     * @return the page limit
     */
    public String getPageLimit() {
        return pageLimit;
    }

    /**
     * Sets the page limit.
     *
     * @param pageLimit the new page limit
     */
    public void setPageLimit(String pageLimit) {
        this.pageLimit = pageLimit;
    }

    /**
     * Gets the dept no.
     *
     * @return the dept no
     */
    public String getDeptNo() {
        return deptNo;
    }

    /**
     * Sets the dept no.
     *
     * @param deptNo the new dept no
     */
    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    /**
     * Gets the work flowlist.
     *
     * @return the work flowlist
     */
    public List<WorkFlow> getWorkFlowlist() {
        return workFlowlist;
    }

    /**
     * Sets the work flowlist.
     *
     * @param workFlowlist the new work flowlist
     */
    public void setWorkFlowlist(List<WorkFlow> workFlowlist) {
        this.workFlowlist = workFlowlist;
    }

    /**
     * Gets the total number of pets.
     *
     * @return the total number of pets
     */
    public String getTotalNumberOfPets() {
        return totalNumberOfPets;
    }

    /**
     * Sets the total number of pets.
     *
     * @param totalNumberOfPets the new total number of pets
     */
    public void setTotalNumberOfPets(String totalNumberOfPets) {
        this.totalNumberOfPets = totalNumberOfPets;
    }

    /**
     * Gets the page number list.
     *
     * @return the page number list
     */
    public List<String> getPageNumberList() {
        return pageNumberList;
    }

    /**
     * Sets the page number list.
     *
     * @param pageNumberList the new page number list
     */
    public void setPageNumberList(List<String> pageNumberList) {
        this.pageNumberList = pageNumberList;
    }

    /**
     * Gets the selected page.
     *
     * @return the selected page
     */
    public String getSelectedPage() {
        return selectedPage;
    }

    /**
     * Sets the selected page.
     *
     * @param selectedPage the new selected page
     */
    public void setSelectedPage(String selectedPage) {
        this.selectedPage = selectedPage;
    }

    /**
     * Gets the selected column.
     *
     * @return the selected column
     */
    public String getSelectedColumn() {
        return selectedColumn;
    }

    /**
     * Sets the selected column.
     *
     * @param selectedColumn the new selected column
     */
    public void setSelectedColumn(String selectedColumn) {
        this.selectedColumn = selectedColumn;
    }

    /**
     * Gets the selected department from db.
     *
     * @return the selected department from db
     */
    public List<DepartmentDetails> getSelectedDepartmentFromDB() {
        return selectedDepartmentFromDB;
    }

    /**
     * Sets the selected department from db.
     *
     * @param selectedDepartmentFromDB the new selected department from db
     */
    public void setSelectedDepartmentFromDB(
        List<DepartmentDetails> selectedDepartmentFromDB) {
        this.selectedDepartmentFromDB = selectedDepartmentFromDB;
    }

    /**
     * Gets the selected pep department from db.
     *
     * @return the selected pep department from db
     */
    public List<PepDepartment> getSelectedPepDepartmentFromDB() {
        return selectedPepDepartmentFromDB;
    }

    /**
     * Sets the selected pep department from db.
     *
     * @param selectedPepDepartmentFromDB the new selected pep department from db
     */
    public void setSelectedPepDepartmentFromDB(
        List<PepDepartment> selectedPepDepartmentFromDB) {
        this.selectedPepDepartmentFromDB = selectedPepDepartmentFromDB;
    }

    /**
     * Gets the pep user id.
     *
     * @return the pep user id
     */
    public String getPepUserID() {
        return pepUserID;
    }

    /**
     * Sets the pep user id.
     *
     * @param pepUserID the new pep user id
     */
    public void setPepUserID(String pepUserID) {
        this.pepUserID = pepUserID;
    }

    /**
     * Gets the test string.
     *
     * @return the test string
     */
    public String getTestString() {
        return testString;
    }

    /**
     * Sets the test string.
     *
     * @param testString the new test string
     */
    public void setTestString(String testString) {
        this.testString = testString;
    }

    /**
     * Gets the searched deptdetails from adse.
     *
     * @return the searched deptdetails from adse
     */
    public List<DepartmentDetails> getSearchedDeptdetailsFromADSE() {
        return searchedDeptdetailsFromADSE;
    }

    /**
     * Sets the searched deptdetails from adse.
     *
     * @param searchedDeptdetailsFromADSE the new searched deptdetails from adse
     */
    public void setSearchedDeptdetailsFromADSE(
        List<DepartmentDetails> searchedDeptdetailsFromADSE) {
        this.searchedDeptdetailsFromADSE = searchedDeptdetailsFromADSE;
    }

    /**
     * Gets the first timesearched deptdetails from adse.
     *
     * @return the first timesearched deptdetails from adse
     */
    public List<DepartmentDetails> getFirstTimesearchedDeptdetailsFromADSE() {
        return firstTimesearchedDeptdetailsFromADSE;
    }

    /**
     * Sets the first timesearched deptdetails from adse.
     *
     * @param firstTimesearchedDeptdetailsFromADSE the new first timesearched deptdetails from adse
     */
    public void setFirstTimesearchedDeptdetailsFromADSE(
        List<DepartmentDetails> firstTimesearchedDeptdetailsFromADSE) {
        this.firstTimesearchedDeptdetailsFromADSE =
            firstTimesearchedDeptdetailsFromADSE;
    }

    public String getDeptSearchResult() {
        return deptSearchResult;
    }

    public void setDeptSearchResult(String deptSearchResult) {
        this.deptSearchResult = deptSearchResult;
    }

    public String getRoleEditable() {
        return roleEditable;
    }

    public void setRoleEditable(String roleEditable) {
        this.roleEditable = roleEditable;
    }

    public WorkListDisplay getWorkListDisplay() {
        return workListDisplay;
    }

    public void setWorkListDisplay(WorkListDisplay workListDisplay) {
        this.workListDisplay = workListDisplay;
    }

    public String getPetNotFound() {
        return petNotFound;
    }

    public void setPetNotFound(String petNotFound) {
        this.petNotFound = petNotFound;
    }
	
	    public String getReadOnlyUser() {
        return readOnlyUser;
    }

    public void setReadOnlyUser(String readOnlyUser) {
        this.readOnlyUser = readOnlyUser;
    }
	
	
	 public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

	
//For Advance Search
/** AdvanceSearchClicked **/
    
    private String advSearchClicked;
    
    private String searchClicked;
    
    private String searchResultsReturned;
    
    public String getSearchClicked() {
        return searchClicked;
    }

    public void setSearchClicked(String searchClicked) {
        this.searchClicked = searchClicked;
    }


    public String getSearchResultsReturned() {
		return searchResultsReturned;
	}

	public void setSearchResultsReturned(String searchResultsReturned) {
		this.searchResultsReturned = searchResultsReturned;
	}

    private AdvanceSearch advanceSearch;
    
    
    public String getAdvSearchClicked() {
        return advSearchClicked;
    }

    public void setAdvSearchClicked(String advSearchClicked) {
        this.advSearchClicked = advSearchClicked;
    }

    public AdvanceSearch getAdvanceSearch() {
        return advanceSearch;
    }

    public void setAdvanceSearch(AdvanceSearch advanceSearch) {
        this.advanceSearch = advanceSearch;
    }

    public List<WorkFlow> getFullWorkFlowlist() {
        return fullWorkFlowlist;
    }

    public void setFullWorkFlowlist(List<WorkFlow> fullWorkFlowlist) {
        this.fullWorkFlowlist = fullWorkFlowlist;
    }


	private String workListType;


    /**
     * @return the workListType
     */
    public String getWorkListType() {
        return workListType;
    }

    /**
     * @param workListType the workListType to set
     */
    public void setWorkListType(String workListType) {
        this.workListType = workListType;
    }
      
    
    
    
  }
