/**
 * 
 */
package cl.zpricing.avant.web.reports;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.RptComplejo;
import cl.zpricing.avant.model.RptEmpresa;
import cl.zpricing.avant.model.reports.SemanaNielsen;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.EmpresaDao;
import cl.zpricing.avant.util.Util;
import cl.zpricing.commons.utils.ErroresUtils;

/**
 * <b>Descripción de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 26-01-2010 Camilo Araya: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */

public class AdministrarNielsenController extends SimpleFormController {
	private Logger		log	= (Logger) Logger.getLogger(this.getClass());
	private ComplejoDao	complejoDao;
	private EmpresaDao	empresaDao;

	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView(getSuccessView());
		if (request.getParameter("operacion") != null && (request.getParameter("id") != null || request.getParameter("idComplejo") != null)) {
			String operacion = request.getParameter("operacion");
			if (operacion.equalsIgnoreCase("updateNombreEmpresa"))
				this.updateNombreEmpresa(request);
			if (operacion.equalsIgnoreCase("eliminarEmpresa"))
				this.eliminarEmpresa(request);
			if (operacion.equalsIgnoreCase("editarIdComplejo"))
				this.editarIdComplejo(request);
			if (operacion.equalsIgnoreCase("editarNombreComplejo"))
				this.editarNombreComplejo(request);
			if (operacion.equalsIgnoreCase("editarCantidadSalasComplejo"))
				this.editarCantidadSalasComplejo(request);
			if (operacion.equalsIgnoreCase("editarEmpresaComplejo"))
				this.editarEmpresaComplejo(request);
			if (operacion.equalsIgnoreCase("editarCiudadComplejo"))
				this.editarCiudadComplejo(request);
			if (operacion.equalsIgnoreCase("eliminarComplejo"))
				this.eliminarComplejo(request);
			if (operacion.equalsIgnoreCase("editarRmComplejo"))
				this.editarRmComplejo(request);
			if (operacion.equalsIgnoreCase("eliminarSemanaNielsen"))
				this.eliminarSemanaNielsen(request);
		} 
		mv.addAllObjects(referenceData(request));
		return mv;

		// return new ModelAndView(getSuccessView());

	}
	private void eliminarSemanaNielsen(HttpServletRequest request) {
		try {
			String fechaDesde = request.getParameter("fechaDesde");
			Date fecha = Util.StringToDate(fechaDesde);
			log.debug("Se borrarán los datos de Nielsen con fecha " + Util.DateToString(fecha));
			
			complejoDao.eliminarSemanaNielsen(fecha);
			
		} catch (Exception e) {
			log.debug(ErroresUtils.extraeStackTrace(e));
		}
		
	}
	private void editarRmComplejo(HttpServletRequest request) {
		try {
			String id = request.getParameter("idComplejo");
			String fechaDesde = request.getParameter("fechaDesde");
			String parametro = request.getParameter("nombre"); 
			
			RptComplejo rptComplejo = new RptComplejo();
			rptComplejo.setRptComplejoId(Integer.valueOf(id));
			rptComplejo.setFechaDesde(Util.StringToDate(fechaDesde));
			
			log.debug("parametro, que debería ser 'true' o 'false', aquí es " + parametro);
			rptComplejo.setRm(new Boolean(Boolean.parseBoolean(parametro)));
			log.debug("Luego rptComplejo.isRm() = " + rptComplejo.isRm());
			complejoDao.editarRmComplejo(rptComplejo);
		} catch (Exception e) {
			log.debug(ErroresUtils.extraeStackTrace(e));
		}
		
	}

	private void eliminarComplejo(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			String fechaDesde = request.getParameter("fechaDesde");
			RptComplejo rptComplejo = new RptComplejo();
			rptComplejo.setFechaDesde(Util.StringToDate(fechaDesde));
			rptComplejo.setRptComplejoId(Integer.parseInt(id));
			complejoDao.eliminarRptComplejo(rptComplejo);
		} catch (Exception e) {
			log.debug(ErroresUtils.extraeStackTrace(e));
		}
		
	}

	/* Complejos */
	private void editarCiudadComplejo(HttpServletRequest request) {
		try {
			String id = request.getParameter("idComplejo");
			String fechaDesde = request.getParameter("fechaDesde");
			String parametro = request.getParameter("nombre"); 
			
			RptComplejo rptComplejo = new RptComplejo();
			rptComplejo.setRptComplejoId(Integer.valueOf(id));
			rptComplejo.setFechaDesde(Util.StringToDate(fechaDesde));
			
			rptComplejo.setCiudad(parametro);  //Cambiar el set "blah"
			complejoDao.editarCiudadComplejo(rptComplejo); // Cambiar el método
		} catch (Exception e) {
			log.debug(ErroresUtils.extraeStackTrace(e));
		}
	}

	private void editarEmpresaComplejo(HttpServletRequest request) {
		try {
			String id = request.getParameter("idComplejo");
			String fechaDesde = request.getParameter("fechaDesde");
			String parametro = request.getParameter("nombre"); 
			
			log.debug("id:" + id);
			log.debug("fechaDesde:" + fechaDesde);
			log.debug("parametro:"+parametro);
			
			RptComplejo rptComplejo = new RptComplejo();
			rptComplejo.setRptComplejoId(Integer.valueOf(id));
			rptComplejo.setFechaDesde(Util.StringToDate(fechaDesde));
			
			rptComplejo.setRptEmpresa(empresaDao.obtenerRptEmpresa(Integer.valueOf(parametro)));  //Cambiar el set "blah"
			
			complejoDao.editarEmpresaComplejo(rptComplejo); // Cambiar el método
		} catch (Exception e) {
			log.debug(ErroresUtils.extraeStackTrace(e));
		}		
	}

	private void editarCantidadSalasComplejo(HttpServletRequest request) {
		try {
			String id = request.getParameter("idComplejo");
			String fechaDesde = request.getParameter("fechaDesde");
			String parametro = request.getParameter("nombre"); 
			
			RptComplejo rptComplejo = new RptComplejo();
			rptComplejo.setRptComplejoId(Integer.valueOf(id));
			rptComplejo.setFechaDesde(Util.StringToDate(fechaDesde));
			
			rptComplejo.setCantidadSalas(Integer.valueOf(parametro));
			
			complejoDao.editarCantidadSalasComplejo(rptComplejo); // Cambiar el método
		} catch (Exception e) {
			log.debug(ErroresUtils.extraeStackTrace(e));
		}		
	}

//	private void editarFechaDesdeComplejo(HttpServletRequest request) {
//		try {
//			String id = request.getParameter("idComplejo");
//			String fechaDesde = request.getParameter("fechaDesde");
//			String parametro = request.getParameter("nombre"); 
//			
//			RptComplejo rptComplejo = new RptComplejo();
//			rptComplejo.setRptComplejoId(Integer.valueOf(id));
//			rptComplejo.setFechaDesde(Util.StringToDate(fechaDesde));
//			
//			rptComplejo.setFechaDesde(Util.StringToDate(parametro));  //Cambiar el set "blah"
//			complejoDao.editarFechaDesdeComplejo(rptComplejo); // Cambiar el método
//		} catch (Exception e) {
//			log.debug(ErroresUtils.extraeStackTrace(e));
//		}		
//	}

	private void editarNombreComplejo(HttpServletRequest request) {
		try {
			
			String id = request.getParameter("idComplejo");
			String fechaDesde = request.getParameter("fechaDesde");
			String parametro = request.getParameter("nombre");
			
			log.debug("idComplejo:" + id);
			log.debug("fechaDesde:" + fechaDesde);
			log.debug("parametro:" + parametro);
			log.debug("¿Es convertible? " + Util.DateToString(Util.StringToDate(fechaDesde)));
			
			RptComplejo rptComplejo = new RptComplejo();
			rptComplejo.setRptComplejoId(Integer.valueOf(id));
			rptComplejo.setFechaDesde(Util.StringToDate(fechaDesde));
			
			rptComplejo.setNombre(parametro);
			
			complejoDao.editarNombreComplejo(rptComplejo); // Cambiar el método
		} catch (Exception e) {
			log.debug(ErroresUtils.extraeStackTrace(e));
		}		
	}

	private void editarIdComplejo(HttpServletRequest request) {
		/* Este también necesita un poco más de cariño */
		try {
			
			HashMap<String,Object> map = new HashMap<String, Object>();
			Integer newId = new Integer(Integer.parseInt(request.getParameter("nombre")));
			Integer oldId = new Integer(Integer.parseInt(request.getParameter("idComplejo")));
			map.put("oldId", oldId);
			map.put("newId", newId);
			map.put("fechaDesde", Util.StringToDate(request.getParameter("fechaDesde")));
			
			complejoDao.editarIdComplejo(map); // Cambiar el método
		} catch (Exception e) {
			log.debug(ErroresUtils.extraeStackTrace(e));
		}		
	}

	/* Empresas */
	private void eliminarEmpresa(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			RptEmpresa rptEmpresa = new RptEmpresa();
			rptEmpresa.setRpt_empresa_id(Integer.valueOf(id));
			empresaDao.eliminarRptEmpresa(rptEmpresa);
		} catch (Exception e) {
			log.debug(ErroresUtils.extraeStackTrace(e));
		}
		
	}

	private void updateNombreEmpresa(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			String nombre = request.getParameter("nombre");
			RptEmpresa rptEmpresa = new RptEmpresa();
			rptEmpresa.setRpt_empresa_id(Integer.valueOf(id));
			rptEmpresa.setNombre(nombre);
			empresaDao.actualizarNombreRptEmpresa(rptEmpresa);
		} catch (Exception e) {
			log.debug(ErroresUtils.extraeStackTrace(e));
		}
	}

	@SuppressWarnings("unchecked")
	public Map referenceData(HttpServletRequest request) {
		// Se le pasan todas las empresas y complejos para que los muestre.
		List<RptEmpresa> rptEmpresas = empresaDao.obtenerRptEmpresaTodos();
		List<RptComplejo> rptComplejos = complejoDao.obtenerRptComplejosTodos();
		List<SemanaNielsen> semanasNielsen = complejoDao.obtenerSemanasNielsen();
		
		HashMap map = new HashMap<String, Object>();
		map.put("rptEmpresas", rptEmpresas);
		map.put("rptComplejos", rptComplejos);
		map.put("semanasNielsen", semanasNielsen);

		return map;

	}

	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

	public ComplejoDao getComplejoDao() {
		return complejoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	public EmpresaDao getEmpresaDao() {
		return empresaDao;
	}
}
