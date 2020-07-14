<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><fmt:message key="pageTitle" /></title>
		
		<link rel="stylesheet" type="text/css" media="screen"
			href="<spring:theme code="tablas_admin"/>" />
	</head>
	<body style="background-color: black;">
		<div id="contenedor" style="width: 900px;"><span id="titulo"><fmt:message key="prediction" /></span> <br>
			<div id="form-area" style="width: 900px; overflow: auto;">
				<form:form method="post" commandName="editarprediccionesdia">
					<table id="rounded-corner">
						<thead>
							<tr>
								<th nowrap="nowrap">
									<fmt:message key="movie" /> <c:out value="${p.pelicula.nombre}"></c:out><br>
									<fmt:message key="complex" /> <c:out value="${p.complejo.nombre}"></c:out>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${p.prediccionPorDia}" var="pd">
								<tr>
									<td nowrap="nowrap">
										<fmt:message key="day"/> <fmt:formatDate value="${pd.fecha}"/><br>
										<fmt:message key="estimation"/> <c:out value="${pd.estimacion}"></c:out>
									</td>
									<c:choose>
										<c:when test="${not empty pd.prediccionesPorFuncion}">
											<c:forEach items="${pd.prediccionesPorFuncion}" var="pf">
												<td nowrap="nowrap">
													<fmt:message key="hour"/> - <fmt:formatDate value="${pf.funcionPredecida.fecha}" type="time" timeStyle="short"/><br>
													<fmt:message key="estimation"/> - <c:out value="${pf.estimacion}"></c:out><br>
													<fmt:message key="variance"/> - <c:out value="${pf.varianza}"></c:out>
												</td>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<td>
												<fmt:message key="day" /> <fmt:message key="without" /> <fmt:message key="functions" />
											</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form:form>
			</div>
			
			<c:if test="${fn:length(p.prediccionParametros) > 0}">
	<div align="center">
	<table id="rounded-corner" style="width: 400px; margin-left: 0px">
		<thead>
			<tr>
				<th class="rounded-company"><fmt:message key="movieUsed"></fmt:message></th>
				<th class="rounded-q4"><fmt:message key="movieWeight" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${p.prediccionParametros}"
				var="thisPrediccionParametros">
				<tr>
					<td><c:if
						test="${thisPrediccionParametros.pelicula.id != p.pelicula.id }">
						<a
							href="asistenciaporpelicula.htm?peliculaId=<c:out value="${thisPrediccionParametros.pelicula.id}"/>"
							rel="external"> <c:out
							value="${thisPrediccionParametros.pelicula.nombre}" /> </a>
					</c:if> <c:if
						test="${thisPrediccionParametros.pelicula.id == p.pelicula.id }">
						<c:out value="${thisPrediccionParametros.pelicula.nombre}" />
				 (<fmt:message key="last" />
						<fmt:message key="prediction" />)
				</c:if><c:if
						test="${thisPrediccionParametros.pelicula.id != p.pelicula.id }"></c:if></td>
					<td><c:out value="${thisPrediccionParametros.ponderacion}" />

					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</c:if>
		</div>
	</body>
</html>