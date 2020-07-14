package cl.zpricing.avant.negocio;

import java.util.ArrayList;
import java.util.Date;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Marker;

/**
 * <b>Descripci�n de la Clase</b>
 * Dao utilizado para obtener datos acerca de los markers
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 16-01-2009 MARIO: versi�n inicial.</li>
 *   <li>1.2 16-01-2009 MARIO: Agregada la funcion para obtener precios especiales.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public interface MarkerManager {

	/**
	 * @param fecha de inicio
	 * @param fecha de termino
	 * @return lista de markers en ese rango de fechas
	 */
	public ArrayList<Marker> obtenerMarkersFechas(Date inicio, Date fin);
	
	/**
	 * @param fecha
	 * @param complejoId
	 * @param peliculaId
	 * @return markers asociados a una fecha en particular
	 */
	public ArrayList<Marker> obtenerMarkers(Date fecha, int complejoId, int peliculaId);
	
	/**
	 * @param fecha
	 * @return si es feriado en esa fecha
	 */
	public boolean isFechaFeriado(Date fecha);
	
	/**
	 * @param fecha
	 * @return si es feriado en esa fecha
	 */
	public boolean isFechaFeriado(Date fecha, Complejo complejo);
	
	/**
	 * @param fecha
	 * @return si es vacaciones en esa fecha
	 */
	public boolean isFechaVacaciones(Date fecha);
	
	/**
	 * @param fecha
	 * @return si es una fecha normal
	 */
	public boolean isFechaNormal(Date fecha);
	
	/**
	 * Obtiene un array con los precios que seran considerados especiales. Estos son
	 * los precios marcados con el marker de tipo Precio Especial.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 28-01-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param complejo
	 * @return array con los precios considerados especiales
	 * @since 1.0
	 */
	public ArrayList<Double> obtenerPreciosEspeciales(Complejo complejo);
	

}
