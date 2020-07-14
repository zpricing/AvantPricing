package cl.zpricing.avant.web.prediction;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Asistencia;
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
import cl.zpricing.avant.servicios.PrediccionDao;
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
 * <b>Controlador de la vista seleccionarpeliculas2, encargada de comenzar
 * predicciones de peliculas ya estrenadas</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 12-01-2009 Daniel Estevez Garay: version inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class SeleccionarPelicula2Controller extends SimpleFormController {
	private MarkerDao markerDao;
	private PeliculaDao peliculaDao;
	private CategoriaDao categoriaDao;
	private EpocaDao epocaDao;
	private PublicoDao publicoDao;
	private ComplejoDao complejoDao;
	private FuncionDao funcionDao;
	private UsuarioDao usuarioDao;
	private PrediccionDao prediccionDao;
	private PrediccionManager prediccionManager;
	private ServiciosRevenueManager serviciosRM;

	private static final int idUltimaPrediccion = -1;
	private boolean usarPrediccionAnterior = false;

	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		//log.setLevel(Level.ALL);
		log.debug("Entrando a seleccionarPeliculas2....");
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
		int primeros_dias_a_elegir = form.getPrimeros_dias_a_elegir();
		int dias_a_saltar = form.getDias_a_saltar();
		String direccion = form.getDireccion();

		/**
		 * FILTRO DE PELICULAS
		 */
		// predecirDesde.compareTo("borrar esta linea);");
		
		if (request.getParameter("filtrar") != null) {
			usarPrediccionAnterior = false;
			log.debug("Entrando a filtrar...");
			double rango = Double.parseDouble(form.getRango());
			ModelAndView mv = showForm(request, response, errors);

			List<Pelicula> peliculas = new ArrayList<Pelicula>();
			ArrayList<Pelicula> peliculas_todas = peliculaDao.obtenerListaPeliculas();
			Pelicula pel = peliculaDao.obtenerPelicula(id_pelicula);//esta es la pelicula para predecir
			int dia_estreno = Integer.parseInt(serviciosRM.obtenerParametro("Cine", "Dia_Estreno").getCodigo());
			int hora_inicio_dia = Integer.parseInt(serviciosRM.obtenerParametro("Cine", "hora_inicio_dia").getCodigo());
			int hora_termino_dia = Integer.parseInt(serviciosRM.obtenerParametro("Cine", "hora_termino_dia").getCodigo());
			int diferencia_dias_dia_cine = Integer.parseInt(serviciosRM.obtenerParametro("Cine", "diferencia_dias_dia_cine").getCodigo());

			/**
			 * CALCULO DE ASISTENCIAS DE PELICULA YA ESTRENADA en el rango ya
			 * sea a partir de la fecha de estreno o desde la fecha actual hacia
			 * atras
			 */

			int[][] primeras_asistencias_complejo = new int[primeros_dias_a_elegir][id_complejos.length];
			/**
			 * dias a sumar a la fecha de estreno para tomar el rango de dias
			 * hasta hoy
			 */
			int dias_a_sumar = 0;
			for (int i = 0; i < id_complejos.length; i++) {
				// i recorriendo cada uno de los complejos seleccionados
				Complejo complejo = complejoDao.obtenerComplejo(id_complejos[i]);
				Funcion primera_funcion_pelicula = funcionDao.obtenerPrimeraFuncion(pel, complejo, dia_estreno);
				if (primera_funcion_pelicula == null) {
					for (int j = 0; j < primeros_dias_a_elegir; j++)
						primeras_asistencias_complejo[j][i] = 0;
					break;
				}

				GregorianCalendar dia_primero = new GregorianCalendar();
				GregorianCalendar dia_fin = new GregorianCalendar();
				dia_primero.setTime(primera_funcion_pelicula.getFecha());

				/**
				 * CASO HACIA ATRAS
				 */
				if (direccion.compareTo("A") == 0) {
					/**
					 * fecha actual
					 */
					Calendar hoy = Calendar.getInstance();
					
					/**
					 * Se revisa el caso en que la fecha de estreno no haya sido
					 * en este a�o
					 */
					if (dia_primero.get(Calendar.DAY_OF_YEAR) > hoy.get(Calendar.DAY_OF_YEAR)) {
						if (dia_primero.isLeapYear(dia_primero.get(Calendar.YEAR)))
							dias_a_sumar = 366 - dia_primero.get(Calendar.DAY_OF_YEAR);
						else
							dias_a_sumar = 365 - dia_primero.get(Calendar.DAY_OF_YEAR);

						dias_a_sumar += hoy.get(Calendar.DAY_OF_YEAR);
					} else {
						dias_a_sumar = hoy.get(Calendar.DAY_OF_YEAR) - dia_primero.get(Calendar.DAY_OF_YEAR);
					}

					dia_primero.add(Calendar.DAY_OF_YEAR, dias_a_sumar);
				}
				/**
				 * CASO DESDE FECHA DE ESTRENO SE DEJA POR DEFECTO por lo que no
				 * se hace su caso especial
				 */
				dia_fin.setTime(dia_primero.getTime());
				dia_primero.set(Calendar.HOUR_OF_DAY, hora_inicio_dia);
				dia_primero.set(Calendar.MINUTE, 0);
				dia_primero.set(Calendar.SECOND, 0);
				dia_fin.set(Calendar.HOUR_OF_DAY, hora_termino_dia);
				dia_fin.set(Calendar.MINUTE, 0);
				dia_fin.set(Calendar.SECOND, 0);
				dia_fin.add(Calendar.DAY_OF_MONTH, diferencia_dias_dia_cine);

				for (int j = 0; j < primeros_dias_a_elegir; j++) {
					int asistencia_aux = 0;
					ArrayList<Funcion> funciones = funcionDao.obtenerListaFuncionesComplejo(dia_primero, dia_fin, pel, complejo);
					Iterator<Funcion> itfun = funciones.iterator();
					while (itfun.hasNext()) {
						Funcion funcion = itfun.next();
						ArrayList<Asistencia> asistencias = funcion.getAsistenciasDeFuncion();
						Iterator<Asistencia> it_as = asistencias.iterator();
						while (it_as.hasNext()) {
							Asistencia asistencia = it_as.next();
							asistencia_aux += asistencia.getAsistencia();
						}

					}
					//i: recorre los complejos; j: recorre los dias
					primeras_asistencias_complejo[j][i] = asistencia_aux;
					log.debug("Asistencias pelicula dia " + j + " complejo " + i + " = " + asistencia_aux);
					dia_primero.add(Calendar.DAY_OF_MONTH, 1);
					dia_fin.add(Calendar.DAY_OF_MONTH, 1);
				}
				/*
				 * En este punto, primeras_asistencias_complejo[j][i] tiene la
				 * cantidad de gente que fue a ver la pel�cula (pel) en el
				 * complejo i en el d�a j, donde j=0 corresponde al estreno.
				 */

			}

			/**
			 * FILTRO POR CATEGORIAS, EPOCA Y TIPO DE PUBLICO
			 */
			Iterator<Pelicula> it = peliculas_todas.iterator();
			while (it.hasNext()) {

				Pelicula p = it.next();
				log.debug("Haciendo test a pelicula " + p.getNombre()+"\tID = "+p.getId());
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

				/*
				 * Llego a este punto ssi la pelicula que estoy revisando
				 * ahora (de entre todas las peliculas disponibles en
				 * peliculas_todas) o bien corresponde a una de las categorias
				 * que escogi, o bien a una de las epocas que escogi, o
				 * bien a uno de los publicos que escogi.
				 */
				boolean[][] test_rango = new boolean[dias_a_predecir][id_complejos.length];

				for (int i = 0; i < id_complejos.length; i++) {
					Complejo complejo = complejoDao.obtenerComplejo(id_complejos[i]);
					Funcion primera_funcion_pelicula = funcionDao.obtenerPrimeraFuncion(p, complejo, dia_estreno);

					if (primera_funcion_pelicula == null) {
						for (int j = 0; j < primeros_dias_a_elegir; j++)
							test_rango[j][i] = false;
						break;
					}
					GregorianCalendar fecha_primera_funcion = new GregorianCalendar();
					fecha_primera_funcion.setTime(primera_funcion_pelicula.getFecha());
					/**
					 * SUMA DE DIAS PARA CASO HACIA ATRAS si no es el caso los
					 * dias a sumar seran cero
					 */
					fecha_primera_funcion.add(Calendar.DAY_OF_YEAR, dias_a_sumar);
					log.debug("%%% fecha_primera_funcion = "+Util.DateToString(fecha_primera_funcion.getTime()));
					int[] asistencias = prediccionManager.obtenerAsistenciaPorPeliculaDiaComplejo(p, complejo, fecha_primera_funcion,
						primeros_dias_a_elegir);

					for (int j = 0; j < primeros_dias_a_elegir; j++) {
						
						
						int tipo_de_rango = form.getTipo_de_rango();
						log.debug("tipo_de_rango = "+tipo_de_rango);
						
						//j: recorre los dias; i: recorre los complejos
						double cota_superior = 0, cota_inferior = 0;
						if(tipo_de_rango == 1){
							//rango de porcentajes; corresponde a un entero entre 0 y 100
							cota_superior = (1.0+rango*0.01)*(double)primeras_asistencias_complejo[j][i];
						   	cota_inferior = (1.0-rango*0.01)*(double)primeras_asistencias_complejo[j][i];
						}else if(tipo_de_rango == 2){
							//rango de asistencias; corresponde a un entero positivo
							cota_superior = (double)primeras_asistencias_complejo[j][i] + rango;
							cota_inferior = (double)primeras_asistencias_complejo[j][i] - rango;
						}

						/*						
						double cota_superior = (1.0+rango)*(double)primeras_asistencias_complejo[j][i];
						double cota_inferior = (1.0-rango)*(double)primeras_asistencias_complejo[j][i];
						*/
						
						log.debug("primeras_asistencias_complejo = "+(double)primeras_asistencias_complejo[j][i]);
						log.debug("cota_superior "+cota_superior+" cota inferior "+cota_inferior);
						
						if(asistencias[j]>=cota_inferior && asistencias[j]<=cota_superior)
							test_rango[j][i]=true;
						else
							test_rango[j][i] = false;

						// log.debug("Asistencias pelicula dia "+j+" complejo "+i+" = "+primeras_asistencias_complejo[j][i]+" Verificando= "+asistencias[j]+
						// " resultado= "+test_rango[j][i]);

					}

				}
				/**
				 * condicion actual para aprobar test de rango es que en al
				 * menos un complejo se cumplan los dias_a_pasar
				 * 
				 * test_rango[dia][complejos] es true cuando en dia la
				 * asistencia en el complejo a la pelicula esta dentro del
				 * rango, false en caso contrario
				 */
				int dias_a_pasar = primeros_dias_a_elegir - dias_a_saltar;

				boolean test_ran = false;
				for (int k = 0; k < id_complejos.length; k++) {
					int dias_pasados = 0;
					for (int h = 0; h < primeros_dias_a_elegir; h++) {
						if (test_rango[h][k]) {
							++dias_pasados;
						}
					}
					if (dias_pasados >= dias_a_pasar) {
						test_ran = true;
						break;
					}
				}

				if (/* test_epoca && test_categoria && test_publico&& */test_ran)
					peliculas.add(p);
			}
			mv.addObject("peliculas_filtradas", peliculas);

			List<Pelicula> peliculas_seleccionadas = new ArrayList<Pelicula>();
			for (int i = 0; i < id_peliculas_seleccionadas.length; i++)
				peliculas_seleccionadas.add(peliculaDao.obtenerPelicula(id_peliculas_seleccionadas[i]));

			mv.addObject("peliculas_seleccionadas", peliculas_seleccionadas);
			
			return mv;
		}/* fin if(filtrar) */

		if (request.getParameter("seleccionar") != null) {
			usarPrediccionAnterior = false;
			log.debug("Pasando de seleccionar pelicula2 a asistencia pelicula dia");
			Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute("datosGraficos");
			if (map == null)
				map = new HashMap<String, Object>();
			map.put("id_pelicula", id_pelicula);
			map.put("id_complejos", id_complejos);
			map.put("id_peliculas_seleccionadas", id_peliculas_seleccionadas);
			map.put("fecha", fecha);
			map.put("dias_a_predecir", new Integer(dias_a_predecir));
			log.debug("peliculas seleccionadas" + new Integer(id_peliculas_seleccionadas.length));
			map.put("nro_peliculas_seleccionadas", new Integer(id_peliculas_seleccionadas.length));
			
			request.getSession().setAttribute("seleccion", new Integer(2));
			
			
			int maxCantPeliculasValidas = 0;

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
			// color ultima prediccion
			color = new ColorSerie();
			color.setIdSerie(idUltimaPrediccion);
			color.setColor(Color.generarColor());
			colores.add(color);

			// color pelicula
			color = new ColorSerie();
			color.setIdSerie(id_pelicula);
			color.setColor(Color.generarColor());
			colores.add(color);

			// Setear Dia estreno
			Parametro r = serviciosRM.obtenerParametro("Cine", "Dia_Estreno");
			int diaEstreno = Integer.parseInt(r.getCodigo());
			log.debug("diaEstreno = "+diaEstreno);
			// Obtener pelicula seleccionada
			Pelicula peliculaSeleccionada = peliculaDao.obtenerPelicula(id_pelicula);

			// Fecha Actual
			GregorianCalendar fechaPredecirDesde = new GregorianCalendar();
			fechaPredecirDesde.setTime(Calendar.getInstance().getTime());
			// Prueba loca:
			fechaPredecirDesde.setTime(Util.StringToDate(fecha));
			// fechaPredecirDesde ya no corresponde a "hoy" sino a la fecha que
			// puso el usuario a partir de la cual
			// quiere predecir.

			// Recorrer por complejo
			List<Map<String, Object>> graficos = new ArrayList<Map<String, Object>>();
			CopyOnWriteArrayList<Prediccion> predicciones = new CopyOnWriteArrayList<Prediccion>();

			List<String> graficosMalos = new ArrayList<String>();
			log.debug("Revisando todos los complejos.");
			
			for (int j = 0; j < id_complejos.length; j++)
				maxCantPeliculasValidas = realizarPrediccion(request, id_pelicula, id_complejos, id_peliculas_seleccionadas,
					dias_a_predecir, fecha, maxCantPeliculasValidas, usuario, colores, diaEstreno, peliculaSeleccionada,
					fechaPredecirDesde, graficos, predicciones, graficosMalos, j, null);

			map.put("nombrePelicula", peliculaDao.obtenerPelicula(id_pelicula).getNombre());
			map.put("graficos", graficos);
			map.put("maxCantPeliculasValidas", maxCantPeliculasValidas);
			map.put("predicciones", predicciones);
			
			log.debug( "Se agrego la siguiente prediccion: ");
			/*
			Iterator yay = predicciones.iterator();
			while (yay.hasNext()) {
				Prediccion p = (Prediccion) yay.next();
				log.debug("Complejo: "+ p.complejo.getNombre());
				log.debug("Pelicula: " + p.pelicula.getNombre());
				log.debug("Predicciones: ");
				String bar = "";
				Iterator foo = p.prediccionPorDia.iterator();
				while (foo.hasNext()) {
					PrediccionPorDia ppd = (PrediccionPorDia)foo.next();
					bar += Util.DateToString(ppd.getFecha()) + ":";
					bar += ppd.getEstimacion() + " ";
				}
				log.debug(bar);
			}
			*/

			Boolean hayGraficosMalos = new Boolean(false);
			if (graficosMalos.size() > 0) {
				hayGraficosMalos = new Boolean(true);
				map.put("graficosMalos", graficosMalos);
			}
			map.put("hayGraficosMalos", hayGraficosMalos);

			if (request.getSession().getAttribute("predicciones") == null)
				request.getSession().setAttribute("predicciones", new CopyOnWriteArrayList<Prediccion>());

			safelyRemoveAttribute(request, "datosGraficos");
			safelyRemoveAttribute(request, "notNuevaPrediccion");
			
			request.getSession().setAttribute("datosGraficos", map);

			return new ModelAndView(new RedirectView(getSuccessView()));
		}
		
		if (request.getParameter("prediccionanterior") != null) {
			log.debug("Se usara prediccion anterior");
			usarPrediccionAnterior = true;
			Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute("datosGraficos");
			if (map == null)
				map = new HashMap<String, Object>();
			map.put("id_pelicula", id_pelicula);
			map.put("id_complejos", id_complejos);
			
			// Obtener Usuario
			String user = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario usuario = null;
			usuario = usuarioDao.obtenerUsuarioByName(user);
			map.put("nombreUsuario", usuario.getNombreCompleto());
			

			
			Map<String, Object> parametrosUltimaPrediccion = new HashMap<String, Object>();
			parametrosUltimaPrediccion.put("idUsuario", new Integer(usuario.getId()));
			parametrosUltimaPrediccion.put("idPelicula", new Integer(id_pelicula));
			//duda: �solo un complejo?
			log.debug("Parametros ultima predicion: usuario-" + (Integer) parametrosUltimaPrediccion.get("idUsuario") + " pelicula-"
											+ (Integer) parametrosUltimaPrediccion.get("idPelicula"));

			Prediccion ultimaPrediccion = prediccionDao.obtenerUltimaPrediccionCualquierComplejo(parametrosUltimaPrediccion);

			if (ultimaPrediccion == null) {
				// Nunca se había hecho una predicción para esta película. Devolvemos a la vista.
				ModelAndView mv = showForm(request,errors,"seleccionarpeliculas2");
				mv.addObject("noHayUltimaPrediccion", true);
				return mv;
				
			}
			
			if (ultimaPrediccion != null)
				log.debug("obtenerUltimaPrediccion: " + ultimaPrediccion.getId() + ": " + Util.DateToString(ultimaPrediccion.getFecha()));
			
			//obtener la lista de pel�culas de la �ltima predicci�n de la pel�cula

			//List<PrediccionParametros> listaPeliculas = prediccionDao.obtenerParametrosPrediccion(ultimaPrediccion.getId());
			Map<String, Object> parametrosLista = new HashMap<String, Object>();

			parametrosLista.put("prediccion_id", new Integer(ultimaPrediccion.getId()));
			parametrosLista.put("pelicula_id", new Integer(id_pelicula));
			List<PrediccionParametros> listaPeliculas = prediccionDao.obtenerPonderacionesPeliculas(parametrosLista);
			id_peliculas_seleccionadas = new int[listaPeliculas.size()];//convertir la lista de pel�culas de la predicci�n en un arreglo
			 
			int ind = 0;
			//Convertir de List<Integer> a int[]
			Iterator<PrediccionParametros> itListaPeliculas = listaPeliculas.iterator();

			log.debug("Lista de pel�culas usadas previamente");
			PrediccionParametros pps = null;
			while(itListaPeliculas.hasNext()){
				pps = (PrediccionParametros)itListaPeliculas.next();
				id_peliculas_seleccionadas[ind] = pps.getPelicula().getId();
				log.debug("PrediccionPelicula(id) = "+id_peliculas_seleccionadas[ind]);
				++ind;
			}
			
			map.put("id_peliculas_seleccionadas", id_peliculas_seleccionadas);//esto se debe sacar de la predicci�n anterior
			map.put("fecha", fecha);
			map.put("dias_a_predecir", new Integer(dias_a_predecir));
			log.debug("peliculas seleccionadas = " + new Integer(id_peliculas_seleccionadas.length));
			map.put("nro_peliculas_seleccionadas", new Integer(id_peliculas_seleccionadas.length));
			
			request.getSession().setAttribute("seleccion", new Integer(2));
			
			int maxCantPeliculasValidas = 0;

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
			// color ultima prediccion
			color = new ColorSerie();
			color.setIdSerie(idUltimaPrediccion);
			color.setColor(Color.generarColor());
			colores.add(color);

			// color pelicula
			color = new ColorSerie();
			color.setIdSerie(id_pelicula);
			color.setColor(Color.generarColor());
			colores.add(color);

			// Setear Dia estreno
			Parametro r = serviciosRM.obtenerParametro("Cine", "Dia_Estreno");
			int diaEstreno = Integer.parseInt(r.getCodigo());
			log.debug("diaEstreno = "+diaEstreno);
			// Obtener pelicula seleccionada
			Pelicula peliculaSeleccionada = peliculaDao.obtenerPelicula(id_pelicula);

			// Fecha Actual
			GregorianCalendar fechaPredecirDesde = new GregorianCalendar();
			fechaPredecirDesde.setTime(Calendar.getInstance().getTime());
			// Prueba loca:
			fechaPredecirDesde.setTime(Util.StringToDate(fecha));
			// fechaPredecirDesde ya no corresponde a "hoy" sino a la fecha que
			// puso el usuario a partir de la cual
			// quiere predecir.

			// Recorrer por complejo
			List<Map<String, Object>> graficos = new ArrayList<Map<String, Object>>();
			CopyOnWriteArrayList<Prediccion> predicciones = new CopyOnWriteArrayList<Prediccion>();

			List<String> graficosMalos = new ArrayList<String>();
			log.debug("Revisando todos los complejos.");
			
			ArrayList<PrediccionParametros> overridePesos = (ArrayList)prediccionDao.obtenerParametrosPrediccion(prediccionDao.obtenerUltimaPrediccionCualquierComplejo(parametrosUltimaPrediccion).getId());
			
			for (int j = 0; j < id_complejos.length; j++)
				maxCantPeliculasValidas = realizarPrediccion(request, id_pelicula, id_complejos, id_peliculas_seleccionadas,
					dias_a_predecir, fecha, maxCantPeliculasValidas, usuario, colores, diaEstreno, peliculaSeleccionada,
					fechaPredecirDesde, graficos, predicciones, graficosMalos, j, overridePesos);

			map.put("nombrePelicula", peliculaDao.obtenerPelicula(id_pelicula).getNombre());
			map.put("graficos", graficos);
			map.put("maxCantPeliculasValidas", maxCantPeliculasValidas);
			map.put("predicciones", predicciones);
			
			log.debug("Se agrego la siguiente prediccion: ");
			
			Boolean hayGraficosMalos = new Boolean(false);
			if (graficosMalos.size() > 0) {
				hayGraficosMalos = new Boolean(true);
				map.put("graficosMalos", graficosMalos);
			}
			map.put("hayGraficosMalos", hayGraficosMalos);
			
			//duda
			if (request.getSession().getAttribute("predicciones") == null)
				request.getSession().setAttribute("predicciones", new CopyOnWriteArrayList<Prediccion>());

			safelyRemoveAttribute(request, "datosGraficos");
			safelyRemoveAttribute(request, "notNuevaPrediccion");
			
			request.getSession().setAttribute("datosGraficos", map);

			return new ModelAndView(new RedirectView(getSuccessView()));
		}

		return null;

	}

	private void safelyRemoveAttribute(HttpServletRequest request, String string) {
		if (request.getSession().getAttribute(string) != null) {
			request.getSession().removeAttribute(string);
		}
	}

	protected int realizarPrediccion(
			HttpServletRequest request, int id_pelicula, int[] id_complejos, int[] id_peliculas_seleccionadas, int dias_a_predecir,
			String fecha, int maxCantPeliculasValidas, Usuario usuario, List<ColorSerie> colores, int diaEstreno,
			Pelicula peliculaSeleccionada, GregorianCalendar fechaPredecirDesde, List<Map<String, Object>> graficos,
			CopyOnWriteArrayList<Prediccion> predicciones, List<String> graficosMalos, int j, ArrayList<PrediccionParametros> overridePesos) {
		Parametro r;
		{

			log.debug("Revisando complejo j="+j+" "+id_complejos[j]);
			Map<String, Object> grafico = new HashMap<String, Object>();

			List<SerieDatos> seriesDatAnt = new ArrayList<SerieDatos>();
			SerieDatos serieDatAnt;
			List<Valor> valoresAnt;

			List<SerieDatos> seriesDat = new ArrayList<SerieDatos>();
			SerieDatos serieDat;
			List<Valor> valores;
			List<Double> pesosDias;

			Complejo complejo = complejoDao.obtenerComplejo(id_complejos[j]);

			Funcion primaFuncionSeleccionada = funcionDao.obtenerPrimeraFuncion(peliculaSeleccionada, complejo, diaEstreno);
			log.debug("Pelicula seleccionada:" + peliculaSeleccionada.getNombre());

			if (primaFuncionSeleccionada != null) {
				//log.debug("Primera funcion: " + Util.DateToString(primaFuncionSeleccionada.getFecha()));
				GregorianCalendar fechaSeleccionada = new GregorianCalendar();
				fechaSeleccionada.setTime(primaFuncionSeleccionada.getFecha());
				
				/* diferencia entre fecha desde y fecha de la primera funci�n */
				int diferenciaDias = Util.diferenciaFechas(fechaPredecirDesde, fechaSeleccionada);
				/*
				log.debug("fechaPredecirDesde = " + Util.DateToString(fechaPredecirDesde.getTime()));
				log.debug("fechaSeleccionada = " + Util.DateToString(fechaSeleccionada.getTime()));
				log.debug("diferenciaDias = fechaPredecirDesde - fechaSeleccionada = " + diferenciaDias);
				*/
				/* d�a de ayer */
				GregorianCalendar diaActual = new GregorianCalendar();
				diaActual.setTime(Calendar.getInstance().getTime());
				diaActual.add( Calendar.DATE, -1 );
				GregorianCalendar fechaAyer = diaActual;
				
				/* diferencia entre fecha de ayer y fecha de la primera funci�n */
				int diferenciaDias2 = Util.diferenciaFechas(fechaAyer, fechaSeleccionada);
							
				if (diferenciaDias >= 0) {
					// Calcular el max de d�as a ver hacia atr�s (desde el registro)
					r = serviciosRM.obtenerParametro("Revenue Manager", "prediccion_dias_atras");
					int diasAntesPrediccion = Integer.parseInt(r.getCodigo());
					if (diasAntesPrediccion > diferenciaDias2)
						diasAntesPrediccion = diferenciaDias2;

					//log.debug("diasAntesPrediccion = " + diasAntesPrediccion);
					
					log.debug("Categor'ias futuro");
					// Crear las categorias de las predicciones (futuro)
					List<CategoriaGrafico> catGraf = new ArrayList<CategoriaGrafico>();
					for (int i = diaEstreno + (diferenciaDias + 1); i < dias_a_predecir + (diferenciaDias + 1) + diaEstreno; i++) {
						catGraf.add(new CategoriaGrafico(Dia.getDia(i) + (i - diaEstreno + 1)));
						log.debug("new CategoriaGrafico = "+(Dia.getDia(i) + (i - diaEstreno + 1)));
						
					}

					log.debug("Categor�as hist�ricas");
					// Crear las categorias anteriores (hist�rico)
					List<CategoriaGrafico> catGrafAnt = new ArrayList<CategoriaGrafico>();
					for (int i = diaEstreno + ((diferenciaDias2 + 1) - (diasAntesPrediccion - 1));
						 i <= (diferenciaDias2 + 1) + diaEstreno; i++) {
						catGrafAnt.add(new CategoriaGrafico(Dia.getDia(i - 1) + (i - diaEstreno)));
						log.debug("new CategoriaGrafico = "+(Dia.getDia(i - 1) + (i - diaEstreno)));
					}

					// a�adir peliculas para predecir (futuro)
					log.info("Agregando peliculas para predecir");
					log.debug(".........");
					for (int i = 0; i < id_peliculas_seleccionadas.length; i++) {
						//se recorren las pel�culas seleccionadas
						Pelicula pelicula = peliculaDao.obtenerPelicula(id_peliculas_seleccionadas[i]);
						
						log.debug("Pelicula: " + pelicula.getNombre());

						Funcion primaFuncion = funcionDao.obtenerPrimeraFuncion(pelicula, complejo, diaEstreno);

						if (primaFuncion != null) {
							//log.debug("Estreno: " + primaFuncion.getId() + " " + primaFuncion.getFecha().toString());

							GregorianCalendar fechaPrimeraFuncion = new GregorianCalendar();
							fechaPrimeraFuncion.setTime(primaFuncion.getFecha());

							GregorianCalendar fechaFuncion = new GregorianCalendar();
							fechaFuncion.setTime(primaFuncion.getFecha());
							fechaFuncion = new GregorianCalendar(fechaFuncion.get(Calendar.YEAR), fechaFuncion.get(Calendar.MONTH),
								fechaFuncion.get(Calendar.DAY_OF_MONTH));

							fechaFuncion.add(Calendar.DAY_OF_MONTH, diferenciaDias + 1);

							int asistencias[] = prediccionManager.obtenerAsistenciaPorPeliculaDiaComplejo(pelicula, complejo,
								fechaFuncion, dias_a_predecir);
							serieDat = new SerieDatos();
							valores = new ArrayList<Valor>();
							pesosDias = new ArrayList<Double>();

							// Rellenar el valor con los datos necesarios
							// para predecir
							fechaFuncion.setTime(primaFuncion.getFecha());
							fechaFuncion = new GregorianCalendar(fechaFuncion.get(Calendar.YEAR), fechaFuncion.get(Calendar.MONTH),
							fechaFuncion.get(Calendar.DAY_OF_MONTH));
							fechaFuncion.add(Calendar.DAY_OF_MONTH, diferenciaDias + 1);
							for (int x = 0; x < asistencias.length; x++) {
								//log.debug("asistencias["+x+"] = "+asistencias[x]);
								Valor valor = new Valor(asistencias[x]);

								// Info
								List<String> info = new ArrayList<String>();
								info.add("Dia: " + Util.DateToString(fechaFuncion.getTime()));
								valor.setInfo(info);

								// Markers
								valor.setMarkers(markerDao.obtenerMarkersFechaComplejoPelicula(fechaFuncion.getTime(), complejo,
									pelicula));

								valores.add(valor);

								fechaFuncion.add(Calendar.DAY_OF_MONTH, 1);

								pesosDias.add(new Double(1.0));
							}

							// Info
							List<String> info = new ArrayList<String>();
							info.add("Pelicula: " + pelicula.getNombre());
							serieDat.setInfo(info);

							// Markers
							serieDat.setMarkers(pelicula.getMarkers());

							serieDat.setValores(valores);
							serieDat.setPesosDias(pesosDias);
							if (overridePesos!=null && id_peliculas_seleccionadas[i] == id_pelicula) {
								serieDat.setNombreSerie("Ultima Prediccion");
								serieDat.setColor(ColorSerie.colorSerie(colores, idUltimaPrediccion));

							} else {
								serieDat.setNombreSerie(pelicula.getNombre());
								serieDat.setColor(ColorSerie.colorSerie(colores, id_peliculas_seleccionadas[i]));

							}
							serieDat.setFecha(fechaPrimeraFuncion);

							seriesDat.add(serieDat);

							// a�adir lo que pas� antes
							fechaFuncion.setTime(primaFuncion.getFecha());
							fechaFuncion.add(Calendar.DAY_OF_MONTH, -(diasAntesPrediccion - 1) + diferenciaDias2);
							int asistenciasAnt[] = prediccionManager.obtenerAsistenciaPorPeliculaDiaComplejo(pelicula, complejo,
								fechaFuncion, diasAntesPrediccion);
							serieDatAnt = new SerieDatos();
							valoresAnt = new ArrayList<Valor>();

							//log.debug("Asistencias anteriores");
							for (int x = 0; x < asistenciasAnt.length; x++){
								valoresAnt.add(new Valor(asistenciasAnt[x]));
								//log.debug("asistenciasAnt["+x+"] = "+asistenciasAnt[x]);
							}

							serieDatAnt.setValores(valoresAnt); 
							serieDatAnt.setNombreSerie(pelicula.getNombre());
							serieDatAnt.setFecha(fechaPrimeraFuncion);
							serieDatAnt.setColor(ColorSerie.colorSerie(colores, id_peliculas_seleccionadas[i]));

							seriesDatAnt.add(serieDatAnt);
						}
					}
					
					log.debug(".........");

					if (seriesDat.size() > 0) {
						Map<String, Object> parametrosUltimaPrediccion = new HashMap<String, Object>();
						parametrosUltimaPrediccion.put("idUsuario", new Integer(usuario.getId()));
						parametrosUltimaPrediccion.put("idPelicula", new Integer(peliculaSeleccionada.getId()));
						parametrosUltimaPrediccion.put("idComplejo", new Integer(complejo.getId()));
						log.debug("Parametros ultima predicion: usuario-" + (Integer) parametrosUltimaPrediccion.get("idUsuario") + " pelicula-"
								+ (Integer) parametrosUltimaPrediccion.get("idPelicula") + " complejo-" + (Integer) parametrosUltimaPrediccion.get("idComplejo"));

						// Agregar ultima prediccion
						Prediccion ultimaPrediccion = prediccionDao.obtenerUltimaPrediccion(parametrosUltimaPrediccion);
						if (ultimaPrediccion != null && (overridePesos == null || usarPrediccionAnterior)) {
							// overridePesos no tiene nada que ver ah�, pero es para decidir si es que viene de PrediccionIncompleta
							// y est� barseando este m�todo, o si viene del cauce normal de los hechos
							log.debug("obtenerUltimaPrediccion: " + ultimaPrediccion.getId() + ": " + ultimaPrediccion.getFecha());

							List<PrediccionPorDia> prediccionesPorDia = ultimaPrediccion.getPrediccionPorDia();
							if (prediccionesPorDia != null) {
								Iterator<PrediccionPorDia> iterPredDia = prediccionesPorDia.iterator();
								PrediccionPorDia ppdia = null;
								while(iterPredDia.hasNext()){
									ppdia = (PrediccionPorDia)iterPredDia.next();
									log.debug(ppdia.getFecha()+"\t"+ppdia.getEstimacion());
								}
								
								// Setear la fecha de busqueda
								/*
								 * GregorianCalendar fechaInicialPrediccion
								 * = new GregorianCalendar(
								 * fechaActual.get(Calendar.YEAR),
								 * fechaActual.get(Calendar.MONTH),
								 * fechaActual .get(Calendar.DAY_OF_MONTH));
								 * //
								 */
								
								
								GregorianCalendar fechaInicialPrediccion = new GregorianCalendar();
								fechaInicialPrediccion.setTime(Util.StringToDate(fecha));
								
								/*
								GregorianCalendar fechaInicialPrediccion = fechaAyer;
								fechaInicialPrediccion.add(Calendar.DAY_OF_MONTH, diasAntesPrediccion);
								log.debug("-> FechaInicialPrediccion = "+fechaInicialPrediccion);
								*/
								
								// fechaInicialPrediccion.add(Calendar.DAY_OF_MONTH,
								// 1);

								// Buscar si la fecha se encuentra
								Iterator<PrediccionPorDia> itPredDia = prediccionesPorDia.iterator();
								PrediccionPorDia predDia;
								//boolean prediccionEnRango = false;
								valores = new ArrayList<Valor>();
								valoresAnt = new ArrayList<Valor>();

								GregorianCalendar fechaMarker = new GregorianCalendar();
								fechaMarker = new GregorianCalendar(fechaMarker.get(Calendar.YEAR), fechaMarker.get(Calendar.MONTH),
									fechaMarker.get(Calendar.DAY_OF_MONTH));
								
								GregorianCalendar fechaLimiteSuperiorAnteriores = new GregorianCalendar();
								log.debug("fechaLimiteSuperiorAnteriores0 = " + fechaLimiteSuperiorAnteriores.getTime());

								fechaLimiteSuperiorAnteriores.add(Calendar.DAY_OF_MONTH, -diasAntesPrediccion - 1);

								/*
								log.debug("Ahora revisando prediccionesPorDia");
								log.debug("fechaInicialPrediccion =  " + fechaInicialPrediccion.getTime());										
								log.debug("fechaLimiteSuperiorAnteriores = " + fechaLimiteSuperiorAnteriores.getTime());
								log.debug("fechaAyer = " + fechaAyer.getTime());
								*/
								
								/*
								while (itPredDia.hasNext()) {
									predDia = itPredDia.next();//predDia = estimacion por dia de ultima prediccion
									log.debug("@@@ fechaPredDia = " + predDia.getFecha());
									
									if (predDia.getFecha().compareTo(fechaInicialPrediccion.getTime()) >= 0) {
										log.debug("=== predDia.getFecha() = " + predDia.getFecha());
										log.debug("=== fechaInicialPrediccion.getTime() = " + fechaInicialPrediccion.getTime());
										prediccionEnRango = true;
										Valor vDia = new Valor(predDia.getEstimacion());
										log.debug("vDia = "+vDia.toString());

										// Info
										List<String> info = new ArrayList<String>();
										info.add("Dia: " + Util.DateToString(predDia.getFecha()));
										vDia.setInfo(info);

										// Markers
										vDia.setMarkers(markerDao.obtenerMarkersFechaComplejoPelicula(predDia.getFecha(), complejo,
											peliculaSeleccionada));

										fechaMarker.setTime(predDia.getFecha());
										valores.add(vDia);
										break;
									} else if (predDia.getFecha().compareTo(fechaLimiteSuperiorAnteriores.getTime()) >= 0
											&& predDia.getFecha().compareTo(fechaAyer.getTime()) <= 0){
										Valor vDiaAnt = new Valor(predDia.getEstimacion());
										log.debug("vDiaAnt = "+vDiaAnt.toString());
										valoresAnt.add(vDiaAnt);
									}
								}
								*/
								
								while (itPredDia.hasNext()) {
									predDia = itPredDia.next();//predDia = estimacion por dia de ultima prediccion
																	
									if (predDia.getFecha().compareTo(fechaLimiteSuperiorAnteriores.getTime()) >= 0) {
										if(predDia.getFecha().compareTo(fechaAyer.getTime()) > 0){
											break;
										}else{
											Valor vDiaAnt = new Valor(predDia.getEstimacion());
											log.debug("vDiaAnt = "+vDiaAnt.toString());
											valoresAnt.add(vDiaAnt);
										}
									}
								}
								
								

								// Agregar las predicciones por dia que
								// estan en el rango
								int contador = 0;
								/*
								prediccionEnRango = true;
								if (prediccionEnRango == true) {
									while (itPredDia.hasNext()) {
										predDia = itPredDia.next();
										if (contador < dias_a_predecir && predDia.getFecha().compareTo(fechaInicialPrediccion.getTime())>=0) {
											Valor vDia = new Valor(predDia.getEstimacion());

											// Info
											List<String> info = new ArrayList<String>();
											info.add("Dia: " + Util.DateToString(predDia.getFecha()));
											//log.debug("Dia: " + Util.DateToString(predDia.getFecha()));
											vDia.setInfo(info);

											// Markers
											vDia.setMarkers(markerDao.obtenerMarkersFechaComplejoPelicula(predDia.getFecha(),
												complejo, peliculaSeleccionada));

											fechaMarker.setTime(predDia.getFecha());
											valores.add(vDia);
											log.debug("### predDia = "+Util.DateToString(predDia.getFecha())+" \tvDia = " + vDia.getValor());
											contador++;
										} else
											break;
									}
								}
								*/
								
								// obtener los dias de la ultima prediccion que estan entre predecir_desde y predecir_desde + dias_a_predecir
								while (itPredDia.hasNext()) {
									predDia = itPredDia.next();
									if (predDia.getFecha().compareTo(fechaInicialPrediccion.getTime())>=0) {
										if(contador >= dias_a_predecir) break;
										Valor vDia = new Valor(predDia.getEstimacion());

										// Info
										List<String> info = new ArrayList<String>();
										info.add("Dia: " + Util.DateToString(predDia.getFecha()));
										//log.debug("Dia: " + Util.DateToString(predDia.getFecha()));
										vDia.setInfo(info);

										// Markers
										vDia.setMarkers(markerDao.obtenerMarkersFechaComplejoPelicula(predDia.getFecha(),
											complejo, peliculaSeleccionada));

										fechaMarker.setTime(predDia.getFecha());
										valores.add(vDia);
										log.debug("### predDia = "+Util.DateToString(predDia.getFecha())+" \tvDia = " + vDia.getValor());
										contador++;
									}
								}
								
								

								// Rellenar con 1s si a la prediccion le
								// faltan dias
								fechaMarker.add(Calendar.DAY_OF_MONTH, 1);
								while (contador < dias_a_predecir) {
									Valor vDia = new Valor(1);

									// Info
									List<String> info = new ArrayList<String>();
									info.add("Dia: " + Util.DateToString(fechaMarker.getTime()));
									log.debug("Dia: " + Util.DateToString(fechaMarker.getTime()));
									vDia.setInfo(info);

									// Markers
									vDia.setMarkers(markerDao.obtenerMarkersFechaComplejoPelicula(fechaMarker.getTime(), complejo,
										peliculaSeleccionada));

									fechaMarker.add(Calendar.DAY_OF_MONTH, 1);

									valores.add(vDia);
									contador++;
								}
								log.debug("contador = " + contador);

								// Setear los pesos por dia
								pesosDias = new ArrayList<Double>();
								for (int i = 0; i < dias_a_predecir; i++)
									pesosDias.add(new Double(1.0));

								// Agregar los datos al grafico
								serieDat = new SerieDatos();

								/*
								 * //Info List<String> info = new
								 * ArrayList<String>();
								 * info.add("Ultima Prediccion");
								 * serieDat.setInfo(info);
								 * 
								 * //Markers
								 * serieDat.setMarkers(peliculaSeleccionada
								 * .getMarkers());
								 */

								serieDat.setValores(valores);
								serieDat.setPesosDias(pesosDias);
								serieDat.setNombreSerie("Ultima Prediccion");
								serieDat.setFecha(fechaSeleccionada);
								serieDat.setColor(ColorSerie.colorSerie(colores, idUltimaPrediccion));

								seriesDat.add(serieDat);

								//Ajustar los valores anteriores
								if (valoresAnt.size() > diasAntesPrediccion)
									for (int i = valoresAnt.size(); i > diasAntesPrediccion; i--)
										valoresAnt.remove(0);
								else if (valoresAnt.size() < diasAntesPrediccion) {
									List<Valor> valoresAntAux = new ArrayList<Valor>();

									for (int contValoresFaltantes = diasAntesPrediccion - valoresAnt.size(); contValoresFaltantes > 0; contValoresFaltantes--) {
										Valor vDia = new Valor(0);
										valoresAntAux.add(vDia);
									}
									Iterator<Valor> itValoresAnt = valoresAnt.iterator();
									while (itValoresAnt.hasNext()){
										Valor v = itValoresAnt.next();
										valoresAntAux.add(v);
										log.debug("v = "+ v.toString());
									}
									valoresAnt = valoresAntAux;
								}

								// a�adir lo que pas� antes
								serieDatAnt = new SerieDatos();

								serieDatAnt.setValores(valoresAnt);
								serieDatAnt.setNombreSerie("Ultima Prediccion");
								serieDatAnt.setFecha(fechaSeleccionada);
								serieDatAnt.setColor(ColorSerie.colorSerie(colores, idUltimaPrediccion));

								seriesDatAnt.add(serieDatAnt);
							}
						}

						// Setear los pesos por pelicula iniciales
						Iterator<SerieDatos> itSeries = seriesDat.iterator();
						int indiceSeries = 0;
						log.debug("A setear los pesos de las peliculas.");
						
						int sizeSeriesDat = seriesDat.size();
						int sizeOverridePesos = 0;
						int limite = sizeSeriesDat;
						
						if (overridePesos != null) {
							sizeSeriesDat = seriesDat.size();
							sizeOverridePesos = overridePesos.size();
							
							limite = 0;
							if (sizeOverridePesos < sizeSeriesDat) limite = sizeSeriesDat - 1;
							else limite = sizeSeriesDat;
						}
						
						while (itSeries.hasNext() && indiceSeries < limite) {
							serieDat = itSeries.next();
							log.debug(" > Seteando a " + serieDat.getNombreSerie());
							if (overridePesos != null) {

								log.debug(" > > Override: seteando "
										+ overridePesos.get(indiceSeries)
												.getPonderacion());
								serieDat.setPesoPel(overridePesos.get(
										indiceSeries).getPonderacion());

							} else {
								log.debug(" > > Seteando "
										+ (new Double(1.0 / seriesDat.size())));
								serieDat.setPesoPel(new Double(1.0 / seriesDat
										.size()));
							}
							indiceSeries++;
						}

						////////// HACK!
					
						if (overridePesos != null) 
							if (sizeOverridePesos < sizeSeriesDat) 
								seriesDat.get(seriesDat.size()-1).setPesoPel(1.0);
						
						/////////// HASTA AQUI NO NAS
						
						// a�adir pelicula a predecir
						serieDat = new SerieDatos();
						valores = SerieDatos.calcularResultado(seriesDat);

						// crear la predicci�n
						Prediccion prediccion = new Prediccion();
						prediccion.setPelicula(peliculaSeleccionada);
						prediccion.setComplejo(complejo);
						prediccion.setFecha(Calendar.getInstance().getTime());
						prediccion.setUsuario(usuario);
						prediccion.setPrediccionPorDia(new ArrayList<PrediccionPorDia>());

						GregorianCalendar fechaPrediccion = new GregorianCalendar();
						fechaPrediccion = new GregorianCalendar();

						// En vez de partir ma�ana, la fecha de la
						// predicci�n parte en la fecha dada por el
						// formulario
						// GregorianCalendar fechaPrediccion = new
						// GregorianCalendar();
						fechaPrediccion.setTime(Util.StringToDate(fecha));

						Iterator<Valor> itValor = valores.iterator();
						Valor vDia;

						/*
						 * Este ciclo recorre los d�as indicados en "Dias a Predecir"
						 * */
						while (itValor.hasNext()) {
							vDia = itValor.next();

							// Info
							List<String> info = new ArrayList<String>();
							info.add("Dia: " + Util.DateToString(fechaPrediccion.getTime()));
							vDia.setInfo(info);

							// Markers
							vDia.setMarkers(markerDao.obtenerMarkersFechaComplejoPelicula(fechaPrediccion.getTime(), complejo,
								peliculaSeleccionada));

							PrediccionPorDia ppDia = new PrediccionPorDia();
							ppDia.setEstimacion((int) vDia.getValor());
							ppDia.setFecha(fechaPrediccion.getTime());
							ppDia.setPrediccion(prediccion);
							prediccion.getPrediccionPorDia().add(ppDia);

							fechaPrediccion.add(Calendar.DAY_OF_MONTH, 1);
							
						}
						
						ArrayList<PrediccionParametros> parametros = new ArrayList<PrediccionParametros>();
						
						
						//si se va a usar la informaci�n de la predicci�n anterior,
						//se deben incluir los ponderadores de esa predicci�n
						if(usarPrediccionAnterior){
							log.debug("------------------");
							log.debug("Se extraen par�metros de la �ltima predicci�n:");
							for (int i = 0; i < id_peliculas_seleccionadas.length; i++) {
								if (funcionDao.obtenerPrimeraFuncion(peliculaDao.obtenerPelicula(id_peliculas_seleccionadas[i]), complejo,
									diaEstreno) != null) {
									PrediccionParametros param = new PrediccionParametros();
									param.setPelicula(peliculaDao.obtenerPelicula(id_peliculas_seleccionadas[i]));
									param.setPrediccion(prediccion);
									
									Map<String, Object> parametrosUltimaPred = new HashMap<String, Object>();
									if ( prediccionDao.obtenerUltimaPrediccionCualquierComplejo(parametrosUltimaPrediccion) != null) {
										parametrosUltimaPred.put("prediccion_id",prediccionDao.obtenerUltimaPrediccionCualquierComplejo(parametrosUltimaPrediccion).getId());
									}
					
									
									parametrosUltimaPred.put("pelicula_id", id_peliculas_seleccionadas[i]);
																		
									param.setPonderacion(prediccionDao.obtenerPonderacion(parametrosUltimaPred));
									parametros.add(param);
									//log.debug("    Ponderaci�n: " + param.getPonderacion());
									log.debug(i + ". Pel�cula seleccionada: " + param.getPelicula().getNombre()+
											  "\tPred. = " + param.getPrediccion().getId() + 
											  "\tPond. = " + param.getPonderacion());
								} else {
									log.debug("No hay primeraFuncion para la pelicula " + peliculaDao.obtenerPelicula(id_peliculas_seleccionadas[i]).getNombre());
								}
							}
							log.debug("------------------");
							usarPrediccionAnterior = false;
						}else{
							log.debug("------------------");
							log.debug("Se crean a continuaci�n los par�metros de la predicci�n. ");
							for (int i = 0; i < id_peliculas_seleccionadas.length; i++) {
								if (funcionDao.obtenerPrimeraFuncion(peliculaDao.obtenerPelicula(id_peliculas_seleccionadas[i]), complejo,
									diaEstreno) != null) {
									PrediccionParametros param = new PrediccionParametros();
									param.setPelicula(peliculaDao.obtenerPelicula(id_peliculas_seleccionadas[i]));
									//log.debug(i + ". Pel�cula seleccionada: " + param.getPelicula().getNombre());
									param.setPrediccion(prediccion);
									//log.debug("    Predicci�n: " + param.getPrediccion().getId());
									param.setPonderacion(new Double(1.0 / seriesDat.size()));
									parametros.add(param);
									//log.debug("    Ponderaci�n: " + param.getPonderacion());
									log.debug(i + ". Pel�cula seleccionada: " + param.getPelicula().getNombre()+
											  "\t\tPred. = " + param.getPrediccion().getId() + 
											  "\t\tPond. = " + param.getPonderacion());
								} else {
									log.debug("No hay primeraFuncion para la pelicula " + peliculaDao.obtenerPelicula(id_peliculas_seleccionadas[i]).getNombre());
								}
							}
							log.debug("------------------");
							
							
						}
						
						// Aqu� hay que agregar tambi�n la �ltima predicci�n, si existe	
						if (prediccionDao.obtenerUltimaPrediccion(parametrosUltimaPrediccion) != null) {
							PrediccionParametros param = new PrediccionParametros();
							Prediccion predic = prediccionDao.obtenerUltimaPrediccion(parametrosUltimaPrediccion);
							//Aqu� hay un poco de contradicci�n: se supone que esta predicci�n no es una pel�cula sino una "pseudo-Pel�cula" 
							//que corresponde a la �ltima predicci�n realizada
							param.setPelicula(predic.getPelicula());
							log.debug("predic.getPelicula(): " + predic.getPelicula().getId() + " " + predic.getPelicula().getNombre());
							param.setPrediccion(prediccion);
							param.setPonderacion(new Double(1.0 / seriesDat.size()));
							parametros.add(param);
						}
						
						prediccion.setPrediccionParametros(parametros);
						predicciones.add(prediccion);
						
					

						// Guardar los datos de la pelicula seleccionada
						serieDat.setValores(valores);
						serieDat.setNombreSerie(peliculaSeleccionada.getNombre());
						serieDat.setSerieResultado();
						serieDat.setColor(ColorSerie.colorSerie(colores, id_pelicula));
						seriesDat.add(serieDat);
						

						// Guardar los datos del grafico
						GeneradorXMLGrafico gen = new GeneradorXMLGrafico();
						gen.setCategorias(catGraf);
						gen.setDatos(seriesDat);
						gen.setLegendaX("Dias");
						gen.setLegendaY1("Asistencia");
						gen.setTitulo("Prediccion para " + peliculaSeleccionada.getNombre() + " en " + complejo.getNombre());

						ArchivoGrafico graXML = new ArchivoGrafico(request, "asistencia" + (complejo.getNombre().replace(" ", ""))+j);
						gen.setRutaArchivo(graXML.rutaGraficoInterna());

						gen.aXML();

						/*
						 * //Info List<String> info = new
						 * ArrayList<String>();
						 * info.add("Complejo: "+complejo.getNombre());
						 * grafico.put("info", info);
						 * 
						 * //Markers grafico.put("Markers",
						 * markerDao.obtenerMarkersComplejo(complejo));
						 */

						grafico.put("genGraf", gen);

						if (seriesDat.size() - 1 > maxCantPeliculasValidas)
							maxCantPeliculasValidas = seriesDat.size() - 1;
						grafico.put("cantPeliculasValidas", new Integer(seriesDat.size() - 1));

						grafico.put("xmlGraf", graXML.rutaGraficoExterna());
						grafico.put("complejo", complejo.getNombre());
						grafico.put("fechaEstreno", fechaSeleccionada);
						graficos.add(grafico);

						// Aqui se crea el grafico de los dias anteriores a la fecha de prediccion
						GregorianCalendar fechaFuncion = new GregorianCalendar();
						fechaFuncion = new GregorianCalendar(fechaFuncion.get(Calendar.YEAR), fechaFuncion.get(Calendar.MONTH),
							fechaFuncion.get(Calendar.DAY_OF_MONTH));
						fechaFuncion.add(Calendar.DAY_OF_MONTH, -(diasAntesPrediccion - 1));
						log.debug("obtenerAsistenciaPorPeliculaDiaComplejo(" + peliculaSeleccionada.getNombre() + ", "
								+ complejo.getNombre() + ", " + Util.DateToString(fechaFuncion.getTime()) + ", "
								+ diasAntesPrediccion + ")");

						int asistenciasAnt[] = prediccionManager.obtenerAsistenciaPorPeliculaDiaComplejo(peliculaSeleccionada,
							complejo, fechaFuncion, diasAntesPrediccion);
						serieDatAnt = new SerieDatos();
						valoresAnt = new ArrayList<Valor>();

						/* Valores de asistencia de la pel�cula seleccionada dias anteriores a la fecha de prediccion */
						for (int x = 0; x < asistenciasAnt.length; x++) {
							valoresAnt.add(new Valor(asistenciasAnt[x]));
							log.debug("asistenciasAnt["+x+"]" + asistenciasAnt[x]);
						}

						serieDatAnt.setValores(valoresAnt);
						serieDatAnt.setNombreSerie(peliculaSeleccionada.getNombre());
						serieDatAnt.setColor(ColorSerie.colorSerie(colores, id_pelicula));
						seriesDatAnt.add(serieDatAnt);

						// Guardar los datos del grafico
						GeneradorXMLGrafico gen2 = new GeneradorXMLGrafico();
						gen2.setCategorias(catGrafAnt);
						gen2.setDatos(seriesDatAnt);
						gen2.setLegendaX("Dias");
						gen2.setLegendaY1("Asistencia");
						gen2.setTitulo("Asistencia Dias Anteriores a " + peliculaSeleccionada.getNombre() + " en "
								+ complejo.getNombre());

						ArchivoGrafico graXML2 = new ArchivoGrafico(request, "asistenciaAnt"
								+ (complejo.getNombre().replace(" ", ""))+j);
						gen2.setRutaArchivo(graXML2.rutaGraficoInterna());

						gen2.aXML();

						grafico.put("genGrafAnt", gen2);
						grafico.put("xmlGrafAnt", graXML2.rutaGraficoExterna());
					} else {
						graficosMalos.add(complejo.getNombre());
					}
				} else {
					graficosMalos.add(complejo.getNombre());
				}
			} else {
				graficosMalos.add(complejo.getNombre());
			}
		}
		return maxCantPeliculasValidas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.BaseCommandController#onBindAndValidate
	 * (javax.servlet.http.HttpServletRequest, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object arg0, BindException arg1) throws Exception {
		SeleccionarPeliculaForm form = (SeleccionarPeliculaForm) arg0;
		if (request.getParameter("filtrar") != null) {

			if (form.getId_complejos().length == 0)
				arg1.rejectValue("id_complejos", "error.complejos");
			if (form.getCategorias().length == 0)
				arg1.rejectValue("categorias", "error.categorias");
			if (form.getEpocas().length == 0)
				arg1.rejectValue("epocas", "error.epocas");
			if (form.getPublicos().length == 0)
				arg1.rejectValue("publicos", "error.publicos");

			ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "primeros_dias_a_elegir", "error.primeros_dias_a_elegir");
			ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "dias_a_saltar", "error.dias_a_saltar");
			ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "rango", "error.rango");
			

			try {
				@SuppressWarnings("unused")
				double rango = Double.parseDouble(form.getRango());
				if (Double.parseDouble(form.getRango()) == 0.0)
					arg1.rejectValue("rango", "error.notzero");
				
				if (form.getTipo_de_rango() == 1 && (Double.parseDouble(form.getRango()) < 0.0 || Double.parseDouble(form.getRango()) > 100))
					arg1.rejectValue("rango", "error.rango_porcentual");
				if (form.getTipo_de_rango() == 2 && Double.parseDouble(form.getRango()) < 0.0)
					arg1.rejectValue("rango", "error.rango_tickets");
				

			} catch (Exception e) {
				arg1.rejectValue("rango", "error.not-a-number");
			}

			if (form.getDias_a_saltar() >= form.getPrimeros_dias_a_elegir())
				arg1.rejectValue("dias_a_saltar", "error.dias_a_saltar_mayor");
			if (form.getPrimeros_dias_a_elegir() == 0)
				arg1.rejectValue("primeros_dias_a_elegir", "error.notzero");

		}

		if (request.getParameter("seleccionar") != null) {
			if (form.getId_peliculas_seleccionada().length == 0)
				arg1.rejectValue("id_peliculas_seleccionada", "error.peliculas_seleccionadas");

			ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "dias_a_predecir", "error.dias_a_predecir");

			if (form.getId_complejos().length == 0)
				arg1.rejectValue("id_complejos", "error.complejos");

			if (form.getDias_a_predecir() == 0)
				arg1.rejectValue("dias_a_predecir", "error.notzero");

		}
		
		if (request.getParameter("prediccionanterior") != null) {
			ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "dias_a_predecir", "error.dias_a_predecir");

			if (form.getId_complejos().length == 0)
				arg1.rejectValue("id_complejos", "error.complejos");

			if (form.getDias_a_predecir() == 0)
				arg1.rejectValue("dias_a_predecir", "error.notzero");

		}

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
		form.setDias_a_predecir(13);
		form.setDireccion("E");
		form.setRango("40");
		Calendar calendar = Calendar.getInstance();
		// form.setFecha(Util.DateToString(calendar.getTime()));
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		String fechaForm = Util.DateToString(calendar.getTime());
		form.setFecha(fechaForm);
		return form;
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
		map.put("peliculas", peliculaDao.obtenerListaPeliculasActivas());
		map.put("peliculas_filtradas", peliculaDao.obtenerListaPeliculas());
		map.put("categorias", categoriaDao.obtenerCategorias());
		map.put("epocas", epocaDao.obtenerEpocas());
		map.put("publicos", publicoDao.obtenerPublicos());
		map.put("complejos", complejoDao.complejosTodos());

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
	public void setPrediccionDao(PrediccionDao prediccionDao) {
		this.prediccionDao = prediccionDao;
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
