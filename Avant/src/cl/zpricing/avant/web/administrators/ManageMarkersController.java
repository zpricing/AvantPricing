package cl.zpricing.avant.web.administrators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Clase;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Marker;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.TipoMarker;
import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.servicios.ClaseDao;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.FuncionDao;
import cl.zpricing.avant.servicios.MarkerDao;
import cl.zpricing.avant.servicios.PeliculaDao;
import cl.zpricing.avant.servicios.SalaDao;
import cl.zpricing.avant.servicios.TipoMarkerDao;
import cl.zpricing.avant.servicios.UsuarioDao;
import cl.zpricing.avant.web.form.ManageMarkersForm;
import cl.zpricing.commons.utils.ErroresUtils;

/**
 * Controlador encargo de la administracion de markers
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 30-12-2008 Julio Andres Olivares Alarcon: version inicial.</li>
 * <li>1.1 23-09-2010 Nicolas Dujovne W.: modificacion en el parseo de fechas, uso de simpleDateFormat en vez de Util y limpieza de codigo innecesario.
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ManageMarkersController extends SimpleFormController {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	private MarkerDao markerDao;
	private UsuarioDao usuarioDao;
	private PeliculaDao peliculaDao;
	private SalaDao salaDao;
	private ComplejoDao complejoDao;
	private TipoMarkerDao tipoMarkerDao;
	private FuncionDao funcionDao;
	private ClaseDao claseDao;

	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		String id_complejo = request.getParameter("id_complejo");
		ManageMarkersForm modifyform = new ManageMarkersForm();

		if (id_complejo != null)
			modifyform.setComplejo_id(id_complejo);

		return modifyform;
	}

	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws Exception {		
		Map<String, Object> refdata = new HashMap<String, Object>();
		String user = SecurityContextHolder.getContext().getAuthentication().getName();

		List<Marker> markers = markerDao.listaMarkerTodos();
		List<TipoMarker> tipoMarkers = tipoMarkerDao.listarTipoMarker();
		List<Pelicula> peliculas = peliculaDao.obtenerListaPeliculasActivas();
		List<Complejo> complejos = complejoDao.complejosTodos();
		List<Clase> clases = claseDao.obtenerListaDeClasesDesc();
		Usuario usuario = new Usuario();
		usuario = usuarioDao.obtenerUsuarioByName(user);

		refdata.put("markers", markers);
		refdata.put("tipoMarkers", tipoMarkers);
		refdata.put("peliculas", peliculas);
		refdata.put("complejos", complejos);
		refdata.put("clases", clases);
		refdata.put("user", usuario.getNombreCompleto());

		return refdata;
	}

	public ModelAndView onSubmit(Object command) throws ServletException {
		try {
			String user = SecurityContextHolder.getContext().getAuthentication().getName();
			Marker marker = new Marker();
			ManageMarkersForm form = (ManageMarkersForm) command;
			String tipo_marker_id = form.getTipo_marker_id();
			String pelicula_id = form.getPelicula_id();
			String complejo_id = form.getComplejo_id();
			String descripcion = form.getDescripcion();
			String clase_id = form.getClase_id();
			log.debug("fecha : " + form.getFecha());
			log.debug("fecha_hasta : " + form.getFecha_hasta());
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			Date fecha = format.parse(form.getFecha());
			
			log.debug("fecha : " + fecha);
			
			if (!form.getFecha_hasta().isEmpty()) {
				Date fecha_hasta = format.parse(form.getFecha_hasta());
				marker.setFechaHasta(fecha_hasta);
				log.debug("marker FechaHasta: " + marker.getFechaHasta().toString());
			}
	
			marker.setFecha(fecha);
			marker.setUsuario(usuarioDao.obtenerUsuarioByName(user));
	
			if (pelicula_id.compareTo("-") == 0)
				marker.setPelicula(null);
			else
				marker.setPelicula(peliculaDao.obtenerPelicula(Integer.parseInt(pelicula_id)));
	
			if (complejo_id.compareTo("-") == 0)
				marker.setComplejo(null);
			else
				marker.setComplejo(complejoDao.obtenerComplejo(Integer.parseInt(complejo_id)));
	
			if (clase_id.compareTo("-") == 0)
				marker.setClase(null);
			else
				marker.setClase(claseDao.obtenerClase(Integer.parseInt(clase_id)));
	
			marker.setTipoMarker(tipoMarkerDao.obtenerTipoMarker(Integer.parseInt(tipo_marker_id)));
			marker.setDescripcion(descripcion);	
			markerDao.agregarMarker(marker);
		} catch (ParseException e) {
			ErroresUtils.extraeStackTrace(e);
			//TODO redirigir a pagina de error.
		}
		return new ModelAndView(new RedirectView(getSuccessView()));
	}

	public void setMarkerDao(MarkerDao markerDao) {
		this.markerDao = markerDao;
	}
	public void setPeliculaDao(PeliculaDao peliculaDao) {
		this.peliculaDao = peliculaDao;
	}
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	public void setSalaDao(SalaDao salaDao) {
		this.salaDao = salaDao;
	}
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
	public void setTipoMarkerDao(TipoMarkerDao tipoMarkerDao) {
		this.tipoMarkerDao = tipoMarkerDao;
	}
	public void setFuncionDao(FuncionDao funcionDao) {
		this.funcionDao = funcionDao;
	}
	public void setClaseDao(ClaseDao claseDao) {
		this.claseDao = claseDao;
	}
}
