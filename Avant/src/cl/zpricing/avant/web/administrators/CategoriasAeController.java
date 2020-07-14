package cl.zpricing.avant.web.administrators;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Categoria;
import cl.zpricing.avant.servicios.CategoriaDao;
import cl.zpricing.avant.web.form.CategoriasForm;

/**
 * <b>Clase controladora de la vista aecategoria</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 29-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class CategoriasAeController extends SimpleFormController {
	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private CategoriaDao categoriaDao;

	/**
	 * @return categoriaDao
	 */
	public CategoriaDao getCategoriaDao() {
		return categoriaDao;
	}

	/**
	 * @param categoriaDao
	 */
	public void setCategoriaDao(CategoriaDao categoriaDao) {
		this.categoriaDao = categoriaDao;
	}

	/**
	 * 
	 * M�todo responsable de la vista aecategoria al hacer submit en su form asociado.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 29-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP
	 * @param response Respuesta HTTP
	 * @param command Objeto recibido por el form asociado a la vista
	 * @param errors Errores
	 * @return Modelo y vista para aecategoria
	 * @throws Exception
	 * @since 1.0
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		CategoriasForm categoriasForm = (CategoriasForm) command;
		String descripcion = categoriasForm.getDescripcion();
		Categoria nueva = new Categoria();
		nueva.setDescripcion(descripcion);
		nueva.setId(0);
		categoriaDao.agregarCategoria(nueva);
		

		ModelAndView mv = showForm(request, response, errors);

		return mv;
	}
	/**
	 * 
	 * Objeto responsable de crear el form asociado a la vista aecategoria.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 29-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP.
	 * @return objeto CategoriasForm
	 * @throws ServletException 
	 * @since 1.0
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		log.debug("Iniciando formBackingObject...");
		CategoriasForm categoriasForm = new CategoriasForm();
		categoriasForm.setId("0");
		return categoriasForm;
	}
	/**
	 * 
	 * Objeto responsable de establecer las variables globales en la vista aecategoria.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 29-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP
	 * @return Map de variables
	 * @throws Exception
	 * @since 1.0
	 */	
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		Map<String, Object> mapCategoria = new HashMap<String, Object>(1);
		mapCategoria.put("categorias", categoriaDao.obtenerCategorias());
		return mapCategoria;
	}

}
