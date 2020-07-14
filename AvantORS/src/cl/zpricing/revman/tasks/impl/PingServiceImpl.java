package cl.zpricing.revman.tasks.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import cl.zpricing.commons.utils.ErroresUtils;
import cl.zpricing.revman.model.Parametro;
import cl.zpricing.revman.tasks.PingService;

public class PingServiceImpl implements PingService {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private MongoOperations mongoOperation;
	
	@Override
	public boolean sendPing() {
		HttpURLConnection con;
		try {
			Parametro serviceId = mongoOperation.findOne(new Query(Criteria.where("name").is("service_id")), Parametro.class);
			Parametro serviceURL = mongoOperation.findOne(new Query(Criteria.where("name").is("ping_service_ip")), Parametro.class);
			log.debug("Service URL : [" + serviceURL.getValue() + "]");
			log.debug("Id de Servicio : " + serviceId.getValue());
			con = (HttpURLConnection) new URL("http://" + serviceURL.getValue() + "/ping/" + serviceId.getValue()).openConnection();
			con.setRequestMethod("POST");
			con.getInputStream();
			
			BufferedReader rd  = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			
			String resultMessage = sb.toString().trim();
			log.debug(resultMessage);
			
			if (resultMessage.equalsIgnoreCase("ok")) {
				return true;
			}
		} catch (MalformedURLException e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		} catch (IOException e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
		return false;
	}
	
	public void setMongoOperation(MongoOperations mongoOperation) {
		this.mongoOperation = mongoOperation;
	}
	
}
