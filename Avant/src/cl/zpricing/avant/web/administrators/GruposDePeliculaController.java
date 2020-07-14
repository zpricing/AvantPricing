package cl.zpricing.avant.web.administrators;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import cl.zpricing.avant.model.GrupoPelicula;
import cl.zpricing.avant.servicios.GrupoPeliculaDao;
import cl.zpricing.avant.web.form.GrupoPeliculaForm;

@Controller
@RequestMapping("/movie_groups.htm")
public class GruposDePeliculaController {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private GrupoPeliculaDao grupoPeliculaDao;
	
	@RequestMapping(method = RequestMethod.GET)
	public String show(
			@RequestParam(value="pagina", defaultValue = "1") Integer pagina,  
			@RequestParam(value="scheduled", defaultValue = "1") Integer scheduled,
			@RequestParam(value="unclassified", defaultValue = "0") Integer unclassified,
			@RequestParam(value="data_loaded", defaultValue = "0") Integer dataLoaded,
			ModelMap model) {
		log.debug("Desplegando grupos de peliculas");
		log.debug("  pagina : " + pagina);
		log.debug("  scheduled : " + scheduled);
		log.debug("  unclassified : " + unclassified);
		log.debug("  data_loaded : " + dataLoaded);
		
		GrupoPeliculaForm form = new GrupoPeliculaForm();
		List<GrupoPelicula> gruposDePeliculaEnCartelera = null;
		
		if (unclassified.intValue() == 1) {
			if (scheduled.intValue() == 0) {
				gruposDePeliculaEnCartelera = grupoPeliculaDao.obtenerTodosSinNombreOriginal(pagina);
			}
			else {
				gruposDePeliculaEnCartelera = grupoPeliculaDao.obtenerEnCarteleraSinNombreOriginal(pagina);
			}
		}
		else {
			if (scheduled.intValue() == 1) {
				gruposDePeliculaEnCartelera = grupoPeliculaDao.obtenerEnCartelera(pagina, dataLoaded.intValue());
			}
			else {
				gruposDePeliculaEnCartelera = grupoPeliculaDao.obtenerTodos(pagina, dataLoaded.intValue());
			}
		}

		form.setGrupos((GrupoPelicula[])gruposDePeliculaEnCartelera.toArray(new GrupoPelicula[gruposDePeliculaEnCartelera.size()]));
		
		log.debug("# de grupos en cartelera : " + gruposDePeliculaEnCartelera.size());
		
		model.addAttribute("gruposPelicula", form);
		model.addAttribute("scheduled", scheduled);
		model.addAttribute("unclassified", unclassified);
		model.addAttribute("dataLoaded", dataLoaded);
		
		model.addAttribute("page", pagina);
		model.addAttribute("next", pagina + 1);
		if(pagina > 1) {
			model.addAttribute("previous", pagina-1);
		}
 
		return "grupos_pelicula";
	}
	
	public String show(ModelMap model) {
		return this.show(1, 1, 0, 0, model);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("gruposPelicula") GrupoPeliculaForm gruposPelicula, BindingResult result, SessionStatus status, ModelMap model) {
		log.debug("processSubmit");
		
		log.debug("pagina : " + gruposPelicula.getFormPage().intValue());
		log.debug("scheduled : " + gruposPelicula.getFormScheduled().intValue());
		log.debug("unclassified : " + gruposPelicula.getFormUnclassified().intValue());
		log.debug("data_loaded : " + gruposPelicula.getFormDataLoaded().intValue());
		
		GrupoPelicula[] grupos = gruposPelicula.getGrupos();
		
		for (int i = 0 ; i < grupos.length ; i++) {
			log.debug(" Grupo: " + grupos[i].getDescripcion() + " : " + grupos[i].getNombreOriginal());
			grupoPeliculaDao.actualizar(grupos[i]);
		}
		
		return this.show(gruposPelicula.getFormPage().intValue(), gruposPelicula.getFormScheduled().intValue(), gruposPelicula.getFormUnclassified().intValue(), gruposPelicula.getFormDataLoaded().intValue(), model);
	}

	public void setGrupoPeliculaDao(GrupoPeliculaDao grupoPeliculaDao) {
		this.grupoPeliculaDao = grupoPeliculaDao;
	}
}
