<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="2" data-adminica-side-inner="1">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="complex"/></h2>
				</div>

				<div class="box grid_16">
					<h2 class="box_head"><fmt:message key="complex" /></h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<form:form method="post" commandName="modifycomplejo">
						<form:hidden path="id"/>
						<div class="toggle_container">
							<div class="block" style="opacity: 1;">
								<div class="columns clearfix">
									<div class="col_40">
										<fieldset>
											<label><fmt:message key="name" /></label>
											<div>
												<form:input path="nombre" />
											</div>
										</fieldset>
									</div>
									<div class="col_60">
										<fieldset>
											<label><fmt:message key="address" /></label>
											<div>
												<form:input path="direccion" />
											</div>
										</fieldset>
									</div>
								</div>

								<div class="columns clearfix">
									<div class="col_40">
										<fieldset>
											<label><fmt:message key="enterprise" /></label>
											<div class="clearfix">
												<form:select path="empresa" id="sel" cssClass="uniform full_width">
													<c:forEach items="${empresas}" var="e">
														<c:choose>
															<c:when test="${e.id == id_empresa_original}">
																<option selected="selected" value="${e.id}">${e.nombre}</option>
															</c:when>
															<c:when test="${e.id != id_empresa_original}">
																<form:option value="${e.id}">${e.nombre}</form:option>
															</c:when>
														</c:choose>	
													</c:forEach>
												</form:select>
											</div>
										</fieldset>
									</div>
									<div class="col_30">
										<fieldset>
											<label><fmt:message key="externId" /></label>
											<div>
												<form:input path="complejo_id_externo" />
											</div>
										</fieldset>
									</div>
									<div class="col_30">
										<fieldset>
											<label><fmt:message key="serverIp" /></label>
											<div>
												<form:input path="ip_servidor" />
											</div>
										</fieldset>
									</div>
								</div>

								<div class="button_bar clearfix">
									<button type="submit" class="green has_text small img_icon">
										<div class="ui-icon ui-icon-check"></div>
										<span><fmt:message key="modify"/></span>
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