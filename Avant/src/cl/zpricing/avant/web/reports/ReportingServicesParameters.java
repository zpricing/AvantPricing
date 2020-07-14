/**
 * 
 */
package cl.zpricing.avant.web.reports;

import java.util.Map;

/**
 * <b>Representa la configuración del uso del servidor de Reporting Services.
 * </b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 13-01-2010 Camilo Araya: versión inicial.</li>
 * <li>1.1 27-01-2010 Camilo Araya: flexibilización.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */

public class ReportingServicesParameters {
	private boolean					usingReportingServices;
	private boolean					usingClassicReports;
	private String					baseURL;
	private Map<String, ReportRoot>	reportes;
	private boolean					usingToolbar;

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public void setUsingReportingServices(boolean usingReportingServices) {
		this.usingReportingServices = usingReportingServices;
	}

	public boolean isUsingReportingServices() {
		return usingReportingServices;
	}

	public void setUsingClassicReports(boolean usingClassicReports) {
		this.usingClassicReports = usingClassicReports;
	}

	public boolean isUsingClassicReports() {
		return usingClassicReports;
	}

	public void setReportes(Map<String, ReportRoot> reportes) {
		this.reportes = reportes;
	}

	public Map<String, ReportRoot> getReportes() {
		return reportes;
	}

	public void setUsingToolbar(boolean usingToolbar) {
		this.usingToolbar = usingToolbar;
	}

	public boolean isUsingToolbar() {
		return usingToolbar;
	}

}
