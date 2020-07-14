<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="ISO-8859-1"?>
<advancedSellingAvailabilityResponse>
	<message>${response.message}</message>
	<cinemaId>${cinemaId}</cinemaId>
	<movieId>${movieId}</movieId>
	<transactionId>${response.transactionId }</transactionId>
	<c:forEach items="${response.sessionAvailability}" var="sessionAvailability">
	<sessionAvailability>
		<session>${sessionAvailability.sessionId}</session>
		<c:forEach items="${sessionAvailability.ticketAvailability}" var="ticketAvailability">
		<ticketAvailability>
			<ticketTypeCode>${ticketAvailability.ticketTypeCode}</ticketTypeCode>
			<numberOfTicketsAvailable>${ticketAvailability.purchase}</numberOfTicketsAvailable>
		</ticketAvailability>
		</c:forEach>
	</sessionAvailability>
	</c:forEach>
</advancedSellingAvailabilityResponse>