/**
 * 
 */
package cl.zpricing.avant.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cl.zpricing.avant.web.form.ClasesForm;

/**
 * <b>Descripci�n de la Clase</b> Validador del formulario de administracion de
 * clases
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 29-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class ClasesValidator implements Validator {

	/**
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 29-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param
	 * @param parametro2
	 *            Descripci�n de par�metro.
	 * @return Descripci�n de lo que retorna el m�todo.
	 * @throws "Tipo de Excepci�n" Descripci�n de excepci�n lanzada por el
	 *         m�todo.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class arg0) {

		return ClasesForm.class.equals(arg0);
	}

	/**
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 29-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param
	 * @param parametro2
	 *            Descripci�n de par�metro.
	 * @return Descripci�n de lo que retorna el m�todo.
	 * @throws "Tipo de Excepci�n" Descripci�n de excepci�n lanzada por el
	 *         m�todo.
	 * @since 1.0
	 */
	@Override
	public void validate(Object arg0, Errors errors) {
		ClasesForm form = (ClasesForm) arg0;

		if (form.getId() == null || form.getId().equals(""))
			errors.rejectValue("id", "id.not-specified", null, "Id requerido");

		if (form.getDescripcion() == null)
			errors.rejectValue("descripcion", "descripcion.not-specified",
					null, "Descripcion requerida");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "precio",
				"error.clase_precio");

	}

}
