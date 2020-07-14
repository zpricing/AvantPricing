<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="pageTitle" /></title>
<script type="text/javascript" src="<c:url value="/js/background.js" />" > </script>
		
		<script type="text/javascript" src="<c:url value="/js/tabber.js" />" ></script>
		<link rel="stylesheet" href="<c:url value="/styles/tabber.css" />" TYPE="text/css" MEDIA="screen">
		<link rel="stylesheet" href="<c:url value="/styles/tabber-print.css" />" TYPE="text/css" MEDIA="print">
		<script type="text/javascript">

			/* Optional: Temporarily hide the "tabber" class so it does not "flash"
			   on the page as plain HTML. After tabber runs, the class is changed
			   to "tabberlive" and it will appear. */
			
			document.write('<style type="text/css">.tabber{display:none;}<\/style>');

		
		</script>
		
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="form"/>" />
	<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />
<script language="JavaScript" src="<c:url value="/FusionCharts/FusionCharts.js"/>"></script>
<style type="text/css">
#reporteEmbebido {
 border: 0px;
 height: 1290px;
 width: 98%;
 padding: 1%;
</style>
</head>
<body>
<jsp:include page="/menu2.htm"></jsp:include>
<div id="contenedor"><span id="titulo">
<fmt:message key="${nombreReporte}"/>
</span> <br>
<iframe id="reporteEmbebido" name="reporteEmbebido" src="<c:out value="${reportURL }"/>"></iframe>
<c:if test="${!ocultarExportacion}">
<p>
	<fmt:message key="exportAs"/>: 
	<a href="<c:out value="${reportURL}"/>&rs:Format=PDF">PDF</a> | 
	<a href="<c:out value="${reportURL}"/>&rs:Format=Word">Word</a> | 
	<a href="<c:out value="${reportURL}"/>&rs:Format=Excel">Excel</a> |
	<a href="<c:out value="${reportURL}"/>&rs:Format=CSV">CSV</a> |  
</p>
</c:if>
</div>
</body>
</html>