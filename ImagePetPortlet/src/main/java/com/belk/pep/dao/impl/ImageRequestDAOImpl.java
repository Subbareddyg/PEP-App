package com.belk.pep.dao.impl;

import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.belk.pep.constants.ImageConstants;
import com.belk.pep.constants.XqueryConstants;
import com.belk.pep.dao.ImageRequestDAO;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.form.ArtDirectorRequestDetails;
import com.belk.pep.form.ContactInfoDetails;
import com.belk.pep.form.ImageProductDetails;
import com.belk.pep.form.PepDetailsHistory;
import com.belk.pep.form.SamleImageDetails;
import com.belk.pep.form.StyleInfoDetails;
import com.belk.pep.form.VendorInfoDetails;
import com.belk.pep.model.ImageLinkVO;
import com.belk.pep.model.PetsFound;
import com.belk.pep.model.StyleColor;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.model.WorkListDisplay;
import com.belk.pep.util.ImageUtils;
import com.belk.pep.util.PropertyLoader;

/**
 * This class responsible for handling all the DAO call to the VP Database.
 * 
 * 
 */

public class ImageRequestDAOImpl implements ImageRequestDAO {
    /** The Constant LOGGER. */
    private final static Logger LOGGER =
        Logger.getLogger(ImageRequestDAO.class.getName());

    /** The session factory. */
    private SessionFactory sessionFactory;
    
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



    @Override
    public ArrayList<StyleInfoDetails> getStyleInfoDetails(String orinNo) throws PEPPersistencyException{
        LOGGER.info("inside getStyleInfoDetails() method");
        List<StyleInfoDetails> styleInfolist = new ArrayList<StyleInfoDetails>();
        Session session = this.sessionFactory.openSession();
      
        Query query = session.createSQLQuery(xqueryConstants.getStyleInfoDetails());
        query.setParameter("orinNo", orinNo); 
        query.setFetchSize(10);
        List<Object[]> rows = query.list();
        try{
        StyleInfoDetails styleInfo  =  null ;
        for(Object[] row : rows){
            
            styleInfo = new StyleInfoDetails();
            styleInfo.setOrinGrouping(row[0]!=null?row[0].toString():null);
            styleInfo.setStyleNo(row[3]!=null?row[3].toString():null);            
            styleInfo.setImageClass(row[4]!=null?row[4].toString():null);
            styleInfo.setDeptNo(row[1]!=null?row[1].toString():null);
            styleInfo.setVendorNo(row[5]!=null?row[5].toString():null);
            styleInfo.setImageDesc(row[6]!=null?row[6].toString():null);
            
            styleInfo.setOmniChannelVendor(row[7]!=null?row[7].toString():null);
            styleInfo.setVendorProvidedImage(row[8]!=null?row[8].toString():null);
            styleInfo.setVendorProvidedSample(row[9]!=null?row[9].toString():null);
            styleInfo.setVendorName(row[2]!=null?row[2].toString():null);
             
            styleInfolist.add(styleInfo);
        }
        }catch(Exception e){
        	e.printStackTrace();
        }
     
        session.close();       
        LOGGER.info("Exiting getStyleInfoDetails");
        return (ArrayList<StyleInfoDetails>) styleInfolist;

    }

    @Override
    public ArrayList<ImageProductDetails> getImageInfoDetails(String orinNo) throws PEPPersistencyException {        
        LOGGER.info("inside getImageInfoDetails() method");
        
        List<ImageProductDetails> imageProdlist = new ArrayList<ImageProductDetails>();
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        //TODO need to modify the in Java Code format and set the method in Query Constant as orinNo parameter
        
        Query query = session.createSQLQuery(xqueryConstants.getImageProductDetails());
        query.setParameter("orinNo", orinNo); 
        query.setFetchSize(10);
        List<Object[]> rows = query.list();
        try{
        for(Object[] row : rows){
           
            
            ImageProductDetails imgProdDetails = new ImageProductDetails();
            if(row[2] != null){
                
                imgProdDetails.setProductName(row[2]!=null?row[2].toString():null);
            }else{
                imgProdDetails.setProductName("");
            }
                   
            imgProdDetails.setProductDescription(row[3]!=null?row[3].toString():null);
            
            
            imageProdlist.add(imgProdDetails);
           
        }  
        }catch(Exception e){
        	e.printStackTrace();
        }
        tx.commit();
        session.close();      
        LOGGER.info("Exiting getImageInfoDetails() method");
        return (ArrayList<ImageProductDetails>)imageProdlist;
    }  
    
    
    /**
     * get the vendorInformation details
     * @param orinNo
     * @return
     */
    @Override
    public ArrayList<VendorInfoDetails> getVendorInformation(String orinNo)
    throws PEPPersistencyException{
        LOGGER.info("in getVendorInfoDetails()");        
        List<VendorInfoDetails> vendorDetailsList = new ArrayList<VendorInfoDetails>();
        Session session = this.sessionFactory.openSession();
        Query query = null;
        Transaction tx = session.beginTransaction();
        query = session.createSQLQuery(xqueryConstants.getVendorInformation());
        query.setParameter("orinNo", orinNo); 
        query.setFetchSize(10);
        List<Object[]> rows = query.list();
        try{
        for(Object[] row : rows){
            
            VendorInfoDetails viDetails = new VendorInfoDetails();
            //Null check
            if(row[2] != null){
                viDetails.setCarrierAcct(row[0]!=null?row[2].toString():null);                
            }else{
               
                viDetails.setCarrierAcct("");                
            }if(row[3]!= null){                
                viDetails.setVendorsampleAddress(row[3]!=null?row[3].toString():null);
            }else{
               
                viDetails.setVendorsampleAddress("");
            }
            
            viDetails.setVendorsampleIndicator(row[0]!=null?row[1].toString():null);
            
            vendorDetailsList.add(viDetails);
            
        } 
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        tx.commit();
        session.close(); 
        
        LOGGER.info("Exiting getVendorInfoDetails");
        return (ArrayList<VendorInfoDetails>) vendorDetailsList;
    }
    
    @Override
    public ArrayList<ContactInfoDetails> getContactInformation(String orinNo)
    throws PEPPersistencyException{
        LOGGER.info("inside getContactInfoDetails()");
     
        List<ContactInfoDetails> conInfoDetailList = new ArrayList<ContactInfoDetails>() ;
        Session session = this.sessionFactory.openSession();
        Query query = null;
        Transaction tx = session.beginTransaction();
        try{
        query = session.createSQLQuery(xqueryConstants.getContactInformation());
        query.setParameter("orinNo", orinNo); 
        query.setFetchSize(10);
        List<Object[]> rows = query.list();
        for(Object[] row: rows){
            LOGGER.info("iterating the resultset");
            if(null!=row[0]){
            if(ImageConstants.IMAGE_CONTACT1_TITLE.equals(row[0].toString())){              
                ContactInfoDetails contact1obj = new ContactInfoDetails();
                contact1obj.setContactTitle(row[0].toString());
                contact1obj.setVendorContactName(row[3]!=null?row[3].toString():null);
                if(row[5] != null){
                    contact1obj.setVendorContactEmail(row[5]!=null?row[5].toString():null);
                }else{
                   
                    contact1obj.setVendorContactEmail("");
                }
                contact1obj.setVendorContactTelephone(row[4]!=null?row[4].toString():null);
                conInfoDetailList.add(contact1obj);
            }
            }
            if(null!=row[0]){
            if(ImageConstants.IMAGE_CONTACT2_TITLE.equals(row[0].toString())){               
                ContactInfoDetails contact2obj = new ContactInfoDetails();
                contact2obj.setContactTitle(row[0]!=null?row[0].toString():null);
                contact2obj.setVendorContactName(row[3]!=null?row[3].toString():null);
                if(row[5] != null){
                    contact2obj.setVendorContactEmail(row[5]!=null?row[5].toString():null);
                }else{                   
                    contact2obj.setVendorContactEmail("");
                }
                contact2obj.setVendorContactTelephone(row[4]!=null?row[4].toString():null);
                conInfoDetailList.add(contact2obj);
                
            }           
        }
        
            if(null!=row[0]){
                if("Contact 3".equals(row[0].toString())){               
                    ContactInfoDetails contact3obj = new ContactInfoDetails();
                    contact3obj.setContactTitle(row[0]!=null?row[0].toString():null);
                    contact3obj.setVendorContactName(row[3]!=null?row[3].toString():null);
                    if(row[5] != null){
                        contact3obj.setVendorContactEmail(row[5]!=null?row[5].toString():null);
                    }else{
                       
                        contact3obj.setVendorContactEmail("");
                    }
                    contact3obj.setVendorContactTelephone(row[4]!=null?row[4].toString():null);
                    conInfoDetailList.add(contact3obj);
                    
                }           
            }
            
            if(null!=row[0]){
                if("Contact 4".equals(row[0].toString())){               
                    ContactInfoDetails contact4obj = new ContactInfoDetails();
                    contact4obj.setContactTitle(row[0]!=null?row[0].toString():null);
                    contact4obj.setVendorContactName(row[3]!=null?row[3].toString():null);
                    if(row[5] != null){
                        contact4obj.setVendorContactEmail(row[5]!=null?row[5].toString():null);
                    }else{
                       
                        contact4obj.setVendorContactEmail("");
                    }
                    contact4obj.setVendorContactTelephone(row[4]!=null?row[4].toString():null);
                    conInfoDetailList.add(contact4obj);
                    
                }           
            }
            
            if(null!=row[0]){
                if("Contact 5".equals(row[0].toString())){               
                    ContactInfoDetails contact5obj = new ContactInfoDetails();
                    contact5obj.setContactTitle(row[0]!=null?row[0].toString():null);
                    contact5obj.setVendorContactName(row[3]!=null?row[3].toString():null);
                    if(row[5] != null){
                        contact5obj.setVendorContactEmail(row[5]!=null?row[5].toString():null);
                    }else{
                       
                        contact5obj.setVendorContactEmail("");
                    }
                    contact5obj.setVendorContactTelephone(row[4]!=null?row[4].toString():null);
                    conInfoDetailList.add(contact5obj);
                    
                }           
            }
            
            
        }
        }catch(Exception e){
        	e.printStackTrace();
        	
        }finally{
        	 tx.commit();
             session.close(); 
        }
      
        
        LOGGER.info("Exiting getVendorInfoDetails");
        return (ArrayList<ContactInfoDetails>) conInfoDetailList;      
    }
    
    /**
     * this is dao method to invoke pep history
     */
    @Override
    public ArrayList<PepDetailsHistory> getPepHistoryDetails(String orinNo) throws PEPPersistencyException{
        LOGGER.info("inside getPepHistoryDetails()");
        List<PepDetailsHistory> pepList = new ArrayList<PepDetailsHistory>();
        Session session = this.sessionFactory.openSession();
        Query query = null;
        Transaction tx = session.beginTransaction();
        query = session.createSQLQuery(xqueryConstants.getPepHistoryDetails());
        query.setParameter("orinNo", orinNo); 
        query.setFetchSize(100);
        List<Object[]> rows = query.list();
        SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");
        Date date = null;
        Date date1 = null;
        String createdOn = "";
        String updatedDate = "";
        
        
        try{
        	Properties prop =PropertyLoader.getPropertyLoader(ImageConstants.LOAD_IMAGE_PROPERTY_FILE);
        for(Object[] row: rows){
            LOGGER.info("iterating pep history");
            PepDetailsHistory pepHist = new PepDetailsHistory();
            pepHist.setPepOrinNumber(row[0]!=null?row[0].toString():null);
            
          
            
            if(null != row[6]){
            	if("01".equalsIgnoreCase(row[6].toString())){
            		pepHist.setHistoryStatus("Was Initiated");
            	}
            	if("02".equalsIgnoreCase(row[6].toString())){
            		pepHist.setHistoryStatus("Was Completed");
            	}
				if("03".equalsIgnoreCase(row[6].toString())){
					pepHist.setHistoryStatus("Was Approved");
				}
				if("04".equalsIgnoreCase(row[6].toString())){
					pepHist.setHistoryStatus("Was Failed RRD Check");
				}
				if("05".equalsIgnoreCase(row[6].toString())){
					pepHist.setHistoryStatus("Was Deactivated");
				}
				if("06".equalsIgnoreCase(row[6].toString())){
					pepHist.setHistoryStatus("Was Closed");
				}
				if("07".equalsIgnoreCase(row[6].toString())){
					pepHist.setHistoryStatus("Was Rejected By DCA");
				}
				if("08".equalsIgnoreCase(row[6].toString())){
					pepHist.setHistoryStatus("Was Ready For Review");
				}
            	
            }else{
            	pepHist.setHistoryStatus("");
            }
            
            
            pepHist.setPepUpdatedBy(row[5]!=null?row[5].toString():null);
            
            if(row[2]!=null){
            	
            	date = formatter.parse(row[2].toString());
            	createdOn = formatter1.format(date);
            	pepHist.setCreatedOn(createdOn!=null?createdOn.toString():null);
            	
            	
            }
            if(row[4]!=null){
            	date1 = formatter.parse(row[4].toString());
            	updatedDate = formatter1.format(date1);
            	pepHist.setUpdateDate(updatedDate!=null?updatedDate.toString():null);
            }
            
            pepHist.setCreatedBy(row[1]!=null?row[1].toString():null);
            
            if(null != row[3]){
            	String imageStateCode=row[3] == null? "" : row[3].toString();
                String imageStateDesc = prop.getProperty("Image"+imageStateCode);
                
                String imageState = imageStateDesc == null ? "": imageStateDesc.toString();
                pepHist.setUpdatedStatus(imageState);
            }else{
            	pepHist.setUpdatedStatus("");
            }
            
            
            pepList.add(pepHist);
            
        }
        }catch(Exception e){
        	
        }
        tx.commit();
        session.close();
       
        return (ArrayList<PepDetailsHistory>) pepList;       
    }
    
    /**
     * this dao method invokes sampleImagelinks
     * @param orinNo
     * @return
     */
    @Override
    public ArrayList<SamleImageDetails> getSampleImageLinks(String orinNo) throws PEPPersistencyException{
    	LOGGER.info("inside getSampleImageLinksDetails()dao impl");
    	List<SamleImageDetails> sampleImgList = new ArrayList<SamleImageDetails>();
    	Session session = this.sessionFactory.openSession();
        Query query = null;
        Transaction tx = session.beginTransaction();
        query = session.createSQLQuery(xqueryConstants.getSampleImageLinks());
        query.setParameter("orinNo", orinNo); 
        query.setFetchSize(10);
        List<Object[]> rows = query.list();
        try{
        for(Object[] row : rows){        	
        	
        	SamleImageDetails sampleImage = new SamleImageDetails();        	
        	sampleImage.setOrinNo(row[0]!=null?row[0].toString():null);
        	sampleImage.setImageId(row[1]!=null?row[1].toString():null);
        	//Null check
        	if(row[2]!=null){
        		sampleImage.setImageName(row[2]!=null?row[2].toString():null);
        	}else {
        		sampleImage.setImageName("");
        	}
        	if(row[3] !=null){
        		sampleImage.setImageLocation(row[3]!=null?row[3].toString():null);
        	}else{
        		sampleImage.setImageLocation("");
        	}
        	
        	sampleImage.setImageShotType(row[4]!=null?row[4].toString():null);
        	
        	if(row[5] !=null){
        		
        		if(row[5].toString().equalsIgnoreCase("ReadyForReview") || row[5].toString().equalsIgnoreCase("Ready_For_Review") || row[5].toString().contains("Ready_")){
        			
        			sampleImage.setLinkStatus("ReadyForReview");
        		}else{
        			
        			sampleImage.setLinkStatus(row[5]!=null?row[5].toString():null);
        		}        		
        	}else{
        		LOGGER.info("Link Status is null");
        		sampleImage.setLinkStatus("Initiated");
        	}
        	
        	
        	
        	if(row[6] !=null){
        		
        		if(row[6].toString().equalsIgnoreCase("Ready_For_Review") || row[6].toString().contains("Ready_")){
        			LOGGER.info("if condtion ReadyForReview ");
        			sampleImage.setImageStatus("ReadyForReview");
        		}else{
        			
        			sampleImage.setImageStatus(row[6]!=null?row[6].toString():null);
        		}        		
        	}else{
        		LOGGER.info("imageStatus as----null in DAO ");
        		sampleImage.setImageStatus("Initiated");
        	}
        	
        	if(row[7] !=null){
        		sampleImage.setImageSampleId(row[7]!=null?row[7].toString():null);
        	}else {
        		sampleImage.setImageSampleId("");
        	}
        	sampleImage.setImageSamleReceived(row[8]!=null?row[8].toString():null);
        	sampleImage.setImageSilhouette(row[9]!=null?row[9].toString():null);
        	if(row[10] !=null){
        		sampleImage.setTiDate(row[10]!=null?row[10].toString():null);
        	}else {
        		
        		sampleImage.setTiDate("");
        	}
        	if(row[11] !=null){
        		sampleImage.setSampleCoordinatorNote(row[11]!=null?row[11].toString():null);
        	}else {
        		
        		sampleImage.setSampleCoordinatorNote("");
        	}       	
        	sampleImgList.add(sampleImage);
        }
        }catch(Exception e){
        	e.printStackTrace();
        }
        finally{
    	tx.commit();    	
    	session.close();
        }
    	
    	
    	return (ArrayList<SamleImageDetails>) sampleImgList; 
    	
    }
    
    
    /**
     * this method invokes artDirectorDetails
     * @param orinNo
     * @return
     */
    @Override
    public ArrayList<ArtDirectorRequestDetails> getArtDirectorRequestDetails(String orinNo) throws PEPPersistencyException{
    	LOGGER.info("inside getArtDirectorRequestDetails() dao impl");
    	List<ArtDirectorRequestDetails> artDirectorList = new ArrayList<ArtDirectorRequestDetails>();
    	Session session = this.sessionFactory.openSession();
        Query query = null;
        Transaction tx = session.beginTransaction();
        query = session.createSQLQuery(xqueryConstants.getArtDirectorRequestDetails());
        query.setParameter("orinNo", orinNo); 
        query.setFetchSize(10);
        List<Object[]> rows = query.list();
        try{
        for(Object[] row: rows){
        	
        	ArtDirectorRequestDetails ardDetails = new ArtDirectorRequestDetails();
        	ardDetails.setOrinNo(row[0]!=null?row[0].toString():null);        	
        	ardDetails.setImageId(row[1]!=null?row[1].toString():null);
        	ardDetails.setImageType("");
        	if(row[2] != null){
        		ardDetails.setImageLocation(row[2]!=null?row[2].toString():null);
        	}else {
        		ardDetails.setImageLocation	("");
        	}
        	if(row[3] !=null){
        		ardDetails.setImageDescription(row[3]!=null?row[3].toString():null);
        	}else{
        		ardDetails.setImageDescription("");
        	}
        	if(row[4]!=null){
        		ardDetails.setAdditionalInfo(row[4]!=null?row[4].toString():null);
        	}else {
        		ardDetails.setAdditionalInfo("");
        	}
        	if(row[5] !=null){
        		ardDetails.setImageUrlDesc(row[5]!=null?row[5].toString():null);
        	}else {
        		ardDetails.setImageUrlDesc("");
        	} 
        	if(row[6] !=null){
        		ardDetails.setArdIndicator(row[6]!=null?row[6].toString():null);
        	}else {
        		ardDetails.setArdIndicator("");
        	} 
        	if(row[7] !=null){
        		ardDetails.setArtDirectorNote((row[7]!=null?row[7].toString():null));
        	}else {
        		ardDetails.setArtDirectorNote("");
        	} 
        	if(row[8] !=null){
        		ardDetails.setImageStatus((row[8]!=null?row[8].toString():null));
        	}else {
        		ardDetails.setImageStatus("");        	} 
        	
        	artDirectorList.add(ardDetails);        	
        	
        }
        }catch(Exception e){
        	e.printStackTrace();
        }
        finally{
        tx.commit();
        session.close();  
        }
        
        LOGGER.info("exiting getArtDirectorRequestDetails() dao impl");
        return (ArrayList<ArtDirectorRequestDetails>) artDirectorList;
        
        
    }
    
    /**
     * This method will return the PET details from the ADSE on base of department 
     * @param departmentNumbers
     * @return
     * @throws PEPServiceException
     * @throws PEPPersistencyException
     */
    @Override
    public List<WorkFlow> getImageMgmtDetailsByOrin(String orinNum)
        throws PEPPersistencyException {
        LOGGER.info("getImageMgmtDetailsByOrin....Enter");
        ArrayList pepDetails = null;
        List<WorkFlow> workFlowList = null;
        try {
            List<PetsFound> petList = getWorkListDisplayData(orinNum);
            
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
        return workFlowList;
    }
    
    /**
     * Gets the work list display data.
     *
     * @return the work list display data
     * @throws SQLException 
     * @throws ParseException 
     */
    public List<PetsFound> getWorkListDisplayData(String orinNo) throws SQLException, ParseException {
        LOGGER.info("This is from getWorkListDisplayData..Start" );
        List<PetsFound> petList = new ArrayList<PetsFound>();
        PetsFound pet=null;
        XqueryConstants xqueryConstants= new XqueryConstants();
        Session session = null;
        Transaction tx =  null;
            
          try{
            Properties prop =PropertyLoader.getPropertyLoader(ImageConstants.LOAD_IMAGE_PROPERTY_FILE);
            session = sessionFactory.openSession();
            tx = session.beginTransaction();      
           //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
            Query query = session.createSQLQuery(xqueryConstants.getImageManagementDetails());  
            query.setParameter("orinNo", orinNo); 
            query.setFetchSize(100);
            
            // execute delete SQL statement
            List<Object[]> rows = query.list();
            if (rows != null) {
                
                for(Object[] row : rows){   
                    
                    String parentStyleORIN = row[0]!=null?row[0].toString():null;
                    
                    String orinNumber=row[1]!=null?row[1].toString():null;
                    String entryType= row[2]!=null?row[2].toString():null;
                    String vendorStyle=row[3]!=null?row[3].toString():null;
                    String vendorColor=row[4]!=null?row[4].toString():null;
                    String vendorColorDesc=row[5]!=null?row[5].toString():null;
                    String imageStateCode=row[6] == null? "" : row[6].toString();
                    String imageStateDesc = prop.getProperty("Image"+imageStateCode);
                    String imageState = imageStateDesc != null ? imageStateDesc.toString() : null;
                    
                    String completionDate=row[7]!=null?row[7].toString():null;
                    String supplierId=row[8]!=null?row[8].toString():null;
                  

                    String petStateCode=row[9] == null? "" : row[9].toString();
                    String petStateDesc = prop.getProperty(petStateCode);
                    String petState = petStateDesc != null ? petStateDesc.toString() : null;
                    
                    String returnCarsFlag = row[10] == null? "" : row[10].toString();;
                    
                    pet  = mapAdseDbPetsToPortal(parentStyleORIN,orinNumber,entryType,vendorColor,vendorColorDesc,imageState,completionDate,vendorStyle,pet,supplierId,returnCarsFlag);                                    
                    petList.add(pet);
                }
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
    
    private WorkListDisplay getFormatedPetDetails(List<PetsFound> petList) {
        String childsParentOrinNumber = null;
        String  parentOrinNumber = null;
        List<StyleColor> styleColorList = new ArrayList<StyleColor>();
        List<WorkFlow> styleList = new ArrayList<WorkFlow>();
        List<WorkFlow> styleListForWorkListDisplay = new ArrayList<WorkFlow>();
        WorkListDisplay workListDisplay= new WorkListDisplay();
       
        
        try{
        for(PetsFound pet:petList)
        {
            String entryType=pet.getEntryType();  
            
            LOGGER.info("entryType Image DAO IMPL*******  ."+entryType);
            if(entryType.equalsIgnoreCase("StyleColor") || entryType.equalsIgnoreCase("PackColor") )
            {
            	
                childsParentOrinNumber=pet.getParentStyleOrin();
                String orinNumber=pet.getOrinNumber();  
                String vendorColor = pet.getVendorColorCode();  
                String vendorColorDesc = pet.getVendorColorDesc();  
             
                String completionDate=pet.getCompletionDate();
           
                String imageState=pet.getImageState();                   
                String vendorStyle= pet.getVendorStyle();
             
                StyleColor styleColor = new StyleColor();
                styleColor.setEntryType(entryType);
                styleColor.setParentStyleOrinNumber(childsParentOrinNumber);
                styleColor.setOrinNumber(orinNumber);
                styleColor.setVendorStyle(vendorStyle);
               
                styleColor.setCompletionDate(completionDate);
            
              
                styleColor.setVendorColorCode(vendorColor);
                styleColor.setVendorColorDesc(vendorColorDesc);
                styleColor.setImageStatus(imageState);
                styleColor.setSupplierID(pet.getSupplierID());
                
                styleColor.setReturnCarsFlag(pet.getReturnCarsFlag());
             
                styleColorList.add(styleColor);//Add all the StyleColor to the  Style Color list 
                
              
            }
            if(entryType.equalsIgnoreCase("Style") || entryType.contains("Complex"))
            {                    
             
            	 
                        String orinNumber= pet.getOrinNumber();
                     
                        parentOrinNumber=pet.getParentStyleOrin();
                      
                        String colorDes=pet.getVendorColorDesc();
                        String VendorColorCode=pet.getVendorColorCode();
                        String completionDate=pet.getCompletionDate();
                  
                        String imageState=pet.getImageState(); 
                        String vendorStyle= pet.getVendorStyle();
                        
                
                        WorkFlow style = new WorkFlow();
                        style.setEntryType(entryType);
                        style.setOrinNumber(orinNumber);
                        style.setParentStyleOrinNumber(parentOrinNumber);
                        style.setColorDes(colorDes);
                        style.setVendorColorCode(VendorColorCode);                      
                        style.setCompletionDate(completionDate);
                       
                        style.setImageStatus(imageState);
                       
                        
                        style.setVendorStyle(vendorStyle);
                        style.setStyleColor(styleColorList);
                        style.setSupplierID(pet.getSupplierID());
                        
                        styleList.add(style);//Add all the Style to the  Style  list  
                        
              
                }        
            
           
        }
         
        //Check for the Parent Child association
        for(WorkFlow style :styleList)
        {
           parentOrinNumber = style.getParentStyleOrinNumber();
             
           List<StyleColor> subStyleColorList = new ArrayList<StyleColor>();
           
           for(StyleColor styleColor :styleColorList)
           {
              
              childsParentOrinNumber =styleColor.getParentStyleOrinNumber();
            
              if(parentOrinNumber.equalsIgnoreCase(childsParentOrinNumber))
              {
                  //Defect 14
                  styleColor.setVendorStyle(style.getVendorStyle());
                  subStyleColorList.add(styleColor);
              }
               
           }
           if(subStyleColorList.size()>0){
              
               style.setStyleColor(subStyleColorList);//Add all the child Style Colors to the Parent Style
           }else{
               
               //style.setStyleColor(new ArrayList<StyleColor>()); 
           }
           styleListForWorkListDisplay.add(style);//Add  all the Styles with children Style Color to the declared worklist display list 
            
        }
        
        }catch(Exception e){
        	e.printStackTrace();
        }
       
        workListDisplay.setWorkListDisplay(styleListForWorkListDisplay);//Add all the Styles to the work list display form
        LOGGER.info("worklistdisplay size.."+workListDisplay.getWorkListDisplay().size());
        return workListDisplay;
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
        String orinNumber, String entryType, String vendorColor,String vendorColorDesc, 
        String imageState,
         String completionDate,String vendorStyle, PetsFound pet,String supplierId,String returnCarsFlag) {
    try{
        pet = new PetsFound();
        pet.setParentStyleOrin(parentStyleORIN);       
        pet.setOrinNumber(orinNumber);
        pet.setEntryType(entryType);
        pet.setVendorColorCode(vendorColor);
        pet.setVendorColorDesc(vendorColorDesc); 
        pet.setVendorStyle(vendorStyle);
        pet.setImageState(imageState);   
        pet.setCompletionDate(completionDate);   
        pet.setSupplierID(supplierId);
        if(returnCarsFlag.equalsIgnoreCase("true"))
        {
        	pet.setReturnCarsFlag("Yes");
        }
        else
        {
        	pet.setReturnCarsFlag("No");
        }
        
      
      
  
    }catch(Exception e){
    	e.printStackTrace();
    }
    return pet; 
    }
    public boolean releseLockedPet(  String orin, String pepUserID,String pepFunction)throws PEPPersistencyException {
        LOGGER.info("releseLockedPet image request DAO Impl:: lockPET"); 
        boolean  isPetReleased  = false;        
			
		 Session session = null;
		 Transaction tx = null;
		 try{
		   session = sessionFactory.openSession();
		   tx = session.beginTransaction();
		   Query query = session.getNamedQuery("PetLock.deleteLockedPet");
		   query.setString("petId", orin);
		   //query.setString("pepUser", pepUserID);
		   query.setString("pepFunction", pepFunction);	
		   query.executeUpdate();
		    
		 }catch(Exception e) {
		     e.printStackTrace();
		 }
		 finally{
		     session.flush();
		     tx.commit();
		     session.close(); 
		 }
            
        return isPetReleased;
        
    }
    /**
     * Method to get the Image attribute details from database.
     *    
     * @param orin String   
     * @return imageLinkVOList List<ImageLinkVO>
     * 
     * Method added For PIM Phase 2 - Regular Item Image Link Attribute
     * Date: 05/13/2016
     * Added By: Cognizant
     */
    @Override
    public List<ImageLinkVO> getScene7ImageLinks(String orinNum) throws PEPPersistencyException {

        LOGGER.info("***Entering getScene7ImageLinks() method.");
        Session session = null;        
        List<ImageLinkVO> imageLinkVOList = new ArrayList<ImageLinkVO>();
        ImageLinkVO imageLinkVO = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();            
            final Query query =session.createSQLQuery(xqueryConstants.getScene7ImageLinks());
            if(query!=null)
            {
                query.setParameter(0, orinNum);                
                rows = query.list();
            }
            if(rows!=null)
            {
                for (final Object[] row : rows) {
                	imageLinkVO = new ImageLinkVO();
                	imageLinkVO.setOrin(row[0] == null? "" : row[0].toString());
                	imageLinkVO.setShotType(row[4] == null? "" : row[4].toString());
                	imageLinkVO.setImageURL(row[1] == null? "" : row[1].toString());
                	imageLinkVO.setSwatchURL(row[2] == null? "" : row[2].toString());
                	imageLinkVO.setViewURL(row[3] == null? "" : row[3].toString());              
                    
                    LOGGER.debug("Image Link Attribute Values -- \nORIN: " + imageLinkVO.getOrin() +
                        "\nSHOT TYPE: " + imageLinkVO.getShotType() +
                        "\nIMAGE URL: " + imageLinkVO.getImageURL() + 
                        "\nSWATCH URL: " + imageLinkVO.getSwatchURL() + 
                        "\nVIEW URL: " + imageLinkVO.getViewURL());
                    
                    imageLinkVOList.add(imageLinkVO);
                }
            }
        }
        catch(final Exception exception){
            LOGGER.error("Exception in getScene7ImageLinks() method DAO layer. -- " + exception.getMessage());
            throw new PEPPersistencyException(exception);
        }
        finally {
            session.flush();            
            session.close();
        }
        LOGGER.info("***Exiting ImageRequestDAO.getScene7ImageLinks() method.");
        return imageLinkVOList;
    }
    
    @Override
    public ArrayList<StyleInfoDetails> getGroupingInfoDetails(String groupingId) throws PEPPersistencyException{
        LOGGER.info("inside getGroupingInfoDetails() method "+groupingId);
        List<StyleInfoDetails> styleInfolist = new ArrayList<StyleInfoDetails>();
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createSQLQuery(xqueryConstants.getGroupingInfoDetails());
        query.setParameter(0, groupingId); 
        query.setFetchSize(10);
        List<Object[]> rows = query.list();
        try{
        StyleInfoDetails styleInfo  =  null ;
        for(Object[] row : rows){            
            styleInfo = new StyleInfoDetails();
            styleInfo.setOrinGrouping(row[0]!=null?row[0].toString():null);
            styleInfo.setDeptNo(row[1]!=null?row[1].toString():null);
            styleInfo.setStyleNo(row[2]!=null?row[2].toString():null); 
            styleInfo.setGroupingType(row[3]!=null?row[3].toString():null); 
            styleInfo.setVendorNo(row[4]!=null?row[4].toString():null);
            styleInfo.setVendorName(row[5]!=null?row[5].toString():null);
            styleInfo.setOmniChannelVendor(row[6]!=null?row[6].toString():null);
            styleInfo.setImageClass(row[7]!=null?row[7].toString():null);
            styleInfo.setVendorProvidedImage(row[8]!=null?row[8].toString():null);
            styleInfo.setVendorProvidedSample(row[9]!=null?row[9].toString():null);           
            styleInfolist.add(styleInfo);
        }
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	  session.close();      
        }       
       
        LOGGER.info("Exiting getStyleInfoDetails");
        return (ArrayList<StyleInfoDetails>) styleInfolist;

    }
    @Override
    public ArrayList<ImageProductDetails> getGroupingDetails(String groupingId) throws PEPPersistencyException {        
        LOGGER.info("inside getGroupingDetails() method"); 
        List<ImageProductDetails> imageProdlist = new ArrayList<ImageProductDetails>();
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();      
        Query query = session.createSQLQuery(xqueryConstants.getGroupingDetails());
        query.setParameter(0, groupingId); 
        query.setFetchSize(1);
        List<Object[]> rows = query.list();
        try{
        for(Object[] row : rows){
            ImageProductDetails imgProdDetails = new ImageProductDetails();
            if(row[1] != null){
                imgProdDetails.setProductName(row[1]!=null?row[1].toString():null);
            }else{
                imgProdDetails.setProductName("");
            }  
            final Clob groupDescClob = (Clob) ((Clob) row[2]!=null?row[2]:null);
            LOGGER.info("groupDescClob  "+groupDescClob); 
			String groupDesc = ImageUtils.clobToString(groupDescClob);
            imgProdDetails.setProductDescription(groupDesc != null ? groupDesc : "");
            imageProdlist.add(imgProdDetails);           
        }  
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	  session.close();      
        }      
        LOGGER.info("Exiting getImageInfoDetails() method");
        return (ArrayList<ImageProductDetails>)imageProdlist;
    } 
    
    /**
     * this dao method invokes sampleImagelinks
     * @param orinNo
     * @return
     */
    @Override
    public ArrayList<SamleImageDetails> getGroupingSampleImageLinks(String groupingId) throws PEPPersistencyException{
    	LOGGER.info("inside getSampleImageLinksDetails()dao impl");
    	List<SamleImageDetails> sampleImgList = new ArrayList<SamleImageDetails>();
    	Session session = this.sessionFactory.openSession();
        Query query = null;      
        query = session.createSQLQuery(xqueryConstants.getGroupingSampleImageLinks());
        query.setParameter(0, groupingId); 
        query.setFetchSize(1);
        List<Object[]> rows = query.list();
        try{
        for(Object[] row : rows){        	
        	SamleImageDetails sampleImage = new SamleImageDetails();        	
        	sampleImage.setOrinNo(row[0]!=null?row[0].toString():null);
        	sampleImage.setImageId(row[1]!=null?row[1].toString():null);
        	if(row[2]!=null){
        		sampleImage.setImageName(row[2]!=null?row[2].toString():null);
        	}else {
        		sampleImage.setImageName("");
        	}
        	if(row[3] !=null){
        		sampleImage.setOriginalImageName(row[3]!=null?row[3].toString():null);
        	}else{
        		sampleImage.setOriginalImageName("");
        	}        	
        	sampleImage.setImageShotType(row[4]!=null?row[4].toString():null);
        	if(row[5] !=null){
        			sampleImage.setLinkStatus(row[5]!=null?row[5].toString():null);
        		     		
        	}
        	if(row[7] !=null){        			
        			sampleImage.setImageStatus(row[7]!=null?row[7].toString():null);
        	}   
        	
        	sampleImgList.add(sampleImage);
        }
        }catch(Exception e){
        	LOGGER.error("inside getGroupingSampleImageLinks ",e);
        	e.printStackTrace();
        }
        finally{    
    	  session.close();
        }
    	return (ArrayList<SamleImageDetails>) sampleImgList; 
    	
    }
    
    /**
     * this is dao method to invoke pep history
     */
    @Override
    public ArrayList<PepDetailsHistory> getGroupingHistoryDetails(String groupingId) throws PEPPersistencyException{
        LOGGER.info("inside getGroupingHistoryDetails()");
        List<PepDetailsHistory> pepList = new ArrayList<PepDetailsHistory>();
        Session session = this.sessionFactory.openSession();
        Query query = null;      
        query = session.createSQLQuery(xqueryConstants.getGroupingHistoryDetails());
        query.setParameter(0,groupingId); 
        query.setFetchSize(100);
        List<Object[]> rows = query.list();
        SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");
        Date date = null;
        Date date1 = null;
        String createdOn = "";
        String updatedDate = "";     
        try{
        	Properties prop =PropertyLoader.getPropertyLoader(ImageConstants.LOAD_IMAGE_PROPERTY_FILE);
         for(Object[] row: rows){           
            PepDetailsHistory pepHist = new PepDetailsHistory();
            pepHist.setPepOrinNumber(row[0]!=null?row[0].toString():null);            
            if(null != row[6]){
            	if("01".equalsIgnoreCase(row[6].toString())){
            		pepHist.setHistoryStatus("Was Initiated");
            	}
            	if("02".equalsIgnoreCase(row[6].toString())){
            		pepHist.setHistoryStatus("Was Completed");
            	}
				if("03".equalsIgnoreCase(row[6].toString())){
					pepHist.setHistoryStatus("Was Approved");
				}
				if("04".equalsIgnoreCase(row[6].toString())){
					pepHist.setHistoryStatus("Was Failed RRD Check");
				}
				if("05".equalsIgnoreCase(row[6].toString())){
					pepHist.setHistoryStatus("Was Deactivated");
				}
				if("06".equalsIgnoreCase(row[6].toString())){
					pepHist.setHistoryStatus("Was Closed");
				}
				if("07".equalsIgnoreCase(row[6].toString())){
					pepHist.setHistoryStatus("Was Rejected By DCA");
				}
				if("08".equalsIgnoreCase(row[6].toString())){
					pepHist.setHistoryStatus("Was Ready For Review");
				}
            	
            }else{
            	pepHist.setHistoryStatus("");
            }
            pepHist.setPepUpdatedBy(row[5]!=null?row[5].toString():null);            
            if(row[2]!=null){            	
            	date = formatter.parse(row[2].toString());
            	createdOn = formatter1.format(date);
            	pepHist.setCreatedOn(createdOn!=null?createdOn.toString():null);
            }
            if(row[4]!=null){
            	date1 = formatter.parse(row[4].toString());
            	updatedDate = formatter1.format(date1);
            	pepHist.setUpdateDate(updatedDate!=null?updatedDate.toString():null);
            }            
            pepHist.setCreatedBy(row[1]!=null?row[1].toString():null);            
            if(null != row[3]){
            	String imageStateCode=row[3] == null? "" : row[3].toString();
                String imageStateDesc = prop.getProperty("Image"+imageStateCode);
                
                String imageState = imageStateDesc == null ? "": imageStateDesc.toString();
                pepHist.setUpdatedStatus(imageState);
            }else{
            	pepHist.setUpdatedStatus("");
            }
            pepList.add(pepHist);            
        }
        }catch(Exception e){
        	LOGGER.error("inside getGroupingHistoryDetails ",e);
        }finally{     
          session.close();
        }
       
        return (ArrayList<PepDetailsHistory>) pepList;       
    }
    /**
     * Method to get the Image attribute details from database.
     *    
     * @param orin String   
     * @return imageLinkVOList List<ImageLinkVO>
     * 
     * Method added For PIM Phase 2 - Regular Item Image Link Attribute
     * Date: 05/13/2016
     * Added By: Cognizant
     */
    @Override
    public List<ImageLinkVO> getGroupingScene7ImageLinks(String groupingId) throws PEPPersistencyException {

        LOGGER.info("***Entering getScene7ImageLinks() method.");
        Session session = null;        
        List<ImageLinkVO> imageLinkVOList = new ArrayList<ImageLinkVO>();
        ImageLinkVO imageLinkVO = null;
        List<Object[]> rows=null;
        final XqueryConstants xqueryConstants = new XqueryConstants();
        try {
            session = sessionFactory.openSession();            
            final Query query =session.createSQLQuery(xqueryConstants.getGroupingScene7ImageLinks());
            if(query!=null)
            {
                query.setParameter(0, groupingId);                
                rows = query.list();
            }
            if(rows!=null)
            {
                for (final Object[] row : rows) {
                	imageLinkVO = new ImageLinkVO();
                	imageLinkVO.setOrin(row[0] == null? "" : row[0].toString());
                	imageLinkVO.setShotType(row[4] == null? "" : row[4].toString());
                	imageLinkVO.setImageURL(row[1] == null? "" : row[1].toString());
                	imageLinkVO.setSwatchURL(row[2] == null? "" : row[2].toString());
                	imageLinkVO.setViewURL(row[3] == null? "" : row[3].toString());              
                    
                    LOGGER.debug("Image Link Attribute Values -- \nORIN: " + imageLinkVO.getOrin() +
                        "\nSHOT TYPE: " + imageLinkVO.getShotType() +
                        "\nIMAGE URL: " + imageLinkVO.getImageURL() + 
                        "\nSWATCH URL: " + imageLinkVO.getSwatchURL() + 
                        "\nVIEW URL: " + imageLinkVO.getViewURL());
                    
                    imageLinkVOList.add(imageLinkVO);
                }
            }
        }
        catch(final Exception e){
            LOGGER.error("Exception in getGroupingScene7ImageLinks() method DAO layer. -- ",e);
            throw new PEPPersistencyException(e);
        }
        finally {                 
            session.close();
        }
        LOGGER.info("***Exiting ImageRequestDAO.getScene7ImageLinks() method.");
        return imageLinkVOList;
    }

}
