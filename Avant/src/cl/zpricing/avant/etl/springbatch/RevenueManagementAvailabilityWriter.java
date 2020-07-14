package cl.zpricing.avant.etl.springbatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import cl.zpricing.avant.etl.model.Availability;
import cl.zpricing.avant.etl.model.Session;
import cl.zpricing.avant.etl.model.Upselling;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.commons.utils.DateUtils;

public class RevenueManagementAvailabilityWriter implements ItemWriter<Object> {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private MongoOperations mongoOperation;

	@Override
	public void write(List<? extends Object> funciones) throws Exception {
		log.info("Cargando " + funciones.size() +  " funciones");
		List<Session> funcionesAreaParaInsertar = new ArrayList<Session>();
				
		for (int i = 0; i < funciones.size(); i++) {
			Session session = (Session) funciones.get(i);
			log.debug("  session.cinemaId : " + session.getCinemaId());
			log.debug("  session.sessionId : " + session.getSessionId());
			
			Session savedSession = (Session) mongoOperation.findOne(new Query( Criteria.where("cinemaId").is(session.getCinemaId()).and("sessionId").is(session.getSessionId())), Session.class);			
			
			if (savedSession != null) {
				log.debug("  savedSession no es null");
				Update updatedSession = new Update();
				
				List<Availability> updatedAvailabilities = new ArrayList<Availability>();
				for (Availability availability : session.getAvailability()) {
					Availability updatedAvailability =  availability;
					for(Availability savedAvailability : savedSession.getAvailability()) {
						if(savedAvailability.getTicketTypeId().equals(availability.getTicketTypeId())) {
							updatedAvailability.setOccupied(savedAvailability.getOccupied());
							if(availability.getAvailable() > 0) {
								updatedAvailability.setAvailable(availability.getTotal() - savedAvailability.getOccupied());
							}
							if(availability.getAvailable() == 0 && savedAvailability.getAvailable() > 0) {
								updatedAvailability.setAvailable(0);
							}
							break;
						}
					}
					updatedAvailabilities.add(updatedAvailability);
				}
				
				updatedSession.set("availability", updatedAvailabilities);
				
				log.debug("Upselling: " + session.getUpselling().size());
				updatedSession.set("upselling", session.getUpselling());
				
				log.debug("secondSelling: " + session.getSecondSelling().size());
				updatedSession.set("secondSelling", session.getSecondSelling());
				
				log.debug("lastMinuteSelling: " + session.isLastMinuteSelling());
				updatedSession.set("lastMinuteSelling", session.isLastMinuteSelling());
				
				log.debug("eliminada: " + session.isEliminada());
				updatedSession.set("eliminada", session.isEliminada());
				
				log.debug("movieId:" + session.getMovieId());
				updatedSession.set("movieId", session.getMovieId());
				
				log.debug("date:" + session.getDate());
				updatedSession.set("date", DateUtils.getUTCdate(session.getDate(), DateUtils.format_yyyyMMdd));
				
				log.debug("time:" + session.getTime());
				updatedSession.set("time", DateUtils.getUTCdate(session.getTime(), DateUtils.format_yyyyMMdd_HHmmss));
				
				log.debug("fullPrice:" + session.getFullPrice());
				updatedSession.set("fullPrice", session.getFullPrice());
				
				log.debug("fullPriceBookingFee:" + session.getFullPriceBookingFee());
				updatedSession.set("fullPriceBookingFee", session.getFullPriceBookingFee());
				
				log.debug("movieName:" + session.getMovieName());
				updatedSession.set("movieName", session.getMovieName());
				
				log.debug("movieGraphicUrl:" + session.getMovieGraphicUrl());
				updatedSession.set("movieGraphicUrl", session.getMovieGraphicUrl());
				
				log.debug("movieRating:" + session.getMovieRating());
				updatedSession.set("movieRating", session.getMovieRating());
				
				WriteResult result = mongoOperation.updateFirst(new Query(Criteria.where("cinemaId").is(session.getCinemaId()).and("sessionId").is(session.getSessionId())), updatedSession, session.getClass());
				
				if (result.getN() == 0) {
					log.warn("  No se logro actualizar funcion: [" + session.getCinemaId() + "] ; sessionId : ["+ session.getSessionId() + "]");
				}
			}
			else {
				log.debug("  savedSession es null");
				log.debug("Upselling: " + session.getUpselling().size());
				log.debug("secondSelling: " + session.getSecondSelling().size());
				log.debug("lastMinuteSelling: " + session.isLastMinuteSelling());
				
				log.debug("  session date : " + session.getDate());
				log.debug("  session time : " + session.getTime());
				
				session.setDate(DateUtils.getUTCdate(session.getDate(), DateUtils.format_yyyyMMdd));
				session.setTime(DateUtils.getUTCdate(session.getTime(), DateUtils.format_yyyyMMdd_HHmmss));
				
				funcionesAreaParaInsertar.add(session);
			}
		}
		log.info("  Nuevas funciones a cargar a Mongo : " + funcionesAreaParaInsertar.size());
		mongoOperation.insertAll(funcionesAreaParaInsertar);
		log.info(" Terminando de cargar nuevas funciones");
	}

	public void setMongoOperation(MongoOperations mongoOperation) {
		this.mongoOperation = mongoOperation;
	}
}
