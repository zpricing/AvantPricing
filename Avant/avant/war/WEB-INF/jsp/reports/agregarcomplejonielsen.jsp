<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
<script src="/ZPCinemasDev/js/popcalendar.js" type="text/javascript"></script>
</head>
<body>
	
<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor"><span id="titulo"><fmt:message key="add"/> <fmt:message key="new"/> <fmt:message key="complex"/></span> <br>

<div id="form-area" >
	<form:form method="post" commandName="AgregarComplejoNielsen">
	<fieldset style="width: 220px;">
	<legend><fmt:message key="add"/> <fmt:message key="complex"/></legend>


	<table id="tablaForm">
		<tr>
			<td>Id:</td>
			<td>
			<c:if test="${idComplejoIsSet != null}"><form:input path="id" disabled="true" /> </c:if>
			<c:if test="${idComplejoIsSet == null}">
				<form:input path="id" /> 
			</c:if></td>
		</tr>
		<tr>
			<td><fmt:message key="name"/></td>
			<td><form:input path="nombre"></form:input></td>
		</tr>
		<tr>
			<td><fmt:message key="date"/></td>
			<td><form:input id="fechaDesde" path="fechaDesde" size="12"></form:input> <img width="20" height="20" onclick="javascript:popUpCalendar(this,document.getElementById('fechaDesde'), 'dd-mm-yyyy');" src="/ZPCinemasDev/images/calendar/cal.jpg" alt="calendar" title="calendar" name="calendar1" id="calendar1"></td>
		</tr>
		<tr>
			<td><fmt:message key="roomAmount"/></td>
			<td><form:input path="cantidadSalas"></form:input></td>
		</tr>
		<tr>
			<td><fmt:message key="enterprise"/></td>
			<td><select name="empresaId">
				<c:forEach items="${rptEmpresas}" var="empresa">
					<option value="${empresa.rpt_empresa_id}"><c:out
						value="${empresa.codigo_nielsen}" /> <c:if
						test="${empresa.nombre != null && empresa.nombre != '' }">
			 (<c:out value="${empresa.nombre}" />)
			</c:if></option>
				</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td><fmt:message key="city"/></td>
			<td><form:input path="ciudad"></form:input></td>
		</tr>
		<tr>
			<td title="<fmt:message key="isRmExplanation"/>">RM:</td>
			<td><form:checkbox path="rm"/> </td>
		</tr>
		<tr>
			<td></td>
			<td>
				<input id="boton" type="submit" value="<fmt:message key="add"/>">
			</td>
		</tr>
	</table>

	</fieldset>
	</form:form>
</div>
</div>
<jsp:include page="/footer.htm"></jsp:include>
</body>
</html>