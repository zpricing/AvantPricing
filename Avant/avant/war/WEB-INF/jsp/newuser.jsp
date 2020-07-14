<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="4" data-adminica-side-inner="4">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="users"/></h2>
				</div>

				<div class="box grid_16">
					<h2 class="box_head">
						<c:if test="${anadir}"><fmt:message key="add"/></c:if>
						<c:if test="${!anadir}"><fmt:message key="modify"/></c:if>
						<fmt:message key="menuElement"/>
					</h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<form:form method="post" commandName="newuser">
						<div class="toggle_container">
							<div class="block" style="opacity: 1;">
								<div class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="name" /></label>
											<div>
												<form:input path="usuario" />
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="password" /></label>
											<div>
												<form:password path="password" />
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_30">
										<fieldset>
											<label><fmt:message key="name" /></label>
											<div>
												<form:input path="nombreCompleto" />
											</div>
										</fieldset>
									</div>
									<div class="col_30">
										<fieldset>
											<label><fmt:message key="email" /></label>
											<div>
												<form:input path="email" />
											</div>
										</fieldset>
									</div>
									<div class="col_40">
										<fieldset>
											<label><fmt:message key="email" /></label>
											<div class="clearfix">
												<form:select path="rol" id="sel" cssClass="uniform full_width">
													<c:forEach items="${rol}" var="rol">
														<form:option value="${rol.id}">${rol.rol}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="button_bar clearfix">
									<button type="submit" class="green has_text small img_icon">
										<div class="ui-icon ui-icon-check"></div>
										<span><fmt:message key="add"/></span>
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