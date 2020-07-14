package cl.zpricing.avant.web.administrators;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Categoria;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.servicios.CategoriaDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.web.form.PeliculasSinClasificarForm;

public class PeliculasSinClasificarController extends SimpleFormController  {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private CategoriaDao categoriaDao;
	private PeliculaDao peliculaDao;
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		log.debug(" Iniciando formBackingObject");
		PeliculasSinClasificarForm form = new PeliculasSinClasificarForm();
		
		int pagina = 1;
		boolean scheduled = true;
		boolean unclassified = false;
		String search = "";
		if(request.getParameter("page") != null) {
			pagina = Integer.parseInt(request.getParameter("page"));
		}
		
		if (request.getParameter("scheduled") != null && request.getParameter("scheduled").equals("0")) {
			scheduled = false;
		}
		if (request.getParameter("unclassified") != null && request.getParameter("unclassified").equals("1")) {
			unclassified = true;
		}
		if (request.getParameter("search") != null) {
			search = request.getParameter("search").trim();
		}
		List<Pelicula> peliculas = peliculaDao.obtenerListaPeliculas(pagina, search, unclassified, scheduled);
		
		Pelicula[] formPeliculas = new Pelicula[peliculas.size()];
		Iterator<Pelicula> iterPeliculas = peliculas.iterator();
		for(int i=0; iterPeliculas.hasNext(); i++) {
			Pelicula pelicula = iterPeliculas.next();
			if(pelicula.categorias.size() == 0) {
				Categoria categoriaDummy = new Categoria();
				categoriaDummy.setId(-1);
				pelicula.categorias.add(categoriaDummy);
			}
			formPeliculas[i] = pelicula;
		}
		form.setPeliculas(formPeliculas);
		
		return form;
	}
	
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("categorias", categoriaDao.obtenerCategorias());
		params.put("rankings", peliculaDao.obtenerRankings());
		params.put("ratings", peliculaDao.obtenerRatings());
		params.put("idiomas", peliculaDao.obtenerIdiomas());
		params.put("formatos", peliculaDao.obtenerFormatos());
		
		int pagina = 1;
		boolean scheduled = true;
		boolean unclassified = false;
		String search = "";
		if(request.getParameter("page") != null) {
			pagina = Integer.parseInt(request.getParameter("page"));
		}
		params.put("next", pagina+1);
		if(pagina>1) {
			params.put("previous", pagina-1);
		}
		if (request.getParameter("scheduled") != null && request.getParameter("scheduled").equals("0")) {
			scheduled = false;
		}
		if (request.getParameter("unclassified") != null && request.getParameter("unclassified").equals("1")) {
			unclassified = true;
		}
		if (request.getParameter("search") != null) {
			search = (String) request.getParameter("search");
			search = search.trim();
		}
		
		params.put("scheduled", scheduled? "1": "0");
		params.put("unclassified", unclassified? "1" : "0");
		params.put("search", search);
		
		return params;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		PeliculasSinClasificarForm form = (PeliculasSinClasificarForm) command;
		
		Pelicula[] peliculas = form.getPeliculas();
		
		for (int i = 0 ; i < peliculas.length ; i++) {
			Pelicula pelicula = peliculas[i];
			
			peliculaDao.actualizarClasificacionPelicula(pelicula);
			
			peliculaDao.eliminaRelacionesCategoria(pelicula);
			peliculaDao.agregaRelacionCategoria(pelicula, (Categoria) pelicula.getCategorias().toArray()[0]);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(request.getParameter("page") != null) {
			params.put("page", request.getParameter("page"));
		}
		if(request.getParameter("todas") != null) {
			params.put("todas", request.getParameter("todas"));
		}
		if(request.getParameter("cartelera") != null) {
			params.put("cartelera", request.getParameter("cartelera"));
		}
		
		
		return new ModelAndView(new RedirectView(getSuccessView()), params);
	}

	public void setCategoriaDao(CategoriaDao categoriaDao) {
		this.categoriaDao = categoriaDao;
	}

	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}
}

