package cl.zpricing.avant.etl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import scriptella.execution.EtlExecutorException;

import cl.zpricing.avant.alerts.ProcessAlertFactory;
import cl.zpricing.avant.etl.model.ResultCompleteExecution;
import cl.zpricing.avant.etl.model.ResultEtl;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.LogProcesosDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.commons.utils.DateUtils;
import cl.zpricing.commons.utils.EnvioDeCorreo;
import cl.zpricing.commons.utils.ErroresUtils;

public abstract class EtlManager extends Proceso {
	protected Logger log = (Logger) Logger.getLogger(this.getClass());
	
	protected CinemaEtl cinemaDistributorEtl;
	protected CinemaEtl cinemaCategoryEtl;
	protected CinemaEtl cinemaMovieEtl;
	protected CinemaEtl cinemaScreenEtl;
	protected CinemaEtl cinemaPriceCardEtl;
	protected CinemaEtl cinemaPriceCardPricesEtl;
	protected CinemaEtl cinemaSessionEtl;
	protected CinemaEtl cinemaTicketEtl;
	protected CinemaEtl cinemaAttendanceEtl;
	protected GroupMoviesEtl groupMoviesEtl;
	
	protected ComplejoDao complejoDao;
	protected ServiciosRevenueManager serviciosRM;
	protected LogProcesosDao logProcesosDao;
	protected LogProcesos logProcesos;
	protected ProcessAlertFactory processAlertFactory;
	protected boolean iniciado = false;
	
	public ResultCompleteExecution completeExtraction(Complejo complejo, boolean isFullDataExtraction) {
		
		long id = Math.round(Math.random() * 1000);
		log.info("[" + id + "] Iniciando completeExtraction [" + complejo.getNombre() + "] ; fullDataExtraction? : [" + isFullDataExtraction + "]");
		ResultCompleteExecution resultExecution = new ResultCompleteExecution(complejo);
		
		boolean result = false;
		String resultMessage = "";
		
		try {
			checkInterrupt();
			
			logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), complejo.getNombre()+": "+": Starting complete extraction");
			logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), complejo.getNombre()+": "+"extract Distributors");
			result = extractDistributors(complejo);
			resultMessage = result ? "OK" : "ERROR";
			log.info("  [" + id + "] Distributors: " + resultMessage);
			resultExecution.addResultEtl(new ResultEtl("Distributors", result));
			
			checkInterrupt();
			logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), complejo.getNombre()+": "+"extract categories");
			result = extractCategories(complejo);
			resultMessage = result ? "OK" : "ERROR";			
			log.info("  [" + id + "] Categories: " + resultMessage);
			resultExecution.addResultEtl(new ResultEtl("Categories", result));
			
			checkInterrupt();
			logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), complejo.getNombre()+": "+"extract screens");
			result = extractScreens(complejo);
			resultMessage = result ? "OK" : "ERROR";
			log.info("  [" + id + "] Screens: " + resultMessage);
			resultExecution.addResultEtl(new ResultEtl("Screens", result));
			
			checkInterrupt();
			logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), complejo.getNombre()+": "+"extract tickets");
			result = extractTickets(complejo);
			resultMessage = result ? "OK" : "ERROR";
			log.info("  [" + id + "] Tickets: " + resultMessage);
			resultExecution.addResultEtl(new ResultEtl("Tickets", result));
			
			checkInterrupt();
			logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), complejo.getNombre()+": "+"extract price cards");
			result = extractPriceCards(complejo);
			resultMessage = result ? "OK" : "ERROR";
			log.info("  [" + id + "] PriceCards: " + resultMessage);
			resultExecution.addResultEtl(new ResultEtl("PriceCards", result));
			
			checkInterrupt();
			logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), complejo.getNombre()+": "+"extract prices");
			result = extractPrices(complejo);
			resultMessage = result ? "OK" : "ERROR";
			log.info("  [" + id + "] Prices: " + resultMessage);
			resultExecution.addResultEtl(new ResultEtl("Prices", result));
			
			checkInterrupt();
			logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), complejo.getNombre()+": "+"extract movies");
			result = extractMovies(complejo, isFullDataExtraction);
			resultMessage = result ? "OK" : "ERROR";
			log.info("  [" + id + "] Movies: " + resultMessage);
			resultExecution.addResultEtl(new ResultEtl("Movies", result));
			
			checkInterrupt();
			logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), complejo.getNombre()+": "+"extract sessions");
			result = extractSessions(complejo, isFullDataExtraction);
			resultMessage = result ? "OK" : "ERROR";
			log.info("  [" + id + "] Sessions: " + resultMessage);
			resultExecution.addResultEtl(new ResultEtl("Sessions", result));
			
			checkInterrupt();
			logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), complejo.getNombre()+": "+"extract attendance");
			result = extractAttendance(complejo, isFullDataExtraction);
			resultMessage = result ? "OK" : "ERROR";
			log.info("  [" + id + "] Attendance: " + resultMessage);
			resultExecution.addResultEtl(new ResultEtl("Attendance", result));
			
			result = true;
			
			if (resultExecution.isSuccessful()) {
				complejo.setUltimaCargaCompleta(new Date());
				complejo.setUltimaCargaFunciones(new Date());
				complejoDao.actualizarComplejo(complejo);
			}
			
			log.info("[" + id + "] Fin completeExtraction");
		} catch (Exception e) {
			resultExecution.addError(ErroresUtils.extraeStackTrace(e));
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), false);
		} finally {
			LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(),"Iteration Finished: " + complejo.getNombre());
		}
		return resultExecution;
	}
	
	public boolean isEnEjecucion() {
		return iniciado;
	}

	public void setEnEjecucion(boolean estado) {
		iniciado = estado;	
	}

	public void setLogProcesos(LogProcesos logProcesos) {
		this.logProcesos = logProcesos;
	}
	
	public void setProcessAlertFactory(ProcessAlertFactory processAlertFactory) {
		this.processAlertFactory = processAlertFactory;
	}
	
	public LogProcesos logProceso() {
		return logProcesos;
	}
	
	public void checkInterrupt() {
		if(Thread.currentThread().isInterrupted()) 
			throw new IllegalStateException("Interrumpido");
	}
	
	public boolean extractDistributors(Complejo complejo) throws IOException, EtlExecutorException {
		return cinemaDistributorEtl.execute(complejo, true);
	}
	public boolean extractCategories(Complejo complejo) throws IOException, EtlExecutorException {
		return cinemaCategoryEtl.execute(complejo, true);
	}
	public boolean extractMovies(Complejo complejo, boolean isFullDataExtraction) throws IOException, EtlExecutorException {
		return cinemaMovieEtl.execute(complejo, isFullDataExtraction);
	}
	public boolean extractScreens(Complejo complejo) throws IOException, EtlExecutorException {
		return cinemaScreenEtl.execute(complejo, true);
	}
	public boolean extractPriceCards(Complejo complejo) throws IOException, EtlExecutorException {
		return cinemaPriceCardEtl.execute(complejo, true);
	}
	public boolean extractPrices(Complejo complejo) throws IOException, EtlExecutorException {
		return cinemaPriceCardPricesEtl.execute(complejo, true);
	}
	public boolean extractSessions(Complejo complejo, boolean isFullDataExtraction) throws IOException, EtlExecutorException {
		return cinemaSessionEtl.execute(complejo, isFullDataExtraction);
	}
	public boolean extractTickets(Complejo complejo) throws IOException, EtlExecutorException {
		return cinemaTicketEtl.execute(complejo, true);
	}
	public boolean extractAttendance(Complejo complejo, boolean isFullDataExtraction) throws IOException, EtlExecutorException {
		return cinemaAttendanceEtl.execute(complejo, isFullDataExtraction);
	}
	public boolean groupMovies() throws IOException, EtlExecutorException {
		return groupMoviesEtl.execute();
	}
	
	public void setCinemaDistributorEtl(CinemaEtl cinemaDistributorEtl) {
		this.cinemaDistributorEtl = cinemaDistributorEtl;
	}
	public void setCinemaCategoryEtl(CinemaEtl cinemaCategoryEtl) {
		this.cinemaCategoryEtl = cinemaCategoryEtl;
	}
	public void setCinemaMovieEtl(CinemaEtl cinemaMovieEtl) {
		this.cinemaMovieEtl = cinemaMovieEtl;
	}
	public void setCinemaScreenEtl(CinemaEtl cinemaScreenEtl) {
		this.cinemaScreenEtl = cinemaScreenEtl;
	}
	public void setCinemaPriceCardEtl(CinemaEtl cinemaPriceCardEtl) {
		this.cinemaPriceCardEtl = cinemaPriceCardEtl;
	}
	public void setCinemaPriceCardPricesEtl(CinemaEtl cinemaPriceCardPricesEtl) {
		this.cinemaPriceCardPricesEtl = cinemaPriceCardPricesEtl;
	}
	public void setCinemaSessionEtl(CinemaEtl cinemaSessionEtl) {
		this.cinemaSessionEtl = cinemaSessionEtl;
	}
	public void setCinemaTicketEtl(CinemaEtl cinemaTicketEtl) {
		this.cinemaTicketEtl = cinemaTicketEtl;
	}
	public void setCinemaAttendanceEtl(CinemaEtl cinemaAttendanceEtl) {
		this.cinemaAttendanceEtl = cinemaAttendanceEtl;
	}
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
	public void setServiciosRM(ServiciosRevenueManager serviciosRM) {
		this.serviciosRM = serviciosRM;
	}
	public void setGroupMoviesEtl(GroupMoviesEtl groupMoviesEtl) {
		this.groupMoviesEtl = groupMoviesEtl;
	}
}
