package cl.zpricing.avant.web.administrators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.servicios.ClaseDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.MascaraDao;
import cl.zpricing.avant.web.form.AsistenciasForm;

/**
 * <b>Clase controladora de la vista asistencias</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 29-12-2008 Daniel Estévez Garay: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class AsistenciasController extends SimpleFormController {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private FuncionDao funcionDao;
	private ClaseDao claseDao;
	private MascaraDao mascaraDao; 

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		AsistenciasForm asistenciasForm = (AsistenciasForm) command;
		
		int id = asistenciasForm.getFuncion();
		int mascaraId = asistenciasForm.getMascara();
		
		log.debug("Id Funcion a actualizar : [" + id + "]");
		log.debug("Id Mascara : [" + mascaraId + "]");
		
		Funcion funcion = funcionDao.obtenerFuncion(id);
		Mascara mascara = mascaraDao.obtenerMascara(mascaraId);
		
		if (mascara == null) {
			mascara = mascaraDao.obtenerMascara(funcion.getSala(), "full");
		}
		
		log.debug("Mascara : [" + mascara.getDescripcion() + "]");
		log.debug("PriceCard : [" + funcion.getPriceCardDescription() + "]");
		
		funcion.cambiaMascara(mascara);
		funcionDao.actualizarFuncion(funcion);
		
		/*
		Asistencia asistencia = new Asistencia();
		Clase clase = claseDao.obtenerClase(asistenciasForm.getClase());
		asistencia.setAsistencia(asistenciasForm.getAsistencia());
		asistencia.setClase(clase);
		asistencia.setFuncionAsociada(funcion);
		ArrayList<Asistencia> asistencias = funcion.getAsistenciasDeFuncion();
		asistencias.add(asistencia);
		funcion.setAsistenciasDeFuncion(asistencias);
		log.debug("Asistencia funcion id..." + asistencia.getFuncionAsociada().getId());
		funcionDao.actualizarAsistencia(funcion);
*/
		ModelAndView mv = showForm(request, response, errors);
		return mv;
	}

	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		log.debug("Iniciando formBackingObject...");
		int id = Integer.parseInt(request.getParameter("idfuncion"));
		
		Funcion funcion = funcionDao.obtenerFuncion(id);
		AsistenciasForm asistenciasForm = new AsistenciasForm();
		asistenciasForm.setFuncion(id);
		if (funcion.getMascara() != null) {
			asistenciasForm.setMascara(funcion.getMascara().getId());
		}
		else {
			asistenciasForm.setMascara(0);
		}
		return asistenciasForm;
	}

	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		int id = Integer.parseInt(request.getParameter("idfuncion"));
		Map<String, Object> map = new HashMap<String, Object>();

		Funcion funcion = funcionDao.obtenerFuncion(id);
		List<Mascara> mascaras = mascaraDao.obtenerMascarasSala(funcion.getSala());
		map.put("funcion", funcion);
		map.put("mascaras", mascaras);
		map.put("clases", claseDao.obtenerListaDeClases());

		return map;
	}

	public void setClaseDao(ClaseDao claseDao) {
		this.claseDao = claseDao;
	}
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}
	public void setMascaraDao(MascaraDao mascaraDao) {
		this.mascaraDao = mascaraDao;
	}
}
