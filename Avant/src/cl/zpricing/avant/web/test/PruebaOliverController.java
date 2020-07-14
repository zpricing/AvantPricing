package cl.zpricing.avant.web.test;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cl.zpricing.avant.web.chart.CategoriaGrafico;
import cl.zpricing.avant.web.chart.GeneradorXMLGrafico;
import cl.zpricing.avant.web.chart.SerieDatos;
import cl.zpricing.avant.web.chart.Valor;

public class PruebaOliverController implements Controller {
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		GeneradorXMLGrafico grafico = new GeneradorXMLGrafico();
		grafico.setRutaArchivo("prueba.xml");
		
		SerieDatos serieDatos;
		List<SerieDatos> seriesDatos = new ArrayList<SerieDatos>();
		List<CategoriaGrafico> categorias = new ArrayList<CategoriaGrafico>();
		for (int i = 0; i < 5; i++) {
			serieDatos = new SerieDatos();
			categorias.add(new CategoriaGrafico(""+i));
			List<Valor> valores = new ArrayList<Valor>();
			for (int j = 0; j < 5; j++) {
				valores.add(new Valor(i+j));
			}
			serieDatos.setNombreSerie("�����sd");
			serieDatos.setValores(valores);
			seriesDatos.add(serieDatos);
		}
		grafico.setTitulo("Prueba �N");
		grafico.setDatos(seriesDatos);
		grafico.setCategorias(categorias);
		
		grafico.aXML();
		
		return new ModelAndView("pruebaoliver");
	}
	
}