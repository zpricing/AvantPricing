<%@tag description="display the whole grupoComplejo" pageEncoding="UTF-8"%>
<%@attribute name="grupo" type="cl.zpricing.revman.model.GrupoComplejo" required="true" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>
<li>${grupo.descripcion}
<c:if test="${fn:length(grupo.hijos) > 0}">
    <ul>
    <c:forEach var="hijo" items="${grupo.hijos}">
        <template:grupoComplejoTree node="${hijo}"/>
    </c:forEach>
    </ul>
</c:if>
</li>