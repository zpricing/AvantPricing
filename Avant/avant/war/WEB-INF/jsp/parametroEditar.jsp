<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="4" data-adminica-side-inner="2">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="parameters" /></h2>
				</div>

				<div class="box grid_16">
					<h2 class="box_head">
						<fmt:message key="modify" /> <fmt:message key="parameter" />
					</h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<form:form method="post" commandName="parametroEditar">
						<div class="toggle_container">
							<div class="block" style="opacity: 1;">
								<div class="columns clearfix">
									<div class="col_30">
										<fieldset>
											<label><fmt:message key="system" /></label>
											<div>
												<form:input path="sistema" readonly="true" />
											</div>
										</fieldset>
									</div>
									<div class="col_30">
										<fieldset>
											<label><fmt:message key="subsystem" /></label>
											<div>
												<form:input path="subSistema" readonly="true" />
											</div>
										</fieldset>
									</div>
									<div class="col_40">
										<fieldset>
											<label><fmt:message key="value" /></label>
											<div>
												<form:input path="codigo" />
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