package cl.zpricing.avantors.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.zpricing.avantors.webservices.model.CancelTransactionResponse;
import cl.zpricing.avantors.webservices.model.LastMinuteFeaturedResponse;
import cl.zpricing.avantors.webservices.model.LastMinuteSellingAvailabilityResponse;
import cl.zpricing.avantors.webservices.model.RevenueManagementAvailabilityResponse;
import cl.zpricing.avantors.webservices.model.RevenueManagementMovieAvailabilityResponse;
import cl.zpricing.avantors.webservices.model.RevenueManagementUpdateAvailabilityResponse;
import cl.zpricing.avantors.webservices.model.SecondSellingAvailabilityResponse;
import cl.zpricing.avantors.webservices.model.UpsellingAvailabilityResponse;
import cl.zpricing.revman.webservices.ZhetaPricingServices;

@Controller
public class RevenueManagementServicesController {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private ZhetaPricingServices zhetaPricingServices;
	
	@RequestMapping(value = "advancedSellingAvailability/cinemaId/{cinemaId}/movieId/{movieId}/date/{date}", method = RequestMethod.GET)
	public void advancedSellingAvailability(@PathVariable("cinemaId") String cinemaId, @PathVariable("movieId") String movieId, @PathVariable("date") String date, ModelMap model) {
		log.debug("Iniciando advancedSellingAvailability");
		log.debug("  cinemaId : " + cinemaId);
		log.debug("  movieId : " + movieId);
		log.debug("  date : " + date);
		model.addAttribute("cinemaId", cinemaId);
		model.addAttribute("movieId", movieId);
		model.addAttribute("date", date);
		
		RevenueManagementMovieAvailabilityResponse response = zhetaPricingServices.getAdvancedSellingAvailabilityByDate(cinemaId, movieId, date);
		
		log.debug("  response : " + response.getMessage());
		
		model.addAttribute("model", response);
	}
	
	@RequestMapping(value = "advancedSellingAvailability/cinemaId/{cinemaId}/sessionId/{sessionId}", method = RequestMethod.GET)
	public void advancedSellingAvailability(@PathVariable("cinemaId") String cinemaId, @PathVariable("sessionId") String sessionId, ModelMap model) {
		log.debug("Iniciando advancedSellingAvailability");
		log.debug("  cinemaId : " + cinemaId);
		log.debug("  sessionId : " + sessionId);
		
		RevenueManagementAvailabilityResponse response = zhetaPricingServices.getAdvancedSellingAvailability(cinemaId, sessionId);
		
		log.debug("  cantidad detalle cupos : " + response.getDetalleCupos().size());
		
		model.addAttribute("model", response);
	}
	
	@RequestMapping(value = "upsellingSellingSuggestions/cinemaId/{cinemaId}/sessionId/{sessionId}/numberOfTickets/{numberOfTickets}/ticketPrice/{ticketPrice}", method = RequestMethod.GET)
	public void upsellingSuggestions(@PathVariable("cinemaId") String cinemaId, @PathVariable("sessionId") String sessionId, @PathVariable("numberOfTickets") Integer numberOfTickets, @PathVariable("ticketPrice") String ticketPrice, ModelMap model) {
		log.debug("Iniciando upsellingSuggestions");
		log.debug("  cinemaId : " + cinemaId);
		log.debug("  sessionId : " + sessionId);
		log.debug("  numberOfTickets : " + numberOfTickets);
		log.debug("  ticketPrice : " + ticketPrice);
		
		UpsellingAvailabilityResponse response = zhetaPricingServices.getUpsellingSuggestions(cinemaId, sessionId, numberOfTickets, ticketPrice);
		log.debug("  message : " + response.getMessage());
		model.addAttribute("model", response);
	}
	
	@RequestMapping(value = "secondSellingSuggestions/cinemaId/{cinemaId}/sessionId/{sessionId}/ticketPrice/{price}/numberOfTickets/{numberOfTickets}/userId/{userId}", method = RequestMethod.GET)
	public void secondSellingSuggestions(@PathVariable("cinemaId") String cinemaId, @PathVariable("sessionId") String sessionId, @PathVariable("price") String price, @PathVariable("numberOfTickets") Integer numberOfTickets, @PathVariable("userId") String userId, ModelMap model) {
		log.debug("Iniciando secondSellingSuggestions");
		log.debug("  cinemaId : " + cinemaId);
		log.debug("  sessionId : " + sessionId);
		log.debug("  userId : " + userId);
		
		SecondSellingAvailabilityResponse response = zhetaPricingServices.getSecondSellingSuggestions(cinemaId, sessionId, price, numberOfTickets, userId);
		log.debug("  message : " + response.getMessage());
		model.addAttribute("model", response);
	}
	
	@RequestMapping(value = "lastMinuteSuggestions/cinemaId/{cinemaId}/date/{date}", method = RequestMethod.GET)
	public void lastMinuteSuggestions(@PathVariable("cinemaId") String cinemaId, @PathVariable("date") String date, ModelMap model) {
		log.debug("Iniciando lastMinuteSuggestions");
		log.debug("  cinemaId : " + cinemaId);
		log.debug("  date : " + date);
		
		LastMinuteSellingAvailabilityResponse response = zhetaPricingServices.getLastMinuteSellingSuggestions(cinemaId, date);
		log.debug("  message : " + response.getMessage());
		model.addAttribute("model", response);
	}
	
	@RequestMapping(value = "lastMinuteSuggestions/cinemaId/{cinemaId}", method = RequestMethod.GET)
	public void lastMinuteSuggestionsByCinema(@PathVariable("cinemaId") String cinemaId, ModelMap model) {
		log.debug("Iniciando lastMinuteSuggestions by cinema");
		log.debug("  cinemaId : " + cinemaId);
		
		LastMinuteSellingAvailabilityResponse response = zhetaPricingServices.getLastMinuteSellingSuggestions(cinemaId, "");
		log.debug("  message : " + response.getMessage());
		model.addAttribute("model", response);
	}
	@RequestMapping(value = "lastMinuteSuggestions/date/{date}", method = RequestMethod.GET)
	public void lastMinuteSuggestionsByDate(@PathVariable("date") String date, ModelMap model) {
		log.debug("Iniciando lastMinuteSuggestions By Date");
		log.debug("  date : " + date);
		
		LastMinuteSellingAvailabilityResponse response = zhetaPricingServices.getLastMinuteSellingSuggestions("", date);
		log.debug("  message : " + response.getMessage());
		model.addAttribute("model", response);
	}
	@RequestMapping(value = "lastMinuteSuggestions", method = RequestMethod.GET)
	public void lastMinuteSuggestions(ModelMap model) {
		log.debug("Iniciando lastMinuteSuggestions Without Parameters");
		
		LastMinuteSellingAvailabilityResponse response = zhetaPricingServices.getLastMinuteSellingSuggestions("", "");
		log.debug("  message : " + response.getMessage());
		model.addAttribute("model", response);
	}
	
	@RequestMapping(value = "lastMinuteFeatured/date/{date}", method = RequestMethod.GET)
	public void lastMinuteFeaturedSuggestion(@PathVariable("date") String date, ModelMap model) {
		log.debug("Iniciando lastMinuteFeaturedSuggestion");
		LastMinuteFeaturedResponse response = zhetaPricingServices.getFeaturedLastMinuteSellingSuggestion(date);
		log.debug("  message : " + response.getMessage());
		model.addAttribute("model", response);
	}
	
	@RequestMapping(value = "secondSellingSuggestions/cinemaId/{cinemaId}/sessionId/{sessionId}/ticketPrice/{price}/numberOfTickets/{numberOfTickets}", method = RequestMethod.GET)
	public void secondSellingSuggestions(@PathVariable("cinemaId") String cinemaId, @PathVariable("sessionId") String sessionId, @PathVariable("price") String price, @PathVariable("numberOfTickets") Integer numberOfTickets, ModelMap model) {
		this.secondSellingSuggestions(cinemaId, sessionId, price, numberOfTickets, "", model);
	}
	
	@RequestMapping(value = "updateAvailability/cinemaId/{cinemaId}/sessionId/{sessionId}/ticketId/{ticketId}/numberOfTickets/{numberOfTickets}/transactionId/{transactionId}", method = RequestMethod.POST)
	public void updateRevenueManagementAvailability(@PathVariable("cinemaId") String cinemaId, @PathVariable("sessionId") String sessionId, @PathVariable("ticketId") String ticketId, @PathVariable("numberOfTickets") int numberOfTickets, @PathVariable("transactionId") String transactionId, ModelMap model) {
		log.debug("Iniciando updateRevenueManagementAvailability");
		log.debug("  cinemaId : " + cinemaId);
		log.debug("  sessionId : " + sessionId);
		log.debug("  ticketId : " + ticketId);
		log.debug("  cantidad : " + numberOfTickets);
		log.debug("  transactionId : " + transactionId);
		
		RevenueManagementUpdateAvailabilityResponse response = zhetaPricingServices.finishTransaction(cinemaId, sessionId, ticketId, numberOfTickets, transactionId, "");
		log.debug("  message : " + response.getMessage());
		model.addAttribute("model", response);
	}
	
	@RequestMapping(value = "cancelTransaction/transactionId/{transactionId}", method = RequestMethod.POST)
	public void cancelTransaction(@PathVariable("transactionId") String transactionId, ModelMap model) {
		log.debug("Iniciando cancelTransaction");
		log.debug("  transactionId : " + transactionId);
		
		String responseMessage = zhetaPricingServices.cancelTransaction(transactionId);
		log.debug("  message : " + responseMessage);
		
		CancelTransactionResponse response = new CancelTransactionResponse(responseMessage);
		model.addAttribute("model", response);
	}

	public void setZhetaPricingServices(ZhetaPricingServices zhetaPricingServices) {
		this.zhetaPricingServices = zhetaPricingServices;
	}
}
