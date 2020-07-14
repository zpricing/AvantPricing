package cl.zpricing.avant.model;

import java.util.ArrayList;
import java.util.Date;

import cl.zpricing.avant.model.restriccion.Restriction;

/**
 * Clase para manejar todos los datos asociados a una pelicula
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 09-02-2009 Oliver Cordero: version inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class Pelicula extends DescripcionId {
	public String nombre;
	public int duracion;
	public ArrayList<Funcion> funcionesPlanificadas;
	public ArrayList<Epoca> epocas;
	public ArrayList<Publico> tiposDePublico;
	public ArrayList<Categoria> categorias;
	public boolean activo;
	private ArrayList<Marker> markers;
	private String idExterno;
	private Integer formato3d;
	private Distribuidor distribuidor;
	private Integer idCentral;
	private String nombreCentral;
	private Double gradoSimilitud;
	private Integer formatoId;
	
	private Ranking ranking;
	private Rating rating;
	private Idioma idioma;
	private Formato formato;
	
	private Date fechaEstreno;
	private Integer cantidadDeFunciones;
	
	private Restriction restriccion;
	
	private GrupoPelicula grupoPelicula;

	public String getIdExterno() {
		return idExterno;
	}
	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
	}
	public ArrayList<Marker> getMarkers() {
		return markers;
	}
	public void setMarkers(ArrayList<Marker> markers) {
		this.markers = markers;
	}
	public boolean getActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Funcion> getFuncionesPlanificadas() {
		return funcionesPlanificadas;
	}
	public void setFuncionesPlanificadas(
			ArrayList<Funcion> funcionesPlanificadas) {
		this.funcionesPlanificadas = funcionesPlanificadas;
	}
	public ArrayList<Epoca> getEpocas() {
		return epocas;
	}
	public void setEpocas(ArrayList<Epoca> epocas) {
		this.epocas = epocas;
	}
	public ArrayList<Publico> getTiposDePublico() {
		return tiposDePublico;
	}
	public void setTiposDePublico(ArrayList<Publico> tiposDePublico) {
		this.tiposDePublico = tiposDePublico;
	}
	public ArrayList<Categoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(ArrayList<Categoria> categorias) {
		this.categorias = categorias;
	}
        public Integer getFormato3d() {
		return formato3d;
	}
	public void setFormato3d(Integer formato3d) {
		this.formato3d = formato3d;
	}
	public Distribuidor getDistribuidor() {
		return distribuidor;
	}
	public void setDistribuidor(Distribuidor distribuidor) {
		this.distribuidor = distribuidor;
	}
	public Integer getIdCentral() {
		return idCentral;
	}
	public void setIdCentral(Integer idCentral) {
		this.idCentral = idCentral;
	}
	public String getNombreCentral() {
		return nombreCentral;
	}
	public void setNombreCentral(String nombreCentral) {
		this.nombreCentral = nombreCentral;
	}
	public Double getGradoSimilitud() {
		return gradoSimilitud;
	}
	public void setGradoSimilitud(Double gradoSimilitud) {
		this.gradoSimilitud = gradoSimilitud;
	}
	public Integer getFormatoId() {
		return formatoId;
	}
	public void setFormatoId(Integer formatoId) {
		this.formatoId = formatoId;
	}
	public Ranking getRanking() {
		return ranking;
	}
	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}
	public Rating getRating() {
		return rating;
	}
	public void setRating(Rating rating) {
		this.rating = rating;
	}
	public Idioma getIdioma() {
		return idioma;
	}
	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}
	public Formato getFormato() {
		return formato;
	}
	public void setFormato(Formato formato) {
		this.formato = formato;
	}
	public Date getFechaEstreno() {
		return fechaEstreno;
	}
	public void setFechaEstreno(Date fechaEstreno) {
		this.fechaEstreno = fechaEstreno;
	}
	public Restriction getRestriccion() {
		return restriccion;
	}
	public void setRestriccion(Restriction restriccion) {
		this.restriccion = restriccion;
	}
	public Integer getCantidadDeFunciones() {
		return cantidadDeFunciones;
	}
	public void setCantidadDeFunciones(Integer cantidadDeFunciones) {
		this.cantidadDeFunciones = cantidadDeFunciones;
	}
	public GrupoPelicula getGrupoPelicula() {
		return grupoPelicula;
	}
	public void setGrupoPelicula(GrupoPelicula grupoPelicula) {
		this.grupoPelicula = grupoPelicula;
	}
}
