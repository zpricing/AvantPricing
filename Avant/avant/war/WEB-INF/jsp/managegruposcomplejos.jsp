<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title><fmt:message key="pageTitle" /></title>
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="general"/>" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />
<style type="text/css">
.error {
	color: red;
}
</style>
<link type="text/css" href="assets/jquery_ui/css/jquery.multiselect.css" rel="Stylesheet" />
<link type="text/css" href="assets/jquery_ui/css/ui-lightness/jquery-ui-1.8.21.custom.css" rel="Stylesheet" />
<script type="text/javascript" src="assets/jquery_ui/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="assets/jquery_ui/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="assets/jquery_ui/js/jquery.multiselect.min.js"></script>
<script type="text/javascript">
	$(function() {
		$( ".grupos_accordion_container button:first" ).button({
		    icons: {
		        primary: "ui-icon-plus"
		    }
		}).click(function() {
				$( "#dialog-grupo" ).dialog( "open" );
				return false;
			}
		);
		
		$( "#dialog-grupo" ).dialog({
			autoOpen: false,
			modal: true,
			buttons: {
				"Nuevo Grupo": function() {
					$( this ).dialog( "close" );
					$( "#managegruposcomplejos_new" ).submit();
				},
				Cancelar: function() {
					$( this ).dialog( "close" );
				}
			},
		});
		
		
	});
</script>
</head>
<body>

<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor"><span id="titulo"><fmt:message
	key="complexsgroups" /></span> <br>
<c:if test="${error == 1}">
	<b class="error"><fmt:message key="error.complexReferenced" /></b>
	<br>
</c:if> 
<br>
<div id="dialog-grupo" title="Nuevo Grupo">
	<form id="managegruposcomplejos_new" action="admin_managegruposcomplejos.htm" method="post">
	<fieldset>
		<label for="name">Nombre</label>
		<input type="text" name="descripcion" id="descripcion" class="text ui-widget-content ui-corner-all" value="">
		<input type="hidden" name="padre" id="padre" value="0">
		<input type="hidden" name="empresa" id="empresa" value="${empresas[0].id}">
		<input type="hidden" name="orden" id="orden" value="">
		<input type="hidden" name="action" id="action" value="new">
	</fieldset>
	</form>
</div>
<div class="grupos_accordion_container" style="padding: 1em 2.2em;">
	<button>Nuevo Grupo</button>
	<br/>
	<br/>
	<c:forEach var="grupo" items="${grupos}">
		<c:set var="grupo" value="${grupo}" scope="request"/>
		<c:set var="complejos" value="${complejos}" scope="request"/>
		<jsp:include page="grupoComplejoTree.jsp"/>
	</c:forEach>
</div>
<br>
</div>
<jsp:include page="/footer.htm"></jsp:include>
</body>
</html>