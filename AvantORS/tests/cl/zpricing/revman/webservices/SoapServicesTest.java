package cl.zpricing.revman.webservices;

import static org.junit.Assert.*;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cl.zpricing.test.utils.SoapClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class SoapServicesTest {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private SoapClient soapClient = new SoapClient();
	
	@Before
	public void before() {
		
	}
	
	@After
	public void after() {
		
	}
		
	@Test
	public void getRevenueManagementAvailabilityPerDay() throws SOAPException {
		SOAPBody soapBody = soapClient.getRevenueManagementAvailabilityPerDay("getRevenueManagementAvailabilityPerDay", "006", "HO00001009", "2013-07-15");
		
		Node message = soapBody.getElementsByTagName("message").item(0);
		log.debug(message.getTextContent());
		assertEquals("mensaje de respuesta", "Success", message.getTextContent());
	}
	
	@Test
	public void getRevenueManagementAvailability() throws SOAPException {
		SOAPBody soapBody = soapClient.getRevenueManagementAvailability("getRevenueManagementAvailability", "003", "59562");
		
		Node message = soapBody.getElementsByTagName("message").item(0);
		log.debug(message.getTextContent());
		assertEquals("mensaje de respuesta", "Success", message.getTextContent());
		
		NodeList elementsDetalleCupos = soapBody.getElementsByTagName("detalleCupos");
		
		assertEquals("Validando existencia de detalle cupos", 3, elementsDetalleCupos.getLength());
		
		NodeList elementsPurchase = soapBody.getElementsByTagName("purchase");
		log.debug("# of purchases : " + elementsPurchase.getLength());
		assertEquals("Validando existencia de cupos purchase", 3, elementsPurchase.getLength());
		
		NodeList elementsTicketTypeCode = soapBody.getElementsByTagName("ticketTypeCode");
		log.debug("# of ticketTypeCode : " + elementsTicketTypeCode.getLength());
		assertEquals("Validando existencia de cupos ticketTypeCode", 3, elementsTicketTypeCode.getLength());
	}
}
