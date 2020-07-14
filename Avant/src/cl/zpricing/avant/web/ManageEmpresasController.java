/**
 * 
 */
package cl.zpricing.avant.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Empresa;
import cl.zpricing.avant.model.RptEmpresa;
import cl.zpricing.avant.servicios.EmpresaDao;
import cl.zpricing.avant.web.form.ManageEmpresasForm;

/**
 * <b>Descripci�n de la Clase</b> Controlador de la vista para administrar
 * empresas
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 23-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ManageEmpresasController extends SimpleFormController {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private EmpresaDao empresaDao;

	/**
	 * @param empresaDao
	 *            the empresaDao to set
	 */
	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	/**
	 * @return the empresaDao
	 */
	public EmpresaDao getEmpresaDao() {
		return empresaDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java
	 * .lang.Object)
	 */
	public ModelAndView onSubmit(Object command) throws ServletException {

		
		String nombre_empresa = ((ManageEmpresasForm) command).getNombre();
		String direccion = ((ManageEmpresasForm) command).getDireccion();
		String email = ((ManageEmpresasForm) command).getEmail();
		String rpt_empresa_id = ((ManageEmpresasForm) command).getRpt_empresa_id();
		log.debug("rpt_empresa_id (del form): " + rpt_empresa_id);

		Empresa empresa = new Empresa();

		empresa.setNombre(nombre_empresa);
		empresa.setDireccion(direccion);
		empresa.setEmail(email);
		empresa.setRptEmpresa(!rpt_empresa_id.equalsIgnoreCase("") ? empresaDao.obtenerRptEmpresa(new Integer(Integer.parseInt(rpt_empresa_id))) : null);

		empresaDao.agregarEmpresa(empresa);

		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#referenceData
	 * (javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws Exception {

		Map<String, Object> refdata = new HashMap<String, Object>();

		List<Empresa> empresas = new ArrayList<Empresa>();

		String error = request.getParameter("error");

		empresas = empresaDao.empresasTodas();

		refdata.put("empresa", empresas);
		refdata.put("error", error);
		
		List<RptEmpresa> rptComplejos = new ArrayList<RptEmpresa>();
		rptComplejos.add(null);
		rptComplejos.addAll(empresaDao.obtenerRptEmpresaTodos());
		refdata.put("rptEmpresas", rptComplejos);


		return refdata;
	}
}
