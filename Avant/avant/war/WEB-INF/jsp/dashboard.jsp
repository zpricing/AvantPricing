<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/dwr/interface/dwrDashboard.js"/>'></script>
	<script type="text/javascript">
		
		function alertsRefresh() {
			dwrDashboard.getAlerts({ callback:handlerAlertsRefresh, 
			errorHandler:errorHandler});
		}

		function tasksQueueRefresh() {
			dwrDashboard.getTasksQueue({ callback:handlerTasksQueueRefresh, 
			errorHandler:errorHandler})
		}

		function markAsRead(alertId) {
			dwrDashboard.markAlertAsRead(alertId, { callback:markAsReadHandler, 
			errorHandler:errorHandler});
		}

		function markAllAsRead() {
			dwrDashboard.markAllAlertsAsRead({ callback:markAsReadHandler, 
			errorHandler:errorHandler});
		}

		function markAsReadHandler() {
			alertsRefresh();
			countAlerts();
		}

		function handlerTasksQueueRefresh(data) {
			var tasks = eval("(" + data + ")");
			$("#tasks").empty();
			var count = 0;
			
			$.each(tasks.queue, function(i,propt) {
				if(propt.estado != "Finished") {
					count++;
				}
			});	

			if(count == 0) {
				$("#tasks").append("<div class=\"block\" style=\"opacity: 1;\"><div class=\"section\"><p><fmt:message key="notasksrunning" /></p></div></div>");
			}
			else {
				$("#tasks").append("<table class=\"static editable\"><thead><tr><th><fmt:message key="task_type" /></th><th><fmt:message key="state" /></th></tr></thead><tbody id=\"tasksQueueBody\"></tbody></table>");
				var tbo = document.getElementById("tasksQueueBody");
				$.each(tasks.queue, function(i,propt){
					if(propt.estado != "Finished") {
						//Columna de tipo de proceso 
						var row=document.createElement('tr');
						row.setAttribute('id',''+propt.getId);
						var cell1=document.createElement('td');
						cell1.appendChild(document.createTextNode(propt.tipoProceso))
						row.appendChild(cell1);
						
						//Comlumna estado del proceso
						cell1=document.createElement('td');
						cell1.setAttribute('class','estado');
						cell1.appendChild(document.createTextNode(propt.estado))
						row.appendChild(cell1);

						tbo.appendChild(row);
					}
				});
			}
		}

		function handlerAlertsRefresh(data) {
			var alerts = JSON.parse(data);
			$("#alerts").empty();
			if(alerts.length > 0) {
				$("#alerts").append("<ul class=\"block content_accordion\"></ul>");
			}
			else {
				$("#alerts").append("<div class=\"block\" style=\"opacity: 1;\"><div class=\"section\"><p><fmt:message key="noalerts" /></p></div></div>");
			}
			for(var i = 0; i < alerts.length; i++) {
				var myAlert = alerts[i];
				if($("#alert-" + myAlert.id).length == 0) {
					var li = "<li id=\"alert-" + myAlert.id + "\"><a href=\"#\" class=\"handle\"></a><h3 class=\"bar\">" + myAlert.title + " (" + myAlert.createdAt + ")</h3><div class=\"content\"><p class=\"section\"><pre>" + myAlert.description + "</pre></p><fieldset><div class=\"clearfix\"><button type=\"button\" class=\"green has_text small img_icon div_icon\" onclick=\"markAsRead('"+myAlert.id+"');\"><div class=\"ui-icon ui-icon-check\"></div><span><fmt:message key="dismiss" /></span></button></div></fieldset></div></li>";
					$("#alerts ul").append(li);

					$("button, .button").on('mousedown',function(){
						$(this).addClass("button_down");
					}).on('mouseup',function(){
						$(this).removeClass("button_down");
					}).on('mouseleave',function(){
						$(this).removeClass("button_down");
					});

					$( ".content_accordion" ).accordion("destroy").accordion({
						collapsible: true,
						active:false,
						header: 'h3.bar',
						autoHeight:false,
						event: 'mousedown',
						icons:false,
						animated: true
					});
				} 
			}
			if(alerts.length > 0) {
				$("#alerts").append("<div class=\"block\" style=\"opacity: 1;\"><div class=\"button_bar clearfix\"><button type=\"button\" class=\"green has_text small img_icon div_icon dialog_button\" data-dialog=\"dialog_delete\"><div class=\"ui-icon ui-icon-check\"></div><span><fmt:message key="dismissAll" /></span></button></div></div>");
			}
		}

		function errorHandler(message) {
			alert(message);
		}
		$(document).ready(function() { 
			alertsRefresh(); 
			tasksQueueRefresh();
		});
		window.setInterval(alertsRefresh, 30000);
		window.setInterval(tasksQueueRefresh, 30000);
	</script>
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="1">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2>Dashboard</h2>
				</div>

				<div class="grid_16">
					<div class="isotope_holder indented_area">
						<ul class="gallery feature_tiles clearfix isotope" style="position: relative; overflow: hidden; height: 505px; ">

							<li class="cool isotope-item" style="position: absolute; left: 639px; top: 217px; ">
								<a href="admin_funciones.htm" class="features">
										<img src="assets/adminica/images/icons/large/grey/clock.png">
										<span class="name sort_1"><fmt:message key="functions"/></span>
										<div class="starred green"></div>
								</a>
							</li>

							<li class="cool isotope-item" style="position: absolute; left: 639px; top: 217px; ">
								<a href="admin_markers.htm" class="features">
										<img src="assets/adminica/images/icons/large/grey/month_calendar.png">
										<span class="name sort_1"><fmt:message key="markers"/></span>
										<div class="starred blue"></div>
								</a>
							</li>

							<li class="cool isotope-item" style="position: absolute; left: 639px; top: 217px; ">
								<a href="admin_mascara.htm" class="features">
										<img src="assets/adminica/images/icons/large/grey/list.png">
										<span class="name sort_1"><fmt:message key="masks"/></span>
										<div class="starred green"></div>
								</a>
							</li>

							<li class="cool isotope-item" style="position: absolute; left: 639px; top: 217px; ">
								<a href="administracionProcesos.htm" class="features">
										<img src="assets/adminica/images/icons/large/grey/cog_3.png">
										<span class="name sort_1"><fmt:message key="procesos"/></span>
										<div class="starred"></div>
								</a>
							</li>

							<li class="cool isotope-item" style="position: absolute; left: 639px; top: 217px; ">
								<a href="admin_ver_restricciones_pelicula.htm" class="features">
										<img src="assets/adminica/images/icons/large/grey/locked_2.png">
										<span class="name sort_1"><fmt:message key="restricciones"/></span>
										<div class="starred blue"></div>
								</a>
							</li>

							<li class="cool isotope-item" style="position: absolute; left: 639px; top: 217px; ">
								<a href="admin_peliculasSinClasificar.htm" class="features">
										<img src="assets/adminica/images/icons/large/grey/books.png">
										<span class="name sort_1"><fmt:message key="moviesunclassified"/></span>
										<div class="starred"></div>
								</a>
							</li>

							<li class="cool isotope-item" style="position: absolute; left: 639px; top: 217px; ">
								<a href="admin_managecomplejos.htm" class="features">
										<img src="assets/adminica/images/icons/large/grey/apartment_building.png">
										<span class="name sort_1"><fmt:message key="complexs"/></span>
										<div class="starred blue"></div>
								</a>
							</li>

							<li class="cool isotope-item" style="position: absolute; left: 639px; top: 217px; ">
								<a href="admin_aeroom.htm" class="features">
										<img src="assets/adminica/images/icons/large/grey/list_w_images.png">
										<span class="name sort_1"><fmt:message key="rooms"/></span>
										<div class="starred blue"></div>
								</a>
							</li>
							
							<li class="cool isotope-item" style="position: absolute; left: 639px; top: 217px; ">
								<a href="movie_groups.htm" class="features">
										<img src="assets/adminica/images/icons/large/grey/books_2.png">
										<span class="name sort_1"><fmt:message key="moviegroups"/></span>
										<div class="starred yellow"></div>
								</a>
							</li>

						</ul>					
					</div>
				</div>

				<div class="box grid_16">
					<h2 class="box_head"><fmt:message key="alerts" /></h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<div id="alerts" class="toggle_container">
					</div>
				</div>

				<div class="box grid_8">
					<h2 class="box_head"><fmt:message key="tasks_queue" /></h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<div id="tasks" class="toggle_container">
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

	<div class="display_none">						
		<div id="dialog_delete" class="dialog_content narrow no_dialog_titlebar" title="Delete Confirmation">
			<div class="block">
				<div class="section">
					<h1><fmt:message key="dismissAll" /></h1>
					<div class="dashed_line"></div>	
					<p><fmt:message key="alert_dismiss_confirmation" /></p>
				</div>
				<div class="button_bar clearfix">
					<button type="button" class="delete_confirm dark green small no_margin_bottom close_dialog" onclick="markAllAsRead();">
						<div class="ui-icon ui-icon-check"></div>
						<span><fmt:message key="dismissAll" /></span>
					</button>
					<button class="light send_right close_dialog">
						<div class="ui-icon ui-icon-closethick"></div>
						<span><fmt:message key="cancel" /></span>
					</button>
				</div>
			</div>
		</div>
	</div> 

</body>
</html>