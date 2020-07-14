package cl.zpricing.avant.web.chart;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cl.zpricing.avant.model.Marker;
import cl.zpricing.avant.model.TipoMarker;

/**
 * <b>Descripci�n de la Clase</b> Clase basica para meter datos en los gr�ficos
 * Si se desea cambiar el tipo de datos, se debe extender Valor y redefinir el
 * m�todo getValor
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 06-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class Valor implements Elemento {
	private long valor;
	private List<String> info;
	private List<Marker> markers;
	private List<TipoMarker> tiposMarkers;

	public Valor() {
		this.valor = 0;
	}

	/**
	 * @param valor
	 */
	public Valor(long valor) {
		this.valor = valor;
	}

	/**
	 * @return valor
	 */
	public long getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(long valor) {
		this.valor = valor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.web.chart.Elemento#crearElemento(org.w3c.dom.Document)
	 */
	@Override
	public Element crearElemento(Document documento) {
		Element raizEle = documento.createElement("set");
		raizEle.setAttribute("value", Long.toString(this.getValor()));
		return raizEle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return Long.toString(valor);
	}

	/**
	 * @return the info
	 */
	public List<String> getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(List<String> info) {
		this.info = info;
	}

	/**
	 * @return the markers
	 */
	public List<Marker> getMarkers() {
		return markers;
	}

	/**
	 * @param markers
	 *            the markers to set
	 */
	public void setMarkers(List<Marker> markers) {
		this.markers = markers;
		this.tiposMarkers = Marker.obtenerTiposMarkers(markers);
	}

	/**
	 * @return the tiposMarkers
	 */
	public List<TipoMarker> getTiposMarkers() {
		return tiposMarkers;
	}

}
