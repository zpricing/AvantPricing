<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="../header.jsp"/>
		
<body>	
	<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/dwr/interface/dwrProcesos.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/assets/zpcinemas/JavaScript/adminProcesos.js"/>'></script>

	<!--<script type='text/javascript' src='assets/jquery/jquery-1.6.2.min.js'/>-->

	<script type='text/javascript' src='assets/noty/jquery.noty.js'></script>
	<script type="text/javascript" src="assets/noty/layouts/top.js"></script>
	<script type="text/javascript" src="assets/noty/layouts/topLeft.js"></script>
	<script type="text/javascript" src="assets/noty/layouts/topRight.js"></script>

	<script type="text/javascript" src="assets/noty/themes/default.js"></script>

	<script>
		$(document).ready(function() {
			<c:forEach items="${procesos}" var="proceso">
			agregarProceso('<fmt:message key="${proceso.codigo}" />', '<c:out value="${proceso.codigo}"/>', '<c:out value="${proceso.type}"/>');
			</c:forEach>
			
			pagina_refresh();

			$('#limpiarCola').click(limpiarCola);
			$('#todosErrores').click(erroresRefresh);
			$('#headerCola').css({"height": '40px'});
		});
	</script>
	<link href="assets/zpcinemas/css/adminProcesos.css" rel="stylesheet" type="text/css">
	
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="3" data-adminica-side-inner="4">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="tasks_manager" /></h2>
				</div>

				<div class="box grid_16 tabs ui-tabs ui-widget ui-widget-content ui-corner-all">
					<ul class="tab_header clearfix ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
						<li class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active">
							<a href="#procesos-tab-1"><fmt:message key="system_tasks" /></a>
						</li>
						<li class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active">
							<a href="#procesos-tab-2"><fmt:message key="data_extraction_tasks" /></a>
						</li>
						<li class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active">
							<a href="#procesos-tab-3"><fmt:message key="business_intelligence_tasks" /></a>
						</li>
						<li class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active">
							<a href="#procesos-tab-4"><fmt:message key="data_load_tasks" /></a>
						</li>
					</ul>

					<h2 class="box_head"><fmt:message key="tasks_manager" /></h2>					
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<div class="toggle_container">
						<div id="procesos-tab-1" class="block ui-tabs-panel ui-widget-content ui-corner-bottom" style="opacity: 1;">
							<table class="static editable">
								<thead>
									<tr>
										<th><fmt:message key="name" /></th>
										<th><fmt:message key="state" /></th>
										<th><fmt:message key="stage" /></th>
										<th><fmt:message key="last_execution" /></th>
										<th><fmt:message key="last_successful_execution" /></th>
										<th><fmt:message key="execution_time" /></th>
										<th><fmt:message key="add_to_queue" /></th>
										<th><fmt:message key="enable_button" /></th>
										<th><fmt:message key="error" /></th>								
									</tr>
								</thead>
								<tbody id="listaDeProcesos_system">
								</tbody>
							</table>
						</div>
						<div id="procesos-tab-2" class="block ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide">
							<table class="static editable">
								<thead>
									<tr>
										<th><fmt:message key="name" /></th>
										<th><fmt:message key="state" /></th>
										<th><fmt:message key="stage" /></th>
										<th><fmt:message key="last_execution" /></th>
										<th><fmt:message key="last_successful_execution" /></th>
										<th><fmt:message key="execution_time" /></th>
										<th><fmt:message key="add_to_queue" /></th>
										<th><fmt:message key="enable_button" /></th>
										<th><fmt:message key="error" /></th>								
									</tr>
								</thead>
								<tbody id="listaDeProcesos_data_extraction">
								</tbody>
							</table>
						</div>

						<div id="procesos-tab-3" class="block ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide">
							<table class="static editable">
								<thead>
									<tr>
										<th><fmt:message key="name" /></th>
										<th><fmt:message key="state" /></th>
										<th><fmt:message key="stage" /></th>
										<th><fmt:message key="last_execution" /></th>
										<th><fmt:message key="last_successful_execution" /></th>
										<th><fmt:message key="execution_time" /></th>
										<th><fmt:message key="add_to_queue" /></th>
										<th><fmt:message key="enable_button" /></th>
										<th><fmt:message key="error" /></th>								
									</tr>
								</thead>
								<tbody id="listaDeProcesos_business_intelligence">
								</tbody>
							</table>
						</div>

						<div id="procesos-tab-4" class="block ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide">
							<table class="static editable">
								<thead>
									<tr>
										<th><fmt:message key="name" /></th>
										<th><fmt:message key="state" /></th>
										<th><fmt:message key="stage" /></th>
										<th><fmt:message key="last_execution" /></th>
										<th><fmt:message key="last_successful_execution" /></th>
										<th><fmt:message key="execution_time" /></th>
										<th><fmt:message key="add_to_queue" /></th>
										<th><fmt:message key="enable_button" /></th>
										<th><fmt:message key="error" /></th>								
									</tr>
								</thead>
								<tbody id="listaDeProcesos_data_load">
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="box grid_16">
					<div class="block" style="opacity: 1;">
						
						<h2 class="box_head" id="headerCola"><fmt:message key="tasks_queue" />
							<button type="button" id="limpiarCola">
								<img src="assets/adminica/images/icons/small/white/refresh_3.png">
							</button>
						</h2>					
						<div class="controls">
							<a href="#" class="grabber"></a>
							<a href="#" class="toggle"></a>
						</div>
							<div class="toggle_container">
								<div class="block" style="opacity: 1;">
									<table class="static editable" id="tablaDeCola">
										<thead>
											<tr>
												<th><fmt:message key="task_type" /></th>
												<th><fmt:message key="state" /></th>
												<th><fmt:message key="id" /></th>
												<th><fmt:message key="cancel_remove_from_queue" /></th>				
											</tr>
										</thead>
										<tbody id="cuerpoTabla">
										</tbody>
									</table>
								</div>
							</div>
						</div>
							<div class="block" style="opacity: 1;">						
								<h2 class="box_head" id="headerCola"><fmt:message key="errors" />
									<button type="button" id="todosErrores">
										<img src="assets/adminica/images/icons/small/white/refresh_3.png">
									</button>
								</h2>					
								<div class="controls">
									<a href="#" class="grabber"></a>
									<a href="#" class="toggle"></a>
								</div>
									<div class="toggle_container">
										<div class="block" style="opacity: 1;">
											<table class="static editable" id="dt1">
												<thead>
													<tr>
														<th><fmt:message key="task" /></th>
														<th><fmt:message key="time" /></th>
														<th><fmt:message key="error" /></th>
													</tr>
												</thead>
												<tbody id="listaErrores">									
												</tbody>
											</table>
												<ul class="content_accordion ui-accordion ui-widget ui-helper-reset ui-sortable" role="tablist">
													<li class="ui-accordion-li-fix">
														<a href="#" class="handle"></a>
														<h3 class="bar ui-accordion-header ui-helper-reset ui-state-default ui-corner-all" role="tab" aria-expanded="true" aria-selected="true" tabindex="0"><fmt:message key="error" /></h3>
														<div class="content ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom" role="tabpanel" style="display: none;">
															<p id="containerError" class="section"></p>
														</div>											
													</li>		
												</ul> 
										</div>
									</div>
								</div>							
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