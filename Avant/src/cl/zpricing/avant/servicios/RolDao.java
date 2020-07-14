/**
 * 
 */
package cl.zpricing.avant.servicios;

import java.util.List;

import cl.zpricing.avant.model.Rol;

/**
 * <b>Descripci�n de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 22-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public interface RolDao {

	/**
	 * @param rol
	 *            a agregar
	 */
	public void agregarRol(Rol rol);

	/**
	 * @param rol
	 *            a modificar
	 */
	public void modificarRol(Rol rol);

	/**
	 * @param id
	 *            del rol a eliminar
	 */
	public void eliminarRol(int id);

	/**
	 * @param id
	 * @return rol correspondiente al id
	 */
	public Rol obtenerRol(int id);

	/**
	 * @return lista de todos los roles
	 */
	public List<Rol> sacarTodos();

}