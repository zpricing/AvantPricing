package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Empresa;
import cl.zpricing.avant.model.GrupoComplejo;
import cl.zpricing.avant.servicios.GrupoComplejoDao;

public class GrupoComplejoDaoImpl extends SqlMapClientDaoSupport implements GrupoComplejoDao{
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Override
	public GrupoComplejo obtenerGrupoComplejo(int id) {
		log.debug("obtenerGrupoComplejo...");
		log.debug("id: " + id);
		GrupoComplejo grupo = new GrupoComplejo();
		grupo = (GrupoComplejo) getSqlMapClientTemplate().queryForObject("obtenerGrupoComplejo", id);
		return grupo;
	}
	
	@Override
	public List<GrupoComplejo> obtenerTodosByEmpresa(Empresa empresa){
		log.debug("obtenerTodosByEmpresa...");
		log.debug("empresa id: " + empresa.getId());
		List<GrupoComplejo> grupos = new ArrayList<GrupoComplejo>();
		grupos = (List<GrupoComplejo>) getSqlMapClientTemplate().queryForList("obtenerTodosByEmpresa",empresa);
		log.debug("grupos: " + grupos.toString());
		return grupos;
	}
	
	@Override
	public List<GrupoComplejo> obtenerTodos(){
		log.debug("obtenerTodos...");
		List<GrupoComplejo> grupos = new ArrayList<GrupoComplejo>();
		grupos = (List<GrupoComplejo>) getSqlMapClientTemplate().queryForList("obtenerGruposComplejos");
		log.debug("grupos: " + grupos.toString());
		return grupos;
	}
	
	@Override
	public List<GrupoComplejo> obtenerHijos(GrupoComplejo padre) {
		log.debug("obtenerHijos...");
		log.debug("grupoComplejo padre id: " + padre.getId());
		List<GrupoComplejo> grupos = new ArrayList<GrupoComplejo>();
		grupos = (List<GrupoComplejo>) getSqlMapClientTemplate().queryForList("obtenerGruposByPadre", padre);
		log.debug("grupos: " + grupos.toString());
		Iterator<GrupoComplejo> iterGrupoComplejos = grupos.iterator();
		while (iterGrupoComplejos.hasNext()) {
			GrupoComplejo hijo = iterGrupoComplejos.next();
			hijo.setHijos((ArrayList<GrupoComplejo>) this.obtenerHijos(hijo));
			hijo.setComplejos((ArrayList<Complejo>) this.obtenerComplejosByGrupo(hijo));
		}
		return grupos;
	}
	
	@Override
	public void agregarGrupoComplejo(GrupoComplejo grupo) {
		log.debug("agregarGrupoComplejo...");
		log.debug("empresa id: " + grupo.getEmpresa().getId());
		log.debug("grupoComplejo padre id: " + grupo.getPadre().getId());
		getSqlMapClientTemplate().insert("agregarGrupoComplejo", grupo);
	}
	
	@Override
	public void actualizarGrupoComplejo(GrupoComplejo grupo) {
		log.debug("actualizarGrupoComplejo...");
		log.debug("grupoComplejo id: " + grupo.getId());
		log.debug("grupoComplejo descripcion: " + grupo.getDescripcion());
		getSqlMapClientTemplate().insert("actualizarGrupoComplejo", grupo);
	}
	
	@Override
	public void eliminarGrupoComplejo(GrupoComplejo grupo) {
		log.debug("eliminarGrupoComplejo...");
		log.debug("grupoComplejo id: " + grupo.getId());
		getSqlMapClientTemplate().delete("eliminarGrupoComplejo", grupo);
		List<GrupoComplejo> grupos = new ArrayList<GrupoComplejo>();
		grupos = (List<GrupoComplejo>) getSqlMapClientTemplate().queryForList("obtenerGruposByPadre", grupo);
		Iterator<GrupoComplejo> iterGrupoComplejos = grupos.iterator();
		while (iterGrupoComplejos.hasNext()) {
			GrupoComplejo hijo = iterGrupoComplejos.next();
			this.eliminarGrupoComplejo(hijo);
		}
	}
	
	@Override
	public List<Complejo> obtenerComplejosByGrupo(GrupoComplejo grupo){
		log.debug("obtenerComplejosByGrupo...");
		log.debug("grupoComplejo id: " + grupo.getId());
		List<Complejo> complejos = new ArrayList<Complejo>();
		complejos = (List<Complejo>) getSqlMapClientTemplate().queryForList("obtenerComplejosByGrupo", grupo);
		return complejos;
	}
}
