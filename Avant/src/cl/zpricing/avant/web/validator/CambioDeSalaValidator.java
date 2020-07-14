package cl.zpricing.avant.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cl.zpricing.avant.web.form.CambioDeSalaForm;

public class CambioDeSalaValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return CambioDeSalaForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		CambioDeSalaForm form = (CambioDeSalaForm) obj;
		
		if (form.getComplejo() == null || form.getComplejo().equalsIgnoreCase("")) {
			errors.rejectValue("complejo", "complejo.not-specified", null, "Debe seleccionar un complejo para el cambio de sala.");
		}
		
		if (form.getSalaSwap1() == null || form.getSalaSwap1().equalsIgnoreCase("")) {
			errors.rejectValue("salaSwap1", "sala.not-specified", null, "Debe que seleccionar una sala para el cambio.");
		}
		
		if (form.getSalaSwap2() == null || form.getSalaSwap2().equalsIgnoreCase("")) {
			errors.rejectValue("salaSwap2", "nombre.not-specified", null, "Debe que seleccionar una sala para el cambio.");
		}
		
		if (form.getSalaSwap1() != null && !form.getSalaSwap1().equalsIgnoreCase("") && form.getSalaSwap2() != null && !form.getSalaSwap2().equalsIgnoreCase("")) {
			if (form.getSalaSwap1().equalsIgnoreCase(form.getSalaSwap2())) {
				errors.rejectValue("salaSwap2", "nombre.same-screen", null, "Las salas deben ser distintas.");
			}
		}
		
		if (form.getFechaDesde() == null || form.getFechaDesde().equalsIgnoreCase("")) {
			errors.rejectValue("fechaDesde", "fechaDesde.not-specified", null, "Debe especificar una fecha de inicio para el cambio.");
		}
		if (form.getFechaHasta() == null || form.getFechaHasta().equalsIgnoreCase("")) {
			errors.rejectValue("fechaHasta", "fechaHasta.not-specified", null, "Debe especificar una fecha limite (incluida) para el cambio.");
		}
	}

}
