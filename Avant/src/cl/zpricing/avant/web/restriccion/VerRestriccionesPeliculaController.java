package cl.zpricing.avant.web.restriccion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.servicios.RestrictionDao;

public class VerRestriccionesPeliculaController implements Controller {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private RestrictionDao restrictionDao;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("restrictions/ver_restricciones_pelicula");
		mv.addObject("peliculasConRestriccion", restrictionDao.obtenerPeliculasConRestriccion());
		return mv;
	}

	public void setRestrictionDao(RestrictionDao restrictionDao) {
		this.restrictionDao = restrictionDao;
	}
}
