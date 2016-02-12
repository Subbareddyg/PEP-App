<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>         
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/portletBorder.css" /> 
<script type="text/javascript" src="<%=request.getContextPath()%>/js/libs/jq-plugins-adapter.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/libs/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/libs/jquery.scrollTo-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/belk.cars.login.js"></script>
<script>

(function ($, global) {

    var _hash = "!",
    noBackPlease = function () {
        global.location.href += "#";

        setTimeout(function () {
            global.location.href += "!";
        }, 50);
    };

    global.setInterval(function () {
        if (global.location.hash != _hash) {
            global.location.hash = _hash;
        }
    }, 100);

    global.onload = function () {
        noBackPlease();

        // disables backspace on page except on input fields and textarea..
        $(document.body).keydown(function (e) {
            var elm = e.target.nodeName.toLowerCase();
            if (e.which == 8 && elm !== 'input' && elm  !== 'textarea') {
                e.preventDefault();
            }
            // stopping event bubbling up the DOM tree..
            e.stopPropagation();
        });
    }

})(jQuery, window);

</script>
    
</head>
<portlet:defineObjects />
<fmt:bundle basename="internalLoginLabel">
	<body id="login_page">   
	 <portlet:actionURL var="formAction"> 
		<portlet:param name="action" value="loginSubmit"/>
	</portlet:actionURL>
	<br/> 
     </div>
        <div id="content">
            <div id="main">
              <div id="login_tabs">
				<div id="login" title="Login"   class="tab x-hide-display">		
						<form:form commandName="loginForm" method="post" action="${formAction}">	
						<fieldset>
							<ol>
								<c:if test="${errorMsg!= null}">
								<li class ="error" id="ext-gen27">
								<img src="<%=response.encodeURL(request.getContextPath()+"/images/iconWarning.gif")%>" alt="" class="icon" id="ext-gen28">
								<c:out value="${errorMsg}" /></li>
								</c:if>
										<li class="text">
											<label for="j_username"><fmt:message key="internal.login.label.lanId"/></label>
											<input type="text" id="username" name="username" value="${userName}"/>
										</li>
										<li class="text">
											<label for="j_password"><fmt:message key="internal.login.label.password"/></label>
											<input type="password" id="password" name="password" value="${password}"/>
										</li>
										<li>
											<input type="submit" class="btn" value="Login" />												
										</li>
                 				</ol>
						</fieldset>
					</form:form>
   			 	</div>      
		  </div>
       </div> 
 </fmt:bundle>


