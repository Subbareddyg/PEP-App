/**
 * 
 */

function repalcePetTable(responseText){
var startindex= responseText.indexOf("tableStart");
var startend= responseText.indexOf("tableEnd");
var tablecontent1 = responseText.substring(startindex+18,startend-9);
return tablecontent1;
}


$(document).ready(function() {
	$('.tree').treegrid();
	$('.tree2').treegrid({
		expanderExpandedClass: 'icon-minus-sign',
		expanderCollapsedClass: 'icon-plus-sign'
	});


$("#deptNo").keyup(function (event) {
    if (event.which== 13) {
        getpetdetails();
     }else{
     return;
     }
   });

$('#selectAllRow').click(function(event) {  //on click 
    if(this.checked) { // check select status
        $('.checkbox1').each(function() { //loop through each checkbox
            this.checked = true;  //select all checkboxes with class "checkbox1"               
        });
    }else{
        $('.checkbox1').each(function() { //loop through each checkbox
            this.checked = false; //deselect all checkboxes with class "checkbox1"                       
        });         
    }
});


});



function search() {
     refreshParent  = window.open("/wps/myportal/home/belksearch", "_blank", "toolbar=yes, scrollbars=no, resizable=yes, top=260, left=680, width=400, height=550");
	 refreshParent.focus();
	//window.location = "/wps/myportal/Home/WorkList";
}
function createmannualpet() {
	window.location = "/wps/myportal/home/createmannualpet";
}
function createGrouping() {
	window.location = "/wps/myportal/home/creategrouping";
}
function inactivepets() {
	window.location = "/wps/myportal/home/inactivepets";
}
function activatepets() {
	window.location = "/wps/myportal/home/activatepets";
}


function contentstatus(){

window.location = "/wps/myportal/home/contentstatus";
}
function imagestatus(){

window.location = "/wps/myportal/home/imagestatus";
}

function columnsorting(selectedColumn)
{
                 var url = $("#ajaxaction").val();
                 $.get(url,{selectedColumnName:selectedColumn},function(responseText) { 
                        responseText = repalcePetTable(responseText);
                        $("#petTable").html(responseText);         
						$('.tree').treegrid();
							$('.tree2').treegrid({
								expanderExpandedClass: 'icon-minus-sign',
								expanderCollapsedClass: 'icon-plus-sign'
							});
							
							$('#selectAllRow').click(function(event) {  //on click 
							    if(this.checked) { // check select status
							        $('.checkbox1').each(function() { //loop through each checkbox
							            this.checked = true;  //select all checkboxes with class "checkbox1"               
							        });
							    }else{
							        $('.checkbox1').each(function() { //loop through each checkbox
							            this.checked = false; //deselect all checkboxes with class "checkbox1"                       
							        });         
							    }
							});
							
                    });
}



function getpetdetails(){
	var depLength = document.getElementById("deptNo").value.trim().length;
	if( depLength > 0){
		$("#workListDisplayForm").submit();
	} else{
		alert("Please select at least one department.");
	}
}

function getThePageContent(pageNumber){
                    var url = $("#ajaxaction").val();
                 $.get(url,{pageNo:pageNumber},function(responseText) { 
                        responseText = repalcePetTable(responseText);
                        $("#petTable").html(responseText);         
						$('.tree').treegrid();
						$('.tree2').treegrid({
							expanderExpandedClass: 'icon-minus-sign',
							expanderCollapsedClass: 'icon-plus-sign'
						});
						$('#selectAllRow').click(function(event) {  //on click 
						    if(this.checked) { // check select status
						        $('.checkbox1').each(function() { //loop through each checkbox
						            this.checked = true;  //select all checkboxes with class "checkbox1"               
						        });
						    }else{
						        $('.checkbox1').each(function() { //loop through each checkbox
						            this.checked = false; //deselect all checkboxes with class "checkbox1"                       
						        });         
						    }
						});
                    });
                
}

function depSearch(depOperation) {
		if(depOperation == 'depSaveClose'){
				 var depts = [];
				 $.each($("input[name='chkSelectedDept']:checked"), function(){            
				               depts.push($(this).val());
				           });
				if(depts.length==0){
				alert("Please select at least one department.");
				
				return;
				}else{
				   $("#deptNo").val(depts.join(", ")); 
				   $("#deptNo").attr("disabled","disabled");
				   }
				   
				   //Submitting the request to save the content to the controller
				                    var url = $("#ajaxaction").val();
									var departs = $("#deptNo").val();
				                 $.get(url,{departmentOperation:depOperation,selectedDepartments:departs},function(responseText) { 
				                        responseText = repalcePetTable(responseText);
				                        $("#petTable").html(responseText);         
										
										$('.tree').treegrid();
										$('.tree2').treegrid({
											expanderExpandedClass: 'icon-minus-sign',
											expanderCollapsedClass: 'icon-plus-sign'
										});
										$('#selectAllRow').click(function(event) {  //on click 
										    if(this.checked) { // check select status
										        $('.checkbox1').each(function() { //loop through each checkbox
										            this.checked = true;  //select all checkboxes with class "checkbox1"               
										        });
										    }else{
										        $('.checkbox1').each(function() { //loop through each checkbox
										            this.checked = false; //deselect all checkboxes with class "checkbox1"                       
										        });         
										    }
										});
				                    });
		   	
			}else if(depOperation == 'depClose'){
					 var url = $("#ajaxaction").val();
				                 $.get(url,{departmentOperation:depOperation},function(responseText) { 
				                        responseText = repalcePetTable(responseText);
				                        $("#petTable").html(responseText);         
										$('.tree').treegrid();
										$('.tree2').treegrid({
											expanderExpandedClass: 'icon-minus-sign',
											expanderCollapsedClass: 'icon-plus-sign'
										});
										$("#dialog_Dept").css("display","none");	
										$('#selectAllRow').click(function(event) {  //on click 
										    if(this.checked) { // check select status
										        $('.checkbox1').each(function() { //loop through each checkbox
										            this.checked = true;  //select all checkboxes with class "checkbox1"               
										        });
										    }else{
										        $('.checkbox1').each(function() { //loop through each checkbox
										            this.checked = false; //deselect all checkboxes with class "checkbox1"                       
										        });         
										    }
										});
				                    });
					
			}else if(depOperation == 'depClear'){
				
				        $('.deptcheckbox').each(function() { //loop through each checkbox
				            this.checked = false; //deselect all checkboxes with class "checkbox1"                       
				        });         
				   
				
					
			}
			else if(depOperation == 'depSearch'){
				   //Submitting the request to save the content to the controller
				                    var url = $("#ajaxaction").val();
									var departsToSearch = $("#selectedDeptSearch").val().trim();
									if(departsToSearch.length==0){
									alert("Please enter department details to search.");
									return;
									}
				                 $.get(url,{departmentOperation:depOperation,departmentsToSearch:departsToSearch},function(responseText) { 
				                        responseText = repalcePetTable(responseText);
				                        $("#petTable").html(responseText);         
										$('.tree').treegrid();
					$('.tree2').treegrid({
						expanderExpandedClass: 'icon-minus-sign',
						expanderCollapsedClass: 'icon-plus-sign'
					});
					$('#selectAllRow').click(function(event) {  //on click 
					    if(this.checked) { // check select status
					        $('.checkbox1').each(function() { //loop through each checkbox
					            this.checked = true;  //select all checkboxes with class "checkbox1"               
					        });
					    }else{
					        $('.checkbox1').each(function() { //loop through each checkbox
					            this.checked = false; //deselect all checkboxes with class "checkbox1"                       
					        });         
					    }
					});
										
						$("#dialog_Dept").css("display","block");				
				  });
			
			}
}


function showDeptPopup() {
	$("#dialog_Dept").css("display","block");
}

function contentStatus(contentStatusValue){
	window.location = "/wps/myportal/home/contentstatus";
	
}

function imageStatus(contentStatusValue){
	window.location = "/wps/myportal/home/imagestatus";
	
}