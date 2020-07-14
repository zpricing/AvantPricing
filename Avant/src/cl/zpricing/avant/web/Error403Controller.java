package cl.zpricing.avant.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.SimpleFormController;

public class Error403Controller extends SimpleFormController {
	
	@SuppressWarnings("unchecked")
	public Map formBackingObject(HttpServletRequest request) {

		HashMap map = new HashMap<String, Object>();

		return map;
	}

}
