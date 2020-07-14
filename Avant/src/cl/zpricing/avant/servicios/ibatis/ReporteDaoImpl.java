/**
 * 
 */
package cl.zpricing.avant.servicios.ibatis;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.reports.PrecioAsistencia;
import cl.zpricing.avant.model.reports.Rango;
import cl.zpricing.avant.servicios.ReporteDao;
import cl.zpricing.avant.web.chart.Valor;

/**
 * <b>Descripci�n de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 20-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ReporteDaoImpl extends SqlMapClientDaoSupport implements
		ReporteDao {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.ReporteDao#ticketPromedioPorPeriodo(cl.zpricing
	 * .revman.model.reports.Rango)
	 */
	@Override
	public Valor ticketPromedioPorPeriodo(Rango limites) {
		return (Valor) getSqlMapClientTemplate().queryForObject(
				"ticketPromedioPorPeriodo", limites);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.ReporteDao#precioAsistenciaPorPeriodo(cl
	 * .zpricing.revman.model.reports.Rango)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrecioAsistencia> precioAsistenciaPorPeriodo(Rango limites) {
		return (List<PrecioAsistencia>) getSqlMapClientTemplate().queryForList(
				"precioAsistenciaPorPeriodo", limites);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.ReporteDao#obtenerPrecios()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Valor> obtenerPrecios() {
		return (List<Valor>) getSqlMapClientTemplate().queryForList(
				"obtenerPrecios");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecl.zpricing.revman.servicios.ReporteDao#ingresosPromedio(java.util.
	 * GregorianCalendar, java.util.GregorianCalendar,
	 * cl.zpricing.revman.model.Complejo)
	 */
	@Override
	public double ingresosPromedio(GregorianCalendar fecha_inicio,
			GregorianCalendar fecha_termino, Complejo complejo) {

		double ingreso = 0;

		Map<String, Object> param = new HashMap<String, Object>(3);
		param.put("fecha_inicio", String.format("%1$tF %1$tT", fecha_inicio
				.getTime()));
		param.put("fecha_fin", String.format("%1$tF %1$tT", fecha_termino
				.getTime()));
		param.put("complejo", complejo);

		try {
			ingreso = (Double) getSqlMapClientTemplate().queryForObject(
					"ingresosPorPeriodo", param);
			return ingreso;
		} catch (Exception e) {
		}
		return ingreso;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.ReporteDao#asistenciaIngresoPorPeriodo(cl
	 * .zpricing.revman.model.reports.Rango)
	 */
	@Override
	public PrecioAsistencia asistenciaIngresoPorPeriodo(Rango limites) {
		return (PrecioAsistencia) getSqlMapClientTemplate().queryForObject(
				"asistenciaIngresoPorPeriodo", limites);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.ReporteDao#asistenciaIngresoPorPeriodoRM
	 * (cl.zpricing.revman.model.reports.Rango)
	 */
	@Override
	public PrecioAsistencia asistenciaIngresoPorPeriodoRM(Rango limites) {
		return (PrecioAsistencia) getSqlMapClientTemplate().queryForObject(
				"asistenciaIngresoPorPeriodoRM", limites);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.ReporteDao#ingresosConfiteriaPorPeriodo(
	 * cl.zpricing.revman.model.reports.Rango)
	 */
	@Override
	public Long ingresosConfiteriaPorPeriodo(Rango limites) {
		return (Long) getSqlMapClientTemplate().queryForObject(
				"ingresosConfiteriaPorPeriodo", limites);
	}

}