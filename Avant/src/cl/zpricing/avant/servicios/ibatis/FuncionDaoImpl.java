package cl.zpricing.avant.servicios.ibatis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.etl.model.Session;
import cl.zpricing.avant.model.Area;
import cl.zpricing.avant.model.Asistencia;
import cl.zpricing.avant.model.AsistenciaDiaria;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.FuncionArea;
import cl.zpricing.avant.model.FuncionAreaProxy;
import cl.zpricing.avant.model.LastMinuteSession;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.Precio;
import cl.zpricing.avant.model.Sala;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.util.Util;
import cl.zpricing.commons.utils.DateUtils;
import cl.zpricing.commons.utils.ErroresUtils;

/**
 * 
 * <b>Implementacion DAO sobre iBatis para el manejo de funciones</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 17/12/2008 Daniel Estévez Garay: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class FuncionDaoImpl extends SqlMapClientDaoSupport implements FuncionDao {
	/**
	 * Impresión de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.FuncionDao#actualizarAsistencia(cl.zpricing
	 * .revman.model.Funcion)
	 */
	public void actualizarAsistencia(Funcion Funcion) {
		getSqlMapClientTemplate().delete("eliminarAsistencias", Funcion);
		Iterator<Asistencia> itAs = Funcion.getAsistenciasDeFuncion().iterator();
		while (itAs.hasNext())
			getSqlMapClientTemplate().insert("agregarAsistencia", itAs.next());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.FuncionDao#actualizarFuncion(cl.zpricing
	 * .revman.model.Funcion)
	 */
	public void actualizarFuncion(Funcion funcion) {
		if (funcion.getMascara() == null) {
			getSqlMapClientTemplate().update("actualizarFuncionSinMascara", funcion);
		}
		else {
			getSqlMapClientTemplate().update("actualizarFuncion", funcion);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.FuncionDao#agregarFuncion(cl.zpricing.revman
	 * .model.Funcion)
	 */
	public void agregarFuncion(Funcion Funcion) {
		getSqlMapClientTemplate().insert("agregarFuncion", Funcion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.FuncionDao#eliminarFuncion(cl.zpricing.revman
	 * .model.Funcion)
	 */
	public void eliminarFuncion(Funcion Funcion) {
		getSqlMapClientTemplate().delete("eliminarFuncion", Funcion);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.FuncionDao#obtenerFuncion(int)
	 */
	public Funcion obtenerFuncion(int id) {
		return (Funcion) getSqlMapClientTemplate().queryForObject("obtenerFuncion", id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.FuncionDao#obtenerListaFunciones(java.util
	 * .GregorianCalendar, java.util.GregorianCalendar,
	 * cl.zpricing.revman.model.Pelicula)
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Funcion> obtenerListaFunciones(GregorianCalendar fecha_inicio, GregorianCalendar fecha_fin, Pelicula pelicula) {

		Map<String, Object> param = new HashMap<String, Object>(3);
		param.put("fecha_inicio", String.format("%1$tF %1$tT", fecha_inicio.getTime()));
		param.put("fecha_fin", String.format("%1$tF %1$tT", fecha_fin.getTime()));
		param.put("pelicula", pelicula);

		return (ArrayList<Funcion>) getSqlMapClientTemplate().queryForList("obtenerFuncionesByPeliculaDia", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecl.zpricing.revman.servicios.FuncionDao#obtenerFunciones(java.util.
	 * GregorianCalendar, java.util.GregorianCalendar)
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Funcion> obtenerFunciones(GregorianCalendar fecha_inicio, GregorianCalendar fecha_termino) {

		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("fecha_inicio", String.format("%1$tF %1$tT", fecha_inicio.getTime()));
		param.put("fecha_termino", String.format("%1$tF %1$tT", fecha_termino.getTime()));

		return (ArrayList<Funcion>) getSqlMapClientTemplate().queryForList("obtenerFunciones", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.FuncionDao#eliminarAsistencia(cl.zpricing
	 * .revman.model.Asistencia)
	 */
	@Override
	public void eliminarAsistencia(Asistencia asistencia) {
		getSqlMapClientTemplate().delete("eliminarAsistencia", asistencia);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.FuncionDao#obtenerAsistencia(int)
	 */
	@Override
	public Asistencia obtenerAsistencia(int id) {
		return (Asistencia) getSqlMapClientTemplate().queryForObject("obtenerAsistencia", id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.FuncionDao#obtenerPrimeraFuncion(cl.zpricing
	 * .revman.model.Pelicula, cl.zpricing.revman.model.Complejo, int)
	 */
	public Funcion obtenerPrimeraFuncion(Pelicula pelicula, Complejo complejo, int dia) {
		Map<String, Object> param = new HashMap<String, Object>(3);
		param.put("pelicula", pelicula);
		param.put("complejo", complejo);
		param.put("dia", dia);

		return (Funcion) getSqlMapClientTemplate().queryForObject("obtenerPrimeraFuncion", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.FuncionDao#obtenerListaFuncionesComplejo
	 * (java.util.GregorianCalendar, java.util.GregorianCalendar,
	 * cl.zpricing.revman.model.Pelicula, cl.zpricing.revman.model.Complejo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Funcion> obtenerListaFuncionesComplejo(
			GregorianCalendar fecha_inicio, GregorianCalendar fecha_fin, Pelicula pelicula, Complejo complejo) {
		Map<String, Object> param = new HashMap<String, Object>(4);
		param.put("fecha_inicio", String.format("%1$tF %1$tT", fecha_inicio.getTime()));
		param.put("fecha_fin", String.format("%1$tF %1$tT", fecha_fin.getTime()));
		param.put("pelicula", pelicula);
		param.put("complejo", complejo);

		return (ArrayList<Funcion>) getSqlMapClientTemplate().queryForList("obtenerFuncionesByPeliculaDiaComplejo", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.FuncionDao#Propagar(cl.zpricing.revman.model
	 * .Complejo, cl.zpricing.revman.model.Pelicula,
	 * java.util.GregorianCalendar, java.util.GregorianCalendar,
	 * java.util.GregorianCalendar)
	 */
	@Override
	public void Propagar(
			Complejo complejo, Pelicula pelicula, GregorianCalendar imagen, GregorianCalendar fecha_inicio, GregorianCalendar fecha_fin) {
		GregorianCalendar inicio = new GregorianCalendar();
		GregorianCalendar fin = new GregorianCalendar();

		inicio.setTime(imagen.getTime());
		fin.setTime(imagen.getTime());
		inicio.set(Calendar.HOUR_OF_DAY, 0);
		inicio.set(Calendar.MINUTE, 0);
		fin.add(Calendar.DAY_OF_MONTH, 1);
		fin.set(Calendar.HOUR_OF_DAY, 3);
		fin.set(Calendar.MINUTE, 0);
		ArrayList<Funcion> funciones = obtenerListaFuncionesComplejo(inicio, fin, pelicula, complejo);
		Iterator<Funcion> itfun = funciones.iterator();
		while (itfun.hasNext()) {
			Funcion funcion = itfun.next();
			GregorianCalendar calendar = new GregorianCalendar();
			GregorianCalendar calendarfun = new GregorianCalendar();
			calendar.setTime(fecha_inicio.getTime());
			calendarfun.setTime(funcion.getFecha());
			while (fecha_fin.after(calendar)) {
				calendar.set(Calendar.HOUR_OF_DAY, calendarfun.get(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendarfun.get(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendarfun.get(Calendar.MINUTE));
				funcion.setFecha(calendar.getTime());
				agregarFuncion(funcion);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.FuncionDao#obtenerAsistenciasByFuncion(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Asistencia> obtenerAsistenciasByFuncion(int funcion_id) {
		return (ArrayList<Asistencia>) getSqlMapClientTemplate().queryForList("obtenerAsistenciasFuncion", funcion_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.FuncionDao#obtenerFuncionesComplejo(java
	 * .util.GregorianCalendar, java.util.GregorianCalendar,
	 * cl.zpricing.revman.model.Complejo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Funcion> obtenerFuncionesComplejo(GregorianCalendar fecha_inicio, GregorianCalendar fecha_termino, Complejo complejo) {
		Map<String, Object> param = new HashMap<String, Object>(3);
		param.put("fecha_inicio", String.format("%1$tF %1$tT", fecha_inicio.getTime()));
		param.put("fecha_fin", String.format("%1$tF %1$tT", fecha_termino.getTime()));
		param.put("complejo", complejo);

		return (ArrayList<Funcion>) getSqlMapClientTemplate().queryForList("obtenerFuncionesComplejo", param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Funcion> obtenerFunciones(Complejo complejo, Pelicula pelicula, Date fecha) {
		Map<String, Object> param = new HashMap<String, Object>(3);
		param.put("fecha", String.format("%1$tF %1$tT", fecha));
		param.put("pelicula_id", pelicula.getId());
		param.put("complejo_id", complejo.getId());
		
		log.debug("  fecha : " + param.get("fecha"));
		log.debug("  pelicula : " + param.get("pelicula_id"));
		log.debug("  complejo : " + param.get("complejo_id"));
		return (List<Funcion>) getSqlMapClientTemplate().queryForList("obtenerFuncionesPorPelicula", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cl.zpricing.revman.servicios.FuncionDao#eliminarFuncionId(int)
	 */
	@Override
	public void eliminarFuncionId(int id) {
		getSqlMapClientTemplate().delete("eliminarFuncionId", id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecl.zpricing.revman.servicios.FuncionDao#
	 * obtenerAsistenciasHoraPeliculaComplejo(java.util.ArrayList,
	 * cl.zpricing.revman.model.Complejo, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public HashMap<Integer, Double> obtenerAsistenciasHoraPeliculaComplejo(
			ArrayList<Pelicula> peliculas, Complejo complejo, int dias, int dia_estreno) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("peliculas", peliculas);
		param.put("complejo", complejo);
		param.put("dias", dias);
		param.put("dia_estreno", dia_estreno);
		log.debug("llamando con parametros.. complejo " + complejo.getId() + " " + complejo.getNombre() + " dias " + dias
				+ " dia estreno " + dia_estreno);
		Iterator<Pelicula> it_pel = peliculas.iterator();

		while (it_pel.hasNext()) {
			Pelicula pel = it_pel.next();
			log.debug("Pelicula.. " + pel.getId() + " " + pel.getNombre());
		}
		return (HashMap<Integer, Double>) getSqlMapClientTemplate().queryForMap("obtenerAsistenciasHoraPeliculaComplejo", param, "hora",
			"asistencias");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.FuncionDao#obtenerVarianzasHoraComplejo(
	 * java.util.ArrayList, cl.zpricing.revman.model.Complejo, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public HashMap<Integer, Double> obtenerVarianzasHoraComplejo(
			ArrayList<Pelicula> peliculas, Complejo complejo, int dias, int dia_estreno) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("peliculas", peliculas);
		param.put("complejo", complejo);
		param.put("dias", dias);
		param.put("dia_estreno", dia_estreno);
		log.debug("llamando con parametros.. complejo " + complejo.getId() + " " + complejo.getNombre() + " dias " + dias
				+ " dia estreno " + dia_estreno);
		Iterator<Pelicula> it_pel = peliculas.iterator();

		while (it_pel.hasNext()) {
			Pelicula pel = it_pel.next();
			log.debug("Pelicula.. " + pel.getId() + " " + pel.getNombre());
		}
		return (HashMap<Integer, Double>) getSqlMapClientTemplate().queryForMap("obtenerVarianzasFuncionesHora", param, "hora",
			"varianza");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<AsistenciaDiaria> obtenerAsistenciaDiariaPorPelicula(int peliculaId) {
		ArrayList<AsistenciaDiaria> asistencias = (ArrayList<AsistenciaDiaria>) getSqlMapClientTemplate().queryForList(
			"obtenerAsistenciaDiariaPorPelicula", peliculaId);
		Iterator itAsistencias = asistencias.iterator();
		int indice = 0;
		GregorianCalendar fechaPrimeraFuncion = new GregorianCalendar();
		while (itAsistencias.hasNext()) {
			AsistenciaDiaria thisAsistenciaDiaria = (AsistenciaDiaria) itAsistencias.next();
			if (indice == 0) {
				fechaPrimeraFuncion.setTime(thisAsistenciaDiaria.getFecha());
				thisAsistenciaDiaria.setDiasDesdeEstreno(1);
				indice++;
			}
			GregorianCalendar thisAsistenciaDiariaFecha = new GregorianCalendar();
			thisAsistenciaDiariaFecha.setTime(thisAsistenciaDiaria.getFecha());
			// log.debug(thisAsistenciaDiariaFecha.get(Calendar.DAY_OF_WEEK) + ": " +  DateUtils.DIAS_DE_SEMANA[thisAsistenciaDiariaFecha.get(Calendar.DAY_OF_WEEK) - 1]);
			thisAsistenciaDiaria.setDiaDeLaSemana(DateUtils.DIAS_DE_SEMANA[thisAsistenciaDiariaFecha.get(Calendar.DAY_OF_WEEK) - 1]);
			// TODO: en DateUtils los dias de la semana están en castellano. Hay que i18n-arlo.
			thisAsistenciaDiaria.setDiasDesdeEstreno(1 + Util.diferenciaFechas(thisAsistenciaDiariaFecha, fechaPrimeraFuncion));
		}
		return asistencias;
	}
	
	@Override
	public List<Funcion> obtenerFunciones(Complejo complejo, Date fechaContable) {
		return obtenerFunciones(complejo, fechaContable, false);
	}

	@Override
	public List<Funcion> obtenerFunciones(Complejo complejo, Date fechaContable, boolean soloFuncionesNoCargadas) {
		Map<String, Object> param = new HashMap<String, Object>(3);
		param.put("fecha", String.format("%1$tF %1$tT", fechaContable));
		param.put("complejo", complejo);
		
		if (soloFuncionesNoCargadas) {
			return (List<Funcion>) getSqlMapClientTemplate().queryForList("obtenerFuncionesDiaContableNoCargadas", param);
		}
		return (List<Funcion>) getSqlMapClientTemplate().queryForList("obtenerFuncionesDiaContable", param);
	}
	
	@Override
	public List<Funcion> obtenerFuncionesParaForecast(Complejo complejo, Date fechaContable, boolean soloFuncionesNuevas) {
		log.debug("obtenerFuncionesParaForecast");
		log.debug("  complejo : " + complejo.getId());
		log.debug("  soloFuncionesNuevas: "+ soloFuncionesNuevas);
		
		String query = "obtenerFuncionesParaForecast";
		if(soloFuncionesNuevas) {
			query = "obtenerFuncionesNuevasParaForecast";
		}
		
		Map<String, Object> param = new HashMap<String, Object>(3);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fecha = sdf.format(fechaContable);
		param.put("fecha", fecha);
		param.put("complejo", complejo);
		
		return (List<Funcion>) getSqlMapClientTemplate().queryForList(query, param);
	}
	
	@Override
	public List<Funcion> obtenerFuncionesSinAnalizar(Complejo complejo, Date fechaContable) {
		log.debug("obtenerFuncionesSinAnalizar");
		log.debug("  complejo : " + complejo.getId());
		Map<String, Object> param = new HashMap<String, Object>(3);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fecha = sdf.format(fechaContable);
		param.put("fecha", fecha);
		param.put("fecha_ultima_carga_asistencias", complejo.getUltimaCargaCompleta());
		param.put("complejo", complejo);

		return (List<Funcion>) getSqlMapClientTemplate().queryForList("obtenerFuncionesSinAnalizar", param);
	}
	
	@Override
	public Map<String,Integer> ObtenerSemanaExhibicion(Funcion funcion) {
		log.debug("ObtenerSemanaExhibicion");
		log.debug("  funcion : " + funcion.getId());
		Map<String, Object> param = new HashMap<String, Object>(3);
		param.put("fecha_estreno", funcion.getPeliculaAsociada().getFechaEstreno());
		param.put("fecha", funcion.getFechaContable());
		param.put("id_pelicula", funcion.getPeliculaAsociada().getId());
		param.put("id_complejo", funcion.getSala().getComplejoAlCualPertenece().getId());
		
		return (Map<String,Integer>) getSqlMapClientTemplate().queryForObject("ObtenerSemanaExhibicion", param);
	}
	
	public void actualizaFuncionArea(Funcion funcion, FuncionArea funcionArea) {
		log.debug("actualizaFuncionArea");
		log.debug("  funcion : " + funcion.getId());
		log.debug("  funcionArea : " + funcionArea.getArea().getDescripcion());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("funcion_id", funcion.getId());
		map.put("area_id", funcionArea.getArea().getId());
		map.put("disponible", funcionArea.getCapacidadDisponible());
		map.put("total", funcionArea.getCapacidadTotal());
		
		FuncionArea funcionArea_temp = (FuncionArea) getSqlMapClientTemplate().queryForObject("obtenerFuncionArea", map);
		if (funcionArea_temp != null) {
			log.debug("Actualizando funcionArea");
			try {
				getSqlMapClientTemplate().update("actualizarFuncionArea", map);
				log.debug("funcionArea Actualizada");
			}
			catch (Exception e) {
				log.error(ErroresUtils.extraeStackTrace(e));
			}
		}
		else {
			log.debug("Agregando funcionArea");
			getSqlMapClientTemplate().insert("agregarFuncionArea", map);
		}
	}
	
	public boolean actualizarFuncionAreaCuposOcupados(Funcion funcion, Area area, Integer ocupados) {
		log.debug("Iniciando actualizarFuncionAreaCuposOcupados");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("funcion_id", funcion.getId());
		map.put("area_id", area.getId());
		map.put("ocupados", ocupados);
		int resultado = getSqlMapClientTemplate().update("actualizarFuncionAreaCuposOcupados", map);
		log.debug("  resultado : [" + resultado + "]");
		return resultado == 0 ? true : false;
	}
	
	public FuncionArea obtenerFuncionArea(Funcion funcion, Area area) {
		log.debug("obtenerFuncionArea");
		log.debug("funcion_id : [" + funcion.getId() + "]");
		log.debug("area_id : [" + area.getId() + "]");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("funcion_id", funcion.getId());
		map.put("area_id", area.getId());
		return (FuncionArea) getSqlMapClientTemplate().queryForObject("obtenerFuncionArea", map);
	}
	
	@Override
	public List<FuncionArea> obtenerFuncionesArea(Funcion funcion) {
		return (List<FuncionArea>) getSqlMapClientTemplate().queryForList("obtenerFuncionesArea", funcion.getId());
	}
	
	@Override
	public Precio obtenerPrecioFuncionArea(Funcion funcion, Area area) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("funcion_id", funcion.getId());
		map.put("area_id", area.getId());
		Precio precio = (Precio) getSqlMapClientTemplate().queryForObject("obtenerPrecioFuncionArea", map);
		log.debug("Resultado : " + precio.valorTotal());
		return precio;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Funcion> obtenerFunciones(Sala sala, Date fecha) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fecha", DateUtils.format_yyyyMMdd.format(fecha));
		param.put("sala_id", sala.getId());

		return (List<Funcion>) getSqlMapClientTemplate().queryForList("obtenerFuncionesSalaDia", param);
	}

	@Override
	public Funcion obtenerFuncionPorIdExterno(Complejo complejo, String idExterno) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complejo_id", complejo.getId());
		params.put("id_externo", idExterno);
		return (Funcion) getSqlMapClientTemplate().queryForObject("obtenerFuncionPorIdExterno", params);
	}

	@Override
	public List<FuncionArea> obtenerFuncionesAreaConTicketId(Funcion funcion) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("funcion_id", funcion.getId());
		params.put("complejo_id", funcion.getSala().getComplejoAlCualPertenece().getId());
		params.put("formato_id", funcion.getPeliculaAsociada().getFormatoId());
		
		log.debug("obtenerFuncionesAreaConTicketId");
		log.debug("  funcion_id : [" + funcion.getId() + "]");
		log.debug("  complejo_id : [" + funcion.getSala().getComplejoAlCualPertenece().getId() + "]");
		log.debug("  formato_id : [" + funcion.getPeliculaAsociada().getFormatoId() + "]");
		
		return (List<FuncionArea>) getSqlMapClientTemplate().queryForList("obtenerFuncionesAreaConTicketId", params);
	}

	@Override
	public int copiaMascaraAFuncionesFuturas(Funcion funcion) {
		if (funcion.getMascara() != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fecha_contable", funcion.getFechaContable());
			params.put("mascara_id", funcion.getMascara().getId());
			params.put("sala_id", funcion.getSala().getId());
			params.put("pelicula_id", funcion.getPeliculaAsociada().getId());
			params.put("hora", DateUtils.format_HHmmss.format(funcion.getFecha()));
			
			log.debug("fecha_contable : [" + DateUtils.format_yyyyMMdd.format(funcion.getFechaContable()) + "]");
			log.debug("mascara : [" + funcion.getMascara().getId() + "]");
			log.debug("sala_id : [" + funcion.getSala().getId() + "]");
			log.debug("pelicula_id : [" + funcion.getPeliculaAsociada().getId() + "]");
			log.debug("hora : [" + DateUtils.format_HHmmss.format(funcion.getFecha()) + "]");
			
			return getSqlMapClientTemplate().update("copiaMascaraAFuncionesFuturas", params);
		}
		return -1;
	}

	@Override
	public void actualizaMascara(Funcion funcion) {
		
	}
	
	@Override
	public void actualizaMascaraYBloquear(int funcionId, int mascaraId) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("funcion_id", funcionId);
		params.put("mascara_id", mascaraId);
		getSqlMapClientTemplate().update("actualizarMascaraYBloquear", params);
	}
	
	@Override
	public void actualizarAsistenciaProyectada(Funcion funcion) {
		getSqlMapClientTemplate().update("actualizarAsistenciaProyectada", funcion);
	}
	
	@Override
	public void actualizarAsistenciaProyectadaSinMascara(Funcion funcion) {
		getSqlMapClientTemplate().update("actualizarAsistenciaProyectadaSinMascara", funcion);
	}
	
	@Override
	public void actualizarAnalizada(Funcion funcion) {
		getSqlMapClientTemplate().update("actualizarAnalizada", funcion);
	}
	
	@Override
	public void actualizarBloqueada(Funcion funcion){
		getSqlMapClientTemplate().update("actualizarBloqueada", funcion);
	}
	
	@Override
	public void actualizarFuncionesUpselling(int preTime, int postTime) {
		log.debug("actualizarFuncionesUpselling...");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pre_time", preTime);
		params.put("post_time", postTime);
		
		getSqlMapClientTemplate().update("cleanFuncionesUpselling");
		getSqlMapClientTemplate().update("actualizarFuncionesUpselling", params);
	}

	@Override
	public void actualizarCuposUpselling() {
		log.debug("actualizarFuncionesUpselling...");
		getSqlMapClientTemplate().update("actualizarCuposUpselling");
	}
	
	@Override
	public void actualizarFuncionesSecondSelling() {
		log.debug("actualizarFuncionesSecondSelling...");
		getSqlMapClientTemplate().delete("cleanSimilitudGrupoPeliculas");
		getSqlMapClientTemplate().delete("cleanPeliculasSecondSelling");
		getSqlMapClientTemplate().delete("cleanFuncionesSecondSelling");
		getSqlMapClientTemplate().delete("cleanFuncionesSecondSellingStaging");
		
		getSqlMapClientTemplate().update("actualizarSimilitudGrupoPeliculas");
		getSqlMapClientTemplate().update("actualizarPeliculasSecondSelling");
		getSqlMapClientTemplate().update("actualizarFuncionesSecondSellingStaging");
		getSqlMapClientTemplate().update("actualizarFuncionesSecondSelling");
	}
	
	@Override
	public void actualizarFuncionesSecondSellingClientSuggestions() {
		log.info("Iniciando actualizarFuncionesSecondSellingClientSuggestions");
		log.info("  cleanPreferenciasDeClientesDeFuncionesEnCartelera");
		getSqlMapClientTemplate().delete("cleanPreferenciasDeClientesDeFuncionesEnCartelera");
		log.info("  cleanSugerenciasPeliculasClientes");
		getSqlMapClientTemplate().delete("cleanSugerenciasPeliculasClientes");
		log.info("  cleanSugerenciasSecondSellingClientes");
		getSqlMapClientTemplate().delete("cleanSugerenciasSecondSellingClientes");
		log.info("  calcularPreferenciasDeClientesDeFuncionesEnCartelera");
		getSqlMapClientTemplate().update("calcularPreferenciasDeClientesDeFuncionesEnCartelera");
		log.info("  calcularSugerenciasPeliculasClientes");
		getSqlMapClientTemplate().update("calcularSugerenciasPeliculasClientes");
		log.info("  calcularSugerenciasSecondSellingClientes");
		getSqlMapClientTemplate().update("calcularSugerenciasSecondSellingClientes");
		log.info("Terminando  actualizarFuncionesSecondSellingClientSuggestions");
	}
	
	@Override
	public void actualizarFuncionesLastMinuteSelling(int maxFuncionesDiarias, int maxPorcentajeOcupacionProyectado, int minDiasCartelera) {
		log.debug("actualizarFuncionesLastMinuteSelling...");
		
		getSqlMapClientTemplate().delete("cleanLMSellingSessions");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("max_funciones_diarias", maxFuncionesDiarias);
		params.put("max_porcentaje_ocupacion_proyectado", maxPorcentajeOcupacionProyectado);
		params.put("min_dias_cartelera", minDiasCartelera);
		getSqlMapClientTemplate().insert("updateLMSellingSessions", params);
	}
	
	@Override
	public List<LastMinuteSession> obtenerFuncionesLastMinuteSelling(int complejoId) {
		log.debug("obtenerFuncionesLastMinuteSelling...");
		return getSqlMapClientTemplate().queryForList("getLastMinuteSellingSessions", complejoId);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Session> obtenerFuncionesArea() {
		return (List<Session>) getSqlMapClientTemplate().queryForList("obtenerFuncionesAreaFuturasLocal");
	}

	@Override
	public void setCuposCargados(int funcionId, boolean estado) {
		log.debug("setCuposCargados...");
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("funcion_id", funcionId);
			params.put("estado", estado ? 1 : 0);
			getSqlMapClientTemplate().update("setCuposCargados", params);
		}
		catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
		}
	}

	@Override
	public void setCuposCargadosFuncionesFuturas() {
		log.debug("setCuposCargadosFuncionesFuturas...");
		getSqlMapClientTemplate().update("setCuposCargadosFuncionesFuturas");
	}

	@Override
	public void actualizarSemanaExhibicion(Funcion funcion) {
		log.debug("actualizarSemanaExhibicion");
		log.debug("  funcion : " + funcion.getId());
		getSqlMapClientTemplate().update("actualizarSemanaExhibicion", funcion);
	}
}
