<%@ include file="/WEB-INF/pages/jsp/include.jsp" %><form></form>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<head>
 	 <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
     <title>Content Display.</title>
     <link rel="stylesheet" type="text/css" href="<%=response.encodeURL(request.getContextPath()+ "/css/pep.css")%>">
     <link rel="stylesheet" type="text/css" href="<%=response.encodeURL(request.getContextPath()+ "/css/contentDecoration.css")%>">     
     <link rel="stylesheet" type="text/css"   href="<%=response.encodeURL(request.getContextPath()+"/css/extjs-resources/css/ext-all.css")%>">

     <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
     <link rel="stylesheet" type="text/css"  href="<%=response.encodeURL(request.getContextPath()+"/css/bootstrap.css")%>">
     <link rel="stylesheet"  type="text/css"  href="<%=response.encodeURL(request.getContextPath()+"/css/jquery.treegrid.css")%>">
	 <link rel="stylesheet" type="text/css" href="<%=response.encodeURL(request.getContextPath()+ "/css/jquery.alerts.css")%>" >
     
	<script type="text/javascript" 	src="<%=request.getContextPath()%>/js/libs/jq-plugins-adapter.js"></script>	
	<script type="text/javascript"  src="<%=request.getContextPath()%>/js/libs/ext-all.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/libs/jquery.scrollTo-min.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/belk.pep.editpep.js?v=2008111301"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/libs/jquery-latest.min.js"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/libs/jquery-plugins.js"></script> 	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.alerts.js"></script> 	


	<script type="text/javascript" src="<%=request.getContextPath()%>/js/contentScreen.js"></script>
	

	
  	<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
 	<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	
	
    <script src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.min.js")%>"></script>
    <script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.treegrid.js")%>"></script>
	<script type="text/javascript">
		$(function(){
			/** Modified For PIM Phase 2 
				* - code to handle copy orin popup visiblity 
				* Date: 06/18/2016
				* Modified By: Cognizant
			*/
			$('#btnCopyORIN').on('click', function(e){
				if($(this).hasClass('chevron-down')){
					$('#orin-popup').slideDown();
					$(this).removeClass('chevron-down').addClass('chevron-up');
				}else{
					$('#orin-popup').slideUp();
					$(this).addClass('chevron-down').removeClass('chevron-up');
					//clearing the orin textbox when collapsed
					$('#orin-popup').find('input[type=text]').val('');
				} 
			});

			$('#doCopyOrin').on('click', function(e){
				if($('#copyORINstyleIdTmp').val().trim() != ''){
					$('#copyORINstyleId').val($('#copyORINstyleIdTmp').val());
					$('#copyORINForm').submit();
				}else{
					$('#copyORINstyleIdTmp').addClass('error_txt');
				}
			});
			
			$('#copyORINstyleIdTmp').on('change', function(){
				$(this).removeClass('error_txt');
			});
			
			
			setTimeout(function(){
				$('#formIphMappingErrorMessage').fadeOut('slow');
			}, 8000);
		});
	</script>
    <script type="text/javascript">
    var inputChanged = true;
   
            $(document).ready(function() {

            	 //code for alternate colors for Product and legacy attribute table rows-START
			$("#productAttr_table tr:even").css("background-color", "#F9F9F9");
			$("#productAttr_table tr:odd").css("background-color", "#FFFFFF");
			$("#legacyAttributeTable tr:even").css("background-color", "#F9F9F9");
			$("#legacyAttributeTable tr:odd").css("background-color", "#FFFFFF");
            //code for alternate colors for Product and legacy attribute table rows-END
            	//Logic for disabling the Product Name , Product Description Text  Area ,Style Submit button ,omni channel ,car brand
            	// when the Style Pet Status is completed or closed
            	  var  pepUserRoleName = "";
            	if(document.getElementById("roleNameId") != null){
            		    pepUserRoleName = document.getElementById("roleNameId").value;  
            	}
            	       	
                  var stylePetContentStatus =    $(".contentStatusId").html();  
                  var styleColorRowCount = "";
                  if(document.getElementById("styleColorCountId") != null) {
                	   styleColorRowCount = document.getElementById("styleColorCountId").value; 
                  }
            
                  //Retain the value entered in the field   logic  when the IPH drop down changes
          		  var selectedCategoryID="${requestScope.selectedCategory}";
          			selectedCategoryID = selectedCategoryID.trim();

          			if (typeof selectedCategoryID !== "undefined" && selectedCategoryID !== null && selectedCategoryID!='' ) { 
          				
          			  $("#iphCategoryDropDownId").val("${requestScope.selectedCategory}").attr('selected', 'selected');
          			 // $("#iphCategoryDropDownId").focus();
          			} 
          			
          	    //Logic for highlighting the selected omnichannel brand name 	in its drop  down  on change of IPH Drop Down	
          		    var selectedOmnichannelbrand="${requestScope.selectedOmnichannelbrand}";
          			if (typeof selectedOmnichannelbrand != "undefined" && selectedOmnichannelbrand != null && selectedOmnichannelbrand.trim()!='') {  
              			$("#omnichannelbrand").val("${requestScope.selectedOmnichannelbrand}").attr('selected', 'selected');
              			document.getElementById("selectedOmniBrand").value = selectedOmnichannelbrand;
              			
          			} //else{
               			//$("#omnichannelbrand").val('-1').attr('selected', 'selected');
               		//}	 		
          	
          			//Logic for highlighting the selected car brand name 	in its drop  down  on change of IPH Drop Down	
             				var selectedCarbrand="${requestScope.selectedCarbrand}";
                    		if (typeof selectedCarbrand != "undefined" && selectedCarbrand != null && selectedCarbrand.trim()!='' ) {   
                        			$("#carbrand").val("${requestScope.selectedCarbrand}").attr('selected', 'selected');
                        			document.getElementById("selectedCarsBrand").value = selectedCarbrand;
                    		} //else{
                   			//$("#carbrand").val('-1').attr('selected', 'selected');
                   		//}	
                        				
                    		
                    		/* 	var selectedChannelEx="${requestScope.selectedChannelExclusive}";
                       		if (typeof selectedChannelEx != "undefined" && selectedChannelEx != null && selectedChannelEx.trim()!='' ) {   
                           			$("#channelExclId").val("${requestScope.selectedChannelExclusive}").attr('selected', 'selected');
                           			document.getElementById("selectedChannelExclusive").value = selectedChannelEx;
                       		} else{
                       			$("#channelExclId").val('-1').attr('selected', 'selected');
                       		}
                    		
                 		 		var selectedBelkEx="${requestScope.selectedBelkExclusive}";
                       		if (typeof selectedBelkEx != "undefined" && selectedBelkEx != null && selectedBelkEx.trim()!='' ) {   
                           			$("#belkExclId").val("${requestScope.selectedBelkExclusive}").attr('selected', 'selected');
                           			document.getElementById("selectedBelkExclusive").value = selectedBelkEx;
                       		} else{
                       			$("#belkExclId").val('-1').attr('selected', 'selected');
                       		}
              		
                       		var selectedGWP="${requestScope.selectedGWP}";
                       		if (typeof selectedGWP != "undefined" && selectedGWP != null && selectedGWP.trim()!='' ) {   
                           			$("#globalGWPId").val("${requestScope.selectedGWP}").attr('selected', 'selected');
                           			document.getElementById("selectedGWP").value = selectedGWP;
                       		} else{
                       			$("#globalGWPId").val('No').attr('selected', 'selected');
                       		}
                       		
                       		var selectedPWP="${requestScope.selectedPWP}";
                       		if (typeof selectedPWP != "undefined" && selectedPWP != null && selectedPWP.trim()!='' ) {   
                           			$("#globalPWPId").val("${requestScope.selectedPWP}").attr('selected', 'selected');
                           			document.getElementById("selectedPWP").value = selectedPWP;
                       		} else{
                       			$("#globalPWPId").val('No').attr('selected', 'selected');
                       		}
                       		
                       		var selectedPYG="${requestScope.selectedPYG}";
                       		if (typeof selectedPYG != "undefined" && selectedPYG != null && selectedPYG.trim()!='' ) {   
                           			$("#globalPYGId").val("${requestScope.selectedPYG}").attr('selected', 'selected');
                           			document.getElementById("selectedPYG").value = selectedPYG;
                       		} else{
                       			$("#globalPYGId").val('No').attr('selected', 'selected');
                       		}
                       		
                       		var selectedBopis="${requestScope.selectedBopis}";
                       		if (typeof selectedBopis != "undefined" && selectedBopis != null && selectedBopis.trim()!='') {  
                       			if(selectedBopis=='Yes' || selectedBopis=='No' ){
                       				$("#bopislId").val("${requestScope.selectedBopis}").attr('selected', 'selected');
                           			document.getElementById("selectedBopis").value = selectedBopis;
                       			}else{
                       				$("#bopislId").val('-1').attr('selected', 'selected');
                       				document.getElementById("selectedBopis").value = '-1';
                       			}
                       		} else{
                            			$("#bopislId").val('-1').attr('selected', 'selected');
                            			document.getElementById("selectedBopis").value = '-1';
                       		} */
                       		
             	//Logic for highlighting the selected omni channel color family in its  drop down on change of IPH Drop Down	
             	
             	   var selectedOmniColorFamily='${requestScope.selectedOmniColorFamily}';
                 			if (typeof selectedOmniColorFamily !== "undefined" && selectedOmniColorFamily !== null && selectedOmniColorFamily!='' ) {   
                     			$("#a_drop_down_box").val("${requestScope.selectedOmniColorFamily}").attr('selected', 'selected');
                    } 
                 			
	              //Logic for highlighting the selected secondaryColor1 in its  drop down on change of IPH Drop Down	
	                         	
	               	 var selectedSecondaryColor1='${requestScope.selectedSecondaryColor1}';
	                            			if (typeof selectedSecondaryColor1 !== "undefined" && selectedSecondaryColor1 !== null && selectedSecondaryColor1!='' ) {   
	                                			$("#secondaryColorId").val("${requestScope.selectedSecondaryColor1}").attr('selected', 'selected');
	              	} 
                            			
	                //Logic for highlighting the selected secondaryColor2 in its  drop down on change of IPH Drop Down	
	                                     	
	                	 var selectedSecondaryColor2='${requestScope.selectedSecondaryColor2}';
	                          if (typeof selectedSecondaryColor2 !== "undefined" && selectedSecondaryColor2 !== null && selectedSecondaryColor2!='' ) {   
	                    $("#secondaryColorId2").val("${requestScope.selectedSecondaryColor2}").attr('selected', 'selected');
	                 	}  else{
	           			$("#secondaryColorId2").val("-1").attr('selected', 'selected');
	           		}
                          
                          
	            //Logic for highlighting the selected secondaryColor3 in its  drop down on change of IPH Drop Down	
	                         	
	              var selectedSecondaryColor3='${requestScope.selectedSecondaryColor3}';
	                                 if (typeof selectedSecondaryColor3 !== "undefined" && selectedSecondaryColor3 !== null && selectedSecondaryColor3!='' ) {   
	                           $("#secondaryColorId3").val("${requestScope.selectedSecondaryColor3}").attr('selected', 'selected');
	                 	}   
                                 
                                 
	            //Logic for highlighting the selected secondaryColor4 in its  drop down on change of IPH Drop Down	
	                                	
	               var selectedSecondaryColor4='${requestScope.selectedSecondaryColor4}';
	                            if (typeof selectedSecondaryColor4 !== "undefined" && selectedSecondaryColor4 !== null && selectedSecondaryColor4!='' ) {   
	                                              $("#secondaryColorId4").val("${requestScope.selectedSecondaryColor4}").attr('selected', 'selected');
	                       	}                            			
                                                          	
		         //Logic for highlighting the selected Omni Channel Color Description in its  drop down on change of IPH Drop Down	
		                          	
		               var selectedOmniChannelColorDescription='${requestScope.selectedOmniChannelColorDescription}';
		                 if (typeof selectedOmniChannelColorDescription !== "undefined" && selectedOmniChannelColorDescription !== null && selectedOmniChannelColorDescription!='' ) {   
		                                $("#omniChannelColorDescriptionId").val("${requestScope.selectedOmniChannelColorDescription}");
		                  	}                            			
			      //Logic for highlighting the selected selectedVendorColor in its  drop down on change of IPH Drop Down	
			             	
			        var selectedVendorColor='${requestScope.selectedVendorColor}';
			                if (typeof selectedVendorColor !== "undefined" && selectedVendorColor !== null && selectedVendorColor!='' ) {   
			                                  $("#vendorColorId").val("${requestScope.selectedVendorColor}");
			       	}     
                  
                  // Disable color section
                  if(pepUserRoleName == "vendor")
        		  { 
	            		  //Logic for disabling the Product Name , Product Description Text  Area ,Style Color  Submit button ,omni channel ,car brand
	                      //when the Style  Pet Status is ready for review for vendor
	                       if(stylePetContentStatus=='Ready_For_Review' || stylePetContentStatus=='Closed' || stylePetContentStatus=='Completed' || stylePetContentStatus=='Deactivated')
	            		{  
		            		  $('#txt_outfitName').attr("disabled", "disabled"); 
		            		  $('#vendorStyleId').attr("disabled", "disabled");  
		            		  $('#styleSubmit').attr("disabled", "disabled"); 	            		  
		            		  $('#omnichannelbrand').attr("disabled", "disabled");  
		            		  $('#carbrand').attr("disabled", "disabled"); 
		            		  $('#iphCategoryDropDownId').attr("disabled", "disabled"); 
		            		  $('#saveButtonId').attr("disabled", "disabled");
		            		  $('#channelExclId').attr("disabled", "disabled");
		            		  $('#belkExclId').attr("disabled", "disabled");
		            		  $('#globalGWPId').attr("disabled", "disabled");
		            		  $('#globalPWPId').attr("disabled", "disabled");
		            		  $('#globalPYGId').attr("disabled", "disabled");
		            		  $('#bopislId').attr("disabled", "disabled");
		            		  
		            		  
		            		  //logic for disabling the product attribute multi  select drop down
		            		  if(document.getElementById("paDropDownCounter")){ 
		            		  var  dropDownCounting=document.getElementById("paDropDownCounter").value;
		            		  for(q=1; q<=dropDownCounting; q++){
		            				
		            				 var paDropDownId = "dropDownsId_id"+q;
		            				 document.getElementById(paDropDownId).disabled = true;
		            			}
		            		  }
		            		  
		            		//logic for disabling the product attribute  text field
		            		  if(document.getElementById("paTextAttributeCount")){
		          				var textFieldCount = document.getElementById("paTextAttributeCount").value;
		          				for(var a=0; a<textFieldCount; a++){
		          					document.getElementById("paText_Id"+(a+1)).disabled=true;		          					    
		          				}
		                       }
		            		
		            		  //logic for disabling the blue nartini attribute multi  select drop down
		            		  if(document.getElementById("bmDropDownCounter")){
		            		   var  bmDropDownCounting=document.getElementById("bmDropDownCounter").value;
		            		  for(z=1; z<=bmDropDownCounting; z++){
		            				
		            				 var bmDropDownId = "bmDropDownsId_id"+z;
		            				 document.getElementById(bmDropDownId).disabled = true;
		            			}
		            		  }
		            		  
		            		  //logic for disabling the blue martini text field 
							   	
			            	  if(document.getElementById("bmTextAttributeCount")){
			         			 var bmTextFieldCount = document.getElementById("bmTextAttributeCount").value;
			         				for(var a=0; a<bmTextFieldCount; a++){
			         					document.getElementById("blueMartiniText_Id"+(a+1)).disabled=true;
			         				   
			         				}
			         			  } 
		            		  
			            	  if(document.getElementById("paRadioButtonCounter")){ 
			            		  var  radioButtonCount =document.getElementById("paRadioButtonCounter").value;		
			            	   
			     				  for(var j=1; j<=radioButtonCount; j++){
			                	  	 for(var i=1; i<3; i++){ 
			                		 var temp = j+""+i;
				    	            	  document.getElementById("paRadio_Id"+temp).disabled = true;
			                		 }  
			     				  }
			            	  }
			            	  
			            	  if(document.getElementById("bmRadioButtonCounter")){ 
			            		  var  radioButtonCount =document.getElementById("bmRadioButtonCounter").value;	
			     				  for(var j=1; j<=radioButtonCount; j++){
			                	  	 for(var i=1; i<3; i++){ 
			                		 var temp = j+""+i;
				    	            	 document.getElementById("bmRadio_Id"+temp).disabled = true; 
				    	             }
			     				  }
			            	  }
		            		  
	            		}
	                       else if(stylePetContentStatus=='Initiated')
	                    	   { 
	                    	     $('#txt_outfitName').removeAttr('disabled'); 
			            		  $('#vendorStyleId').removeAttr('disabled');  
			            		  $('#styleSubmit').removeAttr('disabled'); 	            		  
			            		  $('#omnichannelbrand').removeAttr('disabled');  
			            		  $('#carbrand').removeAttr('disabled'); 
			            		  $('#iphCategoryDropDownId').removeAttr('disabled'); 
			            		  $('#saveButtonId').removeAttr('disabled');   
			            		  $('#channelExclId').removeAttr('disabled');  
			            		  $('#belkExclId').removeAttr('disabled');  
			            		  $('#globalGWPId').removeAttr('disabled');  
			            		  $('#globalPWPId').removeAttr('disabled');  
			            		  $('#globalPYGId').removeAttr('disabled');  
			            		  $('#bopislId').removeAttr('disabled');
			            		 
			            		 
			            		//logic for enabling the product attribute multi  select drop down
			            		if(document.getElementById("paDropDownCounter")){
			            		 var  dropDownCounting = document.getElementById("paDropDownCounter").value;
			            		  for(q=1; q<=dropDownCounting; q++){
			            				
			            				 var paDropDownId = "dropDownsId_id"+q;
			            				 document.getElementById(paDropDownId).disabled = false;
			            				 
			            			}
			            		}
			            		 
			            		//logic for enabling the product attribute  text field
			            		  if(document.getElementById("paTextAttributeCount")){
			            			var textFieldCount = document.getElementById("paTextAttributeCount").value;
			          				for(var a=1; a<textFieldCount; a++){
			          					      var paTextId="paText_Id"+a;
			          					      document.getElementById(paTextId).disabled = false;
			          				     
			          				}
			                       }
			            		 
			            		  //logic for enabling the blue martini attribute multi  select drop down
			            		  if(document.getElementById("bmDropDownCounter")){ 
			            		   var  bmDropDownCounting=document.getElementById("bmDropDownCounter").value;
			            		  for(z=1; z<=bmDropDownCounting; z++){
			            				
			            				 var bmDropDownId = "bmDropDownsId_id"+z;
			            				 document.getElementById(bmDropDownId).disabled = false;
			            			}
			            		  }
			            		  
			            		  //logic for disabling the blue martini text field 
								 
				            	  if(document.getElementById("bmTextAttributeCount")){
				         			 var bmTextFieldCount = document.getElementById("bmTextAttributeCount").value;
				         				for(var a=0; a<bmTextFieldCount; a++){
				         					document.getElementById("blueMartiniText_Id"+(a+1)).disabled=false;
				         				   
				         				}
				         			  } 
	                    	   
	                       
			            	  if(document.getElementById("paRadioButtonCounter")){ 
			            		  var  radioButtonCount =document.getElementById("paRadioButtonCounter").value;		
			            	   
			     				  for(var j=1; j<=radioButtonCount; j++){
			                	  	 for(var i=1; i<3; i++){ 
			                		 var temp = j+""+i;
				    	            	  document.getElementById("paRadio_Id"+temp).disabled = false;
			                		 }  
			     				  }
			            	  }
			            	  
			            	  if(document.getElementById("bmRadioButtonCounter")){ 
			            		  var  radioButtonCount =document.getElementById("bmRadioButtonCounter").value;	
			     				  for(var j=1; j<=radioButtonCount; j++){
			                	  	 for(var i=1; i<3; i++){ 
			                		 var temp = j+""+i;
				    	            	 document.getElementById("bmRadio_Id"+temp).disabled = false; 
				    	             }
			     				  }
			            	  }
			            	  
	                    	   }
	                   
	                     //logic for disabling the save action button only when the style pet status is completed and it child style color pet status is completed
	     	      			// or style pet status is Ready_For_Review and all its child style color pet status is Ready_For_Review
	     	      			// or style pet status is Closed and all its child style color pet status is Closed
	     	      			// or style pet status is Deactivated and all its child style color pet status is Deactivated
	     	      			
	     	      			for(i=1; i<=styleColorRowCount; i++){
	     	      				var styleColorContentPetStatus= $(".petColorRowClass"+(i)).text();	     	      			
	     	      			    var submitButtonId = "styleColorSubmitButtonId"+i;	 
	     	      			    
	     	      				if(styleColorContentPetStatus=="Ready_For_Review"  || styleColorContentPetStatus=='Closed' || styleColorContentPetStatus=='Completed' || styleColorContentPetStatus=='Deactivated')
		     	           		{ 
	    		            		  document.getElementById(submitButtonId).disabled = true;
	    		            		  
		     	           		} else  if(styleColorContentPetStatus=="Initiated") {
		     	           			
		     	           			 document.getElementById(submitButtonId).disabled = false;
		     	           		}
	     	      				
	     	      			}   
        		  
        		  }// End of Vendor
            
            	//End of logic for vendor
            	
            	//Start of logic for role dca  for enabling and disabling the action buttons
            	 if(pepUserRoleName == "dca"){
            	      //Logic for Style
	            	  if(stylePetContentStatus=='Completed' || stylePetContentStatus=='Closed' || stylePetContentStatus=='Deactivated')
	            		{ 
		            		  $('#txt_outfitName').attr("disabled", "disabled"); 
		            		  $('#vendorStyleId').attr("disabled", "disabled");  
		            		  $('#styleSubmit').attr("disabled", "disabled"); 	            		  
		            		  $('#omnichannelbrand').attr("disabled", "disabled");  
		            		  $('#carbrand').attr("disabled", "disabled"); 
		            		  $('#iphCategoryDropDownId').attr("disabled", "disabled"); 
		            		  $('#saveButtonId').attr("disabled", "disabled");  
		            		  $('#channelExclId').attr("disabled", "disabled");
		            		  $('#belkExclId').attr("disabled", "disabled");
		            		  $('#globalGWPId').attr("disabled", "disabled");
		            		  $('#globalPWPId').attr("disabled", "disabled");
		            		  $('#globalPYGId').attr("disabled", "disabled");
		            		  $('#bopislId').attr("disabled", "disabled");
							  $("#btnSubmit").attr("disabled", "disabled"); 
								$("#btnCopyORIN").attr("disabled", "disabled"); 
								$("#publisStatusCodeReadyForCopy").attr("disabled", "disabled"); 
								$("#publisStatusCodePublishToWeb").attr("disabled", "disabled"); 
		            		  
		            		 //Logic for disabling the product attribute multi select drop down 
		            		 if(document.getElementById("paDropDownCounter")){
		            		  var  dropDownCounting=document.getElementById("paDropDownCounter").value;
		            			for(q=1; q<=dropDownCounting; q++){
		            				 var paDropDownId = "dropDownsId_id"+q;
		            				 document.getElementById(paDropDownId).disabled = true;
		            			}
		            		 }
		            			
		             
		            			 
			            	 //logic for disabling the blue martini attribute multi  select drop down
			            	 if(document.getElementById("bmDropDownCounter")){ 
			            		   var  bmDropDownCounting=document.getElementById("bmDropDownCounter").value;
					            	  for(z=1; z<=bmDropDownCounting; z++){
					            				 var bmDropDownId = "bmDropDownsId_id"+z;
					            				 document.getElementById(bmDropDownId).disabled = true;
					            			}	
			            	 }
					            	  
					            	  
		            		//logic for disabling the product attribute  text field
		            		  if(document.getElementById("paTextAttributeCount")){
		          				var textFieldCount = document.getElementById("paTextAttributeCount").value;
		          				for(var a=0; a<textFieldCount; a++){
		          					document.getElementById("paText_Id"+(a+1)).disabled=true;		          					    
		          				}
		                       } 	  
					            		 
						   	 //logic for disabling the blue martini text field 
						   	
					            	  if(document.getElementById("bmTextAttributeCount")){
					         			 var bmTextFieldCount = document.getElementById("bmTextAttributeCount").value;
					         				for(var a=0; a<bmTextFieldCount; a++){
					         					document.getElementById("blueMartiniText_Id"+(a+1)).disabled=true;
					         				   
					         				}
					         			  } 	  
				            		  
					            	  if(document.getElementById("paRadioButtonCounter")){ 
					            		  var  radioButtonCount =document.getElementById("paRadioButtonCounter").value;		
					            	   
					     				  for(var j=1; j<=radioButtonCount; j++){
					                	  	 for(var i=1; i<3; i++){ 
					                		 var temp = j+""+i;
						    	            	  document.getElementById("paRadio_Id"+temp).disabled = true;
					                		 }  
					     				  }
					            	  }
					            	  
					            	  if(document.getElementById("bmRadioButtonCounter")){ 
					            		  var  radioButtonCount =document.getElementById("bmRadioButtonCounter").value;	
					     				  for(var j=1; j<=radioButtonCount; j++){
					                	  	 for(var i=1; i<3; i++){ 
					                		 var temp = j+""+i;
						    	            	 document.getElementById("bmRadio_Id"+temp).disabled = true; 
						    	             }
					     				  }
					            	  }
		            		  
	            		}
	            	  
	            	  else  if(stylePetContentStatus=='Initiated' || stylePetContentStatus=='Ready_For_Review')
		          		{  
		            		  
	            		      $('#txt_outfitName').removeAttr('disabled');		            		  
		            		  $('#vendorStyleId').removeAttr('disabled');		            		  
		            		  $('#styleSubmit').removeAttr('disabled'); 		            		  
		            		  $('#omnichannelbrand').removeAttr('disabled'); 		            		  
		            		  $('#carbrand').removeAttr('disabled'); 		            		  
		            		  $('#iphCategoryDropDownId').removeAttr('disabled'); 
		            		  $('#saveButtonId').removeAttr('disabled');		
		            		  $('#channelExclId').removeAttr('disabled'); 
		            		  $('#belkExclId').removeAttr('disabled');  
		            		  $('#globalGWPId').removeAttr('disabled');  
		            		  $('#globalPWPId').removeAttr('disabled');  
		            		  $('#globalPYGId').removeAttr('disabled');  
		            		  $('#bopislId').removeAttr('disabled');  
		            		  
		            		  //$('#styleSubmit').removeAttr('disabled');     		         		  
		            	 
		            		  //Logic for enabling the product attribute multi select drop down 
		            		  if(document.getElementById("paDropDownCounter")){ 
		            		  var  dropDownCounting=document.getElementById("paDropDownCounter").value;		            		
		            			for(q=1; q<=dropDownCounting; q++){	           				
		            				 var paDropDownId = "dropDownsId_id"+q;		 
		            				 document.getElementById(paDropDownId).disabled = false;
		            			}
		            		  }
		             
		            		 //Logic for enabling the blue martini attribute multi  select drop down
		            		 if(document.getElementById("bmDropDownCounter")){ 
			            		   var  bmDropDownCounting=document.getElementById("bmDropDownCounter").value;
					            	  for(z=1; z<=bmDropDownCounting; z++){
					            				 var bmDropDownId = "bmDropDownsId_id"+z;
					            				 document.getElementById(bmDropDownId).disabled = false;
					            	}	
		            		 }
					            	  
		             
					            	//logic for disabling the product attribute  text field
				            		  if(document.getElementById("paTextAttributeCount")){
				          				var textFieldCount = document.getElementById("paTextAttributeCount").value;
				          				for(var a=0; a<textFieldCount; a++){
				          					document.getElementById("paText_Id"+(a+1)).disabled=false;		          					    
				          				}
				                       } 
					            	  
					            	  //logic for disabling the blue martini text field 
					            	  if(document.getElementById("bmTextAttributeCount")){  
					         			 var bmTextFieldCount = document.getElementById("bmTextAttributeCount").value;
					         				for(var a=0; a<bmTextFieldCount; a++){
					         					document.getElementById("blueMartiniText_Id"+(a+1)).disabled=false;
					         			 
					         				}
					         			  }
					            	  
					            	  if(document.getElementById("paRadioButtonCounter")){ 
					            		  var  radioButtonCount =document.getElementById("paRadioButtonCounter").value;		
					            	   
					     				  for(var j=1; j<=radioButtonCount; j++){
					                	  	 for(var i=1; i<3; i++){ 
					                		 var temp = j+""+i;
						    	            	  document.getElementById("paRadio_Id"+temp).disabled = false;
					                		 }  
					     				  }
					            	  }
					            	  
					            	  if(document.getElementById("bmRadioButtonCounter")){ 
					            		  var  radioButtonCount =document.getElementById("bmRadioButtonCounter").value;	
					     				  for(var j=1; j<=radioButtonCount; j++){
					                	  	 for(var i=1; i<3; i++){ 
					                		 var temp = j+""+i;
						    	            	 document.getElementById("bmRadio_Id"+temp).disabled = false; 
						    	             }
					     				  }
					            	  }
					            
		          		}

					//End of Logic for Style
            	 
      
            	 //Logic for disabling the Product Name , Product Description Text  Area ,Style Color  Approve button ,omni channel ,car brand
            	 //when the Style Color Pet Status is completed or closed
            	             
	      			  
            	  //End of logic for disabling for role name dca       	      
        		  } // End of DCA	    	  
            	  
            	  //End of logic for disabling
            	  
            	       
            	  //Logic for disabling the Omni channel color fields and Car brand field ,style color fields  for the user role read only            	  
            	  if(pepUserRoleName == "readonly")
        		  {            		              		  
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
            		  $("#btnSubmit").attr("disabled", "disabled"); 
						$("#btnCopyORIN").attr("disabled", "disabled"); 
						$("#publisStatusCodeReadyForCopy").attr("disabled", "disabled"); 
						$("#publisStatusCodePublishToWeb").attr("disabled", "disabled"); 
        		  }
            	  
            	  
               
            	  //End of logic for disabling
            	  
            	  if(document.getElementById("paDropDownCounter")){ 
            		  var  dropDownCounting=document.getElementById("paDropDownCounter").value;		
            		 
            			for(q=1; q<=dropDownCounting; q++){	           				
            				var attNameAndPath = document.getElementById("dropdownAttributeNameXpath_id"+q).value;
            				 var list =  document.forms['contentDisplayForm'].elements["dropDownsName_id"+q+""];
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
            					document.getElementById("dropdownhidden_id"+q).value = finalVal;
            		  }
            	  }
            	  
            	  if(document.getElementById("bmDropDownCounter")){ 
            		  var  dropDownCounting=document.getElementById("bmDropDownCounter").value;		
            		 
            			for(q=1; q<=dropDownCounting; q++){	           		
            				var attNameAndPath = document.getElementById("blueMartiniDropDownAttributeNameXpath_id"+q).value;
            				 var list =  document.forms['contentDisplayForm'].elements["bmDropDownsId_id"+q+""];
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
            				 document.getElementById("blueMartiniDropDownHidden_id"+q).value = finalVal;
            		  }
            	  }
            	  
            	              	  
            	  if(document.getElementById("paRadioButtonCounter")){ 
            		  var  radioButtonCount =document.getElementById("paRadioButtonCounter").value;		
            	   
     				  for(var j=1; j<=radioButtonCount; j++){
     					 var attributePath = document.getElementById("paRadioAttributeXpath_id"+j).value;
                	  	 for(var i=1; i<3; i++){ 
                		 var temp = j+""+i;
	    	            	 if(document.getElementById("paRadio_Id"+temp).checked){
	    	            		 var userSelected = document.getElementById("paRadio_Id"+temp).value;
	    	            		 var finalString = attributePath +"#"+userSelected;
	    	            		 document.getElementById("radioButtonHidden_id"+j).value = finalString;
	    	            	 }
                		 }  
     				  }
            	  }
                
            	  if(document.getElementById("bmRadioButtonCounter")){ 
            		  var  radioButtonCount =document.getElementById("bmRadioButtonCounter").value;	
     				  for(var j=1; j<=radioButtonCount; j++){
     					 var attributePath = document.getElementById("bmRadioAttributeXpath_id"+j).value;
                	  	 for(var i=1; i<3; i++){ 
                		 var temp = j+""+i;
	    	            	 if(document.getElementById("bmRadio_Id"+temp).checked){
	    	            		 var userSelected = document.getElementById("bmRadio_Id"+temp).value;
	    	            		 var finalString = attributePath +"#"+userSelected;
	    	            		 document.getElementById("bmradioButtonHidden_id"+j).value = finalString;
	    	            	 }
                		 }  
     				  }
            	  }
            	  
            	  
            			  
                   			

                $('.tree').treegrid();
                $('.tree2').treegrid({
                    expanderExpandedClass: 'icon-minus-sign',
                    expanderCollapsedClass: 'icon-plus-sign'
                });
                
                // Display +/- signs in grouping component 
                // group id tr tablereport
                // style id tr tablereportStyle
                // Style color id tr tablereportStyleColor
                
                
            });
            
            
            function dropDownValues(selectedDropdwonValue, selectedDropDown, totalDropDwons){
         	 var list =  document.forms['contentDisplayForm'].elements["dropDownsName_id"+selectedDropDown+""];
         	 var attNameAndPath = document.getElementById("dropdownAttributeNameXpath_id"+(selectedDropDown)).value;
         	 
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
                  	var exisVal = document.getElementById("dropdownhidden_id"+(selectedDropDown)).value;
                  	document.getElementById("dropdownhidden_id"+(selectedDropDown)).value = finalVal;
                  //	alert(document.getElementById("dropdownhidden_id"+(selectedDropDown)).value);
         	  	}
            
            
            function bmDropDownValues(selectedDropdwonValue, selectedDropDown, totalDropDwons){
            	
            	 var list =  document.forms['contentDisplayForm'].elements["bmDropDownsId_id"+selectedDropDown+""];
            	 var attNameAndPath = document.getElementById("blueMartiniDropDownAttributeNameXpath_id"+(selectedDropDown)).value;
            	  
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
                     	var exisVal = document.getElementById("blueMartiniDropDownHidden_id"+(selectedDropDown)).value;
                     	document.getElementById("blueMartiniDropDownHidden_id"+(selectedDropDown)).value = finalVal;
                     	// alert(document.getElementById("blueMartiniDropDownHidden_id"+(selectedDropDown)).value);
            	  	}
            
            
            
            function pimRadioButtonValues (selectedRadioValue, totalRadio){
            	  var attributePath = document.getElementById("paRadioAttributeXpath_id"+selectedRadioValue).value;
 
            	for(var i=1; i<3; i++){ 
            		var temp = selectedRadioValue+""+i;
            	 
	            	 if(document.getElementById("paRadio_Id"+temp).checked){
	            		 var userSelected = document.getElementById("paRadio_Id"+temp).value;
	            		 
	            		 var finalString = attributePath +"#"+userSelected;
	            		 document.getElementById("radioButtonHidden_id"+selectedRadioValue).value = finalString;
	            	 }
            	}  
            	  
            }
            
            
            function bmRadioButtonValues (selectedRadioValue, totalRadio){
            	var attributePath = document.getElementById("bmRadioAttributeXpath_id"+selectedRadioValue).value;
            	
            	for(var i=1; i<3; i++){ 
            		var temp = selectedRadioValue+""+i;
	            	 if(document.getElementById("bmRadio_Id"+temp).checked){
	            		 var userSelected = document.getElementById("bmRadio_Id"+temp).value;
	            		
	            		 var finalString = attributePath +"#"+userSelected;
	            		 document.getElementById("bmradioButtonHidden_id"+selectedRadioValue).value = finalString;
	            	 }
            	}
            	  // alert(document.getElementById("bmradioButtonHidden_id"+selectedRadioValue).value);
            }
     </script>
	
     <script>
     
	     function calliph(iph,petId)
	     {
	     	document.getElementById("iphCategoryId").value = $('#iphCategoryDropDownId :selected').text().split("/")[3];

	    	 document.getElementById("getIPHCategory").value=iph;
	    	 document.forms['contentDisplayForm'].petIdForWebservice.value=petId;    	 
	    	
	    	 
	     }
	     
	     
	    function   updateContentPetStyleDataStatus()	     
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
		  inputChanged  = false ;
	            document.getElementById("lockedPet").value="";
		    var values = document.getElementById("iphCategoryDropDownId");
	            var selectedVal = values.options[values.selectedIndex].value;
	           
		   if(selectedVal !='select'){			   
	         document.forms['contentDisplayForm'].dynamicCategoryId.value = selectedVal;	
           	
	         // setTimeout(function(){selectedVal},3000);	
	          document.forms['contentDisplayForm'].submit();  
		}         
		}  
      
	 	
	 	//goToWorkListDisplayScreen() function is called on click of the Close  button to navigate back to the  WorkListDisplay Screen
		function goToWorkListDisplayScreen(loggedInUser,releseLockedPetURL) {			
			var  pepUserRoleName = "";
			inputChanged  = false ; 
			
	 		if(document.getElementById("roleNameId") != null){
      		 	 pepUserRoleName = document.getElementById("roleNameId").value;  
      		}
            var stylePetContentStatus =    $(".contentStatusId").html();  
			
			if(pepUserRoleName == 'vendor'){
				if(stylePetContentStatus=='Initiated'){
					var response =  confirm("Are you sure you want to close without saving ? ");
					if(response == false){
						return false;
				 	}
				}
			} else if(pepUserRoleName == 'dca'){
				if(stylePetContentStatus=='Initiated' || stylePetContentStatus=='Ready_For_Review'){

					
					 var response =  confirm("Are you sure you want to close without saving ? ");
					if(response == false){
						return false;
				 	} 
				}
			}
			
			if(timeOutFlag == 'yes'){
				$("#timeOutId").show();
				timeOutConfirm = 'Y';		
				setTimeout(function(){
					logout_home(loggedInUser,releseLockedPetURL);
					},4000);
				
			}else{
				$("#timeOutId").hide();
				releseLockedPet(loggedInUser,releseLockedPetURL);
				window.location = "/wps/portal/home/worklistDisplay";	
			}
		}
	 	
		
//on click of the sku orin number hyper link get the sku attributes
		function getSkuAttributes(url,selectedOrinNumber){	
			
			var ajaxCall=  $.ajax({
				  
		        url: url,
		        type: 'POST',
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
	                         $("#ajaxResponseSkuAttribute").append("<b>Belk 04 UPC :</b> " +data.skuObjectInfo.belk04Upc +"<br>");
	                         $("#ajaxResponseSkuAttribute").append("<b>Vendor UPC :</b> " +data.skuObjectInfo.vendorUpc  +"<br>");
	                         $("#ajaxResponseSkuAttribute").append("<b>Launch Date :</b> " + data.skuObjectInfo.launchDate + "<br>");
	                         $("#ajaxResponseSkuAttribute").append("<b>Gift Box :</b> " +data.skuObjectInfo.giftBox + "<br>");
	                         $("#ajaxResponseSkuAttribute").append("<b>Product Dimension UOM :</b> " + data.skuObjectInfo.productDimensionUom + "<br>");
	                         $("#ajaxResponseSkuAttribute").append("<b>Product Length :</b> "+data.skuObjectInfo.productLength +" <br>");
	                         $("#ajaxResponseSkuAttribute").append("<b>Product Width:</b> "+ data.skuObjectInfo.productWidth+ "<br> ");
	                         $("#ajaxResponseSkuAttribute").append("<b>Product Height :</b> " + data.skuObjectInfo.productHeight + "<br>");
	                         $("#ajaxResponseSkuAttribute").append("<b>Product Weight UOM :</b> "+data.skuObjectInfo.productWeightUom+ "<br>");
	                         $("#ajaxResponseSkuAttribute").append("<b>Product Weight :</b> "+data.skuObjectInfo.productWeight+ "<br>");
		            	
		        },
                
		   
		    });
		}
		
		// to reset values
		function resetColorAttributes(){ 
			$("#a_drop_down_box").val("-1").attr('selected', 'selected');
			$("#secondaryColorId").val("-1").attr('selected', 'selected');
			$("#secondaryColorId2").val("-1").attr('selected', 'selected');
			$("#secondaryColorId3").val("-1").attr('selected', 'selected');
			$("#secondaryColorId4").val("-1").attr('selected', 'selected');
			$("#omniChannelColorDescriptionId").val(" ");
			$("#vendorColorId").val(" ");
			$("#nrfColorCodeId").val(" ");
			
			disableStyleColoreFields();
     		$("#ajaxResponseStyleColorAttribute").html("");
		}
		
		//on click of the style Color orin number hyperlink get the style Color attributes
		function getStyleColorAttributes(url,selectedOrinNumber, currentRecord, userRole){	
			resetColorAttributes();			 
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
		            		 if(obj.styleColorObjectInfo){ 
		                    	 setStyleColorAttributeValue(obj.styleColorObjectInfo.colorFamilyCode,obj.styleColorObjectInfo.secondaryColorOne,obj.styleColorObjectInfo.secondaryColorTwo,obj.styleColorObjectInfo.secondaryColorThree, obj.styleColorObjectInfo.secondaryColorFour,obj.styleColorObjectInfo.nrfColorCode,obj.styleColorObjectInfo.omniChannelColor,obj.styleColorObjectInfo.nrfColorDescription,currentRecord,userRole);
		            		 }
		                     // enableStyleColorSectionForVendor()//when the style color pet is in initiated status
		                   //  $("#selectedStyleColorOrinNumber").val = selectedOrinNumber;
		                     document.getElementById("selectedStyleColorOrinNumber").value = selectedOrinNumber;
		                     $("#ajaxResponseStyleColorAttribute").html("");
		                     $("#ajaxResponseStyleColorAttribute").focus();
		             },
		             error: function(XMLHttpRequest, textStatus, errorThrown){
		            	  $("#ajaxResponseStyleColorAttribute").append(" " + obj.styleColorObjectInfo.message + "<br>");	   	                
 	                  
 	                  }
		    });
		}
		

		
		//function to populate the Style Color Section
		
		 function setStyleColorAttributeValue(omniChannelColorFamilyValue,secondaryColor1Value,secondaryColor2Value,secondaryColor3Value,secondaryColor4Value,nrfColorCodeValue,omniChannelColorDescriptionValue,vendorColorValue, currentRecord, userRole)
		 {
			
			 var styleColorStatus = $("#petColorRow_id"+currentRecord).html();
			  if(userRole == 'dca'){
				  if(styleColorStatus == 'Initiated' || styleColorStatus == 'Ready_For_Review'){
					  removeStyleColorDisable();
				  }
				  if(styleColorStatus == 'Completed' || styleColorStatus == 'Closed'){
					  disableStyleColoreFields();
				  }
				  
			  }else if('vendor'){
				 if(styleColorStatus == 'Initiated'){
					 removeStyleColorDisable();
				  }
				 if(styleColorStatus == 'Completed' || styleColorStatus == 'Ready_For_Review' || styleColorStatus == 'Closed'){
					 disableStyleColoreFields();
				  }
			  }
			               
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
		
		function removeStyleColorDisable(){
			//  $('#a_drop_down_box').removeAttr('disabled');              		  
    		   $('#secondaryColorId').removeAttr('disabled');          		  
    		   $('#secondaryColorId2').removeAttr('disabled'); 		            		  
    		   $('#secondaryColorId3').removeAttr('disabled');       		  
    		   $('#secondaryColorId4').removeAttr('disabled'); 	            		  
    		   $('#omniChannelColorDescriptionId').removeAttr('disabled');   
    		   $('#vendorColorButton').removeAttr('disabled');   
    		//   $('#vendorColorId').attr("disabled", "disabled"); 
		}
		
		//function enable Style Color Section For Vendor when the Style Color Pet is in Initiated status
		function enableStyleColorSectionForVendor()
		  {
			
			 if(document.getElementById("roleNameId") != null){
   		         var  pepUserRoleName = document.getElementById("roleNameId").value;  
           		 if(pepUserRoleName == "vendor")
            	 {
                	 var styleColorRowCount = document.getElementById("styleColorCountId").value;
	                 for(i=1; i<=styleColorRowCount; i++){
   		     	      				var styleColorContentPetStatus= $(".petColorRowClass"+(i)).text();	
   		     	      			    var submitButtonId = "styleColorSubmitButtonId"+i;	     	      			
   		     	      				if(styleColorContentPetStatus=="Initiated")
   			     	           		{
   		     		            		  document.getElementById("a_drop_down_box").disabled = false;
   		     		            		  document.getElementById("secondaryColorId").disabled = false;
   		     		            		  document.getElementById("secondaryColorId2").disabled = false;
   		     		            		  document.getElementById("secondaryColorId3").disabled = false;
   		     		            		  document.getElementById("secondaryColorId4").disabled = false;
   		     		            		  document.getElementById("omniChannelColorDescriptionId").disabled = false;
   		     		            		//  document.getElementById("vendorColorId").disabled = false;
   		     		            		  document.getElementById("saveButtonId").disabled = false;
   		     		            		  document.getElementById(submitButtonId).disabled = false;
   			     	           		}
	                     }		
            	 }
        	}
			
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
		
		
   
		//on click of the Style Data row submit button ,pass the style orin number ,style pet status and logger in user,role Name to the update content pet statuswebservice
		var  styleContentPetWebserviceResponseFlag = "false";
		var  styleColorContentPetWebserviceResponseFlag="false";
		
	   
	 //on click of the Style Color  Data row submit button ,pass the style color orin number ,styleColor  pet status and logger in user to the update content pet style color  status by caling webservice
		function getUpdateStyleColorContentPetStatusWebserviceResponse(url,styleColorPetOrinNumber,styleColorPetContentStatus,user,roleName,clickedButtonId,colorRowCount){
 
			 var status = true;
			// var val =  document.getElementById("styleContentStatusId").value;
			 
			    /*  var a_drop_down_sel= document.getElementById("a_drop_down_box");
			     if(a_drop_down_sel.value == null || a_drop_down_sel.value == '-1'){
					 alert("Please select value for Omnichannel Color Family");
					 return;
				 }    */
			     
				 /* var  omniChannelColorDescriptionId=   $("#omniChannelColorDescriptionId").val();
				 if(omniChannelColorDescriptionId == null || omniChannelColorDescriptionId.trim() == ''){
					 alert("Please complete Color fields");
					 return;
				 } */
			  
			    var val = $('.contentStatusId').html(); 
				if(roleName=='dca' && val != null && (val == 'Initiated' || val == 'Ready_For_Review')){
					status = false;
					jAlert('Please approve style level before approving the style Color Pet.', 'Alert');
					//alert("Please approve style level before approving the style Color Pet.");
				} else if( (roleName=='vendor' || roleName=='readonly') && val != null && (val == 'Initiated')){  
					status = false;
					jAlert('Please submit style level before submitting the Style Color Pet.', 'Alert');
					//alert("Please submit style level before submitting the Style Color Pet.");
				}
				
			if(status){
			if(roleName=='dca')
			{
				styleColorPetContentStatus='02' // Set Style Color  Content Pet status to Completed when  dca approves the Pet
				
				//logic to do for style color content status
				var styleColorRowCount = document.getElementById("styleColorCountId").value;
				
				//logic end 
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
                                 $("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Color Pet Status updated successfully !</font></b>" );      
                                 $("#saveButtonId").attr("disabled", "disabled");
                                 //logic to dynamically generate unique ids for the  Style Color Submit or Style Color Approve button and disable the action button
                          	     document.getElementById(clickedButtonId).disabled = true;
                                 styleColorContentPetWebserviceResponseFlag="true";
                                 $("#petColorRow_id"+colorRowCount).html('Completed');
                                 disableStyleColoreFields();
                         		
                       },
                       
                       error: function(XMLHttpRequest, textStatus, errorThrown){
   	                	
   	                	$("#ajaxResponseUpdateStylePetContentStatus").html("");
   	                	$("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Color Pet Status could not be updated, please contact Belk helpdesk </font></b> <br>"); 
   	                    $("#ajaxResponseUpdateStylePetContentStatus").append("<br>");
   	                    $("#saveButtonId").removeAttr("disabled");
   	                    //logic to dynamically generate unique ids for the  Style Color Submit or Style Color Approve button and disable the action button
   	                    document.getElementById(clickedButtonId).disabled = false;
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
                                 $("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Color Pet Status updated successfully !</font></b>" );      
                                 $("#saveButtonId").attr("disabled", "disabled");
                                 //logic to dynamically generate unique ids for the  Style Color Submit or Style Color Approve button and disable the action button
                                 document.getElementById(clickedButtonId).disabled = true;
                                 styleColorContentPetWebserviceResponseFlag="true";
								 $("#petColorRow_id"+colorRowCount).html('Ready_For_Review');
								 disableStyleColoreFields();
                                 
                       },
                       
                       error: function(XMLHttpRequest, textStatus, errorThrown){
   	                	
   	                	$("#ajaxResponseUpdateStylePetContentStatus").html("");
   	                	$("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Color Pet Status could not be updated, please contact Belk helpdesk </font></b> <br>"); 
   	                    $("#ajaxResponseUpdateStylePetContentStatus").append("<br>");
   	                    $("#saveButtonId").removeAttr("disabled");
   	                    //logic to dynamically generate unique ids for the  Style Color Submit or Style Color Approve button and disable the action button
   	                    document.getElementById(clickedButtonId).disabled = false;
   	                    styleColorContentPetWebserviceResponseFlag="false";
   	                  }
            });
			}
		
			}
		}
		
	   function disableStyleColoreFields(){
		  $('#vendorColorButton').attr("disabled", "disabled"); 	
          $('#a_drop_down_box').attr("disabled", "disabled");              		  
   		 $('#secondaryColorId').attr("disabled", "disabled");          		  
   		 $('#secondaryColorId2').attr("disabled", "disabled"); 		            		  
   		 $('#secondaryColorId3').attr("disabled", "disabled");       		  
   		 $('#secondaryColorId4').attr("disabled", "disabled"); 	            		  
   		 $('#omniChannelColorDescriptionId').attr("disabled", "disabled");        		  
   		 $('#vendorColorId').attr("disabled", "disabled"); 	
   		 $('#nrfColorCodeId').attr("disabled", "disabled"); 
	   }
	 
		function saveContentColorAttributes(url, stylePetOrinNumber){
			
			//Logic for capturing the Style Color Attributes Value
			var  selectedStyleColorOrinNumber= $("#selectedStyleColorOrinNumber").val();
			
			//Logic for selecting the omni color family from the drop down and passing the selected value to ajax and from ajax to the controller
			var a_drop_down_sel= document.getElementById("a_drop_down_box");
			var omniFamilyOptions = a_drop_down_sel.options[a_drop_down_sel.selectedIndex].value;

			//Logic for selecting the secondary color 1 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColor_sel= document.getElementById("secondaryColorId");
			var secondaryColorOptions = secondaryColor_sel.options[secondaryColor_sel.selectedIndex].value;
			
			//Logic for selecting the secondary color 2 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColorId2_sel= document.getElementById("secondaryColorId2");
			var secondaryColorId2Options = secondaryColorId2_sel.options[secondaryColorId2_sel.selectedIndex].value;
		 
			//Logic for selecting the secondary color 3 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColorId3_sel= document.getElementById("secondaryColorId3");
			var secondaryColorId3Options = secondaryColorId3_sel.options[secondaryColorId3_sel.selectedIndex].value;
			
			//Logic for selecting the secondary color 4 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColorId4_sel= document.getElementById("secondaryColorId4");
			var secondaryColorId4Options = secondaryColorId4_sel.options[secondaryColorId4_sel.selectedIndex].value;
				
			var  nrfColorCodeId=   $("#nrfColorCodeId").val();			
			var  omniChannelColorDescriptionId=   $("#omniChannelColorDescriptionId").val();			
			var  vendorColorId=   $("#vendorColorId").val();	
			
 
		      var packColorEntry =  $("#packColorEntryId").val();
		      var styleColorEntry=$("#styleColorEntryId").val();
		      
			$.ajax({
                url: url ,
                type: 'POST',
                datatype:'json',
                data: {
                	      stylePetOrinNumber:stylePetOrinNumber,
                          styleColorPetOrinNumber:selectedStyleColorOrinNumber,
                          omniColor:omniFamilyOptions,
                          secondaryColorStyleColorAttribute:secondaryColorOptions,
                          secondaryColorId2StyleColorAttribute:secondaryColorId2Options,
                          secondaryColorId3StyleColorAttribute:secondaryColorId3Options,
                          secondaryColorId4StyleColorAttribute:secondaryColorId4Options,
                          nrfColorCodeStyleColorAttribute:nrfColorCodeId,                          
                          omniChannelColorDescriptionStyleColorAttribute:omniChannelColorDescriptionId,                      
                          vendorColorIdStyleColorAttribute:vendorColorId,
                          packColorReq:packColorEntry,
                          styleColorReq:styleColorEntry
                       },
                    	   success: function(data){		
						var json = $.parseJSON(data);  
					
						$(json).each(function(i,val){
				
						var status = val.UpdateStatus;
						  if(val.UpdateStatus == 'Success'){
								 $("#ajaxResponseStyleColorAttribute").html("");
                    	                              $("#ajaxResponseStyleColorAttribute").append("<b><font size='2'>Color Attribute saved successfully!</font></b>"); 
                    	                              $("#ajaxResponseStyleColorAttribute").focus();
                    	                              
                    	                              
                    	  						 }else{
                    	  							$("#ajaxResponseStyleColorAttribute").html("");
                    		   	                	$("#ajaxResponseStyleColorAttribute").append("<b><font size='2'>Color Attribute save failed, please contact Belk helpdesk </font></b>"); 
                    		   	                    $("#ajaxResponseStyleColorAttribute").focus();
                    	  						 }
									});	
                    	   }
					 });
			
		}
	 
		 //on click of the Save button ,pass content pet attributes and make a ajax call to the  webservice
		function saveContentPetAttributesWebserviceResponse(url,stylePetOrinNumber,user,dropdownCount,bmDropDownCount, pimRadioButtonCount, bmRadionButtonCount, approveUrl, roleName, from, styleOrColor, isSubmitClicked){	
			
			 // Validate Form data
			 if(from == 'Approve'){
				if(!validateFormData(dropdownCount,bmDropDownCount, pimRadioButtonCount, bmRadionButtonCount, styleOrColor)){
					return;
				}
			 } else if(from == 'Save'){
				 var values = document.getElementById("iphCategoryDropDownId");
				 if(values != null && (values.value == 'select' || values.value == null) ){
					jAlert('IPH selection is mandatory.', 'Alert');
					//alert("IPH selection is mandatory.");
					return false;
				 }  
			 }/** Modified For PIM Phase 2 
				* - added new case for submit button 
				* Date: 06/20/2016
				* Modified By: Cognizant
			*/
			else if(from == 'Submit'){
				if(!validateFormData(dropdownCount,bmDropDownCount, pimRadioButtonCount, bmRadionButtonCount, styleOrColor)){
					return;
				}
			}
			
			var  productName= document.getElementById("txt_outfitName").value;
			var  productDescription= document.getElementById("vendorStyleId").value;
			var onminvalue =  $("#selectedOmniBrand").val();
			var carsvalue =  $("#selectedCarsBrand").val();
			
			//Commented for grouping
		/* 	var channelExcVal =  $("#selectedChannelExclusive").val();
			var belkExcVal =  $("#selectedBelkExclusive").val();
			
			var bopisValue  =  $("#selectedBopis").val();
			
			 // if(compackValue != 'Complex Pack'){
		     var selectedGWP =  $("#selectedGWP").val();
		     var selectedPWP =  $("#selectedPWP").val();
		     var selectedPYG =  $("#selectedPYG").val(); */
		     
		     
		     var channelExcVal =  "";
			var belkExcVal =  "";
			
			var bopisValue  =  "";
			
			 // if(compackValue != 'Complex Pack'){
		     var selectedGWP =  "";
		     var selectedPWP =  "";
		     var selectedPYG =  "";
		     
		     
		     
		     
			 //  }
		     // End Added by Sriharsha
		     
		  /* Commented for grouping.   
			//Logic for capturing the Style Color Attributes Value
			var  selectedStyleColorOrinNumber= $("#selectedStyleColorOrinNumber").val();
			
			//Logic for selecting the omni color family from the drop down and passing the selected value to ajax and from ajax to the controller
			var a_drop_down_sel= document.getElementById("a_drop_down_box");
			var omniFamilyOptions = a_drop_down_sel.options[a_drop_down_sel.selectedIndex].value;

			//Logic for selecting the secondary color 1 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColor_sel= document.getElementById("secondaryColorId");
			var secondaryColorOptions = secondaryColor_sel.options[secondaryColor_sel.selectedIndex].value;
			
			//Logic for selecting the secondary color 2 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColorId2_sel= document.getElementById("secondaryColorId2");
			var secondaryColorId2Options = secondaryColorId2_sel.options[secondaryColorId2_sel.selectedIndex].value;
		 
			//Logic for selecting the secondary color 3 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColorId3_sel= document.getElementById("secondaryColorId3");
			var secondaryColorId3Options = secondaryColorId3_sel.options[secondaryColorId3_sel.selectedIndex].value;
			
			//Logic for selecting the secondary color 4 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColorId4_sel= document.getElementById("secondaryColorId4");
			var secondaryColorId4Options = secondaryColorId4_sel.options[secondaryColorId4_sel.selectedIndex].value; 
				
			var  nrfColorCodeId=   $("#nrfColorCodeId").val();			
			var  omniChannelColorDescriptionId=   $("#omniChannelColorDescriptionId").val();			
			var  vendorColorId=   $("#vendorColorId").val();*/	
			
			
			var  selectedStyleColorOrinNumber = "";
			
			//Logic for selecting the omni color family from the drop down and passing the selected value to ajax and from ajax to the controller
			//var a_drop_down_sel= document.getElementById("a_drop_down_box");
			var omniFamilyOptions = "";

			//Logic for selecting the secondary color 1 from the drop down and passing the selected value to ajax and from ajax to the controller
			//var secondaryColor_sel= document.getElementById("secondaryColorId");
			var secondaryColorOptions = "";
			
			//Logic for selecting the secondary color 2 from the drop down and passing the selected value to ajax and from ajax to the controller
			//var secondaryColorId2_sel= document.getElementById("secondaryColorId2");
			var secondaryColorId2Options = "";
		 
			//Logic for selecting the secondary color 3 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColorId3_sel= "";
			var secondaryColorId3Options= "";
			
			//Logic for selecting the secondary color 4 from the drop down and passing the selected value to ajax and from ajax to the controller
			var secondaryColorId4_sel = "";
			var secondaryColorId4Options = "";
				
			var  nrfColorCodeId  = "";
			var  omniChannelColorDescriptionId = "";		
			var  vendorColorId  = "";
			
			 // Added by Sriharsha
			 

			//Logic for passing selected multi select product attribute drop downs ,its value and  the xpath
			var finalString = "";
			for(i=0; i<dropdownCount; i++){
				var temp = $("#dropdownhidden_id"+(i+1)).val();
				if(!(temp == null || temp =="")){ 
					if(finalString==null || finalString==""){
						finalString = temp;
					}else{
						finalString = finalString +"~" + temp;
					}
				}  
			}
			
	        // For Blue Martini values
            var bmFinalString = "";
             for(i=0; i<bmDropDownCount; i++){ 
                      var temp = $("#blueMartiniDropDownHidden_id"+(i+1)).val();
                      if(!(temp == null || temp =="")){ 
                                if(bmFinalString==null || bmFinalString==""){
                                          bmFinalString = temp;
                                }else{
                                          bmFinalString = bmFinalString +"~" + temp;
                                }
                      }  
             } 

			
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
		  for(j=0; j<textFieldCount; j++){
           var temp = $("#textFieldHidden_id"+(j+1)).val();
           if(!(temp == null || temp =="")){ 
                     if(finalTextValueString==null || finalTextValueString==""){
                               finalTextValueString = temp;
                     }else{
                               finalTextValueString = finalTextValueString +"~" + temp;
                     }
           	}
           }
		  
		  
		  //Get the ration buttons
		  var finalPIMRadioString = "";
			for(i=0; i<(pimRadioButtonCount); i++){
				var temp = $("#radioButtonHidden_id"+(i+1)).val();
				if(!(temp == null || temp =="")){ 
					if(finalPIMRadioString==null || finalPIMRadioString==""){
						finalPIMRadioString = temp;
					}else{
						finalPIMRadioString = finalPIMRadioString +"~" + temp;
					}
				} 
			}
		 
			
		  //Get the bm ration buttons
		  var finalBMRadioString = "";
			for(i=0; i<bmRadionButtonCount; i++){
				var temp = $("#bmradioButtonHidden_id"+(i+1)).val();
				if(!(temp == null || temp =="")){ 
					if(finalBMRadioString==null || finalBMRadioString==""){
						finalBMRadioString = temp;
					}else{
						finalBMRadioString = finalBMRadioString +"~" + temp;
					}
				} 
			}
			
		  
		  //Get the Blue Martini Attribute Text Field Values
		  
		  if(document.getElementById("bmTextAttributeCount")){
			 var bmTextFieldCount = document.getElementById("bmTextAttributeCount").value;
				for(var a=0; a<bmTextFieldCount; a++){
				    var textFieldAttNameAndPath = document.getElementById("blueMartiniTextAttributeNameXpath_id"+(a+1)).value;
				    //alert("bmTextFieldAttNameAndPath.."+textFieldAttNameAndPath);
				    var userEnteredValue = document.getElementById("blueMartiniText_Id"+(a+1)).value;
				    var temp  = textFieldAttNameAndPath +"#"+userEnteredValue 
				    document.getElementById("bmTextFieldHidden_id"+(a+1)).value = temp;
				//   alert(document.getElementById("textFieldHidden_id"+(a+1)).value);
				}
			  }
           
		  var bmfinalTextValueString = "";
		  for(j=0; j<bmTextFieldCount; j++){
         var temp = $("#bmTextFieldHidden_id"+(j+1)).val();
         if(!(temp == null || temp =="")){ 
                   if(bmfinalTextValueString==null || bmfinalTextValueString==""){
                	   bmfinalTextValueString = temp;
                   }else{
                	   bmfinalTextValueString = bmfinalTextValueString +"~" + temp;
                   }
         	}
         }
		  
		     
		     //Added to capture the entry type as complex pack ,pack color,style ,style color
		     
		      var complexPackEntry =  $("#complexPackEntryId").val();
		      var packColorEntry =  $("#packColorEntryId").val();
		      var styleEntry=$("#styleEntryId").val();		
		      var styleColorEntry=$("#styleColorEntryId").val();
		     
		      // End Added  of the logic capture the entry type as complex pack ,pack color,style ,style color
		     
		     var selectedCategory = document.getElementById("iphCategoryDropDownId");
		 
		     if(from == 'Submit'){
				  	$('#overlay_pageLoadingapprove').show();
				  }else{
					$('#overlay_pageLoadingsave').show();
			  }
		     var groupingSave = "groupingSave";
		  //Make the ajax call
			var data = {
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
                          styleColorReq:styleColorEntry,
                          belkExclusive:belkExcVal,
                          iphCategoryDropDownId:selectedCategory.value,
                          GWPValue:selectedGWP,
                          PWPValue:selectedPWP,
                          PYGValue:selectedPYG,
                          pimradioValues:finalPIMRadioString,
                          bmradioValues:finalBMRadioString,
                          channelExclusive:channelExcVal,
                          bopisSelectedValue:bopisValue,
                          groupingSave:groupingSave
                       };
			if(isSubmitClicked !== undefined && isSubmitClicked == true){
				data['submitClicked'] = 'Yes';
				data['groupId'] = $('#hdnGroupId').val();
				data['groupType'] = $('#hdnGroupType').val();
				data['groupOverallStatus'] = $('[name=publisStatusCode]:checked').val();
				
			}
			 $.ajax({
                url: url ,
                type: 'POST',
                datatype:'json',
                data: data,
                    	   success: function(data){		
                    	  						var json = $.parseJSON(data);  
                    	  						 
                    	  						$(json).each(function(i,val){
                    	  						 
                    	  						var status = val.UpdateStatus;
                    	  						$("#overlay_pageLoadingsave").hide();
                    	  						 if(val.UpdateStatus == 'Success'){
													 if(val.ContentStatusUpdate !== undefined && val.ContentStatusUpdate != ''){
															$("#ajaxResponseSaveContentPetAttribute").html("");
														  $("#ajaxResponseSaveContentPetAttribute").append("<b><font size='2'>" + val.ContentStatusUpdate +"</font></b>"); 															
															window.location.reload(true)
																	
																	
                    	                                                    $("#saveButtonId").attr("disabled", "disabled");
                    	                                                    $("#styleSubmit").attr("disabled", "disabled"); 
                    	                                                    $("#btnSubmit").attr("disabled", "disabled"); 
                    	                                                    $("#btnCopyORIN").attr("disabled", "disabled"); 
                    	                                                    $("#publisStatusCodeReadyForCopy").attr("disabled", "disabled"); 
                    	                                                    $("#publisStatusCodePublishToWeb").attr("disabled", "disabled"); 
                    	                                                    $('.contentStatusId').html('Completed');	
																	$("#ajaxResponseSaveContentPetAttribute").focus();																			
                    	                                                   
														}else{
														 $("#ajaxResponseSaveContentPetAttribute").html("");
														  $("#ajaxResponseSaveContentPetAttribute").append("<b><font size='3'>Pet Content saved successfully!</font></b>"); 
														  $("#ajaxResponseSaveContentPetAttribute").focus();
														}
														
                    	                              if(from == 'Approve'){
                    	                              if(roleName=='dca')
                    	                  			{
														console.log('dca submit');
                    	                  				stylePetContentStatus='02' // Set Style Content Pet status to Completed when  dca approves the Pet
                    	                  				 
                    	                  				 var ajaxCall=  $.ajax({
                    	                                   
                    	                                   url: approveUrl,
                    	                                   type: 'GET',
                    	                                   dataType: "json",         
                    	                                   cache:false,                      
                    	                                   data: { 
                    	                                             selectedOrinNumber:stylePetOrinNumber,
                    	                                             styleContentStatus:stylePetContentStatus,
                    	                                             loggedInUser:user
                    	                                                  },
                    	                                           
                    	                                       
                    	                                          success: function(data){
																	  console.log('Success');
                    	                                                    $("#ajaxResponseUpdateStylePetContentStatus").html("");
                    	                                                    $("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Group Status updated successfully !</font></b>" );      
                    	                                                    $("#saveButtonId").attr("disabled", "disabled");
                    	                                                    $("#styleSubmit").attr("disabled", "disabled"); 
                    	                                                    $("#btnSubmit").attr("disabled", "disabled"); 
                    	                                                    $("#btnCopyORIN").attr("disabled", "disabled"); 
                    	                                                    $("#publisStatusCodeReadyForCopy").attr("disabled", "disabled"); 
                    	                                                    $("#publisStatusCodePublishToWeb").attr("disabled", "disabled"); 
                    	                                                    $('.contentStatusId').html('Completed');		
                    	                                                    $("#ajaxResponseUpdateStylePetContentStatus").focus();
                    	                                                    styleContentPetWebserviceResponseFlag="true";
                    	                                                   
                    	                                                    
                    	                                          },
                    	                                          
                    	                                          error: function(XMLHttpRequest, textStatus, errorThrown){
                    	                      	                	console.log('Error' + XMLHttpRequest.status);
                    	                      	                	$("#ajaxResponseUpdateStylePetContentStatus").html("");
                    	                      	                	$("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Pet Status could not be updated, please contact Belk helpdesk.</font></b> <br>"); 
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
                    	                                   
                    	                                   url: approveUrl,
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
                    	                                                    $("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Pet Status updated successfully !</font></b>" );      
                    	                                                    $("#saveButtonId").attr("disabled", "disabled");
                    	                                                    $("#styleSubmit").attr("disabled", "disabled");                                 
                    	                                                    styleContentPetWebserviceResponseFlag="true";
                    	                  								  $('.contentStatusId').html('Ready For Review');
                    	                  								  styleContentPetWebserviceResponseFlag="true";
                    	                                                   
                    	                                                    
                    	                                          },
                    	                                          
                    	                                          error: function(XMLHttpRequest, textStatus, errorThrown){
                    	                      	                	
                    	                      	                	$("#ajaxResponseUpdateStylePetContentStatus").html("");
                    	                      	                	$("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Pet Status could not be updated, please contact Belk helpdesk. </font></b> <br>"); 
                    	                      	                    $("#ajaxResponseUpdateStylePetContentStatus").append("<br>");
                    	                      	                    $("#saveButtonId").removeAttr("disabled");
                    	                      	                    $("#styleSubmit").removeAttr("disabled");  
                    	                      	                    styleContentPetWebserviceResponseFlag="false";
                    	                      	                  
                    	                      	                  }
                    	                             
                    	                              
                    	                               });

                    	                  			}
                    	                              //$("#overlay_pageLoadingapprove").hide();
                    	                              } //
                    	                              
                    	  						 }else{
                    	  							$("#ajaxResponseSaveContentPetAttribute").html("");
                    		   	                	$("#ajaxResponseSaveContentPetAttribute").append("<b><font size='3'>Pet save failed. Please contact Belk helpdesk. </font></b>"); 
                    		   	                    $("#ajaxResponseSaveContentPetAttribute").focus();
                    		   	                    
                    		   	                 	if(from == 'Approve') {//End of Approve
	                 	                            	$("#ajaxResponseUpdateStylePetContentStatus").html("");
	       	                      	                	$("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Pet Status could not be updated as Pet Content save failed. Please contact Belk helpdesk.</font></b> <br>"); 
	       	                      	                    $("#ajaxResponseUpdateStylePetContentStatus").append("<br>");
	                 	                              }
													  $('#overlay_pageLoadingapprove').hide();
                    	  						 }
                    	  					});			 
                    	  				} 
					 });
	 
            
			//saveContentFormDetailsForSaveAndApprove(url,stylePetOrinNumber,user,dropdownCount,bmDropDownCount, pimRadioButtonCount, bmRadionButtonCount)
		}
		 
		 // Saving details
		 function validateFormData(dropdownCount,bmDropDownCount, pimRadioButtonCount, bmRadionButtonCount, styleOrColor){
				var omniBrandCount = document.getElementById("omniBrandCount").value
				var carBrandCount = document.getElementById("carBrandCount").value
				//if(omniBrandCount > 0){
					if(omniBrandCount != 1){
						var onminvalue =  $("#selectedOmniBrand").val();
						 if(onminvalue == null || onminvalue == '' || onminvalue == '-1'){
					    	 //alert("Please select value for Omni Channel Brand");
							 jAlert('Please select value for Omni Channel Brand', 'Alert');
					    	 return;
					     }
					}else if(omniBrandCount == 1){
						var firstValue = document.getElementById("omnichannelbrand").value;
						 if(firstValue == null || firstValue == '' || firstValue == '-1'){
							 //alert("Please select value for Omni Channel Brand");
							 jAlert("Please select value for Omni Channel Brand", 'Alert');
							 return;
						}else{
							document.getElementById("selectedOmniBrand").value = firstValue;
					}
				}
				//}
				//if(carBrandCount > 0){
					if(carBrandCount != 1){
					 var carsvalue =  $("#selectedCarsBrand").val();
						 if(carsvalue == null || carsvalue == '' || carsvalue == '-1'){
					    	 //alert("Please select value for Car Brand");
					    	 jAlert("Please select value for Car Brand", 'Alert');
					    	 return;
					     }
					}else if(carBrandCount == 1){
						var firstValue = document.getElementById("carbrand").value;
						 if(firstValue == null || firstValue == '' || firstValue == '-1'){
							 //alert("Please select value for Car Brand");
							 jAlert("Please select value for Car Brand", 'Alert');
					    	 return;
						 }else{
							 document.getElementById("selectedCarsBrand").value = firstValue;
						 }
					}

				//}
				
				var  productName= document.getElementById("txt_outfitName").value;
				if(productName == null || productName.trim() == ""){
					 jAlert("Please enter Grouping Name", 'Alert');
					 //alert("Please enter Product Name");
					 return;
				}else if(productName != null && productName.trim().length > 300){
					 //alert("Product Name should be less than 300 characters.");
					 jAlert("Grouping Name should be less than 300 characters.", 'Alert');
					 return;
				}
				
				var  productDescription= document.getElementById("vendorStyleId").value;
				if(productDescription == null || productDescription.trim() == ""){
					 jAlert('Please enter Grouping Description', 'Alert');
					 //alert("Please enter Product Description");
			    	 return;
				}else if (productDescription != null && productDescription.length < 40) {
					jAlert('Grouping Description should be minimum of 40 characters', 'Alert');
					 //alert("Product Description should be minimum of 40 characters");
					 
					 return;
				}else if (productDescription != null && productDescription.trim().length > 2000) {
					jAlert('Grouping Description should be less than 2000 characters', 'Alert');
					//alert("Product Description should be less than 2000 characters");
					 return;
				}
				
				 var belkExcVal = "";
				/*  var belkExcVal =  $("#selectedBelkExclusive").val();
			     if(belkExcVal == null || belkExcVal == '-1' || belkExcVal == ''){
			    	 alert("Please select value for Belk Exclusive");
			    	 return;
			     } */

			     /* if(styleOrColor == 'Color'){
				     var a_drop_down_sel= document.getElementById("a_drop_down_box");
				     if(a_drop_down_sel.value == null || a_drop_down_sel.value == '-1'){
						 alert("Please select value for Omnichannel Color Family");
						 return;
					 }   
				     
					 var  omniChannelColorDescriptionId=   $("#omniChannelColorDescriptionId").val();
					 if(omniChannelColorDescriptionId == null || omniChannelColorDescriptionId.trim() == ''){
						 alert("Please select value for Omnichannel Color Description");
						 return;
					 }
			     } */
			       var values = document.getElementById("iphCategoryDropDownId");
					if(values != null && (values.value == 'select' || values.value == null) ){
						jAlert("IPH selection is mandatory.", 'Alert');
						//alert("IPH selection is mandatory.");
						return;
				 }  
				
				//if(action=='APPROVE'){
						
					
			   //Logic for passing selected multi select product attribute drop downs ,its value and  the xpath
					var finalString = "";
					for(i=0; i<dropdownCount; i++){
						var temp = $("#dropdownhidden_id"+(i+1)).val();
						// Added to get the last two value by Cognizant.
						var lastTwo= temp.substr(temp.length -2);
						// Added to get the last two value by Cognizant.
						var mandatory = $("#isPIMMandatory_id"+(i+1)).val();
						// Added the condition in If condition by Cognizant.
						if(!(lastTwo == "-1" || temp == null || temp =="")){ 
							if(finalString==null || finalString==""){
								finalString = temp;
							}else{
								finalString = finalString +"~" + temp;
							}
						} 
						else {
							if(mandatory == 'Yes'){ 
							jAlert(" Please select mandatory product attributes", 'Alert');
							//alert(" Please select mandatory product attributes");
								return;
							}
						} 
						
					}
					
					  //Get the ration buttons
			   		  var finalPIMRadioString = "";
			   			for(i=0; i<(pimRadioButtonCount); i++){
			   				var temp = $("#radioButtonHidden_id"+(i+1)).val();
			   				var mandatory = $("#isPIMRadioMandatory_id"+(i+1)).val();
			   				if(!(temp == null || temp =="")){ 
			   					if(finalPIMRadioString==null || finalPIMRadioString==""){
			   						finalPIMRadioString = temp;
			   					}else{
			   						finalPIMRadioString = finalPIMRadioString +"~" + temp;
			   					}
			   				} 
			   				  else {
			   					if(mandatory == 'Yes'){ 
			   						//alert(" Please make a selection to mandatory product radio attributes");
			   						jAlert(" Please make a selection to mandatory product radio attributes", 'Alert');
			   						return;
			   					}
			   				}  
			   				
			   			}
			   			
					 // Get Text Field Values
		             // Text Field
		             if(document.getElementById("paTextAttributeCount")){
						var textFieldCount = document.getElementById("paTextAttributeCount").value;
						for(var a=0; a<textFieldCount; a++){
						    var textFieldAttNameAndPath = document.getElementById("paTextAttributeXpath_id"+(a+1)).value;
						    var mandatory = $("#isPIMTextMandatory_id"+(a+1)).val();
						    var userEnteredValue = document.getElementById("paText_Id"+(a+1)).value;
						    if(userEnteredValue == null || userEnteredValue.trim() == '' ){
						    	if(mandatory == 'Yes'){ 
					         		 jAlert(" Please enter mandatory product text field attributes", 'Alert');
					         		// alert(" Please enter mandatory product text field attributes");
					         		return; 
						    	}
						    }else{ 
						    var temp  = textFieldAttNameAndPath +"#"+userEnteredValue 
						    document.getElementById("textFieldHidden_id"+(a+1)).value = temp;
						    }
						}
		             }
		             
				  var finalTextValueString = "";
				  for(j=0; j<textFieldCount; j++){
		           var temp = $("#textFieldHidden_id"+(j+1)).val();
		           if(!(temp == null || temp =="")){ 
		                     if(finalTextValueString==null || finalTextValueString==""){
		                               finalTextValueString = temp;
		                     }else{
		                               finalTextValueString = finalTextValueString +"~" + temp;
		                     }
		           	}
		           }
					
		   			
			        // For Blue Martini values
		            var bmFinalString = "";
		             for(i=0; i<bmDropDownCount; i++){ 
		                      var temp = $("#blueMartiniDropDownHidden_id"+(i+1)).val();
		                      var mandatory = $("#isBMMandatory_id"+(i+1)).val();
					// Added to get the last two value by Cognizant.
					 var lastTwo= temp.substr(temp.length -2);
					// Added to get the last two value by Cognizant.
					// Added in If condition for the "Please Select" value select.
		                      if(!(temp == null || temp =="" || lastTwo== "-1")){ 
		                                if(bmFinalString==null || bmFinalString==""){
		                                          bmFinalString = temp;
		                                }else{
		                                          bmFinalString = bmFinalString +"~" + temp;
		                                }
		                      } 
		                        else {
		                        	if(mandatory == 'Yes'){ 
		      						 //alert(" Please select mandatory legacy attributes");
		      						 jAlert(" Please select mandatory legacy attributes", 'Alert');
		      						return;
		                        	}
		      				 }  
		                      
		             }
		            
	             //Get the bm ration buttons
		   		  var finalBMRadioString = "";
		   			for(i=0; i<(bmRadionButtonCount); i++){
		   				var temp = $("#bmradioButtonHidden_id"+(i+1)).val();
		   				 var mandatory = $("#isBMRadioMandatory_id"+(i+1)).val();
		   				if(!(temp == null || temp =="")){ 
		   					if(finalBMRadioString==null || finalBMRadioString==""){
		   						finalBMRadioString = temp;
		   					}else{
		   						finalBMRadioString = finalBMRadioString +"~" + temp;
		   					}
		   				} 
		   				  else {
		   					if(mandatory == 'Yes'){ 
		   						//alert(" Please make a selection to mandatory legacy radio attributes");
		   						jAlert(" Please make a selection to mandatory legacy radio attributes", 'Alert');
		   						return;
		   					}
		   				}  
		   				
		   			}
			   			
		   		 if(document.getElementById("bmTextAttributeCount")){
					 var bmTextFieldCount = document.getElementById("bmTextAttributeCount").value;
						for(var a=0; a<bmTextFieldCount; a++){
						    var textFieldAttNameAndPath = document.getElementById("blueMartiniTextAttributeNameXpath_id"+(a+1)).value;
						    var mandatory = $("#isBMTextMandatory_id"+(a+1)).val();
						    var userEnteredValue = document.getElementById("blueMartiniText_Id"+(a+1)).value;
						    if(userEnteredValue == null || userEnteredValue.trim() == '' ){
						    	if(mandatory == 'Yes'){ 
						      		//alert(" Please enter mandatory legacy text field attributes");
						      		jAlert(" Please enter mandatory legacy text field attributes", 'Alert');
						    		return;  
						    	}
						    }else{ 
						    var temp  = textFieldAttNameAndPath +"#"+userEnteredValue 

						    document.getElementById("bmTextFieldHidden_id"+(a+1)).value = temp;
						//   alert(document.getElementById("textFieldHidden_id"+(a+1)).value);
						    }
						}
					  }
		           
				  var bmfinalTextValueString = "";
				  for(j=0; j<bmTextFieldCount; j++){
		         var temp = $("#bmTextFieldHidden_id"+(j+1)).val();
		         if(!(temp == null || temp =="")){ 
		                   if(bmfinalTextValueString==null || bmfinalTextValueString==""){
		                	   bmfinalTextValueString = temp;
		                   }else{
		                	   bmfinalTextValueString = bmfinalTextValueString +"~" + temp;
		                   }
		         	} 
		         }
		             
				//}// End of Approve
			  
			return true;
		} 
		 // Added by Sriharsha
		function getSelectedOmniBrand(selectedOption){
			if(selectedOption != null ) {
				document.getElementById("selectedOmniBrand").value = selectedOption.value;  
			}
		}

		function getSelectedOCarsBrand(selectedOption){
			if(selectedOption != null ) {
				document.getElementById("selectedCarsBrand").value = selectedOption.value;  
			}
		}
		
		function getSelectedChannelExclId(selectedOption){
			if(selectedOption != null ) {
				document.getElementById("selectedChannelExclusive").value = selectedOption.value;  
			}
		}
		
		function getSelectedbelkExclId(selectedOption){
			if(selectedOption != null ) {
				document.getElementById("selectedBelkExclusive").value = selectedOption.value;  
			}
		}
		
		function getSelectedBopislId(selectedOption){
			if(selectedOption != null ) {
				document.getElementById("selectedBopis").value = selectedOption.value;  
			}
		}
		function getSelectedglobalGWPId(selectedOption){
			if(selectedOption != null ) {
				document.getElementById("selectedGWP").value = selectedOption.value;  
			}
		}
		
		function getSelectedglobalPWPId(selectedOption){
			if(selectedOption != null ) {
				document.getElementById("selectedPWP").value = selectedOption.value;  
			}
		}
		
		function getSelectedglobalPYGId(selectedOption){
			if(selectedOption != null ) {
				document.getElementById("selectedPYG").value = selectedOption.value;  
			}
		}
		
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

//TimeOut
var timeOutFlag = 'no';
var timeOutvar = null;
var timeOutConfirm ='N';

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
	 
	}
	
function redirectSessionTimedOutContent(){ 
 //  timeOutFlag ="yes";
   confirmationMessage = false;
   var releseLockedPetURL = $("#releseLockedPet").val();
   var loggedInUser= $("#loggedInUser").val();
   releseLockedPet(loggedInUser,releseLockedPetURL);
   if(loggedInUser.indexOf('@') === -1) 
   {
      window.location = "/wps/portal/home/InternalLogin";
   } else {
         
         window.location = "/wps/portal/home/ExternalVendorLogin";
   }
}

function logout_home(loggedInUser,releseLockedPetURL){
	inputChanged  = false ;
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
         if( inputChanged && confirmationMessage != false){ 
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
                         data: { lockedPet:lockedPet,loggedInUser:loggedInUser,pepFunction:'Content' },
                               success: function(data){
                               //alert('data '+data);
                                                                      
                    }//End of Success
            }); 
 
    return true;

}

var timeOutvarContent = null;
timeOutvarContent = setTimeout(redirectSessionTimedOutContent, 3600000);

function timeOutContentPage(){
	timeOutvarContent = setTimeout(redirectSessionTimedOutContent, 3600000);
}

document.onclick = clickListenerContent;
function clickListenerContent(e){
    clearTimeout(timeOutvar);	
	clearTimeout(timeOutvarContent);
	timeOutFlag = 'no';
	timeOutPage();
	timeOutContentPage();
}

function toggleRows(currentRow, styleId, styleColorId){

	if(currentRow == 'group'){
		if($("#tablereport").hasClass("treegrid-collapsed")){
			$("#tablereport").removeClass("treegrid-collapsed");
			$("#tablereport").addClass("treegrid-expanded");
			$("tr[id*=tablereportStyle]").show();
			$("tr[id*=tablereportStyleColor]").show();
			$("tr[id*=tablereportSku]").show();
			$("tr[id*=tablereportGroup]").show();
		}else{
			$("#tablereport").removeClass("treegrid-expanded");
			$("#tablereport").addClass("treegrid-collapsed");
			$("tr[id*=tablereportStyle]").hide();
			$("tr[id*=tablereportStyleColor]").hide();
			$("tr[id*=tablereportSku]").hide();
			$("tr[id*=tablereportGroup]").hide();
		}
		
		if($("#groupSign").hasClass("icon-minus-sign")){
			$("#groupSign").removeClass("icon-minus-sign");
			$("#groupSign").addClass("icon-plus-sign");
		}else{
			$("#groupSign").removeClass("icon-plus-sign");
			$("#groupSign").addClass("icon-minus-sign");
		}
	}else if(currentRow == 'style'){
		var styleTableId = "tablereportStyle_"+styleId;
		var styleSign = "styleSign_"+styleId;
		
		if($("#"+styleTableId).hasClass("treegrid-collapsed")){
			$("#"+styleTableId).removeClass("treegrid-collapsed");
			$("#"+styleTableId).addClass("treegrid-expanded");
			var styleColorId = "tr[id*=tablereportStyleColor_"+styleId+"]";
			var skuId = "tr[id*=tablereportSku_"+styleId+"]";
			$(styleColorId).show();
			$(skuId).show();
		}else{
			$("#"+styleTableId).removeClass("treegrid-expanded");
			$("#"+styleTableId).addClass("treegrid-collapsed");
			var styleColorId = "tr[id*=tablereportStyleColor_"+styleId+"]";
			var skuId = "tr[id*=tablereportSku_"+styleId+"]";
			$(styleColorId).hide();
			$(skuId).hide();
		}
		
		if($("#"+styleSign).hasClass("icon-minus-sign")){
			$("#"+styleSign).removeCLass("icon-minus-sign");
			$("#"+styleSign).addCLass("icon-plus-sign");
		}else{
			$("#"+styleSign).removeClass("icon-plus-sign");
			$("#"+styleSign).addClass("icon-minus-sign");
		}
		
	}else if(currentRow == 'styleColor'){
		var styleColortableId = "tablereportStyleColor_"+styleId+"_"+styleColorId;
		var styleColorSign = "styleColorSign_"+styleId+"_"+styleColorId;
		
		if($("#"+styleColortableId).hasClass("treegrid-collapsed")){
			$("#"+styleColortableId).removeClass("treegrid-collapsed");
			$("#"+styleColortableId).addClass("treegrid-expanded");
			var skuId = "tr[id*=tablereportSku_"+styleId+"]";
			$(skuId).show();
		}else{
			$("#"+styleColortableId).removeClass("treegrid-expanded");
			$("#"+styleColortableId).addClass("treegrid-collapsed");
			var skuId = "tr[id*=tablereportSku_"+styleId+"]";
			$(skuId).hide();
		}
		
		if($("#"+styleColorSign).hasClass("icon-minus-sign")){
			$("#"+styleColorSign).removeClass("icon-minus-sign");
			$("#"+styleColorSign).addClass("icon-plus-sign");
		}else{
			$("#"+styleColorSign).removeClass("icon-plus-sign");
			$("#"+styleColorSign).addClass("icon-minus-sign");
		}
		
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
		<portlet:resourceURL var="saveContentColorAttributes" id="saveContentColorAttributes"></portlet:resourceURL>
		<portlet:actionURL var="copyOrinContent"> 
			<portlet:param name="action" value="copyOrinContent"/>
		</portlet:actionURL>		
		<body onload="timeOutPage();">
	
		<div id="content">
				<div id="main">
					
					<form id="copyORINForm" name="copyORINForm" action="${copyOrinContent}" method="post">
						<input type="hidden" name="groupType" id="hdnGroupType" value="${contentDisplayForm.styleInformationVO.groupingType}"/>
						<input type="hidden" name="groupId" id="hdnGroupId" value="${contentDisplayForm.styleInformationVO.orin}"/>
						<input type="hidden" name="styleId" id="copyORINstyleId" value="" required />
					</form>
					<form:form commandName="contentDisplayForm" method="post" action="${getChangedIPHCategoryData}" name="contentDisplayForm" id="contentDisplayForm">
				
				 
							 <div id="overlay_pageLoadingsave" style="display:none;position:absolute;top:200px; left:350px;">
										<img src="<%=response.encodeURL(request.getContextPath()+"/img/loading.gif")%>" height="100px;" />
							</div> 
							 <div id="overlay_pageLoadingapprove" style="display:none;position:absolute;top:1000px; left:350px;">
										<img src="<%=response.encodeURL(request.getContextPath()+"/img/loading.gif")%>" height="100px;" />
							</div> 
				
					        <input type="hidden" id="getIPHCategory" name="getIPHCategory" value=""></input>
					        <input type="hidden" id="petIdForWebservice" name="petIdForWebservice" value=""/>
							<input type=hidden id="actionParameter" name="actionParameter"/>
							<input type="hidden" id="username" name="username" value="${contentDisplayForm.userName}"/>
							<input type="hidden" id="dynamicCategoryId" name="dynamicCategoryId" value=""/>
							<input type=hidden id="stylePetId" name="stylePetId" value="${contentDisplayForm.styleInformationVO.orin}"/>
							<input type=hidden id="selectedOmniBrand" name="selectedOmniBrand"  value="${contentDisplayForm.omniBrandSelected}"/>
							<input type=hidden id="selectedCarsBrand" name="selectedCarsBrand" value="${contentDisplayForm.carBrandSelected}"/>
							<input type="hidden" id="complexPackEntryId" name="complexPackEntry" value="${contentDisplayForm.styleAndItsChildDisplay.complexPackEntry}"/>
							<input type="hidden" id="packColorEntryId" name="packColorEntry" value="${contentDisplayForm.styleAndItsChildDisplay.packColorEntry}"/>
							<input type="hidden" id="styleEntryId" name="styleEntry" value="${contentDisplayForm.styleAndItsChildDisplay.styleEntry}"/>
							<input type="hidden" id="styleColorEntryId" name="styleColorEntry" value="${contentDisplayForm.styleAndItsChildDisplay.styleColorEntry}"/>
							<input type="hidden"  id="roleNameId" name="roleName" value="${contentDisplayForm.roleName}"/>
							<input type=hidden id="selectedChannelExclusive" name="selectedChannelExclusive"  value=""/>	
							<input type=hidden id="selectedBelkExclusive" name="selectedBelkExclusive"  value=""/>	
							<input type=hidden id="selectedBopis" name="selectedBopis"  value=""/>	
							<input type=hidden id="selectedGWP" name="selectedGWP"  value=""/>	
							<input type=hidden id="selectedPWP" name="selectedPWP"  value=""/>	
							<input type=hidden id="selectedPYG" name="selectedPYG"  value=""/>	
							
							<portlet:resourceURL var="releseLockedPet" id ="releseLockedPet">  </portlet:resourceURL>
							<input type="hidden" id="releseLockedPet" name="releseLockedPet" value="${releseLockedPet}"></input>
							
							<!--Logout for Content Screen -->		
							 <div align="right" >	
								<c:out value="${contentDisplayForm.pepUserId}"/> &nbsp;	 
								<input type="button"   style="font-weight: bold" name="logout" value="Logout" 
                                                 onclick=logout_home('<c:out value="${contentDisplayForm.userName}"/>','${releseLockedPet}')  />     
							 </div>
							
							<div id="timeOutId" align="left" style="display: none;width:80%;">
								<p style="color: red; font-weight: bold; font-size: 14pt;">Your session has been ended due to inactivity</p> 
							</div>

							<!--Style Info Section starts over here  -->					  
							<div  class="cars_panel x-hidden">
								<div class="x-panel-header">
										<fmt:message key="label.groupinginfo" bundle="${display}"/>										
								</div>
								
								<input type="hidden" name="lockedPet" id="lockedPet" value="<c:out value="${contentDisplayForm.styleInformationVO.orin}" />" />
                                <input type="hidden" name="loggedInUser" id="loggedInUser" value="<c:out value="${contentDisplayForm.userName}" />" />
								
									
								<div class="x-panel-body">	
								        
									<!-- to show message from save content web service  -->
									
									<div id="ajaxResponseSaveContentPetAttribute"></div>
								    <div id="formIphMappingErrorMessage">
								     <c:if test="${not empty  contentDisplayForm.iphMappingMessage}">	
										     <table><tr><td><b><font size='2'><c:out value="${contentDisplayForm.iphMappingMessage}"/></font></b></td></tr></table>
									 </c:if>
									 <c:if test="${not empty  contentDisplayForm.copyContentMessage}">	
										     <table><tr><td><b><font size='2'><c:out value="${contentDisplayForm.copyContentMessage}"/></font></b></td></tr></table>
									 </c:if>
								   </div>
								   <div><table><tr><td><b><font size='2'><c:out value="${contentCopyStatusMessage}" /></font></b></td></tr></table></div>
									<c:if test="${contentDisplayForm.styleInformationVO.groupingType == 'CPG'}">
								    <div class="orin-popup-container">
										<input type="button" class="btn chevron-down" id="btnCopyORIN" value="Copy ORIN" style="width: 150px; padding: 6px;"/>
										<div class="clearfix"></div>
										<div class="orin-popup" id="orin-popup" style="display:none">
											<div class="popup-header x-panel-header x-unselectable">Copy ORIN</div>
											<div class="popup-body">
												
												<div class="form-conatiner">
													<div class="input-area">
														<label for="styleId">ORIN#</label>
														<input type="text" name="styleId" id="copyORINstyleIdTmp" value="" required maxlength="9" />
													</div>
													<div class="submit-area">
														<input type="button" id="doCopyOrin" value="Submit" class="action-button" />
													</div>
												</div>
											</div>
										</div>
									</div>
									</c:if>
								   	<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">								   	    
										<li class="txt_attr_name" style="width: 30%;">
											
										</li>				
									</ul> 
									  
									<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="label.grouping" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.orin}"/></li>				
										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="label.groupingType" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.groupingType}"/></li>
										<%-- <li class="txt_attr_name" style="width: 20%;"><fmt:message key="label.deptDescription" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.deptDescription}"/></li> --%>
									</ul>


									<ul class="pep_info"
										style="font-size: 11px; padding: 0 0 10px !important;">
										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="label.omnichannelVendor" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.omniChannelVendorIndicator}"/></li>
										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="labelDepartment" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.departmentId}"/></li>
										<li class="txt_attr_name" style="width: 20%;"><fmt:message key="labelDeptDescription" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.deptDescription}"/></li>										
									</ul>
			
									<ul class="pep_info"
										style="font-size: 11px; padding: 0 0 10px !important;">
										<li class="txt_attr_name"  style="width: 30%;"><fmt:message key="label.vendorProvidedImage" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.vendorProvidedImageIndicator}"/></li>
										<li class="ctxt_attr_name" style="width: 30%;"><fmt:message key="label.class" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.classId}"/></li>
										<li class="ctxt_attr_name" style="width: 20%;"><fmt:message key="labelClassDescription" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.classDescription}"/></li>
									</ul>
									
									<ul class="pep_info"
										style="font-size: 11px; padding: 0 0 10px !important;">
										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="label.vendorProvidedSample" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.vendorSampleIndicator}"/></li>
										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="label.vendorNumber" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.vendorId}"/></li>
										<li class="txt_attr_name" style="width: 20%;"><fmt:message key="label.vendorName" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.vendorName}"/></li>	
									</ul>
									
									<!-- Added by Sriharsha -->
									<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important; margin-bottom:2px">
										 <li class="txt_attr_name" style="width: 30%;">
										 <fmt:message key="lableOminChannelBrand" bundle="${display}"/>
										 <input type="hidden" name="omniBrandCount"  id="omniBrandCount" value="${contentDisplayForm.lstOmniChannelBrandVO.size()}"  /> 
										 <select style="width:52%;height: 27px;"  id="omnichannelbrand" name="omnichannelbrand" onclick="javascript:getSelectedOmniBrand(omnichannelbrand)">
											       
													<c:if test="${contentDisplayForm.lstOmniChannelBrandVO.size() == 1}">
														<option id="-1" value="-1">Please Select</option>    
												        <c:forEach var="omnibrandlist" items="${contentDisplayForm.lstOmniChannelBrandVO}">		
															<option id="${omnibrandlist.omniChannelBrandCode}" value="${omnibrandlist.omniChannelBrandCode}" selected="selected"> ${omnibrandlist.omniChannelBrandDesc}</option>             
														</c:forEach>	
													</c:if>		
													<c:if test="${contentDisplayForm.lstOmniChannelBrandVO.size() == 0 || contentDisplayForm.lstOmniChannelBrandVO.size() > 1}">
												        <option id="-1" value="-1">Please Select</option>    
												        <c:forEach var="omnibrandlist" items="${contentDisplayForm.lstOmniChannelBrandVO}">		
															<option id="${omnibrandlist.omniChannelBrandCode}" value="${omnibrandlist.omniChannelBrandCode}"> ${omnibrandlist.omniChannelBrandDesc}</option>             
														</c:forEach>	
													</c:if>												
										</select>
										</li>
										 <li class="txt_attr_name" style="width: 30%;"><fmt:message key="label.carBrand" bundle="${display}"/>
										 <input type="hidden" name="carBrandCount"  id="carBrandCount" value="${contentDisplayForm.lstCarBrandVO.size()}"  /> 
										 <select style="width:52%;height: 27px;"  id="carbrand" name="carbrand" onclick="javascript:getSelectedOCarsBrand(carbrand)">
													 <c:if test="${contentDisplayForm.lstCarBrandVO.size() == 1}">
													 <option id="-1" value="-1">Please Select</option>   
											        <c:forEach var="carbrandList" items="${contentDisplayForm.lstCarBrandVO}">												
														<option id="${carbrandList.carBrandDesc}" value="${carbrandList.carBrandDesc}" selected="selected"> ${carbrandList.carBrandDesc}</option>               
													</c:forEach>	
													</c:if>			
													 <c:if test="${contentDisplayForm.lstCarBrandVO.size() == 0 || contentDisplayForm.lstCarBrandVO.size() > 1}">
													 <option id="-1" value="-1">Please Select</option>   
											        <c:forEach var="carbrandList" items="${contentDisplayForm.lstCarBrandVO}">												
														<option id="${carbrandList.carBrandDesc}" value="${carbrandList.carBrandDesc}"> ${carbrandList.carBrandDesc}</option>               
													</c:forEach>	
													</c:if>												
										</select>
										</li>
										 <li class="txt_attr_name" style="width: 20%;"><fmt:message key="label.completionDate" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.completionDateOfStyle}"/></li>
									</ul>
									<!-- End Added by Sriharsha -->
									<div style="float:right">
									<input type="button" id="btnSubmit" name="btnsubmit" value="Submit" class="action-button" 
									<c:if test="${contentDisplayForm.roleName == 'readonly'}">
										disabled="disabled"
									</c:if>
									onclick="javascript:saveContentPetAttributesWebserviceResponse('${saveContentPetAttributes}','<c:out value="${contentDisplayForm.styleInformationVO.orin}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>', '<c:out value="${contentDisplayForm.productAttributesDisplay.dropDownList.size()}"/>','<c:out value="${contentDisplayForm.legacyAttributesDisplay.dropDownList.size()}"/>', '<c:out value="${contentDisplayForm.productAttributesDisplay.radiobuttonList.size()}"/>', '<c:out value="${contentDisplayForm.legacyAttributesDisplay.radiobuttonList.size()}"/>', '', '<c:out value="${contentDisplayForm.roleName}"/>', 'Submit','', true)" />
									
									<c:if test="${contentDisplayForm.roleName == 'readonly'}">									
									   
									        
						             <input type="button" name="Save" value="<fmt:message key="content.label.saveButton" bundle="${display}"/>" 
		  							       class="saveContentButtonGroup"  onclick="javascript:saveContent();" disabled="true"/>							                            
								      
		  							  
		  					          <input type="button" name="Close" value="<fmt:message key="content.label.closeButton" bundle="${display}"/>" 
		  							      class="saveContentButtonGroup"  onclick="javascript:goToWorkListDisplayScreen('<c:out value="${contentDisplayForm.userName}"/>','${releseLockedPet}');"/>
							
                                        
                                    </c:if>
                                     
									<c:if test="${contentDisplayForm.roleName == 'dca' || contentDisplayForm.roleName == 'vendor' }">										
										  
										        
									             <input type="button" name="Save" id="saveButtonId"  value="<fmt:message key="content.label.saveButton" bundle="${display}"/>" 
					  							       class="saveContentButtonGroup"  onclick="javascript:saveContentPetAttributesWebserviceResponse('${saveContentPetAttributes}','<c:out value="${contentDisplayForm.styleInformationVO.orin}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>', '<c:out value="${contentDisplayForm.productAttributesDisplay.dropDownList.size()}"/>','<c:out value="${contentDisplayForm.legacyAttributesDisplay.dropDownList.size()}"/>', '<c:out value="${contentDisplayForm.productAttributesDisplay.radiobuttonList.size()}"/>', '<c:out value="${contentDisplayForm.legacyAttributesDisplay.radiobuttonList.size()}"/>', '', '', 'Save','')"/>
									                            
											      
					  							  
					  					          <input type="button" name="Close" value="<fmt:message key="content.label.closeButton" bundle="${display}"/>" 
					  							      class="closeContentButton"  onclick="javascript:goToWorkListDisplayScreen('<c:out value="${contentDisplayForm.userName}"/>','${releseLockedPet}');"/>
										
									 </c:if>
									</div>
									<div style="float:right;margin-right:35px">
										<label style="margin-right:15px"><input type="radio" id="publisStatusCodeReadyForCopy" name="publisStatusCode" value="02" <c:if test="${contentDisplayForm.roleName == 'readonly'}"> disabled="disabled" </c:if> /> Ready For Copy</label>
										<label style="margin-right:15px"><input type="radio" id="publisStatusCodePublishToWeb" name="publisStatusCode" value="09" <c:if test="${contentDisplayForm.roleName == 'readonly'}"> disabled="disabled" </c:if> /> Publish to Web</label>
									</div>
                                </div>								 
							</div>
							<!--Product Details Section starts over here  -->	
							<div id="prod_detail_pnl" class="cars_panel x-hidden">
								<div class="x-panel-header">
								   <fmt:message key="label.groupingDetails" bundle="${display}"/>										
								</div>
								<div class="x-panel-body">
									<div id="prod_detail">
									<ul class="attrs">
									
									   	<c:if test="${contentDisplayForm.roleName == 'readonly'}">	
									   		
									   	       <li class="prod_name">
													<label><fmt:message key="label.groupingName" bundle="${display}"/></label>				
														
														<textarea  id ="txt_outfitName"  name="productName" class="maxChars spellcheck recommended" cols="126" rows="4" onkeyup="textCounter();" onblur="textCounter();" disabled="disabled"><c:out value="${contentDisplayForm.productDetailsVO.productName}" escapeXml="true"/></textarea>
														
															 <div align="center" class="text_counter">
																	Max Chars: <span class="max_chars_val">300</span>  &nbsp; &nbsp; 
																	Chars Count: <span id="outfit_name_length">0</span>
																	<br style="clear:both;" />
															 </div>
																				
														<br style="clear:both;"/>
												</li> 
											
												<li class="prod_desc">
													<label><fmt:message key="label.groupingDescription" bundle="${display}"/></label>			
													<textarea id="vendorStyleId"	class="maxChars spellcheck textCount recommended" name="productDescription" cols="126" rows="7" onkeyup="textCounter();" onblur="textCounter();"  disabled="disabled"><c:out value="${contentDisplayForm.productDetailsVO.productDescription}"/></textarea>		
														
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
												 
													<label><fmt:message key="label.groupingName" bundle="${display}"/></label>				
														
															<textarea  id ="txt_outfitName"  name="productName" class="maxChars spellcheck recommended" cols="126" rows="4" onkeyup="textCounter();" onblur="textCounter();" ><c:out value="${contentDisplayForm.productDetailsVO.productName}" escapeXml="true"/></textarea>
																 <div align="center" class="text_counter">
																		Max Chars: <span class="max_chars_val">300</span>  &nbsp; &nbsp; 
																		Chars Count: <span id="outfit_name_length">0</span>
																		<br style="clear:both;" />
																 </div>					
															<br style="clear:both;"/>
														
													
												</li> 
											
												<li class="prod_desc">
													<label><fmt:message key="label.groupingDescription" bundle="${display}"/></label>			
													<textarea id="vendorStyleId"	class="maxChars spellcheck textCount recommended" name="productDescription" cols="126" rows="7" onkeyup="textCounter();" onblur="textCounter();" ><c:out value="${contentDisplayForm.productDetailsVO.productDescription}"/></textarea>		
														
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
													<label><fmt:message key="label.groupingName" bundle="${display}"/></label>				
														
															<textarea  id ="txt_outfitName"  name="productName" class="maxChars spellcheck recommended" cols="126" rows="4" onkeyup="textCounter();" onblur="textCounter();" disabled="true"><c:out value="${contentDisplayForm.productDetailsVO.productName}" escapeXml="true"/></textarea>
																 <div align="center" class="text_counter">
																		Max Chars: <span class="max_chars_val">300</span>  &nbsp; &nbsp; 
																		Chars Count: <span id="outfit_name_length">0</span>
																		<br style="clear:both;" />
																 </div>					
															<br style="clear:both;"/>
														
													
												</li> 
											
												<li class="prod_desc">
													<label><fmt:message key="label.groupingDescription" bundle="${display}"/></label>			
													<textarea id="vendorStyleId"	class="maxChars spellcheck textCount recommended" name="productDescription" cols="126" rows="7" onkeyup="textCounter();" onblur="textCounter();" disabled="true"><c:out value="${contentDisplayForm.productDetailsVO.productDescription}"/></textarea>		
														
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
							
							
						
						<!-- Copy Attribute section starts here -->
							<!-- Method modified to show the Copy attribute details in screen. -->
							<!-- Modified For PIM Phase 2 - Regular Item Copy Attribute -->
							<!-- Date: 05/16/2016 -->
							<!-- Modified By: Cognizant -->
							<div class="cars_panel x-hidden" id="copyAttributeSection">
								
										<div class="x-panel-header">
											<fmt:message key="sectionCopyAttribute" bundle="${display}"/>
										</div>
										
										<div id="ajaxResponseStyleAttribute" class="x-panel-body" style="width:906px;">	
											
											<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
												<li class="txt_attr_name" style="width: 20%;"><b>Product Copy Text : </b> </li>
												<li class="txt_attr_name" style="width: 60%;"><textarea id="productCopyText" name="productCopyText" rows="8" cols="87" disabled="disabled"/>${contentDisplayForm.copyAttributeVO.productCopyText}</textarea></li>
												
											</ul>
											
											<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
												<li class="txt_attr_name" style="width: 20%;"><b>Copy_Line_1 : </b> </li>
												<li class="txt_attr_name" style="width: 60%;"><input type="text" size="87" id="copyLine1" name="copyLine1" value="${contentDisplayForm.copyAttributeVO.copyLine1}" disabled="disabled"/></li>												
											</ul>
											
											<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
												<li class="txt_attr_name" style="width: 20%;"><b>Copy_Line_2 : </b> </li>
												<li class="txt_attr_name" style="width: 60%;"><input type="text" size="87" id="copyLine2" name="copyLine2" value="${contentDisplayForm.copyAttributeVO.copyLine2}" disabled="disabled"/></li>												
											</ul>
											
											<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
												<li class="txt_attr_name" style="width: 20%;"><b>Copy_Line_3 : </b> </li>
												<li class="txt_attr_name" style="width: 60%;"><input type="text" size="87" id="copyLine3" name="copyLine3" value="${contentDisplayForm.copyAttributeVO.copyLine3}" disabled="disabled"/></li>												
											</ul>
											
											<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
												<li class="txt_attr_name" style="width: 20%;"><b>Copy_Line_4 : </b> </li>
												<li class="txt_attr_name" style="width: 60%;"><input type="text" size="87" id="copyLine4" name="copyLine4" value="${contentDisplayForm.copyAttributeVO.copyLine4}" disabled="disabled"/></li>												
											</ul>																																																																	
											
											<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
												<li class="txt_attr_name" style="width: 20%;"><b>Copy_Line_5 : </b> </li>
												<li class="txt_attr_name" style="width: 60%;"><input type="text" size="87" id="copyLine5" name="copyLine5" value="${contentDisplayForm.copyAttributeVO.copyLine5}" disabled="disabled"/></li>												
											</ul>
																						
											<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
												<li class="txt_attr_name" style="width: 20%;"><b>Copy_Material :</b> </li>
												<li class="txt_attr_name" style="width: 20%;"><input type="text" id="copyMaterial" name="copyMaterial" value="${contentDisplayForm.copyAttributeVO.material}" disabled="disabled"/></li>
												<li class="txt_attr_name" style="width: 20%;"><b>Copy_Care :</b> </li>
												<li class="txt_attr_name" style="width: 20%;"><input type="text" id="copyCare" name="copyCare" value="${contentDisplayForm.copyAttributeVO.care}" disabled="disabled"/></li>
											</ul>
											<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
												<li class="txt_attr_name" style="width: 20%;"><b>Copy_Country Of Origin :</b> </li>
												<li class="txt_attr_name" style="width: 20%;"><input type="text" id="countryOfOrigin" name="countryOfOrigin" value="${contentDisplayForm.copyAttributeVO.countryOfOrigin}" disabled="disabled"/></li>
												<li class="txt_attr_name" style="width: 20%;"><b>Copy_Exclusive :</b> </li>
												<li class="txt_attr_name" style="width: 20%;"><input type="text" id="copyExclusive" name="copyExclusive" value="${contentDisplayForm.copyAttributeVO.exclusive}" disabled="disabled"/></li>
											</ul>	
											<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
												<li class="txt_attr_name" style="width: 20%;"><b>Copy_Import Domestic :</b> </li>
												<li class="txt_attr_name" style="width: 20%;"><input type="text" id="copyImportDomestic" name="copyImportDomestic" value="${contentDisplayForm.copyAttributeVO.importDomestic}" disabled="disabled"/></li>
												<li class="txt_attr_name" style="width: 20%;"><b>Copy_CAProp65 Compliant :</b> </li>
												<li class="txt_attr_name" style="width: 20%;"><input type="text" id="copyCaprop65" name="copyCaprop65" value="${contentDisplayForm.copyAttributeVO.caprop65Compliant}" disabled="disabled"/></li>
											</ul>
											<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">	
												<li class="txt_attr_name" style="width: 20%;"><b>Copy_Product Name : </b> </li>
												<li class="txt_attr_name" style="width: 25%;"><input type="text" id="copyProductName" name="copyProductName" value="${contentDisplayForm.copyAttributeVO.copyProductName}" disabled="disabled"/></li>
												<li class="txt_attr_name" style="width: 20%;"></li>
												<li class="txt_attr_name" style="width: 25%;"></li>
											</ul>
											
									    </div>	
							</div>
							
							<!-- Copy Attribute section ends here -->
						
							<!-- Grouping Specific Attributes Section starts here -->
							<!-- <div class="cars_panel x-hidden">
								<div class="x-panel-header">
									<fmt:message key="label.groupingSpecificAttributes" bundle="${display}"/>
								</div>
								
								<div class="x-panel-body" style="width: 906px;">
									<ul class="pep_info">
										<li><fmt:message key="label.styleNumber" bundle="${display}"/></li>
										<li><c:out value="${contentDisplayForm.grouping.styleNumber}"/></li>
									</ul>
									<ul class="pep_info">
										<li></li>
									</ul>
									<ul class="pep_info">
										<li><fmt:message key="label.style.description" bundle="${display}"/></li>
										<li><c:out value="${contentDisplayForm.grouping.styleDescription}"/></li>
									</ul>
								</div>
							</div> -->
							<!-- Grouping Specific Attributes Section ends here -->
						
						
							<!--groupingComponents Section starts over here  -->					
							<div class="cars_panel x-hidden">
							
									<div class="x-panel-header">
											<fmt:message key="lable.groupingComponents" bundle="${display}"/>
									</div>
									<c:if test="${not empty noStylePetsFound}">
									    <div class="x-panel-body" style="width:906px;">	
									         	<table  cellpadding="0">
									         	   <tr>
									 		          <td><font color = "red"><c:out value="${noStylePetsFound}" /></font></td>
									 		       </tr>
									 		    </table>
									 	</div>
									</c:if>
									
									
									
									<div class="x-panel-body" style="width:906px;">	
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
												
											  </tr>
										   </thead>
										   <tbody>
										   <!-- Style and its child Style Color ,Style Color and its child SKU  logic  starts over here  -->
										    <input type="hidden" id="styleActionParameterId"      name="updateContentStatusStyleActionParameter"></input>
										    <input type="hidden" id="styleColorActionParameterId" name="updateContentStatusStyleColorActionParameter"></input>
										    
										   	
										   	
										   	<c:set var="subcount" value="260"/>
						                    <c:set var="countList" value="0"/>
						                    
						                    	<tr id="tablereport" class="treegrid-${countList}">
						                    		<td>
						                    			<c:if test="${fn:length(contentDisplayForm.styleAndItsChildDisplay.styleList) > 0}">
						                    			<span id="groupSign" class="treegrid-expander icon-minus-sign" onclick="toggleRows('group','','')"></span>
						                    			</c:if>
						                    		</td>
						                    		<td><c:out value="${contentDisplayForm.groupingNumber}"/></td>
						                    		<td></td>
						                    		<td></td>
						                    		<td></td>
						                    		<td></td>
						                    		<td class="contentStatusId"><c:out value="${contentDisplayForm.styleInformationVO.contentStatus}"/></td>
						                    		<td></td>
						                    	
						                    	
												   	<c:forEach items="${contentDisplayForm.styleAndItsChildDisplay.styleList}" 
									                             var="styleDisplayList"  varStatus="status">							                             
									                           
									                         <portlet:actionURL name="loadStyleAttribute" var="loadStyleAttributeURL">	
									                            <portlet:param name="action" value="loadStyleAttributeAction"></portlet:param>						                         
									                         	<portlet:param name="orinNumber" value="${styleDisplayList.orinNumber}"></portlet:param>
									                         </portlet:actionURL>
									                         
															<tr id="tablereportStyle_${styleDisplayList.orinNumber}"  class="treegrid-${countList} treegrid-expanded">	
																<td>
																	<c:if test="${fn:length(styleDisplayList.styleColorList)>0}">
																		<span class="treegrid-indent"></span>
																		<span id="styleSign_${styleDisplayList.orinNumber}" class="treegrid-expander icon-minus-sign" onclick="toggleRows('style','<c:out value="${styleDisplayList.orinNumber}"/>','')">
																	</c:if>
																</td>													
															 	<td>${styleDisplayList.orinNumber}</td>
															 	<input type="hidden" id="styleOrinNumberId" name="styleOrinNumber" value="${styleDisplayList.orinNumber}"></input>
																<td><c:out value="${contentDisplayForm.styleInformationVO.styleId}"/></td>
																<td><c:out value="${styleDisplayList.color}"/></td>
																<td><c:out value="${styleDisplayList.vendorSize}" /></td>
																<td><c:out value="${styleDisplayList.omniSizeDescription}" /></td>											
															
																<td><span ><c:out value="${styleDisplayList.contentStatus}"/></span></td>	
																
																  
																<input type="hidden" id="styleContentStatusId" name="styleContentStatus" value="${styleDisplayList.contentStatus}"></input>								
																<td><c:out value="${styleDisplayList.completionDate}" /></td>
															
															</tr>
													
													         <% int colorRows=1;  %>
															<c:forEach items="${styleDisplayList.styleColorList}" var="styleDisplayColorList"  varStatus="styleColorList">	
															          <c:set var="subcount" value="${subcount + styleColorList.count}" />																					
															       	  <portlet:resourceURL var="getStyleColorAttributeDetails" id ="getStyleColorAttributeDetails">		
																		             	<portlet:param name="styleColorOrinNumber" value="${styleDisplayColorList.orinNumber}"></portlet:param>
									                                  </portlet:resourceURL>
									                                  <c:if test="${styleDisplayColorList.styleNumber == styleDisplayList.orinNumber}">
																	          <tr id="tablereportStyleColor_${styleDisplayList.orinNumber}_${styleDisplayColorList.orinNumber}" name="treegrid-${subcount} treegrid-parent-${countList}"  class="treegrid-${subcount} treegrid-parent-${countList}  treegrid-expanded">               
																			   <td>
																			   		<c:if test="${fn:length(styleDisplayColorList.skuList)>0}">
																			   			<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
																						<span class="treegrid-indent"></span>
																						<span id="styleColorSign_${styleDisplayList.orinNumber}_${styleDisplayColorList.orinNumber}" class="treegrid-expander icon-minus-sign" onclick="toggleRows('styleColor','<c:out value="${styleDisplayList.orinNumber}"/>','<c:out value="${styleDisplayColorList.orinNumber}" />')"></span>
																					</c:if>
																			   </td> 
																			   <c:choose>
																			   <c:when test="${contentDisplayForm.roleName == 'vendor'}">
																			  	 <td>${styleDisplayColorList.orinNumber}</td>
																			   </c:when>
																			   <c:when test="${contentDisplayForm.roleName == 'dca'}">
																			  	 <td>${styleDisplayColorList.orinNumber}</td>
																			   </c:when>
																			   </c:choose>
																			   <input type="hidden" id="selectedStyleColorOrinNumber" name="selectedStyleColorOrinNumber" value=""></input>   
																			   															  														  
																			   <input type="hidden" id="styleColorOrinNumberId" name="styleColorOrinNumber" value="${styleDisplayColorList.orinNumber}"></input>                                                                                       
																		
																			   <td><c:out value="${contentDisplayForm.styleInformationVO.styleId}"/></td>                                                                                         
																			   <td><c:out value="${styleDisplayColorList.color}"/></td> 
																			   <td><c:out value="${styleDisplayColorList.vendorSize}" /></td>                                                                                        
																			   <td><c:out value="${styleDisplayColorList.omniSizeDescription}" /></td>  
																			   <input type="hidden" id="styleColorContentStatusId" name="styleColorContentStatus" value="${styleDisplayColorList.contentStatus}"></input> 	
																			   <input type="hidden" id="styleColorCountId" name="styleColorCount" value="<c:out value="${styleDisplayList.styleColorList.size()}"/>"></input> 
																			   <td  id="petColorRow_id<%= colorRows %>"  class="petColorRowClass<%= colorRows %>"><c:out value="${styleDisplayColorList.contentStatus}"/></td>														      
																			   <td><c:out value="${styleDisplayColorList.completionDate}" /></td> 
																			                                                                 
																		   </tr>   
																   	 </c:if>
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
																	       <c:if test="${styleColorsChildSkuList.styleId == styleDisplayList.orinNumber}">
																		       <tr id="tablereportSku_${styleDisplayList.orinNumber}_${styleDisplayColorList.orinNumber}" name="tablereportSku" class="treegrid-${subcount} treegrid-parent-${countList} treegrid-expanded">               
																		          <td>&nbsp;</td>
																		          <td>${styleColorsChildSkuList.orin}</td> 															          								      
																		          <td><c:out value="${contentDisplayForm.styleInformationVO.styleId}"/></td>                                                                                         
																		          <td><c:out value="${styleColorsChildSkuList.color}"/></td> 
																		          <td><c:out value="${styleColorsChildSkuList.vendorSize}" /></td>                                                                                        
																		          <td><c:out value="${styleColorsChildSkuList.omniChannelSizeDescription}" /></td>  															         
																		       	  <td><c:out value="${styleColorsChildSkuList.contentStatus}"/></td> 												                                        
																		          <td><c:out value="${styleColorsChildSkuList.completionDate}" /></td> 	
																		           										    		 
																	          </tr> 
																          </c:if>  
																   
																	            	
															          </c:forEach>
															           <% colorRows++;  %>  	                                                            
													                  
														    </c:forEach>
														    
														    
														  
														    <c:set var="countList" value="${countList+1}" />
									               	</c:forEach> 
													<c:forEach items="${contentDisplayForm.styleAndItsChildDisplay.groupList}" var="groupDisplayList"  varStatus="groupList">
												    	<tr id="tablereportGroup" class="treegrid-${countList}">
												    		<td></td>
												    		<td><c:out value="${groupDisplayList.orin}"/></td>
												    		<td><c:out value="${groupDisplayList.styleNumber}"/></td>
												    		<td><c:out value="${groupDisplayList.color}"/></td>
												    		<td><c:out value="${groupDisplayList.vendorSizeCodeDesc}"/></td>
												    		<td></td>
												    		<td><c:out value="${groupDisplayList.contentStatus}"/></td>
												    		<td><c:out value="${groupDisplayList.completionDate}"/></td>
												    	</tr>
												    </c:forEach>
												</tr>
											</tbody>			
		                                   </table>			
															
									</div>

									
							</div>
							
									  
				
						
					
						<!--Product  Attributes Section starts here  -->	
						<div class="cars_panel x-hidden"  id="productAttributeSection">
					 <form:form commandName="contentDisplayForm1" modelAttribute="fdForm" method="post" action="${formAction}" id="contentDisplayForm1"> 
							
									<div class="x-panel-header">
										<fmt:message key="label.groupingProductAttributes" bundle="${display}"/>
									</div>
									
									<div class="x-panel-body" style="width:906px;">	
									        <table  cellpadding="0"  class="table tree2 table-bordered table-condensed" id="productAttr_table">
									            <div id="webserviceMessageDescription"><tr><font color="blue"> </font></tr></div>
									           
									            <tr>
									                <portlet:resourceURL id="getIPHCategory" var="getIPHCategory"></portlet:resourceURL>
									                <td><p>* Select IPH Category:</p></td>
													<td>
													    <input type=hidden id="mapItemToIphActionParameter" name="mapItemToIphActionParameter"/>
													    <input type=hidden id="iphCategoryId" name="iphCategoryName"/>	
													    <input type="hidden" id="petId" name="petId" value="${contentDisplayForm.styleInformationVO.orin}"/>	
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
					                                               		<c:choose>
					                                               			<c:when test="${dynamicCategoryId == categoryMap.key}">
					                                               				<option value="${categoryMap.key}"  selected="selected" ><c:out value="${categoryMap.value}"/></option>
					                                               			</c:when>
					                                               			<c:otherwise>
					                                               				<option value="${categoryMap.key}"><c:out value="${categoryMap.value}"/></option>
					                                               			</c:otherwise>
					                                               		</c:choose>                                                                             
					                                                        
					                                               </c:forEach>                                                                                           
			                                               </select>
			                                                                                              

													    </c:if>												    
													    
													 </td>   
													
												</tr>
												<!-- Changes made Cognizant for sorting the attributes starts. -->
												  <% int i=1;  %>
												 <% int k=1;  %>
												 <% int j=1;  %>
													<c:forEach items= "${contentDisplayForm.productAttributesDisplay.displayList}"  var="categoryDisplayList" >
													
															<c:if test="${categoryDisplayList.attributeFieldType == 'Drop Down'}">																				
															<tr>
																<td><c:if test="${categoryDisplayList.isMandatory == 'Yes'}">* </c:if>
																<c:out value="${categoryDisplayList.displayName}"/></td>
																
																<input type="hidden" name="dropdownAttributeNameXpath"  id="dropdownAttributeNameXpath_id<%= i %>"  value="${categoryDisplayList.attributeName}#${categoryDisplayList.attributePath}" />	
																<input type="hidden" name="isPIMMandatory_id"  id="isPIMMandatory_id<%= i %>"  value="${categoryDisplayList.isMandatory}" />															
																<td>
																	<input type="hidden" name="dropdownhidden" id="dropdownhidden_id<%= i %>" value="" /> 
																	
																	<!-- Change for Multi/Single Drop Down Starts -->
																	
																	<c:if test="${categoryDisplayList.maximumOcurrence == '1'}">
																	<select id="dropDownsId_id<%= i %>" name="dropDownsName_id<%= i %>" onchange="javascript:dropDownValues(dropDownsName_id<%= i %>, <%= i %>, '<c:out value="${contentDisplayForm.productAttributesDisplay.dropDownList.size()}"/>')">
																      
																	<option id="-1" value="-1">Please Select</option>
																      
																        <c:forEach var="dropDownMap" items="${categoryDisplayList.dropDownValuesMap}">												
																			<c:set var="highlited" value=""/>
																			<c:forEach  var="savedDropDownMap"  items="${categoryDisplayList.savedDropDownValuesMap}" >
																				<c:set var="stSelected" value=""/>
																				<c:if test="${dropDownMap.key ==savedDropDownMap.key}">
																					<c:set var="stSelected" value="selected='selected'"/>
																					 <option value="${dropDownMap.key}"  <c:out value="${stSelected}"/> ><c:out value="${dropDownMap.value}"  escapeXml="false"/></option> 
																					 <c:set var="highlited" value="${savedDropDownMap.key}"/>
																				</c:if>                                                     
					                                                        							</c:forEach>
																			  <c:if test="${dropDownMap.key != highlited}">
																			  	<option value="${dropDownMap.key}" /> <c:out value="${dropDownMap.value}"  escapeXml="false"/></option>
																			</c:if>			
																		</c:forEach>														
															        	</select>
																	</c:if>
																	
																	<c:if test="${categoryDisplayList.maximumOcurrence !='1'}">
																	<select style="width:58%; height: 80px;"  multiple="multiple" id="dropDownsId_id<%= i %>" name="dropDownsName_id<%= i %>" onchange="javascript:dropDownValues(dropDownsName_id<%= i %>, <%= i %>, '<c:out value="${contentDisplayForm.productAttributesDisplay.dropDownList.size()}"/>')">
																        
																        <c:forEach var="dropDownMap" items="${categoryDisplayList.dropDownValuesMap}">												
																			<c:set var="highlited" value=""/>
																			<c:forEach  var="savedDropDownMap"  items="${categoryDisplayList.savedDropDownValuesMap}" >
																				<c:set var="stSelected" value=""/>
																				<c:if test="${dropDownMap.key ==savedDropDownMap.key}">
																					<c:set var="stSelected" value="selected='selected'"/>
																					 <option value="${dropDownMap.key}"  <c:out value="${stSelected}"/> escapeXml="false"><c:out value="${dropDownMap.value}"  escapeXml="false"/></option> 
																					 <c:set var="highlited" value="${savedDropDownMap.key}"/>
																				</c:if>                                                     
					                                                        </c:forEach>
																			  <c:if test="${dropDownMap.key != highlited}">
																			  	<option value="${dropDownMap.key}"/> <c:out value="${dropDownMap.value}"  escapeXml="false"/></option>
																			</c:if>			
																		</c:forEach>														
															        	</select>
																	</c:if>
																	<!-- Change for Multi/Single Drop Down Ends -->																	
														        		</td>															
																										
															</tr>
															<% i++; %>
															</c:if>	
														                                                               
													
													<input type="hidden" id="paDropDownCounter" name="paDropDownCounter" value="${contentDisplayForm.productAttributesDisplay.dropDownList.size()}"></input>
												    
												    
												      <c:if test="${categoryDisplayList.attributeFieldType == 'Radio Button'}">
												     
															<input type="hidden" name="paRadioAttributeXpath"  id="paRadioAttributeXpath_id<%= k%>"  value="${categoryDisplayList.attributeName}#${categoryDisplayList.attributePath}" />
															<input type="hidden" name="radioButtonHidden_id" id="radioButtonHidden_id<%= k %>" value="" />
															<input type="hidden" name="isPIMRadioMandatory_id"  id="isPIMRadioMandatory_id<%= k %>"  value="${categoryDisplayList.isMandatory}" />	
															 <div id="radiofieldSet_id<%=k %>" >
															  <tr>
																<td><c:if test="${categoryDisplayList.isMandatory == 'Yes'}">* </c:if>
																<c:out value="${categoryDisplayList.displayName}"/></td>
																<td> <table><tr><td> <fieldset id="radiofieldSet_id<%=k %>" onclick="javascript:pimRadioButtonValues(<%= k %>, '<c:out value="${contentDisplayForm.productAttributesDisplay.radiobuttonList.size()}"/>')">
																  <% int z=1;  %>
																	<c:forEach var="dropDownMap" items="${categoryDisplayList.radioButtonValuesMap}">	
																		<c:if test="${categoryDisplayList.savedRadioButtonValuesMap.size() > 0 }" >
																			<c:forEach var="savedDropDownMap" items="${categoryDisplayList.savedRadioButtonValuesMap}">	
																		 	 <input type="radio"  id="paRadio_Id<%=k %><%=z %>"  name="paRadio<%=k %>" value="${dropDownMap.key}" <c:if test="${savedDropDownMap.key == dropDownMap.key}"> checked="checked"  </c:if>/><c:out value="${dropDownMap.value}" escapeXml="false"/>  &nbsp;&nbsp;		
																		 	 </c:forEach>	
																		</c:if>
																		<c:if test="${categoryDisplayList.savedRadioButtonValuesMap.size() == 0 }" >
																			<input type="radio"  id="paRadio_Id<%=k %><%=z %>"  name="paRadio<%=k %>" value="${dropDownMap.key}" escapeXml="false" /><c:out value="${dropDownMap.value}" escapeXml="false"/>  &nbsp;&nbsp;	
																		</c:if>
															     <% z++; %>
															     </c:forEach>   
															    </fieldset></td></tr></table> 
															     </td>
														      </tr>	           
														         </div>                              
 														<% k++; %>
													
												    </c:if>
												    <input type="hidden" id="paRadioButtonCounter" name="paRadioButtonCounter" value="${contentDisplayForm.productAttributesDisplay.radiobuttonList.size()}"></input>
												    
												    
					    							
                                                   <input type="hidden" name="paTextAttributeCount"  id="paTextAttributeCount" value="${contentDisplayForm.productAttributesDisplay.textFieldList.size()}"  /> 
                                                  <c:if test="${categoryDisplayList.attributeFieldType == 'Text Field'}">
                                                         <input type="hidden" name="isPIMTextMandatory_id"  id="isPIMTextMandatory_id<%= j %>"  value="${categoryDisplayList.isMandatory}" />	
                                                         <input type="hidden" name="textFieldHidden" id="textFieldHidden_id<%= j %>" value="" />
                                                         <input type="hidden" name="paTextAttributeXpath"  id="paTextAttributeXpath_id<%= j %>"  value="${categoryDisplayList.attributeName}#${categoryDisplayList.attributePath}" />
                                                             <tr>                                                                                         
                                                                       <td><c:if test="${categoryDisplayList.isMandatory == 'Yes'}">* </c:if><c:out value="${categoryDisplayList.displayName}"/></td>
                                                                       <td><input type="text" id="paText_Id<%=j%>" name="paText_Id<%=j%>" value="${categoryDisplayList.attributeFieldValue}" /></td>                                                                                                                                     
                                                             </tr> 
								<% j++; %>
                                                   </c:if>
                                                        
                                                        
                                                       
                                                 </c:forEach>

										   </table>		
															
									</div>				
							
						</div>	
						<!-- Changes made Cognizant for sorting the attributes ends. -->
						
						<!--Legacy  Attributes Section starts here  -->	
						<div class="cars_panel x-hidden" id="legacyAttributeSection">
							
									<div class="x-panel-header">
										<fmt:message key="sectionLegacyAttribute" bundle="${display}"/>
									</div>
									
									<div class="x-panel-body" style="width:906px;">	
										<table id="legacyAttributeTable" class="table tree2 table-bordered table-condensed"  border="1" cellspacing="1" cellpadding="1">
											<% int bmDropDownCount=1;  %>
													  <% int c=1;  %>
													  <% int bmTextFieldCount=1; %>
													<c:forEach items= "${contentDisplayForm.legacyAttributesDisplay.displayList}"  var="blueMartiniAllList" >
													
															<c:if test="${blueMartiniAllList.attributeFieldType == 'Drop Down'}">																				
															<tr>
																<td><c:if test="${blueMartiniAllList.isMandatory == 'Yes'}">* </c:if><c:out value="${blueMartiniAllList.displayName}"/></td>
																<input type="hidden" name="isBMMandatory_id"  id="isBMMandatory_id<%= bmDropDownCount %>"  value="${blueMartiniAllList.isMandatory}" />	
																<input type="hidden" name="blueMartiniDropDownAttributeNameXpath"  id="blueMartiniDropDownAttributeNameXpath_id<%= bmDropDownCount %>"  value="${blueMartiniAllList.attributeName}#${blueMartiniAllList.attributePath}" />															
																<td>
																	<input type="hidden" name="blueMartiniDropDownHidden" id="blueMartiniDropDownHidden_id<%= bmDropDownCount %>" value="" />
																	
																	<!-- Change for Multi/Single Drop Down Starts. -->
																	<c:if test="${blueMartiniAllList.maximumOcurrence =='1'}">
																	<select id="bmDropDownsId_id<%= bmDropDownCount %>" name="bmDropDownsName_id<%= bmDropDownCount %>" onchange="javascript:bmDropDownValues(bmDropDownsId_id<%= bmDropDownCount %>, <%= bmDropDownCount %>, '<c:out value="${contentDisplayForm.legacyAttributesDisplay.dropDownList.size()}"/>')">
																       		 
																			<option id="-1" value="-1">Please Select</option>
																		 
																		 	<c:forEach var="dropDownMap" items="${blueMartiniAllList.dropDownValuesMap}">												
																			<c:set var="highlited" value=""/>
																			<c:forEach  var="savedDropDownMap"  items="${blueMartiniAllList.savedDropDownValuesMap}" >
																				<c:set var="stSelected" value=""/>
																				 	<c:if test="${dropDownMap.key ==savedDropDownMap.key}">
																				 	<c:set var="stSelected" value="selected='selected'"/>
																				 	 <option value="${dropDownMap.key}"  <c:out value="${stSelected}"/> ><c:out value="${dropDownMap.value}"  escapeXml="false"/></option>
																				 		 <c:set var="highlited" value="${savedDropDownMap.key}"/>
																				 	</c:if>
					                                                        							</c:forEach>
					                                                      	 					<c:if test="${dropDownMap.key != highlited}">
																			  	<option value="${dropDownMap.key}"  escapeXml="false" ><c:out value="${dropDownMap.value}" escapeXml="false"/></option>
																			</c:if>																			  
																		</c:forEach>														
															       	 </select>
																	</c:if>
																	
																	<c:if test="${blueMartiniAllList.maximumOcurrence !='1'}">
																	<select style="width:58%; height: 80px;"  multiple="multiple" id="bmDropDownsId_id<%= bmDropDownCount %>" name="bmDropDownsName_id<%= bmDropDownCount %>" onchange="javascript:bmDropDownValues(bmDropDownsId_id<%= bmDropDownCount %>, <%= bmDropDownCount %>, '<c:out value="${contentDisplayForm.legacyAttributesDisplay.dropDownList.size()}"/>')">
																        <c:forEach var="dropDownMap" items="${blueMartiniAllList.dropDownValuesMap}">												
																			<c:set var="highlited" value=""/>
																			<c:forEach  var="savedDropDownMap"  items="${blueMartiniAllList.savedDropDownValuesMap}" >
																				<c:set var="stSelected" value=""/>
																				 	<c:if test="${dropDownMap.key ==savedDropDownMap.key}">
																				 	<c:set var="stSelected" value="selected='selected'"/>
																				 	 <option value="${dropDownMap.key}"  <c:out value="${stSelected}"/> escapeXml="false" ><c:out value="${dropDownMap.value}"  escapeXml="false"/></option>
																				 		 <c:set var="highlited" value="${savedDropDownMap.key}"/>
																				 	</c:if>
					                                                        </c:forEach>
					                                                      	 			<c:if test="${dropDownMap.key != highlited}">
																			  	<option value="${dropDownMap.key}"  ><c:out value="${dropDownMap.value}" escapeXml="false"/></option>
																			</c:if>																			  
																		</c:forEach>														
															        	</select>
																	</c:if>
																	
																	<!-- Change for Multi/Single Drop Down Ends. -->																	
														        </td>															
																										
															</tr>	
															<% bmDropDownCount++; %>
														      </c:if>                                                          
													
												   
												    <input type="hidden" id="bmDropDownCounter" name="bmDropDownCounter" value="${contentDisplayForm.legacyAttributesDisplay.dropDownList.size()}"></input>
												    
												    
												     <c:if test="${blueMartiniAllList.attributeFieldType == 'Radio Button'}">
															<input type="hidden" name="bmRadioAttributeXpath"  id="bmRadioAttributeXpath_id<%= c%>"  value="${blueMartiniAllList.attributeName}#${blueMartiniAllList.attributePath}" />
															 <input type="hidden" name="bmradioButtonHidden_id" id="bmradioButtonHidden_id<%= c%>" value="" />
															<input type="hidden" name="isBMRadioMandatory_id"  id="isBMRadioMandatory_id<%= c %>"  value="${blueMartiniAllList.isMandatory}" />
															 <div id="bmradiofieldSet_id<%=c %>" >
															  <tr>
																<td><c:if test="${blueMartiniAllList.isMandatory == 'Yes'}">* </c:if><c:out value="${blueMartiniAllList.displayName}"/></td>
																<td> <table><tr><td> <fieldset id="bmradiofieldSet_id<%=c %>" onclick="javascript:bmRadioButtonValues(<%= c %>, '<c:out value="${contentDisplayForm.legacyAttributesDisplay.radiobuttonList.size()}"/>')">
																  <% int l=1;  %>
																	<c:forEach var="bmdropDownMap" items="${blueMartiniAllList.radioButtonValuesMap}">
																		<c:if test="${blueMartiniAllList.savedRadioButtonValuesMap.size() > 0 }" >
																		<c:forEach var="bmsavedDropDownMap" items="${blueMartiniAllList.savedRadioButtonValuesMap}">	
																	 	 <input type="radio"  id="bmRadio_Id<%=c %><%=l %>"  name="bmRadio<%=c %>" value="${bmdropDownMap.key}" escapeXml="false" <c:if test="${bmsavedDropDownMap.key == bmdropDownMap.key}"> checked="checked"  </c:if>/><c:out value="${bmdropDownMap.value}" escapeXml="false"/>  &nbsp;&nbsp;		
																	 	 </c:forEach>	
																	 	</c:if>			
																	 	<c:if test="${blueMartiniAllList.savedRadioButtonValuesMap.size() == 0 }" >
																	 	<input type="radio"  id="bmRadio_Id<%=c %><%=l %>"  name="bmRadio<%=c %>" value="${bmdropDownMap.key}" escapeXml="false"/><c:out value="${bmdropDownMap.value}" escapeXml="false"/>  &nbsp;&nbsp;
																	 	</c:if>
																	<% l++; %>					
															     </c:forEach>   
															    </fieldset></td></tr></table> 
															     </td>
														      </tr>	           
														         </div> 
														 <% c++; %>                             
												    </c:if>
												     <input type="hidden" id="bmRadioButtonCounter" name="bmRadioButtonCounter" value="${contentDisplayForm.legacyAttributesDisplay.radiobuttonList.size()}"></input>
												     
  													
  													<c:if test="${blueMartiniAllList.attributeFieldType == 'Text Field'}">
															<tr>
															    <input type="hidden" name="bmTextAttributeCount"  id="bmTextAttributeCount" value="${contentDisplayForm.legacyAttributesDisplay.textFieldList.size()}"  />
																<input type="hidden" name="blueMartiniTextAttributeNameXpath"  id="blueMartiniTextAttributeNameXpath_id<%= bmTextFieldCount %>"  value="${blueMartiniAllList.attributeName}#${blueMartiniAllList.attributePath}" />
																<input type="hidden" name="bmTextFieldHidden" id="bmTextFieldHidden_id<%= bmTextFieldCount %>" value="" />
																<input type="hidden" name="isBMTextMandatory_id"  id="isBMTextMandatory_id<%= bmTextFieldCount %>"  value="${blueMartiniAllList.isMandatory}" />
																<td><c:if test="${blueMartiniAllList.isMandatory == 'Yes'}">* </c:if><c:out value="${blueMartiniAllList.displayName}"/></td>
																<td><input type="text" id="blueMartiniText_Id<%=bmTextFieldCount%>" name="blueMartiniTextName_Id<%=bmTextFieldCount%>" value="${blueMartiniAllList.attributeFieldValue}" /></td>																
																										
															</tr>	
													<% bmTextFieldCount++; %>
												    </c:if>
												    
												    
												   												    
													 </c:forEach>	
									<input type="hidden" id="paDisplayListCounter" name="paDisplayListCounter" value="${contentDisplayForm.legacyAttributesDisplay.displayList.size()}"></input>													
									</table>
															
									</div>				
									
						</div>

							</form:form>
						
						
						
							
						
								<!--History Display starts here  -->	
						<div class="cars_panel x-hidden collapsed">
							
									<div class="x-panel-header">
										 	<fmt:message key="sectionHistory" bundle="${display}"/>
									</div>
									
									<div class="x-panel-body" style="width:906px;">	
									
											<table id="historyID" class="table tree2 table-bordered table-striped table-condensed"  border="1" cellspacing="1" cellpadding="1">																			
												 <thead>
												  <tr>		
												 	 <th>
														PET 
													 </th>								
													 <th>
														Date PET Created
													 </th>
													 <th>
														PET Created by
													 </th>
													 <th>
														Content Last Updated Date
													 </th>
													  <th>
														Content Last Updated by 
													 </th>
													 <th>
														Content Last Updated Status at Style
													 </th>
												  </tr>
											   </thead>
											   <tbody>
											   <c:forEach var="pepHistory" varStatus="status" items="${contentDisplayForm.contentHistoryList}">
												  <tr>				
												     <td><c:out value="${pepHistory.orinNumber}"/></td>										
													 <td><c:out value="${pepHistory.contentCreatedDate}"/></td>										
													 <td><c:out value="${pepHistory.contentCreatedBy}"/></td>
													  <td><c:out value="${pepHistory.contentLastUpdatedDate}"/></td>
													 <td><c:out value="${pepHistory.contentLastUpdatedBy}"/></td>
													 <td><c:out value="${pepHistory.contentStatus}"/></td>																				
												  </tr>	
												  </c:forEach>							 
												</tbody>			
								          </table>		
															
									</div>				
									
						</div>	
					</form:form>
				</div>
		</div>
		</body>