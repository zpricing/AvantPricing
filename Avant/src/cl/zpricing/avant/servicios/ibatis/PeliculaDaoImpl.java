package cl.zpricing.avant.servicios.ibatis;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

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
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.commons.exceptions.DaoException;
/**
 * Implementacion DAO para iBatis para el objeto Pelicula
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 17/12/2008 Daniel Estevez Garay: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class PeliculaDaoImpl extends SqlMapClientDaoSupport implements PeliculaDao {
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	public void actualizarPelicula(Pelicula Pelicula) {
		getSqlMapClientTemplate().update("actualizarPelicula", Pelicula);
	}
	
	@Override
	public void actualizarClasificacionPelicula(Pelicula pelicula){
		getSqlMapClientTemplate().update("actualizarClasificacionPelicula", pelicula);
	}
	
	@Override
	public Pelicula obtenerPelicula(int id) {
		return (Pelicula) getSqlMapClientTemplate().queryForObject("obtenerPelicula", id);
	}
	
	@Override
	public Pelicula obtenerPorIdExternoDeComplejo(Complejo complejo, String idExterno) throws DaoException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complejo_id", complejo.getId());
		params.put("id_externo", idExterno);
		
		Pelicula pelicula = (Pelicula) getSqlMapClientTemplate().queryForObject("obtenerPeliculaPorIdExternoDeComplejo", params);
		if (pelicula == null) {
			throw new DaoException("Pelicula [" + idExterno + "] no encontrada.");
		}
		
		return pelicula;
	}
	
	@Override
	public String obtenerIdExternoComplejo(Complejo complejo, Pelicula pelicula) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complejo_id", complejo.getId());
		params.put("pelicula_id", pelicula.getId());
		
		return (String) getSqlMapClientTemplate().queryForObject("obtenerIdExternoComplejo", params);
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PeliculaDao#agregarPelicula(cl.zpricing.revman.model.Pelicula)
	 */
	public void agregarPelicula(Pelicula pelicula) {
		int id = (Integer) getSqlMapClientTemplate().insert("agregarPelicula", pelicula);
		pelicula.setId(id);
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PeliculaDao#eliminarPelicula(cl.zpricing.revman.model.Pelicula)
	 */
	public void eliminarPelicula(Pelicula Pelicula) {
		getSqlMapClientTemplate().delete("eliminarPelicula", Pelicula);
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PeliculaDao#obtenerListaPeliculas()
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Pelicula> obtenerListaPeliculas() {
		
		return (ArrayList<Pelicula>) getSqlMapClientTemplate().queryForList("obtenerListaPeliculas");

	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PeliculaDao#obtenerListaPeliculas(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Pelicula> obtenerListaPeliculas(int pagina) {
		int maxResults = 15;
		return (ArrayList<Pelicula>) getSqlMapClientTemplate().queryForList("obtenerListaPeliculas", (pagina-1)*maxResults, maxResults);
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PeliculaDao#obtenerListaPeliculasActivas()
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Pelicula> obtenerListaPeliculasActivas() {
		
		return (ArrayList<Pelicula>) getSqlMapClientTemplate().queryForList("obtenerListaPeliculasActivas");
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PeliculaDao#obtenerListaPeliculasNoActivas()
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Pelicula> obtenerListaPeliculasNoActivas() {
		
		return (ArrayList<Pelicula>) getSqlMapClientTemplate().queryForList("obtenerListaPeliculasNoActivas");
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Pelicula> obtenerListaPeliculas(int pagina, String search, boolean unclassified, boolean scheduled) {
		int maxResults = 15;
		Map<String, String> param= new HashMap<String, String>();
		param.put("search", search);
		if(unclassified && scheduled) {
			return (ArrayList<Pelicula>) getSqlMapClientTemplate().queryForList("obtenerListaPeliculasSinClasificarEnCartelera", param, (pagina-1)*maxResults, maxResults);
		}
		else if(unclassified && !scheduled) {
			return (ArrayList<Pelicula>) getSqlMapClientTemplate().queryForList("obtenerListaPeliculasSinClasificar", param, (pagina-1)*maxResults, maxResults);
		}
		else if(!unclassified && scheduled) {
			return (ArrayList<Pelicula>) getSqlMapClientTemplate().queryForList("obtenerListaPeliculasEnCartelera", param, (pagina-1)*maxResults, maxResults);
		}
		else {
			return (ArrayList<Pelicula>) getSqlMapClientTemplate().queryForList("obtenerListaPeliculasTodas", param, (pagina-1)*maxResults, maxResults);
		}
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PeliculaDao#agregaRelacionCategoria(cl.zpricing.revman.model.Pelicula, cl.zpricing.revman.model.Categoria)
	 */
	public void agregaRelacionCategoria(Pelicula pelicula, Categoria object) {
		Map<String, Integer> param= new HashMap<String, Integer>(2);
		param.put("id", pelicula.getId());
		param.put("idObjeto", object.getId());
		getSqlMapClientTemplate().insert("agregaRelacionCategoria", param);
	}	
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PeliculaDao#agregaRelacionPublico(cl.zpricing.revman.model.Pelicula, cl.zpricing.revman.model.Publico)
	 */
	public void agregaRelacionPublico(Pelicula pelicula, Publico object){
		Map<String, Integer> param= new HashMap<String, Integer>(2);
		param.put("id", pelicula.getId());
		param.put("idObjeto", object.getId());
		getSqlMapClientTemplate().insert("agregaRelacionPublico", param);
		
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PeliculaDao#agregaRelacionEpoca(cl.zpricing.revman.model.Pelicula, cl.zpricing.revman.model.Epoca)
	 */
	public void agregaRelacionEpoca(Pelicula pelicula, Epoca object){
		Map<String, Integer> param= new HashMap<String, Integer>(2);
		param.put("id", pelicula.getId());
		param.put("idObjeto", object.getId());
		getSqlMapClientTemplate().insert("agregaRelacionEpoca", param);
		
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PeliculaDao#eliminaRelaciones(cl.zpricing.revman.model.Pelicula)
	 */
	@Override
	public void eliminaRelaciones(Pelicula pelicula) {
		getSqlMapClientTemplate().delete("eliminarPeliculaEpoca", pelicula);
		getSqlMapClientTemplate().delete("eliminarPeliculaCategoria", pelicula);
		getSqlMapClientTemplate().delete("eliminarPeliculaPublico", pelicula);
		
	}
	
	@Override
	public void eliminaRelacionesCategoria(Pelicula pelicula) {
		getSqlMapClientTemplate().delete("eliminarPeliculaCategoria", pelicula);
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PeliculaDao#buscarPeliculas(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Pelicula> buscarPeliculas(String busqueda) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("busqueda", busqueda+"%");
		return (ArrayList<Pelicula>) getSqlMapClientTemplate().queryForList("buscarPeliculas", param);
	}
	
	@Override
	public ArrayList<Ranking> obtenerRankings() {
		return (ArrayList<Ranking>) getSqlMapClientTemplate().queryForList("obtenerRankings");
	}
	
	@Override
	public ArrayList<Rating> obtenerRatings(){
		return (ArrayList<Rating>) getSqlMapClientTemplate().queryForList("obtenerRatings");
	}
	
	@Override
	public ArrayList<Idioma> obtenerIdiomas(){
		return (ArrayList<Idioma>) getSqlMapClientTemplate().queryForList("obtenerIdiomas");
	}
	
	@Override
	public ArrayList<Formato> obtenerFormatos(){
		return (ArrayList<Formato>) getSqlMapClientTemplate().queryForList("obtenerFormatos");
	}

	@Override
	public List<Pelicula> obtenerPeliculasFuturasSinRestriccion() {
		return (List<Pelicula>) getSqlMapClientTemplate().queryForList("obtenerPeliculasEnCarteleraSinRestriccion");
	}
}
