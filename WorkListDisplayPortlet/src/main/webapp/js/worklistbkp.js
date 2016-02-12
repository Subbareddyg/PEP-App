/**
 * 
 */

function repalcePetTable(responseText){
var startindex= responseText.indexOf("tableStart");
var startend= responseText.indexOf("tableEnd");
var tablecontent1 = responseText.substring(startindex+18,startend-9);
return tablecontent1;
}


function repalceDeptTable(responseText){
	var startindex= responseText.indexOf("tableDeptStart");
	var startend= responseText.indexOf("tableDeptEnd");
	var tablecontent1 = responseText.substring(startindex+22,startend-9);
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



function createmannualpet() {
	window.location = "/wps/portal/home/createmannualpet";
}
function createGrouping() {
	window.location = "/wps/portal/home/creategrouping";
}
function inactivepets() {
	window.location = "/wps/portal/home/inactivepets";
}
function activatepets() {
	window.location = "/wps/portal/home/activatepets";
}


function contentstatus(){

window.location = "/wps/portal/home/contentstatus";
}

function imagestatus(){

window.location = "/wps/portal/home/imagestatus";
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

function onloaddept() {
	
	var depts = []; 
	
	$.each($("input[name='chkSelectedDept']:checked"), function(){            
        depts.push($(this).val());
    });
	
	if(depts.length >0){
		
		   $("#deptNo").val(depts.join(",")); 
		   //alert('--------------' + $("#deptNo").val(depts.join(",")));
		   $("#deptNo").attr("disabled","disabled");
   }
	
}


function depSearch(depOperation) {
		if(depOperation == 'depSaveClose'){
			if('yes'==$("#AdseachClicked").val()){
				 var addepts = [];
				 $.each($("input[name='chkSelectedDept']:checked"), function(){            
					 			addepts.push($(this).val());
				           });
				  $("#adDeptNo").val(addepts.join(","));
				  //Getting all the other fields to retain the values
				  
				  var advSelectedDepartments="";
					if( $("#adDeptNo").val().trim().length>0 ){
						advSelectedDepartments = $("#adDeptNo").val().trim();
					}
					//alert(advSelectedDepartments);
					var completionDateFrom="";
					if($("#datepicker1").val().trim().length>0){
						completionDateFrom =$("#datepicker1").val().trim();
					}
					//alert(completionDateFrom);
					var completionDateTo="";
					if($("#datepicker2").val().trim().length>0){
						completionDateTo =$("#datepicker2").val().trim();
					}
					//alert(completionDateTo);
					var imageStatus="";
					if($("#advImageStatus").val().trim().length>0){
						imageStatus =$("#advImageStatus").val().trim();
					}
					//alert(imageStatus);
					var contentStatus="";
					if($("#advImageStatus").val().trim().length>0){
						contentStatus =$("#advContentStatus").val().trim();
					}
					//alert(contentStatus);
					/*var petStatus="";
					if($("#petInActive").prop("checked")){
						petStatus ="no";
					}
					if($("#petActive").prop("checked")){
						petStatus ="yes";
					}*/
					var petStatus = '01';
					var temp = $('input:radio[name=petActive]:checked').val();
					//alert(petStatus);
					if(temp == 'yes'){
						petStatus = '01';
					}
					temp = $('input:radio[name=petInActive]:checked').val();
					if(temp == 'yes'){
						petStatus = '05';
					}
					temp = $('input:radio[name=petClosed]:checked').val();
					if(temp == 'yes'){
						petStatus = '06';
					}
					//alert(petStatus);
					var requestType="";
					if($("#advRequestType").val().trim().length>0){
						requestType =$("#advRequestType").val().trim();
					}
					//alert(requestType);
					var orinNumber="";
					if($("#advOrin").val().trim().length>0){
						orinNumber =$("#advOrin").val().trim();
					}
					//alert(orinNumber);
					var vendorStyle="";
					if($("#advVendorStyle").val().trim().length>0){
						vendorStyle =$("#advVendorStyle").val().trim();
					}
					//alert(vendorStyle);
					var upc="";
					if($("#advUpc").val().trim().length>0){
						upc =$("#advUpc").val().trim();
					}
					//alert(upc);
					var classNumber="";
						if($("#advClassNumber").val().trim().length>0){
						classNumber =$("#advClassNumber").val().trim();
					}
					//alert(classNumber);
					var createdToday="";
					var finalTodayDate = "";
					createdToday = $('input:radio[name=rdCretedToday]:checked').val();
					if(!("yes"== createdToday || "no" == createdToday)){
						createdToday ="";					
					}
					//186 Start
					if("yes"== createdToday && !("no"==  createdToday)){
						var todayDate = new Date();
						var mm = todayDate.getMonth()+1;
						var dd= todayDate.getDate();
						var yyyy= todayDate.getFullYear();
						if(dd<10){
							dd='0'+dd;
							}
							if(mm<10){
							mm='0'+mm;
							}
							finalTodayDate = mm+"-"+dd+"-"+yyyy;
						}// End Defect #186
					
					var vendorNumber="";
					if($("#advVenNumber").val().trim().length>0){
						vendorNumber =$("#advVenNumber").val().trim();
					}
				  
				  //getting the Class details
				  			  
				  var url = $("#ajaxaction").val();
				//	var departstogetClass = $("#adDeptNo").val();
               $.get(url,{advSearchOperation:'advsaveandclose',advSelectedDepartments:advSelectedDepartments,
            	   	 completionDateFrom:completionDateFrom,
	                 completionDateTo:completionDateTo,
	                 imageStatus:imageStatus,
	                 contentStatus:contentStatus,
	                 petStatus:petStatus,
	                 requestType:requestType,
	                 orinNumber:orinNumber,
	                 vendorStyle:vendorStyle,
	                 upc:upc,
	                 classNumber:classNumber,
	                 createdToday:createdToday,
	                 finalTodayDate:finalTodayDate,
	                 vendorNumber:vendorNumber
               },function(responseText) { 
                      responseText = repalceAdvPetTable(responseText);
                      $("#advanceSearchDiv").html(responseText);         
						$("#dialog_ASearch").css("display","block");
						defaultAdvSearchSettings(); 
                  });
				  
				  
				  $("#adDeptNo").attr("disabled","disabled");
				  $("#overlay_Dept").css("display","none");
				  $("#dialog_Dept").css("display","none");
			}else{
			
				 var depts = [];
				 $.each($("input[name='chkSelectedDept']:checked"), function(){            
				               depts.push($(this).val());
				           });
				if(depts.length==0){
				alert("Please select at least one department.");
				
				return;
				}else{
				   $("#deptNo").val(depts.join(",")); 
				   $("#deptNo").attr("disabled","disabled");
				   }
				   
				   //Submitting the request to save the content to the controller
				                    var url = $("#ajaxaction").val();
									var departs = $("#deptNo").val();
									var done="no";
								
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
						}
			}else if(depOperation == 'depClose'){
				// alert("This is from dep close"+$("#AdseachClicked").val());
				if('yes'==$("#AdseachClicked").val()){
					$("#overlay_Dept").css("display","none");
					$("#dialog_Dept").css("display","none");
				}else{
				// alert("normal operation");
					 var url = $("#ajaxaction").val();
				                 $.get(url,{departmentOperation:depOperation},function(responseText) { 
				                        responseText = repalceDeptTable(responseText);
				                        $("#deptTable").html(responseText);         
										
										$("#overlay_Dept").css("display","none");
										$("#dialog_Dept").css("display","none");
										
				                    });
				}
			}else if(depOperation == 'depClear'){
				
				        $('.deptcheckbox').each(function() { //loop through each checkbox
				            this.checked = false; //deselect all checkboxes with class "checkbox1"                       
				        });         
				   
				
					
			}
			else if(depOperation == 'depSearch'){
				   //Submitting the request to save the content to the controller
									var adsrch=$("#AdseachClicked").val();
									
				                    var url = $("#ajaxaction").val();
									var departsToSearch = $("#selectedDeptSearch").val().trim();
									if(departsToSearch.length==0){
									alert("Please enter department details to search.");
									return;
									}
									
									var departs = $("#deptNo").val();
				                 $.get(url,{departmentOperation:depOperation,departmentsToSearch:departsToSearch,AdseachClicked:adsrch,selectedDepartments:departs},function(responseText) { 
				                        responseText = repalceDeptTable(responseText);
				                        $("#deptTable").html(responseText);         
										
					$("#overlay_Dept").css("display","block");				
						$("#dialog_Dept").css("display","block");	
						
						if('yes'==$("#AdseachClicked").val()){
							$("#dialog_ASearch").css("display","block");
						}
						
				  });
			
			}
}


function showDeptPopup() {
	$("#overlay_Dept").css("display","block");
	$("#dialog_Dept").css("display","block");
	$("#AdseachClicked").val("no");
}

function contentStatus(contentStatusValue){
	window.location = "/wps/portal/home/contentstatus";
	
}

function imageStatus(contentStatusValue){
	window.location = "/wps/portal/home/imagestatus";
	
}
//Advance Search
function searchClose()
{
   $("#dialog_ASearch").css("display","none");
   $("#overlay_Image_advSearch").css("display","none");
}


function searchClear()
{
  $("#adDeptNo").val(""); 
  $("#datepicker1").val("");
  $("#datepicker2").val("");
  $("#advImageStatus").val("");
  $("#advContentStatus").val("");
//  $("#petActive").attr('checked',false);
//  $("#petInActive").attr('checked',false);
  $('input[name=petActive]').attr('checked',false);
  $('input[name=petInActive]').attr('checked',false);
  $('input[name=petClosed]').attr('checked',false);
  
  $("#advRequestType").val("");
  $("#advOrin").val("");
  $("#advVendorStyle").val("");
  $("#advUpc").val("");
  $("#advClassNumber").val("");
  $('input[name=rdCretedToday]').attr('checked',false);
  $("#advVenNumber").val("");
}


function searchReset()
{
  
  var url = $("#ajaxaction").val();
				                 $.get(url,{advSearchOperation:'searchReset'},function(responseText) { 
				                        responseText = repalceAdvPetTable(responseText);
				                        $("#advanceSearchDiv").html(responseText);   
										
										$("#overlay_Dept").css("display","none");
										$("#dialog_Dept").css("display","none");
										
										$("#dialog_ASearch").css("display","block");
										resetAdvSearchSettings();   
				                    });
				                 
				                    
 
}



function searchSearch()
{	
	$("#dialog_ASearch").css("display","none");
	$("#overlay_pageLoading").show();
	
	
	$("#deptNo").val("");
	//alert('---------------' +$("#deptNo").val("")); 
	var advSelectedDepartments="";
	if( $("#adDeptNo").val().trim().length>0 ){
		advSelectedDepartments = $("#adDeptNo").val().trim();
	}
	//alert(advSelectedDepartments);
	var completionDateFrom="";
	if($("#datepicker1").val().trim().length>0){
		completionDateFrom =$("#datepicker1").val().trim();
		if(!checkdate(completionDateFrom)){
		alert('Enter valid From Date.');
		$("#datepicker1").focus();
		$("#datepicker1").val("");
			return false;
		}
	}
	//alert(completionDateFrom);
	var completionDateTo="";
	if($("#datepicker2").val().trim().length>0){
		completionDateTo =$("#datepicker2").val().trim();
		
		if(!checkdate(completionDateTo)){
		alert('Enter valid To Date.');
 			$("#datepicker2").focus();
 			$("#datepicker2").val("");		
			return false;
		}
	}
	//Checking the From and To date combinations
	if(completionDateFrom.length>0){
		if(completionDateTo.length==0){
			alert("Please enter the To Date since From Date is entered.");
			$("#datepicker2").val("");
			$("#datepicker2").focus();
			return false;
		}
	}
	if(completionDateTo.length>0){
		if(completionDateFrom.length==0){
			alert("Please enter the From Date since To Date is entered.");
			$("#datepicker1").val("");
			$("#datepicker1").focus();
			return false;
		}
	}
	//Cheking the From to date Greater or not
	if(completionDateFrom.length>0 && completionDateTo.length>0){
		var fromCDate = new Date(completionDateFrom);
		var toCDate = new Date(completionDateTo);
		if(fromCDate > toCDate){
		alert("From Date is greater than To Date. Please change the date.");
		return false;
		}
	}
	
	var imageStatus="";
	if($("#advImageStatus").val().trim().length>0){
		imageStatus =$("#advImageStatus").val().trim();
	}
	//alert(imageStatus);
	var contentStatus="";
	if($("#advContentStatus").val().trim().length>0){
		contentStatus =$("#advContentStatus").val().trim();
	}
	//alert(contentStatus);
	/*var petStatus="";
	if($("#petInActive").prop("checked")){
		petStatus ="no";
	}
	if($("#petActive").prop("checked")){
		petStatus ="yes";
	}*/
	
	var petStatus = '01';
	var temp = $('input:radio[name=petActive]:checked').val();
	//alert(petStatus);
	if(temp == 'yes'){
		petStatus = '01';
	}
	temp = $('input:radio[name=petInActive]:checked').val();
	if(temp == 'yes'){
		petStatus = '05';
	}
	temp = $('input:radio[name=petClosed]:checked').val();
	if(temp == 'yes'){
		petStatus = '06';
	}
 
 
	var requestType="";
	if($("#advRequestType").val().trim().length>0){
		requestType =$("#advRequestType").val().trim();
	}
	//alert(requestType);
	var orinNumber="";
	if($("#advOrin").val().trim().length>0){
		orinNumber =$("#advOrin").val().trim();
	}
	//alert(orinNumber);
	var vendorStyle="";
	if($("#advVendorStyle").val().trim().length>0){
		vendorStyle =$("#advVendorStyle").val().trim();
		// Defect 132
		//return isMultiVendorStyle(vendorStyle);
		if(vendorStyle.length > 0){
		    if( /[,]/.test( vendorStyle ) ) {
		       alert('Vendor style number is not valid - please re-enter.');
		       $("#advVendorStyle").val("");
			    $("#advVendorStyle").focus();
			    return false;
		    }
        }
	}
	//alert(vendorStyle);
	var upc="";
	if($("#advUpc").val().trim().length>0){
		upc =$("#advUpc").val().trim();
	}
	//alert(upc);
	var classNumber="";
		if($("#advClassNumber").val().trim().length>0){
		classNumber =$("#advClassNumber").val().trim();
	}
	//alert(classNumber);
	var createdToday="";
	//Fix for Defect #186 Start
	var finalTodayDate = "";
	createdToday = $('input:radio[name=rdCretedToday]:checked').val();
	if(!("yes"== createdToday || "no" == createdToday)){
		createdToday ="";
	}
	//186 Start
	if("yes"== createdToday && !("no"==  createdToday)){
		var todayDate = new Date();
		var mm = todayDate.getMonth()+1;
		var dd= todayDate.getDate();
		var yyyy= todayDate.getFullYear();
		if(dd<10){
			dd='0'+dd;
			}
			if(mm<10){
			mm='0'+mm;
			}
			finalTodayDate = mm+"-"+dd+"-"+yyyy;
		}// End Defect #186
	
	  var vendorNumber="";
		if($("#advVenNumber").val().trim().length>0){
			vendorNumber =$("#advVenNumber").val().trim();
		}
		
   var url = $("#ajaxaction").val();
				                 $.get(url,{advSearchOperation:'searchSearch',
				                 advSelectedDepartments:advSelectedDepartments,
				                 completionDateFrom:completionDateFrom,
				                 completionDateTo:completionDateTo,
				                 imageStatus:imageStatus,
				                 contentStatus:contentStatus,
				                 petStatus:petStatus,
				                 requestType:requestType,
				                 orinNumber:orinNumber,
				                 vendorStyle:vendorStyle,
				                 upc:upc,
				                 classNumber:classNumber,
				                 createdToday:createdToday,
				                 finalTodayDate:finalTodayDate,
				                 vendorNumber:vendorNumber},function(responseText) { 
				                        responseText = repalcePetTable(responseText);
				                        $("#petTable").html(responseText);         
										$('.tree').treegrid();
										$('.tree2').treegrid({
											expanderExpandedClass: 'icon-minus-sign',
											expanderCollapsedClass: 'icon-plus-sign'
										});
										$("#overlay_Dept").css("display","none");
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
										$("#dialog_ASearch").css("display","none");
										defaultAdvSearchSettings();   
										
										$("#overlay_pageLoading").hide();
				                 });
				               //document.getElementById('searchReturnId').value = '';
				             	document.getElementById('searchReturnId').value = 'true';
				             	//alert("In searchSearch()--" + document.getElementById('searchReturnId').value);
				             			
 
}

function searchSearchDeptopen()
{
  $("#overlay_Dept").css("display","block");
$("#dialog_Dept").css("display","block");
$("#AdseachClicked").val("yes");
 
}







function searchimageStatusopen()
{
  //alert("searchimageStatusopen");
   $("#overlay_Image").css("display","block");
  
 $("#dialog_Image").css("display","block");
 
}

function searchImageClear()
{
	 $('.advImgcheckboxclass').each(function() { 
	//loop through each checkbox
				           
	 this.checked = false; //deselect all checkboxes with class "checkbox1"                       
       });
 
}
function searchImageSave()
{
		 var selectedImages = [];
				 $.each($("input[name='advChkImageStatus']:checked"), function(){            
					 			selectedImages.push($(this).val());
				           });
				 $("#advImageStatus").val(selectedImages.join(",")); 
				  $("#advImageStatus").attr("disabled","disabled");
				  $("#overlay_Image").css("display","none");
				  $("#dialog_Image").css("display","none");
 
}
function searchImageClose()
{
  $("#overlay_Image").css("display","none");
	$("#dialog_Image").css("display","none");
 
}




function searchcontentStatusopen()
{
  $("#overlay_Content").css("display","block");
  $("#dialog_Content").css("display","block");
 
}

function searchContentClear()
{
	 $('.advContcheckboxclass').each(function() { 
//loop through each checkbox
				           
 	this.checked = false; //deselect all checkboxes with class "checkbox1"                       
       });  
 
}
function searchContentSaveAndClose()
{
	var selectedContent = [];
				 $.each($("input[name='advChkContentStatus']:checked"), function(){            
					 			selectedContent.push($(this).val());
				           });
				 $("#advContentStatus").val(selectedContent.join(",")); 
				  $("#advContentStatus").attr("disabled","disabled");
				  $("#overlay_Content").css("display","none");
				  $("#dialog_Content").css("display","none");
 
}
function searchContentClose()
{
  $("#overlay_Content").css("display","none");
	$("#dialog_Content").css("display","none");
 
}





function searchRequestTypeopen()
{
  $("#overlay_ReqType").css("display","block");
 $("#dialog_ReqType").css("display","block");
 
}

function searchRequestTypeClear()
{
	$('.advReqcheckboxclass').each(function() { 
//loop through each checkbox
				           
 this.checked = false; //deselect all checkboxes with class "checkbox1"                       
       });  
 
}
function searchRequestTypeSaveAndClose()
{
	var selectedRequest = [];
				 $.each($("input[name='advChkRequestType']:checked"), function(){            
					 			selectedRequest.push($(this).val());
				           });
				 $("#advRequestType").val(selectedRequest.join(",")); 
				  $("#advRequestType").attr("disabled","disabled");
				  $("#overlay_ReqType").css("display","none");
				  $("#dialog_ReqType").css("display","none");
 
}
function searchRequestTypeClose()
{
   $("#overlay_ReqType").css("display","none");
	$("#dialog_ReqType").css("display","none");
 
}






function searchClassNumberopen()
{
  $("#overlay_ClassNo").css("display","block");
	$("#dialog_ClassNo").css("display","block");
	
 
}

function searchClassNumberClear()
{
	 $('.advClassscheckboxclass').each(function() { 
//loop through each checkbox
				           
 this.checked = false; //deselect all checkboxes with class "checkbox1"                       
       }); 
 
}
function searchClassNumberSaveAndClose()
{
	var selectedClass = [];
				 $.each($("input[name='advChkSelectionClassNo']:checked"), function(){            
					 			selectedClass.push($(this).val());
				           });
				 $("#advClassNumber").val(selectedClass.join(",")); 
				  $("#advClassNumber").attr("disabled","disabled");
				  $("#overlay_ClassNo").css("display","none");
				  $("#dialog_ClassNo").css("display","none");
 
}
function searchClassNumberClose()
{
   $("#overlay_ClassNo").css("display","none");
	$("#dialog_ClassNo").css("display","none");
 
}


function petinactiveclicked()
{
	//$("#petActive").attr('checked',false);
	$('input[name=petActive]').attr('checked',false);
	$('input[name=petClosed]').attr('checked',false);
}

function petactiveclicked()
{
  //$("#petInActive").attr('checked',false);
  $('input[name=petInActive]').attr('checked',false);
  $('input[name=petClosed]').attr('checked',false);
}


function petclosedclicked()
{
  //$("#petInActive").attr('checked',false);
	 $('input[name=petInActive]').attr('checked',false);
     $('input[name=petActive]').attr('checked',false);
}

function disableStatus(input){
	if(input != null){
		var val = input.value;
		if(val.length > 0){
			$('input[name=petActive]').attr('disabled','disabled');
			$('input[name=petInActive]').attr('disabled','disabled');
			$('input[name=petClosed]').attr('disabled','disabled');
		}else{
			$('input[name=petActive]').attr('disabled',false);
			$('input[name=petInActive]').attr('disabled',false);
			$('input[name=petClosed]').attr('disabled',false);
		}
	}
}

function isNumberUpc() {  
      //  alert("Checking Numeric or not");
       var upc = $("#advUpc").val().trim();
      //  alert("UPC"+upc.length);
       if(upc.length > 0){
        if (isNaN(upc)) 
		  {
		    alert("UPC number should be numeric");
		    $("#advUpc").val("");
		     $("#advUpc").focus();
		  }else {
			  if (upc.length < 12){
			   alert("UPC number should have minimum length of 12 digit.");
			    $("#advUpc").val("");
			    $("#advUpc").focus();
			  }
		  }
        }
    }  

 
function isVendorNumber() {  
    //  alert("Checking Numeric or not");
     var vendNumber = $("#advVenNumber").val().trim();
    //  alert("UPC"+upc.length);
     if(vendNumber.length > 0){
      if (isNaN(vendNumber)) 
		  {
		    alert("Vendor Number should be numeric");
		    $("#advVenNumber").val("");
		     $("#advVenNumber").focus();
		  }else {
			  if (vendNumber.length <= 6 || vendNumber.length > 8){
			   alert("Vendor Number should be either 7 digit or 8 digit.");
			    $("#advVenNumber").val("");
			    $("#advVenNumber").focus();
			  }
		  }
      }
  } 


function advSearch() {
	$("#overlay_Image_advSearch").css("display","block");
	$("#dialog_ASearch").css("display","block");
	defaultAdvSearchSettings();
}


function repalceAdvPetTable(responseText){
var startindex= responseText.indexOf("advTableStart");
var startend= responseText.indexOf("advTableEnd");
var tablecontent1 = responseText.substring(startindex+21,startend-9);
return tablecontent1;
}



function checkdate(input){
var validformat=/(\d{2})-(\d{2})-(\d{4})/; 
var returnval=false;
if (!validformat.test(input))
returnval=false;
else{ //Detailed check for valid date ranges
	var monthfield=input.split("-")[0];
	var dayfield=input.split("-")[1];
	var yearfield=input.split("-")[2];
	var dayobj = new Date(yearfield, monthfield-1, dayfield);
	if ((dayobj.getMonth()+1!=monthfield)||(dayobj.getDate()!=dayfield)||(dayobj.getFullYear()!=yearfield))
	returnval=false
	else
	returnval=true;
}
return returnval;
}

function createmannualpet(){
	window.location = '<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/wps/portal/home/CreatePET" %>';
	}


	function logout_home(){			
		var username = document.getElementById("username").value;		
		if(username.indexOf('@') === -1) 
		{
		   window.location = "/wps/portal/home/InternalLogin";
		} else {
			
			window.location = "/wps/portal/home/ExternalVendorLogin";
		}
		
	}

	function imageStatus(imageStatusValue,orinNnubmer){

		document.getElementById("selectedOrin").value =orinNnubmer;
		document.getElementById("imageStatus").value =imageStatusValue;

		$("#workListDisplayForm").submit();

			//window.location = "/wps/portal/home/imagestatus";
			
		}
	
	
	function contentStatus(contentStatusValue,orinNumber){

		
		document.getElementById("selectedOrin").value =orinNumber;
		document.getElementById("contentStatus").value =contentStatusValue;
		
		$("#workListDisplayForm").submit();		
			
		}
function imageStatus(imageStatusValue,orinNnubmer){


document.getElementById("selectedOrin").value =orinNnubmer;
document.getElementById("imageStatus").value =imageStatusValue;

$("#workListDisplayForm").submit();

}
//Method for inActivate ajax call
function inactivateAjaxCall(){
	//alert('inactivate');
	var url = $("#ajaxaction").val();	
	var deptNo = $("#deptNo").val();
	/*if( $("#adDeptNo").val().trim().length == 0){
		searchClear();
		$("#adDeptNo").val(deptNo);
	}*/
	//Default Load inactivate through search 
	/*if( $("#deptNo").val().trim().length > 0){
		alert("Logic got");
		searchClear();
		$("#adDeptNo").val(deptNo);
	}*/
	
	//$("#deptNo").val(depts.join(", "));
	
	var selectedRequest = [];
	$.each($("input[name='styleItem']:checked,input[name='selectedStyles']:checked"), function(){            
		selectedRequest.push($(this).val());
	});
	$("#inActivateid").val(selectedRequest.join(","));
	var inactivateOrinValue = $("#inActivateid").val();
		if(selectedRequest.length==0){
			alert("Please select atleast one row then press Inactivate PETs");
			return ;
		}else if($("#stylepetstatid").val() == 'Deactivated' ||$("#stylecolorpetstatid").val()== 'Deactivated'){
			alert("Selected PET/PETs are already inactive. Please select active PET. ");
			return;
		}else{
			$.get(url,{styleItem: inactivateOrinValue,statusParam: 'inactivate'},function(responseText) { 
				if('false'== $("#searchReturnId").val()){
					//alert('not come from search');
					//document.getElementById('workListDisplayForm').submit();//Default Load Page submission
					if( $("#deptNo").val().trim().length > 0){
						//alert("Logic got");
						searchClear();
						$("#adDeptNo").val(deptNo);
						searchSearch();
					}
				}else if('true'== $("#searchReturnId").val()) {
					//alert('come from search inactivate');
					//document.getElementById('petActive').checked = true;					
					searchSearch();
					//$("#deptNo").val("");
					//$("#deptNo").val(deptNo);
				}
			});
		}
}


//Method for activate ajax call
function activateAjaxCall(){
	//alert("Calling Activate")
	var url = $("#ajaxaction").val();     
	var deptNo = $("#deptNo").val();
	/*if( $("#adDeptNo").val().trim().length == 0){
		searchClear();
		$("#adDeptNo").val(deptNo);
	}*/
	var selectedActivateRequest = [];
	$.each($("input[name='styleItem']:checked,input[name='selectedStyles']:checked"), function(){            
		selectedActivateRequest.push($(this).val());
	});
	$("#activatePetid").val(selectedActivateRequest.join(","));
	var activateOrinValue = $("#activatePetid").val();			
		if(selectedActivateRequest.length==0){
			alert("Please select atleast one row then press Activate PETs");
			return;
		}else if($("#stylepetstatid").val() == 'Initiated' ||$("#stylecolorpetstatid").val()== 'Initiated'){
			alert("Selected PET/PETs are already active. Please select inactive PET.");
			return;
		}else{			
			$.get(url,{styleItem: activateOrinValue,statusParam: 'activate'},function(responseText) {					
				if('false'== $("#searchReturnId").val()){
					//alert('not come from search Activate');
					//document.getElementById('workListDisplayForm').submit();//Default Load Page submission
					if( $("#deptNo").val().trim().length > 0){
						//alert("Logic got Activate");
						searchClear();
						$("#adDeptNo").val(deptNo);
						searchSearch();
					}
				}else if('true'== $("#searchReturnId").val()) {
					//alert('come from search Activate');
					//document.getElementById('petInActive').checked = true;					
					searchSearch();
					//$("#deptNo").val("");
					//$("#deptNo").val(deptNo);
				}				
				
				//document.getElementById('petInActive').checked = true;
				//searchSearch();
				//$("#deptNo").val("");
				//$("#deptNo").val(deptNo);
			});
		}
}


/*This method is responsible for passing values of parent child orin selection */
function childCheckedRows(elem,parentOrinNo){
	var checkedStyle = $("#mainPetTable").find("[parentorinno ='"+parentOrinNo+"']");
	$(checkedStyle).each(function() {
		if(elem.checked){
			this.checked = true;
		}else{
			this.checked = false;
		}			               
	});
}

function getPetStatValue(petstatusValue){
	document.getElementById('stylepetstatid').value = petstatusValue;
}

function getStyleColorPetStatValue(chilepetstatusValue){
	document.getElementById('stylecolorpetstatid').value = chilepetstatusValue;setHiddenFieldValue()
}


function setHiddenFieldValue(){
	document.getElementById('searchReturnId').value = 'true';
}
// new requirement
function checkcompletiondate(dtElm, count){	
	//alert('dtElm.'+dtElm.value);
	var completionDate="";
	if($("#tCompletionDate").val().trim().length>0){
		completionDate =dtElm.value;
		//alert('completionDate.'+completionDate);
		var completionDateBackupvar='tbCompletionDate'+count;
		var orinNumberVar = 'tbOrinNumber'+count; 
		var styleOrinNumberVar = 'tbStyleOrinNumber'+count; 		
		//alert('pass.'+document.getElementById(completionDateBackupvar).value);
		var completionDateBackup = document.getElementById(completionDateBackupvar).value;
		//var completionDateBackup = dtElm.value;
		
		
		var orinNumber = document.getElementById(orinNumberVar).value;
		var styleorinNumber = document.getElementById(styleOrinNumberVar).value;		
		var returnval=false;
		//var validformat=/(\d{2})-(\d{2})-(\d{4})/; 
		var validformat=/(\d{4})-(\d{2})-(\d{2})/; 
		
		if (!validformat.test(completionDate))
		returnval=false;
		else{ //Detailed check for valid date ranges
			/*var monthfield=completionDate.split("-")[0];
			var dayfield=completionDate.split("-")[1];
			var yearfield=completionDate.split("-")[2];*/
			var yearfield=completionDate.split("-")[0];
			var monthfield=completionDate.split("-")[1];
			var dayfield=completionDate.split("-")[2];
			var dayobj = new Date(yearfield, monthfield-1, dayfield);
			if ((dayobj.getMonth()+1!=monthfield)||(dayobj.getDate()!=dayfield)||(dayobj.getFullYear()!=yearfield)){
			returnval=false;
			}
			else{
			returnval=true;
			}
		}
		if(!returnval){
			alert('Please enter completion date in yyyy-mm-dd format.');
			$("#tCompletionDate").val(completionDateBackup);
		}else{
			
			//var compareResult =completionDate.localeCompare(completionDateBackup);
			//if(compareResult == 0){
				//alert('Entered date and previous dates are same.');
				//$("#tCompletionDate").val(completionDateBackup);
			//}else{
				var newCompletionDate = new Date(completionDate);
				//var newCompletionDate = new Date(completionDateBackupTmp);
				
				
				
				
				var parentCompletionDateVal = document.getElementById(orinNumber+'_CmpDate').value ;
				var parentCompletionDate = new Date(parentCompletionDateVal);
				var isCmpDateEarlier = 'N';
				if(parentCompletionDate > newCompletionDate){
						isCmpDateEarlier = 'Y';
				
				}
				//alert('isCmpDateEarlier:::' +isCmpDateEarlier +'parentCompletionDate::'+ parentCompletionDate+"newCompletionDate::" +newCompletionDate);
				var todayDate = new Date();
				if(newCompletionDate > todayDate){					
					invokeAjaxCallForCompletionDate(completionDate, completionDateBackup, orinNumber, styleorinNumber,isCmpDateEarlier);
				}else{
					alert('Date invalid - please enter a future date.');
					$("#tCompletionDate").val(completionDateBackup);
				}
				
			//}
		}
	}else{
		alert('Please enter the completion date.');
	}
	
	
	
	}

function clickcompletiondate(count){	
	var completionDateBackup='tbCompletionDate'+count;
	alert(document.getElementById(completionDateBackup).value);
	//tbCompletionDate${subcount}
	
}

function invokeAjaxCallForCompletionDate(completionDate, completionDateBackup, orinNumber, styleorinNumber,isCmpDateEarlier){	
var url = $("#ajaxaction").val();
$.get(url,{completionDate:completionDate,orinNumber:orinNumber,styleorinNumber:styleorinNumber,isCmpDateEarlier:isCmpDateEarlier},function(responseText) { 
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
			alert(document.getElementById("updateDateMessage").value);
			
   });
}
