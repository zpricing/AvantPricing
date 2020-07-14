package cl.zpricing.avant.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.GrupoComplejo;
import cl.zpricing.avant.model.RptComplejo;
import cl.zpricing.avant.model.reports.SemanaNielsen;
import cl.zpricing.commons.exceptions.DaoException;

/**
 * Interface para accesar los datos del Complejo en la base de datos
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 12-01-2009 Julio Olivares Alarc�n: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public interface ComplejoDao {

	/**
	 * Obtiene objeto Complejo desde la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param id
	 *            Identificador entero
	 * @return Objeto Complejo
	 * @since 1.0
	 */
	public Complejo obtenerComplejo(int id);

	/**
	 * Agrega objeto Complejo en la capa de datos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param complejo
	 *            Objeto Complejo
	 * @return void
	 * @since 1.0
	 */
	public void agregarComplejo(Complejo complejo);

	/**
	 * Elimina objeto Complejo de la capa de datos
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param Complejo
	 *            Objeto complejo
	 * @return void
	 * @since 1.0
	 */
	public boolean eliminarComplejo(Complejo complejo);

	/**
	 * Actualiza objeto Complejo en la capa de datos
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 17/12/2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param Complejo
	 *            Objeto complejo
	 * @return void
	 * @since 1.0
	 */
	public void actualizarComplejo(Complejo complejo);

	/**
	 * Obtiene Todos los complejos
	 * 
	 * @return lista de complejos
	 */
	public List<Complejo> complejosTodos();
	
	/**
	 * 
	 * Obtiene una lista de todos los objetos RptComplejo que representan los complejos
	 * de los que hay información en los reportes de Nielsen.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 25-01-2010 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return
	 * @since 3.0
	 */
	public List<RptComplejo> obtenerRptComplejosTodos();
	
	/**
	 * 
	 * Obtiene el último RptComplejo válido para una id en particular.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 25-01-2010 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return
	 * @since 3.0
	 */
	public RptComplejo obtenerRptComplejo(int id);

	public void editarCiudadComplejo(RptComplejo rptComplejo);

	public void editarCantidadSalasComplejo(RptComplejo rptComplejo);

	public void editarNombreComplejo(RptComplejo rptComplejo);

	public void editarIdComplejo(HashMap<String, Object> map);

	public void editarEmpresaComplejo(RptComplejo rptComplejo);

	public void eliminarRptComplejo(RptComplejo rptComplejo);

	public void agregarRptComplejo(RptComplejo rptComplejo);

	public void editarRmComplejo(RptComplejo rptComplejo);

	/**
	 * 
	 * Obtiene el rpt_complejo_id más alto actualmente registrado en la aplicación.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 10-02-2010 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return
	 * @since 3.0
	 */
	public Integer obtenerRptComplejoIdMasAlto(); 
	
	/**
	 * 
	 * Obtiene en forma resumida todas los reportes semanales de Nielsen que se han incorporado 
	 * en la aplicación.
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 10-02-2010 Camilo Araya: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return
	 * @since 3.0
	 */
	public ArrayList<SemanaNielsen> obtenerSemanasNielsen();

	public void eliminarSemanaNielsen(Date fecha);
	
	public Complejo obtenerComplejoPorIdExterno(String idExterno) throws DaoException; 
	
	public void agregaRelacionGrupoComplejo(Complejo complejo, GrupoComplejo grupo);
}
