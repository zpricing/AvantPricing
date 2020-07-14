

package cl.zpricing.avant.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

import com.lowagie.text.List;

import scriptella.driver.csv.opencsv.CSVWriter;

import cl.zpricing.avant.model.Diaria;
import cl.zpricing.avant.model.Mensual;
import cl.zpricing.avant.model.Semanal;
import cl.zpricing.avant.servicios.ReportesDao;
import cl.zpricing.avant.web.form.ReportesForm;
import cl.zpricing.commons.utils.DateUtils;

@RemoteProxy(name="dwrReportes")
public class ReportesController extends SimpleFormController{
	
	@Autowired
	private ReportesDao reportesDao;
	
	public static final String date = "dd-MM-yyyy HH-mm-ss";
	static SimpleDateFormat dateFormat = new SimpleDateFormat(date);
	public enum Tipos {diario,semanal, mensual};

	
	
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response,
			Object command,
			BindException errors)	
			throws Exception {

		ReportesForm form = (ReportesForm) command;
		String tipo = form.getTipo();
		String fecha_inicio = form.getInicio();
		String fecha_fin = form.getFin();
		
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(cal.YEAR),cal.get(cal.MONTH),1);
		Date inicio = cal.getTime();
		
		cal = Calendar.getInstance();
		Date fin = cal.getTime();
		
		if(fecha_inicio != "" && fecha_fin != ""){
			inicio = new SimpleDateFormat("MM/dd/yyyy").parse(fecha_inicio);
			fin = new SimpleDateFormat("MM/dd/yyyy").parse(fecha_fin);
		}
		
		StringWriter sw = new StringWriter();
		CSVWriter csvWriter = new CSVWriter(sw);
		
		ArrayList<String[]> data = getData(tipo,inicio,fin);
		
		for(String[] entradas : data){
			csvWriter.writeNext(entradas);
		}
		csvWriter.close();
		
		String fileName =  "reporte"+ "_" + tipo +  "_" + now() + ".csv";
		response.setContentType("text/csv; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName+ "\"");
		
		InputStream is = new ReaderInputStream(new StringReader(sw.toString()));
		IOUtils.copy(is, response.getOutputStream());
		response.flushBuffer();
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
	
	private String now() {
		Calendar calendar = Calendar.getInstance();
		return dateFormat.format(calendar.getTime());
	}
	
	private ArrayList<String[]> getData(String tipo, Date inicio, Date fin) {
		ArrayList<String[]> data = new ArrayList<String[]>();
		
		switch ( Tipos.valueOf(tipo) )
		{
			case diario: data = getDiario(inicio,fin);
			break;
			case semanal:data = getSemanal(inicio,fin);
			break;
			case mensual: data = getMensual(inicio,fin);
			break;
		}
		return data;
	}

	private ArrayList<String[]> getMensual(Date inicio, Date fin) {
		ArrayList<Mensual> reporte = reportesDao.obtenerReporteMensual(inicio, fin);
		ArrayList<String[]> data = new ArrayList<String[]>();
		int i=0;
		for(Mensual m:reporte){
			if(i == 0) {
				data.add(m.getHeader());
			}
			data.add(m.getString());
			i++;
		}
		return data ;
	}

	private ArrayList<String[]> getSemanal(Date inicio, Date fin) {
		ArrayList<Semanal> reporte = reportesDao.obtenerReporteSemanal(inicio, fin);
		ArrayList<String[]> data = new ArrayList<String[]>();
		int i=0;
		for(Semanal s:reporte){
			if(i == 0) {
				data.add(s.getHeader());
			}
			data.add(s.getString());
			i++;
		}
		return data ;
	}

	private ArrayList<String[]> getDiario(Date inicio, Date fin) {
		ArrayList<Diaria> reporte = reportesDao.obtenerReporteDiario(inicio, fin);
		ArrayList<String[]> data = new ArrayList<String[]>();
		int i=0;
		for(Diaria d:reporte){
			if(i == 0) {
				data.add(d.getHeader());
			}
			data.add(d.getString());
			i++;
		}
		return data ;
	}
	
	@RemoteMethod
	public String getGraficos(String fecha_inicio,String fecha_fin) throws JSONException, ParseException{
		

		Date inicio = new SimpleDateFormat("MM/dd/yyyy").parse(fecha_inicio);
		Date fin = new SimpleDateFormat("MM/dd/yyyy").parse(fecha_fin);
		ArrayList<Diaria> reporteDiario = reportesDao.obtenerReporteDiario(inicio, fin);
		ArrayList<Semanal> reporteSemanal = reportesDao.obtenerReporteSemanal(inicio, fin);
		ArrayList<Mensual> reporteMensual = reportesDao.obtenerReporteMensual(inicio, fin);
		
		JSONObject graficos = new JSONObject();
		
		// graficos para la tabla diaria
		JSONArray graficoDiario = new JSONArray();
		JSONArray graficoDiarioPorc = new JSONArray();
		
		JSONArray att_online_diario = new JSONArray();
		JSONArray att_online_rm_diario= new JSONArray();
		JSONArray att_online_diarioPrevY = new JSONArray();
		JSONArray porc_online_diario = new JSONArray();
		JSONArray porc_online_rm_diario= new JSONArray();
		JSONArray porc_online_diarioPrevY = new JSONArray();
		
		for(Diaria d:reporteDiario){
			long fecha = d.getFecha().getTime();
			int asistencia_online = d.getAsistencia_online();
			int asistencia_rm = d.getAsistencia_RM();
			int asistencia_online_prev_year = d.getAsistencia_online_prev_year();
			int asistencia_online_rm = 	d.getAsistencia_online() -d.getAsistencia_RM();
			float porc_online = d.getPorc_asistencia_online();
			float porc_online_prev_year = d.getPorc_asistencia_online_prev_year();
			float porc_online_rm =  (asistencia_online - asistencia_rm)*100/d.getAsistencia_total();

			att_online_diario.add(getPunto(fecha, asistencia_online));
			att_online_rm_diario.add(getPunto(fecha,asistencia_online_rm));
			att_online_diarioPrevY.add(getPunto(fecha, asistencia_online_prev_year));
			porc_online_diario.add(getPunto(fecha,porc_online));
			porc_online_diarioPrevY.add(getPunto(fecha, porc_online_prev_year));
			porc_online_rm_diario.add(getPunto(fecha,porc_online_rm));
			
		}
		
		graficoDiario.add(obtenerGrafico("Online Attendance",att_online_diario));
		graficoDiario.add(obtenerGrafico("Online Attendance - RM",att_online_rm_diario));
		graficoDiario.add(obtenerGrafico("Online Attendance Prev. Year",att_online_diarioPrevY));
		graficoDiarioPorc.add(obtenerGrafico("% Online Attendance",porc_online_diario));
		graficoDiarioPorc.add(obtenerGrafico("% Online Attendance - RM",porc_online_rm_diario));
		graficoDiarioPorc.add(obtenerGrafico("% Online Attendance Prev. Year",porc_online_diarioPrevY));
		
		graficos.put("graficoDiario", graficoDiario);		
		graficos.put("graficoDiarioPorc", graficoDiarioPorc);
		
		
		// graficos para la tabla semanal
		JSONArray graficoSemanal = new JSONArray();
		JSONArray graficoSemanalPorc = new JSONArray();
		
		JSONArray ticksSemanal = new JSONArray();
		
		JSONArray att_online_semanal = new JSONArray();
		JSONArray att_online_semanalPrevY = new JSONArray();
		JSONArray att_online_rm_semanal= new JSONArray();
		JSONArray porc_online_semanal = new JSONArray();
		JSONArray porc_online_semanalPrevY = new JSONArray();
		JSONArray porc_online_rm_semanal= new JSONArray();
		
		int i  = 0;
		for(Semanal d:reporteSemanal){
			int semana = d.getSemana();
			int asistencia_online = d.getAsistencia_online();
			int asistencia_online_prev_year = d.getAsistencia_online_prev_year();
			int asistencia_rm = d.getAsistencia_RM();
			int asistencia_online_rm = 	d.getAsistencia_online() -d.getAsistencia_RM();
			float porc_online = d.getPorc_asistencia_online();
			float porc_online_prev_year = d.getPorc_asistencia_online_prev_year();
			float porc_online_rm =  (asistencia_online - asistencia_rm)*100/d.getAsistencia_total();

			ticksSemanal.add(getPunto(i, semana));
			att_online_semanal.add(getPunto(i, asistencia_online));
			att_online_semanalPrevY.add(getPunto(i, asistencia_online_prev_year));
			att_online_rm_semanal.add(getPunto(i,asistencia_online_rm));
			porc_online_semanal.add(getPunto(i,porc_online));
			porc_online_semanalPrevY.add(getPunto(i,porc_online_prev_year));
			porc_online_rm_semanal.add(getPunto(i,porc_online_rm));
			i++;
		}
		
		graficoSemanal.add(obtenerGrafico("Online Attendance",att_online_semanal));
		graficoSemanal.add(obtenerGrafico("Online Attendance - RM",att_online_rm_semanal));
		graficoSemanal.add(obtenerGrafico("Online Attendance Prev. Year",att_online_semanalPrevY));
		graficoSemanalPorc.add(obtenerGrafico("% Online Attendance",porc_online_semanal));
		graficoSemanalPorc.add(obtenerGrafico("% Online Attendance - RM",porc_online_rm_semanal));
		graficoSemanalPorc.add(obtenerGrafico("% Online Attendance Prev. Year",porc_online_semanalPrevY));
		
		graficos.put("graficoSemanal", graficoSemanal);		
		graficos.put("graficoSemanalPorc", graficoSemanalPorc);
		graficos.put("ticksSemanal", ticksSemanal);
		
		// graficos para la tabla mensual
		JSONArray graficoMensual = new JSONArray();
		JSONArray graficoMensualPorc = new JSONArray();
		
		JSONArray ticksMensual = new JSONArray();
		
		JSONArray att_online_mensual = new JSONArray();
		JSONArray att_online_mensualPrevY = new JSONArray();
		JSONArray att_online_rm_mensual= new JSONArray();
		JSONArray porc_online_mensual = new JSONArray();
		JSONArray porc_online_mensualPrevY = new JSONArray();
		JSONArray porc_online_rm_mensual= new JSONArray();
		
		i  = 0;
		for(Mensual d:reporteMensual){
			int mes = d.getMes();
			int asistencia_online = d.getAsistencia_online();
			int asistencia_online_prev_year = d.getAsistencia_online_prev_year();
			int asistencia_rm = d.getAsistencia_RM();
			int asistencia_online_rm = 	d.getAsistencia_online() -d.getAsistencia_RM();
			float porc_online = d.getPorc_asistencia_online();
			float porc_online_prev_year = d.getPorc_asistencia_online_prev_year();
			float porc_online_rm =  (asistencia_online - asistencia_rm)*100/d.getAsistencia_total();

			ticksMensual.add(getPunto(i, mes));
			att_online_mensual.add(getPunto(i, asistencia_online));
			att_online_mensualPrevY.add(getPunto(i, asistencia_online_prev_year));
			att_online_rm_mensual.add(getPunto(i,asistencia_online_rm));
			porc_online_mensual.add(getPunto(i,porc_online));
			porc_online_mensualPrevY.add(getPunto(i,porc_online_prev_year));
			porc_online_rm_mensual.add(getPunto(i,porc_online_rm));
			i++;
		}
		
		graficoMensual.add(obtenerGrafico("Online Attendance",att_online_mensual));
		graficoMensual.add(obtenerGrafico("Online Attendance - RM",att_online_rm_mensual));
		graficoMensual.add(obtenerGrafico("Online Attendance Prev. Year",att_online_mensualPrevY));
		graficoMensualPorc.add(obtenerGrafico("% Online Attendance",porc_online_mensual));
		graficoMensualPorc.add(obtenerGrafico("% Online Attendance - RM",porc_online_rm_mensual));
		graficoMensualPorc.add(obtenerGrafico("% Online Attendance Prev. Year",porc_online_mensualPrevY));
		
		graficos.put("graficoMensual", graficoMensual);		
		graficos.put("graficoMensualPorc", graficoMensualPorc);	
		graficos.put("ticksMensual", ticksMensual);
		
		return graficos.toString();
	}
	
	


	private JSONArray  getPunto(Object x, Object y) {
		JSONArray punto = new JSONArray();
		punto.add(x);
		punto.add(y);

		return punto;
	}

	private JSONObject obtenerGrafico(String label, JSONArray data) throws JSONException {
		
		JSONObject grafico = new JSONObject();
		grafico .put("label",label);
		grafico.put("data", data);

		return grafico;
	}

	protected Map<String, Object> referenceData(HttpServletRequest req) throws Exception{
		
		Map<String, Object> params = new HashMap<String, Object>();
		String fecha_inicio = req.getParameter("inicio");
		String fecha_fin = req.getParameter("fin");
		
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(cal.YEAR),0,1);
		Date inicio = cal.getTime();
		
		cal = Calendar.getInstance();
		Date fin = cal.getTime();
		
		if(fecha_inicio != null && fecha_fin != null){
			inicio = new SimpleDateFormat("MM/dd/yyyy").parse(fecha_inicio);
			fin = new SimpleDateFormat("MM/dd/yyyy").parse(fecha_fin);
		}
		params.put("inicio", new SimpleDateFormat("MM/dd/yyyy").format(inicio));
		params.put("fin", new SimpleDateFormat("MM/dd/yyyy").format(fin));
		
		params.put("diario", reportesDao.obtenerReporteDiario(inicio, fin));
		params.put("semanal", reportesDao.obtenerReporteSemanal(inicio, fin));
		params.put("mensual", reportesDao.obtenerReporteMensual(inicio, fin));

		return params;		
	}

	public void setReportesDao(ReportesDao reportesDao){
		this.reportesDao = reportesDao;
	}	
	
}