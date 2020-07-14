<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="jquery.superbox"/>" />
		<link rel="stylesheet" href="<c:url value="/styles/tabber.css" />" TYPE="text/css" MEDIA="screen">
		<link rel="stylesheet" href="<c:url value="/styles/tabber-print.css" />" TYPE="text/css" MEDIA="print">
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="form"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="tablas_admin"/>" />
		
		<script type="text/javascript" src="<c:url value="/js/showhide.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/tabber.js" />" >No pezca javascript</script>
		<script type="text/javascript" src="assets/jquery/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="assets/jquery/jquery-superbox-0.9.1/jquery.superbox-min.js"></script>
		
		<style>
			ul.nueva_mascara {
				list-style: none;
			}
			ul.nueva_mascara li {
				margin-top: 10px;
			}
		</style>
	</head>
	<body>
		<div id="contenedor">
			<span id="titulo">Crear Nueva Mascara</span>
			<br>
			<form:form commandName="mascara">
				<ul class="nueva_mascara">
					<li>Complejo: <c:out value="${mascara.complejo.nombre}"/></li>
					<li>Descripcion: <form:input path="descripcion"/></li>
					<li>
						<table id="rounded-corner">
							<thead>
								<tr>
									<th class="rounded-company">Area</th>
									<th>Capacidad (%)</th>
									<th class="rounded-q4">Expiraci&oacute;n</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${mascara.mascarasAreas}" var="mascaraArea" varStatus="i">
									<tr>
										<td><c:out value="${mascaraArea.area.descripcion}"/></td>
										<td><form:input path="mascarasAreas[${i.index}].capacidad" onchange="ajusteRestante()"/></td>
										<td><form:input path="mascarasAreas[${i.index}].diasExpiracion"/></td>
									</tr>
								</c:forEach>
								<tr>
									<td>Tradicional</td>
									<td><input type="text" readonly="true" name="general" id="general"/></td>
									<td>n/a</td>
								</tr>
							</tbody>
						</table>
					</li>
					<li><input type="submit" value="Guardar"/></li>
				</ul>
			</form:form>
		</div>
	</body>
</html>