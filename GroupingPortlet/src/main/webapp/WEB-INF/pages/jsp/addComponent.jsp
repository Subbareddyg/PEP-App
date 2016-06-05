<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<fmt:setBundle basename="grouping" />
<div align="right" style="margin-bottom: 0.5cm" >	
			<c:out value="${LAN_ID}"/> &nbsp;	 
			<input type="button"   style="font-weight: bold" name="logout" value="Logout" 
			    onclick="grouping_logOut('<c:out value="${LAN_ID}"/>'); " />	
			
		 </div>
<div  class="cars_panel x-hidden">
	<div class="x-panel-header">
		<b>Grouping Details</b>
	</div>
	
	<div class="x-panel-body">
		<div class="group-create-area">
			<table cellspacing="5" cellpadding="0" border="0" class="content-table">
				<tr>
					<th width="23%"><b>Grouping ID :</b></th>
					<td width="31%"><c:out value="${groupDetailsForm.groupId}" /></td>
					<th width="23%"><b>Status :</b></b></th>
					<td width="23%"><c:out value="${groupDetailsForm.groupStatusDesc}" /></td>
				</tr>
				<tr>
					<th><b>Grouping Name:</b></th>
					<td><c:out value="${groupDetailsForm.groupName}" /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<th><b>Grouping Description:</b></th>
					<td><c:out value="${groupDetailsForm.groupDesc}" /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<th><b>Grouping Type:</b></th>
					<td><c:out value="${groupDetailsForm.groupTypeDesc}" /></td>
					<td></td>
					<td></td>
				</tr>
				
				<c:if test="${groupDetailsForm.groupType == 'BCG'}">
					<tr>
						<th><b>Start Date:</b></th>
						<td><c:out value="${groupDetailsForm.groupLaunchDate}" /></td>
						<th><b>End Date:</b></th>
						<td><c:out value="${groupDetailsForm.endDate}" /></td>
					</tr>
				</c:if>
				<c:if test="${groupDetailsForm.groupType == 'RCG'}">
					<tr>
						<th><b>CARS Group Type:</b></th>
						<td><c:out value="${groupDetailsForm.carsGroupType}" /></td>
						<th></th>
						<td></td>
					</tr>
				</c:if>
			
			</table>	
		</div>
	</div>
</div>
<div  class="cars_panel x-hidden">
	<div class="x-panel-header">
		<b>Add Components</b>
	</div>
	
	<div class="x-panel-body">
		<div class="group-search-area">
			<form name="frmComponentSearch" id="frmComponentSearch" action="">
			<input type="hidden" name="groupType" value=" <c:out value="${groupDetailsForm.groupType}" /> " id="groupType" >
			<input type="hidden" name="groupId" value=" <c:out value="${groupDetailsForm.groupId}" /> " id="groupId" >
			<table cellspacing="5" cellpadding="0" border="0" class="content-table">
				<tr>
					<td width="10%" align="right">
						<label for="styleOrinNo">Style ORIN#</label>
					</td>
					<td>
						<input type="text" name="styleOrinNo" id="styleOrinNo" value="" />
					</td>
					<td width="10%" align="right">
						<label for="s-grouping-name">DEPT#</label>
					</td>
					<td>
						<input type="text" name="groupName" id="s-grouping-name" value="" />
						<input type="button" value="open" id="btnDlgDept"/>
					</td>
					<td width="10%" align="right">
						<label for="s-grouping-ssite">Class</label>
					</td>
					<td>
						<input type="text" name="supplierId" id="s-grouping-ssite" value="" />
						<input type="button" value="open" id="btnDlgClass"/>
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">
						<label for="vendorStyleNo">Vendor Style Number</label>
					</td>
					<td>
						<input type="text" name="vendorStyleNo" id="vendorStyleNo" value="" />
						
					</td>
					<td width="10%" align="right">
						<label for="s-grouping-dept">Supplier site#</label>
					</td>
					<td>
						<input type="text" name="classes" id="s-grouping-dept" value="" />
						
					</td>
					<td width="10%" align="right">
						<label for="s-grouping-dept">UPC#</label>				
					</td>
					<td><input type="text" name="orinNumber" id="s-grouping-dept" value="" /></td>
				</tr>
				<tr>
					<td width="10%" align="right">
						<label for="s-grouping-dept">Group ID#</label>	
					</td>
					<td><input type="text" name="vendor" id="s-grouping-dept" value="" /></td>
					<td width="10%" align="right">
						<label for="s-grouping-dept">Group Name</label>	
					</td>
					<td><input type="text" name="vendor" id="s-grouping-dept" value="" /></td>					
					<td>&nbsp;</td>
					<td align="right" >
						<input type="reset" value="Clear" class="btn" />
						<input type="submit" value="Search" class="btn"  id="search-groups"/>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</div>
<div  class="cars_panel x-hidden hidden" id="search-result-panel">
	<div class="x-panel-header">
		<b>Search Results</b>
	</div>
	<div class="x-panel-body">
		<div class="group-search-area" id="dataTable">
			<div class="pagination-container">
				<div class="pagination-left">
					<div class="pagination-left-wrapper">
						<label for="page-limit-1">Show: </label>
						<select name="page-limit-1" id="page-limit-1" class="record-limit">
							<option value="10">10</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
						<span class="pagination-text"></span>
					</div>
				</div>
				<div class="pagination-right">
					<ul class="paginator"></ul>
				</div>
			</div>
			<div class="clearfix"></div>
			<c:if test="${groupDetailsForm.groupType == 'SCG'}">
				<%@ include file="/WEB-INF/pages/jsp/splitColorTemplate.jsp" %>
			</c:if>
			<c:if test="${groupDetailsForm.groupType == 'SSG'}">
				<%@ include file="/WEB-INF/pages/jsp/splitSKUTemplate.jsp" %>
			</c:if>
			<div class="pagination-container">
				<div class="pagination-left">
					<div class="pagination-left-wrapper">
						<label for="page-limit-2">Show: </label>
						<select name="page-limit-2" id="page-limit-2" class="record-limit">
							<option value="10">10</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
						<span class="pagination-text"></span>
					</div>
				</div>
				<div class="pagination-right">
					<ul class="paginator"></ul>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="pagination-container" style="text-align:left; margin-top: 15px;">
				<input type="button" class="btn" value="Add Component" style="width: 110px;" id="add-components" />
			</div>
		</div>
	</div>
</div>
<div class="cars_panel x-hidden">
	<div class="x-panel-header">
		<b>Existing Components</b>
	</div>
	<div class="x-panel-body">
		<div class="group-search-area" id="exisiting-table-dataTable">
			<div class="pagination-container">
				<div class="pagination-left">
					<div class="pagination-left-wrapper">
						<label for="page-limit-1">Show: </label>
						<select name="page-limit-1" id="page-limit-1" class="record-limit">
							<option value="10">10</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
						<span class="pagination-text"></span>
					</div>
				</div>
				<div class="pagination-right">
					<ul class="paginator"></ul>
				</div>
			</div>
			<div class="clearfix"></div>
			<c:if test="${groupDetailsForm.groupType == 'SCG'}">
				<%@ include file="/WEB-INF/pages/jsp/splitColorExisting.jsp" %>
			</c:if>
			<c:if test="${groupDetailsForm.groupType == 'SSG'}">
				<%@ include file="/WEB-INF/pages/jsp/splitSKUExisting.jsp" %>
			</c:if>
			<div class="pagination-container">
				<div class="pagination-left">
					<div class="pagination-left-wrapper">
						<input type="button" class="btn" value="Remove Component" style="width: 140px; opacity:0.5" id="remove-existing-group" disabled="disabled" /> 
						<!--
						<br> <br>
						<label for="page-limit-2">Show: </label>
						<select name="page-limit-2" id="page-limit-2" class="record-limit">
							<option value="10">10</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
						<span class="pagination-text"></span>
						-->
					</div>
				</div>
				<div class="pagination-right">
					<ul class="paginator"></ul>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="pagination-container" style="text-align:center; margin-top: 15px;">
				<input type="button" class="btn" value="Save" style="width: 70px;" id="save-existing-group" />
				<input type="button" class="btn" value="Close" style="width: 80px;" id="close-existing-group" />
			</div>
		</div>
	</div>
</div>

<div class="group-search-footer-area">
	<div class="footer-content"><input type="button" onclick="history.back(1)" class="btn" value="Close"></div>
</div>

<!-- div loading starts-->
<div class="overlay_pageLoading hidden"><img src="<%=response.encodeURL(request.getContextPath())%>/img/loading.gif" alt="Loading.."></div>
<!-- div loading ends-->

<portlet:resourceURL id="getExistGrpComponent" var="getExistGrpComponentURL" />
<portlet:resourceURL id="getNewGrpComponent" var="getNewGrpComponentURL" />
<portlet:resourceURL id="addComponentToGroup" var="addComponentToGroupURL" />

<div class="ui-widget" id="errorBox" style="display:none">
    <div class="ui-state-error ui-corner-all" style="padding: 15px;"> 
        <p>
            <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
            <span id="error-massege">Sample ui-state-error style. </span>
        </p>
    </div>
</div>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery-ui.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/constants.js")%>"></script>
<script>
	app.Global.defaults.contextPath = '<%=response.encodeURL(request.getContextPath())%>';
</script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/underscore-min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.twbsPagination.min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/URLFactory.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/DataTable.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/GroupFactory.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/grouping.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/editComponent.js")%>"></script>
<script>
	app.URLFactory.urlCollection.existingCompUrl = "${getExistGrpComponentURL}";
	app.URLFactory.urlCollection.splitComponentSearchUrl = "${getNewGrpComponentURL}";
	app.URLFactory.urlCollection.addComponentToGroup = "${addComponentToGroupURL}";
	
	//init main SPA
	app.EditComponentLandingApp.init();
</script>
