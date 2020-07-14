
package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.model.prediction.PrediccionIncompleta;
import cl.zpricing.avant.model.prediction.PrediccionParametros;
import cl.zpricing.avant.model.prediction.PrediccionPorClase;
import cl.zpricing.avant.model.prediction.PrediccionPorDia;
import cl.zpricing.avant.model.prediction.PrediccionPorFuncion;
import cl.zpricing.avant.util.Util;
/**
 * 
 * Implementacion DAO para iBatis para el objeto Prediccion
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 17/12/2008 Daniel Estevez Garay: version inicial.</li>
 *   <li>1.1 23/06/2009 Mario Lavandero: Se agrego que al actualizar la prediccion por funcion se actualizan los parametros de la funcion
 *   									asociada, en especial si la funcion esta cargada o no.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class PrediccionDaoImpl extends SqlMapClientDaoSupport implements cl.zpricing.avant.servicios.PrediccionDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#actualizarPrediccion(cl.zpricing.revman.model.Prediccion)
	 */
	public void actualizarPrediccion(Prediccion prediccion) {
		getSqlMapClientTemplate().update("actualizarPrediccion", prediccion);
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#agregarPrediccion(cl.zpricing.revman.model.Prediccion)
	 */
	public int agregarPrediccion(Prediccion prediccion) {
		return (Integer) getSqlMapClientTemplate().insert("agregarPrediccion", prediccion);		
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#eliminarPrediccion(cl.zpricing.revman.model.Prediccion)
	 */
	public void eliminarPrediccion(Prediccion prediccion) {
//		log.debug(" > eliminarPrediccion(): prediccion.id = " + prediccion.getId());
		getSqlMapClientTemplate().delete("eliminarPrediccion", prediccion);		
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#obtenerListaPredicciones(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Prediccion> obtenerListaPredicciones(String estado) {
		ArrayList<Prediccion> predicciones = 
			(ArrayList<Prediccion>) getSqlMapClientTemplate().queryForList("obtenerListaPredicciones", estado);
		return predicciones;
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#obtenerListaPrediccionesUsuario(cl.zpricing.revman.model.Usuario)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Prediccion> obtenerListaPrediccionesUsuario(Usuario usuario) {
		return (ArrayList<Prediccion>) getSqlMapClientTemplate().queryForList("obtenerPrediccionesUsuario", usuario.getId());
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#obtenerListaPrediccionesUsuarioCargadas(cl.zpricing.revman.model.Usuario)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<PrediccionIncompleta> obtenerListaPrediccionesUsuarioCargadas(
			Usuario usuario) {
		ArrayList<PrediccionIncompleta> predicciones = (ArrayList<PrediccionIncompleta>) getSqlMapClientTemplate().queryForList("obtenerPrediccionesUsuarioCargadas", usuario.getId());
		return predicciones;
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#obtenerListaPrediccionesusuarioCargadas(cl.zpricing.revman.model.Usuario, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<PrediccionIncompleta> obtenerListaPrediccionesUsuarioCargadas(Usuario usuario, int pagina, int numeroResultados) {
		return (ArrayList<PrediccionIncompleta>) getSqlMapClientTemplate().queryForList("obtenerPrediccionesUsuarioCargadas", usuario.getId(), (pagina-1)*numeroResultados, numeroResultados);
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#obtenerListaPrediccionesCargadas(int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<PrediccionIncompleta> obtenerListaPrediccionesCargadas(
			int pagina, int numeroResultados) {
		return (ArrayList<PrediccionIncompleta>) getSqlMapClientTemplate().queryForList("obtenerPrediccionesCargadas", (pagina-1)*numeroResultados, numeroResultados);
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#obtenerPrediccion(int)
	 */
	@SuppressWarnings("unchecked")
	public Prediccion obtenerPrediccion(int id) {
		Prediccion prediccion = (Prediccion) getSqlMapClientTemplate().queryForObject("obtenerPrediccion", id);
		prediccion.setPrediccionParametros((ArrayList<PrediccionParametros>) getSqlMapClientTemplate().queryForList("obtenerParametrosPrediccion", id));
		return prediccion;		
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#obtenerPrediccionPorDia(int)
	 */
	@Override
	public PrediccionPorDia obtenerPrediccionPorDia(int id) {
		PrediccionPorDia prediccionPorDia = (PrediccionPorDia) getSqlMapClientTemplate().queryForObject("obtenerPrediccionPorDiaPorId", id);
		return prediccionPorDia;
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#obtenerUltimaPrediccion(java.util.Map)
	 */
	@Override
	public Prediccion obtenerUltimaPrediccion(Map<String, Object> parametros) {
		return (Prediccion)getSqlMapClientTemplate().queryForObject("obtenerUltimaPrediccion", parametros);
	}
	
	/**
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 05-01-2010 Roberto Vargas: Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param parametros
	 * @return
	 * @since 3.0
	 */
	public Prediccion obtenerUltimaPrediccionCualquierComplejo(Map<String, Object> parametros) {
		return (Prediccion)getSqlMapClientTemplate().queryForObject("obtenerUltimaPrediccionCualquierComplejo", parametros);
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#obtenerPredicciones()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Prediccion> obtenerPredicciones() {
		
		return (ArrayList<Prediccion>) getSqlMapClientTemplate().queryForList("obtenerPredicciones");
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#obtenerPrediccionSinPrediccionPorClase()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrediccionIncompleta> obtenerPrediccionSinPrediccionPorClase(int idUsuario) {
		
		return (List<PrediccionIncompleta>) getSqlMapClientTemplate().queryForList("obtenerPrediccionSinPrediccionPorClase",idUsuario);
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#obtenerPrediccionSinPrediccionPorFuncion()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrediccionIncompleta> obtenerPrediccionSinPrediccionPorFuncion(int idUsuario) {
		return (List<PrediccionIncompleta>) getSqlMapClientTemplate().queryForList("obtenerPrediccionSinPrediccionPorFuncion",idUsuario);
	}

	@SuppressWarnings("unchecked")
	public List<PrediccionParametros> obtenerParametrosPrediccion(int idPrediccion) {
		return (List<PrediccionParametros>) getSqlMapClientTemplate().queryForList("obtenerParametrosPrediccion", idPrediccion);
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#actualizarPrediccionPorClase(cl.zpricing.revman.model.PrediccionPorClase)
	 */
	@Override
	public void actualizarPrediccionPorClase(PrediccionPorClase prediccion) {
		getSqlMapClientTemplate().update("actualizarPrediccionPorClase", prediccion);
		
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#actualizarPrediccionPorDia(cl.zpricing.revman.model.PrediccionPorDia)
	 */
	@Override
	public void actualizarPrediccionPorDia(PrediccionPorDia prediccion) {
		getSqlMapClientTemplate().update("actualizarPrediccionPorDia", prediccion);	
		
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#actualizarPrediccionPorFuncion(cl.zpricing.revman.model.PrediccionPorFuncion)
	 */
	@Override
	public void actualizarPrediccionPorFuncion(PrediccionPorFuncion prediccion) {
//		try {
//			log.debug("DAO: se guardará la ppf " + prediccion.getId());
//		} catch (Exception e) {
//			log.debug("No se pudo mostrar la id de la ppf");
//		}
//
//		try {
//			log.debug(" de la ppd " + prediccion.getPrediccionPorDia().getId());
//		} catch (Exception e) {
//			log.debug("No se pudo mostrar la id de la ppd");
//		}
//
//		try {
//			log.debug(" de la prediccion " + prediccion.getPrediccionPorDia().getPrediccion().getId());
//		} catch (Exception e) {
//			log.debug("No se pudo mostrear la id de la prediccion.");
//		}
		
		getSqlMapClientTemplate().update("actualizarPrediccionPorFuncion", prediccion);
		//getSqlMapClientTemplate().update("actualizarFuncion", prediccion.getFuncionPredecida());
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#agregarPrediccionPorDia(cl.zpricing.revman.model.PrediccionPorDia)
	 */
	@Override
	public int agregarPrediccionPorDia(PrediccionPorDia prediccion) {
		return (Integer) getSqlMapClientTemplate().insert("agregarPrediccionPorDia", prediccion);
		
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#agregarPrediccionPorFuncion(cl.zpricing.revman.model.PrediccionPorFuncion)
	 */
	@Override
	public int agregarPrediccionPorFuncion(PrediccionPorFuncion prediccion) {
		log.debug("Se intentará agregar la predicción por función: ");
		log.debug(" id de la prediccion: " + prediccion.getPrediccionPorDia().getPrediccion().getId());
		log.debug(" id de la prediccionPorDia: " + prediccion.getPrediccionPorDia().getId());
		log.debug(" mascara: " + prediccion.getMascara().getId());
		log.debug(" estimacion: " + prediccion.getEstimacion());
		log.debug(" fecha: " + Util.DateToString(prediccion.getFuncionPredecida().getFecha()));
		log.debug(" sala: " + prediccion.getFuncionPredecida().getSala().getNumero());
		log.debug(" varianza: " + prediccion.getVarianza());
		Integer returnValue = (Integer) getSqlMapClientTemplate().insert("agregarPrediccionPorFuncion", prediccion);
//		log.debug(" id de esta ppf: " + returnValue);
		return returnValue;
		
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#agregarPrediccionporClase(cl.zpricing.revman.model.PrediccionPorClase)
	 */
	@Override
	public int agregarPrediccionporClase(PrediccionPorClase prediccion) {
		return (Integer) getSqlMapClientTemplate().insert("agregarPrediccionPorClase", prediccion);
		
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#eliminarPrediccionPorClase(cl.zpricing.revman.model.PrediccionPorClase)
	 */
	@Override
	public void eliminarPrediccionPorClase(PrediccionPorClase prediccion) {
		getSqlMapClientTemplate().delete("eliminarPrediccionPorClase", prediccion);
			
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#eliminarPrediccionPorDia(cl.zpricing.revman.model.PrediccionPorDia)
	 */
	@Override
	public void eliminarPrediccionPorDia(PrediccionPorDia prediccion) {
	getSqlMapClientTemplate().delete("eliminarPrediccionPorDia", prediccion);	
		
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PrediccionDao#eliminarPrediccionPorFuncion(cl.zpricing.revman.model.PrediccionPorFuncion)
	 */
	@Override
	public void eliminarPrediccionPorFuncion(PrediccionPorFuncion prediccion) {
		getSqlMapClientTemplate().delete("eliminarPrediccionPorFuncion", prediccion);

		
	}

	@Override
	public void agregarPrediccionParametros(PrediccionParametros prediccionParametros) {
//		if (prediccionParametros != null) {
//		log.debug("Se intentará agregar el siguiente objecto PrediccionParametros:" +
//			" Pelicula: " + prediccionParametros.getPelicula().getNombre() +
//			" Prediccion: " + prediccionParametros.getPrediccion().getId() +
//			" Ponderacion: " + prediccionParametros.getPonderacion()); }
//		else {
//			log.debug("El objeto prediccionParametros a ingresar es null.");
//		}
//		
		try {
			getSqlMapClientTemplate().insert("agregarPrediccionParametros", prediccionParametros);
		} catch (Exception e) {
//			log.debug("Falló .insert(\"agregarPrediccionParametros\", prediccionParametros");
//			log.debug(ErroresUtils.extraeStackTrace(e));
		}
	}

	@Override
	public void actualizarPrediccionParametros(PrediccionParametros prediccionParametros) {
//		log.debug("Actualizando PrediccionParametros: ");
//		log.debug(" - prediccion: " + prediccionParametros.getPrediccion().getId());
//		log.debug(" - pelicula: " + prediccionParametros.getPelicula().getId());
//		log.debug(" - ponderacion: "+prediccionParametros.getPonderacion());
		getSqlMapClientTemplate().update("actualizarPrediccionParametros", prediccionParametros);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PrediccionIncompleta> obtenerPrediccionNoCargada(int idUsuario) {
		return (List<PrediccionIncompleta>) getSqlMapClientTemplate().queryForList("obtenerPrediccionNoCargada",idUsuario);
	}

	public Integer obtenerCantidadDiasPrediccion(int idPrediccion) {
		return (Integer) getSqlMapClientTemplate().queryForObject("obtenerCantidadDiasPrediccion", idPrediccion);
	}

	@Override
	public void eliminarPrediccionParametros(PrediccionParametros prediccionParametros) {
//		log.debug("Se eliminará la siguiente fila de Prediccion_Parametros:");
//		log.debug("prediccion.id = " + prediccionParametros.getPrediccion().getId() + "; pelicula.id = " + prediccionParametros.getPelicula().getId());
		getSqlMapClientTemplate().delete("eliminarPrediccionParametros", prediccionParametros);
		
	}

	@Override
	public Date obtenerFechaDesdePrediccion(Prediccion prediccion) {
//		log.debug("Se intentará obtener la fecha desde la cual se hizo la predicción " + prediccion.getId());
		Date fechaDesde = (Date)getSqlMapClientTemplate().queryForObject("obtenerFechaDesdePrediccion", prediccion.getId());
//		log.debug(fechaDesde!=null? "Se obtuvo la fecha " + Util.DateToString(fechaDesde) : "No se pudo obtener una fecha");
		return fechaDesde;
	}

	@Override
	public List<PrediccionParametros> obtenerPonderacionesPeliculas(Map<String, Object> parametros) {
		return (List<PrediccionParametros>) getSqlMapClientTemplate().queryForList("obtenerPonderacionesPeliculas", parametros);	
	}
	
	public double obtenerPonderacion(Map<String, Object> parametros){
		return (Double) getSqlMapClientTemplate().queryForObject("obtenerPonderacion", parametros);
	}
	
	public HashMap obtenerPonderaciones(int prediccion_id){
		HashMap<String, Double> hm = new HashMap<String, Double>();
		List<PrediccionParametros> listaPeliculasPonderaciones = (ArrayList<PrediccionParametros>) getSqlMapClientTemplate().queryForList("obtenerParametrosPrediccion", prediccion_id);
		Iterator<PrediccionParametros> itLista = listaPeliculasPonderaciones.iterator();
		PrediccionParametros predPar = null;
		while(itLista.hasNext()){
			predPar = itLista.next();
			String nombre = (String)getSqlMapClientTemplate().queryForObject("obtenerNombrePelicula", predPar.getPelicula().getId());
			hm.put(nombre, predPar.getPonderacion());
		}
		return hm;
	}

	@Override
	public PrediccionPorFuncion obtenerUltimaPrediccionPorFuncion(Funcion funcion) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("funcion_id", funcion.getId());
		PrediccionPorFuncion prediccionPorFuncion = (PrediccionPorFuncion) getSqlMapClientTemplate().queryForObject("obtenerUltimaPrediccionPorFuncion", mapa);
		return prediccionPorFuncion;	
	}	

 }
