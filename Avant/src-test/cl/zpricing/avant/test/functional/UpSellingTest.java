package cl.zpricing.avant.test.functional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import static com.jayway.restassured.RestAssured.get;
import static org.junit.Assert.*;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import cl.zpricing.avant.etl.model.Session;
import cl.zpricing.avant.negocio.sincronizador.MetaProceso;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.test.utils.DataLoad;
import cl.zpricing.commons.utils.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class UpSellingTest {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoOperations mongoTemplate;
	
	@Autowired
	private DataLoad dataLoad;
	
	@Autowired
	@Qualifier("procesoUpselling")
	private MetaProceso upsellingMetaProcess;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("revenueManagementAvailabilityJob")
	private Job revenueManagementAvailabilityJob;
	
	private Proceso upsellingProcess;
	private final String cinemaId = "003";
	private final String movieId = "HO0011111";
	private final String superSaverTicketId = "0253";
	private final String saverTicketId = "0251";
	private final String lightTicketId = "0252";
	private final String fullPriceTicketId = "0001";
	private final String upSellingTicketId = "0002";
	private final String sessionId = "12345";
	private final String session3DId = "12346";
	private final String session4DXId = "12347";
	
	@Before
	public void before() throws ParseException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		mongoTemplate.dropCollection(Session.class);
		dataLoad.cleanTables();
		
		int zonaGeograficaId = dataLoad.insertarZonaGeografica("Zona Geografica 1");
		int complejoId = dataLoad.insertarComplejo("Complejo 1", this.cinemaId, zonaGeograficaId);
		int grupoSalaId = dataLoad.insertarGrupoDeSala(complejoId);
		int salaId  = dataLoad.insertarSala(grupoSalaId, complejoId);
		
		int priceCardId = dataLoad.crearPriceCard("000001", "Price Card test", complejoId);
		log.debug("priceCardId: " + priceCardId);
		int mascaraId = dataLoad.crearMascara("Mascara Test", complejoId);
		log.debug("mascaraId: " + mascaraId);
		
		int priceCard3DId = dataLoad.crearPriceCard("000002", "Price Card 3D test", complejoId);
		int mascara3DId = dataLoad.crearMascara("Mascara 3D", complejoId);
		
		int priceCard4DXId = dataLoad.crearPriceCard("000002", "Price Card 4DX test", complejoId);
		int mascara4DXId = dataLoad.crearMascara("Mascara 4DX", complejoId);
		
		int grupoPeliculaId = dataLoad.insertarGrupoPelicula("Group Pelicula TEST");
		int peliculaId = dataLoad.crearPelicula35mm("Pelicula TEST", this.movieId, grupoPeliculaId, complejoId, 1);
		int pelicula3DId = dataLoad.crearPelicula3D("Pelicula TEST 3D", this.movieId, grupoPeliculaId, complejoId, 1);
		int pelicula4DXId = dataLoad.crearPelicula4DX("Pelicula TEST 4DX", this.movieId, grupoPeliculaId, complejoId, 1);
		
		int ticketAdultoId = dataLoad.crearTicketAdulto("Adulto testing", this.fullPriceTicketId, complejoId);
		int ticketLightId = dataLoad.crearTicketLight("Light testing", this.lightTicketId, complejoId);
		int ticketSaverId = dataLoad.crearTicketEconomico("Saver testing", this.saverTicketId, complejoId);
		int ticketSuperSaverId = dataLoad.crearTicketSuperEconomico("Super Saver testing", this.superSaverTicketId, complejoId);
		
		int ticketAdulto3DId = dataLoad.crearTicketAdulto("Adulto 3D testing", this.fullPriceTicketId, complejoId);
		int ticketLight3DId = dataLoad.crearTicketLight("Light 3D testing", this.lightTicketId, complejoId);
		int ticketSaver3DId = dataLoad.crearTicketEconomico("Saver 3D testing", this.saverTicketId, complejoId);
		int ticketSuperSaver3DId = dataLoad.crearTicketSuperEconomico("Super Saver 3D testing", this.superSaverTicketId, complejoId);
		int ticketUpSelling3DId = dataLoad.crearTicketUpSelling("Up Selling 3D testing", this.upSellingTicketId, complejoId);
		
		int ticketAdulto4DXId = dataLoad.crearTicketAdulto("Adulto 4DX testing", this.fullPriceTicketId, complejoId);
		int ticketUpSelling4DXId = dataLoad.crearTicketUpSelling("Up Selling 4DX testing", this.upSellingTicketId, complejoId);
		
		dataLoad.asociarTicketPriceCard(priceCardId, ticketAdultoId, new BigDecimal("4000.0000"), new BigDecimal("500.0000"));
		dataLoad.asociarTicketPriceCard(priceCardId, ticketLightId, new BigDecimal("3000.0000"), new BigDecimal("400.0000"));
		dataLoad.asociarTicketPriceCard(priceCardId, ticketSaverId, new BigDecimal("2000.0000"), new BigDecimal("200.0000"));
		dataLoad.asociarTicketPriceCard(priceCardId, ticketSuperSaverId, new BigDecimal("1500.0000"), new BigDecimal("0"));
		
		dataLoad.asociarTicketPriceCard(priceCard3DId, ticketAdulto3DId, new BigDecimal("7000.0000"), new BigDecimal("500.0000"));
		dataLoad.asociarTicketPriceCard(priceCard3DId, ticketUpSelling3DId, new BigDecimal("5500.0000"), new BigDecimal("500.0000"));
		dataLoad.asociarTicketPriceCard(priceCard3DId, ticketLight3DId, new BigDecimal("4500.0000"), new BigDecimal("400.0000"));
		dataLoad.asociarTicketPriceCard(priceCard3DId, ticketSaver3DId, new BigDecimal("4500.0000"), new BigDecimal("200.0000"));
		dataLoad.asociarTicketPriceCard(priceCard3DId, ticketSuperSaver3DId, new BigDecimal("4500.0000"), new BigDecimal("0"));
		
		dataLoad.asociarTicketPriceCard(priceCard4DXId, ticketAdulto4DXId, new BigDecimal("8500.0000"), new BigDecimal("1000.0000"));
		dataLoad.asociarTicketPriceCard(priceCard4DXId, ticketUpSelling4DXId, new BigDecimal("6500.0000"), new BigDecimal("1000.0000"));
		
		dataLoad.asociarTicketMascara(ticketSuperSaverId, mascaraId, 4, 2, salaId);
		dataLoad.asociarTicketMascara(ticketSaverId, mascaraId, 8, 1, salaId);
		dataLoad.asociarTicketMascara(ticketLightId, mascaraId, 12, 0, salaId);
		
		dataLoad.asociarTicketMascara(ticketUpSelling3DId, mascara3DId, 6, 0, salaId);
		dataLoad.asociarTicketMascara(ticketLight3DId, mascara3DId, 4, 2, salaId);
		dataLoad.asociarTicketMascara(ticketSaver3DId, mascara3DId, 8, 1, salaId);
		dataLoad.asociarTicketMascara(ticketSuperSaver3DId, mascara3DId, 12, 0, salaId);
		
		dataLoad.asociarTicketMascara(ticketUpSelling4DXId, mascara4DXId, 10, 0, salaId);
		
		Calendar cal = DateUtils.obtenerCalendario();
		cal.add(Calendar.DATE, 1);
		Date tomorrow = cal.getTime();
		
		dataLoad.crearFuncion(complejoId, salaId, tomorrow, "19:30:00", peliculaId, priceCardId, mascaraId, this.sessionId);
		dataLoad.crearFuncion(complejoId, salaId, tomorrow, "20:30:00", pelicula3DId, priceCard3DId, mascara3DId, this.session3DId);
		dataLoad.crearFuncion(complejoId, salaId, tomorrow, "20:00:00", pelicula4DXId, priceCard4DXId, mascara4DXId, this.session4DXId);
		
		this.upsellingProcess = upsellingMetaProcess.ejecutarProceso();
		this.upsellingProcess.setCodigo(upsellingMetaProcess.getCodigo());
		upsellingProcess.run();
		
		jobLauncher.run(revenueManagementAvailabilityJob, new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
	}
	
	@Test
	public void testUpsellingProcess() {
		assertTrue(upsellingProcess.isSuccessful());
	}
	
	@Test
	public void testUpsellingSuggestionFor2Dsession() {
		Response response = get("/AvantServices/upsellingSellingSuggestions/cinemaId/" + this.cinemaId + "/sessionId/" + this.sessionId + "/numberOfTickets/" + 2 + "/ticketPrice/" + 40000000 + ".json");
		
		assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	log.debug(json);
    	JsonPath jp = new JsonPath(json);
    	
    	assertEquals("Success", jp.get("model.message"));
	}
	
	@Test
	public void testUpsellingSuggestionFor3Dsession() {
		Response response = get("/AvantServices/upsellingSellingSuggestions/cinemaId/" + this.cinemaId + "/sessionId/" + this.session3DId + "/numberOfTickets/" + 2 + "/ticketPrice/" + 60000000 + ".json");
		
		assertEquals(200, response.getStatusCode());
    	String json = response.asString();
    	log.debug(json);
    	JsonPath jp = new JsonPath(json);
    	
    	assertEquals("Success", jp.get("model.message"));
	}

}
