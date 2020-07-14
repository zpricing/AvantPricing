<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">

var parametrosNombre = new Array();
var parametrosPrefijo = new Array();
var parametrosSufijo = new Array();
var parametrosWeb = new Array();
var convertiblesEnFecha = new Array();
var dominios = new Array();
var parametrosKey = new Array();

<c:forEach items="${reportesDisponibles}" var="reporte">
parametrosNombre['<c:out value="${reporte.key }"/>'] = new Array();
parametrosPrefijo['<c:out value="${reporte.key }"/>'] = new Array();
parametrosSufijo['<c:out value="${reporte.key }"/>'] = new Array();
parametrosWeb['<c:out value="${reporte.key }"/>'] = new Array();
convertiblesEnFecha['<c:out value="${reporte.key }"/>'] = new Array();
dominios['<c:out value="${reporte.key }"/>'] = new Array();
parametrosKey['<c:out value="${reporte.key }"/>'] = new Array();
<% int i = 0; %>
<c:forEach items="${reporte.value.parametros}" var="parametro">
	parametrosNombre['<c:out value="${reporte.key}"/>'][<%=i%>] = '<c:out value="${parametro.value.parametroNombre }"/>';
	parametrosPrefijo['<c:out value="${reporte.key}"/>']['<c:out value="${parametro.value.parametroNombre}"/>'] = '<c:out value="${parametro.value.parametroPrefijo }"/>';
	parametrosSufijo['<c:out value="${reporte.key}"/>']['<c:out value="${parametro.value.parametroNombre}"/>'] = '<c:out value="${parametro.value.parametroSufijo }"/>';
	parametrosWeb['<c:out value="${reporte.key}"/>']['<c:out value="${parametro.value.parametroNombre}"/>'] = '<c:out value="${parametro.value.parametroWeb }"/>';
	convertiblesEnFecha['<c:out value="${reporte.key}"/>']['<c:out value="${parametro.value.parametroNombre}"/>'] = '<c:out value="${parametro.value.convertibleEnFecha }"/>';
	dominios['<c:out value="${reporte.key}"/>']['<c:out value="${parametro.value.parametroNombre}"/>'] = '<c:out value="${parametro.value.dominio }"/>';
	parametrosKey['<c:out value="${reporte.key }"/>']['<c:out value="${parametro.value.parametroNombre}"/>'] = '<fmt:message key="${parametro.value.parametroNombre}" />';
	<% i++; %>
</c:forEach>
</c:forEach>

function mostrarParametros(reporte) {
	var loading = document.getElementById("loadingDiv");
	loading.style.display="inline";
	var output = document.getElementById("parametrosReportes");
	output.innerHTML = '';
	var countParameters = 0;
	var form = '<table border="0">';
	for (parametro in parametrosNombre[reporte]) {
		form += 
			'<tr><td>'
			+ parametrosKey[reporte][parametrosNombre[reporte][parametro]]
			+ '</td><td>';
			

		if (dominios[reporte][parametrosNombre[reporte][parametro]] != "") {
			var xmlhttp;
			if (window.XMLHttpRequest) xmlhttp=new XMLHttpRequest();
			else xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			xmlhttp.open("GET","auxiliarNielsen.htm?name=" + parametrosWeb[reporte][parametrosNombre[reporte][parametro]] +"&dominio=" + dominios[reporte][parametrosNombre[reporte][parametro]],false);
			xmlhttp.send(null);

			while (xmlhttp.readyState != 4) {;}
			form += xmlhttp.responseText;
			
		} else {
			form += '<input type="text" name="'
			+ parametrosWeb[reporte][parametrosNombre[reporte][parametro]]
			+ '" id="'
			+ reporte
			+ ';'
			+ parametrosWeb[reporte][parametrosNombre[reporte][parametro]]
			+ '" />';
		}
		if (convertiblesEnFecha[reporte][parametrosNombre[reporte][parametro]] == "true") {
			form += '<img width="20" height="20" onclick="javascript:popUpCalendar(this,document.getElementById(\''
				+ reporte
				+ ';'
				+ parametrosWeb[reporte][parametrosNombre[reporte][parametro]]
				+ '\'), \'dd-mm-yyyy\');" src="/ZPCinemasDev/images/calendar/cal.jpg" alt="calendar" title="calendar" name="calendar1" id="calendar1">';
		}
		form += '</td></tr>';
			
		countParameters++;
	}
	form += "</table>";

	loading.style.display="none";
	output.innerHTML += form;
	if (countParameters == 0) {
		var takesZeroParametersMsg = '<fmt:message key="report.takesZeroParameters"/>';
		output.innerHTML += takesZeroParametersMsg;
	}
	String.prototype.endsWith = function(str)
	{return (this.match(str+"$")==str)};
	if (location.href.endsWith("#parametros")) location.href = location.href.substr(0, location.href.length-11) + "#parametros";
	else location.href += "#parametros";
}

function abrir()
{
	var form = document.getElementById("rsForm");
	var radio = form.elements["tipo_reporte"];
	var reporteElegido;
	for (var i = 0; i < radio.length;i++) {
		if (radio[i].checked) reporteElegido = radio[i].value; 
	}
	var huboErrores = false;
	var dateFechaInicio, dateFechaFin;

	// Borrar el error, si es que se había creado ya:
	if (document.getElementById("error;parametrosReportes")!= null) {
		var toRemove = document.getElementById("error;parametrosReportes");
		toRemove.parentNode.removeChild(toRemove);
	}
	
	for (parametro in parametrosNombre[reporteElegido]) {
		if (convertiblesEnFecha[reporteElegido][parametrosNombre[reporteElegido][parametro]] == "true") {
			// El parámetro es una fecha. ¿Es inicio o es fin?
			var esFechaInicio = false;
			var esFechaFin = false;
			if (parametrosNombre[reporteElegido][parametro].indexOf("FromTiempo") != -1 
					|| parametrosNombre[reporteElegido][parametro].indexOf("FromFecha") != -1
					|| parametrosNombre[reporteElegido][parametro].indexOf("fechaInicio") != -1) esFechaInicio = true;
			if (parametrosNombre[reporteElegido][parametro].indexOf("ToTiempo") != -1
					|| parametrosNombre[reporteElegido][parametro].indexOf("ToFecha") != -1
					|| parametrosNombre[reporteElegido][parametro].indexOf("fechaFin") != -1)    esFechaFin    = true;

			var dayOfWeek;
			if (esFechaInicio || esFechaFin) {
				var nombreWebParametro = parametrosWeb[reporteElegido][parametrosNombre[reporteElegido][parametro]];
				// Borramos posibles errores que hayan quedado.
				if (document.getElementById("error;" + reporteElegido + ";" + nombreWebParametro)!= null) {
					var toRemove = document.getElementById("error;" + reporteElegido + ";" + nombreWebParametro);
					toRemove.parentNode.removeChild(toRemove);
				}
				// Seguimos: 
				var fecha = document.getElementById(reporteElegido + ";" + nombreWebParametro).value;
				var fechaSeparada = fecha.split("-");
				var year = fechaSeparada[2];
				var month = fechaSeparada[1];
				var day = fechaSeparada[0];
				var d = new Date(year, month-1, day);
				if (esFechaInicio) dateFechaInicio = d;
				if (esFechaFin) dateFechaFin = d;
				dayOfWeek = d.getDay();
			}

			// La fecha inicial debe ser jueves.
			if (esFechaInicio && dayOfWeek != 4) {
				var error = '<fmt:message key="error.notThursday"/>';
				mostrarError(document.getElementById(reporteElegido + ";" + nombreWebParametro), error);
				huboErrores = true;
			}
			// La fecha final debe ser miércoles.
			if (esFechaFin && dayOfWeek != 3) {
				var error = '<fmt:message key="error.notWednesday"/>';
				mostrarError(document.getElementById(reporteElegido + ";" + nombreWebParametro), error);
				huboErrores = true;
			}
		}
	}

	// Finalmente, fechaFinal > fechaInicial.
	if (dateFechaInicio != null && dateFechaFin != null) {

		if (dateFechaFin < dateFechaInicio) {
			var error = '<fmt:message key="error.wrongDatesOrder"/>';
			mostrarError(document.getElementById("parametrosReportes"), error); 
			huboErrores = true;
		}
	}
	
	if (huboErrores) return false; else	return true;
	document.getElementById('popup').style.display='block';
}

function mostrarError(objeto, mensaje) {
	// El mensaje se muestra al lado de objeto
	var msg = document.createElement("span");
	msg.innerHTML = mensaje;
	msg.style.color = 'red';
	msg.id = "error;" + objeto.id;
	objeto.parentNode.appendChild(msg);

}
</script>

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
   
   h3 
   {
   	  color: gray;
   	  border-bottom: 1px silver solid;
   		
   }
   .atenuarTooltip {
   	  color:silver;
   	  opacity: 0.7;
   	  
   }
   .atenuarTooltip:hover {
   	  opacity: 1.0;
   }

</style>
</head>
<body>

<jsp:include page="/menu2.htm"></jsp:include>
<div id="contenedor">
<span id="titulo"><fmt:message key="reportsTitle" /></span> 
<br>
	<form method="post" class="formStyle" id="rsForm" onsubmit="return abrir();">

<div id="form-area">

	<c:if test="${usingReportingServices}">
	<input type="hidden" name="useRS" value="true"></input>
		<fieldset style="width: 650px;"><legend>ZP<fmt:message
			key="reports" /></legend>
		<table>
		<tr><td>
		<table id="tablaForm">
			<c:forEach items="${reportesDisponiblesCategorizados}" var="categoria">
	
				<tr>
					<td colspan="3">
						<h3><fmt:message key="${categoria.key}"/></h3>
					</td>
				</tr>
				<c:forEach items="${categoria.value }" var="reporte"> 
				<tr>
					<td colspan="2">
						<input type="radio" name="tipo_reporte" value="${reporte.key}" id="radio${reporte.key}" onclick="mostrarParametros('${reporte.key}');" />
						<label for="radio<c:out value="${reporte.key}"/>" onclick="mostrarParametros('${reporte.key}');" >
							<fmt:message key="${reporte.key}" />
							<fmt:message key="${reporte.key}Expl" var="mensaje"/>
							<c:if test="${!fn:startsWith(mensaje,'???')}">
								<span class="atenuarTooltip" title="${mensaje}" }>[ ? ]</span>
							</c:if>
						</label>
					</td>
				</tr>
				</c:forEach>
			</c:forEach>
		</table>
		</td>
		<td valign="top">

		</td>
		</tr>
		</table>
		</fieldset>



	</c:if>


	<fieldset style="width: 650px;" id="parameters">
	<legend><fmt:message key="parameters"/></legend>
	<a name="parametros" ></a>
	<div id="loadingDiv" style="width:600px; display:none;" align="center">
		<p><img src="<c:url value="/images/loading.gif"/>"/></p>
	</div>
	<div id="parametrosReportes"></div>

	<input id="boton"  type="submit" class="botonSubmit"
					value="<fmt:message key="generate" /> <fmt:message key="report" />">
			
	</fieldset>

</div>
</form>
</div>


<jsp:include page="/footer.htm"></jsp:include>
</body>
<div id="popup">
&nbsp;<fmt:message key="processing" /><br>
<img align="middle" alt="s" src="<c:url value="/images/loading_page.gif"/>">
</div>
</html>