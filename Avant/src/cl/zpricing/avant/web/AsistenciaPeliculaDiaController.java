package cl.zpricing.avant.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Parametro;
import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.model.prediction.PrediccionParametros;
import cl.zpricing.avant.model.prediction.PrediccionPorDia;
import cl.zpricing.avant.model.prediction.PrediccionPorFuncion;
import cl.zpricing.avant.negocio.PrediccionManager;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.MascaraDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.servicios.PrediccionDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.chart.CategoriaGrafico;
import cl.zpricing.avant.web.chart.GeneradorXMLGrafico;
import cl.zpricing.avant.web.chart.SerieDatos;
import cl.zpricing.avant.web.chart.Valor;
import cl.zpricing.avant.web.form.AsistenciaPeliculaDiaForm;
import cl.zpricing.commons.charts.utils.ArchivoGrafico;
import cl.zpricing.commons.utils.DateUtils;
import cl.zpricing.commons.utils.ErroresUtils;

/**
 * <b>Descripci�n de la Clase</b> Controlador de la vista asistenciapeliculadia,
 * permite hacer las ponderaciones por dia de las peliculas
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 07-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class AsistenciaPeliculaDiaController extends SimpleFormController implements Controller {

	private PrediccionManager prediccionManager;
	private PeliculaDao peliculaDao;
	private PrediccionDao prediccionDao;
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private ServiciosRevenueManager serviciosRM;
	private FuncionDao funcionDao;
	private MascaraDao mascaraDao;

	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		log.debug("onSubmit de AsistenciaPeliculaDia");

		// Buscamos en sesion las constantes que necesitamos
		Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute("datosGraficos");
		List<Map<String, Object>> graficos;
		CopyOnWriteArrayList<Prediccion> predicciones;
		if (map != null) {
			graficos = (List<Map<String, Object>>) map.get("graficos");
			if (graficos == null)
				graficos = new ArrayList<Map<String, Object>>();
			predicciones = (CopyOnWriteArrayList<Prediccion>) map.get("predicciones");
			if (graficos == null)
				graficos = new ArrayList<Map<String, Object>>(); //??
		} else {
			map = new HashMap<String, Object>();
			graficos = new ArrayList<Map<String, Object>>();
			predicciones = new CopyOnWriteArrayList<Prediccion>();
		}

		// buscamos las predicciones que ya estan guardadas
		CopyOnWriteArrayList<Prediccion> prediccionesAcumuladas = (CopyOnWriteArrayList<Prediccion>) request.getSession().getAttribute("predicciones");
		if (prediccionesAcumuladas == null) {
			prediccionesAcumuladas = new CopyOnWriteArrayList<Prediccion>();
		}
		
		log.debug("En onSubmit de AsistenciaPleiculaDia, antes de pasar por la BD. PrediccionesAcumuladas.size = " + prediccionesAcumuladas.size());

		// Hay que guardar lo que se lleva hecho en caso de volver o seguir
		if (request.getParameter("volver") != null || request.getParameter("aceptar") != null) {
			log.debug(" > Volver o aceptar");
			// Borrar los datos del grafico
			request.getSession().removeAttribute("datosGraficos");
			// Y luego grabar: 
			actualizarEnBD(request, predicciones, prediccionesAcumuladas);
			log.debug("En onSubmit de AsistenciaPeliculaDia, despues de actualiazarEnBD. PrediccionesAcumuladas.size = " + prediccionesAcumuladas.size());
		}
		// Al ponderar
		if (request.getParameter("p") != null) {
			log.debug(" > Ponderar");
			Iterator<Map<String, Object>> itGraf = graficos.iterator();
			Iterator<Prediccion> itPred = predicciones.iterator();

			// recuperamos los pesos desde el formulario
			AsistenciaPeliculaDiaForm form = (AsistenciaPeliculaDiaForm) command;
			String SpesosDias[][][] = form.getPesosDias();
			String SpesosPel[][] = form.getPesosPel();

			Integer nPel = (Integer) map.get("maxCantPeliculasValidas");
			Integer diasAPredecir = (Integer) map.get("dias_a_predecir");

			double pesosDias[][][] = new double[graficos.size()][nPel][diasAPredecir];
			double pesosPel[][] = new double[graficos.size()][nPel];

			int g = 0;
			// recorremos las predicciones
			log.debug("Recorriendo predicciones (List<Map<String,Object>> graficos) leyendo los nuevos pesos ");
			while (itGraf.hasNext()) {
				Map<String, Object>  grafico = itGraf.next();
				
				log.debug(" > Recorriendo elemento g:" + g);

				GeneradorXMLGrafico gen = (GeneradorXMLGrafico) grafico.get("genGraf");
				Iterator<SerieDatos> itSeries = gen.getDatos().iterator();

				int i = 0;
				SerieDatos serieDat;
				// recorremos las peliculas seleccionadas
				log.debug(" > Recorriendo series");
				while (itSeries.hasNext()) {
					serieDat = itSeries.next();
					log.debug(" > > Recorriendo serie i:" + i);
					if (!serieDat.esSerieResultado()) {
						// asignamos el peso por pelicula al formulario
						pesosPel[g][i] = Double.parseDouble(SpesosPel[g][i]);

						// La siguiente linea actualiza la ponderación dentro
						// del objetoPrediccionParametros al ponderar
						predicciones.get(g).getPrediccionParametros().get(i).setPonderacion(new Double(pesosPel[g][i]));
						
						if (map.get("overridePrediccionId") != null) {
							predicciones.get(g).setId((Integer.parseInt((String)map.get("overridePrediccionId"))));
						}

						Iterator<Double> itDias = serieDat.getPesosDias().iterator();
						int j = 0;
						@SuppressWarnings("unused")
						Double pDia;
						// recorremos los dias
						while (itDias.hasNext()) {
							pDia = itDias.next();
							// asignamos el peso por dia al formulario
							pesosDias[g][i][j] = Double.parseDouble(SpesosDias[g][i][j]);
							j++;
						}
					}
					i++;
				}
				g++;
			}

			itGraf = graficos.iterator();

			g = 0;
			// Recorremos las predicciones
			log.debug("Recorriendo de nuevo las predicciones para asignarlas");
			while (itGraf.hasNext() && itPred.hasNext()) {
				log.debug(" > Recorriendo elemento g:" + g);

				Map<String, Object> grafico = itGraf.next();
				GeneradorXMLGrafico gen = (GeneradorXMLGrafico) grafico.get("genGraf");

				// borramos la serie de datos de la pelicula a predecir
				List<SerieDatos> seriesDat = gen.getDatos();

				SerieDatos serieAnt = seriesDat.get(seriesDat.size() - 1);

				seriesDat.remove(seriesDat.size() - 1);

				SerieDatos serieDat;
				List<Double> pDias;

				// Asignamos los pesos a las peliculas y a los dias
				Iterator<SerieDatos> itDatos = seriesDat.iterator();
				int i = 0;
				log.debug(" > Recorriendo las series de datos.");
				while (itDatos.hasNext()) {
					serieDat = itDatos.next();
					log.debug(" > > Recorriendo la serie de datos i:" +i+" ("+serieDat.getNombreSerie()+")");
					serieDat.setPesoPel(new Double(pesosPel[g][i]));

					pDias = new ArrayList<Double>();
					for (int j = 0; j < diasAPredecir; j++) {
						pDias.add(new Double(pesosDias[g][i][j]));
					}
					serieDat.setPesosDias(pDias);

					i++;
				}
				// Calculamos los valores para la pelicula a predecir
				serieDat = new SerieDatos();
				log.debug(" > Llamando a SerieDatos.calcularResultado(seriesDat). seriesDat.size = " + seriesDat.size());
				List<Valor> valores = SerieDatos.calcularResultado(seriesDat);
				log.debug(" > Terminó calcularResultado. valores.size = " + valores.size());
				
				// modificar la prediccion
				Prediccion prediccion = itPred.next();
				Iterator<PrediccionPorDia> itPPDia = prediccion.getPrediccionPorDia().iterator();
				Iterator<Valor> itValor = valores.iterator();
				Iterator<Valor> itValorAnt = serieAnt.getValores().iterator();
				Valor vDia, vAnt;
				PrediccionPorDia ppDia;
				while (itValor.hasNext() && itPPDia.hasNext() && itValorAnt.hasNext()) {
					vDia = itValor.next();
					ppDia = itPPDia.next();
					vAnt = itValorAnt.next();
					vDia.setMarkers(vAnt.getMarkers());
					vDia.setInfo(vAnt.getInfo());
					ppDia.setEstimacion((int) vDia.getValor());
				}

				// guardar serie
				serieDat.setValores(valores);
				serieDat.setNombreSerie(serieAnt.getNombreSerie());
				serieDat.setSerieResultado();
				serieDat.setColor(serieAnt.getColor());
				seriesDat.add(serieDat);

				log.debug("Se guardó la serie en seriesDat. seriesDat.size = " + seriesDat.size());
				gen.aXML();
				g++;
			}
			// Finalmente, grabamos en la base de datos las predicciones, tal como están ahora, de modo 
			// que se pueda volver.

			actualizarEnBD(request, predicciones, prediccionesAcumuladas);
			//request.getSession().setAttribute("notNuevaPrediccion", true);

		} else if (request.getParameter("aceptar") != null) {
			// **************************** ACEPTAR ****************************
			// Al aceptar se debe generar las predicciones por funcion para cada
			// prediccion por dia de cada prediccion acumulada que ya tenemos
			// lista
			log.debug(" > ENTRANDO A Aceptar.");
			Iterator<Prediccion> itPred = prediccionesAcumuladas.iterator();
			// Recorremos las predicciones
			int i = 0;
			while (itPred.hasNext()) {
				log.debug("Predicción dentro de PrediccionesAcumuladas = " + i);
				Prediccion prediccion = itPred.next();
				// vemos si ya tiene predicciones por funcion
				// TODO Revisar este metodo.
				if (!prediccion.getHayPrediccionesPorFuncion()) {
					// Setear Dia estreno
					Parametro r = serviciosRM.obtenerParametro("Cine", "Dia_Estreno");
					int diaEstreno = Integer.parseInt(r.getCodigo());
					
					if(map.get("overridePrediccionId")!=null) {
						// Si viene desde prediccion incompleta, sólo habrá una predicción así que con toda seguridad le podemos poner esto
						prediccion.setId(Integer.parseInt((String)map.get("overridePrediccionId")));
					}

					// Obtener Fecha de estreno
					GregorianCalendar fechaPrimeraFuncion = new GregorianCalendar();
					Funcion primaFuncionSeleccionada = funcionDao.obtenerPrimeraFuncion(prediccion.getPelicula(),
						prediccion.getComplejo(), diaEstreno);
					if (primaFuncionSeleccionada != null)
						fechaPrimeraFuncion.setTime(primaFuncionSeleccionada.getFecha());
					else {
						fechaPrimeraFuncion.setTime(prediccion.getPrediccionPorDia().get(0).getFecha());
					}

					// Setear el rango
					r = serviciosRM.obtenerParametro("Revenue Manager", "funcion_dias_atras");
					int diasAtras = Integer.parseInt(r.getCodigo());
					r = serviciosRM.obtenerParametro("Revenue Manager", "funcion_dias_adelante");
					int diasAdelante = Integer.parseInt(r.getCodigo());

					GregorianCalendar fecha_inicio_rango = new GregorianCalendar();
					fecha_inicio_rango.setTime(fechaPrimeraFuncion.getTime());
					GregorianCalendar fecha_fin_rango = new GregorianCalendar();
					fecha_fin_rango.setTime(fechaPrimeraFuncion.getTime());
					fecha_inicio_rango.add(Calendar.DAY_OF_MONTH, -diasAtras);
					fecha_inicio_rango.add(Calendar.YEAR, -1);
					fecha_fin_rango.add(Calendar.DAY_OF_MONTH, diasAdelante);
					fecha_fin_rango.add(Calendar.YEAR, -1);

					int diferenciaDias = 0;
					Integer seleccion = (Integer) request.getAttribute("seleccion");
					if (seleccion == null) {
						seleccion = new Integer(1);
						request.setAttribute("seleccion", seleccion);
					}
					if (seleccion == 2) {
						// Fecha Actual
						GregorianCalendar fechaActual = new GregorianCalendar();
						fechaActual.setTime(Calendar.getInstance().getTime());
						diferenciaDias = Util.diferenciaFechas(fechaActual, fechaPrimeraFuncion);
						if (diferenciaDias < 0)
							diferenciaDias = 0;
					}
//					log.debug("diferenciaDias " + diferenciaDias);
//					log.debug("fecha_inicio_rango " + fecha_inicio_rango.getTime());
//					log.debug("fecha_fin_rango " + fecha_fin_rango.getTime());
//
//					log.debug("Se llamará a predecirDemandaPorFuncion(): ");
//					log.debug(" prediccion: " + prediccion.getId());
//					log.debug("   prediccion.ppd.size: " + prediccion.getPrediccionPorDia().size());
					prediccionManager.predecirDemandaPorFuncion(prediccion, diferenciaDias, fecha_inicio_rango, fecha_fin_rango);

					// Guardar cada prediccion por D�a
					Iterator<PrediccionPorDia> itPredDia = prediccion.getPrediccionPorDia().iterator();
					while (itPredDia.hasNext()) {
						PrediccionPorDia prediccionPorDia = itPredDia.next();
						//PrediccionPorDia ppdFromBD = itPredBD.next();
						
//						log.debug("Esta predicción por día tiene id: " + prediccionPorDia.getId());
						GregorianCalendar unDiaMas = (GregorianCalendar) DateUtils.obtenerCalendario(prediccionPorDia.getFecha());
						unDiaMas.add(GregorianCalendar.DAY_OF_MONTH, 1);
						// Agrego las predicciones por función de una predicción por día sólo si es que tiene estimación > 0
						if (!funcionDao.obtenerListaFuncionesComplejo(
							(GregorianCalendar) DateUtils.obtenerCalendario(
								prediccionPorDia.getFecha()),
								unDiaMas,
								prediccionPorDia.getPrediccion().getPelicula(), 
								prediccionPorDia.getPrediccion().getComplejo()).isEmpty()) {

							// Guardar cada prediccion por Funcion
							if (prediccionPorDia.getPrediccionesPorFuncion() != null) {
								Iterator<PrediccionPorFuncion> itPredFun = prediccionPorDia.getPrediccionesPorFuncion().iterator();
								while (itPredFun.hasNext()) {
									PrediccionPorFuncion prediccionPorFuncion = itPredFun.next();
									// log.debug(" prediccionPorFuncion.getMascara():" + prediccionPorFuncion.getMascara().getId());
									
									// Truco flaite para evitar que se caiga en ciertos momentos cuando varianza isNaN o infinito
									// en el caso cuando la estimación del día es 0 (al calcular la varianza se divide por cero y el 
									// universo implota en sí mismo)
									
									if (Double.isNaN(prediccionPorFuncion.getVarianza()) || Double.isInfinite(prediccionPorFuncion.getVarianza()) ) {
										prediccionPorFuncion.setVarianza(0.0);
									}
									
									prediccionPorFuncion.setId(prediccionDao.agregarPrediccionPorFuncion(prediccionPorFuncion));

								}
							}
						}
					}
					// Guardar finalmente los parámetros con los que se realizó
					// esta predicción
					/*
					 * Iterator itParametros =
					 * prediccion.getPrediccionParametros().iterator(); while
					 * (itParametros.hasNext()) { PrediccionParametros
					 * thisPrediccionParametros = (PrediccionParametros)
					 * itParametros.next();
					 * prediccionDao.agregarPrediccionParametros
					 * (thisPrediccionParametros); }
					 */
					// Parece que lo estaba haciendo dos veces
				}
				i++;
			}

			return new ModelAndView(new RedirectView("predicciones.htm"));
		} else if (request.getParameter("volver") != null) {
			log.debug(" > Volver");
			// redireccionar a donde se origino el pedido
			Integer seleccion = (Integer) request.getSession().getAttribute("seleccion");
			log.debug(seleccion == null ? ("seleccion == null") : ("seleccion != null"));
			if (seleccion == null) {
				seleccion = new Integer(1);
				request.setAttribute("seleccion", seleccion);
			}
			log.debug("# Seleccion = " + seleccion);
			if (seleccion == 1)
				return new ModelAndView(new RedirectView("seleccionarpeliculas.htm"));
			else if (seleccion == 2)
				return new ModelAndView(new RedirectView("seleccionarpeliculas2.htm"));
			else if (seleccion == 3)
				return new ModelAndView(new RedirectView("prediccionincompleta.htm"));
			else 
				return new ModelAndView(new RedirectView("seleccionarpeliculas.htm"));

		} else if (Integer.parseInt(request.getParameter("borrarPeliculaX")) >= 0
				&& Integer.parseInt(request.getParameter("borrarPeliculaY")) >= 0) {

			log.debug(" > Borrar Pelicula.");

			// Se eliminará la película nº borrarPelicula del gráfico (i.e., complejo) nº borrarFromGrafico
			int borrarFromGrafico = Integer.parseInt(request.getParameter("borrarPeliculaX"));
			int borrarPelicula = Integer.parseInt(request.getParameter("borrarPeliculaY"));
			
			PrediccionParametros prediccionParametrosABorrar = predicciones.get(borrarFromGrafico).getPrediccionParametros().get(borrarPelicula);
			
			// Se elimina de la base de datos
			if (map.get("idPrediccion")!=null)
				prediccionParametrosABorrar.getPrediccion().setId(Integer.parseInt((String)map.get("idPrediccion")));
			
			prediccionDao.eliminarPrediccionParametros(prediccionParametrosABorrar);
			
			// La siguiente línea remueve el PrediccionParametros de la película que se eliminó
			predicciones.get(borrarFromGrafico).getPrediccionParametros().remove(borrarPelicula);
			// Más adelante, se actualiza el valor de ponderación que tendrán las que quedan
			
			Map<String, Object> grafico = graficos.get(borrarFromGrafico);
			
			/* grafico contiene:
			 *  - GeneradorXMLGrafico genGraf*
			 *  - Integer cantPeliculasValidas*
			 *  - String xmlGraf*
			 *  - String complejo
			 *  - GregorianCalendar fechaEstreno
			 *  Los que tienen * son los que hay que actualizar al borrar una película. */
  
			// Borrar la película de los datos del genGRaf
			GeneradorXMLGrafico genGraf = (GeneradorXMLGrafico) graficos.get(borrarFromGrafico).get("genGraf");
			genGraf.getDatos().remove(borrarPelicula);
			
			// Generar un nuevo gráfico con los datos que quedan
			// TODO: pasarlo a Factory
			ArchivoGrafico graXML = new ArchivoGrafico(request, "asistencia"
					+ (((String) graficos.get(borrarFromGrafico).get("complejo")).replace(" ", ""))
					+ borrarFromGrafico);
			genGraf.setRutaArchivo(graXML.rutaGraficoInterna());
			String nuevoXML = genGraf.aXML();
			String viejoXML = (String) grafico.get("xmlGraf");
			viejoXML = nuevoXML;
			
			//Actualizar la cantidad de películas válidas (1 menos)
			Integer nuevaCantidadPeliculasValidas = (Integer) graficos.get(borrarFromGrafico).get("cantPeliculasValidas") - 1;
			graficos.get(borrarFromGrafico).put("cantPeliculasValidas", nuevaCantidadPeliculasValidas);

			// Borrarla también en el gráfico de asistencia días anteriores, si es que existe
			if (graficos.get(borrarFromGrafico).get("genGrafAnt") != null) {
				log.debug("Borrando en el gráfico de asistencia días anteriores");
				GeneradorXMLGrafico genGrafAnt = (GeneradorXMLGrafico) graficos.get(borrarFromGrafico).get("genGrafAnt");
				genGrafAnt.getDatos().remove(borrarPelicula);
				ArchivoGrafico graXML2 = new ArchivoGrafico(request, "asistenciaAnt"
						+ (((String) graficos.get(borrarFromGrafico).get("complejo")).replace(" ", ""))
						+ request.getParameter("borrarPeliculaX"));
				genGrafAnt.setRutaArchivo(graXML2.rutaGraficoInterna());
				genGrafAnt.aXML();
			}
			
			// Cambiar los pesos de todas las películas que quedan, proporcionalmente
			// Reponderar repondero
			int indicePeliculasParametros = 0;
			Iterator itDatos = genGraf.getDatos().iterator();
			while (itDatos.hasNext()) {
				SerieDatos thisSerieDatos = (SerieDatos)itDatos.next();
				if (itDatos.hasNext()) { //Esto es para saltarse la última serie, que es la película misma
					Double nuevoPesoPel = thisSerieDatos.getPesoPel();
					if (genGraf.getDatos().size() == 1) {
						nuevoPesoPel = new Double(1.0);
					} else {
						nuevoPesoPel = new Double(nuevoPesoPel * (new Double(nuevaCantidadPeliculasValidas.intValue() + 1.0))
								/ new Double(nuevaCantidadPeliculasValidas.intValue()));
					}
					PrediccionParametros pParamToUpdate = new PrediccionParametros();
					pParamToUpdate.setPelicula(predicciones.get(borrarFromGrafico).getPrediccionParametros().get(indicePeliculasParametros).getPelicula());
					pParamToUpdate.setPrediccion(prediccionParametrosABorrar.getPrediccion());
					if (map.get("overridePrediccionId")!= null) {
						pParamToUpdate.setPrediccion(prediccionDao.obtenerPrediccion((Integer.parseInt((String)map.get("overridePrediccionId")))));
					}
					pParamToUpdate.setPonderacion(nuevoPesoPel);
					prediccionDao.actualizarPrediccionParametros(pParamToUpdate);
					thisSerieDatos.setPesoPel(nuevoPesoPel);
				} 
				indicePeliculasParametros++;
			}
			// Y finalmente actualizar la estimación en base a los nuevos pesos. 

			// recuperamos los pesos desde el formulario
			AsistenciaPeliculaDiaForm form = (AsistenciaPeliculaDiaForm) command;
			String SpesosDias[][][] = form.getPesosDias();

			Integer nPel = (Integer) map.get("maxCantPeliculasValidas");
			Integer diasAPredecir = (Integer) map.get("dias_a_predecir");

			double pesosDias[][][] = new double[graficos.size()][nPel][diasAPredecir];			

			int indexSeries = 0;
			Iterator itSeries = genGraf.getDatos().iterator();

			// Reasignar pesos por días
			while (itSeries.hasNext()) {
				SerieDatos serieDat = (SerieDatos) itSeries.next();
				if (!serieDat.esSerieResultado()) {
					Iterator<Double> itDias = serieDat.getPesosDias().iterator();
					int indexDias = 0;
					while (itDias.hasNext()) {
						itDias.next();
						pesosDias[borrarFromGrafico][indexSeries][indexDias] = Double.parseDouble(SpesosDias[borrarFromGrafico][indexSeries][indexDias]);
						indexDias++;
					}
				}
				indexSeries++;
			}
			
			// Borrar la última serie, que tiene datos desactualizados
			List<SerieDatos> seriesDat = genGraf.getDatos();
			SerieDatos serieAnt = seriesDat.get(seriesDat.size() - 1);
			seriesDat.remove(seriesDat.size() - 1);

			SerieDatos serieDat;
			List<Double> pDias;
			Iterator<SerieDatos> itSeriesDat = seriesDat.iterator();
			
			int i = 0;
			while (itSeriesDat.hasNext()) {
				serieDat = itSeriesDat.next();
				pDias = new ArrayList<Double>();
				for (int j = 0; j < diasAPredecir; j++) {
					pDias.add(new Double(pesosDias[borrarFromGrafico][i][j]));
				}
				serieDat.setPesosDias(pDias);
				i++;
			}
			
			// Calculamos los valores para la pelicula a predecir
			serieDat = new SerieDatos();
			List<Valor> valores = SerieDatos.calcularResultado(seriesDat);

			// modificar la prediccion
			Prediccion prediccion = predicciones.get(borrarFromGrafico);
			Iterator<PrediccionPorDia> itPPDia = prediccion.getPrediccionPorDia().iterator();
			Iterator<Valor> itValor = valores.iterator();
			Iterator<Valor> itValorAnt = serieAnt.getValores().iterator();
			Valor vDia, vAnt;
			PrediccionPorDia ppDia;
			while (itValor.hasNext() && itPPDia.hasNext() && itValorAnt.hasNext()) {
				vDia = itValor.next();
				ppDia = itPPDia.next();
				vAnt = itValorAnt.next();
				vDia.setMarkers(vAnt.getMarkers());
				vDia.setInfo(vAnt.getInfo());
				ppDia.setEstimacion((int) vDia.getValor());
			}
			
			// guardar serie
			serieDat.setValores(valores);
			serieDat.setNombreSerie(serieAnt.getNombreSerie());
			serieDat.setSerieResultado();
			serieDat.setColor(serieAnt.getColor());
			seriesDat.add(serieDat);
			
			//Finally
			genGraf.setDatos(seriesDat);
			
			/*
			// THE BIG DUMP of seriesDat
			Iterator<SerieDatos> itSeriesDatDump = seriesDat.iterator();
			while (itSeriesDatDump.hasNext()) {
				SerieDatos thisSerieDatos = itSeriesDatDump.next();
				String nombreSerie = thisSerieDatos.getNombreSerie();
				Double pesoPel = thisSerieDatos.getPesoPel();
				log.debug(seriesDat.indexOf(thisSerieDatos) + ". " + nombreSerie + ": peso " + pesoPel);
				
				
				List<Double> pesosDiasDump = thisSerieDatos.getPesosDias();
				Iterator<Double> itPesosDiasDump = pesosDiasDump.iterator();
				while(itPesosDiasDump.hasNext()) {
					log.debug(" pesosDias: " + itPesosDiasDump.next());
				}				
				
				List<Valor> valoresDump = thisSerieDatos.getValores();
				Iterator<Valor> itValoresDump = valoresDump.iterator();
				while (itValoresDump.hasNext()) {
					log.debug(" valor: " + itValoresDump.next().getValor());
				}
				List<Valor> valoresPonderadosDump = thisSerieDatos.getValoresPonderados();
				Iterator<Valor> itValoresPonderadosDump = valoresPonderadosDump.iterator();
				while (itValoresPonderadosDump.hasNext()) {
					log.debug(" valoresPonderados: " + itValoresPonderadosDump.next().getValor());
				}	
			}
			// Este dump provoca un NullPointerException; está bien.
			//*/
			
			//////////////////////////////////////////////////////////////////////
			
			AsistenciaPeliculaDiaForm ourForm = (AsistenciaPeliculaDiaForm) command;
			ourForm.setBorrarPeliculaX(-1);
			ourForm.setBorrarPeliculaY(-1);
			
			// Finalmente, actualizar en BD
			// actualizarEnBD(request, predicciones, prediccionesAcumuladas);

			// request.getSession().setAttribute("notNuevaPrediccion", true);

			
		} else {
			log.debug("Entrando a else (eliminar).");
			// Eliminar algun grafico
			int nGraf = graficos.size();
			log.debug("nGraf = " + nGraf);
			for (int i = 0; i < nGraf; i++) {
				log.debug(" Revisando el gráfico nº" + i);
				log.debug("  El parámetro e" + i + (request.getParameter("e" + i) != null? " está seteado" : " es nulo"));
				if (request.getParameter("e" + i) != null) {
					log.debug("  Entrando a eliminar la predicción.");
					graficos.remove(i);
					Prediccion prediccionARemover = predicciones.get(i);
					
					// Ahora, como regalo, lo vamos a borrar de la BD, si corresponde
					if (map.get("idPrediccion") != null) {
						prediccionARemover.setId(Integer.parseInt((String)map.get("idPrediccion")));
						log.debug("Se intentará eliminar de la BD la prediccion " + prediccionARemover.getId());
						prediccionDao.eliminarPrediccion(prediccionARemover);
					}
					predicciones.remove(i);
					break;
				}
			}
			return new ModelAndView(new RedirectView("seleccionarpeliculas.htm"));
		}

		request.getSession().setAttribute("datosGraficos", map);

		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute("datosGraficos");
		return map;
	}
	
	@SuppressWarnings("unchecked")
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		log.debug("Iniciando AsistenciaPeliculaDiaController:formBackingObject...");
		AsistenciaPeliculaDiaForm asistenciaPeliculaDiaria = new AsistenciaPeliculaDiaForm();
		// buscamos los parametros que necesitamos de sesion
		Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute("datosGraficos");
		List<Map<String, Object>> graficos;
		CopyOnWriteArrayList<Prediccion> predicciones;
		if (map != null) {
			log.debug("  flujo: map is null");
			graficos = (List<Map<String, Object>>) map.get("graficos");
			if (graficos == null){
				graficos = new ArrayList<Map<String, Object>>();
			}
			predicciones = (CopyOnWriteArrayList<Prediccion>) map.get("predicciones");
			if (predicciones == null) {
				predicciones = new CopyOnWriteArrayList<Prediccion>();
			}
		} 
		else {
			log.debug("  flujo: map is not null");
			map = new HashMap<String, Object>();
			graficos = new ArrayList<Map<String, Object>>();
			predicciones = new CopyOnWriteArrayList<Prediccion>();
			map.put("graficos", graficos);
			map.put("predicciones", predicciones);
			map.put("dias_a_predecir", new Integer(0));
			map.put("maxCantPeliculasValidas", new Integer(0));
			request.getSession().setAttribute("datosGraficos", map);
		}

		if (graficos != null) {
			log.debug("  flujo: graficos is not null");
			Iterator<Map<String, Object>> itGraf = graficos.iterator();

			final Integer nPel = (Integer) map.get("maxCantPeliculasValidas");
			final Integer diasAPredecir = (Integer) map.get("dias_a_predecir");

//			log.debug("tamanho: g:" + graficos.size() + " i:" + nPel + " j:" + diasAPredecir);
			
			String[][][] pesosDias = new String[graficos.size()][nPel][diasAPredecir];
			String[][] pesosPel = new String[graficos.size()][nPel];

			int g = 0;
			// recorremos las predicciones
			while (itGraf.hasNext()) {
				Map<String, Object> grafico = itGraf.next();

				GeneradorXMLGrafico gen = (GeneradorXMLGrafico) grafico.get("genGraf");
				Iterator<SerieDatos> itSeries = gen.getDatos().iterator();

				int i = 0;
				SerieDatos serieDat;
//				log.debug("cant pel validas" + gen.getDatos().size());
				// recorremos las peliculas seleccionadas
				while (itSeries.hasNext()) {
					serieDat = itSeries.next();

					if (!serieDat.esSerieResultado()) {
//						log.debug("!serieDat.esSerieResultado(): " + serieDat.getNombreSerie());
						// asignamos el peso por pelicula al formulario
						// log.debug("indices g:" + g + " i:" + i);

						pesosPel[g][i] = serieDat.getPesoPel().toString();
						
//						log.debug("pesosPel[" + g + "][" + i + "]: " + pesosPel[g][i]);

						Iterator<Double> itDias = serieDat.getPesosDias().iterator();
						int j = 0;
						Double pDia;
						// recorremos los dias
						while (itDias.hasNext()) {
							pDia = itDias.next();
							// asignamos el peso por dia al formulario
							pesosDias[g][i][j] = pDia.toString();
							j++;
						}
					}
					i++;
				}
				g++;
			}

			asistenciaPeliculaDiaria.setPesosDias(pesosDias);
			asistenciaPeliculaDiaria.setPesosPel(pesosPel);
		}
		asistenciaPeliculaDiaria.setBorrarPeliculaX(-1);
		asistenciaPeliculaDiaria.setBorrarPeliculaY(-1);
		
		CopyOnWriteArrayList<Prediccion> prediccionesAcumuladas = new CopyOnWriteArrayList<Prediccion>();
		if (request.getSession().getAttribute("notNuevaPrediccion") == null) {
			log.debug("  flujo: request.getSession().getAttribute('notNuevaPrediccion') is null");
			log.debug("formBackingObject: notNuevaPrediccion no está seteado.");
			this.grabarEnBD(request, predicciones, prediccionesAcumuladas);
		} 
		else {
			log.debug("  flujo: request.getSession().getAttribute('notNuevaPrediccion') is not null");
			log.debug("formBackingObject: notNuevaPrediccion está seteado.");
			if (map.get("overridePrediccionId")!= null) {
				log.debug(" formBackingObject: overridePrediccionId está seteado");
				int prediccionId = Integer.parseInt((String)map.get("overridePrediccionId"));
				map.remove("predicciones");
				CopyOnWriteArrayList<Prediccion> newPredicciones = new CopyOnWriteArrayList<Prediccion>();
				newPredicciones.add(prediccionDao.obtenerPrediccion(prediccionId));
				map.put("predicciones", newPredicciones);
			}
			//request.getSession().removeAttribute("notNuevaPrediccion");
		}
		log.debug("acumuladas:" + ((CopyOnWriteArrayList<Prediccion>)request.getSession().getAttribute("predicciones")) !=null ? ((CopyOnWriteArrayList<Prediccion>)request.getSession().getAttribute("predicciones")).size() : " no existe predicciones acumuladas");
		return asistenciaPeliculaDiaria;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object arg0, BindException arg1) throws Exception {
		AsistenciaPeliculaDiaForm form = (AsistenciaPeliculaDiaForm) arg0;

		Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute("datosGraficos");

		List<Map<String, Object>> graficos;
		graficos = (List<Map<String, Object>>) map.get("graficos");

		Iterator<Map<String, Object>> itGraf = graficos.iterator();

		String SpesosDias[][][] = form.getPesosDias();
		String SpesosPel[][] = form.getPesosPel();

		Integer nPel = (Integer) map.get("maxCantPeliculasValidas");
		Integer diasAPredecir = (Integer) map.get("dias_a_predecir");

		double pesosDias[][][] = new double[graficos.size()][nPel][diasAPredecir];
		double pesosPel[][] = new double[graficos.size()][nPel];

		int g = 0;
		// recorremos las predicciones
		while (itGraf.hasNext()) {
			Map<String, Object> grafico = itGraf.next();
			GeneradorXMLGrafico gen = (GeneradorXMLGrafico) grafico.get("genGraf");
			Iterator<SerieDatos> itSeries = gen.getDatos().iterator();

			int i = 0;
			SerieDatos serieDat;
			// recorremos las peliculas seleccionadas
			while (itSeries.hasNext()) {
				serieDat = itSeries.next();

				if (!serieDat.esSerieResultado()) {
					// asignamos el peso por pelicula al formulario
					try {
						pesosPel[g][i] = Double.parseDouble(SpesosPel[g][i]);
					} catch (NumberFormatException e) {
						arg1.rejectValue("pesosPel", null, "Ponderacion de pelicula " + serieDat.getNombreSerie()
								+ " no es un numero para el complejo " + (String) grafico.get("complejo"));
						return;
					}

					Iterator<CategoriaGrafico> itCat = gen.getCategorias().iterator();
					int j = 0;
					CategoriaGrafico cat;
					// recorremos los dias
					while (itCat.hasNext()) {
						cat = itCat.next();
						// asignamos el peso por dia al formulario
						try {
							pesosDias[g][i][j] = Double.parseDouble(SpesosDias[g][i][j]);
						} catch (NumberFormatException e) {
							arg1.rejectValue("pesosPel", null, "Ponderacion de dia " + cat.getNombreCategoria() + " de la pelicula "
									+ serieDat.getNombreSerie() + " no es un numero para el complejo "
									+ (String) grafico.get("complejo"));
							return;
						}
						j++;
					}
				}
				i++;
			}
			g++;
		}

		// arg1.rejectValue( "pesosPel", "error.pesosPel");

		// ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "pesosPel",
		// "error.dias_a_predecir");
	}

	private void actualizarEnBD(HttpServletRequest request, CopyOnWriteArrayList<Prediccion> predicciones, CopyOnWriteArrayList<Prediccion> prediccionesAcumuladas) {
		log.debug("Entrando a actualizar en BD. ");
		for (Prediccion prediccion : predicciones) {
			int prediccionAcumuladaABorrar = -1;
			int indice = 0;
			for (Prediccion prediccionAcumulada : prediccionesAcumuladas) {
				if (prediccionAcumulada.getId() == prediccion.getId()) {
					prediccionAcumuladaABorrar = indice;
				}
				indice++;
			}
			if (prediccionAcumuladaABorrar >= 0) {
				prediccionesAcumuladas.remove(prediccionAcumuladaABorrar);
			}
			
			if (request.getSession().getAttribute("notNuevaPrediccion") == null) {
				prediccionesAcumuladas.add(prediccion); //the culprit!
				// Cuando venimos de predicciones incompletas, y no del método normal,
				// no agregamos la predicción a las acumuladas, porque ya fue agregada antes en
				// PrediccionIncompletaController.
			}
			
			prediccionDao.actualizarPrediccion(prediccion);
			
			// Guardar cada prediccion por Día
			for (PrediccionPorDia thisPPD : prediccion.getPrediccionPorDia()) {
				prediccionDao.actualizarPrediccionPorDia(thisPPD);
			}
			// Guardar también sus parámetros
			for (PrediccionParametros thisPParam : prediccion.getPrediccionParametros()) {
				prediccionDao.actualizarPrediccionParametros(thisPParam);
			}
		}
	}
	
	private void grabarEnBD(HttpServletRequest request, CopyOnWriteArrayList<Prediccion> predicciones, CopyOnWriteArrayList<Prediccion> prediccionesAcumuladas) {
		log.debug("Entrando a grabarEnBD.");

		// Guardar cada prediccion
		Iterator<Prediccion> itPred = predicciones.iterator();
		while (itPred.hasNext()) {
			Prediccion prediccion = itPred.next();
			log.debug("PrediccionId : [" + prediccion.getId() + "]");
			if (prediccion.getId() > 0) {
				continue;
			}
			prediccionesAcumuladas.add(prediccion);
			prediccion.setId(prediccionDao.agregarPrediccion(prediccion));
			log.debug("+ Guardando predicción: " + prediccion.getId() + " " + prediccion.getPelicula().getNombre());

			// Guardar cada prediccion por Dia
			Iterator<PrediccionPorDia> itPredDia = prediccion.getPrediccionPorDia().iterator();
			while (itPredDia.hasNext()) {
				PrediccionPorDia prediccionPorDia = itPredDia.next();
				prediccionPorDia.setId(prediccionDao.agregarPrediccionPorDia(prediccionPorDia));
				log.debug("+ + Guardando predicción por día " + prediccionPorDia.getId() + ": "
						+ Util.DateToString(prediccionPorDia.getFecha()) + ": " + prediccionPorDia.getEstimacion());
			}

			// Guardar también sus parámetros
			Iterator<PrediccionParametros> itPrediccionParametros = prediccion.getPrediccionParametros().iterator();
			while (itPrediccionParametros.hasNext()) {
				PrediccionParametros thisPrediccionParametros = itPrediccionParametros.next();
				log.debug("+ + Guardar parametros:\n" + "PrediccionId: " + thisPrediccionParametros.getPrediccion().getId()
						+ "\nPeliculaId: + " + thisPrediccionParametros.getPelicula().getId() + "\nPonderacionm: "
						+ thisPrediccionParametros.getPonderacion());
				try {
					prediccionDao.agregarPrediccionParametros(thisPrediccionParametros);
				} catch (Exception e) {
					log.debug("No se pudo agregar los parámetros de la predicción : " + ErroresUtils.extraeStackTrace(e));
					// log.debug("No se pudo agregar los parámetros de la predicción.");
				}
			}
		}

	}

	public void setPrediccionManager(PrediccionManager prediccionManager) {
		this.prediccionManager = prediccionManager;
	}
	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}
	public void setPrediccionDao(PrediccionDao prediccionDao) {
		this.prediccionDao = prediccionDao;
	}
	public void setServiciosRM(ServiciosRevenueManager serviciosRM) {
		this.serviciosRM = serviciosRM;
	}
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}
	public void setMascaraDao(MascaraDao mascaraDao) {
		this.mascaraDao = mascaraDao;
	}
}
