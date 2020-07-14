<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><fmt:message key="pageTitle" /></title>
  		<script type="text/javascript" src="<c:url value="/FusionCharts/FusionCharts.js"/>"></script>
  		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="datepicker"/>" />

		<script type="text/javascript" src="<c:url value="/js/popcalendar.js" />"></script>
		<script type="text/javascript" src="<c:url value="/js/background.js" />" > </script>
		
		<script type="text/javascript" src="<c:url value="/js/tabber.js" />" >No pezca javascript</script>
		<link rel="stylesheet" href="<c:url value="/styles/tabber.css" />" TYPE="text/css" MEDIA="screen">
		<link rel="stylesheet" href="<c:url value="/styles/tabber-print.css" />" TYPE="text/css" MEDIA="print">
		<script type="text/javascript">

			/* Optional: Temporarily hide the "tabber" class so it does not "flash"
			   on the page as plain HTML. After tabber runs, the class is changed
			   to "tabberlive" and it will appear. */
			
			document.write('<style type="text/css">.tabber{display:none;}<\/style>');
			init();
		
		</script>
		
		<link rel="stylesheet" type="text/css" media="screen"
			href="<spring:theme code="form"/>" />
		<link rel="stylesheet" type="text/css" media="screen"
			href="<spring:theme code="tablas_admin"/>" />
		<style>

			.div_datos { color: red;
			display: none;}
			</style>
	</head>
	<body>
		<jsp:include page="/menu2.htm"></jsp:include>
		<div id="contenedor"><span id="titulo"><fmt:message key="averageTicket"/> <fmt:message key="and"/> <fmt:message key="attendance"/></span> <br>
			<table id="rounded-corner">
				<tr>
					<th><fmt:message key="since" /></th>
					<td><c:out value="${map.desde}"></c:out></td>
				</tr>
				<tr>
					<th><fmt:message key="until" /></th>
					<td><c:out value="${map.hasta}"></c:out></td>
				</tr>
			</table>
			<div class="tabber">
				<%int i=0; %>
				<c:forEach items="${map.graficos}" var="g">
					<div title="${g.complejo}" class="tabbertab" align="center">
						<table>
							<tr>
								<th><fmt:message key="averageTicket" /></th>
								<td><c:out value="${g.ticketProm}"></c:out></td>
							</tr>
						</table>
						<c:if test="${g.existePrecioAsistencia}">
							<br/>
							<div id="div<%= i%>" align="center"><fmt:message key="error.chart"/></div>
							<script type="text/javascript">
								var myChart = new FusionCharts("<c:url value="/FusionCharts/FCF_Pie3D.swf"/>", "myChartId", "800", "600");
								myChart.setDataURL("<c:url value="${g.xmlGraf}"/>");
								myChart.render("div<%= i++%>");
							</script>
							<br/>
							<table id="rounded-corner">
								<tr>
									<th><fmt:message key="price" /></th>
									<c:forEach items="${g.precioAsistencia}" var="pa">
										<td><c:out value="${pa.precio}"></c:out></td>
									</c:forEach>
								</tr>
								<tr>
									<th><fmt:message key="attendance" /></th>
									<c:forEach items="${g.precioAsistencia}" var="pa">
										<td><c:out value="${pa.asistencia}"></c:out></td>
									</c:forEach>
								</tr>
							</table>
						</c:if>
					</div>
				</c:forEach>
			</div>
			<div align="center">
				<a href="generadorPDF.htm?tipo=2"  style="text-decoration:none;" target="_blank" ><img border="0" name="pdf" title="pdf" alt="generarPDF" src="<c:url value="/images/pdf.gif"/>" /></a>
				<a href="generadorExcel.htm?tipo=2"  style="text-decoration:none;" target="_blank" ><img border="0" name="excel" title="excel" alt="generarExcel" src="<c:url value="/images/excel_icon.gif"/>" /></a>
			</div>
		</div>
	</body>
</html>
