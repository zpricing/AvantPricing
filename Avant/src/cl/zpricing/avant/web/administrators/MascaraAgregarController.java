package cl.zpricing.avant.web.administrators;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.servicios.AreaDao;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.MascaraDao;

public class MascaraAgregarController extends SimpleFormController {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private MascaraDao mascaraDao;
	private ComplejoDao complejoDao;
	private AreaDao areaDao;
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Mascara mascara = new Mascara();
		if (request.getParameter("complejo_id") != null) {
			Integer paramComplejoId = Integer.parseInt(request.getParameter("complejo_id"));
			Complejo complejo = complejoDao.obtenerComplejo(paramComplejoId);
			mascara.setComplejo(complejo);
			mascara.setActivo(true);
		}
		return mascara;
	}
	
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		return super.referenceData(request);
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Mascara mascara = (Mascara) command;
		//mascara = mascaraDao.guardar(mascara);

		return new ModelAndView(new RedirectView("admin_mascara_nueva.htm?complejo_id=" + mascara.getComplejo().getId()));
	}

	public void setMascaraDao(MascaraDao mascaraDao) {
		this.mascaraDao = mascaraDao;
	}

	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

	public void setAreaDao(AreaDao areaDao) {
		this.areaDao = areaDao;
	}
}
