<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
	<jsp:directive.include file="include_metatags.jsp" />
	<title><s:message code="app.title.addpaymentgateway" arguments="${applicationScope['APPLICATION_HEADER_TEXT']}"/></title>
	<jsp:directive.include file="include_head.jsp" />
	
	<link href="${pageContext.request.contextPath}/css/datatables.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
</head>
<body>
<jsp:include page="menu.jsp">
	<jsp:param value="Configuration" name="currentpage" />
</jsp:include>

<section class="login-page">
   	<div class="container">
       	<div class="row">
           	<div class="col-md-12 add">
               	<div class="col-md-12 head">
                   	<h4> <s:message code="label.add.paymentgateway"/></h4>
                 </div>
                   
                   <div class="col-md-12 bottom">
	                   <div class="col-md-12 head1">
	                   		<h5> <a href="${pageContext.request.contextPath}/home"><s:message code="app.link.home"/></a>  /  
	                   		<a href="${pageContext.request.contextPath}/paymentgateway"><s:message code="form.home.paymentGatewayList"/></a>  /  
	                   		<s:message code="form.paymentgateway.create"/></h5>
                       </div>
                    	
                    	<div class="col-md-12 head1">
                        	<jsp:directive.include file="alertMessage.jsp" />
							<div class="alert alert-warning" id="errorMessages" style="display: none;"></div>
                        </div>
                    	
                       	<div class="col-md-12">
                        	<div class="col-md-12 downNew">
                          		<%-- <form class="form-horizontal"> --%>
                          		<form name="paymentgatewayform" id="paymentgatewayform" class="form-horizontal form-main" role="form" data-toggle="validator" method="post">
                        			<div class="col-md-12" style="padding-left:0px; padding-right:0px">
						  				<div class="col-md-6 left1">
                                      			<div class="form-group">
                                        			<label class="col-sm-4 control-label"><s:message code="form.paymentgateway.paymentgatewayname"/><font color="red"> *</font> : </label>
													<s:message code="form.paymentgateway.paymentgatewayname" var="paymentgatewayname"/>
													<div class="col-sm-6"> 
														<input type="text" name="paymentgatewayname" id="paymentGatewayName" placeholder="${paymentgatewayname}" 
														   	maxlength="50" size="50" class="form-control2" required="required"></input>
														<div class="help-block with-errors" ></div> 
													</div>
                                      			</div>
                          				</div>
                             			<div class="col-md-6 right1">
                                     		<div class="form-group">
                                        		<label class="col-sm-4 control-label"><s:message code="form.paymentgateway.paymentgatewaydescription"/>
                                        			 : </label>
                                        		<s:message code="form.paymentgateway.paymentgatewaydescription" var="paymentgatewaydescription"/>
                                        		<div class="col-sm-6">
                                          			<textarea name="description" id="description" placeholder="${paymentgatewaydescription}" 
														   rows="4" maxlength="1000" class="form-control2"></textarea>
													<div class="help-block with-errors"></div>
                                        		</div>
                                      		</div>
                              			</div>
                    	 			</div>
                    	 			
                    	 			<!-- Parent Div for gateway parameters key value pair fields - Start -->
									<div id = "gatewayParametersParentDiv">
										<div id="gatewayParametersParentDivFirst">
											<div class="col-md-12" style="padding-left:0px; padding-right:0px">
												<div class="col-md-6 left1">
													<div class="form-group">
														<label class="col-sm-4 control-label"><s:message code="form.paymentgateway.gatewayparameterkey" /> : </label>
														<s:message code="form.paymentgateway.gatewayparameterkey" var="gatewayparameterkey"/>
														<div class="col-sm-6">
															<input type="text" name="gatewayParameterKeyId" id="gatewayParameterKeyId" placeholder="${gatewayparameterkey}" 
														   		value=""  
														   		class="form-control2" maxlength="50" onkeyup="updateNameOfNextInput(this);" onblur="updateNameOfNextInput(this);" required="required">
															<div class="help-block with-errors"></div>
														</div>
													</div>
												</div>
												<div class="col-md-6 right1">
													<div class="form-group">
														<label class="col-md-4 control-label"><s:message code="form.paymentgateway.gatewayparametervalue" /> : </label>
														<s:message code="form.paymentgateway.gatewayparametervalue" var="gatewayparametervalue" />
														<div class="col-md-6">
															<input type="text" name="gatewayParameterValueId" id="gatewayParameterValueId" placeholder="${gatewayparametervalue}" 
																	       value="" class="form-control2" maxlength="50" required="required" >
															<div class="help-block with-errors"></div>
														</div>
														<div class="col-md-1 pm" id ="addGatewayParametersDiv" >
															<i  class="glyphicon glyphicon-plus plus" id="addGatewayParameters" onclick="addGatewayParametersDiv(this);"></i>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- Parent Div for gateway parameters key value pair fields - End -->
									
									<!-- Div used to add new gateway parameters key value pair form fields - Start -->
									<div id="gatewayParametersCloneDiv" style="display: none;">
										<div id="gatewayParametersCloneDivFirst">
											<div class="col-md-12" style="padding-left:0px; padding-right:0px">
												<div class="col-md-6 left1">
													<div class="form-group">
														<label class="col-sm-4 control-label"><s:message code="form.paymentgateway.gatewayparameterkey" /> : </label>
														<s:message code="form.paymentgateway.gatewayparameterkey" var="gatewayparameterkey" />
														<div class="col-sm-6">
															<input type="text" name="gatewayParameterKeyId" id="gatewayParameterKeyId" placeholder="${gatewayparameterkey}" 
																value=""  
																class="form-control2" maxlength="50" onkeyup="updateNameOfNextInput(this);" onblur="updateNameOfNextInput(this);" required="required">
															<div class="help-block with-errors"></div>
														</div>
													</div>
												</div>
												<div class="col-md-6 right1">
													<div class="form-group">
														<label class="col-sm-4 control-label"><s:message code="form.paymentgateway.gatewayparametervalue" /> : </label>
														<s:message code="form.paymentgateway.gatewayparametervalue" var="gatewayparametervalue" />
														<div class="col-sm-6">
															<input type="text" name="gatewayParameterValueId" id="gatewayParameterValueId" 
																   placeholder="${gatewayparametervalue}" value="" class="form-control2" maxlength="50" required="required" > 
															<div class="help-block with-errors"></div>
														</div>
														<div class="col-md-1 pm" id="addGatewayParametersCloneDiv" > 
															<i class="glyphicon glyphicon-minus minus" id="removeGatewayParametersClone" onclick="removeGatewayParametersDiv(this);"></i>
															<i class="glyphicon glyphicon-plus plus" id="addGatewayParametersClone" onclick="addGatewayParametersDiv(this); "></i>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- Div used to add new gateway parameters key value pair form fields - End -->
		                		</form>
			      			</div>&nbsp;
			      			<div class="actionDiv" style="margin-left: 0px;">
				           		<a href="#" id="savePaymentGatewayBtn" class="btn-box">
									<s:message code="form.generalAction.buttonSave"/></a>
								<a class="btn-box" href="${pageContext.request.contextPath}/paymentgateway" role="button">
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

	//Resposible to add new div
	function addGatewayParametersDiv(addBtn){
		
		$('#gatewayParametersCloneDiv #addGatewayParametersClone').attr("id","addGatewayParameters");
		$("#gatewayParametersCloneDiv").children().clone(true).appendTo("#gatewayParametersParentDiv");
		$('#gatewayParametersCloneDiv #addGatewayParameters').attr("id","addGatewayParametersClone");
		
		$(addBtn).remove();
	}
	
	//responsible to remove div
	function removeGatewayParametersDiv(removeBtn){
		
		$(removeBtn).closest('#gatewayParametersCloneDivFirst').remove();
		
		if ($('#gatewayParametersParentDiv').children().size()>1){
			
			if ($('#gatewayParametersParentDiv').children().last().find('#addGatewayParametersCloneDiv').find('#addGatewayParameters').length <= 0 ){
	    		
				var addButton =$('#gatewayParametersCloneDiv #addGatewayParametersClone').attr("id","addGatewayParameters").clone(true);
				
	    		addButton.appendTo($('#gatewayParametersParentDiv').children().last().find('#addGatewayParametersCloneDiv'));
	    		
	    		$('#gatewayParametersCloneDiv #addGatewayParameters').attr("id","addGatewayParametersClone");
			}
			
		}else{
		
				var addButton =$('#gatewayParametersCloneDiv #addGatewayParametersClone').attr("id","addGatewayParameters").clone(true);
				
				addButton.appendTo("#gatewayParametersParentDivFirst #addGatewayParametersDiv");
				addButton.appendTo($('#gatewayParametersParentDiv').children().last().find('#addGatewayParametersCloneDiv'));
				
				$('#gatewayParametersCloneDiv #addGatewayParameters').attr("id","addGatewayParametersClone");
		}
	}

	function updateNameOfNextInput(keyInput){
		
    	var gatewayParameterValue = keyInput.value;
    	var gatewayParameters = "gatewayParameters['"+gatewayParameterValue+"']";
    	
    	$(keyInput).closest("#gatewayParametersParentDivFirst").find("#gatewayParameterValueId").attr("name",gatewayParameters);
    	$(keyInput).closest("#gatewayParametersCloneDivFirst").find("#gatewayParameterValueId").attr("name",gatewayParameters);
    }

	$(document).ready(function() {
		
		$('#errorMessages').hide();
		
		$("#savePaymentGatewayBtn").click(function(){
			
	       	var paymentgateway = $('#paymentgatewayform').serializeObject();
	       
			var jsonData = JSON.parse(JSON.stringify(paymentgateway));
	       	
	       	if(isPaymentGatewayKeyDuplicate(jsonData) == true) {
				$('#errorMessages').html("Payment Gateway Key must be unique.");
				$('#errorMessages').show();
				window.scrollTo(0, 0);
				return false;
			}
	       	
	       	var isNullKey = false;
	       	var gatewayParameterValuesData, gatewayParameterKeyData; 
			$(function(){
			   var values = $('input[id="gatewayParameterValueId"]').map(function(){
			       return this
			   }).get();
			   gatewayParameterValuesData = values;
			});
			
			$(function(){
			   var values = $('input[id="gatewayParameterKeyId"]').map(function(){
			       return this
			   }).get();
			   gatewayParameterKeyData = values;
			});
			
			gatewayParameterValuesData.pop();
			gatewayParameterKeyData.pop();
			
			$.each(gatewayParameterValuesData, function (index, value)  
            {
				$.each(gatewayParameterKeyData, function (idx, val)  
                {
					if( (val.value === "" || val.value === null) && (value.value == null || value.value == "")) {
						isNullKey = false;
					}else if( (val.value !== "" || val.value !== null) && (value.value == null || value.value == "")) {
						isNullKey = false;
					}else{
						if( (val.value == "" || val.value == null) && (value.value !== null || value.value !== "")) {
							isNullKey = true;
						}else {
							if( (val.value !== "" || val.value !== null) && (value.value !== null || value.value !== "")) {
								isNullKey = false;
							} else{
								isNullKey = true;
							}
						}
					}
                });
            });
	       	
			if(isNullKey) {
				$('#errorMessages').html("Please enter Payment Gateway Parameter Key for Payment Gateway Parameter Value");
				$('#errorMessages').show();
				window.scrollTo(0, 0);
				return false;
			}
			
			// Validate selector key is duplicate or not
			function isPaymentGatewayKeyDuplicate(jsonData) {
				
				var result = [], output = ""; 
				if ( jsonData['gatewayParameterKeyId'] != null ){
					
					var gatewayParameterKey = jsonData['gatewayParameterKeyId'].toString();
					if( gatewayParameterKey.split(',').length === 1 ){
					}else {
						$.each(gatewayParameterKey.split(','), function(index, value) {
							$.each(gatewayParameterKey.split(','), function(idx, val) {
								if( value.trim().length > 0 && val.trim().length > 0 ){
									if( value === val && index !== idx ){
										output = true;
										return false;
									}
								}
								
							});
							if( output ){
								return false;
							}
						});
					}
				}
				return output; 
			}
			
		 	$.ajax({
				type : "POST",
				url : '${pageContext.request.contextPath}/paymentgatewayadd',
				beforeSend: function(request) {
					request.setRequestHeader("userId", '${sessionScope["accessToken"].openid}');
				    request.setRequestHeader("accessToken", '${sessionScope["accessToken"].accesstoken}');
			  	},
				data :  paymentgateway,
				success : function(response) {
					window.location.href = "${pageContext.request.contextPath}/paymentgateway?code="+response.code;
				},
				error : function(xhr, status, error) {
					errorInfo = "";
					var json = JSON.parse(xhr.responseText);
					while (json.messages.length > 0) {
						errorInfo += json.messages.pop()
						 + '<br/>';
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