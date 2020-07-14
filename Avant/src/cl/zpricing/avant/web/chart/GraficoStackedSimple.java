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
 * <li>1.0 14-07-2009 Mario Lavandero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class GraficoStackedSimple {

	private String caption;
	private String subCaption;
	private String xAxisName;
	private String yAxisName;
	private String alpha;
	private String showNames;
	private String rotateNames;
	private String numDivLines;
	private String numberPrefix;
	private String showValues;
	private String width;
	private String height;
	private List<String> categories;
	private List<SerieDatosSimple> seriesDatos;

	/**
	 * @param caption
	 * @param subCaption
	 * @param axisName
	 * @param axisName2
	 * @param alpha
	 * @param showNames
	 * @param rotateNames
	 * @param numDivLines
	 * @param numberPrefix
	 * @param showValues
	 * @param width
	 * @param height
	 * @param categories
	 * @param seriesDatos
	 */
	public GraficoStackedSimple(String caption, String subCaption,
			String xAxisName, String yAxisName, String alpha, String showNames,
			String rotateNames, String numDivLines, String numberPrefix,
			String showValues, String width, String height,
			List<String> categories, List<SerieDatosSimple> seriesDatos) {
		this.caption = caption;
		this.subCaption = subCaption;
		this.xAxisName = xAxisName;
		this.yAxisName = yAxisName;
		this.alpha = alpha;
		this.showNames = showNames;
		this.rotateNames = rotateNames;
		this.numDivLines = numDivLines;
		this.numberPrefix = numberPrefix;
		this.showValues = showValues;
		this.width = width;
		this.height = height;
		this.categories = categories;
		this.seriesDatos = seriesDatos;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getSubCaption() {
		return subCaption;
	}

	public void setSubCaption(String subCaption) {
		this.subCaption = subCaption;
	}

	public String getXAxisName() {
		return xAxisName;
	}

	public void setXAxisName(String axisName) {
		xAxisName = axisName;
	}

	public String getYAxisName() {
		return yAxisName;
	}

	public void setYAxisName(String axisName) {
		yAxisName = axisName;
	}

	public String getAlpha() {
		return alpha;
	}

	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}

	public String getShowNames() {
		return showNames;
	}

	public void setShowNames(String showNames) {
		this.showNames = showNames;
	}

	public String getRotateNames() {
		return rotateNames;
	}

	public void setRotateNames(String rotateNames) {
		this.rotateNames = rotateNames;
	}

	public String getNumDivLines() {
		return numDivLines;
	}

	public void setNumDivLines(String numDivLines) {
		this.numDivLines = numDivLines;
	}

	public String getNumberPrefix() {
		return numberPrefix;
	}

	public void setNumberPrefix(String numberPrefix) {
		this.numberPrefix = numberPrefix;
	}

	public String getShowValues() {
		return showValues;
	}

	public void setShowValues(String showValues) {
		this.showValues = showValues;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<SerieDatosSimple> getSeriesDatos() {
		return seriesDatos;
	}

	public void setSeriesDatos(List<SerieDatosSimple> seriesDatos) {
		this.seriesDatos = seriesDatos;
	}

	public String getXML() {
		StringBuilder out = new StringBuilder();
		out.append("<graph caption='" + this.caption + "' ");
		if (!this.subCaption.isEmpty())
			out.append("subCaption='" + this.subCaption + "' ");
		out.append("xAxisName='" + this.xAxisName + "' ");
		out.append("yAxisName='" + this.yAxisName + "' ");
		out.append("showNamew='" + this.showNames + "' ");
		out.append("rotateNames='" + this.rotateNames + "' ");
		out.append("numDivLines='" + this.numDivLines + "' ");
		out.append("numberPrefix='" + this.numberPrefix + "' ");
		out.append("showValues='" + this.showValues + "' ");
		out.append(">");
		// categories
		out.append("<categories>");
		Iterator<String> iterCategories = this.categories.iterator();
		while (iterCategories.hasNext())
			out.append("<category name='" + iterCategories.next() + "' />");
		out.append("</categories>");
		// series de datos
		Iterator<SerieDatosSimple> iterSerieDatos = this.seriesDatos.iterator();
		while (iterSerieDatos.hasNext()) {
			SerieDatosSimple serie = iterSerieDatos.next();
			out.append("<dataset seriesName='" + serie.getNombre()
					+ "' color='" + serie.getColor() + "'>");
			Iterator<Integer> iterInt = serie.getValores().iterator();
			while (iterInt.hasNext()) {
				out.append("<set value='" + iterInt.next() + "' />");
			}
			out.append("</dataset>");
		}
		out.append("</graph>");
		return out.toString();
	}

}
