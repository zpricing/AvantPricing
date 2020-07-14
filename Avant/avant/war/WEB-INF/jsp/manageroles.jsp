<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="4" data-adminica-side-inner="3">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="roles"/></h2>
				</div>

				<div class="box grid_16">
					<h2 class="box_head">
						<c:if test="${anadir}">	
							<fmt:message key="add"/>
						</c:if> 
						<c:if test="${!anadir}">
							<fmt:message key="modify"/>
						</c:if> 
						<fmt:message key="role"/>
					</h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<form:form method="post" commandName="manageroles">
						<form:hidden path="id"/>
						<form:hidden path="tipoIng"/>
						<div class="toggle_container">
							<div class="block" style="opacity: 1;">
								<div class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="role" /></label>
											<div>
												<form:input path="rol" />
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="description" /></label>
											<div>
												<form:input path="descripcion" />
											</div>
										</fieldset>
									</div>
								</div>
								<div class="button_bar clearfix">
									<button type="submit" class="green has_text small img_icon">
										<div class="ui-icon ui-icon-check"></div>
										<span><fmt:message key="save"/></span>
									</button>
								</div>
							</div>
						</div>
					</form:form>
				</div>

				<div class="box grid_16">
					<h2 class="box_head"><fmt:message key="roles" /></h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<div class="toggle_container">
						<div class="block" style="opacity: 1;">
							<table class="static editable">
								<thead>
									<tr>
											<th><fmt:message key="id"/></th>
											<th><fmt:message key="role"/></th>
											<th><fmt:message key="description"/></th>
											<th><fmt:message key="authority"/></th>
											<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${rol}" var="rol">
										<tr>
											<td><c:out value="${rol.id}"></c:out></td>
											<td><c:out value="${rol.rol}"></c:out></td>
											<td><c:out value="${rol.descripcion}"></c:out></td>
											<td><a href="<c:url value="admin_setautorities.htm?id_rol=${rol.id}"/>"><c:out value="${rol.autoridades}"></c:out></a></td>
											<td>
												<button type="button" class="black has_text small img_icon" onClick="parent.location='<c:url value="admin_manageroles.htm?id_rol=${rol.id}"/>'">
													<img src="assets/adminica/images/icons/small/white/pencil.png">
													<span><fmt:message key="modify" /></span>
												</button>
												<button type="button" class="red has_text small img_icon" onClick="parent.location='<c:url value="admin_deleterol.htm?id_rol=${rol.id}"/>'">
													<img src="assets/adminica/images/icons/small/white/trashcan.png">
													<span><fmt:message key="eliminate" /></span>
												</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
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