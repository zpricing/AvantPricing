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

import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.servicios.UsuarioDao;

/**
 * <b>Controlador de la vista para eliminar Usuarios</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 29-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */
public class DeleteUserController extends SimpleFormController implements
		Controller {

	protected final Log logger = LogFactory.getLog(getClass());

	private UsuarioDao usuarioDao;

	/**
	 * @return userDao
	 */
	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

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

		String idUsuario = request.getParameter("iduser");

		Usuario usuario = new Usuario();

		usuario.setId(Integer.parseInt(idUsuario));

		usuarioDao.eliminarUsuario(usuario);

		return new ModelAndView(new RedirectView(getSuccessView()));
	}

}