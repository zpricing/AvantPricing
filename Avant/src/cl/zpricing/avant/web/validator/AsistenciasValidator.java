/**
 * 
 */
package cl.zpricing.avant.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cl.zpricing.avant.web.form.AsistenciasForm;

/**
 * <b>Validador del formulario de administracion de asistencias</b>
 * 
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 29-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class AsistenciasValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class arg0) {

		return AsistenciasForm.class.equals(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors errors) {
		@SuppressWarnings("unused")
		AsistenciasForm form = (AsistenciasForm) arg0;

	}

}
