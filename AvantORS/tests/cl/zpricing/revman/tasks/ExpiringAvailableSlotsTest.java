package cl.zpricing.revman.tasks;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class ExpiringAvailableSlotsTest{
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private ExpiringAvailableSlots expiringAvailableSlots;
	
	@Test
	public void testExpiringCapacity() {
		expiringAvailableSlots.execute();
		assertTrue(true);
	}
}
