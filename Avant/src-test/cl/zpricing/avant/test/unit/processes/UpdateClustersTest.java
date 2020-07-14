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
public class UpdateClustersTest {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("processUpdateClusters")
	private MetaProceso processUpdateClusters;
	
	@Test
	public void updateClustersTest() {
		log.debug("Init updateClustersTest");
		
		Proceso proceso = processUpdateClusters.ejecutarProceso();
		proceso.setCodigo(processUpdateClusters.getCodigo());
		proceso.run();
		
		assertTrue(proceso.isSuccessful());
	}

}
