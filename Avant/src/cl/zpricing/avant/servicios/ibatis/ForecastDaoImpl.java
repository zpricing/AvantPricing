package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Forecast;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.servicios.ForecastDao;
import cl.zpricing.avant.servicios.FuncionException;
import cl.zpricing.commons.utils.ErroresUtils;

public class ForecastDaoImpl extends SqlMapClientDaoSupport implements ForecastDao{
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	public Integer getAsistenciaProyectada(Funcion funcion) {
		Integer asistenciaProyectada = null;
		
		return asistenciaProyectada;
	}
	
	public Forecast obtenerForecast(Funcion funcion, Date fechaUltimaCargaAsistencias) throws FuncionException {
		log.debug("obtenerForecast...");
		validateFuncion(funcion);
		
		log.debug("funcion id: " + funcion.getId());
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("complejo_id", funcion.getSala().getComplejoAlCualPertenece().getId());
		mapa.put("pelicula_id", funcion.getPeliculaAsociada().getId());
		mapa.put("fecha", funcion.getFechaContable());
		mapa.put("fecha_hora", funcion.getFecha());
		mapa.put("fecha_ultima_carga_asistencias", fechaUltimaCargaAsistencias);
		mapa.put("ranking_id", funcion.getPeliculaAsociada().getRanking().getId());
		mapa.put("rating_id", funcion.getPeliculaAsociada().getRating().getId());
		mapa.put("categoria_id", funcion.getPeliculaAsociada().categorias.get(0).getId());
		mapa.put("exhibicion", 1);
		mapa.put("semana", funcion.getSemana());
		mapa.put("dia", funcion.getDia().getId());
		mapa.put("hora", funcion.getHora().getId());
		mapa.put("idioma_id", funcion.getPeliculaAsociada().getIdioma().getId());
		mapa.put("formato_id", funcion.getPeliculaAsociada().getFormato().getId());
		mapa.put("sala_id", funcion.getSala().getId());
		mapa.put("grupo_id", funcion.getSala().getGrupo().getId());
		
		Forecast forecast = (Forecast) getSqlMapClientTemplate().queryForObject("obtenerForecast", mapa);
		
		return forecast;
	}
	
	public void agregarAsistenciaFuncion(Funcion funcion) throws FuncionException {
		log.debug("agregarAsistenciaFuncion...");
		validateFuncion(funcion);
		
		log.debug("funcion id: " + funcion.getId());
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("complejo_id", funcion.getSala().getComplejoAlCualPertenece().getId());
		mapa.put("funcion_id", funcion.getId());
		mapa.put("fecha", funcion.getFecha());
		mapa.put("ranking_id", funcion.getPeliculaAsociada().getRanking().getId());
		mapa.put("rating_id", funcion.getPeliculaAsociada().getRating().getId());
		mapa.put("categoria_id", funcion.getPeliculaAsociada().categorias.get(0).getId());
		mapa.put("semana", funcion.getSemana());
		mapa.put("exhibicion", funcion.getExhibicion());
		mapa.put("dia_id", funcion.getDia().getId());
		mapa.put("hora_id", funcion.getHora().getId());
		mapa.put("idioma_id", funcion.getPeliculaAsociada().getIdioma().getId());
		mapa.put("formato_id", funcion.getPeliculaAsociada().getFormato().getId());
		mapa.put("sala_id", funcion.getSala().getId());
		mapa.put("grupo_id", funcion.getSala().getGrupo().getId());
		mapa.put("asistencia_total", funcion.getAsistenciaTotal());
		
		try {
			getSqlMapClientTemplate().insert("agregarAsistenciaFuncion", mapa);
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}
	
	public void actualizarForecast(Complejo complejo) {
		log.debug("actualizarForecast...");
		try {
			getSqlMapClientTemplate().delete("eliminarForecast", complejo.getId());
			getSqlMapClientTemplate().update("actualizarForecast", complejo.getId());
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}
	
	public void actualizarSimilitudCategoria(Complejo complejo) {
		log.debug("actualizarSimilitudCategoria...");
		try {
			getSqlMapClientTemplate().delete("eliminarSimilitudCategoria", complejo.getId());
			getSqlMapClientTemplate().update("actualizarSimilitudCategoria", complejo.getId());
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}
	
	public void actualizarSimilitudDiaHora(Complejo complejo) {
		log.debug("actualizarSimilitudDiaHora...");
		try {
			getSqlMapClientTemplate().delete("eliminarSimilitudDiaHora", complejo.getId());
			getSqlMapClientTemplate().update("actualizarSimilitudDiaHora", complejo.getId());
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}
	
	public void actualizarSimilitudFormato(Complejo complejo) {
		log.debug("actualizarSimilitudFormato...");
		try {
			getSqlMapClientTemplate().delete("eliminarSimilitudFormato", complejo.getId());
			getSqlMapClientTemplate().update("actualizarSimilitudFormato", complejo.getId());
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}
	
	public void actualizarSimilitudGrupo(Complejo complejo) {
		log.debug("actualizarSimilitudGrupo...");
		try {
			getSqlMapClientTemplate().delete("eliminarSimilitudGrupo", complejo.getId());
			getSqlMapClientTemplate().update("actualizarSimilitudGrupo", complejo.getId());
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}
	
	public void actualizarSimilitudIdioma(Complejo complejo) {
		log.debug("actualizarSimilitudIdioma...");
		try {
			getSqlMapClientTemplate().delete("eliminarSimilitudIdioma", complejo.getId());
			getSqlMapClientTemplate().update("actualizarSimilitudIdioma", complejo.getId());
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}
	
	public void actualizarSimilitudRanking(Complejo complejo) {
		log.debug("actualizarSimilitudRanking...");
		try {
			getSqlMapClientTemplate().delete("eliminarSimilitudRanking", complejo.getId());
			getSqlMapClientTemplate().update("actualizarSimilitudRanking", complejo.getId());
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}
	
	public void actualizarSimilitudRating(Complejo complejo) {
		log.debug("actualizarSimilitudRating...");
		try {
			getSqlMapClientTemplate().delete("eliminarSimilitudRating", complejo.getId());
			getSqlMapClientTemplate().update("actualizarSimilitudRating", complejo.getId());
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}
	
	public void actualizarSimilitudSemana(Complejo complejo) {
		log.debug("actualizarSimilitudSemana...");
		try {
			getSqlMapClientTemplate().delete("eliminarSimilitudSemana", complejo.getId());
			getSqlMapClientTemplate().update("actualizarSimilitudSemana", complejo.getId());
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}
	
	public void actualizarEstadisticas() {
		log.debug("actualizarEstadisticas...");
		try {
			getSqlMapClientTemplate().update("actualizarEstadisticas");
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}
	
	protected void validateFuncion(Funcion funcion) throws FuncionException {
		ArrayList<String> errores = new ArrayList<String>();
		
		if(	funcion.getHora() == null) {
			errores.add("La Hora de la funcion es null.");
		}
		if(	funcion.getDia() == null) {
			errores.add("El Dia de la funcion es null.");
		}
		if(	funcion.getSemana() == null) {
			errores.add("La Semana de la funcion es null.");
		}
		if(	funcion.getSala() == null) {
			errores.add("La Sala de la funcion es null.");
		}
		if(	funcion.getSala().getGrupo() == null) {
			errores.add("El Grupo de la sala de la funcion es null.");
		}
		if(	funcion.getSala().getComplejoAlCualPertenece() == null) {
			errores.add("El Complejo de la sala de la funcion es null.");
		}
		if(	funcion.getPeliculaAsociada().getRanking() == null) {
			errores.add("El Ranking de la pelicula de la funcion es null.");
		}
		if(	funcion.getPeliculaAsociada().getRating() == null) {
			errores.add("El Rating de la pelicula de la funcion es null.");
		}
		if(	funcion.getPeliculaAsociada().getCategorias() == null || 
			funcion.getPeliculaAsociada().getCategorias().size() == 0) {
			errores.add("La Categoria de la pelicula de la funcion es null.");
		}
		if(	funcion.getPeliculaAsociada().getIdioma() == null) {
			errores.add("El Idioma de la pelicula de la funcion es null.");
		}
		if(	funcion.getPeliculaAsociada().getFormato() == null) {
			errores.add("El Formato de la pelicula de la funcion es null.");
		}
		if( funcion.getExhibicion() == null) {
			errores.add("La Exhibicion de la funcion es null.");
		}
		if(errores.size() > 0) {
			throw new FuncionException(funcion, errores);
		}
		
	}
}
