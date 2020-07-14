package cl.zpricing.avant.web.restriccion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.restriccion.Restriction;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.servicios.RestrictionDao;
import cl.zpricing.avant.web.form.RestriccionAsignarPeliculasForm;
import cl.zpricing.commons.utils.DateUtils;

public class AsignarRestriccionesPeliculaController extends SimpleFormController {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private PeliculaDao peliculaDao;
	private RestrictionDao restrictionDao;
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		RestriccionAsignarPeliculasForm form = new RestriccionAsignarPeliculasForm();
		return form;
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		List<Restriction> listaRestricciones = restrictionDao.obtenerTodos(new Restriction());
		List<Pelicula> listaPeliculasEnCartelera = peliculaDao.obtenerPeliculasFuturasSinRestriccion();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("listaRestricciones", listaRestricciones);
		map.put("listaPeliculas", listaPeliculasEnCartelera);
		
		return map;
	}
	
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		RestriccionAsignarPeliculasForm form = (RestriccionAsignarPeliculasForm) command;
		
		if (form.getPeliculas() != null) {
			log.debug("Cantidad de peliculas: " + form.getPeliculas().size());
		}
		else {
			log.debug("peliculas es null");
		}
		
		
		if (form.getRestriccion() != null) {
			log.debug("Restriccion: " + form.getRestriccion());
		}
		else {
			log.debug("restriction es null");
		}
		
		log.debug("Date : " + form.getFecha());
		
		for (Integer peliculaId : form.getPeliculas()) {
			restrictionDao.asignarRestriccionPelicula(peliculaId, form.getRestriccion(), DateUtils.format_ddMMyyyy.parse(form.getFecha()));
		}
		
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}

	public void setRestrictionDao(RestrictionDao restrictionDao) {
		this.restrictionDao = restrictionDao;
	}
}
