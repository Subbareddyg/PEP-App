package com.belk.pep.controller;

import javax.naming.NamingException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.Controller;

//import java.util.logging.Logger;
import org.apache.log4j.Logger;
import com.belk.pep.exception.checked.PEPServiceException;
import com.belk.pep.model.UserObject;
import com.belk.pep.common.model.Common_BelkUser;
import com.belk.pep.common.userdata.UserData;
import com.belk.pep.constants.InternalPortalConstants;
import com.belk.pep.util.ActiveDirectoryUtil;
import com.belk.pep.util.PropertyLoader;
import com.belk.pep.delegate.InternalPortalLoginDelegate;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * The Class InternalPortalLoginController.
 */
public class InternalPortalLoginController implements Controller {
	String  errorMsg= "";
	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(InternalPortalLoginController.class.getName());
	
	/** The login delegate. */
	private InternalPortalLoginDelegate loginDelegate;

	
	/**
	 * Gets the login delegate.
	 *
	 * @return the loginDelegate
	 */
	public InternalPortalLoginDelegate getLoginDelegate() {
		return loginDelegate;
	}
	
	

	/**
	 * Sets the login delegate.
	 *
	 * @param loginDelegate the loginDelegate to set
	 */
	public void setLoginDelegate(InternalPortalLoginDelegate loginDelegate) {
		this.loginDelegate = loginDelegate;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.portlet.mvc.Controller#handleActionRequest(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
	 */
	public void handleActionRequest(ActionRequest request, ActionResponse response)
	throws Exception {
	    
		errorMsg            = "";
		boolean flag        =false;
		boolean isUserIdNull        =false;
		boolean isPasswordNull        =false;
		UserObject  userObj = null;		
		Properties prop = PropertyLoader.getPropertyLoader(InternalPortalConstants.MESS_PROP);
		LOGGER.info("Action -->"+request.getParameter("action"));
		String userID = request.getParameter(InternalPortalConstants.USER_NAME);	
		String password = request.getParameter(InternalPortalConstants.PASSWORD);		
		/* LDAP Verification */	
						
		if((null!= userID && !userID.isEmpty())&& (null != password && !password.isEmpty())){		
			LOGGER.info("USERID"+userID);	
			try{				
				flag = ActiveDirectoryUtil.authenticateUser(userID, password);
			}
			catch(NamingException ex ){
				errorMsg = prop.getProperty(InternalPortalConstants.INVALID_PWD_ENTERED);	
				LOGGER.info("exception 2");
				ex.printStackTrace();
			}
			catch(Exception e ){
				errorMsg = prop.getProperty(InternalPortalConstants.INVALID_SEC_MSG);	
				LOGGER.info("Exception ***"+e.getMessage());
				e.printStackTrace();
			}
		}
		/* VALIDATIONS */
		else{
			if(null == userID || userID.isEmpty() ){
				isUserIdNull =  true;
				try{
					LOGGER.info("userID"+userID);
					
					errorMsg =prop.getProperty(InternalPortalConstants.INVALID_ID);
				}catch(NullPointerException e){
					errorMsg = prop.getProperty(InternalPortalConstants.INVALID_ID);	
					LOGGER.info("NullPointerException USERID");
				}
				catch(Exception ex){
					errorMsg = prop.getProperty(InternalPortalConstants.INVALID_ID);;	
					LOGGER.info("Exception USERID");
				}
			}
			if(null == password ||password.isEmpty() ){
				isPasswordNull = true;
				try{
					LOGGER.info("password"+password);
					errorMsg =prop.getProperty(InternalPortalConstants.INVALID_PWD);
				}catch(NullPointerException e){
					errorMsg = prop.getProperty(InternalPortalConstants.INVALID_PWD);
					LOGGER.info("NullPointerException password");
				}
				catch(Exception ex){
					errorMsg = prop.getProperty(InternalPortalConstants.INVALID_PWD);	
					LOGGER.info("Exception password");
				}
			}
			
			if(isUserIdNull && isPasswordNull){
				try{
					//errorMsg =prop.getProperty(InternalPortalConstants.INVALID_PWD);
					errorMsg ="Please enter a valid lanid and password";
				}catch(NullPointerException e){
					errorMsg = "Please enter a valid lanid and password";
					LOGGER.info("NullPointerException password");
				}
				catch(Exception ex){
					errorMsg = "Please enter a valid lanid and password";	
					LOGGER.info("Exception password");
				}
				
			}
			
		}
		/* Flag set after LanID verification in LDAP and DB called */
		if(flag){
			
				try {
					 if(StringUtils.isNotBlank(userID))
					 {
						 userObj= (UserObject)loginDelegate.fetchUserDetails(userID);
						 if(userObj == null  ){
							 errorMsg = prop.getProperty(InternalPortalConstants.INVALID_USER_ID);
						 }else{
							 LOGGER.info("InternalPortalLoginController:::UserObj ID:::"+userObj.getLanID()+"accessType"+ userObj.getRoleAccessType()+"User type:::"+userObj.getUserType()+"UserObj Role Status:::"+userObj.getPepRoleStatus());
						 }
					 }
					
		        }
		        catch (PEPServiceException e) {
		        	LOGGER.info("DBexception");		        	
					errorMsg = prop.getProperty(InternalPortalConstants.INVALID_DB_ERR);
					e.printStackTrace();   
		        }
		        catch (Exception e) {		        	
		        	
		        	LOGGER.info("DBexception");		        	
					errorMsg = prop.getProperty(InternalPortalConstants.INVALID_DB_ERR);
					e.printStackTrace();
		        }		
			
		}		
		if (null != userObj){			
			UserData userDetails = new UserData();
					
			userDetails.setInternal(true);
			Common_BelkUser commonData = new Common_BelkUser();
			userDetails.setAccessRight(userObj.getUserType());
			userDetails.setRoleName(userObj.getPepRoleId());
			commonData.setLanId(userObj.getLanID());			
			userDetails.setBelkUser(commonData);
			response.setEvent(InternalPortalConstants.USER_OBJ, userDetails);						
		}		
		response.setRenderParameter(InternalPortalConstants.ERR_MSG, errorMsg);	
		response.setRenderParameter(InternalPortalConstants.USER_NAME, userID);					
		response.setRenderParameter(InternalPortalConstants.PASSWORD, password);
			 	        			
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.portlet.mvc.Controller#handleRenderRequest(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	public ModelAndView handleRenderRequest(RenderRequest request,
			RenderResponse response) throws Exception {
		LOGGER.info("InternalPortalLoginController:::handleRenderRequest" + request.getParameter("action"));
		ModelAndView mv = null;		
		String errMsg =request.getParameter(InternalPortalConstants.ERR_MSG);
		String userID =request.getParameter(InternalPortalConstants.USER_NAME);
		String password =request.getParameter(InternalPortalConstants.PASSWORD);
		if(null !=  errMsg && errMsg!= ""){				
			LOGGER.info("InternalPortalLoginController:::ErrMsg"+errMsg);
			LOGGER.info("InternalPortalLoginController:::userID"+userID);
			LOGGER.info("InternalPortalLoginController:::password"+password);
			mv= new ModelAndView(InternalPortalConstants.LOGIN_PAGE);			
			mv.addObject(InternalPortalConstants.ERROR_MSG,errMsg );
			
			mv.addObject("userName",userID );	
			if(null !=  password && password!= ""){				
				mv.addObject(InternalPortalConstants.PASSWORD,password );	
			}
			//Santanu 03/16/16 
			if(null !=  password && password!= "" && errMsg.equalsIgnoreCase("Please enter a valid LAN id and password")){
				LOGGER.info("Lin 211");
				mv.addObject(InternalPortalConstants.PASSWORD,"" );	
			}
			
		}		
		else{			
			LOGGER.info("login");
			mv= new ModelAndView(InternalPortalConstants.LOGIN_PAGE);		
		}
		return mv;			
	}
}
