package cl.zpricing.avant.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.log4j.Logger;

import cl.zpricing.avant.model.Categoria;
import cl.zpricing.avant.model.Epoca;
import cl.zpricing.avant.model.Publico;
import cl.zpricing.avant.servicios.ClaseDao;

/**
 * 
 * <b>Clase de utilidades generales</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 26-12-2008 Daniel Est�vez Garay: versi�n inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zetha Pricing.</B>
 * <P>
 */
public class Util {

	private ClaseDao claseDao;

	/**
	 * @return the claseDao
	 */
	public ClaseDao getClaseDao() {
		return claseDao;
	}

	/**
	 * @param claseDao
	 *            the claseDao to set
	 */
	public void setClaseDao(ClaseDao claseDao) {
		this.claseDao = claseDao;
	}

	/**
	 * Impresi�n de log.
	 */
	private Logger log = (Logger) Logger.getLogger(this.getClass());

	/**
	 * 
	 * Cast de ArrayList de objetos Categoria a Array de Strings de los id de
	 * Categoria.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param arrayList
	 *            ArrayList de objetos Categoria.
	 * @return Array de String.
	 * @since 1.0
	 */
	static public String[] toArrayCategoria(ArrayList<Categoria> arrayList) {
		Iterator<Categoria> it = arrayList.iterator();
		String[] array = new String[arrayList.size()];
		for (int i = 0; it.hasNext(); i++)
			array[i] = it.next().getId() + "";

		return array;
	}

	/**
	 * 
	 * Cast de ArrayList de objetos Publico a Array de Strings de los id de
	 * Publico.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param arrayList
	 *            ArrayList de objetos Publico.
	 * @return Array de String.
	 * @since 1.0
	 */
	static public String[] toArrayPublico(ArrayList<Publico> arrayList) {
		Iterator<Publico> it = arrayList.iterator();
		String[] array = new String[arrayList.size()];
		for (int i = 0; it.hasNext(); i++)
			array[i] = it.next().getId() + "";

		return array;
	}

	/**
	 * 
	 * Cast de ArrayList de objetos Epoca a Array de Strings de los id de Epoca.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-12-2008 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param arrayList
	 *            ArrayList de objetos Epoca.
	 * @return Array de String.
	 * @since 1.0
	 */
	static public String[] toArrayEpoca(ArrayList<Epoca> arrayList) {
		Iterator<Epoca> it = arrayList.iterator();
		String[] array = new String[arrayList.size()];
		for (int i = 0; it.hasNext(); i++)
			array[i] = it.next().getId() + "";

		return array;
	}

	/**
	 * 
	 * Descripci�n de M�todo.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 13-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param string
	 * @return
	 * @since 1.0
	 */
	public String[] getArray(String[] string) {
		for (int i = 0; i < string.length; i++)
			log.debug("IDArray: " + string[i]);

		return string;

	}

	/**
	 * M�todo que rellena un int con ceros a la izquierda retornando un String.
	 * 
	 * @param numero
	 *            N�mero a rellenar con ceros a la izquierda.
	 * @param cerosArellenar
	 *            Cantidad de ceros a rellenar.
	 * @return Retorna un String con ceros a la izquierda.
	 */
	static public String rellenaCerosIzquierda(int numero, int cerosArellenar) {
		String resultado = String.valueOf(numero);
		return rellenaCerosIzquierda(resultado, cerosArellenar);
	}

	/**
	 * M�todo que rellena un String con ceros a la izquierda.
	 * 
	 * @param numero
	 *            String a rellenas con ceros a la izquierda.
	 * @param cerosArellenar
	 *            cantidad de ceros a rellenar.
	 * @return retorna el numero con ceros a la izquierda.
	 */
	static public String rellenaCerosIzquierda(String numero, int cerosArellenar) {
		String ceros = "";
		for (int i = 0; i < cerosArellenar; i++) {
			ceros += "0";
		}
		return ceros + numero;
	}

	/**
	 * Metodo para sacar la diferencia entre dias
	 * 
	 * @param fecha1
	 * @param fecha2
	 * @return diferencia entre dias
	 */
	public static int diferenciaFechas(GregorianCalendar fecha1,
			GregorianCalendar fecha2) {
		long diferenciaFechaMillis = fecha1.getTimeInMillis()
				- fecha2.getTimeInMillis();
		return (int) (((double) diferenciaFechaMillis) / (1000.0 * 60.0 * 60.0 * 24.0));
	}

	/**
	 * Para probar funciones
	 * 
	 * @param args
	 */
	/*
	 * public static void main(String[] args){ GregorianCalendar fecha1 = new
	 * GregorianCalendar(2007,1,16); GregorianCalendar fecha2 = new
	 * GregorianCalendar(2008,1,16);
	 * System.out.println("diferencia "+diferenciaFechas(fecha1, fecha2)); }
	 */

	/**
	 * Pasa un elemento de tipo Date a un string de formato dd-mm-a�o para
	 * desplegarlo en un formulario.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 20-01-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param date
	 * @return
	 * @since 1.0
	 * @deprecated Utilizar DateUtils
	 */
	static public String DateToString(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH) + 1;
		int anio = calendar.get(Calendar.YEAR);
		String separador = "-";
		String result = Integer.toString(dia) + separador
				+ Integer.toString(mes) + separador + Integer.toString(anio);
		return result;
	}

	/**
	 * Obtiene del objeto Date las horas y minutos y devuelve un String en el
	 * formato HH:mm.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 02-06-2009 Mario Lavandero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param date
	 * @return String
	 * @since 2.0
	 */
	static public String DateToHourString(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		String result = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY))
				+ ":" + Integer.toString(calendar.get(Calendar.MINUTE));
		return result;
	}

	/**
	 * Pasa un elemento de tipo Date a un string de formato yyyy-mm-dd para
	 * desplegarlo en un formulario.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 20-01-2009 Oliver Cordero : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param date
	 * @return
	 * @since 1.0
	 */
	static public String DateToString2(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH) + 1;
		int anio = calendar.get(Calendar.YEAR);
		String separador = "-";
		String result = Integer.toString(anio) + separador
				+ Integer.toString(mes) + separador + Integer.toString(dia);
		return result;
	}

	/**
	 * Pasa un string en formato dd-mm-a�o obtenido de un formulario a un objeto
	 * de tipo Date.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 20-01-2009 MARIO : Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param string
	 * @return
	 * @since 1.0
	 * @deprecated Utilizar SimpleDateFormat en vez de este metodo.
	 */
	static public Date StringToDate(String string) {
		String separador = "-";
		String[] temp = string.split(separador);
		int dia = Integer.parseInt(temp[0]);
		int mes = Integer.parseInt(temp[1]);
		int anio = Integer.parseInt(temp[2]);
		GregorianCalendar cal = new GregorianCalendar(anio, mes - 1, dia);
		return cal.getTime();
	}

	/**
	 * 
	 * Calcula el m�ximo com�n divisor entre dos n�meros. Orden log(n)
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 21-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param a
	 *            primer entero
	 * @param b
	 *            segundo entero
	 * @return entero que es el m�ximo com�n divisor
	 * @since 1.0
	 */
	static public int MCD(int a, int b) {
		while (a != b) {
			if (a < b)
				b -= a;
			else
				a -= b;
		}
		return a;
	}

	/**
	 * 
	 * Calcula el m�ximo com�n divisor de una lista de enteros. Orden nlog(n)
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 21-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param a
	 *            lista de enteros
	 * @return entero que es el m�ximo com�n divisor. 1 en caso de error.
	 * @since 1.0
	 */
	static public int MCD2(double[] a) {

		if (a.length < 2)
			return 1;
		int mcd = (int) a[0];
		for (int i = 1; i < a.length; i++)
			mcd = MCD(mcd, (int) a[i]);
		return mcd;
	}

	/**
	 * 
	 * M�todo quicksort que ordena una lista de n�meros de doble precisi�n, en
	 * un rango de �ndices.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param a
	 *            lista de n�meros de doble precisi�n
	 * @param imin
	 *            �ndice de partida del rango
	 * @param imax
	 *            �ndice de t�rmino del rango
	 * @since 1.0
	 */
	static public void quicksort(double[] a, int imin, int imax) {
		if (imin >= imax)
			return;
		int k = particionar(a, imin, imax);
		quicksort(a, imin, k - 1);
		quicksort(a, k + 1, imax);
	}

	/**
	 * 
	 * M�todo particionar que es parte del m�todo de ordenamiento quicksort.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param a
	 *            lista de n�meros de doble precisi�n
	 * @param imin
	 *            �ndice de partida del rango
	 * @param imax
	 *            �ndice de t�rmino del rango
	 * @return �ndice en el cual se particiona el rango
	 * @since 1.0
	 */
	static public int particionar(double[] a, int imin, int imax) {
		int ipiv = imin;
		int k = imin;
		int j = k + 1;
		while (j <= imax) {
			if (a[j] < a[ipiv]) {
				k = k + 1;
				intercambiar(a, k, j);
			}
			j = j + 1;
		}
		intercambiar(a, k, ipiv);
		return k;
	}

	/**
	 * 
	 * M�todo que intercambia dos n�meros dentro de una lista en funci�n de sus
	 * �ndices.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 26-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param a
	 *            lista de n�meros de doble precisi�n
	 * @param i
	 *            �ndice a intercambiar
	 * @param j
	 *            �ndice a intercambiar
	 * @since 1.0
	 */
	static public void intercambiar(double[] a, int i, int j) {
		double aux1 = a[i];

		a[i] = a[j];
		a[j] = aux1;

	}

	/**
	 * 
	 * Metodo de ordenamiento quicksort de una lista de Strings.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 13-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param a
	 *            lista de Strings
	 * @param i
	 *            �ndice de comienzo del rango
	 * @param j
	 *            �ndice de t�rmino del rango
	 * @since 1.0
	 */
	static public void quicksortString(String[] a, int imin, int imax) {
		if (imin >= imax)
			return;
		int k = particionarString(a, imin, imax);
		quicksortString(a, imin, k - 1);
		quicksortString(a, k + 1, imax);
	}

	/**
	 * 
	 * M�todo particionar que es parte del m�todo de ordenamiento
	 * quicksortString.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 13-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param a
	 *            lista de Strings
	 * @param imin
	 *            �ndice de comienzo del rango
	 * @param imax
	 *            �ndice de t�rmino del rango
	 * @returnn �ndice en el cual se particiona el rango
	 * @since 1.0
	 */
	static public int particionarString(String[] a, int imin, int imax) {
		int ipiv = imin;
		int k = imin;
		int j = k + 1;
		while (j <= imax) {
			if (a[j].compareTo(a[ipiv]) < 0) {
				k = k + 1;
				intercambiarString(a, k, j);
			}
			j = j + 1;
		}
		intercambiarString(a, k, ipiv);
		return k;
	}

	/**
	 * 
	 * M�todo que intercambia String dentro de una lista de Strings.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param a
	 *            lista de Strings
	 * @param i
	 *            �ndice a intercambiar
	 * @param j
	 *            �ndice a intercambiar
	 * @since 1.0
	 */
	static public void intercambiarString(String[] a, int i, int j) {
		String aux1 = a[i];

		a[i] = a[j];
		a[j] = aux1;

	}

	/**
	 * 
	 * M�todo quicksort que ordena el primer array de n�meros manteniendo el
	 * orden en funcion de �ndices con otro array
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 21-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param a
	 *            array a ordenar
	 * @param b
	 *            array con el que se mantiene orden en cuanto a �ndice
	 * @param imin
	 *            �ndice de comienzo de rango
	 * @param imax
	 *            �ndice de termino de rango
	 * @since 1.0
	 */
	static public void quicksortDoble(double[] a, double[] b, int imin, int imax) {
		if (imin >= imax)
			return;
		int k = particionarDoble(a, b, imin, imax);
		quicksortDoble(a, b, imin, k - 1);
		quicksortDoble(a, b, k + 1, imax);
	}

	/**
	 * 
	 * M�todo particionar del algoritmo quicksortDoble que mantiene el orden de
	 * un segundo array en funci�n del que se est�a ordenando .
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param a
	 *            array a ordenar
	 * @param b
	 *            array con el que se mantiene orden en cuanto a �ndice
	 * @param imin
	 *            �ndice de comienzo de rango
	 * @param imax
	 *            �ndice de t�rmino de rango
	 * @return
	 * @since 1.0
	 */
	static public int particionarDoble(double[] a, double[] b, int imin,
			int imax) {
		int ipiv = imin;
		int k = imin;
		int j = k + 1;
		while (j <= imax) {
			if (a[j] < a[ipiv]) {
				k = k + 1;
				intercambiarDoble(a, b, k, j);
			}
			j = j + 1;
		}
		intercambiarDoble(a, b, k, ipiv);
		return k;
	}

	/**
	 * 
	 * M�todo que intercambia dos n�meros dentro de una lista manteniendo el
	 * orden en cuanto a �ndices de un segundo array.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param a
	 *            array a ordenar
	 * @param b
	 *            array con el que se mantiene orden en cuanto a �ndice
	 * @param i
	 *            �ndice a intercambiar
	 * @param j
	 *            �ndice a intercambiar
	 * @since 1.0
	 */
	static public void intercambiarDoble(double[] a, double[] b, int i, int j) {
		double aux1 = a[i];
		double aux2 = b[i];
		a[i] = a[j];
		b[i] = b[j];
		a[j] = aux1;
		b[j] = aux2;
	}

	/**
	 * 
	 * Realiza una interpolacion lineal de un valor entre 2 puntos
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 21-01-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param x1
	 *            x1 x1<x2
	 * @param x2
	 *            x2 x1<x2
	 * @param y1
	 *            y1 f(x1)=y1
	 * @param y2
	 *            y2 f(x2)=y2
	 * @param x
	 *            n�mero a interpolar
	 * @return f(x) asumiendo linealidad en la funci�n
	 */
	static public double interpolar(double x1, double x2, double y1, double y2,
			double x) {
		return (y1 - y2) * (x - x1) / (x1 - x2) + y1;
	}

	/**
	 * 
	 * Devuelve un String con el n�mero formateado para presentaci�n, es decir,
	 * con punto cada tres digitos.
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 28-01-2009 Julio Olivares Alarc�n: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param num
	 *            numero a formatear
	 * @since 1.0
	 */
	static public String formatNumber(double num) {

		NumberFormat nf = NumberFormat.getInstance();

		return nf.format(num);
	}

	/**
	 * 
	 * Devuelve un String con el n�mero formateado para presentaci�n, es decir,
	 * con punto cada tres digitos
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 28-01-2009 Julio Olivares Alarc�n: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param num
	 *            numero a formatear
	 * @since 1.0
	 */
	static public String formatNumber(int num) {

		NumberFormat nf = NumberFormat.getInstance();

		return nf.format(num);
	}

	/**
	 * 
	 * Devuelve un String con el n�mero formateado para presentaci�n, es decir,
	 * con punto cada tres digitos
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 28-01-2009 Julio Olivares Alarc�n: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param num
	 *            numero a formatear
	 * @since 1.0
	 */
	static public String formatNumber(long num) {

		NumberFormat nf = NumberFormat.getInstance();

		return nf.format(num);
	}

	/**
	 * 
	 * M�todo de apoyo para ser aplicado en las vistas mediante
	 * date-function.tld. Devuelve verdadero en caso de que una fecha este
	 * dentro de un rango de fechas.
	 * 
	 * <P>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18-02-2009 Daniel Est�vez Garay: Versi�n Inicial</li>
	 * </ul>
	 * </P>
	 * 
	 * @param test
	 *            fecha a ser probada
	 * @param inicio
	 *            fecha inicio del rango
	 * @param fin
	 *            fecha t�rmino del rango
	 * @return verdadero en caso de que esta dentro del rango, falso en caso
	 *         contrario.
	 * @since 1.0
	 */
	static public boolean between(Date test, Date inicio, Date fin) {
		if (fin != null) {
			if (test.compareTo(fin) == 0 || test.compareTo(inicio) == 0
					|| (test.after(inicio) && test.before(fin)))
				return true;
		}
		if (test.compareTo(inicio) == 0)
			return true;
		return false;
	}
	
	static public int redondeaCapacidad(double capacidad) {
		return (int) Math.round(capacidad);
	}

}
