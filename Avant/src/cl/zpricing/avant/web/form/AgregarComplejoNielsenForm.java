package cl.zpricing.avant.web.form;


public class AgregarComplejoNielsenForm {
	private int		id;
	private String	nombre;
	private String	fechaDesde;
	private int		cantidadSalas;
	private int		empresaId;
	private String	ciudad;
	private boolean rm;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public int getCantidadSalas() {
		return cantidadSalas;
	}

	public void setCantidadSalas(int cantidadSalas) {
		this.cantidadSalas = cantidadSalas;
	}



	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public void setEmpresaId(int empresaId) {
		this.empresaId = empresaId;
	}

	public int getEmpresaId() {
		return empresaId;
	}

	public void setRm(boolean rm) {
		this.rm = rm;
	}

	public boolean isRm() {
		return rm;
	}

}
