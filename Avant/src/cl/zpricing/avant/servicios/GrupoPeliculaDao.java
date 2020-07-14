package cl.zpricing.avant.servicios;

import java.util.List;

import cl.zpricing.avant.model.GrupoPelicula;

public interface GrupoPeliculaDao {
	public void actualizar(GrupoPelicula grupoPelicula);
	
	public List<GrupoPelicula> obtenerTodos(int pagina, int includeWithLoadedData);
	
	public List<GrupoPelicula> obtenerTodosConNombreOriginalSinDatos();
	
	public List<GrupoPelicula> obtenerTodosSinNombreOriginal(int pagina);
	
	public List<GrupoPelicula> obtenerEnCartelera(int pagina, int includeWithLoadedData);
	
	public List<GrupoPelicula> obtenerEnCarteleraSinNombreOriginal(int pagina);
	
	public void agregaRelacionCategoria(GrupoPelicula grupoPelicula, String externalSourceCode);
	
}
