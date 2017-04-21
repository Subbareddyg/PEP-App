<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<portlet:resourceURL id="invalidate" var="logouturl" />
<fmt:setBundle basename="grouping" />
<div align="left" style="display: inline; padding: 5px 10px;margin-bottom: 0.5cm" >
<input type="button" style="padding: 5px 10px;font-weight: bold" name="home" value="Home" onclick=goToHomeScreen();  />
</div>
<div align="right" style="display: inline;padding: 5px 5px 10px 750px; margin-bottom: 0.5cm; left:750px" >
    <input type="button" class="btn" value="Close" style="width: 80px;" id="close-existing-group"
        onclick="window.location.href='/wps/portal/home/Grouping'" />
</div>
<div align="right" style="margin: 1em 0;" >
			<c:out value="${LAN_ID}"/> &nbsp;	 
			<input type="button"   style="font-weight: bold" name="logout" value="Logout" 
			    onclick="grouping_logOut('<c:out value="${LAN_ID}"/>','${logouturl }'); " />	
			
		 </div>
<div  class="cars_panel x-hidden">
	<div class="x-panel-header">
		<b><fmt:message key="addcomponent.screen.level.groupingDetails" /></b>
	</div>
	
	<div class="x-panel-body">
		<div class="group-create-area">
			<div id="group-header-message-area"></div>
			<form name="fromHeaderEdit" id="fromHeaderEdit" class="group-edit-area">
				<c:if test="${readonly =='yes'}">
					<input type="hidden" name="groupType" value=" <c:out value="${groupDetailsForm.groupType}" /> " id="groupType" >
					<input type="hidden" name="groupId" value=" <c:out value="${groupDetailsForm.groupId}" /> " id="groupId" >
				</c:if>
				<input type="hidden" name="modifiedBy" value="${LAN_ID}" id="userId" />
				<table cellspacing="5" cellpadding="0" border="0" class="content-table">
					<tr>
						<th width="18%"><b><fmt:message key="addcomponent.screen.level.groupingId" /></b></th>
						<th width="32%">
							<c:out value="${groupDetailsForm.groupId}" />
							<input type="hidden" name="groupId" value="${groupDetailsForm.groupId}" />
						</th>
						<th width="13%"><b><fmt:message key="addcomponent.screen.level.status" /></b></th>
						<th width="32%">
							<c:out value="${groupDetailsForm.groupStatusDesc}" />
							<input type="hidden" name="status" value="${groupDetailsForm.groupStatusDesc}" />
						</th>
					</tr>
					<tr>
						<td><b rel="groupName"><fmt:message key="addcomponent.screen.level.groupingName" /></b></th>
						<td>
							<span class="editable" data-type="text" data-field-name="groupName" data-required="true"><c:out value="${groupDetailsForm.groupName}" /></span>
							<div class="maxChars hidden"><div class="charlimit">Current Chars:<span id="nameCurChars">0</span> Max Chars: <span id="nameMaxChars">0</span></div></div>
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td><b rel="groupDesc"><fmt:message key="addcomponent.screen.level.groupingDesc" /></b></th>
						<td>
							<span class="editable" data-type="textarea" data-field-name="groupDesc" data-required="true"><c:out value="${groupDetailsForm.groupDesc}" /></span>
							<div class="maxChars hidden"><div class="charlimit">Current Chars: <span id="descCurChars">0</span> Max Chars: <span id="descMaxChars">0</span></div></div>
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td><b><fmt:message key="addcomponent.screen.level.groupingType" /></b></td>
						<td>
							<c:out value="${groupDetailsForm.groupTypeDesc}" />
							<input type="hidden" name="groupType" value="${groupDetailsForm.groupTypeDesc}" />
						</td>
						<td></td>
						<td></td>
					</tr>
					
					<c:if test="${groupDetailsForm.groupType == 'BCG'}">
						<tr>
							<th><b rel="startDate"><fmt:message key="addcomponent.screen.level.startDate" /></b></th>
							<td><span class="editable" data-type="date" data-field-name="startDate"><c:out value="${groupDetailsForm.groupLaunchDate}" /></td>
							<td><b><fmt:message key="addcomponent.screen.level.endDate" /></b></td>
							<td><span class="editable" data-type="date" data-field-name="endDate"><c:out value="${groupDetailsForm.endDate}" /></span></td>
						</tr>
					</c:if>
					<c:if test="${groupDetailsForm.groupType == 'RCG'}">
						<tr>
							<td><b><fmt:message key="addcomponent.screen.level.carsGroupType" /></b></td>
							<td>
							<c:if test="${groupDetailsForm.carsGroupType == 'PATTERN-SPLIT-VS'}">
								<fmt:message key="addcomponent.screen.level.pattern" />
							</c:if>
							<c:if test="${groupDetailsForm.carsGroupType == 'PATTERN-SSKU-VS'}">
								<fmt:message key="addcomponent.screen.level.collection" />
							</c:if>
							</td>
							<th></th>
							<td></td>
						</tr>
					</c:if>
					<tr>
						<td colspan="3">&nbsp;</td>
						<c:if test="${readonly !='yes'}">
						<td align="right">
							<input type="button" value="Cancel" class="btn" id="cancel-edit-header" style="visibility:hidden" disabled="disabled"/>
							<input type="button" value="Edit" class="btn"  id="edit-header"/>
						</td>
						</c:if>
					</tr>
				</table>
			</form>			
		</div>
	</div>
</div>
<c:if test="${readonly !='yes'}">
<div  class="cars_panel x-hidden">
	<div class="x-panel-header">
		<b><fmt:message key="addcomponent.screen.level.header" /></b>
	</div>
	
	<div class="x-panel-body">
		<div class="group-search-area">
			<form name="frmComponentSearch" id="frmComponentSearch" action="" class="<c:out value="${groupDetailsForm.groupType}" />-group-validation">
			<input type="hidden" name="groupType" value=" <c:out value="${groupDetailsForm.groupType}" /> " id="groupType" >
			<input type="hidden" name="groupId" value=" <c:out value="${groupDetailsForm.groupId}" /> " id="groupId" >
			<input type="hidden" name="alreadyInGroup" value="${groupDetailsForm.isAlreadyInGroup}" id="alreadyInGroup" />
			<c:if test="${groupDetailsForm.groupType == 'SSG' || groupDetailsForm.groupType == 'SCG'}">
				<input type="hidden" name="styleOrinNo" value="" id="styleOrinNo" >
			</c:if>
			<table cellspacing="5" cellpadding="0" border="0" class="content-table">
				<tr>
					<td width="10%" align="right">
						<label for="styleOrinNoShowOnly"><fmt:message key="addcomponent.screen.level.styleOrinNo" /></label>
					</td>
					<td>
						<input type="text" name="styleOrinNo" id="styleOrinNoShowOnly" value="" />
					</td>
					<td width="10%" align="right">
						<label for="s-grouping-dept"><fmt:message key="addcomponent.screen.level.deptNo" /></label>
					</td>
					<td>
						<input type="text" name="deptSearch" id="s-grouping-dept" value="" disabled />
						<input type="button" value="open" id="btnDlgDept" disabled />
					</td>
					<td width="10%" align="right">
						<label for="s-grouping-class"><fmt:message key="addcomponent.screen.level.class" /></label>
					</td>
					<td>
						<input type="text" name="classSearch" id="s-grouping-class" value="" disabled />
						<input type="button" value="open" id="btnDlgClass" disabled />
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">
						<label for="vendorStyleNo"><fmt:message key="addcomponent.screen.level.vendorStyleNo" /></label>
					</td>
					<td>
						<input type="text" name="vendorStyleNo" id="vendorStyleNo" value="" />
						
					</td>
					<td width="10%" align="right">
						<label for="supplier-Search"><fmt:message key="addcomponent.screen.level.supplierSiteNo" /></label>
					</td>
					<td>
						<input type="text" name="supplierSearch" id="supplier-Search" value="" />
						
					</td>
					<td width="10%" align="right">
						<label for="upc-search"><fmt:message key="addcomponent.screen.level.upcNo" /></label>				
					</td>
					<td><input type="text" name="UPCSearch" id="upc-search" value="" /></td>
				</tr>
				<tr>
					<td width="10%" align="right">
						<label for="groupId-search"><fmt:message key="addcomponent.screen.level.groupId" /></label>	
					</td>
					<td><input type="text" name="groupIDSearch" id="groupId-search" value="" /></td>
					<td width="10%" align="right">
						<label for="groupName-search"><fmt:message key="addcomponent.screen.level.groupName" /></label>	
					</td>
					<td><input type="text" name="groupNameSearch" id="groupName-search" value="" /></td>					
					<td>&nbsp;</td>
					<td align="right" >
						<input type="reset" value="Clear" class="btn" />
						<input type="submit" value="Search" class="btn"  id="search-components"/>
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
				<div id="group-creation-messages"></div>
				
			<div class="pagination-container">
				<div class="pagination-left">
					<div class="pagination-left-wrapper">
						<label for="page-limit-1"><fmt:message key="splitgroup.screen.level.show" /> </label>
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
			<c:choose>
				<c:when test="${groupDetailsForm.groupType == 'CPG'}">
				  <%@ include file="/WEB-INF/pages/jsp/CPGTemplate.jsp" %>
				</c:when>
				<c:when test="${groupDetailsForm.groupType == 'SCG'}">
				  <%@ include file="/WEB-INF/pages/jsp/splitColorTemplate.jsp" %>
				</c:when>
				<c:when test="${groupDetailsForm.groupType == 'SSG'}">
				  <%@ include file="/WEB-INF/pages/jsp/splitSKUTemplate.jsp" %>
				</c:when>
				<c:when test="${groupDetailsForm.groupType == 'GSG'}">
				  <%@ include file="/WEB-INF/pages/jsp/GSGTemplate.jsp" %>
				</c:when>
				<c:when test="${groupDetailsForm.groupType == 'RCG'}">
				  <%@ include file="/WEB-INF/pages/jsp/RCGTemplate.jsp" %>
				</c:when>
				<c:when test="${groupDetailsForm.groupType == 'BCG'}">
				  <%@ include file="/WEB-INF/pages/jsp/BCGTemplate.jsp" %>
				</c:when>
			</c:choose>			
			<div class="pagination-container">
				<div class="pagination-left">
					<div class="pagination-left-wrapper">
						<label for="page-limit-2"><fmt:message key="splitgroup.screen.level.show" /> </label>
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
				<div class="pagination-left">
					<input type="button" class="btn" value="Add Component" style="width: 110px;" id="add-components" />
				</div>
				<div class="pagination-left" style="width:50%; margin-top:-12px; text-align:center">
					<div id="group-creation-messages" ></div>
				</div>
			<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>
</c:if>
<div class="cars_panel x-hidden">
	<div class="x-panel-header">
		<b><fmt:message key="addcomponent.screen.level.existingComponents" /></b>
	</div>
	<div class="x-panel-body">
		<div class="group-search-area" id="exisiting-table-dataTable">
			<div id="group-existing-component-area"></div>
			<div class="pagination-container hide_after_error">
				<div class="pagination-left">
					<div class="pagination-left-wrapper ">
						<label for="page-limit-1"><fmt:message key="splitgroup.screen.level.show" /> </label>
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
			<c:choose>
				<c:when test="${groupDetailsForm.groupType == 'CPG'}">
				  <%@ include file="/WEB-INF/pages/jsp/CPGExisting.jsp" %>
				</c:when>
				<c:when test="${groupDetailsForm.groupType == 'SCG'}">
				  <%@ include file="/WEB-INF/pages/jsp/splitColorExisting.jsp" %>
				</c:when>
				<c:when test="${groupDetailsForm.groupType == 'SSG'}">
				 <%@ include file="/WEB-INF/pages/jsp/splitSKUExisting.jsp" %>
				</c:when>
				<c:when test="${groupDetailsForm.groupType == 'GSG'}">
				 <%@ include file="/WEB-INF/pages/jsp/GSGExisting.jsp" %>
				</c:when>
				<c:when test="${groupDetailsForm.groupType == 'RCG'}">
				  <%@ include file="/WEB-INF/pages/jsp/RCGExisting.jsp" %>
				</c:when>
				<c:when test="${groupDetailsForm.groupType == 'BCG'}">
				  <%@ include file="/WEB-INF/pages/jsp/BCGExisting.jsp" %>
				</c:when>
			</c:choose>
			<div class="pagination-container">
				<div class="pagination-left">
					<div class="pagination-left-wrapper hide_after_error">
						<c:if test="${readonly !='yes'}">
							<input type="button" class="btn" value="Remove Component" id="remove-existing-component" disabled="disabled" /> 
						</c:if>
					</div>
				</div>
				<div class="pagination-right">
					<ul class="paginator"></ul>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="pagination-container" style="text-align:center; margin-top: 15px;">
				<c:if test="${readonly !='yes'}"> 
					<c:choose>
						<c:when test="${groupDetailsForm.groupType != 'CPG'}">
							<input type="button" class="btn" value="Save" style="width: 70px;" id="save-existing-group"/>
						</c:when>
					</c:choose>
				</c:if>
			</div>
		</div>
	</div>
</div>

<div class="group-search-footer-area">
	
</div>

<!-- div loading starts-->
<div class="overlay_pageLoading"><img src="<%=response.encodeURL(request.getContextPath())%>/img/loading.gif" alt="Loading.."></div>
<!-- div loading ends-->

<portlet:resourceURL id="getExistGrpComponent" var="getExistGrpComponentURL" />
<portlet:resourceURL id="getNewGrpComponent" var="getNewGrpComponentURL" />
<portlet:resourceURL id="addComponentToGroup" var="addComponentToGroupURL" />
<portlet:resourceURL var="searchGroupResourceRequest"></portlet:resourceURL>
<portlet:resourceURL id="saveEditedGroup" var="editGroupURL" />
<portlet:resourceURL id="setDefaultColor" var="defaultValueResourceRequest" />
<portlet:resourceURL id="removeComponent" var="removeComponentURL" />
<portlet:resourceURL id="releseLockedPetURL" var="releseLockedPetURL" />


<!-- Department Search Result Row Template starts -->
<script type="text/template" id="dept-row-template">
{{ if(data.length){ }}
	{{_.each(data, function(row, key){ }}
		<tr>
			<td><input type="checkbox" name="deptitems[]" {{=_.contains(selected, row.deptId) ? 'checked="checked"' : '' }} class="dept-items" value="{{=row.deptId}}" /></td>
			<td>{{=row.deptId}}</td>
			<td>{{=row.deptDesc}}</td>
		</tr>
	{{ }) }}	
{{ }else{ }}
	<tr>
		<td colspan="3" align="center"><strong>No record Found!</strong></td>
	</tr>
{{ } }}
</script>
<!-- Department Search Result Row Template ends -->

<!-- Class Search Result Row Template starts -->
<script type="text/template" id="class-row-template">
{{ if(data.length){ }}
	{{_.each(data, function(row, key){ }}
		<tr>
			<td><input type="checkbox" name="deptitems[]" {{=_.contains(selected, row.classId) ? 'checked="checked"' : '' }} class="class-items" value="{{=row.classId}}" /></td>
			<td>{{=row.classId}}</td>
			<td>{{=row.classDesc}}</td>
		</tr>
	{{ }) }}	
{{ }else{ }}
	<tr>
		<td colspan="3" align="center"><strong>No record Found!</strong></td>
	</tr>
{{ } }}
</script>
<!-- Class Sear Result Row Template ends -->




<!-- Dept selection dialog starts -->
<div id="dlgdeptSearch" class="hidden">
	<div class="dialog-wrapper">
		<div class="dept-button-area">
			<input id="btnClearDept" type="button" class="btn" value="Clear"  tabindex="5" /> &nbsp;
			<input id="btnSaveDept" type="button" value="Save and Close" class="btn" tabindex="3" /> &nbsp;
			<input id="btnCloseDept" type="button" value="Close" class="btn" tabindex="4" /> 
		</div>
		<div class="dept-search-area">
			<form id="srchDeptsFrm" name="srchDeptsFrm">
				<label for="selectedDeptSearch">Dept # </label><input type="text" name ="selectedDeptSearch" id ="selectedDeptSearch" tabindex="1" />
				<input id="btnDepSearch" type="submit" value="Search" class="btn" tabindex="2" />
			</form>
		</div>
		<div class="dept-result-area">
			<table cellspacing="5" cellpadding="0" border="0" class="content-table border-simple" align="center">
				<thead>
					<tr>

						<th></th>
						<th>Dept#</th>
						<th>Dept Description</th>
					<tr>
				</thead>
				<tbody id="dept-search-result">
					
				</tbody>
			</table>
		</div>
	</div>
</div>
<!-- Dept selection dialog ends -->

<!-- Class selection dialog starts -->
<div id="dlgClassSearch" class="hidden">
	<div class="dialog-wrapper">
		<div class="dept-button-area">
			<input id="btnClearClass" type="button" class="btn" value="Clear"  tabindex="5" /> &nbsp;
			<input id="btnSaveClass" type="button" value="Save and Close" class="btn" tabindex="3" /> &nbsp;
			<input id="btnCloseClass" type="button" value="Close" class="btn" tabindex="4" /> 
		</div>
		<div class="dept-result-area">
			<table cellspacing="5" cellpadding="0" border="0" class="content-table border-simple" align="center">
				<thead>
					<tr>
						<th></th>
						<th>Class #</th>
						<th>Class Description</th>
					<tr>
				</thead>
				<tbody id="class-result-area">
					
				</tbody>
			</table>
		</div>
	</div>
</div>
<!-- Class selection dialog ends -->




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
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/idle-timer.min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/constants.js")%>"></script>
<script>
	app.Global.defaults.contextPath = '<%=response.encodeURL(request.getContextPath())%>';
</script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/underscore-min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.twbsPagination.min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/URLFactory.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/DataTable.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/DataTableComponentAjax.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/GroupFactory.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/grouping.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/editComponent.js")%>"></script>
<script>
	app.URLFactory.urlCollection.existingCompUrl = "${getExistGrpComponentURL}";
	app.URLFactory.urlCollection.splitComponentSearchUrl = "${getNewGrpComponentURL}";
	app.URLFactory.urlCollection.addComponentToGroup = "${addComponentToGroupURL}";
	app.URLFactory.urlCollection.groupSearchUrl = "${searchGroupResourceRequest}";
	app.URLFactory.urlCollection.editDefaultComponentUrl = "${defaultValueResourceRequest}";
	app.URLFactory.urlCollection.removeComponentURL = "${removeComponentURL}";
	
	app.URLFactory.urlCollection.saveHeader = "${editGroupURL}";
	
	app.URLFactory.urlCollection.groupLockHandleURL = "${releseLockedPetURL}";
	//init main SPA
	
	app.EditComponentLandingApp.init();
	function goToHomeScreen(){
            window.location.href = "/wps/portal/home/worklistDisplay";
	}
</script>