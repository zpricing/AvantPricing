package cl.zpricing.avant.model.restriccion;

import java.util.Date;

import org.apache.log4j.Logger;

import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.FuncionArea;
import cl.zpricing.commons.model.Hour;
import cl.zpricing.commons.utils.DateUtils;

public class PriceRule implements Rule {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private int id;
	private double precioMinimo;
	private String dias;
	private Hour horaDesde;
	
	public boolean apply(Funcion funcion, FuncionArea funcionArea) {
		log.debug("Aplicando Regla [" + this.id + "]");
		log.debug("  funcionArea.getPrecio() [" + funcionArea.getPrecio() + "]");
		log.debug("  this.precioMinimo [" + this.precioMinimo + "]");
		Hour sessionTime = new Hour(funcion.getFecha());
		if (funcionArea.getPrecio() == null) {
			log.warn("  FuncionArea tiene precio nulo.");
			return true;
		}
		else if (funcionArea.getPrecio().valorTotal() < this.precioMinimo 
				&& isDayInRule(funcion.getFechaContable()) 
				&& this.isHourGreaterThanRule(sessionTime)) {
			
			return true;
		}
		return false;
	}
	
	private boolean isDayInRule(Date sessionDate) {
		log.debug("   this.dias [" +  this.dias + "]");
		log.debug("   sessionDate [" +  DateUtils.obtenerRepresentacionDia(sessionDate) + "]");
		log.debug("  result [" + (this.dias.indexOf("*") > -1 || this.dias.indexOf(DateUtils.obtenerRepresentacionDia(sessionDate)) > -1) + "]");
		return this.dias.indexOf("*") > -1 || this.dias.indexOf(DateUtils.obtenerRepresentacionDia(sessionDate)) > -1;  
	}
	
	private boolean isHourGreaterThanRule(Hour sessionTime) {
		log.debug("  this.horaDesde [" + this.horaDesde + "]");
		log.debug("  sessionTime [" + sessionTime + "]");
		log.debug("  result [" + (this.horaDesde == null || sessionTime.isGreaterThan(this.horaDesde)) + "]");
		return this.horaDesde == null || sessionTime.isGreaterThan(this.horaDesde);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPrecioMinimo() {
		return precioMinimo;
	}
	public void setPrecioMinimo(double precioMinimo) {
		this.precioMinimo = precioMinimo;
	}
	public String getDias() {
		return dias;
	}
	public void setDias(String dias) {
		this.dias = dias;
	}
	public Hour getHoraDesde() {
		return horaDesde;
	}
	public void setHoraDesde(Hour hora) {
		this.horaDesde = hora;
	}
}
