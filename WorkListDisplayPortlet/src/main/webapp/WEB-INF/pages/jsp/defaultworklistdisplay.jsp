<%@ include file="/WEB-INF/pages/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<head>
     
     
     <title>Deafult WorkFlow Display.</title>
	 
	 

<style type = "text/css">
#overlay_pageLoading
{   
   position: fixed;
   width: 550px;
   height: 250px;
   top: 40%;
   left: 40%;	
}
.web_dialog_petStatusPopUp
{
   display: none;
   position: fixed;
   width: 550px;
   height: 122px;
   top: 44%;
   left: 29%;
   margin-left: 80px;
   margin-top: -100px;
   background-color: #ffffff;
   border: 2px solid #336699;
   padding: 0px;
   z-index: 102;
   font-family: Verdana;
   font-size: 10pt;
   font: normal 11px tahoma,arial,helvetica,sans-serif;
}
</style>

</head>

<portlet:defineObjects />
<fmt:bundle basename="workListDisplay">
<portlet:actionURL var="formDefaultAction"> 
		<portlet:param name="action" value="defaultworkListDisplaySubmit"/>
</portlet:actionURL>
<form:form  method="post" action="${formDefaultAction}" name="defaultworkListDisplayForm" id="defaultworkListDisplayForm" >
<p>You Page is loading please wait..................</p>
</form:form>
</fmt:bundle>
<script type="text/javascript"> 
document.getElementById('<portlet:namespace />defaultworkListDisplayForm').submit();
</script>
