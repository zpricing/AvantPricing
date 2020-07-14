<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
		<title><fmt:message key="pageTitle" /></title>
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="datepicker"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="form"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="tablas_admin"/>" />
		<style type="text/css">
			.error {
				color: red;
			}
		</style>
		<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
		<script type='text/javascript' src='<c:url value="/dwr/interface/dwrAdminPeliculas.js"/>'></script>
		
		<script type="text/javascript">
			function cambiarEstado(id){
				dwrAdminPeliculas.cambiarEstado(id, actualizaEstadoPelicula);
			}
		
			function actualizaEstadoPelicula(data) {
				cambiaTextoTabla("campo_activo_" + data.id, data.activo);
			}

			function cambiaTextoTabla(id, data) {
				document.getElementById(id).innerHTML = data;
			}
				
		
			function externalLinks() {  
			 if (!document.getElementsByTagName) return;  
			 var anchors = document.getElementsByTagName("a");  
			 for (var i=0; i<anchors.length; i++) {  
			   var anchor = anchors[i];  
			   if (anchor.getAttribute("href") &&  
			       anchor.getAttribute("rel") == "external")  
			     anchor.target = "_blank";  
			 }  
			}  
			window.onload = externalLinks;
		</script>
	</head>
	<body>
		<jsp:include page="/menu2.htm"></jsp:include>
		
		<div id="contenedor">
			<span id="titulo">
				<fmt:message key="movies" />
			</span> 
			<br>
			<div id="form-area">
				<form:form commandName="busqueda" method="post">
					<fieldset style="width: 250px;">
						<legend>
							<fmt:message key="search2" />
						</legend>
						<table id="tablaForm">
							<tr>
								<td>
									<label for="busqueda" id="busqueda"> 
										<fmt:message key="search" /> 
									</label>
								</td>
								<td>
									<form:input path="busqueda" />
								</td>
							</tr>
							<tr>
								<td>
									<input type="submit" id="boton" value="<fmt:message key="search"/>" />
								</td>
							</tr>
						</table>
					</fieldset>
				</form:form>
			</div>
			<table id="rounded-corner">
				<thead>
					<tr>
						<th scope="col" class="rounded-company"><fmt:message key="id" /></th>
						<th scope="col" class="rounded-q1"><fmt:message key="externId" /></th>
						<th scope="col" class="rounded-q1"><fmt:message key="name" /></th>
						<!--<th scope="col" class="rounded-q1"><fmt:message key="duration" /></th>-->
						<!--
						<th scope="col" class="rounded-q1"><fmt:message key="id" /> <fmt:message key="central" /></th>
						<th scope="col" class="rounded-q1"><fmt:message key="name" /> <fmt:message key="central" /></th>
						<th scope="col" class="rounded-q1"><fmt:message key="similarity"/></th>-->
						<!--  <th scope="col" class="rounded-q1"><fmt:message key="duration" /></th>-->
						<th scope="col" class="rounded-q1"><fmt:message key="categories" /></th>
						<th scope="col" class="rounded-q1"><fmt:message key="epochs" /></th>
						<th scope="col" class="rounded-q3"><fmt:message key="publics" /></th>
						<th scope="col" class="rounded-q3">Activada?</th>
						<th scope="col" class="rounded-q3">Acciones</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="5" class="rounded-foot-left"><em> <a
							href="<c:url value="admin_peliculas.htm" />?activo=si"><fmt:message
							key="actives" /></a> | <a
							href="<c:url value="admin_peliculas.htm" />?activo=no"><fmt:message
							key="noActives" /></a> | <a
							href="<c:url value="admin_peliculas.htm" />?activo=ambos&page=1"><fmt:message
							key="seeAll" /></a><br>
						<a href="<c:url value="admin_agregarpelicula.htm"/>"><fmt:message
							key="add" /> <fmt:message key="movie" /></a><br>
						<a href="<c:url value="admin_epocas.htm"/>"><fmt:message
							key="menu.epochs" /></a> | <a href="<c:url value="admin_categorias.htm"/>"><fmt:message
							key="categories" /></a> | <a href="<c:url value="admin_publicos.htm"/>"><fmt:message
							key="publics" /></a><br>
			
						</em></td>
						<td align="right" colspan="3" class="rounded-foot-right"><c:if
							test="${previous!=null}">
							<a
								href="<c:url value="admin_peliculas.htm" />?activo=ambos&page=<c:out value="${previous}" />"><span
								style="color: white; background-color: gray; border: 1px solid white; padding: 2px;">
							&lt; <fmt:message key="previous" /> </span> </a>
					&nbsp;
				</c:if> <c:if test="${next!=null}">
							<a
								href="<c:url value="admin_peliculas.htm" />?activo=ambos&page=<c:out value="${next}" />"><span
								style="color: white; background-color: gray; border: 1px solid white; padding: 2px;">
							&nbsp;<fmt:message key="next" /> &gt; </span> </a>
						</c:if></td>
					</tr>
				</tfoot>
				<tbody>
					<%--<th>Funciones planificadas</th>--%>
					<c:forEach items="${peliculas}" var="p">
						<tr style="font-size: 75%;">
							<td>
								<c:out value="${p.id}"></c:out>
							</td>
							<td>
								<c:out value="${p.idExterno}"></c:out>
							</td>
							<td>
								<a href="asistenciaporpelicula.htm?peliculaId=<c:out value="${p.id }"/>" rel="external" title="<fmt:message key="administrar.peliculas.verasistencia"/>" alt="<fmt:message key="administrar.peliculas.verasistencia"/>">
									<c:out value="${p.nombre}"></c:out>
								</a>
							</td>
							<!--  
							<td><c:out value="${p.idCentral}"></c:out></td>
							<td><c:out value="${p.nombreCentral}"></c:out></td>
							<td><c:out value="${p.gradoSimilitud}"></c:out></td>
							-->
							<td>
								<table>
									<c:forEach items="${p.categorias}" var="c">
										<tr>
											<td style="font-size: 68%; border: none;"><c:out
												value="${c.descripcion}"></c:out></td>
										</tr>
									</c:forEach>
								</table>
							</td>
							<td>
								<table>
									<c:forEach items="${p.epocas}" var="e">
										<tr>
											<td style="font-size: 68%; border: none;"><c:out
												value="${e.descripcion}"></c:out></td>
										</tr>
									</c:forEach>
								</table>
							</td>
							<td>
								<table>
									<c:forEach items="${p.tiposDePublico}" var="tP">
										<tr>
											<td style="font-size: 68%; border: none;"><c:out
												value="${tP.descripcion}"></c:out></td>
										</tr>
									</c:forEach>
								</table>
							</td>
							<td>
								<span id="campo_activo_<c:out value="${p.id}"/>">
								<c:out value="${p.activo}"></c:out>
								</span>
							</td>
							<td>
								<a href="<c:url value="admin_eliminarpelicula.htm?idpelicula=${p.id}"/>"><fmt:message key="eliminate" /></a>
								&nbsp;
								<a href="<c:url value="admin_editarpelicula.htm?idpelicula=${p.id}"/>"><fmt:message key="modify" /></a>
								&nbsp;
								<a href="javascript:cambiarEstado(<c:out value="${p.id}" />)"> Cambiar Estado</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
</html>