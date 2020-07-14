package cl.zpricing.avant.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cl.zpricing.avant.web.form.TipoFuncionForm;

/**
 * <b>Validador del formulario para administrar tipoFuncion</b>
 * 
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 26-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class TipoFuncionValidator implements Validator{

/*
 * (non-Javadoc)
 * @see org.springframework.validation.Validator#supports(java.lang.Class)
 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class arg0) {
		
		return TipoFuncionForm.class.equals(arg0);
	}

/*
 * (non-Javadoc)
 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
 */
	@Override
	public void validate(Object arg0, Errors errors) {
		TipoFuncionForm form = (TipoFuncionForm) arg0;
		
		if (form.getId() == null || form.getId().equals(""))
			errors.rejectValue("id", "id.not-specified", null, "Id requerido");

		if (form.getDescripcion() != null)
			if (form.getDescripcion().trim().length() == 0)
				errors.rejectValue("descripcion", "descripcion.not-specified",
						null, "Descripcion requerida");
		
	}
	

}
