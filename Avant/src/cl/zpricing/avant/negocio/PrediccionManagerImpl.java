package cl.zpricing.avant.negocio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import umontreal.iro.lecuyer.probdist.NormalDist;
import cl.zpricing.avant.model.Asistencia;
import cl.zpricing.avant.model.Categoria;
import cl.zpricing.avant.model.Clase;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Epoca;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.Publico;
import cl.zpricing.avant.model.loadmanager.GrupoPlantillas;
import cl.zpricing.avant.model.loadmanager.Plantilla;
import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.model.prediction.PrediccionPorClase;
import cl.zpricing.avant.model.prediction.PrediccionPorDia;
import cl.zpricing.avant.model.prediction.PrediccionPorFuncion;
import cl.zpricing.avant.servicios.AggregateDao;
import cl.zpricing.avant.servicios.ClaseDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.MarkerDao;
import cl.zpricing.avant.servicios.MascaraDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.servicios.PlantillaDao;
import cl.zpricing.avant.servicios.PrediccionDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.chart.Grafico;

/**
 * <b>Implementacion de los metodos necesarios para la prediccion de
 * asistencia</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 17-02-2009 Daniel Estevez Garay: version inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class PrediccionManagerImpl implements PrediccionManager {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private FuncionDao				funcionDao;
	private PrediccionDao			prediccionDao;
	private PeliculaDao				peliculaDao;
	private AggregateDao			aggregateDao;
	private ClaseDao				claseDao;
	private ServiciosRevenueManager	serviciosRM;
	private MarkerManager			markerManager;
	private PlantillaDao			plantillaDao;
	private MascaraDao				mascaraDao;
	private MarkerDao				markerDao;

	public void predecirDemandaPorDia(Prediccion prediccion) { }

	public void predecirDemandaPorFuncion(Prediccion prediccion, int dias, GregorianCalendar fecha_inicio_rango, GregorianCalendar fecha_fin_rango) {
//		log.debug("Entrando a prediccionPorFuncion...dias " + dias);
		// dias=0;

		Pelicula pelicula = prediccion.getPelicula();
		Complejo complejo = prediccion.getComplejo();
		int dia_estreno = Integer.parseInt(serviciosRM.obtenerParametro("Cine", "Dia_Estreno").getCodigo());
		int dia_reemplazo_feriado = Integer.parseInt(serviciosRM.obtenerParametro("Revenue Manager", "dia_reemplazo_feriado").getCodigo());
		int hora_inicio_dia = Integer.parseInt(serviciosRM.obtenerParametro("Cine", "hora_inicio_dia").getCodigo());
		int hora_termino_dia = Integer.parseInt(serviciosRM.obtenerParametro("Cine", "hora_termino_dia").getCodigo());
		int diferencia_dias_dia_cine = Integer.parseInt(serviciosRM.obtenerParametro("Cine", "diferencia_dias_dia_cine").getCodigo());

		/**
		 * OBTENCION DE PELICULAS SEMEJANTES Se obtienen todas las peliculas y
		 * se obtienen las semejantes
		 */
		ArrayList<Pelicula> peliculas_semejantes = obtenerPeliculasSemejantes(prediccion, fecha_inicio_rango, fecha_fin_rango);

		/**
		 * PREDICCION POR FUNCION POR CADA DIA Teniendo el conjunto de peliculas
		 * semejantes se procede a predecir por funcion para cada prediccion por
		 * dia existente
		 */
		Iterator<PrediccionPorDia> itPredd = prediccion.getPrediccionPorDia().iterator();
		int dia = 0;
		if (dias < 0) {
			dia = dias;
		}

		if (!peliculas_semejantes.isEmpty()) {
			//log.debug("Peliculas semejantes no es empty, como es el ideal");
			while (itPredd.hasNext()) {
				PrediccionPorDia predd = itPredd.next();
//				log.debug("entramos a la siguiente ppd " + predd.getId());
				
//				if (predd.getPrediccionesPorFuncion() != null)
//					continue;
				
//				log.debug("No hay predicciones por función creadas, así que seguimos");

				ArrayList<PrediccionPorFuncion> prediccionesporfuncion = new ArrayList<PrediccionPorFuncion>();
				boolean isFeriado = markerManager.isFechaFeriado(predd.getFecha());
				Map<Integer, Integer> suma_asistencias_por_hora_dia = new HashMap<Integer, Integer>();
				Map<Integer, Integer> suma_funciones_por_hora_dia = new HashMap<Integer, Integer>();
				Map<Integer, Double> suma_diferencia_promedio_asistencia_cuadrado_por_hora_dia = new HashMap<Integer, Double>();
				Map<Integer, Double> promedios_por_hora_dia = new HashMap<Integer, Double>();
				for (int i = 9; i <= 24; i++)
					suma_asistencias_por_hora_dia.put(i, 0);
				for (int i = 9; i <= 24; i++)
					suma_funciones_por_hora_dia.put(i, 0);
				for (int i = 9; i <= 24; i++)
					suma_diferencia_promedio_asistencia_cuadrado_por_hora_dia.put(i, 0.0);

				Iterator<Pelicula> itpel_semejante = peliculas_semejantes.iterator();

				while (itpel_semejante.hasNext()) {
					Pelicula pelicula_semejante = itpel_semejante.next();
					Funcion primeraFuncion = funcionDao.obtenerPrimeraFuncion(pelicula_semejante, complejo, dia_estreno);

					if (primeraFuncion == null)
						continue;

					GregorianCalendar fecha_inicio = new GregorianCalendar();
					fecha_inicio.setTime(primeraFuncion.getFecha());
					fecha_inicio.add(Calendar.DAY_OF_MONTH, dias);
					/**
					 * por cada prediccion por día voy avanzando un día
					 */
					fecha_inicio.add(Calendar.DAY_OF_MONTH, dia);

					//log.debug("Pelicula: " + pelicula_semejante.getNombre()+ " Viendo dia...fecha inicio "+ fecha_inicio.getTime());
					/**
					 * CASO FERIADOS Si es feriado y nuestro día no lo es, se
					 * omite y continua
					 */
					if (markerManager.isFechaFeriado(fecha_inicio.getTime()) && !isFeriado)
						continue;
					/**
					 * se revisa el caso contrario
					 */
					if (!markerManager.isFechaFeriado(fecha_inicio.getTime()) && isFeriado) {
						if (fecha_inicio.get(Calendar.DAY_OF_WEEK) > dia_reemplazo_feriado) {
							fecha_inicio.add(Calendar.DAY_OF_WEEK, 1);
						}
						fecha_inicio.set(Calendar.DAY_OF_WEEK, dia_reemplazo_feriado);
					}
					fecha_inicio.set(Calendar.HOUR_OF_DAY, hora_inicio_dia);
					fecha_inicio.set(Calendar.MINUTE, 0);
					fecha_inicio.set(Calendar.SECOND, 0);
					GregorianCalendar fecha_fin = new GregorianCalendar();
					fecha_fin.setTime(fecha_inicio.getTime());
					fecha_fin.add(Calendar.DAY_OF_MONTH, diferencia_dias_dia_cine);
					fecha_fin.set(Calendar.HOUR_OF_DAY, hora_termino_dia);

					ArrayList<Funcion> funciones_pelicula_semejante = funcionDao.obtenerListaFuncionesComplejo(fecha_inicio, fecha_fin, pelicula_semejante, complejo);

					Iterator<Funcion> itFun_pelicula_semejante = funciones_pelicula_semejante.iterator();

					while (itFun_pelicula_semejante.hasNext()) {
						int asistencias_funcion = 0;
						Funcion funcion_pelicula_semejante = itFun_pelicula_semejante.next();
						ArrayList<Asistencia> asistencias_funcion_pel_sem = funcion_pelicula_semejante.getAsistenciasDeFuncion();
						Iterator<Asistencia> itas_funcion_pel_sem = asistencias_funcion_pel_sem.iterator();

						while (itas_funcion_pel_sem.hasNext()) {
							Asistencia asistencia_funcion_pel_sem = itas_funcion_pel_sem.next();
							asistencias_funcion += asistencia_funcion_pel_sem.getAsistencia();
						}

						Calendar calendar_funcion_pel_sem = Calendar.getInstance();
						calendar_funcion_pel_sem.setTime(funcion_pelicula_semejante.getFecha());

						int posicion_a_insertar = calendar_funcion_pel_sem.get(Calendar.HOUR_OF_DAY);

						if (posicion_a_insertar >= 0 && posicion_a_insertar <= 3)
							posicion_a_insertar = 24;

						int suma_asistencias_por_hora_existente = suma_asistencias_por_hora_dia.get(posicion_a_insertar);
						int suma_funciones_por_hora_existente = suma_funciones_por_hora_dia.get(posicion_a_insertar);
						suma_asistencias_por_hora_dia.put(posicion_a_insertar, asistencias_funcion + suma_asistencias_por_hora_existente);
						suma_funciones_por_hora_dia.put(posicion_a_insertar, suma_funciones_por_hora_existente + 1);
					}
				}
				/**
				 * CALCULOS PROMEDIOS POR HORA DEL DIA Tenemos la suma de
				 * asistencias por cada hora del d�a y el numero de funciones
				 * por hora del dia luego procederemos a calcular los promedios
				 */
				for (int i = 9; i <= 24; i++) {
					if (suma_funciones_por_hora_dia.get(i) != 0)
						promedios_por_hora_dia.put(i, (double) (suma_asistencias_por_hora_dia.get(i) / suma_funciones_por_hora_dia.get(i)));
					else
						promedios_por_hora_dia.put(i, 0.0);
//					log.debug("Promedios por hora dia " + dia + " = "
//							+ promedios_por_hora_dia.get(i)
//							+ " numero asistencias "
//							+ suma_asistencias_por_hora_dia.get(i));
				}

				/**
				 *CALCULO VARIANZA Ya con los promedios por hora podemos saber
				 * cuanto aporta cada funcion a la varianza mediante la suma de
				 * la diferencia con el promedio al cuadrado
				 */
				itpel_semejante = peliculas_semejantes.iterator();
				while (itpel_semejante.hasNext()) {
					Pelicula pelicula_semejante = itpel_semejante.next();
					Funcion primeraFuncion = funcionDao.obtenerPrimeraFuncion(pelicula_semejante, complejo, dia_estreno);

					if (primeraFuncion == null)
						continue;
					GregorianCalendar fecha_inicio = new GregorianCalendar();
					fecha_inicio.setTime(primeraFuncion.getFecha());
					fecha_inicio.add(Calendar.DAY_OF_MONTH, dias);
					/**
					 * por cada prediccion por d�a voy avanzando un d�a
					 */
					fecha_inicio.add(Calendar.DAY_OF_MONTH, dia);

//					log.debug("Pelicula: " + pelicula_semejante.getNombre()
//							+ " Viendo dia...fecha inicio "
//							+ fecha_inicio.getTime());
					/**
					 * CASO FERIADOS Si es feriado y nuestro d�a no lo es, se
					 * omite y continua
					 */
					if (markerManager.isFechaFeriado(fecha_inicio.getTime()) && !isFeriado)
						continue;
					/**
					 * se revisa el caso contrario
					 */
					if (!markerManager.isFechaFeriado(fecha_inicio.getTime()) && isFeriado) {
						if (fecha_inicio.get(Calendar.DAY_OF_WEEK) > dia_reemplazo_feriado)
							fecha_inicio.add(Calendar.DAY_OF_WEEK, 1);

						fecha_inicio.set(Calendar.DAY_OF_WEEK,
								dia_reemplazo_feriado);
					}

					fecha_inicio.set(Calendar.HOUR_OF_DAY, hora_inicio_dia);
					fecha_inicio.set(Calendar.MINUTE, 0);
					fecha_inicio.set(Calendar.SECOND, 0);
					GregorianCalendar fecha_fin = new GregorianCalendar();
					fecha_fin.setTime(fecha_inicio.getTime());
					fecha_fin.add(Calendar.DAY_OF_MONTH, diferencia_dias_dia_cine);
					fecha_fin.set(Calendar.HOUR_OF_DAY, hora_termino_dia);

//					log.debug("Pelicula: " + pelicula_semejante.getNombre()
//							+ " Viendo dia...fecha inicio "
//							+ fecha_inicio.getTime() + " fecha fin "
//							+ fecha_fin.getTime());

					ArrayList<Funcion> funciones_pelicula_semejante = funcionDao.obtenerListaFuncionesComplejo(fecha_inicio, fecha_fin, pelicula_semejante, complejo);
					Iterator<Funcion> itFun_pelicula_semejante = funciones_pelicula_semejante.iterator();

					while (itFun_pelicula_semejante.hasNext()) {
						int asistencias_funcion = 0;
						Funcion funcion_pelicula_semejante = itFun_pelicula_semejante.next();
						ArrayList<Asistencia> asistencias_funcion_pel_sem = funcion_pelicula_semejante.getAsistenciasDeFuncion();
						Iterator<Asistencia> itas_funcion_pel_sem = asistencias_funcion_pel_sem.iterator();

						while (itas_funcion_pel_sem.hasNext()) {
							Asistencia asistencia_funcion_pel_sem = itas_funcion_pel_sem.next();
							asistencias_funcion += asistencia_funcion_pel_sem.getAsistencia();
						}
//						log.debug("Funcion fecha "
//								+ funcion_pelicula_semejante.getFecha()
//								+ " asistencia total funcion de pelicula "
//								+ pelicula_semejante.getNombre() + " "
//								+ asistencias_funcion);
						Calendar calendar_funcion_pel_sem = Calendar.getInstance();
						calendar_funcion_pel_sem.setTime(funcion_pelicula_semejante.getFecha());

						int posicion_a_insertar = calendar_funcion_pel_sem.get(Calendar.HOUR_OF_DAY);

						if (posicion_a_insertar >= 0 && posicion_a_insertar <= 3)
							posicion_a_insertar = 24;

						double suma_diferencia_old = suma_diferencia_promedio_asistencia_cuadrado_por_hora_dia.get(posicion_a_insertar);
						suma_diferencia_promedio_asistencia_cuadrado_por_hora_dia.put(posicion_a_insertar, suma_diferencia_old + Math.pow(promedios_por_hora_dia .get(posicion_a_insertar) - asistencias_funcion, 2));

					}
				}
				/**
				 * CALCULO VARIANZA Ya con la suma de cuadrados se calcula la
				 * varianza
				 */
				Map<Integer, Double> varianza_por_hora_dia = new HashMap<Integer, Double>();
				for (int i = 9; i <= 24; i++) {
					int funciones_por_hora = suma_funciones_por_hora_dia.get(i);
					double varianza = 0.0;
					if (funciones_por_hora != 0 && funciones_por_hora != 1)
						varianza = suma_diferencia_promedio_asistencia_cuadrado_por_hora_dia.get(i) / (funciones_por_hora - 1);

					varianza_por_hora_dia.put(i, varianza);
//					log
//							.debug("Varianza dia "
//									+ dia
//									+ " = "
//									+ varianza_por_hora_dia.get(i)
//									+ " suma dif "
//									+ suma_diferencia_promedio_asistencia_cuadrado_por_hora_dia
//											.get(i) + " funciones por hora "
//									+ funciones_por_hora);
				}

				/**
				 * CREACION DE CURVA ACUMULADA Acumulamos los promedios de
				 * asistencias
				 */
				for (int i = 10; i <= 24; i++) {
					promedios_por_hora_dia.put(i, promedios_por_hora_dia.get(i) + promedios_por_hora_dia.get(i - 1));
//					log.debug("Promedio acumulado dia " + dia + "=" + promedios_por_hora_dia.get(i));
				}

				/**
				 * OBTENCION DE ESTIMACION DIARIA
				 */

				int estimacion_dia = predd.getEstimacion();

				/**
				 * OBTENCION DE FUNCIONES DEL DIA A PREDECIR
				 */
				GregorianCalendar fecha_inicio = new GregorianCalendar();
				fecha_inicio.setTime(predd.getFecha());
				GregorianCalendar fecha_fin = new GregorianCalendar();
				fecha_fin.setTime(fecha_inicio.getTime());
				fecha_inicio.set(Calendar.HOUR_OF_DAY, 9);
				fecha_inicio.set(Calendar.MINUTE, 0);
				fecha_inicio.set(Calendar.SECOND, 0);
				fecha_fin.add(Calendar.DAY_OF_MONTH, 1);
				fecha_fin.set(Calendar.HOUR_OF_DAY, 3);
				fecha_fin.set(Calendar.MINUTE, 0);
				fecha_fin.set(Calendar.SECOND, 0);

				ArrayList<Funcion> funciones_pelicula_dia = funcionDao.obtenerListaFuncionesComplejo(fecha_inicio, fecha_fin, pelicula, complejo);

				Iterator<Funcion> it_fun_pel_dia = funciones_pelicula_dia.iterator();

				/**
				 * CREACION DE LAS PREDICCIONES POR FUNCION
				 */
				int estimacion_anterior = 0;
				while (it_fun_pel_dia.hasNext()) {
					Funcion funcion_pel_dia = it_fun_pel_dia.next();
					Calendar calendar_funcion_pel_dia = Calendar.getInstance();
					calendar_funcion_pel_dia.setTime(funcion_pel_dia.getFecha());
					PrediccionPorFuncion predf = new PrediccionPorFuncion();
					predf.setPrediccionPorDia(predd);
					predf.setFuncionPredecida(funcion_pel_dia);
					double x1 = calendar_funcion_pel_dia.get(Calendar.HOUR_OF_DAY);
					double x2 = x1 + 1;
					if (x1 >= 0 && x1 <= 3) {
						predf.setEstimacion((int) ((promedios_por_hora_dia.get(24)) * estimacion_dia / promedios_por_hora_dia.get(24)) - estimacion_anterior);
						predf.setVarianza(varianza_por_hora_dia.get(24) * promedios_por_hora_dia.get(24) * promedios_por_hora_dia.get(24) / (estimacion_dia * estimacion_dia));
					} else {

						double x = x1 + (double) calendar_funcion_pel_dia.get(Calendar.MINUTE) / 60;


						double y1 = promedios_por_hora_dia.get((int) x1);
						double y2 = promedios_por_hora_dia.get((int) x2);
						double promedio_acumulado_estimado = Util.interpolar(x1, x2, y1, y2, x);

						int estimacion = (int) (promedio_acumulado_estimado * estimacion_dia / promedios_por_hora_dia.get(24));
						predf.setEstimacion(estimacion - estimacion_anterior);
						predf.setVarianza(promedios_por_hora_dia.get(24)* promedios_por_hora_dia.get(24) * Util.interpolar(x1, x2, varianza_por_hora_dia.get((int) x1), varianza_por_hora_dia.get((int) x2), x) / (estimacion_dia * estimacion_dia));
						estimacion_anterior = estimacion;

						/**
						 * Se agregan mascaras y mascara por defecto
						 */
						/*
						 * Esto va fuera del if (Mario)
						 * predf.setMascaras(this.getMascaras(funcion_pel_dia));
						 * predf
						 * .setMascaraPorDefecto(this.getMascaraDefault(predf));
						 */

					}

					/**
					 * Se agregan mascaras y mascara por defecto
					 */
					predf.setMascaras(this.getMascaras(funcion_pel_dia));
					predf.setMascaraPorDefecto(this.getMascaraDefault(predf));

//					log.debug("La máscara escogida se guardará en la base de datos: ");
					// Ahora se guarda la máscara escogida (por default) en la
					// base de datos
					if (this.getMascaraDefault(predf) == 0) {
						// ULTRA FLAITE ALERT:
						// predf.setMascara(mascaraDao.obtenerMascara(1));
						predf.setMascara(mascaraDao.obtenerMascarasSala(predf.getFuncionPredecida().getSala()).get(0));
//						log.debug(": Se le puso cualquiera porque MascaraDefault era 0. La máscara elegida fue: " + predf.getMascara().getDescripcion());

					} else if (mascaraDao.obtenerMascaraCargadaParaUnaFuncion(predf.getFuncionPredecida()) != null) {
						// el caso en que en la base de datos ya se haya cargado esa función; hay que
						// poner aquel como máscara en vez del que le correspondería normalmente
						predf.setMascara(mascaraDao.obtenerMascaraCargadaParaUnaFuncion(predf.getFuncionPredecida()));
						
						if (predf.getMascara() == null) {
							// En caso de que no funcione. Nunca debiera llegar aquí.
							predf.setMascara(mascaraDao.obtenerMascarasSala(predf.getFuncionPredecida().getSala()).get(0));
						}
						
//						log.debug(": Se le puso la máscara que ya estaba cargada para esta función predecida. La máscara es: " + predf.getMascara().getDescripcion());
					} else {
						predf.setMascara(mascaraDao.obtenerMascara(this.getMascaraDefault(predf)));
//						log.debug("Se le puso la máscara default. La máscara es: " + predf.getMascara().getDescripcion());

					}
//					try {
//						log.debug("La siguiente máscara se le puso a la predicción por función " + predf.getId());
//						log.debug(" id: " + predf.getMascara().getId());
//						log.debug(" descripcion: " + predf.getMascara().getDescripcion());
//					} catch (Exception e) {
//						log.debug("No se pudo poner la máscara or something else went wrong");
//						log.debug(ErroresUtils.extraeStackTrace(e));
//					}

					prediccionesporfuncion.add(predf);

				}

				predd.setPrediccionesPorFuncion(prediccionesporfuncion);
				predd.setMarkersFecha(markerDao.obtenerMarkersFechaComplejoPelicula(predd.getFecha(), complejo, pelicula));
				dia++;
			}
//			log.debug("se terminó de recorrer las predicciones por día");
			/**
		 * 
		 */
		} else {
//			log.debug("No se pudo encontrar películas semejantes.");
			while (itPredd.hasNext()) {
				PrediccionPorDia predd = itPredd.next();
				if (predd.getPrediccionesPorFuncion() != null)
					continue;
				ArrayList<PrediccionPorFuncion> prediccionesporfuncion = new ArrayList<PrediccionPorFuncion>();
				GregorianCalendar fecha_inicio = new GregorianCalendar();
				fecha_inicio.setTime(predd.getFecha());
				GregorianCalendar fecha_fin = new GregorianCalendar();
				fecha_fin.setTime(fecha_inicio.getTime());
				fecha_inicio.set(Calendar.HOUR_OF_DAY, 9);
				fecha_inicio.set(Calendar.MINUTE, 0);
				fecha_inicio.set(Calendar.SECOND, 0);
				fecha_fin.add(Calendar.DAY_OF_MONTH, 1);
				fecha_fin.set(Calendar.HOUR_OF_DAY, 3);
				fecha_fin.set(Calendar.MINUTE, 0);
				fecha_fin.set(Calendar.SECOND, 0);

				ArrayList<Funcion> funciones_pelicula_dia = funcionDao.obtenerListaFuncionesComplejo(fecha_inicio, fecha_fin, pelicula, complejo);

				Iterator<Funcion> it_fun_pel_dia = funciones_pelicula_dia.iterator();
				while (it_fun_pel_dia.hasNext()) {
					Funcion funcion_pel_dia = it_fun_pel_dia.next();
					Calendar calendar_funcion_pel_dia = Calendar.getInstance();
					calendar_funcion_pel_dia.setTime(funcion_pel_dia.getFecha());
					PrediccionPorFuncion predf = new PrediccionPorFuncion();
					predf.setPrediccionPorDia(predd);
					predf.setFuncionPredecida(funcion_pel_dia);
					predf.setError(true);
					prediccionesporfuncion.add(predf);
					
					/**********************************************************/
					/*** WARNING WARNING WARNING WARNING ***/
					/**********************************************************/
					/* En el caso de que no hayan películas similares, como se
					 * cae por no tener máscara, le vamos a asignar una máscara
					 * cualquier con tal que no se caiga. */
					log.error("No existen películas similares, por lo que no se encontró máscara, se procede a seleccionar una máscara al azar.");
					predf.setMascara(mascaraDao.obtenerMascarasSala(predf.getFuncionPredecida().getSala()).get(0));					
					/************** FIN ****************/					
					// En la vista, se van a mostrar todas las funciones en cero.

				}
				predd.setError(true);
				predd.setPrediccionesPorFuncion(prediccionesporfuncion);
			}

			prediccion.setError(true);
		}

	}

	public void predecirDemandaPorClase(PrediccionPorFuncion prediccionpf, int tipo_regresion) {

		log.debug("--------------Entrando a PrediccionPorClase............");
		if (prediccionpf.getPrediccionesPorClase() != null)
			return;
		// Pelicula pelicula = prediccion.getPelicula();
		Complejo complejo = prediccionpf.getFuncionPredecida().getSala().getComplejoAlCualPertenece();
		double error_admitido = Double.parseDouble(serviciosRM.obtenerParametro("Revenue Manager", "error_admitido").getCodigo());
		double rango_hora = Double.parseDouble(serviciosRM.obtenerParametro("Revenue Manager", "rango_hora").getCodigo());
		int hora_inicio = Integer.parseInt(serviciosRM.obtenerParametro("Revenue Manager", "hora_inicio").getCodigo());
		int hora_fin = Integer.parseInt(serviciosRM.obtenerParametro("Revenue Manager", "hora_fin").getCodigo());
		double semanas = Double.parseDouble(serviciosRM.obtenerParametro("Revenue Manager", "semanas").getCodigo());
		int dia_reemplazo_feriado = Integer.parseInt(serviciosRM.obtenerParametro("Revenue Manager", "dia_reemplazo_feriado").getCodigo());

		/**
		 * se verifica que el tipo de regresi�n escogida est� dentro del rango,
		 * sino se establece por defecto que se trata de una regresi�n lineal
		 * por tramos
		 */
		if (tipo_regresion != 0 && tipo_regresion != 1 && tipo_regresion != 2 && tipo_regresion != 3)
			tipo_regresion = 0;

		Funcion funcion = prediccionpf.getFuncionPredecida();
		boolean isFeriado = markerManager.isFechaFeriado(funcion.getFecha());

		/**
		 * verificación de que hora de funcion esté dentro de rango de horas
		 */
		GregorianCalendar fecha_funcion = new GregorianCalendar();
		fecha_funcion.setTime(funcion.getFecha());
		GregorianCalendar fecha_inicio_hora = new GregorianCalendar();

		GregorianCalendar fecha_fin_hora = new GregorianCalendar();

		fecha_inicio_hora.setTime(funcion.getFecha());
		fecha_inicio_hora.set(Calendar.HOUR_OF_DAY, hora_inicio);
		fecha_inicio_hora.set(Calendar.MINUTE, 0);
		fecha_fin_hora.setTime(funcion.getFecha());
		fecha_fin_hora.set(Calendar.HOUR_OF_DAY, hora_fin);
		fecha_fin_hora.set(Calendar.MINUTE, 0);

		if (hora_fin <= 3 && hora_fin >= 0)
			fecha_fin_hora.add(Calendar.DAY_OF_MONTH, 1);

		if (fecha_funcion.after(fecha_fin_hora) || fecha_funcion.before(fecha_inicio_hora))
			return;

		ArrayList<Clase> clases_mascara = this.getClasesRM(funcion);

		double precio_maximo = 0.0;
		int numero_clases = 0;

		HashMap<Double, Integer> asistencias_por_clase = new HashMap<Double, Integer>();
		HashMap<Double, Double> suma_de_cuadrados = new HashMap<Double, Double>();

		boolean todas_especiales = true;
		Iterator<Clase> it_clasemre = clases_mascara.iterator();
		while (it_clasemre.hasNext()) {
			Clase clasemre = it_clasemre.next();
			if (!clasemre.isEsEspecial()) {
				todas_especiales = false;
				break;
			}

		}

		it_clasemre = clases_mascara.iterator();

		while (it_clasemre.hasNext()) {

			Clase clasemre = it_clasemre.next();
			double precio_clasem = clasemre.getPrecio();
			if (!clasemre.isEsEspecial() || todas_especiales) {

				asistencias_por_clase.put(precio_clasem, 0);
				suma_de_cuadrados.put(precio_clasem, 0.0);

				if (precio_clasem > precio_maximo)
					precio_maximo = precio_clasem;

				numero_clases++;
			}
		}
		double[] precios_clase = new double[numero_clases];

		HashMap<Double, Double> promedios = new HashMap<Double, Double>();

		/**
		 * Se obtienen las funciones a la misma hora y día, semanas hacia atras
		 */
		GregorianCalendar fecha_inicio = new GregorianCalendar();
		GregorianCalendar fecha_fin = new GregorianCalendar();

		fecha_inicio.setTime(funcion.getFecha());
		if (isFeriado && !markerManager.isFechaFeriado(fecha_inicio.getTime())) {
			if (fecha_inicio.get(Calendar.DAY_OF_WEEK) > dia_reemplazo_feriado)
				fecha_inicio.add(Calendar.DAY_OF_WEEK, 1);

			fecha_inicio.set(Calendar.DAY_OF_WEEK, dia_reemplazo_feriado);

		}
		fecha_fin.setTime(fecha_inicio.getTime());
		fecha_inicio.add(Calendar.HOUR_OF_DAY, -(int) rango_hora);
		fecha_inicio.add(Calendar.MINUTE, -(int) (60 * (rango_hora - (int) rango_hora)));
		fecha_inicio.add(Calendar.SECOND, -(int) (60 * (60 * (rango_hora - (int) rango_hora) - (int) (60 * (rango_hora - (int) rango_hora)))));

		fecha_fin.add(Calendar.HOUR_OF_DAY, (int) rango_hora);
		fecha_fin.add(Calendar.MINUTE, (int) (60 * (rango_hora - (int) rango_hora)));
		fecha_fin.add(Calendar.SECOND, (int) (60 * (60 * (rango_hora - (int) rango_hora) - (int) (60 * (rango_hora - (int) rango_hora)))));

		for (int i = 0; i < semanas; i++) {
			fecha_inicio.add(Calendar.WEEK_OF_MONTH, -1);
			fecha_fin.add(Calendar.WEEK_OF_MONTH, -1);
			// log.debug("Fechas inicio "+fecha_inicio.getTime()+" Fechas inicio "+fecha_fin.getTime());
			ArrayList<Funcion> funciones = funcionDao.obtenerFuncionesComplejo(fecha_inicio, fecha_fin, complejo);
			Iterator<Funcion> it_fun = funciones.iterator();
			while (it_fun.hasNext()) {
				Funcion fun = it_fun.next();

				if (!isFeriado && markerManager.isFechaFeriado(fun.getFecha()))
					continue;

				ArrayList<Asistencia> asistenciasfun = fun.getAsistenciasDeFuncion();
				Iterator<Asistencia> it_asfun = asistenciasfun.iterator();

				while (it_asfun.hasNext()) {
					Asistencia asistenciafun = it_asfun.next();
					Clase clase_asistencia = asistenciafun.getClase();
					if (clase_asistencia.isEsEspecial() && !todas_especiales) {
						asistencias_por_clase.put(precio_maximo, asistencias_por_clase.get(precio_maximo) + asistenciafun.getAsistencia());
					} else {
						Iterator<Clase> it_clam = clases_mascara.iterator();
						while (it_clam.hasNext()) {
							Clase clasemre = it_clam.next();
							if (!clasemre.isEsEspecial() || todas_especiales) {
								double precio_mre = clasemre.getPrecio();
								if (clase_asistencia.getPrecio() >= precio_mre) {
									asistencias_por_clase.put(precio_mre, asistencias_por_clase.get(precio_mre) + asistenciafun.getAsistencia());
									break;
								}
							}
						}
					}
				}
			}
		}
		int j = 0;
		Iterator<Clase> it_clamn = clases_mascara.iterator();
		while (it_clamn.hasNext()) {
			Clase clasemre = it_clamn.next();
			if (!clasemre.isEsEspecial() || todas_especiales) {
				double precio_mre = clasemre.getPrecio();
				promedios.put(precio_mre, asistencias_por_clase.get(precio_mre)
						/ semanas);
				precios_clase[j++] = precio_mre;
				log.debug("precios clase " + precio_mre);
				log.debug(" Promedio de precio " + precio_mre + " - "
						+ asistencias_por_clase.get(precio_mre) / semanas);
			}
		}

		/**
		 * Calculo de varianza
		 */

		fecha_fin.setTime(funcion.getFecha());
		fecha_inicio.setTime(funcion.getFecha());
		fecha_inicio.add(Calendar.HOUR_OF_DAY, -(int) rango_hora);
		fecha_inicio.add(Calendar.MINUTE,-(int) (60 * (rango_hora - (int) rango_hora)));
		fecha_inicio.add(Calendar.SECOND, -(int) (60 * (60 * (rango_hora - (int) rango_hora) - (int) (60 * (rango_hora - (int) rango_hora)))));

		fecha_fin.add(Calendar.HOUR_OF_DAY, (int) rango_hora);
		fecha_fin.add(Calendar.MINUTE, (int) (60 * (rango_hora - (int) rango_hora)));
		fecha_fin.add(Calendar.SECOND, (int) (60 * (60 * (rango_hora - (int) rango_hora) - (int) (60 * (rango_hora - (int) rango_hora)))));

		for (int i = 0; i < semanas; i++) {
			HashMap<Double, Integer> asistencias_por_semana = new HashMap<Double, Integer>();
			Iterator<Clase> it_clamr = clases_mascara.iterator();
			while (it_clamr.hasNext()) {
				Clase clasemre = it_clamr.next();
				if (!clasemre.isEsEspecial() || todas_especiales) {
					double precio_mre = clasemre.getPrecio();
					asistencias_por_semana.put(precio_mre, 0);
				}
			}

			fecha_inicio.add(Calendar.WEEK_OF_MONTH, -1);
			fecha_fin.add(Calendar.WEEK_OF_MONTH, -1);
			ArrayList<Funcion> funciones = funcionDao.obtenerFuncionesComplejo(fecha_inicio, fecha_fin, complejo);
			Iterator<Funcion> it_fun = funciones.iterator();
			while (it_fun.hasNext()) {
				Funcion fun = it_fun.next();

				if (!isFeriado && markerManager.isFechaFeriado(fun.getFecha()))
					continue;

				ArrayList<Asistencia> asistenciasfun = fun.getAsistenciasDeFuncion();
				Iterator<Asistencia> it_asfun = asistenciasfun.iterator();

				while (it_asfun.hasNext()) {
					Asistencia asistenciafun = it_asfun.next();
					Clase clase_asistencia = asistenciafun.getClase();
					if (clase_asistencia.isEsEspecial() && !todas_especiales) {
						asistencias_por_semana.put(precio_maximo, asistencias_por_clase.get(precio_maximo) + asistenciafun.getAsistencia());
					} else {
						Iterator<Clase> it_clam = clases_mascara.iterator();
						while (it_clam.hasNext()) {
							Clase clasemre = it_clam.next();
							if (!clasemre.isEsEspecial() || todas_especiales) {
								double precio_mre = clasemre.getPrecio();
								if (clase_asistencia.getPrecio() >= precio_mre) {
									asistencias_por_semana.put(precio_mre, asistencias_por_clase.get(precio_mre) + asistenciafun.getAsistencia());
									break;
								}
							}
						}
					}
				}
			}
			it_clamr = clases_mascara.iterator();
			while (it_clamr.hasNext()) {
				Clase clasemre = it_clamr.next();
				if (!clasemre.isEsEspecial() || todas_especiales) {
					double precio_mre = clasemre.getPrecio();
					suma_de_cuadrados.put(precio_mre, suma_de_cuadrados.get(precio_mre) + Math.pow(promedios.get(precio_mre) - asistencias_por_semana.get(precio_mre), 2));
				}
			}
		}
		HashMap<Double, Double> varianza = new HashMap<Double, Double>();
		HashMap<Double, Double> desviaciones = new HashMap<Double, Double>();
		Iterator<Clase> it_clamr = clases_mascara.iterator();
		while (it_clamr.hasNext()) {
			Clase clasemre = it_clamr.next();
			if (!clasemre.isEsEspecial() || todas_especiales) {
				double precio_mre = clasemre.getPrecio();
				if (semanas != 0 && semanas != 1)
					varianza.put(precio_mre, suma_de_cuadrados.get(precio_mre) / (semanas - 1));
				else
					varianza.put(precio_mre, 0.0);

				desviaciones.put(precio_mre, Math.sqrt(varianza.get(precio_mre)));
				log.debug("varianza " + varianza.get(precio_mre) + " desviacion " + desviaciones.get(precio_mre));
			}
		}

		/**
		 * acumulo la demanda y se rellenan los coeficiente de varianza
		 */

		it_clamr = clases_mascara.iterator();
		double[] promedios_array = new double[numero_clases];
		double[] coef_array = new double[numero_clases];
		double acumulado = 0.0;
		int k = 0;
		while (it_clamr.hasNext()) {
			Clase clasemre = it_clamr.next();
			if (!clasemre.isEsEspecial() || todas_especiales) {
				double precio_mre = clasemre.getPrecio();
				acumulado += promedios.get(precio_mre);
				promedios_array[k] = acumulado;
				log.debug("Promedio precio " + precio_mre + " - "
						  + promedios.get(precio_mre) + " acumulado"
						  + promedios_array[k]);
				coef_array[k] = desviaciones.get(precio_mre) / acumulado;
				log.debug("coeficiente " + coef_array[k] + " desv "
						  + desviaciones.get(precio_mre));
				k++;
			}
		}

		/**
		 * Algoritmo EMSR-b
		 */

		Grafico graficoRegresionClases = new Grafico(promedios_array, precios_clase);
		Grafico graficoRegresionCoef = new Grafico(precios_clase, coef_array);

		/**
		 * lineal
		 */
		if (tipo_regresion == 1)
			graficoRegresionClases.lineal();
		/**
		 * exponencial
		 */
		else if (tipo_regresion == 2)
			graficoRegresionClases.exponencial();
		/**
		 * potencial
		 */
		else if (tipo_regresion == 3)
			graficoRegresionClases.potencial();

		double pendiente = graficoRegresionClases.getPendiente();
		double interseccion = graficoRegresionClases.getInterseccion();

		/**
		 * la regresión de los coeficientes de varianza siempre es exponencial
		 */
		graficoRegresionCoef.exponencial();
		double pendiente_var = graficoRegresionCoef.getPendiente();
		double interseccion_var = graficoRegresionCoef.getInterseccion();

		/**
		 * Lineal por tramos se cuentan valores distintos de cero
		 */

		int n = 0;
		for (int i = 0; i < promedios_array.length && i < precios_clase.length; i++)
			if ((int) promedios_array[i] != 0 && precios_clase[i] != 0)
				n++;
		/**
		 * se crean arrays auxiliares y se rellenan
		 */
		double[] aux_x = new double[n];
		double[] aux_y = new double[n];

		int h = 0;
		for (int i = 0; i < promedios_array.length && i < precios_clase.length; i++) {
			if (promedios_array[i] != 0 && precios_clase[i] != 0) {
				aux_x[h] = promedios_array[i];
				aux_y[h++] = precios_clase[i];
			}
		}

		/**
		 * se ordena de menor a mayor el eje x
		 */

		Util.quicksortDoble(aux_x, aux_y, 0, n - 1);

		double medias;
		double probabilidades;
		double desviaciones_est;
		double distribuciones_norm_inv;
		double[] estimaciones_normalizadas = new double[clases_mascara.size()];
		Iterator<Clase> it_clases_mascara = clases_mascara.iterator();
		Iterator<Clase> it_clases_mascara_next = clases_mascara.iterator();
		if (it_clases_mascara_next.hasNext())
			it_clases_mascara_next.next();
		double suma_producto = 0.0;
		int indice = 0;
		double aux_y_old = 100000.0;
		double aux_x_old = 0.0;
		double aux_x_next = aux_x[indice];
		double aux_y_next = aux_y[indice++];
		double x = 1.0;
		double x_old = 0.0;
		double distribucion_norm_old = 0.0;
		double y_regresion = 0.0;

		/**
		 * lineal por tramos
		 */
		if (tipo_regresion == 0)
			y_regresion = Util.interpolar(aux_x_old, aux_x_next, aux_y_old, aux_y_next, x);
		/**
		 * lineal
		 */
		if (tipo_regresion == 1)
			y_regresion = pendiente * x + interseccion;
		/**
		 * exponencial
		 */
		if (tipo_regresion == 2)
			y_regresion = Math.exp(interseccion + x * pendiente);
		/**
		 * potencial
		 */
		if (tipo_regresion == 3)
			y_regresion = Math.pow(10, interseccion) * Math.pow(x, pendiente);

		double total = 0.0;
		for (int i = 0; it_clases_mascara.hasNext(); i++) {
			Clase clase = it_clases_mascara.next();
			double precio = clase.getPrecio();
			log.debug("Iterando para precio " + precio);
			double error_actual = 1000.0;

			while (error_actual > error_admitido && y_regresion > precio) {
				/**
				 * lineal por tramos
				 */
				if (tipo_regresion == 0)
					y_regresion = Util.interpolar(aux_x_old, aux_x_next, aux_y_old, aux_y_next, x);
				/**
				 * lineal
				 */
				if (tipo_regresion == 1)
					y_regresion = pendiente * x + interseccion;
				/**
				 * exponencial
				 */
				if (tipo_regresion == 2)
					y_regresion = Math.exp(interseccion + x * pendiente);
				/**
				 * potencial
				 */
				if (tipo_regresion == 3)
					y_regresion = Math.pow(10, interseccion) * Math.pow(x, pendiente);

				error_actual = Math.abs(precio - y_regresion) / precio;

				x++;

				/**
				 * lineal por tramos
				 */
				while (x > aux_x_next && tipo_regresion == 0) {
					aux_x_old = aux_x_next;
					aux_y_old = aux_y_next;
					if (indice < aux_x.length) {
						aux_x_next = aux_x[indice];
						aux_y_next = aux_y[indice++];
					} else {
						aux_x_next = 100000.;
						aux_y_next = 0.0;
					}

				}

			}
			double probabilidad = Math.exp(pendiente_var * precio
					+ interseccion_var);
			desviaciones_est = x * probabilidad;
			medias = x;
			suma_producto += precio * (x - x_old);
			double p_barra = suma_producto / x;
			double precio_next = 0.0;
			if (it_clases_mascara_next.hasNext()) {
				Clase clase_next = it_clases_mascara_next.next();
				precio_next = clase_next.getPrecio();
			}

			probabilidades = precio_next / p_barra;
			if (precio_next != 0.0)
				distribuciones_norm_inv = NormalDist.inverseF(medias, desviaciones_est, probabilidades);
			else
				distribuciones_norm_inv = x - x_old;
			x_old = x;
			if (distribuciones_norm_inv < 0 || Double.isNaN(distribuciones_norm_inv))
				distribuciones_norm_inv = 0.0;
			estimaciones_normalizadas[i] = 0.0;
			if (precio_next != 0.0 && distribucion_norm_old > distribuciones_norm_inv) {
				estimaciones_normalizadas[i] = distribuciones_norm_inv - distribucion_norm_old;
				distribucion_norm_old = distribuciones_norm_inv;
			} else {
				estimaciones_normalizadas[i] = distribuciones_norm_inv;
			}

			total += estimaciones_normalizadas[i];
			log.debug(i + " precio  " + precio + " distribucion inversa "
					  + distribuciones_norm_inv
					  + " suma_producto dividido(p_barra) " + p_barra
					  + " probabilidad " + probabilidades + " desviaciones_est "
					  + desviaciones_est + " medias " + medias);
			log.debug("Estimacion " + estimaciones_normalizadas[i] + " Total:"
					  + total);
		}

		log.debug("total " + total);
		double estimacion = prediccionpf.getEstimacion();
		double desviacion_funcion = Math.sqrt(prediccionpf.getVarianza());
		Iterator<Clase> it_clases_mascaran = clases_mascara.iterator();
		ArrayList<PrediccionPorClase> prediccionesPorClase = new ArrayList<PrediccionPorClase>();
		for (int i = 0; it_clases_mascaran.hasNext(); i++){
			Clase clase = it_clases_mascaran.next();
			double precio = clase.getPrecio();
			log.debug("Iterando para precio " + precio);

			PrediccionPorClase predc = new PrediccionPorClase();

			int asistencia = (int) (Math.round(estimacion * estimaciones_normalizadas[i] / total));

			double var = desviacion_funcion * estimaciones_normalizadas[i] / total;

			predc.setClase(clase);
			predc.setPrediccionPorFuncion(prediccionpf);
			if (Double.isNaN(asistencia) || asistencia < 0)
				asistencia = 0;
			predc.setAsistencia(asistencia);
			/**
			 * evitamos overflow aritmético al insertar en la capa de datos
			 */
			if (var < 0.1 || Double.isNaN(var))
				var = 0.0;
			predc.setVarianza(var);
			log.debug("-----Agregando prediccion---");
			log.debug("Parametros: Clase:" + clase.getPrecio() + " Asistencia:"
					  + asistencia + " Varianza:" + var
					  + " Id de prediccion por función:" + prediccionpf.getId());

			predc.setId(prediccionDao.agregarPrediccionporClase(predc));
			prediccionesPorClase.add(predc);
			// log.debug("Prediccion Por Clase de id "+id+" agregada");

		}
		prediccionpf.setPrediccionesPorClase(prediccionesPorClase);
		prediccionpf.setGraficoRegresionClases(graficoRegresionClases);
		prediccionpf.setGraficoRegresionCoef(graficoRegresionCoef);
	}

	public int[] obtenerAsistenciaPorPeliculaDiaComplejo(Pelicula pelicula,
			Complejo complejo, GregorianCalendar fecha, int cant_dias) {

		// log.debug("obtenerAsistenciaPorPeliculaDiaComplejo....");
		/*
		 * ArrayList<Funcion> funciones = new ArrayList<Funcion>();
		 * ArrayList<Asistencia> asistencias_temp = new ArrayList<Asistencia>();
		 */

		/*
		 * GregorianCalendar fecha_inicio = new GregorianCalendar(fecha
		 * .get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha
		 * .get(Calendar.DAY_OF_MONTH), 8, 0); GregorianCalendar fecha_fin = new
		 * GregorianCalendar(fecha .get(Calendar.YEAR),
		 * fecha.get(Calendar.MONTH), fecha .get(Calendar.DAY_OF_MONTH), 3, 0);
		 * fecha_fin.add(Calendar.DAY_OF_MONTH, 1);
		 * 
		 * fecha_inicio.roll(Calendar.DAY_OF_MONTH, false);
		 * fecha_fin.roll(Calendar.DAY_OF_MONTH, false);
		 */

		GregorianCalendar fechaTemp = fecha;
		fechaTemp.set(Calendar.HOUR_OF_DAY, 0);
		fechaTemp.set(Calendar.MINUTE, 0);

		GregorianCalendar dia = fechaTemp;

		/*
		 * log.debug("peliculaId: " + pelicula.getId()); log.debug("complejoId"
		 * + complejo.getId());
		 */

		int lista[] = new int[cant_dias];

		// log.debug("Iniciando for...");

		for (int i = 0; i < cant_dias; i++) {

			// log.debug("dia: " + dia.getTime().toString());

			lista[i] = aggregateDao.obtenerAsistenciaDiariaPeliculaComplejo(dia.getTime(), pelicula.getId(), complejo.getId());

			// log.debug("lista[" + i + "]: " + lista[i]);

			dia.add(Calendar.DAY_OF_MONTH, 1);

			/*
			 * fecha_inicio.add(Calendar.DAY_OF_MONTH, 1);
			 * fecha_fin.add(Calendar.DAY_OF_MONTH, 1); funciones =
			 * funcionDao.obtenerListaFunciones(fecha_inicio, fecha_fin,
			 * pelicula);
			 * 
			 * for (Funcion f : funciones) {
			 * 
			 * 
			 * if(f.getSala().getComplejoAlCualPertenece().getId() ==
			 * complejo.getId()){ asistencias_temp =
			 * f.getAsistenciasDeFuncion();
			 * 
			 * for (Asistencia a : asistencias_temp) { lista[i]= lista[i] +
			 * a.asistencia; }
			 * 
			 * } }
			 */
		}
		return lista;

	}


	@Override
	public List<Mascara> getMascaras(Funcion funcion) {
		log.debug("funcion: " + funcion.getPeliculaAsociada().getNombre()+ " - " + funcion.getFecha().toString());
		log.debug("sala: " + funcion.getSala().getId() + " - "+ funcion.getSala().getNumero());
		return mascaraDao.obtenerMascarasSala(funcion.getSala());
	}

	
	@Override
	public int getMascaraDefault(PrediccionPorFuncion predPorFuncion) {
//		log.debug("Empezando getMascaraDefault...");
		Funcion funcion = predPorFuncion.getFuncionPredecida();
		// double corteSuperior =
		// Double.parseDouble(serviciosRM.obtenerStringRegistro("Revenue Manager",
		// "optimizacion_corte"));
		double ocupacion = (double) predPorFuncion.getEstimacion() / (double) funcion.getSala().getCapacidad();
		List<Mascara> posiblesMascaras = mascaraDao.obtenerMascarasSalaDefault(funcion.getSala());
		Mascara result = new Mascara();
		result.setId(1000);
		Iterator<Mascara> iter = posiblesMascaras.iterator();
//		log.debug("posibles mascaras...");
		while (iter.hasNext()) {
			Mascara temp = iter.next();
//			log.debug("Mascara: " + temp.getDescripcion());
			if (ocupacion >= temp.getPorcentajeDefault()) {
//				log.debug("mascara elegida...");
				result = temp;
			}
		}
		return result.getId();
	}


	@Override
	public ArrayList<Clase> getClasesRM(Funcion funcion) {
		log.debug("ejecutando getClasesRM...");
		ArrayList<Clase> clases = new ArrayList<Clase>();
		GrupoPlantillas grupo = plantillaDao.obtenerGrupoPlantillaFuncion(funcion);
		Plantilla plantilla = grupo.getPlantillaPadre();
		clases = plantilla.getClases();
		double[] precios = new double[clases.size()];
		double[] ids = new double[clases.size()];
		Iterator<Clase> iter1 = clases.iterator();
		int i = 0;
		while (iter1.hasNext()) {
			Clase temp = iter1.next();
			ids[i] = temp.getId();
			precios[i] = temp.getPrecio();
			i++;
		}
		Util.quicksortDoble(precios, ids, 0, ids.length - 1);
		clases = new ArrayList<Clase>();
		for (int j = ids.length - 1; j >= 0; j--) {
			Clase temp = new Clase();
			temp.setId((int) ids[j]);
			temp.setPrecio(precios[j]);
			temp.setEsEspecial(claseDao.obtenerClasePrecio(precios[j]).isEsEspecial());
			clases.add(temp);
		}
		return clases;
	}
	/**
	 * 
	 * Obtiene una lista con películas semejantes a la película que se está prediciendo, usando
	 * el método "clásico", o, si no se obtiene ninguna película mediante él, utilizando un algoritmo 
	 * alternativo.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 07-01-2010 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion La predicción que contiene la película cuyas semejantes se desea hallar.
	 * @param fechaInicioRango 
	 * @param fechaFinRango
	 * @return Un ArrayList<Pelicula> con todas las películas semejantes
	 * @since 3.0
	 */
	public ArrayList<Pelicula> obtenerPeliculasSemejantes(Prediccion prediccion, GregorianCalendar fechaInicioRango, GregorianCalendar fechaFinRango) {
		ArrayList<Pelicula> peliculasSemejantes = new ArrayList<Pelicula>();
		ArrayList<Pelicula> peliculasTodas = peliculaDao.obtenerListaPeliculas();
		Complejo complejo = prediccion.getComplejo();
		Pelicula pelicula = prediccion.getPelicula();
		ArrayList<Epoca> ourEpocas = pelicula.getEpocas();
		ArrayList<Categoria> ourCategorias = pelicula.getCategorias();
		ArrayList<Publico> ourPublicos = pelicula.getTiposDePublico();
		int diaEstreno = Integer.parseInt(serviciosRM.obtenerParametro("Cine", "Dia_Estreno").getCodigo());

//		 A continuación, recorremos todas las películas que existen en el universo para compararlas
//		 con la nuestra.
		for (Pelicula theirPelicula : peliculasTodas) {
			
			// ¿Comparten al menos un tipo de público?
			boolean testPublico = false;
			for (Publico theirPublico : theirPelicula.getTiposDePublico()) {
				for (Publico ourPublico : ourPublicos) {
					if (theirPublico.getId() == ourPublico.getId()) {
						testPublico = true;
						break;
					}
				}
				if (testPublico)
					break;
			}

			if (!testPublico)
				continue;

			// Y ¿comparten al menos una época?
			boolean testEpoca = false;
			for (Epoca theirEpoca : theirPelicula.getEpocas()) {
				for(Epoca outEpoca : ourEpocas) {
					if (outEpoca.getId() == theirEpoca.getId()) {
						testEpoca = true;
						break;
					}
				}
				if (testEpoca)
					break;
			}
			if (!testEpoca)
				continue;

			// Y ¿comparten al menos una misma categoría?
			boolean testCategoria = false;
			for (Categoria theirCategoria : theirPelicula.getCategorias()) {
				for (Categoria ourCategoria : ourCategorias) {
					if (theirCategoria.getId() == ourCategoria.getId()) {
						testCategoria = true;
						break;
					}
				}
				if (testCategoria)
					break;

			}
			if (!testCategoria)
				continue;
			
			// En este punto theirPelicula comparte con nuestra película al menos una categoría, una época y un público

			// ¿Se estrenó theirPelicula dentro del rango [fechaInicioRango, fechaFinRango]?
			boolean testRango = false;
			Funcion primeraFuncion = funcionDao.obtenerPrimeraFuncion(theirPelicula, complejo, diaEstreno);
			if (primeraFuncion == null)
				continue;

			GregorianCalendar fechaPrimeraFuncion = new GregorianCalendar();
			fechaPrimeraFuncion.setTime(primeraFuncion.getFecha());

			if (fechaPrimeraFuncion.after(fechaInicioRango) && fechaPrimeraFuncion.before(fechaFinRango))
				testRango = true;

			if (!testRango)
				continue;

			// Si sí, la agrego a peliculasSemejantes.
			peliculasSemejantes.add(theirPelicula);
		}
		Double porcentajeDeGiveUpness = 0.25;
		// Algoritmo secundario. Si el algoritmo anterior no encontró ninguna película semejante,
		// se hace lo siguiente.
		if (peliculasSemejantes.size() == 0) {
			log.debug("La película " + pelicula.getNombre() + " no tenía peliculas semejantes con el algoritmo clásico.");
			int cantidadPeliculasSemejantes = Integer.parseInt(serviciosRM.obtenerParametro("Revenue Manager", "cantidad_peliculas_semejantes").getCodigo());
			log.debug("cantidadPeliculasSemejantes = " + cantidadPeliculasSemejantes);
			int cantidadVueltasInfructuosas = 0;
			
			while (peliculasSemejantes.size() < cantidadPeliculasSemejantes) {
				if (cantidadVueltasInfructuosas > (int)(porcentajeDeGiveUpness*peliculasTodas.size()) && peliculasSemejantes.size() > 1) break;
				// Obtengo un número aleatorio
				int numero = obtenerNumeroAleatorio(0, peliculasTodas.size() - 1);
				// y saco la película que corresponde a ese número
				Pelicula theirPelicula = peliculasTodas.get(numero);
				// y reviso a continuación si acaso su estreno está dentro de las fechas que queremos.
				boolean testRango = false;
				Funcion primeraFuncion = funcionDao.obtenerPrimeraFuncion(theirPelicula, complejo, diaEstreno);
				if (primeraFuncion == null)
					continue;

				GregorianCalendar fechaPrimeraFuncion = new GregorianCalendar();
				fechaPrimeraFuncion.setTime(primeraFuncion.getFecha());

				if (fechaPrimeraFuncion.after(fechaInicioRango) && fechaPrimeraFuncion.before(fechaFinRango))
					testRango = true;

				if (!testRango) {
					cantidadVueltasInfructuosas++;
					continue;
				}
				// Si lo está, lo agregamos a películas semejantes
				if (!peliculasSemejantes.contains(theirPelicula)) {
					log.debug("La pelicula " + theirPelicula.getNombre() + " es \"semejante\" y la agregamos. ");
					peliculasSemejantes.add(theirPelicula);
					cantidadVueltasInfructuosas = 0;
				} else {
					log.debug("La pelicula " + theirPelicula.getNombre() + " no es \"semejante\" y la botamos. ");
					cantidadVueltasInfructuosas++;
					continue;
				}
			}
			// Cuando se han alcanzado las 8 películas (o las que sean) nos vamos
		}
		log.debug("peliculasSejantes.size() = " + peliculasSemejantes.size());
		return peliculasSemejantes;
	}
	
	private int obtenerNumeroAleatorio(int min, int max) {
		log.debug("obtenerNumeroAleatorio("+min+","+max+")");
		int numero = (int)(Math.random()*(1.0*(max-min + 1)) + 1.0*min);
		log.debug(" = " + numero);
		return numero;
	}
	
	public void setMarkerDao(MarkerDao markerDao) {
		this.markerDao = markerDao;
	}
	public void setMarkerManager(MarkerManager markerManager) {
		this.markerManager = markerManager;
	}
	public void setServiciosRM(ServiciosRevenueManager serviciosRM) {
		this.serviciosRM = serviciosRM;
	}
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}
	public void setClaseDao(ClaseDao claseDao) {
		this.claseDao = claseDao;
	}
	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}
	public void setPrediccionDao(PrediccionDao prediccionDao) {
		this.prediccionDao = prediccionDao;
	}
	public void setAggregateDao(AggregateDao aggregateDao) {
		this.aggregateDao = aggregateDao;
	}
	public void setPlantillaDao(PlantillaDao plantillaDao) {
		this.plantillaDao = plantillaDao;
	}
	public void setMascaraDao(MascaraDao mascaraDao) {
		this.mascaraDao = mascaraDao;
	}
}
