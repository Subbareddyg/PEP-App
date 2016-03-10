<%@ page session="false" buffer="none"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../includePortalTaglibs.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/tld/engine.tld" prefix="wps" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

 	
<fmt:bundle basename="belkVendorTheme">
<portal-navigation:urlGeneration contentNode="test3">
		<c:set var="ipctargeturl"><% wpsURL.write(out); %></c:set>
</portal-navigation:urlGeneration>
<%-- <div>
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
									<div class="headerrightlinks">
									<ul>
									<li><%=request.getUserPrincipal().getName()%></li>
									<li>|</li>
									<li><a href="#" onclick="themelogout()"><fmt:message key="header.label.logout"/></a></li>
									</ul>
									
									</div>
								</td>
							</tr>
						</table>
					</td>
					<td >
					
					</td>
				</tr>
				
			</table>
			<input type="hidden" value="${ipctargeturl}" id="test3url" name="test3url"/>
			
	<form id="sign-out-form" name="sign-out-form" role="form" action='<portal-navigation:url command="LogoutUser"/>' method="post">
		<input type="hidden" name="username" value="user01" />
	</form>
</div> --%>

 <div id="pg123" style="width: 950px;margin: 0 auto;font-family: Tahoma;">
        <div id="hd">

<a href="/cars/" title="C.A.R.S."><img src="/cars/images/logo.png" alt="Belk Logo" /></a>
Content Acquisition Request System
<br/>
<img src="/cars/images/s.gif" width="650" height="2"/>

<ul id="primary-nav" class="menuList">

          <li>
                        <a href="/cars/logout.jsp" title="Admin" >Admin</a>

          </li>
          <li style="border-right:1px solid transparent;">
                        <a href="/cars/logout.jsp" title="Logout" >Logout</a>

          </li>
          </ul>
</div>



    </div>


</fmt:bundle>



