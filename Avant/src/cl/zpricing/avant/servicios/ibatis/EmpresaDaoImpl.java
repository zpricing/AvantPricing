/**
 * 
 */
package cl.zpricing.avant.servicios.ibatis;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Empresa;
import cl.zpricing.avant.model.RptEmpresa;

/**
 * Clase implementacion del Dao Empresa
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 23-12-2008 Julio Olivares Alarcon: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class EmpresaDaoImpl extends SqlMapClientDaoSupport implements cl.zpricing.avant.servicios.EmpresaDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());


	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.EmpresaDao#actualizarEmpresa(cl.zpricing.revman.model.Empresa)
	 */
	@Override
	public void actualizarEmpresa(Empresa empresa) {
		if (empresa.getRptEmpresa() == null ) {
			RptEmpresa rptEmpresa = new RptEmpresa();
			rptEmpresa.setRpt_empresa_id(0);
			empresa.setRptEmpresa(rptEmpresa);
		}
		getSqlMapClientTemplate().update("updateEmpresa",empresa);
	}


	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.EmpresaDao#agregarEmpresa(cl.zpricing.revman.model.Empresa)
	 */
	@Override
	public void agregarEmpresa(Empresa empresa) {
		if (empresa.getRptEmpresa() == null ) {
			RptEmpresa rptEmpresa = new RptEmpresa();
			rptEmpresa.setRpt_empresa_id(0);
			empresa.setRptEmpresa(rptEmpresa);
		}
		getSqlMapClientTemplate().insert("insertEmpresa", empresa);	
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.EmpresaDao#eliminarEmpresa(cl.zpricing.revman.model.Empresa)
	 */
	@Override
	public boolean eliminarEmpresa(Empresa empresa) {
		try{
		getSqlMapClientTemplate().insert("deleteEmpresa", empresa.getId());
		}catch(Exception e)
		{
		return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.EmpresaDao#obtenerEmpresa(int)
	 */
	@Override
	public Empresa obtenerEmpresa(int id) {
		Empresa empresa = (Empresa)getSqlMapClientTemplate().queryForObject("getEmpresa",id);
		log.debug("existe RptEmpresa: " +  empresa.getRptEmpresa());
		return empresa;
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.EmpresaDao#empresasTodas()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> empresasTodas() {
		return (List<Empresa>) getSqlMapClientTemplate().queryForList("empresasTodas");
	}
	
	@SuppressWarnings("unchecked")
	public List<RptEmpresa> obtenerRptEmpresaTodos() {
		return (List<RptEmpresa>) getSqlMapClientTemplate().queryForList("obtenerRptEmpresaTodos");
	}
	
	public RptEmpresa obtenerRptEmpresa(Integer id) {
		if (id == null) {
			return null;
		}
		return (RptEmpresa) getSqlMapClientTemplate().queryForObject("obtenerRptEmpresa", id);
	}

	public Integer obtenerRptEmpresaIdParaEmpresa(Integer empresaId) {

		return (Integer) getSqlMapClientTemplate().queryForObject("obtenerRptEmpresaIdParaEmpresa", empresaId);
	}


	@Override
	public void actualizarNombreRptEmpresa(RptEmpresa rptEmpresa) {
		getSqlMapClientTemplate().update("actualizarNombreRptEmpresa", rptEmpresa);
	}


	@Override
	public void eliminarRptEmpresa(RptEmpresa rptEmpresa) {
		getSqlMapClientTemplate().delete("eliminarRptEmpresa", rptEmpresa);
		
	}
}
