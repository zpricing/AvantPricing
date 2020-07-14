package cl.zpricing.avant.webservices;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface TestService {
	TestResponse getHolaMundo(@WebParam(name="idPelicula") Integer idPelicula);
}
