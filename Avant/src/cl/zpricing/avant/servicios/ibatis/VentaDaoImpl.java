package cl.zpricing.avant.servicios.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.VentaDiaria;
import cl.zpricing.avant.servicios.VentaDao;

public class VentaDaoImpl extends SqlMapClientDaoSupport implements VentaDao {

	@Override
	public VentaDiaria obtenerVentaDiariaFecha(Complejo complejo, Date fecha) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("complejo", complejo.getId());
		parametros.put("fecha", fecha);
		return (VentaDiaria) getSqlMapClientTemplate().queryForObject("obtenerVentaDiariaFecha", parametros);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VentaDiaria> obtenerVentaDiariaRangoFecha(Complejo complejo, Date fechaDesde, Date fechaHasta) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("complejo", complejo.getId());
		parametros.put("fecha_desde", fechaDesde);
		parametros.put("fecha_hasta", fechaHasta);
		return (List<VentaDiaria>) getSqlMapClientTemplate().queryForList("obtenerVentaDiariaRangoFechas", parametros);
	}

}
