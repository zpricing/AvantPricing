package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Publico;
/**
 * <b>Implementaci�n DAO sobre iBatis para manejo de tipos de p�blico</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 17-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class PublicoDaoImpl extends SqlMapClientDaoSupport implements cl.zpricing.avant.servicios.PublicoDao{

    /* (non-Javadoc)
     * @see cl.zpricing.revman.servicios.PublicoDao#obtenerPublico(int)
     */
    public Publico obtenerPublico(int id){
    	return (Publico) getSqlMapClientTemplate().queryForObject("obtenerPublico",	id);
    }

    /* (non-Javadoc)
     * @see cl.zpricing.revman.servicios.PublicoDao#agregarPublico(cl.zpricing.revman.model.Publico)
     */
    public void agregarPublico(Publico publico){
    	getSqlMapClientTemplate().insert("agregarPublico", publico);
    }

    /* (non-Javadoc)
     * @see cl.zpricing.revman.servicios.PublicoDao#eliminarPublico(cl.zpricing.revman.model.Publico)
     */
    public void eliminarPublico(Publico publico){
    	getSqlMapClientTemplate().delete("eliminarPublico", publico);
    }

    /* (non-Javadoc)
     * @see cl.zpricing.revman.servicios.PublicoDao#actualizarPublico(cl.zpricing.revman.model.Publico)
     */
    public void actualizarPublico(Publico publico){
    	getSqlMapClientTemplate().update("actualizarPublico", publico);
    }

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.PublicoDao#obtenerPublicos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Publico> obtenerPublicos()  {
		return (ArrayList<Publico>) getSqlMapClientTemplate().queryForList("obtenerPublicos");
	}
}
