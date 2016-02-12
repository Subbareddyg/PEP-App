<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
     <title>Content Display.</title>
     <link rel="stylesheet" type="text/css" href="<%=response.encodeURL(request.getContextPath()+ "/css/pep.css")%>">
     <link rel="stylesheet" type="text/css" href="<%=response.encodeURL(request.getContextPath()+ "/css/contentDecoration.css")%>">     
     <link rel="stylesheet" type="text/css"   href="<%=response.encodeURL(request.getContextPath()+"/css/extjs-resources/css/ext-all.css")%>">
     <link rel="stylesheet" type="text/css"  href="<%=response.encodeURL(request.getContextPath()+"/css/googiespell/googiespell.css")%>">
     <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
     <link rel="stylesheet" type="text/css"  href="<%=response.encodeURL(request.getContextPath()+"/css/bootstrap.css")%>">
     <link rel="stylesheet"  type="text/css"  href="<%=response.encodeURL(request.getContextPath()+"/css/jquery.treegrid.css")%>">
     


     
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/js/libs/jq-plugins-adapter.js"></script>	
	<script type="text/javascript"  src="<%=request.getContextPath()%>/js/libs/ext-all.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/libs/jquery.scrollTo-min.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/belk.pep.common.js?v=2008111301"></script>
	
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/belk.pep.editpep.js?v=2008111301"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/belk.pep.uploadimage.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/googiespell/AJS.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/googiespell/googiespell.js?v=2008111301"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/googiespell/cookiesupport.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/belk.pep.sizecolor.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/libs/jquery-latest.min.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/libs/jquery-plugins.js"></script> 	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/belk.pep.searchDialog.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/dialogModal.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/contentScreen.js"></script>
	

	
  	<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
 	<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	
	
	<script>hljs.initHighlightingOnLoad();</script>
    <script src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.min.js")%>"></script>
    <script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.treegrid.js")%>"></script>
  
    <script type="text/javascript">
            $(document).ready(function() {
                $('.tree').treegrid();
                $('.tree2').treegrid({
                    expanderExpandedClass: 'icon-minus-sign',
                    expanderCollapsedClass: 'icon-plus-sign'
                });
            });
            
            
            function dropDownValues(selectedDropdwonValue, selectedDropDown, totalDropDwons){
            	
           
         	 var list =  document.forms['contentDisplayForm'].elements["dropDownsName_id"+selectedDropDown+""];
         	 var attNameAndPath = document.getElementById("dropdownAttributeNameXpath_id"+(selectedDropDown)).value;
         	 
                  //	alert("length "+list.options.length);
                  	var finalVal = "";
                  	for(var z=0; z<list.options.length; z++){
                  		if(list.options[z].selected){
                  			if(finalVal == null || finalVal == ""){
                  				finalVal =  attNameAndPath + "#" + list.options[z].value;
                  			}else{
                  				finalVal = finalVal +","+ list.options[z].value;
                  			}
                	 }
          	  
         	 		}
                  //	alert(finalVal);
                  	var exisVal = document.getElementById("dropdownhidden_id"+(selectedDropDown)).value;
                  	//alert(exisVal);
                  	document.getElementById("dropdownhidden_id"+(selectedDropDown)).value = finalVal;
                  //	alert(document.getElementById("dropdownhidden_id"+(selectedDropDown)).value);
         	  	}
            
     </script>
     
    
     
    
     
  
     


    
  
     
	
     <script>
	     function calliph(iph,petId)
	     {
	    	 alert("calliph..iph."+iph);
	    	 alert("calliph..petId."+petId);
	    	 document.getElementById("getIPHCategory").value=iph;
	    	 document.forms['contentDisplayForm'].petIdForWebservice.value=petId;    	 
	    	
	    	 
	     }
	     
	     
	    function   updateContentPetStyleDataStatus()	     
	     {
	    	alert('updateContentPetStyleDataStatus');
	    	 document.forms['contentDisplayForm'].updateContentStatusStyleActionParameter.value = "updateContentStatusForStyle";		
		 	 var choiceValue = confirm("Have you saved the attributes by clicking on the Save Button?");
               if( choiceValue == true ){	            	
            	  document.forms['contentDisplayForm'].submit(); 	                 
                  return true;
               }
               else{
            	  
                  return false;
               } 
	    	 
	     }
	     
	     
	     
	 	function updateContentStatusForStyleData()
		{ 
			   
			 	document.forms['contentDisplayForm'].updateContentStatusStyleActionParameter.value = "updateContentStatusForStyle";		
			 	 var choiceValue = confirm("Have you saved the attributes by clicking on the Save Button?");
	               if( choiceValue == true ){	            	
	            	  document.forms['contentDisplayForm'].submit(); 	                 
	                  return true;
	               }
	               else{
	            	  
	                  return false;
	               }
				
		} 
	 	
	 	
	       
            
		function getDynamicAttributes()
		{ 
			 //alert("hello dynamic");  
			   var values = document.getElementById("iphCategoryDropDownId");
			   var selectedVal = values.options[values.selectedIndex].value
			   
			 	document.forms['contentDisplayForm'].dynamicCategoryId.value = selectedVal;			           	
	            document.forms['contentDisplayForm'].submit(); 	                 
	                           
	 	
		}  
      

	 	
	 	//goToWorkListDisplayScreen() function is called on click of the Close  button to navigate back to the  WorkListDisplay Screen
		function goToWorkListDisplayScreen() {			
			 
			var response =  confirm("You are trying to close the page. Data entered will be lost if you have not saved. Still would you like to close? ");
			if(response == false){
				return false;
		 	}
			window.location = "/wps/portal/home/worklistDisplay";
		}
	 	
		
//on click of the sku orin number hyper link get the sku attributes
		function getSkuAttributes(url,selectedOrinNumber){	
			alert("about to make ajax call.....for getting  the sku attributes...");
			var ajaxCall=  $.ajax({
				  
		        url: url,
		        type: 'GET',
		        dataType: "json",		
		        cache:false,			        
		        data: { 
		        	   skuOrinNumber:selectedOrinNumber		        	  
					   },
				   
		            
		            	 success: function(data){
		            		  
		                         $("#ajaxResponseSkuAttribute").html("");
		                         $("#ajaxResponseSkuAttribute").append("<b>SKU Orin Number :</b> " + data.skuObjectInfo.skuOrinNumber + "<br>");
		                         $("#ajaxResponseSkuAttribute").append("<b>Import/Domestic :</b> " + data.skuObjectInfo.sourceDomestic + "<br>");
		                         $("#ajaxResponseSkuAttribute").append("<b>NRF Size Code :</b> " + data.skuObjectInfo.nrfSizeCode + "<br>");
		                         $("#ajaxResponseSkuAttribute").append("<b>Vendor Size Description  :</b> " + data.skuObjectInfo.vendorSizeDescription + "<br>");
		                         $("#ajaxResponseSkuAttribute").append("<b>Omnichannel Size Description :</b> " + data.skuObjectInfo.omnichannelSizeDescription + "<br>");
		                         $("#ajaxResponseSkuAttribute").append("<b>Belk 04 UPC :</b> " + data.skuObjectInfo.vendorUpc+ "<br>");
		                         $("#ajaxResponseSkuAttribute").append("<b>Vendor UPC :</b> " +data.skuObjectInfo.belk04Upc + "<br>");
							
		            	
		            	
		        },
                
		   
		    });
		}
		
		//Capture the category id ,pet id on  change of the drop down
		function getCategoryId(url,categoryId,selectedOrinNumber){	
			alert("about to make ajax call.....for getting  the sku attributes...");
			var ajaxCall=  $.ajax({
				  
		        url: url,
		        type: 'GET',
		        dataType: "json",		
		        cache:false,			        
		        data: { 
		        	     categoryId:$("#iphCategoryDropDownId").val(),
		        	     selectedOrinNumber:selectedOrinNumber
		        	     
					   },
				   
		            
		            	 success: function(data){
		            		  
		                         $("#ajaxResponseSkuAttribute").html("");
		                        
							
		            	
		            	
		        },
                
		   
		    });
		}
		
		//on click of the style Color orin number hyperlink get the style Color attributes
		function getStyleColorAttributes(url,selectedOrinNumber){	
			alert("about to make ajax call.....for getting  the getStyleColorAttributes...");
			var ajaxCall=  $.ajax({
				  
		        url: url,
		        type: 'GET',
		        dataType: "json",		
		        cache:false,			        
		        data: { 
		        	   styleColorOrinNumber:selectedOrinNumber		        	  
					   },
				   
		            
		            	 success: function(data){
		            		  
		                         $("#ajaxResponseStyleColorAttribute").html("");
		                         $("#ajaxResponseStyleColorAttribute").append(" " + data.styleColorObjectInfo.message + "<br>");
		                     
							
		            	
		            	
		        },
                
		   
		    });
		}
		
		
		//on click of the style orin number hyperlink get the style  attributes
		function getStyleAttributes(url,selectedOrinNumber){	
			alert("about to make ajax call.....for getting  the getStyleAttributes...");
			var ajaxCall=  $.ajax({
				  
		        url: url,
		        type: 'GET',
		        dataType: "json",		
		        cache:false,			        
		        data: { 
		        	   styleOrinNumber:selectedOrinNumber		        	  
					   },
				   
		            
		            	 success: function(data){
		            		  
		                         $("#ajaxResponseStyleAttribute").html("");
		                         $("#ajaxResponseStyleAttribute").append("<b>SKU Orin Number :</b> " + data.skuObjectInfo.skuOrinNumber + "<br>");
		                         $("#ajaxResponseStyleAttribute").append("<b>Import/Domestic :</b> " + data.skuObjectInfo.sourceDomestic + "<br>");
		                         $("#ajaxResponseStyleAttribute").append("<b>NRF Size Code :</b> " + data.skuObjectInfo.nrfSizeCode + "<br>");
		                         $("#ajaxResponseStyleAttribute").append("<b>Vendor Size Description  :</b> " + data.skuObjectInfo.vendorSizeDescription + "<br>");
		                         $("#ajaxResponseStyleAttribute").append("<b>Omnichannel Size Description :</b> " + data.skuObjectInfo.omnichannelSizeDescription + "<br>");
		                         $("#ajaxResponseStyleAttribute").append("<b>Belk 04 UPC :</b> " + data.skuObjectInfo.vendorUpc+ "<br>");
		                         $("#ajaxResponseStyleAttribute").append("<b>Vendor UPC :</b> " +data.skuObjectInfo.belk04Upc + "<br>");
							
		            	
		            	
		        },
                
		   
		    });
		}
	
   
		//on click of the Style Data row submit button ,pass the style orin number ,style pet status and logger in user to the update content pet statuswebservice
		var  styleContentPetWebserviceResponseFlag = "false";
		var  styleColorContentPetWebserviceResponseFlag="false";
		function getUpdateStyleContentPetStatusWebserviceResponse(url,stylePetOrinNumber,stylePetContentStatus,user){	
			alert("about to make ajax call.....for updating the pet content status...");
			 var ajaxCall=  $.ajax({
                 
                 url: url,
                 type: 'GET',
                 dataType: "json",         
                 cache:false,                      
                 data: { 
                           selectedOrinNumber:stylePetOrinNumber,
                           styleContentStatus:stylePetContentStatus,
                           loggedInUser:user
                                },
                         
                     
                        success: function(data){
                                
                                  $("#ajaxResponseUpdateStylePetContentStatus").html("");
                                  $("#ajaxResponseUpdateStylePetContentStatus").append("<b>Style Pet Status updated successfully !</b>" );      
                                  $("#saveButtonId").attr("disabled", "disabled");
                                  $("#styleSubmit").attr("disabled", "disabled");                                 
                                  styleContentPetWebserviceResponseFlag="true";
                                 
                                  
                        },
                        
                        error: function(XMLHttpRequest, textStatus, errorThrown){
                        	alert("textStatus.."+textStatus);
                        	alert("errorThrown.."+errorThrown);
    	                	
    	                	$("#ajaxResponseUpdateStylePetContentStatus").html("");
    	                	$("#ajaxResponseUpdateStylePetContentStatus").append("<b>Style Pet Status could not be updated ,contact System Administrator. </b> <br>"); 
    	                    $("#ajaxResponseUpdateStylePetContentStatus").append("<br>");
    	                    $("#saveButtonId").removeAttr("disabled");
    	                    $("#styleSubmit").removeAttr("disabled");    	                
    	                  
    	                  }
           
            
             });

			
		}
		
		
	   
	 //on click of the Style Color  Data row submit button ,pass the style color orin number ,styleColor  pet status and logger in user to the update content pet style color  status by caling webservice
		function getUpdateStyleColorContentPetStatusWebserviceResponse(url,styleColorPetOrinNumber,styleColorPetContentStatus,user){	
			alert("about to make ajax call.....for updating the style color pet content status...");
			alert(' test get style color button id dynamically..'+document.getElementById('styleColorSubmitButtonId-'+styleColorPetOrinNumber));
			
			  $("#styleColorSubmitButtonId-"+styleColorPetOrinNumber)
			 var ajaxCall=  $.ajax({
                
                url: url,
                type: 'GET',
                dataType: "json",         
                cache:false,                      
                data: { 
                          selectedStyleColorOrinNumber:styleColorPetOrinNumber,
                          styleColorPetContentStatus:styleColorPetContentStatus,
                          loggedInUser:user
                       },
                        
                    
                       success: function(data){
                    	                                
                                 $("#ajaxResponseUpdateStylePetContentStatus").html("");
                                 $("#ajaxResponseUpdateStylePetContentStatus").append("<b>Style Color Pet Status updated successfully !</b>" );      
                                 $("#saveButtonId").attr("disabled", "disabled");
                                 //logic to dynamically generate unique ids for the  Style Color Submit or Style Color Approve button and disable the action button
                                 $("#styleColorSubmitButtonId-"+styleColorPetOrinNumber).attr("disabled", "disabled"); 
                                 styleColorContentPetWebserviceResponseFlag="true";
                                 
                       },
                       
                       error: function(XMLHttpRequest, textStatus, errorThrown){
   	                	
   	                	$("#ajaxResponseUpdateStylePetContentStatus").html("");
   	                	$("#ajaxResponseUpdateStylePetContentStatus").append("<b>Style Color Pet Status could not be updated ,contact System Administrator. </b> <br>"); 
   	                    $("#ajaxResponseUpdateStylePetContentStatus").append("<br>");
   	                    $("#saveButtonId").removeAttr("disabled");
   	                    //logic to dynamically generate unique ids for the  Style Color Submit or Style Color Approve button and disable the action button
   	                    $("#styleColorSubmitButtonId-"+styleColorPetOrinNumber).removeAttr("disabled");
   	                    styleColorContentPetWebserviceResponseFlag="false";
   	                  
   	                  }
          
           
            });

			
		}
		
	 
		
		 //on click of the Save button ,pass content pet attributes and make a ajax call to the  webservice
		function saveContentPetAttributesWebserviceResponse(url,stylePetOrinNumber,productName,productDescription,user, dropdownCount){	
			alert("about to make ajax call.....for saveContentPetAttributesWebserviceResponse...");
			
			var finalString = "";
			for(i=1; i<(dropdownCount+1); i++){
				var temp = $("#dropdownhidden_id"+(i)).val();
				if(!(temp == null || temp =="")){ 
					if(finalString==null || finalString==""){
						finalString = temp;
					}else{
						finalString = finalString +"~" + temp;
					}
				}
				
			}
			alert(finalString);
			
			 var ajaxCall=  $.ajax({
                
                url: url,
                type: 'GET',
                dataType: "json",         
                cache:false,                      
                data: { 
                	      stylePetOrinNumber:stylePetOrinNumber,
                	      productNameStyleAttribute:productName,
                	      productDescriptionStyleAttribute:productDescription,
                          loggedInUser:user,
                          dropdownhidden_id1:finalString
                       },
                        
                      
                       success: function(data){              	 
                               
                              $("#ajaxResponseSaveContentPetAttribute").html("");
                              $("#ajaxResponseSaveContentPetAttribute").append("<b>"+data.responseObject.message+"!</b>");                             
                                 
                                 
                       },
                       
                       error: function(XMLHttpRequest, textStatus, errorThrown){              	   
   	                	
	   	                	$("#ajaxResponseSaveContentPetAttribute").html("");
	   	                	$("#ajaxResponseSaveContentPetAttribute").append("<b>Content Pet Attributes saved could not be updated ,contact System Administrator. </b> <br>"); 
	   	                    $("#ajaxResponseSaveContentPetAttribute").append("<br>");
	   	                   
   	                  
   	                  }
          
           
            });

			
		}
     </script>
     

</head>

<script>
function createmannualpet(){
window.location = '<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/wps/portal/home/CreatePET" %>';
}


function logout_home(){	
	
	var username = document.getElementById("username").value
	
	
	
	if(username.indexOf('@') === -1) 
	{
	   window.location = "/wps/portal/home/InternalLogin";
	} else {
		
		window.location = "/wps/portal/home/ExternalVendorLogin";
	}
	
}
</script>

<portlet:defineObjects />

<fmt:setBundle basename="contentDisplay" var="display"/>  
		<portlet:actionURL var="formAction"> 
				<portlet:param name="action" value="contentSubmit"/>
		</portlet:actionURL>
		
		<portlet:actionURL var="getIphAttributeAction"> 
				<portlet:param name="action" value="getIphAttributeAction"/>
		</portlet:actionURL>
		
		<portlet:resourceURL var="updateContentPetStyleDataStatus" id="updateContentPetStyleDataStatus"></portlet:resourceURL>
		<portlet:resourceURL var="updateContentPetStyleColorDataStatus" id="updateContentPetStyleColorDataStatus"></portlet:resourceURL>
		<portlet:resourceURL var="saveContentPetAttributes" id="saveContentPetAttributes"></portlet:resourceURL>	
		
	
		<div id="content">
				<div id="main">

					<form:form commandName="contentDisplayForm" method="post" action="${formAction}" name="contentDisplayForm" id="contentDisplayForm">
				
					        <input type="hidden" id="getIPHCategory" name="getIPHCategory" value=""></input>
					        <input type="hidden" id="petIdForWebservice" name="petIdForWebservice" value=""/>
							<input type=hidden id="actionParameter" name="actionParameter"/>
							<input type="hidden" id="username" name="username" value="afusos3"/>
							<input type="hidden" id="dynamicCategoryId" name="dynamicCategoryId" value=""/>
							<input type=hidden id="stylePetId" name="stylePetId" value="${contentDisplayForm.styleInformationVO.orin}"/>	
							
							<!--Logout for Content Screen -->		
							 <div align="right" >	
								<c:out value="${contentDisplayForm.pepUserId}"/> &nbsp;	 
								<input type="button"   style="font-weight: bold" name="logout" value="Logout" 
			   					 onclick=logout_home();  />	
			
							 </div>
							
							<!--Style Info Section starts over here  -->					  
							<div  class="cars_panel x-hidden" >
								<div class="x-panel-header">
										<fmt:message key="content.label.styleInfo" bundle="${display}"/>										
								</div>
								
									
								<div class="x-panel-body">	
								        
									<!-- to show message from save content web service  -->
									
									<div id="ajaxResponseSaveContentPetAttribute"></div>
								    
								   	<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">								   	    

										<li class="txt_attr_name" style="width: 30%;">
										    <c:if test="${not empty  contentDisplayForm.saveStyleAttributeMessage}">	
										        <c:out value="${contentDisplayForm.saveStyleAttributeMessage}"/>										  
										    </c:if>
										</li>				
										
									</ul> 
									  
									<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">

										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="labelOrinGrouping" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.orin}"/></li>				
										<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelDepartment" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.departmentId}"/></li>
										<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelVendorName" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.vendorName}"/></li>	
									</ul>


									<ul class="pep_info"
										style="font-size: 11px; padding: 0 0 10px !important;">
										<li class="ctxt_attr_name" style="width: 30%;"><fmt:message key="labelStyleNumber" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.styleId}"/></li>
										<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelClass" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.classId}"/></li>
										<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelVendorNumber" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.vendorId}"/></li>
										
									</ul>
			
									<ul class="pep_info"
										style="font-size: 11px; padding: 0 0 10px !important;">
										<li class="ctxt_attr_name" style="width: 30%;"><fmt:message key="labelDescription" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.description}"/></li>
										<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelOmnichannelVendor" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.omniChannelVendorIndicator}"/></li>
										<li class="txt_attr_name"><fmt:message key="labelVendorProvidedImage" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.vendorProvidedImageIndicator}"/></li>
										
									</ul>
									
									<ul class="pep_info"
										style="font-size: 11px; padding: 0 0 10px !important;">
										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="labelVendorProvidedSample" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.vendorSampleIndicator}"/></li>
										<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelCompletionDate" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.completionDateOfStyle}"/></li>			
									</ul>
									
									<c:if test="${contentDisplayForm.roleName == 'readonly'}">									
									   
									        
								             <input type="button" name="Save" value="<fmt:message key="content.label.saveButton" bundle="${display}"/>" 
				  							       class="saveContentButton"  onclick="javascript:saveContent();" disabled="true"/>							                            
										      
				  							  
				  					          <input type="button" name="Close" value="<fmt:message key="content.label.closeButton" bundle="${display}"/>" 
				  							      class="closeContentButton"   onclick="javascript:goToWorkListDisplayScreen();"/>
									
                                          
                                    </c:if>
                                     
									<c:if test="${contentDisplayForm.roleName == 'dca' || contentDisplayForm.roleName == 'vendor' }">	
										<c:choose>
										    <c:when test="${contentDisplayForm.disableSaveButton == 'false'}">
										        
									             <input type="button" name="Save" id="saveButtonId"  value="<fmt:message key="content.label.saveButton" bundle="${display}"/>" 
					  							       class="saveContentButton"  onclick="javascript:saveContentPetAttributesWebserviceResponse('${saveContentPetAttributes}','<c:out value="${contentDisplayForm.styleInformationVO.orin}"/>','<c:out value="${contentDisplayForm.productDetailsVO.productName}"/>','<c:out value="${contentDisplayForm.productDetailsVO.productDescription}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>', '<c:out value="${contentDisplayForm.productAttributesDisplay.dropDownList.size()}"/>')"/>
									                            
											      
					  							  
					  					          <input type="button" name="Close" value="<fmt:message key="content.label.closeButton" bundle="${display}"/>" 
					  							      class="closeContentButton"   onclick="javascript:goToWorkListDisplayScreen();"/>
										    </c:when>									   
										    <c:otherwise>
										         <input type="button" name="Save" id="saveButtonId"  value="<fmt:message key="content.label.saveButton" bundle="${display}"/>" 
					  							       class="saveContentButton" disabled="true" onclick="javascript:saveContent();"/>
					  							  
					  					          <input type="button" name="Close" value="<fmt:message key="content.label.closeButton" bundle="${display}"/>" 
					  							      class="closeContentButton"  onclick="javascript:goToWorkListDisplayScreen();"/>
										    </c:otherwise>
										</c:choose>
									 </c:if>
												  	
			
                                </div>								 
									
							</div>
							<!--Product Details Section starts over here  -->	
							<div id="prod_detail_pnl" class="cars_panel x-hidden">
								<div class="x-panel-header">
								   <fmt:message key="labelProductDetails" bundle="${display}"/>										
								</div>
								<div class="x-panel-body">
									<div id="prod_detail">
									<ul class="attrs">
									
									   	<c:if test="${contentDisplayForm.roleName == 'readonly'}">	
									   		
									   	       <li class="prod_name">
													<label><fmt:message key="labelProductName" bundle="${display}"/></label>				
														
															<textarea  id ="txt_outfitName"  name="productName" class="maxChars spellcheck recommended" cols="126" rows="4" onkeyup="textCounter();" onblur="textCounter();" disabled="disabled"><c:out value="${contentDisplayForm.productDetailsVO.productName}" escapeXml="true"/></textarea>
															
																 <div align="center" class="text_counter">
																		Max Chars: <span class="max_chars_val">300</span>  &nbsp; &nbsp; 
																		Chars Count: <span id="outfit_name_length">0</span>
																		<br style="clear:both;" />
																 </div>
																					
															<br style="clear:both;"/>
												</li> 
											
												<li class="prod_desc">
													<label><fmt:message key="labelProductDescription" bundle="${display}"/></label>			
													<textarea id="vendorStyle.Descr:132"	class="maxChars spellcheck textCount recommended" name="productDescription" cols="120" rows="13" onkeyup="textCounter();" onblur="textCounter();"  disabled="disabled"><c:out value="${contentDisplayForm.productDetailsVO.productDescription}"/></textarea>		
														
													<div class="chars_rem">
														<fmt:message key="caredit.page.product.remaining"/> <span class="chars_rem_val">40</span>
													</div>
													<div class="max_chars"
													style="margin-right: 150px; display: inline; white-space: nowrap;">
													<fmt:message key="content.page.product.max.char"  bundle="${display}"/><span class="max_chars_val">2000</span>&nbsp;&nbsp;
													<fmt:message key="content.page.product.minimum.char" bundle="${display}"/>&nbsp;&nbsp;
													
									
													Chars Count: <span id="product_desc_length">0</span>
													</div> 
													<br style="clear:both;" />
												</li>
												
									   	 </c:if>
									  <c:if test="${contentDisplayForm.roleName == 'dca' || contentDisplayForm.roleName == 'vendor' }">		 
									    <c:choose>
										    <c:when test="${contentDisplayForm.disableSaveButton == 'false'}">
												 <li class="prod_name">
												 
													<label><fmt:message key="labelProductName" bundle="${display}"/></label>				
														
															<textarea  id ="txt_outfitName"  name="productName" class="maxChars spellcheck recommended" cols="126" rows="4" onkeyup="textCounter();" onblur="textCounter();" ><c:out value="${contentDisplayForm.productDetailsVO.productName}" escapeXml="true"/></textarea>
																 <div align="center" class="text_counter">
																		Max Chars: <span class="max_chars_val">300</span>  &nbsp; &nbsp; 
																		Chars Count: <span id="outfit_name_length">0</span>
																		<br style="clear:both;" />
																 </div>					
															<br style="clear:both;"/>
														
													
												</li> 
											
												<li class="prod_desc">
													<label><fmt:message key="labelProductDescription" bundle="${display}"/></label>			
													<textarea id="vendorStyle.Descr:132"	class="maxChars spellcheck textCount recommended" name="productDescription" cols="120" rows="13" onkeyup="textCounter();" onblur="textCounter();" ><c:out value="${contentDisplayForm.productDetailsVO.productDescription}"/></textarea>		
														
													<div class="chars_rem">
														<fmt:message key="caredit.page.product.remaining"/> <span class="chars_rem_val">40</span>
													</div>
													<div class="max_chars"
													style="margin-right: 150px; display: inline; white-space: nowrap;">
													<fmt:message key="content.page.product.max.char"  bundle="${display}"/><span class="max_chars_val">2000</span>&nbsp;&nbsp;
													<fmt:message key="content.page.product.minimum.char" bundle="${display}"/>&nbsp;&nbsp;
													
									
													Chars Count: <span id="product_desc_length">0</span>
													</div> 
													<br style="clear:both;" />
												</li>
									    </c:when>									   
									    <c:otherwise>
									         <li class="prod_name">
													<label><fmt:message key="labelProductName" bundle="${display}"/></label>				
														
															<textarea  id ="txt_outfitName"  name="productName" class="maxChars spellcheck recommended" cols="126" rows="4" onkeyup="textCounter();" onblur="textCounter();" disabled="true"><c:out value="${contentDisplayForm.productDetailsVO.productName}" escapeXml="true"/></textarea>
																 <div align="center" class="text_counter">
																		Max Chars: <span class="max_chars_val">300</span>  &nbsp; &nbsp; 
																		Chars Count: <span id="outfit_name_length">0</span>
																		<br style="clear:both;" />
																 </div>					
															<br style="clear:both;"/>
														
													
												</li> 
											
												<li class="prod_desc">
													<label><fmt:message key="labelProductDescription" bundle="${display}"/></label>			
													<textarea id="vendorStyle.Descr:132"	class="maxChars spellcheck textCount recommended" name="productDescription" cols="120" rows="13" onkeyup="textCounter();" onblur="textCounter();" disabled="true"><c:out value="${contentDisplayForm.productDetailsVO.productDescription}"/></textarea>		
														
													<div class="chars_rem">
														<fmt:message key="caredit.page.product.remaining"/> <span class="chars_rem_val">40</span>
													</div>
													<div class="max_chars"
													style="margin-right: 150px; display: inline; white-space: nowrap;">
													<fmt:message key="content.page.product.max.char"  bundle="${display}"/><span class="max_chars_val">2000</span>&nbsp;&nbsp;
													<fmt:message key="content.page.product.minimum.char" bundle="${display}"/>&nbsp;&nbsp;
													
									
													Chars Count: <span id="product_desc_length">0</span>
													</div> 
													<br style="clear:both;" />
												</li>
									    </c:otherwise>
									</c:choose>
								</c:if>	
										
									
									
									</ul>
									</div>
								</div>
							</div>
							<!--Style,Style Color,SKU Content Section starts over here  -->					
							<div class="cars_panel x-hidden">
							
									<div class="x-panel-header">
											<fmt:message key="lableStyle-Color-SKU" bundle="${display}"/>
									</div>
									<c:if test="${not empty noStylePetsFound}">
									    <div class="x-panel-body" style="width:915px;">	
									         	<table  cellpadding="0">
									         	   <tr>
									 		          <td><font color = "red"><c:out value="${noStylePetsFound}" /></font></td>
									 		       </tr>
									 		    </table>
									 	</div>
									</c:if>
									
									<c:if test="${not empty StylePetsFound}">								 	
									
									
									<div class="x-panel-body" style="width:915px;">	
										<div>
										     <c:out value="${contentDisplayForm.updateContentStatusMessage}"/>										    
										</div>
										<div>
										     <c:out value="${contentDisplayForm.styleDataSubmissionDataMessage}"/>										    
										</div>
										
										<div  id="ajaxResponseUpdateStylePetContentStatus">  </div>
										
										<table  cellpadding="0"  class="table tree2 table-bordered table-striped table-condensed">
										   <thead>
											  <tr>
												 <th></th>
												 <th>
													<fmt:message key="headerOrinGrouping" bundle="${display}"/>
												 </th>
												 <th>
													<fmt:message key="headerStyle" bundle="${display}"/>
												 </th>
												 <th>
													<fmt:message key="headerColor" bundle="${display}"/>
												 </th>
												 <th>
													<fmt:message key="headerVendorSize" bundle="${display}"/>
												 </th>
												  <th>
													<fmt:message key="headerOmnichannelSizeDesc" bundle="${display}"/>
												 </th>
												 <th>
													<fmt:message key="headerContentStatus" bundle="${display}"/>
												 </th>
												 <th>
													<fmt:message key="headerCompletionDate" bundle="${display}"/>
												 </th>
												 <th><fmt:message key="headerAction" bundle="${display}"/></th>
												
											  </tr>
										   </thead>
										   <tbody>
										   <!-- Style and its child Style Color ,Style Color and its child SKU  logic  starts over here  -->
										    <input type="hidden" id="styleActionParameterId"      name="updateContentStatusStyleActionParameter"></input>
										    <input type="hidden" id="styleColorActionParameterId" name="updateContentStatusStyleColorActionParameter"></input>
										    
										   	<c:if test="${contentDisplayForm.noPetsFoundInADSEDatabase eq true }">
										   	<tr><td colspan="9"><c:out value="No Style Level Pet  Exists in Database"/></td></tr> 
										   	</c:if>
										   	
										   	<c:set var="subcount" value="260"/>
						                    <c:set var="countList" value="0"/>
						                    
										   	<c:forEach items="${contentDisplayForm.styleAndItsChildDisplay.styleList}" 
							                             var="styleDisplayList"  varStatus="status">							                             
							                           
							                         <portlet:actionURL name="loadStyleAttribute" var="loadStyleAttributeURL">	
							                            <portlet:param name="action" value="loadStyleAttributeAction"></portlet:param>						                         
							                         	<portlet:param name="orinNumber" value="${styleDisplayList.orinNumber}"></portlet:param>
							                         </portlet:actionURL>
							                         
													<tr name="tablereport"  class="treegrid-${countList}">	
														<portlet:resourceURL var="getStyleAttributeDetails" id ="getStyleAttributeDetails">		
																             	<portlet:param name="styleOrinNumber" value="${styleDisplayList.orinNumber}"></portlet:param>
							                             </portlet:resourceURL>						
														<td></td>													
													 	<td><a  href="#"  id="styleAnchorTag"  onclick="getStyleAttributes('${getStyleAttributeDetails}','<c:out value="${styleDisplayList.orinNumber}"/>')">${styleDisplayList.orinNumber}</a></td>
													 	<input type="hidden" id="styleOrinNumberId" name="styleOrinNumber" value="${styleDisplayList.orinNumber}"></input>
														<td><c:out value="QWERTY"/></td>
														<td><c:out value="${styleDisplayList.color}"/></td>
														<td><c:out value="${styleDisplayList.vendorSize}" /></td>
														<td><c:out value="${styleDisplayList.omniSizeDescription}" /></td>
														<c:if test="${empty contentDisplayForm.readyForReviewMessage}">	
															<td><c:out value="${styleDisplayList.contentStatus}" /></td>
														</c:if>	
														<c:if test="${not empty contentDisplayForm.readyForReviewMessage}">	
														   <td><c:out value="Ready For Review" /></td>
														</c:if>	   
														<input type="hidden" id="styleContentStatusId" name="styleContentStatus" value="${styleDisplayList.contentStatus}"></input>								
														<td><c:out value="${styleDisplayList.completionDate}" /></td>
														<portlet:actionURL var="updateContentStatusForStyle">
                                                               <portlet:param name="action" value="updateContentStatusForStyle" />
                                                        </portlet:actionURL>
														
														<c:choose>
														    <c:when test="${contentDisplayForm.roleName == 'vendor'}">
														                    <c:if test="${empty contentDisplayForm.readyForReviewMessage}">	
															                  <td><input type="button"  id="styleSubmit" name="submitStyleData"  value="Submit"   style="width: 170px; height:30px" onclick="javascript:updateContentStatusForStyleData();"/></td>
															                </c:if>	
															     			<c:if test="${not empty contentDisplayForm.readyForReviewMessage}">	
															     				<td><input type="button"  id="styleSubmit" name="submitStyleData"  value="Submit"   style="width: 170px; height:30px"  disabled="disabled"/></td>	
															     			</c:if>				 
														    </c:when>									   
														    <c:when test="${contentDisplayForm.roleName == 'dca'}">
														           
															     <td><input type="button"  id="styleSubmit" name="submitStyleData"  value="Approve"   style="width: 170px; height:30px" onclick="javascript:getUpdateStyleContentPetStatusWebserviceResponse('${updateContentPetStyleDataStatus}','<c:out value="${styleDisplayList.orinNumber}"/>','<c:out value="${styleDisplayList.contentStatus}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>')" /></td>						 
														    </c:when>
														    <c:when test="${contentDisplayForm.roleName == 'readonly'}">
															     <td><input type="button"  id="styleSubmit" name="submitStyleData"  value="Submit"   style="width: 170px; height:30px" onclick="javascript:updateContentStatusForStyleData();" disabled="true"/></td>						 
														    </c:when>
								                       </c:choose>
														
														
													
													</tr>
																	
											
													<c:forEach items="${styleDisplayList.styleColorList}" var="styleDisplayColorList"  varStatus="styleColorList">	
													          <c:set var="subcount" value="${subcount + styleColorList.count}" />																					
													       	  <portlet:resourceURL var="getStyleColorAttributeDetails" id ="getStyleColorAttributeDetails">		
																             	<portlet:param name="styleColorOrinNumber" value="${styleDisplayColorList.orinNumber}"></portlet:param>
							                                  </portlet:resourceURL>
													          <tr name="treegrid-${subcount} treegrid-parent-${countList}"  class="treegrid-${subcount} treegrid-parent-${countList}" >               
															   <td></td> 
															   <td><a  href="#"  id="styleColorAnchorTag"  onclick="getStyleColorAttributes('${getStyleColorAttributeDetails}','<c:out value="${styleDisplayColorList.orinNumber}"/>')">${styleDisplayColorList.orinNumber}</a></td>
															   
															   <input type="hidden" id="selectedStyleColorOrinNumber" name="selectedStyleColorOrinNumber" value="${styleDisplayColorList.orinNumber}"></input>   
															   															  														  
															   <input type="hidden" id="styleColorOrinNumberId" name="styleColorOrinNumber" value="${styleDisplayColorList.orinNumber}"></input>                                                                                       
															  <!--  <td><a><c:out value="${styleDisplayColorList.orinNumber}"/><a/></td> -->
															   <td><c:out value="QWERTY"/></td>                                                                                         
															   <td><c:out value="${styleDisplayColorList.color}"/></td> 
															   <td><c:out value="${styleDisplayColorList.vendorSize}" /></td>                                                                                        
															   <td><c:out value="${styleDisplayColorList.omniSizeDescription}" /></td>  
															   <input type="hidden" id="styleColorContentStatusId" name="styleColorContentStatus" value="${styleDisplayColorList.contentStatus}"></input>  
															   <c:if test="${empty contentDisplayForm.readyForReviewMessageStyleColor}">	                                                                                       
															      <td><c:out value="${styleDisplayColorList.contentStatus}"/></td> 
															   </c:if>
															   <c:if test="${not empty contentDisplayForm.readyForReviewMessageStyleColor}">	                                                                                       
															      <td><c:out value="Ready For Review"/></td> 
															   </c:if>                                   
															   <td><c:out value="${styleDisplayColorList.completionDate}" /></td> 
															   <c:choose>
																    <c:when test="${contentDisplayForm.roleName == 'vendor'}">
																    	 <c:if test="${empty contentDisplayForm.readyForReviewMessageStyleColor}">		 
																	        <td><input type="button"  id="styleColorSubmit" name="submitStyleColorData"  value="Submit"   style="width: 170px; height:30px" onclick="javascript:getUpdateStyleContentPetStatusWebserviceResponse('${updateContentPetStyleColorDataStatus}','<c:out value="${styleDisplayColorList.orinNumber}"/>','<c:out value="${styleDisplayList.contentStatus}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>')"/></td>
																	    </c:if>	
																	     <c:if test="${not empty contentDisplayForm.readyForReviewMessageStyleColor}">	                                                                                       
															     			 <td><input type="button"  id="styleColorSubmit" name="submitStyleColorData"  value="Submit"   style="width: 170px; height:30px" disabled="true"  onclick="javascript:getUpdateStyleColorContentPetStatusWebserviceResponse('${updateContentPetStyleColorDataStatus}','<c:out value="${styleDisplayColorList.orinNumber}"/>','<c:out value="${styleDisplayColorList.contentStatus}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>')"/></td> 
															  			 </c:if> 			 
																    </c:when>									   
																    <c:when test="${contentDisplayForm.roleName == 'dca'}">
																   
																	    <td><input type="button"    id="styleColorSubmitButtonId-${styleDisplayColorList.orinNumber}"   name="submitStyleColorData-${styleDisplayColorList.color}"  value="Approve"   style="width: 170px; height:30px" onclick="javascript:getUpdateStyleColorContentPetStatusWebserviceResponse('${updateContentPetStyleColorDataStatus}','<c:out value="${styleDisplayColorList.orinNumber}"/>','<c:out value="${styleDisplayColorList.contentStatus}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>')"/></td>
																    </c:when>
																    <c:when test="${contentDisplayForm.roleName == 'readonly'}">
																	    <td><input type="button"  id="styleColorSubmit" name="submitStyleColorData"  value="Submit"   style="width: 170px; height:30px" onclick="javascript:getUpdateStyleColorContentPetStatusWebserviceResponse('${updateContentPetStyleColorDataStatus}','<c:out value="${styleDisplayColorList.orinNumber}"/>','<c:out value="${styleDisplayColorList.contentStatus}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>')" disabled="true"/></td>
																    </c:when>
								                               </c:choose>                                                                                                                                                                               
															                                                                 
														   </tr>   
														   	 
															   <c:forEach items="${styleDisplayColorList.skuList}" var="styleColorsChildSkuList"  varStatus="skuList">
															      	
															       <c:set var="subcount" value="${subcount +styleColorList.count +skuList.count}" />
																       <!--   <portlet:actionURL name="loadSkuAttribute" var="loadSkuAttributeURL">	
											                                     <portlet:param name="selectSkuOrinNumberaction" value="loadSkuAttributeAction"></portlet:param>						                         
											                         	          <portlet:param name="skuOrinNumber" value="${styleColorsChildSkuList.orin}"></portlet:param>
								                        		            </portlet:actionURL>
								                        		        -->
								                        		        <portlet:resourceURL var="getSKUAttributeDetails" id ="getSKUAttributeDetails">		
																             	<portlet:param name="skuOrinNumber" value="${styleColorsChildSkuList.orin}"></portlet:param>
							                                            </portlet:resourceURL>
								                        		        
								                        		        <input type="hidden" id="selectedSkuOrinNumber" name="selectedSkuOrinNumber" value=""/>		
															       <tr name="treegrid2" class="treegrid-${subcount} treegrid-parent-${countList}">               
															          <td></td>
															          <td><a  href="#"  id="skuAnchorTag"  onclick="getSkuAttributes('${getSKUAttributeDetails}','<c:out value="${styleColorsChildSkuList.orin}"/>')">${styleColorsChildSkuList.orin}</a></td> 															          								      
															          <td><c:out value="QWERTY"/></td>                                                                                         
															          <td><c:out value="${styleColorsChildSkuList.color}"/></td> 
															          <td><c:out value="${styleColorsChildSkuList.vendorSize}" /></td>                                                                                        
															          <td><c:out value="${styleColorsChildSkuList.omniChannelSizeDescription}" /></td>  															         
															       	  <td><c:out value="${styleColorsChildSkuList.contentStatus}"/></td> 												                                        
															          <td><c:out value="${styleColorsChildSkuList.completionDate}" /></td> 													    		 
																      <td></td>													                                                                                          
															     	 
															                                                                 
														          </tr>   
														   
															            	
													          </c:forEach>	                                                            
											                  
												    </c:forEach>
												  
												    <c:set var="countList" value="${countList+1}" />
							               	</c:forEach> 
							

											
											</tbody>			
		                                   </table>			
															
									</div>
								</c:if>					
									
							</div>
							
						<!--Global Attribute Section starts here  -->	
						<c:if test="${contentDisplayForm.displayStyleAttributeSection =='no'}">	
							 <div class="cars_panel x-hidden" id="globalAttributeSection">
								
										<div class="x-panel-header">
											<fmt:message key="sectionGlobalAttribute" bundle="${display}"/>
										</div>
										
										
										
										<div class="x-panel-body" style="width:915px;">
										 <c:if test="${not empty  contentDisplayForm.noStyleAttributeExistsMessage}">	
										      <c:out value="${contentDisplayForm.noStyleAttributeExistsMessage}"/>
										  
										  </c:if>
										
										  <c:if test="${empty  contentDisplayForm.noStyleAttributeExistsMessage}">	
										
										  <ul class="pep_info"
											style="font-size: 11px; padding: 0 0 10px !important;">
	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelBrand" bundle="${display}"/>
											 <input type="text" name="brand" id="gaBrand" value="${contentDisplayForm.globalAttributesDisplay.omniChannelBrand}"/>
											 <input type=hidden id="omniChannelBrandXpath" name="omniChannelBrandXpath" value="${contentDisplayForm.globalAttributesDisplay.omniChannelBrandXpath}"/>
											
											</li>				
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelLonDescription" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.productDescription}"/></li>
										 
										 </ul>
										 
										  <ul class="pep_info"
											style="font-size: 11px; padding: 0 0 10px !important;">
	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelLaunchDate" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.launchDate}"/></li>	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelBelkExclusive" bundle="${display}"/>
											<input type="text" name="belkExclusive" id="gaBelkExclusive" value="${contentDisplayForm.globalAttributesDisplay.belkExclusive}"/>
											<input type=hidden id="belkExclusiveXpath" name="belkExclusiveXpath"   value="${contentDisplayForm.globalAttributesDisplay.belkExclusiveXpath}"/>
											</li>
										 
										 </ul>
										 
										   <ul class="pep_info"
											style="font-size: 11px; padding: 0 0 10px !important;">
	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="lableChannelExclusive" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.channelExclusive}"/></li>	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelSdf" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.sdf}"/></li>
										 
										 </ul>
										 
										   <ul class="pep_info"
											style="font-size: 11px; padding: 0 0 10px !important;">
	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelProductDimensionsUom" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.productDimensionUom}"/></li>	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelBopis" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.bopis}"/></li>
										 
										 </ul>
										 
										 
										   <ul class="pep_info"
											style="font-size: 11px; padding: 0 0 10px !important;">
	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelProductLength" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.productLength}"/></li>	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelImportDomestic" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.importDomestic}"/></li>
										 
										 </ul>
										 
										   <ul class="pep_info"
											style="font-size: 11px; padding: 0 0 10px !important;">
	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelProductWidth" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.productWidth}"/></li>	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelGiftBox" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.giftBox}"/></li>
										 
										 </ul>
										 
										  </ul>
										 
										   <ul class="pep_info"
											style="font-size: 11px; padding: 0 0 10px !important;">
	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelProductHeight" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.productHeight}"/></li>	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelGwpPwp" bundle="${display}"/><c:out value="Check data later"/></li>
										 
										 </ul>
										 
										  </ul>
										 
										   <ul class="pep_info"
											style="font-size: 11px; padding: 0 0 10px !important;">
	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelProductWeightUom" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.productWeightUom}"/></li>	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelPyg" bundle="${display}"/><c:out value="Check data later"/></li>
										 
										 </ul>
										 
										    <ul class="pep_info"
											style="font-size: 11px; padding: 0 0 10px !important;">
	
											<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelProductWeight" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.productWeight}"/></li>	
										 
										 </ul>
	                                   
	                                    </c:if>
										</div>
										
							</div>	 
						</c:if>
						<!--Color Attributes Section starts here  -->
						<div id ="ajaxResponseStyleColorAttribute">			</div>
						
						
						 <c:if test="${contentDisplayForm.displayStyleColorAttributeSection =='no'}">										
										   
									<div class="cars_panel x-hidden"  id="colorAttributeSection" >
							
										<div class="x-panel-header">
											<fmt:message key="sectionColorAttribute" bundle="${display}"/>
										</div>
									
										<div class="x-panel-body" style="width:915px;">	
										
										  <c:if test="${not empty  contentDisplayForm.noStyleColorAttributeExistsMessage}">	
										      <c:out value="${contentDisplayForm.noStyleColorAttributeExistsMessage}"/>
										  
										  </c:if>
										
										<!--   <c:if test="${empty  contentDisplayForm.noStyleColorAttributeExistsMessage}">	--> 
										
											  <ul class="pep_info"
												style="font-size: 11px; padding: 0 0 10px !important;">
		
												<li class="txt_attr_name" style="width: 50%;"><fmt:message key="labelOmnichannelColorFamily" bundle="${display}"/>
												 <input type="text" name="brand" id="gaBrand" value="${contentDisplayForm.globalAttributesDisplay.omniChannelBrand}"/>
												 <input type=hidden id="omniChannelBrandXpath" name="omniChannelBrandXpath" value="${contentDisplayForm.globalAttributesDisplay.omniChannelBrandXpath}"/>
												 <input type=hidden id="stylePetId" name="stylePetId" value="${contentDisplayForm.styleInformationVO.orin}"/>
												</li>				
												<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelSecondary_Color_1" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.productDescription}"/></li>
											 
											 </ul>
										 
											  <ul class="pep_info"
												style="font-size: 11px; padding: 0 0 10px !important;">
		
												<li class="txt_attr_name" style="width: 50%;"><fmt:message key="labelSuper_Color_2" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.launchDate}"/></li>	
												<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelSuper_Color_3" bundle="${display}"/>
												<input type="text" name="belkExclusive" id="gaBelkExclusive" value="${contentDisplayForm.globalAttributesDisplay.belkExclusive}"/>
												<input type=hidden id="belkExclusiveXpath" name="belkExclusiveXpath"   value="${contentDisplayForm.globalAttributesDisplay.belkExclusiveXpath}"/>
												</li>
											 
											 </ul>
										 
											   <ul class="pep_info"
												style="font-size: 11px; padding: 0 0 10px !important;">
		
												<li class="txt_attr_name" style="width: 50%;"><fmt:message key="labelSuper_Color_4" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.channelExclusive}"/></li>	
												<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelNRFColorCode" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.sdf}"/></li>
											 
											  </ul>
										 
											   <ul class="pep_info"
												style="font-size: 11px; padding: 0 0 10px !important;">
		
												<li class="txt_attr_name" style="width: 50%;"><fmt:message key="labelOmniChannelColorDescription" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.productDimensionUom}"/></li>	
												<li class="txt_attr_name" style="width: 25%;"><fmt:message key="labelVendorColor" bundle="${display}"/><c:out value="${contentDisplayForm.globalAttributesDisplay.bopis}"/></li>
											 
											 </ul>  
										<!--  </c:if>	-->                         
	                                   		
																
									</div>				
									
						      </div>	  
					 </c:if>
						
						
						<!--SKU Attributes Section starts here  -->	
						<div class="cars_panel x-hidden"  id="skuAttributeSection" >
							
									<div class="x-panel-header">
										<fmt:message key="sectionSKUAttribute" bundle="${display}"/>
									</div>
									
									<div id="ajaxResponseSkuAttribute" class="x-panel-body" style="width:915px;">	
									
												
															
									</div>				
									
						</div>	
						
						<!--Product  Attributes Section starts here  -->	
						<div class="cars_panel x-hidden"  id="productAttributeSection">
							
									<div class="x-panel-header">
										<fmt:message key="sectionProductAttribute" bundle="${display}"/>
									</div>
									
									<div class="x-panel-body" style="width:915px;">	
									        <table  cellpadding="0"  class="table tree2 table-bordered table-striped table-condensed">
									            <div id="webserviceMessageDescription"><tr><font color="blue"> </font></tr></div>
									           
									            <tr>
									                <portlet:resourceURL id="fetchIPHCategory" var="fetchIPHCategory"></portlet:resourceURL>
									                <td><p>Select IPH Category:</p></td>
													<td>
													    <input type=hidden id="mapItemToIphActionParameter" name="mapItemToIphActionParameter"/>
													    <input type=hidden id="iphCategoryId" name="iphCategoryName"/>	
													    <input type=hidden id="petId" name="petId" value="${contentDisplayForm.styleInformationVO.orin}"/>	
													   	<c:if test="${contentDisplayForm.roleName == 'readonly'}">												   
														    <select style="width:58%;height: 27px;"  id="iphCategoryDropDownId" name="iphCategoryDropDown" onchange="calliph('${getIPHCategory}','<c:out value="${contentDisplayForm.styleInformationVO.orin}"/>')" disabled="disabled">
														        <c:forEach var="categoryMap" items="${contentDisplayForm.categoryReferenceData}">												
																	<option value="${categoryMap.key}"  [b]selected="true"[/b] ><c:out value="${categoryMap.value}"/></option>              
																</c:forEach>														
														    </select>
													    </c:if>
													    <c:if test="${contentDisplayForm.roleName == 'vendor' || contentDisplayForm.roleName == 'dca'}">
													       	
													   <form:form   method="get" action="${getIphAttributeAction}" name="iphForm" id="iphForm">											   
															    <select style="width:58%;height: 27px;"  id="iphCategoryDropDownId" name="iphCategoryDropDown"  onchange="getCategoryId('${fetchIPHCategory}','<c:out value="${categoryMap.key}"/>','<c:out value="${contentDisplayForm.styleInformationVO.orin}"/>')">
															        <c:forEach var="categoryMap" items="${contentDisplayForm.categoryReferenceData}">												
																		<option value="${categoryMap.key}"  [b]selected="true"[/b] ><c:out value="${categoryMap.value}"/></option> 
																		<option value="4001"  [b]selected="true"[/b] ><c:out value="Accessories"/></option>  
																		<option value="4693"  [b]selected="true"[/b] ><c:out value="Slippers"/></option> 
																		
																		            
																	</c:forEach>														
															    </select>
														</form:form> 
													    </c:if>												    
													    
													    
													 </td>   
													
												</tr>
												 <% int i=1;  %>
													<c:forEach items= "${contentDisplayForm.productAttributesDisplay.dropDownList}"  var="dropDownDisplayList" >
													
																																			
															<tr>
																<td><c:out value="${dropDownDisplayList.displayName}"/></td>
																<input type="hidden" name="dropdownAttributeNameXpath"  id="dropdownAttributeNameXpath_id<%= i %>"  value="${dropDownDisplayList.attributeName}#${dropDownDisplayList.attributePath}" />															
																<td>
																	<input type="hidden" name="dropdownhidden" id="dropdownhidden_id<%= i %>" value="" />
																	<select style="width:58%; height: 80px;"  multiple="multiple" id="dropDownsId_id<%= i %>" name="dropDownsName_id<%= i %>" onclick="javascript:dropDownValues(dropDownsName_id<%= i %>, <%= i %>, '<c:out value="${contentDisplayForm.productAttributesDisplay.dropDownList.size()}"/>')">
																        
																        <c:forEach var="dropDownMap" items="${dropDownDisplayList.dropDownValuesMap}">												
																			<option value="${dropDownMap.key}"  [b]selected="true"[/b] ><c:out value="${dropDownMap.value}"/></option>              
																		</c:forEach>														
															        </select>	
														        </td>															
																										
															</tr>	
														                                                               
													<% i++; %>
												    </c:forEach>
												    
												     <% int j=1;  %>
												    <c:forEach items= "${contentDisplayForm.productAttributesDisplay.textFieldList}"  var="textFieldDisplayList">
															<tr>
																<td><c:out value="${textFieldDisplayList.displayName}"/></td>
																<td><c:out value="${textFieldDisplayList.attributeFieldValue}"/></td>																
																										
															</tr>	
														                                                               
													<% i++; %>
												    </c:forEach>
												    
												     <c:forEach items= "${contentDisplayForm.productAttributesDisplay.radiobuttonList}"  var="radiobuttonDisplayList">
													
																																			
															<tr>
																<td><c:out value="${radiobuttonDisplayList.displayName}"/></td>
																<td><c:out value="${radiobuttonDisplayList.attributeFieldValue}"/></td>																
																										
															</tr>	
														                                                               
												
												    </c:forEach>
												    
												     <c:forEach items= "${contentDisplayForm.productAttributesDisplay.checkboxList}"  var="checkboxDisplayList">
													
																																			
															<tr>
																<td><c:out value="${checkboxDisplayList.displayName}"/></td>
																<td><c:out value="${checkboxDisplayList.attributeFieldValue}"/></td>																										
															</tr>	
														                                                               
												
												    </c:forEach>
												
												
												
										   </table>		
															
									</div>				
									
						</div>	
						
						<!--Legacy  Attributes Section starts here  -->	
						<div class="cars_panel x-hidden" id="legacyAttributeSection">
							
									<div class="x-panel-header">
										<fmt:message key="sectionLegacyAttribute" bundle="${display}"/>
									</div>
									
									<div class="x-panel-body" style="width:915px;">	
									
												
															
									</div>				
									
						</div>
						
							<!--Pet Content Management Display starts here  -->	
						<div class="cars_panel x-hidden">
							
									<div class="x-panel-header">
									   	<fmt:message key="sectionPetContentManagement" bundle="${display}"/>
										
									</div>
									
									<div class="x-panel-body" style="width:915px;">	
									
											<table class="pep_info" cellspacing="0" cellpadding="0"  style="border: 0px;">
								                 <label><font color="blue">1 Style Level PET found, displaying the  Style Level PET.</font></label>
								            </table>
											<table id="petContentManagementTable" class="table tree2 table-bordered table-striped table-condensed"  border="1" cellspacing="1" cellpadding="1">
													<thead>
														<tr>
															<th id="ext-gen383">ORIN/Grouping Number</th>
															<th id="ext-gen382">Style#</th>
															<th id="ext-gen481">Style Name</th>
															<th id="ext-gen356">Brand</th>
															<th id="ext-gen628">Priority</th>
															<th id="ext-gen628">Default Color</th>																							
														</tr>
													</thead>
													<tr>
														<td><c:out value="${contentDisplayForm.contentManagementDisplay.orinNumber}"/></td>
														<td><c:out value="${contentDisplayForm.contentManagementDisplay.styleNumber}"/></td>
														<td><c:out value="${contentDisplayForm.contentManagementDisplay.styleName}"/></td>
														<td><c:out value="${contentDisplayForm.contentManagementDisplay.brand}"/></td>
														<td><c:out value="${contentDisplayForm.contentManagementDisplay.priority}"/></td>
														<td><c:out value="${contentDisplayForm.contentManagementDisplay.entryType}"/></td>											
													</tr>	
													
											</table>	
															
									</div>				
									
						</div>	
						
							<!--Child SKU Display starts here  -->	
						<div class="cars_panel x-hidden">
							
									<div class="x-panel-header">
										 	<fmt:message key="sectionChildSKU" bundle="${display}"/>
									</div>
									
									<div class="x-panel-body" style="width:915px;">	
									  
											<table class="pep_info" cellspacing="0" cellpadding="0"  style="border: 0px;">
								                 <label><font color="blue"><c:out value="${fn:length(contentDisplayForm.childSkuDisplayList) }"/>  SKU Level Pets found, displaying the  SKU Level Pets.</font></label>
								            </table>
											<table id="childSKUPetTable" class="table tree2 table-bordered table-striped table-condensed"  border="1" cellspacing="1" cellpadding="1">
													
												<c:if test="${fn:length(contentDisplayForm.childSkuDisplayList) > 0}">	
													<thead>
														<tr>
															<th id="ext-gen383">ORIN</th>
												            <th id="ext-gen382">Vendor Name</th>
												            <th id="ext-gen481">Style Name</th>
												            <th id="ext-gen356">Style#</th>
												            <th id="ext-gen628">Color Code</th>
												            <th id="ext-gen628">Color Name</th>
												            <th id="ext-gen628">Size Name</th>																							
														</tr>
													</thead>
													
													

														<c:forEach items= "${contentDisplayForm.childSkuDisplayList}"  var="childSkuDisplayList">
													
																																			
															<tr>
																<td><c:out value="${childSkuDisplayList.orinNumber}"/></td>
																<td><c:out value="${childSkuDisplayList.vendorName}"/></td>
																<td><c:out value="${childSkuDisplayList.styleName}"/></td>
																<td><c:out value="${childSkuDisplayList.styleNumber}"/></td>
																<td><c:out value="${childSkuDisplayList.colorCode}"/></td>
																<td><c:out value="${childSkuDisplayList.colorName}"/></td>
																<td><c:out value="${childSkuDisplayList.sizeName}"/></td>											
															</tr>	
														                                                               
												
													    </c:forEach>
												       
                                                 </c:if>
                                                 <c:if test="${fn:length(contentDisplayForm.childSkuDisplayList) == 0}">
                                                   
                                                     <tr><td>No SKU Data Exists</td></tr>
                                                      
                                                 </c:if>
													
											</table>	
												
															
									</div>				
									
						</div>	
						
								<!--History Display starts here  -->	
						<div class="cars_panel x-hidden">
							
									<div class="x-panel-header">
										 	<fmt:message key="sectionHistory" bundle="${display}"/>
									</div>
									
									<div class="x-panel-body" style="width:915px;">	
									
											<table id="historyID" class="table tree2 table-bordered table-striped table-condensed"  border="1" cellspacing="1" cellpadding="1">																			
												 <thead>
												  <tr>										
													 <th>
														Content Creation Date 
													 </th>
													 <th>
														Created By 
													 </th>
													 <th>
														Last Updated By 
													 </th>
													 <th>
														Content Status
													 </th>
													
													
													
												  </tr>
											   </thead>
											   <tbody>
												  <tr>													
													 <td><c:out value="${contentDisplayForm.contentHistoryDisplay.contentCreatedDate}"/></td>										
													 <td><c:out value="${contentDisplayForm.contentHistoryDisplay.contentCreatedBy}"/></td>
													 <td><c:out value="${contentDisplayForm.contentHistoryDisplay.contentLastUpdatedBy}"/></td>
													 <td><c:out value="${contentDisplayForm.contentHistoryDisplay.contentStatus}"/></td>																				
												  </tr>								 
												</tbody>			
								          </table>		
															
									</div>				
									
						</div>	
						
									<!--Copy Attribute starts here  -->	
						<div class="cars_panel x-hidden">
							
									<div class="x-panel-header">
									 	<fmt:message key="sectionCopyAttribute" bundle="${display}"/>
									</div>
									
									<div class="x-panel-body" style="width:915px;">	
									
									</div>				
									
						</div>				
							
					</form:form>
				</div>
		</div>
		
	
   

