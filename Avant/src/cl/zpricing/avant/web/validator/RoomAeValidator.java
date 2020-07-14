package cl.zpricing.avant.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cl.zpricing.avant.web.form.RoomAeForm;

/**
 * <b>Descripci�n de la Clase</b> Validador del formulario de administracion de
 * salas
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 09-02-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class RoomAeValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return RoomAeForm.class.equals(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		RoomAeForm roomAeForm = (RoomAeForm) obj;

		if (roomAeForm.getNumero() == null || roomAeForm.getNumero().equals(""))
			errors.rejectValue("numero", "roomNumber.not-specified", null,
					"Numero de sala requerido");
		else if (roomAeForm.getCapacidad() == null
				|| roomAeForm.getCapacidad().equals(""))
			errors.rejectValue("capacidad", "capacity.not-specified", null,
					"Capacidad requerida");
		else {
			try {
				Integer.parseInt(roomAeForm.getCapacidad());
			} catch (NumberFormatException e) {
				errors.rejectValue("capacidad", "capacity.not-numeric", null,
						"Capacidad no numerica");
			}

		}
	}
}
