package cl.zpricing.avant.servicios.ibatis;

import java.util.Date;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.servicios.BusinessIntelligenceExtractionProcessDao;

public class BusinessIntelligenceProcessLoyaltyDaoImpl extends SqlMapClientDaoSupport implements BusinessIntelligenceExtractionProcessDao {

	@Override
	public void cleanTemporalData() {
		getSqlMapClientTemplate().delete("limpiarTablaTempLoyaltyCustomer");
		getSqlMapClientTemplate().delete("limpiarTablaTempLoyaltyTransaction");
	}

	@Override
	public void agregarTransacciones() {
		getSqlMapClientTemplate().insert("agregarTransaccionesLoyalty");
	}

	@Override
	public void agregarComunas() {
		getSqlMapClientTemplate().insert("agregarComunasLoyalty");
	}

	@Override
	public void actualizarDataCliente() {
		getSqlMapClientTemplate().update("actualizarDataClienteLoyalty");
	}

	@Override
	public void agregarClientesNuevos() {
		getSqlMapClientTemplate().insert("agregarClientesNuevosLoyalty");
	}

	@Override
	public Date obtenerUltimaFechaTransaccion() {
		return  (Date)getSqlMapClientTemplate().queryForObject("obtenerUltimaFechaTransaccionLoyalty");
	}

	@Override
	public Date obtenerUltimaFechaModificacionCliente() {
		return  (Date)getSqlMapClientTemplate().queryForObject("obtenerUltimaFechaModificacionClienteLoyalty");
	}

	@Override
	public void agregarOrigen() {
		getSqlMapClientTemplate().insert("agregarOrigenLoyalty");
	}
	
	@Override
	public void actualizarComportamientoAsistenciaClientes() {
		getSqlMapClientTemplate().update("actualizarComportamientoAsistenciaClientesLoyalty");
	}
	@Override
	public void agregarComportamientoAsistenciaClientes() {
		getSqlMapClientTemplate().insert("agregarComportamientoAsistencieClientesLoyalty");
	}
	@Override
	public void agregarPeliculasVistasClientes() {
		getSqlMapClientTemplate().insert("agregarPeliculasVistasClientesLoyalty");
	}
}
