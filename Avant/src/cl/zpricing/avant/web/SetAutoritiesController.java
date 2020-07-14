/**
 * 
 */
package cl.zpricing.avant.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Autoridad;
import cl.zpricing.avant.model.Rol;
import cl.zpricing.avant.servicios.AutoridadDao;
import cl.zpricing.avant.web.form.SetAutoritiesForm;



/**
 * <b>Descripci�n de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 22-12-2008 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class SetAutoritiesController extends SimpleFormController {
	
	private AutoridadDao autoridadDao;
	private List<String> tipoAutoridad;
	
	/**
	 * @return the userDao
	 */
	public AutoridadDao getAutoridadDao() {
		return autoridadDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setAutoridadDao(AutoridadDao autoridadDao) {
		this.autoridadDao = autoridadDao;
	}
	
	/**
	 * ModelAndView: Recoge los valores del formulario y los guarda en un objeto usuario,
	 * el que psoteriormente se pasa al Dao para que actualize los valores del
	 * Usuario seg�n el id.
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 22/12/2008 Julio: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param command Para obtener valores del formulario.
	 * @return Retorna la vista exitosa
	 * @throws ServletException Excepcion general por si un servlet encuentra problemas.
	 * @since 1.0
	 */
	public ModelAndView onSubmit(Object command)throws ServletException {
		
		String rol_id = ((SetAutoritiesForm) command).getRolId();
		
		String an = ((SetAutoritiesForm) command).getAutoridad();
		
		if(an!= null){
			int autoridad_number = Integer.parseInt(an);
			
			Autoridad autoridad = new Autoridad();
			autoridad.setAutoridad(tipoAutoridad.get(autoridad_number));
			autoridad.setRolId(Integer.parseInt(rol_id));
			
			autoridadDao.agregarAutoridad(autoridad);
		}
		
		return new ModelAndView(new RedirectView("admin_setautorities.htm?id_rol="+rol_id));
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		String id_rol = request.getParameter("id_rol");
		
		SetAutoritiesForm modifyform = new SetAutoritiesForm();
		modifyform.setRolId(id_rol);

		return modifyform;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> refdata = new HashMap<String, Object>();
		
		int id_rol = Integer.parseInt(request.getParameter("id_rol"));
		
		List<Autoridad> autoridades = autoridadDao.obtenerAutoridades(id_rol);
		
		refdata.put("autoridades", autoridades);
		
		Rol rol = autoridadDao.obtenerRolAutoridad(id_rol);
		
		refdata.put("rol", rol);
		
		tipoAutoridad = new ArrayList<String>();
		
		tipoAutoridad.add("ROLE_USER");
		tipoAutoridad.add("ROLE_ADMIN");

		for(int i = 0;i<autoridades.size();i++){
			for(int j = 0;j<tipoAutoridad.size();){
				if(tipoAutoridad.get(j).compareTo(autoridades.get(i).getAutoridad())==0)
					tipoAutoridad.remove(j);
				else
					j++;
			}
		}

		refdata.put("tipoAutoridad", tipoAutoridad);
		
		return refdata;
	}

}
