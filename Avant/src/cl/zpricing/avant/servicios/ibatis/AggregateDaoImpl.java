/**
 * 
 */
package cl.zpricing.avant.servicios.ibatis;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Aggregate;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.servicios.AggregateDao;

/**
 * <b>Descripci�n de la Clase</b> Implementacion de AggregateDao
 * 
 * Registro de versiones:
 * <ul>
 * 
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class AggregateDaoImpl extends SqlMapClientDaoSupport implements
		AggregateDao {

	/**
	 * Impresi�n de log.
	 */
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @seecl.zpricing.revman.servicios.AggregateDao#
	 * obtenerAsistenciaDiariaPeliculaComplejo
	 * (cl.zpricing.revman.servicios.Date, int, int)
	 */
	@Override
	public int obtenerAsistenciaDiariaPeliculaComplejo(Date fecha,
			int peliculaId, int complejoId) {
		// log.debug("obtenerAsistenciaDiariaPeliculaComplejo...");
		Aggregate agg = new Aggregate();
		agg.setComplejo_id(complejoId);
		agg.setPelicula_id(peliculaId);
		agg.setFecha(fecha);
		/*
		 * log.debug("complejoId: " + agg.getComplejo_id());
		 * log.debug("peliculaId: " + agg.getPelicula_id()); log.debug("fecha: "
		 * + agg.getFecha().toString());
		 */
		int result;
		try {
			result = (Integer) getSqlMapClientTemplate().queryForObject(
					"obtenerAsistenciaDiaPeliculaComplejo", agg);
		} catch (Exception e) {
			result = 0;
		}
		// log.debug("----------------Fin-------------");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.AggregateDao#obtenerAsistenciaDiariaComplejo
	 * (java.util.GregorianCalendar, java.util.GregorianCalendar, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int obtenerAsistenciaDiariaComplejo(Date fecha, int complejoId) {

		Aggregate agg = new Aggregate();
		agg.setComplejo_id(complejoId);
		agg.setFecha(fecha);

		int asistencias_totales = 0;

		List<Integer> result = (List<Integer>) getSqlMapClientTemplate()
				.queryForList("obtenerAsistenciaDiaComplejo", agg);

		Iterator<Integer> it = result.iterator();

		while (it.hasNext()) {
			Integer i = it.next();
			asistencias_totales = asistencias_totales + i;
		}

		return asistencias_totales;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecl.zpricing.revman.servicios.AggregateDao#
	 * obtenerIngresosConfiteriaDiarioComplejo(java.util.Date, int)
	 */
	@Override
	public double obtenerIngresosConfiteriaDiarioComplejo(Date fecha,
			int complejoId) {
		HashMap<String, Object> map = new HashMap<String, Object>(2);
		map.put("fecha", fecha);
		map.put("complejoId", complejoId);
		try {
			return (Double) getSqlMapClientTemplate().queryForObject(
					"obtenerIngresosConfiteriaDiarioComplejo", map);
		} catch (Exception e) {
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.AggregateDao#obtenerAsistenciaRMDiarioComplejo
	 * (java.util.Date, cl.zpricing.revman.model.Complejo)
	 */
	@Override
	public double obtenerAsistenciaRMDiarioComplejo(Date fecha,
			Complejo complejo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fecha", fecha);
		map.put("complejoId", complejo.getId());
		try {
			return (Double) getSqlMapClientTemplate().queryForObject(
					"obtenerAsistenciaRMDiaComplejo", map);
		} catch (Exception e) {
			return 0.0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.AggregateDao#obtenerIngresosRMDiarioComplejo
	 * (java.util.Date, cl.zpricing.revman.model.Complejo)
	 */
	@Override
	public double obtenerIngresosRMDiarioComplejo(Date fecha, Complejo complejo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fecha", fecha);
		map.put("complejoId", complejo.getId());
		try {
			return (Double) getSqlMapClientTemplate().queryForObject(
					"obtenerIngresosRMDiaComplejo", map);
		} catch (Exception e) {
			return 0.0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.AggregateDao#obtenerIngresosDiarioComplejo
	 * (java.util.Date, cl.zpricing.revman.model.Complejo)
	 */
	@Override
	public double obtenerIngresosDiarioComplejo(Date fecha, Complejo complejo) {
		Map<String, Object> map = new HashMap<String, Object>();
		// Agrego 3 horas para el trasnoche
		Calendar cal = new GregorianCalendar();
		cal.setTime(fecha);
		cal.add(Calendar.HOUR_OF_DAY, 3);
		Date fecha1 = cal.getTime();
		map.put("fecha1", fecha1);
		// Sumo un dia para tener la diferencia de tiempo
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date fecha2 = cal.getTime();
		map.put("fecha2", fecha2);
		map.put("complejoId", complejo.getId());
		try {
			return (Double) getSqlMapClientTemplate().queryForObject(
					"obtenerIngresosDiaComplejo", map);
		} catch (Exception e) {
			return 0.0;
		}
	}
}
