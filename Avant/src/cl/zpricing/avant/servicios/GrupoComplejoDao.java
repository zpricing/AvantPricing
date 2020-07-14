package cl.zpricing.avant.servicios;

import java.util.List;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Empresa;
import cl.zpricing.avant.model.GrupoComplejo;

public interface GrupoComplejoDao {
	
	public GrupoComplejo obtenerGrupoComplejo(int id);
	
	public List<GrupoComplejo> obtenerTodosByEmpresa(Empresa empresa);
	
	public List<GrupoComplejo> obtenerHijos(GrupoComplejo padre);
	
	public List<GrupoComplejo> obtenerTodos();
	
	public void agregarGrupoComplejo(GrupoComplejo grupo);
	
	public void actualizarGrupoComplejo(GrupoComplejo grupo);
	
	public void eliminarGrupoComplejo(GrupoComplejo grupo);
	
	public List<Complejo> obtenerComplejosByGrupo(GrupoComplejo grupo);
}
