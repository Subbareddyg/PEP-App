<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<portlet:defineObjects />
    <div id="pgforgotpassword" style="width:937px;margin-left: 2px;">
  		<div >
       		<portlet:actionURL var="forgotPasswordAction"> 
				<portlet:param name="action" value="resetPassword"/>
			</portlet:actionURL>
       	</div>
        <div id="content">
            <div id="main">                
                <div id="forgot_password_pnl"><div id="forgot_password_content" class="pnl_content x-hidden">	
				 <form:form commandName="forgotPasswordForm" method="post" action="${forgotPasswordAction}">
					<fieldset>
						<legend></legend>
						<ol>
						<c:if test="${not empty resetUsernameInvalid}">
								<li class="text"><img src="<%=response.encodeURL(request.getContextPath()+"/images/iconWarning.gif")%>" style="padding-right: 5px;padding-bottom: 5px;" /><c:out value="${resetUsernameInvalid}"/></li>
						</c:if>
						<c:if test="${not empty exceptionMessage}">
								<li class="text"><img src="<%=response.encodeURL(request.getContextPath()+"/images/iconWarning.gif")%>" style="padding-right: 5px;padding-bottom: 5px;" /><c:out value="${exceptionMessage}"/></li>
						</c:if>
							<li class="text">
								<label for="emailAddress" class="passwordresetlabel">
									Email Address:
								</label>
								<input id="emailAddress" name="emailAddress" type="text" value=""/>
									<c:if test="${not empty resetEmailEmpty}">
									<label style="float:none;font-weight:normal">
									<c:out value="${resetEmailEmpty}"/>
									</label>
									</c:if>
							</li>
							<li class="buttons" style="padding-left:160px;">
								<input type="submit" class="btn" name="forgotPasswordfield"
									value="Reset Password" />
							</li>
							<c:if test="${not empty resetSuccess}">
							<br/><br/>
								<li style="padding-bottom: 10px; padding-left: 70px;"><c:out value="${resetSuccess}"/></li>
							</c:if>
						</ol>
					</fieldset>
				</form:form>
			  </div>
			 </div>
            </div>
        </div>        

<script type="text/javascript">
$(document).ready(function(){
	// panels
	new Ext.Panel({
        title:'Forgot Password',
        collapsible:true,
		frame:true,
        applyTo:'forgot_password_pnl',
		contentEl:'forgot_password_content',
		height:'auto'
    });
    $('#forgotPasswordForm').submit(function(){
		var emailId=$('#emailAddress').val();
				emailId = emailId.replace(/^\s*|\s*|\s*$/g,'');
			$('#emailAddress').val(emailId);
	});
});
</script>

