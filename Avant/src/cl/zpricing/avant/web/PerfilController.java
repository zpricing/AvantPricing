package cl.zpricing.avant.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.servicios.UsuarioDao;
import cl.zpricing.avant.web.form.NewUserForm;

/**
 * <b>Descripci�n de la Clase</b>
 * Controlador de la vista de edici�n de perfil
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 13-02-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class PerfilController extends SimpleFormController {

	private UsuarioDao usuarioDao;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request)
	throws Exception {
		
		NewUserForm form = new NewUserForm();
		
		String user =  SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = new Usuario();
		
		usuario = usuarioDao.obtenerUsuarioByName(user);
		
		form.setUsuario(usuario.getUsuario());
		form.setNombreCompleto(usuario.getNombreCompleto());
		form.setEmail(usuario.getEmail());
		form.setRol(usuario.getRol().getRol());
		
		return form;
	}
		
/* (non-Javadoc)
 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
 */
protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> refdata = new HashMap<String, Object>();
		
		String mod= request.getParameter("modificado");
		
		boolean modificado = false;
		if(mod!=null)
		{if(mod.compareTo("true")==0)
			modificado=true;
		}
		refdata.put("modificado",modificado);
		
		return refdata;
}
		
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		// variable "encoder" para codificar la antigua contrase�a y compararla con la real al sacarla de la base de datos
		// y para codificar la nueva contrase�a. 
		ShaPasswordEncoder encoder = new ShaPasswordEncoder();
		
		ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
		
		NewUserForm form = (NewUserForm) command;
		
		if(request.getParameter("modificar")!=null){
		
			String user =  SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario up_user = new Usuario();
			up_user = usuarioDao.obtenerUsuarioByName(user);
			
			up_user.setEmail(form.getEmail());
			up_user.setNombreCompleto(form.getNombreCompleto());
			
			usuarioDao.actualizarUsuario(up_user);
			mv.addObject("modificado",true);
		}
		
			if(request.getParameter("cambiar")!=null){
			
			String user =  SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario up_user_pass = new Usuario();
			up_user_pass = usuarioDao.obtenerUsuarioByName(user);
			
			String old_pass = encoder.encodePassword(form.getOld_password(), up_user_pass.getUsuario());
			
			if(old_pass.compareTo(up_user_pass.getPassword())==0)
				{
				up_user_pass.setPassword(encoder.encodePassword(form.getPassword(), up_user_pass.getUsuario()));
				usuarioDao.actualizarUsuario(up_user_pass);
				mv.addObject("modificado",true);
			}
		}
		
	return mv;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#onBindAndValidate(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected void onBindAndValidate(HttpServletRequest request, Object arg0, BindException arg1) throws Exception {
		
		ShaPasswordEncoder encoder = new ShaPasswordEncoder();
		
		NewUserForm form = (NewUserForm) arg0;
		
		if(request.getParameter("cambiar")!=null){
			
			String user =  SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario up_user_pass = new Usuario();
			up_user_pass = usuarioDao.obtenerUsuarioByName(user);
			
			String old_pass = encoder.encodePassword(form.getOld_password(), up_user_pass.getUsuario());
			
			if(old_pass.compareTo(up_user_pass.getPassword())!=0)
				{
			
				arg1.rejectValue( "password", "error.password_match");
			}
		}
	}
	
		
	/**
	 * @param usuarioDao 
	 */
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	/**
	 * @return usuarioDao
	 */
	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}
	
}
