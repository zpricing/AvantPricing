<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<select name="${name }" <c:if test="${multiple}">multiple="multiple"</c:if>>
	<c:forEach items="${dominioCompleto}" var="elemento">
		<option value="${elemento.value}">
		<c:out value="${elemento.nombre}" /> </option>
	</c:forEach>
</select>