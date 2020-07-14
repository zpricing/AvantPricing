package cl.zpricing.avant.servicios.ibatis;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.loadmanager.TimeSpan;
import cl.zpricing.avant.negocio.MarkerManager;
import cl.zpricing.avant.servicios.TimeSpanDao;
import cl.zpricing.commons.utils.DateUtils;

public class TimeSpanDaoImpl extends SqlMapClientDaoSupport implements TimeSpanDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private MarkerManager markerManager;
	
/*
	@SuppressWarnings("unchecked")
	@Override
	public List<TimeSpan> obtenerTodos() {
		return (List<TimeSpan>) getSqlMapClientTemplate().queryForList("obtenerTodosTimeSpan");
	}
*/
	public TimeSpan obtenerTimeSpan3d(Complejo complejo) {
		return (TimeSpan)getSqlMapClientTemplate().queryForObject("obtenerTimeSpan3d", complejo.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TimeSpan> obtenerTodosParaComplejo(Complejo complejo) {
		log.debug("obtenerTodosParaComplejo");
		return (List<TimeSpan>)getSqlMapClientTemplate().queryForList("obtenerTodosParaComplejo", complejo.getId());
	}

	@Override
	public TimeSpan obtenerTimeSpan(Complejo complejo, Funcion funcion) {
		if (funcion.getPeliculaAsociada().getFormato3d() == 1) {
			return (TimeSpan) getSqlMapClientTemplate().queryForObject("obtenerTimeSpan3d", complejo.getId());
		}
		
		Calendar calendarioContable = Calendar.getInstance();
		calendarioContable.setTime(funcion.getFechaContable());
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("complejo_id", complejo.getId());
		log.debug("complejo_id: " + complejo.getId());
		
		boolean esFeriado = markerManager.isFechaFeriado(calendarioContable.getTime(), complejo);
		
		String dia = null; 
		if (esFeriado) {
			dia = "D";
		}
		else {
			dia = DateUtils.obtenerRepresentacionDia(calendarioContable.get(Calendar.DAY_OF_WEEK));
		}
		mapa.put("dia", "%" + dia + "%");
		
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(funcion.getFecha());
		Integer hora = calendario.get(Calendar.HOUR_OF_DAY);
		
		log.debug("esTrasnoche?: " + funcion.esTrasnoche());
		if (funcion.esTrasnoche()) {
			mapa.put("hora_inicio", 23);
			mapa.put("hora_fin", 23);
		}
		else {
			mapa.put("hora_inicio", hora);
			mapa.put("hora_fin", hora);
		}
		log.debug("dia: " + dia);
		log.debug("hora: " + hora);
		
		return (TimeSpan) getSqlMapClientTemplate().queryForObject("obtenerTimeSpanPorComplejoFuncion", mapa);
	}

	public void setMarkerManager(MarkerManager markerManager) {
		this.markerManager = markerManager;
	}
}
