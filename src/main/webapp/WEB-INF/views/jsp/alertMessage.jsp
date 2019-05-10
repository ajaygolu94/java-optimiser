<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${not empty success}">
	<div class="alert alert-success">
			<strong></strong> <c:out value="${success}"/>
	</div>
</c:if>

<c:if test="${not empty error}"> 
	<div class="alert alert-warning">
		<c:set var="error" value="${error}" />
		<c:set var="errors" value="${fn:split(error, ';') }" />
		<strong>Error!</strong> 
		<c:forEach items="${errors}" var="errorMessage">
			 <p><c:out value="${errorMessage}" /></p> 
		</c:forEach>
	</div>
</c:if>