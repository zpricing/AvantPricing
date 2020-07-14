package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Parametro;
import cl.zpricing.avant.servicios.ServiciosRevenueManager;

public class ServiciosRevenueManagerImpl extends SqlMapClientDaoSupport implements ServiciosRevenueManager{

	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.ServiciosRevenueManager#obtenerParametro(java.lang.String, java.lang.String)
	 */
	public Parametro obtenerParametro(String sistema, String subSistema){
		/*
		log.debug("Iniciando obtenerParametro...");
		log.debug("Sistema    : [" + sistema + "]");		
		log.debug("SubSistema : [" + subSistema + "]");
		*/
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("sistema", sistema);
		param.put("subSistema", subSistema);
		Object result = getSqlMapClientTemplate().queryForObject("obtenerParametro", param);
		/*log.debug("result : [" + result + "]");*/
		return (Parametro)result;
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.ServiciosRevenueManager#obtenerStringParametro(java.lang.String, java.lang.String)
	 */
	@Override
	public String obtenerStringParametro(String sistema, String subSistema) {
		Parametro reg = this.obtenerParametro(sistema, subSistema);
		log.debug("sistema = "+sistema+"\tsubsistema = "+subSistema);
		log.debug(reg==null?"reg = null":"reg = not null");
		return reg == null ? null : reg.getCodigo();
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.ServiciosRevenueManager#obtenerIntParametro(java.lang.String, java.lang.String)
	 */
	public int obtenerIntParametro(String sistema, String subSistema)
	throws Exception{
		Parametro reg = this.obtenerParametro(sistema, subSistema);
		
		int resultado = 0;
		try{
			resultado = Integer.parseInt(reg.getCodigo());
		}catch(Exception e){
			throw e;
		}
		return resultado;
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.ServiciosRevenueManager#obtenerDoubleParametro(java.lang.String, java.lang.String)
	 */
	public double obtenerDoubleParametro(String sistema, String subSistema)
	throws Exception{
		Parametro reg = this.obtenerParametro(sistema, subSistema);
		
		double resultado = 0;
		try{
			
			resultado = Double.parseDouble(reg.getCodigo());
		}catch(Exception e){
			throw e;
		}
		return resultado;
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.ServiciosRevenueManager#obtenerParametro(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Parametro> obtenerParametro(String sistema){
		ArrayList<Parametro> parametros = (ArrayList<Parametro>) getSqlMapClientTemplate().queryForList("obtenerParametrosSistema", sistema);
		return parametros;
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.ServiciosRevenueManager#obtenerSistemas()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<String> obtenerSistemas() {
		ArrayList<String> sistemas = (ArrayList<String>) getSqlMapClientTemplate().queryForList("obtenerSistemas");
		return sistemas;
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.ServiciosRevenueManager#actualizarParametro(cl.zpricing.revman.model.Parametro)
	 */
	@Override
	public void actualizarParametro(Parametro parametro) {
		log.debug("actualizarParametro...");
		log.debug("Sistema: " + parametro.getSistema());
		log.debug("SubSistema: " + parametro.getSubSistema());
		log.debug("codigo: " + parametro.getCodigo());
		getSqlMapClientTemplate().update("actualizarParametro", parametro);		
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.ServiciosRevenueManager#nuevoParametro(cl.zpricing.revman.model.Parametro)
	 */
	@Override
	public void nuevoParametro(Parametro parametro) {
		getSqlMapClientTemplate().insert("nuevoParametro", parametro);
	}
}
