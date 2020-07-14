package cl.zpricing.avant.model;

import java.util.Date;

/**
 * <b>Contiene la asistencia de un día completo para una cierta película
 * (identificada por peliculaId), para una cierta fecha (fecha), indicando
 * además la cantidad de días transcurridos desde su estreno
 * (diasDesdeEstreno) partiendo en 1 para el día del estreno.</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 18-12-2009 Camilo Araya: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */
public class AsistenciaDiaria {
	private String peliculaId;
	private Date fecha;
	private String asistenciaDiaria;
	private int diasDesdeEstreno;
	private String diaDeLaSemana;

	public void setPeliculaId(String pelicula) {
		this.peliculaId = pelicula;
	}

	public String getPeliculaId() {
		return peliculaId;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setAsistenciaDiaria(String asistenciaDiaria) {
		this.asistenciaDiaria = asistenciaDiaria;
	}

	public String getAsistenciaDiaria() {
		return asistenciaDiaria;
	}

	public void setDiasDesdeEstreno(int diasDesdeEstreno) {
		this.diasDesdeEstreno = diasDesdeEstreno;
	}

	public int getDiasDesdeEstreno() {
		return diasDesdeEstreno;
	}

	public void setDiaDeLaSemana(String diaDeLaSemana) {
		this.diaDeLaSemana = diaDeLaSemana;
	}

	public String getDiaDeLaSemana() {
		return diaDeLaSemana;
	}

}
