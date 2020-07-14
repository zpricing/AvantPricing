package cl.zpricing.revman.webservices;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
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
import cl.zpricing.revman.model.Statistic;
import cl.zpricing.revman.model.StatisticLastMinuteSelling;
import cl.zpricing.revman.model.StatisticSecondSelling;
import cl.zpricing.revman.model.StatisticUpSelling;
import cl.zpricing.revman.model.Transaccion;
import cl.zpricing.revman.webservices.model.LastMinuteSellingAvailabilityResponse;
import cl.zpricing.revman.webservices.model.RevenueManagementAvailabilityResponse;
import cl.zpricing.revman.webservices.model.RevenueManagementMovieAvailabilityResponse;
import cl.zpricing.revman.webservices.model.RevenueManagementUpdateAvailabilityResponse;
import cl.zpricing.revman.webservices.model.SecondSellingAvailabilityResponse;
import cl.zpricing.revman.webservices.model.UpsellingAvailabilityResponse;
import cl.zpricing.unittest.dataload.RevenueManagementDataLoad;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class StatisticsTest {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private ZhetaPricingServices zhetaPricingServices;
	@Autowired
	private MongoOperations mongoOperation;
	@Autowired
	private RevenueManagementDataLoad revenueManagementDataLoad;
	
	@Before
	public void before() {
		mongoOperation.dropCollection("statistic");
		mongoOperation.dropCollection("session");
	}
	
	@Test
	public void noStatisticsForNoRevenueManagementResults() {
		String cinemaId = "000";
		String movieId = "HO000001";
		String date = "2013-03-01";
		RevenueManagementMovieAvailabilityResponse response = zhetaPricingServices.getRevenueManagementAvailabilityPerDay(cinemaId, movieId, date);
		
		assertSame(null, response.getTransactionId());
	}
	
	@Test
	public void createQueriedStatisticForRevenueManagementAvailability() {
		String cinemaId = "000";
		String date = "2013-03-01";
		String movieId = "HO000001";
		String[] sessions = {"0001", "0002", "0003"};
		String ticketTypeId = "0011";
		int available = 10;
		List<String> ids = new ArrayList<String>();
		
		log.debug("sessions.length : " + sessions.length);
		
		for (String sessionId : sessions) {
			log.debug(" inserting : " + sessionId);
			String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, movieId, date);
			revenueManagementDataLoad.addAvailability(id, ticketTypeId, available, available, 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
			revenueManagementDataLoad.addAvailability(id, ticketTypeId, available, available, 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_UPSELLING);
			
			log.debug("  id : " + id);
			ids.add(id);
		}
		
		RevenueManagementMovieAvailabilityResponse response = zhetaPricingServices.getRevenueManagementAvailabilityPerDay(cinemaId, movieId, date);
		
		Statistic statistic = mongoOperation.findOne(new Query(Criteria.where("id").is(response.getTransactionId())), Statistic.class);
		assertNotNull(statistic);
		assertSame("Checking for rm type", statistic.getType().equalsIgnoreCase("rm"), true);
		assertSame("Checking for status queried", statistic.getStatus().equalsIgnoreCase("queried"), true);
	}
	
	@Test
	public void createQueriedStatisticForUpsellingAvailability() {
		String cinemaId = "000";
		String sessionId = "11111";
		String[] ticketTypeId = {"0001", "0002", "0003", "0004"};
		int[] prices = {1000, 2000, 3000, 4000};
		int[] available = {0, 0, 0, 10};
		double fullPrice = 4500;
		double bfee = 400;
		String selectedSessionWithAvailableUpselling = "12345";
		int numberOfTickets = 2;
		
		String queriedPrice = "30000000";
		BigDecimal queriedPriceAsDecimal = new BigDecimal(queriedPrice).movePointLeft(4);
		String movieName = "Los Croods 3D";
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, movieName, fullPrice, bfee);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[0], available[0], available[0], 0, 0, new BigDecimal(prices[0]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[1], available[1], available[1], 0, 0, new BigDecimal(prices[1]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[2], available[2], available[2], 0, 0, new BigDecimal(prices[2]), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[3], available[3], available[3], 0, 0, new BigDecimal(prices[3]), new BigDecimal(0), ZhetaPricingServices.TIPO_UPSELLING);
		revenueManagementDataLoad.addUpsellingSuggestionsForSession(id, selectedSessionWithAvailableUpselling);
		
		UpsellingAvailabilityResponse response = zhetaPricingServices.getUpsellingSuggestions(cinemaId, selectedSessionWithAvailableUpselling, numberOfTickets, queriedPrice);
		
		assertSame("Checking message Success", response.getMessage().equalsIgnoreCase("Success"), true);
		
		StatisticUpSelling statistic = (StatisticUpSelling) mongoOperation.findOne(new Query(Criteria.where("id").is(response.getTransactionId())), Statistic.class);
		assertNotNull(statistic);
		assertSame("Checking for up_selling type", statistic.getType().equalsIgnoreCase("up_selling"), true);
		assertSame("Checking for status queried", statistic.getStatus().equalsIgnoreCase("queried"), true);
		log.debug("Statistic price queried: " + Double.parseDouble(statistic.getOriginalFormatPrice()));
		log.debug("queriedPriceAsDecimal: " + queriedPriceAsDecimal.doubleValue());
		assertSame("Checking for price queried", Double.parseDouble(statistic.getOriginalFormatPrice()) == queriedPriceAsDecimal.doubleValue(), true);
		log.debug("  suggested price : " + statistic.getUpgradePrice());
		log.debug("  price set : " + prices[3]);
		assertSame("Checking for price suggested", Double.parseDouble(statistic.getUpgradePrice()) == Double.parseDouble(String.valueOf(prices[3])), true);
		assertSame("Checking for number of tickets queried", statistic.getNumberOfTickets().intValue(), numberOfTickets);
		assertSame("Checking for suggested movieName ", statistic.getMovieName().equalsIgnoreCase(movieName), true);
	}
	
	@Test
	public void secondSellingAvailabilityStats() {
		String cinemaId = "000";
		String sessionId = "11111";
		String[] ticketTypeId = {"0001", "0002", "0003", "0004"};
		int[] available = {4, 6, 8, 10};
		String fullPrice = "50000000";
		BigDecimal fullPriceAsDecimal = new BigDecimal(fullPrice).movePointLeft(4);
		double bfee = 400;
		String selectedSessionWithAvailableSecondSelling = "12345";
		String userId = "";
		Calendar cal = DateUtils.obtenerCalendario();
		cal.add(Calendar.DATE, 2);
		String date_session = DateUtils.format_yyyyMMdd.format(cal.getTime());
		int numberOfTickets = 2;
		int numberOfTicketsPurchased = 1;
		String movieName = "Los Croods";
		String movieNameSuggested = "EPIC";
		
		revenueManagementDataLoad.insertSession(cinemaId, selectedSessionWithAvailableSecondSelling, "", movieName, date_session);
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, movieNameSuggested, fullPriceAsDecimal.doubleValue(), bfee, date_session);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[0], available[0], available[0], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[1], available[1], available[1], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[2], available[2], available[2], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[3], available[3], available[3], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_UPSELLING);
		revenueManagementDataLoad.addSecondSellingSuggestionsForSession(id, selectedSessionWithAvailableSecondSelling);
		
		SecondSellingAvailabilityResponse response = zhetaPricingServices.getSecondSellingSuggestions(cinemaId, selectedSessionWithAvailableSecondSelling, fullPrice, numberOfTickets, userId);
		
		assertSame("Checking message Success", response.getMessage().equalsIgnoreCase("Success"), true);
		
		log.debug(" TransactionID : " + response.getTransactionId());
		
		StatisticSecondSelling statistic = (StatisticSecondSelling) mongoOperation.findOne(new Query(Criteria.where("id").is(response.getTransactionId())), Statistic.class);
		assertNotNull(statistic);
		assertSame("Checking for second_selling type", statistic.getType().equalsIgnoreCase("second_selling"), true);
		assertSame("Checking for status queried", statistic.getStatus().equalsIgnoreCase("queried"), true);
		assertSame("Checking for original price queried", Double.parseDouble(statistic.getOriginalPrice()) == fullPriceAsDecimal.doubleValue(), true);
		assertSame("Checking for original number of tickets queried", statistic.getOriginalNumberOfTickets().intValue(), numberOfTickets);
		assertSame("Checking for suggested movie", statistic.getSuggestedMovieName().equalsIgnoreCase(movieNameSuggested), true);
		
		RevenueManagementUpdateAvailabilityResponse finishResponse = zhetaPricingServices.finishTransaction(cinemaId, sessionId, ticketTypeId[1], numberOfTicketsPurchased, response.getTransactionId(), userId);
		
		assertSame("Checking finish transaction id equals queried transaction id", response.getTransactionId().equalsIgnoreCase(finishResponse.getTransactionId()), true);
		
		StatisticSecondSelling statisticAfterFinish = (StatisticSecondSelling) mongoOperation.findOne(new Query(Criteria.where("id").is(response.getTransactionId())), Statistic.class);
		
		assertSame("Checking for number of tickets purchased", statisticAfterFinish.getSelectedNumberOfTickets() == numberOfTicketsPurchased, true);
		assertSame("Checking for status purchased", statisticAfterFinish.getStatus().equalsIgnoreCase("purchased"), true);
	}
	
	@Test
	public void createQueriedStatisticForLastMinuteSellingAvailability() {
		String cinemaId = "000";
		String sessionId = "11111";
		
		Calendar cal = DateUtils.obtenerCalendario();
		String date = DateUtils.format_yyyyMMdd.format(cal.getTime());
		
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 2);
		Date time = cal.getTime();
		
		String[] ticketTypeId = {"0001", "0002", "0003", "0004"};
		int[] available = {4, 6, 8, 10};
		double fullPrice = 5000;
		double bfee = 400;
		String selectedSessionWithAvailableSecondSelling = "12345";
		String userId = "";
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, date, time, true);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[0], available[0], available[0], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[1], available[1], available[1], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[2], available[2], available[2], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId[3], available[3], available[3], 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_UPSELLING);
		revenueManagementDataLoad.addSecondSellingSuggestionsForSession(id, selectedSessionWithAvailableSecondSelling);
		
		LastMinuteSellingAvailabilityResponse response = zhetaPricingServices.getLastMinuteSellingSuggestions(cinemaId, date);
		
		assertSame("Checking message Success", response.getMessage().equalsIgnoreCase("Success"), true);
		
		log.debug(" TransactionID : " + response.getTransactionId());
		
		StatisticLastMinuteSelling statistic = (StatisticLastMinuteSelling) mongoOperation.findOne(new Query(Criteria.where("id").is(response.getTransactionId())), Statistic.class);
		assertNotNull(statistic);
		log.debug("  CinemaId LastMinuteSelling: " + statistic.getCinemaId());
		assertSame("Checking for last_minute_selling type", statistic.getType().equalsIgnoreCase("last_minute_selling"), true);
		assertSame("Checking for status queried", statistic.getStatus().equalsIgnoreCase("queried"), true);
		
		log.debug("  Statistic date" + statistic.getDateQueried());
		log.debug("  Date queried " + date);
		assertSame("Checking for cinemaId match", statistic.getCinemaId().equalsIgnoreCase(cinemaId), true);
		assertSame("Checking for date match", statistic.getDateQueried().equalsIgnoreCase(date), true);
	}
	
	@Test
	public void purchasedStatisticAfterUpdate() {
		String cinemaId = "000";
		String date = "2013-03-01";
		String movieId = "HO000001";
		String sessionId = "11111";
		String ticketTypeId = "0001";
		int available = 10;
		int cantidad = 2;
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, movieId, date);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId, available, available, 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		
		RevenueManagementMovieAvailabilityResponse responseAvailability = zhetaPricingServices.getRevenueManagementAvailabilityPerDay(cinemaId, movieId, date);
		
		log.debug("  TransactionId query: " + responseAvailability.getTransactionId());
		
		RevenueManagementUpdateAvailabilityResponse response = zhetaPricingServices.updateRevenueManagementAvailability(cinemaId, sessionId, ticketTypeId, "", cantidad, responseAvailability.getTransactionId());
		
		log.debug("  TransactionId update: " + response.getTransactionId());
		
		Statistic statistic = mongoOperation.findOne(new Query(Criteria.where("id").is(response.getTransactionId())), Statistic.class);
		assertNotNull(statistic);
		assertSame("Checking for rm type", statistic.getType().equalsIgnoreCase("rm"), true);
		assertSame("Checking for purchased status", statistic.getStatus().equalsIgnoreCase("purchased"), true);
	}
	
	@Test
	public void annulledStatisticAfterCancel() {
		String cinemaId = "000";
		String date = "2013-03-01";
		String movieId = "HO000001";
		String sessionId = "11111";
		String ticketTypeId = "0001";
		int available = 10;
		int cantidad = 2;
		
		String id = revenueManagementDataLoad.insertRevenueManagementAvailability(cinemaId, sessionId, movieId, date);
		revenueManagementDataLoad.addAvailability(id, ticketTypeId, available, available, 0, 0, new BigDecimal(0), new BigDecimal(0), ZhetaPricingServices.TIPO_RM);
		
		RevenueManagementMovieAvailabilityResponse responseAvailability = zhetaPricingServices.getRevenueManagementAvailabilityPerDay(cinemaId, movieId, date);
		
		log.debug("  TransactionId query: " + responseAvailability.getTransactionId());
		RevenueManagementUpdateAvailabilityResponse response = zhetaPricingServices.updateRevenueManagementAvailability(cinemaId, sessionId, ticketTypeId, "", cantidad, responseAvailability.getTransactionId());
		log.debug("  TransactionId update: " + response.getTransactionId());
		
		zhetaPricingServices.cancelTransaction(response.getTransactionId());
		
		Statistic statistic = mongoOperation.findOne(new Query(Criteria.where("id").is(response.getTransactionId())), Statistic.class);
		assertNotNull(statistic);
		assertSame("Checking for rm type", statistic.getType().equalsIgnoreCase("rm"), true);
		assertSame("Checking for purchased status", statistic.getStatus().equalsIgnoreCase("annulled"), true);
	}
	
	public void testInheritance() {
		StatisticSecondSelling statisticSecondSelling = new StatisticSecondSelling();
		statisticSecondSelling.setDate(new Date());
		statisticSecondSelling.setType("second_selling");
		statisticSecondSelling.setNumberOfSuggestions(4);
		
		StatisticUpSelling statisticUpSelling = new StatisticUpSelling();
		statisticUpSelling.setDate(new Date());
		statisticUpSelling.setType("up_selling");
		statisticUpSelling.setCinemaId("013");
		statisticUpSelling.setSessionId("1234");
		
		mongoOperation.save(statisticSecondSelling);
		mongoOperation.save(statisticUpSelling);
		
		Transaccion transaccion = new Transaccion();
		transaccion.setId(statisticSecondSelling.getId());
		transaccion.setCantidad(4);
		mongoOperation.save(transaccion);
		
		log.debug("Id upselling : " + statisticUpSelling.getId());
		log.debug("Id secondselling : " + statisticSecondSelling.getId());
		
		List<Statistic> statistics = mongoOperation.findAll(Statistic.class, "statistic");
		
		for (Statistic statistic : statistics) {
			log.debug("  Type : " + statistic.getType());
			if (statistic instanceof StatisticUpSelling) {
				StatisticUpSelling statisticUpselling = (StatisticUpSelling) statistic;
				log.debug("  cinemaId " + statisticUpselling.getCinemaId());
			}
		}
		
		Statistic queried = mongoOperation.findOne(new Query(Criteria.where("id").is(statisticUpSelling.getId())), Statistic.class);
		
		log.debug("  tipo de statistic queried : " + queried.getType());
	}

	public void setMongoOperation(MongoOperations mongoOperation) {
		this.mongoOperation = mongoOperation;
	}

	public void setRevenueManagementDataLoad(
			RevenueManagementDataLoad revenueManagementDataLoad) {
		this.revenueManagementDataLoad = revenueManagementDataLoad;
	}

	public void setZhetaPricingServices(ZhetaPricingServices zhetaPricingServices) {
		this.zhetaPricingServices = zhetaPricingServices;
	}
}
