<%@ page session="false" buffer="none"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../includePortalTaglibs.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<portal-core:constants/>
<portal-core:defineObjects/> 
<portal-core:stateBase/>
	
	
<%-- <c:set var="isLoggedIn" value="false"></c:set>
<portal-logic:if loggedIn="yes"> 
	<c:set var="isLoggedIn" value="true"></c:set>
</portal-logic:if> --%>

<%-- <c:if contentNode != "ExternalLogin">
<c:set var="isLoggedIn" value="true"></c:set>
</c:if>
 --%>
 <c:set var="isLoggedIn" value="false"></c:set>
<%-- <portal-navigation:urlGeneration contentNode = "test3" >
		<c:set var="isLoggedIn" value="true"></c:set>
</portal-navigation:urlGeneration>  --%>


<c:choose>
	<c:when test="${isLoggedIn == 'true'}">
		<jsp:include page="bannerAuth.jsp" />
	</c:when>
			<c:otherwise>
				<jsp:include page="bannerUnauth.jsp" />
			</c:otherwise>
</c:choose>
