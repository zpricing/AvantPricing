<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="4" data-adminica-side-inner="3">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="role"/></h2>
				</div>

				<c:if test="${error == 1}">
					<div class="box grid_16 no_titlebar">
						<div class="toggle_container">
							<div class="block" style="opacity: 1;">
								<div class="section">
									<div class="alert alert_red">
										<img height="24" width="24" src="assets/adminica/images/icons/small/white/alert_2.png">
										<fmt:message key="error" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:if> 

				<div class="box grid_16">
					<h2 class="box_head"><fmt:message key="role"/></h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<div class="toggle_container">
						<div class="block" style="opacity: 1;">
							<table class="static">
								<thead>
									<tr>
										<th><fmt:message key="id"/></th>
										<th><fmt:message key="role"/></th>
										<th><fmt:message key="description"/></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><c:out value="${rol.id}"></c:out></td>
										<td><c:out value="${rol.rol}"></c:out></td>
										<td><c:out value="${rol.descripcion}"></c:out></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>

				<div class="box grid_16">
					<h2 class="box_head"><fmt:message key="authority"/></h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<div class="toggle_container">
						<div class="block" style="opacity: 1;">
							<table class="static">
								<thead>
									<tr>
										<th><fmt:message key="id"/></th>
								    	<th><fmt:message key="name"/></th>
								    	<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${autoridades}" var="a">
										<tr>
											<td><c:out value="${a.id}"></c:out></td>
											<td><c:out value="${a.autoridad}"></c:out></td>
											<td>
												<button type="button" class="red has_text small img_icon" onClick="parent.location='<c:url value="admin_deleteauthority.htm?id_rol=${rol.id}&id_authority=${a.id}"/>'">
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

				<div class="box grid_16">
					<h2 class="box_head">
						<fmt:message key="add"/> <fmt:message key="authority"/>
					</h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<form:form method="post" commandName="setautorities">
						<form:hidden path="id"/>
						<form:hidden path="rolId"/>
						<div class="toggle_container">
							<div class="block" style="opacity: 1;">
								<div class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="authority" /></label>
											<div class="clearfix">
												<form:select path="autoridad" id="sel" cssClass="uniform full_width">
													<% int ind=0; %>
													<c:forEach items="${tipoAutoridad}" var="t">
														<form:option value="<%= ind++ %>">${t}</form:option>
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