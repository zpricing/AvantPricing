package cl.zpricing.avant.web.administrators;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.servicios.CinemaDao;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.commons.utils.DateUtils;

public class FuncionValidarExistenciaController  implements Controller {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private FuncionDao funcionDao;
	private ComplejoDao complejoDao;
	private CinemaDao cinemaDao;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parametroFecha = request.getParameter("fecha");
		Integer parametroComplejo = Integer.parseInt(request.getParameter("complejo"));
		log.debug("Complejo : " + parametroComplejo);
		log.debug("Fecha : " + parametroFecha);
		
		Date fecha = DateUtils.format_ddMMyyyy.parse(parametroFecha);
		Complejo complejo = complejoDao.obtenerComplejo(parametroComplejo);
		List<Funcion> funciones = funcionDao.obtenerFunciones(complejo, fecha);
		
		log.debug("# de funciones : " + funciones.size());
		
		Iterator<Funcion> iterFunciones = funciones.iterator();
		while (iterFunciones.hasNext()){
			Funcion funcion = iterFunciones.next();
			if (!cinemaDao.existeFuncion(complejo, funcion)) {
				log.info("Eliminando funcion : " + funcion.getIdExterno() + " - Sala : " + funcion.getSala().getIdExterno());
				funcionDao.eliminarFuncion(funcion);
			}
		}
		
		return new ModelAndView("redirect:/admin_funciones.htm?idComplejo="+parametroComplejo+"&fecha="+parametroFecha);
	}

	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
	public void setCinemaDao(CinemaDao cinemaDao) {
		this.cinemaDao = cinemaDao;
	}
}
