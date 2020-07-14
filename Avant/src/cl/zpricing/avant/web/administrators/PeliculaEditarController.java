package cl.zpricing.avant.web.administrators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Categoria;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Epoca;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.Publico;
import cl.zpricing.avant.servicios.CategoriaDao;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.EpocaDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.servicios.PublicoDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.form.PeliculasForm;
/**
 * Clase controladora de la vista editarpelicula
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 22-12-2008 Daniel Estévez Garay: versión inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class PeliculaEditarController extends SimpleFormController {

	private PeliculaDao peliculaDao;
	private PublicoDao publicoDao;
	private EpocaDao epocaDao;
	private CategoriaDao categoriaDao;
	private ComplejoDao complejoDao;
	private FuncionDao funcionDao;
	private ServiciosRevenueManager serviciosRM;
	
	
		
	

	public ServiciosRevenueManager getServiciosRM() {
		return serviciosRM;
	}

	
	public void setServiciosRM(ServiciosRevenueManager serviciosRM) {
		this.serviciosRM = serviciosRM;
	}

	
	public FuncionDao getFuncionDao() {
		return funcionDao;
	}

	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}

	public ComplejoDao getComplejoDao() {
		return complejoDao;
	}

	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
	/**
	 * Impresión de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	public PeliculaDao getPeliculaDao() {
		return peliculaDao;
	}

	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}

	public PublicoDao getPublicoDao() {
		return publicoDao;
	}

	public void setPublicoDao(PublicoDao publicoDao) {
		this.publicoDao = publicoDao;
	}

	public EpocaDao getEpocaDao() {
		return epocaDao;
	}

	
	public void setEpocaDao(EpocaDao epocaDao) {
		this.epocaDao = epocaDao;
	}

	public CategoriaDao getCategoriaDao() {
		return categoriaDao;
	}


	public void setCategoriaDao(CategoriaDao categoriaDao) {
		this.categoriaDao = categoriaDao;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		PeliculasForm form = (PeliculasForm) command;

		Pelicula pelicula = new Pelicula();
		pelicula.setNombre(form.getNombre());
		pelicula.setDescripcion(form.getDescripcion());
		pelicula.setDuracion(Integer.parseInt(form.getDuracion()));
		pelicula.setId(Integer.parseInt(form.getId()));
		pelicula.setActivo(form.getActivo());
		pelicula.setIdExterno(form.getIdExterno());
		pelicula.setGradoSimilitud(form.getGradoSimilitud());
		pelicula.setIdCentral(form.getIdCentral());
		pelicula.setNombreCentral(form.getNombreCentral());
		peliculaDao.actualizarPelicula(pelicula);
		peliculaDao.eliminaRelaciones(pelicula);
		
		String[] categorias = form.getCategorias();
		for (int i = 0; i < categorias.length; i++) {

			Categoria categoria = new Categoria();
			categoria.setId(Integer.parseInt(categorias[i]));
			peliculaDao.agregaRelacionCategoria(pelicula, categoria);
		}
		String[] publicos = form.getPublicos();
		for (int i = 0; i < publicos.length; i++) {
			Publico publico = new Publico();
			log.debug("Publico " + publicos[i]);
			publico.setId(Integer.parseInt(publicos[i]));
			peliculaDao.agregaRelacionPublico(pelicula, publico);
		}

		String[] epocas = form.getEpocas();
		for (int i = 0; i < epocas.length; i++) {
			Epoca epoca = new Epoca();
			log.debug(epocas[i]);
			epoca.setId(Integer.parseInt(epocas[i]));
			peliculaDao.agregaRelacionEpoca(pelicula, epoca);
		}
		ModelAndView mv = showForm(request, response, errors);
		mv.addObject("testnot",true);
		mv.addObject("notificacion", "peliculas.notificacion.editar");
		return mv;

	}
/*
 * (non-Javadoc)
 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
 */
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		log.debug("Iniciando formBackingObject...");
		PeliculasForm peliculasForm = new PeliculasForm();
		String id = request.getParameter("idpelicula");
		Pelicula pelicula = peliculaDao.obtenerPelicula(Integer.parseInt(id));
		peliculasForm.setId(pelicula.getId() + "");
		peliculasForm.setNombre(pelicula.getNombre());
		peliculasForm.setDescripcion(pelicula.getDescripcion());
		peliculasForm.setDuracion(pelicula.getDuracion() + "");
		peliculasForm.setCategorias(Util.toArrayCategoria(pelicula.getCategorias()));
		peliculasForm.setEpocas(Util.toArrayEpoca(pelicula.getEpocas()));
		peliculasForm.setPublicos(Util.toArrayPublico(pelicula.getTiposDePublico()));
		peliculasForm.setActivo(pelicula.getActivo());
		peliculasForm.setIdExterno(pelicula.getIdExterno());
		peliculasForm.setGradoSimilitud(pelicula.getGradoSimilitud());
		peliculasForm.setIdCentral(pelicula.getIdCentral());
		peliculasForm.setNombreCentral(pelicula.getNombreCentral());

		return peliculasForm;
	}
	/*
	 * 	(non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		Map<String, Object> mapPeliculas = new HashMap<String, Object>(3);
		mapPeliculas.put("categorias", categoriaDao.obtenerCategorias());
		mapPeliculas.put("epocas", epocaDao.obtenerEpocas());
		mapPeliculas.put("publicos", publicoDao.obtenerPublicos());
		String id = request.getParameter("idpelicula");
		Pelicula pelicula = peliculaDao.obtenerPelicula(Integer.parseInt(id));
		int dia_estreno = Integer.parseInt(serviciosRM.obtenerParametro("Cine",
		"Dia_Estreno").getCodigo());
		List<Complejo> complejos = complejoDao.complejosTodos();
		Iterator<Complejo> it_com = complejos.iterator();
		ArrayList<Funcion> funciones = new ArrayList<Funcion>();
		while(it_com.hasNext()){
			Complejo complejo = it_com.next();
			Funcion funcion = funcionDao.obtenerPrimeraFuncion(pelicula, complejo, dia_estreno);
			funciones.add(funcion);
		}
		mapPeliculas.put("complejos", complejos);
		mapPeliculas.put("funciones", funciones);
		return mapPeliculas;
	}

}
