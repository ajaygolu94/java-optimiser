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
		<jsp:param value="Configuration" name="currentpage" />
	</jsp:include>
  
  <div class="clearfix"></div>
 
  	<section class="login-page">
    	<div class="container">
        	<div class="row">
            	<div class="col-md-12 add">
                	<div class="col-md-12 head">
                    	<h4><s:message code="app.title.viewpaymentgateway"></s:message></h4>
                    </div>
                    
                    <div class="col-md-12 bottom">
                    	<div class="col-md-12 head1">
	                    	<h5> <a href="${pageContext.request.contextPath}/home"><s:message code="app.link.home"/></a>	/	<a href="${pageContext.request.contextPath}/paymentgateway"><s:message code="form.home.paymentGatewayList"/></a>	/   <s:message code="app.title.viewpaymentgateway"></s:message></h5>
                        </div>
                       
                       	<div class="col-md-12 head1">
                       		<jsp:directive.include file="alertMessage.jsp" />
							<div class="alert alert-warning" id="errorMessages" style="display: none;"></div>
                       	</div>
                       
	                     <div class="col-md-12 table-responsive list-data">
	                     	<input type="hidden" name="paymentgatewayid" id="paymentgatewayid" value="${paymentGatewayId}" />
		                    	<table class="table table-border "> 	                                               
                                 	<tbody class="list-item">
                                 		
	                                 	<tr>
							            	<td class="list2"><s:message code="form.paymentgatewayview.id" />
							            	<td class="list1">:<span id="paymentGatewayIdForView"></span></td>
							            </tr>
							            <tr>
			                                <td class="list2"><s:message code="form.paymentgatewayview.paymentGatewayName" /></td>
			                                <td class="list1">:<span id="paymentGatewayNameForView"></span></td>
			                            </tr>
			                            <tr>
			                                <td class="list2"><s:message code="form.paymentgatewayview.paymentGatewayDescription" /></td>
			                                <td class="list1">:<span id="paymentGatewayDescriptionForView"></span></td>
			                            </tr>
			                            <tr>
			                                <td class="list2"><s:message code="form.paymentgatewayview.paymentGatewayparameters" /></td>
			                                <td class="list1">:<span id="paymentGatewayParametersForView"></span></td>
			                            </tr>
			                            <tr>
			                                <td class="list2"><s:message code="form.paymentgatewayview.createdDate" /></td>
			                                <td class="list1">:<span id="createdDateForView"></span></td>
			                            </tr>
			                            <tr>
			                                <td class="list2"><s:message code="form.paymentgatewayview.active" /></td>
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
		
		if ( $("#paymentgatewayid").val() != null ) {
		
		 	$.ajax({
				type : "GET",
				url : '../paymentgateway/view/'+$("#paymentgatewayid").val(),
				beforeSend: function(request) {
					request.setRequestHeader("userId", '${sessionScope["accessToken"].openid}');
				    request.setRequestHeader("accessToken", '${sessionScope["accessToken"].accesstoken}');
			  	},
				success : function(response) {
					
					$("#paymentGatewayIdForView").text(response.responsedata.paymentgatewayid);
					$("#paymentGatewayNameForView").text(response.responsedata.paymentgatewayname);
					$("#paymentGatewayDescriptionForView").text(response.responsedata.description);
					$("#createdDateForView").text(response.responsedata.createdDate);
					$("#activeForView").text(response.responsedata.active);
					$("#auditTimeStampForView").text(response.responsedata.auditTimeStamp);
					
					if(response.responsedata.gatewayParameters != null) {
						
						$("#paymentGatewayParametersForView").html();
						var content = "<div class='list-data'><table class='table table-border sub-table'><thead class='head_list'><tr><th>Key</th><th>Value</th></tr></thead>";
						for (var i = 0, keys = Object.keys(response.responsedata.gatewayParameters), ii = keys.length; i < ii; i++) {
						    content += '<tbody><tr><td class="list2">' +  keys[i] + '</td><td>' +  response.responsedata.gatewayParameters[keys[i]] + '</td></tr></tbody>';
						}
						content += "</table></div>";
						
						$('#paymentGatewayParametersForView').append(content);	
					}
					
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