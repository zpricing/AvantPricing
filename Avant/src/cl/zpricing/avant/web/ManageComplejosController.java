
package cl.zpricing.avant.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Empresa;
import cl.zpricing.avant.model.RptComplejo;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.EmpresaDao;
import cl.zpricing.avant.web.form.ManageComplejosForm;

/**
 * <b>Descripci�n de la Clase</b>
 * Controlador de la vista para administrar complejos
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 24-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class ManageComplejosController extends SimpleFormController implements Controller{

	private ComplejoDao complejoDao;
	private EmpresaDao empresaDao;
	
/* (non-Javadoc)
 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object)
 */
	
	
public ModelAndView onSubmit(Object command)throws ServletException {
		
		String nombre_complejo = ((ManageComplejosForm) command).getNombre();
		String direccion_complejo = ((ManageComplejosForm) command).getDireccion();
		String empresa_complejo = ((ManageComplejosForm) command).getEmpresa();
		String ip_servidor = ((ManageComplejosForm) command).getIp_servidor();
		String complejo_id_externo = ((ManageComplejosForm) command).getComplejo_id_externo();
		int rptComplejoId;
		if (!((ManageComplejosForm) command).getIdNielsen().equalsIgnoreCase("")) {
			rptComplejoId = Integer.parseInt(((ManageComplejosForm) command).getIdNielsen());
		} else {
			rptComplejoId = 0;
		}
		
		
		Complejo complejo = new Complejo();
		
		Empresa empresa = new Empresa();
		empresa.setId(Integer.parseInt(empresa_complejo));
		
		complejo.setNombre(nombre_complejo);
		complejo.setDireccion(direccion_complejo);
		complejo.setEmpresa(empresa);
		complejo.setServidorIp(ip_servidor);
		complejo.setComplejo_id_externo(complejo_id_externo);
		complejo.setRptComplejo(rptComplejoId != 0 ? complejoDao.obtenerRptComplejo(rptComplejoId) : null);

		complejoDao.agregarComplejo(complejo);
		
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> refdata = new HashMap<String, Object>();
		
		List<Empresa> empresas = new ArrayList<Empresa>();
		empresas = empresaDao.empresasTodas();
		
		List<Complejo> complejos = new ArrayList<Complejo>();
		complejos = complejoDao.complejosTodos();
		
		String error= request.getParameter("error");
		
		refdata.put("empresas", empresas);
		refdata.put("complejos", complejos);
		refdata.put("error", error);
		
		List<RptComplejo> rptComplejos = new ArrayList<RptComplejo>();
		rptComplejos.add(null);
		rptComplejos.addAll(complejoDao.obtenerRptComplejosTodos());
		refdata.put("rptComplejos", rptComplejos);
		
		return refdata;
	}
	
	/**
	 * @param complejoDao
	 */
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

	/**
	 * @return complejoDao
	 */
	public ComplejoDao getComplejoDao() {
		return complejoDao;
	}
	
	/**
	 * @param empresaDao
	 */
	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	/**
	 * @return empresaDao
	 */
	public EmpresaDao getEmpresaDao() {
		return empresaDao;
	}

}
