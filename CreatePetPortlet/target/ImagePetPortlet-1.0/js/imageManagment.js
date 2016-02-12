
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