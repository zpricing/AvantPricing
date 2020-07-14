<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
<head></head>
<body>
	<h1>Ventas Revenue de <fmt:formatDate value="${fecha}" type="both" pattern="EEEE dd-MM-yyyy" /></h1>
	<table width="700px">
		<thead>
			<tr>
				<th>Hora Transaccion</th>
				<th>Ticket</th>
				<th>Cantidad</th>
				<th>Venta</th>
				<th>Fecha Funcion</th>
				<th>pelicula</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${transacciones}" var="transaccion">
				<tr>
					<td>
						<fmt:formatDate value="${transaccion.fecha}" type="both" pattern="HH:mm" />
					</td>
					<td><c:out value="${transaccion.ticket.descripcion}"/></td>
					<td><c:out value="${transaccion.cantidad}"/></td>
					<td>$ <fmt:formatNumber value="${transaccion.venta}" maxFractionDigits="0" minFractionDigits="0" type="number"/></td>
					<td>
						<fmt:formatDate value="${transaccion.funcion.fecha}" type="both" pattern="EEEE dd-MM-yyyy HH:mm" />
					</td>
					<td>
						<c:out value="${transaccion.funcion.peliculaAsociada.descripcion}"/>
					</td>
				</tr>		
			</c:forEach>
		</tbody>
	</table>	
</body>
</html>