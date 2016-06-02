<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<div align="right" style="margin-bottom: 0.5cm" >	
			<c:out value="${LAN_ID}"/> &nbsp;	 
			<input type="button"   style="font-weight: bold" name="logout" value="Logout" 
			    onclick="grouping_logOut('<c:out value="${LAN_ID}"/>'); " />	
			
		 </div>
<div  class="cars_panel x-hidden">
	<div class="x-panel-header">
		Create Split SKU Grouping
	</div>
	
	<div class="x-panel-body">
		<div class="group-search-area">
			<form name="frmComponentSearch" id="frmComponentSearch" action="">
			<input type="hidden" name="fromPage" value="SearchSKU" />
			<table cellspacing="5" cellpadding="0" border="0" class="content-table">
				<tr>
					<td width="15%" align="right">
						<label for="s-grouping-id">Vendor Style Number#:</label>
					</td>
					<td>
						<input type="text" name="vendorStyleNo" id="vendorStyleNo" value="" />
					</td>
					<td width="20%" align="center">
						OR
					</td>
					<td width="15%" align="right">
						<label for="s-grouping-id">Style ORIN#:</label>
					</td>
					<td>
						<input type="text" name="styleOrinNo" id="styleOrinNo" value="" />
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="right">
						<input type="submit" value="Search" class="btn" id="search-components"/>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</div>
<div  class="cars_panel x-hidden hidden" id="search-result-panel" >
	<div class="x-panel-header">
		Search Result:
	</div>
	<div class="x-panel-body">
		<div class="group-search-area">
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
			<form name="selectedComponentForm" id="selectedComponentForm">
			<input type="hidden" name="selectedItems" value="" id="splitCheckboxValue" >
			<table cellpadding="0" cellspacing="0" border="1" class="content-table border-simple colored-row tree-grid" id="dataTable">
				<thead>
					<tr>
						<th width="10%"><label><input type="checkbox" id="select-all" /> Select All</label></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="StyleOrinNo" data-sorted-by="">Style ORIN#</a></th>
						<th width="10%">Style Number</th>
						<th width="15%"><a href="javascript:;" class="sortable" data-sort-column="productName" data-sorted-by="">Name</a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="colorCode" data-sorted-by="">Color Code</a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="colorName" data-sorted-by="">Color Name</a></th>
						<th width="8%"><a href="javascript:;" class="sortable" data-sort-column="size" data-sorted-by="">Size</a></th>
						<th width="10%">Default Size</th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="alreadyInGroup" data-sortedby="">Already in group</a></th>
					</tr>
				</thead>
				<tbody id="row-container"></tbody>
			</table>
			</form>
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
			<div class="pagination-container" style="text-align:right; margin-top: 15px;">
				<input type="button" class="btn" value="Split" style="width: 100px;" id="split-components" />
			</div>
		</div>
	</div>
</div>
<div class="group-search-footer-area">
	<div class="footer-content"><input type="button" onclick="window.location.href='/wps/portal/home/creategrouping'" class="btn" value="Close"></div>
</div>
<portlet:resourceURL var="ajaxUrl" id="splitAttributeSearch"></portlet:resourceURL>

<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery-ui.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/constants.js")%>"></script>

<div class="ui-widget" id="errorBox" style="display:none">
    <div class="ui-state-error ui-corner-all" style="padding: 15px;"> 
        <p>
            <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
            <span id="error-massege" >Sample ui-state-error style. </span>
        </p>
    </div>
</div>
<!-- Group Creation dialog starts -->
<div id="dlgGroupCreate" style="display:none">
	<div class="dialog-wrapper">
		<form action="${createAction}" name="createGroupForm" id = "createGroupForm" method="post">
		<table cellspacing="5" cellpadding="0" border="0" class="content-table" align="center">
			<tr>
				<td align="right">Grouping Type <span class="red-text">(*)</span>:</td>
				<td>
					<!-- placeholder hidden field for holding selected group type -->
					<input type="hidden" name="groupType" id="groupType" value="SSG" />
					<select name="groupTypeDropDown" id="groupTypeDropDown" disabled="disabled">
						<option value="CPG">Consolidated Product Grouping</option>
						<option value="SCG" >Split Color Grouping</option>
						<option value="SSG" selected="selected">Split SKU Grouping</option>
						<option value="RCG">Regular Collection Grouping</option>
						<option value="BCG">Beauty Collection Grouping</option>
						<option value="GSG">Group By Size Grouping</option>
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
<portlet:resourceURL id="submitCreateGroupForm" var="createGroupFormSubmitURL" />

<!-- Group Creation dialog ends -->

<!-- div loading starts-->
<div class="overlay_pageLoading hidden"><img src="<%=response.encodeURL(request.getContextPath())%>/img/loading.gif" alt="Loading.."></div>
<!-- div loading ends-->

<!-- Component Table Row Template starts -->
<script type="text/template" id="row-template">
{{ if(data.length){ }}
	{{_.each(data, function(row, key){ }}
		<tr>
			<td><input type="checkbox" name="selectedItem[]" value="{{=row.size}}" class="item-check" style="margin-left:7px" />
			<td class="text-center">{{=row.StyleOrinNo}}</td>
			<td class="text-center">{{=row.vendorStyleNo}}</td>
			<td>{{=row.productName}}</td>
			<td class="text-center">{{=row.colorCode}}</td>
			<td class="text-center">{{=row.colorName}}</td>
			<td class="text-center">{{=row.size}}</td>
			<td class="text-center"><input type="radio" name="defaultColor" value="{{=row.size}}" disabled="disabled" /></td>
			<td class="text-center">{{=row.alreadyInGroup}}</td>
		</tr>
	{{ }) }}	
{{ }else{ }}
	<tr>
		<td colspan="10" align="center"><strong>{{=dataHeader.message ? dataHeader.message : 'No record Found!'}}</strong></td>
	</tr>
{{ } }}
</script>
<!-- Component Table Row Template ends -->
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/underscore-min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.twbsPagination.min.js")%>"></script>
<script>
	app.Global.defaults.contextPath = '<%=response.encodeURL(request.getContextPath())%>';
</script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/DataTable.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/grouping.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/GroupFactory.js")%>"></script>
<script>
	app.GroupLandingApp.urlCollection.createGroupUrl = "${createGroupFormSubmitURL}";
	app.GroupLandingApp.urlCollection.splitComponentSearchUrl = "${ajaxUrl}";
	
	app.GroupLandingApp.init();
	app.SplitGroupLanding.init();
</script>