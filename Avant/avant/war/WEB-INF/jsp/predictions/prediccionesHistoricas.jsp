<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title><fmt:message key="pageTitle"/></title>
		
		<script type="text/javascript" src="<c:url value="/js/background.js" />" >No pesca javascript</script>
		<script type="text/javascript" src="<c:url value="/js/popup.js" />" >No pesca javascript</script>
		
		<link rel="stylesheet" type="text/css" media="screen"
			href="<spring:theme code="form"/>" />
		<link rel="stylesheet" type="text/css" media="screen"
			href="<spring:theme code="tablas_admin"/>" />
	</head>
	<body>
	
		<script>
        	var popup = new Popup();
	    </script>
		
		<jsp:include page="/menu2.htm"></jsp:include>
		
		<div id="contenedor"><span id="titulo"><fmt:message key="predictions"/></span> <br>
			<table align="center">
				<tr>
					<td>
						<c:if test="${pagina-1!=0}">
							<a href="<c:url value="prediccionesHistoricas.htm?page=${pagina-1}" />">
								&lt;&lt; <fmt:message key="previous"></fmt:message>
							</a>
						</c:if>
					</td>
					<td>
						<c:out value="${pagina}" />
					</td>
					<td>
						<a href="<c:url value="prediccionesHistoricas.htm?page=${pagina+1}" />">
								<fmt:message key="next"></fmt:message> >>
							</a>
					</td>
				</tr>
			</table>
			<table id="rounded-corner">
				<thead>
					<tr>
						<th scope="col" class="rounded-company">
							<fmt:message key="id"/>
						</th>
						<th scope="col" class="rounded-q1">
							<fmt:message key="movie"/>
						</th>
						<th scope="col" class="rounded-q1">
							<fmt:message key="complex"/>
						</th>
						<th scope="col" class="rounded-q1">
							<fmt:message key="date"/>
						</th>
						<th scope="col" class="rounded-q1">
							<fmt:message key="user"/>
						</th>
						<th scope="col" class="rounded-q4"/>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${predicciones}" var="pf">
						<tr style="cursor: pointer;" >
							<td>
								<a href="mostrarPrediccionHistoricaDiaria.htm?idPrediccion=${pf.idPrediccion}">
									<c:out value="${pf.idPrediccion}"></c:out>
								</a>
							</td>
							<td>
								<c:out value="${pf.pelicula}"></c:out>
								<a href="cargaPrediccionHistorica.htm?prediccion_id=${pf.idPrediccion}">
									<img src="images/server_go.png" border="0"/>
								</a>
							</td>
							<td>
								<c:out value="${pf.complejo}"></c:out>
							</td>
							<td>
								<fmt:formatDate type="both" value="${pf.fecha}" pattern="dd-MM-yyyy HH:mm"/>
							</td>
							<td>
								<c:out value="${pf.usuario.nombreCompleto }" />
							</td>
							<td>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="5" class="rounded-foot-left">&nbsp;</td>
						<td class="rounded-foot-right">&nbsp;</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</body>
</html>