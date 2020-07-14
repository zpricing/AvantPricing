package cl.zpricing.avant.negocio;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import cl.zpricing.avant.model.Clase;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.Mascara;
import cl.zpricing.avant.model.Pelicula;
import cl.zpricing.avant.model.prediction.Prediccion;
import cl.zpricing.avant.model.prediction.PrediccionPorFuncion;
/**
 * 
 * <b>Clase contenedora de los m�todos necesarios para predecir la demanda</b>
 *
 * Registro de versiones:
 * <ul>
 *   <li>1.0 18-02-2009 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public interface PrediccionManager {

    public void predecirDemandaPorDia(Prediccion prediccion);

	/**
	 * 
	 * Crea las predicciones por función dentro de un objeto prediccion, en
	 * funcion de la asistencia de la primera funcion de un conjunto de
	 * peliculas semejantes en las que se cumple que su primera funcion esta
	 * dentro de un rango de fecha dado.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 15-01-2009 Daniel Estévez Garay: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 *            predicción en la cual se crearán las predicciones por función
	 * @param dias
	 *            días a partir de la fecha de la primera funcion para tomar la
	 *            asistencia
	 * @param fecha_inicio_rango
	 *            fecha inicio del rango de fecha
	 * @param fecha_fin_rango
	 *            fecha término del rango de fecha
	 * @since 1.0
	 */
    public void predecirDemandaPorFuncion(Prediccion prediccion, int dias, 
			GregorianCalendar fecha_inicio_rango, GregorianCalendar fecha_fin_rango);
	/**
	 * 
	 * Crea las predicciones por clase dentro de una predicci�n, seg�n un
	 * arreglo de precios de clases y rangos de tiempo espec�ficos.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 14-01-2009 Daniel Estévez Garay: Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param prediccion
	 *            predicción donde se crearán las predicciones por
	 *            clase
	 * @param tipo_regresion
	 *            tipo de regresión a efectuar sobre la demanda acumulada
	 *            0 Lineal por tramos
	 *            1 Lineal
	 *            2 Exponencial
	 *            3 Potencial
	 * @since 1.0
	 */
    public void predecirDemandaPorClase(PrediccionPorFuncion predf, int tipo_regresion);
    
    public int[] obtenerAsistenciaPorPeliculaDiaComplejo(Pelicula pelicula, Complejo complejo, GregorianCalendar fecha,int cant_dias);
    
    /**
     * Obtiene las Mascaras que se le pueden aplicar a una funcion.
     *
     * <P>
     * Registro de versiones:
     * <ul>
     *   <li> 1.0 30-01-2009 MARIO : Versi�n Inicial</li>
     * </ul>
     * </P>
     * 
     * @param funcion
     * @return ArrayList<Mascara>
     * @since 1.0
     */
    public List<Mascara> getMascaras(Funcion funcion);
    
    /**
     * Devuelve la Mascara por defecto que tiene una funcion dentro de una Prediccion
     * Por Funcion dado su nivel de ocupacion.
     *
     * <P>
     * Registro de versiones:
     * <ul>
     *   <li> 1.0 30-01-2009 MARIO : Versi�n Inicial</li>
     * </ul>
     * </P>
     * 
     * @param predPorFuncion
     * @return
     * @since 1.0
     */
    public int getMascaraDefault(PrediccionPorFuncion predPorFuncion);
    
    /**
     * Devuelve un Array con las Clases que son vendidas por Revenue
     * Management.  Estas Clases son las que pertenecen a la Plantilla Padre de un
     * Grupo de Plantillas que este en el rango de fechas de la funcion.
     *
     * <P>
     * Registro de versiones:
     * <ul>
     *   <li> 1.0 30-01-2009 MARIO : Versi�n Inicial</li>
     * </ul>
     * </P>
     * 
     * @param funcion
     * @return
     * @since 1.0
     */
    public ArrayList<Clase> getClasesRM(Funcion funcion);
    
}


