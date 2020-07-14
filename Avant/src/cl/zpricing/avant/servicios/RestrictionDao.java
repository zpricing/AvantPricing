package cl.zpricing.avant.servicios;

import java.util.Date;
import java.util.List;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.restriccion.Restriction;
import cl.zpricing.avant.model.restriccion.dto.RestrictionMovie;
import cl.zpricing.commons.dao.DaoGenerico;

public interface RestrictionDao extends DaoGenerico {
	public List<Restriction> obtenerRestricciones(Complejo complejo);
	public List<Restriction> obtenerRestricciones(Funcion funcion);
	public void asignarRestriccionPelicula(Integer peliculaId, Integer restriccionId, Date fechaHasta);
	public List<RestrictionMovie> obtenerPeliculasConRestriccion();
	public int eliminarRestriccionPelicula(Integer peliculaId, Integer restriccionId);
}
