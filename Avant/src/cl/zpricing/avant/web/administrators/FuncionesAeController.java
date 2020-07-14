/**
 * 
 */
package cl.zpricing.avant.web.administrators;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.Sala;
import cl.zpricing.avant.model.TipoFuncion;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.servicios.SalaDao;
import cl.zpricing.avant.servicios.TipoFuncionDao;
import cl.zpricing.avant.web.form.FuncionesForm;


/**
 * <b>Controlador vista agregarfuncion</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 29-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class FuncionesAeController extends SimpleFormController {

	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private FuncionDao funcionDao;
	private PeliculaDao peliculaDao;
	private SalaDao salaDao;
	private TipoFuncionDao tipoFuncionDao;

	/**
	 * @return the funcionDao
	 */
	public FuncionDao getFuncionDao() {
		return funcionDao;
	}

	/**
	 * @param funcionDao
	 *            the funcionDao to set
	 */
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
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
	 * @return the salaDao
	 */
	public SalaDao getSalaDao() {
		return salaDao;
	}

	/**
	 * @param salaDao
	 *            the salaDao to set
	 */
	public void setSalaDao(SalaDao salaDao) {
		this.salaDao = salaDao;
	}

	/**
	 * @return the tipoFuncionDao
	 */
	public TipoFuncionDao getTipoFuncionDao() {
		return tipoFuncionDao;
	}

	/**
	 * @param tipoFuncionDao
	 *            the tipoFuncionDao to set
	 */
	public void setTipoFuncionDao(TipoFuncionDao tipoFuncionDao) {
		this.tipoFuncionDao = tipoFuncionDao;
	}

	/**
	 * 
	 * M�todo responsable de la vista agregarfuncion al hacer submit en su form asociado.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 29-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP
	 * @param response Respuesta HTTP
	 * @param command Objeto recibido por el form asociado a la vista
	 * @param errors Errores
	 * @return Modelo y vista para agregarfuncion
	 * @throws Exception
	 * @since 1.0
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		FuncionesForm form = (FuncionesForm) command;
		Funcion funcion = new Funcion();
		Pelicula pelicula = peliculaDao.obtenerPelicula(form.getPelicula());
		Sala sala = salaDao.obtenerSala(form.getSala());
		TipoFuncion tf = tipoFuncionDao.obtenerTipoFuncion(form.getTipofuncion());
		Calendar fecha = new GregorianCalendar(Integer.valueOf(form.getFecha().split("-")[2]),Integer.valueOf(form.getFecha().split("-")[1]),Integer.valueOf(form.getFecha().split("-")[0]),form.getDia(),form.getHora(),form.getMin());
		funcion.setFecha(fecha.getTime());
		funcion.setPeliculaAsociada(pelicula);
		funcion.setSala(sala);
		funcion.setTipoFuncion(tf);
		
		funcionDao.agregarFuncion(funcion);
		ModelAndView mv = showForm(request, response, errors);
		mv.addObject("testnot",true);
		mv.addObject("notificacion", "funciones.notificacion.agregar");
		return mv;
		
	
	}
	/**
	 * 
	 * Objeto responsable de crear el form asociado a la vista agregarfuncion.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 29-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP.
	 * @return objeto FuncionesForm
	 * @throws ServletException 
	 * @since 1.0
	 */	
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		log.debug("Iniciando formBackingObject...");
		FuncionesForm form = new FuncionesForm();
		form.setId(0);
		return form;
	}
	/**
	 * 
	 * Objeto responsable de establecer las variables globales en la vista agregarfuncion.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 29-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP
	 * @return Map de variables
	 * @throws Exception
	 * @since 1.0
	 */	
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>(4);
		Calendar calendar = Calendar.getInstance();
		map.put("salas", salaDao.obtenerTodas());
		map.put("peliculas", peliculaDao.obtenerListaPeliculas());
		map.put("tipofunciones", tipoFuncionDao.obtenerTipoFunciones());
		map.put("year", calendar.get(Calendar.YEAR));
		return map;
	}

}
