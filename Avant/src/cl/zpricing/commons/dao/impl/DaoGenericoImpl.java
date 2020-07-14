package cl.zpricing.commons.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.commons.dao.DaoGenerico;
import cl.zpricing.commons.exceptions.DaoGenericoException;
import cl.zpricing.commons.utils.ErroresUtils;

/**
 * <p>
 * Implementaci�n de interfaz de modelo de DAO Generico utilizando Ibatis.
 * <br>
 * Cada m�todo asume un cierto nombre para la consulta SQL definida en los archivos de configuraci�n
 * de Ibatis, en base al nombre del objeto que se recibe por parametro.
 * </p>
 *
 * <p>
 * <b>Registro de versiones:</b>
 * <ul>
 * 	   <li>1.0 (22-07-2009: Nicol�s Dujovne W.): versi�n inicial.</li>
 * </ul>
 * </p>
 * <p>
 *   <b>Todos los derechos reservados por ZhetaPricing.</b>
 * </p>
 */
public class DaoGenericoImpl extends SqlMapClientDaoSupport implements DaoGenerico{

	/**
	 * Para la impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());	
	
	/**
	 * <p>M�todo para obtener un objeto desde el m�delo de datos. Se utiliza el m�todo getId del objeto para obtener
	 * el identificador.
	 * <br>
	 * <b>Nota:</b> debe existir en Ibatis el mapeo para "obtener + nombre_clase_objeto".
	 * </p>
	 * <p>
	 * <b>Registro de versiones:</b>
	 * <ul>
	 * 	   <li>1.0 (22-07-2009: Nicol�s Dujovne W.): versi�n inicial.</li>
	 * </ul>
	 * </p>
	 *
	 * @param object Objecto del tipo que se requiere rescatar, debe contener el identificador.
	 * @return Objeto obtenido desde la base de datos.
	 * @throws DaoGenericoException
	 */
	@Override
	public Object obtener(Object object) throws DaoGenericoException {
		Object result;
		try {
			result = (Object) object.getClass().getMethod("getId", null).invoke(object, null);
		} catch (Exception e) {
			log.debug(ErroresUtils.extraeStackTrace(e));
			throw new DaoGenericoException(e);
		}
		return getSqlMapClientTemplate().queryForObject("obtener" + object.getClass().getSimpleName(), result);
	}
	
	/**
	 * <p>M�todo para insertar un objeto en la base de datos mediante Ibatis.
	 * <br>
	 * <b>Nota:</b> debe existir en Ibatis el mapeo para "insertar + nombre_clase_objeto".
	 * </p>
	 * <p>
	 * <b>Registro de versiones:</b>
	 * <ul>
	 * 	   <li>1.0 (22-07-2009: Nicol�s Dujovne W.): versi�n inicial.</li>
	 * </ul>
	 * </p>
	 *
	 * @param object Objeto a insertar en la base de datos.
	 * @return El retorno depende de como este configurado el mapeo del objeto.
	 * @throws DaoGenericoException
	 */
	@Override
	public Object insertar(Object object) throws DaoGenericoException {
		Object objeto= getSqlMapClientTemplate().insert("insertar" + object.getClass().getSimpleName(), object);
		log.debug("objeto=["+objeto+"]");
		return objeto;
	}
	
	@Override
	public Object actualizar(Object object) throws DaoGenericoException {
		return getSqlMapClientTemplate().insert("actualizar" + object.getClass().getSimpleName(), object);
	}
	
	/**
	 * <p>M�todo para obtener todos los objetos de un mismo tipo de la base de datos.
	 * <br>
	 * <b>Nota: </b> debe existir en Ibatis el mapeo para "obtenerTodos + nombre_clase_objeto".
	 * </p>
	 * <p>
	 * <b>Registro de versiones:</b>
	 * <ul>
	 * 	   <li>1.0 (22-07-2009: Nicol�s Dujovne W.): versi�n inicial.</li>
	 * </ul>
	 * </p>
	 *
	 * @param object Objeto del cual se requieren todas las ocurrencias en la base de datos. Puede estar vacio.
	 * @return Lista con todos los objetos de tipo object.getClass de la base de datos.
	 * @throws DaoGenericoException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList obtenerTodos(Object object) throws DaoGenericoException {
		return (ArrayList) getSqlMapClientTemplate().queryForList("obtenerTodos" + object.getClass().getSimpleName());
	}

	/**
	 * <p>Metodo para obtener una lista de objetos segun el valor de uno de sus atributos.
	 * <br>
	 * <b>Nota: </b> debe existir en Ibatis el mapeo para "obtenerListado + nombre_clase_objeto + nombre_campo"
	 * </p>
	 * <p>
	 * <b>Registro de versiones:</b>
	 * <ul>
	 * 	   <li>1.0 (22-07-2009: Nicol�s Dujovne W.): versi�n inicial.</li>
	 * </ul>
	 * </p>
	 *
	 * @param object
	 * @param campo
	 * @return
	 * @throws DaoGenericoException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList obtenerListaPorCampo(Object object, String campo) throws DaoGenericoException {
		log.debug("Campo a utilizar: [" + campo + "]");
		Object result;
		String campoMetodo = campo.toLowerCase().substring(1);
		campoMetodo = campo.substring(0, 1).toUpperCase() + campoMetodo;
		
		log.debug("Metodo a invocar: [" + campoMetodo + "]");
		
		try {
			result = (Object) object.getClass().getMethod("get" + campoMetodo).invoke(object);
		} catch (Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			throw new DaoGenericoException(e);
		}
		
		log.debug("Resultado invocacion metodo: [" + result + "]");
		
		return (ArrayList) getSqlMapClientTemplate().queryForList("obtenerListado" + object.getClass().getSimpleName() + campoMetodo, result);
	}
	/*
	 * (non-Javadoc)
	 * @see cl.zpricing.commons.dao.DaoGenerico#obtenerEnFuncionDe(java.lang.Object, java.lang.Object, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList obtenerEnFuncionDe(Object o1, Object o2)  throws DaoGenericoException{
		try{
			Map<String,Object> param = new HashMap<String, Object>();
			param.put(o1.getClass().getSimpleName(), o1);
			param.put(o2.getClass().getSimpleName(), o2);
			return (ArrayList)getSqlMapClientTemplate().queryForList("obtenerTodos"+o1.getClass().getSimpleName()+o2.getClass().getSimpleName(),param);
		}catch(Exception e){
			log.debug(ErroresUtils.extraeStackTrace(e));
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.commons.dao.DaoGenerico#eliminar(java.lang.Object)
	 */
	@Override
	public void eliminar(Object o) throws DaoGenericoException{
		try{
			getSqlMapClientTemplate().delete("eliminar"+o.getClass().getSimpleName(), o);
		}catch(Exception e){
			log.debug(ErroresUtils.extraeStackTrace(e));				
		}		
	}
	/*
	 * (non-Javadoc)
	 * @see cl.zpricing.commons.dao.DaoGenerico#obtener(java.lang.Object, int)
	 */
	@Override
	public Object obtener(Object object, int i) throws DaoGenericoException {
		try {
			return getSqlMapClientTemplate().queryForObject("obtener" + object.getClass().getSimpleName(), i);
		} catch (Exception e) {
			log.debug(ErroresUtils.extraeStackTrace(e));
			throw new DaoGenericoException(e);
		}
		
	}
	
}
