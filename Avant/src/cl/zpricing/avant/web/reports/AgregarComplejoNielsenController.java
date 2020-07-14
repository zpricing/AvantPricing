package cl.zpricing.avant.web.reports;

import java.util.Date;
import java.util.GregorianCalendar;
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
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.RptComplejo;
import cl.zpricing.avant.model.RptEmpresa;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.EmpresaDao;
import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.form.AgregarComplejoNielsenForm;

public class AgregarComplejoNielsenController extends SimpleFormController {
	private Logger		log	= (Logger) Logger.getLogger(this.getClass());
	private ComplejoDao	complejoDao;
	private EmpresaDao	empresaDao;

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

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		log.debug("@onSubmit() de AgregarComplejoNielsenController");

		AgregarComplejoNielsenForm form = (AgregarComplejoNielsenForm) command;
		int id = form.getId();
		String nombre = form.getNombre();
		Date fechaDesde = Util.StringToDate(form.getFechaDesde());
		int cantidadSalas = form.getCantidadSalas();
		int empresaId = form.getEmpresaId();
		String ciudad = form.getCiudad();
		boolean rm = form.isRm();

		RptComplejo rptComplejo = new RptComplejo();

		rptComplejo.setRptComplejoId(id);
		rptComplejo.setNombre(nombre);
		rptComplejo.setFechaDesde(fechaDesde);
		rptComplejo.setCantidadSalas(cantidadSalas);
		rptComplejo.setRptEmpresa(empresaDao.obtenerRptEmpresa(empresaId));
		rptComplejo.setCiudad(ciudad);
		rptComplejo.setRm(new Boolean(rm));

		complejoDao.agregarRptComplejo(rptComplejo);

		return new ModelAndView(new RedirectView("administrarNielsen.htm"));

	}

	@SuppressWarnings("unchecked")
	public Map referenceData(HttpServletRequest request) {
		HashMap map = new HashMap<String, Object>();

		// Se le pasan todas las empresas y complejos para que los muestre.
		List<RptEmpresa> rptEmpresas = empresaDao.obtenerRptEmpresaTodos();
		List<RptComplejo> rptComplejos = complejoDao.obtenerRptComplejosTodos();

		map.put("rptEmpresas", rptEmpresas);
		map.put("rptComplejos", rptComplejos);

		if (request.getParameter("idComplejo") != null) {
			map.put("idComplejoIsSet", true);
		} 

		return map;

	}

	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		AgregarComplejoNielsenForm form = new AgregarComplejoNielsenForm();
		// Se le pasa el id del complejo sobre el cual se desea crear uno
		// equivalente, si está seteado.
		if (request.getParameter("idComplejo") != null) {
			form.setId(Integer.parseInt(request.getParameter("idComplejo")));
			if (complejoDao.obtenerRptComplejo(Integer.parseInt(request.getParameter("idComplejo"))) != null ) {
				form.setCantidadSalas(complejoDao.obtenerRptComplejo(Integer.parseInt(request.getParameter("idComplejo"))).getCantidadSalas());
				form.setEmpresaId(complejoDao.obtenerRptComplejo(Integer.parseInt(request.getParameter("idComplejo"))).getRptEmpresaId());
				form.setCiudad(complejoDao.obtenerRptComplejo(Integer.parseInt(request.getParameter("idComplejo"))).getCiudad());
				form.setRm(complejoDao.obtenerRptComplejo(Integer.parseInt(request.getParameter("idComplejo"))).isRm() != null ?
						complejoDao.obtenerRptComplejo(Integer.parseInt(request.getParameter("idComplejo"))).isRm()
						: false);
			}
		} else {
			// Sugerir un id disponible
			Integer masAlto = complejoDao.obtenerRptComplejoIdMasAlto();
			Integer sugerencia = masAlto + 1;
			form.setId(sugerencia);
		}
		// Se le pasa la fecha de hoy por si desea que la equivalencia empiece a
		// valer desde ahora.
		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
		form.setFechaDesde(Util.DateToString(calendar.getTime()));
		return form;
	}

}