<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="pageTitle" /></title>

<script type="text/javascript" src="<c:url value="/js/background.js" />">No pezca javascript</script>
<script type="text/javascript" src="<c:url value="/js/popup.js" />">No pezca javascript</script>

<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="form"/>" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />
</head>
<body>

<script>
        	var popup = new Popup();
	    </script>

<jsp:include page="/menu2.htm"></jsp:include>
	<c:if test="${isPSPF}">
	<form:form method="post" commandName="PrediccionIncompletaForm" id="onSubmitPSPF">
	<input type="hidden" name="method" value="onSubmitPSPF"></input>

		<div id="contenedor"><span id="titulo"><fmt:message
			key="predictions" /> <fmt:message key="without" /> <fmt:message
			key="prediction" /> <fmt:message key="by" /> <fmt:message
			key="function" /></span> <br>
		<table id="rounded-corner">
			<thead>
				<tr>
					<th scope="col" class="rounded-company"><fmt:message key="id" />
					</th>
					<th scope="col" class="rounded-q1"><fmt:message key="movie" />
					</th>
					<th scope="col" class="rounded-q1"><fmt:message key="complex" />
					</th>
					<th scope="col" class="rounded-q1"><fmt:message key="date" />
					</th>
					<th scope="col" class="rounded-q4"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pspf}" var="pf">
					<tr
						onclick="popup.show('<c:url value="mostrarprediccion.htm?idPrediccion=${pf.idPrediccion}"/>');" id="tr<c:out value="${pf.idPrediccion}"/>">
						<td><a href="eliminarprediccion.htm?idprediccion=<c:out value="${pf.idPrediccion}"/>" title="<fmt:message key="erasePrediction"/>"
								onclick="document.getElementById('tr<c:out value="${pf.idPrediccion}"/>').onclick='';">
<img border="0" width="12px" src="/ZPCinemasDev/images/delete_icon.gif" alt="<fmt:message key="erasePrediction"/>"/>
</a>
								 <c:out value="${pf.idPrediccion}"></c:out></td>
						<td><c:out value="${pf.pelicula}"></c:out></td>
						<td><c:out value="${pf.complejo}"></c:out></td>
						<td><fmt:formatDate value="${pf.fecha}" /></td>
						<td><form:radiobutton path="prediccionFuncion"
							value="${pf.idPrediccion}" onmouseout="popup.enable();"
							onmouseover="popup.disable();" /></td>
							
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
		<div id="tablaForm" style="margin-left: 8px; margin-bottom: 8px;">
		<input id="boton" type="submit" name="onSubmitPSPF"
			value="<fmt:message key="predict"/>" /></div>
		</div>
		</form:form>
	</c:if>

	<c:if test="${isPNoCargadas}">
	<form:form method="post" commandName="PrediccionIncompletaForm" id="onSubmitPNoCargadas">
	<input type="hidden" name="method" value="onSubmitPNoCargadas"></input>
		<div id="contenedor"><span id="titulo"> <fmt:message
			key="predictionsNotYetLoaded" /> </span> <br />
		<table id="rounded-corner">
			<thead>
				<tr>
					<th scope="col" class="rounded-company"><fmt:message key="id" />
					</th>
					<th scope="col" class="rounded-q1"><fmt:message key="movie" />
					</th>
					<th scope="col" class="rounded-q1"><fmt:message key="complex" />
					</th>
					<th scope="col" class="rounded-q1"><fmt:message key="date" />
					</th>
					<th scope="col" class="rounded-q4" />
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pnocargadas}" var="pnc">
					<tr
						onclick="popup.show('<c:url value="mostrarprediccion.htm?idPrediccion=${pnc.idPrediccion}"/>');" id="tr<c:out value="${pnc.idPrediccion}"/>">
						<td><a href="eliminarprediccion.htm?idprediccion=<c:out value="${pnc.idPrediccion}"/>" title="<fmt:message key="erasePrediction"/>"
								onclick="document.getElementById('tr<c:out value="${pnc.idPrediccion}"/>').onclick='';">
<img border="0" width="12px" src="/ZPCinemasDev/images/delete_icon.gif" alt="<fmt:message key="erasePrediction"/>"/>
</a> <c:out value="${pnc.idPrediccion}"></c:out></td>
						<td><c:out value="${pnc.pelicula}"></c:out></td>
						<td><c:out value="${pnc.complejo}"></c:out></td>
						<td><fmt:formatDate value="${pnc.fecha}" /></td>
						<td><form:checkbox path="prediccionNoCargadas"
							value="${pnc.idPrediccion}" onmouseout="popup.enable();"
							onmouseover="popup.disable();" /></td>
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
				<% // Puedo llegar y copiar? %>
		<div id="tablaForm" style="margin-left: 8px; margin-bottom: 8px;">
		<input id="boton" type="submit" name="onSubmitPNoCargadas"
			value="<fmt:message key="loadGoTo"/>" /></div>
		</div>
		</form:form>

	</c:if>
</body>
</html>