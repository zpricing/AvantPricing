/**
 * 
 */
package cl.zpricing.avant.model.loadmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.DescripcionId;

/**
 * Agrupa plantillas que poseen un uso durante cierto TimeSpan y 
 * para un cierto Complejo
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 29-01-2009 MARIO: versi�n inicial.</li>
 *   <li>1.1 30-01-2009 MARIO: agregados los parametros de fecha.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class GrupoPlantillas extends DescripcionId {

	private Complejo complejo;
	private TimeSpan timeSpan;
	private Date fechaInicio;
	private Date fechaFin;
	private ArrayList<Plantilla> plantillas;
	
	
	/**
	 * @return complejo
	 */
	public Complejo getComplejo() {
		return complejo;
	}
	/**
	 * @param complejo
	 */
	public void setComplejo(Complejo complejo) {
		this.complejo = complejo;
	}
	/**
	 * @return timeSpan
	 */
	public TimeSpan getTimeSpan() {
		return timeSpan;
	}
	/**
	 * @param timeSpan
	 */
	public void setTimeSpan(TimeSpan timeSpan) {
		this.timeSpan = timeSpan;
	}
	/**
	 * @return fecha de Inicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return fecha de termino
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return lista de plantillas
	 */
	public ArrayList<Plantilla> getPlantillas() {
		return plantillas;
	}
	/**
	 * @param plantillas
	 */
	public void setPlantillas(ArrayList<Plantilla> plantillas) {
		this.plantillas = plantillas;
	}
	
	public Plantilla getPlantillaPadre()
	{
		Iterator<Plantilla> iter = this.plantillas.iterator();
		while(iter.hasNext())
		{
			Plantilla temp = iter.next();
			if(temp.getPadre()){
				return temp;
			}
		}
		return null;
	}
	
}
