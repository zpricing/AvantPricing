package cl.zpricing.commons.charts.vo;

import java.util.ArrayList;
import java.util.HashMap;

public class DatosGraficoSingleSeriesCharts extends DatosGrafico {
	private ArrayList<HashMap<String, String>> valores;
	
	public DatosGraficoSingleSeriesCharts() {
		super();
		valores = new ArrayList<HashMap<String,String>>();
	}
	
	public ArrayList<HashMap<String, String>> getValores() {
		return valores;
	}
	public void setValores(ArrayList<HashMap<String, String>> valores) {
		this.valores = valores;
	}
}
