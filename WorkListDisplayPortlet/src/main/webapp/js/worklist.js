/**
 * 
 */
 
 /**
 * global status vars
 */
var depSearchInProgress = false;
var currentSortedColumn = '';


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
	
	
	/**
	* commented and made inactive after js optimization and cleanup
	*/
	/* $('.tree').treegrid();
	$('.tree2').treegrid({
		expanderExpandedClass: 'icon-minus-sign',
		expanderCollapsedClass: 'icon-plus-sign'
	}); */


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

$("#advOrin").on({
	  keydown: function(e) {
		if (e.which === 32)
		  return false;
	  },
	  change: function() {
		this.value = this.value.replace(/\s/g, "");
	  }
	});

});



function createmannualpet() {
	window.location = "/wps/portal/home/createmannualpet";
}
// Belk PIM Phase - II. Change for Create Grouping - Start. AFUPYB3
function goToGrouping() {
	window.location = "/wps/portal/home/Grouping";
}
//Belk PIM Phase - II. Change for Create Grouping - Start. AFUPYB3
function inactivepets() {
	window.location = "/wps/portal/home/inactivepets";
}
function activatepets() {
	window.location = "/wps/portal/home/activatepets";
}


function columnsorting(selectedColumn)
{
                 var url = $("#ajaxaction").val();
				
				$("#overlay_Image_advSearch").show();
				$("#overlay_pageLoading").show();
				
				currentSortedColumn = selectedColumn;
                 
				$.get(url,{selectedColumnName:selectedColumn},function(responseText) { 
                        $('#deptTable').dialog( "destroy" );
						//$('div.dlg-dept').remove();
						
						responseText = repalcePetTable(responseText);
                        
						$("#petTable").html(responseText);
						//$("#petTable").find('#deptTable').remove();		
						
						
						
						bindDeptDialog();
						
						/**
						* commented and made inactive after js optimization and cleanup
						*/
						/*  $('.tree').treegrid();
							$('.tree2').treegrid({
								expanderExpandedClass: 'icon-minus-sign',
								expanderCollapsedClass: 'icon-plus-sign'
							});
						*/	
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
							
                    }).always(function(){
						$("#overlay_Image_advSearch").hide();
						$("#overlay_pageLoading").hide();
					});
}



function getpetdetails(){
	var depLength = document.getElementById("deptNo").value.trim().length;
	if( depLength > 0){
		$("#workListDisplayForm").submit();
	} else{
		$("#overlay_pageLoading1").hide();
		alert("Please select at least one department.");
	}
}

function getThePageContent(pageNumber){
                    var url = $("#ajaxaction").val();
					
					$("#overlay_Image_advSearch").show();
					$("#overlay_pageLoading").show();
                 
				 $.get(url,{selectedColumnName: currentSortedColumn, pageNo:pageNumber, fromPage: 'pagination'},function(responseText) { 
						
						$('#deptTable').dialog( "destroy" );
                        
						
						responseText = repalcePetTable(responseText);
                        
						$("#petTable").html(responseText);
						//$("#petTable").find('#deptTable').remove();						
						
						bindDeptDialog();
						
						/**
						* commented and made inactive after js optimization and cleanup
						*/
						/* $('.tree').treegrid();
						$('.tree2').treegrid({
							expanderExpandedClass: 'icon-minus-sign',
							expanderCollapsedClass: 'icon-plus-sign'
						});
						*/
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
                    }).always(function(){
						$("#overlay_Image_advSearch").hide();
						$("#overlay_pageLoading").hide();
						$('#sel-page-num').val(pageNumber);
					});
                
}

function onloaddept() {
	
	var depts = []; 
	
	$.each($("input[name='chkSelectedDept']:checked"), function(){            
        depts.push($(this).val());
    });
	
	if(depts.length >0){
		
		   $("#deptNo").val(depts.join(","));
		   $("#deptNo").attr("disabled","disabled");
   }
	
}


function depSearch(depOperation) {
		if(depOperation == 'depSaveClose'){	
var dept_validation = [];
			$.each($("input[name='chkSelectedDept']:checked"), function(){            
			              dept_validation.push($(this).val());
			          });			 
			if(dept_validation.length>1){
					alert('Please select only one department.');
					return;
			}		
		$("#overlay_pageLoading1").show();
			if('yes'==$("#AdseachClicked").val()){				
				 var addepts = [];
				 var advSelectedDepartments="";
				 $.each($("input[name='chkSelectedDept']:checked"), function(){            
					 			addepts.push($(this).val());
				           });
						   
				if(addepts.length == 0){
					 alert("Please select at least one department.");
					 return;
				} else{					
					addepts.sort();
					$("#adDeptNo").val(addepts.join(","));
					//Getting all the other fields to retain the values				  
					if( $("#adDeptNo").val().trim().length>0 ){
						advSelectedDepartments = $("#adDeptNo").val().trim();
					}
					//block to remove unchecked items when closing
					$('input.deptcheckbox').each(function(index){
						if(!$(this).is(':checked')){
							$(this).parent().parent().remove();
						}
					});
				}
					var completionDateFrom="";
					if($("#datepicker1").val().trim().length>0){
						completionDateFrom =$("#datepicker1").val().trim();
					}
					var completionDateTo="";
					if($("#datepicker2").val().trim().length>0){
						completionDateTo =$("#datepicker2").val().trim();
					}
					var imageStatus="";
					if($("#advImageStatus").val().trim().length>0){
						imageStatus =$("#advImageStatus").val().trim();
					}
					var contentStatus="";
					if($("#advImageStatus").val().trim().length>0){
						contentStatus =$("#advContentStatus").val().trim();
					}
					var petStatus = '';
					var temp = $('input:radio[name=petActive]:checked').val();
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
					var orinNumber="";
					if($("#advOrin").val().trim().length>0){
						orinNumber =$("#advOrin").val().trim();
					}
					var vendorStyle="";
					if($("#advVendorStyle").val().trim().length>0){
						vendorStyle =$("#advVendorStyle").val().trim();
					}
					var upc="";
					if($("#advUpc").val().trim().length>0){
						upc =$("#advUpc").val().trim();
					}
					var classNumber="";
						if($("#advClassNumber").val().trim().length>0){
						classNumber =$("#advClassNumber").val().trim();
					}
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
			   var url = $("#ajaxaction").val();				
               depSearchInProgress = true;
			   var btnSaveDept1Text = $('#btnSaveDept1').val();
			   $('#btnSaveDept1').val('Saving..');
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
	                 vendorNumber:vendorNumber,
					 
					 /** Modified For PIM Phase 2 
					* - Sending additional attr for grouping search
					* Date: 06/04/2016
					* Modified By: Cognizant
					*/
					searchResults: $('[name=searchResults]:checked').val() || '' ,
					groupingID: $('#groupingID').val(),
					groupingName: $('#groupingName').val(),
               },function(responseText) {
					$('#deptTable').dialog('close');
					$('div.dlg-dept').remove();
                    responseText = repalceAdvPetTable(responseText);
                    $("#advanceSearchDiv").html(responseText);
					$("#overlay_pageLoading1").hide();
					$("#dialog_ASearch").css("display","block");
					defaultAdvSearchSettings();
					
					attachAdvSearchParamsDlg(); //attaching params dlg when adv saearch dlg is open
					
					depSearchInProgress = false;					  
                  })
				  .always(function(){
					  $('#btnSaveDept1').val(btnSaveDept1Text);
				  });
				  
				  $("#adDeptNo").attr("disabled","disabled");
				   //$("#overlay_Dept").css("display","none");
				   //$("#dialog_Dept").css("display","none");
									   
			}else{
				 document.getElementById('searchReturnId').value = 'false';//Need to check		
				 var depts = [];
				 $.each($("input[name='chkSelectedDept']:checked"), function(){            
				               depts.push($(this).val());
				           });
				
				if(depts.length==0){
				$("#overlay_pageLoading1").hide();
				alert("Please select at least one department.");
				
				return;
				}else{
					depts.sort();
				    $("#deptNo").val(depts.join(","));
					$("#adDeptNo").val(depts.join(","));
				    $("#deptNo").attr("disabled","disabled");
				   }
				   
				   //Submitting the request to save the content to the controller
				                    var url = $("#ajaxaction").val();
									var departs = $("#deptNo").val();
									var done="no";
								
								var btnSaveDept1Text = $('#btnSaveDept1').val();
								$('#btnSaveDept1').val('Saving..');
				                
								$.get(url,{departmentOperation:depOperation,selectedDepartments:departs},function(responseText) { 
										$('#deptTable').dialog('close'); //closing the dept search ui dialog
										$('div.dlg-dept').remove(); //fix applied for this struct to make it compatible with ui dialog from rpeating content
										$('div.dlg-advSearch').remove(); //fix for ui dlg
										
										responseText = repalcePetTable(responseText);
										
										
				                        
										$("#petTable").html(responseText);         
										/**
										* commented and made inactive after js optimization and cleanup
										*/
										/* $('.tree').treegrid();
										$('.tree2').treegrid({
											expanderExpandedClass: 'icon-minus-sign',
											expanderCollapsedClass: 'icon-plus-sign'
										}); */
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
										
										//binding ui dialogs
										bindDeptDialog();
										
										
										
				                    }).always(function(){
										$('#btnSaveDept1').val(btnSaveDept1Text);
									});
						}
			}else if(depOperation == 'depClose'){
					 var url = $("#ajaxaction").val();
					 var btnCloseBtnText = $('#btnCloseDept1').val();
					 
						$('#btnCloseDept1').val('Closing..');
				                 $.get(url,{departmentOperation:depOperation},function(responseText) { 
										$('#deptTable').dialog('close'); //closing the dept search ui dialog
										$('div.dlg-dept').remove();
										
										responseText = repalceDeptTable(responseText);
				                        $("#deptTable").html(responseText);         
										
										//$("#overlay_Dept").css("display","none");
										//$("#dialog_Dept").css("display","none");
										bindDeptDialog();
										
				                    }).always(function(){
										$('#btnCloseDept1').val(btnCloseBtnText);
									});
			}else if(depOperation == 'depClear'){
				
				        $('#selectAllDeptOnSearch').prop('checked', false);
						$('.deptcheckbox').each(function() { //loop through each checkbox
				            this.checked = false; //deselect all checkboxes with class "checkbox1"                       
				        });         
				   
				
					
			}
			else if(depOperation == 'depSearch'){
				   //Submitting the request to save the content to the controller
									var adsrch=$("#AdseachClicked").val();
									document.getElementById("searchClicked").value = "no";	
				                    var url = $("#ajaxaction").val();
									var departsToSearch = $("#selectedDeptSearch").val().trim();
									if(departsToSearch.length==0){
									alert("Please enter department details to search.");
									return;
									}
									
									var departs = $("#deptNo").val();
									var srchBtnText= $('#btnSearch1').val();
									$('#btnSearch1').val('Searching..');
				                 $.get(url,{departmentOperation:depOperation,departmentsToSearch:departsToSearch,AdseachClicked:adsrch,selectedDepartments:departs},function(responseText) { 
				                        responseText = repalceDeptTable(responseText);
				                        $("#deptTable").html(responseText);
										
										var toatDeptsPresent = $('[name=chkSelectedDept]').length;
										var totalDeptsChecked = $('[name=chkSelectedDept]:checked').length;
										
										if(toatDeptsPresent == totalDeptsChecked)
											$('#selectAllDeptOnSearch').prop('checked', true);
										else
											$('#selectAllDeptOnSearch').prop('checked', false); //making the all dept selecter checkbox uncheckd
										
						//$("#overlay_Dept").css("display","block");				
						//$("#dialog_Dept").css("display","block");	
						
						if('yes'==$("#AdseachClicked").val()){
							$("#dialog_ASearch").css("display","block");
						}
						
				  }).always(function(){
					  $('#btnSearch1').val(srchBtnText);
				  });
			}
}


function showDeptPopup() {
	//$("#overlay_Dept").css("display","block");
	//$("#dialog_Dept").css("display","block");
	$("#AdseachClicked").val("no");
	document.getElementById("searchClicked").value = "no";
	$("#searchDeptResultsId").html("");
	
	$('#deptTable').dialog('open');
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
	//populateReset();
	//$("#overlay_pageLoading1").hide();
   //$("#dialog_ASearch").css("display","none");
   $("#overlay_Image_advSearch").css("display","none");
   
   $('#advanceSearchDiv').dialog('close');
   
}


function searchClear()
{
  $("#adDeptNo").val(""); 
  $("#datepicker1").val("");
  $("#datepicker2").val("");
  $("#advImageStatus").val("");
  $("#advContentStatus").val("");
  $('input[name=petActive]').attr('checked',false);
  $('input[name=petInActive]').attr('checked',false);
  $('input[name=petClosed]').attr('checked',false);
  if($('input[name=petActive]').attr('disabled')){
	$('input[name=petActive]').prop("disabled" , false); 
  }
 if($('input[name=petInActive]').attr('disabled')){
	$('input[name=petInActive]').prop("disabled" , false); 
 }
 if($('input[name=petClosed]').attr('disabled')){
	$('input[name=petClosed]').prop("disabled" , false); 
 }
  $("#advRequestType").val("");
  $("#advOrin").val("");  
  if($("#advOrin").attr('disabled')){	
	$("#advOrin").prop("disabled" , false); 
  }  
  $("#advVendorStyle").val("");
  $("#advUpc").val("");
  $("#advClassNumber").val("");
  $('input[name=rdCretedToday]').attr('checked',false);  
  
  if($("#advVenNumber").attr('disabled')){
	  $("#advVenNumber").prop("disabled" , false);
  }
  $("#advVenNumber").val("");
  $("#groupingID").val("");
  $("#groupingName").val("");
  
  /** Modified For PIM Phase 2 
	* -clearing search Type, groupname and id
	* Date: 06/04/2016
	* Modified By: Cognizant
	*/
  if(!$('[name=searchResults]').prop('readonly'))
	$('[name=searchResults]').prop('checked', false);
  
  $('#groupingID').prop('disabled', false).val('');
  $('#groupingName').prop('disabled', false).val('');
}


function populateReset()
{
  
  var url = $("#ajaxaction").val();
				                 $.get(url,{advSearchOperation:'searchReset'},function(responseText) { 
				                        responseText = repalceAdvPetTable(responseText);
				                        $("#advanceSearchDiv").html(responseText);   
										resetAdvSearchSettings(); 
				                    });
				                 
				                    
 
}



function searchReset()
{
  
  var url = $("#ajaxaction").val();
				                 $.get(url,{advSearchOperation:'searchReset'},function(responseText) { 
				                        responseText = repalceAdvPetTable(responseText);
				                        $("#advanceSearchDiv").html(responseText);   
										
										//$("#overlay_Dept").css("display","none");
										//$("#dialog_Dept").css("display","none");
										
										$("#dialog_ASearch").css("display","block");
										resetAdvSearchSettings();
										
										attachAdvSearchParamsDlg();
				                    });
				                 

}


function checkGroupingIdNamePopulation(advSelectedDepartments,completionDateFrom,completionDateTo,imageStatus,contentStatus,
		petStatus,requestType,vendorStyle,upc,createdToday,vendorNumber,orinNumber,classNumber,groupingID,groupingName){
	if((advSelectedDepartments.length > 0 || completionDateFrom.length > 0 || completionDateTo.length > 0 || imageStatus.length > 0 || 
			contentStatus.length > 0 || petStatus.length > 0 || requestType.length > 0 || vendorStyle.length > 0 || upc.length > 0||
			createdToday.length > 0 || vendorNumber.length > 0 || orinNumber.length > 0 || classNumber.length > 0) 
			&& (groupingID.length > 0 || groupingName.length > 0)){
		alert("Please search Only Grouping ID or Name");
		return false;
	}
}

function searchSearch()
{	//574
	//$("#deptNo").val("");
	
	document.getElementById("searchClicked").value = 'yes';
	var searchClicked = document.getElementById("searchClicked").value ;
	var searchResult = $("input[name='searchResults']:checked").val();
	var callType = $("#callType").val();
	var groupingID = $("#groupingID").val().trim();
	var groupingName = $("#groupingName").val().trim();
	var searchResults = $("[name=searchResults]").val().trim();
	
	var advSelectedDepartments="";
	if( $("#adDeptNo").val().trim().length>0 ){
		advSelectedDepartments = $("#adDeptNo").val().trim();
	}
	
	
	/*574 to populate dept in dept filter after hitting search*/
	$("#deptNo").val(advSelectedDepartments);
	
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
	var contentStatus="";
	if($("#advContentStatus").val().trim().length>0){
		contentStatus =$("#advContentStatus").val().trim();
	}
	
	var petStatus = '';
	var temp = $('input:radio[name=petActive]:checked').val();
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
	var orinNumber="";
	if($("#advOrin").val().trim().length>0){
		orinNumber =$("#advOrin").val().trim();
	}
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
	var upc="";
	if($("#advUpc").val().trim().length>0){
		upc =$("#advUpc").val().trim();
	}
	var classNumber="";
		if($("#advClassNumber").val().trim().length>0){
		classNumber =$("#advClassNumber").val().trim();
	}
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

   // Populating call type as groupingSearch when grouping id or name is populated
   if((searchResult == "includeGrps")){// && (groupingID != "" || groupingName != "")){
	   callType = "groupingSearch";
   }
   if(searchResult !="" && searchResult!= undefined && searchResult != 'undefined'){
	   if(advSelectedDepartments.length == 0 && completionDateFrom.length == 0 && completionDateTo.length == 0 && imageStatus.length == 0 && contentStatus.length == 0 && petStatus.length == 0 && requestType.length == 0 && vendorStyle.length == 0 && upc.length == 0 && createdToday.length == 0 && vendorNumber.length == 0 && orinNumber.length == 0 && classNumber.length == 0 && groupingID.length == 0 && groupingName.length == 0){
			alert("Please select atleast one search criteria before search");
			return false;
		}else{
			
			// Alert if grouping id/name and other search criteria is selected
			//return checkGroupingIdNamePopulation(advSelectedDepartments,completionDateFrom,completionDateTo,imageStatus,contentStatus,petStatus,requestType,vendorStyle,upc,createdToday,vendorNumber,orinNumber,classNumber,groupingID,groupingName);
			if((advSelectedDepartments.length > 0 || completionDateFrom.length > 0 || completionDateTo.length > 0 || imageStatus.length > 0 || 
					contentStatus.length > 0 || petStatus.length > 0 || requestType.length > 0 || vendorStyle.length > 0 || upc.length > 0||
					createdToday.length > 0 || vendorNumber.length > 0 || orinNumber.length > 0 || classNumber.length > 0) 
					&& (groupingID.length > 0 || groupingName.length > 0)){
				alert("Please search either Grouping ID/Name or other Search Criteria");
				return false;
			}
			
			$("#overlay_pageLoading1").hide();
			//$("#dialog_ASearch").css("display","none");
			
			if($('#advanceSearchDiv').dialog('instance') !== undefined)
				$('#advanceSearchDiv').dialog('close');		
			
			$("#overlay_pageLoading").show();
	if(upc.length>0){	
		$.get(url,{advSearchOperation:'searchSearch',
									 callType:callType,
									 searchClicked:searchClicked,
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
					                 vendorNumber:vendorNumber,
					                 searchResults:searchResult,
					                 groupingID:groupingID,
					                 groupingName:groupingName,
									 searchResults: searchResults,
				},function(responseText) { 
				//$("#overlay_pageLoading").hide();
	              responseText = repalcePetTable(responseText);
	              if(responseText.indexOf('No pet found!') !== -1){
					  $('#advanceSearchDiv').dialog('open');
	            	  //$("#dialog_ASearch").css("display","block"); 
					  alert("UPC is not valid - please re-enter.");
	            	  return false;
					  
	              }
				  else{
					  
					  
					  		$.get(url,{advSearchOperation:'searchSearch',
									 callType:callType,
									 searchClicked:searchClicked,
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
					                 vendorNumber:vendorNumber,
					                 searchResults:searchResult,
					                 groupingID:groupingID,
					                 groupingName:groupingName},function(responseText) { 
											if($('#advanceSearchDiv').dialog('instance') !== undefined)
												$('#advanceSearchDiv').dialog('close');
											
											$('div.dlg-dept').remove(); //fix for ui dlg
											$('div.dlg-advSearch').remove(); //fix for ui dlg
											
											responseText = repalcePetTable(responseText);
					                        $("#petTable").html(responseText);
											/**
											* commented and made inactive after js optimization and cleanup
											*/
											/* $('.tree').treegrid();
											$('.tree2').treegrid({
												expanderExpandedClass: 'icon-minus-sign',
												expanderCollapsedClass: 'icon-plus-sign'
											}); */
											//$("#overlay_Dept").css("display","none");
											//$("#dialog_Dept").css("display","none");
											
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
											
											
											//$("#dialog_ASearch").css("display","none");
											
											defaultAdvSearchSettings();   
											
											//$("#overlay_pageLoading").hide();
					                 })
									.always(function(){
										$("#overlay_pageLoading").hide();
										$("#overlay_Image_advSearch").hide();
									});
					  
					  
					  
					 
					  
				  }
				  
	       }).always(function(){
			$("#overlay_pageLoading").hide();
			$("#overlay_Image_advSearch").hide();
		});
			
		
		
	}   
	else{
		
		
		$.get(url,{advSearchOperation:'searchSearch',
									 callType: callType,	
									 searchClicked:searchClicked,
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
					                 vendorNumber:vendorNumber,
					                 searchResults:searchResult,
					                 groupingID:groupingID,
					                 groupingName:groupingName},function(responseText) {
											
											if($('#advanceSearchDiv').dialog('instance') !== undefined)
												$('#advanceSearchDiv').dialog('close');
											
											$('div.dlg-dept').remove(); //fix for ui dlg
											
											$('div.dlg-advSearch').remove(); //fix for ui dlg
					                        
											responseText = repalcePetTable(responseText);
					                        $("#petTable").html(responseText);
											
											/**
											* commented and made inactive after js optimization and cleanup
											*/
											/* $('.tree').treegrid();
											$('.tree2').treegrid({
												expanderExpandedClass: 'icon-minus-sign',
												expanderCollapsedClass: 'icon-plus-sign'
											}); */
											//$("#overlay_Dept").css("display","none");
											//$("#dialog_Dept").css("display","none");
											
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
											//$("#dialog_ASearch").css("display","none");
											
											defaultAdvSearchSettings();   
											
											//$("#overlay_pageLoading").hide();
					                 }).always(function(){
										$("#overlay_pageLoading").hide();
										$("#overlay_Image_advSearch").hide();
									});
		
		
	}
	   
	}
   }else{
	   alert("Please select Search Result Type");
	   return false;
   }
   
           //document.getElementById('searchReturnId').value = '';
         	document.getElementById('searchReturnId').value = 'true';
				             			
 
}

function searchSearchDeptopen()
{
	//$("#overlay_Dept").css("display","block");
	//$("#dialog_Dept").css("display","block");
	$("#AdseachClicked").val("yes");
	$("#searchDeptResultsId").html("");
	
	$('#deptTable').dialog('open');
 
}

function searchimageStatusopen()
{
  // $("#overlay_Image").css("display","block");
 //$("#dialog_Image").css("display","block");
 
var selctdAttrs = $('#advImageStatus').val() || '';	
//calling method to clear any unsaved attrs
dispayOnlySavedSearchAttrs(selctdAttrs, $('.advImgcheckboxclass'));
	
$('#dialog_Image').dialog('open');
 
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
				  //$("#overlay_Image").css("display","none");
				  //$("#dialog_Image").css("display","none");
				  
				  $('#dialog_Image').dialog('close');
				 
 
}
function dispayOnlySavedSearchAttrs(savedValue, jqObjClass){
	//console.log(savedValue);
	savedValue = savedValue.length ? savedValue.split(',') : savedValue;	
	//iterating through attrs and deselecting if it is not yet saved
	try{
		jqObjClass.each(function(index){
			var curAttrValue = $(this).val() || '';
			if(savedValue.length && savedValue.indexOf(curAttrValue) >=0)
				$(this).prop('checked', true);
			else
				$(this).prop('checked', false);
		});
	}catch(ex){
		//console.log(ex.message);
	}
}

function searchImageClose()
{
   //$("#overlay_Image").css("display","none");
	//$("#dialog_Image").css("display","none");
	
	//searchReset();
	/* var selctdAttrs = $('#advImageStatus').val() || '';	
	//calling method to clear any unsaved attrs
	dispayOnlySavedSearchAttrs(selctdAttrs, $('.advImgcheckboxclass')); */
	
	$('#dialog_Image').dialog('close');
	//$('div.dlg-imgStat').remove();
}

function searchcontentStatusopen()
{
  //$("#overlay_Content").css("display","block");
  //$("#dialog_Content").css("display","block"); 
  
  var selctdAttrs = $('#advContentStatus').val() || '';	
  //calling method to clear any unsaved attrs
  dispayOnlySavedSearchAttrs(selctdAttrs, $('.advContcheckboxclass'));
  
  $('#dialog_Content').dialog('open');
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
				 
				 //$("#overlay_Content").css("display","none");
				  //$("#dialog_Content").css("display","none");
				  
				  $('#dialog_Content').dialog('close');
 
}
function searchContentClose()
{
	//$("#overlay_Content").css("display","none");
	//$("#dialog_Content").css("display","none");
	
	//searchReset();	
	/* var selctdAttrs = $('#advContentStatus').val() || '';	
	//calling method to clear any unsaved attrs
	dispayOnlySavedSearchAttrs(selctdAttrs, $('.advContcheckboxclass')); */
	
	$('#dialog_Content').dialog('close');
 
}

function searchRequestTypeopen()
{
  //$("#overlay_ReqType").css("display","block");
  //$("#dialog_ReqType").css("display","block");
  
  var selctdAttrs = $('#advRequestType').val() || '';	
  //calling method to clear any unsaved attrs
  dispayOnlySavedSearchAttrs(selctdAttrs, $('.advReqcheckboxclass'));
  
  $('#dialog_ReqType').dialog('open');
 
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
				  
				  //$("#overlay_ReqType").css("display","none");
				  //$("#dialog_ReqType").css("display","none");
				  
				  $('#dialog_ReqType').dialog('close');
 
}
function searchRequestTypeClose()
{
   $("#overlay_ReqType").css("display","none");
	$("#dialog_ReqType").css("display","none");
	//searchReset();	
	
	/* var selctdAttrs = $('#advRequestType').val() || '';	
	//calling method to clear any unsaved attrs
	dispayOnlySavedSearchAttrs(selctdAttrs, $('.advReqcheckboxclass')); */
	
	$('#dialog_ReqType').dialog('close');
}

function searchClassNumberopen()
{	
	$("#overlay_pageLoading").show();
	var timeoutID = window.setInterval(function(){
		if(depSearchInProgress == false){
			var selectedClassNos = $("#advClassNumber").val();
			var selectedClassNosArr = [];
			selectedClassNosArr = selectedClassNos.length ? selectedClassNos.split(',') : [];
			
			 $.each($("input[name='advChkSelectionClassNo']"), function(){
				 if(selectedClassNosArr.indexOf($(this).val()) >= 0){
					 $(this).prop('checked', true);
				 }else{
					 $(this).prop('checked', false);
				 }		
			 });
			$("#overlay_pageLoading").hide(); 			
			
			//$("#overlay_ClassNo").css("display","block");
			//$("#dialog_ClassNo").css("display","block");
			
			$('#dialog_ClassNo').dialog('open');
			
			clearInterval(timeoutID);
		}else{
			$("#overlay_pageLoading").show();
		}
	}, 200);
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
				  
				  //$("#overlay_ClassNo").css("display","none");
				  //$("#dialog_ClassNo").css("display","none");
	$('#dialog_ClassNo').dialog('close');
}
function searchClassNumberClose()
{
   //$("#overlay_ClassNo").css("display","none");
   //$("#dialog_ClassNo").css("display","none");

   $('#dialog_ClassNo').dialog('close');
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
       var upc = $("#advUpc").val().trim();
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
     var vendNumber = $("#advVenNumber").val().trim();
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
	//searchReset();
	$("#overlay_Image_advSearch").css("display","block");
	//$("#dialog_ASearch").css("display","block");
	defaultAdvSearchSettings();	
	var deptNos = $("#deptNo").val();
	var advSelectedDepartments="";
	if( $("#adDeptNo").val().trim().length>0 ){
		advSelectedDepartments = $("#adDeptNo").val().trim();
	}	
	var completionDateFrom="";
					if($("#datepicker1").val().trim().length>0){
						completionDateFrom =$("#datepicker1").val().trim();
					}
					var completionDateTo="";
					if($("#datepicker2").val().trim().length>0){
						completionDateTo =$("#datepicker2").val().trim();
					}
					var imageStatus="";
					if($("#advImageStatus").val().trim().length>0){
						imageStatus =$("#advImageStatus").val().trim();
					}
					var contentStatus="";
					if($("#advContentStatus").val().trim().length>0){
						contentStatus =$("#advContentStatus").val().trim();
					}
					var petStatus = '';
					var temp = $('input:radio[name=petActive]:checked').val();
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
					var orinNumber="";
					if($("#advOrin").val().trim().length>0){
						orinNumber =$("#advOrin").val().trim();
					}
					var vendorStyle="";
					if($("#advVendorStyle").val().trim().length>0){
						vendorStyle =$("#advVendorStyle").val().trim();
					}
					var upc="";
					if($("#advUpc").val().trim().length>0){
						upc =$("#advUpc").val().trim();
					}
					var classNumber="";
						if($("#advClassNumber").val().trim().length>0){
						classNumber =$("#advClassNumber").val().trim();
					}
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
					
					
					
	var url = $("#ajaxaction").val();
	//$("#dialog_ASearch").css("display","none");
	$("#overlay_pageLoading").show();
	setTimeout(function(){
		$.ajax({
			url: url,
			data:{advSearchPopUp:'advSearchPopUp',
				defaultDeptNos: deptNos,
				advSearchDept:advSelectedDepartments,
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
	                 vendorNumber:vendorNumber,
					
					/** Modified For PIM Phase 2 
					* - Sending additional attr for grouping search
					* Date: 06/04/2016
					* Modified By: Cognizant
					*/
					searchResults: $('[name=searchResults]:checked').val() || '' ,
					groupingID: $('#groupingID').val(),
					groupingName: $('#groupingName').val(),
					 
               },
			async: false,
			success: function(responseText) {	
					if(responseText.indexOf('advTableStart') !== -1){		
					 responseText = repalceAdvPetTable(responseText);
					  $("#advanceSearchDiv").html(responseText); 					  
					}			
					
					  //$("#overlay_Image_advSearch").css("display","block");					
					  $("#dialog_ASearch").css("display","block");
					  defaultAdvSearchSettings();
					  //VP4
					  searchClear();
					  if(!$('input[name=searchResults]:checked').length){
			                          $(':radio[value="showItemsOnly"]').prop('checked', 'checked');
			                      }
					  toggleEnability();
					  
					  //mapping ui dialog to the searchbox 
					  $('#advanceSearchDiv').dialog({
							modal: true,
							dialogClass: "dlg-custom dlg-advSearch",
							autoOpen: false,
							resizable: true,
							title: 'Search PET',
							width: 440,
							height: 600,
							minHeight: 565,
							minWidth: 440,
							open: function( event, ui ) {
								//$('#selectedDeptSearch').focus();
							}
						});
						
						//mapping image status, content status, class popup ui dlg handler
						attachAdvSearchParamsDlg();
						
						//depSearchInProgress = false;
						$("#overlay_pageLoading").hide();

						$('#advanceSearchDiv').dialog('open');
                  }
		});
	}, 1000);
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


	function logout_home(invalidateUserSessURL){
        var username = document.getElementById("username").value;

	 $.ajax({
			       url: invalidateUserSessURL,
			         type: 'GET',
			         datatype:'json',
			         data: { loggedInUser:username },
				         success: function(data){
                                 //alert('data '+data);
                                           					
	                }//End of Success
	        }); 
		
				
		if(username.indexOf('@') === -1) 
		{
		   window.location = "/wps/portal/home/InternalLogin";
		} else {
			
			window.location = "/wps/portal/home/ExternalVendorLogin";
		}
		
	}

	
//Method to get child data	
function getChildData(orinNum, showHideFlag, isGroup, uniqueIdentifier, parentGroupId){
	var url = $("#ajaxaction").val(); 
	var petLockedStatus = '';
	var petLockedUser ='';
	if($('tr[name=child_' + orinNum + ']').length <= 0){
	$("#overlay_pageLoading").show();
	
      $.ajax({
		         url: url ,
		         type: 'GET',
		         datatype:'json',
		         data: {"orinNum" : orinNum,
				        "callType": isGroup == 'Y' ? 'getChildgroup' : 'getChildData',
						parentGroupId: parentGroupId,
					   },
			         success: function(data){
						 /** Modified For PIM Phase 2 
						* - parsing response and extracting json from html 
						* - for group enabled search result and worklist
						* Date: 06/07/2016
						* Modified By: Cognizant
						*/
						//var myString = data.substr(data.indexOf("[{") , data.indexOf("}]")+2);
						//var myString = data.substr(data.indexOf("[{") , data.indexOf("}]")+2);
						var json = [];
						 try{
							var myString = data.substr(data.indexOf('STARTJSON') + 'STARTJSON'.length , (data.indexOf('ENDJSON') - 'ENDJSON'.length)-2);
							var json = $.parseJSON(myString);
						 }catch(ex){
						 }
						
						populateChildData(json,orinNum, showHideFlag, isGroup, uniqueIdentifier);
						$("#overlay_pageLoading").hide();
				}//End of Success				   
        }); //  $.ajax({
	}else{
		populateChildData([],orinNum, showHideFlag, isGroup, uniqueIdentifier);
	}
}	
	
function imageStatus(imageStatusValue,orinNo,petStatusValue, event){	
	var url = $("#ajaxaction").val(); 
	var petLockedStatus = '';
	var petLockedUser ='';
$("#overlay_pageLoading1").show();
      $.ajax({
		         url: url ,
		         type: 'GET',
		         datatype:'json',
		         data: { "lockedPet" : orinNo,
				        "lockedPettype":"Image"
					   },
			         success: function(data){
						var myString = data.substr(data.indexOf("[{") , data.indexOf("}]")+2);
						var json = $.parseJSON(myString);  
						$(json).each(function(i,val){
						    petLockedStatus = 	val.LockStatus;	
							petLockedUser  = 	val.petLockedUser;
							if(petLockedStatus){
								alert('PET is already locked for editing by '+petLockedUser+'. Please try later ');
								$("#overlay_pageLoading1").hide();
							}else {
								document.getElementById("selectedOrin").value = orinNo;
								document.getElementById("imageStatus").value = imageStatusValue;
								document.getElementById("petStatus").value = petStatusValue;
								var parentOrin = $(event.target).closest('tr').attr("data-tr-root");
								if(typeof parentOrin != 'undefined' && parentOrin!='')
								{
									document.getElementById("selectedParentOrin").value = parentOrin;
								}
								else
								{
									document.getElementById("selectedParentOrin").value = orinNo;
								}
								$("#workListDisplayForm").submit();
								}						
		                        
					});//$(json).each(function(i,val){			 
				}//End of Success				   
        }); //  $.ajax({					
}
	
function contentStatus(contentStatusValue, orinNumber, groupType, event){
/*if($("#groupingID").val() != "" || $("#groupingName").val()!=""){
		groupContentStatus(contentStatusValue, orinNumber, groupType);
	}else{
		if($('#workListType :selected').text() == "Groupings"){
			groupContentStatus(contentStatusValue, orinNumber, groupType);
		}else{*/
	
			var url = $("#ajaxaction").val();
			var petLockedStatus = '';
				var petLockedUser ='';
			$("#overlay_pageLoading1").show();
			      $.ajax({
					         url: url ,
					         type: 'GET',
					         datatype:'json',
					         data: { "lockedPet" : orinNumber,
							        "lockedPettype":"Content"
								   },
						         success: function(data){
									var myString = data.substr(data.indexOf("[{") , data.indexOf("}]")+2);
									var json = $.parseJSON(myString);  
									$(json).each(function(i,val){
									    petLockedStatus = 	val.LockStatus;	
										petLockedUser  = 	val.petLockedUser;
										if(petLockedStatus){
											alert('PET is already locked for editing by '+petLockedUser+'. Please try later ');
											$("#overlay_pageLoading1").hide();
										}else {
											document.getElementById("selectedOrin").value =orinNumber;
											document.getElementById("contentStatus").value =contentStatusValue;
											var parentOrin = $(event.target).closest('tr').attr("data-tr-root");
											if(typeof parentOrin != 'undefined' && parentOrin!='')
											{
												document.getElementById("selectedParentOrin").value = parentOrin;
											}
											else
											{
												document.getElementById("selectedParentOrin").value = orinNumber;
											}
											$("#workListDisplayForm").submit();
											}						
					                        
								});//$(json).each(function(i,val)			 
							}//End of Success				   
			        }); //  $.ajax(		
		/*}
		}*/	
}


function groupContentStatus(contentStatusValue, groupId, groupingType){
	
	document.getElementById("selectedGroupId").value = groupId;
	document.getElementById("contentStatus").value = contentStatusValue;
	document.getElementById("groupingType").value = groupingType;
	if($('#workListType :selected').text() == "Groupings" && document.getElementById("searchResultInput").value == ""){
		document.getElementById("searchResultInput").value = "includeGrps";
	}
	
	
	$("#workListDisplayForm").submit();
}
//Method for inActivate ajax call
function inactivateAjaxCall(){
	var url = $("#ajaxaction").val();	
	var deptNo = $("#deptNo").val();
	var flag = 'no';
	var activeFlag = 'no';
	var deactiveFlag = 'no';
	var parentActiveFlag = 'no';
	var parentDeactiveFlag = 'no';
	var selectedRequest = [];
	$.each($("input[name='styleItem']:checked,input[name='selectedStyles']:checked"), function(){            
		
		/** Modified For PIM Phase 2 
			* - adding group flag for items to be inactivated 
			* - in format e.g. 123:Y,124:N...
			* Date: 06/14/2016
			* Modified By: Cognizant
		*/
		var groupFlag = $(this).data('group') || 'N';
		groupFlag = groupFlag == '' ? 'N': groupFlag;
		
		selectedRequest.push($(this).val() + ':' + groupFlag);
	});
	$("#inActivateid").val(selectedRequest.join(","));
	var inactivateOrinValue = $("#inActivateid").val();
		if(selectedRequest.length==0){
			alert("Please select atleast one row then press Inactivate PETs");
			return ;
		}else{	
		
		
			$.each($("input[name='styleItem']:checked"), function(){   

				var parentStyle = [];
				var groupFlag = $(this).data('group') || 'N';
				if(groupFlag == 'Y'){
					parentStyleGroup = $(this).val().split('##');
					parentStyle = parentStyleGroup[0].split('_');
				}else
					parentStyle = $(this).val().split('_');
				
				if(parentStyle.length > 2){
					parentStyle[1] = parentStyle.slice(1, parentStyle.length).join('_');
				}
				
				if(parentStyle[1]=='Initiated'){
					parentActiveFlag='yes';
					}
				else if(parentStyle[1]=='Deactivated'){
					parentDeactiveFlag = 'yes';
				}
				else if(parentStyle[1]=='Closed'){
					parentActiveFlag='yes';
				}
				else if(parentStyle[1]=='Completed'){
					parentActiveFlag='yes';
				}
				else if(parentStyle[1]=='Publish_To_Web'){
					parentActiveFlag='yes';
				}
				else if(parentStyle[1]=='Waiting_To_Be_Closed'){
					parentActiveFlag='yes';
				}
				else if(parentStyle[1]=='In_Progress'){
					parentActiveFlag='yes';
				}
			});
			
			$.each($("input[name='selectedStyles']:checked"), function(){  
				childStyleColor = [];			
				childStyleColor = $(this).val().split('_');
				if(childStyleColor.length > 2){
					childStyleColor[1] = childStyleColor.slice(1, childStyleColor.length).join('_');
				}
				
				if(childStyleColor[1]=='Initiated'){
					activeFlag='yes';
					}
			else if(childStyleColor[1]=='Deactivated'){
					deactiveFlag = 'yes';
				}
				else if(childStyleColor[1]=='Closed'){
					activeFlag = 'yes';
				}
				else if(childStyleColor[1]=='Completed'){
					activeFlag = 'yes';
				}
				else if(childStyleColor[1]=='Publish_To_Web'){
					activeFlag = 'yes';
				}
				else if(childStyleColor[1]=='Waiting_To_Be_Closed'){
					activeFlag = 'yes';
				}
			});
			
			if(parentActiveFlag == 'yes'){
				
				
				if(deactiveFlag == 'yes'){
					flag = 'no';
				}
				else if(deactiveFlag == 'no' && activeFlag == 'yes'){
					flag = 'yes';
				}
				else if(deactiveFlag == 'no' && activeFlag == 'no' && parentActiveFlag == 'yes'){
					flag = 'yes';
				}
				}
				else{
					if(parentActiveFlag == 'no' && parentDeactiveFlag == 'no'){
							if(deactiveFlag == 'yes'){
							flag = 'no';
							}
							else if(deactiveFlag == 'no' && activeFlag == 'yes'){
								flag = 'yes';
							}
					}
					else{
						flag = 'no';
					}
				}
			if(flag == 'yes'){
			//$("#dialog_ASearch").css("display","none");	
			$("#overlay_pageLoading1").show();
			
			var searchResultValue = "";
			
			if($("#groupingID").val() == "" && $("#groupingName").val() == ""){
				searchResultValue = "";
			}else{
				searchResultValue = "includeGrps";
			}
			
			$.get(url,{styleItem: inactivateOrinValue,statusParam: 'inactivate', groupSearchResult:searchResultValue},function(responseText) {
				
				if('false'== $("#searchReturnId").val()){
					//alert('not come from search');
					document.getElementById('workListDisplayForm').submit();//Default Load Page submission
				}else if('true'== $("#searchReturnId").val()) {
					//document.getElementById('petActive').checked = true;					
					searchSearch();
					//$("#deptNo").val("");
					//$("#deptNo").val(deptNo);
				}
				
			});
			$("#overlay_pageLoading1").hide();
			}else if(flag == 'no'){
				//document.getElementById('petStatusID').innerHTML = 'Pet Status';
				//$("#overlay_petStatus").show();
				//$("#dialog_petStatus").show();
				$("#petStatusLabelId").html("Selected PET/PETs are already inactive. Please select active PET.");
				$('#dialog_petStatus').dialog('option', 'title', 'Pet Status');
				$('#dialog_petStatus').dialog('open');
			}
}
		
}


//Method for activate ajax call
function activateAjaxCall(){	
	var flag = 'no';
	var activeFlag = 'no';
	var deactiveFlag = 'no';
	var parentActiveFlag = 'no';
	var parentDeactiveFlag = 'no';
	
	var url = $("#ajaxaction").val();     
	var deptNo = $("#deptNo").val();
	/*if( $("#adDeptNo").val().trim().length == 0){
		searchClear();
		$("#adDeptNo").val(deptNo);
	}*/
	var selectedActivateRequest = [];
	var childStyleColor = [];
	var parentStyle = [];
	$.each($("input[name='styleItem']:checked,input[name='selectedStyles']:checked"), function(){
		/** Modified For PIM Phase 2 
			* - adding group flag for items to be activated 
			* - in format e.g. 123:Y,124:N...
			* Date: 06/14/2016
			* Modified By: Cognizant
		*/
		var groupFlag = $(this).data('group') || 'N';
		groupFlag = groupFlag == '' ? 'N': groupFlag;
		
		selectedActivateRequest.push($(this).val() + ':' + groupFlag);
	});
	$("#activatePetid").val(selectedActivateRequest.join(","));
	var activateOrinValue = $("#activatePetid").val();			
		if(selectedActivateRequest.length==0){
			alert("Please select atleast one row then press Activate PETs");
			return;
		}else{
			
			
			$.each($("input[name='styleItem']:checked"), function(){   

				var parentStyle = [];
				var groupFlag = $(this).data('group') || 'N';
				if(groupFlag == 'Y'){
					parentStyleGroup = $(this).val().split('##');
					parentStyle = parentStyleGroup[0].split('_');
				}else
					parentStyle = $(this).val().split('_');
				
				if(parentStyle.length > 2){
					parentStyle[1] = parentStyle.slice(1, parentStyle.length).join('_');
				}
				if(parentStyle[1]=='Initiated'){
						
					if(groupFlag == 'Y'){
						parentActiveFlag='no';
						deactiveFlag = 'yes';
					}else	
						parentActiveFlag='yes';
				}
				else if(parentStyle[1]=='Deactivated'){					
					//alert("Inside Parent Deactivated");
					parentDeactiveFlag = 'yes';
				}
				else if(parentStyle[1]=='Closed'){
					parentDeactiveFlag = 'yes';
				}
				else if(parentStyle[1]=='Completed'){
					parentDeactiveFlag = 'yes';
				}
				else if(parentStyle[1]=='Publish_To_Web'){
					parentDeactiveFlag='yes';
				}
				else if(parentStyle[1]=='Waiting_To_Be_Closed'){
					parentDeactiveFlag='yes';
				}else if(parentStyle[1]=='In_Progress'){
					parentDeactiveFlag='yes';
				}
				
		
			});
			
			$.each($("input[name='selectedStyles']:checked"), function(){
				childStyleColor = [];			
				childStyleColor = $(this).val().split('_');
				if(childStyleColor.length > 2){
					childStyleColor[1] = childStyleColor.slice(1, childStyleColor.length).join('_');
				}
				
				if(childStyleColor[1]=='Initiated'){
					activeFlag='yes';
				}
				else if(childStyleColor[1]=='Deactivated'){
					deactiveFlag = 'yes';
				}
				else if(childStyleColor[1]=='Closed'){
					deactiveFlag = 'yes';
				}
				else if(childStyleColor[1]=='Completed'){
					deactiveFlag = 'yes';
				}
				else if(childStyleColor[1]=='Publish_To_Web'){
					deactiveFlag='yes';
				}
				else if(childStyleColor[1]=='Waiting_To_Be_Closed'){
					deactiveFlag='yes';
				}
			});			
			
			
				if(parentDeactiveFlag == 'yes'){
				
				
				if(activeFlag == 'yes'){
					flag = 'no';
				}
				else if(activeFlag == 'no' && deactiveFlag == 'yes'){
					flag = 'yes';
				}
				else if(activeFlag == 'no' && deactiveFlag == 'no' && parentDeactiveFlag == 'yes' ){
					flag = 'yes';
				}
				}
				else{
					if(parentActiveFlag == 'no' && parentDeactiveFlag == 'no'){
							if(activeFlag == 'yes'){
								flag = 'no';
							}
							else if(activeFlag == 'no' && deactiveFlag == 'yes'){
								flag = 'yes';
							}
					}
					else{
						flag = 'no';
					}
				}
			
			
			
			
			if(flag == 'yes'){
			//$("#dialog_ASearch").css("display","none");	
			//$("#overlay_pageLoading").show();
			$("#overlay_pageLoading1").show();
			var searchResultValue = "";
			
			if($("#groupingID").val() == "" && $("#groupingName").val() == ""){
				searchResultValue = "";
			}else{
				searchResultValue = "includeGrps";
			}
			$.get(url,{styleItem: activateOrinValue,statusParam: 'activate', groupSearchResult:searchResultValue},function(responseText) {					
				if('false'== $("#searchReturnId").val()){
					//alert('not come from search Activate');
					document.getElementById('workListDisplayForm').submit();//Default Load Page submission
				}else if('true'== $("#searchReturnId").val()) {
					//alert('come from search Activate');
					//document.getElementById('petInActive').checked = true;					
					searchSearch();
					//$("#deptNo").val("");
					//$("#deptNo").val(deptNo);
				}				
			
			});
			//$("#overlay_pageLoading").hide();
			$("#overlay_pageLoading1").hide();
		}else if(flag == 'no'){
				//alert('no flag in Activate');
				//document.getElementById('petStatusID').innerHTML = 'Pet Status';
				//$("#overlay_petStatus").show();
				//$("#dialog_petStatus").show();
				$("#petStatusLabelId").html("Selected PET/PETs are already active. Please select inactive PET.");
				$('#dialog_petStatus').dialog('option', 'title', 'Pet Status');
				$('#dialog_petStatus').dialog('open');
			}
	}
}

/** Modified For PIM Phase 2 
	* - new method for styles under a group 
	* - works to select immeadiate child color orins
	* Date: 06/16/2016
	* Modified By: Cognizant
*/
		
function selectChildStylesUnderGroup(elem, parentOrinNo){
	var checkedStyle = $("#mainPetTable").find("[parentorinno ='"+parentOrinNo+"']");
	if($(elem).is(':checked')){
		checkedStyle = $("#mainPetTable").find("[parentorinno ='"+parentOrinNo+"']");
		$(checkedStyle).each(function() {
			if(elem.checked){
				this.checked = true;
			}else{
				this.checked = false;
			}			               
		});
	}else{
		$(checkedStyle).each(function() {
				if(elem.checked){
					this.checked = true;
				}else{
					this.checked = false;
				}			               
		});
	}
}

/*This method is responsible for passing values of parent child orin selection */
function childCheckedRows(elem, parentOrinNo, searchClicked, isGroup){
	var checkedStyle = $("#mainPetTable").find("[parentorinno ='"+parentOrinNo+"']");
	
	if(checkedStyle.length <=0){
		if($(elem).is(':checked')){
			$("#callType").val("getChildData");
			if("yes" == searchClicked){//Call child for Advance search query
				searchSearchForChild(parentOrinNo, false, isGroup);
			}else{
				getChildData(parentOrinNo, false, isGroup);
			}
			
			$( document ).ajaxStop(function() {
				checkedStyle = $("#mainPetTable").find("[parentorinno ='"+parentOrinNo+"']");
				$(checkedStyle).each(function() {
					if(elem.checked){
						this.checked = true;
					}else{
						this.checked = false;
					}			               
				});
			});
		}
	}else{
		$(checkedStyle).each(function() {
				if(elem.checked){
					this.checked = true;
				}else{
					this.checked = false;
				}			               
		});
	}	
	
	$("#callType").val("");
}

function getPetStatValue(petstatusValue){
	document.getElementById('stylepetstatid').value = petstatusValue;
}

function getStyleColorPetStatValue(chilepetstatusValue){
	document.getElementById('stylecolorpetstatid').value = chilepetstatusValue;
}


function setHiddenFieldValue(){
	document.getElementById('searchReturnId').value = 'true';
}

function formatDateNew(cdate) {
    var d = new Date(cdate),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    return [year, month, day].join('-');
}

var date_sort_asc = function (date1, date2) {
	 
	  if (date1 > date2) return 1;
	  if (date1 < date2) return -1;
	  return 0;
	};

// new requirement
function checkcompletiondate(dtElm, count){	
	var completionDate="";
		if(dtElm.value.trim().length>0){
		completionDate =dtElm.value;
		
		$("#tbCompletionDate"+count).val(completionDate);
		var completionDateBackupvar='tbCompletionDate'+count;
		var orinNumberVar = 'tbOrinNumber'+count; 
		var styleOrinNumberVar = 'tbStyleOrinNumber'+count; 		
		var completionDateBackup = document.getElementById(completionDateBackupvar).value;
		
		var orinNumber = document.getElementById(orinNumberVar).value;
		
		var styleorinNumber = document.getElementById(styleOrinNumberVar).value;
		
		var childClass = "."+orinNumber+"_child";
		var childDateArray = [];
		$(childClass).each(function() {
		if($(this).val() != '' && $(this).val() != null){
			 var d = new Date($( this ).val());
			 var d1 = new Date(d.getTime() + d.getTimezoneOffset()*60000);
			childDateArray.push(d1);
			}
		});
		childDateArray.sort(date_sort_asc);
		
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
			$("#tCompletionDate").val(completionDateBackup);			
			//$("#overlay_petStatus").show();
			//$("#dialog_petStatus").show();			
			//document.getElementById('petStatusID').innerHTML = 'Completion Date Update Status';			
			$("#petStatusLabelId").html('Please enter completion date in yyyy-mm-dd format.');
			
			$('#dialog_petStatus').dialog('option', 'title', 'Completion Date Update Status');
			$('#dialog_petStatus').dialog('open');
		}else{

				var newCompletionDate = new Date(completionDate);
				//var newCompletionDate = new Date(completionDateBackupTmp);
				
				var parentCompletionDateVal = document.getElementById(orinNumber+'_CmpDate').value ;
				var parentCompletionDateNullValue = document.getElementById('tbisPCompletionDateNull_'+orinNumber).value;
				var parentCompletionDate = '';
				if(parentCompletionDateVal.trim().length != 0){
					parentCompletionDate = new Date(parentCompletionDateVal);
				}
				var isCmpDateEarlier = 'Y';
				
				if(parentCompletionDate > newCompletionDate){
						isCmpDateEarlier = 'Y';
				
				}else if(parentCompletionDate.length == 0){
					isCmpDateEarlier = 'Y';
				}
				parentCompletionDateVal = formatDateNew(childDateArray[0]);
				isCmpDateEarlier = '';
				parentCompletionDateNullValue = 'Y';
				var todayDate = new Date();
				if(newCompletionDate > todayDate){					
					invokeAjaxCallForCompletionDate(completionDate, completionDateBackup, orinNumber, styleorinNumber,isCmpDateEarlier,parentCompletionDateNullValue,parentCompletionDateVal);
				}else{
					
					$(dtElm).val(completionDateBackup);
					
					//$("#overlay_petStatus").show();
					//$("#dialog_petStatus").show();			
					//document.getElementById('petStatusID').innerHTML = 'Completion Date Update Status';			
					$("#petStatusLabelId").html('Date invalid - please enter a future date.');
					$('#dialog_petStatus').dialog('option', 'title', 'Completion Date Update Status');
					$('#dialog_petStatus').dialog('open');
				}
				
		}
	}else {
					//$("#overlay_petStatus").show();
					//$("#dialog_petStatus").show();			
					//document.getElementById('petStatusID').innerHTML = 'Completion Date Update Status';			
					$("#petStatusLabelId").html('Please enter the completion date.');
					$('#dialog_petStatus').dialog('option', 'title', 'Completion Date Update Status');
					$('#dialog_petStatus').dialog('open');
	}
	
	
	
	}

function clickcompletiondate(count){	
	var completionDateBackup='tbCompletionDate'+count;
	//tbCompletionDate${subcount}
	
}

function invokeAjaxCallForCompletionDate(completionDate, completionDateBackup, orinNumber, styleorinNumber,isCmpDateEarlier,parentCompletionDateNullValue,parentCompletionDateVal){	
var url = $("#ajaxaction").val();
$.get(url,{completionDate:completionDate,orinNumber:orinNumber,styleorinNumber:styleorinNumber,isCmpDateEarlier:isCmpDateEarlier,parentCompletionDateNullValue:parentCompletionDateNullValue,parentCompletionDateVal:parentCompletionDateVal},function(responseText) {	
       responseText = repalcePetTable(responseText);
       $("#petTable").html(responseText);  
		
		/**
		* commented and made inactive after js optimization and cleanup
		*/
		/* $('.tree').treegrid();
			$('.tree2').treegrid({
				expanderExpandedClass: 'icon-minus-sign',
				expanderCollapsedClass: 'icon-plus-sign'
			});
		*/	
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
			var showDateUpdate= document.getElementById("updateDateMessage").value;			
			//$("#overlay_petStatus").show();
			//$("#dialog_petStatus").show();			
			//document.getElementById('petStatusID').innerHTML = 'Completion Date Update Status';			
			$("#petStatusLabelId").html(showDateUpdate);
			$('#dialog_petStatus').dialog('option', 'title', 'Completion Date Update Status');
			$('#dialog_petStatus').dialog('open');
			
   });
}

function disableStyleOrin(elm){
	if(elm == 'yes'){
		document.getElementById('advOrin').value = '';
		document.getElementById('advOrin').disabled = true;
	}
}

function enableDisableVendorStyle(orinId,vendorNumberId,upcId){
	var orinIdVal = document.getElementById(orinId).value ;	
	var upcIdVal = document.getElementById(upcId).value ;	
	if(orinIdVal.trim().length > 0 && upcIdVal.trim().length > 0){
		var ven1 = upcIdVal.substring(0,2);
		var ven2 = upcIdVal.substring(0,1);		
		if(ven1 == '04'){			
			document.getElementById(vendorNumberId).value = '';
			document.getElementById(vendorNumberId).disabled = true;
		}
		if(ven2 == '4'){			
			document.getElementById(vendorNumberId).value = '';
			document.getElementById(vendorNumberId).disabled = true;
		}
	}else{
		document.getElementById(vendorNumberId).disabled= false;
	}
}

//Fix for 580  
$(document).keypress(function (e) {
var inputDept = document.getElementById('selectedDeptSearch').value;
    if (e.which == 13) {
	var isDeptSearchVisible = $("#overlay_Dept").css("display");
	var isAdvDeptSearchVisible = $("#dialog_ASearch").css("display");;	
	
	//$("#dialog_Dept").css("display","block");
	if('block' == isDeptSearchVisible){		
		depSearch('depSearch');
	}else if('block' == isAdvDeptSearchVisible){
		searchSearch();
		setHiddenFieldValue();
	}
	
  }
});





function populateChildData(jsonArray, orinNum, showHideFlag, isGroup, uniqueIdentifier){
	//console.log(isGroup);
	
	var mainTb = document.getElementById('mainPetTable');
	var parentTr = document.getElementById('parent_'+orinNum);
	var templateTr = document.getElementById('CHILDTR_ID');
	var displayChild = $(parentTr).attr('displayChild');
	var hidden_roleEditable = $("#hidden_roleEditable").val();
	var hidden_readOnlyUser = $("#hidden_readOnlyUser").val();
	var hidden_roleName = $("#hidden_roleName").val();
	
	if($('tr[name=child_' + orinNum + ']').length > 0){
		$(parentTr).attr('displayChild','Y');
		$('tr[name=child_' + orinNum + ']').each(function(index){
			$(this).removeAttr('style');
		});
		
		return;
	}
	
	if(isGroup !== undefined && isGroup == 'Y'){
		var template = $('#group_items_template').length ? _.template($('#group_items_template').html(), null,  {
			interpolate :  /\{\{\=(.+?)\}\}/g,
			evaluate: /\{\{(.+?)\}\}/g
		}) : null;
		
		var html = template({data: jsonArray, orinNum: orinNum, showHideFlag: showHideFlag, uniqueIdentifier: uniqueIdentifier});
		
		//console.log(html);
		
		if(showHideFlag !== undefined && showHideFlag == false){
			$(parentTr).attr('displayChild','N');
		}else
			$(parentTr).attr('displayChild','Y');
		
		//$(tempTr).html(tempHtml);
		$(html).insertAfter($(parentTr));
		
	}else{
			$(jsonArray).each(function(i,val){
			var tempTr = document.createElement('tr');
			$(tempTr).attr('name','child_'+orinNum);
			
			var tempHtml = '';
			
			if(showHideFlag !== undefined && showHideFlag == false){
				$(tempTr).css({display: 'none'});
				$(parentTr).attr('displayChild','N');
			}else
				$(parentTr).attr('displayChild','Y');
			
			/**
			* code to handle no child message and display it accordingly
			*/
			if(val.noChildMessage){
				tempHtml = ''
					+ '<td>&nbsp;</td>'
					+ '<td colspan="11"><strong>' + val.noChildMessage +  '</strong></td>';
			}else{
				tempHtml = templateTr.innerHTML;
				
				$(tempTr).attr('id','child_'+val.styleOrinNum);
						
				tempHtml = tempHtml.replace("#CHBOX_PARENTORIN", orinNum);
				tempHtml = tempHtml.replace("#CH_STYLE_ORIN", val.styleOrinNum);
				tempHtml = tempHtml.replace("#CH_STYLE_PETSTATUS", val.petStatus);
				tempHtml = tempHtml.replace("#CHBOXONCLK_PETSTATUS", val.petStatus);
				
				tempHtml = tempHtml.replace(/#TD_ORIN/g, val.styleOrinNum); 
				tempHtml = tempHtml.replace("#TD_DEPT_NUM", val.deptId); 
				tempHtml = tempHtml.replace("#TD_VENDOR_NAME", val.vendorName); 
				
				tempHtml = tempHtml.replace("#STYLE_ORIN_NUMBER", val.styleOrinNum); 
				var vendorStyle = $("#"+orinNum+"_vendorStyle").val();
				if(vendorStyle){
					tempHtml = tempHtml.replace("#TD_VENDOR_STYLE", vendorStyle);
				}else{
					tempHtml = tempHtml.replace("#TD_VENDOR_STYLE", "");
				}
				
				var productName = $("#"+orinNum+"_productName").val();
				if(productName){
					tempHtml = tempHtml.replace("#TD_PRODUCT_NAME", productName);
				}else{
					tempHtml = tempHtml.replace("#TD_PRODUCT_NAME", "");
				}		
				

				/*<a href="#" onclick="contentStatus('#CONTENT_STATUS','#CON_PARENT_ORIN')">#TD_CONTENT_STATUS</a>		*/
				tempHtml = tempHtml.replace("#CONTENT_STATUS", val.contentStatus);
				tempHtml = tempHtml.replace("#CON_PARENT_ORIN", orinNum);
				tempHtml = tempHtml.replace("#TD_CONTENT_STATUS", val.contentStatus);
				
				if("yes" == hidden_roleEditable || "yes" == hidden_readOnlyUser){
					tempHtml = tempHtml.replace("#CONTENT_DISPLAY_STATUS", "");
					tempHtml = tempHtml.replace("#TD_CONTENT_STATUS_NOTEDITABLE", "");
				}else{
					tempHtml = tempHtml.replace("#CONTENT_DISPLAY_STATUS", "none");
					tempHtml = tempHtml.replace("#TD_CONTENT_STATUS_NOTEDITABLE", val.contentStatus);
				}
				
				/*<a href="#" onclick="imageStatus('#IMAGE_STATUS','#PARENT_ORIN','#PET_STATUS')">#TD_IMAGE_STATUS</a>*/
				
				tempHtml = tempHtml.replace("#TD_IMAGE_STATUS", val.imageStatus);
				tempHtml = tempHtml.replace("#IMAGE_STATUS", val.imageStatus);
				tempHtml = tempHtml.replace("#IMG_PARENT_ORIN", orinNum);
				tempHtml = tempHtml.replace("#PET_STATUS", val.petStatus);
				
				if("yes" == hidden_roleEditable || "yes" == hidden_readOnlyUser){
					tempHtml = tempHtml.replace("#IMAGE_DISPLAY_STATUS", "");
					tempHtml = tempHtml.replace("#TD_IMAGE_STATUS_NOTEDITABLE", "");
				}else{
					tempHtml = tempHtml.replace("#IMAGE_DISPLAY_STATUS", "none");
					tempHtml = tempHtml.replace("#TD_IMAGE_STATUS_NOTEDITABLE", val.imageStatus);
				}
				
				tempHtml = tempHtml.replace("#TD_COMPLETION_DATE", val.completionDate);
				
				var random_subcount = ""+Math.random();
				random_subcount = random_subcount.substring(2,10)	;
				
				tempHtml = tempHtml.replace("#CMP_TXT_CMP_DT", val.completionDate);
				tempHtml = tempHtml.replace("#SUBCOUNT_RANDOM", random_subcount);
				tempHtml = tempHtml.replace("#SUBCOUNT_RANDOM", random_subcount);
				tempHtml = tempHtml.replace("#SUBCOUNT_RANDOM", random_subcount);
				tempHtml = tempHtml.replace("#SUBCOUNT_RANDOM", random_subcount);
				tempHtml = tempHtml.replace("#SUBCOUNT_RANDOM", random_subcount);
				tempHtml = tempHtml.replace("#SUBCOUNT_RANDOM", random_subcount);
				tempHtml = tempHtml.replace("#SUBCOUNT_RANDOM", random_subcount);
				tempHtml = tempHtml.replace("#SUBCOUNT_RANDOM", random_subcount);
				tempHtml = tempHtml.replace("#SUBCOUNT_RANDOM", random_subcount);		
				
				//tempHtml = tempHtml.replace("#CMP_INPUT_HIDDEN_ID_SUBCOUNT", random_subcount);
				//tempHtml = tempHtml.replace("#CMP_INPUT_HIDDEN_NAME_SUBCOUNT", random_subcount);
				tempHtml = tempHtml.replace("#CMP_HIDDEN_STYLE_CMP_DT", val.completionDate);
				tempHtml = tempHtml.replace("#CMP_PARENT_ORIN", orinNum);
				//tempHtml = tempHtml.replace("#CMP_HIDDEN_ID_SUBCOUNT", random_subcount);
				//tempHtml = tempHtml.replace("#CMP_HIDDEN_NAME_SUBCOUNT", random_subcount);
				tempHtml = tempHtml.replace("#CMP_HIDDEN_PARENT_ORIN", orinNum);
				//tempHtml = tempHtml.replace("#CMP_HIDDENSTYLE_ID_SUBCOUNT", random_subcount);
				//tempHtml = tempHtml.replace("#CMP_HIDDENSTYLE_NAME_SUBCOUNT", random_subcount);
				tempHtml = tempHtml.replace("#CMP_HIDDEN_STYLE_ORIN", val.styleOrinNum);
				
				
				//<c:when test="${workflowForm.roleName =='dca' &&  style.petStatus == 'Initiated' && workflowForm.readOnlyUser !='yes' }" >
				//var chldOrinNo = val.styleOrinNum.replace('/','');
				var chldOrinNo = val.styleOrinNum ? val.styleOrinNum.split(' ') : null;
				chldOrinNo = chldOrinNo ? chldOrinNo.join('') : '';
				
				chldOrinNo = chldOrinNo.length >=12 ? chldOrinNo.substring(0, 12) : chldOrinNo;
				
				tempHtml = tempHtml.replace("editable-cc-date-#CON_CHILD_ORIN", "editable-cc-date-"+ chldOrinNo);
				tempHtml = tempHtml.replace("readonly-cc-date-#CON_CHILD_ORIN", "readonly-cc-date-"+ chldOrinNo);
				
				if("dca" == hidden_roleName && "Initiated" == val.petStatus && "yes" != hidden_readOnlyUser){
					setTimeout(function(){
						$('.editable-cc-date-' + chldOrinNo).css({display: 'inline'});
						$('.readonly-cc-date-' + chldOrinNo).css({display: 'none'});
					}, 3);
					
				}else if("Deactivated" != val.petStatus){
					setTimeout(function(){
						$('.editable-cc-date-' + chldOrinNo).css({display: 'none'});
						$('.readonly-cc-date-' + chldOrinNo).css({display: 'inline'});
					}, 3);
				}else{
					 if("Deactivated" == val.petStatus){
						setTimeout(function(){
							$('.readonly-cc-date-' + chldOrinNo).css({display: 'none'});
							$('.editable-cc-date-' + chldOrinNo).css({display: 'none'});
						}, 3); 
					 }else{
						setTimeout(function(){
							$('.readonly-cc-date-' + chldOrinNo).css({display: 'none'});
						}, 3); 
					 }
					
				}
			
				tempHtml = tempHtml.replace("#TD_SOURCE_TYPE", val.petSourceType);
				
				tempHtml = tempHtml.replace("#TD_PET_STATUS", val.petStatus);
				
				/** Modified For PIM Phase 2 
					* - adding group flag for child item
					* Date: 06/14/2016
					* Modified By: Cognizant
				*/
				
				tempHtml = tempHtml.replace("#GROUP_FLAG", val.isGroup);
				
				if("dca" == hidden_roleName && "Completed" != val.imageStatus && "yes" != hidden_readOnlyUser){
					if("StyleColor" == val.ENTRY_TYPE || "BCG" == val.ENTRY_TYPE || "RCG" == val.ENTRY_TYPE || "GSG" == val.ENTRY_TYPE)
					{
						tempHtml = tempHtml.replace("#ASSET_STYLE_STATUS", 'block');
						if(""!=val.missingAsset)
						{
							if("Sample"==val.missingAsset)
							{
								///#TD_ORIN/g
								tempHtml = tempHtml.replace(/#ASSET_SAMPLE/ig, "selected");
								tempHtml = tempHtml.replace(/#ASSET_CLEAR/ig, "");
								tempHtml = tempHtml.replace(/#ASSET_IMAGE/ig, "");
							}
							if("Image"==val.missingAsset)
							{
								tempHtml = tempHtml.replace(/#ASSET_SAMPLE/ig, "");
								tempHtml = tempHtml.replace(/#ASSET_CLEAR/ig, "");
								tempHtml = tempHtml.replace(/#ASSET_IMAGE/ig, "selected");
							}
							if("Clear"==val.missingAsset)
							{
								tempHtml = tempHtml.replace(/#ASSET_SAMPLE/ig, "");
								tempHtml = tempHtml.replace(/#ASSET_CLEAR/ig, "selected");
								tempHtml = tempHtml.replace(/#ASSET_IMAGE/ig, "");
							}
						}
					}
					else
					{
						tempHtml = tempHtml.replace("#ASSET_STYLE_STATUS", 'none');
					}
				}
				else
				{
					tempHtml = tempHtml.replace("#ASSET_STYLE_STATUS", 'none');
				}
				
			}
			
			$(tempTr).html(tempHtml);
			$(tempTr).insertAfter($(parentTr));
			
			setTimeout(function(){
			//logic to set the child element already selected if the parent is previously selected
		
			if($(parentTr).find('input[type=checkbox]').is(':checked')){
				$('tr[name=child_' + orinNum + ']').find('input[type=checkbox]').attr('checked', 'checked');
			}else{
				
				//$(templateTr).find('input[type=checkbox]').prop('checked', false);
				$('tr[name=child_' + orinNum + ']').find('input[type=checkbox]').removeAttr('checked');
			}
				
			
			}, 20);
			
		});
		
		//$(parentTr).attr('displayChild','Y');
		$(parentTr).attr('dataRetrieved','Y');
		//expandCollapse(orinNum);
	}
}

function expandCollapse(orinNum, searchClicked, isGroup, uniqueIdentifier, parentGroupId){
	/** Modified For PIM Phase 2 
	* - change to make multiple parent child unique as 
	* - it is not valid as same orin can be in many places
	* Date: 06/16/2016
	* Modified By: Cognizant
	*/
	
	var parentTr, displayChild, expand, collapsed;
	
	if(uniqueIdentifier !== undefined && uniqueIdentifier != ''){
		parentTr = $('tr[rel=parent_' + orinNum + '_' + uniqueIdentifier + ']');
		displayChild = parentTr.attr('displayChild');
		expand = $('img[rel=parentSpan_' + orinNum + '_' + uniqueIdentifier + '_expand]');
		collapsed = $('img[rel=parentSpan_' + orinNum + '_' + uniqueIdentifier + '_collapsed]');
	}else{
		parentTr = $('#parent_'+orinNum);
		displayChild = parentTr.attr('displayChild');
		//var span_Parent = $("#parentSpan_"+orinNum);
		expand = $("#parentSpan_"+orinNum+'_expand');
		collapsed = $("#parentSpan_"+orinNum+'_collapsed');
		//var searchClicked = $("#searchClicked").val();
		//var searchClicked = document.getElementById("searchClicked").value;
	}
	
	
	
	if('Y' == displayChild){
		
		
		/** Modified For PIM Phase 2 
		* - change to remove group level children
		* Date: 06/08/2016
		* Modified By: Cognizant
		*/
		
		//getting all child orin spawned from this root parent orin
		var childOrins = [];
		$("tr[data-tr-root=" + orinNum + "]").each(function(i){
			childOrins.push($(this).attr('id').split('_')[1]);
		});
		
		//console.log(childOrins);
		//removing parent item and its direct associated children
		$("tr[data-tr-root=" + orinNum + "]").remove();
		
		//removing all children childre spawned from deferred call
		childOrins.forEach(function(i){ //asuming latest browsers and specially chrome
			$('tr[data-tr-root=' + i + ']').remove();
		});
		
		
		var childElms = null;
		
		if(uniqueIdentifier != undefined && uniqueIdentifier != '')
			childElms = $('tr[rel=parent_' + orinNum + '_' + uniqueIdentifier + ']');
		else
			childElms = $("tr[name='child_"+orinNum+"'] ");
		
		$(childElms).each(function(i,elm){
			$(elm).remove();
		});
		
		
		
		//$(parentTr).attr('displayChild','N');
		parentTr.attr('displayChild','N');
		//$(parentTr).find('input[type=checkbox]').prop('checked', false);
		parentTr.find('input[type=checkbox]').prop('checked', false);
		//$(span_Parent).html('Expand');
		$(expand).show();
		$(collapsed).hide();
	}else{
		//$("#callType").val("getChildData");
		$("#callType").val((isGroup !== undefined && isGroup.trim().length) ? 'getChildgroup' : 'getChildData');
		
		if("yes" == searchClicked){//Call child for Advance search query
			if(uniqueIdentifier != undefined && uniqueIdentifier != '')
				searchSearchForChild(orinNum, true, isGroup, uniqueIdentifier, parentGroupId);
			else
				searchSearchForChild(orinNum, true, isGroup, undefined, parentGroupId);
			$(expand).hide();
			$(collapsed).show();
		}else{
			if(uniqueIdentifier != undefined && uniqueIdentifier != '')
				getChildData(orinNum, true, isGroup, uniqueIdentifier, parentGroupId);
			else
				getChildData(orinNum, true, isGroup, undefined, parentGroupId);
			
			$(expand).hide();
			$(collapsed).show();
		}
		//$(span_Parent).html('Collapse');
	}
	$("#callType").val("");
	return false;
}

/** Added for flag Missing Asset 
 * @param sel
 * @param orinNum
 * @returns
 */
function onChangeMissingAsset(sel, orinNum){
	var missingAssetVal = sel.value;
	var url = $("#ajaxaction").val();
	var orinNumber = orinNum;
	
	if(sel==null || orinNumber==null)
	{
		return;
	}

   $.ajax({
	         url: url ,
	         type: 'GET',
	         datatype:'json',
	         data: { 		 missingAsset:missingAssetVal,orinNum:orinNumber,
				   },
		         success: function(data){
						return;
					}
		 });	
}

/** Modified For PIM Phase 2 
* - new funtion to expand style colors under style when a group is expanded 
* Date: 06/07/2016
* Modified By: Cognizant
*/

function expandStyleColorCollapse(orin, uniqueIdentifier){
	var parentTr = $('tr#parent_' + orin + '_' + uniqueIdentifier);
	if(parentTr.attr('displayChild') == 'Y'){
		$('tr[name=child_' + orin + '_' + uniqueIdentifier + ']').css({display: "none"});
		$('#parentSpan_' + orin + '_' + uniqueIdentifier + '_expand').show();
		$('#parentSpan_' + orin + '_' + uniqueIdentifier + '_collapsed').hide();
		parentTr.attr('displayChild', 'N');
	}else{
		$('tr[name=child_' + orin + '_' + uniqueIdentifier + ']').css({display: "table-row"});
		parentTr.attr('displayChild', 'Y');
		$('#parentSpan_' + orin + '_' + uniqueIdentifier + '_expand').hide();
		$('#parentSpan_' + orin + '_' + uniqueIdentifier + '_collapsed').show();
	}
}


function searchSearchForChild(parentOrin, showHideFlag, isGroup, uniqueIdentifier, parentGroupId)
{	
	
	document.getElementById("searchClicked").value = 'yes';
	var searchClicked = document.getElementById("searchClicked").value ;
	var callType = $("#callType").val();
	
	var advSelectedDepartments="";
	if( $("#adDeptNo").val().trim().length>0 ){
		advSelectedDepartments = $("#adDeptNo").val().trim();
	}
	
	
	/*574 to populate dept in dept filter after hitting search*/
	$("#deptNo").val(advSelectedDepartments);
	
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
	
	var contentStatus="";
	if($("#advContentStatus").val().trim().length>0){
		contentStatus =$("#advContentStatus").val().trim();
	}
	
	
	var petStatus = '';
	var temp = $('input:radio[name=petActive]:checked').val();
	
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
	
	var orinNumber="";
	if($("#advOrin").val().trim().length>0){
		orinNumber =$("#advOrin").val().trim();
	}
	
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
	
	var upc="";
	if($("#advUpc").val().trim().length>0){
		upc =$("#advUpc").val().trim();
	}
	
	var classNumber="";
		if($("#advClassNumber").val().trim().length>0){
		classNumber =$("#advClassNumber").val().trim();
	}
	
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
	 
	  /** Modified For PIM Phase 2 
		* - getting call type either to include groupings or pets only 
		* - added additional params for groups if applicable
		* Date: 06/08/2016
		* Modified By: Cognizant
	*/
	
	var searchResults = $('[name=searchResults]:checked').val() || '';
	var groupingID = $('#groupingID').val();
	var groupingName = $('#groupingName').val();
	 
	 if($('tr[name=child_' + parentOrin + ']').length <= 0){
		$("#overlay_pageLoading").show();
       $.ajax({
		         url: url ,
		         type: 'GET',
		         datatype:'json',
		         data: { 		 advSearchOperation:'searchSearch',
								 orinNum: (isGroup === undefined || isGroup != 'Y') ? parentOrin: '',
								 callType: callType,	
								 searchClicked:searchClicked,
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
				                 vendorNumber:vendorNumber,
								/** Modified For PIM Phase 2 
								* - parsing response and extracting json from html 
								* - for group enabled search result and worklist
								* Date: 06/07/2016
								* Modified By: Cognizant
								*/
								searchResults:searchResults,
								groupID: isGroup == 'Y' ? parentOrin: '',
								groupingName: groupingName,
								parentGroupId: parentGroupId,
					   },
			         success: function(data){
						/** Modified For PIM Phase 2 
						* - parsing response and extracting json from html 
						* - for group enabled search result and worklist
						* Date: 06/07/2016
						* Modified By: Cognizant
						*/
						//var myString = data.substr(data.indexOf("[{") , data.indexOf("}]")+2);
						var myString = data.substr(data.indexOf('STARTJSON') + 'STARTJSON'.length , (data.indexOf('ENDJSON') - 'ENDJSON'.length)-2);
						var json = $.parseJSON(myString);  
						populateChildData(json,parentOrin, showHideFlag, isGroup, uniqueIdentifier);
						
						$("#overlay_pageLoading").hide();
						
				}//End of Success				   
        });
	 }else{
		populateChildData([],parentOrin, showHideFlag, isGroup, uniqueIdentifier); 
	 }
 
}