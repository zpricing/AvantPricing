package cl.zpricing.avant.web.optimization;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.model.prediction.PrediccionPorDia;
import cl.zpricing.avant.model.prediction.PrediccionPorFuncion;
import cl.zpricing.avant.servicios.PrediccionDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;

/**
 * <b>Descripci�n de la Clase</b> Controlador encargado de la vista de carga al
 * ERP del cliente
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 04-02-2009 MARIO: versi�n inicial.</li>
 * <li>1.1 17-03-2009 MARIO: Agregado control que revisa si la carga esta
 * activada via registros.</li>
 * <li>1.2 15-05-2009 MARIO: Se agrega que siempre se considere cargada la
 * Prediccion por funcion si pasan por esta clase.</li>
 * <li>1.3 23-06-2009 MARIO: Se actualiza la prediccion por funcion en la base
 * de datos.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class CargaErpController implements Controller {

	/**
	 * Impresión de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private ServiciosRevenueManager ServiciosRM;
	private PrediccionDao prediccionDao;

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("CargaErpController: entrando a handleRequest...");
		// Obtengo la lista de predicciones que esta
		List<Prediccion> predicciones = (List<Prediccion>) request.getSession().getAttribute("predicciones");
		// Lista de las funciones que han sido cargadas.
		ArrayList<Funcion> cargadas = new ArrayList<Funcion>();
		ModelAndView mv = new ModelAndView("cargaerp");
		boolean popup = true;
		// Reviso el registro apropiado para ver si esta desactivada la carga
		int carga = Integer.parseInt(ServiciosRM.obtenerStringParametro("cine", "carga_automatizada"));
		// Reviso si no esta vacia para ejecutar el algoritmo de carga.
		if (predicciones != null && carga == 1) {
			Iterator<Prediccion> iter = predicciones.iterator();
			while (iter.hasNext()) {
				Prediccion pred = iter.next();
			}
			mv.addObject("cargadas", cargadas);
			try {
				if ((Integer) request.getSession().getAttribute("cargar") == 1) {
					popup = false;
				}
			} catch (Exception e) {

			}
		}
		// Se setea la variable de cada prediccion cargada=true para saber que
		// ya pasó por este proceso, ssi no la estábamos optimizando.
		Iterator<Prediccion> iter = predicciones.iterator();
		while (iter.hasNext()) {
			Prediccion pred = iter.next();
			Iterator<PrediccionPorDia> iterPPD = pred.getPrediccionPorDia().iterator();
			while (iterPPD.hasNext()) {
				PrediccionPorDia predPorDia = iterPPD.next();
				Iterator<PrediccionPorFuncion> iterPPF = predPorDia.getPrediccionesPorFuncion().iterator();
				while (iterPPF.hasNext()) {
					PrediccionPorFuncion predPorFuncion = iterPPF.next();

					if (predPorFuncion.isCargando()) {
						log.debug("setCargando(false)");
						predPorFuncion.setCargando(false);
						log.debug("setCargada(true)");
						predPorFuncion.setCargada(true);
					}
					prediccionDao.actualizarPrediccionPorFuncion(predPorFuncion);
				}
			}
		}
		mv.addObject("carga", carga);
		mv.addObject("popup", popup);
		return mv;
	}
	
	public void setServiciosRM(ServiciosRevenueManager serviciosRM) {
		ServiciosRM = serviciosRM;
	}
	public void setPrediccionDao(PrediccionDao prediccionDao) {
		this.prediccionDao = prediccionDao;
	}
}
