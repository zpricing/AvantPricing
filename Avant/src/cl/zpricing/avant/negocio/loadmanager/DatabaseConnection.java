/**
 * 
 */
package cl.zpricing.avant.negocio.loadmanager;

import java.sql.Connection;
import java.util.Map;

import cl.zpricing.avant.model.Complejo;

/**
 * Encargada de la conexion a la base de datos para 
 * cargar los datos de vuelta al ERP
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 31-01-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public interface DatabaseConnection {
		
	/**
	 * Devuelve una conexion con el servidor del complejo.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 01-02-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param complejo
	 * @return
	 * @since 1.0
	 */
	public Connection Conectar(Complejo complejo);

	/**
	 * Ejecuta un comando sql.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 01-02-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param query
	 * @since 1.0
	 */
	public void executeQuery(String query);
	
	/**
	 * Cierra la conexion.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 01-02-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @since 1.0
	 */
	public void closeConnection();
	
	/**
	 * Realiza llamada a procedimientos almacenados o funciones
	 * @param atributos
	 * @return
	 */
	public Map<String,Object> ejecutarProcedimiento(Map<String,Object> atributos);
		
	
	
}
