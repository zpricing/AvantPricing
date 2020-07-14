<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="../tld/math-function.tld" prefix="fun" %>
<%@ taglib uri="../tld/date-function.tld" prefix="fecha" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>

<html>
<head>
<title><fmt:message key="predictions" /></title>
<link rel="shortcut icon" href="<c:url value="/images/shortcut.gif"/>" id="shortcut">
<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="predc"/>" />
<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="form"/>" />
<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="tablas_admin"/>" />
	
<script type="text/javascript" src="<c:url value="/js/showhide.js"/>"></script>
<script language="JavaScript" src="<c:url value="/FusionCharts/FusionCharts.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/popupDiv.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/scrollDiv.js"/>"></script>
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
<script src='<c:url value="/js/prototype.js"/>' type="text/javascript"></script>
<script src='<c:url value="/js/tooltip-v0.2.js"/>' type="text/javascript"></script>

<script type="text/javascript">
var com = -1; 
var pred = -1;
var mostrando = -1;
var mostrandocoef = -1;

function cierra(){
	showhide("boton_grafico");
	var a1 = mostrando + "Div";
	var a2= mostrandocoef + "CoefDiv";
	showhide(a1);
	showhide(a2);
	mostrando = -1;
	mostrandocoef = -1;
}

function muestra(id, coef) {
	try {
		rePos();
		
		if(mostrando==id){
			var a1 = id + "Div";
			var a2= coef + "CoefDiv";
			showhide(a1);
			showhide(a2);
			mostrando = -1;
			mostrandocoef = -1;
			showhide("boton_grafico");
			
		}
		
		else if(mostrando==-1){
			var a1 = id + "Div";
			var a2= coef + "CoefDiv";
			showhide(a1);
			showhide(a2);
			mostrando = id;
			mostrandocoef = coef;
			showhide("boton_grafico");
		}
		else{			
			var g1 = mostrando + "Div";
			var g2 = mostrandocoef + "CoefDiv";
			showhide(g1);
			showhide(g2);
			var a1 = id + "Div";
			var a2= coef + "CoefDiv";
			showhide(a1);
			showhide(a2);
			mostrando = id;
			mostrandocoef = coef;
			
		}
		
	} catch (e) {
		alert("En muestra " + e);
	}
}
function muestraComplejo(c) {
	try {
		if (c != com) {
			if (mostrando != -1) {
				var g1 = mostrando + "Div";
				var g2 = mostrandocoef + "CoefDiv";
				showhide(g1);
				showhide(g2);
				mostrando = -1;
				mostrandocoef = -1;
				showhide("boton_grafico");
			}

			if (pred != -1) {
				var p1 = "Pred" + pred;
				showhide(p1);
				var p2 = "a_muestra_prediccion_"+pred;
				desmarcar(p2);
				pred = -1;
			}

			if (com != -1) {
				var c1 = "Com" + com;
				var c4 = "a_muestra_complejo_"+com;
				desmarcar(c4);
				showhide(c1);
				com = -1;
			}
			
			var c2 = "Com" + c;
			showhide(c2);
			var c3 = "a_muestra_complejo_"+c;
			marcar(c3);			
			com = c;

		} else {
			if (mostrando != -1) {
				var g1 = mostrando + "Div";
				var g2 = mostrandocoef + "CoefDiv";
				showhide(g1);
				showhide(g2);
				mostrando = -1;
				mostrandocoef = -1;
				showhide("boton_grafico");
			}
			if (pred != -1) {
				var p1 = "Pred" + pred;
				showhide(p1);
				var p2 = "a_muestra_prediccion_"+pred;
				desmarcar(p2);
				pred = -1;
			}
			
			var c1 = "Com" + c;
			showhide(c1);
			var c2 = "a_muestra_complejo_"+c;
			desmarcar(c2);
			com = -1;

		}
	} catch (e) {
		alert("En muestraComplejo " + e);
	}
}


function muestraPrediccion(p) {
	try {
		if (pred != p) {
			if (mostrando != -1) {
				var g1 = mostrando + "Div";
				var g2 = mostrandocoef + "CoefDiv";
				showhide(g1);
				showhide(g2);
				mostrando = -1;
				mostrandocoef = -1;
				showhide("boton_grafico");
			}

			if (pred != -1) {
				var p1 = "Pred" + pred;
				showhide(p1);
				var p4 = "a_muestra_prediccion_"+pred;
				desmarcar(p4);
				pred = -1;
			}
			var p2 = "Pred" + p;
			showhide(p2);
			var p3 = "a_muestra_prediccion_"+p;
			marcar(p3);
			pred = p;
		}

		else {

			if (mostrando != -1) {
				var g1 = mostrando + "Div";
				var g2 = mostrandocoef + "CoefDiv";
				showhide(g1);
				showhide(g2);
				mostrando = -1;
				mostrandocoef = -1;
				showhide("boton_grafico");
			}

			var p1 = "Pred" + p;
			showhide(p1);
			var p2 = "a_muestra_prediccion_"+p;
			desmarcar(p2);
			pred = -1;

		}
	} catch (e) {
		alert("En muestraPrediccion " + e);
	}

}


	

	function esconder() {
		var len = document.getElementById("graficos").childNodes.length;
		try{
			<c:forEach items="${predicciones}" var="p">
				<c:forEach items="${p.prediccionPorDia}" var="pd">
					<c:forEach items="${pd.prediccionesPorFuncion}" var="pf">
					
					if(<c:out escapeXml="false" value="'${pf.graficoRegresionClases.id}'"></c:out>!=''){
					var g1= <c:out value="${pf.graficoRegresionClases.id}"></c:out>+"Div";
					var g2=<c:out value="${pf.graficoRegresionCoef.id}"></c:out>+"CoefDiv";
					showhide(g1);
					showhide(g2);
					}
					
					</c:forEach>					
				</c:forEach>
				showhide("<c:out value="Pred${p.id}"></c:out>");
				
			</c:forEach>

			<c:forEach items="${complejos}" var="c">
				showhide("<c:out value="Com${c.id}"></c:out>");
			</c:forEach>

			initialize();
			showhide("boton_grafico");
			init();
			document.getElementById("cuerpo").style.visibility="visible";//IE
	<c:if test="${not terminado}">
		<c:if test="${test}">
			Popup();
			show("<c:url value='/cargaerp.htm'/>");
			<c:if test="${not cargar_solo}">
				abrir();
			</c:if>			
		</c:if>	
	</c:if>
	<c:if test="${terminado}">
	
	document.getElementById("boton").disabled = true;
	
	</c:if>
	
			
		}catch(e){
			alert("Error en esconder "+e);
		document.getElementById("cuerpo").style.visibility="visible";//Firefox
		}
		
	}
	function submitForm()
	{
		
		document.myform.submit();
	}

function marcar(t){
	document.getElementById(t).className = "marcado";
}

function desmarcar(t){
	document.getElementById(t).className = "";
}

</script>

<script language="JavaScript">
IE5=NN4=NN6=false
if(document.all)IE5=true
else if(document.layers)NN4=true
else if(document.getElementById)NN6=true

var cursorY=0;
var cursorX=0;
function initialize() {
	if(IE5||NN6){
		myObj=document.getElementById("graficos").style;
		myBut=document.getElementById("boton_grafico").style;
	}
	else if(NN4){
		myObj=document.graficos;
		myBut=document.boton_grafico;
	}

	
	rePos();
}

function init() {
	  if (window.Event) {
	    document.captureEvents(Event.MOUSEMOVE);
	  }
	  document.onmousemove = getXY;
	}

	function getXY(e) {
	  x = (window.Event) ? e.pageX : event.clientX;
	  y = (window.Event) ? e.pageY : event.clientY;

	  cursorX=x;
	  cursorY=y;
	}

function rePos() {
	// compute center coordinate
	
	// reposition div
	
	if(IE5||NN6){
		myObj.top = cursorY+ "px";
		myBut.top = cursorY+ "px";
	}
	else if(this.NN4){
		myObj.moveTo(80,cursorY);
		myBut.top = cursorY+ "px";
	}
}

function shortcut(){
	try{
	document.getElementById('shortcut').href = "<c:url value='/images/loading.gif'/>";
	}catch(e){
		alert("error "+e);
	}
}

function guardar() {
	document.getElementById('popup').style.display='block';
	document.getElementById("botonImportante").value = "<fmt:message key='sending' />...";
	document.getElementById("botonImportante").disabled = true;
	//document.getElementById("botonImportante2").disabled = true;
	document.getElementById("boton").disabled = true;
	document.myform.action = "predicciones.htm?boton_abrir=si";

	submitForm();
}

function cargar() {
	//document.getElementById('popup').style.display='block';
	//document.getElementById("botonImportante2").value = "<fmt:message key='sending' />...";
	//document.getElementById("botonImportante").disabled = true;
	//document.getElementById("botonImportante2").disabled = true;
	//document.getElementById("boton").disabled = true;
	//document.myform.action = "predicciones.htm?boton_cargar=si";
	
	
	//submitForm();
	Popup();
	show("<c:url value='/cargaerp.htm'/>");
}

</script>

</head>
<body onload="esconder()">
<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor">
<span id="titulo"><fmt:message key="predictions" /></span> 
<br>

<div id="cuerpo">


<div id="menu">
<div id = "complejos">
<ul>
<c:forEach items="${complejos}" var="c">
<li id="a_muestra_complejo_<c:out value="${c.id}"></c:out>"><a href="javascript:muestraComplejo(<c:out value="${c.id}"></c:out>)";>
		<c:out value="${c.nombre}"></c:out></a></li>
</c:forEach>
</ul>
</div>
<c:forEach items="${complejos}" var="c">
<div id="<c:out value="Com${c.id}"></c:out>">
<ul>
	<c:forEach items="${predicciones}" var="p">
	<c:if test="${p.complejo.id == c.id}">
		<li id="a_muestra_prediccion_<c:out value="${p.id}"></c:out>"><a 
			href="javascript:muestraPrediccion(<c:out value="${p.id}"></c:out>)";>
		<c:out value="${p.pelicula.nombre}"></c:out></a></li>
	</c:if>
	</c:forEach>
</ul>
</div>
</c:forEach>
</div>

<div id = "predicciones" style="overflow:auto;">
<c:set var="i" value="${0}"></c:set>

<c:choose>
<c:when test="${terminado}">
<c:set var="enviar_value" value="charge"></c:set>
</c:when>
<c:otherwise>
<c:set var="enviar_value" value="charge_and_optimize"></c:set>
</c:otherwise>
</c:choose>
<form:form method="post" commandName="predicciones" name="myform">
<c:set var="j" value="${0}"></c:set>
<c:set var="k" value="${0}"></c:set>
<c:forEach items="${predicciones}" var="p" varStatus="prediccionStatus">
<div id="<c:out value="Pred${p.id}"></c:out>">
	
	<div class="hed">	
	<div class="boton_eliminar">
	<a title="Eliminar" href="<c:url value="eliminarprediccion.htm?idprediccion=${p.id}"/>">
		<img border="0" width="12px" alt="" src="<c:url value='/images/delete_icon.gif'/>" />
	</a>
	</div>
	<fmt:message key="prediction" /> <fmt:message key="movie" /> <c:out value="${p.pelicula.nombre}"></c:out> <fmt:message key="of" /> <fmt:message key="complex" /> <c:out value="${p.complejo.nombre}"></c:out>
	</div>
	<div class="hed">
	<fmt:message key="date" /> <fmt:message key="and" />  <fmt:message key="hour" />  <fmt:message key="of" />  <fmt:message key="creation" />  <fmt:formatDate value="${p.fecha}" type="both"
				pattern="dd/MM/yy HH:mm" /> <fmt:message key="by"/> <c:out value="${p.usuario.nombreCompleto}"></c:out>
				<p><fmt:message key="movies"/>:
				<span style="font-weight:normal">
				<c:forEach items="${p.prediccionParametros}" var="thisPrediccionParametros" varStatus="thisPrediccionParametrosStatus">
					<c:out value="${thisPrediccionParametros.pelicula.nombre }"/>
					<c:if test="${thisPrediccionParametros.pelicula.id == p.pelicula.id }">
					 (<fmt:message key="last"/> <fmt:message key="prediction"/>)
					</c:if>
					[<span style="color:gray" title="<fmt:message key="movieWeight"/>: ${thisPrediccionParametros.ponderacion}"><fmt:formatNumber pattern="0.000" value="${thisPrediccionParametros.ponderacion}"></fmt:formatNumber></span>]<c:if test="${!thisPrediccionParametrosStatus.last}">, </c:if>
				</c:forEach>
				</span>
				</p>
	</div>	
	
<table class="stats">	
	<c:forEach items="${p.prediccionPorDia}" var="pd" varStatus="prediccionPorDiaStatus">
	<tr>
		<td>
			<div style="width: 80px;">
				<table>
					<tr>
						<td>
							<span class="dia">
								<fmt:formatDate value="${pd.fecha}" type="both" pattern="EEEE dd/MM/yy" />
							</span>
						</td>
					</tr>
					<tr>
						<td>	
							<c:forEach items="${pd.markersFecha}" var="m">
								<span style="color:${m.tipoMarker.color};" id="trigger_${j}">
									<c:out value="${m.tipoMarker.codigo}"></c:out>
								</span>
								<c:set var="j" value="${j+1}"></c:set>	
							</c:forEach>
						</td>
					</tr>		
					<tr>
						<td>
							<fmt:message key="estimate" /> <c:out value="${pd.estimacion}"></c:out>
						</td>
					</tr>
				<%-- 
					<tr>
						<td>
							<c:out value="${pd.varianza}"></c:out>
						</td>
					</tr>
				--%>
				</table>
			</div>
		</td>
		<c:if test="${empty pd.prediccionesPorFuncion}">
		<td>		
			<fmt:message key="day" /> <fmt:message key="without" /> <fmt:message key="functions" />
		</td>
		</c:if>
		<td class="predicciones_por_funcion">
			<table class="predicciones_por_sala">
				<c:forEach items="${pd.salasUtilizadas}" var="s" varStatus="salasStatus">
					<c:set var="iteradorPrediccionPorFuncion" value="0"/>
					<tr>
						<td class="sala">
							<div style="width: 45px;"><c:out value="${s.numero}"/></div>
						</td>
							<c:forEach items="${pd.prediccionesPorFuncion}" var="pf">
								<c:choose>
									<c:when test="${pf.cargando || pf.optimizando || pf.cargada}">
										<c:set var="disable_select" value="true"></c:set>
										<c:if test="${pf.optimizada && !pf.cargada}">
											<c:set var="disable_select" value="false"></c:set>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:set var="disable_select" value="false"></c:set>
									</c:otherwise>
								</c:choose>
		
								<c:if test="${s.id==pf.funcionPredecida.sala.id}">						
								<td 
									<c:choose>
										<c:when test="${pf.cargando}">
											<c:out escapeXml="false" value="class='cargando'"/>
										</c:when>
										<c:when test="${pf.estimacion/pf.funcionPredecida.sala.capacidad>=optimizacion_corte || pf.optimizada || pf.optimizando}">
											<c:out escapeXml="false" value="class='optimizando'"/>
										</c:when>
										<c:otherwise>
											<c:out escapeXml="false" value="class='seleccionando'"/>				
										</c:otherwise>
									</c:choose>
								>
									<div style="width: 180px;">		
										<%="<!--"%>
										<c:out value="${pf.id }"/>
										<%="-->" %>	
										
										<table>
											<tr>
												<td>			
													<fmt:message key="hour" /> <fmt:formatDate value="${pf.funcionPredecida.fecha}" type="both" pattern="HH:mm" />
												</td>
											</tr>
											<tr>
												<td>
													<fmt:message key="estimate" /> <c:out value="${pf.estimacion}"></c:out>
												</td>
											</tr>
											<tr>
												<td>
													<fmt:message key="dispersion" /> <fmt:formatNumber value="${fun:sqrt(pf.varianza)}" maxFractionDigits="2" minFractionDigits="2"></fmt:formatNumber> 
												</td>
											</tr>
											<tr>
												<td>
													<fmt:message key="occupation" /> <fmt:formatNumber value="${100*pf.estimacion/pf.funcionPredecida.sala.capacidad}" maxFractionDigits="2" minFractionDigits="2"></fmt:formatNumber>% 
												</td>
											</tr>
											<tr>
												<td>
													<form:select path="mascaraFuncion[${prediccionStatus.index}][${prediccionPorDiaStatus.index}][${salasStatus.index}][${iteradorPrediccionPorFuncion}]" cssClass="select">
														<form:option value="0"><fmt:message key="optimize" /></form:option>
											            <form:options items="${s.mascaras}" itemValue="id" itemLabel="descripcion"/>
											        </form:select>
												</td>
											</tr>
											<c:if test="${not empty pf.prediccionesPorClase}">
												<tr>
													<td>
														<table>
															<tr>
																<td><fmt:message key="price" /></td>
																<td><fmt:message key="attendance" /></td>
																<td><fmt:message key="dispersion" /></td>			
															</tr>
															<c:forEach items="${pf.prediccionesPorClase}" var="pc">
																<tr>
																	<td><c:out value="${pc.clase.precio}" /></td>
																	<td><c:out value="${pc.asistencia}" /></td>
																	<td><fmt:formatNumber value="${fun:sqrt(pc.varianza)}" maxFractionDigits="2" minFractionDigits="2"></fmt:formatNumber></td>
																</tr>					
															</c:forEach>
														</table>			
													</td>
												</tr>
												<tr>
													<td>
														<a href="javascript:muestra(<c:out value="${pf.graficoRegresionClases.id}"></c:out>,<c:out value="${pf.graficoRegresionCoef.id}"></c:out>)";>
															<fmt:message key="graphics" /> <img border="0" alt="" width="10px" src="<c:url value="/images/graph_icon.png"/>"/>
														</a>					
													</td>
												</tr>
												
											</c:if>			
										</table>
									</div>					
								</td>
								<c:set var="iteradorPrediccionPorFuncion" value="${iteradorPrediccionPorFuncion + 1}"/>
							</c:if>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>	
</c:forEach>	
</table>

</div>	
	<c:forEach items="${p.prediccionPorDia}" var="pd">
		<c:forEach items="${pd.markersFecha}" var="m">
			<div style="display:none; padding: 15px; width:100px; height:auto; background-color:#FFF; color:#000; border:1px #000 solid;" id="tooltip_${k}">
			<c:if test="${not empty m.complejo}">
			<fmt:message key="complex" /> -
			</c:if>
			<c:if test="${not empty m.pelicula}">
			<fmt:message key="movie" /> -
			</c:if>
			<c:if test="${not empty m.clase}">
			<fmt:message key="class" /> <c:out value="${m.clase.precio}"></c:out> -
			</c:if>
			<c:out value="${m.descripcion}"></c:out>
			</div>
			<script type="text/javascript">
	  		var my_tooltip_<c:out value="${k}"/> = new Tooltip('<c:out value="trigger_${k}"/>', '<c:out value="tooltip_${k}"/>');
			</script>
			<c:set var="k" value="${k+1}"></c:set>	
		</c:forEach>		
	</c:forEach>

</c:forEach>

	<c:choose>
			<c:when test="${test && not cargar_solo}">
				<c:set var="disable_select_regresion" value="true"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="disable_select_regresion" value="false"></c:set>
			</c:otherwise>			
	</c:choose>
	

</div>
		

<div align="center">
	<div id="tablaForm">
		<input type="submit" name="volver" style="float:left;" id="boton" 
		value="<fmt:message key="return"/>" onclick="volver(); return false;" />
		
		<c:if test="${predicciones_guardadas}">
			<input type="button" class="submit" id="botonImportante2" value="<fmt:message key="charge" />" onclick="cargar(); return false;"/>
		</c:if>
		
		<c:if test="${!predicciones_guardadas}">
			<input type="button" class="submit" id="botonImportante" value="<fmt:message key='save' />" onclick="guardar(); return false;"/>
		</c:if>
		
	</div>
</div>

<div align="center" style="margin-top: 10px;">
	<fmt:message key="regresionType" />			
	<form:select path="tipo_regresion" disabled="${disable_select_regresion}" cssClass="select">
	<form:option value="0"><fmt:message key="lineal_by" /></form:option>
	<form:option value="1"><fmt:message key="lineal" /></form:option>
	<form:option value="2"><fmt:message key="exp" /></form:option>
	<form:option value="3"><fmt:message key="potencial" /></form:option>
	</form:select>
</div>

				
</form:form>	





<div id="graficos">
<div id="boton_grafico"> 
				<a href="javascript:cierra()";>
					<img border="0" alt="X" src="<c:url value="/images/delete_icon.gif"/>" /></a>
</div>

<c:forEach items="${predicciones}" var="p">
	<c:forEach items="${p.prediccionPorDia}" var="pd">
		<c:forEach items="${pd.prediccionesPorFuncion}" var="pf">
		<c:if test="${not empty pf.prediccionesPorClase}">
			<jsp:include page="FusionChartsRenderer.jsp" flush="true">
				<jsp:param name="chartSWF" value="${pf.graficoRegresionClases.swf}" />
				<jsp:param name="strURL" value="" />
				<jsp:param name="strXML" value="${pf.graficoRegresionClases.strXML}" />
				<jsp:param name="chartId" value="${pf.graficoRegresionClases.id}" />
				<jsp:param name="chartWidth"
					value="${pf.graficoRegresionClases.ancho}" />
				<jsp:param name="chartHeight" value="400" />
				<jsp:param name="debugMode" value="false" />
				<jsp:param name="registerWithJS" value="true" />
			</jsp:include>

			<jsp:include page="FusionChartsRenderer.jsp" flush="true">
				<jsp:param name="chartSWF" value="${pf.graficoRegresionCoef.swf}" />
				<jsp:param name="strURL" value="" />
				<jsp:param name="strXML" value="${pf.graficoRegresionCoef.strXML}" />
				<jsp:param name="chartId" value="${pf.graficoRegresionCoef.id}Coef" />
				<jsp:param name="chartWidth"
					value="${pf.graficoRegresionCoef.ancho}" />
				<jsp:param name="chartHeight" value="400" />
				<jsp:param name="debugMode" value="false" />
				<jsp:param name="registerWithJS" value="true" />
			</jsp:include>
		</c:if>
		</c:forEach>
	</c:forEach>
</c:forEach>
</div>






</div>
</div>
<div id="popup">
&nbsp;<fmt:message key="optimizing" />...<br>
<img align="middle" alt="" src="<c:url value="/images/loading_page.gif"/>"/>
</div>

<div id="flecha_derecha" align="center" onmouseover="scrollDivLeft('predicciones')" onmouseout="stopMe()" onclick="toRight('predicciones')">
<img width="25px" alt="" align="middle" src="<c:url value="/images/arrow_right.gif"/>"/>
</div>

<div id="flecha_izquierda" align="center" onmouseover="scrollDivRight('predicciones')" onmouseout="stopMe()" onclick="toLeft('predicciones')">
<img width="25px" alt="" align="middle" src="<c:url value="/images/arrow_left.gif"/>"/>
</div>

</body>

</html>
