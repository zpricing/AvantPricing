package cl.zpricing.avant.model;

/**
 * <b>Representa un objeto RPT_Empresa de la base de datos, que a su vez
 * representa una empresa de la cual se tiene información extraída de los
 * informes de Nielsen.</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 25-01-2010 Camilo Araya: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */

public class RptEmpresa {
	private int		rptEmpresaId;
	private String	nombre;
	private String	codigo_nielsen;

	public int getRpt_empresa_id() {
		return rptEmpresaId;
	}
	public void setRpt_empresa_id(int rptEmpresaId) {
		this.rptEmpresaId = rptEmpresaId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigo_nielsen() {
		return codigo_nielsen;
	}
	public void setCodigo_nielsen(String codigoNielsen) {
		codigo_nielsen = codigoNielsen;
	}

}
