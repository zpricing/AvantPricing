/**
 * 
 */
package cl.zpricing.avant.web.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.reports.PrecioAsistencia;
import cl.zpricing.avant.model.reports.Rango;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.ReporteDao;
import cl.zpricing.avant.util.Util;
import cl.zpricing.avant.web.chart.ArchivoGrafico;
import cl.zpricing.avant.web.chart.CategoriaGrafico;
import cl.zpricing.avant.web.chart.Color;
import cl.zpricing.avant.web.chart.GeneradorXMLGrafico;
import cl.zpricing.avant.web.chart.SerieDatos;
import cl.zpricing.avant.web.chart.Valor;

/**
 * <b>Descripci�n de la Clase</b>
 * Controlador de asistenciaingresoperiodorm permite generar el informe que ve ingresos, asistencia
 * e ingresos de confiteria por RM
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 23-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class AsistenciaIngresoPeriodoRMController implements Controller {
	public static final int COMP = 0;
	public static final int TOTAL = 1;
	public static final int RM = 2;
	public static final int NO_RM = 3;
	public static final int PER_RM = 4;
	public static final int COLUMNAS = 5;
	
	private ReporteDao reporteDao;
	private ComplejoDao complejoDao;
	
	@SuppressWarnings("unused")
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> map = (Map<String, Object>)request.getSession().getAttribute("map_reporte");
		if(map!=null){
			
			ModelAndView mv = new ModelAndView("reports/reporteaiprm");
				
			Rango rango = new Rango();
			rango.setInicio(Util.DateToString2(Util.StringToDate((String)map.get("fecha_inicio")))+" 00:00:00");
			Date fechaFin = Util.StringToDate((String)map.get("fecha_fin"));
			Calendar cal = new GregorianCalendar();
			cal.setTime(fechaFin);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			rango.setFin(Util.DateToString2(cal.getTime())+" 00:00:00");
				
	
			String idComplejos[] = (String[])map.get("id_complejos");
			
			//iniciamos los datos importantes
			List<CategoriaGrafico> catGraf = new ArrayList<CategoriaGrafico>();
	
			List<Valor> valIngSRM = new ArrayList<Valor>();
			List<Valor> valIngRM = new ArrayList<Valor>();
			List<Valor> valAsiSRM = new ArrayList<Valor>();
			List<Valor> valAsiRM = new ArrayList<Valor>();
			List<Valor> valConfSRM = new ArrayList<Valor>();
			List<Valor> valConfRM = new ArrayList<Valor>();
			
			String tablaIng[][] = new String[idComplejos.length][COLUMNAS];
			String tablaAsi[][] = new String[idComplejos.length][COLUMNAS];
			String tablaConf[][] = new String[idComplejos.length][COLUMNAS];
					
			//Recorremos todos los complejos seleccionados y sacamos los datos de cada item
			for(int i=0; i<idComplejos.length; i++){
								
				rango.setIdComplejo(idComplejos[i]);
				
				//RM
				PrecioAsistencia asistenciaIngresoRM = reporteDao.asistenciaIngresoPorPeriodoRM(rango);
				Valor valorIngRM = new Valor(asistenciaIngresoRM.getPrecio());
				valIngRM.add(valorIngRM);
				tablaIng[i][RM] = "$"+Util.formatNumber(valorIngRM.getValor()); 
				Valor valorAsiRM = new Valor(asistenciaIngresoRM.getAsistencia()); 
				valAsiRM.add(valorAsiRM);
				tablaAsi[i][RM] = ""+Util.formatNumber(valorAsiRM.getValor());
							
				//Total
				PrecioAsistencia asistenciaIngreso = reporteDao.asistenciaIngresoPorPeriodo(rango);
				Valor valorIngT = new Valor(asistenciaIngreso.getPrecio());
				tablaIng[i][TOTAL] = "$"+Util.formatNumber(valorIngT.getValor());
				Valor valorAsiT = new Valor(asistenciaIngreso.getAsistencia());
				tablaAsi[i][TOTAL] = ""+Util.formatNumber(valorAsiT.getValor());
				
				//No RM
				Valor valorIngSRM = new Valor(asistenciaIngreso.getPrecio() - asistenciaIngresoRM.getPrecio());
				valIngSRM.add(valorIngSRM);
				tablaIng[i][NO_RM] = "$"+Util.formatNumber(valorIngSRM.getValor());
				Valor valorAsiSRM = new Valor(asistenciaIngreso.getAsistencia() - asistenciaIngresoRM.getAsistencia()); 
				valAsiSRM.add(valorAsiSRM);
				tablaAsi[i][NO_RM] = ""+Util.formatNumber(valorAsiSRM.getValor());
				
				//Porcentaje
				tablaIng[i][PER_RM] = Util.formatNumber(100.0*((double)(valorIngRM.getValor()))/((double)(valorIngSRM.getValor())))+"%";
				tablaAsi[i][PER_RM] = Util.formatNumber(100.0*((double)(valorAsiRM.getValor()))/((double)(valorAsiSRM.getValor())))+"%";
							
				//Confiteria (para calcular sus datos de debe usar los datos de los anteriores)
				Long confiteriaTotal = reporteDao.ingresosConfiteriaPorPeriodo(rango);
				double fracRM = ((double)valorAsiRM.getValor())/((double)valorAsiT.getValor());
				double fracNoRM = 1.0-fracRM;
				Valor valorConfRM = new Valor((long)(((double)confiteriaTotal)*fracRM));
				valConfRM.add(valorConfRM);
				tablaConf[i][RM] = "$"+Util.formatNumber(valorConfRM.getValor());
				
				Valor valorConfSRM = new Valor((long)(((double)confiteriaTotal)*fracNoRM)); 
				valConfSRM.add(valorConfSRM);
				tablaConf[i][NO_RM] = "$"+Util.formatNumber(valorConfSRM.getValor());
				
				tablaConf[i][TOTAL] = "$"+Util.formatNumber(confiteriaTotal);
				tablaConf[i][PER_RM] = Util.formatNumber(100.0*((double)(valorConfRM.getValor()))/((double)(valorConfSRM.getValor())))+"%";
				
				//Total
				Complejo complejo = complejoDao.obtenerComplejo(Integer.parseInt(idComplejos[i]));
				String nombreComplejo = complejo.getNombre();
				tablaIng[i][COMP] = nombreComplejo;
				tablaAsi[i][COMP] = nombreComplejo;
				tablaConf[i][COMP] = nombreComplejo;
				
				catGraf.add(new CategoriaGrafico(nombreComplejo));
			}
			
			map.put("tablaIng", tablaIng);
			map.put("tablaAsi", tablaAsi);
			map.put("tablaConf", tablaConf);
			
			//Iniciar grafico Ingreso
			List<SerieDatos> seriesDat = new ArrayList<SerieDatos>();
			
			SerieDatos serieIngRM = new SerieDatos();
			serieIngRM.setValores(valIngRM);
			serieIngRM.setNombreSerie("RM");
			serieIngRM.setColor(Color.generarColor(Color.ROJO));
			seriesDat.add(serieIngRM);
			
			SerieDatos serieIngSRM = new SerieDatos();
			serieIngSRM.setValores(valIngSRM);
			serieIngSRM.setNombreSerie("Total sin RM");
			serieIngSRM.setColor(Color.generarColor(Color.PLOMO));
			seriesDat.add(serieIngSRM);
			
			//Guardar los datos del grafico Ingreso
			GeneradorXMLGrafico gen = new GeneradorXMLGrafico();
			gen.setCategorias(catGraf);
			gen.setDatos(seriesDat);
			gen.setTitulo("Ingresos");
			gen.setLegendaX("Complejos");
			gen.setLegendaY1("Ingresos en pesos");
		
			ArchivoGrafico graXML = new ArchivoGrafico(request,"ingresoRM");
			gen.setRutaArchivo(graXML.rutaGraficoInterna());
		
			gen.setMostrarNombres();
			gen.aXML();
			
			map.put("xmlGrafGI", graXML.rutaGraficoExterna());
	
			//Iniciar grafico Asistencia
			List<SerieDatos> seriesDatAsi = new ArrayList<SerieDatos>();
			
			SerieDatos serieAsiRM = new SerieDatos();
			serieAsiRM.setValores(valAsiRM);
			serieAsiRM.setNombreSerie("RM");
			serieAsiRM.setColor(Color.generarColor(Color.ROJO));
			seriesDatAsi.add(serieAsiRM);
			
			SerieDatos serieAsiSRM = new SerieDatos();
			serieAsiSRM.setValores(valAsiSRM);
			serieAsiSRM.setNombreSerie("Total sin RM");
			serieAsiSRM.setColor(Color.generarColor(Color.PLOMO));
			seriesDatAsi.add(serieAsiSRM);
			
			//Guardar los datos del grafico de Asistencia
			gen.setCategorias(catGraf);
			gen.setDatos(seriesDatAsi);
			gen.setTitulo("Asistencia");
			gen.setLegendaX("Complejos");
			gen.setLegendaY1("Asistencia (personas)");
		
			graXML = new ArchivoGrafico(request,"asistenciaRM");
			gen.setRutaArchivo(graXML.rutaGraficoInterna());
		
			gen.setMostrarNombres();
			gen.aXML();
			
			map.put("xmlGrafGA", graXML.rutaGraficoExterna());
			
			//Iniciar grafico Confiteria
			List<SerieDatos> seriesDatConf = new ArrayList<SerieDatos>();
			
			SerieDatos serieConfRM = new SerieDatos();
			serieConfRM.setValores(valConfRM);
			serieConfRM.setNombreSerie("RM");
			serieConfRM.setColor(Color.generarColor(Color.ROJO));
			seriesDatConf.add(serieConfRM);
			
			SerieDatos serieConfSRM = new SerieDatos();
			serieConfSRM.setValores(valConfSRM);
			serieConfSRM.setNombreSerie("Total sin RM");
			serieConfSRM.setColor(Color.generarColor(Color.PLOMO));
			seriesDatConf.add(serieConfSRM);
			
			//Guardar los datos del grafico de Confiteria
			gen.setCategorias(catGraf);
			gen.setDatos(seriesDatConf);
			gen.setTitulo("Ingresos Confiteria");
			gen.setLegendaX("Complejos");
			gen.setLegendaY1("Ingresos en pesos");
		
			graXML = new ArchivoGrafico(request,"confiteriaRM");
			gen.setRutaArchivo(graXML.rutaGraficoInterna());
		
			gen.setMostrarNombres();
			gen.aXML();
			
			map.put("xmlGrafGC", graXML.rutaGraficoExterna());
			
			map.put("desde", (String)map.get("fecha_inicio"));
			map.put("hasta", (String)map.get("fecha_fin"));
			
		
			mv.addObject("map",map);
			
			//para la vista, el numero de complejos
			request.setAttribute("nComplejos", idComplejos.length);
		
			//Guarda datos en sesion por si se quiere realizar el pdf
			request.getSession().setAttribute("para_reportes", mv);
				
			return mv;
		}
		
		map = new HashMap<String, Object>();
		ModelAndView mv = new ModelAndView("reporteaiprm");
		
		mv.addObject("map",map);
		
		//Guarda datos en sesion por si se quiere realizar el pdf
		request.getSession().setAttribute("para_reportes", mv);
			
		return mv;
	}

	/**
	 * @return the reporteDao
	 */
	public ReporteDao getReporteDao() {
		return reporteDao;
	}

	/**
	 * @param reporteDao the reporteDao to set
	 */
	public void setReporteDao(ReporteDao reporteDao) {
		this.reporteDao = reporteDao;
	}

	/**
	 * @return the complejoDao
	 */
	public ComplejoDao getComplejoDao() {
		return complejoDao;
	}

	/**
	 * @param complejoDao the complejoDao to set
	 */
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}

}
