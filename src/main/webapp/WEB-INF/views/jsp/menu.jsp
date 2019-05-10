<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${param.currentpage!='login'}">
<nav class="navbar navbar-inverse header1">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
         <a class="navbar-brand logo" href="${pageContext.request.contextPath}/home"><img src="${pageContext.request.contextPath}/img/logo.png"></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav pull-right pm1">
            <li><a href="${pageContext.request.contextPath}/home" title="Home" class="menu_new"><span class="glyphicon glyphicon-home hm" aria-hidden="true" ></span>&nbsp;Home</a></li>
            <c:choose>
            	<c:when test="${not empty menuList}">
		 			<c:forEach var="menu" items="${menuList}" varStatus="theCount">
		 				<c:set var="isActive" value="false"/>
		 				<c:if test="${not isActive && param.currentpage eq menu.menuItem}">
		 					<c:set var="isActive" value="true"/>
		 				</c:if>
		 				<c:choose>
		 					<c:when test="${fn:length(menu.menuList) gt 0}">
		 						<li class="dropdown">
							   	  	<a class="${isActive ? 'active' : ''}" href="${pageContext.request.contextPath}${menu.url}" data-toggle="dropdown" role="button" aria-haspopup="true"
											aria-expanded="false" title="${menu.menuItem}"><span class="opt">${menu.menuItem} &nbsp;</span> <span class="caret"></span></a>
								  	<c:if test="${not empty menu.menuList}">
									  	<ul class="dropdown-menu">
											<li>
												<c:forEach var="submenu" items="${menu.menuList}" varStatus="theCount2">
													<li><a href="${pageContext.request.contextPath}${submenu.url}" title="${submenu.menuItem}">${submenu.menuItem}&nbsp;</a></li>
												</c:forEach>
											</li>
									  	</ul>
								  	</c:if>
							   	</li>
		 					</c:when>
		 					<c:otherwise>
					  			<li>
					    			<a class="${isActive ? 'active' : ''} menu_new" href="${pageContext.request.contextPath}${menu.url}" role="button" title="${menu.menuItem}">${menu.menuItem}</a>
					    		</li>
				  			</c:otherwise>
		 				</c:choose>
		 			</c:forEach>
		 		</c:when>
            </c:choose>
            <li class="dropdown">
				<a href="#" class="dropdown-toggle"  data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false" title="${applicationuser.name}" > <span class="opt">Welcome ${applicationuser.name} &nbsp;</span> <span class="caret"></span>
				</a>
				<ul class="dropdown-menu">
					<li>
						<a href="#" id="userProfileButton" data-toggle="modal"  title="<s:message code="app.menu.userprofile"/>" 
							data-target="#userProfileModel">
							<s:message code="app.menu.userprofile"/>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/logout" title="<s:message code="app.menu.logout"/>" >
							<s:message code="app.menu.logout"/>&nbsp;<span class="glyphicon glyphicon-off" aria-hidden="true" style="top: 2px;"></span>
						</a>
					</li>
				</ul>
			</li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
</c:if>

<!-- User Profile Dialog Modal -->
 <div class="modal fade" id="userProfileModel" role="dialog" data-backdrop="static">
   <div class="modal-dialog">
	   <!-- Modal content-->
	   <div class="modal-content" style="width: 100%;">
	      <div class="modal-header">
		       <button type="button" class="close" data-dismiss="modal">&times;</button>
		       <h4 class="modal-title" align="center" id="userProfileModalTitle" style='color: #000000;'>User Profile Details</h4>
	      </div>
	      <div class="modal-body" id="userProfileModelBody">
	      </div>
	      <div class="modal-footer">
	        	<button type="button" class="btn btn-danger" data-dismiss="modal" style="margin-top: -5px;">Close</button>
	      </div>
	   </div>
   </div>
 </div>
<script src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">

$("#userProfileButton").click(function(){

	$("#userProfileModelBody").html("Loading...");
	
	$.ajax({
		type : "GET",
		url : '${pageContext.request.contextPath}/userprofile',
		beforeSend: function(request) {
			request.setRequestHeader("userId", '${sessionScope["accessToken"].openid}');
		    request.setRequestHeader("accessToken", '${sessionScope["accessToken"].accesstoken}');
	  	},
		headers : {
			'Content-Type' : 'application/json'
		},
		success : function(data, status) {
			$('#userProfileModelBody').html(data);
		},
		error : function(xhr, status, error) {
			errorInfo = "";
			var json = JSON.parse(xhr.responseText);
			
			while (json.messages.length > 0) {  
				errorInfo += json.messages.pop() + '<br/>';  
			} 
			$('#errorMessages').html(errorInfo);
			$('#errorMessages').show();
		}
	}); 
});
</script>
