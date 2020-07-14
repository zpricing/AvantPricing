package cl.zpricing.avant.servicios;

import java.util.ArrayList;
import java.util.Date;

import cl.zpricing.avant.model.Diaria;
import cl.zpricing.avant.model.Mensual;
import cl.zpricing.avant.model.Semanal;

public interface ReportesDao{
	public ArrayList<Diaria> obtenerReporteDiario(Date inicio, Date fin);
	public ArrayList<Semanal> obtenerReporteSemanal(Date inicio, Date fin);
	public ArrayList<Mensual> obtenerReporteMensual(Date inicio, Date fin);
	public void actualizarReporteDiario();
	public void actualizarReporteSemanal();
	public void actualizarReporteMensual();
}


