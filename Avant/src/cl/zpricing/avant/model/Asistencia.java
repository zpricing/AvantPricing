package cl.zpricing.avant.model;

/**
 * Clase que asocia la asistencia a una funcion con una clase en particular
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 12-01-2009 Mario Lavandero: version inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class Asistencia extends DescripcionId {
	public Funcion funcionAsociada;
	public Clase clase;
	public int asistencia;

	public Funcion getFuncionAsociada() {
		return funcionAsociada;
	}
	public void setFuncionAsociada(Funcion funcionAsociada) {
		this.funcionAsociada = funcionAsociada;
	}
	public Clase getClase() {
		return clase;
	}
	public void setClase(Clase clase) {
		this.clase = clase;
	}
	public int getAsistencia() {
		return asistencia;
	}
	public void setAsistencia(int asistencia) {
		this.asistencia = asistencia;
	}
}
