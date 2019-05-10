<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="${pageContext.request.contextPath}/${applicationScope['APPLICATION_CSS']}" rel="stylesheet" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/${applicationScope['FAVICON_IMAGE']}" />

<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->
<link href="${pageContext.request.contextPath}/css/DataTables-1.10.8/css/jquery.dataTables.min.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/bootstrap-toggle.css" rel="stylesheet" />

<!-- Set the All Role supported in Optimiser  -->
<c:set var="isAdmin" value="Admin"/>
<c:set var="isMerchant" value="Merchant"/>
<c:set var="isMerchantManager" value="Merchant Manager"/>