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
<% 
String uName = "";
boolean isLoggedInb = false;

com.ibm.wps.model.ModelUtil util = com.ibm.wps.model.ModelUtil.from((javax.servlet.ServletRequest)request);

                  com.ibm.portal.navigation.NavigationNode node = (com.ibm.portal.navigation.NavigationNode) util.getNavigationSelectionModel().getSelectedNode();

                  if(node != null && node.getContentNode() != null && 

                              node.getContentNode().getObjectID().getUniqueName() != null) 
                  {

                        uName= node.getContentNode().getObjectID().getUniqueName().trim();

                  }
                  
                 if(uName.equalsIgnoreCase("worklistDisplay")){
                   isLoggedInb = true;
                 }
                 System.out.println("Current page:"+uName) ;
                
                 


 %>


 <c:set var="isLoggedIn" value="<%=isLoggedInb%>"></c:set>



<c:choose>
	<c:when test="${isLoggedIn == 'true'}">
		<jsp:include page="bannerAuth.jsp" />
	</c:when>
			<c:otherwise>
				<jsp:include page="bannerUnauth.jsp" />
			</c:otherwise>
</c:choose>
