<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
  <head>
    <jsp:directive.include file="include_metatags.jsp" />
	<title><s:message code="form.login" arguments="${applicationScope['APPLICATION_HEADER_TEXT']}"/></title>
	<jsp:directive.include file="include_head.jsp" />
  </head>
  <body>
	<section class="main-login">
    	<div class="container">
        	<div class="row">
            	<div class="col-md-12">
	            	<div class="col-md-6 main-page1">
	                	<img src="${pageContext.request.contextPath}/img/optimiser-large-logo.png">
	                </div>
	                <div class="col-md-6 main-page2">
	                	<jsp:directive.include file="alertMessage.jsp" />
	                	<div class="col-md-12">
	                    	<div class="log-btn">
			                	<button id="loginWithOpenIdBtn" class="button btn-default" type="button">Login With OpenID</button>
	                        </div>
	                     </div>
	                     <div class="col-md-12">
	                       	<h4> Not a Member? </h4> 	
	    	                <button id="signupWithOpenIdBtn" class="button1 btn-default" type="button">Signup With OpenID</button>
	            		</div> 	
	                </div>
                </div>
            </div>
        </div>
    </section>
   	<form id="loginWithOpenIdForm" action="loginwithopenid" method="get">
	</form>
	<form id="signupWithOpenIdForm" action="signupwithopenid" method="get">
	</form>
   	<div class="clearfix"></div>
    <jsp:directive.include file="include_body_scripts.jsp" />
    <script>
	    $(document).ready(function() {
	    	
	   	 	$( "#loginWithOpenIdBtn" ).click(function() {
	   	 		$( "#loginWithOpenIdForm" ).submit();
	   	   	});
	   	 	
	   	 	$( "#signupWithOpenIdBtn" ).click(function() {
	   			$( "#signupWithOpenIdForm" ).submit();
		   	});
	    });
	</script>
  </body>
</html>