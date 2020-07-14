package cl.zpricing.avant.model;
/**
 * <p>Clase que determina la capacidad total, disponible y ocupada de una determinada area para una función.</p>
 *
 * <p>
 * <b>Registro de versiones:</b>
 * <ul>
 * 	   <li>1.0 (08-05-2010: Nicolas Dujovne W.): version inicial.</li>
 * </ul>
 * </p>
 * <p>
 *   <b>Todos los derechos reservados por ZhetaPricing.</b>
 * </p>
 */
public class FuncionArea {
	private Area area;
	private int capacidadTotal;
	private int capacidadDisponible;
	private int capacidadOcupada;
	private int capacidadReservada;
	private int diasAntesExpira;
	private String idExterno;
	
	private Precio precio;
	
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public int getCapacidadTotal() {
		return capacidadTotal;
	}
	public void setCapacidadTotal(int capacidadTotal) {
		this.capacidadTotal = capacidadTotal;
	}
	public int getCapacidadDisponible() {
		return capacidadDisponible;
	}
	public void setCapacidadDisponible(int capacidadDisponible) {
		this.capacidadDisponible = capacidadDisponible;
	}
	public int getCapacidadOcupada() {
		return capacidadOcupada;
	}
	public void setCapacidadOcupada(int capacidadOcupada) {
		this.capacidadOcupada = capacidadOcupada;
	}
	public int getCapacidadReservada() {
		return capacidadReservada;
	}
	public void setCapacidadReservada(int capacidadReservada) {
		this.capacidadReservada = capacidadReservada;
	}
	public String getIdExterno() {
		return idExterno;
	}
	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
	}
	public Precio getPrecio() {
		return precio;
	}
	public void setPrecio(Precio precio) {
		this.precio = precio;
	}
	public int getDiasAntesExpira() {
		return diasAntesExpira;
	}
	public void setDiasAntesExpira(int diasAntesExpira) {
		this.diasAntesExpira = diasAntesExpira;
	}
}
