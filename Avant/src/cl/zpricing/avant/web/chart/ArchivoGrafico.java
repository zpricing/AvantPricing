/**
 * 
 */
package cl.zpricing.avant.web.chart;

import javax.servlet.http.HttpServletRequest;

/**
 * <b>Descripci�n de la Clase</b>
 * Clase utilizada para poder generar y guardar la ruta de un archivo xml de un
 * grafico
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 08-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class ArchivoGrafico {
	private String nombre;
	private HttpServletRequest request;
	
	public ArchivoGrafico(HttpServletRequest request, String nombre) {
		//GregorianCalendar.getInstance().getTime().getTime()
		this.request = request;
		this.nombre = request.getSession().getId()+nombre;
	}
	
	/**
	 * Entrega la ruta del archivo que se debe usar en el codigo java
	 * @return ruta interna del grafico
	 */
	public String rutaGraficoInterna(){
		if(request.getContextPath().compareTo("/")!=0)
			return "webapps"+request.getContextPath()+"/grafico/"+nombre+".xml";
		else
			return "webapps/grafico/"+nombre+".xml";
	}
	
	/**
	 * Entrega la ruta del archivo que se debe usar en el JSP
	 * @return ruta externa del grafico
	 */
	public String rutaGraficoExterna(){
		return "/grafico/"+nombre+".xml";
	}
}
