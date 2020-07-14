package cl.zpricing.avant.negocio.sincronizador;


public abstract class MetaProceso {
	private String nombre;
	private String codigo;
	private boolean enEjecucion;
	private String type;
	public abstract Proceso ejecutarProceso();

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public boolean isEnEjecucion() {
		return enEjecucion;
	}
	public void setEnEjecucion(boolean enEjecucion) {
		this.enEjecucion = enEjecucion;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
