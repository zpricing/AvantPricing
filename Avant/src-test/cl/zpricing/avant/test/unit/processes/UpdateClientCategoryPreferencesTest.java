package cl.zpricing.avant.test.unit.processes;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.zpricing.avant.negocio.sincronizador.MetaProceso;
import cl.zpricing.avant.negocio.sincronizador.Proceso;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class UpdateClientCategoryPreferencesTest {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("processUpdateClientCategoryPreferences")
	private MetaProceso processUpdateClientCategoryPreferences;
	
	@Test
	public void clientCategoryPreferencesTest() {
		log.debug("Init clientCategoryPreferencesTest");
		
		Proceso proceso = processUpdateClientCategoryPreferences.ejecutarProceso();
		proceso.setCodigo(processUpdateClientCategoryPreferences.getCodigo());
		proceso.run();
		
		assertTrue(proceso.isSuccessful());
	}

}
