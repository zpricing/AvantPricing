<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="pageTitle" /></title>

<script type="text/javascript"
	src="<c:url value="/FusionCharts/FusionCharts.js"/>"></script>

<script type="text/javascript" src="<c:url value="/js/showhide.js"/>"></script>

<script type="text/javascript" src="<c:url value="/js/tabber.js" />">No pezca javascript</script>
<link rel="stylesheet" href="<c:url value="/styles/tabber.css" />"
	TYPE="text/css" MEDIA="screen">
<link rel="stylesheet" href="<c:url value="/styles/tabber-print.css" />"
	TYPE="text/css" MEDIA="print">
<script type="text/javascript">

			/* Optional: Temporarily hide the "tabber" class so it does not "flash"
			   on the page as plain HTML. After tabber runs, the class is changed
			   to "tabberlive" and it will appear. */
			
			document.write('<style type="text/css">.tabber{display:none;}<\/style>');

		</script>

<script language="javascript" type="text/javascript">
			function abrir()
			{
				document.getElementById('popup').style.display='block';
			}
			function setearPeliculaParaBorrar(grafico,pelicula) {
				var graficoDelQueBorrar = document.getElementById("borrarPeliculaX");
				var peliculaABorrar = document.getElementById("borrarPeliculaY");
				graficoDelQueBorrar.value = grafico;
				peliculaABorrar.value = pelicula;
				document.getElementById("asistenciapeliculadia").submit();
				return true;
			}
			function resizeGraficos() {
				var numeroGrafico = 4;
				var embedElement;
				while (document.getElementById("myChart"+numeroGrafico)!=null) {
					embedElement = document.getElementById("myChart"+numeroGrafico);
					embedElement.height = 50+parseInt(embedElement.height);
					embedElement.width= 200+parseInt(embedElement.width);
					numeroGrafico++;
				}

				var numeroGraficoAnt = 4;
				var embedElementAnt;
				while (document.getElementById("myChartAnt"+numeroGraficoAnt)!=null) {
					embedElementAnt = document.getElementById("myChartAnt"+numeroGraficoAnt);
					embedElementAnt.height = 50+parseInt(embedElementAnt.height);
					embedElementAnt.width= 200+parseInt(embedElementAnt.width);
					numeroGraficoAnt++;
				}
				
				return numeroGrafico + " " + numeroGraficoAnt;
			}
		
		</script>
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="form"/>" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="popup"/>" />

<script src="<c:url value="/js/prototype.js" />" type="text/javascript"></script>
<script src="<c:url value="/js/tooltip-v0.2.js" />"
	type="text/javascript"></script>

</head>
<body >
<jsp:include page="/menu2.htm"></jsp:include>
<div id="contenedor"><span id="titulo"><fmt:message
	key="prediction" /> <fmt:message key="by" /> <fmt:message key="day" />
<fmt:message key="for" /> <c:out value="${nombrePelicula}"></c:out></span> <br>
<form:form method="post" commandName="asistenciapeliculadia"
	onsubmit="abrir(); return true;">
	<div class="tabber">
	<%
		Object ODiasAPredecir = request.getAttribute("dias_a_predecir");
			int diasAPredecir = 1;
			if (ODiasAPredecir != null) {
				diasAPredecir = (Integer) ODiasAPredecir;
			}
			if (diasAPredecir <= 0)
				diasAPredecir = 1;
	%> <c:forEach items="${graficos}" var="g" varStatus="graficoStatus">
		<div title="${g.complejo}" class="tabbertab" align="center">
		<div id="grafico${graficoStatus.index}" align="center">
		<table>
			<tr>
				<c:if test="${not empty g.genGrafAnt.datos}">
					<td>
					<div id="divA${graficoStatus.index}" align="center"><fmt:message
						key="error.chart" /></div>
					<script type="text/javascript">
													var myChart = new FusionCharts("<c:url value="/FusionCharts/FCF_MSLine.swf"/>", "myChartAnt${graficoStatus.index}", "400", "300");
													myChart.setDataURL("<c:url value="${g.xmlGrafAnt}"/>");
													myChart.render("divA${graficoStatus.index}");
												</script></td>
				</c:if>
				<td>
				<div id="div${graficoStatus.index}" align="center"><fmt:message
					key="error.chart" /></div>
				<script type="text/javascript">
												var myChart = new FusionCharts("<c:url value="/FusionCharts/FCF_MSLine.swf"/>", "myChart${graficoStatus.index}", "500", "400");
												myChart.setDataURL("<c:url value="${g.xmlGraf}"/>");
												myChart.render("div${graficoStatus.index}");
											</script></td>
			</tr>
		</table>
		</div>
		<div style="color: #E54224;"><form:errors path="pesosPel" /></div>
		<br>
		<table id="rounded-corner">
			<thead>
				<tr>
					<th scope="col" class="rounded-company"></th>
					<th scope="col" class="rounded-q1"><fmt:message key="premiere" /></th>
					<th scope="col" class="rounded-q1"><fmt:formatDate
						value="${g.fechaEstreno.time}" /></th>
					<th colspan="<%=diasAPredecir%>"><fmt:message key="daysWeight" /></th>
					<th scope="col" class="rounded-q4"><fmt:message
						key="movieWeight" /></th>
				</tr>
				<tr>
					<th><fmt:message key="color" /></th>
					<th colspan="2"><fmt:message key="movie" /></th>
					<c:forEach items="${g.genGraf.categorias}" var="m">
						<th><c:out value="${m}"></c:out></th>
					</c:forEach>
					<th />
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${g.genGraf.datos}"
					end="${g.cantPeliculasValidas-1}" var="d"
					varStatus="peliculaStatus">
					<tr>
						<td style="background-color: #${d.color}"></td>
						<td id="trigger_cb${graficoStatus.index}_${peliculaStatus.index}"
							colspan="2"><c:out value="${d.nombreSerie}"></c:out></td>
						<c:forEach items="${d.pesosDias}" var="c" varStatus="diaStatus">
							<td
								id="trigger_db${graficoStatus.index}_${peliculaStatus.index}_${diaStatus.index}">
							<form:input
								path="pesosDias[${graficoStatus.index}][${peliculaStatus.index}][${diaStatus.index}]"
								size="2" /></td>
						</c:forEach>
						<td><form:input
							path="pesosPel[${graficoStatus.index}][${peliculaStatus.index}]"
							tabindex="${peliculaStatus.index+1}" size="4" /> <c:if
							test="${g.cantPeliculasValidas>1}">

								<img src="images/delete_icon.gif" 
								title="<fmt:message key="eraseMovie"/>"
								alt="<fmt:message key="eraseMovie"/>"
								style="cursor:pointer"
								onClick="return setearPeliculaParaBorrar(${graficoStatus.index},${peliculaStatus.index})" />
						</c:if></td>
					</tr>
				</c:forEach>

			</tbody>
			<thead>
				<tr>
					<th><fmt:message key="color" /></th>
					<th><fmt:message key="movie" /></th>
					<th><fmt:message key="premiere" /></th>
					<c:forEach items="${g.genGraf.categorias}" var="m">
						<th><c:out value="${m}"></c:out></th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${g.genGraf.datos}" var="d"
					varStatus="peliculaStatus">
					<tr>
						<td style="background-color: #${d.color}" />
						<td id="trigger_c${graficoStatus.index}_${peliculaStatus.index}"><c:out
							value="${d.nombreSerie}"></c:out></td>
						<td><fmt:formatDate value="${d.fecha.time}" /></td>
						<c:forEach items="${d.valoresPonderados}" var="d2"
							varStatus="diaStatus">
							<td nowrap="nowrap"
								id="trigger_d${graficoStatus.index}_${peliculaStatus.index}_${diaStatus.index}">
							<c:forEach items="${d2.tiposMarkers}" var="tm">
								<span
									style="color: <c:choose><c:when test="${tm != null}"><c:out value="${tm.color}"/></c:when><c:otherwise>#000000</c:otherwise></c:choose>;"><c:out
									value="${tm.codigo}" /> </span>
							</c:forEach> <br>
							<c:out value="${d2}"></c:out></td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="<%=diasAPredecir + 2%>" class="rounded-foot-left">&nbsp;</td>
					<td class="rounded-foot-right">&nbsp;</td>
				</tr>
			</tfoot>
		</table>
		<c:if test="${not empty g.genGrafAnt.datos}">
			<span
				onclick="javascript:showhide('tablaAnterior${graficoStatus.index}');"><fmt:message
				key="table" /> <fmt:message key="of" /> <fmt:message
				key="previousDays" /></span>
			<br>
			<div id="tablaAnterior${graficoStatus.index}" style="display: none;"
				align="center">
			<table id="rounded-corner">
				<thead>
					<tr>
						<th scope="col" class="rounded-company"></th>
						<th scope="col" class="rounded-q1"><fmt:message
							key="premiere" /></th>
						<th scope="col" class="rounded-q4"><fmt:formatDate
							value="${g.fechaEstreno.time}" /></th>
					</tr>
					<tr>
						<th><fmt:message key="color" /></th>
						<th><fmt:message key="movie" /></th>
						<th><fmt:message key="premiere" /></th>
						<c:forEach items="${g.genGrafAnt.categorias}" var="m">
							<th><c:out value="${m}"></c:out></th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${g.genGrafAnt.datos}" var="d">
						<tr>
							<td style="background-color: #${d.color}" />
							<td><c:out value="${d.nombreSerie}" /></td>
							<td><fmt:formatDate value="${d.fecha.time}" /></td>
							<c:forEach items="${d.valores}" var="d2">
								<td><c:out value="${d2}"></c:out></td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
			<br>
		</c:if>
		<div id="tablaForm"><input id="botonImportante" type="submit"
			name="p" value="<fmt:message key="weight"/>" /> <input id="boton"
			type="submit"
			style="float: left; margin-bottom: 4px; margin-left: 4px;"
			name="e${graficoStatus.index}" value="<fmt:message key="eliminate"/>" />
		</div>
		<%--
							<button id="trigger_comp${graficoStatus.index}">
								<fmt:message key="complexInfo"/>
							</button>
							--%></div>
	</c:forEach> <c:if test="${hayGraficosMalos}">
		<div title="<fmt:message key="unsuccessfulsPrediction"/>"
			class="tabbertab" align="center"><c:forEach
			items="${graficosMalos}" var="g">
			<c:out value="${g}" />
			<br>
		</c:forEach></div>
	</c:if></div>
	<div align="center">
	<div id="tablaForm"><input id="boton"
		style="float: left; margin-bottom: 8px; margin-left: 8px; margin-top: 8px;"
		type="submit" name="volver" value="<fmt:message key="return"/>" /> <input
		id="botonImportante" style="margin-top: 8px;" type="submit"
		name="aceptar" value="<fmt:message key="continue"/>" /></div>
	</div>
					<form:hidden path="borrarPeliculaX" />
				<form:hidden path="borrarPeliculaY" />
</form:form></div>
<c:forEach items="${graficos}" var="g" varStatus="graficoStatus">
	<%--
			<div id="tooltip_comp${graficoStatus.index}" class="tooltip" style="display: none;">
				<c:forEach items="${g.info}" var="i" varStatus="infoStatus">
					<c:out value="${i}"></c:out><br>
				</c:forEach>
				<c:forEach items="${g.markers}" var="m" varStatus="markerStatus">
					<span style="color: <c:choose><c:when test="${m.tipoMarker != null}"><c:out value="${m.tipoMarker.color}"/></c:when><c:otherwise>#000000</c:otherwise></c:choose>;"><c:out value="${m.descripcion}"/></span><br>
				</c:forEach>
			</div>
			--%>
	<c:forEach items="${g.genGraf.datos}" var="d"
		varStatus="peliculaStatus">
		<%--
				<div id="tooltip_c${graficoStatus.index}_${peliculaStatus.index}" class="tooltip" style="display: none; width:200px;" align="center">
					<c:forEach items="${d.info}" var="i" varStatus="infoStatus">
						<c:out value="${i}"></c:out><br>
					</c:forEach>
					<c:forEach items="${d.markers}" var="m" varStatus="markerStatus">
						<span style="color: <c:choose><c:when test="${m.tipoMarker != null}"><c:out value="${m.tipoMarker.color}"/></c:when><c:otherwise>#000000</c:otherwise></c:choose>;"><c:out value="${m.descripcion}"/></span><br>
					</c:forEach>
				</div>
				--%>
		<c:forEach items="${d.valores}" var="d2" varStatus="diaStatus">
			<div
				id="tooltip_d${graficoStatus.index}_${peliculaStatus.index}_${diaStatus.index}"
				class="tooltip" style="display: none; width: 200px;" align="center">
			<c:forEach items="${d2.info}" var="i" varStatus="infoStatus">
				<c:out value="${i}"></c:out>
				<br>
			</c:forEach> <c:forEach items="${d2.markers}" var="m" varStatus="markerStatus">
				<span
					style="color: <c:choose><c:when test="${m.tipoMarker != null}"><c:out value="${m.tipoMarker.color}"/></c:when><c:otherwise>#000000</c:otherwise></c:choose>;"><c:out
					value="${m.descripcion}" /></span>
				<br>
			</c:forEach></div>
		</c:forEach>
	</c:forEach>

</c:forEach>
<script type="text/javascript">
			<c:forEach items="${graficos}" var="g" varStatus="graficoStatus">
				<%--
				var my_tooltip_comp${graficoStatus.index} = new Tooltip('trigger_comp${graficoStatus.index}', 'tooltip_comp${graficoStatus.index}');
				--%>
				<c:forEach items="${g.genGraf.datos}" var="d" varStatus="peliculaStatus">
					<%--
					var my_tooltip_c${graficoStatus.index}_${peliculaStatus.index} = new Tooltip('trigger_c${graficoStatus.index}_${peliculaStatus.index}', 'tooltip_c${graficoStatus.index}_${peliculaStatus.index}');
					<c:if test="${peliculaStatus.index < g.cantPeliculasValidas}">
						var my_tooltip_cb${graficoStatus.index}_${peliculaStatus.index} = new Tooltip('trigger_cb${graficoStatus.index}_${peliculaStatus.index}', 'tooltip_c${graficoStatus.index}_${peliculaStatus.index}');
					</c:if>
					--%>
					<c:forEach items="${d.valoresPonderados}" var="d2" varStatus="diaStatus">
						var my_tooltip_d${graficoStatus.index}_${peliculaStatus.index}_${diaStatus.index}=new Tooltip('trigger_d${graficoStatus.index}_${peliculaStatus.index}_${diaStatus.index}', 'tooltip_d${graficoStatus.index}_${peliculaStatus.index}_${diaStatus.index}');
						<c:if test="${peliculaStatus.index < g.cantPeliculasValidas}">
							var my_tooltip_db${graficoStatus.index}_${peliculaStatus.index}_${diaStatus.index}=new Tooltip('trigger_db${graficoStatus.index}_${peliculaStatus.index}_${diaStatus.index}', 'tooltip_d${graficoStatus.index}_${peliculaStatus.index}_${diaStatus.index}');
						</c:if>
					</c:forEach>
				</c:forEach>
			</c:forEach>
			<%--
			Event.observe(window,"load",function() {
				$$("*").findAll(function(node){
					return node.getAttribute('title');
				}).each(function(node){
					new Tooltip(node,node.title);
					node.removeAttribute("title");
				});
			});
			--%>
		</script>
<jsp:include page="/footer.htm"></jsp:include>
<div id="popup">&nbsp;<fmt:message key="processing" /><br>
<img align="middle" alt="s"
	src="<c:url value="/images/loading_page.gif"/>"></div>
	<script type="text/javascript">
	//resizeGraficos();
	</script>
</body>
</html>