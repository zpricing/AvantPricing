<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<script type="text/javascript">
		function searchMovies() {
			var unclassified = $('#unclassified').is(':checked') ? '1' : '0';
			var scheduled = $('#scheduled').is(':checked') ? '1' : '0';
			var data_loaded = $('#data_loaded').is(':checked') ? '1' : '0';
			
			var url = '<c:url value="movie_groups.htm" />?unclassified='+unclassified+'&scheduled='+scheduled+'&data_loaded='+data_loaded;
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
					<h2><fmt:message key="menu.movieGroups" /></h2>
				</div>
				<form:form method="POST" commandName="gruposPelicula">
					<form:hidden path="formPage" value="${page}"/>
					<form:hidden path="formScheduled" value="${scheduled}"/>
					<form:hidden path="formUnclassified" value="${unclassified}"/>
					<form:hidden path="formDataLoaded" value="${dataLoaded}"/>
					
					<div class="box grid_16">
						<h2 class="box_head"><fmt:message key="menu.movieGroups" /></h2>
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
											<th scope="col"><fmt:message key="original_name" /></th>
											<th scope="col"><fmt:message key="external_id" /></th>
											<th class="rounded-q4" scope="col"><fmt:message key="external_data_loaded" /></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td colspan="2">
												<fieldset class="bottom" style="float:left">
													<div class="uniform">
														<label for="unclassified">
															<input type="checkbox" name="unclassified" id="unclassified" <c:if test="${unclassified == '1'}">checked="checked"</c:if>/><fmt:message key="unclassified"/>
														</label>
													</div>
												</fieldset>
												<fieldset class="bottom" style="float:left">
													<div class="uniform">
														<label for="scheduled">
															<input type="checkbox" name="scheduled" id="scheduled" <c:if test="${scheduled == '1'}">checked="checked"</c:if>/><fmt:message key="scheduled" />
														</label>
													</div>
												</fieldset>
												<fieldset class="bottom" style="float:left">
													<div class="uniform">
														<label for="data_loaded">
															<input type="checkbox" name="data_loaded" id="data_loaded" <c:if test="${dataLoaded == '1'}">checked="checked"</c:if>/><fmt:message key="data_loaded" />
														</label>
													</div>
												</fieldset>
											</td>
											<td>
												<button type="button" class="black small img_icon has_text bottom" onClick="searchMovies()">
													<img src="assets/adminica/images/icons/small/white/magnifying_glass.png">
													<span><fmt:message key="search" /></span>
												</button>
											</td>
										</tr>
										<c:forEach items="${gruposPelicula.grupos}" var="grupo" varStatus="i">
											<tr>
												<td>
													<c:out value="${fn:trim(grupo.descripcion)}"></c:out>
													<form:hidden path="grupos[${i.index}].id" />
													<form:hidden path="grupos[${i.index}].descripcion" />
												</td>
												<td>
													<form:input path="grupos[${i.index}].nombreOriginal" cssClass="uniform full_width" />
												</td>
												<td>
													<form:input path="grupos[${i.index}].externalSourceId" cssClass="uniform full_width" />
												</td>
												<td>
													<c:if test="${grupo.externalSourceDataLoaded}">Si</c:if>
													<c:if test="${!grupo.externalSourceDataLoaded}">No</c:if>
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
										<button type="button" class="black has_text small img_icon send_right" onClick="parent.location='<c:url value="movie_groups.htm" />?unclassified=<c:out value="${unclassified}" />&scheduled=<c:out value="${scheduled}" />&data_loaded=<c:out value="${dataLoaded}" />&pagina=<c:out value="${next}" />'">
											<div class="ui-icon ui-icon-carat-1-e"></div>
											<span><fmt:message key="next" /></span>
										</button>
									</c:if> 
									<c:if test="${previous!=null}">
										<button type="button" class="black has_text small img_icon send_right" onClick="parent.location='<c:url value="movie_groups.htm" />?unclassified=<c:out value="${unclassified}" />&scheduled=<c:out value="${scheduled}" />&data_loaded=<c:out value="${dataLoaded}" />&pagina=<c:out value="${previous}" />'">
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