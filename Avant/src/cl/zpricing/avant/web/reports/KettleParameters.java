package cl.zpricing.avant.web.reports;

/***
 * 
 * <b>Contiene los parámetros para usar Kettle dentro de la aplicación, obtenidos
 * desde el archivo kettleConfiguration.xml</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 25-01-2010 Camilo Araya: versión inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */
public class KettleParameters {
	private String urlTransformacion;

	public void setUrlTransformacion(String urlTransformacion) {
		this.urlTransformacion = urlTransformacion;
	}

	public String getUrlTransformacion() {
		return urlTransformacion;
	}

}
