package cl.zpricing.avant.servicios;

import java.util.Date;
import java.util.List;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.VentaDiaria;

public interface VentaDao {
	public VentaDiaria obtenerVentaDiariaFecha(Complejo complejo, Date fecha);
	public List<VentaDiaria> obtenerVentaDiariaRangoFecha(Complejo complejo, Date fechaDesde, Date fechaHasta);
}
