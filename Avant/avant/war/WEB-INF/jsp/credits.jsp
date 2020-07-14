<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title><fmt:message key="pageTitle" /></title>
<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="form"/>" />
<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="tablas_admin"/>" />
<style type="text/css">
.error {
	color: red;
}
</style>
</head>
<body style="background-color: black;">

<div id="contenedor">
<span id="titulo"><fmt:message key="credits"/></span>
		<table>
			<tr>
				<td><b>CEO</b></td>
				<td>Tom&aacute;s Bercovich</td>
			</tr>
			<tr>
				<td><b><fmt:message key="softwareArchitect"/></b></td>
				<td>Nicol&aacute;s Dujovne</td>
			</tr>
			<tr>
				<td><b><fmt:message key="chiefProgrammer"/></b></td>
				<td>Mario Lavandero</td>
			</tr>
			<tr></tr>
			<tr>
				<td><b><fmt:message key="programmers"/></td>
				<td>Oliver Cordero</td>
			</tr>
			<tr>
				<td></td>
				<td>Daniel Estévez</td>
			</tr>
			<tr>
				<td></b></td>
				<td>Julio Olivares</td>
			</tr>
		</table> 
		<br>
		<a href="<c:url value="login.htm"/>"><fmt:message key="logIn"/></a>
		</div>
		<jsp:include page="/footer.htm"></jsp:include>
	</body>
</html>