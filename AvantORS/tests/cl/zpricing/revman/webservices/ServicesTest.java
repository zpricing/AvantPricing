package cl.zpricing.revman.webservices;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.post;
import static com.jayway.restassured.matcher.RestAssuredMatchers.matchesXsd;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cl.zpricing.revman.model.Statistic;
import cl.zpricing.revman.webservices.model.CapacidadCupos;
import cl.zpricing.unittest.dataload.DataLoad;
import cl.zpricing.unittest.dataload.RevenueManagementDataLoad;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class ServicesTest {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private String date;
    
    @Autowired
	private DataLoad dataLoad;
    
    @Autowired
	private MongoOperations mongoOperation;
    
    private final String superSaverTicketId = "0001";
    private final String saverTicketId = "0002";
    private final String lightTicketId = "0003";
    private final String fullPriceTicketId = "0004";
    
    @Before
    public void setUp() throws ParseException {
    	dataLoad.deleteCollections();
    	
    	Calendar cal = DateUtils.obtenerCalendario();
    	cal.add(Calendar.DATE, 4);
    	cal.set(Calendar.HOUR, 19);
    	cal.set(Calendar.MINUTE, 30);
    	this.date = DateUtils.format_yyyyMMdd.format(cal.getTime());
    	
    	String sessionId = dataLoad.createSession("000", "1111", "H000000111", cal.getTime());
    	dataLoad.addAvailability(sessionId, this.superSaverTicketId, new BigDecimal("1500.0000"), new BigDecimal("0.0000"), 2, 0, 2, ZhetaPricingServices.TIPO_RM);
    	dataLoad.addAvailability(sessionId, this.saverTicketId, new BigDecimal("1800.0000"), new BigDecimal("400.0000"), 4, 0, 1, ZhetaPricingServices.TIPO_RM);
    	dataLoad.addAvailability(sessionId, this.lightTicketId, new BigDecimal("2400.0000"), new BigDecimal("600.0000"), 6, 0, 0, ZhetaPricingServices.TIPO_RM);
    	
    	String upSellingSession = dataLoad.createSession("000", "4444", "H000000444", cal.getTime());
    	dataLoad.addAvailability(upSellingSession, this.superSaverTicketId, new BigDecimal("3000.0000"), new BigDecimal("0.0000"), 2, 0, 2, ZhetaPricingServices.TIPO_RM);
    	dataLoad.addAvailability(upSellingSession, this.saverTicketId, new BigDecimal("3600.0000"), new BigDecimal("400.0000"), 4, 0, 1, ZhetaPricingServices.TIPO_RM);
    	dataLoad.addAvailability(upSellingSession, this.lightTicketId, new BigDecimal("4200.0000"), new BigDecimal("600.0000"), 6, 0, 0, ZhetaPricingServices.TIPO_RM);
    	dataLoad.addAvailability(upSellingSession, this.fullPriceTicketId, new BigDecimal("4800.0000"), new BigDecimal("600.0000"), 10, 0, 0, ZhetaPricingServices.TIPO_UPSELLING);
    	dataLoad.addUpsellingSuggestionsForSession(upSellingSession, "1111", 1);
    	
    	cal.add(Calendar.DATE, 2);
    	
    	String secondSellingSessionId = dataLoad.createSession("000", "2222", "H000000222", cal.getTime());
    	dataLoad.addAvailability(secondSellingSessionId, this.superSaverTicketId, new BigDecimal("1500.0000"), new BigDecimal("0.0000"), 2, 0, 2, ZhetaPricingServices.TIPO_RM);
    	dataLoad.addAvailability(secondSellingSessionId, this.saverTicketId, new BigDecimal("1800.0000"), new BigDecimal("400.0000"), 4, 0, 1, ZhetaPricingServices.TIPO_RM);
    	dataLoad.addAvailability(secondSellingSessionId, this.lightTicketId, new BigDecimal("2400.0000"), new BigDecimal("600.0000"), 6, 0, 0, ZhetaPricingServices.TIPO_RM);
    	dataLoad.addSecondSellingSuggestionsForSession(secondSellingSessionId, "1111", 1);
    	
    	String secondSellingSessionWithoutSuperSaverId = dataLoad.createSession("000", "3333", "H000000333", cal.getTime());
    	dataLoad.addAvailability(secondSellingSessionWithoutSuperSaverId, this.superSaverTicketId, new BigDecimal("1500.0000"), new BigDecimal("0.0000"), 0, 0, 2, ZhetaPricingServices.TIPO_RM);
    	dataLoad.addAvailability(secondSellingSessionWithoutSuperSaverId, this.saverTicketId, new BigDecimal("1800.0000"), new BigDecimal("400.0000"), 4, 0, 1, ZhetaPricingServices.TIPO_RM);
    	dataLoad.addAvailability(secondSellingSessionWithoutSuperSaverId, this.lightTicketId, new BigDecimal("2400.0000"), new BigDecimal("600.0000"), 6, 0, 0, ZhetaPricingServices.TIPO_RM);
    	dataLoad.addSecondSellingSuggestionsForSession(secondSellingSessionWithoutSuperSaverId, "2222", 1);
    	
    	
    }
    
    @After
	public void after() {
    	//dataLoad.deleteCollections();
	}
    
    @Test
    public void testStatusResponseMessageAndTransactionIdFromGetAdvancedSellingAvailabilityByDay() throws Exception {
    	Response response = get("/ZPCinemasWS/advancedSellingAvailability/cinemaId/000/movieId/H000000111/date/" + this.date + ".json");
    	assertEquals(200, response.getStatusCode());
    	JsonPath jp = new JsonPath(response.asString());
    	assertEquals("WebService message is Success", "Success", jp.get("model.message"));
    	assertNotNull("Transaction id is not null", jp.get("model.transactionId"));
    	
    	Statistic statistic = mongoOperation.findOne(new Query(Criteria.where("id").is(jp.get("model.transactionId"))), Statistic.class);
    	assertNotNull(statistic);
    	log.debug("statistic type: " + statistic.getType());
    	assertSame("Checking for rm type", statistic.getType().equalsIgnoreCase("rm"), true);
		assertSame("Checking for status queried", statistic.getStatus().equalsIgnoreCase("queried"), true);
    }
    
    @Test
    public void testTicketAvailabilityFromGetAdvancedSellingAvailabilityByDay() throws Exception {
    	Response response = get("/ZPCinemasWS/advancedSellingAvailability/cinemaId/000/movieId/H000000111/date/" + this.date + ".json");
    	JsonPath jp = new JsonPath(response.asString());
    	List<Map<String, Object>> availability = jp.getList("model.sessionAvailability[0].ticketAvailability");
    	log.debug("  #Êof tickets : " + availability.size());
    	
    	for (Map<String, Object> ticketAvailability : availability) {
    		String ticketTypeCode = (String) ticketAvailability.get("ticketTypeCode");
    		Integer ticketsAvailable = (Integer) ticketAvailability.get("purchase");
    		log.debug("  TicketTypeCode : " + ticketTypeCode);
    		log.debug("  ticketsAvailable : " + ticketsAvailable);
    		
			if (ticketTypeCode.equalsIgnoreCase(this.superSaverTicketId))
				assertEquals(2, ticketsAvailable.intValue());
			else if (ticketTypeCode.equalsIgnoreCase(this.saverTicketId))
				assertEquals(4, ticketsAvailable.intValue());
			else if (ticketTypeCode.equalsIgnoreCase(this.lightTicketId))
				assertEquals(6, ticketsAvailable.intValue());
		}
    }
    
    @Test
    public void testStatusResponseMessageAndTransactionFromGetAdvancedSellingAvailability() throws Exception {
    	Response response = get("/ZPCinemasWS/advancedSellingAvailability/cinemaId/000/sessionId/1111.json");
    	assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	JsonPath jp = new JsonPath(json);
    	assertEquals("Success", jp.get("model.message"));
    	assertNotNull("Transaction id is not null", jp.get("model.transactionId"));
    	
    	Statistic statistic = mongoOperation.findOne(new Query(Criteria.where("id").is(jp.get("model.transactionId"))), Statistic.class);
    	assertNotNull(statistic);
    	log.debug("statistic type: " + statistic.getType());
    	assertSame("Checking for rm type", statistic.getType().equalsIgnoreCase("rm"), true);
		assertSame("Checking for status queried", statistic.getStatus().equalsIgnoreCase("queried"), true);
    }
    
    @Test
    public void testTicketAvailabilityFromGetAdvancedSellingAvailability() throws Exception {
    	Response response = get("/ZPCinemasWS/advancedSellingAvailability/cinemaId/000/sessionId/1111.json");
    	JsonPath jp = new JsonPath(response.asString());
    	List<Map<String, Object>> availability = jp.getList("model.ticketAvailability");
    	log.debug("  #Êof tickets : " + availability.size());
    	
    	for (Map<String, Object> ticketAvailability : availability) {
    		String ticketTypeCode = (String) ticketAvailability.get("ticketTypeCode");
    		Integer ticketsAvailable = (Integer) ticketAvailability.get("purchase");
    		log.debug("  TicketTypeCode : " + ticketTypeCode);
    		log.debug("  ticketsAvailable : " + ticketsAvailable);
    		
			if (ticketTypeCode.equalsIgnoreCase(this.superSaverTicketId))
				assertEquals(2, ticketsAvailable.intValue());
			else if (ticketTypeCode.equalsIgnoreCase(this.saverTicketId))
				assertEquals(4, ticketsAvailable.intValue());
			else if (ticketTypeCode.equalsIgnoreCase(this.lightTicketId))
				assertEquals(6, ticketsAvailable.intValue());
		}
    }
    
    @Test
    public void testStatusResponseMessageAndAvailavilityFromGetAdvancedSellingAvailabilityByDateWithNoResults() throws Exception {
    	Response response = get("/ZPCinemasWS/advancedSellingAvailability/cinemaId/000/movieId/H000000112/date/" + this.date + ".json");
    	assertEquals(200, response.getStatusCode());
    	JsonPath jp = new JsonPath(response.asString());
    	assertEquals("No results found", jp.get("model.message"));
    	List<Map<String, Object>> availability = jp.getList("model.sessionAvailability");
    	assertEquals(0, availability.size());
    }
    
    @Test
    public void testStatusResponseMessageAndAvailavilityFromGetAdvancedSellingAvailabilityWithNoResults() throws Exception {
    	Response response = get("/ZPCinemasWS/advancedSellingAvailability/cinemaId/000/sessionId/1112.json");
    	assertEquals(200, response.getStatusCode());
    	JsonPath jp = new JsonPath(response.asString());
    	assertEquals("No results found", jp.get("model.message"));
    	List<Map<String, Object>> availability = jp.getList("model.ticketAvailability");
    	assertEquals(0, availability.size());
    }
    
    @Test
    public void testSecondSellingWithNoResults() {
    	Response response = get("/ZPCinemasWS/secondSellingSuggestions/cinemaId/006/sessionId/126121/ticketPrice/2000/numberOfTickets/2.json");
    	assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	JsonPath jp = new JsonPath(json);
    	
    	assertEquals("No results found", jp.get("model.message"));
    	assertNull(jp.get("transactionId"));
    	assertEquals(0, jp.getList("model.secondSellingSuggestions").size());
    }
    
    @Test
    public void testSecondSellingWithResults() {
    	Response response = get("/ZPCinemasWS/secondSellingSuggestions/cinemaId/000/sessionId/1111/ticketPrice/40000000/numberOfTickets/2.json");
    	assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	log.debug(json);
    	JsonPath jp = new JsonPath(json);
    	
    	assertEquals("Success", jp.get("model.message"));
    	assertNotNull(jp.get("model.transactionId"));
    	assertEquals(1, jp.getList("model.secondSellingSuggestions").size());
    	assertEquals("1500.0000", jp.get("model.secondSellingSuggestions[0].ticketPrice"));
    	
    	Statistic statistic = mongoOperation.findOne(new Query(Criteria.where("id").is(jp.get("model.transactionId"))), Statistic.class);
    	assertNotNull(statistic);
    	log.debug("statistic type: " + statistic.getType());
    	assertSame("Checking for second selling type", statistic.getType().equalsIgnoreCase("second_selling"), true);
		assertSame("Checking for status queried", statistic.getStatus().equalsIgnoreCase("queried"), true);
    }
    
    @Test
    public void testSecondSellingBestAvailablePrice() {
    	Response response = get("/ZPCinemasWS/secondSellingSuggestions/cinemaId/000/sessionId/2222/ticketPrice/40000000/numberOfTickets/2.json");
    	assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	log.debug(json);
    	JsonPath jp = new JsonPath(json);
    	
    	assertEquals("Success", jp.get("model.message"));
    	assertNotNull(jp.get("model.transactionId"));
    	assertEquals(1, jp.getList("model.secondSellingSuggestions").size());
    	assertEquals("1800.0000", jp.get("model.secondSellingSuggestions[0].ticketPrice"));
    	assertEquals("400.0000", jp.get("model.secondSellingSuggestions[0].ticketBookingFee"));
    }
    
    @Test
    public void testUpSellingWithNoResults() {
    	Response response = get("/ZPCinemasWS/upsellingSellingSuggestions/cinemaId/006/sessionId/129344/numberOfTickets/1/ticketPrice/4500.json");
    	assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	JsonPath jp = new JsonPath(json);
    	
    	assertEquals("No results found", jp.get("model.message"));
    	assertNull(jp.get("transactionId"));
    	assertEquals(0, jp.getList("model.upsellingSuggestions").size());
    }
    
    @Test
    public void testUpSellingWithResults() {
    	Response response = get("/ZPCinemasWS/upsellingSellingSuggestions/cinemaId/000/sessionId/1111/numberOfTickets/1/ticketPrice/45000000.json");
    	assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	JsonPath jp = new JsonPath(json);
    	
    	assertEquals("Success", jp.get("model.message"));
    	assertNotNull(jp.get("model.transactionId"));
    	assertEquals(1, jp.getList("model.upsellingSuggestions").size());
    	assertEquals("4800.0000", jp.get("model.upsellingSuggestions[0].ticketPrice"));
    	assertEquals("600.0000", jp.get("model.upsellingSuggestions[0].ticketBookingFee"));
    	
    	Statistic statistic = mongoOperation.findOne(new Query(Criteria.where("id").is(jp.get("model.transactionId"))), Statistic.class);
    	assertNotNull(statistic);
    	log.debug("statistic type: " + statistic.getType());
    	assertSame("Checking for up selling type", statistic.getType().equalsIgnoreCase("up_selling"), true);
		assertSame("Checking for status queried", statistic.getStatus().equalsIgnoreCase("queried"), true);
    }
    
    @Test
    public void testUpSellingBestPosiblePrice() {
    	Response response = get("/ZPCinemasWS/upsellingSellingSuggestions/cinemaId/000/sessionId/1111/numberOfTickets/1/ticketPrice/42000000.json");
    	assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	JsonPath jp = new JsonPath(json);
    	
    	assertEquals("Success", jp.get("model.message"));
    	assertNotNull(jp.get("model.transactionId"));
    	assertEquals(1, jp.getList("model.upsellingSuggestions").size());
    	assertEquals("4200.0000", jp.get("model.upsellingSuggestions[0].ticketPrice"));
    	assertEquals("600.0000", jp.get("model.upsellingSuggestions[0].ticketBookingFee"));
    }
    
    @Test
    public void testUpdatedAvailabilityFromUpdateAdvancedSellingAvailabilityWithoutTransactionId() {
    	Response responseUpdate = post("/ZPCinemasWS/updateAvailability/cinemaId/000/sessionId/1111/ticketId/" + this.superSaverTicketId + "/numberOfTickets/2/transactionId/.json");
    	assertEquals(200, responseUpdate.getStatusCode());
    	JsonPath jpResponse = new JsonPath(responseUpdate.asString());
    	assertEquals("Success", jpResponse.get("model.message"));
    	
    	Response responseAvailabilityUpdated = get("/ZPCinemasWS/advancedSellingAvailability/cinemaId/000/sessionId/1111.json");
    	JsonPath jpUpdated = new JsonPath(responseAvailabilityUpdated.asString());
    	List<Map<String, Object>> availability = jpUpdated.getList("model.ticketAvailability");
    	for (Map<String, Object> ticketAvailability : availability) {
    		String ticketTypeCode = (String) ticketAvailability.get("ticketTypeCode");
    		Integer ticketsAvailable = (Integer) ticketAvailability.get("purchase");
    		log.debug("  TicketTypeCode : " + ticketTypeCode);
    		log.debug("  ticketsAvailable : " + ticketsAvailable);
    		
			if (ticketTypeCode.equalsIgnoreCase(this.superSaverTicketId)) {
				assertEquals(0, ticketsAvailable.intValue());
				break;
			}
		}
    }
    
    @Test
    public void testAvailabilityAfterCancelledTransaction() {
    	Response responseUpdate = post("/ZPCinemasWS/updateAvailability/cinemaId/000/sessionId/1111/ticketId/" + this.superSaverTicketId + "/numberOfTickets/2/transactionId/.json");
    	assertEquals(200, responseUpdate.getStatusCode());
    	JsonPath jpResponse = new JsonPath(responseUpdate.asString());
    	assertNotNull("Transaction id is not null", jpResponse.get("model.transactionId"));
    	
    	Response responseCancelTransaction = post("/ZPCinemasWS/cancelTransaction/transactionId/" + jpResponse.get("model.transactionId") + ".json");
    	assertEquals(200, responseCancelTransaction.getStatusCode());
    	JsonPath jpCancel = new JsonPath(responseCancelTransaction.asString());
    	assertEquals("Success", jpCancel.get("model.message"));
    	
    	Response responseAvailabilityUpdated = get("/ZPCinemasWS/advancedSellingAvailability/cinemaId/000/sessionId/1111.json");
    	JsonPath jpUpdated = new JsonPath(responseAvailabilityUpdated.asString());
    	List<Map<String, Object>> availability = jpUpdated.getList("model.ticketAvailability");
    	for (Map<String, Object> ticketAvailability : availability) {
    		String ticketTypeCode = (String) ticketAvailability.get("ticketTypeCode");
    		Integer ticketsAvailable = (Integer) ticketAvailability.get("purchase");
    		log.debug("  TicketTypeCode : " + ticketTypeCode);
    		log.debug("  ticketsAvailable : " + ticketsAvailable);
    		
			if (ticketTypeCode.equalsIgnoreCase(this.superSaverTicketId)) {
				assertEquals(2, ticketsAvailable.intValue());
				break;
			}
		}
    }
    
    @Test
    public void testTransactionAndStatisticFromUpdateAdvancedSellingAvailabilityWithoutTransactionId() {
    	Response responseUpdate = post("/ZPCinemasWS/updateAvailability/cinemaId/000/sessionId/1111/ticketId/" + this.superSaverTicketId + "/numberOfTickets/2/transactionId/.json");
    	assertEquals(200, responseUpdate.getStatusCode());
    	JsonPath jpResponse = new JsonPath(responseUpdate.asString());
    	assertEquals("Success", jpResponse.get("model.message"));
    	assertNotNull("Transaction id is not null", jpResponse.get("model.transactionId"));
    	
    	Statistic statistic = mongoOperation.findOne(new Query(Criteria.where("id").is(jpResponse.get("model.transactionId"))), Statistic.class);
    	assertNull(statistic);
    }
    
    @Test
    public void testTransactionAndStatisticFromUpdateAdvancedSellingAvailabilityWithTransactionId() {
    	Response responseAvailability = get("/ZPCinemasWS/advancedSellingAvailability/cinemaId/000/sessionId/1111.json");
    	JsonPath jp = new JsonPath(responseAvailability.asString());
    	
    	Response responseUpdate = post("/ZPCinemasWS/updateAvailability/cinemaId/000/sessionId/1111/ticketId/" + this.superSaverTicketId + "/numberOfTickets/2/transactionId/" + jp.get("model.transactionId") + ".json");
    	assertEquals(200, responseUpdate.getStatusCode());
    	JsonPath jpResponse = new JsonPath(responseUpdate.asString());
    	assertEquals("Success", jpResponse.get("model.message"));
    	assertEquals("Same TransactionId as input", jp.get("model.transactionId"), jpResponse.get("model.transactionId"));
    	
    	Statistic statistic = mongoOperation.findOne(new Query(Criteria.where("id").is(jpResponse.get("model.transactionId"))), Statistic.class);
    	assertNotNull(statistic);
    	log.debug("statistic type: " + statistic.getType());
    	assertSame("Checking for rm type", statistic.getType().equalsIgnoreCase("rm"), true);
		assertSame("Checking for status queried", statistic.getStatus().equalsIgnoreCase("purchased"), true);
    }
}
