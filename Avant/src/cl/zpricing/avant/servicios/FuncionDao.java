package cl.zpricing.avant.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.zpricing.avant.etl.model.Session;
import cl.zpricing.avant.model.Area;
import cl.zpricing.avant.model.Asistencia;
import cl.zpricing.avant.model.AsistenciaDiaria;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.FuncionArea;
import cl.zpricing.avant.model.FuncionAreaProxy;
import cl.zpricing.avant.model.LastMinuteSession;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.Precio;
import cl.zpricing.avant.model.Sala;
/**
 * 
 * <b>DAO para el manejo de las funciones</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 18-02-2009 Daniel Estevez Garay: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public interface FuncionDao {

	/**
	 * Obtiene una funcion de determinado identificador desde la capa de datos.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 17/12/2008 Daniel Estevez Garay: Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param id identificador de la funcion
	 * @return funcion
	 * @since 1.0
	 */
    public Funcion obtenerFuncion(int id);

    /**
	 * Agrega objeto Funcion a la capa de datos.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param funcion funcion a agregar
	 * @since 1.0
	 */
    public void agregarFuncion(Funcion funcion);

    /**
	 * Actualiza una funcion en la capa de datos.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param funcion funci�n a actualizar
	 * @since 1.0
	 */
    public void actualizarFuncion(Funcion funcion);

    /**
	 * Elimina objeto Funcion de la capa de datos
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param funcion funci�n a eliminar
	 * @since 1.0
	 */
    public void eliminarFuncion(Funcion funcion);
    
    /**
	 * Elimina funcion de la capa de datos mediante su identificador.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 22-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param id identificador de la funcion a eliminar 
	 * @since 1.0
	 */
    public void eliminarFuncionId(int id);

    /**
     * 
     * Obtiene una lista de funciones desde la capa de datos de una pelicula 
	 * en un rango de fechas.
     *
     * <P>
     * Registro de versiones:
     * <ul>
     *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
     * </ul>
     * </P>
     * 
     * @param fecha_inicio fecha inicio del rango de fechas
     * @param fecha_fin fecha termino del rango de fechas
     * @param pelicula pelicula de la cual se desean sus funciones
     * @return lista de funciones
     * @since 1.0
     */
    public ArrayList<Funcion> obtenerListaFunciones(GregorianCalendar fecha_inicio, GregorianCalendar fecha_fin, Pelicula pelicula);

    /**
	 * Actualiza las asistencias de una funcion.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param funcion funcion a ser actualizada
	 * @since 1.0
	 */
    public void actualizarAsistencia(Funcion funcion);
    
   /**
    * 
    * Elimina una asistencia de la capa de datos.
    *
    * <P>
    * Registro de versiones:
    * <ul>
    *   <li> 1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
    * </ul>
    * </P>
    * 
    * @param asistencia asistencia a eliminar
    * @since 1.0
    */
    public void eliminarAsistencia (Asistencia asistencia);
    
   /**
    * 
    * Obtiene una asistencia desde la capa de datos.
    *
    * <P>
    * Registro de versiones:
    * <ul>
    *   <li> 1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
    * </ul>
    * </P>
    * 
    * @param id identificador de la asistencia
    * @return asistencia
    * @since 1.0
    */
    public Asistencia obtenerAsistencia(int id);
    
    /**
     * 
     * Obtiene las funciones existentes en la capa de datos dentro de un rango de fechas.
     *
     * <P>
     * Registro de versiones:
     * <ul>
     *   <li> 1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
     * </ul>
     * </P>
     * 
     * @param fecha_inicio fecha inicio del rango
     * @param fecha_termino fecha termino del rango
     * @return lista de funciones
     * @since 1.0
     */
    public ArrayList<Funcion> obtenerFunciones(GregorianCalendar fecha_inicio, GregorianCalendar fecha_termino);
    
    public List<Funcion> obtenerFunciones(Complejo complejo, Pelicula pelicula, Date fecha);
    
    
    /**
     * 
     * Obtiene las funciones existentes en la capa de datos dentro de un rango de fechas
     * de determinado complejo.
     *
     * <P>
     * Registro de versiones:
     * <ul>
     *   <li> 1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
     * </ul>
     * </P>
     * 
     * @param fecha_inicio fecha inicio del rango
     * @param fecha_termino fecha termino del rango
     * @param complejo complejo del cual se obtienen las funciones
     * @return lista de funciones
     * @since 1.0
     */
    public ArrayList<Funcion> obtenerFuncionesComplejo(GregorianCalendar fecha_inicio, GregorianCalendar fecha_termino, Complejo complejo);
    
/**
 * 
 * Obtiene la primera funci�n de una pel�cula en un complejo de determinado d�a de la semana.
 *
 * <P>
 * Registro de versiones:
 * <ul>
 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
 * </ul>
 * </P>
 * 
 * @param pelicula pel�cula de la cual se consulta
 * @param complejo complejo en que se estren� la pelicula
 * @param dia d�a de la semana en que se realiza la funci�n, comprende un rango de 0 a 6, donde 0 es lunes y 6 es domingo.
 * @return lista de funciones
 * @since 1.0
 */
    public Funcion obtenerPrimeraFuncion(Pelicula pelicula, Complejo complejo, int dia);
    
  /**
   * 
   * Obtiene las funciones de una pel�cula en un complejo dentro de un rango de fechas.
   *
   * <P>
   * Registro de versiones:
   * <ul>
   *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
   * </ul>
   * </P>
   * 
   * @param fecha_inicio fecha inicio del rango de fechas
   * @param fecha_fin fecha termino del rango de fechas
   * @param pelicula pelicula por la que se consulta
   * @param complejo complejo en el que tiene funciones la pel�cula
   * @return lista de funciones
   * @since 1.0
   */
    public ArrayList<Funcion> obtenerListaFuncionesComplejo(GregorianCalendar fecha_inicio, GregorianCalendar fecha_fin, Pelicula pelicula, Complejo complejo);
    
    /**
	 * 
	 * Propaga funciones de fecha imagen al rango de fechas comprendido entre fecha_inicio, fecha_fin
	 * para determinada pelicula y complejo.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 08-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param complejo complejo en el que se crean las funciones
	 * @param pelicula pelicula para la cual se propagan las funciones
	 * @param imagen fecha desde la cual se tomaran las funciones a propagar
	 * @param fecha_inicio fecha inicio del rango a propagar
	 * @param fecha_fin fecha termino del rango a propagar
	 * @since 1.0
	 */
	public void Propagar(Complejo complejo, Pelicula pelicula, GregorianCalendar imagen, GregorianCalendar fecha_inicio, GregorianCalendar fecha_fin);
	
	/**
	 * 
	 * Obtiene las asistencias de una funci�on mediante el identificador de esta.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 MARIO: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param funcion_id identificador de la funci�n
	 * @return lista de asistencias
	 * @since 1.0 
	 */
	public ArrayList<Asistencia> obtenerAsistenciasByFuncion(int funcion_id);
	
	/**
	 * 
	 * Obtiene las asistencias de una pelicula en un complejo por d�a para cada hora a partir de su fecha de estreno
	 * en determinado d�a de la semana para cierta cantidad de d�as.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param peliculas pelicula por la cual se consulta
	 * @param complejo complejo en el cual se emite la pelicula
	 * @param dias dias a consultar
	 * @param dia_estreno d�a de la semana en la que se estrena la pelicula, comprende un rango de 0 a 6, donde 0 es lunes y 6 es domingo
	 * @return
	 * @since 1.0
	 */
	@Deprecated
	public HashMap<Integer, Double> obtenerAsistenciasHoraPeliculaComplejo(ArrayList<Pelicula> peliculas, Complejo complejo, int dias, int dia_estreno);
	
	/**
	 * Obtiene las varianzas por hora de las asistencias de una pelicula en un complejo que fue estrenada determinado d�a para cierta 
	 * cantidad de d�as.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 27-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param peliculas pelicula a consultar
	 * @param complejo complejo en el cual se emite la pelicula
	 * @param dias dias a consultar
	 * @param dia_estreno d�a de la semana en la que se estrena la pelicula, comprende un rango de 0 a 6, donde 0 es lunes y 6 es domingo
	 * @return 
	 * @since 1.0
	 */
	@Deprecated
	public HashMap<Integer, Double> obtenerVarianzasHoraComplejo(ArrayList<Pelicula> peliculas, Complejo complejo, int dias, int dia_estreno);
	
	/**
	 * 
	 * Devuelve una lista con las AsistenciaDiaria correspondientes a cada día en los cuales la película identificada por 
	 * peliculaId se ha encontrado o se encontraba en cartelera.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-12-2009 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param peliculaId el identificador de la película de la que se quiere obtener su asistencia
	 * @return un ArrayList con objetos AsistenciaDiaria con la asistencia para la película en cuestión
	 * @since 3.0
	 */
	public ArrayList<AsistenciaDiaria> obtenerAsistenciaDiariaPorPelicula(int peliculaId);
	
	/**
	 * Devuelve una lista con las funciones correspondientes a un complejo y a la fecha contable de la función.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-12-2009 Nicolás Dujovne W.: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param complejo Objeto de complejo.
	 * @param fechaContable Fecha en la cual se contabiliza la funcion.
	 * @return un ArrayList con objetos AsistenciaDiaria con la asistencia para la película en cuestión
	 * @since 3.1
	 */
	public List<Funcion> obtenerFunciones(Complejo complejo, Date fechaContable, boolean soloFuncionesNoCargadas);

	public List<Funcion> obtenerFunciones(Complejo complejo, Date fechaContable);

	public List<Funcion> obtenerFuncionesParaForecast(Complejo complejo, Date fechaContable, boolean soloFuncionesNuevas);

	public List<Funcion> obtenerFuncionesSinAnalizar(Complejo complejo, Date fechaContable);

	public Map<String,Integer> ObtenerSemanaExhibicion(Funcion funcion);

	public List<Funcion> obtenerFunciones(Sala sala, Date fecha);

	public void actualizaFuncionArea(Funcion funcion, FuncionArea funcionArea);

	public FuncionArea obtenerFuncionArea(Funcion funcion, Area area);

	public Precio obtenerPrecioFuncionArea(Funcion funcion, Area area);

	public List<FuncionArea> obtenerFuncionesArea(Funcion funcion);

	public List<FuncionArea> obtenerFuncionesAreaConTicketId(Funcion funcion);

	public Funcion obtenerFuncionPorIdExterno(Complejo complejo, String idExterno);

	public boolean actualizarFuncionAreaCuposOcupados(Funcion funcion, Area area, Integer ocupados);

	public int copiaMascaraAFuncionesFuturas(Funcion funcion);

	public void actualizaMascara(Funcion funcion);
	
	public void actualizaMascaraYBloquear(int funcionId, int mascaraId);

	public void actualizarAsistenciaProyectada(Funcion funcion);

	public void actualizarAsistenciaProyectadaSinMascara(Funcion funcion);

	public void actualizarAnalizada(Funcion funcion);

	public void actualizarBloqueada(Funcion funcion);

	public void actualizarFuncionesUpselling(int preTime, int postTime);

	public void actualizarCuposUpselling();

	public void actualizarFuncionesSecondSelling();

	public void actualizarFuncionesLastMinuteSelling(int maxFuncionesDiarias, int maxPorcentajeOcupacionProyectado, int minDiasCartelera);
	
	public List<LastMinuteSession> obtenerFuncionesLastMinuteSelling(int complejoId);

	public void actualizarSemanaExhibicion(Funcion funcion);
	
	public List<Session> obtenerFuncionesArea();

	public void setCuposCargados(int funcionId, boolean estado);

	public void setCuposCargadosFuncionesFuturas();
	
	public void actualizarFuncionesSecondSellingClientSuggestions();
}


