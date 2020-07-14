package cl.zpricing.avant.test.unit;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.zpricing.avant.servicios.BusinessIntelligenceExtractionProcessDao;
import cl.zpricing.avant.servicios.BusinessIntelligenceProcessDao;
import cl.zpricing.avant.test.utils.DataLoad;
import cl.zpricing.commons.utils.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class ClientesPorZonaGeograficaTest {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private DataLoad dataLoad;
	
	@Autowired
	@Qualifier("businessIntelligenceProcessCineticketDao")
	private BusinessIntelligenceExtractionProcessDao businessIntelligenceExtractionProcessDao;
	
	@Autowired
	private BusinessIntelligenceProcessDao businessIntelligenceProcessDao;
	
	private int zonaGeograficaUltimaCompraClienteMultiplesCompras;
	private int zonaGeograficaCompraAntiguaClienteMultiplesCompras;
	private int zonaGeograficaClienteSinCompras;
	
	private int comunaId = 0;
	
	private String rutClienteConMultiplesCompras = "1-1";
	private String rutClienteSinCompras = "2-2";
	
	@Before
	public void before() throws ParseException {
		dataLoad.cleanTables();
		
		Calendar cal = DateUtils.obtenerCalendario();
		cal.add(Calendar.DATE, -7);
		Date sameDayOneWeekAgo = cal.getTime();
		cal.add(Calendar.DATE, -7);
		Date sameDayTwoWeeksAgo = cal.getTime();
		
		this.zonaGeograficaUltimaCompraClienteMultiplesCompras = dataLoad.insertarZonaGeografica("Zona Geografica A");
		this.zonaGeograficaCompraAntiguaClienteMultiplesCompras = dataLoad.insertarZonaGeografica("Zona Geografica B");
		this.zonaGeograficaClienteSinCompras = dataLoad.insertarZonaGeografica("Zona Geografica C");
		
		int complejoA = dataLoad.insertarComplejo("Complejo 1", "111", this.zonaGeograficaUltimaCompraClienteMultiplesCompras);
		int complejoB = dataLoad.insertarComplejo("Complejo 2", "222", this.zonaGeograficaCompraAntiguaClienteMultiplesCompras);
		
		dataLoad.insertarTransaccionTemporalClienteCineticket(rutClienteConMultiplesCompras, "a@a.com", "cliente 1", "Las Condes", "111", sameDayOneWeekAgo);
		dataLoad.insertarTransaccionTemporalClienteCineticket(rutClienteConMultiplesCompras, "a@a.com", "cliente 1", "Las Condes", "222", sameDayTwoWeeksAgo);
		
		this.comunaId = dataLoad.insertarComuna("Vitacura", this.zonaGeograficaClienteSinCompras);
		dataLoad.insertarCliente(rutClienteSinCompras, "b@b.b", "cliente 2", this.comunaId);
		
		businessIntelligenceExtractionProcessDao.agregarTransacciones();
		businessIntelligenceExtractionProcessDao.agregarComunas();
		businessIntelligenceExtractionProcessDao.agregarClientesNuevos();
		businessIntelligenceProcessDao.actualizarPreferenciasCineClientes(0.5);
		businessIntelligenceProcessDao.updateClientsCluster();
	}
	
	@Test
	public void ultimoCineVisitadoTienePreferenciaTest() {
		List<Map<String, Object>> result = dataLoad.obtenerClienteZonaGeografica();
		
		for (Map<String, Object> map : result) {
			String rutCliente = (String) map.get("rut");
			Integer zonaGeograficaId = (Integer) map.get("zona_geografica_id");
			
			if (rutCliente.equalsIgnoreCase(this.rutClienteConMultiplesCompras)) {
				assertTrue(zonaGeograficaId.intValue() == this.zonaGeograficaUltimaCompraClienteMultiplesCompras);
			}
			else if (rutCliente.equalsIgnoreCase(this.rutClienteSinCompras)) {
				assertTrue(zonaGeograficaId.intValue() == this.zonaGeograficaClienteSinCompras);
			}
		}
	}
}
