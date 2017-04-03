<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<portlet:resourceURL id="invalidate" var="logouturl" />	

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


	<script  type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/contentScreen.js"></script>

	
  	<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
 	<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	
	
    <script src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.min.js")%>"></script>
    <script type="text/javascript" src="<%=response.encodeURL(request.getContextPath()+"/js/jquery.treegrid.js")%>"></script>
  
  <style type="text/css">
  .confirm {
  display: inherit;
  }
    body{
    overflow-y : auto;
  }

  header{
    position: fixed;
    width: 100%;
    z-index: 10;
    background-color: #52527a;
  }
  .component-container{
    margin-top: 107px;
  }
  .freeze-cntr{
    position: fixed;
    background-color: #52527A;
    top: 87px;
    width: 949px;
    padding: 10px 0px;
    z-index: 100;
  }
  .freeze-cntr-right input[type="button"]{
    margin: 0 10px;
  }
  #ajaxResponseSaveContentPetAttribute{
    padding: 5px 10px;
    float:left;
    color:white;
    margin-top:5px;
    width: 929px;
          }
  </style>
   <script type="text/javascript">
		$(function(){
			/** Modified For PIM Phase 2 
				* - code to handle copy orin popup visibility 
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
					confirmationMessage = false;
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
			}, 10000);
		});
	</script>
  
    <script type="text/javascript">
    var inputChanged = true;
   
            $(document).ready(function() {
            	 //code for alternate colors for Product and legacy attribute table rows-START
			$("#productAttr_table tr:even").css("background-color", "#F9F9F9");
			$("#productAttr_table tr:odd").css("background-color", "#FFFFFF");
			<%-- VP21
			$("#legacyAttributeTable tr:even").css("background-color", "#F9F9F9");
			$("#legacyAttributeTable tr:odd").css("background-color", "#FFFFFF");
			VP21 --%>
            //code for alternate colors for Product and legacy attribute table rows-END
            	//Logic for disabling the Product Name , Product Description Text  Area ,Style Submit button ,omni channel ,car brand
            	// when the Style Pet Status is completed or closed
            	  var  pepUserRoleName = "";
            	if(document.getElementById("roleNameId") != null){
            		    pepUserRoleName = document.getElementById("roleNameId").value;  
            	}
				//VP21
            	      if(pepUserRoleName != "vendor") {
						  $("#legacyAttributeTable tr:even").css("background-color", "#F9F9F9");
						  $("#legacyAttributeTable tr:odd").css("background-color", "#FFFFFF");
					  }
				//VP21
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
                        				
                    		
                    			var selectedChannelEx="${requestScope.selectedChannelExclusive}";
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
                       		}
                       		
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
		            		  
		            		  disableStyleLevelAttributes();
		            		  
		            		  // Disable Product & Legacy Attributes
		            		  disabledProductAndLegacyAttributes();
		            		  
	            		}
	                       else if(stylePetContentStatus=='Initiated')
	                    	   { 
	                    	  	  enabledStyleLevelAttributes();
			            		 
			            		  // Enable Product & Legacy Attriutes
			            		  enableProductAndLegacyAttributes();
			            	  
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
                       disableSkuLevelAttributes();
        		  }// End of Vendor
            
            	//End of logic for vendor
            	
            	//Start of logic for role dca  for enabling and disabling the action buttons
            	 if(pepUserRoleName == "dca")
        		  {
            	      //Logic for Style
	            	  if(stylePetContentStatus=='Completed' || stylePetContentStatus=='Closed' || stylePetContentStatus=='Deactivated')
	            		{ 
	            			  disableStyleLevelAttributes();
		            		  
		            		  // Disable Product & Legacy Attriutes
		            		  disabledProductAndLegacyAttributes();
	            		}
	            	  
	            	  else  if(stylePetContentStatus=='Initiated' || stylePetContentStatus=='Ready_For_Review')
		          		{  
		            		  
		            		  enabledStyleLevelAttributes();
		            		  //$('#styleSubmit').removeAttr('disabled');     	
		            		  
		            		  // Enable Product & Legacy Attributes
		            		  enableProductAndLegacyAttributes();
		            	 
					            
		          		}

					//End of Logic for Style
            	 
      
            	 //Logic for disabling the Product Name , Product Description Text  Area ,Style Color  Approve button ,omni channel ,car brand
            	 //when the Style Color Pet Status is completed or closed
            	    var styleColorInitiatedCount = false;       
	      			for(i=1; i<=styleColorRowCount; i++){
      				   var styleColorContentPetStatus= $(".petColorRowClass"+(i)).text();
      				   var approveButtonId = "styleColorApproveButtonId"+i;
	      	    	   if(styleColorContentPetStatus=='Completed' || styleColorContentPetStatus=='Closed' || styleColorContentPetStatus=='Deactivated')
		           		{ 
		            		  document.getElementById(approveButtonId).disabled = true;
		           		}
	            	   else if(styleColorContentPetStatus=='Initiated'|| styleColorContentPetStatus=='Ready_For_Review' )
	            		   {    
		            		  $('#vendorColorId').attr("disabled", "disabled"); //	vendorColor would always be non editable -defect 358
		            		//  $('#saveButtonId').removeAttr('disabled'); 		            		  
		            		  document.getElementById(approveButtonId).disabled = false;            		   
	            		   }
	      	    	   if(styleColorContentPetStatus=='Initiated')
	      	    		   {
	      	    		 		styleColorInitiatedCount = true;
	      	    		   }
	      			}
	      			
	      			if(styleColorInitiatedCount==true)
	      				{

	      					$('#publisStatusCodePublishToWeb').prop('checked', false);
	      				}
            	  //End of logic for disabling for role name dca       	      
        		  } // End of DCA	    	  
            	  
            	  //End of logic for disabling
            	  
            	       
            	  //Logic for disabling the Omni channel color fields and Car brand field ,style color fields  for the user role read only            	  
            	  if(pepUserRoleName == "readonly")
        		  {            		              		  
            		  disableStyleLevelAttributes();
              		 
            		  disabledProductAndLegacyAttributes();
            		  
                      disableSkuLevelAttributes();
        		  }// End of readonly
            	  
            	  
               
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
            	  //VP21
				  if(pepUserRoleName != "vendor") {
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
				  }
            	  //VP21
            	              	  
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
				   //VP21
				   if(pepUserRoleName != "vendor") {
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
				   }
            	  //VP21
            	  
			    if(pepUserRoleName == "dca" && ((stylePetContentStatus=='Initiated' || stylePetContentStatus=='Ready_For_Review'))){
            		  disableProductDropDownAttributes();
            		  disableProductTextAttributes();
            		  disableProductRadioAttributes();
            	  } else if (pepUserRoleName == "vendor" && stylePetContentStatus=='Initiated'){
            		  disableProductDropDownAttributes();
            		  disableProductTextAttributes();
            		  disableProductRadioAttributes();
            	  }
                   			

                $('.tree').treegrid();
                $('.tree2').treegrid({
                    expanderExpandedClass: 'icon-minus-sign',
                    expanderCollapsedClass: 'icon-plus-sign',
                    initialState:'collapsed' /*scroll optimization */
                });
            });
            
            
            function enabledStyleLevelAttributes(){
            	  $('#txt_outfitName').removeAttr('disabled'); 
        		  $('#vendorStyleId').removeAttr('disabled');  
        		  $('#styleSubmit').removeAttr('disabled'); 	            		  
        		  $('#omnichannelbrand').removeAttr('disabled');  
        		  $('#carbrand').removeAttr('disabled'); 
        		  $('#iphCategoryDropDownId').removeAttr('disabled'); 
        		  $('#saveButtonId').removeAttr('disabled');   
        		  $('#channelExclId').removeAttr('disabled');  
        		   $("#publisStatusCodePublishToWeb").removeAttr("disabled"); 
        		  $('#belkExclId').removeAttr('disabled');  
        		  $('#globalGWPId').removeAttr('disabled');  
        		  $('#globalPWPId').removeAttr('disabled');  
        		  $('#globalPYGId').removeAttr('disabled');  
        		  $('#bopislId').removeAttr('disabled');
        		  /* RMS -UDA enhancement */
        		  $('#vendorCollId').removeAttr('disabled');
            }
            
            function disableStyleLevelAttributes(){
	              $('#txt_outfitName').attr("disabled", "disabled"); 
	       		  $('#vendorStyleId').attr("disabled", "disabled");  
	       		  $('#styleSubmit').attr("disabled", "disabled"); 	            		  
	       		  $('#omnichannelbrand').attr("disabled", "disabled");  
	       		  $('#carbrand').attr("disabled", "disabled"); 
	       		  $('#iphCategoryDropDownId').attr("disabled", "disabled"); 
	       		  $('#saveButtonId').attr("disabled", "disabled");
	       		  $('#channelExclId').attr("disabled", "disabled");
	       		  $("#publisStatusCodePublishToWeb").attr("disabled", "disabled");
	       		  $('#belkExclId').attr("disabled", "disabled");
	       		  $('#globalGWPId').attr("disabled", "disabled");
	       		  $('#globalPWPId').attr("disabled", "disabled");
	       		  $('#globalPYGId').attr("disabled", "disabled");
	       		  $('#bopislId').attr("disabled", "disabled");
			  /*RMS - UDA enhancement */
	       		  $('#vendorCollId').attr("disabled", "disabled");
	       		  $("#btnCopyORIN").attr("disabled", "disabled"); //disabling Copy ORIN button for a read only user
            }
            
            // disableProductAndLegacy Attributes
            function disabledProductAndLegacyAttributes(){
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
				//VP21
      		  //logic for disabling the blue nartini attribute multi  select drop down
			  if(pepUserRoleName != "vendor") {
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
			  }
      		  //VP21
          	  if(document.getElementById("paRadioButtonCounter")){ 
          		  var  radioButtonCount =document.getElementById("paRadioButtonCounter").value;		
          	   
   				  for(var j=1; j<=radioButtonCount; j++){
              	  	 for(var i=1; i<3; i++){ 
              		 var temp = j+""+i;
	    	            	  document.getElementById("paRadio_Id"+temp).disabled = true;
              		 }  
   				  }
          	  }
          	  //VP21
			  if(pepUserRoleName != "vendor") {
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
			  //VP21
            }
            
            // disableProductAndLegacy drop down Attributes
            function disableProductDropDownAttributes(){
            	 //logic for disabling the product attribute multi  select drop down
      		  if(document.getElementById("paDropDownCounter")){ 
      		  var  dropDownCounting=document.getElementById("paDropDownCounter").value;
      		  for(q=1; q<=dropDownCounting; q++){
      				
      				 var paDropDownId = "dropDownsId_id"+q;
      				 var isEditable = document.getElementById("isPIMEditable_id"+q).value;
      				 
      				 if(isEditable == "No"){
      					document.getElementById(paDropDownId).disabled = true;
      				 }else{
      					document.getElementById(paDropDownId).disabled = false;
      				 }
      				 
      			}
      		  }
      		  
       		  //logic for disabling the blue martini attribute multi  select drop down
      		  if(document.getElementById("bmDropDownCounter")){
      		   var  bmDropDownCounting=document.getElementById("bmDropDownCounter").value;
      		  for(z=1; z<=bmDropDownCounting; z++){
      				 var bmDropDownId = "bmDropDownsId_id"+z;
      				 var isBMEditable = document.getElementById("isBMEditable_id"+z).value;
      				 if(isBMEditable == "No"){
      				    document.getElementById(bmDropDownId).disabled = true;
      					
      				 }else{
      					document.getElementById(bmDropDownId).disabled = false; 
      				 }
      			}
      		  }

            }
            
            // disableProductAndLegacy text Attributes
            function disableProductTextAttributes(){
            	 //logic for disabling the text attributes
      		  if(document.getElementById("paTextAttributeCount")){ 
	      		  var  textCounter=document.getElementById("paTextAttributeCount").value;
	      		  for(q=1; q<=textCounter; q++){
	      				
	      				 var paTextId = "paText_Id"+q;
	      				 var isEditable = document.getElementById("isPIMTextEditable_id"+q).value;
	      				 
	      				 if(isEditable == "No"){
	      					document.getElementById(paTextId).disabled = true;
	      				 }else{
	      					document.getElementById(paTextId).disabled = false;
	      				 }
	      				 
	      			}
      		  }
      		  
       		  //logic for disabling the blue martini text attributes
      		  if(document.getElementById("bmTextAttributeCount")){ 
        		  var  bmtextCounter=document.getElementById("bmTextAttributeCount").value;
        		  for(k=1; k<=bmtextCounter; k++){
        				
        				 var bmTextId = "blueMartiniText_Id"+k;
        				 var isBMEditable = document.getElementById("isBMTextEditable_id"+k).value;
        				 
        				 if(isBMEditable == "No"){
        					document.getElementById(bmTextId).disabled = true;
        				 }else{
        					document.getElementById(bmTextId).disabled = false;
        				 }
        				 
        			}
        		  }

            }
            
         // disableProductAndLegacy Radio Attributes
            function disableProductRadioAttributes(){
            	 //logic for disabling the radio attributes
      		  if(document.getElementById("paRadioButtonCounter")){ 
	      		  var  radioCounter=document.getElementById("paRadioButtonCounter").value;
	      		  for(q=1; q<=radioCounter; q++){	      				
      				 var isEditable = document.getElementById("isPIMRadioEditable_id"+q).value;
      				 //alert("editable flag value of radio button "+isEditable);
      				 for(var i=1; i<3; i++){
      					var temp = q+""+i;
      					var paRadioId = "paRadio_Id"+temp;
	      				 if(isEditable == "No"){
	     					//alert("paRadioId is  "+paRadioId); 
	      					document.getElementById(paRadioId).disabled = true;
	      				 }else{
	      					document.getElementById(paRadioId).disabled = false;
	      				 }
      				 }	 
	      				 
      			}
      		  }
      		  
       		  //logic for disabling the blue martini radio button attributes
      		  if(document.getElementById("bmRadioButtonCounter")){ 
        		  var  bmradioCounter=document.getElementById("bmRadioButtonCounter").value;
        		  for(k=1; k<=bmradioCounter; k++){
        				 var isBMEditable = document.getElementById("isBMRadioEditable_id"+k).value;
        				 //alert("BM editable flag value of radio button "+isBMEditable);
        				 for(var l=1; l<3; l++){
           					var temp1 = k+""+l;
           					var bmRadioId = "bmRadio_Id"+temp1;
     	      				 if(isBMEditable == "No"){
     	     					//alert("bmRadioId is  "+bmRadioId); 
     	      					document.getElementById(bmRadioId).disabled = true;
     	      				 }else{
     	      					document.getElementById(bmRadioId).disabled = false;
     	      				 }
           				 }
        				 
        			}
        		  }

            }
            
            function disableSkuLevelAttributes() {
                $('select[name=omniChannelSizeDescription]').each(function(i,row) {
                    var selectDropDown = $(row);
                    if ($(selectDropDown).is(":disabled")) {
                        return; //do not disable if already disabled.
                    }

                    selectDropDown.attr("disabled","disabled");
                });
            }
            
            // Enable Product Attributes
            function enableProductAndLegacyAttributes(){
            	 //Logic for enabling the product attribute multi select drop down 
      		  if(document.getElementById("paDropDownCounter")){ 
      		  var  dropDownCounting=document.getElementById("paDropDownCounter").value;		            		
      			for(q=1; q<=dropDownCounting; q++){	           				
      				 var paDropDownId = "dropDownsId_id"+q;		 
      				 document.getElementById(paDropDownId).disabled = false;
      			}
      		  }
			//VP21
      		 //Logic for enabling the blue martini attribute multi  select drop down
			 if(pepUserRoleName != "vendor") {
      		 if(document.getElementById("bmDropDownCounter")){ 
          		 
      			 var  bmDropDownCounting=document.getElementById("bmDropDownCounter").value;
		            	  for(z=1; z<=bmDropDownCounting; z++){
		            				 var bmDropDownId = "bmDropDownsId_id"+z;
		            				 document.getElementById(bmDropDownId).disabled = false;
		            	}	
      			 }
			 }
		       //VP21     	  
       
            	//logic for disabling the product attribute  text field
           		  if(document.getElementById("paTextAttributeCount")){
         				var textFieldCount = document.getElementById("paTextAttributeCount").value;
         				for(var a=0; a<textFieldCount; a++){
         					document.getElementById("paText_Id"+(a+1)).disabled=false;		          					    
         				}
                      } 
            	  //VP21
            	  //logic for disabling the blue martini text field 
				  if(pepUserRoleName != "vendor") {
            	  if(document.getElementById("bmTextAttributeCount")){  
         			 var bmTextFieldCount = document.getElementById("bmTextAttributeCount").value;
         				for(var a=0; a<bmTextFieldCount; a++){
         					document.getElementById("blueMartiniText_Id"+(a+1)).disabled=false;
         			 
         				}
         			  }
				  }
            	  //VP21
            	  if(document.getElementById("paRadioButtonCounter")){ 
            		  var  radioButtonCount =document.getElementById("paRadioButtonCounter").value;		
            	   
     				  for(var j=1; j<=radioButtonCount; j++){
                	  	 for(var i=1; i<3; i++){ 
                		 var temp = j+""+i;
	    	            	  document.getElementById("paRadio_Id"+temp).disabled = false;
                		 }  
     				  }
            	  }
            	  //VP21
				  if(pepUserRoleName != "vendor") {
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
				  //VP21
            }
            
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
                    //VP28 - display selected attributes -start
                    if(list.multiple){
                  	  var id=list.id;
                  	  var valueDD=$("#"+id+"_value");
                  	  valueDD.empty(); //empty current options
                  	  $('#'+id+" option:selected").each(function(){
                  		  valueDD.append($('<option />',{
                  			 value:$(this).val(),
                  			 text:$(this).text()
                  		  }));
                  	  });
                    }
                    //VP28 - end
           	  	}
            
            //VP21
			 var  pepUserRoleName = "";
            	if(document.getElementById("roleNameId") != null){
            		    pepUserRoleName = document.getElementById("roleNameId").value;  
            	}
			if(pepUserRoleName != "vendor") {
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
			}
            //VP21
            
            
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
            
            function setFurAttributes(selectedRadioValue,attributeName){
            	//alert("Inside setFurAttributes function"+selectedRadioValue +" "+attributeName);
            	if(attributeName == "Faux_Fur"){
            		//alert("attribute is faux_fur");
            		var radioName = "paRadio"+selectedRadioValue;
            		var radioButtons = document.getElementsByName(radioName);
            		var radioValue;
            	    for (var i = 0; i < radioButtons.length; i++) {
            	        if (radioButtons[i].type === "radio" && radioButtons[i].checked) {
            	        	radioValue = radioButtons[i].value;
            	        }
            	    }            		
            		if(radioValue == 'N'){
            			populateDefaultValuesForFurAttributes();
            		}
            	}
            }
            
            function populateDefaultValuesForFurAttributes(){
            	if(document.getElementById("paDropDownCounter")){ 
            		  var  dropDownCounting=document.getElementById("paDropDownCounter").value;		            		
            			for(q=1; q<=dropDownCounting; q++){
            				 var paDropDownAttrName = document.getElementById("dropdownAttributeName"+q).value;
            				 //alert("paDropDownAttrName"+paDropDownAttrName);
            				 if(paDropDownAttrName == "Fur_animal_name" || paDropDownAttrName == "Fur_treatment"){
            					 var paDropDownId = "dropDownsId_id"+q;
                				 document.getElementById(paDropDownId).value = "0";
            				 }
            				
            			}
                }
            	
            	if(document.getElementById("paTextAttributeCount")){
     				var textFieldCount = document.getElementById("paTextAttributeCount").value;
     				for(var a=0; a<textFieldCount; a++){
     					var textAttrName = document.getElementById("paTextAttributeName_id"+(a+1)).value;
     					//alert("textAttrName"+textAttrName);
     					if(textAttrName == "Fur_RN_Number_or_Distributor" || textAttrName == "Fur_country_of_origin"){
     						document.getElementById("paText_Id"+(a+1)).value="N/A";
     					}
   							          					    
     				}
                }
            }
            
            //VP21
			if(pepUserRoleName != "vendor") {
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
			}
			//VP21
     </script>
	
     <script>
	 //VP21
		var  pepUserRoleName = "";
           if(document.getElementById("roleNameId") != null){
			pepUserRoleName = document.getElementById("roleNameId").value;  
        }
		//VP21
	     function calliph(iph,petId)
	     {

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
					jConfirm('Are you sure you want to close without saving ?', 'Confirm', function(result) {     
						if(!result){
						 return false;
						}else{
							goBackToWorkList(loggedInUser,releseLockedPetURL);
						}
					});
				} else {
					goBackToWorkList(loggedInUser,releseLockedPetURL);
				}
			} else if(pepUserRoleName == 'dca'){
				if(stylePetContentStatus=='Initiated' || stylePetContentStatus=='Ready_For_Review'){
					jConfirm('Are you sure you want to close without saving ?', 'Confirm', function(result) {     
						if(!result){
						 return false;
						}else{
							goBackToWorkList(loggedInUser,releseLockedPetURL);
						}
					});
				} else {
					goBackToWorkList(loggedInUser,releseLockedPetURL);
				}
			} else {
				goBackToWorkList(loggedInUser,releseLockedPetURL);
			}
		}
	 	
	 	function goBackToWorkList(loggedInUser,releseLockedPetURL){
	 		if(timeOutFlag == 'yes'){
				$("#timeOutId").show();
				timeOutConfirm = 'Y';		
				setTimeout(function(){
					logout_home(loggedInUser,releseLockedPetURL);
					},4000);
				
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
				  
			  }else if(userRole == 'vendor'){
				 if(styleColorStatus == 'Initiated'){
					 removeStyleColorDisable();
				  }
				 if(styleColorStatus == 'Completed' || styleColorStatus == 'Ready_For_Review' || styleColorStatus == 'Closed'){
					 disableStyleColoreFields();
				  }
			  } else if(userRole == 'readonly'){
				  disableStyleColoreFields();
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
		
        //remove error message if exists upon selecting valid option
        function handleOmniSizeDescSelect(selectedElement) {
            var errorMsgDiv = $(selectedElement).parent().find(".errorMsg");
            if (errorMsgDiv.text() != "") {
                errorMsgDiv.text("");
            }
        }

        // on click of Style-Color "Approve" row button, pass the style color orin number, pet status, & logged-in user to update pet style color by calling webservice.
        function approveStyleColor(approveUrl,styleOrinNumber,styleColorOrinNumber,childrenClassIdentifier,user,roleName,errorMsg,styleColorPetContentStatus,clickedButtonId,colorRowCount,styleColorPetImageStatus) {
            var success = true;
            if(roleName=="dca") {
               
               //run through each row
               $('table#StyleStyleColorSkuContentId tr.' + childrenClassIdentifier).each(function(i,row) {
                   var omniSizeValue = $(row).find("select").val();
                   
                   if (typeof omniSizeValue == "undefined" || !omniSizeValue) {
                       success = false;
                   }
                   else if (omniSizeValue == "Please Select") {
                       $(row).find("div.errorMsg").text(errorMsg)
                       success = false;
                   }
                   else {
                       $(row).find("div.errorMsg").text('')
                   }
               });
            }
            if (success) {
                $('span#'+styleColorOrinNumber+'_notify').text('');
                
                var removeParentFlag = true;
                $('table#StyleStyleColorSkuContentId span.notify').each(function(i,row) {
                    var notifyText = $(row).text();
                    
                    if (typeof notifyText != "undefined" && notifyText == "!") {
                        removeParentFlag = false;
                    }
                });
                
                if (removeParentFlag) {
                    $('span#'+styleOrinNumber+'_notify').text('');
                }
                
                getUpdateStyleColorContentPetStatusWebserviceResponse(approveUrl,styleColorOrinNumber,styleColorPetContentStatus,user,roleName,clickedButtonId,colorRowCount,styleColorPetImageStatus,styleOrinNumber,childrenClassIdentifier);
            }
            else {
                $('span#'+styleOrinNumber+'_notify').text('!');
                $('span#'+styleColorOrinNumber+'_notify').text('!');
                jAlert('Please assign OmniChannel Size Description for each sku of style color pet.', 'Alert');
            }
		}
	   
	 //on click of the Style Color  Data row submit button ,pass the style color orin number ,styleColor  pet status and logger in user to the update content pet style color  status by caling webservice
		function getUpdateStyleColorContentPetStatusWebserviceResponse(approveUrl,styleColorPetOrinNumber,styleColorPetContentStatus,user,roleName,clickedButtonId,colorRowCount,styleColorPetImageStatus,styleOrinNumber,childrenClassIdentifier){

                // PIMTWO-13: save logic added
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
                if(omniChannelColorDescriptionId == null || omniChannelColorDescriptionId.trim() == ''){
                    jAlert("Please enter Omnichannel Color Description", "Alert");
                    return false;
                }
                var  vendorColorId=   $("#vendorColorId").val();	


                var packColorEntry =  $("#packColorEntryId").val();
                var styleColorEntry=$("#styleColorEntryId").val();

                // PIMTWO-13: sku & omniSizeCode will be stored in an array
                var omniSizeCodeSkuList = {};
                //run through each row
                $('table#StyleStyleColorSkuContentId tr.' + childrenClassIdentifier).each(function(i,row) {
                    var selectDropDown = $(row).find("select");
                    if ($(selectDropDown).is(":disabled")) {
                        return; //do not add sku if there is only one option available.
                    }

                    var omniSizeValue = $(selectDropDown).val();

                    if (typeof omniSizeValue != "undefined" && omniSizeValue != null && omniSizeValue != "Please Select") {
                        var skuOrinNumber = $(row).find("#skuAnchorTag").text();
                        omniSizeCodeSkuList[skuOrinNumber] = omniSizeValue;
                    }
                });
				
				
				var status = true;
			  
			    var val = $('.contentStatusId').html(); 
				if(roleName=='dca' && val != null && (val == 'Initiated' || val == 'Ready_For_Review')){
					status = false;
					jAlert('Please approve style level before approving the style color pet.', 'Alert');
					//alert("Please approve style level before approving the style Color Pet.");
				} else if( (roleName=='vendor' || roleName=='readonly') && val != null && (val == 'Initiated')){  
					status = false;
					jAlert('Please submit style level before submitting the style color pet', 'Alert');
					//alert("Please submit style level before submitting the Style Color Pet.");
				} else if(roleName=='dca' && styleColorPetImageStatus != 'Completed' && $('#publisStatusCodePublishToWeb').is(':checked')){  
					status = false;
					jAlert('Please complete Image before approving the style color pet', 'Alert');
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
                
                url: approveUrl,
                type: 'GET',
                dataType: "json",         
                cache:false,                      
                data: { 
                          selectedStyleColorOrinNumber:styleColorPetOrinNumber,
                          styleColorPetContentStatus:styleColorPetContentStatus,
                          loggedInUser:user,
                          petState:$('#publisStatusCodePublishToWeb:checked').val(),
                          stylePetOrinNumber:styleOrinNumber,
                          styleColorPetOrinNumber:selectedStyleColorOrinNumber,
                          omniColor:omniFamilyOptions,
                          secondaryColorStyleColorAttribute:secondaryColorOptions,
                          secondaryColorId2StyleColorAttribute:secondaryColorId2Options,
                          secondaryColorId3StyleColorAttribute:secondaryColorId3Options,
                          secondaryColorId4StyleColorAttribute:secondaryColorId4Options,
                          nrfColorCodeStyleColorAttribute:nrfColorCodeId,                          
                          omniChannelColorDescriptionStyleColorAttribute:omniChannelColorDescriptionId,                      
                          vendorColorIdStyleColorAttribute:vendorColorId,
                          omniSizeCodeSkuList:JSON.stringify(omniSizeCodeSkuList),
                          packColorReq:packColorEntry,
                          styleColorReq:styleColorEntry
                       },
                        
                    
                       success: function(data){
                           
                           var saveStatus = $(data).attr("SaveStyleColorAttributesStatus");
                           if (saveStatus == "Success") {
                               $("#ajaxResponseUpdateStylePetContentStatus").html("");
                               $("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Color Pet Status updated successfully !</font></b>" );      
                               $("#saveButtonId").attr("disabled", "disabled");
                               //logic to dynamically generate unique ids for the  Style Color Submit or Style Color Approve button and disable the action button
                               document.getElementById(clickedButtonId).disabled = true;
                               styleColorContentPetWebserviceResponseFlag="true";
                               $("#petColorRow_id"+colorRowCount).html('Completed');
                               disableStyleColoreFields();
                           }
                           else {
                               $("#ajaxResponseSaveContentPetAttribute").html("");
                               $("#ajaxResponseSaveContentPetAttribute").append("<b><font size='2'>Style-Color Level and SKU Level edits have NOT been saved successfully, please contact helpdesk.</font></b>"); 
                               $("#ajaxResponseSaveContentPetAttribute").focus();
                               window.setTimeout(function() { 
                                   $("#ajaxResponseSaveContentPetAttribute").html("");
                               }, 10000);
                               
                               $("#ajaxResponseUpdateStylePetContentStatus").html("");
                               $("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Color Pet Status could not be updated, please contact Belk helpdesk </font></b> <br>"); 
                               $("#ajaxResponseUpdateStylePetContentStatus").append("<br>");
                               $("#saveButtonId").removeAttr("disabled");
                               //logic to dynamically generate unique ids for the  Style Color Submit or Style Color Approve button and disable the action button
                               document.getElementById(clickedButtonId).disabled = false;
                               styleColorContentPetWebserviceResponseFlag="false";
                           }
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
                
                url: approveUrl,
                type: 'GET',
                dataType: "json",         
                cache:false,                      
                data: { 
                          selectedStyleColorOrinNumber:styleColorPetOrinNumber,
                          styleColorPetContentStatus:styleColorPetContentStatus,
                          loggedInUser:user,
                          stylePetOrinNumber:styleOrinNumber,
                          styleColorPetOrinNumber:selectedStyleColorOrinNumber,
                          omniColor:omniFamilyOptions,
                          secondaryColorStyleColorAttribute:secondaryColorOptions,
                          secondaryColorId2StyleColorAttribute:secondaryColorId2Options,
                          secondaryColorId3StyleColorAttribute:secondaryColorId3Options,
                          secondaryColorId4StyleColorAttribute:secondaryColorId4Options,
                          nrfColorCodeStyleColorAttribute:nrfColorCodeId,                          
                          omniChannelColorDescriptionStyleColorAttribute:omniChannelColorDescriptionId,                      
                          vendorColorIdStyleColorAttribute:vendorColorId,
                          omniSizeCodeSkuList:JSON.stringify(omniSizeCodeSkuList),
                          packColorReq:packColorEntry,
                          styleColorReq:styleColorEntry
                       },
                        
                    
                       success: function(data){
                           
                           var saveStatus = $(data).attr("SaveStyleColorAttributesStatus");
                           if (saveStatus == "Success") {
                               $("#ajaxResponseUpdateStylePetContentStatus").html("");
                               $("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Color Pet Status updated successfully !</font></b>" );      
                               $("#saveButtonId").attr("disabled", "disabled");
                               //logic to dynamically generate unique ids for the  Style Color Submit or Style Color Approve button and disable the action button
                               document.getElementById(clickedButtonId).disabled = true;
                               styleColorContentPetWebserviceResponseFlag="true";
                               $("#petColorRow_id"+colorRowCount).html('Ready_For_Review');
                               disableStyleColoreFields();
                           }
                           else {
                               $("#ajaxResponseSaveContentPetAttribute").html("");
                               $("#ajaxResponseSaveContentPetAttribute").append("<b><font size='2'>Style-Color Level and SKU Level edits have NOT been saved successfully, please contact helpdesk.</font></b>"); 
                               $("#ajaxResponseSaveContentPetAttribute").focus();
                               window.setTimeout(function() { 
                                   $("#ajaxResponseSaveContentPetAttribute").html("");
                               }, 10000);
                               
                               $("#ajaxResponseUpdateStylePetContentStatus").html("");
                               $("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Color Pet Status could not be updated, please contact Belk helpdesk </font></b> <br>"); 
                               $("#ajaxResponseUpdateStylePetContentStatus").append("<br>");
                               $("#saveButtonId").removeAttr("disabled");
                               //logic to dynamically generate unique ids for the  Style Color Submit or Style Color Approve button and disable the action button
                               document.getElementById(clickedButtonId).disabled = false;
                               styleColorContentPetWebserviceResponseFlag="false";
                           }
                           
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
	 
		function saveStyleColor(url, stylePetOrinNumber, childrenClassIdentifier){
		    
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
			if(omniChannelColorDescriptionId == null || omniChannelColorDescriptionId.trim() == ''){
				 jAlert("Please enter Omnichannel Color Description", "Alert");
				 return false;
			 }
			var  vendorColorId=   $("#vendorColorId").val();	
			
 
		      var packColorEntry =  $("#packColorEntryId").val();
		      var styleColorEntry=$("#styleColorEntryId").val();
		      
		      // PIMTWO-13: sku & omniSizeCode will be stored in an array
		      var omniSizeCodeSkuList = {};
		      //run through each row
              $('table#StyleStyleColorSkuContentId tr.' + childrenClassIdentifier).each(function(i,row) {
                  var selectDropDown = $(row).find("select");
                  if ($(selectDropDown).is(":disabled")) {
                      return; //do not add sku if there is only one option available.
                  }
                  
                  var omniSizeValue = $(selectDropDown).val();
                  
                  if (typeof omniSizeValue != "undefined" && omniSizeValue != null && omniSizeValue != "Please Select") {
                      var skuOrinNumber = $(row).find("#skuAnchorTag").text();
                      omniSizeCodeSkuList[skuOrinNumber] = omniSizeValue;
                  }
              });
		      
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
                          omniSizeCodeSkuList:JSON.stringify(omniSizeCodeSkuList),
                          packColorReq:packColorEntry,
                          styleColorReq:styleColorEntry
                       },
                	   success: function(data){		
						var json = $.parseJSON(data);  
					
						$(json).each(function(i,val){
				
						var status = val.UpdateStatus;
						  if(val.UpdateStatus == 'Success'){
								 $("#ajaxResponseSaveContentPetAttribute").html("");
    	                              $("#ajaxResponseSaveContentPetAttribute").append("<b><font size='2'>Style-Color and SKU Level edits have been saved successfully!</font></b>"); 
    	                              $("#ajaxResponseSaveContentPetAttribute").focus();
    	                              window.setTimeout(function() { 
    	                                  $("#ajaxResponseSaveContentPetAttribute").html("");
    	                              }, 10000);
    	  						 }else{
    	  							$("#ajaxResponseSaveContentPetAttribute").html("");
    		   	                	$("#ajaxResponseSaveContentPetAttribute").append("<b><font size='2'>Style-Color Level and SKU Level edits have NOT been saved successfully, please contact helpdesk.</font></b>"); 
    		   	                    $("#ajaxResponseSaveContentPetAttribute").focus();
                                    window.setTimeout(function() { 
                                        $("#ajaxResponseSaveContentPetAttribute").html("");
                              	    }, 10000);
    	  						 }
						});	
                	   }
					 });
			return true;
		}
	 
		 //on click of the Save button ,pass content pet attributes and make a ajax call to the  webservice
		function saveContentPetAttributesWebserviceResponse(url,stylePetOrinNumber,user,dropdownCount,bmDropDownCount, pimRadioButtonCount, bmRadionButtonCount, approveUrl, roleName, from, styleOrColor){	
				var  pepUserRoleName = "";
            	if(document.getElementById("roleNameId") != null){
            		    pepUserRoleName = document.getElementById("roleNameId").value;  
            	}
				//VP21
			
			 // Validate Form data
			 if(from == 'Approve'){
				if(!validateFormData(dropdownCount,bmDropDownCount, pimRadioButtonCount, bmRadionButtonCount, styleOrColor)){
					return;
				}
			 } else if(from == 'Save'){
				//RMS  - UDA enhancement form validation
					if($('#vendorCollId').val().trim()!= null && ! $('#vendorCollId').val().trim()==""){
		 			if(!/^[a-zA-Z0-9- ]*$/.test($('#vendorCollId').val().trim())){
						jAlert('Vendor Collection should not contain special characters', 'Alert');
		 				return;
		 			}
					}
				 var values = document.getElementById("iphCategoryDropDownId");
				 if(values != null && (values.value == 'select' || values.value == null) ){
					//alert("IPH selection is mandatory.");
					jAlert('IPH selection is mandatory', 'Alert');
					return false;
				 }  
			 }
			
			var  productName= document.getElementById("txt_outfitName").value;
			var  productDescription= document.getElementById("vendorStyleId").value;
			var onminvalue =  $("#selectedOmniBrand").val();
			var carsvalue =  $("#selectedCarsBrand").val();
			var channelExcVal =  $("#selectedChannelExclusive").val();
			var belkExcVal =  $("#selectedBelkExclusive").val();
			
			var bopisValue  =  $("#selectedBopis").val();
			// RMS - UDA enhancement
			var vendor_collection =$("#vendorCollId").val().trim();
			 // if(compackValue != 'Complex Pack'){
		     var selectedGWP =  $("#selectedGWP").val();
		     var selectedPWP =  $("#selectedPWP").val();
		     var selectedPYG =  $("#selectedPYG").val();
			 //  }
		     // End Added by Sriharsha
		     
		     
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
			//VP21
			if(pepUserRoleName != "vendor") {
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
			}
			//VP21
			
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
		  //VP21
		  if(pepUserRoleName != "vendor") {
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
		  }
			//VP21
		 //VP21
		  //Get the Blue Martini Attribute Text Field Values
		  if(pepUserRoleName != "vendor") {
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
		  }
           //VP21
		  var bmfinalTextValueString = "";
		  //VP21
		  if(pepUserRoleName != "vendor") {
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
		  }
		  //VP21
		     
		     //Added to capture the entry type as complex pack ,pack color,style ,style color
		     
		      var complexPackEntry =  $("#complexPackEntryId").val();
		      var packColorEntry =  $("#packColorEntryId").val();
		      var styleEntry=$("#styleEntryId").val();		
		      var styleColorEntry=$("#styleColorEntryId").val();
		     
		      // End Added  of the logic capture the entry type as complex pack ,pack color,style ,style color
		     
		     var selectedCategory = document.getElementById("iphCategoryDropDownId");
		 
		     if(from == 'Approve'){
				  	$('#overlay_pageLoadingapprove').show();
				  }else{
					$('#overlay_pageLoadingsave').show();
			  }
		     
		  //Make the ajax call
			 $.ajax({
                url: url ,
                type: 'POST',
                datatype:'json',
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
                          
			  vendor_collection: vendor_collection /*RMS - UDA enhancement */
                       },
                    	   success: function(data){		
                    	  						var json = $.parseJSON(data);  
                    	  						 
                    	  						$(json).each(function(i,val){
                    	  						 
                    	  						var status = val.UpdateStatus;
                    	  						$("#overlay_pageLoadingsave").hide();
                    	  						 if(val.UpdateStatus == 'Success'){
                    	  							  $("#ajaxResponseSaveContentPetAttribute").html("");
                    	                              $("#ajaxResponseSaveContentPetAttribute").append("<b><font size='3'>Pet Content saved successfully!</font></b>"); 
                    	                              $("#ajaxResponseSaveContentPetAttribute").focus();
                    	                              
                    	                              if(from == 'Approve'){
                    	                              if(roleName=='dca')
                    	                  			{
                    	                  				stylePetContentStatus='02' // Set Style Content Pet status to Completed when  dca approves the Pet
                    	                  				 
                    	                  				 var ajaxCall=  $.ajax({
                    	                                   
                    	                                   url: approveUrl,
                    	                                   type: 'GET',
                    	                                   dataType: "json",         
                    	                                   cache:false,                      
                    	                                   data: { 
                    	                                             selectedOrinNumber:stylePetOrinNumber,
                    	                                             styleContentStatus:stylePetContentStatus,
                    	                                             loggedInUser:user,
                    	                                             petState:$('#publisStatusCodePublishToWeb:checked').val()
                    	                                                  },
                    	                                           
                    	                                       
                    	                                          success: function(data){
                    	                                                    $("#ajaxResponseUpdateStylePetContentStatus").html("");
                    	                                                    $("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Pet Status updated successfully !</font></b>" );      
                    	                                                    $("#saveButtonId").attr("disabled", "disabled");
                    	                                                    $("#styleSubmit").attr("disabled", "disabled");
                    	                                                    $("#publisStatusCodePublishToWeb").attr("disabled", "disabled");
                    	                                                    $('.contentStatusId').html('Completed');		
                    	                                                    $("#ajaxResponseUpdateStylePetContentStatus").focus();
                    	                                                    styleContentPetWebserviceResponseFlag="true";
                    	                                                   
                    	                                                    
                    	                                          },
                    	                                          
                    	                                          error: function(XMLHttpRequest, textStatus, errorThrown){
                    	                      	                	
                    	                      	                	$("#ajaxResponseUpdateStylePetContentStatus").html("");
                    	                      	                	$("#ajaxResponseUpdateStylePetContentStatus").append("<b><font size='2'>Style Pet Status could not be updated, please contact Belk helpdesk.</font></b> <br>"); 
                    	                      	                    $("#ajaxResponseUpdateStylePetContentStatus").append("<br>");
                    	                      	                    $("#saveButtonId").removeAttr("disabled");
                    	                      	                    $("#styleSubmit").removeAttr("disabled"); 
                    	                      	                    $("#publisStatusCodePublishToWeb").removeAttr("disabled", "disabled");   
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
                    	                              $("#overlay_pageLoadingapprove").hide();
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
                    	  						 }
                    	  					});			 
                    	  				} 
					 });
	 
            
			//saveContentFormDetailsForSaveAndApprove(url,stylePetOrinNumber,user,dropdownCount,bmDropDownCount, pimRadioButtonCount, bmRadionButtonCount)
		}
		 
		 // Saving details
		 function validateFormData(dropdownCount,bmDropDownCount, pimRadioButtonCount, bmRadionButtonCount, styleOrColor){
				var  pepUserRoleName = "";
            	if(document.getElementById("roleNameId") != null){
            		    pepUserRoleName = document.getElementById("roleNameId").value;  
            	}
				//VP21
		 		var isStyleColorOpen =$('[name=hdnStyleColorOpen]:last').val();
		 		var petStatusCode =$('[name=hdnPETStatusCode]:last').val();
		 		var petStatus = $('#publisStatusCodePublishToWeb:checked').val();
				//RMS  - UDA enhancement form validation
				if($('#vendorCollId').val().trim()!= null && ! $('#vendorCollId').val().trim()==""){
	 			if(!/^[a-zA-Z0-9- ]*$/.test($('#vendorCollId').val().trim())){
					jAlert('Vendor Collection should not contain special characters', 'Alert');
	 				
	 				return;
	 			}
				}
		 		if($('#publisStatusCodePublishToWeb').is(':checked')){		 				 			
		 			
		 			if(petStatusCode == 'Deactivated'){
		 				jAlert('Please ReInitiate the Style before Publishing to Web', 'Alert');
		 				
		 				return;
		 			}
		 			
		 		}
		 		
				var omniBrandCount = document.getElementById("omniBrandCount").value
				var carBrandCount = document.getElementById("carBrandCount").value
				//if(omniBrandCount > 0){
					if(omniBrandCount != 1){
						var onminvalue =  $("#selectedOmniBrand").val();
						 if(onminvalue == null || onminvalue == '' || onminvalue == '-1'){
							 jAlert('Please select value for Omni Channel Brand', 'Alert');
					    	// alert("Please select value for Omni Channel Brand");
					    	 return;
					     }
					}else if(omniBrandCount == 1){
						var firstValue = document.getElementById("omnichannelbrand").value;
						 if(firstValue == null || firstValue == '' || firstValue == '-1'){
							 jAlert('Please select value for Omni Channel Brand', 'Alert');
							// alert("Please select value for Omni Channel Brand");
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
							 jAlert('Please select value for Car Brand', 'Alert');
					    	 //alert("Please select value for Car Brand");
					    	 return;
					     }
					}else if(carBrandCount == 1){
						var firstValue = document.getElementById("carbrand").value;
						 if(firstValue == null || firstValue == '' || firstValue == '-1'){
							 jAlert('Please select value for Car Brand', 'Alert');
							// alert("Please select value for Car Brand");
					    	 return;
						 }else{
							 document.getElementById("selectedCarsBrand").value = firstValue;
						 }
					}

				//}
				
				var  productName= document.getElementById("txt_outfitName").value;
				if(productName == null || productName.trim() == ""){
					 jAlert('Please enter Product Name', 'Alert');
					// alert("Please enter Product Name");
					 return;
				}else if(productName != null && productName.trim().length > 300){
					 jAlert('Product Name should be less than 300 characters', 'Alert');
					// alert("Product Name should be less than 300 characters.");
					 return;
				}
				
				var  productDescription= document.getElementById("vendorStyleId").value;
				if(productDescription == null || productDescription.trim() == ""){
					 jAlert('Please enter Product Description', 'Alert');
					// alert("Please enter Product Description");
			    	 return;
				}else if (productDescription != null && productDescription.trim().length > 2000) {
					 jAlert('Product Description should be less than 2000 characters', 'Alert');
					// alert("Product Description should be less than 2000 characters");
					 return;
				}
				
				 var belkExcVal =  $("#selectedBelkExclusive").val();
			     if(belkExcVal == null || belkExcVal == '-1' || belkExcVal == ''){
			    	 jAlert('Please select value for Belk Exclusive', 'Alert');
			    	// alert("Please select value for Belk Exclusive");
			    	 return;
			     }

			       var values = document.getElementById("iphCategoryDropDownId");
					if(values != null && (values.value == 'select' || values.value == null) ){
						jAlert('IPH selection is mandatory', 'Alert');
						//alert("IPH selection is mandatory.");
						return;
				 }  
				
				//if(action=='APPROVE'){
						
					
			   //Logic for passing selected multi select product attribute drop downs ,its value and  the xpath
					var finalString = "";
					for(i=0; i<dropdownCount; i++){
						var attNameAndPath = document.getElementById("dropdownAttributeNameXpath_id"+(i+1)).value;
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
								var attName = attNameAndPath.split("#");
								jAlert('Please select mandatory product attribute : <b> '+attName[0] + '</b>', 'Alert');
								//jAlert('Please select mandatory product attributes', 'Alert');
								//alert(" Please select mandatory product attributes");
								return;
							}
						} 
						
					}
					
					  //Get the ration buttons
			   		  var finalPIMRadioString = "";
			   			for(i=0; i<(pimRadioButtonCount); i++){
			   			   var attributePath = document.getElementById("paRadioAttributeXpath_id"+(i+1)).value;
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
			   						var attName = attributePath.split("#");
			   						jAlert('Please make a selection to mandatory product radio attribute :'+attName[0], 'Alert');
			   						//jAlert('Please make a selection to mandatory product radio attributes', 'Alert');
			   					//	alert(" Please make a selection to mandatory product radio attributes");
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
						    		 var attName = textFieldAttNameAndPath.split("#");
						    		 jAlert('Please enter mandatory product text field attribute :'+attName[0], 'Alert');
						    		//jAlert('Please enter mandatory product text field attributes', 'Alert');
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
					
		   			//VP21
			        // For Blue Martini values
		            var bmFinalString = "";
					if(pepUserRoleName != "vendor") {
		             for(i=0; i<bmDropDownCount; i++){ 
		            	 var attNameAndPath = document.getElementById("blueMartiniDropDownAttributeNameXpath_id"+(i+1)).value;
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
		                        	 var attName = attNameAndPath.split("#");
		                        	 jAlert('Please select mandatory legacy attribute : <b> '+attName[0] + '</b>', 'Alert');
		                        	 //jAlert('Please select mandatory legacy attributes', 'Alert');
		      						// alert(" Please select mandatory legacy attributes");
		      						return;
		                        	}
		      				 }  
		                      
		             }
					}
		            //VP21
					//VP21
	             //Get the bm ration buttons
		   		  var finalBMRadioString = "";
				  if(pepUserRoleName != "vendor") {
		   			for(i=0; i<(bmRadionButtonCount); i++){
		   				var attributePath = document.getElementById("bmRadioAttributeXpath_id"+(i+1)).value;
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
		   						var attName = attributePath.split("#");
		   					    jAlert('Please make a selection to mandatory legacy radio attribute : '+attName[0], 'Alert');
		   					    //jAlert('Please make a selection to mandatory legacy radio attributes', 'Alert');
		   						//alert(" Please make a selection to mandatory legacy radio attributes");
		   						return;
		   					}
		   				}  
		   				
		   			}
				  }
					//VP21
			   		//VP21
				if(pepUserRoleName != "vendor") {					
		   		 if(document.getElementById("bmTextAttributeCount")){
					 var bmTextFieldCount = document.getElementById("bmTextAttributeCount").value;
						for(var a=0; a<bmTextFieldCount; a++){
						    var textFieldAttNameAndPath = document.getElementById("blueMartiniTextAttributeNameXpath_id"+(a+1)).value;
						    var mandatory = $("#isBMTextMandatory_id"+(a+1)).val();
						    var userEnteredValue = document.getElementById("blueMartiniText_Id"+(a+1)).value;
						    if(userEnteredValue == null || userEnteredValue.trim() == '' ){
						    	if(mandatory == 'Yes'){ 
						    		var attName = textFieldAttNameAndPath.split("#");
						    		jAlert('Please enter mandatory legacy text field attribute :'+attName[0], 'Alert');
						    		//jAlert('Please enter mandatory legacy text field attributes', 'Alert');
						      		//alert(" Please enter mandatory legacy text field attributes");
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
				}
		          //VP21   
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
		timeOutvar = setTimeout(redirectSessionTimedOut, 3600000);
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

function goToHomeScreen(loggedInUser,releseLockedPetURL){
    inputChanged  = false ;
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

function logout_home(loggedInUser,releseLockedPetURL){
	inputChanged  = false ;
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


</script>

<portlet:defineObjects />

<fmt:setBundle basename="contentDisplay" var="display"/>  
	<portlet:actionURL var="getChangedIPHCategoryData"> 
				<portlet:param name="action" value="getChangedIPHCategoryData"/>
		</portlet:actionURL>
		
		<portlet:actionURL var="copyOrinContent"> 
				<portlet:param name="action" value="copyRegularOrinContent"/>
		</portlet:actionURL>	
		
		<portlet:resourceURL var="updateContentPetStyleDataStatus" id="updateContentPetStyleDataStatus"></portlet:resourceURL>
		<portlet:resourceURL var="updateContentPetStyleColorDataStatus" id="updateContentPetStyleColorDataStatus"></portlet:resourceURL>
		<portlet:resourceURL var="saveContentPetAttributes" id="saveContentPetAttributes"></portlet:resourceURL>	
		<portlet:resourceURL var="saveContentColorAttributes" id="saveContentColorAttributes"></portlet:resourceURL>	
		<body onload="timeOutPage();">
	
		<div id="content">
				<div id="main">

					<form id="copyORINForm" name="copyORINForm" action="${copyOrinContent}" method="post">
						<input type="hidden" name="destinationOrinType" id="hdnType" value="${contentDisplayForm.styleInformationVO.entryType}"/>
						<input type="hidden" name="destinationOrin" id="hdnId" value="${contentDisplayForm.styleInformationVO.orin}"/>
						<input type="hidden" name="destinationOrinPETStatus" id="hdnToOrinPETStatus" value="${contentDisplayForm.styleAndItsChildDisplay.styleList[0].petState}"/>
						<input type="hidden" name="sourceOrin" id="copyORINstyleId" value="" required />
						
					</form>
					<form:form commandName="contentDisplayForm" method="post" action="${getChangedIPHCategoryData}" name="contentDisplayForm" id="contentDisplayForm">
				
				 
							 <div id="overlay_pageLoadingsave" style="display:none;position:fixed;top:50%; left:50%;">
										<img src="<%=response.encodeURL(request.getContextPath()+"/img/loading.gif")%>" height="100px;" />
							</div> 
							 <div id="overlay_pageLoadingapprove" style="display:none;position:absolute;top:1000px; left:350px;">
										<img src="<%=response.encodeURL(request.getContextPath()+"/img/loading.gif")%>" height="100px;" />
							</div> 
	`						
							<input type="hidden" name="hdnStyleColorOpen" id="hdnStyleColorOpen" value="false"/>
							<input type="hidden" name="hdnPETStatusCode" id="hdnPETStatusCode" value=""/>
									
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
							<div class="freeze-cntr">
                                <div style="display: inline; padding: 0 10px; float: left;" >
                                    <input type="button" style="padding: 5px 15px;font-weight: bold" name="home"
                                    value="Home"
                                    onclick=goToHomeScreen('<c:out value="${contentDisplayForm.userName}"/>','${releseLockedPet}');  />
                                </div>
                                <div style="float: right;" class="freeze-cntr-right">
                                    <div class="orin-popup-container">
                                        <input type="button" class="btn chevron-down" id="btnCopyORIN" value="Copy ORIN"
                                        style="width: 150px; padding: 6px;"/>
                                        <div class="clearfix"></div>
                                            <div class="orin-popup" id="orin-popup" style="display:none">
                                                <div class="popup-header x-panel-header x-unselectable">Copy ORIN</div>
                                                <div class="popup-body">
                                                    <div class="form-conatiner">
                                                        <div class="input-area">
                                                            <label for="styleId">ORIN#/Vendor Style#</label>
                                                            <input type="text" name="fromOrin" id="copyORINstyleIdTmp" value="" required maxlength="15" />
                                                        </div>
                                                        <div class="submit-area">
                                                            <input type="button" id="doCopyOrin" value="Submit" class="action-button" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    <c:if test="${contentDisplayForm.roleName == 'readonly'}">
                                             <input type="button" name="Save" value="<fmt:message key="content.label.saveButton" bundle="${display}"/>"
                                                   class="saveContentButton"  onclick="javascript:saveContent();" disabled="true" style="margin-left:0"/>
                                             <input type="button" name="Close" value="<fmt:message key="content.label.closeButton" bundle="${display}"/>"
                                                  class="closeContentButton"  onclick="javascript:goToWorkListDisplayScreen('<c:out value="${contentDisplayForm.userName}"/>','${releseLockedPet}');"/>
                                    </c:if>
                                    <c:if test="${contentDisplayForm.roleName == 'dca' || contentDisplayForm.roleName == 'vendor' }">
                                                <input type="button" name="Save" id="saveButtonId"  value="<fmt:message key="content.label.saveButton" bundle="${display}"/>"
                                                    class="saveContentButton"  style="margin-left:0" onclick="javascript:saveContentPetAttributesWebserviceResponse('${saveContentPetAttributes}','<c:out value="${contentDisplayForm.styleInformationVO.orin}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>', '<c:out value="${contentDisplayForm.productAttributesDisplay.dropDownList.size()}"/>','<c:out value="${contentDisplayForm.legacyAttributesDisplay.dropDownList.size()}"/>', '<c:out value="${contentDisplayForm.productAttributesDisplay.radiobuttonList.size()}"/>', '<c:out value="${contentDisplayForm.legacyAttributesDisplay.radiobuttonList.size()}"/>', '', '', 'Save','')"/>
                                                <input type="button" name="Close" value="<fmt:message key="content.label.closeButton" bundle="${display}"/>"
                                                    class="closeContentButton"  onclick="javascript:goToWorkListDisplayScreen('<c:out value="${contentDisplayForm.userName}"/>','${releseLockedPet}');"/>
                                     </c:if>
                                     <c:if test="${contentDisplayForm.roleName != 'vendor'}">
                                        <div style="margin:10px 15px 0 0;float:left; color: #FFF;">
                                            <label style="margin-right:15px"><input type="checkbox" id="publisStatusCodePublishToWeb" name="publisStatusCodePublishToWeb" style="margin:0;" value="09"
                                            <c:if test="${contentDisplayForm.styleInformationVO.overallStatusCode == '09' || contentDisplayForm.globalAttributesDisplay.petStatus == '09'}"> checked=checked </c:if>
                                            <c:if test="${contentDisplayForm.roleName == 'readonly'}"> disabled="disabled" </c:if> /> <b>Publish to Web (Skip CMP Task)</b></label>
                                        </div>
                                     </c:if>
                                </div>
                                <div id="ajaxResponseSaveContentPetAttribute"></div>
                            </div>
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
										<fmt:message key="content.label.styleInfo" bundle="${display}"/>										
								</div>
								
								<input type="hidden" name="lockedPet" id="lockedPet" value="<c:out value="${contentDisplayForm.styleInformationVO.orin}" />" />
                                <input type="hidden" name="loggedInUser" id="loggedInUser" value="<c:out value="${contentDisplayForm.userName}" />" />
								
									
								<div class="x-panel-body">	
								        
									<!-- to show message from save content web service  -->
									
									
								    <div id="formIphMappingErrorMessage">
								     <c:if test="${not empty  contentDisplayForm.iphMappingMessage}">	
										     <table><tr><td><b><font size='2'><c:out value="${contentDisplayForm.iphMappingMessage}"/></font></b></td></tr></table>
									 </c:if>
									 <table><tr><td><b><font size='2' color="red"><c:out value="${contentCopyStatusMessage}" /></font></b></td></tr></table>
								   </div>
								   	<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">								   	    
										<li class="txt_attr_name" style="width: 30%;">
										   
										</li>				
									</ul> 
									  
									 <ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="labelOrinGrouping" bundle="${display}"/>&nbsp;<c:out value="${contentDisplayForm.styleInformationVO.orin}"/>&nbsp;</li>				
										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="labelDepartment" bundle="${display}"/>&nbsp;<c:out value="${contentDisplayForm.styleInformationVO.departmentId}"/>&nbsp;</li>
										<li class="txt_attr_name" style="width: 20%;"><fmt:message key="labelDeptDescription" bundle="${display}"/>&nbsp;<c:out value="${contentDisplayForm.styleInformationVO.deptDescription}"/>&nbsp;</li>
									</ul>


									<ul class="pep_info"
										style="font-size: 11px; padding: 0 0 10px !important;">
										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="labelOmnichannelVendor" bundle="${display}"/>&nbsp;<c:out value="${contentDisplayForm.styleInformationVO.omniChannelVendorIndicator}"/>&nbsp;</li>
										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="labelClass" bundle="${display}"/>&nbsp;<c:out value="${contentDisplayForm.styleInformationVO.classId}"/>&nbsp;</li>
										<li class="txt_attr_name" style="width: 20%;"><fmt:message key="labelClassDescription" bundle="${display}"/>&nbsp;<c:out value="${contentDisplayForm.styleInformationVO.classDescription}"/>&nbsp;</li>										
									</ul>
			
									<ul class="pep_info"
										style="font-size: 11px; padding: 0 0 10px !important;">
										<li class="txt_attr_name"  style="width: 30%;"><fmt:message key="labelVendorProvidedImage" bundle="${display}"/>&nbsp;<c:out value="${contentDisplayForm.styleInformationVO.vendorProvidedImageIndicator}"/>&nbsp;</li>
										<li class="ctxt_attr_name" style="width: 30%;"><fmt:message key="labelStyleNumber" bundle="${display}"/>&nbsp;<c:out value="${contentDisplayForm.styleInformationVO.styleId}"/>&nbsp;</li>
										<li class="ctxt_attr_name" style="width: 20%;"><fmt:message key="labelDescription" bundle="${display}"/>&nbsp;<c:out value="${contentDisplayForm.styleInformationVO.description}"/>&nbsp;</li>
									</ul>
									
									<ul class="pep_info"
										style="font-size: 11px; padding: 0 0 10px !important;">
										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="labelVendorProvidedSample" bundle="${display}"/>&nbsp;<c:out value="${contentDisplayForm.styleInformationVO.vendorSampleIndicator}"/>&nbsp;</li>
										<li class="txt_attr_name" style="width: 30%;"><fmt:message key="labelVendorNumber" bundle="${display}"/>&nbsp;<c:out value="${contentDisplayForm.styleInformationVO.vendorId}"/>&nbsp;</li>
										<li class="txt_attr_name" style="width: 20%;"><fmt:message key="labelVendorName" bundle="${display}"/>&nbsp;<c:out value="${contentDisplayForm.styleInformationVO.vendorName}"/>&nbsp;</li>	
									</ul>
									
									<!-- Added by Sriharsha -->
									<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
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
										 <li class="txt_attr_name" style="width: 30%;"><fmt:message key="labelCarBrand" bundle="${display}"/>
										 <input type="hidden" name="carBrandCount"  id="carBrandCount" value="${contentDisplayForm.lstCarBrandVO.size()}"  /> 
										 <select style="width:52%;height: 27px;"  id="carbrand" name="carbrand" onclick="javascript:getSelectedOCarsBrand(carbrand)">
													 <c:if test="${contentDisplayForm.lstCarBrandVO.size() == 1}">
													 <option id="-1" value="-1">Please Select</option>   
											        <c:forEach var="carbrandList" items="${contentDisplayForm.lstCarBrandVO}">												
														<option id="${carbrandList.carBrandCode}" value="${carbrandList.carBrandCode}" selected="selected"> ${carbrandList.carBrandDesc}</option>               
													</c:forEach>	
													</c:if>			
													 <c:if test="${contentDisplayForm.lstCarBrandVO.size() == 0 || contentDisplayForm.lstCarBrandVO.size() > 1}">
													 <option id="-1" value="-1">Please Select</option>   
											        <c:forEach var="carbrandList" items="${contentDisplayForm.lstCarBrandVO}">												
														<option id="${carbrandList.carBrandCode}" value="${carbrandList.carBrandCode}"> ${carbrandList.carBrandDesc}</option>               
													</c:forEach>	
													</c:if>												
										</select>
										</li>
										 <li class="txt_attr_name" style="width: 20%;"><fmt:message key="labelCompletionDate" bundle="${display}"/><c:out value="${contentDisplayForm.styleInformationVO.completionDateOfStyle}"/></li>
									</ul>
									<!-- End Added by Sriharsha -->
									<div style="float:right">
									 <portlet:actionURL var="formAction">
						                <portlet:param name="action" value="workListDisplay"/>
						                <portlet:param name="orinNumber" value="${orinNumber}"/>
						                <portlet:param name="pageNumber" value="${pageNumber}"/>
            						 </portlet:actionURL>
            						 <input type="hidden" name="workListDisplayUrl" value="${formAction}" />
            						 </div>
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
													<textarea id="vendorStyleId"	class="maxChars spellcheck textCount recommended" name="productDescription" cols="126" rows="7" onkeyup="textCounter();" onblur="textCounter();"  disabled="disabled"><c:out value="${contentDisplayForm.productDetailsVO.productDescription}"/></textarea>		
														
													<div class="chars_rem">
														<fmt:message key="caredit.page.product.remaining"/> <span class="chars_rem_val">40</span>
													</div>
													<div class="max_chars"
													style="margin-right: 150px; display: inline; white-space: nowrap;">
													<fmt:message key="content.page.product.max.char"  bundle="${display}"/><span class="max_chars_val">2000</span>&nbsp;&nbsp;
													
									
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
													<textarea id="vendorStyleId"	class="maxChars spellcheck textCount recommended" name="productDescription" cols="126" rows="7" onkeyup="textCounter();" onblur="textCounter();" ><c:out value="${contentDisplayForm.productDetailsVO.productDescription}"/></textarea>		
														
													<div class="chars_rem">
														<fmt:message key="caredit.page.product.remaining"/> <span class="chars_rem_val">40</span>
													</div>
													<div class="max_chars"
													style="margin-right: 150px; display: inline; white-space: nowrap;">
													<fmt:message key="content.page.product.max.char"  bundle="${display}"/><span class="max_chars_val">2000</span>&nbsp;&nbsp;
																										
									
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
													<textarea id="vendorStyleId"	class="maxChars spellcheck textCount recommended" name="productDescription" cols="126" rows="7" onkeyup="textCounter();" onblur="textCounter();" disabled="true"><c:out value="${contentDisplayForm.productDetailsVO.productDescription}"/></textarea>		
														
													<div class="chars_rem">
														<fmt:message key="caredit.page.product.remaining"/> <span class="chars_rem_val">40</span>
													</div>
													<div class="max_chars"
													style="margin-right: 150px; display: inline; white-space: nowrap;">
													<fmt:message key="content.page.product.max.char"  bundle="${display}"/><span class="max_chars_val">2000</span>&nbsp;&nbsp;
													
													
									
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
							
							<!--Global Attribute Section starts here  -->	
						
							 <div class="cars_panel x-hidden" id="globalAttributeSection">
								
										<div class="x-panel-header">
											<fmt:message key="sectionGlobalAttribute" bundle="${display}"/>
										</div>
										
										<div id="ajaxResponseStyleAttribute" class="x-panel-body" style="width:906px;">	
											<!-- <b>Style Orin Number :</b> <c:out value="${contentDisplayForm.globalAttributesDisplay.styleOrinNumber}" /><br></br>  -->
											<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
												<li class="txt_attr_name" style="width: 30%;"><b>Long Description : </b> <c:out value="${contentDisplayForm.globalAttributesDisplay.longDescription}" /></li>
												<li class="txt_attr_name" style="width: 25%;"><b>SDF :</b> <c:out value="${contentDisplayForm.globalAttributesDisplay.sdf}"/></li>
											</ul>
											
											<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
												<li class="txt_attr_name" style="width: 30%;"><b>Channel Exclusive : </b> 
												 <select style="width:50%;height: 27px;" id="channelExclId" name="channelExclId" onclick="javascript:getSelectedChannelExclId(channelExclId)"> 
													<option id="-1" value="-1">Please Select</option>
													<option id="Store" value="Store">Store</option>
													<option id="Ecomm" value="Ecomm">Ecomm</option>
													<option id="Store and Ecomm" value="Store and Ecomm">Store and Ecomm</option>
												</select>
												</li>
												
												</li>
												 <li class="txt_attr_name" style="width: 25%;"><b>BOPIS :</b>
												 <select style="width:50%;height: 27px;" id="bopislId" name="bopislId" onclick="javascript:getSelectedBopislId(bopislId)"> 
													<option id="-1" value="-1">Please Select</option>
													<option id="Yes" value="Yes">Yes</option>
													<option id="No" value="No">No</option>
												</select>
												</li>
												<li class="txt_attr_name" style="width: 25%;"><b>* Belk Exclusive :</b>
												 <select style="width:50%;height: 27px;" id="belkExclId" name="belkExclId" onclick="javascript:getSelectedbelkExclId(belkExclId)"> 
													<option id="-1" value="-1">Please Select</option>
													<option id="Yes" value="Yes">Yes</option>
													<option id="No" value="No">No</option>
												</select>
												</li>
											</ul>
												<ul class="pep_info" style="font-size: 11px; padding: 0 0 10px !important;">
													<li class="txt_attr_name" style="width: 30%;"><b>* GWP :</b>
														<select style="width:50%;height: 27px;"  id="globalGWPId" name="globalGWPId" onclick="javascript:getSelectedglobalGWPId(globalGWPId)"> 
															<option id="No" value="No">No</option>
															<option id="Yes" value="Yes">Yes</option>
															
														</select>
													</li>
													<li class="txt_attr_name" style="width: 25%;"><b>* PWP :</b>
													<select style="width:50%;height: 27px;"  id="globalPWPId" name="globalPWPId" onclick="javascript:getSelectedglobalPWPId(globalPWPId)"> 
														<option id="No" value="No">No</option>
														<option id="Yes" value="Yes">Yes</option>
													</select>
													</li>
													<li class="txt_attr_name" style="width: 25%;"><b>* PYG :</b>
													 <select style="width:50%;height: 27px;"  id="globalPYGId" name="globalPYGId" onclick="javascript:getSelectedglobalPYGId(globalPYGId)"> 
														<option id="No" value="No">No</option>
														<option id="Yes" value="Yes">Yes</option>
													</select>
													</li>
												</ul>
						<%--RMS - UDA enhancement --%>
						<ul class="pep_info"
							style="font-size: 11px; padding: 0 0 10px !important;">
							<c:if
								test="${not fn:contains(contentDisplayForm.pepUserId,'@') }">
								<c:if
									test="${contentDisplayForm.globalAttributesDisplay.trends != ' '  }">
									<li class="txt_attr_name" style="width: 30%;"><b>Trends
											:</b> <c:out
											value="${contentDisplayForm.globalAttributesDisplay.trends}" />
									</li>
								</c:if>
							</c:if>
							<li class="txt_attr_name" style="width: 60%;"><b>Vendor
									Collection :</b> <!-- create js method --> <input id="vendorCollId"
								name="vendorCollId" maxlength="60"
								value="<c:out value="${contentDisplayForm.globalAttributesDisplay.vendor_collection }" />"
								onclick="" />
							</li>
						</ul>
						<c:if test="${not fn:contains(contentDisplayForm.pepUserId,'@') }">
							<ul class="pep_info"
								style="font-size: 11px; padding: 0 0 10px !important;">
								<c:if
									test="${contentDisplayForm.globalAttributesDisplay.age_group != ' '  }">
									<li class="txt_attr_name" style="width: 30%;"><b>Age
											Group :</b> <c:out
											value="${contentDisplayForm.globalAttributesDisplay.age_group}" />
									</li>
								</c:if>
								<c:if
									test="${contentDisplayForm.globalAttributesDisplay.women_big_Ideas != ' '  }">
									<li class="txt_attr_name" style="width: 25%;"><b>Women
											Big Ideas :</b> <c:out
											value="${contentDisplayForm.globalAttributesDisplay.women_big_Ideas}" />
									</li>
								</c:if>
							</ul>

							<ul class="pep_info"
								style="font-size: 11px; padding: 0 0 10px !important;">
								<c:if
									test="${contentDisplayForm.globalAttributesDisplay.yc_big_Ideas != ' '  }">
									<li class="txt_attr_name" style="width: 30%;"><b>YC
											Big Ideas :</b> <c:out
											value="${contentDisplayForm.globalAttributesDisplay.yc_big_Ideas}" />
									</li>
								</c:if>
							</ul>
						</c:if>
						<%-- end --%>
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
						
						<!--Color Attributes Section starts here  -->
						
							<!--Style,Style Color,SKU Content Section starts over here  -->					
							<div class="cars_panel x-hidden">
							
									<div class="x-panel-header">
											<fmt:message key="lableStyle-Color-SKU" bundle="${display}"/>
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
									
									<c:if test="${not empty StylePetsFound}">								 	
									
									
									<div class="x-panel-body" style="width:906px;">	
										<div>
										     <c:out value="${contentDisplayForm.updateContentStatusMessage}"/>										    
										</div>
										<div>
										     <c:out value="${contentDisplayForm.styleDataSubmissionDataMessage}"/>										    
										</div>
										
										<div  id="ajaxResponseUpdateStylePetContentStatus">  </div>
										
										<table id="StyleStyleColorSkuContentId" cellpadding="0"  class="table tree2 table-bordered table-striped table-condensed">
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
						                    <c:set var="skuCount" value="500"/>
						                    
										   	<c:forEach items="${contentDisplayForm.styleAndItsChildDisplay.styleList}" 
							                             var="styleDisplayList"  varStatus="status">							                             
							                           
							                         <portlet:actionURL name="loadStyleAttribute" var="loadStyleAttributeURL">	
							                            <portlet:param name="action" value="loadStyleAttributeAction"></portlet:param>						                         
							                         	<portlet:param name="orinNumber" value="${styleDisplayList.orinNumber}"></portlet:param>
							                         </portlet:actionURL>
							                         
													<tr id="tablereport"  class="treegrid-${countList}">	
														<td style="white-space:nowrap;"><span style="color:red;" id="${styleDisplayList.orinNumber}_notify"></span></td>													
													 	<td><a  href="javascript:;"  id="styleAnchorTag"  onclick="resetColorAttributes()" >${styleDisplayList.orinNumber}</a></td>
													 	<input type="hidden" id="styleOrinNumberId" name="styleOrinNumber" value="${styleDisplayList.orinNumber}"></input>
														<td><c:out value="${contentDisplayForm.styleInformationVO.styleId}"/></td>
														<td><c:out value="${styleDisplayList.color}"/></td>
														<td><c:out value="${styleDisplayList.vendorSize}" /></td>
														<td><c:out value="${styleDisplayList.omniSizeDescription}" /></td>											
													
														<td><span class="contentStatusId"><c:out value="${styleDisplayList.contentStatus}"/></span></td>	
														<input type="hidden" name="hdnPETStatusCode" id="hdnPETStatusCode" value="${styleDisplayList.petState}"/>	
														  
														<input type="hidden" id="styleContentStatusId" name="styleContentStatus" value="${styleDisplayList.contentStatus}"></input>								
														<td><c:out value="${styleDisplayList.completionDate}" /></td>
														<portlet:actionURL var="updateContentStatusForStyle">
                                                               <portlet:param name="action" value="updateContentStatusForStyle" />
                                                        </portlet:actionURL>
														
														<c:choose>
														    <c:when test="${contentDisplayForm.roleName == 'vendor'}">
														                    <c:if test="${empty contentDisplayForm.readyForReviewMessage}">	
															                  <td><input type="button"  id="styleSubmit" name="submitStyleData"  value="Submit"   style="height:30px; padding:0 15px;" 
															                  onclick="javascript:saveContentPetAttributesWebserviceResponse('${saveContentPetAttributes}','<c:out value="${styleDisplayList.orinNumber}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>','<c:out value="${contentDisplayForm.productAttributesDisplay.dropDownList.size()}"/>','<c:out value="${contentDisplayForm.legacyAttributesDisplay.dropDownList.size()}"/>', '<c:out value="${contentDisplayForm.productAttributesDisplay.radiobuttonList.size()}"/>', '<c:out value="${contentDisplayForm.legacyAttributesDisplay.radiobuttonList.size()}"/>','${updateContentPetStyleDataStatus}','vendor', 'Approve', '')"/></td>
															                </c:if>	
															     			<c:if test="${not empty contentDisplayForm.readyForReviewMessage}">	
															     				<td><input type="button"  id="styleSubmit" name="submitStyleData"  value="Submit"   style="height:30px; padding:0 15px;"  disabled="disabled"/></td>	
															     			</c:if>				 
														    </c:when>									   
														    <c:when test="${contentDisplayForm.roleName == 'dca'}">
														           
															     <td style="white-space: nowrap;">
                                                                 <input type="button"  id="styleSubmit" name="submitStyleData"  value="Approve"   style="height:30px; padding:0 15px;" 
															     onclick="javascript:saveContentPetAttributesWebserviceResponse('${saveContentPetAttributes}','<c:out value="${styleDisplayList.orinNumber}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>','<c:out value="${contentDisplayForm.productAttributesDisplay.dropDownList.size()}"/>','<c:out value="${contentDisplayForm.legacyAttributesDisplay.dropDownList.size()}"/>', '<c:out value="${contentDisplayForm.productAttributesDisplay.radiobuttonList.size()}"/>', '<c:out value="${contentDisplayForm.legacyAttributesDisplay.radiobuttonList.size()}"/>','${updateContentPetStyleDataStatus}','dca', 'Approve', '')" /></td>						 
														    </c:when>
														    <c:when test="${contentDisplayForm.roleName == 'readonly'}">
															     <td><input type="button"  id="styleSubmit" name="submitStyleData"  value="Submit"   style="height:30px; padding:0 15px;" onclick="javascript:updateContentStatusForStyleData();" disabled="true"/></td>						 
														    </c:when>
								                       </c:choose>
														
														
													
													</tr>
																	
											
											         <% int colorRows=1;  %>
													<c:forEach items="${styleDisplayList.styleColorList}" var="styleDisplayColorList"  varStatus="styleColorList">	
													         <!--  <c:set var="subcount" value="${subcount + styleColorList.count}" />	-->																				
													       	  <portlet:resourceURL var="getStyleColorAttributeDetails" id ="getStyleColorAttributeDetails">		
																             	<portlet:param name="styleColorOrinNumber" value="${styleDisplayColorList.orinNumber}"></portlet:param>
							                                  </portlet:resourceURL>
													          <tr class="treegrid-${subcount} treegrid-parent-${countList}" >               
															   <td style="white-space:nowrap;">&nbsp;<span style="color:red;" id="${styleDisplayColorList.orinNumber}_notify" class="notify"></span></td> 
															   <c:choose>
															   <c:when test="${contentDisplayForm.roleName == 'vendor'}">
															  	 <td><a id="<c:out value="${styleDisplayColorList.orinNumber}"/>"  href="#colorAttributeSection"  onclick="getStyleColorAttributes('${getStyleColorAttributeDetails}','<c:out value="${styleDisplayColorList.orinNumber}"/>', <%= colorRows %>, 'vendor')">${styleDisplayColorList.orinNumber}</a></td>
															   </c:when>
															   <c:when test="${contentDisplayForm.roleName == 'dca'}">
															  	 <td><a  id="<c:out value="${styleDisplayColorList.orinNumber}"/>" href="#colorAttributeSection"  onclick="getStyleColorAttributes('${getStyleColorAttributeDetails}','<c:out value="${styleDisplayColorList.orinNumber}"/>', <%= colorRows %>, 'dca')">${styleDisplayColorList.orinNumber}</a></td>
															   </c:when>
															    <c:when test="${contentDisplayForm.roleName == 'readonly'}">
															    	<td><a  id="<c:out value="${styleDisplayColorList.orinNumber}"/>" href="#colorAttributeSection"  onclick="getStyleColorAttributes('${getStyleColorAttributeDetails}','<c:out value="${styleDisplayColorList.orinNumber}"/>', <%= colorRows %>, 'readonly')">${styleDisplayColorList.orinNumber}</a></td>
															    </c:when>
															   </c:choose>
															   <input type="hidden" id="selectedStyleColorOrinNumber" name="selectedStyleColorOrinNumber" value=""></input>   
															   															  														  
															   <input type="hidden" id="styleColorOrinNumberId" name="styleColorOrinNumber" value="${styleDisplayColorList.orinNumber}"></input>                                                                                       
																<c:if test="${styleDisplayColorList.petState == 'Initiated' || styleDisplayColorList.petState == 'Ready_For_Review'}">
																	<input type="hidden" name="hdnStyleColorOpen" id="hdnStyleColorOpen" value="true"/>
																</c:if>																
															   <td><c:out value="${contentDisplayForm.styleInformationVO.styleId}"/></td>                                                                                         
															   <td><c:out value="${styleDisplayColorList.color}"/></td> 
															   <td><c:out value="${styleDisplayColorList.vendorSize}" /></td>                                                                                        
															   <td><c:out value="${styleDisplayColorList.omniSizeDescription}" /></td>  
															   <input type="hidden" id="styleColorContentStatusId" name="styleColorContentStatus" value="${styleDisplayColorList.contentStatus}"></input> 	
															   <input type="hidden" id="styleColorCountId" name="styleColorCount" value="<c:out value="${styleDisplayList.styleColorList.size()}"/>"></input> 
															   <td  id="petColorRow_id<%= colorRows %>"  class="petColorRowClass<%= colorRows %>"><c:out value="${styleDisplayColorList.contentStatus}"/></td>														      
															   <td><c:out value="${styleDisplayColorList.completionDate}" /></td> 
															   <c:choose>
																    
																	 <c:when test="${contentDisplayForm.roleName == 'vendor'}">
																   
																	    <td style="white-space: nowrap;">
																	    <input type="button" id="styleColorSaveButtonId<%= colorRows %>" style="height:30px; padding:0 15px;" value="Style-Color Save" onclick="javascript:saveStyleColor('${saveContentColorAttributes}', '','treegrid-parent-${subcount}')" />
																	    <input type="button"    id="styleColorSubmitButtonId<%= colorRows %>"   name="styleColorSubmitButtonId<%= colorRows %>"  value="Submit"   style="height:30px; padding:0 15px;" onclick="javascript:getUpdateStyleColorContentPetStatusWebserviceResponse('${updateContentPetStyleColorDataStatus}','<c:out value="${styleDisplayColorList.orinNumber}"/>','<c:out value="${styleDisplayColorList.contentStatusCode}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>','vendor',this.id,<%= colorRows %>,'<c:out value="${styleDisplayColorList.imageStatus}"/>','${styleDisplayList.orinNumber}','treegrid-parent-${subcount}')"/></td>
																	     <input type="hidden" id="styleColorSubmitButtonId<%= colorRows %>"  name="styleColorSubmitButtonId<%= colorRows %>"  value="<c:out value="styleColorSubmitButtonId-${styleDisplayColorList.orinNumber}"/>"></input> 
																    </c:when>
																    <c:when test="${contentDisplayForm.roleName == 'dca'}">
																   
																	    <td style="white-space: nowrap;">
                                                                        <input type="button" id="styleColorSaveButtonId<%= colorRows %>" style="height:30px; padding:0 15px;" value="Style-Color Save" onclick="javascript:saveStyleColor('${saveContentColorAttributes}', '','treegrid-parent-${subcount}')" />
                                                                        <input type="button"    id="styleColorApproveButtonId<%= colorRows %>"  class="petColorApproveButtonClass<%= colorRows %>" name="styleColorApproveButtonId<%= colorRows %>"  value="Approve"   style="height:30px; padding:0 15px;" onclick="javascript:approveStyleColor('${updateContentPetStyleColorDataStatus}','${styleDisplayList.orinNumber}','${styleDisplayColorList.orinNumber}', 'treegrid-parent-${subcount}', '${contentDisplayForm.pepUserId}', '${contentDisplayForm.roleName}','<fmt:message key="approve.error.no.size.selected" bundle="${display}" />','${styleDisplayColorList.contentStatusCode}',this.id, <%= colorRows %>, '${styleDisplayColorList.imageStatus}')"/></td>
																	    <input type="hidden" id="styleColorApproveButtonId<%= colorRows %>"  name="styleColorApproveButtonId<%= colorRows %>"   value="<c:out value="styleColorApproveButtonId-${styleDisplayColorList.orinNumber}"/>"></input> 
																    </c:when>
																    <c:when test="${contentDisplayForm.roleName == 'readonly'}">
																	    <td style="white-space: nowrap;">
                                                                        <input type="button" id="styleColorSaveButtonId<%= colorRows %>" style="height:30px; padding:0 15px;" value="Style-Color Save" onclick="javascript:saveStyleColor('${saveContentColorAttributes}', '','treegrid-parent-${subcount}')" disabled="disabled"/>
                                                                        <input type="button"  id="styleColorSubmit" name="submitStyleColorData"  value="Submit"   style="height:30px; padding:0 15px;" onclick="javascript:getUpdateStyleColorContentPetStatusWebserviceResponse('${updateContentPetStyleColorDataStatus}','<c:out value="${styleDisplayColorList.orinNumber}"/>','<c:out value="${styleDisplayColorList.contentStatusCode}"/>','<c:out value="${contentDisplayForm.pepUserId}"/>','<c:out value="${contentDisplayForm.roleName}"/>', '', '','<c:out value="${styleDisplayColorList.imageStatus}"/>','${styleDisplayList.orinNumber}','treegrid-parent-${subcount}')" disabled="disabled"/></td>
																    </c:when>
																    <c:otherwise><td>&nbsp;</td></c:otherwise>
								                               </c:choose>                                                                                                                                                                               
															                                                                 
														   </tr>   
														   	 
															   <c:forEach items="${styleDisplayColorList.skuList}" var="styleColorsChildSkuList"  varStatus="skuList">
															      	
															     <!--  <c:set var="skuCount" value="${subcount +styleColorList.count +skuList.count}" /> --> 
																       <!--   <portlet:actionURL name="loadSkuAttribute" var="loadSkuAttributeURL">	
											                                     <portlet:param name="selectSkuOrinNumberaction" value="loadSkuAttributeAction"></portlet:param>						                         
											                         	          <portlet:param name="skuOrinNumber" value="${styleColorsChildSkuList.orin}"></portlet:param>
								                        		            </portlet:actionURL>
								                        		        -->
								                        		        <portlet:resourceURL var="getSKUAttributeDetails" id ="getSKUAttributeDetails">		
																             	<portlet:param name="skuOrinNumber" value="${styleColorsChildSkuList.orin}"></portlet:param>
							                                            </portlet:resourceURL>
								                        		        
								                        		        <input type="hidden" id="selectedSkuOrinNumber" name="selectedSkuOrinNumber" value=""/>		
															       <tr  class="treegrid-parent-${subcount}">               
															          <td>&nbsp;</td>
															          <td><a  href="#skuAttributeSection"  id="skuAnchorTag"  onclick="resetColorAttributes(); getSkuAttributes('${getSKUAttributeDetails}','<c:out value="${styleColorsChildSkuList.orin}"/>')">${styleColorsChildSkuList.orin}</a></td> 															          								      
															          <td><c:out value="${contentDisplayForm.styleInformationVO.styleId}"/></td>                                                                                         
															          <td><c:out value="${styleColorsChildSkuList.color}"/></td> 
															          <td><c:out value="${styleColorsChildSkuList.vendorSize}" /></td>
                                                                      <td>
                                                                        <c:choose>
                                                                        <c:when test="${styleColorsChildSkuList.omniChannelSizeDescriptionList == null || styleColorsChildSkuList.omniChannelSizeDescriptionList.size() == 0}">
                                                                            <!-- no sizes available to display -->
                                                                            *<select name="omniChannelSizeDescription" style="width:100px;"></select>
                                                                            <c:if test="${contentDisplayForm.roleName == 'dca'}">
                                                                                <div style="color:red;"><fmt:message key="approve.error.no.size.available" bundle="${display}" /></div>
                                                                            </c:if> 
                                                                        </c:when>
                                                                        <c:when test="${styleColorsChildSkuList.omniChannelSizeDescriptionList.size() == 1}">
                                                                            *<select name="omniChannelSizeDescription" style="width:100px;" disabled="disabled">
                                                                                <option value="${styleColorsChildSkuList.omniChannelSizeDescriptionList[0].omniSizeCode}"> 
                                                                                    ${styleColorsChildSkuList.omniChannelSizeDescriptionList[0].omniSizeDesc}
                                                                                </option>
                                                                            </select>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            *<select name="omniChannelSizeDescription"  style="width:100px;" onchange="javascript:handleOmniSizeDescSelect(this)">
                                                                                <option value="Please Select">Please Select</option>
                                                                                <c:forEach items="${styleColorsChildSkuList.omniChannelSizeDescriptionList}" var="omniSizeVO">
                                                                                    <c:choose>
                                                                                    <c:when test="${omniSizeVO.omniSizeCode.equals(styleColorsChildSkuList.selectedOmniChannelSizeCode)}">
                                                                                        <option value="${omniSizeVO.omniSizeCode}" selected="selected">${omniSizeVO.omniSizeDesc}</option>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <option value="${omniSizeVO.omniSizeCode}">${omniSizeVO.omniSizeDesc}</option>
                                                                                    </c:otherwise>
                                                                                    </c:choose>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </c:otherwise>
                                                                        </c:choose>
                                                                        <div class="errorMsg" style="color:red;"></div>
                                                                      </td>
                                                                      <td><c:out value="${styleColorsChildSkuList.contentStatus}"/></td> 												                                        
															          <td><c:out value="${styleColorsChildSkuList.completionDate}" /></td> 	
															          <td>&nbsp;</td>											    		 
														          </tr>   
														   
															            <c:set var="skucount" value="${skucount+1}" />	
													          </c:forEach>
													           <% colorRows++;  %>  	                                                            
											                  <c:set var="subcount" value="${subcount+1}" />
												    </c:forEach>
												    <c:set var="countList" value="${countList+1}" />
							               	</c:forEach> 
							

											
											</tbody>			
		                                   </table>			
															
									</div>
								</c:if>					
									
							</div>
									<!--<a name= "StyleColorSection"></a>-->
									<div id="colorAttributeSection" class="cars_panel x-hidden">
							
										<div class="x-panel-header">
											<fmt:message key="sectionColorAttribute" bundle="${display}"/>
										</div>
									
										<div id="colorAttributeBody" class="x-panel-body" style="width:906px; ">	
										
										  <div id ="ajaxResponseStyleColorAttribute">			</div>
							
								  <table id="styleColorAttributeID" class="table">												
									<tbody>
									    <tr>
									   		<td width="20%"><b>&nbsp;NRF Color Code</b></td>											
											<td><input type="text" id="nrfColorCodeId" name="nrfColorCodeId" value=" " disabled="disabled"/></td>
											<td width="30px"></td>
											<td><b>&nbsp;Secondary Color 1</b></td>
											<td>
												<select style="width: 137px" disabled="disabled" name="secondaryColor1" id="secondaryColorId" >
												 <option id="-1" value="-1">Please Select</option>  
														<c:forEach var="colorMap" items="${contentDisplayForm.colorDataSet}" >	
														   											
															<!-- <option value="${colorMap.key}"  [b]selected="true"[/b] ><c:out value="${colorMap.value}"/></option>--> 
															<option id="${colorMap.key}" value="${colorMap.key}"> ${colorMap.value}</option>              
														</c:forEach>
												  										  
												</select>
                                            </td>		
										</tr>
										<tr>
											<td><b>&nbsp;Vendor Color Description</b></td>
											<td><input type="text" id="vendorColorId" name="vendorColor" value=" "  disabled="disabled"/></td>
                                            <td width="30px"></td>
                                          	<td><b>&nbsp;Secondary Color 2</b></td>
											<td>
												<select style="width: 137px" disabled="disabled" name="secondaryColor2" id="secondaryColorId2" >
												  <option id="-1" value="-1">Please Select</option>  
														<c:forEach var="colorMap" items="${contentDisplayForm.colorDataSet}">												
														<!-- <option value="${colorMap.key}"  [b]selected="true"[/b] ><c:out value="${colorMap.value}"/></option>--> 
															<option id="${colorMap.key}" value="${colorMap.key}"> ${colorMap.value}</option>               
														</c:forEach>
												  										  
												</select>
											</td>					
										</tr>
										<tr>
										<td><b>* Omnichannel Color Description</b></td>
											<td><input type="text" id="omniChannelColorDescriptionId" name="omniChannelColorDescription" disabled="disabled"/></td>
											<td width="30px"></td>
											<td><b>&nbsp;Secondary Color 3</b></td>
											<td>
												<select style="width: 137px" disabled="disabled" name="secondaryColor3" id="secondaryColorId3" >
												  <option id="-1" value="-1">Please Select</option>  
														<c:forEach var="colorMap" items="${contentDisplayForm.colorDataSet}">												
															<!-- <option value="${colorMap.key}"  [b]selected="true"[/b] ><c:out value="${colorMap.value}"/></option>--> 
															<option id="${colorMap.key}" value="${colorMap.key}"> ${colorMap.value}</option>             
														</c:forEach>
																							  
												</select> 
											</td>
										</tr>
										<tr>
											<td ><b>* Omnichannel Color Family</b></td>
											<td>
												 <select style="width: 137px" disabled="disabled" name="omniColorFamily" id='a_drop_down_box' >
													<!--  <option value="omnichannelColorFamily1"> </option>-->
													        <option id="-1" value="-1"> Please Select</option>  
														<c:forEach var="colorMap" items="${contentDisplayForm.colorDataSet}">												
														<!--  	<option value="${colorMap.key}"  [b]selected="true"[/b] ><c:out value="${colorMap.value}"/></option>   -->
															<option id="${colorMap.key}" value="${colorMap.key}"> ${colorMap.value}</option>               
														</c:forEach>	
												</select> 
											</td>
											<td width="30px"></td>
											<td><b>&nbsp;Secondary Color 4</b></td>
											<td>
												<select style="width: 137px" disabled="disabled" name="secondaryColor4" id="secondaryColorId4" >
												  <option id="-1" value="-1">Please Select</option>  
														<c:forEach var="colorMap" items="${contentDisplayForm.colorDataSet}">												
															<!-- <option value="${colorMap.key}"  [b]selected="true"[/b] ><c:out value="${colorMap.value}"/></option>--> 
															<option id="${colorMap.key}" value="${colorMap.key}"> ${colorMap.value}</option>             
														</c:forEach>
												  								  
												</select> 
											</td>
										</tr>
										<tr>
											<td colspan="5" align="left">
											</td>
										</tr>
									</tbody>	
								</table>							
																
									</div>				
						      </div>	  
				
						
						<!--SKU Attributes Section starts here  -->	
						<!--<a name= "SKU_Attribute_Section"></a>-->
						<div class="cars_panel x-hidden"  id="skuAttributeSection" >
							
									<div class="x-panel-header">
										<fmt:message key="sectionSKUAttribute" bundle="${display}"/>
									</div>
									
									<div id="ajaxResponseSkuAttribute" class="x-panel-body" style="width:906px;">	
															
									</div>				
									
						</div>	
						
						<!--Product  Attributes Section starts here  -->	
						<div class="cars_panel x-hidden"  id="productAttributeSection">
					 <form:form commandName="contentDisplayForm1" modelAttribute="fdForm" method="post" action="${formAction}" id="contentDisplayForm1"> 
							
									<div class="x-panel-header">
										<fmt:message key="sectionProductAttribute" bundle="${display}"/>
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
													    <input type=hidden id="petId" name="petId" value="${contentDisplayForm.styleInformationVO.orin}"/>	
													   	<c:if test="${contentDisplayForm.roleName == 'readonly'}">												   
														    <select style="width:58%;height: 27px;"  id="iphCategoryDropDownId" name="iphCategoryDropDown" onchange="calliph('${getIPHCategory}','<c:out value="${contentDisplayForm.styleInformationVO.orin}"/>')" disabled="disabled">
														        <option id="select" value="select" selected> Please Select</option>
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
												<!-- Changes made Cognizant for sorting the attributes starts. -->
												  <% int i=1;  %>
												 <% int k=1;  %>
												 <% int j=1;  %>
													<c:forEach items= "${contentDisplayForm.productAttributesDisplay.displayList}"  var="categoryDisplayList" >
													
															<c:if test="${categoryDisplayList.attributeFieldType == 'Drop Down'}">																				
															<tr>
																<td><c:if test="${categoryDisplayList.isMandatory == 'Yes'}">* </c:if>
																<c:out value="${categoryDisplayList.displayName}"/></td>
																
																<input type="hidden" name="dropdownAttributeNameXpath"  id="dropdownAttributeNameXpath_id<%= i %>"  value="${categoryDisplayList.displayName}#${categoryDisplayList.attributePath}" />
																<input type="hidden" name="dropdownAttributeName"  id="dropdownAttributeName<%= i %>"  value="${categoryDisplayList.attributeName}" />	
																<input type="hidden" name="isPIMMandatory_id"  id="isPIMMandatory_id<%= i %>"  value="${categoryDisplayList.isMandatory}" />
																<input type="hidden" name="isPIMEditable_id"  id="isPIMEditable_id<%= i %>"  value="${categoryDisplayList.isEditable}" />															
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
																				<c:if test="${fn:toUpperCase(dropDownMap.key) ==fn:toUpperCase(savedDropDownMap.key)}">
																					<c:set var="stSelected" value="selected='selected'"/>
																					 <option value="${dropDownMap.key}"  <c:out value="${stSelected}"/> ><c:out value="${dropDownMap.value}"  escapeXml="false"/></option> 
																					 <c:set var="highlited" value="${savedDropDownMap.key}"/>
																				</c:if>                                                     
					                                                        </c:forEach>
                                                    							<%--VP22 - default value as NA  - start --%>
                                                    							<c:if test="${empty categoryDisplayList.savedDropDownValuesMap && dropDownMap.key == 0 }">
                                                    								<option value="${dropDownMap.key}"  selected='selected' > <c:out value="${dropDownMap.value}"  escapeXml="false"/></option> 
												 									<c:set var="highlited" value="${dropDownMap.key}"/>
                                                    							</c:if>
                                                    							<%--VP22 - end --%>
													 						 <c:if test="${fn:toUpperCase(dropDownMap.key) != fn:toUpperCase(highlited)}">
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
																				<c:if test="${fn:toUpperCase(dropDownMap.key) ==fn:toUpperCase(savedDropDownMap.key)}">
																					<c:set var="stSelected" value="selected='selected'"/>
																					 <option value="${dropDownMap.key}"  <c:out value="${stSelected}"/> escapeXml="false"><c:out value="${dropDownMap.value}"  escapeXml="false"/></option> 
																					 <c:set var="highlited" value="${savedDropDownMap.key}"/>
																				</c:if>                                                     
					                                                        </c:forEach>
					                                                        <%--VP22 - default value as NA  - start --%>
					                                                        							<c:if test="${empty categoryDisplayList.savedDropDownValuesMap && dropDownMap.key == 0 }">
					                                                        								<option value="${dropDownMap.key}"  selected='selected' > <c:out value="${dropDownMap.value}"  escapeXml="false"/></option> 
																					 						<c:set var="highlited" value="${dropDownMap.key}"/>
					                                                        							</c:if>
					                                                        							<%--VP22 - end --%>
																			  <c:if test="${fn:toUpperCase(dropDownMap.key) != fn:toUpperCase(highlited)}">
																			  	<option value="${dropDownMap.key}"/> <c:out value="${dropDownMap.value}"  escapeXml="false"/></option>
																			</c:if>			
																		</c:forEach>														
															        	</select>
															        	<%-- VP-28 display selected values  - start--%>	
															        	<select style="width:250px;height:80px;" multiple disabled id="dropDownsId_id<%= i %>_value">
															        		<c:forEach  var="savedDropDownMap"  items="${categoryDisplayList.savedDropDownValuesMap}" >
																				 <option value="${savedDropDownMap.key}"  escapeXml="false"><c:out value="${savedDropDownMap.value}"  escapeXml="false"/></option> 
					                                                        </c:forEach>
					                                                        <c:if test="${empty categoryDisplayList.savedDropDownValuesMap }">
					                                                        	<c:forEach var="dropDownMap" items="${categoryDisplayList.dropDownValuesMap}" begin="0" end="0">
					                                                        		<c:if test="${dropDownMap.key eq 0 }">
					                                                        			<option value="0"  > <c:out value="N/A"  escapeXml="false"/></option>
					                                                        		</c:if>
					                                                        	</c:forEach>
					                                                        </c:if>
															        	</select>
															        	<%-- VP-28 display selected values - end --%>	
																	</c:if>
																	<!-- Change for Multi/Single Drop Down Ends -->																	
														        		</td>															
																										
															</tr>
															<% i++; %>
															</c:if>	
														                                                               
													
													<input type="hidden" id="paDropDownCounter" name="paDropDownCounter" value="${contentDisplayForm.productAttributesDisplay.dropDownList.size()}"></input>
												    
												    
												      <c:if test="${categoryDisplayList.attributeFieldType == 'Radio Button'}">
												     
															<input type="hidden" name="paRadioAttributeXpath"  id="paRadioAttributeXpath_id<%= k%>"  value="${categoryDisplayList.displayName}#${categoryDisplayList.attributePath}" />
															<input type="hidden" name="paRadioAttributeName"  id="paRadioAttributeName_id<%= k%>"  value="${categoryDisplayList.attributeName}" />
															<input type="hidden" name="radioButtonHidden_id" id="radioButtonHidden_id<%= k %>" value="" />
															<input type="hidden" name="isPIMRadioMandatory_id"  id="isPIMRadioMandatory_id<%= k %>"  value="${categoryDisplayList.isMandatory}" />	
															<input type="hidden" name="isPIMRadioEditable_id"  id="isPIMRadioEditable_id<%= k %>"  value="${categoryDisplayList.isEditable}" />
															 <div id="radiofieldSet_id<%=k %>" >
															  <tr>
																<td><c:if test="${categoryDisplayList.isMandatory == 'Yes'}">* </c:if>
																<c:out value="${categoryDisplayList.displayName}"/></td>
																<td> <table><tr><td> <fieldset id="radiofieldSet_id<%=k %>" onclick="javascript:pimRadioButtonValues(<%= k %>, '<c:out value="${contentDisplayForm.productAttributesDisplay.radiobuttonList.size()}"/>');setFurAttributes(<%= k%>, '<c:out value="${categoryDisplayList.attributeName}" />')">
																  <% int z=1;  %>
																	<c:forEach var="dropDownMap" items="${categoryDisplayList.radioButtonValuesMap}">	
																		<c:if test="${categoryDisplayList.savedRadioButtonValuesMap.size() > 0 }" >
																			<c:forEach var="savedDropDownMap" items="${categoryDisplayList.savedRadioButtonValuesMap}">	
																		 	 <input type="radio"  id="paRadio_Id<%=k %><%=z %>"  name="paRadio<%=k %>" value="${dropDownMap.key}" <c:if test="${fn:toUpperCase(savedDropDownMap.key) == fn:toUpperCase(dropDownMap.key)}"> checked="checked"  </c:if>/><c:out value="${dropDownMap.value}" escapeXml="false"/>  &nbsp;&nbsp;		
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
                                                         <input type="hidden" name="isPIMTextEditable_id"  id="isPIMTextEditable_id<%= j %>"  value="${categoryDisplayList.isEditable}" />
                                                         <input type="hidden" name="textFieldHidden" id="textFieldHidden_id<%= j %>" value="" />
                                                         <input type="hidden" name="paTextAttributeXpath"  id="paTextAttributeXpath_id<%= j %>"  value="${categoryDisplayList.displayName}#${categoryDisplayList.attributePath}" />
                                                         <input type="hidden" name="paTextAttributeName"  id="paTextAttributeName_id<%= j %>"  value="${categoryDisplayList.attributeName}" />
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
						<c:if test="${not fn:contains(contentDisplayForm.pepUserId,'@') }">
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
																<input type="hidden" name="isBMEditable_id"  id="isBMEditable_id<%= bmDropDownCount %>"  value="${blueMartiniAllList.isEditable}" />	
																<input type="hidden" name="blueMartiniDropDownAttributeNameXpath"  id="blueMartiniDropDownAttributeNameXpath_id<%= bmDropDownCount %>"  value="${blueMartiniAllList.displayName}#${blueMartiniAllList.attributePath}" />															
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
																				 	<c:if test="${fn:toUpperCase(dropDownMap.key) ==fn:toUpperCase(savedDropDownMap.key)}">
																				 	<c:set var="stSelected" value="selected='selected'"/>
																				 	 <option value="${dropDownMap.key}"  <c:out value="${stSelected}"/> ><c:out value="${dropDownMap.value}"  escapeXml="false"/></option>
																				 		 <c:set var="highlited" value="${savedDropDownMap.key}"/>
																				 	</c:if>
					                                                        							</c:forEach>
					                                                      	 					<c:if test="${fn:toUpperCase(dropDownMap.key) != fn:toUpperCase(highlited)}">
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
																				 	<c:if test="${fn:toUpperCase(dropDownMap.key) ==fn:toUpperCase(savedDropDownMap.key)}">
																				 	<c:set var="stSelected" value="selected='selected'"/>
																				 	 <option value="${dropDownMap.key}"  <c:out value="${stSelected}"/> escapeXml="false" ><c:out value="${dropDownMap.value}"  escapeXml="false"/></option>
																				 		 <c:set var="highlited" value="${savedDropDownMap.key}"/>
																				 	</c:if>
					                                                        </c:forEach>
					                                                      	 			<c:if test="${fn:toUpperCase(dropDownMap.key) != fn:toUpperCase(highlited)}">
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
															<input type="hidden" name="bmRadioAttributeXpath"  id="bmRadioAttributeXpath_id<%= c%>"  value="${blueMartiniAllList.displayName}#${blueMartiniAllList.attributePath}" />
															 <input type="hidden" name="bmradioButtonHidden_id" id="bmradioButtonHidden_id<%= c%>" value="" />
															<input type="hidden" name="isBMRadioMandatory_id"  id="isBMRadioMandatory_id<%= c %>"  value="${blueMartiniAllList.isMandatory}" />
															 <input type="hidden" name="isBMRadioEditable_id"  id="isBMRadioEditable_id<%= c %>"  value="${blueMartiniAllList.isEditable}" />
															 <div id="bmradiofieldSet_id<%=c %>" >
															  <tr>
																<td><c:if test="${blueMartiniAllList.isMandatory == 'Yes'}">* </c:if><c:out value="${blueMartiniAllList.displayName}"/></td>
																<td> <table><tr><td> <fieldset id="bmradiofieldSet_id<%=c %>" onclick="javascript:bmRadioButtonValues(<%= c %>, '<c:out value="${contentDisplayForm.legacyAttributesDisplay.radiobuttonList.size()}"/>')">
																  <% int l=1;  %>
																	<c:forEach var="bmdropDownMap" items="${blueMartiniAllList.radioButtonValuesMap}">
																		<c:if test="${blueMartiniAllList.savedRadioButtonValuesMap.size() > 0 }" >
																		<c:forEach var="bmsavedDropDownMap" items="${blueMartiniAllList.savedRadioButtonValuesMap}">	
																	 	 <input type="radio"  id="bmRadio_Id<%=c %><%=l %>"  name="bmRadio<%=c %>" value="${bmdropDownMap.key}" escapeXml="false" <c:if test="${fn:toUpperCase(bmsavedDropDownMap.key) == fn:toUpperCase(bmdropDownMap.key)}"> checked="checked"  </c:if>/><c:out value="${bmdropDownMap.value}" escapeXml="false"/>  &nbsp;&nbsp;		
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
																<input type="hidden" name="blueMartiniTextAttributeNameXpath"  id="blueMartiniTextAttributeNameXpath_id<%= bmTextFieldCount %>"  value="${blueMartiniAllList.displayName}#${blueMartiniAllList.attributePath}" />
																<input type="hidden" name="bmTextFieldHidden" id="bmTextFieldHidden_id<%= bmTextFieldCount %>" value="" />
																<input type="hidden" name="isBMTextMandatory_id"  id="isBMTextMandatory_id<%= bmTextFieldCount %>"  value="${blueMartiniAllList.isMandatory}" />
																<input type="hidden" name="isBMTextEditable_id"  id="isBMTextEditable_id<%= bmTextFieldCount %>"  value="${blueMartiniAllList.isEditable}" />
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
							</c:if></form:form>
						
							<!--Pet Content Management Display starts here  -->	
						<div class="cars_panel x-hidden collapsed">
							
									<div class="x-panel-header">
									   	<fmt:message key="sectionPetContentManagement" bundle="${display}"/>
										
									</div>
									
									<div class="x-panel-body" style="width:906px;">	
									
											<table class="pep_info" cellspacing="0" cellpadding="0"  style="border: 0px;">
												<tr><td>
								                 <label><font color="blue">1 Style Level PET found, displaying the  Style Level PET.</font></label>
								                </td> </tr>
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
						<div class="cars_panel x-hidden collapsed">
							
									<div class="x-panel-header">
										 	<fmt:message key="sectionChildSKU" bundle="${display}"/>
									</div>
									
									<div class="x-panel-body" style="width:906px;">	
									  
											<table class="pep_info" cellspacing="0" cellpadding="0"  style="border: 0px;">
								                <tr><td><label><font color="blue"><c:out value="${fn:length(contentDisplayForm.childSkuDisplayList) }"/>  SKU Level Pets found, displaying the  SKU Level Pets.</font>
								                </label></td></tr>
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
		<script>
		</script>
		</body>