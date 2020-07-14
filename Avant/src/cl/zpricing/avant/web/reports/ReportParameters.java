package cl.zpricing.avant.web.reports;

/**
 * 
 * <p><b>Representa un parámetro de un reporte. Un ReportRoot puede tener muchos de
 * estos.</b></p>
 * <p>Tiene los siguientes campos:
 * <ul>
 * <li>parametroNombre: el nombre del parámetro. E.g. FromTiempoCinePKDate</li>
 * <li>parametroPrefijo: esto es requerido por el lenguaje que usa Analysis Services (MDX) para consultar el cubo; 
 * corresponse al nombre de la dimensión y la jerarquía sobre la cual se buscará el parámetro. E.g. [TiempoCine].[PK_Date]</li>
 * <li>parametroSufijo: se agrega en algunos casos. E.g. T00:00:00 en el caso de los campos de fecha.</li>
 * <li>parametroWeb: indica cómo se llama el campo del formulario en la web el que se asociará a este parámetro. Permite hacer 
 * un <i>mapping</i> que será interpretado por ReportingServicesController en forma automática. E.g.: fecha_from.</li>
 * <li>convertibleEnFecha: boolean que indica si el parámetro es una fecha o no.</li>
 * <li>dominio: indica que este parámetro no puede tomar cualquier valor, sino que toma un valor desde un dominio definido.
 * AuxiliarNielsenController usa este valor para generar una lista que contiene los valores posibles de este dominio. E.g. RPTComplejos.</li>
 * </ul>
 * </p>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 27-01-2010 Camilo Araya: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */
public class ReportParameters {
	private String	parametroNombre;
	private String	parametroPrefijo;
	private String	parametroSufijo;
	private String	parametroWeb;
	private boolean	convertibleEnFecha;
	private String	dominio;

	public String getParametroNombre() {
		return parametroNombre;
	}

	public void setParametroNombre(String parametroNombre) {
		this.parametroNombre = parametroNombre;
	}

	public String getParametroPrefijo() {
		return parametroPrefijo;
	}

	public void setParametroPrefijo(String parametroPrefijo) {
		this.parametroPrefijo = parametroPrefijo;
	}

	public String getParametroSufijo() {
		return parametroSufijo;
	}

	public void setParametroSufijo(String parametroSufjo) {
		this.parametroSufijo = parametroSufjo;
	}

	public String getParametroWeb() {
		return parametroWeb;
	}

	public void setParametroWeb(String parametroWeb) {
		this.parametroWeb = parametroWeb;
	}

	public void setConvertibleEnFecha(boolean convertibleEnFecha) {
		this.convertibleEnFecha = convertibleEnFecha;
	}

	public boolean isConvertibleEnFecha() {
		return convertibleEnFecha;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public String getDominio() {
		return dominio;
	}

}
