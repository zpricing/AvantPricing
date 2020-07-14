package cl.zpricing.avant.servicios;

import java.util.List;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Sala;
/**
 * 
 * <b>DAO para el manejo de salas</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 18-02-2009 Daniel Estevez Garay: version inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public interface SalaDao {

	/**
	 * Obtiene una sala desde la capa de datos.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 17/12/2008 Daniel Estevez Garay: Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param id identificador de la sala 
	 * @return sala
	 * @since 1.0
	 */
    public Sala obtenerSala(int id);

    /**
	 * Agrega una sala a la capa de datos.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 17/12/2008 Daniel Estevez Garay: Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param sala sala a agregar
	 * @since 1.0
	 */
    public void agregarSala(Sala sala);

    /**
	 * Elimina una sala de la capa de datos
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 17/12/2008 Daniel Estevez Garay: Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param sala sala a eliminar
	 * @since 1.0
	 */
    public boolean eliminarSala(int id);

    /**
	 * Actualiza una sala en la capa de datos
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 17/12/2008 Daniel Estevez Garay: Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param sala sala a actualizar
	 * @since 1.0
	 */
    public void actualizarSala(Sala sala);

	/**
	 * 
	 * Obtiene todas las salas existentes en la capa de datos.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 MARIO: Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return lista de salas
	 * @since 1.0
	 */
	public List<Sala> obtenerTodas();

	/**
	 * 
	 * Obtiene todas las salas de un complejo.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 18-02-2009 MARIO: Version Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param complejo complejo por el cual se consulta
	 * @return lista de salas del complejo
	 * @since 1.0
	 */
	public List<Sala> obtenerTodasByComplejo(Complejo complejo);
	
	public Sala obtenerPorIdExterno(Complejo complejo, String idExterno);
}


