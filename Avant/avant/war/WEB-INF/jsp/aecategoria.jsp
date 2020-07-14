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

<div id="contenedor"><span id="titulo"><fmt:message
	key="categories" /></span> <br>

<div id="form-area" >
<form:form method="post"
	commandName="agregar">
	<fieldset style="width: 220px;"><legend><fmt:message key="add" /> <fmt:message
		key="category" /></legend>
	<table id="tablaForm">
		<tr>
			<td><fmt:message key="description"/></td>
		</tr>
		<tr>
			<td><form:textarea path="descripcion" /> <form:errors
				path="descripcion" cssClass="error" /> <form:hidden path="id" /></td>
				</tr>
		<tr>
			<td align="right"><input type="submit" id="boton" value="<fmt:message key="add"/>">
			</td>
		</tr>
	</table>
	</fieldset>
</form:form></div>


<form:form method="post" commandName="eliminate">
		<table id="rounded-corner">
		<thead>
			<tr>
			<th scope="col" class="rounded-company"><fmt:message key="id"></fmt:message></th>
			<th scope="col" class="rounded-q1"><fmt:message key="description"></fmt:message></th>
			<th scope="col" class="rounded-q4"><fmt:message key="eliminate"></fmt:message></th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${categorias}" var="c">
			<tr>
				<td><c:out value="${c.id}"></c:out></td>
				<td><c:out value="${c.descripcion}"></c:out></td>
				<td><a
					href="<c:url value="admin_eliminarcategoria.htm?idcategoria=${c.id}"/>"><fmt:message
					key="eliminate"></fmt:message></a></td>
			</tr>
		</c:forEach>
		</tbody>
		 <tfoot>
        <tr>
        	<td colspan="2" class="rounded-foot-left"><em><a href="admin_categorias.htm"><fmt:message key="modify" /> <fmt:message
	key="categories" /></a></em></td>
        	<td class="rounded-foot-right">&nbsp;</td>
        </tr>
    </tfoot>
	</table>
</form:form> 
	</div>
	<jsp:include page="/footer.htm"></jsp:include>
</body>
</html>