/**
 * 
 */
package cl.zpricing.avant.web.prediction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.model.Sala;
import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.model.prediction.PrediccionPorDia;
import cl.zpricing.avant.model.prediction.PrediccionPorFuncion;
import cl.zpricing.avant.negocio.PrediccionManager;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.MascaraDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.servicios.PrediccionDao;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;
import cl.zpricing.avant.servicios.UsuarioDao;
import cl.zpricing.avant.web.form.PrediccionPorClaseForm;

/**
 * <b>Controlador de la vista predicciones</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 07-01-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class PrediccionController extends SimpleFormController {
	private PrediccionDao prediccionDao;
	private ComplejoDao complejoDao;
	private UsuarioDao usuarioDao;
	private PeliculaDao peliculaDao;
	private PrediccionManager prediccionManager;
	private FuncionDao funcionDao;
	private ServiciosRevenueManager serviciosRM;
	private MascaraDao mascaraDao;
	
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		PrediccionPorClaseForm form = new PrediccionPorClaseForm();
		// log.debug("Entrando a formBackingObject...");
		double optimizacion_corte = Double.parseDouble(serviciosRM.obtenerParametro("Revenue Manager", "optimizacion_corte").getCodigo());
		CopyOnWriteArrayList<Prediccion> predicciones = (CopyOnWriteArrayList<Prediccion>) request.getSession().getAttribute("predicciones");

		// Esto es si es que acaso veníamos de una predicción incompleta, para
		// olvidarlo
		if (request.getSession().getAttribute("notNuevaPrediccion") != null) {
			request.getSession().removeAttribute("notNuevaPrediccion");
		}
		
		int maxNumDias = 0;
		int maxNumSalas = 0;
		int maxNumFunciones = 0;

		if (predicciones != null) {
			Iterator<Prediccion> it_pred = predicciones.iterator();
			while (it_pred.hasNext()) {
				Prediccion pred = it_pred.next();
				ArrayList<PrediccionPorDia> prediccionesPorDia = pred.getPrediccionPorDia();
				
				if (prediccionesPorDia != null) {
					maxNumDias = prediccionesPorDia.size() > maxNumDias ? prediccionesPorDia.size() : maxNumDias;
					
					Iterator<PrediccionPorDia> it_predd = prediccionesPorDia.iterator();
					while (it_predd.hasNext()) {
						ArrayList<Sala> salas = new ArrayList<Sala>();
						ArrayList<Integer> id_salas = new ArrayList<Integer>();
						PrediccionPorDia predd = it_predd.next();

						try {
							log.debug("Se va a mostrar la ppd: " + predd.getId());
						} catch (Exception e1) {
							log.debug("!! en formBackingObject de PrediccionController ya se había perdida el id de la ppd");
						}

						List<PrediccionPorFuncion> prediccionesPorFuncion = predd.getPrediccionesPorFuncion();
						if (prediccionesPorFuncion != null) {
							maxNumFunciones = prediccionesPorFuncion.size() > maxNumFunciones ? prediccionesPorFuncion.size() : maxNumFunciones;
							
							Iterator<PrediccionPorFuncion> it_predf = prediccionesPorFuncion.iterator();
							while (it_predf.hasNext()) {
								PrediccionPorFuncion predf = it_predf.next();
								try {
									log.debug(" Se va a mostrar ppf: " + predf.getId());
								} catch (Exception e) {
									log.debug("!! En formBackingObject de PrediccionController ya se perdió el id de las ppf.");
								}
								Sala sala = predf.getFuncionPredecida().getSala();
								if (!id_salas.contains(sala.getId())) {
									salas.add(sala);
									id_salas.add(sala.getId());
								}
							}
						}
						maxNumSalas = salas.size() > maxNumSalas ? salas.size() : maxNumSalas;
						predd.setSalasUtilizadas(salas);
					}
				}
			}

		}
		
		form = new PrediccionPorClaseForm(predicciones.size(), maxNumDias, maxNumSalas, maxNumFunciones);
		
		for (int i = 0 ; i < predicciones.size() ; i++) {
			for (int j = 0 ; j < predicciones.get(i).getPrediccionPorDia().size() ; j++ ) {
				for (int k = 0 ; k < predicciones.get(i).getPrediccionPorDia().get(j).getSalasUtilizadas().size() ; k++ ) {
					List<PrediccionPorFuncion> prediccionesPorFuncion = predicciones.get(i).getPrediccionPorDia().get(j).getPrediccionesPorFuncion(predicciones.get(i).getPrediccionPorDia().get(j).getSalasUtilizadas().get(k).getId());
					for (int w = 0 ; w < prediccionesPorFuncion.size() ; w++) {
						int mascara_default = prediccionesPorFuncion.get(w).getMascaraPorDefecto();
						double estimacion = prediccionesPorFuncion.get(w).getEstimacion();
						double capacidad = prediccionesPorFuncion.get(w).getFuncionPredecida().getSala().getCapacidad();
						double coef = estimacion / capacidad;
						
						log.debug("Mascara : [" + prediccionesPorFuncion.get(w).getMascara() + "]");
						
						if (prediccionesPorFuncion.get(w).getMascara() != null) {
							form.setMascaraFuncion(i, j, k, w, prediccionesPorFuncion.get(w).getMascara().getId());
						} else if (coef < optimizacion_corte)
							form.setMascaraFuncion(i, j, k, w, mascara_default);
						else {
							form.setMascaraFuncion(i, j, k, w, 0);
						}
					}
				}
			}
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		CopyOnWriteArrayList<Prediccion> predicciones = (CopyOnWriteArrayList<Prediccion>) request.getSession().getAttribute("predicciones");

		ArrayList<Complejo> complejos = new ArrayList<Complejo>();
		ArrayList<Integer> id_complejos = new ArrayList<Integer>();
		double optimizacion_corte = Double.parseDouble(serviciosRM.obtenerParametro("Revenue Manager", "optimizacion_corte").getCodigo());
		
		if (predicciones != null) {
			for (Prediccion pred : predicciones) {
				Complejo complejo = pred.getComplejo();
				if (!id_complejos.contains(complejo.getId())) {
					complejos.add(complejo);
					id_complejos.add(complejo.getId());
				}
				if (pred.getPrediccionPorDia() != null) {
					for (PrediccionPorDia predd : pred.getPrediccionPorDia()) {
						ArrayList<Sala> salas = new ArrayList<Sala>();
						ArrayList<Integer> id_salas = new ArrayList<Integer>();
						if ( predd.getPrediccionesPorFuncion() != null) {
							for (PrediccionPorFuncion predf : predd.getPrediccionesPorFuncion()) {
								Sala sala = predf.getFuncionPredecida().getSala();
								if (!id_salas.contains(sala.getId())) {
									salas.add(sala);
									id_salas.add(sala.getId());
								}
							}
						}
						predd.setSalasUtilizadas(salas);
					}
				}
			}
			map.put("complejos", complejos);
		}
		map.put("predicciones", predicciones);
		map.put("optimizacion_corte", optimizacion_corte);
		map.put("predicciones_guardadas", false);
		request.getSession().setAttribute("optimizar", 0);
		request.getSession().setAttribute("cargar", 0);

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		/** Glosario: PPF: predicción por función; PPD: predicción por día. */
		log.debug("PrediccionControler onSubmit");
		
		// Los atributos de sesión 'optimizar' y 'cargar' indican en qué parte del flujo nos encontramos, cuando se ha 
		// apretado el botón "Cargar y Optimizar". Parte con 0,0.
		int optimizar = (Integer) request.getSession().getAttribute("optimizar");
		int cargar = (Integer) request.getSession().getAttribute("cargar");
		
		log.debug("Parametros : ");
		log.debug("  Optimizar 	: [" + optimizar + "]");
		log.debug("  Cargar 	: [" + cargar + "]");
		
		PrediccionPorClaseForm form = (PrediccionPorClaseForm) command;
		CopyOnWriteArrayList<Prediccion> predicciones = (CopyOnWriteArrayList<Prediccion>) request.getSession().getAttribute("predicciones");
		
		for (int i = 0 ; i < predicciones.size() ; i++) {
			log.info("Pelicula : [" + predicciones.get(i).getPelicula().getDescripcion() + "]");
			for (int j = 0 ; j < predicciones.get(i).getPrediccionPorDia().size() ; j++ ) {
				log.info("  Dia : [" + predicciones.get(i).getPrediccionPorDia().get(j).getFecha() + "]");
				for (int k = 0 ; k < predicciones.get(i).getPrediccionPorDia().get(j).getSalasUtilizadas().size() ; k++ ) {
					log.info("    Sala : [" + predicciones.get(i).getPrediccionPorDia().get(j).getSalasUtilizadas().get(k).getId() + "]");
					int w = 0;
					for (PrediccionPorFuncion prediccionPorFuncion : predicciones.get(i).getPrediccionPorDia().get(j).getPrediccionesPorFuncion()) {
						if (prediccionPorFuncion.getFuncionPredecida().getSala().getId() == predicciones.get(i).getPrediccionPorDia().get(j).getSalasUtilizadas().get(k).getId()) {
							log.info("      i = [" + i + "] ; j = [" + j + "] ; k = [" + k + "] ; w = [" + w + "]");
							int mascara = form.getMascaraFuncion(i, j, k, w);
							log.info("      Mascara : [" + mascara + "]");
							
							if (mascara != 0) {
								Mascara mascara_temp = mascaraDao.obtenerMascara(mascara);
								log.info("      Mascara : [" + mascara + " - " + mascara_temp.getDescripcion() + "]");
								
								prediccionPorFuncion.setMascaraPorDefecto(form.getMascaraFuncion(i, j, k, w));
								prediccionPorFuncion.setMascara(mascara_temp);
								//prediccionPorFuncion.setCargando(true);
							}
							else {
								//TODO Habilitar optimizacion
								//OPTIMIZA
								//prediccionPorFuncion.setOptimizando(true);
								/*
								if (predf.getPrediccionesPorClase() == null) {
									prediccionManager.predecirDemandaPorClase(predf, tipo_regresion);
									predf.setOptimizada(true);
								}

								if (predf.getGraficoRegresionClases() != null) {
									int idgrc = Integer.parseInt(predf.getId() + "" + id);
									int idgcv = Integer.parseInt(predf.getId() + "" + id);
									++id;

									// Caso lineal:
									if (tipo_regresion == 1)
										predf.getGraficoRegresionClases().doStrXMLLin(true, "FusionCharts/FCF_MSLine.swf", idgrc, "", "", "2", "2", "Demanda", "Precio");

									// Caso exponencial:
									else if (tipo_regresion == 2)
										predf.getGraficoRegresionClases().doStrXMLExp(true, "FusionCharts/FCF_MSLine.swf", idgrc, "", "", "2", "2", "Demanda", "Precio");

									// Caso potencial:
									else if (tipo_regresion == 3)
										predf.getGraficoRegresionClases().doStrXMLPot(true, "FusionCharts/FCF_MSLine.swf", idgrc, "", "", "2", "2", "Demanda", "Precio");

									// Lineal por tramos (por defecto)
									else {
										predf.getGraficoRegresionClases().doStrXML(true, "FusionCharts/FCF_MSLine.swf", idgrc, "", "", "2", "2", "Demanda", "Precio");
									}
									predf.getGraficoRegresionCoef().doStrXMLExp(false, "FusionCharts/FCF_MSLine.swf", idgcv, "", "", "2", "2", "Precio", "Coeficiente de Variabilidad");
								}
								*/
							}
							w++;
						}
					}
				}
			}
		}
		guardarMascaras(predicciones);

		
		// Si se apreta el botón "Volver", se guardan las máscaras que se hayan escogido (para 
		// volver a ellas más adelante).
		if (request.getParameter("volver") != null) {
			/*
			// Obtenemos las predicciones para pasárselas al método que guarda las máscaras
			//CopyOnWriteArrayList<Prediccion> predicciones = (CopyOnWriteArrayList<Prediccion>) request.getSession().getAttribute("predicciones");

			// Guardamos las máscaras
			//guardarMascaras(command, predicciones);

			// Y finalmente volvemos a donde corresponda; 1 si veníamos de película estreno, 2 de película estrenada
			// y 3 si veníamos de predicción incompleta.
			Integer seleccion = (Integer) request.getAttribute("seleccion");
			if (seleccion == null) {
				seleccion = new Integer(1);
				request.setAttribute("seleccion", seleccion);
			}
			if (seleccion == 1)
				return new ModelAndView(new RedirectView("seleccionarpeliculas.htm"));
			else if (seleccion == 2)
				return new ModelAndView(new RedirectView("seleccionarpeliculas2.htm"));
			else if (seleccion == 3)
				return new ModelAndView(new RedirectView("prediccionincompleta.htm"));
		
			// Si se apreta el botón "Cargar" se entra aquí, y se guardan las predicciones por función que hayan tenido
			// una máscara asignada en el formulario (se salta aquellas que se haya puesto Optimizar). 
			 * 
			 */
		} 

		ModelAndView mv = showForm(request, response, errors);

		mv.addObject("predicciones", predicciones);
		mv.addObject("test", true);
		mv.addObject("terminado", true);
		mv.addObject("cargar_solo", false);
		mv.addObject("predicciones_guardadas", true);
		request.getSession().setAttribute("cargar", 1);

		return mv;
	}

	private void traspasarMascarasAlFormulario(CopyOnWriteArrayList<Prediccion> predicciones, String[] mascaras) {
		int i;
		i = 0;

		if (predicciones != null) {
			for (Prediccion prediccion : predicciones) {
				for (PrediccionPorDia thisPPD : prediccion.getPrediccionPorDia()) {
					if (thisPPD.getSalasUtilizadas() != null) {
						for (Sala sala : thisPPD.getSalasUtilizadas()) {
							if (thisPPD.getPrediccionesPorFuncion() != null) {
								for (PrediccionPorFuncion predf : thisPPD.getPrediccionesPorFuncion()) {
									if (predf.getFuncionPredecida().getSala().getId() == sala.getId()) {
										if (Integer.parseInt(mascaras[i]) == 0) {
											mascaras[i] = predf.getMascara().getId() + "";
										}
										i++;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void cargar(PrediccionPorClaseForm form, CopyOnWriteArrayList<Prediccion> predicciones, boolean cargarYOptimizar) {
		String[] mascaras = form.getMascaras();
		int i = 0;

		if (predicciones != null) {
			for (Prediccion thisPrediccion : predicciones) {
				ArrayList<PrediccionPorDia> prediccionesPorDia = thisPrediccion.getPrediccionPorDia();
				for (PrediccionPorDia thisPPD : prediccionesPorDia) {
					List<Sala> salasUtilizadas = thisPPD.getSalasUtilizadas();
					if (salasUtilizadas != null) {
						for (Sala thisSala : salasUtilizadas) {
							List<PrediccionPorFuncion> prediccionesPorFuncion = thisPPD.getPrediccionesPorFuncion();
							if (prediccionesPorFuncion != null) {
								for (PrediccionPorFuncion thisPPF : prediccionesPorFuncion){
									if (thisPPF.getFuncionPredecida().getSala().getId() == thisSala.getId()) {
										if (cargarYOptimizar) {
											// Entramos aquí si venimos de cargar una vez optimizadas algunas de las funciones
											// (i.e., habiendo presionado el botón "Cargar y Optimizar").
											if (thisPPF.isOptimizada()) {
												// no se optimiza por lo que le asigno la mascara escogida
												thisPPF.setMascaraPorDefecto(Integer.parseInt(mascaras[i]));
												thisPPF.setCargando(true);
											}
										} else {
											// Entramos aquí si venimos de haber presionado el botón "Cargar", sin optimizar.
											if (Integer.parseInt(mascaras[i]) != 0) {
												// no se optimiza por lo que le asigno la mascara escogida
												thisPPF.setMascaraPorDefecto(Integer.parseInt(mascaras[i]));
												thisPPF.setMascara(mascaraDao.obtenerMascara(Integer.parseInt(mascaras[i])));
												thisPPF.setOptimizada(false);
												thisPPF.setOptimizando(false);
												thisPPF.setCargando(true);
											} else {
												thisPPF.setCargando(false);
											}
										}
										i++;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void guardarMascaras(CopyOnWriteArrayList<Prediccion> predicciones) {
		int i = 0;
		// Recorremos las predicciones y dentro de cada una de ellas las
		// predicciones por día y por función
		for (Prediccion thisPrediccion : predicciones) {
			for (PrediccionPorDia thisPPD : thisPrediccion.getPrediccionPorDia()) {
				for (PrediccionPorFuncion thisPPF : thisPPD.getPrediccionesPorFuncion()) {
					if (thisPPF.getMascara() != null) {
						// Y finalmente se guarda en la base de datos.
						prediccionDao.actualizarPrediccionPorFuncion(thisPPF);
					}
					i++;
				}
			}
			thisPrediccion.setEstado(1);
			prediccionDao.actualizarPrediccion(thisPrediccion);
		}
	}

	public void setMascaraDao(MascaraDao mascaraDao) {
		this.mascaraDao = mascaraDao;
	}

	public void setServiciosRM(ServiciosRevenueManager serviciosRM) {
		this.serviciosRM = serviciosRM;
	}

	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}

	public void setPrediccionManager(PrediccionManager prediccionManager) {
		this.prediccionManager = prediccionManager;
	}

	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}

	public void setPrediccionDao(PrediccionDao prediccionDao) {
		this.prediccionDao = prediccionDao;
	}
}
