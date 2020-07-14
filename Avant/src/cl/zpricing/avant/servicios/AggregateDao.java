/**
 * 
 */
package cl.zpricing.avant.servicios;

import java.util.Date;

import cl.zpricing.avant.model.Complejo;

/**
 * <b>Descripci�n de la Clase</b> Dao para manejar tablas agregadas
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 12-01-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public interface AggregateDao {

	/**
	 * Entrega la asistencia diaria para una pelicula en un complejo en una
	 * fecha
	 * 
	 * @param fecha
	 * @param peliculaId
	 * @param complejoId
	 * @return asistencia
	 * @since 1.0
	 */
	public int obtenerAsistenciaDiariaPeliculaComplejo(Date fecha,
			int peliculaId, int complejoId);

	/**
	 * Entrega la asistencia diaria para un complejo
	 * 
	 * @param fecha
	 * @param complejoId
	 * @return asistencia
	 * @since 1.0
	 */
	public int obtenerAsistenciaDiariaComplejo(Date fecha, int complejoId);

	/**
	 * Entrega los ingresos de confiteria diarios para un complejo
	 * 
	 * @param fecha
	 * @param complejoId
	 * @return ingresos de confiteria
	 * @since 1.0
	 */
	public double obtenerIngresosConfiteriaDiarioComplejo(Date fecha,
			int complejoId);

	/**
	 * Obtiene la asistencia RM del dia para el complejo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 13-07-2009 Mario Lavandero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param fecha
	 * @param complejo
	 * @return
	 * @since 2.0
	 */
	public double obtenerAsistenciaRMDiarioComplejo(Date fecha,
			Complejo complejo);

	/**
	 * Obtiene los ingresos RM del dia para el complejo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 13-07-2009 Mario Lavandero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param fecha
	 * @param complejo
	 * @return
	 * @since 2.0
	 */
	public double obtenerIngresosRMDiarioComplejo(Date fecha, Complejo complejo);

	/**
	 * Obtiene los ingresos diarios del complejo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 14-07-2009 Mario Lavandero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param fecha
	 * @param complejo
	 * @return
	 * @since 2.0
	 */
	public double obtenerIngresosDiarioComplejo(Date fecha, Complejo complejo);
}
