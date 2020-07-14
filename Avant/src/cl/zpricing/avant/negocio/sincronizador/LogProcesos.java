package cl.zpricing.avant.negocio.sincronizador;

import java.util.Date;
import java.util.List;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;


/**
 * <b>Registro de los estados de procesos de RVM.</b>
 * 
 */

public class LogProcesos {
	private Long inicio = new Date().getTime();
	private Long ultimoCambio = new Date().getTime();
	private Long ultimaEjecucionExitosa;
	private Integer id;
	private String nombreProceso;
	private String estado;
	private String estadoSubetapa;
	private String error;
	private String tieneError = "false";
	private List<String> errores;
	
	private static final String enEjecucion = "Running";
	private static final String finalizado = "Finished";
	private static final String iniciando = "Starting";
	
	private long tiempoDeEjecucionMinutos;
	private long tiempoDeEjecucionSegundos;
	private long tiempoDeEjecucionHoras;
	
	public void actualizarHora(){
		ultimoCambio = new Date().getTime();
	}
	
	public String getTiempoDeEjecucionHoras() {
		tiempoDeEjecucionHoras = (this.getFinishTime() - inicio) / (60 * 60 * 1000) % 60;
		return Float.toString(tiempoDeEjecucionHoras);
	}
	
	public String getTiempoDeEjecucionMinutos() {
		tiempoDeEjecucionMinutos = (this.getFinishTime() - inicio) / (60 * 1000) % 60;
		return Float.toString(tiempoDeEjecucionMinutos);
	}

	public String getTiempoDeEjecucionSegundos() {
		tiempoDeEjecucionSegundos = (this.getFinishTime() - inicio) / (1000) % 60;
		return Float.toString(tiempoDeEjecucionSegundos);
	}
	
	private long getFinishTime() {
		long finishTime = this.ultimoCambio;
		if (this.estado.equalsIgnoreCase(LogProcesos.enEjecucion)) {
			finishTime = new Date().getTime();
		}
		return finishTime;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
		actualizarHora();
	}
	
	public JSONObject toJson() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("UltimaEjecucion", new Date(this.inicio).toString());
		json.put("UltimaEjecucionExitosa", this.ultimaEjecucionExitosa == null ? "" : new Date(this.ultimaEjecucionExitosa).toString());
		json.put("Estado", estado);
		json.put("Etapa", estadoSubetapa);
		json.put("Error", error);
		json.put("TieneError", tieneError);
		json.put("Errores", this.errores);
	
		getTiempoDeEjecucionHoras();
		if(this.tiempoDeEjecucionHoras == 0) {
			json.put("TiempoE", getTiempoDeEjecucionMinutos().split("\\.")[0]+" minutos y "+getTiempoDeEjecucionSegundos().split("\\.")[0]+" segundos");
		}
		else {
			json.put("TiempoE", getTiempoDeEjecucionHoras().split("\\.")[0]+" hora(s) , "+getTiempoDeEjecucionMinutos().split("\\.")[0]+" minutos y "+getTiempoDeEjecucionSegundos().split("\\.")[0]+" segundos");
		}

		return json;
	}

	public static String getIniciando() {
		return iniciando;
	}

	public static String getEnEjecucion() {
		return enEjecucion;
	}

	public static String getFinalizado() {
		return finalizado;
	}

	
	public Long getUltimoCambio() {
		return ultimoCambio;
	}
	public void setUltimoCambio(Long ultimoCambio) {
		this.ultimoCambio = ultimoCambio;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getInicio() {
		return inicio;
	}

	public void setInicio(Long inicio) {
		this.inicio = inicio;
		actualizarHora();
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public String getEstado() {
		return estado;
		
	}

	public void setEstado(String estado) {
		this.estado = estado;
		actualizarHora();
	}

	public String getEstadoSubetapa() {
		if(estadoSubetapa==null)
		{
			return "";
		}
		return estadoSubetapa;
	}

	public void setEstadoSubetapa(String estadoSubetapa) {
		this.estadoSubetapa = estadoSubetapa;
		actualizarHora();
	}

	public String getError() {
		if(error==null)
		{
			return "";
		}
		return error;
	}

	public void setError(String error) {
		this.error = error;
		actualizarHora();
	}
	public void setTieneError(String tieneError) {
		this.tieneError = tieneError;
	}
	public String getTieneError() {
		return tieneError;
	}
	public List<String> getErrores() {
		return errores;
	}
	public void setErrores(List<String> errores) {
		this.errores = errores;
	}

	public Long getUltimaEjecucionExitosa() {
		return ultimaEjecucionExitosa;
	}

	public void setUltimaEjecucionExitosa(Long ultimaEjecucionExitosa) {
		this.ultimaEjecucionExitosa = ultimaEjecucionExitosa;
	}
}
