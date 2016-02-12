
	function callWorkList(){
	window.location = "/wps/portal/home/worklistDisplay";
	}

	

  	function clearFields(){ 
     document.forms['workflowForm'].orinNumber.value = ""; 	
	 document.forms['workflowForm'].actionParameter.value = "ClearForm";
	 document.forms['workflowForm'].submit();	
 	 }
 	 function searchAction(){ 	 
 	 $("#overlay_pageLoading").show();
 	 document.forms['workflowForm'].actionParameter.value = "select";
     document.forms['workflowForm'].submit(); 
	
	
 	 } 
 	  function createAction(){ 
	  $("#overlay_pageLoading").show();
 	  document.forms['workflowForm'].actionParameter.value = "createPET";
      document.forms['workflowForm'].submit(); 	
 	 } 

	$(document).keypress(function (e) {
     if (e.which == 13) {
     var orinTOCreatePet = document.getElementById('orinNumber').value;
	if (typeof orinTOCreatePet !== "undefined" && orinTOCreatePet !== null && orinTOCreatePet !='') 
	  searchAction(); 
    	}
	});