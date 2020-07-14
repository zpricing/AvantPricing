package cl.zpricing.avantors.tasks.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import cl.zpricing.avantors.tasks.CleanSessionCollection;

public class CleanSessionCollectionImpl implements CleanSessionCollection {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private MongoOperations mongoOperation;

	@Override
	public void execute() {
		log.info("Iniciando limpieza de coleccion Session.");
		mongoOperation.executeCommand("{'eval': 'cleanSessionCollection()', 'nolock' : 'true'}");
		log.info("Finalizando limpieza de coleccion Session.");
	}

	public void setMongoOperation(MongoOperations mongoOperation) {
		this.mongoOperation = mongoOperation;
	}
}
