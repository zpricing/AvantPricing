package cl.zpricing.avant.servicios.ibatis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Diaria;
import cl.zpricing.avant.model.Mensual;
import cl.zpricing.avant.model.Semanal;
import cl.zpricing.avant.servicios.ReportesDao;
import cl.zpricing.commons.utils.ErroresUtils;

public class ReportesDaoImpl extends SqlMapClientDaoSupport implements ReportesDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private SimpleDateFormat sdf;

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Diaria> obtenerReporteDiario(Date inicio, Date fin) {
		log.debug("obtenerReporteDiario...");
		
		Map<String, Object> param = new HashMap<String, Object>(2);
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fecha_inicio = sdf.format(inicio);
		String fecha_fin = sdf.format(fin);
		
		param.put("fecha_inicio", fecha_inicio);
		param.put("fecha_fin", fecha_fin);
		
		
		return (ArrayList<Diaria>) getSqlMapClientTemplate().queryForList("obtenerReporteDiario",param);
	}


	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Semanal> obtenerReporteSemanal(Date inicio, Date fin) {
		log.debug("obtenerReporteSemanal...");
		
		Map<String, Object> param = new HashMap<String, Object>(2);
		sdf = new SimpleDateFormat("MM/dd/yyyy");
		String fecha_inicio = sdf.format(inicio);
		String fecha_fin = sdf.format(fin);
		
		param.put("fecha_inicio", fecha_inicio);
		param.put("fecha_fin", fecha_fin);
		
		return (ArrayList<Semanal>) getSqlMapClientTemplate().queryForList("obtenerReporteSemanal",param);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Mensual> obtenerReporteMensual(Date inicio, Date fin) {
		log.debug("obtenerReporteMensual...");
		
		Map<String, Object> param = new HashMap<String, Object>(2);
		sdf = new SimpleDateFormat("MM/dd/yyyy");
		String fecha_inicio = sdf.format(inicio);
		String fecha_fin = sdf.format(fin);
		
		param.put("fecha_inicio", fecha_inicio);
		param.put("fecha_fin", fecha_fin);
		
		return (ArrayList<Mensual>) getSqlMapClientTemplate().queryForList("obtenerReporteMensual",param);
	}

	@Override
	public void actualizarReporteDiario() {
		log.debug("actualizarReporteDiario...");
		
		try {
			getSqlMapClientTemplate().delete("limpiarReporteDiario");
			getSqlMapClientTemplate().insert("actualizarReporteDiario");
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}

	@Override
	public void actualizarReporteSemanal() {
		log.debug("actualizarReporteSemanal...");
		
		try {
			getSqlMapClientTemplate().delete("limpiarReporteSemanal");
			getSqlMapClientTemplate().insert("actualizarReporteSemanal");
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
		
	}

	@Override
	public void actualizarReporteMensual() {
		log.debug("actualizarReporteMensual...");
		
		try {
			getSqlMapClientTemplate().delete("limpiarReporteMensual");
			getSqlMapClientTemplate().insert("actualizarReporteMensual");
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
		
	}
}
