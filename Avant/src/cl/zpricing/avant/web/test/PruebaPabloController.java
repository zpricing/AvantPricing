package cl.zpricing.avant.web.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.servicios.FuncionDao;

public class PruebaPabloController implements Controller{
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(this.getClass());
	private FuncionDao funcionDao;

	
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		//loadManager.actualizarPlantillas();
		//Funcion funcion = funcionDao.obtenerFuncion(60004);
		//funcion.setProtegido(true);
		//funcionDao.actualizarFuncion(funcion);
		log.debug("Termino prueba");
        ModelAndView mv=new ModelAndView("test/testPablo");
		return mv;
	}


	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}
}
