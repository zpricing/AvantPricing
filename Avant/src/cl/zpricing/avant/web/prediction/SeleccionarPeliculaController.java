package cl.zpricing.avant.web.prediction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Categoria;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Epoca;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Parametro;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.Publico;
import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.model.prediction.PrediccionParametros;
import cl.zpricing.avant.model.prediction.PrediccionPorDia;
import cl.zpricing.avant.negocio.PrediccionManager;
import cl.zpricing.avant.servicios.CategoriaDao;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.EpocaDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.MarkerDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.servicios.PublicoDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.avant.servicios.UsuarioDao;
import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.chart.ArchivoGrafico;
import cl.zpricing.avant.web.chart.CategoriaGrafico;
import cl.zpricing.avant.web.chart.Color;
import cl.zpricing.avant.web.chart.ColorSerie;
import cl.zpricing.avant.web.chart.Dia;
import cl.zpricing.avant.web.chart.GeneradorXMLGrafico;
import cl.zpricing.avant.web.chart.SerieDatos;
import cl.zpricing.avant.web.chart.Valor;
import cl.zpricing.avant.web.form.SeleccionarPeliculaForm;

/**
 * <b>Controlador de la vista seleccionarpeliculas, encargada de comenzar
 * predicciones de películas que van a ser estrenadas</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 05-01-2009 Daniel Estévez Garay: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class SeleccionarPeliculaController extends SimpleFormController {
	private MarkerDao markerDao;
	private PeliculaDao peliculaDao;
	private CategoriaDao categoriaDao;
	private EpocaDao epocaDao;
	private PublicoDao publicoDao;
	private ComplejoDao complejoDao;
	private FuncionDao funcionDao;
	private PrediccionManager prediccionManager;
	private UsuarioDao usuarioDao;
	private ServiciosRevenueManager serviciosRM;

	/**
	 * Impresión de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/**
	 * Descripción de Método.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 05-01-2009 Daniel Estévez Garay: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @param errors
	 * @return
	 * @throws Exception
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		SeleccionarPeliculaForm form = (SeleccionarPeliculaForm) command;

		int id_pelicula = form.getId_pelicula();
		int[] id_complejos = form.getId_complejos();
		@SuppressWarnings("unused")
		int[] id_peliculas = form.getId_peliculas();
		int[] id_peliculas_seleccionadas = form.getId_peliculas_seleccionada();
		int[] categorias = form.getCategorias();
		int[] publicos = form.getPublicos();
		int[] epocas = form.getEpocas();
		int dias_a_predecir = form.getDias_a_predecir();
		String fecha = form.getFecha();

		/*
		 * for(int i=0;i<id_peliculas_seleccionadas.length;i++)
		 * log.debug("Id peliculas_seleccionadas: "
		 * +id_peliculas_seleccionadas[i]);
		 */

		if (request.getParameter("filtrar") != null) {
			log.debug("Entrando a filtrar...");

			ModelAndView mv = showForm(request, response, errors);

			List<Pelicula> peliculas = new ArrayList<Pelicula>();
			ArrayList<Pelicula> peliculas_todas = peliculaDao.obtenerListaPeliculas();

			Iterator<Pelicula> it = peliculas_todas.iterator();

			while (it.hasNext()) {
				Pelicula p = it.next();
				// log.debug("Haciendo test a pelicula " +p.getNombre());
				boolean test_epoca = false;
				Iterator<Epoca> itep = p.getEpocas().iterator();
				while (itep.hasNext()) {
					Epoca epo = itep.next();
					for (int i = 0; i < epocas.length; i++) {
						if (epocas[i] == epo.getId()) {
							test_epoca = true;
							break;
						}
					}
					if (test_epoca)
						break;
				}
				if (!test_epoca)
					continue;

				boolean test_categoria = false;
				Iterator<Categoria> itcat = p.getCategorias().iterator();
				while (itcat.hasNext()) {
					Categoria cat = itcat.next();
					for (int i = 0; i < categorias.length; i++) {
						if (categorias[i] == cat.getId()) {
							test_categoria = true;
							break;
						}
					}
					if (test_categoria)
						break;
				}
				if (!test_categoria)
					continue;

				boolean test_publico = false;
				Iterator<Publico> itpub = p.getTiposDePublico().iterator();
				while (itpub.hasNext()) {
					Publico pub = itpub.next();
					for (int i = 0; i < publicos.length; i++) {
						if (publicos[i] == pub.getId()) {
							test_publico = true;
							break;
						}
					}
					if (test_publico)
						break;
				}
				if (!test_publico)
					continue;

				if (test_epoca && test_categoria && test_publico)
					peliculas.add(p);
			}
			mv.addObject("peliculas_filtradas", peliculas);

			List<Pelicula> peliculas_seleccionadas = new ArrayList<Pelicula>();
			for (int i = 0; i < id_peliculas_seleccionadas.length; i++) {
				peliculas_seleccionadas.add(peliculaDao.obtenerPelicula(id_peliculas_seleccionadas[i]));
			}

			for (int i = 0; i < id_complejos.length; i++) {
				log.debug("Id complejos seleccionados: " + id_complejos[i]);
			}

			log.debug("Id pelicula: " + id_pelicula);

			mv.addObject("peliculas_seleccionadas", peliculas_seleccionadas);
			return mv;
		}

		if (request.getParameter("seleccionar") != null) {
			log.debug("Pasando de seleccionar pelicula1 a asistencia pelicula dia");
			Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute("datosGraficos");
			if (map == null) {
				map = new HashMap<String, Object>();
			}
			map.put("id_pelicula", id_pelicula);
			map.put("id_complejos", id_complejos);
			map.put("id_peliculas_seleccionadas", id_peliculas_seleccionadas);
			map.put("fecha", fecha);
			map.put("dias_a_predecir", new Integer(dias_a_predecir));
			map.put("nro_peliculas_seleccionadas", new Integer(id_peliculas_seleccionadas.length));
			request.getSession().setAttribute("seleccion", new Integer(1));

			Integer maxCantPeliculasValidas = new Integer(0);

			// Obtener Usuario
			String user = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario usuario = null;
			usuario = usuarioDao.obtenerUsuarioByName(user);
			map.put("nombreUsuario", usuario.getNombreCompleto());

			// iniciarColores
			List<ColorSerie> colores = new ArrayList<ColorSerie>();
			ColorSerie color;
			Color.iniciarColor();
			for (int i = 0; i < id_peliculas_seleccionadas.length; i++) {
				color = new ColorSerie();
				color.setIdSerie(id_peliculas_seleccionadas[i]);
				color.setColor(Color.generarColor());
				colores.add(color);
			}
			color = new ColorSerie();
			color.setIdSerie(id_pelicula);
			color.setColor(Color.generarColor());
			colores.add(color);

			// Setear Dia Estreno
			Parametro r = serviciosRM.obtenerParametro("Cine", "Dia_Estreno");
			int diaEstreno = Integer.parseInt(r.getCodigo());

			// SetearCategorias
			List<CategoriaGrafico> catGraf = new ArrayList<CategoriaGrafico>();
			for (int i = diaEstreno; i < dias_a_predecir + diaEstreno; i++) {
				catGraf.add(new CategoriaGrafico(Dia.getDia(i) + (i - diaEstreno + 1)));
			}

			// Recorrer por complejo
			List<Map<String, Object>> graficos = new ArrayList<Map<String, Object>>();
			CopyOnWriteArrayList<Prediccion> predicciones = new CopyOnWriteArrayList<Prediccion>();

			List<String> graficosMalos = new ArrayList<String>();
			for (int j = 0; j < id_complejos.length; j++) {
				Complejo complejo = complejoDao.obtenerComplejo(id_complejos[j]);
				maxCantPeliculasValidas = realizarPrediccion(request, id_pelicula, id_peliculas_seleccionadas, dias_a_predecir, fecha, maxCantPeliculasValidas, usuario, colores, diaEstreno, catGraf, graficos, predicciones, graficosMalos, j, complejo, null);
			}

			map.put("nombrePelicula", peliculaDao.obtenerPelicula(id_pelicula).getNombre());
			map.put("graficos", graficos);
			map.put("maxCantPeliculasValidas", maxCantPeliculasValidas);
			map.put("predicciones", predicciones);

			Boolean hayGraficosMalos = new Boolean(false);
			if (graficosMalos.size() > 0) {
				hayGraficosMalos = new Boolean(true);
				map.put("graficosMalos", graficosMalos);
			}
			map.put("hayGraficosMalos", hayGraficosMalos);

			if (request.getSession().getAttribute("predicciones") == null)
				request.getSession().setAttribute("predicciones", new CopyOnWriteArrayList<Prediccion>());

			if (request.getSession().getAttribute("datosGraficos")!=null) {
				request.getSession().removeAttribute("datosGraficos");
			}
			request.getSession().setAttribute("datosGraficos", map);

			return new ModelAndView(new RedirectView(getSuccessView()));
		}

		return null;

	}
	
	/**
	 * 
	 * Método extraído de onSubmit() de SeleccionarPeliculaController. Realiza la predección para un complejo en particular,
	 * con sus propias películas seleccionadas.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 28-12-2009 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 * @param idPeliculaAPredecir
	 * @param idPeliculasSeleccionadas
	 * @param cantidadDiasAPredecir
	 * @param fechaDesdeString
	 * @param maxCantPeliculasValidas
	 * @param usuario
	 * @param colores
	 * @param diaEstreno
	 * @param categoriasGraficos
	 * @param graficos
	 * @param predicciones
	 * @param graficosMalos
	 * @param numeroCorrelativo
	 * @param complejo
	 * @param overridePesos 
	 * @return
	 * @since 3.0
	 */

	protected int realizarPrediccion(
			HttpServletRequest request, int idPeliculaAPredecir, int[] idPeliculasSeleccionadas, int cantidadDiasAPredecir,
			String fechaDesdeString, Integer maxCantPeliculasValidas, Usuario usuario, List<ColorSerie> colores, int diaEstreno,
			List<CategoriaGrafico> categoriasGraficos, List<Map<String, Object>> graficos,
			CopyOnWriteArrayList<Prediccion> predicciones, List<String> graficosMalos, int numeroCorrelativo, Complejo complejo, 
			ArrayList<PrediccionParametros> overridePesos) {
		SerieDatos serieDat;
		List<Valor> valores;
		List<Double> pesosDias;
		// añadir peliculas para predecir
		Map<String, Object> grafico = new HashMap<String, Object>();
		List<SerieDatos> seriesDat = new ArrayList<SerieDatos>();
		for (int i = 0; i < idPeliculasSeleccionadas.length; i++) {
			Pelicula pelicula = peliculaDao.obtenerPelicula(idPeliculasSeleccionadas[i]);
			// log.debug("NOMBRE: " + pelicula.getNombre());
			Funcion primaFuncion = funcionDao.obtenerPrimeraFuncion(pelicula, complejo, diaEstreno);

			if (primaFuncion != null) {
				// log.debug("Pelicula: " +
				// pelicula.getNombre()+" Complejo: "+complejo.getNombre()+" Fecha: "+primaFuncion.getFecha());
				GregorianCalendar fechaPrimeraFuncion = new GregorianCalendar();
				fechaPrimeraFuncion.setTime(primaFuncion.getFecha());
				// log.debug(" Fecha Interna: "+fechaPrimeraFuncion.getTime());

				int asistencias[] = prediccionManager.obtenerAsistenciaPorPeliculaDiaComplejo(pelicula, complejo, fechaPrimeraFuncion, cantidadDiasAPredecir);
				fechaPrimeraFuncion.setTime(primaFuncion.getFecha());

				serieDat = new SerieDatos();
				valores = new ArrayList<Valor>();
				pesosDias = new ArrayList<Double>();

				// Agregar datos relevantes por cada dia a la serie de
				// Datos
				GregorianCalendar fechaFuncion = new GregorianCalendar();
				fechaFuncion.setTime(primaFuncion.getFecha());
				fechaFuncion = new GregorianCalendar(fechaFuncion.get(Calendar.YEAR), fechaFuncion.get(Calendar.MONTH), fechaFuncion.get(Calendar.DAY_OF_MONTH));
				for (int x = 0; x < asistencias.length; x++) {
					Valor valor = new Valor(asistencias[x]);
					log.debug("antes: " + fechaFuncion.getTime());

					// Info
					List<String> info = new ArrayList<String>();
					info.add("Dia: " + Util.DateToString(fechaFuncion.getTime()));
					valor.setInfo(info);

					log.debug("antes: " + fechaFuncion.getTime());
					// Markers
					valor.setMarkers(markerDao.obtenerMarkersFechaComplejoPelicula(fechaFuncion.getTime(), complejo, pelicula));

					valores.add(valor);
					fechaFuncion.add(Calendar.DAY_OF_MONTH, 1);
					pesosDias.add(new Double(1.0));
				}

				/*
				 * //Info List<String> info = new ArrayList<String>();
				 * info.add("Pelicula: "+pelicula.getNombre());
				 * serieDat.setInfo(info);
				 * 
				 * //Markers serieDat.setMarkers(pelicula.getMarkers());
				 */

				serieDat.setValores(valores);
				serieDat.setPesosDias(pesosDias);
				serieDat.setNombreSerie(pelicula.getNombre());
				serieDat.setFecha(fechaPrimeraFuncion);
				serieDat.setColor(ColorSerie.colorSerie(colores, idPeliculasSeleccionadas[i]));

				seriesDat.add(serieDat);
			} else {
				log.debug("No existe la primeraFuncion para la pelicula " + pelicula.getNombre());
			}
		}
		if (seriesDat.size() > 0) {

			Iterator<SerieDatos> itSeries = seriesDat.iterator();
			int indiceSeries = 0;
			while (itSeries.hasNext()) {
				serieDat = itSeries.next();
				if (overridePesos!=null) {
					serieDat.setPesoPel(overridePesos.get(indiceSeries).getPonderacion());
				} else {
					serieDat.setPesoPel(new Double(1.0 / seriesDat.size()));
				}
				indiceSeries++;
			}

			// añadir pelicula a predecir
			serieDat = new SerieDatos();
			valores = new ArrayList<Valor>();

			Pelicula peliculaSeleccionada = peliculaDao.obtenerPelicula(idPeliculaAPredecir);

			valores = SerieDatos.calcularResultado(seriesDat);

			// crear la prediccion
			Prediccion prediccion = new Prediccion();
			prediccion.setPelicula(peliculaSeleccionada);
			prediccion.setComplejo(complejo);
			
			prediccion.setFecha(Calendar.getInstance().getTime());
			if (request.getSession().getAttribute("overrideFecha") != null) {
				log.debug("prediccion.setFecha("+ Util.StringToDate(fechaDesdeString)+ ")");
				prediccion.setFecha((Date) request.getSession().getAttribute("overrideFecha"));
			}

			prediccion.setUsuario(usuario);
			prediccion.setPrediccionPorDia(new ArrayList<PrediccionPorDia>());

			ArrayList<PrediccionParametros> parametros = new ArrayList<PrediccionParametros>();
			log.debug("Se crean a continuación los parámetros de la predicción. ");
			for (int i = 0; i < idPeliculasSeleccionadas.length; i++) {
				if (funcionDao.obtenerPrimeraFuncion(peliculaDao.obtenerPelicula(idPeliculasSeleccionadas[i]), complejo,
					diaEstreno) != null) {
					PrediccionParametros param = new PrediccionParametros();
					param.setPelicula(peliculaDao.obtenerPelicula(idPeliculasSeleccionadas[i]));
					log.debug(i + ". Película seleccionada: " + param.getPelicula().getNombre());
					param.setPrediccion(prediccion);
					log.debug("    Predicción: " + param.getPrediccion().getId());
					param.setPonderacion(new Double(1.0 / seriesDat.size()));
					parametros.add(param);
					log.debug("    Ponderación: " + param.getPonderacion());
				} 
				else {
					log.debug("No hay primeraFuncion para la pelicula " + peliculaDao.obtenerPelicula(idPeliculasSeleccionadas[i]).getNombre());
				}
			}

			prediccion.setPrediccionParametros(parametros);

			GregorianCalendar fechaPrediccion = new GregorianCalendar();
			fechaPrediccion.setTime(Util.StringToDate(fechaDesdeString));
			Iterator<Valor> itValor = valores.iterator();
			Valor vDia;

			while (itValor.hasNext()) {
				vDia = itValor.next();

				// Info
				List<String> info = new ArrayList<String>();
				info.add("Dia: " + Util.DateToString(fechaPrediccion.getTime()));
				vDia.setInfo(info);

				// Marker
				vDia.setMarkers(markerDao.obtenerMarkersFechaComplejoPelicula(fechaPrediccion.getTime(), complejo, peliculaSeleccionada));

				PrediccionPorDia ppDia = new PrediccionPorDia();
				ppDia.setEstimacion((int) vDia.getValor());
				ppDia.setFecha(fechaPrediccion.getTime());
				ppDia.setPrediccion(prediccion);
				prediccion.getPrediccionPorDia().add(ppDia);

				fechaPrediccion.add(Calendar.DAY_OF_MONTH, 1);
			}
			predicciones.add(prediccion);

			// Info
			List<String> info = new ArrayList<String>();
			info.add("Pelicula: " + peliculaSeleccionada.getNombre());
			serieDat.setInfo(info);

			// Markers
			serieDat.setMarkers(peliculaSeleccionada.getMarkers());

			serieDat.setValores(valores);
			serieDat.setNombreSerie(peliculaSeleccionada.getNombre());
			serieDat.setSerieResultado();
			serieDat.setColor(ColorSerie.colorSerie(colores, idPeliculaAPredecir));
			seriesDat.add(serieDat);

			// Guardar los datos del grafico
			GeneradorXMLGrafico gen = new GeneradorXMLGrafico();
			gen.setCategorias(categoriasGraficos);
			gen.setDatos(seriesDat);
			gen.setLegendaX("Dias");
			gen.setLegendaY1("Asistencia");
			gen.setTitulo("Prediccion para " + peliculaSeleccionada.nombre + " en " + complejo.getNombre());

			ArchivoGrafico graXML = new ArchivoGrafico(request, "asistencia" + (complejo.getNombre().replace(" ", "")) + numeroCorrelativo);
			gen.setRutaArchivo(graXML.rutaGraficoInterna());

			gen.aXML();

			grafico.put("genGraf", gen);

			if (seriesDat.size() - 1 > maxCantPeliculasValidas) {
				maxCantPeliculasValidas = seriesDat.size() - 1;
			}
			grafico.put("cantPeliculasValidas", new Integer(seriesDat.size() - 1));

			grafico.put("xmlGraf", graXML.rutaGraficoExterna());
			grafico.put("complejo", complejo.getNombre());

			GregorianCalendar fechaGrafico = new GregorianCalendar();
			fechaGrafico.setTime(Util.StringToDate(fechaDesdeString));

			// ¿Qué es fechaEstreno?
			grafico.put("fechaEstreno", fechaGrafico);

			/*
			 * //Info info = new ArrayList<String>();
			 * info.add("Complejo: "+complejo.getNombre());
			 * grafico.put("info", info);
			 * 
			 * //Markers grafico.put("Markers",
			 * markerDao.obtenerMarkersComplejo(complejo));
			 */

			graficos.add(grafico);
		} else {
			graficosMalos.add(complejo.getNombre());
		}
		return maxCantPeliculasValidas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		log.debug("Iniciando formBackingObject...");
		SeleccionarPeliculaForm form = new SeleccionarPeliculaForm();
		form.setDias_a_predecir(7);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		String fechaForm = Util.DateToString(calendar.getTime());
		form.setFecha(fechaForm);
		return form;
	}

	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object arg0, BindException arg1) throws Exception {
		SeleccionarPeliculaForm form = (SeleccionarPeliculaForm) arg0;
		if (request.getParameter("filtrar") != null) {

			if (form.getCategorias().length == 0)
				arg1.rejectValue("categorias", "error.categorias");
			if (form.getEpocas().length == 0)
				arg1.rejectValue("epocas", "error.epocas");
			if (form.getPublicos().length == 0)
				arg1.rejectValue("publicos", "error.publicos");

		}

		if (request.getParameter("seleccionar") != null) {
			if (form.getId_peliculas_seleccionada().length == 0)
				arg1.rejectValue("id_peliculas_seleccionada", "error.peliculas_seleccionadas");

			ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "dias_a_predecir", "error.dias_a_predecir");

			if (form.getDias_a_predecir() == 0)
				arg1.rejectValue("dias_a_predecir", "error.notzero");

			if (form.getId_complejos().length == 0)
				arg1.rejectValue("id_complejos", "error.complejos");

			try {
				GregorianCalendar gc = new GregorianCalendar();
				gc.setLenient(false);
				gc.setTime(Util.StringToDate(form.getFecha()));

				gc.getTime();
			} catch (Exception e) {
				arg1.rejectValue("ano", "error.fecha", null, "Fecha incorrecta");
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#referenceData
	 * (javax.servlet.http.HttpServletRequest)
	 */
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>(8);
		Calendar calendar = Calendar.getInstance();
		map.put("year", calendar.get(Calendar.YEAR));
		log.debug("Obteniendo peliculas activas");
		map.put("peliculas", peliculaDao.obtenerListaPeliculasActivas());
		log.debug("Obteniendo todas las peliculas");
		map.put("peliculas_filtradas", peliculaDao.obtenerListaPeliculas());
		log.debug("Obteniendo las categorias");
		map.put("categorias", categoriaDao.obtenerCategorias());
		log.debug("Obteniendo las epocas");
		map.put("epocas", epocaDao.obtenerEpocas());
		log.debug("Obteniendo las tipos de publico");
		map.put("publicos", publicoDao.obtenerPublicos());
		log.debug("Obteniendo los complejos");
		map.put("complejos", complejoDao.complejosTodos());
		log.debug("Finalizando reference data");
		return map;
	}


	public void setPrediccionManager(PrediccionManager prediccionManager) {
		this.prediccionManager = prediccionManager;
	}
	public void setServiciosRM(ServiciosRevenueManager serviciosRM) {
		this.serviciosRM = serviciosRM;
	}
	public void setMarkerDao(MarkerDao markerDao) {
		this.markerDao = markerDao;
	}
	public void setCategoriaDao(CategoriaDao categoriaDao) {
		this.categoriaDao = categoriaDao;
	}
	public void setEpocaDao(EpocaDao epocaDao) {
		this.epocaDao = epocaDao;
	}
	public void setPublicoDao(PublicoDao publicoDao) {
		this.publicoDao = publicoDao;
	}
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
}
