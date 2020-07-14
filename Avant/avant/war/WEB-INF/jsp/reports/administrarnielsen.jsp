<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title><fmt:message key="pageTitle" /></title>
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="form"/>" />
	<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />
<style>
.error {
	color: red;
}
</style>
</head>
<script type="text/javascript">
var activo = new Array();
var activoComplejo = new Array();
var textoOriginal = new Array();

function recuperar(x) {	
	var id = x.parentNode.parentNode.id;
	x.parentNode.parentNode.innerHTML = textoOriginal[id];
	activo[id] = false;
	activoComplejo[id] = false;
}

function editar(x, operacion) {
	if (!activo[x.id]) {
		activo[x.id]=true; 
		x.onclick=null;
		var id = x.id.split("_")[x.id.split("_").length-1];
		var contenido = x.innerHTML;
		textoOriginal[x.id] = contenido;
		x.innerHTML = '<form method="post"><input name="operacion" type="hidden" value="'+operacion+'">'
			+ '<input name="id" type="hidden" value="'+id+'"><input type="text" name="nombre" id="' + operacion + id + '" value="'+contenido+'"/>'
			+ '<input type="submit" class="boton" id="submitear' + operacion + id+ '"/><a onclick="recuperar(this);" style="cursor:pointer">[&times;]</a></form>';
		var continuar = '<fmt:message key="continue"/>';
		document.getElementById("submitear" + operacion + id).value=continuar;
		var toFocus = operacion+id;
		document.getElementById(toFocus).focus();
	}
	return false;
}

function editarComplejo(x, operacion, size) {
	if (!activoComplejo[x.id]) {
		var idComplejo = x.parentNode.id.split(";")[0];
		var fechaDesde = x.parentNode.id.split(";")[1];
		activoComplejo[x.id]=true; 
		x.onclick=null;
		var id = x.id.split("_")[x.id.split("_").length-1];
		var contenido = x.innerHTML;
		textoOriginal[x.id] = contenido;
		x.innerHTML = '<form method="post"><input name="operacion" type="hidden" value="'+operacion+'">'
			+ '<input type="hidden" name="fechaDesde" value="'+fechaDesde+'">'
			+ '<input name="idComplejo" type="hidden" value="'+idComplejo+'"><input type="text" size="'+size+'" name="nombre" id="' + operacion + id + '" value="'+contenido+'"/>'
			+ '<input type="submit" class="boton" id="submitear' + operacion + id+ '"/><a onclick="recuperar(this);" style="cursor:pointer">[&times;]</a></form>';
		var continuar = '<fmt:message key="continue"/>';
		document.getElementById("submitear" + operacion + id).value=continuar;
		var toFocus = operacion+id;
		document.getElementById(toFocus).focus();
	}
	return false;
}
function editarComplejoEmpresa(x, operacion) {
	if (!activoComplejo[x.id]) {
		var idComplejo = x.parentNode.id.split(";")[0];
		var fechaDesde = x.parentNode.id.split(";")[1];
		activoComplejo[x.id]=true; 
		x.onclick=null;
		var id = x.id.split("_")[x.id.split("_").length-1];
		var contenido = x.parentNode.innerHTML;
		textoOriginal[x.parentNode.id] = contenido;
	
		x.innerHTML = '<input name="operacion" type="hidden" value="'+operacion+'">'
			+ '<input type="hidden" name="fechaDesde" value="'+fechaDesde+'">'
			+ '<input name="idComplejo" type="hidden" value="'+idComplejo+'">';

		if (window.XMLHttpRequest)
		  {
		  xmlhttp=new XMLHttpRequest();
		  }
		else
		  {
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		xmlhttp.open("GET","auxiliarNielsen.htm?name=nombre&dominio=rptEmpresas",false);
		xmlhttp.send(null);

		while (xmlhttp.readyState != 4) {;}
		x.innerHTML += xmlhttp.responseText;
			
		x.innerHTML += '<input class="boton" type="submit" id="submitear' + operacion + id+ '"/><a onclick="recuperar(this);" style="cursor:pointer">[&times;]</a>';
		var continuar = '<fmt:message key="continue"/>';
		document.getElementById("submitear" + operacion + id).value=continuar;
		var toFocus = operacion+id;
	}
	return false;
}
function editarComplejoRm(x, operacion) {
	if (!activoComplejo[x.id]) {
		var idComplejo = x.parentNode.id.split(";")[0];
		var fechaDesde = x.parentNode.id.split(";")[1];
		activoComplejo[x.id]=true; 
		x.onclick=null;
		var id = x.id.split("_")[x.id.split("_").length-1];
		var contenido = x.parentNode.innerHTML;
		textoOriginal[x.parentNode.id] = contenido;
	
		x.innerHTML = '<form method="post"><input name="operacion" type="hidden" value="'+operacion+'">'
			+ '<input type="hidden" name="fechaDesde" value="'+fechaDesde+'">'
			+ '<input name="idComplejo" type="hidden" value="'+idComplejo+'">'
			+ '<select name="nombre">'
			+ '<option value="true"><fmt:message key="Y"/></option>'
			+ '<option value="false"><fmt:message key="N"/></option>'
			+ '</select>'
			+ '<input class="boton" type="submit" id="submitear'
			+ operacion + id
			+ '" /></form><a onclick="recuperar(this);" style="cursor:pointer">[&times;]</a>';
		var continuar = '<fmt:message key="continue"/>';
		document.getElementById("submitear" + operacion + id).value=continuar;
		var toFocus = operacion+id;
	}
	return false;
}
</script>
<body>
<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor">
<span id="titulo"><fmt:message key="enterprises"/></span>
<br>

<table id="rounded-corner">
    <thead>
    	<tr>
    		<th scope="col" class="rounded-company"><fmt:message key="id"/></th>
        	<th scope="col" class="rounded-q1"><fmt:message key="name"/></th>
            <th scope="col" class="rounded-q2"><fmt:message key="NielsenCode"/></th>
            <th scope="col" class="rounded-q3"><fmt:message key="modify"/></th>
            <th scope="col" class="rounded-q4"><fmt:message key="eliminate"/></th>
        </tr>
    </thead>
        <tfoot>
    	<tr>
        	<td colspan="4" class="rounded-foot-left"><em></em></td>
        	<td class="rounded-foot-right">&nbsp;</td>
        </tr>
    </tfoot>

    <tbody>
    <c:forEach items="${rptEmpresas}" var="empresa">
    <tr>
    		<td><c:out value="${empresa.rpt_empresa_id}"></c:out></td>
    		<td onclick="editar(this, 'updateNombreEmpresa');" id="empresa_nombre_${empresa.rpt_empresa_id}"><c:out value="${empresa.nombre}"></c:out></td>
			<td><c:out value="${empresa.codigo_nielsen}"></c:out></td>
			<td onclick="editar(document.getElementById('empresa_nombre_${empresa.rpt_empresa_id}'),'updateNombreEmpresa');return false;"><a ><fmt:message key="modify"/></a></td>
			<td><a href="<c:url value="administrarNielsen.htm?operacion=eliminarEmpresa&id=${empresa.rpt_empresa_id}"/>"><fmt:message key="eliminate"/></a></td>
		</tr>
	</c:forEach>
    </tbody>
</table>

	</div>
	
	
<div id="contenedor">
<span id="titulo"><fmt:message key="complex"/></span>
<br/>

<table id="rounded-corner">
    <thead>
    	<tr>
    		<th scope="col" class="rounded-company"><fmt:message key="id"/></th>
        	<th scope="col" class="rounded-q1"><fmt:message key="name"/></th>
            <th scope="col" class="rounded-q2" title="<fmt:message key="dateFromExplanation"/>"><fmt:message key="dateFrom"/></th>
            <th scope="col" class="rounded-q1"><fmt:message key="roomAmount"/></th>
            <th scope="col" class="rounded-q1"><fmt:message key="enterprise"/></th>
            <th scope="col" class="rounded-q1"><fmt:message key="city"/></th>
            <th scope="col" class="rounded-q1" title="<fmt:message key="isRmExplanation"/>">RM</th>
            <th scope="col" class="rounded-q3"><fmt:message key="add"/></th>
            <th scope="col" class="rounded-q4"><fmt:message key="eliminate"/></th>
        </tr>
    </thead>
        <tfoot>
    	<tr>
        	<td colspan="8" class="rounded-foot-left"><em></em></td>
        	<td class="rounded-foot-right">&nbsp;</td>
        </tr>
    </tfoot>

    <tbody>
    <c:forEach items="${rptComplejos}" var="complejo">
    <tr id="${complejo.rptComplejoId};<fmt:formatDate value="${complejo.fechaDesde}"  pattern="dd-MM-yyyy"/>">
    		<td id="complejo_id_${complejo.rptComplejoId}" onclick="editarComplejo(this, 'editarIdComplejo', 3);"><c:out value="${complejo.rptComplejoId}"></c:out></td>
    		<td id="complejo_nombre_${complejo.rptComplejoId}" onclick="editarComplejo(this, 'editarNombreComplejo', 15);"><c:out value="${complejo.nombre}"></c:out></td>
			<td id="complejo_fecha_${complejo.rptComplejoId}" ><fmt:formatDate value="${complejo.fechaDesde}"  pattern="dd-MM-yyyy"/></td>
			<td id="complejo_salas_${complejo.rptComplejoId}" onclick="editarComplejo(this, 'editarCantidadSalasComplejo', 3);"><c:out value="${complejo.cantidadSalas}"></c:out></td>
			<td>
				<form method="post" id="${complejo.rptComplejoId};<fmt:formatDate value="${complejo.fechaDesde}"  pattern="dd-MM-yyyy"/>">
					<div id="complejo_empresa_${complejo.rptComplejoId}" onclick="editarComplejoEmpresa(this, 'editarEmpresaComplejo');">
						<c:out value="${complejo.rptEmpresa.nombre}" default="${complejo.rptEmpresa.codigo_nielsen}"/>
					</div>
				</form>
			</td>
			<td id="complejo_ciudad_${complejo.rptComplejoId}" onclick="editarComplejo(this, 'editarCiudadComplejo', 15);"><c:out value="${complejo.ciudad}"></c:out></td>
			<td id="complejo_rm_${complejo.rptComplejoId}" onclick="editarComplejoRm(this, 'editarRmComplejo');"><c:if test="${complejo.rm}"><fmt:message key="Y"/></c:if> <c:if test="${!complejo.rm}"><fmt:message key="N"/></c:if></td>
			<td><a href="<c:url value="agregarComplejoNielsen.htm?idComplejo=${complejo.rptComplejoId}"/>"><span title='<fmt:message key="addComplejoNielsenExpl"/>'><fmt:message key="add"/></span></a></td>
			<td><a href="administrarNielsen.htm?operacion=eliminarComplejo&id=${complejo.rptComplejoId}&fechaDesde=<fmt:formatDate value="${complejo.fechaDesde}"  pattern="dd-MM-yyyy"/>"/><fmt:message key="eliminate"/></a></td>
		</tr>
	</c:forEach>
	<tr> 
	<td class="rounded-foot-left" colspan="8"> <a href="agregarComplejoNielsen.htm">
	<fmt:message key="add" /> <fmt:message key="new"/> <fmt:message key="complex"/></a></td>
	<td class="rounder-foot-right"></td></tr>
    </tbody>
</table>

	</div>
	
	<div id="contenedor" >
	<span id="titulo"><fmt:message key="NielsenWeeks"/></span>
	<br/>
	<div style="overflow:scroll; height: 250px;">
	<table id="rounded-corner">
    <thead>
    	<tr>
            <th scope="col" class="rounded-company"><fmt:message key="dateFrom"/></th>
            <th scope="col" class="rounded-q1"><fmt:message key="week"></fmt:message></th>
            <th scope="col" class="rounded-q1"><fmt:message key="roomAmount"/></th>
            <th scope="col" class="rounded-q4"><fmt:message key="eliminate"/></th>
        </tr>
    </thead>
        <tfoot>
    	<tr>
        	<td colspan="3" class="rounded-foot-left"><em></em></td>
        	<td class="rounded-foot-right">&nbsp;</td>
        </tr>
    </tfoot>

    <tbody>
    	<c:forEach items="${semanasNielsen}" var="semana">
    		<tr>
    			<td><fmt:formatDate value="${semana.fechaInicio}"  pattern="dd-MM-yyyy"/></td>
    			<td><c:out value="${semana.weekOfYear}"/> <fmt:message key="of"/> <fmt:formatDate value="${semana.fechaInicio}"  pattern="yyyy"/></td>
    			<td><c:out value="${semana.cantidadComplejos}"/></td>
    			<td><a href="administrarNielsen.htm?operacion=eliminarSemanaNielsen&id=any&fechaDesde=<fmt:formatDate value="${semana.fechaInicio}"  pattern="dd-MM-yyyy"/>"/><fmt:message key="eliminate"/></a></td>
    		</tr>
    	</c:forEach>
    </tbody>
    </table>
    </div>
	</div>
	<jsp:include page="/footer.htm"></jsp:include>
</body>
</html>