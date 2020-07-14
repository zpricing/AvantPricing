package cl.zpricing.avant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.etl.variantes.EtlCinemaExtractionImpl;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.negocio.sincronizador.ProcesoCinemaExtraction;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.util.FactoryProcesos;

public class CinemaExtractDataController implements Controller {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private ComplejoDao complejoDao;
	private ProcesoCinemaExtraction procesoCinemaExtraction;
	
	private FactoryProcesos factoryProcesos;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cinemaId = request.getParameter("id");
		boolean isFullExtraction = Boolean.parseBoolean(request.getParameter("full"));
		log.debug("CinemaExtractDataController - parameters:");
		log.debug("  cinemaId : " + cinemaId);
		log.debug("  isFullExtraction : " + isFullExtraction);
		
		Complejo complejo = complejoDao.obtenerComplejo(Integer.parseInt(cinemaId));
		EtlCinemaExtractionImpl etlCinemaExtraction = (EtlCinemaExtractionImpl) procesoCinemaExtraction.obtenerProceso();
		etlCinemaExtraction.setFullHistoryDataExtraction(isFullExtraction);
		etlCinemaExtraction.setComplejo(complejo);
		String codigoComplejo = complejo.getNombre().replaceAll(" ", "");
		log.debug("  Codigo complejo : " + codigoComplejo);
		procesoCinemaExtraction.setCodigo(etlCinemaExtraction.getCodigo() + codigoComplejo);
		procesoCinemaExtraction.setProceso(etlCinemaExtraction);
		factoryProcesos.iniciarProceso(procesoCinemaExtraction);
		
		return new ModelAndView(new RedirectView("admin_managecomplejos.htm"));
	}

	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
	public void setProcesoCinemaExtraction(
			ProcesoCinemaExtraction procesoCinemaExtraction) {
		this.procesoCinemaExtraction = procesoCinemaExtraction;
	}
	public void setFactoryProcesos(FactoryProcesos factoryProcesos) {
		this.factoryProcesos = factoryProcesos;
	}
}
