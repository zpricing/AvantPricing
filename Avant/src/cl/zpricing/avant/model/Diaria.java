package cl.zpricing.avant.model;

import java.text.SimpleDateFormat;
import java.util.Date;



public class Diaria {
	
	private Date fecha;
	private int semana;
	private int asistencia_HT;
	private int ingreso_HT;
	private int asistencia_RM;
	private int ingreso_RM;
	private int asistencia_total;
	private int ingreso_total;
	private int asistencia_online;
	private int asistencia_online_prev_year;
	private float porc_asistencia_online;
	private float porc_asistencia_online_prev_year;
	private float porc_asistencia_HT;
	private float crec_asistencia_RM;
	private float prom_HT;
	private float prom_RM;
	private float prom_total;
	
	public int getSemana() {
		return semana;
	}
	public Date getFecha() {
		return fecha;
	}
	public int getAsistencia_HT() {
		return asistencia_HT;
	}
	public int getIngreso_HT() {
		return ingreso_HT;
	}
	public int getAsistencia_RM() {
		return asistencia_RM;
	}
	public int getIngreso_RM() {
		return ingreso_RM;
	}
	public int getAsistencia_total() {
		return asistencia_total;
	}
	public int getIngreso_total() {
		return ingreso_total;
	}
	public int getAsistencia_online() {
		return asistencia_online;
	}
	public int getAsistencia_online_prev_year() {
		return asistencia_online_prev_year;
	}
	public void setAsistencia_online_prev_year(int asistencia_online_prev_year) {
		this.asistencia_online_prev_year = asistencia_online_prev_year;
	}
	public float getPorc_asistencia_online_prev_year() {
		return porc_asistencia_online_prev_year;
	}
	public void setPorc_asistencia_online_prev_year(
			float porc_asistencia_online_prev_year) {
		this.porc_asistencia_online_prev_year = porc_asistencia_online_prev_year;
	}
	public float getPorc_asistencia_HT() {
		return porc_asistencia_HT;
	}
	public float getCrec_asistencia_RM() {
		return crec_asistencia_RM;
	}
	public float getPorc_asistencia_online() {
		return porc_asistencia_online;
	}
	public float getProm_HT() {
		return prom_HT;
	}
	public float getProm_RM() {
		return prom_RM;
	}
	public float getProm_total() {
		return prom_total;
	}
	public void setSemana(int semana) {
		this.semana = semana;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public void setAsistencia_HT(int asistencia_HT) {
		this.asistencia_HT = asistencia_HT;
	}
	public void setIngreso_HT(int ingreso_HT) {
		this.ingreso_HT = ingreso_HT;
	}
	public void setAsistencia_RM(int asistencia_RM) {
		this.asistencia_RM = asistencia_RM;
	}
	public void setIngreso_RM(int ingreso_RM) {
		this.ingreso_RM = ingreso_RM;
	}
	public void setAsistencia_total(int asistencia_total) {
		this.asistencia_total = asistencia_total;
	}
	public void setIngreso_total(int ingreso_total) {
		this.ingreso_total = ingreso_total;
	}
	public void setAsistencia_online(int asistencia_online) {
		this.asistencia_online = asistencia_online;
	}
	public void setPorc_asistencia_HT(float porc_asistencia_HT) {
		this.porc_asistencia_HT = porc_asistencia_HT;
	}
	public void setCrec_asistencia_RM(float crec_asistencia_RM) {
		this.crec_asistencia_RM = crec_asistencia_RM;
	}
	public void setPorc_asistencia_online(float porc_asistencia_online) {
		this.porc_asistencia_online = porc_asistencia_online;
	}
	public void setProm_HT(float prom_HT) {
		this.prom_HT = prom_HT;
	}
	public void setProm_RM(float prom_RM) {
		this.prom_RM = prom_RM;
	}
	public void setProm_total(float prom_total) {
		this.prom_total = prom_total;
	}
	
	public String[] getHeader() {
		return new String[] {
				"Date","Week","A(HT)","Inc(HT)","A(RM)",
				"Inc(RM)","A(Tot)","Inc(Tot)","A(On)","%(HT)","gro(RM)",
				"%(On)","Avg(HT)","Avg(RM)","Avg(Tot)"
		};
	}
	
	public String[] getString() {
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
		return  new String[] { 
				String.valueOf(dt.format(fecha)), 
				String.valueOf(semana),
				String.valueOf(asistencia_HT),
				String.valueOf(ingreso_HT),
				String.valueOf(asistencia_RM),
				String.valueOf(ingreso_RM),
				String.valueOf(asistencia_total),
				String.valueOf(ingreso_total),
				String.valueOf(asistencia_online),
				String.valueOf(porc_asistencia_HT),
				String.valueOf(crec_asistencia_RM),
				String.valueOf(porc_asistencia_online),
				String.valueOf(prom_HT),
				String.valueOf(prom_RM),
				String.valueOf(prom_total),
		};
		
	}

	
	

	

}
