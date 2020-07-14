<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="4" data-adminica-side-inner="1">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="menuElements"/></h2>
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
					<form:form method="post" commandName="managemenu">
						<form:hidden path="modoForm"/>
						<form:hidden path="id_menu_element"/>
						<div class="toggle_container">
							<div class="block" style="opacity: 1;">
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="role" /></label>
											<div class="clearfix">
												<form:select path="rol_id" id="sel" cssClass="uniform full_width">
													<c:forEach items="${roles}" var="rol">
														<form:option value="${rol.id}">${rol.rol}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="title" /></label>
											<div>
												<form:input path="title" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="link" /></label>
											<div>
												<form:input path="link" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="parent" /></label>
											<div>
												<form:input path="parent" />
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
					<h2 class="box_head"><fmt:message key="complexs" /></h2>
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
										<th><fmt:message key="title"/></th>
										<th><fmt:message key="link"/></th>
										<th><fmt:message key="parent"/></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${elementos}" var="elemento">
										<tr>
											<td><c:out value="${elemento.id_menu_element}"></c:out></td>
											<td><c:out value="${elemento.rol.rol}"></c:out></td>
											<td><c:out value="${elemento.title}"></c:out></td>
											<td><c:out value="${elemento.link}"></c:out></td>
											<td><c:out value="${elemento.parent}"></c:out></td>
											<td>
												<button type="button" class="black has_text small img_icon" onClick="parent.location='<c:url value="managemenu.htm?id_menu_element=${elemento.id_menu_element}"/>'">
													<img src="assets/adminica/images/icons/small/white/pencil.png">
													<span><fmt:message key="modify" /></span>
												</button>
												<button type="button" class="red has_text small img_icon" onClick="parent.location='<c:url value="deletemenu.htm?id_menu_element=${elemento.id_menu_element}"/>'">
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