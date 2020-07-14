
package cl.zpricing.avant.web.administrators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.servicios.FuncionDao;

/**
 * <b>Controlador de la vista eliminarfuncion</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 23-12-2008 Daniel Estévez Garay: versión inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class EliminarFuncionController implements Controller{
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private FuncionDao funcionDao;
	
	/**
	 * 
	 * Método responsable de la vista eliminarfuncion al hacer request.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 23-12-2008 Daniel Estévez Garay: Versión Inicial</li>
	 *   <li> 1.1 04-02-2010 Camilo Araya: borrar múltiples funciones</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP
	 * @param arg1 Respuesta HTTP
	 * @return Modelo y vista que redirecciona a vista funciones
	 * @throws Exception
	 * @since 1.0
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if ( request.getParameterValues("funciones") != null) {
			String[] funcionesElegidasABorrar = request.getParameterValues("funciones");
			for (String thisFuncionElegida : funcionesElegidasABorrar) {
				log.debug("Borrando función " + thisFuncionElegida);
				int id = Integer.parseInt(thisFuncionElegida);
				Funcion nueva = new Funcion();
				nueva.setDescripcion("");
				nueva.setId(id);
				funcionDao.eliminarFuncion(nueva);
			}
		}
		
		//para borrar de a una funcion
		if ( request.getParameter("idfuncion") != null) {
			int id = Integer.parseInt(request.getParameter("idfuncion") );
			log.debug("Borrando función " + id);
			Funcion nueva = new Funcion();
			nueva.setDescripcion("");
			nueva.setId(id);
			funcionDao.eliminarFuncion(nueva);
		}
		
		
		//info de fecha y complejo de donde se elimino
		String complejoId = request.getParameter("idComplejo");
		String fecha = request.getParameter("fecha");
		return new ModelAndView("redirect:/admin_funciones.htm?idComplejo="+complejoId+"&fecha1="+fecha);
	}
	
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}
}
