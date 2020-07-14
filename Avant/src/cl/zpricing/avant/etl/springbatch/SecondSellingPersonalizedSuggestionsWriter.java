package cl.zpricing.avant.etl.springbatch;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import cl.zpricing.avant.etl.model.Session;
import cl.zpricing.avant.etl.model.SessionPersonalizedSuggestions;

public class SecondSellingPersonalizedSuggestionsWriter implements ItemWriter<Object> {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private MongoOperations mongoOperation;

	@Override
	public void write(List<? extends Object> funciones) throws Exception {
		log.info("Escribiendo " + funciones.size() +  " funciones");
				
		for (int i = 0; i < funciones.size(); i++) {
			SessionPersonalizedSuggestions session = (SessionPersonalizedSuggestions) funciones.get(i);
			log.debug("  session.cinemaId : " + session.getCinemaId());
			log.debug("  session.sessionId : " + session.getSessionId());
			
			Update updatedSession = new Update();
			
			log.debug("  # of suggestions :" + session.getSuggestions().size());
			updatedSession.set("second_selling_client_suggestions", session.getSuggestions());
			
			WriteResult result = mongoOperation.updateFirst(new Query(Criteria.where("cinemaId").is(session.getCinemaId()).and("sessionId").is(session.getSessionId())), updatedSession, Session.class);
			
			if (result.getN() == 0) {
				log.warn("  No se logro actualizar funcion: [" + session.getCinemaId() + "] ; sessionId : ["+ session.getSessionId() + "]");
				log.warn("Error : " + result.getError());
			}
		}
		log.info(" Terminando de escribir");
	}

	public void setMongoOperation(MongoOperations mongoOperation) {
		this.mongoOperation = mongoOperation;
	}
}
