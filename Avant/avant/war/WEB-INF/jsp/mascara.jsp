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
					<h2><fmt:message key="menu.masks"></fmt:message></h2>
				</div>

				<form:form commandName="mascaras">
					<div class="box grid_16 side_tabs tabs">
						<div class="side_holder">
							<ul class="tab_sider clearfix">
								<c:forEach items="${mascaras.complejos}" var="complejo" varStatus="i">
									<li><a href="#tabs-${complejo.id}"><c:out value='${complejo.nombre}'/></a></li>
								</c:forEach>
							</ul>
						</div>
						<c:forEach items="${mascaras.complejos}" var="complejo" varStatus="i">
							<div id="tabs-${complejo.id}" class="block">
								<div class="section">
									<div class="box grid_16">
										<div>
											<table class="static editable">
												<thead>
													<tr>
														<th><fmt:message key="name" /></th>
														<th><fmt:message key="order" /></th>
														<th><fmt:message key="description" /></th>
														<th colspan="2"></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${mascaras.mascaras[i.index]}" var="mascara_temp" varStatus="j">
														<tr>
															<td>
																<form:input path="mascaras[${i.index}][${j.index}].descripcion" />
															</td>
															<td><form:input path="mascaras[${i.index}][${j.index}].orden" size="4"/></td>
															<td>
																<form:input path="mascaras[${i.index}][${j.index}].descripcionDetallada" size="30"/>
															</td>
															<td>
																<div class="clearfix">
																	<button type="button" class="black icon_only small img_icon img_editar editar">
																		<img src="assets/adminica/images/icons/small/white/pencil.png">
																	</button>
																	<button type="button" class="red icon_only small img_icon img_cancelar cancelar_edicion">
																		<div class="ui-icon ui-icon-closethick"></div>
																	</button>
																	<button type="button" class="black icon_only small img_icon" onClick="parent.location='admin_mascara_detalle.htm?complejo_id=<c:out value='${complejo.id}'/>&mascara_id=<c:out value='${mascara_temp.id}'/>'">
																		<img src="assets/adminica/images/icons/small/white/list.png">
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
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
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