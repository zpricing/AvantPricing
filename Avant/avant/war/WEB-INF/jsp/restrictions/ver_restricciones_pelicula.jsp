<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="../header.jsp"/>
<body>	
	<script type="text/javascript">
		function eliminarRestriccionPelicula(pelicula_id, restriccion_id) {
			$('#pelicula_id').val(pelicula_id);
			$('#restriccion_id').val(restriccion_id);
			$('#form_eliminar').submit();
		}
	</script>
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="3" data-adminica-side-inner="5">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="list_movie_restrictions_title" /></h2>
				</div>

				<div class="box grid_16">
					<h2 class="box_head"><fmt:message key="list_movie_restrictions_title" /></h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<div class="toggle_container">
						<div class="block" style="opacity: 1;">
							<table class="static editable">
								<thead>
									<tr>
										<th><fmt:message key="list_movie_restrictions_column_header_movie" /></th>
										<th><fmt:message key="list_movie_restrictions_column_header_restriction" /></th>
										<th colspan="2"><fmt:message key="list_movie_restrictions_column_header_dateTo" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${peliculasConRestriccion}" var="peliculaConRestriccion">
										<tr>
											<td><c:out value="${peliculaConRestriccion.pelicula.nombre}"/></td>
											<td><c:out value="${peliculaConRestriccion.restriccion.descripcion}"/></td>
											<td><fmt:formatDate value="${peliculaConRestriccion.fechaHasta}" type="both" pattern="EEEE dd-MM-yyyy" /></td>
											<td>

												<button type="button" class="red icon_only small img_icon" onclick="javascript:eliminarRestriccionPelicula('<c:out value="${peliculaConRestriccion.pelicula.id}"/>', '<c:out value="${peliculaConRestriccion.restriccion.id}"/>')">
													<img src="assets/adminica/images/icons/small/white/trashcan.png">
												</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="button_bar clearfix">
							<button type="button" class="green has_text small img_icon" onClick="parent.location='admin_asignar_restricciones_pelicula.htm'">
								<div class="ui-icon ui-icon-plusthick"></div>
								<span><fmt:message key="list_movie_restrictions_link_add" /></span>
							</button>
						</div>
					</div>
				</div>
			</div> <!-- #main_container -->
		</div> <!-- wrapper -->
		<form method="post" action="admin_eliminar_restricciones_pelicula.htm" id="form_eliminar">
			<input type="hidden" name="pelicula_id" id="pelicula_id"/>
			<input type="hidden" name="restriccion_id" id="restriccion_id"/>
		</form>
	</div> <!-- #pjax -->		

	<div id="loading_overlay">
		<div class="loading_message round_bottom">
			<img src="assets/adminica/images/interface/loading.gif" alt="loading" />
		</div>
	</div>
</body>
</html>