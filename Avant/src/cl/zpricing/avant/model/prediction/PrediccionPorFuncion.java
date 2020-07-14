package cl.zpricing.avant.model.prediction;

import java.util.ArrayList;
import java.util.List;

import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.web.chart.Grafico;

/**
 * <b>Clase utilizada para manejar todo lo referente a una prediccion por funcion.
 * Un prediccion por dia contiene muchas predicciones por funcion</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 09-02-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 *   <li>1.1 12-03-2009 Mario Lavandero: Se revisa el parametro cargada de la funcionPredecida en
 *   vez de el de la Prediccion por Funcion.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class PrediccionPorFuncion {
	private int id;
	private int estimacion;
	private PrediccionPorDia prediccionPorDia;
	private Funcion funcionPredecida;
	private double varianza;
	private Grafico graficoRegresionClases;
	private Grafico graficoRegresionCoef;
	private boolean error;
	private Mascara mascara;
	/**
	 * Mascaras posibles.
	 */
	private List<Mascara> mascaras;
	/**
	 * mascara por defecto.
	 */
	private int mascaraPorDefecto;
	private boolean cargada;
	public List<PrediccionPorClase> prediccionesPorClase;
	private boolean cargando;
	private boolean optimizando;
	private boolean optimizada;	
		
			
	public boolean isOptimizada() {
		return optimizada;
	}
	public void setOptimizada(boolean optimizada) {
		this.optimizada = optimizada;
	}
	public boolean isOptimizando() {
		return optimizando;
	}
	public void setOptimizando(boolean optimizando) {
		this.optimizando = optimizando;
	}
	public boolean isCargando() {
		return cargando;
	}
	public void setCargando(boolean cargando) {
		this.cargando = cargando;
	}
	public int getMascaraPorDefecto() {
		return mascaraPorDefecto;
	}
	public void setMascaraPorDefecto(int mascaraPorDefecto) {
		this.mascaraPorDefecto = mascaraPorDefecto;
	}
	public List<Mascara> getMascaras() {
		return mascaras;
	}
	public void setMascaras(List<Mascara> mascaras) {
		this.mascaras = mascaras;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public Grafico getGraficoRegresionClases() {
		return graficoRegresionClases;
	}
	public void setGraficoRegresionClases(Grafico graficoRegresionClases) {
		this.graficoRegresionClases = graficoRegresionClases;
	}
	public Grafico getGraficoRegresionCoef() {
		return graficoRegresionCoef;
	}
	public void setGraficoRegresionCoef(Grafico graficoRegresionCoef) {
		this.graficoRegresionCoef = graficoRegresionCoef;
	}
	public double getVarianza() {
		return varianza;
	}
	public void setVarianza(double varianza) {
		this.varianza = varianza;
	}
	public PrediccionPorDia getPrediccionPorDia() {
		return prediccionPorDia;
	}
	public void setPrediccionPorDia(PrediccionPorDia prediccionPorDia) {
		this.prediccionPorDia = prediccionPorDia;
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
	public Funcion getFuncionPredecida() {
		return funcionPredecida;
	}
	public void setFuncionPredecida(Funcion funcionPredecida) {
		this.funcionPredecida = funcionPredecida;
	}
	public List<PrediccionPorClase> getPrediccionesPorClase() {
		return prediccionesPorClase;
	}
	public void setPrediccionesPorClase(
			ArrayList<PrediccionPorClase> prediccionesPorClase) {
		this.prediccionesPorClase = prediccionesPorClase;
	}
	public boolean getCargada() {
		if(this.funcionPredecida!=null)
		{
			return this.funcionPredecida.getCargada();
		}
		else
		{
			return false;
		}
	}
	public void setCargada(boolean cargada) {
		if(this.funcionPredecida!=null)
		{
			this.funcionPredecida.setCargada(cargada);
		}
	}
	public Mascara getMascara() {
		return mascara;
	}
	public void setMascara(Mascara mascara) {
		this.mascara = mascara;
	}
	public boolean getCargando() {
		return cargando;
	}
}
