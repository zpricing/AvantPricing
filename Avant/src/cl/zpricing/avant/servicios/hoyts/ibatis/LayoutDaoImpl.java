package cl.zpricing.avant.servicios.hoyts.ibatis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.Area;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Sala;
import cl.zpricing.avant.model.loadmanager.Layout;
import cl.zpricing.avant.model.loadmanager.LayoutArea;
import cl.zpricing.avant.model.loadmanager.LayoutPorCategoria;
import cl.zpricing.avant.servicios.hoyts.LayoutDao;


public class LayoutDaoImpl extends SqlMapClientDaoSupport implements LayoutDao {
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public Layout obtenerLayout(int idLayout) {
		return (Layout) getSqlMapClientTemplate().queryForObject("obtenerLayoutPorId", idLayout);
	}
	
	@Override
	public Layout obtenerLayout(Sala sala, int anticipacion) {
		log.debug("Iniciando obtenerLayout");
		log.debug("  Sala : [" + sala.getId() + "]");
		log.debug("  Anticipacion : [" + anticipacion + "]");
		Map<String, String> params = new HashMap<String, String>();
		params.put("sala_id", Integer.toString(sala.getId()));
		params.put("anticipacion", Integer.toString(anticipacion));
		return (Layout) getSqlMapClientTemplate().queryForObject("obtenerLayoutPorAnticipacion", params);
	}
	
	@Override
	public Layout obtenerLayout(Sala sala, List<Area> areas) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("sala_id", Integer.toString(sala.getId()));
		List<Layout> layouts = (List<Layout>)getSqlMapClientTemplate().queryForList("obtenerLayoutsSala", params);
		Iterator<Layout> iterLayout = layouts.iterator();
		
		while(iterLayout.hasNext()) {
			Layout layout = iterLayout.next();
			Iterator<LayoutArea> iterAreasLayout = layout.getAreas().iterator();
			int contador = 0;
			while (iterAreasLayout.hasNext()) {
				LayoutArea areaLayout = iterAreasLayout.next();
				
				Iterator<Area> iterMascarasAreas = areas.iterator();
				while (iterMascarasAreas.hasNext()) {
					Area area = iterMascarasAreas.next();
					
					if (areaLayout.getAreaId() == area.getId()) {
						contador++;
					}
				}
			}
			
			if (layout.getAreas().size() == contador) {
				return layout;
			}
		}
		return null;
	}

	@Override
	public List<Layout> obtenerTodos() {
		return (List<Layout>) getSqlMapClientTemplate().queryForList("obtenerTodosLayouts");
	}

	@Override
	public List<Layout> obtenerTodos(Complejo complejo) {
		return (List<Layout>) getSqlMapClientTemplate().queryForList("obtenerTodosLayoutsPorComplejo", complejo.getId());
	}
	
	@Override
	public List<Layout> obtenerTodos(Sala sala) {
		return (List<Layout>) getSqlMapClientTemplate().queryForList("obtenerTodosLayoutsPorSala", sala.getId());
	}

	@Override
	public Area obtenerAreaFisica(Sala sala, Integer areaBytNum) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sala_id", sala.getId());
		params.put("area_bytNum", areaBytNum);
		return (Area) getSqlMapClientTemplate().queryForList("obtenerAreaFisica", params);
	}

	@Override
	public List<LayoutPorCategoria> obtenerTodosAgrupadosPorCategoria(Sala sala) {
		return (List<LayoutPorCategoria>) getSqlMapClientTemplate().queryForList("obtenerTodosLayoutsPorSalaPorCategoria", sala.getId());
	}

	
}
