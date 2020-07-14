package cl.zpricing.avant.negocio.forecastmanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cl.zpricing.avant.alerts.ProcessAlert;
import cl.zpricing.avant.alerts.ProcessAlertFactory;
import cl.zpricing.avant.model.Asistencia;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Dia;
import cl.zpricing.avant.model.Empresa;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Hora;
import cl.zpricing.avant.model.Parametro;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.loadmanager.ActualizacionResultado;
import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.EmpresaDao;
import cl.zpricing.avant.servicios.ForecastDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.FuncionException;
import cl.zpricing.avant.servicios.LogProcesosDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.servicios.ReportesDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.commons.utils.DateUtils;
import cl.zpricing.commons.utils.EnvioDeCorreo;
import cl.zpricing.commons.utils.ErroresUtils;

public class AsistenciasManager extends Proceso {
	protected Logger log = (Logger) Logger.getLogger(this.getClass());

	protected FuncionDao funcionDao;
	protected PeliculaDao peliculaDao;
	protected ComplejoDao complejoDao;
	protected ForecastDao forecastDao;
	protected EmpresaDao empresaDao;
	protected ReportesDao reportesDao;
	protected ServiciosRevenueManager serviciosRM;
	protected LogProcesosDao logProcesosDao; 
	protected LogProcesos logProcesos;
	protected ProcessAlertFactory processAlertFactory;
	protected volatile boolean iniciado=false;

	public void run() {
		iniciado = true;
		log.info("************* EJECUTANDO RUN *************");
		logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		List<Complejo> complejos = complejoDao.complejosTodos();
		log.info("  # Complejos : [" + complejos.size() + "]");
		actualizarForecastComplejos(complejos);
		checkInterrupt();
		
		int activado = 0;
		try {
			activado = serviciosRM.obtenerIntParametro("Reports", "activado");
			if(activado == 1) {
				LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Updating Reports");
				checkInterrupt();
				actualizarReportes();
				
				LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Sending Log");
				checkInterrupt();
				iniciado = false;
				LogProcesosManager.finalizado(logProcesos.getNombreProceso());
			}
		} catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			logProcesos = LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e) ,false);
		}
	}

	protected void actualizarReportes() {
		log.info("Comenzando ejecucion de actualizarReportes...");
		log.info("Fecha de ejecucion : " + new Date());
		
		logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Updating Daily Reports");
		log.info("Updating Daily Reports");
		reportesDao.actualizarReporteDiario();
		
		LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Updating Weekly Reports");
		log.info("Updating Weekly Reports");
		reportesDao.actualizarReporteSemanal();
		
		LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Updating Monthly Reports");
		log.info("Updating Monthly Reports");
		reportesDao.actualizarReporteMensual();
	}
	
	protected void actualizarForecastComplejos(List<Complejo> complejos)  {
		log.info("Comenzando ejecucion de actualizarForecastComplejos...");
		log.info("Fecha de ejecucion : " + new Date());
		logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Updating Theater Forecast");
		Map<Integer, ActualizacionResultado> resultadoGlobal = new HashMap<Integer, ActualizacionResultado>();

		Iterator<Complejo> iteradorComplejos = complejos.iterator();
		HashMap<String, ArrayList<String>> peliculasSinClasificar = new HashMap<String, ArrayList<String>>();
		
		while (iteradorComplejos.hasNext()) {
			checkInterrupt();
			Complejo complejo = iteradorComplejos.next();
			
			try {
				int diasAntesRevision = serviciosRM.obtenerIntParametro("AsistenciasManager", "diasAntesRevision");
				log.info("	Comenzando iteracion, dias a revisar : [" + diasAntesRevision + "]");
				LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Starting iteration: " +complejo.getNombre());
				//Obtencion de calendario con fecha de hoy.
				Calendar calendario = DateUtils.obtenerCalendario();
				calendario.add(Calendar.DATE, -diasAntesRevision);

				log.info("	Complejo : [" + complejo.getNombre() + "]");

				for (int i = 0 ; i < diasAntesRevision ; i++) {
					checkInterrupt();
					Date fechaFunciones = DateUtils.obtenerSoloFecha(calendario);
					log.info("	Revisando dia : [" + fechaFunciones + "]");

					List<Funcion> funciones = funcionDao.obtenerFuncionesSinAnalizar(complejo, fechaFunciones);
					log.info("	# funciones : [" + funciones.size() + "]");

					Iterator<Funcion> iteradorFunciones = funciones.iterator();
					while (iteradorFunciones.hasNext()) {
						checkInterrupt();
						Funcion funcion = iteradorFunciones.next();

						if (!funcion.isAnalizada()) {
							log.debug("Funcion: [" + funcion.getId() + "] no se ha analizado, actualizando tablas de Forecast...");


							Calendar fechaHoraFuncion=Calendar.getInstance();
							fechaHoraFuncion.setTime(funcion.getFecha());
							Dia diaFuncion = new Dia();
							Hora horaFuncion = new Hora();
							diaFuncion.setId(fechaHoraFuncion.get(Calendar.DAY_OF_WEEK));
							horaFuncion.setId(fechaHoraFuncion.get(Calendar.HOUR_OF_DAY));
							funcion.setDia(diaFuncion);
							funcion.setHora(horaFuncion);

							ArrayList<Asistencia> asistencias = funcionDao.obtenerAsistenciasByFuncion(funcion.getId()); 
							funcion.setAsistenciasDeFuncion(asistencias);

							Map<String, Integer> semanaExhibicion = funcionDao.ObtenerSemanaExhibicion(funcion);

							funcion.setSemana(semanaExhibicion.get("semana"));
							funcion.setExhibicion(semanaExhibicion.get("exhibicion"));

							try {
								forecastDao.agregarAsistenciaFuncion(funcion);
								funcion.setAnalizada(true);
								funcionDao.actualizarAnalizada(funcion);
							}
							catch (FuncionException e) {
								String msg = "([" + complejo.getNombre() + "] - [" + e.getFuncion().getId() + "] " + e.getFuncion().getFecha() + 
										")-" + e.getMessage();
								log.error(msg);
								ArrayList<String> funcionesError  = new ArrayList<String>();
								if(peliculasSinClasificar.containsKey(e.getFuncion().getPeliculaAsociada().getNombre())) {
									funcionesError  = peliculasSinClasificar.get(e.getFuncion().getPeliculaAsociada().getNombre());
								}
								funcionesError.add(msg);
								peliculasSinClasificar.put(e.getFuncion().getPeliculaAsociada().getNombre(), funcionesError);
							}
						}
					}
					calendario.add(Calendar.DATE, 1);
				}
				checkInterrupt();
				forecastDao.actualizarForecast(complejo);
				forecastDao.actualizarSimilitudCategoria(complejo);
				forecastDao.actualizarSimilitudFormato(complejo);
				forecastDao.actualizarSimilitudGrupo(complejo);
				forecastDao.actualizarSimilitudIdioma(complejo);
				forecastDao.actualizarSimilitudRanking(complejo);
				forecastDao.actualizarSimilitudRating(complejo);
				forecastDao.actualizarSimilitudSemana(complejo);
				forecastDao.actualizarSimilitudDiaHora(complejo);

			}
			catch(IllegalStateException e){
				throw new IllegalStateException("Interrumpido");
			}
			catch (Exception e) {
				ProcessAlert alert = (ProcessAlert) processAlertFactory.makeAlert(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e));
				alert.notifyAlert();
				log.error(ErroresUtils.extraeStackTrace(e));
				LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e),false);
			}
		}
		if(!peliculasSinClasificar.isEmpty()) {
			String msg = "";
			String nl = System.getProperty("line.separator");
			Iterator<Entry<String, ArrayList<String>>> it = peliculasSinClasificar.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry pairs = (Map.Entry)it.next();
				msg += "["+pairs.getKey()+"]"+nl;
				ArrayList<String> funcionesError = (ArrayList<String>) pairs.getValue();
				Iterator<String> funcionIt = funcionesError.iterator();
				while(funcionIt.hasNext()) {
					msg +="  "+funcionIt.next()+nl;
				}
			}
			ProcessAlert alert = (ProcessAlert) processAlertFactory.makeAlert(logProcesos.getNombreProceso(), msg);
			alert.notifyAlert();
		}
		
		checkInterrupt();
		Parametro parametro = new Parametro();
		parametro.setSistema("AsistenciasManager");
		parametro.setSubSistema("ultimaActualizacion");
		parametro.setCodigo(DateUtils.format_EEE_ddMMyyyy_HHmm.format(new Date()));
		serviciosRM.actualizarParametro(parametro);

		log.info("Termino de actualizarForecastComplejos");
		LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Finishing actualizarForecastComplejos");
	}

	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}

	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}

	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

	public void setForecastDao(ForecastDao forecastDao) {
		this.forecastDao = forecastDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
	
	public void setReportesDao(ReportesDao reportesDao) {
		this.reportesDao = reportesDao;
	}

	public void setServiciosRM(ServiciosRevenueManager serviciosRM) {
		this.serviciosRM = serviciosRM;
	}

	public void setLogProcesosDao(LogProcesosDao logProcesosDao) {
		this.logProcesosDao = logProcesosDao;
	}

	public void setLogProcesos(LogProcesos logProcesos) {
		this.logProcesos = logProcesos;
	}
	
	public void setProcessAlertFactory(ProcessAlertFactory processAlertFactory) {
		this.processAlertFactory = processAlertFactory;
	}

	public boolean isEnEjecucion() {
		return iniciado;
	}

	public void setEnEjecucion(boolean estado) {
		this.iniciado = estado;
	}

	@Override
	public LogProcesos logProceso() {
		return logProcesos;
	}

	public void checkInterrupt() {
		if(Thread.currentThread().isInterrupted()) 
			throw new IllegalStateException("Interrumpido");
	}
}
