package cl.zpricing.avant.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Clase modelo que representa un Complejo
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 12-01-2009 Julio Olivares Alarcon: version inicial.</li>
 *   <li>1.1 25-01-2010 Camilo Araya: se le agregó el identificador del complejo de Nielsen</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class Complejo implements Serializable {
	private static final long serialVersionUID = 7333519159056593058L;
	private int id;
    private String nombre;
    private String direccion;
    private Empresa empresa;
    private String servidorIp;
    private String servidorUsuario;
    private String servidorPassword;
    private String servidorBaseDeDatos;
    private Date ultimaCargaCompleta;
    private Date ultimaCargaFunciones;
    
    private String complejo_id_externo;
    private ArrayList<Area> areas;
    private ArrayList<Marker> markers;
    private RptComplejo rptComplejo;
    
    /* Constructores */
    public Complejo() { }
    
    public Complejo(int id) { 
    	this.id = id;
    }
    
    /* Setters y Getters*/
	public ArrayList<Marker> getMarkers() {
		return markers;
	}
	public void setMarkers(ArrayList<Marker> markers) {
		this.markers = markers;
	}
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setComplejo_id_externo(String complejo_id_externo) {
		this.complejo_id_externo = complejo_id_externo;
	}
	public String getComplejo_id_externo() {
		return complejo_id_externo;
	}
	public ArrayList<Area> getAreas() {
		return areas;
	}
	public void setAreas(ArrayList<Area> areas) {
		this.areas = areas;
	}
	public void setRptComplejo(RptComplejo rptComplejo) {
		this.rptComplejo = rptComplejo;
	}
	public RptComplejo getRptComplejo() {
		return rptComplejo;
	}

	public String getServidorIp() {
		return servidorIp;
	}
	public void setServidorIp(String servidorIp) {
		this.servidorIp = servidorIp;
	}
	public String getServidorUsuario() {
		return servidorUsuario;
	}
	public void setServidorUsuario(String servidorUsuario) {
		this.servidorUsuario = servidorUsuario;
	}
	public String getServidorPassword() {
		return servidorPassword;
	}
	public void setServidorPassword(String servidorPassword) {
		this.servidorPassword = servidorPassword;
	}
	public String getServidorBaseDeDatos() {
		return servidorBaseDeDatos;
	}
	public void setServidorBaseDeDatos(String servidorBaseDeDatos) {
		this.servidorBaseDeDatos = servidorBaseDeDatos;
	}
	public Date getUltimaCargaCompleta() {
		return ultimaCargaCompleta;
	}
	public void setUltimaCargaCompleta(Date ultimaCargaCompleta) {
		this.ultimaCargaCompleta = ultimaCargaCompleta;
	}
	public Date getUltimaCargaFunciones() {
		return ultimaCargaFunciones;
	}
	public void setUltimaCargaFunciones(Date ultimaCargaFunciones) {
		this.ultimaCargaFunciones = ultimaCargaFunciones;
	}
}
