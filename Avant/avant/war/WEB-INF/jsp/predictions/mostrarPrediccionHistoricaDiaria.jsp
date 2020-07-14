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

<script language="JavaScript"
	src="<c:url value="/FusionCharts/FusionCharts.js"/>"></script>
<script type="text/javascript">
function externalLinks() {  
 if (!document.getElementsByTagName) return;  
 var anchors = document.getElementsByTagName("a");  
 for (var i=0; i<anchors.length; i++) {  
   var anchor = anchors[i];  
   if (anchor.getAttribute("href") &&  
       anchor.getAttribute("rel") == "external")  
     anchor.target = "_blank";  
 }  
}  
window.onload = externalLinks;
</script>
<style>
.data {
padding-left: 15px;
font-size: large;
color: rgb(40,40,40);
}
.data > .enfasis {
font-weight: bold;
color: #000000;
}
</style>
</head>
<body style="background-color: black;">
<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor" style="width: auto;"><span id="titulo"><fmt:message
	key="prediction" /> <c:out value="${prediccion.id}"/></span> <br>
<div class="data">
<span class="enfasis"><fmt:message key="complex" /></span>: <c:out value="${complejo.nombre}"></c:out>
</div>
<div class="data">
<span class="enfasis"><fmt:message key="movie" /></span>: <c:out value="${pelicula.nombre}"></c:out>
</div>
<jsp:include page="../FusionChartsRenderer.jsp" flush="true">
	<jsp:param name="chartSWF" value="FusionCharts/FCF_MSLine.swf" />
	<jsp:param name="strURL" value="" />
	<jsp:param name="strXML" value="${grafico.XML}" />
	<jsp:param name="chartId" value="PrediccionvsReal" />
	<jsp:param name="chartWidth" value="${grafico.width}" />
	<jsp:param name="chartHeight" value="300" />
	<jsp:param name="debugMode" value="true" />
	<jsp:param name="registerWithJS" value="true" />
</jsp:include>

<table id="rounded-corner">
	<thead>
		<tr>
			<th class="rounded-company"><fmt:message key="day" /></th>
			<c:forEach items="${predsPorDia}" var="ppd" varStatus="ppdStatus">
				<th <c:if test="${!ppdStatus.last}">class="rounded-q1"</c:if>
					<c:if test="${ppdStatus.last}">class="rounded-q4"</c:if>><a
					href="<c:url value="mostrarPrediccionHistoricaFunciones.htm?idPrediccionDia=${ppd.id}" />"><fmt:formatDate
					value="${ppd.fecha}" pattern="EE dd-MM-yy" /></a></th>
			</c:forEach>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><fmt:message key="prediction" /></td>
			<c:forEach items="${predicciones}" var="pred" varStatus="i">
				<td><c:out value="${pred}"></c:out></td>
			</c:forEach>
		</tr>
		<tr>
			<td><fmt:message key="real" /></td>
			<c:forEach items="${reales}" var="real">
				<td><c:out value="${real}"></c:out></td>
			</c:forEach>
		</tr>
		<tr>
			<td><fmt:message key="error" /></td>
			<c:forEach items="${error}" var="e">
				<td><c:out value="${e}" /></td>
			</c:forEach>
		</tr>
	</tbody>
</table>

<c:if test="${fn:length(prediccion.prediccionParametros) > 0}">
	<div align="center">
	<table id="rounded-corner" style="width: 400px; margin-left: 0px">
		<thead>
			<tr>
				<th class="rounded-company"><fmt:message key="movieUsed"></fmt:message></th>
				<th class="rounded-q4"><fmt:message key="movieWeight" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${prediccion.prediccionParametros}"
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
</c:if></div>
</body>
</html>