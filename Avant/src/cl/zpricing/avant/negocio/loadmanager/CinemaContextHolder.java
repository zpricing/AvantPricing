package cl.zpricing.avant.negocio.loadmanager;

import org.springframework.util.Assert;

public class CinemaContextHolder {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	   
	public static void setCinemaRoute(String cinemaId) {
		Assert.notNull(cinemaId, "customerType cannot be null");
		contextHolder.set(cinemaId);
	}

	public static String getCinemaRoute() {
		return contextHolder.get();
	}

	public static void clearCinemaRoute() {
		contextHolder.remove();
	}
}
