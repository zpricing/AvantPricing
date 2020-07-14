package cl.zpricing.avant.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cl.zpricing.avant.web.form.CategoriasForm;


/**
 * <b>Validador del formulario de administracionde categorias</b>
 * 
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 09-02-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class CategoriasValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return CategoriasForm.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		CategoriasForm categoriaForm = (CategoriasForm) obj;

		if (categoriaForm.getId() == null || categoriaForm.getId().equals(""))
			errors.rejectValue("id", "id.not-specified", null, "Id requerido");

		if (categoriaForm.getDescripcion() != null)
			if (categoriaForm.getDescripcion().trim().length() == 0)
				errors.rejectValue("descripcion", "descripcion.not-specified",
						null, "Descripcion requerida");

	}

}
