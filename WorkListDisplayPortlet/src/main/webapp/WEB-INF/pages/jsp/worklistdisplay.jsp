<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<head>
 
          
     <title>WorkFlow Display.</title>	 	 

<style type = "text/css">
.pre-visited, .pre-visited td{
    background-color: #8FBC8F !important;
}
tr.children td{#border:1px dashed #ddd;}
tr.cfas-row td{background-color:yellow !important;}
input.btn-new, input.btn-new:hover {
    background: #cedff5;
    border: 1px solid #aaaaaa;
    color: #15428b;
    cursor: pointer;
    #display: block;
    #float: left;
    #font-size: 13px;
    #font-weight: bold;
    #margin-right: 1px;
    #padding: 1px 0;
    text-decoration: none;
}
.dlg-custom .ui-widget-header{
	background: #369 repeat-x;
	color: #fff;
}
.dlg-custom .ui-dialog-titlebar-close {
  display: none;
}

.dlg-custom #advanceSearchDiv{
	overflow-x: hidden !important;
	overflow-y: hidden !important;
	height: 100%;
}
#search_tabs li.text label{
	clear: both;
}
#search_tabs{
	margin: 0 !important;
}
.treegrid-expander{
	display:none;
}
#overlay_pageLoading
{   
   position: fixed;
   width: 550px;
   height: 250px;
   top: 40%;
   left: 40%;	
   z-index: 9999;   
}
.web_dialog_petStatusPopUp
{
   display: none;
   position: fixed;
   width: 550px;
   height: 122px;
   top: 44%;
   left: 29%;
   margin-left: 80px;
   margin-top: -100px;
   background-color: #ffffff;
   border: 2px solid #336699;
   padding: 0px;
   z-index: 102;
   font-family: Verdana;
   font-size: 10pt;
   font: normal 11px tahoma,arial,helvetica,sans-serif;
}

#mainPetTable{
   table-layout:fixed;
   width: 100%;
}

#mainPetTable thead tr {
   display: block;
   position: relative;
}

#mainPetTable tbody {
   display: block;
   overflow: auto;
   width: 100%;
   height: 362px;
        }
#checkboxTD{
	width:7.3%;
	min-width:56px;
	max-width:56px;
	word-wrap:break-word;
}
#orinTD{
	width: 8.5%;
	min-width:67px;
	max-width:67px;
	word-wrap:break-word;
}
#deptTD{
	width: 5%;
	min-width:34px;
	max-width:34px;
	word-wrap:break-word;
}
#nameTD{
	width: 13%;
	min-width:108px;
	max-width:108px;
	word-wrap:break-word;
}
#styleTD{
	width: 10.3%;
	min-width:83px;
	max-width:83px;
	word-wrap:break-word;
}
#prdTD{
	width: 15%;
	min-width:126px;
	max-width:126px;
	word-wrap:break-word;
}
#contentTD{
	width: 7.8%;
	min-width:60px;
	max-width:60px;
	word-wrap:break-word;
}
#imageTD{
	width: 7.8%;
	min-width:60px;
	max-width:60px;
	word-wrap:break-word;
}
#statusTD{
	width: 8.3%;
	min-width:65px;
	max-width:65px;
	word-wrap:break-word;
}
#dateTD{
	width: 8%;
	min-width:74px;
	max-width:74px;
	word-wrap:break-word;
}
#petTD{
	width: 9%;
	min-width:45px;
	max-width:45px;
	word-wrap:break-word;
}


</style>

</head>

<portlet:defineObjects />
<fmt:bundle basename="workListDisplay">
<portlet:actionURL var="formAction"> 
		<portlet:param name="action" value="workListDisplaySubmit"/>
</portlet:actionURL>	
<portlet:resourceURL var="ajaxUrl"> 
</portlet:resourceURL>
<portlet:resourceURL id="invalidate" var="logouturl" />
<input type="hidden" id="ajaxaction" name="ajaxaction" value="${ajaxUrl}"></input>		
<div id="content">
<div id="main">

<form>
	<input type="hidden" id="clearLockOnBackPage" name="clearLockOnBackPage" value="0">
</form>

<script>

var lockClearOnBack=document.getElementById('clearLockOnBackPage');
if(lockClearOnBack.value=='1'){
	window.location.reload();
}
lockClearOnBack.value='1';

</script>

<form:form commandName="workflowForm" method="post" action="${formAction}" name="workListDisplayForm" id="workListDisplayForm" onload="onloaddept()" >
	
	<input type="hidden" id="username" name="username" value="<c:out value='${workflowForm.pepUserID}'/>" />
	<input type="hidden" id="imageStatus" name="imageStatus" value=""/>
	<input type="hidden" id="contentStatus" name="contentStatus" value=""/>
	<input type="hidden" id="selectedGroupId" name="selectedGroupId" value=""/>
	<input type="hidden" id="groupingType" name="groupingType" value=""/>
	<input type="hidden" id="searchResultInput" name="searchResultInput" value="${workflowForm.advanceSearch.searchResults}"/>
    <input type="hidden" id="selectedOrin" name="selectedOrin" value=""/>
    <input type="hidden" name="selectedPageNumber" id="sel-page-num" value="${workflowForm.selectedPage}" />
    <input type="hidden" id="selectedParentOrin" name="selectedParentOrin" value=""/>
    <input type="hidden" id="stylepetstatid" name="stylepetstatid" value=""/>
    <input type="hidden" id="stylecolorpetstatid" name="stylecolorpetstatid" value=""/>
    <input type="hidden"  name="createManualPet" id="createManualPet" value=""/>
    <!-- Belk PIM Phase - II. Change for Create Grouping - Start. AFUPYB3 -->
    <input type="hidden"  name="goToGrouping" id="goToGrouping" value=""/>
    <!-- Belk PIM Phase - II. Change for Create Grouping - End -->
	<input type="hidden" id="petStatus" name="petStatus" value=""/>    
    
    <input type="hidden" id="searchReturnId" name="searchReturnId" value="false" />	
    <input type="hidden" id="groupSearchResult" name="groupSearchResult" value="" />
	
	<!--Commented Belk Best Plan for displaying DCA afuszr6-->
	<!--<c:if test="${isInternal =='yes' && workflowForm.readOnlyUser == 'no'}"> 
		<div style="float:left;width: 200px;margin-bottom: 0.5cm;"><a href="<c:out value='${BEBESTPLAN}'/>" target="_blank">Belk Best Plan</a></div>
	</c:if>-->
	<c:if test="${isInternal =='no'}"> 
		<div style="float:left;width: 200px;margin-bottom: 0.5cm;"><a href="<c:out value='${BEBESTPLAN}'/>" target="_blank">Belk Best Plan</a></div>
	</c:if>	
	
	<div align="left" style="display: inline; padding: 5px 10px;margin-bottom: 0.5cm" >
<input type="button" style="padding: 5px 10px;font-weight: bold" name="home" value="Home" onclick=goToHomeScreen('${ajaxUrl}'); />
</div>

		 <div align="right" style="margin-bottom: 0.5cm" >	
			<c:out value="${workflowForm.pepUserID}"/> &nbsp;	 
			<input type="button"   style="font-weight: bold" name="logout" value="Logout" 
			    onclick=logout_home('${logouturl}');  />	
			
		 </div>
 <span>

 </span>
        <input type="hidden" name="prevVisitedOrin" value="${prevVisitedOrin}" id="visited-orin-num"/>
		<div  class="cars_panel x-hidden" >
		<div class="x-panel-header">
				<fmt:message key="worklist.filterby.dept.label"/>
			<input type ="text" name="deptNo"  id="deptNo"  size="13px;" onchange="getpetdetails()" /> 
			<input type="button" id="btnShowDept1" value="<fmt:message key="worklist.dept.label"/>" onclick="showDeptPopup()" />
			<c:if test="${isInternal =='yes'}"> 
				<c:if test="${workflowForm.readOnlyUser !='yes'}"> 
					<input type="button"  id=" createPet" value="<fmt:message key="worklist.create.mannual.label"/>" 
					    onclick=createmannualpet();  class="createpetbutton"/>  
				 </c:if>  
				    
				<input type="button"  id="createGrouping" value="<fmt:message key="worklist.grouping.label"/>" 
				    onclick=createGroup();  class="creategroupingbutton"/>  

				 <c:if test="${workflowForm.readOnlyUser !='yes'}">   
					
						<input type="button" name="delete" value="<fmt:message key="worklist.inactivate.pet.label"/>" 
						    onclick=inactivateAjaxCall(); class="inactivepetsbutton"/>
					
				</c:if>
				<c:if test="${workflowForm.readOnlyUser !='yes'}"> 
					<input type="button" name="delete" value="<fmt:message key="worklist.activate.pet.label"/>" 
					    onclick=activateAjaxCall();  class="activepetbutton"/>	
				 </c:if>  
			</c:if>
			
			<input type="hidden" id="inActivateid" name="inActivateid" value=""></input>
			<input type="hidden" id="activatePetid" name="activatePetid" value=""></input>
			
			<input type="button" onClick=advSearch(); value="<fmt:message key="worklist.search.pet.label"/>" class="searchpetbutton">
		</div> 
				
				<c:if test="${isPetAvailable =='yes'}">
				
				<c:set var="contextpath" value="<%=response.encodeURL(request.getContextPath())%>"></c:set>
				<c:set var="imagemidpath" value="/img/"></c:set>
				<c:set var="imagename" value="${workflowForm.sortingImage}"></c:set>
				<div id="tableStart"></div>
				<div class="x-panel-body" id="petTable" name="petTable">
				<c:if test="${isInternal =='yes'}">
					Show:
						<select name="workListType" id="workListType" onchange="document.getElementById('workListDisplayForm').submit()" style="width: auto; margin-right:10px;margin-bottom:4px;">
							<option value="Regular PET" <c:if test="${'Groupings'!= workflowForm.workListType}"> selected="selected" </c:if>>Item PET</option>
							<option value="Groupings" <c:if test="${'Groupings'== workflowForm.workListType}"> selected="selected" </c:if>>Grouping PET</option>
						</select>
				</c:if>
				<input type="hidden" id="updateDateMessage" name="updateDateMessage" value="${workflowForm.updateCompletionDateMsg}"/>
					<c:choose>
                    	<c:when test="${ (workflowForm.searchClicked != null && fn:toLowerCase(workflowForm.searchClicked) == 'yes') || 
                    		(workflowForm.advSearchClicked != null && fn:toLowerCase(workflowForm.advSearchClicked)  == 'yes') }">
                            <c:if test="${workflowForm.totalNumberOfPets ne '0'}">
                            	<span class="pagebanner">
                                &nbsp;<c:out value="${workflowForm.totalNumberOfPets}"/>
                                <fmt:message key="worklist.pets.found.displaying.label"/>
                                <c:out value="${workflowForm.startIndex}"/>&nbsp;to&nbsp;<c:out value="${workflowForm.endIndex}"/>&nbsp;of&nbsp;<c:out value="${workflowForm.totalNumberOfPets}"/>
                                <c:if test="${workflowForm.displayPagination == 'yes'}">
                                    <fmt:message key="worklist.pagination.left.sq.bracket.label"/>
                                    <c:choose>
                                        <c:when test="${workflowForm.selectedPage ne '1' }" >
                                         <a href="#" onclick="getThePageContent('1')"><fmt:message key="worklist.pagination.first.label"/></a>
                                        </c:when>
                                        <c:otherwise>
                                        <fmt:message key="worklist.pagination.first.label"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <fmt:message key="worklist.pagination.left.slash.label"/>
                                        <c:choose>
                                            <c:when test="${workflowForm.selectedPage ne '1' }" >
                                             <a href="#" onclick="getThePageContent('${workflowForm.previousCount}')"><fmt:message key="worklist.pagination.prev.label"/></a>
                                            </c:when>
                                            <c:otherwise>
                                            <fmt:message key="worklist.pagination.prev.label"/>
                                            </c:otherwise>
                                        </c:choose>
                                    <fmt:message key="worklist.pagination.right.sq.bracket.label"/>
                                    <c:forEach items="${workflowForm.pageNumberList}" var="pageNumber" varStatus="status">
                                        <c:choose>
                                            <c:when test="${pageNumber == workflowForm.selectedPage}" >
                                            <c:out value="${pageNumber}"/>
                                            </c:when>
                                            <c:otherwise>
                                            <a href="#" onclick="getThePageContent('${pageNumber}')"> <c:out value="${pageNumber}"/> </a>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test="${pageNumber ne workflowForm.totalPageno}"> ,</c:if>
                                    </c:forEach>
                                    <fmt:message key="worklist.pagination.left.sq.bracket.label"/>
                                        <c:choose>
                                            <c:when test="${workflowForm.selectedPage ne workflowForm.totalPageno }" >
                                             <a href="#" onclick="getThePageContent('${workflowForm.nextCount}')"><fmt:message key="worklist.pagination.next.label"/></a>
                                            </c:when>
                                            <c:otherwise>
                                            <fmt:message key="worklist.pagination.next.label"/>
                                            </c:otherwise>
                                        </c:choose>
                                    <fmt:message key="worklist.pagination.left.slash.label"/>
                                        <c:choose>
                                            <c:when test="${workflowForm.selectedPage ne workflowForm.totalPageno }" >
                                             <a href="#" onclick="getThePageContent('${workflowForm.totalPageno}')"><fmt:message key="worklist.pagination.last.label"/></a>
                                            </c:when>
                                            <c:otherwise>
                                            <fmt:message key="worklist.pagination.last.label"/>
                                            </c:otherwise>
                                        </c:choose>
                                    <fmt:message key="worklist.pagination.right.sq.bracket.label"/>
                                </c:if>
                            </span>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                    	<span class="pagebanner">
                            <fmt:message key="worklist.pagination.page.label" />&nbsp;<c:out value="${workflowForm.selectedPage}" />&nbsp;
                            <fmt:message key="worklist.displaying.label"/>&nbsp;<c:out value="${workflowForm.totalNumberOfPets}"/>&nbsp;<fmt:message key="worklist.records.label"/>&nbsp;&nbsp;
                            <c:if test="${workflowForm.displayPagination == 'yes'}">
                            <fmt:message key="worklist.pagination.left.sq.bracket.label"/>&nbsp;
                                    <c:choose>
                                        <c:when test="${workflowForm.selectedPage ne '1' }" >
                                         <a href="#" onclick="getThePageContent('${workflowForm.previousCount}')"><fmt:message key="worklist.pagination.prev.label"/></a>
                                        </c:when>
                                        <c:otherwise>
                                        <fmt:message key="worklist.pagination.prev.label"/>
                                        </c:otherwise>
                                    </c:choose>|
                                    <c:choose>
                                        <c:when test="${workflowForm.selectedPage < workflowForm.totalPageno }" >
                                         <a href="#" onclick="getThePageContent('${workflowForm.nextCount}')"><fmt:message key="worklist.pagination.next.label"/></a>
                                        </c:when>
                                        <c:otherwise>
                                        <fmt:message key="worklist.pagination.next.label"/>
                                        </c:otherwise>
                                    </c:choose>
                            &nbsp;<fmt:message key="worklist.pagination.right.sq.bracket.label"/>
                            </c:if>
                            </span>
                    </c:otherwise>
                </c:choose>
				<hr style="margin:4px; margin-bottom:10px;">
				<div>
				<!-- Image Loading message starts -->
					<div id="overlay_pageLoading" style="display:none;">
						<img src="${contextpath}${imagemidpath}loading.gif" height="100px;"height="100px;" />
					</div>
					<div id="overlay_pageLoading1" style="display:none;position:absolute;top:50%;left:50%">
						<img src="${contextpath}${imagemidpath}loadingDept.gif" height="100px;"height="100px;" />
					</div>
				<!-- Image Loading message ends -->
				
				<div id="overlay_petStatus" class="web_dialog_imageUploadPopUp"></div>
				<div id="dialog_petStatus" style="display:none">
		<div id="content">
		<!-- <div class="x-panel-header" id="petStatusID">
			Pet Status
		</div> -->
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<div class="ui-widget">
							<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
								<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								<strong id="petStatusLabelId"></strong></p>
							</div>
						</div>
			<ul>
				<!-- <li>				
					<label id = "petStatusLabelId" style="margin-left:53px;height: 16px;"></label>
				</li> -->				
			</br>
				<li class="buttons" style="float:right;">
					<input class="btn-new ui-button ui-corner-all ui-widget" id="petStatusPopUpClose" type="button" onclick="$('#dialog_petStatus').dialog('close');" name="Close" value="Close" />
				</li>
			</ul>

		</div>
	</div>
</div>
						<table  cellpadding="0"  class="table tree2 table-bordered table-striped table-condensed" id="mainPetTable" name="mainPetTable"  >
							<thead >
							<tr>
							<th id="checkboxTD"><input  class="selectAllCheckBox" type="checkbox" name="selectAllRow" id="selectAllRow">
							</th>
							<th id="orinTD">
							<a href="#" onclick="columnsorting('orinGroup')"><fmt:message key="worklist.orin.group.label"/>#</a>
							<c:if test="${workflowForm.selectedColumn =='orinGroup'}">
								<img src="${contextpath}${imagemidpath}${imagename}"> </img>
							</c:if>
							</th>
							<th id="deptTD">
							<a href="#" onclick="columnsorting('dept')"><fmt:message key="worklist.column.dept.label"/>#</a>
							<c:if test="${workflowForm.selectedColumn =='dept'}">
								<img src="${contextpath}${imagemidpath}${imagename}"> </img>
							</c:if>
							</th>
							<th id="nameTD">
							<a href="#" onclick="columnsorting('vendorName')"><fmt:message key="worklist.column.vendor.name.label"/></a>
							<c:if test="${workflowForm.selectedColumn =='vendorName'}">
								<img src="${contextpath}${imagemidpath}${imagename}"> </img>
							</c:if>
							</th>
							<th id="styleTD">
							<a href="#" onclick="columnsorting('vendorStyle')"><fmt:message key="worklist.column.vendor.style.label"/></a>
							<c:if test="${workflowForm.selectedColumn =='vendorStyle'}">
								<img src="${contextpath}${imagemidpath}${imagename}"> </img>
							</c:if>
							</th>
							<th id="prdTD">
							<a href="#" onclick="columnsorting('productName')"><fmt:message key="worklist.column.product.name.label"/></a>
							<c:if test="${workflowForm.selectedColumn =='productName'}">
								<img src="${contextpath}${imagemidpath}${imagename}"> </img>
							</c:if>
							</th>
							<th id="contentTD">
							<a href="#" onclick="columnsorting('contentStatus')"><fmt:message key="worklist.column.content.status.label"/></a>
							<c:if test="${workflowForm.selectedColumn =='contentStatus'}">
								<img src="${contextpath}${imagemidpath}${imagename}"> </img>
							</c:if>
							</th>
							<th id="imageTD"><a href="#" onclick="columnsorting('imageStatus')"><fmt:message key="worklist.column.img.status.label"/></a>
							<c:if test="${workflowForm.selectedColumn =='imageStatus'}">
								<img src="${contextpath}${imagemidpath}${imagename}"> </img>
							</c:if>
							<th id="statusTD"><a href="#" onclick="columnsorting('petStatus')"><fmt:message key="worklist.column.pet.status.label"/></a>
							<c:if test="${workflowForm.selectedColumn =='petStatus'}">
								<img src="${contextpath}${imagemidpath}${imagename}"> </img>
							</c:if>
							</th>		
							<th id="dateTD"><a href="#" onclick="columnsorting('completionDate')"><fmt:message key="worklist.column.icompletion.date.label"/></a>
							<c:if test="${workflowForm.selectedColumn =='completionDate'}">
								<img src="${contextpath}${imagemidpath}${imagename}"> </img>
							</c:if>
							</th>
							<th id="petTD"><a href="#" onclick="columnsorting('petSourceType')">PET Source</a>
							<c:if test="${workflowForm.selectedColumn =='petSourceType'}">
								<img src="${contextpath}${imagemidpath}${imagename}"> </img>
							</c:if>					
							</th>
							</tr>
							</thead>
							<tbody  class="scrollbarset" id="table-container">
									<input type="hidden" id="hidden_roleEditable" name="hidden_roleEditable" value="${workflowForm.roleEditable}"/>
									<input type="hidden" id="hidden_readOnlyUser" name="hidden_readOnlyUser" value="${workflowForm.readOnlyUser}"/>
									<input type="hidden" id="hidden_roleName" name="hidden_roleName" value="${workflowForm.roleName}"/>
									<input type="hidden" id="callType" name="callType" value=""/>
									<input type="hidden" id="searchClicked" name="searchClicked" value="${workflowForm.searchClicked}" />
									
									
									
											
							<!-- Table Grid logic Start -->
									<c:set var="subcount" value="260" />
									<c:set var="countList" value="0" />
									<c:if test="${workflowForm.petNotFound ne null}"><tr><td colspan="11"><c:out value="${workflowForm.petNotFound}"/></td></tr> </c:if>
									<c:forEach items="${workflowForm.workFlowlist}" var="workFlow"
										varStatus="status">
											<tr id="parent_${workFlow.orinNumber}" name="tablereport" class="treegrid-${countList} <c:if test="${isInternal == 'yes' && workFlow.CFAS =='true'}">cfas-row</c:if>" data-group="${workFlow.isGroup}">
											<td align="center" id="checkboxTD">
											
												<div style="display: inline;margin:-17px 0 0 0px">
													<img id="parentSpan_${workFlow.orinNumber}_expand" onclick="expandCollapse('${workFlow.orinNumber}','${workflowForm.searchClicked}', '${workFlow.isGroup}')" src="${contextpath}${imagemidpath}expand.png" style="cursor:pointer;border:0;" width="14" />
													
													<img id="parentSpan_${workFlow.orinNumber}_collapsed" onclick="expandCollapse('${workFlow.orinNumber}','${workflowForm.searchClicked}', '${workFlow.isGroup}')" src="${contextpath}${imagemidpath}collapsed.png" style="display:none;cursor:pointer" style="cursor:pointer;border:0;" width="14"/>
												<c:if test="${workFlow.isGroup != 'Y'}"	>							
													<input type="checkbox" name="styleItem" id="styleItem" class="checkbox1" value="${workFlow.orinNumber}_${workFlow.petStatus}" onclick="childCheckedRows(this,'${workFlow.orinNumber}','${workflowForm.searchClicked}', '${workFlow.isGroup}' );getPetStatValue('${workFlow.petStatus}')" style="margin:0;" data-group="${workFlow.isGroup}"/>
												</c:if>
												<c:if test="${workFlow.isGroup == 'Y'}"	>
													<input type="checkbox" name="styleItem" id="styleItem" class="checkbox1" value="${workFlow.orinNumber}_${workFlow.petStatus}##${workFlow.entryType}" onclick="getPetStatValue('${workFlow.petStatus}')" style="margin:0;" data-group="${workFlow.isGroup}"/>
												</c:if>
											</div>
											
											</td>
											<td id="orinTD"><c:out value="${workFlow.orinNumber}" />
											<c:if test="${workFlow.omniChannelVendor =='Y' && workflowForm.roleEditable =='yes'}">*</c:if>
											<c:if test="${isInternal == 'yes' && workFlow.existsInGroup =='Y'}">
												<img src="${contextpath}${imagemidpath}grouping_indicator.png" alt="In Grouping" width="9" style="position: relative; top: -8px;" />
											</c:if>
											</td>
											<td id="deptTD"><c:out value="${workFlow.deptId}" />
											</td>
											<td id="nameTD"><c:out value="${workFlow.vendorName}" />
											</td>
											<td id="styleTD"><c:out value="${workFlow.vendorStyle}" />
											<input type="hidden" id="${workFlow.orinNumber}_vendorStyle" value="${workFlow.vendorStyle}"/>
											</td>
											<td id="prdTD"><c:out value="${workFlow.productName}" />
											<input type="hidden" id="${workFlow.orinNumber}_productName" value="${workFlow.productName}"/>
											</td>
											<td id="contentTD">
											   
												<c:choose>									    
													<c:when test="${workflowForm.roleEditable =='yes' && workFlow.vendorStyle != 'Split SKU Grouping' && workFlow.vendorStyle != 'Split Color Grouping'}" >										
													  <a href="#" onclick="contentStatus('<c:out value="${workFlow.contentStatus}"/>','<c:out value="${workFlow.orinNumber}"/>','<c:out value="${workFlow.vendorStyle}"/>',event)"><c:out value="${workFlow.contentStatus}"/></a>
													</c:when>
													<c:when test="${workflowForm.readOnlyUser =='yes' && workFlow.vendorStyle != 'Split SKU Grouping' && workFlow.vendorStyle != 'Split Color Grouping'}" >	
													   <a href="#" onclick="contentStatus('<c:out value="${workFlow.contentStatus}"/>','<c:out value="${workFlow.orinNumber}"/>','<c:out value="${workFlow.vendorStyle}"/>',event)"><c:out value="${workFlow.contentStatus}"/></a>
													</c:when>
													<c:otherwise>
													   <c:out value="${workFlow.contentStatus}" />
													</c:otherwise>
												</c:choose>
											</td>
												<td id="imageTD">
												<c:choose>
													<c:when test="${workflowForm.roleEditable =='yes' && workFlow.vendorStyle != 'Consolidated Product Grouping' && workFlow.vendorStyle != 'Split SKU Grouping' && workFlow.vendorStyle != 'Split Color Grouping'}" >
													  <a href="#" onclick="imageStatus('<c:out value="${workFlow.imageStatus}"/>','<c:out value="${workFlow.orinNumber}"/>','${workFlow.petStatus}', event)"><c:out value="${workFlow.imageStatus}"/></a>										
													</c:when>
													
													<c:when test="${workflowForm.readOnlyUser =='yes' && workFlow.vendorStyle != 'Consolidated Product Grouping' && workFlow.vendorStyle != 'Split SKU Grouping' && workFlow.vendorStyle != 'Split Color Grouping'}" >	
													   <a href="#" onclick="imageStatus('<c:out value="${workFlow.imageStatus}"/>','<c:out value="${workFlow.orinNumber}"/>','${workFlow.petStatus}', event)"><c:out value="${workFlow.imageStatus}"/></a>
													</c:when>
													
													<c:otherwise>
													<c:out value="${workFlow.imageStatus}" />
													</c:otherwise>
												</c:choose>
												</td>
											<td id="statusTD"><c:out value="${workFlow.petStatus}" />
											<input type="hidden" id="${workFlow.orinNumber}_petStatus" value="${workFlow.petStatus}"/>
											</td>		
											<td id="dateTD">																
											<c:if test = "${workFlow.petStatus ne 'Deactivated'}">										
												<c:out value="${workFlow.completionDate}" />
											</c:if>							
											<input type="hidden" id="${workFlow.orinNumber}_CmpDate" name="${workFlow.orinNumber}_CmpDate" value="${workFlow.completionDate}"/>
											<input type="hidden" id="tbisPCompletionDateNull_${workFlow.orinNumber}" name="tbisPCompletionDateNull_${workFlow.orinNumber}" value="${workFlow.isPCompletionDateNull}" />
											</td>
											
											<td id="petTD"><c:out value="${workFlow.sourceType}" /></td>
											
											<!-- <td style="display: none;"><c:out value="${workFlow.petStatus}" />
											</td> -->
											
										</tr>
									
									
										<c:set var="countList" value="${countList+1}" />
									</c:forEach> 
									<tr id="CHILDTR_ID" name="CHILDTR_NAME"
												class="" style="display:none">
												<td id="checkboxTD">
												<div style="padding-left: 15px;">
													<input type="checkbox" parentOrinNo="#CHBOX_PARENTORIN" name="selectedStyles" class="checkbox1"
													value="#CH_STYLE_ORIN_#CH_STYLE_PETSTATUS" onclick="getStyleColorPetStatValue('#CHBOXONCLK_PETSTATUS')" style="margin:16px 0 0 5px" data-group="#GROUP_FLAG"/>
												</div>
												</td>
												<td id="orinTD">#TD_ORIN</td>
												<td id="deptTD">#TD_DEPT_NUM</td>
												<td id="nameTD">#TD_VENDOR_NAME</td>
												<td id="styleTD">#TD_VENDOR_STYLE</td>
												<td id="prdTD">#TD_PRODUCT_NAME</td>
												<td id="contentTD">
												<a href="#" onclick="contentStatus('#CONTENT_STATUS','#CON_PARENT_ORIN','',event)">#TD_CONTENT_STATUS</a>
												</td>
												<td id="imageTD">
												<a href="#" onclick="imageStatus('#IMAGE_STATUS','#IMG_PARENT_ORIN','#PET_STATUS',event)">#TD_IMAGE_STATUS</a>
												</td>
												<td id="statusTD">#TD_PET_STATUS
													<input type="hidden" id="#CON_PARENT_ORIN_petStatus" value="#TD_PET_STATUS"/>
												</td>
												<td id="dateTD">
												<div id="#CMP_SEC1" style="display:#SEC1_STYLE_STATUS" class="editable-cc-date-#CON_CHILD_ORIN">
												<!--<input type="text" id="tCompletionDate" name="tCompletionDate" value=''  
												onchange="checkcompletiondate(this,'#SUBCOUNT_RANDOM')"/>-->
												#CMP_TXT_CMP_DT
													<input type="hidden" id="tbCompletionDate#SUBCOUNT_RANDOM" name="tbCompletionDate#SUBCOUNT_RANDOM" value='#CMP_HIDDEN_STYLE_CMP_DT' class = '#CMP_PARENT_ORIN_child '/>										 
													<input type="hidden" id="tbOrinNumber#SUBCOUNT_RANDOM" name="tbOrinNumber#SUBCOUNT_RANDOM" value='#CMP_HIDDEN_PARENT_ORIN'/>
													<input type="hidden" id="tbStyleOrinNumber#SUBCOUNT_RANDOM" name="tbStyleOrinNumber#SUBCOUNT_RANDOM" value='#CMP_HIDDEN_STYLE_ORIN'/>
												</div>
												<div id="#CMP_SEC2" style="display: #SEC2_STYLE_STATUS;" class="readonly-cc-date-#CON_CHILD_ORIN">
												<input type="hidden" id="tbCompletionDate#SUBCOUNT_RANDOM" name="tbCompletionDate#SUBCOUNT_RANDOM"
												value='#CMP_TXT_CMP_DT' class = '#CMP_PARENT_ORIN_child '/>
												 #TD_COMPLETION_DATE
												</div>
												</td>
												<td id="petTD">#TD_SOURCE_TYPE</td>
												<!-- <td style="display: none;">#TD_PET_STATUS</td> -->
											</tr>	
							<!--  Table Grid Login End -->	       
							</tbody>
							</table>
				
				<!-- Popup content -->
					<div id="overlay_Dept" class="web_dialog_overlay_advSearch_popup"></div> 
					<div id="tableDeptStart"></div>
					<div  id="deptTable" name="deptTable">					
					<!---------------  -->
						<div id="dialog_Dept" name="dialog_Dept" style="padding: 10px; border: 2px solid #336699; height: 90%">
						
					   <table class="deptmaintable" cellpadding="3" cellspacing="0" >
					      <tbody>
						  <!-- <tr>
					         <td class="web_dialog_title"><fmt:message key="worklist.dept.filter.department.label"/></td>
					         <td class="web_dialog_title align_right">           
					         </td>
					      </tr>
					      <tr>
					         <td>&nbsp;</td>
					         <td>&nbsp;</td>
					      </tr>
						  <tr>  
						 </tr> --> 
					         
							<tr>
							 
					            <td colspan="2">
								&nbsp;&nbsp;&nbsp;
								 <input id="btnClearDept1" type="button" value="<fmt:message key="worklist.dept.filter.clear.label"/>" onclick="depSearch('depClear')"/> &nbsp;
								<input id="btnSaveDept1" type="button" value="<fmt:message key="worklist.dept.filter.save.close.label"/>" onclick="depSearch('depSaveClose')"/> &nbsp;
									<input id="btnCloseDept1" type="button" value="<fmt:message key="worklist.dept.filter.close.label"/>" onclick="depSearch('depClose')"/>   								 
								</td>								
					            							
							   
					       </tr>
						   <tr>
					          <td id="searchDeptResultsId"><c:out  value="${workflowForm.deptSearchResult}"/></td>
					         <td>&nbsp;</td>
					      </tr>	  
						</tbody>
						
						</table>
						
						<table class="deptintable" align="center"  >	 
						   <tbody>						   
						  
						  <tr>
							   <td>
							  
								   <table border="0" width="100%" >
								        
										 
										   <tr>
										     
											<td colspan="3"  >	Dept # <input type="text" name ="selectedDeptSearch" id ="selectedDeptSearch" class="selectedDeptSearchtxtbox"/>
												<input id="btnSearch1" type="button" value="<fmt:message key="worklist.dept.filter.search.label"/>" onclick="depSearch('depSearch')"/>  
											</td>
											
										  </tr> 
										  <tr style="height : 2px">
											<td colspan="3" >
											&nbsp;
										  </td>
											
										  </tr> 
										
									</table>										
								   
								   						<table width="100%" border="0">
															
															 
															  
															<tr class="deptintabletrheading"  >
																		<th width="8%" style="border:1px solid black;">
																			<input type="checkbox" id="selectAllDeptOnSearch" checked="checked" style="display:none;"/>
																		</th>
																		<th width="20%" style="border:1px solid black;font-weight: bold">Dept #</th> 
																		<th width="71%" style="border:1px solid black;font-weight: bold">Dept Description</th>
																  </tr>		
															 
															 
														</table>
														 <div class="scrollbarsetdept">
														  <table width="100%">
															<tbody>
															
															 <c:forEach items="${workflowForm.selectedDepartmentFromDB}" var="sdfdb"
																		varStatus="status">
																		<tr name="abc" >
																		
																			<td width="8%" style="border:1px solid black" align="center" >
																				<input type="checkbox" name="chkSelectedDept"  id="chkSelectedDept" checked="checked" class="deptcheckbox"
																					value="<c:out  value="${sdfdb.id}"/>" /></td>
																			<td width="20%" align="center" style="border:1px solid black"><c:out value="${sdfdb.id}" /></td>
																			
																			<td width="71%" align="left"  style="border:1px solid black" >&nbsp;<c:out value="${sdfdb.desc}" /></td>																		
																			
																		</tr>
															 </c:forEach>	

															 
															 <c:forEach items="${workflowForm.searchedDeptdetailsFromADSE}" var="sddfa"
																					varStatus="status">
																					
																<tr name="abc" >
																		
																			<td width="8%" style="border:1px solid black" align="center">
																				<input type="checkbox" name="chkSelectedDept"  id="chkSelectedDept" class="deptcheckbox" 
															value="<c:out  value="${sddfa.id}"/>" /></td>
																			<td width="20%" align="center" style="border:1px solid black"><c:out value="${sddfa.id}" /></td>
																			
																			<td width="71%" align="left"  style="border:1px solid black" >&nbsp;<c:out value="${sddfa.desc}" /></td>																		
																			
																		</tr>				
																					
																					
																	
															</c:forEach>	
															
															<c:forEach items="${workflowForm.firstTimesearchedDeptdetailsFromADSE}" var="ftsddfa"
																		varStatus="status">
																	<tr name="abc" >
																		<td width="8%"  align="center" style="border:1px solid black;border:1px solid black">
																		<input type="checkbox" name="chkSelectedDept"  id="chkSelectedDept" class="deptcheckbox"
															value="<c:out  value="${ftsddfa.id}"/>" /></td>
																		<td width="20%" align="center" style="border:1px solid black;border:1px solid black"><c:out value="${ftsddfa.id}" /></td>
																		<td width="71%" align="left"  style="border:1px solid black;border:1px solid black" >&nbsp;<c:out value="${ftsddfa.desc}" /></td>
																	</tr>
															</c:forEach>
									
								   
								   </tbody></table>
								  
								  </div>
								 </td>
						  </tr>		 
								 
						  
						  
						 
						 	
						  
						</tbody></table>	  
					   
			  		
			  		
				</div>
				
				<!-- ------------ -->
				</div><div id="tableDeptEnd"></div>	
					<!-- popup content end -->
				<!-- Search Content Start -->
				<div id="overlay_Image_advSearch" class="web_dialog_overlay_advSearch">
				
				<!-- Search Loading Santanu Popup -->	
					
				</div>
				<div id="advTableStart"></div>
				<div id="advanceSearchDiv" name="advanceSearchDiv" style="background-color:#f0f0f0">
				<div id="dialog_ASearch" class="search_web_dialog_search" name="dialog_ASearch" style="background-color:#f0f0f0">
				<div id="search_tabs">
					<div id="login1" title="" class="tab">
						<div>
						     <input type="button"  value="<fmt:message key="worklist.adv.jsp.main.search.button.label"/>" onclick="searchSearch();setHiddenFieldValue();"/>
						     <input type="button"  value="<fmt:message key="worklist.adv.jsp.main.clear.button.label"/>" onclick="searchClear();"/> 
						     <input type="button"  value="<fmt:message key="worklist.adv.jsp.main.reset.button.label"/>" onclick="searchReset();" />	
						     <input type="button" value="<fmt:message key="worklist.adv.jsp.main.close.button.label"/>" onclick="searchClose();" value=""/>				
										
							</div>
							<br />							
							<p>Enter Your Search Criteria below.</p>
										
												<ol>
										<!-- New radio for Search Results starts -->
										<c:if test="${isInternal =='yes'}">
										<li class="text">
											<label for="SearchResults"><fmt:message key="worklist.adv.jsp.main.search.results.body.label"/></label>
											<input type="radio" id="searchResults" name="searchResults" value="includeGrps" <c:if test="${workflowForm.advanceSearch.searchResults == 'includeGrps'}"> checked="checked" </c:if>onchange="toggleEnability()"/>
											<label1 for="Include Groupingss"><fmt:message key="worklist.adv.jsp.main.searchResults.includeGrps.body.label"/></label1>
											<input type="radio" id="searchResults" name="searchResults" value="showItemsOnly" <c:if test="${workflowForm.advanceSearch.searchResults == 'showItemsOnly'}"> checked="checked" </c:if> onchange="toggleEnability()" />
											<label1 for="Items Only"><fmt:message key="worklist.adv.jsp.main.searchResults.showItemsOnly.body.label"/></label1>
										</li>
										</c:if>
										<c:if test="${isInternal !='yes'}">
											<input type="radio" id="searchResults" name="searchResults" value="showItemsOnly" checked="checked" style="visibility:hidden" readonly="readonly" />
										</c:if>
										<li class="text" style="width :440px">
											<label for="DeptNo"><fmt:message key="worklist.adv.jsp.main.deptno.body.label"/></label>
											<input type="text" id="adDeptNo" name="adDeptNo" value="${workflowForm.advanceSearch.deptNumbers}" />						
											<input type="button" id="btnShowDepts1" value="Open" onclick="searchSearchDeptopen();" />
											<input type="hidden" id="AdseachClicked" name="AdseachClicked" value="${workflowForm.advSearchClicked}" />
											
											
					      
										</li>
										<li class="text">
											<label for="Completion_Date_Range_From"><fmt:message key="worklist.adv.jsp.main.from.date.body.label"/></label>
											<input type="text" id="datepicker1" name="datepicker1" value="${workflowForm.advanceSearch.dateFrom}"  />					
											<input type="hidden" id="fromdateValueHolder" name="fromdateValueHolder" value="${workflowForm.advanceSearch.dateFrom}"  />
										</li>
					
					 					<li class="text">
											<label for="Completion_Date_Range_To"><fmt:message key="worklist.adv.jsp.main.to.date.body.label"/></label>
											<input type="text" id="datepicker2" name="datepicker2" value="${workflowForm.advanceSearch.dateTo}"  />
											<input type="hidden" id="todateValueHolder" name="todateValueHolder" value="${workflowForm.advanceSearch.dateTo}"  />
										</li>
					 					
										<li class="text">
											<label for="Image Status"><fmt:message key="worklist.adv.jsp.main.image.status.body.label"/></label>
					
											<input type="text" id="advImageStatus" name="advImageStatus" value="${workflowForm.advanceSearch.imageStatus}"/>
											<input type="button" id="btnShowSimpleImages1" value="Open" onclick="searchimageStatusopen();" />
										</li>
										<li class="text">
											<label for="Content Status"><fmt:message key="worklist.adv.jsp.main.content.status.body.label"/></label>				
					
											<input type="text" id="advContentStatus" name="advContentStatus" value="${workflowForm.advanceSearch.contentStatus}"/>
											<input type="button" id="btnShowSimpleContents1" value="Open" onclick="searchcontentStatusopen();" />
										</li>
										
										<li class= "text">
											<label for="Content Status"><fmt:message key="worklist.adv.jsp.main.pet.status.body.label"/></label>
											<input type="radio" name="petActive" id="petActive"  value='yes' <c:if test="${'01'== workflowForm.advanceSearch.active}"> checked="checked" </c:if> onclick="petactiveclicked(); disableStyleOrin(this.value);"> Active
											<input type="radio" name="petInActive"  id="petInActive" value='yes' <c:if test="${'05'== workflowForm.advanceSearch.inActive}"> checked="checked" </c:if> onclick="petinactiveclicked(); disableStyleOrin(this.value);"> InActive
											<input type="radio" name="petClosed" id="petClosed"  value='yes' <c:if test="${'06'== workflowForm.advanceSearch.closed}"> checked="checked" </c:if> onclick="petclosedclicked(); disableStyleOrin(this.value);"> Closed
										</li>
										
										<li class="text">
											<label for="Request Type"><fmt:message key="worklist.adv.jsp.main.request.type.body.label"/></label>						
					
											<input type="text" id="advRequestType" name="advRequestType" value="${workflowForm.advanceSearch.requestType}" />
											<input type="button" id="btnShowReqTypes1" value="Open" onclick="searchRequestTypeopen();"/>
										</li>
					
										<li class="text" style="width :440px;">
											<label for="ORIN"><fmt:message key="worklist.adv.jsp.main.orin.body.label"/></label>
											<input type="text" id="advOrin" name="advOrin" maxlength="9" onchange="javascript:disableStatus(this);" onblur="enableDisableVendorStyle('advOrin','advVenNumber','advUpc');" value="${workflowForm.advanceSearch.orin}" />				
										
										</li>
										<li class="text">
											<label for="Vendor Style"><fmt:message key="worklist.adv.jsp.main.vendor.style.body.label"/> #:</label>
											<input type="text" id="advVendorStyle" name="advVendorStyle" maxlength="30"  onblur="isAlphaNumberVendorStyle();" value="${workflowForm.advanceSearch.vendorStyle}"/>
											
										</li>
										<li class="text">
											<label for="UPC"><fmt:message key="worklist.adv.jsp.main.upc.body.label"/></label>
											<input type="text" id="advUpc" name="advUpc"  maxlength="14"  onblur="isNumberUpc();enableDisableVendorStyle('advOrin','advVenNumber','advUpc');" value="${workflowForm.advanceSearch.upc}"/>
										</li>
										<li class="text">
											<label for="Vendor Number"><fmt:message key="worklist.adv.jsp.main.vendor.number.body.label"/></label>
											<input type="text" id="advVenNumber" name="advVenNumber"  maxlength="13"  onblur="isVendorNumber();" value="${workflowForm.advanceSearch.vendorNumber}"/>
										</li>
										 <li class="text">						
											<label for="Class Number"><fmt:message key="worklist.adv.jsp.main.class.no.body.label"/></label>
											<input type="text" id="advClassNumber" name="advClassNumber" value="${workflowForm.advanceSearch.classNumber}"/>
											<input   type="button" id="btnClassNos1" value="Open" onclick="searchClassNumberopen();" />
										</li>
					 					
										
										<li class= "text">
											<label for="Content Status"><fmt:message key="worklist.adv.jsp.main.created.today.body.label"/></label>
											<label1 for="Yes"><fmt:message key="worklist.adv.jsp.main.created.today.yes.body.label"/></label1>
											<input type="radio" id="rdCretedToday" name="rdCretedToday" value="yes" <c:if test="${workflowForm.advanceSearch.createdToday == 'yes'}"> checked="checked" </c:if>/>
											<label1 for="No"><fmt:message key="worklist.adv.jsp.main.created.today.no.body.label"/></label1>
											<input type="radio" id="rdCretedToday" name="rdCretedToday" value="no" <c:if test="${workflowForm.advanceSearch.createdToday == 'no'}"> checked="checked" </c:if> />
										</li>
										<c:if test="${isInternal =='yes'}">
										<!-- Grouping ID and name search options Starts-->
										<li class="text">
											<label for="Grouping ID"><fmt:message key="worklist.adv.jsp.main.grouping.id.body.label"/></label>
											<input type="text" id="groupingID" name="groupingID" value="${workflowForm.advanceSearch.groupingID}"/>
										</li>
										
										<li class="text">
											<label for="Grouping Name"><fmt:message key="worklist.adv.jsp.main.grouping.name.body.label"/></label>
											<input type="text" id="groupingName" name="groupingName" value="${workflowForm.advanceSearch.groupingName}"/>
										</li>
										<!-- Grouping ID and name search options Ends-->
										</c:if>
										<c:if test="${isInternal !='yes'}">
											<input type="hidden" id="groupingID" name="groupingID" value=""/>
											<input type="hidden" id="groupingName" name="groupingName" value=""/>
										</c:if>
					                 </ol>
							
							
					    </div>
					
					<!-- --------------------------- -->
				</div>
				</div>
				
				<!-- pop ups -->
				<!-- Class Details start -->
				
				<div id="overlay_ClassNo" class="web_dialog_overlay_advSearch_popup"></div>
    
				<div id="dialog_ClassNo" class="search_web_dialog_class_new" style="background-color:#f0f0f0">
				   <div style="border: 2px solid #336699;margin: 5px auto;padding: 10px; height: 86%;">
					   <table style="width: 100%; border: 0px;" cellpadding="3" cellspacing="0">
						  <!--<tr>
							 <td class="web_dialog_title"><fmt:message key="worklist.adv.jsp.popup.class.no.header.label"/></td>
							 <td class="web_dialog_title align_right">
							   
							 </td>
						  </tr>
						  <tr>
							 <td>&nbsp;</td>
							 <td>&nbsp;</td>
						  </tr>
						  <tr>  
						 </tr> 
						   
						 
						  <tr>
							 <td>&nbsp;</td>
							 <td>&nbsp;</td>
						  </tr> -->
						   
							<tr>
						 <td colspan="2" align="center">	 
								<input id="btnClearClassNos1" type="button" value="<fmt:message key="worklist.adv.jsp.popup.class.no.button.clear.label"/>" onclick="searchClassNumberClear();" />        
								<input id="btnSaveClassNos1" type="button" value="<fmt:message key="worklist.adv.jsp.popup.class.no.button.sac.label"/>" onclick="searchClassNumberSaveAndClose();" />        
								<input id="btnCloseClassNos1" type="button" value="<fmt:message key="worklist.adv.jsp.popup.class.no.button.close.label"/>"  onclick="searchClassNumberClose();"/>       
							 </td>	   
						   </tr>
						   <tr>
							  <td>&nbsp;</td>
							 <td>&nbsp;</td>
						  </tr>
						 </table> 
						<div class="table-container-uidlg" style="height: 150px; overflow:auto;">
						<table align="center"  border="1" width="100%">	
						   
							
					  <tr style="background-color:#f0f0f0" >
						<th class="popupborderline"></th>
						<th class="popupborderline"><fmt:message key="worklist.adv.jsp.popup.class.no.caption.class.label"/> #</th> 
						<th class="popupborderline"><fmt:message key="worklist.adv.jsp.popup.class.no.caption.class.desc.label"/></th>
					  </tr>
					 
					  <c:forEach items="${workflowForm.advanceSearch.classDetails}" var="ascd"
														varStatus="ascdstatus">
														<tr  >
															<td  class="popupborderline">
															<input type="checkbox" name="advChkSelectionClassNo" id ="advChkSelectionClassNo" value="${ascd.id}" class="advClassscheckboxclass" /> 
															</td>
															<td class="popupborderline"><c:out value="${ascd.id}" /></td>
															<td class="popupborderline"><c:out value="${ascd.desc}" /></td>
														</tr>
								</c:forEach>
						  <tr>
							 <td>&nbsp;</td>
							 <td>&nbsp;</td>
						  </tr>
						  <tr>
							 
						  </tr>
					   </table>
					   </div>
					</div>
				</div>
				
				<!-- Class Details end -->	
				<!-- Image Details start -->
				<div id="overlay_Image" class="web_dialog_overlay_advSearch_popup"></div>
					
					<div id="dialog_Image" class="search_web_dialog_imageaction_new">
						<div style="border: 2px solid #336699;margin: 5px auto;padding: 10px; height: 81%">
					   <table style="width: 100%; border: 0px;" cellpadding="3" cellspacing="0" align="center">
					      <!-- <tr>
					         <td class="web_dialog_title"><fmt:message key="worklist.adv.jsp.popup.image.header.label"/></td>
					         <td class="web_dialog_title align_right">
					            <!--<a href="#" id="btnClose">Close</a>-->
					      <!--   </td>
					      </tr>
					      <tr>
					         <td>&nbsp;</td>
					         <td>&nbsp;</td>
					      </tr>
						  <tr>  
						 </tr> 
					       
						 
					      <tr>
					         <td>&nbsp;</td>
					         <td>&nbsp;</td>
					      </tr>
						   -->
							<tr>
						 <td colspan="2" align="center">	 
					            <!--<input id="btnSearch" type="button" value="Search" />-->        
					            <input id="btnClearImages1" type="button" value="<fmt:message key="worklist.adv.jsp.popup.image.button.clear.label"/>" onclick="searchImageClear();" />        
					            <input id="btnSaveImages1" type="button" value="<fmt:message key="worklist.adv.jsp.popup.image.button.sac.label"/>" onclick="searchImageSave();" />        
					            <input id="btnCloseImages1" type="button" value="<fmt:message key="worklist.adv.jsp.popup.image.button.close.label"/>" onclick="searchImageClose();" />       
							 </td>	   
					       </tr>
						   <tr>
					          <td>&nbsp;</td>
					         <td>&nbsp;</td>
					      </tr>
						  
						  
					 <table style="width:100%" border="1" align="center">
					  <tr>
					    <th class="popupborderline"></th>
					    <th class="popupborderline"><fmt:message key="worklist.adv.jsp.popup.image.caption.image.status.label"/></th>		
					  
					  </tr>
					  <c:forEach items="${workflowForm.advanceSearch.imageStatusDropDown}" var="imagedropvalue"
							varStatus="imagedropvalueststus">
							 <tr>
							    <td align="center" class="popupborderline"><input type="checkbox" name ="advChkImageStatus" id="advChkImageStatus" <c:if test="${imagedropvalue.checked =='yes'}"> checked="checked" </c:if> value="${imagedropvalue.value}" class="advImgcheckboxclass" />  </td>
							    <td class="popupborderline">${imagedropvalue.value}</td>		
							  </tr>
						</c:forEach>
					</table>
					 
					      <tr>
					         <td>&nbsp;</td>
					         <td>&nbsp;</td>
					      </tr>
					      <tr>
					         
					      </tr>
					   </table>
					   </div>
					</div>
				<!--  Image Details End -->
				<!-- Content Status Start-->
				<div id="overlay_Content" class="web_dialog_overlay_advSearch_popup"></div>
    
				<div id="dialog_Content" class="search_web_dialog_content_new">
					<div style="border: 2px solid #336699;margin: 5px auto;padding: 10px; height: 82%;">
					   <table style="width: 100%; border: 0px;" cellpadding="3" cellspacing="0">
						  <!-- <tr>
							 <td class="web_dialog_title"><fmt:message key="worklist.adv.jsp.popup.content.header.label"/></td>
							 <td class="web_dialog_title align_right">
							 </td>
						  </tr>
						  <tr>
							 <td>&nbsp;</td>
							 <td>&nbsp;</td>
						  </tr>
						  <tr>  
						 </tr> 
						   
						 
						  <tr>
							 <td>&nbsp;</td>
							 <td>&nbsp;</td>
						  </tr> -->
						   
							<tr>
						 <td colspan="2" align="center">	 
								<!--<input id="btnSearch" type="button" value="Search" />-->       
								<input id="btnClearContents1" type="button" value="<fmt:message key="worklist.adv.jsp.popup.content.button.clear.label"/>" onclick="searchContentClear();" />        
								<input id="btnSaveContents1" type="button" value="<fmt:message key="worklist.adv.jsp.popup.content.button.sac.label"/>" onclick="searchContentSaveAndClose();"/>        
								<input id="btnCloseContents1" type="button" value="<fmt:message key="worklist.adv.jsp.popup.content.button.close.label"/>" onclick="searchContentClose();" />       
							 </td>	   
						   </tr>
						   <tr>
							  <td>&nbsp;</td>
							 <td>&nbsp;</td>
						  </tr>
						  
						  
					
					<table style="width:100%" border="1" align="center">
					  <tr>
						<th class="popupborderline"></th>
						<th class="popupborderline"><fmt:message key="worklist.adv.jsp.popup.content.caption.image.status.label"/></th>		
					  
					  </tr>
					  <c:forEach items="${workflowForm.advanceSearch.contentStatusDropDown}" var="contentdropvalue"
								varStatus="contentdropvalueststus">
								 <tr>
									<td align="center" class="popupborderline"><input type="checkbox" name ="advChkContentStatus" id="advChkContentStatus" <c:if test="${contentdropvalue.checked =='yes'}"> checked="checked" </c:if> value="${contentdropvalue.value}" class="advContcheckboxclass"/>  </td>
									<td class="popupborderline">${contentdropvalue.value}</td>		
								  </tr>
							</c:forEach>
					</table>
					
						  
						  <tr>
							 <td>&nbsp;</td>
							 <td>&nbsp;</td>
						  </tr>
						  <tr>
							 
						  </tr>
					   </table>
					</div>
				</div>
				<!-- Content Status End -->
				<!-- Image Start -->
				
				<!-- Image End -->
				<!-- Request Type -->
				<div id="overlay_ReqType" class="web_dialog_overlay_advSearch_popup"></div>
    
				<div id="dialog_ReqType" class="search_web_dialog_request_new">
				   <div style="border: 2px solid #336699;margin: 5px auto;padding: 10px; height: 82%;">
					   <table style="width: 100%; border: 0px;" cellpadding="3" cellspacing="0">
						  <!-- <tr>
							 <td class="web_dialog_title"><fmt:message key="worklist.adv.jsp.popup.request.header.label"/></td>
							 <td class="web_dialog_title align_right">
								<!--<a href="#" id="btnClose">Close</a>-->
						   <!--  </td>
						  </tr>
						  <tr>
							 <td>&nbsp;</td>
							 <td>&nbsp;</td>
						  </tr>
						  <tr>  
						 </tr> 
						   
						 
						  <tr>
							 <td>&nbsp;</td>
							 <td>&nbsp;</td>
						  </tr> -->
						   
							<tr>
						 <td colspan="2" align="center">	 
									   
								<input id="btnClearReqType" type="button" value="<fmt:message key="worklist.adv.jsp.popup.request.button.clear.label"/>" onclick="searchRequestTypeClear();"/>        
								<input id="btnSaveReqType" type="button" value="<fmt:message key="worklist.adv.jsp.popup.request.button.sac.label"/>" onclick="searchRequestTypeSaveAndClose();"/>        
								<input id="btnCloseReqType" type="button" value="<fmt:message key="worklist.adv.jsp.popup.request.button.close.label"/>" onclick="searchRequestTypeClose();"/>       
							 </td>	   
						   </tr>
						   <tr>
							  <td>&nbsp;</td>
							 <td>&nbsp;</td>
						  </tr>
						  
					</table>  
					 <table style="width:100%" border="1" align="center">
					  <tr>
						<th class="popupborderline"></th>
						<th class="popupborderline"><fmt:message key="worklist.adv.jsp.popup.request.caption.image.status.label"/></th>		
					  
					  </tr>
					  <c:forEach items="${workflowForm.advanceSearch.requestTypeDropDown}" var="requestdropvalue"
								varStatus="requestdropvalueststus">
								 <tr>
									<td align="center" class="popupborderline"><input type="checkbox" name="advChkRequestType" id="advChkRequestType" <c:if test="${requestdropvalue.checked =='yes'}"> checked="checked" </c:if> value="${requestdropvalue.value}" class="advReqcheckboxclass"/>  </td>
									<td class="popupborderline">${requestdropvalue.value}</td>		
								  </tr>
							</c:forEach>
					  
					</table>
					</div>
				</div>
				<!-- Request Type End -->
				<!-- pop ups End-->	
				</div><div id="advTableEnd"></div>
				<!-- Search Content End -->	
					
			</div><div id="tableEnd"></div>
			</c:if>
		</div>
	</form:form>
	</div>
 </div>
		
</fmt:bundle>		
  
<script type="text/template" id="group_items_template">
{{ if(data[0].noChildMessage){ }}
	<tr data-tr-root="{{=data[0].rootOrin}}" id="parent_{{=data[0].rootOrin}}" class="children" name="child_{{=orinNum}}" {{=(showHideFlag !== undefined && showHideFlag == false) ? 'style="display:none"' : '' }}>
			<td style="width:34px">&nbsp;</td>
			<td colspan="10" align="center"><strong>{{=data[0].noChildMessage}}</strong></td>
	</tr>
{{ }else{ }}
	{{_.each(data, function(item, key){ }}
	{{ var random = Math.random().toString(); random= random.substring(random.indexOf('.')+1, 10) }}
		{{ if(item.isGroup == 'Y'){  }}
		<tr class="children" name="child_{{=orinNum}}" id="parent_{{=item.styleOrinNum}}" {{=uniqueIdentifier !== undefined ? 'rel="parent_'+item.styleOrinNum + '_' + uniqueIdentifier +'"' : ''}} {{=(showHideFlag !== undefined && showHideFlag == false) ? 'style="display:none"' : '' }} data-tr-root="{{=orinNum}}">
		{{ }else{  }}
		<tr class="children" name="child_{{=orinNum}}" id="parent_{{=item.styleOrinNum}}_{{=random}}" {{=(showHideFlag !== undefined && showHideFlag == false) ? 'style="display:none"' : '' }} data-tr-root="{{=orinNum}}">
		{{ } }}
		<td id="checkboxTD">
			<div style="padding-left: 15px;">
			{{ if(item.isGroup == 'Y'){  }}
				<img id="parentSpan_{{=item.styleOrinNum}}_expand" onclick="expandCollapse('{{=item.styleOrinNum}}', '{{=item.advSearchClicked}}', '{{=item.isGroup}}', '{{=uniqueIdentifier===undefined ? '' : uniqueIdentifier}}', '{{=orinNum}}')" src="${contextpath}${imagemidpath}expand.png"  
					style="cursor:pointer;border:0;" width="14" rel="parentSpan_{{=item.styleOrinNum}}_{{=random}}_expand" />
				<img id="parentSpan_{{=item.styleOrinNum}}_collapsed" onclick="expandCollapse('{{=item.styleOrinNum}}','{{=item.advSearchClicked}}', '{{=item.isGroup}}', '{{=uniqueIdentifier===undefined ? '' : uniqueIdentifier}}', '{{=orinNum}}')" src="${contextpath}${imagemidpath}collapsed.png"  
					style="display:none;cursor:pointer" style="cursor:pointer;border:0;" width="14" rel="parentSpan_{{=item.styleOrinNum}}_{{=random}}_collapsed" />
					
					<input type="checkbox" name="styleItem" id="styleItem" class="checkbox1" value="{{=item.styleOrinNum}}_{{=item.petStatus}}##{{=item.ENTRY_TYPE}}" onclick="getPetStatValue('{{=item.petStatus}}')" style="margin:0;" data-group="{{=item.isGroup}}"/>
			{{ }else{  }}
				<img id="parentSpan_{{=item.styleOrinNum}}_{{=random}}_expand" onclick="expandStyleColorCollapse('{{=item.styleOrinNum}}', '{{=random}}')" src="${contextpath}${imagemidpath}expand.png"  
					style="cursor:pointer;border:0;" width="14" />
				<img id="parentSpan_{{=item.styleOrinNum}}_{{=random}}_collapsed" onclick="expandStyleColorCollapse('{{=item.styleOrinNum}}', '{{=random}}')" src="${contextpath}${imagemidpath}collapsed.png"  
					style="display:none;cursor:pointer" style="cursor:pointer;border:0;" width="14" />
				
				<input type="checkbox" name="styleItem" id="styleItem" class="checkbox1" value="{{=item.styleOrinNum}}_{{=item.petStatus}}" onclick="selectChildStylesUnderGroup(this,'{{=item.styleOrinNum}}_{{=random}}');getPetStatValue('{{=item.petStatus}}')" style="margin:0;" data-group="{{=item.isGroup}}"/>
			{{ } }}
			</div>
		</td>
		<td id="orinTD">
			{{=item.styleOrinNum}}
			{{if(item.existsInGroup=='Y'){ }} 
				<img src="${contextpath}${imagemidpath}grouping_indicator.png" alt="In Grouping" width="9" style="position: relative; top: -8px;" />
			{{ } }}
		</td>
		<td id="deptTD">{{=item.deptId}}</td>
		<td id="nameTD">{{=item.vendorName}}</td>
		<td id="styleTD">{{=item.vendorStyle}}</td>
		<td id="prdTD">{{=item.productName}}</td>
		<td id="contentTD">
		{{ if(item.vendorStyle != 'Split SKU Grouping' && item.vendorStyle != 'Split Color Grouping') { }}
			<a href="#" onclick="contentStatus('{{=item.contentStatus}}','{{=item.styleOrinNum}}','',event)">{{=item.contentStatus}}</a>
		{{ }else{ }}
			{{=item.contentStatus}}
		{{ } }}
		</td>
		<td id="imageTD">
		{{ if(item.vendorStyle != 'Consolidated Product Grouping' && item.vendorStyle != 'Split SKU Grouping' && item.vendorStyle != 'Split Color Grouping') { }}
		<a href="#" onclick="imageStatus('{{=item.imageStatus}}','{{=item.styleOrinNum}}','{{=item.petStatus}}',event)">{{=item.imageStatus}}</a>
		{{ }else{ }}
			{{=item.imageStatus}}
		{{ } }}
		</td>
		<td id="statusTD">{{=item.petStatus}}
			<input type="hidden" id="{{=orinNum}}_petStatus" value="{{=item.petStatus}}"/>
		</td>
		<td id="dateTD">
		<!----- -->
		{{ var chldOrinNo = item.styleOrinNum.split(' ');chldOrinNo = chldOrinNo.join('');chldOrinNo = chldOrinNo.length >=12 ? chldOrinNo.substring(0, 12) : chldOrinNo; }}
		<div id="#CMP_SEC1" style="display:#SEC1_STYLE_STATUS" class="editable-cc-date-{{=chldOrinNo}}">
		<!--<input type="text" id="tCompletionDate" name="tCompletionDate" value=''  
		onchange="checkcompletiondate(this,'#SUBCOUNT_RANDOM')"/>-->
		{{=item.completionDate}}
		{{ var random_subcount = ""+Math.random();random_subcount = random_subcount.substring(2,10); }}
			<input type="hidden" id="tbCompletionDate{{=random_subcount}}" name="tbCompletionDate{{=random_subcount}}" value='{{=item.completionDate}}' class ='{{=orinNum}}_child'/>										 
			<input type="hidden" id="tbOrinNumber{{=random_subcount}}" name="tbOrinNumber{{=random_subcount}}" value='{{=orinNum}}'/>
			<input type="hidden" id="tbStyleOrinNumber{{=random_subcount}}" name="tbStyleOrinNumber{{=random_subcount}}" value='{{=item.styleOrinNum}}'/>
		</div>
		
		
		<!----- -->
		<!-- <div id="#CMP_SEC2" style="display: #SEC2_STYLE_STATUS;" class="readonly-cc-date-{{=chldOrinNo}}">
		<input type="hidden" id="tbCompletionDate{{=random_subcount}}" name="tbCompletionDate{{=random_subcount}}"
		value='{{=item.completionDate}}' class ='{{=orinNum}}_child '/>
			{{=item.completionDate}}
		</div> -->
		</td>
		<td id="petTD">{{=item.petSourceType}}</td>
		<!-- <td style="display: none;">#TD_PET_STATUS</td> -->
	</tr>
		{{ if(item.isGroup != 'Y'){  }}
			{{ if(item.colorList[0] !== undefined && item.colorList[0].noChildMessage === undefined){ }}
				
				{{ _.each(item.colorList, function(childItem, childKey){ }}
					<tr class="" data-tr-root="{{=orinNum}}" name="child_{{=item.styleOrinNum}}_{{=random}}" id="parent_{{=childItem.styleOrinNum.replace(/\s/g, '')}}" style="display:none">
						<td id="checkboxTD">
							<div style="padding-left: 15px;">
								<input type="checkbox" parentOrinNo="{{=item.styleOrinNum}}_{{=random}}" name="selectedStyles" class="checkbox1" value="{{=childItem.styleOrinNum}}_{{=childItem.petStatus}}" onclick="getStyleColorPetStatValue('{{=childItem.petStatus}}')" data-group="{{=childItem.isGroup}}" />
							</div>
						</td>		
						<td id="orinTD">{{=childItem.styleOrinNum}}
						{{if(childItem.existsInGroup=='Y'){ }} 
							<img src="${contextpath}${imagemidpath}grouping_indicator.png" alt="In Grouping" width="9" style="position: relative; top: -8px;" />
						{{ } }}</td>
						<td id="deptTD">{{=childItem.deptId}}</td>
						<td id="nameTD">{{=childItem.vendorName}}</td>
						<td id="styleTD">{{=childItem.vendorStyle}}</td>
						<td id="prdTD">{{=item.productName}}</td>
						<td id="contentTD">
						{{ if(childItem.vendorStyle != 'Split SKU Grouping' && childItem.vendorStyle != 'Split Color Grouping') { }}
							<a href="#" onclick="contentStatus('{{=childItem.contentStatus}}','{{=item.styleOrinNum}}','',event)">{{=childItem.contentStatus}}</a>
						{{ }else{ }}
							{{=childItem.contentStatus}}
						{{ } }}
						</td>
						<td id="imageTD">
						{{ if(childItem.vendorStyle != 'Consolidated Product Grouping' && childItem.vendorStyle != 'Split SKU Grouping' && childItem.vendorStyle != 'Split Color Grouping') { }}
							<a href="#" onclick="imageStatus('{{=childItem.imageStatus}}','{{=item.styleOrinNum}}','{{=childItem.petStatus}}',event)">{{=childItem.imageStatus}}</a>
						{{ }else{ }}
							{{=childItem.imageStatus}}
						{{ } }}
						</td>
						<td id="statusTD">{{=childItem.petStatus}}
							<input type="hidden" id="{{=item.styleOrinNum}}_petStatus" value="{{=childItem.petStatus}}"/>
						</td>
						<td id="dateTD">
							{{=childItem.completionDate}}
						</td>
						<td id="petTD">{{=childItem.petSourceType}}</td>
						<!-- <td style="display: none;">#TD_PET_STATUS</td> -->
					</tr>
				{{ }) }}
			{{ }else{ }}
				<tr class="" data-tr-root="{{=orinNum}}" id="parent_{{=item.styleOrinNum}}" name="child_{{=item.styleOrinNum}}_{{=random}}" style="display:none">
					<td style="width:34px">&nbsp;</td>
					<td colspan="10" align="center"><strong>{{=item.colorList[0].noChildMessage}}</strong></td>
				</tr>
			{{ } }}
		{{ } }}
	{{ }) }}
{{ } }}
</script>  

<script type="text/javascript"> 

function goToHomeScreen(UserSessURL){ 
    window.location = "/wps/portal/home/worklistDisplay";
}

var timeOutvarWLD = null;
timeOutvarWLD = setTimeout(redirectSessionTimedOut, 1800000);

function createmannualpet(){
document.getElementById("createManualPet").value = 'gotoCreateManualPet';

$("#workListDisplayForm").submit();
}
//** Belk PIM Phase - II. Change for Create Grouping - Start. AFUPYB3 **/
function createGroup(){
	document.getElementById("goToGrouping").value = 'goToGrouping';

	$("#workListDisplayForm").submit();
	}
//** Belk PIM Phase - II. Change for Create Grouping - End **/
window.onload = onloaddept;

function defaultAdvSearchSettings()
{	
		$("#adDeptNo").attr("disabled","disabled");
 		$("#advImageStatus").attr("disabled","disabled");
		$("#advContentStatus").attr("disabled","disabled");
		$("#advRequestType").attr("disabled","disabled");
		$("#advClassNumber").attr("disabled","disabled");
	    var jscontextpath = '<%=response.encodeURL(request.getContextPath())%>';
		var jsmidpath = '/img/';		
		var jsimagename	='iconCalendar.gif';
		var jsFullImgPath=jscontextpath + jsmidpath + jsimagename;
		//alert(todateValue);
    $("#datepicker1").datepicker({
        showOn: 'button',
        buttonText: 'Date',
        buttonImageOnly: true,
        buttonImage: jsFullImgPath,
        dateFormat: 'mm-dd-yy',
        constrainInput: true
    });
	
$("#datepicker2").datepicker({
        showOn: 'button',
        buttonText: 'Date',
        buttonImageOnly: true,
        buttonImage: jsFullImgPath,
        dateFormat: 'mm-dd-yy',
        constrainInput: true
    });


    $(".ui-datepicker-trigger").mouseover(function() {
        $(this).css('cursor', 'pointer');
    });
	
$('#deptTable').dialog({
		modal: true,
		autoOpen: false,
		resizable: true,
		dialogClass: "dlg-custom dlg-dept",
		title: 'Department',
		width: 310,
		height: 340,
		minWidth: 310,
		minHeight: 340,
		open: function( event, ui ) {
			$('#selectedDeptSearch').focus();
		},
		resizeStop: function(event, ui){
			//VP 27 dialog content resizing code starts
			var height = parseInt($(this).find ('.scrollbarsetdept').css('height'), 10);
			height += parseInt(ui.size.height - ui.originalSize.height);
			height = height < 100 ? 100 : height;
			$(this).find('.scrollbarsetdept').css({height: height});
			//VP 27 dialog content resizing code ends
			
			$(this).find('#deptTable').css({height: '100%'});
		},
	});
$('#dialog_petStatus').dialog({
	modal: true,
	autoOpen: false,
	resizable: true,
	dialogClass: "dlg-custom dlg-pet-status",
	title: 'Pet Status',
	minWidth: 350,
	minHeight: 140,
});	
}

function attachAdvSearchParamsDlg(){
	setTimeout(function(){ //made async delay for internal processing
		$('div.dlg-imgStat').remove(); //ui dlg fix
		$('div.dlg-contentStat').remove(); //ui dlg fix
		$('div.dlg-reqType').remove(); //ui dlg fix
		$('div.dlg-classNo').remove(); //ui dlg fix
		
		$('#dialog_Image').dialog({
			modal: true,
			autoOpen: false,
			resizable: true,
			dialogClass: "dlg-custom dlg-imgStat",
			title: 'Image',
			minWidth: 309,
			minHeight: 241,
		});
		
		$('#dialog_Content').dialog({
			modal: true,
			autoOpen: false,
			resizable: true,
			dialogClass: "dlg-custom dlg-contentStat",
			title: 'Content Status',
			minWidth: 309,
			minHeight: 241,
		});
		
		$('#dialog_ReqType').dialog({
			modal: true,
			autoOpen: false,
			resizable: true,
			dialogClass: "dlg-custom dlg-reqType",
			title: 'Request Type',
			minWidth: 346,
			minHeight: 238,
		});
		
		$('#dialog_ClassNo').dialog({
			modal: true,
			autoOpen: false,
			resizable: true,
			dialogClass: "dlg-custom dlg-classNo",
			title: 'Class Number',
			minWidth: 321,
			minHeight: 293,
			resizeStop: function(event, ui){
				//console.log(ui.size);
				$(this).find('.table-container-uidlg').css({height: '80%'});
			},
		});
		
	}, 10);
}
function resetAdvSearchSettings()
{
		$("#adDeptNo").attr("disabled","disabled");
 		$("#advImageStatus").attr("disabled","disabled");
		$("#advContentStatus").attr("disabled","disabled");
		$("#advRequestType").attr("disabled","disabled");
		$("#advClassNumber").attr("disabled","disabled");
	    var jscontextpath = '<%=response.encodeURL(request.getContextPath())%>';
		var jsmidpath = '/img/';		
		var jsimagename	='iconCalendar.gif';
		var jsFullImgPath=jscontextpath + jsmidpath + jsimagename;
		var fromdateValue="-90";
		if($("#fromdateValueHolder").val().trim().length>0){
			fromdateValue = $("#fromdateValueHolder").val().trim();
		}
		var todateValue="+90";
		if($("#todateValueHolder").val().trim().length>0){
			todateValue = $("#todateValueHolder").val().trim();
		}
    $("#datepicker1").datepicker({
        showOn: 'button',
        buttonText: 'Date',
        buttonImageOnly: true,
        buttonImage: jsFullImgPath,
        dateFormat: 'mm-dd-yy',
        constrainInput: true
    }).datepicker("setDate", fromdateValue);
	
$("#datepicker2").datepicker({
        showOn: 'button',
        buttonText: 'Date',
        buttonImageOnly: true,
        buttonImage: jsFullImgPath,
        dateFormat: 'mm-dd-yy',
        constrainInput: true
    }).datepicker("setDate", todateValue);


    $(".ui-datepicker-trigger").mouseover(function() {
        $(this).css('cursor', 'pointer');
    });
    
}

$(document).ready(function() {

$("#advImageStatus").attr("disabled","disabled");
$("#advContentStatus").attr("disabled","disabled");
$("#advRequestType").attr("disabled","disabled");
var jscontextpath = '<%=response.encodeURL(request.getContextPath())%>';
var jsmidpath = '/img/';		
var jsimagename	='iconCalendar.gif';
var jsFullImgPath=jscontextpath + jsmidpath + jsimagename;
//alert(jsFullImgPath);
$("#datepicker1").datepicker({
showOn: 'button',
buttonText: 'Date',
buttonImageOnly: true,
buttonImage: jsFullImgPath,
dateFormat: 'mm-dd-yy',
constrainInput: true

}).datepicker("setDate", "-90");

$("#datepicker2").datepicker({
showOn: 'button',
buttonText: 'Date',
buttonImageOnly: true,
buttonImage: jsFullImgPath,
dateFormat: 'mm-dd-yy',
constrainInput: true
}).datepicker("setDate", "+90");


$('body').on('click', '#selectAllDeptOnSearch', function(e){
	if($(this).is(':checked')){
		$('[name=chkSelectedDept]').prop('checked', true);
	}else{
		$('[name=chkSelectedDept]').prop('checked', false);
	}
});
//code for dept search popup select all checkbox 
$('body').on('click', '[name=chkSelectedDept]', function(e){
	if($(this).is(':checked')){
		var toatDeptsPresent = $('[name=chkSelectedDept]').length;
		var totalDeptsChecked = $('[name=chkSelectedDept]:checked').length;
		if(toatDeptsPresent == totalDeptsChecked)
			$('#selectAllDeptOnSearch').prop('checked', true);
	}else{
		$('#selectAllDeptOnSearch').prop('checked', false);
	}
});
//code for dept search popup search on enter key
$('body').on('keypress', '#selectedDeptSearch', function(e){
	if(e.which && e.which == 13){
		//console.log($('#advanceSearchDiv').dialog( "isOpen" ));
		try{
			if($('#advanceSearchDiv').dialog( "isOpen" ))
				$("#AdseachClicked").val("yes");	
		}catch(ex){
			
		}finally{
			depSearch('depSearch');
		}
		return false;
	}
});

$('#deptTable').dialog({
	modal: true,
	autoOpen: false,
	resizable: true,
	dialogClass: "dlg-custom dlg-dept",
	title: 'Department',
	width: 310,
	height: 340,
	minWidth: 310,
	minHeight: 340,
	open: function( event, ui ) {
		$('#selectedDeptSearch').focus();
	},
	resizeStop: function(event, ui){
		//VP 27 dialog content resizing code starts
		var height = parseInt($(this).find ('.scrollbarsetdept').css('height'), 10);
		height += parseInt(ui.size.height - ui.originalSize.height);
		height = height < 100 ? 100 : height;
		$(this).find('.scrollbarsetdept').css({height: height});
		//VP 27 dialog content resizing code ends
		
		$(this).find('#deptTable').css({height: '100%'});
	},
});
$('#dialog_petStatus').dialog({
	modal: true,
	autoOpen: false,
	resizable: true,
	dialogClass: "dlg-custom dlg-pet-status",
	title: 'Pet Status',
	minWidth: 350,
	minHeight: 140,
});

	$(".ui-datepicker-trigger").mouseover(function() {
$(this).css('cursor', 'pointer');
});

});

function bindDeptDialog (){
	$('#deptTable').dialog({
		modal: true,
		autoOpen: false,
		resizable: true,
		dialogClass: "dlg-custom dlg-dept",
		title: 'Department',
		width: 310,
		height: 340,
		minWidth: 310,
		minHeight: 340,
		open: function( event, ui ) {
			$('#selectedDeptSearch').focus();
		},
		resizeStop: function(event, ui){
			//VP 27 dialog content resizing code starts
			var height = parseInt($(this).find ('.scrollbarsetdept').css('height'), 10);
			height += parseInt(ui.size.height - ui.originalSize.height);
			height = height < 100 ? 100 : height;
			$(this).find('.scrollbarsetdept').css({height: height});
			//VP 27 dialog content resizing code ends
			
			$(this).find('#deptTable').css({height: '100%'});
		},
	});
}
	
function timeOutWLDPage()
{
	timeOutvarWLD = setTimeout(redirectSessionTimedOut, 1800000);
}

document.onclick = clickListenerWLD;
function clickListenerWLD(e){
	clearTimeout(timeOutvarWLD);
	timeOutWLDPage();
}

function redirectSessionTimedOut(){
var loggedInUser= $("#username").val();
if(loggedInUser.indexOf('@') === -1) 
{
  window.location = "/wps/portal/home/InternalLogin";
} else {
	window.location = "/wps/portal/home/ExternalVendorLogin";
	}
}

/*
* This function is used for toggeling group id and group name input boxes disability
* Disable Group ID and Group Name will be disabled if Show Only Items is selected in Search Result
* Enable Group ID and Group Name will be enabled if Include groupings is selected in Search Result
*/
function toggleEnability(){
	var radioName = $('input:radio[name=searchResults]:checked').val();
	
	// Disabling Group ID and name on selecting Show Items Only radio. Enabling if otherwise
	if(radioName == 'showItemsOnly'){
		$("#groupingID").attr("disabled",true);
		$("#groupingName").attr("disabled",true);
		$("#groupingID").val("");
		$("#groupingName").val("");
	}else if(radioName == 'includeGrps'){
		$("#groupingID").attr("disabled",false);
		$("#groupingName").attr("disabled",false);
	}
}

//function isTodayClick(){
	//var todayDate = new Date();
	//var mm = todayDate.getMonth()+1;
	//var dd= todayDate.getDate();
	//var yyyy= todayDate.getFullYear();
	//if(dd<10){
	//dd='0'+dd;
	//}
	//if(mm<10){
	//mm='0'+mm;
	//}
	//var finalTodayDate = mm+"-"+dd+"-"+yyyy;
	//$("#datepicker1").val(finalTodayDate);
	//$("#datepicker2").val(finalTodayDate);
//}

(function($){
    $('#table-container').ready(function(){
        var orinId  = $('#visited-orin-num').val(), selEle = $("#parent_"+orinId), container = $('#table-container');
        if(orinId && selEle){
            container.animate({
                    scrollTop: selEle.offset().top - container.offset().top + container.scrollTop()-50
                    }, 1);
             selEle.addClass("pre-visited");
        }
    });
})(jQuery)
</script>