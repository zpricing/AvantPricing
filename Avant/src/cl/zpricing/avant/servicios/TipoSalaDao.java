/**
 * 
 */
package cl.zpricing.avant.servicios;

import java.util.List;

import cl.zpricing.avant.model.TipoSala;

/**
 * <b>Descripci�n de la Clase</b> Dao para administrar los tipos de sala
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 29-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public interface TipoSalaDao {

	/**
	 * @return lista de todos los tipos de Sala
	 */
	public List<TipoSala> tipoSalaTodos();
}
