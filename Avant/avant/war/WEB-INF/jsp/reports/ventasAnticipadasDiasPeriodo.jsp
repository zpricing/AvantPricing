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

<div class="tabber">
	<c:forEach items="${datos}" var="datos">
		<div title="${datos.complejo.nombre}" class="tabbertab" align="center">
			<div style="overflow: auto;">
				<table id="rounded-corner" style="margin-left: 65px;" cellpadding="2">
					<thead>
						<tr>
					     	<th align="center" scope="col" class="rounded-company"><fmt:message key="date"/></th>
							<th align="center"><fmt:message key="RMAssistance"/></th>
							<th align="center"><fmt:message key="RMSales"/></th>
							<th align="center"><fmt:message key="TotalAssistance"/></th>
							<th align="center"><fmt:message key="TotalSales"/></th>
							<th align="center"><fmt:message key="PercentRMAssistance"/></th>
							<th align="center" scope="col" class="rounded-q4"><fmt:message key="PercentRMSales"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${datos.dias}" var="dia">
							<tr>
								<td align="center"><fmt:formatDate value="${dia.fecha}" pattern="EE, dd-MM-yy"/> </td>
								<td align="center"><fmt:formatNumber value="${dia.rmassistance}" pattern="###,###" /> </td>
								<td align="center"><fmt:formatNumber value="${dia.rmsales}" pattern="$###,###" /> </td>
								<td align="center"><fmt:formatNumber value="${dia.assistance}" pattern="###,###" /></td>
								<td align="center"><fmt:formatNumber value="${dia.sales}" pattern="$###,###" /> </td>
								<td align="center"><fmt:formatNumber value="${dia.percentRMassistance}" type="percent" /> </td>
								<td align="center"><fmt:formatNumber value="${dia.percentRMsales}" type="percent" /> </td>
							</tr>
						</c:forEach>
						<tr>
							<td align="center"><fmt:message key="total" /></td>
							<td align="center"><fmt:formatNumber value="${datos.totalRMAssistance}" pattern="###,###" /> </td>
							<td align="center"><fmt:formatNumber value="${datos.totalRMSales}" pattern="$###,###" /> </td>
							<td align="center"><fmt:formatNumber value="${datos.totalAssistance}" pattern="###,###" /></td>
							<td align="center"><fmt:formatNumber value="${datos.totalSales}" pattern="$###,###" /> </td>
							<td align="center"><fmt:formatNumber value="${datos.totalPercentRMAssistance}" type="percent" /> </td>
							<td align="center"><fmt:formatNumber value="${datos.totalPercentRMSales}" type="percent" /> </td>
						</tr>
					</tbody>
					<tfoot>
				        <tr>
				        	<td colspan="6" class="rounded-foot-left"><em><fmt:message key="report.attendances"/></em></td>
				        	<td class="rounded-foot-right">&nbsp;</td>
				        </tr>
				    </tfoot>	
				</table>
				<br />
				<jsp:include page="../FusionChartsRenderer.jsp" flush="true">
					<jsp:param name="chartSWF" value="FusionCharts/FCF_StackedColumn3D.swf" />
					<jsp:param name="strURL" value="" />
					<jsp:param name="strXML" value="${datos.graficoAssistance.XML}" />
					<jsp:param name="chartId" value="Assistance_${datos.complejo.nombre}" />
					<jsp:param name="chartWidth" value="${datos.graficoAssistance.width}" />
					<jsp:param name="chartHeight" value="${datos.graficoAssistance.height}" />
					<jsp:param name="debugMode" value="true" />
					<jsp:param name="registerWithJS" value="true" />
				</jsp:include>
				<br />
				<jsp:include page="../FusionChartsRenderer.jsp" flush="true">
					<jsp:param name="chartSWF" value="FusionCharts/FCF_StackedColumn3D.swf" />
					<jsp:param name="strURL" value="" />
					<jsp:param name="strXML" value="${datos.graficoSales.XML}" />
					<jsp:param name="chartId" value="Sales_${datos.complejo.nombre}" />
					<jsp:param name="chartWidth" value="${datos.graficoSales.width}" />
					<jsp:param name="chartHeight" value="${datos.graficoSales.height}" />
					<jsp:param name="debugMode" value="true" />
					<jsp:param name="registerWithJS" value="true" />
				</jsp:include>
			</div>
		</div>
	</c:forEach>
</div>

<br>   
<fieldset style="width: 80px;margin-left: 10px;">
<a href="generadorPDF.htm?tipo=0"  style="text-decoration:none;" target="_blank" ><img border="0" name="pdf" title="pdf" alt="generarPDF" src="<c:url value="/images/pdf.gif"/>" /></a>
&nbsp;&nbsp;&nbsp;<a href="generadorExcel.htm?tipo=0"  style="text-decoration:none;" target="_blank" ><img border="0" name="pdf" title="pdf" alt="generarPDF" src="<c:url value="/images/excel_icon.gif"/>" /></a>
</fieldset>
<br>
</div>
</body>
</html>