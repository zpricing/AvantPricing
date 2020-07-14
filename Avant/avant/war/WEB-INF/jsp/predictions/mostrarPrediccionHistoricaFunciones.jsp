<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><fmt:message key="pageTitle" /></title>
		
		<link rel="stylesheet" type="text/css" media="screen"
			href="<spring:theme code="tablas_admin"/>" />
			
		<script language="JavaScript"
			src="<c:url value="/FusionCharts/FusionCharts.js"/>"></script>
	</head>
	<body style="background-color: black;">
		<div id="contenedor" style="width: auto;"><span id="titulo"><fmt:message key="prediction" /></span> <br>
			<fmt:message key="complex" />: <c:out value="${complejo.nombre}"></c:out><br />
			<fmt:message key="movie" />: <c:out value="${pelicula.nombre}"></c:out><br />
			
			<jsp:include page="../FusionChartsRenderer.jsp" flush="true">
				<jsp:param name="chartSWF" value="FusionCharts/FCF_MSLine.swf" />
				<jsp:param name="strURL" value="" />
				<jsp:param name="strXML" value="${grafico.XML}" />
				<jsp:param name="chartId" value="PrediccionvsRealFuncion" />
				<jsp:param name="chartWidth" value="${grafico.width}" />
				<jsp:param name="chartHeight" value="300" />
				<jsp:param name="debugMode" value="true" />
				<jsp:param name="registerWithJS" value="true" />
			</jsp:include>
			
			<table id="rounded-corner">
				<tr>
					<th>
						<fmt:message key="date" />
					</th>
					<c:forEach items="${horas}" var="hora">
						<th>
							<c:if test="${hora!=null}">
								<fmt:formatDate value="${hora}" type="both" pattern="dd/MM/yy HH:mm"/>
							</c:if>
							<c:if test="${hora==null}">
								N/A
							</c:if>
						</th>
					</c:forEach>
				</tr>
				<tr>
					<td>
						<fmt:message key="prediction" />
					</td>
					<c:forEach items="${predicciones}" var="pred" varStatus="i">
						<td>
							<c:out value="${pred}"></c:out>
						</td>
					</c:forEach>
				</tr>
				<tr>
					<td>
						<fmt:message key="real" />
					</td>
					<c:forEach items="${asistencias}" var="asistencia">
						<td>
							<c:out value="${asistencia}"></c:out>
						</td>
					</c:forEach>
				</tr>
				<tr>
					<td>
						<fmt:message key="error" />
					</td>
					<c:forEach items="${error}" var="e">
						<td>
							<c:out value="${e }" />
						</td>
					</c:forEach>
				</tr>
			</table>
		</div>
	</body>
</html>