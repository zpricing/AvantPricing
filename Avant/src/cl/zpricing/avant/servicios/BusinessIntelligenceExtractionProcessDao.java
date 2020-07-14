package cl.zpricing.avant.servicios;

import java.util.Date;

public interface BusinessIntelligenceExtractionProcessDao {
	public void cleanTemporalData();
	public void agregarTransacciones();
	public void agregarComunas();
	public void actualizarDataCliente();
	public void agregarClientesNuevos();
	public void agregarOrigen();
	
	public Date obtenerUltimaFechaTransaccion();
	public Date obtenerUltimaFechaModificacionCliente();
	
	public void actualizarComportamientoAsistenciaClientes();
	public void agregarComportamientoAsistenciaClientes();
	public void agregarPeliculasVistasClientes();
}
