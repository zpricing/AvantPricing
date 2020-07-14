package cl.zpricing.avant.model.prediction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.zpricing.avant.model.Marker;
import cl.zpricing.avant.model.Sala;

/**
 * <b>Clase utilizada para manejar todo lo referente a una prediccion por dia.
 * Una prediccion contiene muchas predicciones por dia</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 09-02-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class PrediccionPorDia {
	private int id;
	private int estimacion;
	/**
	 * destevez: agregado fecha y prediccion
	 */
	private Date fecha;
	private Prediccion prediccion;
	private double varianza;
	private boolean error;
	private List<PrediccionPorFuncion> prediccionesPorFuncion;
	private List<Marker> markersFecha;
	private List<Sala> salasUtilizadas;
	
	
	public List<PrediccionPorFuncion> getPrediccionesPorFuncion(int sala) {
		List<PrediccionPorFuncion> prediccionesFuncionSala = new ArrayList<PrediccionPorFuncion>();
		for (PrediccionPorFuncion prediccionPorFuncion : prediccionesPorFuncion) {
			if (prediccionPorFuncion.getFuncionPredecida().getSala().getId() == sala) {
				prediccionesFuncionSala.add(prediccionPorFuncion);
			}
		}
		return prediccionesFuncionSala;
	}

	public List<Sala> getSalasUtilizadas() {
		return salasUtilizadas;
	}
	public void setSalasUtilizadas(List<Sala> salasUtilizadas) {
		this.salasUtilizadas = salasUtilizadas;
	}
	public List<Marker> getMarkersFecha() {
		return markersFecha;
	}
	public void setMarkersFecha(List<Marker> markersFecha) {
		this.markersFecha = markersFecha;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public double getVarianza() {
		return varianza;
	}
	public void setVarianza(double varianza) {
		this.varianza = varianza;
	}
	public Prediccion getPrediccion() {
		return prediccion;
	}
	public void setPrediccion(Prediccion prediccion) {
		this.prediccion = prediccion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEstimacion() {
		return estimacion;
	}
	public void setEstimacion(int estimacion) {
		this.estimacion = estimacion;
	}
	public List<PrediccionPorFuncion> getPrediccionesPorFuncion() {
		return prediccionesPorFuncion;
	}
	public void setPrediccionesPorFuncion(List<PrediccionPorFuncion> prediccionesPorFuncion) {
		this.prediccionesPorFuncion = prediccionesPorFuncion;
	}
}
