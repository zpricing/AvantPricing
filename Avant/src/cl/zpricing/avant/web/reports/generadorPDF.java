package cl.zpricing.avant.web.reports;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.model.reports.PrecioAsistencia;
import cl.zpricing.avant.util.GraficosPDF;
import cl.zpricing.avant.util.Util;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Esta clase se encarga de crear el pdf, desde un ModelAndView en sesi�n
 * obtiene un map con todos los datos respectivos del reporte para generar el pdf.
 * El controlador de cada reporte a la vez que genera en ModelAndView para el reporte online,
 * es decir, el que contiene los datos para el JSP, tambi�n lo guarda en sesion con el nombre
 * "para_reportes", entonces esta clase se encarga de sacar esos datos y de acuerdo al parametro
 * tipo que se le pasa por "arriba" llama a la funci�n que generar� el reporte correcto. 
 * 
 *  Es sencillo crear un pdf, solo hay que llamar a este Controlador que en el modelo ModelAndView
 *  la vista que gen�ra es el pdf mismo, es por esto que en el servlet el bean se nombre con un id, 
 *  en este caso 'generadorPDF', a diferencia de el atributo name de los otros beans.
 *  Los datos para crear el pdf se pueden obtener por un map, por sesi�n o de cualquier otra manera
 *  permitida para pasar datos a un Controlador, luego se van adhiriendo los elementos mediante el m�todo 
 *  pdf.add(Element element). Hay elementos especiales para agregar contenido, como Phrase(), PdfPCell,
 *  PdfTable, Paragraph, etc. Una vez que termina el m�todo buildPdfDocument el pdf se crear� solo
 *  y se deslpegar� en el navegador. 
 *  Si se quieren agregar gr�ficos mediante JFreeChart antes de comenzar a agregar elementos primero se
 *  debe crear un PdfWriter para escribir en el pdf y luego abrir el pdf mediante pdf.open(). Finalmente
 *  se debe cerrar el pdf mediante pdf.close().
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 22-01-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class generadorPDF extends AbstractPdfView {
	
	//Fuentes gen�ricas que se usar�n en todos los reportes
	private Font smallFont = FontFactory.getFont(FontFactory.HELVETICA,7);
	private Font normalFont = FontFactory.getFont(FontFactory.HELVETICA,12);
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/**
	 * buidPdfDocument construye el pdf, el par�metro pdfDoc ser� el pdf final y en �l hay
	 * que ir agregando todos los elementos que se quieren mostrar con el m�todo pdfDoc.add(Element element).
	 * Es importante usar el par�metro HttpServletResponse arg4 para obtener el OutputStream para poder
	 * mostrar los gr�ficos.
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 05-01-2009 Julio Andr�s Olivares Alarc�n: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param arg0
	 * @param pdfDoc  El documento pdf al que se escribe 
	 * @param arg2  Writer que en realidad no lo ocupo
	 * @param request  Se obtiene el par�metro tipo para determinar el tipo de reporte
	 * @param arg4  Se obtiene el OutputStream
	 * @return
	 * @throws Exception 
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	protected void buildPdfDocument(Map arg0, Document pdfDoc, PdfWriter arg2,
			HttpServletRequest request, HttpServletResponse arg4) throws Exception {
		
		//se obtiene el tipo de reporte mediante el par�metro tipo
		//este par�metro est� adherido al link en el jsp que llama a generarGrafico
		String tipo = request.getParameter("tipo");
		
	
		// se indica que el pdf se escribir� para salir por pantalla en el navegador
		// tambi�n podr�a ser en un archivo u otro Stream.
		PdfWriter writer = PdfWriter.getInstance(pdfDoc, arg4.getOutputStream());
		pdfDoc.open();
		
		pdfDoc.addAuthor("ZhetaPricing Chile S.A.");
		pdfDoc.addTitle("Reportes");
		
        Image logo = Image.getInstance("webapps"+request.getContextPath()+"/images/LogoZP.PNG");
        logo.scalePercent(33);
        logo.setAlignment(Image.RIGHT);
        pdfDoc.add(logo); 

		//agrega un encabezado estandar con la fecha, hora y autor del reporte.
		try{
		pdfDoc.add(new Phrase("" + Util.DateToString(Calendar.getInstance().getTime())
				+ " " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) 
				+ ":"  + Calendar.getInstance().get(Calendar.MINUTE)
				+ "  " + ((Usuario)request.getSession().getAttribute("Usuario")).getNombreCompleto(),FontFactory.getFont(FontFactory.HELVETICA,11,Font.ITALIC)));
		}catch(Exception e)
		{
			pdfDoc.add(new Phrase("" + Util.DateToString(Calendar.getInstance().getTime())
					+ " " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) 
					+ ":"  + Calendar.getInstance().get(Calendar.MINUTE),FontFactory.getFont(FontFactory.HELVETICA,11,Font.ITALIC)));
		}
		
		
		ModelAndView mv = (ModelAndView) request.getSession().getAttribute("para_reportes");
		
		Map<String,Object> map = mv.getModel();
		
		if(tipo.compareTo("0")==0)
			asistenciasPorPeriodo(pdfDoc,map,arg4.getOutputStream(),writer);
		if(tipo.compareTo("1")==0)
			ingresosPorPeriodo(pdfDoc,map,arg4.getOutputStream(),writer);
		if(tipo.compareTo("2")==0)
			ticketPromedio(pdfDoc,map,arg4.getOutputStream(),writer);
		if(tipo.compareTo("3")==0)
			confiteria(pdfDoc,map,arg4.getOutputStream(),writer);
		if(tipo.compareTo("4")==0)
			asistenciaIngresoRM(pdfDoc,map,arg4.getOutputStream(),writer);
	}
	
	private void confiteria(Document pdf, Map<String, Object> map, ServletOutputStream servletOutputStream, PdfWriter writer) throws FileNotFoundException, DocumentException {

		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100f);
		PdfPCell cell = new PdfPCell(new Phrase("Ingresos por concepto de Confiter�a", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD, new Color(255, 255, 255))));
		cell.setBackgroundColor(new Color(0,0,0));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		pdf.add(table);
		
		totalesPorComplejo(pdf, map, servletOutputStream, writer, "Complejos", "Ingresos", "Dias", "Ingresos","$");

		pdf.close();
	}
    
	@SuppressWarnings("unchecked")
	private void ticketPromedio(Document pdf, Map<String, Object> map, ServletOutputStream servletOutputStream, PdfWriter writer) throws DocumentException {
		
		Map<String, Object> map_map = (Map<String, Object>)map.get("map");
		List<Map<String, Object>> graficos = (List<Map<String, Object>>)map_map.get("graficos");
		
		//Titulo
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100f);
		PdfPCell cell = new PdfPCell(new Phrase("Ticket Promedio", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD, new Color(255, 255, 255))));
		cell.setBackgroundColor(new Color(0,0,0));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		pdf.add(table);
		//end
		
		/* graficos contiene un map por cada complejo al cual se le esta calculando el ticket promedio.
		 * Cada map contiene el nombre del complejo, el (long)ticketPromedio y una lista de objetos PrecioAsistencia
		 * con el precio(clase) y su respectiva asistencia. 
		 */
		for (Map<String, Object> map_grafico : graficos) {
			List<Map<String, Object>> lista_de_maps_grafico= new ArrayList<Map<String,Object>>();
						
			//subtitulo: nombre del complejo subrayado
			pdf.add(new Paragraph(" "));
			Paragraph subtitulo1 = new Paragraph((String)map_grafico.get("complejo"),FontFactory.getFont(FontFactory.HELVETICA_BOLD,12,Font.UNDERLINE));
			subtitulo1.setAlignment(Element.ALIGN_CENTER);
			pdf.add(subtitulo1);
	
			pdf.add(new Paragraph("Desde: " + map_map.get("desde"),normalFont));
			pdf.add(new Paragraph("Hasta:  " + map_map.get("hasta"),normalFont));
			pdf.add(new Paragraph(new Phrase("Ticket Promedio: " +  Util.formatNumber((Long)map_grafico.get("ticketProm")), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD))));
			pdf.add(new Paragraph(" "));
			
			
			PdfPTable tabla_ticket= new PdfPTable(((List<PrecioAsistencia>)map_grafico.get("precioAsistencia")).size()+1);
			
			tabla_ticket.addCell(new PdfPCell(new Phrase("Precio",smallFont)));
			for (PrecioAsistencia precioAsistencia : (List<PrecioAsistencia>)map_grafico.get("precioAsistencia")) {
				try{
				tabla_ticket.addCell(new PdfPCell(new Phrase(Util.formatNumber(precioAsistencia.getPrecio()), smallFont)));
				}catch(Exception e)
				{}
			}
			tabla_ticket.addCell(new PdfPCell(new Phrase("Asistencia", smallFont)));

			for (PrecioAsistencia precioAsistencia : (List<PrecioAsistencia>)map_grafico.get("precioAsistencia")) {
				try{
				tabla_ticket.addCell(new PdfPCell(new Phrase(Util.formatNumber(precioAsistencia.getAsistencia()), smallFont)));
				}catch(Exception e){}
			}
			tabla_ticket.setWidthPercentage(100f);
			pdf.add(tabla_ticket);
	
			for (PrecioAsistencia precioAsistencia : (List<PrecioAsistencia>)map_grafico.get("precioAsistencia")) {
			
				try{
					Map<String,Object> map_graf_pdf = new HashMap<String, Object>();
					map_graf_pdf.put("categoria", String.valueOf(precioAsistencia.getPrecio()));
					map_graf_pdf.put("valor", ((Long)precioAsistencia.getAsistencia()).doubleValue());
					lista_de_maps_grafico.add(map_graf_pdf);
				}catch(Exception e){}
			}
			
			JFreeChart chart = GraficosPDF.getPieChart("",lista_de_maps_grafico,true);
			PiePlot3D plot = (PiePlot3D) chart.getPlot();
			plot.setLabelFont(new java.awt.Font("SansSerif",Font.UNDERLINE,4));
			
			PdfContentByte cb = writer.getDirectContent();
			PdfTemplate tp = cb.createTemplate(500, 270);
			Graphics2D g2d = tp.createGraphics(500, 270, new DefaultFontMapper());
			Rectangle2D r2d = new Rectangle2D.Double(0, 0, 500, 270);
			chart.draw(g2d, r2d);
			Image jpg = Image.getInstance(tp);
			pdf.add(jpg);
			pdf.newPage();
		}
		
		pdf.close();
		
	}
	

	private void ingresosPorPeriodo(Document pdf, Map<String, Object> map, ServletOutputStream servletOutputStream, PdfWriter writer) throws DocumentException {

		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100f);
		PdfPCell cell = new PdfPCell(new Phrase("Ingresos por Periodos", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD, new Color(255, 255, 255))));
		cell.setBackgroundColor(new Color(0,0,0));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		pdf.add(table);
		
		totalesPorComplejo(pdf, map, servletOutputStream, writer,"Complejos","Ingresos" ,"Dias" ,"Ingresos","$" );

		pdf.close();
	}
	

	private void asistenciasPorPeriodo(Document pdf, Map<String,Object> map, ServletOutputStream servletOutputStream, PdfWriter writer ) throws DocumentException{
		
		
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100f);
		PdfPCell cell = new PdfPCell(new Phrase("Asistencias por Periodos", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD, new Color(255, 255, 255))));
		cell.setBackgroundColor(new Color(0,0,0));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		pdf.add(table);
		
		totalesPorComplejo(pdf, map, servletOutputStream, writer,"Complejos","Asistencias" ,"Dias" ,"Asistencias", "" );
		
		pdf.close();
	}

	@SuppressWarnings("unchecked")
	private void totalesPorComplejo(Document pdf, Map<String,Object> map, ServletOutputStream servletOutputStream, PdfWriter writer,
			String ejeX, String ejeY,String ejeX2, String ejeY2, String simbol) throws DocumentException{

		ArrayList<Map<String, Object>> arreglo_maps_por_complejo = (ArrayList<Map<String, Object>>)map.get("arreglo_maps_por_complejo");
		
		//para graficar en pdf
		List<Map<String, Object>> lista_de_maps_grafico= new ArrayList<Map<String,Object>>();	
		List<String> valores_ejeX = new ArrayList<String>();
		//..
		
		//int cantidad_dias = (Integer)map.get("contador_dias");
		List<String> lista_dias_primer_periodo = (List<String>)map.get("lista_dias_primer_periodo");
		@SuppressWarnings("unused")
		List<String> lista_dias_segundo_periodo = (List<String>)map.get("lista_dias_segundo_periodo");
		
		Paragraph subtitulo1 = new Paragraph("Primer Periodo: " + (String)map.get("fecha_inicio_primer_periodo") + " al " + (String)map.get("fecha_fin_primer_periodo"),normalFont);
		Paragraph subtitulo2 = new Paragraph("Segundo Periodo: " + (String)map.get("fecha_inicio_segundo_periodo") + " al " + (String)map.get("fecha_fin_segundo_periodo"),normalFont);
		subtitulo1.setAlignment(Element.ALIGN_CENTER);
		subtitulo2.setAlignment(Element.ALIGN_CENTER);
		pdf.add(subtitulo1);
		pdf.add(subtitulo2);
		
		//PARTE UNO
		pdf.add(new Paragraph(" "));
		pdf.add(new Paragraph("1.Totales por complejo.",normalFont));
		pdf.add(new Paragraph(" "));
		
		PdfPTable tabla_asistencias_totales= new PdfPTable(3);
		tabla_asistencias_totales.addCell(new PdfPCell(new Phrase("Complejo", normalFont)));
		tabla_asistencias_totales.addCell(new PdfPCell(new Phrase("Primer Periodo", normalFont)));
		tabla_asistencias_totales.addCell(new PdfPCell(new Phrase("Segundo Periodo", normalFont)));
		
		for(int i=0;i<2;i++)
		{
		Map<String,Object> map_cat = new HashMap<String, Object>();	
		if(i==0)
			map_cat.put("categoria","Primer Periodo");
		else
			map_cat.put("categoria","Segundo Periodo");
		
		List<Double> lista_valores = new ArrayList<Double>();

		//for each complejo
		for (Map<String, Object> map_complex : arreglo_maps_por_complejo) {		
			valores_ejeX.add((String)map_complex.get("nombre_complejo"));
			if(i==0)
				{
				lista_valores.add((Double)map_complex.get("total_primer_periodo"));
				tabla_asistencias_totales.addCell(new PdfPCell(new Phrase((String)map_complex.get("nombre_complejo"),normalFont)));
				tabla_asistencias_totales.addCell(new PdfPCell(new Phrase("" + Util.formatNumber((Double)map_complex.get("total_primer_periodo")),FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD,new Color(255,0,0)))));
				tabla_asistencias_totales.addCell(new PdfPCell(new Phrase("" + Util.formatNumber((Double)map_complex.get("total_segundo_periodo")), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD,new Color(255,0,0)))));
				}
			else
				lista_valores.add((Double)map_complex.get("total_segundo_periodo"));
			
		}
		map_cat.put("valores", lista_valores);
		lista_de_maps_grafico.add(map_cat);
		}
		pdf.add(tabla_asistencias_totales);
		
		//crear garfico
		JFreeChart chart = GraficosPDF.getBarChart(" ",ejeX,ejeY,lista_de_maps_grafico,valores_ejeX,true);
		
		PdfContentByte cb = writer.getDirectContent();
		PdfTemplate tp = cb.createTemplate(500, 300);
		Graphics2D g2d = tp.createGraphics(500, 300, new DefaultFontMapper());
		Rectangle2D r2d = new Rectangle2D.Double(0, 0, 500, 300);
		chart.draw(g2d, r2d);
		Image jpg = Image.getInstance(tp);
		pdf.add(jpg);
		//fin grafico
		
		//:::::::::::::::
		valores_ejeX.clear();
		for (Map<String, Object> map_complex : arreglo_maps_por_complejo) {
			lista_de_maps_grafico.clear();
			pdf.newPage();
			pdf.add(new Paragraph((String)map_complex.get("nombre_complejo"),normalFont));
			pdf.add(new Paragraph(" "));
			
			PdfPTable tabla_asistencia_diarias= new PdfPTable(lista_dias_primer_periodo.size()+1);;
		
			tabla_asistencia_diarias.addCell(new PdfPCell(new Phrase("Periodos/Tiempo", smallFont)));
			
			for (String string : lista_dias_primer_periodo) {
				try{
					tabla_asistencia_diarias.addCell(new PdfPCell(new Phrase(string, smallFont)));	
					valores_ejeX.add(string);
				}catch(Exception e){}
			}
			
			List<Double> lista_asistencia_diario = (List<Double>)map_complex.get("por_dias_primer_periodo");
			List<Double> lista_asistencia_diario2 = (List<Double>)map_complex.get("por_dias_segundo_periodo");
			
			tabla_asistencia_diarias.addCell(new PdfPCell(new Phrase("Primer Periodo",smallFont)));
			for (Double valor_diario : lista_asistencia_diario) {
				//SIGNOOOOOOOOOOOOO
				tabla_asistencia_diarias.addCell(new PdfPCell(new Phrase(simbol + Util.formatNumber(valor_diario), smallFont)));
			}
			tabla_asistencia_diarias.addCell(new PdfPCell(new Phrase("Segundo Periodo",smallFont)));
			for (Double valor_diario : lista_asistencia_diario2) {
				//SIGNOOOOOOOOOOOOO
				tabla_asistencia_diarias.addCell(new PdfPCell(new Phrase(simbol + Util.formatNumber(valor_diario), smallFont)));	
			}
			
			tabla_asistencia_diarias.setWidthPercentage(100f);
			pdf.add(tabla_asistencia_diarias);
			
			
			for(int j=0;j<2;j++){
				Map<String,Object> map_graf_pdf = new HashMap<String, Object>();
				List<Double> lista_valores = new ArrayList<Double>();
				List<Double> lista = null;
				
				if(j==0)
					{
					map_graf_pdf.put("categoria","Primer Periodo");
					lista = (List<Double>)map_complex.get("por_dias_primer_periodo");
					}
				else
					{
					map_graf_pdf.put("categoria","Segundo Periodo");
					lista = (List<Double>)map_complex.get("por_dias_segundo_periodo");
					}				
				
				for (Double ingreso_dia : lista)
				{
					try{
					lista_valores.add(ingreso_dia.doubleValue());
					}catch(Exception e){}
				}
				map_graf_pdf.put("valores", lista_valores);		
				lista_de_maps_grafico.add(map_graf_pdf);
			}
			
				
			JFreeChart chart3 = GraficosPDF.getLineChart(" ",ejeX2,ejeY2,lista_de_maps_grafico,valores_ejeX,true);
			PdfTemplate tp3 = cb.createTemplate(500, 300);
			Graphics2D g2d3 = tp3.createGraphics(500, 300, new DefaultFontMapper());
			Rectangle2D r2d3 = new Rectangle2D.Double(0, 0, 500, 300);
			chart3.draw(g2d3, r2d3);
			Image jpg3 = Image.getInstance(tp3);
			pdf.add(jpg3);
			
		}
			/*
			int fin=0;
			int bloques = (int) Math.ceil((lista_dias_primer_periodo.size()/12));
			int dias_q_lleva=0;
			int dias_q_lleva_2=0;
			for(int i=0;i<bloques+1;i++)
			{
				dias_q_lleva_2=dias_q_lleva;
				
				pdf.add(new Paragraph(" "));
				
				PdfPTable tabla_asistencia_diarias;
				if(bloques==i)
					{
					fin=lista_dias_primer_periodo.size();
					tabla_asistencia_diarias = new PdfPTable(lista_dias_primer_periodo.size()-dias_q_lleva+1);
					tabla_asistencia_diarias.addCell(new PdfPCell(new Phrase("Periodo 1", smallFont)));
					}
				else
				{
					fin=11+dias_q_lleva;
					tabla_asistencia_diarias_primer = new PdfPTable(12);
					tabla_asistencia_diarias_primer.addCell(new PdfPCell(new Phrase("Complejo", smallFont)));
				}
				
				
				for (int x=dias_q_lleva;x<fin;x++) {
					try{
						tabla_asistencia_diarias_primer.addCell(new PdfPCell(new Phrase(lista_dias_primer_periodo.get(x), smallFont)));	
						valores_ejeX.add(lista_dias_primer_periodo.get(x));
						dias_q_lleva=x+1;
					}catch(Exception e){}
				}
				
				
				for (Map<String, Object> map_complex : arreglo_maps_por_complejo) {	
					tabla_asistencia_diarias_primer.addCell(new PdfPCell(new Phrase((String)map_complex.get("nombre_complejo"), smallFont)));
					List<Double> lista_asistencia_diario = (List<Double>)map_complex.get("por_dias_primer_periodo");
				
					for (int x=dias_q_lleva_2;x<fin;x++) 
					{
						//VER LO DEL SIGNIO PESO
						tabla_asistencia_diarias_primer.addCell(new PdfPCell(new Phrase("$" + Util.formatNumber(lista_asistencia_diario.get(x)), smallFont)));	
					}
						
				}
					pdf.close();*/
	
	}
	
	/**
	 * Maneja los datos del informe de ingresos, asistencia e ingresos por RM en un periodo para lanzarlos al pdf.
	 * Ver AsistenciaIngresoPeriodoRMControler para entender como se generan los datos
	 * 
	 * @param pdf documento que se usa para generar el pdf
	 * @param map datos
	 * @param servletOutputStream 
	 * @param writer
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
			
	@SuppressWarnings("unchecked")
	private void asistenciaIngresoRM(Document pdf, Map<String, Object> map, ServletOutputStream servletOutputStream, PdfWriter writer) throws FileNotFoundException, DocumentException {

		Map<String, Object> map_map = (Map<String, Object>)map.get("map");
				
		PdfContentByte cb = writer.getDirectContent();
		
		//Titulo
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100f);
		PdfPCell cell = new PdfPCell(new Phrase("Ingreso y Asistencia RM", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD, new Color(255, 255, 255))));
		cell.setBackgroundColor(new Color(0,0,0));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		pdf.add(table);
		//end
		
		/* graficos contiene un map por cada complejo al cual se le esta calculando el ticket promedio.
		 * Cada map contiene el nombre del complejo, el (long)ticketPromedio y una lista de objetos PrecioAsistencia
		 * con el precio(clase) y su respectiva asistencia. 
		 */
		
		pdf.add(new Paragraph("Desde: " + map_map.get("desde"),normalFont));
		pdf.add(new Paragraph("Hasta:  " + map_map.get("hasta"),normalFont));
		
		String[][] tablaIng = (String[][])map_map.get("tablaIng");
		String[][] tablaAsi = (String[][])map_map.get("tablaAsi");
		String[][] tablaConf = (String[][])map_map.get("tablaConf");
		
		int tam = tablaIng.length;
		if(tam<1)
			tam = 1;
		int ancho = 400;
		int alto = 100+45*tam;
		
		//Ingresos
		if(tablaIng.length>0){
			//subtitulo: Ingresos subrayado
			pdf.add(new Paragraph(" "));
			Paragraph subtitulo1 = new Paragraph("Ingresos",FontFactory.getFont(FontFactory.HELVETICA_BOLD,12,Font.UNDERLINE));
			subtitulo1.setAlignment(Element.ALIGN_CENTER);
			pdf.add(subtitulo1);
			pdf.add(new Paragraph(" "));
			
			//Preparar Grafico
			List<String> catFilasIng = new ArrayList<String>();
			List<String> catColumnasIng = new ArrayList<String>();
			catFilasIng.add("RM");
			catFilasIng.add("Total sin RM");
						
			Map<String, Map<String, Double>> datosIng = new HashMap<String, Map<String, Double>>();
			
			Map<String, Double> rmIng = new HashMap<String, Double>();
			datosIng.put("RM", rmIng);
			Map<String, Double> noRmIng = new HashMap<String, Double>();
			datosIng.put("Total sin RM", noRmIng);
			
			for (int i=0; i < tablaIng.length; i++){

				String complejo = tablaIng[i][AsistenciaIngresoPeriodoRMController.COMP];
				catColumnasIng.add(complejo);
				
				String ingresoNoRm = tablaIng[i][AsistenciaIngresoPeriodoRMController.NO_RM];
				ingresoNoRm = (ingresoNoRm.replace("$", "")).replace(".", "");
				noRmIng.put(complejo, Double.parseDouble(ingresoNoRm));
				
				String ingresoRm = tablaIng[i][AsistenciaIngresoPeriodoRMController.RM];
				ingresoRm = (ingresoRm.replace("$", "")).replace(".", "");
				rmIng.put(complejo, Double.parseDouble(ingresoRm));
				
				log.debug("ingreso c: "+complejo+" no RM: "+ingresoNoRm +" RM: "+ingresoRm);
			}
			
			//crear garfico
			JFreeChart chart1 = GraficosPDF.getStackedBar("Ingresos", "Complejos", "Ingresos en pesos", catFilasIng, catColumnasIng, datosIng, true);
			PdfTemplate tp1 = cb.createTemplate(ancho, alto);
			Graphics2D g2d1 = tp1.createGraphics(ancho, alto, new DefaultFontMapper());
			Rectangle2D r2d1 = new Rectangle2D.Double(0, 0, ancho, alto);
			chart1.draw(g2d1, r2d1);
			Image jpg1 = Image.getInstance(tp1);
			pdf.add(jpg1);
			pdf.add(new Paragraph(" "));
			
			//crear tabla
			PdfPTable tablaIngreso= new PdfPTable(tablaIng[0].length);
				
			tablaIngreso.addCell(new PdfPCell(new Phrase("Complejo",smallFont)));
			tablaIngreso.addCell(new PdfPCell(new Phrase("Total",smallFont)));
			tablaIngreso.addCell(new PdfPCell(new Phrase("RM",smallFont)));
			tablaIngreso.addCell(new PdfPCell(new Phrase("Total sin RM",smallFont)));
			tablaIngreso.addCell(new PdfPCell(new Phrase("% RM",smallFont)));
			 
			for (int i=0; i < tablaIng.length; i++){
				for(int j=0; j < tablaIng[i].length; j++){
					tablaIngreso.addCell(new PdfPCell(new Phrase(tablaIng[i][j], smallFont)));
				}
			}
			tablaIngreso.setWidthPercentage(100f);
			pdf.add(tablaIngreso);
		}
		
		//Asistencia
		if(tablaAsi.length>0){
			pdf.newPage();
			//subtitulo: Asistencia subrayado
			pdf.add(new Paragraph(" "));
			Paragraph subtitulo2 = new Paragraph("Asistencia",FontFactory.getFont(FontFactory.HELVETICA_BOLD,12,Font.UNDERLINE));
			subtitulo2.setAlignment(Element.ALIGN_CENTER);
			pdf.add(subtitulo2);
			pdf.add(new Paragraph(" "));

			//Preparar Grafico
			List<String> catFilasAsi = new ArrayList<String>();
			List<String> catColumnasAsi = new ArrayList<String>();
			catFilasAsi.add("RM");
			catFilasAsi.add("Total sin RM");
						
			Map<String, Map<String, Double>> datosAsi = new HashMap<String, Map<String, Double>>();
			
			Map<String, Double> rmAsi = new HashMap<String, Double>();
			datosAsi.put("RM", rmAsi);
			Map<String, Double> noRmAsi = new HashMap<String, Double>();
			datosAsi.put("Total sin RM", noRmAsi);
			
			for (int i=0; i < tablaIng.length; i++){

				String complejo = tablaAsi[i][AsistenciaIngresoPeriodoRMController.COMP];
				catColumnasAsi.add(complejo);
				
				String asistenciaNoRm = tablaAsi[i][AsistenciaIngresoPeriodoRMController.NO_RM];
				asistenciaNoRm = (asistenciaNoRm.replace("$", "")).replace(".", "");
				noRmAsi.put(complejo, Double.parseDouble(asistenciaNoRm));
				
				String asistenciaRm = tablaAsi[i][AsistenciaIngresoPeriodoRMController.RM];
				asistenciaRm = (asistenciaRm.replace("$", "")).replace(".", "");
				rmAsi.put(complejo, Double.parseDouble(asistenciaRm));
				
				log.debug("asistencia c: "+complejo+" no RM: "+asistenciaNoRm +" RM: "+asistenciaRm);
			}
			
			//crear garfico
			JFreeChart chart2 = GraficosPDF.getStackedBar("Asistencia", "Complejos", "Asistencia (personas)", catFilasAsi, catColumnasAsi, datosAsi, true);
			PdfTemplate tp2 = cb.createTemplate(ancho, alto);
			Graphics2D g2d2 = tp2.createGraphics(ancho, alto, new DefaultFontMapper());
			Rectangle2D r2d2 = new Rectangle2D.Double(0, 0, ancho, alto);
			chart2.draw(g2d2, r2d2);
			Image jpg2 = Image.getInstance(tp2);
			pdf.add(jpg2);
			pdf.add(new Paragraph(" "));
			
			PdfPTable tablaAsistencia= new PdfPTable(tablaAsi[0].length);
				
			tablaAsistencia.addCell(new PdfPCell(new Phrase("Complejo",smallFont)));
			tablaAsistencia.addCell(new PdfPCell(new Phrase("Total",smallFont)));
			tablaAsistencia.addCell(new PdfPCell(new Phrase("RM",smallFont)));
			tablaAsistencia.addCell(new PdfPCell(new Phrase("Total sin RM",smallFont)));
			tablaAsistencia.addCell(new PdfPCell(new Phrase("% RM",smallFont)));
			 
			for (int i=0; i < tablaAsi.length; i++){
				for(int j=0; j < tablaAsi[i].length; j++){
					tablaAsistencia.addCell(new PdfPCell(new Phrase(tablaAsi[i][j], smallFont)));
				}
			}
			tablaAsistencia.setWidthPercentage(100f);
			pdf.add(tablaAsistencia);
		}
	
		//Confiteria
		if(tablaConf.length>0){
			pdf.newPage();
			//subtitulo: Asistencia subrayado
			pdf.add(new Paragraph(" "));
			Paragraph subtitulo2 = new Paragraph("Ingresos de Confiteria",FontFactory.getFont(FontFactory.HELVETICA_BOLD,12,Font.UNDERLINE));
			subtitulo2.setAlignment(Element.ALIGN_CENTER);
			pdf.add(subtitulo2);
			pdf.add(new Paragraph(" "));

			//Preparar Grafico
			List<String> catFilasConf = new ArrayList<String>();
			List<String> catColumnasConf = new ArrayList<String>();
			catFilasConf.add("RM");
			catFilasConf.add("Total sin RM");
						
			Map<String, Map<String, Double>> datosConf = new HashMap<String, Map<String, Double>>();
			
			Map<String, Double> rmConf = new HashMap<String, Double>();
			datosConf.put("RM", rmConf);
			Map<String, Double> noRmConf = new HashMap<String, Double>();
			datosConf.put("Total sin RM", noRmConf);
			
			for (int i=0; i < tablaConf.length; i++){

				String complejo = tablaConf[i][AsistenciaIngresoPeriodoRMController.COMP];
				catColumnasConf.add(complejo);
				
				String confNoRm = tablaConf[i][AsistenciaIngresoPeriodoRMController.NO_RM];
				confNoRm = (confNoRm.replace("$", "")).replace(".", "");
				noRmConf.put(complejo, Double.parseDouble(confNoRm));
				
				String confRm = tablaConf[i][AsistenciaIngresoPeriodoRMController.RM];
				confRm = (confRm.replace("$", "")).replace(".", "");
				rmConf.put(complejo, Double.parseDouble(confRm));
				
				log.debug("ingreso conf c: "+complejo+" no RM: "+confNoRm +" RM: "+confRm);
			}
			
			//crear garfico
			JFreeChart chart3 = GraficosPDF.getStackedBar("Ingresos de Confiteria", "Complejos", "Ingresos confiteria en pesos", catFilasConf, catColumnasConf, datosConf, true);
			PdfTemplate tp3 = cb.createTemplate(ancho, alto);
			Graphics2D g2d3 = tp3.createGraphics(ancho, alto, new DefaultFontMapper());
			Rectangle2D r2d3 = new Rectangle2D.Double(0, 0, ancho, alto);
			chart3.draw(g2d3, r2d3);
			Image jpg3 = Image.getInstance(tp3);
			pdf.add(jpg3);
			pdf.add(new Paragraph(" "));
			
			PdfPTable tablaConfiteria= new PdfPTable(tablaConf[0].length);
				
			tablaConfiteria.addCell(new PdfPCell(new Phrase("Complejo",smallFont)));
			tablaConfiteria.addCell(new PdfPCell(new Phrase("Total",smallFont)));
			tablaConfiteria.addCell(new PdfPCell(new Phrase("RM",smallFont)));
			tablaConfiteria.addCell(new PdfPCell(new Phrase("Total sin RM",smallFont)));
			tablaConfiteria.addCell(new PdfPCell(new Phrase("% RM",smallFont)));
			 
			for (int i=0; i < tablaConf.length; i++){
				for(int j=0; j < tablaConf[i].length; j++){
					tablaConfiteria.addCell(new PdfPCell(new Phrase(tablaConf[i][j], smallFont)));
				}
			}
			tablaConfiteria.setWidthPercentage(100f);
			pdf.add(tablaConfiteria);
		}
		
		pdf.close();
	}
	
	public void setLog(Logger log) {
		this.log = log;
	}

	public Logger getLog() {
		return log;
	}


}