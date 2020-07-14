<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

GrupoPlantillas

<c:forEach	items="${grupoPlantillas}" var="grupo">
	<c:out value="${grupo.timeSpan.descripcion}"></c:out> - 
	<c:out value="${grupo.descripcion}"></c:out><br />
	----------------------------- <br />
</c:forEach>