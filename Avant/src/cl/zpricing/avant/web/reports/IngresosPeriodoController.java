/**
 * 
 */
package cl.zpricing.avant.web.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.servicios.AggregateDao;
import cl.zpricing.avant.servicios.ComplejoDao;
import cl.zpricing.avant.servicios.ReporteDao;
import cl.zpricing.avant.web.chart.CategoriaGrafico;
import cl.zpricing.avant.web.chart.Color;
import cl.zpricing.avant.web.chart.Dia;
import cl.zpricing.avant.web.chart.GeneradorXMLGrafico;
import cl.zpricing.avant.web.chart.SerieDatos;
import cl.zpricing.avant.web.chart.Valor;

/**
 * <b>Controlador de la vista del informe de ingresos por periodo.</b>
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 19-01-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class IngresosPeriodoController implements Controller {

	private ComplejoDao complejoDao;
	private AggregateDao aggregateDao;
	private ReporteDao reporteDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		/*
		 * Todos los comentarios del c�digo se encuentran en:
		 * 
		 * cl.zpricing.revman.web.reports.AsistenciaPeriodoController.java
		 * 
		 * Ya que los c�digos hacen practicamente lo mismo, solo cambian los nombres.
		 */

		Map<String, Object> map = (Map<String, Object>)arg0.getSession().getAttribute("map_reporte");
		
		ModelAndView mv = new ModelAndView("reports/ingresosPeriodo");				
		
		List<CategoriaGrafico> lista_categorias = new ArrayList<CategoriaGrafico>();
		List<SerieDatos> lista_series = new ArrayList<SerieDatos>();
		
		String[] id_complejos_seleccionados =(String[])map.get("id_complejos");
		double total_ingresos = 0;
		double total_semana=0;
		int contador_dias=0;
		int contador_semana=0;
		int largo_grafico=0;
		boolean existe_grafico=false;
		boolean por_semana=false;
		String fecha = (String)map.get("fecha_inicio");
		String fecha_fin = (String)map.get("fecha_fin");
		int dia=Integer.parseInt(fecha.split("-")[0]);
		int mes=Integer.parseInt(fecha.split("-")[1])-1;
		int ano=Integer.parseInt(fecha.split("-")[2]);
		int dia_fin=Integer.parseInt(fecha_fin.split("-")[0]);
		int mes_fin=Integer.parseInt(fecha_fin.split("-")[1])-1;
		int ano_fin=Integer.parseInt(fecha_fin.split("-")[2]);
		GregorianCalendar fecha_inicio_primer_periodo = new GregorianCalendar(ano, mes, dia);
		GregorianCalendar fecha_inicio_segundo_periodo = new GregorianCalendar(ano, mes, dia);
		GregorianCalendar fecha_inicio_tmp = new GregorianCalendar(ano, mes,dia);
		GregorianCalendar fecha_fin_primer_periodo = new GregorianCalendar(ano_fin, mes_fin,dia_fin);
		GregorianCalendar fecha_fin_segundo_periodo = new GregorianCalendar(ano_fin, mes_fin,dia_fin);
		GregorianCalendar fecha_fin_tmp = new GregorianCalendar(ano_fin,mes_fin,dia_fin);

		fecha_inicio_primer_periodo.add(Calendar.YEAR, -1);
		fecha_fin_primer_periodo.add(Calendar.YEAR, -1);
		
		while (fecha_inicio_segundo_periodo.get(Calendar.DAY_OF_WEEK) != fecha_inicio_primer_periodo.get(Calendar.DAY_OF_WEEK)) {
			fecha_inicio_primer_periodo.add(Calendar.DAY_OF_MONTH, 1);
			fecha_fin_primer_periodo.add(Calendar.DAY_OF_MONTH, 1);
		}
	
		mv.addObject("fecha_inicio_primer_periodo",Dia.getDia(fecha_inicio_primer_periodo.get(Calendar.DAY_OF_WEEK)) + " " + fecha_inicio_primer_periodo.get(Calendar.DAY_OF_MONTH) + " de " + Dia.getMes(fecha_inicio_primer_periodo.get(Calendar.MONTH)));
		mv.addObject("fecha_fin_primer_periodo",Dia.getDia(fecha_fin_primer_periodo.get(Calendar.DAY_OF_WEEK)) + " " + fecha_fin_primer_periodo.get(Calendar.DAY_OF_MONTH) + " de " + Dia.getMes(fecha_fin_primer_periodo.get(Calendar.MONTH)) + " del " + fecha_fin_primer_periodo.get(Calendar.YEAR));
		mv.addObject("fecha_inicio_segundo_periodo",Dia.getDia(fecha_inicio_segundo_periodo.get(Calendar.DAY_OF_WEEK)) + " " + fecha_inicio_segundo_periodo.get(Calendar.DAY_OF_MONTH) + " de " + Dia.getMes(fecha_inicio_segundo_periodo.get(Calendar.MONTH)));
		mv.addObject("fecha_fin_segundo_periodo",Dia.getDia(fecha_fin_segundo_periodo.get(Calendar.DAY_OF_WEEK)) + " " + fecha_fin_segundo_periodo.get(Calendar.DAY_OF_MONTH) + " de " + Dia.getMes(fecha_fin_segundo_periodo.get(Calendar.MONTH)) + " del " + fecha_fin_segundo_periodo.get(Calendar.YEAR));	

		ArrayList<Map<String, Object>> arreglo_maps_por_complejo = new ArrayList<Map<String, Object>>();

		fecha_fin_primer_periodo.add(Calendar.DAY_OF_MONTH, 1);
		fecha_fin_segundo_periodo.add(Calendar.DAY_OF_MONTH, 1);
		
		fecha_inicio_tmp.set(fecha_inicio_primer_periodo.get(Calendar.YEAR),fecha_inicio_primer_periodo.get(Calendar.MONTH), fecha_inicio_primer_periodo.get(Calendar.DAY_OF_MONTH), 0, 0);
		fecha_fin_tmp.set(fecha_fin_primer_periodo.get(Calendar.YEAR),fecha_fin_primer_periodo.get(Calendar.MONTH), fecha_fin_primer_periodo.get(Calendar.DAY_OF_MONTH), 0, 0);
		contador_dias = 0;
		while (fecha_inicio_tmp.compareTo(fecha_fin_tmp) != 0) {
			fecha_inicio_tmp.add(Calendar.DAY_OF_MONTH, 1);
			contador_dias++;
		}
		
		if(contador_dias>28)por_semana=true;
		
		for (int x = 0; x < id_complejos_seleccionados.length; x++) {

			Map<String, Object> map_complex = new HashMap<String, Object>();
			List<Double> ingresos_por_dias_primer = new ArrayList<Double>();
			List<Double> ingresos_por_dias_segundo = new ArrayList<Double>();
			List<String> lista_dias_primer_periodo = new ArrayList<String>();
			List<String> lista_dias_segundo_periodo = new ArrayList<String>();
			//...
			SerieDatos serieDat = new SerieDatos();
			SerieDatos serieDat2 = new SerieDatos();
			List<Valor> lista_valores = new ArrayList<Valor>();
			List<Valor> lista_valores2 = new ArrayList<Valor>();
			List<Double> lista_valores_falsos = new ArrayList<Double>();
			List<Double> lista_valores_falsos2 = new ArrayList<Double>();
			List<CategoriaGrafico> lista_categorias2 = new ArrayList<CategoriaGrafico>();
			List<SerieDatos> lista_series2 = new ArrayList<SerieDatos>();
			GeneradorXMLGrafico grafico = new GeneradorXMLGrafico();
			Color.iniciarColor();
			existe_grafico=false;
			
			Complejo complejo = complejoDao.obtenerComplejo(Integer.parseInt(id_complejos_seleccionados[x]));
			map_complex.put("nombre_complejo", complejo.getNombre());
			
			lista_categorias.add(new CategoriaGrafico(complejo.getNombre()));
			
			fecha_inicio_tmp.set(fecha_inicio_primer_periodo.get(Calendar.YEAR),fecha_inicio_primer_periodo.get(Calendar.MONTH), fecha_inicio_primer_periodo.get(Calendar.DAY_OF_MONTH), 0, 0);
			fecha_fin_tmp.set(fecha_fin_primer_periodo.get(Calendar.YEAR),fecha_fin_primer_periodo.get(Calendar.MONTH), fecha_fin_primer_periodo.get(Calendar.DAY_OF_MONTH), 0, 0);
			contador_dias = 0;
			total_ingresos = 0;
			contador_semana=0;
			total_semana=0;
			while (fecha_inicio_tmp.compareTo(fecha_fin_tmp) != 0) {
				
				double valor=obtenerIngresosDiariosComplejo(fecha_inicio_tmp,complejo);;
				total_ingresos = total_ingresos + valor;
				total_semana=total_semana+valor;
				
				if(por_semana)
				{
					if(contador_dias==7)
					{	
					lista_categorias2.add(new CategoriaGrafico("Sem" + contador_semana/7));
					lista_dias_primer_periodo.add("Sem" + contador_semana/7);	
				
					ingresos_por_dias_primer.add(total_semana);
					lista_valores_falsos.add(new Double(1));
					lista_valores.add(new Valor(((Double)total_semana).intValue()));
					contador_dias=0;
					total_semana=0;
					}
					contador_semana++;
				}
				else
				{
				ingresos_por_dias_primer.add(valor);
				lista_valores_falsos.add(new Double(1));
				lista_valores.add(new Valor(((Double)valor).intValue()));
				char inicial_dia=' ';
				if(fecha_inicio_tmp.get(Calendar.DAY_OF_WEEK)!=4)
					inicial_dia =Dia.getDia((fecha_inicio_tmp.get(Calendar.DAY_OF_WEEK))).charAt(0);
				else
					inicial_dia='X';
				lista_categorias2.add(new CategoriaGrafico("" + inicial_dia + fecha_inicio_tmp.get(Calendar.DAY_OF_MONTH)));
				lista_dias_primer_periodo.add("" + inicial_dia + fecha_inicio_tmp.get(Calendar.DAY_OF_MONTH));
				}
				
				fecha_inicio_tmp.add(Calendar.DAY_OF_MONTH, 1);
				contador_dias++;
			}
			
			serieDat.setNombreSerie("Primer Periodo");
			serieDat.setValores(lista_valores);
			serieDat.setColor(Color.generarColor());
			serieDat.setPesosDias(lista_valores_falsos);
			serieDat.setPesoPel(new Double(1));
			lista_series2.add(serieDat);
			
			if (total_ingresos>0)
				existe_grafico=true;
			map_complex.put("por_dias_primer_periodo", ingresos_por_dias_primer);
			map_complex.put("total_primer_periodo", total_ingresos);
			
			double ingresos_primer_periodo = total_ingresos;	//para calculo porcentaje diferencia
			
			fecha_inicio_tmp.set(fecha_inicio_segundo_periodo.get(Calendar.YEAR),fecha_inicio_segundo_periodo.get(Calendar.MONTH), fecha_inicio_segundo_periodo.get(Calendar.DAY_OF_MONTH), 0, 0);
			fecha_fin_tmp.set(fecha_fin_segundo_periodo.get(Calendar.YEAR),fecha_fin_segundo_periodo.get(Calendar.MONTH), fecha_fin_segundo_periodo.get(Calendar.DAY_OF_MONTH), 0, 0);
			total_ingresos = 0;
			contador_semana=0;
			contador_dias=0;
			total_semana=0;
			while (fecha_inicio_tmp.compareTo(fecha_fin_tmp) != 0) {
				
				double valor=obtenerIngresosDiariosComplejo(fecha_inicio_tmp,complejo);
				total_ingresos = total_ingresos + valor;
				total_semana=total_semana+valor;
				
				if(por_semana)
				{
					if(contador_dias==7)
					{
						lista_dias_segundo_periodo.add("Sem" + contador_semana/7);
						
						ingresos_por_dias_segundo.add(total_semana);
						lista_valores_falsos2.add(new Double(1));
						lista_valores2.add(new Valor(((Double)total_semana).intValue()));
						contador_dias=0;
						total_semana=0;
						}
						contador_semana++;
				}
				else
				{
					ingresos_por_dias_segundo.add(valor);
					lista_valores_falsos2.add(new Double(1));
					lista_valores2.add(new Valor(((Double)valor).intValue()));
				
				char inicial_dia=' ';
				if(fecha_inicio_tmp.get(Calendar.DAY_OF_WEEK)!=4)
					inicial_dia =Dia.getDia((fecha_inicio_tmp.get(Calendar.DAY_OF_WEEK))).charAt(0);
				else
					inicial_dia='X';
				lista_dias_segundo_periodo.add("" + inicial_dia + fecha_inicio_tmp.get(Calendar.DAY_OF_MONTH));
				
				}
				fecha_inicio_tmp.add(Calendar.DAY_OF_MONTH, 1);
				contador_dias++;
			}
			if (total_ingresos>0)
				existe_grafico=true;
			
			serieDat2.setNombreSerie("Segundo Periodo");
			serieDat2.setValores(lista_valores2);
			serieDat2.setColor(Color.generarColor());
			serieDat2.setPesosDias(lista_valores_falsos2);
			serieDat2.setPesoPel(new Double(1));
			lista_series2.add(serieDat2);
			
			
			grafico.setCategorias(lista_categorias2);
			grafico.setDatos(lista_series2);
			grafico.setTitulo("Ingresos Diarias.");
			grafico.setLegendaX("Dias");
			grafico.setLegendaY1("Ingresos");
			String graficString = grafico.aXML().replace("\"", "'");
			graficString = graficString.replace("\n", "");
			map_complex.put("grafico", graficString);
			
			map_complex.put("lista_dias_primer_periodo",lista_dias_primer_periodo);
			map_complex.put("lista_dias_segundo_periodo",lista_dias_segundo_periodo);
			map_complex.put("por_dias_segundo_periodo", ingresos_por_dias_segundo);
			map_complex.put("total_segundo_periodo", total_ingresos);
			double ingresos_segundo_periodo = total_ingresos;	//para calculo porcentaje diferencia
			double porcentaje_diferencia = (ingresos_segundo_periodo - ingresos_primer_periodo)/ingresos_primer_periodo;
			map_complex.put("porcentaje_diferencia", porcentaje_diferencia);
			map_complex.put("existe_grafico", existe_grafico);
			arreglo_maps_por_complejo.add(map_complex);
			
			mv.addObject("lista_dias_segundo_periodo",lista_dias_segundo_periodo);
			mv.addObject("lista_dias_primer_periodo",lista_dias_primer_periodo);
		}
		mv.addObject("arreglo_maps_por_complejo",arreglo_maps_por_complejo);
		mv.addObject("contador_dias",contador_dias);
		mv.addObject("existe_grafico", existe_grafico);
		
		if (contador_dias>=80)
			largo_grafico=80;
		else if (contador_dias<6)
				largo_grafico=6;
			 else
				 largo_grafico=contador_dias;
		
		mv.addObject("largo_grafico",largo_grafico);
		
		arg0.getSession().setAttribute("para_reportes", mv);
		
		GeneradorXMLGrafico grafico = new GeneradorXMLGrafico();
		
		Color.iniciarColor();
		for(int i=0;i<2;i++)
		{
		SerieDatos serieDat = new SerieDatos();
		if(i==0)
			serieDat.setNombreSerie("Primer Periodo");
		else
			serieDat.setNombreSerie("Segundo Periodo");
		List<Valor> lista_valores = new ArrayList<Valor>();
		List<Double> lista_valores_falsos = new ArrayList<Double>();
		
		for (Map<String, Object> mapa_complejo: arreglo_maps_por_complejo) {	
	
			lista_valores_falsos.add(new Double(1));
			if(i==0)
			lista_valores.add(new Valor(((Double)mapa_complejo.get("total_primer_periodo")).intValue()));
			else
			lista_valores.add(new Valor(((Double)mapa_complejo.get("total_segundo_periodo")).intValue()));
		}
		serieDat.setValores(lista_valores);
		serieDat.setColor(Color.generarColor());
		serieDat.setPesosDias(lista_valores_falsos);
		serieDat.setPesoPel(new Double(1));
		lista_series.add(serieDat);
		}
		grafico.setCategorias(lista_categorias);
		grafico.setDatos(lista_series);
		grafico.setTitulo("Ingresos por Periodo");
		grafico.setLegendaX("Periodos");
		grafico.setLegendaY1("Ingresos");
		String graficString = grafico.aXML().replace("\"", "'");
		graficString = graficString.replace("\n", "");
		mv.addObject("grafico", graficString);

		return mv;
		
	}

	private double obtenerIngresosDiariosComplejo(GregorianCalendar dia_buscado, Complejo complejo) {
		
		GregorianCalendar fecha_temporal_inicial= new GregorianCalendar(dia_buscado.get(Calendar.YEAR),dia_buscado.get(Calendar.MONTH),dia_buscado.get(Calendar.DAY_OF_MONTH),8,0);
		GregorianCalendar fecha_temporal_de_final= new GregorianCalendar(dia_buscado.get(Calendar.YEAR),dia_buscado.get(Calendar.MONTH),dia_buscado.get(Calendar.DAY_OF_MONTH),3,0);
		fecha_temporal_de_final.add(Calendar.DAY_OF_MONTH,1);
		
		return reporteDao.ingresosPromedio(fecha_temporal_inicial, fecha_temporal_de_final, complejo);
	}

	public void setAggregateDao(AggregateDao aggregateDao) {
		this.aggregateDao = aggregateDao;
	}

	public AggregateDao getAggregateDao() {
		return aggregateDao;
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

}
