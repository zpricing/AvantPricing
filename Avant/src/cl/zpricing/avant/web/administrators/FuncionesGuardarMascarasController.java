package cl.zpricing.avant.web.administrators;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.MascaraDao;

public class FuncionesGuardarMascarasController implements Controller {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private FuncionDao funcionDao;
	private MascaraDao mascaraDao;
	private ComplejoDao complejoDao;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] mascaras = request.getParameterValues("mascaras");
		String[] funcionesBloqueadas = request.getParameterValues("bloqueadas");
		String complejoId = request.getParameter("idComplejo");
		String fecha = request.getParameter("fecha");
		
		log.debug("complejoId : [" + complejoId + "]");
		log.debug("fecha : [" + fecha + "]");
		
		if (mascaras == null) {
			log.warn("No se encontro info de mascaras");
		}
		
		if (mascaras != null) {
			log.info("Cantidad de funciones a actualizar : [" + mascaras.length + "]");
			for(int i = 0 ; i < mascaras.length ; i++) {
				String map[] = mascaras[i].split(":");
				log.info("   Tupla funcion - mascara : [" + map[0] + " - " + map[1] + "]");
				
				if (map[0] != null && map[1] != null && !map[1].equalsIgnoreCase("0")) {
					int funcionId = Integer.parseInt(map[0]);
					int mascaraId = Integer.parseInt(map[1]);
					
					Funcion funcion = funcionDao.obtenerFuncion(funcionId);
					Mascara mascara = mascaraDao.obtenerMascara(mascaraId);
					if (mascara != null) {
						if (funcion != null) {
							funcion.cambiaMascara(mascara);
							funcionDao.actualizarFuncion(funcion);
						}
					}
				}
			}
		}
		else {
			log.debug("mascaras o complejo nulos");
		}
		if (funcionesBloqueadas != null) {
			for(int i = 0 ; i < funcionesBloqueadas.length ; i++) {
				String map[] = funcionesBloqueadas[i].split(":");
				if (map[0] != null && map[1] != null) {
					int funcionId = Integer.parseInt(map[0]);
					boolean bloqueada = Boolean.parseBoolean(map[1]);
					Funcion funcion = new Funcion();
					funcion.setId(funcionId);
					funcion.setBloqueada(bloqueada);
					funcionDao.actualizarBloqueada(funcion);
				}
			}
		}
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("idComplejo", complejoId);
		mapa.put("fecha", fecha);
	
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
