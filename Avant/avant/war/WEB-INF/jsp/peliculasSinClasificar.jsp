<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<script type="text/javascript">
		function searchMovies() {
			var unclassified = $('#unclassified').is(':checked') ? '1' : '0';
			var scheduled = $('#scheduled').is(':checked') ? '1' : '0';
			var search = $.trim($('#search').val());
			var url = '<c:url value="admin_peliculasSinClasificar.htm" />?unclassified='+unclassified+'&scheduled='+scheduled+'&search='+search;
			window.location.href = url;
		}
		$(document).ready(function() {
			$(window).keydown(function(event){
				if(event.keyCode == 13) {
				event.preventDefault();
				searchMovies();
				return false;
				}
			});
		});
	</script>
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="5" data-adminica-side-inner="1">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="menu.movies" /></h2>
				</div>

				<form:form commandName="peliculas">
					<div class="box grid_16">
						<h2 class="box_head"><fmt:message key="menu.movies" /></h2>
						<div class="controls">
							<a href="#" class="grabber"></a>
							<a href="#" class="toggle"></a>
						</div>
						<div class="toggle_container">
							<div class="block" style="opacity: 1;">
								<table class="static editable">
									<thead>
										<tr>
											<th scope="col" class="rounded-company"><fmt:message key="name" /></th>
											<th scope="col"><fmt:message key="category" /></th>
											<th scope="col"><fmt:message key="ranking" /></th>
											<th scope="col"><fmt:message key="format" /></th>
											<th scope="col"><fmt:message key="language" /></th>
											<th class="rounded-q4" scope="col"><fmt:message key="rating" /></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>
												<fieldset class="bottom">
													<input type="text" class="text" id="search" value="<c:out value="${search}"></c:out>">
												</fieldset>
											</td>
											<td>
												<fieldset class="bottom">
													<div class="uniform">
														<label for="unclassified">
															<input type="checkbox" name="unclassified" id="unclassified" <c:if test="${unclassified == '1'}">checked="checked"</c:if>/><fmt:message key="unclassified"/>
														</label>
													</div>
												</fieldset>
											</td>
											<td colspan="2">
												<fieldset class="bottom">
													<div class="uniform">
														<label for="scheduled"><input type="checkbox" name="scheduled" id="scheduled" <c:if test="${scheduled == '1'}">checked="checked"</c:if>/><fmt:message key="scheduled" /></label>
													</div>
												</fieldset>
											</td>
											<td colspan="2">
												<button type="button" class="black small img_icon has_text bottom" onClick="searchMovies()">
													<img src="assets/adminica/images/icons/small/white/magnifying_glass.png">
													<span><fmt:message key="search" /></span>
												</button>
											</td>
										</tr>
										<c:forEach items="${peliculas.peliculas}" var="pelicula" varStatus="i">
											<tr>
												<td>
													<c:out value="${fn:trim(pelicula.nombre)}"></c:out>
													[<c:out value="${pelicula.grupoPelicula.descripcion}"></c:out>]
													(<fmt:formatDate value="${pelicula.fechaEstreno}" pattern="dd/MM/yyyy" />)
													[<c:out value="${pelicula.cantidadDeFunciones}"></c:out>]
												</td>
												<td>
													<form:select path="peliculas[${i.index}].categorias[0].id" id="peliculas[${i.index}].categorias[0].id" cssClass="uniform full_width">
														<form:option value="-1">
															<fmt:message key="select"/>
														</form:option>
														<c:forEach items="${categorias}" var="c">
															<form:option value="${c.id}">
																<c:out value="${c.descripcion}"></c:out>
															</form:option>
														</c:forEach>
													</form:select>
												</td>
												<td>
													<form:select path="peliculas[${i.index}].ranking.id" id="peliculas[${i.index}].ranking.id" cssClass="uniform full_width">
														<form:option value="-1">
															<fmt:message key="select"/>
														</form:option>
														<c:forEach items="${rankings}" var="c">
															<form:option value="${c.id}">
																<c:out value="${c.descripcion}"></c:out>
															</form:option>
														</c:forEach>
													</form:select>
												</td>
												<td>
													<form:select path="peliculas[${i.index}].formato.id" id="peliculas[${i.index}].formato.id" cssClass="uniform full_width">
														<form:option value="-1">
															<fmt:message key="select"/>
														</form:option>
														<c:forEach items="${formatos}" var="c">
															<form:option value="${c.id}">
																<c:out value="${c.descripcion}"></c:out>
															</form:option>
														</c:forEach>
													</form:select>
												</td>
												<td>
													<form:select path="peliculas[${i.index}].idioma.id" id="peliculas[${i.index}].idioma.id" cssClass="uniform full_width">
														<form:option value="-1">
															<fmt:message key="select"/>
														</form:option>
														<c:forEach items="${idiomas}" var="c">
															<form:option value="${c.id}">
																<c:out value="${c.descripcion}"></c:out>
															</form:option>
														</c:forEach>
													</form:select>
												</td>
												<td>
													<form:select path="peliculas[${i.index}].rating.id" id="peliculas[${i.index}].rating.id" cssClass="uniform full_width">
														<form:option value="-1">
															<fmt:message key="select"/>
														</form:option>
														<c:forEach items="${ratings}" var="c">
															<form:option value="${c.id}">
																<c:out value="${c.descripcion}"></c:out>
															</form:option>
														</c:forEach>
													</form:select>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="button_bar clearfix">
									<button type="submit" class="green has_text small img_icon">
											<div class="ui-icon ui-icon-check"></div>
											<span><fmt:message key="save"/></span>
									</button>
									<c:if test="${next!=null}">
										<button type="button" class="black has_text small img_icon send_right" onClick="parent.location='<c:url value="admin_peliculasSinClasificar.htm" />?unclassified=<c:out value="${unclassified}" />&scheduled=<c:out value="${scheduled}" />&search=<c:out value="${search}" />&page=<c:out value="${next}" />'">
											<div class="ui-icon ui-icon-carat-1-e"></div>
											<span><fmt:message key="next" /></span>
										</button>
									</c:if> 
									<c:if test="${previous!=null}">
										<button type="button" class="black has_text small img_icon send_right" onClick="parent.location='<c:url value="admin_peliculasSinClasificar.htm" />?unclassified=<c:out value="${unclassified}" />&scheduled=<c:out value="${scheduled}" />&search=<c:out value="${search}" />&page=<c:out value="${previous}" />'">
											<div class="ui-icon ui-icon-carat-1-w"></div>
											<span><fmt:message key="previous" /> </span>
										</button>
									</c:if> 
								</div>
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