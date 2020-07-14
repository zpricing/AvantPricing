package cl.zpricing.avant.etl.scriptella;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import scriptella.configuration.ConfigurationEl;
import scriptella.configuration.ConnectionEl;
import scriptella.execution.EtlExecutor;
import scriptella.execution.EtlExecutorException;
import scriptella.interactive.ConsoleProgressIndicator;
import scriptella.interactive.ProgressIndicator;
import cl.zpricing.avant.etl.OnlineRevenueServicesEtl;
import cl.zpricing.avant.util.PropertiesUtil;
import cl.zpricing.commons.utils.ErroresUtils;

public class OnlineRevenueServicesEtlImpl implements OnlineRevenueServicesEtl {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private Resource resourceEtlExecutor;

	@Override
	public boolean execute() {
		try {
			log.debug(" url : " + PropertiesUtil.getProperty("jdbc.url.zpcinemas"));
			
			log.debug("Antes de crear etl executor");
			EtlExecutor etlExecutor;
			etlExecutor = EtlExecutor.newExecutor(resourceEtlExecutor.getFile());
			log.debug("Despues de crear etl executor");
			
			ConfigurationEl conf = etlExecutor.getConfiguration();
			List<ConnectionEl> connections = conf.getConnections();
			
			for (int i = 0 ; i < connections.size() ; i++) {
				if (connections.get(i).getId().equalsIgnoreCase("zpcinemas")) {
					connections.get(i).setUrl(PropertiesUtil.getProperty("jdbc.url.zpcinemas"));
					connections.get(i).setUser(PropertiesUtil.getProperty("jdbc.username"));
					connections.get(i).setPassword(PropertiesUtil.getProperty("jdbc.password"));
				}
				else if (connections.get(i).getId().equalsIgnoreCase("zpcinemas-ors")) {
					connections.get(i).setUrl(PropertiesUtil.getProperty("jdbc.ors.url"));
					connections.get(i).setUser(PropertiesUtil.getProperty("jdbc.ors.username"));
					connections.get(i).setPassword(PropertiesUtil.getProperty("jdbc.ors.password"));
				}
			}
			
			ProgressIndicator indicator = new ConsoleProgressIndicator();			
			etlExecutor.execute(indicator);
			
			return true;
		} catch (IOException e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		} catch (EtlExecutorException e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
		
		return false;
	}

	public void setResourceEtlExecutor(Resource resourceEtlExecutor) {
		this.resourceEtlExecutor = resourceEtlExecutor;
	}

}
