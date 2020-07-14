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
<div id="contenedor">
<span id="titulo"><fmt:message key="classes"/></span>
<br>

	
	<form:form commandName="clases" method="post">
	<table  id="rounded-corner">
	<thead>
		<tr>
			<th scope="col" class="rounded-company" ><fmt:message key="price"/></th>
			<th scope="col" class="rounded-q1"><fmt:message key="description"/></th>
			<th scope="col" class="rounded-q3"><fmt:message key="especialprice"/></th>
			<th scope="col" class="rounded-q3"><fmt:message key="modify"/></th>
            <th scope="col" class="rounded-q4"><fmt:message key="eliminate"/></th>
		</tr>
		</thead>
		   <tfoot>
    	<tr>
        	<td colspan="2" class="rounded-foot-left"><em><a href="<c:url value="admin_funciones.htm"/>"><fmt:message key="functions"/></a><br><a href="<c:url value="admin_agregarclase.htm"/>"><fmt:message key="add"/> <fmt:message key="class"/></a></em></td>
        	<td><input id="boton" type="submit" value="<fmt:message key="update"/>"></td>
        	<td colspan="1"></td>
        	<td class="rounded-foot-right">&nbsp;</td>
        </tr>
    </tfoot>
    <tbody>
		<c:set var="i" value="${0}"></c:set>
		<c:forEach items="${clases}" var="c">
			<tr>
			<td><fmt:formatNumber value="${c.precio}"  pattern="$###,###"/></td>
			<td><c:out value="${c.descripcion}"/></td>
			<td>
			<form:checkbox path="especial[${i}]"/>
			</td>
			<td><a href="<c:url value="admin_editarclase.htm?idclase=${c.id}"/>"><fmt:message key="modify"></fmt:message></a></td>
			<td><a href="<c:url value="admin_eliminarclase.htm?idclase=${c.id}"/>"><fmt:message key="eliminate"></fmt:message></a></td>
			</tr>
			<c:set var="i" value="${i+1}"></c:set>
		</c:forEach>
		</tbody>
		
	</table>
	</form:form>
	</div>
<jsp:include page="/footer.htm"></jsp:include>
</body>
</html>
