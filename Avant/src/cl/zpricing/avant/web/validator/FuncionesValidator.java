/**
 * 
 */
package cl.zpricing.avant.web.validator;

import java.util.GregorianCalendar;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cl.zpricing.avant.web.form.FuncionesForm;

/**
 * <b>Validador del formulario de administracion de funciones</b>
 * 
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 30-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class FuncionesValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class arg0) {
		return FuncionesForm.class.equals(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors errors) {
		FuncionesForm form = (FuncionesForm) arg0;
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setLenient(false);
			gc.set(GregorianCalendar.YEAR, Integer.valueOf(form.getFecha()
					.split("-")[2]));
			gc.set(GregorianCalendar.MONTH, Integer.valueOf(form.getFecha()
					.split("-")[1]));
			gc.set(GregorianCalendar.DATE, Integer.valueOf(form.getFecha()
					.split("-")[0]));
			gc.set(GregorianCalendar.HOUR_OF_DAY, form.getHora());
			gc.set(GregorianCalendar.MINUTE, form.getMin());
			gc.getTime();
		} catch (Exception e) {
			errors.rejectValue("min", "fecha.incorrecta", null,
					"Fecha incorrecta");
		}

	}

}
