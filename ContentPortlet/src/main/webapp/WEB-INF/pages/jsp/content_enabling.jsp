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
            	
            	//Logic for disabling the Product Name , Product Description Text  Area ,Style Submit button ,omni channel ,car brand
            	// when the Style Pet Status is completed or closed
            	
            	  var  pepUserRoleName = document.getElementById("roleNameId").value;
            	
            	  alert("pepUserRoleName---"+pepUserRoleName)

                  var stylePetContentStatus=    $(".contentStatusId").html();    
            	  
            	  if(pepUserRoleName == "vendor")
        		  {
        		  
        		  		alert("if loop is true---"+pepUserRoleName);
	            		  //Logic for disabling the Product Name , Product Description Text  Area ,Style Color  Submit button ,omni channel ,car brand
	                      //when the Style  Pet Status is ready for review for vendor
	                      
	                      alert("stylePetContentStatus---"+stylePetContentStatus);
	                      
	                       if(stylePetContentStatus=='Ready_For_Review' || stylePetContentStatus=='Closed' || styleColorContentPetStatus=='Completed')
	            		{
		            		  $('#txt_outfitName').attr("disabled", "disabled"); 
		            		  
		            		  $('#vendorStyleId').attr("disabled", "disabled");  
		            		  
		            		  $('#styleSubmit').attr("disabled", "disabled"); 	            		  
		            		  
		            		  $('#omnichannelbrand').attr("disabled", "disabled");  
		            		  
		            		  $('#carbrand').attr("disabled", "disabled"); 
		            		  
		            		  $('#iphCategoryDropDownId').attr("disabled", "disabled"); 
		            		  
		            		  $('#saveButtonId').attr("disabled", "disabled");       		  
		            		  
		            		  
	            		}
	                       
	                       
	                       alert("styleColorApproveButtonUniqueId--"+styleColorApproveButtonUniqueId);
	                       //styleColorApproveButtonUniqueId
	                 	  var styleColorContentPetStatus = "";
	     	      			for(i=1; i<=styleColorRowCount; i++){
	     	      				

	     	      				 //alert("i--"+i);
	     	      				 //alert("petColorRow_id--"+"#petColorRow_id"+(i));
	     	      				
	     	      				//var styleColorPetContentStatus = $("#petColorRow_id"+(i)).val();
	     	      				//alert("styleColorPetContentStatus---"+styleColorPetContentStatus);
	     	      				 
	     	      				var styleColorContentPetStatus= $(".petColorRowClass"+(i)).text();
	     	      				alert("styleColorContentPetStatus--"+styleColorContentPetStatus);
	     	      				
	     	      				var approveButtonStatus= $(".petColorApproveButtonClass"+(i)).text();
	     	      				 alert("approveButtonStatus--"+approveButtonStatus);
	     	      				 
	     	      				 
	     	      				if(styleColorContentPetStatus=="Ready_For_Review")
		     	           		{
		     		            		 alert("you are in if style color loop---");
		     	            		      $('#txt_outfitName').attr("disabled", "disabled"); 		            		  
		     		            		  $('#vendorStyleId').attr("disabled", "disabled"); 		            		  
		     		            		  $('#styleSubmit').attr("disabled", "disabled");         		  
		     		            		  $('#omnichannelbrand').attr("disabled", "disabled");         		  
		     		            		  $('#carbrand').attr("disabled", "disabled");             		  
		     		            		  $('#a_drop_down_box').attr("disabled", "disabled");              		  
		     		            		  $('#secondaryColorId').attr("disabled", "disabled");          		  
		     		            		  $('#secondaryColorId2').attr("disabled", "disabled"); 		            		  
		     		            		  $('#secondaryColorId3').attr("disabled", "disabled");       		  
		     		            		  $('#secondaryColorId4').attr("disabled", "disabled"); 	            		  
		     		            		  $('#omniChannelColorDescriptionId').attr("disabled", "disabled");        		  
		     		            		  $('#vendorColorId').attr("disabled", "disabled"); 	
		     		            		  $('#saveButtonId').attr("disabled", "disabled"); 		            		  
		     		            		  $('#styleColorApproveButtonUniqueId').attr("disabled", "disabled"); 
		     		            		  
		     		            		  
		     		            		  
		     	           		}
	     	      				
	     	      				
	     	      				
	     	      			}   	  
	                 	
	                 	   
	     	      		  
	                 	   
        		
        		  
        		  
        		  
        		  
        		  }
        	 
            	//End of logic for vendor
            	
            	 // alert("stylePetContentStatus----"+stylePetContentStatus);
            	
            	  if(stylePetContentStatus=='Completed' || stylePetContentStatus=='Closed')
            		{
	            		  $('#txt_outfitName').attr("disabled", "disabled"); 
	            		  
	            		  $('#vendorStyleId').attr("disabled", "disabled");  
	            		  
	            		  $('#styleSubmit').attr("disabled", "disabled"); 	            		  
	            		  
	            		  $('#omnichannelbrand').attr("disabled", "disabled");  
	            		  
	            		  $('#carbrand').attr("disabled", "disabled"); 
	            		  
	            		  $('#iphCategoryDropDownId').attr("disabled", "disabled"); 
	            		  
	            		  $('#saveButtonId').attr("disabled", "disabled"); 
	            		  
	            		  
	            		  
	            		  
	            		  
            		}

					//End of Logic
            	 

            	 //Logic for disabling the Product Name , Product Description Text  Area ,Style Color  Submit button ,omni channel ,car brand
            	 //when the Style Color Pet Status is completed or closed
            	             
                  var styleColorRowCount = document.getElementById("styleColorCountId").value;                  
            	  
            	  alert("styleColorRowCount--"+styleColorRowCount);
                  
                  var styleColorApproveButtonUniqueId = document.getElementById("styleColorApproveButtonId").value;
                  document.getElementById(styleColorApproveButtonUniqueId).disabled = true;

                  
                  
                  
                  alert("styleColorApproveButtonUniqueId--"+styleColorApproveButtonUniqueId);
                  //styleColorApproveButtonUniqueId
            	  var styleColorContentPetStatus = "";
	      			for(i=1; i<=styleColorRowCount; i++){
	      				

	      				 //alert("i--"+i);
	      				 //alert("petColorRow_id--"+"#petColorRow_id"+(i));
	      				
	      				//var styleColorPetContentStatus = $("#petColorRow_id"+(i)).val();
	      				//alert("styleColorPetContentStatus---"+styleColorPetContentStatus);
	      				 
	      				var styleColorContentPetStatus= $(".petColorRowClass"+(i)).text();
	      				alert("styleColorContentPetStatus--"+styleColorContentPetStatus);
	      				
	      				var approveButtonStatus= $(".petColorApproveButtonClass"+(i)).text();
	      				 alert("approveButtonStatus--"+approveButtonStatus);
	      				
	      				
	      				
	      			}   	  
            	
            	  
            	  
            	  
            	   if(styleColorContentPetStatus=='Completed' || styleColorContentPetStatus=='Closed')
	           		{
		            		 alert("you are in if loop---");
	            		      $('#txt_outfitName').attr("disabled", "disabled"); 		            		  
		            		  $('#vendorStyleId').attr("disabled", "disabled"); 		            		  
		            		  $('#styleSubmit').attr("disabled", "disabled");         		  
		            		  $('#omnichannelbrand').attr("disabled", "disabled");         		  
		            		  $('#carbrand').attr("disabled", "disabled");             		  
		            		  $('#a_drop_down_box').attr("disabled", "disabled");              		  
		            		  $('#secondaryColorId').attr("disabled", "disabled");          		  
		            		  $('#secondaryColorId2').attr("disabled", "disabled"); 		            		  
		            		  $('#secondaryColorId3').attr("disabled", "disabled");       		  
		            		  $('#secondaryColorId4').attr("disabled", "disabled"); 	            		  
		            		  $('#omniChannelColorDescriptionId').attr("disabled", "disabled");        		  
		            		  $('#vendorColorId').attr("disabled", "disabled"); 	
		            		  $('#saveButtonId').attr("disabled", "disabled"); 		            		  
		            		  $('#styleColorApproveButtonUniqueId').attr("disabled", "disabled"); 
		            		  
		            		  
		            		  
	           		}
	            	  
            	  
            	  
            	  
            	  //End of logic for disabling
            	      
            	 //Logic for disabling the Product Name , Product Description Text  Area ,Style Color  Submit button ,omni channel ,car brand
               	 //when the Style  Pet Status is ready for review for vendor
            		   
            	 //Logic for disabling the Product Name , Product Description Text  Area ,Style Color  Submit button ,omni channel ,car brand
            	 //when the Style Color Pet Status is ready for review for vendor
            	 
            	  var  pepUserRoleName = document.getElementById("roleNameId").value;
            	  
            	   alert("pepUserRoleName--"+pepUserRoleName);
            	  
            	 
            	  
            	  
            	  //End of logic for disabling
            	  
            	  //Retain the value entered in the field   logic  when the IPH drop down changes
            	
            		var selectedCategoryID='${requestScope.selectedCategory}';
            			selectedCategoryID = selectedCategoryID.trim();

            			if (typeof selectedCategoryID !== "undefined" && selectedCategoryID !== null && selectedCategoryID!='' ) {   
            			$("#iphCategoryDropDownId").val("${requestScope.selectedCategory}").attr('selected', 'selected');
            			} 
            			
            	//Logic for highlighting the selected omnichannel brand name 	in its drop  down  on change of IPH Drop Down	
            	 var selectedOmnichannelbrand='${requestScope.selectedOmnichannelbrand}';
            	// alert("selectedOmnichannelbrand...."+selectedOmnichannelbrand);
            			if (typeof selectedOmnichannelbrand !== "undefined" && selectedOmnichannelbrand !== null && selectedOmnichannelbrand!='' ) {   
                			$("#omnichannelbrand").val("${requestScope.selectedOmnichannelbrand}").attr('selected', 'selected');
                		} 		
            	
            			//Logic for highlighting the selected car brand name 	in its drop  down  on change of IPH Drop Down	
               	 var selectedCarbrand='${requestScope.selectedCarbrand}';
                  //  alert("selectedCarbrand...."+selectedCarbrand);
                      		if (typeof selectedCarbrand !== "undefined" && selectedCarbrand !== null && selectedCarbrand!='' ) {   
                          			$("#carbrand").val("${requestScope.selectedCarbrand}").attr('selected', 'selected');
                      		} 		
                          						
            			
                		
                		
            			
            	//Logic for highlighting the selected product name	in text area on change of IPH Drop Down	
            	 var selectedProductName='${requestScope.selectedProductName}';
            	// alert("selectedProductName...."+selectedProductName);
            			if (typeof selectedProductName !== "undefined" && selectedProductName !== null && selectedProductName!='' ) {   
                			$("#txt_outfitName").val("${requestScope.selectedProductName}");
                		} 
            			
            			
            	//Logic for highlighting the selected product name in text area	on change of IPH Drop Down	
                   	 var selectedProductDescription='${requestScope.selectedProductDescription}';
                   	// alert("selectedProductDescription...."+selectedProductDescription);
                   			if (typeof selectedProductDescription !== "undefined" && selectedProductDescription !== null && selectedProductDescription!='' ) {   
                       			$("#vendorStyleId").val("${requestScope.selectedProductDescription}");
                       		} 
                   					
               	//Logic for highlighting the selected omni channel color family in its  drop down on change of IPH Drop Down	
               	
               	 var selectedOmniColorFamily='${requestScope.selectedOmniColorFamily}';
                   //	 alert("selectedOmniColorFamily...."+selectedOmniColorFamily);
                   			if (typeof selectedOmniColorFamily !== "undefined" && selectedOmniColorFamily !== null && selectedOmniColorFamily!='' ) {   
                       			$("#a_drop_down_box").val("${requestScope.selectedOmniColorFamily}").attr('selected', 'selected');
                       		} 
                   			
                //Logic for highlighting the selected secondaryColor1 in its  drop down on change of IPH Drop Down	
                           	
                 	 var selectedSecondaryColor1='${requestScope.selectedSecondaryColor1}';
                              	// alert("selectedSecondaryColor1...."+selectedSecondaryColor1);
                              			if (typeof selectedSecondaryColor1 !== "undefined" && selectedSecondaryColor1 !== null && selectedSecondaryColor1!='' ) {   
                                  			$("#secondaryColorId").val("${requestScope.selectedSecondaryColor1}").attr('selected', 'selected');
                	} 
                              			
                  //Logic for highlighting the selected secondaryColor2 in its  drop down on change of IPH Drop Down	
                                       	
                  	 var selectedSecondaryColor2='${requestScope.selectedSecondaryColor2}';
                             	// alert("selectedSecondaryColor2...."+selectedSecondaryColor2);
                            if (typeof selectedSecondaryColor2 !== "undefined" && selectedSecondaryColor2 !== null && selectedSecondaryColor2!='' ) {   
                      $("#secondaryColorId2").val("${requestScope.selectedSecondaryColor2}").attr('selected', 'selected');
                   	}  
                            
                            
              //Logic for highlighting the selected secondaryColor3 in its  drop down on change of IPH Drop Down	
                           	
                var selectedSecondaryColor3='${requestScope.selectedSecondaryColor3}';
                                    	// alert("selectedSecondaryColor3...."+selectedSecondaryColor3);
                                   if (typeof selectedSecondaryColor3 !== "undefined" && selectedSecondaryColor3 !== null && selectedSecondaryColor3!='' ) {   
                             $("#secondaryColorId3").val("${requestScope.selectedSecondaryColor3}").attr('selected', 'selected');
                   	}   
                                   
                                   
              //Logic for highlighting the selected secondaryColor4 in its  drop down on change of IPH Drop Down	
                                  	
                 var selectedSecondaryColor4='${requestScope.selectedSecondaryColor4}';
                                                       	// alert("selectedSecondaryColor4...."+selectedSecondaryColor4);
                              if (typeof selectedSecondaryColor4 !== "undefined" && selectedSecondaryColor4 !== null && selectedSecondaryColor4!='' ) {   
                                                $("#secondaryColorId4").val("${requestScope.selectedSecondaryColor4}").attr('selected', 'selected');
                         	}                            			
                                                            	
           //Logic for highlighting the selected Omni Channel Color Description in its  drop down on change of IPH Drop Down	
                            	
                 var selectedOmniChannelColorDescription='${requestScope.selectedOmniChannelColorDescription}';
                         	// alert("selectedOmniChannelColorDescription...."+selectedOmniChannelColorDescription);
                   if (typeof selectedOmniChannelColorDescription !== "undefined" && selectedOmniChannelColorDescription !== null && selectedOmniChannelColorDescription!='' ) {   
                                  $("#omniChannelColorDescriptionId").val("${requestScope.selectedOmniChannelColorDescription}");
                    	}                            			
        //Logic for highlighting the selected selectedVendorColor in its  drop down on change of IPH Drop Down	
               	
          var selectedVendorColor='${requestScope.selectedVendorColor}';
                           //	 alert("selectedVendorColor...."+selectedVendorColor);
                  if (typeof selectedVendorColor !== "undefined" && selectedVendorColor !== null && selectedVendorColor!='' ) {   
                                    $("#vendorColorId").val("${requestScope.selectedVendorColor}");
         	}                                                                  			    			
            			  
                   			

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
            
            
            function bmDropDownValues(selectedDropdwonValue, selectedDropDown, totalDropDwons){
            	//alert(" -"+selectedDropdwonValue.value +"-" +selectedDropDown+"-"+totalDropDwons);
            	
            	 var list =  document.forms['contentDisplayForm'].elements["bmDropDownsId_id"+selectedDropDown+""];
            	 	//alert("2" +list);
            	 var attNameAndPath = document.getElementById("blueMartiniDropDownAttributeNameXpath_id"+(selectedDropDown)).value;
            	  
            	// alert(attNameAndPath); 
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
                     //alert(finalVal);
                     	var exisVal = document.getElementById("blueMartiniDropDownHidden_id"+(selectedDropDown)).value;
                     	document.getElementById("blueMartiniDropDownHidden_id"+(selectedDropDown)).value = finalVal;
                     	//alert(document.getElementById("blueMartiniDropDownHidden_id"+(selectedDropDown)).value);
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
	    	//alert('updateContentPetStyleDataStatus');
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
	 	
	 	
	       
            
	function getDynamicAttributes(form)
		{ 
			//alert("hello dynamic");  
		    var values = document.getElementById("iphCategoryDropDownId");
	        var selectedVal = values.options[values.selectedIndex].value;
		   if(selectedVal !='select'){			   
	         document.forms['contentDisplayForm'].dynamicCategoryId.value = selectedVal;	
           	
	          document.forms['contentDisplayForm'].submit();  
		}         
	                           
	 	
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
		
		
		//on click of the style Color orin number hyperlink get the style Color attributes
		function getStyleColorAttributes(url,selectedOrinNumber){	
			//alert("about to make ajax call.....for getting  the getStyleColorAttributes...");
			var ajaxCall=  $.ajax({
				  
		        url: url,
		        type: 'GET',
		       // dataType: "json",		
		        cache:false,			        
		        data: { 
		        	   styleColorOrinNumber:selectedOrinNumber		        	  
					   },
				   
		            
		            	 success: function(data){
		            		 
		            		 var obj = $.parseJSON(data);//parsing the json data
		            		 
		            		// alert("style color attribute obj..."+obj);
		            		 
		            		// alert("style color attribute obj..styleColorObjectInfo."+obj.styleColorObjectInfo);
		            		 
		            		// alert("style color attribute obj..styleColorObjectInfo.colorFamilyCode"+obj.styleColorObjectInfo.colorFamilyCode);
		            		   	
		                     setStyleColorAttributeValue(obj.styleColorObjectInfo.colorFamilyCode,obj.styleColorObjectInfo.secondaryColorOne,obj.styleColorObjectInfo.secondaryColorTwo,obj.styleColorObjectInfo.secondaryColorThree, obj.styleColorObjectInfo.secondaryColorFour,obj.styleColorObjectInfo.nrfColorCode,obj.styleColorObjectInfo.omniChannelColor,obj.styleColorObjectInfo.nrfColorDescription);
		             },
		             error: function(XMLHttpRequest, textStatus, errorThrown){
                     	//alert("textStatus.."+textStatus);
                     	//alert("errorThrown.."+errorThrown);
 	                	
		            	  $("#ajaxResponseStyleColorAttribute").append(" " + obj.styleColorObjectInfo.message + "<br>");	   	                
 	                  
 	                  }
                
		   
		    });
		}
		

		
		//function to populate the Style Color Section
		
		 function setStyleColorAttributeValue(omniChannelColorFamilyValue,secondaryColor1Value,secondaryColor2Value,secondaryColor3Value,secondaryColor4Value,nrfColorCodeValue,omniChannelColorDescriptionValue,vendorColorValue)
		 {
			               
               var number = document.getElementById('a_drop_down_box');
               selectItemByValue(number,omniChannelColorFamilyValue);
               var  secondaryColorSelect = document.getElementById("secondaryColorId");
               selectItemByValue(secondaryColorSelect,secondaryColor1Value);
               
               var  secondaryColorId2Select = document.getElementById("secondaryColorId2");
               selectItemByValue(secondaryColorId2Select,secondaryColor2Value);
            
               
               var  secondaryColorId3Select = document.getElementById("secondaryColorId3");
               selectItemByValue(secondaryColorId3Select,secondaryColor3Value);
             
               var  secondaryColorId4Select = document.getElementById("secondaryColorId4");
               selectItemByValue(secondaryColorId4Select,secondaryColor4Value);        


			   document.getElementById("nrfColorCodeId").value=nrfColorCodeValue;
				
			   document.getElementById("omniChannelColorDescriptionId").value=omniChannelColorDescriptionValue;
	
			   document.getElementById("vendorColorId").value = vendorColorValue;

			 
			 
			
		 }

			//function to set the drop down saved value originating from ajax call 
		 function selectItemByValue(elmnt, value){

			    
			    for(var i=0; i < elmnt.options.length; i++)
			    {
			    	
			      if(elmnt.options[i].value == value)
			    	  {
			    	   
			            elmnt.selectedIndex = i;
			            
			    	  }
			    }
			  }
		
		
		
		//on click of the style orin number hyperlink get the style  attributes
		function getStyleAttributes(url,selectedOrinNumber){	
			//alert("about to make ajax call.....for getting  the getStyleAttributes...");
			var ajaxCall=  $.ajax({
				  
		        url: url,
		        type: 'GET',
		        dataType: "json",		
		        cache:false,			        
		        data: { 
		        	   styleOrinNumber:selectedOrinNumber		        	  
					   },
				   
		            
		            	 success: function(data){
		            		  // alert("data.."+data);
		            		 
		            		 var result = data.toString();
		            		 //alert("result.."+result);
		            		 
		            		 if (result !='')
		            			 {
		            			   // alert("result.loop."+result);
			                         $("#ajaxResponseStyleAttribute").html("");			                         
			                         $("#ajaxResponseStyleAttribute").append("<b>Style Orin Number :</b> " + data.styleAttributeObject.styleOrinNumber + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>Long Description :</b> " + data.styleAttributeObject.longDescription + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>Launch Date :</b> " + data.styleAttributeObject.launchDate + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>Belk Exclusive  :</b> " + data.styleAttributeObject.belkExclusive + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>Channel Exclusive :</b> " + data.styleAttributeObject.channelExclusive + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>SDF :</b> " + data.styleAttributeObject.sdf+ "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>Bopis :</b> " +data.styleAttributeObject.bopis + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>Import/Domestic:</b> " +data.styleAttributeObject.importDomestic + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>Gift Box :</b> " +data.styleAttributeObject.giftBox + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>GWP :</b> " +data.styleAttributeObject.gwp + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>PWP :</b> " +data.styleAttributeObject.pwp + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>PYG :</b> " +data.styleAttributeObject.pyg + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>Product Dimension UOM :</b> " +data.styleAttributeObject.productDimesionUOM + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>Product Length :</b> " +data.styleAttributeObject.length + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>Product Width:</b> " +data.styleAttributeObject.width + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>Product Height :</b> " +data.styleAttributeObject.height + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>Product Weight UOM :</b> " +data.styleAttributeObject.weightUOM + "<br>");
			                         $("#ajaxResponseStyleAttribute").append("<b>Product Weight :</b> " +data.styleAttributeObject.weight + "<br>");
		            			 }
		            		 else
		            			 {
		            			  $("#ajaxResponseStyleAttribute").html("");
			                         $("#ajaxResponseStyleAttribute").append("<b>No Global Attribute Data Exists for the Style Orin Number</b> " +  "<br>");
		            			 }
							
		            	
		            	
		        },
                
		   
		    });
		}
	
   
		//on click of the Style Data row submit button ,pass the style orin number ,style pet status and logger in user,role Name to the update content pet statuswebservice
		var  styleContentPetWebserviceResponseFlag = "false";
		var  styleColorContentPetWebserviceResponseFlag="false";
		function getUpdateStyleContentPetStatusWebserviceResponse(url,stylePetOrinNumber,stylePetContentStatus,user,roleName){	
			
			var response =  confirm("Have you saved the Content Pet Attributes? ");
			if(response == false){
				return false;
		 	}
			//alert("roleName"+roleName);
			if(roleName=='dca')
			{
				stylePetContentStatus='02' // Set Style Content Pet status to Completed when  dca approves the Pet
				//alert("stylePetContentStatus.."+stylePetContentStatus);
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
                                  $('.contentStatusId').html('Completed');								  
                                  styleContentPetWebserviceResponseFlag="true";
                                 
                                  
                        },
                        
                        error: function(XMLHttpRequest, textStatus, errorThrown){
                        	//alert("textStatus.."+textStatus);
                        	//alert("errorThrown.."+errorThrown);
    	                	
    	                	$("#ajaxResponseUpdateStylePetContentStatus").html("");
    	                	$("#ajaxResponseUpdateStylePetContentStatus").append("<b>Style Pet Status could not be updated ,contact System Administrator. </b> <br>"); 
    	                    $("#ajaxResponseUpdateStylePetContentStatus").append("<br>");
    	                    $("#saveButtonId").removeAttr("disabled");
    	                    $("#styleSubmit").removeAttr("disabled");    
    	                    styleContentPetWebserviceResponseFlag="false";
    	                  
    	                  }
           
            
             });

			}
			else if(roleName=='vendor')
			{
				stylePetContentStatus='08'; // Set Style Content Pet status to Ready For Review when  vendor submits the Pet
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
								  $('.contentStatusId').html('Ready For Review');
								  styleContentPetWebserviceResponseFlag="true";
                                 
                                  
                        },
                        
                        error: function(XMLHttpRequest, textStatus, errorThrown){
                        	//alert("textStatus.."+textStatus);
                        	//alert("errorThrown.."+errorThrown);
    	                	
    	                	$("#ajaxResponseUpdateStylePetContentStatus").html("");
    	                	$("#ajaxResponseUpdateStylePetContentStatus").append("<b>Style Pet Status could not be updated ,contact System Administrator. </b> <br>"); 
    	                    $("#ajaxResponseUpdateStylePetContentStatus").append("<br>");
    	                    $("#saveButtonId").removeAttr("disabled");
    	                    $("#styleSubmit").removeAttr("disabled");  
    	                    styleContentPetWebserviceResponseFlag="false";
    	                  
    	                  }
           
            
             });

			}
			
			
			
		}
		
		
	   
	 //on click of the Style Color  Data row submit button ,pass the style color orin number ,styleColor  pet status and logger in user to the update content pet style color  status by caling webservice
		function getUpdateStyleColorContentPetStatusWebserviceResponse(url,styleColorPetOrinNumber,styleColorPetContentStatus,user,roleName){

			if(roleName=='dca')
			{
				styleColorPetContentStatus='02' // Set Style Color  Content Pet status to Completed when  dca approves the Pet
				//alert("styleColorPetContentStatus.."+styleColorPetContentStatus);
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
								 $("#contentStatusId-"+styleColorPetOrinNumber).html('Completed');
								 
                                 
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
			else if(roleName=='vendor')
			{
				
				styleColorPetContentStatus='08'; // Set Style Color Content Pet status to be Ready For Review  when  vendor submits the Pet
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
								 $("#contentStatusId-"+styleColorPetOrinNumber).html('Ready_For_Review');
								 
                                 
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
		
			

			
		}
		
	 
		
		 //on click of the Save button ,pass content pet attributes and make a ajax call to the  webservice
		function saveContentPetAttributesWebserviceResponse(url,stylePetOrinNumber,user,dropdownCount,bmDropDownCount){	
			
			var  productName= document.getElementById("txt_outfitName").value;
			//alert("txt_outfitName1..."+productName);
			
			var  productDescription= document.getElementById("vendorStyleId").value;
			
			//alert("productDescription..."+productDescription);
			
		
			
			//Logic for capturing the Style Color Attributes Value
			var  selectedStyleColorOrinNumber= $("#selectedStyleColorOrinNumber").val();
			//alert("selectedStyleColorOrinNumber.."+selectedStyleColorOrinNumber);			
			//var  a_drop_down_box=   $("#a_drop_down_box").val();			
			//alert("a_drop_down_box.."+a_drop_down_box);			
			//var  a_drop_down_box_selected_drop_down= $("#a_drop_down_box:selected").text();	
			
			//var  a_drop_down_sel=document.getElementById("a_drop_down_box").selectedIndex;
			//alert("a_drop_down_sel.."+a_drop_down_sel);	
			
			//var  selectedColorFamily12=  document.getElementsByTagName("option")[a_drop_down_sel].value;
			//alert("selectedColorFamily12.."+selectedColorFamily12);	
			
			
			//Logic for selecting the omni color family from the drop down and passing the selected value to ajax and from ajax to the controller
			var a_drop_down_sel= document.getElementById("a_drop_down_box");
			var omniFamilyOptions = a_drop_down_sel.options[a_drop_down_sel.selectedIndex].value;
			//alert("omniFamilyOptions.."+omniFamilyOptions);
			
			

			//Logic for selecting the secondary color 1 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColor_sel= document.getElementById("secondaryColorId");
			// alert("secondaryColor_sel.."+secondaryColor_sel);
			var secondaryColorOptions = secondaryColor_sel.options[secondaryColor_sel.selectedIndex].value;
			//alert("secondaryColorOptions.."+secondaryColorOptions);
			
			//Logic for selecting the secondary color 2 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColorId2_sel= document.getElementById("secondaryColorId2");
			var secondaryColorId2Options = secondaryColorId2_sel.options[secondaryColorId2_sel.selectedIndex].value;
			//alert("secondaryColorId2Options.."+secondaryColorId2Options);
			
			//Logic for selecting the secondary color 3 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColorId3_sel= document.getElementById("secondaryColorId3");
			var secondaryColorId3Options = secondaryColorId3_sel.options[secondaryColorId3_sel.selectedIndex].value;
			//alert("secondaryColorId3Options.."+secondaryColorId3Options);
			
			
			//Logic for selecting the secondary color 4 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColorId4_sel= document.getElementById("secondaryColorId4");
			var secondaryColorId4Options = secondaryColorId4_sel.options[secondaryColorId4_sel.selectedIndex].value;
			//alert("secondaryColorId4Options.."+secondaryColorId4Options);
				
				
			
			var  nrfColorCodeId=   $("#nrfColorCodeId").val();			
			var  omniChannelColorDescriptionId=   $("#omniChannelColorDescriptionId").val();			
			var  vendorColorId=   $("#vendorColorId").val();			
			//alert("vendorColorId.."+vendorColorId);
			

			

			

			

			

			

			
			//Logic for passing selected multi select product attribute drop downs ,its value and  the xpath
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
		//	alert("pafinalString"+finalString);
			
	        // For Blue Martini values
            var bmFinalString = "";
             for(i=1; i<(bmDropDownCount+1); i++){ 
                      var temp = $("#blueMartiniDropDownHidden_id"+(i)).val();
                      if(!(temp == null || temp =="")){ 
                                if(bmFinalString==null || bmFinalString==""){
                                          bmFinalString = temp;
                                }else{
                                          bmFinalString = bmFinalString +"~" + temp;
                                }
                      }
                      
             } 
             //alert("bmFinalString----"+bmFinalString);

			
          // Get Text Field Values
             // Text Field
             if(document.getElementById("paTextAttributeCount")){
				var textFieldCount = document.getElementById("paTextAttributeCount").value;
				for(var a=0; a<textFieldCount; a++){
				    var textFieldAttNameAndPath = document.getElementById("paTextAttributeXpath_id"+(a+1)).value;
				    var userEnteredValue = document.getElementById("paText_Id"+(a+1)).value;
				    var temp  = textFieldAttNameAndPath +"#"+userEnteredValue 
				    document.getElementById("textFieldHidden_id"+(a+1)).value = temp;
				     //alert(document.getElementById("textFieldHidden_id"+(a+1)).value);
				}
             }
             
		  var finalTextValueString = "";
		  for(j=1; j<(textFieldCount+1); j++){
           var temp = $("#textFieldHidden_id"+(j)).val();
           if(!(temp == null || temp =="")){ 
                     if(finalTextValueString==null || finalTextValueString==""){
                               finalTextValueString = temp;
                     }else{
                               finalTextValueString = finalTextValueString +"~" + temp;
                     }
           }
           }
		  
		 //alert("PA Final Text String "+finalTextValueString);
		  
		  
		  //Get the Blue Martini Attribute Text Field Values
		  
		  if(document.getElementById("bmTextAttributeCount")){
			 var bmTextFieldCount = document.getElementById("bmTextAttributeCount").value;
			  //alert("bmTextFieldCount.."+bmTextFieldCount);
				for(var a=0; a<bmTextFieldCount; a++){
				    var textFieldAttNameAndPath = document.getElementById("blueMartiniTextAttributeNameXpath_id"+(a+1)).value;
				    //alert("bmTextFieldAttNameAndPath.."+textFieldAttNameAndPath);
				    var userEnteredValue = document.getElementById("blueMartiniText_Id"+(a+1)).value;
				    var temp  = textFieldAttNameAndPath +"#"+userEnteredValue 
				    //alert("bmtemp.."+temp);
				    document.getElementById("bmTextFieldHidden_id"+(a+1)).value = temp;
				//   alert(document.getElementById("textFieldHidden_id"+(a+1)).value);
				}
			  }
           
		  var bmfinalTextValueString = "";
		  for(j=1; j<(bmTextFieldCount+1); j++){
         var temp = $("#bmTextFieldHidden_id"+(j)).val();
         if(!(temp == null || temp =="")){ 
                   if(bmfinalTextValueString==null || bmfinalTextValueString==""){
                	   bmfinalTextValueString = temp;
                   }else{
                	   bmfinalTextValueString = bmfinalTextValueString +"~" + temp;
                   }
         }
         }
		  
		  
			 // Added by Sriharsha
		     var onminvalue =  $("#selectedOmniBrand").val();
		     var carsvalue =  $("#selectedCarsBrand").val();
		     // End Added by Sriharsha
		     
		     
		     //Added to capture the entry type as complex pack ,pack color,style ,style color
		     
		     
		      var complexPackEntry =  $("#complexPackEntryId").val();
		      //alert("complexPackEntry..."+complexPackEntry);
		      var packColorEntry =  $("#packColorEntryId").val();
		      //alert("packColorEntry..."+packColorEntry);
		      var styleEntry=$("#styleEntryId").val();		
		     // alert("styleEntry..."+styleEntry);
		      var styleColorEntry=$("#styleColorEntryId").val();
		     // alert("styleColorEntry..."+styleColorEntry);
		     
		      // End Added  of the logic capture the entry type as complex pack ,pack color,style ,style color
		     
		     
		     
		     
		     
		     
		  //Make the ajax call
			
			
			 var ajaxCall=  $.ajax({
                
                url: url,
                type: 'GET',
                dataType: "json",         
                cache:false,                      
                data: { 
                	      stylePetOrinNumber:stylePetOrinNumber,
                	      productName:productName,
                	      productDescription:productDescription,
                          loggedInUser:user,
                          dropdownhidden_id1:finalString,
						  blueMartiniDropdownhidden_id1:bmFinalString,
						  textFieldsValues:finalTextValueString,
						  bmTextFieldsValues:bmfinalTextValueString,
                          styleColorPetOrinNumber:selectedStyleColorOrinNumber,
                          omniColor:omniFamilyOptions,
                          secondaryColorStyleColorAttribute:secondaryColorOptions,
                          secondaryColorId2StyleColorAttribute:secondaryColorId2Options,
                          secondaryColorId3StyleColorAttribute:secondaryColorId3Options,
                          secondaryColorId4StyleColorAttribute:secondaryColorId4Options,
                          nrfColorCodeStyleColorAttribute:nrfColorCodeId,                          
                          omniChannelColorDescriptionStyleColorAttribute:omniChannelColorDescriptionId,                      
                          vendorColorIdStyleColorAttribute:vendorColorId,
                          selectedOmniBrand:onminvalue, // Added by Sriharsha
                          selectedCarsBrand:carsvalue,  // Added by Sriharsha
                          complexPackReq:complexPackEntry,
                          packColorReq:packColorEntry,
                          styleReq:styleEntry,
                          styleColorReq:styleColorEntry
                          
                                            
                       },
                        
                      
                       success: function(data){  
                    	  
                               
                              $("#ajaxResponseSaveContentPetAttribute").html("");
                              $("#ajaxResponseSaveContentPetAttribute").append("<b>"+data.responseObject.message+"!</b>");                             
                                 
                                 
                       },
                       
                       error: function(XMLHttpRequest, textStatus, errorThrown){              	   
   	                	
	   	                	$("#ajaxResponseSaveContentPetAttribute").html("");
	   	                	//$("#ajaxResponseSaveContentPetAttribute").append("<b>Content Pet Attributes  could not be saved ,contact System Administrator. </b> <br>"); 
	   	                	$("#ajaxResponseSaveContentPetAttribute").append("<b>Content Pet Attributes  Saved Successfully!. </b> <br>"); 
	   	                    $("#ajaxResponseSaveContentPetAttribute").append("<br>");
	   	                   
   	                  
   	                  }
          
           
            });

			
		}
		 
		 
		 
		// Added by Sriharsha
		function getSelectedOmniBrand(selectedOption){
			//alert("omni brand selectedOption-----"+selectedOption);
			if(selectedOption != null ) {
				document.getElementById("selectedOmniBrand").value = selectedOption.value;  
			}
		}

		function getSelectedOCarsBrand(selectedOption){
			//alert("car brand selectedOption-----"+selectedOption);
			if(selectedOption != null ) {
				document.getElementById("selectedCarsBrand").value = selectedOption.value;  
			}
		}
		
		function retainSelected(){
			var omnival  = document.getElementById("selectedOmniBrand").value;  
			if(!(omnival == null && omnival == '')){
				var omniList = document.forms['contentDisplayForm'].elements["omnichannelbrand"];
				for(var i = 0; i < omniList.options.length; i++) {
				     if(omniList.options[i].value == value){
				    	 omniList.options[i].value = value;
					 } 
				}
			}
			var carval  = document.getElementById("selectedOmniBrand").value;
			if(!(carval == null && carval == '')){
				var carList = document.forms['contentDisplayForm'].elements["carbrand"];
				for(var i = 0; i < carList.options.length; i++) {
				     if(carList.options[i].value == value){
				    	 carList.options[i].value = value;
					 } 
				}
			}
		}
		
	    // End by Sriharsha
	    
	    
	    
	    //Added by Sudha
	    
	    
		function getSelectedOmniColorFamily(selectedOption){
			if(selectedOption != null ) {
				document.getElementById("a_drop_down_box").value = selectedOption.value;  
			}
		}

		function getSelectedSecColorOne(selectedOption){
			if(selectedOption != null ) {
				document.getElementById("secondaryColorId").value = selectedOption.value;  
			}
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
	<portlet:actionURL var="getChangedIPHCategoryData"> 
				<portlet:param name="action" value="getChangedIPHCategoryData"/>
		</portlet:actionURL>
		
		
		
		<portlet:resourceURL var="updateContentPetStyleDataStatus" id="updateContentPetStyleDataStatus"></portlet:resourceURL>
		<portlet:resourceURL var="updateContentPetStyleColorDataStatus" id="updateContentPetStyleColorDataStatus"></portlet:resourceURL>
		<portlet:resourceURL var="saveContentPetAttributes" id="saveContentPetAttributes"></portlet:resourceURL>	
		
	
		<div id="content">
				<div id="main">

					<form:form commandName="contentDisplayForm" method="post" action="${getChangedIPHCategoryData}" name="contentDisplayForm" id="contentDisplayForm">
				
					        <input type="hidden" id="getIPHCategory" name="getIPHCategory" value=""></input>
					        <input type="hidden" id="petIdForWebservice" name="petIdForWebservice" value=""/>
							<input type=hidden id="actionParameter" name="actionParameter"/>
							<input type="hidden" id="username" name="username" value="afusos3"/>
							<input type="hidden" id="dynamicCategoryId" name="dynamicCategoryId" value=""/>
							<input type=hidden id="stylePetId" name="stylePetId" value="${contentDisplayForm.styleInformationVO.orin}"/>
							<input type=hidden id="selectedOmniBrand" name="selectedOmniBrand"  value="${contentDisplayForm.omniBrandSelected}"/>
							<input type=hidden id="selectedCarsBrand" name="selectedCarsBrand" value="${contentDisplayForm.carBrandSelected}"/>
							<input type="hidden" id="complexPackEntryId" name="complexPackEntry" value="${contentDisplayForm.styleAndItsChildDisplay.complexPackEntry}"/>
							<input type="hidden" id="packColorEntryId" name="packColorEntry" value="${contentDisplayForm.styleAndItsChildDisplay.packColorEntry}"/>
							<input type="hidden" id="styleEntryId" name="styleEntry" value="${contentDisplayForm.styleAndItsChildDisplay.styleEntry}"/>
							<input type="hidden" id="styleColorEntryId" name="styleColorEntry" value="${contentDisplayForm.styleAndItsChildDisplay.styleColorEntry}"/>
							<input type="hidden"  id="roleNameId" name="roleName" value="${contentDisplayForm.roleName}"/>
								
								
							
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
									
									
									<!-- Added by Sriharsha -->
									<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
										 <li class="txt_attr_name" style="width: 30%;"><fmt:message key="lableOminChannelBrand" bundle="${display}"/>
										 <select style="width:52%;height: 27px;"  id="omnichannelbrand" name="omnichannelbrand" onclick="javascript:getSelectedOmniBrand(omnichannelbrand)">
											        <option id="-1" value="-1"> Please Select</option>    
											        <c:forEach var="omnibrandlist" items="${contentDisplayForm.lstOmniChannelBrandVO}">		
														<option id="${omnibrandlist.omniChannelBrandCode}" value="${omnibrandlist.omniChannelBrandCode}-${omnibrandlist.omniChannelBrandDesc}"> ${omnibrandlist.omniChannelBrandDesc}</option>             
													</c:forEach>														
										</select>
										</li>
										 <li class="txt_attr_name" style="width: 30%;"><fmt:message key="labelCarBrand" bundle="${display}"/>
										 <select style="width:52%;height: 27px;"  id="carbrand" name="carbrand" onclick="javascript:getSelectedOCarsBrand(carbrand)">
													 <option id="-1" value="-1"> Please Select</option>   
											        <c:forEach var="carbrandList" items="${contentDisplayForm.lstCarBrandVO}">												
														<option id="${carbrandList.carBrandCode}" value="${carbrandList.carBrandCode}-${carbrandList.carBrandDesc}"> ${carbrandList.carBrandDesc}</option>               
													</c:forEach>														
										</select>
										</li>
									</ul>
									<!-- End Added by Sriharsha -->
									
									
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
					  							       class="saveContentButton"  onclick="javascript:saveContentPetAttributesWebserviceResponse('${saveContentPetAttributes}','<c:out value="${contentDisplayForm.styleInformationVO.orin}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>', '<c:out value="${contentDisplayForm.productAttributesDisplay.dropDownList.size()}"/>','<c:out value="${contentDisplayForm.legacyAttributesDisplay.dropDownList.size()}"/>')"/>
									                            
											      
					  							  
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
													<textarea id="vendorStyleId"	class="maxChars spellcheck textCount recommended" name="productDescription" cols="120" rows="13" onkeyup="textCounter();" onblur="textCounter();"  disabled="disabled"><c:out value="${contentDisplayForm.productDetailsVO.productDescription}"/></textarea>		
														
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
													<textarea id="vendorStyleId"	class="maxChars spellcheck textCount recommended" name="productDescription" cols="120" rows="13" onkeyup="textCounter();" onblur="textCounter();" ><c:out value="${contentDisplayForm.productDetailsVO.productDescription}"/></textarea>		
														
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
													<textarea id="vendorStyleId"	class="maxChars spellcheck textCount recommended" name="productDescription" cols="120" rows="13" onkeyup="textCounter();" onblur="textCounter();" disabled="true"><c:out value="${contentDisplayForm.productDetailsVO.productDescription}"/></textarea>		
														
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
													
														<td><span class="contentStatusId"><c:out value="${styleDisplayList.contentStatus}"/></span></td>	
														
														  
														<input type="hidden" id="styleContentStatusId" name="styleContentStatus" value="${styleDisplayList.contentStatus}"></input>								
														<td><c:out value="${styleDisplayList.completionDate}" /></td>
														<portlet:actionURL var="updateContentStatusForStyle">
                                                               <portlet:param name="action" value="updateContentStatusForStyle" />
                                                        </portlet:actionURL>
														
														<c:choose>
														    <c:when test="${contentDisplayForm.roleName == 'vendor'}">
														                    <c:if test="${empty contentDisplayForm.readyForReviewMessage}">	
															                  <td><input type="button"  id="styleSubmit" name="submitStyleData"  value="Submit"   style="width: 170px; height:30px" onclick="javascript:getUpdateStyleContentPetStatusWebserviceResponse('${updateContentPetStyleDataStatus}','<c:out value="${styleDisplayList.orinNumber}"/>','<c:out value="${styleDisplayList.contentStatusCode}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>','vendor')"/></td>
															                </c:if>	
															     			<c:if test="${not empty contentDisplayForm.readyForReviewMessage}">	
															     				<td><input type="button"  id="styleSubmit" name="submitStyleData"  value="Submit"   style="width: 170px; height:30px"  disabled="disabled"/></td>	
															     			</c:if>				 
														    </c:when>									   
														    <c:when test="${contentDisplayForm.roleName == 'dca'}">
														           
															     <td><input type="button"  id="styleSubmit" name="submitStyleData"  value="Approve"   style="width: 170px; height:30px" onclick="javascript:getUpdateStyleContentPetStatusWebserviceResponse('${updateContentPetStyleDataStatus}','<c:out value="${styleDisplayList.orinNumber}"/>','<c:out value="${styleDisplayList.contentStatusCode}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>','dca')" /></td>						 
														    </c:when>
														    <c:when test="${contentDisplayForm.roleName == 'readonly'}">
															     <td><input type="button"  id="styleSubmit" name="submitStyleData"  value="Submit"   style="width: 170px; height:30px" onclick="javascript:updateContentStatusForStyleData();" disabled="true"/></td>						 
														    </c:when>
								                       </c:choose>
														
														
													
													</tr>
																	
											
											         <% int colorRows=1;  %>
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
															   <input type="hidden" id="styleColorCountId" name="styleColorCount" value="<c:out value="${styleDisplayList.styleColorList.size()}"/>"></input> 
															   <td   id="petColorRow_id<%= colorRows %>"  class="petColorRowClass<%= colorRows %>"><c:out value="${styleDisplayColorList.contentStatus}"/></td>														      
																
															    	
															
															    
															  
															                                   
															   <td><c:out value="${styleDisplayColorList.completionDate}" /></td> 
															   <c:choose>
																    
																	 <c:when test="${contentDisplayForm.roleName == 'vendor'}">
																   
																	    <td><input type="button"    id="styleColorSubmitButtonId-${styleDisplayColorList.orinNumber}"   name="submitStyleColorData-${styleDisplayColorList.color}"  value="Submit"   style="width: 170px; height:30px" onclick="javascript:getUpdateStyleColorContentPetStatusWebserviceResponse('${updateContentPetStyleColorDataStatus}','<c:out value="${styleDisplayColorList.orinNumber}"/>','<c:out value="${styleDisplayColorList.contentStatusCode}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>','vendor')"/></td>
																	     <input type="hidden" id="styleColorApproveButtonId" name="styleColorApproveButton" value="<c:out value="styleColorSubmitButtonId-${styleDisplayColorList.orinNumber}"/>"></input> 
																    </c:when>
																    <c:when test="${contentDisplayForm.roleName == 'dca'}">
																   
																	    <td><input type="button"    id="styleColorSubmitButtonId-${styleDisplayColorList.orinNumber}"  class="petColorApproveButtonClass<%= colorRows %>" name="submitStyleColorData-${styleDisplayColorList.color}"  value="Approve"   style="width: 170px; height:30px" onclick="javascript:getUpdateStyleColorContentPetStatusWebserviceResponse('${updateContentPetStyleColorDataStatus}','<c:out value="${styleDisplayColorList.orinNumber}"/>','<c:out value="${styleDisplayColorList.contentStatusCode}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>','dca')"/></td>
																	    <input type="hidden" id="styleColorApproveButtonId" name="styleColorApproveButton" value="<c:out value="styleColorSubmitButtonId-${styleDisplayColorList.orinNumber}"/>"></input> 
																    </c:when>
																    <c:when test="${contentDisplayForm.roleName == 'readonly'}">
																	    <td><input type="button"  id="styleColorSubmit" name="submitStyleColorData"  value="Submit"   style="width: 170px; height:30px" onclick="javascript:getUpdateStyleColorContentPetStatusWebserviceResponse('${updateContentPetStyleColorDataStatus}','<c:out value="${styleDisplayColorList.orinNumber}"/>','<c:out value="${styleDisplayColorList.contentStatusCode}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>','<c:out value="${contentDisplayForm.roleName}"/>')" disabled="true"/></td>
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
													           <% colorRows++;  %>  	                                                            
											                  
												    </c:forEach>
												  
												    <c:set var="countList" value="${countList+1}" />
							               	</c:forEach> 
							

											
											</tbody>			
		                                   </table>			
															
									</div>
								</c:if>					
									
							</div>
							
						<!--Global Attribute Section starts here  -->	
						
							 <div class="cars_panel x-hidden" id="globalAttributeSection">
								
										<div class="x-panel-header">
											<fmt:message key="sectionGlobalAttribute" bundle="${display}"/>
										</div>
										
										<div id="ajaxResponseStyleAttribute" class="x-panel-body" style="width:915px;">	
									
												
															
									    </div>	
										
										
										
										
										
										
										
							</div>	 
						
						<!--Color Attributes Section starts here  -->
						
						
						
						
						
													
										   
									<div class="cars_panel x-hidden"  id="colorAttributeSection" >
							
										<div class="x-panel-header">
											<fmt:message key="sectionColorAttribute" bundle="${display}"/>
										</div>
									
										<div class="x-panel-body" style="width:915px;">	
										
										  <div id ="ajaxResponseStyleColorAttribute">			</div>
										
										<!--   <c:if test="${empty  contentDisplayForm.noStyleColorAttributeExistsMessage}">	--> 
										
											 
										 
											  
										 
											    
										<!--  </c:if>	-->   
										
										
							
								  <table id="styleColorAttributeID" class="table">												
									<tbody>
									    <tr>
											<td width="20%"><b>Omnichannel Color Family</b></td>
											<td>
												 <select  name="omniColorFamily"   id='a_drop_down_box'   selected="selected">
													<!--  <option value="omnichannelColorFamily1"> </option>-->
													        <option id="-1" value="-1"> Please Select</option>  
														<c:forEach var="colorMap" items="${contentDisplayForm.colorDataSet}">												
														<!--  	<option value="${colorMap.key}"  [b]selected="true"[/b] ><c:out value="${colorMap.value}"/></option>   -->
															<option id="${colorMap.key}" value="${colorMap.key}"> ${colorMap.value}</option>               
														</c:forEach>	
												</select> 
											</td>
										</tr>
										<tr>
											<td><b>Secondary Color 1</b></td>
											<td>
												<select name="secondaryColor1" id="secondaryColorId" >
												 <option id="-1" value="-1"> Please Select</option>  
														<c:forEach var="colorMap" items="${contentDisplayForm.colorDataSet}">	
														   											
															<!-- <option value="${colorMap.key}"  [b]selected="true"[/b] ><c:out value="${colorMap.value}"/></option>--> 
															<option id="${colorMap.key}" value="${colorMap.key}"> ${colorMap.value}</option>              
														</c:forEach>
												  										  
												</select>
                                            </td>											
										</tr>
										<tr>
											<td><b>Secondary Color 2</b></td>
											<td>
												<select name="secondaryColor2" id="secondaryColorId2" >
												  <option id="-1" value="-1"> Please Select</option>  
														<c:forEach var="colorMap" items="${contentDisplayForm.colorDataSet}">												
														<!-- <option value="${colorMap.key}"  [b]selected="true"[/b] ><c:out value="${colorMap.value}"/></option>--> 
															<option id="${colorMap.key}" value="${colorMap.key}"> ${colorMap.value}</option>               
														</c:forEach>
												  										  
												</select>
											</td>
										</tr>
										<tr>
											<td><b>Secondary Color 3</b></td>
											<td>
												<select name="secondaryColor3" id="secondaryColorId3" >
												  <option id="-1" value="-1"> Please Select</option>  
														<c:forEach var="colorMap" items="${contentDisplayForm.colorDataSet}">												
															<!-- <option value="${colorMap.key}"  [b]selected="true"[/b] ><c:out value="${colorMap.value}"/></option>--> 
															<option id="${colorMap.key}" value="${colorMap.key}"> ${colorMap.value}</option>             
														</c:forEach>
																							  
												</select> 
											</td>
										</tr>
										<tr>
											<td><b>Secondary Color 4</b></td>
											<td>
												<select name="secondaryColor4" id="secondaryColorId4" >
												  <option id="-1" value="-1"> Please Select</option>  
														<c:forEach var="colorMap" items="${contentDisplayForm.colorDataSet}">												
															<!-- <option value="${colorMap.key}"  [b]selected="true"[/b] ><c:out value="${colorMap.value}"/></option>--> 
															<option id="${colorMap.key}" value="${colorMap.key}"> ${colorMap.value}</option>             
														</c:forEach>
												  								  
												</select> 
											</td>
										</tr>
										<tr>
											<td><b>NRF Color Code</b></td>											
											<td><input type="text" id="nrfColorCodeId" name="nrfColorCodeId" value=" " disabled="true"/></td>
										</tr>
										<tr>
											<td><b>Omni Channel Color Description</b></td>
											<td><input type="text" id="omniChannelColorDescriptionId" name="omniChannelColorDescription" value=" "/></td>
										</tr>	
										<tr>
											<td><b>Vendor Color</b></td>
											<td><input type="text" id="vendorColorId" name="vendorColor" value=" "/></td>
										</tr>
									</tbody>	
								</table>							
											
							                      
	                                   		
																
									</div>				
									
						      </div>	  
				
						
						
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
					 <form:form commandName="contentDisplayForm1" modelAttribute="fdForm" method="post" action="${formAction}" id="contentDisplayForm1"> 
							
									<div class="x-panel-header">
										<fmt:message key="sectionProductAttribute" bundle="${display}"/>
									</div>
									
									<div class="x-panel-body" style="width:915px;">	
									        <table  cellpadding="0"  class="table tree2 table-bordered table-striped table-condensed">
									            <div id="webserviceMessageDescription"><tr><font color="blue"> </font></tr></div>
									           
									            <tr>
									                <portlet:resourceURL id="getIPHCategory" var="getIPHCategory"></portlet:resourceURL>
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
													       	
													                                                                       
                                                                                                           <select style="width:58%;height: 27px;"  id="iphCategoryDropDownId" name="iphCategoryDropDown"  onchange="getDynamicAttributes()">
					<option id="select" value="select" selected> Please Select</option>
                                                                                                               <c:forEach var="categoryMap" items="${contentDisplayForm.categoryReferenceData}">                                                                             
                                                                                                                            <option value="${categoryMap.key}"  [b]selected="true"[/b] ><c:out value="${categoryMap.value}"/></option> 
                                                                                                                          
                                                                                                                            
                                                                                                                                        
                                                                                                                     </c:forEach>                                                                                           
                                                                                                           </select>
                                                                                              

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
																			
																			<c:forEach  var="savedDropDownMap"  items="${dropDownDisplayList.savedDropDownValuesMap}" >
																				<c:set var="stSelected" value=""/>
																				<c:if test="${dropDownMap.key ==savedDropDownMap.key}">
																					<c:set var="stSelected" value="selected='selected'"/>
																				</c:if>                                                     
																				 
					                                                        </c:forEach>
																			 <option value="${dropDownMap.key}"  <c:out value="${stSelected}" escapeXml="false"/> ><c:out value="${dropDownMap.value}"/></option> 
																		</c:forEach>														
															        </select>	
														        </td>															
																										
															</tr>	
														                                                               
													<% i++; %>
												    </c:forEach>
												    
												                  <% int j=1;  %>
                                                                   <input type="hidden" name="paTextAttributeCount"  id="paTextAttributeCount" value="${contentDisplayForm.productAttributesDisplay.textFieldList.size()}"  /> 
                                                                    <c:forEach items= "${contentDisplayForm.productAttributesDisplay.textFieldList}"  var="paTextFieldDisplayList">
                                                                                       <input type="hidden" name="textFieldHidden" id="textFieldHidden_id<%= j %>" value="" />
                                                                           <input type="hidden" name="paTextAttributeXpath"  id="paTextAttributeXpath_id<%= j %>"  value="${paTextFieldDisplayList.attributeName}#${paTextFieldDisplayList.attributePath}" />
                                                                                             <tr>                                                                                         
                                                                                                       <td><c:out value="${paTextFieldDisplayList.displayName}"/></td>
                                                                                                       <td><input type="text" id="paText_Id<%=j%>" name="paText_Id<%=j%>" value="${paTextFieldDisplayList.attributeFieldValue}" /></td>                                                                                                                                     
                                                                                             </tr> 
                                                                          <% j++; %>
                                                                   </c:forEach>

												    
												     <% int k=1;  %>
												     <c:forEach items= "${contentDisplayForm.productAttributesDisplay.radiobuttonList}"  var="paRadiobuttonDisplayList">
															<input type="hidden" name="paRadioAttributeXpath"  id="paRadioAttributeXpath_id<%= k%>"  value="${paRadiobuttonDisplayList.attributeName}#${paRadiobuttonDisplayList.attributePath}" />
															
																																			
															<tr>
																<td><c:out value="${paRadiobuttonDisplayList.displayName}"/></td>
																
																<td><input type="radio"  id="paRadio_Id<%=k%>"  name="paRadioName_Id<%=k%>" value="${paRadiobuttonDisplayList.attributeFieldValue}" checked></td>												
																															
																										
															</tr>	
														                                                               
													<% k++; %>
												    </c:forEach>
												    
												     <% int m=1;  %>
												     <c:forEach items= "${contentDisplayForm.productAttributesDisplay.checkboxList}"  var="paCheckboxDisplayList">
															 <input type="hidden" name="paCheckBoxAttributeNameXpath"  id="paCheckBoxAttributeNameXpath_id<%= m%>"  value="${paCheckboxDisplayList.attributeName}#${paCheckboxDisplayList.attributePath}"/>
																
																																			
															<tr>
																<td><c:out value="${paCheckboxDisplayList.displayName}"/></td>
																
																<td><input type="checkbox"   id="paCheckBox_Id<%=m%>"    name="paCheckBoxName_Id<%=m%>"  value="${paCheckboxDisplayList.attributeFieldValue}"> </td>
																
																																									
															</tr>	
														                                                               
															                                                               
													 <% m++; %>
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
										<table id="legacyAttributeTable" class="table tree2 table-bordered table-striped table-condensed"  border="1" cellspacing="1" cellpadding="1">
														
														
														
														
													 <% int bmDropDownCount=1;  %>
													<c:forEach items= "${contentDisplayForm.legacyAttributesDisplay.dropDownList}"  var="blueMartiniDropDownList" >
													
																																			
															<tr>
																<td><c:out value="${blueMartiniDropDownList.displayName}"/></td>
																
																<input type="hidden" name="blueMartiniDropDownAttributeNameXpath"  id="blueMartiniDropDownAttributeNameXpath_id<%= bmDropDownCount %>"  value="${blueMartiniDropDownList.attributeName}#${blueMartiniDropDownList.attributePath}" />															
																<td>
																	<input type="hidden" name="blueMartiniDropDownHidden" id="blueMartiniDropDownHidden_id<%= bmDropDownCount %>" value="" />
																	<select style="width:58%; height: 80px;"  multiple="multiple" id="bmDropDownsId_id<%= bmDropDownCount %>" name="bmDropDownsName_id<%= bmDropDownCount %>" onclick="javascript:bmDropDownValues(bmDropDownsId_id<%= bmDropDownCount %>, <%= bmDropDownCount %>, '<c:out value="${contentDisplayForm.legacyAttributesDisplay.dropDownList.size()}"/>')">
																        
																        <c:forEach var="dropDownMap" items="${blueMartiniDropDownList.dropDownValuesMap}">												
																			
																			<c:forEach  var="savedDropDownMap"  items="${blueMartiniDropDownList.savedDropDownValuesMap}" >
																				<c:set var="stSelected" value=""/>
																				<c:if test="${dropDownMap.key ==savedDropDownMap.key}">
																					<c:set var="stSelected" value="selected='selected'"/>
																				</c:if>                                                     
																				 
					                                                        </c:forEach>
																			 <option value="${dropDownMap.key}"  <c:out value="${stSelected}" escapeXml="false"/> ><c:out value="${dropDownMap.value}"/></option> 
																		</c:forEach>														
															        </select>	
														        </td>															
																										
															</tr>	
														                                                               
													<% bmDropDownCount++; %>
												    </c:forEach>
												    
												     <% int bmTextFieldCount=1; %>
												    <c:forEach items= "${contentDisplayForm.legacyAttributesDisplay.textFieldList}"  var="blueMartiniTextFieldList">
															<tr>
															    <input type="hidden" name="bmTextAttributeCount"  id="bmTextAttributeCount" value="${contentDisplayForm.legacyAttributesDisplay.textFieldList.size()}"  />
																<input type="hidden" name="blueMartiniTextAttributeNameXpath"  id="blueMartiniTextAttributeNameXpath_id<%= bmTextFieldCount %>"  value="${blueMartiniTextFieldList.attributeName}#${blueMartiniTextFieldList.attributePath}" />
																<input type="hidden" name="bmTextFieldHidden" id="bmTextFieldHidden_id<%= bmTextFieldCount %>" value="" />
																<td><c:out value="${blueMartiniTextFieldList.displayName}"/></td>
																<td><input type="text" id="blueMartiniText_Id<%=bmTextFieldCount%>" name="blueMartiniTextName_Id<%=bmTextFieldCount%>" value="${blueMartiniTextFieldList.attributeFieldValue}" /></td>																
																										
															</tr>	
														                                                               
													<% bmTextFieldCount++; %>
												    </c:forEach>
												    
												    
												     <% int bmRadioButton=1; %>
												     <c:forEach items= "${contentDisplayForm.legacyAttributesDisplay.radiobuttonList}"  var="blueMartiniRadiobuttonDisplayList">
													
																																			
															<tr>
																<input type="hidden" name="blueMartiniRadioAttributeNameXpath"  id="blueMartiniRadioAttributeNameXpath_id<%= bmRadioButton %>"  value="${blueMartiniRadiobuttonDisplayList.attributeName}#${blueMartiniRadiobuttonDisplayList.attributePath}"/>
																<td><c:out value="${blueMartiniRadiobuttonDisplayList.displayName}"/></td>
																<td><input type="radio"  id="blueMartiniRadio_Id<%=bmRadioButton%>"  name="blueMartiniRadioName_Id<%=bmRadioButton%>" value="${blueMartiniRadiobuttonDisplayList.attributeFieldValue}" checked></td>
																															
																										
															</tr>	
														                                                               
													     	                                                               
													<% bmRadioButton++; %>
												    </c:forEach>
												    
												    
												    <% int bmCheckBox=1; %>
												     <c:forEach items= "${contentDisplayForm.legacyAttributesDisplay.checkboxList}"  var="blueMartiniCheckboxDisplayList">
													
																																			
															<tr>
															    <input type="hidden" name="blueMartiniCheckBoxAttributeNameXpath"  id="blueMartiniCheckBoxAttributeNameXpath_id<%= bmCheckBox %>"  value="${blueMartiniCheckboxDisplayList.attributeName}#${blueMartiniCheckboxDisplayList.attributePath}"/>
																<td><c:out value="${blueMartiniCheckboxDisplayList.displayName}"/></td>
																<td><input type="checkbox" id="blueMartiniCheckBox_Id<%=bmCheckBox%>"    name="blueMartiniCheckBoxName_Id<%=bmCheckBox%>" value="${blueMartiniCheckboxDisplayList.attributeFieldValue}"> <br></td>
																																									
															</tr>	
														                                                               
													<% bmCheckBox++; %>
												    </c:forEach>
												
												
													
														
														
														
										</table>
												
															
									</div>				
									
						</div>
							</form:form>
						
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
		
	
   

