package cl.zpricing.avant.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Empresa;
import cl.zpricing.avant.model.RptComplejo;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.EmpresaDao;
import cl.zpricing.avant.web.form.ManageComplejosForm;

/**
 * <b>Descripción de la Clase</b> Controlador encargado de la modificación de
 * Complejos
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 26-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class ModifyComplejosController extends SimpleFormController {

	private ComplejoDao complejoDao;
	private EmpresaDao empresaDao;
	/**
	 * id_empresa_original es usado para guardar el id del combobox en el jsp
	 * original, cosa que cuando se pase al formulario de modificacion se
	 * seleccione la empresa original en el combobox.
	 */
	private int id_empresa_original;

	/**
	 * ModelAndView: Recoge los valores del formulario y los guarda en un objeto
	 * Complejo, el que posteriormente se pasa al Dao para que actualize los
	 * valores del Complejo seg�n el id.
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 22/12/2008 Julio: Versión Inicial</li>
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

		String id_complejo = ((ManageComplejosForm) command).getId();
		String nombre = ((ManageComplejosForm) command).getNombre();
		String id_empresa_complejo = ((ManageComplejosForm) command)
				.getEmpresa();
		String direccion = ((ManageComplejosForm) command).getDireccion();
		String ip_servidor = ((ManageComplejosForm) command).getIp_servidor();
		String complejo_id_externo = ((ManageComplejosForm) command)
				.getComplejo_id_externo();
		String rpt_complejo_id = ((ManageComplejosForm)command).getIdNielsen();

		Complejo complejo = new Complejo();
		/*
		 * Se utiliza un objeto empresa para sacar el id_empresa asociado al
		 * complejo
		 */
		Empresa empresa_complejo = new Empresa();

		empresa_complejo.setId(Integer.parseInt(id_empresa_complejo));

		complejo.setId(Integer.parseInt(id_complejo));
		complejo.setNombre(nombre);
		complejo.setDireccion(direccion);
		complejo.setEmpresa(empresa_complejo);
		complejo.setServidorIp(ip_servidor);
		complejo.setComplejo_id_externo(complejo_id_externo);
//		complejo.setRptComplejo(complejoDao.obtenerRptComplejo(Integer.parseInt(rpt_complejo_id)));
		complejo.setRptComplejo(!rpt_complejo_id.equalsIgnoreCase("") ? complejoDao.obtenerRptComplejo(Integer.parseInt(rpt_complejo_id)) : null);

		complejoDao.actualizarComplejo(complejo);

		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		String id_complejo = request.getParameter("id_complejo");
		Complejo complejo = new Complejo();

		complejo = complejoDao.obtenerComplejo(Integer.parseInt(id_complejo));

		ManageComplejosForm modifyform = new ManageComplejosForm();

		modifyform.setId(id_complejo);
		modifyform.setNombre(complejo.getNombre());
		modifyform.setDireccion(complejo.getDireccion());
		modifyform.setEmpresa(String.valueOf(complejo.getEmpresa()));
		modifyform.setIp_servidor(complejo.getServidorIp());
		modifyform.setComplejo_id_externo(complejo.getComplejo_id_externo());
		modifyform.setIdNielsen(String.valueOf(complejo.getRptComplejo() != null ? complejo.getRptComplejo().getRptComplejoId() : 0));

		/*
		 * guarda el id de la empresa original en el combobox para
		 * posteriormente seleccionar la ubicacion correcta en el combo del
		 * formulario de modificar
		 */
		id_empresa_original = complejo.getEmpresa().getId();

		return modifyform;
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

		empresas = empresaDao.empresasTodas();

		refdata.put("empresas", empresas);
		/*
		 * envia id de la empresa original al jsp, cuando se genera el select si
		 * la empresa a enviar es igual a la original del complejo se selecciona
		 * como Selected (default), todo esto mediante un control when.
		 */
		refdata.put("id_empresa_original", id_empresa_original);
		
		List<RptComplejo> rptComplejos = new ArrayList<RptComplejo>();
		rptComplejos.add(null);
		rptComplejos.addAll(complejoDao.obtenerRptComplejosTodos());
		refdata.put("rptComplejos", rptComplejos);

		return refdata;
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

}
