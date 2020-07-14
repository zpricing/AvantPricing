package cl.zpricing.unittest.dataload;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.ibatis.sqlmap.engine.transaction.Transaction;

import cl.zpricing.commons.utils.DateUtils;
import cl.zpricing.revman.model.Availability;
import cl.zpricing.revman.model.SecondSelling;
import cl.zpricing.revman.model.Session;
import cl.zpricing.revman.model.Upselling;

public class DataLoad {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private MongoOperations mongoOperation;
	
	public String createSession(String cinemaId, String sessionId, String movieId, Date sessionTime) throws ParseException {
		Session session = new Session();
		session.setSessionId(sessionId);
		session.setCinemaId(cinemaId);
		session.setMovieId(movieId);
		session.setTime(sessionTime);
		Calendar cal = DateUtils.obtenerCalendario(sessionTime);
		session.setDate(DateUtils.getUTCdate(cal.getTime()));
		session.setAvailability(new ArrayList<Availability>());
		
		mongoOperation.insert(session);
		
		log.debug("session Id : " + session.getId());
		
		return session.getId();
	}
	
	public void addAvailability(String sessionId, String ticketTypeId, BigDecimal ticketPrice, BigDecimal ticketBookingFee, int available, int occupied, int daysBeforeExpiration, String type) {
		Availability availability = new Availability();
		availability.setTicketTypeId(ticketTypeId);
		availability.setTicketPrice(ticketPrice);
		availability.setTicketBookingFee(ticketBookingFee);
		availability.setAvailable(available);
		availability.setOccupied(occupied);
		availability.setTotal(available + occupied);
		availability.setDaysBeforeSessionExpiration(daysBeforeExpiration);
		availability.setType(type);
		
		Session session = mongoOperation.findOne(new Query(Criteria.where("id").is(sessionId)), Session.class);
		
		session.getAvailability().add(availability);
		
		mongoOperation.save(session);
	}
	
	public void addSecondSellingSuggestionsForSession(String id, String selectedSessionWithAvailableSecondSelling, int orden) {
		Session session = mongoOperation.findOne(new Query(Criteria.where("id").is(id)), Session.class);
		SecondSelling secondSelling = new SecondSelling();
		secondSelling.setOrden(orden);
		secondSelling.setSessionId(selectedSessionWithAvailableSecondSelling);
		
		if (session.getSecondSelling() == null){
			session.setSecondSelling(new ArrayList<SecondSelling>());
		}
		session.getSecondSelling().add(secondSelling);
		mongoOperation.save(session);
	}
	
	public void addUpsellingSuggestionsForSession(String id, String sessionId, int orden) {
		Session session = mongoOperation.findOne(new Query(Criteria.where("id").is(id)), Session.class);
		
		Upselling upselling = new Upselling();
		upselling.setOrden(orden);
		upselling.setSessionId(sessionId);
		
		if (session.getUpselling() == null) {
			session.setUpselling(new ArrayList<Upselling>());
		}
		session.getUpselling().add(upselling);
		mongoOperation.save(session);
	}
	
	public void deleteCollections() {
		mongoOperation.dropCollection(Transaction.class);
		mongoOperation.dropCollection(Session.class);
	}
}
