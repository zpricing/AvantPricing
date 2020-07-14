package cl.zpricing.avant.web.prediction;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 * 
 * <b>Devuelve el método correcto a llamar cuando se entra a
 * prediccionincompleta.htm. Código obtenido desde
 * http://www.cwinters.com/blog/
 * 2004/02/18/spring_setting_a_default_action_for_multiactioncontroller.html</b>
 * 
 * Registro de versiones:
 * <ul>
 * <li>1.0 24-12-2009 Camilo Araya: versión inicial.</li>
 * </ul>
 * <P>
 * <B>Todos los derechos reservados por Zheta Pricing.</B>
 * <P>
 */
public class CommonMethodNameResolver implements
		MethodNameResolver {

	private String paramName, defaultMethod;

	@Override
	public String getHandlerMethodName(HttpServletRequest request)
			throws NoSuchRequestHandlingMethodException {
		String name = request.getParameter(paramName);
		if (name == null) {
			return defaultMethod;
		}
		if (name.equalsIgnoreCase("")) {
			name = defaultMethod;
		}

		// name="referenceData";
		return name;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public void setDefaultMethod(String defaultMethod) {
		this.defaultMethod = defaultMethod;
	}

}
