package cl.zpricing.avant.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cl.zpricing.avant.web.form.NewUserForm;

/**
 * <b>Descripci�n de la Clase</b> Validador del formulario de creacion de
 * usuario
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 09-02-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class NewUserValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return NewUserForm.class.equals(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		NewUserForm newUserForm = (NewUserForm) obj;

		if (newUserForm.getUsuario() == null
				|| newUserForm.getUsuario().equals(""))
			errors.rejectValue("usuario", "user.not-specified", null,
					"Usuario requerido");
		else if (newUserForm.getPassword() == null
				|| newUserForm.getPassword().equals(""))
			errors.rejectValue("password", "password.not-specified", null,
					"Password requerido");
		else if (newUserForm.getNombreCompleto() == null
				|| newUserForm.getNombreCompleto().equals(""))
			errors.rejectValue("nombreCompleto", "completeName.not-specified",
					null, "Nombre Completo requerido");
		else if (newUserForm.getEmail() == null
				|| newUserForm.getEmail().equals(""))
			errors.rejectValue("email", "email.not-specified", null,
					"Email requerido");
	}
}
