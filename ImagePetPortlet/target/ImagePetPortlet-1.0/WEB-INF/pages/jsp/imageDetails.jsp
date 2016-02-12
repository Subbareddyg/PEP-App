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
<script src="<%=request.getContextPath()%>/js/belk.cars.editcar.js"></script>
<!-- <script src="jquery.min.js"></script>-->
<script src="http://yandex.st/highlightjs/7.3/highlight.min.js"></script>


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
</style>

<script type="text/javascript">

var timeOutFlag = 'no';
var inputChanged = true;
document.getElementById('removeFlagOff').value = inputChanged;

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

    $("#datepicker1").datepicker({
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
document.onclick = clickListener;
function clickListener(e){
	clearTimeout(timeOutvar);
	timeOutPage();
}

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

	//alert('timeout happend' +timeOutFlag);
	return timeOutFlag;
	//$("#timeOutId").show();
	
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
	}
	
	
}
		
function getSaveImageShotTypeAjax(url){	
	var selectedOrin = $("#selectedOrinVPI").val();	
	var count1 = 0;
	var count2 = 0;
	var duplicateShotType = 'N';
	$("#vImage tr:gt(0)").each(function(i){		
		var shotTypeSelected = $(this).find("#shotType :selected").val();				
				count2 = 0;
				$("#vImage tr:gt(0)").each(function(i){					
					var shotTypeSelected1 = $(this).find("#shotType :selected").val();					
					if(shotTypeSelected == shotTypeSelected1 && count1 != count2){
						duplicateShotType = 'Y';	
					}					
					if(shotTypeSelected == '---Select---'){
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
							$("#saveShotTypeID").css("margin-left",'74px');
							document.getElementById('saveShotTypeID').innerHTML= 'ShotType Saved Successfully';
							$("#overlay_Upload").show();
							$("#dialog_uniqueShotTypeCheckId").show();
						}
						
					},
					error:function (xhr, ajaxOptions, thrownError){
						var error1 = $.parseJSON(xhr.responseText);
						console.log("Severe Service Error::" + thrownError + "Status Code::" + xhr.status +"error::"+ error1);
				}
			});
	
	}
	
	else{		
		$("#overlay_Upload").show();
		$("#dialog_uniqueShotTypeCheckId").show();
		return false;
	}
	setTimeout(function(){setUploadVPILink($("#ajaxaction").val(),document.getElementById("selectedColorOrinNum").value,$("#removeImageUrl").val());trClick();scrollToView('vImage','vImage');},2500);	
}

//UI VALIDATIONS

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
				$("#overlay_imageLoading").show();
				setTimeout(function(){document.getElementById(formId).submit();},3000);				
			}
		}//End imageLocation check for FTP
	else{
		if(imageLocation ==0){		
			var fileType = document.getElementById('fileData').value;
			var fileExtension = fileType.lastIndexOf('.');
			var ext = fileType.substring(fileExtension+1);
			if(fileType.length > 0){
				if(ext == 'jpeg' || ext == 'jpg' || ext =='psd'|| ext =='tiff'|| ext =='eps'){
					document.getElementById('errorDIV').innerHTML = "";
					$("#overlay_Upload").hide();
					$("#dialog_UploadImage").hide();
					$("#overlay_imageLoading").show();					
					setTimeout(function(){document.getElementById(formId).submit();},3000);
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
}
//UI VLIDATION ENDS

function submitApproveAction(url){
 var selectedColorOrinNum = $("#selectedColorOrinNum").val();	
			
		$.ajax({				
				type: 'POST',
				url: url,
				data: { approveOrRejectOrinNum:selectedColorOrinNum },
				success: function(xhr,response,textStatus){					
					var completionStatusId = selectedColorOrinNum+'_statusId';					
					document.getElementById(completionStatusId).innerHTML = 'Completed';
					document.getElementById('hiddenImageStatus').value = "Completed";
					if(document.getElementById('hiddenImageStatus').value == "Completed" || document.getElementById('hiddenImageStatus').value == "Approved"){
						document.getElementById('image_approve').disabled = true ;
						document.getElementById('btnImageUploadAction').disabled = true ;					
						document.getElementById('saveImage').disabled = true ;
					}
					
				},
				cache: false,                
				error: function(){
					
				}
			});
		
			setTimeout(function(){setUploadVPILink($("#ajaxaction").val(),document.getElementById("selectedColorOrinNum").value,$("#removeImageUrl").val());trClick();scrollToView('vImage','vImage');},2000);			
}

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
	$("#overlay_Upload").hide();
	$("#dialog_submitApprove").hide();
}


function dialogApproveShow(){		
	$("#overlay_Upload").show();
	$("#dialog_submitApprove").show();	
}

function disableAllButtons(){	
	if(document.getElementById('hiddenImageStatus').value == 'Completed' || document.getElementById('hiddenImageStatus').value == 'Approved'){		
		document.getElementById('btnImageUploadAction').disabled = true;
		document.getElementById('saveImage').disabled = true ;
		document.getElementById('image_approve').disabled = true ;
	}
}

function checkShotTypeOnApprove(){	
	var shotTypeNull = '';
	var passImgid = '';	
	var isCheckShotType= 'false';
	//alert('2');
	$("#overlay_Upload").hide();
	$("#dialog_submitApproveCheck").hide();
	$("#overlay_Upload").hide();
	$("#dialog_submitApprove").hide();	
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
			$("#overlay_Upload").hide();
			$("#dialog_submitApproveCheck").hide();
			dialogApproveShow();
		}	
		else{
			 //alert('23');
			 $("#overlay_Upload").show();
			 $("#dialog_checkShotTypeA").show();
		}	
				
	}else if (shotTypeNull == 'Y'){	
		/*var spanCheckApproveId = document.getElementById('checkShotTypeSpanId');		
		if ('textContent' in spanCheckApproveId){
					spanCheckApproveId.textContent = '';
					spanCheckApproveId.textContent = passImgid;
			}else{
					spanCheckApproveId.innerText = '';
					spanCheckApproveId.innerText = passImgid;
			}*/		
		$("#overlay_Upload").show();
		$("#dialog_submitApproveCheck").show();		
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



var myEvent = window.attachEvent || window.addEventListener;
var chkevent = window.attachEvent ? 'onbeforeunload' : 'beforeunload'; /// make IE7, IE8 compitable

           myEvent(chkevent, function(e) { // For >=IE7, Chrome, Firefox
           if(inputChanged){
           var confirmationMessage = '';  // a space
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

<ul class="car_info"
	style="font-size: 11px; padding: 0 0 10px !important;">
	<c:if test="${not empty imageDetailsForm.styleInfo}">
		<%-- Style Information code starts here   --%>
		<c:forEach var="styleInformation" varStatus="status"
			items="${imageDetailsForm.styleInfo}">

                  
		<input type="hidden" name="lockedPet" id="lockedPet" value="<c:out value="${styleInformation.orinGrouping}" />" />
		<input type="hidden" name="loggedInUser" id="loggedInUser" value="<c:out value="${imageDetailsForm.username}" />" />

			<li class="txt_attr_name" style="width: 30%;"><b><fmt:message
				key="label.styleInfo.orionGrouping" /></b>${styleInformation.orinGrouping}</li>
			<li class="txt_attr_name" style="width: 25%;"><b><fmt:message
				key="label.styleInfo.deptNumber" /></b> ${styleInformation.deptNo}</li>
			<li class="txt_attr_name" style="width: 25%;"><b><fmt:message
				key="label.styleInfo.vendorName" /></b>${styleInformation.vendorName}</li>
</ul>
<ul class="car_info"
	style="font-size: 11px; padding: 0 0 10px !important;">
	<li class="ctxt_attr_name" style="width: 30%;"><b><fmt:message
		key="label.styleInfo.styleNumber" /></b>${styleInformation.styleNo}</li>
	<li class="txt_attr_name" style="width: 25%;"><b><fmt:message
		key="label.styleInfo.class" /></b>${styleInformation.imageClass}</li>
	<li class="txt_attr_name"><b><fmt:message
		key="label.styleInfo.vendorNumber" /></b>${styleInformation.vendorNo}</li>
</ul>

<ul class="car_info"
	style="font-size: 11px; padding: 0 0 10px !important;">
	<li class="ctxt_attr_name" style="width: 30%;"><b><fmt:message
		key="label.styleInfo.description" /></b>${styleInformation.imageDesc}</li>
	<li class="txt_attr_name" style="width: 25%;"><b><fmt:message
		key="label.styleInfo.omniChannelVendor" /></b>${styleInformation.omniChannelVendor}</li>
	<li class="txt_attr_name"><b><fmt:message
		key="label.styleInfo.vendorProvidedImage" /></b>${styleInformation.vendorProvidedImage}</li>

</ul>
<ul class="car_info"
	style="font-size: 11px; padding: 0 0 10px !important; margin-left: 15cm;>			
				<li class="txt_attr_name"><b><fmt:message key="label.styleInfo.vendorProvidedSample"/></b>${styleInformation.vendorProvidedSample}</li>
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
<div class="userButtons">



<form id="vendorImageDetailsFormID" name="vendorImageDetailsFormID">

<input type ="hidden" name="mySelectBox_hidden" id="mySelectBox_hidden" value ="" />
<input type ="hidden" name="sampleReceivedCheckValue" id="sampleReceivedCheckValue" value ="" />
<input type ="hidden" name="silhouetteHiddenValue" id="silhouetteHiddenValue" value ="" />
<input type ="hidden" name="linkStatusHiddenValue" id="linkStatusHiddenValue" value ="" />
<input type ="hidden" name="imageStatusHiddenValue" id="imageStatusHiddenValue" value ="" />
<input type ="hidden" name="datePickHidden" id="datePickHidden" value ="" />


<input class="btn uploadButton" id="btnImageUploadAction"  align="center" type="button" name="imageUploadSubmit" value="Upload VPI"  style="margin-left:400px;" />
<input class="btn Approve"  align="center" id="image_approve"  onclick="checkShotTypeOnApprove();"	type="button" name="imageApprove" value="Approve"  style="margin-left:10px;" />		
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
<!-- Readonly user blocj ends -->

<%--VPI and Sample Image Links ends--%>

<%--Vendor Information Starts--%>

<!-- <div id="vendor_Imformation" class="cars_panel x-hidden collapsed">
	<div class="x-panel-header">
		<fmt:message key="label.vendorInformation.header" />
	</div>
	<div class="x-panel-body">
	
	<c:if test="${not empty imageDetailsForm.vendorInfoList }">
	
	<table id="vendor_Imformation" cellpadding="0" cellspacing="0">
	<c:forEach var="vendDetails" items="${imageDetailsForm.vendorInfoList }" varStatus="status">
		<tr>
	 <td>
	 
		<li class="car_id">
			<strong><fmt:message key ="label.vendorInformation.returnSampleIndicator" /></strong> 
			${vendDetails.vendorsampleIndicator }
		</li>
		<li class="dept">
			<strong><fmt:message key ="label.vendorInformation.carrier" /></strong>	
			${vendDetails.carrierAcct}
		</li>		
		
		</td>
		<td>
		<li class="vendor_name">
			<strong><fmt:message key ="label.vendorInformation.vendorReturnSample"/></br></strong>
			 ${vendDetails.vendorsampleAddress}
		</li>		
		</td>
		
		</tr>
		</br>	
		</c:forEach>
</table>		
</c:if>
</div></div>-->

<%--Vendor Information Ends--%>

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

	<div class="x-panel-header">
	Upload Image
	</div>
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
				<input name="fileData" id="fileData"type="file" accept="Image/jpeg,image/jpg,image/psd,image/tiff,image/eps" />
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
		<input class="btn"   id="btnImageUploadPopUpClose" type="button" name="Cancel" value="Cancel" style="margin-right: 4px;" onclick="clearErrorMessage();"/> 		
		<input class="btn" type="button" name="imageSave1" id="imageSave1" value="Add Image" style="margin-right: 10px;" onclick="validateFields('imageuploadformid');"/>
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
				<label style="margin-left:53px;height: 16px;">Image ${imageName} successfully uploaded. </label>
			</li>
				
			</br>
						
		<input class="btn"   id="successPopUpClose" type="button"
		onclick='$("#overlay_Upload").hide();$("#dialog_uploadSuccess").hide();' name="Close" value="Close" style="float: right;margin-right:10px;" /> 		
		

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
<div id="dialog_submitApprove" class="web_dialog_imageUploadPopUp" style="height: 140px; width:350px;">
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
				
					<input class="btn"   id="approveConfirmPopUpOkId" type="button" onclick="submitApproveAction('${imageApproveAction}');dialogApproveHideonOk();" name="Ok" value="Ok" style="float: right;" />			
					
				</li>				
			</ul>

		</div>
	</div>
</div>
<!-- Image Approve Confirmation Popup Ends-->

<!-- At least one shot type 'A' is required starts. -->
<div id="dialog_checkShotTypeA" class="web_dialog_imageUploadPopUp" style="height: 140px; width:350px;">
	<div id="content">
		<div class="x-panel-header" id="checkShotTypeAId">
			Approve Image Confirmation			
		</div>
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<ul>
				<li style="width:100%;float:left;margin-top:10px;">				
					<label style="margin-left:20px;height: 16px;" id="saveImageAtLeastShotTypeAId">At least one of the uploaded image should be Shot Type 'A'</label>
				</li>				
			</br>
				<li style="width:100%;float:left;margin-top:10px;">
					<input class="btn"   id="approvePopUpCloseId" type="button" onclick='$("#overlay_Upload").hide();$("#dialog_checkShotTypeA").hide();' name="Ok" value="Ok" style="float: right;margin-left:10px;margin-right:50%;" /> &nbsp;&nbsp;&nbsp;&nbsp;				
				</li>				
			</ul>

		</div>
	</div>
</div>
<!-- At least one shot type 'A' is required ends. -->


<!-- ShotType Check Approve Starts -->
<div id="dialog_submitApproveCheck" class="web_dialog_imageUploadPopUp" style="height: 140px; width:350px;">
	<div id="content">
		<div class="x-panel-header" id="saveApprovePopID">
			Approve Image Confirmation			
		</div>
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<ul>
				<li style="width:100%;float:left;margin-top:10px;">				
					<label style="margin-left:20px;height: 16px;" id="saveApprovePopLabelID">Please save unique ShotType for all images before Approve</label>
				</li>				
			</br>
				<li style="width:100%;float:left;margin-top:10px;">
					<input class="btn"   id="approvePopUpClose" type="button" onclick='$("#overlay_Upload").hide();$("#dialog_submitApproveCheck").hide();' name="Ok" value="Ok" style="float: right;margin-left:10px;margin-right:50%;" /> &nbsp;&nbsp;&nbsp;&nbsp;				
				</li>				
			</ul>

		</div>
	</div>
</div>
<!-- ShotType Check Approve Ends -->

<!--Unique ShotType POPUP Alert Starts -->
<div id="dialog_uniqueShotTypeCheckId" class="web_dialog_imageUploadPopUp" style="height: 140px; width:350px;">
	<div id="content">
		<div class="x-panel-header">
			Save ShotType Status			
		</div>
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<ul>
				<li style="width:100%;float:left;margin-top:10px;">				
					<label style="margin-left:-17px;height: 16px;" id="saveShotTypeID">Please select unique shottype for each uploaded image before saving</label>
				</li>				
			</br>
				<li style="width:100%;float:left;margin-top:10px;">
					<input class="btn"   id="uniqueShotTypeCheckClose" type="button" onclick='$("#overlay_Upload").hide();$("#dialog_uniqueShotTypeCheckId").hide();' name="Ok" value="Ok" style="float: right;margin-left:10px;margin-right:50%;" /> &nbsp;&nbsp;&nbsp;&nbsp;				
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
var table = document.getElementById("vImage");
	var rowCount = table.rows.length-1;
  if(uploadStatus=='Y'){	
	$("#overlay_Upload").show();
	$("#dialog_uploadSuccess").show();	
	selectedColorOrin1= '${selectedColorOrin}';	
  }else if(uploadStatus=='N'){		
		$("#overlay_Upload").show();
		$("#dialog_uploadFailure").show();
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
if(uploadStatus=='Y' && $("#selectedOrinVPI").val().trim().length != 0 ){	
	expandCollapse("petTable",selectedColorOrin1);	
}
trClick();
if(uploadStatus=='Y'){	
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
</script>