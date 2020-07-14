package cl.zpricing.avant.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.AsistenciaDiaria;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.web.chart.MapeoDatosGraficoAsistenciaPorPelicula;
import cl.zpricing.commons.charts.GraficoSingleSeriesChartsFactory;
import cl.zpricing.commons.utils.ErroresUtils;

//public class AsistenciaPorPeliculaController extends BaseCommandController {
public class AsistenciaPorPeliculaController extends SimpleFormController {

	private FuncionDao funcionDao;
	private PeliculaDao peliculaDao;

	private Logger log = (Logger) Logger.getLogger(this.getClass());

	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}

	public PeliculaDao getPeliculaDao() {
		return peliculaDao;
	}

	/*
	 * @Override protected ModelAndView onSubmit(HttpServletRequest request,
	 * HttpServletResponse response, Object command, BindException errors)
	 * throws Exception { log.debug("onSubmit()"); AsistenciaPorPeliculaForm
	 * form = (AsistenciaPorPeliculaForm) command; int peliculaId =
	 * form.getPeliculaId(); ArrayList<AsistenciaDiaria> asistenciaDiaria; while
	 * (request.getParameterNames().hasMoreElements()) {
	 * log.debug((String)request.getParameterNames().nextElement()); }
	 * if(request.getParameter("peliculaId")!=null) { asistenciaDiaria =
	 * funcionDao
	 * .obtenerAsistenciaDiariaPorPelicula(Integer.parseInt(request.getParameter
	 * ("peliculaId"))); } else { asistenciaDiaria =
	 * funcionDao.obtenerAsistenciaDiariaPorPelicula(peliculaId); } ModelAndView
	 * mv = super.onSubmit(request, response, command, errors);
	 * 
	 * mv.addObject("peliculaNombre",
	 * peliculaDao.obtenerPelicula(peliculaId).getNombre());
	 * mv.addObject("asistenciaDiaria", asistenciaDiaria);
	 * 
	 * //mv.addObject("peliculas", peliculaDao.obtenerListaPeliculas());
	 * mv.setViewName("asistenciaporpelicula");
	 * 
	 * return mv; }
	 */

	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		// TODO Auto-generated method stub
		super.onBindAndValidate(request, command, errors);
	}

	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}

	public FuncionDao getFuncionDao() {
		return funcionDao;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {


		
		ArrayList<AsistenciaDiaria> asistenciaDiaria = new ArrayList<AsistenciaDiaria>();

		// ModelAndView mv = super.handleRequest(request, response);
		ModelAndView mv = new ModelAndView();
		if (request.getParameter("peliculaId") != null) {
			try {
				asistenciaDiaria = funcionDao
						.obtenerAsistenciaDiariaPorPelicula(Integer
								.parseInt(request.getParameter("peliculaId")));
			} catch (Exception e) {
				if (request.getParameter("peliculaId").equals("")) {
					return mv;
				} else {
					log
							.debug("No se pudo obtener la asistencia diaria por pelicula para peliculaId = "
									+ request.getParameter("peliculaId")
									+ ". Error: "
									+ ErroresUtils.extraeStackTrace(e));
					mv.addObject("error", "error.invalidMovieId");
					mv.addObject("peliculaId", request
							.getParameter("peliculaId"));
				}
				return mv;
			}

			// No sé si lo siguiente sea necesario
			if (asistenciaDiaria.size() == 0) {
				try {
					mv.addObject("peliculaNombre", peliculaDao.obtenerPelicula(
							Integer
									.parseInt(request
											.getParameter("peliculaId")))
							.getNombre());
				} catch (Exception e) {
					log.debug("La pelicula "
							+ request.getParameter("peliculaId")
							+ " no tiene nombre.");
				}
				return mv;
			}
			// Se agrega el nombre de la película:
			mv.addObject("nombrePelicula", peliculaDao.obtenerPelicula(
					Integer.parseInt(request.getParameter("peliculaId")))
					.getNombre());
			// Se agrega la fecha de estreno...
			mv.addObject("fechaEstreno", asistenciaDiaria.get(0).getFecha());
			// ... y la fecha de la última función
			mv.addObject("fechaTermino", asistenciaDiaria.get(
					asistenciaDiaria.size() - 1).getFecha());
			// Se agrega finalmente la List asistenciaDiaria con los datos de la
			// asistencia de todos los días. Esto es para mostrar la tabla.
			mv.addObject("asistenciaDiaria", asistenciaDiaria);

			// Crear el grafico
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nombrePelicula", peliculaDao.obtenerPelicula(
					Integer.parseInt(request.getParameter("peliculaId")))
					.getNombre());
			GraficoSingleSeriesChartsFactory factoria = new GraficoSingleSeriesChartsFactory();
			String xml = factoria.execute(asistenciaDiaria,
					new MapeoDatosGraficoAsistenciaPorPelicula(), parametros);
			// Agregar el gráfico
			mv.addObject("xml", xml);
		}
			
		// Listaylor

		return mv;
	}
}
