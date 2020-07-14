<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
	<head>
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="datepicker"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="zpcinemas_general"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="form"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="tablas_admin"/>" />
		
		<script type="text/javascript" src="<c:url value="/js/popcalendar.js" />"></script>
		<style type="text/css">
			.error {
				color: red;
			}
		</style>
		<script type="text/javascript">
			function cambiarComplejo() {
				var complejo = document.getElementById('complejo').value;
				window.location = "admin_cambiodesala.htm?id_complejo="+complejo;
			}
		</script>
	</head>
	<body>
		<jsp:include page="/menu2.htm"></jsp:include>
		<div id="contenedor_contenido">
			<h1>Cambio de Sala</h1>
			<hr class="separador_titulo">
			
			<ul>
			<c:forEach items="${resultCambioSala}" var="mensaje">
				<li><c:out value="${mensaje}"/></li>
			</c:forEach>
			</ul>
		</div>
	</body>
</html>