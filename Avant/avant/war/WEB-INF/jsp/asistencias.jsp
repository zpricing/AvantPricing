<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
		<title><fmt:message key="pageTitle" /></title>
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="form"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="tablas_admin"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="cmxform"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="zpcinemas_general"/>" />
		
		<script type="text/javascript" src="assets/jquery/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="assets/cmxform/js/cmxform.js"></script>
	</head>
<body>
	<div id="contenedor_contenido">
		<h1><fmt:message key="attendances" /></h1>
		
		<form:form method="post" commandName="agregar" cssClass="cmxform">
			<fieldset>
				<legend><fmt:message key="function" /> <c:out value="${funcion.id}"/></legend> 
				<ol>
					<li>
						<label><fmt:message key="pelicula" /> :</label> <c:out value="${funcion.peliculaAsociada.descripcion}"/>
					</li>
					<li>
						<label><fmt:message key="room" /> :</label> <c:out value="${funcion.sala.numero}"/>
					</li>
					<li>
						<label><fmt:message key="date" /> :</label> <fmt:formatDate value="${funcion.fecha}" type="both" pattern="dd-MM-yyyy HH:mm" />
					</li>
					<li>
						<label><fmt:message key="mascara" /> :</label>
						<form:select path="mascara">
							<form:option value="0"><fmt:message key="seleccionmascara" /></form:option>
							<form:options items="${mascaras}" itemValue="id" itemLabel="descripcion"/>
						</form:select>
					</li>
					<li class="centrado">
						<input type="submit" value="<fmt:message key="update" />">
					</li>
				</ol>
			</fieldset>
		</form:form>
	</div>
</body>
</html>