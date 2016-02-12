
	function callWorkList(){
	window.location = "/wps/myportal/Home/WorkList";
	}

	

  	function clearFields(){ 
    	document.forms['workflowForm'].orinNumber.value = ""; 
	
 	 }
 	 function searchAction(){ 

 	 document.forms['workflowForm'].actionParameter.value = "select";


    	document.forms['workflowForm'].submit(); 
	
 	 } 
 	  function createAction(){ 
 	 	document.forms['workflowForm'].actionParameter.value = "createPET";
    	document.forms['workflowForm'].submit(); 
	
 	 } 
