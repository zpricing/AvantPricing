package cl.zpricing.avant.servicios;

import java.util.ArrayList;

import cl.zpricing.avant.model.Parametro;

/**
 * <b>Descripci�n de la Clase</b>
 * Dao para manejar los datos por defecto del sistema de Revenue
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 12-01-2009 MARIO: versi�n inicial.</li>
 *   <li>1.1 02-02-2009 MARIO: Agregado metodo para retornar string de codigo directamente.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public interface ServiciosRevenueManager {
	/**
	 * @param sistema
	 * @param subSistema
	 * @return registro asociado al sistema y subsistema dado
	 */
	public Parametro obtenerParametro(String sistema, String subSistema);
	
	/**
	 * Devuelve como un string el codigo del registro obtenido.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 02-02-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param sistema
	 * @param subSistema
	 * @return
	 * @since 1.0
	 */
	public String obtenerStringParametro(String sistema, String subSistema);
	
	/**
	 * @param sistema
	 * @param subSistema
	 * @return un entero, si el codigo puede ser convertido en un entero
	 */
	public int obtenerIntParametro(String sistema, String subSistema)
	throws Exception;
	
	/**
	 * @param sistema
	 * @param subsistema
	 * @return un double, si el codigo puede ser convertido en double
	 */
	public double obtenerDoubleParametro(String sistema, String subsistema)
	throws Exception;
	
	/**
	 * @param sistema
	 * @return lista de registros asociadas al sistema
	 */
	public ArrayList<Parametro> obtenerParametro(String sistema);
	
	/**
	 * @return lista de sistemas
	 */
	public ArrayList<String> obtenerSistemas();
	
	/**
	 * @param parametro a actualizar
	 */
	public void actualizarParametro(Parametro parametro);
	
	/**
	 * @param parametro a agregar
	 */
	public void nuevoParametro(Parametro parametro);
}
