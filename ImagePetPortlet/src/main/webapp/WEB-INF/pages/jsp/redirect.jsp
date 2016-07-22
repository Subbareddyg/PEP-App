<script>
var username="${username}";
if(username.indexOf('@') === -1) 
{
   window.location = "/wps/portal/home/InternalLogin";
} else {
	
	window.location = "/wps/portal/home/ExternalVendorLogin";
}
</script>