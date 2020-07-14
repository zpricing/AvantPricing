<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="pageTitle" /></title>

<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="form"/>" />
	<link rel="stylesheet" type="text/css" media="screen"
	href="<spring:theme code="tablas_admin"/>" />


<style>

.error {
	color: red;
}
#popup
   {
      display:none;
      position: absolute;
      border: 1px solid #666666;
	  background-color:white;
	  color:graytext;
      float:left;
      left: 400px;
      top: 300px;
      padding: 10px;
      text-align: center;
      position:fixed;  
      width:200px;
      height: 60px;
	  left:50%;
	  margin-left:-100px;
	  top:50%;
	  margin-top:-100px;
	  padding: 10px;
   }

</style>

</head>
<body>
<jsp:include page="/menu2.htm"></jsp:include>
<div id="contenedor">
<span id="titulo"><fmt:message key="uploadNielsen" /></span> 

<fieldset>
<legend><fmt:message key="files"></fmt:message></legend>

<c:if test="${exito!=null && exito==true }">
	<fmt:message key="success.Kettle"/>
	<ul>
	<c:forEach items="${listaExitosos}" var="archivoExitoso">
		<li><c:out value="${archivoExitoso}"/></li>
	</c:forEach>
	</ul>
</c:if>

<c:if test="${errores!=null && errores==true }">
	<fmt:message key="error.KettleException"/>
	<ul>
	<c:forEach items="${listaErrores}" var="archivoErroneo">
		<li><c:out value="${archivoErroneo}"/></li>
	</c:forEach>
	</ul>
</c:if>

<form:form commandName="SubirArchivo" enctype="multipart/form-data" method="post"> 
<% int i = 0; %>
<% for (i = 0; i < 10; i++) { %>
<p><input id="subidor<%=i %>" type="file" name="archivo<%=i %>"  size="45" /></p>
<% } %>

<input name="cantidadArchivos" type="hidden" value="<%=i %>"/> 

<input class="boton" type="submit" value="<fmt:message key="upload"/>"/>
</form:form>

</fieldset>
</div>
<jsp:include page="/footer.htm"></jsp:include>
</body>
<div id="popup">
&nbsp;<fmt:message key="processing" /><br>
<img align="middle" alt="s" src="<c:url value="/images/loading_page.gif"/>">
</div>
</html>
