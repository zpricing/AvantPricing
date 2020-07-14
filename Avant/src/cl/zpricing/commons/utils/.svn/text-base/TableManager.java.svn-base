package cl.zpricing.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

public class TableManager {
	private static TableManager instance;
	private Map<String, Properties> propiedades;
	
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	public TableManager() {
		propiedades = new HashMap<String, Properties>();
	}
	
	public static TableManager getInstance() {
		if (instance == null) {
			instance = new TableManager();
		}
		return instance;
	}
	
	private Properties load(String archivo) throws IOException {
		if (!propiedades.containsKey(archivo)) {
			Properties file = new Properties();
			//InputStream in = ClassLoader.getSystemResourceAsStream(archivo);
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(archivo);
			
			file.load(in);
			propiedades.put(archivo, file);
		}
		return propiedades.get(archivo);
	}
	
	public String get(String archivo, String llave) throws IOException {
		Properties file = load(archivo);
		String result = file.getProperty(llave);
		return result;
	}
	
	public Properties get(String archivo) throws IOException {
		Properties file = load(archivo);
		return file;
	}
	
	public static void main(String[] args) throws IOException {
		String test = TableManager.getInstance().get("test.properties", "prueba");
		System.out.println(test);
	}
}
