package cl.zpricing.revman.webservices;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import cl.zpricing.avantors.exceptions.ParameterNotFoundException;
import cl.zpricing.avantors.model.Availability;
import cl.zpricing.avantors.model.Parametro;
import cl.zpricing.avantors.model.SecondSelling;
import cl.zpricing.avantors.model.Session;
import cl.zpricing.avantors.model.Statistic;
import cl.zpricing.avantors.model.StatisticLastMinuteSelling;
import cl.zpricing.avantors.model.StatisticRevenueManagement;
import cl.zpricing.avantors.model.StatisticSecondSelling;
import cl.zpricing.avantors.model.StatisticUpSelling;
import cl.zpricing.avantors.model.Transaccion;
import cl.zpricing.avantors.model.Upselling;
import cl.zpricing.avantors.webservices.model.CapacidadCupos;
import cl.zpricing.avantors.webservices.model.CapacidadCuposPorPeliculaYDia;
import cl.zpricing.avantors.webservices.model.LastMinuteFeaturedResponse;
import cl.zpricing.avantors.webservices.model.LastMinuteSellingAvailabilityResponse;
import cl.zpricing.avantors.webservices.model.LastMinuteSuggestion;
import cl.zpricing.avantors.webservices.model.RevenueManagementAvailabilityResponse;
import cl.zpricing.avantors.webservices.model.RevenueManagementMovieAvailabilityResponse;
import cl.zpricing.avantors.webservices.model.RevenueManagementUpdateAvailabilityResponse;
import cl.zpricing.avantors.webservices.model.SecondSellingAvailabilityResponse;
import cl.zpricing.avantors.webservices.model.SecondSellingSuggestion;
import cl.zpricing.avantors.webservices.model.UpsellingAvailabilityResponse;
import cl.zpricing.avantors.webservices.model.UpsellingSuggestion;
import cl.zpricing.commons.utils.DateUtils;
import cl.zpricing.commons.utils.ErroresUtils;

@WebService(endpointInterface = "cl.zpricing.revman.webservices.ZhetaPricingServices", serviceName = "ZhetaPricingServices")
public class ZhetaPricingServicesImpl implements ZhetaPricingServices {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private MongoOperations mongoOperation;
	
	private final String ESTADO_COMPRADO = "comprado";
	private final String ESTADO_ANULADO = "anulado";
	
	private final String MESSAGE_SUCCESS = "Success";
	private final String MESSAGE_NO_RESULTS = "No results found";
	
	@Override
	public RevenueManagementAvailabilityResponse getRevenueManagementAvailability(String cinemaId, String sessionId) {
		return this.getAdvancedSellingAvailability(cinemaId, sessionId);
	}
	
	@Override
	public RevenueManagementAvailabilityResponse getAdvancedSellingAvailability(String cinemaId, String sessionId) {
		RevenueManagementAvailabilityResponse response = new RevenueManagementAvailabilityResponse();
		try {
			log.debug("getRevenueManagementAvailability");
			log.debug("  cinemaId : [" + cinemaId + "]");
			log.debug("  sessionId : [" + sessionId + "]");
			Session session = mongoOperation.findOne(new Query(Criteria.where("cinemaId").is(cinemaId).and("sessionId").is(sessionId)), Session.class);
			if (session == null) {
				response.setMessage(this.MESSAGE_NO_RESULTS);
				return response;
			}
			log.debug(session);
			log.debug("  session movieId : [" + session.getMovieId() + "]");
			log.debug("  session # availability : [" + session.getAvailability().size() + "]");
			boolean availableSlots = false;
			for (Availability availability : session.getAvailability()) {
				if (availability.getType().equalsIgnoreCase(this.TIPO_RM)) {
					CapacidadCupos capacidadCupos = new CapacidadCupos();
					capacidadCupos.setTicketTypeCode(availability.getTicketTypeId());
					capacidadCupos.setPurchase(availability.getAvailable());
					capacidadCupos.setBooking(0);

					response.getDetalleCupos().add(capacidadCupos);
					if (availability.getAvailable() > 0) {
						availableSlots = true;
					}
				}
			}
			response.setMessage(this.MESSAGE_SUCCESS);
			
			if (availableSlots) {
				StatisticRevenueManagement statistic = new StatisticRevenueManagement(cinemaId, sessionId);
				mongoOperation.save(statistic);
				response.setTransactionId(statistic.getId());
			}
			else {
				response.setMessage(this.MESSAGE_NO_RESULTS);
			}
			
			return response;
		}
		catch (Exception e) {
			response.setMessage(e.getMessage());
			log.error(ErroresUtils.extraeStackTrace(e));
			return new RevenueManagementAvailabilityResponse();
		}
	}
	
	@Override
	public RevenueManagementMovieAvailabilityResponse getRevenueManagementAvailabilityPerDay(String cinemaId, String movieId, String date) {
		return this.getAdvancedSellingAvailabilityByDate(cinemaId, movieId, date);
	}
	
	@Override
	public RevenueManagementMovieAvailabilityResponse getAdvancedSellingAvailabilityByDate(String cinemaId, String movieId, String date) {
		RevenueManagementMovieAvailabilityResponse response = new RevenueManagementMovieAvailabilityResponse();
		try {
			log.debug("cinemaId : " + cinemaId);
			log.debug("movieId : " + movieId);
			log.debug("date : " + date);
			response.setMovieId(movieId);
			response.setCinemaId(cinemaId);
			Date sessionDate = DateUtils.getUTCdate(date, DateUtils.format_yyyyMMdd);
			
			boolean availableSlots = false;
			
			log.debug("  sessionDate : " + sessionDate);
			
			List<Session> sessionList = mongoOperation.find(new Query(Criteria.where("cinemaId").is(cinemaId).and("movieId").is(movieId).and("date").is(sessionDate).and("eliminada").is(false)), Session.class);
			
			log.debug("  # de funciones encontradas de mongo: " + sessionList.size());
			
			Map<String, CapacidadCuposPorPeliculaYDia> availabilityPerSession = new HashMap<String, CapacidadCuposPorPeliculaYDia>();
			
			for (Session session : sessionList) {
				availabilityPerSession.put(session.getSessionId(), new CapacidadCuposPorPeliculaYDia(session.getSessionId()));
				
				for (Availability availability : session.getAvailability()) {
					if (availability.getType().equalsIgnoreCase(this.TIPO_RM)) {
						CapacidadCupos capacidadCupos = new CapacidadCupos();
						capacidadCupos.setTicketTypeCode(availability.getTicketTypeId());
						capacidadCupos.setPurchase(availability.getAvailable());
						capacidadCupos.setBooking(0);
						availabilityPerSession.get(session.getSessionId()).getTicketAvailability().add(capacidadCupos);
						if (!availableSlots && availability.getAvailable() > 0) {
							availableSlots = true;
						}
					}
				}
			}
			response.parseSessionAvailabilityFromMap(availabilityPerSession);
			response.setMessage(this.MESSAGE_SUCCESS);
			
			log.debug("  # de funciones a responder : " + response.getSessionAvailability().size());
			
			if (availableSlots) {
				StatisticRevenueManagement statistic = new StatisticRevenueManagement(cinemaId, movieId, date);
				mongoOperation.save(statistic);
				response.setTransactionId(statistic.getId());
			}
			else {
				response.setMessage(this.MESSAGE_NO_RESULTS);
			}
		} catch (ParseException e) {
			response.setMessage("Date format must be 'YYYY-MM-DD'");
			log.error(ErroresUtils.extraeStackTrace(e));
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			log.error(ErroresUtils.extraeStackTrace(e));
		}
		return response;
	}
	
	@Override
	public RevenueManagementUpdateAvailabilityResponse updateRevenueManagementAvailability(String cinemaId, String sessionId, String ticketId, String tipoTransaccion, int cantidad, String transactionId) {
		return this.finishTransaction(cinemaId, sessionId, ticketId, cantidad, transactionId, "");
	}
	
	@Override
	public RevenueManagementUpdateAvailabilityResponse finishTransaction(String cinemaId, String sessionId, String ticketId, int numberOfTickets, String transactionId, String userId) {
		RevenueManagementUpdateAvailabilityResponse response = new RevenueManagementUpdateAvailabilityResponse();
		response.setTransactionId("");
		
		if (cinemaId.equalsIgnoreCase("")) {
			response.setMessage("Error: cinema can't be blank or null.");
			return response;
		}
		else if (sessionId.equalsIgnoreCase("")) {
			response.setMessage("Error: sessionId can't be blank or null.");
			return response;
		}
		else if (ticketId.equalsIgnoreCase("")) {
			response.setMessage("Error: ticketId can't be blank or null.");
			
		}
		else if (numberOfTickets <= 0) {
			response.setMessage("Error: cantidad must be greater than 0.");
		}
		else {
			Update updateAvailable = new Update();
			updateAvailable.inc("availability.$.available", (numberOfTickets * -1) );
			updateAvailable.inc("availability.$.occupied", numberOfTickets);
			WriteResult wr = mongoOperation.updateFirst(new Query(Criteria.where("cinemaId").is(cinemaId).and("sessionId").is(sessionId).and("availability.ticketTypeId").is(ticketId)), updateAvailable, Session.class);
			
			log.debug(" # updated : " + wr.getN());
			
			if(wr.getN() == 1) {
				String type = "";
				Transaccion transaccion = new Transaccion();
				if (transactionId != null && !transactionId.equalsIgnoreCase("")) {
					log.debug(" TransactionId Parameter : " + transactionId);
					Statistic statistic = mongoOperation.findOne(new Query(Criteria.where("id").is(transactionId)), Statistic.class);
					if (statistic != null) {
						statistic.setStatus("purchased");
						log.debug(" Stat type : " + statistic.getType());
						if (statistic instanceof StatisticSecondSelling) {
							log.debug(" stat instance of SecondSelling");
							((StatisticSecondSelling) statistic).setSelectedNumberOfTickets(numberOfTickets);
							((StatisticSecondSelling) statistic).setSelectedSessionId(sessionId);
							((StatisticSecondSelling) statistic).setSelectedTicketTypeId(ticketId);
						}
						mongoOperation.save(statistic);
						type = statistic.getType();
					}
				}
				
				transaccion.setId(transactionId);
				transaccion.setCinemaId(cinemaId);
				transaccion.setSessionId(sessionId);
				transaccion.setTicketTypeCode(ticketId);
				transaccion.setCantidad(numberOfTickets);
				transaccion.setCreatedAt(new Date());
				transaccion.setUpdatedAt(new Date());
				transaccion.setType(type);
				transaccion.setEstado(this.ESTADO_COMPRADO);
				
				mongoOperation.save(transaccion);
				
				log.debug("  Transaccion id : " + transaccion.getId());
				response.setTransactionId(transaccion.getId());
				response.setMessage(this.MESSAGE_SUCCESS);
			}
			else {
				response.setMessage("Error: could not update availability. session not found.");
			}
		}
		return response;
	}
	
	@Override
	public String cancelTransaction(String transaccionId) {
		try {
			log.debug("cancelTransaction");
			log.debug("  Transaccion id :" + transaccionId);
			
			Transaccion transaccion = mongoOperation.findAndModify(new Query(Criteria.where("id").is(transaccionId)), Update.update("estado", ESTADO_ANULADO), Transaccion.class);
			mongoOperation.updateFirst(new Query(Criteria.where("id").is(transaccionId)), Update.update("status", "annulled"), Statistic.class);
			
			log.debug("  transaccion.estado : " + transaccion.getEstado());
			
			if (transaccion.getEstado().equalsIgnoreCase("comprado")) {
				Update updateAvailable = new Update();
				updateAvailable.inc("availability.$.available", transaccion.getCantidad());
				updateAvailable.inc("availability.$.occupied", (transaccion.getCantidad() * -1));
				WriteResult wr = mongoOperation.updateFirst(new Query(Criteria.where("cinemaId").is(transaccion.getCinemaId()).and("sessionId").is(transaccion.getSessionId()).and("availability.ticketTypeId").is(transaccion.getTicketTypeCode())), updateAvailable, Session.class);
				if(wr.getN() == 1) {
					return this.MESSAGE_SUCCESS;
				}
				else {
					return  "Error: session not found.";
				}
			}
			else {
				return "Transaction already cancelled.";
			}
		} catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			return "Error";
		}
	}

	@Override
	public UpsellingAvailabilityResponse getUpsellingSuggestions(String cinemaId, String sessionId, Integer numberOfTickets, String ticketPrice) {
		log.debug("Iniciando getUpsellingSuggestions");
		log.debug("  cinemaId : " + cinemaId);
		log.debug("  sessionId : " + sessionId);
		log.debug("  numberOfTickets : " + numberOfTickets);
		log.debug("  ticketPrice : " + ticketPrice);
		
		BigDecimal userSelectedPrice = (new BigDecimal(ticketPrice)).movePointLeft(4);
		
		UpsellingAvailabilityResponse response = new UpsellingAvailabilityResponse(sessionId);
		response.setMessage(this.MESSAGE_SUCCESS);
		try {
			Session queriedSession = mongoOperation.findOne(new Query(Criteria.where("sessionId").is(sessionId).and("cinemaId").is(cinemaId)), Session.class);
			
			Parametro upSellingRMin = mongoOperation.findOne(new Query(Criteria.where("name").is("upselling_range_min").and("movie_format").is(queriedSession.getMovieFormat())), Parametro.class);
			Parametro upSellingRMax = mongoOperation.findOne(new Query(Criteria.where("name").is("upselling_range_max").and("movie_format").is(queriedSession.getMovieFormat())), Parametro.class);
			
			if (upSellingRMin == null || upSellingRMax == null) {
				throw new ParameterNotFoundException("Parametros de rango minimo y/o maximo de Upselling no configurados.");
			}
			double upSellingRangeMin = upSellingRMin.getValue().equals("") ? 0 : Double.parseDouble(upSellingRMin.getValue());
			double upSellingRangeMax = upSellingRMax.getValue().equals("") ? 0 : Double.parseDouble(upSellingRMax.getValue());
			
			log.debug("  upSellingRangeMin : " + upSellingRangeMin);
			log.debug("  upSellingRangeMax : " + upSellingRangeMax);
			log.debug("  userSelectedPrice : " + userSelectedPrice);
			
			List<Session> sessionList = mongoOperation.find(new Query(Criteria.where("cinemaId").is(cinemaId).and("upselling.sessionId").is(sessionId).and("eliminada").is(false)), Session.class);
			log.debug("Funciones de upselling encontradas : " + sessionList.size());			
			
			List<UpsellingSuggestion> upsellingSuggestions = new ArrayList<UpsellingSuggestion>();
			for (Session session : sessionList) {
				UpsellingSuggestion suggestion = new UpsellingSuggestion();
				
				suggestion.setSessionId(session.getSessionId());
				suggestion.setSessionTime(session.getTime());
				suggestion.setCinemaId(session.getCinemaId());
				suggestion.setMovieName(session.getMovieName());
				suggestion.setTicketPriceFull(session.getFullPrice());
				suggestion.setTicketBookingFeeFull(session.getFullPriceBookingFee());
				for (Upselling upSelling : session.getUpselling()) {
					if(upSelling.getSessionId().equals(sessionId)){
						suggestion.setOrder(upSelling.getOrden());
						break;
					}
				}
				
				Availability bestPriceAvailability = null;
				BigDecimal bestPriceWithBookingFee = null;
				for (Availability availability : session.getAvailability()) {
					BigDecimal ticketPriceWithBookingFee = availability.getTicketPriceWithBookingFee();
					log.debug("  Analizing ticket with price: " + ticketPriceWithBookingFee.toString());
					//Set the availability as total - occupied
					availability.setAvailable(availability.getTotal() - availability.getOccupied());
					
					boolean availableTickets = availability.getAvailable() >= numberOfTickets;
					boolean suggestedPriceOverClientPrice = ( ticketPriceWithBookingFee.compareTo(userSelectedPrice) > 0 );
					boolean suggestedPriceLowerThanBestPrice = ( bestPriceAvailability == null || ticketPriceWithBookingFee.compareTo(bestPriceWithBookingFee) < 0 );
					boolean priceInRange = (	(	availability.getType().equalsIgnoreCase(this.TIPO_RM) 
											&&	(ticketPriceWithBookingFee.subtract(userSelectedPrice).doubleValue()) >= upSellingRangeMin
											&& 	(ticketPriceWithBookingFee.subtract(userSelectedPrice).doubleValue()) <= upSellingRangeMax 
										)
										||
										(	availability.getType().equalsIgnoreCase(this.TIPO_UPSELLING) 
											&& 	(ticketPriceWithBookingFee.subtract(userSelectedPrice).doubleValue()) <= upSellingRangeMax 
										)
										);
					
					boolean validation = availableTickets && suggestedPriceOverClientPrice && suggestedPriceLowerThanBestPrice && priceInRange;
					
					log.debug("    availableTickets : " + availableTickets);
					log.debug("    suggestedPriceOverClientPrice : " + suggestedPriceOverClientPrice);
					log.debug("    suggestedPriceLowerThanBestPrice : " + suggestedPriceLowerThanBestPrice);
					log.debug("    priceInRange : " + priceInRange);
					log.debug("  Validation : " + validation);
					
					if (validation) {
						log.debug("  Posible best price found: " + availability.getTicketTypeId());
						bestPriceAvailability = availability;
						bestPriceWithBookingFee = bestPriceAvailability.getTicketPriceWithBookingFee();
					}
				}
				
				if (bestPriceAvailability != null) {
					log.debug("  Best price found: " + bestPriceWithBookingFee.toString());
					suggestion.setTicketId(bestPriceAvailability.getTicketTypeId());
					suggestion.setTicketPrice(bestPriceAvailability.getTicketPrice().toString());
					suggestion.setTicketBookingFee(bestPriceAvailability.getTicketBookingFee().toString());
					suggestion.setAvailable(bestPriceAvailability.getAvailable());
					upsellingSuggestions.add(suggestion);
				}
			}
			response.setUpsellingSuggestions(upsellingSuggestions);
			if(response.getUpsellingSuggestions().size() == 0) {
				response.setMessage(this.MESSAGE_NO_RESULTS);
			}
			else {
				StatisticUpSelling statistic = new StatisticUpSelling(cinemaId, sessionId, userSelectedPrice.toString(), numberOfTickets);
				statistic.setMovieName(response.getUpsellingSuggestions().get(0).getMovieName());
				statistic.setUpgradePrice(String.valueOf(new Double(response.getUpsellingSuggestions().get(0).getTicketPrice()).doubleValue() + new Double(response.getUpsellingSuggestions().get(0).getTicketBookingFee()).doubleValue()));
				mongoOperation.save(statistic);
				response.setTransactionId(statistic.getId());
			}
		}
		catch (Exception e) {
			response.setMessage(this.MESSAGE_NO_RESULTS);
			log.error("Exception : " + e.getMessage());
			log.error(ErroresUtils.extraeStackTrace(e));
		}
		return response;
	}
	public SecondSellingAvailabilityResponse getSecondSellingSuggestions(String cinemaId, String sessionId, String userId) {
		return this.getSecondSellingSuggestions(cinemaId, sessionId, null, null, userId);
	}
	
	@Override
	public SecondSellingAvailabilityResponse getSecondSellingSuggestions(String cinemaId, String sessionId, String ticketPrice, Integer numberOfTickets, String userId) {
		log.debug("Iniciando getSecondSellingSuggestions");
		log.debug("  cinemaId : " + cinemaId);
		log.debug("  sessionId : " + sessionId);
		log.debug("  userId : " + userId);
		SecondSellingAvailabilityResponse response = new SecondSellingAvailabilityResponse(sessionId);
		response.setMessage(this.MESSAGE_SUCCESS);
		
		try {
			boolean personalizedSuggestions = true;
			List<Session> sessionList = this.getSecondSellingPersonalizedSuggestions(cinemaId, sessionId, userId);
			if (sessionList.size() == 0) {
				personalizedSuggestions = false;
				sessionList = this.getSecondSellingGenericSuggestions(cinemaId, sessionId);
			}

			List<SecondSellingSuggestion> secondSellingSuggestions =  new ArrayList<SecondSellingSuggestion>();
			log.debug("  Cantidad de funciones second selling encontradas : " + sessionList.size());
			for (Session session : sessionList) {
				Availability bestPrice = null;
				BigDecimal bestPriceWithBookingFee = null;
				for (Availability availability : session.getAvailability()) {
					if (availability.getType().equalsIgnoreCase(this.TIPO_RM) 
							&& (bestPrice == null || (availability.getTicketPriceWithBookingFee().compareTo(bestPriceWithBookingFee) < 0))
							&& availability.getAvailable() > 0) {
						bestPrice = availability;
						bestPriceWithBookingFee = bestPrice.getTicketPriceWithBookingFee();
					}
				}
				
				if (bestPrice != null) {
					SecondSellingSuggestion suggestion = new SecondSellingSuggestion();
					
					suggestion.setSessionId(session.getSessionId());
					suggestion.setCinemaId(session.getCinemaId());
					suggestion.setMovieName(session.getMovieName());
					suggestion.setSessionTime(session.getTime());
					
					for (SecondSelling secondSelling : session.getSecondSelling()) {
						if(secondSelling.getSessionId().equals(sessionId)){
							suggestion.setOrder(secondSelling.getOrden());
							break;
						}
					}
					
					suggestion.setTicketId(bestPrice.getTicketTypeId());
					suggestion.setTicketPrice(bestPrice.getTicketPrice().toString());
					suggestion.setTicketBookingFee(bestPrice.getTicketBookingFee().toString());
					suggestion.setAvailable(bestPrice.getAvailable());
					secondSellingSuggestions.add(suggestion);
				}
			}
			log.debug("  Cantidad de funciones second selling enviadas : " + secondSellingSuggestions.size());
			response.setSecondSellingSuggestions(secondSellingSuggestions);
			if(response.getSecondSellingSuggestions().size() == 0) {
				response.setMessage(this.MESSAGE_NO_RESULTS);
			}
			else {
				StatisticSecondSelling statistic = new StatisticSecondSelling(cinemaId, sessionId, userId, secondSellingSuggestions.size());
				statistic.setOriginalNumberOfTickets(numberOfTickets);
				statistic.setOriginalPrice(new BigDecimal(ticketPrice).movePointLeft(4).toString());
				statistic.setSuggestedMovieName(response.getSecondSellingSuggestions().get(0).getMovieName());
				statistic.setPersonalized(personalizedSuggestions);
				mongoOperation.save(statistic);
				response.setTransactionId(statistic.getId());
			}
		}
		catch (Exception e) {
			response.setMessage(this.MESSAGE_NO_RESULTS);
			log.error(ErroresUtils.extraeStackTrace(e));
		}
		return response;
	}
	
	private List<Session> getSecondSellingGenericSuggestions(String cinemaId, String sessionId) {
		Query query = new Query(Criteria.where("cinemaId").is(cinemaId).and("secondSelling.sessionId").is(sessionId).and("date").gt(new Date()).and("eliminada").is(false));
		query.fields().exclude("second_selling_client_suggestions");
		return mongoOperation.find(query, Session.class);
	}
	
	private List<Session> getSecondSellingPersonalizedSuggestions(String cinemaId, String sessionId, String clientId) {
		Query query = new Query(Criteria.where("cinemaId").is(cinemaId).and("second_selling_client_suggestions.sessionId").is(sessionId).and("second_selling_client_suggestions.clientId").is(clientId).and("date").gt(new Date()).and("eliminada").is(false));
		query.fields().exclude("second_selling_client_suggestions");
		return mongoOperation.find(query, Session.class);
	}

	@Override
	public LastMinuteSellingAvailabilityResponse getLastMinuteSellingSuggestions(String cinemaId, String date) {
		log.debug("Iniciando getLastMinuteSellingSuggestions");
		LastMinuteSellingAvailabilityResponse response = new LastMinuteSellingAvailabilityResponse();
		response.setMessage(this.MESSAGE_SUCCESS);
		try {
			Query query = new Query(Criteria.where("lastMinuteSelling").is(true));
			query.addCriteria(Criteria.where("eliminada").is(false));
			
			if (date != null && !date.equalsIgnoreCase("")) {
				Date sessionDate = DateUtils.getUTCdate(date, DateUtils.format_yyyyMMdd);
				query.addCriteria(Criteria.where("date").is(sessionDate));
			}
			else {
				Date sessionDate = DateUtils.getUTCdate(new Date(), DateUtils.format_yyyyMMdd);
				query.addCriteria(Criteria.where("date").gte(sessionDate));
			}
			if (cinemaId != null && !cinemaId.equalsIgnoreCase("")) {
				query.addCriteria(Criteria.where("cinemaId").is(cinemaId));
			}
			
			List<Session> sessionList = mongoOperation.find(query, Session.class);
			
			log.debug("  Cantidad de funciones encontradas : " + sessionList.size());
			
			List<LastMinuteSuggestion> lastMinuteSuggestions = new ArrayList<LastMinuteSuggestion>();
			
			for (Session session : sessionList) {
				Availability bestPrice = this.getBestPriceAvailable(session.getAvailability());
				
				if (bestPrice != null) {
					LastMinuteSuggestion suggestion = new LastMinuteSuggestion();
					
					suggestion.setCinemaId(session.getCinemaId());
					suggestion.setCinemaName(session.getCinemaName());
					suggestion.setSessionId(session.getSessionId());
					suggestion.setMovieName(session.getMovieName());
					suggestion.setMovieGraphicUrl(session.getMovieGraphicUrl());
					suggestion.setMovieRating(session.getMovieRating());
					suggestion.setSessionTime(session.getTime());
					suggestion.setTicketId(bestPrice.getTicketTypeId());
					suggestion.setTicketPrice(bestPrice.getTicketPrice().toString());
					suggestion.setTicketBookingFee(bestPrice.getTicketBookingFee().toString());
					suggestion.setAvailable(bestPrice.getAvailable());
					
					lastMinuteSuggestions.add(suggestion);
				}
			}
			response.setLastMinuteSuggestions(lastMinuteSuggestions);
			
			if(response.getLastMinuteSuggestions().size() == 0) {
				response.setMessage(this.MESSAGE_NO_RESULTS);
			}
			else {
				Statistic statistic = new StatisticLastMinuteSelling(cinemaId, date, response.getLastMinuteSuggestions().size());
				mongoOperation.save(statistic);
				response.setTransactionId(statistic.getId());
			}
			
		} catch (ParseException e) {
			response.setMessage("Date format must be 'YYYY-MM-DD'");
			log.error(e.getMessage());
		}
		return response;
	}
	
	public void setMongoOperation(MongoOperations mongoOperation) {
		this.mongoOperation = mongoOperation;
	}

	@Override
	public LastMinuteFeaturedResponse getFeaturedLastMinuteSellingSuggestion(String date) {
		LastMinuteFeaturedResponse response = new LastMinuteFeaturedResponse();
		response.setMessage(this.MESSAGE_SUCCESS);
		
		try {
			Date sessionDate = DateUtils.getUTCdate(date, DateUtils.format_yyyyMMdd);
			
			log.debug("SessionDate : " + sessionDate);
			Query query = new Query(Criteria.where("lastMinuteSelling").is(true).and("date").is(sessionDate));
			
			List<Session> sessionList = mongoOperation.find(query, Session.class);
			log.debug("  funcionesLastMinute : " + sessionList.size());
			
			if (sessionList.size() == 0) {
				response.setMessage("No results.");
				return response;
			}
			
			Random random = new Random();
			int randomIndex = random.nextInt(sessionList.size());
			Session session = sessionList.get(randomIndex);
			
			Availability bestPrice = this.getBestPriceAvailable(session.getAvailability());
			
			LastMinuteSuggestion lastMinuteSuggestion = new LastMinuteSuggestion();
			lastMinuteSuggestion.setAvailable(bestPrice.getAvailable());
			lastMinuteSuggestion.setCinemaId(session.getCinemaId());
			lastMinuteSuggestion.setMovieGraphicUrl(session.getMovieGraphicUrl());
			lastMinuteSuggestion.setMovieName(session.getMovieName());
			lastMinuteSuggestion.setMovieRating(session.getMovieRating());
			lastMinuteSuggestion.setSessionId(session.getSessionId());
			lastMinuteSuggestion.setSessionTime(session.getTime());
			lastMinuteSuggestion.setTicketBookingFee(bestPrice.getTicketBookingFee().toString());
			lastMinuteSuggestion.setTicketId(bestPrice.getTicketTypeId());
			lastMinuteSuggestion.setTicketPrice(bestPrice.getTicketPrice().toString());
			lastMinuteSuggestion.setCinemaName(session.getCinemaName());
			
			response.setLastMinuteSuggestion(lastMinuteSuggestion);
			
		} catch (ParseException e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			response.setMessage("No results: [" + e.getMessage() + "]");
		}
		
		return response;
	}
	
	private Availability getBestPriceAvailable(List<Availability> priceAvailability) {
		Availability bestPrice = null;
		BigDecimal bestPriceWithBookingFee = null;
		for (Availability availability : priceAvailability) {
			//Set the availability as total - occupied
			availability.setAvailable(availability.getTotal() - availability.getOccupied());
			
			if (availability.getType().equalsIgnoreCase(this.TIPO_RM) 
					&& (bestPrice == null || (availability.getTicketPriceWithBookingFee().compareTo(bestPriceWithBookingFee) < 0 ))
					&& availability.getAvailable() > 0) {
				bestPrice = availability;
				bestPriceWithBookingFee = bestPrice.getTicketPriceWithBookingFee();
			}
		}
		return bestPrice;
	}
}
