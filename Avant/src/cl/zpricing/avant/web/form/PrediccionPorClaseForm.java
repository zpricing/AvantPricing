package cl.zpricing.avant.web.form;


/**
 * <b>Form de vista predicciones</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 23-01-2009 Daniel Estévez Garay: versión inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class PrediccionPorClaseForm {
	
	private String[] mascaras;
	private int tipo_regresion;
	
	private int[][][][] mascaraFuncion;
	
	public PrediccionPorClaseForm() {}
	
	public PrediccionPorClaseForm(int numPredicciones, int maxNumDias, int maxNumSalas, int maxNumFunciones) {
		mascaraFuncion = new int[numPredicciones][maxNumDias][maxNumSalas][maxNumFunciones];
	}
	
	public void setMascaraFuncion(int prediccion, int dia, int sala, int funcion, int mascara) {
		mascaraFuncion[prediccion][dia][sala][funcion] = mascara;
	}
	public int getMascaraFuncion(int prediccion, int dia, int sala, int funcion) {
		return mascaraFuncion[prediccion][dia][sala][funcion];
	}
	public void setTipo_regresion(int tipo_regresion) {
		this.tipo_regresion = tipo_regresion;
	}
	public String[] getMascaras() {
		return mascaras;
	}
	public void setMascaras(String[] mascaras) {
		this.mascaras = mascaras;
	}
	public int[][][][] getMascaraFuncion() {
		return mascaraFuncion;
	}
	public void setMascaraFuncion(int[][][][] mascaraFuncion) {
		this.mascaraFuncion = mascaraFuncion;
	}
	public int getTipo_regresion() {
		return tipo_regresion;
	}
}
