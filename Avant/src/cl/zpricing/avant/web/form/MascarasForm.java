/**
 * 
 */
package cl.zpricing.avant.web.form;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Mascara;

/**
 * <b>Descripcion de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 18-02-2009 MARIO: version inicial.</li>
 * </ul>
 * <P>
 * <b>Todos los derechos reservados por ZhetaPricing.</b>
 * <P>
 */

public class MascarasForm {
	private Complejo[] complejos;
	private Mascara[][] mascaras;
	private Double[][] cuposRestantes;

	public Complejo[] getComplejos() {
		return complejos;
	}
	public void setComplejos(Complejo[] complejos) {
		this.complejos = complejos;
	}
	public Mascara[][] getMascaras() {
		return mascaras;
	}
	public void setMascaras(Mascara[][] mascaras) {
		this.mascaras = mascaras;
	}
	public Double[][] getCuposRestantes() {
		return cuposRestantes;
	}
	public void setCuposRestantes(Double[][] cuposRestantes) {
		this.cuposRestantes = cuposRestantes;
	}
}
