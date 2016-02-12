$(document).ready(function () {
	
    $('button[class^="next"]').click(function () {
        //$(this).next($(".skip")).hide().fadeIn(5000);
    });
});
  
  function  ShowImageAction(modal,imageId)
   { 
    document.getElementById('approveOrRejectImgId').value = imageId;

      $("#overlay_ImageAction").show();
      $("#dialog_ImageAction").fadeIn(300);

      if (modal)
      {
         $("#overlay_ImageAction").unbind("click");
      }
      else
      {
		  
         $("#overlay_ImageAction").click(function (e)
         {
            HideImageAction();
         });
      }
   }
 
   function  SampleCoordinatorNotePopUp(modal)
   {
	
   
      $("#overlay_SampleSwatchMgmt").show();
      $("#dialog_SampleSwatchMgmt").fadeIn(300);

      if (modal)
      {
         $("#overlay_SampleSwatchMgmt").unbind("click");
      }
      else
      {
		  
         $("#overlay_SampleSwatchMgmt").click(function (e)
         {
            HideImageNotePopUp();
         });
      }
   }
   
   function  ShowImageUploadPopUp(modal)
   {
	
   
      $("#overlay_UploadImage").show();
      $("#dialog_UploadImage").fadeIn(300);

      if (modal)
      {
         $("#overlay_UploadImage").unbind("click");
      }
      else
      {
		  
         $("#overlay_UploadImage").click(function (e)
         {
            HideImageUploadPopUp();
         });
      }
   }
   
    function HideImageUploadPopUp()
   {
      $("#overlay_UploadImage").hide();
      $("#dialog_UploadImage").fadeOut(300);
   } 
      
   function  ShowImageADRequestPopUp(modal)
   {
   
      $("#overlay_ImageADRequestPopUp").show();
      $("#dialog_ImageADRequestPopUp").fadeIn(300);

      if (modal)
      {
         $("#overlay_ImageADRequestPopUp").unbind("click");
      }
      else
      {
		  
         $("#overlay_ImageADRequestPopUp").click(function (e)
         {
            HideShowImageADRequestPopUp();
         });
      }
   }
   
    function HideShowImageADRequestPopUp()
   {
      $("#overlay_ImageADRequestPopUp").hide();
      $("#dialog_ImageADRequestPopUp").fadeOut(300);
   } 
   
   
 function  ShowDept(modal)
   {
	  
   
      $("#overlay_Dept").show();
      $("#dialog_Dept").fadeIn(300);

      if (modal)
      {
         $("#overlay_Dept").unbind("click");
      }
      else
      {
         $("#overlay_Dept").click(function (e)
         {
            HideDept();
         });
      }
   }

   function HideDept()
   {
      $("#overlay_Dept").hide();
      $("#dialog_Dept").fadeOut(300);
   } 

 function HideImageAction()
   {
      $("#overlay_ImageAction").hide();
      $("#dialog_ImageAction").fadeOut(300);
   }    
   
   function  ShowDialogReqType(modal)
   {
   
      $("#overlay_ReqType").show();
      $("#dialog_ReqType").fadeIn(300);

      if (modal)
      {
         $("#overlay_ReqType").unbind("click");
      }
      else
      {
         $("#overlay_ReqType").click(function (e)
         {
            HideDialogReqType();
         });
      }
   }

   function HideDialogReqType()
   {
      $("#overlay_ReqType").hide();
      $("#dialog_ReqType").fadeOut(300);
   } 
   
 
 function ShowDialog(modal)
   {
   
      $("#overlay").show();
      $("#dialog").fadeIn(300);

      if (modal)
      {
         $("#overlay").unbind("click");
      }
      else
      {
         $("#overlay").click(function (e)
         {
            HideDialog();
         });
      }
   }

   function HideDialog()
   {
      $("#overlay").hide();
      $("#dialog").fadeOut(300);
   } 
   
   
   

 function ShowDialogUPC(modal)
   {
   
      $("#overlay_UPC").show();
      $("#dialog_UPC").fadeIn(300);

      if (modal)
      {
         $("#overlay_UPC").unbind("click");
      }
      else
      {
         $("#overlay_UPC").click(function (e)
         {
            HideDialog();
         });
      }
   }

   function HideDialogUPC()
   {
      $("#overlay_UPC").hide();
      $("#dialog_UPC").fadeOut(300);
   } 
   
   function ShowDialogImage(modal)
   {
   
      $("#overlay_Image").show();
      $("#dialog_Image").fadeIn(300);
	  
	 var r = $("#completed").text();	 
	 
	$("input[name='chkSelectionImage']").prop('checked', true);
      
	if(r =='Completed'){
	   $("#checkImage").prop( "checked", false );
	}
      if (modal)
      {
         $("#overlay_Image").unbind("click");
      }
      else
      {
         $("#overlay_Image").click(function (e)
         {
            HideDialogImage();
         });
      }
   }

   function HideDialogImage()
   {
      $("#overlay_Image").hide();
      $("#dialog_Image").fadeOut(300);
   }


function ShowDialogContent(modal)
   {
   
      $("#overlay_Content").show();
      $("#dialog_Content").fadeIn(300);
	  
	  var contentVal= $("#contId").text(); 
	
	  $("input[name='chkSelectionContent']").prop('checked', true);
      
	  if(contentVal=='Completed'){
	  $("#checkContent").prop( "checked", false );
	}
      if (modal)
      {
         $("#overlay_Content").unbind("click");
      }
      else
      {
         $("#overlay_Content").click(function (e)
         {
            HideDialogContent();
         });
      }
   }

   function HideDialogContent()
   {
      $("#overlay_Content").hide();
      $("#dialog_Content").fadeOut(300);
   }  
   
   
   
   
   
   function ShowClassNo(modal)
   {
   
      $("#overlay_ClassNo").show();
      $("#dialog_ClassNo").fadeIn(300);

      if (modal)
      {
         $("#overlay_ClassNo").unbind("click");
      }
      else
      {
         $("#overlay_ClassNo").click(function (e)
         {
            HideDialogClassNo();
         });
      }
   }

   function HideDialogClassNo()
   {
      $("#overlay_ClassNo").hide();
      $("#dialog_ClassNo").fadeOut(300);
   }  
   
   function ShowDialogVendor(modal)
   {
   
      $("#overlay_Vendor").show();
      $("#dialog_Vendor").fadeIn(300);

      if (modal)
      {
         $("#overlay_Vendor").unbind("click");
      }
      else
      {
         $("#overlay_Vendor").click(function (e)
         {
            HideDialogVendor();
         });
      }
   }

   function HideDialogVendor()
   {
      $("#overlay_Vendor").hide();
      $("#dialog_Vendor").fadeOut(300);
   }  
   
   
   function ShowDialogSearch(modal)
   {
   $("#AdvSearch").css("visibility","visible");
     
      $("#btnAdvSearch").hide();
      //$("#dialog_Vendor").fadeIn(300);
	 
         $("#btnAdvSearch").click(function (e)
         {
           // HideDialogSearch();
         });
      
   
   }

  function clearFields() {  
  
    document.getElementById("DeptNo").value="" ;
    document.getElementById("datepicker1").value="" ;
	document.getElementById("datepicker2").value="" ;
	document.getElementById("imageStatus").value="" ;
    document.getElementById("contentStatus").value="" ;
	document.getElementById("petStatus").value="" ;
	document.getElementById("RequestType").value="" ;
    
	document.getElementById("ORIN").value="" ;
    document.getElementById("vendorStyle").value="" ;
	document.getElementById("UPC").value="" ;
    document.getElementById("VendorNumber").value="" ;
	
	document.getElementById("ClassNumber").value="" ;
   	document.getElementById("Yes").checked = false;
	document.getElementById("No").checked = false;
	
	document.getElementById("DeptNo").removeAttribute('disabled');
	document.getElementById("datepicker1").removeAttribute('disabled');
	document.getElementById("datepicker2").removeAttribute('disabled');
	document.getElementById("RequestType").removeAttribute('disabled');
	document.getElementById("ORIN").removeAttribute('disabled');
	document.getElementById("vendorStyle").removeAttribute('disabled');
	document.getElementById("UPC").removeAttribute('disabled');
	document.getElementById("VendorNumber").removeAttribute('disabled');
	document.getElementById("ClassNumber").removeAttribute('disabled');
	document.getElementById("Yes").removeAttribute('disabled');
	document.getElementById("No").removeAttribute('disabled');
	
	
}
	function alphanumericCheck() {
	 var inputVar = document.getElementById("vendorStyle").value;
	// var Exp =[^a-zA-Z0-9] ;

		if(!inputVar.match(/^[0-9a-z]+$/)){
			alert("Only alphanumeric allowed");
		   return false;
		  }
		  else{
		  return true;
		  }
	  }	
	
  function isNumber(evt) {  
        var iKeyCode = (evt.which) ? evt.which : evt.keyCode
        if (iKeyCode != 46 && iKeyCode > 31 && (iKeyCode < 48 || iKeyCode > 57))
		{
		alert("Only Numbers allowed");
		return false;
			}
		else
		{	
        return true;
		}
    }   

function HideImageNotePopUp()
   {
  
     $("#overlay_SampleSwatchMgmt").hide();
      $("#dialog_SampleSwatchMgmt").fadeOut(300);
   }  
	
//method show receive request
function  ShowReceiveRequest(modal)
{  
  
   $("#overlay_ImageReceiveRequestPopUp").show();
   $("#dialog_receiveRequestAction").fadeIn(300);

   if (modal)
   {
      $("#overlay_ImageReceiveRequestPopUp").unbind("click");
   }
   else
   {
		  
      $("#overlay_ImageReceiveRequestPopUp").click(function (e)
      {
         HideReceiveRequest();
      });
   }
}

//method for hide receive request
function HideReceiveRequest()
{
   $("#overlay_ImageReceiveRequestPopUp").hide();
   $("#dialog_receiveRequestAction").fadeOut(300);
}  



function  ShowADReceiveRequestPopUp(modal,imageId)
   {


	document.getElementById('ArdApproveOrRejectImgId').value = imageId;
   
      $("#overlay_imageADReceiveRequestPopUp").show();
      $("#dialog_imageADReceiveRequestPopUp").fadeIn(300);

      if (modal)
      {
         $("#overlay_imageADReceiveRequestPopUp").unbind("click");
      }
      else
      {
		  
         $("#overlay_imageADReceiveRequestPopUp").click(function (e)
         {
            HideADReceiveRequestPopUp();
         });
      }
   }


     function HideADReceiveRequestPopUp()
   {
      $("#overlay_imageADReceiveRequestPopUp").hide();
      $("#dialog_imageADReceiveRequestPopUp").fadeOut(300);
   }   
	