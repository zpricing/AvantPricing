package cl.zpricing.avant.web.administrators;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Marker;
import cl.zpricing.avant.model.TipoMarker;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.MarkerDao;
import cl.zpricing.avant.web.form.MarkersForm;
import cl.zpricing.commons.utils.ErroresUtils;

public class MarkersController extends SimpleFormController {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private MarkerDao markerDao;
	private ComplejoDao complejoDao;
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		log.debug(" Iniciando formBackingObject");
		MarkersForm form = new MarkersForm();
		return form;
	}
	
	public ModelAndView onSubmit(Object command)throws ServletException {
		String markersData = ((MarkersForm) command).getMarkers();
		String[] markersSerialized = markersData.split("@");
		Marker[] markers = new Marker[markersSerialized.length];
		
		for(int i=0; i< markersSerialized.length; i++) {
			Marker marker = new Marker();
			String[] markerData = markersSerialized[i].split("#");
			
			TipoMarker tipoMarker = new TipoMarker();
			tipoMarker.setId(Integer.parseInt(markerData[0]));
			marker.setTipoMarker(tipoMarker);
			
			String[] complejosData = markerData[1].split(",");
			Complejo[] complejos = new Complejo[complejosData.length];
			for(int j=0; j<complejosData.length; j++){
				Complejo complejo = new Complejo();
				complejo.setId(Integer.parseInt(complejosData[j]));
				complejos[j] = complejo;
			}
			marker.setComplejos(complejos);
			
			try {
				DateFormat df = new SimpleDateFormat("y-M-d H:m:s");
				Date fechaDesde = df.parse(markerData[2]);
				Date fechaHasta = df.parse(markerData[3]);
				marker.setFecha(fechaDesde);
				marker.setFechaHasta(fechaHasta);
				markers[i] = marker;
			}
			catch (Exception e) {
				log.error(ErroresUtils.extraeStackTrace(e));
			}
		}
		markerDao.actualizarNMarkers(markers);
		
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		List<Marker> markers = markerDao.listaNMarkerTodos();
		List<Complejo> complejos = complejoDao.complejosTodos();
		List<TipoMarker> tipoMarkers = markerDao.listaTipoNMarkersTodos();
		
		Map<String, Object> refdata = new HashMap<String, Object>();
		
		refdata.put("markers", markers);
		refdata.put("complejos", complejos);
		refdata.put("tipoMarkers", tipoMarkers);
		
		return refdata;
	}
	
	public void setMarkerDao(MarkerDao markerDao) {
		this.markerDao = markerDao;
	}
	
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
}
