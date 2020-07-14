<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<script>
	$(function() {
			
		$("#sistemas").change(function(){
			parent.location='<c:url value="parametro.htm?Sistema="/>'+ $("#sistemas").val();
		});
	});
	</script>
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="4" data-adminica-side-inner="2">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="parameters"/></h2>
				</div>

				<div class="box grid_16 no_titlebar" style="opacity: 1; ">
					<div class="toggle_container">
						<div class="block" style="opacity: 1; ">
							<div class="columns clearfix">
								<div class="col_50">
									<fieldset class="label_side top bottom">
										<label><fmt:message key="systems" />:</label>
										<div class="clearfix">
											<select id="sistemas" class="uniform full_width">
												<c:forEach items="${sistemas}" var="sis">
													<option value="${sis}" <c:if test="${sistema==sis}">selected="true"</c:if>>${sis}</option>
												</c:forEach>
											</select>
										</div>
									</fieldset>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<c:if test="${parametro}">
					<div class="box grid_16">
						<h2 class="box_head"><fmt:message key="system"/>: <c:out value="${sistema}"></c:out></h2>
						<div class="controls">
							<a href="#" class="grabber"></a>
							<a href="#" class="toggle"></a>
						</div>
						<div class="toggle_container">
							<div class="block" style="opacity: 1;">
								<table class="static editable">
									<thead>
										<tr>
											<th><fmt:message key="subsystem"></fmt:message></th>
											<th> <fmt:message key="value"></fmt:message></th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${parametros}" var="par">
											<tr>
												<td>${par.subSistema }</td>
												<td>${par.codigo}</td>
												<td>
													<button type="button" class="red has_text small img_icon" onClick="parent.location='<c:url value="parametroEditar.htm?sistema=${par.sistema}&subsistema=${par.subSistema}"/>'">
														<img src="assets/adminica/images/icons/small/white/pencil.png">
														<span><fmt:message key="modify" /></span>
													</button>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="button_bar clearfix">
									<button type="button" class="green has_text small img_icon" onClick="parent.location='<c:url value="parametroNuevo.htm?Sistema=${sistema}"/>'">
										<div class="ui-icon ui-icon-plus"></div>
										<span><fmt:message key="add" /> <fmt:message key="parameter" /></span>
									</button>
								</div>
							</div>
						</div>
					</div>
				</c:if>

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