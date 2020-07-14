package cl.zpricing.avant.web.restriccion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.servicios.RestrictionDao;

public class EliminarRestriccionPeliculaController implements Controller {
	
	private RestrictionDao restrictionDao;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Integer restriccionId = Integer.parseInt(request.getParameter("restriccion_id"));
		Integer peliculaId = Integer.parseInt(request.getParameter("pelicula_id"));
		
		int result = restrictionDao.eliminarRestriccionPelicula(peliculaId, restriccionId);
		
		ModelAndView mv = new ModelAndView(new RedirectView("admin_ver_restricciones_pelicula.htm"));
		
		return mv;
	}

	public void setRestrictionDao(RestrictionDao restrictionDao) {
		this.restrictionDao = restrictionDao;
	}
}
