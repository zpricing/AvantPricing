/**
 * 
 */
package cl.zpricing.avant.servicios;

import java.util.ArrayList;

import cl.zpricing.avant.model.TipoMarker;

/**
 * <b>Descripci�n de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 30-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public interface TipoMarkerDao {

	/**
	 * @param nuevoMarker
	 *            tipoMarker a agregar
	 * @return si se pudo agregar
	 */
	public boolean agregarTipoMarker(TipoMarker nuevoMarker);

	/**
	 * @return lista de tipos de markers
	 */
	public ArrayList<TipoMarker> listarTipoMarker();

	/**
	 * @param id
	 *            del tipo de Marker a eliminar
	 * @return si se pudo eliminar
	 */
	public boolean eliminarTipoMarker(int id);

	/**
	 * @param parseInt
	 *            id del tipo de Marker a obtener
	 * @return tipoMarker correspondiente al id
	 */
	public TipoMarker obtenerTipoMarker(int parseInt);

	/**
	 * @param nuevomarker
	 *            tipoMarker a modificar
	 * @return si el tipoMarker fue modificado
	 */
	public boolean modificarTipoMarker(TipoMarker nuevomarker);
}
