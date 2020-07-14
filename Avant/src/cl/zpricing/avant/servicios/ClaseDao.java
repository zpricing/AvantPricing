package cl.zpricing.avant.servicios;

import java.util.ArrayList;

import cl.zpricing.avant.model.Clase;

/**
 * 
 * <b>DAO para el manejo de las clases</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 18-02-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public interface ClaseDao {

	/**
	 * 
	 * Obtiene una Clase desde la capa de datos de determinado identificador.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param id
	 *            identificador de la Clase
	 * @return clase
	 * @since 1.0
	 */
	public Clase obtenerClase(int id);

	/**
	 * Obtiene una clase desde la capa de datos de determinado precio.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 14-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param precio
	 *            precio de la clase
	 * @return clase
	 * @since 1.0
	 */
	public Clase obtenerClasePrecio(double precio);

	/**
	 * 
	 * Agrega una clase a la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param Clase
	 *            clase a agregar
	 * @since 1.0
	 */
	public void agregarClase(Clase Clase);

	/**
	 * 
	 * Elimina una clase de la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param Clase
	 *            clase a eliminar
	 * @since 1.0
	 */
	public void eliminarClase(Clase Clase);

	/**
	 * 
	 * Actualiza una clase de la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param Clase
	 *            clase a actualizar
	 * @since 1.0
	 */
	public void actualizarClase(Clase Clase);

	/**
	 * 
	 * Obtiene todas las clases desde la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return ArrayList de objetos Clase
	 * @since 1.0
	 */
	public ArrayList<Clase> obtenerListaDeClases();

	/**
	 * 
	 * Obtiene todas las clases en orden decendente desde la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return lista de clases
	 * @since 1.0
	 */
	public ArrayList<Clase> obtenerListaDeClasesDesc();
}
