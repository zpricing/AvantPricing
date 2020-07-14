package cl.zpricing.avant.test.unit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import cl.zpricing.commons.utils.DateUtils;
import junit.framework.TestCase;

public class DateUtilsTest extends TestCase {
	public void testCantidadDeDias() {
		Calendar cal = Calendar.getInstance();
		Date a = cal.getTime();
		cal.add(Calendar.DATE, 3);
		Date b = cal.getTime();
		
		int result = DateUtils.cantidadDeDias(a, b);
		
		assertTrue(result < 5);
	}
	
	public void testUTCDate() throws ParseException {
		String dateAsString = "2013-09-13";
		
		SimpleDateFormat formatter = (SimpleDateFormat) DateUtils.format_yyyyMMdd_HHmmss.clone();
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Date date = DateUtils.format_yyyyMMdd_HHmmss.parse(dateAsString);
		System.out.println(date);
		Date dateUTC = formatter.parse(dateAsString);
		System.out.println(dateUTC);
		
		System.out.println(DateUtils.getUTCdate(dateAsString, DateUtils.format_yyyyMMdd));
		
		
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		cal.setTime(date);
		
		formatter.setCalendar(cal);
	}
}
