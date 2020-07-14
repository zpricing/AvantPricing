package cl.zpricing.avant.web.prediction;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.servicios.PrediccionDao;
import cl.zpricing.commons.utils.ErroresUtils;

/**
 * <b>Controlador de la vista eliminarprediccion</b>. Este controlador 
 * sirve para eliminar predicciones que se encuentran actualmente
 * cargadas en el atributo de sesión "predicciones", tanto del atributo
 * mismo como de la base de datos.
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 04-02-2009 Daniel Estpevez Garay: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class EliminarPrediccionController implements Controller {
	/**
	 * Impresión de log.
	 */
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private PrediccionDao prediccionDao;

	/**
	 *
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 04-02-2009 Daniel Estévez Garay: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws Exception
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("Iniciando EliminarPrediccionController");
		int id = Integer.parseInt(request.getParameter("idprediccion"));
		String eliminaPrediccionDeBDString = request.getParameter("eliminaPrediccionDeBD");
		boolean eliminaPrediccionDeBD = false;
		
		log.debug("Id 						: [" + id + "]");
		log.debug("eliminaPrediccionDeBD 	: [" + eliminaPrediccionDeBDString + "]");
		
		if (eliminaPrediccionDeBDString != null && !eliminaPrediccionDeBDString.equals("")) {
			eliminaPrediccionDeBD = Boolean.parseBoolean(eliminaPrediccionDeBDString);
		}
		
		if (eliminaPrediccionDeBD) {
			log.info("Eliminando prediccion [" + id + "] de base de datos.");
			Prediccion nueva = new Prediccion();
			nueva.setDescripcion("");
			nueva.setId(id);
			prediccionDao.eliminarPrediccion(nueva);
		}
		
		CopyOnWriteArrayList<Prediccion> predicciones = (CopyOnWriteArrayList<Prediccion>) request.getSession().getAttribute("predicciones");
		boolean noEstabaEnPredicciones = false;
		try {
			Iterator<Prediccion> it_pred = predicciones.iterator();

			CopyOnWriteArrayList<Prediccion> nuevas_predicciones = new CopyOnWriteArrayList<Prediccion>();
			
			while (it_pred.hasNext()) {
				Prediccion pred = it_pred.next();
				if (pred.getId() != id) {
					nuevas_predicciones.add(pred);
				}
			}
			if (nuevas_predicciones.size() == predicciones.size()) {
				noEstabaEnPredicciones = true;
			}
			request.getSession().setAttribute("predicciones", nuevas_predicciones);
		} catch (Exception e) {
			ErroresUtils.extraeStackTrace(e);
			noEstabaEnPredicciones = true;
		}

		if (noEstabaEnPredicciones) {
			return new ModelAndView("redirect:/prediccionincompleta.htm");
		}
		else {
			return new ModelAndView("redirect:/predicciones.htm");
		}
	}
	
	public void setPrediccionDao(PrediccionDao prediccionDao) {
		this.prediccionDao = prediccionDao;
	}
}

