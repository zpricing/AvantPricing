package cl.zpricing.avant.etl.scriptella;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import scriptella.configuration.ConfigurationEl;
import scriptella.configuration.ConnectionEl;
import scriptella.execution.EtlExecutor;
import scriptella.execution.EtlExecutorException;
import scriptella.execution.ExecutionStatistics;
import scriptella.execution.ExecutionStatistics.ElementInfo;
import scriptella.interactive.ConsoleProgressIndicator;
import scriptella.interactive.ProgressIndicator;
import cl.zpricing.avant.etl.CinemaEtl;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.util.PropertiesUtil;

public class CinemaEtlImpl implements CinemaEtl {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private Resource resourceProperties;
	private Resource resourceEtlExecutor;
	
	public boolean execute(Complejo complejo, boolean isFullDataExtraction) throws IOException, EtlExecutorException {
		log.debug(" url : " + PropertiesUtil.getProperty("jdbc.url.zpcinemas"));
		log.debug(" resource path : " + resourceProperties.getFile().getAbsolutePath());
		
		Properties prop = new Properties();
		prop.put("cinema_code", String.valueOf(complejo.getComplejo_id_externo()));
		prop.put("full_data_extraction", String.valueOf(isFullDataExtraction));
		FileOutputStream file = new FileOutputStream(resourceProperties.getFile().getAbsolutePath());
		prop.store(file, "Generado automaticamente por CinemaEtlImpl");
		file.close();
		
		log.debug("Antes de crear etl executor");
		EtlExecutor etlExecutor = EtlExecutor.newExecutor(resourceEtlExecutor.getFile());
		log.debug("Despues de crear etl executor");
		
		ConfigurationEl conf = etlExecutor.getConfiguration();
		List<ConnectionEl> connections = conf.getConnections();
		
		for (int i = 0 ; i < connections.size() ; i++) {
			if (connections.get(i).getId().equalsIgnoreCase("cinema")) {
				connections.get(i).setUser(complejo.getServidorUsuario());
				connections.get(i).setPassword(complejo.getServidorPassword());
				connections.get(i).setUrl("jdbc:sqlserver://" + complejo.getServidorIp() + ";database=" + complejo.getServidorBaseDeDatos());
			}
			else if (connections.get(i).getId().equalsIgnoreCase("zpcinemas")) {
				connections.get(i).setUser(PropertiesUtil.getProperty("jdbc.username"));
				connections.get(i).setPassword(PropertiesUtil.getProperty("jdbc.password"));
				connections.get(i).setUrl(PropertiesUtil.getProperty("jdbc.url.zpcinemas"));
			}
		}
		
		ProgressIndicator indicator = new ConsoleProgressIndicator();			
		ExecutionStatistics statistics = etlExecutor.execute(indicator);
		
		Map<String, Integer> categories = statistics.getCategoriesStatistics();
		Set<String> keys = categories.keySet();
		
		for (String key : keys) {
			log.debug(" categories : [" + key + "] ; [" + categories.get(key) + "]");
		}
		
		Collection<ElementInfo> info = statistics.getElements();
		for (ElementInfo elementInfo : info) {
			String id = elementInfo.getId();
			long statementsCount = elementInfo.getStatementsCount();
			int failedExecutionCount = elementInfo.getFailedExecutionCount();
			int succesfulExecutionCount = elementInfo.getSuccessfulExecutionCount();
			double throughput = elementInfo.getThroughput();
			long workingTime = elementInfo.getWorkingTime();
			
			log.debug("id : " + id);
			log.debug("	statementsCount : " + statementsCount);
			log.debug("	failedExecutionCount : " + failedExecutionCount);
			log.debug("	succesfulExecutionCount : " + succesfulExecutionCount);
			log.debug("	throughput : " + throughput);
			log.debug("	workingTime : " + workingTime);
		}
		
		return true;
	}

	public void setResourceProperties(Resource resourceProperties) {
		this.resourceProperties = resourceProperties;
	}

	public void setResourceEtlExecutor(Resource resourceEtlExecutor) {
		this.resourceEtlExecutor = resourceEtlExecutor;
	}
}
