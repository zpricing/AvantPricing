/**
 * 
 */
package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Clase;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Marker;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.TipoMarker;
import cl.zpricing.avant.servicios.MarkerDao;
import cl.zpricing.commons.utils.ErroresUtils;

/**
 * Implementacion de MarkerDao
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 30-12-2008 Julio Olivares Alarcon: version inicial.</li>
 * <li>2.0 20-01-2009 Mario Lavandero Soto: Agregada funcionalidad para rangos
 * de fecha.</li>
 * <li>2.5 20-01-2009 Mario Lavandero Soto: Agregadas consultas para manejo
 * markers clase (Precio Especial).</li>
 * <li>3.0 12-02-2009 Daniel Estevez Garay: Agregadas consultas con todas la combinaciones
 * posibles en el manejo de markers</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class MarkerDaoImpl extends SqlMapClientDaoSupport implements MarkerDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#agregarMarker(cl.zpricing.revman.model.Marker)
	 */
	@Override
	public boolean agregarMarker(Marker marker) {
		getSqlMapClientTemplate().insert("agregarMarker",marker);
		return true;
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#listaMarkerTodos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> listaMarkerTodos() {
		return (List<Marker>) getSqlMapClientTemplate().queryForList("listaMarkerTodos");
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#listaMarkersByFecha(java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> listaMarkersByFecha(Date fecha) {
		return (ArrayList<Marker>) getSqlMapClientTemplate().queryForList("obtenerMarkersFecha", fecha);
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#listaMarkersEntreFechas(java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> listaMarkersEntreFechas(Date inicio, Date fin) {
		HashMap<String, Object> map = new HashMap<String, Object>(2);
		map.put("inicio", inicio);
		map.put("fin", fin);
		return (ArrayList<Marker>) getSqlMapClientTemplate().queryForList("obtenerMarkersEntreFechas", map);
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#listaMarkersByID(int)
	 */
	@Override
	public List<Marker> listaMarkersByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#eliminarMarker(cl.zpricing.revman.model.Marker)
	 */
	@Override
	public boolean eliminarMarker(Marker marker) {
		try{
			getSqlMapClientTemplate().insert("deleteMarker", marker.getId());
			}catch(Exception e)
			{
			return false;
			}
			return true;
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#actualizarMarker(cl.zpricing.revman.model.Marker)
	 */
	@Override
	public void actualizarMarker(Marker marker) {
		getSqlMapClientTemplate().update("actualizarMarker", marker);
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#agregarRangoMarkers(cl.zpricing.revman.model.Marker, java.util.Date, java.util.Date)
	 */
	@Override
	public void agregarRangoMarkers(Marker marker, Date inicio, Date fin) {
				
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#obtenerMarker(int)
	 */
	@Override
	public Marker obtenerMarker(int id) {		
		return (Marker) getSqlMapClientTemplate().queryForObject("obtenerMarker", id);
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#obtenerMarkersClase()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> obtenerMarkersClase() {
		return (ArrayList<Marker>) getSqlMapClientTemplate().queryForList("obtenerClaseMarkers");
	}
	/*
	 * (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#obtenerMarkersClase(cl.zpricing.revman.model.Clase)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> obtenerMarkersClase(Clase clase) {
		return (ArrayList<Marker>)getSqlMapClientTemplate().queryForList("obtenerMarkersClase", clase.getId());
	}

	/*
	 * (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#obtenerMarkersComplejo(cl.zpricing.revman.model.Complejo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> obtenerMarkersComplejo(Complejo complejo) {
		return (ArrayList<Marker>)getSqlMapClientTemplate().queryForList("obtenerMarkersComplejo", complejo.getId());
	}

	/*
	 * (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#obtenerMarkersComplejoClase(cl.zpricing.revman.model.Complejo, cl.zpricing.revman.model.Clase)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> obtenerMarkersComplejoClase(Complejo complejo, Clase clase) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("complejo", complejo);
		param.put("clase", clase);
		return (ArrayList<Marker>)getSqlMapClientTemplate().queryForList("obtenerMarkersComplejoClase", param);
	}

	/*
	 * (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#obtenerMarkersPelicula(cl.zpricing.revman.model.Pelicula)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> obtenerMarkersPelicula(Pelicula pelicula) {
		return (ArrayList<Marker>)getSqlMapClientTemplate().queryForList("obtenerMarkersPelicula", pelicula.getId());
	}

	/*
	 * (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#obtenerMarkersPeliculaClase(cl.zpricing.revman.model.Pelicula, cl.zpricing.revman.model.Clase)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> obtenerMarkersPeliculaClase(Pelicula pelicula, Clase clase) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pelicula", pelicula);
		param.put("clase", clase);
		return (ArrayList<Marker>)getSqlMapClientTemplate().queryForList("obtenerMarkersPeliculaClase", param);
	}

	/*
	 * (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#obtenerMarkersPeliculaComplejo(cl.zpricing.revman.model.Pelicula, cl.zpricing.revman.model.Complejo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> obtenerMarkersPeliculaComplejo(Pelicula pelicula, Complejo complejo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pelicula", pelicula);
		param.put("complejo", complejo);
		return (ArrayList<Marker>)getSqlMapClientTemplate().queryForList("obtenerMarkersPeliculaComplejo", param);
	}

	/*
	 * (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#obtenerMarkersPeliculaComplejoClase(cl.zpricing.revman.model.Pelicula, cl.zpricing.revman.model.Complejo, cl.zpricing.revman.model.Clase)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> obtenerMarkersPeliculaComplejoClase(Pelicula pelicula, Complejo complejo, Clase clase) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("complejo", complejo);
		param.put("clase", clase);
		param.put("pelicula", pelicula);
		return (ArrayList<Marker>)getSqlMapClientTemplate().queryForList("obtenerMarkersPeliculaComplejoClase", param);
	}

	/*
	 * (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#obtenerMarkersFecha(java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> obtenerMarkersFecha(Date fecha) {
		return (ArrayList<Marker>)getSqlMapClientTemplate().queryForList("obtenerMarkersFecha2", fecha);
	}

	/*
	 * (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#obtenerMarkersFechaComplejoPelicula(java.util.Date, cl.zpricing.revman.model.Complejo, cl.zpricing.revman.model.Pelicula)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> obtenerMarkersFechaComplejoPelicula(Date fecha, Complejo complejo, Pelicula pelicula) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fecha", fecha);
		param.put("complejo", complejo);
		param.put("pelicula", pelicula);
		return (List<Marker>)getSqlMapClientTemplate().queryForList("obtenerMarkersFechaPeliculaComplejo", param);
	}

	/*
	 * (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.MarkerDao#obtenerMarkersFechaComplejoPeliculaClase(java.util.Date, cl.zpricing.revman.model.Complejo, cl.zpricing.revman.model.Pelicula, cl.zpricing.revman.model.Clase)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> obtenerMarkersFechaComplejoPeliculaClase(Date fecha, Complejo complejo, Pelicula pelicula, Clase clase) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fecha", fecha);
		param.put("complejo", complejo);
		param.put("pelicula", pelicula);
		param.put("clase", clase);
		return (List<Marker>)getSqlMapClientTemplate().queryForList("obtenerMarkersFechaPeliculaComplejoClase", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> obtenerMarkersFechaComplejo(Date fecha, Complejo complejo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fecha", fecha);
		param.put("complejo", complejo);
		return (List<Marker>)getSqlMapClientTemplate().queryForList("obtenerMarkersFechaComplejo", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Marker> listaNMarkerTodos(){
		return (List<Marker>) getSqlMapClientTemplate().queryForList("listaNMarkerTodos");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoMarker> listaTipoNMarkersTodos(){
		return (List<TipoMarker>) getSqlMapClientTemplate().queryForList("listaTipoNMarkersTodos");
	}

	@Override
	public void actualizarNMarkers(Marker[] markers){
		log.debug("actualizarNMarkers...");
		try {
			getSqlMapClientTemplate().delete("eliminarComplejosNMarkers");
			getSqlMapClientTemplate().delete("eliminarNMarkers");
			for(int i=0;i < markers.length; i++) {
				Marker marker = markers[i];
				Integer marker_id = (Integer) getSqlMapClientTemplate().insert("agregarNMarker", marker);
				Complejo[] complejos = marker.getComplejos();
				for(int j=0; j< complejos.length; j++) {
					Complejo complejo = complejos[j];
					Map<String, Integer> param= new HashMap<String, Integer>(2);
					param.put("complejo_id", complejo.getId());
					param.put("marker_id", marker_id);
					getSqlMapClientTemplate().insert("agregaRelacionComplejosNMarkers", param);
				}
			}
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}
	
}