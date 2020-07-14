<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title><fmt:message key="pageTitle" /></title>

<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="form"/>" />
	<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="datepicker"/>" />
	<style>
.error {
	color: red;
}
</style>
<script type="text/javascript" src="<c:url value="/js/ColorPicker2.js"/>"></script>
<script type="text/javascript"">
var cp = new ColorPicker(); // DIV style
</script>

</head>
<body>

<jsp:include page="/menu2.htm"></jsp:include>

<div id="contenedor">
<span id="titulo"><fmt:message key="markerTypes" /></span>
<br>
<div id="form-area" style="width: 290px;">
<fieldset>
	<legend><fmt:message key="add"/> <fmt:message key="markerType" /></legend>
<form:form method="post" commandName="tipomarker" name="formm">
			<table id="tablaForm">
				<tr>
					<td><form:hidden path="id"/>				
					</td>
				</tr>
				<tr>
					<td><fmt:message key="color" /></td>
					<td><form:input path="color" id="color" readonly="true"/></td>
					<td>
					<a href="#" onclick="cp.select(document.formm.color,'pick');return false;" name="pick" id="pick">
					<img style="float:right;" alt="" border="0" src="<c:url value="images/Palette.png"/>" width="40px"/>
					</a>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="code" /></td>
					<td><form:input path="codigo" maxlength="4"/>
					</td>
				</tr>
				<tr>
					<td><form:errors path="codigo" cssClass="error"></form:errors>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="description" /></td>
					<td><form:input path="descripcion"/>
					</td>
				</tr>
				
				<tr>
				<td align="right">
				<input id="boton" style="margin-right: 44px;" type="submit" value="<fmt:message key="modify"/>">
				</td>
				</tr>
				<c:out value="${error}"></c:out>
				<c:if test="${error}">
				<tr>
				<td>
				<fmt:message key="error.code-already-exist"></fmt:message>
				</td>
				</tr>
				</c:if>
			</table>
				
		</form:form>
</fieldset>
</div>
<br>
<table id="rounded-corner">
	<thead>
	<tr>
	    <th scope="col" class="rounded-company" ><fmt:message key="color"/></th>
	    <th scope="col" class="rounded-q1" ><fmt:message key="code" /></th>
		<th scope="col" class="rounded-q1" ><fmt:message key="description"/></th>
		<th scope="col" class="rounded-q1" ><fmt:message key="modify"/></th>
		<th scope="col" class="rounded-q4" ><fmt:message key="eliminate"/></th>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${tipomarkers}" var="tm">
		<tr>
			<td width="30px" style="background-color:${tm.color};"></td>
			<td><c:out value="${tm.codigo}"></c:out></td>			
			<td><c:out value="${tm.descripcion}"></c:out></td>
			<td><a href="<c:url value="admin_modifytipomarker.htm?id_tipomarker=${tm.id}"/>"><fmt:message key="modify"/></a></td>
			<td><a href="<c:url value="admin_deletetipomarker.htm?id_tipomarker=${tm.id}"/>"><fmt:message key="eliminate"/></a></td>
		</tr>
		</c:forEach>
	</tbody>
	<tfoot>
	
	<tr>
        	<td colspan="4" class="rounded-foot-left"><em></em></td>
        	<td class="rounded-foot-right">&nbsp;</td>
        </tr>
	</tfoot>
</table>
<br>
</div>
<SCRIPT LANGUAGE="JavaScript">cp.writeDiv()</SCRIPT>
</body>
</html>