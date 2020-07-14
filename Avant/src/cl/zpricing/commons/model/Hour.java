package cl.zpricing.commons.model;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class Hour {
	private int hour;
	private int minutes;
	
	public Hour(String hourAsString) {
		StringTokenizer tokeneizer = new StringTokenizer(hourAsString, ":");
		this.hour = Integer.valueOf((String) tokeneizer.nextElement());
		this.minutes = Integer.valueOf((String) tokeneizer.nextElement());
	}
	
	public Hour(Date date) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(date);
		this.hour = calendario.get(Calendar.HOUR_OF_DAY);
		this.minutes = calendario.get(Calendar.MINUTE);
	}
	
	public boolean isGreaterThan(Hour hourToCompare) {
		if (this.hour > hourToCompare.getHour() || (this.hour >= hourToCompare.getHour() && this.minutes >= hourToCompare.getHour()) ) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return this.hour + ":" + this.minutes;
	}
	
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
}
