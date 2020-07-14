<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="2" data-adminica-side-inner="2">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="enterprises"/></h2>
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
							        	<th><fmt:message key="enterprise"/></th>
							            <th><fmt:message key="address"/></th>
							            <th><fmt:message key="email"/></th>
							            <th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${empresa}" var="empresa">
									    <tr>
								    		<td><c:out value="${empresa.id}"></c:out></td>
								    		<td><c:out value="${empresa.nombre}"></c:out></td>
											<td><c:out value="${empresa.direccion}"></c:out></td>
											<td><c:out value="${empresa.email}"></c:out></td>
											<td>
												<button type="button" class="black has_text small img_icon" onClick="parent.location='<c:url value="modifyempresas.htm?id_empresa=${empresa.id}"/>'">
													<img src="assets/adminica/images/icons/small/white/pencil.png">
													<span><fmt:message key="modify" /></span>
												</button>
												<button type="button" class="red has_text small img_icon" onClick="parent.location='<c:url value="deleteempresa.htm?id_empresa=${empresa.id}"/>'">
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
						<fmt:message key="new"/> <fmt:message key="enterprise"/>
					</h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<form:form method="post" commandName="managempresas">
						<div class="toggle_container">
							<div class="block" style="opacity: 1;">
								<div class="columns clearfix">
									<div class="col_30">
										<fieldset>
											<label><fmt:message key="name" /></label>
											<div>
												<form:input path="nombre" />
											</div>
										</fieldset>
									</div>
									<div class="col_30">
										<fieldset>
											<label><fmt:message key="address" /></label>
											<div>
												<form:input path="direccion" />
											</div>
										</fieldset>
									</div>
									<div class="col_40">
										<fieldset>
											<label><fmt:message key="email" /></label>
											<div>
												<form:input path="email" />
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