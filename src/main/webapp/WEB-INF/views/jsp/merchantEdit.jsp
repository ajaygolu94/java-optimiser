<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
	<jsp:directive.include file="include_metatags.jsp" />
	<title><s:message code="app.title.editmerchant" arguments="${applicationScope['APPLICATION_HEADER_TEXT']}"/></title>
	<jsp:directive.include file="include_head.jsp" />
	
</head>
<body>
<jsp:include page="menu.jsp">
	<jsp:param value="Merchant" name="currentpage" />
</jsp:include>
	
<section class="login-page">
   	<div class="container">
		<div class="row">
			<div class="col-md-12 add">
				<div class="col-md-12 head">
                   	<h4> <s:message code="label.edit.merchant"/></h4>
          		</div>
                 
                <div class="col-md-12 bottom">
		                <div class="col-md-12 head1">
		               		<h5> <a href="${pageContext.request.contextPath}/home"><s:message code="app.link.home"/></a>  /  <a href="${pageContext.request.contextPath}/merchant" > <s:message code="app.title.listmerchant"/></a>
               				  /  <s:message code="label.edit.merchant"/></h5>
                    	</div>
              		<div class="col-md-12 head1">
                        	<jsp:directive.include file="alertMessage.jsp" />
							<div class="alert alert-warning" id="errorMessages"></div>
                        </div>
                    
           			<div class="col-md-12 downForm">
                    
                    	<form name="merchanteditform" class="form-main" id="merchanteditform" role="form" data-toggle="validator" method="post">
                    		<input type="hidden" id="userId" name="userId" value="${user.userId}">
                    		<input type="hidden" name="merchantId" id="merchantId" value="${merchant.merchantId}" />
							<input type="hidden" name="createddate" id="createdDate" value="${merchant.createddate}" />
	                    	<table class=" col-md-12 infoTabForm">
		  						<tbody class="col-md-6 left1Form">
	                            	<tr>
	                                	<td class="list1Form"><s:message code="form.merchant.registeredUser"/><font color="red"> *</font>&nbsp;:</td>
	                                    <td class="list2Form">
											<input type="text" name="username" id="username" maxlength="64" size="50" 
	                                    		   value="${user.name}" class="form-control" tabindex="1" readonly>
											<div class="help-block with-errors"></div> 
	                                    </td>
	                                </tr>
	                                <tr>
	                                	<td class="list1Form"><s:message code="form.merchant.merchantName"/><font color="red"> *</font>&nbsp;:</td>
	                                	<s:message code="form.merchant.merchantName" var="merchantName" />
	                                    <td class="list2Form">
	                                    	<input type="text" name="merchantname" id="merchantName" placeholder="${merchantName}" 
														maxlength="64" size="50" value="${merchant.merchantname}" class="form-control" tabindex="3" required>
											<div class="help-block with-errors"></div> 
	                                    </td>
	                                </tr>
	                            </tbody>                        	
	                        	<tbody class="col-md-6 right1Form">
	                        		<tr>
	                                	<td class="list1Form"><s:message code="form.merchant.sourceMerchantId"/><font color="red"> *</font>&nbsp;:</td>
	                                	<s:message code="form.merchant.sourceMerchantId" var="sourceMerchantId" />
	                                    <td class="list2Form">
	                                    	<input type="text" name="sourcemerchantid" id="sourcemerchantid" placeholder="${sourceMerchantId}" 
									  					maxlength="64" size="50" value="${merchant.sourcemerchantid}" class="form-control" tabindex="2" required>
											<div class="help-block with-errors"></div> 
										</td>
									</tr>
	                            </tbody>
               			</table>  
               		</form>     
	               		<div class="actionDiv">
	               			<a id="updateMerchantBtn" href="#" class="btn-box">
								<s:message code="form.generalAction.buttonUpdate"/></a>
							<a class="btn-box" href="${pageContext.request.contextPath}/merchant">
								<s:message code="form.generalAction.buttonCancel"/></a>
	               		</div>                 
                	</div>
           			
            	</div>
        	</div>
     	</div>
 	</div>
</section>
<div class="clearfix"></div>

<jsp:directive.include file="include_body_scripts.jsp" />
<script src="${pageContext.request.contextPath}/js/jquery.serializeObject.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		$('#errorMessages').hide();
		
		$("#updateMerchantBtn").click(function(){
			
	       var merchant = $('#merchanteditform').serializeObject();
	       
		 	$.ajax({
				type : "POST",
				url : '${pageContext.request.contextPath}/merchant/edit/',
				beforeSend: function(request) {
					request.setRequestHeader("userId", '${sessionScope["accessToken"].openid}');
				    request.setRequestHeader("accessToken", '${sessionScope["accessToken"].accesstoken}');
			  	},
				headers : {
					'Content-Type' : 'application/json'
				},
				data :  JSON.stringify(merchant),
				success : function(response) {
					window.location.href = "${pageContext.request.contextPath}/merchant?code="+response.code;
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
	    }); 
	});
</script>
</body>
</html>
