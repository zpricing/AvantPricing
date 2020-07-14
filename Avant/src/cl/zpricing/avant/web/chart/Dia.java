/**
 * 
 */
package cl.zpricing.avant.web.chart;

import java.util.GregorianCalendar;

/**
 * <b>Descripci�n de la Clase</b> Clase para generar los strings correspondiente
 * a una fecha
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 08-01-2009 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class Dia {

	/**
	 * @param numero
	 *            de dia de la semana
	 * @return string que representa a ese dia de la semana
	 */
	public static String getDia(int dia) {
		dia = dia % 7;
		if (dia == 0)
			dia = 7;
		switch (dia) {
		case GregorianCalendar.MONDAY:
			return "Lun";
		case GregorianCalendar.TUESDAY:
			return "Mar";
		case GregorianCalendar.WEDNESDAY:
			return "Mie";
		case GregorianCalendar.THURSDAY:
			return "Jue";
		case GregorianCalendar.FRIDAY:
			return "Vie";
		case GregorianCalendar.SATURDAY:
			return "Sab";
		case GregorianCalendar.SUNDAY:
			return "Dom";
		default:
			return null;
		}

	}

	/**
	 * @param mes
	 * @return string correspondiente al mes ingresado
	 */
	public static String getMes(int mes) {
		mes = mes % 12;
		switch (mes) {
		case GregorianCalendar.JANUARY:
			return "Enero";
		case GregorianCalendar.FEBRUARY:
			return "Febrero";
		case GregorianCalendar.MARCH:
			return "Marzo";
		case GregorianCalendar.APRIL:
			return "Abril";
		case GregorianCalendar.MAY:
			return "Mayo";
		case GregorianCalendar.JUNE:
			return "Junio";
		case GregorianCalendar.JULY:
			return "Julio";
		case GregorianCalendar.AUGUST:
			return "Agosto";
		case GregorianCalendar.SEPTEMBER:
			return "Septiembre";
		case GregorianCalendar.OCTOBER:
			return "Octubre";
		case GregorianCalendar.NOVEMBER:
			return "Noviembre";
		case GregorianCalendar.DECEMBER:
			return "Diciembre";
		default:
			return null;
		}
	}
}