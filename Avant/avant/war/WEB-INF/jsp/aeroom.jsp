<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="2" data-adminica-side-inner="3">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="room"/></h2>
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
					<h2 class="box_head">
						<c:if test="${anadir}"><fmt:message key="add"/></c:if>
						<c:if test="${!anadir}"><fmt:message key="modify"/></c:if> 
						<fmt:message key="room"/>
					</h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<form:form method="post" commandName="aeroom">
						<form:hidden path="id"/>
						<form:hidden path="tipoIng"/>
						<div class="toggle_container">
							<div class="block" style="opacity: 1;">
								<div class="columns clearfix">
									<div class="col_30">
										<fieldset>
											<label><fmt:message key="number" /></label>
											<div>
												<form:input path="numero" />
											</div>
										</fieldset>
									</div>
									<div class="col_30">
										<fieldset>
											<label><fmt:message key="capacity" /></label>
											<div>
												<form:input path="capacidad" />
											</div>
										</fieldset>
									</div>
									<div class="col_40">
										<fieldset>
											<label><fmt:message key="complex" /></label>
											<div class="clearfix">
												<form:select path="complejoAlCualPertenece" id="sel" cssClass="uniform full_width">
													<c:forEach items="${complejos}" var="c">
														<form:option value="${c.id}">${c.nombre}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</fieldset>
									</div>
								</div>

								<div class="columns clearfix">
									<div class="col_30">
										<fieldset>
											<label><fmt:message key="externId" /></label>
											<div>
												<form:input path="idExterno" />
											</div>
										</fieldset>
									</div>
									<div class="col_30">
										<fieldset>
											<label><fmt:message key="order" /></label>
											<div>
												<form:input path="orden" />
											</div>
										</fieldset>
									</div>
									<div class="col_40">
										<fieldset>
											<label><fmt:message key="group" /></label>
											<div class="clearfix">
												<form:select path="grupo" id="sel" cssClass="uniform full_width">
													<c:forEach items="${grupos}" var="s">
														<form:option value="${s.id}">${s.descripcion}</form:option>
													</c:forEach>
												</form:select>
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
										<th><fmt:message key="number"/></th>
										<th><fmt:message key="capacity"/></th>
										<th><fmt:message key="complex"/></th>
										<th><fmt:message key="group"/></th>
										<th><fmt:message key="externId"/></th>
										<th><fmt:message key="order"/></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${salas}" var="sala">
										<tr>
											<td><c:out value="${sala.id}"></c:out></td>
											<td><c:out value="${sala.numero}"></c:out></td>
											<td><c:out value="${sala.capacidad}"></c:out></td>
											<td><c:out value="${sala.complejoAlCualPertenece.nombre}"></c:out></td>
											<td><c:out value="${sala.grupo.descripcion}"></c:out></td>
											<td><c:out value="${sala.idExterno}"></c:out></td>
											<td><c:out value="${sala.orden}"></c:out></td>
											<td>
												<button type="button" class="black has_text small img_icon" onClick="parent.location='<c:url value="admin_aeroom.htm?id_room=${sala.id}"/>'">
													<img src="assets/adminica/images/icons/small/white/pencil.png">
													<span><fmt:message key="modify" /></span>
												</button>
												<button type="button" class="red has_text small img_icon" onClick="parent.location='<c:url value="admin_deleteroom.htm?id_room=${sala.id}"/>'">
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