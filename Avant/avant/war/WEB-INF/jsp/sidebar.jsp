<%@ include file="/WEB-INF/jsp/include.jsp" %>

<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/interface/dwrSideBarAlerts.js"/>'></script>
<script type="text/javascript">
	
	function countAlerts() {
		dwrSideBarAlerts.countAlerts({ callback:handlerCountAlerts, 
		errorHandler:handlerErrorCountAlerts});
	}

	function handlerCountAlerts(count) {
		$("#alertContainer").empty();
		if(count > 0) {
			$("#alertContainer").html("<div class=\"alert badge alert_red\">"+count+"</div>");
		}
	}

	function handlerErrorCountAlerts(message) {
		alert(message);
	}

	$(document).ready(function() { countAlerts(); });
	window.setInterval(countAlerts, 30000);
</script>

<div id="sidebar" class="sidebar pjax_links">
	<div class="cog">+</div>
	<a href="dashboard.htm" class="logo"><span>ZhetaPricing</span></a>

	<div class="user_box dark_box clearfix">
		<img src="assets/adminica/images/interface/profile.jpg" width="55" alt="Profile Pic" />
		<h2><c:out value="${user.rol.rol}"></c:out></h2>
		<h3><a href="<c:url value="perfil.htm"/>"><c:out value="${user.nombreCompleto}"></c:out></a></h3>
		<ul>
			<li><a href="j_spring_security_logout"><fmt:message key="logout"/></a></li>
		</ul>
	</div><!-- #user_box -->

	<ul class="side_accordion " id="nav_side"> 
		<li><a id="dashboardTab" href="dashboard.htm"><img src="assets/adminica/images/icons/small/grey/laptop.png"/>Dashboard<div id="alertContainer"></div></a>
		<c:forEach items="${map}" var="map_parent">
			<li><a href="#"><img src="assets/adminica/images/icons/small/grey/<c:out value="${map_parent.header}"></c:out>.png"/><fmt:message key="${map_parent.header}"/></a>
				<ul class="drawer">
					<c:forEach items="${map_parent.lista_elementos}" var="menuElement">
						<li><a href="<c:url value="${menuElement.link}"/>"><fmt:message key="${menuElement.title}"/></a></li>
					</c:forEach>
				</ul>
			</li>
		</c:forEach>
	</ul>
	<div align="center">
		<span style="padding-top: 10px; font-size: 10px;">Avant Pricing - <c:out value="${version}"></c:out> - <c:out value="${year}"></c:out></span>
	</div>
</div><!-- #sidebar -->