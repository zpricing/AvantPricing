package cl.zpricing.avant.test.functional;

import static org.junit.Assert.*;

import java.text.ParseException;


import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.zpricing.avant.negocio.sincronizador.MetaProceso;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.test.utils.DataLoad;
import scriptella.execution.EtlExecutorException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class ExtractDataFromVistaLoyaltyTest {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("procesoLoyaltyDataExtractionManager")
	private MetaProceso procesoLoyaltyDataExtractionManager;
	
	@Autowired
	private DataLoad dataLoad;
	
	@Before
	public void before() {
		dataLoad.cleanTables();
		dataLoad.insertarZonaGeografica(1, "N/A");
		dataLoad.insertarComuna("N/A", 1);
	}

	@Test
	public void testCineticketDataExtraction() throws EtlExecutorException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, ParseException {
		Proceso proceso = procesoLoyaltyDataExtractionManager.ejecutarProceso();
		proceso.setCodigo(procesoLoyaltyDataExtractionManager.getCodigo());
		proceso.run();
		
		assertTrue(proceso.isSuccessful());
	}
}
