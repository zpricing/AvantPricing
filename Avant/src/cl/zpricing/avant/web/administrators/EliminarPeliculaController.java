package cl.zpricing.avant.web.administrators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.servicios.PeliculaDao;

/**
 * <b>Controlador de la vista eliminarpelicula</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 23-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class EliminarPeliculaController extends SimpleFormController {

	private PeliculaDao peliculaDao;

	/**
	 * Impresi�n de log.
	 */
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/**
	 * @return the peliculaDao
	 */
	public PeliculaDao getPeliculaDao() {
		return peliculaDao;
	}

	/**
	 * @param peliculaDao
	 *            the peliculaDao to set
	 */
	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}

	/**
	 * 
	 * M�todo responsable de la vista eliminarpelicula al hacer request.
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
	 * @return Modelo y vista que redirecciona a vista peliculas
	 * @throws Exception
	 * @since 1.0
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		int id = Integer.parseInt(request.getParameter("idpelicula"));
		Pelicula nueva = new Pelicula();
		nueva.setDescripcion("");
		nueva.setId(id);
		peliculaDao.eliminarPelicula(nueva);

		return new ModelAndView(new RedirectView(getSuccessView()));
	}

}
