<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.belk.pep.delegate.ContentDelegate"%>    
<%@ page import="com.belk.pep.vo.GlobalAttributesVO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
GlobalAttributesVO styleAttributes = null;
String orinNumber = request.getParameter("orinNumber");
if(!orinNumber.isEmpty()){
	
ContentDelegate contentDelegate = new ContentDelegate();
styleAttributes = contentDelegate.getStyleAttributes(orinNumber);


}


%>

<td>

<%styleAttributes.toString(); %>

</td>

</body>
</html>