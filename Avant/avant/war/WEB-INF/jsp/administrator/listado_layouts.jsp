<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<title><fmt:message key="pageTitle" /></title>
	
	<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="datepicker"/>" />
	<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="form"/>" />
	<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="tablas_admin"/>" />
	<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="cmxform"/>" />
	<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="zpcinemas_general"/>" />
	<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="jquery.superbox"/>" />
	
	
	<script type="text/javascript" src="assets/jquery/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="assets/jquery/jquery-superbox-0.9.1/jquery.superbox-min.js"></script>
	<script type="text/javascript" src="assets/cmxform/js/cmxform.js"></script>
	<script type="text/javascript" src="<c:url value="/js/popcalendar.js" />"></script>
	<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/dwr/interface/dwrFunciones.js"/>'></script>
	<script>
		function cargaCapacidadFuncion(funcionId) {
			dwrFunciones.cargaCapacidadFuncion(funcionId, { callback:handleCargaCapacidadFuncion, errorHandler:errorHandlerCargaCapacidadFuncion});
		}
	
		function handleCargaCapacidadFuncion(data) {
			alert(data.id);
			alert(data.funcionArea);
			alert(data.funcionArea.length);
			var disponible = "";
			for (var i = 0 ; i < data.funcionArea.length ; i++) {
				alert(data.funcionArea[i].capacidadDisponible);
				disponible = disponible + data.funcionArea[i].area.descripcionCorta + " : " + data.funcionArea[i].capacidadDisponible + " ";
			}
	
			dwr.util.setValue("funcionArea_disponible_" + data.id, disponible);
		}
	
		function errorHandlerCargaCapacidadFuncion(message) {
			alert("ERROR : " + message);
		}
	
		function limpiaCuposRevenueSala(salaId) {
			var fecha = '<fmt:formatDate value="${fecha_actual}" type="both" pattern="dd-MM-yyyy" />';
			dwrFunciones.limpiaCuposRevenueSala(salaId, fecha, { callback:handleLimpiaCuposRevenueSala, errorHandler:errorHandlerLimpiaCuposRevenueSala});
		}
		function handleLimpiaCuposRevenueSala(data) {
			alert("Ok");
		}
		function errorHandlerLimpiaCuposRevenueSala(message) {
			alert("ERROR : " + message);
		}
	
		function recargarCuposRevenueSala(salaId) {
			var fecha = '<fmt:formatDate value="${fecha_actual}" type="both" pattern="dd-MM-yyyy" />';
			dwrFunciones.recargaCuposRevenueSala(salaId, fecha, { callback:handleRecargarCuposRevenueSala, errorHandler:errorHandlerRecargarCuposRevenueSala});
		}
		function handleRecargarCuposRevenueSala(data) {
			alert("Ok");
		}
		function errorHandlerRecargarCuposRevenueSala(message) {
			alert("ERROR : " + message);
		}
		
		function submitFormularioFecha(fecha) {
			document.getElementById('fecha').value = fecha;
			document.getElementById('funciones').submit();
		}
	
		function submitEliminar() {
			document.form_funciones.action = "admin_eliminarfuncion.htm";
			document.form_funciones.submit();
		}
	
		function submitGuardar() {
			document.form_funciones.action = "admin_guardarMascarasFunciones.htm";
			document.form_funciones.submit();
		}
	
		function modificarTodasLasMascaras() {
			var mascara = document.getElementById('mascaraGlobal').value;
			var combosMascaras =  document.getElementsByName('mascaras');
			for (var i = 0; i < combosMascaras.length ; i++) {	
				var items = document.getElementsByName('mascaras')[i].options;
				for (var j = 0 ; j < items.length ; j++) {
					if (items[j].text.toLowerCase() == mascara.toLowerCase()) {
						document.getElementsByName('mascaras')[i].selectedIndex = j;
					}
				}
			}
		}
		
		$(function(){
			$.superbox();
		});
	</script>
</head>
<body>

<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor_contenido">
	<h1>Layouts</h1>
	<hr class="separador_titulo">
	
	<table>
		<tr>
			<th>Id</th>
			<th>Descripcion</th>
		</tr>
	<c:forEach items="${listaLayouts}" var="layout">
		<tr>
			<td><c:out value="${layout.id}" /></td>
			<td><c:out value="${layout.descripcion}" /></td>
			<td></td>
		</tr>
		
	</c:forEach>
	</table>
</div>

<jsp:include page="/footer.htm"></jsp:include>
</body>
</html>