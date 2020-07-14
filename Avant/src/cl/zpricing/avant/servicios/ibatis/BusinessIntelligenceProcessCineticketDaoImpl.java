package cl.zpricing.avant.servicios.ibatis;

import java.util.Date;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.servicios.BusinessIntelligenceExtractionProcessDao;

public class BusinessIntelligenceProcessCineticketDaoImpl extends SqlMapClientDaoSupport implements BusinessIntelligenceExtractionProcessDao {
	@Override
	public void cleanTemporalData() {
		getSqlMapClientTemplate().delete("limpiarTablaTempCineticketTransaction");
		getSqlMapClientTemplate().delete("limpiarTablaTempCineticketCustomer");
	}
	
	@Override
	public void agregarTransacciones() {
		getSqlMapClientTemplate().insert("agregarTransacciones");
	}

	@Override
	public void agregarComunas() {
		getSqlMapClientTemplate().insert("agregarComunas");
	}

	@Override
	public void actualizarDataCliente() {
		getSqlMapClientTemplate().update("actualizarDataCliente");
	}

	@Override
	public void agregarClientesNuevos() {
		getSqlMapClientTemplate().insert("agregarClientesNuevos");
	}
	
	@Override
	public Date obtenerUltimaFechaTransaccion() {
		return  (Date)getSqlMapClientTemplate().queryForObject("obtenerUltimaFechaTransaccionCineticket");
	}
	
	@Override
	public Date obtenerUltimaFechaModificacionCliente() {
		return  (Date) getSqlMapClientTemplate().queryForObject("obtenerUltimaFechaModificacionClienteCineticket");
	}

	@Override
	public void agregarOrigen() {
		getSqlMapClientTemplate().insert("agregarOrigenCineticket");
		
	}

	@Override
	public void actualizarComportamientoAsistenciaClientes() {
		getSqlMapClientTemplate().update("actualizarComportamientoAsistenciaClientesCineticket");
	}
	@Override
	public void agregarComportamientoAsistenciaClientes() {
		getSqlMapClientTemplate().insert("agregarComportamientoAsistencieClientesCineticket");
	}
	@Override
	public void agregarPeliculasVistasClientes() {
		getSqlMapClientTemplate().insert("agregarPeliculasVistasClientesCineticket");
	}
}
