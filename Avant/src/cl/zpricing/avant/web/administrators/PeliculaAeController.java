package cl.zpricing.avant.web.administrators;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Categoria;
import cl.zpricing.avant.model.Epoca;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.Publico;
import cl.zpricing.avant.servicios.CategoriaDao;
import cl.zpricing.avant.servicios.EpocaDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.servicios.PublicoDao;
import cl.zpricing.avant.web.form.PeliculasForm;

/**
 * Clase controladora de la vista agregarpelicula
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 22-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class PeliculaAeController extends SimpleFormController {
	/**
	 * Impresión de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private CategoriaDao categoriaDao;
	private EpocaDao epocaDao;
	private PublicoDao publicoDao;
	private PeliculaDao peliculaDao;

	/**
	 * @return the categoriaDao
	 */
	public CategoriaDao getCategoriaDao() {
		return categoriaDao;
	}

	/**
	 * @param categoriaDao
	 *            the categoriaDao to set
	 */
	public void setCategoriaDao(CategoriaDao categoriaDao) {
		this.categoriaDao = categoriaDao;
	}

	/**
	 * @return the epocaDao
	 */
	public EpocaDao getEpocaDao() {
		return epocaDao;
	}

	/**
	 * @param epocaDao
	 *            the epocaDao to set
	 */
	public void setEpocaDao(EpocaDao epocaDao) {
		this.epocaDao = epocaDao;
	}

	/**
	 * @return the publicoDao
	 */
	public PublicoDao getPublicoDao() {
		return publicoDao;
	}

	/**
	 * @param publicoDao
	 *            the publicoDao to set
	 */
	public void setPublicoDao(PublicoDao publicoDao) {
		this.publicoDao = publicoDao;
	}

	/**
	 * @return the peliculaDao
	 */
	public PeliculaDao getPeliculaDao() {
		return peliculaDao;
	}

	/**
	 * @param peliculaDao
	 *            the peliculaDao to set
	 */
	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}

	/**
	 * 
	 * M�todo responsable de la vista agregarpelicula al hacer submit en su form
	 * asociado.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 22-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 *            Solicitud HTTP
	 * @param response
	 *            Respuesta HTTP
	 * @param command
	 *            Objeto recibido por el form asociado a la vista
	 * @param errors
	 *            Errores
	 * @return Modelo y vista para agregarfuncion
	 * @throws Exception
	 * @since 1.0
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		PeliculasForm form = (PeliculasForm) command;

		Pelicula pelicula = new Pelicula();
		pelicula.setNombre(form.getNombre());
		pelicula.setDescripcion(form.getDescripcion());
		pelicula.setDuracion(Integer.parseInt(form.getDuracion()));
		pelicula.setIdExterno(form.getIdExterno());
		peliculaDao.agregarPelicula(pelicula);
		pelicula.setId(pelicula.getId());
		pelicula.setActivo(form.getActivo());
		pelicula.setGradoSimilitud(form.getGradoSimilitud());
		pelicula.setIdCentral(form.getIdCentral());
		pelicula.setNombreCentral(form.getNombreCentral());

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

		mv.addObject("testnot", true);
		mv.addObject("notificacion", "peliculas.notificacion.agregar");
		return mv;

	}

	/**
	 * 
	 * Objeto responsable de crear el form asociado a la vista agregarpelicula.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 22-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 *            Solicitud HTTP.
	 * @return objeto PeliculasForm
	 * @throws ServletException
	 * @since 1.0
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		log.debug("Iniciando formBackingObject...");
		PeliculasForm peliculasForm = new PeliculasForm();
		peliculasForm.setId("0");
		return peliculasForm;
	}

	/**
	 * 
	 * Objeto responsable de establecer las variables globales en la vista
	 * agregarpelicula.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 22-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 *            Solicitud HTTP
	 * @return Map de variables
	 * @throws Exception
	 * @since 1.0
	 */
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		Map<String, Object> mapPeliculas = new HashMap<String, Object>(4);
		mapPeliculas.put("categorias", categoriaDao.obtenerCategorias());
		mapPeliculas.put("epocas", epocaDao.obtenerEpocas());
		mapPeliculas.put("publicos", publicoDao.obtenerPublicos());
		mapPeliculas.put("peliculas", peliculaDao.obtenerListaPeliculas());
		return mapPeliculas;
	}

}
