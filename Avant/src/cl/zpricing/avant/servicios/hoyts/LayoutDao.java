package cl.zpricing.avant.servicios.hoyts;

import java.util.List;

import cl.zpricing.avant.model.Area;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Sala;
import cl.zpricing.avant.model.loadmanager.Layout;
import cl.zpricing.avant.model.loadmanager.LayoutPorCategoria;

public interface LayoutDao {
	public Layout obtenerLayout(int idLayout);
	
	public Layout obtenerLayout(Sala sala, int anticipacion);
	public Layout obtenerLayout(Sala sala, List<Area> areas);
	public List<Layout> obtenerTodos();
	public List<Layout> obtenerTodos(Complejo complejo);
	public List<Layout> obtenerTodos(Sala sala);
	
	public Area obtenerAreaFisica(Sala sala, Integer areaBytNum);
	
	public List<LayoutPorCategoria> obtenerTodosAgrupadosPorCategoria(Sala sala);
}
