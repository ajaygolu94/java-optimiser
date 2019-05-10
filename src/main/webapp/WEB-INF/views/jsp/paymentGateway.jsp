<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
	<jsp:directive.include file="include_metatags.jsp" />
	<title><s:message code="app.title.paymentgatewaylist" arguments="${applicationScope['APPLICATION_HEADER_TEXT']}"/></title>
	<jsp:directive.include file="include_head.jsp" />
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
                 	<h4><s:message code="form.home.paymentGatewayList" />   
                 		<c:if test="${role eq isAdmin}">
                 			| <a href="${pageContext.request.contextPath}/addPaymentGateway">
                 			<span style="text-decoration: underline;"><s:message code="form.paymentgateway.create" /></span></a>
                 		</c:if>
                 	</h4>
                 </div>
                 <div class="col-md-12 bottom">
               		<div class="col-md-12 head1">
                			<h5> <a href="${pageContext.request.contextPath}/home"><s:message code="app.link.home"/></a>  /  <s:message code="form.home.paymentGatewayList"></s:message> </h5>
                   	</div>
                   	<div class="col-md-12 head1">
                    	<jsp:directive.include file="alertMessage.jsp" />
						<div class="alert alert-warning" id="errorMessages"></div>
                    </div>
                    
                   	<div class="col-md-12 table-responsive list-data">
						<table class="table table-border" id="paymentgateways" >
							<thead class="head_list">
					            <tr>
					            	<th><s:message code="form.paymentgatewaylist.paymentGatewayName" />
					            	<th><s:message code="form.paymentgatewaylist.paymentGatewayDescription" />
					            	<th><s:message code="form.paymentgatewaylist.createdDate" />
					            	<th><s:message code="form.paymentgatewaylist.action" />
					            </tr>
					        </thead>
					        <tbody class="list">
					            
					        </tbody>
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
	    
		var isAdmin = '${role eq isAdmin}';
		
		$('#errorMessages').hide();
		
		var paymentGatewayTable = $('#paymentgateways').DataTable({
			dom: '<lf<t>ip>',
			"aaSorting": [],
			"pagingType": "full_numbers",
	    	"bProcessing" : false,
           	"bServerSide" : false,
           	"aaSorting": [],
    		responsive: true,
           	"autoWidth": false,
            "ajax": {
                "url": "${pageContext.request.contextPath}/paymentgateway/list",
                "type": "GET",
                "dataType": "json",
                'beforeSend': function (request) {
                	request.setRequestHeader("userId", '${sessionScope["accessToken"].openid}');
					   request.setRequestHeader("accessToken", '${sessionScope["accessToken"].accesstoken}');
                 },
                "cache": false,
                "contentType": "application/json; charset=utf-8",
                "dataSrc": "responsedata",
    	        error : function(xhr, status, error) {
    				$(".dataTables_empty").html('No data available in table');
    			}
            },
            "columns" : [
                { "data": "paymentgatewayname"},
                { "data": "description"},
                { "data": "createdDate"},
                { "render": function(data, type, row, meta){
                		
                	var id = "'"+row.paymentgatewayid+"'";
                	
                	var edit_link = '', view_link = '', delete_link = '';
	            	if(isAdmin == 'true') {
	            		edit_link = '<a href="${pageContext.request.contextPath}/editPaymentGateway/'+row.paymentgatewayid+'" class="action-i" data-toggle="tooltip" title="Edit" data-original-title="Edit"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>';
	            		delete_link = '<a href="#" onclick="return check('+id+');" class="action-i deletePaymentGatewayBtn" data-toggle="tooltip" title="Delete" data-original-title="delete"><i class="fa fa-trash" aria-hidden="true"></i></a>';
	            	}
	            	
	            	view_link = '<a href="${pageContext.request.contextPath}/viewPaymentGatewayPage/'+row.paymentgatewayid+'" class="action-i" data-toggle="tooltip" title="View" data-original-title="View"><i class="fa fa-eye" aria-hidden="true"></i></a>';
	            	
	            	data = edit_link + view_link + delete_link;
               	 	return data;
           		}, "sortable":false}
        	]
	    });
		
	});
	
	function check(paymentGatewayId) {
		
		if (confirm("Are you sure you want to Delete this Payment Gateway? If you delete this Payment Gateway, it might affect in Merchant Gateways of other Merchants.") == true) {
		    
			 if( paymentGatewayId != null){
				$.ajax({
					type : "DELETE",
					url : '${pageContext.request.contextPath}/paymentgateway/delete/'+paymentGatewayId,
					beforeSend: function(request) {
						request.setRequestHeader("userId", '${sessionScope["accessToken"].openid}');
					    request.setRequestHeader("accessToken", '${sessionScope["accessToken"].accesstoken}');
				  	},
					headers : {
						'Content-Type' : 'application/json'
					},
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
			 }
		} else {
			return false;
		}
	}
</script>
</body>
</html>