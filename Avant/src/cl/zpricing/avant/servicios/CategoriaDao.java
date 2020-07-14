package cl.zpricing.avant.servicios;

import java.util.ArrayList;

import cl.zpricing.avant.model.Categoria;

/**
 * 
 * <b>DAO para el manejo de las categor�as</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 18-02-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public interface CategoriaDao {

    /**
     * 
     * Obtiene una categoria de determinado id desde la capa de datos.
     *
     * <P>
     * Registro de versiones:
     * <ul>
     *   <li> 1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
     * </ul>
     * </P>
     * 
     * @param id identificador de la categor�a
     * @return categoria
     * @since 1.0
     */
    public Categoria obtenerCategoria(int id);

  /**
   * 
   * Agrega una categor�a a la capa de datos.
   *
   * <P>
   * Registro de versiones:
   * <ul>
   *   <li> 1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
   * </ul>
   * </P>
   * 
   * @param categoria categor�a a agregar
   * @since 1.0
   */
    public void agregarCategoria(Categoria categoria);

   /**
    * 
    * Elimina una categoria de la capa de datos.
    *
    * <P>
    * Registro de versiones:
    * <ul>
    *   <li> 1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
    * </ul>
    * </P>
    * 
    * @param categoria categor�a a eliminar
    * @since 1.0
    */
    public void eliminarCategoria(Categoria categoria);

    /**
     * 
     * Actualiza una categoria de la capa de datos.
     *
     * <P>
     * Registro de versiones:
     * <ul>
     *   <li> 1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
     * </ul>
     * </P>
     * 
     * @param categoria categor�a a actualizar
     * @since 1.0
     */
    public void actualizarCategoria(Categoria categoria);
    
    /**
     * 
     * Obtiene el conjunto de todas las categorias desde la capa de datos.
     *
     * <P>
     * Registro de versiones:
     * <ul>
     *   <li> 1.0 17-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
     * </ul>
     * </P>
     * 
     * @return lista de categorias
     * @since 1.0
     */
    public ArrayList<Categoria> obtenerCategorias();
    
    public Integer agregarCategoriaSiNoExiste(String externalSourceCode);
}
