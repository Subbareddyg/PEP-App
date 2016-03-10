<%@ page session="false" buffer="none"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../includePortalTaglibs.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	



<fmt:bundle basename="belkVendorTheme">
<portal-navigation:urlGeneration contentNode="test3">
		<c:set var="ipctargeturl"><% wpsURL.write(out); %></c:set>
</portal-navigation:urlGeneration>

<div>
		<table name="headertable" id="headertable">
				<tr>
					<td >
						
					</td>
					<td >
						<table name="innerheadertable" id="innerheadertable">
							<tr>
								<td >
									<img src=<r:url uri="dav:fs-type1/common-resources/BelkVendorStaticResources/assets/img/lib/BelkIcon.png" allowRelativeURL="true" mode="download" lateBinding="false"/> alt="Belk" >
								</td>
								<td >
									<label ><fmt:message key="header.label"/></label>
								</td>
								<td >
									
								</td>
							</tr>
						</table>
					</td>
					<td >
					
					</td>
				</tr>
				
			</table>
		<input type="hidden" value="${ipctargeturl}" id="test3url" name="test3url"/>	
	
</div>
</fmt:bundle>



