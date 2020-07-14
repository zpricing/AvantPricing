package cl.zpricing.avant.web;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.Transaccion;
import cl.zpricing.commons.dao.DaoGenerico;
import cl.zpricing.commons.utils.DateUtils;

public class TransaccionesPorFechaController implements Controller {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private DaoGenerico daoGenerico;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("dashboard/transaccionesPorFecha");
		String fechaString = request.getParameter("fecha");
		Date fecha = new Date();
		
		if (fechaString != null && !fechaString.equalsIgnoreCase("")) {
			fecha = DateUtils.format_ddMMyyyy.parse(fechaString);
		}
		
		Calendar calendario = DateUtils.obtenerCalendario(fecha);
		Transaccion transaccion = new Transaccion();
		transaccion.setFecha(calendario.getTime());
		List<Transaccion> transacciones = daoGenerico.obtenerListaPorCampo(transaccion, "fecha");
		
		Iterator<Transaccion> iterTransacciones = transacciones.iterator();
		log.debug("Transacciones: ");
		while(iterTransacciones.hasNext()) {
			transaccion = iterTransacciones.next();
			log.debug("  Transaccion : " + transaccion.getTicket().getDescripcion());
		}
		
		mv.addObject("fecha", fecha);
		mv.addObject("transacciones", transacciones);
		return mv;
	}

	public void setDaoGenerico(DaoGenerico daoGenerico) {
		this.daoGenerico = daoGenerico;
	}
}
