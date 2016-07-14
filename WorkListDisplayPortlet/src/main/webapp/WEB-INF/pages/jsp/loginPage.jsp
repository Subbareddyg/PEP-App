<script>
var loggedInUser= '${loggedInUser}';
if(loggedInUser.indexOf('@') === -1) 
		{
		   window.location = "/wps/portal/home/InternalLogin";
		} else {
			
			window.location = "/wps/portal/home/ExternalVendorLogin";
		}

</script>