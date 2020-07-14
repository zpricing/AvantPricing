package cl.zpricing.avant.servicios;

import java.util.List;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.loadmanager.TimeSpan;

/**
 * Manejo de Objetos de tipo TimeSpan
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 31-01-2009 MARIO: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public interface TimeSpanDao {
	//public List<TimeSpan> obtenerTodos();
	public List<TimeSpan> obtenerTodosParaComplejo(Complejo complejo);
	public TimeSpan obtenerTimeSpan3d(Complejo complejo);
	public TimeSpan obtenerTimeSpan(Complejo complejo, Funcion funcion);
}
