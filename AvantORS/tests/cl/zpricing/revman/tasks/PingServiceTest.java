package cl.zpricing.revman.tasks;

import org.apache.log4j.Logger;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


public class PingServiceTest extends AbstractTransactionalDataSourceSpringContextTests {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private PingService pingService;	

	public void testSendPing() {
		boolean result = pingService.sendPing();
		assertEquals(true, result);
	}

	public void setPingService(PingService pingService) {
		this.pingService = pingService;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-ibatis.xml"};
	}
}
