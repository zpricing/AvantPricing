package cl.zpricing.avant.web.administrators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Categoria;
import cl.zpricing.avant.servicios.CategoriaDao;

/**
 * <b>Controlador de la vista eliminarcategoria</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 23-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class EliminarCategoriaController extends SimpleFormController {
	/**
	 * Impresi�n de log.
	 */
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private CategoriaDao categoriaDao;

	/**
	 * @return the categoriaDao
	 */
	public CategoriaDao getCategoriaDao() {
		return categoriaDao;
	}

	/**
	 * @param categoriaDao
	 *            the categoriaDao to set
	 */
	public void setCategoriaDao(CategoriaDao categoriaDao) {
		this.categoriaDao = categoriaDao;
	}

	/**
	 * 
	 * M�todo responsable de la vista eliminarcategoria al hacer request.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 23-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request
	 *            Solicitud HTTP
	 * @param arg1
	 *            Respuesta HTTP
	 * @return Modelo y vista que redirecciona a la vista aecategoria
	 * @throws Exception
	 * @since 1.0
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		int id = Integer.parseInt(request.getParameter("idcategoria"));
		Categoria nueva = new Categoria();
		nueva.setDescripcion("");
		nueva.setId(id);
		categoriaDao.eliminarCategoria(nueva);

		return new ModelAndView(new RedirectView(getSuccessView()));
	}

}