<!DOCTYPE html>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<html lang="en">
<head>
	<jsp:directive.include file="include_metatags.jsp" />
	<title><s:message code="app.title.homepage" arguments="${applicationScope['APPLICATION_HEADER_TEXT']}"/></title>
	<jsp:directive.include file="include_head.jsp" />
</head>
<body>
	<jsp:include page="menu.jsp">
		<jsp:param value="home" name="currentpage" />
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
	
	<c:if test="${role eq isMerchantManager || role eq isMerchant}">
		<div class="container amo2">
	   		<div class="card card-container2">
				<div class="container-fluid mer table-responsive" id="wht">
					<div class="col-md-12 sq">
	        			<div class="row">
		        			<c:forEach var="menu" items="${menuList}" varStatus="theCount">
			        			<c:choose>
			        				<c:when test="${fn:length(menu.menuList) == 0}">
				            			<div class="col-lg-4 col-sm-12 first_1">
				                 			<a href="${pageContext.request.contextPath}${menu.url}">
				                				<div class="panel panel-blue">
				                    				<div class="panel-heading">
				                        				<div class="row">
				                            				<div class="col-md-8 col-md-offset-2 cen">
				                             					<i class="fa fa-credit-card fa-2x"></i>
				                                				<div class="huge">${menu.menuItem}</div>
				                            				</div>
				                        				</div>
							                         	<div class="panel-footer">
								                            <span class="pull-center">View Details</span><br>
								                            <span class="pull-center"><i class="fa fa-arrow-circle-right"></i></span>
								                            <div class="clearfix"></div>
								                        </div>
				                    				</div>
				                  
				                				</div>
				                  			</a>
				            			</div>
			            			</c:when>
		            			</c:choose>
	            			</c:forEach>
	        			</div>
					</div>
	   			</div>
  			</div>
 		</div>
 	</c:if>
	
	<c:if test="${role eq isAdmin}">
		<div class="container amo2">
	   		<div class="card card-container2">
				<div class="container-fluid mer table-responsive" id="wht">
					<div class="col-md-12 sq">
	        			<div class="row">
	        				<c:forEach var="menu" items="${menuList}" varStatus="theCount">
	        					<c:choose>
		        					<c:when test="${fn:length(menu.menuList) == 0}">
				            			<div class="col-lg-4 col-sm-12 first_1">
				                 			<a href="${pageContext.request.contextPath}${menu.url}">
				                				<div class="panel panel-blue">
				                    				<div class="panel-heading">
				                        				<div class="row">
				                            				<div class="col-md-8 col-md-offset-2 cen">
				                             					<i class="fa fa-credit-card fa-2x"></i>
				                                				<div class="huge">${menu.menuItem}</div>
				                            				</div>
				                        				</div>
							                         	<div class="panel-footer">
								                            <span class="pull-center">View Details</span><br>
								                            <span class="pull-center"><i class="fa fa-arrow-circle-right"></i></span>
								                            <div class="clearfix"></div>
								                        </div>
				                    				</div>
				                  
				                				</div>
				                  			</a>
				            			</div>
			            			</c:when>
		            			</c:choose>
	            			</c:forEach>
	        			</div>
					</div>
	   			</div>
  			</div>
 		</div>
 	</c:if>
    <jsp:include page="include_body_scripts.jsp" />
</body>
</html>
