<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:message code="app.title.loginpage" arguments="${applicationScope['APPLICATION_HEADER_TEXT']}" /></title>
<script type="text/javascript">
	window.onload = function callMe(){
		document.myForm.submit();
	} 
</script>
</head>
<body>

	<form action='<c:out value="${url}"></c:out>' method="post" name="myForm">
		<input type="hidden" id="accessToken" name="accessToken" value=<c:out value="${accessToken}"></c:out>>
		<input type="hidden" id="redirectURL" name="redirectURL" value=<c:out value="${redirectURL}"></c:out>>
		<input type="hidden" id="applicationName" name="applicationName" value=<c:out value="${applicationName}"></c:out>>
	</form>

</body>
</html>