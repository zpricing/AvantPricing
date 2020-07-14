package cl.zpricing.avant.servicios;

import java.util.List;

import cl.zpricing.avant.model.Usuario;

/**
 * <b>Descripci�n de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 31-12-2008 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public interface UsuarioDao {

	/**
	 * Obtiene un Usuario espec�fico seg�n su id
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 22/12/2008 Julio Olivares Alarc�n: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param id
	 *            Identificador del usuario a obtener.
	 * @return Retorna objeto usuario con los datos buscados
	 * @since 1.0
	 */
	public Usuario obtenerUsuario(int id);

	/**
	 * @param user
	 *            de usuario a obtener
	 * @return usuario correspondiente a user
	 */
	public Usuario obtenerUsuarioByName(String user);

	/**
	 * Agrega un usuario al sistema
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 31/12/2008 Oliver Cordero: Anhadida encriptacion</li>
	 * </ul>
	 * </P>
	 * 
	 * @param usuario
	 *            Objeto usuario con datos respectivos
	 * @return True
	 * @since 1.0
	 */
	public boolean agregarUsuario(Usuario Usuario);

	/**
	 * Actualiza un usuario seg�n su id contenido en el objeto usuario
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 31/12/2008 Oliver Cordero: Anadida Encriptacion</li>
	 * </ul>
	 * </P>
	 * 
	 * @param usuario
	 *            Objeto usuario que contiene el id del usuario a actualizar
	 * @since 1.0
	 */
	public void actualizarUsuario(Usuario Usuario);

	/**
	 * Elimina un Usuario espec�fico seg�n su id contenido en el objeto Usuario
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 22/12/2008 Julio Olivares Alarc�n: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param usuario
	 *            Objeto usuario que contiene el id a eliminar
	 * @return True si se elimina con �xito, False en caso contrario.
	 * @since 1.0
	 */
	public boolean eliminarUsuario(cl.zpricing.avant.model.Usuario Usuario);

	/**
	 * Retorna lista con todos los usuarios desde la capa de datos
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return List<Usuario>
	 * @since 1.0
	 */
	public List<Usuario> obtenerTodos();

}