package cl.zpricing.avant.test.functional.dataload;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import cl.zpricing.avant.etl.model.Cluster;
import cl.zpricing.avant.etl.model.ClusterProduct;
import cl.zpricing.avant.negocio.bi.LoadBusinessIntelligenceData;
import cl.zpricing.avant.negocio.sincronizador.MetaProceso;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.test.utils.DataLoad;
import cl.zpricing.commons.utils.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class BusinessIntelligenceClustersJobTest {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("mongoIzyTemplate")
	private MongoOperations mongoTemplate;
	
	@Autowired
	private DataLoad dataLoad;
	
	@Autowired
	@Qualifier("processLoadClustersData")
	private MetaProceso procesoLoadClusters;
	
	@Autowired
	private LoadBusinessIntelligenceData loadBusinessIntelligenceData;
	
	private Proceso loadClustersProcess;
	
	@Before
	public void before() throws Exception {
		log.debug("before");
		dataLoad.cleanTables();
		mongoTemplate.dropCollection("segments");
		
		Map<String, Object> customSegment = new HashMap<String, Object>();
		customSegment.put("name", "Custom Segment");
		customSegment.put("description", "Description custom segment.");
		mongoTemplate.insert(customSegment, "segments");
		
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
		
		int zonaGeograficaSantiago = dataLoad.insertarZonaGeografica("Santiago");
		int zonaGeograficaArica = dataLoad.insertarZonaGeografica("Arica");
		dataLoad.insertarZonaGeografica(1, "N/A");
		
		int comunaLasCondes = dataLoad.insertarComuna("Las Condes", zonaGeograficaSantiago);
		int comunaArica = dataLoad.insertarComuna("Arica", zonaGeograficaArica);
		int clientWithComunaAndCategoryPreference = dataLoad.insertarCliente("1-9", "ndujovne@gmail.com", "Nicolas", comunaLasCondes);
		int clientWithoutComunaButWithCinemaPreference = dataLoad.insertarCliente("1-9", "jaiherna@gmail.com", "Jaime");
		int clientWithoutCategoryPreference = dataLoad.insertarCliente("1-9", "daniel.andrade@zpricing.com", "Daniel", comunaArica);
		int clientWithoutZonaGeograficaAndCinemaPreference = dataLoad.insertarCliente("1-9", "erwin.alvarez@zpricing.com", "Erwin");
		
		
		int complejoParqueArauco = dataLoad.insertarComplejo("Parque Arauco", "111", zonaGeograficaSantiago);
		int complejoArica = dataLoad.insertarComplejo("Arica", "222", zonaGeograficaArica);
		
		
		dataLoad.asociarCineCliente(clientWithoutComunaButWithCinemaPreference, complejoParqueArauco, 1.0);
		
		int grupoPeliculaId_1 = dataLoad.insertarGrupoPelicula("Grupo Pelicula 1");
		int pelicula_1 = dataLoad.crearPelicula35mm("Pelicula 1", "0001", grupoPeliculaId_1, complejoParqueArauco, 2);
		int pelicula_3 = dataLoad.crearPelicula35mm("Pelicula 1", "0003", grupoPeliculaId_1, complejoArica, 2);
		
		int grupoPeliculaId_2 = dataLoad.insertarGrupoPelicula("Grupo Pelicula 2");
		int pelicula_2 = dataLoad.crearPelicula35mm("Pelicula 2", "0002", grupoPeliculaId_2, complejoParqueArauco, 2);
		
		int grupoPeliculaId_3 = dataLoad.insertarGrupoPelicula("Grupo Pelicula 3");
		int pelicula_4 = dataLoad.crearPelicula35mm("Pelicula 4", "0004", grupoPeliculaId_3, complejoParqueArauco, 1);
		
		int grupoPeliculaId_4 = dataLoad.insertarGrupoPelicula("Grupo Pelicula 4");
		int pelicula_5 = dataLoad.crearPelicula35mm("Pelicula 5", "0005", grupoPeliculaId_4, complejoParqueArauco, 1);
		
		int grupoSalaId = dataLoad.insertarGrupoDeSala(complejoParqueArauco); 
		int salaId = dataLoad.insertarSala(grupoSalaId, complejoParqueArauco);
		
		int grupoSalaId_2 = dataLoad.insertarGrupoDeSala(complejoArica);
		int salaId_2 = dataLoad.insertarSala(grupoSalaId_2, complejoArica);
		
		dataLoad.crearFuncion(complejoParqueArauco, salaId, sameDayAsTodayLastWeek, "23:59:59", pelicula_1);
		dataLoad.crearFuncion(complejoParqueArauco, salaId, sameDayAsTodayLastWeek, "23:59:59", pelicula_5);
		dataLoad.crearFuncion(complejoParqueArauco, salaId, tomorrow, "23:59:59", pelicula_1);
		dataLoad.crearFuncion(complejoParqueArauco, salaId, tomorrow, "23:59:59", pelicula_2);
		dataLoad.crearFuncion(complejoParqueArauco, salaId, tomorrow, "23:59:59", pelicula_4);
		dataLoad.crearFuncion(complejoParqueArauco, salaId, tomorrow, "23:59:59", pelicula_5);
		dataLoad.crearFuncion(complejoArica, salaId_2, tomorrow, "23:59:59", pelicula_3);
		
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
		
		dataLoad.asociarGrupoPeliculaCategoria(grupoPeliculaId_1, categoriaAccion);
		dataLoad.asociarGrupoPeliculaCategoria(grupoPeliculaId_1, categoriaDrama);
		dataLoad.asociarGrupoPeliculaCategoria(grupoPeliculaId_2, categoriaComedia);
		dataLoad.asociarGrupoPeliculaCategoria(grupoPeliculaId_3, categoriaDocumental);
		dataLoad.asociarGrupoPeliculaCategoria(grupoPeliculaId_4, categoriaComedia);
		
		loadBusinessIntelligenceData.buildClusters();
		
		this.loadClustersProcess = procesoLoadClusters.ejecutarProceso();
		this.loadClustersProcess.setCodigo(procesoLoadClusters.getCodigo());
		this.loadClustersProcess.run();
	}
	
	@Test
	public void loadClustersProcessSuccess() {
		assertTrue(this.loadClustersProcess.isSuccessful());
	}
	@Test
	public void doNotDeleteCustomSegments() {
		long count = mongoTemplate.count(new Query(Criteria.where("name").is("Custom Segment")),"segments");
		assertEquals(1, count);
	}
	
	@Test
	public void createGeographicalClusters() {
		long count = mongoTemplate.count(new Query(Criteria.where("name").is("Todos por Zona Geografica")), "segments");
		assertEquals(3, count);
	}
	
	@Test
	public void createGeographicalAndCategoryClusters() {
		long count = mongoTemplate.count(new Query(Criteria.where("name").is("Todos por Zona Geografica por Categoria")), "segments");
		assertEquals(12, count);
	}
	
	@Test
	public void segmentFromZonaGeograficaSantiagoShouldHaveOneShowingAndOnePremiere() {
		Cluster cluster = (Cluster) mongoTemplate.findOne(new Query(Criteria.where("name").is("Todos por Zona Geografica").and("description").is("Santiago")), Cluster.class, "segments");
		List<ClusterProduct> showing = (List<ClusterProduct>) cluster.getProducts().get("showing");
		List<ClusterProduct> premiere = (List<ClusterProduct>) cluster.getProducts().get("premiere");
		assertEquals(2, showing.size());
		assertEquals(2, premiere.size());
	}
	@Test
	public void segmentFromAricaShouldOnlyHaveOneMovieOnPremiere() {
		Cluster cluster = (Cluster) mongoTemplate.findOne(new Query(Criteria.where("name").is("Todos por Zona Geografica").and("description").is("Arica")), Cluster.class, "segments");
		List<LinkedHashMap<String, Object>> showing = (List<LinkedHashMap<String, Object>>) cluster.getProducts().get("showing");
		List<LinkedHashMap<String, Object>> premiere = (List<LinkedHashMap<String, Object>>) cluster.getProducts().get("premiere");
		assertEquals(0, showing.size());
		assertEquals(1, premiere.size());
		
		assertEquals("Grupo Pelicula 1" , premiere.get(0).get("name"));
	}
	@Test
	public void segmentWithoutZonaGeograficaShouldHaveOneShowingAndOnePremiere() {
		Cluster cluster = (Cluster) mongoTemplate.findOne(new Query(Criteria.where("name").is("Todos por Zona Geografica").and("description").is("N/A")), Cluster.class, "segments");
		List<ClusterProduct> showing = (List<ClusterProduct>) cluster.getProducts().get("showing");
		List<ClusterProduct> premiere = (List<ClusterProduct>) cluster.getProducts().get("premiere");
		assertEquals(2, showing.size());
		assertEquals(2, premiere.size());
	}
	@Test
	public void moviesShowingFromSantiagoShouldBeInRankingOrder() {
		Cluster cluster = (Cluster) mongoTemplate.findOne(new Query(Criteria.where("name").is("Todos por Zona Geografica").and("description").is("Santiago")), Cluster.class, "segments");
		List<LinkedHashMap<String, Object>> showing = (List<LinkedHashMap<String, Object>>) cluster.getProducts().get("showing");
		
		String movieName = (String) showing.get(0).get("name");
		assertEquals("Movie should be 'Grupo Pelicula 4'", "Grupo Pelicula 4", movieName);
	}
	@Test
	public void moviesPremiereFromSantiagoShouldBeInRankingOrder() {
		Cluster cluster = (Cluster) mongoTemplate.findOne(new Query(Criteria.where("name").is("Todos por Zona Geografica").and("description").is("Santiago")), Cluster.class, "segments");
		List<LinkedHashMap<String, Object>> premieres = (List<LinkedHashMap<String, Object>>) cluster.getProducts().get("premiere");
		
		String movieName = (String) premieres.get(0).get("name");
		assertEquals("Movie should be 'Grupo Pelicula 3'", "Grupo Pelicula 3", movieName);
	}
	
	@Test
	public void moviesShowingFromSantiagoAccionShouldBeOnCategoryOrder() {
		Cluster cluster = (Cluster) mongoTemplate.findOne(new Query(Criteria.where("name").is("Todos por Zona Geografica por Categoria").and("description").is("Santiago_Accion")), Cluster.class, "segments");
		List<LinkedHashMap<String, Object>> showing = (List<LinkedHashMap<String, Object>>) cluster.getProducts().get("showing");
		
		String movieName = (String) showing.get(0).get("name");
		assertEquals("Movie should be 'Grupo Pelicula 1'", "Grupo Pelicula 1", movieName);
	}
	@Test
	public void moviesPremiereFromSantiagoComediaShouldBeOnCategoryOrder() {
		Cluster cluster = (Cluster) mongoTemplate.findOne(new Query(Criteria.where("name").is("Todos por Zona Geografica por Categoria").and("description").is("Santiago_Comedia")), Cluster.class, "segments");
		List<LinkedHashMap<String, Object>> premieres = (List<LinkedHashMap<String, Object>>) cluster.getProducts().get("premiere");
		
		String movieName = (String) premieres.get(0).get("name");
		assertEquals("Movie should be 'Grupo Pelicula 2'", "Grupo Pelicula 2", movieName);
	}
}
