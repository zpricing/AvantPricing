package cl.zpricing.avant.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.model.prediction.PrediccionIncompleta;
import cl.zpricing.avant.model.prediction.PrediccionParametros;
import cl.zpricing.avant.model.prediction.PrediccionPorClase;
import cl.zpricing.avant.model.prediction.PrediccionPorDia;
import cl.zpricing.avant.model.prediction.PrediccionPorFuncion;

/**
 * <b>Descripci�n de la Clase</b> Dao para manejar las predicciones
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 09-02-2009 Oliver Cordero: versi�n inicial.</li>
 * <li>1.1 02-06-2009 Oliver Mario Lavandero: Agregado metodo para obtener
 * predicciones por dia.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public interface PrediccionDao {

	/**
	 * Obtiene la prediccion dado un id.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param id
	 *            identificador entero
	 * @return objeto Prediccion
	 * @since 1.0
	 */
	public Prediccion obtenerPrediccion(int id);

	/**
	 * Obtiene una prediccion por dia dado el id de esta.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 02-06-2009 Mario Lavandero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param id
	 * @return
	 * @since 2.0
	 */
	public PrediccionPorDia obtenerPrediccionPorDia(int id);

	/**
	 * Agrega un objeto Prediccion a la capa de datos
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 *            Objeto Prediccion
	 * @return id de la prediccion el la base de datos
	 * @since 1.0
	 */
	public int agregarPrediccion(Prediccion prediccion);

	/**
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 08-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 *            por dia a agregar
	 * @since 1.0
	 */
	public int agregarPrediccionPorDia(PrediccionPorDia prediccion);

	/**
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 08-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 *            por funcion a agregar
	 * @since 1.0
	 */
	public int agregarPrediccionPorFuncion(PrediccionPorFuncion prediccion);

	/**
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 08-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 *            por clase a agregar
	 * @since 1.0
	 */
	public int agregarPrediccionporClase(PrediccionPorClase prediccion);

	/**
	 * Actualiza objeto Prediccion en la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 *            objeto Prediccion
	 * @return void
	 * @since 1.0
	 */
	public void actualizarPrediccion(Prediccion prediccion);

	/**
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 08-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 *            por dia a actualizar
	 * @since 1.0
	 */
	public void actualizarPrediccionPorDia(PrediccionPorDia prediccion);

	/**
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 08-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 * @since 1.0
	 */
	public void actualizarPrediccionPorFuncion(PrediccionPorFuncion prediccion);

	/**
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 08-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 *            a actualizar
	 * @since 1.0
	 */
	public void actualizarPrediccionPorClase(PrediccionPorClase prediccion);

	/**
	 * Elimina objeto Prediccion de la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 *            Objeto Prediccion
	 * @return void
	 * @since 1.0
	 */
	public void eliminarPrediccion(Prediccion prediccion);

	/**
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 08-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 * @since 1.0
	 */
	public void eliminarPrediccionPorDia(PrediccionPorDia prediccion);

	/**
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 08-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 *            por funcion a eliminar
	 * @since 1.0
	 */
	public void eliminarPrediccionPorFuncion(PrediccionPorFuncion prediccion);

	/**
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 08-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 *            por clase a eliminar
	 * @since 1.0
	 */
	public void eliminarPrediccionPorClase(PrediccionPorClase prediccion);

	/**
	 * Obtiene la lista de objetos Prediccion desde la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param estado
	 *            String de estado
	 * @return ArrayList<Prediccion> Lista de objetos Prediccion
	 * @since 1.0
	 */
	public ArrayList<Prediccion> obtenerListaPredicciones(String estado);

	/**
	 * Obtiene una lista de objetos Prediccion que fueron realizadas por un
	 * usuario.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 15-05-2009 Mario Lavandero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param usuario
	 * @return lista de Predicciones de usuario
	 * @since 2.0
	 */
	public ArrayList<Prediccion> obtenerListaPrediccionesUsuario(Usuario usuario);

	/**
	 * Obtiene una lista de objetos Prediccion que son de un usuario y que
	 * tienen todas las Predicciones por Funcion cargadas.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 15-05-2009 Mario Lavandero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param usuario
	 * @return lista de Predicciones de usuario cargadas
	 * @since 2.0
	 */
	public ArrayList<PrediccionIncompleta> obtenerListaPrediccionesUsuarioCargadas(
			Usuario usuario);

	/**
	 * Obtiene una lista de objetos Prediccion que son de un usuario y que
	 * tienen todas las Predicciones por Funcion cargadas, pero solo obtiene
	 * cierto numero de elementos, dados por el numero de numeroResultados y la
	 * pagina.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 14-12-2009 Mario Lavandero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param usuario
	 * @param pagina
	 * @param numeroResultados
	 * @return ArrayList<PrediccionIncompleta>
	 * @since 3.0
	 */
	public ArrayList<PrediccionIncompleta> obtenerListaPrediccionesUsuarioCargadas(
			Usuario usuario, int pagina, int numeroResultados);

	/**
	 * Otiene una lista de objetos Prediccion que han sido cargadas. No
	 * discrimina el usuario que las realizo y muestra cierta pagina con cierto
	 * numero de resultados.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 14-12-2009 Mario Lavandero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param pagina
	 * @param numeroResultados
	 * @return
	 * @since 3.0
	 */
	public ArrayList<PrediccionIncompleta> obtenerListaPrediccionesCargadas(
			int pagina, int numeroResultados);

	/**
	 * Obtiene la lista de objetos Prediccion desde la capa de datos que no
	 * tienen prediccion por funcion asociada a un usuario.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 09/02/2009 Oliver Cordero: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param idUsuario
	 * @return lista de predicciones que no tienen prediccion por Funcion
	 */
	public List<PrediccionIncompleta> obtenerPrediccionSinPrediccionPorFuncion(
			int idUsuario);

	/**
	 * Obtiene la lista de objetos Prediccion desde la capa de datos que no
	 * tienen prediccion por Clase asociada a un usuario.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 09/02/2009 Oliver Cordero: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param idUsuario
	 * @return lista de predicciones que no tienen prediccion por Clase
	 */
	public List<PrediccionIncompleta> obtenerPrediccionSinPrediccionPorClase(
			int idUsuario);

	/**
	 * Obtiene la ultima Prediccion asociada a una pelicula, un complejo y un
	 * usuario.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 05/02/2009 Oliver Corder: Versi�n Inicial</li>
	 * <li>1.1 16/02/2009 Oliver Corder: A�adido complejo</li>
	 * </ul>
	 * </P>
	 * 
	 * @param map
	 *            con el idPelicula, idComplejo e idUsuario que se estan
	 *            buscando
	 * @return Prediccion Lista de objetos Prediccion
	 * @since 1.0
	 */
	public Prediccion obtenerUltimaPrediccion(Map<String, Object> parametros);
	
	/**
	 * Descripci�n de M�todo.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 05-01-2010 Roberto Vargas: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param parametros
	 * @return
	 * @since 3.0
	 */
	public Prediccion obtenerUltimaPrediccionCualquierComplejo(Map<String, Object> parametros);

	/**
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 07-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return lista de predicciones
	 * @since 1.0
	 */
	public ArrayList<Prediccion> obtenerPredicciones();

	/**
	 * 
	 * Obtiene los parámetros asociados a una predicción, i.e., la
	 * ponderación que corresponde a cada película.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 22-12-2009 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param idPrediccion
	 * @return
	 * @since 3.0
	 */
	public List<PrediccionParametros> obtenerParametrosPrediccion(
			int idPrediccion);

	/**
	 * 
	 * Agrega una fila en la tabla Prediccion_Parametros, correspondiente a una
	 * película con su ponderación para una cierta predicción.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 28-12-2009 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccionParametros
	 * @since 3.0
	 */
	public void agregarPrediccionParametros(
			PrediccionParametros prediccionParametros);

	/**
	 * 
	 * Actualiza la ponderación usada en una ponderación para una película en
	 * particular.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 28-12-2009 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccionParametros
	 * @since 3.0
	 */
	public void actualizarPrediccionParametros(
			PrediccionParametros prediccionParametros);

	/**
	 * 
	 * Obtiene todas las predicciones que ya han sido calculadas (i.e., se
	 * llegó -en el flujo de la aplicación- a predicciones.htm) pero que
	 * todavía no han sido cargadas, para el usuario actual.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 28-12-2009 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param idUsuario
	 * @return
	 * @since 3.0
	 */
	public List<PrediccionIncompleta> obtenerPrediccionNoCargada(int idUsuario);

	/**
	 * 
	 * Obtiene una fila con un solo número que corresponde a la cantidad de
	 * días para los cuales se hizo una predicción en particular.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 28-12-2009 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param idPrediccion
	 * @return
	 * @since 3.0
	 */
	public Integer obtenerCantidadDiasPrediccion(int idPrediccion);

	/**
	 * 
	 * Elimina una fila de la tabla Prediccion_Parametros (i.e., borra una de
	 * las películas usadas en una predicción).
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 28-12-2009 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccionParametros
	 *            El objeto que representa la fila a eliminar
	 * @since 3.0
	 */
	public void eliminarPrediccionParametros(
			PrediccionParametros prediccionParametros);

	public Date obtenerFechaDesdePrediccion(Prediccion prediccion);

	/**
	 * Descripci�n de M�todo.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 06-01-2010 Roberto Vargas: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param parametros
	 * @return
	 * @since 3.0
	 */
	public List<PrediccionParametros> obtenerPonderacionesPeliculas(Map<String, Object> parametros);
	
	public double obtenerPonderacion(Map<String, Object> parametros);
	
	public HashMap<String, Double> obtenerPonderaciones(int prediccion_id);
	
	public PrediccionPorFuncion obtenerUltimaPrediccionPorFuncion(Funcion funcion);
}
