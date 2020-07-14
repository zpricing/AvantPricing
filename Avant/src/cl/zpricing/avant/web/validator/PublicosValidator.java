package cl.zpricing.avant.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cl.zpricing.avant.web.form.PublicosForm;

/**
 * <b>Validador del formulario de administracion de funciones</b>
 * 
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 10-02-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class PublicosValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return PublicosForm.class.equals(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		PublicosForm publicoForm = (PublicosForm) obj;

		if (publicoForm.getId() == null || publicoForm.getId().equals(""))
			errors.rejectValue("id", "id.not-specified", null, "Id requerido");

		if (publicoForm.getDescripcion() != null)
			if (publicoForm.getDescripcion().trim().length() == 0)
				errors.rejectValue("descripcion", "descripcion.not-specified",
						null, "Descripcion requerida");

	}

}
