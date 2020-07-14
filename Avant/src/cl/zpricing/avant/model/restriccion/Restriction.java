package cl.zpricing.avant.model.restriccion;

import java.util.List;

import org.apache.log4j.Logger;

import cl.zpricing.avant.model.DescripcionId;
import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.FuncionArea;

public class Restriction extends DescripcionId {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	private List<Rule> reglas;

	public List<Rule> getReglas() {
		return reglas;
	}

	public void setReglas(List<Rule> reglas) {
		this.reglas = reglas;
	}
	
	public boolean aplicaRestriccion(Funcion funcion, FuncionArea funcionArea) {
		log.debug("Aplicando Restriccion [" + this.getDescripcion() + "]");
		log.debug("  Cantidad de Reglas [" + this.reglas.size() + "]");
		for (Rule rule : this.reglas) {
			if (rule.apply(funcion, funcionArea)) {
				log.debug(" Regla Aplica");
				return true;
			}
			log.debug(" Regla NO Aplica");
		}
		return false;
	}
}