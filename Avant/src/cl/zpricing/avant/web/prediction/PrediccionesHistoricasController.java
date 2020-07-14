package cl.zpricing.avant.web.prediction;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.model.prediction.PrediccionIncompleta;
import cl.zpricing.avant.servicios.PrediccionDao;
import cl.zpricing.avant.servicios.UsuarioDao;

/**
 * <b>Clase controladora para la vista de las predicciones historicas realizadas
 * por un usuario. Las predicciones historicas son predicciones que han sido
 * cargadas al ERP.</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 15-05-2009 Mario Lavandero: version inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class PrediccionesHistoricasController implements Controller {
	private UsuarioDao usuarioDao;
	private PrediccionDao prediccionDao;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Se revisa que pagina se quiere consultar, la primera vez se asume que
		// es la pagina 1.
		int pagina = 1;
		String paramPagina = request.getParameter("page");

		try {
			pagina = Integer.parseInt(paramPagina);
		} catch (Exception e) {

		}

		// Numero de resultado a desplegar
		int maxResults = 15;

		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = new Usuario();
		usuario = usuarioDao.obtenerUsuarioByName(user);

		// Se revisa que tipo de usuario es para ver que informacion ve.
		// Si se es administrador se ven todas las predicciones, sino solo se
		// ven las propias del usuario.
		ArrayList<PrediccionIncompleta> predicciones = null;
		if (usuario.getRol().getRol().equalsIgnoreCase("Administrador")) {
			predicciones = prediccionDao.obtenerListaPrediccionesCargadas(pagina, maxResults);
		} else {
			predicciones = prediccionDao.obtenerListaPrediccionesUsuarioCargadas(usuario, pagina, maxResults);
		}

		ModelAndView mv = new ModelAndView("predictions/prediccionesHistoricas");
		mv.addObject("predicciones", predicciones);
		mv.addObject("pagina", pagina);

		return mv;
	}
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public void setPrediccionDao(PrediccionDao prediccionDao) {
		this.prediccionDao = prediccionDao;
	}
}
