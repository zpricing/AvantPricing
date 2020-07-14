package cl.zpricing.avant.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Clase utilizada para manejar todos los datos referente a una sala
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 09-02-2009 Oliver Cordero: version inicial.</li>
 *   <li>1.1 02-06-2009 Mario Lavandero: agregado orden.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class Sala extends DescripcionId {
    private int capacidad;
    private String numero;
    public HashMap<Date, ArrayList<Funcion>> funciones;
    public Complejo complejoAlCualPertenece;
    public TipoSala tipoSala;
    public Grupo grupo;
    private String idExterno;
    private int orden;
    private ArrayList<Mascara> mascaras;

    
    public ArrayList<Mascara> getMascaras() {
		return mascaras;
	}
	public void setMascaras(ArrayList<Mascara> mascaras) {
		this.mascaras = mascaras;
	}
    public String getNumero(){
    	return numero;
    }
    public void setNumero(String numero){
    	this.numero = numero;
    }
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	public HashMap<Date, ArrayList<Funcion>> getFunciones() {
		return funciones;
	}
	public void setFunciones(HashMap<Date, ArrayList<Funcion>> funciones) {
		this.funciones = funciones;
	}
	public Complejo getComplejoAlCualPertenece() {
		return complejoAlCualPertenece;
	}
	public void setComplejoAlCualPertenece(Complejo complejoAlCualPertenece) {
		this.complejoAlCualPertenece = complejoAlCualPertenece;
	}
	public TipoSala getTipoSala() {
		return tipoSala;
	}
	public void setTipoSala(TipoSala tipoSala) {
		this.tipoSala = tipoSala;
	}
	 public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public String getIdExterno() {
		return idExterno;
	}
	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
}