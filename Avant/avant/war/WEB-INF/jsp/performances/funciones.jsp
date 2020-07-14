<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="../header.jsp"/>
<body>	
	<script>
		
		function lock(funcionId) {
			$('#lock_'+funcionId).hide();
			$('#unlock_'+funcionId).show();
			$('#funcion_'+funcionId).addClass('bloqueada');
			$('#select_mascara_'+funcionId).attr('disabled', 'disabled');
			$('#funcion_bloqueada_'+funcionId).val(funcionId+":true");
		}
		
		function unlock(funcionId) {
			$('#unlock_'+funcionId).hide();
			$('#lock_'+funcionId).show();
			$('#funcion_'+funcionId).removeClass('bloqueada');
			$('#select_mascara_'+funcionId).removeAttr('disabled');
			$('#funcion_bloqueada_'+funcionId).val(funcionId+":false");
		}

		function submitGuardar() {
			document.form_funciones.action = "admin_guardarMascarasFunciones.htm";
			$("select[name=\"mascaras\"]").each(function() {
				$(this).removeAttr('disabled');
			});
			document.form_funciones.submit();
		}

		$(function() {
			date = $( ".datepicker" ).val();
			$( ".datepicker" ).datepicker("option", "dateFormat", "dd-mm-yy");
			$( ".datepicker" ).val(date);
		});

		$(document).ready(function() {

			$("form#form_funciones").find("div[id^='funcion_']").each(function(){
				id = $(this).attr("id");
				funcionId = id.split("_")[1];
				if($("#funcion_bloqueada_"+funcionId).val().indexOf("true") > 0) {
					lock(funcionId);
				}
			});

			var todas_seleccionadas = false;
			$("#seleccionar_todas").click(function(){
				if(todas_seleccionadas) {
					$("form#form_funciones").find("input[type=checkbox]").each(function(){
						$(this).removeAttr("checked");
					});
					todas_seleccionadas = false;
				}
				else {
					$("form#form_funciones").find("input[type=checkbox]").each(function(){
						$(this).attr("checked","checked");
					});
					todas_seleccionadas = true;
				}
			});

			$(".seleccionar_sala").click(function(){
				var checked = "";
				if ($(this).attr("checked")) {
					checked = "checked";
				}

				$(this).parent().parent().parent().find("input[type=checkbox]").each(function(){
					if (checked == "") {
						$(this).removeAttr("checked");
					}
					else {
						$(this).attr("checked", checked);
					}

				});
			});

			$("#cambiar_mascaras").click(function() {
				var mascara = $("#mascaraGlobal").val();

				$("select[name=\"mascaras\"]").each(function() {
					if($(this).parent().parent().parent().find("input[name=funciones]").attr("checked") &&
					$(this).parent().parent().find("input[name=bloqueadas]").val().indexOf("false") > 0) {

						$(this).find("option").each(function() {
							if($(this).text() == mascara) {
								$(this).attr("selected", "selected");
								$(this).parent().change();
							}
						});

					}
				});
			});

			$("#bloquear_sel").click(function(){
				$("form#form_funciones").find("div[id^='funcion_']").each(function(){
					id = $(this).attr("id");
					funcionId = id.split("_")[1];
					if($(this).find("input[name=funciones]").attr("checked")) {
						lock(funcionId);
					}
				});
			});
			
			$("#desbloquear_sel").click(function(){
				$("form#form_funciones").find("div[id^='funcion_']").each(function(){
					id = $(this).attr("id");
					funcionId = id.split("_")[1];
					if($(this).find("input[name=funciones]").attr("checked")) {
						unlock(funcionId);
					}
				});
			});

		});

	</script>
	<div id="pjax">
		<div id="wrapper" data-adminica-side-top="3" data-adminica-side-inner="1">
			
			<jsp:include page="/sidebar.htm"/>

			<div id="main_container" class="main_container container_16 clearfix">
				
				<div class="flat_area grid_16">
					<h2><fmt:message key="functions" /></h2>
				</div>

				<div class="grid_16 box clearfix no_titlebar" style="opacity: 1; ">
					<div class="indented_button_bar clearfix">
					</div>
				</div>

				<div class="box grid_16">
					<h2 class="box_head"><fmt:message key="functions" />: <c:out value='${complejo.nombre}'/> (<fmt:formatDate value="${fecha_actual}" type="both" pattern="EEEE dd-MM-yyyy" />)
</h2>
					<div class="controls">
						<a href="#" class="grabber"></a>
						<a href="#" class="toggle"></a>
					</div>
					<div class="block">
						<div class="section">
							<div class="contenedor_salas_complejo">
								<form method="POST" id="form_funciones" name="form_funciones" action="">
									<input type="hidden" name="idComplejo" value="<c:out value='${complejoId}'/>" />
									<input type="hidden" name="fecha" value="<c:out value='${fechaFormateada}' />" />
									<input type="hidden" name="cargar" id="cargar" value="0" />
									<input type="hidden" name="accion"/>
									<c:forEach items="${salas}" var="s">
										<div class="contenedor_sala" id="sala_<c:out value='${s.id}'/>">
											<div class="sala">
												<span style="font-size: 90%">
													<input type="checkbox" class="seleccionar_sala"/>
													<c:out value="${s.numero}"/> (<c:out value="${s.capacidad}"/>)
												</span>
											</div>
											<c:forEach items="${funciones}" var="f">
												<c:if test="${s.id == f.sala.id}">
													<div id="funcion_${f.id}" class="funcion sala_<c:out value='${s.id}'/> <c:if test="${f.mascara.id != null && f.mascara.id != 0}">tieneMascara</c:if>">
														<div>
															<span>
																<input type="checkbox" id="funcion${f.id}" value="${f.id }" name="funciones"/> 
																<span class="hora"><fmt:formatDate value="${f.fecha}" type="both" pattern="HH:mm"/></span>
															</span>
															<span style="float:right;">
																<div id="lock_<c:out value='${f.id}' />">
																	<a href="javascript:lock(<c:out value='${f.id}' />);">
																		<img title="bloquear" src="images/unlock_icon.png" border="0">
																	</a>
																</div>
																<div id="unlock_<c:out value='${f.id}' />" style="display:none;">
																	<a href="javascript:unlock(<c:out value='${f.id}' />);">
																		<img title="desbloquear" src="images/lock_icon.png" border="0">
																	</a>
																</div>
																<!-- 
																	<a href="<c:url value="asistenciaporpelicula.htm?peliculaId=${f.peliculaAsociada.id}"/>" rel="superbox[iframe][700x500]">
																		<img title="<fmt:message key='verGraficoAsistencia' />" src="images/chart_curve.png" border="0">
																	</a>
																-->
															</span>			
														</div>
														<div>
															<span title="<c:out value='${f.peliculaAsociada.nombre}' />" style="font-size: 90%"><b><c:out value="${fn:substring(f.peliculaAsociada.nombre,0,14)}" /></b></span>
														</div>
														<div>
															<c:set var="ac" value="${0}"></c:set>
															<c:forEach items="${f.asistenciasDeFuncion}" var="a">
																<c:set var="ac" value="${ac+a.asistencia}"></c:set>
															</c:forEach>
															<span style="font-size: 90%">
																<fmt:message key="attendance" />:&nbsp;<b><c:out value="${ac}"></c:out></b>
																<fmt:message key="forecastAttendance" />:&nbsp;<b><c:out value="${f.asistenciaProyectada}"></c:out></b>
																<fmt:message key="forecastOccupation" />:&nbsp;<b><fmt:formatNumber value="${f.porcentajeOcupacionProyectado}" maxFractionDigits="0" />%</b>
															</span><br/>
														</div>
														<div >
															<span>
																<fmt:message key="mask" />: 
																<select name="mascaras" id="select_mascara_<c:out value='${f.id}' />" class="select_mascara" style="font-size: x-small;" <c:if test="${f.protegido}">disabled</c:if>">
																	<option value="<c:out value="${f.id}"/>:0">S/M</option>
																	<c:forEach items="${s.mascaras}" var="mascara">
																		<c:choose>
																			<c:when test="${f.mascara.id == mascara.id}">
																				<option value="<c:out value="${f.id}"/>:<c:out value="${mascara.id}"/>" selected><c:out value="${mascara.descripcion}"/></option>
																			</c:when>
																			<c:otherwise>
																				<option value="<c:out value="${f.id}"/>:<c:out value="${mascara.id}"/>"><c:out value="${mascara.descripcion}"/></option>
																			</c:otherwise>
																		</c:choose>
																	</c:forEach>
																</select>
															</span>
															<input type="hidden" name="bloqueadas" id="funcion_bloqueada_<c:out value='${f.id}' />" value="<c:out value='${f.id}' />:<c:out value='${f.bloqueada}' />" />
														</div>
													</div>
												</c:if>
											</c:forEach>
										</div>
									</c:forEach>
								</form>
							</div>
						</div>
						
						<div class="button_bar clearfix">
							<button class="green img_icon has_text" onClick="submitGuardar()">
								<img src="assets/adminica/images/icons/small/white/create_write.png">
								<span><fmt:message key="save"/></span>
							</button>
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

	<div id="funciones_options" class="clearfix">
		<form:form method="post" commandName="funciones" cssClass="cmxform" action="admin_funciones.htm">

		<!--<div class="grid_16 box clearfix no_titlebar compressed" style="opacity: 1; ">-->
			<div class="container_16">

				<div class="box grid_2 no_titlebar compressed" style="opacity: 1; ">
					<div class="block" style="opacity: 1; ">
						<fieldset class="label_top top">
							<label><fmt:message key="date" /></label>
							<div class="clearfix">
								<form:input path="fecha" cssClass="datepicker" cssStyle="width:100px;"/>
							</div>
						</fieldset>
					</div>
				</div>

				<div class="box grid_5 no_titlebar compressed" style="opacity: 1; ">
					<div class="block" style="opacity: 1; ">
						<fieldset class="label_top top">
							<label><fmt:message key="complex" /></label>
							<div class="clearfix">
								<form:select path="idComplejo" id="sel" cssStyle="margin-bottom: 0px; margin-top: 8px;">
									<c:forEach items="${complejos}" var="c">
										<form:option value="${c.id}">
											<c:out value="${c.nombre}"></c:out>
										</form:option>
									</c:forEach>
								</form:select>
								<button class="black small img_icon has_text" style="float: right;" onClick="document.funciones.submit()">
									<img src="assets/adminica/images/icons/small/white/bended_arrow_down.png">
									<span><fmt:message key="unhide" /></span>
								</button>
							</div>
						</fieldset>
					</div>
				</div>

				<div class="box grid_3 no_titlebar compressed" style="opacity: 1; ">
					<div class="block" style="opacity: 1; ">
						<fieldset class="label_top top">
							<label><fmt:message key="masks" /></label>
							<div class="clearfix">
								<div class="clearfix">
									<select id="mascaraGlobal" name="mascaraGlobal" style="margin-bottom: 0px;">
										<c:forEach items="${mascaras}" var="mascara">
											<option value="${mascara.descripcion}">${mascara.descripcion}</option>
										</c:forEach>
									</select>
									<button id="cambiar_mascaras" class="black small img_icon has_text" style="float: right;">
										<img src="assets/adminica/images/icons/small/white/cog_3.png">
										<span><fmt:message key="apply" /></span>
									</button>
								</div>

							</div>
						</fieldset>
					</div>
				</div>

				<div class="box grid_6 no_titlebar compressed" style="opacity: 1; ">
					<div class="block" style="opacity: 1; ">
						<fieldset class="label_top top">
							<label><fmt:message key="functions" /></label>
							<div class="uniform clearfix">
								<button id="desbloquear_sel" class="black small img_icon has_text" style="float: right; margin-top: 2px;">
									<img src="assets/adminica/images/icons/small/white/unlocked.png">
									<span><fmt:message key="unlock" /></span>
								</button>
								<button id="bloquear_sel" class="black small img_icon has_text" style="float: right;">
									<img src="assets/adminica/images/icons/small/white/locked_2.png">
									<span><fmt:message key="lock" /></span>
								</button>
								<label for="seleccionar_todas"><input type="checkbox" name="seleccionar_todas" id="seleccionar_todas"/><fmt:message key="select" /> <fmt:message key="all2" /></label>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>