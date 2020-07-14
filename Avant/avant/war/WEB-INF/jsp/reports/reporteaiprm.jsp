<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.servlet.ModelAndView"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><fmt:message key="pageTitle" /></title>
		<script language="JavaScript" src="<c:url value="/FusionCharts/FusionCharts.js"/>"></script>
		
		<script type="text/javascript" src="<c:url value="/js/tabber.js" />" >No pezca javascript</script>
		<link rel="stylesheet" href="<c:url value="/styles/tabber.css" />" TYPE="text/css" MEDIA="screen">
		<link rel="stylesheet" href="<c:url value="/styles/tabber-print.css" />" TYPE="text/css" MEDIA="print">
		<script type="text/javascript">

			/* Optional: Temporarily hide the "tabber" class so it does not "flash"
			   on the page as plain HTML. After tabber runs, the class is changed
			   to "tabberlive" and it will appear. */
			
			document.write('<style type="text/css">.tabber{display:none;}<\/style>');
		
		</script>
		
		<link rel="stylesheet" type="text/css" media="screen"
			href="<spring:theme code="form"/>" />
		<link rel="stylesheet" type="text/css" media="screen"
			href="<spring:theme code="tablas_admin"/>" />
	</head>
	<body>
		<jsp:include page="/menu2.htm"></jsp:include>
		<div id="contenedor"><span id="titulo"><fmt:message key="rmIncomesAndAttendances" /></span> <br>
			<%
				int i = 0;
				
				Object numComplejos = request.getAttribute("nComplejos");
				int nComplejos = 1;
				if(numComplejos!=null){
					nComplejos = (Integer)numComplejos;
				}
				if(nComplejos<=0)
					nComplejos=1;
				
				int altura = 100+45*nComplejos;
			%>
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
				<div title="<fmt:message key="income" />" class="tabbertab" align="center">
					<div id="div<%= i%>" align="center"><fmt:message key="error.chart"/></div>
					<script type="text/javascript">
						var myChart = new FusionCharts("<c:url value="/FusionCharts/FCF_StackedBar2D.swf"/>", "myChartId", "600", "<%=altura%>");
						myChart.setDataURL("<c:url value="${map.xmlGrafGI}"/>");
						myChart.render("div<%= i++%>");
					</script>
					<table id="rounded-corner">
						<thead>
							<tr>
								<th scope="col" class="rounded-company"><fmt:message key="complex" /></th>
								<th scope="col" class="rounded-q1"><fmt:message key="total" /></th>
								<th scope="col" class="rounded-q1"><fmt:message key="rm" /></th>
								<th scope="col" class="rounded-q1"><fmt:message key="total" /> <fmt:message key="without" /> <fmt:message key="rm" /></th>
								<th scope="col" class="rounded-q4"><fmt:message key="rmPercentage"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${map.tablaIng}" var="f">
								<tr>
									<c:forEach items="${f}" var="c">
										<td>
											<c:out value="${c}"></c:out>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
					        <tr>
					        	<td colspan="4" class="rounded-foot-left">&nbsp;</td>
					        	<td class="rounded-foot-right">&nbsp;</td>
					        </tr>
					    </tfoot>
					</table>
				</div>
				<div title="<fmt:message key="attendance" />" class="tabbertab" align="center">
					<div id="div<%= i%>" align="center"><fmt:message key="error.chart"/></div>
					<script type="text/javascript">
						var myChart = new FusionCharts("<c:url value="/FusionCharts/FCF_StackedBar2D.swf"/>", "myChartId", "600", "<%=altura%>");
						myChart.setDataURL("<c:url value="${map.xmlGrafGA}"/>");
						myChart.render("div<%= i++%>");
					</script>
					<table id="rounded-corner">
						<thead>
							<tr>
								<th scope="col" class="rounded-company"><fmt:message key="complex" /></th>
								<th scope="col" class="rounded-q1"><fmt:message key="total" /></th>
								<th scope="col" class="rounded-q1"><fmt:message key="rm" /></th>
								<th scope="col" class="rounded-q1"><fmt:message key="total" /> <fmt:message key="without" /> <fmt:message key="rm" /></th>
								<th scope="col" class="rounded-q4"><fmt:message key="rmPercentage"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${map.tablaAsi}" var="f">
								<tr>
									<c:forEach items="${f}" var="c">
										<td>
											<c:out value="${c}"></c:out>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
					        <tr>
					        	<td colspan="4" class="rounded-foot-left">&nbsp;</td>
					        	<td class="rounded-foot-right">&nbsp;</td>
					        </tr>
					    </tfoot>
					</table>
				</div>
				<div title="<fmt:message key="candyStoreIncomes" />" class="tabbertab" align="center">
					<div id="div<%= i%>" align="center"><fmt:message key="error.chart"/></div>
					<script type="text/javascript">
						var myChart = new FusionCharts("<c:url value="/FusionCharts/FCF_StackedBar2D.swf"/>", "myChartId", "600", "<%=altura%>");
						myChart.setDataURL("<c:url value="${map.xmlGrafGC}"/>");
						myChart.render("div<%= i++%>");
					</script>
					<table id="rounded-corner">
						<thead>
							<tr>
								<th scope="col" class="rounded-company"><fmt:message key="complex" /></th>
								<th scope="col" class="rounded-q1"><fmt:message key="total" /></th>
								<th scope="col" class="rounded-q1"><fmt:message key="rm" /></th>
								<th scope="col" class="rounded-q1"><fmt:message key="total" /> <fmt:message key="without" /> <fmt:message key="rm" /></th>
								<th scope="col" class="rounded-q4"><fmt:message key="rmPercentage"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${map.tablaConf}" var="f">
								<tr>
									<c:forEach items="${f}" var="c">
										<td>
											<c:out value="${c}"></c:out>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
					        <tr>
					        	<td colspan="4" class="rounded-foot-left">&nbsp;</td>
					        	<td class="rounded-foot-right">&nbsp;</td>
					        </tr>
					    </tfoot>
					</table>
				</div>
			</div>
			<div align="center">
				<a href="generadorPDF.htm?tipo=4"  style="text-decoration:none;" target="_blank" ><img border="0" name="pdf" title="pdf" alt="generarPDF" src="<c:url value="/images/pdf.gif"/>" /></a>
				<a href="generadorExcel.htm?tipo=4"  style="text-decoration:none;" target="_blank" ><img border="0" name="excel" title="excel" alt="generarExcel" src="<c:url value="/images/excel_icon.gif"/>" /></a>
			</div>
		</div>
	</body>
</html>