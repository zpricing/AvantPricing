/**
 * 
 */
package cl.zpricing.avant.web.chart;

import org.apache.log4j.Logger;

/**
 * <b>Descripci�n de la Clase</b> Clase para generar los colores con los que se
 * pintan los elementos de los graficos
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 07-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */

public class Color {
	public final static int ROJO = 0;
	public final static int VERDE = 1;
	public final static int AZUL = 2;
	public final static int AMARILLO = 3;
	public final static int PLOMO = 4;
	public final static int CELESTE = 5;
	public final static int NEGRO = 6;
	public final static int FUCSIA = 7;
	public final static int BLANCO = 8;

	private static int colorActual = 0;
	private final static int COLORES = 9;

	/**
	 * Transforma un numero entre 0 y 15 (decimal) a un numero entre 0 y F
	 * (hexadecimal)
	 * 
	 * @param i
	 *            el numero a transformar
	 * @return el string del numero en hexadecimal
	 */
	private static String iToH(int i) {
		switch (i) {
		case 10:
			return "A";
		case 11:
			return "B";
		case 12:
			return "C";
		case 13:
			return "D";
		case 14:
			return "E";
		case 15:
			return "F";
		default:
			return "" + i;
		}
	}

	/**
	 * Transforma un numero entre 0 y 255 (decimal) a un numero entre 00 y FF
	 * (hexadecimal)
	 * 
	 * @param n
	 *            el numero a transformar
	 * @return el string del numero en hexadecimal
	 */
	private static String intToHex(int n) {
		if (n > 255)
			n = 255;
		if (n < 0)
			n = 0;
		int ph = n / 16;
		int sh = n - ph * 16;
		return iToH(ph) + iToH(sh);
	}

	/**
	 * Genera un color con las intensidades puestas en los parametros
	 * 
	 * @param R
	 *            intensidad de rojo
	 * @param G
	 *            intensidad de verde
	 * @param B
	 *            intensidad de azul
	 * @return color correspondiente a las intensidades definidas
	 */
	public static String generarColor(int R, int G, int B) {
		return intToHex(R) + intToHex(G) + intToHex(B);
	}

	/**
	 * Genera colores en el orden establecido por las constantes de color
	 * Utilice iniciarColor() antes para siempre obtener el mismo orden de
	 * colores
	 * 
	 * @return color correspondiente a esta llamada
	 */
	public static String generarColor() {
		if (colorActual >= COLORES)
			colorActual = 0;
		Logger log = (Logger) Logger.getLogger("Color");
		log.debug("color " + colorActual);
		return generarColor(colorActual++);
	}

	/**
	 * Devuelve el color asignado a la constante de color
	 * 
	 * @param color
	 *            numero de la constante de color
	 * @return color asignado a la constante
	 */
	public static String generarColor(int color) {
		switch (color) {
		case ROJO:
			return "FF0000";
		case VERDE:
			return "00FF00";
		case AZUL:
			return "0000FF";
		case BLANCO:
			return "FFFFFF";
		case NEGRO:
			return "000000";
		case AMARILLO:
			return "FFFF00";
		case FUCSIA:
			return "FF00FF";
		case CELESTE:
			return "00FFFF";
		case PLOMO:
			return "999999";
		default:
			return "000000";
		}
	}

	/**
	 * Se debe usar para iniciar la asignacion de colores
	 */
	public static void iniciarColor() {
		colorActual = 0;
	}
}
