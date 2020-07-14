package cl.zpricing.avant.etl.springbatch;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ItemWriteListener;

import cl.zpricing.commons.utils.ErroresUtils;

public class CustomItemWriterListener implements ItemWriteListener<Object> {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Override
	public void afterWrite(List<? extends Object> arg0) {
		log.debug("ItemWriterListener - afterWrite");
	}

	@Override
	public void beforeWrite(List<? extends Object> arg0) {
		log.debug("ItemWriterListener - beforeWrite");
	}

	@Override
	public void onWriteError(Exception e, List<? extends Object> arg1) {
		log.debug("ItemWriterListener - onWriteError");
		log.error(ErroresUtils.extraeStackTrace(e));
	}

}
