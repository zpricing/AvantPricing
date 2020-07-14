package cl.zpricing.avant.model;

public class Forecast {
	
	private Integer ranking_id;
	private Integer rating_id;
	private Integer categoria_id;
	private Integer semana;
	private Integer dia_id;
	private Integer hora_id;
	private Integer idioma_id;
	private Integer formato_id;
	private Integer grupo_id;
	
	private Integer asistenciaProyectada;
	private Double puntaje;
	private Double porcentajeOcupacionProyectado;
	private Mascara mascara;
	
	public Integer getAsistenciaProyectada() {
		return asistenciaProyectada;
	}
	public void setAsistenciaProyectada(Integer asistenciaProyectada) {
		this.asistenciaProyectada = asistenciaProyectada;
	}
	public Double getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(Double puntaje) {
		this.puntaje = puntaje;
	}
	public Integer getRanking_id() {
		return ranking_id;
	}
	public void setRanking_id(Integer ranking_id) {
		this.ranking_id = ranking_id;
	}
	public Integer getRating_id() {
		return rating_id;
	}
	public void setRating_id(Integer rating_id) {
		this.rating_id = rating_id;
	}
	public Integer getCategoria_id() {
		return categoria_id;
	}
	public void setCategoria_id(Integer categoria_id) {
		this.categoria_id = categoria_id;
	}
	public Integer getSemana() {
		return semana;
	}
	public void setSemana(Integer semana) {
		this.semana = semana;
	}
	public Integer getDia_id() {
		return dia_id;
	}
	public void setDia_id(Integer dia_id) {
		this.dia_id = dia_id;
	}
	public Integer getHora_id() {
		return hora_id;
	}
	public void setHora_id(Integer hora_id) {
		this.hora_id = hora_id;
	}
	public Integer getIdioma_id() {
		return idioma_id;
	}
	public void setIdioma_id(Integer idioma_id) {
		this.idioma_id = idioma_id;
	}
	public Integer getFormato_id() {
		return formato_id;
	}
	public void setFormato_id(Integer formato_id) {
		this.formato_id = formato_id;
	}
	public Integer getGrupo_id() {
		return grupo_id;
	}
	public void setGrupo_id(Integer grupo_id) {
		this.grupo_id = grupo_id;
	}
	public Double getPorcentajeOcupacionProyectado() {
		return porcentajeOcupacionProyectado;
	}
	public void setPorcentajeOcupacionProyectado(
			Double porcentajeOcupacionProyectado) {
		this.porcentajeOcupacionProyectado = porcentajeOcupacionProyectado;
	}
	public Mascara getMascara() {
		return mascara;
	}
	public void setMascara(Mascara mascara) {
		this.mascara = mascara;
	}
}
