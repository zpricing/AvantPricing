/**
 * 
 */
package cl.zpricing.avant.servicios;

import java.util.ArrayList;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.loadmanager.GrupoPlantillas;
import cl.zpricing.avant.model.loadmanager.Plantilla;

/**
 * <b>Manejo de los Objetos de tipo Plantilla y GrupoPlantillas</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 02-02-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public interface PlantillaDao {

	/**
	 * Obtiene todos los GrupoPlantillas que esten guardados.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 02-02-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return
	 * @since 1.0
	 */
	public ArrayList<GrupoPlantillas> obtenerTodosGrupoPlantillas();

	/**
	 * Retorna el grupo plantilla que corresponde para una funcion.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 03-02-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param funcion
	 * @return
	 * @since 1.0
	 */
	public GrupoPlantillas obtenerGrupoPlantillaFuncion(Funcion funcion);

	/**
	 * Obtiene los GrupoPlantillas correspondientes a un complejo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 05-02-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param complejo
	 * @return lista de GrupoPlantillas
	 * @since 1.0
	 */
	public ArrayList<GrupoPlantillas> obtenerGrupoPlantillasComplejo(
			Complejo complejo);

	/**
	 * Actualiza las plantillas dentro de un GrupoPlantillas.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 05-02-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param grupoPlantillas
	 * @since 1.0
	 */
	public void actualizarPlantillasGrupoPlantillas(
			GrupoPlantillas grupoPlantillas);

	/**
	 * Agrega a la plantilla la clase con id claseId asociada al area con id
	 * areaId.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 06-02-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param plantilla
	 * @param claseId
	 * @param areaId
	 * @since 1.0
	 */
	public void agregarClaseAreaPlantilla(Plantilla plantilla, int claseId,
			int areaId);

	/**
	 * Obtener el GrupoPlantilla dado un id.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 06-02-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param id
	 * @return
	 * @since 1.0
	 */
	public GrupoPlantillas obtenerGrupoPlantillas(int grupoId);

	/**
	 * Devuelve la plantilla de id plantillaId dentro de un GrupoPlantillas. Si
	 * no existe en el grupo entonces retorna null.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 06-02-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param grupo
	 * @param plantillaId
	 * @return
	 * @since 1.0
	 */
	public Plantilla obtenerPlantillaGrupoPlantillas(GrupoPlantillas grupo,
			int plantillaId);

	/**
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 06-02-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param plantillaId
	 * @param claseId
	 * @since 1.0
	 */
	public void borrarPlantillaClaseArea(int plantillaId, int claseId);

}
