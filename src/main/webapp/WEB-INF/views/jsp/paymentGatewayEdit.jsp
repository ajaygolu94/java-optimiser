<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
	<jsp:directive.include file="include_metatags.jsp" />
	<title><s:message code="app.title.editpaymentgateway" arguments="${applicationScope['APPLICATION_HEADER_TEXT']}"/></title>
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
           	<div class="col-md-12 add" style="margin: 0px auto;">
               	<div class="col-md-12 head">
                   	<h4> <s:message code="label.edit.paymentgateway"/></h4>
                   </div>
                   
                   <div class="col-md-12 bottom">
                   
                   	<div class="col-md-12 head1">
						<h5> <a href="${pageContext.request.contextPath}/home"><s:message code="app.link.home"/></a>  /  
						<a href="${pageContext.request.contextPath}/paymentgateway"><s:message code="form.home.paymentGatewayList"/></a>  /  
						<s:message code="label.edit.paymentgateway"/></h5>
					</div>  
						<div class="col-md-12 head1">
                        	<jsp:directive.include file="alertMessage.jsp" />
							<div class="alert alert-warning" id="errorMessages"></div>
                        </div>
						<div class="col-md-12">
                        	<div class="col-md-12 downNew">
                       	<form:form name="paymentgatewayform" class="form-horizontal form-main" id="paymentgatewayform" modelAttribute="paymentgateways" role="form" data-toggle="validator" method="post">
						
							<input type="hidden" name="paymentgatewayid" id="paymentgatewayid" value="${paymentgateways.paymentgatewayid}" />
							<input type="hidden" name="createdDate" id="createdDate" value="${paymentgateways.createdDate}" />
							
							<div class="col-md-12" style="padding-left:0px; padding-right:0px">
						  		<div class="col-md-6 left1">
                                     <div class="form-group">
                                        <label class="col-sm-4 control-label"><s:message code="form.paymentgateway.paymentgatewayname"/><font color="red"> *</font> : </label>
											<s:message code="form.paymentgateway.paymentgatewayname" var="paymentgatewayname"/>
											<div class="col-sm-6"> 
												<input type="text" class="form-control2" name="paymentgatewayname" id="paymentGatewayName" placeholder="${paymentgatewayname}" 
														 maxlength="50" size="50" class="form-control2" required="required" value="${paymentgateways.paymentgatewayname}"></input>
												<div class="help-block with-errors" ></div> 
											</div>
                                      	</div>
                          		</div>
                             	<div class="col-md-6 right1">
                                     <div class="form-group">
                                        <label class="col-sm-4 control-label"><s:message code="form.paymentgateway.paymentgatewaydescription"/> : </label>
                                        <s:message code="form.paymentgateway.paymentgatewaydescription" var="paymentgatewaydescription"/>
                                        <div class="col-sm-6">
                                          	<textarea name="description" class="form-control2" id="description" placeholder="${paymentgatewaydescription}" 
												rows="4" maxlength="1000" ><c:out value="${paymentgateways.description}"/></textarea>
											<div class="help-block with-errors"></div>
                                     	</div>
                                     </div>
                              	</div>
                    	 	</div>
                    	 			
							<!-- If empty gatewayParameters then show only one DIV -->
							<c:if test="${empty paymentgateways.gatewayParameters}">
								<div id = "gatewayParametersParentDiv">
									<div id="gatewayParametersParentDivFirst">
										<div class="col-md-12" style="padding-left:0px; padding-right:0px">
											<div class="col-md-6 left1">
												<div class="form-group">
													<label class="col-sm-4 control-label"><s:message code="form.paymentgateway.gatewayparameterkey" /> : </label>
													<s:message code="form.paymentgateway.gatewayparameterkey" var="gatewayparameterkey"/>
													<div class="col-sm-6">
														<input type="text" name="gatewayParameterKeyId" id="gatewayParameterKeyId" placeholder="${gatewayparameterkey}" 
														   		value=""  maxlength="50"
														   		class="form-control2" onkeyup="updateNameOfNextInput(this);" onblur="updateNameOfNextInput(this);" required="required">
														<div class="help-block with-errors"></div>
													</div>
												</div>
											</div>
											<div class="col-md-6 right1">
												<div class="form-group">
													<label class="col-md-4 control-label"><s:message code="form.paymentgateway.gatewayparametervalue" /> : </label>
													<s:message code="form.paymentgateway.gatewayparametervalue" var="gatewayparametervalue" />
													<div class="col-md-5">
														<input type="text" name="gatewayParameterValueId" id="gatewayParameterValueId" placeholder="${gatewayparametervalue}" 
																	maxlength="50" value="" class="form-control2" required="required" style="width: 125%;">
														<div class="help-block with-errors"></div>
													</div>
													<div class="col-lg-1 pm" id ="addGatewayParametersDiv" style=" right: -12%;">
														<i  class="glyphicon glyphicon-plus plus" id="addGatewayParameters" onclick="addGatewayParametersDiv(this);"></i>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</c:if>
							
							<!-- Gateway Parameters --Start -->
							<div id = "gatewayParametersParentDiv">
								<c:forEach items="${paymentgateways.gatewayParameters}" var="gatewayParameter" varStatus="index" >
									<c:choose>
										<c:when test="${index.index == 0 }" >
											<div id="gatewayParametersParentDivFirst">
										</c:when>
										<c:otherwise>
											<div id="gatewayParametersCloneDivFirst">
										</c:otherwise>
									</c:choose>
									
									<div class="col-sm-6">
										<div class="form-group row">
											<label class="col-sm-4 control-label"><s:message code="form.paymentgateway.gatewayparameterkey" /> : </label>
											<div class="col-sm-6">
												<s:message code="form.paymentgateway.gatewayparameterkey" var="gatewayparameterkey"></s:message>
												<input type="text" name="gatewayParameterKeyId" id="gatewayParameterKeyId" placeholder="${gatewayparameterkey}" 
												   	maxlength="50" size="50" value="${gatewayParameter.key}"
												   	class="form-control" onkeyup="updateNameOfNextInput(this);" onblur="updateNameOfNextInput(this);" required="required">
												<div class="help-block with-errors"></div>   	   
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group row">
											<label class="col-sm-4 control-label"><s:message code="form.paymentgateway.gatewayparametervalue" /> : </label>
											<div class="col-sm-6">
												<s:message code="form.paymentgateway.gatewayparametervalue" var="gatewayparametervalue"></s:message>
												<input type="url" name="gatewayParameters['${gatewayParameter.key}']" id="gatewayParameterValueId" 
													   placeholder="${gatewayparametervalue}" maxlength="50" value="${gatewayParameter.value}" class="form-control"/>
											</div>
											<div class="col-lg-1 pm" id ="addGatewayParametersDiv" style=" right: -4%;">
												<c:choose>
													<c:when test="${paymentgateways.gatewayParameters.size() ==  index.index+1}">
														<i class="glyphicon glyphicon-plus plus" id="addGatewayParameters" onclick="addGatewayParametersDiv(this);"></i>
														<c:if test="${index.index !=0 }">
															<i class="glyphicon glyphicon-minus minus" id="removeGatewayParametersClone" onclick="removeGatewayParametersDiv(this);"></i>
														</c:if>
													</c:when>
													<c:otherwise>
														<c:if test="${index.index > 0}">
															<i class="glyphicon glyphicon-minus minus" id="removeGatewayParametersClone" onclick="removeGatewayParametersDiv(this);"></i>
														</c:if>
													</c:otherwise>
												</c:choose>	
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
						
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
															value=""  maxlength="50"
															class="form-control2" onkeyup="updateNameOfNextInput(this);" onblur="updateNameOfNextInput(this);" required="required">
														<div class="help-block with-errors"></div>
													</div>
												</div>
											</div>
											<div class="col-md-6 right1">
												<div class="form-group">
												<label class="col-sm-4 control-label"><s:message code="form.paymentgateway.gatewayparametervalue" /> : </label>
												<s:message code="form.paymentgateway.gatewayparametervalue" var="gatewayparametervalue" />
												<div class="col-sm-5">
													<input type="text" name="gatewayParameterValueId" id="gatewayParameterValueId" style="width: 125%;"
														placeholder="${gatewayparametervalue}" value="" maxlength="50" class="form-control2" required="required" > 
												<div class="help-block with-errors"></div>
											</div>
											<div class="col-md-1 pm" id="addGatewayParametersCloneDiv" style=" right: -12%;"> 
												<i class="glyphicon glyphicon-minus minus" id="removeGatewayParametersClone" onclick="removeGatewayParametersDiv(this);"></i>
												<i class="glyphicon glyphicon-plus plus" id="addGatewayParametersClone" onclick="addGatewayParametersDiv(this); "></i>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- Div used to add new gateway parameters key value pair form fields - End -->
                   	</div>
               	</div>
					</form:form>&nbsp;
					<div class="actionDiv"><a href="#" class="btn-box" id="updatePaymentGatewayBtn">
						<s:message code="form.generalAction.buttonUpdate" /></a>
					<a class="btn-box" href="${pageContext.request.contextPath}/paymentgateway" role="button">
						<s:message code="form.generalAction.buttonCancel"/></a>
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
		
			if ($('#gatewayParametersParentDiv').children().last().find('#addGatewayParametersCloneDiv').find('#addGatewayParameters').length <= 0 ){
	    			
				var addButton =$('#gatewayParametersCloneDiv #addGatewayParametersClone').attr("id","addGatewayParameters").clone(true);
				
				addButton.appendTo("#gatewayParametersParentDivFirst #addGatewayParametersDiv");
				addButton.appendTo($('#gatewayParametersParentDiv').children().last().find('#addGatewayParametersCloneDiv'));
				
				$('#gatewayParametersCloneDiv #addGatewayParameters').attr("id","addGatewayParametersClone");
			}
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
		
		$("#updatePaymentGatewayBtn").click(function(){
			
	       	var paymentgateway = $('#paymentgatewayform').serializeObject();
	       
	       	var jsonData = JSON.parse(JSON.stringify(paymentgateway));
	       	
	       	if(isPaymentGatewayKeyDuplicate(jsonData) == true) {
				$('#errorMessages').html("Payment Gateway Parameter Key must be unique.");
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
				url : '${pageContext.request.contextPath}/paymentgatewayupdate',
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