package cl.zpricing.avant.servicios;

import java.util.List;

import cl.zpricing.avant.model.Area;
import cl.zpricing.avant.model.Distribuidor;
import cl.zpricing.avant.model.Grupo;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.model.MascaraAreaGrupo;
import cl.zpricing.avant.model.loadmanager.TimeSpan;

public interface AreaDao {
	public Area obtenerAreaPorIdExterno(String idExterno);
	
	public List<Area> obtenerAreasRevenueManagement();
	
	public List<MascaraAreaGrupo> obtenerMascarasAreaGrupos(Grupo grupo, Mascara mascara);
	
	public List<MascaraAreaGrupo> obtenerMascarasAreaGruposRevenueManagement(Grupo grupo, Mascara mascara);
	
	public List<Integer> obtenerAreaExcepcionDistribuidor(TimeSpan timeSpan, Distribuidor distribuidor);
	
	public MascaraAreaGrupo actualizarMascaraAreaGrupo(MascaraAreaGrupo mascaraAreaGrupo);
}
