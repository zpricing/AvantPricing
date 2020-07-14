package cl.zpricing.revman.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import cl.zpricing.revman.webservices.model.LastMinuteSellingAvailabilityResponse;
import cl.zpricing.revman.webservices.model.RevenueManagementAvailabilityResponse;
import cl.zpricing.revman.webservices.model.RevenueManagementMovieAvailabilityResponse;
import cl.zpricing.revman.webservices.model.RevenueManagementUpdateAvailabilityResponse;
import cl.zpricing.revman.webservices.model.SecondSellingAvailabilityResponse;
import cl.zpricing.revman.webservices.model.UpsellingAvailabilityResponse;

@WebService
public interface ZhetaPricingServices {
	public final String TIPO_UPSELLING = "US";
	public final String TIPO_SECONDSELLING = "SECONDSELLING";
	public final String TIPO_RM = "RM";
	
	@Deprecated
	@WebMethod
	public RevenueManagementAvailabilityResponse getRevenueManagementAvailability(@WebParam(name="cinemaId") String cinemaId,@WebParam(name="sessionId") String sessionId);
	@WebMethod
	public RevenueManagementAvailabilityResponse getAdvancedSellingAvailability(@WebParam(name="cinemaId") String cinemaId,@WebParam(name="sessionId") String sessionId);
	
	@Deprecated
	@WebMethod
	public RevenueManagementMovieAvailabilityResponse getRevenueManagementAvailabilityPerDay(@WebParam(name="cinemaId") String cinemaId, @WebParam(name="movieId") String movieId, @WebParam(name="date") String date);
	@WebMethod
	public RevenueManagementMovieAvailabilityResponse getAdvancedSellingAvailabilityByDate(@WebParam(name="cinemaId") String cinemaId, @WebParam(name="movieId") String movieId, @WebParam(name="date") String date);
	
	@Deprecated
	@WebResult(name="respuesta")
	public RevenueManagementUpdateAvailabilityResponse updateRevenueManagementAvailability(@WebParam(name="cinemaId") String cinemaId, @WebParam(name="sessionId") String sessionId, @WebParam(name="ticketId") String ticketId, @WebParam(name="tipoTransaccion") String tipoTransaccion, @WebParam(name="cantidad") int cantidad, String transactionId);
	@WebMethod
	public RevenueManagementUpdateAvailabilityResponse finishTransaction(@WebParam(name="cinemaId") String cinemaId, @WebParam(name="sessionId") String sessionId, @WebParam(name="ticketId") String ticketId, @WebParam(name="numberOfTickets") int numberOfTickets, @WebParam(name="transactionId") String transactionId, @WebParam(name="userId") String userId);
	
	@WebMethod
	@WebResult(name="response")
	public String cancelTransaction(@WebParam(name="transaccionId") String transaccionId);
	@WebMethod
	public UpsellingAvailabilityResponse getUpsellingSuggestions(@WebParam(name="cinemaId") String cinemaId,@WebParam(name="sessionId") String sessionId, @WebParam(name="numberOfTickets") Integer numberOfTickets, @WebParam(name="ticketPrice") String ticketPrice);
	@WebMethod
	public SecondSellingAvailabilityResponse getSecondSellingSuggestions(@WebParam(name="cinemaId") String cinemaId,@WebParam(name="sessionId") String sessionId, @WebParam(name="ticketPrice") String price, @WebParam(name="numberOfTickets") Integer numberOfTickets, @WebParam(name="userId") String userId);
	@WebMethod
	public LastMinuteSellingAvailabilityResponse getLastMinuteSellingSuggestions(@WebParam(name="cinemaId") String cinemaId, @WebParam(name="date") String date);
	
}
