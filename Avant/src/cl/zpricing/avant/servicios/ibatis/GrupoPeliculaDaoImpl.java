package cl.zpricing.avant.servicios.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import cl.zpricing.avant.model.GrupoPelicula;
import cl.zpricing.avant.servicios.GrupoPeliculaDao;

public class GrupoPeliculaDaoImpl extends SqlMapClientDaoSupport implements GrupoPeliculaDao {	
	private int maxResults = 15;
	
	@Override
	public void actualizar(GrupoPelicula grupoPelicula) {
		getSqlMapClientTemplate().update("actualizarGrupoPelicula", grupoPelicula);
	}
	
	@Override
	public List<GrupoPelicula> obtenerTodos(int pagina, int includeWithLoadedData) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("include_with_loaded_data", includeWithLoadedData);
		return (List<GrupoPelicula>) getSqlMapClientTemplate().queryForList("obtenerGruposDePelicula", params, (pagina-1) * maxResults, maxResults);
	}
	
	@Override
	public List<GrupoPelicula> obtenerTodosConNombreOriginalSinDatos() {
		return (List<GrupoPelicula>) getSqlMapClientTemplate().queryForList("obtenerGruposDePeliculaParaCargaDeInformacionExterna");
	}

	@Override
	public List<GrupoPelicula> obtenerTodosSinNombreOriginal(int pagina) {
		return (List<GrupoPelicula>) getSqlMapClientTemplate().queryForList("obtenerGruposDePeliculaSinNombreOriginal", (pagina-1) * maxResults, maxResults);
	}
	
	@Override
	public List<GrupoPelicula> obtenerEnCartelera(int pagina, int includeWithLoadedData) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("include_with_loaded_data", includeWithLoadedData);
		return (List<GrupoPelicula>) getSqlMapClientTemplate().queryForList("obtenerGruposDePeliculaEnCartelera", params, (pagina-1) * maxResults, maxResults);
	}
	
	@Override
	public List<GrupoPelicula> obtenerEnCarteleraSinNombreOriginal(int pagina) {
		return (List<GrupoPelicula>) getSqlMapClientTemplate().queryForList("obtenerGruposDePeliculaEnCarteleraSinNombreOriginal", (pagina-1) * maxResults, maxResults);
	}
	
	@Override
	public void agregaRelacionCategoria(GrupoPelicula grupoPelicula, String externalSourceCode) {
		Map<String, Object> param= new HashMap<String, Object>(2);
		param.put("grupo_pelicula_id", grupoPelicula.getId());
		param.put("categoria_external_source_code", externalSourceCode);
		getSqlMapClientTemplate().insert("agregaRelacionGrupoPeliculaCategoriaPorCodigoDeFuenteExterna", param);
	}

}
