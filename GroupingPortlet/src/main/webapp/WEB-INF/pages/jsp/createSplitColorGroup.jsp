<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<fmt:setBundle basename="grouping" />
<portlet:resourceURL id="invalidate" var="logouturl" />
<div align="right" style="margin-bottom: 0.5cm" >	
			<c:out value="${LAN_ID}"/> &nbsp;	 
			<input type="button"   style="font-weight: bold" name="logout" value="Logout" 
			    onclick="grouping_logOut('<c:out value="${LAN_ID}"/>','${logouturl }'); " />	
			<input type="hidden" name="userId" value="${LAN_ID}" id="userId" />
		 </div>
<div  class="cars_panel x-hidden">
	<div class="x-panel-header">
		<fmt:message key="splitgroup.screen.level.header.scg" />
	</div>
	
	<div class="x-panel-body">
		<div class="group-search-area">
			<form name="frmComponentSearch" id="frmComponentSearch" action="" class="SCG-group-validation">
			<input type="hidden" name="fromPage" value="SearchColor" />
			<table cellspacing="5" cellpadding="0" border="0" class="content-table">
				<tr>
					<td width="15%" align="right">
						<label for="s-grouping-id"><fmt:message key="splitgroup.screen.level.vendorStyleNo" /></label>
					</td>
					<td>
						<input type="text" name="vendorStyleNo" id="vendorStyleNo" value="" />
					</td>
					<td width="20%" align="center">
						<fmt:message key="splitgroup.screen.level.or" />
					</td>
					<td width="15%" align="right">
						<label for="s-grouping-id"><fmt:message key="splitgroup.screen.level.styleOrinNo" /></label>
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
<div  class="cars_panel x-hidden hidden" id="search-result-panel">
	<div class="x-panel-header">
		<fmt:message key="group.screen.level.searchResult" />
	</div>
	<div class="x-panel-body">
		<div class="group-search-area" id="dataTable">
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
			
			<!-- Including jsp for split color having template and data table -->
			<%@ include file="/WEB-INF/pages/jsp/splitColorTemplate.jsp" %>
			
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
			<div class="pagination-container" style="text-align:right; margin-top: 15px;">
				<input type="button" class="btn" value="Split" style="width: 100px;" id="split-components" />
			</div>
		</div>
	</div>
</div>
<div class="group-search-footer-area">
	<div class="footer-content"><input type="button" onclick="window.location.href='/wps/portal/home/Grouping'" class="btn" value="Close"></div>
</div>
<portlet:resourceURL var="ajaxUrl" id="splitAttributeSearch"></portlet:resourceURL>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery-ui.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/constants.js")%>"></script>

<div class="ui-widget" id="errorBox" style="display:none">
    <div class="ui-state-error ui-corner-all" style="padding: 15px;"> 
        <p>
            <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
            <span id="error-massege" ><fmt:message key="splitgroup.screen.level.subHeader" /> </span>
        </p>
    </div>
</div>
<!-- Group Creation dialog starts -->
<div id="dlgGroupCreate" style="display:none">
	<div class="dialog-wrapper">
		<form action="${createAction}" name="createGroupForm" id = "createGroupForm" method="post">
		<table cellspacing="5" cellpadding="0" border="0" class="content-table" align="center">
			<tr>
				<td align="right"><fmt:message key="splitgroup.screen.level.groupingType" /> <span class="red-text">(*)</span>:</td>
				<td>
					<!-- placeholder hidden field for holding selected group type -->
					<input type="hidden" name="groupType" id="groupType" value="SCG" />

					<select name="groupTypeDropDown" id="groupTypeDropDown" disabled="disabled">
						<c:forEach var="groupTypesMap" items="${groupTypesMap}">                                                                                                
	                        <option value="${groupTypesMap.key}"  ${groupTypesMap.key == 'SCG' ? 'selected' : '' }><c:out value="${groupTypesMap.value}"/></option>
	                    </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right"><fmt:message key="splitgroup.screen.level.groupingName" /> <span class="red-text">(*)</span>:</td>
				<td>
					<input type="text" name="groupName" id="groupName" value="" class="" required />
					<div class="charlimit"><fmt:message key="splitgroup.screen.level.currentChars" /> <span id="nameCurChars">0</span> <fmt:message key="splitgroup.screen.level.maxChars" /> <span id="nameMaxChars">0</span></div>
				</td>
			</tr>
			<tr>
				<td align="right"><fmt:message key="splitgroup.screen.level.groupingDesc" /> <span class="red-text">(*)</span>:</td>
				<td>
					<textarea cols="10" rows="4" class="" name="groupDesc" id="groupDesc" required></textarea>
					<div class="charlimit"><fmt:message key="splitgroup.screen.level.currentChars" /> <span id="descCurChars">0</span> Min Chars:<span id="descMinChars">0</span> <fmt:message key="splitgroup.screen.level.maxChars" /> <span id="descMaxChars">0</span>
				</td>
			</tr>
			<tr class="optional-fields">
				<td align="right"><fmt:message key="splitgroup.screen.level.launchDate" /> <span class="red-text">(*)</span>:</td>
				<td>
					<input type="text" name="startDate" id="startDate" value="" class="" style="width: 80%"/>
				</td>
			</tr>
			<tr class="optional-fields">
				<td align="right"><fmt:message key="splitgroup.screen.level.endDate" /></td>
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
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/idle-timer.min.js")%>"></script>
<!-- Component Table Row Template ends -->
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/underscore-min.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.twbsPagination.min.js")%>"></script>
<script>
	app.Global.defaults.contextPath = '<%=response.encodeURL(request.getContextPath())%>';
</script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/URLFactory.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/DataTable.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/grouping.js")%>"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/GroupFactory.js")%>"></script>
<script>
	app.URLFactory.urlCollection.createGroupUrl = "${createGroupFormSubmitURL}";
	app.URLFactory.urlCollection.splitComponentSearchUrl = "${ajaxUrl}";
	
	app.GroupLandingApp.init();
	app.SplitGroupLanding.init();
</script>