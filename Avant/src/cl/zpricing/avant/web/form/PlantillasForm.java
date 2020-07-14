package cl.zpricing.avant.web.form;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.loadmanager.GrupoPlantillas;

/**
 * <b>Descripci�n de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 05-02-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class PlantillasForm {
	
	private GrupoPlantillas[][] grupoPlantillas;
	private Complejo[] complejo;
	
	/**
	 * @return grupoPlantillas
	 */
	public GrupoPlantillas[][] getGrupoPlantillas() {
		return grupoPlantillas;
	}
	
	/**
	 * @param grupoPlantillas
	 */
	public void setGrupoPlantillas(GrupoPlantillas[][] grupoPlantillas) {
		this.grupoPlantillas = grupoPlantillas;
	}
	
	/**
	 * @return lista de complejos
	 */
	public Complejo[] getComplejo() {
		return complejo;
	}
	
	/**
	 * @param lista de complejos
	 */
	public void setComplejo(Complejo[] complejo) {
		this.complejo = complejo;
	}
	
}
