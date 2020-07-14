/**
 *
 */
package cl.zpricing.avant.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.servicios.ComplejoDao;

/**
 * <b>Clase Controlador para borrar Complejos</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 26-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class DeleteComplejoController extends SimpleFormController implements
		Controller {

	private ComplejoDao complejoDao;

	/**
	 * Para eliminar Complejos. Rescata el par�metro "id_complejo" desde el
	 * request, luego llama al ComplejoDao para eliminar el complejo seg�n su
	 * id. Si la eliminaci�n es exitosa vuelve a la vista de Complejos, caso
	 * contrario, despliega un mensaje de error.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 05-01-2009 Julio Andr�s Olivares Alarc�n: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 *             , IOException
	 * @since 1.0
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id_complejo = request.getParameter("id_complejo");

		Complejo complejo = new Complejo();

		complejo.setId(Integer.parseInt(id_complejo));

		boolean exito = complejoDao.eliminarComplejo(complejo);

		if (exito) {
			return new ModelAndView(new RedirectView(getSuccessView()));
		} else {
			return new ModelAndView(new RedirectView(
					"admin_managecomplejos.htm?error=1"));
		}
	}

	/**
	 * @return complejoDao
	 */
	public ComplejoDao getComplejoDao() {
		return complejoDao;
	}

	/**
	 * @param complejoDao
	 */
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

}
