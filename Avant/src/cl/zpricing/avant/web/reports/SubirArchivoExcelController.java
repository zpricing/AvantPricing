package cl.zpricing.avant.web.reports;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.trans.StepLoader;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.version.BuildVersion;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import cl.zpricing.avant.servicios.ReporteDao;
import cl.zpricing.commons.utils.ErroresUtils;

/***
 * 
 * <b>Controlador de /subirArchivo.htm, que permite subir un archivo .xls con el
 * reporte semanal de Nielsen y agregar la información que contiene a la base de
 * datos.</b>
 * 
 * <ul>
 * <li>Los datos de conexión se obtienen del bean dataSource definido en
 * applicationContext.xml.</li>
 * <li>La transformación a usar se obtiene del bean kettleConfiguration definido
 * en kettleConfiguration.xml, la que se busca por defecto en WEB-INF/kettle.</li>
 * <li>El archivo se sube a WEB-INF/tmp.</li>
 * </ul>
 * Registro de versiones:
 * <ul>
 * <li>1.0 22-01-2010 Camilo Araya: versión inicial.</li>
 * <li>1.1 23-01-2010 Roberto Vargas: grabar archivo.</li>
 * <li>1.2 23-01-2010 Camilo Araya: integración Kettle.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */
public class SubirArchivoExcelController extends SimpleFormController {
	private Logger		log	= (Logger) Logger.getLogger(this.getClass());
	private ReporteDao	reporteDao;

	public void setReporteDao(ReporteDao reporteDao) {
		this.reporteDao = reporteDao;
	}

	public ReporteDao getReporteDao() {
		return reporteDao;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		log.debug("@onSubmit() de SubirArchivoExcelController");
		ModelAndView mv = new ModelAndView(getSuccessView());

		ArrayList<String> listaErrores = new ArrayList<String>();
		mv.addObject("listaErrores", listaErrores);
		ArrayList<String> listaExitosos = new ArrayList<String>();
		mv.addObject("listaExitosos", listaExitosos);

		int cantidadArchivos = Integer.parseInt(request.getParameter("cantidadArchivos"));
		for (int i = 0; i < cantidadArchivos; i++) {
			try {
				// / PARS PRIMA: Subir el archivo
				// hacer referencia al directorio de destino
				String basePath = request.getSession().getServletContext().getRealPath("");
				String fileDir = basePath + "\\" + "WEB-INF\\tmp";

				// guardar archivo
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile multipartFile = multipartRequest.getFile("archivo" + i);

				if (!multipartFile.isEmpty()) {
					File newFile = new File(fileDir, multipartFile.getOriginalFilename());
					log.debug("procesnado arhco: " + newFile.getName());

					try {
						FileUtils.writeByteArrayToFile(newFile, multipartFile.getBytes());
						log.debug("fileDir = " + fileDir);
					} catch (Throwable e) {
						e.printStackTrace();
					}

					// / PARS SECUNDA: Ejecutar el ETL
					// Inicializando Kettle
					log.debug("StepLoader: init");
					StepLoader.init();
					EnvUtil.environmentInit();
					BuildVersion.getInstance().setRevision("ZPCustom"); // Esto
																		// es
																		// necesario
																		// para
																		// inicializar
																		// Kettle
					BuildVersion.getInstance().setBuildDate("2009-01-01"); /* Ibid. */

					// Obtener la transformación a ocupar, según esté
					// configurado en kettleConfiguration.xml.
					KettleParameters kettleParameters = (KettleParameters) cl.zpricing.avant.util.AppContext.getApplicationContext().getBean(
						"etlNielsen");
					TransMeta transMeta = new TransMeta(basePath + "\\WEB-INF\\kettle\\" + kettleParameters.getUrlTransformacion());
					Trans kettleTransformation = new Trans(transMeta);

					// Obtener los parámetros de conexión al servidor desde
					// ApplicationContext;
					BasicDataSource dataSource = (BasicDataSource) cl.zpricing.avant.util.AppContext.getApplicationContext().getBean(
						"dataSource");

					// Lo siguiente es asumiendo que la forma de la url que se
					// recibirá es
					// jdbc:mssql://192.168.100.195;defaultCatalog=zp_dev o algo
					// así.
					// Si no es el caso, WE'RE ALL DOOMED!!
					String splitMula[] = dataSource.getUrl().split("/");
					String paramServidor = splitMula[splitMula.length - 1].split(";")[0];
					String paramDb = splitMula[splitMula.length - 1].split(";")[1].split("=")[1];
					// El resto de los parámetros que se pueden obtener
					// limpiamente desde el dataSource
					String paramUsuario = dataSource.getUsername();
					String paramPass = dataSource.getPassword();
					String paramArchivoExcel = newFile.getAbsolutePath();

					// Pasar los parámetros a la transformación
					kettleTransformation.setParameterValue("servidor", paramServidor);
					kettleTransformation.setParameterValue("usuario", paramUsuario);
					kettleTransformation.setParameterValue("pass", paramPass);
					kettleTransformation.setParameterValue("db", paramDb);
					kettleTransformation.setParameterValue("archivo_excel", paramArchivoExcel);

					// For logging purposes
					log.debug("Se hará la transformación usando los sgtes. parámetros: ");
					log.debug("Transformacion: " + transMeta.getFilename());
					log.debug(" >servidor: " + paramServidor);
					log.debug(" >usuario: " + paramUsuario);
					log.debug(" >pass: " + paramPass);
					log.debug(" >db: " + paramDb);
					log.debug(" >archivo_excel: " + paramArchivoExcel);

					// Ejecutar la transformación
					kettleTransformation.execute(null);
					kettleTransformation.waitUntilFinished();

					// ¿Hubo errores?
					if (kettleTransformation.getErrors() > 0) {
						mv.addObject("errores", true);
						if (mv.getModel().get("listaErrores") != null) {
							if (mv.getModel().get("listaErrores") instanceof ArrayList<?>) {
								((ArrayList<String>) mv.getModel().get("listaErrores")).add(newFile.getName());
							}
						}
						throw new RuntimeException("kettleTransformation.getErrors > 0");
					} else {
						mv.addObject("exito", true);
						if (mv.getModel().get("listaExitosos") != null) {
							if (mv.getModel().get("listaExitosos") instanceof ArrayList<?>) {
								((ArrayList<String>) mv.getModel().get("listaExitosos")).add(newFile.getName());
							}
						}
					}
				}
			} catch (Exception e) {
				log.debug("KettleException. " + ErroresUtils.extraeStackTrace(e));
				
			}

		}
		return mv;
	}

	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
		log.debug("@initBinder() de SubirArchivoExcelController");
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
}
