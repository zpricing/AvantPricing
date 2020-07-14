package cl.zpricing.revman.webservices;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.zpricing.commons.utils.DateUtils;
import cl.zpricing.revman.model.Session;
import cl.zpricing.revman.webservices.model.CapacidadCupos;
import cl.zpricing.revman.webservices.model.CapacidadCuposPorPeliculaYDia;
import cl.zpricing.revman.webservices.model.LastMinuteSellingAvailabilityResponse;
import cl.zpricing.revman.webservices.model.RevenueManagementAvailabilityResponse;
import cl.zpricing.revman.webservices.model.RevenueManagementMovieAvailabilityResponse;
import cl.zpricing.revman.webservices.model.RevenueManagementUpdateAvailabilityResponse;
import cl.zpricing.revman.webservices.model.SecondSellingAvailabilityResponse;
import cl.zpricing.revman.webservices.model.UpsellingAvailabilityResponse;
import cl.zpricing.unittest.dataload.RevenueManagementDataLoad;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class ZhetaPricingServicesTest {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private MongoOperations mongoOperation;
	
	@Autowired
	private ZhetaPricingServices zhetaPricingServices;
	@Autowired
	private RevenueManagementDataLoad revenueManagementDataLoad;
	
	@Before
	public void before() {
		revenueManagementDataLoad.deleteCollections();
	}
	
	@After
	public void after() {
		
	}
	
	
	@Test
	public void cancelTransactionAdjustAvailability() {
		String cinemaId = "000";
		String sessionId = "11111";
		String ticketTypeId = "0001";
		int available = 10;
		int cantidad = 2;
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId, available, available, 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		
		RevenueManagementUpdateAvailabilityResponse response = zhetaPricingServices.updateRevenueManagementAvailability(cinemaId, sessionId, ticketTypeId, "", cantidad, "");
		
		String cancelResponse = zhetaPricingServices.cancelTransaction(response.getTransactionId());
		
		Session session = revenueManagementDataLoad.getSession(cinemaId, sessionId);
		
		assertSame(10, session.getAvailability().get(0).getAvailable());
		assertSame(0, session.getAvailability().get(0).getOccupied());
	}
	
	@Test
	public void cancelTransactiontransactionAlreadyCancelled() {
		String cinemaId = "000";
		String sessionId = "11111";
		String ticketTypeId = "0001";
		int available = 10;
		int cantidad = 2;
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId, available, available, 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		
		RevenueManagementUpdateAvailabilityResponse response = zhetaPricingServices.updateRevenueManagementAvailability(cinemaId, sessionId, ticketTypeId, "", cantidad, "");
	
		String transactionId = response.getTransactionId();
		
		zhetaPricingServices.cancelTransaction(transactionId);
		
		String responseAlreadyCancelled = zhetaPricingServices.cancelTransaction(transactionId);
		assertTrue(responseAlreadyCancelled.equalsIgnoreCase("Transaction already cancelled."));	
	}
	
	@Test
	public void testUpsellingSuggestionSameDayAsShowtime() {
		String cinemaId = "000";
		String sessionId = "11111";
		String[] ticketTypeId = {"0001", "0002", "0003", "0004"};
		int[] prices = {1000, 2000, 3000, 4000};
		int[] available = {0, 0, 0, 10};
		String fullPrice = "30000000";
		BigDecimal fullPriceAsDecimal = new BigDecimal(fullPrice).movePointLeft(4);
		double bfee = 400;
		String selectedSessionWithAvailableUpselling = "12345";
		int numberOfTickets = 2;
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, fullPriceAsDecimal.doubleValue(), bfee);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[0], available[0], available[0], 0, 0, new BigDecimal(prices[0]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[1], available[1], available[1], 0, 0, new BigDecimal(prices[1]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[2], available[2], available[2], 0, 0, new BigDecimal(prices[2]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[3], available[3], available[3], 0, 0, new BigDecimal(prices[3]), new BigDecimal(0), ZhetaPricingServices.TIPO_UPSELLING);
		revenueManagementDataLoad.addUpsellingSuggestionsForSession(id, selectedSessionWithAvailableUpselling);
		
		UpsellingAvailabilityResponse response = zhetaPricingServices.getUpsellingSuggestions(cinemaId, selectedSessionWithAvailableUpselling, numberOfTickets, fullPrice);
		
		log.debug("" + response.getMessage());
		assertTrue(response.getUpsellingSuggestions().size() > 0);
		assertSame(available[3], response.getUpsellingSuggestions().get(0).getAvailable());
		log.debug("  cantidad de funciones : " + response.getUpsellingSuggestions().size());
	}
	
	@Test
	public void testUpsellingSuggestionPriceOutOfRange() {
		String cinemaId = "000";
		String sessionId = "11111";
		String[] ticketTypeId = {"0001", "0002", "0003", "0004"};
		int[] prices = {1000, 2000, 4000, 4300};
		int[] available = {0, 0, 2, 10};
		String fullPrice = "30000000";
		String fullPriceDiffBiggerThanMaxRange = "24000000";
		String fullPriceDiffLowerThanMinRange = "41000000";
		BigDecimal fullPriceAsDecimal = new BigDecimal(fullPrice).movePointLeft(4);
		double bfee = 600;
		String selectedSessionWithAvailableUpselling = "12345";
		int numberOfTickets = 2;
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, fullPriceAsDecimal.doubleValue(), bfee);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[0], available[0], available[0], 0, 0, new BigDecimal(prices[0]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[1], available[1], available[1], 0, 0, new BigDecimal(prices[1]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[2], available[2], available[2], 0, 0, new BigDecimal(prices[2]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[3], available[3], available[3], 0, 0, new BigDecimal(prices[3]), new BigDecimal(0), ZhetaPricingServices.TIPO_UPSELLING);
		revenueManagementDataLoad.addUpsellingSuggestionsForSession(id, selectedSessionWithAvailableUpselling);
		
		UpsellingAvailabilityResponse responseRMInRange = zhetaPricingServices.getUpsellingSuggestions(cinemaId, selectedSessionWithAvailableUpselling, numberOfTickets, fullPrice);
		UpsellingAvailabilityResponse responseRMLowerThanRangeReturnsUpselling = zhetaPricingServices.getUpsellingSuggestions(cinemaId, selectedSessionWithAvailableUpselling, numberOfTickets, fullPriceDiffLowerThanMinRange);
		UpsellingAvailabilityResponse responseRMBiggerThanRangeReturnsNada = zhetaPricingServices.getUpsellingSuggestions(cinemaId, selectedSessionWithAvailableUpselling, numberOfTickets, fullPriceDiffBiggerThanMaxRange);
		
		zhetaPricingServices.updateRevenueManagementAvailability(cinemaId, sessionId, ticketTypeId[2], "", numberOfTickets, "");
		
		log.debug("" + responseRMInRange.getMessage());
		assertTrue(responseRMInRange.getUpsellingSuggestions().size() > 0);
		assertSame(available[2], responseRMInRange.getUpsellingSuggestions().get(0).getAvailable());
		log.debug("  cantidad de funciones : " + responseRMInRange.getUpsellingSuggestions().size());
		
		log.debug("" + responseRMLowerThanRangeReturnsUpselling.getMessage());
		assertTrue(responseRMLowerThanRangeReturnsUpselling.getUpsellingSuggestions().size() > 0);
		assertSame(available[3], responseRMLowerThanRangeReturnsUpselling.getUpsellingSuggestions().get(0).getAvailable());
		log.debug("  cantidad de funciones : " + responseRMLowerThanRangeReturnsUpselling.getUpsellingSuggestions().size());
		
		log.debug("" + responseRMBiggerThanRangeReturnsNada.getMessage());
		assertTrue(responseRMBiggerThanRangeReturnsNada.getUpsellingSuggestions().size() == 0);
		log.debug("  cantidad de funciones : " + responseRMBiggerThanRangeReturnsNada.getUpsellingSuggestions().size());
	}
	
	@Test
	public void testUpsellingSuggestionReturnsBestPrice() {
		String cinemaId = "000";
		String sessionId = "11111";
		String[] ticketTypeId = {"0001", "0002", "0003", "0004"};
		int[] available = {0, 6, 8, 10};
		int[] prices = {1000, 2000, 3000, 4000};
		String fullPrice = "11000000";
		BigDecimal fullPriceAsDecimal = new BigDecimal(fullPrice).movePointLeft(4);
		double bfee = 400;
		String selectedSessionWithAvailableUpselling = "12345";
		int numberOfTickets = 2;
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, fullPriceAsDecimal.doubleValue(), bfee);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[0], available[0], available[0], 0, 0, new BigDecimal(prices[0]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[1], available[1], available[1], 0, 0, new BigDecimal(prices[1]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[2], available[2], available[2], 0, 0, new BigDecimal(prices[2]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[3], available[3], available[3], 0, 0, new BigDecimal(prices[3]), new BigDecimal(0), ZhetaPricingServices.TIPO_UPSELLING);
		revenueManagementDataLoad.addUpsellingSuggestionsForSession(id, selectedSessionWithAvailableUpselling);
		
		UpsellingAvailabilityResponse response = zhetaPricingServices.getUpsellingSuggestions(cinemaId, selectedSessionWithAvailableUpselling, numberOfTickets, fullPrice);
		
		log.debug("" + response.getMessage());
		assertTrue(response.getUpsellingSuggestions().size() > 0);
		assertSame(available[1], response.getUpsellingSuggestions().get(0).getAvailable());
		log.debug("  cantidad de funciones : " + response.getUpsellingSuggestions().size());
	}
	
	@Test
	public void testUpsellingSuggestionReturnsBestPriceAboveTicketPrice() {
		String cinemaId = "000";
		String sessionId = "11111";
		String[] ticketTypeId = {"0001", "0002", "0003", "0004"};
		int[] prices = {1000, 2000, 3000, 4000};
		int[] available = {0, 6, 8, 10};
		String fullPrice = "25000000";
		BigDecimal fullPriceAsDecimal = new BigDecimal(fullPrice).movePointLeft(4);
		double bfee = 400;
		String selectedSessionWithAvailableUpselling = "12345";
		int numberOfTickets = 2;
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, fullPriceAsDecimal.doubleValue(), bfee);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[0], available[0], available[0], 0, 0, new BigDecimal(prices[0]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[1], available[1], available[1], 0, 0, new BigDecimal(prices[1]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[2], available[2], available[2], 0, 0, new BigDecimal(prices[2]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[3], available[3], available[3], 0, 0, new BigDecimal(prices[3]), new BigDecimal(0), ZhetaPricingServices.TIPO_UPSELLING);
		revenueManagementDataLoad.addUpsellingSuggestionsForSession(id, selectedSessionWithAvailableUpselling);
		
		UpsellingAvailabilityResponse response = zhetaPricingServices.getUpsellingSuggestions(cinemaId, selectedSessionWithAvailableUpselling, numberOfTickets, fullPrice);
		
		log.debug("" + response.getMessage());
		assertTrue(response.getUpsellingSuggestions().size() > 0);
		assertSame(available[2], response.getUpsellingSuggestions().get(0).getAvailable());
		log.debug("  cantidad de funciones : " + response.getUpsellingSuggestions().size());
	}

	@Test
	public void testUpdateUpsellingAvailabilitySameDayAsShowtime() {
		String cinemaId = "000";
		String sessionId = "11111";
		String[] ticketTypeId = {"0001", "0002", "0003", "0004"};
		int[] prices = {1000, 2000, 3000, 4000};
		int[] available = {0, 0, 0, 10};
		String fullPrice = "30000000";
		BigDecimal fullPriceAsDecimal = new BigDecimal(fullPrice).movePointLeft(4);
		double bfee = 400;
		int cantidad = 2;
		String selectedSessionWithAvailableUpselling = "12345";
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, fullPriceAsDecimal.doubleValue(), bfee);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[0], available[0], available[0], 0, 0, new BigDecimal(prices[0]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[1], available[1], available[1], 0, 0, new BigDecimal(prices[1]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[2], available[2], available[2], 0, 0, new BigDecimal(prices[2]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[3], available[3], available[3], 0, 0, new BigDecimal(prices[3]), new BigDecimal(0), ZhetaPricingServices.TIPO_UPSELLING);
		revenueManagementDataLoad.addUpsellingSuggestionsForSession(id, selectedSessionWithAvailableUpselling);
		
		RevenueManagementUpdateAvailabilityResponse result = zhetaPricingServices.updateRevenueManagementAvailability(cinemaId, sessionId, ticketTypeId[3], "", cantidad, "");
		
		UpsellingAvailabilityResponse response = zhetaPricingServices.getUpsellingSuggestions(cinemaId, selectedSessionWithAvailableUpselling, cantidad, fullPrice);
		
		assertSame(available[3] - cantidad, response.getUpsellingSuggestions().get(0).getAvailable());
	}
	
	@Test
	public void testSecondSellingSuggestion() {
		String cinemaId = "000";
		String sessionId = "11111";
		String movieName = "testMovie";
		String[] ticketTypeId = {"0001", "0002", "0003", "0004"};
		int[] available = {4, 6, 8, 10};
		String fullPrice = "50000000";
		BigDecimal fullPriceAsDecimal = new BigDecimal(fullPrice).movePointLeft(4);
		double bfee = 400;
		String selectedSessionWithAvailableSecondSelling = "12345";
		String userId = "";
		int numberOfTickets = 2;
		
		Calendar cal = DateUtils.obtenerCalendario();
		String date_session_1 = DateUtils.format_yyyyMMdd.format(cal.getTime());
		cal.add(Calendar.DATE, 2);
		String date_session_2 = DateUtils.format_yyyyMMdd.format(cal.getTime());
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, movieName, fullPriceAsDecimal.doubleValue(), bfee, date_session_1);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[0], available[0], available[0], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[1], available[1], available[1], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[2], available[2], available[2], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[3], available[3], available[3], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_UPSELLING);
		revenueManagementDataLoad.addSecondSellingSuggestionsForSession(id, selectedSessionWithAvailableSecondSelling);
		
		id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, movieName, fullPriceAsDecimal.doubleValue(), bfee, date_session_2);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[0], available[0], available[0], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[1], available[1], available[1], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[2], available[2], available[2], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[3], available[3], available[3], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_UPSELLING);
		revenueManagementDataLoad.addSecondSellingSuggestionsForSession(id, selectedSessionWithAvailableSecondSelling);
		
		String id2 = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, selectedSessionWithAvailableSecondSelling, movieName, fullPriceAsDecimal.doubleValue(), bfee, date_session_1);
		
		SecondSellingAvailabilityResponse result = zhetaPricingServices.getSecondSellingSuggestions(cinemaId, selectedSessionWithAvailableSecondSelling, fullPrice, numberOfTickets, userId);
		log.debug("result : " + result.getMessage());
		
		assertTrue(result.getSecondSellingSuggestions().size() == 1);
	}
	
	@Test
	public void testSecondSellingSuggestionReturnsBestPrice() {
		String cinemaId = "000";
		String sessionId = "11111";
		String movieName = "testMovie";
		String[] ticketTypeId = {"0001", "0002", "0003", "0004"};
		int[] available = {0, 6, 8, 10};
		int[] prices = {1000, 2000, 3000, 4000};
		String fullPrice = "50000000";
		BigDecimal fullPriceAsDecimal = new BigDecimal(fullPrice).movePointLeft(4);
		double bfee = 400;
		String selectedSessionWithAvailableSecondSelling = "12345";
		String userId = "";
		int numberOfTickets = 2;
		
		Calendar cal = DateUtils.obtenerCalendario();
		cal.add(Calendar.DATE, 2);
		String date_session = DateUtils.format_yyyyMMdd.format(cal.getTime());
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, movieName, fullPriceAsDecimal.doubleValue(), bfee, date_session);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[0], available[0], available[0], 0, 0, new BigDecimal(prices[0]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[1], available[1], available[1], 0, 0, new BigDecimal(prices[1]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[2], available[2], available[2], 0, 0, new BigDecimal(prices[2]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addSecondSellingSuggestionsForSession(id, selectedSessionWithAvailableSecondSelling);
		
		String id2 = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, selectedSessionWithAvailableSecondSelling, movieName, fullPriceAsDecimal.doubleValue(), bfee, date_session);
		
		SecondSellingAvailabilityResponse result = zhetaPricingServices.getSecondSellingSuggestions(cinemaId, selectedSessionWithAvailableSecondSelling, fullPrice, numberOfTickets,  userId);
		log.debug("result : " + result.getMessage());
		
		assertTrue(result.getSecondSellingSuggestions().size() > 0);
		assertSame(available[1], result.getSecondSellingSuggestions().get(0).getAvailable());
		log.debug("  cantidad de funciones : " + result.getSecondSellingSuggestions().size());
	}
	
	@Test
	public void testLastMinuteSellingSuggestion() {
		String cinemaId = "000";
		String cinemaIdWithoutSuggestions = "001";
		String sessionId = "11111";
		Calendar cal = DateUtils.obtenerCalendario();
		String date = DateUtils.format_yyyyMMdd.format(cal.getTime());
		
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 2);
		Date time1 = cal.getTime();
		
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 15);
		Date time2 = cal.getTime();
		
		String dateWithoutSuggestions = "2013-03-02";
		String[] ticketTypeId = {"0001", "0002", "0003", "0004"};
		int[] available = {4, 6, 8, 10};
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, date, time1, true);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[0], available[0], available[0], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[1], available[1], available[1], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[2], available[2], available[2], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[3], available[3], available[3], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_UPSELLING);
		
		String id2 = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, date, false);
		revenueManagementDataLoad.addAvailability(id2, ticketTypeId[0], available[0], available[0], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id2, ticketTypeId[1], available[1], available[1], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id2, ticketTypeId[2], available[2], available[2], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id2, ticketTypeId[3], available[3], available[3], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_UPSELLING);
		
		LastMinuteSellingAvailabilityResponse responseWithEmptyCinemaAndDate = zhetaPricingServices.getLastMinuteSellingSuggestions("", "");
		LastMinuteSellingAvailabilityResponse responseWithCinemaAndEmptyDate = zhetaPricingServices.getLastMinuteSellingSuggestions(cinemaId, "");
		LastMinuteSellingAvailabilityResponse responseWithDateAndEmptyCinema = zhetaPricingServices.getLastMinuteSellingSuggestions("", date);
		LastMinuteSellingAvailabilityResponse responseWithCinemaAndDate = zhetaPricingServices.getLastMinuteSellingSuggestions(cinemaId, date);
		LastMinuteSellingAvailabilityResponse responseWithCinemaAndDateForDateWithoutSuggestions = zhetaPricingServices.getLastMinuteSellingSuggestions(cinemaId, dateWithoutSuggestions);
		LastMinuteSellingAvailabilityResponse responseWithCinemaAndDateForCinemaWithoutSuggestions = zhetaPricingServices.getLastMinuteSellingSuggestions(cinemaIdWithoutSuggestions, date);
		LastMinuteSellingAvailabilityResponse responseWithEmptyCinemaAndDateForDateWithoutSuggestions = zhetaPricingServices.getLastMinuteSellingSuggestions("", dateWithoutSuggestions);
		LastMinuteSellingAvailabilityResponse responseWithCinemaAndEmptyDateForCinemaWithoutSuggestions = zhetaPricingServices.getLastMinuteSellingSuggestions(cinemaIdWithoutSuggestions, "");
		
		assertSame("Success", responseWithEmptyCinemaAndDate.getMessage());
		assertNotNull(responseWithEmptyCinemaAndDate.getTransactionId());
		assertEquals("LastMinuteSuggestions With Empty Cinema and Date", 1, responseWithEmptyCinemaAndDate.getLastMinuteSuggestions().size());
		assertEquals("LastMinuteSuggestions With Cinema and Empty Date", 1, responseWithCinemaAndEmptyDate.getLastMinuteSuggestions().size());
		assertEquals("LastMinuteSuggestions With Date and Empty Cinema", 1, responseWithDateAndEmptyCinema.getLastMinuteSuggestions().size());
		assertEquals("LastMinuteSuggestions With Cinema and Date", 1, responseWithCinemaAndDate.getLastMinuteSuggestions().size());
		assertEquals("LastMinuteSuggestions With Cinema and Date for date without suggestions", 0, responseWithCinemaAndDateForDateWithoutSuggestions.getLastMinuteSuggestions().size());
		assertEquals("LastMinuteSuggestions With Cinema and Date for cinema without suggestions", 0, responseWithCinemaAndDateForCinemaWithoutSuggestions.getLastMinuteSuggestions().size());
		assertEquals("LastMinuteSuggestions With Empty Cinema and Date for date without suggestions", 0, responseWithEmptyCinemaAndDateForDateWithoutSuggestions.getLastMinuteSuggestions().size());
		assertEquals("LastMinuteSuggestions With Cinema and Empty Date for Cinema without suggestions", 0, responseWithCinemaAndEmptyDateForCinemaWithoutSuggestions.getLastMinuteSuggestions().size());
	}
	
	public void setMongoOperation(MongoOperations mongoOperation) {
		this.mongoOperation = mongoOperation;
	}
}
