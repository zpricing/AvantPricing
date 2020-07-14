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
 * <b>Clase controladora para la modificaci�n de empresas</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 19-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * <li>1.1 25-01-2010 Camilo Araya: soporte para RptEmpresa.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ModifyEmpresasController extends SimpleFormController {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private EmpresaDao	empresaDao;

	/**
	 * @param empresaDao
	 */
	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

	/**
	 * @return
	 */
	public EmpresaDao getEmpresaDao() {
		return empresaDao;
	}

	/**
	 * ModelAndView: Recoge los valores del formulario y los guarda en un objeto
	 * usuario, el que psoteriormente se pasa al Dao para que actualize los
	 * valores del Usuario seg�n el id.
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 22/12/2008 Julio: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param command
	 *            Para obtener valores del formulario.
	 * @return Retorna la vista exitosa
	 * @throws ServletException
	 *             Excepcion general por si un servlet encuentra problemas.
	 * @since 1.0
	 */
	public ModelAndView onSubmit(Object command) throws ServletException {
		log.debug("@onSubmit() de ModifyEmpresasController.");

		String id_empresa = ((ManageEmpresasForm) command).getId();
		String nombre_empresa = ((ManageEmpresasForm) command).getNombre();
		String direccion = ((ManageEmpresasForm) command).getDireccion();
		String email = ((ManageEmpresasForm) command).getEmail();
		String rpt_empresa_id = ((ManageEmpresasForm) command).getRpt_empresa_id();

		Empresa empresa = new Empresa();

		empresa.setId(Integer.parseInt(id_empresa));
		empresa.setNombre(nombre_empresa);
		empresa.setDireccion(direccion);
		empresa.setEmail(email);
		empresa.setRptEmpresa(rpt_empresa_id.equalsIgnoreCase("") ? null : empresaDao.obtenerRptEmpresa(new Integer(
			Integer.parseInt(rpt_empresa_id))));

		log.debug("rpt_empresa_id (del form): " + rpt_empresa_id);
		empresaDao.actualizarEmpresa(empresa);

		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {

		String id_empresa = request.getParameter("id_empresa");
		Empresa empresa = new Empresa();

		empresa = empresaDao.obtenerEmpresa(Integer.parseInt(id_empresa));

		ManageEmpresasForm modifyform = new ManageEmpresasForm();

		modifyform.setId(id_empresa);
		modifyform.setNombre(empresa.getNombre());
		modifyform.setEmail(empresa.getEmail());
		modifyform.setDireccion(empresa.getDireccion());

		Integer idRptEmpresa = empresaDao.obtenerRptEmpresaIdParaEmpresa(Integer.parseInt(id_empresa));
		modifyform.setRpt_empresa_id(String.valueOf(empresaDao.obtenerRptEmpresa(idRptEmpresa) != null ? empresaDao.obtenerRptEmpresa(idRptEmpresa).getRpt_empresa_id() : 0));

		log.debug("modifyform.setRpt_empresa_id(" + modifyform.getRpt_empresa_id() + ");");
		return modifyform;
	}

	protected Map referenceData(HttpServletRequest request) throws Exception {

		Map<String, Object> refdata = new HashMap<String, Object>();

		List<RptEmpresa> rptEmpresas = new ArrayList<RptEmpresa>();
		rptEmpresas.add(null);
		rptEmpresas.addAll(empresaDao.obtenerRptEmpresaTodos());
		refdata.put("rptEmpresas", rptEmpresas);

		return refdata;
	}

}
