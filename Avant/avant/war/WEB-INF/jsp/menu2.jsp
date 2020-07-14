<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
	<head>
		<title><fmt:message key="pageTitle"/></title>
		<style>
			.success { color: green; }
			
			div#footer{
			 
			 margin-top:30px;
			  color:#202020;
			  font-size: 10px;
			  z-index: -4000;
			}
		</style>

		<link rel="stylesheet" type="text/css" media="screen" href="<spring:theme code="menu_style"/>" />
	</head>
	<body style="background-color: black;">

		<div style="background-color: white;">
			<img style="margin: 15px;" alt="Logo ZP" src="<c:url value="/images/LogoZP.PNG"/>" border="0"/>

			<div align="right" style="margin-top: -84px;margin-right: 5px; width: auto;">
				<fieldset style="width:170px; color: gray;font-size: 12px; font-family: 'Lucida Sans Unicode', 'Lucida Grande', Sans-Serif;">
					<legend><fmt:message key="user"/></legend>
					<a href="<c:url value="perfil.htm"/>"><c:out value="${user.nombreCompleto}"></c:out></a><br>
					<c:out value="${user.rol.rol}"></c:out><br/>
					<a href="j_spring_security_logout"><fmt:message key="logout"/></a>
				</fieldset>
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
			</ul>
		</div>
