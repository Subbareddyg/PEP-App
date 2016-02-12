package com.belk.pep.dao.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.belk.pep.constants.ImageConstants;
import com.belk.pep.constants.XqueryConstants;
import com.belk.pep.dao.ImageRequestDAO;

import com.belk.pep.exception.checked.PEPPersistencyException;


import com.belk.pep.form.ArtDirectorRequestDetails;
import com.belk.pep.form.ContactInfoDetails;
import com.belk.pep.form.ImageProductDetails;
import com.belk.pep.form.PepDetailsHistory;
import com.belk.pep.form.SamleImageDetails;
import com.belk.pep.form.StyleInfoDetails;
import com.belk.pep.form.VendorInfoDetails;
import com.belk.pep.model.PetsFound;
import com.belk.pep.model.Sku;
import com.belk.pep.model.Style;
import com.belk.pep.model.StyleColor;
import com.belk.pep.model.WorkFlow;
import com.belk.pep.model.WorkFlowList;
import com.belk.pep.model.WorkListDisplay;

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
        Transaction tx = session.beginTransaction();
        Query query = session.createSQLQuery(xqueryConstants.getStyleInfoDetails(orinNo));
        List<Object[]> rows = query.list();
        try{
        StyleInfoDetails styleInfo  =  null ;
        for(Object[] row : rows){
            //LOGGER.info("iterate style rows");
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
        tx.commit();
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
        
        Query query = session.createSQLQuery(xqueryConstants.getImageProductDetails(orinNo));
        List<Object[]> rows = query.list();
        try{
        for(Object[] row : rows){
           // LOGGER.info("iterating rows of thee resultset");
            
            ImageProductDetails imgProdDetails = new ImageProductDetails();
            if(row[2] != null){
                //LOGGER.info("iterating rows................");
                imgProdDetails.setProductName(row[2]!=null?row[2].toString():null);
            }else{
                imgProdDetails.setProductName("");
            }
            //LOGGER.info("iterating rows done::::" +imgProdDetails.getProductName() );            
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
        query = session.createSQLQuery(xqueryConstants.getVendorInformation(orinNo));
        List<Object[]> rows = query.list();
        try{
        for(Object[] row : rows){
            //LOGGER.info("vendorinfo---->>>");
            VendorInfoDetails viDetails = new VendorInfoDetails();
            //Null check
            if(row[2] != null){
                viDetails.setCarrierAcct(row[0]!=null?row[2].toString():null);                
            }else{
               // LOGGER.info("else carrier");
                viDetails.setCarrierAcct("");                
            }if(row[3]!= null){                
                viDetails.setVendorsampleAddress(row[3]!=null?row[3].toString():null);
            }else{
                //LOGGER.info("else sample addrees");
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
        query = session.createSQLQuery(xqueryConstants.getContactInformation(orinNo));
        List<Object[]> rows = query.list();
        for(Object[] row: rows){
            LOGGER.info("iterating the resultset");
            if(null!=row[0]){
            if(ImageConstants.IMAGE_CONTACT1_TITLE.equals(row[0].toString())){              
                ContactInfoDetails contact1obj = new ContactInfoDetails();
                contact1obj.setContactTitle(row[0].toString());
                contact1obj.setVendorContactName(row[3]!=null?row[3].toString():null);
                if(row[6] != null){
                    contact1obj.setVendorContactEmail(row[6]!=null?row[6].toString():null);
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
                if(row[6] != null){
                    contact2obj.setVendorContactEmail(row[6]!=null?row[6].toString():null);
                }else{
                   
                    contact2obj.setVendorContactEmail("");
                }
                contact2obj.setVendorContactTelephone(row[4]!=null?row[4].toString():null);
                conInfoDetailList.add(contact2obj);
                
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
        query = session.createSQLQuery(xqueryConstants.getPepHistoryDetails(orinNo));
        List<Object[]> rows = query.list();
        try{
        for(Object[] row: rows){
            LOGGER.info("iterating pep history");
            PepDetailsHistory pepHist = new PepDetailsHistory();
            pepHist.setPepOrinNumber(row[0]!=null?row[0].toString():null);
            
            //pepHist.setHistoryStatus(row[1]!=null?row[1].toString():null);
            
            if(null != row[1]){
            	if("01".equalsIgnoreCase(row[1].toString())){
            		pepHist.setHistoryStatus("01-Initiated");
            	}
            	if("02".equalsIgnoreCase(row[1].toString())){
            		pepHist.setHistoryStatus("02-Completed");
            	}
				if("03".equalsIgnoreCase(row[1].toString())){
					pepHist.setHistoryStatus("03-Approved");
				}
				if("04".equalsIgnoreCase(row[1].toString())){
					pepHist.setHistoryStatus("04-Failed_RRD_Check");
				}
				if("05".equalsIgnoreCase(row[1].toString())){
					pepHist.setHistoryStatus("05-Deactivated");
				}
				if("06".equalsIgnoreCase(row[1].toString())){
					pepHist.setHistoryStatus("06-Closed");
				}
				if("07".equalsIgnoreCase(row[1].toString())){
					pepHist.setHistoryStatus("07-Rejected_By_DCA");
				}
				if("08".equalsIgnoreCase(row[1].toString())){
					pepHist.setHistoryStatus("08-Ready_For_Review");
				}
            	
            }else{
            	pepHist.setHistoryStatus("");
            }
            
            
            pepHist.setPepUpdatedBy(row[2]!=null?row[2].toString():null);
            pepHist.setCreatedOn(row[3]!=null?row[3].toString():null);
            pepHist.setUpdateDate(row[4]!=null?row[4].toString():null);
            pepHist.setPepUpdatedBy(row[5]!=null?row[5].toString():null);
            pepList.add(pepHist);
            
        }
        }catch(Exception e){
        	
        }
        tx.commit();
        session.close();
        LOGGER.info("exiting getPephistory()");
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
        query = session.createSQLQuery(xqueryConstants.getSampleImageLinks(orinNo));
        List<Object[]> rows = query.list();
        try{
        for(Object[] row : rows){        	
        	//LOGGER.info("iterating rows of sampleImageDetails");
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
        		//LOGGER.info("")
        		if(row[5].toString().equalsIgnoreCase("ReadyForReview") || row[5].toString().equalsIgnoreCase("Ready_For_Review") || row[5].toString().contains("Ready_")){
        			LOGGER.info("if condtion ReadyForReview ");
        			sampleImage.setLinkStatus("ReadyForReview");
        		}else{
        			LOGGER.info("else  condtion ReadyForReview Link ");
        			sampleImage.setLinkStatus(row[5]!=null?row[5].toString():null);
        		}        		
        	}else{
        		LOGGER.info("Link Status is null");
        		sampleImage.setLinkStatus("Initiated");
        	}
        	//sampleImage.setLinkStatus("");
        	
        	
        	if(row[6] !=null){
        		//LOGGER.info("")
        		if(row[6].toString().equalsIgnoreCase("Ready_For_Review") || row[6].toString().contains("Ready_")){
        			LOGGER.info("if condtion ReadyForReview ");
        			sampleImage.setImageStatus("ReadyForReview");
        		}else{
        			LOGGER.info("else  conidtion ReadyForReview11111 ");
        			sampleImage.setImageStatus(row[6]!=null?row[6].toString():null);
        		}        		
        	}else{
        		LOGGER.info("imageStatus as----null in DAO ");
        		sampleImage.setImageStatus("Initiated");
        	}
        	//sampleImage.setImageStatus("");
        	
        	
        	
        	
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
        		//LOGGER.info("TID is null so setting blank");
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
    	
    	LOGGER.info("exiting getSampleImageLinks() dao impl "+sampleImgList.size());
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
        query = session.createSQLQuery(xqueryConstants.getArtDirectorRequestDetails(orinNo));
        List<Object[]> rows = query.list();
        try{
        for(Object[] row: rows){
        	//LOGGER.info("iterate rows in --> getArtDirectorRequestDetails() ");
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
        //LOGGER.info("getImageMgmtDetailsByOrin....Enter");
       
        
        ArrayList pepDetails = null;
        List<WorkFlow> workFlowList = null;
       
      
        try {
            List<PetsFound> petList = getWorkListDisplayData(orinNum);
            //System.out.println("petList **********"+petList.size());
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
    public List<PetsFound> getWorkListDisplayData(String orinNum) throws SQLException, ParseException {
        LOGGER.info("This is from getWorkListDisplayData..Start" );
        List<PetsFound> petList = new ArrayList<PetsFound>();
        PetsFound pet=null;
        XqueryConstants xqueryConstants= new XqueryConstants();
        Session session = null;
        Transaction tx =  null;
            
          try{
            session = sessionFactory.openSession();
            tx = session.beginTransaction();      
           //Hibernate provides a createSQLQuery method to let you call your native SQL statement directly.   
            Query query = session.createSQLQuery(xqueryConstants.getImageManagementDetails(orinNum));  
            LOGGER.info("Image management query ******.." + query);
            // execute delete SQL statement
            List<Object[]> rows = query.list();
            if (rows != null) {
                //LOGGER.info("recordsFetched..." + rows);
                for(Object[] row : rows){   
                    //LOGGER.info("Query Executing");
                    String parentStyleORIN = row[0]!=null?row[0].toString():null;
                    
                    String orinNumber=row[1]!=null?row[1].toString():null;
                    String entryType= row[2]!=null?row[2].toString():null;
                    String vendorStyle=row[3]!=null?row[3].toString():null;
                    String vendorColor=row[4]!=null?row[4].toString():null;
                    String vendorColorDesc=row[5]!=null?row[5].toString():null;
                    String imageState=row[6]!=null?row[6].toString():null;
                    String completionDate=row[7]!=null?row[7].toString():null;
                   
                    		
                    String supplierId=row[8]!=null?row[8].toString():null;
                  
                  
                  /*  if(vendorStyle!=null){                    	
                    }else{
                    	vendorStyle ="TestStyle";
                    }*/
                   /* String imageState=row[7]!=null?row[7].toString():null;
                    String contentState=row[8]!=null?row[8].toString():null;
                    String completionDate=row[9]!=null?row[9].toString():null;  
                    String omniChannelIndicator=row[10]!=null?row[10].toString():null; */ 
                    
                    /*LOGGER.info("parentStyleORIN" + "\t" + parentStyleORIN);
                    LOGGER.info("orinNumber" + "\t" + orinNumber);
                    LOGGER.info("entryType" + "\t" + entryType);
                    LOGGER.info("vendorColor" + "\t" + vendorColor);
                    LOGGER.info("vendorColorDesc" + "\t" + vendorColorDesc);
                   
                    LOGGER.info("imageState" + "\t" + imageState);
                   
                    LOGGER.info("completionDate" + "\t" + completionDate);
                  
                    LOGGER.info("Query Executing end");*/
                    pet  = mapAdseDbPetsToPortal(parentStyleORIN,orinNumber,entryType,vendorColor,vendorColorDesc,imageState,completionDate,vendorStyle,pet,supplierId);                                    
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
       // LOGGER.info("getFormatedPetDetails  .");
        
        try{
        for(PetsFound pet:petList)
        {
            String entryType=pet.getEntryType();  
            
            LOGGER.info("entryType Image DAO IMPL*******  ."+entryType);
            if(entryType.equalsIgnoreCase("StyleColor") || entryType.equalsIgnoreCase("PackColor") )
            {
            	  //LOGGER.info("getFormatedPetDetails 1 .");
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
               // styleColor.setVendorName(vendorName);
                styleColor.setCompletionDate(completionDate);
            
               // styleColor.setContentStatus(contentState);
                styleColor.setVendorColorCode(vendorColor);
                styleColor.setVendorColorDesc(vendorColorDesc);
                styleColor.setImageStatus(imageState);
                styleColor.setSupplierID(pet.getSupplierID());
              //  styleColor.setOmniChannelVendor(ominInd);
                styleColorList.add(styleColor);//Add all the StyleColor to the  Style Color list 
                //LOGGER.info("styleColorList size.."+styleColorList.size());
              
            }
            if(entryType.equalsIgnoreCase("Style") || entryType.contains("Complex"))
            {                    
             
            	  //LOGGER.info("getFormatedPetDetails 2 Style.");
                        String orinNumber= pet.getOrinNumber();
                     
                        parentOrinNumber=pet.getParentStyleOrin();
                      
                        String colorDes=pet.getVendorColorDesc();
                        String VendorColorCode=pet.getVendorColorCode();
                        String completionDate=pet.getCompletionDate();
                  
                        String imageState=pet.getImageState(); 
                        String vendorStyle= pet.getVendorStyle();
                        //String deptId=pet.getDeptId();
                
                        WorkFlow style = new WorkFlow();
                        style.setEntryType(entryType);
                        style.setOrinNumber(orinNumber);
                        style.setParentStyleOrinNumber(parentOrinNumber);
                        style.setColorDes(colorDes);
                        style.setVendorColorCode(VendorColorCode);                      
                        style.setCompletionDate(completionDate);
                        //style.setDeptId(deptId);
                        style.setImageStatus(imageState);
                       // style.setContentStatus(contentState);
                       // style.setProductName(productName);  
                        style.setVendorStyle(vendorStyle);
                        style.setStyleColor(styleColorList);
                        style.setSupplierID(pet.getSupplierID());
                        //style.setOmniChannelVendor(ominInd);
                        styleList.add(style);//Add all the Style to the  Style  list  
                        //LOGGER.info("getFormatedPetDetails 2 Style.");
              
                }        
            
           
        }
        //LOGGER.info("styleList length="+styleList.size());
        //LOGGER.info("styleColorList length="+styleColorList.size()); 
        //Check for the Parent Child association
        for(WorkFlow style :styleList)
        {
           parentOrinNumber = style.getParentStyleOrinNumber();
           // LOGGER.info("parentOrinNumber.."+parentOrinNumber);  
           List<StyleColor> subStyleColorList = new ArrayList<StyleColor>();
           
           for(StyleColor styleColor :styleColorList)
           {
              
              childsParentOrinNumber =styleColor.getParentStyleOrinNumber();
             // LOGGER.info("childsParentOrinNumber.."+childsParentOrinNumber);
              if(parentOrinNumber.equalsIgnoreCase(childsParentOrinNumber))
              {
                  //Defect 14
                  styleColor.setVendorStyle(style.getVendorStyle());
                  subStyleColorList.add(styleColor);
              }
               
           }
           if(subStyleColorList.size()>0){
              // LOGGER.info("Size of the Color List .."+subStyleColorList.size());
               style.setStyleColor(subStyleColorList);//Add all the child Style Colors to the Parent Style
           }else{
               //LOGGER.info("This is from Else part");
               //style.setStyleColor(new ArrayList<StyleColor>()); 
           }
           styleListForWorkListDisplay.add(style);//Add  all the Styles with children Style Color to the declared worklist display list 
            
        }
        
        }catch(Exception e){
        	e.printStackTrace();
        }
       // LOGGER.info("styleColorList.."+styleColorList.size());
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
         String completionDate,String vendorStyle, PetsFound pet,String supplierId) {
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
        //LOGGER.info("This is from mapAdseDbPetsToPortal..Exit" );
      
  
    }catch(Exception e){
    	e.printStackTrace();
    }
    return pet; 
    }

}
