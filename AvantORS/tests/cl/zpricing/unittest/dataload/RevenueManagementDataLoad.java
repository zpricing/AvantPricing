package cl.zpricing.unittest.dataload;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.ibatis.sqlmap.engine.transaction.Transaction;

import cl.zpricing.commons.utils.DateUtils;
import cl.zpricing.commons.utils.ErroresUtils;
import cl.zpricing.revman.model.Availability;
import cl.zpricing.revman.model.SecondSelling;
import cl.zpricing.revman.model.Session;
import cl.zpricing.revman.model.Upselling;

public class RevenueManagementDataLoad {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private MongoOperations mongoOperation;
	
	public String insertSession(String cinemaId, String sessionId, String movieId, String movieName, String date) {
		return this.insertRevenueManagementAvailability(cinemaId, sessionId, movieId, movieName, date, new Date(), 0, 0, false);
	}
	
	public String insertRevenueManagementAvailability(String cinemaId, String sessionId) {
		return this.insertRevenueManagementAvailability(cinemaId, sessionId, "", "2013-01-01");
	}
	
	public String insertRevenueManagementAvailability(String cinemaId, String sessionId, String movieId, String date) {
		return this.insertRevenueManagementAvailability(cinemaId, sessionId, movieId, date, 0, 0, false);
	}
	
	public String insertRevenueManagementAvailability(String cinemaId, String sessionId, String date, boolean lastMinuteSelling) {
		return this.insertRevenueManagementAvailability(cinemaId, sessionId, "", date, 0, 0, lastMinuteSelling);
	}
	
	public String insertRevenueManagementAvailability(String cinemaId, String sessionId, String date, Date time, boolean lastMinuteSelling) {
		return this.insertRevenueManagementAvailability(cinemaId, sessionId, "", "", date, time, 0, 0, lastMinuteSelling);
	}
	
	public String insertRevenueManagementAvailability(String cinemaId, String sessionId, double priceFull, double bfeeFull) {
		return this.insertRevenueManagementAvailability(cinemaId, sessionId, "", "2013-01-01", 0, 0, false);
	}
	
	public String insertRevenueManagementAvailability(String cinemaId, String sessionId, String movieName, double priceFull, double bfeeFull) {
		return insertRevenueManagementAvailability(cinemaId, sessionId, movieName, priceFull, bfeeFull, "2013-01-01");
	}
	
	public String insertRevenueManagementAvailability(String cinemaId, String sessionId, String movieName, double priceFull, double bfeeFull, String date) {
		return insertRevenueManagementAvailability(cinemaId, sessionId, "", movieName, date, new Date(), priceFull, bfeeFull, false);
	}
	
	public String insertRevenueManagementAvailability(String cinemaId, String sessionId, double priceFull, double bfeeFull, String date) {
		return this.insertRevenueManagementAvailability(cinemaId, sessionId, "", date, 0, 0, false);
	}
	
	public String insertRevenueManagementAvailability(String cinemaId, String sessionId, String movieId, String date, double priceFull, double bfeeFull) {
		return this.insertRevenueManagementAvailability(cinemaId, sessionId, movieId, date, priceFull, bfeeFull, false);
	}
	public String insertRevenueManagementAvailability(String cinemaId, String sessionId, String movieId, String date, double priceFull, double bfeeFull, boolean lastMinuteSelling) {
		return this.insertRevenueManagementAvailability(cinemaId, sessionId, movieId, "", date, new Date(), priceFull, bfeeFull, lastMinuteSelling);
	}
	
	public String insertRevenueManagementAvailability(String cinemaId, String sessionId, String movieId, String movieName, String date, Date time, double priceFull, double bfeeFull, boolean lastMinuteSelling) {
		try {
			Session session = new Session();
			session.setCinemaId(cinemaId);
			session.setSessionId(sessionId);
			session.setMovieId(movieId);
			session.setMovieName(movieName);
			session.setFullPrice(Double.toString(priceFull));
			session.setFullPriceBookingFee(Double.toString(bfeeFull));
			session.setLastMinuteSelling(lastMinuteSelling);
			session.setDate(DateUtils.getUTCdate(date));
			session.setTime(time);
			session.setEliminada(false);
			
			session.setAvailability(new ArrayList<Availability>());
			
			mongoOperation.insert(session);
			
			log.debug("  session _id : " + session.getId() );
			return session.getId();
		} catch (ParseException e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
		return null;
	}
	
	public void addAvailability(String id, String ticketTypeId, int available, int total, int occupied, int daysBeforeExpiration, BigDecimal price, BigDecimal bookingFee, String type) {
		Session session = mongoOperation.findOne(new Query(Criteria.where("id").is(id)), Session.class);
		
		Availability availability = new Availability();
		availability.setTicketTypeId(ticketTypeId);
		availability.setAvailable(available);
		availability.setTotal(total);
		availability.setOccupied(occupied);
		availability.setDaysBeforeSessionExpiration(daysBeforeExpiration);
		availability.setTicketPrice(price);
		availability.setTicketBookingFee(bookingFee);
		availability.setLevel(0);
		availability.setType(type);
		
		session.getAvailability().add(availability);
		
		mongoOperation.save(session);
	}
	
	public void addUpsellingSuggestionsForSession(String id, String sessionId) {
		Session session = mongoOperation.findOne(new Query(Criteria.where("id").is(id)), Session.class);
		
		Upselling upselling = new Upselling();
		upselling.setOrden(1);
		upselling.setSessionId(sessionId);
		
		if (session.getUpselling() == null) {
			session.setUpselling(new ArrayList<Upselling>());
		}
		session.getUpselling().add(upselling);
		mongoOperation.save(session);
	}
	
	public void addSecondSellingSuggestionsForSession(String id, String selectedSessionWithAvailableSecondSelling) {
		Session session = mongoOperation.findOne(new Query(Criteria.where("id").is(id)), Session.class);
		SecondSelling secondSelling = new SecondSelling();
		secondSelling.setOrden(1);
		secondSelling.setSessionId(selectedSessionWithAvailableSecondSelling);
		
		if (session.getSecondSelling() == null){
			session.setSecondSelling(new ArrayList<SecondSelling>());
		}
		session.getSecondSelling().add(secondSelling);
		mongoOperation.save(session);
	}
	
	public void deleteSession(String id ) {
		mongoOperation.remove(new Query(Criteria.where("id").is(id)), Session.class);
	}
	
	public void deleteCollections() {
		mongoOperation.dropCollection(Transaction.class);
		mongoOperation.dropCollection(Session.class);
	}
	
	public Session getSession(String cinemaId, String sessionId) {
		return mongoOperation.findOne(new Query(Criteria.where("cinemaId").is(cinemaId).and("sessionId").is(sessionId)), Session.class);
	}
}
