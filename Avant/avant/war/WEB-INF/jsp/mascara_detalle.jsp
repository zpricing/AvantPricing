<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<script>
		$(document).ready(function() {
			$(".editar").live('click', function() {
				$(this).parent().parent().parent().addClass("editando");
			});

			$(":input").live('click', function() {
				$(this).parent().parent().addClass("editando");
			});

			$(".cancelar_edicion").live('click', function() {
				$(this).parent().parent().parent().removeClass("editando");
			});
		});
	</script>
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="3" data-adminica-side-inner="3">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="booking_limits" /></h2>
				</div>

				<form:form commandName="grupos">
					<div class="box grid_16">
						<h2 class="box_head"><c:out value="${grupos.complejo.nombre}"/> (<fmt:message key="mask" />: <c:out value="${grupos.mascara.descripcion}"/>)</h2>
						<div class="controls">
							<a href="#" class="grabber"></a>
							<a href="#" class="toggle"></a>
						</div>
						<div class="toggle_container">
							<div class="block" style="opacity: 1;">
								<table class="static editable">
									<thead>
										<tr>
											<th><fmt:message key="group" /></th>
											<c:forEach items="${grupos.mascarasAreasGrupos[0]}" var="mascaraAreaGrupo" varStatus="i">
												<th colspan="2"><c:out value="${grupos.mascarasAreasGrupos[0][i.index].area.descripcion}"/></th>
											</c:forEach>
											<th colspan="2"></th>
										</tr>
										<tr>
											<th></th>
											<c:forEach items="${grupos.mascarasAreasGrupos[0]}" var="mascaraAreaGrupo" varStatus="i">
												<th><fmt:message key="booking_limit" /></th>
												<th><fmt:message key="days" /></th>	
											</c:forEach>
											<th colspan="2"><fmt:message key="min_porc" /></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${grupos.mascarasAreasGrupos}" var="mascaraAreaGrupo" varStatus="i">
											<tr>
												<td>
													<c:out value="${grupos.mascarasAreasGrupos[i.index][0].grupo.descripcion}"/>
												</td>
												<c:forEach items="${grupos.mascarasAreasGrupos[i.index]}" var="grupos" varStatus="j">
													<td>
														<form:input path="mascarasAreasGrupos[${i.index}][${j.index}].capacidad" size="4"/>
													</td>
													<td>
														<form:input path="mascarasAreasGrupos[${i.index}][${j.index}].diasExpiracion" size="4"/>
													</td>
												</c:forEach>
												<td>
													<form:input path="mascarasAreasGrupos[${i.index}][0].porcentajeMinimo" size="4"/>
												</td>
												<td>
													<div class="clearfix">
														<button type="button" class="black icon_only small img_icon img_editar editar">
															<img src="assets/adminica/images/icons/small/white/pencil.png">
														</button>
														<button type="button" class="red icon_only small img_icon img_cancelar cancelar_edicion">
															<div class="ui-icon ui-icon-closethick"></div>
														</button>
													<div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="button_bar clearfix">
								<button type="submit" class="green has_text small img_icon">
									<div class="ui-icon ui-icon-check"></div>
									<span><fmt:message key="save" /></span>
								</button>
								<button class="light send_right" onClick="parent.location='admin_mascara.htm'">
									<div class="ui-icon ui-icon-closethick"></div>
									<span><fmt:message key="cancel" /></span>
								</button>
							</div>
						</div>
					</div>
				</form:form>

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