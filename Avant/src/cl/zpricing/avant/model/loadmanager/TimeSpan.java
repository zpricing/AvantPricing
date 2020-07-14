/**
 * 
 */
package cl.zpricing.avant.model.loadmanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import cl.zpricing.avant.model.DescripcionId;

/**
 * Representa un rango de tiempo.  Dado por uno o varios dias a la semana y un
 * rango de horas dentro de esos dias.
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 29-01-2009 MARIO: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */
public class TimeSpan extends DescripcionId {

	private String dias;
	private int horaInicio;
	private int horaFin;
	
	
	/**
	 * @return dias
	 */
	public String getDias() {
		return dias;
	}
	/**
	 * @param dias
	 */
	public void setDias(String dias) {
		this.dias = dias;
	}
	/**
	 * @return hora de inicio
	 */
	public int getHoraInicio() {
		return horaInicio;
	}
	/**
	 * @param horaInicio
	 */
	public void setHoraInicio(int horaInicio) {
		this.horaInicio = horaInicio;
	}
	/**
	 * @return hora de termino
	 */
	public int getHoraFin() {
		return horaFin;
	}
	/**
	 * @param hora de termino
	 */
	public void setHoraFin(int horaFin) {
		this.horaFin = horaFin;
	}
	
	/**
	 * @return lista de dias
	 */
	public ArrayList<Integer> getDiasCalendar()
	{
		String[] temp = this.dias.split("_");
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < temp.length; i++) {
			result.add(this.letterToInt(temp[i]));
		}
		return result;
	}
	
	/**
	 * Retorna si un dia esta en el time span
	 * @param day
	 * @return si esta en el time span
	 */
	public boolean isDayInTimeSpan(int day)
	{
		Iterator<Integer> iter = this.getDiasCalendar().iterator();
		while(iter.hasNext())
		{
			if(iter.next() == day)
				return true;
		}
		return false;
	}
	
	/**
	 * Retorna si una hora esta en el time span
	 * @param hour
	 * @return si esta en el time span
	 */
	public boolean isHourInTimeSpan(int hour)
	{
		int hora;
		if(hour >= 0 && hour <= 3 || hour == 24)
			hora = 23;
		else
			hora = hour;
		if(hora >= this.horaInicio && hora < this.horaFin)
			return true;
		return false;
	}
	
	
	/**
	 * Pasa la letra de un dia de la semana a la constantes que le corresponde en Calendar
	 * @param letter
	 * @return constante del dia de la semana en Calendar
	 */
	private int letterToInt(String letter)
	{
		if(letter.equalsIgnoreCase("L"))
			return Calendar.MONDAY;
		else if(letter.equalsIgnoreCase("M"))
			return Calendar.TUESDAY;
		else if(letter.equalsIgnoreCase("W"))
			return Calendar.WEDNESDAY;
		else if(letter.equalsIgnoreCase("J"))
			return Calendar.THURSDAY;
		else if(letter.equalsIgnoreCase("V"))
			return Calendar.FRIDAY;
		else if(letter.equalsIgnoreCase("S"))
			return Calendar.SATURDAY;
		else if(letter.equalsIgnoreCase("D"))
			return Calendar.SUNDAY;
		return -1;
	}
	
}
