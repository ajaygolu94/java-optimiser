<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
	<jsp:directive.include file="include_metatags.jsp" />
	<title><s:message code="app.title.merchantlist" arguments="${applicationScope['APPLICATION_HEADER_TEXT']}"/></title>
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
                 	<h4><s:message code="app.title.listmerchant"/>   
	                 	<c:if test="${role eq isMerchantManager}">
	                 		 |	<a href="${pageContext.request.contextPath}/addMerchant" > 
	                 		 <span style="text-decoration: underline;"><s:message code="form.merchant.create"/></span></a>
	                 	</c:if>
                 	</h4>
                 </div>
                 <div class="col-md-12 bottom">
               		<div class="col-md-12 head1">
                			<h5> <a href="${pageContext.request.contextPath}/home"><s:message code="app.link.home"/></a>  /  <s:message code="app.title.listmerchant"></s:message> </h5>
                   	</div>
                   	
                   	<div class="col-md-12 head1">
                  		<jsp:directive.include file="alertMessage.jsp" />
						<div class="alert alert-warning" id="errorMessages" style="display: none;"></div>
                   	</div>
                    
                   	<div class="col-md-12 table-responsive list-data">
                   		
					<table class="table table-border" id="merchants">
						<thead class="head_list">
							<tr>
				            	<th><s:message code="form.merchantlist.userName" /></th>
				            	<th><s:message code="form.merchantlist.createdDate" /></th>
				            	<th><s:message code="form.merchantlist.merchantName" /></th>
				            	<th><s:message code="form.merchantlist.sourceMerchantId" /></th>
				            	<th><s:message code="form.merchantlist.action" /></th>
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
	    
		var isMerchantManager = '${role eq isMerchantManager}';
		
		$('#errorMessages').hide();
		var merchantTable = $('#merchants').DataTable({
		    dom: '<lf<t>ip>',
		    "aaSorting": [],
           	"pagingType": "full_numbers",
	    	"bProcessing" : false,
           	"bServerSide" : false,
    		"responsive": true,
           	"autoWidth": true,
           	"autoHeight": true,
           	"ajax": {
               	"url": "merchant/list",
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
               	{ "data": "userName", "sortable":false},
               	{ "data": "createddate"},
               	{ "data": "merchantname"},
               	{ "data": "sourcemerchantid"},
               	{ "render": function(data, type, row, meta){

               		var id = "'"+row.merchantId+"'";
                	
               		var edit_link = '', view_link = '', delete_link = '';
               		
               		if(isMerchantManager == 'true') {
               			edit_link = '<a href="${pageContext.request.contextPath}/editMerchant/'+row.merchantId+'" class="action-i" data-toggle="tooltip" title="Edit" data-original-title="Edit"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>'; 			
               			delete_link = '&nbsp;&nbsp;<a href="#" onclick="return check('+id+');" id="deleteMerchantBtn" class="action-i deleteMerchantBtn" data-toggle="tooltip" title="Delete" data-original-title="delete"><i class="fa fa-trash" aria-hidden="true"></i></a>';
               		}
               		
               		var view_link = '&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/merchantView/'+row.merchantId+'" class="action-i" data-toggle="tooltip" title="View" data-original-title="View"><i class="fa fa-eye" aria-hidden="true"></i></a>';
               		
               	 	data = edit_link + view_link + delete_link +'&nbsp;&nbsp;<a class="action-i" href="${pageContext.request.contextPath}/merchantgateway/'+row.merchantId+'">Merchant Gateways</a>&nbsp;|&nbsp;<a class="action-i" href="${pageContext.request.contextPath}/merchantrules/'+row.merchantId+'">Merchant Rules</a>';
               	 	return data;
           		}, "sortable":false}
       		]
	    });
		
	});
	
	function check(merchantId) {
		
		if (confirm("Are you sure you want to Delete this Merchant?") == true) {
		    
			 if( merchantId != null){
				$.ajax({
					type : "DELETE",
					url : '${pageContext.request.contextPath}/merchant/delete/'+merchantId,
					beforeSend: function(request) {
						request.setRequestHeader("userId", '${sessionScope["accessToken"].openid}');
					    request.setRequestHeader("accessToken", '${sessionScope["accessToken"].accesstoken}');
				  	},
					headers : {
						'Content-Type' : 'application/json'
					},
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
			 }
		} else {
			return false;
		}
	}
</script>
</body>
</html>