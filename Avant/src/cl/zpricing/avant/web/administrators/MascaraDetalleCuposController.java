package cl.zpricing.avant.web.administrators;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Grupo;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.model.MascaraAreaGrupo;
import cl.zpricing.avant.servicios.AreaDao;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.GrupoDao;
import cl.zpricing.avant.servicios.MascaraDao;
import cl.zpricing.avant.web.form.GruposForm;

public class MascaraDetalleCuposController extends SimpleFormController {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private GrupoDao grupoDao;
	private AreaDao areaDao;
	private ComplejoDao complejoDao;
	private MascaraDao mascaraDao;
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		log.debug(" Iniciando formBackingObject");
		GruposForm form = new GruposForm();
		
		String idComplejo = (String)request.getParameter("complejo_id");
		String idMascara = (String)request.getParameter("mascara_id");
		
		Complejo formComplejo = complejoDao.obtenerComplejo(Integer.parseInt(idComplejo));
		List<Grupo> formGrupos = grupoDao.obtenerTodosByComplejo(formComplejo);
		MascaraAreaGrupo[][] formMascaraAreaGrupo = new MascaraAreaGrupo[formGrupos.size()][];
		Mascara formMascara = mascaraDao.obtenerMascara(Integer.parseInt(idMascara));
		
		Iterator<Grupo> iterGrupos = formGrupos.iterator();
		int indiceGrupo = 0;
		while (iterGrupos.hasNext()) {
			Grupo grupo = iterGrupos.next();
			List<MascaraAreaGrupo> mascarasAreasGrupos = areaDao.obtenerMascarasAreaGrupos(grupo, formMascara);
			
			formMascaraAreaGrupo[indiceGrupo] = new MascaraAreaGrupo[mascarasAreasGrupos.size()];
			Iterator<MascaraAreaGrupo> iterMascarasAreasGrupos = mascarasAreasGrupos.iterator();
			int indiceMascarasAreasGrupos = 0;
			while (iterMascarasAreasGrupos.hasNext()) {
				MascaraAreaGrupo mascarasAreaGrupo = iterMascarasAreasGrupos.next();
				formMascaraAreaGrupo[indiceGrupo][indiceMascarasAreasGrupos] = mascarasAreaGrupo;
				indiceMascarasAreasGrupos++;
			}
			indiceGrupo++;
		}
		form.setMascarasAreasGrupos(formMascaraAreaGrupo);
		form.setGrupos(formGrupos);
		form.setMascara(formMascara);
		form.setComplejo(formComplejo);
		return form;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		GruposForm form = (GruposForm) command;
		
		MascaraAreaGrupo[][] formMascaraAreaGrupo = form.getMascarasAreasGrupos();
		for (int i = 0 ; i < formMascaraAreaGrupo.length ; i++) {
			for (int j = 0 ; j < formMascaraAreaGrupo[i].length ; j++) {
				MascaraAreaGrupo mascaraAreaGrupo = formMascaraAreaGrupo[i][j];
				mascaraAreaGrupo.setPorcentajeMinimo(formMascaraAreaGrupo[i][0].getPorcentajeMinimo());
				areaDao.actualizarMascaraAreaGrupo(mascaraAreaGrupo);
			}
		}
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
	public void setGrupoDao(GrupoDao grupoDao) {
		this.grupoDao = grupoDao;
	}
	public void setAreaDao(AreaDao areaDao) {
		this.areaDao = areaDao;
	}
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
	public void setMascaraDao(MascaraDao mascaraDao) {
		this.mascaraDao = mascaraDao;
	}	
}
