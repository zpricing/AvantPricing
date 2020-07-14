/**
 * 
 */
package cl.zpricing.avant.servicios;

import java.util.GregorianCalendar;
import java.util.List;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.reports.PrecioAsistencia;
import cl.zpricing.avant.model.reports.Rango;
import cl.zpricing.avant.web.chart.Valor;

/**
 * Interface para accesar y obtener datos de la base de datos con la finalidad
 * de generar reportes
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 20-01-2009 Julio Olivares Alarcon: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public interface ReporteDao {
	/**
	 * @param limites
	 *            (ver Rango)
	 * @return ticket promedio por Complejo en un rango de fechas
	 */
	public Valor ticketPromedioPorPeriodo(Rango limites);

	/**
	 * @param limites
	 *            (ver Rango)
	 * @return lista de pares precio-asistencia por Complejo en un limite de
	 *         tiempo
	 */
	public List<PrecioAsistencia> precioAsistenciaPorPeriodo(Rango limites);

	/**
	 * @return lista de precios disponibles
	 */
	public List<Valor> obtenerPrecios();

	/**
	 * @param fecha_inicio
	 *            del rango
	 * @param fecha_termino
	 *            del rango
	 * @param complejo
	 *            complejo
	 * @return ingresos promedio por complejo en un rango de fechas dado
	 */
	public double ingresosPromedio(GregorianCalendar fecha_inicio,
			GregorianCalendar fecha_termino, Complejo complejo);

	/**
	 * @param limites
	 *            (ver Rango)
	 * @return asistencia e ingreso promedio para un complejo en un rango de
	 *         fechas dado
	 */
	public PrecioAsistencia asistenciaIngresoPorPeriodo(Rango limites);

	/**
	 * @param limites
	 *            (ver Rango)
	 * @return asistencia e ingreso promedio para un complejo en un rango de
	 *         fechas dado
	 */
	public PrecioAsistencia asistenciaIngresoPorPeriodoRM(Rango limites);

	/**
	 * @param limites
	 *            (ver Rango)
	 * @return ingresos de confiteria para un complejo en un rango de fechas
	 *         dado
	 */
	public Long ingresosConfiteriaPorPeriodo(Rango limites);
}
