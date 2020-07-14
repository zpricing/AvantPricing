package cl.zpricing.avant.web.chart;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * <b>Descripci�n de la Clase</b> Clase mas importante para generar el XML de
 * los graficos
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 05-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class GeneradorXMLGrafico {

	private Document dom = null;
	private List<CategoriaGrafico> categorias = null;
	private List<SerieDatos> datos = null;
	private String titulo = null;
	private String legendaX = null;
	private String legendaY1 = null;
	private String legendaY2 = null;
	private String colorFondo = null;
	private String rutaArchivo = null;
	private boolean mostrarNumeros = false;
	private boolean formatNumberScale = false;
	private boolean decimalPrecision = false;
	private boolean imageSave = true;
	private String numberPrefix = null;
	private String imageSaveURL = null;
	private boolean esPie = false;
	private boolean mostrarNombres = false;

	/**
	 * Se utiliza para llamar al las clases que inician el XML
	 */
	private void iniciar() {
		/**
		 * Get a DOM object Using JAXP in implementation independent manner
		 * create a document object using which we create a xml tree in memory
		 */
		// get an instance of factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// get an instance of builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// create an instance of DOM
			dom = db.newDocument();

		} catch (ParserConfigurationException pce) {
			// dump it
			System.out
					.println("Error mientras se trata de instanciar DocumentBuilder "
							+ pce);
			System.exit(1);
		}
	}

	/**
	 * Se utiliza para crear la estructura del arbol
	 */
	private void crearArbolGrafico() {

		// create the root element <Graph>
		iniciar();
		Element raizEle = dom.createElement("graph");
		dom.appendChild(raizEle);

		// Aqu� se crean los atributos para el gr�fico
		if (titulo != null)
			raizEle.setAttribute("caption", titulo);
		else
			raizEle.setAttribute("caption", "sin titulo");

		// leyenda eje x
		if (legendaX != null)
			raizEle.setAttribute("xAxisName", legendaX);
		else
			raizEle.setAttribute("xAxisName", "X");

		// prefijo del numero
		if (numberPrefix != null)
			raizEle.setAttribute("numberPrefix", numberPrefix);

		// ruta para guardar imagen
		if (imageSaveURL != null)
			raizEle.setAttribute("imageSaveURL", imageSaveURL);

		// etiqueta de el(los) eje(s) y(s)
		if (legendaY2 != null) {
			if (this.legendaY1 != null)
				raizEle.setAttribute("PYAxisName", legendaY1);
			else
				raizEle.setAttribute("PYAxisName", "Y1");
			raizEle.setAttribute("SYAxisName", legendaY2);
		} else {
			if (this.legendaY1 != null)
				raizEle.setAttribute("yAxisName", legendaY1);
			else
				raizEle.setAttribute("yAxisName", "Y1");
		}

		// color de fondo
		if (colorFondo != null)
			raizEle.setAttribute("bgcolor", colorFondo);

		// mostrar numeros
		if (mostrarNumeros)
			raizEle.setAttribute("showValues", "1");
		else
			raizEle.setAttribute("showValues", "0");

		// mostrar nombres
		if (mostrarNombres)
			raizEle.setAttribute("showNames", "1");

		// formato escal numerica
		if (formatNumberScale)
			raizEle.setAttribute("formatNumberScale", "1");
		else
			raizEle.setAttribute("formatNumberScale", "0");

		// precision decimal
		if (decimalPrecision)
			raizEle.setAttribute("decimalPrecision", "1");
		else
			raizEle.setAttribute("decimalPrecision", "0");

		// guardar imagen
		if (imageSave)
			raizEle.setAttribute("imageSave", "1");
		else
			raizEle.setAttribute("imageSave", "0");

		// Aqui se generan los graficos en forma de torta
		if (!esPie) {
			// Aqu� se generan las categorias
			Element catEle = dom.createElement("categories");
			raizEle.appendChild(catEle);
			if (categorias != null) {
				Iterator<CategoriaGrafico> it2 = categorias.iterator();
				while (it2.hasNext()) {
					CategoriaGrafico b = it2.next();
					Element ele = b.crearElemento(dom);
					catEle.appendChild(ele);
				}
			}

			// Aqu� se generan las series de datos
			if (datos != null) {
				Iterator<SerieDatos> it = datos.iterator();
				while (it.hasNext()) {
					SerieDatos b = it.next();

					if (legendaY2 != null)
						b.setExisteEjeSecundario();
					Element ele = b.crearElemento(dom);
					raizEle.appendChild(ele);
				}
			}
			// Cuando es en forma de torta el grafico debe ser generado de otra
			// manera
		} else {
			if (datos.size() == 1) {
				Iterator<CategoriaGrafico> it2 = categorias.iterator();

				List<Valor> valores = datos.get(0).getValores();
				Iterator<Valor> it = valores.iterator();

				while (it.hasNext() && it2.hasNext()) {
					Valor val = it.next();
					CategoriaGrafico catGraf = it2.next();

					Element setEle = dom.createElement("set");
					raizEle.appendChild(setEle);

					setEle.setAttribute("name", catGraf.getNombreCategoria());
					setEle.setAttribute("value", "" + val.getValor());
				}
			}
		}

	}

	/**
	 * @return the numberPrefix
	 */
	public String getNumberPrefix() {
		return numberPrefix;
	}

	/**
	 * @param numberPrefix
	 *            the numberPrefix to set
	 */
	public void setNumberPrefix(String numberPrefix) {
		this.numberPrefix = numberPrefix;
	}

	/**
	 * This method uses Xerces specific classes prints the XML document to
	 * string(is commented to change to a file.
	 */
	public String aXML() {
		crearArbolGrafico();
		try {
			// escribir XML en un string
			OutputFormat format = new OutputFormat(dom);
			format.setIndenting(true);

			StringWriter salida = new StringWriter();

			XMLSerializer serializer = new XMLSerializer(salida, format);

			serializer.serialize(dom);

			String out = salida.toString();

			// Pasar XML a un archivo si existe ruta
			try {
				if (rutaArchivo != null) {
					FileOutputStream fos = new FileOutputStream(rutaArchivo);
					// Escribir los bytes del BOM de UTF-8
					fos.write(239); // 0xEF
					fos.write(187); // 0xBB
					fos.write(191); // 0xBF
					OutputStreamWriter osw = new OutputStreamWriter(fos,
							"UTF-8");
					// Escribir el XML en UTF-8
					osw.write(out);
					osw.flush();
					osw.close();
					fos.close();
				}
			} catch (java.io.IOException ioex) {
				System.out.println("se presento el error: " + ioex.toString());
			}
			return out;

		} catch (IOException ie) {
			ie.printStackTrace();
			return null;
		}
	}

	/**
	 * @param datos
	 *            the datos to set
	 */
	public void setDatos(List<SerieDatos> datos) {
		this.datos = datos;
	}

	/**
	 * @return the datos
	 */
	public List<SerieDatos> getDatos() {
		return datos;
	}

	/**
	 * @param titulo
	 *            the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @param legendaX
	 *            the legendaX to set
	 */
	public void setLegendaX(String legendaX) {
		this.legendaX = legendaX;
	}

	/**
	 * @param legendaY1
	 *            the legendaY1 to set
	 */
	public void setLegendaY1(String legendaY1) {
		this.legendaY1 = legendaY1;
	}

	/**
	 * @param legendaY2
	 *            the legendaY2 to set
	 */
	public void setLegendaY2(String legendaY2) {
		this.legendaY2 = legendaY2;
	}

	/**
	 * @param categorias
	 *            the categorias to set
	 */
	public void setCategorias(List<CategoriaGrafico> categorias) {
		this.categorias = categorias;
	}

	/**
	 * @return the categorias
	 */
	public List<CategoriaGrafico> getCategorias() {
		return categorias;
	}

	/**
	 * @param colorFondo
	 *            string en formato RRGGBB en hexadecimal
	 */
	public void setColorFondo(String colorFondo) {
		this.colorFondo = colorFondo;
	}

	/**
	 * @param rutaArchivo
	 *            la ruta en el contenedor web donde se va a guardar el archivo
	 */
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	/**
	 * setear verdadero mostrar numeros
	 */
	public void setMostrarNumero() {
		this.mostrarNumeros = true;
	}

	/**
	 * @param formatNumberScale
	 *            booleano para decidir si mostrar o no la escala numerica
	 */
	public void setFormatNumberScale(boolean formatNumberScale) {
		this.formatNumberScale = formatNumberScale;
	}

	/**
	 * @return si esta o no activada la escala de numeros
	 */
	public boolean isFormatNumberScale() {
		return formatNumberScale;
	}

	/**
	 * @param decimalPrecision
	 *            booleano que indica si debe haber precision decimal
	 */
	public void setDecimalPrecision(boolean decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
	}

	/**
	 * @return si hay o no precision decimal
	 */
	public boolean isDecimalPrecision() {
		return decimalPrecision;
	}

	/**
	 * @param imageSave
	 *            si se graba o no imagen (no funciona con Fusion Chart Free)
	 */
	public void setImageSave(boolean imageSave) {
		this.imageSave = imageSave;
	}

	/**
	 * @return si se graba o no imagen (no funciona con Fusion Chart Free)
	 */
	public boolean isImageSave() {
		return imageSave;
	}

	/**
	 * @param imageSaveURL
	 *            la ruta donde se guardan las imagenes (no funciona en Fusion
	 *            Chart Free)
	 */
	public void setImageSaveURL(String imageSaveURL) {
		this.imageSaveURL = imageSaveURL;
	}

	/**
	 * @return la ruta donde se guardan las imagenes (no funciona en Fusion
	 *         Chart Free)
	 */
	public String getImageSaveURL() {
		return imageSaveURL;
	}

	/**
	 * setea a un grafico del tipo pie (debido a que no acepta la informacion de
	 * la manera que la acepta un grafico normal (ver codigo de
	 * crearArbolGrafico para mas detalles))
	 */
	public void setEsPie() {
		this.esPie = true;
	}

	/**
	 * setea mostrar nombres
	 */
	public void setMostrarNombres() {
		this.mostrarNombres = true;
	}
}