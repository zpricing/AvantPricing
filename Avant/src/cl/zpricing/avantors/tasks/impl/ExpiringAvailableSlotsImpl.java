package cl.zpricing.avantors.tasks.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import com.mongodb.CommandResult;

import cl.zpricing.avantors.tasks.ExpiringAvailableSlots;

public class ExpiringAvailableSlotsImpl implements ExpiringAvailableSlots {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private MongoOperations mongoOperation;
	
	@Override
	public void execute() {
		log.info("Iniciando expiracion de cupos.");
		CommandResult result = mongoOperation.executeCommand("{'eval': 'expireAvailability()', 'nolock' : 'true'}");
		log.info("Funciones Analizadas: " + result.getInt("retval"));
		log.info("Finalizando expiracion de cupos");
	}
	
	public void setMongoOperation(MongoOperations mongoOperation) {
		this.mongoOperation = mongoOperation;
	}
}
