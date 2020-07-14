<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="form"/>" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />
<body style="background-color: #000000">
	<div id="contenedor">
		<span id="titulo"><fmt:message key="loadedSuccessfull"></fmt:message></span>
		<br>
		<c:choose>
			<c:when test="${cargadas!=null}">
				<c:forEach items="${cargadas}" var="funcion">
					<c:out value="${funcion.sala.complejoAlCualPertenece.nombre}" /> <c:out value="${funcion.sala.numero}"></c:out> - 
					<c:out value="${funcion.peliculaAsociada.nombre}"></c:out> - 
					<fmt:formatDate value="${funcion.fecha}" pattern="dd/MM/yy HH:mm"/>
					<br/>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${carga==0}">
						<fmt:message key="loadDeactivated"></fmt:message>
					</c:when>
					<c:otherwise>
						<fmt:message key="loadError"></fmt:message>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		<br />
		<div id="tablaForm" align="center">
			<c:choose>
				<c:when test="${popup}">
					<button class="button" id="botonImportante" onclick="javascript:window.close();">
						<fmt:message key="close"></fmt:message>
					</button>
				</c:when>
				<c:otherwise>
					<a class="button" id="botonImportante" href="<c:url value="menu.htm" />">
						<fmt:message key="return"></fmt:message>
					</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>