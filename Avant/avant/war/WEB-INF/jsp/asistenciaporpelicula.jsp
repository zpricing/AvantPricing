<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"%>
<%@page import="cl.zpricing.avant.model.AsistenciaDiaria"%><html>
<head>
<title><fmt:message key="pageTitle" /></title>
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="datepicker"/>" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="form"/>" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />
<style type="text/css">
<
style>.error {
	color: red;
}
</style>
<script language="JavaScript"
	src="<c:url value="/FusionCharts/FusionCharts.js"/>"></script>

</head>
<body>
<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor"><span id="titulo"><fmt:message
	key="attendance" /> <fmt:message key="by" /> <fmt:message
	key="movie" /></span> <br>
<c:choose>
	<c:when test="${error!=null}">
		<fmt:message key="${error}" /> <c:out value="${peliculaId }"/>
	</c:when>
</c:choose> <c:choose>
	<c:when test="${asistenciaDiaria==null}">
		<p><fmt:message key="error.notsuchmovie"/></p>
		<form action="" method="get"><input type="text" id="peliculaId"
			name="peliculaId"></input><input type="submit" value="<fmt:message key="search"/>" /></form>

	</c:when>
	<c:otherwise>
		<p><fmt:message key="movie" />: <c:out value="${nombrePelicula}" />
		(<fmt:message key="between" /> <fmt:formatDate
			value="${fechaEstreno}" pattern="dd-MM-yyyy" /> <fmt:message
			key="and" /> <fmt:formatDate value="${fechaTermino}"
			pattern="dd-MM-yyyy" />)</p>
		<div id="chartdiv" align="center"></div>
		<script type="text/javascript">
var myChart = new FusionCharts("<c:url value="/FusionCharts/FCF_Line.swf"/>", "myChartId", "900", "300", "0", "0");
myChart.setDataXML("<c:out value="${xml}"/>");
myChart.render("chartdiv");
   </script>
		<div id="tablaAsistencias">
		<table id="rounded-corner" style="width: 400px">
			<thead>
				<tr>
					<th class="rounded-company"><fmt:message key="day"/></th>
					<th class="rounded-q1"><fmt:message key="date"/></th>
					<th class="rounded-q4"><fmt:message key="attendance"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${asistenciaDiaria}" var="thisAsistenciaDiaria">
					<tr>
						<td><c:out value="${thisAsistenciaDiaria.diasDesdeEstreno}" /></td>
						<td><c:out value="${thisAsistenciaDiaria.diaDeLaSemana}" />
						<fmt:formatDate value="${thisAsistenciaDiaria.fecha}"
							pattern="dd-MM-yyyy" /></td>
						<td><c:out value="${thisAsistenciaDiaria.asistenciaDiaria}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		</div>
	</c:otherwise>
</c:choose></div>
</body>
</html>