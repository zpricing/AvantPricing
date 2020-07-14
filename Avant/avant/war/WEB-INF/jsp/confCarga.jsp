<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script language="javascript" type="text/javascript">
	function abrir()
	{
		document.getElementById('popup').style.display='block';
	}		
</script>

<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="form"/>" />
<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="tablas_admin"/>" />
<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="popup"/>" />
<style type="text/css">
	.error { color: red; }
</style>
			
<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor">
	<span id="titulo"><fmt:message key="conf_charge" /></span>
	<fieldset style="width: 240px;">
		<legend><fmt:message key="state" /></legend>
		<c:if test="${carga==1}">
			<h2><fmt:message key="active"/></h2>
			<a href="<c:url value="/confCarga.htm?carga=desactivar" />"><fmt:message key="deactivate"/></a>
		</c:if>
		<c:if test="${carga==0}">
			<h2><fmt:message key="inactive"/></h2>
			<a href="<c:url value="/confCarga.htm?carga=activar" />"><fmt:message key="activate"/></a>
		</c:if>
	</fieldset>
	<form:form method="post" commandName="confCarga">
		<fieldset style="width: 240px;">
			<legend><fmt:message key="modify"/> <fmt:message key="hour"/> <fmt:message key="charge"/> </legend>
			<table id="tablaForm">
				<tr>
					<td><fmt:message key="hour"/></td>
					<td><form:input path="hora"/></td>
				</tr>
				<tr>
					<td align="right">
					<input id="boton" type="submit" value="<fmt:message key="modify"/>"></td>
					</tr>
			</table>
		</fieldset>
	</form:form>
	<fieldset>
		<legend><fmt:message key="execute"/></legend>
		<a href="<c:url value="/confCarga.htm?ejecutar" />" ><fmt:message key="executeNow"/></a>
	</fieldset>
	<fieldset>
		<legend><fmt:message key="execute"/>&nbsp;<c:out value="${nombreTrabajo}" /></legend>
		<a href="<c:url value="/confCarga.htm?ejecutarJob=${nombreTrabajo}"/>"
		   onclick = "abrir(); return true;">
		<fmt:message key="execute"/>&nbsp;${nombreTrabajo}</a>

	</fieldset>
	<div id="popup">&nbsp;<fmt:message key="executing" /><br>
		<img align="middle" alt="s" src="<c:url value="/images/loading_page.gif"/>">
	</div>
</div>