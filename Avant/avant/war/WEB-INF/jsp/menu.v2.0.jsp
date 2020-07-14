<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
	<head>
		<title><fmt:message key="pageTitle"/></title>
		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="style.v2.0"/>" />
		<script type="text/javascript" src="<c:url value="javascript.v2.0/script.js"></c:url>"></script>
	</head>

	<body>
		<div id="cabecera">
			<div id="logo">
				<a href="#"><img src="<c:url value="images.v2.0/logo.png"></c:url>" alt="ZP Cinemas" width="107" height="128" border="0" /></a>
			</div>
			<div id="login">
				<img src="<c:url value="images/useravatar.png"></c:url>" width="16" height="16" alt="user" /> <a href="<c:url value="perfil.htm"/>">Bienvenido <c:out value="${user.nombreCompleto}"></c:out></a> <img src="<c:url value="images/separate.png"></c:url>" width="3" height="13" /> <a href="<c:url value="/j_spring_security_logout"/>">Logout</a>
			</div>  
		</div>
	
		
		
	
	
	</body>


<div style="background-color: white;">
<img style="margin: 15px;" alt="Logo ZP" src="<c:url value="/images/LogoZP.PNG"/>"/>

<div align="right" style="margin-top: -84px;margin-right: 5px; width: auto;">
<fieldset style="width:170px; color: gray;font-size: 12px; font-family: 'Lucida Sans Unicode', 'Lucida Grande', Sans-Serif;">
<legend><fmt:message key="user"/></legend>
<c:out value="${user.nombreCompleto}"></c:out><br>
<c:out value="${user.email}"></c:out><br>
<c:out value="${user.rol.rol}"></c:out>
</fieldset>
<br>
</div>

</div>

<div id="outside">
<ul id="navigation-1">
<c:forEach items="${map}" var="map_parent">
   <li><a href="#" title="<c:url value="${map_parent.header}"/>"> <fmt:message key="${map_parent.header}"/></a>
      <ul class="navigation-2">
         <c:forEach items="${map_parent.lista_elementos}" var="menuElement">
         	<li><a href="<c:url value="${menuElement.link}"/>" target="_self" ><fmt:message key="${menuElement.title}"/></a></li>
         </c:forEach>
      </ul>
   </li>
   </c:forEach>
   <li><a href="#" title="Usuario"><fmt:message key="user"/></a>
      <ul class="navigation-2">
         <li><a href="perfil.htm" target="_self" ><fmt:message key="profile"/></a></li>
         <li><a href="<c:url value="/j_spring_security_logout"/>" target="_self" ><fmt:message key="logOut"/></a></li>
      </ul>
   </li>
</ul>
</div>
	</body>
</html>

