<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
		<title><fmt:message key="pageTitle" /></title>
		<style>
			.error {
				color: red;
			}
		</style>
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="form"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="tablas_admin"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="datepicker"/>" />
		<script type="text/javascript" src="<c:url value="/js/popcalendar.js" />"></script>
		<script type="text/javascript" src="<c:url value="/js/showhide.js" />" > </script>
		<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
		<script type="text/javascript">
		
			function pintar(){
				select = dwr.util.byId("select_tm"); 
				select.style["color"] = select.options[select.selectedIndex].style["color"];
				// // esto es para que funcione el calendario
			}
			init();
		</script>
	</head>
	<body onload="pintar();">

	<jsp:include page="/menu2.htm"></jsp:include>

	<div id="contenedor">
		<span id="titulo"><fmt:message key="marker" /></span>
		<br>
		<div id="form-area">
		<form:form method="post" commandName="managemarkers" cssClass="formStyle">
			<fieldset style="width: 250px;">
				<legend><fmt:message key="add"/> <fmt:message key="marker" /></legend>
				<table id="tablaForm">
					<tr>
						<td><fmt:message key="user" /></td>
						<td><c:out value="${user}"></c:out></td>
					</tr>
					<tr>
						<td><fmt:message key="markerType" /></td>
						<td>
							<form:select path="tipo_marker_id" onchange="pintar();" id="select_tm" cssStyle="width:144px; font-family: 'Lucida Sans Unicode', 'Lucida Grande', Sans-Serif;font-size: 12px;">
								<c:forEach items="${tipoMarkers}" var="tm">
									<form:option value="${tm.id}" cssStyle="color:${tm.color};">${tm.descripcion}</form:option>
								</c:forEach>
							</form:select>
						</td>
					</tr>
					<tr>
						<td><fmt:message key="description" /></td>
						<td><form:input path="descripcion" /></td>
					</tr>
					<tr>
						<td><fmt:message key="date" /></td>
						<td>
							<form:input path="fecha" readonly="true" cssClass="fecha" />
							<img name="calendar1" title="calendar" alt="calendar" src="<c:url value="/images/calendar/cal.jpg"/>" width="20" height="20" onclick="javascript:popUpCalendar(this,document.getElementById('fecha'), 'dd-mm-yyyy');" />
							&nbsp; 
							<a class="jsclick" onclick="javascript:showhide('hasta');"><fmt:message key="add" /> <fmt:message key="range" /></a></td>
					</tr>
					<tr id="hasta" style="display: none;" >
						<td>
							<fmt:message key="until" />
						</td>
						<td>
							<form:input path="fecha_hasta" readonly="true" cssClass="fecha" />
							<img name="calendar1" title="calendar" alt="calendar" src="<c:url value="/images/calendar/cal.jpg"/>" width="20" height="20" onclick="javascript:popUpCalendar(this,document.getElementById('fecha_hasta'), 'dd-mm-yyyy');" />
						</td>
					</tr>
					<tr>
						<td><fmt:message key="movie" /></td>
						<td><form:select path="pelicula_id" id="sel">
							<form:option value="-"> <fmt:message key="all2" /> </form:option>
							<c:forEach items="${peliculas}" var="p">
								<form:option value="${p.id}">${p.nombre}</form:option>
							</c:forEach>
						</form:select></td>
					</tr>
					<tr>
						<td><fmt:message key="complex" /></td>
						<td><form:select path="complejo_id" cssStyle="width:144px; font-family: 'Lucida Sans Unicode', 'Lucida Grande', Sans-Serif;
			  	font-size: 12px;">
							<form:option value="-"><fmt:message key="all" /> </form:option>
							<c:forEach items="${complejos}" var="c">
								<form:option value="${c.id}">${c.nombre}</form:option>
							</c:forEach>
						</form:select></td>
					</tr>
					<tr>
						<td><fmt:message key="class" /></td>
						<td><form:select path="clase_id" cssStyle="width:144px; font-family: 'Lucida Sans Unicode', 'Lucida Grande', Sans-Serif;
			  	font-size: 12px;">
							<form:option value="-"> <fmt:message key="all2" /> </form:option>
							<c:forEach items="${clases}" var="c">
								<form:option value="${c.id}"><fmt:formatNumber value="${c.precio}" pattern="###,###"/></form:option>
							</c:forEach>
						</form:select></td>
					</tr>
					<tr>
						<td></td>
						<td align="right"><input id="boton" type="submit"
							value="<fmt:message key="add"/>"></td>
					</tr>
				</table>
			</fieldset>
		</form:form>
		</div>
		
		
		<br>
		<table id="rounded-corner">
			<thead>
			<tr>
			<th  scope="col" class="rounded-company" ><fmt:message key="user" /></th>
			<th scope="col" class="rounded-q1"><fmt:message key="marker" /></th>
			<th scope="col" class="rounded-q1"><fmt:message key="description" /></th>
			<th scope="col" class="rounded-q1" ><fmt:message key="date" /></th>
			<th scope="col" class="rounded-q1"><fmt:message key="until" /></th>	
			<th scope="col" class="rounded-q1"><fmt:message key="movie" /></th>
			<th scope="col" class="rounded-q1"><fmt:message key="complex" /></th>
			<th scope="col" class="rounded-q1"><fmt:message key="class" /></th>
			<th scope="col" class="rounded-q1"><fmt:message key="modify" /></th>
			<th scope="col" class="rounded-q4"><fmt:message key="eliminate" /></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${markers}" var="m">
				<tr>
					<td><c:out value="${m.usuario.nombreCompleto}"></c:out></td>
					<td><c:out value="${m.tipoMarker.descripcion}"></c:out></td>
					<td><c:out value="${m.descripcion}"></c:out></td>
					<td><fmt:formatDate value="${m.fecha}" pattern="dd-MM-yyyy" /></td>
					<td><fmt:formatDate value="${m.fechaHasta}" pattern="dd-MM-yyyy" /></td>			
					<td><c:out value="${m.pelicula.nombre}"></c:out></td>
					<td><c:out value="${m.complejo.nombre}"></c:out></td>
					<td><fmt:formatNumber value="${m.clase.precio}" pattern="###,###"/></td>
					<td><a
						href="<c:url value="admin_modifymarkers.htm?id_marker=${m.id}"/>"><fmt:message key="modify" /></a></td>
					<td><a
						href="<c:url value="admin_deletemarker.htm?id_marker=${m.id}"/>"><fmt:message
						key="eliminate" /></a></td>
				</tr>
			</c:forEach>
			</tbody>
			<tfoot>
			
			<tr>
		        	<td colspan="9" class="rounded-foot-left"><em><a href="<c:url value="admin_tipomarker.htm"/>"><fmt:message key="manage" /> <fmt:message key="markerType" /></a></em></td>
		        	<td class="rounded-foot-right">&nbsp;</td>
		        </tr>
			</tfoot>
		</table>
	
	
	</div>
<jsp:include page="/footer.htm"></jsp:include>
</body>
</html>