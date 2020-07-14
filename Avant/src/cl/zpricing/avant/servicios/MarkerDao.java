package cl.zpricing.avant.servicios;

import java.util.Date;
import java.util.List;

import cl.zpricing.avant.model.Clase;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Marker;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.TipoMarker;

/**
 * Interface para accesar los datos de los Markers en la base de datos
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 30-12-2008 Julio Olivares Alarcon: version inicial.</li>
 * <li>2.0 20-01-2009 Mario Lavandero Soto: Agregada funcionalidad para rangos de fecha.</li>
 * <li>2.5 20-01-2009 Mario Lavandero Soto: Agregadas consultas para manejo markers clase (Precio Especial).</li>
 * <li>3.0 12-02-2009 Daniel Estevez Garay: Agregadas consultas con todas la combinaciones posibles en el manejo de markers</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public interface MarkerDao {

	/**
	 * @param marker
	 *            a agregar
	 * @return si se agrego el marker
	 */
	public boolean agregarMarker(Marker marker);

	/**
	 * @return lista de todos los markers
	 */
	public List<Marker> listaMarkerTodos();

	/**
	 * @param date
	 *            fecha para la cual se desea la lista
	 * @return lista de markers para una fecha dada
	 */
	public List<Marker> listaMarkersByFecha(Date date);

	/**
	 * @param inicio
	 *            del rango de fechas
	 * @param fin
	 *            del rango de fechas
	 * @return lista de markers en el rango de fechas
	 */
	public List<Marker> listaMarkersEntreFechas(Date inicio, Date fin);
	
	/**
	 * @param id
	 * @return lista de markers para el id dado
	 */
	public List<Marker> listaMarkersByID(int id);

	/**
	 * @param marker a eliminar
	 * @return si elimino el marker o no
	 */
	public boolean eliminarMarker(Marker marker);

	/**
	 * @param marker
	 *            a actualizar
	 */
	public void actualizarMarker(Marker marker);

	/**
	 * Obsoleta
	 * 
	 * @param marker
	 * @param inicio
	 * @param fin
	 */
	@Deprecated
	public void agregarRangoMarkers(Marker marker, Date inicio, Date fin);

	/**
	 * @param id
	 *            del marker a buscar
	 * @return marker deseado
	 */
	public Marker obtenerMarker(int id);

	/**
	 * @return lista de markers de clases
	 */
	public List<Marker> obtenerMarkersClase();
	
	
	/**
	 * 
	 * Obtiene los markers de determinada fecha.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 12-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param fecha fecha a consultar
	 * @return lista de markers
	 * @since 1.0
	 */
	public List<Marker> obtenerMarkersFecha(Date fecha);
	
	/**
	 * 
	 * Obtiene los markers de determinada fecha, pelicula y complejo.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param fecha fecha a consultar
	 * @param complejo complejo a consultar
	 * @param pelicula pel�cula a consultar
	 * @return lista de markers
	 * @since 1.0
	 */
	public List<Marker> obtenerMarkersFechaComplejoPelicula(Date fecha, Complejo complejo, Pelicula pelicula);
	
	/**
	 * 
	 * Obtiene los markers de determinada fecha, pel�cula, complejo y clase.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param fecha fecha a consultar
	 * @param complejo complejo a consultar
	 * @param pelicula pel�cula a consultar
	 * @param clase clase a consultar
	 * @return lista de markers
	 * @since 1.0
	 */
	public List<Marker> obtenerMarkersFechaComplejoPeliculaClase(Date fecha, Complejo complejo, Pelicula pelicula, Clase clase);
	
	/**
	 * 
	 * Obtiene los markers de un complejo.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param complejo complejo a consultar
	 * @return lista de markers
	 * @since 1.0
	 */
	public List<Marker> obtenerMarkersComplejo(Complejo complejo);
	
	/**
	 * 
	 * Obtiene los markers de una pel�cula.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param pelicula pelicula a consultar
	 * @return lista de markers
	 * @since 1.0
	 */
	public List<Marker> obtenerMarkersPelicula(Pelicula pelicula);
	
	/**
	 * 
	 * Obtiene los markers de una clase.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param clase
	 * @return lista de markers
	 * @since 1.0
	 */
	public List<Marker> obtenerMarkersClase(Clase clase);
	
	/**
	 * 
	 * Obtiene los markers de una pelicula en un complejo.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param pelicula pelicula del complejo
	 * @param complejo complejo a consultar
	 * @return lista de markers
	 * @since 1.0
	 */
	public List<Marker> obtenerMarkersPeliculaComplejo(Pelicula pelicula, Complejo complejo);
	
	
	/**
	 * 
	 * Obtiene los markers de una pel�cula y clase determinada.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param pelicula pel�cula a consultar
	 * @param clase clase a consultar
	 * @return lista de markers
	 * @since 1.0
	 */
	public List<Marker> obtenerMarkersPeliculaClase(Pelicula pelicula, Clase clase);
	
	/**
	 * 
	 * Obtiene los markers de un complejo y clase determinado.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param complejo complejo a consultar
	 * @param clase clase a consultar
	 * @return lista de markers
	 * @since 1.0
	 */
	public List<Marker> obtenerMarkersComplejoClase(Complejo complejo, Clase clase);
	
	/**
	 * 
	 * Obtiene los markers de determinada pel�cula, complejo y clase.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param pelicula pel�cula a consultar
	 * @param complejo complejo a consultar
	 * @param clase clase a consultar
	 * @return lista de markers
	 * @since 1.0
	 */
	public List<Marker> obtenerMarkersPeliculaComplejoClase(Pelicula pelicula, Complejo complejo, Clase clase);
	
	public List<Marker> obtenerMarkersFechaComplejo(Date fecha, Complejo complejo);
	
	public List<Marker> listaNMarkerTodos();
	
	public List<TipoMarker> listaTipoNMarkersTodos();

	public void actualizarNMarkers(Marker[] markers);
}
