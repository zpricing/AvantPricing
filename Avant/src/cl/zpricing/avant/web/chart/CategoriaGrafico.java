/**
 * 
 */
package cl.zpricing.avant.web.chart;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <b>Descripci�n de la Clase</b>
 * Clase para poner las etiquetas a los graficos
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 06-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class CategoriaGrafico implements Elemento{
	private String nombreCategoria;

	public CategoriaGrafico(String nombreCategoria){
		this.nombreCategoria = nombreCategoria;
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.web.chart.Elemento#crearElemento(org.w3c.dom.Document)
	 */
	@Override
	public Element crearElemento(Document documento) {
		// TODO Auto-generated method stub
		Element raizEle = documento.createElement("category");
		raizEle.setAttribute("name", nombreCategoria);
		return raizEle;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return nombreCategoria;
	}

	/**
	 * @return the nombreCategoria
	 */
	public String getNombreCategoria() {
		return nombreCategoria;
	}

	/**
	 * @param nombreCategoria the nombreCategoria to set
	 */
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	
}
