package cl.zpricing.avant.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cl.zpricing.avant.web.form.PeliculasForm;

/**
 * <b>Validador del formulario de administracion de peliculas</b>
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
public class PeliculasValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return PeliculasForm.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		PeliculasForm form = (PeliculasForm) obj;
		
		if ((form.getNombre() == null) || (form.getNombre().trim().length() == 0))
			errors.rejectValue("nombre", "nombre.not-specified",
						null, "Nombre requerido");		
		/*if ((form.getDescripcion() == null) || (form.getDescripcion().trim().length() == 0))
			errors.rejectValue("descripcion", "descripcion.not-specified",
						null, "Descripci�n requerida");*/
		
		if ((form.getDuracion() == null) || (form.getDuracion().trim().length() == 0))
			errors.rejectValue("duracion", "duracion.not-specified",
						null, "Duraci�n requerida");
		else{
			try{
				Integer.parseInt(form.getDuracion());
			}
			catch(Exception e){
				errors.rejectValue("duracion", "duracion.not-number",
						null, "La duraci�n debe ser un n�mero");
			}
		}
		
					
//		if(form.getCategorias().length == 0)
//			errors.rejectValue("categorias", "categorias.not-specified",
//					null, "Debe seleccionar al menos una categor�a");
//		if(form.getPublicos().length == 0)
//			errors.rejectValue("publicos", "publicos.not-specified",
//					null, "Debe seleccionar al menos un tipo de p�blico");
//		if(form.getEpocas().length == 0)
//			errors.rejectValue("epocas", "epocas.not-specified",
//					null, "Debe seleccionar al menos una �poca");
		
		
	}
}