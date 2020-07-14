package cl.zpricing.avant.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Clase utilizada para guardar informacion extra no manejada por el sistema
 * directamente que sea relevante para fijar precios
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 30-12-2008 Julio Andros Olivares Alarcon: version inicial.</li>
 * <li>1.0 28-01-2009 Mario Lavandero: Agregado campo Clase.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class Marker extends DescripcionId {
	private Date fecha;
	private Date fechaHasta;
	private TipoMarker tipoMarker;
	private Usuario usuario;
	private Pelicula pelicula;
	private Complejo complejo;
	private Complejo[] complejos;
	private Clase clase;

	public void setTipoMarker(TipoMarker tipoMarker) {
		this.tipoMarker = tipoMarker;
	}
	public TipoMarker getTipoMarker() {
		return tipoMarker;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}
	public Pelicula getPelicula() {
		return pelicula;
	}
	public void setComplejo(Complejo complejo) {
		this.complejo = complejo;
	}
	public Complejo getComplejo() {
		return complejo;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Date getFecha() {
		return fecha;
	}
	public Date getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public Clase getClase() {
		return clase;
	}
	public void setClase(Clase clase) {
		this.clase = clase;
	}

	/**
	 * Funcion para obtener los tipo de marker presentes en una lista de markers
	 * 
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 12-02-2009 Oliver Cordero. Version Inicial</li>
	 * </ul>
	 * 
	 * @param lista
	 *            de markers
	 * @return lista con los tipos de markers
	 * 
	 *         <P>
	 *         <B>Todos los derechos reservados por ZhetaPricing.</B>
	 *         <P>
	 */
	public static List<TipoMarker> obtenerTiposMarkers(List<Marker> markers) {
		Map<String, TipoMarker> tiposMarkers = new HashMap<String, TipoMarker>();
		if (markers != null) {
			Iterator<Marker> itMarker = markers.iterator();
			while (itMarker.hasNext()) {
				Marker marker = itMarker.next();
				if (marker.tipoMarker != null) {
					String idTipo = Integer.toString(marker.tipoMarker.getId());
					if (tiposMarkers.get(idTipo) == null)
						tiposMarkers.put(idTipo, marker.tipoMarker);
				}
			}
			Object[] arreglo = tiposMarkers.values().toArray();
			List<TipoMarker> lista = new ArrayList<TipoMarker>();
			for (int i = 0; i < arreglo.length; i++)
				lista.add((TipoMarker) arreglo[i]);
			return lista;
		}
		return null;
	}
	public Complejo[] getComplejos() {
		return complejos;
	}
	public void setComplejos(Complejo[] complejos) {
		this.complejos = complejos;
	}
}
