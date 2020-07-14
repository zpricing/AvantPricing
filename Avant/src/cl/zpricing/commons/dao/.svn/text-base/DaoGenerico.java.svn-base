package cl.zpricing.commons.dao;

import java.util.ArrayList;

import cl.zpricing.commons.exceptions.DaoGenericoException;

/**
 * <p>Clase que contiene las interfaces para un modelo DAO generico.</p>
 *
 * <p>
 * <b>Registro de versiones:</b>
 * <ul>
 * 	   <li>1.0 (22-07-2009: Nicolás Dujovne W.): versión inicial.</li>
 * 	   <li>1.0 (17-02-2010: Daniel Estévez G.) : agregados métodos condicionales</li>
 * </ul>
 * </p>
 * <p>
 *   <b>Todos los derechos reservados por ZhetaPricing.</b>
 * </p>
 */
public interface DaoGenerico {
	public Object obtener(Object object) throws DaoGenericoException;
	@SuppressWarnings("unchecked")
	public ArrayList obtenerTodos(Object object) throws DaoGenericoException;
	public Object insertar(Object object) throws DaoGenericoException;
	@SuppressWarnings("unchecked")
	public ArrayList obtenerListaPorCampo(Object object, String campo) throws DaoGenericoException;
	
	public Object actualizar(Object object) throws DaoGenericoException;
	/**
	 * 
	 * Obtiene objetos o1 en funcion de objetos o2 con id de objeto definido
	 *
	 * Registro de versiones:
	 * <ul>
	 *   <li>1.0 18-08-2009 Daniel Estévez Garay: versión inicial.</li>
	 * </ul>
	 *
	 * @param o1 tipo objeto a seleccionar
	 * @param o2 objeto en funcion de
	 * @return
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public ArrayList obtenerEnFuncionDe(Object o1, Object o2) throws DaoGenericoException;
	/**
	 * 
	 * Elimina un objeto de la capa de datos
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 (22-02-2010 Daniel Estévez Garay): Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param o objeto a eliminar en la capa de datos
	 * @since 1.0
	 */
	public void eliminar(Object o) throws DaoGenericoException;
	/**
	 * 
	 * Obtiene un tipo de objeto asociado a un índice desde la capa de datos 
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 (12-04-2010 Daniel Estévez Garay): versión inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param object tipo de objeto
	 * @param i indice del objeto a obtener
	 * @return objeto tipo del indice asociado
	 * @throws DaoGenericoException
	 * @since 1.0
	 */
	public Object obtener(Object object, int i) throws DaoGenericoException;
}
