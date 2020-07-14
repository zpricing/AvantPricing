package cl.zpricing.revman.tasks.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import com.mongodb.CommandResult;

import cl.zpricing.revman.tasks.StatsAdditionalData;

public class StatsAdditionalDataImpl implements StatsAdditionalData{
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private MongoOperations mongoOperation;
	
	@Override
	public void execute() {
		log.info("Iniciando Stats Addional Data.");
		CommandResult result = mongoOperation.executeCommand("{'eval': 'statsAdditionalData()', 'nolock' : 'true'}");
		log.info("Stats Actualizadas: " + result.getInt("retval"));
		log.info("Finalizando Stats Addional Data");
	}
}
