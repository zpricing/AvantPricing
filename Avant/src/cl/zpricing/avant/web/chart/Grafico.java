/**
 * 
 */
package cl.zpricing.avant.web.chart;

import java.text.NumberFormat;

import org.apache.log4j.Logger;

import cl.zpricing.avant.util.Regresion;
import cl.zpricing.avant.util.Util;

import com.infosoftglobal.fusioncharts.FusionChartsHelper;

/**
 * <b>Provee de funcionalidades para el manejo de dos series de datos: - Genera
 * el XML para ser desplegado con FusionCharts. - Calcula las regresiones y
 * genera sus XML para FusionCharts.</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 21-01-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class Grafico {

	private double[] x;
	private double[] y;
	private int id;
	private FusionChartsHelper colorHelper;
	private double pendiente;
	private double correlacion;
	private double interseccion;
	private String strXML;
	private int ancho;
	private String swf;
	private NumberFormat numberFormat;
	private NumberFormat numberFormat2;

	/**
	 * @return the swf
	 */
	public String getSwf() {
		return swf;
	}

	/**
	 * @param swf
	 *            the swf to set
	 */
	public void setSwf(String swf) {
		this.swf = swf;
	}

	/**
	 * @return the ancho
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * @param ancho
	 *            the ancho to set
	 */
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/**
	 * @return the strXML
	 */
	public String getStrXML() {
		return strXML;
	}

	/**
	 * @param strXML
	 *            the strXML to set
	 */
	public void setStrXML(String strXML) {
		this.strXML = strXML;
	}

	/**
	 * @return the interseccion
	 */
	public double getInterseccion() {
		return interseccion;
	}

	/**
	 * @param interseccion
	 *            the interseccion to set
	 */
	public void setInterseccion(double interseccion) {
		this.interseccion = interseccion;
	}

	/**
	 * @return the pendiente
	 */
	public double getPendiente() {
		return pendiente;
	}

	/**
	 * @param pendiente
	 *            the pendiente to set
	 */
	public void setPendiente(double pendiente) {
		this.pendiente = pendiente;
	}

	/**
	 * @return the correlacion
	 */
	public double getCorrelacion() {
		return correlacion;
	}

	/**
	 * @param correlacion
	 *            the correlacion to set
	 */
	public void setCorrelacion(double correlacion) {
		this.correlacion = correlacion;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public Grafico(double[] x, double[] y) {
		/**
		 * se cuentan valores distintos de cero
		 */
		int n = 0;
		for (int i = 0; i < x.length && i < y.length; i++)
			if ((int) x[i] != 0 && y[i] != 0)
				n++;
		/**
		 * se crean arrays auxiliares y se rellenan
		 */
		double[] aux_x = new double[n];
		double[] aux_y = new double[n];
		log.debug("tama�o array " + n);
		int h = 0;
		for (int i = 0; i < x.length && i < y.length; i++) {
			if ((int) x[i] != 0 && y[i] != 0) {
				log.debug("agregando (x,y) (" + x[i] + "," + y[i] + ")");
				aux_x[h] = x[i];
				aux_y[h++] = y[i];
			}
		}

		/**
		 * se ordena de menor a mayor el eje x y se asigna
		 */

		Util.quicksortDoble(aux_x, aux_y, 0, n - 1);
		this.x = aux_x;
		this.y = aux_y;

		/**
		 * se inicializa FusionChartsHelper para los colores
		 */
		colorHelper = new FusionChartsHelper();
		numberFormat = NumberFormat.getInstance();
		/**
		 * se inicializan las clases para el formato de n�meros
		 */
		numberFormat.setMaximumFractionDigits(2);
		numberFormat2 = NumberFormat.getInstance();
		numberFormat2.setMaximumFractionDigits(4);
		/**
		 * se setean los valores iniciales de alto y ancho del gr�fico
		 */

		this.ancho = aux_x.length * 10;
	}

	/**
	 * 
	 * M�todo que genera el XML de las series de datos para ser utilizado con
	 * Fusion Charts. Este XML tiene interpolaciones autom�ticas entre cada par
	 * de n�meros de la serie.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param ajustado
	 *            indica si el grafico se debe ajustar, es �til para cuando la
	 *            interpolaci�n genera muchos n�meros
	 * @param swf
	 *            archivo swf de FusionCharts a ser utilizado
	 * @param id
	 *            id del gr�fico de FusionCharts (para cada gr�fico de la vista
	 *            este n�mero DEBE ser distinto)
	 * @param caption
	 *            titulo del gr�fico
	 * @param numberPrefix
	 *            prefijo de los n�meros del eje y
	 * @param formatNumberScale
	 *            formato de n�meros
	 * @param decimalPrecision
	 *            n�meros de ceros a la derecha
	 * @param xAxisName
	 *            nombre del eje x
	 * @param yAxisName
	 *            nombre del eje y
	 * @since 1.0
	 */
	public void doStrXML(boolean ajustado, String swf, int id, String caption,
			String numberPrefix, String formatNumberScale,
			String decimalPrecision, String xAxisName, String yAxisName) {
		this.id = id;
		this.swf = swf;
		/**
		 * se calcula el m�ximo com�n divisor que nos dar� el tama�o de
		 * intervalo del eje x
		 */

		double mcd = Util.MCD2(this.x);

		StringBuilder strXML = new StringBuilder();
		StringBuilder categorias = new StringBuilder();
		StringBuilder data = new StringBuilder();

		strXML
				.append("<graph subcaption='Lineal por tramos' bgSWF='images/fusioncharts.jpg' showvalues='0' rotateNames='1' canvasBgColor='FFFFFF' canvasBgAlpha='20' canvasBorderColor='7B3F00' canvasBorderThickness='0' divLineColor='a82925' numdivlines='4' showgridbg='1' showhovercap='1' lineThickness='2' animation='1' caption='"
						+ caption
						+ "' xAxisName='"
						+ xAxisName
						+ "' yAxisName='"
						+ yAxisName
						+ "' numberPrefix='"
						+ numberPrefix
						+ "' formatNumberScale='"
						+ formatNumberScale
						+ "' decimalPrecision='"
						+ decimalPrecision + "'>");
		categorias.append("<categories>");
		data
				.append("<dataset showValue='1' alpha='100' anchorAlpha='0' lineThickness='2' seriesName='"
						+ yAxisName
						+ "' color='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBorderColor='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBgColor='"
						+ this.colorHelper.getFCColor() + "'>");

		double x_anterior = this.x[0];
		double y_anterior = this.y[0];
		double x = x_anterior + mcd;
		double y;
		double x_siguiente;
		double y_siguiente;
		categorias.append("<category name='"
				+ this.numberFormat.format(x_anterior) + "'/>");
		data.append("<set value='" + y_anterior + "'/>");

		for (int i = 1; i < this.x.length; i++) {

			x_siguiente = this.x[i];
			y_siguiente = this.y[i];
			if (x_siguiente == x_anterior)
				continue;

			while (x < x_siguiente) {
				categorias.append("<category/>");
				y = Util.interpolar(x_anterior, x_siguiente, y_anterior,
						y_siguiente, x);
				data.append("<set value='" + y + "'/>");
				x += mcd;
				this.ancho += 10;
			}

			if (x_siguiente != this.x[this.x.length - 1] && ajustado
					&& (int) x_siguiente == (int) x_anterior + mcd
					|| (int) x_siguiente == (int) x_anterior + 2 * mcd
					|| (int) x_siguiente == (int) x_anterior + 3 * mcd
					|| (int) x_siguiente == (int) x_anterior + 4 * mcd)
				categorias.append("<category/>");
			else
				categorias.append("<category name='"
						+ this.numberFormat.format(x_siguiente) + "'/>");
			data.append("<set value='" + y_siguiente + "'/>");
			log.debug("x siguiente,y_siguiente " + x_siguiente + ","
					+ y_siguiente + "agregando a strXML valores " + x_siguiente
					+ "," + y_siguiente);
			this.ancho += 10;
			x += mcd;
			x_anterior = x_siguiente;
			y_anterior = y_siguiente;

		}

		categorias.append("</categories>");
		data.append("</dataset>");

		strXML.append(categorias.toString());
		strXML.append(data.toString());
		strXML.append("</graph>");

		this.strXML = strXML.toString();
		if (this.ancho > 1000)
			this.ancho = 1000;

	}

	/**
	 * 
	 * M�todo que genera el XML de las series de datos para ser utilizado con
	 * Fusion Charts. Este XML tiene interpolaciones autom�ticas entre cada par
	 * de n�meros de la serie. Agrega adem�s la regresi�n lineal exponencial en
	 * el gr�fico
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param ajustado
	 *            indica si el grafico se debe ajustar, es �til para cuando la
	 *            interpolaci�n genera muchos n�meros
	 * @param swf
	 *            archivo swf de FusionCharts a ser utilizado
	 * @param id
	 *            id del gr�fico de FusionCharts (para cada gr�fico de la vista
	 *            este n�mero DEBE ser distinto)
	 * @param caption
	 *            titulo del gr�fico
	 * @param numberPrefix
	 *            prefijo de los n�meros del eje y
	 * @param formatNumberScale
	 *            formato de n�meros
	 * @param decimalPrecision
	 *            n�meros de ceros a la derecha
	 * @param xAxisName
	 *            nombre del eje x
	 * @param yAxisName
	 *            nombre del eje y
	 * @since 1.0
	 */
	public void doStrXMLExp(boolean ajustado, String swf, int id,
			String caption, String numberPrefix, String formatNumberScale,
			String decimalPrecision, String xAxisName, String yAxisName) {
		this.id = id;
		this.swf = swf;
		/**
		 * se calcula el m�ximo com�n divisor que nos dar� el tama�o de
		 * intervalo del eje x
		 */

		double mcd = Util.MCD2(this.x);

		StringBuilder strXML = new StringBuilder();
		StringBuilder categorias = new StringBuilder();
		StringBuilder data = new StringBuilder();
		StringBuilder dataReg = new StringBuilder();
		strXML
				.append("<graph subcaption='Exponencial R="
						+ this.numberFormat.format(this.correlacion)
						+ "' bgSWF='images/fusioncharts.jpg' showvalues='0' rotateNames='1' canvasBgColor='FFFFFF' canvasBgAlpha='20' canvasBorderColor='7B3F00' canvasBorderThickness='0' divLineColor='a82925' numdivlines='4' showgridbg='1' showhovercap='1' lineThickness='2' animation='1' caption='"
						+ caption + "' xAxisName='" + xAxisName
						+ "' yAxisName='" + yAxisName + "' numberPrefix='"
						+ numberPrefix + "' formatNumberScale='"
						+ formatNumberScale + "' decimalPrecision='"
						+ decimalPrecision + "'>");
		categorias.append("<categories>");
		data
				.append("<dataset showValue='1' alpha='100' anchorAlpha='0' lineThickness='2' seriesName='"
						+ yAxisName
						+ "' color='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBorderColor='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBgColor='"
						+ this.colorHelper.getFCColor() + "'>");
		dataReg
				.append("<dataset showValue='1' alpha='100' anchorAlpha='0' lineThickness='2' seriesName='Regresion' color='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBorderColor='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBgColor='"
						+ this.colorHelper.getFCColor() + "'>");
		double x_anterior = this.x[0];
		double y_anterior = this.y[0];
		double x = x_anterior + mcd;
		double y;
		double x_siguiente;
		double y_siguiente;
		categorias.append("<category name='"
				+ this.numberFormat.format(x_anterior) + "'/>");
		data.append("<set value='" + y_anterior + "'/>");
		dataReg.append("<set value='"
				+ Math.exp(this.interseccion + x_anterior * this.pendiente)
				+ "'/>");

		for (int i = 1; i < this.x.length; i++) {

			x_siguiente = this.x[i];
			y_siguiente = this.y[i];
			if (x_siguiente == x_anterior)
				continue;

			while (x < x_siguiente) {
				categorias.append("<category/>");
				y = Util.interpolar(x_anterior, x_siguiente, y_anterior,
						y_siguiente, x);
				data.append("<set value='" + y + "'/>");
				dataReg.append("<set value='"
						+ Math.exp(this.interseccion + x * this.pendiente)
						+ "'/>");
				x += mcd;
				this.ancho += 10;
			}

			if (x_siguiente != this.x[this.x.length - 1] && ajustado
					&& (int) x_siguiente == (int) x_anterior + mcd
					|| (int) x_siguiente == (int) x_anterior + 2 * mcd
					|| (int) x_siguiente == (int) x_anterior + 3 * mcd
					|| (int) x_siguiente == (int) x_anterior + 4 * mcd)
				categorias.append("<category/>");
			else
				categorias.append("<category name='"
						+ this.numberFormat.format(x_siguiente) + "'/>");
			data.append("<set value='" + y_siguiente + "'/>");
			dataReg.append("<set value='"
					+ Math
							.exp(this.interseccion + x_siguiente
									* this.pendiente) + "'/>");
			log.debug("x siguiente,y_siguiente " + x_siguiente + ","
					+ y_siguiente + "agregando a strXML valores " + x_siguiente
					+ "," + y_siguiente);
			this.ancho += 10;
			x += mcd;
			x_anterior = x_siguiente;
			y_anterior = y_siguiente;

		}

		categorias.append("</categories>");
		data.append("</dataset>");
		dataReg.append("</dataset>");
		strXML.append(categorias.toString());
		strXML.append(data.toString());
		strXML.append(dataReg.toString());
		strXML.append("</graph>");

		this.strXML = strXML.toString();
		if (this.ancho > 1000)
			this.ancho = 1000;

	}

	/**
	 * 
	 * M�todo que genera el XML de las series de datos para ser utilizado con
	 * Fusion Charts. Este XML tiene interpolaciones autom�ticas entre cada par
	 * de n�meros de la serie. Agrega adem�s la regresi�n potencial en el
	 * gr�fico.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param ajustado
	 *            indica si el grafico se debe ajustar, es �til para cuando la
	 *            interpolaci�n genera muchos n�meros
	 * @param swf
	 *            archivo swf de FusionCharts a ser utilizado
	 * @param id
	 *            id del gr�fico de FusionCharts (para cada gr�fico de la vista
	 *            este n�mero DEBE ser distinto)
	 * @param caption
	 *            titulo del gr�fico
	 * @param numberPrefix
	 *            prefijo de los n�meros del eje y
	 * @param formatNumberScale
	 *            formato de n�meros
	 * @param decimalPrecision
	 *            n�meros de ceros a la derecha
	 * @param xAxisName
	 *            nombre del eje x
	 * @param yAxisName
	 *            nombre del eje y
	 * @since 1.0
	 */
	public void doStrXMLPot(boolean ajustado, String swf, int id,
			String caption, String numberPrefix, String formatNumberScale,
			String decimalPrecision, String xAxisName, String yAxisName) {
		this.id = id;
		this.swf = swf;
		/**
		 * se calcula el m�ximo com�n divisor que nos dar� el tama�o de
		 * intervalo del eje x
		 */

		double mcd = Util.MCD2(this.x);

		StringBuilder strXML = new StringBuilder();
		StringBuilder categorias = new StringBuilder();
		StringBuilder data = new StringBuilder();
		StringBuilder dataReg = new StringBuilder();
		strXML
				.append("<graph subcaption='Potencial R="
						+ this.numberFormat.format(this.correlacion)
						+ "' bgSWF='images/fusioncharts.jpg' showvalues='0' rotateNames='1' canvasBgColor='FFFFFF' canvasBgAlpha='20' canvasBorderColor='7B3F00' canvasBorderThickness='0' divLineColor='a82925' numdivlines='4' showgridbg='1' showhovercap='1' lineThickness='1' animation='1' caption='"
						+ caption + "' xAxisName='" + xAxisName
						+ "' yAxisName='" + yAxisName + "' numberPrefix='"
						+ numberPrefix + "' formatNumberScale='"
						+ formatNumberScale + "' decimalPrecision='"
						+ decimalPrecision + "'>");
		categorias.append("<categories>");
		data
				.append("<dataset showValue='1' alpha='100' anchorAlpha='0' lineThickness='2' seriesName='"
						+ yAxisName
						+ "' color='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBorderColor='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBgColor='"
						+ this.colorHelper.getFCColor() + "'>");
		dataReg
				.append("<dataset showValue='1' alpha='100' anchorAlpha='0' lineThickness='2' seriesName='Regresion' color='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBorderColor='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBgColor='"
						+ this.colorHelper.getFCColor() + "'>");
		double x_anterior = this.x[0];
		double y_anterior = this.y[0];
		double x = x_anterior + mcd;
		double y;
		double x_siguiente;
		double y_siguiente;
		categorias.append("<category name='"
				+ this.numberFormat.format(x_anterior) + "'/>");
		data.append("<set value='" + y_anterior + "'/>");
		dataReg.append("<set value='" + Math.pow(10, this.interseccion)
				* Math.pow(x_anterior, this.pendiente) + "'/>");

		for (int i = 1; i < this.x.length; i++) {

			x_siguiente = this.x[i];
			y_siguiente = this.y[i];
			if (x_siguiente == x_anterior)
				continue;

			while (x < x_siguiente) {
				categorias.append("<category/>");
				y = Util.interpolar(x_anterior, x_siguiente, y_anterior,
						y_siguiente, x);
				data.append("<set value='" + y + "'/>");
				dataReg.append("<set value='" + Math.pow(10, this.interseccion)
						* Math.pow(x, this.pendiente) + "'/>");
				x += mcd;
				this.ancho += 10;
			}

			if (x_siguiente != this.x[this.x.length - 1] && ajustado
					&& (int) x_siguiente == (int) x_anterior + mcd
					|| (int) x_siguiente == (int) x_anterior + 2 * mcd
					|| (int) x_siguiente == (int) x_anterior + 3 * mcd
					|| (int) x_siguiente == (int) x_anterior + 4 * mcd)
				categorias.append("<category/>");
			else
				categorias.append("<category name='"
						+ this.numberFormat.format(x_siguiente) + "'/>");
			data.append("<set value='" + y_siguiente + "'/>");
			dataReg.append("<set value='" + Math.pow(10, this.interseccion)
					* Math.pow(x_siguiente, this.pendiente) + "'/>");
			log.debug("x siguiente,y_siguiente " + x_siguiente + ","
					+ y_siguiente + "agregando a strXML valores " + x_siguiente
					+ "," + y_siguiente);
			x += mcd;
			this.ancho += 10;
			x_anterior = x_siguiente;
			y_anterior = y_siguiente;

		}

		categorias.append("</categories>");
		data.append("</dataset>");
		dataReg.append("</dataset>");
		strXML.append(categorias.toString());
		strXML.append(data.toString());
		strXML.append(dataReg.toString());
		strXML.append("</graph>");

		this.strXML = strXML.toString();
		if (this.ancho > 1000)
			this.ancho = 1000;

	}

	/**
	 * 
	 * M�todo que genera el XML de las series de datos para ser utilizado con
	 * Fusion Charts. Este XML tiene interpolaciones autom�ticas entre cada par
	 * de n�meros de la serie. Agrega adem�s la regresi�n lineal en el gr�fico.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param ajustado
	 *            indica si el grafico se debe ajustar, es �til para cuando la
	 *            interpolaci�n genera muchos n�meros
	 * @param swf
	 *            archivo swf de FusionCharts a ser utilizado
	 * @param id
	 *            id del gr�fico de FusionCharts (para cada gr�fico de la vista
	 *            este n�mero DEBE ser distinto)
	 * @param caption
	 *            titulo del gr�fico
	 * @param numberPrefix
	 *            prefijo de los n�meros del eje y
	 * @param formatNumberScale
	 *            formato de n�meros
	 * @param decimalPrecision
	 *            n�meros de ceros a la derecha
	 * @param xAxisName
	 *            nombre del eje x
	 * @param yAxisName
	 *            nombre del eje y
	 * @since 1.0
	 */
	public void doStrXMLLin(boolean ajustado, String swf, int id,
			String caption, String numberPrefix, String formatNumberScale,
			String decimalPrecision, String xAxisName, String yAxisName) {
		this.id = id;
		this.swf = swf;
		/**
		 * se calcula el m�ximo com�n divisor que nos dar� el tama�o de
		 * intervalo del eje x
		 */

		double mcd = Util.MCD2(this.x);

		StringBuilder strXML = new StringBuilder();
		StringBuilder categorias = new StringBuilder();
		StringBuilder data = new StringBuilder();
		StringBuilder dataReg = new StringBuilder();
		strXML
				.append("<graph subcaption='Lineal R="
						+ this.numberFormat.format(this.correlacion)
						+ "' bgSWF='images/fusioncharts.jpg' showvalues='0' rotateNames='1' canvasBgColor='FFFFFF' canvasBgAlpha='20' canvasBorderColor='7B3F00' canvasBorderThickness='0' divLineColor='a82925' numdivlines='4' showgridbg='1' showhovercap='1' lineThickness='1' animation='1' caption='"
						+ caption + "' xAxisName='" + xAxisName
						+ "' yAxisName='" + yAxisName + "' numberPrefix='"
						+ numberPrefix + "' formatNumberScale='"
						+ formatNumberScale + "' decimalPrecision='"
						+ decimalPrecision + "'>");
		categorias.append("<categories>");
		data
				.append("<dataset showValue='1' alpha='100' anchorAlpha='0' lineThickness='2' seriesName='"
						+ yAxisName
						+ "' color='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBorderColor='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBgColor='"
						+ this.colorHelper.getFCColor() + "'>");
		dataReg
				.append("<dataset showValue='1' alpha='100' anchorAlpha='0' lineThickness='2' seriesName='Regresion' color='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBorderColor='"
						+ this.colorHelper.getFCColor()
						+ "' anchorBgColor='"
						+ this.colorHelper.getFCColor() + "'>");
		double x_anterior = this.x[0];
		double y_anterior = this.y[0];
		double x = x_anterior + mcd;
		double y;
		double x_siguiente;
		double y_siguiente;

		categorias.append("<category name='"
				+ this.numberFormat.format(x_anterior) + "'/>");
		data.append("<set value='" + y_anterior + "'/>");
		dataReg.append("<set value='" + this.pendiente * x_anterior
				+ this.interseccion + "'/>");

		for (int i = 1; i < this.x.length; i++) {

			x_siguiente = this.x[i];
			y_siguiente = this.y[i];
			if (x_siguiente == x_anterior)
				continue;
			while (x < x_siguiente) {
				categorias.append("<category/>");
				y = Util.interpolar(x_anterior, x_siguiente, y_anterior,
						y_siguiente, x);
				data.append("<set value='" + y + "'/>");
				dataReg.append("<set value='" + this.pendiente * x
						+ this.interseccion + "'/>");
				x += mcd;
				this.ancho += 10;
			}

			if (x_siguiente != this.x[this.x.length - 1] && ajustado
					&& (int) x_siguiente == (int) x_anterior + mcd
					|| (int) x_siguiente == (int) x_anterior + 2 * mcd
					|| (int) x_siguiente == (int) x_anterior + 3 * mcd
					|| (int) x_siguiente == (int) x_anterior + 4 * mcd)
				categorias.append("<category/>");
			else
				categorias.append("<category name='"
						+ this.numberFormat.format(x_siguiente) + "'/>");
			data.append("<set value='" + y_siguiente + "'/>");
			dataReg.append("<set value='" + this.pendiente * x_siguiente
					+ this.interseccion + "'/>");
			log.debug("x siguiente,y_siguiente " + x_siguiente + ","
					+ y_siguiente + "agregando a strXML valores " + x_siguiente
					+ "," + y_siguiente);
			x_anterior = x_siguiente;
			x += mcd;
			this.ancho += 10;

			y_anterior = y_siguiente;

		}

		categorias.append("</categories>");
		data.append("</dataset>");
		dataReg.append("</dataset>");
		strXML.append(categorias.toString());
		strXML.append(data.toString());
		strXML.append(dataReg.toString());
		strXML.append("</graph>");

		this.strXML = strXML.toString();
		if (this.ancho > 1000)
			this.ancho = 1000;

	}

	/**
	 * 
	 * Realiza la regresi�n lineal de los datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @since 1.0
	 */
	public void lineal() {
		Regresion regresion = new Regresion(this.x, this.y);
		regresion.lineal();
		this.pendiente = regresion.pendiente();
		this.correlacion = regresion.correlacion();
		this.interseccion = regresion.interseccion();
		/**
		 * y=pendiente*x + interseccion
		 */
	}

	/**
	 * 
	 * Realiza la regresi�n exponencial de los datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @since 1.0
	 */
	public void exponencial() {
		double[] y_aux = new double[this.y.length];
		for (int i = 0; i < y_aux.length; i++) {
			y_aux[i] = Math.log(this.y[i]);
			log.debug("y_log " + y_aux[i]);
		}

		Regresion regresion = new Regresion(this.x, y_aux);
		regresion.lineal();
		this.pendiente = regresion.pendiente();
		this.correlacion = regresion.correlacion();
		this.interseccion = regresion.interseccion();
		/**
		 * y=e^(interseccion + x*pendiente)
		 */
	}

	/**
	 * 
	 * Realiza la regresi�n potencial de los datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @since 1.0
	 */
	public void potencial() {
		double[] x_aux = new double[this.x.length];
		double[] y_aux = new double[this.y.length];
		for (int i = 0; i < x_aux.length; i++) {
			x_aux[i] = Math.log10(this.x[i]);
			y_aux[i] = Math.log10(this.y[i]);
			log.debug("x_log10 " + x_aux[i]);
			log.debug("y_log10 " + y_aux[i]);
		}
		Regresion regresion = new Regresion(x_aux, y_aux);
		regresion.lineal();
		this.pendiente = regresion.pendiente();
		this.correlacion = regresion.correlacion();
		this.interseccion = regresion.interseccion();
		/**
		 * y=10^interseccion * x^pendiente
		 */
	}

}
