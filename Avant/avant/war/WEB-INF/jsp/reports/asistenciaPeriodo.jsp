<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="pageTitle" /></title>
<script type="text/javascript" src="<c:url value="/js/background.js" />" > </script>
		
		<script type="text/javascript" src="<c:url value="/js/tabber.js" />" ></script>
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
<script language="JavaScript" src="<c:url value="/FusionCharts/FusionCharts.js"/>"></script>
<style type="text/css">
</style>
</head>
<body>

<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor"><span id="titulo"><fmt:message key="report.attendances"/></span> <br>

<br>

<table id="rounded-corner"   style="margin-left: 65px;"  cellpadding="2">
<thead>
	<tr>
     	<th align="center" scope="col" class="rounded-company"><fmt:message key="complex"/></th>
		<th align="center"><c:out value="${fecha_inicio_primer_periodo}"/> al <br><c:out value="${fecha_fin_primer_periodo}"/></th>
		<th align="center"><c:out value="${fecha_inicio_segundo_periodo}"/> al <br><c:out value="${fecha_fin_segundo_periodo}"/></th>
		<th align="center" scope="col" class="rounded-q4"><fmt:message key="report.percentDifference"/></th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${arreglo_maps_por_complejo}" var="mapa_complejo">
		<tr>
			<td><c:out value="${mapa_complejo.nombre_complejo}"/></td>
			<td align="center"><fmt:formatNumber value="${mapa_complejo.total_primer_periodo}" pattern="###,###"/></td>
			<td align="center"><fmt:formatNumber value="${mapa_complejo.total_segundo_periodo}" pattern="###,###"/></td>
			<td align="center"><fmt:formatNumber value="${mapa_complejo.porcentaje_diferencia }" type="percent" /></td>
		</tr>
	</c:forEach>
</tbody>
	<tfoot>
        <tr>
        	<td colspan="3" class="rounded-foot-left"><em><fmt:message key="report.attendances"/></em></td>
        	<td class="rounded-foot-right">&nbsp;</td>
        </tr>
    </tfoot>	
</table>
<c:if test="${existe_grafico==true}">
<div id="chartdiv" align="center"><fmt:message key="error.chart"/></div>
<script type="text/javascript">
var myChart = new FusionCharts("<c:url value="/FusionCharts/FCF_MSColumn3D.swf"/>", "myChartId", "900", "300", "0", "0");
myChart.setDataXML('<c:out value="${grafico}"/>');
myChart.render("chartdiv");
   </script>
</c:if>

<br>
<br>

<!-- parte 3-->
<div class="tabber">
<c:forEach items="${arreglo_maps_por_complejo}" var="mapa_complejo" varStatus="conta">
<div title="${mapa_complejo.nombre_complejo}" class="tabbertab" align="center">
<div style="overflow: auto;">
<br>
<table id="rounded-corner" style="margin-left: 65px;" cellpadding="4">
<thead>
	<tr>
     	<th scope="col" class="rounded-company"><fmt:message key="period"/>/<fmt:message key="time"/></th>
     	<c:forEach items="${mapa_complejo.lista_dias_segundo_periodo}" var="dia" varStatus="conta2">
		<th scope="col" class="rounded-q1"><c:out value="${dia}"/>(<c:out value="${mapa_complejo.lista_dias_primer_periodo[conta2.index]}"/>)</th>
		</c:forEach>
	</tr>
	</thead>
	<tbody>
		<tr>
			<td><c:out value="${fecha_inicio_primer_periodo}"/> al <c:out value="${fecha_fin_primer_periodo}"/></td>
				<c:forEach items="${mapa_complejo.por_dias_primer_periodo}"  var="asistencia_dia">
					<td><fmt:formatNumber value="${asistencia_dia}" pattern="###,###"/></td>
				</c:forEach>
		</tr>
		<tr>
			<td><c:out value="${fecha_inicio_segundo_periodo}"/> al <c:out value="${fecha_fin_segundo_periodo}"/></td>
				<c:forEach items="${mapa_complejo.por_dias_segundo_periodo}"  var="asistencia_dia">
					<td><fmt:formatNumber value="${asistencia_dia}" pattern="###,###"/></td>
				</c:forEach>
		</tr>
		</tbody>
</table>


<div id="chartdiv<c:out value="${conta.count}"/>" align="center"><fmt:message key="error.chart"/></div>
<c:if test="${mapa_complejo.existe_grafico==true}">
<script type="text/javascript">
var myChart = new FusionCharts("<c:url value="/FusionCharts/FCF_MSLine.swf"/>", "myChartId<c:out value="${conta.count}"/>", "<c:out value="${(largo_grafico/2)*100}"/>", "300", "0", "0");
myChart.setDataXML('<c:out value="${mapa_complejo.grafico}"/>');
myChart.render("chartdiv<c:out value="${conta.count}"/>");
</script>
</c:if>

</div>
</div>
</c:forEach>
</div>
<em><fmt:message key="report.aviso1"/></em><br><em><fmt:message key="report.aviso2"/></em>
<br>
<br>   
<fieldset style="width: 80px;margin-left: 10px;">
<a href="generadorPDF.htm?tipo=0"  style="text-decoration:none;" target="_blank" ><img border="0" name="pdf" title="pdf" alt="generarPDF" src="<c:url value="/images/pdf.gif"/>" /></a>
&nbsp;&nbsp;&nbsp;<a href="generadorExcel.htm?tipo=0"  style="text-decoration:none;" target="_blank" ><img border="0" name="pdf" title="pdf" alt="generarPDF" src="<c:url value="/images/excel_icon.gif"/>" /></a>
</fieldset>
<br>
</div>
</body>
</html>