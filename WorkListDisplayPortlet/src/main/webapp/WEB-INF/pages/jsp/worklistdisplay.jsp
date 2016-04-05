<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<head>

     
     
     <title>WorkFlow Display.</title>
	 
	 

<style type = "text/css">
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
</style>

</head>

<portlet:defineObjects />
<fmt:bundle basename="workListDisplay">
<portlet:actionURL var="formAction"> 
		<portlet:param name="action" value="workListDisplaySubmit"/>
</portlet:actionURL>	
<portlet:resourceURL var="ajaxUrl"> 
</portlet:resourceURL>
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
    <input type="hidden" id="selectedOrin" name="selectedOrin" value=""/>
    
    <input type="hidden" id="stylepetstatid" name="stylepetstatid" value=""/>
    <input type="hidden" id="stylecolorpetstatid" name="stylecolorpetstatid" value=""/>
    <input type="hidden"  name="createManualPet" id="createManualPet" value=""/>
	<input type="hidden" id="petStatus" name="petStatus" value=""/>    
    
    <input type="hidden" id="searchReturnId" name="searchReturnId" value="false" />	
	
	<!--Commented Belk Best Plan for displaying DCA afuszr6-->
	<!--<c:if test="${isInternal =='yes' && workflowForm.readOnlyUser == 'no'}"> 
		<div style="float:left;width: 200px;margin-bottom: 0.5cm;"><a href="<c:out value='${BEBESTPLAN}'/>" target="_blank">Belk Best Plan</a></div>
	</c:if>-->
	<c:if test="${isInternal =='no'}"> 
		<div style="float:left;width: 200px;margin-bottom: 0.5cm;"><a href="<c:out value='${BEBESTPLAN}'/>" target="_blank">Belk Best Plan</a></div>
	</c:if>	
	
		 <div align="right" style="margin-bottom: 0.5cm" >	
			<c:out value="${workflowForm.pepUserID}"/> &nbsp;	 
			<input type="button"   style="font-weight: bold" name="logout" value="Logout" 
			    onclick=logout_home();  />	
			
		 </div>
 <span>

 </span>

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
				<c:if test="${workflowForm.readOnlyUser !='yes'}">    
					<input type="button"  id="createGrouping" value="<fmt:message key="worklist.grouping.label"/>" 
					    onclick=createGrouping();  class="creategroupingbutton" disabled/>  
				</c:if>  
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
				<div id="tableStart"></div><div class="x-panel-body" id="petTable" name="petTable">	
				<input type="hidden" id="updateDateMessage" name="updateDateMessage" value="${workflowForm.updateCompletionDateMsg}"/>
				<c:if test="${workflowForm.totalNumberOfPets ne '0'}">
				<span class="pagebanner">&nbsp;<c:out value="${workflowForm.totalNumberOfPets}"/>
				
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
				
				<c:forEach items="${workflowForm.pageNumberList}" var="pageNumber"
							varStatus="status">
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
							
				</span>
				</c:if>
				</c:if>				
				<div class="scrollbarset">
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
				<th style="width:34px"><input  class="selectAllCheckBox" type="checkbox" name="selectAllRow" id="selectAllRow">
				</th>
				<th class="sortable sorted order2" width="10%">
				<a href="#" onclick="columnsorting('orinGroup')"><fmt:message key="worklist.orin.group.label"/>#</a>
				<c:if test="${workflowForm.selectedColumn =='orinGroup'}">
					<img src="${contextpath}${imagemidpath}${imagename}"> </img>
				</c:if>
				</th>
				<th class="sortable">
				<a href="#" onclick="columnsorting('dept')"><fmt:message key="worklist.column.dept.label"/>#</a>
				<c:if test="${workflowForm.selectedColumn =='dept'}">
					<img src="${contextpath}${imagemidpath}${imagename}"> </img>
				</c:if>
				</th>
				<th class="sortable">
				<a href="#" onclick="columnsorting('vendorName')"><fmt:message key="worklist.column.vendor.name.label"/></a>
				<c:if test="${workflowForm.selectedColumn =='vendorName'}">
					<img src="${contextpath}${imagemidpath}${imagename}"> </img>
				</c:if>
				</th>
				<th class="sortable">
				<a href="#" onclick="columnsorting('vendorStyle')"><fmt:message key="worklist.column.vendor.style.label"/>#</a>
				<c:if test="${workflowForm.selectedColumn =='vendorStyle'}">
					<img src="${contextpath}${imagemidpath}${imagename}"> </img>
				</c:if>
				</th>
				<th class="sortable">
				<a href="#" onclick="columnsorting('productName')"><fmt:message key="worklist.column.product.name.label"/></a>
				<c:if test="${workflowForm.selectedColumn =='productName'}">
					<img src="${contextpath}${imagemidpath}${imagename}"> </img>
				</c:if>
				</th>
				<th class="sortable">
				<a href="#" onclick="columnsorting('contentStatus')"><fmt:message key="worklist.column.content.status.label"/></a>
				<c:if test="${workflowForm.selectedColumn =='contentStatus'}">
					<img src="${contextpath}${imagemidpath}${imagename}"> </img>
				</c:if>
				</th>
				<th><a href="#" onclick="columnsorting('imageStatus')"><fmt:message key="worklist.column.img.status.label"/></a>
				<c:if test="${workflowForm.selectedColumn =='imageStatus'}">
					<img src="${contextpath}${imagemidpath}${imagename}"> </img>
				</c:if>
				</th>		
				<th><a href="#" onclick="columnsorting('completionDate')"><fmt:message key="worklist.column.icompletion.date.label"/></a>
				<c:if test="${workflowForm.selectedColumn =='completionDate'}">
					<img src="${contextpath}${imagemidpath}${imagename}"> </img>
				</c:if>
				</th>
				<th><a href="#" onclick="columnsorting('petSourceType')">PET Source</a>
				<c:if test="${workflowForm.selectedColumn =='petSourceType'}">
					<img src="${contextpath}${imagemidpath}${imagename}"> </img>
				</c:if>					
				</th>
				</tr>
				</thead>
				<tbody >
						<input type="hidden" id="hidden_roleEditable" name="hidden_roleEditable" value="${workflowForm.roleEditable}"/>
						<input type="hidden" id="hidden_readOnlyUser" name="hidden_readOnlyUser" value="${workflowForm.readOnlyUser}"/>
						<input type="hidden" id="hidden_roleName" name="hidden_roleName" value="${workflowForm.roleName}"/>
						<input type="hidden" id="callType" name="callType" value=""/>
						<input type="hidden" id="searchClicked" name="searchClicked" value="${workflowForm.searchClicked}" />
						
						
						
								
				<!-- Table Grid logic Start -->
						<c:set var="subcount" value="260" />
						<c:set var="countList" value="0" />
						<c:if test="${workflowForm.petNotFound ne null}"><tr><td colspan="10"><c:out value="${workflowForm.petNotFound}"/></td></tr> </c:if>
						<c:forEach items="${workflowForm.workFlowlist}" var="workFlow"
							varStatus="status">
							
								<tr id="parent_${workFlow.orinNumber}" name="tablereport" class="treegrid-${countList}" >
								<td align="center" style="width:34px">
								
									<div style="display: inline;margin:-17px 0 0 0px">
								<c:if test="${workFlow.isChildPresent =='Y'}">
								<img id="parentSpan_${workFlow.orinNumber}_expand" onclick="expandCollapse('${workFlow.orinNumber}','${workflowForm.searchClicked}')" src="${contextpath}${imagemidpath}expand.png"  
								style="cursor:pointer;border:0;" width="14" />
								<img id="parentSpan_${workFlow.orinNumber}_collapsed" onclick="expandCollapse('${workFlow.orinNumber}','${workflowForm.searchClicked}')" src="${contextpath}${imagemidpath}collapsed.png"  
								style="display:none;cursor:pointer" style="cursor:pointer;border:0;" width="14"/>
								</c:if>
																
								<input type="checkbox" name="styleItem" id="styleItem" class="checkbox1"
									value="${workFlow.orinNumber}_${workFlow.petStatus}" onclick="childCheckedRows(this,'${workFlow.orinNumber}','${workflowForm.searchClicked}');getPetStatValue('${workFlow.petStatus}')" style="margin:0;"/>
								</div>
								
								</td>
								<td><c:out value="${workFlow.orinNumber}" />
								<c:if test="${workFlow.omniChannelVendor =='Y' && workflowForm.roleEditable =='yes'}">*</c:if>
								</td>
								<td><c:out value="${workFlow.deptId}" />
								</td>
								<td><c:out value="${workFlow.vendorName}" />
								</td>
								<td><c:out value="${workFlow.vendorStyle}" />
								<input type="hidden" id="${workFlow.orinNumber}_vendorStyle" value="${workFlow.vendorStyle}"/>
								</td>
								<td><c:out value="${workFlow.productName}" />
								<input type="hidden" id="${workFlow.orinNumber}_productName" value="${workFlow.productName}"/>
								</td>
								<td>
								   
									<c:choose>									    
										<c:when test="${workflowForm.roleEditable =='yes' }" >										
										  <a href="#" onclick="contentStatus('<c:out value="${workFlow.contentStatus}"/>','<c:out value="${workFlow.orinNumber}"/>')"><c:out value="${workFlow.contentStatus}"/></a>
										</c:when>
										<c:when test="${workflowForm.readOnlyUser =='yes' }" >	
										   <a href="#" onclick="contentStatus('<c:out value="${workFlow.contentStatus}"/>','<c:out value="${workFlow.orinNumber}"/>')"><c:out value="${workFlow.contentStatus}"/></a>
										</c:when>
										<c:otherwise>
										   <c:out value="${workFlow.contentStatus}" />
										</c:otherwise>
									</c:choose>
								</td>
									<td>
									<c:choose>
										<c:when test="${workflowForm.roleEditable =='yes' }" >
										  <a href="#" onclick="imageStatus('<c:out value="${workFlow.imageStatus}"/>','<c:out value="${workFlow.orinNumber}"/>','${workFlow.petStatus}')"><c:out value="${workFlow.imageStatus}"/></a>										
										</c:when>
										
										<c:when test="${workflowForm.readOnlyUser =='yes' }" >	
										   <a href="#" onclick="imageStatus('<c:out value="${workFlow.imageStatus}"/>','<c:out value="${workFlow.orinNumber}"/>','${workFlow.petStatus}')"><c:out value="${workFlow.imageStatus}"/></a>
										</c:when>
										
										<c:otherwise>
										<c:out value="${workFlow.imageStatus}" />
										</c:otherwise>
									</c:choose>
									</td>
								<td>								
								<c:if test = "${workFlow.petStatus ne 'Deactivated'}">										
									<c:out value="${workFlow.completionDate}" />
								</c:if>							
								<input type="hidden" id="${workFlow.orinNumber}_CmpDate" name="${workFlow.orinNumber}_CmpDate" value="${workFlow.completionDate}"/>
								<input type="hidden" id="tbisPCompletionDateNull_${workFlow.orinNumber}" name="tbisPCompletionDateNull_${workFlow.orinNumber}" value="${workFlow.isPCompletionDateNull}" />
								</td>
								
								<td><c:out value="${workFlow.sourceType}" /></td>
								
								<td style="display: none;"><c:out value="${workFlow.petStatus}" />
								</td>
								
							</tr>
						
						
							<c:set var="countList" value="${countList+1}" />
						</c:forEach> 
						<tr id="CHILDTR_ID" name="CHILDTR_NAME"
									class="" style="display:none">
									<td style="width:34px"><input type="checkbox" parentOrinNo="#CHBOX_PARENTORIN" name="selectedStyles" class="checkbox1"
										value="#CH_STYLE_ORIN_#CH_STYLE_PETSTATUS" onclick="getStyleColorPetStatValue('#CHBOXONCLK_PETSTATUS')" style="margin:16px 0 0 19px"/></td>
									<td>#TD_ORIN</td>
									<td>#TD_DEPT_NUM</td>
									<td>#TD_VENDOR_NAME</td>
									<td>#TD_VENDOR_STYLE</td>
									<td>#TD_PRODUCT_NAME</td>
									<td>
									<a href="#" onclick="contentStatus('#CONTENT_STATUS','#CON_PARENT_ORIN')">#TD_CONTENT_STATUS</a>
									</td>
									<td>
									<a href="#" onclick="imageStatus('#IMAGE_STATUS','#IMG_PARENT_ORIN','#PET_STATUS')">#TD_IMAGE_STATUS</a>
									</td>
									<td>
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
									<td>#TD_SOURCE_TYPE</td>
									<td style="display: none;">#TD_PET_STATUS</td>
								</tr>	
				<!--  Table Grid Login End -->	       
				</tbody>
				</table>
				</div>
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
   

<script type="text/javascript"> 


var timeOutvarWLD = null;
timeOutvarWLD = setTimeout(redirectSessionTimedOut, 1800000);

function createmannualpet(){
document.getElementById("createManualPet").value = 'gotoCreateManualPet';

$("#workListDisplayForm").submit();
}

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

</script>