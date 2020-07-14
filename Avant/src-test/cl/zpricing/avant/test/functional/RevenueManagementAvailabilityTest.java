package cl.zpricing.avant.test.functional;

import static com.jayway.restassured.RestAssured.get;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.mongodb.WriteResult;

import cl.zpricing.avant.etl.model.Availability;
import cl.zpricing.avant.etl.model.Session;
import cl.zpricing.avant.negocio.forecastmanager.UpsellingManager;
import cl.zpricing.avant.negocio.sincronizador.MetaProceso;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.test.utils.DataLoad;
import cl.zpricing.avantors.model.Statistic;
import cl.zpricing.commons.utils.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class RevenueManagementAvailabilityTest {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoOperations mongoTemplate;
	
	@Autowired
	private DataLoad dataLoad;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("revenueManagementAvailabilityJob")
	private Job revenueManagementAvailabilityJob;
	@Autowired
	@Qualifier("secondSellingPersonalizedSuggestionsJob")
	private Job secondSellingPersonalizedSuggestionsJob;
	
	private final String movieId = "HO0011111";
	
	private final String clienteId = "id_cliente";
	
	private final String superSaverTicketId = "0253";
	private final String saverTicketId = "0251";
	private final String lightTicketId = "0252";
	private final String fullPriceTicketId = "0001";
	
	private final String existingSessionIdWithThreDaysInAdvance = "59562";
	private final String sessionIdWithThreeDaysInAdvance = "11111";
	private final String sessionIdWithTwoDaysInAdvance = "22222";
	private final String sessionIdWithOneDayInAdvance = "33333";
	private final String sessionIdForToday = "44444";
	private final String sessionIdLastMinute = "55555";
	private final String cinemaId = "003";
	
	@Before
	public void before() throws ParseException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		mongoTemplate.dropCollection(Session.class);
		dataLoad.cleanTables();
		
		int zonaGeograficaId = dataLoad.insertarZonaGeografica("Zona Geografica 1");
		int complejoId = dataLoad.insertarComplejo("Complejo 1", this.cinemaId, zonaGeograficaId);
		int grupoSalaId = dataLoad.insertarGrupoDeSala(complejoId);
		int salaId  = dataLoad.insertarSala(grupoSalaId, complejoId);
		
		Session session = new Session();
		
		List<Availability> sessionAvailability = new ArrayList<Availability>();
		Availability superSaverAvailability = new Availability();
		superSaverAvailability.setTicketTypeId(this.superSaverTicketId);
		superSaverAvailability.setAvailable(4);
		superSaverAvailability.setOccupied(2);
		superSaverAvailability.setTotal(6);
		sessionAvailability.add(superSaverAvailability);
		
		Availability saverAvailability = new Availability();
		saverAvailability.setTicketTypeId(this.saverTicketId);
		saverAvailability.setAvailable(10);
		saverAvailability.setOccupied(0);
		saverAvailability.setTotal(10);
		sessionAvailability.add(saverAvailability);
		
		Availability lightAvailability = new Availability();
		lightAvailability.setTicketTypeId(this.lightTicketId);
		lightAvailability.setAvailable(10);
		lightAvailability.setOccupied(0);
		lightAvailability.setTotal(10);
		sessionAvailability.add(lightAvailability);
		
		session.setAvailability(sessionAvailability);
		session.setCinemaId(this.cinemaId);
		session.setSessionId(this.existingSessionIdWithThreDaysInAdvance);
		
		mongoTemplate.save(session);
		
		int priceCardId = dataLoad.crearPriceCard("000001", "Price Card test", complejoId);
		log.debug("priceCardId: " + priceCardId);
		int mascaraId = dataLoad.crearMascara("Mascara Test", complejoId);
		log.debug("mascaraId: " + mascaraId);
		int mascaraLastMinute = dataLoad.crearMascaraLastMinute(complejoId);
		log.debug("mascaraLastMinute: " + mascaraLastMinute);
		
		int grupoPeliculaId = dataLoad.insertarGrupoPelicula("Group Pelicula TEST");
		int peliculaId = dataLoad.crearPelicula35mm("Pelicula TEST", this.movieId, grupoPeliculaId, complejoId, 1);
		
		int ticketAdultoId = dataLoad.crearTicketAdulto("Adulto testing", this.fullPriceTicketId, complejoId);
		int ticketLightId = dataLoad.crearTicketLight("Light testing", this.lightTicketId, complejoId);
		int ticketSaverId = dataLoad.crearTicketEconomico("Saver testing", this.saverTicketId, complejoId);
		int ticketSuperSaverId = dataLoad.crearTicketSuperEconomico("Super Saver testing", this.superSaverTicketId, complejoId);
		
		dataLoad.asociarTicketPriceCard(priceCardId, ticketAdultoId, new BigDecimal("4000.0000"), new BigDecimal("500.0000"));
		dataLoad.asociarTicketPriceCard(priceCardId, ticketLightId, new BigDecimal("3000.0000"), new BigDecimal("400.0000"));
		dataLoad.asociarTicketPriceCard(priceCardId, ticketSaverId, new BigDecimal("2000.0000"), new BigDecimal("200.0000"));
		dataLoad.asociarTicketPriceCard(priceCardId, ticketSuperSaverId, new BigDecimal("1500.0000"), new BigDecimal("0"));
		
		dataLoad.asociarTicketMascara(ticketSuperSaverId, mascaraId, 4, 2, salaId);
		dataLoad.asociarTicketMascara(ticketSaverId, mascaraId, 8, 1, salaId);
		dataLoad.asociarTicketMascara(ticketLightId, mascaraId, 12, 0, salaId);
		
		dataLoad.asociarTicketMascara(ticketSuperSaverId, mascaraLastMinute, 20, 2, salaId);
		dataLoad.asociarTicketMascara(ticketSaverId, mascaraLastMinute, 0, 1, salaId);
		dataLoad.asociarTicketMascara(ticketLightId, mascaraLastMinute, 0, 0, salaId);
		
		Calendar cal = DateUtils.obtenerCalendario();
		Date today = cal.getTime();
		cal.add(Calendar.DATE, 1);
		Date oneDayInAdvance = cal.getTime();
		cal.add(Calendar.DATE, 1);
		Date twoDaysInAdvance = cal.getTime();
		cal.add(Calendar.DATE, 1);
		Date threeDaysInAdvance = cal.getTime();
		
		dataLoad.crearFuncion(complejoId, salaId, threeDaysInAdvance, "19:30:00", peliculaId, priceCardId, mascaraId, this.existingSessionIdWithThreDaysInAdvance);
		int funcionSecondSelling = dataLoad.crearFuncion(complejoId, salaId, threeDaysInAdvance, "20:30:00", peliculaId, priceCardId, mascaraId, this.sessionIdWithThreeDaysInAdvance);
		int funcionSecondSellingClienteId = dataLoad.crearFuncion(complejoId, salaId, twoDaysInAdvance, "20:30:00", peliculaId, priceCardId, mascaraId, this.sessionIdWithTwoDaysInAdvance);
		dataLoad.crearFuncion(complejoId, salaId, oneDayInAdvance, "20:30:00", peliculaId, priceCardId, mascaraId, this.sessionIdWithOneDayInAdvance);
		int funcionSeleccionada = dataLoad.crearFuncion(complejoId, salaId, today, "23:59:59", peliculaId, priceCardId, mascaraId, this.sessionIdForToday);
		
		dataLoad.crearFuncion(complejoId, salaId, today, "23:59:59", peliculaId, priceCardId, mascaraLastMinute, this.sessionIdLastMinute);
		
		dataLoad.asociarFuncionSecondSelling(funcionSeleccionada, funcionSecondSelling, 1);
		dataLoad.asociarFuncionSecondSellingPersonalized(funcionSeleccionada, funcionSecondSellingClienteId, clienteId, 1);
		
		jobLauncher.run(revenueManagementAvailabilityJob, new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
		jobLauncher.run(secondSellingPersonalizedSuggestionsJob, new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
	}
	
	@Test
	public void testAvailabilityForNewSessionWithThreeDaysInAdvance() {
		Calendar cal = DateUtils.obtenerCalendario();
		cal.add(Calendar.DATE, 3);
		String url = "/AvantServices/advancedSellingAvailability/cinemaId/" + this.cinemaId + "/movieId/" + this.movieId + "/date/" + DateUtils.format_yyyyMMdd.format(cal.getTime()) + ".json";
		log.debug("url : " + url);
		Response response = get(url);
		
		assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	JsonPath jp = new JsonPath(json);
    	assertEquals("Success", jp.get("model.message"));
    	assertNotNull("Transaction id is not null", jp.get("model.transactionId"));
    	
    	Statistic statistic = mongoTemplate.findOne(new Query(Criteria.where("id").is(jp.get("model.transactionId"))), Statistic.class);
    	assertNotNull(statistic);
    	log.debug("statistic type: " + statistic.getType());
    	assertSame("Checking for rm type", statistic.getType().equalsIgnoreCase("rm"), true);
		assertSame("Checking for status queried", statistic.getStatus().equalsIgnoreCase("queried"), true);
	}
	
	@Test
	public void testAvailabilityForNewSessionWithTwoDaysInAdvance() {
		Session savedSession = (Session) mongoTemplate.findOne(new Query( Criteria.where("cinemaId").is(this.cinemaId).and("sessionId").is(this.sessionIdWithTwoDaysInAdvance)), Session.class);
		
		List<Availability> sessionAvailability = savedSession.getAvailability();
		log.debug("  # of sessionAvailabilities : " + sessionAvailability.size());
		
		for (Availability availability : sessionAvailability) {
			if (availability.getTicketTypeId().equalsIgnoreCase(this.superSaverTicketId)) {
				assertEquals("Valida cupos super economicos disponibles", 0, availability.getAvailable());
			}
			else if (availability.getTicketTypeId().equalsIgnoreCase(this.saverTicketId)) {
				assertEquals("Valuda cupos economomicos disponibles", 8, availability.getAvailable());
			}
			else if (availability.getTicketTypeId().equalsIgnoreCase(this.lightTicketId)) {
				assertEquals("Valuda cupos light disponibles", 12, availability.getAvailable());
			}
		}
	}
	
	@Test
	public void testAvailabilityForNewSessionWithOneDayInAdvance() {
		Session savedSession = (Session) mongoTemplate.findOne(new Query( Criteria.where("cinemaId").is(this.cinemaId).and("sessionId").is(this.sessionIdWithOneDayInAdvance)), Session.class);
		
		List<Availability> sessionAvailability = savedSession.getAvailability();
		log.debug("  # of sessionAvailabilities : " + sessionAvailability.size());
		
		for (Availability availability : sessionAvailability) {
			if (availability.getTicketTypeId().equalsIgnoreCase(this.superSaverTicketId)) {
				assertEquals("Valida cupos super economicos disponibles", 0, availability.getAvailable());
			}
			else if (availability.getTicketTypeId().equalsIgnoreCase(this.saverTicketId)) {
				assertEquals("Valuda cupos economomicos disponibles", 0, availability.getAvailable());
			}
			else if (availability.getTicketTypeId().equalsIgnoreCase(this.lightTicketId)) {
				assertEquals("Valuda cupos light disponibles", 12, availability.getAvailable());
			}
		}
	}
	
	@Test
	public void testAvailabilityForNewSessionToday() {
		Session savedSession = (Session) mongoTemplate.findOne(new Query( Criteria.where("cinemaId").is(this.cinemaId).and("sessionId").is(this.sessionIdForToday)), Session.class);
		
		List<Availability> sessionAvailability = savedSession.getAvailability();
		log.debug("  # of sessionAvailabilities : " + sessionAvailability.size());
		
		for (Availability availability : sessionAvailability) {
			if (availability.getTicketTypeId().equalsIgnoreCase(this.superSaverTicketId)) {
				assertEquals("Valida cupos super economicos disponibles", 0, availability.getAvailable());
			}
			else if (availability.getTicketTypeId().equalsIgnoreCase(this.saverTicketId)) {
				assertEquals("Valuda cupos economomicos disponibles", 0, availability.getAvailable());
			}
			else if (availability.getTicketTypeId().equalsIgnoreCase(this.lightTicketId)) {
				assertEquals("Valuda cupos light disponibles", 0, availability.getAvailable());
			}
		}
	}
	
	@Test
	public void testUpdatedSessionWithOccupiedAvailability() {
		Session savedSession = (Session) mongoTemplate.findOne(new Query( Criteria.where("cinemaId").is(this.cinemaId).and("sessionId").is(this.existingSessionIdWithThreDaysInAdvance)), Session.class);
		
		List<Availability> sessionAvailability = savedSession.getAvailability();
		log.debug("  # of sessionAvailabilities : " + sessionAvailability.size());
		
		for (Availability availability : sessionAvailability) {
			if (availability.getTicketTypeId().equalsIgnoreCase(this.superSaverTicketId)) {
				assertEquals("Valida cupos ocupados no sean modificados", 2, availability.getOccupied());
				assertEquals("Valida cupos disponibles ajustados", 2, availability.getAvailable());
			}
		}
	}
	
	@Test
	public void testSecondSellingSuggestions() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		Response response = get("/AvantServices/secondSellingSuggestions/cinemaId/" + this.cinemaId + "/sessionId/" + this.sessionIdForToday + "/ticketPrice/2000/numberOfTickets/2.json");
		
		assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	log.debug(json);
    	JsonPath jp = new JsonPath(json);
    	
    	assertEquals("Success", jp.get("model.message"));
    	List<Map<String, Object>> suggestions = jp.getList("model.secondSellingSuggestions");
    	assertEquals("Cantidad de ofertas igual a 1", 1, suggestions.size());
    	
    	log.debug(suggestions.get(0).get("sessionId"));
    	
    	assertEquals(this.sessionIdWithThreeDaysInAdvance, suggestions.get(0).get("sessionId"));
	}
	
	@Test
	public void testPersonalizedSecondSellingSuggestions() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		Response response = get("/AvantServices/secondSellingSuggestions/cinemaId/" + this.cinemaId + "/sessionId/" + this.sessionIdForToday + "/ticketPrice/2000/numberOfTickets/2/userId/" + this.clienteId + ".json");
		
		assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	log.debug(json);
    	JsonPath jp = new JsonPath(json);
    	
    	assertEquals("Success", jp.get("model.message"));
    	List<Map<String, Object>> suggestions = jp.getList("model.secondSellingSuggestions");
    	assertEquals("Cantidad de ofertas igual a 1", 1, suggestions.size());
    	
    	log.debug(suggestions.get(0).get("sessionId"));
    	
    	assertEquals(this.sessionIdWithTwoDaysInAdvance, suggestions.get(0).get("sessionId"));
	}
	
	@Test
	public void testPersonalizedSecondSellingSuggestionsNotRegistered() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		Response response = get("/AvantServices/secondSellingSuggestions/cinemaId/" + this.cinemaId + "/sessionId/" + this.sessionIdForToday + "/ticketPrice/2000/numberOfTickets/2/userId/clientIdDoesntExist.json");
		
		assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	log.debug(json);
    	JsonPath jp = new JsonPath(json);
    	
    	assertEquals("Success", jp.get("model.message"));
    	List<Map<String, Object>> suggestions = jp.getList("model.secondSellingSuggestions");
    	assertEquals("Cantidad de ofertas igual a 1", 1, suggestions.size());
    	
    	log.debug(suggestions.get(0).get("sessionId"));
    	
    	assertEquals(this.sessionIdWithThreeDaysInAdvance, suggestions.get(0).get("sessionId"));
	}
	
	@Test
	public void testFeaturedLastMinuteSuggestion() {
		Calendar cal = DateUtils.obtenerCalendario();
		String date = DateUtils.format_yyyyMMdd.format(cal.getTime());
		log.debug("date : " + date);
		Response response = get("/AvantServices/lastMinuteFeatured/date/" + date + ".json");
		
		assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	log.debug(json);
    	JsonPath jp = new JsonPath(json);
    	
    	assertEquals("Success", jp.get("model.message"));
    	assertEquals(this.sessionIdLastMinute, jp.get("model.lastMinuteSuggestion.sessionId"));
	}
	

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	public void setDataLoad(DataLoad dataLoad) {
		this.dataLoad = dataLoad;
	}

	public void setRevenueManagementAvailabilityJob(Job revenueManagementAvailabilityJob) {
		this.revenueManagementAvailabilityJob = revenueManagementAvailabilityJob;
	}

	public void setSecondSellingPersonalizedSuggestionsJob(Job secondSellingPersonalizedSuggestionsJob) {
		this.secondSellingPersonalizedSuggestionsJob = secondSellingPersonalizedSuggestionsJob;
	}
}