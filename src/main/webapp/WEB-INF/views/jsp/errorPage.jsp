<!DOCTYPE html>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<html lang="en">
<head>
	<jsp:directive.include file="include_metatags.jsp" />
	<title><s:message code="app.title.homepage" arguments="${applicationScope['APPLICATION_HEADER_TEXT']}"/></title>
	<jsp:directive.include file="include_head.jsp" />
</head>
<body>
	<jsp:include page="errorMenu.jsp">
		<jsp:param value="error" name="currentpage" />
	</jsp:include>
	<div class="body content">
		<div id="page-wrapper">
           <div class="container-fluid">
		 		<div class="row">
		 			<c:if test="${not empty success}">
						<div class="alert alert-success" style="margin: 45px 6px -45px;">
				 			<strong></strong> <c:out value="${success}"/>
						</div>
					</c:if>
	            	<div class="col-lg-12 first_1" style="margin-top: 50px"> 
	               		
	              	</div>
           		</div>
			</div> 
			<div class="clearfix"></div>
		</div>
	</div>
    <jsp:include page="include_body_scripts.jsp" />
</body>
</html>
