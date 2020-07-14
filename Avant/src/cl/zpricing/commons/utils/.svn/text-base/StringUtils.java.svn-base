package cl.zpricing.commons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtils {
	/**
     * Constante de fin de linea.
     * <p>
     * La constante aqui definida tiene un solo caracter. Es vital recordar
     * que en muchas partes el codigo fuente asume que esto es asi (costumbre
     * derivada de Unix), por lo que cambiar a, por ejemplo, un par CR/LF
     * podria tener repercusiones negativas.
     */
    static public String EOL="\n";
	/**
     * Constante de fin de linea.
     */
    static public char   EOLchar='\n';
	public static String rellenaConCerosIzquierda(String aRellenar, int tamanoFinal) {
		String nuevaCadena = "";
		
		if (tamanoFinal <= aRellenar.length()) {
			return aRellenar;
		}
		
		for (int i = 0 ; i < tamanoFinal - aRellenar.length() ; i++) {
			nuevaCadena += "0";
		}
		
		return nuevaCadena + aRellenar;
	}
	
	/**
     * Recibe un arreglo de strings y retorna una lista separada por comas
     * basada en su contenido. Lo unico que hace es invocar a
     * <code>joinFiltrado(", ", palabras)</code>.
     *
     * @param palabras los strings a unir; el arreglo puede ser
     *                 <code>null</code> o tener cero elementos; si tiene
     *                 elementos, estos pueden ser <code>null</code> (pero
     *                 seran descartados).
     * @return un string con todos los elementos (que no se descartaron)
     *         unidos; en el peor de los casos se retorna <code>""</code>.
     * @see #joinFiltrado
     * @since 1.0
     */
    static public String listaSeparadaPorComas(String[] palabras) {
        return joinFiltrado(", ", palabras);
    }
    
    /**
     * Recibe un arreglo de strings y los une en uno solo, poniendo el
     * separador que se indique entremedio de cada par de strings originales.
     * La diferencia con el m�todo join() es que aqu� se descartan los
     * strings nulos, vac�os (<code>""</code>) o blancos (<code>" "</code>,
     * ect�tera).
     * <p>
     *
     * Ejemplos:<pre>
     * - joinFiltrado("  ", {"hola", "que", "tal"})                 entregar� "hola  que  tal" (festival).
     * - joinFiltrado("/",  {"Pedro", " Pablo", "Vilma ", "Betty"}) entregar� "Pedro/Pablo/Vilma/Betty".
     * - joinFiltrado("",   {"a", null, "i ", "o", "u"})            entregar� "aiou".
     * - joinFiltrado("$",  {"1", "  ", " 2 ", "  "})               entregar� "1$2".
     * - joinFiltrado(null, {"hola", null, "mundo"})                entregar� "holamundo".
     * - joinFiltrado("#",  {})                                     entregar� "".
     * - joinFiltrado("#",  null)                                   entregar� "".
     * </pre>
     *
     * @param separador el string a poner entremedio de cada elemento del
     *                  arreglo; si se entrega <code>null</code>, se
     *                  reemplazar� por <code>""</code>. Ojo: no es lo mismo
     *                  <code>""</code> que <code>" "</code> (ver los
     *                  ejemplos).<p>
     *
     * @param elementos los strings a unir; el arreglo puede ser
     *                  <code>null</code> o tener cero elementos; si tiene
     *                  elementos, estos pueden ser <code>null</code> (pero
     *                  ser�n descartados).<p>
     *
     * @return un string con todos los elementos (que no se descartaron)
     *         unidos; en el peor de los casos se retorna <code>""</code>.
     * @see #join
     * @since 1.0
     */
    static public String joinFiltrado(String separador, String[] elementos) {
        String x="";
        if (separador==null) separador="";
        if ((elementos!=null)&&(elementos.length>0)) {
            for(int i=0;i<elementos.length;i++) {
                String elemento=elementos[i];
                if ((elemento!=null)&&(!(elemento.trim().equals("")))) x+=(elemento.trim()+separador);
                // Notese el trim() del elemento - esto es intencional.
            }
        }
        if (x.length()>0) x=x.substring(0, x.length()-separador.length()); // Elimino el separador final.
        return x;
    }
    
    /**
     * Parte un string, utilizando como separadores todos los caracteres
     * delimitadores que se indiquen.
     *
     * @param divideme      El string a dividir.<p>
     *
     * @param delimitadores Los caracteres delimitadores que se usar�n; si se
     *                      entrega <code>null</code> se reemplazar� por
     *                      <code>""</code>; esto �ltimo ES v�lido e implica
     *                      que el string no se partir� (claro que entonces
     *                      no tiene mucho sentido invocar a este
     *                      m�todo...).<p>
     *
     * @return un arreglo de strings, con cada uno de los segmentos en que se
     *         divide el string inicial; los delimitadores NO seran parte de
     *         ninguno; <code>null</code> es un resultado valido, producto de
     *         haber entregado un string nulo; si se entrega un string vacio
     *         o compuesto solamente por delimitadores, el resultado sera un
     *         arreglo de cero elementos (independientemente de lo que
     *         contuviere el parametro delimitadores).<p>
     *
     * @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/util/StringTokenizer.html">StringTokenizer</a>
     * @since 1.0
     */
    static public String[] divide(String divideme, String delimitadores) {
        if (divideme==null) return null;
        if (delimitadores==null) delimitadores="";

        StringTokenizer t=new StringTokenizer(divideme, delimitadores);
        String[] resultado=new String[t.countTokens()];
        int i=0;

        while (t.hasMoreTokens()) {
            resultado[i++]=t.nextToken();
        }
        return resultado;
    }
    
    static public List<String> divideALista(String divideme, String delimitadores) {
        if (divideme == null) {
        	return null;
        }
        if (delimitadores == null) {
        	delimitadores="";
        }

        StringTokenizer t = new StringTokenizer(divideme, delimitadores);
        List<String> resultado = new ArrayList<String>();

        while (t.hasMoreTokens()) {
        	resultado.add(t.nextToken());
        }
        return resultado;
    }
}
