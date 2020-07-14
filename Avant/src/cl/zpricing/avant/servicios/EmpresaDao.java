/**
 * 
 */
package cl.zpricing.avant.servicios;

import java.util.List;

import cl.zpricing.avant.model.Empresa;
import cl.zpricing.avant.model.RptEmpresa;

/**
 * Interface para accesar los datos de las Empresas en la base de datos
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 23-12-2008 Julio Olivares Alarcon: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public interface EmpresaDao {

	    /**
	     * @param id
	     * @return empresa
	     */
	    public Empresa obtenerEmpresa(int id);

	    /**
	     * @param empresa a agregar
	     */
	    public void agregarEmpresa(Empresa empresa);

	    /**
	     * Elimina una empresa si no posee complejos asociados a ella
	     * @param empresa
	     * @return si la empresa pudo ser eliminada
	     */
	    public boolean eliminarEmpresa(Empresa empresa);

	    /**
	     * @param empresa a actualizar
	     */
	    public void actualizarEmpresa(Empresa empresa);

		/**
		 * @return lista de todas las empresas
		 */
		public List<Empresa> empresasTodas();
		public List<RptEmpresa> obtenerRptEmpresaTodos();		
		public RptEmpresa obtenerRptEmpresa(Integer id);
		public Integer obtenerRptEmpresaIdParaEmpresa(Integer empresaId);
		public void actualizarNombreRptEmpresa(RptEmpresa rptEmpresa);

		public void eliminarRptEmpresa(RptEmpresa rptEmpresa);

}
