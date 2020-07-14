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

<script type='text/javascript' src='<c:url value="/dwr/interface/Util.js"/>'> </script>
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
<span id="titulo"> <fmt:message key="functions" /></span>
<br>
<div id="form-area" >
	<form:form method="post" commandName="tipofuncion">
<fieldset style="width: 160px;">
	<legend><fmt:message key="modify" /> <fmt:message key="functionType" /></legend>

	<table id="tablaForm">
		<tr>
			<td><fmt:message key="select" /><br>
				<form:select path="id" id="sel" onchange="update()">
					<c:forEach items="${tipofuncion}" var="tf">
						<form:option value="${tf.id}">
							<c:out value="${tf.descripcion}"></c:out>
						</form:option>
					</c:forEach>
				</form:select>
			</td>
		</tr>
		<tr>
			<td>
			<fmt:message key="new" /> <fmt:message key="name" /><br>
			<form:input path="descripcion" id="text"/></td>
			<td><form:errors path="descripcion" cssClass="error" /></td>
		</tr>
		<tr><td align="right"><input id="boton" type="submit" value="<fmt:message key="modify"/>"></td></tr>
	</table>
	
</fieldset>
</form:form>
<br>
<a href="admin_aetipofuncion.htm"><fmt:message key="add" />/<fmt:message key="eliminate" /> <fmt:message key="functionTypes" /></a><br>
<a href="<c:url value="admin_funciones.htm"/>"><fmt:message key="functions"/></a>
<br>
<br>
</div>
</div>
</body>
</html>
