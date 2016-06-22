package com.belk.pep.dao.impl;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.belk.pep.constants.WorkListDisplayConstants;
import com.belk.pep.constants.XqueryConstants;
import com.belk.pep.dao.WorkListDisplayDAO;
import com.belk.pep.domain.PepDepartment;
import com.belk.pep.domain.PetLock;
import com.belk.pep.domain.PetLockPK;
import com.belk.pep.exception.checked.PEPFetchException;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.model.AdvanceSearch;
import com.belk.pep.model.PetsFound;
import com.belk.pep.model.PetsFoundHierarchy;
import com.belk.pep.model.StyleColor;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.model.WorkListDisplay;
import com.belk.pep.util.ClassDetails;
import com.belk.pep.util.ComplexPackComparator;
import com.belk.pep.util.DepartmentDetails;
import com.belk.pep.util.HashmapWorkFlow;
import com.belk.pep.util.PropertiesFileLoader;
import com.belk.pep.util.PropertyLoader;

/**
 * This class responsible for handling all the DAO call to the VP Database.
 * @author AFUBXJ1
 *
 */

public class WorkListDisplayDAOImpl implements WorkListDisplayDAO{
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(WorkListDisplayDAOImpl.class
        .getName()); 

    /** The session factory. */
    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }




    /** The xquery constants. */
    XqueryConstants xqueryConstants= new XqueryConstants();

    /**
     * Sets the session factory.
     *
     * @param sessionFactory
     *            the new session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }   
    
    


    /**
     * This method will get the Department details from the PEP_DEPARTMENT table using the pepUserId
     * @param pepUserId
     * @return
     * @throws PEPPersistencyException
     */
    @SuppressWarnings("rawtypes")
    @Override
    public ArrayList getSavedDepartmentDetailsByPepUserId(String pepUserId) throws PEPPersistencyException 
    {
        LOGGER.info("pepUserId in the DAO layer"+pepUserId);
         ArrayList departmentDetails = new ArrayList();
         Session session = null;
         Transaction tx = null;
         try{
         session = sessionFactory.openSession();
           tx = session.beginTransaction();
           Query query = session.getNamedQuery("PepDepartment.findDept");
            if(StringUtils.isNotBlank(pepUserId))//Null check to resolve the spinning issue
            {
                query.setString("pepUsrId", pepUserId);
                List<PepDepartment> pepDepartmentList = query.list();
               
                if(null!=pepDepartmentList && pepDepartmentList.size()>0){
                    LOGGER.info("pepUserId in the worklist display DAO layer 5 "+pepDepartmentList.size());
                    for(int i=0;i<pepDepartmentList.size();i++){
                        departmentDetails.add(pepDepartmentList.get(i));  
                    }
                }  
            }
            else
            {
                LOGGER.info("pepUserId in the worklist display DAO layer is blank..."+pepUserId);
            }
           
         }catch(Exception e) {
             e.printStackTrace();
         }
         finally{
             session.flush();
             tx.commit();
             session.close(); 
         }
           
        return departmentDetails;
    }


    /**
     * This method will return the PET details from the ADSE on base of department 
     * @param departmentNumbers
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    
    public List<WorkFlow> getPetDetailsByDepNos(ArrayList departmentNumbers,String email,List<String> supplierIdList)
        throws PEPPersistencyException {
        
        ArrayList pepDetails = null;
        List<WorkFlow> workFlowList = null;
        
        try {
                LOGGER.info("getPetDetailsByDepNos....Enter");
                System.out.println("\n--->> departmentNumbers " + departmentNumbers);
                System.out.println("\n--->> email " + email);
                StringBuffer depNumbers= new StringBuffer();
                
                if(departmentNumbers!=null) {
                    for(int i=0;i<departmentNumbers.size();i++){
                        if(i==0){
                        depNumbers.append(((DepartmentDetails)departmentNumbers.get(i)).getId());
                        }else{
                            if(i<departmentNumbers.size()){
                                //Fix for the defect 20
                            depNumbers.append(","+((DepartmentDetails)departmentNumbers.get(i)).getId());
                            }else{
                                depNumbers.append(((DepartmentDetails)departmentNumbers.get(i)).getId());
                            }
                        }
                    }
                }
                LOGGER.info("The Department numbers...."+depNumbers.toString());
                try {
                    //multi supplier id changes start
                    String vendorId = null;
                    if(null!= supplierIdList && supplierIdList.size()>0){
                       for (int i=0;i<supplierIdList.size();i++){
                         if(i==0){  
                             vendorId = supplierIdList.get(i);
                         }else{
                             vendorId = vendorId+","+supplierIdList.get(i); 
                         }
                       }
                    }
                    //Modified on 1224 - AFUAXK4
                    boolean vendorLoginFlag = false;
                    if(null != email)
                    {
                        vendorLoginFlag = true;
                    }
                    LOGGER.info("Vendor id===="+vendorId);
                    
                    
                    
                    List<PetsFound> petList = getWorkListDisplayData(depNumbers.toString(),email,null,vendorId,vendorLoginFlag);    
                    //multi supplier id changes end
                    //Complex Pack changes
                    List<PetsFound> complexPackList = getWorkListDisplayDataComplexPack(depNumbers.toString(),email,null,vendorId, vendorLoginFlag);
                    petList.addAll(complexPackList);
                    //Get it ordered
                    petList = orderPetDetailsList(petList);
                    
                    WorkListDisplay workListDisplay = getFormatedPetDetails(petList);
                    workFlowList = workListDisplay.getWorkListDisplay();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                   throw new PEPPersistencyException("SQL Exception cought from getPetDetailsByDepNos");
                   
                }
                catch (ParseException e) {
                  
                    e.printStackTrace();
                    throw new PEPPersistencyException("Parse Exception cought from getPetDetailsByDepNos");
                }
                
        } catch(Exception e)    {
            e.printStackTrace();
        }
        return workFlowList;
    }
    
    
    
    
    

    /**
     * Gets the formated pet details.
     *
     * @param petList the pet list
     * @return the formated pet details
     */
    private WorkListDisplay getFormatedPetDetails(List<PetsFound> petList) {
        LOGGER.info("This is from getFormatedPetDetails...");
        String childsParentOrinNumber = null;
        String  parentOrinNumber = null;
        List<StyleColor> styleColorList = new ArrayList<StyleColor>();
        List<WorkFlow> styleList = new ArrayList<WorkFlow>();
        List<WorkFlow> styleListForWorkListDisplay = new ArrayList<WorkFlow>();
        WorkListDisplay workListDisplay= new WorkListDisplay();
        
        for(PetsFound pet:petList)
        {
            LOGGER.info("ORIN-->"+pet.getOrinNumber()+"Image Status-->"+pet.getImageState()+"Content Status-->"+pet.getContentState());
            String entryType=pet.getEntryType();
            if("StyleColor".equalsIgnoreCase(entryType) || "PackColor".equalsIgnoreCase(entryType))//Pack Color changes
            {
                childsParentOrinNumber=pet.getParentStyleOrin();
                String orinNumber=pet.getOrinNumber();
                String vendorStyle= pet.getVendorStyle();
                String vendorName=pet.getVendorName();
                String productName=pet.getProductName();
                String completionDate=pet.getCompletionDate();
              
                String contentState=pet.getContentState();
                String imageState=pet.getImageState();                   
                String deptId=pet.getDeptId();
                String ominInd=pet.getOmniChannelVendor();
                
                String petStatus = pet.getPetStatus();
                
                StyleColor styleColor = new StyleColor();
                styleColor.setEntryType(entryType);
                styleColor.setParentStyleOrinNumber(childsParentOrinNumber);
                styleColor.setOrinNumber(orinNumber);
                styleColor.setVendorStyle(vendorStyle);
                styleColor.setVendorName(vendorName);
                styleColor.setCompletionDate(completionDate);
                styleColor.setProductName(productName);
                styleColor.setPetStatus(petStatus);
                
                styleColor.setContentStatus(contentState);
                styleColor.setDeptId(deptId);
                styleColor.setImageStatus(imageState);
                styleColor.setOmniChannelVendor(ominInd);
                styleColorList.add(styleColor);//Add all the StyleColor to the  Style Color list 
                //LOGGER.info("styleColorList size.."+styleColorList.size());
              
            }
            if(("Style".equalsIgnoreCase(entryType) || "Complex Pack".equalsIgnoreCase(entryType)) )//Complex Pack changes
            {                    
             
                        String orinNumber= pet.getOrinNumber();
                        String vendorStyle= pet.getVendorStyle();
                        parentOrinNumber=pet.getParentStyleOrin();
                        String vendorName=pet.getVendorName();
                        String productName=pet.getProductName();
                        String petStatus = pet.getPetStatus();
                        String completionDate=pet.getCompletionDate();
                        String contentState=pet.getContentState();
                        String imageState=pet.getImageState();                   
                        String deptId=pet.getDeptId();
                        String ominInd=pet.getOmniChannelVendor();
                        
                        WorkFlow style = new WorkFlow();
                        style.setEntryType(entryType);
                        style.setOrinNumber(orinNumber);
                        style.setParentStyleOrinNumber(parentOrinNumber);
                        style.setVendorName(vendorName);
                        
                        style.setCompletionDate(completionDate);
                        
                        style.setDeptId(deptId);
                        style.setImageStatus(imageState);
                        style.setContentStatus(contentState);
                        style.setProductName(productName);
                        style.setPetStatus(petStatus);
                        style.setVendorStyle(vendorStyle);
                       // style.setStyleColor(styleColorList);
                        style.setOmniChannelVendor(ominInd);
                        styleList.add(style);//Add all the Style to the  Style  list  
              
                }
        }
        LOGGER.info("styleList length="+styleList.size());
        LOGGER.info("styleColorList length="+styleColorList.size()); 
        //Check for the Parent Child association
        for(WorkFlow style :styleList)
        {
           parentOrinNumber = style.getParentStyleOrinNumber();
           List<StyleColor> subStyleColorList = new ArrayList<StyleColor>();
           //Commented by AFUAXK4
           String compTempDate=null;
           for(StyleColor styleColor :styleColorList)
           {
             childsParentOrinNumber =styleColor.getParentStyleOrinNumber();
              if(parentOrinNumber!=null && parentOrinNumber.equalsIgnoreCase(childsParentOrinNumber))
              {
                  //Defect 14
                  styleColor.setVendorStyle(style.getVendorStyle());
                  compTempDate= styleColor.getCompletionDate();
                  subStyleColorList.add(styleColor);
              }
               
           }
           if(subStyleColorList.size()>0){
               //Defect Fix 258
               if(null == style.getCompletionDate()){
                   style.setCompletionDate(compTempDate);
                   style.setIsPCompletionDateNull("Y");
               }
               style.setStyleColor(subStyleColorList);//Add all the child Style Colors to the Parent Style
           }else{
           }
           styleListForWorkListDisplay.add(style);//Add  all the Styles with children Style Color to the declared worklist display list 
        }
        LOGGER.info("styleColorList.."+styleColorList.size());
        workListDisplay.setWorkListDisplay(styleListForWorkListDisplay);//Add all the Styles to the work list display form
        LOGGER.info("worklistdisplay size.."+workListDisplay.getWorkListDisplay().size());
        return workListDisplay;
    }

    /** 
     * This method will populate the WorkFlowList with Dummy Data
     * @return
     */

    public ArrayList getWorkFlowList() {
        HashmapWorkFlow hashMapWorkFlow= new HashmapWorkFlow();
        ArrayList workFlowList = hashMapWorkFlow.getWorkFlows();
        return workFlowList;
    }


 /**
  * This method will update the Department table
  */
    @Override
    public boolean updatePepDeptDetails(
        List<PepDepartment> updatedPePDetailsToDb, String pepUserID)
        throws PEPPersistencyException {
        LOGGER.info("This is from updatePepDeptDetails.."+pepUserID);
        boolean isUpdated =false;
        Session session = null;
        Transaction tx = null;
        try{
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            //Remove the records of user
            Query query = session.getNamedQuery("PepDepartment.removeDept");
            if(StringUtils.isNotBlank(pepUserID))//Null check for pepUserID
            {
                query.setString("pepUsrId", pepUserID);
                query.executeUpdate();
                // Save or update the updated tables
                for(int i=0;i<updatedPePDetailsToDb.size();i++)
                {
                    LOGGER.info("This is from updatePepDeptDetails.Loop"+updatedPePDetailsToDb.get(i).getId().getDeptId());   
                    session.saveOrUpdate(updatedPePDetailsToDb.get(i));
                    isUpdated = true;
                }
               
            }
        }
        finally{
            session.flush();
            tx.commit();
            session.close();
        }
        LOGGER.info("This is from updatePepDeptDetails...Exit");
        return isUpdated;
    }


    /**
     * This method will return the department details on base of dependent no/desc from ADSE table
     * @throws PEPPersistencyException 
     * @throws PEPFetchException 
     */
    @Override
    public ArrayList getDeptDetailsByDepNoFromADSE(String departmentsToBesearched) throws PEPPersistencyException
        {
        LOGGER.info("This is from getDeptDetailsByDepNoFromADSE...Start"+departmentsToBesearched);
        //Call the method getLikeDepartmentDetails()
        ArrayList<DepartmentDetails> searchedDepartmentDetails = null;
        try {
            searchedDepartmentDetails = getLikeSearchDepartmentDetails(departmentsToBesearched);
            
        }
        catch (PEPFetchException e) {
            e.printStackTrace();
            throw new PEPPersistencyException("Exception while getting Dept Details from ADSE");
        }
        LOGGER.info("This is from getDeptDetailsByDepNoFromADSE...Exit");
        return searchedDepartmentDetails;
       
    }



/**
 * This method will return the Department Details from ADSE table on base of Lan ID for internal User
 */

    @Override
    public ArrayList getDepartmentDetailsForInernalFirstTimeLogin(String lanId)
        throws PEPPersistencyException {
        LOGGER.info("This is from getDepartmentDetailsForInernalFirstTimeLogin...Start");
        ArrayList <DepartmentDetails> searchedDepartmentDetails = getAllDepartmentDetails();
        LOGGER.info("This is from getDepartmentDetailsForInernalFirstTimeLogin...End");
        return searchedDepartmentDetails;
    }


/**
 * This method will return the Pet details on base of Vendor Email
 */
    @Override
    public List<WorkFlow> getPetDetailsByVendor(String userEmailAddress, List<String> supplierIdList)
        throws PEPPersistencyException {
        LOGGER.info("This is from getPetDetailsByVendor...Start");
        LOGGER.info("User Email Address..."+userEmailAddress);
         List<WorkFlow> workFlowList = null;
         try {
           //MultiSupplierID Changes start
             String vendorId = null;
            if(null!= supplierIdList && supplierIdList.size()>0){
               for (int i=0;i<supplierIdList.size();i++){
                 if(i==0){  
                     vendorId = supplierIdList.get(i);
                 }else{
                     vendorId = vendorId+","+supplierIdList.get(i); 
                 }
               }
            }
            LOGGER.info("Vendor id===="+vendorId);
            //MultiSupplierID Changes end
            List<PetsFound> petList = getWorkListDisplayData(null,userEmailAddress,null,vendorId, true);
            //Complex Pack changes for vendor
            List<PetsFound> complexPackList = getWorkListDisplayDataComplexPack(null,userEmailAddress,null,vendorId, true);
            petList.addAll(complexPackList);
            Collections.sort(petList, new ComplexPackComparator());
            WorkListDisplay workListDisplay = getFormatedPetDetails(petList);
            workFlowList = workListDisplay.getWorkListDisplay();
         }
         catch (SQLException e) {
             e.printStackTrace();
             throw new PEPPersistencyException("SQLException from getPetDetailsByVendor");
         }
         catch (ParseException e) {
             e.printStackTrace();
             throw new PEPPersistencyException("ParseException from getPetDetailsByVendor");
         }
         LOGGER.info("This is from getPetDetailsByVendor...End");
         return workFlowList;
       
    }


/**
 * This method will return the Department details for the External user for the first time using Email address
 */
@Override
public ArrayList getDepartmentDetailsForExternalFirstTimeLogin(
    String userEmailAddress) throws PEPPersistencyException {
    LOGGER.info("This is from getDepartmentDetailsForExternalFirstTimeLogin...Start");
    ArrayList<DepartmentDetails> searchedDepartmentDetails = new ArrayList();
    try {
        searchedDepartmentDetails = getDepartmentDetailsForExternalUserFirstTimeLogin(userEmailAddress);
    }
    catch (PEPFetchException e) {
        e.printStackTrace();
        throw new PEPPersistencyException("Exception While getting the Department Details");
    }
    LOGGER.info("This is from getDepartmentDetailsForExternalFirstTimeLogin...End");
    return searchedDepartmentDetails;
}


/**
 * Gets the department details for external user first time login.
 *
 * @param vendorEmail the vendor email
 * @return the department details for external user first time login
 */
public ArrayList<DepartmentDetails> getDepartmentDetailsForExternalUserFirstTimeLogin(String vendorEmail) throws PEPFetchException{
    Session session = null;
    Transaction tx = null;
    ArrayList<DepartmentDetails> adseDepartmentList = new ArrayList<DepartmentDetails>();
    try{
    session = sessionFactory.openSession();
    tx = session.beginTransaction();            
   //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
    Query query = session.createSQLQuery(xqueryConstants.getAllDepartmentDetails(vendorEmail));
    query.setParameter("vendorEmail", vendorEmail); 
    query.setFetchSize(100);
    List<Object[]> rows = query.list();
    for(Object[] row : rows){      
        DepartmentDetails departmentDetails = new DepartmentDetails();
        departmentDetails.setId(row[0].toString());
        departmentDetails.setDesc(row[1].toString());   
        adseDepartmentList.add(departmentDetails);          
    }       
    
    }finally{
     session.flush();  
     tx.commit();
     session.close();
    }
    return adseDepartmentList;    
 }



/**
* Gets the like department details it  is applicable for search box on the pop up department screen applicable for the External User and Belk User as well.
*
* @param searchString the search string
* @return the like department details
*/
public ArrayList<DepartmentDetails> getLikeSearchDepartmentDetails(String searchString) throws PEPFetchException
{
    LOGGER.info("This is from getLikeSearchDepartmentDetails...Start");
    Session session = null;
    Transaction tx = null;
    ArrayList<DepartmentDetails> adseDepartmentList = new ArrayList<DepartmentDetails>();    
    try{
         session = sessionFactory.openSession();
         tx = session.beginTransaction(); 
   //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly. 
   
   /* if(searchString.matches("[0-9]+")){//If contains only numbers then it is Department ID
        Query query = session.createSQLQuery(xqueryConstants.getLikeDepartmentDetailsForNumber(searchString)); 
        LOGGER.info("511..Query.." + query);
        if(query!=null)
        {
            List<Object[]> rows = query.list();
            if(rows!=null&&rows.size()>0)
            {
                for(Object[] row : rows){     
                    LOGGER.info("This is from getLikeSearchDepartmentDetails..Number"+row[0].toString());
                    DepartmentDetails departmentDetails = new DepartmentDetails();
                    departmentDetails.setId(row[0].toString());
                    departmentDetails.setDesc(row[1].toString());   
                    adseDepartmentList.add(departmentDetails);          
                } 
            }
        }
        
    }else{*/
        LOGGER.info("This is from getLikeSearchDepartmentDetails search is a desc");
      //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
        Query query1 = session.createSQLQuery(xqueryConstants.getLikeDepartmentDetailsForString(searchString));  
        if(query1!=null)
        {
            List<Object[]> rows1 = query1.list();
            if(rows1!=null && rows1.size()>0)
            {
                for(Object[] row : rows1){      
                    LOGGER.info("This is from getLikeSearchDepartmentDetails..String"+row[0].toString());
                    DepartmentDetails departmentDetails = new DepartmentDetails();
                    departmentDetails.setId(row[0].toString());
                    departmentDetails.setDesc(row[1].toString());   
                    adseDepartmentList.add(departmentDetails);          
                }
            }
            
        }
        
    //}
   
  }catch(Exception e){
 }
finally
{
    session.flush(); 
    tx.commit();
    session.close();
   
}
 
    LOGGER.info("This is from getLikeSearchDepartmentDetails...End");
    return adseDepartmentList; 
}


/**
 * Gets the all the merchandise department details for the Belk User,The Belk User is DCA .
 * All Merchandise department details need to be displayed on the  the Department Pop Up Screen ,when the Belk User logs in for the first time 
 * 
 * @param orinNo the orin no
 * @return the all department details
 */
public ArrayList<DepartmentDetails> getAllDepartmentDetails() 
{
    Session session = null;
    Transaction tx =null;
    ArrayList<DepartmentDetails> adseDepartmentList = new ArrayList<DepartmentDetails>();
    try{
    session = sessionFactory.openSession();
    tx = session.beginTransaction();            
   //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
    Query query = session.createSQLQuery(xqueryConstants.getAllDepartmentDetails());   
    
    List<Object[]> rows = query.list();
    for(Object[] row : rows){      
        DepartmentDetails departmentDetails = new DepartmentDetails();
        departmentDetails.setId(row[0].toString());
        departmentDetails.setDesc(row[1].toString());   
        adseDepartmentList.add(departmentDetails);          
    }       
     
    }finally{
     session.flush();  
     tx.commit();
     session.close();
    }
    return adseDepartmentList; 
}





/**
 * Gets the work list display data.
 *
 * @return the work list display data
 * @throws SQLException 
 * @throws ParseException 
 */
public List<PetsFound> getWorkListDisplayData(String depts,String email,String pepId,String supplierId, boolean vendorLogin) throws SQLException, ParseException {
    LOGGER.info("This is from getWorkListDisplayData..Start+Email:"+email );
    LOGGER.info("depts:"+depts+",email:"+email+", pepId:"+pepId+", supplierId:"+supplierId);
    List<PetsFound> petList = new ArrayList<PetsFound>();
    List<PetsFound> petListNew = new ArrayList<PetsFound>();
    String parent = "";
    Map<String,List<PetsFound>> petMap = new HashMap<String,List<PetsFound>>();
    PetsFound pet=null;
    XqueryConstants xqueryConstants= new XqueryConstants();
      Session session = null;
      Transaction tx =  null;
      
      try{
        session = sessionFactory.openSession();
        tx = session.beginTransaction();      
       //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
       // Query query = session.createSQLQuery(xqueryConstants.getWorkListDisplayData(depts, email, pepId, supplierId));

        /****Newly added for DE795,796 ******/
        Query query = session.createSQLQuery(xqueryConstants.getWorkListDisplayData());
        query.setParameter("depts", depts); 
        query.setParameter("email", email);
        query.setParameter("pepId", pepId);
        query.setParameter("supplierId", supplierId);   
        query.setFetchSize(100);
         /****END ******/
        
        LOGGER.info("Query..default123-->" + query);
        // execute delete SQL statement
        List<Object[]> rows = query.list();
        if (rows != null) {
            LOGGER.info("recordsFetched..." + rows.size());
            for(Object[] row : rows){   
                String parentStyleORIN = row[0]!=null?row[0].toString():null;
                
                String orinNumber=row[1]!=null?row[1].toString():null;
                String deptId= row[2]!=null?row[2].toString():null;
                String supplierId_temp = row[3]!=null?row[3].toString():null;
                
                
                
                String productName=row[4]!=null?row[4].toString():null;
                String entryType=row[5]!=null?row[5].toString():null;
                
                String primaryUPC=row[6]!=null?row[6].toString():null;
                String colorDesc=row[7]!=null?row[7].toString():null;
                
                String vendorName=row[8]!=null?row[8].toString():null;
                String vendorStyle=row[9]!=null?row[9].toString():null;
                String petStatus=row[10]!=null?row[10].toString():null;
                
                String imageState=row[11]!=null?row[11].toString():null;
                String contentState=row[12]!=null?row[12].toString():null;
                String completionDate=row[13]!=null?row[13].toString():null;  
                String omniChannelIndicator=row[14]!=null?row[14].toString():null;
                String sourceSystem=row[15]!=null?row[15].toString():null;
                
                petList.add(pet);
            }
            petList = petStatusMapping(petList);
            Collections.sort(petList);
            petMap = mapPetDetailsList(petList);
            petList = validatePetDetails(petMap, petList, vendorLogin);
      } 
        
     
  }catch(Exception e){
      e.printStackTrace();
    
 }
  finally
  {
      LOGGER.info("recordsFetched. worklistdisplayDAOimpl finally block.." );
      session.flush();
      tx.commit();
      session.close();
     
  }

        LOGGER.info("This is from getWorkListDisplayData..End" );
    return petList;

    
}


public List<PetsFound> getWorkListDisplayDataComplexPack(String depts,String email,String pepId,String supplierId, boolean vendorLogin) throws SQLException, ParseException {
    LOGGER.info("This is from getWorkListDisplayDataComplexPack..Start+Email:"+email );
    LOGGER.info("depts:"+depts+",email:"+email+", pepId:"+pepId+", supplierId:"+supplierId);
    List<PetsFound> petList = new ArrayList<PetsFound>();
    List<PetsFound> petListNew = new ArrayList<PetsFound>();
    PetsFound pet=null;
    String parent = "";
    Map<String,List<PetsFound>> petMap = new HashMap<String,List<PetsFound>>();
    XqueryConstants xqueryConstants= new XqueryConstants();
      Session session = null;
      Transaction tx =  null;
      Query query = null;
      Query query1 = null;
      try{
        session = sessionFactory.openSession();
        tx = session.beginTransaction();      
       //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
        //query = session.createSQLQuery(xqueryConstants.getWorkListDisplayDataComplexPack(depts, email, pepId, supplierId));
        /****Newly added for DE795,796 ******/
        query = session.createSQLQuery(xqueryConstants.getWorkListDisplayDataComplexPack());
        query.setParameter("depts", depts); 
        query.setParameter("email", email);
        query.setParameter("pepId", pepId);
        query.setParameter("supplierId", supplierId);    
        query.setFetchSize(100);
         /****END ******/
        LOGGER.info("Query..getWorkListDisplayDataComplexPack-->" + query);
        // execute delete SQL statement
        List<Object[]> rows = query.list();
        if (rows != null) {
            LOGGER.info("recordsFetched...getWorkListDisplayDataComplexPack.." + rows.size());
            for(Object[] row : rows){   
                String parentStyleORIN = row[0]!=null?row[0].toString():null;
                
                String orinNumber=row[1]!=null?row[1].toString():null;
                String deptId= row[2]!=null?row[2].toString():null;
                String supplierId_temp = row[3]!=null?row[3].toString():null;
                
                String productName=row[4]!=null?row[4].toString():null;
                String entryType=row[5]!=null?row[5].toString():null;
                
                String primaryUPC=row[6]!=null?row[6].toString():null;
                String colorDesc=row[7]!=null?row[7].toString():null;
                
                String vendorName=row[8]!=null?row[8].toString():null;
                String vendorStyle=row[9]!=null?row[9].toString():null;
                String petStatus=row[10]!=null?row[10].toString():null;
                
                String imageState=row[11]!=null?row[11].toString():null;
                String contentState=row[12]!=null?row[12].toString():null;
                String completionDate=row[13]!=null?row[13].toString():null;  
                String omniChannelIndicator=row[14]!=null?row[14].toString():null;
                String sourceSystem=row[15]!=null?row[15].toString():null;
               
                petList.add(pet);
            }
            petList = petStatusMapping(petList);
            Collections.sort(petList);
            petMap = mapPetDetailsList(petList);
            petList = validatePetDetails(petMap, petList, vendorLogin);
      } 
        
     
  }catch(Exception e){
      e.printStackTrace();
    
 }
  finally
  {
      LOGGER.info("recordsFetched. getWorkListDisplayDataComplexPack DAO finally block.." );
      session.flush();
      tx.commit();
      session.close();
     
  }

        LOGGER.info("This is from getWorkListDisplayDataComplexPack..End" );
    return petList;

    
}




/**
 * Map adse db pets to portal.
 *
 * @param parentStyleORIN the parent style orin
 * @param orinNumber the orin number
 * @param deptId the dept id
 * @param productName the product name
 * @param entryType the entry type
 * @param vendorName the vendor name
 * @param vendorStyle the vendor style
 * @param imageState the image state
 * @param contentState the content state
 * @param completionDate the completion date
 * @param pet the pet
 * @param omniChannelIndicator 
 * @return the pets found
 */
private PetsFound mapAdseDbPetsToPortal(String parentStyleORIN,
    String orinNumber, String deptId, String productName, String entryType,
    String vendorName, String vendorStyle, String imageState,
    String contentState, String completionDate, PetsFound pet, String omniChannelIndicator,
    String primaryUPC,String colorDesc,String petStatus, String sourceSystem,
    String petStyleState, String petImageState,String petContentState, String earliestComplitionDate,
    String existsInGroup, String cFAS) {
    //LOGGER.info("This is from mapAdseDbPetsToPortal..Enter" );
    pet = new PetsFound();
    pet.setParentStyleOrin(parentStyleORIN);
    pet.setOrinNumber(orinNumber);
    pet.setDeptId(deptId);
    pet.setProductName(productName); 
    pet.setEntryType(entryType);
    pet.setVendorName(vendorName);
    pet.setVendorStyle(vendorStyle);
    pet.setImageState(imageState);
    pet.setContentState(contentState);
    if(null != entryType && (("Style").equals(entryType) || ("Complex Pack").equals(entryType))){
        pet.setCompletionDate(earliestComplitionDate);
        //LOGGER.info("earliestComplitionDate" + earliestComplitionDate);
    }else{
        pet.setCompletionDate(completionDate);
        LOGGER.info("completionDate" + completionDate);
    }
    pet.setOmniChannelVendor(omniChannelIndicator);
    pet.setPrimaryUPC(primaryUPC);
    pet.setColorDesc(colorDesc);
    pet.setPetStatus(petStatus);
    pet.setReq_Type(sourceSystem);
    pet.setPetStyleState(petStyleState);
    pet.setPetImageState(petImageState);
    pet.setPetContentState(petContentState);
    pet.setEarliestComplitionDate(earliestComplitionDate);
    pet.setExistsInGroup(existsInGroup);
    pet.setCFAS(cFAS);
    return pet; 
}

/**
 * This method will return the Class details from the ADSE on base of department
 */

@Override
public List<ClassDetails> getClassDetailsByDepNos(String departmentNumbers)
    throws PEPPersistencyException {
    LOGGER.info("This is from getClassDetailsByDepNos...Start"+departmentNumbers);
    //Call the method getLikeDepartmentDetails()
    List<ClassDetails> searchedClassDetails = null;
    try {
        searchedClassDetails = getClassDetails(departmentNumbers);
        
    }
    catch (PEPFetchException e) {
        e.printStackTrace();
        throw new PEPPersistencyException("Exception while getting Class Details from ADSE");
    }
    LOGGER.info("This is from getDeptDetailsByDepNoFromADSE...Exit");
    return searchedClassDetails;
    
}

/**
 * This method will return the class details based on input departments
 * @param departmentNumbers
 * @return
 * @throws PEPFetchException
 */


private List<ClassDetails> getClassDetails(String departmentNumbers)throws PEPFetchException {
    LOGGER.info("This is from getClassDetails...Enter");
    Session session = null;
    Transaction tx = null;
    List<ClassDetails> classDetailsList = new ArrayList<ClassDetails>();
    try{
    session = sessionFactory.openSession();
    tx = session.beginTransaction();            
    LOGGER.info("This is from getClassDetails..."+departmentNumbers);
   //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
    //Query query = session.createSQLQuery(xqueryConstants.getClassDetailsUsingDeptnumbers(departmentNumbers));
    /****Newly added for DE795,796 ******/
    Query query = session.createSQLQuery(xqueryConstants.getClassDetailsUsingDeptnumbers(departmentNumbers));
    query.setParameter("deptids", departmentNumbers);
    query.setFetchSize(100);
    /****END ******/
    List<Object[]> rows = query.list();
    for(Object[] row : rows){      
        ClassDetails classDetails = new ClassDetails();
        classDetails.setId(row[0].toString());
        classDetails.setDesc(row[1].toString());   
        classDetailsList.add(classDetails);          
    }       
    
    }finally{
     session.flush();   
     tx.commit();
     session.close();
    }
    LOGGER.info("This is from getClassDetails...Exit");
    return classDetailsList; 
}

/**
 * This method will fetch the WorkList Details on base of the Advance search.
 */

@Override
public List<WorkFlow> getPetDetailsByAdvSearch(AdvanceSearch advanceSearch,List<String> supplierIdList,String vendorEmail)
    throws PEPPersistencyException {
    LOGGER.info("getPetDetailsByAdvSearch....Enter");
    ArrayList pepDetails = null;
    List<WorkFlow> workFlowList = null;
    try {
        List<PetsFound> petList = getAdvWorkListDisplayData(advanceSearch);
        LOGGER.info("getPetDetailsByAdvSearch....Enter1111" +petList.size());
        
        List<PetsFound> complexPackList = getAdvWorkListDisplayDataComplexPack(advanceSearch);
        petList.addAll(complexPackList);
        Collections.sort(petList, new ComplexPackComparator());
        
        if(null != vendorEmail)  {  
            LOGGER.info("Vendor Logged in  --- petlist size " + petList.size() + " Supplier Id size " + supplierIdList.size()+ "vendorEmail Search DAO---->"+ vendorEmail);
        List<PetsFound> petList1 = new ArrayList<PetsFound>();
        String supplierId = null;
        for(int i=0 ; i<supplierIdList.size() ;i++){
           supplierId = supplierIdList.get(i).toString();
           LOGGER.info("Supplier id outer loop -- " + supplierId);
           for(int j=0 ; j<petList.size(); j++){
               if(supplierId.equalsIgnoreCase(petList.get(j).getSupplierId())){
                   LOGGER.info("Supplier id -- " + supplierId);
                   petList1.add(petList.get(j));
               }
               
           }
        }
        petList = petList1;
        LOGGER.info("Vendor Logged in  --- petlist size bottom " + petList.size());
        }
        //Added to Sort data -- AFUAXK4
        petList = orderPetDetailsList(petList);
        
        WorkListDisplay workListDisplay = getFormatedPetDetails(petList);
        workFlowList = workListDisplay.getWorkListDisplay();
        LOGGER.info("getPetDetailsByAdvSearch DAO........Enter919" +workFlowList.size());
    }
    catch (SQLException e) {
        e.printStackTrace(System.out);
       throw new PEPPersistencyException("SQL Exception cought from getPetDetailsByAdvSearch923");
       
    }
    catch (ParseException e) {
        e.printStackTrace(System.out);
        throw new PEPPersistencyException("Parse Exception cought from getPetDetailsByAdvSearch930");
    }
    return workFlowList;
}

/**
 * This method will fetch the WorkList Details on base of the Advance search.
 * @param advanceSearch
 * @return
 * @throws SQLException
 * @throws ParseException
 */

private List<PetsFound> getAdvWorkListDisplayData(AdvanceSearch advanceSearch)throws SQLException, ParseException  {
   
        LOGGER.info("This is from getAdvWorkListDisplayData..Start" );
        List<PetsFound> petList = new ArrayList<PetsFound>();
        PetsFound pet=null;
        XqueryConstants xqueryConstants= new XqueryConstants();
        
        
            Session session = null;
            Transaction tx =  null;
            
          try{
            session = sessionFactory.openSession();
            tx = session.beginTransaction();      
           //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
            Query query = session.createSQLQuery(xqueryConstants.getAdvWorkListDisplayData(advanceSearch));  
            LOGGER.info("Query.." + query);
            // execute delete SQL statement
            List<Object[]> rows = query.list();
            if (rows != null) {
                LOGGER.info("recordsFetched..." + rows);
                for(Object[] row : rows){   
                    String parentStyleORIN = row[0]!=null?row[0].toString():null;
                    
                    String orinNumber=row[1]!=null?row[1].toString():null;
                    String deptId= row[2]!=null?row[2].toString():null;
                    
                    String supplierId=row[3]!=null?row[3].toString():null;
                    String productName=row[4]!=null?row[4].toString():null;
                    String entryType=row[5]!=null?row[5].toString():null;
                    String primaryUPC = row[6]!=null?row[6].toString():null;
                    String vendorName=row[7]!=null?row[7].toString():null;
                    String vendorStyle=row[8]!=null?row[8].toString():null;
                    String classId = row[9]!=null?row[9].toString():null;
                    String petStatus=row[10]!=null?row[10].toString():null;
                    String imageState=row[11]!=null?row[11].toString():null;
                    String contentState=row[12]!=null?row[12].toString():null;
                    String completionDate=row[13]!=null?row[13].toString():null;  
                    String omniChannelIndicator=row[14]!=null?row[14].toString():null;
                    String req_Type=row[15]!=null?row[15].toString():null;
                    String colorDesc=row[16]!=null?row[16].toString():null;
                    
                    LOGGER.info("parentStyleORIN" + "\t" + parentStyleORIN);
                    LOGGER.info("orinNumber" + "\t" + orinNumber);
                    LOGGER.info("deptId" + "\t" + deptId);
                    LOGGER.info("productName" + "\t" + productName);
                    LOGGER.info("entryType" + "\t" + entryType);
                    LOGGER.info("vendorName" + "\t" + vendorName);
                    LOGGER.info("vendorStyle" + "\t" + vendorStyle);
                    LOGGER.info("imageState" + "\t" + imageState);
                    LOGGER.info("contentState" + "\t" + contentState);
                    LOGGER.info("petStatus" + "\t" + petStatus);
                    LOGGER.info("completionDate" + "\t" + completionDate);
                    LOGGER.info("omni channel indicator" + "\t" + omniChannelIndicator);
                    //pet  = mapAdseDbPetsToPortalAdvSearch(parentStyleORIN, orinNumber, deptId, supplierId, productName, entryType, primaryUPC, classId, vendorName, vendorStyle, imageState, contentState, petStatus, completionDate, pet, omniChannelIndicator, req_Type, colorDesc);  
                    
                    
                    petList.add(pet);
                }
                petList = petStatusMapping(petList);
                Collections.sort(petList);
                
          } 
            LOGGER.info("End of try873...");
            LOGGER.info("End of try874.." +rows.size()+"==========" +petList.size());
      }catch(Exception e){
          LOGGER.info("inside catch dao in search");
          e.printStackTrace(System.out);        
     }
      finally
      {
          LOGGER.info("recordsFetched. getAdvWorkListDisplayData finally block.." );
          session.flush();   
          tx.commit();
          session.close();
         
      }

            LOGGER.info("This is from getAdvWorkListDisplayData..End" );
            return petList;

        
   
}

/**
 * For complex pack Adv search 
 * @param advanceSearch
 * @return
 * @throws SQLException
 * @throws ParseException
 */
private List<PetsFound> getAdvWorkListDisplayDataComplexPack(AdvanceSearch advanceSearch)throws SQLException, ParseException  {
    
    LOGGER.info("This is from getAdvWorkListDisplayDataComplexPack..Start" );
    List<PetsFound> petList = new ArrayList<PetsFound>();
    PetsFound pet=null;
    XqueryConstants xqueryConstants= new XqueryConstants();
    
    
        Session session = null;
        Transaction tx =  null;
        
      try{
        session = sessionFactory.openSession();
        tx = session.beginTransaction();      
       //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
        Query query = session.createSQLQuery(xqueryConstants.getAdvWorkListDisplayDataComplexPack(advanceSearch));  
        LOGGER.info("Query..getAdvWorkListDisplayDataComplexPack**" + query);
        // execute delete SQL statement
        List<Object[]> rows = query.list();
        if (rows != null) {
            LOGGER.info("recordsFetched..." + rows);
            for(Object[] row : rows){   
                String parentStyleORIN = row[0]!=null?row[0].toString():null;
                
                String orinNumber=row[1]!=null?row[1].toString():null;
                String deptId= row[2]!=null?row[2].toString():null;
                
                String supplierId=row[3]!=null?row[3].toString():null;
                String productName=row[4]!=null?row[4].toString():null;
                String entryType=row[5]!=null?row[5].toString():null;
                String primaryUPC = row[6]!=null?row[6].toString():null;
                String vendorName=row[7]!=null?row[7].toString():null;
                String vendorStyle=row[8]!=null?row[8].toString():null;
                String classId = row[9]!=null?row[9].toString():null;
                String petStatus=row[10]!=null?row[10].toString():null;
                String imageState=row[11]!=null?row[11].toString():null;
                String contentState=row[12]!=null?row[12].toString():null;
                String completionDate=row[13]!=null?row[13].toString():null;  
                String omniChannelIndicator=row[14]!=null?row[14].toString():null;
                String req_Type=row[15]!=null?row[15].toString():null;
                String colorDesc=row[16]!=null?row[16].toString():null;

                LOGGER.info("parentStyleORIN" + "\t" + parentStyleORIN);
                LOGGER.info("orinNumber" + "\t" + orinNumber);
                LOGGER.info("deptId" + "\t" + deptId);
                LOGGER.info("productName" + "\t" + productName);
                LOGGER.info("entryType" + "\t" + entryType);
                LOGGER.info("vendorName" + "\t" + vendorName);
                LOGGER.info("vendorStyle" + "\t" + vendorStyle);
                LOGGER.info("imageState" + "\t" + imageState);
                LOGGER.info("contentState" + "\t" + contentState);
                LOGGER.info("petStatus" + "\t" + petStatus);
                LOGGER.info("completionDate" + "\t" + completionDate);
                LOGGER.info("omni channel indicator" + "\t" + omniChannelIndicator);
                petList.add(pet);
            }
            petList = petStatusMapping(petList);
            Collections.sort(petList);
            
      } 
  }catch(Exception e){
      LOGGER.info("inside catch dao in search in adv complex pack");
      e.printStackTrace(System.out);        
 }
  finally
  {
      LOGGER.info("recordsFetched. getAdvWorkListDisplayDataComplexPack finally block.." );
      session.flush();   
      tx.commit();
      session.close();
     
  }

        LOGGER.info("This is from getAdvWorkListDisplayData..End" );
        return petList;

    

}

private PetsFound mapAdseDbPetsToPortalAdvSearch(String parentStyleORIN,
    String orinNumber, String deptId, String supplierId,String productName, String entryType,String primaryUPC,String classId,
    String vendorName, String vendorStyle, String imageState,
    String contentState, String petStatus, String completionDate, PetsFound pet, 
    String omniChannelIndicator,String req_Type, String colorCode, String earliestCompletionDate, String existsInGroup, String cFAS) {
    pet = new PetsFound();
    pet.setParentStyleOrin(parentStyleORIN);
    pet.setOrinNumber(orinNumber);
    pet.setDeptId(deptId);
    pet.setProductName(productName); 
    pet.setEntryType(entryType);
    pet.setVendorName(vendorName);
    pet.setVendorStyle(vendorStyle);
    pet.setImageState(imageState);
    pet.setContentState(contentState);
    
    pet.setSupplierId(supplierId);
    pet.setPrimaryUPC(primaryUPC);
    pet.setReq_Type(req_Type);
    pet.setClassId(classId);
    pet.setExistsInGroup(existsInGroup);
    pet.setCFAS(cFAS);
    
    pet.setPetStatus(petStatus);
    
    if(null != entryType && (("Style").equals(entryType) || ("Complex Pack").equals(entryType))){
        pet.setCompletionDate(earliestCompletionDate);
    }else{
        pet.setCompletionDate(completionDate);
    }
    
    pet.setOmniChannelVendor(omniChannelIndicator);
    
    pet.setColorDesc(colorCode);
    pet.setEarliestComplitionDate(earliestCompletionDate);
    
    return pet; 
    
    
    }

    /**
     * Gets the formated pet details.
     *
     * @param petList the pet list
     * @return the formated pet details
     */
    private List<PetsFound> validatePetDetails(Map<String,List<PetsFound>> petList, List<PetsFound> originalPetList, boolean vendorLoginFlag) {
        LOGGER.info("This is from validatePetDetails...");
        LOGGER.info("Vendor Login Flag-->"+vendorLoginFlag);
        
        List<PetsFound> modifiedPetList = new ArrayList<PetsFound>();
        if(vendorLoginFlag){                         
            for(Map.Entry<String,List<PetsFound>> pet:petList.entrySet()) 
            {
                LOGGER.info("Default Orin No-->"+pet.getKey());
                List<PetsFound> petMapList = pet.getValue();
                boolean flag = false;
                boolean parentState = false;
                String imageState = "";
                String contentState = "";
                for(PetsFound petObject:petMapList)
                {
                    imageState = petObject.getImageState();
                    contentState = petObject.getContentState();
                    LOGGER.info("Default Orin No-->"+petObject.getOrinNumber()+"--"+imageState+"--"+contentState+"--"+petObject.getEntryType()+"--");
                    if(petObject.getEntryType().equalsIgnoreCase("Style") 
                            || petObject.getEntryType().equalsIgnoreCase("Complex Pack")
                            || petObject.getEntryType().equalsIgnoreCase("ComplexPack"))
                    {
                        if(null != imageState
                                && (imageState.equalsIgnoreCase("Ready_For_Review")
                                        || imageState.equalsIgnoreCase("Completed"))
                                && null != contentState
                                && (contentState.equalsIgnoreCase("Ready_For_Review")
                                        || contentState.equalsIgnoreCase("Completed")))
                        {
                            parentState = true;
                        } 
                    }
                    else{
                        if(parentState)
                        {
                            if(null != imageState && null != contentState
                                    && ((!imageState.equalsIgnoreCase("Ready_For_Review")
                                            && !imageState.equalsIgnoreCase("Completed"))                                
                                    || (!contentState.equalsIgnoreCase("Ready_For_Review")
                                            && !contentState.equalsIgnoreCase("Completed"))))
                            {
                                flag = true;
                            } 
                        }
                                                               
                    }
                }
                LOGGER.info("parentState-->"+parentState+"-flag-"+flag);
                if((parentState && flag)
                        || (!parentState && !flag)){
                    for(PetsFound petObject:petMapList)                {
                        if(petObject.getEntryType().equalsIgnoreCase("Style") 
                                || petObject.getEntryType().equalsIgnoreCase("Complex Pack")
                                || petObject.getEntryType().equalsIgnoreCase("ComplexPack"))
                        {
                            modifiedPetList.add(petObject);                        
                        }
                        else{
                            if((!petObject.getImageState().equalsIgnoreCase("Ready_For_Review")
                                    && !petObject.getImageState().equalsIgnoreCase("Completed"))
                                    || (!petObject.getContentState().equalsIgnoreCase("Ready_For_Review")
                                            && !petObject.getContentState().equalsIgnoreCase("Completed")))
                            {
                                modifiedPetList.add(petObject);
                            }
                        }
                    }
                }            
            }
        }
        else{
            modifiedPetList = originalPetList;
        }
        return modifiedPetList;
    }
    
    /**
     * Gets the ordered pet details.
     *
     * @param petList the pet list
     * @return the formated pet details
     */
    private List<PetsFound> orderPetDetailsList(List<PetsFound> petList) {
        LOGGER.info("This is from orderPetDetailsList...LIST SIZE-->"+petList.size());
        
        List<PetsFoundHierarchy> modifiedPetList = new ArrayList<PetsFoundHierarchy>();
        List<PetsFound> childList = new ArrayList<PetsFound>();
        List<PetsFound> sortedListMaster = new ArrayList<PetsFound>();
        PetsFoundHierarchy pet = new PetsFoundHierarchy();
        Date parentDate = null;
        Date childDate = null;
        //Populate new list to get it in required order
        try{
            for(PetsFound petObject:petList)
            {
                String entryType = (null==petObject.getEntryType()?"":petObject.getEntryType().trim());
                if(entryType.equalsIgnoreCase("Style") 
                        || entryType.equalsIgnoreCase("Complex Pack")
                        || entryType.equalsIgnoreCase("ComplexPack"))
                {                    
                    pet = new PetsFoundHierarchy();
                    //LOGGER.info("BEFORE DATE CONVERT-->"+petObject.getCompletionDate());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String completionDate = "";
                    pet.setParent(petObject);
                    pet.setParentStyleOrin(petObject.getOrinNumber());
                    if(null != petObject.getEarliestComplitionDate()
                            && (!petObject.getEarliestComplitionDate().equals("")))
                    {                        
                            completionDate = petObject.getEarliestComplitionDate();
                            parentDate = formatter.parse(completionDate);
                            pet.setCompletionDate(parentDate);  
                    }                    
                    else
                    {
                        parentDate = new Date(Long.MAX_VALUE);
                        pet.setCompletionDate(new Date(Long.MAX_VALUE));
                    }
                    if(null != petObject.getDeptId()
                            && (!petObject.getDeptId().equals("")))
                    {
                        pet.setDeptId(Integer.parseInt(petObject.getDeptId()));
                    }
                    else
                    {
                        pet.setDeptId(9999);
                    }
                    if(null != petObject.getReq_Type()
                            && (!petObject.getReq_Type().equals("")))
                    {
                        if(petObject.getReq_Type().equalsIgnoreCase("PO"))
                        {
                            pet.setReq_Type("1");
                        }
                        else if(petObject.getReq_Type().equalsIgnoreCase("DIRECTFLAG")
                                || petObject.getReq_Type().equalsIgnoreCase("DROPSHIP"))
                        {
                            pet.setReq_Type("2");
                        }
                        else if(petObject.getReq_Type().equalsIgnoreCase("Sold-Online")
                                || petObject.getReq_Type().equalsIgnoreCase("SOLDONLINE"))
                        {
                            pet.setReq_Type("3");
                        }
                        else if(petObject.getReq_Type().equalsIgnoreCase("PEP"))
                        {
                            pet.setReq_Type("4");
                        }
                        else if(petObject.getReq_Type().equalsIgnoreCase("PO:C"))
                        {
                            pet.setReq_Type("5");
                        }
                        else if(petObject.getReq_Type().equalsIgnoreCase("DIRECTFLAG:C")
                                || petObject.getReq_Type().equalsIgnoreCase("DROPSHIP:C"))
                        {
                            pet.setReq_Type("6");
                        }
                        else if(petObject.getReq_Type().equalsIgnoreCase("Sold-Online:C")
                                || petObject.getReq_Type().equalsIgnoreCase("SOLDONLINE:C"))
                        {
                            pet.setReq_Type("7");
                        }
                        else if(petObject.getReq_Type().equalsIgnoreCase("PEP:C"))
                        {
                            pet.setReq_Type("8");
                        }
                        else
                        {
                            pet.setReq_Type("9");
                        }
                    } 
                    else
                    {
                        pet.setReq_Type("10");
                    }
                    if(null != pet.getParentStyleOrin() 
                            && (!pet.getParentStyleOrin().equals("")))
                    {
                        modifiedPetList.add(pet);
                    }
                }                
            }             
            LOGGER.info("NEW OBJECT LIST SIZE-->"+modifiedPetList.size());
            //DO required changes for ordering
            Collections.sort(modifiedPetList);
            //Modification End
            LOGGER.info("SORTED NEW OBJECT LIST SIZE-->"+modifiedPetList.size());
            //Populate old list again with ordered list
            for(PetsFoundHierarchy orderedObject:modifiedPetList)
            {
                sortedListMaster.add(orderedObject.getParent());                
            }                      
            
        }catch(ParseException exception){
            LOGGER.info("Parse Exception in orderPetDetailsList method");
            exception.printStackTrace();
        }
        catch(Exception exception){
            LOGGER.info("Exception in orderPetDetailsList method");
            exception.printStackTrace();
        }
        return sortedListMaster;
    }
    
    public ArrayList isPETLocked(String Orin,String pepUserId,String searchPetLockedtype) throws PEPPersistencyException 
    {
      
      
        LOGGER.info("pepUserId in the isPETLocked layer"+pepUserId);
        
        LOGGER.info("pepUserId in the Orin layer"+Orin);
        LOGGER.info("pepUserId in the searchPetLockedtype layer"+searchPetLockedtype);
         Session session = null;
         Transaction tx = null;
         ArrayList petLockDetails = new ArrayList();
         try{
           session = sessionFactory.openSession();
           tx = session.beginTransaction();
           Query query = session.getNamedQuery("PetLock.isPetLocked");
           query.setString("petId", Orin);
           query.setString("pepFunction", searchPetLockedtype); 
                  List<PetLock> petLock = query.list();
                  if(null!=petLock && petLock.size()>0){
                      LOGGER.info("isPETLocked  in DAO IMPL  "+petLock.size());
                      for(int i=0;i<petLock.size();i++){                            
                          petLockDetails.add(petLock.get(i));  
                      }
                  }  
                  
            
         }catch(Exception e) {
             e.printStackTrace();
         }
         finally{
             session.flush();
             tx.commit();
             session.close(); 
         }
           
        return petLockDetails;
    }

    /**
     * This method will update the Department table
     */
       public boolean lockPET(  String orin, String pepUserID, String pepfunction)
           throws PEPPersistencyException {
           LOGGER.info("This is  in DAO IMPL lockRecord.."+pepUserID);
           boolean isUpdated =false;
           Session session = null;
           Transaction tx = null;  
           PetLock ptLock = new PetLock();
           PetLockPK petLock =  new PetLockPK();   
           try{
              session = sessionFactory.openSession();
              tx = session.beginTransaction(); 
              petLock.setPetId(orin);
              petLock.setPepUser(pepUserID);
              petLock.setLockDate(new Date());
              ptLock.setPepFunction(pepfunction);
              ptLock.setLockStatus("Yes");
              ptLock.setId(petLock);          
              LOGGER.info("petLock -->"+petLock);
              session.saveOrUpdate(ptLock);            
           }
           finally{
               session.flush();
               tx.commit();
               session.close();
           }
           LOGGER.info("This is from lockPET...Exit");
           return isUpdated;
       }
       
       /**
        * Gets the pet details in MAP.
        *
        * @param petList the pet list
        * @return the formated pet details
        */
       private Map<String,List<PetsFound>> mapPetDetailsList(List<PetsFound> petList) {
           LOGGER.info("This is from mapPetDetailsList...LIST SIZE-->"+petList.size());
           List<PetsFound> petListNew = new ArrayList<PetsFound>();
           String parent = "";
           Map<String,List<PetsFound>> petMap = new HashMap<String,List<PetsFound>>();
           for(PetsFound pet: petList){
               LOGGER.info(parent + "-----"+pet.getOrinNumber());           
               if(parent.equals(pet.getParentStyleOrin()))
               {
                   petListNew.add(pet);
               
               }
               else if(!parent.equals("") && !parent.equals(pet.getParentStyleOrin()))
               {
                   petMap.put(parent, petListNew);
                   parent = pet.getParentStyleOrin();
                   petListNew = new ArrayList<PetsFound>();
                   petListNew.add(pet);
                   
               }
               else{                    
                   parent = pet.getParentStyleOrin();
                   petListNew = new ArrayList<PetsFound>();
                   petListNew.add(pet);
               } 
                                                    
           }
           if(petListNew.size() > 0){
               petMap.put(parent, petListNew);    
       }
   
        return petMap;       
   }
       
       
       /**
        * Gets the pet status mapping.
        *
        * @param petList the pet list
        * @return the formated pet details
     * @throws IOException / AFUPYB3
        */
       private List<PetsFound> petStatusMapping(List<PetsFound> petList) throws IOException {
           LOGGER.info("This is from petStatusMapping...LIST SIZE-->"+petList.size());
           List<PetsFound> petListNew = new ArrayList<PetsFound>();           
           String imageStatus = "";
           String contentStatus = "";
           String petStatus = "";
           Properties prop= PropertiesFileLoader.getExternalLoginProperties();
           for(PetsFound pet: petList){
               imageStatus = prop.getProperty("Image"+pet.getImageState());
               contentStatus = prop.getProperty("Content"+pet.getContentState());
               petStatus = prop.getProperty(pet.getPetStatus());
               pet.setImageState(imageStatus);
               pet.setContentState(contentStatus);
               pet.setPetStatus(petStatus);
               petListNew.add(pet);
           }
        return petListNew;
   } 
       
       /**
        * Gets the work list display data for Parent ORIN.
        *
        * @return the work list display data
        * @throws SQLException 
        * @throws ParseException 
        */
       public List<PetsFound> getWorkListDisplayDataForParent(String depts,String email,String pepId,String supplierId, boolean vendorLogin) throws SQLException, ParseException {
           LOGGER.info("This is from getWorkListDisplayDataForParent..Start" );
           LOGGER.info("depts:"+depts+",email:"+email+", pepId:"+pepId+", supplierId:"+supplierId+", vendorLogin:"+vendorLogin);
           List<PetsFound> petList = new ArrayList<PetsFound>();                                 
           PetsFound pet=null;
           XqueryConstants xqueryConstants= new XqueryConstants();
             Session session = null;
             Transaction tx =  null;
             
             try{
               session = sessionFactory.openSession();
               tx = session.beginTransaction();      
              //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
               //Query query = session.createSQLQuery(xqueryConstants.getWorkListDisplayDataParent(depts, email, pepId, supplierId, vendorLogin));
               /**Newly added for DE795, 796 START**/
               //Query query = session.createSQLQuery(xqueryConstants.getWorkListDisplayDataParent(vendorLogin));
               Query query = session.createSQLQuery(xqueryConstants.getWorkListDisplayDataParent(depts, email, pepId, supplierId, vendorLogin));

              // query.setParameter("depts", depts); 
              // query.setParameter("email", email);
               //query.setParameter("pepId", pepId);
              // query.setParameter("supplierId", supplierId);
               query.setFetchSize(100);
               /******* END**/
               
               LOGGER.info("Query..getWorkListDisplayDataForParent-->" + query);
               // execute delete SQL statement
               List<Object[]> rows = query.list();
               if (rows != null) {
                   LOGGER.info("recordsFetched..." + rows.size());
                   for(Object[] row : rows){                         
                       String parentStyleORIN = row[0]!=null?row[0].toString():null;
                       
                       String orinNumber=row[1]!=null?row[1].toString():null;
                       String deptId= row[2]!=null?row[2].toString():null;
                       String supplierId_db = row[3]!=null?row[3].toString():null;
                                                                   
                       String productNameStyle=row[4]!=null?row[4].toString():null;
                       String entryType=row[5]!=null?row[5].toString():null;
                       
                       String primaryUPC=row[6]!=null?row[6].toString():null;                       
                       
                       String vendorName=row[7]!=null?row[7].toString():null;
                       String vendorStyle=row[8]!=null?row[8].toString():null;
                       String petStatus=row[9]!=null?row[9].toString():null;
                       
                       String imageState=row[10]!=null?row[10].toString():null;
                       String contentState=row[11]!=null?row[11].toString():null;
                       String completionDate=row[12]!=null?row[12].toString():null;  
                       String omniChannelIndicator=row[13]!=null?row[13].toString():null;
                       String sourceSystem=row[14]!=null?row[14].toString():null;
                       
                       String petStyleState=row[15]!=null?row[15].toString():null;
                       String petImageState=row[16]!=null?row[16].toString():null;
                       String petContentState=row[17]!=null?row[17].toString():null;
                       String earliestCompletionDate=row[18]!=null?row[18].toString():null;
                       String productNameComplex=row[19]!=null?row[19].toString():null;
                       
                       String existsInGroup = row[20]!=null?row[20].toString():"";
                       String cfas = row[21]!=null?row[21].toString():"";
                       String conversionFlag =row[22]!=null?row[22].toString():"";
                       if(conversionFlag.equalsIgnoreCase("true"))
                       {
                           sourceSystem = sourceSystem + ":C";
                       }
                        //  "orin.PRODUCT_NAME_COMPLEX " +
                       String productName = (productNameStyle != null) ? productNameStyle : productNameComplex;
                       
                       pet  = mapAdseDbPetsToPortal(parentStyleORIN,orinNumber,deptId,productName,
                           entryType,vendorName,vendorStyle,imageState,contentState,completionDate,
                           pet,omniChannelIndicator, primaryUPC, "", petStatus, sourceSystem,
                           petStyleState, petImageState, petContentState, earliestCompletionDate, existsInGroup, cfas);                                    
                       petList.add(pet);
                   }
                   petList = petStatusMapping(petList);
                   Collections.sort(petList);
             } 
         }catch(Exception e){
             e.printStackTrace();
        }
         finally
         {
             LOGGER.info("recordsFetched. getWorkListDisplayDataForParent finally block.." );
             session.flush();
             tx.commit();
             session.close();
            
         }
           LOGGER.info("This is from getWorkListDisplayDataForParent..End" );
           return petList;
       }  
       
       /**
        * Gets the work list display data for Child ORIN.
        *
        * @return the work list display data
        * @throws SQLException 
        * @throws ParseException 
        */
       public List<PetsFound> getWorkListDisplayDataForChild(String parentOrin, boolean vendorLogin) throws SQLException, ParseException {
           LOGGER.info("This is from getWorkListDisplayDataForChild..Start" );
           LOGGER.info("parentOrin:"+parentOrin);
           List<PetsFound> petList = new ArrayList<PetsFound>();                                 
           PetsFound pet=null;
           XqueryConstants xqueryConstants= new XqueryConstants();
             Session session = null;
             Transaction tx =  null;
             
             try{
               session = sessionFactory.openSession();
               tx = session.beginTransaction();      
              //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
               //Query query = session.createSQLQuery(xqueryConstants.getWorkListDisplayDataChild(parentOrin, vendorLogin));
               /****Newly added for DE795,796 ******/
               Query query = session.createSQLQuery(xqueryConstants.getWorkListDisplayDataChild(vendorLogin));
               query.setParameter("parentOrin", parentOrin);
               query.setFetchSize(100);
               /****END ******/
               
             
               LOGGER.info("Query..getWorkListDisplayDataForChild-->" + query);
               // execute delete SQL statement
               List<Object[]> rows = query.list();
               if (rows != null) {
                   LOGGER.info("recordsFetched..." + rows.size());
                   for(Object[] row : rows){                         
                       String parentStyleORIN = row[0]!=null?row[0].toString():null;
                       
                       String orinNumber=row[1]!=null?row[1].toString():null;
                       String deptId= row[2]!=null?row[2].toString():null;
                       String supplierId_db = row[3]!=null?row[3].toString():null;
                                                                   
                       String productNameStyleColor=row[4]!=null?row[4].toString():null;
                       String entryType=row[5]!=null?row[5].toString():null;
                       
                       String primaryUPC=row[6]!=null?row[6].toString():null;
                       String colorDescStyleColor=row[7]!=null?row[7].toString():null;
                       
                       String vendorName=row[8]!=null?row[8].toString():null;
                       String vendorStyle=row[9]!=null?row[9].toString():null;
                       String petStatus=row[10]!=null?row[10].toString():null;
                       
                       String imageState=row[11]!=null?row[11].toString():null;
                       String contentState=row[12]!=null?row[12].toString():null;
                       String completionDate=row[13]!=null?row[13].toString():null;  
                       String omniChannelIndicator=row[14]!=null?row[14].toString():null;
                       String sourceSystem=row[15]!=null?row[15].toString():null;
                       String colorDescPackColor=row[16]!=null?row[16].toString():null;
                       String productNamePackColor=row[17]!=null?row[17].toString():null;
                       String conversionFlag =row[18]!=null?row[18].toString():"";
                       if(conversionFlag.equalsIgnoreCase("true"))
                       {
                           sourceSystem = sourceSystem + ":C";
                       }

                       String colorDesc = (colorDescStyleColor != null) ? colorDescStyleColor : colorDescPackColor;
                       String productName = (productNameStyleColor != null) ? productNameStyleColor : productNamePackColor;
                       
                       
                       pet  = mapAdseDbPetsToPortal(parentStyleORIN,orinNumber,deptId,productName,
                           entryType,vendorName,vendorStyle,imageState,contentState,completionDate,
                           pet,omniChannelIndicator, primaryUPC,colorDesc, petStatus, sourceSystem,
                           "", "", "", "", "", "");                                    
                       petList.add(pet);
                   }
                   petList = petStatusMapping(petList);
                   //Collections.sort(petList);
             } 
         }catch(Exception e){
             e.printStackTrace();
        }
         finally
         {
             LOGGER.info("recordsFetched. getWorkListDisplayDataForParent finally block.." );
             session.flush();
             tx.commit();
             session.close();
            
         }
           LOGGER.info("This is from getWorkListDisplayDataForParent..End" );
           return petList;
       } 

       /**
        * This method will return the PET details from the ADSE on base of department for Parent
        * @param departmentNumbers
        * @return
        * @throws PEPServiceException
        * @throws PEPPersistencyException
        */
       
       public List<WorkFlow> getPetDetailsByDepNosForParent(ArrayList departmentNumbers,String email,List<String> supplierIdList)
           throws PEPPersistencyException {
           
           ArrayList pepDetails = null;
           List<WorkFlow> workFlowList = null;
           
           try {
                   LOGGER.info("getPetDetailsByDepNosForParent....Enter");
                   System.out.println("\n--->> departmentNumbers " + departmentNumbers);
                   System.out.println("\n--->> email " + email);
                   StringBuffer depNumbers= new StringBuffer();
                   
                   if(departmentNumbers!=null) {
                       for(int i=0;i<departmentNumbers.size();i++){
                           if(i==0){
                           depNumbers.append(((DepartmentDetails)departmentNumbers.get(i)).getId());
                           }else{
                               if(i<departmentNumbers.size()){
                                   //Fix for the defect 20
                               depNumbers.append(","+((DepartmentDetails)departmentNumbers.get(i)).getId());
                               }else{
                                   depNumbers.append(((DepartmentDetails)departmentNumbers.get(i)).getId());
                               }
                           }
                       }
                   }
                   LOGGER.info("The Department numbers...."+depNumbers.toString());
                   try {
                       //multi supplier id changes start
                       String vendorId = null;
                       if(null!= supplierIdList && supplierIdList.size()>0){
                          for (int i=0;i<supplierIdList.size();i++){
                            if(i==0){  
                                vendorId = supplierIdList.get(i);
                            }else{
                                vendorId = vendorId+","+supplierIdList.get(i); 
                            }
                          }
                       }                       
                       boolean vendorLoginFlag = false;
                       if(null != email)
                       {
                           vendorLoginFlag = true;
                       }
                       LOGGER.info("Vendor id===="+vendorId);
                       
                       
                       
                       List<PetsFound> petList = getWorkListDisplayDataForParent(depNumbers.toString(),email,null,vendorId,vendorLoginFlag);   // TODO 
                       
                       //Get it ordered
                       petList = orderPetDetailsList(petList);
                       
                       WorkListDisplay workListDisplay = getFormatedPetDetailsForParent(petList, vendorLoginFlag);
                       workFlowList = workListDisplay.getWorkListDisplay();
                   }
                   catch (SQLException e) {
                       e.printStackTrace();
                      throw new PEPPersistencyException("SQL Exception cought from getPetDetailsByDepNosForParent");
                      
                   }
                   catch (ParseException e) {
                     
                       e.printStackTrace();
                       throw new PEPPersistencyException("Parse Exception cought from getPetDetailsByDepNosForParent");
                   }
                   
           } catch(Exception e)    {
               e.printStackTrace();
           }
           return workFlowList;
       }
       
       /**
        * This method will return the PET details from the ADSE on base of department for Child
        * @param parentOrin
        * @return
        * @throws PEPServiceException
        * @throws PEPPersistencyException
        */
       
       public List<StyleColor> getPetDetailsByDepNosForChild(String email,String parentOrin)
           throws PEPPersistencyException {
                      
           List<StyleColor> styleColorList = new ArrayList<StyleColor>();
           
           try {
                   LOGGER.info("getPetDetailsByDepNosForChild....Enter");
                   System.out.println("\n--->> parentOrin " + parentOrin);
                   System.out.println("\n--->> email " + email);                  
                   
                   boolean vendorLoginFlag = false;
                   if(null != email)
                   {
                       vendorLoginFlag = true;
                   }   
                       
                   List<PetsFound> petList = getWorkListDisplayDataForChild(parentOrin,vendorLoginFlag);    // TODO
                       
                   styleColorList = getFormatedPetDetailsForChild(petList);
               }
               catch (SQLException e) {
                   e.printStackTrace();
                   throw new PEPPersistencyException("SQL Exception cought from getPetDetailsByDepNosForChild");
                  
               }
               catch(Exception e) {
                   e.printStackTrace();
                   throw new PEPPersistencyException("Exception cought from getPetDetailsByDepNosForChild");
               }
           return styleColorList;
       }
       
       /**
        * Gets the formated pet details for Header.
        *
        * @param petList the pet list
        * @return the formated pet details
        */
       private WorkListDisplay getFormatedPetDetailsForParent(List<PetsFound> petList, boolean vendorLoginFlag) {
           LOGGER.info("This is from getFormatedPetDetailsForParent...");           
           String  parentOrinNumber = null;           
           List<WorkFlow> styleList = new ArrayList<WorkFlow>();
           List<WorkFlow> styleListForWorkListDisplay = new ArrayList<WorkFlow>();
           WorkListDisplay workListDisplay= new WorkListDisplay();                      
           
           for(PetsFound pet:petList)
           {               
               String entryType=pet.getEntryType();               
               if(("Style".equalsIgnoreCase(entryType) || "Complex Pack".equalsIgnoreCase(entryType)) )//Complex Pack changes
               {                    
                
                   String orinNumber= pet.getOrinNumber();
                   String vendorStyle= pet.getVendorStyle();
                   parentOrinNumber=pet.getParentStyleOrin();
                   String vendorName=pet.getVendorName();
                   String productName=pet.getProductName();
                   String petStatus = pet.getPetStatus();
                   String completionDate = "";
                   if(null != pet.getCompletionDate()
                           && (!pet.getCompletionDate().equals("")))
                   {                        
                           completionDate = pet.getCompletionDate().substring(0,10);                          
                   }  
                   String contentState=pet.getContentState();
                   String imageState=pet.getImageState();                   
                   String deptId=pet.getDeptId();
                   String ominInd=pet.getOmniChannelVendor();
                   
                   String petStyleState=pet.getPetStyleState();
                   String petImageState=pet.getPetImageState();
                   String petContentState=pet.getPetContentState();
                   String earliestCompletionDate=pet.getEarliestComplitionDate();
                   String existsInGroup=pet.getExistsInGroup();
                   String cfas=pet.getCFAS();
                   
                   WorkFlow style = new WorkFlow();
                   style.setEntryType(entryType);
                   style.setOrinNumber(orinNumber);
                   style.setParentStyleOrinNumber(parentOrinNumber);
                   style.setVendorName(vendorName);
                   
                   style.setCompletionDate((null == completionDate)? earliestCompletionDate:completionDate);
                   style.setDeptId(deptId);
                   style.setImageStatus(imageState);
                   style.setContentStatus(contentState);
                   style.setProductName(productName);
                   style.setPetStatus(petStatus);
                   style.setVendorStyle(vendorStyle);                          
                   style.setOmniChannelVendor(ominInd); 
                   
                   style.setPetStyleState(petStyleState);
                   style.setPetImageState(petImageState);
                   style.setPetContentState(petContentState);
                   style.setEarliestComplitionDate(earliestCompletionDate);
                   
                   style.setExistsInGroup(existsInGroup);
                   style.setCFAS(cfas);
                   
                   petStyleState = (null == petStyleState ? "" : petStyleState); // TODO
                   petImageState = (null == petImageState ? "" : petImageState);
                   petContentState = (null == petContentState ? "" : petContentState);
                   if(vendorLoginFlag){
                       if(("Y").equalsIgnoreCase(petStyleState) 
                               && (("Y").equalsIgnoreCase(petImageState) || ("Y").equalsIgnoreCase(petContentState))){
                           style.setIsChildPresent("Y");
                       }
                   }else{
                       if(("Y").equalsIgnoreCase(petStyleState)){
                           style.setIsChildPresent("Y");
                       }
                   }
                   
                   /**
                    * Modified for Defect# 927
                    * Date: 03/09/2016
                    * Modified by: AFUAXK4
                    */
                   String sourceType = pet.getReq_Type();
                   //LOGGER.info("sourceType ="+sourceType);
                   style.setSourceType(sourceType);
                  
                   /**
                    * Modification end
                    */

                   styleListForWorkListDisplay.add(style);
               }
           }
           LOGGER.info("styleList length="+styleList.size());
           //Add all the Styles to the work list display form         
           workListDisplay.setWorkListDisplay(styleListForWorkListDisplay);
           LOGGER.info("worklistdisplay size.."+workListDisplay.getWorkListDisplay().size());
           return workListDisplay;
       }
       

       /**
        * Gets the formated pet details for SearchPet.
        *
        * @param petList the pet list
        * @return the formated pet details
        */
       private WorkListDisplay getFormatedPetDetailsForSearchPetParent(List<PetsFound> petList, boolean vendorLoginFlag) {
           LOGGER.info("This is from getFormatedPetDetailsForSearchPetParent...");           
           String  parentOrinNumber = null;           
           List<WorkFlow> styleList = new ArrayList<WorkFlow>();
           List<WorkFlow> styleListForWorkListDisplay = new ArrayList<WorkFlow>();
           WorkListDisplay workListDisplay= new WorkListDisplay();                      

           WorkFlow style = null;
           String parentStyleOrin = "";
           for(PetsFound pet:petList)
           {               
               String entryType=pet.getEntryType();               
               if(("Style".equalsIgnoreCase(entryType) || "Complex Pack".equalsIgnoreCase(entryType)) )//Complex Pack changes
               {             
                   if(null != style){
                       styleListForWorkListDisplay.add(style);
                   }
                   parentStyleOrin = (null == pet.getParentStyleOrin() ? "" : pet.getParentStyleOrin());
                   String orinNumber= pet.getOrinNumber();
                   String vendorStyle= pet.getVendorStyle();
                   parentOrinNumber=pet.getParentStyleOrin();
                   String vendorName=pet.getVendorName();
                   String productName=pet.getProductName();
                   String petStatus = pet.getPetStatus();
                   String completionDate = "";
                   if(null != pet.getCompletionDate()
                           && (!pet.getCompletionDate().equals("")))
                   {                        
                           completionDate = pet.getCompletionDate().substring(0,10);                          
                   }  
                   String contentState=pet.getContentState();
                   String imageState=pet.getImageState();                   
                   String deptId=pet.getDeptId();
                   String ominInd=pet.getOmniChannelVendor();
                   
                   String petStyleState=pet.getPetStyleState();
                   String petImageState=pet.getPetImageState();
                   String petContentState=pet.getPetContentState();
                   String earliestCompletionDate=pet.getEarliestComplitionDate();
                   String existsInGroup=pet.getExistsInGroup();
                   String cfas=pet.getCFAS();
                   
                   style = new WorkFlow();
                   style.setEntryType(entryType);
                   style.setOrinNumber(orinNumber);
                   style.setParentStyleOrinNumber(parentOrinNumber);
                   style.setVendorName(vendorName);
                   
                   style.setCompletionDate((null == completionDate)? earliestCompletionDate:completionDate);
                   style.setDeptId(deptId);
                   style.setImageStatus(imageState);
                   style.setContentStatus(contentState);
                   style.setProductName(productName);
                   style.setPetStatus(petStatus);
                   style.setVendorStyle(vendorStyle);                          
                   style.setOmniChannelVendor(ominInd); 
                   
                   style.setPetStyleState(petStyleState);
                   style.setPetImageState(petImageState);
                   style.setPetContentState(petContentState);
                   style.setEarliestComplitionDate(earliestCompletionDate);
                   
                   petStyleState = (null == petStyleState ? "" : petStyleState); // TODO
                   petImageState = (null == petImageState ? "" : petImageState);
                   petContentState = (null == petContentState ? "" : petContentState);
                   
                   style.setIsChildPresent("N");
                   //LOGGER.info("existsInGroup " + existsInGroup);
                   style.setExistsInGroup(existsInGroup);
                   style.setCFAS(cfas);
                   
                   /**
                    * Modified for Defect# 927
                    * Date: 03/09/2016
                    * Modified by: AFUAXK4
                    */
                   String sourceType = pet.getReq_Type();
                   //LOGGER.info("sourceType ="+sourceType);
                   style.setSourceType(sourceType);
                  
                   /**
                    * Modification end
                    */

//                   styleListForWorkListDisplay.add(style);
               }else{
                   String parentStyleOrinChild = (null == pet.getParentStyleOrin() ? "" : pet.getParentStyleOrin());
                   if(parentStyleOrin.equals(parentStyleOrinChild)){
                       style.setIsChildPresent("Y");
                   }
               }
           }
           if(null != style){
               styleListForWorkListDisplay.add(style);
           }
           
           LOGGER.info("styleList length="+styleList.size());
           //Add all the Styles to the work list display form         
           workListDisplay.setWorkListDisplay(styleListForWorkListDisplay);
           LOGGER.info("worklistdisplay size.."+workListDisplay.getWorkListDisplay().size());
           return workListDisplay;
       }
       
       
       /**
        * Gets the formated pet details.
        *
        * @param petList the pet list
        * @return the formated pet details
        */
       private List<StyleColor> getFormatedPetDetailsForChild(List<PetsFound> petList) {
           LOGGER.info("This is from getFormatedPetDetailsForChild...");                                
           List<StyleColor> styleColorList = new ArrayList<StyleColor>();                                      
           
           for(PetsFound pet:petList)
           {              
               String childsParentOrinNumber = "";
               String entryType=pet.getEntryType();               
               if(("StyleColor".equalsIgnoreCase(entryType) || "PackColor".equalsIgnoreCase(entryType)) )
               {                    
                
                   childsParentOrinNumber=pet.getParentStyleOrin();
                   String orinNumber=pet.getOrinNumber();
                   String vendorStyle= pet.getVendorStyle();
                   String vendorName=pet.getVendorName();
                   String productName=pet.getProductName();
                   String completionDate = "";
                   if(null != pet.getCompletionDate()
                           && (!pet.getCompletionDate().equals("")))
                   {                        
                           completionDate = pet.getCompletionDate().substring(0,10);                          
                   }  
                   
                   String contentState=pet.getContentState();
                   String imageState=pet.getImageState();                   
                   String deptId=pet.getDeptId();
                   String ominInd=pet.getOmniChannelVendor();
                   
                   String petStatus = pet.getPetStatus();
                   String newOrinNumber = "";
                   if(null != orinNumber
                           && orinNumber.length() > 9)
                   {
                           String orin = orinNumber.substring(0, 9);
                           String code = orinNumber.substring(9);
                           String color = "";
                           if(null != pet.getColorDesc())
                           {
                               color = pet.getColorDesc();
                           }
                           newOrinNumber = orin + " " + code + " " + color;
                   }
                   
                   StyleColor styleColor = new StyleColor();
                   styleColor.setEntryType(entryType);
                   styleColor.setParentStyleOrinNumber(childsParentOrinNumber);
                   //styleColor.setOrinNumber(orinNumber);
                   styleColor.setOrinNumber(newOrinNumber);
                   styleColor.setVendorStyle(vendorStyle);
                   styleColor.setVendorName(vendorName);
                   styleColor.setCompletionDate(completionDate);
                   styleColor.setProductName(productName);
                   styleColor.setPetStatus(petStatus);
                   
                   
                   styleColor.setContentStatus(contentState);
                   styleColor.setDeptId(deptId);
                   styleColor.setImageStatus(imageState);
                   styleColor.setOmniChannelVendor(ominInd);
                   
                   /**
                    * Modified for Defect# 927
                    * Date: 03/09/2016
                    * Modified by: AFUAXK4
                    */
                   String sourceType = pet.getReq_Type();
                   //LOGGER.info("sourceType ="+sourceType);
                   styleColor.setSourceType(sourceType);
                  
                   /**
                    * Modification end
                    */
                    
                   styleColorList.add(styleColor);
               }
                   
           }
           return styleColorList;
       }
       
       /**
        * This method will fetch the WorkList Details for Parent on base of the Advance search.
        * @param advanceSearch
        * @return
        * @throws SQLException
        * @throws ParseException
        */

       private List<PetsFound> getAdvWorkListDisplayDataForParent(AdvanceSearch advanceSearch)throws SQLException, ParseException  {
           
           LOGGER.info("This is from getAdvWorkListDisplayDataForParent..Start" );
           List<PetsFound> petList = new ArrayList<PetsFound>();
           PetsFound pet=null;
           XqueryConstants xqueryConstants= new XqueryConstants();
           
             Session session = null;
             Transaction tx =  null;
               
             try{
               session = sessionFactory.openSession();
               tx = session.beginTransaction();      
              //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
               Query query = session.createSQLQuery(xqueryConstants.getAdvWorkListDisplayDataForParent(advanceSearch));  
               LOGGER.info("getAdvWorkListDisplayDataForParent Query.." + query);
               // execute delete SQL statement
               List<Object[]> rows = query.list();
               if (rows != null) {
                   LOGGER.info("recordsFetched..." + rows);
                   for(Object[] row : rows){   
                       String parentStyleORIN = row[0]!=null?row[0].toString():null;
                       
                       String orinNumber=row[1]!=null?row[1].toString():null;
                       String deptId= row[2]!=null?row[2].toString():null;
                       String supplierId=row[3]!=null?row[3].toString():null;
                       
                       
                       String productNameStyle=row[4]!=null?row[4].toString():null;
                       String entryType=row[5]!=null?row[5].toString():null;
                       String primaryUPC = row[6]!=null?row[6].toString():null;
                       
                       String vendorName=row[7]!=null?row[7].toString():null;
                       String vendorStyle=row[8]!=null?row[8].toString():null;                                              
                       String classId = row[9]!=null?row[9].toString():null;
                       
                       String petStatus=row[10]!=null?row[10].toString():null;
                       String imageState=row[11]!=null?row[11].toString():null;
                       String contentState=row[12]!=null?row[12].toString():null;
                       
                       String completionDate=row[13]!=null?row[13].toString():null;
                       String omniChannelIndicator=row[14]!=null?row[14].toString():null;
                       String req_Type=row[15]!=null?row[15].toString():null;
                       
                       String earliestCompletionDate=row[16]!=null?row[16].toString():null;                       
                       String productNameComplex=row[17]!=null?row[17].toString():null;
                       
                       String existsInGroup = row[18]!=null?row[18].toString():"";
                       String cfas = row[19]!=null?row[19].toString():"";
                       String conversionFlag =row[20]!=null?row[20].toString():"";
                       if(conversionFlag.equalsIgnoreCase("true"))
                       {
                           req_Type = req_Type + ":C";
                       }
                       String productName = (productNameStyle != null) ? productNameStyle : productNameComplex;

                       pet  = mapAdseDbPetsToPortalAdvSearch(parentStyleORIN, orinNumber, 
                           deptId, supplierId, productName, entryType, primaryUPC, 
                           classId, vendorName, vendorStyle, imageState, contentState, 
                           petStatus, completionDate, pet, omniChannelIndicator, 
                           req_Type, "", earliestCompletionDate, existsInGroup, cfas);  
                       
                       petList.add(pet);
                   }
                   petList = petStatusMapping(petList);
                   Collections.sort(petList);               
                   
             } 
         }catch(Exception e){
             LOGGER.info("inside catch dao in getAdvWorkListDisplayDataForParent");
             e.printStackTrace(System.out);        
         }
         finally
         {
             LOGGER.info("recordsFetched. getAdvWorkListDisplayDataForParent finally block.." );
             session.flush();   
             tx.commit();
             session.close();                
         }

         LOGGER.info("This is from getAdvWorkListDisplayDataForParent..End" );
         return petList;
   }
       
       /**
        * This method will fetch the WorkList Details for Child on base of the Advance search.
        * @param advanceSearch
        * @return
        * @throws SQLException
        * @throws ParseException
        */

       private List<PetsFound> getAdvWorkListDisplayDataForChild(AdvanceSearch advanceSearch, String parentOrin)throws SQLException, ParseException  {
           
           LOGGER.info("This is from getAdvWorkListDisplayDataForChild..Start" );
           List<PetsFound> petList = new ArrayList<PetsFound>();
           PetsFound pet=null;
           XqueryConstants xqueryConstants= new XqueryConstants();
           
           
               Session session = null;
               Transaction tx =  null;
               
             try{
               session = sessionFactory.openSession();
               tx = session.beginTransaction();      
               //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
               Query query = session.createSQLQuery(xqueryConstants.getAdvWorkListDisplayDataForChild(advanceSearch, parentOrin));  
               LOGGER.info("getAdvWorkListDisplayDataForChild Query.." + query);
               // execute delete SQL statement
               List<Object[]> rows = query.list();
               if (rows != null) {
                   LOGGER.info("recordsFetched..." + rows);
                   for(Object[] row : rows){   
                       String parentStyleORIN = row[0]!=null?row[0].toString():null;
                       
                       String orinNumber=row[1]!=null?row[1].toString():null;
                       String entryType=row[2]!=null?row[2].toString():null;
                       String colorDesc=row[3]!=null?row[3].toString():null;
                       String supplierId=row[4]!=null?row[4].toString():null;
                       String deptId= row[5]!=null?row[5].toString():null;
                       
                       String productNameStyleColor=row[6]!=null?row[6].toString():null;
                       String vendorStyle=row[7]!=null?row[7].toString():null;
                       String primaryUPC = row[8]!=null?row[8].toString():null;
                       String classId = row[9]!=null?row[9].toString():null;
                       String completionDate=row[10]!=null?row[10].toString():null;
                       String req_Type=row[11]!=null?row[11].toString():null;
                       
                       String imageState=row[12]!=null?row[12].toString():null;
                       String contentState=row[13]!=null?row[13].toString():null;
                       String petStatus=row[14]!=null?row[14].toString():null;
                       
                       String vendorName=row[15]!=null?row[15].toString():null;                             
                       String omniChannelIndicator=row[16]!=null?row[16].toString():null;
                       
                       String colorDescComplexPack=row[17]!=null?row[17].toString():null;
                       String productNamePackColor = row[18]!=null?row[18].toString():null;
                       String conversionFlag =row[19]!=null?row[19].toString():"";
                       if(conversionFlag.equalsIgnoreCase("true"))
                       {
                           req_Type = req_Type + ":C";
                       }
                       String origincalColorDesc = (null != colorDesc)?colorDesc:colorDescComplexPack;                       
                       String productName = (productNameStyleColor != null) ? productNameStyleColor : productNamePackColor;
                       
                       pet  = mapAdseDbPetsToPortalAdvSearch(parentStyleORIN, orinNumber, 
                           deptId, supplierId, productName, entryType, primaryUPC, 
                           classId, vendorName, vendorStyle, imageState, contentState, 
                           petStatus, completionDate, pet, omniChannelIndicator, 
                           req_Type, origincalColorDesc, "", "", "");  
                       
                       petList.add(pet);
                   }
                   petList = petStatusMapping(petList);
                   
             } 
         }catch(Exception e){
             LOGGER.info("inside catch dao in getAdvWorkListDisplayDataForChild");
             e.printStackTrace(System.out);        
         }
         finally
         {
             LOGGER.info("recordsFetched. getAdvWorkListDisplayDataForChild finally block.." );
             session.flush();   
             tx.commit();
             session.close();                
         }

         LOGGER.info("This is from getAdvWorkListDisplayDataForChild..End" );
         return petList;
   }
       
       /**
        * This method will fetch the WorkList Details for Parent on base of the Advance search.
        */
       
       public List<WorkFlow> getPetDetailsByAdvSearchForParent(AdvanceSearch advanceSearch,List<String> supplierIdList,String vendorEmail)
           throws PEPPersistencyException {

           LOGGER.info("getPetDetailsByAdvSearchForParent....Enter");
           List<WorkFlow> workFlowList = null;
         
           try {
               List<PetsFound> petList = getAdvWorkListDisplayDataForParent(advanceSearch);
               LOGGER.info("getPetDetailsByAdvSearchForParent...." +petList.size());                            
               
               if(null != vendorEmail)  {  
                   LOGGER.info("Vendor Logged in  --- petlist size " + petList.size() + " Supplier Id size " + supplierIdList.size()+ "vendorEmail Search DAO---->"+ vendorEmail);
               List<PetsFound> petList1 = new ArrayList<PetsFound>();
               String supplierId = null;
               for(int i=0 ; i<supplierIdList.size() ;i++){
                  supplierId = supplierIdList.get(i).toString();
                  for(int j=0 ; j<petList.size(); j++){
                      if(supplierId.equalsIgnoreCase(petList.get(j).getSupplierId())){                          
                          petList1.add(petList.get(j));
                      }
                  }
               }
               petList = petList1;
               LOGGER.info("Vendor Logged in  --- petlist size bottom " + petList.size());
               }
               petList = orderPetDetailsListAdvanceParent(petList);
               
               WorkListDisplay workListDisplay = getFormatedPetDetailsForSearchPetParent(petList, false); // TODO
               workFlowList = workListDisplay.getWorkListDisplay();
               LOGGER.info("getPetDetailsByAdvSearchForParent DAO........" +workFlowList.size());
           }
           catch (SQLException e) {
               e.printStackTrace(System.out);
              throw new PEPPersistencyException("SQL Exception cought from getPetDetailsByAdvSearchForParent");
              
           }
           catch (ParseException e) {             
               e.printStackTrace(System.out);
               throw new PEPPersistencyException("Parse Exception cought from getPetDetailsByAdvSearchForParent");
           }
           catch (Exception e) {             
               e.printStackTrace(System.out);
               throw new PEPPersistencyException("Exception cought from getPetDetailsByAdvSearchForParent");
           }
           return workFlowList;
       }
       
       /**
        * This method will fetch the WorkList Details for Parent on base of the Advance search.
        */
       
       public List<StyleColor> getPetDetailsByAdvSearchForChild(AdvanceSearch advanceSearch, String parentOrin)
           throws PEPPersistencyException {
           LOGGER.info("getPetDetailsByAdvSearchForChild....Enter");
           List<StyleColor> styleColorList = null;
         
           try {
               List<PetsFound> petList = getAdvWorkListDisplayDataForChild(advanceSearch, parentOrin);
               LOGGER.info("getPetDetailsByAdvSearchForChild...." +petList.size());                            
               
               styleColorList = getFormatedPetDetailsForChild(petList);
               LOGGER.info("getPetDetailsByAdvSearchForChild DAO........" +styleColorList.size());
           }
           catch (SQLException e) {
               e.printStackTrace(System.out);
              throw new PEPPersistencyException("SQL Exception cought from getPetDetailsByAdvSearchForChild");
              
           }
           catch (ParseException e) {             
               e.printStackTrace(System.out);
               throw new PEPPersistencyException("Parse Exception cought from getPetDetailsByAdvSearchForChild");
           }
           catch (Exception e) {             
               e.printStackTrace(System.out);
               throw new PEPPersistencyException("Exception cought from getPetDetailsByAdvSearchForChild");
           }
           return styleColorList;
       }
       
       
       /**
        * Gets the ordered pet details.
        *
        * @param petList the pet list
        * @return the formated pet details
        */
       private List<PetsFound> orderPetDetailsListAdvanceParent(List<PetsFound> petList) {
           LOGGER.info("This is from orderPetDetailsListAdvanceParent...LIST SIZE-->"+petList.size());
           
           List<PetsFoundHierarchy> modifiedPetList = new ArrayList<PetsFoundHierarchy>();
           List<PetsFound> childList = new ArrayList<PetsFound>();
           List<PetsFound> sortedListMaster = new ArrayList<PetsFound>();
           PetsFoundHierarchy pet = new PetsFoundHierarchy();
           Date parentDate = null;
           Date childDate = null;
           //Populate new list to get it in required order
           try{
               for(PetsFound petObject:petList)
               {
                   String entryType = (null==petObject.getEntryType()?"":petObject.getEntryType().trim());
                   if(entryType.equalsIgnoreCase("Style") 
                           || entryType.equalsIgnoreCase("Complex Pack")
                           || entryType.equalsIgnoreCase("ComplexPack"))
                   {
                       if(childList.size() > 0)
                       {
                           pet.setChildList(childList);
                           modifiedPetList.add(pet);
                       }
                       else if(null != pet.getParentStyleOrin() 
                               && (!pet.getParentStyleOrin().equals("")))
                       {
                           modifiedPetList.add(pet);
                       }
                       childList = new ArrayList<PetsFound>();
                       pet = new PetsFoundHierarchy();
                       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                       String completionDate = "";
                       pet.setParent(petObject);
                       pet.setParentStyleOrin(petObject.getOrinNumber());
                       if(null != petObject.getEarliestComplitionDate()
                               && (!petObject.getEarliestComplitionDate().equals("")))
                       {                        
                               completionDate = petObject.getEarliestComplitionDate();
                               parentDate = formatter.parse(completionDate);
                               pet.setCompletionDate(parentDate);  
                       }                    
                       else
                       {
                           parentDate = new Date(Long.MAX_VALUE);
                           pet.setCompletionDate(new Date(Long.MAX_VALUE));
                       }
                       if(null != petObject.getDeptId()
                               && (!petObject.getDeptId().equals("")))
                       {
                           pet.setDeptId(Integer.parseInt(petObject.getDeptId()));
                       }
                       else
                       {
                           pet.setDeptId(9999);
                       }
                       if(null != petObject.getReq_Type()
                               && (!petObject.getReq_Type().equals("")))
                       {
                           if(petObject.getReq_Type().equalsIgnoreCase("PO"))
                           {
                               pet.setReq_Type("1");
                           }
                           else if(petObject.getReq_Type().equalsIgnoreCase("DIRECTFLAG")
                                   || petObject.getReq_Type().equalsIgnoreCase("DROPSHIP"))
                           {
                               pet.setReq_Type("2");
                           }
                           else if(petObject.getReq_Type().equalsIgnoreCase("Sold-Online")
                                   || petObject.getReq_Type().equalsIgnoreCase("SOLDONLINE"))
                           {
                               pet.setReq_Type("3");
                           }
                           else if(petObject.getReq_Type().equalsIgnoreCase("PEP"))
                           {
                               pet.setReq_Type("4");
                           }
                           else
                           {
                               pet.setReq_Type("5");
                           }
                       } 
                       else
                       {
                           pet.setReq_Type("6");
                       }
                   }
                   else{
                       
                       if(null != pet.getParentStyleOrin()
                           && !pet.getParentStyleOrin().equals("")
                           && pet.getParentStyleOrin().equals(petObject.getParentStyleOrin()))
                       {
                           childList.add(petObject);   
                       }
                       
                   }
               } 
               LOGGER.info("BEFORE LAST ENTRY....");
               if(childList.size() > 0)
               {
                   pet.setChildList(childList);
                   modifiedPetList.add(pet);
               }
               else if(null != pet.getParentStyleOrin() 
                       && (!pet.getParentStyleOrin().equals("")))
               {
                   modifiedPetList.add(pet);
               }
               LOGGER.info("NEW OBJECT LIST SIZE-->"+modifiedPetList.size());
               //DO required changes for ordering
               Collections.sort(modifiedPetList);
               //Modification End
               LOGGER.info("SORTED NEW OBJECT LIST SIZE-->"+modifiedPetList.size());
               //Populate old list again with ordered list
               for(PetsFoundHierarchy orderedObject:modifiedPetList)
               {
                   sortedListMaster.add(orderedObject.getParent());
                   if(null != orderedObject.getChildList())
                   {
                       for(PetsFound orderedObjectChild:orderedObject.getChildList())
                       {
                           sortedListMaster.add(orderedObjectChild);
                       }
                   }
               }                      
               
           }catch(ParseException exception){
               LOGGER.info("Parse Exception in orderPetDetailsList method");
               exception.printStackTrace();
           }
           catch(Exception exception){
               LOGGER.info("Exception in orderPetDetailsList method");
               exception.printStackTrace();
           }
           return sortedListMaster;
       }



    /**
     * Method to get the group for search pet.
     * 
     * @param adSearch
     *            AdvanceSearch
     * @param supplierIdList
     *            List<String>
     * @param vendorEmail
     *            String
     * @param styleOrinList
     *            List<String>
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Search Pet Date: 06/06/2016 Added
     *         By: Cognizant
     */
    @Override
    public List<WorkFlow> getAdvWorklistGroupingData(
        final AdvanceSearch adSearch, final List<String> supplierIdList,
        final String vendorEmail, final List<String> styleOrinList) {
        LOGGER.info("in DAO getAdvWorklistGroupingData");
        List<WorkFlow> workFlowList = new ArrayList<WorkFlow>();
        Session session = null;

        WorkFlow workFlow = null;
        XqueryConstants xqueryConstants = new XqueryConstants();
        Properties prop =
            PropertyLoader
                .getPropertyLoader(WorkListDisplayConstants.WORK_LIST_DISPLAY_PROPERTIES_FILE_NAME);
        if (xqueryConstants.getGroupSearchQueryForAdvSearch(adSearch,
            styleOrinList).contains("AND")) {

            try {
                session = sessionFactory.openSession();

                Query query =
                    session.createSQLQuery(xqueryConstants
                        .getGroupSearchQueryForAdvSearch(adSearch,
                            styleOrinList));
                LOGGER.info("Grouping Query:- " + query);

                query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                List<Object> rowList = query.list();

                if (rowList != null) {
                    for (final Object rowObj : rowList) {
                        final Map row = (Map) rowObj;
                        workFlow = new WorkFlow();

                        workFlow.setOrinNumber(row
                            .get(WorkListDisplayConstants.MDMID) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.MDMID).toString());
                        workFlow
                            .setProductName(row
                                .get(WorkListDisplayConstants.GROUP_NAME) == null
                                ? WorkListDisplayConstants.EMPTY_STRING : row
                                    .get(WorkListDisplayConstants.GROUP_NAME)
                                    .toString());
                        String overallStatusCode =
                            prop.getProperty(row
                                .get(WorkListDisplayConstants.GROUP_OVERALL_STATUS_CODE) == null
                                ? WorkListDisplayConstants.EMPTY_STRING
                                : row
                                    .get(
                                        WorkListDisplayConstants.GROUP_OVERALL_STATUS_CODE)
                                    .toString());
                        if (overallStatusCode != null
                            && (overallStatusCode
                                .equalsIgnoreCase(WorkListDisplayConstants.WAITING_TO_BE_CLOSED)
                                || overallStatusCode
                                    .equalsIgnoreCase(WorkListDisplayConstants.CLOSED) || overallStatusCode
                                .equalsIgnoreCase(WorkListDisplayConstants.PUBLISH_TO_WEB))) {
                            workFlow
                                .setPetStatus(WorkListDisplayConstants.COMPLETED);
                        }
                        else if (overallStatusCode != null
                            && (overallStatusCode
                                .equalsIgnoreCase(WorkListDisplayConstants.REACTIVATED))) {
                            workFlow
                                .setPetStatus(WorkListDisplayConstants.INITIATED);
                        }
                        else {
                            workFlow.setPetStatus(overallStatusCode);
                        }
                        workFlow
                            .setVendorStyle(prop.getProperty(row
                                .get(WorkListDisplayConstants.ENTRY_TYPE) == null
                                ? WorkListDisplayConstants.EMPTY_STRING : row
                                    .get(WorkListDisplayConstants.ENTRY_TYPE)
                                    .toString()));
                        workFlow.setDeptId(row
                            .get(WorkListDisplayConstants.DEF_DEPT_ID) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.DEF_DEPT_ID)
                                .toString());
                        workFlow
                            .setVendorName(row
                                .get(WorkListDisplayConstants.SUPPLIER_NAME) == null
                                ? WorkListDisplayConstants.EMPTY_STRING
                                : row.get(
                                    WorkListDisplayConstants.SUPPLIER_NAME)
                                    .toString());
                        workFlow
                            .setImageStatus(prop.getProperty(WorkListDisplayConstants.IMAGE
                                + row.get(WorkListDisplayConstants.IMAGE_STATE) == null
                                ? WorkListDisplayConstants.EMPTY_STRING : row
                                    .get(WorkListDisplayConstants.IMAGE_STATE)
                                    .toString()));
                        workFlow
                            .setContentStatus(prop.getProperty(WorkListDisplayConstants.CONTENT
                                + row
                                    .get(WorkListDisplayConstants.CONTENT_STATE) == null
                                ? WorkListDisplayConstants.EMPTY_STRING
                                : row.get(
                                    WorkListDisplayConstants.CONTENT_STATE)
                                    .toString()));
                        workFlow
                            .setSourceType(row
                                .get(WorkListDisplayConstants.PET_SOURCE) == null
                                ? WorkListDisplayConstants.EMPTY_STRING : row
                                    .get(WorkListDisplayConstants.PET_SOURCE)
                                    .toString());
                        workFlow
                            .setExistsInGroup(row
                                .get(WorkListDisplayConstants.EXIST_IN_GROUP) == null
                                ? WorkListDisplayConstants.EMPTY_STRING
                                : row.get(
                                    WorkListDisplayConstants.EXIST_IN_GROUP)
                                    .toString());
                        if ((row.get(WorkListDisplayConstants.CHILD_GROUP) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.CHILD_GROUP)
                                .toString())
                            .equalsIgnoreCase(WorkListDisplayConstants.CHILD_GROUP_C)
                            || (row.get(WorkListDisplayConstants.CHILD_GROUP) == null
                                ? WorkListDisplayConstants.EMPTY_STRING : row
                                    .get(WorkListDisplayConstants.CHILD_GROUP)
                                    .toString())
                                .equalsIgnoreCase(WorkListDisplayConstants.CHILD_GROUP_O)) {
                            workFlow
                                .setIsChildPresent(WorkListDisplayConstants.YES_Y);
                        }
                        else {
                            workFlow
                                .setIsChildPresent(WorkListDisplayConstants.NO_N);
                        }

                        String completionDate =
                            row.get(WorkListDisplayConstants.COMPLETION_DATE_RESULT_SET) == null
                                ? WorkListDisplayConstants.EMPTY_STRING
                                : row
                                    .get(
                                        WorkListDisplayConstants.COMPLETION_DATE_RESULT_SET)
                                    .toString();
                        if (completionDate.length() >= 10) {
                            workFlow.setCompletionDate(completionDate
                                .substring(0, 10));
                        }
                        else {
                            workFlow.setCompletionDate(completionDate);
                        }
                        workFlow.setIsGroup(WorkListDisplayConstants.YES_Y);
                        /*if (LOGGER.isDebugEnabled()) {
                            LOGGER
                                .debug("Grouping Attribute Values -- \nGROUP ID: "
                                    + workFlow.getOrinNumber()
                                    + "\nGROUP NAME: "
                                    + workFlow.getProductName()
                                    + "\nGROUP PET STATE: "
                                    + workFlow.getPetStatus()
                                    + "\nGROUP CONTENT STATUS: "
                                    + workFlow.getContentStatus()
                                    + "\nGROUP IMAGE STATUS: "
                                    + workFlow.getImageStatus()
                                    + "\nPET_SOURCE: "
                                    + workFlow.getSourceType()
                                    + "\nCOMPLETION_DATE: "
                                    + workFlow.getCompletionDate()
                                    + "\nGROUP TYPE: "
                                    + workFlow.getVendorStyle()
                                    + "\nDEPT ID: "
                                    + workFlow.getDeptId()
                                    + "\nVENDOR NAME: "
                                    + workFlow.getVendorName()
                                    + "\nCHILD GROUP : "
                                    + workFlow.getIsChildPresent());
                        }*/

                        workFlowList.add(workFlow);

                    }
                }
            }
            finally {
                if (session != null) {
                    session.flush();
                    session.close();
                }

            }
        }
        return workFlowList;
    }

    /**
     * Method to get the groups for search pet.
     * 
     * @param groupId
     *            String
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Search Pet Date: 06/02/2016 Added
     *         By: Cognizant

     */
    @Override
    public List<WorkFlow> groupSearchParent(final String groupId) {

        LOGGER.info("Entering WorkListDAO.groupSearchParent() method.");
        Session session = null;
        final List<WorkFlow> parentGroupList = new ArrayList<WorkFlow>();
        WorkFlow workFlow = null;
        List<Object> rows = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        Properties prop =
            PropertyLoader
                .getPropertyLoader(WorkListDisplayConstants.WORK_LIST_DISPLAY_PROPERTIES_FILE_NAME);
        try {
            session = sessionFactory.openSession();
            final Query query =
                session.createSQLQuery(xqueryConstants
                    .getGroupDetailsQueryParent(groupId));
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            rows = query.list();

            if (rows != null) {
                for (final Object rowObj : rows) {
                    final Map row = (Map) rowObj;
                    workFlow = new WorkFlow();

                    workFlow
                        .setOrinNumber(row
                            .get(WorkListDisplayConstants.GROUP_ID_RESULT_SET) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.GROUP_ID_RESULT_SET)
                                .toString());
                    workFlow.setProductName(row
                        .get(WorkListDisplayConstants.GROUP_NAME) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.GROUP_NAME).toString());
                    String overallStatusCode = prop.getProperty(row
                        .get(WorkListDisplayConstants.GROUP_OVERALL_STATUS_CODE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING
                        : row
                            .get(
                                WorkListDisplayConstants.GROUP_OVERALL_STATUS_CODE)
                            .toString());
                    if(overallStatusCode != null 
                            && (overallStatusCode.equalsIgnoreCase(WorkListDisplayConstants.WAITING_TO_BE_CLOSED)
                            || overallStatusCode.equalsIgnoreCase(WorkListDisplayConstants.CLOSED)
                            || overallStatusCode.equalsIgnoreCase(WorkListDisplayConstants.PUBLISH_TO_WEB)))
                    {
                        workFlow
                            .setPetStatus(WorkListDisplayConstants.COMPLETED);
                    }
                    else if(overallStatusCode != null 
                            && (overallStatusCode.equalsIgnoreCase(WorkListDisplayConstants.REACTIVATED)))
                    {
                        workFlow
                            .setPetStatus(WorkListDisplayConstants.INITIATED);
                    }
                    else
                    {
                        workFlow
                            .setPetStatus(overallStatusCode);
                    }
                    workFlow.setVendorStyle(prop.getProperty(row
                        .get(WorkListDisplayConstants.ENTRY_TYPE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.ENTRY_TYPE).toString()));
                    workFlow.setDeptId(row
                        .get(WorkListDisplayConstants.DEF_DEPT_ID) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.DEF_DEPT_ID).toString());
                    workFlow.setVendorName(row
                        .get(WorkListDisplayConstants.SUPPLIER_NAME) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.SUPPLIER_NAME).toString());
                    workFlow.setImageStatus(row
                        .get(WorkListDisplayConstants.IMAGE_STATE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.IMAGE_STATE).toString());
                    workFlow.setContentStatus(row
                        .get(WorkListDisplayConstants.CONTENT_STATE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.CONTENT_STATE).toString());
                    workFlow.setSourceType(row
                        .get(WorkListDisplayConstants.PET_SOURCE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.PET_SOURCE).toString());
                    workFlow
                        .setExistsInGroup(row
                            .get(WorkListDisplayConstants.EXIST_IN_GROUP) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.EXIST_IN_GROUP)
                                .toString());
                    if ((row.get(WorkListDisplayConstants.CHILD_GROUP) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.CHILD_GROUP).toString())
                        .equalsIgnoreCase(WorkListDisplayConstants.CHILD_GROUP_C)
                        || (row.get(WorkListDisplayConstants.CHILD_GROUP) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.CHILD_GROUP)
                                .toString())
                            .equalsIgnoreCase(WorkListDisplayConstants.CHILD_GROUP_O)) {
                        workFlow
                            .setIsChildPresent(WorkListDisplayConstants.YES_Y);
                    }
                    else {
                        workFlow
                            .setIsChildPresent(WorkListDisplayConstants.NO_N);
                    }

                    String completionDate =
                        row.get(WorkListDisplayConstants.COMPLETION_DATE_RESULT_SET) == null
                            ? WorkListDisplayConstants.EMPTY_STRING
                            : row
                                .get(
                                    WorkListDisplayConstants.COMPLETION_DATE_RESULT_SET)
                                .toString();
                    if (completionDate.length() >= 10) {
                        workFlow.setCompletionDate(completionDate.substring(0,
                            10));
                    }
                    else {
                        workFlow.setCompletionDate(completionDate);
                    }
                    workFlow.setIsGroup(WorkListDisplayConstants.YES_Y);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER
                            .debug("Grouping Attribute Values -- \nGROUP ID: "
                                + workFlow.getOrinNumber() + "\nGROUP NAME: "
                                + workFlow.getProductName()
                                + "\nGROUP PET STATE: "
                                + workFlow.getPetStatus()
                                + "\nGROUP CONTENT STATUS: "
                                + workFlow.getContentStatus()
                                + "\nGROUP IMAGE STATUS: "
                                + workFlow.getImageStatus() + "\nPET_SOURCE: "
                                + workFlow.getSourceType()
                                + "\nCOMPLETION_DATE: "
                                + workFlow.getCompletionDate()
                                + "\nGROUP TYPE: " + workFlow.getVendorStyle()
                                + "\nDEPT ID: " + workFlow.getDeptId()
                                + "\nVENDOR NAME: " + workFlow.getVendorName()
                                + "\nCHILD GROUP : "
                                + workFlow.getIsChildPresent());
                    }

                    parentGroupList.add(workFlow);
                }
            }
        }
        finally {
            session.flush();
            session.close();
        }
        LOGGER.info("Exiting WorkListDAO.groupSearchParent() method.");
        return parentGroupList;
    }

    /**
     * Method to get the child of Groups for search pet.
     * 
     * @param groupId
     *            String
     * @param advanceSearch
     *            AdvanceSearch
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Search Pet Date: 06/06/2016 Added
     *         By: Cognizant
     * @throws PEPPersistencyException
     * @throws PEPServiceException
     */
    @Override
    public List<WorkFlow> getChildForGroup(final String groupId,
        final AdvanceSearch advanceSearch) {

        LOGGER.info("Entering WorkListDAO.getChildForGroup() method.");
        LOGGER.info("Group ID: " + groupId);
        Session session = null;
        List<WorkFlow> childGroupList = new ArrayList<WorkFlow>();
        WorkFlow workFlow = null;
        List<Object> rows = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        Properties prop =
            PropertyLoader
                .getPropertyLoader(WorkListDisplayConstants.WORK_LIST_DISPLAY_PROPERTIES_FILE_NAME);
        try {
            session = sessionFactory.openSession();
            final Query query =
                session.createSQLQuery(xqueryConstants
                    .getChildForGroupQuery(groupId));
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            query.setParameter(WorkListDisplayConstants.GROUP_ORIN, groupId);
            query.setParameter(WorkListDisplayConstants.GROUP_ORIN, groupId);
            rows = query.list();

            if (rows != null) {
                for (final Object rowObj : rows) {
                    final Map row = (Map) rowObj;
                    workFlow = new WorkFlow();

                    String componentType =
                        row.get(WorkListDisplayConstants.COMPONENT_TYPE) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.COMPONENT_TYPE)
                                .toString();
                    if (componentType
                        .equalsIgnoreCase(WorkListDisplayConstants.GROUP)) {
                        workFlow
                            .setVendorStyle(prop.getProperty(row
                                .get(WorkListDisplayConstants.ENTRY_TYPE) == null
                                ? WorkListDisplayConstants.EMPTY_STRING : row
                                    .get(WorkListDisplayConstants.ENTRY_TYPE)
                                    .toString()));
                        workFlow.setIsGroup(WorkListDisplayConstants.YES_Y);
                        if ((row.get(WorkListDisplayConstants.CHILD_GROUP) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.CHILD_GROUP)
                                .toString())
                            .equalsIgnoreCase(WorkListDisplayConstants.CHILD_GROUP_C)
                            || (row.get(WorkListDisplayConstants.CHILD_GROUP) == null
                                ? WorkListDisplayConstants.EMPTY_STRING : row
                                    .get(WorkListDisplayConstants.CHILD_GROUP)
                                    .toString())
                                .equalsIgnoreCase(WorkListDisplayConstants.CHILD_GROUP_O)) {
                            workFlow
                                .setIsChildPresent(WorkListDisplayConstants.YES_Y);
                        }
                        else {
                            workFlow
                                .setIsChildPresent(WorkListDisplayConstants.NO_N);
                        }
                    }
                    else {
                        workFlow
                            .setVendorStyle(row
                                .get(WorkListDisplayConstants.VENDOR_STYLE_RESULT_SET) == null
                                ? WorkListDisplayConstants.EMPTY_STRING
                                : row
                                    .get(
                                        WorkListDisplayConstants.VENDOR_STYLE_RESULT_SET)
                                    .toString());
                        if (componentType
                            .equalsIgnoreCase(WorkListDisplayConstants.STYLE)) {
                            workFlow
                                .setIsChildPresent(WorkListDisplayConstants.YES_Y);
                        }
                        else {
                            workFlow
                                .setIsChildPresent(WorkListDisplayConstants.NO_N);
                        }

                    }
                    workFlow.setParentStyleOrinNumber(row
                        .get(WorkListDisplayConstants.PARENT_MDMID) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.PARENT_MDMID).toString());
                    workFlow.setOrinNumber(row
                        .get(WorkListDisplayConstants.MDMID) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.MDMID).toString());
                    workFlow
                        .setProductName(row
                            .get(WorkListDisplayConstants.PRODUCTNAME_RESULT_SET) == null
                            ? WorkListDisplayConstants.EMPTY_STRING
                            : row
                                .get(
                                    WorkListDisplayConstants.PRODUCTNAME_RESULT_SET)
                                .toString());
                    String overallStatusCode = prop.getProperty(row
                        .get(WorkListDisplayConstants.PET_STATE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING
                        : row
                            .get(
                                WorkListDisplayConstants.PET_STATE)
                            .toString());
                    if(overallStatusCode != null 
                            && (overallStatusCode.equalsIgnoreCase(WorkListDisplayConstants.WAITING_TO_BE_CLOSED)
                            || overallStatusCode.equalsIgnoreCase(WorkListDisplayConstants.CLOSED)
                            || overallStatusCode.equalsIgnoreCase(WorkListDisplayConstants.PUBLISH_TO_WEB)))
                    {
                        workFlow
                            .setPetStatus(WorkListDisplayConstants.COMPLETED);
                    }
                    else if(overallStatusCode != null 
                            && (overallStatusCode.equalsIgnoreCase(WorkListDisplayConstants.REACTIVATED)))
                    {
                        workFlow
                            .setPetStatus(WorkListDisplayConstants.INITIATED);
                    }
                    else
                    {
                        workFlow
                            .setPetStatus(overallStatusCode);
                    }                    

                    workFlow
                        .setDeptId(row.get(WorkListDisplayConstants.DEPT) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.DEPT).toString());
                    workFlow.setVendorName(row
                        .get(WorkListDisplayConstants.SUPPLIER_NAME) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.SUPPLIER_NAME).toString());
                    workFlow.setImageStatus(prop.getProperty(WorkListDisplayConstants.IMAGE
                        + row.get(WorkListDisplayConstants.IMAGE_STATE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.IMAGE_STATE).toString()));
                    workFlow
                        .setContentStatus(prop.getProperty(WorkListDisplayConstants.CONTENT
                            + row.get(WorkListDisplayConstants.CONTENT_STATE) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.CONTENT_STATE)
                                .toString()));
                    workFlow.setSourceType(row
                        .get(WorkListDisplayConstants.PET_SOURCE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.PET_SOURCE).toString());
                    workFlow.setEntryType(row
                        .get(WorkListDisplayConstants.ENTRY_TYPE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.ENTRY_TYPE).toString());
                    workFlow
                        .setOmniChannelVendor(row
                            .get(WorkListDisplayConstants.OMNICHANNELINDICATOR) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.OMNICHANNELINDICATOR)
                                .toString());
                    workFlow
                        .setExistsInGroup(row
                            .get(WorkListDisplayConstants.EXIST_IN_GROUP) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.EXIST_IN_GROUP)
                                .toString());
                    String completionDate =
                        row.get(WorkListDisplayConstants.COMPLETION_DATE_RESULT_SET) == null
                            ? WorkListDisplayConstants.EMPTY_STRING
                            : row
                                .get(
                                    WorkListDisplayConstants.COMPLETION_DATE_RESULT_SET)
                                .toString();
                    if (completionDate.length() >= 10) {
                        workFlow.setCompletionDate(completionDate.substring(0,
                            10));
                    }
                    else {
                        workFlow.setCompletionDate(completionDate);
                    }

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER
                            .debug("Grouping Attribute Values -- \nGROUP ID: "
                                + workFlow.getOrinNumber() + "\nGROUP NAME: "
                                + workFlow.getProductName()
                                + "\nGROUP PET STATE: "
                                + workFlow.getPetStatus()
                                + "\nGROUP CONTENT STATUS: "
                                + workFlow.getContentStatus()
                                + "\nGROUP IMAGE STATUS: "
                                + workFlow.getImageStatus() + "\nPET_SOURCE: "
                                + workFlow.getSourceType()
                                + "\nCOMPLETION_DATE: "
                                + workFlow.getCompletionDate()
                                + "\nGROUP TYPE: " + workFlow.getVendorStyle()
                                + "\nDEPT ID: " + workFlow.getDeptId()
                                + "\nVENDOR NAME: " + workFlow.getVendorName()
                                + "\nCHILD GROUP : "
                                + workFlow.getIsChildPresent());
                    }

                    childGroupList.add(workFlow);
                }
            }
        }
        finally {
            if(session != null)
            {
                session.flush();
                session.close();
            }
        }
        childGroupList = getFormattedChildForGroup(childGroupList);
        LOGGER.info("Exiting WorkListDAO.getChildForGroup() method.");
        return childGroupList;
    }

    /**
     * Method to arrange the child for search pet.
     * 
     * @param groupChilList
     *            List<WorkFlow>
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Search Pet Date: 06/06/2016 Added
     *         By: Cognizant
     */
    public List<WorkFlow> getFormattedChildForGroup(
        final List<WorkFlow> groupChilList) {

        LOGGER.info("Entering WorkListDAO.getFormattedChildForGroup method.");
        List<WorkFlow> masterList = new ArrayList<WorkFlow>();
        WorkFlow parentStyle = null;
        List childColorList = new ArrayList();
        for (Iterator iterator = groupChilList.iterator(); iterator.hasNext();) {
            WorkFlow workFlow = (WorkFlow) iterator.next();
            if (!workFlow.getEntryType().equals(WorkListDisplayConstants.STYLE)
                && !workFlow.getEntryType().equals(
                    WorkListDisplayConstants.STYLECOLOR)) {
                if (parentStyle != null) {
                    parentStyle.setColorList(childColorList);
                    masterList.add(parentStyle);
                    parentStyle = null;
                    childColorList = new ArrayList();
                }
                masterList.add(workFlow);
            }
            else {
                if (workFlow.getEntryType().equals(
                    WorkListDisplayConstants.STYLE)) {
                    if (parentStyle != null) {
                        parentStyle.setColorList(childColorList);
                        masterList.add(parentStyle);
                        parentStyle = null;
                        childColorList = new ArrayList();
                    }
                    parentStyle = workFlow;
                }
                else {
                    if (parentStyle != null
                        && parentStyle.getOrinNumber().equals(
                            workFlow.getParentStyleOrinNumber())) {
                        childColorList.add(workFlow);
                    }
                }
            }
        }
        if (parentStyle != null) {
            parentStyle.setColorList(childColorList);
            masterList.add(parentStyle);
            parentStyle = null;
            childColorList = new ArrayList();
        }
        LOGGER.info("Exiting WorkListDAO.getFormattedChildForGroup method.");
        return masterList;
    }

    /**
     * Method to get the child of Groups for search pet.
     * 
     * @param departmentNumbers
     *            ArrayList
     * @param pageNumber
     *            int
     * @param sortColumn
     *            String
     * @param sortOrder
     *            String
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Worklist Group Date: 06/08/2016
     *         Added By: Cognizant
     */
    @Override
    public List<WorkFlow> getWorklistGroupData(
        final ArrayList departmentNumbers, int pageNumber, String sortColumn,
        String sortOrder) {

        LOGGER.info("Entering WorkListDAO.getWorklistGroupData() method.");
        Session session = null;
        List<WorkFlow> childGroupList = new ArrayList<WorkFlow>();
        WorkFlow workFlow = null;
        List<Object> rows = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        Properties prop =
            PropertyLoader
                .getPropertyLoader(WorkListDisplayConstants.WORK_LIST_DISPLAY_PROPERTIES_FILE_NAME);
        String recordsPerPage =
            prop.getProperty(WorkListDisplayConstants.PAGE_LIMIT);
        int recordInPage = Integer.parseInt(recordsPerPage);
        LOGGER.info("Department Numbers -- " + departmentNumbers);
        StringBuffer depNumbers = new StringBuffer();

        if (departmentNumbers != null) {
            for (int i = 0; i < departmentNumbers.size(); i++) {
                if (i == 0) {
                    depNumbers.append(((DepartmentDetails) departmentNumbers
                        .get(i)).getId());
                }
                else {
                    if (i < departmentNumbers.size()) {
                        depNumbers.append(WorkListDisplayConstants.COMMA
                            + ((DepartmentDetails) departmentNumbers.get(i))
                                .getId());
                    }
                    else {
                        depNumbers
                            .append(((DepartmentDetails) departmentNumbers
                                .get(i)).getId());
                    }
                }
            }
        }
        try {
            session = sessionFactory.openSession();
            final Query query =
                session.createSQLQuery(xqueryConstants.getWorkListGroupQuery(
                    depNumbers.toString(), sortColumn, sortOrder));
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            query.setFirstResult(((pageNumber - 1) * recordInPage));
            query.setMaxResults(recordInPage);
            rows = query.list();

            if (rows != null) {
                for (final Object rowObj : rows) {
                    final Map row = (Map) rowObj;
                    workFlow = new WorkFlow();

                    workFlow.setVendorStyle(prop.getProperty(row
                        .get(WorkListDisplayConstants.ENTRY_TYPE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.ENTRY_TYPE).toString()));
                    workFlow.setIsGroup(WorkListDisplayConstants.YES_Y);
                    if ((row.get(WorkListDisplayConstants.CHILD_GROUP) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.CHILD_GROUP).toString())
                        .equalsIgnoreCase(WorkListDisplayConstants.CHILD_GROUP_C)
                        || (row.get(WorkListDisplayConstants.CHILD_GROUP) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.CHILD_GROUP)
                                .toString())
                            .equalsIgnoreCase(WorkListDisplayConstants.CHILD_GROUP_O)) {
                        workFlow
                            .setIsChildPresent(WorkListDisplayConstants.YES_Y);
                    }
                    else {
                        workFlow
                            .setIsChildPresent(WorkListDisplayConstants.NO_N);
                    }

                    workFlow.setOrinNumber(row
                        .get(WorkListDisplayConstants.MDMID) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.MDMID).toString());
                    workFlow.setProductName(row
                        .get(WorkListDisplayConstants.GROUP_NAME) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.GROUP_NAME).toString());
                    workFlow
                        .setPetStatus(row
                            .get(WorkListDisplayConstants.GROUP_OVERALL_STATUS_CODE) == null
                            ? WorkListDisplayConstants.EMPTY_STRING
                            : row
                                .get(
                                    WorkListDisplayConstants.GROUP_OVERALL_STATUS_CODE)
                                .toString());

                    workFlow.setDeptId(row
                        .get(WorkListDisplayConstants.DEF_DEPT_ID) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.DEF_DEPT_ID).toString());
                    workFlow.setVendorName(row
                        .get(WorkListDisplayConstants.SUPPLIER_NAME) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.SUPPLIER_NAME).toString());
                    workFlow.setImageStatus(row
                        .get(WorkListDisplayConstants.IMAGE_STATE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.IMAGE_STATE).toString());
                    workFlow.setContentStatus(row
                        .get(WorkListDisplayConstants.CONTENT_STATE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.CONTENT_STATE).toString());
                    workFlow.setSourceType(row
                        .get(WorkListDisplayConstants.PET_SOURCE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.PET_SOURCE).toString());
                    workFlow.setEntryType(row
                        .get(WorkListDisplayConstants.ENTRY_TYPE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.ENTRY_TYPE).toString());
                    workFlow
                        .setOmniChannelVendor(WorkListDisplayConstants.NO_N);
                    workFlow
                        .setExistsInGroup(row
                            .get(WorkListDisplayConstants.EXIST_IN_GROUP) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.EXIST_IN_GROUP)
                                .toString());
                    String completionDate =
                        row.get(WorkListDisplayConstants.COMPLETION_DATE_RESULT_SET) == null
                            ? WorkListDisplayConstants.EMPTY_STRING
                            : row
                                .get(
                                    WorkListDisplayConstants.COMPLETION_DATE_RESULT_SET)
                                .toString();
                    if (completionDate.length() >= 10) {
                        workFlow.setCompletionDate(completionDate.substring(0,
                            10));
                    }
                    else {
                        workFlow.setCompletionDate(completionDate);
                    }

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER
                            .debug("Grouping Attribute Values -- \nGROUP ID: "
                                + workFlow.getOrinNumber() + "\nGROUP NAME: "
                                + workFlow.getProductName()
                                + "\nGROUP PET STATE: "
                                + workFlow.getPetStatus()
                                + "\nGROUP CONTENT STATUS: "
                                + workFlow.getContentStatus()
                                + "\nGROUP IMAGE STATUS: "
                                + workFlow.getImageStatus() + "\nPET_SOURCE: "
                                + workFlow.getSourceType()
                                + "\nCOMPLETION_DATE: "
                                + workFlow.getCompletionDate()
                                + "\nGROUP TYPE: " + workFlow.getVendorStyle()
                                + "\nDEPT ID: " + workFlow.getDeptId()
                                + "\nVENDOR NAME: " + workFlow.getVendorName()
                                + "\nCHILD GROUP : "
                                + workFlow.getIsChildPresent());
                    }

                    childGroupList.add(workFlow);
                }
            }
        }
        finally {
            if(session!=null){
                session.flush();
                session.close();
            }
        }
        LOGGER.info("Exiting WorkListDAO.getWorklistGroupData() method.");
        return childGroupList;
    }

    /**
     * Method to get the groups count for Worklist group.
     * 
     * @param departmentNumbers
     *            ArrayList
     * @return int
     * 
     *         Method added For PIM Phase 2 - WorkList Group Date: 06/08/2016
     *         Added By: Cognizant
     */
    @Override
    public int groupWorklistSearchCount(final ArrayList departmentNumbers) {

        LOGGER.info("Entering WorkListDAO.groupWorklistSearchCount() method.");
        Session session = null;
        BigDecimal rowCount = new BigDecimal(0);
        List<Object> rows = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        LOGGER.info("Department Numbers -- " + departmentNumbers);
        StringBuffer depNumbers = new StringBuffer();

        if (departmentNumbers != null) {
            for (int i = 0; i < departmentNumbers.size(); i++) {
                if (i == 0) {
                    depNumbers.append(((DepartmentDetails) departmentNumbers
                        .get(i)).getId());
                }
                else {
                    if (i < departmentNumbers.size()) {
                        depNumbers.append(WorkListDisplayConstants.COMMA
                            + ((DepartmentDetails) departmentNumbers.get(i))
                                .getId());
                    }
                    else {
                        depNumbers
                            .append(((DepartmentDetails) departmentNumbers
                                .get(i)).getId());
                    }
                }
            }
        }
        try {
            session = sessionFactory.openSession();
            final Query query =
                session.createSQLQuery(xqueryConstants
                    .getWorkListGroupCountQuery(depNumbers.toString()));
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            rows = query.list();

            if (rows != null) {
                for (final Object rowObj : rows) {
                    final Map row = (Map) rowObj;
                    rowCount =
                        (BigDecimal) (row
                            .get(WorkListDisplayConstants.TOTAL_COUNT) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row
                                .get(WorkListDisplayConstants.TOTAL_COUNT));
                    LOGGER
                        .debug("Grouping Attribute Count -- \nCOUNT OF RECORDS: "
                            + rowCount);
                }
            }
        }
        finally {
            if(session!=null){
                session.flush();
                session.close();
            }
        }
        LOGGER.info("Exiting WorkListDAO.groupWorklistSearchCount() method.");

        return rowCount.intValue();
    }

    /**
     * Method to get the child of Groups for Worklist.
     * 
     * @param groupId
     *            String
     * @return List<WorkFlow>
     * 
     *         Method added For PIM Phase 2 - Group Worklist Date: 06/09/2016
     *         Added By: Cognizant
     */
    @Override
    public List<WorkFlow> getChildForGroupWorklist(final String groupId) {

        LOGGER.info("Entering WorkListDAO.getChildForGroupWorklist() method.");
        Session session = null;
        List<WorkFlow> childGroupList = new ArrayList<WorkFlow>();
        WorkFlow workFlow = null;
        List<Object> rows = null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        Properties prop =
            PropertyLoader
                .getPropertyLoader(WorkListDisplayConstants.WORK_LIST_DISPLAY_PROPERTIES_FILE_NAME);
        try {
            session = sessionFactory.openSession();
            final Query query =
                session.createSQLQuery(xqueryConstants
                    .getChildForGroupWorklistQuery(groupId));
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            query.setParameter(WorkListDisplayConstants.ORIN_NUM, groupId);
            query.setParameter(WorkListDisplayConstants.ORIN_NUM, groupId);
            rows = query.list();

            if (rows != null) {
                for (final Object rowObj : rows) {
                    final Map row = (Map) rowObj;
                    workFlow = new WorkFlow();

                    String componentType =
                        row.get(WorkListDisplayConstants.COMPONENT_TYPE) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.COMPONENT_TYPE)
                                .toString();
                    if (componentType
                        .equalsIgnoreCase(WorkListDisplayConstants.GROUP)) {
                        workFlow
                            .setVendorStyle(prop.getProperty(row
                                .get(WorkListDisplayConstants.ENTRY_TYPE) == null
                                ? WorkListDisplayConstants.EMPTY_STRING : row
                                    .get(WorkListDisplayConstants.ENTRY_TYPE)
                                    .toString()));
                        workFlow.setIsGroup(WorkListDisplayConstants.YES_Y);
                        if ((row.get(WorkListDisplayConstants.CHILD_GROUP) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.CHILD_GROUP)
                                .toString())
                            .equalsIgnoreCase(WorkListDisplayConstants.CHILD_GROUP_C)
                            || (row.get(WorkListDisplayConstants.CHILD_GROUP) == null
                                ? WorkListDisplayConstants.EMPTY_STRING : row
                                    .get(WorkListDisplayConstants.CHILD_GROUP)
                                    .toString())
                                .equalsIgnoreCase(WorkListDisplayConstants.CHILD_GROUP_O)) {
                            workFlow
                                .setIsChildPresent(WorkListDisplayConstants.YES_Y);
                        }
                        else {
                            workFlow
                                .setIsChildPresent(WorkListDisplayConstants.NO_N);
                        }
                    }
                    else {
                        workFlow
                            .setVendorStyle(row
                                .get(WorkListDisplayConstants.VENDOR_STYLE_RESULT_SET) == null
                                ? WorkListDisplayConstants.EMPTY_STRING
                                : row
                                    .get(
                                        WorkListDisplayConstants.VENDOR_STYLE_RESULT_SET)
                                    .toString());
                        if (componentType
                            .equalsIgnoreCase(WorkListDisplayConstants.STYLE)) {
                            workFlow
                                .setIsChildPresent(WorkListDisplayConstants.YES_Y);
                        }
                        else {
                            workFlow
                                .setIsChildPresent(WorkListDisplayConstants.NO_N);
                        }

                    }
                    workFlow.setParentStyleOrinNumber(row
                        .get(WorkListDisplayConstants.PARENT_MDMID) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.PARENT_MDMID).toString());
                    workFlow.setOrinNumber(row
                        .get(WorkListDisplayConstants.MDMID) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.MDMID).toString());
                    workFlow
                        .setProductName(row
                            .get(WorkListDisplayConstants.PRODUCTNAME_RESULT_SET) == null
                            ? WorkListDisplayConstants.EMPTY_STRING
                            : row
                                .get(
                                    WorkListDisplayConstants.PRODUCTNAME_RESULT_SET)
                                .toString());
                    String overallStatusCode = prop.getProperty(row
                        .get(WorkListDisplayConstants.PET_STATE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING
                        : row
                            .get(
                                WorkListDisplayConstants.PET_STATE)
                            .toString());
                    if(overallStatusCode != null 
                            && (overallStatusCode.equalsIgnoreCase(WorkListDisplayConstants.WAITING_TO_BE_CLOSED)
                            || overallStatusCode.equalsIgnoreCase(WorkListDisplayConstants.CLOSED)
                            || overallStatusCode.equalsIgnoreCase(WorkListDisplayConstants.PUBLISH_TO_WEB)))
                    {
                        workFlow
                            .setPetStatus(WorkListDisplayConstants.COMPLETED);
                    }
                    else if(overallStatusCode != null 
                            && (overallStatusCode.equalsIgnoreCase(WorkListDisplayConstants.REACTIVATED)))
                    {
                        workFlow
                            .setPetStatus(WorkListDisplayConstants.INITIATED);
                    }
                    else
                    {
                        workFlow
                            .setPetStatus(overallStatusCode);
                    }

                    workFlow
                        .setDeptId(row.get(WorkListDisplayConstants.DEPT) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.DEPT).toString());
                    workFlow.setVendorName(row
                        .get(WorkListDisplayConstants.SUPPLIER_NAME) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.SUPPLIER_NAME).toString());
                    workFlow.setImageStatus(prop.getProperty("Image"
                        + row.get(WorkListDisplayConstants.IMAGE_STATE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.IMAGE_STATE).toString()));
                    workFlow
                        .setContentStatus(prop.getProperty("Content"
                            + row.get(WorkListDisplayConstants.CONTENT_STATE) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.CONTENT_STATE)
                                .toString()));
                    workFlow.setSourceType(row
                        .get(WorkListDisplayConstants.PET_SOURCE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.PET_SOURCE).toString());
                    workFlow.setEntryType(row
                        .get(WorkListDisplayConstants.ENTRY_TYPE) == null
                        ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                            WorkListDisplayConstants.ENTRY_TYPE).toString());
                    workFlow
                        .setOmniChannelVendor(row
                            .get(WorkListDisplayConstants.OMNICHANNELINDICATOR) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.OMNICHANNELINDICATOR)
                                .toString());
                    workFlow
                        .setExistsInGroup(row
                            .get(WorkListDisplayConstants.EXIST_IN_GROUP) == null
                            ? WorkListDisplayConstants.EMPTY_STRING : row.get(
                                WorkListDisplayConstants.EXIST_IN_GROUP)
                                .toString());
                    String completionDate =
                        row.get(WorkListDisplayConstants.COMPLETION_DATE_RESULT_SET) == null
                            ? WorkListDisplayConstants.EMPTY_STRING
                            : row
                                .get(
                                    WorkListDisplayConstants.COMPLETION_DATE_RESULT_SET)
                                .toString();
                    if (completionDate.length() >= 10) {
                        workFlow.setCompletionDate(completionDate.substring(0,
                            10));
                    }
                    else {
                        workFlow.setCompletionDate(completionDate);
                    }
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER
                            .debug("Grouping Attribute Values -- \nGROUP ID: "
                                + workFlow.getOrinNumber() + "\nGROUP NAME: "
                                + workFlow.getProductName()
                                + "\nGROUP PET STATE: "
                                + workFlow.getPetStatus()
                                + "\nGROUP CONTENT STATUS: "
                                + workFlow.getContentStatus()
                                + "\nGROUP IMAGE STATUS: "
                                + workFlow.getImageStatus() + "\nPET_SOURCE: "
                                + workFlow.getSourceType()
                                + "\nCOMPLETION_DATE: "
                                + workFlow.getCompletionDate()
                                + "\nGROUP TYPE: " + workFlow.getVendorStyle()
                                + "\nDEPT ID: " + workFlow.getDeptId()
                                + "\nVENDOR NAME: " + workFlow.getVendorName()
                                + "\nCHILD GROUP : "
                                + workFlow.getIsChildPresent());
                    }

                    childGroupList.add(workFlow);
                }
            }
            childGroupList = getFormattedChildForGroup(childGroupList);
        }
        finally {
            if(session!=null){
                session.flush();
                session.close();
            }
        }
        LOGGER.info("Exiting WorkListDAO.getChildForGroupWorklist() method.");
        return childGroupList;
    }
    
}
