package cl.zpricing.avant.negocio.datasource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import cl.zpricing.avant.alerts.ProcessAlert;
import cl.zpricing.avant.model.GrupoPelicula;
import cl.zpricing.avant.negocio.MovieDataSource;
import cl.zpricing.avant.negocio.sincronizador.LogProcesosManager;
import cl.zpricing.avant.servicios.CategoriaDao;
import cl.zpricing.avant.servicios.GrupoPeliculaDao;
import cl.zpricing.commons.utils.ErroresUtils;

public class OpenMovieDatabaseImpl extends MovieDataSource {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private GrupoPeliculaDao grupoPeliculaDao;
	
	@Autowired
	private CategoriaDao categoriaDao;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		log.info("************* EJECUTANDO RUN *************");
		this.logProcesos = LogProcesosManager.inicioProceso(this.getCodigo());
		this.iniciado = true;
		
		try {
			List<GrupoPelicula> grupoPeliculas = grupoPeliculaDao.obtenerTodosConNombreOriginalSinDatos();
			StringBuffer alerta = new StringBuffer();
			
			for (GrupoPelicula grupoPelicula : grupoPeliculas) {
				log.debug("Extrayendo informacion de pelicula : " + grupoPelicula.getDescripcion());
				log.debug("  Id externo : " + grupoPelicula.getExternalSourceId());
				log.debug("  Nombre original : " + grupoPelicula.getNombreOriginal());
				
				String uri = "http://www.omdbapi.com/?t=" + grupoPelicula.getNombreOriginal();
				if (grupoPelicula.getExternalSourceId() != null && !grupoPelicula.getExternalSourceId().isEmpty() ) {
					uri = "http://www.omdbapi.com/?i=" + grupoPelicula.getExternalSourceId();
				}
				
				String response = restTemplate.getForObject(uri, String.class);
				
				ObjectMapper mapper = new ObjectMapper();
				Map<String, String> movieData = mapper.readValue(response, Map.class);
				
				if (movieData.get("Response").equalsIgnoreCase("True")) {
					String genresAsCommaSeparatedString = movieData.get("Genre");
					String id = movieData.get("imdbID");
					String title = movieData.get("Title");
					
					grupoPelicula.setExternalSourceId(id);
					grupoPelicula.setNombreOriginal(title);
					
					StringTokenizer tokenizer = new StringTokenizer(genresAsCommaSeparatedString, ", ");
					
					while(tokenizer.hasMoreElements()) {
						String categoriaExternalCode = tokenizer.nextToken();
						Integer categoriaId = categoriaDao.agregarCategoriaSiNoExiste(categoriaExternalCode);
						
						log.debug("Nueva categoria ID : " + categoriaId);
						
						if (categoriaId != null) {
							alerta.append("Se agrego nueva categoria : [" + categoriaExternalCode + "]<br>");
						}
						
						grupoPeliculaDao.agregaRelacionCategoria(grupoPelicula, categoriaExternalCode);
					}
					grupoPelicula.setExternalSourceDataLoaded(true);
					grupoPeliculaDao.actualizar(grupoPelicula);
				}
				else {
					String error = "Error extracting movie : [" + grupoPelicula.getDescripcion() + "] original name: [" + grupoPelicula.getNombreOriginal() + "] : " + movieData.get("Error");
					log.warn(error);
					alerta.append(error + "<br>");
				}
			}
			processAlertFactory.makeAlert(logProcesos.getNombreProceso(), alerta.toString());
			LogProcesosManager.finalizado(logProcesos.getNombreProceso());
		} 
		catch (JsonParseException e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
		} 
		catch (JsonMappingException e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
		} 
		catch (IOException e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
		}
		catch(Exception e) {
			log.error(ErroresUtils.extraeStackTrace(e));
			LogProcesosManager.cambioError(logProcesos.getNombreProceso(), ErroresUtils.extraeStackTrace(e), true);
		}
		finally {
			this.iniciado = false;
		}
	}

	public void setGrupoPeliculaDao(GrupoPeliculaDao grupoPeliculaDao) {
		this.grupoPeliculaDao = grupoPeliculaDao;
	}
	public void setCategoriaDao(CategoriaDao categoriaDao) {
		this.categoriaDao = categoriaDao;
	}
}
