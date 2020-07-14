<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<h1><fmt:message key="menu.priceTemplates"></fmt:message></h1>

<fmt:message key="complex" />: ${grupo.complejo.nombre} <br/>
<fmt:message key="templateGroup" />: ${grupo.descripcion} <br/>
<fmt:message key="template" />: ${plantilla.descripcion} <br/>
<br />
<h2><fmt:message key="add" /> <fmt:message key="price" /> &lt;-&gt; <fmt:message key="area" /></h2>

<form:form commandName="plantillasAddPrecio">
	<form:select path="claseId">
		<form:options items="${clases}" itemLabel="precio" itemValue="id"/>
	</form:select>
	&lt;-&gt;
	<form:select path="areaId">
		<form:options items="${areas}" itemLabel="descripcion" itemValue="id"/>
	</form:select>
	
	<input type="submit" name="agregar" value="<fmt:message key="add"/>" />
	
</form:form>