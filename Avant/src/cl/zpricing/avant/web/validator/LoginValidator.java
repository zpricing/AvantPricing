package cl.zpricing.avant.web.validator;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cl.zpricing.avant.web.form.LoginForm;

/**
 * <b>Descripci�n de la Clase</b>
 * 
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 09-02-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class LoginValidator implements Validator {
	private Logger log = (Logger) Logger.getLogger(cl.zpricing.avant.web.LoginController.class);

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		log.debug("@supports() de LoginValidator");
		return LoginForm.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		log.debug("@validate() de LoginValidator");
		LoginForm loginForm = (LoginForm) obj;

		if (loginForm.getUsuario() == null || loginForm.getUsuario().equals(""))
			errors.rejectValue("j_username", "user.not-specified", null, "Usuario requerido");
		else if (loginForm.getPassword() == null || loginForm.getPassword().equals(""))
			errors.rejectValue("j_password", "password.not-specified", null, "Password requerido");
	}
 }
