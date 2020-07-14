package cl.zpricing.avant.web.prediction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.servicios.PrediccionDao;

public class PrediccionRecargaController implements Controller {
	
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private PrediccionDao prediccionDao;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Iniciando PrediccionRecargaController");
		int prediccionId = Integer.parseInt(request.getParameter("prediccion"));
		
		log.debug("PrediccionId : [" + prediccionId + "]");
		
		Prediccion prediccion = prediccionDao.obtenerPrediccion(prediccionId);
		//TODO reemplazar por recarga de prediccion.

		
		return new ModelAndView(new RedirectView("predicciones.htm"));
	}

	public void setPrediccionDao(PrediccionDao prediccionDao) {
		this.prediccionDao = prediccionDao;
	}
}
