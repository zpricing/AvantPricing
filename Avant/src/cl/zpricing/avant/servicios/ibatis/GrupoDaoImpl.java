package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Grupo;
import cl.zpricing.avant.servicios.GrupoDao;

public class GrupoDaoImpl extends SqlMapClientDaoSupport implements GrupoDao {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	@Override
	public List<Grupo> obtenerTodosByComplejo(Complejo complejo) {
		log.debug("obtenerTodosByComplejo...");
		log.debug("complejo id: " + complejo.getId());
		List<Grupo> grupos = new ArrayList<Grupo>();
		grupos = (List<Grupo>) getSqlMapClientTemplate().queryForList("obtenerTodosByComplejo",complejo);
		log.debug("grupos: " + grupos.toString());
		return grupos;
	}
	
	@Override
	public List<Grupo> obtenerTodos() {
		log.debug("obtenerTodos...");
		List<Grupo> grupos = new ArrayList<Grupo>();
		grupos = (List<Grupo>) getSqlMapClientTemplate().queryForList("obtenerGrupos");
		log.debug("grupos: " + grupos.toString());
		return grupos;
	}

	@Override
	public void agregar(Grupo grupo) {
		int grupoId = (Integer) getSqlMapClientTemplate().insert("agregarGrupo", grupo);
		grupo.setId(grupoId);		
	}

}
