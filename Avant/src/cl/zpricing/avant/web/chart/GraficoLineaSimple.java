/**
 * 
 */
package cl.zpricing.avant.web.chart;

import java.util.Iterator;
import java.util.List;

/**
 * <b>Descripci�n de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 26-05-2009 Mario Lavandero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class GraficoLineaSimple {
	
	private String titulo;
	private String ejeX;
	private String ejeY;
	private String numberPrefix;
	private int showValues;
	private List<String> categorias;
	private List<SerieDatosSimple> valoresSeries;
	private int width;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getEjeX() {
		return ejeX;
	}
	public void setEjeX(String ejeX) {
		this.ejeX = ejeX;
	}
	public String getEjeY() {
		return ejeY;
	}
	public void setEjeY(String ejeY) {
		this.ejeY = ejeY;
	}
	public String getNumberPrefix() {
		return numberPrefix;
	}
	public void setNumberPrefix(String numberPrefix) {
		this.numberPrefix = numberPrefix;
	}
	public int getShowValues() {
		return showValues;
	}
	public void setShowValues(int showValues) {
		this.showValues = showValues;
	}
	public List<String> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}
	public List<SerieDatosSimple> getValoresSeries() {
		return valoresSeries;
	}
	public void setValoresSeries(List<SerieDatosSimple> valoresSeries) {
		this.valoresSeries = valoresSeries;
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @param titulo
	 * @param ejeX
	 * @param ejeY
	 * @param numberPrefix
	 * @param showValues
	 * @param categorias
	 * @param valoresSeries
	 */
	public GraficoLineaSimple(String titulo, String ejeX, String ejeY,
			String numberPrefix, int showValues, List<String> categorias, 
			List<SerieDatosSimple> valoresSeries) {
		this.titulo = titulo;
		this.ejeX = ejeX;
		this.ejeY = ejeY;
		this.numberPrefix = numberPrefix;
		this.showValues = showValues;
		this.categorias = categorias;
		this.valoresSeries = valoresSeries;
		this.width = this.categorias.size()*100;
		if(this.width<500)
		{
			this.width = 500;
		}
	}
	
	public String getXML()
	{
		StringBuilder out = new StringBuilder();
		//inicio
		out.append("<graph caption='"+this.titulo+"' ");
		out.append("xAxisName='"+this.ejeX+"' yAxisName='"+this.ejeY+"' ");
		out.append("showValues='"+this.showValues+"' numberPrefix='"+this.numberPrefix+"' decimalPrecision='0'>");

		//categorias
		out.append("<categories>");
		Iterator<String> iterString = this.categorias.iterator();
		while(iterString.hasNext())
		{
			out.append("<category name='"+iterString.next()+"' />");
		}
		out.append("</categories>");
		Iterator<SerieDatosSimple> iterSerieDatos = this.valoresSeries.iterator();
		while(iterSerieDatos.hasNext())
		{
			SerieDatosSimple serie = iterSerieDatos.next();
			out.append("<dataset seriesName='"+serie.getNombre()+"' color='"+serie.getColor()+"'>");
			Iterator<Integer> iterInt = serie.getValores().iterator();
			while(iterInt.hasNext())
			{
				out.append("<set value='"+iterInt.next()+"' />");
			}
			out.append("</dataset>");
		}
		out.append("</graph>");
		return out.toString();
	}
	

}
