package cl.zpricing.avant.etl.springbatch;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;

public class BusinessIntelligenceClientWriter implements ItemWriter<Object> {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("mongoIzyTemplate")
	private MongoOperations mongoOperation;
	
	@Override
	public void write(List<? extends Object> clients) throws Exception {
		log.info("Iniciando BusinessIntelligenceClientWriter");
		log.info("  Inserting : " + clients.size());
		mongoOperation.insert(clients, "clients_temp");
		log.info("Finalizando BusinessIntelligenceClientWriter");
	}

	public void setMongoOperation(MongoOperations mongoOperation) {
		this.mongoOperation = mongoOperation;
	}
}


