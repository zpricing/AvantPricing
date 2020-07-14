package cl.zpricing.avant.etl.springbatch;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ItemReadListener;

import cl.zpricing.commons.utils.ErroresUtils;

public class CustomItemReaderListener implements ItemReadListener<Object> {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	@Override
	public void afterRead(Object arg0) {
		log.debug("ItemReadListener - afterRead");
	}

	@Override
	public void beforeRead() {
		log.debug("ItemReadListener - beforeRead");
	}

	@Override
	public void onReadError(Exception e) {
		log.debug("ItemReadListener - onReadError");
		log.error(ErroresUtils.extraeStackTrace(e));
	}
}
