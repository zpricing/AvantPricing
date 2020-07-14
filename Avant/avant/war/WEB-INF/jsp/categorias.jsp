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

<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>

<script type='text/javascript'>
	function update() {
		dwr.util.setValue("text",dwr.util.getText("sel"));
		
	}
</script>


</head>
<body onload="update()">
		
<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor">
<span id="titulo"><fmt:message key="categories" /></span>
<br>
<div id="form-area" >
	
	<form:form method="post" commandName="categorias" cssClass="formStyle">
	<fieldset style="width: 210px;">
	<legend><fmt:message key="modify"/> <fmt:message key="categories" /></legend>
		<table id="tablaForm">
			<tr>
				<td><fmt:message key="select2" /><br>
					<form:select path="id" id="sel" onchange="update()">
						<c:forEach items="${categorias}" var="c">
							<form:option value="${c.id}" >
								<c:out value="${c.descripcion}"></c:out>
							</form:option>
						</c:forEach>
					</form:select>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="new" /> <fmt:message key="name" /><br>
				<form:textarea path="descripcion" id="text" cssStyle="font-family: 'Lucida Sans Unicode', 'Lucida Grande', Sans-Serif; font-size: 12px;"/></td>
				<td><form:errors path="descripcion" cssClass="error" /></td>
			</tr>
			<tr>
				<td><input type="submit" id="boton" value="<fmt:message key="modify"/>"></td>
			</tr>
		</table>
		</fieldset>
	</form:form>

<br>
<a href="admin_aecategoria.htm"><fmt:message key="add" />/<fmt:message key="eliminate" /> <fmt:message key="categories" /></a><br>
<a href="<c:url value="admin_peliculas.htm"/>"><fmt:message key="movies" /></a>
<br>
<br>
</div>
</div>
</body>
</html>
