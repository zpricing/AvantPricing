package cl.zpricing.avant.servicios;

import java.util.ArrayList;
import java.util.List;

import cl.zpricing.avant.model.Categoria;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Epoca;
import cl.zpricing.avant.model.Formato;
import cl.zpricing.avant.model.GrupoPelicula;
import cl.zpricing.avant.model.Idioma;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.Publico;
import cl.zpricing.avant.model.Ranking;
import cl.zpricing.avant.model.Rating;
import cl.zpricing.commons.exceptions.DaoException;
/**
 * 
 * <b>DAO para el manejo de peliculas</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 18-02-2009 Daniel Estevez Garay: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZethaPricing.</B>
 * <P>
 */
public interface PeliculaDao {
    public Pelicula obtenerPelicula(int id);
    
    public Pelicula obtenerPorIdExternoDeComplejo(Complejo complejo, String idExterno) throws DaoException;
    
    public String obtenerIdExternoComplejo(Complejo complejo, Pelicula pelicula);
    
    public void agregarPelicula(Pelicula pelicula);

    public void actualizarPelicula(Pelicula pelicula);
    
    public void actualizarClasificacionPelicula(Pelicula pelicula);

    /**
	 * Elimina una pelicula de la capa de datos
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Daniel Estevez Garay: Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param pelicula a eliminar
	 * @since 1.0
	 */
    public void eliminarPelicula(Pelicula pelicula);

    /**
	 * Obtiene todas las peliculas de la capa de datos
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return lista de peliculas
	 * @since 1.0
	 */
    public ArrayList<Pelicula> obtenerListaPeliculas();
    
    /**
     * Obtiene la lista de peliculas, pero solo el grupo designado por la pagina.
     *
     * <P>
     * Registro de versiones:
     * <ul>
     *   <li> 1.0 27-01-2009 MARIO : Version Inicial</li>
     * </ul>
     * </P>
     * 
     * @param pagina
     * @return
     * @since 1.0
     */
    public ArrayList<Pelicula> obtenerListaPeliculas(int pagina);
    
    /**
	 * Obtiene lista de Pelicula que estan activas de la capa de datos
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Mario Lavandero Soto: Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return ArrayList<Pelicula>
	 * @since 1.0
	 */
    public ArrayList<Pelicula> obtenerListaPeliculasActivas();
    
    /**
	 * Obtiene lista de Pelicula que no estan activas de la capa de datos
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Mario Lavandero Soto: Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return ArrayList<Pelicula>
	 * @since 1.0
	 */
    public ArrayList<Pelicula> obtenerListaPeliculasNoActivas();
    
    public ArrayList<Pelicula> obtenerListaPeliculas(int pagina, String search, boolean unclassified, boolean scheduled);
    
    /**
     * 
     * Agrega una categor�a a una pel�cula.
     *
     * <P>
     * Registro de versiones:
     * <ul>
     *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
     * </ul>
     * </P>
     * 
     * @param pelicula pel�cula a la cual se le agregar� la categor�a
     * @param object categor�a a agregar
     * @since 1.0
     */
    public void agregaRelacionCategoria(Pelicula pelicula, Categoria object);
    
	/**
	 * 
	 * Agrega un tipo de p�blico a una pel�cula.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param pelicula pel�cula a la cual se le va a agregar el tipo de p�blico
	 * @param object tipo de p�blico a agregar
	 * @since 1.0
	 */
	public void agregaRelacionPublico(Pelicula pelicula, Publico object);
	
	/**
	 * 
	 * Agrega una �poca a una pel�cula.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param pelicula pelicula a la cual se le va a agregar la �poca
	 * @param object tipo de p�blico a agregar
	 * @since 1.0
	 */
	public void agregaRelacionEpoca(Pelicula pelicula, Epoca object);
	
	/**
	 * 
	 * Elimina todas las relaciones que tiene una pelicula con categoria, publico y epoca.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param pelicula pel�cula a la cual se le eliminar�n las categor�as, �pocas y tipos de p�blico
	 * @since 1.0
	 */
	public void eliminaRelaciones(Pelicula pelicula);
	
	public void eliminaRelacionesCategoria(Pelicula pelicula);
	
	/**
	 * 
	 * Busca todas las peliculas cuyo nombre comience con determinado texto
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 16-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param busqueda texto a buscar
	 * @return
	 * @since 1.0
	 */
	public ArrayList<Pelicula> buscarPeliculas(String busqueda);
	
	public ArrayList<Ranking> obtenerRankings();
	
	public ArrayList<Rating> obtenerRatings();
	
	public ArrayList<Idioma> obtenerIdiomas();
	
	public ArrayList<Formato> obtenerFormatos();
	
	public List<Pelicula> obtenerPeliculasFuturasSinRestriccion();
}


