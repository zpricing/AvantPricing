<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" type="text/css" media="screen"
     href="<spring:theme code="showmarkers"/>" />
     
<script type="text/javascript" src="<c:url value="/js/showhide.js" />" > </script>

<div class="ModMarkers">
	<h4><fmt:message key="markers" /></h4>
	<div>
		<div><fmt:message key="id"/></div>
		<div><fmt:message key="description"/></div>
		<div><fmt:message key="markerType"/></div>
	</div>
	<c:forEach items="${map.markers}" var="m">
		<div class="marker">
			<div class="titulo" onclick="javascript:showhide(<c:out value="${m.id}" />);">
				<fmt:formatDate value="${m.fecha}" pattern="dd-MM-yyyy"/>
				<c:if test="${m.fechaHasta != null}" >
					->
					<fmt:formatDate value="${m.fechaHasta}" pattern="dd-MM-yyyy"/>
				</c:if>
				&nbsp;|&nbsp;
				<c:out value="${m.tipoMarker.descripcion}"></c:out>
			</div>
			<div id="<c:out value="${m.id}" />" class="descripcion">
				<c:out value="${m.descripcion}"></c:out>
			</div>
		</div>
	</c:forEach>
</div>