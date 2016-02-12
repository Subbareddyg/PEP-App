 	 

//saveContent content is called on click of the Save button to update the attributes 
function saveContent()
	{ 
		 
		 var prod = document.getElementById("txt_outfitName").value;
		 var desc = document.getElementById("vendorStyle.Descr:132").value;
		 if(prod == null || prod.trim() == ''){
			 alert("Please enter Product Name. ");
			 return;
		 }
		 if(desc == null || desc.trim() == ''){
			 alert("Please enter Product Description. ");
			 return;
		 }
	 	document.forms['contentDisplayForm'].actionParameter.value = "saveContent";	 		
	    document.forms['contentDisplayForm'].submit(); 
		
	} 


//update content status  is called on click of the Submit  button at the Style Color Data Level 
	function updateContentStatusForStyleColorData()
	{ 
		    
		 	document.forms['contentDisplayForm'].updateContentStatusStyleColorActionParameter.value = "updateContentStatusForStyleColor";			
		    document.forms['contentDisplayForm'].submit(); 
	
	} 
	
	 

//log out to the Belk User Login page or External Vendor Login Page depending upon the username 
function logout_home(){	
		
		var username = document.getElementById("username").value;
		
		
		
		if(username.indexOf('@') === -1) 
		{
		   window.location = "/wps/portal/home/InternalLogin";
		} else {
			
			window.location = "/wps/portal/home/ExternalVendorLogin";
		}
		
	}



//Set the selected sku orin number on click of the sku orin number hyper link
function setSelectedSkuOrinNumber(skuOrinNumber){	
		document.getElementById("selectedSkuOrinNumber").value =skuOrinNumber;	
		$("#contentDisplayForm").submit();		
		
	}