/**
 * 
 */
package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Area;
import cl.zpricing.avant.model.Clase;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.loadmanager.GrupoPlantillas;
import cl.zpricing.avant.model.loadmanager.Plantilla;
import cl.zpricing.avant.servicios.PlantillaDao;

/**
 * <b>Descripci�n de la Clase</b> Implementacion de PlantillaDao
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 02-02-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class PlantillaDaoImpl extends SqlMapClientDaoSupport implements
		PlantillaDao {

	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.PlantillaDao#obtenerTodosGrupoPlantillas()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<GrupoPlantillas> obtenerTodosGrupoPlantillas() {
		return (ArrayList<GrupoPlantillas>) getSqlMapClientTemplate()
				.queryForList("obtenerTodosGrupoPlantillas");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.PlantillaDao#obtenerGrupoPlantillaFuncion
	 * (cl.zpricing.revman.model.Funcion)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GrupoPlantillas obtenerGrupoPlantillaFuncion(Funcion funcion) {
		log.debug("OBTENERGRUPOPLANTILLAFUNCION...");
		log.debug("complejo id: "
				+ funcion.getSala().getComplejoAlCualPertenece().getId());
		ArrayList<GrupoPlantillas> grupoComplejo = (ArrayList<GrupoPlantillas>) getSqlMapClientTemplate()
				.queryForList("obtenerGrupoPlantillasComplejo",
						funcion.getSala().getComplejoAlCualPertenece());
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.setTime(funcion.getFecha());
		int diaSemana = fecha.get(Calendar.DAY_OF_WEEK);
		int hora = fecha.get(Calendar.HOUR_OF_DAY);
		log.debug("hora: " + hora);
		Iterator<GrupoPlantillas> iter = grupoComplejo.iterator();
		// log.debug("empezando iteracion...");
		while (iter.hasNext()) {
			GrupoPlantillas grupo = iter.next();
			// log.debug("timeSpan desc: " +
			// grupo.getTimeSpan().getDescripcion());
			if (grupo.getTimeSpan().isDayInTimeSpan(diaSemana)
					&& grupo.getTimeSpan().isHourInTimeSpan(hora)) {
				return grupo;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.PlantillaDao#obtenerGrupoPlantillasComplejo
	 * (cl.zpricing.revman.model.Complejo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<GrupoPlantillas> obtenerGrupoPlantillasComplejo(
			Complejo complejo) {

		return (ArrayList<GrupoPlantillas>) getSqlMapClientTemplate()
				.queryForList("obtenerGrupoPlantillasComplejo", complejo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.PlantillaDao#actualizarPlantillasGrupoPlantillas
	 * (cl.zpricing.revman.model.loadmanager.GrupoPlantillas)
	 */
	@Override
	public void actualizarPlantillasGrupoPlantillas(
			GrupoPlantillas grupoPlantillas) {
		Iterator<Plantilla> iter = grupoPlantillas.getPlantillas().iterator();
		while (iter.hasNext()) {
			Plantilla plantilla = iter.next();
			getSqlMapClientTemplate().update("actualizarPlantilla", plantilla);
			getSqlMapClientTemplate().delete("borrarTodosPlantillaClaseArea",
					plantilla);
			Iterator<Clase> iterClases = plantilla.getClases().iterator();
			int i = 0;
			while (iterClases.hasNext()) {
				Clase clase = iterClases.next();
				Area area = plantilla.getAreas().get(i);
				HashMap<String, Integer> map = new HashMap<String, Integer>(3);
				map.put("plantilla_id", plantilla.getId());
				map.put("clase_id", clase.getId());
				map.put("area_id", area.getId());
				getSqlMapClientTemplate().insert("ingresarPlantillaClaseArea",
						map);
				i++;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.PlantillaDao#agregarClaseAreaPlantilla(cl
	 * .zpricing.revman.model.loadmanager.Plantilla, int, int)
	 */
	@Override
	public void agregarClaseAreaPlantilla(Plantilla plantilla, int claseId,
			int areaId) {
		HashMap<String, Integer> map = new HashMap<String, Integer>(3);
		map.put("plantilla_id", plantilla.getId());
		map.put("clase_id", claseId);
		map.put("area_id", areaId);
		getSqlMapClientTemplate().insert("ingresarPlantillaClaseArea", map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.PlantillaDao#obtenerGrupoPlantillas(int)
	 */
	@Override
	public GrupoPlantillas obtenerGrupoPlantillas(int grupoId) {
		return (GrupoPlantillas) getSqlMapClientTemplate().queryForObject(
				"obtenerGrupoPlantillas", grupoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.PlantillaDao#obtenerPlantillaGrupoPlantillas
	 * (cl.zpricing.revman.model.loadmanager.GrupoPlantillas, int)
	 */
	@Override
	public Plantilla obtenerPlantillaGrupoPlantillas(GrupoPlantillas grupo,
			int plantillaId) {
		Iterator<Plantilla> iter = grupo.getPlantillas().iterator();
		while (iter.hasNext()) {
			Plantilla plantilla = iter.next();
			if (plantilla.getId() == plantillaId)
				return plantilla;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cl.zpricing.revman.servicios.PlantillaDao#borrarPlantillaClaseArea(int,
	 * int)
	 */
	@Override
	public void borrarPlantillaClaseArea(int plantillaId, int claseId) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("plantilla_id", plantillaId);
		map.put("clase_id", claseId);
		getSqlMapClientTemplate().delete("borrarPlantillaClaseArea", map);
	}

}
