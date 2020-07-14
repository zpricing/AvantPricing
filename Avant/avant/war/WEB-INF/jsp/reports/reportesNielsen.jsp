<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="JavaScript">
function selTodos(){
	cas=document.reportesForm.precios.length;
	for(i=0;i<cas;i=i+1)
		document.reportesForm.precios[i].click();
}

</script> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="pageTitle" /></title>


<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="form"/>" />
	<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />


<script type="text/javascript" src="<c:url value="/js/showhide.js" />"></script>
<script language="javascript">
function abrir()
{
document.getElementById('popup').style.display='block';
}


</script>
<script type="text/javascript" src="<c:url value="/js/popcalendar.js" />"></script>

<style>

.error {
	color: red;
}
#popup
   {
      display:none;
      position: absolute;
      border: 1px solid #666666;
	  background-color:white;
	  color:graytext;
      float:left;
      left: 400px;
      top: 300px;
      padding: 10px;
      text-align: center;
      position:fixed;  
      width:200px;
      height: 60px;
	  left:50%;
	  margin-left:-100px;
	  top:50%;
	  margin-top:-100px;
	  padding: 10px;
   }

</style>
</head>
<body>
<jsp:include page="/menu2.htm"></jsp:include>
<div id="contenedor">
<span id="titulo"><fmt:message key="reportsTitle" /></span> 
<br>
<div id="form-area">
<form:form method="post" commandName="reportes" cssClass="formStyle" name="reportesForm" onsubmit="abrir(); return true;">
	<fieldset style="width: 550px;">
	<legend><fmt:message key="data" /></legend>
	<table  id="tablaForm">
		<tr>
			<td><label for="fecha_inicio" id="fecha_inicio_label">
			<fmt:message key="since" /> </label>  </td>
			<td><form:input path="fecha_inicio" cssClass="fecha" /> 
			<img id="calendar1" name="calendar1" title="calendar" alt="calendar" src="<c:url value="/images/calendar/cal.jpg"/>" width="20" height="20" onclick="javascript:popUpCalendar(this,document.getElementById('fecha_inicio'), 'dd-mm-yyyy');" /></td>
			<td><form:errors path="fecha_inicio" cssClass="error" /></td>
		</tr>
		<tr>
			<td><label for="fecha_fin" id="fecha_fin_label"><fmt:message key="until" /> </label></td>
			<td><form:input path="fecha_fin"  />
			<img name="calendar2" title="calendar" alt="calendar" src="<c:url value="/images/calendar/cal.jpg"/>" width="20" height="20" onclick="javascript:popUpCalendar(this,document.getElementById('fecha_fin'), 'dd-mm-yyyy');" />
			 </td>
			<td><form:errors path="fecha_fin" cssClass="error" /><br></td>
		</tr>
		<tr>
			<td><label for="complejo" ><fmt:message key="complexs" /> </label></td>
			<td><form:select id="sel" cssStyle="width:145px;" path="complejo">
				<c:forEach items="${complejos}" var="c">
					<form:option value="${c.id}">${c.nombre}</form:option>
				</c:forEach>
			</form:select>
			</td>
			<td><form:errors path="complejo" cssClass="error" /></td>
		</tr>
		<tr>
			
		</tr>
		
	</table>
	<span onclick="javascript:showhide('preciosField');" > <fmt:message key="unhide" /> <fmt:message key="prices" /></span><form:errors path="precios" cssClass="error" />
	<fieldset id="preciosField" style="display: none;">
	<table  id="tablaForm">
						<%
						int i=0;
						int ancho=6;
						%>
						<spring:bind path="precios">
						<c:forEach items="${precios}" var="p">
							<%if(i%ancho == 0){%>
							<tr>
							<%}%>
							<td align="right"><c:out value="${p.valor}"></c:out>
							<input type="checkbox" name="precios" value="${p.valor}" checked="checked">
							</td>
							<%if(i%ancho == (ancho-1)){%>
							</tr>
							<%}%>
							<%i++;%>
						</c:forEach>
						</spring:bind>
						<tr>
							<td colspan="<%=ancho%>"><input type="checkbox" name="seleccionarTodos" value="false" checked="checked" onchange="selTodos();"> <fmt:message key="select" /> <fmt:message key="all" /> </td>
						</tr>
					</table>
					
	</fieldset>
	</fieldset>
	


	<c:if test="${usingClassicReports}">
	<fieldset style="width: 450px;">
	<legend><fmt:message key="reports" /></legend>
	<table id="tablaForm">
		<tr>
			<td colspan="2"> <form:radiobutton  path="tipo_reporte" value="asist_por_periodo" /> <fmt:message key="attendances" /></td>
		</tr>
		<tr>
			<td colspan="2"> <form:radiobutton path="tipo_reporte" value="ing_por_periodo" /> <fmt:message key="incomes" /></td>
		</tr>
		<tr>
			<td colspan="2"> <form:radiobutton path="tipo_reporte" value="ticket_promedio"   /> <fmt:message key="averageTicket" /></td>
		</tr>
		<tr>
			<td colspan="2"> <form:radiobutton path="tipo_reporte" value="confiteria"   /> <fmt:message key="candyStoreIncomes" /></td>
		</tr>
		<tr>
			<td colspan="2"> <form:radiobutton path="tipo_reporte" value="asistencia_ingreso_rm"   /> <fmt:message key="rmIncomesAndAttendances" /></td>
		</tr>
		<tr>
			<td colspan="2"> <form:radiobutton path="tipo_reporte" value="ventas_anticipadas_dias"   /> <fmt:message key="advanceSales" /></td>
		</tr>
		<tr>
			<td><br>
			<input id="boton" type="submit" class="submit" value="<fmt:message key="generate" /> <fmt:message key="report" />" ></td>
		</tr>
		
	</table>
	</fieldset>
	</c:if>

	<c:if test="${usingReportingServices}">
		<fieldset style="width: 450px;"><legend><fmt:message
			key="reports" /></legend>
		<table id="tablaForm">

			<c:forEach items="${reportesDisponibles}" var="reporte">
				<tr>
					<td colspan="2"><form:radiobutton path="tipo_reporte"
						value="${reporte.value}" id="radio${reporte.value}" /><label for="radio<c:out value="${reporte.value}"/>"><fmt:message key="${reporte.key}" /></label> </td>
				</tr>

			</c:forEach>

			<tr>
				<td><br />
				<input id="boton" type="submit" class="submit"
					value="<fmt:message key="generate" /> <fmt:message key="report" />"></td>
			</tr>

		</table>
		</fieldset>

	</c:if>


</form:form>

</div>
</div>
<jsp:include page="/footer.htm"></jsp:include>
</body>
<div id="popup">
&nbsp;<fmt:message key="processing" /><br>
<img align="middle" alt="s" src="<c:url value="/images/loading_page.gif"/>">
</div>
</html>