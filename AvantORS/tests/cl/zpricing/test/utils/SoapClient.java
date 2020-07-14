package cl.zpricing.test.utils;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Node;

public class SoapClient {
	public static void main(String[] args) throws SOAPException {
		//SoapClient.getRevenueManagementAvailabilityPerDay("006", "HO00001009", "2013-07-15");
	}
	
	private static final String endpoint = "http://localhost:8080/ZPCinemasWStest/services/cxfZhetaPricingServices";
	
	public SOAPBody getRevenueManagementAvailabilityPerDay(String serviceName, String cinemaId, String movieId, String date) throws SOAPException {
		SOAPMessage message = MessageFactory.newInstance().createMessage();
		SOAPHeader header = message.getSOAPHeader();
		header.detachNode();
		
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.setAttribute("xmlns:ns1", "http://webservices.revman.zpricing.cl");
		
		SOAPBody body = message.getSOAPBody();
		QName bodyName = new QName(serviceName);
		SOAPBodyElement bodyElement = body.addBodyElement(bodyName);
		SOAPElement cinemaIdElement = bodyElement.addChildElement("cinemaId");
		cinemaIdElement.addTextNode(cinemaId);
		
		SOAPElement movieIdElement = bodyElement.addChildElement("movieId");
		movieIdElement.addTextNode(movieId);
		
		SOAPElement dateElement = bodyElement.addChildElement("date");
		dateElement.addTextNode(date);
		
		SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
		SOAPMessage response = connection.call(message, endpoint);
		connection.close();
		
		SOAPBody responseBody = response.getSOAPBody();
		
		return responseBody;
	}
	
	public SOAPBody getRevenueManagementAvailability(String serviceName, String cinemaId, String sessionId) throws SOAPException {
		SOAPMessage message = MessageFactory.newInstance().createMessage();
		SOAPHeader header = message.getSOAPHeader();
		header.detachNode();
		
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.setAttribute("xmlns:web", "http://webservices.revman.zpricing.cl");
		
		SOAPBody body = message.getSOAPBody();
		QName bodyName = new QName(serviceName);
		SOAPBodyElement bodyElement = body.addBodyElement(bodyName);
		
		SOAPElement cinemaIdElement = bodyElement.addChildElement("cinemaId");
		cinemaIdElement.addTextNode(cinemaId);
		
		SOAPElement sessionIdElement = bodyElement.addChildElement("sessionId");
		sessionIdElement.addTextNode(sessionId);
		
		SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
		SOAPMessage response = connection.call(message, endpoint);
		connection.close();
		
		SOAPBody responseBody = response.getSOAPBody();
		
		return responseBody;
	}
}