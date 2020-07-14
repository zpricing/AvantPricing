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
</head>
<body>
	
<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor"><span id="titulo"><fmt:message key="movies"/></span> <br>

<div id="form-area" >
	<form:form method="post" commandName="agregarPelicula">
	<fieldset style="width: 220px;">
	<legend><fmt:message key="add"/> <fmt:message key="movie"/></legend>
		<table id="tablaForm">
			<tr>
				<td><fmt:message key="name" /></td>
				<td><form:input path="nombre" /></td>
				<td><form:errors path="nombre" cssClass="error" /></td>
			</tr>
			<tr>
				<td><fmt:message key="duration" /></td>
				<td><form:input path="duracion" /></td>
				<td><form:errors path="duracion" cssClass="error" /></td>
			</tr>
			<tr>
				<td><fmt:message key="id" /> <fmt:message key="central" /></td>
				<td><form:input path="idCentral" /></td>
				<td><form:errors path="idCentral" cssClass="error" /></td>
			</tr>
			<tr>
				<td><fmt:message key="name" /> <fmt:message key="central" /></td>
				<td><form:input path="nombreCentral" /></td>
				<td><form:errors path="nombreCentral" cssClass="error" /></td>
			</tr>
			<tr>
				<td><fmt:message key="similarity" /></td>
				<td><form:input path="gradoSimilitud" /></td>
				<td><form:errors path="gradoSimilitud" cssClass="error" /></td>
			</tr>
			<tr>
				<td><fmt:message key="description" /></td>
				<td><form:input path="descripcion" /></td>
				<td><form:errors path="descripcion" cssClass="error" /></td>
			</tr>
			<tr>
				<td><fmt:message key="category" /></td>
				<td>
					<form:select size="4" path="categorias" multiple="true" id="sel" cssStyle="width:140px;">
						<c:forEach items="${categorias}" var="c">
							<form:option value="${c.id}">
								<c:out value="${c.descripcion}"></c:out>
							</form:option>
						</c:forEach>
					</form:select>
				</td>
				<td><form:errors path="categorias" cssClass="error" /></td>
			</tr>
			<tr>
				<td><fmt:message key="public" /></td>
				<td>
					<form:select path="publicos" multiple="true" id="sel" cssStyle="width:140px;">
						<c:forEach items="${publicos}" var="p">
							<form:option value="${p.id}">
								<c:out value="${p.descripcion}"></c:out>
							</form:option>
						</c:forEach>
					</form:select>
				</td>
				<td><form:errors path="publicos" cssClass="error" /></td>
			</tr>
			<tr>
				<td><fmt:message key="epoch" /></td>
				<td>
					<form:select path="epocas" multiple="true" id="sel" cssStyle="width:140px;">
						<c:forEach items="${epocas}" var="e">
							<form:option value="${e.id}">
								<c:out value="${e.descripcion}"></c:out>
							</form:option>
						</c:forEach>
					</form:select>
				</td>
				<form:errors path="epocas" cssClass="error" />
			</tr>
			<tr>
				<td><fmt:message key="externId" /></td>
				<td><form:input path="idExterno" /></td>
				<td><form:errors path="idExterno" cssClass="error" /></td>
			</tr>
			<tr>
				<td><fmt:message key="active" /></td>
				<td><form:checkbox path="activo" /></td>
			</tr>
			<tr><td>	<c:if test="${testnot}">
						<fmt:message key="${notificacion}"/>
					</c:if></td>
				<td align="right"><br><input id="boton" type="submit" value="<fmt:message key="add"/>"></td>
		
				
			</tr>
		</table>
		</fieldset>
	</form:form>
</div>
</div>
<jsp:include page="/footer.htm"></jsp:include>
</body>
</html>