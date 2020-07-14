package cl.zpricing.avant.servicios;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;

public interface CinemaDao {
	public boolean existeFuncion(Complejo complejo, Funcion funcion);
}
