package cl.zpricing.avant.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Empresa;
import cl.zpricing.avant.servicios.EmpresaDao;

/**
 * <b>Controlador encargado de borrar las Empresas</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 24-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class DeleteEmpresaController extends SimpleFormController implements
		Controller {

	protected final Log logger = LogFactory.getLog(getClass());

	private EmpresaDao empresaDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractController#handleRequest(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		/*
		 * Obtiene el id de la empresa que se eliminar� desde el par�metro
		 * "id_empresa" que obtiene desde el request.
		 */
		String id_empresa = request.getParameter("id_empresa");

		Empresa empresa = new Empresa();

		empresa.setId(Integer.parseInt(id_empresa));

		boolean exito = empresaDao.eliminarEmpresa(empresa);

		if (exito)
			return new ModelAndView(new RedirectView(getSuccessView()));
		else
			return new ModelAndView(new RedirectView(
					"managempresas.htm?error=1"));

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
