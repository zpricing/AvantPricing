package cl.zpricing.avant.webservices;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import cl.zpricing.avant.webservices.model.RevenueManagementAvailabilityResponse;
import cl.zpricing.avant.webservices.model.RevenueManagementMovieAvailabilityResponse;

@WebService
public interface ZhetaPricingServices {
	public RevenueManagementAvailabilityResponse getRevenueManagementAvailability(@WebParam(name="cinemaId") String cinemaId,@WebParam(name="sessionId") String sessionId);
	
	public RevenueManagementMovieAvailabilityResponse getRevenueManagementAvailabilityPerDay(@WebParam(name="cinemaId") String cinemaId, @WebParam(name="movieId") String movieId, @WebParam(name="date") String date);
	
	@WebResult(name="respuesta")
	public String updateRevenueManagementAvailability(@WebParam(name="cinemaId") String cinemaId, @WebParam(name="sessionId") String sessionId, @WebParam(name="ticketId") String ticketId, @WebParam(name="tipoTransaccion") String tipoTransaccion, @WebParam(name="cantidad") int cantidad );
}
