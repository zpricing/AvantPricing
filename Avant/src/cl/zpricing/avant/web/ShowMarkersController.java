/**
 * 
 */
package cl.zpricing.avant.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.Marker;
import cl.zpricing.avant.servicios.MarkerDao;
import cl.zpricing.avant.util.Util;

/**
 * <b>Controlador encargado de mostrar el modulo de markers segun los parametros
 * entregados. Muestra la lista de markers pedidos.</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 21-01-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ShowMarkersController implements Controller {

	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */

	private MarkerDao markerDao;

	/**
	 * @return the markerDao
	 */
	public MarkerDao getMarkerDao() {
		return markerDao;
	}

	/**
	 * @param markerDao
	 *            the markerDao to set
	 */
	public void setMarkerDao(MarkerDao markerDao) {
		this.markerDao = markerDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("handelRequest...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		String[] peliculaIds = null;
		@SuppressWarnings("unused")
		String complejoId;
		// Obtengo un set de rangos de fecha.
		String rangoFechas = request.getParameter("rangoFechas");
		String[] rangos = rangoFechas.split("/");
		// Obtengo los rangos.
		ArrayList<HashMap<String, Object>> arrayMapRangos = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < rangos.length; i++) {
			String[] temp = rangos[i].split("_");
			HashMap<String, Object> tempMap = new HashMap<String, Object>(2);
			tempMap.put("inicio", Util.StringToDate(temp[0]));
			tempMap.put("fin", Util.StringToDate(temp[1]));
			arrayMapRangos.add(tempMap);
		}
		// Obtengo los ids de peliculas si es que son pasados como parametros.
		if (request.getParameter("peliculaIds") != null) {
			String listaPeliculaIds = request.getParameter("peliculaIds");
			peliculaIds = listaPeliculaIds.split("_");
		}
		// Obtengo el id de complejo
		if (request.getParameter("complejoId") != null) {
			complejoId = request.getParameter("complejoId");
		}
		// Itero sobre los rangos para obtener todos los markers en esos rangos.
		Iterator<HashMap<String, Object>> iterFechas = arrayMapRangos
				.iterator();
		ArrayList<Marker> listaMarkers = new ArrayList<Marker>();
		while (iterFechas.hasNext()) {
			HashMap<String, Object> temp = iterFechas.next();
			Date inicio = (Date) temp.get("inicio");
			Date fin = (Date) temp.get("fin");
			listaMarkers.addAll(markerDao.listaMarkersEntreFechas(inicio, fin));
		}
		// Reviso los markers y obtengo todos los que no son especificos.
		ArrayList<Marker> markersGenerales = new ArrayList<Marker>();
		Iterator<Marker> iterMarkers = listaMarkers.iterator();
		while (iterMarkers.hasNext()) {
			Marker tempM = iterMarkers.next();
			if (tempM.getComplejo() == null && tempM.getPelicula() == null
					&& !isMarkerInArray(tempM, markersGenerales)) {
				markersGenerales.add(tempM);
			}
		}
		if (request.getParameter("peliculaIds") != null) {

			for (int i = 0; i < peliculaIds.length; i++) {
				// Reviso los markers y obtengo todos los que tengan relacion
				// con una pelicula.

				iterMarkers = listaMarkers.iterator();
				while (iterMarkers.hasNext()) {
					Marker tempM = iterMarkers.next();
					if (tempM.getPelicula() != null
							&& tempM.getPelicula().getId() == Integer
									.parseInt(peliculaIds[i])) {

					}
				}
			}
		}
		// Reviso los markers y obtengo todos los que tengan relacion con el
		// complejo.
		return new ModelAndView("showmarkers", "map", map);
	}

	/**
	 * Revisa si existe el marker en el array dado. Lo hace comparando el Id del
	 * marker.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-01-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param marker
	 * @param array
	 * @return
	 * @since 1.0
	 */
	private boolean isMarkerInArray(Marker marker, ArrayList<Marker> array) {
		Iterator<Marker> iter = array.iterator();
		while (iter.hasNext()) {
			Marker m = iter.next();
			if (marker.getId() == m.getId())
				return true;
		}
		return false;
	}
}
