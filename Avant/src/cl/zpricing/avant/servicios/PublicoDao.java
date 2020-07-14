package cl.zpricing.avant.servicios;

import java.util.ArrayList;

import cl.zpricing.avant.model.Publico;

/**
 * 
 * <b>DAO para el manejo de tipos de p�blico</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 18-02-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public interface PublicoDao {

	/**
	 * 
	 * Obtiene un tipo de p�blico desde la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param id
	 *            identificador del tipo de p�blico
	 * @return tipo de p�blico
	 * @since 1.0
	 */
	public Publico obtenerPublico(int id);

	/**
	 * 
	 * Agrega un tipo de p�blico en la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param publico
	 *            tipo de p�blico a agregar
	 * @since 1.0
	 */
	public void agregarPublico(Publico publico);

	/**
	 * 
	 * Elimina un tipo de p�blico en la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param publico
	 *            tipo de p�blico a eliminar
	 * @since 1.0
	 */
	public void eliminarPublico(Publico publico);

	/**
	 * 
	 * Actualiza un tipo de p�blico en la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param publico
	 *            tipo de p�blico a actualizar
	 * @since 1.0
	 */
	public void actualizarPublico(Publico publico);

	/**
	 * 
	 * Obtiene todos los tipos de p�blico existentes en la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return lista de tipos de p�blico
	 * @since 1.0
	 */
	public ArrayList<Publico> obtenerPublicos();

}
