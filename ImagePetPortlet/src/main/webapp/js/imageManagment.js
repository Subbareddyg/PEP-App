
var sampleImageExist=false;
  var swatchImageExist=false;
function SelectImageLocation(selectedValue){
 if(selectedValue=='FTP'){
  document.getElementById("ftpDiv_1").style.display = 'block';
  document.getElementById("selectImage").style.display = 'none';  
  }else{
  document.getElementById("ftpDiv_1").style.display = 'none';
  document.getElementById("selectImage").style.display = 'block';
    
  }
 
 }
 


function removeSampleRow(table,rowCount)
{
if(confirm("Are you sure to delete?")){		
 table.deleteRow(rowCount);
 document.getElementById('btn_image_swatch').disabled=false;
 sampleImageExist=false;
 swatchImageExist=false;
}


}

function removeSwatchImageRow(table,rowCount)
{
if(confirm("Are you sure to delete?")){		
table.deleteRow(rowCount);
document.getElementById('btn_image_swatch').disabled=false;
document.getElementById('image_sample').disabled=false;
sampleImageExist=false;
swatchImageExist=false; 
}


}

function saveImageDetails(){
	
	alert(" selected value"+document.getElementById('mySelectBox').value);
}

	

function sampleImageButton(tableID) {
	if(sampleImageExist){
	  return;
	}else if(swatchImageExist){
	return;
	}
	document.getElementById('btn_image_swatch').disabled=true;
	var table = document.getElementById(tableID);
	var rowCount = table.rows.length;		
	var row = table.insertRow(rowCount);
	
	var cell1 = row.insertCell(0);
	cell1.id="imageId";
    cell1.name="imageId";	
	
	var cell2 = row.insertCell(1);
	cell2.id="imageName";
    cell2.name="imageName";
	
	var cell3 = row.insertCell(2);
	cell3.id="imageLocation";
    cell3.name="imageLocation";
    
	var cell4 = row.insertCell(3);			
	var selectBox = document.createElement("Select");
	selectBox.name="mySelectBox";
	selectBox.id="mySelectBox";
	selectBox.style.width= "33px";

	//Creating first Option 
	var option1 = document.createElement("OPTION");
	option1.selected="selected";
	option1.text="A";
	option1.value="A";
	selectBox.options.add(option1);
	cell4.appendChild(selectBox);

	/*var option2 =document.createElement("OPTION");
 
	//Creating Second Option
	option2.text="B"
	option2.value=2
	selectBox.options.add(option2)
	cell4.appendChild(selectBox);
	
	var option3 =document.createElement("OPTION") 
	//Creating Second Option
	option3.text="C"
	option3.value=2
	selectBox.options.add(option3)
	cell4.appendChild(selectBox);	
	
	var option4 =document.createElement("OPTION") 
	//Creating Second Option
	option4.text="B"
	option4.value=2
	selectBox.options.add(option4)
	cell4.appendChild(selectBox);	
	cell4.style.width = 'width:3%;';*/
  


	var cell5 = row.insertCell(4);
	cell5.id="linkStatus";
    cell5.name="linkStatus";
	cell5.innerHTML = 'Pending';
	
	var cell6 = row.insertCell(5);
	cell6.id="imageStatus";
    cell6.name="imageStatus";
	cell6.innerHTML = 'Pending';
	
	
	var cell7 = row.insertCell(6);
	cell7.id="sampleId";
    cell7.name="sampleId";
	
	
	var cell8 = row.insertCell(7);
	var element8 = document.createElement("input");
	element8.type = "checkbox";
	element8.name="sampleReceived";
	element8.id="sampleReceived";	
	cell8.appendChild(element8);
	
	var cell9 = row.insertCell(8);
	var element9 = document.createElement("input");
	element9.type = "checkbox";
	element9.name="silhouette";
	element9.id="silhouette";
	cell9.appendChild(element9);
	
	var cell10 = row.insertCell(9);	
	var element10 = document.createElement("input");
	element10.style.width= "110px";
	element10.type = "date";	
	element10.style.class="hasDatepicker";
	element10.id="datepicker11";			
	element10.name = "turnInDate";	 

	cell10.appendChild(element10);
	
	var cell11 = row.insertCell(10);
	//cell11.innerHTML = 'Approved';
	var element11 = document.createElement("input");
	element11.style.fontWeight = 'bold';
	element11.style.width= "59px";
	element11.type = "button";
	element11.name="sampleCordinatorNote";
	element11.id="sampleCordinatorNote";
	element11.value="Add Note";
	element11.onclick = function(){
	  SampleCoordinatorNotePopUp(false);
	//sampleSwatchNote();
	return false;
	};			
	cell11.appendChild(element11);
	
	
	
	var cell12 = row.insertCell(11);
	//cell12.innerHTML = 'Approved';
	var element12 = document.createElement("input");
	element12.disabled=true;
	element12.style.fontWeight = 'bold';
	element12.style.width= "59px";
	element12.type = "button";
	element12.name="Action";
	element12.value="Action";
	cell12.appendChild(element12);		
	var cell13 = row.insertCell(12);
	cell13.innerHTML = '<a href="javascript:removeSampleRow('+tableID+','+rowCount+')">Remove</a>';
	sampleImageExist=true;
			   
}




function swatchImageButton(tableID) {
	if(sampleImageExist){
	return;
	}else if(swatchImageExist){
		return;
	}
	document.getElementById('image_sample').disabled=true;
	var table = document.getElementById(tableID);
	var rowCount = table.rows.length;		
	var row = table.insertRow(rowCount);			
	var cell1 = row.insertCell(0);
	cell1.id="imageId";
    cell1.name="imageId";	
	
	var cell2 = row.insertCell(1);
	cell2.id="imageName";
    cell2.name="imageName";
	
	var cell3 = row.insertCell(2);
	cell3.id="imageLocation";
    cell3.name="imageLocation";
    
	var cell4 = row.insertCell(3);
	cell4.id = 'SW';
	cell4.name = 'SW';
	cell4.innerHTML = 'SW';
	
	var cell5 = row.insertCell(4);
	cell5.id="linkStatus";
    cell5.name="linkStatus";
	cell5.innerHTML = 'Pending';
	
	var cell6 = row.insertCell(5);
	cell6.id="imageStatus";
    cell6.name="imageStatus";
	cell6.innerHTML = 'Pending';
	
	
	var cell7 = row.insertCell(6);
	cell7.id="sampleId";
    cell7.name="sampleId";
	
	
	var cell8 = row.insertCell(7);
	var element8 = document.createElement("input");
	element8.disabled=true;
	element8.type = "checkbox";
	element8.name="sampleReceived";
	element8.id="sampleReceived";	
	cell8.appendChild(element8);
	
	var cell9 = row.insertCell(8);
	var element9 = document.createElement("input");
	element9.type = "checkbox";
	element9.name="silhouette";
	element9.id="silhouette";
	cell9.appendChild(element9);
	
	
	var cell10 = row.insertCell(9);
	//cell10.innerHTML = 'Approved';
	var element10 = document.createElement("input");
	element10.style.width= "105px";
	element10.type = "date";	
	element10.style.class="hasDatepicker";
	element10.id="datepicker12";			
	element10.name = "datepicker12";
	cell10.appendChild(element10);
	
	var cell11 = row.insertCell(10);
	//cell11.innerHTML = 'Approved';
	var element11 = document.createElement("input");
	element11.style.fontWeight = 'bold';
	element11.style.width= "59px";
	element11.type = "button";
	element11.name="btnImageADRequestAction1";
	element11.id="btnImageADRequestAction1";
	element11.value="Add Note";
	element11.onclick = function(){
	  SampleCoordinatorNotePopUp(false);
	//sampleSwatchNote();
	return false;
	};			
	cell11.appendChild(element11);
	
	var cell12 = row.insertCell(11);
	//cell12.innerHTML = 'Approved';
	var element12 = document.createElement("input");
	element12.style.fontWeight = 'bold';
	element12.disabled=true;
	element12.style.width= "59px";
	element12.type = "button";
	element12.name="Action";
	element12.value="Action";
	cell12.appendChild(element12);
		
	var cell13 = row.insertCell(12);	
	
	cell13.innerHTML = '<a href="javascript:removeSwatchImageRow('+tableID+','+rowCount+')">Remove</a>';
	
	
	swatchImageExist = true;
			
			   
}



function validateGrpImageFields(formId){



	var imageLocation = document.getElementById('imageLocationButton').selectedIndex;
		
	//logic to check null shot type ends
	var errorMessage = '';		

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
									

					$("#overlay_groupimageLoading").show();									
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

function confirmGRPImageRemovePopUp(imageId,imageName){

    
	document.getElementById('imgHiddenId').value = imageId;	
	document.getElementById('imgNameHiddenId').value = imageName;


	$("#dialog_submitRemove").show();	
}

function dialogHideonOk(){
	
	$("#dialog_submitRemove").hide();
}

function removeGroupingImage(imageId,imageName,removeImageUrl){  

var groupingId = $("#groupingId").val();

   $.ajax({
			type: 'POST',
			url : removeImageUrl,
			datatype:'json',			
			data: {groupingId:groupingId,imageIDToDel:imageId,imageNameToDel:imageName},			
			success: function(data){
			document.getElementById('btnGPImageUploadAction').disabled=false;


			var table = document.getElementById("groupImageTable");  
  			table.deleteRow(1); 
				var json = $.parseJSON(data);
				var responseCodeOnRemove = json.responseCodeOnRemove;
				
				
			},
			error:function (xhr, ajaxOptions, thrownError){            	
            	var error = $.parseJSON(xhr.responseText);               
             }

		});	
	
}

function dialogGRPApproveHideonOk(){
	
	$("#dialog_submitApprove").hide();
	
	
}

function confirmGroupingImageApprove(url){

	$("#overlay_Upload").hide();	
	$("#dialog_submitApprove").show();
		
}

var groupOverallStatus = "";

function groupingImageApproveAction(url){
var imageId = $("#hidImageId").val().trim();
var groupingId = $("#groupingId").val();	
$("#overlay_groupimageLoading").show();	
var selectedRadioButton = $('input[name=radiobutton]');
if (typeof selectedRadioButton.filter(':checked').val()  !== "undefined" && selectedRadioButton.filter(':checked').val()){
   groupOverallStatus = selectedRadioButton.filter(':checked').val();	
}


var groupingType = $("#groupingType").val();	

		$.ajax({				
				type: 'POST',
				url: url,
				data: { groupingId:groupingId,groupOverallStatus:groupOverallStatus,groupingType:groupingType,imageId:imageId },
				success: function(data){					
					var json = $.parseJSON(data);
					var responseCode = json.responseCode;								
					if(responseCode == 'SUCCESS'){	
 						if (imageId !== '' && imageId !== null ){
						document.getElementById("imageStatus").innerHTML = 'Completed';						
						var table = document.getElementById("groupImageTable");							
						var Cells = table.getElementsByTagName("td");
						Cells[4].innerHTML = "Remove";	
						}
									
						document.getElementById('image_approve').disabled = true ;
						$("#overlay_groupimageLoading").hide();
						document.getElementById('btnGPImageUploadAction').disabled = true ;
					
				     }else{
                                        alert('Image not approved.');
				     }						
					
				},
				cache: false,                
				error: function(jqXHR,textStatus, errorThrown){
					
				},
				complete: function(){
					$("#overlay_imageLoading").hide();
				}
			});
		
/* setTimeout(function(){setUploadVPILink($("#ajaxaction").val(),document.getElementById("selectedColorOrinNum").value,$("#removeImageUrl").val());trClick();scrollToView('vImage','vImage');},2000); */			
}

function redirectSessionTimedOutGrpImage(){

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

function showGrpImageActionMessage(eventType, imageId){

	imageId = (imageId === undefined || imageId == '') ? '' : imageId;
	switch (eventType){
		case 'uploadSuccess':
			var dlgContent = buildGrpMessage('Image ' + imageId + ' successfully uploaded.', 'success');
			$('#image-Upload-Message-Area').html(dlgContent).fadeIn('fast');
			//cleanupMessage($('#image-Upload-Message-Area'));
			break;
		case 'uploadError':
			var dlgContent = buildGrpMessage('Image ' + imageId + ' not uploaded.', 'error');
			$('#image-Upload-Message-Area').html(dlgContent).fadeIn('fast');
			//cleanupMessage($('#image-Upload-Message-Area'));
			break;
		
	}
}
function buildGrpMessage(msg, dlgType){
	var highlightClass = dlgType == 'error' ? 'ui-state-error ui-custom-message-styling-error' : 'ui-state-highlight ui-custom-message-styling-info';
	
	return '<div class="ui-widget">'
		+ '<div class="ui-corner-all ' +  highlightClass + '">'
		+ 	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>'
		+		'<strong>' + msg + '</strong>'
		+ 	'</p>'
		+ '</div>'
		+ '</div>';
}

function openGRPImage(url){


	var win = window.open(url, '', "toolbar=no,resizable=no,width=640,height=480,scrollbars=yes");
}

function openGRPScen7Image(url){


	var win = window.open(url, '', "toolbar=no,resizable=no,width=400,height=580,scrollbars=yes");
}



function onloadVPILinks(groupVPILinks){
	
var table = document.getElementById("groupImageTable");
var rowCount = table.rows.length;		
var row = table.insertRow(rowCount);
var json = JSON.parse(groupVPILinks);
var imageUrl = document.getElementById('downloadFilePathUrl').value+"?filePath="+json["imagefilepath"]+"&imageName="+json["imageName"];

     var cell1 = row.insertCell(0);
     cell1.id="hiddenImageId";
    cell1.name="hiddenImageId";
    cell1.innerHTML = json["imageID"];
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    cell2.innerHTML = json["originalImageName"];   
    cell3.innerHTML = "<a href=\"javascript:openGRPImage(\'"+imageUrl+"\')\"> "+json["imageName"]+"</a>";
    cell4.id="imageStatus";
    cell4.name="imageStatus";
   if (typeof json["imageStatus"]  !== "undefined" && json["imageStatus"]){
   cell4.innerHTML = json["imageStatus"];	
  }else{ 
   cell4.innerHTML = "Initiated";
  }
   cell5.id="removeGPImage" ;
   cell5.name="removeGPImage" ;   
if(json["imageStatus"]  == 'Completed'){
 cell5.id="removeGPImage" ;

		cell5.innerHTML = 'Remove';
	}else{
		cell5.innerHTML = '<a href="javascript:;" id="test" onclick="confirmGRPImageRemovePopUp('+json["imageID"]+',\''+json["imageName"]+'\')">Remove</a>';
	}

return;


}


