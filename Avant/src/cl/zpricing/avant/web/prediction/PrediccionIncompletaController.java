package cl.zpricing.avant.web.prediction;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.model.Parametro;
import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.model.prediction.PrediccionIncompleta;
import cl.zpricing.avant.model.prediction.PrediccionParametros;
import cl.zpricing.avant.model.prediction.PrediccionPorDia;
import cl.zpricing.avant.model.prediction.PrediccionPorFuncion;
import cl.zpricing.avant.negocio.PrediccionManager;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.EpocaDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.MarkerDao;
import cl.zpricing.avant.servicios.MascaraDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.servicios.PrediccionDao;
import cl.zpricing.avant.servicios.PublicoDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.avant.servicios.UsuarioDao;
import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.chart.CategoriaGrafico;
import cl.zpricing.avant.web.chart.Color;
import cl.zpricing.avant.web.chart.ColorSerie;
import cl.zpricing.avant.web.chart.Dia;
import cl.zpricing.avant.web.form.PrediccionIncompletaForm;
import cl.zpricing.commons.utils.DateUtils;

/**
 * <b>Descripci�n de la Clase</b> Controlador que maneja la vista de
 * predicciones incompletas
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 26-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class PrediccionIncompletaController extends MultiActionController {
	private PrediccionDao prediccionDao;
	private UsuarioDao usuarioDao;
	private FuncionDao funcionDao;
	private PeliculaDao peliculaDao;
	private PrediccionManager prediccionManager;
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private ServiciosRevenueManager serviciosRM;
	private ComplejoDao complejoDao;
	private EpocaDao epocaDao;
	private MarkerDao markerDao;
	private PublicoDao publicoDao;
	private MascaraDao mascaraDao;

	/**
	 * <P>
	 * Método ejecutado cuando se quiere predecir una predicción incompleta
	 * sin predicciones por función.
	 * </p>
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26/01/2009 Oliver: Versión Inicial</li>
	 * <li>1.1 25/12/2009 Camilo Araya: versión MultiActionController</li>
	 * </ul>
	 * </P>
	 * 
	 * @param command
	 *            Para obtener valores del formulario.
	 * @return Retorna la vista exitosa
	 * @throws ServletException
	 *             Excepcion general por si un servlet encuentra problemas.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView onSubmitPSPF(HttpServletRequest request,
			HttpServletResponse response, PrediccionIncompletaForm command)
			throws Exception {
		PrediccionIncompletaForm form = (PrediccionIncompletaForm) command;
		log.debug("onSubmitPSPF has been called.");

		log.debug(this.getClass());
		CopyOnWriteArrayList<Complejo> listaDeComplejos = new CopyOnWriteArrayList<Complejo>();
		CopyOnWriteArrayList<Prediccion> listaDePredicciones = new CopyOnWriteArrayList<Prediccion>();
		if (form.getPrediccionFuncion() != null) {
			for (int i = 0; i < form.getPrediccionFuncion().length; i++) {
				Prediccion thisPrediccion = prediccionDao
						.obtenerPrediccion(Integer.parseInt(form
								.getPrediccionFuncion()[i]));
				if (!listaDeComplejos.contains(thisPrediccion.getComplejo())) {
					listaDeComplejos.add(thisPrediccion.getComplejo());
				}
				listaDePredicciones.add(thisPrediccion);
			}
		}

		// i. Parámetros comunes
		List<Map<String, Object>> graficos = new ArrayList<Map<String, Object>>();
		CopyOnWriteArrayList<Prediccion> predicciones = new CopyOnWriteArrayList<Prediccion>();
		Integer maxCantPeliculasValidas = new Integer(0);
		String user = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		Usuario usuario = null;
		usuario = usuarioDao.obtenerUsuarioByName(user);
		List<ColorSerie> colores = new ArrayList<ColorSerie>();
		ColorSerie color;
		Color.iniciarColor();
		List<String> graficosMalos = new ArrayList<String>();
		Parametro r = serviciosRM.obtenerParametro("Cine", "Dia_Estreno");
		int diaEstreno = Integer.parseInt(r.getCodigo());

		// ii. Parámetros propios de cada predicción
		for (int j = 0; j < listaDePredicciones.size(); j++) {
			log.debug("Recorriendo listaDePredicciones. Now on j="+j);
			int idPeliculaAPredecir = prediccionDao.obtenerPrediccion(
					Integer.parseInt(form.getPrediccionFuncion()[j]))
					.getPelicula().getId();

			int[] idPeliculasSeleccionadas = new int[prediccionDao
					.obtenerParametrosPrediccion(
							Integer.parseInt(form.getPrediccionFuncion()[j]))
					.size()];

			int indiceParametros = 0;
			for (PrediccionParametros thisPrediccionParametros : prediccionDao
					.obtenerParametrosPrediccion(Integer.parseInt(form
							.getPrediccionFuncion()[j]))) {
				idPeliculasSeleccionadas[indiceParametros] = thisPrediccionParametros
						.getPelicula().getId();
				indiceParametros++;
			}

			int cantidadDiasAPredecir = prediccionDao
					.obtenerCantidadDiasPrediccion(Integer.parseInt(form
							.getPrediccionFuncion()[j]));
			String fechaDesdeString = Util.DateToString(prediccionDao
					.obtenerFechaDesdePrediccion(prediccionDao
							.obtenerPrediccion(Integer.parseInt(form
									.getPrediccionFuncion()[j]))));
			Complejo complejo = listaDePredicciones.get(j).getComplejo();

//			log.debug("Creando las categoriasGraficos:");
			List<CategoriaGrafico> categoriasGraficos = new ArrayList<CategoriaGrafico>();
			GregorianCalendar fechaInicioPrediccionCal = (GregorianCalendar) DateUtils
					.obtenerCalendario(prediccionDao
							.obtenerFechaDesdePrediccion(prediccionDao
									.obtenerPrediccion(Integer.parseInt(form
											.getPrediccionFuncion()[j]))));
//			log.debug("fechaInicioPrediccionCal: "
//					+ Util.DateToString(fechaInicioPrediccionCal.getTime()));
			GregorianCalendar fechaEstrenoCal = (GregorianCalendar) DateUtils
					.obtenerCalendario(funcionDao.obtenerPrimeraFuncion(
							peliculaDao.obtenerPelicula(idPeliculaAPredecir),
							complejo, diaEstreno).getFecha());
//			log.debug("fechaEstrenoCal: "
//					+ Util.DateToString(fechaEstrenoCal.getTime()));
			int diaEnQuePartimosLaPrediccion = Util.diferenciaFechas(
					fechaInicioPrediccionCal, fechaEstrenoCal) + 1;

			GregorianCalendar diaCal = new GregorianCalendar();
			diaCal.setTime(fechaInicioPrediccionCal.getTime());
			for (int i = diaEnQuePartimosLaPrediccion; i < cantidadDiasAPredecir
					+ diaEnQuePartimosLaPrediccion; i++) {
				String categoria = "";
				categoria += Dia.getDia(diaCal
						.get(GregorianCalendar.DAY_OF_WEEK));
				categoria += i;
				categoriasGraficos.add(new CategoriaGrafico(categoria));
				diaCal.add(GregorianCalendar.DAY_OF_MONTH, 1);
			}

			for (int i = 0; i < idPeliculasSeleccionadas.length; i++) {
				color = new ColorSerie();
				color.setIdSerie(idPeliculasSeleccionadas[i]);
				color.setColor(Color.generarColor());
				colores.add(color);
			}

			color = new ColorSerie();
			color.setIdSerie(idPeliculaAPredecir);
			color.setColor(Color.generarColor());
			colores.add(color);

			ArrayList<PrediccionParametros> overridePesos = (ArrayList<PrediccionParametros>) prediccionDao
					.obtenerParametrosPrediccion(Integer.parseInt(form
							.getPrediccionFuncion()[j]));

			Date fechaEnQueSeHizoLaPrediccion = prediccionDao
					.obtenerPrediccion(
							Integer.parseInt(form.getPrediccionFuncion()[j]))
					.getFecha();
			request.getSession().setAttribute("overrideFecha",
					fechaEnQueSeHizoLaPrediccion);

			// Decidir si lo mando a seleccionarPelicula o seleccioanrPelicula2
			// Controller en base a las fechas
//			log
//					.debug("Decidiendo si es película estreno o película estrenada.");

			// PEQUEÑO DUMP PARA DEBUG
//			log.debug("Comparando " + idPeliculaAPredecir + " con: ");
//			for (int id : idPeliculasSeleccionadas) {
//				log.debug("+" + id + ": "
//						+ peliculaDao.obtenerPelicula(id).getNombre());
//			}
			// FIN DEL PEQUEÑO DUMP

			if (idPeliculasSeleccionadas[idPeliculasSeleccionadas.length - 1] != idPeliculaAPredecir) {

				// Pelicula Estreno; llamar a SeleccionarPeliculaController
				SeleccionarPeliculaController spc = new SeleccionarPeliculaController();

				spc.setComplejoDao(complejoDao);
				spc.setPeliculaDao(peliculaDao);
				spc.setFuncionDao(funcionDao);
				spc.setServiciosRM(serviciosRM);
				spc.setEpocaDao(epocaDao);
				spc.setMarkerDao(markerDao);
				spc.setPrediccionManager(prediccionManager);
				spc.setPublicoDao(publicoDao);

//				log
//						.debug("Se llamara a spc.realizarPrediccion() con los siguientes parámetros:");
//				log.debug("idPeliculaAPredecir: " + idPeliculaAPredecir);
//				String foo = "";
//				for (int i : idPeliculasSeleccionadas)
//					foo += i + " ";
//				log.debug("idPeliculasSeleccionadas: " + foo);
//				log.debug("cantidadDiasAPredecir: " + cantidadDiasAPredecir);
//				log.debug("fechaDesdeString: " + fechaDesdeString);
//				log
//						.debug("maxCantPeliculasValidas: "
//								+ maxCantPeliculasValidas);
//				log.debug("diaEstreno: " + diaEstreno);
				// log.debug("categoriasGraficos: " + categoriasGraficos);
				// graficos, predicciones, graficosMalos, j, complejo,
				// overridePesos
				// Now we're ready
				maxCantPeliculasValidas = new Integer(spc.realizarPrediccion(
						request, idPeliculaAPredecir, idPeliculasSeleccionadas,
						cantidadDiasAPredecir, fechaDesdeString,
						maxCantPeliculasValidas, usuario, colores, diaEstreno,
						categoriasGraficos, graficos, predicciones,
						graficosMalos, j, complejo, overridePesos));
				request.getSession().removeAttribute("overrideFecha");
			} else {
				// Pelicula Estrenada; llamar a SeleccionarPelicula2Controller
				SeleccionarPelicula2Controller spc = new SeleccionarPelicula2Controller();
				spc.setComplejoDao(complejoDao);
				spc.setPeliculaDao(peliculaDao);
				spc.setFuncionDao(funcionDao);
				spc.setServiciosRM(serviciosRM);
				spc.setEpocaDao(epocaDao);
				spc.setMarkerDao(markerDao);
				spc.setPrediccionManager(prediccionManager);
				spc.setPublicoDao(publicoDao);
				spc.setPrediccionDao(prediccionDao);

				GregorianCalendar fechaPredecirDesde = new GregorianCalendar();
				fechaPredecirDesde.setTime(Util.StringToDate(fechaDesdeString));
				int[] idComplejos = new int[listaDeComplejos.size()];
				int indiceComplejos = 0;
				for (Complejo c : listaDeComplejos) {
					idComplejos[indiceComplejos] = c.getId();
					indiceComplejos++;
				}
				// Borrar la última película en este caso
				int[] idPeliculasSeleccionadasMinusOne = new int[idPeliculasSeleccionadas.length - 1];

				int indice = 0;
				int exceptionCount = 0;
				for (int i : idPeliculasSeleccionadas) {
//					log.debug("idPeliculasSeleccionadas: " + i);
					if (i == idPeliculaAPredecir && exceptionCount == 0) {
//						log.debug(" > es igual a idPeliculaAPredecir por primera vez");
						exceptionCount++;
						continue;
					} else {
//						log.debug(" > se copia a idPeliculasSeleccionadasMinusOne[" + indice + "]");
						idPeliculasSeleccionadasMinusOne[indice] = i;
						indice++;
					}
				}
				/*
				 * if
				 * (idPeliculasSeleccionadas[idPeliculasSeleccionadas.length-1]
				 * == idPeliculaAPredecir) { // Esto es en el caso de que se
				 * haya incluido la Última Predicción
				 * 
				 * } else {
				 */
				maxCantPeliculasValidas = new Integer(spc.realizarPrediccion(
						request, idPeliculaAPredecir, idComplejos,
						idPeliculasSeleccionadas, cantidadDiasAPredecir,
						fechaDesdeString, maxCantPeliculasValidas, usuario,
						colores, diaEstreno, peliculaDao
								.obtenerPelicula(idPeliculaAPredecir),
						fechaPredecirDesde, graficos, predicciones,
						graficosMalos, j, overridePesos));
				// }

			}

		}
		
		// Setear las ids de las predicciones por día
		int idnice = 0;
		for (Prediccion p : predicciones) {
			Prediccion prediccionDB = prediccionDao.obtenerPrediccion(Integer.parseInt(form.getPrediccionFuncion()[idnice]));
			p.setPrediccionPorDia(prediccionDB.getPrediccionPorDia());
			// Y aquí debería venir con las id
			idnice++;
		}

		// Ahora, poner en sesión todo lo que es necesario para que se pueda
		// mostrar en /asistenciapeliculadia.htm
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("graficos", graficos);
		log.debug("graficos.size:" + graficos.size());
		map.put("graficosMalos", graficosMalos);
		log.debug("graficosMalos.size:"+graficosMalos.size());
		map.put("predicciones", predicciones);
		log.debug("predicciones.size:"+predicciones.size());
		map.put("maxCantPeliculasValidas", maxCantPeliculasValidas.intValue());
		log.debug("getPrediccionFuncion()[0]: "
				+ form.getPrediccionFuncion()[0]);
		map
				.put("nombrePelicula", predicciones.get(0).getPelicula()
						.getNombre());
		// / Esto es no es tan flaite ahora, dado que solamente se podrá ir a
		// predecir predicciones incompletas de a una.
		map.put("dias_a_predecir", prediccionDao
				.obtenerCantidadDiasPrediccion(Integer.parseInt(form
						.getPrediccionFuncion()[0])));
		map.put("overridePrediccionId", form.getPrediccionFuncion()[0]);
		map.put("idPrediccion", form.getPrediccionFuncion()[0]);
		// map.put("seleccion", new Integer(3));
		if (request.getSession().getAttribute("datosGraficos") != null) {
			request.getSession().removeAttribute("datosGraficos");
		}
		request.getSession().setAttribute("datosGraficos", map);
		// La idea de setear notNuevaPrediccion es que no se grabe en la BD como
		// una nueva predicción
		// que es lo que sucede por defecto al entrar a
		// /asistenciapeliculadia.htm
		request.getSession().setAttribute("notNuevaPrediccion", true);
		request.getSession().setAttribute("seleccion", new Integer(3));
		
		// Finalmente agregamos esta predicción a las prediccionesAcumuladas
		CopyOnWriteArrayList<Prediccion> prediccionesAcumuladas = (CopyOnWriteArrayList<Prediccion>) request.getSession().getAttribute("predicciones");
		if (prediccionesAcumuladas != null) {
			request.getSession().removeAttribute("predicciones");
		}
		request.getSession().setAttribute("predicciones",predicciones);
		
		// Allons-y!
		
		ModelAndView mv = new ModelAndView(new RedirectView(
				"asistenciapeliculadia.htm"));
		return mv;
	}

	public PeliculaDao getPeliculaDao() {
		return peliculaDao;
	}

	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}

	/**
	 * 
	 * Método ejecutado cuando se quiere cargar una predicción incompleta, con
	 * predicciones por función pero no cargada aún.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24-12-2009 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 * @since 3.0
	 */
	public ModelAndView onSubmitPNoCargadas(HttpServletRequest request,
			HttpServletResponse response, PrediccionIncompletaForm command)
			throws Exception {
		PrediccionIncompletaForm form = (PrediccionIncompletaForm) command;
		log.debug("onSubmitPNoCargadas has been called.");

		/** Cambiar todo esto según TODO **/
		/**********************************/
		// Obtener las predicciones elegidas
		CopyOnWriteArrayList<Complejo> listaDeComplejos = new CopyOnWriteArrayList<Complejo>();
		CopyOnWriteArrayList<Prediccion> listaDePredicciones = new CopyOnWriteArrayList<Prediccion>();
		if (form.getPrediccionNoCargadas() != null) {
			for (int i = 0; i < form.getPrediccionNoCargadas().length; i++) {
				Prediccion thisPrediccion = prediccionDao
						.obtenerPrediccion(Integer.parseInt(form
								.getPrediccionNoCargadas()[i]));
				if (!listaDeComplejos.contains(thisPrediccion.getComplejo())) {
					listaDeComplejos.add(thisPrediccion.getComplejo());
				}

				for (PrediccionPorDia ppd : thisPrediccion.getPrediccionPorDia()) {
					for (PrediccionPorFuncion ppf : ppd.getPrediccionesPorFuncion()) {
						List<Mascara> listaMascaras = mascaraDao.obtenerMascarasSala(ppf.getFuncionPredecida().getSala());
						ppf.setMascaras(listaMascaras);
					}
				}

				listaDePredicciones.add(thisPrediccion);
				
				try {
					log.debug("thisPrediccion.id = " + thisPrediccion.getId() + " brought back");
					try {
						for (PrediccionPorDia ppd : thisPrediccion.getPrediccionPorDia()) {
							log.debug(" > ppd.id: " + ppd.getId() + "; ppd.fecha: " + Util.DateToString(ppd.getFecha()));
						}
					} catch (Exception e) {
						log.debug(" > No se pudo obtener el id de una predicción por día para esta predicción.");
					}
				} catch (Exception e){
					log.debug("En PrediccionIncompletaController, no se pudo obtener el id de la predicción agregada");
				}
				
			}
		}

		ModelAndView mv = new ModelAndView(new RedirectView("predicciones.htm"));
		// Ponerlas en la sesión
		request.getSession().setAttribute("complejos", listaDeComplejos);
		request.getSession().setAttribute("predicciones", listaDePredicciones);
		mv.addObject("complejos", listaDeComplejos);
		mv.addObject("predicciones", listaDePredicciones);
		request.getSession().setAttribute("overrideMascaras",true);
		// Mandarlas a la vista /predicciones.jsp
		return mv;

		// return new ModelAndView("prediccionincompleta");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		PrediccionIncompletaForm form = new PrediccionIncompletaForm();
		return form;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#referenceData
	 * (javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView referenceData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("referenceData" + " has been called.");

		ModelAndView mv = new ModelAndView("prediccionincompleta");
		Map<String, Object> map = mv.getModel();

		String user = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		Usuario usuario = new Usuario();
		usuario = usuarioDao.obtenerUsuarioByName(user);

		// Obtenemos las predicciones incompletas ssin prediccion por funcion y
		// con prediccion no cargadas
		List<PrediccionIncompleta> pspf = prediccionDao
				.obtenerPrediccionSinPrediccionPorFuncion(usuario.getId());
		List<PrediccionIncompleta> pnocargadas = prediccionDao
				.obtenerPrediccionNoCargada(usuario.getId());

		map.put("isPSPC", new Boolean(false));
		map.put("pspf", pspf);
		map.put("pnocargadas", pnocargadas);
		if (pspf.size() > 0)
			map.put("isPSPF", new Boolean(true));
		else
			map.put("isPSPF", new Boolean(false));

		if (pnocargadas.size() > 0)
			map.put("isPNoCargadas", new Boolean(true));
		else
			map.put("isPNoCargadas", new Boolean(false));

		request.setAttribute("PrediccionIncompletaForm",
				new PrediccionIncompletaForm());
		return mv;

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
	 * @return the prediccionManager
	 */
	public PrediccionManager getPrediccionManager() {
		return prediccionManager;
	}

	/**
	 * @param prediccionManager
	 *            the prediccionManager to set
	 */
	public void setPrediccionManager(PrediccionManager prediccionManager) {
		this.prediccionManager = prediccionManager;
	}

	/**
	 * @return the usuarioDao
	 */
	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	/**
	 * @param usuarioDao
	 *            the usuarioDao to set
	 */
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
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

	/**
	 * @return the serviciosRM
	 */
	public ServiciosRevenueManager getServiciosRM() {
		return serviciosRM;
	}

	/**
	 * @param serviciosRM
	 *            the serviciosRM to set
	 */
	public void setServiciosRM(ServiciosRevenueManager serviciosRM) {
		this.serviciosRM = serviciosRM;
	}

	public ComplejoDao getComplejoDao() {
		return complejoDao;
	}

	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

	public EpocaDao getEpocaDao() {
		return epocaDao;
	}

	public void setEpocaDao(EpocaDao epocaDao) {
		this.epocaDao = epocaDao;
	}

	public MarkerDao getMarkerDao() {
		return markerDao;
	}

	public void setMarkerDao(MarkerDao markerDao) {
		this.markerDao = markerDao;
	}

	public PublicoDao getPublicoDao() {
		return publicoDao;
	}

	public void setPublicoDao(PublicoDao publicoDao) {
		this.publicoDao = publicoDao;
	}

	/**
	 * @return the mascaraDao
	 */
	public MascaraDao getMascaraDao() {
		return mascaraDao;
	}

	/**
	 * @param mascaraDao
	 *            the mascaraDao to set
	 */
	public void setMascaraDao(MascaraDao mascaraDao) {
		this.mascaraDao = mascaraDao;
	}
}