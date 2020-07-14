<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value="/js/showhide.js"/>"></script>
		
<script type="text/javascript" src="<c:url value="/js/tabber.js" />" >No pezca javascript</script>
<link rel="stylesheet" href="<c:url value="/styles/tabber.css" />" TYPE="text/css" MEDIA="screen">
<link rel="stylesheet" href="<c:url value="/styles/tabber-print.css" />" TYPE="text/css" MEDIA="print">
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="form"/>" />
	<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />
<script type="text/javascript">

	/* Optional: Temporarily hide the "tabber" class so it does not "flash"
	   on the page as plain HTML. After tabber runs, the class is changed
	   to "tabberlive" and it will appear. */
	
	document.write('<style type="text/css">.tabber{display:none;}<\/style>');
		
</script>


<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor">
<span id="titulo"><fmt:message key="menu.priceTemplates"></fmt:message></span>
<br>

<form:form commandName="plantillas">
	<input type="submit" name="modificar" value="<fmt:message key="modify"/>" />
	<div class="tabber" style="margin-left: 8px; margin-right: 8px;">
		<c:forEach items="${plantillas.complejo}" var="complejo" varStatus="i">
		<div title="${complejo.nombre}" class="tabbertab">
			<c:forEach items="${plantillas.grupoPlantillas[i.index]}" var="grupo" varStatus="j">
				<br/><c:out value="${grupo.descripcion}"></c:out><br/><br/>
				<table id="rounded-corner">
					<thead>
						<tr>
							<th scope="col" class="rounded-company"><fmt:message key="template"></fmt:message></th>
							<th scope="col" class="rounded-q1"><fmt:message key="daysToPerformance" /></th>
							<th scope="col" class="rounded-q4"><fmt:message key="father" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${grupo.plantillas}" var="plantilla" varStatus="k">
							<tr>
								<td><c:out value="${plantilla.descripcion}"></c:out></td>
								<td><form:input path="grupoPlantillas[${i.index}][${j.index}].plantillas[${k.index}].diasFuncion"/></td>
								<td><form:checkbox path="grupoPlantillas[${i.index}][${j.index}].plantillas[${k.index}].padre"/></td>
							</tr>
							<tr>
								<td colspan="3">
								<em><a onclick="javascript:showhide('${i.index}-${j.index}-${k.index}');" style="cursor: pointer;">
									<fmt:message key="prices"></fmt:message> / <fmt:message key="areas"></fmt:message>
								</a></em>
								</td>
							</tr>
							<tr id="${i.index}-${j.index}-${k.index}" style="display: none;">
								<td colspan="3">
									<table id="tablaForm">
										<c:forEach items="${plantilla.clases}" var="clase" varStatus="l">
											<tr>
												<td><form:select
													path="grupoPlantillas[${i.index}][${j.index}].plantillas[${k.index}].clases[${l.index}].id">
													<form:options items="${clases}" itemLabel="precio"
														itemValue="id" />
												</form:select></td>
												<td>&lt;-&gt;</td>
												<td><form:select
													path="grupoPlantillas[${i.index}][${j.index}].plantillas[${k.index}].areas[${l.index}].id">
													<form:options items="${complejo.areas}"
														itemLabel="descripcion" itemValue="id" />
												</form:select></td>
												<td><a
													href="<c:url value="admin_plantillasDeletePrecio.htm" />?plantillaId=${plantilla.id}&claseId=${clase.id}">
												<img border="0"
													src="<c:url value="/images/delete_icon.gif" />" /> </a></td>
											</tr>
										</c:forEach>
										<tr>
											<td colspan="3"><a
												href="<c:url value="admin_plantillasAddPrecio.htm" />?grupoId=${grupo.id}&plantillaId=${plantilla.id}">
											<fmt:message key="add"></fmt:message> </a></td>
										</tr>
									</table>
								</td>		
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:forEach>
		</div>
		</c:forEach>
	</div>
</form:form>
	</div>