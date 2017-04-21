<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
     <title>WorkFlow Display.</title>
<script src="<%=request.getContextPath()%>/js/imageManagment.js"></script> 

<style>
#shotType {
	width: 77px;
	text-align:center;
}

.scrollbarsetScroll{
	max-height: 335px;
	overflow-y: auto;
	

}
</style>

</head>

<script>
var count = 0;
var pickedup;
var rowClick;
var inputChangedNew = true;
function openImage(url){
	var win = window.open(url, '', "toolbar=no,resizable=no,width=640,height=480,scrollbars=yes");
}
function trClick(){
	var completionStatusId1 = '';
	count = 0;
	var flag = false;
	
	var selectedOrin = $("#selectedOrinVPI").val();	
	document.getElementById("selectedColorOrinNum").value = selectedOrin;	
	selectedOrin = selectedOrin.trim().length;  	
	completionStatusId1 = $("#selectedOrinVPI").val()+'_statusId';
	   
	var returnCarsFlag = $('#'+ $("#selectedOrinVPI").val() +'_carsFlag').text();
	
	
		if(selectedOrin <= 9){
			return;
		}else{	
	       if (pickedup != null) {
               pickedup.css( "background-color", "" );
				var tableHeaderRowCount = 1;
				var table = document.getElementById('vImage');
				var rowCount = table.rows.length;
				for (var i = tableHeaderRowCount; i < rowCount; i++) {
				table.deleteRow(tableHeaderRowCount);
				}
          		     
            }		
			  $(this).css('background-color','#DCE2EC');
			  pickedup = $( this );		
             var url = $("#ajaxaction").val(); 
             var selectedOrin = $("#selectedOrinVPI").val();  
			 var shotTypeArray = ["A","B","C","D","E","F","G","H","I","SW"];
			 var shotTypeJsonArray=["A","B","C","D","E","F","G","H","I","SW"];
			 var shotTypeParamArray=[];        
	         $.ajax({
		         url: url ,
		         type: 'GET',
		         datatype:'json',
		         data: { selectedColorOrin:selectedOrin },
			         success: function(data){
			         var json = $.parseJSON(data);   
					 $(json).each(function(i,val){	
					//Logic for approve disbaled on submit image status					
					if(val.imageStatus == 'Initiated' || val.imageStatus == 'Rejected'){						
						document.getElementById('image_approve').disabled=true;
						flag = true;
						document.getElementById('flagHidden').value = "";
						document.getElementById('flagHidden').value = flag;
					}				
		             if (typeof val.imageID  !== "undefined" && val.imageID){
						  count++;						
						  shotTypeJsonArray = ["---Select---","A","B","C","D","E","F","G","H","I","SW"];						 
						  $(json).each(function(j,val1){
						  
							for(var k=0;k<shotTypeJsonArray.length;k++){								
								if(shotTypeJsonArray[k]==val1.shotType){									
										shotTypeJsonArray.splice(k,1); 									
								}								
							}
						  
						  });
						  
						 if(typeof val.shotType != "undefined"){
							shotTypeJsonArray.push(val.shotType);
						 }
						 shotTypeParamArray = shotTypeJsonArray;
						VPISampleImageRows(val.imageID,val.imageName,val.imagefilepath,val.imageLocation,val.shotType ,val.linkStatus,val.imageStatus,val.sampleId,val.sampleReceived,
								val.silhouette,val.turnInDate,val.sampleCordinatorNote,val.action,val.role, shotTypeParamArray, val.rejectType, val.rejectReason, val.rejectionTimestamp);
						
						
						
						
					 	
					}
				});
								
				if(count > 10 ){
					document.getElementById('btnImageUploadAction').disabled=true;
					document.getElementById('saveImage').disabled=true;
					document.getElementById('image_approve').disabled=true;
					count = 0;
				}else{
					
				if(document.getElementById('hiddenImageStatus').value == "Completed" || document.getElementById('hiddenImageStatus').value == "Approved" || $("#"+completionStatusId1).text() == 'Completed' || $("#"+completionStatusId1).text() == 'Approved'){			
					
					document.getElementById('btnImageUploadAction').disabled = true ;					
					document.getElementById('saveImage').disabled = true ;
					document.getElementById('image_approve').disabled=true;				
					document.getElementById('shotType').disabled=true;
				}else{					
					
					document.getElementById('btnImageUploadAction').disabled = false ;
					document.getElementById('saveImage').disabled = false ;
					document.getElementById('image_approve').disabled=false;
					
				}				
				//Logic for Image Status and SuperImage Status combination
				if(flag == false){					
					if(document.getElementById('hiddenImageStatus').value == "Completed" || document.getElementById('hiddenImageStatus').value == "Approved" || $("#"+completionStatusId1).text() == 'Approved' || $("#"+completionStatusId1).text() == 'Completed'){
						
						document.getElementById('image_approve').disabled = true ;
					}
					else{
						
						document.getElementById('image_approve').disabled = false ;	
					}
				}
				else if(flag == true){	
					
					document.getElementById('image_approve').disabled = true ;
					} 
				}				
				if(count==10){
					document.getElementById('btnImageUploadAction').disabled=true;
					var shotTypeNull = '';
					var imageStatusTable = '';
					var imageStatusApproved = '';					
					var selectedOrin = $("#selectedOrinVPI").val();	
					$("#vImage tr:gt(0)").each(function(i){		
						var hiddenShotType = $(this).find("#hiddenShotType").val();	
						imageStatusTable = $(this).find("#imageStatus").html();							
						if(hiddenShotType == 'undefined' || hiddenShotType == null){								
							shotTypeNull = 'Y';								
						}
						if(imageStatusTable=='Approved'){
							imageStatusApproved = 'Y'
						}
					});					
					if(shotTypeNull!='Y'){						
						document.getElementById('saveImage').disabled=true;
					}
					if(imageStatusApproved=='Y'){
						document.getElementById('image_approve').disabled=true;
					}						
					if(flag == true){
						document.getElementById('image_approve').disabled=true;
					}						
				}
				if(count==0){					
						document.getElementById('saveImage').disabled=true;
						//document.getElementById('image_approve').disabled=true;//Fix for 720									   
				}
                  
				 if(returnCarsFlag == 'Yes'){
					document.getElementById('btnImageUploadAction').disabled=true; 
				 }
						  
				   var reject_status = 'N';
				   var review_status = 'N';
				   var initiated_status = 'N';
				   var completed_status = 'N';				   
				   
				   
				   
				  // Setting the image status for image management based on the child status for 275
						$(json).each(function(k,val2){						
							
							if(val2.imageStatus=='Rejected' || val2.imageStatus == 'Rejected_By_DCA'){
								
								reject_status = 'Y';
							}
							if(val2.imageStatus=='Ready_For_Review' || val2.imageStatus=='ReadyForReview'){
								
								review_status = 'Y';
							}
							if(val2.imageStatus=='Initiated'){
								
								initiated_status = 'Y';
							}
							if(val2.imageStatus=='Completed'){
								
								completed_status = 'Y';
							}
							
						  
						  });
						  
				    var onSubmitId = $('#onSubmitId').val();//Replace upper image status on Ajax Success
					var OnRemovalImageId = $('#OnRemovalImageId').val();
						  
					if((typeof onSubmitId != 'undefined' &&  onSubmitId == '100')|| (typeof OnRemovalImageId != 'undefined' &&  OnRemovalImageId == '100')){
						//alert('--OnRemovalImageId--' +OnRemovalImageId +'--onSubmitId--'+onSubmitId);
						  if(reject_status == 'Y'){							  
							  document.getElementById(completionStatusId1).innerHTML = 'Rejected_By_DCA';
						  }
						  else if(review_status == 'Y'){							  
							  document.getElementById(completionStatusId1).innerHTML = 'Ready_For_Review';							  
						  }
						  else if(initiated_status == 'Y'){							  
							  document.getElementById(completionStatusId1).innerHTML = 'Initiated';							  
						  }
						  else if(completed_status == 'Y'){
							  document.getElementById(completionStatusId1).innerHTML = 'Completed';							  
						  }
						  else{
							   document.getElementById(completionStatusId1).innerHTML = 'Initiated';							  
						  }
					}
					// Setting the image status for image management based on the child status END for 275
		  }//End of Success
				   
        }); 
 
    }
}

$(document).ready(function() {	
	if(document.getElementById('uploadStatHidden').value == ""){		
	setTimeout(function(){		
		document.getElementById('btnImageUploadAction').disabled=true;
		document.getElementById('saveImage').disabled=true;
		document.getElementById('image_approve').disabled=true;		
	},1000);
	}	
    $(".tree2 tr").click(function() {	 
	  var selectedOrin = $("#selectedOrinVPI").val(); 
	   document.getElementById("selectedColorOrinNum").value = selectedOrin;		
	   selectedOrin = selectedOrin.trim().length;	  		
		if(selectedOrin <= 9){
			return;
		}else{			
			var prevRows = $(".tree2 tr").css('background-color',"");		
			$(this).css('background-color','#DCE2EC');			
			trClick();
	 	}
  	});
});
function removeVPISampleImageRows(selectedOrin){  
	  var removeImageUrl = $("#removeImageUrl").val(); 
	  var table = document.getElementById("vImage");
	  var imgId=[];
	  var imgName=[];
	  inputChangedNew = document.getElementById("removeFlagOff").value;
	 
	  $('.SelectAllImg').each(function() { 
          if(this.checked){         
          imgId[imgId.length] = $('td:eq( 0 )', $(this).parent().parent()).text();
          imgName[imgName.length] = $('td:eq( 1 )', $(this).parent().parent()).text();
         }
         });
	
	  $.ajax({
				type: 'POST',
				url : removeImageUrl,
				datatype:'json',			
				data: {selectedColorOrin:selectedOrin,imageIDToDel:imgId,imageNameToDel:imgName},			
				success: function(data){
					var json = $.parseJSON(data);
					var resCodeRemove = json.resCodeRemove;
					if(json.responseCodeOnRemove == '100'){
						showImageActionMessage('removeSuccess', '');
					}			
					if(json.responseCodeOnRemove == '101'){
						showImageActionMessage('removeError','');
					}				
					
					//$('#image-operations-Message-Area').fadeOut('fast').html(''); //hotfix for #931 after image removal process;
					
					if(json.responseCodeOnRemove && json.responseCodeOnRemove !== undefined){
						console.log('json.responseCodeOnRemove--' + json.responseCodeOnRemove);
						document.getElementById('OnRemovalImageId').value = json.responseCodeOnRemove;
					}			
						
					
					setUploadVPILink($("#ajaxaction").val(),document.getElementById("selectedColorOrinNum").value,$("#removeImageUrl").val());
					trClick();
					scrollToView('vImage','vImage');
				},
				error:function (xhr, ajaxOptions, thrownError){            	
	            	var error = $.parseJSON(xhr.responseText);               
	             }

			});	
		/* setTimeout(function(){setUploadVPILink($("#ajaxaction").val(),document.getElementById("selectedColorOrinNum").value,$("#removeImageUrl").val());trClick();scrollToView('vImage','vImage');},2000); */	
	}


function VPISampleImageRows(imageId,imageName,imagefilepath,imageLocation,shotType ,linkStatus,imageStatus,sampleId,sampleReceived,
silhouette,turnInDate,sampleCordinatorNote,action,role, shotTypeParamArray, rejectCode, rejectReason, rejectionTimestamp) {	
	var table = document.getElementById("vImage");	
	var rowCount = table.rows.length;		
	var row = table.insertRow(rowCount);	
  	if(role=='vendor'){	
	  	//Vendor Login	
	   	var cell1 = row.insertCell(0);
	   	cell1.id="imageId";
	   	cell1.name="imageId";	
		cell1.innerHTML = imageId;
		
		var cell2 = row.insertCell(1);
		cell2.id="imageName";
	   	cell2.name="imageName";
	   	//cell2.innerHTML = imageName;
	   	var imageUrl = document.getElementById('downloadFilePathUrl').value+"?filePath="+imagefilepath+"&imageName="+imageName;
	   	//alert("imageurl :: "+imageUrl);
	   	//cell2.innerHTML = "<a href='"+imageUrl+"' target='_blank' > "+imageName+"</a>";
		cell2.innerHTML = "<a href=\"javascript:openImage(\'"+imageUrl+"\')\"> "+imageName+"</a>";
		
		var cell3 = row.insertCell(2);
		cell3.id="imageLocation";
	   	cell3.name="imageLocation";
	   	cell3.innerHTML = imageLocation;
	
		var cell4 = row.insertCell(3);			
		var selectBox = document.createElement("Select");
		selectBox.name="shotType ";
		selectBox.id="shotType ";	
		selectBox.disabled = true;
		
		var hiddenShotType = document.createElement("input");
		hiddenShotType.type = "hidden";
		hiddenShotType.value = shotType;
		hiddenShotType.id = "hiddenShotType";
		
		cell4.appendChild(hiddenShotType);	
		
		for(var j=0;j<shotTypeParamArray.length;j++){		
			var option1 = document.createElement("OPTION")
				if(shotTypeParamArray[j]==shotType){
					option1.selected="selected";
				}
				option1.text=shotTypeParamArray[j];
				option1.value=shotTypeParamArray[j];
				selectBox.options.add(option1);			
			}
			cell4.appendChild(selectBox);	
	
		/*var cell5 = row.insertCell(4);
		cell5.id="linkStatus";
	    cell5.name="linkStatus";	
		cell5.innerHTML = linkStatus;*/
		
		var cell5 = row.insertCell(4);
		cell5.id="imageStatus";
	    cell5.name="imageStatus";
	    
		if(imageStatus  == 'Rejected' || imageStatus  == 'Rejected_By_DCA'){
			if(rejectCode){
				cell5.innerHTML = imageStatus +"<br/><br/><a href=\"javascript:showPetRejectReasons('"+rejectCode+"','"+rejectReason+"','"+rejectionTimestamp+"')\">Reject Reason</a>";
			}
			else{
				cell5.innerHTML = imageStatus;	
			}
		}else{
			cell5.innerHTML = imageStatus;	
		}
		
		if(imageStatus  == "Initiated"){
			var cell6 = row.insertCell(5);	
			var element7 = document.createElement("input");
			element7.style.fontWeight = 'bold';
			element7.style.width= "59px";
	
			element7.type = "button";
			element7.name="Submit";
			element7.id="Submit";
			element7.value="Submit";
			element7.onclick=function(event){
				getValuesforSubmitorRejectAjax(imageId,imageStatus,element7,event,"","");
				return false;
			};
		}else{
			var cell6 = row.insertCell(5);	
			var element7 = document.createElement("input");
			element7.style.fontWeight = 'bold';
			element7.style.width= "59px";
			element7.type = "button";
			element7.disabled = true;
			element7.name="Submit";
			element7.id="Submit";
			element7.value="Submit";
			
		}
		cell6.appendChild(element7);	
		var cell7 = row.insertCell(6);
		cell7.id="imgSelect";
		cell7.name="imgSelect";	
		
		/*if(imageStatus  == 'Completed' || imageStatus  == 'Ready_For_Review'){
			cell7.innerHTML = 'Remove';
		}else{
			cell7.innerHTML = '<a href="javascript:confirmRemovePopUp('+imageId+',\''+imageName+'\','+rowCount+')">Remove</a>';
		}	*/	
		if(imageStatus  == 'Completed' || imageStatus  == 'Ready_For_Review' || imageStatus  == 'ReadyForReview'){
			cell7.innerHTML = '<input type="checkbox" class="SelectAllImg" id="imgSelect" name="imgSelect" disabled>	';
		}else{
			cell7.innerHTML = '<input type="checkbox" class="SelectAllImg" id="imgSelect" name="imgSelect">	';
		}

	}
  	else if(role == 'dca'){
		//DCA Login	
		var cell1 = row.insertCell(0);
		cell1.id="imageId";
	   	cell1.name="imageId";	
		cell1.innerHTML = imageId;	
		
		var cell2 = row.insertCell(1);
		cell2.id="imageName";
	   	cell2.name="imageName";
	   	//cell2.innerHTML = imageName;
	   	var imageUrl = document.getElementById('downloadFilePathUrl').value+"?filePath="+imagefilepath+"&imageName="+imageName;
	   	//alert("imageurl :: "+imageUrl);
	   	cell2.innerHTML = "<a href=\"javascript:openImage(\'"+imageUrl+"\')\"> "+imageName+"</a>";
		
		var cell3 = row.insertCell(2);
		cell3.id="imageLocation";
	   	cell3.name="imageLocation";
	   	cell3.innerHTML = imageLocation;	
	
		var cell4 = row.insertCell(3);			
		var selectBox = document.createElement("Select")
		selectBox.name="shotType";
		selectBox.id="shotType";
		selectBox.class = "shotType";
		
		if(imageStatus  == "Completed"){
			selectBox.disabled = true;
		}
	
		/*Below code will store the shotType value from Database.*/
		
		var hiddenShotType = document.createElement("input");
		hiddenShotType.type = "hidden";
		hiddenShotType.value = shotType;
		hiddenShotType.id = "hiddenShotType";
		
		cell4.appendChild(hiddenShotType);	
		
		for(var j=0;j<shotTypeParamArray.length;j++){		
			var option1 = document.createElement("OPTION")
			if(shotTypeParamArray[j]==shotType){
				option1.selected="selected";
			}
			option1.text=shotTypeParamArray[j];
			option1.value=shotTypeParamArray[j];
			selectBox.options.add(option1);
		}
	
		cell4.appendChild(selectBox);
		
		var cell5 = row.insertCell(4);
		cell5.id="imageStatus";
	   	cell5.name="imageStatus";
		if(imageStatus  == 'Rejected' || imageStatus  == 'Rejected_By_DCA'){
			if(rejectCode){
				cell5.innerHTML = imageStatus +"<br/><br/><a href=\"javascript:showPetRejectReasons('"+rejectCode+"','"+rejectReason+"','"+rejectionTimestamp+"')\">Reject Reason</a>";
			}
			else{
				cell5.innerHTML = imageStatus;	
			}
		}else{
			cell5.innerHTML = imageStatus;	
		}
	
		//New Changes
		if(typeof imageStatus  == "undefined" || imageStatus  == "Initiated"){
			var cell11 = row.insertCell(5);	
			var element12 = document.createElement("input");
			element12.style.fontWeight = 'bold';
			element12.style.width= "60px";
			element12.type = "button";
			element12.name="Submit";
			element12.id="Submit";
			element12.value="Submit";
			element12.onclick = function(event){
				getValuesforSubmitorRejectAjax(imageId,imageStatus,element12,event,"","");
				return false;		
			};
		}
		else{
			var cell11 = row.insertCell(5);	
			var element12 = document.createElement("input");
			element12.style.fontWeight = 'bold';
			element12.style.width= "60px";
			element12.type = "button";
			element12.disabled = true;
			element12.name="Submit";
			element12.id="Submit";
			element12.value="Submit";
			
		}
		cell11.appendChild(element12);
	
		//For Reject Status need to check
		if(imageStatus  == 'Approved' || imageStatus  == 'Rejected' || imageStatus  == 'Rejected_By_DCA' || imageStatus  == 'Completed'){
			var cell12 = row.insertCell(6);	
			var element13 = document.createElement("input");
			element13.disabled=true;
			element13.style.fontWeight = 'bold';
			element13.style.width= "59px";
			element13.type = "button";
			element13.disabled=true;
			selectBox.disabled = true;
			element13.name="Reject";
			element13.value="Reject";		
		}
		else{
			var cell12 = row.insertCell(6);	
			var element13 = document.createElement("input");	
			element13.style.fontWeight = 'bold';
			element13.style.width= "59px";
			element13.type = "button";
			element13.name="Reject";
			element13.value="Reject";
			element13.onclick = function(event){
				showRejectReason(imageId,imageStatus,element13,event);
				return false;			
			};	
		}	
		cell12.appendChild(element13);	
		var cell13 = row.insertCell(7);
		cell13.id="imgSelect";
		cell13.name="imgSelect";
		
		if(imageStatus  == 'Completed'){
			cell13.innerHTML = '<input type="checkbox" class="SelectAllImg" id="imgSelect" name="imgSelect" disabled>';
		}
		else{
			cell13.innerHTML = '<input type="checkbox" class="SelectAllImg" id="imgSelect" name="imgSelect" >';
		}
	}
  	else if(role=='readonly'){	
	  	//readonly Login	
	   	var cell1 = row.insertCell(0);
	   	cell1.id="imageId";
	   	cell1.name="imageId";	
		cell1.innerHTML = imageId;
		
		var cell2 = row.insertCell(1);
		cell2.id="imageName";
	   	cell2.name="imageName";
	   	//cell2.innerHTML = imageName;
	   	var imageUrl = document.getElementById('downloadFilePathUrl').value+"?filePath="+imagefilepath+"&imageName="+imageName;
	   	//alert("imageurl :: "+imageUrl);
	   	//cell2.innerHTML = "<a href='"+imageUrl+"' target='_blank' > "+imageName+"</a>";
		cell2.innerHTML = "<a href=\"javascript:openImage(\'"+imageUrl+"\')\"> "+imageName+"</a>";
		
		var cell3 = row.insertCell(2);
		cell3.id="imageLocation";
	   	cell3.name="imageLocation";
	   	cell3.innerHTML = imageLocation;
	
		var cell4 = row.insertCell(3);			
		var selectBox = document.createElement("Select");
		selectBox.name="shotType ";
		selectBox.id="shotType ";	
		selectBox.disabled = true;
		
		var hiddenShotType = document.createElement("input");
		hiddenShotType.type = "hidden";
		hiddenShotType.value = shotType;
		hiddenShotType.id = "hiddenShotType";
		
		cell4.appendChild(hiddenShotType);	
		
		for(var j=0;j<shotTypeParamArray.length;j++){		
			var option1 = document.createElement("OPTION")
				if(shotTypeParamArray[j]==shotType){
					option1.selected="selected";
				}
				option1.text=shotTypeParamArray[j];
				option1.value=shotTypeParamArray[j];
				selectBox.options.add(option1);			
			}
			cell4.appendChild(selectBox);	
	
		/*var cell5 = row.insertCell(4);
		cell5.id="linkStatus";
	    cell5.name="linkStatus";	
		cell5.innerHTML = linkStatus;*/
		
		var cell5 = row.insertCell(4);
		cell5.id="imageStatus";
	    cell5.name="imageStatus";
		if(imageStatus  == 'Rejected' || imageStatus  == 'Rejected_By_DCA'){
			if(rejectCode){
				cell5.innerHTML = imageStatus +"<br/><br/><a href=\"javascript:showPetRejectReasons('"+rejectCode+"','"+rejectReason+"','"+rejectionTimestamp+"')\">Reject Reason</a>";
			}
			else{
				cell5.innerHTML = imageStatus;	
			}
		}
		else{
			cell5.innerHTML = imageStatus;	
		}

	}
  	
	if($('input[name=imgSelect]').filter(':enabled').length === 0){
        $('input[name="removeImage"]').attr('disabled', 'disabled');
        $('input[name="imgSelectAll"]').attr('disabled', 'disabled');
	}
	else{
	    $('input[name="removeImage"]').removeAttr('disabled');
        $('input[name="imgSelectAll"]').removeAttr('disabled');
	}
	
}

function setUploadVPILink(url,orin,removeImageUrl,supplierId ){
	document.getElementById("ajaxaction").value=url;
	document.getElementById("selectedOrinVPI").value=orin;
	document.getElementById("removeImageUrl").value=removeImageUrl;
}


function populateColorCode(ColorCode){
	document.getElementById("selectedVendorColorCode").value=ColorCode;
	
}

function getScene7Url(orin, url){
	$.get(url, {selectedColorOrin: orin})
		.done(function(result){
			
			if(result.length){
				var responseJSON = $.parseJSON(result);
				var html = '';
				
				if(responseJSON !== undefined && responseJSON.forEach instanceof Function){
					responseJSON.forEach(function(item){
					html += '<tr>'
					html += '<td>' + item.shotType + '</td>'
						+ "<td><a href=\"javascript:openGRPScen7Image(\'" + escape(item.imageUrl) + "\');\">ImageURL</a></td>"
						+ "<td><a href=\"javascript:openGRPScen7Image(\'" + escape(item.swatchUrl) + "\');\">SwatchURL</a></td>"
						+ "<td><a href=\"javascript:openGRPScen7Image(\'" + escape(item.viewUrl) + "\');\">ViewURL</a></td>"
						+ '/<tr>';
					});
					
					if(html.length)
						$('#scene7-rows').html(html);
					else{
						$('#scene7-rows').html(
							'<tr>'
							+ '<td colspan="4" align="center"><strong>No Data Found!</strong></td>'										
							+ '</tr>'
						);
					}
				}		
			}else
				console.warn('Invalid Response Received!');
				
		});
};

function getValuesforSubmitorRejectAjax(imageId,imageStatus,element,event,selectedRejectReasonCode,selectedRejectReasonText){
	var target = event.srcElement || event.target;
	//console.log($(target).parent().parent().find('select :selected').val());
	var shotTypeValueOnSubmit = $(target).parent().parent().find('select :selected').length ? 
	$(target).parent().parent().find('select :selected').val() : null;
	var url = document.getElementById('submitOrRejectAjaxUrlId').value;
	var selectedOrin = document.getElementById("selectedColorOrinNum").value; 
	var statusparam;
	if(element.value == "Submit"){
		statusparam = "Submit";
	}else if(element.value == "Reject"){
		statusparam = "Reject";
	}
	$("#overlay_imageLoading").show();
	$.ajax({
			type: 'POST',
			url : url,
			datatype:'json',
			data: {'selectedColorOrin': selectedOrin,'imageId' : imageId,'imageStatus' : imageStatus,'statusparam' : statusparam, 
				shotTypeValueOnSubmit: shotTypeValueOnSubmit, rejectType: selectedRejectReasonCode, rejectReason: selectedRejectReasonText},
			cache: true,
			async: true,
			success: function(data){					
				var json = $.parseJSON(data);				
				var responseCode = json.resCodeRet;				
				var responseStatusParam = json.statusParamRet;
				var responseImageId = json.imageIdRet;				
				var responseCodeOnSubmit = json.responseCodeOnSubmit;
				//alert('--responseCode--' +responseCode);
				//alert('--responseCodeOnSubmit--' +responseCodeOnSubmit);
				document.getElementById('onSubmitId').value = responseCodeOnSubmit;//Replace upper image status on Ajax Success				
				if(responseStatusParam == 'Submit'){					
					if(responseCode == '100'){						
						var spanSubmitSuccessId = document.getElementById('imageIdSubmitSuccessSpan');
						if ('textContent' in spanSubmitSuccessId){
							spanSubmitSuccessId.textContent = '';
							spanSubmitSuccessId.textContent = responseImageId;							
						}else{
								spanSubmitSuccessId.innerText = '';
								spanSubmitSuccessId.innerText = responseImageId;								
						}
							//$("#overlay_submitOrReject").show();
							//$("#dialog_submitSuccess").show();
							
							/* jq('#img-dialog-generic').dialog('option', 'imgActionType', 'submitSuccess');
							jq('#img-dialog-generic').dialog('option', 'imageID', responseImageId);
							jq('#img-dialog-generic').dialog('open'); */
							
							showImageActionMessage('submitSuccess', responseImageId); //new message instead of dialog for as per requirement 931
							
							//logic for button disable in submit
								element.disabled = true;
					}else{//Responce code 101 and submit							
							var spanSubmitFailedId = document.getElementById('imageIdSubmitFailSpan');
							if ('textContent' in spanSubmitFailedId){
									spanSubmitFailedId.textContent = '';
									spanSubmitFailedId.textContent = responseImageId;
							}else{
									spanSubmitFailedId.innerText = '';
									spanSubmitFailedId.innerText = responseImageId;
							}
							
							//$("#overlay_submitOrReject").show();
							//$("#dialog_submitFailed").show();

							/* jq('#img-dialog-generic').dialog('option', 'imgActionType', 'submitError');
							jq('#img-dialog-generic').dialog('option', 'imageID', responseImageId);
							jq('#img-dialog-generic').dialog('open'); */
							
							showImageActionMessage('submitError', responseImageId); //new message instead of dialog for as per requirement 931
						}
				}else if(responseStatusParam == 'Reject'){					
					if(responseCode == '100'){						
						var spanRejectSuccessId = document.getElementById('imageIdRejectSuccessSpan');
						if ('textContent' in spanRejectSuccessId){
							spanRejectSuccessId.textContent = '';
							spanRejectSuccessId.textContent = responseImageId;
						}else{
							spanRejectSuccessId.innerText = '';
							spanRejectSuccessId.innerText = responseImageId;
						}
							//$("#overlay_submitOrReject").show();
							//$("#dialog_rejectSuccess").show();
							
							/* jq('#img-dialog-generic').dialog('option', 'imgActionType', 'rejectSuccess');
							jq('#img-dialog-generic').dialog('option', 'imageID', responseImageId);
							jq('#img-dialog-generic').dialog('open'); */
							
							showImageActionMessage('rejectSuccess', responseImageId); //new message instead of dialog for as per requirement 931
							
							//Button Disabled Logic for Reject Success
							element.disabled = true;
					}else {							
							var spanRejectFailId = document.getElementById('imageIdRejectFailedSpan');
							if ('textContent' in spanRejectFailId){
								spanRejectFailId.textContent = '';
								spanRejectFailId.textContent = responseImageId;
							}else{
								spanRejectFailId.innerText = '';
								spanRejectFailId.innerText = responseImageId;
							}
							
							//$("#overlay_submitOrReject").show();
							//$("#dialog_rejectFailed").show();	

							/* jq('#img-dialog-generic').dialog('option', 'imgActionType', 'rejectError');
							jq('#img-dialog-generic').dialog('option', 'imageID', responseImageId);
							jq('#img-dialog-generic').dialog('open'); */
							
							showImageActionMessage('rejectError', responseImageId); //new message instead of dialog for as per requirement 931
						}
					}	
					setUploadVPILink($("#ajaxaction").val(),document.getElementById("selectedColorOrinNum").value,$("#removeImageUrl").val());			
					trClick();
					scrollToView('vImage','vImage');
			},//Success block ends
				error:function (xhr, ajaxOptions, thrownError){            	
            	var error1 = $.parseJSON(xhr.responseText);
               
             },
			 complete: function(){
					$("#overlay_imageLoading").hide();
				}

		});//Ajax Ends		/*setTimeout(function(){setUploadVPILink($("#ajaxaction").val(),document.getElementById("selectedColorOrinNum").value,$("#removeImageUrl").val());trClick();scrollToView('vImage','vImage');},2000); */	
}

function confirmRemovePopUp(imageId,imageName,rowId){	
	document.getElementById('imgHiddenId').value = "";
	document.getElementById('imgRowId').value = "";
	document.getElementById('imgNameHiddenId').value = "";	
	document.getElementById('imgHiddenId').value = imageId;
	document.getElementById('imgNameHiddenId').value = imageName;
	document.getElementById('imgRowId').value = rowId;	
	
	//$("#overlay_submitOrReject").show();
	//$("#dialog_submitRemove").show();
	
	jq('#dialog_submitRemove').dialog('open');
}

function showRejectReason(imageId,imageStatus,element,event) {
	$("#overlay_rejectReason").css("display","block");
	jq("#dialog_rejectReason").dialog('open');
	$("#rejectReasonPopupSubmit").bind("click", function(){
		//validation 
		var selectedRejectReasonText = $("#allImageRejectReasons option:selected").text();
		var selectedRejectReasonCode = $("#allImageRejectReasons option:selected").val();		
		getValuesforSubmitorRejectAjax(imageId,imageStatus,element,event,selectedRejectReasonCode,selectedRejectReasonText);
		$("#overlay_rejectReason").hide();
		jq("#dialog_rejectReason").dialog('close'); //closing upload dialog
		$("#rejectReasonPopupSubmit").unbind("click");
		return false;			
	});
	$("#rejectReasonPopupClose").bind("click", function(){
		$("#overlay_rejectReason").hide();
		jq("#dialog_rejectReason").dialog('close'); //closing upload dialog
		return false;			
	});
}

function showPetRejectReasons(rejectCode, rejectReason, rejectionTimestamp) {
	$("#overlay_petRejectReason").css("display","block");
	jq('#dialog_petRejectReason').dialog('open');
	if(rejectionTimestamp === 'undefined'){
		$("#reject-reason").html(rejectCode + "  " + rejectReason);
	}
	else{
		$("#reject-reason").html(rejectionTimestamp + "  " + rejectCode + "  " + rejectReason);
	}
	$("#petRejectReasonPopupClose").bind("click", function(){
		$("#overlay_petRejectReason").hide();
		jq("#dialog_petRejectReason").dialog('close'); //closing upload dialog
		return false;			
	});
}

function dialogHideonOk(){
	//$("#overlay_submitOrReject").hide();
	//$("#dialog_submitRemove").hide();
	jq("#dialog_submitRemove").dialog('close');
}

function checkApproveImageStatus(orinId,imageStatus){	
	document.getElementById('hiddenOrinId').value =orinId;
	document.getElementById('hiddenImageStatus').value =imageStatus;	
}
</script>

<portlet:defineObjects />
<portlet:resourceURL var="submitOrRejectAjaxUrl" id="submitOrRejectAjaxUrl"></portlet:resourceURL>
<fmt:setBundle basename="imagePortletResources" />

<portlet:resourceURL var="downloadImageFile" id="downloadImageFile"></portlet:resourceURL>
<input type="hidden" id="downloadFilePathUrl" name="downloadFilePathUrl" value="${downloadImageFile}" />

<input type="hidden" id="ajaxaction" name="ajaxaction" value=""></input>
<input type="hidden" id="selectedOrinVPI" name="selectedOrinVPI" value=""></input>

<input type="hidden" id="selectedVendorColorCode" name="selectedVendorColorCode" value=""></input>

<input type="hidden" id="removeImageUrl" name="removeImageUrl" value=""></input>
<input type="hidden" id="submitOrRejectAjaxUrlId" name="submitOrRejectAjaxUrlId" value="${submitOrRejectAjaxUrl}" />
<input type="hidden" id="uploadStatHidden" name="uploadStatHidden" value=""></input>
<input type="hidden" id="shothidden" name="shothidden" value="" />
<input type="hidden" id="removeFlagOff" name="removeFlagOff" value="" />
<input type="hidden" id="saveImageShotTypeHiddenID" name="saveImageShotTypeHiddenID" value="" />

<input type="hidden" id="onSubmitId" name="onSubmitId" value="" />
<input type="hidden" id="OnRemovalImageId" name="OnRemovalImageId" value="" />

<!-- generaic dialog for image reject success or failure etc. -->
<!-- generaic dialog for image reject success or failure etc. -->
<div id="img-dialog-generic" style="display:none">
	<div class="ui-widget">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;" id="img-dialog-message-area">
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;" id="img-dialog-message-icon"></span>
			<strong id="img-dialog-generic-text">Image successfully processed.</strong></p>
		</div>
	</div>
</div>
<!-- Submit Render Starts -->
<div id="overlay_submitOrReject" class="web_dialog_overlay"></div>
<!-- Will be called only once for all conditions -->
<!-- Image id submit 100 success start -->
<div id="dialog_submitSuccess" class="web_dialog_imageUploadPopUp" style="height: 140px;">
	<div id="content">
		<div class="x-panel-header">
			Submit Image Status
		</div>
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<ul>
				<li>				
					<label style="margin-left:53px;height: 16px;">Image '<span id="imageIdSubmitSuccessSpan"></span> ' successfully submitted. </label>
				</li>				
			</br>
				<li>
					<input class="btn"   id="submitSuccessPopUpClose" type="button" onclick='$("#overlay_submitOrReject").hide();$("#dialog_submitSuccess").hide();' name="Close" value="Close" style="float: right;" />
				</li>
			</ul>

		</div>
	</div>
</div>
<!-- Image id submit success end-->

<!-- Submit Render Starts -->
<div id="overlay_rejectReason" class="web_dialog_overlay"></div>
<!-- Will be called only once for all conditions -->
<!-- Image id submit 100 success start -->
<div id="dialog_rejectReason" class="web_dialog_imageUploadPopUp" style="height: 140px;">
	<div id="content">
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			<br>
			<span id="selectReasonText">
				Select the reject reason from the list below.
			</span>
			<br>
			<br>
			<span id="selectReasonText">
				Reject Reason:
			</span>   
			<select class="styled-select.slate" id="allImageRejectReasons" name="allImageRejectReasons">
				<c:forEach items="${imageForm.allImageRejectReasons}" var="imageRejectReason" varStatus="status">
					<option value="${imageRejectReason.reasonCode}">${imageRejectReason.reasonCode}  ${imageRejectReason.rejectReason}</option>
				</c:forEach>
			</select>
			<ul>
				<br>
				<li class="buttons" style="float:right;">
					<input class="btn-new ui-button ui-corner-all ui-widget" id="rejectReasonPopupSubmit" type="button" name="Submit" value="Submit" style="float: left;" />
					<input class="btn-new ui-button ui-corner-all ui-widget" id="rejectReasonPopupClose" type="button"  name="Close" value="Close" style="float: left;" />
				</li>
			</ul>
		</div>
	</div>
</div>

<div id="overlay_petRejectReason" class="web_dialog_overlay"></div>
<div id="dialog_petRejectReason" class="web_dialog_imageUploadPopUp" style="height: 140px;">
	<div id="content">
		<div class="pet-reject-reason">
			<br>
			<ul id="reject-reasons">
				<li id="reject-reason">
				<!-- The information for the popup is filled through JS showPetRejectReason() -->
				</li>
			</ul>
				
			<input class="btn-new ui-button ui-corner-all ui-widget corner-btn" id="petRejectReasonPopupClose" type="button" name="Close" value="Close" />
		</div>
	</div>
</div>

<!-- Image id submit failed start -->
<div id="dialog_submitFailed" class="web_dialog_imageUploadPopUp" style="height: 140px;">
	<div id="content">
		<div class="x-panel-header">
			Submit Image Status
		</div>
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<ul>
				<li>				
					<label style="margin-left:53px;height: 16px;">Image '<span id="imageIdSubmitFailSpan"></span> ' not submitted. </label>
				</li>				
			</br>
				<li>
					<input class="btn"   id="submitFailedPopUpClose" type="button" onclick='$("#overlay_submitOrReject").hide();$("#dialog_submitFailed").hide();' name="Close" value="Close" style="float: right;" />
				</li>
			</ul>

		</div>
	</div>
</div>
<!-- Image id submit failed end-->
<!-- Image id reject success start -->
<div id="dialog_rejectSuccess" class="web_dialog_imageUploadPopUp" style="height: 140px;">
	<div id="content">
		<div class="x-panel-header">
			Reject Image Status
		</div>
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<ul>
				<li>				
					<label style="margin-left:53px;height: 16px;">Image '<span id="imageIdRejectSuccessSpan"></span> ' successfully rejected. </label>
				</li>				
			</br>
				<li>
					<input class="btn"   id="rejectSuccessPopUpClose" type="button" onclick='$("#overlay_submitOrReject").hide();$("#dialog_rejectSuccess").hide();' name="Close" value="Close" style="float: right;" />
				</li>
			</ul>

		</div>
	</div>
</div>
<!-- Image id reject success end-->
<!-- Image id reject failed start -->
<div id="dialog_rejectFailed" class="web_dialog_imageUploadPopUp" style="height: 140px;">
	<div id="content">
		<div class="x-panel-header">
			Reject Image Status
		</div>
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<ul>
				<li>				
					<label style="margin-left:53px;height: 16px;">Image '<span id="imageIdRejectFailedSpan"></span> ' not rejected. </label>
				</li>				
			</br>
				<li>
					<input class="btn"   id="rejectFailedPopUpClose" type="button" onclick='$("#overlay_submitOrReject").hide();$("#dialog_rejectFailed").hide();' name="Close" value="Close" style="float: right;" />
				</li>
			</ul>

		</div>
	</div>
</div>
<!-- Image id reject failed end-->
<!-- Image id Remove failed start -->
<div id="dialog_removeFailed" style="display:none;">
	<div id="content">
		<!-- <div class="x-panel-header">
			Remove Image Status
		</div> -->
		<div class="x-panel-body;border: 0px solid #99bbe8;">
			</br>
			</br>
			<div class="ui-widget">
				<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
					<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					<strong id= "removeFailLevelId">Image not removed</strong></p>
				</div>
			</div>
			<ul>
				<!-- <li style="width:100%;float:left;margin-top:10px;">				
					<label style="margin-left:53px;height: 16px;" id= "removeFailLevelId"></label>
				</li> -->								
			</br>
				<li class="buttons" style="float:right;">
					<input class="btn-new ui-button ui-corner-all ui-widget" type="button" onclick="jq('#dialog_removeFailed').dialog('close')" name="Close" value="Close" />
				</li>
			</ul>

		</div>
	</div>
</div>
<!-- Image id Remove failed end-->
<!-- Image Remove Confirmation Popup Starts-->
<div id="dialog_submitRemove" class="web_dialog_imageUploadPopUp">
	<div id="content">
		<!-- <div class="x-panel-header">
			Remove Image Confirmation
			
			
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
								<strong>Are you sure you would like to remove the selected images?</strong></p>
							</div>
						</div>
			<ul>
				<!--<li>				
					 <label style="margin-left:53px;height: 16px;"> </label>	
				</li>-->				
			</br> 
				<li class="buttons" style="float:right;">
					<input class="btn-new ui-button ui-corner-all ui-widget"   id="removeConfirmPopUpOkId" type="button" onclick="removeVPISampleImageRows(document.getElementById('selectedOrinVPI').value);dialogHideonOk();" name="Ok" value="Ok" />			
					
					<input class="btn-new ui-button ui-corner-all ui-widget"   id="removePopUpClose" type="button" onclick="jq('#dialog_submitRemove').dialog('close')" name="Cancel" value="Cancel" /> &nbsp;&nbsp;&nbsp;&nbsp;
				</li>				
			</ul>

		</div>
	</div>
</div>
<!-- Image Remove Confirmation Popup Ends-->
<!-- Image Remove Status Popup Start-->
<div id="dialog_submitRemoveSuccess" class="web_dialog_imageUploadPopUp" style="height: 140px;">
	<div id="content">
		
		<div class="ui-widget">
      <div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
         <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
         <strong>Image Removed Successfully.</strong></p>
      </div>
   </div>			
				
				<br>							
					<input class="btn"   id="removeSuccessPopClose" type="button" onclick="jq('#dialog_submitRemoveSuccess').dialog('close');" name="Ok" value="Ok" style="float: right;" />					
				
				
				
			</ul>

		</div>
	</div>
</div>
<!-- Image Remove Status Popup Ends-->
<div id="dialog_removeImage" class="web_dialog_imageUploadPopUp">
   <div class="ui-widget">
      <div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
         <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
         <strong>Please select atlest one Image.</strong></p>
      </div>
   </div>
   <br>
   <input class="btn-new ui-button ui-corner-all ui-widget"   id="removeImgClose" type="button" onclick="jq('#dialog_removeImage').dialog('close')" name="Close" value="Close" />
</div>
<form:form commandName="workflowForm" method="post" action="${formAction}" name="workListDisplayForm" id="workListDisplayForm">
      
		<div  class="cars_panel x-hidden" >
		<div class="x-panel-header">
		<fmt:message key="label.imageManagement.header"/>				
		</div> 			
				<div class="x-panel-body" id="petTable_div" name="petTable">			
				<div id="scrollbarset" class="scrollbarset" >
				<table  cellpadding="0"  class="table tree2 table-bordered table-striped table-condensed" id="petTable" name="petTable"  >
				<thead >
				<tr>
					<th></th>				
					<th><fmt:message key="label.imageManagement.orionGrouping"/></th>
					<th><fmt:message key="label.imageManagement.styleNumber"/></th>
					<th><fmt:message key="label.imageManagement.color"/></th>																
					<th><fmt:message key="label.imageManagement.imageStatus"/></th>
					<th><fmt:message key="label.imageManagement.completionDate"/></th>
					<th>Pre-cutover CARS Flag</th>
				</tr>
				</thead>
				<tbody >
				<!-- Table Grid logic Start -->
						<c:set var="subcount" value="260" />
						<c:set var="countList" value="0" />						
												
						<c:if test="${workflowForm.petNotFound ne null}"><tr><td colspan="9"><c:out value="${workflowForm.petNotFound}"/></td></tr> </c:if>
						<c:forEach items="${workflowForm.workFlowlist}" var="workFlow"
							varStatus="status">
							<portlet:resourceURL var="getUploadVPIDetails" id ="getUploadVPIDetails">							
							</portlet:resourceURL>
							<portlet:resourceURL var="removeSampleImageUrl" id ="removeSampleImageUrl">								
							</portlet:resourceURL>							
							<script>
							document.getElementById("ajaxaction").value='${getUploadVPIDetails}';
							document.getElementById("removeImageUrl").value='${removeSampleImageUrl}';	
							</script>							
								<tr name="tablereport" class="treegrid-${countList}"  onclick="setUploadVPILink('${getUploadVPIDetails}','<c:out value="${workFlow.orinNumber}" />','${removeSampleImageUrl}')" id='petTableRowId'>
								<td align="center"></td>
								<td><c:out value="${workFlow.orinNumber}" />						
								</td>								
								<td><c:out value="${workFlow.vendorStyle}" />
								</td>								
								<td><c:out value="${workFlow.vendorColorCode}" />
								<c:out value="${workFlow.colorDes}" />
								</td>							
								<td>
								<c:out value="${workFlow.imageStatus}" />
								</td>
								<td><c:out value="${workFlow.completionDate}" />
								</td>
								<td></td>
							</tr>
						
						<c:forEach items="${workFlow.styleColor}" var="style"
								varStatus="Stylestatus">
								<c:set var="subcount" value="${subcount + Stylestatus.count }" />
									<portlet:resourceURL var="getUploadVPIDetails" id ="getUploadVPIDetails">							
							        </portlet:resourceURL>
							        <portlet:resourceURL var="removeSampleImageUrl" id ="removeSampleImageUrl">								
									</portlet:resourceURL>
									<portlet:resourceURL var="scene7ImageURL" id ="scene7ImageURL">								
									</portlet:resourceURL>
								<tr name="treegrid2"
									class="treegrid-${subcount} treegrid-parent-${countList}"  onclick="populateColorCode('${style.vendorColorCode}');setUploadVPILink('${getUploadVPIDetails}','<c:out value="${style.orinNumber}" />','${removeSampleImageUrl}');checkApproveImageStatus('${style.orinNumber}','${style.imageStatus}');getScene7Url('${style.orinNumber}', '${scene7ImageURL}');disableAllButtons();">

									<td><input type="hidden" name="supplierId" id="supplierId" value="<c:out value="${style.supplierID}" />"/></td>
									<td><a href="javascript:;" onclick=" populateColorCode('${style.vendorColorCode}');setUploadVPILink('${getUploadVPIDetails}','<c:out value="${style.orinNumber}" />','${removeSampleImageUrl}');checkApproveImageStatus('${style.orinNumber}','${style.imageStatus}');getScene7Url('${style.orinNumber}', '${scene7ImageURL}');disableAllButtons();"><c:out value="${style.orinNumber}" /> </a>
									<input type="hidden" name="orinHiddenId" id="${style.orinNumber}" value="<c:out value="${style.orinNumber}" />" />
									</td>
									<input type="hidden" name="vendorStyle" id="vendorStyle" value="<c:out value="${style.vendorStyle}" />" />
									<input type="hidden" name="vendorColorCode" id="vendorColorCode" value="<c:out value="${style.vendorColorCode}" />" />
									
									<td><c:out value="${style.vendorStyle}" />
									</td>
									
								    <td><c:out value="${style.vendorColorCode}" />
								     <c:out value="${style.vendorColorDesc}" />
								   									
									<td id="${style.orinNumber}_statusId">
									<c:out value="${style.imageStatus}" />
									</td>
									<td><c:out value="${style.completionDate}" /> 

									</td>
									<td id="${style.orinNumber}_carsFlag"><c:out value="${style.returnCarsFlag}" /></td>
								</tr>			
							</c:forEach>
							<c:set var="countList" value="${countList+1}" />
						</c:forEach>      
				<!--  Table Grid Login End -->	       
				</tbody>
				</table>
				<script>
				
				var rowcount=document.getElementById('petTable').getElementsByTagName('tbody')[0].getElementsByTagName('tr').length;
				
				if(rowcount>10){
					document.getElementById('scrollbarset').className='scrollbarsetScroll';
				}
				
				
				
				</script>
				</div>			
			</div><div id="tableEnd"></div>			
		</div>		
	</form:form>
