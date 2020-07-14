package cl.zpricing.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cl.zpricing.commons.exceptions.FormatoFechaException;

public class Calendario {
	public static final SimpleDateFormat format_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	private Calendar calendario;
	
	public Calendario() {
		calendario = Calendar.getInstance();
	}
	
	public Calendario(String fecha) throws FormatoFechaException {
		calendario = Calendar.getInstance();
		validarFormatoFecha(fecha);
		try {
			Date fechaFormateada = format_yyyyMMdd.parse(fecha);
			configurarFecha(fechaFormateada);
		} catch (ParseException e) {
			throw new FormatoFechaException("Fecha mal formateada: dd-mm-yyyy");
		}
	}
	
	private void validarFormatoFecha(String fecha) throws FormatoFechaException {
		String expression = "(19|20)\\d{2}-(0[1-9]|1[012]|[1-9])-(0[1-9]|[1-9]|[12][0-9]|3[01])$";  
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(fecha);
		
		if (!matcher.matches()) {
			throw new FormatoFechaException("Fecha mal formateada: [" + fecha + "]. Debe tener el siguiente formato yyyy-mm-dd");
		}
	}
	
	private void configurarFecha(Date fecha) {
		this.calendario.setTime(fecha);
		this.calendario.set(Calendar.HOUR, 0);
		this.calendario.set(Calendar.HOUR_OF_DAY, 0);
		this.calendario.set(Calendar.MINUTE, 0);
		this.calendario.set(Calendar.SECOND, 0);
		this.calendario.set(Calendar.MILLISECOND, 0);
	}
	
	public Date getDate() {
		return this.calendario.getTime();
	}
	
	
}
