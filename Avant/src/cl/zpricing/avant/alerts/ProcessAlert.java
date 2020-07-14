package cl.zpricing.avant.alerts;

import cl.zpricing.avant.util.PropertiesUtil;
import cl.zpricing.commons.utils.EnvioDeCorreo;

public class ProcessAlert extends Alert{
	
	@Override
	public boolean notifyAlert() {
		
		String to = PropertiesUtil.getProperty("alerts.to");
		String from = PropertiesUtil.getProperty("alerts.from");
		String subject = this.getTitle();
		String cuerpo = this.getDescription();
		
		return EnvioDeCorreo.normal("AvantPricing - Alert", 
				from, 
				to, 
				subject, 
				"", 
				"", 
				"", 
				cuerpo, 
				"", 
				//attachments
				null
				) == 0;
	}
}
