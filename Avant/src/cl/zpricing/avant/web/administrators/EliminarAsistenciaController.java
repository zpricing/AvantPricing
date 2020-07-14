
package cl.zpricing.avant.web.administrators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.model.Asistencia;
import cl.zpricing.avant.servicios.FuncionDao;

/**
 * <b>Controlador de la vista eliminarasistencia</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 29-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class EliminarAsistenciaController extends SimpleFormController{
	
	/**
	 * Impresi�n de log.
	 */
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private FuncionDao funcionDao;

	/**
	 * @return the funcionDao
	 */
	public FuncionDao getFuncionDao() {
		return funcionDao;
	}

	/**
	 * @param funcionDao the funcionDao to set
	 */
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}
	
	/**
	 * 
	 * M�todo responsable de la vista eliminarasistencia al hacer request.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 29-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param request Solicitud HTTP
	 * @param arg1 Respuesta HTTP
	 * @return Modelo y vista que redirecciona a vista asistencias
	 * @throws Exception
	 * @since 1.0
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		int id = Integer.parseInt(request.getParameter("idasistencia"));
		Asistencia nueva = funcionDao.obtenerAsistencia(id);
		funcionDao.eliminarAsistencia(nueva);
						
		return new ModelAndView("redirect:/admin_asistencias.htm?idfuncion="+nueva.getFuncionAsociada().getId());
	}
	

}
