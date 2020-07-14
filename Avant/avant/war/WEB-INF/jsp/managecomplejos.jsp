<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="2" data-adminica-side-inner="1">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="complexs" /></h2>
				</div>

				<c:if test="${error == 1}">
					<div class="box grid_16 no_titlebar">
						<div class="toggle_container">
							<div class="block" style="opacity: 1;">
								<div class="section">
									<div class="alert alert_red">
										<img height="24" width="24" src="assets/adminica/images/icons/small/white/alert_2.png">
										<fmt:message key="error.complexReferenced" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:if> 

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
										<th><fmt:message key="externId" /></th>
										<th><fmt:message key="name" /></th>
										<th><fmt:message key="address" /></th>
										<th><fmt:message key="serverIp" /></th>
										<th><fmt:message key="cinema.lastCompleteDataExtraction" /></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${complejos}" var="c">
										<tr>
											<td><c:out value="${c.id}"></c:out></td>
											<td><c:out value="${c.complejo_id_externo}"></c:out></td>
											<td><c:out value="${c.nombre}"></c:out></td>
											<td><c:out value="${c.direccion}"></c:out></td>
											<td><c:out value="${c.servidorIp}"></c:out></td>
											<td><fmt:formatDate value="${c.ultimaCargaCompleta}" type="both" pattern="dd-MM-yyyy HH:mm" /></td>
											<td>
												<button type="button" class="black has_text small img_icon" onClick="parent.location='<c:url value="admin_modifycomplejo.htm?id_complejo=${c.id}"/>'">
													<img src="assets/adminica/images/icons/small/white/pencil.png">
													<span><fmt:message key="modify" /></span>
												</button>
												<button type="button" class="black has_text small img_icon" onClick="parent.location='<c:url value="admin_cinema_extract.htm?id=${c.id}"/>'">
													<img src="assets/adminica/images/icons/small/white/cog_3.png">
													<span><fmt:message key="cinema.dataExtraction" /></span>
												</button>
												<button type="button" class="black has_text small img_icon" onClick="parent.location='<c:url value="admin_cinema_extract.htm?id=${c.id}&full=true"/>'">
													<img src="assets/adminica/images/icons/small/white/cog_3.png">
													<span><fmt:message key="cinema.fullDataExtraction" /></span>
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
					<h2 class="box_head"><fmt:message key="add" /> <fmt:message key="complex" /></h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<form:form method="post" commandName="managecomplejos">
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
														<form:option value="${e.id}">${e.nombre}</form:option>
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