package cl.zpricing.avant.etl.springbatch;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import cl.zpricing.avant.etl.model.Availability;
import cl.zpricing.avant.etl.model.Session;
import cl.zpricing.avant.model.FuncionAreaProxy;

public class RevenueManagementAvailabilityProcessor implements ItemProcessor<Object, Object>{
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Override
	public Object process(Object funcion) throws Exception {
		Session session = (Session) funcion;
		
		return null;
	}
}