<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>	
	<script type='text/javascript'>

		var marker_types = {
			<c:forEach var="tipoMarker" items="${tipoMarkers}">
				<c:out value="${tipoMarker.id}"/> : '<c:out value="${tipoMarker.descripcion}"/>',
			</c:forEach>
		}

		var complejos = {
			<c:forEach var="complejo" items="${complejos}">
				<c:out value="${complejo.id}"/> : '<c:out value="${complejo.nombre}"/>',
			</c:forEach>
		}

		var markers = [
			<c:forEach var="marker" items="${markers}" varStatus="i">
				{
					id: <c:out value="${marker.id}"/>,
					title: "<c:out value="${marker.tipoMarker.descripcion}"/>",
					type: "<c:out value="${marker.tipoMarker.id}"/>",
					complejos: [
								<c:forEach var="complejo" items="${markers[i.index].complejos}">
									"<c:out value="${complejo.id}"/>",
								</c:forEach>
								],
					start: "<fmt:formatDate value="${marker.fecha}" pattern="yyyy-MM-dd HH:mm:ss" />",
					end: "<fmt:formatDate value="${marker.fechaHasta}" pattern="yyyy-MM-dd HH:mm:ss" />",
					className: 'calendar_black',
					allDay: false
				},
			</c:forEach>
		];

		$(document).ready(function() {
				
			/* Create Selects
			-----------------------------------------------------------------*/
			
			var select_create = $('#tipo_marker_create');
			var select_edit = $('#tipo_marker_edit');
			if(select_create.prop) {
			  var options_create = select_create.prop('options');
			}
			else {
			  var options_create = select_create.attr('options');
			}
			if(select_edit.prop) {
			  var options_edit = select_edit.prop('options');
			}
			else {
			  var options_edit = select_edit.attr('options');
			}
			$('option', select_create).remove();
			$('option', select_edit).remove();

			$.each(marker_types, function(val, text) {
			    options_create[options_create.length] = new Option(text, val);
			    options_edit[options_edit.length] = new Option(text, val);
			});
			
			$.each(complejos, function(val, text) {
			    complejos_create[complejos_create.length] = new Option(text, val);
			    complejos_edit[complejos_edit.length] = new Option(text, val);
			});

			$( "#complejos_create" ).multiselect( 'destroy' );
			$( "#complejos_create" ).multiselect();
			
			/* initialize the calendar
			-----------------------------------------------------------------*/
			
			$('#calendar').fullCalendar({
				events : markers,
				firstDay:'1',
	    		weekMode:'liquid',
	    		aspectRatio: '1.5',
				theme:true,
				selectable:true,
				editable:true,
				draggable:true,
				droppable:true,
				timeFormat:'H:mm',
		    	axisFormat:'H:mm',
		    	columnFormat:{
				    month: 'ddd',    // Mon
				    week: 'ddd dS', // Mon 9/7
				    day: 'dddd dS MMMM'  // Monday 9/7
				},
				titleFormat:{
				    month: 'MMMM yyyy',                             // September 2009
				    week: "MMM d[ yyyy]{ 'to'[ MMM] d, yyyy}", // Sep 7 - 13 2009
				    day: 'ddd, MMMM d, yyyy'                  // Tuesday, Sep 8, 2009
				},
		    	allDayText:'All Day',
				header:{
				    left:   'prev title next, today',
				    center: '',
				    right:  'agendaWeek,agendaDay,month'
					},
				allDaySlot : false,
				editable: true,
				droppable: true, // this allows things to be dropped onto the calendar !!!
				drop: function(date, allDay) { // this function is called when something is dropped
				
					// retrieve the dropped element's stored Event Object
					var originalEventObject = $(this).data('eventObject');
					
					// we need to copy it, so that multiple events don't have a reference to the same object
					var copiedEventObject = $.extend({}, originalEventObject);
					
					// assign it the date that was reported
					copiedEventObject.start = date;
					copiedEventObject.allDay = false;
					copiedEventObject.id = new Date().getTime();

					if(allDay) {
						dateEnd = new Date();
						dateEnd.setTime(copiedEventObject.start.getTime());
						dateEnd.setDate(dateEnd.getDate() + 1);
						copiedEventObject.end = dateEnd;
					}
					
					// render the event on the calendar
					// the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
					$('#calendar').fullCalendar('renderEvent', copiedEventObject, true);
					
				},
				dayClick: function(date, allDay, jsEvent, view) {
					
					$('#calendar').fullCalendar( 'gotoDate', date );
			        $('#calendar').fullCalendar( 'changeView', 'agendaDay' );
			
				},
				eventClick: function(calEvent, jsEvent, view) {
					dateStart = new Date();
					dateEnd = new Date();
					dateStart.setTime(calEvent.start.getTime());
					if(calEvent.allDay) {
						dateEnd.setTime(dateStart.getTime());
						dateEnd.setDate(dateEnd.getDate() + 1);
					}
					else {
						dateEnd.setTime(calEvent.end.getTime());
					}
					$("#fecha_inicio_edit").val(
						("0" + (dateStart.getMonth() + 1)).substr(-2,2) + "/" + 
						("0" + dateStart.getDate()).substr(-2,2) + "/" + 
						dateStart.getFullYear() + " " + 
						("0" + dateStart.getHours()).substr(-2,2) + ":" + ("0" + dateStart.getMinutes()).substr(-2,2)
					);
					$("#fecha_fin_edit").val(
						("0" + (dateEnd.getMonth() + 1)).substr(-2,2) + "/" + 
						("0" + dateEnd.getDate()).substr(-2,2) + "/" + 
						dateEnd.getFullYear() + " " + 
						("0" + dateEnd.getHours()).substr(-2,2) + ":" + ("0" + dateEnd.getMinutes()).substr(-2,2)
					);

					$("#marker_edit_id").val(calEvent.id);
					
					$("#tipo_marker_edit option").filter(function() {
					    return $(this).text() == calEvent.title; 
					}).attr('selected', true);
					$.uniform.update("#tipo_marker_edit");
					
					$( "#complejos_edit" ).empty();

					$.each(complejos, function(val, text) {
					    complejos_edit[complejos_edit.length] = new Option(text, val);
					});

					$("#complejos_edit option").each(function() { 
						this.selected = ($.inArray(this.value, calEvent.complejos) > -1)
					});

					$( "#complejos_edit" ).multiselect( 'destroy' );
					$( "#complejos_edit" ).multiselect();
					
					$( ".alert" ).removeClass( "alert_red" );
					$( ".alert" ).addClass( "alert_black" );
					$( ".validateTips" ).text( "<fmt:message key="all_fields_are_mandatory"/>." );
					$( "#dialog-edit" ).dialog( "open" );
				}
			});
		});

		function addMarker() {
			dateStart = new Date($("#fecha_inicio_create").val());
			dateEnd = new Date($("#fecha_fin_create").val());
			complejosSel = $("#complejos_create").val();
			var eventObject = {
					id: new Date().getTime(),
					type: $("#tipo_marker_create option:selected").val(),
					title: $("#tipo_marker_create option:selected").text(),
					complejos: complejosSel,
					start: dateStart,
					end: dateEnd,
					className: 'calendar_black',
					allDay: false
			};
			$('#calendar').fullCalendar('renderEvent', eventObject, true);
		}

		function editMarker() {
			dateStart = new Date($("#fecha_inicio_edit").val());
			dateEnd = new Date($("#fecha_fin_edit").val());
			complejosSel = $("#complejos_edit").val();
			marker_id = $("#marker_edit_id").val();
			calEvents = $('#calendar').fullCalendar('clientEvents', marker_id);
			$.each(calEvents, function() {
				this.type = $("#tipo_marker_edit option:selected").val();
				this.title = $("#tipo_marker_edit option:selected").text();
				this.complejos = complejosSel;
				this.start = dateStart;
				this.end = dateEnd;
				this.allDay = false;
				$('#calendar').fullCalendar('updateEvent', this);
			});
			var eventObject = {
					title: $("#tipo_marker_edit option:selected").text(),
					start : dateStart,
					end: dateEnd,
					allDay: false
			};
		}

		function deleteMarker() {
			marker_id = $("#marker_edit_id").val();
			$('#calendar').fullCalendar('removeEvents', marker_id);
		}

		function updateTips( t ) {
			$( ".validateTips" ).text( t );
			$( ".alert" ).removeClass( "alert_black" );
			$( ".alert" ).addClass( "alert_red" );
		}

		function validate( mode ) {
			if ($("#complejos_" + mode ).val() == null) {
				updateTips("<fmt:message key="select_theaters"/>.");
				return false;
			}
			if ($("#fecha_inicio_" + mode ).val() == "") {
				updateTips("<fmt:message key="select_start_date"/>.");
				return false;
			}
			if ($("#fecha_fin_" + mode ).val() == "") {
				updateTips("<fmt:message key="select_end_date"/>.");
				return false;
			}
			if ($("#fecha_fin_" + mode ).val() == $("#fecha_inicio_" + mode ).val()) {
				updateTips("<fmt:message key="selenct_end_date_diff_start_date"/>.");
				return false;
			}
			updateTips("<fmt:message key="all_fields_are_mandatory"/>.");
			return true;
		}

		$(function() {
			
			$("#eliminar_marker_confirm").click(function(){
				$(".dialog_content").dialog( "close" ); 
				$("#dialog-confirm").dialog( "open" ); 
			});

			$("#eliminar_marker").click(function(){
				deleteMarker();
				$(".dialog_content").dialog( "close" ); 
			});

			$("#editar_marker").click(function(){
				if (validate("edit")) { 
					editMarker();
					$(".dialog_content").dialog( "close" ); 
				}
			});

			$("#crear_marker").click(function(){
				if (validate("create")) { 
					addMarker();
					$(".dialog_content").dialog( "close" ); 
				}
			});

			$('#guardar').click(function(){
					markersInCalendar = $('#calendar').fullCalendar('clientEvents');
					markersToSave = [];
					$.each(markersInCalendar, function(index, marker) { 
						dateStart = marker.start.getFullYear() + "-" + ("0" + (marker.start.getMonth() + 1)).substr(-2,2) + "-" + ("0" + marker.start.getDate()).substr(-2,2) + " " + 
									("0" + marker.start.getHours()).substr(-2,2) + ":" + ("0" + marker.start.getMinutes()).substr(-2,2) + ":00";
						dateEnd = marker.end.getFullYear() + "-" + ("0" + (marker.end.getMonth() + 1)).substr(-2,2) + "-" + ("0" + marker.end.getDate()).substr(-2,2) + " " + 
									("0" + marker.end.getHours()).substr(-2,2) + ":" + ("0" + marker.end.getMinutes()).substr(-2,2) + ":00";
						
						markerData = marker.type + "#" + marker.complejos.join(",") + "#" + dateStart + "#" + dateEnd;
						markersToSave.push(markerData);
					});
					markersSerialized = markersToSave.join("@");
					$( "#markers" ).val(markersSerialized);
					$( "#admin_markers" ).submit();
				});

			$('#fecha_inicio_edit').datetimepicker({
			    hourGrid: 4,
				minuteGrid: 10,
			    onClose: function(dateText, inst) {
			        var endDateTextBox = $('#fecha_fin_edit');
			        if (endDateTextBox.val() != '') {
			            var testStartDate = new Date(dateText);
			            var testEndDate = new Date(endDateTextBox.val());
			            if (testStartDate > testEndDate)
			                endDateTextBox.val(dateText);
			        }
			        else {
			            endDateTextBox.val(dateText);
			        }
			    },
			    onSelect: function (selectedDateTime){
			        var start = $(this).datetimepicker('getDate');
			        $('#fecha_fin_edit').datetimepicker('option', 'minDate', new Date(start.getTime()));
			    }
			});
			
			$('#fecha_fin_edit').datetimepicker({
				hourGrid: 4,
				minuteGrid: 10,
			    onClose: function(dateText, inst) {
			        var startDateTextBox = $('#fecha_inicio_edit');
			        if (startDateTextBox.val() != '') {
			            var testStartDate = new Date(startDateTextBox.val());
			            var testEndDate = new Date(dateText);
			            if (testStartDate > testEndDate)
			                startDateTextBox.val(dateText);
			        }
			        else {
			            startDateTextBox.val(dateText);
			        }
			    },
			    onSelect: function (selectedDateTime){
			        var end = $(this).datetimepicker('getDate');
			        $('#fecha_inicio_edit').datetimepicker('option', 'maxDate', new Date(end.getTime()) );
			    }
			});

			$('#fecha_inicio_create').datetimepicker({
			    hourGrid: 4,
				minuteGrid: 10,
			    onClose: function(dateText, inst) {
			        var endDateTextBox = $('#fecha_fin_create');
			        if (endDateTextBox.val() != '') {
			            var testStartDate = new Date(dateText);
			            var testEndDate = new Date(endDateTextBox.val());
			            if (testStartDate > testEndDate)
			                endDateTextBox.val(dateText);
			        }
			        else {
			            endDateTextBox.val(dateText);
			        }
			    },
			    onSelect: function (selectedDateTime){
			        var start = $(this).datetimepicker('getDate');
			        $('#fecha_fin_create').datetimepicker('option', 'minDate', new Date(start.getTime()));
			    }
			});
			
			$('#fecha_fin_create').datetimepicker({
				hourGrid: 4,
				minuteGrid: 10,
			    onClose: function(dateText, inst) {
			        var startDateTextBox = $('#fecha_inicio_create');
			        if (startDateTextBox.val() != '') {
			            var testStartDate = new Date(startDateTextBox.val());
			            var testEndDate = new Date(dateText);
			            if (testStartDate > testEndDate)
			                startDateTextBox.val(dateText);
			        }
			        else {
			            startDateTextBox.val(dateText);
			        }
			    },
			    onSelect: function (selectedDateTime){
			        var end = $(this).datetimepicker('getDate');
			        $('#fecha_inicio_create').datetimepicker('option', 'maxDate', new Date(end.getTime()) );
			    }
			});
		});
	</script>
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="3" data-adminica-side-inner="2">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><fmt:message key="markers"/></h2>
				</div>

				<div class="box grid_13">
					<h2 class="box_head"><fmt:message key="markers"/></h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<div class="toggle_container">
						<div class="block">
							<div class="section">
								<div id="calendar"></div>
							</div>
						</div>
					</div>
				</div>

				<div class="box grid_3">
					<h2 class="box_head"><fmt:message key="actions"/></h2>
					<div class="section clearfix stacked_buttons" style="padding-bottom:10px;">
						
						<button class="black full_width dialog_button" data-dialog="dialog-create">
							<div class="ui-icon ui-icon-plus"></div>
							<span><fmt:message key="add"/> <fmt:message key="marker"/></span>
						</button>

						<button id="guardar" class="dark green full_width">
							<div class="ui-icon ui-icon-check"></div>
							<span><fmt:message key="save"/></span>
						</button>
					</div>
				</div>

			</div> <!-- #main_container -->
		</div> <!-- wrapper -->

		<div class="display_none">
			<div id="dialog-edit" class="dialog_content"  title="Editar Marker">
				<input type="hidden" id="marker_edit_id">
				<div class="block">
					<div class="section">
						<div class="alert alert_black">
							<img width="24" height="24" src="assets/adminica/images/icons/small/white/alert_2.png">
							<span class="validateTips"><fmt:message key="all_fields_are_mandatory"/>.</span>
						</div>
					</div>
					<fieldset class="label_side top">
						<label><fmt:message key="marker_type"/></label>
						<div class="clearfix">
							<select class="uniform full_width" name="tipo_marker_edit" id="tipo_marker_edit">
							</select>
						</div>
					</fieldset>
					<fieldset class="label_side">
						<label><fmt:message key="complex"/></label>
						<div class="clearfix">
							<select id="complejos_edit" name="complejos_edit" class="multisorter indent" multiple="multiple" style="height:230px;">
							</select>
						</div>
					</fieldset>
					<fieldset class="label_side">
						<label><fmt:message key="start_date"/></label>
						<div class="clearfix">
							<input type="text" name="fecha_inicio_edit" id="fecha_inicio_edit"/>
						</div>
					</fieldset>
					<fieldset class="label_side">
						<label><fmt:message key="end_date"/></label>
						<div class="clearfix">
							<input type="text" name="fecha_fin_edit" id="fecha_fin_edit"/>
						</div>
					</fieldset>
					<div class="button_bar clearfix">
						<button id="eliminar_marker_confirm" class="dark red">
							<div class="ui-icon ui-icon-trash"></div>
							<span><fmt:message key="delete"/> <fmt:message key="marker"/></span>
						</button>
						<button id="editar_marker" class="dark green">
							<div class="ui-icon ui-icon-check"></div>
							<span><fmt:message key="edit"/> <fmt:message key="marker"/></span>
						</button>
						<button class="light send_right close_dialog">
							<div class="ui-icon ui-icon-closethick"></div>
							<span><fmt:message key="cancel"/></span>
						</button>
					</div>
				</div>
			</div>
		</div>

		<div class="display_none">
			<div id="dialog-create" class="dialog_content"  title="Agregar Marker">
				<div class="block">
					<div class="section">
						<div class="alert alert_black">
							<img width="24" height="24" src="assets/adminica/images/icons/small/white/alert_2.png">
							<span class="validateTips"><fmt:message key="all_fields_are_mandatory"/>.</span>
						</div>
					</div>
					<fieldset class="label_side top">
						<label><fmt:message key="marker_type"/></label>
						<div class="clearfix">
							<select class="uniform full_width" name="tipo_marker_create" id="tipo_marker_create">
							</select>
						</div>
					</fieldset>
					<fieldset class="label_side">
						<label><fmt:message key="complexs"/></label>
						<div class="clearfix">
							<select id="complejos_create" name="complejos_create" class="multisorter indent" multiple="multiple" style="height:230px;">
							</select>
						</div>
					</fieldset>
					<fieldset class="label_side">
						<label><fmt:message key="start_date"/></label>
						<div class="clearfix">
							<input type="text" name="fecha_inicio_create" id="fecha_inicio_create"/>
						</div>
					</fieldset>
					<fieldset class="label_side">
						<label><fmt:message key="end_date"/></label>
						<div class="clearfix">
							<input type="text" name="fecha_fin_create" id="fecha_fin_create"/>
						</div>
					</fieldset>
					<div class="button_bar clearfix">
						<button id="crear_marker" class="dark green">
							<div class="ui-icon ui-icon-check"></div>
							<span><fmt:message key="create"/> <fmt:message key="marker"/></span>
						</button>
						<button class="light send_right close_dialog">
							<div class="ui-icon ui-icon-closethick"></div>
							<span><fmt:message key="cancel"/></span>
						</button>
					</div>
				</div>
			</div>
		</div>

		<div class="display_none">						
			<div id="dialog-confirm" class="dialog_content narrow no_dialog_titlebar" title="Eliminar Marker">
				<div class="block">
					<div class="section">
						<h1><fmt:message key="delete"/> <fmt:message key="marker"/></h1>
						<div class="dashed_line"></div>	
						<p><fmt:message key="marker_elimination_confirmation"/></p>
					</div>
					<div class="button_bar clearfix">
						<button id="eliminar_marker" class="delete_confirm dark red no_margin_bottom">
							<div class="ui-icon ui-icon-check"></div>
							<span><fmt:message key="delete"/> <fmt:message key="marker"/></span>
						</button>
						<button class="light send_right close_dialog">
							<div class="ui-icon ui-icon-closethick"></div>
							<span><fmt:message key="cancel"/></span>
						</button>
					</div>
				</div>
			</div>
		</div> 

	</div> <!-- #pjax -->		

	<div id="loading_overlay">
		<div class="loading_message round_bottom">
			<img src="assets/adminica/images/interface/loading.gif" alt="loading" />
		</div>
	</div>
	
	<form id="admin_markers" action="admin_markers.htm" method="post">
		<input type="hidden" name="markers" id="markers" value="">
	</form>

</body>
</html>