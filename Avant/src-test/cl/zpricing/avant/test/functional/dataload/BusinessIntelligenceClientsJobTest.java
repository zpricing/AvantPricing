package cl.zpricing.avant.test.functional.dataload;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.zpricing.avant.etl.model.ClientSegment;
import cl.zpricing.avant.etl.model.Cluster;
import cl.zpricing.avant.etl.model.ClusterClient;
import cl.zpricing.avant.etl.model.Tag;
import cl.zpricing.avant.negocio.sincronizador.MetaProceso;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.test.utils.DataLoad;
import cl.zpricing.commons.utils.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class BusinessIntelligenceClientsJobTest {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("mongoIzyTemplate")
	private MongoOperations mongoTemplate;
	
	@Autowired
	private DataLoad dataLoad;
	
	@Autowired
	@Qualifier("processLoadBusinessIntelligenceData")
	private MetaProceso processLoadBusinessIntelligenceData;
	
	Proceso proceso;
	
	@Before
	public void before() throws ParseException {
		log.debug("before");
		dataLoad.cleanTables();
		mongoTemplate.dropCollection("clients");
		mongoTemplate.dropCollection("clients_temp");
		
		
		Calendar cal = DateUtils.obtenerCalendario();
		Date today = cal.getTime();
		cal.add(Calendar.DATE, -7);
		Date sameDayAsTodayLastWeek = cal.getTime();
		cal.add(Calendar.DATE, 8);
		Date tomorrow = cal.getTime();
		cal.add(Calendar.DATE, 7);
		Date samDayAsTomorrowNextWeek = cal.getTime();
		
		dataLoad.crearSemanaCine(sameDayAsTodayLastWeek, today);
		dataLoad.crearSemanaCine(tomorrow, samDayAsTomorrowNextWeek);
		
		
		
		
		ClusterClient client = new ClusterClient();
		client.setEmail("ndujovne@gmail.com");
		client.setName("Nicolas Eduardo");
		List<Tag> tags = new ArrayList<Tag>();
		Tag tag = new Tag();
		tag.setType("campañas");
		tag.setValue("USACH");
		tags.add(tag);
		Tag tagDynamic = new Tag();
		tagDynamic.setType("Cine");
		tagDynamic.setValue("Dominicos");
		tagDynamic.setOrigin("dynamic");
		tags.add(tagDynamic);
		client.setTags(tags);
		mongoTemplate.insert(client, "clients");
		
		int zonaGeograficaSantiago = dataLoad.insertarZonaGeografica("Santiago");
		int zonaGeograficaArica = dataLoad.insertarZonaGeografica("Arica");
		int zonaGeograficaAntofagasta = dataLoad.insertarZonaGeografica("Antofagasta");
		dataLoad.insertarZonaGeografica(1, "N/A");
		
		int comunaLasCondes = dataLoad.insertarComuna("Las Condes", zonaGeograficaSantiago);
		int comunaMaipu = dataLoad.insertarComuna("Maipu", zonaGeograficaSantiago);
		int comunaAntofagasta = dataLoad.insertarComuna("Las Condes", zonaGeograficaAntofagasta);
		int comunaArica = dataLoad.insertarComuna("Arica", zonaGeograficaArica);
		
		int clientWithComunaAndCategoryPreference = dataLoad.insertarCliente("1-9", "ndujovne@gmail.com", "Nicolas", comunaLasCondes);
		int clientWithoutComunaButWithCinemaPreference = dataLoad.insertarCliente("1-9", "jaiherna@gmail.com", "Jaime");
		int clientWithoutCategoryPreference = dataLoad.insertarCliente("1-9", "daniel.andrade@zpricing.com", "Daniel", comunaArica);
		int clientWithoutZonaGeograficaAndCinemaPreference = dataLoad.insertarCliente("1-9", "erwin.alvarez@zpricing.com", "Erwin");
		
		
		int complejoParqueArauco = dataLoad.insertarComplejo("Parque Arauco", "013", zonaGeograficaSantiago);
		int complejoLaReina = dataLoad.insertarComplejo("La Reina", "006", zonaGeograficaSantiago);
		int complejoAntofagastaLider = dataLoad.insertarComplejo("Antofagasta Lider", "023", zonaGeograficaAntofagasta);
		int complejoAraucoMaipu = dataLoad.insertarComplejo("Arauco Maipu", "014", zonaGeograficaSantiago);
		int complejoArica = dataLoad.insertarComplejo("Arica", "222", zonaGeograficaArica);
		
		dataLoad.asociarCineCliente(clientWithComunaAndCategoryPreference, complejoParqueArauco, 0.3);
		dataLoad.asociarCineCliente(clientWithComunaAndCategoryPreference, complejoLaReina, 0.1);
		dataLoad.asociarCineCliente(clientWithoutComunaButWithCinemaPreference, complejoAraucoMaipu, 3);
		dataLoad.asociarCineCliente(clientWithoutCategoryPreference, complejoAntofagastaLider, 0.2);
		
		int grupoPeliculaIronMan = dataLoad.insertarGrupoPelicula("Iron Man");
		int grupoPeliculaAvatar = dataLoad.insertarGrupoPelicula("Avatar");
		
		int peliculaIronMan = dataLoad.crearPelicula35mm("Iron Man 3 (SUBT)", "0001", grupoPeliculaIronMan, complejoParqueArauco, 1);
		int peliculaAvatarSantiago = dataLoad.crearPelicula35mm("Avatar 3D", "0002", grupoPeliculaAvatar, complejoParqueArauco, 2);
		int peliculaAvatarArica = dataLoad.crearPelicula35mm("Avatar 3D", "0002", grupoPeliculaAvatar, complejoArica, 2);
		
		dataLoad.asociarClientePelicula(clientWithComunaAndCategoryPreference, peliculaIronMan);
		dataLoad.asociarClientePelicula(clientWithComunaAndCategoryPreference, peliculaAvatarSantiago);
		
		int grupoSalaSantiago = dataLoad.insertarGrupoDeSala(complejoParqueArauco); 
		int salaSantiago = dataLoad.insertarSala(grupoSalaSantiago, complejoParqueArauco);
		
		int grupoSalaArica = dataLoad.insertarGrupoDeSala(complejoArica);
		int salaArica= dataLoad.insertarSala(grupoSalaArica, complejoArica);
		
		dataLoad.crearFuncion(complejoParqueArauco, salaSantiago, sameDayAsTodayLastWeek, "23:59:59", peliculaIronMan);
		dataLoad.crearFuncion(complejoParqueArauco, salaSantiago, tomorrow, "23:59:59", peliculaIronMan);
		dataLoad.crearFuncion(complejoParqueArauco, salaSantiago, tomorrow, "23:59:59", peliculaAvatarSantiago);
		dataLoad.crearFuncion(complejoArica, salaArica, tomorrow, "23:59:59", peliculaAvatarArica);
		
		int categoriaAccion = dataLoad.crearCategoria("Accion", 0.4);
		int categoriaComedia = dataLoad.crearCategoria("Comedia", 0.2);
		int categoriaDrama = dataLoad.crearCategoria("Drama", 0.3);
		int categoriaDocumental = dataLoad.crearCategoria("Documental", 0.1);
		
		dataLoad.asociarSimilitudCategorias(categoriaAccion, categoriaAccion, 1);
		dataLoad.asociarSimilitudCategorias(categoriaComedia, categoriaComedia, 1);
		dataLoad.asociarSimilitudCategorias(categoriaDrama, categoriaDrama, 1);
		dataLoad.asociarSimilitudCategorias(categoriaDocumental, categoriaDocumental, 1);
		
		dataLoad.asociarSimilitudCategorias(categoriaAccion, categoriaComedia, 0.6);
		dataLoad.asociarSimilitudCategorias(categoriaAccion, categoriaDrama, 0.7);
		dataLoad.asociarSimilitudCategorias(categoriaAccion, categoriaDocumental, 0.4);
		dataLoad.asociarSimilitudCategorias(categoriaComedia, categoriaDrama, 0.4);
		dataLoad.asociarSimilitudCategorias(categoriaComedia, categoriaDocumental, 0.4);
		dataLoad.asociarSimilitudCategorias(categoriaDrama, categoriaDocumental, 0.8);
		
		dataLoad.asociarClienteCategoria(clientWithComunaAndCategoryPreference, categoriaAccion, 1);
		dataLoad.asociarClienteCategoria(clientWithComunaAndCategoryPreference, categoriaComedia, 2);
		dataLoad.asociarClienteCategoria(clientWithoutComunaButWithCinemaPreference, categoriaComedia, 1);
		dataLoad.asociarClienteCategoria(clientWithoutComunaButWithCinemaPreference, categoriaDocumental, 2);
		
		dataLoad.asociarGrupoPeliculaCategoria(grupoPeliculaIronMan, categoriaAccion);
		dataLoad.asociarGrupoPeliculaCategoria(grupoPeliculaIronMan, categoriaDrama);
		dataLoad.asociarGrupoPeliculaCategoria(grupoPeliculaAvatar, categoriaComedia);
		
		
		this.proceso = processLoadBusinessIntelligenceData.ejecutarProceso();
		this.proceso.setCodigo(processLoadBusinessIntelligenceData.getCodigo());
		this.proceso.run();
	}
	
	@Test
	public void testLoadClientsProcessSuccess() {
		assertTrue(this.proceso.isSuccessful());
	}
	
	@Test
	public void testNameUpdate() {
		ClusterClient existingClient = (ClusterClient) mongoTemplate.findOne(new Query( Criteria.where("email").is("ndujovne@gmail.com")), ClusterClient.class, "clients");
		assertEquals("Nicolas", existingClient.getName());
	}
	
	@Test
	public void shouldNotDuplicateClients() {
		long count =  mongoTemplate.count(new Query( Criteria.where("email").is("ndujovne@gmail.com")), "clients");
		assertEquals(1, count);
	}
	
	@Test
	public void testNotToDeleteExistingCustomTags() {
		ClusterClient existingClient = (ClusterClient) mongoTemplate.findOne(new Query( Criteria.where("email").is("ndujovne@gmail.com")), ClusterClient.class, "clients");
		log.debug("  # of tags: " + existingClient.getTags().size());
		boolean existsTagUsach = false;
		for (Tag tag : existingClient.getTags()) {
			log.debug("  tag type: " + tag.getType());
			if (tag.getType().equalsIgnoreCase("campañas") && tag.getValue().equalsIgnoreCase("USACH")) {
				existsTagUsach = true;
				break;
			}
		}
		assertTrue(existsTagUsach);
	}
	
	@Test
	public void shouldAddNewDynamicTags() {
		ClusterClient existingClient = (ClusterClient) mongoTemplate.findOne(new Query( Criteria.where("email").is("ndujovne@gmail.com")), ClusterClient.class, "clients");
		assertEquals(7, existingClient.getTags().size());
	}
	
	@Test
	public void testDeleteExistingDynamicTag() {
		ClusterClient existingClient = (ClusterClient) mongoTemplate.findOne(new Query( Criteria.where("email").is("ndujovne@gmail.com")), ClusterClient.class, "clients");
		boolean existsTagDominicos = true;
		for (Tag tag : existingClient.getTags()) {
			if (tag.getType().equalsIgnoreCase("cine") && tag.getValue().equalsIgnoreCase("Dominicos")) {
				existsTagDominicos = false;
				break;
			}
		}
		assertTrue(existsTagDominicos);
	}
	@Test
	public void clientWithoutComunaAndWithCinemaPreferenceShouldBeInSegmentSantiagoComedia() {
		ClusterClient client = (ClusterClient) mongoTemplate.findOne(new Query(Criteria.where("email").is("jaiherna@gmail.com")), ClusterClient.class, "clients");
		
		boolean hasSegmentSantiagoComedia = false;
		boolean hasSegmentSantiago = false;
		int numbewOfSegmentsPorZonaGeografica = 0;
		int numbewOfSegmentsPorZonaGeograficaPorCategoria = 0;
		
		assertNotNull("Client exists", client);
		assertNotNull("Client has a list segments", client.getSegments());
		
		for (ClientSegment segment : client.getSegments()) {
			Cluster cluster = (Cluster) mongoTemplate.findOne(new Query(Criteria.where("_id").is(segment.getId())), Cluster.class, "segments");
			
			if (cluster.getName().equalsIgnoreCase("Todos por Zona Geografica por Categoria")) {
				numbewOfSegmentsPorZonaGeograficaPorCategoria++;
				if (cluster.getDescription().equalsIgnoreCase("Santiago_Comedia")) {
					hasSegmentSantiagoComedia = true;
				}
			}
			else if (cluster.getName().equalsIgnoreCase("Todos por Zona Geografica")) {
				numbewOfSegmentsPorZonaGeografica++;
				if (cluster.getDescription().equalsIgnoreCase("Santiago")) {
					hasSegmentSantiago = true;
				}
			}
		}
		
		assertTrue("client must be in segment Santiago Comedia", hasSegmentSantiagoComedia);
		assertTrue("client must be in segment Santiago", hasSegmentSantiago);
		assertEquals("count Zona Geografica por categiria should be 1", 1, numbewOfSegmentsPorZonaGeograficaPorCategoria);
		assertEquals("count Zona Geografica should be 1", 1, numbewOfSegmentsPorZonaGeografica);
	}
	@Test
	public void clientWithoutCategoryPreferenceShouldBeInSegmentAricaSinCategoria() {
		ClusterClient client = (ClusterClient) mongoTemplate.findOne(new Query(Criteria.where("email").is("daniel.andrade@zpricing.com")), ClusterClient.class, "clients");
		
		boolean hasSegmentSantiagoComedia = false;
		boolean hasSegmentSantiago = false;
		int numbewOfSegmentsPorZonaGeografica = 0;
		int numbewOfSegmentsPorZonaGeograficaPorCategoria = 0;
		
		assertNotNull("Client exists", client);
		assertNotNull("Client has a list segments", client.getSegments());
		
		for (ClientSegment segment : client.getSegments()) {
			Cluster cluster = (Cluster) mongoTemplate.findOne(new Query(Criteria.where("_id").is(segment.getId())), Cluster.class, "segments");
			
			if (cluster.getName().equalsIgnoreCase("Todos por Zona Geografica por Categoria")) {
				numbewOfSegmentsPorZonaGeograficaPorCategoria++;
				if (cluster.getDescription().equalsIgnoreCase("Antofagasta_SinCategoria")) {
					hasSegmentSantiagoComedia = true;
				}
			}
			else if (cluster.getName().equalsIgnoreCase("Todos por Zona Geografica")) {
				numbewOfSegmentsPorZonaGeografica++;
				if (cluster.getDescription().equalsIgnoreCase("Antofagasta")) {
					hasSegmentSantiago = true;
				}
			}
		}
		
		assertTrue("client must be in segment Antofagasta SinCategoria", hasSegmentSantiagoComedia);
		assertTrue("client must be in segment Antofagasta", hasSegmentSantiago);
		assertEquals("count Zona Geografica por categoria should be 1", 1, numbewOfSegmentsPorZonaGeograficaPorCategoria);
		assertEquals("count Zona Geografica should be 1", 1, numbewOfSegmentsPorZonaGeografica);
	}
	@Test
	public void clientWithoutZonaGeograficaAndCinemaPreferenceShouldBeInSegmentSinZonaSinCategoria() {
		ClusterClient client = (ClusterClient) mongoTemplate.findOne(new Query(Criteria.where("email").is("erwin.alvarez@zpricing.com")), ClusterClient.class, "clients");
		
		boolean hasSegmentSantiagoComedia = false;
		boolean hasSegmentSantiago = false;
		int numbewOfSegmentsPorZonaGeografica = 0;
		int numbewOfSegmentsPorZonaGeograficaPorCategoria = 0;
		
		assertNotNull("Client exists", client);
		assertNotNull("Client has a list segments", client.getSegments());
		
		for (ClientSegment segment : client.getSegments()) {
			Cluster cluster = (Cluster) mongoTemplate.findOne(new Query(Criteria.where("_id").is(segment.getId())), Cluster.class, "segments");
			
			if (cluster.getName().equalsIgnoreCase("Todos por Zona Geografica por Categoria")) {
				numbewOfSegmentsPorZonaGeograficaPorCategoria++;
				if (cluster.getDescription().equalsIgnoreCase("N/A_SinCategoria")) {
					hasSegmentSantiagoComedia = true;
				}
			}
			else if (cluster.getName().equalsIgnoreCase("Todos por Zona Geografica")) {
				numbewOfSegmentsPorZonaGeografica++;
				if (cluster.getDescription().equalsIgnoreCase("N/A")) {
					hasSegmentSantiago = true;
				}
			}
		}
		
		assertTrue("client must be in segment N/A SinCategoria", hasSegmentSantiagoComedia);
		assertTrue("client must be in segment N/A", hasSegmentSantiago);
		assertEquals("count Zona Geografica por categoria should be 1", 1, numbewOfSegmentsPorZonaGeograficaPorCategoria);
		assertEquals("count Zona Geografica should be 1", 1, numbewOfSegmentsPorZonaGeografica);
	}
	@Test
	public void clientWithComunaAndCategoryPreferenceShouldBeInSegmentSantiagoAccion() {
		ClusterClient client = (ClusterClient) mongoTemplate.findOne(new Query(Criteria.where("email").is("ndujovne@gmail.com")), ClusterClient.class, "clients");
		
		boolean hasSegmentSantiagoComedia = false;
		boolean hasSegmentSantiago = false;
		int numbewOfSegmentsPorZonaGeografica = 0;
		int numbewOfSegmentsPorZonaGeograficaPorCategoria = 0;
		
		assertNotNull("Client exists", client);
		assertNotNull("Client has a list segments", client.getSegments());
		
		for (ClientSegment segment : client.getSegments()) {
			Cluster cluster = (Cluster) mongoTemplate.findOne(new Query(Criteria.where("_id").is(segment.getId())), Cluster.class, "segments");
			
			if (cluster.getName().equalsIgnoreCase("Todos por Zona Geografica por Categoria")) {
				numbewOfSegmentsPorZonaGeograficaPorCategoria++;
				if (cluster.getDescription().equalsIgnoreCase("Santiago_Accion")) {
					hasSegmentSantiagoComedia = true;
				}
			}
			else if (cluster.getName().equalsIgnoreCase("Todos por Zona Geografica")) {
				numbewOfSegmentsPorZonaGeografica++;
				if (cluster.getDescription().equalsIgnoreCase("Santiago")) {
					hasSegmentSantiago = true;
				}
			}
		}
		
		assertTrue("client must be in segment Santiago Accion", hasSegmentSantiagoComedia);
		assertTrue("client must be in segment Santiago", hasSegmentSantiago);
		assertEquals("count Zona Geografica por categoria should be 1", 1, numbewOfSegmentsPorZonaGeograficaPorCategoria);
		assertEquals("count Zona Geografica should be 1", 1, numbewOfSegmentsPorZonaGeografica);
	}
	@Test
	public void shouldCreateNewClients() {
		long numberOfClients = mongoTemplate.count(new Query(), "clients");
		assertEquals(4, numberOfClients);
	}
	@Test
	public void shouldCreateNewClientsWithTags() {
		ClusterClient existingClient = (ClusterClient) mongoTemplate.findOne(new Query( Criteria.where("email").is("jaiherna@gmail.com")), ClusterClient.class, "clients");
		assertNotNull(existingClient);
		assertEquals(4, existingClient.getTags().size());
	}
}
