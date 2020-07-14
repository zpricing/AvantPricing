package cl.zpricing.avant.servicios.ibatis;

import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.servicios.TrabajoDao;

public class TrabajoDaoImpl extends SqlMapClientDaoSupport implements TrabajoDao{

	@Override
	public String ejecutarTrabajo(String nombreTrabajo, int tiempoMaximo) {
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		String resultado = "";
		paramMap.put("resultado", resultado);
		paramMap.put("dato", nombreTrabajo);
		paramMap.put("tiempoMaximo", new Integer(tiempoMaximo));
		getSqlMapClientTemplate().queryForObject("ejecutarTrabajo", paramMap);
		return (String)paramMap.get("resultado");
	}
	
}