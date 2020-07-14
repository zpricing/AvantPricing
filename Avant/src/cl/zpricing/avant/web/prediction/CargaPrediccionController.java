package cl.zpricing.avant.web.prediction;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.servicios.PrediccionDao;

public class CargaPrediccionController implements Controller {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private PrediccionDao prediccionDao;
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mvc = new ModelAndView(new RedirectView("predicciones.htm"));
		String prediccionId = request.getParameter("prediccion_id");
		
		log.debug("Prediccion a cargar : [" + prediccionId + "]");
		if (prediccionId != null) {
			Prediccion prediccion = prediccionDao.obtenerPrediccion(Integer.parseInt(prediccionId));

			CopyOnWriteArrayList<Prediccion> predicciones = (CopyOnWriteArrayList<Prediccion>) request.getSession().getAttribute("predicciones");
			
			if (predicciones == null) {
				predicciones = new CopyOnWriteArrayList<Prediccion>();
			}
			
			predicciones.add(prediccion);
			request.getSession().setAttribute("predicciones", predicciones);
		}
		
		return mvc;
	}
	
	public void setPrediccionDao(PrediccionDao prediccionDao) {
		this.prediccionDao = prediccionDao;
	}
}
