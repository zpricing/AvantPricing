package cl.zpricing.avant.web.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.RptComplejo;
import cl.zpricing.avant.model.RptEmpresa;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.EmpresaDao;

public class AuxiliarNielsenController extends SimpleFormController {
	private Logger		log	= (Logger) Logger.getLogger(this.getClass());
	private ComplejoDao	complejoDao;
	private EmpresaDao	empresaDao;

	// public ModelAndView handleRequestInternal(HttpServletRequest request,
	// HttpServletResponse response) {
	// HashMap<String, List> map = new HashMap<String, List>();
	// map.put("empresas",empresaDao.obtenerRptEmpresaTodos());
	// ModelAndView mv = new ModelAndView(getSuccessView());
	// mv.addObject("empresas", empresaDao.obtenerRptEmpresaTodos());
	// return null;
	//
	// }

	@SuppressWarnings("unchecked")
	public Map formBackingObject(HttpServletRequest request) {
		// Se le pasan todas las empresas y complejos para que los muestre.
		List<RptEmpresa> rptEmpresas = empresaDao.obtenerRptEmpresaTodos();
		List<RptComplejo> rptComplejos = complejoDao.obtenerRptComplejosTodos();

		HashMap map = new HashMap<String, Object>();
		map.put("rptEmpresas", rptEmpresas);
		map.put("rptComplejos", rptComplejos);

		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map referenceData(HttpServletRequest request) {
		List dominioCompleto = new ArrayList();
		HashMap map = new HashMap<String, Object>();


		if (request.getParameter("name") != null && request.getParameter("dominio") != null) {
			String name = request.getParameter("name");
			String dominio = request.getParameter("dominio");
			boolean multiple = false;
			
			if (dominio.equalsIgnoreCase("complejos")) {
				for (Complejo cx : complejoDao.complejosTodos()) {
					HashMap unComplejo = new HashMap();
					unComplejo.put("nombre", cx.getNombre());
					unComplejo.put("value", cx.getId());
					dominioCompleto.add(unComplejo);
				}
			}
			
			if (dominio.equalsIgnoreCase("rptEmpresas")) {
				for (RptEmpresa rpte : empresaDao.obtenerRptEmpresaTodos()) {
					HashMap unaEmpresa = new HashMap();
					unaEmpresa.put("nombre", rpte.getNombre()!=null || rpte.getNombre()!="" ? rpte.getCodigo_nielsen() + " (" + rpte.getNombre() + ")" : rpte.getCodigo_nielsen());
					unaEmpresa.put("value", rpte.getRpt_empresa_id());
					dominioCompleto.add(unaEmpresa);
				}
			}
			
			if (dominio.equalsIgnoreCase("rptEmpresasUseNameAsValue")) {
				for (RptEmpresa rpte : empresaDao.obtenerRptEmpresaTodos()) {
					HashMap unaEmpresa = new HashMap();
					unaEmpresa.put("nombre", rpte.getNombre()!=null || rpte.getNombre()!="" ? rpte.getCodigo_nielsen() + " (" + rpte.getNombre() + ")" : rpte.getCodigo_nielsen());
					unaEmpresa.put("value", rpte.getNombre()); // Esto puede causar problemas si la empresa no tiene nombre.
					dominioCompleto.add(unaEmpresa);
//					multiple = true;
				}
			}
			
			if (dominio.equalsIgnoreCase("rptComplejos")) {
				for (RptComplejo rptcx : complejoDao.obtenerRptComplejosTodos()) {
					HashMap unComplejo = new HashMap();
					unComplejo.put("nombre", rptcx.getNombre());
					unComplejo.put("value", rptcx.getNombre()); // El reporte ReporteNielsenPorCine requiere que se le pase el Nombre como valor, no la Id!
					dominioCompleto.add(unComplejo);
				}
			}
			
			if (dominio.equalsIgnoreCase("complejosNombres")) {
				for (Complejo cx : complejoDao.complejosTodos()) {
					HashMap unComplejo = new HashMap();
					unComplejo.put("nombre", cx.getNombre());
					unComplejo.put("value", cx.getNombre());
					dominioCompleto.add(unComplejo);
				}
			}
			// Finalmente, pasar name y dominioCompleto a la vista:
			map.put("name", name);
			map.put("dominioCompleto", dominioCompleto);
			map.put("multiple", multiple);
		}

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
