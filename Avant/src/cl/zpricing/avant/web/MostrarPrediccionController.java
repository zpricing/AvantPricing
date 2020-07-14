package cl.zpricing.avant.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.PrediccionDao;

/**
 * <b>Descripci�n de la Clase</b> Controlador de la vista que muestra
 * predicciones en las predicciones incompletas
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 15-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class MostrarPrediccionController implements Controller {

	private PrediccionDao prediccionDao;
	private FuncionDao funcionDao;
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("idPrediccion");
		int idPrediccion;
		try {
			idPrediccion = (Integer.parseInt(id));
		} catch (NumberFormatException e) {
			idPrediccion = 0;
		}
		Prediccion p = prediccionDao.obtenerPrediccion(idPrediccion);
		map.put("p", p);
		return new ModelAndView("mostrarprediccion", map);
	}

	/**
	 * @return the prediccionDao
	 */
	public PrediccionDao getPrediccionDao() {
		return prediccionDao;
	}

	/**
	 * @param prediccionDao
	 *            the prediccionDao to set
	 */
	public void setPrediccionDao(PrediccionDao prediccionDao) {
		this.prediccionDao = prediccionDao;
	}

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

}
