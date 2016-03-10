<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	 
	<head>
	<title>Attribute Maintenance</title>
	
<script  type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.alerts.js"></script>
<script  type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script  type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.ui.draggable.js"></script>

<link rel="stylesheet" type="text/css" href="<%=response.encodeURL(request.getContextPath()+ "/css/jquery.alerts.css")%>" >
</head>

<style type="text/css">
fieldset {
	border: 1px solid green;
}
 
legend {
	padding: 0.2em 0.5em;
	border: 0px solid green;
	color: black;
	font-size: 90%;
	text-align: left;
}

.selectedDeptSearchtxtbox{
width:83px;
}

.scrollbarsetdept{
height:100px;

overflow:auto;
}

.deptmaintable{
width: 100%; 
border: 0px;
}
.deptintable{
width:90%;
margin-left:20px;
margin-bottom:20px;
}

.deptintabletrheading{
background-color:#f0f0f0;
}
.deptintableborder{
padding-left: 15px;
border:1px solid black;
}

.deptintdSearchText{
padding-left: 0px;
border:1px solid black;
}

.web_dialog_title
{
   border-bottom: solid 2px #336699;
   background-color: #336699;
   padding: 4px;
   color: White;
   font-weight:bold;
   font-family: 'Arial'
}

.web_dialog_overlay_advSearch_popup
{
   position: fixed;
   top: 0;
   right: 0;
   bottom: 0;
   left: 0;
   height: 100%;
   width: 100%;
   margin: 0;
   padding: 0;
   background: #000000;
   opacity: .16;
   filter: alpha(opacity=15);
   -moz-opacity: .15;
   z-index: 103;
   display: none;
}

.web_dialog_dept
{
   display: none;
   position: fixed;
   width: 460px;
   height: 550px;
   top: 30%;
   left: 29%;
   margin-left: -190px;
   margin-top: -100px;
   background-color: #f0f0f0;
   border: 2px solid #336699;
   padding: 0px;
   z-index: 104;
   font-family: Verdana;
   font-size: 10pt;
}
</style>
	<script type="text/javascript">
		function searchAttribute() {
			var ipVal = document.getElementById("iphvalue").value;
			var radActVal = $('input:radio[name=radioactive]:checked').val();
			var radInacVal = $('input:radio[name=radioinactive]:checked').val();
			var pimChkVal = $("input[name='pim']:checked").val();
			var bmChkVal = $("input[name='blueMartin']:checked").val();
			
			if( (ipVal == null || ipVal == "") || (!(pimChkVal =='on' || bmChkVal =='on')) ||  (!(radActVal == 'Yes' || radInacVal == 'Yes')) ){
				//alert("Please enter search criteria.");
				jAlert(' Please enter proper search criteria ', ' Alert ');
			}else{
				document.forms['attributeMaintenanceForm'].categoryValue.value = ipVal
				document.forms['attributeMaintenanceForm'].actionParameter.value = "searchAttribute";
				document.forms['attributeMaintenanceForm'].submit();
			}
		}
		
		function updatedAttDetails(list) {
			//var confirmation = confirm("Are you sure you want to update above attribute details?");
			
			//if(confirmation == true){
				var attType = document.getElementById("attributeType").value;
				var catAttId = document.getElementById("categoryAttributeId").value
				if(catAttId != null){
					var val = catAttId.split('~')
					document.forms['attributeMaintenanceForm'].attributeId.value = val[1];
					document.forms['attributeMaintenanceForm'].categoryId.value = val[0];
				}
				if(attType =='Drop Down'){
					var finalOptions;
					for(var i=0; i<list.options.length; i++){
						var val = list.options[i].value
						if(i == 0){
							finalOptions =  val;
						}else{
							finalOptions = finalOptions+ " | " +val;
						}
					}
					document.forms['attributeMaintenanceForm'].dropDrownAttributeValue.value =finalOptions
				 }
				document.forms['attributeMaintenanceForm'].actionParameter.value = "updateAttribute";
				document.forms['attributeMaintenanceForm'].submit();
			 //} 
		 }
		
		function loadIPHValues(){
			document.forms['attributeMaintenanceForm'].actionParameter.value = "iphSearch";
			document.forms['attributeMaintenanceForm'].submit();
		}
		
		function clearAttribute() {
			document.getElementById("iphvalue").value =  "";
			$('input[name=radioactive]').attr('checked',false);
			$('input[name=radioinactive]').attr('checked',false);
			$('input[name=pim]').attr('checked',false);
			$('input[name=blueMartin]').attr('checked',false);
			document.forms['attributeMaintenanceForm'].actionParameter.value = "clearform";
			document.forms['attributeMaintenanceForm'].submit();
		}
		
		
		function dislayAttribute(attribute_txt_area){
		 
			if(attribute_txt_area != null && attribute_txt_area.value != null){
				//alert(attribute_txt_area.value);
				var val = attribute_txt_area.value.split('~')
			 
				document.forms['attributeMaintenanceForm'].attributeId.value = val[1];
				document.forms['attributeMaintenanceForm'].categoryId.value = val[0];
				document.forms['attributeMaintenanceForm'].actionParameter.value = "getAttributeDetails";
				document.forms['attributeMaintenanceForm'].submit();
			}
		}
		

		function attributeSearch(depOperation) {
			if (depOperation == 'attClose') {

				$("#overlay_Dept").css("display", "none");
				$("#dialog_Dept").css("display", "none");

			} else if (depOperation == 'attSaveClose'){	
				$("#overlay_Dept").css("display", "none");
				$("#dialog_Dept").css("display", "none");
				
			} else if (depOperation == 'attClear'){
				
				$("#overlay_Dept").css("display", "none");
				$("#dialog_Dept").css("display", "none");
				
			}
		}
		
	
		function showIPHPopup() {
			$("#overlay_Dept").css("display", "block");
			$("#dialog_Dept").css("display", "block");
		}

		function categoryClose() {
			$("#overlay_Dept").css("display", "none");
			$("#dialog_Dept").css("display", "none");
		}

		function selectCategory() {
			var temp = $('input:radio[name=radSelectedCategory]:checked').val();
			document.getElementById("iphvalue").value = temp;
			$("#overlay_Dept").css("display", "none");
			$("#dialog_Dept").css("display", "none");
		}
		
	
		function activeclicked() {
			$('input[name=radioinactive]').attr('checked', false);
		}

		function attActiveClicked() {
			$('input[name=attradioinactive]').attr('checked', false);
		}

		function attrInactivClicked() {
			$('input[name=attradioactive]').attr('checked', false);
		}

		function inactiveclicked() {
			$('input[name=radioactive]').attr('checked', false);
		}

		function removeFromList(list) {
			if (list.selectedIndex == -1) {
				//alert("Please select the option to remove. ");
				jAlert(' Please select the option to remove ', ' Alert ');
			}
			list.remove(list.selectedIndex)
		}
		
		function addToList(list) {
			var newItem = document.createElement("option");
			var dynamicCal = document.getElementById("dynamicValueSet").value
			if (dynamicCal == '') {
				//alert("Please enter the value to add. ");
				jAlert(' Please enter the value to add ', ' Alert ');
			}
			newItem.value = dynamicCal;
			newItem.text = dynamicCal;
			list.add(newItem);
			document.getElementById("dynamicValueSet").value = "";
		}
		
		window.onload = retainSelected;
		
		function retainSelected(){
			var value  = document.getElementById("categoryAttributeId").value;  
			var oList = document.forms['attributeMaintenanceForm'].elements["attribute_txt_area"];
			 for(var i = 0; i < oList.options.length; i++) {
			     if(oList.options[i].value == value){
			    	 oList.options[i].selected=true;
			     } 
			   }
		}

		function exitFromAttributeMaintenance() {
			window.location = "/wps/portal/home/Welcome";
		}
		
		function enableAddButton(){
			var value  = document.getElementById("dynamicValueSet").value;  
			if(value.length > 0){
				document.getElementById("dynamicValueSetAdd").disabled = false;
			}else if(value.length == 0){
				document.getElementById("dynamicValueSetAdd").disabled = true;
			}
			
		}
		
	</script>


<portlet:defineObjects />
	 <fmt:setBundle basename="attributeMaintenanceDisplay.properties" var="display"/>  
			<portlet:actionURL var="formAction"> 
					<portlet:param name="action" value="attributeSearchSubmit"/>
			</portlet:actionURL>	
			<portlet:resourceURL var="ajaxUrl"> 
</portlet:resourceURL>
		<div id="content">
<div id="main">
		<form:form commandName="attributeMaintenanceForm" method="post" action="${formAction}" 	name="attributeMaintenanceForm" id="attributeMaintenanceForm"  onload="javascript:retainSelected();">
		<input type=hidden id="actionParameter" name="actionParameter"/>
		<input type=hidden id="attributeId" name="attributeId"/>
		<input type=hidden id="categoryId" name="categoryId"/>
		<input type=hidden id="attributeType" name="attributeType" value="${attributeMaintenanceForm.attributeValueVO.attributeFieldType}"/>
		<input type=hidden id="dropDrownAttributeValue" name="dropDrownAttributeValue"/>
		<input type=hidden id="categoryValue" name="categoryValue"/>
		<input type=hidden id="categoryAttributeId" name="categoryAttributeId" value="${attributeMaintenanceForm.categoryAttributeId}" />
		

		<table width="90%">
			<tr>
				<td width="">
					<fieldset>
						<legend>Search</legend>
						<table>
							<tr>
								<td valign="bottom"> &nbsp;IPH: <input type="text" size="38" disabled="disabled" name="iphvalue" id="iphvalue" value="${attributeMaintenanceForm.attributeSearchVO.categoryIdAndName}" />&nbsp;
									&nbsp; <input type="button" name="iphSearch" value=" + "
									class="saveContentButton" onclick="javascript:showIPHPopup()" />
								</td>
								<td width="10px"></td>
								<td >
									<fieldset title="Attribute Types">
										<legend>Type</legend>
										&nbsp;<input type="checkbox" name="pim" id="pim" <c:if test="${'on' == attributeMaintenanceForm.attributeSearchVO.attributeTypePIM}"> checked="checked" </c:if> /> PIM  
										&nbsp; &nbsp; &nbsp; &nbsp; <input type="checkbox" id="blueMartin" name="blueMartin" <c:if test="${'on' == attributeMaintenanceForm.attributeSearchVO.attributeTypeBM}"> checked="checked" </c:if> /> Blue Martini &nbsp;&nbsp;
									</fieldset>
								</td>
								<td width="10px"></td>
								<td >
									<fieldset title="Attribute Status">
										<legend>Status</legend> 
										<li class= "text">
										&nbsp; <input type="radio" name="radioactive" id="radioactive" value="Yes" onclick="activeclicked()";
										 <c:if test="${'ACTIVE' == attributeMaintenanceForm.attributeSearchVO.attributeStatus}"> checked="checked" </c:if>  /> Active
										 &nbsp; &nbsp; &nbsp; &nbsp;<input type="radio" value="Yes" id="radioinactive" name="radioinactive"  onclick="inactiveclicked()";
										 <c:if test="${'INACTIVE' == attributeMaintenanceForm.attributeSearchVO.attributeStatus}"> checked="checked" </c:if> /> Inactive &nbsp;&nbsp;
										</li>
									</fieldset>
								</td>
								<td width="10px"></td>
								<td valign="bottom" ><input type="button" align="middle" title="Search's for the result" name="searchbutton" value="   Search   " class="saveContentButton"
									onclick="javascript:searchAttribute();" /></td>
								<td width="10px"></td>
								<td valign="bottom"><input type="button" align="middle" title="Clears the Search Criteria" name="clearbutton" value="   Clear   "  class="saveContentButton"
									onclick="javascript:clearAttribute();" /></td>
								<td width="10px">&nbsp;</td>
								<td valign="bottom"><input type="button" align="middle"  title="Application will close" name="closebutton" value="   Exit   "  class="saveContentButton"
 									onclick="javascript:exitFromAttributeMaintenance();" /></td>
								<td width="1px">&nbsp;</td>
							</tr>
							<tr><td colspan="10">&nbsp;
								<c:if test="${attributeMaintenanceForm.searchResultErrorMessage != null}" >
										<b style="background-color: red;">${attributeMaintenanceForm.searchResultErrorMessage}</b>
								 </c:if>
							</td></tr>
						</table>
	
					</fieldset>
				</td>
			</tr>

		</table>
		<table>
	 		<tr>
	 			<td>&nbsp;</td>
	 		</tr>
	 	</table>
	 
	 	<table width="90%" border="1">
	 		<tr><td align="left" colspan="3" style="font-family: Arial">&nbsp;<b>Attribute Id - Attribute Name</b></td></tr>
	 		<tr>
	 			<td width="20%" valign="top">
	 				 <select name="attribute_txt_area" id="attribute_txt_area" size="20" style="width: 280px"
	 				 onclick="javascript:dislayAttribute(attribute_txt_area)"  title="List of Attribute Id's & Name's">
	 				  <c:forEach items="${attributeMaintenanceForm.attributeDetail}" var="childAttDisplayList">
		 				  <option id="${childAttDisplayList.catagoryId}~${childAttDisplayList.attributeId}"
		 				   value="${childAttDisplayList.catagoryId}~${childAttDisplayList.attributeId}" >
		 				    ${childAttDisplayList.attributeId} - ${childAttDisplayList.attributeName}</option>
	 				  </c:forEach>  
	 				 </select>
	 			</td>
	 			<td width="10px" valign="top">&nbsp;</td>
	 			
	 			<td align="left" valign="top">
	 				<fieldset title="">
					<legend>Attribute Value</legend>
	 				 	<table>
	 				 	 <tr><td></td></tr>
	 				 		<tr>
	 				 			<td width="10px"> &nbsp;</td>
	 				 			<td>
	 				 				<fieldset title="Attribute Status">
				  						<legend>Status</legend>
				   						 &nbsp;<input type="radio" id="attradioactive" name="attradioactive" value="Yes" onclick="attActiveClicked()" <c:if test="${'ACTIVE' == attributeMaintenanceForm.attributeValueVO.attributeStatus}"> checked="checked" </c:if> /> Active  &nbsp; &nbsp; &nbsp;
				   						 <input type="radio" id="attradioinactive" name="attradioinactive" value="Yes" onclick="attrInactivClicked()" <c:if test="${'INACTIVE' == attributeMaintenanceForm.attributeValueVO.attributeStatus}"> checked="checked" </c:if> /> Inactive &nbsp;&nbsp;	
		 							</fieldset>
		 							 </td>
		 							 <td width="12px"> &nbsp;</td>
		 							 <td>
		 							<fieldset title="Attribute Properties">
				  						<legend>Properties</legend>
				   						&nbsp; <input type="checkbox" id="issearchable" name="issearchable" <c:if test="${'Yes' == attributeMaintenanceForm.attributeValueVO.isSearchable}"> checked="checked" </c:if> /> Searchable &nbsp; &nbsp; &nbsp;
				   						&nbsp; <input type="checkbox" id="iseditable" name="iseditable" <c:if test="${'Yes' == attributeMaintenanceForm.attributeValueVO.isEditable}"> checked="checked" </c:if> />  Editable  &nbsp; &nbsp; &nbsp;
				   						&nbsp; <input type="checkbox" id="isrequired" name="isrequired" <c:if test="${'Yes' == attributeMaintenanceForm.attributeValueVO.isRequired}"> checked="checked" </c:if> /> Required  &nbsp; &nbsp; &nbsp;
		 							</fieldset>
		 							 
	 				 			</td>
	 				 		</tr>
	 				 	</table>
	 				 	<table>
	 				 		<tr><td colspan="2" >&nbsp;</td></tr>
	 				 		<tr>
	 				 			<td colspan="3" >
	 				 				 &nbsp; &nbsp; Description &nbsp; &nbsp; <input type="text" size="56" id="attDescription" name="attDescription"  
	 				 				  value="${attributeMaintenanceForm.attributeValueVO.attributeName}" />
	 				 			</td>
	 				 		</tr>
	 				 		<tr><td colspan="2" >&nbsp;</td></tr>
	 				 		<tr>
	 				 			<td colspan="3" >
	 				 				 &nbsp; &nbsp; Display Name <input type="text" size="56" id = 'attDisplayname' name="attDisplayname" 
	 				 				 value="${attributeMaintenanceForm.attributeValueVO.attributeDisplayName}" />
	 				 			</td>
	 				 		</tr>
	 				 		
	 				 		<c:if test="${attributeMaintenanceForm.attributeValueVO.attributeFieldType.trim() == 'Text Field' or 
	 				 		attributeMaintenanceForm.attributeValueVO.attributeFieldType.trim() == 'Text'}" > 
		 				 		<tr><td colspan="2" >&nbsp;</td></tr>
		 				 		<tr><td >&nbsp; &nbsp; &nbsp;Field Type &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 				 		<input type="text" size="16" id='txtfieldtype' name="txtfieldtype" disabled="disabled" value="${attributeMaintenanceForm.attributeValueVO.attributeFieldType}" /></td>
		 				 		<td> &nbsp;&nbsp;Field Value &nbsp; <input type="text" size="22" id ='attFieldvalue' name="attFieldvalue"  value="${attributeMaintenanceForm.attributeValueVO.attributeFieldValue}" /></td>
		 				 		</tr>
	 				 		</c:if>
	 				 		<c:if test="${attributeMaintenanceForm.attributeValueVO.attributeFieldType.trim() == 'Radio Button'}">
	 				 			<tr><td colspan="2" >&nbsp;</td></tr>
	 				 			<tr><td>&nbsp; &nbsp; &nbsp;Field Type  &nbsp; &nbsp; &nbsp;
	 				 			<input type="text" size="10" id="radfieldtype" name="radfieldtype" disabled="disabled" value="${attributeMaintenanceForm.attributeValueVO.attributeFieldType}" /></td>
	 				 			<td>&nbsp;&nbsp; <input type="text" size="16" id="radiofirst" name="radiofirst" value="${attributeMaintenanceForm.attributeValueVO.radioFirstValue}" />  
	 				 			&nbsp;  <input type="text" size="16"  id="radiosecond" name="radiosecond" value="${attributeMaintenanceForm.attributeValueVO.radioSecondValue}" />  </td>
	 				 			</tr>
	 				 		</c:if>
	 				 		<c:if test="${attributeMaintenanceForm.attributeValueVO.attributeFieldType.trim() == 'Check Boxes'}">
		 				 		<tr><td colspan="2" >&nbsp;</td></tr>
		 				 		<tr><td>&nbsp; &nbsp; &nbsp;Field Type  &nbsp; &nbsp;&nbsp; 
		 				 		<input type="checkbox" size="10" id='radfieldtype' name="radfieldtype" disabled="disabled"  value="${attributeMaintenanceForm.attributeValueVO.attributeFieldType}" /></td>
		 				 		<td>&nbsp;</td>
		 				 		<td> &nbsp; <input type="text" size="16" id="checkboxfirst" name="checkboxfirst" value="${attributeMaintenanceForm.attributeValueVO.checkboxFirstValue}" />  
		 				 		<input type="text" size="16"  id="checkboxsecond" name="checkboxsecond" value="${attributeMaintenanceForm.attributeValueVO.checkboxSecondValue}" /></td>
		 				 		</tr>
	 				 		</c:if>
	 				 		<c:if test="${attributeMaintenanceForm.attributeValueVO.attributeFieldType == 'Drop Down'}">
		 				 		<tr><td colspan="2" >&nbsp;</td></tr>
		 				 		<tr><td colspan="2" >
		 				 		<table border="2">
		 				 			<tr><td width="100%" > &nbsp;&nbsp; &nbsp; &nbsp;Field Type &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" size="8" id='radfieldtype' name="radfieldtype" disabled="disabled" value="${attributeMaintenanceForm.attributeValueVO.attributeFieldType}" /></td></tr>
		 				 		<tr><td>&nbsp;</td></tr>
		 				 		<tr>
		 				 		<td width="50px">&nbsp;</td>
		 				 		<td align="right" valign="bottom" >    
			 				 		<select name="fileValuelist" id="fileValuelist" size="10" > 
			 				 			<c:forEach items="${attributeMaintenanceForm.attributeValueVO.attributeValueList}" var="dropDrownList">
				 				  				<option id="${dropDrownList.attributeFieldValueSeq}" > ${dropDrownList.attributeFieldValue}</option>
			 				 			</c:forEach>
			 				 		</select>
			 				 		</td>
			 				 		<td width="6%">&nbsp;</td>
				 				 		<td>
				 				 		 <table>
				 				 		 	<tr><td><input type="text" size="18" id='dynamicValueSet' name="dynamicValueSet" onblur="enableAddButton();" /> </td> 
				 				 		 		<td>&nbsp;</td>
				 				 		 		<td><input type="button" align="right" id='dynamicValueSetAdd' name="dynamicValueSetAdd" disabled="disabled" value=" Add " onclick="javascript:addToList(this.form.fileValuelist)"/> 			 				 		 		</td>
				 				 		 	</tr><tr><td>&nbsp;</td></tr>
				 				 		 	<tr><td colspan="2">&nbsp;</td>
				 				 		 		<td><input type="button" id='dynamicValueSetRemove' name="dynamicValueSetRemove" value=" Remove " onclick="javascript:removeFromList(this.form.fileValuelist)"/> </td>
				 				 		 	</tr>
				 				 		 </table>
				 				 		</td>
			 				 		</tr>
			 				 		</table>
			 				 		</td>
		 				 		</tr>
	 				 		</c:if>
	 				 		<c:if test="${attributeMaintenanceForm.attributeValueVO.attributeFieldType.trim() == 'Text Field' or 
	 				 		attributeMaintenanceForm.attributeValueVO.attributeFieldType.trim() == 'Radio Button' or 
	 				 		attributeMaintenanceForm.attributeValueVO.attributeFieldType.trim() == 'Check Boxes' or 
	 				 		childAttDisplayList.catagoryId == null or childAttDisplayList.catagoryId == ''}" > 
	 				 		 <tr><td colspan="2" >&nbsp;</td></tr>
	 				 		 </c:if>
	 				 		<!-- <tr><td colspan="2" >&nbsp;</td></tr>
								<tr><td colspan="2" >&nbsp;</td></tr>  -->
								
	 				 			<tr valign="top">
	 				 			<td>&nbsp;
	 				 			<c:if test="${attributeMaintenanceForm.updateStatusMessage != null}" >
	 				 				<b style="background-color: red;">${attributeMaintenanceForm.updateStatusMessage}</b>
	 				 				</c:if>
	 				 			</td>
	 				 			<td align="right">&nbsp;<input type="button" title="Updates the Attribute Details"  id='updatebutton' name="updatebutton" value=" Update "
								class="updateAttributeButton"   <c:if test="${false == attributeMaintenanceForm.enableUpdateDetailsButton}"> disabled='disabled' </c:if>  onclick="javascript:updatedAttDetails(this.form.fileValuelist,attribute_txt_area)"/></td></tr>
								
								<!-- <tr><td colspan="2" >&nbsp;</td></tr>  
								<tr><td colspan="2" >&nbsp;<b>${attributeMaintenanceForm.updateStatusMessage}</b></td></tr> -->
	 				 	</table>
	 				 
	 			</td>
	 		</tr>
	 	</table>
	
	
		<!-- Popup content -->
			<div id="overlay_Dept" class="web_dialog_overlay_advSearch_popup"></div>
			<div id="tableDeptStart"></div>
			<div id="deptTable" name="deptTable">
				<!---------------  -->
				<div style="display: none;" id="dialog_Dept" class="web_dialog_dept"
					name="dialog_Dept ">
  
					<table class="deptmaintable" cellpadding="3" cellspacing="0"style="background-color: #f0f0f0" >
						<tbody>
							<tr>
								<td class="web_dialog_title">IPH (Category Search)</td>
								<td class="web_dialog_title align_right"></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
							</tr>

							<tr>
								<td width="55%">&nbsp;&nbsp;&nbsp;</td>
								<td>&nbsp;&nbsp;&nbsp;
								<!--  <input  id="btnClearDept1" type="button" value="Clear"  onclick="depSearch('depClear')" /> &nbsp; -->
								 <input style="font-family: Arial" id="btnSaveDept1" type="button" value=" Select "  onclick="selectCategory()" /> &nbsp;   
								 <input style="font-family: Arial" align="right" id="btnCloseDept1" type="button" value=" Close " onclick="categoryClose()" />
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
						</tbody>

					</table>  

					<table class="deptintable" align="center" style="background-color: #f0f0f0">
						<tbody>
							<tr>
								<td>
									<table width="96%" border="0">
										<tr class="deptintabletrheading">
											<th align="center" width="7%" style="border: 1px solid black;">&nbsp;</th>
											<th align="right" width="19%"
												style="border: 1px solid black; font-weight: bold; font-family: Arail; font-size: 14px;" >Category Id</th>
											<th align="right" width="60%"
												style="border: 1px solid black; font-weight: bold; font-family: Arail; font-size: 14px;" >Category Description</th>
										</tr>
									</table>
									<div class="scrollbarsetdept" style="height: 400px">
										<table width="100%">
											<tbody >
												<c:forEach items="${attributeMaintenanceForm.categoryListVO}" var="categoryItems" varStatus="status">
													<tr name="abc">

														<td width="8%" style="border: 1px solid black"
															align="center"><input type="radio"
															name="radSelectedCategory"  id="radSelectedCategory" class="deptcheckbox"
															value="<c:out  value="${categoryItems.categoryId} - ${categoryItems.categoryName}"/>" /> </td>
														<td width="22%" align="center"
															style="border: 1px solid black">&nbsp;<c:out
																value="${categoryItems.categoryId}" /></td>

														<td width="66%" align="left"
															style="border: 1px solid black">&nbsp;<c:out
																value="${categoryItems.categoryName}" /></td>
													</tr>
												</c:forEach>

											</tbody>
										</table>

									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>

				<!-- ------------ -->
			</div>
			<div id="tableDeptEnd"></div>
			<!-- popup content end -->
					
</form:form>
	</div>
 </div>
		