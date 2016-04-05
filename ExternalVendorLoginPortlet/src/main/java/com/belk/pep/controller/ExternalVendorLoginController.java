
package com.belk.pep.controller; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//import java.util.logging.Logger;
import org.apache.log4j.Logger;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.Controller;




import com.belk.pep.delegate.ExternalVendorLoginDelegate;
import com.belk.pep.domain.ExternalUser;
import com.belk.pep.domain.ExternalUserPK;
import com.belk.pep.domain.PepRole;
import com.belk.pep.exception.checked.PEPPersistencyException;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.common.model.Common_Vpuser;
import com.belk.pep.common.userdata.UserData;
import com.belk.pep.constants.ExternalVendorLoginConstants;
import com.belk.pep.util.AESEncryptDecrypt;
import com.belk.pep.util.PropertiesFileLoader;
import com.belk.pep.util.SimpleBase64Encoder;


/**
 * This  class is responsible to control the External Login 
 * @author AFUBXJ1
 *
 */

public class ExternalVendorLoginController implements Controller {
    
    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(ExternalVendorLoginController.class.getName()); 
    
    /** The login delegate. */
    private ExternalVendorLoginDelegate externalVendorLoginDelegate;

    
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.portlet.mvc.Controller#handleActionRequest(javax
     * .portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    public void handleActionRequest(ActionRequest request,
            ActionResponse response) throws Exception {
        LOGGER.info("***inside the  handle handleActionRequest method****");    
        String userName=null;
        String password=null;
            //Handle Login action
            handleLoginAction(request,response,userName,password);
            //Redirection to Forgot password page
            handlePasswordresetRedirection(request,response);
            //Handle Password reset Action            
            Properties prop = PropertiesFileLoader.getExternalLoginProperties();            
            
            
            handlePasswordResetAction(request,response,userName,password,prop);
    }
     /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.portlet.mvc.Controller#handleRenderRequest(javax
     * .portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    public ModelAndView handleRenderRequest(RenderRequest request,
            RenderResponse response) throws Exception {
        LOGGER.info("****inside the  handle handleRenderRequest method****");           
        LOGGER.info("VendorPortalLoginController:handleRenderRequest:handleRenderRequest "+request.getParameter(ExternalVendorLoginConstants.ACTION));
        Properties prop = PropertiesFileLoader.getExternalLoginProperties();
        ModelAndView mv = null;
        //Forgot password Normal flow
        if(null!=request.getParameter(ExternalVendorLoginConstants.ACTION) && (ExternalVendorLoginConstants.ACTION_FORGOTPASSWORD_SUBMIT.equals(request.getParameter(ExternalVendorLoginConstants.ACTION)))){
            LOGGER.info("VendorPortalLoginController:handleRenderRequest:forgotPassword ");
             mv= new ModelAndView(ExternalVendorLoginConstants.MODELVIEW_PASSWORD_RESET);
        }//Forgot password message handling
        else if(null!=request.getParameter(ExternalVendorLoginConstants.ACTION) && (ExternalVendorLoginConstants.ACTION_RESETPASSWORD_SUBMIT.equals(request.getParameter(ExternalVendorLoginConstants.ACTION)))){
            LOGGER.info("VendorPortalLoginController:handleRenderRequest:ResetPassword ");
             mv= new ModelAndView(ExternalVendorLoginConstants.MODELVIEW_PASSWORD_RESET);
             handleForgetPasswordMessages(mv,request,prop);
        }
        else if(null!=request.getParameter(ExternalVendorLoginConstants.ACTION) && (ExternalVendorLoginConstants.LOGIN_FAILURE.equals(request.getParameter(ExternalVendorLoginConstants.ACTION)))){
            LOGGER.info("VendorPortalLoginController:handleRenderRequest:LoginFailure ..");
             mv= new ModelAndView(ExternalVendorLoginConstants.MODELVIEW_LOGIN);
             //Error message handling
             handleLoginFailure(mv,request);
        }//Handling Login Exception
        else if(null!=request.getParameter(ExternalVendorLoginConstants.ACTION) && (ExternalVendorLoginConstants.LOGIN_EXCEPTION.equals(request.getParameter(ExternalVendorLoginConstants.ACTION)))){
            mv= new ModelAndView(ExternalVendorLoginConstants.MODELVIEW_LOGIN);
            if(null!=request.getParameter(ExternalVendorLoginConstants.J_USERNAME)){
                mv.addObject(ExternalVendorLoginConstants.USER_NAME,request.getParameter(ExternalVendorLoginConstants.J_USERNAME));
            }
            mv.addObject(ExternalVendorLoginConstants.EXCEPTION_MSG,ExternalVendorLoginConstants.EXCEPTION_MSG_SYSTEM_FAILURE);
        }// Handling Password reset Exception
        else if(null!=request.getParameter(ExternalVendorLoginConstants.ACTION) && (ExternalVendorLoginConstants.RESET_PASSWORD_EXCEPTION.equals(request.getParameter(ExternalVendorLoginConstants.ACTION)))){
            mv= new ModelAndView(ExternalVendorLoginConstants.MODELVIEW_PASSWORD_RESET);
            mv.addObject(ExternalVendorLoginConstants.EXCEPTION_MSG,ExternalVendorLoginConstants.EXCEPTION_MSG_SYSTEM_FAILURE);
        }
        else{
             LOGGER.info("VendorPortalLoginController:handleRenderRequest:login ");
             mv= new ModelAndView(ExternalVendorLoginConstants.MODELVIEW_LOGIN);           
        }
        
        String belkBestPlan = prop.getProperty(ExternalVendorLoginConstants.BELK_BEST_PLAN);
        mv.addObject(ExternalVendorLoginConstants.BELK_BEST_PLAN_URL_KEY, belkBestPlan);
        
        return mv;  
    }
    
    /**
     * This method will handle the redirection to the password reset page
     * @param request
     * @param response
     */
    private void handlePasswordresetRedirection(ActionRequest request, ActionResponse response) {
        if(null!=request.getParameter(ExternalVendorLoginConstants.ACTION) && (ExternalVendorLoginConstants.ACTION_FORGOTPASSWORD_SUBMIT.equals(request.getParameter(ExternalVendorLoginConstants.ACTION))))
        {
            response.setRenderParameter(ExternalVendorLoginConstants.ACTION,ExternalVendorLoginConstants.ACTION_FORGOTPASSWORD_SUBMIT);
            LOGGER.info("VendorPortalLoginController:handleActionRequest:Forgot Password ");
            
        }
    }
    
    /**
     * This method will handle the action from  PasswordReset page
     * @param request
     * @param response
     * @param userName
     * @param password
     */
    private void handlePasswordResetAction(ActionRequest request,
        ActionResponse response, String userName, String password,Properties prop) {
        try
        {
        if(null!=request.getParameter(ExternalVendorLoginConstants.ACTION) && (ExternalVendorLoginConstants.ACTION_RESETPASSWORD_SUBMIT.equals(request.getParameter(ExternalVendorLoginConstants.ACTION)))){
           
            response.setRenderParameter(ExternalVendorLoginConstants.ACTION,ExternalVendorLoginConstants.ACTION_RESETPASSWORD_SUBMIT);
             LOGGER.info("VendorPortalLoginController:handleActionRequest:resetPassword **");
             boolean validEmail =false;
             ExternalUser rPvpUser = null;
             List<ExternalUser> listVpUser = null;
             String rpEmailAddress=null;
             if(!request.getParameter(ExternalVendorLoginConstants.EMAIL_ADDRESS_PRARAM).isEmpty()){
                 rpEmailAddress = request.getParameter(ExternalVendorLoginConstants.EMAIL_ADDRESS_PRARAM).trim();
             }
                 if(null!=rpEmailAddress && rpEmailAddress.length()>0){
                    //Call DB to validate the password
                     
                     //rPvpUser = externalVendorLoginDelegate.getUserDetailsFromDataBaseByEmail(rpEmailAddress);
                     //MultiSupplierID Changes start
                     listVpUser = externalVendorLoginDelegate.getUserDetailsFromDataBaseByEmail(rpEmailAddress);
                     if(null!=listVpUser && listVpUser.size()>0){
                         
                         for(int i=0;i<listVpUser.size();i++){
                             LOGGER.info("Suppplier Id ID="+listVpUser.get(i).getId().getSupplierId());
                             LOGGER.info("Supplier Email="+listVpUser.get(i).getId().getSupplierEmail());
                             LOGGER.info("Password="+listVpUser.get(i).getSupplierPwd());
                             if(null!=listVpUser.get(i).getSupplierPwd() && listVpUser.get(i).getSupplierPwd().length()>0){
                                 LOGGER.info("Found VPUser with Password");
                                 rPvpUser = listVpUser.get(i);
                                 break;
                             }
                         }
                     }
                   //MultiSupplierID Changes end
                     boolean isPasswordUpdated = false;
                     if(null!=rPvpUser){
                         LOGGER.info("VendorPortalLoginController:handleActionRequest:Email Address is available");
                         validEmail = true;
                         isPasswordUpdated = externalVendorLoginDelegate.updateExternalUserPasswordByEmailId(rPvpUser,prop);
                     }
                     
                    
                     
                     if(validEmail && isPasswordUpdated){
                         response.setRenderParameter(ExternalVendorLoginConstants.FORGOT_PASSWORD_MESSAGE,ExternalVendorLoginConstants.FORGOT_PASSWORD_SUCCESS);  
                     }else{
                         response.setRenderParameter(ExternalVendorLoginConstants.FORGOT_PASSWORD_MESSAGE,ExternalVendorLoginConstants.FORGOT_PASSWORD_INVALID); 
                     }
                 }else {
                     response.setRenderParameter(ExternalVendorLoginConstants.FORGOT_PASSWORD_MESSAGE,ExternalVendorLoginConstants.FORGOT_PASSWORD_EMPTY);
                 }
        }
        }catch(PEPServiceException serviceException)
       
        { serviceException.printStackTrace();
            LOGGER.info("VendorPortalLoginController:handleActionRequest:Service Exception Occured."+serviceException.getMessage());
            response.setRenderParameter(ExternalVendorLoginConstants.ACTION, ExternalVendorLoginConstants.RESET_PASSWORD_EXCEPTION);
            response.setRenderParameter(ExternalVendorLoginConstants.PEPSERVICE_EXCEPTION_PARAMETER, ExternalVendorLoginConstants.SYSTEM_FAILURE_MSG);
          
        }
        catch(PEPPersistencyException persistencyException)
        { persistencyException.printStackTrace();
            LOGGER.info("VendorPortalLoginController:handleActionRequest:DAO Exception Occured."+persistencyException.getMessage());
            response.setRenderParameter(ExternalVendorLoginConstants.ACTION, ExternalVendorLoginConstants.RESET_PASSWORD_EXCEPTION);
            response.setRenderParameter(ExternalVendorLoginConstants.PEPSISTANCE_EXCEPTION_PARAMETER, ExternalVendorLoginConstants.SYSTEM_FAILURE_MSG);
              
        }
        catch (Exception e) {
           LOGGER.info("Exception Happened actionaction handler..."+e.getMessage());
           e.printStackTrace();
           LOGGER.info("VendorPortalLoginController:handleActionRequest:DAO Exception Occured."+e.getMessage());
           response.setRenderParameter(ExternalVendorLoginConstants.ACTION, ExternalVendorLoginConstants.RESET_PASSWORD_EXCEPTION);
           response.setRenderParameter(ExternalVendorLoginConstants.PEPSISTANCE_EXCEPTION_PARAMETER, ExternalVendorLoginConstants.SYSTEM_FAILURE_MSG);
        }
        
    }
    
    /**
     * This method will handle the action submit from the Login page
     * @param request
     * @param response
     * @param userName
     * @param password
     */
    private void handleLoginAction(ActionRequest request,
        ActionResponse response, String userName, String password) {
        try
        {
            Properties prop = PropertiesFileLoader.getExternalLoginProperties();
            LOGGER.info("ExternalVendorLoginController:handleActionRequest:handleActionRequest="+request.getParameter(ExternalVendorLoginConstants.ACTION));
            //Login Flow
            if(ExternalVendorLoginConstants.ACTION_LOGIN_SUBMIT.equalsIgnoreCase(request.getParameter(ExternalVendorLoginConstants.ACTION)))
            {
                LOGGER.info("VendorPortalLoginController:handleActionRequest:user name ="+request.getParameter(ExternalVendorLoginConstants.J_USERNAME));
                LOGGER.info("VendorPortalLoginController:handleActionRequest:password="+request.getParameter(ExternalVendorLoginConstants.J_PASSWORD));
                ExternalUser externalUser = null;
                String roleName = null;
                String accessRight = null;
                String userPwd = null;
                List<ExternalUser> listVpUser = null;
                userName=request.getParameter(ExternalVendorLoginConstants.J_USERNAME);
                password=request.getParameter(ExternalVendorLoginConstants.J_PASSWORD);
                    boolean loginDataReady=false;
                    if(null!=request.getParameter(ExternalVendorLoginConstants.J_USERNAME) && request.getParameter(ExternalVendorLoginConstants.J_USERNAME).length()>0)
                    {
                        userName=request.getParameter(ExternalVendorLoginConstants.J_USERNAME);
                        if(null!=request.getParameter(ExternalVendorLoginConstants.J_PASSWORD) && request.getParameter(ExternalVendorLoginConstants.J_PASSWORD).length()>0)
                        {
                            password = request.getParameter(ExternalVendorLoginConstants.J_PASSWORD);
                            //DB hit flow starting.
                            LOGGER.info("ExternalVendorLoginControllerr:handleActionRequest:This is from DB Connection starting....");
                           // externalUser = externalVendorLoginDelegate.getUserDetailsFromDataBaseByEmail(userName);
                          //MultiSupplierID Changes start
                            listVpUser = externalVendorLoginDelegate.getUserDetailsFromDataBaseByEmail(userName);
                            if(null!=listVpUser && listVpUser.size()>0){
                               
                                for(int i=0;i<listVpUser.size();i++){
                                    LOGGER.info("Suppplier Id ID="+listVpUser.get(i).getId().getSupplierId());
                                    LOGGER.info("Supplier Email="+listVpUser.get(i).getId().getSupplierEmail());
                                    String email = listVpUser.get(i).getId().getSupplierEmail();
                                    userPwd = listVpUser.get(i).getSupplierPwd();
                                    LOGGER.info("Password="+userPwd);
                                    
                                    if(null!=email && !email.trim().equalsIgnoreCase("") && email.length()>0){
                                        LOGGER.info("Found VPUser with Password");
                                        externalUser = listVpUser.get(i);
                                        break;
                                    }
                                }
                            }
                          //MultiSupplierID Changes end
                           
                            if(null!=externalUser)
                            {//Converting the Encrypted password to normal password using Decryption.
                            if( userPwd != null && !userPwd.trim().equalsIgnoreCase("") && password.equals(getDecryptedPassword(externalUser.getSupplierPwd())))
                            {
                                LOGGER.info("ExternalVendorLoginController:handleActionRequest:The password="+externalUser.getSupplierPwd());
                                if(null!=externalUser.getPepRole() && null!=externalUser.getPepRole().getPepRoleId())
                                {
                                
                                        roleName=externalUser.getPepRole().getPepRoleId();
                                        //Getting the role name and others from the PEPRole Table
                                        LOGGER.info("ExternalVendorLoginController:handleActionRequest:Role Name==="+roleName);
                                        //Get ROLE_ACCESS_RIGHT_ID from the Table ROLE_ACCESS_MAPPING
                                            PepRole pepRole =null;
                                            pepRole=externalVendorLoginDelegate.getRoleDetailsFromDataBaseByPepRoleId(roleName);
                                            if(null!=pepRole)
                                            accessRight = pepRole.getAccessType();
                                            LOGGER.info("ExternalVendorLoginController:handleActionRequest:AccessRight= "+accessRight);
                                            LOGGER.info("ExternalVendorLoginController:handleActionRequest:UserType= "+pepRole.getUserType());
                                            loginDataReady = true;
                                       
                                    }
                            }else{
                                
                                response.setRenderParameter(ExternalVendorLoginConstants.LOGIN_FAILED_PASSWORD_INVALID, prop.getProperty(ExternalVendorLoginConstants.PROPERTIES_KEY_MSG_INVALID_PASSWORD));
                                response.setRenderParameter(ExternalVendorLoginConstants.J_USERNAME, userName);
                            }
                            
                            }else{
                                LOGGER.info("Setting the user name is invalid.");
                                response.setRenderParameter(ExternalVendorLoginConstants.LOGIN_FAILED_USER_NAME_INVALID, prop.getProperty(ExternalVendorLoginConstants.PROPERTIES_KEY_MSG_INVALID_USERNAME));
                            }
                            //DB hit flow ending.
                        }else{
                            LOGGER.info("ExternalVendorLoginController:handleActionRequest:Password is empty.");
                            response.setRenderParameter(ExternalVendorLoginConstants.LOGIN_FAILED_NO_PASSWORD, prop.getProperty(ExternalVendorLoginConstants.PROPERTIES_KEY_MSG_NO_PASSWORD));    
                            response.setRenderParameter(ExternalVendorLoginConstants.J_USERNAME, userName);    
                        }
                    }else{
                        LOGGER.info("ExternalVendorLoginController:handleActionRequest:Email is empty.");
                        response.setRenderParameter(ExternalVendorLoginConstants.LOGIN_FAILED_NO_USER_NAME, prop.getProperty(ExternalVendorLoginConstants.PROPERTIES_KEY_MSG_NO_USERNAME));   
                        if(null==request.getParameter(ExternalVendorLoginConstants.J_PASSWORD) || (null!=request.getParameter(ExternalVendorLoginConstants.J_PASSWORD) && !(request.getParameter(ExternalVendorLoginConstants.J_PASSWORD).length()>0))){
                            LOGGER.info("ExternalVendorLoginController:handleActionRequest:Password is empty.");
                            response.setRenderParameter(ExternalVendorLoginConstants.LOGIN_FAILED_NO_PASSWORD, prop.getProperty(ExternalVendorLoginConstants.PROPERTIES_KEY_MSG_NO_PASSWORD));    
                        }
                    }
                    if(loginDataReady){
                       
                        UserData commonUserdata= new UserData();
                        commonUserdata.setExternal(true);
                        commonUserdata.setRoleName(roleName);
                        commonUserdata.setAccessRight(accessRight);
                        Common_Vpuser common_Vpuser = new Common_Vpuser();
                        if(externalUser.getId()!=null){
                            LOGGER.info("externalUser.getId().."+externalUser.getId());
                            LOGGER.info("externalUser.getId().getSupplierEmail().."+externalUser.getId().getSupplierEmail());
                            LOGGER.info("externalUser.getId().getSupplierId().."+externalUser.getId().getSupplierId());
                            ExternalUserPK externalUserPrimaryKey=externalUser.getId();
                            String supplierEmail= externalUserPrimaryKey.getSupplierEmail();
                            String supplierId=externalUserPrimaryKey.getSupplierId();
                            String pepUserID=externalUserPrimaryKey.getSupplierEmail();
                            if(StringUtils.isNotBlank(supplierEmail))
                            {
                                common_Vpuser.setUserEmailAddress(supplierEmail);
                            }
                            
                            if(StringUtils.isNotBlank(supplierId))
                            {
                                common_Vpuser.setUserid(supplierId);
                            }
                            if(StringUtils.isNotBlank(pepUserID))
                            {
                                common_Vpuser.setPepUserID(pepUserID);
                            }  
                            
                            //MultiSupplierID Changes start
                            if(null!=listVpUser && listVpUser.size()>0){
                               List<String> supplierIdList = new ArrayList<String>();
                                for(int i=0;i<listVpUser.size();i++){
                                    LOGGER.info("Suppplier Id ID="+listVpUser.get(i).getId().getSupplierId());
                                    if(StringUtils.isNotBlank(listVpUser.get(i).getId().getSupplierId())){
                                        supplierIdList.add(listVpUser.get(i).getId().getSupplierId());
                                    }
                                }
                                LOGGER.info("supplierIdList size="+supplierIdList.size());
                                //Setting the Supplier ids list to common_VPUser
                                common_Vpuser.setSupplierIdsList(supplierIdList);
                            }
                          //MultiSupplierID Changes end
                            
                        }                       
                        
                        commonUserdata.setVpUser(common_Vpuser);
                        //Set the externalvendor login data in the userDataOBJ for IPC ,so the worklist display portlet retrieves the userDataOBJ
                        response.setEvent("userDataOBJ", commonUserdata);
                    }else{
                        response.setRenderParameter(ExternalVendorLoginConstants.ACTION, ExternalVendorLoginConstants.LOGIN_FAILURE);
                    }
                    
            }
            
        }catch(PEPServiceException serviceException)
        {serviceException.printStackTrace();
            LOGGER.info("VendorPortalLoginController:handleActionRequest:Service Exception Occured."+serviceException.getMessage());
            response.setRenderParameter(ExternalVendorLoginConstants.ACTION, ExternalVendorLoginConstants.LOGIN_EXCEPTION);
            response.setRenderParameter(ExternalVendorLoginConstants.PEPSERVICE_EXCEPTION_PARAMETER, ExternalVendorLoginConstants.SYSTEM_FAILURE_MSG);
            if(null!=userName){
                response.setRenderParameter(ExternalVendorLoginConstants.J_USERNAME, userName);  
            }
            if(null!=password){   
                response.setRenderParameter(ExternalVendorLoginConstants.J_PASSWORD, password);
            }
        }
        catch(PEPPersistencyException persistencyException)
        {persistencyException.printStackTrace();
            LOGGER.info("VendorPortalLoginController:handleActionRequest:DAO Exception Occured."+persistencyException.getMessage());
            response.setRenderParameter(ExternalVendorLoginConstants.ACTION, ExternalVendorLoginConstants.LOGIN_EXCEPTION);
            response.setRenderParameter(ExternalVendorLoginConstants.PEPSISTANCE_EXCEPTION_PARAMETER, ExternalVendorLoginConstants.SYSTEM_FAILURE_MSG);
            if(null!=userName){
                response.setRenderParameter(ExternalVendorLoginConstants.J_USERNAME, userName);  
            }
            if(null!=password){   
                response.setRenderParameter(ExternalVendorLoginConstants.J_PASSWORD, password);
            }    
        }
        catch(IOException ioexception){
            ioexception.printStackTrace();
            LOGGER.info("VendorPortalLoginController:handleActionRequest:IO Exception Occured."+ioexception.getMessage());
            response.setRenderParameter(ExternalVendorLoginConstants.ACTION, ExternalVendorLoginConstants.LOGIN_EXCEPTION);
            response.setRenderParameter(ExternalVendorLoginConstants.IO_EXCEPTION_PARAMETER, ExternalVendorLoginConstants.SYSTEM_FAILURE_MSG);
            if(null!=userName){
                response.setRenderParameter(ExternalVendorLoginConstants.J_USERNAME, userName);  
            }
            if(null!=password){   
                response.setRenderParameter(ExternalVendorLoginConstants.J_PASSWORD, password);
            }
        }
           
        catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("VendorPortalLoginController:handleActionRequest:Exception Occured."+e.getMessage());
            response.setRenderParameter(ExternalVendorLoginConstants.ACTION, ExternalVendorLoginConstants.LOGIN_EXCEPTION);
            response.setRenderParameter(ExternalVendorLoginConstants.IO_EXCEPTION_PARAMETER, ExternalVendorLoginConstants.SYSTEM_FAILURE_MSG);
            if(null!=userName){
                response.setRenderParameter(ExternalVendorLoginConstants.J_USERNAME, userName);  
            }
            if(null!=password){   
                response.setRenderParameter(ExternalVendorLoginConstants.J_PASSWORD, password);
            }
        }
        
    }
/**
 * This method will return decrypted password
 * @param userPassword
 * @return
 * @throws Exception
 */
    private String getDecryptedPassword(String userPassword) throws Exception {
        String decryptedPassword= AESEncryptDecrypt.getDecryptedPassword(SimpleBase64Encoder.decode(userPassword));
        LOGGER.info("The Decrypted password is=="+decryptedPassword);
        return decryptedPassword;
    }

   
    /**
     * This method will set the proper messages to the model view after failure happens from Login flow
     * @param mv
     * @param request
     */
    private void handleLoginFailure(ModelAndView mv, RenderRequest request) {
        
        if(null!=request.getParameter(ExternalVendorLoginConstants.LOGIN_FAILED_NO_USER_NAME) && request.getParameter(ExternalVendorLoginConstants.LOGIN_FAILED_NO_USER_NAME).length()>0){
            mv.addObject(ExternalVendorLoginConstants.LOGIN_FAILED_NO_USER_NAME, request.getParameter(ExternalVendorLoginConstants.LOGIN_FAILED_NO_USER_NAME));
        }
        if(null!=request.getParameter(ExternalVendorLoginConstants.LOGIN_FAILED_NO_PASSWORD) && request.getParameter(ExternalVendorLoginConstants.LOGIN_FAILED_NO_PASSWORD).length()>0){
            mv.addObject(ExternalVendorLoginConstants.USER_NAME,request.getParameter(ExternalVendorLoginConstants.J_USERNAME));
            mv.addObject(ExternalVendorLoginConstants.LOGIN_FAILED_NO_PASSWORD, request.getParameter(ExternalVendorLoginConstants.LOGIN_FAILED_NO_PASSWORD));
        }
        if(null!=request.getParameter(ExternalVendorLoginConstants.LOGIN_FAILED_USER_NAME_INVALID) && request.getParameter(ExternalVendorLoginConstants.LOGIN_FAILED_USER_NAME_INVALID).length()>0){
            mv.addObject(ExternalVendorLoginConstants.LOGIN_FAILED_USER_NAME_INVALID, request.getParameter(ExternalVendorLoginConstants.LOGIN_FAILED_USER_NAME_INVALID));
        }
        if(null!=request.getParameter(ExternalVendorLoginConstants.LOGIN_FAILED_PASSWORD_INVALID) && request.getParameter(ExternalVendorLoginConstants.LOGIN_FAILED_PASSWORD_INVALID).length()>0){
            mv.addObject(ExternalVendorLoginConstants.LOGIN_FAILED_PASSWORD_INVALID, request.getParameter(ExternalVendorLoginConstants.LOGIN_FAILED_PASSWORD_INVALID));
            mv.addObject(ExternalVendorLoginConstants.USER_NAME,request.getParameter(ExternalVendorLoginConstants.J_USERNAME));
        }
        
    }
    
    /**
     * This method will handle the messages after Forgot password flow
     * @param mv
     * @param request
     * @param prop
     */
    private void handleForgetPasswordMessages(ModelAndView mv,
        RenderRequest request, Properties prop) {
        LOGGER.info("VendorPortalLoginController:handleForgetPasswordMessages:The Forgot password message is=="+request.getParameter(ExternalVendorLoginConstants.FORGOT_PASSWORD_MESSAGE));
        if(null!=request.getParameter(ExternalVendorLoginConstants.FORGOT_PASSWORD_MESSAGE) && request.getParameter(ExternalVendorLoginConstants.FORGOT_PASSWORD_MESSAGE).length()>0){
            if(ExternalVendorLoginConstants.FORGOT_PASSWORD_SUCCESS.equalsIgnoreCase(request.getParameter(ExternalVendorLoginConstants.FORGOT_PASSWORD_MESSAGE))){
                mv.addObject(ExternalVendorLoginConstants.RESET_SUCCESS,prop.getProperty(ExternalVendorLoginConstants.PROPERTIES_KEY_RESET_SUCCESS_MSG)) ;
            }
            if(ExternalVendorLoginConstants.FORGOT_PASSWORD_INVALID.equalsIgnoreCase(request.getParameter(ExternalVendorLoginConstants.FORGOT_PASSWORD_MESSAGE))){
                mv.addObject(ExternalVendorLoginConstants.RESET_USER_INVALID, prop.getProperty(ExternalVendorLoginConstants.PROPERTIES_KEY_RESET_USER_INVALID_MSG)) ;
            }
            if(ExternalVendorLoginConstants.FORGOT_PASSWORD_EMPTY.equalsIgnoreCase(request.getParameter(ExternalVendorLoginConstants.FORGOT_PASSWORD_MESSAGE))){
                mv.addObject(ExternalVendorLoginConstants.RESET_EMAIL_EMPTY, prop.getProperty(ExternalVendorLoginConstants.PROPERTIES_KEY_RESET_EMAIL_EMPTY_MSG)) ;
            }
        } 
        
    }
    
    /**
     * Getter for externalVendorLoginDelegate
     * @return
     */
    public ExternalVendorLoginDelegate getExternalVendorLoginDelegate() {
        return externalVendorLoginDelegate;
    }
    
    /**
     * Setter for externalVendorLoginDelegate
     * @param externalVendorLoginDelegate
     */
    public void setExternalVendorLoginDelegate(
        ExternalVendorLoginDelegate externalVendorLoginDelegate) {
        this.externalVendorLoginDelegate = externalVendorLoginDelegate;
    }
    
    


}
