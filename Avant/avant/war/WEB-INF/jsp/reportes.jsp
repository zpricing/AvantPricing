<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp"/>
<body>
	<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/dwr/interface/dwrReportes.js"/>'></script>
	<script type="text/javascript"> 
		
		function enviarURL(val){
		   var parent = "reportes.htm";
		   var tipo = "actualizar";
		   var fecha_inicio = document.getElementById('fecha_inicial_'+val).value;
		   var fecha_fin = document.getElementById('fecha_final_'+val).value;
		   var url =  "?tipo="+tipo+"&inicio="+fecha_inicio+"&fin="+fecha_fin;
		   window.location =  parent + url;
		}

		function download(tipo){ 
			document.getElementById('tipo').value = tipo;
			$('#datos').submit();
		} 	
		
		$(function() {
			$(".tabs").tabs("select", 1);
			dwrReportes.getGraficos('${inicio}','${fin}',{ callback : function(str) { 
			var result = JSON.parse(str);
			
			var opcionesDiario = {
			    series: {
			        lines: { show: true },
			        points: { show: true }
			    },
 				xaxis: {
 				    mode: "time",
 				    timeformat: "%d/%m"
 				},
 				grid: {
 					hoverable: true,
 					clickable: true
 				}
			};
			var opcionesDiarioPorc = {
			    series: {
			        lines: { show: true },
			        points: { show: true }
			    },
 				xaxis: {
 				    mode: "time",
 				    timeformat: "%d/%m"
 				},
 				yaxis: {
 				   	max: 100	
 				},
 				grid: {
 					hoverable: true,
 					clickable: true
 				}

			};		     				
			var opcionesSemanal = {
			    series: {
			        lines: { show: true },
			        points: { show: true }
			    },
			    xaxis: {
			    	ticks : result.ticksSemanal
			    },
 				grid: {
 					hoverable: true,
 					clickable: true
 				}
			    

			};
			var opcionesMensual = {
			    series: {
			        lines: { show: true },
			        points: { show: true }
			    },
			    xaxis: {
			    	ticks : result.ticksMensual
			    },
 				grid: {
 					hoverable: true,
 					clickable: true
 				}
			    

			};
			var opcionesPorcSemanal = {
			    series: {
			        lines: { show: true },
			        points: { show: true }
			    },
			    xaxis: {
			    	ticks : result.ticksSemanal
			    },
 				yaxis: {
 				   	max: 100	
 				},
 				grid: {
 					hoverable: true,
 					clickable: true
 				}

			};	 							
			var opcionesPorcMensual = {
			    series: {
			        lines: { show: true },
			        points: { show: true }
			    },
			    xaxis: {
			    	ticks : result.ticksMensual
			    },
 				yaxis: {
 				   	max: 100	
 				},
 				grid: {
 					hoverable: true,
 					clickable: true
 				}

			};		

			$.plot("#graficoDiario", result.graficoDiario,opcionesDiario); 
			$.plot("#graficoDiarioPorc", result.graficoDiarioPorc,opcionesDiarioPorc); 
			
			$.plot("#graficoSemanal", result.graficoSemanal,opcionesSemanal); 
			$.plot("#graficoSemanalPorc", result.graficoSemanalPorc,opcionesPorcSemanal); 
			
			$.plot("#graficoMensual", result.graficoMensual,opcionesMensual); 
			$.plot("#graficoMensualPorc", result.graficoMensualPorc,opcionesPorcMensual);

			function showTooltip(x, y, contents) {
				$("<div id='tooltip'>" + contents + "</div>").css({
					position: "absolute",
					display: "none",
					top: y + 5,
					left: x + 5,
					border: "1px solid #fdd",
					padding: "2px",
					"background-color": "#fee",
					opacity: 0.80
				}).appendTo("body").fadeIn(200);
			}
			
			
			var previousPoint = null;
			function mostrarInfo(item){
				if (item) {
					if (previousPoint != item.datapoint) {
						previousPoint = item.datapoint;
						$("#tooltip").remove();
						var x = item.datapoint[0];
						var y = item.datapoint[1];
						showTooltip(item.pageX, item.pageY,y);
					}
				}else {
					$("#tooltip").remove();
					previousPoint = null;            
				}
			}
			
			$("#graficoDiario").bind("plothover", function (event, pos, item) {									
				mostrarInfo(item);								
			});
			$("#graficoSemanal").bind("plothover", function (event, pos, item) {									
				mostrarInfo(item);								
			});
			$("#graficoMensual").bind("plothover", function (event, pos, item) {									
				mostrarInfo(item);								
			});
			
			$("#graficoDiarioPorc").bind("plothover", function (event, pos, item) {									
				mostrarInfo(item);								
			});
			$("#graficoSemanalPorc").bind("plothover", function (event, pos, item) {									
				mostrarInfo(item);								
			});
			$("#graficoMensualPorc").bind("plothover", function (event, pos, item) {									
				mostrarInfo(item);								
			});
		} });
	});
	</script>	
			<div id="pjax">
				<div id="wrapper" data-adminica-side-top="2" data-adminica-side-inner="1">
					<jsp:include page="/sidebar.htm"/>
					<div id="main_container" class="main_container  container_16  clearfix">
							
						<div class="flat_area grid_16">
							<h2><fmt:message key="reports" /></h2>
						</div>

       					<form:form method="post" id="datos" action="reportes.htm" commandName="reportes">
							
							<form:hidden path="tipo"/>
							<form:hidden path="inicio" value="${inicio}"/>
							<form:hidden path="fin" value="${fin}"/>
	        				
	        				<div class = "box  tabs">
							<ul class="tab_header clearfix">
								<li><a href="#tabs-1"><fmt:message key="daily"/></a></li>
								<li><a href="#tabs-2"><fmt:message key="weekly"/></a></li>
								<li><a href="#tabs-3"><fmt:message key="monthly"/></a></li>
							</ul>
						
							<div class="toggle_container">
								
								<div id="tabs-1" class="block">
									<div class="box grid_16">
										<div class="columns clearfix">
											<div class="col_40">
												<fieldset>
													<label><fmt:message key="since"/></label>
													<div class="clearfix">
														<input id="fecha_inicial_d" value="${inicio}"  type="text" class="datepicker" style="width:100px;">
													</div>
												</fieldset>
											</div>

											<div class="col_40">
												<fieldset>
													<label><fmt:message key="until"/></label>
													<div class="clearfix">
														<input id="fecha_final_d"  value="${fin}" type="text" class="datepicker" style="width:100px;">
													</div>
												</fieldset>
											</div>

											<div class="col_20">
												<fieldset>
													<label><fmt:message key="update"/></label>
													<div class="clearfix">
														<button type="button" class="black img_icon has_text" onclick="enviarURL('d')">
															<img src="assets/adminica/images/icons/small/white/refresh.png">
															<span><fmt:message key="update"/></span>
														</button>
													</div>
												</fieldset>
											</div>
										</div>
									</div>

									<div class="box grid_16">
										<h2 class="box_head"><fmt:message key="charts" /></h2>
										<div class="controls">
											<a href="#" class="grabber"></a>
											<a href="#" class="toggle"></a>
										</div>
										<div class="block">
											<div id="graficoDiario" style="width:1060px;height:400px"></div>
											<div id="graficoDiarioPorc" style="width:1060px;height:400px"></div>
										</div>
									</div>
									
									<div class="box grid_16">
										<h2 class="box_head"><fmt:message key="data" /></h2>
										<div class="controls">
											<a href="#" class="grabber"></a>
											<a href="#" class="toggle"></a>
										</div>										
										<div class="block">
											<table class="static editable">
												<thead>
													<tr>
														<th scope="col"><fmt:message key="date"/></th>
														<th scope="col"><fmt:message key="week"/></th>
														<th scope="col"><fmt:message key="attendance"/>(HT)</th>
														<th scope="col"><fmt:message key="short.income"/>(HT)</th>
														<th scope="col"><fmt:message key="attendance"/>(RM)</th>
														<th scope="col"><fmt:message key="short.income"/>(RM)</th>
														<th scope="col"><fmt:message key="attendance"/>(tot)</th>
														<th scope="col"><fmt:message key="short.income"/>(tot)</th>	
														<th scope="col"><fmt:message key="attendance"/>(on)</th>	
														<th scope="col"><fmt:message key="short.percentage"/>(HT)</th>
														<th scope="col"><fmt:message key="short.growth"/>(RM)</th>
														<th scope="col"><fmt:message key="short.percentage"/>(on)</th>	
														<th scope="col"><fmt:message key="short.average"/>(HT)</th>
														<th scope="col"><fmt:message key="short.average"/>(RM)</th>
														<th scope="col"><fmt:message key="short.average"/>(tot)</th>										
													</tr>
												</thead>
												<tbody>
				                                    <c:forEach items="${diario}" var="diario">
					                                    <tr>
					                                    	<td><fmt:formatDate value="${diario.fecha}" pattern="MM/dd/yyyy" /></td>
					                                    	<td><c:out value="${fn:trim(diario.semana)}"></c:out></td>		                                    		
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(diario.asistencia_HT)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(diario.ingreso_HT)}" /></td> 		                                    	
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(diario.asistencia_RM)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(diario.ingreso_RM)}"/></td> 			                                    		
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(diario.asistencia_total)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(diario.ingreso_total)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(diario.asistencia_online)}"/></td>
					                                    	<td><fmt:formatNumber	pattern="###.###"value="${fn:trim(diario.porc_asistencia_HT)}" />%</td>
					                                    	<td><fmt:formatNumber  pattern="###.###" value="${fn:trim(diario.crec_asistencia_RM)}" />%</td>
					                                    	<td><fmt:formatNumber  pattern="###.###"value="${fn:trim(diario.porc_asistencia_online)}"/>%</td>		                                    	
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(diario.prom_HT)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(diario.prom_RM)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(diario.prom_total)}"/></td>		                                    		       	
					                                    </tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
										<div class="button_bar clearfix">
											<button type="button"  id = "diario" class="black has_text small img_icon"  onclick="download(this.id)" > 
												<img src="assets/adminica/images/icons/small/white/cloud_download.png">
												<span><fmt:message key="download"/></span>
											</button>
									
										</div>	
									</div>
								</div>
								
								<div id="tabs-2" class="block">
									<div class="box grid_16">
										<div class="columns clearfix">
											<div class="col_40">
												<fieldset>
													<label><fmt:message key="since"/></label>
													<div class="clearfix">
														<input id="fecha_inicial_w" value="${inicio}"  type="text" class="datepicker" style="width:100px;">
													</div>
												</fieldset>
											</div>

											<div class="col_40">
												<fieldset>
													<label><fmt:message key="until"/></label>
													<div class="clearfix">
														<input id="fecha_final_w"  value="${fin}" type="text" class="datepicker" style="width:100px;">
													</div>
												</fieldset>
											</div>

											<div class="col_20">
												<fieldset>
													<label><fmt:message key="update"/></label>
													<div class="clearfix">
														<button type="button" class="black img_icon has_text" onclick="enviarURL('w')">
															<img src="assets/adminica/images/icons/small/white/refresh.png">
															<span><fmt:message key="update"/></span>
														</button>
													</div>
												</fieldset>
											</div>
										</div>
									</div>
									
									<div class="box grid_16">
										<h2 class="box_head"><fmt:message key="charts" /></h2>
										<div class="controls">
											<a href="#" class="grabber"></a>
											<a href="#" class="toggle"></a>
										</div>										
										<div class="block">
											<div id="graficoSemanal" style="width:1060px;height:400px"></div>
											<div id="graficoSemanalPorc" style="width:1060px;height:400px"></div>
										</div>
									</div>		

									<div class="box grid_16">
										<h2 class="box_head"><fmt:message key="data" /></h2>
										<div class="controls">
											<a href="#" class="grabber"></a>
											<a href="#" class="toggle"></a>
										</div>										
										<div class="block">
											<table class="static editable">
												<thead>
													<tr>
														<th scope="col"><fmt:message key="week"/></th>
														<th scope="col"><fmt:message key="month"/></th>
														<th scope="col"><fmt:message key="year"/></th>
														<th scope="col"><fmt:message key="attendance"/>(HT)</th>
														<th scope="col"><fmt:message key="short.income"/>(HT)</th>
														<th scope="col"><fmt:message key="attendance"/>(RM)</th>
														<th scope="col"><fmt:message key="short.income"/>(RM)</th>
														<th scope="col"><fmt:message key="attendance"/>(tot)</th>
														<th scope="col"><fmt:message key="short.income"/>(tot)</th>	
														<th scope="col"><fmt:message key="attendance"/>(on)</th>	
														<th scope="col"><fmt:message key="short.percentage"/>(HT)</th>
														<th scope="col"><fmt:message key="short.growth"/>(RM)</th>
														<th scope="col"><fmt:message key="short.percentage"/>(on)</th>	
														<th scope="col"><fmt:message key="short.average"/>(HT)</th>
														<th scope="col"><fmt:message key="short.average"/>(RM)</th>
														<th scope="col"><fmt:message key="short.average"/>(tot)</th>										
													</tr>
												</thead>
												<tbody>
				                                    <c:forEach items="${semanal}" var="semanal">
					                                    <tr>
					                                    	<td><c:out value="${fn:trim(semanal.semana)}"></c:out></td>
					                                    	<td><c:out value="${fn:trim(semanal.mes)}"></c:out></td> 		
					                                    	<td><c:out value="${fn:trim(semanal.anno)}"></c:out></td><td><fmt:formatNumber  pattern="###,###" value="${fn:trim(semanal.asistencia_HT)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(semanal.ingreso_HT)}" /></td> 		                                    	
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(semanal.asistencia_RM)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(semanal.ingreso_RM)}"/></td> 			                                    		
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(semanal.asistencia_total)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(semanal.ingreso_total)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(semanal.asistencia_online)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###.###"value="${fn:trim(semanal.porc_asistencia_HT)}" />%</td>
					                                    	<td><fmt:formatNumber  pattern="###.###" value="${fn:trim(semanal.crec_asistencia_RM)}" />%</td>
					                                    	<td><fmt:formatNumber  pattern="###.###"value="${fn:trim(semanal.porc_asistencia_online)}"/>%</td>		                                    	
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(semanal.prom_HT)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(semanal.prom_RM)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(semanal.prom_total)}"/></td>	                                    		       	
					                                    </tr>
													</c:forEach>
												</tbody>
											</table>								
										</div>
										<div class="button_bar clearfix">
											<button type="button"  id = "semanal" class="black has_text small img_icon"  onclick="download(this.id)"> 
												<img src="assets/adminica/images/icons/small/white/cloud_download.png">
												<span><fmt:message key="download"/></span>
											</button>
										</div>
									</div>
								</div>
								
								<div id="tabs-3" class="block">
									<div class="box grid_16">
										<div class="columns clearfix">
											<div class="col_40">
												<fieldset>
													<label><fmt:message key="since"/></label>
													<div class="clearfix">
														<input id="fecha_inicial_m" value="${inicio}"  type="text" class="datepicker" style="width:100px;">
													</div>
												</fieldset>
											</div>

											<div class="col_40">
												<fieldset>
													<label><fmt:message key="until"/></label>
													<div class="clearfix">
														<input id="fecha_final_m"  value="${fin}" type="text" class="datepicker" style="width:100px;">
													</div>
												</fieldset>
											</div>

											<div class="col_20">
												<fieldset>
													<label><fmt:message key="update"/></label>
													<div class="clearfix">
														<button type="button" class="black img_icon has_text" onclick="enviarURL('m')">
															<img src="assets/adminica/images/icons/small/white/refresh.png">
															<span><fmt:message key="update"/></span>
														</button>
													</div>
												</fieldset>
											</div>
										</div>
									</div>
									
									<div class="box grid_16">
										<h2 class="box_head"><fmt:message key="charts" /></h2>
										<div class="controls">
											<a href="#" class="grabber"></a>
											<a href="#" class="toggle"></a>
										</div>
										<div class="block">
											<div id="graficoMensual" style="width:1060px;height:400px"></div>
											<div id="graficoMensualPorc" style="width:1060px;height:400px"></div>																				
										</div>
									</div>		

									<div class="box grid_16">
										<h2 class="box_head"><fmt:message key="data" /></h2>
	
										<div class="controls">
											<a href="#" class="grabber"></a>
											<a href="#" class="toggle"></a>
										</div>										
										<div class="lock">
											<table class="static editable">
												<thead>
													<tr>
														<th scope="col"><fmt:message key="month"/></th>
														<th scope="col"><fmt:message key="year"/></th>
														<th scope="col"><fmt:message key="attendance"/>(HT)</th>
														<th scope="col"><fmt:message key="short.income"/>(HT)</th>
														<th scope="col"><fmt:message key="attendance"/>(RM)</th>
														<th scope="col"><fmt:message key="short.income"/>(RM)</th>
														<th scope="col"><fmt:message key="attendance"/>(tot)</th>
														<th scope="col"><fmt:message key="short.income"/>(tot)</th>	
														<th scope="col"><fmt:message key="attendance"/>(on)</th>	
														<th scope="col"><fmt:message key="short.percentage"/>(HT)</th>
														<th scope="col"><fmt:message key="short.growth"/>(RM)</th>
														<th scope="col"><fmt:message key="short.percentage"/>(on)</th>	
														<th scope="col"><fmt:message key="short.average"/>(HT)</th>
														<th scope="col"><fmt:message key="short.average"/>(RM)</th>
														<th scope="col"><fmt:message key="short.average"/>(tot)</th>										
													</tr>
												</thead>
												<tbody>
				                                    <c:forEach items="${mensual}" var="mensual">
					                                    <tr>
					                                    	<td><c:out value="${fn:trim(mensual.mes)}"></c:out></td> 		    
					                                    	<td><c:out value="${fn:trim(mensual.anno)}"></c:out></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(mensual.asistencia_HT)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(mensual.ingreso_HT)}" /></td> 		                                    	
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(mensual.asistencia_RM)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(mensual.ingreso_RM)}"/></td> 			                                    		
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(mensual.asistencia_total)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(mensual.ingreso_total)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(mensual.asistencia_online)}"/></td>
					                                    	<td><fmt:formatNumber	pattern="###.###"value="${fn:trim(mensual.porc_asistencia_HT)}" />%</td>
					                                    	<td><fmt:formatNumber  pattern="###.###" value="${fn:trim(mensual.crec_asistencia_RM)}" />%</td>
					                                    	<td><fmt:formatNumber  pattern="###.###"value="${fn:trim(mensual.porc_asistencia_online)}"/>%</td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(mensual.prom_HT)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(mensual.prom_RM)}"/></td>
					                                    	<td><fmt:formatNumber  pattern="###,###" value="${fn:trim(mensual.prom_total)}"/></td>		                                    		       	
					                                	</tr>
													</c:forEach>
												</tbody>
											</table>	
										</div>
										<div class="button_bar clearfix">
											<button type="button"  id = "mensual" class="black has_text small img_icon"  onclick="download(this.id)"> 
												<img src="assets/adminica/images/icons/small/white/cloud_download.png">
												<span><fmt:message key="download"/></span>
											</button>
										</div>									
									</div>
								</div>
							</div>
						</div>				
					</form:form>
				</div>
			</div>
</body>
