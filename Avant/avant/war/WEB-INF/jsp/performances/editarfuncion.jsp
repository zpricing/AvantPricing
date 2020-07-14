<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title><fmt:message key="pageTitle" /></title>

<style type="text/css">
.error {
	color: red;
}
</style>

</head>
<body>

<h1><fmt:message key="modify" /> <fmt:message key="function" /></h1>
<form:form method="post" commandName="editarFuncion">
	<table id="tablaForm">
		<form:hidden path="id"/>
		<tr>
			<td><fmt:message key="day" /></td>
			<td><form:select path="dia">
				<c:forEach begin="1" end="31" var="d">
					<form:option value="${d}">
						<c:out value="${d}"></c:out>
					</form:option>
				</c:forEach>
			</form:select></td>
			<td><fmt:message key="month" /></td>
			<td>
				<form:select path="mes">
					<c:forEach begin="1" end="12" var="m">
						<form:option value="${m-1}">
							<c:out value="${m}"></c:out>
						</form:option>
					</c:forEach>
				</form:select>
			</td>
			<td><fmt:message key="year" /></td>
			<td>
				<form:select path="ano">
					<c:forEach begin="2005" end="${year}" var="a">
						<form:option value="${a}">
							<c:out value="${a}"></c:out>
						</form:option>
					</c:forEach>
				</form:select>
			</td>
			<td><fmt:message key="hour" /></td>
			<td>
				<form:select path="hora">
					<c:forEach begin="0" end="23" var="h">
						<form:option value="${h}">
							<c:out value="${h}"></c:out>
						</form:option>
					</c:forEach>
				</form:select>
			</td>
			<td><fmt:message key="minutes" /></td>
			<td>
				<form:select path="min">
					<c:forEach begin="0" end="59" var="m">
						<form:option value="${m}">
							<c:out value="${m}"></c:out>
						</form:option>
					</c:forEach>
				</form:select>
			</td>
			<td><form:errors path="min" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="movie" /></td>
			<td>
				<form:select path="pelicula">
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
				<form:select path="sala">
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
				<form:select path="tipofuncion">
					<c:forEach items="${tipofunciones}" var="tf">
						<form:option value="${tf.id}">
							<c:out value="${tf.descripcion}"></c:out>
						</form:option>
					</c:forEach>
				</form:select>
			</td>
		</tr>
		<tr>
			<td><input type="submit" value="<fmt:message key="modify"/> <fmt:message key="function"/>"></td>
		</tr>
		<tr>
			<td>
				<c:if test="${testnot}">
				<fmt:message key="${notificacion}"/>
				</c:if>
			</td>
		</tr>
	</table>
</form:form>

<a href="<c:url value="admin_funciones.htm"/>"><fmt:message
	key="functions" /></a>
<jsp:include page="/footer.htm"></jsp:include>
</body>
</html>