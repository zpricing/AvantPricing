package cl.zpricing.avant.etl.springbatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import cl.zpricing.avant.etl.model.Cluster;
import cl.zpricing.avant.servicios.BusinessIntelligenceProcessDao;

public class BusinessIntelligenceClusterWriter implements ItemWriter<Object> {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private BusinessIntelligenceProcessDao businessIntelligenceDao;
	
	@Autowired
	@Qualifier("mongoIzyTemplate")
	private MongoOperations mongoOperation;
	
	@Override
	public void write(List<? extends Object> clusters) throws Exception {
		log.info("Iniciando BusinessIntelligenceClusterUpdateWriter");
		
		for (Object object : clusters) {
			Cluster cluster = (Cluster) object;
			log.info("  Actualizando Cluster: " + cluster.getDescription());
			Map<String, Object> products = new HashMap<String, Object>();
			products.put("premiere", cluster.getPremieres());
			products.put("showing", cluster.getShowings());
			
			Update update = new Update();
			update.set("products", products);
			update.set("name", cluster.getName());
			update.set("description", cluster.getDescription());
			Query query = new Query(Criteria.where("name").is(cluster.getName()).and("description").is(cluster.getDescription()));
			WriteResult writeResult = mongoOperation.upsert(query, update, "segments");
			
			Cluster clusterInserted = mongoOperation.findOne(query, Cluster.class, "segments");
			
			log.debug("  ID : " + clusterInserted.getId().toString());
			businessIntelligenceDao.addSegmentIdToCluster(cluster.getClusterId(), clusterInserted.getId().toString());
		}
		
		log.info("Finalizando BusinessIntelligenceClusterUpdateWriter");
	}

	public void setMongoOperation(MongoOperations mongoOperation) {
		this.mongoOperation = mongoOperation;
	}
}
