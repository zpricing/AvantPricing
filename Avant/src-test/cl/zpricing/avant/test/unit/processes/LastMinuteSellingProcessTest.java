package cl.zpricing.avant.test.unit.processes;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.negocio.sincronizador.MetaProceso;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.test.utils.DataLoad;
import cl.zpricing.commons.utils.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class LastMinuteSellingProcessTest {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private DataLoad dataLoad;
	
	@Autowired
	private FuncionDao funcionDao;
	
	@Autowired
	@Qualifier("procesoLastMinuteSelling")
	private MetaProceso lastMinuteSelling;
	
	private Proceso lastMinuteSellingProcess;
	
	private int complejoParqueArauco;
	
	@Before
	public void before() throws ParseException {
		log.debug("before Last Minute Test");
		dataLoad.cleanTables();
		Calendar cal = DateUtils.obtenerCalendario();
		Date today = cal.getTime();
		
		int zonaGeograficaSantiago = dataLoad.insertarZonaGeografica("Santiago");
		complejoParqueArauco = dataLoad.insertarComplejo("Parque Arauco", "111", zonaGeograficaSantiago);
		
		dataLoad.crearMascaraLastMinute(complejoParqueArauco);
		int mascaraId = dataLoad.crearMascara("Mascara A ", complejoParqueArauco);
		
		int priceCardId = dataLoad.crearPriceCard("0001", "full", complejoParqueArauco);
		
		int grupoSalaId = dataLoad.insertarGrupoDeSala(complejoParqueArauco); 
		int salaId = dataLoad.insertarSala(grupoSalaId, complejoParqueArauco);
		
		int ticketAdultoId = dataLoad.crearTicketAdulto("Adulto testing", "0001", complejoParqueArauco);
		int ticketLightId = dataLoad.crearTicketLight("Light testing", "0002", complejoParqueArauco);
		int ticketSaverId = dataLoad.crearTicketEconomico("Saver testing", "0003", complejoParqueArauco);
		int ticketSuperSaverId = dataLoad.crearTicketSuperEconomico("Super Saver testing", "0004", complejoParqueArauco);
		
		dataLoad.asociarTicketMascara(ticketSuperSaverId, mascaraId, 4, 2, salaId);
		dataLoad.asociarTicketMascara(ticketSaverId, mascaraId, 8, 1, salaId);
		dataLoad.asociarTicketMascara(ticketLightId, mascaraId, 12, 0, salaId);
		
		int grupoPeliculaId_1 = dataLoad.insertarGrupoPelicula("Grupo Pelicula 1");
		int pelicula_1 = dataLoad.crearPelicula35mm("Pelicula 1", "0001", grupoPeliculaId_1, complejoParqueArauco, 2);
		
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "10:00:00", pelicula_1, priceCardId, mascaraId, "000001");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "10:00:00", pelicula_1, priceCardId, mascaraId, "000002");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "12:00:00", pelicula_1, priceCardId, mascaraId, "000003");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "14:00:00", pelicula_1, priceCardId, mascaraId, "000004");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "16:00:00", pelicula_1, priceCardId, mascaraId, "000005");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "18:00:00", pelicula_1, priceCardId, mascaraId, "000006");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "20:00:00", pelicula_1, priceCardId, mascaraId, "000007");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "22:00:00", pelicula_1, priceCardId, mascaraId, "000008");
		
		int grupoPeliculaId_2 = dataLoad.insertarGrupoPelicula("Grupo Pelicula 2");
		int pelicula_2 = dataLoad.crearPelicula35mm("Pelicula 2", "0002", grupoPeliculaId_2, complejoParqueArauco, 2);
		
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "10:00:00", pelicula_2, priceCardId, mascaraId, "000009");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "12:00:00", pelicula_2, priceCardId, mascaraId, "000010");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "14:00:00", pelicula_2, priceCardId, mascaraId, "000011");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "16:00:00", pelicula_2, priceCardId, mascaraId, "000012");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "18:00:00", pelicula_2, priceCardId, mascaraId, "000013");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "20:00:00", pelicula_2, priceCardId, mascaraId, "000014");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "22:00:00", pelicula_2, priceCardId, mascaraId, "000015");
		
		
		int grupoPeliculaId_3 = dataLoad.insertarGrupoPelicula("Grupo Pelicula 3");
		int pelicula_3 = dataLoad.crearPelicula35mm("Pelicula 3", "0003", grupoPeliculaId_3, complejoParqueArauco, 2);
		
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "10:00:00", pelicula_3, priceCardId, mascaraId, "000016");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "12:00:00", pelicula_3, priceCardId, mascaraId, "000017");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "14:00:00", pelicula_3, priceCardId, mascaraId, "000018");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "16:00:00", pelicula_3, priceCardId, mascaraId, "000019");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "18:00:00", pelicula_3, priceCardId, mascaraId, "000020");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "20:00:00", pelicula_3, priceCardId, mascaraId, "000021");
		dataLoad.crearFuncion(complejoParqueArauco, salaId, today, "22:00:00", pelicula_3, priceCardId, mascaraId, "000022");
		
		this.lastMinuteSellingProcess = lastMinuteSelling.ejecutarProceso();
		this.lastMinuteSellingProcess.setCodigo(lastMinuteSelling.getCodigo());
		this.lastMinuteSellingProcess.run();
	}
	
	@Test
	public void clientCategoryPreferencesTest() {
		log.debug("Init clientCategoryPreferencesTest");
		assertTrue(this.lastMinuteSellingProcess.isSuccessful());
	}
	
	@Test
	public void shouldExistAtLeastOnelastMinuteSessions() {
		Complejo complejo = new Complejo();
		complejo.setId(this.complejoParqueArauco);
		Calendar cal = DateUtils.obtenerCalendario();
		Date today = cal.getTime();
		List<Funcion> funciones = funcionDao.obtenerFunciones(complejo, today);
		
		int lastMinuteSessionsCount = 0;
		
		for (Funcion funcion : funciones) {
			if (funcion.getMascara().getDescripcion().equalsIgnoreCase("LM")) {
				lastMinuteSessionsCount++;
			}
		}
		log.debug("  # de funciones de last minute selling : " + lastMinuteSessionsCount);
		assertTrue("Should have at least 1 last minute session", lastMinuteSessionsCount > 0);
	}

}
