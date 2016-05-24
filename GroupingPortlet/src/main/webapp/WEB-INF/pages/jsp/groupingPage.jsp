<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<fmt:setBundle basename="grouping" />
<div  class="cars_panel x-hidden">
	<div class="x-panel-header">
		<b><fmt:message key="group.screen.level.Groupings" /></b>
	</div>
	
	<div class="x-panel-body">
		<div class="group-create-area">
				<select id="groupTypeSelector">
					<c:forEach var="groupTypesMap" items="${groupTypesMap}">                                                                                                
                        <option value="${groupTypesMap.key}"  [b]selected="true"[/b] ><c:out value="${groupTypesMap.value}"/></option>              
                    </c:forEach>
				</select>
				<input type="button" class="btn" id="btnGroupCreate" value="Create Grouping">
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
						<input type="text" name="departments" id="s-grouping-dept" value="" />
						<input type="button" value="open" id="btnDlgClass"/>
					</td>
					<td width="10%" align="right">
						<label for="s-grouping-dept"><fmt:message key="group.screen.level.class" /></label>
					</td>
					<td>
						<input type="text" name="classes" id="s-grouping-dept" value="" />
						<input type="button" value="open" id="btnDlgClass"/>
					</td>
					<td width="10%" align="right">
						<label for="s-grouping-dept"><fmt:message key="group.screen.level.styleOrin" /></label>				
					</td>
					<td><input type="text" name="orinNumber" id="s-grouping-dept" value="" /></td>
				</tr>
				<tr>
					<td width="10%" align="right">
						<label for="s-grouping-dept"><fmt:message key="group.screen.level.vendorStyle" /></label>	
					</td>
					<td><input type="text" name="vendor" id="s-grouping-dept" value="" /></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="right" >
						<input type="hidden" value="SearchGroup" name="ResourceType" />
						<input type="reset" value="Clear" class="btn" />
						<input type="submit" value="Search" class="btn"  id="search-groups"/>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</div>
<div  class="cars_panel x-hidden">
	<div class="x-panel-header">
		<b><fmt:message key="group.screen.level.searchResult" /></b>
	</div>
	<div class="x-panel-body">
		<div class="group-search-area">
			<div class="pagination-container">
				<div class="pagination-left">
					<div class="pagination-left-wrapper">
						<label for="page-limit-1">Show: </label>
						<select name="page-limit-1" id="page-limit-1">
							<option value="10">10</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
						<span class="pagination-text">Showing 10 of 12 records</span>
					</div>
				</div>
				<div class="pagination-right">
					<ul class="paginator">
						<li><a href="#"><<</a></li>
						<li><a href="#" class="active">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">>></a></li>
					</ul>
				</div>
			</div>
			<div class="clearfix"></div>
			<table cellpadding="0" cellspacing="0" border="1" class="content-table border-simple colored-row tree-grid">
				<thead>
					<tr>
						<th width="15%"><a href="#" class="sorting-available"><b><fmt:message key="group.screen.level.table.header1" /></b></a></th>
						<th width="20%"><a href="#" class="sorting-available"><b><fmt:message key="group.screen.level.table.header2" /></b></a></th>
						<th width="15%"><a href="#" class="sorting-available"><b><fmt:message key="group.screen.level.table.header3" /></b></a></th>
						<th width="5%"><a href="#" class="sorting-available"><b><fmt:message key="group.screen.level.table.header4" /></b></a></th>
						<th width="5%"><a href="#" class="sorting-available"><b><fmt:message key="group.screen.level.table.header5" /></b></a></th>
						<th width="10%"><a href="#" class="sorting-available"><b><fmt:message key="group.screen.level.table.header6" /></b></a></th>
						<th width="10%"><a href="#" class="sorting-available"><b><fmt:message key="group.screen.level.table.header7" /></b></a></th>
						<th width="15%">&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<div class="node parent">
								<a href="#" class="expander collapse"><img src="<%=response.encodeURL(request.getContextPath())%>/img/collapsed.png" alt="Collapse" /></a>
								<span class="orin-group">RCG123456789</span>
							</div>
						</td>
						<td>Regular Collection Test1 </td>
						<td>Regular Collection</td>
						<td>Initiated</td>
						<td>Initiated</td>
						<td></td>
						<td></td>
						<td class="text-center"><a href="#">Details</a> | <a href="#">Delete</a></td>
					</tr>
					<tr>
						<td>
							<div class="node leaf">
								<span class="orin-group">RCG123456789</span>
							</div>
						</td>
						<td>Regular Collection Test1 </td>
						<td>Regular Collection</td>
						<td>Initiated</td>
						<td>Initiated</td>
						<td></td>
						<td></td>
						<td class="text-center"><a href="#">Details</a> | <a href="#">Delete</a></td>
					</tr>
					<tr>
						<td>
							<div class="node parent">
								<a href="#" class="expander"><img src="<%=response.encodeURL(request.getContextPath())%>/img/expand.png" alt="expand" /></a>
								<span class="orin-group">RCG123456789</span>
							</div>
						</td>
						<td>Regular Collection Test1 </td>
						<td>Regular Collection</td>
						<td>Initiated</td>
						<td>Initiated</td>
						<td></td>
						<td></td>
						<td class="text-center"><a href="#">Details</a> | <a href="#">Delete</a></td>
					</tr>
					<tr>
						<td>
							<div class="node parent">
								<a href="#" class="expander"><img src="<%=response.encodeURL(request.getContextPath())%>/img/expand.png" alt="expand" /></a>
								<span class="orin-group">RCG123456789</span>
							</div>
						</td>
						<td>Regular Collection Test1 </td>
						<td>Regular Collection</td>
						<td>Initiated</td>
						<td>Initiated</td>
						<td></td>
						<td></td>
						<td class="text-center"><a href="#">Details</a> | <a href="#">Delete</a></td>
					</tr>
					<tr>
						<td>
							<div class="node parent">
								<a href="#" class="expander"><img src="<%=response.encodeURL(request.getContextPath())%>/img/expand.png" alt="expand" /></a>
								<span class="orin-group">RCG123456789</span>
							</div>
						</td>
						<td>Regular Collection Test1 </td>
						<td>Regular Collection</td>
						<td>Initiated</td>
						<td>Initiated</td>
						<td></td>
						<td></td>
						<td class="text-center"><a href="#">Details</a> | <a href="#">Delete</a></td>
					</tr>
					<tr>
						<td>
							<div class="node parent">
								<a href="#" class="expander"><img src="<%=response.encodeURL(request.getContextPath())%>/img/expand.png" alt="expand" /></a>
								<span class="orin-group">RCG123456789</span>
							</div>
						</td>
						<td>Regular Collection Test1 </td>
						<td>Regular Collection</td>
						<td>Initiated</td>
						<td>Initiated</td>
						<td></td>
						<td></td>
						<td class="text-center"><a href="#">Details</a> | <a href="#">Delete</a></td>
					</tr>
					<tr>
						<td>
							<div class="node parent">
								<a href="#" class="expander"><img src="<%=response.encodeURL(request.getContextPath())%>/img/expand.png" alt="expand" /></a>
								<span class="orin-group">RCG123456789</span>
							</div>
						</td>
						<td>Regular Collection Test1 </td>
						<td>Regular Collection</td>
						<td>Initiated</td>
						<td>Initiated</td>
						<td></td>
						<td></td>
						<td class="text-center"><a href="#">Details</a> | <a href="#">Delete</a></td>
					</tr>
					<tr>
						<td>
							<div class="node root-leaf">
								<span class="orin-group">RCG123456789</span>
							</div>
						</td>
						<td>Regular Collection Test1 </td>
						<td>Regular Collection</td>
						<td>Initiated</td>
						<td>Initiated</td>
						<td></td>
						<td></td>
						<td class="text-center"><a href="#">Details</a> | <a href="#">Delete</a></td>
					</tr>
					<tr>
						<td>
							<div class="node parent">
								<a href="#" class="expander"><img src="<%=response.encodeURL(request.getContextPath())%>/img/expand.png" alt="expand" /></a>
								<span class="orin-group">RCG123456789</span>
							</div>
						</td>
						<td>Regular Collection Test1 </td>
						<td>Regular Collection</td>
						<td>Initiated</td>
						<td>Initiated</td>
						<td></td>
						<td></td>
						<td class="text-center"><a href="#">Details</a> | <a href="#">Delete</a></td>
					</tr>
					<tr>
						<td>
							<div class="node parent">
								<a href="#" class="expander"><img src="<%=response.encodeURL(request.getContextPath())%>/img/expand.png" alt="expand" /></a>
								<span class="orin-group">RCG123456789</span>
							</div>
						</td>
						<td>Regular Collection Test1 </td>
						<td>Regular Collection</td>
						<td>Initiated</td>
						<td>Initiated</td>
						<td></td>
						<td></td>
						<td class="text-center"><a href="#">Details</a> | <a href="#">Delete</a></td>
					</tr>
					<tr>
						<td>
							<div class="node parent">
								<a href="#" class="expander"><img src="<%=response.encodeURL(request.getContextPath())%>/img/expand.png" alt="expand" /></a>
								<span class="orin-group">RCG123456789</span>
							</div>
						</td>
						<td>Regular Collection Test1 </td>
						<td>Regular Collection</td>
						<td>Initiated</td>
						<td>Initiated</td>
						<td></td>
						<td></td>
						<td class="text-center"><a href="#">Details</a> | <a href="#">Delete</a></td>
					</tr>
				</tbody>
			</table>
			<div class="pagination-container">
				<div class="pagination-left">
					<div class="pagination-left-wrapper">
						<label for="page-limit-2">Show: </label>
						<select name="page-limit-2" id="page-limit-2">
							<option value="10">10</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
						<span class="pagination-text">Showing 10 of 12 records</span>
					</div>
				</div>
				<div class="pagination-right">
					<ul class="paginator">
						<li><a href="#"><<</a></li>
						<li><a href="#" class="active">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">>></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="group-search-footer-area">
	<div class="footer-content"><input type="button" onclick="history.back(1)" class="btn" value="Close"></div>
</div>



<portlet:actionURL var="createAction"> 
		<portlet:param name="action" value="createAction"/>
</portlet:actionURL>

<portlet:renderURL var="splitColorGroupingSubmit"> 
	<portlet:param name="groupingTypeSplitColor" value="splitColor" />
</portlet:renderURL>

<portlet:resourceURL var="searchGroupResourceRequest"> 
</portlet:resourceURL>

<portlet:renderURL var="splitSKUGroupingSubmit"> 
	<portlet:param name="groupingTypeSplitSKU" value="splitSKU" />
</portlet:renderURL>

<portlet:resourceURL id="submitCreateGroupForm" var="createGroupFormSubmitURL" />

<portlet:renderURL var="createGrpRenderUrl"> 
    <portlet:param name="createGroupSuccessRender" value="CreateGrpSuccess" />
</portlet:renderURL>
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
						<option value="CPG">Consolidated Product Grouping</option>
						<option value="SCG">Split Color Grouping</option>
						<option value="SSG">Split SKU Grouping</option>
						<option value="RCG">Regular Collection Grouping</option>
						<option value="BCG">Beauty Collection Grouping</option>
						<option value="GSS">Group By Size Grouping</option>
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
			<tr>
				<td align="right">Grouping Description <span class="red-text">(*)</span>:</td>
				<td>
					<textarea cols="10" rows="4" class="" name="groupDesc" id="groupDesc" required></textarea>
					<div class="charlimit">Current Chars: <span id="descCurChars">0</span> Max Chars: <span id="descMaxChars">0</span>
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
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery-ui.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/constants.js")%>"></script>
<script>
	app.Global.defaults.contextPath = '<%=response.encodeURL(request.getContextPath())%>';
</script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/grouping.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/GroupFactory.js")%>"></script>
<script>
	app.GroupLandingApp.urlCollection.SCGUrl = "${splitColorGroupingSubmit}";
	app.GroupLandingApp.urlCollection.SSGUrl = "${splitSKUGroupingSubmit}";
	app.GroupLandingApp.urlCollection.groupSearchUrl = "${searchGroupResourceRequest}";
	app.GroupLandingApp.urlCollection.createGroupUrl = "${createGroupFormSubmitURL}";
	app.GroupLandingApp.urlCollection.addComponentUrl = "${createGrpRenderUrl}";
	
	//init main SPA
	app.GroupLandingApp.init();
</script>