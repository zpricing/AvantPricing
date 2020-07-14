<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
	<head>
		<title><fmt:message key="pageTitle"/></title>
		<style>
			.success { color: green; }
		</style>
	</head>
	<body>
	<table bgcolor="f8f8ff">
		<tr>
		<td><fmt:message key="user"/></td><td>${map.user}</td>
		</tr>
		<tr>
		<td><fmt:message key="role"/></td><td>${map.rol}</td>
		</tr>
		</table>
		
		<h1><fmt:message key="menu"/></h1>
		<c:forEach items="${map.links}" var="link">
			<a href="<c:url value="${link.link}"/>"> <fmt:message key="${link.title}"/></a><br>
		</c:forEach>
		<br>
		<a href="<c:url value="/j_spring_security_logout"/>"><fmt:message key="logOut"/></a>
		<br>
		
		
	</body>
</html>