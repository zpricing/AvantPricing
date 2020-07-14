package cl.zpricing.avant.test.unit;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import cl.zpricing.commons.exceptions.FormatoFechaException;
import cl.zpricing.commons.utils.Calendario;
import cl.zpricing.commons.utils.DateUtils;

public class CalendarioTest extends TestCase {
	public void testCalendario() throws FormatoFechaException, ParseException {
		Calendar cal = DateUtils.obtenerCalendario();
		String dateString = "2013-08-26"; 
		Date sessionDate = DateUtils.format_yyyyMMdd.parse(dateString);
		
		Date utcDate = DateUtils.getUTCdate(dateString, DateUtils.format_yyyyMMdd);
		
		
		String hora = "20:30";
		String time = DateUtils.format_ddMMyyyy.format(utcDate) + " " + hora;
		Date dateTime = DateUtils.format_ddMMyyyy_HHmm.parse(time);
		
		System.out.println("Cal.getTime : " + cal.getTime());
		System.out.println("Parse stringDate to Date : " + sessionDate);
		System.out.println("Parsed String date to UTC : " + utcDate);
		System.out.println("String datetime parsed to date : " + dateTime);
	}
}
