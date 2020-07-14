<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
		<jsp:include page="../template/header.jsp"></jsp:include>
		<%--<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="datepicker"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="form"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="tablas_admin"/>" />--%> 

	<style type="text/css">
		.error {
			color: red;
		}
	</style>
	<script type="text/javascript">
		function externalLinks() {  
 			if (!document.getElementsByTagName) return;  
 			var anchors = document.getElementsByTagName("a");  
 			for (var i=0; i<anchors.length; i++) {  
   				var anchor = anchors[i];  
   				if (anchor.getAttribute("href") && anchor.getAttribute("rel") == "external")
   	   				anchor.target = "_blank";
	   		}
	   	}

	   	window.onload = externalLinks;
	</script>
	</head>
	<body>

		<jsp:include page="../template/cabecera.jsp"></jsp:include>
	
		<jsp:include page="../template/menu.jsp"></jsp:include>
		
		<div id="tablas">
		
			<div id="titulo"><fmt:message key="movies" /></div>
			<div id="subtitulo">Cambios Pendientes</div>
			  
			<div id="form-area">
				<form:form commandName="busqueda" method="post">
					<fieldset style="width: 250px;">
						<legend><fmt:message key="search2" /></legend>
						<table id="tablaForm">
							<tr>
								<td><label for="busqueda" id="busqueda"> <fmt:message key="search" /> </label></td>
								<td><form:input path="busqueda" /></td>
							</tr>
							<tr>
								<td><input type="submit" id="boton" value="<fmt:message key="search"/>" /></td>
							</tr>
						</table>
					</fieldset>
				</form:form>
			</div>			
			
			<br /><br />
			
			<!-- COMIENZO TABLA -->
			<table class="stats" cellspacing="0">
				<tr>
					<!-- CABECERA TABLA -->					
					
					<td class="hed"><fmt:message key="id" /></td>
					<td class="hed"><fmt:message key="externId" /></td>
					<td class="hed"><fmt:message key="name" /></td>
					<td class="hed"><fmt:message key="duration" /></td>
					<td class="hed"><fmt:message key="id" /> <fmt:message key="central" /></td>
					<td class="hed"><fmt:message key="name" /> <fmt:message key="central" /></td>
					<td class="hed"><fmt:message key="similarity"/></td>
					<td class="hed"><fmt:message key="duration" /></td>
					<td class="hed"><fmt:message key="categories" /></td>
					<td class="hed"><fmt:message key="epochs" /></td>
					<td class="hed"><fmt:message key="publics" /></td>
					<td class="hed"><fmt:message key="eliminate" /></td>
					<td class="hed"><fmt:message key="modify" /></td>
					
					<!-- FIN CABECERA TABLA -->
				</tr>					
					
				
				<!-- CUERPO TABLA -->
				
				<c:forEach items="${peliculas}" var="p">
					<tr style="font-size: 75%;">
						<td><c:out value="${p.id}"></c:out></td>
						<td><c:out value="${p.idExterno}"></c:out></td>
						<td>
							<a href="asistenciaporpelicula.htm?peliculaId=<c:out value="${p.id }"/>" rel="external" title="<fmt:message key="administrar.peliculas.verasistencia"/>" alt="<fmt:message key="administrar.peliculas.verasistencia"/>">
								<c:out value="${p.nombre}"></c:out>
							</a>
						</td>
						<td><c:out value="${p.duracion}"></c:out></td>
						<td><c:out value="${p.idCentral}"></c:out></td>
						<td><c:out value="${p.nombreCentral}"></c:out></td>
						<td><c:out value="${p.gradoSimilitud}"></c:out></td>
						<td><c:out value="${p.duracion}"></c:out></td>
						<td>
							<table>
								<c:forEach items="${p.categorias}" var="c">
									<tr>
										<td style="font-size: 68%; border: none;"><c:out value="${c.descripcion}"></c:out></td>
									</tr>
								</c:forEach>
							</table>
						</td>
						<td>
							<table>
								<c:forEach items="${p.epocas}" var="e">
									<tr>
										<td style="font-size: 68%; border: none;"><c:out value="${e.descripcion}"></c:out></td>
									</tr>
								</c:forEach>
							</table>
						</td>
						<td>
							<table>
								<c:forEach items="${p.tiposDePublico}" var="tP">
									<tr>
										<td style="font-size: 68%; border: none;"><c:out value="${tP.descripcion}"></c:out></td>
									</tr>
								</c:forEach>
							</table>
						</td>
						<td>
							<a href="<c:url value="admin_eliminarpelicula.htm?idpelicula=${p.id}"/>">
								<fmt:message key="eliminate" />
							</a>
						</td>
						<td> 
							<a href="<c:url value="admin_editarpelicula.htm?idpelicula=${p.id}"/>">
								<fmt:message key="modify" />
							</a>
						</td>
					</tr>
				</c:forEach>
				
				<!-- FIN CUERPO TABLA -->
				
				<!-- FOOTER TABLA -->
				<tr>
					<td>
						<em>
							<a href="<c:url value="admin_peliculas.htm" />?activo=si">
								<fmt:message key="actives" />
							</a> | 
							<a href="<c:url value="admin_peliculas.htm" />?activo=no">
								<fmt:message key="noActives" />
							</a> | 
							<a href="<c:url value="admin_peliculas.htm" />?activo=ambos&page=1">
								<fmt:message key="seeAll" />
							</a>
							<br>
							<a href="<c:url value="admin_agregarpelicula.htm"/>">
								<fmt:message key="add" /> <fmt:message key="movie" />
							</a>
							<br>
							<a href="<c:url value="admin_epocas.htm"/>">
								<fmt:message key="menu.epochs" />
							</a> | 
							<a href="<c:url value="admin_categorias.htm"/>">
								<fmt:message key="categories" />
							</a> | 
							<a href="<c:url value="admin_publicos.htm"/>">
								<fmt:message key="publics" />
							</a>
							<br>
						</em>
					</td>
					<td align="right" colspan="3" class="rounded-foot-right">
						<c:if test="${previous!=null}">
							<a href="<c:url value="admin_peliculas.htm" />?activo=ambos&page=<c:out value="${previous}" />">
								<span style="color: white; background-color: gray; border: 1px solid white; padding: 2px;">
									&lt; 
									<fmt:message key="previous" />
								</span>
							</a>
							&nbsp;
						</c:if>
						<c:if test="${next!=null}">
							<a href="<c:url value="admin_peliculas.htm" />?activo=ambos&page=<c:out value="${next}" />">
								<span style="color: white; background-color: gray; border: 1px solid white; padding: 2px;">
									&nbsp;
									<fmt:message key="next" />
									&gt;
								</span>
							</a>
						</c:if>
					</td>
					<!-- FIN FOOTER TABLA -->
				</tr>
			</table>
		</div>
		
		<!-- FORM DE BUSQUEDA -->
		<%--<div id="form-area">
			<form:form commandName="busqueda" method="post">
				<fieldset style="width: 250px;">
					<legend><fmt:message key="search2" /></legend>
					<table id="tablaForm">
						<tr>
							<td><label for="busqueda" id="busqueda"> <fmt:message key="search" /> </label></td>
							<td><form:input path="busqueda" /></td>
						</tr>
						<tr>
							<td><input type="submit" id="boton" value="<fmt:message key="search"/>" /></td>
						</tr>
					</table>
				</fieldset>
			</form:form>
		</div> --%>
		<!-- FIN FORM DE BUSQUEDA -->
		
		<jsp:include page="../template/footer.jsp"></jsp:include>
	</body>
</html>
		
