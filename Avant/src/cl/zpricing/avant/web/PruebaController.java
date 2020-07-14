/**
 * 
 */
package cl.zpricing.avant.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.web.form.PruebaForm;

/**
 * <b>Clase de Prueba para demostrar como funciona el framework en
 * su totalidad</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 17-12-2008 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class PruebaController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	private FuncionDao funcionDao;
	private ComplejoDao complejoDao;
	private PeliculaDao peliculaDao;
	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());	
	

	/**
	 * @return the complejoDao
	 */
	public ComplejoDao getComplejoDao() {
		return complejoDao;
	}

	/**
	 * @param complejoDao the complejoDao to set
	 */
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

	/**
	 * @return the peliculaDao
	 */
	public PeliculaDao getPeliculaDao() {
		return peliculaDao;
	}

	/**
	 * @param peliculaDao the peliculaDao to set
	 */
	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}

	/**
	 * @return the funcionDao
	 */
	public FuncionDao getFuncionDao() {
		return funcionDao;
	}

	/**
	 * @param funcionDao the funcionDao to set
	 */
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		logger.debug("formBackingObject...");
		logger.debug("Prueba...");
		PruebaForm form = new PruebaForm();
		return form;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Complejo complejo = complejoDao.obtenerComplejo(1);
		ArrayList<Pelicula> peliculas = peliculaDao.obtenerListaPeliculasActivas();
		
		Map<Integer, Double> map2 = funcionDao.obtenerAsistenciasHoraPeliculaComplejo(peliculas, complejo, 0, 5);
		Iterator<Entry<Integer, Double>> it_map = map2.entrySet().iterator();
		while(it_map.hasNext()){
			Entry<Integer, Double> entry = it_map.next();
			log.debug("key "+entry.getKey()+"valor "+entry.getValue().intValue());
		}
		if(map2.isEmpty())
			log.debug("NADA");
		
		map2 = funcionDao.obtenerVarianzasHoraComplejo(peliculas, complejo, 0, 5);
		Iterator<Entry<Integer, Double>> it_map1 = map2.entrySet().iterator();
		while(it_map1.hasNext()){
			Entry<Integer, Double> entry = it_map1.next();
			log.debug("key "+entry.getKey()+"valor "+entry.getValue().intValue());
		}
		if(map2.isEmpty())
			log.debug("NADA");
		
		map.put("hola", 0);
		return map;
	}

	

}
