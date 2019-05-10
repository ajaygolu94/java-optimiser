<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
    <jsp:directive.include file="include_metatags.jsp" />
	<title><s:message code="app.title.viewMerchant" arguments="${applicationScope['APPLICATION_HEADER_TEXT']}"/></title>
	<jsp:directive.include file="include_head.jsp" />

</head>
<body>
  <jsp:include page="menu.jsp">
		<jsp:param value="Merchant" name="currentpage" />
	</jsp:include>
  
  <div class="clearfix"></div>
 
  	<section class="login-page">
    	<div class="container">
        	<div class="row">
            	<div class="col-md-12 add">
                	<div class="col-md-12 head">
                    	<h4><s:message code="app.title.viewmerchant"></s:message></h4>
                    </div>
                    
                    <div class="col-md-12 bottom">
                    	<div class="col-md-12 head1">
	                    	<h5> <a href="${pageContext.request.contextPath}/home"><s:message code="app.link.home"/></a>	/	<a href="${pageContext.request.contextPath}/merchant"><s:message code="app.title.listmerchant"/></a>	/   <s:message code="app.title.viewmerchant"></s:message></h5>
                        </div>
                        
                        <div class="col-md-12 head1">
                       		<jsp:directive.include file="alertMessage.jsp" />
							<div class="alert alert-warning" id="errorMessages" style="display: none;"></div>
                       	</div>
                       
	                     <div class="col-md-12 table-responsive list-data">
	                     	<input type="hidden" name="merchantId" id="merchantId" value="${merchantId}" />
		                    	<table class="table table-border "> 	                                               
                                 	<tbody class="list-item">
	                                 	<tr>
							            	<td class="list2"><s:message code="form.merchantlist.id" />
							            	<td class="list1">:<span id="materialIdForView"></span></td>
							            </tr>
							            <tr>
			                                <td class="list2"><s:message code="form.merchantlist.userId" /></td>
			                                <td class="list1">:<span id="userIdForView"></span></td>
			                            </tr>
			                            <tr>
			                                <td class="list2"><s:message code="form.merchantlist.createdDate" /></td>
			                                <td class="list1">:<span id="createdDateForView"></span></td>
			                            </tr>
			                            <tr>
			                                <td class="list2"><s:message code="form.merchantlist.merchantName" /></td>
			                                <td class="list1">:<span id="merchantNameForView"></span></td>
			                            </tr>
			                            <tr>
			                                <td class="list2"><s:message code="form.merchantlist.sourceMerchantId" /></td>
			                                <td class="list1">:<span id="sourceMerchantIdForView"></span></td>
			                            </tr>
			                            <tr>
			                                <td class="list2"><s:message code="form.merchantlist.active" /></td>
			                                <td class="list1">:<span id="activeForView"></span></td>
			                            </tr>
			                            <tr>
			                                <td class="list2"><s:message code="app.label.auditTimeStamp" /></td>
			                                <td class="list1">:<span id="auditTimeStampForView"></span></td>
			                            </tr>
		                            </tbody>
	                            <!-- <tbody class="col-md-6 list-item1"> -->
							</table>
	                    </div>
                   </div>
               </div>
           </div>
       </div>
   </section>
  
   	<div class="clearfix"></div>
    <jsp:directive.include file="include_body_scripts.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
	    
		$('#errorMessages').hide();
		
		if ( $("#merchantId").val() != null ) {
		
		 	$.ajax({
				type : "GET",
				url : '${pageContext.request.contextPath}/merchant/view/'+$("#merchantId").val(),
				beforeSend: function(request) {
					request.setRequestHeader("userId", '${sessionScope["accessToken"].openid}');
				    request.setRequestHeader("accessToken", '${sessionScope["accessToken"].accesstoken}');
			  	},
				success : function(response) {
					
					$("#materialIdForView").text(response.responsedata.merchantId);
					$("#userIdForView").text(response.responsedata.userId);
					$("#createdDateForView").text(response.responsedata.createddate);
					$("#merchantNameForView").text(response.responsedata.merchantname);
					
					if( response.responsedata.sourcemerchantid != null){
						$("#sourceMerchantIdForView").text(response.responsedata.sourcemerchantid);
					}
					
					$("#activeForView").text(response.responsedata.active);
					$("#auditTimeStampForView").text(response.responsedata.auditTimeStamp);
					
				},
				error : function(xhr, status, error) {
					errorInfo = "";
					var json = JSON.parse(xhr.responseText);
					
					for (var i = 0; i < json.messages.length; i++) {
						errorInfo +=json.messages[i]+ '<br/>';
					}
					$('#errorMessages').html(errorInfo);
					$('#errorMessages').show();
				}
			});
		}
	});
</script>
<script src="${pageContext.request.contextPath}/js/validator.min.js"></script>
  </body>
</html>