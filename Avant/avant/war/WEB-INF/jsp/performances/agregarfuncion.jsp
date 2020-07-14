<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title><fmt:message key="pageTitle" /></title>
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="datepicker"/>" />
<script type="text/javascript" src="<c:url value="/js/popcalendar.js" />"></script>

<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="form"/>" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />
<style type="text/css">
.error {
	color: red;
}
</style>

</head>
<body>

<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor"><span id="titulo"><fmt:message key="functions" /></span> <br>

<div id="form-area">
<form:form method="post" commandName="agregarFuncion">
<fieldset style="width: 550px;">
	<legend><fmt:message key="add" /> <fmt:message key="function" /></legend>
	<table id="tablaForm">
		<tr>
			<td><fmt:message key="date" /></td>
			<td>
			<form:input path="fecha" readonly="true" cssClass="fecha" />
			<img name="calendar1" title="calendar" alt="calendar" src="<c:url value="/images/calendar/cal.jpg"/>" width="20" height="20" onclick="javascript:popUpCalendar(this,document.getElementById('fecha'), 'dd-mm-yyyy');" />
			<fmt:message key="hour" />
			<form:select path="hora">
					<c:forEach begin="0" end="23" var="h">
						<form:option value="${h}">
							<c:out value="${h}"></c:out>
						</form:option>
					</c:forEach>
				</form:select>
			<fmt:message key="minutes" />
			<form:select path="min">
					<c:forEach begin="0" end="59" var="m">
						<form:option value="${m}">
							<c:out value="${m}"></c:out>
						</form:option>
					</c:forEach>
				</form:select>
				<form:errors path="min" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="movie" /></td>
			<td colspan="10">
				<form:select path="pelicula" id="sel">
					<c:forEach items="${peliculas}" var="p">
						<form:option value="${p.id}">
							<c:out value="${p.nombre}"></c:out>
						</form:option>
					</c:forEach>
				</form:select>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="room" /></td>
			<td>
				<form:select path="sala" id="sel" cssStyle="width:150px;">
					<c:forEach items="${salas}" var="s">
						<form:option value="${s.id}">
							<c:out value="${s.id}"></c:out>
							<c:out value="${s.complejoAlCualPertenece.nombre}"></c:out>
							<c:out value="${s.numero}"></c:out>
						</form:option>
					</c:forEach>
				</form:select>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="functionType" /></td>
			<td>
				<form:select path="tipofuncion" id="sel" cssStyle="width:150px;">
					<c:forEach items="${tipofunciones}" var="tf">
						<form:option value="${tf.id}">
							<c:out value="${tf.descripcion}"></c:out>
						</form:option>
					</c:forEach>
				</form:select>
			</td>
		</tr>
		<tr><td></td>
		<td></td>
			<td align="right"><input type="submit" id="boton" value="<fmt:message key="add"/>"></td>
		</tr>
		<tr>
			<td>
				<c:if test="${testnot}">
					<fmt:message key="${notificacion}"/>
				</c:if>
			</td>
		</tr>
	</table>
	</fieldset>
</form:form>
<a href="<c:url value="admin_funciones.htm"/>"><fmt:message key="functions" /></a>
<br>
<br>

</div>
</div>
<jsp:include page="/footer.htm"></jsp:include>
</body>
</html>