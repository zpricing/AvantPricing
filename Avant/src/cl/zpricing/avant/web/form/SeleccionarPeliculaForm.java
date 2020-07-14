/**
 * 
 */
package cl.zpricing.avant.web.form;

/**
 * <b>Formulario encargado de la vista seleccionarpeliculas</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 05-01-2009 Daniel Estévez Garay: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */
public class SeleccionarPeliculaForm {

	private int[] id_complejos;
	private int id_pelicula;
	private int dias_a_predecir;
	private String fecha;
	private int[] id_peliculas;
	private int[] id_peliculas_seleccionada;
	private int[] categorias;
	private int[] epocas;
	private int[] publicos;
	private int primeros_dias_a_elegir;
	private int dias_a_saltar;
	private String rango;
	private String direccion;
	private int tipo_de_rango;

	private int ponderador;

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the rango
	 */
	public String getRango() {
		return rango;
	}

	/**
	 * @param rango
	 *            the rango to set
	 */
	public void setRango(String rango) {
		this.rango = rango;
	}

	/**
	 * @return the primeros_dias_a_elegir
	 */
	public int getPrimeros_dias_a_elegir() {
		return primeros_dias_a_elegir;
	}

	/**
	 * @param primeros_dias_a_elegir
	 *            the primeros_dias_a_elegir to set
	 */
	public void setPrimeros_dias_a_elegir(int primeros_dias_a_elegir) {
		this.primeros_dias_a_elegir = primeros_dias_a_elegir;
	}

	/**
	 * @return the dias_a_saltar
	 */
	public int getDias_a_saltar() {
		return dias_a_saltar;
	}

	/**
	 * @param dias_a_saltar
	 *            the dias_a_saltar to set
	 */
	public void setDias_a_saltar(int dias_a_saltar) {
		this.dias_a_saltar = dias_a_saltar;
	}

	/**
	 * @return the dias_a_predecir
	 */
	public int getDias_a_predecir() {
		return dias_a_predecir;
	}

	/**
	 * @param dias_a_predecir
	 *            the dias_a_predecir to set
	 */
	public void setDias_a_predecir(int dias_a_predecir) {
		this.dias_a_predecir = dias_a_predecir;
	}

	/**
	 * @return the id_complejos
	 */
	public int[] getId_complejos() {
		return id_complejos;
	}

	/**
	 * @param id_complejos
	 *            the id_complejos to set
	 */
	public void setId_complejos(int[] id_complejos) {
		this.id_complejos = id_complejos;
	}

	/**
	 * @return the id_pelicula
	 */
	public int getId_pelicula() {
		return id_pelicula;
	}

	/**
	 * @param id_pelicula
	 *            the id_pelicula to set
	 */
	public void setId_pelicula(int id_pelicula) {
		this.id_pelicula = id_pelicula;
	}

	/**
	 * @return the id_peliculas
	 */
	public int[] getId_peliculas() {
		return id_peliculas;
	}

	/**
	 * @param id_peliculas
	 *            the id_peliculas to set
	 */
	public void setId_peliculas(int[] id_peliculas) {
		this.id_peliculas = id_peliculas;
	}

	/**
	 * @return the id_peliculas_seleccionada
	 */
	public int[] getId_peliculas_seleccionada() {
		return id_peliculas_seleccionada;
	}

	/**
	 * @param id_peliculas_seleccionada
	 *            the id_peliculas_seleccionada to set
	 */
	public void setId_peliculas_seleccionada(int[] id_peliculas_seleccionada) {
		this.id_peliculas_seleccionada = id_peliculas_seleccionada;
	}

	/**
	 * @return the categorias
	 */
	public int[] getCategorias() {
		return categorias;
	}

	/**
	 * @param categorias
	 *            the categorias to set
	 */
	public void setCategorias(int[] categorias) {
		this.categorias = categorias;
	}

	/**
	 * @return the epocas
	 */
	public int[] getEpocas() {
		return epocas;
	}

	/**
	 * @param epocas
	 *            the epocas to set
	 */
	public void setEpocas(int[] epocas) {
		this.epocas = epocas;
	}

	/**
	 * @return the publicos
	 */
	public int[] getPublicos() {
		return publicos;
	}

	/**
	 * @param publicos
	 *            the publicos to set
	 */
	public void setPublicos(int[] publicos) {
		this.publicos = publicos;
	}

	/**
	 * @param ponderador
	 */
	public void setPonderador(int ponderador) {
		this.ponderador = ponderador;
	}

	/**
	 * @return ponderador
	 */
	public int getPonderador() {
		return ponderador;
	}

	/**
	 * @param tipo_de_rango
	 */
	public void setTipo_de_rango(int tipo_de_rango) {
		this.tipo_de_rango = tipo_de_rango;
	}

	/**
	 * @return tipo_de_rango
	 */
	public int getTipo_de_rango() {
		return tipo_de_rango;
	}

}
