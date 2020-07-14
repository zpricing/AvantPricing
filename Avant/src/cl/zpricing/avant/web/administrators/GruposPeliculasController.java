package cl.zpricing.avant.web.administrators;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Categoria;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.web.form.PeliculasSinClasificarForm;

public class GruposPeliculasController extends SimpleFormController  {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private PeliculaDao peliculaDao;
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		
		log.debug(" Iniciando formBackingObject");
		PeliculasSinClasificarForm form = new PeliculasSinClasificarForm();
		
		int pagina = 1;
		if(request.getParameter("page") != null) {
			pagina = Integer.parseInt(request.getParameter("page"));
		}
		
		List<Pelicula> peliculas;
		
		return form;
	}
	
	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}

}
