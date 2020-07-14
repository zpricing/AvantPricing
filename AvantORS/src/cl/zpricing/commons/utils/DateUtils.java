package cl.zpricing.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * <p>Clase con utilidades para el manejo y formateo de fechas.</p>
 *
 * <p>
 * <b>Registro de versiones:</b>
 * <ul>
 * 	   <li>1.0 (27-08-2009: Nicolás Dujovne W.): versión inicial.</li>
 * </ul>
 * </p>
 * <p>
 *   <b>Todos los derechos reservados por ZhetaPricing.</b>
 * </p>
 */
public class DateUtils {
	
	public static final String[] MESES = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"}; 
	public static final String[] DIAS_DE_SEMANA = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
	public static final String[] DIAS_DE_SEMANA_LUNES_A_DOMINGO = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado","Domingo"};
	
	public static final SimpleDateFormat format_ddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
	public static final SimpleDateFormat format_ddMMyyyy_HHmm = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	public static final SimpleDateFormat format_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat format_ddMM = new SimpleDateFormat("dd-MM");
	public static final SimpleDateFormat format_EEE_ddMMyyyy_HHmm = new SimpleDateFormat("EEE dd-MM-yyyy HH:mm");
	public static final SimpleDateFormat format_HHmmss = new SimpleDateFormat("HH:mm:ss");
	
	public static final SimpleDateFormat FORMAT_ISODATE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	
	/**
	 * <p>Valida si las fechas recibidas por parametros corresponden al mismo día, ignorando la hora.</p>
	 * <p>
	 * <b>Registro de versiones:</b>
	 * <ul>
	 * 	   <li>1.0 (27-08-2009: Nicolás Dujovne W.): version inicial.</li>
	 * </ul>
	 * </p>
	 *
	 * @param a Fecha para comparar día.
	 * @param b Fecha para comparar día.
	 * @return verdadero si las fechas corresponden al mismo día o falso en caso contrario.
	 */
	public static boolean esMismoDia(Date a, Date b) {
		Calendar cal_a = Calendar.getInstance();
		cal_a.setTime(a);
		Calendar cal_b = Calendar.getInstance();
		cal_b.setTime(b);
		
		if (cal_a.get(Calendar.YEAR) != cal_b.get(Calendar.YEAR)) {
			return false;
		}
		else if (cal_a.get(Calendar.MONTH) != cal_b.get(Calendar.MONTH)) {
			return false;
		}
		else if (cal_a.get(Calendar.DATE) != cal_b.get(Calendar.DATE)) {
			return false;
		}
		
		return true;
	}
		
	public static int cantidadDeDias(Date a, Date b) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(a);
		
	    if (a.compareTo(b) > 0) {
	    	return -1;
	    }
	
	    int contador = 0;
	    while (!esMismoDia(cal.getTime(), b)) {
			contador++;
			cal.add(Calendar.DATE, 1);
	    }
	    return contador;
	}
	
	public static Calendar obtenerCalendario() {
	    return obtenerCalendario(new Date());
	}
	
	public static Calendar obtenerCalendario(Date fecha) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fecha);
	    calendario.set(Calendar.HOUR, 0);
	    calendario.set(Calendar.HOUR_OF_DAY, 0);
	    calendario.set(Calendar.MINUTE, 0);
	    calendario.set(Calendar.SECOND, 0);
	    calendario.set(Calendar.MILLISECOND, 0);
	    return calendario;
	}
	
	public static Date obtenerSoloFecha(Calendar cal) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		return format.parse(cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH ) + 1) + "-" + cal.get(Calendar.YEAR)); 
	}
	
	public static String[] obtenerAnos(int anoInicio, int anoFin) {
		String[] anos = new String[(anoFin - anoInicio) + 1];
		for (int i = 0 ; i <= anoFin - anoInicio ; i++) {
			anos[i] = Integer.toString(i + anoInicio);
		}
		return anos;
	}
	
	/**
	 * 
	 * Retorna el numero de mes dentro del año de la fecha
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 (28-12-2009 Daniel Estévez Garay): Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param fecha
	 * @return numero de mes del 1 al 12
	 * @since 1.0
	 */
	public static int numeroMes(Date fecha){
		Calendar c = new GregorianCalendar();
		c.setTime(fecha);
		return c.get(Calendar.MONTH)+1;
	}
	/**
	 * 
	 * Retorna el numero de semana dentro del mes de la fecha. El numero de semana es relativo al primer día del mes, si este
	 * es feriado no se cuenta. De esta forma las semanas estan dadas por los siguientes días:
	 * 		semana numero 		rango días
	 * 			1					1-7
	 * 			2					8-14
	 * 			3					15-21
	 * 			4					22-28
	 * 			5					29-30(31)
	 *
	 *		En caso de que el primer día sea feriado las semanas se desplazan en un día
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 (28-12-2009 Daniel Estévez Garay): Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param fecha
	 * @return numero de semana dentro de mes
	 * @since 1.0
	 */
	public static int numeroSemana(Date fecha){
		Calendar calendar = new GregorianCalendar();
		int correccion =0;
		calendar.setTime(fecha);
		/**
		 * se testea si el primer día es feriado
		 * 
		 * en caso afirmativo se desplaza la generación de semanas en un día
		 */
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		if(esFeriado(calendar.getTime())){
			correccion=1;
		}
		
		
		calendar.setTime(fecha);
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		/**
		 * Test de rangos
		 */
		if(dia>=1+correccion && dia<=7+correccion){
			return 1;
		}
		else if(dia>=8+correccion && dia<=14+correccion){
			return 2;
		}
		else if(dia>=15+correccion && dia<=21+correccion){
			return 3;
		}
		else if(dia>=22+correccion && dia<=28+correccion){
			return 4;
		}	
		
		return -1;
	}
	
	
	/**
	 * 
	 * Retorna el numero de semanas en el año de la fecha proporcionada
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 (17-02-2010 Daniel Estévez Garay): Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param fecha fecha del año a consultar
	 * @return
	 * @since 1.0
	 */
	public static int numeroDeSemanasAno(Date fecha){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		return calendar.getMaximum(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 
	 * Retorna el numero de semanas del mes de la fecha proporcionada
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 (17-02-2010 Daniel Estévez Garay): Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param fecha fecha del mes a consultar
	 * @return
	 * @since 1.0
	 */
	public static int numeroDeSemanasMes(Date fecha){
		/**
		 * por convención se establece para todos los meses un máximo de 5 semanas
		 */
		//FIXME: verificar correctitud en afirmacion siempre para demonio de season factor
		return 4;
	}
	
	/**
	 * 
	 * Retorna el numero de día dentro de la semana de la fecha. La distribución es la siguiente:
	 * 
	 * 		día			número
	 * 		Lunes			1
	 * 		Martes			2
	 * 		Miércoles		3
	 *		Jueves			4
	 *		Viernes			5
	 *		Sábado			6
	 *		Domingo			7
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 (28-12-2009 Daniel Estévez Garay): Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param fecha
	 * @return numero de día del 1 al 7
	 * @since 1.0
	 */
	public static int diaSemana(Date fecha){
		Calendar c = new GregorianCalendar();
		c.setTime(fecha);
		return c.get(Calendar.DAY_OF_WEEK) == 1 ?  7 : c.get(Calendar.DAY_OF_WEEK) -1;
	}
	/**
	 * 
	 * Retorna si un día es feriado en el calendario local
	 *
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 *   <li> 1.0 (18-02-2010 Daniel Estévez Garay): Versión Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @return verdadero en caso de ser feriado, falso en caso contrario
	 * @since 1.0
	 */
	public static boolean esFeriado(Date fecha){
		//TODO: implementar con markers u método a conveniencia
		return false;
	}
	
	public static String obtenerRepresentacionDia(int diaSemana) {
		if (Calendar.MONDAY == diaSemana) {
			return "L";
		}
		if (Calendar.TUESDAY == diaSemana) {
			return "M";
		}
		if (Calendar.WEDNESDAY == diaSemana) {
			return "W";
		}
		if (Calendar.THURSDAY == diaSemana) {
			return "J";
		}
		if (Calendar.FRIDAY == diaSemana) {
			return "V";
		}
		if (Calendar.SATURDAY == diaSemana) {
			return "S";
		}
		if (Calendar.SUNDAY == diaSemana) {
			return "D";
		}
		return null;
	}
	
	public static Date getUTCdate(String date) throws ParseException {
		SimpleDateFormat utcFormat = (SimpleDateFormat) DateUtils.format_yyyyMMdd.clone();
		utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return utcFormat.parse(date);
	}
	
	public static Date getUTCdate(Date date) throws ParseException {
		String date_as_string = DateUtils.format_yyyyMMdd.format(date);
		return DateUtils.getUTCdate(date_as_string);
	}
	
	public static String obtenerRepresentacionDia(Date date) {
		Calendar calendario = DateUtils.obtenerCalendario(date);
		return DateUtils.obtenerRepresentacionDia(calendario.get(Calendar.DAY_OF_WEEK));
	}
	
	public static void main(String[] args) {
		try {
			Date fecha = DateUtils.obtenerSoloFecha(DateUtils.obtenerCalendario());
			System.out.println(DateUtils.obtenerCalendario().getTime());
			System.out.println(fecha);
			
			SimpleDateFormat FORMAT_ISODATE = (SimpleDateFormat) DateUtils.FORMAT_ISODATE.clone();
			FORMAT_ISODATE.setTimeZone(TimeZone.getTimeZone("UTC"));
			
			System.out.println(FORMAT_ISODATE.format(DateUtils.getUTCdate("2013-03-20")));
			System.out.println(FORMAT_ISODATE.format(DateUtils.getUTCdate(new Date())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
