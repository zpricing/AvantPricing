package cl.zpricing.avant.servicios;

import java.util.ArrayList;
import java.util.List;

import cl.zpricing.avant.model.Area;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.model.Sala;

/**
 * Acceso a los objetos de tipo Mascara
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 01-02-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public interface MascaraDao {
	
	/**
	 * @param sala
	 * @return lista de mascaras para la sala dada
	 */
	public List<Mascara> obtenerMascarasSala(Sala sala);
	
	public List<Mascara> obtenerMascaras(Complejo complejo);
	
	/**
	 * Retorna las mascaras activas de una Sala.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 20-02-2009 MARIO : Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param sala
	 * @return
	 * @since 1.0
	 */
	public List<Mascara> obtenerMascarasSalaActivos(Sala sala);
	
	/**
	 * @param sala
	 * @return lista por defecto de mascaras para la sala dada
	 */
	public List<Mascara> obtenerMascarasSalaDefault(Sala sala);
	
	/**
	 * @param sala
	 * @param mascaraId
	 * @return mascara para una sala y un id dado
	 */
	public Mascara obtenerMascaraSala(Sala sala, int mascaraId);
	
	/**
	 * @param complejo
	 * @return lista de areas pertenecientes a un complejo
	 */
	public ArrayList<Area> obtenerAreasComplejo(Complejo complejo);

	/**
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 MARIO : Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param mascara
	 * @since 1.0
	 */
	public void actualizarMascara(Mascara mascara);

	public Mascara obtenerMascara(int mascaraDefault);

	public Mascara obtenerMascaraCargadaParaUnaFuncion(Funcion funcionPredecida);
	
	public Mascara obtenerMascara(Sala sala, String mascaraDescripcion);
	
	public void agregar(Mascara mascara);
	
	public Mascara obtenerMascaraLastMinute(int complejoId);
}
