/**
 * 
 */
package cl.zpricing.avant.web.test;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.log.Log;
import cl.zpricing.avant.model.log.TipoLog;
import cl.zpricing.avant.negocio.MarkerManager;
import cl.zpricing.avant.negocio.PrediccionManager;
import cl.zpricing.avant.servicios.AggregateDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.LogDao;
import cl.zpricing.avant.servicios.TipoLogDao;

/**
 * <b>Descripci�n de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 13-01-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class PruebaMarioController implements Controller {

	/**
	 * Impresi�n de log.
	 */
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private AggregateDao aggregateDao;
	private MarkerManager markerManager;
	private PrediccionManager prediccionManager;
	private FuncionDao funcionDao;
	private TipoLogDao tipoLogDao;
	private LogDao logDao;

	/**
	 * @return the aggregateDao
	 */
	public AggregateDao getAggregateDao() {
		return aggregateDao;
	}

	/**
	 * @param aggregateDao
	 *            the aggregateDao to set
	 */
	public void setAggregateDao(AggregateDao aggregateDao) {
		this.aggregateDao = aggregateDao;
	}

	public MarkerManager getMarkerManager() {
		return markerManager;
	}

	public void setMarkerManager(MarkerManager markerManager) {
		this.markerManager = markerManager;
	}

	public PrediccionManager getPrediccionManager() {
		return prediccionManager;
	}

	public void setPrediccionManager(PrediccionManager prediccionManager) {
		this.prediccionManager = prediccionManager;
	}

	public FuncionDao getFuncionDao() {
		return funcionDao;
	}

	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}

	public TipoLogDao getTipoLogDao() {
		return tipoLogDao;
	}

	public void setTipoLogDao(TipoLogDao tipoLogDao) {
		this.tipoLogDao = tipoLogDao;
	}

	public LogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		ArrayList<TipoLog> tipos = tipoLogDao.obtenerTiposLog();
		ArrayList<Log> logs = logDao.obtenerLogsTipoLog(tipos.get(0));
		return new ModelAndView("pruebaMario", "dato", logs);
	}

}
