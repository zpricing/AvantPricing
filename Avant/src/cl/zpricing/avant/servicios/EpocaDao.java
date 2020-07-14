package cl.zpricing.avant.servicios;

import java.util.ArrayList;

import cl.zpricing.avant.model.Epoca;

/**
 * 
 * <b>DAO para el manejo de las �pocas</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 18-02-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public interface EpocaDao {

	/**
	 * 
	 * Obtiene una epoca desde la capa de datos de determinado identificador.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param id
	 *            identificador de la epoca
	 * @return �poca
	 * @since 1.0
	 */
	public Epoca obtenerEpoca(int id);

	/**
	 * 
	 * Agrega una epoca a la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param epoca
	 *            �poca a agregar
	 * @since 1.0
	 */
	public void agregarEpoca(Epoca epoca);

	/**
	 * 
	 * Elimina una epoca de la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param epoca
	 *            �poca a eliminar
	 * @since 1.0
	 */
	public void eliminarEpoca(Epoca epoca);

	/**
	 * 
	 * Actualiza una epoca en la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param epoca
	 *            �poca a actualizar
	 * @since 1.0
	 */
	public void actualizarEpoca(Epoca epoca);

	/**
	 * 
	 * Obtiene todas las epocas existentes en la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return lista de �pocas
	 * @since 1.0
	 */
	public ArrayList<Epoca> obtenerEpocas();
}
