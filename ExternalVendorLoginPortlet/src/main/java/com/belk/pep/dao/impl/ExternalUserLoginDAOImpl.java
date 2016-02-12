
package com.belk.pep.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.belk.pep.constants.ExternalVendorLoginConstants;
import com.belk.pep.dao.ExternalUserLoginDAO;
import com.belk.pep.domain.ExternalUser;
import com.belk.pep.domain.PepRole;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.util.AESEncryptDecrypt;
import com.belk.pep.util.PasswordGenerator;
/**
 * This class responsible for handling all the DAO call to the VP Database.
 * @author AFUBXJ1
 *
 */

public class ExternalUserLoginDAOImpl implements ExternalUserLoginDAO{
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(ExternalUserLoginDAOImpl.class
        .getName());

    /** The session factory. */
    private SessionFactory sessionFactory;

   
    /**
     * Sets the session factory.
     *
     * @param sessionFactory the new session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /** The mail sender. */
    private JavaMailSender mailSender;
    
    
    
    /**
     * Gets the mail sender.
     *
     * @return the mail sender
     */
    public JavaMailSender getMailSender() {
        return mailSender;
    }

    /**
     * Sets the mail sender.
     *
     * @param mailSender the new mail sender
     */
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    /**
     * This method will get the VP user details from the VPUSER table using the Email address
     * @param emailAddress
     * @return
     * @throws SQLException
     */
    @Override
    public List<ExternalUser> getUserDetailsFromDataBaseByEmail(String emailAddress) throws PEPPersistencyException 
    {
            
            ExternalUser vpUser = null;
            Session session = null;
            Transaction tx = null;
            List<ExternalUser> listVpUser = null;
            try{
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Query query = session.getNamedQuery("ExternalUser.findUser"); 
            LOGGER.info("External query ="+query);
            if(emailAddress.toLowerCase()!=null){
            LOGGER.info("emailAddress.toLowerCase()  ="+emailAddress.toLowerCase());
            }
            String lowerCaseemailAddress = emailAddress.toLowerCase();
            LOGGER.info("emailAddress before setting ::::::::::::"+emailAddress);
            
            query.setString("1", lowerCaseemailAddress);             
            listVpUser = query.list();
            LOGGER.info("listVpUser.size ::::::::::::"+listVpUser.size());
            if(listVpUser.size()>0){
                vpUser = listVpUser.get(0);
               // LOGGER.info("Suppplier Id ID="+vpUser.getId().getSupplierId());
              //  LOGGER.info("Supplier Email="+vpUser.getId().getSupplierEmail());
                for(int i=0;i<listVpUser.size();i++){
                    LOGGER.info("Suppplier Id ID="+listVpUser.get(i).getId().getSupplierId());
                    LOGGER.info("Supplier Email="+listVpUser.get(i).getId().getSupplierEmail());
                }
            }
           
            tx.commit();
            }
            catch (Exception e) {
                LOGGER.info("Exception occured getUserDetailsFromDataBaseByEmail :"+e);
                LOGGER.info("Exception occured getUserDetailsFromDataBaseByEmail :"+e.getMessage());
            }
            finally{
            session.flush();    
            session.close();
            }
        return listVpUser;
    
    }

   

   /**
    * This method will return the PepRole from DB
    */

    @Override
    public PepRole getRoleDetailsFromDataBaseByPepRoleId(String pepRoleID)
        throws PEPPersistencyException {
        PepRole userRole= null;
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.getNamedQuery("PepRole.findRoleDetails");
        query.setString("peproleid", pepRoleID);
        List<PepRole> listUserRole = query.list();
        if(listUserRole.size()>0){
            userRole = listUserRole.get(0); 
            LOGGER.info("Role Name:"+userRole.getPepRoleId());
          }
     
        tx.commit();
        session.close();
        
        
        return userRole;
    }

   /**
    * This method will update the USer password details in DataBase
    * @param vpuser
    * @return
    * @throws PEPPersistencyException
    */
    @Override
    public boolean updateExternalUserPasswordByEmailId(ExternalUser externalUser,Properties props)
        throws PEPPersistencyException {
        LOGGER.info("This is from DAO updateExternalUserPasswordByEmailId");
        boolean isUpdated =false;
        Session session = null;
        Transaction tx = null;
        try{
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        
        PasswordGenerator pasg1= new PasswordGenerator(8);
        String resetPassword = pasg1.nextString();
       // LOGGER.info("The Random Password is====>"+resetPassword);
        //Encrypt the Password
        String encryptedPassword;
        try {
            java.util.Date today = new java.util.Date();        
            encryptedPassword = AESEncryptDecrypt.getEncryptedPassword(resetPassword);
            externalUser.setSupplierPwd(encryptedPassword);
            externalUser.setCreatedBy(externalUser.getCreatedBy());        
            externalUser.setUpdatedBy("PEPUSER");
            externalUser.setUpdatedDate(new java.sql.Timestamp(today.getTime()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
       
        session.saveOrUpdate(externalUser);
        tx.commit();
        isUpdated = true;
        //Sending mail
        String subject = "Password has been reset";
        String message = "testmail";
        
        LOGGER.info("email id= " + externalUser.getId().getSupplierEmail());
        LOGGER.info("vendor login= " + props.getProperty(ExternalVendorLoginConstants.EXTERNAL_VENDOR_LOGIN_URL));
        
        message = createEmailMessage(resetPassword,externalUser.getId().getSupplierEmail(),props.getProperty(ExternalVendorLoginConstants.EXTERNAL_VENDOR_LOGIN_URL));
        String recipientAddress = externalUser.getId().getSupplierEmail();
        
        // prints debug info
        LOGGER.info("To: " + recipientAddress);
        LOGGER.info("Subject: " + subject);
        LOGGER.info("Message: " + message);
        
        // creates a simple e-mail object
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(recipientAddress);
        emailMessage.setSubject(subject);
        emailMessage.setText(message);
        // sends the e-mail
        mailSender.send(emailMessage);
        }
        finally{
            session.flush();
            session.close();
        }
        return isUpdated;
    }

    /**
     * Creates the email message.
     *
     * @param resetPassword the reset password
     * @param email the email
     * @return the string
     */
    private String createEmailMessage(String resetPassword, String email,String externalLoginUrl) {           
       
       StringBuilder emailMessage= new StringBuilder(); 
       emailMessage.append("Hello,"+
           "\nYour password for the Belk Product Enrichment Portal (PEP) has been reset." +
            "\n\nThe new password for user name "+email+" is <"+resetPassword+"> " +
            "\nPlease make a note of it."+
        "\n\nPlease click  "+ externalLoginUrl+"   to access the system."+
        "\nThank you,"+
        "\nThe Belk Digital Content Team");

        return emailMessage.toString();
    }


}
