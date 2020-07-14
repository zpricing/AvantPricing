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
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Dia;
import cl.zpricing.avant.model.Empresa;
import cl.zpricing.avant.model.Forecast;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Hora;
import cl.zpricing.avant.model.Parametro;
import cl.zpricing.avant.model.loadmanager.ActualizacionResultado;
import cl.zpricing.avant.negocio.sincronizador.LogProcesos;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.negocio.sincronizador.Proceso;
import cl.zpricing.avant.negocio.sincronizador.Sincronizador;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.EmpresaDao;
import cl.zpricing.avant.servicios.ForecastDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.FuncionException;
import cl.zpricing.avant.servicios.LogProcesosDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.commons.utils.DateUtils;
import cl.zpricing.commons.utils.EnvioDeCorreo;
import cl.zpricing.commons.utils.ErroresUtils;

public class MascarasManager extends Proceso{
	protected Logger log = (Logger) Logger.getLogger(this.getClass());
	
	protected FuncionDao funcionDao;
	protected PeliculaDao peliculaDao;
	protected ComplejoDao complejoDao;
	protected ForecastDao forecastDao;
	protected EmpresaDao empresaDao;
	protected ServiciosRevenueManager serviciosRM;
	protected LogProcesosDao logProcesosDao;
	protected LogProcesos logProcesos;
	protected ProcessAlertFactory processAlertFactory;
	protected boolean isIniciado = false;
	protected Sincronizador sincronizador = Sincronizador.getInstance();
	protected boolean soloFuncionesNuevas;
	
	public void run()  {
		this.isIniciado = true;
		logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "RUN | soloFuncionesNuevas?: "+ soloFuncionesNuevas);
		log.info("************* EJECUTANDO RUN *************");
		log.info("soloFuncionesNuevas?: " + soloFuncionesNuevas);
		List<Complejo> complejos = complejoDao.complejosTodos();
		log.info("  # Complejos : [" + complejos.size() + "]");
		actualizarMascarasComplejos(complejos, soloFuncionesNuevas);
		
		if(!soloFuncionesNuevas)	{
			log.info("Actualizando Estadisticas");
			actualizarEstadisticas();
		}
		
		logProcesos = LogProcesosManager.finalizado(logProcesos.getNombreProceso());
		this.isIniciado = false;
	}
	
	protected void actualizarMascarasComplejos(List<Complejo> complejos, boolean soloFuncionesNuevas){
		log.info("Comenzando ejecucion de actualizarMascarasComplejos...");
		log.info("Fecha de ejecucion : " + new Date());
		logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Running Theaters Masks update");
		
		Iterator<Complejo> iteradorComplejos = complejos.iterator();
		HashMap<String, ArrayList<String>> peliculasSinClasificar = new HashMap<String, ArrayList<String>>();
		
		while (iteradorComplejos.hasNext()) {
			checkInterrupt();
			Complejo complejo = iteradorComplejos.next();
			
			try {
				int diasRevision = serviciosRM.obtenerIntParametro("MascarasManager", "diasRevision");
				log.info("	Comenzando iteracion, dias a revisar : [" + diasRevision + "]");
				
				//Obtencion de calendario con fecha de hoy.
				Calendar calendario = DateUtils.obtenerCalendario();
				calendario.add(Calendar.DATE, 1);
				
				log.info("	Complejo : [" + complejo.getNombre() + "]");
				logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Iterating Theater : [" + complejo.getNombre() + "]");
				
				for (int i = 0 ; i < diasRevision ; i++) {
					checkInterrupt();
					Date fechaFunciones = DateUtils.obtenerSoloFecha(calendario);
					log.info("	Revisando dia : [" + fechaFunciones + "]");
					
					List<Funcion> funciones = funcionDao.obtenerFuncionesParaForecast(complejo, fechaFunciones, soloFuncionesNuevas);
					log.info("	# funciones : [" + funciones.size() + "]");
					
					Iterator<Funcion> iteradorFunciones = funciones.iterator();
					while (iteradorFunciones.hasNext()) {
						checkInterrupt();
						Funcion funcion = iteradorFunciones.next();
						
						log.debug("Calculando Forecast Funcion: [" + funcion.getId() + "] ...");
						Calendar fechaHoraFuncion=Calendar.getInstance();
						fechaHoraFuncion.setTime(funcion.getFecha());
						Dia diaFuncion = new Dia();
						Hora horaFuncion = new Hora();
						diaFuncion.setId(fechaHoraFuncion.get(Calendar.DAY_OF_WEEK));
						horaFuncion.setId(fechaHoraFuncion.get(Calendar.HOUR_OF_DAY));
						funcion.setDia(diaFuncion);
						funcion.setHora(horaFuncion);
						
						if(funcion.getSemana() == null || funcion.getExhibicion() == null) {
							Map<String, Integer> semanaExhibicion = funcionDao.ObtenerSemanaExhibicion(funcion);
							funcion.setSemana(semanaExhibicion.get("semana"));
							funcion.setExhibicion(semanaExhibicion.get("exhibicion"));
							log.debug("Actualizando Semana/Exhibicion funcion: " + funcion.getId() 
									+ " Semana: " + funcion.getSemana() + " Exhibicion: " + funcion.getExhibicion());
							funcionDao.actualizarSemanaExhibicion(funcion);
						}
						
						try {
							Forecast forecast = forecastDao.obtenerForecast(funcion, complejo.getUltimaCargaCompleta());
							funcion.setAsistenciaProyectada(forecast.getAsistenciaProyectada());
							funcion.setPorcentajeOcupacionProyectado(forecast.getPorcentajeOcupacionProyectado());
							log.debug("	Asistencia Proyectada: "+ funcion.getAsistenciaProyectada());
							log.debug("	Porcentaje Ocupacion Proyectado: "+ funcion.getPorcentajeOcupacionProyectado());
							if(funcion.isBloqueada()) {
								log.debug("	Funcion Bloqueada, no se modifica la mascara.");
								funcionDao.actualizarAsistenciaProyectadaSinMascara(funcion);
							}
							else {
								if (forecast.getMascara() != null) {
									funcion.cambiaMascara(forecast.getMascara());
									log.debug("	Mascara elegida: " + funcion.getMascara().getDescripcion());
									funcionDao.actualizarAsistenciaProyectada(funcion);
								}
								else {
									String msg = "([" + complejo.getNombre() + "] - [" + funcion.getId() + "] " + funcion.getFecha() + 
											")- OP: [" + funcion.getPorcentajeOcupacionProyectado() + "" +
											"%]) NO EXISTE UNA MASCARA APLICABLE, SE OMITE";
									log.error(msg);
									ArrayList<String> funcionesError  = new ArrayList<String>();
									if(peliculasSinClasificar.containsKey(funcion.getPeliculaAsociada().getNombre())) {
										funcionesError  = peliculasSinClasificar.get(funcion.getPeliculaAsociada().getNombre());
									}
									funcionesError.add(msg);
									peliculasSinClasificar.put(funcion.getPeliculaAsociada().getNombre(), funcionesError);
								}
							}
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
					calendario.add(Calendar.DATE, 1);
				}
			}
			catch(IllegalStateException e){
				break;
			}
			catch (Exception e) {
				ProcessAlert alert = (ProcessAlert) processAlertFactory.makeAlert(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e));
				alert.notifyAlert();
				log.error(ErroresUtils.extraeStackTrace(e));
				logProcesos = LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e) ,false);
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
					msg += "  " + funcionIt.next()+nl;
				}
			}
			ProcessAlert alert = (ProcessAlert) processAlertFactory.makeAlert(logProcesos.getNombreProceso(), msg);
			alert.notifyAlert();
		}
		checkInterrupt();
		Parametro parametro = new Parametro();
		parametro.setSistema("MascarasManager");
		parametro.setSubSistema("ultimaActualizacion");
		parametro.setCodigo(DateUtils.format_EEE_ddMMyyyy_HHmm.format(new Date()));
		serviciosRM.actualizarParametro(parametro);
		logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(),"Masks Update Finished");
		log.info("Termino de actualizacion de mascaras");
	}

	protected void actualizarEstadisticas() {
		log.info("Comenzando ejecucion de actualizarEstadisticas...");
		logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(), "Running Statistics Update");
		forecastDao.actualizarEstadisticas();
		logProcesos = LogProcesosManager.cambioDeEtapa(logProcesos.getNombreProceso(),"Statistics Update Finished");
		log.info("Termino de actualizarEstadisticas");
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

	@Override
	public boolean isEnEjecucion() {
		return isIniciado;
	}

	@Override
	public void setEnEjecucion(boolean estado) {
		this.isIniciado = estado;
		
	}	

	@Override
	public LogProcesos logProceso() {
		return logProcesos;
	}
	public void checkInterrupt() {
		if(Thread.currentThread().isInterrupted()) 
			throw new IllegalStateException("Interrumpido");
	}
	public void setSoloFuncionesNuevas(boolean soloFuncionesNuevas) {
		this.soloFuncionesNuevas = soloFuncionesNuevas;
	}
}
