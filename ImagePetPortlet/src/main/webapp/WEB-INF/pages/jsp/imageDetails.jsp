<%@ include file="/WEB-INF/pages/jsp/include.jsp"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<link rel="stylesheet" href="<%=response.encodeURL(request.getContextPath()+"/css/worklistDisplay.css")%>">
<link rel="stylesheet" href="<%=response.encodeURL(request.getContextPath()+"/css/portletBorder.css")%>">
<link rel="stylesheet"  href="<%=response.encodeURL(request.getContextPath()+ "/css/bootstrap.css")%>"/>
<link rel="stylesheet" type="text/css" href="<%=response.encodeURL(request.getContextPath()+ "/css/jquery.treegrid.css")%>"/>
<link rel="stylesheet" type="text/css" href="<%=response.encodeURL(request.getContextPath()+ "/css/default.min.css")%>">
<link rel="stylesheet" type="text/css" href="<%=response.encodeURL(request.getContextPath()+ "/css/dialogImage.css")%>" />
<link rel="stylesheet" type="text/css" href="<%=response.encodeURL(request.getContextPath()+ "/css/jquery-ui.css")%>" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.treegrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/treegridlocal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/belk.pep.imageDialog.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
<script src="<%=request.getContextPath()%>/js/imageManagment.js"></script>

<script>
	var jq = jQuery.noConflict(true);
</script>

<script>
	hljs.initHighlightingOnLoad();
</script>

<style type = "text/css">
#overlay_imageLoading
{   
   position: fixed;
   width: 550px;
   height: 250px;
   top: 40%;
   left: 40%;	
}
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
.dlg-custom .ui-dialog-titlebar-close {
  display: none;
}
.message-area{
	width: 60%;
	margin: 0 auto;
	display: none;
}
.ui-custom-message-styling-info{
	height:54px;
	background: #fffa90 !important;
}
.ui-custom-message-styling-error{
	height:54px;
	background: #fddfdf !important;
}
.ui-custom-message-styling-info p, .ui-custom-message-styling-error p{
	margin: 19px;
}
</style>

<script type="text/javascript">

var timeOutFlag = 'no';
var inputChanged = true;
if (typeof document.getElementById('removeFlagOff')  !== "undefined" && document.getElementById('removeFlagOff')){
	document.getElementById('removeFlagOff').value = inputChanged;
}

	$(document).ready(function() {

		$('.tree').treegrid();
		$('.tree2').treegrid({
			expanderExpandedClass : 'icon-minus-sign',
			expanderCollapsedClass : 'icon-plus-sign'
		});
	});
  function SelectImageLocation(selectedValue){
  document.getElementById('errorDIV').innerHTML = '';
  if(selectedValue=='FTP'){
  document.getElementById("ftpDiv_1").style.display = 'block';
  document.getElementById("selectImage").style.display = 'none'; 
  }else{
  document.getElementById("ftpDiv_1").style.display = 'none';
  document.getElementById("selectImage").style.display = 'block';
    
  }
 
 }
$(document).ready(function() {

    jq("#datepicker1").datepicker({
        showOn: 'button',
        buttonText: 'Date',
        buttonImageOnly: true,
        buttonImage: '<%=response.encodeURL(request.getContextPath()+ "/img/iconCalendar.gif")%>',
        dateFormat: 'mm/dd/yy',
        constrainInput: true
    });

    $(".ui-datepicker-trigger").mouseover(function() {
        $(this).css('cursor', 'pointer');
    });


});


//TimeOut
var timeOutvar = null;
var timeOutConfirm ='N';
/* document.onclick = clickListener;
function clickListener(e){
	clearTimeout(timeOutvar);
	timeOutPage();
} */

function timeOutPage(){
	if(timeOutConfirm=='N')
	{	
		timeOutvar = setTimeout(redirectSessionTimedOut, 600000);
	}
}


function redirectSessionTimedOut(){	
	timeOutFlag ="yes";
      	var releseLockedPetURL = $("#releseLockedPet").val();
      	var loggedInUser= $("#loggedInUser").val();
    	 releseLockedPet(loggedInUser,releseLockedPetURL);

	
	return timeOutFlag;
	
	
}

//goToWorkListDisplayScreen()
function goToWorkListDisplayScreen(loggedInUser,releseLockedPetURL){
inputChanged  = false ;
document.getElementById('removeFlagOff').value = inputChanged;
	if(timeOutFlag == 'yes'){
		$("#timeOutId").show();
		timeOutConfirm = 'Y';		
		setTimeout(function(){logout_home(loggedInUser,releseLockedPetURL);},4000);
		
	}else{
		$("#timeOutId").hide();
		releseLockedPet(loggedInUser,releseLockedPetURL);
		window.location = "/wps/portal/home/worklistDisplay";
		//window.location = "/wps/portal/WorkListDisplayPageUrl";	
	}
	
	
}
		

function getSaveImageShotTypeAjax(url){	
	var selectedOrin = $("#selectedOrinVPI").val();	
	document.getElementById('saveImageShotTypeHiddenID').value = url;
	var count1 = 0;
	var count2 = 0;
	var duplicateShotType = 'N';
	$("#vImage tr:gt(0)").each(function(i){		
		var shotTypeSelected = $(this).find("#shotType :selected").val();				
				count2 = 0;
				$("#vImage tr:gt(0)").each(function(i){					
					var shotTypeSelected1 = $(this).find("#shotType :selected").val();					
					if(shotTypeSelected == shotTypeSelected1 && count1 != count2 && shotTypeSelected != '---Select---'){
						duplicateShotType = 'Y';	
					}					
					if(shotTypeSelected == '---Select---' && duplicateShotType!='Y'){
						duplicateShotType = 'N';	
					}					
					count2++;
					});		
		count1++;
		
	});
	var selectedOrinImageShotArray=[];
	
	var ifCount = 0;	

	if(duplicateShotType == 'N') {
	

	var arrayElementText = '';
	$("#vImage tr:gt(0)").each(function(i){		
		var passImgid = $(this).find("#imageId").text();		
		var passShotType = $(this).find("#shotType :selected").val();
		var hiddenShotType = $(this).find("#hiddenShotType").val();			
			if(passShotType!=hiddenShotType){
			
				if(ifCount>=1){
					arrayElementText =arrayElementText + ";";
				}
			arrayElementText =arrayElementText + selectedOrin + '_' + passImgid + '_' + passShotType;			
		    ifCount = ifCount+1;
		}		
		
	});//Close Each
	//Fix for 871
	if(arrayElementText.length == 0){
		return;
	}//End
	$("#overlay_imageLoading").show();
	
	$.ajax({
					type: 'POST',
					url : url,
					datatype:'json',
					data: {'selectedOrinImageShotArray': arrayElementText},
					cache: true,
					async: true,
					success: function(data){						
						var json = $.parseJSON(data);						
						var responseCode = json.resCode;						
						if(responseCode == '100'){
							//$("#saveShotTypeIDSuccess").css("margin-left",'74px');
							document.getElementById('saveShotTypeID').innerHTML = '';
							document.getElementById('saveShotTypeIDSuccess').innerHTML= 'ShotType Saved Successfully';
							//$("#overlay_Upload").show();
							//$("#dialog_uniqueShotTypeCheckId").show();
							
							//jq('#dialog_uniqueShotTypeCheckId').dialog('open');
							showImageActionMessage('saveSuccess'); //new message instead of dialog for as per requirement 931
						}
						
						setUploadVPILink($("#ajaxaction").val(),document.getElementById("selectedColorOrinNum").value,$("#removeImageUrl").val());
						trClick();
						scrollToView('vImage','vImage');
						
					},
					error:function (xhr, ajaxOptions, thrownError){
						var error1 = $.parseJSON(xhr.responseText);
						console.log("Severe Service Error::" + thrownError + "Status Code::" + xhr.status +"error::"+ error1);
				},
				complete: function(){
					$("#overlay_imageLoading").hide();
				} 
			});
	
	}
	
	else{
		document.getElementById('saveShotTypeIDSuccess').innerHTML = '';
		//$("#saveShotTypeID").css("margin-left",'-17px');
		document.getElementById('saveShotTypeID').innerHTML= 'Please select unique shottype for each uploaded image before saving';
		//$("#overlay_Upload").show();
		//$("#dialog_uniqueShotTypeCheckId").show();
		jq('#dialog_uniqueShotTypeCheckId').dialog('open');
		
		return false;
	}
	/* setTimeout(function(){setUploadVPILink($("#ajaxaction").val(),document.getElementById("selectedColorOrinNum").value,$("#removeImageUrl").val());trClick();scrollToView('vImage','vImage');},3000); */	
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

function validateFields(formId){
	inputChanged  = false ;	
	document.getElementById('removeFlagOff').value = inputChanged;
	var ftpUrl11 = document.getElementById('ftpUrl1').value;
	var ftpPath1 = document.getElementById('ftpPath').value;
	var ftpFileName1 = document.getElementById('ftpFileName').value;
	var ftpUserId1 = document.getElementById('ftpUserId').value;
	var ftpPassword11 = document.getElementById('ftpPassword1').value;
	var imageLocation = document.getElementById('imageLocationButton').selectedIndex;
	var shotTypeNull = '';
	var passImgid = '';
	//logic to check null shot type
	var selectedOrin = $("#selectedOrinVPI").val();	
	$("#vImage tr:gt(0)").each(function(i){		
		var hiddenShotType = $(this).find("#hiddenShotType").val();		
		if(hiddenShotType == 'undefined' || hiddenShotType == null){
			passImgid = $(this).find("#imageId").text();
			shotTypeNull = 'Y';			
		}
	});
	//logic to check null shot type ends
	var errorMessage = '';
			if(imageLocation== 1){
				if(!ftpUrl11){
					errorMessage = errorMessage+"<span style='color:red'>Please enter FTP Url &nbsp;</span>";
				}	
				if(!ftpFileName1){
					errorMessage = errorMessage+"<span style='color:red'>Please enter Image Name &nbsp;</span>";
				}
				if(!ftpUserId1){
					errorMessage = errorMessage+"<span style='color:red'>Please enter Username &nbsp;</span>";
				}
			if(!ftpPassword11){
					errorMessage = errorMessage+"<span style='color:red'>Please enter Password &nbsp;</span>";
			}	
			if(errorMessage.trim().length>0){		
				document.getElementById('errorDIV').style.display ="";
				document.getElementById('errorDIV').innerHTML = errorMessage;
			}else{				
				$("#overlay_Upload").hide();
				$("#dialog_UploadImage").hide();
				jq('#dialog_UploadImage').dialog('close'); //closing upload dialog
				
				$("#overlay_imageLoading").show();
				//setTimeout(function(){document.getElementById(formId).submit();},3000);
				document.getElementById(formId).submit();
				
			}
		}//End imageLocation check for FTP
	else{
		if(imageLocation ==0){		
			var fileType = document.getElementById('fileData').value;
			var fileExtension = fileType.lastIndexOf('.');
			var ext = fileType.substring(fileExtension+1).toLowerCase();
			if(fileType.length > 0){
				if(selectedFileSize > constMaxFileSizeMB){
					document.getElementById('errorDIV').style.display ="";
					errorMessage = errorMessage+"<span style='color:red'>Please select a file lesser than " + constMaxFileSizeMB + " MB in size &nbsp;</span>";
					document.getElementById('errorDIV').innerHTML = errorMessage;
				}else if(ext == 'jpeg' || ext == 'jpg' || ext =='psd'|| ext =='tiff'|| ext =='eps' || ext =='tif'){
					document.getElementById('errorDIV').innerHTML = "";
					$("#overlay_Upload").hide();
					$("#dialog_UploadImage").hide();
					
					jq('#dialog_UploadImage').dialog('close'); //closing upload dialog
					
					$("#overlay_imageLoading").show();					
					setTimeout(function(){document.getElementById(formId).submit();},500);
				}else{
					document.getElementById('errorDIV').style.display ="";
					errorMessage = errorMessage+"<span style='color:red'>Please enter a valid file format &nbsp;</span>";
					document.getElementById('errorDIV').innerHTML = errorMessage;
				}
		}else{//End file Blank check		
			document.getElementById('errorDIV').style.display ="";
			errorMessage = errorMessage+"<span style='color:red'>Please browse image file &nbsp;</span>";
			document.getElementById('errorDIV').innerHTML = errorMessage;
		}
	 }
  }
}

function clearErrorMessage(){
	document.getElementById('errorDIV').innerHTML = "";
	document.getElementById('errorDIV').style.display="none";
	document.getElementById('fileData').value = "";	
	
	jq('#dialog_UploadImage').dialog('close'); //closing upload dialog
}
//UI VLIDATION ENDS

function submitApproveAction(url){
 var selectedColorOrinNum = $("#selectedColorOrinNum").val();	
		$("#overlay_imageLoading").show();	
		$.ajax({				
				type: 'POST',
				url: url,
				data: { approveOrRejectOrinNum:selectedColorOrinNum },
				success: function(data){ 					
					var json = $.parseJSON(data);
					var responseCode = json.responseCode;					
					if(responseCode == '100'){					
					var completionStatusId = selectedColorOrinNum+'_statusId'; 					
					document.getElementById(completionStatusId).innerHTML = 'Completed';
					document.getElementById('hiddenImageStatus').value = "Completed";
					if(document.getElementById('hiddenImageStatus').value == "Completed" || document.getElementById('hiddenImageStatus').value == "Approved"){
						document.getElementById('image_approve').disabled = true ;
						document.getElementById('btnImageUploadAction').disabled = true ;					
						document.getElementById('saveImage').disabled = true ;
					}
					
					$('#image-operations-Message-Area').fadeOut('fast').html(''); //hotfix for #931 after image removal process;
					
					//goToWorkListDisplayOnApprove(loggedInUser,releseLockedPetURL);
				  }
					
					setUploadVPILink($("#ajaxaction").val(),document.getElementById("selectedColorOrinNum").value,$("#removeImageUrl").val());
					trClick();
					scrollToView('vImage','vImage');
					
					
				},
				cache: false,                
				error: function(jqXHR,textStatus, errorThrown){
					//alert(jqXHR.status + ": " + textStatus + "- " + errorThrown);
				},
				complete: function(){
					$("#overlay_imageLoading").hide();
				}
			});
		
			/* setTimeout(function(){setUploadVPILink($("#ajaxaction").val(),document.getElementById("selectedColorOrinNum").value,$("#removeImageUrl").val());trClick();scrollToView('vImage','vImage');},2000); */			
}

/*function goToWorkListDisplayOnApprove(loggedInUser,releseLockedPetURL){	
	inputChanged  = false ;
	document.getElementById('removeFlagOff').value = inputChanged;
	//window.location = "/wps/portal/WorkListDisplayPageUrl";
	releseLockedPet(loggedInUser,releseLockedPetURL);
	window.location = "/wps/portal/home/worklistDisplay";
}*/

function scrollToView(attToView,attFocus){	
	var ele = document.getElementById(attToView);	
	var curleft = curtop = 0;	
	if (ele.offsetParent) {
		curleft = ele.offsetLeft;
		curtop = ele.offsetTop;
		while (ele = ele.offsetParent){
			curleft += ele.offsetLeft;
			curtop += ele.offsetTop;
		}	
	} 	
  	window.scrollTo(curleft, curtop);
	document.getElementById(attFocus).focus();	
	return false;	
}

function dialogApproveHideonOk(){
	//$("#overlay_Upload").hide();
	//$("#dialog_submitApprove").hide();
	
	jq('#dialog_submitApprove').dialog('close');
}


function dialogApproveShow(){		
	//$("#overlay_Upload").show();
	//$("#dialog_submitApprove").show();
	
	jq('#dialog_submitApprove').dialog('open');
}

function disableAllButtons(){	
	if(document.getElementById('hiddenImageStatus').value == 'Completed' || document.getElementById('hiddenImageStatus').value == 'Approved'){		
		document.getElementById('btnImageUploadAction').disabled = true;
		document.getElementById('saveImage').disabled = true ;
		document.getElementById('image_approve').disabled = true ;
	}
}

function checkShotTypeOnApprove(url){	
	var shotTypeNull = '';
	var passImgid = '';	
	var isCheckShotType= 'false';
	
	//$("#overlay_Upload").hide();
	//$("#dialog_submitApproveCheck").hide();
	//$("#overlay_Upload").hide();
	//$("#dialog_submitApprove").hide();
	
	//Fix for 720 Start
	if($("#vImage tr:gt(0)").length <= 0){		
		dialogApproveShow();		
		return;
	}
	//Fix for 720 End	
	$("#vImage tr:gt(0)").each(function(i){
		var hiddenShotType = $(this).find("#hiddenShotType").val();		
		if(hiddenShotType == 'undefined' || hiddenShotType == null){
			passImgid = $(this).find("#imageId").text();
			shotTypeNull = 'Y';			
		}

		else if(hiddenShotType == 'A'){
			isCheckShotType= 'true';
		}		
	});	
	if(shotTypeNull != 'Y'){
		if(isCheckShotType == 'true'){
			//$("#overlay_Upload").hide();
			//$("#dialog_submitApproveCheck").hide();			
			jq('#dialog_submitApproveCheck').dialog('close');			
			dialogApproveShow();
		}	
		else{			 
			 //$("#overlay_Upload").show();
			 //$("#dialog_checkShotTypeA").show();			 
			 jq('#dialog_checkShotTypeA').dialog('open');
		}	
				
	}else if (shotTypeNull == 'Y'){
			
		//$("#overlay_Upload").show();
		//$("#dialog_submitApproveCheck").show();

		jq('#dialog_submitApproveCheck').dialog('open');
	}	
}
function logout_home(loggedInUser,releseLockedPetURL){
inputChanged  = false ;
document.getElementById('removeFlagOff').value = inputChanged;
	releseLockedPet(loggedInUser,releseLockedPetURL);
	if(loggedInUser.indexOf('@') === -1) 
		{
		   window.location = "/wps/portal/home/InternalLogin";
		} else {
			
			window.location = "/wps/portal/home/ExternalVendorLogin";
		}
		
	}


var confirmationMessage = '';  // a space
var myEvent = window.attachEvent || window.addEventListener;
var chkevent = window.attachEvent ? 'onbeforeunload' : 'beforeunload'; /// make IE7, IE8 compitable

           myEvent(chkevent, function(e) { // For >=IE7, Chrome, Firefox          
          if(inputChanged && confirmationMessage !== false){ 
           (e || window.event).returnValue = confirmationMessage;

	var releseLockedPetURL = $("#releseLockedPet").val();
	var loggedInUser= $("#loggedInUser").val();
 	releseLockedPet(loggedInUser,releseLockedPetURL);

                return confirmationMessage;
}
 });

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

<portlet:actionURL var="imageUploadAction"><portlet:param name="action" value="imageUploadAction" /></portlet:actionURL>
<portlet:resourceURL var="imageApproveAction" id="imageApproveAction"></portlet:resourceURL>
<portlet:resourceURL var="saveImageShotType" id="saveImageShotType"></portlet:resourceURL>

<input type="hidden" id="addImageId" name="addImageId" value="${uploadedFileAction}" />
<input type="hidden" id="selectedColorOrinNum" name="selectedColorOrinNum" value="" />
<input type="hidden" id="saveImageHiddenId" name="saveImageHiddenId" value=""></input>
<input type="hidden" id="flagHidden" name="flagHidden" value=""></input>
<input type="hidden" id="hiddenOrinId" name="hiddenOrinId" value=""></input>
<input type="hidden" id="hiddenImageStatus" name="hiddenImageStatus" value=""></input> 
<input type="hidden" id="hiddenRoleId" name="hiddenRoleId" value=""></input>

<fmt:setBundle basename="imagePortletResources" />
<body onload="timeOutPage();">
<div class="container">
<div id="pg">
<div id="content">
<div id="main"><input type="hidden" id="removeImageUrl"
	name="removeImageUrl" value="${removeUrl}"></input>

<form commandName="imageDetailsForm" id="imageDetail" method="post"
	action="#"><!--Style Info Starts-->
	
<input type ="hidden" name="loginUserHidden" id="loginUserHidden" value ="${imageDetailsForm.username}" />
<portlet:resourceURL var="releseLockedPet" id ="releseLockedPet">  </portlet:resourceURL>

<input type="hidden" id="releseLockedPet" name="releseLockedPet" value="${releseLockedPet}"></input>	

<div align="right" style="margin-bottom: 0.5cm" >	
			<c:out value="${imageDetailsForm.username}"/> &nbsp;	 
			<input type="button"   style="font-weight: bold" name="logout" value="Logout" 
			    onclick=logout_home('<c:out value="${imageDetailsForm.username}"/>','${releseLockedPet}');  />	
			
</div>
	
<div id="timeOutId" align="left" style="display: none;width:80%;">
	<p style="color: red; font-weight: bold; font-size: 14pt;">Your session has been ended due to inactivity</p> 
</div>


<div id="car_info_pnl" class="cars_panel x-hidden">

<div class="x-panel-header"><fmt:message
	key="label.styleInfo.header" /></div>
<div class="x-panel-body">
	<div id="image-Upload-Message-Area" class="message-area">
		
	</div>
	<br />
<ul class="car_info"
	style="font-size: 11px; padding: 0 0 10px !important;">
	<c:if test="${not empty imageDetailsForm.styleInfo}">
		<%-- Style Information code starts here   --%>
		<c:forEach var="styleInformation" varStatus="status"
			items="${imageDetailsForm.styleInfo}">

                  
		<input type="hidden" name="lockedPet" id="lockedPet" value="<c:out value="${styleInformation.orinGrouping}" />" />
		<input type="hidden" name="loggedInUser" id="loggedInUser" value="<c:out value="${imageDetailsForm.username}" />" />

			<li class="txt_attr_name" style="width: 30%;"><b><fmt:message
				key="label.styleInfo.orionGrouping" /></b> ${styleInformation.orinGrouping}</li>
			<li class="txt_attr_name" style="width: 25%;"><b><fmt:message
				key="label.styleInfo.deptNumber" /></b> ${styleInformation.deptNo}</li>
			<li class="txt_attr_name" style="width: 25%;"><b><fmt:message
				key="label.styleInfo.vendorName" /></b> ${styleInformation.vendorName}</li>
</ul>
<ul class="car_info"
	style="font-size: 11px; padding: 0 0 10px !important;">
	<li class="ctxt_attr_name" style="width: 30%;"><b><fmt:message
		key="label.styleInfo.styleNumber" /></b> ${styleInformation.styleNo}</li>
	<li class="txt_attr_name" style="width: 25%;"><b><fmt:message
		key="label.styleInfo.class" /></b> ${styleInformation.imageClass}</li>
	<li class="txt_attr_name"><b><fmt:message
		key="label.styleInfo.vendorNumber" /></b> ${styleInformation.vendorNo}</li>
</ul>

<ul class="car_info"
	style="font-size: 11px; padding: 0 0 10px !important;">
	<li class="ctxt_attr_name" style="width: 30%;"><b><fmt:message
		key="label.styleInfo.description" /></b> ${styleInformation.imageDesc}</li>
	<li class="txt_attr_name" style="width: 25%;"><b><fmt:message
		key="label.styleInfo.omniChannelVendor" /></b> ${styleInformation.omniChannelVendor}</li>
	<li class="txt_attr_name"><b><fmt:message
		key="label.styleInfo.vendorProvidedImage" /></b> ${styleInformation.vendorProvidedImage}</li>

</ul>
<ul class="car_info"
	style="font-size: 11px; padding: 0 0 10px !important; margin-left: 15cm;>			
				<li class="txt_attr_name"><b><fmt:message key="label.styleInfo.vendorProvidedSample"/></b> ${styleInformation.vendorProvidedSample}</li>
			</ul>
			</c:forEach>
			</c:if>	
			<input type="button" name="Close" value="Close"   class="closeContentButton"  onclick="javascript:goToWorkListDisplayScreen('<c:out value="${imageDetailsForm.username}"/>','${releseLockedPet}');" style="width: 100px; height: 30px; margin-left:800px"/>
			
			
					
</div>
</div>
<%--Style Info Ends--%>	


<%--Product Details Starts here--%>
	
<div id="prod_detail_pnl" class="cars_panel x-hidden">
	<div class="x-panel-header">
		<fmt:message key="label.productDetails.header"/>
	</div>
	<div class="x-panel-body">
	<div id="prod_detail">
	<c:if test="${not empty imageDetailsForm.imageProductInfo}">		
	<ul class="attrs">
		<c:forEach var="productInformation" varStatus="status" items="${imageDetailsForm.imageProductInfo}">
		<li class="prod_name">		
			<label><b><fmt:message key="label.productDetails.productName"/>:</label>	</b>		
				<textarea  style="margin-left:30px;"  id ="txt_outfitName"  name="vendorStyle.Name:512224" cols="100" rows="5" onkeyup="textCounter();" onblur="textCounter();"  readonly>${productInformation.productName}</textarea>
		</li>	
		<li class="prod_desc">
			<label><b><fmt:message key="label.productDetails.productDescription"/>:</label></b>			
			<textarea id="vendorStyle.Descr:512224"	 name="vendorStyle.Descr:512224" onkeyup="textCounter();" cols="100" rows="5 onblur="textCounter();" readonly>${productInformation.productDescription}</textarea>		
				
			<div class="chars_rem">
				<fmt:message key="label.productDetails.productRemaining"/><span class="chars_rem_val">40</span>
			</div>			
			<br style="clear:both;" />
		</li>
		</c:forEach>		
	</ul>	
	</c:if>
	</div>
</div></div>
<%--Product Details Ends here--%>


<%--Image Management Screen  starts over here  --%>
<jsp:include page="/WEB-INF/pages/jsp/imageManagmentDisplay.jsp"/>
<%-- Image management links ends here --%>


<%--VPI and Sample Image Links starts--%>
<%--Logic to handle user data--%>
<c:if test ="${requestScope.userAttr == 'dca'}">
	<% System.out.println("DCA USer-->>>>");	%>


<div id="img_mgmt_pnl" class="cars_panel x-hidden">
	<div class="x-panel-header">
		<strong><fmt:message key="label.vpisampleImage.header" /></strong>
	</div>
	<div class="x-panel-body">
	<div id="image-operations-Message-Area" class="message-area">
		
	</div>
	<br />
<div class="userButtons">



<form id="vendorImageDetailsFormID" name="vendorImageDetailsFormID">

<input type ="hidden" name="mySelectBox_hidden" id="mySelectBox_hidden" value ="" />
<input type ="hidden" name="sampleReceivedCheckValue" id="sampleReceivedCheckValue" value ="" />
<input type ="hidden" name="silhouetteHiddenValue" id="silhouetteHiddenValue" value ="" />
<input type ="hidden" name="linkStatusHiddenValue" id="linkStatusHiddenValue" value ="" />
<input type ="hidden" name="imageStatusHiddenValue" id="imageStatusHiddenValue" value ="" />
<input type ="hidden" name="datePickHidden" id="datePickHidden" value ="" />

<input type ="hidden" name="saveImageShotTypeId" id="saveImageShotTypeId" value ="" />

<input class="btn uploadButton" id="btnImageUploadAction"  align="center" type="button" name="imageUploadSubmit" value="Upload VPI"  style="margin-left:400px;" />
<input class="btn Approve"  align="center" id="image_approve"  onclick="checkShotTypeOnApprove('${imageApproveAction}');"	type="button" name="imageApprove" value="Approve"  style="margin-left:10px;" />		
<input  class="btn Save Button" id="saveImage" type="button"  onclick="getSaveImageShotTypeAjax('${saveImageShotType}');" name="saveImage" title="Save" width="48" value="  Save  " style="margin-left:48px;" />								

</div>
		<div class="need_images">							
		<div id="vendor_images">
<table id="vImage" cellpadding="0" cellspacing="0">
<thead>
<tr>
<th class="sorted order1"><fmt:message key="label.vpisampleImage.imageID" /></th>
<th><fmt:message key="label.vpisampleImage.imageName" /></th>
<th><fmt:message key="label.vpisampleImage.imageLoc" /></th>
<th><fmt:message key="label.vpisampleImage.imageShotType" /></th>
<th><fmt:message key="label.vpisampleImage.imgrStatus" /></th>
<th>Submit</th>
<th><fmt:message key="label.vpisampleImage.Reject" /></th>
<th>Remove</th>
<!-- Commented as the table is populating dynamically -->
</table>
</form>
				</div>
			</div>	
		
	</div>
</div>

</c:if>
<c:if test ="${requestScope.userAttr eq 'vendor'}">
<div id="img_mgmt_pnl" class="cars_panel x-hidden">
	<div class="x-panel-header">
		<strong><fmt:message key="label.vpisampleImage.header" /></strong>
	</div>
	<div class="x-panel-body">
	<div id="image-operations-Message-Area" class="message-area">
		
	</div>
	<br />	
<div class="userButtons">

<input class="btn uploadButton" id="btnImageUploadAction"  align="center" type="button" name="imageUploadSubmit" value="Upload VPI"  style="margin-left:400px;" />	
<input type ="hidden" id = "image_approve" />
<input type ="hidden" id = "saveImage" />					
</div>		
		<div class="need_images">							
		<div id="vendor_images">
<table id="vImage" cellpadding="0" cellspacing="0">
<thead>
<tr>

<th><fmt:message key="label.vpisampleImage.imageID" /></th>
<th><fmt:message key="label.vpisampleImage.imageName" /></th>
<th><fmt:message key="label.vpisampleImage.imageLoc" /></th>
<th><fmt:message key="label.vpisampleImage.imageShotType" /></th>
<th><fmt:message key="label.vpisampleImage.imgrStatus" /></th>
<th>Submit</th>
<th>Remove</th>
</table>
	</div>
			</div>	
		
	</div>
</div>
</c:if>

<!-- Readonly user block starts -->
<c:if test ="${requestScope.userAttr eq 'readonly'}">
<div id="img_mgmt_pnl" class="cars_panel x-hidden">
	<div class="x-panel-header">
		<strong><fmt:message key="label.vpisampleImage.header" /></strong>
	</div>
	<div class="x-panel-body">	
<div class="userButtons">	
<c:if test = "${requestScope.petStatus ne 'Deactivated'}">
	<input class="btn uploadButton" id="btnImageUploadAction"  align="center" type="button" name="imageUploadSubmit" value="Upload VPI"  style="margin-left:400px;" disabled/>
</c:if>	
<input type ="hidden" id = "image_approve" />
<input type ="hidden" id = "saveImage" />					
</div>		
		<div class="need_images">							
		<div id="vendor_images">
<table id="vImage" cellpadding="0" cellspacing="0">
<thead>
<tr>

<th><fmt:message key="label.vpisampleImage.imageID" /></th>
<th><fmt:message key="label.vpisampleImage.imageName" /></th>
<th><fmt:message key="label.vpisampleImage.imageLoc" /></th>
<th><fmt:message key="label.vpisampleImage.imageShotType" /></th>
<th><fmt:message key="label.vpisampleImage.imgrStatus" /></th>
<!--<th>Submit</th>
<th>Remove</th>-->
</table>
	</div>
			</div>	
		
	</div>
</div>
</c:if>
<!-- Readonly user block ends -->





<%--Contact Information Starts--%>
<div id="contact_Information" class="cars_panel x-hidden collapsed">
	<div class="x-panel-header">
		<fmt:message key ="label.contactInformation.header" />
	</div>
	<div class="x-panel-body">
	<table id="vendor_Imformation" cellpadding="0" cellspacing="0">
	<!--<tr>
	<td>
	<li class="car_id">
			<strong><fmt:message key ="label.contactInformation.contact1" /></strong>		
		</li>
	</td>
	<td>
		<li class="car_id" >
			<strong style="margin-left:300px"><fmt:message key ="label.contactInformation.contact2"  /></strong>		
		</li>
		</td>
	</tr>-->
	<tr>
	 <!--<td>-->
	 <c:if test="${not empty imageDetailsForm.contactInfoList }">
	 	<c:forEach var="conDetails" items="${imageDetailsForm.contactInfoList}" varStatus="status">
		<td>
	 	<c:if test = "${conDetails.contactTitle == 'Contact 1'}">
		<li class="car_id">
			<strong><fmt:message key ="label.contactInformation.contact1" /></strong>		
		</li>		
		<li class="car_id">
			<strong><fmt:message key ="label.contactInformation.vendorContactName" /></strong> 
			${conDetails.vendorContactName} 
		</li>		
		<li class="dept">
			<strong><fmt:message key ="label.contactInformation.vendorContactEmail" /></strong>	
			${conDetails.vendorContactEmail}
		</li>		
		<li class="dept">
			<strong><fmt:message key ="label.contactInformation.vendorContactTelephone" /></strong>	
			${conDetails.vendorContactTelephone}
		</li>
			</c:if>		
		</td>
	<td>
		<c:if test = "${conDetails.contactTitle == 'Contact 2'}">
		<li class="car_id" >
			<strong><fmt:message key ="label.contactInformation.contact2"  /></strong>		
		</li>
		<li class="car_id">
			<strong><fmt:message key ="label.contactInformation.vendorContactName" /></strong> 
			${conDetails.vendorContactName}
		</li>
		<li class="dept">
			<strong><fmt:message key ="label.contactInformation.vendorContactEmail" /></strong>	
			${conDetails.vendorContactEmail}
		</li>
		<li class="dept">
			<strong><fmt:message key ="label.contactInformation.vendorContactTelephone" /></strong>	
			${conDetails.vendorContactTelephone}
		</li>
			</c:if>
		</td>
		
		<td>
		<c:if test = "${conDetails.contactTitle == 'Contact 3'}">
		<li class="car_id" >
			<strong>Contact 3:</strong>		
		</li>
		<li class="car_id">
			<strong><fmt:message key ="label.contactInformation.vendorContactName" /></strong> 
			${conDetails.vendorContactName}
		</li>
		<li class="dept">
			<strong><fmt:message key ="label.contactInformation.vendorContactEmail" /></strong>	
			${conDetails.vendorContactEmail}
		</li>
		<li class="dept">
			<strong><fmt:message key ="label.contactInformation.vendorContactTelephone" /></strong>	
			${conDetails.vendorContactTelephone}
		</li>
			</c:if>
		</td>
		
		<td>
		<c:if test = "${conDetails.contactTitle == 'Contact 4'}">
		<li class="car_id" >
			<strong>Contact 4:</strong>		
		</li>
		<li class="car_id">
			<strong><fmt:message key ="label.contactInformation.vendorContactName" /></strong> 
			${conDetails.vendorContactName}
		</li>
		<li class="dept">
			<strong><fmt:message key ="label.contactInformation.vendorContactEmail" /></strong>	
			${conDetails.vendorContactEmail}
		</li>
		<li class="dept">
			<strong><fmt:message key ="label.contactInformation.vendorContactTelephone" /></strong>	
			${conDetails.vendorContactTelephone}
		</li>
			</c:if>
		</td>
		
		<td>
		<c:if test = "${conDetails.contactTitle == 'Contact 5'}">
		<li class="car_id" >
			<strong>Contact 5:</strong>		
		</li>
		<li class="car_id">
			<strong><fmt:message key ="label.contactInformation.vendorContactName" /></strong> 
			${conDetails.vendorContactName}
		</li>
		<li class="dept">
			<strong><fmt:message key ="label.contactInformation.vendorContactEmail" /></strong>	
			${conDetails.vendorContactEmail}
		</li>
		<li class="dept">
			<strong><fmt:message key ="label.contactInformation.vendorContactTelephone" /></strong>	
			${conDetails.vendorContactTelephone}
		</li>
			</c:if>
		</td>		
		</c:forEach>
		</c:if>
		</tr>
		
</table>		

</div></div>
<%--Contact Imformation Ends--%>

<%--PEP History Block Starts Here--%>
<div id="car_history_details_pnl" class="cars_panel x-hidden collapsed">
	<div class="x-panel-header">
		<fmt:message key ="label.pepHistory.header" />
	</div>
	<div class="x-panel-body" style="font-size: 11px;font-family: tahoma,arial,helvetica,sans-serif;">			
	
	<table cellspacing="1" cellpadding="1" border="1" class="table tree2 table-bordered table-striped table-condensed" id="historyID">
	<thead>	
	<tr>										
													<th>
													 PET
													 </th>
													 
													 <th>
														Date PET Created
													 </th>
													 <th>
														PET Created By 
													 </th>
													 
													 <th>
														Image Last Updated Date 
													 </th>
													 <th>
														Image Last Updated By 
													 </th>
													 <th>
														Image Last Updated Status at Style
													 </th>
													 <!--<th>
														Action Taken
													 </th>-->
													
													
													
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
<!--Contact Imformation Ends-->
</form><!-- Main form ends here -->

<%--Upload VPI POPUP Image Starts--%>
<div id="overlay_UploadImage" class="web_dialog_overlay"></div>    
<div id="dialog_UploadImage" class="web_dialog_imageUploadPopUp" style="height: 210px;">
 <div id="content">          

	<!-- <div class="x-panel-header">
	Upload Image
	</div> -->
	<div class="x-panel-body;border: 0px solid #99bbe8;">
	<form:form id="imageuploadformid" modelAttribute="fdForm" method="post" action="${imageUploadAction}" enctype="multipart/form-data">
	        <input type="hidden" name="selectedColorOrin" id="selectedColorOrin" value=""/>	
	        <input type="hidden" name="supplierIdVPI" id="supplierIdVPI" value=""/>	
			<input type="hidden" name="vendorStyle#" id="vendorStyle#" value=""/>
			<input type="hidden" name="colorCode" id="colorCode" value=""/>	
			
			<div id="errorDIV" style="display:none;"></div>
	              
	                </br>
					                </br>
		<ul>
			<li>				
				<label style="margin-left:53px;height: 16px;">Image Location *:</label>
				

						<select id="imageLocationButton" name="imageLocation" onchange="SelectImageLocation(this.value);" style="height: 18px;" >														
							<option value="BML" selected>Browse My Locations</option>      							 			
							<!--<option value="FTP"  >FTP </option>-->																											
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
			
			<div id="ftpDiv_1" style="float:;display:none;" >
				<li>	 
						<label class="ftpLabel" >Image Path to FTP (URL) *:</label> 
						<input type="text" name="ftpUrl1"  align="" path="ftpUrl" id="ftpUrl1"/>
						<span class="suggestion">Example: ftp.xyz.com </span> 
				</li>
				<li>
						 <label class="ftpLabel" style="margin-left:110px;"  >Folder :</label> 
						<input type="text" name="ftpPath" id="ftpPath" path="ftpPath" />
						 <span class="suggestion">Example: /myPictures/belk/ </span>
				</li>	 
				<li>
						 <label class="ftpLabel" style="margin-left:67px;" >Image Name *:</label> 
						<input type="text" name="ftpFileName" id="ftpFileName" path="ftpFileName"  />
						 <span class="suggestion" > Example: shirt.jpg </span> 
				</li>	
				<li>
						<label class="ftpLabel" style="margin-left:81px;" >Username *:</label> 
						<input  type="text" id="ftpUserId" name="ftpUserId" path="ftpUserId" />
						<span class="suggestion">Example: FTP Username</span>
				</li>
				<li>
						 <label class="ftpLabel" style="margin-left:83px;" >Password *:</label>
						<input type="text" path="ftpPassword" id="ftpPassword1" name="ftpPassword1"/>
						 <span class="suggestion">Example: FTP Password</span>
				</li>	
			</div>		
		
				<li>
					
				</li>
				</br>
				</br>
		<li class="buttons" style="float:right;">
		<input class="btn-new ui-button ui-corner-all ui-widget" type="button" name="imageSave1" id="imageSave1" value="Add Image" style="margin-right: 10px;" onclick="validateFields('imageuploadformid');"/>
		<input class="btn-new ui-button ui-corner-all ui-widget"   id="btnImageUploadPopUpClose" type="button" name="Cancel" value="Cancel" style="margin-right: 4px;" onclick="clearErrorMessage();"/> 		
		
	</li>
</ul>
</form:form>
</div>
</div>
</div>
<%--Upload VPI Image POPUP Ends--%>

<%--File Upload Render Starts--%>
<div id="overlay_Upload" class="web_dialog_overlay"></div>
<div id="dialog_uploadSuccess" class="web_dialog_imageUploadPopUp" style="height: 140px;">
 <div id="content">          

	<div class="x-panel-header">
	Upload Image Status
	</div>
	<div class="x-panel-body;border: 0px solid #99bbe8;">
	
	                </br>
					                </br>
		<ul>
			<li>				
				<label style="margin-left:53px;height: 16px;" id="labelUploadImageId" data-imgname="${imageName}"> &nbsp;</label>
			</li>
				
			</br>
						
		<input class="btn"   id="successPopUpClose" type="button"
		onclick='$("#overlay_Upload").hide();$("#dialog_uploadSuccess").hide();' name="Close" value="Close" style="float: right;margin-right:10px;" /> 		
		

	</li>
</ul>

</div>
</div>
</div>

<div id="img-dialog-general">
	<label style="margin-left:53px;height: 16px;" id="img-dialog-general-text">Image ${imageName} successfully uploaded. </label>
</div>

<!-- Image Approve Confirmation Popup Starts-->
<div id="dialog_submitApprove" class="web_dialog_imageUploadPopUp">
	<div id="content">
		<!-- <div class="x-panel-header">
			Approve Image Confirmation	
		</div> -->
		<input type="hidden" id="imgHiddenId" name="imgHiddenId" value=""></input>
		<input type="hidden" id="imgRowId" name="imgRowId" value=""></input>
		<input type="hidden" id="imgNameHiddenId" name="imgNameHiddenId" value=""></input>
		
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<div class="ui-widget">
							<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
								<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								<strong>Are you sure you want to Approve?</strong></p>
							</div>
						</div>
			
			<ul>
				<!-- <li style="width:100%;float:left;margin-top:10px;">				
					<label style="margin-left:53px;height: 16px;">Are you sure you want to Approve? </label>
				</li> -->			
			</br>
				<li class="buttons" style="float:right;">
					<input class="btn-new ui-button ui-corner-all ui-widget"   id="approveConfirmPopUpOkId" type="button" onclick="submitApproveAction('${imageApproveAction}');dialogApproveHideonOk();" name="Ok" value="Ok" />			
					
					<input class="btn-new ui-button ui-corner-all ui-widget"   id="approvePopUpClose" type="button" onclick="dialogApproveHideonOk();" name="Cancel" value="Cancel" /> &nbsp;&nbsp;&nbsp;&nbsp;
				</li>				
			</ul>

		</div>
	</div>
</div>
<!-- Image Approve Confirmation Popup Ends-->

<!-- At least one shot type 'A' is required starts. -->
<div id="dialog_checkShotTypeA" class="web_dialog_imageUploadPopUp" style="height: 140px; width:350px;">
	<div id="content">
		<!-- <div class="x-panel-header" id="checkShotTypeAId">
			Approve Image Confirmation			
		</div> -->
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<div class="ui-widget">
							<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
								<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								<strong>At least one of the uploaded image should be Shot Type 'A'</strong></p>
							</div>
						</div>
			<ul>
				<!-- <li style="width:100%;float:left;margin-top:10px;">				
					<label style="margin-left:20px;height: 16px;" id="saveImageAtLeastShotTypeAId">At least one of the uploaded image should be Shot Type 'A'</label> -->
				</li>				
			</br>
				<li class="buttons" style="float:right;">
					<input class="btn-new ui-button ui-corner-all ui-widget" id="approvePopUpCloseId" type="button" onclick="jq('#dialog_checkShotTypeA').dialog('close');" name="Ok" value="Ok" /> &nbsp;&nbsp;&nbsp;&nbsp;				
				</li>				
			</ul>

		</div>
	</div>
</div>
<!-- At least one shot type 'A' is required ends. -->


<!-- ShotType Check Approve Starts -->
<div id="dialog_submitApproveCheck" class="web_dialog_imageUploadPopUp">
	<div id="content">
		<!-- <div class="x-panel-header" id="saveApprovePopID">
			Approve Image Confirmation			
		</div> -->
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<div class="ui-widget">
							<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
								<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								<strong>Please save unique ShotType for all images before Approve</strong></p>
							</div>
						</div>
			<ul>
				<!-- <li style="width:100%;float:left;margin-top:10px;">				
					<label style="margin-left:20px;height: 16px;" id="saveApprovePopLabelID">Please save unique ShotType for all images before Approve</label>
				</li> -->				
			</br>
				<li class="buttons" style="float:right;">
					<input class="btn-new ui-button ui-corner-all ui-widget" id="approvePopUpClose" type="button" onclick="jq('#dialog_submitApproveCheck').dialog('close');" name="Ok" value="Ok" /> &nbsp;&nbsp;&nbsp;&nbsp;				
				</li>				
			</ul>

		</div>
	</div>
</div>
<!-- ShotType Check Approve Ends -->

<!--Unique ShotType POPUP Alert Starts -->
<div id="dialog_uniqueShotTypeCheckId" class="web_dialog_imageUploadPopUp" style="height: 140px; width:350px;">
	<div id="content">
		<!-- <div class="x-panel-header">
			Save ShotType Status			
		</div> -->
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<div class="ui-widget">
							<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
								<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								<label id="saveShotTypeID"></label>
								<label id="saveShotTypeIDSuccess"></label></p>
							</div>
						</div>
			<ul>
				<!-- <li style="width:100%;float:left;margin-top:10px;">				
					<label style="margin-left:-17px;height: 16px;" id="saveShotTypeID"></label>
					<label style="margin-left:-17px;height: 16px;" id="saveShotTypeIDSuccess"></label>
				</li>	-->			
			</br>
				<li class="buttons" style="float:right;">
					<input class="btn-new ui-button ui-corner-all ui-widget" id="uniqueShotTypeCheckClose" type="button" onclick="jq('#dialog_uniqueShotTypeCheckId').dialog('close');" name="Ok" value="Ok" style="float: right;margin-left:10px;margin-right:50%;" /> &nbsp;&nbsp;&nbsp;&nbsp;				
				</li>				
			</ul>

		</div>
	</div>
</div>
<!--Unique ShotType POPUP Alert Ends-->

<!--Image Loading message starts-->
	<div id="overlay_imageLoading" style="display:none;">
		<img src="<%=request.getContextPath()%>/img/loading.gif" height="100px;"height="100px;" />
	</div>
<!--Image Loading message ends -->
<%--File Upload Render Ends--%>
</div>
</div>
</div>
</div>
</body>
<script>
//Logic for fileUpload rendering using popup

var uploadStatus = '${uploadSuccess}';

document.getElementById('uploadStatHidden').value = uploadStatus;
var selectedColorOrin1 = '';

$(function() {
jq('#dialog_UploadImage').dialog({
	//modal: true,
	autoOpen: false,
	resizable: true,
	dialogClass: "dlg-custom",
	title: 'Upload Image',
	minWidth: 520,
	minHeight: 150,
	//show: { effect: "fade", duration: 800 },
});
jq('#dialog_submitRemove').dialog({
	modal: true,
	autoOpen: false,
	resizable: true,
	dialogClass: "dlg-custom",
	title: 'Remove Image Confirmation',
	minWidth: 350,
	minHeight: 140,
	//show: { effect: "fade", duration: 800 },
});
jq('#dialog_submitApproveCheck').dialog({
	modal: true,
	autoOpen: false,
	resizable: true,
	dialogClass: "dlg-custom",
	title: 'Save ShotType Status',
	minWidth: 350,
	minHeight: 140,
	//show: { effect: "fade", duration: 800 },
});
jq('#dialog_uniqueShotTypeCheckId').dialog({
	modal: true,
	autoOpen: false,
	resizable: true,
	dialogClass: "dlg-custom",
	title: 'Save ShotType Status',
	minWidth: 350,
	minHeight: 140,
	//show: { effect: "fade", duration: 800 },
});
jq('#dialog_removeFailed').dialog({
	modal: true,
	autoOpen: false,
	resizable: true,
	dialogClass: "dlg-custom",
	title: 'Remove Image Status',
	minWidth: 350,
	minHeight: 140,
	//show: { effect: "fade", duration: 800 },
});
jq('#dialog_checkShotTypeA').dialog({
	modal: true,
	autoOpen: false,
	resizable: true,
	dialogClass: "dlg-custom",
	title: 'Approve Image Confirmation',
	minWidth: 350,
	minHeight: 140,
	//show: { effect: "fade", duration: 800 },
});
jq('#dialog_submitApprove').dialog({
	modal: true,
	autoOpen: false,
	resizable: true,
	dialogClass: "dlg-custom",
	title: 'Approve Image Confirmation',
	minWidth: 350,
	minHeight: 140,
	//show: { effect: "fade", duration: 800 },
});

jq('#img-dialog-general').dialog({
	modal: true,
	autoOpen: false,
	resizable: true,
	dialogClass: "dlg-custom",
	title: 'Upload Image Status',
	minWidth: 520,
	minHeight: 150,
	//show: { effect: "fade", duration: 800 },
	buttons: [
		{
		  text: "Close",
		  icons: {
			//primary: "ui-icon-heart"
		  },
		  click: function() {
			jq( this ).dialog( "close" );
		  }
		}
	],
	open: function(event, ui){
		//console.log(jq(this).dialog('option', 'imgDlgType'));
		var imgDlgType = jq(this).dialog('option', 'imgDlgType') || 'failed';
		
		if(imgDlgType == 'failed'){
			jq('#img-dialog-general-text').text('Image not uploaded.');
		}
	},
});

jq('#img-dialog-generic').dialog({
	modal: true,
	autoOpen: false,
	resizable: true,
	dialogClass: "dlg-custom",
	title: 'Upload Image Status',
	minWidth: 520,
	minHeight: 150,
	//show: { effect: "fade", duration: 800 },
	buttons: [
		{
		  text: "Close",
		  icons: {
			//primary: "ui-icon-heart"
		  },
		  click: function() {
			jq( this ).dialog( "close" );
		  }
		}
	],
	open: function(event, ui){
		//console.log(jq(this).dialog('option', 'imgDlgType'));
		var imageID = jq(this).dialog('option', 'imageID') || '';
		var imgActionType = jq(this).dialog('option', 'imgActionType') || '';
		
		switch(imgActionType){
			case 'rejectSuccess':
				jq(this).dialog("option", "title", 'Reject Image Status');
				jq('#img-dialog-message-area').removeClass('ui-state-error').addClass('ui-state-highlight');
				jq('#img-dialog-message-icon').removeClass('ui-icon-alert').addClass('ui-icon-info');
				jq('#img-dialog-generic-text').text('Image ' + imageID + ' successfully rejected.');
				break;
			case 'rejectError':
				jq(this).dialog("option", "title", 'Reject Image Status');
				jq('#img-dialog-message-area').addClass('ui-state-error').removeClass('ui-state-highlight');
				jq('#img-dialog-message-icon').addClass('ui-icon-alert').removeClass('ui-icon-info');
				jq('#img-dialog-generic-text').text('Image ' + imageID + ' not rejected.');
				break;
			case 'submitSuccess':
				jq(this).dialog("option", "title", 'Submit Image Status');
				jq('#img-dialog-message-area').removeClass('ui-state-error').addClass('ui-state-highlight');
				jq('#img-dialog-message-icon').removeClass('ui-icon-alert').addClass('ui-icon-info');
				jq('#img-dialog-generic-text').text('Image ' + imageID + ' successfully submitted.');
				break;
			case 'submitError':
				jq(this).dialog("option", "title", 'Submit Image Status');
				jq('#img-dialog-message-area').addClass('ui-state-error').removeClass('ui-state-highlight');
				jq('#img-dialog-message-icon').addClass('ui-icon-alert').removeClass('ui-icon-info');
				jq('#img-dialog-generic-text').text('Image ' + imageID + ' not submitted.');
				break;
		}
	},
});
//ColorCode Population
var vendorColorCode = '${selectedColorCode}';
populateColorCode(vendorColorCode);
var table = document.getElementById("vImage");
	var rowCount = table.rows.length-1;
	var imgName = $('#labelUploadImageId').data('imgname') || '';
	//selectedColorOrin1= '${selectedColorOrin}';	
console.log('--uploadStatus***--' + uploadStatus);	
  if(uploadStatus=='Y'){
	//jq('#img-dialog-general').dialog('option', 'imgDlgType', 'success');
	//jq('#img-dialog-general').dialog('open');
	showImageActionMessage('uploadSuccess', '${imageName}'); //new message instead of dialog for as per requirement 931
	selectedColorOrin1= '${selectedColorOrin}';
  }else if(uploadStatus=='N'){		
		//jq('#img-dialog-general').dialog('option', 'imgDlgType', 'failed');
		//jq('#img-dialog-general').dialog('open');
		showImageActionMessage('uploadError', '${imageName}'); //new message instead of dialog for as per requirement 931
		selectedColorOrin1= '${selectedColorOrin}';
	}	
if(rowCount >= 10 ){
	document.getElementById('btnImageUploadAction').disabled=true;
}else{
if(document.getElementById('hiddenImageStatus').value == "Completed" || document.getElementById('hiddenImageStatus').value == "Approved"){	
	document.getElementById('btnImageUploadAction').disabled = true ;	
	document.getElementById('saveImage').disabled = true ;
	document.getElementById('image_approve').disabled=true;
}else{		
	document.getElementById('btnImageUploadAction').disabled = false ;
	document.getElementById('saveImage').disabled = false ;
	document.getElementById('image_approve').disabled=false;
}
	if(flagHidden == false){		
		document.getElementById('image_approve').disabled = false ;	
	}
	else if(flagHidden == true){		
		document.getElementById('image_approve').disabled = true ;
	}	
}
setUploadVPILink($("#ajaxaction").val(),selectedColorOrin1,$("#removeImageUrl").val());
if((uploadStatus=='Y' || uploadStatus=='N') && $("#selectedOrinVPI").val().trim().length != 0 ){	
	expandCollapse("petTable",selectedColorOrin1);	
}
trClick();
if(uploadStatus=='Y' || uploadStatus=='N'){	
	scrollToView('vImage','vImage');
}
});

function dialogHide(){
	$("#overlay_Upload").hide();
	$("#dialog_uploadSuccess").hide();
}

function expandCollapse(tableId,selectedColorOrin1){
	var tableElm = $("#"+tableId);	
	$(tableElm).find("tr").each(function() {		
	if($(this).find("#" +selectedColorOrin1).size() > 0){		
		$(this).css('background-color','#DCE2EC');
	}
		$(this).removeClass("treegrid-collapsed");
		$(this).addClass("treegrid-expanded");
		$(this).show();
	});	
	$(tableElm).find(".icon-plus-sign").each(function(){		
	if($(this).hasClass("treegrid-expander")){			
		$(this).removeClass("icon-plus-sign");
		$(this).addClass("icon-minus-sign");
	}		
});
}

$(document).keydown(function(e) { if (e.keyCode == 8) e.preventDefault(); });

var timeOutvarImage = null;
timeOutvarImage = setTimeout(redirectSessionTimedOutImage, 1800000);
function timeOutImagePage()
{
                timeOutvarImage = setTimeout(redirectSessionTimedOutImage, 1800000);
}

document.onclick = clickListenerImage;
function clickListenerImage(e){
	clearTimeout(timeOutvar);
	clearTimeout(timeOutvarImage);
	timeOutFlag = 'no';
	timeOutPage();
	timeOutImagePage();
}

function redirectSessionTimedOutImage(){

confirmationMessage = false;
var loggedInUser= $("#loggedInUser").val();
var releseLockedPetURL = $("#releseLockedPet").val();
releseLockedPet(loggedInUser,releseLockedPetURL);
if(loggedInUser.indexOf('@') === -1) 
{
                window.location = "/wps/portal/home/InternalLogin";
} else {
                window.location = "/wps/portal/home/ExternalVendorLogin";
                }
}

function showImageActionMessage(eventType, imageId){
	imageId = (imageId === undefined || imageId == '') ? '' : imageId;
	switch (eventType){
		case 'uploadSuccess':
			var dlgContent = buildMessage('Image ' + imageId + ' successfully uploaded.', 'success');
			$('#image-Upload-Message-Area').html(dlgContent).fadeIn('fast');
			//cleanupMessage($('#image-Upload-Message-Area'));
			break;
		case 'uploadError':
			var dlgContent = buildMessage('Image ' + imageId + ' not uploaded.', 'error');
			$('#image-Upload-Message-Area').html(dlgContent).fadeIn('fast');
			//cleanupMessage($('#image-Upload-Message-Area'));
			break;
		case 'submitSuccess':
			var dlgContent = buildMessage('Image ' + imageId + ' successfully submitted.', 'success');
			$('#image-operations-Message-Area').html(dlgContent).fadeIn('fast');
			//cleanupMessage($('#image-Upload-Message-Area'));
			break;
		case 'submitError':
			var dlgContent = buildMessage('Image ' + imageId + ' not submitted.', 'error');
			$('#image-operations-Message-Area').html(dlgContent).fadeIn('fast');
			//cleanupMessage($('#image-Upload-Message-Area'));
			break;
		case 'rejectSuccess':
			var dlgContent = buildMessage('Image ' + imageId + ' successfully rejected.', 'success');
			$('#image-operations-Message-Area').html(dlgContent).fadeIn('fast');
			//cleanupMessage($('#image-Upload-Message-Area'));
			break;
		case 'rejectError':
			var dlgContent = buildMessage('Image ' + imageId + ' not rejected.', 'error');
			$('#image-operations-Message-Area').html(dlgContent).fadeIn('fast');
			//cleanupMessage($('#image-Upload-Message-Area'));
			break;
		case 'saveSuccess':
			var dlgContent = buildMessage('ShotType saved successfully', 'success');
			$('#image-operations-Message-Area').html(dlgContent).fadeIn('fast');
			//cleanupMessage($('#image-operations-Message-Area'));
			break;
		case 'saveError':
			var dlgContent = buildMessage('ShotType not saved successfully', 'error');
			$('#image-operations-Message-Area').html(dlgContent).fadeIn('fast');
			//cleanupMessage($('#image-Upload-Message-Area'));
			break;
	}
}
function buildMessage(msg, dlgType){
	var highlightClass = dlgType == 'error' ? 'ui-state-error ui-custom-message-styling-error' : 'ui-state-highlight ui-custom-message-styling-info';
	
	return '<div class="ui-widget">'
		+ '<div class="ui-corner-all ' +  highlightClass + '">'
		+ 	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>'
		+		'<strong>' + msg + '</strong>'
		+ 	'</p>'
		+ '</div>'
		+ '</div>';
}
function cleanupMessage(jqAreaObj, timeoutMS){
	timeoutMS = timeoutMS === undefined ? 8000 : timeoutMS;
	
	setTimeout(function(){
		jqAreaObj.fadeOut('slow');		
	}, timeoutMS);
}
</script>