<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title><fmt:message key="pageTitle" /></title>
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="form"/>" />
	<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />
<style type="text/css">
.error {
	color: red;
}
</style>

<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
</head>
<body>
		
<jsp:include page="/menu2.htm"></jsp:include>
<div id="contenedor">
<span id="titulo"><fmt:message key="error.notAuthorized" /></span>
<br/>
<div id="form-area" >
<sec:authorize ifNotGranted="ROLE_ADMIN">
	<fmt:message key="error.notAuthorizedExpl"/>
</sec:authorize>
</div>
</div>
</body>
</html>