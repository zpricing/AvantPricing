package cl.zpricing.avant.web.administrators;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.MascaraDao;
import cl.zpricing.commons.utils.DateUtils;

public class FuncionesCopiarMascarasController implements Controller {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private FuncionDao funcionDao;
	private MascaraDao mascaraDao;
	private ComplejoDao complejoDao;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String complejoId = request.getParameter("complejo_id");
		String fecha = request.getParameter("fecha");
		
		log.debug("complejo : " + complejoId);
		log.debug("fecha : " + fecha);
		
		Date fechaContable = DateUtils.format_ddMMyyyy.parse(fecha);
		Complejo complejo = complejoDao.obtenerComplejo(Integer.parseInt(complejoId)); 
		
		List<Funcion> funciones = funcionDao.obtenerFunciones(complejo, fechaContable);
		
		for (Funcion funcion : funciones) {
			if (funcion.getMascara() != null) {
				funcionDao.copiaMascaraAFuncionesFuturas(funcion);
			}
		}
		
		return new ModelAndView("redirect:/admin_funciones.htm?idComplejo="+complejoId+"&fecha="+fecha);
	}
	
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}
	public void setMascaraDao(MascaraDao mascaraDao) {
		this.mascaraDao = mascaraDao;
	}
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
}
