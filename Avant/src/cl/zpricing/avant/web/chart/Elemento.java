package cl.zpricing.avant.web.chart;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <b>Descripci�n de la Clase</b>
 * Interfaz que define el metodo para crear el Element del XML
 * Registro de versiones:
 * <ul>
 *   <li>1.0 05-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public interface Elemento {
	
	/**
	 * @param documento el cual se esta usando para generar el grafico
	 * @return element del elemento
	 */
	public Element crearElemento(Document documento);
}
