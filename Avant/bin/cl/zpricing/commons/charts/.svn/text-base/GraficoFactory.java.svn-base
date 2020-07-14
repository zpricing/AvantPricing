package cl.zpricing.commons.charts;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import cl.zpricing.commons.charts.mapping.MapeoDeDatosAbstract;
import cl.zpricing.commons.charts.vo.DatosGrafico;
import cl.zpricing.commons.charts.vo.DatosGraficoSingleSeriesCharts;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * <p>Clase abstracta utilizada para la creacion de archivos XML para utilizar en los graficos 
 * de FusionCharts.</p>
 *
 * <p>
 * <b>Registro de versiones:</b>
 * <ul>
 * 	   <li>1.0 (19-08-2009: Nicolas Dujovne W.): version inicial.</li>
 * </ul>
 * </p>
 * <p>
 *   <b>Todos los derechos reservados por ZhetaPricing.</b>
 * </p>
 */
public abstract class GraficoFactory {
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/**
	 * Documento DOM.
	 */
	protected Document documentDOM;
	
	/**
	 * Ruta en donde dejar el archivo XML generado.
	 */
	private String rutaArchivo;
	
	/**
	 * <p>Metodo que ejecuta la creaci�n del archivo XML para representar en grafico.</p>
	 * <p>
	 * <b>Registro de versiones:</b>
	 * <ul>
	 * 	   <li>1.0 (19-08-2009: Nicolas Dujovne W.): version inicial.</li>
	 * </ul>
	 * </p>
	 *
	 * @param estructuraDeDatos Datos para crear el grafico.
	 * @param claseTraductora Clase que mapea los datos desde el formato original al formato requerido por cada grafico.
	 * @return String que contiene el XML generado.
	 */
	public String execute(Object estructuraDeDatos, MapeoDeDatosAbstract claseTraductora, Map<String, Object> parametros) {
		log.debug("----- Iniciando execute de GraficoFactory -----");
		DatosGrafico datosGrafico = claseTraductora.execute(estructuraDeDatos, parametros);
		generarEstructuraXML(datosGrafico);
		String xml = generarXML();
		xml = xml.replace("\"", "'");
		xml = xml.replace("\n", "");
		log.debug("");
		log.debug(xml);
		log.debug("");
		return xml; 
	}
	
	/**
	 * <p>Metodo abstracto que crea y carga el objeto Document documentoDOM de la clase.</p>
	 * <p>
	 * <b>Registro de versiones:</b>
	 * <ul>
	 * 	   <li>1.0 (19-08-2009: Nicolas Dujovne W.): version inicial.</li>
	 * </ul>
	 * </p>
	 */
	protected abstract void generarEstructuraXML(DatosGrafico datosGrafico);
	
	protected void configurarDocumento(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			documentDOM = db.newDocument();
		}
		catch(ParserConfigurationException pce) {
			log.error("Error mientras se trata de instanciar DocumentBuilder " + pce);
			//throws 
		}
	}
	
	/**
	 * <p>Metodo que genera el archivo fisico XML en base al contenido del objeto documentoDOM.</p>
	 * <p>
	 * <b>Registro de versiones:</b>
	 * <ul>
	 * 	   <li>1.0 (19-08-2009: Nicolas Dujovne W.): version inicial.</li>
	 * </ul>
	 * </p>
	 *
	 * @return String que contiene el XML generado.
	 */
	protected String generarXML() {
		log.debug("----- Iniciando generarXML -----");
		try {
			//escribir XML en un string
			OutputFormat format = new OutputFormat(documentDOM);
			format.setIndenting(true);

			StringWriter salida = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(salida, format);
			serializer.serialize(documentDOM);
		    String out = salida.toString(); 
			
		    //Pasar XML a un archivo si existe ruta
			try {
				log.debug("Ruta archivo : " + rutaArchivo);
				if(rutaArchivo != null){
					FileOutputStream fos = new FileOutputStream(rutaArchivo);
					//Escribir los bytes del BOM de UTF-8
					fos.write(239); // 0xEF
					fos.write(187); // 0xBB
					fos.write(191); // 0xBF
					OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
					//Escribir el XML en UTF-8
				    osw.write(out);
				    osw.flush();
					osw.close();
					fos.close();
				}
			}
			catch(java.io.IOException ioex) {
				log.error("se presento el error: "+ioex.toString());
			}
				
			return out;
			
		}catch(IOException ie) {
		    ie.printStackTrace();
		    return null;
		}
	}

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
}
