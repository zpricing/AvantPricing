package cl.zpricing.avant.web.reports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.infosoftglobal.fusioncharts.FusionChartsCreator;

public class EjemploReporteController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		@SuppressWarnings("unused")
		String grafico = FusionChartsCreator.createChart("", "", "XML", "", 200, 200, false, false);
		return null;
	}
}
