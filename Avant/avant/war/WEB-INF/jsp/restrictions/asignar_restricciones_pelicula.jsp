<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="../header.jsp"/>
<body>	
	<script>
		$(function() {
			date = $( ".datepicker" ).val();
			$( ".datepicker" ).datepicker("option", "dateFormat", "dd-mm-yy");
			$( ".datepicker" ).val(date);
		});
	</script>
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="3" data-adminica-side-inner="5">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="assign_movie_restrictions_title" /></h2>
				</div>

				<div class="box grid_16">
					<h2 class="box_head"><fmt:message key="assign_movie_restrictions_title" /></h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<form:form method="post" commandName="asignar_restriccion_pelicula" action="admin_asignar_restricciones_pelicula.htm">
						<div class="toggle_container">
							<div class="block">
								
								<div class="columns clearfix">
									<div class="col_60">
										<fieldset class="label_side">
											<label><fmt:message key="assign_movie_restrictions_select_restriction" /></label>
											<div class="clearfix">
												<form:select path="restriccion" id="sel_restriccion" cssClass="uniform full_width">
													<c:forEach items="${listaRestricciones}" var="restriccion">
														<form:option value="${restriccion.id}">
															<c:out value="${restriccion.descripcion}"></c:out>
														</form:option>
													</c:forEach>
												</form:select>
											</div>
										</fieldset>
									</div>
									<div class="col_40">
										<fieldset class="right label_side">
											<label><fmt:message key="assign_movie_restrictions_select_date" /></label>
											<div>
												<input type="text" class="datepicker" name="fecha" id="fecha_inicio_edit"/>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<fieldset class="label_side top">
										<label><fmt:message key="assign_movie_restrictions_select_movies" /></label>
										<div class="clearfix">
											<form:select path="peliculas" id="sel_pelicula" multiple="multiple" cssClass="multisorter indent" cssStyle="height:230px;">
												<c:forEach items="${listaPeliculas}" var="pelicula">
													<form:option value="${pelicula.id}">
														<c:out value="${pelicula.nombre}"></c:out>
													</form:option>
												</c:forEach>
											</form:select>
										</div>
									</fieldset>
								</div>

								<div class="button_bar clearfix">
									<button type="submit" class="green has_text small img_icon">
										<div class="ui-icon ui-icon-check"></div>
										<span><fmt:message key="assign_movie_restrictions_submit" /></span>
									</button>
								</div>
							</div>
						</div>
					</form:form>
				</div>
			</div> <!-- #main_container -->
		</div> <!-- wrapper -->
	</div> <!-- #pjax -->		

	<div id="loading_overlay">
		<div class="loading_message round_bottom">
			<img src="assets/adminica/images/interface/loading.gif" alt="loading" />
		</div>
	</div>
</body>
</html>