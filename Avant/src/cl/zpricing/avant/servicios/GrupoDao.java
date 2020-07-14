package cl.zpricing.avant.servicios;

import java.util.List;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Grupo;

public interface GrupoDao {
	/**
	 * 
	 * Obtiene todas las salas de un complejo.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> </li>
	 * </ul>
	 * </P>
	 * 
	 * @param complejo complejo por el cual se consulta
	 * @return lista de grupos de salas del complejo
	 * @since 1.0
	 */
	public List<Grupo> obtenerTodosByComplejo(Complejo complejo);
	
	public List<Grupo> obtenerTodos();
	
	public void agregar(Grupo grupo);
}
