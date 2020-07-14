package cl.zpricing.avant.web.validator;

import java.util.GregorianCalendar;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.form.FuncionesCalForm;

/**
 * <b>Validador del formulario del calendario de clases</b>
 * 
 * <br>
 * Registro de versiones:
 * <ul>
 * <li>1.0 30-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class FuncionesCalValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class arg0) {
		return FuncionesCalForm.class.equals(arg0);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors errors) {
		FuncionesCalForm form = (FuncionesCalForm) arg0;
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setLenient(false);
			gc.setTime(Util.StringToDate(form.getFecha()));
			gc.getTime();

		} catch (Exception e) {
			errors
					.rejectValue("fecha", "error.fecha", null,
							"Fecha incorrecta");
		}

	}

}
