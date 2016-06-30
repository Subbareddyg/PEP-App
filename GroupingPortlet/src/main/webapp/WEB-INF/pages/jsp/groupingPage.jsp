<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<fmt:setBundle basename="grouping" />
<div align="right" style="margin-bottom: 0.5cm" >	
			<c:out value="${LAN_ID}"/> &nbsp;	 
			<input type="button"   style="font-weight: bold" name="logout" value="Logout" 
			    onclick="grouping_logOut('<c:out value="${LAN_ID}"/>'); " />	
			
		 </div>
<div  class="cars_panel x-hidden">
	<div class="x-panel-header">
		<b><fmt:message key="group.screen.level.Groupings" /></b>
	</div>
	
	<div class="x-panel-body">
		<div class="group-create-area">
				<select id="groupTypeSelector" <c:if test="${readonly =='yes'}">disabled style="opacity:0.6;" </c:if>>
					<c:forEach var="groupTypesMap" items="${groupTypesMap}">                                                                                                
                        <option value="${groupTypesMap.key}"  [b]selected="true"[/b] ><c:out value="${groupTypesMap.value}"/></option>              
                    </c:forEach>
				</select>
				<input type="button" class="btn" id="btnGroupCreate" <c:if test="${readonly =='yes'}">disabled style="opacity:0.6;" </c:if> value="Create Grouping">
		</div>
	</div>
</div>
<div  class="cars_panel x-hidden">
	<div class="x-panel-header">
		<b><fmt:message key="group.screen.level.searchGroupings" /></b>
	</div>
	
	<div class="x-panel-body">
		<div class="group-search-area">
			<form name="frmGroupSearch" action="" id="frmGroupSearch">
			<table cellspacing="5" cellpadding="0" border="0" class="content-table">
				<tr>
					<td width="10%" align="right">
						<label for="s-grouping-id"><fmt:message key="group.screen.level.groupingNumber" /></label>
					</td>
					<td>
						<input type="text" name="groupId" id="s-grouping-id" value="" />
					</td>
					<td width="10%" align="right">
						<label for="s-grouping-name"><fmt:message key="group.screen.level.groupingName" /></label>
					</td>
					<td>
						<input type="text" name="groupName" id="s-grouping-name" value="" />
					</td>
					<td width="10%" align="right">
						<label for="s-grouping-ssite"><fmt:message key="group.screen.level.supplierId" /></label>
					</td>
					<td>
						<input type="text" name="supplierId" id="s-grouping-ssite" value="" />
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">
						<label for="s-grouping-dept"><fmt:message key="group.screen.level.dept" /></label>
					</td>
					<td>
						<input type="text" name="departments" id="s-grouping-dept" value="" disabled />
						<input type="button" value="open" id="btnDlgDept" disabled />
					</td>
					<td width="10%" align="right">
						<label for="s-grouping-class"><fmt:message key="group.screen.level.class" /></label>
					</td>
					<td>
						<input type="text" name="classes" id="s-grouping-class" value="" disabled />
						<input type="button" value="open" id="btnDlgClass" disabled />
					</td>
					<td width="10%" align="right">
						<label for="s-orinNumber"><fmt:message key="group.screen.level.styleOrin" /></label>				
					</td>
					<td><input type="text" name="orinNumber" id="s-orinNumber" value="" /></td>
				</tr>
				<tr>
					<td width="10%" align="right">
						<label for="s-vendor"><fmt:message key="group.screen.level.vendorStyle" /></label>	
					</td>
					<td><input type="text" name="vendor" id="s-vendor" value="" /></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="right" >
						<input type="hidden" value="searchGroup" name="resourceType" />
						<input type="button" id="clrSrchGroup" value="Clear" class="btn" />
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
		<b><fmt:message key="group.screen.level.searchResult" /></b>
	</div>
	<div class="x-panel-body">
		<div class="group-search-area" id="dataTable">
			<div id="group-deletion-messages"></div>
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
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="clearfix"></div>
			<table cellpadding="0" cellspacing="0" border="1" class="content-table border-simple colored-row tree-grid">
				<thead>
					<tr>
						<th width="15%"><a href="javascript:;" class="sortable" data-sort-column="groupId" data-sorted-by=""><b><fmt:message key="group.screen.level.table.header1" /></b></a></th>
						<th width="20%"><a href="javascript:;" class="sortable" data-sort-column="groupName" data-sorted-by=""><b><fmt:message key="group.screen.level.table.header2" /></b></a></th>
						<th width="19%"><a href="javascript:;" class="sortable" data-sort-column="groupType" data-sorted-by=""><b><fmt:message key="group.screen.level.table.header3" /></b></a></th>
						<th width="7%"><a href="javascript:;" class="sortable" data-sort-column="imageStatus" data-sorted-by=""><b><fmt:message key="group.screen.level.table.header4" /></b></a></th>
						<th width="7%"><a href="javascript:;" class="sortable" data-sort-column="contentStatus" data-sorted-by=""><b><fmt:message key="group.screen.level.table.header5" /></b></a></th>
						<th width="9%"><a href="javascript:;" class="sortable" data-sort-column="startDate" data-sorted-by=""><b><fmt:message key="group.screen.level.table.header6" /></b></a></th>
						<th width="8%"><a href="javascript:;" class="sortable" data-sort-column="endDate" data-sorted-by=""><b><fmt:message key="group.screen.level.table.header7" /></b></a></th>
						<th width="10%">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="row-container"><tr><td colspan="8" align="center"><strong>No record Found!</strong></td></tr></tbody>
			</table>
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
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="group-search-footer-area">
	<div class="footer-content"><input type="button" onclick="window.location.href='/wps/portal/home/worklistDisplay'" class="btn" value="Close"></div>
</div>

<!-- div loading starts-->
<div class="overlay_pageLoading hidden"><img src="<%=response.encodeURL(request.getContextPath())%>/img/loading.gif" alt="Loading.."></div>
<!-- div loading ends-->

<portlet:actionURL var="createAction"> 
		<portlet:param name="action" value="createAction"/>
</portlet:actionURL>

<portlet:renderURL var="splitColorGroupingSubmit"> 
	<portlet:param name="groupingTypeSplitColor" value="splitColor" />
</portlet:renderURL>

<portlet:resourceURL var="searchGroupResourceRequest"> 
</portlet:resourceURL>

<portlet:resourceURL var="deleteGroupResourceRequest"> 
</portlet:resourceURL>

<portlet:renderURL var="splitSKUGroupingSubmit"> 
	<portlet:param name="groupingTypeSplitSKU" value="splitSKU" />
</portlet:renderURL>

<portlet:resourceURL id="submitCreateGroupForm" var="createGroupFormSubmitURL" />

<portlet:renderURL var="createGrpRenderUrl"> 
    <portlet:param name="createGroupSuccessRender" value="CreateGrpSuccess" />
</portlet:renderURL>

<portlet:renderURL var="getGroupDetailsUrl"> 
	<portlet:param name="getGroupDetails" value="getGroupDetails" />
</portlet:renderURL>


<portlet:actionURL var="getGroupDetailsUrl"> 
		<portlet:param name="getGroupDetails" value="getGroupDetails"/>
</portlet:actionURL>

<!-- Group Creation dialog starts -->
<div id="dlgGroupCreate" style="display:none">
	<div class="dialog-wrapper">
		<form action="${createAction}" name="createGroupForm" id = "createGroupForm" method="post">
		<table cellspacing="5" cellpadding="0" border="0" class="content-table" align="center">
			<tr>
				<td align="right">Grouping Type <span class="red-text">(*)</span>:</td>
				<td>
					<!-- placeholder hidden field for holding selected group type -->
					<input type="hidden" name="groupType" id="groupType" value="" />
					<select name="groupTypeDropDown" id="groupTypeDropDown" disabled="disabled">
						<c:forEach var="groupTypesMap" items="${groupTypesMap}">
							<option value="${groupTypesMap.key}"><c:out value="${groupTypesMap.value}"/></option>              
	                    </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">Grouping Name <span class="red-text">(*)</span>:</td>
				<td>
					<input type="text" name="groupName" id="groupName" value="" class="" required />
					<div class="charlimit">Current Chars: <span id="nameCurChars">0</span> Max Chars: <span id="nameMaxChars">0</span></div>
				</td>
			</tr>
			<tr class='add-RCG-field'>
				<td align="right">CARS Grouping Type<span class="red-text">(*)</span>:</td>
				<td>
					
					<select name="carsGroupType" id="carsGroupType">
						<option value="PATTERN-SPLIT-VS" selected="selected" >PATTERN-SPLIT-VS</option>
						<option value="PATTERN-SSKU-VS">PATTERN-SSKU-VS </option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">Grouping Description <span class="red-text">(*)</span>:</td>
				<td>
					<textarea cols="10" rows="4" class="" name="groupDesc" id="groupDesc" required></textarea>
					<div class="charlimit">Current Chars: <span id="descCurChars">0</span> Min Chars:<span id="descMinChars">0</span> Max Chars: <span id="descMaxChars">0</span>
				</td>
			</tr>
			<tr class="optional-fields">
				<td align="right">Launch Date <span class="red-text">(*)</span>:</td>
				<td>
					<input type="text" name="startDate" id="startDate" value="" class="" style="width: 80%"/>
				</td>
			</tr>
			<tr class="optional-fields">
				<td align="right">End Date:</td>
				<td>
					<input type="text" name="endDate" id="endDate" value="" class="" style="width: 80%" />
				</td>
			</tr>
			<tr>
				<td align="right">&nbsp;</td>
				<td>
					<input type="button" class="btn" value="Cancel" id="closeGrpDlg" />
					<input type="submit" class="btn" value="Save" id="btnCreateGroup"/>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<div id="group-creation-messages"></div>
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
<!-- Group Creation dialog ends -->

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

<!-- UI Confirmation dialog starts -->
<div class="ui-widget" id="errorBox" style="display:none">
    <div class="ui-state-error ui-corner-all" style="padding: 15px;"> 
        <p>
            
            <span id="error-massege"></span>
        </p>
    </div>
</div>
<!-- UI Confirmation dialog starts -->

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

<!-- Group search Table Row Template starts -->
<script type="text/template" id="row-template">
{{ if(data.length){ }}
	{{_.each(data, function(row, key){ }}
		<tr>
			<td>
				{{ if(row.childList && row.childList.length){ }}
					<div class="icon-tree parent-node-expand" data-node-id="{{=(row.groupId + '_' + key)}}">
						&nbsp;
					</div>
				{{ }else{ }}
					<div class="icon-tree parent-leaf" data-node-id="{{=(row.groupId + '_' + key)}}">
						&nbsp;
					</div>
				{{ } }}
				{{=row.groupId}}
				{{if(row.childGroup == 'Y'){ }} 
					<img src="<%=response.encodeURL(request.getContextPath())%>/img/grouping_indicator.png" alt="In Grouping" width="9" style="position: relative; top: -8px;" />
				{{ } }}
			</td>
			<td>{{=row.groupName}}</td>
			<td>{{=row.groupType}}</td>
			<td class="text-center">{{=row.groupImageStatus}}</td>
			<td class="text-center">{{=row.groupContentStatus}}</td>
			<td class="text-center">{{=row.startDate}}</td>
			<td class="text-center">{{=row.endDate}}</td>
			<td class="text-center">
				<form id="form_{{=row.groupId}}_{{=key}}" action="${getGroupDetailsUrl}">
					<input type="hidden" name="groupId" value="{{=row.groupId}}" />
					<input type="hidden" name="groupType" value="{{=row.groupTypeCode}}" />
				</form>
				<a href="javascript:;" onclick="document.getElementById('form_{{=row.groupId}}_{{=key}}').submit()" class="gs-item-details" data-id="{{=row.groupId}}">Details</a> <c:if test="${readonly !='yes'}"> | <a href="javascript:;" class="delete-item" data-group-type="{{=row.groupTypeCode}}" data-group-id="{{=row.groupId}}">Delete</a></c:if></td>
		</tr>
		{{ if(row.childList && row.childList.length){ }}
			</tr>
			{{ _.each(row.childList, function(childRow, childKey){ }}
				<tr class="hidden-child" data-parent-id="{{=(row.groupId + '_' + key) }}">
					<td>
						<div class="icon-tree leaf-node" data-node-id="{{=(childRow.groupId + '_' + childKey)}}">
						&nbsp;
						</div> {{=childRow.groupId}}
					</td>
					<td>{{=childRow.groupName}}</td>
					<td>{{=childRow.groupType}}</td>
					<td class="text-center">{{=childRow.groupImageStatus}}</td>
					<td class="text-center">{{=childRow.groupContentStatus}}</td>
					<td class="text-center">{{=childRow.startDate}}</td>

					<td class="text-center">{{=childRow.endDate}}</td>
					<td class="text-center">
						<form id="form_{{=childRow.groupId}}_{{=childKey}}" action="${getGroupDetailsUrl}">
							<input type="hidden" name="groupId" value="{{=childRow.groupId}}" />
							<input type="hidden" name="groupType" value="{{=childRow.groupTypeCode}}" />
						</form>
						<a href="javascript:;" onclick="document.getElementById('form_{{=childRow.groupId}}_{{=childKey}}').submit()" class="gs-item-details" data-id="{{=row.groupId}}">Details</a> <c:if test="${readonly !='yes'}">| <a href="javascript:;" class="delete-item" data-group-type="{{=childRow.groupTypeCode}}" data-group-id="{{=childRow.groupId}}">Delete</a></c:if>
					</td>
				</tr>
			{{ }) }}
		{{ } }}
	{{ }) }}
{{ }else { }}
	<tr><td colspan="8" align="center"><strong>No record Found!</strong></td></tr>
{{ } }}
</script>
<!-- Group search Table Row Template ends -->

<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery-ui.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/constants.js")%>"></script>
<script>
	app.Global.defaults.contextPath = '<%=response.encodeURL(request.getContextPath())%>';
</script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/underscore-min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.twbsPagination.min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/URLFactory.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/DataTableAjax.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/grouping.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/GroupFactory.js")%>"></script>
<script>
	app.URLFactory.urlCollection.SCGUrl = "${splitColorGroupingSubmit}";
	app.URLFactory.urlCollection.SSGUrl = "${splitSKUGroupingSubmit}";
	app.URLFactory.urlCollection.groupSearchUrl = "${searchGroupResourceRequest}";
	app.URLFactory.urlCollection.createGroupUrl = "${createGroupFormSubmitURL}";
	app.URLFactory.urlCollection.addComponentUrl = "${createGrpRenderUrl}";
	app.URLFactory.urlCollection.deleteGroupUrl = "${deleteGroupResourceRequest}";
	
	//init main SPA
	app.GroupLandingApp.init();
</script>