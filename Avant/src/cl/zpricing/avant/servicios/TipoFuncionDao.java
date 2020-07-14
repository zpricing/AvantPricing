/**
 * 
 */
package cl.zpricing.avant.servicios;

import java.util.ArrayList;

import cl.zpricing.avant.model.TipoFuncion;

/**
 * <b>DAO para el manejo de tipos de funciones</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 26-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public interface TipoFuncionDao {

	/**
	 * Obtiene un tipo de funci�n desde la capa de datos
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param id
	 *            identificador del tipo de funci�n
	 * @return tipo de funci�n
	 * @since 1.0
	 */
	public TipoFuncion obtenerTipoFuncion(int id);

	/**
	 * Agrega un tipo de funci�n a la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param tipofuncion
	 *            tipo de funci�n a agregar
	 * @since 1.0
	 */
	public void agregarTipoFuncion(TipoFuncion tipofuncion);

	/**
	 * Elimina un tipo de funci�n de la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param tipofuncion
	 *            tipo de funci�n a eliminar
	 * @since 1.0
	 */
	public void eliminarTipoFuncion(TipoFuncion tipofuncion);

	/**
	 * Actualiza un tipo de funci�n en la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param tipofuncion
	 *            tipo de funci�n a actualizar
	 * @since 1.0
	 */
	public void actualizarTipoFuncion(TipoFuncion tipofuncion);

	/**
	 * Obtiene todos los tipos de funciones existentes en la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return lista de tipos de funciones
	 * @since 1.0
	 */
	public ArrayList<TipoFuncion> obtenerTipoFunciones();

}
