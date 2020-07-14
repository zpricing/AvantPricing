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
					<h2 class="box_head"><fmt:message key="users"/></h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<div class="toggle_container">
						<div class="block" style="opacity: 1;">
							<table class="static editable">
								<thead>
									<tr>
										<th><fmt:message key="user"/></th>
										<th><fmt:message key="name"/></th>
										<th><fmt:message key="email"/></th>
										<th><fmt:message key="role"/></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${user}" var="u">
										<tr>
											<td><c:out value="${u.usuario}"></c:out></td>
											<td><c:out value="${u.nombreCompleto}"></c:out></td>
											<td><c:out value="${u.email}"></c:out></td>
											<td><c:out value="${u.rol.rol}"></c:out></td>	
											<td>
												<button type="button" class="black has_text small img_icon" onClick="parent.location='<c:url value="modifyuser.htm?iduser=${u.id}"/>'">
													<img src="assets/adminica/images/icons/small/white/pencil.png">
													<span><fmt:message key="modify" /></span>
												</button>
												<button type="button" class="red has_text small img_icon" onClick="parent.location='<c:url value="deleteuser.htm?iduser=${u.id}"/>'">
													<img src="assets/adminica/images/icons/small/white/trashcan.png">
													<span><fmt:message key="eliminate" /></span>
												</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<sec:authorize ifAllGranted="ROLE_ADMIN">
								<div class="button_bar clearfix">
									<button type="buttton" class="green has_text small img_icon" onClick="parent.location='<c:url value="admin_newuser.htm"/>'">
										<div class="ui-icon ui-icon-plus"></div>
										<span><fmt:message key="add" /> <fmt:message key="user" /></span>
									</button>
								</div>
							</sec:authorize>
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