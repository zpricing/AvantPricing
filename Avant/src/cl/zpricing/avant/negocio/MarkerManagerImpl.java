package cl.zpricing.avant.negocio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Marker;
import cl.zpricing.avant.servicios.MarkerDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;

/**
 * Implementacion de Marker Manager. Realiza operaciones para obtener
 * informaci�n sobre los markers guardados.
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 16-01-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class MarkerManagerImpl implements MarkerManager {

	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private MarkerDao markerDao;
	private ServiciosRevenueManager serviciosRM;

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

	/**
	 * @return the serviciosRM
	 */
	public ServiciosRevenueManager getServiciosRM() {
		return serviciosRM;
	}

	/**
	 * @param serviciosRM
	 *            the serviciosRM to set
	 */
	public void setServiciosRM(ServiciosRevenueManager serviciosRM) {
		this.serviciosRM = serviciosRM;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.negocio.MarkerManager#obtenerMarkersFecha(java.util
	 * .Date)
	 */
	@Override
	public ArrayList<Marker> obtenerMarkersFechas(Date inicio, Date fin) {
		ArrayList<Marker> result = new ArrayList<Marker>();
		GregorianCalendar inicioCal = new GregorianCalendar();
		inicioCal.setTime(inicio);
		GregorianCalendar fecha = inicioCal;
		while (fecha.before(fin)) {
			fecha.add(Calendar.DAY_OF_YEAR, 1);
			result.addAll(markerDao.listaMarkersByFecha(fecha.getTime()));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.negocio.MarkerManager#obtenerMarkers(java.util.Date,
	 * java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public ArrayList<Marker> obtenerMarkers(Date fecha, int complejoId,
			int peliculaId) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.negocio.MarkerManager#isFechaFeriado(java.util.Date)
	 */
	@Override
	public boolean isFechaFeriado(Date fecha) {
		List<Marker> lista = markerDao.listaMarkersByFecha(fecha);
		int feriado_id = Integer.parseInt(serviciosRM.obtenerParametro("Marker", "feriado_id").getCodigo());
		Iterator<Marker> iter = lista.iterator();
		while (iter.hasNext()) {
			Marker m = iter.next();
			if (m.getFechaHasta() == null && m.getTipoMarker().getId() == feriado_id) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isFechaFeriado(Date fecha, Complejo complejo) {
		List<Marker> lista = markerDao.obtenerMarkersFechaComplejo(fecha, complejo);
		int feriado_id = Integer.parseInt(serviciosRM.obtenerParametro("Marker", "feriado_id").getCodigo());
		Iterator<Marker> iter = lista.iterator();
		while (iter.hasNext()) {
			Marker marker = iter.next();
			if (marker.getTipoMarker().getId() == feriado_id) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.negocio.MarkerManager#isFechaVacaciones(java.util.
	 * Date)
	 */
	@Override
	public boolean isFechaVacaciones(Date fecha) {
		List<Marker> lista = markerDao.listaMarkersByFecha(fecha);
		int vacaciones_id = Integer.parseInt(serviciosRM.obtenerParametro("Marker", "vacaciones").getCodigo());
		Iterator<Marker> iter = lista.iterator();
		while (iter.hasNext()) {
			Marker m = iter.next();
			if (m.getFechaHasta().compareTo(fecha) >= 0
					&& m.getTipoMarker().getId() == vacaciones_id) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.negocio.MarkerManager#isFechaNormal(java.util.Date)
	 */
	@Override
	public boolean isFechaNormal(Date fecha) {
		List<Marker> lista = markerDao.listaMarkersByFecha(fecha);
		int vacaciones_id = Integer.parseInt(serviciosRM.obtenerParametro("Marker", "normal_id").getCodigo());
		Iterator<Marker> iter = lista.iterator();
		while (iter.hasNext()) {
			Marker m = iter.next();
			if (m.getFechaHasta().compareTo(fecha) >= 0
					&& m.getTipoMarker().getId() == vacaciones_id) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.negocio.MarkerManager#obtenerPreciosEspeciales(cl.
	 * zpricing.revman.model.Complejo)
	 */
	@Override
	public ArrayList<Double> obtenerPreciosEspeciales(Complejo complejo) {
		List<Marker> markers = markerDao.obtenerMarkersClase();
		ArrayList<Double> precios = new ArrayList<Double>();
		Iterator<Marker> iter = markers.iterator();
		int precioEspecialId = Integer.parseInt(serviciosRM.obtenerParametro(
				"Marker", "precioespecial_id").getCodigo());
		log.debug("precioespecial_id: " + precioEspecialId);
		while (iter.hasNext()) {
			Marker m = iter.next();
			if (m.getTipoMarker().getId() == precioEspecialId
					&& (m.getComplejo() == null || m.getComplejo().getId() == complejo
							.getId()))
				precios.add(m.getClase().getPrecio());
		}
		return precios;
	}
}
