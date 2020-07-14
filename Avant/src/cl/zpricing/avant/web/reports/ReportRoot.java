package cl.zpricing.avant.web.reports;

import java.util.Map;

/**
 * 
 * <b>Esta clase representa un reporte de Reporting Services y contiene su
 * ubicación en el servidor y una lista con los parámetros requeridos para
 * ejecutarlo.</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 27-01-2010 Camilo Araya: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */
public class ReportRoot {
	private String							ubicacion;
	private Map<String, ReportParameters>	parametros;
	private boolean							ocultarExportacion = false;

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public void setParametros(Map<String, ReportParameters> parametros) {
		this.parametros = parametros;
	}

	public Map<String, ReportParameters> getParametros() {
		return parametros;
	}

	public void setOcultarExportacion(boolean ocultarExportacion) {
		this.ocultarExportacion = ocultarExportacion;
	}

	public boolean isOcultarExportacion() {
		return ocultarExportacion;
	}

}
