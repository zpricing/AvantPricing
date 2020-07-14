package cl.zpricing.avant.web.form;

/**
 * <b>Formulario utilizado para la administracion de peliculas </b>
 * 
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 10-02-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class PeliculasForm {
	private String[] categorias;
	private String[] epocas;
	private String[] publicos;
	private String id;
	private String idExterno;
	private String nombre;
	private String descripcion;
	private String duracion;
	private boolean activo;
	/**
	 * asociación con central
	 */
	private int idCentral;
	private String nombreCentral;
	private double gradoSimilitud;

	private String[] rankings;
	private String[] ratings;
	private String[] formatos;
	private String[] idiomas;
	
	public int getIdCentral() {
		return idCentral;
	}

	public void setIdCentral(int idCentral) {
		this.idCentral = idCentral;
	}

	public String getNombreCentral() {
		return nombreCentral;
	}

	public void setNombreCentral(String nombreCentral) {
		this.nombreCentral = nombreCentral;
	}

	public double getGradoSimilitud() {
		return gradoSimilitud;
	}

	public void setGradoSimilitud(double gradoSimilitud) {
		this.gradoSimilitud = gradoSimilitud;
	}

	public String getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String[] getCategorias() {
		return categorias;
	}

	
	public void setCategorias(String[] categorias) {
		this.categorias = categorias;
	}

	public String[] getEpocas() {
		return epocas;
	}

	public void setEpocas(String[] epocas) {
		this.epocas = epocas;
	}

	public String[] getPublicos() {
		return publicos;
	}

	public void setPublicos(String[] publicos) {
		this.publicos = publicos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public String[] getRankings() {
		return rankings;
	}

	public void setRankings(String[] rankings) {
		this.rankings = rankings;
	}

	public String[] getRatings() {
		return ratings;
	}

	public void setRatings(String[] ratings) {
		this.ratings = ratings;
	}

	public String[] getFormatos() {
		return formatos;
	}

	public void setFormatos(String[] formatos) {
		this.formatos = formatos;
	}

	public String[] getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(String[] idiomas) {
		this.idiomas = idiomas;
	}

}
