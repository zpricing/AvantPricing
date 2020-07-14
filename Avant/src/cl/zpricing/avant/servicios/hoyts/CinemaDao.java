package cl.zpricing.avant.servicios.hoyts;

import java.util.List;

import cl.zpricing.avant.model.Sala;
import cl.zpricing.avant.model.loadmanager.AreaCount;

public interface CinemaDao extends cl.zpricing.avant.servicios.CinemaDao{
	public String consultaNombreCinema();
	
	public List<AreaCount> consultaCapacidadFuncion(String idFuncion);
	
	public Integer consultaSalaFuncion(String idFuncion);
	
	public Integer consultaOrdenesEnProceso(String idFuncion);
	
	public String consultaEstadoFuncion(String idFuncion);
	
	public Integer consultaLayoutSala(Sala sala);
}
