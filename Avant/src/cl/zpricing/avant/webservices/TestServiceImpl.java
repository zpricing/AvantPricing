package cl.zpricing.avant.webservices;


import javax.jws.WebService;

import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.servicios.PeliculaDao;

@WebService(endpointInterface = "cl.zpricing.revman.webservices.TestService", serviceName = "ZhetaPricingTestServices")
public class TestServiceImpl implements TestService {
	protected PeliculaDao peliculaDao;

	@Override
	public TestResponse getHolaMundo(Integer idPelicula) {
		Pelicula pelicula = peliculaDao.obtenerPelicula(idPelicula);
		TestResponse response = new TestResponse();
		response.setNombre(pelicula.getNombre());
		response.setDistribuidor(pelicula.getDistribuidor().getId());		
		return response;
	}

	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}
}
