package cl.zpricing.avant.negocio.loadmanager;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class CinemaRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return CinemaContextHolder.getCinemaRoute();		
	}

}
