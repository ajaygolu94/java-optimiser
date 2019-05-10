<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${param.currentpage!='login'}">
	<nav class="navbar navbar-inverse header1">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand logo" href="#"><img
					src="${pageContext.request.contextPath}/img/logo.png"></a>
			</div>
			<div></div>
			<!--/.nav-collapse -->
		</div>
	</nav>
	<div class="container">
		<div class="starter-template" style="color: #ffffff;" align="center">
			<h1>Error</h1>
			<h1 style="font-size: 100px;">${status}</h1>
			<h2>${message}</h2>
			<br><br><br>
			<h4>You can go to 
			<c:choose>
				<c:when test='${sessionScope["accessToken"].openid eq null}'>
					<a style="color: #71dfff;" href="${pageContext.request.contextPath}/login">Login page</a>
				</c:when>
				<c:otherwise>
					<a style="color: #71dfff;" href="${pageContext.request.contextPath}/home">Home page</a>
				</c:otherwise>
			</c:choose>
			</h4>
			
			
		</div>
	</div>
</c:if>
