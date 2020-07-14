package cl.zpricing.avant.web;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Controller
@Service("springService")
@RemoteProxy(name="dwrService")
@Transactional

public class AjaxTestController {
	
	@RemoteMethod
	public String getHolaMundo() {
		return "Hola mundo";
	}
}
