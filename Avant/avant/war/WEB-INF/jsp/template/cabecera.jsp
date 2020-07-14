<%@ include file="/WEB-INF/jsp/include.jsp" %>

<!-- CABECERA -->
<div id="cabecera">
	<div id="logo">
		<a href="#"><img src="<c:url value="images.v2.0/logo.png"></c:url>" alt="ZP Cinemas" width="107" height="128" border="0" /></a>
	</div>
	<div id="login">
		<img src="<c:url value="images.v2.0/useravatar.png"></c:url>" width="16" height="16" alt="user" /><a href="<c:url value="perfil.htm"/>">Bienvenido <c:out value="${user.nombreCompleto}"></c:out></a><img src="<c:url value="images.v2.0/separate.png"></c:url>" width="3" height="13" /> <a href="<c:url value="/j_spring_security_logout"/>">Logout</a>
	</div>  
</div>
<!-- FIN CABECERA -->
	
