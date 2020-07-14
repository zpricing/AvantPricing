<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:forEach items="${dato}" var="d">
	<c:out value="${d.id}"></c:out> - <c:out value="${d.timestamp}"></c:out> - 
	<c:out value="${d.descripcion}"></c:out><br/>
</c:forEach>