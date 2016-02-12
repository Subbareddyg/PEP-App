<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	
<script type="text/javascript">
var sTime=new Date();
	var path = "./";
</script>
<portlet:defineObjects />
<fmt:bundle basename="belkLoginPortlets">
   		 <div>
       		<portlet:actionURL var="formAction"> 
				<portlet:param name="action" value="loginSubmit"/>
			</portlet:actionURL>
       	</div>
 		<div id="content">
            <div id="main">
              <div id="login_tabs">
				<div id="login"  class="tab x-hide-display">
				<form:form commandName="loginForm" method="post" action="${formAction}">
							<fieldset>
							<ol>
								<c:if test="${not empty loginFailureUserNameinvalid}">
								<li class="text"><img src="<%=response.encodeURL(request.getContextPath()+"/images/iconWarning.gif")%>" class="messagedisplay"/><c:out value="${loginFailureUserNameinvalid}"/></li>
								</c:if>
								<c:if test="${not empty loginFailurePasswordinvalid}">
								<li class="text"><img src="<%=response.encodeURL(request.getContextPath()+"/images/iconWarning.gif")%>" class="messagedisplay"/><c:out value="${loginFailurePasswordinvalid}"/></li>
								</c:if>
								<c:if test="${not empty loginFailureNoUserName}">
								<li class="text"><img src="<%=response.encodeURL(request.getContextPath()+"/images/iconWarning.gif")%>" class="messagedisplay"/><c:out value="${loginFailureNoUserName}"/></li>
								</c:if>
								<c:if test="${not empty loginFailureNoPassword}">
								<li class="text"><img src="<%=response.encodeURL(request.getContextPath()+"/images/iconWarning.gif")%>" class="messagedisplay"/><c:out value="${loginFailureNoPassword}"/></li>
								</c:if>
								<c:if test="${not empty exceptionMessage}">
								<li class="text"><img src="<%=response.encodeURL(request.getContextPath()+"/images/iconWarning.gif")%>" class="messagedisplay"/><c:out value="${exceptionMessage}"/></li>
								</c:if>
							<li class="text">
								<label for="j_username"><fmt:message key="external.login.label.email"/></label>
								<input type="text" id="j_username" name="j_username" value="${userName}" />
							</li>
							<li class="text">
								<label for="j_password">Password:</label>
								<input type="password" id="j_password" name="j_password" value="${password}"/>
							</li>
							<li>
							<input type="submit" value="Login" class="btn" />
							<a href='<portlet:renderURL> <portlet:param name="action" value="forgotPassword"/></portlet:renderURL>'><fmt:message key="external.login.label.forgot.password"/></a>
						   </li>
	                      </ol>
						 </fieldset>
					 </form:form>
				</div>
				</div>
            </div>
        </div>
</fmt:bundle>



