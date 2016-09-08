<%@ include file="/WEB-INF/pages/jsp/include.jsp"%> 
<portlet:resourceURL id="invalidate" var="logouturl" /> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<link rel="stylesheet" href="<%=response.encodeURL(request.getContextPath()+"/css/portletBorder.css")%>">
<link rel="stylesheet" type="text/css" href="<%=response.encodeURL(request.getContextPath()+ "/css/dialogImage.css")%>" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/belk.pep.imageDialog.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
<script src="<%=request.getContextPath()%>/js/imageManagment.js"></script>
<script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/dialogModal.js")%>"></script> 

<script>
	hljs.initHighlightingOnLoad();
</script>

<style type = "text/css">
</style>

<script type="text/javascript">

var timeOutFlag = 'no';
//TimeOut
var timeOutvar = null;
var timeOutConfirm ='N';

function timeOutPage(){
	if(timeOutConfirm=='N')
	{	
		timeOutvar = setTimeout(redirectSessionTimedOutGrpImage, 600000);
	}
}


$(document).keydown(function(e) { if (e.keyCode == 8) e.preventDefault(); });

var timeOutvarImage = null;
timeOutvarImage = setTimeout(redirectSessionTimedOutGrpImage, 3600000);
function timeOutImagePage()
{
                timeOutvarImage = setTimeout(redirectSessionTimedOutGrpImage, 3600000);
}

document.onclick = clickListenerImage;
function clickListenerImage(e){
	clearTimeout(timeOutvar);
	clearTimeout(timeOutvarImage);
	timeOutFlag = 'no';
	timeOutPage();
	timeOutImagePage();
}

function redirectSessionTimedOutGrpImage(){	
	timeOutFlag ="yes";
      	var releseLockedPetURL = $("#releseLockedPet").val();
      	var loggedInUser= $("#loggedInUser").val();
    	 releseLockedPet(loggedInUser,releseLockedPetURL);

	
	return timeOutFlag;
	
	
}
function grpclearErrorMessage(){
	document.getElementById('errorDIV').innerHTML = "";
	document.getElementById('errorDIV').style.display="none";
	document.getElementById('fileData').value = "";	
    HideGPImageUploadPopUp();

}

function goToHomeScreen(loggedInUser,releseLockedPetURL){
	if(timeOutFlag == 'yes'){
		$("#timeOutId").show();
		timeOutConfirm = 'Y';
		setTimeout(function(){logout_home(loggedInUser,releseLockedPetURL);},4000);
	}else{
		$("#timeOutId").hide();
		releseLockedPet(loggedInUser,releseLockedPetURL);
		    window.location = "/wps/portal/home/worklistDisplay";
	}
}

function goToWorkListDisplayScreen(loggedInUser,releseLockedPetURL){
	if(timeOutFlag == 'yes'){
		$("#timeOutId").show();
		timeOutConfirm = 'Y';		
		setTimeout(function(){logout_home(loggedInUser,releseLockedPetURL);},4000);
		
	}else{
		$("#timeOutId").hide();
		releseLockedPet(loggedInUser,releseLockedPetURL);
		var url = $('input[name=workListDisplayUrl]').val();
        if(url){
            window.location = $('input[name=workListDisplayUrl]').val();
        }else{
            window.location = "/wps/portal/home/worklistDisplay";
        }
	}	
	
}

		

//UI VALIDATIONS

var selectedFileSize = 0;
var constMaxFileSizeMB = 30;  //this value is same as the applicationContext value

function checkfilesize(obj){
	//console.log(obj.files[0].size);
	selectedFileSize = obj.files[0].size || 0;
	selectedFileSize = (selectedFileSize/1024)/1024;
	//alert(selectedFileSize);
}






function logout_home(loggedInUser,releseLockedPetURL){

	releseLockedPet(loggedInUser,releseLockedPetURL);
	var logouturl="${logouturl}";
	$.ajax({
		url : logouturl,
		type : 'GET',
		success : function(data) {
			console.log(data);
		}
	});
	if(loggedInUser.indexOf('@') === -1) 
		{
		   window.location = "/wps/portal/home/InternalLogin";
		} else {
			
			window.location = "/wps/portal/home/ExternalVendorLogin";
		}
		
	}




function releseLockedPet(loggedInUser,releseLockedPetURL){

	var lockedPet= $("#lockedPet").val();


	 $.ajax({
			       url: releseLockedPetURL,
			         type: 'GET',
			         datatype:'json',
			         data: { lockedPet:lockedPet,loggedInUser:loggedInUser,pepFunction:'Image' },
				         success: function(data){
                                 //alert('data '+data);
                                           					
	                }//End of Success
	        }); 
   
	return true;

}




</script>

 <%	
	String userAttr = (String)request.getAttribute("userAttr");
	System.out.println("User Attrbute.............." +userAttr);
 %>
 
<portlet:defineObjects />
<portlet:actionURL var="groupingImgUploadAction"><portlet:param name="action" value="groupingImgUploadAction" /></portlet:actionURL>
<portlet:actionURL var="formAction">  <portlet:param name="action" value="imageRemoveFormSubmit"/>
</portlet:actionURL>
<portlet:resourceURL var="groupingImgApproveAction" id="groupingImgApproveAction"></portlet:resourceURL>

<portlet:resourceURL var="removeGroupingImage" id ="removeGroupingImage">	</portlet:resourceURL>



<portlet:resourceURL var="downloadImageFile" id="downloadImageFile"></portlet:resourceURL>
<input type="hidden" id="downloadFilePathUrl" name="downloadFilePathUrl" value="${downloadImageFile}" />

<input type="hidden" id="addImageId" name="addImageId" value="${groupingImgUploadAction}" />
<input type="hidden" id="flagHidden" name="flagHidden" value=""></input>
<input type="hidden" id="hiddenImageStatus" name="hiddenImageStatus" value=""></input> 
<input type="hidden" id="hiddenRoleId" name="hiddenRoleId" value=""></input>

<fmt:setBundle basename="imagePortletResources" />
<body onload="timeOutPage();">
<div class="scrollbarset">
<div class="container">
<div id="pg">
<div id="content">
<div id="main">

<form commandName="imageDetailsForm" id="imageDetail" method="post"  name="imageDetail" 	 action="#" >
<!--Style Info Starts-->
	
<input type ="hidden" name="loginUserHidden" id="loginUserHidden" value ="${imageDetailsForm.username}" />
<portlet:resourceURL var="releseLockedPet" id ="releseLockedPet">  </portlet:resourceURL>

<input type="hidden" id="releseLockedPet" name="releseLockedPet" value="${releseLockedPet}"></input>	

<div align="left" style="display: inline; padding: 5px 10px;margin-bottom: 0.5cm" >
<input type="button" style="padding: 5px 10px;font-weight: bold" name="home" value="Home" onclick=goToHomeScreen('<c:out value="${imageDetailsForm.username}"/>','${releseLockedPet}');  />
</div>

<div align="right" style="margin-bottom: 0.5cm" >	
			<c:out value="${imageDetailsForm.username}"/>  &nbsp;	 
			<input type="button"   style="font-weight: bold" name="logout" value="Logout" 
			    onclick=logout_home('<c:out value="${imageDetailsForm.username}"/>','${releseLockedPet}');  />	
			
</div>
	
<div id="timeOutId" align="left" style="display: none;width:80%;">
	<p style="color: red; font-weight: bold; font-size: 14pt;">Your session has been ended due to inactivity</p> 
</div>


<%--Style Info Starts here--%>	




<div class="cars_panel x-hidden" id="GroupingInfoSection">
							
	<div class="x-panel-header">
		<fmt:message 	key="label.groupingInfo.header" />
	</div>	
	<div id="GroupingInfoId" class="x-panel-body" >
	<div id="image-Upload-Message-Area" class="message-area">
		
	</div>												
	<ul class="car_info" 	style="font-size: 11px; padding: 0 0 10px !important;">
	<c:if test="${not empty imageDetailsForm.styleInfo}">
		<%-- Style Information code starts here   --%>
		<c:forEach var="styleInformation" varStatus="status" items="${imageDetailsForm.styleInfo}">  
		<input type="hidden" name="lockedPet" id="lockedPet" value="<c:out value="${styleInformation.orinGrouping}" />" />       
		<input type="hidden" name="groupingId" id="groupingId" value="<c:out value="${styleInformation.orinGrouping}" />" />
		<input type="hidden" name="groupingType" id="groupingType" value="<c:out value="${styleInformation.groupingType}" />" />
		<input type="hidden" name="loggedInUser" id="loggedInUser" value="<c:out value="${imageDetailsForm.username}" />" />
		<li class="txt_attr_name" style="width: 30%;"><b><fmt:message
			key="label.GroupingNumber" /></b> &nbsp;${styleInformation.orinGrouping}&nbsp;</li>
		<li class="txt_attr_name" style="width: 25%;"><b><fmt:message
			key="label.styleInfo.deptNumber" /></b>&nbsp; ${styleInformation.deptNo}&nbsp;</li>
		<li class="txt_attr_name" style="width: 25%;"><b><fmt:message
			key="label.styleInfo.vendorName" /></b>&nbsp; ${styleInformation.vendorName}&nbsp;</li>
</ul>
<ul class="car_info"
	style="font-size: 11px; padding: 0 0 10px !important;">
	<li class="ctxt_attr_name" style="width: 30%;"><b><fmt:message
		key="label.styleInfo.styleNumber" /></b>&nbsp; ${styleInformation.styleNo}&nbsp;</li>
	<li class="txt_attr_name" style="width: 25%;"><b><fmt:message
		key="label.styleInfo.class" /></b>&nbsp; ${styleInformation.imageClass}&nbsp;</li>
	<li class="txt_attr_name"><b><fmt:message
		key="label.styleInfo.vendorNumber" /></b>&nbsp; ${styleInformation.vendorNo}&nbsp;</li>

</ul>

<ul class="car_info"
	style="font-size: 11px; padding: 0 0 10px !important;">
	
	<li class="txt_attr_name" style="width: 25%;"><b><fmt:message
		key="label.styleInfo.omniChannelVendor" /></b> &nbsp;${styleInformation.omniChannelVendor}&nbsp;</li>
	<li class="txt_attr_name" ><b><fmt:message
		key="label.styleInfo.vendorProvidedImage" /></b>&nbsp; ${styleInformation.vendorProvidedImage}&nbsp;</li>
<li class="txt_attr_name" style="margin-left: 120px;"><b>&nbsp;<fmt:message key="label.styleInfo.vendorProvidedSample"/></b> ${styleInformation.vendorProvidedSample}&nbsp;</li>


</ul>

	</c:forEach>
	</c:if>
    <div style="padding:10px;float:right">
        <input type="button" name="Close" value="Close"   class="closeContentButton"  onclick="javascript:goToWorkListDisplayScreen('<c:out value="${imageDetailsForm.username}"/>','${releseLockedPet}');" style= " width: 100px; height: 30px;"/>
    </div>
<portlet:actionURL var="formAction">
    <portlet:param name="action" value="workListDisplay"/>
    <portlet:param name="orinNumber" value="${orinNumber}"/>
    <portlet:param name="pageNumber" value="${pageNumber}"/>
</portlet:actionURL>
<input type="hidden" name="workListDisplayUrl" value="${formAction}" />
  </div>	
</div>	


<%--Style Info Ends--%>
<%--Grouping  Details Starts here--%>

<div class="cars_panel x-hidden" id="GroupingDetails">								
		<div class="x-panel-header">
			<fmt:message key="label.groupingDetails.header"/>
		</div>
		
		<div id="GroupingDetailsId" class="x-panel-body" style="width:906px;">												
			<c:if test="${not empty imageDetailsForm.imageProductInfo}">		
			<ul class="attrs">
				<c:forEach var="productInformation" varStatus="status" items="${imageDetailsForm.imageProductInfo}">
				<li class="prod_name">		
					<label><b><fmt:message key="label.groupingDetails.groupName"/></label>	</b>		
						<textarea  style="margin-left:30px;"  id ="txt_outfitName"  name="vendorStyle.Name:512224" cols="100" rows="5" onkeyup="textCounter();" onblur="textCounter();"  readonly>${productInformation.productName}</textarea>
				</li>	
				<li class="prod_desc">
					<label><b><fmt:message key="label.productDescription"/></label></b>			
					<textarea id="vendorStyle.Descr:512224"	 name="vendorStyle.Descr:512224" onkeyup="textCounter();" cols="100" rows="5 onblur="textCounter();" readonly>${productInformation.productDescription}</textarea>		
									
					<br style="clear:both;" />
				</li>
				</c:forEach>		
			</ul>
			</c:if>	
			
	    </div>	
	</div>	

<%--Grouping  Details ends here--%>
<%--VPI and Sample Image Links starts--%>
<%--Logic to handle user data--%>


<c:if test ="${requestScope.userAttr == 'dca'}">
<div id="img_mgmt_pnl" class="cars_panel x-hidden" style="margin-top: 0px;">


	<div class="x-panel-header">
		<strong><fmt:message key="label.vpisampleImage.header" /></strong>
	</div>
	<div class="x-panel-body" style="width:906px;">	

	
	
<div class="userButtons" style="margin-bottom:10px">

<form id="vendorImageDetailsFormID" name="vendorImageDetailsFormID" action="${formAction}">



<input class="btn uploadButton" id="btnGPImageUploadAction"  align="center" type="button" name="imageUploadSubmit" value="Upload VPI"  style="margin-left:400px;" />


 <input type="checkbox" name="pubTOwebcheckbox"  value="09"  style="margin-left:63px;" onchange="myFunction(this);"  /> <b>
<fmt:message key="label.publishToWeb" /></b>	

	

<input    class="btn uploadButton" type="button" id="image_approve" onclick="confirmGroupingImageApprove('${saveImageShotType}');" name="image_approve" title="Approve" width="48" value="  Approve  "  style="margin-left:283px;margin-top:-18px" />								

</div>
		<div class="need_images">							
		<div id="vendor_images">



	
	
<table id="groupImageTable" cellpadding="0" cellspacing="0">
<input type="hidden" name="hidImageId" id="hidImageId" value=""/>
				<thead >
				<tr>								
					<th><fmt:message key="label.vpisampleImage.imageID"/></th>
					<th><fmt:message key="label.originalImageName.header"/></th>
					<th><fmt:message key="label.vpisampleImage.imageName"/></th>																
					<th><fmt:message key="label.imageManagement.imageStatus"/></th>
					<th>Remove</th>
				</tr>
				 </thead>
				
	</table>

 </form>
				</div>
			</div>	
		
	</div>
</div>

</c:if>

<!-- Readonly user block starts -->
<c:if test ="${requestScope.userAttr eq 'readonly'}">
<div id="img_mgmt_pnl" class="cars_panel x-hidden" style="margin-top: 0px; margin-bottom: 0px;">
	<div class="x-panel-header">
		<strong><fmt:message key="label.vpisampleImage.header" /></strong>
	</div>
	<div class="x-panel-body">
<div class="userButtons">
<c:if test = "${requestScope.petStatus ne 'Deactivated'}">
	<input class="btn uploadButton" id="btnGPImageUploadAction"  align="center" type="button" name="imageUploadSubmit" value="Upload VPI"  style="margin-left:400px;" disabled/>
</c:if>	
<input type ="hidden" id = "image_approve" />
<input type ="hidden" id = "saveImage" />					
</div>		
		<div class="need_images">							
		<div id="vendor_images">
<table id="groupImageTable" cellpadding="0" cellspacing="0">
<input type="hidden" name="hidImageId" id="hidImageId" value=""/>

				<thead >
				<tr>								
					<th><fmt:message key="label.vpisampleImage.imageID"/></th>
					<th><fmt:message key="label.originalImageName.header"/></th>
					<th><fmt:message key="label.vpisampleImage.imageName"/></th>																
					<th><fmt:message key="label.imageManagement.imageStatus"/></th>
					<th>Remove</th>
				</tr>
				 </thead>
				
	</table>
	</div>
			</div>	
		
	</div>
</div>

</c:if>

<!-- Readonly user block ends -->

<%--Scene 7 Image URL starts--%>


<div id="img_mgmt_pnl" class="x-panel-header" style="margin-top: -20px;">
	<div class="x-panel-header" style="margin-top: 0px;">
		<strong><fmt:message key="image.scene7.header" /></strong>
	</div>
	<div class="x-panel-body">
	<div id="image-operations-Message-Area" class="message-area">
		
	</div>
	<br />

		<div class="need_images">							
			<div id="vendor_images">
				<table id="vImage" cellpadding="0" cellspacing="0" width="90%">
				<thead>
				<tr>
				<th class="sorted order1"><fmt:message key="image.scene7.shottype" /></th>
				<th><fmt:message key="image.scene7.imageurl" /></th>
				<th><fmt:message key="image.scene7.swatchurl" /></th>
				<th><fmt:message key="image.scene7.viewurl" /></th>
				</tr>
				</thead>

				<c:if test="${not empty imageDetailsForm.imageLinkVOList}">	
				<c:forEach items= "${imageDetailsForm.imageLinkVOList}"  var="imageLinkVOList">
				<tr>  
				<c:if test="${not empty imageLinkVOList.imageURL}">
					<td><c:out value="${imageLinkVOList.shotType}"/></td>					
					<td><a href="#" onclick="openGRPScen7Image('<c:out value="${imageLinkVOList.imageURL}"/>');">ImageURL</a></td>
					<td><a href="#" onclick="openGRPScen7Image('<c:out value="${imageLinkVOList.swatchURL}"/>');">SwatchURL</a></td>
					<td><a href="#" onclick="openGRPScen7Image('<c:out value="${imageLinkVOList.viewURL}"/>');">ViewURL</a></td>
					</c:if>					
				</tr>
				</c:forEach>
				</c:if>
				</table>
				</form>
			</div>
		</div>	
		
	</div>
</div>
<!-- Scene 7 Image URL block ends -->

<%--PEP History Block Starts Here--%>
<div id="car_history_details_pnl" class="cars_panel x-hidden collapsed">
	<div class="x-panel-header">
		<fmt:message key ="label.pepHistory.header" />
			</div>
	<div class="x-panel-body" style="font-size: 11px;font-family: tahoma,arial,helvetica,sans-serif;">		
	
	<table cellspacing="1" cellpadding="1" border="1" class="table tree2 table-bordered table-striped table-condensed" id="historyID">
	<thead>	
	<tr>										
		<th><fmt:message key ="label.pepHistory.pet" /> </th>
		 <th><fmt:message key ="label.pepHistory.datePet" /></th>
		 <th><fmt:message key ="label.pepHistory.petCreatedBy"/> </th>
		 <th><fmt:message key ="label.pepHistory.imageLastUpdatedDt" /></th>
		 <th><fmt:message key ="label.pepHistory.imageLastUpdatedBy" /></th>
		 <th><fmt:message key ="label.pepHistory.imageLastUpdatedAtGrp"/></th>									
  </tr>
  </thead>
	<c:if test="${not empty imageDetailsForm.pepHistoryList}">
		<c:forEach var="pepHistory" varStatus="status" items="${imageDetailsForm.pepHistoryList}">
		
		<tr>
			<td>${pepHistory.pepOrinNumber}</td>		
			<td>${pepHistory.createdOn}</td>
			<td>${pepHistory.createdBy}</td>			
			<td>${pepHistory.updateDate}</td>
			<td>${pepHistory.pepUpdatedBy}</td>
			<td>${pepHistory.updatedStatus}</td>
			<!--<td>${pepHistory.historyStatus}</td>-->
		</tr>
	</c:forEach>
			</c:if>
			</table>
				
		</div>
	</div>

</div>
</form>
<%--PEP History Block ends Here--%>
</form><!-- Main form ends here -->

<%--Upload VPI POPUP Image Starts--%>
<div id="grp_overlay_UploadImage" class="web_dialog_overlay"></div>    
<div id="grp_dialog_UploadImage" class="web_dialog_gpr_imageUploadPopUp" style="height: 210px;">
 <div id="content">
 	<div class="x-panel-header">
	Upload Image
	</div>
	<div class="x-panel-body;border: 0px solid #99bbe8;">
	<form:form id="imageuploadformid" modelAttribute="fdForm" method="post" action="${groupingImgUploadAction}" enctype="multipart/form-data">
	        <input type="hidden" name="groupingIdSel" id="groupingIdSel" value=""/>	
	        <input type="hidden" name="groupType" id="groupType" value=""/>							
			<div id="errorDIV" style="display:none;"></div>	              
              </br>
		   </br>
		<ul>
			<li>				
				<label style="margin-left:53px;height: 16px;">Image Location *:</label>
						<select id="imageLocationButton" name="imageLocation" onchange="SelectImageLocation(this.value);" style="height: 18px;" >														
							<option value="BML" selected>Browse My Locations</option>												
						</select>																		
			</li>
				</br>
				<div id="selectImage">			
			<li>
				<label style="margin-left:65px;height: 16px;">Select Image *:</label> 
				<input name="fileData" id="fileData"type="file" onchange="checkfilesize(this);" accept="Image/jpeg,image/jpg,image/psd,image/tiff,image/eps" />
			</li>
			</div>
			</br>				
			</br>
			</br>
		<li class="buttons" style="float:right;">		
		<input class="btn"   id="btnGprImageUploadPopUpClose" type="button" name="Cancel" value="Cancel" style="margin-right: 4px;" onclick="grpclearErrorMessage();" /> 		
		<input class="btn" type="button" name="imageSave1" id="imageSave1" value="Add Image" style="margin-right: 10px;" onclick="validateGrpImageFields('imageuploadformid');"/>
	</li>
</ul>
<!--Image Loading message starts-->
	<div id="overlay_groupimageLoading" style="display:none;">
		<img src="<%=request.getContextPath()%>/img/loading.gif" height="100px;" />
	</div>	

<!--Image Loading message ends -->
</form:form>
</div>
</div>
</div>
<%--Upload VPI Image POPUP Ends--%>

<%--File Upload Render Starts--%>
<div id="overlay_Upload" class="web_dialog_overlay"></div>
<div id="dialog_uploadSuccess" class="web_dialog_gpr_imageUploadPopUp" style="height: 140px;">
 <div id="content"> 
	<div class="x-panel-header">
	Upload Image Status
	</div>
	<div class="x-panel-body;border: 0px solid #99bbe8;">	
     </br>
	</br>
		<ul>
			<li>				
				<label style="margin-left:53px;height: 16px;">Image ${imageName} successfully uploaded. </label>
			</li>				
			</br>						
		<input class="btn"   id="successPopUpClose" type="button" onclick='$("#overlay_Upload").hide();$("#dialog_uploadSuccess").hide();' name="Close" value="Close" style="float: right;margin-right:10px;" /> 	
		
	</li>
</ul>

</div>

<div id="dialog_uploadFailure" class="web_dialog_imageaction">Image not uploaded.
<br/>
<br/>
<input id="button" type="button" value="Close" onclick="dialogHide();"/>
</div>
</div>
</div>

<!-- Image Approve Confirmation Popup Starts-->
<div id="dialog_submitApprove" class="web_dialog_gpr_imageUploadPopUp" style="height: 140px; width:350px;">
	<div id="content">
		<div class="x-panel-header">
			Approve Image Confirmation
			<input type="hidden" id="imgHiddenId" name="imgHiddenId" value=""></input>
			<input type="hidden" id="imgRowId" name="imgRowId" value=""></input>
			<input type="hidden" id="imgNameHiddenId" name="imgNameHiddenId" value=""></input>			
		</div>
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<ul>
				<li style="width:100%;float:left;margin-top:10px;">				
					<label style="margin-left:53px;height: 16px;">Are you sure you want to Approve? </label>
				</li>				
			</br>
				<li style="width:100%;float:left;margin-top:10px;">
					<input class="btn"   id="approvePopUpClose" type="button" onclick='$("#overlay_Upload").hide();$("#dialog_submitApprove").hide();' name="Cancel" value="Cancel" style="float: right;margin-left:10px;margin-right:50%;" /> &nbsp;&nbsp;&nbsp;&nbsp;				
				
					<input class="btn"   id="approveConfirmPopUpOkId" type="button" onclick="groupingImageApproveAction('${groupingImgApproveAction}');dialogGRPApproveHideonOk();" name="Ok" value="Ok" style="float: right;" />			
					
				</li>				
			</ul>

		</div>
	</div>
</div>
<!-- Image Approve Confirmation Popup Ends-->

<div id="dialog_submitRemove" class="web_dialog_gpr_imageUploadPopUp" style="height: 140px; width:350px;">
	<div id="content">
		<div class="x-panel-header">
			Remove Image Confirmation
			<input type="hidden" id="imgHiddenId" name="imgHiddenId" value=""></input>
			<input type="hidden" id="imgRowId" name="imgRowId" value=""></input>
			<input type="hidden" id="imgNameHiddenId" name="imgNameHiddenId" value=""></input>			
		</div>
		<div class="x-panel-body;border: 0px solid #99bbe8;">


			</br>
			</br>
			<ul>
				<li style="width:100%;float:left;margin-top:10px;">				
					<label style="margin-left:53px;height: 16px;">Are you sure you want to remove? </label>
				</li>				
			</br>
				<li style="width:100%;float:left;margin-top:10px;">
					<input class="btn"   id="removePopUpClose" type="button" onclick='$("#overlay_submitOrReject").hide();$("#dialog_submitRemove").hide();' name="Cancel" value="Cancel" style="float: right;margin-left:10px;margin-right:50%;" /> &nbsp;&nbsp;&nbsp;&nbsp;				
				
					<input class="btn"   id="removeConfirmPopUpOkId" type="button" onclick="removeGroupingImage(document.getElementById('imgHiddenId').value,document.getElementById('imgNameHiddenId').value,'${removeGroupingImage}');dialogHideonOk();" name="Ok" value="Ok" style="float: right;" />			
					
				</li>				
			</ul>


		</div>
	</div>
</div>


<%--File Upload Render Ends--%>
</div>
</div>
</div>
</div>
</div>
</body>
<script>

var uploadStatus = '${uploadSuccess}';
var imageCount = '${imageCount}';
var imageStatus= '${imageStatus}';
var imageFilePath= '${imageFilePath}';
var uploadImgeId= '${uploadImgeId}';
var groupVPILinks= '${groupVPILinks}';
var imageOverallStatus= '${imageOverallStatus}';

$(function() {
 
if ((typeof groupVPILinks !== "undefined" && groupVPILinks !== null) && (uploadImgeId !=="" &&  uploadImgeId !==null) ) 
         { 

	  document.getElementById('hidImageId').value = uploadImgeId;
  	  onloadVPILinks(groupVPILinks);

	 }

	  if(uploadStatus=='Y'){		
		showGrpImageActionMessage('uploadSuccess', '${imageName}');
		document.getElementById('btnGPImageUploadAction').disabled=true;
		
	  }else if(uploadStatus=='N'){

	 showGrpImageActionMessage('uploadError', '${imageName}');
	
	}
	if (typeof imageCount !== "undefined" && (imageCount.trim()) > 0) 
         {
	      document.getElementById('btnGPImageUploadAction').disabled=true;
	 }
	if (typeof imageCount !== "undefined" && (imageCount.trim()) > 0) 
         {
	      document.getElementById('btnGPImageUploadAction').disabled=true;
	 }


	if ( imageStatus=='Completed' ||  imageOverallStatus=='Completed') 
         { 
  	 document.getElementById('image_approve').disabled = true ;
 	 document.getElementById('btnGPImageUploadAction').disabled=true;

	 }
	 
	});
	
$(document).keydown(function(e) { if (e.keyCode == 8) e.preventDefault(); });

var timeOutvarImage = null;
timeOutvarImage = setTimeout(redirectSessionTimedOutGrpImage, 3600000);
function timeOutImagePage()
{
     timeOutvarImage = setTimeout(redirectSessionTimedOutGrpImage, 3600000);
}

document.onclick = clickListenerImage;
function clickListenerImage(e){
	clearTimeout(timeOutvar);
	clearTimeout(timeOutvarImage);
	timeOutFlag = 'no';
	timeOutPage();
	timeOutImagePage();
}

</script>
