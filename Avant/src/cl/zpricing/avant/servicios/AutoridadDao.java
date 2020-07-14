package cl.zpricing.avant.servicios;

import java.util.List;

import cl.zpricing.avant.model.Autoridad;
import cl.zpricing.avant.model.Rol;

/**
 * <b>Descripci�n de la Clase</b>
 * Dao para obtener informacion acerca de autoridades
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 09-02-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public interface AutoridadDao{
	/**
	 * @param autoridad
	 */
	public void agregarAutoridad(Autoridad autoridad);
	
	/**
	 * Elimina una autoridad si no hay ningun rol que la posea
	 * @param id
	 * @return si la autoridad fue eliminada
	 */
	public boolean eliminarAutoridad(int id);
	
	/**
	 * @param rol_id
	 * @return lista de autoridades para un rol dado 
	 */
	public List<Autoridad> obtenerAutoridades(int rol_id);
	
	/**
	 * @param rol_id
	 * @return el rol que le pertenece a ese id
	 */
	public Rol obtenerRolAutoridad(int rol_id);
}