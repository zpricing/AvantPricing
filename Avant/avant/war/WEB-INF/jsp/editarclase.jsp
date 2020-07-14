<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<title><fmt:message key="pageTitle" /></title>
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="form"/>" />
	<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />
			<style type="text/css">
				.error { color: red; }
			</style>

</head>
<body>

<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor">
<span id="titulo"><fmt:message key="classes" /></span>
<br>
<div id="form-area">
<form:form method="post" commandName="editar">
<fieldset style="width: 200px;">
	<legend><fmt:message key="modify" /> <fmt:message key="class" /></legend>

<table id="tablaForm">
	<tr>
		<td><fmt:message key="price" /></td>
		<td><form:input path="precio"/></td>
		<td><form:errors path="precio" cssClass="error"/></td>
	</tr>
	<tr>
		<td><fmt:message key="description" /></td>
		<td><form:input path="descripcion"/></td>
		<td><form:errors path="descripcion" cssClass="error"/></td> 
		<form:hidden path="id"/>
	</tr>
	<tr>
	<td></td>
		<td align="right"><input id="boton" type="submit" value="<fmt:message key="modify"/>"></td>
	</tr>
</table>
</fieldset>
</form:form>

<a href="<c:url value="admin_clases.htm"/>"><fmt:message key="classes"/></a>
<br>
</div>
</div>
<jsp:include page="/footer.htm"></jsp:include>
</body>
</html>