/**
 * 
 */
package cl.zpricing.avant.web.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import cl.zpricing.avant.model.Usuario;
import cl.zpricing.avant.model.reports.PrecioAsistencia;
import cl.zpricing.avant.util.Util;

/**
 * <b>Descripci�n de la Clase</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 03-02-2009 Julio Andr�s Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class generadorExcel extends AbstractExcelView {

	private HSSFFont boldFont = null;
	private HSSFFont headerWhiteFont = null;
	private HSSFFont boldFontUnder = null;
	private HSSFCellStyle tablaStyle = null;
	private HSSFCellStyle tablaStyleBold = null;
	private HSSFCellStyle boldFontStyle = null;
	private HSSFCellStyle tablaHeaderStyle = null;
	private HSSFCellStyle fondoClaroStyle = null;
	private HSSFCellStyle tablaIngresoStyle = null;
	private HSSFCellStyle tablaUnderlineStyle = null;

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map arg0, HSSFWorkbook arg1,
			HttpServletRequest request, HttpServletResponse arg3)
			throws Exception {

		// se obtiene el tipo de reporte mediante el par�metro tipo
		// este par�metro est� adherido al link en el jsp que llama a
		// generarGrafico
		String tipo = request.getParameter("tipo");

		// agrega un encabezado estandar con la fecha, hora y autor del reporte.
		ModelAndView mv = (ModelAndView) request.getSession().getAttribute(
				"para_reportes");

		Map<String, Object> map = mv.getModel();
		ArrayList<Map<String, Object>> arreglo_maps_por_complejo = (ArrayList<Map<String, Object>>) map
				.get("arreglo_maps_por_complejo");
		List<Map<String, Object>> graficos = null;

		HSSFSheet sheet = arg1.createSheet("Asistencias por Periodo");
		sheet.setDisplayGridlines(false);// quitar l�neas de cuadricula del
											// excel
		sheet.setColumnWidth(1, 8000);

		creacionEstilos(arg1);

		HSSFRow row = sheet.createRow(1);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("Reporte:");
		cell.setCellStyle(fondoClaroStyle);

		cell = row.createCell(1);
		if (tipo.compareTo("0") == 0)
			cell.setCellValue("ASISTENCIAS POR PERIODO");
		if (tipo.compareTo("1") == 0)
			cell.setCellValue("INGRESOS POR PERIODO");
		if (tipo.compareTo("2") == 0) {
			cell.setCellValue("TICKET PROMEDIO");
			Map<String, Object> map_map = (Map<String, Object>) map.get("map");
			graficos = (List<Map<String, Object>>) map_map.get("graficos");
		}
		if (tipo.compareTo("3") == 0)
			cell.setCellValue("INGRESOS POR CONFITER�A");
		if (tipo.compareTo("4") == 0)
			cell.setCellValue("INGRESOS Y ASISTENCIA RM");
		cell.setCellStyle(fondoClaroStyle);

		if (tipo.compareTo("4") != 0) {
			row = sheet.createRow(3);
			cell = row.createCell(0);
			cell.setCellValue("Complejos:");
			cell.setCellStyle(fondoClaroStyle);

			cell = row.createCell(1);
			String complejos = "";
			if (tipo.compareTo("2") == 0) {
				for (Map<String, Object> map_complex : graficos) {
					complejos = complejos
							+ (String) map_complex.get("complejo") + ", ";
				}
			} else {
				for (Map<String, Object> map_complex : arreglo_maps_por_complejo) {
					complejos = complejos
							+ (String) map_complex.get("nombre_complejo")
							+ ", ";
				}
			}
			complejos = complejos.substring(0, complejos.length() - 2) + ".";
			cell.setCellValue(complejos);
			cell.setCellStyle(fondoClaroStyle);
		}

		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue("Empresa:");
		cell.setCellStyle(fondoClaroStyle);

		cell = row.createCell(1);
		cell.setCellValue("Hoyst");
		cell.setCellStyle(fondoClaroStyle);

		row = sheet.createRow(4);
		cell = row.createCell(0);
		cell.setCellStyle(fondoClaroStyle);
		cell.setCellValue("Fecha:");
		cell = row.createCell(1);
		cell.setCellValue(""
				+ Util.DateToString(Calendar.getInstance().getTime()) + " "
				+ Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":"
				+ Calendar.getInstance().get(Calendar.MINUTE));
		cell.setCellStyle(fondoClaroStyle);

		row = sheet.createRow(5);
		cell = row.createCell(0);
		cell.setCellValue("Autor:");
		cell.setCellStyle(fondoClaroStyle);

		cell = row.createCell(1);
		cell.setCellStyle(fondoClaroStyle);
		cell.setCellValue(((Usuario) request.getSession().getAttribute(
				"Usuario")).getNombreCompleto());

		if (tipo.compareTo("0") == 0)
			diasPorPeriodo(arg1, map, sheet, 0);
		if (tipo.compareTo("1") == 0)
			diasPorPeriodo(arg1, map, sheet, 1);
		if (tipo.compareTo("2") == 0)
			ticketPromedio(arg1, map, sheet, 2);
		if (tipo.compareTo("3") == 0)
			diasPorPeriodo(arg1, map, sheet, 3);
		if (tipo.compareTo("4") == 0)
			asistenciaIngresoRM(arg1, map, sheet, 4);
	}

	@SuppressWarnings("unchecked")
	private void ticketPromedio(HSSFWorkbook arg1, Map<String, Object> map,
			HSSFSheet sheet, int tipo) {

		Map<String, Object> map_map = (Map<String, Object>) map.get("map");
		List<Map<String, Object>> graficos = (List<Map<String, Object>>) map_map
				.get("graficos");

		HSSFRow row = sheet.createRow(7);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(boldFontStyle);
		cell.setCellValue("Desde:");
		cell = row.createCell(1);
		cell.setCellStyle(boldFontStyle);
		cell.setCellValue((String) map_map.get("desde"));

		row = sheet.createRow(8);
		cell = row.createCell(0);
		cell.setCellStyle(boldFontStyle);
		cell.setCellValue("Hasta:");
		cell = row.createCell(1);
		cell.setCellStyle(boldFontStyle);
		cell.setCellValue((String) map_map.get("hasta"));

		int row_inicial = 10;
		for (Map<String, Object> map_grafico : graficos) {

			row = sheet.createRow(row_inicial);
			cell = row.createCell(1);
			cell.setCellStyle(tablaUnderlineStyle);
			cell.setCellValue((String) map_grafico.get("complejo"));

			row = sheet.createRow(row_inicial + 2);
			cell = row.createCell(1);
			cell.setCellStyle(boldFontStyle);
			cell.setCellValue("Ticket Promedio: "
					+ (Long) map_grafico.get("ticketProm"));

			row = sheet.createRow(row_inicial + 4);
			cell = row.createCell(1);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("Precio");

			int i = 2;
			for (PrecioAsistencia precioAsistencia : (List<PrecioAsistencia>) map_grafico
					.get("precioAsistencia")) {
				cell = row.createCell(i);
				cell.setCellStyle(tablaIngresoStyle);
				cell.setCellValue(precioAsistencia.getPrecio());
				i++;
			}

			row = sheet.createRow(row_inicial + 5);
			cell = row.createCell(1);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("Asistencia");

			i = 2;
			for (PrecioAsistencia precioAsistencia : (List<PrecioAsistencia>) map_grafico
					.get("precioAsistencia")) {
				cell = row.createCell(i);
				cell.setCellStyle(tablaStyle);
				cell.setCellValue(precioAsistencia.getAsistencia());
				i++;
			}

			row_inicial = row_inicial + 7;
		}
	}

	@SuppressWarnings("unchecked")
	private void diasPorPeriodo(HSSFWorkbook arg1, Map<String, Object> map,
			HSSFSheet sheet, int tipo) {

		ArrayList<Map<String, Object>> arreglo_maps_por_complejo = (ArrayList<Map<String, Object>>) map
				.get("arreglo_maps_por_complejo");
		List<String> lista_dias_primer_periodo = (List<String>) map
				.get("lista_dias_primer_periodo");
		@SuppressWarnings("unused")
		List<String> lista_dias_segundo_periodo = (List<String>) map
				.get("lista_dias_segundo_periodo");

		int row_inicial = 7;
		double total = 0;
		for (Map<String, Object> map_complex : arreglo_maps_por_complejo) {

			HSSFRow row = sheet.createRow(row_inicial);
			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(boldFontStyle);

			cell.setCellValue((String) map_complex.get("nombre_complejo"));

			row = sheet.createRow(row_inicial + 2);
			cell = row.createCell(1);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("Periodo/Tiempo");

			int i = 2;
			for (String dia : lista_dias_primer_periodo) {

				cell = row.createCell(i);
				cell.setCellStyle(tablaHeaderStyle);
				cell.setCellValue(dia);
				i++;
			}
			cell = row.createCell(i);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("Total");

			List<Double> lista_valores_diario = null;
			lista_valores_diario = (List<Double>) map_complex
					.get("por_dias_primer_periodo");

			i = 2;
			row = sheet.createRow(row_inicial + 3);
			cell = row.createCell(1);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue((String) map.get("fecha_inicio_primer_periodo")
					+ " al " + (String) map.get("fecha_fin_primer_periodo"));
			for (Double valor_dia : lista_valores_diario) {
				cell = row.createCell(i);

				if (tipo == 0)
					cell.setCellStyle(tablaStyle);
				if (tipo == 1 || tipo == 3)
					cell.setCellStyle(tablaIngresoStyle);

				cell.setCellValue(valor_dia);

				total = total + valor_dia;
				i++;
			}
			cell = row.createCell(i);
			if (tipo == 0)
				cell.setCellStyle(tablaStyle);
			if (tipo == 1 || tipo == 3)
				cell.setCellStyle(tablaIngresoStyle);
			cell.setCellValue(total);

			// segundo periodo en mismo complejo para tercera fila tabla
			lista_valores_diario = (List<Double>) map_complex
					.get("por_dias_segundo_periodo");
			i = 2;
			total = 0;
			row = sheet.createRow(row_inicial + 4);
			cell = row.createCell(1);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue((String) map.get("fecha_inicio_segundo_periodo")
					+ " al " + (String) map.get("fecha_fin_segundo_periodo"));
			for (Double valor_dia : lista_valores_diario) {
				cell = row.createCell(i);

				if (tipo == 0)
					cell.setCellStyle(tablaStyle);
				if (tipo == 1 || tipo == 3)
					cell.setCellStyle(tablaIngresoStyle);

				cell.setCellValue(valor_dia);

				total = total + valor_dia;
				i++;
			}
			cell = row.createCell(i);
			if (tipo == 0)
				cell.setCellStyle(tablaStyle);
			if (tipo == 1 || tipo == 3)
				cell.setCellStyle(tablaIngresoStyle);
			cell.setCellValue(total);

			row_inicial = row_inicial + 7;
			total = 0;
		}

	}

	/**
	 * @param arg1
	 */
	private void creacionEstilos(HSSFWorkbook arg1) {
		// Creacion de estilos
		boldFont = arg1.createFont();// creacion fuente negrita
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		boldFontUnder = arg1.createFont();// creacion fuente negrita
		boldFontUnder.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldFontUnder.setUnderline((byte) 1);

		headerWhiteFont = arg1.createFont();// creacion fuente negrita blanca
											// para header
		headerWhiteFont.setColor(HSSFColor.WHITE.index);

		tablaStyle = arg1.createCellStyle();// estilo para tablas
		tablaStyle.setBorderLeft(CellStyle.BORDER_THIN);// borde izquierdo tabla
		tablaStyle.setBorderRight(CellStyle.BORDER_THIN);// borde derecho tabla
		tablaStyle.setBorderBottom(CellStyle.BORDER_THIN);// borde abajo tabla
		tablaStyle.setBorderTop(CellStyle.BORDER_THIN);// borde arriba tabla
		tablaStyle.setDataFormat((short) 3);// DATA FORMAT 5 ES DINERO!

		tablaIngresoStyle = arg1.createCellStyle();// estilo para tablas
		tablaIngresoStyle.cloneStyleFrom(tablaStyle);
		tablaIngresoStyle.setDataFormat((short) 5);

		tablaStyleBold = arg1.createCellStyle();// estilo para tablas celda bold
		tablaStyleBold.cloneStyleFrom(tablaStyle);
		tablaStyleBold.setFont(boldFont);

		boldFontStyle = arg1.createCellStyle();// estilo fuente negrita
		boldFontStyle.setFont(boldFont);

		tablaHeaderStyle = arg1.createCellStyle();// estilo para tablas celda
													// bold
		tablaHeaderStyle.cloneStyleFrom(tablaStyleBold);
		tablaHeaderStyle
				.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		tablaHeaderStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		tablaHeaderStyle.setFont(headerWhiteFont);

		fondoClaroStyle = arg1.createCellStyle();
		fondoClaroStyle.setFillForegroundColor((short) 10);
		fondoClaroStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		fondoClaroStyle.setFont(boldFont);

		tablaUnderlineStyle = arg1.createCellStyle();// estilo para tablas
		tablaUnderlineStyle.cloneStyleFrom(boldFontStyle);
		tablaUnderlineStyle.setDataFormat((short) 4);
		tablaUnderlineStyle.setFont(boldFontUnder);
		// ...
	}

	/**
	 * Maneja la parte del excel que tiene que ver con el informe de RM. Ver
	 * AsistenciaIngresoPeriodoRMController para ver como se generan los datos
	 * 
	 * @param arg1
	 *            workbook
	 * @param map
	 *            datos
	 * @param sheet
	 *            sheet
	 * @param tipo
	 *            tipo
	 */
	@SuppressWarnings("unchecked")
	private void asistenciaIngresoRM(HSSFWorkbook arg1,
			Map<String, Object> map, HSSFSheet sheet, int tipo) {

		Map<String, Object> map_map = (Map<String, Object>) map.get("map");

		tablaStyle = arg1.createCellStyle();// estilo para tablas
		tablaStyle.setBorderLeft(CellStyle.BORDER_THIN);// borde izquierdo tabla
		tablaStyle.setBorderRight(CellStyle.BORDER_THIN);// borde derecho tabla
		tablaStyle.setBorderBottom(CellStyle.BORDER_THIN);// borde abajo tabla
		tablaStyle.setBorderTop(CellStyle.BORDER_THIN);// borde arriba tabla

		HSSFRow row = sheet.createRow(7);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(boldFontStyle);
		cell.setCellValue("Desde:");
		cell = row.createCell(1);
		cell.setCellStyle(boldFontStyle);
		cell.setCellValue((String) map_map.get("desde"));

		row = sheet.createRow(8);
		cell = row.createCell(0);
		cell.setCellStyle(boldFontStyle);
		cell.setCellValue("Hasta:");
		cell = row.createCell(1);
		cell.setCellStyle(boldFontStyle);
		cell.setCellValue((String) map_map.get("hasta"));

		int col_tabla_inicial = 2;
		int row_inicial = 10;

		String[][] tablaIng = (String[][]) map_map.get("tablaIng");
		String[][] tablaAsi = (String[][]) map_map.get("tablaAsi");
		String[][] tablaConf = (String[][]) map_map.get("tablaConf");
		// Tabla Ingresos
		if (tablaIng.length > 0) {
			// Titulo
			row = sheet.createRow(row_inicial);
			cell = row.createCell(col_tabla_inicial);
			cell.setCellStyle(tablaUnderlineStyle);
			cell.setCellValue("Ingresos");

			// Encabezado tabla
			row_inicial += 2;
			row = sheet.createRow(row_inicial);
			cell = row.createCell(AsistenciaIngresoPeriodoRMController.COMP
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("Complejo");

			cell = row.createCell(AsistenciaIngresoPeriodoRMController.TOTAL
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("Total");

			cell = row.createCell(AsistenciaIngresoPeriodoRMController.RM
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("RM");

			cell = row.createCell(AsistenciaIngresoPeriodoRMController.NO_RM
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("Total sin RM");

			cell = row.createCell(AsistenciaIngresoPeriodoRMController.PER_RM
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("% RM");

			// Datos Tabla
			for (int i = 0; i < tablaIng.length; i++) {
				row = sheet.createRow(++row_inicial);
				for (int j = 0; j < 0 + tablaIng[i].length; j++) {
					cell = row.createCell(col_tabla_inicial + j);
					String dato = tablaIng[i][j].replace("$", "").replace(".",
							"").replace("%", "").replace(",", ".");
					cell.setCellStyle(tablaStyle);
					if (j != AsistenciaIngresoPeriodoRMController.COMP) {
						try {
							cell.setCellValue(Double.parseDouble(dato));
						} catch (NumberFormatException e) {
							cell.setCellValue(0);
						}
					} else
						cell.setCellValue(dato);
				}
			}

		}

		// Tabla Asistencia
		if (tablaAsi.length > 0) {
			// Titulo
			row_inicial += 2;
			row = sheet.createRow(row_inicial);
			cell = row.createCell(col_tabla_inicial);
			cell.setCellStyle(tablaUnderlineStyle);
			cell.setCellValue("Asistencia");

			// Encabezado tabla
			row_inicial += 2;
			row = sheet.createRow(row_inicial);
			cell = row.createCell(AsistenciaIngresoPeriodoRMController.COMP
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("Complejo");

			cell = row.createCell(AsistenciaIngresoPeriodoRMController.TOTAL
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("Total");

			cell = row.createCell(AsistenciaIngresoPeriodoRMController.RM
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("RM");

			cell = row.createCell(AsistenciaIngresoPeriodoRMController.NO_RM
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("Total sin RM");

			cell = row.createCell(AsistenciaIngresoPeriodoRMController.PER_RM
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("% RM");

			// Datos Tabla
			for (int i = 0; i < tablaAsi.length; i++) {
				row = sheet.createRow(++row_inicial);
				for (int j = 0; j < 0 + tablaAsi[i].length; j++) {
					cell = row.createCell(col_tabla_inicial + j);
					cell.setCellStyle(tablaStyle);
					String dato = tablaAsi[i][j].replace("$", "").replace(".",
							"").replace("%", "").replace(",", ".");
					if (j != AsistenciaIngresoPeriodoRMController.COMP) {
						try {
							cell.setCellValue(Double.parseDouble(dato));
						} catch (NumberFormatException e) {
							cell.setCellValue(0);
						}
					} else
						cell.setCellValue(dato);
				}
			}

		}

		// Tabla Ingresos Confiteria
		if (tablaConf.length > 0) {
			// Titulo
			row_inicial += 2;
			row = sheet.createRow(row_inicial);
			cell = row.createCell(col_tabla_inicial);
			cell.setCellStyle(tablaUnderlineStyle);
			cell.setCellValue("Ingresos de Confiteria");

			// Encabezado tabla
			row_inicial += 2;
			row = sheet.createRow(row_inicial);
			cell = row.createCell(AsistenciaIngresoPeriodoRMController.COMP
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("Complejo");

			cell = row.createCell(AsistenciaIngresoPeriodoRMController.TOTAL
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("Total");

			cell = row.createCell(AsistenciaIngresoPeriodoRMController.RM
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("RM");

			cell = row.createCell(AsistenciaIngresoPeriodoRMController.NO_RM
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("Total sin RM");

			cell = row.createCell(AsistenciaIngresoPeriodoRMController.PER_RM
					+ col_tabla_inicial);
			cell.setCellStyle(tablaHeaderStyle);
			cell.setCellValue("% RM");

			// Datos Tabla
			for (int i = 0; i < tablaConf.length; i++) {
				row = sheet.createRow(++row_inicial);
				for (int j = 0; j < 0 + tablaConf[i].length; j++) {
					cell = row.createCell(col_tabla_inicial + j);
					cell.setCellStyle(tablaStyle);
					String dato = tablaConf[i][j].replace("$", "").replace(".",
							"").replace("%", "").replace(",", ".");
					if (j != AsistenciaIngresoPeriodoRMController.COMP) {
						try {
							cell.setCellValue(Double.parseDouble(dato));
						} catch (NumberFormatException e) {
							cell.setCellValue(0);
						}
					} else
						cell.setCellValue(dato);
				}
			}

		}
	}
}
