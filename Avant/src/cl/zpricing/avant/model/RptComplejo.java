package cl.zpricing.avant.model;

import java.util.Date;

/**
 * 
 * <b>Representa un objeto RPT_Complejo de la base de datos; corresponde a un complejo de los
 * reportes de Nielsen.</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 25-01-2010 Camilo Araya: versión inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */
public class RptComplejo {
	private int rptComplejoId;
	private String nombre;
	private Date fechaDesde;
	private int cantidadSalas;
	private int rptEmpresaId;
	private RptEmpresa rptEmpresa;
	private String ciudad;
	private Boolean rm;
	
	public void setRptComplejoId(int rptComplejoId) {
		this.rptComplejoId = rptComplejoId;
	}
	public int getRptComplejoId() {
		return rptComplejoId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public int getCantidadSalas() {
		return cantidadSalas;
	}
	public void setCantidadSalas(int cantidadSalas) {
		this.cantidadSalas = cantidadSalas;
	}
	public int getRptEmpresaId() {
		return rptEmpresa.getRpt_empresa_id();
	}
	public void setRptEmpresaId(int rptEmpresaId) {
		this.rptEmpresaId = rptEmpresaId;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public void setRptEmpresa(RptEmpresa rptEmpresa) {
		this.rptEmpresaId = rptEmpresa.getRpt_empresa_id();
		this.rptEmpresa = rptEmpresa;
	}
	public RptEmpresa getRptEmpresa() {
		return rptEmpresa;
	}
	public void setRm(Boolean rm) {
		this.rm = rm;
	}
	public Boolean isRm() {
		return rm;
	}
	public Boolean getRm() {
		return rm;
	}
}
