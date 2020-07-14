package cl.zpricing.avant.web.administrators;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.web.MenuController2;
import cl.zpricing.avant.web.form.BusquedaPeliculasForm;

/**
 * Clase controladora de la vista peliculas
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 17-12-2008 Daniel Estévez Garay: versión inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class PeliculaController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	private PeliculaDao peliculaDao;
	private MenuController2 menu;

	@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
		String param = request.getParameter("activo");
		logger.debug("param: " + param);
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(param == null || param.equalsIgnoreCase("si")) {
			map.put("peliculas", peliculaDao.obtenerListaPeliculasActivas());
		}
		else if(param.equalsIgnoreCase("no")) {
			map.put("peliculas", peliculaDao.obtenerListaPeliculasNoActivas());
		}
		else if(param.equalsIgnoreCase("ambos")) {
			int pagina = Integer.parseInt(request.getParameter("page"));
			map.put("peliculas", peliculaDao.obtenerListaPeliculas(pagina));
			map.put("next", pagina+1);
			if(pagina>1)
				map.put("previous", pagina-1);
		}
		menu.getMenu(request,map);	
		return map;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {		
		return new BusquedaPeliculasForm();
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		BusquedaPeliculasForm form = (BusquedaPeliculasForm)command;
		ModelAndView mv = showForm(request, response, errors);
		mv.addObject("peliculas", peliculaDao.buscarPeliculas(form.getBusqueda()));
		return mv;
	}
	
	public Pelicula cambiarEstado(String peliculaId) {
		logger.info("Inicio cambio de estado");
		logger.info("  PeliculaId : [" + peliculaId + "]");
		int id = Integer.parseInt(peliculaId);
		Pelicula pelicula = peliculaDao.obtenerPelicula(id);
		pelicula.setActivo(!pelicula.getActivo());
		peliculaDao.actualizarPelicula(pelicula);
		return pelicula;
	}
	
	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}
	public void setMenu(MenuController2 menu) {
		this.menu = menu;
	}
}