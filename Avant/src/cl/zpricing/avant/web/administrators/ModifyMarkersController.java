/**
 * 
 */
package cl.zpricing.avant.web.administrators;

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
import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.form.ManageMarkersForm;

/**
 * <b>Controlador de la vista modifymarkers</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 19-01-2009 MARIO: version inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class ModifyMarkersController extends SimpleFormController {
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
		ManageMarkersForm form = new ManageMarkersForm();
		int id = Integer.parseInt(request.getParameter("id_marker"));
		Marker marker = markerDao.obtenerMarker(id);
		if (marker.getClase() != null)
			form.setClase_id(marker.getClase() + "");
		if (marker.getComplejo() != null)
			form.setComplejo_id(marker.getComplejo() + "");
		if (marker.getPelicula() != null)
			form.setPelicula_id(marker.getPelicula() + "");
		if (marker.getFechaHasta() != null)
			form.setFecha_hasta(Util.DateToString(marker.getFechaHasta()));

		form.setUsuario_id(marker.getUsuario().getId() + "");
		form.setTipo_marker_id(marker.getTipoMarker().getId() + "");
		try {
			form.setFecha(Util.DateToString(marker.getFecha()));
		} catch (Exception e) {
			form.setFecha("");
		}
		form.setDescripcion(marker.getDescripcion());
		form.setId(id + "");
		return form;
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
		Usuario usuario = usuarioDao.obtenerUsuarioByName(user);

		refdata.put("markers", markers);
		refdata.put("tipoMarkers", tipoMarkers);
		refdata.put("peliculas", peliculas);
		refdata.put("complejos", complejos);
		refdata.put("clases", clases);
		refdata.put("user", usuario.getNombreCompleto());

		return refdata;
	}

	public ModelAndView onSubmit(Object command) throws ServletException {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		Marker marker = new Marker();
		ManageMarkersForm form = (ManageMarkersForm) command;
		int id = Integer.parseInt(form.getId());
		String tipo_marker_id = form.getTipo_marker_id();
		String pelicula_id = form.getPelicula_id();
		String complejo_id = form.getComplejo_id();
		String descripcion = form.getDescripcion();
		String clase_id = form.getClase_id();
		Date fecha = Util.StringToDate(form.getFecha());
		if (!form.getFecha_hasta().isEmpty()) {
			Date fecha_hasta = Util.StringToDate(form.getFecha_hasta());
			marker.setFechaHasta(fecha_hasta);
			log.debug("marker FechaHasta: " + marker.getFechaHasta().toString());
		}

		marker.setFecha(fecha);
		marker.setUsuario(usuarioDao.obtenerUsuarioByName(user));

		if (pelicula_id.compareTo("-") == 0)
			marker.setPelicula(null);
		else
			marker.setPelicula(peliculaDao.obtenerPelicula(Integer
					.parseInt(pelicula_id)));

		if (complejo_id.compareTo("-") == 0)
			marker.setComplejo(null);
		else
			marker.setComplejo(complejoDao.obtenerComplejo(Integer
					.parseInt(complejo_id)));

		if (clase_id.compareTo("-") == 0)
			marker.setClase(null);
		else
			marker.setClase(claseDao.obtenerClase(Integer.parseInt(clase_id)));

		marker.setTipoMarker(tipoMarkerDao.obtenerTipoMarker(Integer
				.parseInt(tipo_marker_id)));

		marker.setDescripcion(descripcion);
		marker.setId(id);

		markerDao.actualizarMarker(marker);

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
