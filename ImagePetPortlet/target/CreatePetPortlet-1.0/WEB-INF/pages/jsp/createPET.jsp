<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<head>
<link rel="stylesheet" type="text/css" 	href="<%=response.encodeURL(request.getContextPath()+ "/css/portletBorder.css")%>">
<link rel="stylesheet" type="text/css" 	href="<%=response.encodeURL(request.getContextPath()+ "/css/bootstrap.css")%>">
<link rel="stylesheet" type="text/css" 	href="<%=response.encodeURL(request.getContextPath()+ "/css/jquery.treegrid.css")%>">
<link rel="stylesheet" type="text/css" 	href="<%=response.encodeURL(request.getContextPath()+ "/css/default.min.css")%>">
<script type="text/javascript" 	src="<%=request.getContextPath()%>/js/createPet.js"></script>
<script type="text/javascript" 	src="<%=request.getContextPath()%>/js/jquery.treegrid.js"></script>
<script type="text/javascript" 	src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script type="text/javascript" 	src="<%=request.getContextPath()%>/js/treegridlocal.js"></script>
<script src="http://yandex.st/highlightjs/7.3/highlight.min.js"></script>
<script>
	hljs.initHighlightingOnLoad();
</script>

<script type="text/javascript">
	$(document).ready(function() {

		$('.tree').treegrid();
		$('.tree2').treegrid({
			expanderExpandedClass : 'icon-minus-sign',
			expanderCollapsedClass : 'icon-plus-sign'
		});
	});
function callWorkList(){
window.location = '<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/wps/portal/home/worklistDisplay" %>';
	}
	
function logout_home(){	
	
	window.location = "/wps/portal/home/InternalLogin";
	
}
</script>
	   
</head> 
<portlet:defineObjects /> 
<fmt:bundle basename="createPetlabel">   
<div class="container">   
<div id="pg">    
<div id="content">
<div id="main">
<div id="prodtype_list" >
<div align="right" style="margin-bottom: 0.5cm" >	
			<c:out value="${workflowForm.loggedInUser}"/> &nbsp;	 
			<input type="button"   style="font-weight: bold" name="logout" value="Logout" 
			    onclick=logout_home();  />	
			
		 </div>

<div class="x-panel-header">
		<fmt:message key="createpet.label.addPet"/>
	</div>
<div class="x-panel-body">
<div style="margin-top: 0.9cm;margin-left: 0.5cm;margin-bottom: 0.6cm;">
	<c:if test="${not empty errorMsg}">
		<li class ="error" id="ext-gen27"><img src="<%=response.encodeURL(request.getContextPath()+"/img/iconWarning.gif")%>" alt="" class="icon" id="ext-gen28"><c:out value="${errorMsg}" /></li>
	</c:if>	
	<c:if test="${not empty responseMsg}"><c:out value="${responseMsg}" /></li>
	</c:if>
</div>
<div class="userButtons">	
	<portlet:actionURL var="formAction">
		</portlet:actionURL>	
	<form:form commandName="workflowForm" method="post" action="${formAction}">
	<input type=hidden id="actionParameter" name="actionParameter">
	<ol>	
		<li>
			<label style="margin-left: 0.1cm;"><fmt:message key="createpet.label.orinNo"/></label>			
			<input type="text" name="orinNumber"  id="orinNumber"  value="${orinNumber}" maxlength="12" />		
			<a class="btn" href="#" onclick ="javascript:callWorkList();"><fmt:message key="createpet.label.close"/></a>
			<a class="btn" href="#" onclick ="javascript:clearFields();"><fmt:message key="createpet.label.clear"/></a>		
		<c:if test="${not empty enableMsg}">
			<a class="btn"   href='#'  onclick="javascript:createAction();" ><fmt:message key="createpet.label.createPet"/></a>	</c:if>
		<c:if test="${empty enableMsg}">
			<a class="btn"   href='#'  disabled ><fmt:message key="createpet.label.createPet"/></a>
		</c:if>
			<a class="btn" href='#' onclick="javascript:searchAction();" ><fmt:message key="createpet.label.search"/></a>	
		</li>
	</ol>	
</div>	



<div id ="div2" style="display:none"> 
</div>	
<c:if test="${not empty workflowForm.workFlowList}">
<div id ="div2" > 
            
	<table class="table tree2 table-bordered table-striped table-condensed">	
		<thead>
		<tr>
		<th>
		</th>
		<th><fmt:message key="createpet.label.orin"/></th>
		<th><fmt:message key="createpet.label.orinDescription"/></th>
		<th><fmt:message key="createpet.label.supplierName"/></th>
		<th><fmt:message key="createpet.label.color"/></th>
		<th><fmt:message key="createpet.label.size"/></th>
		</tr></thead> 
	    <c:forEach items="${workflowForm.workFlowList}" var="workFlow" >    
		<tr>
		   
		   <c:set var="sku" value="${workFlow.skuObj}" />
		   <c:if test="${not empty workFlow.skuObj}">
		    	<td align="center" style="width: 54px;  height: 24px;"><input type="checkbox" name="selectedSkus" id="selectedSkus"
								value="<c:out  value="${sku.orin} "/>" /></td>
				
				<td><c:out value="${sku.orin}" /><input type="hidden" name="SkuLevelOrin"   value="${sku.orin} "/><input type="hidden" name="entryType"  id="entryType" value="${workFlow.entryType} "/></td>
				<td><c:out value="${sku.orinDesc}" /></td>
				<td><c:out value="${sku.supplierSiteId}" /> <c:out value="${sku.supplierSiteName}" /></td>
				<td><c:out value="${sku.colorDes}" /></td>
				<td><c:out value="${sku.sizeCode}" />    <c:out value="${sku.sizeDesc}" /></td>
                              
		   </c:if>
		   
		</tr>
		</c:forEach>
		<c:set var="subcount" value="3" />
		<c:set var="countList" value="0" />	
								
				<c:forEach items="${workflowForm.workFlowList}" var="workFlow" 	varStatus="status">
					<c:if test="${not empty workFlow.orin}">
					<tr name="tablereport" class="treegrid-${countList}">
						

						<td align="center" style="width: 54px;  height: 24px;"><input type="checkbox" name="selectedItems"  id="selectedItems" value="<c:out  value="${workFlow.orin} "/>" /></td>
						<td><c:out value="${workFlow.orin}" /><input type="hidden" name="StyleLOrin"  value="${workFlow.orin} "/></td>
						<td><c:out value="${workFlow.orinDesc}" /><input type="hidden" name="entryType"  id="entryType" value="${workFlow.entryType} "/></td>
						<td><c:out value="${workFlow.supplierSiteName}" /></td>
						<td><c:out value="${workFlow.colorDes}" /></td>	
 <td></td>
										
						</tr>
					<c:if test="${workFlow.styleList != null}">	
						<c:forEach items="${workFlow.styleList}" var="style" varStatus="Stylestatus">
						<c:set var="subcount" value="${subcount +Stylestatus.count }" />					
						<tr name="treegrid2" class="treegrid-${subcount} treegrid-parent-${countList}">
						

							<td><input  style="margin-left: 1.1cm;" type="checkbox" name="selectedStyles" id="selectedStyles" value="<c:out  value="${style.orin}, ${style.colorCode} "/>" /><input type="hidden" id="entryType" name="entryType"  value="${style.entryType} "/></td>
							<td><c:out value="${style.orin}" />&nbsp;<c:out value="${style.colorCode}" /> </td>
							<td><c:out value="${style.orinDesc}" /></td>
							<td><c:out value="${style.supplierSiteId}" /><c:out value="${style.supplierSiteName}" /></td>
							<td><c:out value="${style.colorCode}" /> <c:out value="${style.colorDes}" /></td>
							<td><c:out value="${style.sizeDesc}" /></td>						
						</tr>			
						<c:if test="${style.skuList != null}">					
							<c:forEach items="${style.skuList}" var="sku" varStatus="Skustatus">
							<c:set var="subcount" value="${subcount +Stylestatus.count +Skustatus.count }" />
							<tr name="treegrid2" class="treegrid-${subcount} treegrid-parent-${countList}">


								<td><input  style="margin-left: 1.9cm;" type="checkbox" name="selectedSkus"  id="selectedSkus" value="<c:out  value="${sku.orin} "/>" /></td>
								<td><c:out value="${sku.orin}" /></td>
								<td><c:out value="${sku.orinDesc}" /></td>
								<td><c:out value="${sku.supplierSiteId}" /><c:out value="${sku.supplierSiteName}" /></td>
								<td><c:out value="${sku.colorCode}" />  <c:out value="${sku.colorDes}" /></td>
								<td><c:out value="${sku.sizeCode}" />    <c:out value="${sku.sizeDesc}" /></td>						
							</tr>	
							</c:forEach>
						</c:if>
					</c:forEach>
					</c:if>					
				</c:if>				
				<c:set var="countList" value="${countList+1}" />	
			</c:forEach>	
		</c:if>
		</form:form>	
	  </table>
	</div>	
</fmt:bundle>
		





