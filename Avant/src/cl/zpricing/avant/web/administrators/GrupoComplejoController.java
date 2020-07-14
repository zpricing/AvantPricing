package cl.zpricing.avant.web.administrators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Empresa;
import cl.zpricing.avant.model.GrupoComplejo;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.EmpresaDao;
import cl.zpricing.avant.servicios.GrupoComplejoDao;
import cl.zpricing.avant.web.form.GrupoComplejoForm;

public class GrupoComplejoController extends SimpleFormController {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private ComplejoDao complejoDao;
	private GrupoComplejoDao grupoComplejoDao;
	private EmpresaDao empresaDao;
	
	public ModelAndView onSubmit(Object command)throws ServletException {
		
		String id = ((GrupoComplejoForm) command).getId();
		String descripcion = ((GrupoComplejoForm) command).getDescripcion();
		String padre_id = ((GrupoComplejoForm) command).getPadre();
		String empresa_id = ((GrupoComplejoForm) command).getEmpresa();
		String orden = ((GrupoComplejoForm) command).getOrden();
		String[] complejos = ((GrupoComplejoForm) command).getComplejos();
		String action = ((GrupoComplejoForm) command).getAction();
		
		GrupoComplejo padre = new GrupoComplejo();
		GrupoComplejo grupo = new GrupoComplejo();
		Empresa empresa = new Empresa();
		
		if(action.equals("new")) {
			if(!padre_id.isEmpty()) {
				padre.setId(Integer.parseInt(padre_id));
				if (empresa_id.isEmpty()) {
					padre = grupoComplejoDao.obtenerGrupoComplejo(Integer.parseInt(padre_id));
					empresa.setId(padre.getEmpresa().getId());
				}
				else {
					empresa.setId(Integer.parseInt(empresa_id));
				}
				
				grupo.setDescripcion(descripcion);
				grupo.setEmpresa(empresa);
				grupo.setPadre(padre);
				if (!orden.isEmpty()) {
					grupo.setOrden(Integer.parseInt(orden));
				}
				
				grupoComplejoDao.agregarGrupoComplejo(grupo);
			}
		}
		
		if(action.equals("edit")) {
			if(!id.isEmpty()) {
				grupo = grupoComplejoDao.obtenerGrupoComplejo(Integer.parseInt(id));
				grupo.setDescripcion(descripcion);
				grupoComplejoDao.actualizarGrupoComplejo(grupo);
			}
		}
		
		if(action.equals("complex")) {
			if(!id.isEmpty()) {
				for (int i = 0; i < complejos.length; i++) {
					Complejo complejo = new Complejo();
					complejo.setId(Integer.parseInt(complejos[i]));
					grupo.setId(Integer.parseInt(id));
					complejoDao.agregaRelacionGrupoComplejo(complejo, grupo);
				}
			}
		}
		
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> refdata = new HashMap<String, Object>();
		
		List<Empresa> empresas = new ArrayList<Empresa>();
		empresas = empresaDao.empresasTodas();
		
		List<Complejo> complejos = new ArrayList<Complejo>();
		complejos = complejoDao.complejosTodos();
		
		List<GrupoComplejo> grupos = new ArrayList<GrupoComplejo>();
		grupos = grupoComplejoDao.obtenerTodos();
		Iterator<GrupoComplejo> iterGrupoComplejos = grupos.iterator();
		while (iterGrupoComplejos.hasNext()) {
			GrupoComplejo grupo = iterGrupoComplejos.next();
			grupo.setHijos((ArrayList<GrupoComplejo>) grupoComplejoDao.obtenerHijos(grupo));
			grupo.setComplejos((ArrayList<Complejo>) grupoComplejoDao.obtenerComplejosByGrupo(grupo));
		}
		
		refdata.put("empresas", empresas);
		refdata.put("grupos", grupos);
		refdata.put("complejos", complejos);
		
		return refdata;
	}

	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
	public void setGrupoComplejoDao(GrupoComplejoDao grupoComplejoDao) {
		this.grupoComplejoDao = grupoComplejoDao;
	}
	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

}
