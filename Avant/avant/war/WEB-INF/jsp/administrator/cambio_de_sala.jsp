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
			<form:form method="post" commandName="cambioDeSalaForm">
				<ul style="list-style: none;width: 600px;">
					<li style="width: 600px;">
						<label for="select_complejo">Complejo: </label>
						<form:select path="complejo" onchange="javascript:cambiarComplejo()">
							<option value="">Seleccione Complejo</option>
							<c:forEach items="${lista_complejos}" var="complejo">
								<form:option value="${complejo.id}"><c:out value="${complejo.nombre}"/></form:option>
							</c:forEach>
						</form:select>
						<form:errors path="complejo" cssClass="error" />
					</li>
					<c:if test="${complejo != null}">
						<li>
							<label for="select_sala_1">Sala 1: </label>
							<form:select path="salaSwap1">
								<option value="">Seleccion Sala</option>
								<c:forEach items="${lista_salas}" var="sala">
									<form:option value="${sala.id}"><c:out value="${sala.numero}"/></form:option>
								</c:forEach>
							</form:select>
							<form:errors path="salaSwap1" cssClass="error" />
						</li>
						<li>
							<label for="select_sala_2">Sala 2: </label>
							<form:select path="salaSwap2">
								<option value="">Seleccion Sala</option>
								<c:forEach items="${lista_salas}" var="sala">
									<form:option value="${sala.id}"><c:out value="${sala.numero}"/></form:option>
								</c:forEach>
							</form:select>
							<form:errors path="salaSwap2" cssClass="error" />
						</li>
						<li>
							<label>Fecha desde: </label> <form:input path="fechaDesde" /> <img class="link" name="calendar1" title="calendar" alt="calendar" src="<c:url value="/images/calendar/cal.jpg"/>" width="20" height="20" onclick="javascript:popUpCalendar(this,document.getElementById('fechaDesde'), 'dd-mm-yyyy');" />
							<form:errors path="fechaDesde" cssClass="error" />
						</li>
						<li>
							<label>Fecha hasta: </label> <form:input path="fechaHasta" /> <img class="link" name="calendar1" title="calendar" alt="calendar" src="<c:url value="/images/calendar/cal.jpg"/>" width="20" height="20" onclick="javascript:popUpCalendar(this,document.getElementById('fechaHasta'), 'dd-mm-yyyy');" />
							<form:errors path="fechaHasta" cssClass="error" /> 
						</li>
						<li>
							<input type="submit" value="Procesar">
						</li>
					</c:if>
				</ul>
			</form:form>
		</div>
	</body>
</html>