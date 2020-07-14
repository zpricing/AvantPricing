package cl.zpricing.avant.web.administrators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.GrupoComplejo;
import cl.zpricing.avant.servicios.GrupoComplejoDao;

public class EliminarGrupoComplejoController extends SimpleFormController{
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private GrupoComplejoDao grupoComplejoDao;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		int id = Integer.parseInt(request.getParameter("id_grupocomplejo"));
		GrupoComplejo grupo = new GrupoComplejo();
		grupo.setId(id);
		grupoComplejoDao.eliminarGrupoComplejo(grupo);
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
	public void setGrupoComplejoDao(GrupoComplejoDao grupoComplejoDao) {
		this.grupoComplejoDao = grupoComplejoDao;
	}
}
