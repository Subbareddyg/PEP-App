/*
 * belk.cars.searchDialog.js
 * Belk  searchDialog javascript
 */

var context='/cars/';
var three ="";



   $(document).ready(function ()
   {
       
      $("#btnAdvSearch").click(function (e)
      {
         ShowDialogSearch(false);
         e.preventDefault();
      });
 	$("#btnShowDept").click(function (e)
      {
         ShowDept(false);
         e.preventDefault();
      });

	$(".btnShowImageAction").click(function ()
      {
	
         ShowImageAction(false);
         //e.preventDefault();
      });
	  


$("#btnImageUploadAction").click(function (e)
     {

	
	document.getElementById('selectedColorOrin').value = document.getElementById('selectedOrinVPI').value;
	document.getElementById('supplierIdVPI').value = document.getElementById('supplierId').value;
	document.getElementById('vendorStyle#').value =document.getElementById('vendorStyle').value;
	document.getElementById('colorCode').value = document.getElementById('selectedVendorColorCode').value || '';     
	ShowImageUploadPopUp(true);
     e.preventDefault();
 });


$("#btnGPImageUploadAction").click(function (e)
     {
	  document.getElementById('groupingIdSel').value = $("#groupingId").val();
  	 document.getElementById('groupType').value = $("#groupingType").val();
	     
	ShowGpImageUploadPopUp(false);
     e.preventDefault();
 });
 
 
 $("#btn_image_sample").click(function (e)
      {
	
	  // $(".tree2 tr").click(function() {
	
	 // $(this).css('background-color','#DCE2EC')
	alert("You clicked my <td>!" + $(this).html());
       
        //get <td> element values here!!??
   // });
		 
        // ShowImageUploadPopUp(false);
         e.preventDefault();
 });


 

$("#btnImageUploadPopUpClose").click(function (e)
      {
		 
         HideImageUploadPopUp(false);
         e.preventDefault();
 }); 
 
 $("#btnImageADRequestAction").click(function (e)
      {
		 
         ShowImageADRequestPopUp(false);
         e.preventDefault();
 });	

  $("#btnImageADRequestClose").click(function (e)
      {
        HideShowImageADRequestPopUp();
         e.preventDefault();
      });
	  
 $("#btnSampleCoordinatorNotePopUp").click(function (e)
      {
		 
         SampleCoordinatorNotePopUp(false);
         e.preventDefault();
 });	

  $("#btnImageADRequestClose").click(function (e)
      {
        HideShowImageADRequestPopUp();
         e.preventDefault();
      });
	  
	  
	  
	  
	  
	  
	
	$("#btnShowReqType").click(function (e)
      {
		ShowDialogReqType(false);
        e.preventDefault();
      });
	  
 	$("#btnClassNo").click(function (e)
      {
         ShowClassNo(false);
         e.preventDefault();
      });
	  
	  $("#btnVendNo").click(function (e)
      {
         ShowDialogVendor(false);
         e.preventDefault();
      });
   
      $("#btnShowSimple").click(function (e)
      {
         ShowDialog(false);
         e.preventDefault();
      });
	  $("#btnShowSimple1").click(function (e)
      {
         ShowDialogVendor(false);
         e.preventDefault();
      });
	  $("#btnShowSimple2").click(function (e)
      {
         ShowDialogUPC(false);
         e.preventDefault();
      });
	  $("#btnShowSimple3").click(function (e)
      {
         ShowDialog(false);
         e.preventDefault();
      });
	  $("#btnClassNumber").click(function (e)
      {
         ShowDialogClassNumber(false);
         e.preventDefault();
      });
      $("#btnShowSimpleImage").click(function (e)
      {
         ShowDialogImage(false);
         e.preventDefault();
      });
	$("#btnShowSimpleContent").click(function (e)
      {
         ShowDialogContent(false);
         e.preventDefault();
      });	 	
	
      $("#btnShowModal").click(function (e)
      {
         ShowDialog(true);
         e.preventDefault();
      });

      $("#btnClose").click(function (e)
      {
         HideDialog();
         e.preventDefault();
      });
	 $("#btnClose1").click(function (e)
      {
         HideDialog();
         e.preventDefault();
      });
	  
      $("#btnSubmit").click(function (e)
      {
         var brand = $("#brands input:radio:checked").val();
       
         HideDialog();
         e.preventDefault();
      });

   $("input[type='radio']").click(function(){
     
        $("input[type='radio']").prop('checked', false);
        $(this).prop('checked', true);
   
	});
   // });
	
   $('#btnSave').on('click', function () {
	
 	$('[name=chkSelection]:checked').each(function () { 		
        	
 	var one = $(this).parent().siblings('td').eq(0).text();
       	var two = $(this).parent().siblings('td').eq(1).text();
        three = $(this).parent().siblings('td').eq(2).text();
        $("#ORIN").val(two);
        HideDialog();
         //e.preventDefault();

   	 	});
	$("input[name='chkSelection']").prop('checked', false);

 });
 
 	  
	  
	    
	  
 $('#btnSaveClassNumber').on('click', function () {
	var selectedImage='';
 	$('[name=chkSelectionClassNo]:checked').each(function () { 
	if(selectedImage!=""){ 
     selectedImage+=","+$(this).parent().siblings('td').eq(0).text();
	}else{
     selectedImage=$(this).parent().siblings('td').eq(0).text();
	}                  
	 $("#ClassNumber").val(selectedImage);
	  $("#ClassNumber").attr("disabled","true");
         HideDialogClassNumber();
         //e.preventDefault();
   	 });
	//$("input[name='chkSelectionClassNo']").prop('checked', false);

 });
 
 $("#btnCloseClassNumber").click(function (e)
      {
         HideDialogClassNumber();
         e.preventDefault();
      });
	  
	  
$('#btnSaveDept').on('click', function () {	
	var selectedDept='';
 	$('[name=chkSelectedDept]:checked').each(function () { 
	if(selectedDept!=""){ 
     selectedDept+=","+$(this).parent().siblings('td').eq(0).text();
	}else{
     selectedDept=$(this).parent().siblings('td').eq(0).text();
	 }                  
	 $("#DeptNo").val(selectedDept);
	  $("#DeptNo").attr("disabled","true");	
          HideDept();
       
   	 });
 });
 
 
 $('#btnSelectedImageAction').on('click', function () { 
	var selectedDept='';
 	$('[name=radionSelectedIAction]:checked').each(function () {	
	if(selectedDept!=""){ 
     selectedDept+=","+$(this).parent().siblings('td').eq(0).text();
	}else{
     selectedDept=$(this).parent().siblings('td').eq(0).text();
	 }     
   	 });
	 HideImageAction();

 });
 
  $("#btnCloseImageAction").click(function (e)
      {
        HideImageAction();
         e.preventDefault();
      });

 
 $("#btnCloseDept").click(function (e)
      {
        HideDept();
         e.preventDefault();
      });
	
$('#btnSaveUPC').on('click', function () {
	
 	$('[name=chkSelectionUPC]:checked').each(function () {
        	
 	var one = $(this).parent().siblings('td').eq(0).text();
       	var two = $(this).parent().siblings('td').eq(1).text();       
        
	 $("#UPC").val(two);
         HideDialogUPC();
         //e.preventDefault();

   	 	});
	$("input[name='chkSelectionUPC']").prop('checked', false);

 });


 $("#btnCloseUPC").click(function (e)
      {
         HideDialogUPC();
         e.preventDefault();
      });

$('#btnSaveImage').on('click', function () {
	var selectedImage='';
 	$('[name=chkSelectionImage]:checked').each(function () { 


  if(selectedImage!=""){ 
     selectedImage+=","+$(this).parent().siblings('td').eq(0).text();
	}else{
     selectedImage=$(this).parent().siblings('td').eq(0).text();
	}    	
 	        
	 $("#imageStatus").val(selectedImage);
	 $("#imageStatus").attr("disabled","true");
         HideDialogImage();
         //e.preventDefault();

   	 	});
	//$("input[name='chkSelectionImage']").prop('checked', false);

 });


 $("#btnCloseImage").click(function (e)
      {
         HideDialogImage();
         e.preventDefault();
      });


$('#btnSaveContent').on('click', function () {	

var selectedContent='';
 	$('[name=chkSelectionContent]:checked').each(function () {		
        	
 	if(selectedContent!=""){ 
     selectedContent+=","+$(this).parent().siblings('td').eq(0).text();
	}else{
     selectedContent=$(this).parent().siblings('td').eq(0).text();
	} 	      
        
	 $("#contentStatus").val(selectedContent);
	  $("#contentStatus").attr("disabled","true");
         HideDialogContent();
         //e.preventDefault();

   	});
	//$("input[name='chkSelectionContent']").prop('checked', false);

 });


 $("#btnCloseContent").click(function (e)
      {
         HideDialogContent();
         e.preventDefault();
      });

$("#btnClearContent").click(function (e)
      {
	$('[name=chkSelectionContent]:checked').each(function () {
	$("input[name='chkSelectionContent']").prop('checked', false);
      });
});

$("#btnClearImage").click(function (e)
      {
	$('[name=chkSelectionImage]:checked').each(function () {

	$("input[name='chkSelectionImage']").prop('checked', false);
      });
});
$("#btnClearUPC").click(function (e)
      {
	$('[name=chkSelectionUPC]:checked').each(function () {
	$("input[name='chkSelectionUPC']").prop('checked', false);
      });
});


$("#btnClearDept").click(function (e)
      {
	$('[name=chkSelectedDept]:checked').each(function () {
	$("input[name='chkSelectedDept']").prop('checked', false);
      });
});
$("#btnCloseDept").click(function (e)
      {
	HideDept();
    e.preventDefault();
});

$('#btnSaveReqType').on('click', function () {	

var selectedReqType='';
 	$('[name=chkSelectionReqType]:checked').each(function () {		
        	
 	if(selectedReqType!=""){ 
     selectedReqType+=","+$(this).parent().siblings('td').eq(0).text();
	}else{
     selectedReqType=$(this).parent().siblings('td').eq(0).text();
	} 	      
        
	 $("#RequestType").val(selectedReqType);
	  $("#RequestType").attr("disabled","true");
         HideDialogReqType();
		 
         //e.preventDefault();

   	});
	//$("input[name='chkSelectionContent']").prop('checked', false);

 });


 $("#btnCloseReqType").click(function (e)
      {
         HideDialogReqType();
         e.preventDefault();
      });

$("#btnClearReqType").click(function (e)
      {
	$('[name=chkSelectionReqType]:checked').each(function () {
	$("input[name='chkSelectionReqType']").prop('checked', false);
      });
});

$('#btnSaveClassNo').on('click', function () {	

var selectedClassNo='';
 	$('[name=chkSelectionClassNo]:checked').each(function () {		
        	
 	if(selectedClassNo!=""){ 
     selectedClassNo+=","+$(this).parent().siblings('td').eq(0).text();
	}else{
     selectedClassNo=$(this).parent().siblings('td').eq(0).text();
	} 	      
        
	 $("#ClassNumber").val(selectedClassNo);
	  $("#ClassNumber").attr("disabled","true");
         HideDialogClassNo();	 
         //e.preventDefault();

   	});
	//$("input[name='chkSelectionContent']").prop('checked', false);

 });

 
  $("#addImageBtn").click(function (e)
      {
	
  
 var imageLocation=$('#imageLocationUpload :selected').val();

		   var selectImage = $('#cdImageFile').val();		  
		   var imagePathFTP = $("input#ftpUrl1").val();
		   var ftpPath = $("input#ftpPath").val();
		   var ftpFileName = $("input#ftpFileName").val();

		   var ftpUserId = $("input#ftpUserId").val();
		   var ftpPassword1 = $("input#ftpPassword1").val();
		  
		
		   alert('imageLocation' +imageLocation);



$.ajax({
            url : '..\removeSampleImageUrl',
            data : {
                //userName : $('#userName').val()
            },
            success : function(responseText) {
                $('#ajaxGetUserServletResponse').text(responseText);
            }
        });
		   
	
 });	

/* method for remove sample image */
function removeSampleImageref(rowId,removeImageUrl){	
	if(confirm("Are you sure to delete")){		
		$.ajax({
			type: 'POST',
			url : removeImageUrl,			
			success: function(e,response){				
				e.preventDefault();				
				$("#rowId").closest('tr').remove();
			},
			error:function (xhr, ajaxOptions, thrownError){
            	//On error, we alert user
               alert("Severe Service Error::" + thrownError + 
               "Status Code::" + xhr.status);
             }

		});
		
	}
}
   $(".AdReceiveRequestPopUp").click(function (e)
      {
	

         ShowADReceiveRequestPopUp(false);
         e.preventDefault();
 });

$(".saveADReceiveRequestCancel").click(function (e)
      {
	

        HideADReceiveRequestPopUp()
         e.preventDefault();
 })




	   
 });   

 
	  
  $("#btnCancelReceiveRequest").click(function (e)
	      {
	        HideReceiveRequest();
	         e.preventDefault();
	      });



	  