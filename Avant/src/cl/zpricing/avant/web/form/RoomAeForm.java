/**
 *
 */
package cl.zpricing.avant.web.form;

/**
 * Clase de formulario para Administrar salas
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 17-12-2008 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class RoomAeForm {
	private String id;
	
    private String capacidad;
    
    private String numero;

    private String complejoAlCualPertenece;
    
    private String tipoSala;
    
    private String grupo;

	private String tipoIng;
    
    private String idExterno;
    
    private String orden;
    
    /**
     * @return tipoIng
     */
    public String getTipoIng(){
    	return tipoIng;
    }
    
    /**
     * @param tipoIng
     */
    public void setTipoIng(String tipoIng){
    	this.tipoIng = tipoIng;
    }

    /**
     * @return numero
     */
    public String getNumero(){
    	return numero;
    }
    
    /**
     * @param numero
     */
    public void setNumero(String numero){
    	this.numero = numero;
    }
    
	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return capacidad
	 */
	public String getCapacidad() {
		return capacidad;
	}

	/**
	 * @param capacidad
	 */
	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	/**
	 * @return complejoAlCualPertenece
	 */
	public String getComplejoAlCualPertenece() {
		return complejoAlCualPertenece;
	}

	public void setComplejoAlCualPertenece(String complejoAlCualPertenece) {
		this.complejoAlCualPertenece = complejoAlCualPertenece;
	}
	
	public String getTipoSala() {
		return tipoSala;
	}

	public void setTipoSala(String tipoSala) {
		this.tipoSala = tipoSala;
	}
	
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	/**
	 * @return the idExterno
	 */
	public String getIdExterno() {
		return idExterno;
	}

	/**
	 * @param idExterno the idExterno to set
	 */
	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

}
